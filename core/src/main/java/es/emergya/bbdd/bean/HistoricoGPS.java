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
import javax.persistence.Temporal;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Table(name = "historico_gps")
public class HistoricoGPS extends BaseObject implements java.io.Serializable {

    private static final long serialVersionUID = 3718733072510002908L;
    @Id
    @SequenceGenerator(sequenceName = "historico_gps_x_historico_seq", name = "X_HISTORICOGPS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "X_HISTORICOGPS")
    @Column(name = "x_historico", unique = true, nullable = false)
    private Long id;
    @Column(name = "geom")
    @Type(type = "org.hibernatespatial.GeometryUserType")
    private Geometry geom;
    @Column(name = "pos_x")
    private Double posX;
    @Column(name = "pos_y")
    private Double posY;
    @Column(name = "marca_temporal")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date marcaTemporal;
    @Column(name = "recurso")
    private String recurso;
    @Column(name = "subflota")
    private String subflota;
    @Column(name = "tipo_recurso")
    private String tipoRecurso;

    public HistoricoGPS() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public Date getMarcaTemporal() {
        return marcaTemporal;
    }

    public void setMarcaTemporal(Date marcaTemporal) {
        this.marcaTemporal = marcaTemporal;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public String getSubflota() {
        return subflota;
    }

    public void setSubflota(String subflota) {
        this.subflota = subflota;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime;
        result = prime * result + ((geom == null) ? 0 : geom.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result
                + ((marcaTemporal == null) ? 0 : marcaTemporal.hashCode());
        result = prime * result + ((posX == null) ? 0 : posX.hashCode());
        result = prime * result + ((posY == null) ? 0 : posY.hashCode());
        result = prime * result + ((recurso == null) ? 0 : recurso.hashCode());
        result = prime * result
                + ((subflota == null) ? 0 : subflota.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistoricoGPS)) {
            return false;
        }
        HistoricoGPS other = (HistoricoGPS) obj;
        if (geom == null) {
            if (other.geom != null) {
                return false;
            }
        } else if (!geom.equals(other.geom)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (marcaTemporal == null) {
            if (other.marcaTemporal != null) {
                return false;
            }
        } else if (!marcaTemporal.equals(other.marcaTemporal)) {
            return false;
        }
        if (posX == null) {
            if (other.posX != null) {
                return false;
            }
        } else if (!posX.equals(other.posX)) {
            return false;
        }
        if (posY == null) {
            if (other.posY != null) {
                return false;
            }
        } else if (!posY.equals(other.posY)) {
            return false;
        }
        if (recurso == null) {
            if (other.recurso != null) {
                return false;
            }
        } else if (!recurso.equals(other.recurso)) {
            return false;
        }
        if (subflota == null) {
            if (other.subflota != null) {
                return false;
            }
        } else if (!subflota.equals(other.subflota)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistoricoGPS [geom=" + geom + ", id=" + id + ", marcaTemporal="
                + marcaTemporal + ", posX=" + posX + ", posY=" + posY
                + ", recurso=" + recurso + ", subflota=" + subflota + "]";
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }
}
