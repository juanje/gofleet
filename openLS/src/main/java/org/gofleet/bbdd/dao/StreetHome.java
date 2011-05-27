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
package org.gofleet.bbdd.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.gofleet.bbdd.beans.Street;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

@Repository("streetHome")
public class StreetHome extends GenericDaoHibernate<Street, Long> {

	public StreetHome() {
		super(Street.class);
	}

	private static final Log log = LogFactory.getLog(StreetHome.class);
	private static Random random = new Random();

	@Override
	@Transactional(readOnly = true, timeout = 50)
	public Street get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	/**
	 * Random, for test purposes.
	 * 
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Street getRandom() {
		try {

			Criteria q = getSessionFactory().getCurrentSession()
					.createCriteria(Street.class).setMaxResults(1)
					.add(Restrictions.isNotNull("the_geom"));

			if (random.nextBoolean()) {
				Order order = null;
				String property = "gid";
				if (random.nextBoolean()) {
					property = "name";
				} else if (random.nextBoolean()) {
					property = "the_geom";
				}
				if (random.nextBoolean()) {
					order = Order.asc(property);
				} else {
					order = Order.desc(property);
				}
				q.addOrder(order);
			}

			return (Street) q.uniqueResult();
		} catch (Throwable t) {
			log.error("Error al extraer un objeto random", t);
			return null;
		}
	}

	/**
	 * Gets the first maxResults which are like name.
	 * 
	 * @param name
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Street> getByName(String name, int maxResults) {
		try {

			if (name == null)
				name = "";
			if (name.indexOf("%") < 0)
				name = name + "%";

			return (List<Street>) getSessionFactory().getCurrentSession()
					.createCriteria(Street.class)
					.add(Restrictions.ilike("name", name))
					.add(Restrictions.isNotNull("the_geom"))
					.addOrder(Order.desc("name")).setMaxResults(maxResults)
					.list();
		} catch (Throwable t) {
			log.error("Error al extraer un objeto random", t);
			return null;
		}
	}

	/**
	 * Default maxResults = 20.
	 * 
	 * @param name
	 * @return
	 */
	public List<Street> getByName(String name) {
		return getByName(name, 20);
	}

	/**
	 * Gets the first maxResults which are like name.
	 * 
	 * @param name
	 * @param maxResults
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Geometry getExactlyUnifiedGeometry(String name) {
		try {
			MultiLineString res = null;
			if (name != null) {
				List<MultiLineString> ways = getSessionFactory()
						.getCurrentSession().createCriteria(Street.class)
						.setProjection(Projections.property("the_geom"))
						.add(Restrictions.ilike("name", name.trim() + "%"))
						.add(Restrictions.isNotNull("the_geom"))
						.addOrder(Order.desc("name")).list();

				List<LineString> linestrings = new LinkedList<LineString>();

				Integer srid = null;

				for (MultiLineString way : ways) {
					for (int i = 0; i < way.getNumGeometries(); i++) {
						LineString geometryN = (LineString) way.getGeometryN(i);
						linestrings.add(geometryN);
						if (srid == null)
							srid = geometryN.getSRID();
						else if (srid != geometryN.getSRID())
							throw new Exception(
									"Geometries of different SRID involved on this operation");
					}
				}

				res = new MultiLineString(
						linestrings.toArray(new LineString[0]),
						new GeometryFactory());

				if (srid == null)
					throw new NullPointerException("Geometry without SRID");

				res.setSRID(srid);
			}
			return res;
		} catch (Throwable t) {
			log.error("Error al extraer un objeto random", t);
			return null;
		}
	}
}
