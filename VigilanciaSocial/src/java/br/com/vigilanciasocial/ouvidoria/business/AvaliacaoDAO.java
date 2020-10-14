/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.PreexistingEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Avaliacao;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class AvaliacaoDAO implements Serializable {

    public AvaliacaoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Avaliacao avaliacao) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencia idOcorrencia = avaliacao.getIdOcorrencia();
            if (idOcorrencia != null) {
                idOcorrencia = em.getReference(idOcorrencia.getClass(), idOcorrencia.getIdOcorrencia());
                avaliacao.setIdOcorrencia(idOcorrencia);
            }
            em.persist(avaliacao);
            if (idOcorrencia != null) {
                idOcorrencia.getAvaliacaoList().add(avaliacao);
                idOcorrencia = em.merge(idOcorrencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvaliacao(avaliacao.getIdAvaliacao()) != null) {
                throw new PreexistingEntityException("Avaliacao " + avaliacao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Avaliacao avaliacao) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Avaliacao persistentAvaliacao = em.find(Avaliacao.class, avaliacao.getIdAvaliacao());
            Ocorrencia idOcorrenciaOld = persistentAvaliacao.getIdOcorrencia();
            Ocorrencia idOcorrenciaNew = avaliacao.getIdOcorrencia();
            if (idOcorrenciaNew != null) {
                idOcorrenciaNew = em.getReference(idOcorrenciaNew.getClass(), idOcorrenciaNew.getIdOcorrencia());
                avaliacao.setIdOcorrencia(idOcorrenciaNew);
            }
            avaliacao = em.merge(avaliacao);
            if (idOcorrenciaOld != null && !idOcorrenciaOld.equals(idOcorrenciaNew)) {
                idOcorrenciaOld.getAvaliacaoList().remove(avaliacao);
                idOcorrenciaOld = em.merge(idOcorrenciaOld);
            }
            if (idOcorrenciaNew != null && !idOcorrenciaNew.equals(idOcorrenciaOld)) {
                idOcorrenciaNew.getAvaliacaoList().add(avaliacao);
                idOcorrenciaNew = em.merge(idOcorrenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avaliacao.getIdAvaliacao();
                if (findAvaliacao(id) == null) {
                    throw new NonexistentEntityException("The avaliacao with id " + id + " no longer exists.");
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
            Avaliacao avaliacao;
            try {
                avaliacao = em.getReference(Avaliacao.class, id);
                avaliacao.getIdAvaliacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avaliacao with id " + id + " no longer exists.", enfe);
            }
            Ocorrencia idOcorrencia = avaliacao.getIdOcorrencia();
            if (idOcorrencia != null) {
                idOcorrencia.getAvaliacaoList().remove(avaliacao);
                idOcorrencia = em.merge(idOcorrencia);
            }
            em.remove(avaliacao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Avaliacao> findAvaliacaoEntities() {
        return findAvaliacaoEntities(true, -1, -1);
    }

    public List<Avaliacao> findAvaliacaoEntities(int maxResults, int firstResult) {
        return findAvaliacaoEntities(false, maxResults, firstResult);
    }

    private List<Avaliacao> findAvaliacaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Avaliacao as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Avaliacao findAvaliacao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Avaliacao.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvaliacaoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Avaliacao as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
