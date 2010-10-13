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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.EstadoRecurso;
import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.consultas.RecursoConsultas;
import es.emergya.utils.LogicConstants;

@Repository("patrullaHome")
public class PatrullaHome extends GenericDaoHibernate<Patrulla, Long> {

	public PatrullaHome() {
		super(Patrulla.class);
	}

	@Override
	public Patrulla get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe " + t);
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Integer getTotal() {
		try {
			org.hibernate.Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Patrulla.class)
					.setProjection(Projections.rowCount());
			Integer count = (Integer) criteria.uniqueResult();
			return count.intValue();
		} catch (Throwable t) {
			log.error("Error en getTotal()", t);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Patrulla> getByFilter(Patrulla p) {
		List<Patrulla> res = new ArrayList<Patrulla>(0);

		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Patrulla.class);

			if (p.getInfoAdicional() != null)
				criteria = criteria.add(Restrictions.ilike("infoAdicional",
						LogicConstants.getGenericString(p.getInfoAdicional())));
			if (p.getNombre() != null)
				criteria = criteria.add(Restrictions.ilike("nombre",
						LogicConstants.getGenericString(p.getNombre())));

			res = criteria.addOrder(Order.asc("nombre"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Patrulla> getAll() {
		List<Patrulla> res = new ArrayList<Patrulla>(0);
		try {
			Session currentSession = getSession();
			currentSession.clear();
			res = currentSession.createCriteria(Patrulla.class)
					.addOrder(Order.asc("nombre"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (Throwable e) {
			log.error(e, e);
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Patrulla> getAllTests() {
		List<Patrulla> res = new ArrayList<Patrulla>(0);
		try {
			Session currentSession = getSession();
			currentSession.clear();
			res = currentSession.createCriteria(Patrulla.class)
					.addOrder(Order.asc("nombre"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

			for (Patrulla p : res)
				for (Recurso r : p.getRecursos())
					r.getId();

		} catch (Throwable e) {
			log.error(e, e);
		}

		return res;
	}

	public Boolean alreadyExists(String p) {
		Boolean res = false;
		try {
			res = (find(p) != null);
		} catch (Throwable t1) {
			log.error(t1, t1);
			res = false;
		}

		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Patrulla find(String p) {
		Patrulla res = null;

		try {
			Session currentSession = getSession();
			currentSession.clear();
			res = (Patrulla) currentSession.createCriteria(Patrulla.class)
					.add(Restrictions.ilike("nombre", p)).setMaxResults(1)
					.uniqueResult();
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean saveOrUpdate(Patrulla p) {
		boolean res = false;
		if (p == null)
			return res;
		log.debug("Saving " + p);
		try {
			Session currentSession = getSession();
			currentSession.clear();

			Patrulla entity = null;

			if (p.getId() != null && this.get(p.getId()) != null)
				entity = (Patrulla) currentSession.merge(p);
			else
				entity = p;

			if (p.getRecursos() != null)
				for (Recurso r : p.getRecursos()) {
					if (r.getId() != null) {
						r = (Recurso) currentSession.get(Recurso.class,
								r.getId());
						r.setPatrullas(entity);
						currentSession.saveOrUpdate(r);
					}
				}

			currentSession.saveOrUpdate(entity);

			log.debug(p + " saved");
			return true;

		} catch (Throwable t) {
			log.error(t, t);
			return false;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void removeRecursos(Patrulla p) {
		if (p == null || p.getId() == null)
			return;

		try {
			Session currentSession = getSession();
			currentSession.clear();

			p = this.get(p.getId());
			if (p != null && p.getRecursos() != null) {
				for (Recurso r : p.getRecursos()) {
					try {
						r.setPatrullas(null);
						currentSession.saveOrUpdate(r);
					} catch (Throwable t) {
						log.error("No pude eliminar el recurso de la patrulla",
								t);
					}
				}
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean delete(Patrulla p) {
		try {
			if (p.getId() != null && this.get(p.getId()) != null)
				if (this.get(p.getId()).getRecursos().size() > 0)
					throw new Exception("La patrulla tiene recursos asignados.");
			this.remove(p.getId());
		} catch (Throwable t) {
			log.error("Error al borrar patrulla", t);
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<String> getAllNames() {
		List<String> icons = new ArrayList<String>(0);
		try {
			Session currentSession = getSession();
			currentSession.clear();
			icons = (List<String>) currentSession
					.createCriteria(Patrulla.class)
					.setProjection(Projections.property("nombre"))
					.addOrder(Order.asc("nombre"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		} catch (Throwable t) {
			log.error(t, t);
		}
		return icons;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Patrulla.class)
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

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
	public Patrulla find(Long id) {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			return (Patrulla) currentSession.createCriteria(Patrulla.class)
					.add(Restrictions.eq("id", id)).uniqueResult();
		} catch (Throwable t) {
			log.error(t, t);
		}
		return null;
	}

	public Patrulla[] getAsigned(Incidencia i) {
		Recurso[] recursos = RecursoConsultas.getAsigned(i);
		LinkedList<Patrulla> res = new LinkedList<Patrulla>();
		for (Recurso rec : recursos)
			if (!res.contains(rec.getPatrullas()))
				res.add(rec.getPatrullas());
		return res.toArray(new Patrulla[0]);
	}

	/**
	 * Busca una patrulla a partir de su nombre (case insensitive)
	 * 
	 * @param nombre
	 *            Nombre de la patrulla a buscar
	 * @return La patrulla correspondiente, o null en caso de no existir
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Patrulla findPatrullaByNombre(String nombre) {
		Patrulla resultado;
		if (nombre != null && !nombre.equals("")) {
			Criteria crit = getSession().createCriteria(Patrulla.class).add(
					Restrictions.like("nombre", nombre));
			resultado = (Patrulla) crit.uniqueResult();

		} else {
			resultado = null;
		}

		return resultado;

	}

	/**
	 * Inserta una patrulla
	 * 
	 * @param patrulla
	 *            Patrulla a insertar
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void savePatrulla(Patrulla patrulla) {
		getSession().saveOrUpdate(patrulla);

	}

	/**
	 * Actualiza los recursos de las patrullas que NO se encuentren en el array
	 * 
	 * @param idsPatrullas
	 *            Array de ids de patrullas
	 * @param estado
	 *            Estado a establecer
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void updateEstadosRecursos(Long[] idsPatrullas, String estado) {

		if (idsPatrullas != null && idsPatrullas.length > 0) {
			Criteria crit = getSession().createCriteria(Patrulla.class).add(
					Restrictions.not(Restrictions.in("id", idsPatrullas)));
			List<Patrulla> resultado = crit.list();
			EstadoRecurso estadoObj = getEstadoRecursoByIdentificador(estado);
			if (estado != null) {
				for (Patrulla pat : resultado) {
					log.debug("Actualizamos los recursos de la patrulla "
							+ pat.getId());
					List<Recurso> recs = getRecursosByPatrulla(pat);
					log.debug("Recursos:" + recs.size());
					if (recs != null && recs.size() > 0) {
						for (Recurso rec : recs) {
							log.debug("Actualizamos el recurso " + rec.getId()
									+ " a " + estado);
							rec.setEstadoEurocop(estadoObj);
							getSession().update(rec);
						}
					}
				}
			} else
				log.error("El estado " + estado
						+ " no se encuentra en la base de datos");

		}

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Recurso> getRecursosByPatrulla(Patrulla patrulla) {
		Criteria crit = getSession().createCriteria(Recurso.class)
				.add(Restrictions.eq("patrullas", patrulla))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return crit.list();
	}

	/**
	 * Obtiene el objeto EstadoRecurso que se corresponda con el identificador
	 * 
	 * @param identificador
	 *            Identificador del EstadoRecurso a obtener
	 * @return EstadoRecurso correspondiente, o null en caso de no encontrarlo
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public EstadoRecurso getEstadoRecursoByIdentificador(String identificador) {
		EstadoRecurso resultado;
		if (identificador != null && !identificador.equals("")) {
			Criteria crit = getSession().createCriteria(EstadoRecurso.class)
					.add(Restrictions.like("identificador", identificador));
			resultado = (EstadoRecurso) crit.uniqueResult();

		} else {
			resultado = null;
		}

		return resultado;
	}

}
