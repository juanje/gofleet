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
package es.emergya.consultas;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.bbdd.bean.CapaInformacionUsuario;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.dao.UsuarioHome;
import es.emergya.utils.MyBeanFactory;

public class UsuarioConsultas {

	private UsuarioConsultas() {
		super();
	}

	static {
		usuarioHome = (UsuarioHome) MyBeanFactory.getBean("usuarioHome");
	}

	static final Log log = LogFactory.getLog(UsuarioConsultas.class);
	private static UsuarioHome usuarioHome;

	public static boolean alreadyExists(String nombreUsuario) {
		return usuarioHome.alreadyExists(nombreUsuario);
	}

	public static Usuario find(String nombreUsuario) {
		return usuarioHome.find(nombreUsuario);
	}

	public static List<Usuario> getByExample(Usuario example) {
		return usuarioHome.getByFilter(example);
	}

	public static int getTotal() {
		return usuarioHome.getTotal();
	}

	public static Boolean isLastAdmin(String nombre) {
		return usuarioHome.isLastAdmin(nombre);
	}

	public static Calendar lastUpdated() {
		return usuarioHome.lastUpdated();
	}

	public static List<CapaInformacionUsuario> getCapas(Usuario u) {
		return usuarioHome.getCapas(u);
	}
}
