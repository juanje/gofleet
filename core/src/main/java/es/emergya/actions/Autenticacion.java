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
package es.emergya.actions;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.ClienteConectado;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.dao.ClienteConectadoHome;
import es.emergya.utils.MyBeanFactory;

@SuppressWarnings("unchecked")
public class Autenticacion {

	static final Log log = LogFactory.getLog(Autenticacion.class);
	private final static Long id = nextId();
	private static Usuario u = null;

	private static Long nextId() {
		Random r = new Random();
		return r.nextLong();
	}

	// public static void setId(Long id) {
	// Autenticacion.id = id;
	// }

	private static ClienteConectadoHome clienteConectadoHome;
	static {
		clienteConectadoHome = (ClienteConectadoHome) MyBeanFactory
				.getBean("clienteConectadoHome");
	}

	// public static Long newId() {
	// id = nextId();
	// return id;
	// }

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Throwable.class)
	public static void logOut() {
		try {
			u = null;
			ClienteConectado actual = clienteConectadoHome.get(id);
			if (actual != null)
				clienteConectadoHome.remove(id);
		} catch (ObjectRetrievalFailureException t) {
			log.error("Parece que nos han echado, independientemente de nuestro logout. "
					+ t.toString());
		} catch (Throwable t) {
			log.error("Error al hacer logout", t);
			// } finally {
			// id = nextId();
		}
	}

	public static Usuario getUsuario() {
		return u;
	}

	public static boolean isAutenticated() {
		return u != null;
	}

	public static Long getId() {
		return id;
	}

	public static void setUsuario(Usuario u2) {
		u = u2;
	}

	public static boolean isAdministrator() {
		if (!isAutenticated())
			return false;
		else
			return u.getAdministrador() == true;
	}
}
