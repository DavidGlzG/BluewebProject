/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.CCiudad;
import entities.CDistribuidor;
import entities.HActivacion;
import entities.HActivacion_;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import utils.LocalEntityManagerFactory;

/**
 *
 * @author David Gonzalez
 */
public class HActivacionJpaController implements Serializable {

    public HActivacionJpaController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Funcion una lista de datos tipo HActivacion en base a un rango de fechas. 
     * @param fechaInicio Es la fecha de inicio para el rango de busqueda.
     * @param fechaFinal Es la fecha final para el rango de busqueda.
     * @return Se regresa una lista tipo HActivacion con todos los datos obtenidos de 
     * la consulta a la base de datos por rango de fechas en la columna FECHA_PETICION.
     */
    public List<HActivacion> trarReporteHActivacion(Date fechaInicio, Date fechaFinal) {
        List<HActivacion> listaHActivacion = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<HActivacion> cq = cb.createQuery(HActivacion.class);
            Root<HActivacion> hActivacion = cq.from(HActivacion.class);
            Predicate fecha = cb.between(hActivacion.get("fechaPeticion"), fechaInicio, fechaFinal);
            cq.where(fecha);
            listaHActivacion = em.createQuery(cq).getResultList();

        } catch (Exception e) {
            Logger.getLogger(HActivacionJpaController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return listaHActivacion;
    }

    public void create(HActivacion HActivacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CCiudad idCiudad = HActivacion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getIdCiudad());
                HActivacion.setIdCiudad(idCiudad);
            }
            em.persist(HActivacion);
            if (idCiudad != null) {
                idCiudad.getHActivacionCollection().add(HActivacion);
                idCiudad = em.merge(idCiudad);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHActivacion(HActivacion.getId()) != null) {
                throw new PreexistingEntityException("HActivacion " + HActivacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HActivacion HActivacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HActivacion persistentHActivacion = em.find(HActivacion.class, HActivacion.getId());
            CCiudad idCiudadOld = persistentHActivacion.getIdCiudad();
            CCiudad idCiudadNew = HActivacion.getIdCiudad();
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getIdCiudad());
                HActivacion.setIdCiudad(idCiudadNew);
            }
            HActivacion = em.merge(HActivacion);
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getHActivacionCollection().remove(HActivacion);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getHActivacionCollection().add(HActivacion);
                idCiudadNew = em.merge(idCiudadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = HActivacion.getId();
                if (findHActivacion(id) == null) {
                    throw new NonexistentEntityException("The hActivacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HActivacion HActivacion;
            try {
                HActivacion = em.getReference(HActivacion.class, id);
                HActivacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The HActivacion with id " + id + " no longer exists.", enfe);
            }
            CCiudad idCiudad = HActivacion.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getHActivacionCollection().remove(HActivacion);
                idCiudad = em.merge(idCiudad);
            }
            em.remove(HActivacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HActivacion> findHActivacionEntities() {
        return findHActivacionEntities(true, -1, -1);
    }

    public List<HActivacion> findHActivacionEntities(int maxResults, int firstResult) {
        return findHActivacionEntities(false, maxResults, firstResult);
    }

    private List<HActivacion> findHActivacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HActivacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public HActivacion findHActivacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HActivacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHActivacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HActivacion> rt = cq.from(HActivacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
