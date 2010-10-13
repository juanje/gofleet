package es.emergya.bbdd.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "street")
public class Street implements java.io.Serializable {

	private static final long serialVersionUID = 3718703072510002908L;
	@Id
	@SequenceGenerator(name = "x_street_gen", sequenceName = "street_x_street_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "x_street_gen")
	@Column(name = "x_street", unique = true, nullable = false)
	private Integer id;
	@Column(name = "tipoviaine")
	private Integer tipoviaine;
	@Column(name = "codigoine")
	private Integer codigoine;
	@Column(name = "nombreviaine")
	private String nombreviaine;
	@Column(name = "sentido")
	private String sentido;
	@Column(name = "estado")
	private String estado;
	@Column(name = "revest")
	private String revest;
	@Column(name = "length_")
	private Double length;
	@Column(name = "the_geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry geometria;
	@Column(name = "centroid")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry centroid;
	@OneToMany(mappedBy = "street")
	private Set<Incidencia> incidencias;

	public Street() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipoviaine() {
		return tipoviaine;
	}

	public void setTipoviaine(Integer tipoviaine) {
		this.tipoviaine = tipoviaine;
	}

	public Integer getCodigoine() {
		return codigoine;
	}

	public void setCodigoine(Integer codigoine) {
		this.codigoine = codigoine;
	}

	public String getNombreviaine() {
		return nombreviaine;
	}

	public void setNombreviaine(String nombreviaine) {
		this.nombreviaine = nombreviaine;
	}

	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRevest() {
		return revest;
	}

	public void setRevest(String revest) {
		this.revest = revest;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Geometry getGeometria() {
		return geometria;
	}

	public void setGeometria(Geometry geometria) {
		this.geometria = geometria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime;
		result = prime * result
				+ ((codigoine == null) ? 0 : codigoine.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((geometria == null) ? 0 : geometria.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result
				+ ((nombreviaine == null) ? 0 : nombreviaine.hashCode());
		result = prime * result + ((revest == null) ? 0 : revest.hashCode());
		result = prime * result + ((sentido == null) ? 0 : sentido.hashCode());
		result = prime * result
				+ ((tipoviaine == null) ? 0 : tipoviaine.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Street)) {
			return false;
		}
		Street other = (Street) obj;
		if (codigoine == null) {
			if (other.codigoine != null) {
				return false;
			}
		} else if (!codigoine.equals(other.codigoine)) {
			return false;
		}
		if (estado == null) {
			if (other.estado != null) {
				return false;
			}
		} else if (!estado.equals(other.estado)) {
			return false;
		}
		if (geometria == null) {
			if (other.geometria != null) {
				return false;
			}
		} else if (!geometria.equals(other.geometria)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (length == null) {
			if (other.length != null) {
				return false;
			}
		} else if (!length.equals(other.length)) {
			return false;
		}
		if (nombreviaine == null) {
			if (other.nombreviaine != null) {
				return false;
			}
		} else if (!nombreviaine.equals(other.nombreviaine)) {
			return false;
		}
		if (revest == null) {
			if (other.revest != null) {
				return false;
			}
		} else if (!revest.equals(other.revest)) {
			return false;
		}
		if (sentido == null) {
			if (other.sentido != null) {
				return false;
			}
		} else if (!sentido.equals(other.sentido)) {
			return false;
		}
		if (tipoviaine == null) {
			if (other.tipoviaine != null) {
				return false;
			}
		} else if (!tipoviaine.equals(other.tipoviaine)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Street [codigoine=" + codigoine + ", estado=" + estado
				+ ", geometria=" + geometria + ", id=" + id + ", length="
				+ length + ", nombreviaine=" + nombreviaine + ", revest="
				+ revest + ", sentido=" + sentido + ", tipoviaine="
				+ tipoviaine + "]";
	}

	public Set<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public Geometry getCentroid() {
		return centroid;
	}

	public void setCentroid(Geometry centroid) {
		this.centroid = centroid;
	}

	public void calculateCentroid() {
		if (this.geometria != null) {
			this.centroid = getCenter(this.geometria);
		}
	}

	private Point getCenter(Geometry geom) {
		if (geom == null) {
			return null;
		}
		if (geom instanceof Point) {
			return (Point) geom;
		}
		if (geom.getNumGeometries() < 2) {
			return (Point) geom.getInteriorPoint();
		}
		int n = Math.round(geom.getNumGeometries() / 2);
		return getCenter(geom.getGeometryN(n));
	}
}
