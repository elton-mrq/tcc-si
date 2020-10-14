/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.business;

import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import br.com.vigilanciasocial.ouvidoria.entity.Endvitima;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import java.util.ArrayList;
import java.util.List;
import br.com.vigilanciasocial.ouvidoria.entity.Telvitma;
import br.com.vigilanciasocial.ouvidoria.entity.Vitima;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Elton
 */
public class VitimaDAO implements Serializable {

    public VitimaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vitima vitima) {
        if (vitima.getOcorrenciaList() == null) {
            vitima.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        if (vitima.getTelvitmaList() == null) {
            vitima.setTelvitmaList(new ArrayList<Telvitma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endvitima endvitima = vitima.getEndvitima();
            if (endvitima != null) {
                endvitima = em.getReference(endvitima.getClass(), endvitima.getIdEndVitima());
                vitima.setEndvitima(endvitima);
            }
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : vitima.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            vitima.setOcorrenciaList(attachedOcorrenciaList);
            List<Telvitma> attachedTelvitmaList = new ArrayList<Telvitma>();
            for (Telvitma telvitmaListTelvitmaToAttach : vitima.getTelvitmaList()) {
                telvitmaListTelvitmaToAttach = em.getReference(telvitmaListTelvitmaToAttach.getClass(), telvitmaListTelvitmaToAttach.getIdTelVitma());
                attachedTelvitmaList.add(telvitmaListTelvitmaToAttach);
            }
            vitima.setTelvitmaList(attachedTelvitmaList);
            em.persist(vitima);
            if (endvitima != null) {
                Vitima oldIdVitimaOfEndvitima = endvitima.getIdVitima();
                if (oldIdVitimaOfEndvitima != null) {
                    oldIdVitimaOfEndvitima.setEndvitima(null);
                    oldIdVitimaOfEndvitima = em.merge(oldIdVitimaOfEndvitima);
                }
                endvitima.setIdVitima(vitima);
                endvitima = em.merge(endvitima);
            }
            for (Ocorrencia ocorrenciaListOcorrencia : vitima.getOcorrenciaList()) {
                Vitima oldIdVitmaOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getIdVitma();
                ocorrenciaListOcorrencia.setIdVitma(vitima);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldIdVitmaOfOcorrenciaListOcorrencia != null) {
                    oldIdVitmaOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldIdVitmaOfOcorrenciaListOcorrencia = em.merge(oldIdVitmaOfOcorrenciaListOcorrencia);
                }
            }
            for (Telvitma telvitmaListTelvitma : vitima.getTelvitmaList()) {
                Vitima oldIdVitimaOfTelvitmaListTelvitma = telvitmaListTelvitma.getIdVitima();
                telvitmaListTelvitma.setIdVitima(vitima);
                telvitmaListTelvitma = em.merge(telvitmaListTelvitma);
                if (oldIdVitimaOfTelvitmaListTelvitma != null) {
                    oldIdVitimaOfTelvitmaListTelvitma.getTelvitmaList().remove(telvitmaListTelvitma);
                    oldIdVitimaOfTelvitmaListTelvitma = em.merge(oldIdVitimaOfTelvitmaListTelvitma);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vitima vitima) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vitima persistentVitima = em.find(Vitima.class, vitima.getIdVitima());
            Endvitima endvitimaOld = persistentVitima.getEndvitima();
            Endvitima endvitimaNew = vitima.getEndvitima();
            List<Ocorrencia> ocorrenciaListOld = persistentVitima.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = vitima.getOcorrenciaList();
            List<Telvitma> telvitmaListOld = persistentVitima.getTelvitmaList();
            List<Telvitma> telvitmaListNew = vitima.getTelvitmaList();
            List<String> illegalOrphanMessages = null;
            if (endvitimaOld != null && !endvitimaOld.equals(endvitimaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Endvitima " + endvitimaOld + " since its idVitima field is not nullable.");
            }
            for (Ocorrencia ocorrenciaListOldOcorrencia : ocorrenciaListOld) {
                if (!ocorrenciaListNew.contains(ocorrenciaListOldOcorrencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ocorrencia " + ocorrenciaListOldOcorrencia + " since its idVitma field is not nullable.");
                }
            }
            for (Telvitma telvitmaListOldTelvitma : telvitmaListOld) {
                if (!telvitmaListNew.contains(telvitmaListOldTelvitma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telvitma " + telvitmaListOldTelvitma + " since its idVitima field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (endvitimaNew != null) {
                endvitimaNew = em.getReference(endvitimaNew.getClass(), endvitimaNew.getIdEndVitima());
                vitima.setEndvitima(endvitimaNew);
            }
            List<Ocorrencia> attachedOcorrenciaListNew = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListNewOcorrenciaToAttach : ocorrenciaListNew) {
                ocorrenciaListNewOcorrenciaToAttach = em.getReference(ocorrenciaListNewOcorrenciaToAttach.getClass(), ocorrenciaListNewOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaListNew.add(ocorrenciaListNewOcorrenciaToAttach);
            }
            ocorrenciaListNew = attachedOcorrenciaListNew;
            vitima.setOcorrenciaList(ocorrenciaListNew);
            List<Telvitma> attachedTelvitmaListNew = new ArrayList<Telvitma>();
            for (Telvitma telvitmaListNewTelvitmaToAttach : telvitmaListNew) {
                telvitmaListNewTelvitmaToAttach = em.getReference(telvitmaListNewTelvitmaToAttach.getClass(), telvitmaListNewTelvitmaToAttach.getIdTelVitma());
                attachedTelvitmaListNew.add(telvitmaListNewTelvitmaToAttach);
            }
            telvitmaListNew = attachedTelvitmaListNew;
            vitima.setTelvitmaList(telvitmaListNew);
            vitima = em.merge(vitima);
            if (endvitimaNew != null && !endvitimaNew.equals(endvitimaOld)) {
                Vitima oldIdVitimaOfEndvitima = endvitimaNew.getIdVitima();
                if (oldIdVitimaOfEndvitima != null) {
                    oldIdVitimaOfEndvitima.setEndvitima(null);
                    oldIdVitimaOfEndvitima = em.merge(oldIdVitimaOfEndvitima);
                }
                endvitimaNew.setIdVitima(vitima);
                endvitimaNew = em.merge(endvitimaNew);
            }
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Vitima oldIdVitmaOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getIdVitma();
                    ocorrenciaListNewOcorrencia.setIdVitma(vitima);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldIdVitmaOfOcorrenciaListNewOcorrencia != null && !oldIdVitmaOfOcorrenciaListNewOcorrencia.equals(vitima)) {
                        oldIdVitmaOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldIdVitmaOfOcorrenciaListNewOcorrencia = em.merge(oldIdVitmaOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            for (Telvitma telvitmaListNewTelvitma : telvitmaListNew) {
                if (!telvitmaListOld.contains(telvitmaListNewTelvitma)) {
                    Vitima oldIdVitimaOfTelvitmaListNewTelvitma = telvitmaListNewTelvitma.getIdVitima();
                    telvitmaListNewTelvitma.setIdVitima(vitima);
                    telvitmaListNewTelvitma = em.merge(telvitmaListNewTelvitma);
                    if (oldIdVitimaOfTelvitmaListNewTelvitma != null && !oldIdVitimaOfTelvitmaListNewTelvitma.equals(vitima)) {
                        oldIdVitimaOfTelvitmaListNewTelvitma.getTelvitmaList().remove(telvitmaListNewTelvitma);
                        oldIdVitimaOfTelvitmaListNewTelvitma = em.merge(oldIdVitimaOfTelvitmaListNewTelvitma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vitima.getIdVitima();
                if (findVitima(id) == null) {
                    throw new NonexistentEntityException("The vitima with id " + id + " no longer exists.");
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
            Vitima vitima;
            try {
                vitima = em.getReference(Vitima.class, id);
                vitima.getIdVitima();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vitima with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Endvitima endvitimaOrphanCheck = vitima.getEndvitima();
            if (endvitimaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vitima (" + vitima + ") cannot be destroyed since the Endvitima " + endvitimaOrphanCheck + " in its endvitima field has a non-nullable idVitima field.");
            }
            List<Ocorrencia> ocorrenciaListOrphanCheck = vitima.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOrphanCheckOcorrencia : ocorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vitima (" + vitima + ") cannot be destroyed since the Ocorrencia " + ocorrenciaListOrphanCheckOcorrencia + " in its ocorrenciaList field has a non-nullable idVitma field.");
            }
            List<Telvitma> telvitmaListOrphanCheck = vitima.getTelvitmaList();
            for (Telvitma telvitmaListOrphanCheckTelvitma : telvitmaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vitima (" + vitima + ") cannot be destroyed since the Telvitma " + telvitmaListOrphanCheckTelvitma + " in its telvitmaList field has a non-nullable idVitima field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vitima);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vitima> findVitimaEntities() {
        return findVitimaEntities(true, -1, -1);
    }

    public List<Vitima> findVitimaEntities(int maxResults, int firstResult) {
        return findVitimaEntities(false, maxResults, firstResult);
    }

    private List<Vitima> findVitimaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Vitima as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vitima findVitima(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vitima.class, id);
        } finally {
            em.close();
        }
    }

    public int getVitimaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Vitima as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
       public int getUltVitma() {

        EntityManager em = getEntityManager();

        Query q = em.createQuery("Select Max(v.idVitima) from Vitima v");
        return ((Integer) q.getSingleResult()).intValue();

    }
    
        public Vitima getVitima(String nome, Date dtNas) {

            EntityManager em = getEntityManager();
            try {
                Query q = em.createQuery("Select v from Vitima v where v.nomeVitima = :nome and v.dtNasc = :dtNasc");
                q.setParameter("nome", nome);
                q.setParameter("dtNasc", dtNas);
                Vitima v = (Vitima) q.getSingleResult();
                return v;
            } catch (NoResultException no) {
                return null;
            }

    }
    
}
