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
package es.emergya.constants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.emergya.tools.ExtensionClassLoader;

/**
 * @author aromero Contains the constants for the logic layer
 */
public class LogicConstants {


    private static Properties p;
	private static final Log LOG = LogFactory.getLog(LogicConstants.class);
	private static final String constantsProperties = "/conf/constants.properties";
	
	
	
	static {
	
		LogicConstants.p = new Properties();

		try {
			ExtensionClassLoader ecl = new ExtensionClassLoader();
			InputStream is = ecl
					.getResourceAsStream(LogicConstants.constantsProperties);
			if (is == null)
				is = LogicConstants.class
						.getResourceAsStream(LogicConstants.constantsProperties);
			LogicConstants.p.load(is);
		} catch (FileNotFoundException fnf) {
			LogicConstants.LOG
					.error(
							"Fichero de configuarcion conf/contants.properties no encontrado",
							fnf);
		} catch (IOException e) {
			LogicConstants.LOG.error(e);
		} catch (Exception e) {
			LogicConstants.LOG.error(
					"NO se pudo cargar el fichero de propiedades", e);
		}
	}
	
	
	public static final int EF_TIMEOUT_LOGIN_CHECK = getInt("EF_TIMEOUT_LOGIN_CHECK", "120");
	public static final int MAX_CLIENTES_CONECTADOS = getInt("EF_CONTROL_CLIENTS", "5");
        static {
            LOG.info("EF_TIMEOUT_LOGIN_CHECK = " + EF_TIMEOUT_LOGIN_CHECK);
            LOG.info("EF_CONTROL_CLIENTS = " + MAX_CLIENTES_CONECTADOS);
        }





	public static String get(String string) {
		return LogicConstants.p.getProperty(string);
	}

	public static String get(String string, String defaultValue) {
		return LogicConstants.p.getProperty(string, defaultValue);
	}

	public static int getInt(String string) {
		String s = LogicConstants.p.getProperty(string);
		return Integer.valueOf(s);
	}
	public static int getInt(String string, String defaultValue) {
		String s = LogicConstants.p.getProperty(string, defaultValue);
		return Integer.valueOf(s);
	}
}
