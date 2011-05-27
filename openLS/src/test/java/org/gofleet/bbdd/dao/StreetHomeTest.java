package org.gofleet.bbdd.dao;
/*
 * Copyright (C) 2011, Emergya (http://www.emergya.es)
 *
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.appfuse.dao.BaseDaoTestCase;
import org.gofleet.bbdd.beans.Street;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiLineString;

public class StreetHomeTest extends BaseDaoTestCase {

	@Autowired
	private StreetHome streetHome;

	@Test
	public void test() {
		assertNotNull(streetHome);
	}

	@Test
	public void testGetRandom() {
		System.out.println("testGetRandom()");
		assertNotNull(streetHome.getRandom());
	}

	@Test
	public void testGetByNameStringInt() {
		System.out.println("Starting testGetByNameStringInt()");
		for (int i = 1; i < 100; i = i + 10) {
			List<Street> res = streetHome.getByName("%", i);
			assertNotNull(res);
			assertTrue(res.size() == i);
			for (Street s : res) {
				assertNotNull(s);
				assertNotNull(s.getThe_geom());
			}
		}

	}

	@Test
	public void testGetByNameString() {
		List<Street> res = streetHome.getByName("%");
		assertNotNull(res);
		assertTrue(res.size() == 20);

		for (Street s : res)
			assertNotNull(s);
	}

	@Test
	public void testGetByNameCalle() {
		List<Street> res = streetHome.getByName("Calle ", 5);
		assertNotNull(res);
		assertTrue(res.size() == 5);

		for (Street s : res) {
			assertNotNull(s);
			assertTrue(s.getName().indexOf("Calle ") == 0);
		}
	}

	@Test
	public void testGetByNameCalleTrajano() {
		Geometry res = streetHome.getExactlyUnifiedGeometry("Calle Trajano");
		assertNotNull(res);
		assertTrue(res instanceof MultiLineString);
	}
}
