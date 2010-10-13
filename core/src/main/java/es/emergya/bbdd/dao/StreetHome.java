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
 */package es.emergya.bbdd.dao;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Street;

@Repository("streetHome")
public class StreetHome extends GenericDaoHibernate<Street, Long> {

	private static final Log LOG = LogFactory.getLog(StreetHome.class);

	public StreetHome() {
		super(Street.class);
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Street get(Integer id) {
		return (Street) getSession().get(Street.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Collection<Street> getAllMatching(String expression) {
		Collection<Street> res = new LinkedList<Street>();
		Session currentSession = getSession();
		currentSession.flush();
		Criteria criteria = currentSession.createCriteria(Street.class)
				.add(Restrictions.ilike("nombreviaine", expression))
				.addOrder(Order.asc("nombreviaine"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		res = criteria.list();
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Street> getStreet(String nombre) {
		List<Street> res = new LinkedList<Street>();
		Session currentSession = getSession();
		currentSession.flush();
		Criteria criteria = currentSession.createCriteria(Street.class)
				.add(Restrictions.ilike("nombreviaine", nombre))
				.addOrder(Order.asc("nombreviaine"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		res = criteria.list();
		return res;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean saveOrUpdate(Street s) {
		boolean res = true;
		if (s == null || s.getId() == null) {
			return res;
		}
		Session currentSession = getSession();
		currentSession.clear();
		Street entity = get(s.getId());
		if (entity != null) {
			entity.setCentroid(s.getCentroid());
			currentSession.saveOrUpdate(entity);
		}

		return res;
	}

	/**
	 * Devuelve la calle que tenga el codigo INE pasado como parámetro.
	 * 
	 * @param codigoine
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Street getByCodigoIne(Integer codigoine) {
		Street res = null;
		Session currentSession = getSession();
		currentSession.flush();
		Criteria criteria = currentSession.createCriteria(Street.class).add(
				Restrictions.eq("codigoine", codigoine));
		res = (Street) criteria.uniqueResult();
		return res;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Street save(Street s) {
		return (Street) getSession().merge(s);
	}
}
