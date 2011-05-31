/*
 * Copyright (C) 2011, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:marias@emergya.com">María Arias</a>
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
package org.gofleet.bbdd.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.gofleet.bbdd.beans.Street;
import org.gofleet.configuration.Configuration;
import org.gofleet.openLS.bean.TSPPlan;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernatespatial.GeometryUserType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;

@Repository("routingHome")
public class RoutingHome extends GenericDaoHibernate<Street, Long> {

	private final static String table = Configuration.getConfiguration()
			.getString("routing_table", "ways");
	private final static String id = Configuration.getConfiguration()
			.getString("routing_id_field", "gid");
	private final static String the_geom = Configuration.getConfiguration()
			.getString("routing_geometry_field", "the_geom");
	private final static String source = Configuration.getConfiguration()
			.getString("routing_source_field", "source");
	private final static String target = Configuration.getConfiguration()
			.getString("routing_target_field", "target");
	private final static String cost = Configuration.getConfiguration()
			.getString("routing_cost_field", "cost");
	private final static String reverse_cost = Configuration.getConfiguration()
			.getString("routing_reverse_cost_field", "reverse_cost");
	private final static String rule = Configuration.getConfiguration()
			.getString("routing_rule_field", "rule");
	private final static String to_cost = Configuration.getConfiguration()
			.getString("routing_to_cost_field", "to_cost");
	private final static Integer SRID = Configuration.getConfiguration()
			.getInteger("routing_srid", 4326);
	private final static Double speed_average = Configuration
			.getConfiguration().getDouble("speed_average", 40);

	public enum funcion {
		SIMPLE, SHOOTING_STAR
	};

	public RoutingHome() {
		super(Street.class);
	}

	/**
	 * Devuelve la lista de ids de la ruta desde vertice_origen a
	 * vertice_destino.
	 * 
	 * Utiliza la funcion shooting_star
	 * 
	 * @param origin
	 * @param goal
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	private List<Long> shortest_path_shooting_star(final Long origin,
			final Long goal) {
		final List<Long> lista = new ArrayList<Long>();
		try {
			Session currentSession = getSessionFactory().getCurrentSession();
			CallableStatement consulta = currentSession.connection()
					.prepareCall(
							"{call shortest_path_shooting_star(?,?,?,?,?)}");

			consulta.setString(1, "SELECT " + id + "::integer as id, " + source
					+ "::integer as source, " + target
					+ "::integer as target, " + cost + " as cost,"
					+ reverse_cost + " as reverse_cost, "
					+ "ST_X(ST_StartPoint(" + the_geom + ")) as x1,"
					+ "ST_Y(ST_StartPoint(" + the_geom + ")) as y1,"
					+ "ST_X(ST_EndPoint(" + the_geom + ")) as x2,"
					+ "ST_Y(ST_EndPoint(" + the_geom + ")) as y2," + rule
					+ " as rule, " + to_cost + " as to_cost FROM " + table
			// + " order by " + id
			);
			consulta.setInt(2, origin.intValue());
			consulta.setInt(3, goal.intValue());
			consulta.setBoolean(4, true);
			consulta.setBoolean(5, true);
			log.trace(consulta);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next())
				lista.add(resultado.getLong("edge_id"));

		} catch (Exception e) {
			log.error("No se pudo calcular la ruta", e);
		}

		return lista;
	}

	/**
	 * Devuelve la lista de ids de la ruta desde vertice_origen a
	 * vertice_destino
	 * 
	 * @param origin
	 * @param goal
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	private List<Long> getSimpleGid(final Long origin, final Long goal) {
		final List<Long> lista = new ArrayList<Long>();
		try {
			Session currentSession = getSessionFactory().getCurrentSession();
			CallableStatement consulta = currentSession.connection()
					.prepareCall("{call shortest_path(?,?,?,?,?)}");
			consulta.setString(1, "SELECT id, " + source + "::int4, " + target
					+ "::int4, " + "ST_length2d(" + the_geom
					+ ")::float8 as cost FROM " + table);
			consulta.setLong(2, origin);
			consulta.setLong(3, goal);
			consulta.setBoolean(4, false);
			consulta.setBoolean(5, false);
			log.trace(consulta);
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next())
				lista.add(resultado.getLong("edge_id"));
		} catch (Exception e) {
			log.error("Error computing route", e);
		}

		return lista;
	}

	/**
	 * Devuelve null si hay algún error.
	 * 
	 * @param origen
	 * @param destino
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	public MultiLineString calculateRoute(final Point origen,
			final Point destino, final funcion f) {
		log.trace("calculateRoute(" + origen + ", " + destino + ", " + f + ")");
		MultiLineString resultado = null;
		try {
			Long origin = getVertex(origen, false);
			Long goal = getVertex(destino, true);
			List<Long> ids = new ArrayList<Long>(0);

			if (origin != null && goal != null)
				switch (f) {
				case SIMPLE:
					ids = getSimpleGid(origin, goal);
					break;
				case SHOOTING_STAR:
					ids = shortest_path_shooting_star(origin, goal);
					break;
				}

			if (ids.size() > 0) {

				Session currentSession = getSessionFactory()
						.getCurrentSession();
				final Criteria criteria = currentSession
						.createCriteria(Street.class)
						.add(Restrictions.in("id", ids))
						.setProjection(Projections.property("geometria"));
				log.trace(criteria);
				List<Object> lineas = criteria.list();
				List<LineString> lineStrings = new LinkedList<LineString>();

				for (Object m : lineas) {
					if (m instanceof MultiLineString)
						for (int i = 0; i < ((MultiLineString) m)
								.getNumGeometries(); i++)
							lineStrings.add((LineString) ((MultiLineString) m)
									.getGeometryN(i));
					else if (m instanceof LineString)
						lineStrings.add((LineString) m);
					else
						log.error("Returned something strange: " + m);
				}

				resultado = new MultiLineString(
						lineStrings.toArray(new LineString[0]),
						new GeometryFactory());

				resultado.setSRID(4326);

				if (log.isTraceEnabled())
					log.trace("Result: " + resultado);
			}
		} catch (Throwable t) {
			log.error("Error computing route", t);
			resultado = null;
		}

		return resultado;
	}

	/**
	 * Devuelve el id del vértice más cercano, calculado según la tabla de
	 * routing.
	 * 
	 * @param p
	 * @param end
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	private Long getVertex(Point p, boolean end) {
		log.trace("getVertex(" + p + ", " + end + ")");
		Long res = -1l;
		try {
			Session currentSession = getSessionFactory().getCurrentSession();
			String point = "Start";
			if (end) {
				point = "End";
			}

			Query q = currentSession.createQuery("select " + id + " from "
					+ table
					+ " order by ST_Distance(ST_SETSRID(ST_POINT(ST_X(ST_"
					+ point + "Point(" + the_geom + ")),ST_Y(ST_" + point
					+ "Point(" + the_geom + "))), " + SRID + ")"
					+ ",ST_SETSRID(?, " + SRID + ")) asc");
			q.setParameter(0, p, GeometryUserType.TYPE);
			log.trace("SRID " + p.getSRID());
			q.setMaxResults(1);
			log.trace(q);
			res = (Long) q.uniqueResult();
		} catch (Throwable t) {
			log.error("Error al calcular el vertice mas cercano" + t, t);
		}
		log.trace(res);

		return res;
	}

	/**
	 * Devuelve el id del vértice más cercano, calculado según la tabla de
	 * routing.
	 * 
	 * @param p
	 * @param end
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	private Integer getEdge(Point p) {
		log.trace("getEdge(" + p + ")");
		Integer res = -1;
		try {
			Session currentSession = getSessionFactory().getCurrentSession();

			Query q = currentSession.createSQLQuery("select " + id + " from "
					+ table + " order by ST_Distance(the_geom,ST_SETSRID(?, "
					+ SRID + ")) asc");
			q.setParameter(0, p, GeometryUserType.TYPE);
			q.setMaxResults(1);
			log.trace(q);
			res = (Integer) q.uniqueResult();
		} catch (Throwable t) {
			log.error("Error calculating nearest edge" + t, t);
		}
		log.trace(res);

		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public TSPPlan[] getTSP(Integer maxVehicles, Integer maxTiempo,
			Point[][] stops, Integer timeSpentOnStop, Integer startTime,
			Point origin, TSPPlan[] res) {

		try {
			log.debug("getTSP(" + maxVehicles + ", " + maxTiempo + ", " + stops
					+ ", " + timeSpentOnStop + ", " + startTime + ", " + origin
					+ ")");
			Integer origin_id = getEdge(origin).intValue();
			Session currentSession = getSessionFactory().getCurrentSession();

			LineString[] lineStrings2 = new LineString[0];
			GeometryFactory factory = new GeometryFactory();
			// Loop through every zone:
			int i = 0;
			String tmpTable = "puntos";
			for (Point[] stops_ : stops) {
				List<Long> lista = new LinkedList<Long>();
				// Creating temporary table for stop points
				// String tmpTable = "puntos_" + System.currentTimeMillis();
				// query = "CREATE TEMPORARY TABLE " + tmpTable
				// + " (id INTEGER NOT NULL PRIMARY KEY)";
				// currentSession.createSQLQuery(query).executeUpdate();

				String query = "DELETE FROM " + tmpTable;
				currentSession.createSQLQuery(query).executeUpdate();
				currentSession.flush();
				List<Integer> stop_list = new LinkedList<Integer>();
				// Generating stop points
				for (Point stop : stops_) {
					Integer edge_id = getEdge(stop);
					if (edge_id != -1 && !stop_list.contains(edge_id)) {
						stop_list.add(edge_id);
					}
				}

				for (Integer edge_id : stop_list) {
					log.info(edge_id);
					query = "INSERT INTO " + tmpTable + " (id) VALUES ("
							+ edge_id + ")";
					currentSession.createSQLQuery(query).executeUpdate();
				}
				currentSession.flush();

				log.debug(stop_list);
				// Now we calculate the route:
				CallableStatement consulta = currentSession.connection()
						.prepareCall("{call gofleet_tsp(?,?,?,?)}");
				consulta.setString(1, RoutingHome.table);
				consulta.setString(2, "puntos");
				consulta.setString(3, RoutingHome.id);
				consulta.setInt(4, origin_id);
				log.info("select * from gofleet_tsp(" + RoutingHome.table
						+ ", " + tmpTable + ", " + RoutingHome.id + ", "
						+ origin_id + ")");
				ResultSet resultado = consulta.executeQuery();

				while (resultado.next())
					lista.add(resultado.getLong("edge"));

				if (lista.size() != 0) {
					List<MultiLineString> linestrings = (List<MultiLineString>) currentSession
							.createCriteria(Street.class)
							.setProjection(Projections.property("the_geom"))
							.add(Restrictions.isNotNull("the_geom"))
							.add(Restrictions.in(RoutingHome.id, lista)).list();

					List<LineString> realLinestrings = new LinkedList<LineString>();
					for (MultiLineString m : linestrings) {
						for (int n = 0; n < m.getNumGeometries(); n++)
							realLinestrings.add((LineString) m.getGeometryN(n));
					}

					LineString[] array = realLinestrings.toArray(lineStrings2);
					MultiLineString way = new MultiLineString(array, factory);
					Double distance = way.getLength();
					Double time = (distance * speed_average + stops_.length
							* timeSpentOnStop);

					res[i++].setDistance(distance);
					res[i++].setOrigin(origin);
					res[i++].setTime(time);
					res[i++].setWay((new WKTWriter()).write(way));
				}
			}
		} catch (Throwable t) {
			log.error("Error computing TSP", t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public TSPPlan getTSP(Integer maxTiempo,
			Point[] stops, Integer timeSpentOnStop, Integer startTime,
			Point origin, TSPPlan res) {

		try {
			log.debug("getTSP(" + ", " + maxTiempo + ", " + stops
					+ ", " + timeSpentOnStop + ", " + startTime + ", " + origin
					+ ")");
			Integer origin_id = getEdge(origin).intValue();
			Session currentSession = getSessionFactory().getCurrentSession();

			LineString[] lineStrings2 = new LineString[0];
			GeometryFactory factory = new GeometryFactory();
			// Loop through every zone:
			String tmpTable = "puntos";

			List<Long> lista = new LinkedList<Long>();
			// Creating temporary table for stop points
			// String tmpTable = "puntos_" + System.currentTimeMillis();
			// query = "CREATE TEMPORARY TABLE " + tmpTable
			// + " (id INTEGER NOT NULL PRIMARY KEY)";
			// currentSession.createSQLQuery(query).executeUpdate();

			String query = "DELETE FROM " + tmpTable;
			currentSession.createSQLQuery(query).executeUpdate();
			currentSession.flush();
			List<Integer> stop_list = new LinkedList<Integer>();
			// Generating stop points
			for (Point stop : stops) {
				Integer edge_id = getEdge(stop);
				if (edge_id != -1 && !stop_list.contains(edge_id)) {
					stop_list.add(edge_id);
				}
			}

			for (Integer edge_id : stop_list) {
				log.info(edge_id);
				query = "INSERT INTO " + tmpTable + " (id) VALUES (" + edge_id
						+ ")";
				currentSession.createSQLQuery(query).executeUpdate();
			}
			currentSession.flush();

			log.debug(stop_list);
			// Now we calculate the route:
			CallableStatement consulta = currentSession.connection()
					.prepareCall("{call gofleet_tsp(?,?,?,?)}");
			consulta.setString(1, RoutingHome.table);
			consulta.setString(2, "puntos");
			consulta.setString(3, RoutingHome.id);
			consulta.setInt(4, origin_id);
			log.info("select * from gofleet_tsp(" + RoutingHome.table + ", "
					+ tmpTable + ", " + RoutingHome.id + ", " + origin_id + ")");
			ResultSet resultado = consulta.executeQuery();

			while (resultado.next())
				lista.add(resultado.getLong("edge"));

			if (lista.size() != 0) {
				List<MultiLineString> linestrings = (List<MultiLineString>) currentSession
						.createCriteria(Street.class)
						.setProjection(Projections.property("the_geom"))
						.add(Restrictions.isNotNull("the_geom"))
						.add(Restrictions.in(RoutingHome.id, lista)).list();

				List<LineString> realLinestrings = new LinkedList<LineString>();
				for (MultiLineString m : linestrings) {
					for (int n = 0; n < m.getNumGeometries(); n++)
						realLinestrings.add((LineString) m.getGeometryN(n));
				}

				LineString[] array = realLinestrings.toArray(lineStrings2);
				MultiLineString way = new MultiLineString(array, factory);
				Double distance = way.getLength() * 60 * 1852 / 1000;
				Double time = (distance * speed_average + stops.length
						* timeSpentOnStop);

				res.setDistance(distance);
				res.setOrigin(origin);
				res.setTime(time);
				res.setWay((new WKTWriter()).write(way));

			}
		} catch (Throwable t) {
			log.error("Error computing TSP", t);
		}
		return res;
	}
}
