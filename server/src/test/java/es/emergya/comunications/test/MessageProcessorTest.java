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
package es.emergya.comunications.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

import es.emergya.bbdd.bean.BandejaEntrada;
import es.emergya.bbdd.bean.HistoricoGPS;
import es.emergya.bbdd.bean.Patrulla;
import es.emergya.bbdd.bean.Recurso;
import es.emergya.communications.MessageProcessor;
import es.emergya.comunications.exceptions.MessageProcessingException;
import es.emergya.consultas.PatrullaConsultas;
import es.emergya.consultas.RecursoConsultas;

public class MessageProcessorTest {

	@Test
	public void testProcessingMessagePosicion()
			throws MessageProcessingException {
		final Double y = new Double(42.349167);
		final Double x = new Double(3.684722);
		MessageProcessor mp = new MessageProcessor();
		BandejaEntrada entrada = new BandejaEntrada();
		entrada.setDatagramaTetra("|16|" + y + ",N|" + x + ",W|1|");
		entrada.setMarcaTemporal(Calendar.getInstance().getTime());
		entrada.setOrigen("08000002");
		entrada.setProcesado(false);
		mp.processingMessage(entrada);

		final Recurso getbyDispositivo = RecursoConsultas
				.getbyDispositivo("08000002");
		if (getbyDispositivo != null) {
			HistoricoGPS historico = getbyDispositivo.getHistoricoGps();

			assertEquals(historico.getGeom().getSRID(), 4326);
			assertEquals((Double) (-x), (Double) historico.getPosX());
			assertEquals(y, (Double) historico.getPosY());
		}
		assertTrue(entrada.isProcesado());
	}

	@Test
	public void testProcessingMessagePatrulla()
			throws MessageProcessingException {
		List<Patrulla> patrullas = PatrullaConsultas.getAll();
		if (patrullas.size() > 0) {
			String patrulla = patrullas.get(0).getNombre();
			MessageProcessor mp = new MessageProcessor();
			BandejaEntrada entrada = new BandejaEntrada();
			entrada.setDatagramaTetra("|30|" + patrulla + "|");
			entrada.setMarcaTemporal(Calendar.getInstance().getTime());
			entrada.setOrigen("08000002");
			entrada.setProcesado(false);
			mp.processingMessage(entrada);
			assertTrue(entrada.isProcesado());
		}
	}

	@Test
	public void testProcessingMessagePatrullaInexistente()
			throws MessageProcessingException {
		String patrulla = "avkclf";
		MessageProcessor mp = new MessageProcessor();
		BandejaEntrada entrada = new BandejaEntrada();
		entrada.setDatagramaTetra("|30|" + patrulla + "|");
		entrada.setMarcaTemporal(Calendar.getInstance().getTime());
		entrada.setOrigen("08000002");
		entrada.setProcesado(false);
		mp.processingMessage(entrada);
		assertTrue(entrada.isProcesado());
	}

	@Test
	public void testProyeccion() {
		GeometryFactory factory = new GeometryFactory();
		final String sourceSRID = "EPSG:4326";
		final String targetSRID = "EPSG:3395";
		Geometry geom = factory.createPoint(new Coordinate(42.349167d,
				3.684722d));
		geom = MessageProcessor.transform(geom, sourceSRID, targetSRID);
		assertEquals(geom.toText(),
				"POINT (410181.3767547725 5184634.982024495)");

	}
}
