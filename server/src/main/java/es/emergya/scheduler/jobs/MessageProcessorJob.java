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
package es.emergya.scheduler.jobs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import es.emergya.bbdd.bean.BandejaEntrada;
import es.emergya.communications.MessageProcessor;
import es.emergya.consultas.BandejaEntradaConsultas;

/**
 * Search for new messages to show on the graphic interface
 * 
 * @author marias
 */
public class MessageProcessorJob implements StatefulJob {

	private static final Log log = LogFactory.getLog(MessageProcessorJob.class);

	/**
	 * Procesa los mensajes
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			MessageProcessor mp = new MessageProcessor();
			for (BandejaEntrada ba : BandejaEntradaConsultas.getNotProcessed()) {
				try {
					if (log.isDebugEnabled()) {
						log.debug("Procesando mensaje " + ba);
					}
					mp.processingMessage(ba);
				} catch (Throwable t) {
					log.error("Error al procesar un mensaje.", t);
				} finally {
					log.debug("Mensaje procesado " + ba);
				}
			}
		} catch (Throwable e) {
			log.error("Error al ejecutar el job de procesamiento de mensajes "
					+ "(¿al sacar los no procesados?)", e);
		}
	}
}
