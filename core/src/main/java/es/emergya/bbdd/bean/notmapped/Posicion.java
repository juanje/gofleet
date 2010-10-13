/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.emergya.bbdd.bean.notmapped;

import java.util.Calendar;

/**
 * 
 * @author jlrodriguez
 */
public class Posicion {

	private double x;
	private double y;
	private String identificador;
	private Calendar marcaTemporal;

	public Calendar getMarcaTemporal() {
		return marcaTemporal;
	}

	public void setMarcaTemporal(Calendar marcaTemporal) {
		this.marcaTemporal = marcaTemporal;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String resource) {
		this.identificador = resource;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Posicion[" + "identificador=" + getIdentificador() + ", x="
				+ getX() + ", y=" + getY() + "]";
	}

}
