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
package es.emergya.cliente;

import static es.emergya.cliente.constants.LogicConstants.deriveBoldFont;
import static es.emergya.cliente.constants.LogicConstants.deriveLightFont;
import static es.emergya.cliente.constants.LogicConstants.getInt;
import static es.emergya.cliente.constants.LogicConstants.getLightFont;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Enumeration;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionException;

import es.emergya.cliente.scheduler.jobs.LoginJob;
import es.emergya.cliente.scheduler.jobs.UpdateAdminJob;
import es.emergya.cliente.scheduler.jobs.UpdateMapsJob;
import es.emergya.i18n.Internacionalization;
import es.emergya.scheduler.CustomScheduler;
import es.emergya.ui.base.BasicWindow;
import es.emergya.ui.base.LoginWindow;
import es.emergya.ui.base.plugins.PluginContainer;
import es.emergya.ui.base.plugins.PluginEventHandler;
import es.emergya.ui.base.plugins.PluginType;
import es.emergya.ui.base.plugins.Tab;
import es.emergya.ui.gis.FleetControlMapViewer;
import es.emergya.ui.gis.HistoryMapViewer;
import es.emergya.ui.plugins.admin.AdminFleets;
import es.emergya.ui.plugins.admin.AdminLayers;
import es.emergya.ui.plugins.admin.AdminResources;
import es.emergya.ui.plugins.admin.AdminRoles;
import es.emergya.ui.plugins.admin.AdminSquads;
import es.emergya.ui.plugins.admin.AdminUsers;
import es.emergya.ui.plugins.forms.FormIncidencia;
import es.emergya.ui.plugins.list.ListIncidences;

/**
 * Main class.
 * 
 * @author jlrodriguez
 * @author marias
 * 
 */
public final class Main {

	private static final int LOGIN_FREQUENCY = getInt("LOGIN_FREQUENCY", 10) * 1000;
	private static final int UPDATE_MAPS_FREQUENCY = getInt(
			"UPDATE_MAPS_FREQUENCY", 10) * 1000;
	private static final int UPDATE_LISTADOS_FREQUENCY = getInt(
			"UPDATE_LISTADOS_FREQUENCY", 10) * 1000;
	private static final org.apache.commons.logging.Log LOG = LogFactory
			.getLog(Main.class);

	/** Constructor is private to avoid creating objects. */
	private Main() {
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
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

		PluginContainer container = new PluginContainer();

		// Mapas
		FleetControlMapViewer fleetcontrol = new FleetControlMapViewer(
				Internacionalization.getString("Main.FleetControl"),
				PluginType.GPS, 1, "tab_icon_controlflota");
		container.addPlugin(fleetcontrol);
		container.addPlugin(new HistoryMapViewer(Internacionalization
				.getString("Main.History"), PluginType.GPS, 2,
				"tab_icon_historico"));

		// Administracion
		final Tab administracion = new Tab(
				Internacionalization.getString("Main.Administration"),
				PluginType.ADMIN, 2, "tab_icon_administracion",
				Color.LIGHT_GRAY);
		administracion.setDetachable(true);
		container.addPlugin(administracion);
		final AdminFleets adminFleets = new AdminFleets(1);
		container.addPlugin(adminFleets);
		final AdminRoles adminRoles = new AdminRoles(2);
		container.addPlugin(adminRoles);
		final AdminSquads adminSquads = new AdminSquads(3);
		container.addPlugin(adminSquads);
		final AdminResources adminResources = new AdminResources(4);
		container.addPlugin(adminResources);
		final AdminUsers adminUsers = new AdminUsers(5);
		container.addPlugin(adminUsers);
		final AdminLayers adminLayers = new AdminLayers(6);
		container.addPlugin(adminLayers);

		// Listados
		final Tab listados = new Tab(
				Internacionalization.getString("Main.Listados"),
				PluginType.LIST, 3, "tab_icon_listados", Color.LIGHT_GRAY);
		listados.setDetachable(true);
		container.addPlugin(listados);
		final ListIncidences listIncidences = new ListIncidences(1);
		container.addPlugin(listIncidences);

		// Formularios
		final Tab formularios = new Tab(
				Internacionalization.getString("Main.Formularios"),
				PluginType.FORMS, 3, "tab_icon_formularios", Color.LIGHT_GRAY);
		formularios.setDetachable(true);
		container.addPlugin(formularios);
		final FormIncidencia formulario = new FormIncidencia(1);
		container.addPlugin(formulario);

		BasicWindow.setPluginContainer(container);
		LoginWindow.showLogin();

		// Eventos de refresco
		PluginEventHandler.register(adminUsers, adminRoles);
		PluginEventHandler.register(adminRoles, adminUsers);
		PluginEventHandler.register(adminUsers, adminLayers);
		PluginEventHandler.register(adminLayers, adminUsers);
		PluginEventHandler.register(adminResources, adminFleets);
		PluginEventHandler.register(adminResources, adminSquads);
		PluginEventHandler.register(adminFleets, adminResources);
		PluginEventHandler.register(adminSquads, adminResources);
		PluginEventHandler.register(adminRoles, adminFleets);
		PluginEventHandler.register(adminFleets, adminRoles);
	}

