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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.dao.CapaInformacionHome;
import es.emergya.utils.MyBeanFactory;

public class CapaConsultas {

	static final Log log = LogFactory.getLog(CapaConsultas.class);
	private static CapaInformacionHome capaInformacionHome;

	static {
		capaInformacionHome = (CapaInformacionHome) MyBeanFactory
				.getBean("capaInformacionHome");
	}

	private CapaConsultas() {
		super();
	}

	public static List<CapaInformacion> getAll() {
		return capaInformacionHome.getAll();
	}

        /**
         *
         * @return todas las capas de información ordenadas por el campo orden
         * ascendentemente.
         */
        public static List<CapaInformacion> getAllOrderedByOrden() {
		return capaInformacionHome.getAllOrderedByOrden();
	}

	public static List<CapaInformacion> getAll(boolean base, Boolean historico) {
		return capaInformacionHome.getAll(base, historico);
	}

	public static Integer getTotal() {
		return capaInformacionHome.getTotal();
	}

	public static List<CapaInformacion> getByExample(CapaInformacion p) {
		List<CapaInformacion> res = new ArrayList<CapaInformacion>(0);
		try {
			res = capaInformacionHome.getByFilter(p);
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static boolean alreadyExists(String text) {
		return capaInformacionHome.alreadyExists(text);
	}

	public static Calendar lastUpdated() {
		return capaInformacionHome.lastUpdated();
	}

	/**
	 * 
	 * @param nombre
	 * @return Devuelve la capa con el nombre nombre
	 */
	public static CapaInformacion getByNombre(String nombre) {
		return capaInformacionHome.getByNombre(nombre);
	}

}
