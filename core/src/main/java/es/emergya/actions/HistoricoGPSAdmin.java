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

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.GenericDao;

import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.dao.HistoricoGPSHome;
import es.emergya.utils.MyBeanFactory;

@SuppressWarnings("unchecked")
public class HistoricoGPSAdmin {


	private HistoricoGPSAdmin() {
		super();
	}

	private static final GenericDao<HistoricoGPS, Long> historicoGPSDao;
	private static final HistoricoGPSHome historicoGPSHome;
	static {
		historicoGPSDao = (GenericDao<HistoricoGPS, Long>) MyBeanFactory
				.getBean("historicoGPSDAO");
		historicoGPSHome = (HistoricoGPSHome) MyBeanFactory
				.getBean("historicoGPSHome");
	}

	static final Log log = LogFactory.getLog(HistoricoGPSAdmin.class);

	public static boolean saveOrUpdate(HistoricoGPS hgps) {
		return (historicoGPSHome.saveOrUpdate(hgps) != null);
	}

	public static void delete(String identificador, Calendar ini, Calendar fin) {
		historicoGPSHome.delete(identificador, ini, fin);

	}
    public static HistoricoGPS saveServer(HistoricoGPS historicoGPS) {
        return historicoGPSHome.saveServer(historicoGPS);
    }
}
