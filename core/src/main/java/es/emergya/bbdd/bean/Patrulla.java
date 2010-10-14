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

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.appfuse.model.BaseObject;

@Entity
@Table(name = "patrullas")
public class Patrulla extends BaseObject implements java.io.Serializable {

    private static final long serialVersionUID = 3718703072510002908L;
    @Id
    @SequenceGenerator(sequenceName = "patrullas_x_patrulla_seq", name = "X_PATRULLA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "X_PATRULLA")
    @Column(name = "x_patrulla", unique = true, nullable = false)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "info_adicional")
    private String infoAdicional;
    @Column(name = "updated_at")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updatedAt;
    @OneToMany(mappedBy = "patrullas", fetch = FetchType.LAZY)
    private Set<Recurso> recursos;

    public Patrulla() {
        super();
        updatedAt = Calendar.getInstance().getTime();
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

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result
                + ( ( infoAdicional == null ) ? 0 : infoAdicional.hashCode() );
        result = prime * result + ( ( nombre == null ) ? 0 : nombre.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!( obj instanceof Patrulla )) {
            return false;
        }
        Patrulla other = (Patrulla) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (infoAdicional == null) {
            if (other.infoAdicional != null) {
                return false;
            }
        } else if (!infoAdicional.equals(other.infoAdicional)) {
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

    @Override
    public String toString() {
        return nombre;
    }

    public Set<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        this.recursos = recursos;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
