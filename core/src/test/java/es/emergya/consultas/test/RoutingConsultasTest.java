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
package es.emergya.consultas.test;

import org.appfuse.dao.BaseDaoTestCase;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

import es.emergya.consultas.RoutingConsultas;

public class RoutingConsultasTest extends BaseDaoTestCase {

	@Test
	@Transactional
	public void testGetRoute() throws Exception {
//		executeSqlScript("routing_pruebas.sql", false);
//		setComplete();
//
//		GeometryFactory factory = new GeometryFactory();
//		Point origen = factory.createPoint(new Coordinate(300d, -80d));
//		Point destino = factory.createPoint(new Coordinate(180d, -20d));
//		MultiLineString res = RoutingConsultas.calculateRoute(origen, destino);
//		assertNotNull(res);
//		assertEquals(
//				"MULTILINESTRING ((180 -20, 180.21797968012095 -45.90354832312572), "
//						+ "(220 -80, 181.03941055748223 -75.47505990813163), "
//						+ "(300 -80, 251.68246601055188 -79.17149885625736), "
//						+ "(251.68246601055188 -79.17149885625736, 245.5217344303423 -72.18933639868652, "
//						+ "227.8609705670749 -72.18933639868652, 220 -80), "
//						+ "(181.03941055748223 -75.47505990813163, 180.21797968012095 -45.90354832312572))",
//				res.toText());
	}
}
