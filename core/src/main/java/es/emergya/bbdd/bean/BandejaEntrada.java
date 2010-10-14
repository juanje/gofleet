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
package es.emergya.bbdd.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;

@Entity
@Table(name = "bandeja_entrada")
public class BandejaEntrada extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -6255895098255580761L;

	@Id
	@SequenceGenerator(name = "X_BANDEJA_ENTRADA", sequenceName = "bandeja_entrada_x_bandeja_entrada_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_BANDEJA_ENTRADA")
	@Column(name = "x_bandeja_entrada", unique = true, nullable = false)
	private Long id;
	@Column(name = "origen")
	private String origen;
	@Column(name = "datagrama_tetra")
	private String datagramaTetra;
	@Column(name = "marca_temporal")
	private Date marcaTemporal;
	@Column(name = "procesado")
	private boolean procesado;

	public BandejaEntrada() {
	}

	public boolean isProcesado() {
		return this.procesado;
	}

	public void setProcesado(boolean procesado) {
		this.procesado = procesado;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDatagramaTetra() {
		return this.datagramaTetra;
	}

	public void setDatagramaTetra(String datagramaTetra) {
		this.datagramaTetra = datagramaTetra;
	}

	public Date getMarcaTemporal() {
		return this.marcaTemporal;
	}

	public void setMarcaTemporal(Date marcaTemporal) {
		this.marcaTemporal = marcaTemporal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime
				* ((datagramaTetra == null) ? 0 : datagramaTetra.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((marcaTemporal == null) ? 0 : marcaTemporal.hashCode());
		result = prime * result + ((origen == null) ? 0 : origen.hashCode());
		result = prime * result + (procesado ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		BandejaEntrada other = (BandejaEntrada) obj;
		if (datagramaTetra == null) {
			if (other.datagramaTetra != null)
				return false;
		} else if (!datagramaTetra.equals(other.datagramaTetra))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (marcaTemporal == null) {
			if (other.marcaTemporal != null)
				return false;
		} else if (!marcaTemporal.equals(other.marcaTemporal))
			return false;
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		if (procesado != other.procesado)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BandejaEntrada [datagramaTetra=" + datagramaTetra + ", id="
				+ id + ", marcaTemporal=" + marcaTemporal + ", origen="
				+ origen + ", procesado=" + procesado + "]";
	}

}
