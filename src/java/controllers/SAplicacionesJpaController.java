/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.exceptions.NonexistentEntityException;
import controllers.exceptions.PreexistingEntityException;
import entities.SAplicaciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import utils.LocalEntityManagerFactory;

/**
 *
 * @author David Gonzalez
 */
public class SAplicacionesJpaController implements Serializable {

    public SAplicacionesJpaController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Función que trae datos de un stored procedure para generar la lista
     * de elementos para un menú dinámico 
     * @param usuario Es el usuario del cual obtenemos su lista de elementos 
     * que despliega su menú.
     * @return Regresa una lista tipo SAplicaciones donde se almacenaron
     * que despliega su menú.
     */
    public List<SAplicaciones> traerListaAplicaciones(String usuario) {
        List<SAplicaciones> listaAplicaciones = new ArrayList<>();
        SAplicaciones aplicaciones = new SAplicaciones();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("stp_CargaMenu");
            storedProcedure.registerStoredProcedureParameter("usuario", String.class, ParameterMode.IN);
            storedProcedure.setParameter("usuario", usuario);
            storedProcedure.execute();

            List<Object[]> listaResultados = storedProcedure.getResultList();

            for (Object[] obj : listaResultados) {
                aplicaciones.setIdAplicacion(Integer.parseInt(obj[0].toString()));
                aplicaciones.setNombreAplicacion((obj[1].toString()));
                aplicaciones.setIcono(obj[2].toString());
                aplicaciones.setUrl(obj[3].toString());
                listaAplicaciones.add(aplicaciones);
                aplicaciones = new SAplicaciones();
            }

        } catch (Exception e) {
            Logger.getLogger(SAplicacionesJpaController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return listaAplicaciones;
    }

    public void create(SAplicaciones SAplicaciones) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(SAplicaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSAplicaciones(SAplicaciones.getIdAplicacion()) != null) {
                throw new PreexistingEntityException("SAplicaciones " + SAplicaciones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SAplicaciones SAplicaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAplicaciones = em.merge(SAplicaciones);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = SAplicaciones.getIdAplicacion();
                if (findSAplicaciones(id) == null) {
                    throw new NonexistentEntityException("The sAplicaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SAplicaciones SAplicaciones;
            try {
                SAplicaciones = em.getReference(SAplicaciones.class, id);
                SAplicaciones.getIdAplicacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The SAplicaciones with id " + id + " no longer exists.", enfe);
            }
            em.remove(SAplicaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SAplicaciones> findSAplicacionesEntities() {
        return findSAplicacionesEntities(true, -1, -1);
    }

    public List<SAplicaciones> findSAplicacionesEntities(int maxResults, int firstResult) {
        return findSAplicacionesEntities(false, maxResults, firstResult);
    }

    private List<SAplicaciones> findSAplicacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SAplicaciones.class));
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

    public SAplicaciones findSAplicaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SAplicaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSAplicacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SAplicaciones> rt = cq.from(SAplicaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
