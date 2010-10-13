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
package es.emergya;

import java.util.Calendar;

import es.emergya.auxbeans.IncidenciaWS;
import es.emergya.bbdd.bean.notmapped.Posicion;

/**
 * 
 * @author jlrodriguez
 */
public interface IService {
	public String loginEF(String username, String password, Long fsUid);

	public boolean actualizaLoginEF(Long fsUid);

	public String[] getRecursosEnPeriodo(String nombreUsuario,
			Calendar fechaInicio, Calendar fechaFinal, Long[] zonas);

	public IncidenciaWS[] getIncidenciasAbiertasEnPeriodo(String nombreUsuario,
			Calendar fechaInicio, Calendar fechaFinal, Long[] zonas);

	public String[] getRutasRecursos(String nombreUsuario,
			String[] listaRecursos, Calendar fechaInicio, Calendar fechaFin);

	public Posicion[] getUltimasPosiciones(String nombreUsuario,
			String[] idRecursos, Long[] zonas);

	public Posicion[] getPosicionesIncidencias(String nombreUsuario,
			String[] idIncidencias, Long[] zonas);

	public String[] getRutasRecursosFromBBDD(String nombreUsuario,
			String[] listaRecursos, Calendar fechaInicio, Calendar fechaFin);

}
