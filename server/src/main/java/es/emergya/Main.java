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

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.jmx.StatisticsService;
import org.quartz.JobListener;

import es.emergya.scheduler.CustomScheduler;
import es.emergya.scheduler.jobs.MessageProcessorJob;
import es.emergya.utils.MyBeanFactory;

/**
 * Main class.
 * 
 * @author jlrodriguez
 * 
 */
public final class Main {

	private static final int FREQUENCY = 2 * 1000;
	private static final org.apache.commons.logging.Log LOG = LogFactory
			.getLog(Main.class);

	/** Constructor is private to avoid creating objects. */
	private Main() {
	}

	/**
	 * Starts the app.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(final Thread t, final Throwable e) {
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
		}

		try {
			TimeZone.setDefault(TimeZone.getTimeZone("Europe/Madrid")); //$NON-NLS-1$
			new Initializer().run();
		} catch (Throwable t) {
			LOG.error("Fallo el SwingUtilities.invokeLater", t);
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

				final SessionFactory sessionFactory = (SessionFactory) MyBeanFactory
						.getBean("sessionFactory");
				final MBeanServer mbeanServer = ManagementFactory
						.getPlatformMBeanServer();
				final ObjectName on = new ObjectName(
						"Hibernate:type=statistics,application=appfuse");
				final StatisticsService mBean = new StatisticsService();
				mBean.setStatisticsEnabled(true);
				mBean.setSessionFactory(sessionFactory);
				mbeanServer.registerMBean(mBean, on);
			} catch (InstanceAlreadyExistsException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (MBeanRegistrationException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (NotCompliantMBeanException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (MalformedObjectNameException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (NullPointerException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
						ex);
			} catch (Exception e) {
				LOG.error(
						"Error registering Hibernate StatisticsService ["
								+ e.getMessage() + "]", e);
			}

			try {
				final CustomScheduler scheduler = new CustomScheduler();

				scheduler.addJob(FREQUENCY, "messageProcessorJob",
						MessageProcessorJob.class, null,
						new ArrayList<JobListener>(0));

				scheduler.start();
			} catch (Throwable e) {
				Main.LOG.error("No se pudo programar el job", e); //$NON-NLS-1$
			}
		}
	}
}
