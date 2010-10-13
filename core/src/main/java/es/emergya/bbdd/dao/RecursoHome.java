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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.DistanceOrder;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import es.emergya.bbdd.bean.EstadoRecurso;
import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.bean.notmapped.RecursoBean;
import es.emergya.utils.LogicConstants;
import es.emergya.utils.MyBeanFactory;

@Repository("recursoHome")
public class RecursoHome extends GenericDaoHibernate<Recurso, Long> {

	private static final int LONGITUD_ISSI = LogicConstants.getInt(
			"LONGITUD_ISSI", 8);
	private final Recurso filter;

	public RecursoHome() {
		super(Recurso.class);
		filter = new Recurso();
		filter.setHabilitado(true);
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Recurso get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso get2(Long id) {
		try {
			Recurso uniqueResult = this.get(id);
			if (uniqueResult != null) {
				this.getSession().refresh(uniqueResult);
				if (uniqueResult.getPatrullas() != null) {
					uniqueResult.getPatrullas().getId();
				}
				if (uniqueResult.getFlotas() != null) {
					uniqueResult.getFlotas().getId();
				}
				if (uniqueResult.getEstadoEurocop() != null) {
					uniqueResult.getEstadoEurocop().getId();
				}
				if (uniqueResult.getIncidencias() != null) {
					uniqueResult.getIncidencias().getId();
				}
			}
			return uniqueResult;
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Integer getTotal() {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.setProjection(Projections.rowCount());
			Integer count = (Integer) criteria.uniqueResult();
			return count.intValue();
		} catch (Throwable t) {
			log.error(t, t);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public Recurso[] getNotAsigned(Patrulla p) {
		Recurso[] res = new Recurso[0];
		try {
			if (p == null || p.getId() == null) {
				return getByFilter(filter).toArray(new Recurso[0]);
			}
			log.debug("getNotAsigned(" + p.getId() + ")");
			Session currentSession = getSession();
			currentSession.clear();
			Criterion rhs = Restrictions.isNull("patrullas");
			Criterion lhs = Restrictions.ne("patrullas",
					currentSession.load(Patrulla.class, p.getId()));
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.add(Restrictions.eq("habilitado", true))
					.add(Restrictions.or(lhs, rhs));
			criteria = criteria
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = ((List<Recurso>) criteria.list()).toArray(new Recurso[0]);

			for (Recurso r : res)
				try {
					r.getPatrullas().getId();
				} catch (Throwable t) {
				}

		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public Recurso[] getAsigned(Patrulla p) {
		Recurso[] res = new Recurso[0];
		if (p == null || p.getId() == null) {
			return res;
		}
		try {
			log.debug("getAsigned(" + p.getId() + ")");
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.createCriteria("patrullas")
					.add(Restrictions.eq("id", p.getId()));
			criteria = criteria
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = ((List<Recurso>) criteria.list()).toArray(new Recurso[0]);
			for (Recurso r : res)
				try {
					r.getPatrullas().getId();
				} catch (Throwable t) {
				}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso[] getNotAsigned(Flota p) {
		Recurso[] res = new Recurso[0];
		if (p == null || p.getId() == null) {
			return getByFilter(new Recurso()).toArray(new Recurso[0]);
		}
		try {
			log.debug("getNotAsigned(" + p.getId() + ")");
			Session currentSession = getSession();
			currentSession.clear();
			Criterion rhs = Restrictions.isNull("flotas");
			Criterion lhs = Restrictions.ne("flotas",
					currentSession.load(Patrulla.class, p.getId()));
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.add(Restrictions.or(lhs, rhs));
			criteria = criteria
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = ((List<Recurso>) criteria.list()).toArray(new Recurso[0]);

			for (Recurso r : res)
				try {
					r.getFlotas().getId();
				} catch (Throwable t) {
				}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso[] getAsigned(Flota p) {
		Recurso[] res = new Recurso[0];
		if (p == null || p.getId() == null) {
			return res;
		}
		try {
			log.debug("getAsigned(" + p.getId() + ")");
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.createCriteria("flotas")
					.add(Restrictions.eq("id", p.getId()));
			criteria = criteria
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			res = ((List<Recurso>) criteria.list()).toArray(new Recurso[0]);

			for (Recurso r : res)
				try {
					r.getFlotas().getId();
				} catch (Throwable t) {
				}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Recurso> getByFilter(Recurso p) {
		List<Recurso> res = new ArrayList<Recurso>(0);
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class);

			if (p.getInfoAdicional() != null) {
				criteria = criteria.add(Restrictions.ilike("infoAdicional",
						LogicConstants.getGenericString(p.getInfoAdicional())));
			}
			if (p.getNombre() != null) {
				criteria = criteria.add(Restrictions.ilike("nombre",
						LogicConstants.getGenericString(p.getNombre())));
			}

			if (p.getIdentificador() != null) {
				criteria = criteria.add(Restrictions.ilike("identificador",
						LogicConstants.getGenericString(p.getIdentificador())));
			}

			if (p.getHabilitado() != null) {
				criteria = criteria.add(Restrictions.eq("habilitado",
						p.getHabilitado()));
			}
			if (p.idpattern != null && p.idpattern.length() > 0) {
				criteria.add(Restrictions
						.sqlRestriction("lpad({alias}.dispositivo :: varchar, "
								+ LONGITUD_ISSI + ", '0') ilike '"
								+ LogicConstants.getGenericString(p.idpattern)
								+ "'"));
			}

			if (p.getEstadoEurocop() != null) {
				criteria = criteria.add(Restrictions.ilike("estadoEurocop",
						LogicConstants.getGenericString(p.getEstadoEurocop()
								.getIdentificador())));
			}

			if (p.getFlotas() != null) {
				criteria = criteria
						.add(Restrictions.eq("flotas", p.getFlotas()));
			}

			if (p.getPatrullas() != null) {
				criteria = criteria.add(Restrictions.eq("patrullas",
						p.getPatrullas()));
			}

			if (p.getTipo() != null) {
				criteria = criteria.add(Restrictions.ilike("tipo",
						LogicConstants.getGenericString(p.getTipoReal())));
			}

			log.trace(criteria);

			res = criteria.addOrder(Order.asc("nombre"))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

			for (Recurso uniqueResult : res) {
				if (uniqueResult != null) {
					if (uniqueResult.getPatrullas() != null) {
						uniqueResult.getPatrullas().getId();
					}
					if (uniqueResult.getFlotas() != null) {
						uniqueResult.getFlotas().getId();
					}
				}
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return res;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean saveOrUpdate(Recurso p) {
		boolean res = false;
		if (p == null) {
			return res;
		}
		try {
			Session currentSession = getSession();
			currentSession.clear();

			Recurso entity = null;

			if (p.getId() == null
					|| (p.getId() != null && this.get(p.getId()) == null)) {
				entity = p;
			} else {
				entity = (Recurso) currentSession.merge(p);
				if (p.getEstadoEurocop() != null
						&& p.getEstadoEurocop().getId() != null) {
					entity.setEstadoEurocop((EstadoRecurso) currentSession.get(
							EstadoRecurso.class, p.getEstadoEurocop().getId()));
				}
			}
			if (entity == null) {
				entity = p;
			}

			if (entity != null) {
				entity.setInfoAdicional(p.getInfoAdicional());
				entity.setNombre(p.getNombre());
			}

			Patrulla patrulla = null;
			if (p.getPatrullas() != null) {
				if (p.getPatrullas().getId() != null) {
					patrulla = (Patrulla) currentSession.load(Patrulla.class, p
							.getPatrullas().getId());
				} else {
					patrulla = p.getPatrullas();
				}
			}
			entity.setPatrullas(patrulla);

			if (entity.getEstadoEurocop() == null) {
				entity.setEstadoEurocop((EstadoRecurso) currentSession.get(
						EstadoRecurso.class, 1l));
			}

			entity.setIdentificador(entity.getNombre());
			currentSession.saveOrUpdate(entity);
		} catch (Throwable t) {
			log.error(t, t);
		}
		return true;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Recurso r) {
		if (r == null || r.getId() == null) {
			return false;
		}
		try {
			final Session currentSession = getSession();
			currentSession.clear();

			r = this.get(r.getId());
			if (r == null) {
				return true;
			}

			this.remove(r.getId());
		} catch (Throwable t) {
			log.error(t, t);
			return false;
		}
		return true;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
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

	public static String[] getTipos() {
		String[] res = new String[] { Recurso.PERSONA, Recurso.VEHICULO };
		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso getByIdentificador(String origen) {
		Session currentSession = getSession();
		try {
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.add(Restrictions.eq("identificador", origen));
			final Recurso uniqueResult = (Recurso) criteria.uniqueResult();
			if (uniqueResult != null) {
				if (uniqueResult.getPatrullas() != null) {
					uniqueResult.getPatrullas().getId();
				}
				if (uniqueResult.getFlotas() != null) {
					uniqueResult.getFlotas().getId();
				}
				if (uniqueResult.getEstadoEurocop() != null) {
					uniqueResult.getEstadoEurocop().getId();
				}
				if (uniqueResult.getIncidencias() != null) {
					uniqueResult.getIncidencias().getId();
				}
			}
			return uniqueResult;
		} catch (Throwable t) {
			log.error(t, t);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso[] getNearest(double x, double y, Integer num, Usuario u) {
		List<Recurso> res = new LinkedList<Recurso>();
		Session currentSession = getSession();
		currentSession.clear();
		Coordinate coordinate = new Coordinate(x, y);
		Point p = (new GeometryFactory()).createPoint(coordinate);
		p.setSRID(LogicConstants.SRID);

		try {

			final DistanceOrder order = DistanceOrder
					.des("historico1_.geom", p);

			// final Criteria criteria = currentSession.createCriteria(
			// Recurso.class).setResultTransformer(
			// Criteria.DISTINCT_ROOT_ENTITY).createAlias("historicoGps",
			// "historico").createCriteria("flotas").createCriteria(
			// "roles").createCriteria("usuarios").add(
			// Restrictions.eq("id", u.getId())).addOrder(order)
			// .setMaxResults(num);

			Query criteria = currentSession
					.createSQLQuery(
							"select this_.* from recursos this_ "
									+ "inner join flotas flota2_ on this_.flota_x_flota=flota2_.x_flota "
									+ "inner join ROLES_X_FLOTAS roles15_ on flota2_.x_flota=roles15_.X_FLOTA "
									+ "inner join roles rol3_ on roles15_.X_ROL=rol3_.x_rol "
									+ "inner join usuarios usuario4_ on rol3_.x_rol=usuario4_.fk_roles "
									+ "inner join historico_gps historico1_ on this_.fk_historico_gps=historico1_.x_historico "
									+ ((u != null && u.getId() != null) ? "where usuario4_.x_usuarios=:ID "
											+ " and historico1_.marca_temporal > :TIMEOUT "
											: "") + "order by "
									+ order.toString())
					.addEntity(Recurso.class)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setMaxResults(num);

			if (u != null && u.getId() != null) {
				criteria = criteria.setLong("ID", u.getId());
			}
			Calendar timeout = Calendar.getInstance();
			timeout.add(Calendar.MINUTE,
					-LogicConstants.getInt("AVL_TIMEOUT", 30));
			criteria.setTimestamp("TIMEOUT", timeout.getTime());

			log.debug(criteria);

			res = criteria.list();

			for (Recurso uniqueResult : res) {
				if (uniqueResult != null) {
					if (uniqueResult.getPatrullas() != null) {
						uniqueResult.getPatrullas().getId();
					}
					if (uniqueResult.getFlotas() != null) {
						uniqueResult.getFlotas().getId();
					}
				}
			}

		} catch (Throwable e) {
			log.error("Error al calcular los más cercanos", e);
		}

		log.info("Encontrados " + res.size() + " recursos");

		return res.toArray(new Recurso[0]);
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Recurso getByNombre(String nombre) {
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.add(Restrictions.eq("nombre", nombre));
			return (Recurso) criteria.uniqueResult();
		} catch (Throwable t) {
			log.error(t, t);
			return null;
		}
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public Recurso getbyDispositivo(Integer disp) {
		Recurso uniqueResult = null;
		try {
			Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Recurso.class)
					.add(Restrictions.eq("dispositivo", disp));
			uniqueResult = (Recurso) criteria.uniqueResult();
			if (uniqueResult != null) {
				if (uniqueResult.getPatrullas() != null) {
					uniqueResult.getPatrullas().getId();
				}
				if (uniqueResult.getFlotas() != null) {
					uniqueResult.getFlotas().getId();
				}
			}
		} catch (Throwable t) {
			log.error(t, t);
		}
		return uniqueResult;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Recurso> getAll(Usuario u) {
		List<Recurso> res = new ArrayList<Recurso>();
		try {
			Session currentSession = getSession();
			currentSession.clear();

			final Calendar calenar = Calendar.getInstance();
			calenar.add(Calendar.MINUTE,
					-LogicConstants.getInt("AVL_TIMEOUT", 30));
			Date fecha = calenar.getTime();

			String sql = "select this_.* from recursos this_ "
					+ "inner join flotas flota2_ on this_.flota_x_flota=flota2_.x_flota "
					+ "inner join ROLES_X_FLOTAS roles15_ on flota2_.x_flota=roles15_.X_FLOTA "
					+ "inner join roles rol3_ on roles15_.X_ROL=rol3_.x_rol "
					+ "inner join usuarios usuario4_ on rol3_.x_rol=usuario4_.fk_roles "
					+ "inner join historico_gps historico1_ on this_.fk_historico_gps=historico1_.x_historico "
					+ "where this_.habilitado=true "
					+ "and historico1_.marca_temporal>=:DATE ";

			if (u != null && u.getId() != null) {
				sql += "and usuario4_.x_usuarios=:ID";
			}
			Query criteria = currentSession.createSQLQuery(sql)
					.addEntity(Recurso.class).setTimestamp("DATE", fecha)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if (u != null && u.getId() != null) {
				criteria = criteria.setLong("ID", u.getId());
			}

			log.trace("Cogemos los recursos visibles: " + criteria.toString()
					+ " " + fecha);

			res = criteria.list();

			if (log.isTraceEnabled()) {
				log.trace("Recursos que mostramos finalmente:");
				for (Recurso s : res) {
					log.trace(s);
				}
			}
			for (Recurso uniqueResult : res) {
				if (uniqueResult != null) {
					if (uniqueResult.getEstadoEurocop() != null) {
						uniqueResult.getEstadoEurocop().getId();
					}
					if (uniqueResult.getFlotas() != null) {
						uniqueResult.setFlotas((Flota) currentSession.get(
								Flota.class, uniqueResult.getFlotas().getId()));
						currentSession.refresh(uniqueResult.getFlotas());
					}
					if (uniqueResult.getPatrullas() != null) {
						uniqueResult.setPatrullas((Patrulla) currentSession
								.get(Patrulla.class, uniqueResult
										.getPatrullas().getId()));
						uniqueResult.getPatrullas().getId();
					}
				}
			}

		} catch (Throwable t) {
			log.error("Mostrando los recursos visibles", t);
		}

		return res;
	}

	/**
	 * Devuelve todos los recursos que tienen posiciones en HistoricoGPS más
	 * antiguas que límite.
	 * 
	 * @param limite
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Recurso> getTodas(Calendar limite) {
		List<Recurso> res = new ArrayList<Recurso>();
		try {
			Session currentSession = getSession();
			currentSession.clear();

			DetachedCriteria dc = DetachedCriteria.forClass(HistoricoGPS.class)
					.setProjection(Projections.property("recurso"))
					.add(Restrictions.le("marcaTemporal", limite.getTime()))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			final Criteria query = currentSession.createCriteria(Recurso.class)
					.add(Subqueries.propertyIn("identificador", dc))
					.addOrder(Order.asc("identificador"));

			if (log.isDebugEnabled())
				log.debug(query);

			res = query.list();

			// Para evitar LazyInicializationException accedemos a
			// todas las flotas de los recursos devueltos ya que en
			// los lugares en los que se usa esta función es necerasio
			// también la flota.
			for (Recurso uniqueResult : res) {
				if (uniqueResult != null) {
					if (uniqueResult.getFlotas() != null) {
						uniqueResult.getFlotas().getId();
					}
				}
			}
		} catch (Throwable t) {
			log.error("Sacando los recursos para generar los gpx", t);
		}

		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public Recurso[] getAsigned(Incidencia i) {
		Recurso[] res = new Recurso[0];
		if (i == null || i.getId() == null) {
			return res;
		}
		log.debug("getAsigned(" + i + ")");
		Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Recurso.class)
				.createCriteria("incidencias")
				.add(Restrictions.eq("id", i.getId()));
		criteria = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		res = ((List<Recurso>) criteria.list()).toArray(new Recurso[0]);
		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public Recurso getbyDispositivoServer(Integer disp) {
		long now = System.currentTimeMillis();
		Recurso res = null;
		Session currentSession = getSession();
		SessionFactory sessionFactory = (SessionFactory) MyBeanFactory
				.getBean("sessionFactory");
		Criteria criteria = currentSession.createCriteria(Recurso.class)
				.add(Restrictions.eq("dispositivo", disp)).setCacheable(true);
		res = (Recurso) criteria.uniqueResult();
		System.out.print("time run " + disp + ": "
				+ (System.currentTimeMillis() - now));
		System.out.println(", cacheStatsRecurso = "
				+ sessionFactory
						.getStatistics()
						.getSecondLevelCacheStatistics(
								"es.emergya.bbdd.bean.Recurso").toString());
		System.out.println("QueryStats = queryCacheHits= "
				+ sessionFactory.getStatistics().getQueryCacheHitCount()
				+ ", queryCacheMiss="
				+ sessionFactory.getStatistics().getQueryCacheMissCount()
				+ ", queryCachePut="
				+ sessionFactory.getStatistics().getQueryCachePutCount());

		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public RecursoBean getByDispositivoSQL(Integer issi) {

		SQLQuery query = getSession()
				.createSQLQuery(
						"select r.x_recurso as "
								+ "id, r.identificador as identificador, r.habilitado as habilitado, "
								+ "r.tipo as \"tipoRecurso\", s.nombre as "
								+ "subflota, r.dispositivo as dispositivo from recursos as r join flotas s on r.flota_x_flota = s.x_flota "
								+ "where r.dispositivo = :DISPOSITIVO");
		query.setInteger("DISPOSITIVO", issi);
		query.setResultTransformer(Transformers.aliasToBean(RecursoBean.class));
		RecursoBean recurso = (RecursoBean) query.uniqueResult();

		return recurso;

	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public void updateLastGpsSQL(HistoricoGPS historicoGPS, BigInteger idRecurso) {
		SQLQuery query = getSession()
				.createSQLQuery(
						"update recursos set fk_historico_gps = :HISTORICO where x_recurso = :ID");
		query.setBigInteger("ID", idRecurso);
		query.setLong("HISTORICO", historicoGPS.getId());
		query.executeUpdate();
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
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
