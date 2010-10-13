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
package es.emergya.bbdd.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.CustomType;
import org.hibernate.type.Type;
import org.hibernatespatial.GeometryUserType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

import es.emergya.bbdd.bean.Routing;
import es.emergya.utils.LogicConstants;

@Repository("routingHome")
public class RoutingHome extends GenericDaoHibernate<Routing, Long> {

	private final static String table = "Routing";
	private final static String id = "id";
	private final static String the_geom = "the_geom";
	private final static String source = "source";
	private final static String target = "target";
	private final static String cost = "cost";
	private final static String reverse_cost = "reverse_cost";
	private final static String rule = "rule";
	private final static String to_cost = "to_cost";
	public final static Integer SRID = LogicConstants.getInt("SRID", 4326);

	public enum funcion {
		SIMPLE, SHOOTING_STAR
	};

	public RoutingHome() {
		super(Routing.class);
	}

	@Override
	public Routing get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
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
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	private List<Long> shortest_path_shooting_star(final Long origin,
			final Long goal) {
		final List<Long> lista = new ArrayList<Long>();
		try {
			Session currentSession = getSession();
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
	 * Devuelve la lista de num calles que encajan con el patr�n
	 * 
	 * @param pattern
	 * @param num
	 * @return list of streets
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	public List<String> find(final String pattern, final Integer num) {
		List<String> lista = new ArrayList<String>();
		try {
			lista = getSession().createCriteria(Routing.class).setProjection(
					Projections.distinct(Projections.property("name")))
					.setMaxResults(num)
					.add(Restrictions.ilike("name", pattern)).addOrder(
							Order.asc("name")).list();

		} catch (Exception e) {
			log.error("Error al buscar las calles :" + pattern, e);
		}

		return lista;
	}

	/**
	 * Devuelve la primera calle que encuentre con este nombre:
	 * 
	 * @param pattern
	 * @param num
	 * @return list of streets
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class)
	public Routing find(final String calle) {
		Routing res = null;
		try {
			res = (Routing) getSession().createCriteria(Routing.class).add(
					Restrictions.eq("name", calle)).add(
					Restrictions.isNotNull("geometria")).addOrder(
					Order.asc("id")).setMaxResults(1).uniqueResult();

		} catch (Exception e) {
			log.error("Error al buscar las calles :" + calle, e);
		}

		return res;
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
			Session currentSession = getSession();
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
			log.error("No se pudo calcular la ruta", e);
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

				Session currentSession = getSession();
				final Criteria criteria = currentSession.createCriteria(
						Routing.class).add(Restrictions.in("id", ids))
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
						log.error("Devuelto alto extra�o: " + m);
				}

				resultado = new MultiLineString(lineStrings
						.toArray(new LineString[0]), new GeometryFactory());

				resultado.setSRID(4326);

				if (log.isTraceEnabled())
					log.trace("Resultado: " + resultado);
			}
		} catch (Throwable t) {
			log.error("Error al calcular la ruta", t);
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
			Session currentSession = getSession();
			String point = "Start";
			if (end) {
				point = "End";
			}

			Type geometryType = new CustomType(GeometryUserType.class, null);
			Query q = currentSession.createQuery("select " + id + " from "
					+ table
					+ " order by ST_Distance(ST_SETSRID(ST_POINT(ST_X(ST_"
					+ point + "Point(" + the_geom + ")),ST_Y(ST_" + point
					+ "Point(" + the_geom + "))), " + SRID + ")"
					+ ",ST_SETSRID(?, " + SRID + ")) asc");
			q.setParameter(0, p, geometryType);
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

}
