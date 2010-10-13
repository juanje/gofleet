/*
 * Copyright (C) 2010, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:jlrodriguez@emergya.es">Juan Luís Rodríguez</a>
 * @author <a href="mailto:marias@emergya.es">María Arias</a>
 * @author <a href="mailto:aromero@emergya.es">Alejandro Romero Casado</a>
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
package es.emergya.comunications.exceptions;

import org.appfuse.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.actions.BandejaEntradaAdmin;
import es.emergya.bbdd.bean.BandejaEntrada;

/**
 * Exception related with the message processing
 * 
 * @author aromero
 * 
 */
public class MessageProcessingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3877087536574776397L;

	/**
	 * 
	 */
	public MessageProcessingException() {
	}

	private GenericDao<BandejaEntrada, Long> bandejaEntradaDAO;

	@Autowired
	public void setBandejaEntradaDAO(
			GenericDao<BandejaEntrada, Long> bandejaEntradaDAO) {
		this.bandejaEntradaDAO = bandejaEntradaDAO;
	}

	/**
	 * @param arg0
	 */
	public MessageProcessingException(String arg0, BandejaEntrada mensaje) {
		super(arg0);
		save(mensaje);
	}

	@Transactional
	private void save(BandejaEntrada mensaje) {
		if (mensaje != null) {
			mensaje.setProcesado(true);
			BandejaEntradaAdmin.saveOrUpdate(mensaje);
		}
	}

	/**
	 * @param arg0
	 */
	public MessageProcessingException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public MessageProcessingException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public MessageProcessingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
