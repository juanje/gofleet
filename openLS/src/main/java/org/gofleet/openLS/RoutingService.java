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
import org.gofleet.bbdd.dao.RoutingHome;
import org.gofleet.openLS.bean.TSPPlan;
import org.gofleet.openLS.exceptions.TSPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Point;

@Repository("routingService")
public class RoutingService {

	@Autowired
	private RoutingHome routingHome;

	public RoutingHome getRoutingHome() {
		return routingHome;
	}

	public void setRoutingHome(RoutingHome routingHome) {
		this.routingHome = routingHome;
	}

	/**
	 * 
	 * Given a maximum number of vehicles involved, a maximum distance for each
	 * vehicle, it returns the optimized travelling plan minimizing the vehicle
	 * variable.
	 * 
	 * Points are divided by zones.
	 * 
	 * @param maxVehicles
	 * @param maxDistance
	 * @param stops
	 * @param timeSpentOnStop
	 * @return
	 * @throws TSPException
	 */
	public TSPPlan[] getTravellingSalesmanPlanMinVehicle(Integer maxVehicles,
			Integer maxTime, Integer maxDistance, Point[][] stops,
			Integer timeSpentOnStop, Integer startTime, Point origin)
			throws TSPException {

		TSPPlan[] res = null;
		boolean done = false;
		for (int i = 1; i < maxVehicles && res == null && !done; i++) {
			res = routingHome.getTSP(i, maxDistance, stops, timeSpentOnStop,
					startTime, origin);
			done = true;
			for (TSPPlan p : res) {
				done = (done && p.getDistance() < maxDistance && p.getTime() < maxTime);
			}
		}

		if (res == null || res.length > maxVehicles)
			throw new TSPException(
					"We couldn't find a proper plan with the given data. Try with other parameters.");
		return res;
	}
}
