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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.dao.PatrullaHome;
import es.emergya.utils.MyBeanFactory;

@SuppressWarnings("unchecked")
public class PatrullaAdmin {

	static final Log log = LogFactory.getLog(PatrullaAdmin.class);
	private static GenericDao<Patrulla, Long> patrullaDAO;
	private static PatrullaHome patrullaHome;

	static {
		patrullaDAO = (GenericDao<Patrulla, Long>) MyBeanFactory
				.getBean("patrullaDAO");
		patrullaHome = (PatrullaHome) MyBeanFactory.getBean("patrullaHome");
	}

	private PatrullaAdmin() {
		super();
	}

	@Transactional
	public static boolean delete(Patrulla p) {
		return patrullaHome.delete(p);
	}

	@Transactional
	public static boolean saveOrUpdate(Patrulla p) {
		boolean res = false;

		patrullaHome.removeRecursos(p);
		patrullaHome.saveOrUpdate(p);

		return res;
	}

	@Transactional
	public static List<Patrulla> getByExample(Patrulla p) {
		List<Patrulla> res = new ArrayList<Patrulla>(0);
		try {
			res = patrullaHome.getByFilter(p);
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	@Transactional
	public static Integer getTotal() {
		return patrullaHome.getTotal();
	}
}
