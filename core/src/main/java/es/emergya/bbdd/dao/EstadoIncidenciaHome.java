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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.EstadoIncidencia;

@Repository("estadoIncidenciaHome")
public class EstadoIncidenciaHome extends
		GenericDaoHibernate<EstadoIncidencia, Long> {

	private static final Log log = LogFactory
			.getLog(EstadoIncidenciaHome.class);

	public EstadoIncidenciaHome() {
		super(EstadoIncidencia.class);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public EstadoIncidencia getDefault() {
		try {
			Session currentSession = getSession();
			if (currentSession == null) {
				throw new NullPointerException("La sesion de Hibernate es nula");
			}
			Criteria crit = currentSession.createCriteria(
					EstadoIncidencia.class).add(Restrictions.eq("id", 1l));
			return (EstadoIncidencia) crit.setMaxResults(1).uniqueResult();
		} catch (Throwable t) {
			log.error(
					"Error al sacar el estado por defecto de las incidencias",
					t);
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public EstadoIncidencia getByIdentificador(String identificador) {

		Session currentSession = getSession();
		if (currentSession == null) {
			throw new NullPointerException("La sesion de Hibernate es nula");
		}
		Criteria crit = currentSession.createCriteria(EstadoIncidencia.class)
				.add(Restrictions.eq("identificador", identificador));
		return (EstadoIncidencia) crit.setMaxResults(1).uniqueResult();
	}
}
