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
package es.emergya.bbdd.dao.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.bbdd.dao.RecursoHome;
import es.emergya.consultas.PatrullaConsultas;

public class RecursoHomeTest extends org.appfuse.dao.BaseDaoTestCase {

	private RecursoHome recursoHome;

	@Autowired
	public void setRecursoHome(RecursoHome RecursoHome) {
		this.recursoHome = RecursoHome;
	}

	@Test
	@Transactional
	public void testAll() throws Exception {
		List<Recurso> all = recursoHome.getAll();
		assertNotNull(all);
		assertTrue(all.size() > 0);
	}

	@Test
	@Transactional
	public void testAsigneds() throws Exception {
		Patrulla p = null;
		for (Patrulla p2 : PatrullaConsultas.getAllTests())
			if (p2.getRecursos().size() > 0)
				p = p2;
		assertNotNull(p);
		assertNotNull(recursoHome.getAsigned(p));
		assertNotNull(recursoHome.getNotAsigned(p));
		assertTrue(recursoHome.getAsigned(p).length > 0);
		assertNotNull(recursoHome.getNotAsigned(p));
	}
}
