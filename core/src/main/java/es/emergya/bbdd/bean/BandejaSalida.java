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
@Table(name = "bandeja_salida")
public class BandejaSalida extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -6255895093255580761L;

	@Id
	@SequenceGenerator(sequenceName = "bandeja_salida_x_bandeja_seq", name = "X_BANDEJA_SALIDA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_BANDEJA_SALIDA")
	@Column(name = "x_bandeja", unique = true, nullable = false)
	private Long id;
	@Column(name = "datagrama_tetra")
	private String datagramaTetra;
	@Column(name = "marca_temporal_tx")
	private Date marcaTemporal;
	@Column(name = "prioridad")
	private Integer prioridad;
	@Column(name = "destino")
	private String destino;
	@Column(name = "tipo")
	private Integer tipo;

	public BandejaSalida() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDatagramaTetra() {
		return datagramaTetra;
	}

	public void setDatagramaTetra(String datagramaTetra) {
		this.datagramaTetra = datagramaTetra;
	}

	public Date getMarcaTemporal() {
		return marcaTemporal;
	}

	public void setMarcaTemporal(Date marcaTemporal) {
		this.marcaTemporal = marcaTemporal;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * 1
				+ ((datagramaTetra == null) ? 0 : datagramaTetra.hashCode());
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((marcaTemporal == null) ? 0 : marcaTemporal.hashCode());
		result = prime * result
				+ ((prioridad == null) ? 0 : prioridad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		BandejaSalida other = (BandejaSalida) obj;
		if (datagramaTetra == null) {
			if (other.datagramaTetra != null)
				return false;
		} else if (!datagramaTetra.equals(other.datagramaTetra))
			return false;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
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
		if (prioridad == null) {
			if (other.prioridad != null)
				return false;
		} else if (!prioridad.equals(other.prioridad))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BandejaSalida [datagramaTetra=" + datagramaTetra + ", destino="
				+ destino + ", id=" + id + ", marcaTemporal=" + marcaTemporal
				+ ", prioridad=" + prioridad + "]";
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
