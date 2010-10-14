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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "capas_informacion")
public class CapaInformacion extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1314425902959104385L;
	@Id
	@SequenceGenerator(sequenceName = "capas_informacion_x_capa_informacion_seq", name = "X_CAPASINFORMACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_CAPASINFORMACION")
	@Column(name = "x_capa_informacion", unique = true, nullable = false)
	private Long id;
	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	@Column(name = "url", nullable = false, length = 1200)
	private String url = "";
	@Column(name = "url_visible", nullable = false, length = 1200)
	private String url_visible = "";
	@Column(name = "updated_at", updatable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date updatedAt;
	@Column(name = "opcional", nullable = false)
	private Boolean opcional;
	@Column(name = "habilitada", nullable = false)
	private Boolean habilitada;
	@Column(name = "orden", nullable = false, unique = true)
	private Integer orden;
	@Column(name = "info_adicional", length = 256)
	private String infoAdicional;
	@OneToMany(mappedBy = "capaInformacion", fetch = FetchType.LAZY)
	@OrderBy("orden")
	@Cascade( { org.hibernate.annotations.CascadeType.DELETE_ORPHAN,
			org.hibernate.annotations.CascadeType.PERSIST,
			org.hibernate.annotations.CascadeType.MERGE,
			org.hibernate.annotations.CascadeType.SAVE_UPDATE })
	private Set<Capa> capas;
	@OneToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_capa_informacion")
	@Cascade( { org.hibernate.annotations.CascadeType.DELETE })
	private Set<CapaInformacionUsuario> capasInformacion;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url.endsWith("?")) {
			url = url.substring(0, url.length() - 1);
		}
		if (!url.startsWith("http://") && !url.startsWith("ftp://")) {
			url = "http://" + url;
		}
		this.url = url;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(Boolean opcional) {
		this.opcional = opcional;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public CapaInformacion() {
	}

	@Override
	public String toString() {
		return getNombre();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((infoAdicional == null) ? 0 : infoAdicional.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((opcional != null && opcional) ? 1231 : 1237);
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CapaInformacion other = (CapaInformacion) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		if (updatedAt == null) {
			if (other.updatedAt != null) {
				return false;
			}
		} else if (!updatedAt.equals(other.updatedAt)) {
			return false;
		}
		return true;
	}

	public Set<CapaInformacionUsuario> getCapasInformacion() {
		return capasInformacion;
	}

	public void setCapasInformacion(Set<CapaInformacionUsuario> capasInformacion) {
		this.capasInformacion = capasInformacion;
	}

	public Set<Capa> getCapas() {
		return capas;
	}

	public void setCapas(Set<Capa> capas) {
		this.capas = capas;
	}

	public Boolean getHabilitada() {
		return habilitada;
	}

	public Boolean isHabilitada() {
		return habilitada;
	}

	public void setHabilitada(Boolean habilitada) {
		this.habilitada = habilitada;
	}

	public Boolean getOpcional() {
		return opcional;
	}

	public String getUrl_visible() {
		return url_visible;
	}

	public void setUrl_visible(String urlVisible) {
		url_visible = urlVisible;
	}
}
