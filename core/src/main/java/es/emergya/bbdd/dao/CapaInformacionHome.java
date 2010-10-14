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
package es.emergya.bbdd.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.appfuse.dao.hibernate.GenericDaoHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.emergya.actions.Autenticacion;
import es.emergya.bbdd.bean.Capa;
import es.emergya.bbdd.bean.CapaInformacion;
import es.emergya.bbdd.bean.CapaInformacionUsuario;
import es.emergya.utils.LogicConstants;

@Repository("capaInformacionHome")
public class CapaInformacionHome extends GenericDaoHibernate<CapaInformacion, Long> {

    public CapaInformacionHome() {
        super(CapaInformacion.class);
    }

    @Override
    public CapaInformacion get(Long id) {
        try {
            return super.get(id);
        }
        catch (Throwable t) {
            log.error("Estamos buscando un objeto que no existe", t);
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public Integer getTotal() {
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).setProjection(Projections.rowCount());
            return (Integer) criteria.uniqueResult();
        }
        catch (Throwable t) {
            log.error(t, t);
            return -1;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public List<CapaInformacion> getByFilter(CapaInformacion capaInfo) {
        List<CapaInformacion> res = new ArrayList<CapaInformacion>(0);
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(CapaInformacion.class);

            if (capaInfo.getInfoAdicional() != null) {
                criteria = criteria.add(Restrictions.ilike("infoAdicional",
                        LogicConstants.getGenericString(capaInfo.getInfoAdicional())));
            }

            if (capaInfo.getNombre() != null) {
                criteria = criteria.add(Restrictions.ilike("nombre",
                        LogicConstants.getGenericString(capaInfo.getNombre())));
            }

            if (capaInfo.getUrl() != null && capaInfo.getUrl().length() > 0) {
                criteria = criteria.add(Restrictions.ilike("url",
                        LogicConstants.getGenericString(capaInfo.getUrl())));
            }

            if (capaInfo.getOrden() != null) {
                criteria = criteria.add(Restrictions.eq("orden", capaInfo.getOrden()));
            }

            if (capaInfo.getHabilitada() != null) {
                criteria = criteria.add(Restrictions.eq("habilitada", capaInfo.getHabilitada()));
            }

            if (capaInfo.getOpcional() != null) {
                criteria = criteria.add(Restrictions.eq("opcional", capaInfo.getOpcional()));
            }

            criteria = criteria.addOrder(Order.asc("orden")).setCacheable(false).setResultTransformer(
                    Criteria.DISTINCT_ROOT_ENTITY);

            res = criteria.list();

            for (CapaInformacion c : res) {
                if (c != null) {
                    if (c.getCapas() != null) {
                        for (Capa cap : c.getCapas()) {
                            if (cap != null) {
                                cap.getId();
                            }
                        }
                    }
                }
            }

            if (log.isTraceEnabled()) {
                log.info("Sacamos de la base de datos las siguientes capas:");
                for (CapaInformacion capa : res) {
                    log.info(" " + capa.getOrden() + ".- " + capa.getNombre());
                }
            }
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
    public boolean saveOrUpdate(CapaInformacion p) {
        if (p == null) {
            return false;
        }

        CapaInformacion entity = null;
        try {
            Session currentSession = getSession();
            if (p.getId() != null && this.get(p.getId()) != null) {
                entity = (CapaInformacion) currentSession.merge(p);
            }

            if (entity == null) {
                entity = p;
            }

            if (p.getOrden() == null && entity.getOrden() == null) {
                entity.setOrden(getTotal() + 1);
            }

            currentSession.saveOrUpdate(entity);

            return true;
        }
        catch (Throwable t) {
            log.error(t, t);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
    public boolean delete(CapaInformacion r) {
        if (r == null || r.getId() == null) {
            return false;
        }
        try {
            this.remove(r.getId());

            return true;
        }
        catch (Throwable t) {
            log.error(t, t);
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
    public void removeUsuarios(CapaInformacion ci) {
        try {
            final Session currentSession = getSession();
            currentSession.clear();
            CapaInformacion r = this.get(ci.getId());
            if (r != null && r.getCapasInformacion() != null) {
                for (CapaInformacionUsuario capaUsuario : r.getCapasInformacion()) {
                    currentSession.delete(capaUsuario);
                }
            }
        }
        catch (Throwable t) {
            log.error(t, t);
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public boolean alreadyExists(String nombre) {
        Integer res = new Integer(-1);
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).setProjection(Projections.rowCount()).add(
                    Restrictions.ilike("nombre", nombre));
            Integer count = (Integer) criteria.uniqueResult();
            res = count.intValue();
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res != 0;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class)
    public void updateOrden() {
        try {
            List<CapaInformacion> capas = getByFilter(new CapaInformacion());
            int i = 1;
            for (CapaInformacion capa : capas) {
                capa.setOrden(i++);
                saveOrUpdate(capa);
            }
        }
        catch (Throwable t) {
            log.error(t, t);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public Calendar lastUpdated() {
        Calendar res = Calendar.getInstance();
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).setProjection(
                    Projections.max("updatedAt"));
            res.setTime((Date) criteria.uniqueResult());
        }
        catch (Throwable t) {
            log.error("Error al buscar la ultima actualizacion de capas" + t);
            return null;
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public List<CapaInformacion> getAll(boolean base, Boolean historico) {
        List<CapaInformacion> res = new LinkedList<CapaInformacion>();
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).addOrder(Order.desc("orden")).add(
                    Restrictions.eq("opcional", ( !base ))).setResultTransformer(
                    Criteria.DISTINCT_ROOT_ENTITY);

            if (!base) {
                if (historico != null) {
                    criteria = criteria.createCriteria("capasInformacion");
                    if (!historico) {
                        criteria.add(Restrictions.eq("visibleGPS", true));
                    } else {
                        criteria.add(Restrictions.eq("visibleHistorico", true));
                    }
                    criteria = criteria.add(Restrictions.sqlRestriction("{alias}.fk_usuarios = "
                            + Autenticacion.getUsuario().getId()));
                }
            }

            res = (List<CapaInformacion>) criteria.list();
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    @Override
    public List<CapaInformacion> getAll() {
        List<CapaInformacion> res = new LinkedList<CapaInformacion>();
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).setResultTransformer(
                    Criteria.DISTINCT_ROOT_ENTITY);
            res = (List<CapaInformacion>) criteria.list();
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res;
    }

    /**
     *
     * @return  devuelve todas las capas de información ordenadas por el campo orden
     * ascendentemente.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public List<CapaInformacion> getAllOrderedByOrden() {
        List<CapaInformacion> res = new LinkedList<CapaInformacion>();
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).setResultTransformer(
                    Criteria.DISTINCT_ROOT_ENTITY);
            criteria.addOrder(Order.asc("orden"));
            res = (List<CapaInformacion>) criteria.list();
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = Throwable.class)
    public CapaInformacion getByNombre(String nombre) {
        CapaInformacion res = null;
        try {
            Session currentSession = getSession();
            currentSession.clear();
            Criteria criteria = currentSession.createCriteria(
                    CapaInformacion.class).add(
                    Restrictions.ilike("nombre", nombre));
            res = (CapaInformacion) criteria.uniqueResult();
        }
        catch (Throwable t) {
            log.error(t, t);
        }
        return res;
    }
}
