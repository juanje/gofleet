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
package es.emergya.cliente;

import static es.emergya.cliente.constants.LogicConstants.deriveBoldFont;
import static es.emergya.cliente.constants.LogicConstants.deriveLightFont;
import static es.emergya.cliente.constants.LogicConstants.getInt;
import static es.emergya.cliente.constants.LogicConstants.getLightFont;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.UIManager;

import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

import es.emergya.cliente.scheduler.jobs.UpdateAdminJob;
import es.emergya.cliente.scheduler.jobs.UpdateMapsJob;
import es.emergya.i18n.Internacionalization;
import es.emergya.scheduler.CustomScheduler;
import es.emergya.ui.base.plugins.PluginContainer;
import es.emergya.ui.base.plugins.PluginType;
import es.emergya.ui.base.plugins.Tab;
import es.emergya.ui.gis.FleetControlMapViewer;
import es.emergya.ui.gis.HistoryMapViewer;
import es.emergya.ui.plugins.forms.FormIncidencia;
import es.emergya.ui.plugins.list.ListIncidences;

/**
 * Main class.
 * 
 * @author jlrodriguez
 * @author marias
 * 
 */
public final class Mobile extends Loader {

	private static final int UPDATE_MAPS_FREQUENCY = getInt(
			"UPDATE_MAPS_FREQUENCY", 10) * 1000;
	private static final int UPDATE_LISTADOS_FREQUENCY = getInt(
			"UPDATE_LISTADOS_FREQUENCY", 10) * 1000;
	private static final org.apache.commons.logging.Log LOG = LogFactory
			.getLog(Mobile.class);

	static {
		_this = new Mobile();
	}

	/** Constructor is private to avoid creating objects. */
	private Mobile() {
	}

	@Override
	protected void loadJobs() {
		CustomScheduler scheduler;
		try {
			scheduler = new CustomScheduler();

			scheduler.addJob(Mobile.UPDATE_MAPS_FREQUENCY, "updateMaps",
					UpdateMapsJob.class);

			scheduler.addJob(Mobile.UPDATE_LISTADOS_FREQUENCY,
					"updateListados", UpdateAdminJob.class);
			scheduler.start();
		} catch (SchedulerException e) {
			LOG.error(e, e);
			showError(e);
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 * 
	 * 
	 */
	@Override
	protected void createAndShowGUI() {

		container.setMode(PluginContainer.Modes.MOBILE);

		// Mapas
		FleetControlMapViewer fleetcontrol = new FleetControlMapViewer(
				Internacionalization.getString("Main.FleetControl"),
				PluginType.getType("GPS"), 1, "tab_icon_controlflota");
		container.addPlugin(fleetcontrol);
		container.addPlugin(new HistoryMapViewer(Internacionalization
				.getString("Main.GPS"), PluginType.getType("GPS"), 2,
				"tab_icon_historico"));

		// Listados
		final Tab listados = new Tab(Internacionalization
				.getString("Main.Listados"), PluginType.getType("LIST"), 3,
				"tab_icon_listados", Color.LIGHT_GRAY);
		listados.setDetachable(true);
		container.addPlugin(listados);
		final ListIncidences listIncidences = new ListIncidences(1);
		container.addPlugin(listIncidences);

		// Formularios
		final Tab formularios = new Tab(Internacionalization
				.getString("Main.Formularios"), PluginType.getType("FORMS"), 3,
				"tab_icon_formularios", Color.LIGHT_GRAY);
		formularios.setDetachable(true);
		container.addPlugin(formularios);
		final FormIncidencia formulario = new FormIncidencia(1);
		container.addPlugin(formulario);
	}

	@Override
	protected void configureUI() {
		UIManager.put("swing.boldMetal", Boolean.FALSE); //$NON-NLS-1$

		UIManager.put("TabbedPane.selected", Color.decode("#B1BEF0"));
		final Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, getLightFont());
			}

		}
		UIManager.put("TableHeader.font", deriveBoldFont(10f));
		UIManager.put("TabbedPane.font", deriveLightFont(9f));
	}
}
