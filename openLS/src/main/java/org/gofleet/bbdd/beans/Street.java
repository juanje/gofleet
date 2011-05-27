/*
 * Copyright (C) 2011, Emergya (http://www.emergya.es)
 *
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
package org.gofleet.bbdd.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.appfuse.model.BaseObject;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.hibernatespatial.postgis.PostgisDialect;

import com.vividsolutions.jts.geom.Geometry;

@Entity
@Immutable
@Table(name = "ways")
public class Street extends BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 3718733072510002908L;
	@Id
	@Column(name = "gid", unique = true, nullable = false)
	private Long gid;
	@Column(name = "the_geom")
	@Type(type = "org.hibernatespatial.GeometryUserType")
	private Geometry the_geom;
	@Type(type = "java.lang.String")
	private String name;

	@Override
	public String toString() {
		return "Street [the_geom=" + the_geom + ", name=" + name + "]";
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public Geometry getThe_geom() {
		return the_geom;
	}

	public void setThe_geom(Geometry the_geom) {
		this.the_geom = the_geom;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Street() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gid == null) ? 0 : gid.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((the_geom == null) ? 0 : the_geom.hashCode());
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
		Street other = (Street) obj;
		if (gid == null) {
			if (other.gid != null)
				return false;
		} else if (!gid.equals(other.gid))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (the_geom == null) {
			if (other.the_geom != null)
				return false;
		} else if (!the_geom.equals(other.the_geom))
			return false;
		return true;
	}

}
