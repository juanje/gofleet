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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.CategoriaIncidencia;
import es.emergya.bbdd.bean.EstadoIncidencia;
import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.consultas.UsuarioConsultas;
import es.emergya.utils.LogicConstants;

@Repository("incidenciaHome")
public class IncidenciaHome extends GenericDaoHibernate<Incidencia, Long> {

	public IncidenciaHome() {
		super(Incidencia.class);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
	@Override
	public Incidencia get(Long id) {
		try {
			final Incidencia i = super.get(id);
			if (i != null) {
				if (i.getCreador() != null)
					i.getCreador().getId();
				if (i.getEstado() != null)
					i.getEstado().getId();
				if (i.getCategoria() != null)
					i.getCategoria().getId();
				if (i.getRecursos() != null)
					for (Recurso r : i.getRecursos())
						if (r != null)
							r.getId();
			}
			return i;
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
	public Integer getTotal() {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
					.setProjection(Projections.rowCount());
			return (Integer) criteria.uniqueResult();
		} catch (Throwable t) {
			log.error(t, t);
			return -1;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean saveOrUpdate(Incidencia p) {
		if (p == null)
			return false;

		Incidencia entity = null;
		try {
			Session currentSession = getSession();
			if (p.getId() != null && this.get(p.getId()) != null)
				entity = get(p.getId());

			if (entity == null)
				entity = p;
			else {
				if (p.getCategoria() != null)
					try {
						entity.setCategoria((CategoriaIncidencia) super
								.getSession().get(CategoriaIncidencia.class,
										p.getCategoria().getId()));
					} catch (Throwable t) {
						log.error("Categoria desconocida", t);
					}
				else
					entity.setCategoria(null);
				if (p.getCreador() != null)
					entity.setCreador(UsuarioConsultas.find(p.getCreador()
							.getNombreUsuario()));
				else
					entity.setCreador(null);
				entity.setDescripcion(p.getDescripcion());
				if (p.getEstado() != null)
					try {
						entity.setEstado((EstadoIncidencia) super.getSession()
								.get(EstadoIncidencia.class,
										p.getEstado().getId()));
					} catch (Throwable t) {
						log.error("Estado desconocido", t);
					}
				else
					entity.setEstado(null);
				entity.setFechaCierre(p.getFechaCierre());
				entity.setFechaCreacion(p.getFechaCreacion());
				entity.setGeometria(p.getGeometria());
				entity.setPrioridad(p.getPrioridad());
				entity.setTitulo(p.getTitulo());
			}
			currentSession.saveOrUpdate(entity);
		} catch (Throwable t) {
			log.error("Error al guardar una incidencia: " + p, t);
			return false;
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean delete(Incidencia r) {
		if (r == null || r.getId() == null)
			return false;
		try {
			this.remove(r.getId());
		} catch (Throwable t) {
			log.error(t, t);
			return false;
		}
		return true;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
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

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Incidencia> getAll() {
		List<Incidencia> res = new LinkedList<Incidencia>();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = (List<Incidencia>) criteria.list();
			for (Incidencia i : res) {
				if (i.getCreador() != null)
					i.getCreador().getId();
				if (i.getEstado() != null)
					i.getEstado().getId();
				if (i.getCategoria() != null)
					i.getCategoria().getId();
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Incidencia> getOpened() {
		List<Incidencia> res = new LinkedList<Incidencia>();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
					.createCriteria("estado").add(Restrictions.ne("id", 3l))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = (List<Incidencia>) criteria.list();

			for (Incidencia i : res)
				if (i != null) {
					if (i.getCreador() != null)
						i.getCreador().getId();
					if (i.getEstado() != null)
						i.getEstado().getId();
					if (i.getCategoria() != null)
						i.getCategoria().getId();
				}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Incidencia find(String identificador) {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
					.add(Restrictions.eq("referenciaHumana", identificador))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setMaxResults(1);
			final Incidencia uniqueResult = (Incidencia) criteria
					.uniqueResult();
			return uniqueResult;
		} catch (Throwable t) {
			log.error(t, t);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Incidencia> getByExample(Incidencia f) {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Incidencia.class)
					.addOrder(Order.asc("titulo"));

			// titulo
			if (f.getTitulo() != null && f.getTitulo().trim().length() > 0) {
				criteria.add(Restrictions.ilike("titulo",
						LogicConstants.getGenericString(f.getTitulo())));
			}

			// prioridad
			if (f.getPrioridad() != null) {
				criteria.add(Restrictions.eq("prioridad", f.getPrioridad()));
			}

			// categoria
			if (f.getCategoria() != null) {
				criteria.createAlias("categoria", "cat").add(
						Restrictions.eq("cat.identificador", f.getCategoria()
								.getIdentificador()));
			}

			// estado
			if (f.getEstado() != null) {
				criteria.createAlias("estado", "est").add(
						Restrictions.eq("est.identificador", f.getEstado()
								.getIdentificador()));
			} else {
				criteria.createAlias("estado", "est").add(
						Restrictions.ne("est.id", 3l));
			}

			List<Incidencia> res = new LinkedList<Incidencia>();
			res = criteria.list();
			for (Incidencia i : res)
				if (i != null) {
					if (i.getCreador() != null)
						i.getCreador().getId();
					if (i.getEstado() != null)
						i.getEstado().getId();
					if (i.getCategoria() != null)
						i.getCategoria().getId();
				}
			return res;
		} catch (Throwable t) {
			log.error("Error extrayendo las categorias de las incidencias", t);
		}
		return new LinkedList<Incidencia>();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Object> getCategorias() {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(
					CategoriaIncidencia.class).addOrder(
					Order.asc("identificador"));
			return criteria.list();
		} catch (Throwable t) {
			log.error("Error extrayendo las categorias de las incidencias", t);
		}
		return new LinkedList<Object>();
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Object> getStatuses() {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(
					EstadoIncidencia.class)
					.addOrder(Order.asc("identificador"));
			return criteria.list();
		} catch (Throwable t) {
			log.error("Error extrayendo los estados de las incidencias", t);
		}
		return new LinkedList<Object>();
	}

	/**
	 * Devuelve todas las incidencias que estuvieron abiertas en el intervalo
	 * pasado y su posición coincide con alguna de las zonas indicadas. Si no se
	 * pasan zonas, la posición no se utilizará como criterio para obtener la
	 * lista de incidencias.
	 * 
	 * @param nombreUsuario
	 *            el nombre de usuario del usuario que realiza la consulta.
	 * @param fechaInicio
	 *            el instante inicial a usar en el filtro.
	 * @param fechaFinal
	 *            el instante final a usar en el filtro.
	 * @param zonas
	 *            Lista de zonas en las que deben estar las incidencias.
	 * @return La lista de zonas que estuvieron abiertas en algún momento del
	 *         periodo en alguna de las zonas pasadas.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public List<Incidencia> getIncidenciasEnPeriodo(String nombreUsuario,
			Calendar fechaInicio, Calendar fechaFinal) {
		Date inicio = null;
		Date fin = null;

		if (fechaInicio != null) {
			inicio = fechaInicio.getTime();
		} else {
			return new ArrayList<Incidencia>(0);
		}
		if (fechaFinal != null) {
			fin = fechaFinal.getTime();
		} else {
			return new ArrayList<Incidencia>(0);
		}

		Criteria c = getSession()
				.createCriteria(Incidencia.class)
				.addOrder(Order.asc("referenciaHumana"))
				.setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.createAlias("street", "st", Criteria.LEFT_JOIN)
				.createAlias("portal", "pt", Criteria.LEFT_JOIN);
		if (fin != null) {
			c = c.add(Restrictions.le("fechaCreacion", fin));
		}
		if (inicio != null) {
			c = c.add(Restrictions.or(Restrictions.ge("fechaCierre", inicio),
					Property.forName("fechaCierre").isNull()));
		}

		log.trace("Criteria final: " + c);

		return c.list();
	}

	/**
	 * Busca una incidencia a partir de su referencia interna.
	 * 
	 * @param referenciaIngerna
	 *            código interno proporcionado por Eurocop para la incidencia.
	 * @return la incidencia con la referenciaInterna pasada o <code>null</code>
	 *         si no existe en la base de datos.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Incidencia findIncidenciaByReferenciaInterna(Long referenciaInterna) {
		Incidencia resultado;
		if (referenciaInterna != null) {
			Criteria crit = getSession().createCriteria(Incidencia.class).add(
					Restrictions.eq("referenciaInterna", referenciaInterna));
			resultado = (Incidencia) crit.uniqueResult();

		} else {
			resultado = null;
		}

		return resultado;

	}

	/**
	 * Almacena la incidencia en la base de datos.
	 * 
	 * @param incidencia
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public Incidencia saveIncidencia(Incidencia incidencia) {
		getSession().save(incidencia);
		return incidencia;
	}

	/**
	 * Actualiza los estados de las incidencias que NO se encuentren en el array
	 * y cuyo estado no es ya el pasado como parámetro.
	 * 
	 * @param idsIncidencias
	 *            Array de ids de incidencias
	 * @param estado
	 *            Estado a establecer
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void updateEstadosIncidencias(Long[] idsIncidencias, String estado) {

		if (idsIncidencias != null && idsIncidencias.length > 0) {
			EstadoIncidencia estadoObj = getEstadoIncidenciaByIdentificador(estado);
			if (estado != null) {
				Criteria crit = getSession()
						.createCriteria(Incidencia.class)
						.add(Restrictions.not(Restrictions.in("id",
								idsIncidencias)))
						.add(Restrictions.ne("estado", estadoObj));

				List<Incidencia> resultado = crit.list();
				for (Incidencia inc : resultado) {
					if (log.isTraceEnabled()) {
						log.trace("Actualizamos el estado de la incidencia "
								+ inc.getId() + " a " + estado);
					}
					inc.setEstado(estadoObj);
					getSession().update(inc);
				}
			} else {
				log.error("El estado " + estado
						+ " no se encuentra en la base de datos");
			}
		}
	}

	/**
	 * Obtiene el objeto EstadoIncidencia que se corresponda con el
	 * identificador
	 * 
	 * @param identificador
	 *            Identificador del EstadoIncidencia a obtenet
	 * @return EstadoIncidencia correspondiente, o null en caso de no
	 *         encontrarlo
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public EstadoIncidencia getEstadoIncidenciaByIdentificador(
			String identificador) {
		EstadoIncidencia resultado;
		if (identificador != null && !identificador.equals("")) {
			Criteria crit = getSession().createCriteria(EstadoIncidencia.class)
					.add(Restrictions.like("identificador", identificador));
			resultado = (EstadoIncidencia) crit.uniqueResult();

		} else {
			resultado = null;
		}

		return resultado;
	}

}
