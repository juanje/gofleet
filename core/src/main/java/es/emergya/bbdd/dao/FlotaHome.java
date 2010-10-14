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
package es.emergya.bbdd.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.emory.mathcs.backport.java.util.Collections;
import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.bean.Rol;
import es.emergya.utils.LogicConstants;

@Repository("FlotaHome")
public class FlotaHome extends GenericDaoHibernate<Flota, Long> {

	public FlotaHome() {
		super(Flota.class);
	}

	@Override
	public Flota get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe (" + id + ")");
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Integer getTotal() {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Flota.class).add(
					Restrictions.eq("habilitada", true)).setProjection(
					Projections.rowCount());
			Integer count = (Integer) criteria.uniqueResult();
			return count.intValue();
		} catch (Throwable t) {
			log.error(t, t);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Flota> getByFilter(Flota f) {
		List<Flota> res = new ArrayList<Flota>(0);
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Flota.class).add(
					Restrictions.eq("habilitada", true));

			if (f.getInfoAdicional() != null)
				criteria = criteria.add(Restrictions.ilike("infoAdicional",
						LogicConstants.getGenericString(f.getInfoAdicional())));
			if (f.getNombre() != null)
				criteria = criteria.add(Restrictions.ilike("nombre",
						LogicConstants.getGenericString(f.getNombre())));
			if (f.getHabilitada() != null)
				criteria = criteria.add(Restrictions.eq("habilitada", f
						.getHabilitada()));
			if (f.getJuegoIconos() != null)
				criteria = criteria.add(Restrictions.ilike("juegoIconos",
						LogicConstants.getGenericString(f.getJuegoIconos())));
			res = criteria.addOrder(Order.asc("nombre")).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY).list();

			for (Flota fl : res)
				if (fl != null) {
					if (fl.getRoles() != null)
						for (Rol r : fl.getRoles())
							r.getId();
				}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean saveOrUpdate(Flota f) {
		boolean res = false;
		if (f == null)
			return res;
		try {
			log.debug("Saving " + f);
			final Set<Rol> roles = f.getRoles();
			final Set<Recurso> recurso = f.getRecurso();
			removeRecursos(f);
			removeRoles(f);
			Session currentSession = getSession();
			currentSession.clear();

			Flota entity = null;

			if (f.getId() != null && this.get(f.getId()) != null)
				entity = (Flota) currentSession.merge(f);
			else
				entity = f;
			if (recurso != null)
				for (Recurso r : recurso) {
					r = (Recurso) currentSession.get(Recurso.class, r.getId());
					if (r != null) {
						r.setFlotas(entity);
						currentSession.saveOrUpdate(r);
					}
				}

			if (roles != null)
				for (Rol r : roles) {
					Rol rentity = (Rol) currentSession
							.get(Rol.class, r.getId());
					if (rentity != null) {
						rentity.getFlotas().add(entity);
						currentSession.saveOrUpdate(rentity);
					}
				}

			currentSession.saveOrUpdate(entity);
		} catch (Throwable t) {
			log.error(t, t);
			return false;
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	private void removeRecursos(Flota p) {
		try {
			if (p != null && p.getId() != null) {
				Session currentSession = getSession();
				currentSession.clear();
				p = this.get(p.getId());
				if (p != null && p.getRecurso() != null) {
					for (Recurso r : p.getRecurso()) {
						r = (Recurso) currentSession.get(Recurso.class, r
								.getId());
						if (r != null) {
							r.setFlotas(null);
							currentSession.saveOrUpdate(r);
						}
					}
				}
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	private void removeRoles(Flota f) {
		try {
			if (f != null && f.getId() != null) {
				Session currentSession = getSession();
				currentSession.clear();

				f = this.get(f.getId());

				final Set<Rol> roles = Collections
						.unmodifiableSet(f.getRoles());
				if (f != null && roles != null)
					for (Rol r : roles) {
						r = (Rol) currentSession.get(Rol.class, r.getId());
						if (r != null && r.getFlotas() != null) {
							final Set<Flota> flotas = Collections
									.unmodifiableSet(r.getFlotas());
							List<Flota> aBorrar = new LinkedList<Flota>();
							for (Flota fl : flotas)
								if (fl.getId().equals(f.getId()))
									aBorrar.add(fl);
							for (Flota fl : aBorrar)
								r.getFlotas().remove(fl);
							currentSession.saveOrUpdate(r);
						}
					}
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean delete(Flota p) {
		try {
			if (p.getId() == null || (p = this.get(p.getId())) == null)
				return true;

			if (!p.getRecurso().isEmpty())
				return false;

			removeRoles(p);

			this.remove(p.getId());

			return true;
		} catch (Throwable t) {
			log.error(t, t);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<String> getAllIcons() {
		Session currentSession = getSession();
		currentSession.clear();
		return (List<String>) currentSession.createCriteria(Flota.class)
				.setProjection(Projections.property("juegoIconos"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<String> getAllNames() {
		Session currentSession = getSession();
		currentSession.clear();
		return (List<String>) currentSession.createCriteria(Flota.class)
				.setProjection(Projections.property("nombre")).addOrder(
						Order.asc("nombre")).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
	public Flota find(Long id) {
		Session currentSession = getSession();
		currentSession.clear();
		final Flota uniqueResult = (Flota) currentSession.createCriteria(
				Flota.class).add(Restrictions.eq("id", id)).uniqueResult();
		if (uniqueResult != null) {
			uniqueResult.getJuegoIconos();
		}
		return uniqueResult;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<String> getAllNamesHabilitadas() {
		Session currentSession = getSession();
		currentSession.clear();
		return (List<String>) currentSession.createCriteria(Flota.class).add(
				Restrictions.eq("habilitada", Boolean.TRUE)).setProjection(
				Projections.property("nombre")).addOrder(Order.asc("nombre"))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Flota> getAllHabilitadas() {
		Session currentSession = getSession();
		currentSession.clear();
		return (List<Flota>) currentSession.createCriteria(Flota.class).add(
				Restrictions.eq("habilitada", Boolean.TRUE)).addOrder(
				Order.asc("nombre")).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	public boolean existe(String original) {
		boolean res = true;
		try {
			res = (find(original) != null);
		} catch (Throwable t) {
			log.error(t, t);
			res = false;
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Flota find(String nombre) {
		Flota res = null;
		try {
			Session currentSession = getSession();
			currentSession.clear();
			res = (Flota) currentSession.createCriteria(Flota.class).add(
					Restrictions.eq("nombre", nombre)).setMaxResults(1)
					.uniqueResult();
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Flota.class)
					.setProjection(Projections.max("updatedAt"));
			res.setTime((Date) criteria.uniqueResult());
		} catch (NullPointerException t) {
			log.error("No hay datos en la tabla.");
			return null;
		} catch (Throwable t) {
			log.error(t, t);
			return null;
		}
		return res;
	}
}
