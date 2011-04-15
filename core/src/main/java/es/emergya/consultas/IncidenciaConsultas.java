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
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.dao.IncidenciaHome;
import es.emergya.utils.MyBeanFactory;

public class IncidenciaConsultas {

	private IncidenciaConsultas() {
		super();
	}

	static {
		incidenciaHome = (IncidenciaHome) MyBeanFactory
				.getBean("incidenciaHome");
	}

	static final Log log = LogFactory.getLog(IncidenciaConsultas.class);
	private static IncidenciaHome incidenciaHome;

	public static List<Incidencia> getAll() {
		return incidenciaHome.getAll();
	}

	public static List<Incidencia> getOpened() {
		return incidenciaHome.getOpened();
	}

	public static Incidencia get(Long id) {
		return incidenciaHome.get(id);
	}

	public static Incidencia find(String identificador) {
		return incidenciaHome.find(identificador);
	}

	public static Calendar lastUpdated() {
		return incidenciaHome.lastUpdated();
	}

	public static List<Incidencia> getByExample(Incidencia f) {
		return incidenciaHome.getByExample(f);
	}

	public static Object[] getCategorias(boolean hasBlankSpace) {
		List<Object> res = new LinkedList<Object>();
		if (hasBlankSpace)
			res.add("");
		res.addAll(incidenciaHome.getCategorias());
		return res.toArray(new Object[0]);
	}

	public static Object[] getStatuses(boolean hasBlankSpace) {
		List<Object> res = new LinkedList<Object>();
		if (hasBlankSpace)
			res.add("");
		res.addAll(incidenciaHome.getStatuses());
		return res.toArray(new Object[0]);
	}

	public static List<Incidencia> getIncidenciasEnPeriodo(
			String nombreUsuario, Calendar fechaInicio, Calendar fechaFinal) {
		return incidenciaHome.getIncidenciasEnPeriodo(nombreUsuario,
				fechaInicio, fechaFinal);
	}
}
