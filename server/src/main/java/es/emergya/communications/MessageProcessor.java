/*
 * Copyright (C) 2010, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:jlrodriguez@emergya.es">Juan Luís Rodríguez</a>
 * @author <a href="mailto:marias@emergya.es">María Arias</a>
 *
 * This file is part of GoFleet
 *
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
/**
 * 
 */
package es.emergya.communications;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import es.emergya.actions.HistoricoGPSAdmin;
import es.emergya.actions.RecursoAdmin;
import es.emergya.bbdd.bean.BandejaEntrada;
import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.bean.TipoMensaje;
import es.emergya.comunications.MessageGenerator;
import es.emergya.comunications.exceptions.MessageGeneratingException;
import es.emergya.comunications.exceptions.MessageProcessingException;
import es.emergya.consultas.BandejaEntradaConsultas;
import es.emergya.consultas.PatrullaConsultas;
import es.emergya.consultas.RecursoConsultas;
import es.emergya.consultas.TipoMensajeConsultas;
import es.emergya.utils.LogicConstants;

/**
 * @author marias
 * 
 */
public class MessageProcessor extends es.emergya.comunications.MessageProcessor {

    private static final Log log = LogFactory.getLog(MessageProcessor.class);
    private static final int POS_ACTUAL_CODE = 16;
    private static final int ASOCIACION_PATRULLA_CODE = 30;
    private static final int SDS_CODE = 31;

    /**
     * @see es.emergya.comunications.MessageProcessor#processMessage(es.emergya.bbdd.bean.BandejaEntrada,
     *      java.lang.String[], int)
     */
    @Override
    protected void processMessage(final BandejaEntrada entrada, final String[] campos,
            int codeMessageType) throws MessageProcessingException {
        switch (codeMessageType) {
            case MessageProcessor.POS_ACTUAL_CODE:
                processPOS_ACTUAL(entrada, campos);
                break;
            case MessageProcessor.ASOCIACION_PATRULLA_CODE:
                processASOCIACION_PATRULLA(entrada, campos);
                break;
            default:
        }
    }

