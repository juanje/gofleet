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

import java.util.HashMap;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Configuracion;

@Repository("configuracionHome")
public class ConfiguracionHome extends
		GenericDaoHibernate<Configuracion, String> {

	private static HashMap<String, String> configuracion = new HashMap<String, String>();

	public ConfiguracionHome() {
		super(Configuracion.class);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public Configuracion get(String id) {
		try {
			return super.get(id);
		} catch (Throwable t) {
			log.error("Estamos buscando un objeto que no existe", t);
			return null;
		}
	}

	public String getStringValue(String key, String default_) {
		String res = getStringValue(key);
		if (res == null)
			return default_;
		else
			return res;
	}

	public Integer getIntegerValue(String key, Integer default_) {
		Integer res = getIntegerValue(key);
		if (res == null)
			return default_;
		else
			return res;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
	public String getStringValue(String key) {
		String res = getConfiguration(key);
		try {
			Configuracion c = this.get(key);
			if (c != null) {
				res = c.getValor();
				if (!c.getUpdatable())
					setConfiguration(key, res);
			}
		} catch (Throwable t) {
			log.error("Configuracion no encontrada", t);
		}

		return res;
	}

	public Integer getIntegerValue(String key) {
		String res = getStringValue(key);
		Integer i = null;

		try {
			i = new Integer(res);
		} catch (Throwable t) {
			log.error("Error al convertir a entero", t);
		}

		return i;
	}

	private synchronized String setConfiguration(String key, String res) {
		return configuracion.put(key, res);
	}

	private synchronized String getConfiguration(String key) {
		return configuracion.get(key);
	}

}
