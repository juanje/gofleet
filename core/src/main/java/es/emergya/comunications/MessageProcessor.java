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
/**
 * 
 */
package es.emergya.comunications;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.actions.BandejaEntradaAdmin;
import es.emergya.bbdd.bean.BandejaEntrada;
import es.emergya.bbdd.bean.TipoMensaje;
import es.emergya.bbdd.dao.TipoMensajeHome;
import es.emergya.comunications.exceptions.MessageProcessingException;
import es.emergya.utils.LogicConstants;
import es.emergya.utils.MyBeanFactory;

/**
 * @author marias
 * 
 */
public abstract class MessageProcessor {
	private static final Log log = LogFactory.getLog(MessageProcessor.class);

	private TipoMensajeHome tipoMensajeHome;

	public MessageProcessor() {
		tipoMensajeHome = (TipoMensajeHome) MyBeanFactory
				.getBean("tipoMensajeHome");
	}

	/**
	 * @param datragam
	 * @param origen
	 * @return
	 * @throws MessageProcessingException
	 */
	public void processingMessage(BandejaEntrada entrada)
			throws MessageProcessingException {

		// Divide el datagram
		String[] campos = StringUtils.splitPreserveAllTokens(entrada
				.getDatagramaTetra(), LogicConstants.FIELD_SEPARATOR);

		if (entrada.getOrigen() == null)
			throw new MessageProcessingException("No sabemos el origen",
					entrada);
		if (entrada.getDatagramaTetra() == null)
			throw new MessageProcessingException(
					"El mensaje no tenia contenido", entrada);

		/*
		 * Server format: Datagram structure: ID_MESSAGE (0) | ID_MESSAGE_TYPE
		 * (1) | MESSAGE BODY (2 .. n)
		 * 
		 * EME format: Datagram structure: ID_MESSAGE_TYPE (0) | MESSAGE BODY (1
		 * .. n)
		 */
		MessageProcessor.log.debug("Ha llegado un mensaje de "
				+ entrada.getOrigen() + ":" + entrada.getDatagramaTetra());
		int codeMessageType = getTipoMensaje(entrada, campos);
		try {
			processMessage(entrada, campos, codeMessageType);
		} catch (MessageProcessingException e) {
			log.error("Error al procesar el mensaje");
		}
		save(entrada);
	}

	private void save(BandejaEntrada entrada) {
		entrada.setProcesado(true);
		BandejaEntradaAdmin.saveOrUpdate(entrada);
	}

	@Transactional
	private Integer getTipoMensaje(BandejaEntrada entrada, String[] campos) {
		TipoMensaje tipo = null;
		try {
			Integer codigo = Integer.parseInt(campos[1]);
			tipo = tipoMensajeHome.getTipoByCode(codigo);
			if (tipo == null)
				save(entrada);
			else
				return tipo.getCodigo();
		} catch (NumberFormatException nfe) {
			save(entrada);
		}
		return -1;
	}

	/**
	 * 
	 * Se recomienda usar: switch (codeMessageType) { case CODE: ... break; }
	 * 
	 * @param entrada
	 * @param campos
	 * @param codeMessageType
	 */
	protected abstract void processMessage(BandejaEntrada entrada,
			String[] campos, int codeMessageType)
			throws MessageProcessingException;
}
