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
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import br.com.vigilanciasocial.ouvidoria.entity.Segmento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class SegmentoDAO implements Serializable {

    public SegmentoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Segmento segmento) {
        if (segmento.getOcorrenciaList() == null) {
            segmento.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : segmento.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            segmento.setOcorrenciaList(attachedOcorrenciaList);
            em.persist(segmento);
            for (Ocorrencia ocorrenciaListOcorrencia : segmento.getOcorrenciaList()) {
                Segmento oldSegmentoidSegmentoOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getSegmentoidSegmento();
                ocorrenciaListOcorrencia.setSegmentoidSegmento(segmento);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldSegmentoidSegmentoOfOcorrenciaListOcorrencia != null) {
                    oldSegmentoidSegmentoOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldSegmentoidSegmentoOfOcorrenciaListOcorrencia = em.merge(oldSegmentoidSegmentoOfOcorrenciaListOcorrencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Segmento segmento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Segmento persistentSegmento = em.find(Segmento.class, segmento.getIdSegmento());
            List<Ocorrencia> ocorrenciaListOld = persistentSegmento.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = segmento.getOcorrenciaList();
            List<Ocorrencia> attachedOcorrenciaListNew = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListNewOcorrenciaToAttach : ocorrenciaListNew) {
                ocorrenciaListNewOcorrenciaToAttach = em.getReference(ocorrenciaListNewOcorrenciaToAttach.getClass(), ocorrenciaListNewOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaListNew.add(ocorrenciaListNewOcorrenciaToAttach);
            }
            ocorrenciaListNew = attachedOcorrenciaListNew;
            segmento.setOcorrenciaList(ocorrenciaListNew);
            segmento = em.merge(segmento);
            for (Ocorrencia ocorrenciaListOldOcorrencia : ocorrenciaListOld) {
                if (!ocorrenciaListNew.contains(ocorrenciaListOldOcorrencia)) {
                    ocorrenciaListOldOcorrencia.setSegmentoidSegmento(null);
                    ocorrenciaListOldOcorrencia = em.merge(ocorrenciaListOldOcorrencia);
                }
            }
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Segmento oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getSegmentoidSegmento();
                    ocorrenciaListNewOcorrencia.setSegmentoidSegmento(segmento);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia != null && !oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia.equals(segmento)) {
                        oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia = em.merge(oldSegmentoidSegmentoOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = segmento.getIdSegmento();
                if (findSegmento(id) == null) {
                    throw new NonexistentEntityException("The segmento with id " + id + " no longer exists.");
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
            Segmento segmento;
            try {
                segmento = em.getReference(Segmento.class, id);
                segmento.getIdSegmento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The segmento with id " + id + " no longer exists.", enfe);
            }
            List<Ocorrencia> ocorrenciaList = segmento.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOcorrencia : ocorrenciaList) {
                ocorrenciaListOcorrencia.setSegmentoidSegmento(null);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
            }
            em.remove(segmento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Segmento> findSegmentoEntities() {
        return findSegmentoEntities(true, -1, -1);
    }

    public List<Segmento> findSegmentoEntities(int maxResults, int firstResult) {
        return findSegmentoEntities(false, maxResults, firstResult);
    }

    private List<Segmento> findSegmentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Segmento as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Segmento findSegmento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Segmento.class, id);
        } finally {
            em.close();
        }
    }

    public int getSegmentoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Segmento as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
