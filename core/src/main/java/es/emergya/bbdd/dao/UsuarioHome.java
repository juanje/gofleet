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
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.bean.CapaInformacionUsuario;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.utils.LogicConstants;

@Repository("usuarioHome")
public class UsuarioHome extends GenericDaoHibernate<Usuario, Long> {

	public UsuarioHome() {
		super(Usuario.class);
	}

	@Override
	public Usuario get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Integer getTotal() {
		Integer res = new Integer(-1);
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Usuario.class)
				.setProjection(Projections.rowCount());
		Integer count = (Integer) criteria.uniqueResult();
		res = count.intValue();
		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Boolean isLastAdmin(String nombre) {
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Usuario.class)
				.setProjection(Projections.rowCount()).add(
						Restrictions.eq("administrador", true)).add(
						Restrictions.eq("habilitado", true)).add(
						Restrictions.ne("nombreUsuario", nombre)).setCacheable(
						false);
		Integer count = (Integer) criteria.uniqueResult();
		return (count.intValue() == 0);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Usuario> getAll() {
		List<Usuario> res = new ArrayList<Usuario>(0);
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		res = currentSession.createCriteria(Usuario.class).addOrder(
				Order.desc("nombreUsuario")).setResultTransformer(
				Criteria.DISTINCT_ROOT_ENTITY).list();

		return res;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Usuario> getAll(String order, boolean asc) {
		List<Usuario> res = new ArrayList<Usuario>(0);
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		if (asc)
			res = currentSession.createCriteria(Usuario.class).addOrder(
					Order.asc("order")).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY).list();
		else
			res = currentSession.createCriteria(Usuario.class).addOrder(
					Order.desc("order")).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY).list();

		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean alreadyExists(String nombreUsuario) {
		if (nombreUsuario == null)
			return false;
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();

		Integer count = (Integer) currentSession.createCriteria(Usuario.class)
				.add(Restrictions.eq("nombreUsuario", nombreUsuario))
				.setProjection(Projections.rowCount()).uniqueResult();

		return (count != 0);
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean saveOrUpdate(Usuario entity) {
		log.trace("saveOrUpdate(" + entity + ")");
		if (entity == null)
			throw new NullPointerException(
					"No se puede guardar un usuario nulo");
		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		Object e = null;
		try {
			if (entity.getId() == null
					|| (entity.getId() != null && this.get(entity.getId()) == null)) {
				log.trace("Tenemos que crear un usuario nuevo");
				e = entity;
			} else {
				log.trace("Hacemos update sobre un usuario ya antiguo");
				e = currentSession.merge(entity);
			}
		} catch (Throwable t) {
			log
					.error(
							"Tiene toda la pinta de que estamos guardando algo ya borrado",
							t);
		}
		if (e == null) {
			log.debug("Error al mergear");
			throw new NullPointerException(
					"No se puede guardar el usuario, es nulo");
		}
		currentSession.saveOrUpdate(e);
		log.trace("saved");
		return true;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Usuario entity) {
		if (entity == null)
			throw new NullPointerException("El usuario a borrar es nulo.");

		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		if (entity.getId() == null)
			return true;

		try {
			entity = this.get(entity.getId());
		} catch (Throwable t) {
			log
					.error(
							"Tiene toda la pinta de que estamos borrando algo ya borrado.",
							t);
		}

		if (entity == null)
			return true;

		if (entity.getClientesConectados() != null
				&& entity.getClientesConectados().size() > 0) {
			log
					.debug("Se intentó borrar a un usuario conectado a una estación fija.");
			return false;
		}

		currentSession.createSQLQuery(
				"delete from  usuarios_x_capas_informacion where fk_usuarios="
						+ entity.getId()).executeUpdate();

		this.remove(entity.getId());

		return true;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Usuario find(String nombreUsuario) {
		try {
			org.hibernate.Session currentSession = getSession();
			if (currentSession == null)
				throw new RuntimeException("No tenemos session");
			currentSession.clear();
			Usuario u = (Usuario) currentSession.createCriteria(Usuario.class)
					.add(Restrictions.eq("nombreUsuario", nombreUsuario))
					.uniqueResult();
			u.getCapasInformacion();
			return u;
		} catch (Throwable t) {
			log.error("Error al buscar el usuario", t);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<Usuario> getByFilter(Usuario u) {
		List<Usuario> res = new ArrayList<Usuario>(0);

		org.hibernate.Session currentSession = getSession();
		currentSession.clear();
		Criteria criteria = currentSession.createCriteria(Usuario.class)
				.addOrder(Order.asc("nombreUsuario"));

		if (u.getInfoAdicional() != null)
			criteria = criteria.add(Restrictions.ilike("infoAdicional",
					LogicConstants.getGenericString(u.getInfoAdicional())));
		if (u.getAdministrador() != null)
			criteria = criteria.add(Restrictions.eq("administrador", u
					.getAdministrador()));
		if (u.getHabilitado() != null)
			criteria = criteria.add(Restrictions.eq("habilitado", u
					.getHabilitado()));
		if (u.getNombreUsuario() != null)
			criteria = criteria.add(Restrictions.ilike("nombreUsuario",
					LogicConstants.getGenericString(u.getNombreUsuario())));
		if (u.getNombre() != null)
			criteria = criteria.add(Restrictions.ilike("nombre", LogicConstants
					.getGenericString(u.getNombre())));
		if (u.getApellidos() != null)
			criteria = criteria.add(Restrictions.ilike("apellidos",
					LogicConstants.getGenericString(u.getApellidos())));
		if (u.getRoles() != null)
			criteria = criteria.createCriteria("roles").add(
					Restrictions.ilike("nombre", LogicConstants
							.getGenericString(u.getRoles().getNombre())));

		res = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();

		for (Usuario usu : res)
			if (usu != null) {
				if (usu.getRoles() != null)
					usu.getRoles().getId();
			}

		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Calendar lastUpdated() {
		Calendar res = Calendar.getInstance();
		try {
			org.hibernate.Session currentSession = getSession();
			currentSession.clear();
			Criteria criteria = currentSession.createCriteria(Usuario.class)
					.setProjection(Projections.max("updatedAt"));
			res.setTime((Date) criteria.uniqueResult());
		} catch (Throwable t) {
			log.error(t, t);
			return null;
		}
		return res;
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public List<CapaInformacionUsuario> getCapas(Usuario u) {
		final ArrayList<CapaInformacionUsuario> arrayList = new ArrayList<CapaInformacionUsuario>();
		if (u == null || u.getId() == null) {
			return arrayList;
		}

		u = this.get(u.getId());
		if (u == null)
			return arrayList;

		arrayList.addAll(u.getCapasInformacion());

		return arrayList;
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public boolean updateCapasInformacion(CapaInformacionUsuario ciu) {
		if (ciu == null || ciu.getUsuario() == null
				|| ciu.getCapaInformacion() == null) {
			log.error("¿Usuario nulo? ¿Capa nula?");
			return false;
		}
		Usuario u = ciu.getUsuario();
		if (u.getId() == null) {
			log.error("Usuario sin ID");
			return false;
		}
		CapaInformacion capa = ciu.getCapaInformacion();
		if (capa.getId() == null) {
			log.error("Capa sin ID");
			return false;
		}

		u = this.get(u.getId());
		capa = (CapaInformacion) this.getSession().get(CapaInformacion.class,
				capa.getId());

		if (u == null || capa == null) {
			log
					.error("Estamos intentando guardar un usuario o capa que ya no existen");
			return false;
		}

		ciu.setUsuario(u);
		ciu.setCapaInformacion(capa);

		CapaInformacionUsuario old = null;

		for (CapaInformacionUsuario c : u.getCapasInformacion())
			if (c.getCapaInformacion().equals(capa))
				old = c;

		if (old == null) {
			log.debug("Creamos una nueva relacion capa-usuario" + ciu);
			this.getSession().saveOrUpdate(ciu);
		} else {
			log.debug("Actualizamos la relacion capa-usuario: " + ciu);
			old.setVisibleGPS(ciu.getVisibleGPS());
			old.setVisibleHistorico(ciu.getVisibleHistorico());
			this.getSession().saveOrUpdate(old);
		}

		return true;
	}
	

	/**
	 * @param username
	 * @param password
	 * @return el {@link Usuario} si existe un usario con la contraseña pasada y
	 *         <code>null</code> en caso contrario.
	 */
	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public Usuario checkLogin(String username, String password) {
		log.debug("Comprobando nombre de usuario y contraseña");
		return (Usuario) getSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("nombreUsuario", username))
				.add(Restrictions.eq("password", password)).uniqueResult();

	}
}
