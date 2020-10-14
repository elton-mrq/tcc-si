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
import br.com.vigilanciasocial.ouvidoria.entity.Endorgaoresp;
import br.com.vigilanciasocial.ouvidoria.entity.Telorgaoresp;
import java.util.ArrayList;
import java.util.List;
import br.com.vigilanciasocial.ouvidoria.entity.Encaminhar;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class OrgaorespDAO implements Serializable {

    public OrgaorespDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orgaoresp orgaoresp) {
        if (orgaoresp.getTelorgaorespList() == null) {
            orgaoresp.setTelorgaorespList(new ArrayList<Telorgaoresp>());
        }
        if (orgaoresp.getEncaminharList() == null) {
            orgaoresp.setEncaminharList(new ArrayList<Encaminhar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Endorgaoresp endorgaoresp = orgaoresp.getEndorgaoresp();
            if (endorgaoresp != null) {
                endorgaoresp = em.getReference(endorgaoresp.getClass(), endorgaoresp.getIdEndOrgaoResp());
                orgaoresp.setEndorgaoresp(endorgaoresp);
            }
            List<Telorgaoresp> attachedTelorgaorespList = new ArrayList<Telorgaoresp>();
            for (Telorgaoresp telorgaorespListTelorgaorespToAttach : orgaoresp.getTelorgaorespList()) {
                telorgaorespListTelorgaorespToAttach = em.getReference(telorgaorespListTelorgaorespToAttach.getClass(), telorgaorespListTelorgaorespToAttach.getIdTel());
                attachedTelorgaorespList.add(telorgaorespListTelorgaorespToAttach);
            }
            orgaoresp.setTelorgaorespList(attachedTelorgaorespList);
            List<Encaminhar> attachedEncaminharList = new ArrayList<Encaminhar>();
            for (Encaminhar encaminharListEncaminharToAttach : orgaoresp.getEncaminharList()) {
                encaminharListEncaminharToAttach = em.getReference(encaminharListEncaminharToAttach.getClass(), encaminharListEncaminharToAttach.getIdEncaminhamento());
                attachedEncaminharList.add(encaminharListEncaminharToAttach);
            }
            orgaoresp.setEncaminharList(attachedEncaminharList);
            em.persist(orgaoresp);
            if (endorgaoresp != null) {
                Orgaoresp oldIdOrgRespOfEndorgaoresp = endorgaoresp.getIdOrgResp();
                if (oldIdOrgRespOfEndorgaoresp != null) {
                    oldIdOrgRespOfEndorgaoresp.setEndorgaoresp(null);
                    oldIdOrgRespOfEndorgaoresp = em.merge(oldIdOrgRespOfEndorgaoresp);
                }
                endorgaoresp.setIdOrgResp(orgaoresp);
                endorgaoresp = em.merge(endorgaoresp);
            }
            for (Telorgaoresp telorgaorespListTelorgaoresp : orgaoresp.getTelorgaorespList()) {
                Orgaoresp oldIdOrgRespOfTelorgaorespListTelorgaoresp = telorgaorespListTelorgaoresp.getIdOrgResp();
                telorgaorespListTelorgaoresp.setIdOrgResp(orgaoresp);
                telorgaorespListTelorgaoresp = em.merge(telorgaorespListTelorgaoresp);
                if (oldIdOrgRespOfTelorgaorespListTelorgaoresp != null) {
                    oldIdOrgRespOfTelorgaorespListTelorgaoresp.getTelorgaorespList().remove(telorgaorespListTelorgaoresp);
                    oldIdOrgRespOfTelorgaorespListTelorgaoresp = em.merge(oldIdOrgRespOfTelorgaorespListTelorgaoresp);
                }
            }
            for (Encaminhar encaminharListEncaminhar : orgaoresp.getEncaminharList()) {
                Orgaoresp oldOrgaorespidOrgaorespOfEncaminharListEncaminhar = encaminharListEncaminhar.getOrgaorespidOrgaoresp();
                encaminharListEncaminhar.setOrgaorespidOrgaoresp(orgaoresp);
                encaminharListEncaminhar = em.merge(encaminharListEncaminhar);
                if (oldOrgaorespidOrgaorespOfEncaminharListEncaminhar != null) {
                    oldOrgaorespidOrgaorespOfEncaminharListEncaminhar.getEncaminharList().remove(encaminharListEncaminhar);
                    oldOrgaorespidOrgaorespOfEncaminharListEncaminhar = em.merge(oldOrgaorespidOrgaorespOfEncaminharListEncaminhar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orgaoresp orgaoresp) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Orgaoresp persistentOrgaoresp = em.find(Orgaoresp.class, orgaoresp.getIdOrgaoResp());
            Endorgaoresp endorgaorespOld = persistentOrgaoresp.getEndorgaoresp();
            Endorgaoresp endorgaorespNew = orgaoresp.getEndorgaoresp();
            List<Telorgaoresp> telorgaorespListOld = persistentOrgaoresp.getTelorgaorespList();
            List<Telorgaoresp> telorgaorespListNew = orgaoresp.getTelorgaorespList();
            List<Encaminhar> encaminharListOld = persistentOrgaoresp.getEncaminharList();
            List<Encaminhar> encaminharListNew = orgaoresp.getEncaminharList();
            List<String> illegalOrphanMessages = null;
            if (endorgaorespOld != null && !endorgaorespOld.equals(endorgaorespNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Endorgaoresp " + endorgaorespOld + " since its idOrgResp field is not nullable.");
            }
            for (Telorgaoresp telorgaorespListOldTelorgaoresp : telorgaorespListOld) {
                if (!telorgaorespListNew.contains(telorgaorespListOldTelorgaoresp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telorgaoresp " + telorgaorespListOldTelorgaoresp + " since its idOrgResp field is not nullable.");
                }
            }
            for (Encaminhar encaminharListOldEncaminhar : encaminharListOld) {
                if (!encaminharListNew.contains(encaminharListOldEncaminhar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encaminhar " + encaminharListOldEncaminhar + " since its orgaorespidOrgaoresp field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (endorgaorespNew != null) {
                endorgaorespNew = em.getReference(endorgaorespNew.getClass(), endorgaorespNew.getIdEndOrgaoResp());
                orgaoresp.setEndorgaoresp(endorgaorespNew);
            }
            List<Telorgaoresp> attachedTelorgaorespListNew = new ArrayList<Telorgaoresp>();
            for (Telorgaoresp telorgaorespListNewTelorgaorespToAttach : telorgaorespListNew) {
                telorgaorespListNewTelorgaorespToAttach = em.getReference(telorgaorespListNewTelorgaorespToAttach.getClass(), telorgaorespListNewTelorgaorespToAttach.getIdTel());
                attachedTelorgaorespListNew.add(telorgaorespListNewTelorgaorespToAttach);
            }
            telorgaorespListNew = attachedTelorgaorespListNew;
            orgaoresp.setTelorgaorespList(telorgaorespListNew);
            List<Encaminhar> attachedEncaminharListNew = new ArrayList<Encaminhar>();
            for (Encaminhar encaminharListNewEncaminharToAttach : encaminharListNew) {
                encaminharListNewEncaminharToAttach = em.getReference(encaminharListNewEncaminharToAttach.getClass(), encaminharListNewEncaminharToAttach.getIdEncaminhamento());
                attachedEncaminharListNew.add(encaminharListNewEncaminharToAttach);
            }
            encaminharListNew = attachedEncaminharListNew;
            orgaoresp.setEncaminharList(encaminharListNew);
            orgaoresp = em.merge(orgaoresp);
            if (endorgaorespNew != null && !endorgaorespNew.equals(endorgaorespOld)) {
                Orgaoresp oldIdOrgRespOfEndorgaoresp = endorgaorespNew.getIdOrgResp();
                if (oldIdOrgRespOfEndorgaoresp != null) {
                    oldIdOrgRespOfEndorgaoresp.setEndorgaoresp(null);
                    oldIdOrgRespOfEndorgaoresp = em.merge(oldIdOrgRespOfEndorgaoresp);
                }
                endorgaorespNew.setIdOrgResp(orgaoresp);
                endorgaorespNew = em.merge(endorgaorespNew);
            }
            for (Telorgaoresp telorgaorespListNewTelorgaoresp : telorgaorespListNew) {
                if (!telorgaorespListOld.contains(telorgaorespListNewTelorgaoresp)) {
                    Orgaoresp oldIdOrgRespOfTelorgaorespListNewTelorgaoresp = telorgaorespListNewTelorgaoresp.getIdOrgResp();
                    telorgaorespListNewTelorgaoresp.setIdOrgResp(orgaoresp);
                    telorgaorespListNewTelorgaoresp = em.merge(telorgaorespListNewTelorgaoresp);
                    if (oldIdOrgRespOfTelorgaorespListNewTelorgaoresp != null && !oldIdOrgRespOfTelorgaorespListNewTelorgaoresp.equals(orgaoresp)) {
                        oldIdOrgRespOfTelorgaorespListNewTelorgaoresp.getTelorgaorespList().remove(telorgaorespListNewTelorgaoresp);
                        oldIdOrgRespOfTelorgaorespListNewTelorgaoresp = em.merge(oldIdOrgRespOfTelorgaorespListNewTelorgaoresp);
                    }
                }
            }
            for (Encaminhar encaminharListNewEncaminhar : encaminharListNew) {
                if (!encaminharListOld.contains(encaminharListNewEncaminhar)) {
                    Orgaoresp oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar = encaminharListNewEncaminhar.getOrgaorespidOrgaoresp();
                    encaminharListNewEncaminhar.setOrgaorespidOrgaoresp(orgaoresp);
                    encaminharListNewEncaminhar = em.merge(encaminharListNewEncaminhar);
                    if (oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar != null && !oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar.equals(orgaoresp)) {
                        oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar.getEncaminharList().remove(encaminharListNewEncaminhar);
                        oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar = em.merge(oldOrgaorespidOrgaorespOfEncaminharListNewEncaminhar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = orgaoresp.getIdOrgaoResp();
                if (findOrgaoresp(id) == null) {
                    throw new NonexistentEntityException("The orgaoresp with id " + id + " no longer exists.");
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
            Orgaoresp orgaoresp;
            try {
                orgaoresp = em.getReference(Orgaoresp.class, id);
                orgaoresp.getIdOrgaoResp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orgaoresp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Endorgaoresp endorgaorespOrphanCheck = orgaoresp.getEndorgaoresp();
            if (endorgaorespOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orgaoresp (" + orgaoresp + ") cannot be destroyed since the Endorgaoresp " + endorgaorespOrphanCheck + " in its endorgaoresp field has a non-nullable idOrgResp field.");
            }
            List<Telorgaoresp> telorgaorespListOrphanCheck = orgaoresp.getTelorgaorespList();
            for (Telorgaoresp telorgaorespListOrphanCheckTelorgaoresp : telorgaorespListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orgaoresp (" + orgaoresp + ") cannot be destroyed since the Telorgaoresp " + telorgaorespListOrphanCheckTelorgaoresp + " in its telorgaorespList field has a non-nullable idOrgResp field.");
            }
            List<Encaminhar> encaminharListOrphanCheck = orgaoresp.getEncaminharList();
            for (Encaminhar encaminharListOrphanCheckEncaminhar : encaminharListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orgaoresp (" + orgaoresp + ") cannot be destroyed since the Encaminhar " + encaminharListOrphanCheckEncaminhar + " in its encaminharList field has a non-nullable orgaorespidOrgaoresp field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(orgaoresp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Orgaoresp> findOrgaorespEntities() {
        return findOrgaorespEntities(true, -1, -1);
    }

    public List<Orgaoresp> findOrgaorespEntities(int maxResults, int firstResult) {
        return findOrgaorespEntities(false, maxResults, firstResult);
    }

    private List<Orgaoresp> findOrgaorespEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Orgaoresp as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Orgaoresp findOrgaoresp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orgaoresp.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrgaorespCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Orgaoresp as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
       public int getUltidorgao() {

        EntityManager em = getEntityManager();

        Query q = em.createQuery("Select Max(o.idOrgaoResp) from Orgaoresp o");
        return ((Integer) q.getSingleResult()).intValue();

    }

    
}
