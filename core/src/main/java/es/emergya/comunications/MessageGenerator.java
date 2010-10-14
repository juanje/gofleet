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
package es.emergya.comunications;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.BandejaSalida;
import es.emergya.comunications.exceptions.MessageGeneratingException;
import es.emergya.consultas.TipoMensajeConsultas;
import es.emergya.utils.LogicConstants;
import es.emergya.utils.MyBeanFactory;

/**
 * @author marias
 * 
 */
@SuppressWarnings("unchecked")
public class MessageGenerator {

	private static Log log = LogFactory.getLog(MessageGenerator.class);
	private static GenericDao<BandejaSalida, Long> bandejaSalidaDAO;

	@Autowired
	public static void setBandejaSalidaDAO(
			GenericDao<BandejaSalida, Long> bandejaSalidaDAO) {
		MessageGenerator.bandejaSalidaDAO = bandejaSalidaDAO;
	}

	static {
		bandejaSalidaDAO = (GenericDao<BandejaSalida, Long>) MyBeanFactory
				.getBean("bandejaSalidaDAO");
	}

	/**
	 * 
	 * @param tipo
	 * @param prioridad
	 * @param cuerpo
	 * @param destino
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public static BandejaSalida sendMessage(Integer codigo, Integer tipo,
			Integer prioridad, String cuerpo, String destino)
			throws MessageGeneratingException {

		if (destino == null) {
			throw new MessageGeneratingException("Destino nulo.");
		}
		if (codigo == null) {
			throw new MessageGeneratingException("Mensaje sin codigo.");
		}
		if (tipo == null) {
			throw new MessageGeneratingException("Mensaje sin tipo.");
		}
		if (prioridad == null) {
			throw new MessageGeneratingException("Prioridad nula");
		}
		if (cuerpo == null) {
			cuerpo = "";
		}

		log.info("sendMessage(" + codigo + "," + tipo + "," + prioridad + ","
				+ cuerpo + "," + destino + ")");

		try {
			Integer tipo_tetra = TipoMensajeConsultas.getTipoByCode(codigo)
					.getTipoTetra();

			// Limpiamos las comillas:
			cuerpo = StringUtils.remove(cuerpo, "'");

			String datagramaTetra = getDatagrama(codigo, tipo, cuerpo);

			BandejaSalida out = new BandejaSalida();
			out.setMarcaTemporal(Calendar.getInstance().getTime());
			out.setDatagramaTetra(datagramaTetra);
			out.setPrioridad(prioridad);
			out.setDestino(StringUtils.leftPad(destino, LogicConstants.getInt(
					"LONGITUD_ISSI", 8), '0'));
			out.setTipo(tipo_tetra);

			log.info("Enviamos el mensaje " + datagramaTetra + " a " + destino
					+ " con prioridad " + prioridad);

			out = bandejaSalidaDAO.save(out);
			log.info("Enviando " + out);
			return out;
		} catch (Exception e) {
			throw new MessageGeneratingException("Error al generar mensaje", e);
		}
	}

	/**
	 * Devuelve el cuerpo del mensaje tetra.
	 * 
	 * @param codigo
	 * @param tipo
	 * @param cuerpo
	 * @return
	 */
	protected static String getDatagrama(Integer codigo, Integer tipo,
			String cuerpo) {
		String datagramaTetra = null;

		if (log.isTraceEnabled()) {
			log.trace("Tipos de mensaje sin tipo:");
			for (Integer i : LogicConstants.MENSAJES_SIN_TIPO) {
				log.trace(">Tipo: " + i);
			}
			log.trace("Nosotros tenemos un tipo " + tipo);
		}

		for (Integer i : LogicConstants.MENSAJES_SIN_TIPO) {
			if (tipo.equals(i)) {
				log.info("mensaje sin codigo ni tipo");
				datagramaTetra = cuerpo;
			}
		}
		if (datagramaTetra == null) {
			log.error("mensaje con codigo y tipo " + tipo);
			datagramaTetra = codigo + LogicConstants.FIELD_SEPARATOR + cuerpo
					+ LogicConstants.FIELD_SEPARATOR;
		}
		return datagramaTetra;
	}

	/**
	 * @param id
	 *            Id del mensaje que se debe comprobar si existe o no
	 * @return Si el mensaje con id de bandeja de salida <code>id</code> aun
	 *         existe en la base de datos.
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public static boolean messageExists(Long id) {
		return bandejaSalidaDAO.exists(id);
	}

	/**
	 * @param id
	 *            Id del mensaje que se debe comprobar si existe o no
	 * @return Si el mensaje con id de bandeja de salida <code>id</code> aun
	 *         existe en la base de datos.
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public static void remove(Long id) {
		try {
			log.info("Cancelando el envío de " + id);
			bandejaSalidaDAO.remove(id);
		} catch (Throwable t) {
			log.error(t, t);
		}
	}
}
