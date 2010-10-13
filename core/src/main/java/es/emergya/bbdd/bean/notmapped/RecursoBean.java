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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.emergya.bbdd.bean.notmapped;

import java.math.BigInteger;

/**
 *
 * @author jlrodriguez
 */
public class RecursoBean {
    private BigInteger id;
    private String subflota;
    private String identificador;
    private String tipoRecurso;
    private Boolean habilitado;
    private Integer dispositivo;

    /**
     * Get the value of dispositivo
     *
     * @return the value of dispositivo
     */
    public Integer getDispositivo() {
        return dispositivo;
    }

    /**
     * Set the value of dispositivo
     *
     * @param dispositivo new value of dispositivo
     */
    public void setDispositivo(Integer dispositivo) {
        this.dispositivo = dispositivo;
    }


    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getSubflota() {
        return subflota;
    }

    public void setSubflota(String subflota) {
        this.subflota = subflota;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public RecursoBean() {
    }

    public RecursoBean(BigInteger id, String subflota, String identificador, String tipoRecurso, Boolean habilitado, Integer dispositivo) {
        this.id = id;
        this.subflota = subflota;
        this.identificador = identificador;
        this.tipoRecurso = tipoRecurso;
        this.habilitado = habilitado;
        this.dispositivo = dispositivo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RecursoBean other = (RecursoBean) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.subflota == null) ? (other.subflota != null) : !this.subflota.equals(other.subflota)) {
            return false;
        }
        if ((this.identificador == null) ? (other.identificador != null) : !this.identificador.equals(other.identificador)) {
            return false;
        }
        if ((this.tipoRecurso == null) ? (other.tipoRecurso != null) : !this.tipoRecurso.equals(other.tipoRecurso)) {
            return false;
        }
        if (this.habilitado != other.habilitado && (this.habilitado == null || !this.habilitado.equals(other.habilitado))) {
            return false;
        }
        if (this.dispositivo != other.dispositivo && (this.dispositivo == null || !this.dispositivo.equals(other.dispositivo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.subflota != null ? this.subflota.hashCode() : 0);
        hash = 97 * hash + (this.identificador != null ? this.identificador.hashCode() : 0);
        hash = 97 * hash + (this.tipoRecurso != null ? this.tipoRecurso.hashCode() : 0);
        hash = 97 * hash + (this.habilitado != null ? this.habilitado.hashCode() : 0);
        hash = 97 * hash + (this.dispositivo != null ? this.dispositivo.hashCode() : 0);
        return hash;
    }

    

}
