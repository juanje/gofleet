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
package es.emergya.consultas;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.dao.FlotaHome;
import es.emergya.utils.MyBeanFactory;

@SuppressWarnings("unchecked")
public class FlotaConsultas {

	static final Log log = LogFactory.getLog(FlotaConsultas.class);
	private static FlotaHome flotaHome;

	static {
		flotaHome = (FlotaHome) MyBeanFactory.getBean("flotaHome");
	}

	private FlotaConsultas() {
		super();
	}

	public static List<Flota> getAll() {
		return flotaHome.getAllHabilitadas();
	}

	public static Flota get(Long id) {
		return flotaHome.get(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
	public static Flota find(Long id) {
		return flotaHome.find(id);
	}

	public static List<String> getAllIcons(String dir) {
		List<String> res = new ArrayList<String>();
		try {
			URL u = FlotaConsultas.class.getResource(dir);
			if (u != null) {
				File f = new File(u.getFile());
				if (f.isDirectory()) {
					FilenameFilter filter = new FilenameFilter() {
						public boolean accept(File dir, String name) {
							return name.endsWith("_preview.png");
						}
					};
					for (String path : f.list(filter))
						res.add(path.substring(0, path
								.indexOf("_flota_preview")));
				}
			}
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static List<Flota> getByExample(Flota p) {
		List<Flota> res = new ArrayList<Flota>(0);
		if (p == null)
			return getAll();
		try {
			res = flotaHome.getByFilter(p);
		} catch (Throwable t1) {
			log.error(t1, t1);
		}

		return res;
	}

	public static Integer getTotal() {
		return flotaHome.getTotal();
	}

	public static Flota find(String nombre) {
		return flotaHome.find(nombre);
	}

	public static boolean existe(String original) {
		return flotaHome.existe(original);
	}

	@Transactional
	public static String[] getAllFilter() {
		List<String> res = new ArrayList<String>();
		res.add("");
		res.addAll(flotaHome.getAllNamesHabilitadas());
		return res.toArray(new String[0]);
	}

	public static Flota[] getAllHabilitadas() {
		List<Flota> res = new ArrayList<Flota>();
		// res.add(null);
		res.addAll(flotaHome.getAllHabilitadas());
		return res.toArray(new Flota[0]);
	}

	public static Calendar lastUpdated() {
		return flotaHome.lastUpdated();
	}
}
