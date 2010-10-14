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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Cascade;


@Entity
@Table(name = "usuarios")
public class Usuario extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 3718703072511002908L;
	@Id
	@SequenceGenerator(sequenceName = "usuarios_x_usuarios_seq", name = "X_USUARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_USUARIO")
	@Column(name = "x_usuarios", unique = true, nullable = false)
	private Long id;
	@Column(name = "nombre_usuario")
	private String nombreUsuario;
	@Column(name = "password")
	private String password;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "habilitado")
	private Boolean habilitado;
	@Column(name = "administrador")
	private Boolean administrador;
	@Column(name = "vehiculos_visibles")
	private Boolean vehiculosVisibles;
	@Column(name = "personas_visibles")
	private Boolean personasVisibles;
	@Column(name = "incidencias_visibles")
	private Boolean incidenciasVisibles;
	@Column(name = "updated_at")
	private Date updatedAt;
	@Column(name = "info_adicional")
	private String infoAdicional;
	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_roles")
	private Rol roles;
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "fk_usuarios")
	private Set<CapaInformacionUsuario> capasInformacion;
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
	@Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<ClienteConectado> clientesConectados;

	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombreApellidos() {
		return nombre;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombre = nombreApellidos;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Boolean getVehiculosVisibles() {
		return vehiculosVisibles;
	}

	public void setVehiculosVisibles(Boolean vehiculosVisibles) {
		this.vehiculosVisibles = vehiculosVisibles;
	}

	public Boolean getPersonasVisibles() {
		return personasVisibles;
	}

	public void setPersonasVisibles(Boolean personasVisibles) {
		this.personasVisibles = personasVisibles;
	}

	public Boolean getIncidenciasVisibles() {
		return incidenciasVisibles;
	}

	public void setIncidenciasVisibles(Boolean incidenciasVisibles) {
		this.incidenciasVisibles = incidenciasVisibles;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public Rol getRoles() {
		return roles;
	}

	public void setRoles(Rol roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Usuario [administrador=" + administrador + ", habilitado="
				+ habilitado + ", id=" + id + ", incidenciasVisibles="
				+ incidenciasVisibles + ", infoAdicional=" + infoAdicional
				+ ", password=" + password + ", personasVisibles="
				+ personasVisibles + ", updatedAt=" + updatedAt
				+ ", vehiculosVisibles=" + vehiculosVisibles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result
				+ ((administrador == null) ? 0 : administrador.hashCode());
		result = prime * result
				+ ((habilitado == null) ? 0 : habilitado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((incidenciasVisibles == null) ? 0 : incidenciasVisibles
						.hashCode());
		result = prime * result
				+ ((infoAdicional == null) ? 0 : infoAdicional.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((nombreUsuario == null) ? 0 : nombreUsuario.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((personasVisibles == null) ? 0 : personasVisibles.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime
				* result
				+ ((vehiculosVisibles == null) ? 0 : vehiculosVisibles
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		if (administrador == null) {
			if (other.administrador != null)
				return false;
		} else if (!administrador.equals(other.administrador))
			return false;
		if (habilitado == null) {
			if (other.habilitado != null)
				return false;
		} else if (!habilitado.equals(other.habilitado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (incidenciasVisibles == null) {
			if (other.incidenciasVisibles != null)
				return false;
		} else if (!incidenciasVisibles.equals(other.incidenciasVisibles))
			return false;
		if (infoAdicional == null) {
			if (other.infoAdicional != null)
				return false;
		} else if (!infoAdicional.equals(other.infoAdicional))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (nombreUsuario == null) {
			if (other.nombreUsuario != null)
				return false;
		} else if (!nombreUsuario.equals(other.nombreUsuario))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (personasVisibles == null) {
			if (other.personasVisibles != null)
				return false;
		} else if (!personasVisibles.equals(other.personasVisibles))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		if (vehiculosVisibles == null) {
			if (other.vehiculosVisibles != null)
				return false;
		} else if (!vehiculosVisibles.equals(other.vehiculosVisibles))
			return false;
		return true;
	}

	public Set<CapaInformacionUsuario> getCapasInformacion() {
		return capasInformacion;
	}

	public void setCapasInformacion(Set<CapaInformacionUsuario> capasInformacion) {
		this.capasInformacion = capasInformacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Set<ClienteConectado> getClientesConectados() {
		return clientesConectados;
	}

	public void setClientesConectados(Set<ClienteConectado> clientesConectados) {
		this.clientesConectados = clientesConectados;
	}

}
