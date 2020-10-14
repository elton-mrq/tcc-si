/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Endorgaoresp;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class EndorgaorespDAO implements Serializable {

    public EndorgaorespDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Endorgaoresp endorgaoresp) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Orgaoresp idOrgRespOrphanCheck = endorgaoresp.getIdOrgResp();
        if (idOrgRespOrphanCheck != null) {
            Endorgaoresp oldEndorgaorespOfIdOrgResp = idOrgRespOrphanCheck.getEndorgaoresp();
            if (oldEndorgaorespOfIdOrgResp != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Orgaoresp " + idOrgRespOrphanCheck + " already has an item of type Endorgaoresp whose idOrgResp column cannot be null. Please make another selection for the idOrgResp field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orgaoresp idOrgResp = endorgaoresp.getIdOrgResp();
            if (idOrgResp != null) {
                idOrgResp = em.getReference(idOrgResp.getClass(), idOrgResp.getIdOrgaoResp());
                endorgaoresp.setIdOrgResp(idOrgResp);
            }
            em.persist(endorgaoresp);
            if (idOrgResp != null) {
                idOrgResp.setEndorgaoresp(endorgaoresp);
                idOrgResp = em.merge(idOrgResp);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Endorgaoresp endorgaoresp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endorgaoresp persistentEndorgaoresp = em.find(Endorgaoresp.class, endorgaoresp.getIdEndOrgaoResp());
            Orgaoresp idOrgRespOld = persistentEndorgaoresp.getIdOrgResp();
            Orgaoresp idOrgRespNew = endorgaoresp.getIdOrgResp();
            List<String> illegalOrphanMessages = null;
            if (idOrgRespNew != null && !idOrgRespNew.equals(idOrgRespOld)) {
                Endorgaoresp oldEndorgaorespOfIdOrgResp = idOrgRespNew.getEndorgaoresp();
                if (oldEndorgaorespOfIdOrgResp != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Orgaoresp " + idOrgRespNew + " already has an item of type Endorgaoresp whose idOrgResp column cannot be null. Please make another selection for the idOrgResp field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idOrgRespNew != null) {
                idOrgRespNew = em.getReference(idOrgRespNew.getClass(), idOrgRespNew.getIdOrgaoResp());
                endorgaoresp.setIdOrgResp(idOrgRespNew);
            }
            endorgaoresp = em.merge(endorgaoresp);
            if (idOrgRespOld != null && !idOrgRespOld.equals(idOrgRespNew)) {
                idOrgRespOld.setEndorgaoresp(null);
                idOrgRespOld = em.merge(idOrgRespOld);
            }
            if (idOrgRespNew != null && !idOrgRespNew.equals(idOrgRespOld)) {
                idOrgRespNew.setEndorgaoresp(endorgaoresp);
                idOrgRespNew = em.merge(idOrgRespNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = endorgaoresp.getIdEndOrgaoResp();
                if (findEndorgaoresp(id) == null) {
                    throw new NonexistentEntityException("The endorgaoresp with id " + id + " no longer exists.");
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
            Endorgaoresp endorgaoresp;
            try {
                endorgaoresp = em.getReference(Endorgaoresp.class, id);
                endorgaoresp.getIdEndOrgaoResp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The endorgaoresp with id " + id + " no longer exists.", enfe);
            }
            Orgaoresp idOrgResp = endorgaoresp.getIdOrgResp();
            if (idOrgResp != null) {
                idOrgResp.setEndorgaoresp(null);
                idOrgResp = em.merge(idOrgResp);
            }
            em.remove(endorgaoresp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Endorgaoresp> findEndorgaorespEntities() {
        return findEndorgaorespEntities(true, -1, -1);
    }

    public List<Endorgaoresp> findEndorgaorespEntities(int maxResults, int firstResult) {
        return findEndorgaorespEntities(false, maxResults, firstResult);
    }

    private List<Endorgaoresp> findEndorgaorespEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Endorgaoresp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Endorgaoresp findEndorgaoresp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Endorgaoresp.class, id);
        } finally {
            em.close();
        }
    }

    public int getEndorgaorespCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Endorgaoresp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Endorgaoresp getEndOrgao(Orgaoresp org) {

        EntityManager em = getEntityManager();

        try {
            Query q = em.createQuery("Select e from Endorgaoresp e where e.idOrgResp = :orgao");
            q.setParameter("orgao", org);
            Endorgaoresp endereco = (Endorgaoresp) q.getSingleResult();
            return endereco;
        } catch (Exception e) {
            Endorgaoresp endereco = null;
            return endereco;
        } finally {
            em.close();
        }

    }

    
}
