/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Endvitima;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Vitima;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Elton
 */
public class EndvitimaDAO implements Serializable {

    public EndvitimaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endvitima endvitima) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Vitima idVitimaOrphanCheck = endvitima.getIdVitima();
        if (idVitimaOrphanCheck != null) {
            Endvitima oldEndvitimaOfIdVitima = idVitimaOrphanCheck.getEndvitima();
            if (oldEndvitimaOfIdVitima != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Vitima " + idVitimaOrphanCheck + " already has an item of type Endvitima whose idVitima column cannot be null. Please make another selection for the idVitima field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vitima idVitima = endvitima.getIdVitima();
            if (idVitima != null) {
                idVitima = em.getReference(idVitima.getClass(), idVitima.getIdVitima());
                endvitima.setIdVitima(idVitima);
            }
            em.persist(endvitima);
            if (idVitima != null) {
                idVitima.setEndvitima(endvitima);
                idVitima = em.merge(idVitima);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endvitima endvitima) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endvitima persistentEndvitima = em.find(Endvitima.class, endvitima.getIdEndVitima());
            Vitima idVitimaOld = persistentEndvitima.getIdVitima();
            Vitima idVitimaNew = endvitima.getIdVitima();
            List<String> illegalOrphanMessages = null;
            if (idVitimaNew != null && !idVitimaNew.equals(idVitimaOld)) {
                Endvitima oldEndvitimaOfIdVitima = idVitimaNew.getEndvitima();
                if (oldEndvitimaOfIdVitima != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Vitima " + idVitimaNew + " already has an item of type Endvitima whose idVitima column cannot be null. Please make another selection for the idVitima field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idVitimaNew != null) {
                idVitimaNew = em.getReference(idVitimaNew.getClass(), idVitimaNew.getIdVitima());
                endvitima.setIdVitima(idVitimaNew);
            }
            endvitima = em.merge(endvitima);
            if (idVitimaOld != null && !idVitimaOld.equals(idVitimaNew)) {
                idVitimaOld.setEndvitima(null);
                idVitimaOld = em.merge(idVitimaOld);
            }
            if (idVitimaNew != null && !idVitimaNew.equals(idVitimaOld)) {
                idVitimaNew.setEndvitima(endvitima);
                idVitimaNew = em.merge(idVitimaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endvitima.getIdEndVitima();
                if (findEndvitima(id) == null) {
                    throw new NonexistentEntityException("The endvitima with id " + id + " no longer exists.");
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
            Endvitima endvitima;
            try {
                endvitima = em.getReference(Endvitima.class, id);
                endvitima.getIdEndVitima();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endvitima with id " + id + " no longer exists.", enfe);
            }
            Vitima idVitima = endvitima.getIdVitima();
            if (idVitima != null) {
                idVitima.setEndvitima(null);
                idVitima = em.merge(idVitima);
            }
            em.remove(endvitima);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endvitima> findEndvitimaEntities() {
        return findEndvitimaEntities(true, -1, -1);
    }

    public List<Endvitima> findEndvitimaEntities(int maxResults, int firstResult) {
        return findEndvitimaEntities(false, maxResults, firstResult);
    }

    private List<Endvitima> findEndvitimaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Endvitima as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Endvitima findEndvitima(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endvitima.class, id);
        } finally {
            em.close();
        }
    }

    public int getEndvitimaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Endvitima as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public Endvitima getEndVitima(Vitima v) {

        EntityManager em = getEntityManager();

        try {
            Query q = em.createQuery("Select e from Endvitima e where e.idVitima = :vitima");
            q.setParameter("vitima", v);
            Endvitima end = (Endvitima) q.getSingleResult();
            return end;
        } catch (NoResultException e) {
            Endvitima end = null;
            return end;
        }
    }

    
}
