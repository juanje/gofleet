package org.gofleet.openLS;

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

import java.util.Random;

import org.appfuse.dao.BaseDaoTestCase;
import org.gofleet.bbdd.dao.StreetHome;
import org.gofleet.openLS.bean.TSPPlan;
import org.gofleet.openLS.exceptions.TSPException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vividsolutions.jts.geom.Point;

public class RoutingServiceTest extends BaseDaoTestCase {
	private Random random = new Random();
	@Autowired
	private StreetHome streetHome;
	@Autowired
	private RoutingService routingService;

	@Test
	public void testGetTravellingSalesmanPlanMinVehicle() throws TSPException {
		int maxVehicles = random.nextInt(15) + 1;

		Point centroid = streetHome.getRandom().getThe_geom().getCentroid();

		TSPPlan[] param = new TSPPlan[maxVehicles];

		for (int j = 0; j < maxVehicles; j++) {
			int i = random.nextInt(4) + 4;
			String[] stops = new String[i];
			for (int k = 0; k < i; k++) {
				Point p = streetHome.getRandom().getThe_geom().getCentroid();
				stops[k] = p.getCentroid().getX() + ","
						+ p.getCentroid().getY() + "," + "Parada " + k;
			}
			param[j] = new TSPPlan(1d, -1d, null, stops, centroid);
		}
		assertNotNull(routingService.getTravellingSalesmanPlanMinVehicle(param,
				random.nextInt(100) + 100, random.nextInt(100) + 100,
				random.nextInt(10), random.nextInt(12)));

	}
}
