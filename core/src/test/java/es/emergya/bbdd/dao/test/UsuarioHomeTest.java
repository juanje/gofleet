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
/**
 * 
 */
package es.emergya.bbdd.dao.test;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.bean.CapaInformacionUsuario;
import es.emergya.bbdd.bean.Usuario;
import es.emergya.bbdd.dao.UsuarioHome;
import es.emergya.consultas.CapaConsultas;
import es.emergya.utils.MyBeanFactory;

/**
 * @author marias
 * 
 */
public class UsuarioHomeTest extends org.appfuse.dao.BaseDaoTestCase {

	UsuarioHome usuarioHome;
	static Integer total = -1;
	static Integer contador = 0;
	static Boolean error = false;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		usuarioHome = (UsuarioHome) MyBeanFactory.getBean("usuarioHome");
		total = usuarioHome.getTotal();
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#UsuarioHome()}.
	 */
	@Test
	public void testUsuarioHome() {
		assertNotNull(new UsuarioHome());
		assertNotNull(usuarioHome);
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#getTotal()}.
	 */
	@Test
	public void testGetTotal() {
		assertEquals(
				"Numero incorrecto de usuarios, ¿has metido mas en el sample-data?",
				new Integer(5), usuarioHome.getTotal());
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#isLastAdmin(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsLastAdmin() {
		assertFalse("Emergya no es el ultimo admin", usuarioHome
				.isLastAdmin("emergya"));
	}

	/**
	 * Test method for {@link es.emergya.bbdd.dao.UsuarioHome#getAll()}.
	 */
	@Test
	public void testGetAll() {
		List<Usuario> usuarios = usuarioHome.getAll();
		assertNotNull("getAll devolvio una lista vacia", usuarios);
		assertEquals("getAll funciona mal",
				new Integer(usuarioHome.getTotal()), new Integer(usuarios
						.size()));
		for (Usuario u : usuarios)
			assertNotNull(u);
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#alreadyExists(java.lang.String)}
	 * .
	 */
	@Test
	public void testAlreadyExists() {
		assertTrue(usuarioHome.alreadyExists("emergya"));
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#saveOrUpdate(es.emergya.bbdd.bean.Usuario)}
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#delete(es.emergya.bbdd.bean.Usuario)}
	 * .
	 */
	@Test
	public void testSaveOrUpdateAndDelete() {
		Usuario u = new Usuario();
		u.setNombre("marias");
		u.setNombreUsuario("marias");
		u.setPassword("pass");
		u.setApellidos("apellidos");
		u.setAdministrador(false);
		u.setHabilitado(false);
		u.setIncidenciasVisibles(true);

		assertTrue("No se pudo guardar", usuarioHome.saveOrUpdate(u));
		assertTrue("No se pudo borrar", usuarioHome.delete(u));
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#find(java.lang.String)}.
	 */
	@Test
	public void testFind() {
		final Usuario u = usuarioHome.find("emergya");
		assertNotNull(u);
		assertEquals("emergya", u.getNombreUsuario());
	}

	/**
	 * Test method for
	 * {@link es.emergya.bbdd.dao.UsuarioHome#getByFilter(es.emergya.bbdd.bean.Usuario)}
	 * .
	 */
	@Test
	public void testGetByFilter() {
		Usuario emergya = new Usuario();
		emergya.setNombreUsuario("emergya");
		List<Usuario> usuarios = usuarioHome.getByFilter(emergya);
		assertNotNull("getByFilter devolvio una lista vacia", usuarios);
		assertEquals("getByFilter funciona mal", new Integer(1), new Integer(
				usuarios.size()));
		for (Usuario u : usuarios)
			assertNotNull(u);
	}

	@Test
	@Transactional
	public void testUpdateCapas() {
		CapaInformacionUsuario ciu = new CapaInformacionUsuario();
		ciu.setVisibleGPS(true);
		ciu.setVisibleHistorico(true);
		ciu.setUsuario(usuarioHome.find("emergya"));
		CapaInformacion capa = CapaConsultas.getAll().get(0);
		ciu.setCapaInformacion(capa);

		assertTrue(usuarioHome.updateCapasInformacion(ciu));

		Usuario u = usuarioHome.find("emergya");
		assertNotNull(usuarioHome.getCapas(u));
		assertTrue(usuarioHome.getCapas(u).size() > 0);
		CapaInformacionUsuario ciu2 = null;
		for (CapaInformacionUsuario c : usuarioHome.getCapas(u))
			if (c.getUsuario().equals(ciu.getUsuario()))
				ciu2 = c;
		assertNotNull(ciu2);
		assertTrue(ciu2.getVisibleGPS());
		assertTrue(ciu2.getVisibleHistorico());

		ciu2.setVisibleGPS(false);
		ciu2.setVisibleHistorico(false);
		assertTrue(usuarioHome.updateCapasInformacion(ciu2));

		ciu2 = null;
		for (CapaInformacionUsuario c : usuarioHome.getCapas(u))
			if (c.getUsuario().equals(ciu.getUsuario()))
				ciu2 = c;
		assertFalse(ciu2.getVisibleGPS());
		assertFalse(ciu2.getVisibleHistorico());
	}

	@Test
	public void testMuchosThreadsALaVezYCruzaLosDedos() {
		Hilo hilo1 = new Hilo();
		hilo1.start();

		Hilo hilo2 = new Hilo();
		hilo2.start();

		Hilo hilo3 = new Hilo();
		hilo3.start();

		Hilo hilo4 = new Hilo();
		hilo4.start();

		Random r = new Random();
		for (int i = 0; i < 20; i++) {
			System.out.println("Vuelta numero " + i + " con " + getTotal()
					+ " usuarios.");
			try {
				Thread.sleep((int) (500));
			} catch (InterruptedException e) {
				fail(e.toString());
			}
			synchronized (total) {
				List<Usuario> usuarios = usuarioHome.getAll();
				for (Usuario u : usuarios)
					if (r.nextBoolean())
						if (!u.getNombreUsuario().equals("emergya"))
							usuarioHome.delete(u);

				for (int j = 0; j < 3; j++) {
					Usuario u = new Usuario();
					u.setNombre("usuario " + System.currentTimeMillis());
					u.setNombreUsuario("nombre " + System.currentTimeMillis());
					u.setApellidos("apellidos");
					u.setHabilitado(r.nextBoolean());
					u.setAdministrador(r.nextBoolean());
					usuarioHome.saveOrUpdate(u);
				}

				total = usuarioHome.getTotal();
			}
		}

		if (error)
			fail("error en la concurrencia");
		System.out.println(contador + " comprobaciones.");
	}

	public Integer getTotal() {
		synchronized (total) {
			contador++;
			return total;
		}
	}

	class Hilo extends Thread {

		public Hilo() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {
			super.run();
			Random r = new Random();
			while (true) {
				try {
					try {
						Thread.sleep((int) (2000d * r.nextDouble()));
					} catch (InterruptedException e) {
						fail(e.toString());
					}
					assertEquals(new Integer(getTotal()), usuarioHome
							.getTotal());
				} catch (Throwable t) {
					error = true;
				}
			}
		}
	}
}