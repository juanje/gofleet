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
package es.emergya.cliente.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import es.emergya.actions.Autenticacion;
import es.emergya.ui.base.ExitHandler;
import es.emergya.ui.base.LoginWindow;
import es.emergya.webservices.ServiceStub;
import es.emergya.webservices.ServiceStub.ActualizaLoginEF;
import es.emergya.webservices.ServiceStub.ActualizaLoginEFResponse;
import es.emergya.webservices.WSProvider;

/**
 * Search for new messages to show on the graphic interface
 * 
 * @author marias
 */
public class LoginJob implements StatefulJob {

	private static final Log log = LogFactory.getLog(LoginJob.class);

	public LoginJob() {
		super();
	}

	/**
	 * Update all the info in Scena, Notifes and Metar
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if (Autenticacion.getUsuario() != null)
			try {
				ServiceStub client = WSProvider.getServiceClient();
				ActualizaLoginEF param = new ActualizaLoginEF();
				param.setFsUid(Autenticacion.getId());
				ActualizaLoginEFResponse res = client.actualizaLoginEF(param);
				if (!res.get_return()) {
					ExitHandler eh = new ExitHandler();
					eh.actionPerformed(null);
					LoginWindow
							.showError("La sesión ha expirado. Vuelva a autenticarse.");
				}
			} catch (Throwable e) {
				log.error("Error en el LoginJob", e);
				ExitHandler eh = new ExitHandler();
				eh.actionPerformed(null);
				LoginWindow
						.showError("La sesión ha expirado. Vuelva a autenticarse.");
			}
	}
}
