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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.Rol;
import es.emergya.consultas.FlotaConsultas;

@Repository("rolHome")
public class RolHome extends GenericDaoHibernate<Rol, Long> {

	public RolHome() {
		super(Rol.class);
	}

	@Override
	public Rol get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Integer getTotal() {
		Integer res = new Integer(-1);
		Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Rol.class)
				.setProjection(Projections.rowCount());
		Integer count = (Integer) criteria.uniqueResult();
		res = count.intValue();

		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<String> getAllString() {
		Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Rol.class)
				.setProjection(
						Projections.distinct(Projections.property("nombre")))
				.addOrder(Order.asc("nombre")).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Rol> getAll() {
		Session currentSession = getSession();
		currentSession.clear();
		return currentSession.createCriteria(Rol.class).addOrder(
				Order.asc("nombre")).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Rol> getAll(String order, boolean asc) {
		List<Rol> res = new ArrayList<Rol>(0);
		Session currentSession = getSession();
		currentSession.clear();
		if (asc)
			res = currentSession.createCriteria(Rol.class).addOrder(
					Order.asc(order)).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY).list();
		else
			res = currentSession.createCriteria(Rol.class).addOrder(
					Order.desc(order)).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY).list();

		return res;
	}

	public boolean alreadyExists(String nombreRol) {
		return find(nombreRol) != null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Rol find(String nombreRol) {
		Session currentSession = getSession();
		currentSession.clear();
		return (Rol) currentSession.createCriteria(Rol.class).add(
				Restrictions.ilike("nombre", nombreRol)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Rol> findByName(String nombreRol) {
		Session currentSession = getSession();
		currentSession.clear();
		return (List<Rol>) currentSession.createCriteria(Rol.class).add(
				Restrictions.ilike("nombre", nombreRol)).addOrder(
				Order.asc("nombre")).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean delete(Rol rol) {
		if (rol == null || rol.getId() == null)
			return true;

		Session currentSession = getSession();
		currentSession.clear();
		try {
			rol = this.get(rol.getId());
		} catch (Throwable t) {
			log
					.error(
							"Tiene toda la pinta de que estamos borrando un objeto ya borrado",
							t);
		}

		if (rol != null
				&& (rol.getUsuarios() == null || rol.getUsuarios().size() == 0)) {
			if (rol.getFlotas() != null) {
				for (Flota f : rol.getFlotas()) {
					f.getRoles().remove(rol);
					currentSession.saveOrUpdate(f);
				}
			}
			this.remove(rol.getId());
			return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean saveOrUpdate(final Rol rol) {

		Session currentSession = getSession();
		currentSession.clear();
		Rol entity = null;

		final Set<Flota> flotas = rol.getFlotas();
		if (rol.getId() != null && this.get(rol.getId()) != null)
			entity = this.get(rol.getId());

		if (entity == null)
			entity = rol;

		if (entity == null)
			return false;

		entity.setInfoAdicional(rol.getInfoAdicional());
		currentSession.saveOrUpdate(entity);

		if (entity != null && flotas != null) {
			for (Flota f : flotas) {
				try {
					Flota flota = (Flota) currentSession.get(Flota.class, f
							.getId());
					flota.getRoles().add(entity);
					entity.getFlotas().add(flota);
					currentSession.saveOrUpdate(flota);
				} catch (Throwable t) {
					log.error(t);
				}
			}
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void removeFlotas(Rol rol) {
		if (rol == null || rol.getId() == null)
			return;

		Session currentSession = getSession();
		currentSession.clear();
		Rol entity = this.get(rol.getId());

		if (entity == null)
			return;

		if (entity.getFlotas() != null)
			for (Flota f : entity.getFlotas()) {
				if (!rol.getFlotas().contains(f)) {
					log.debug("Borrando la flota " + f + " del rol " + entity);
					f.getRoles().remove(entity);
					currentSession.saveOrUpdate(f);
				} else
					log.debug("La flota " + f + " permanece en el rol "
							+ entity);
			}
		entity.setFlotas(new HashSet<Flota>());
		currentSession.saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Flota> getAsigned(Rol r) {
		if (r == null || r.getId() == null)
			return new ArrayList<Flota>(0);
		log.debug("getAsigned(" + r.getId() + ")");
		Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Flota.class).add(
				Restrictions.eq("habilitada", true)).createCriteria("roles")
				.add(Restrictions.in("id", new Long[] { r.getId() }));
		return (List<Flota>) criteria.setResultTransformer(
				CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	public Set<Flota> getDisponibles(Rol r) {
		Set<Flota> res = new HashSet<Flota>();
		Flota f = new Flota();
		f.setHabilitada(true);
		res.addAll(FlotaConsultas.getByExample(f));
		try {
			res.removeAll(getAsigned(r));
		} catch (Throwable t1) {
			log.error(t1, t1);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Rol.class)
					.setProjection(Projections.max("updatedAt"));
			res.setTime((Date) criteria.uniqueResult());
		} catch (Throwable t) {
			log.error(t, t);
			return null;
		}
		return res;
	}
}
