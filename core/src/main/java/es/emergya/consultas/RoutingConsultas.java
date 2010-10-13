/*
 * Copyright (C) 2010, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:jlrodriguez@emergya.es">Juan Luís Rodríguez</a>
 * @author <a href="mailto:marias@emergya.es">María Arias</a>
 *
 * This file is part of DEMOGIS
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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.GenericDao;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

import es.emergya.bbdd.bean.Routing;
import es.emergya.bbdd.dao.RoutingHome;
import es.emergya.utils.MyBeanFactory;

@SuppressWarnings("unchecked")
public class RoutingConsultas {
	static final Log log = LogFactory.getLog(RoutingConsultas.class);
	@SuppressWarnings("unused")
	private static GenericDao<Routing, Long> routingDAO = null;
	private static RoutingHome routingHome = null;

	static {
		routingDAO = (GenericDao<Routing, Long>) MyBeanFactory
				.getBean("routingDAO");
		routingHome = (RoutingHome) MyBeanFactory.getBean("routingHome");
	}

	/**
	 * Devuelve la ruta desde el punto origen al punto destino. Si no encuentra
	 * ninguna, devuelve null.
	 * 
	 * @param origen
	 * @param destino
	 * @return
	 */
	public static MultiLineString calculateRoute(Point origen, Point destino,
			RoutingHome.funcion funcion) {
		MultiLineString lineStrings = routingHome.calculateRoute(origen,
				destino, funcion);

		if (lineStrings == null || lineStrings.getNumGeometries() == 0)
			return null;

		return lineStrings;

	}

	/**
	 * @see #calculateRoute(Point, Point,
	 *      es.emergya.bbdd.dao.RoutingHome.funcion)
	 * @param origen
	 * @param destino
	 * @return
	 */
	public static MultiLineString calculateRoute(Point origen, Point destino) {
		return calculateRoute(origen, destino,
				RoutingHome.funcion.SHOOTING_STAR);
	}

	/**
	 * Devuelve la primera calle que encuentre con este nombre:
	 * 
	 * @param pattern
	 * @param num
	 * @return list of streets
	 */
	public static Routing find(final String calle) {
		return routingHome.find(calle);
	}

	/**
	 * Devuelve la lista de num calles que encajan con el patr�n
	 * 
	 * @param pattern
	 * @param num
	 * @return list of streets
	 */
	public static List<String> find(final String pattern, final Integer num) {
		return routingHome.find(pattern, num);
	}
}
