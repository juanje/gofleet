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
package es.emergya;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sourceforge.gpstools.gpx.GpxType;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.auxbeans.IncidenciaWS;
import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.bean.Rol;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.bean.notmapped.Posicion;
import es.emergya.bbdd.dao.ClienteConectadoHome;
import es.emergya.bbdd.dao.HistoricoGPSHome;
import es.emergya.bbdd.dao.IncidenciaHome;
import es.emergya.bbdd.dao.UsuarioHome;
import es.emergya.constants.LogicConstants;
import es.emergya.gpx.GPXGenerator;

/**
 * Clase que implementa los servicios expuestos en el servicio web.
 * 
 * @author jlrodriguez
 * 
 */
public class Service implements IService {

	private final static Log LOG = LogFactory.getLog(Service.class);

	/**
	 * Comprueba que el usuario coincide con la contraseña y que el usuario esté
	 * habilitado. Además checkea si hay slots disponibles para la conexión de
	 * una nueva estación fija y elimina las entradas anticuadas.
	 * 
	 * @param username
	 *            nombre de usuario.
	 * @param password
	 *            hash de la contraseña.
	 * @param fsUid
	 *            identificador único de la FS generado en el arranque de la
	 *            misma.
	 * @return la cadena vacía si se permite el login y una cadena con la causa
	 *         en caso de que no se pueda hacer login.
	 */
	@Override
	public String loginEF(String username, String password, Long fsUid) {
		LOG.info("Autenticando a " + username + " en la EF con id " + fsUid);
		try {
			String resultado = "";
			ClienteConectadoHome clientesConectadosHome = new ClienteConectadoHome();
			UsuarioHome usuariosHome = new UsuarioHome();
			clientesConectadosHome
					.cleanOldClienteConectado(LogicConstants.EF_TIMEOUT_LOGIN_CHECK);
			int clientesConectados = clientesConectadosHome
					.countClienteConectado();

			Usuario user = usuariosHome.checkLogin(username, password);
			if (clientesConectados >= LogicConstants.MAX_CLIENTES_CONECTADOS) {
				resultado = "ws.login.toomanyclients";
			} else if (user == null) {
				resultado = "ws.login.wrongUsernameOrPassword";
			} else if (!user.getHabilitado()) {
				resultado = "ws.login.nonHabilitedUser";
			} else {
				clientesConectadosHome.addNewClienteConectado(user, fsUid);
			}
			LOG.info(resultado);

			return resultado;
		} catch (Throwable t) {
			LOG.error("Error durante la autenticación del usuario " + username
					+ " en la EF con id " + fsUid, t);
			throw new RuntimeException(t);
		}
	}

	/**
	 * Mantiene actualizada la tabla clientes conectados. Primero borra los
	 * clientes que llevan un tiempo sin actualizar su conexión. Después
	 * actualiza la columna ultima_conexión de la fila con x_cliente = fsUid.
	 * 
	 * @param fsUid
	 *            identificador de la estación fija
	 * @return <code>true</code> si se pudo actualizar la entrada.
	 *         <code>false</code> si no se encontró la entrada o esta llevaba
	 *         mucho tiempo sin actualizarse.
	 */
	@Override
	public boolean actualizaLoginEF(Long fsUid) {
		LOG.debug("Actualizando el registro de clientes_conectados de la EF con id "
				+ fsUid);
		try {
			ClienteConectadoHome clientesConectadosHome = new ClienteConectadoHome();
			clientesConectadosHome
					.cleanOldClienteConectado(LogicConstants.EF_TIMEOUT_LOGIN_CHECK);
			return clientesConectadosHome.updateLastConnected(fsUid);
		} catch (Throwable t) {
			LOG.error(
					"Error actualizando el registro de clientes_conectados de la EF con id "
							+ fsUid, t);
			throw new RuntimeException(t);
		}
	}

