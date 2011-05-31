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
import org.apache.commons.logging.LogFactory;
import org.gofleet.bbdd.dao.RoutingHome;
import org.gofleet.openLS.bean.TSPPlan;
import org.gofleet.openLS.bean.TSPStop;
import org.gofleet.openLS.exceptions.TSPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

@Repository("routingService")
public class RoutingService{

	@Autowired
	private RoutingHome routingHome;
	private GeometryFactory geometryFactory = new GeometryFactory(
			new PrecisionModel(), 4326);
	private static org.apache.commons.logging.Log log = LogFactory
			.getLog(RoutingService.class);

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
	public TSPPlan[] getTravellingSalesmanPlanMinVehicle(TSPPlan[] param,
			Integer maxDistance, Integer timeSpentOnStop, Integer maxTime,
			Integer startTime) throws TSPException {

		log.debug("getTravellingSalesmanPlanMinVehicle(" + param + ")");
		TSPPlan[] res = null;
		try {

			if (param.length == 0)
				throw new TSPException("Received an empty set of plan");

			Point[][] stops_ = new Point[param.length][];

			for (int i = 0; i < param.length; i++) {
				TSPStop[] st = new TSPStop[param[i].getStops().length];
				int i_ = 0;
				for (String stop : param[i].getStops()) {
					st[i_++] = new TSPStop(stop);
				}

				stops_[i] = new Point[st.length];
				for (int k = 0; k < st.length; k++) {
					stops_[i][k] = getPoint(st[k].getPoint());
				}
			}

			Point origin_ = getPoint(param[0].getOrigin());
			res = routingHome.getTSP(param.length, maxTime, stops_,
					timeSpentOnStop, startTime, origin_, param);
		} catch (Throwable t) {
			log.error("Error calculating route plan", t);
			throw new TSPException("Error calculating route plan");
		}
		if (res == null || res.length > param.length)
			throw new TSPException(
					"We couldn't find a proper plan with the given data. Try with other parameters.");
		return res;
	}

	public TSPPlan getTravellingSalesmanPlan(TSPPlan param,
			Integer maxDistance, Integer timeSpentOnStop, Integer maxTime,
			Integer startTime) throws TSPException {

		log.info("getTravellingSalesmanPlanMinVehicle(" + param + ")");
		TSPPlan res = null;
		try {

			if (param == null)
				throw new TSPException("Received an empty set of plan");

			TSPStop[] st = new TSPStop[param.getStops().length];
			int i_ = 0;
			for (String stop : param.getStops()) {
				st[i_++] = new TSPStop(stop);
			}

			Point[] stops_ = new Point[st.length];
			for (int k = 0; k < st.length; k++) {
				stops_[k] = getPoint(st[k].getPoint());
			}

			Point origin_ = getPoint(param.getOrigin());
			if(routingHome == null)
				throw new TSPException("RoutingHome is null!");
			res = routingHome.getTSP(maxTime, stops_, timeSpentOnStop,
					startTime, origin_, param);
		} catch (Throwable t) {
			log.error("Error calculating route plan", t);
			throw new TSPException("Error calculating route plan");
		}
		if (res == null)
			throw new TSPException(
					"We couldn't find a proper plan with the given data. Try with other parameters.");
		return res;
	}

	private Point getPoint(double[] coor) {
		if (coor.length != 2)
			return null;

		return geometryFactory.createPoint(new Coordinate(coor[0], coor[1]));
	}
}
