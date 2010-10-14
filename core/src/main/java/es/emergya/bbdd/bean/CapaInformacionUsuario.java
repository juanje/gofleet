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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.appfuse.model.BaseObject;


@Entity
@Table(name = "usuarios_x_capas_informacion")
public class CapaInformacionUsuario extends BaseObject implements
		java.io.Serializable {
	private static final long serialVersionUID = 1932630542150784130L;
	@Column(name = "visible_gps")
	private Boolean visibleGPS;
	@Column(name = "visible_historico")
	private Boolean visibleHistorico;
	@EmbeddedId
	private CapaInformacionUsuarioKey id;

	public CapaInformacionUsuario() {
		id = new CapaInformacionUsuarioKey();
	}

	public Boolean getVisibleGPS() {
		return visibleGPS;
	}

	public void setVisibleGPS(Boolean visibleGPS) {
		this.visibleGPS = visibleGPS;
	}

	public Boolean getVisibleHistorico() {
		return visibleHistorico;
	}

	public void setVisibleHistorico(Boolean visibleHistorico) {
		this.visibleHistorico = visibleHistorico;
	}

	public CapaInformacion getCapaInformacion() {
		return id.getCapaInformacion();
	}

	public void setCapaInformacion(CapaInformacion capaInformacion) {
		this.id.setCapaInformacion(capaInformacion);
	}

	public Usuario getUsuario() {
		return id.getUsuario();
	}

	public void setUsuario(Usuario usuario) {
		this.id.setUsuario(usuario);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime
				* result
				+ ((id.getCapaInformacion() == null) ? 0 : id
						.getCapaInformacion().hashCode());
		result = prime * result
				+ ((id.getUsuario() == null) ? 0 : id.getUsuario().hashCode());
		result = prime * result
				+ ((visibleGPS == null) ? 0 : visibleGPS.hashCode());
		result = prime
				* result
				+ ((visibleHistorico == null) ? 0 : visibleHistorico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CapaInformacionUsuario))
			return false;
		CapaInformacionUsuario other = (CapaInformacionUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (visibleGPS == null) {
			if (other.visibleGPS != null)
				return false;
		} else if (!visibleGPS.equals(other.visibleGPS))
			return false;
		if (visibleHistorico == null) {
			if (other.visibleHistorico != null)
				return false;
		} else if (!visibleHistorico.equals(other.visibleHistorico))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CapaInformacionUsuario [capaInformacion="
				+ id.getCapaInformacion() + ", usuario=" + id.getUsuario()
				+ ", visibleGPS=" + visibleGPS + ", visibleHistorico="
				+ visibleHistorico + "]";
	}

	public CapaInformacionUsuarioKey getId() {
		return id;
	}

	public void setId(CapaInformacionUsuarioKey id) {
		if (id == null)
			this.id = new CapaInformacionUsuarioKey();
		else
			this.id = id;
	};
}

class CapaInformacionUsuarioKey extends BaseObject implements
		java.io.Serializable {
	private static final long serialVersionUID = 19326305420784130L;
	@ManyToOne
	@JoinColumn(name = "fk_capa_informacion")
	private CapaInformacion capaInformacion;
	@ManyToOne
	@JoinColumn(name = "fk_usuarios")
	private Usuario usuario;

	public void setCapaInformacion(CapaInformacion capaInformacion) {
		this.capaInformacion = capaInformacion;
	}

	public CapaInformacion getCapaInformacion() {
		return capaInformacion;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result
				+ ((capaInformacion == null) ? 0 : capaInformacion.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CapaInformacionUsuarioKey))
			return false;
		CapaInformacionUsuarioKey other = (CapaInformacionUsuarioKey) obj;
		if (capaInformacion == null) {
			if (other.capaInformacion != null)
				return false;
		} else if (!capaInformacion.equals(other.capaInformacion))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CapaInformacionUsuarioKey [capaInformacion=" + capaInformacion
				+ ", usuario=" + usuario + "]";
	}

}