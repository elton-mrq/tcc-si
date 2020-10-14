/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.PreexistingEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Encaminhar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class EncaminharDAO implements Serializable {

    public EncaminharDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encaminhar encaminhar) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencia ocorrenciaidOcorrencia = encaminhar.getOcorrenciaidOcorrencia();
            if (ocorrenciaidOcorrencia != null) {
                ocorrenciaidOcorrencia = em.getReference(ocorrenciaidOcorrencia.getClass(), ocorrenciaidOcorrencia.getIdOcorrencia());
                encaminhar.setOcorrenciaidOcorrencia(ocorrenciaidOcorrencia);
            }
            Orgaoresp orgaorespidOrgaoresp = encaminhar.getOrgaorespidOrgaoresp();
            if (orgaorespidOrgaoresp != null) {
                orgaorespidOrgaoresp = em.getReference(orgaorespidOrgaoresp.getClass(), orgaorespidOrgaoresp.getIdOrgaoResp());
                encaminhar.setOrgaorespidOrgaoresp(orgaorespidOrgaoresp);
            }
            em.persist(encaminhar);
            if (ocorrenciaidOcorrencia != null) {
                ocorrenciaidOcorrencia.getEncaminharList().add(encaminhar);
                ocorrenciaidOcorrencia = em.merge(ocorrenciaidOcorrencia);
            }
            if (orgaorespidOrgaoresp != null) {
                orgaorespidOrgaoresp.getEncaminharList().add(encaminhar);
                orgaorespidOrgaoresp = em.merge(orgaorespidOrgaoresp);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncaminhar(encaminhar.getIdEncaminhamento()) != null) {
                throw new PreexistingEntityException("Encaminhar " + encaminhar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encaminhar encaminhar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encaminhar persistentEncaminhar = em.find(Encaminhar.class, encaminhar.getIdEncaminhamento());
            Ocorrencia ocorrenciaidOcorrenciaOld = persistentEncaminhar.getOcorrenciaidOcorrencia();
            Ocorrencia ocorrenciaidOcorrenciaNew = encaminhar.getOcorrenciaidOcorrencia();
            Orgaoresp orgaorespidOrgaorespOld = persistentEncaminhar.getOrgaorespidOrgaoresp();
            Orgaoresp orgaorespidOrgaorespNew = encaminhar.getOrgaorespidOrgaoresp();
            if (ocorrenciaidOcorrenciaNew != null) {
                ocorrenciaidOcorrenciaNew = em.getReference(ocorrenciaidOcorrenciaNew.getClass(), ocorrenciaidOcorrenciaNew.getIdOcorrencia());
                encaminhar.setOcorrenciaidOcorrencia(ocorrenciaidOcorrenciaNew);
            }
            if (orgaorespidOrgaorespNew != null) {
                orgaorespidOrgaorespNew = em.getReference(orgaorespidOrgaorespNew.getClass(), orgaorespidOrgaorespNew.getIdOrgaoResp());
                encaminhar.setOrgaorespidOrgaoresp(orgaorespidOrgaorespNew);
            }
            encaminhar = em.merge(encaminhar);
            if (ocorrenciaidOcorrenciaOld != null && !ocorrenciaidOcorrenciaOld.equals(ocorrenciaidOcorrenciaNew)) {
                ocorrenciaidOcorrenciaOld.getEncaminharList().remove(encaminhar);
                ocorrenciaidOcorrenciaOld = em.merge(ocorrenciaidOcorrenciaOld);
            }
            if (ocorrenciaidOcorrenciaNew != null && !ocorrenciaidOcorrenciaNew.equals(ocorrenciaidOcorrenciaOld)) {
                ocorrenciaidOcorrenciaNew.getEncaminharList().add(encaminhar);
                ocorrenciaidOcorrenciaNew = em.merge(ocorrenciaidOcorrenciaNew);
            }
            if (orgaorespidOrgaorespOld != null && !orgaorespidOrgaorespOld.equals(orgaorespidOrgaorespNew)) {
                orgaorespidOrgaorespOld.getEncaminharList().remove(encaminhar);
                orgaorespidOrgaorespOld = em.merge(orgaorespidOrgaorespOld);
            }
            if (orgaorespidOrgaorespNew != null && !orgaorespidOrgaorespNew.equals(orgaorespidOrgaorespOld)) {
                orgaorespidOrgaorespNew.getEncaminharList().add(encaminhar);
                orgaorespidOrgaorespNew = em.merge(orgaorespidOrgaorespNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encaminhar.getIdEncaminhamento();
                if (findEncaminhar(id) == null) {
                    throw new NonexistentEntityException("The encaminhar with id " + id + " no longer exists.");
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
            Encaminhar encaminhar;
            try {
                encaminhar = em.getReference(Encaminhar.class, id);
                encaminhar.getIdEncaminhamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encaminhar with id " + id + " no longer exists.", enfe);
            }
            Ocorrencia ocorrenciaidOcorrencia = encaminhar.getOcorrenciaidOcorrencia();
            if (ocorrenciaidOcorrencia != null) {
                ocorrenciaidOcorrencia.getEncaminharList().remove(encaminhar);
                ocorrenciaidOcorrencia = em.merge(ocorrenciaidOcorrencia);
            }
            Orgaoresp orgaorespidOrgaoresp = encaminhar.getOrgaorespidOrgaoresp();
            if (orgaorespidOrgaoresp != null) {
                orgaorespidOrgaoresp.getEncaminharList().remove(encaminhar);
                orgaorespidOrgaoresp = em.merge(orgaorespidOrgaoresp);
            }
            em.remove(encaminhar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encaminhar> findEncaminharEntities() {
        return findEncaminharEntities(true, -1, -1);
    }

    public List<Encaminhar> findEncaminharEntities(int maxResults, int firstResult) {
        return findEncaminharEntities(false, maxResults, firstResult);
    }

    private List<Encaminhar> findEncaminharEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Encaminhar as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Encaminhar findEncaminhar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encaminhar.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncaminharCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Encaminhar as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