	/**
	 * Starts the app.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					if (LOG.isTraceEnabled()) {
						LOG.trace("Excepcion descontrolada en " + t.toString(),
								e);
					} else {
						LOG.error("Excepcion descontrolada en " + t.toString()
								+ " :: " + e.toString(), e);
					}
				}
			});
		} catch (Throwable t) {
			LOG.error(t, t);
			showError(t);
		}

		try {
			TimeZone.setDefault(TimeZone.getTimeZone("CET")); //$NON-NLS-1$
			SwingUtilities.invokeLater(new Initializer());
		} catch (Throwable t) {
			LOG.error("Fallo el SwingUtilities.invokeLater", t);
			showError(t);
		}
	}

	/**
	 * Initializes the GUI.
	 * 
	 */
	static class Initializer implements Runnable {

		/** Initializtes the GUI. */
		@Override
		public void run() {
			try {
				UIManager.put("swing.boldMetal", Boolean.FALSE); //$NON-NLS-1$
				Main.createAndShowGUI();
				CustomScheduler scheduler = new CustomScheduler();

			//	scheduler.addJob(LOGIN_FREQUENCY, "loginJob", LoginJob.class);

				scheduler.addJob(Main.UPDATE_MAPS_FREQUENCY, "updateMaps",
						UpdateMapsJob.class);

				scheduler.addJob(Main.UPDATE_LISTADOS_FREQUENCY,
						"updateListados", UpdateAdminJob.class);

				scheduler.start();
			} catch (Throwable t) {
				LOG.error("Fallo al inicializar la aplicacion", t);
				Main.showError(t);
			}
		}
	}

	public static void showError(Throwable t) {
		String errorCause = t.toString();
		try {
			LOG.fatal("Error al iniciar la aplicación", t);

			if (t instanceof TransactionException) {
				errorCause = Internacionalization
						.getString("Main.Error.database");
			}

			Object[] options = { "Ver más detalle", "Cerrar" };

			if (JOptionPane.showOptionDialog(null, "<html><p>"
					+ Internacionalization.getString("Main.Error") + ":</p><p>"
					+ errorCause + "</p><html>",
					Internacionalization.getString("Main.Error"),
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,
					options, options[1]) == 0) {
				final JFrame error = new JFrame();
				error.setAlwaysOnTop(true);
				error.setBackground(Color.WHITE);
				JTextArea area = new JTextArea();
				area.setEditable(false);
				area.setText(getStackTrace(t));
				error.add(new JScrollPane(area), BorderLayout.CENTER);
				JButton close = new JButton("Cerrar");
				close.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						error.dispose();
						System.exit(1);
					}
				});
				error.add(close, BorderLayout.SOUTH);
				error.pack();
				error.setPreferredSize(new Dimension(500, 500));
				error.setMaximumSize(new Dimension(500, 500));
				error.setVisible(true);
			} else {
				System.exit(1);
			}

		} catch (Throwable t1) {
			LOG.fatal(t1);
			JOptionPane.showMessageDialog(null, "<html><p>"
					+ "Error al arrancar la aplicación:" + ":</p><p>"
					+ errorCause + "</p><html>", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);

		}
	}

	private static String getStackTrace(Throwable t) {
		if (t == null) {
			return "Desconocido";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}
