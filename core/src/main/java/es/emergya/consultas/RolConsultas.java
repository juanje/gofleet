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
import org.appfuse.dao.GenericDao;

import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.Rol;
import es.emergya.bbdd.dao.RolHome;
import es.emergya.utils.LogicConstants;
import es.emergya.utils.MyBeanFactory;

public class RolConsultas {

	private RolConsultas() {
		super();
	}

	static {
		rolHome = (RolHome) MyBeanFactory.getBean("rolHome");
	}

	static final Log log = LogFactory.getLog(RolConsultas.class);
	private static GenericDao<Rol, Long> rolDAO;
	private static RolHome rolHome;

	public static List<Rol> getAll() {
		List<Rol> res = new ArrayList<Rol>(0);
		try {
			res = rolDAO.getAllDistinct();
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static List<String> getAllNames() {
		List<String> res = new ArrayList<String>();
		try {
			res.addAll(rolHome.getAllString());
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static List<Rol> getByExample(Rol example) {
		List<Rol> res = new ArrayList<Rol>();
		try {
			if (example != null && example.getNombre() != null)
				res = rolHome.findByName(LogicConstants
						.getGenericString(example.getNombre()));
			else
				res = rolHome.getAll();
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static Rol findByName(String string) {
		Rol rol = null;
		try {
			rol = rolHome.find(string);
		} catch (Throwable t) {
			log.error(t, t);
		}
		return rol;
	}

	public static Integer getTotal() {
		return rolHome.getTotal();
	}

	public static Flota[] getDisponibles(Rol r) {
		return rolHome.getDisponibles(r).toArray(new Flota[0]);
	}

	public static Flota[] getAsigned(Rol r) {
		return rolHome.getAsigned(r).toArray(new Flota[0]);
	}

	public static Boolean alreadyExists(String nombre) {
		return rolHome.alreadyExists(nombre);
	}

	public static Calendar lastUpdated() {
		return rolHome.lastUpdated();
	}
}
