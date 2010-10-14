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

import es.emergya.actions.Autenticacion;
import es.emergya.bbdd.bean.Flota;
import es.emergya.bbdd.bean.Incidencia;
import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.bean.notmapped.RecursoBean;
import es.emergya.bbdd.dao.RecursoHome;
import es.emergya.utils.MyBeanFactory;

public class RecursoConsultas {

	static final Log log = LogFactory.getLog(RecursoConsultas.class);
	private static RecursoHome recursoHome;

	static {
		recursoHome = (RecursoHome) MyBeanFactory.getBean("recursoHome");
	}

	private RecursoConsultas() {
		super();
	}

	@Deprecated
	public static List<Recurso> getAll() {
		return getAll(Autenticacion.getUsuario());
	}

	public static List<Recurso> getAll(Usuario u) {
		return recursoHome.getAll(u);
	}

	public static String[] getTipos() {
		return RecursoHome.getTipos();
	}

	public static Recurso[] getNotAsigned(Patrulla p) {
		return recursoHome.getNotAsigned(p);
	}

	public static Recurso[] getAsigned(Incidencia i) {
		return recursoHome.getAsigned(i);
	}

	public static Recurso[] getAsigned(Patrulla p) {
		return recursoHome.getAsigned(p);
	}

	public static Recurso[] getNotAsigned(Flota p) {
		return recursoHome.getNotAsigned(p);
	}

	public static Recurso[] getAsigned(Flota p) {
		return recursoHome.getAsigned(p);
	}

	public static Integer getTotal() {
		return recursoHome.getTotal();
	}

	public static List<Recurso> getByExample(Recurso p) {
		return recursoHome.getByFilter(p);
	}

	public static Calendar lastUpdated() {
		return recursoHome.lastUpdated();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param num
	 * @param u
	 * @return Una lista de num recursos ordenadas por distancia al punto x, y
	 */
	public static Recurso[] getNearest(double x, double y, int num, Usuario u) {
		return recursoHome.getNearest(x, y, num, u);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param num
	 * @param u
	 * @return Una lista de num recursos que el usuario u puede ver ordenadas
	 *         por distancia al punto x, y
	 */
	public static Recurso[] getNearestForUser(double x, double y, int num,
			Usuario u) {
		return recursoHome.getNearest(x, y, num, u);
	}

	public static Recurso getByIdentificador(String origen) {
		return recursoHome.getByIdentificador(origen);
	}

	/**
	 * 
	 * @param dispositivo
	 * @return el recurso que tiene el dispositivo indicado.
	 */
	public static Recurso getbyDispositivo(String origen) {
		try {
			int disp = Integer.valueOf(origen);
			return recursoHome.getbyDispositivo(disp);
		} catch (NumberFormatException ext) {
			log.error("Error buscando dispositivo no numérico: " + origen);
			return null;
		}
	}

	public static boolean alreadyExists(String nombre) {
		return (getByNombre(nombre) != null);
	}

	public static Recurso getByNombre(String nombre) {
		return recursoHome.getByNombre(nombre);
	}

	public static boolean alreadyExists(Integer integer) {
		return (getbyDispositivo(integer.toString()) != null);
	}

	public static boolean alreadyExists(Integer integer, Long myself) {
		final Recurso getbyDispositivo = getbyDispositivo(integer.toString());
		if (getbyDispositivo == null)
			return false;
		return (!getbyDispositivo.getId().equals(myself));
	}

        /**
         * Obtiene todos los recursos que tienen al menos una entrada en
         * HistoricoGPS anterior a fin.
         * @param fin
         * @return la lista de recursos ordenada ascendentemente por identificador.
         */
	public static List<Recurso> getTodas(Calendar fin) {
		return recursoHome.getTodas(fin);
	}

        public static Recurso getbyDispositivoServer(String origen) {
		try {
			int disp = Integer.valueOf(origen);
			return recursoHome.getbyDispositivoServer(disp);
		} catch (NumberFormatException ext) {
			log.error("Error buscando dispositivo no numérico: " + origen);
			return null;
		}
	}

        public static RecursoBean findByDispositivoSQL(String issi) {
            return recursoHome.getByDispositivoSQL(Integer.valueOf(issi));
        }

		public static Recurso get(Long id) {
			return recursoHome.get2(id);
		}
}
