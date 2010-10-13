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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.ClienteConectado;
import es.emergya.bbdd.bean.Usuario;

/**
 * Home object for domain model class ClienteConectado.
 * 
 * @see es.emergya.persistence.ClienteConectado
 * @author Hibernate Tools
 */

@Repository("clienteConectadoHome")
public class ClienteConectadoHome extends
		GenericDaoHibernate<ClienteConectado, Long> {

	public ClienteConectadoHome() {
		super(ClienteConectado.class);
	}

	private static final Log log = LogFactory
			.getLog(ClienteConectadoHome.class);

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void persist(ClienteConectado transientInstance) {
		log.debug("persisting ClienteConectado instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void remove(ClienteConectado persistentInstance) {
		log.debug("removing ClienteConectado instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public ClienteConectado merge(ClienteConectado detachedInstance) {
		log.debug("merging ClienteConectado instance");
		try {
			ClienteConectado result = (ClienteConectado) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public ClienteConectado findById(long id) {
		log.debug("getting ClienteConectado instance with id: " + id);
		try {
			ClienteConectado instance = (ClienteConectado) getSession().get(
					ClienteConectado.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * Elimina los clientes conectados que cuya fecha de última actualización
	 * sea anterior a <code>secondsOld</code> segundos antes de la consulta.
	 * 
	 * @param secondsOld
	 * @return el número de filas borradas.
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public int cleanOldClienteConectado(int secondsOld) {
		Session session = getSession();
		String sqlDelete = "DELETE FROM clientes_conectados c WHERE current_timestamp - c.ultima_conexion  > interval '"
				+ secondsOld + " seconds'";
		int deleted = session.createSQLQuery(sqlDelete).executeUpdate();
		return deleted;

	}

	/**
	 * 
	 * @return el número de entradas en ClienteConectado
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public int countClienteConectado() {
		log.debug("obteniendo el número de clientes conectados");
		Criteria count = getSession().createCriteria(ClienteConectado.class);
		count.setProjection(Projections.rowCount());
		return (Integer) count.list().get(0);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public void addNewClienteConectado(Usuario user, Long fsUid) {
		ClienteConectado cliente = new ClienteConectado();
		cliente.setId(fsUid);
		cliente.setUsuario(user);
		cliente.setUltimaConexion(new Date());

		getSession().save(cliente);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
	public boolean updateLastConnected(long fsUid) {
		log.debug("Actualizando la fecha de última conexión de la EF " + fsUid);
		String hql = "update ClienteConectado set ultimaConexion = now() where id = :FSUID";
		boolean result = false;
		Query query = getSession().createQuery(hql);
		query.setLong("FSUID", fsUid);
		int rowsUpdated = query.executeUpdate();
		if (rowsUpdated == 0) {
			result = false;
		} else {
			result = true;
		}
		return result;

	}
}
