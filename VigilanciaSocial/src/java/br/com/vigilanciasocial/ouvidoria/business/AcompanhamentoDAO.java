/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Acompanhamento;
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
public class AcompanhamentoDAO implements Serializable {

    public AcompanhamentoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Acompanhamento acompanhamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencia idOcorrencia = acompanhamento.getIdOcorrencia();
            if (idOcorrencia != null) {
                idOcorrencia = em.getReference(idOcorrencia.getClass(), idOcorrencia.getIdOcorrencia());
                acompanhamento.setIdOcorrencia(idOcorrencia);
            }
            em.persist(acompanhamento);
            if (idOcorrencia != null) {
                idOcorrencia.getAcompanhamentoList().add(acompanhamento);
                idOcorrencia = em.merge(idOcorrencia);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Acompanhamento acompanhamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acompanhamento persistentAcompanhamento = em.find(Acompanhamento.class, acompanhamento.getIdAcomp());
            Ocorrencia idOcorrenciaOld = persistentAcompanhamento.getIdOcorrencia();
            Ocorrencia idOcorrenciaNew = acompanhamento.getIdOcorrencia();
            if (idOcorrenciaNew != null) {
                idOcorrenciaNew = em.getReference(idOcorrenciaNew.getClass(), idOcorrenciaNew.getIdOcorrencia());
                acompanhamento.setIdOcorrencia(idOcorrenciaNew);
            }
            acompanhamento = em.merge(acompanhamento);
            if (idOcorrenciaOld != null && !idOcorrenciaOld.equals(idOcorrenciaNew)) {
                idOcorrenciaOld.getAcompanhamentoList().remove(acompanhamento);
                idOcorrenciaOld = em.merge(idOcorrenciaOld);
            }
            if (idOcorrenciaNew != null && !idOcorrenciaNew.equals(idOcorrenciaOld)) {
                idOcorrenciaNew.getAcompanhamentoList().add(acompanhamento);
                idOcorrenciaNew = em.merge(idOcorrenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acompanhamento.getIdAcomp();
                if (findAcompanhamento(id) == null) {
                    throw new NonexistentEntityException("The acompanhamento with id " + id + " no longer exists.");
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
            Acompanhamento acompanhamento;
            try {
                acompanhamento = em.getReference(Acompanhamento.class, id);
                acompanhamento.getIdAcomp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acompanhamento with id " + id + " no longer exists.", enfe);
            }
            Ocorrencia idOcorrencia = acompanhamento.getIdOcorrencia();
            if (idOcorrencia != null) {
                idOcorrencia.getAcompanhamentoList().remove(acompanhamento);
                idOcorrencia = em.merge(idOcorrencia);
            }
            em.remove(acompanhamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Acompanhamento> findAcompanhamentoEntities() {
        return findAcompanhamentoEntities(true, -1, -1);
    }

    public List<Acompanhamento> findAcompanhamentoEntities(int maxResults, int firstResult) {
        return findAcompanhamentoEntities(false, maxResults, firstResult);
    }

    private List<Acompanhamento> findAcompanhamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Acompanhamento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Acompanhamento findAcompanhamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Acompanhamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcompanhamentoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Acompanhamento as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
