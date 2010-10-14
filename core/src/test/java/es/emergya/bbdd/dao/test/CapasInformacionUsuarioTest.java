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
package es.emergya.bbdd.dao.test;

import org.appfuse.dao.GenericDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.bean.CapaInformacionUsuario;
import es.emergya.bbdd.bean.Usuario;

public class CapasInformacionUsuarioTest extends
		org.appfuse.dao.BaseDaoTestCase {

	private GenericDao<CapaInformacion, Long> capaInformacionDAO;
	private GenericDao<Usuario, Long> usuarioDAO;
	private GenericDao<CapaInformacionUsuario, Long> capaInformacionUsuarioDAO;

	@Autowired
	public void setCapaInformacionDAO(
			GenericDao<CapaInformacion, Long> capaInformacionDAO) {
		this.capaInformacionDAO = capaInformacionDAO;
	}

	@Autowired
	public void setUsuarioDAO(GenericDao<Usuario, Long> usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Autowired
	public void setCapaInformacionUsuarioDAO(
			GenericDao<CapaInformacionUsuario, Long> capaInformacionusuarioDAO) {
		this.capaInformacionUsuarioDAO = capaInformacionusuarioDAO;
	}

	@Test
	@Transactional
	public void testCreateAndRemove() throws Exception {
		int num_usuarios = this.usuarioDAO.getAll().size();

		Usuario u = new Usuario();
		String nombreUsuario = "nombredelusuario";
		u.setNombreUsuario(nombreUsuario);
		this.usuarioDAO.save(u);
		flush();
		u = null;
		assertEquals(num_usuarios + 1, this.usuarioDAO.getAll().size());
		for (Usuario usuario : this.usuarioDAO.getAll())
			if (nombreUsuario.equals(usuario.getNombreUsuario()))
				u = usuario;
		assertNotNull(u);
		assertNotNull(u.getId());

		int num_capas = this.capaInformacionDAO.getAll().size();
		CapaInformacion ci = new CapaInformacion();
		String infoAdicional = "blablabla";
		ci.setInfoAdicional(infoAdicional);
		ci.setHabilitada(true);
		ci.setOpcional(false);
		ci.setOrden(0);
		ci.setNombre("NOMBRE");
		this.capaInformacionDAO.save(ci);
		ci = null;
		flush();
		assertEquals(num_capas + 1, this.capaInformacionDAO.getAll().size());
		for (CapaInformacion capai : this.capaInformacionDAO.getAll())
			if (infoAdicional.equals(capai.getInfoAdicional()))
				ci = capai;
		assertNotNull(ci);
		assertNotNull(ci.getId());

		CapaInformacionUsuario ciu = new CapaInformacionUsuario();
		ciu.setUsuario(u);
		ciu.setCapaInformacion(ci);
		ciu.setVisibleGPS(true);
		ciu.setVisibleHistorico(true);
		this.capaInformacionUsuarioDAO.save(ciu);
		flush();

		for (Usuario usuario : this.usuarioDAO.getAll())
			if (nombreUsuario.equals(usuario.getNombreUsuario())) {
				assertNotNull(usuario.getCapasInformacion());
				assertEquals(1, usuario.getCapasInformacion().size());
			}

		for (CapaInformacion capai : this.capaInformacionDAO.getAll())
			if (infoAdicional.equals(capai.getInfoAdicional())) {
				assertNotNull(capai.getCapasInformacion());
				assertEquals(1, capai.getCapasInformacion().size());
			}

	}
}
