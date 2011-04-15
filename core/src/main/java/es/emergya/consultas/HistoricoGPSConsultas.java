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
package es.emergya.consultas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.bean.notmapped.Posicion;
import es.emergya.bbdd.dao.HistoricoGPSHome;
import es.emergya.utils.MyBeanFactory;

public class HistoricoGPSConsultas {

	private static HistoricoGPSHome historicoGPSHome;

	static {
		historicoGPSHome = (HistoricoGPSHome) MyBeanFactory
				.getBean("historicoGPSHome");
	}

	/**
	 * @param r
	 *            El recurso del que queremos obtener la fecha de la ultima
	 *            posicion
	 * 
	 * @return La fecha de la ultima posicion de un recurso.
	 */
	public static Date lastGPSDateForRecurso(Recurso r) {
		return historicoGPSHome.lastGPSDateForRecurso(r);
	}

	/**
	 * @param r
	 *            El recurso del que queremos obtener la ultima posicion
	 * 
	 * @return La ultima posicion de un recurso.
	 */
	public static HistoricoGPS lastGPSForRecurso(Recurso r) {
		return historicoGPSHome.lastGPSForRecurso(r);
	}

	/**
	 * @param r
	 *            El recurso del que queremos obtener la ultima posicion
	 * 
	 * @return La ultima posicion de un recurso.
	 */
	public static HistoricoGPS lastGPSForRecurso(String r) {
		return historicoGPSHome.lastGPSForRecurso(r);
	}

	public static Calendar firstGPSDateForRecurso(Recurso recurso) {
		return historicoGPSHome.firstGPSForRecurso(recurso.getIdentificador());
	}

	public static List<String> findRecursosInIntervalForFoltas(
			Set<Flota> flotas, Calendar fechaInicio, Calendar fechaFinal,
			Usuario user) {
		return findRecursosInIntervalForFoltas(flotas, fechaInicio, fechaFinal,
				user);
	}

	public static List<HistoricoGPS> getPosicionesEnIntervalo(String recurso,
			Date inicio, Date fin) {
		return historicoGPSHome.getPosicionesEnIntervalo(recurso, inicio, fin);
	}

	public static List<HistoricoGPS> getPosicionesEnIntervaloSoloBBDD(
			String recurso, Date inicio, Date fin) {
		return historicoGPSHome.getPosicionesEnIntervaloSoloBBDD(recurso,
				inicio, fin);
	}

	public static Posicion[] getPosicionesIncidencias(String[] idIncidencias) {
		return getPosicionesIncidencias(idIncidencias);
	}

	public static Posicion[] getUltimasPosiciones(String[] idRecursos) {
		return historicoGPSHome.getUltimasPosiciones(idRecursos);
	}
}
