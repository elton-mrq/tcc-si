/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Agressor;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class AgressorDAO implements Serializable {

    public AgressorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agressor agressor) {
        if (agressor.getOcorrenciaList() == null) {
            agressor.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : agressor.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            agressor.setOcorrenciaList(attachedOcorrenciaList);
            em.persist(agressor);
            for (Ocorrencia ocorrenciaListOcorrencia : agressor.getOcorrenciaList()) {
                Agressor oldIdAgressorOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getIdAgressor();
                ocorrenciaListOcorrencia.setIdAgressor(agressor);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldIdAgressorOfOcorrenciaListOcorrencia != null) {
                    oldIdAgressorOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldIdAgressorOfOcorrenciaListOcorrencia = em.merge(oldIdAgressorOfOcorrenciaListOcorrencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agressor agressor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agressor persistentAgressor = em.find(Agressor.class, agressor.getIdAgressor());
            List<Ocorrencia> ocorrenciaListOld = persistentAgressor.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = agressor.getOcorrenciaList();
            List<String> illegalOrphanMessages = null;
            for (Ocorrencia ocorrenciaListOldOcorrencia : ocorrenciaListOld) {
                if (!ocorrenciaListNew.contains(ocorrenciaListOldOcorrencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ocorrencia " + ocorrenciaListOldOcorrencia + " since its idAgressor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ocorrencia> attachedOcorrenciaListNew = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListNewOcorrenciaToAttach : ocorrenciaListNew) {
                ocorrenciaListNewOcorrenciaToAttach = em.getReference(ocorrenciaListNewOcorrenciaToAttach.getClass(), ocorrenciaListNewOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaListNew.add(ocorrenciaListNewOcorrenciaToAttach);
            }
            ocorrenciaListNew = attachedOcorrenciaListNew;
            agressor.setOcorrenciaList(ocorrenciaListNew);
            agressor = em.merge(agressor);
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Agressor oldIdAgressorOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getIdAgressor();
                    ocorrenciaListNewOcorrencia.setIdAgressor(agressor);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldIdAgressorOfOcorrenciaListNewOcorrencia != null && !oldIdAgressorOfOcorrenciaListNewOcorrencia.equals(agressor)) {
                        oldIdAgressorOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldIdAgressorOfOcorrenciaListNewOcorrencia = em.merge(oldIdAgressorOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = agressor.getIdAgressor();
                if (findAgressor(id) == null) {
                    throw new NonexistentEntityException("The agressor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agressor agressor;
            try {
                agressor = em.getReference(Agressor.class, id);
                agressor.getIdAgressor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agressor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ocorrencia> ocorrenciaListOrphanCheck = agressor.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOrphanCheckOcorrencia : ocorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agressor (" + agressor + ") cannot be destroyed since the Ocorrencia " + ocorrenciaListOrphanCheckOcorrencia + " in its ocorrenciaList field has a non-nullable idAgressor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(agressor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Agressor> findAgressorEntities() {
        return findAgressorEntities(true, -1, -1);
    }

    public List<Agressor> findAgressorEntities(int maxResults, int firstResult) {
        return findAgressorEntities(false, maxResults, firstResult);
    }

    private List<Agressor> findAgressorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Agressor as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Agressor findAgressor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agressor.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgressorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Agressor as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
