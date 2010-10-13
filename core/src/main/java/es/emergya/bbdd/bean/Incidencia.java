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

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;


@Entity
@Table(name = "incidencias")
public class Incidencia extends BaseObject implements java.io.Serializable {
	private static final long serialVersionUID = 3718733072570002908L;
	@Id
	@SequenceGenerator(sequenceName = "incidencias_x_incidencia_seq", name = "X_INCIDENCIA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_INCIDENCIA")
	@Column(name = "x_incidencia", unique = true, nullable = false)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_estado", nullable = false)
	private EstadoIncidencia estado;
	@Column(name = "prioridad", nullable = false)
	private Integer prioridad;
	@Column(name = "titulo", length = 100, nullable = false)
	private String titulo;
	@Column(name = "fecha_creacion", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	@Column(name = "fecha_cierre")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCierre;
	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry geometria;

	public Geometry getGeometria() {
		return geometria;
	}

	public void setGeometria(Geometry geometria) {
		this.geometria = geometria;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return titulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result
				+ ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Incidencia other = (Incidencia) obj;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoIncidencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public CategoriaIncidencia getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaIncidencia categoria) {
		this.categoria = categoria;
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_usuario", nullable = false)
	private Usuario creador;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_categoria", nullable = false)
	private CategoriaIncidencia categoria;
	@OneToMany(mappedBy = "incidencias", fetch = FetchType.LAZY)
	private Set<Recurso> recursos;

	public Incidencia() {
		this.setFechaCreacion(Calendar.getInstance().getTime());
	}
}
