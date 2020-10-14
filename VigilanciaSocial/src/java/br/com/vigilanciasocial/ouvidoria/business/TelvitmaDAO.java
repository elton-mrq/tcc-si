/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Telvitma;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Vitima;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Elton
 */
public class TelvitmaDAO implements Serializable {

    public TelvitmaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telvitma telvitma) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vitima idVitima = telvitma.getIdVitima();
            if (idVitima != null) {
                idVitima = em.getReference(idVitima.getClass(), idVitima.getIdVitima());
                telvitma.setIdVitima(idVitima);
            }
            em.persist(telvitma);
            if (idVitima != null) {
                idVitima.getTelvitmaList().add(telvitma);
                idVitima = em.merge(idVitima);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telvitma telvitma) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telvitma persistentTelvitma = em.find(Telvitma.class, telvitma.getIdTelVitma());
            Vitima idVitimaOld = persistentTelvitma.getIdVitima();
            Vitima idVitimaNew = telvitma.getIdVitima();
            if (idVitimaNew != null) {
                idVitimaNew = em.getReference(idVitimaNew.getClass(), idVitimaNew.getIdVitima());
                telvitma.setIdVitima(idVitimaNew);
            }
            telvitma = em.merge(telvitma);
            if (idVitimaOld != null && !idVitimaOld.equals(idVitimaNew)) {
                idVitimaOld.getTelvitmaList().remove(telvitma);
                idVitimaOld = em.merge(idVitimaOld);
            }
            if (idVitimaNew != null && !idVitimaNew.equals(idVitimaOld)) {
                idVitimaNew.getTelvitmaList().add(telvitma);
                idVitimaNew = em.merge(idVitimaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telvitma.getIdTelVitma();
                if (findTelvitma(id) == null) {
                    throw new NonexistentEntityException("The telvitma with id " + id + " no longer exists.");
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
            Telvitma telvitma;
            try {
                telvitma = em.getReference(Telvitma.class, id);
                telvitma.getIdTelVitma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telvitma with id " + id + " no longer exists.", enfe);
            }
            Vitima idVitima = telvitma.getIdVitima();
            if (idVitima != null) {
                idVitima.getTelvitmaList().remove(telvitma);
                idVitima = em.merge(idVitima);
            }
            em.remove(telvitma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telvitma> findTelvitmaEntities() {
        return findTelvitmaEntities(true, -1, -1);
    }

    public List<Telvitma> findTelvitmaEntities(int maxResults, int firstResult) {
        return findTelvitmaEntities(false, maxResults, firstResult);
    }

    private List<Telvitma> findTelvitmaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Telvitma as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Telvitma findTelvitma(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telvitma.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelvitmaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Telvitma as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
       public List<Telvitma> telefones(Vitima v){
       
        EntityManager em = getEntityManager();
        try{
        Query q = em.createQuery("Select t from Telvitma t where t.idVitima = :vitima");
        q.setParameter("vitima", v);
        List<Telvitma> tel =  q.getResultList();
        return tel;
        }catch(NoResultException e){
            return null;
        }
    }

    
}