	/**
	 * Obtiene todos los recursos que hayan enviado alguna posición entre
	 * <code>fechaInicio</code> y <code>fechaFinal</code> y que el usuario
	 * <code>nombreUsuario</code> tenga permisos para supervisar según su rol.
	 * 
	 * @param nombreUsuario
	 *            nombre de usuario que hace la consulta.
	 * @param fechaInicio
	 *            fecha de inicio del periodo del consulta.
	 * @param fechaFinal
	 *            fecha de fin del periodo de consulta.
	 * @return la lista de todos los recuros que enviaron posiciones en el
	 *         periodo indicado y son supervisables según el rol de
	 *         nombreUsuario.
	 */
	@Override
	public String[] getRecursosEnPeriodo(String nombreUsuario,
			Calendar fechaInicio, Calendar fechaFinal, Long[] zonas) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getRecursosEnPeriodo: nombreUsuario=" + nombreUsuario
					+ ", fechaInicio="
					+ (fechaInicio == null ? null : fechaInicio.getTime())
					+ ", fechaFinal="
					+ (fechaFinal == null ? null : fechaFinal.getTime())
					+ ", zonas="
					+ (zonas == null ? null : Arrays.toString(zonas)));
		}
		try {

			UsuarioHome usuarioHome = new UsuarioHome();
			HistoricoGPSHome historicoHome = new HistoricoGPSHome();
			Usuario user = usuarioHome.find(nombreUsuario);

			if (user == null) {
				// Si no se encuentra el usuario no ha recursos que mostrar
				LOG.info("Usuario nulo. No devolvemos recursos");
				return new String[] {};
			}
			// Obtenemos todas las subflotas que puede supervisar el usuario a
			// partir de su rol
			Rol r = user.getRoles();
			if (r == null) {
				// Sin rol, no se pueden ver recursos
				LOG.info("Usuario sin rol. No devolvemos recursos.");
				return new String[] {};
			}
			// Utilizamos las flotas del rol para hacer una consulta sobre el
			// histórico.
			List<String> recursos = historicoHome
					.findRecursosInIntervalForFoltas(r.getFlotas(),
							fechaInicio, fechaFinal, user);
			LOG.info("Se encontraron los siguientes recursos: " + recursos);
			return recursos.toArray(new String[0]);
		} catch (Throwable t) {
			LOG.error("Se produjo un error buscando los recursos del periodo",
					t);
			throw new RuntimeException(t);
		}
	}

	@Override
	public IncidenciaWS[] getIncidenciasAbiertasEnPeriodo(String nombreUsuario,
			Calendar fechaInicio, Calendar fechaFinal, Long[] zonas) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getIncidenciasAbiertasEnPeriodo: nombreUsuario="
					+ nombreUsuario + ", fechaInicio="
					+ (fechaInicio == null ? null : fechaInicio.getTime())
					+ ", fechaFinal="
					+ (fechaFinal == null ? null : fechaFinal.getTime())
					+ ", zonas="
					+ (zonas == null ? null : Arrays.toString(zonas)));
		}
		try {
			IncidenciaHome incidenciaHome = new IncidenciaHome();

			List<Incidencia> incidencias = incidenciaHome
					.getIncidenciasEnPeriodo(nombreUsuario, fechaInicio,
							fechaFinal);
			IncidenciaWS[] incidenciasWS = new IncidenciaWS[incidencias.size()];
			int j = 0;
			for (Incidencia i : incidencias) {
				IncidenciaWS inciWS = new IncidenciaWS();
				BeanUtils.copyProperties(inciWS, i);
				incidenciasWS[j++] = inciWS;
			}
			LOG.info("Se encontraron incidencias: "
					+ Arrays.toString(incidenciasWS));
			return incidenciasWS;
		} catch (Throwable t) {
			LOG.error(
					"Se produjo un error al buscar la incidencias abiertas en el periodo",
					t);
			throw new RuntimeException(
					"Error consultando incidencias abiertas en periodo", t);
		}

	}

	@Override
	public String[] getRutasRecursos(String nombreUsuario,
			String[] listaRecursos, Calendar fechaInicio, Calendar fechaFin) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getRutasRecursos: nombreUsuario="
					+ nombreUsuario
					+ ", listaRecursos="
					+ (listaRecursos == null ? null : Arrays
							.toString(listaRecursos)) + ", fechaInicio="
					+ (fechaInicio == null ? null : fechaInicio.getTime())
					+ ", fechaFinal="
					+ (fechaFin == null ? null : fechaFin.getTime()));
		}
		try {
			if (listaRecursos == null || listaRecursos.length == 0) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Lista de recursos vacía. Devolviendo null.");
				}
				return null;
			}
			HistoricoGPSHome historicoHome = new HistoricoGPSHome();
			// Creamos una respuesta para cada elemento de la lista de recursos
			String[] respuesta = new String[listaRecursos.length];
			Date inicio = null, fin = null;
			if (fechaInicio != null) {
				inicio = fechaInicio.getTime();
			}
			if (fechaFin != null) {
				fin = fechaFin.getTime();
			}
			int i = 0;
			for (String recurso : listaRecursos) {
				List<HistoricoGPS> historico = historicoHome
						.getPosicionesEnIntervalo(recurso, inicio, fin);
				if (historico.size() > 0) {
					GPXGenerator generator = new GPXGenerator(recurso);
					StringWriter sw = new StringWriter(historico.size() * 300);
					GpxType gpxtype = generator.generaGPX(historico);
					generator.writeGPX(gpxtype, sw);
					respuesta[i++] = sw.toString();
				} else {
					respuesta[i++] = null;
				}
			}
			LOG.info("Se han encontrado las siguientes rutas "
					+ Arrays.toString(respuesta));
			return respuesta;
		} catch (Throwable t) {
			LOG.error(
					"Error obteniendo la ruta de los recuros "
							+ Arrays.toString(listaRecursos), t);
			throw new RuntimeException("Error obteniendo rutas de los recursos");
		}
	}

	/**
	 * Obtiene las rutas de los recursos indicados durante el intervalo pasado
	 * sólo consultando la base de datos.
	 * 
	 * @param nombreUsuario
	 *            nombre del usuario que hace la consulta
	 * @param listaRecursos
	 *            Lista de recursos cuyas rutas se quieren consultar
	 * @param fechaInicio
	 *            Fecha y hora de comienzo de la ruta.
	 * @param fechaFin
	 *            Fecha y hora de fin de la ruta.
	 * @return Un array con las rutas (en formato GPX). En cada posición se
	 *         puede encontrar la ruta correspondiente al recurso de esa misma
	 *         posición del parámetro listaRecuros.
	 */
	@Override
	public String[] getRutasRecursosFromBBDD(String nombreUsuario,
			String[] listaRecursos, Calendar fechaInicio, Calendar fechaFin) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getRutasRecursosFromBBDD: nombreUsuario="
					+ nombreUsuario
					+ ", listaRecursos="
					+ (listaRecursos == null ? null : Arrays
							.toString(listaRecursos)) + ", fechaInicio="
					+ (fechaInicio == null ? null : fechaInicio.getTime())
					+ ", fechaFinal="
					+ (fechaFin == null ? null : fechaFin.getTime()));
		}
		try {
			if (listaRecursos == null || listaRecursos.length == 0) {
				if (LOG.isInfoEnabled()) {
					LOG.info("Lista de recursos vacía. Devolviendo null.");
				}
				return null;
			}
			HistoricoGPSHome historicoHome = new HistoricoGPSHome();
			// Creamos una respuesta para cada elemento de la lista de recursos
			String[] respuesta = new String[listaRecursos.length];
			Date inicio = null, fin = null;
			if (fechaInicio != null) {
				inicio = fechaInicio.getTime();
			}
			if (fechaFin != null) {
				fin = fechaFin.getTime();
			}
			int i = 0;
			for (String recurso : listaRecursos) {
				List<HistoricoGPS> historico = historicoHome
						.getPosicionesEnIntervaloSoloBBDD(recurso, inicio, fin);
				if (historico.size() > 0) {
					GPXGenerator generator = new GPXGenerator(recurso);
					StringWriter sw = new StringWriter(historico.size() * 150);
					GpxType gpxtype = generator.generaGPX(historico);
					generator.writeGPX(gpxtype, sw);
					respuesta[i++] = sw.toString();
				} else {
					respuesta[i++] = null;
				}
			}
			LOG.info("Se han encontrado las siguientes rutas "
					+ Arrays.toString(respuesta));
			return respuesta;
		} catch (Throwable t) {
			LOG.error(
					"Error obteniendo la ruta de los recuros "
							+ Arrays.toString(listaRecursos), t);
			throw new RuntimeException("Error obteniendo rutas de los recursos");
		}
	}

	@Override
	public Posicion[] getUltimasPosiciones(String nombreUsuario,
			String[] idRecursos, Long[] zonas) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getUltimasPosicones: nombreUsuario=" + nombreUsuario
					+ ", idRecursos="
					+ (idRecursos == null ? null : Arrays.toString(idRecursos))
					+ ", zonas="
					+ (zonas == null ? null : Arrays.toString(zonas)));
		}

		try {
			HistoricoGPSHome historicoHome = new HistoricoGPSHome();

			Posicion[] ultimasPosiciones = historicoHome
					.getUltimasPosiciones(idRecursos);
			LOG.info("Ultimas posiciones: "
					+ Arrays.toString(ultimasPosiciones));

			return ultimasPosiciones;

		} catch (Throwable t) {
			LOG.error(
					"Error obteniendo las últimas posiciones de los recursos "
							+ Arrays.toString(idRecursos), t);
			throw new RuntimeException("Error obteniendo rutas de los recursos");
		}
	}

	@Override
	public Posicion[] getPosicionesIncidencias(String nombreUsuario,
			String[] idIncidencias, Long[] idZonas) {
		if (LOG.isInfoEnabled()) {
			LOG.info("getPosicionesIncidencias: nombreUsuario=" + nombreUsuario
					+ ", incidencias=" + idIncidencias == null ? null : Arrays
					.toString(idIncidencias));
		}
		try {
			HistoricoGPSHome historicoHome = new HistoricoGPSHome();
			Posicion[] ultimasPosiciones = historicoHome
					.getPosicionesIncidencias(idIncidencias);

			return ultimasPosiciones;

		} catch (Throwable t) {
			LOG.error(
					"Error obteniendo las últimas posiciones de las incidencias "
							+ Arrays.toString(idIncidencias), t);
			throw new RuntimeException("Error obteniendo rutas de los recursos");
		}
	}
}
