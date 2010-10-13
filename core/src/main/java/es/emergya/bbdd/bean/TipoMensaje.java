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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;

@Entity
@Table(name = "tipo_mensaje")
public class TipoMensaje extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = -6258365093255580761L;

	@Id
	@SequenceGenerator(sequenceName = "tipo_mensaje_x_tipo_mensaje_seq", name = "X_TIPO_MENSAJE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_TIPO_MENSAJE")
	@Column(name = "x_tipo_mensaje", unique = true, nullable = false)
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "prioridad")
	private Integer prioridad;
	@Column(name = "codigo")
	private Integer codigo;
	@Column(name = "tipo_tetra")
	private Integer tipoTetra;

	public TipoMensaje() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((prioridad == null) ? 0 : prioridad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TipoMensaje))
			return false;
		TipoMensaje other = (TipoMensaje) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
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
		return "TipoMensaje [codigo=" + codigo + ", id=" + id + ", nombre="
				+ nombre + ", prioridad=" + prioridad + "]";
	}

	public Integer getTipoTetra() {
		return tipoTetra;
	}

	public void setTipoTetra(Integer tipoTetra) {
		this.tipoTetra = tipoTetra;
	}

}