    private void processPOS_ACTUAL(final BandejaEntrada entrada, final String[] campos)
            throws MessageProcessingException {
        /*
         * BODY = LATITUD | LONGITUD
         */
        try {
            HistoricoGPS historicoGPS = new HistoricoGPS();

            historicoGPS.setMarcaTemporal(entrada.getMarcaTemporal());

            final Recurso recurso = RecursoConsultas.getbyDispositivo(entrada.getOrigen());
            if (recurso == null) {
                throw new MessageProcessingException("No encuentro el recurso "
                        + entrada.getOrigen());
            }

            if (recurso.getHabilitado()) {
                historicoGPS.setTipoRecurso(recurso.getTipo());
                if (recurso.getFlotas() == null) {
                    throw new MessageProcessingException("El recurso "
                            + recurso + " no tiene asignada ninguna flota.");
                }
                historicoGPS.setSubflota(recurso.getFlotas().getNombre());
                historicoGPS.setRecurso(recurso.getIdentificador());

                Double y = new Double(campos[2].substring(0, campos[2].indexOf(',')));
                Double x = new Double(campos[3].substring(0, campos[3].indexOf(',')));

                // Las posiciones 0.0 se descartan.
                // Deshabilitado por instrucciones de MCGarcia.
//                if (x.equals(0.0d) || y.equals(0.0d)) {
//                    if (log.isTraceEnabled()) {
//                        log.trace("Posicón 0.0N,0.0W recibida de " + recurso.getDispositivo() + "recibida y descartada.");
//                    }
//                    return;
//                }

                if (campos[2].endsWith("S")) {
                    y = -y;
                }
                if (campos[3].endsWith("W")) {
                    x = -x;
                }

                final GeometryFactory factory = new GeometryFactory();
                final com.vividsolutions.jts.geom.Geometry geom = factory.createPoint(new Coordinate(x, y));
                geom.setSRID(4326);
                //
                // final String sourceSRID = "EPSG:4326";
                // final String targetSRID = "EPSG:3395";
                //
                // Geometry geom = transform(geom, sourceSRID, targetSRID);

                historicoGPS.setGeom(geom);
                historicoGPS.setPosX(geom.getCentroid().getX());
                historicoGPS.setPosY(geom.getCentroid().getY());

                HistoricoGPSAdmin.saveOrUpdate(historicoGPS);
                recurso.setHistoricoGps(historicoGPS);
                RecursoAdmin.saveOrUpdate(recurso);
                if (log.isDebugEnabled()) {
                    log.debug("Guardada posicion" + historicoGPS);
                }
            } else {
                log.error("Hay un recurso deshabilitado enviando posiciones: "
                        + recurso);
            }
        } catch (Throwable t) {
            log.error(t, t);
            throw new MessageProcessingException(
                    "Error al procesar un mensaje de posicion" + t, entrada);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("Finalizado procesamiento de " + entrada);
            }
        }

    }

    public static Geometry transform(final com.vividsolutions.jts.geom.Geometry geom,
            final String sourceSRID, final String targetSRID) {
        Geometry p = geom;
        try {
            final CoordinateReferenceSystem sourceCRS = CRS.decode(sourceSRID);
            final CoordinateReferenceSystem targetCRS = CRS.decode(targetSRID);
            final MathTransform transform = CRS.findMathTransform(sourceCRS,
                    targetCRS);
            final com.vividsolutions.jts.geom.Geometry targetGeometry = JTS.transform(geom, transform);
            p = targetGeometry;
            if (targetSRID.indexOf(":") > 0) {
                p.setSRID(Integer.valueOf(targetSRID.substring(targetSRID.indexOf(':') + 1)));
            }
        } catch (Throwable t) {
            log.error("Error al transformar la proyeccion", t);
        }
        return p;
    }

    private void processASOCIACION_PATRULLA(final BandejaEntrada entrada,
            final String[] campos) throws MessageProcessingException {
        /*
         * BODY = PATRULLA
         */
        String confirmacion = LogicConstants.get("SDS_NO",
                "Error al asignar el recurso.");
        TipoMensaje tmensaje = null;
        Recurso recurso = null;
        MessageProcessingException exception = null;
        try {
            tmensaje = TipoMensajeConsultas.getTipoByCode(SDS_CODE);
            recurso = RecursoConsultas.getbyDispositivo(entrada.getOrigen());
            if (recurso == null) {
                throw new MessageProcessingException("No encuentro el recurso "
                        + entrada.getOrigen());
            }

            if (!recurso.getHabilitado()) {
                throw new MessageProcessingException(
                        "El recurso está deshabilitado ('"
                        + recurso.getDispositivo() + "')");
            }
            recurso.setMalAsignado(true);

            Patrulla p = PatrullaConsultas.find(campos[2]);
            if (p == null) {
                throw new MessageProcessingException(
                        "No encuentro la patrulla " + campos[2]);
            }

            recurso.setPatrullas(p);
            recurso.setMalAsignado(false);
            confirmacion = LogicConstants.get("SDS_SI", "Recurso asignado.");

        } catch (Throwable t) {
            log.error(t, t);
            exception = new MessageProcessingException(
                    "Error al procesar un mensaje de asignacion de patrulla: "
                    + t, entrada);
        } finally {
            try {
                if (recurso != null) {
                    RecursoAdmin.saveOrUpdate(recurso);
                }
                MessageGenerator.sendMessage(tmensaje.getCodigo(), tmensaje.getTipoTetra(), tmensaje.getPrioridad(), confirmacion,
                        entrada.getOrigen());
            } catch (MessageGeneratingException e) {
                log.error(e, e);
                throw new MessageProcessingException(
                        "Error al enviar el mensaje " + e, entrada);
            } finally {
                if (exception != null) {
                    throw exception;
                }
            }
        }
    }

    @Deprecated
    private void processPOS_ACTUAL2(final BandejaEntrada entrada, final String[] campos)
            throws MessageProcessingException {
        log.debug("processPOS_ACTUAL2");
        final long time = System.currentTimeMillis();
        /*
         * BODY = LATITUD | LONGITUD
         */
        try {

            Double y = new Double(campos[2].substring(0, campos[2].indexOf(',')));
            Double x = new Double(campos[3].substring(0, campos[3].indexOf(',')));

            if (campos[2].endsWith("S")) {
                y = -y;
            }
            if (campos[3].endsWith("W")) {
                x = -x;
            }

            final GeometryFactory factory = new GeometryFactory();
            final com.vividsolutions.jts.geom.Geometry geom = factory.createPoint(new Coordinate(x, y));
            geom.setSRID(4326);
            RecursoConsultas.findByDispositivoSQL(entrada.getOrigen());

            BandejaEntradaConsultas.processPosiconActual(entrada, geom);

        } catch (Throwable t) {
            log.error(t, t);
            throw new MessageProcessingException(
                    "Error al procesar un mensaje de posicion" + t, entrada);
        } finally {
            log.debug("Finalizado procesamiento de " + entrada);
            System.out.println("TIEMPO " + entrada.getOrigen() + ": "
                    + (System.currentTimeMillis() - time) + " ms");
        }
    }
}
