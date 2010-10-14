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

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.BandejaSalida;

@Repository("bandejaSalidaHome")
public class BandejaSalidaHome extends GenericDaoHibernate<BandejaSalida, Long> {

	public BandejaSalidaHome() {
		super(BandejaSalida.class);
	}

	@Transactional(readOnly = true, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	@Override
	public BandejaSalida get(Long id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public void delete(BandejaSalida b) {
		try {
			b = get(b.getId());
			if (b != null)
				getSession().delete(b);
		} catch (Throwable t) {
			log.error("Error borrando la bandeja de salida", t);
		}
	}

}
