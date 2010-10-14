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
package es.emergya.actions;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.dao.CapaInformacionHome;
import es.emergya.utils.MyBeanFactory;

public class CapaInformacionAdmin {

	static final Log log = LogFactory.getLog(CapaInformacionAdmin.class);
	private static CapaInformacionHome capaInformacionHome;

	static {
		capaInformacionHome = (CapaInformacionHome) MyBeanFactory
				.getBean("capaInformacionHome");
	}

	private CapaInformacionAdmin() {
		super();
	}

	public static boolean delete(CapaInformacion r) {
		capaInformacionHome.removeUsuarios(r);
		final boolean delete = capaInformacionHome.delete(r);
		capaInformacionHome.updateOrden();
		return delete;
	}

	public static boolean saveOrUpdate(CapaInformacion p) {
		return capaInformacionHome.saveOrUpdate(p);
	}

	public static void sube(CapaInformacion c) {
		List<CapaInformacion> capas = capaInformacionHome
				.getByFilter(new CapaInformacion());
		int i = 1;
		for (CapaInformacion capa : capas) {
			if (!capa.getId().equals(c.getId())) {
				capa.setOrden(i++);
				log.debug("Capa " + capa + " en orden " + capa.getOrden());
				saveOrUpdate(capa);
			} else {
				if (i == 1)
					capa.setOrden(i++);
				else {
					capa.setOrden(i - 1);
					log.debug("Subo " + capa + " a " + capa.getOrden());
					CapaInformacion anterior = capas.get(i - 2);
					anterior.setOrden(i++);
					log.debug("Bajo " + anterior + " a " + anterior.getOrden());
					saveOrUpdate(anterior);
				}
				saveOrUpdate(capa);
			}
		}
	}

	public static void baja(CapaInformacion c) {
		List<CapaInformacion> capas = capaInformacionHome
				.getByFilter(new CapaInformacion());
		int i = 1;
		CapaInformacion anterior = null;
		for (CapaInformacion capa : capas) {
			if (!capa.getId().equals(c.getId())) {
				if (!(anterior != null && anterior.getId().equals(capa.getId()))) {
					capa.setOrden(i++);
					log.debug("Capa " + capa + " en orden " + capa.getOrden());
					saveOrUpdate(capa);
				} else
					log
							.debug("Nos saltamos la capa " + capa + " (" + i++
									+ ")");
			} else {
				if (i == capas.size())
					capa.setOrden(i++);
				else {
					anterior = capas.get(i);
					anterior.setOrden(i);
					log.debug("Subo " + anterior + " a " + anterior.getOrden());
					saveOrUpdate(anterior);
					capa.setOrden(++i);
					log.debug("Bajo " + capa + " a " + capa.getOrden());
				}
				saveOrUpdate(capa);
			}
		}
	}
}
