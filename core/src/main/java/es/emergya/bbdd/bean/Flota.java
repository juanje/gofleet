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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "flotas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, include = "non-lazy")
public class Flota extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 3718733072570002908L;
	@Id
	@SequenceGenerator(sequenceName = "flotas_x_flota_seq", name = "X_FLOTA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_FLOTA")
	@Column(name = "x_flota", unique = true, nullable = false)
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "juego_iconos")
	private String juegoIconos;
	@Column(name = "info_adicional")
	private String infoAdicional;
	@Column(name = "habilitado")
	private Boolean habilitada;
	@Column(name = "updated_at")
	private Date updatedAt;
	@OneToMany(mappedBy = "flotas", fetch = FetchType.LAZY)
	private Set<Recurso> recurso;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE,
			org.hibernate.annotations.CascadeType.PERSIST })
	@JoinTable(name = "ROLES_X_FLOTAS", joinColumns = @JoinColumn(name = "X_FLOTA"), inverseJoinColumns = @JoinColumn(name = "X_ROL"))
	private Set<Rol> roles;

	public Flota() {
		super();
		habilitada = true;
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

	public String getJuegoIconos() {
		return juegoIconos;
	}

	public void setJuegoIconos(String juegoIconos) {
		this.juegoIconos = juegoIconos;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((infoAdicional == null) ? 0 : infoAdicional.hashCode());
		result = prime * result
				+ ((juegoIconos == null) ? 0 : juegoIconos.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Flota))
			return false;
		Flota other = (Flota) obj;
		if (infoAdicional == null) {
			if (other.infoAdicional != null)
				return false;
		} else if (!infoAdicional.equals(other.infoAdicional))
			return false;
		if (juegoIconos == null) {
			if (other.juegoIconos != null)
				return false;
		} else if (!juegoIconos.equals(other.juegoIconos))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getNombre();
	}

	public Set<Recurso> getRecurso() {
		return recurso;
	}

	public void setRecurso(Set<Recurso> recurso) {
		this.recurso = recurso;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public Boolean getHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

}
