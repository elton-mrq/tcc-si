/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import br.com.vigilanciasocial.ouvidoria.entity.Telorgaoresp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class TelorgaorespDAO implements Serializable {

    public TelorgaorespDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telorgaoresp telorgaoresp) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orgaoresp idOrgResp = telorgaoresp.getIdOrgResp();
            if (idOrgResp != null) {
                idOrgResp = em.getReference(idOrgResp.getClass(), idOrgResp.getIdOrgaoResp());
                telorgaoresp.setIdOrgResp(idOrgResp);
            }
            em.persist(telorgaoresp);
            if (idOrgResp != null) {
                idOrgResp.getTelorgaorespList().add(telorgaoresp);
                idOrgResp = em.merge(idOrgResp);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telorgaoresp telorgaoresp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telorgaoresp persistentTelorgaoresp = em.find(Telorgaoresp.class, telorgaoresp.getIdTel());
            Orgaoresp idOrgRespOld = persistentTelorgaoresp.getIdOrgResp();
            Orgaoresp idOrgRespNew = telorgaoresp.getIdOrgResp();
            if (idOrgRespNew != null) {
                idOrgRespNew = em.getReference(idOrgRespNew.getClass(), idOrgRespNew.getIdOrgaoResp());
                telorgaoresp.setIdOrgResp(idOrgRespNew);
            }
            telorgaoresp = em.merge(telorgaoresp);
            if (idOrgRespOld != null && !idOrgRespOld.equals(idOrgRespNew)) {
                idOrgRespOld.getTelorgaorespList().remove(telorgaoresp);
                idOrgRespOld = em.merge(idOrgRespOld);
            }
            if (idOrgRespNew != null && !idOrgRespNew.equals(idOrgRespOld)) {
                idOrgRespNew.getTelorgaorespList().add(telorgaoresp);
                idOrgRespNew = em.merge(idOrgRespNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telorgaoresp.getIdTel();
                if (findTelorgaoresp(id) == null) {
                    throw new NonexistentEntityException("The telorgaoresp with id " + id + " no longer exists.");
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
            Telorgaoresp telorgaoresp;
            try {
                telorgaoresp = em.getReference(Telorgaoresp.class, id);
                telorgaoresp.getIdTel();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telorgaoresp with id " + id + " no longer exists.", enfe);
            }
            Orgaoresp idOrgResp = telorgaoresp.getIdOrgResp();
            if (idOrgResp != null) {
                idOrgResp.getTelorgaorespList().remove(telorgaoresp);
                idOrgResp = em.merge(idOrgResp);
            }
            em.remove(telorgaoresp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telorgaoresp> findTelorgaorespEntities() {
        return findTelorgaorespEntities(true, -1, -1);
    }

    public List<Telorgaoresp> findTelorgaorespEntities(int maxResults, int firstResult) {
        return findTelorgaorespEntities(false, maxResults, firstResult);
    }

    private List<Telorgaoresp> findTelorgaorespEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Telorgaoresp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Telorgaoresp findTelorgaoresp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telorgaoresp.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelorgaorespCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Telorgaoresp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Telorgaoresp> getfindTelorgaoresp(Orgaoresp idOrgao) {
        EntityManager em = getEntityManager();
        try {
           Query q = em.createQuery("Select t from Telorgaoresp t where t.idOrgResp = :id");
           q.setParameter("id", idOrgao);          
            List<Telorgaoresp> telefone = q.getResultList();
           return telefone;
        
        } finally {
            em.close();
        }
    
                
    }

    
}
