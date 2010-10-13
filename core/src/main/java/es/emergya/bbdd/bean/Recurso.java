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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "recursos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recurso extends BaseObject implements java.io.Serializable {
	public static String PERSONA = "Per";
	public static String VEHICULO = "Veh";

	public static enum TIPO_TOSTRING {
		SIMPLE, SUBFLOTA, PATRULLA
	};

	@Transient
	private TIPO_TOSTRING tipoToString = TIPO_TOSTRING.SIMPLE;

	private static final long serialVersionUID = 3718703072510002908L;
	@Id
	@SequenceGenerator(sequenceName = "recursos_x_recurso_seq", name = "X_RECURSO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "X_RECURSO")
	@Column(name = "x_recurso", unique = true, nullable = false)
	private Long id;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "identificador")
	private String identificador;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "dispositivo")
	private Integer dispositivo;
	@Column(name = "habilitado")
	private Boolean habilitado;
	@Column(name = "mal_asignado")
	private Boolean malAsignado;
	@ManyToOne
	@JoinColumn(name = "fk_estado")
	private EstadoRecurso estadoEurocop;
	@Column(name = "info_adicional")
	private String infoAdicional;
	@Column(name = "gestor")
	private String gestor;
	@Column(name = "funciones_eurocop")
	private String funcionesEurocop;
	@Column(name = "fecha_cambio_eurocop")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaCambioEurocop;
	@Column(name = "updated_at")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date updatedAt;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patrulla_x_patrulla")
	private Patrulla patrullas;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "incidencia_x_incidencia")
	private Incidencia incidencias;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flota_x_flota")
	private Flota flotas;
	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_historico_gps")
	private HistoricoGPS historicoGps;

	@Transient
	public String idpattern = "";

	public Recurso() {
		super();
		updatedAt = Calendar.getInstance().getTime();
		malAsignado = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		if (tipo == null)
			return null;

		if (tipo.equals("veh"))
			return Recurso.VEHICULO;
		if (tipo.equals("per"))
			return Recurso.PERSONA;
		return tipo;
	}

	public void setTipo(String tipo) {
		if (tipo != null) {
			if (tipo.indexOf(Recurso.VEHICULO) == 0)
				tipo = "veh";
			if (tipo.indexOf(Recurso.PERSONA) == 0)
				tipo = "per";
		}
		this.tipo = tipo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Integer dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public EstadoRecurso getEstadoEurocop() {
		return estadoEurocop;
	}

	public void setEstadoEurocop(EstadoRecurso estadoEurocop) {
		this.estadoEurocop = estadoEurocop;
	}

	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public String getFuncionesEurocop() {
		return funcionesEurocop;
	}

	public void setFuncionesEurocop(String funcionesEurocop) {
		this.funcionesEurocop = funcionesEurocop;
	}

	public Date getFechaCambioEurocop() {
		return fechaCambioEurocop;
	}

	public void setFechaCambioEurocop(Date fechaCambioEurocop) {
		this.fechaCambioEurocop = fechaCambioEurocop;
	}

	public Patrulla getPatrullas() {
		return patrullas;
	}

	public void setPatrullas(Patrulla patrullas) {
		this.patrullas = patrullas;
	}

	public Incidencia getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Incidencia incidencias) {
		this.incidencias = incidencias;
	}

	public Flota getFlotas() {
		return flotas;
	}

	public void setFlotas(Flota flotas) {
		this.flotas = flotas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result
				+ ((dispositivo == null) ? 0 : dispositivo.hashCode());
		result = prime * result + ((gestor == null) ? 0 : gestor.hashCode());
		result = prime * result
				+ ((habilitado == null) ? 0 : habilitado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Recurso)) {
			return false;
		}
		Recurso other = (Recurso) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (identificador == null) {
			if (other.identificador != null) {
				return false;
			}
		} else if (!identificador.equals(other.identificador)) {
			return false;
		}
		if (nombre == null) {
			if (other.nombre != null) {
				return false;
			}
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		return true;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	@Override
	public String toString() {
		if (tipoToString == TIPO_TOSTRING.SIMPLE || tipoToString == null)
			return getNombre();
		if (tipoToString == TIPO_TOSTRING.PATRULLA
				&& this.getPatrullas() != null)
			return getNombre() + "(" + this.getPatrullas() + ")";
		if (tipoToString == TIPO_TOSTRING.SUBFLOTA && this.getFlotas() != null)
			return getNombre() + "(" + this.getFlotas() + ")";
		return getNombre();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getMalAsignado() {
		return malAsignado;
	}

	public void setMalAsignado(Boolean malAsignado) {
		this.malAsignado = malAsignado;
	}

	public HistoricoGPS getHistoricoGps() {
		return historicoGps;
	}

	public void setHistoricoGps(HistoricoGPS historicoGps) {
		this.historicoGps = historicoGps;
	}

	@Transient
	public String getTipoReal() {
		return this.tipo;
	}

	public void setTipoToString(TIPO_TOSTRING tipoToString) {
		this.tipoToString = tipoToString;
	}

	public TIPO_TOSTRING getTipoToString() {
		return tipoToString;
	}
}
