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
import br.com.vigilanciasocial.ouvidoria.entity.Agressor;
import br.com.vigilanciasocial.ouvidoria.entity.Usuario;
import br.com.vigilanciasocial.ouvidoria.entity.Vitima;
import br.com.vigilanciasocial.ouvidoria.entity.Segmento;
import br.com.vigilanciasocial.ouvidoria.entity.Acompanhamento;
import java.util.ArrayList;
import java.util.List;
import br.com.vigilanciasocial.ouvidoria.entity.Avaliacao;
import br.com.vigilanciasocial.ouvidoria.entity.Encaminhar;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class OcorrenciaDAO implements Serializable {

    public OcorrenciaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ocorrencia ocorrencia) {
        if (ocorrencia.getAcompanhamentoList() == null) {
            ocorrencia.setAcompanhamentoList(new ArrayList<Acompanhamento>());
        }
        if (ocorrencia.getAvaliacaoList() == null) {
            ocorrencia.setAvaliacaoList(new ArrayList<Avaliacao>());
        }
        if (ocorrencia.getEncaminharList() == null) {
            ocorrencia.setEncaminharList(new ArrayList<Encaminhar>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Agressor idAgressor = ocorrencia.getIdAgressor();
            if (idAgressor != null) {
                idAgressor = em.getReference(idAgressor.getClass(), idAgressor.getIdAgressor());
                ocorrencia.setIdAgressor(idAgressor);
            }
            Usuario idUsuario = ocorrencia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                ocorrencia.setIdUsuario(idUsuario);
            }
            Vitima idVitma = ocorrencia.getIdVitma();
            if (idVitma != null) {
                idVitma = em.getReference(idVitma.getClass(), idVitma.getIdVitima());
                ocorrencia.setIdVitma(idVitma);
            }
            Segmento segmentoidSegmento = ocorrencia.getSegmentoidSegmento();
            if (segmentoidSegmento != null) {
                segmentoidSegmento = em.getReference(segmentoidSegmento.getClass(), segmentoidSegmento.getIdSegmento());
                ocorrencia.setSegmentoidSegmento(segmentoidSegmento);
            }
            List<Acompanhamento> attachedAcompanhamentoList = new ArrayList<Acompanhamento>();
            for (Acompanhamento acompanhamentoListAcompanhamentoToAttach : ocorrencia.getAcompanhamentoList()) {
                acompanhamentoListAcompanhamentoToAttach = em.getReference(acompanhamentoListAcompanhamentoToAttach.getClass(), acompanhamentoListAcompanhamentoToAttach.getIdAcomp());
                attachedAcompanhamentoList.add(acompanhamentoListAcompanhamentoToAttach);
            }
            ocorrencia.setAcompanhamentoList(attachedAcompanhamentoList);
            List<Avaliacao> attachedAvaliacaoList = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListAvaliacaoToAttach : ocorrencia.getAvaliacaoList()) {
                avaliacaoListAvaliacaoToAttach = em.getReference(avaliacaoListAvaliacaoToAttach.getClass(), avaliacaoListAvaliacaoToAttach.getIdAvaliacao());
                attachedAvaliacaoList.add(avaliacaoListAvaliacaoToAttach);
            }
            ocorrencia.setAvaliacaoList(attachedAvaliacaoList);
            List<Encaminhar> attachedEncaminharList = new ArrayList<Encaminhar>();
            for (Encaminhar encaminharListEncaminharToAttach : ocorrencia.getEncaminharList()) {
                encaminharListEncaminharToAttach = em.getReference(encaminharListEncaminharToAttach.getClass(), encaminharListEncaminharToAttach.getIdEncaminhamento());
                attachedEncaminharList.add(encaminharListEncaminharToAttach);
            }
            ocorrencia.setEncaminharList(attachedEncaminharList);
            em.persist(ocorrencia);
            if (idAgressor != null) {
                idAgressor.getOcorrenciaList().add(ocorrencia);
                idAgressor = em.merge(idAgressor);
            }
            if (idUsuario != null) {
                idUsuario.getOcorrenciaList().add(ocorrencia);
                idUsuario = em.merge(idUsuario);
            }
            if (idVitma != null) {
                idVitma.getOcorrenciaList().add(ocorrencia);
                idVitma = em.merge(idVitma);
            }
            if (segmentoidSegmento != null) {
                segmentoidSegmento.getOcorrenciaList().add(ocorrencia);
                segmentoidSegmento = em.merge(segmentoidSegmento);
            }
            for (Acompanhamento acompanhamentoListAcompanhamento : ocorrencia.getAcompanhamentoList()) {
                Ocorrencia oldIdOcorrenciaOfAcompanhamentoListAcompanhamento = acompanhamentoListAcompanhamento.getIdOcorrencia();
                acompanhamentoListAcompanhamento.setIdOcorrencia(ocorrencia);
                acompanhamentoListAcompanhamento = em.merge(acompanhamentoListAcompanhamento);
                if (oldIdOcorrenciaOfAcompanhamentoListAcompanhamento != null) {
                    oldIdOcorrenciaOfAcompanhamentoListAcompanhamento.getAcompanhamentoList().remove(acompanhamentoListAcompanhamento);
                    oldIdOcorrenciaOfAcompanhamentoListAcompanhamento = em.merge(oldIdOcorrenciaOfAcompanhamentoListAcompanhamento);
                }
            }
            for (Avaliacao avaliacaoListAvaliacao : ocorrencia.getAvaliacaoList()) {
                Ocorrencia oldIdOcorrenciaOfAvaliacaoListAvaliacao = avaliacaoListAvaliacao.getIdOcorrencia();
                avaliacaoListAvaliacao.setIdOcorrencia(ocorrencia);
                avaliacaoListAvaliacao = em.merge(avaliacaoListAvaliacao);
                if (oldIdOcorrenciaOfAvaliacaoListAvaliacao != null) {
                    oldIdOcorrenciaOfAvaliacaoListAvaliacao.getAvaliacaoList().remove(avaliacaoListAvaliacao);
                    oldIdOcorrenciaOfAvaliacaoListAvaliacao = em.merge(oldIdOcorrenciaOfAvaliacaoListAvaliacao);
                }
            }
            for (Encaminhar encaminharListEncaminhar : ocorrencia.getEncaminharList()) {
                Ocorrencia oldOcorrenciaidOcorrenciaOfEncaminharListEncaminhar = encaminharListEncaminhar.getOcorrenciaidOcorrencia();
                encaminharListEncaminhar.setOcorrenciaidOcorrencia(ocorrencia);
                encaminharListEncaminhar = em.merge(encaminharListEncaminhar);
                if (oldOcorrenciaidOcorrenciaOfEncaminharListEncaminhar != null) {
                    oldOcorrenciaidOcorrenciaOfEncaminharListEncaminhar.getEncaminharList().remove(encaminharListEncaminhar);
                    oldOcorrenciaidOcorrenciaOfEncaminharListEncaminhar = em.merge(oldOcorrenciaidOcorrenciaOfEncaminharListEncaminhar);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ocorrencia ocorrencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ocorrencia persistentOcorrencia = em.find(Ocorrencia.class, ocorrencia.getIdOcorrencia());
            Agressor idAgressorOld = persistentOcorrencia.getIdAgressor();
            Agressor idAgressorNew = ocorrencia.getIdAgressor();
            Usuario idUsuarioOld = persistentOcorrencia.getIdUsuario();
            Usuario idUsuarioNew = ocorrencia.getIdUsuario();
            Vitima idVitmaOld = persistentOcorrencia.getIdVitma();
            Vitima idVitmaNew = ocorrencia.getIdVitma();
            Segmento segmentoidSegmentoOld = persistentOcorrencia.getSegmentoidSegmento();
            Segmento segmentoidSegmentoNew = ocorrencia.getSegmentoidSegmento();
            List<Acompanhamento> acompanhamentoListOld = persistentOcorrencia.getAcompanhamentoList();
            List<Acompanhamento> acompanhamentoListNew = ocorrencia.getAcompanhamentoList();
            List<Avaliacao> avaliacaoListOld = persistentOcorrencia.getAvaliacaoList();
            List<Avaliacao> avaliacaoListNew = ocorrencia.getAvaliacaoList();
            List<Encaminhar> encaminharListOld = persistentOcorrencia.getEncaminharList();
            List<Encaminhar> encaminharListNew = ocorrencia.getEncaminharList();
            List<String> illegalOrphanMessages = null;
            for (Acompanhamento acompanhamentoListOldAcompanhamento : acompanhamentoListOld) {
                if (!acompanhamentoListNew.contains(acompanhamentoListOldAcompanhamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Acompanhamento " + acompanhamentoListOldAcompanhamento + " since its idOcorrencia field is not nullable.");
                }
            }
            for (Avaliacao avaliacaoListOldAvaliacao : avaliacaoListOld) {
                if (!avaliacaoListNew.contains(avaliacaoListOldAvaliacao)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Avaliacao " + avaliacaoListOldAvaliacao + " since its idOcorrencia field is not nullable.");
                }
            }
            for (Encaminhar encaminharListOldEncaminhar : encaminharListOld) {
                if (!encaminharListNew.contains(encaminharListOldEncaminhar)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encaminhar " + encaminharListOldEncaminhar + " since its ocorrenciaidOcorrencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAgressorNew != null) {
                idAgressorNew = em.getReference(idAgressorNew.getClass(), idAgressorNew.getIdAgressor());
                ocorrencia.setIdAgressor(idAgressorNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                ocorrencia.setIdUsuario(idUsuarioNew);
            }
            if (idVitmaNew != null) {
                idVitmaNew = em.getReference(idVitmaNew.getClass(), idVitmaNew.getIdVitima());
                ocorrencia.setIdVitma(idVitmaNew);
            }
            if (segmentoidSegmentoNew != null) {
                segmentoidSegmentoNew = em.getReference(segmentoidSegmentoNew.getClass(), segmentoidSegmentoNew.getIdSegmento());
                ocorrencia.setSegmentoidSegmento(segmentoidSegmentoNew);
            }
            List<Acompanhamento> attachedAcompanhamentoListNew = new ArrayList<Acompanhamento>();
            for (Acompanhamento acompanhamentoListNewAcompanhamentoToAttach : acompanhamentoListNew) {
                acompanhamentoListNewAcompanhamentoToAttach = em.getReference(acompanhamentoListNewAcompanhamentoToAttach.getClass(), acompanhamentoListNewAcompanhamentoToAttach.getIdAcomp());
                attachedAcompanhamentoListNew.add(acompanhamentoListNewAcompanhamentoToAttach);
            }
            acompanhamentoListNew = attachedAcompanhamentoListNew;
            ocorrencia.setAcompanhamentoList(acompanhamentoListNew);
            List<Avaliacao> attachedAvaliacaoListNew = new ArrayList<Avaliacao>();
            for (Avaliacao avaliacaoListNewAvaliacaoToAttach : avaliacaoListNew) {
                avaliacaoListNewAvaliacaoToAttach = em.getReference(avaliacaoListNewAvaliacaoToAttach.getClass(), avaliacaoListNewAvaliacaoToAttach.getIdAvaliacao());
                attachedAvaliacaoListNew.add(avaliacaoListNewAvaliacaoToAttach);
            }
            avaliacaoListNew = attachedAvaliacaoListNew;
            ocorrencia.setAvaliacaoList(avaliacaoListNew);
            List<Encaminhar> attachedEncaminharListNew = new ArrayList<Encaminhar>();
            for (Encaminhar encaminharListNewEncaminharToAttach : encaminharListNew) {
                encaminharListNewEncaminharToAttach = em.getReference(encaminharListNewEncaminharToAttach.getClass(), encaminharListNewEncaminharToAttach.getIdEncaminhamento());
                attachedEncaminharListNew.add(encaminharListNewEncaminharToAttach);
            }
            encaminharListNew = attachedEncaminharListNew;
            ocorrencia.setEncaminharList(encaminharListNew);
            ocorrencia = em.merge(ocorrencia);
            if (idAgressorOld != null && !idAgressorOld.equals(idAgressorNew)) {
                idAgressorOld.getOcorrenciaList().remove(ocorrencia);
                idAgressorOld = em.merge(idAgressorOld);
            }
            if (idAgressorNew != null && !idAgressorNew.equals(idAgressorOld)) {
                idAgressorNew.getOcorrenciaList().add(ocorrencia);
                idAgressorNew = em.merge(idAgressorNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getOcorrenciaList().remove(ocorrencia);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getOcorrenciaList().add(ocorrencia);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            if (idVitmaOld != null && !idVitmaOld.equals(idVitmaNew)) {
                idVitmaOld.getOcorrenciaList().remove(ocorrencia);
                idVitmaOld = em.merge(idVitmaOld);
            }
            if (idVitmaNew != null && !idVitmaNew.equals(idVitmaOld)) {
                idVitmaNew.getOcorrenciaList().add(ocorrencia);
                idVitmaNew = em.merge(idVitmaNew);
            }
            if (segmentoidSegmentoOld != null && !segmentoidSegmentoOld.equals(segmentoidSegmentoNew)) {
                segmentoidSegmentoOld.getOcorrenciaList().remove(ocorrencia);
                segmentoidSegmentoOld = em.merge(segmentoidSegmentoOld);
            }
            if (segmentoidSegmentoNew != null && !segmentoidSegmentoNew.equals(segmentoidSegmentoOld)) {
                segmentoidSegmentoNew.getOcorrenciaList().add(ocorrencia);
                segmentoidSegmentoNew = em.merge(segmentoidSegmentoNew);
            }
            for (Acompanhamento acompanhamentoListNewAcompanhamento : acompanhamentoListNew) {
                if (!acompanhamentoListOld.contains(acompanhamentoListNewAcompanhamento)) {
                    Ocorrencia oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento = acompanhamentoListNewAcompanhamento.getIdOcorrencia();
                    acompanhamentoListNewAcompanhamento.setIdOcorrencia(ocorrencia);
                    acompanhamentoListNewAcompanhamento = em.merge(acompanhamentoListNewAcompanhamento);
                    if (oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento != null && !oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento.equals(ocorrencia)) {
                        oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento.getAcompanhamentoList().remove(acompanhamentoListNewAcompanhamento);
                        oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento = em.merge(oldIdOcorrenciaOfAcompanhamentoListNewAcompanhamento);
                    }
                }
            }
            for (Avaliacao avaliacaoListNewAvaliacao : avaliacaoListNew) {
                if (!avaliacaoListOld.contains(avaliacaoListNewAvaliacao)) {
                    Ocorrencia oldIdOcorrenciaOfAvaliacaoListNewAvaliacao = avaliacaoListNewAvaliacao.getIdOcorrencia();
                    avaliacaoListNewAvaliacao.setIdOcorrencia(ocorrencia);
                    avaliacaoListNewAvaliacao = em.merge(avaliacaoListNewAvaliacao);
                    if (oldIdOcorrenciaOfAvaliacaoListNewAvaliacao != null && !oldIdOcorrenciaOfAvaliacaoListNewAvaliacao.equals(ocorrencia)) {
                        oldIdOcorrenciaOfAvaliacaoListNewAvaliacao.getAvaliacaoList().remove(avaliacaoListNewAvaliacao);
                        oldIdOcorrenciaOfAvaliacaoListNewAvaliacao = em.merge(oldIdOcorrenciaOfAvaliacaoListNewAvaliacao);
                    }
                }
            }
            for (Encaminhar encaminharListNewEncaminhar : encaminharListNew) {
                if (!encaminharListOld.contains(encaminharListNewEncaminhar)) {
                    Ocorrencia oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar = encaminharListNewEncaminhar.getOcorrenciaidOcorrencia();
                    encaminharListNewEncaminhar.setOcorrenciaidOcorrencia(ocorrencia);
                    encaminharListNewEncaminhar = em.merge(encaminharListNewEncaminhar);
                    if (oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar != null && !oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar.equals(ocorrencia)) {
                        oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar.getEncaminharList().remove(encaminharListNewEncaminhar);
                        oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar = em.merge(oldOcorrenciaidOcorrenciaOfEncaminharListNewEncaminhar);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ocorrencia.getIdOcorrencia();
                if (findOcorrencia(id) == null) {
                    throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.");
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
            Ocorrencia ocorrencia;
            try {
                ocorrencia = em.getReference(Ocorrencia.class, id);
                ocorrencia.getIdOcorrencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ocorrencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Acompanhamento> acompanhamentoListOrphanCheck = ocorrencia.getAcompanhamentoList();
            for (Acompanhamento acompanhamentoListOrphanCheckAcompanhamento : acompanhamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ocorrencia (" + ocorrencia + ") cannot be destroyed since the Acompanhamento " + acompanhamentoListOrphanCheckAcompanhamento + " in its acompanhamentoList field has a non-nullable idOcorrencia field.");
            }
            List<Avaliacao> avaliacaoListOrphanCheck = ocorrencia.getAvaliacaoList();
            for (Avaliacao avaliacaoListOrphanCheckAvaliacao : avaliacaoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ocorrencia (" + ocorrencia + ") cannot be destroyed since the Avaliacao " + avaliacaoListOrphanCheckAvaliacao + " in its avaliacaoList field has a non-nullable idOcorrencia field.");
            }
            List<Encaminhar> encaminharListOrphanCheck = ocorrencia.getEncaminharList();
            for (Encaminhar encaminharListOrphanCheckEncaminhar : encaminharListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ocorrencia (" + ocorrencia + ") cannot be destroyed since the Encaminhar " + encaminharListOrphanCheckEncaminhar + " in its encaminharList field has a non-nullable ocorrenciaidOcorrencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Agressor idAgressor = ocorrencia.getIdAgressor();
            if (idAgressor != null) {
                idAgressor.getOcorrenciaList().remove(ocorrencia);
                idAgressor = em.merge(idAgressor);
            }
            Usuario idUsuario = ocorrencia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getOcorrenciaList().remove(ocorrencia);
                idUsuario = em.merge(idUsuario);
            }
            Vitima idVitma = ocorrencia.getIdVitma();
            if (idVitma != null) {
                idVitma.getOcorrenciaList().remove(ocorrencia);
                idVitma = em.merge(idVitma);
            }
            Segmento segmentoidSegmento = ocorrencia.getSegmentoidSegmento();
            if (segmentoidSegmento != null) {
                segmentoidSegmento.getOcorrenciaList().remove(ocorrencia);
                segmentoidSegmento = em.merge(segmentoidSegmento);
            }
            em.remove(ocorrencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ocorrencia> findOcorrenciaEntities() {
        return findOcorrenciaEntities(true, -1, -1);
    }

    public List<Ocorrencia> findOcorrenciaEntities(int maxResults, int firstResult) {
        return findOcorrenciaEntities(false, maxResults, firstResult);
    }

    private List<Ocorrencia> findOcorrenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ocorrencia as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Ocorrencia findOcorrencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ocorrencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getOcorrenciaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Ocorrencia as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public int getUltOcorrencia(){
        
        EntityManager em = getEntityManager();
        Query q = em.createQuery("select MAX(o.idOcorrencia) from Ocorrencia o");
        return ((Integer) q.getSingleResult()).intValue();
        
    }
    
        public List<Ocorrencia> getOcorrenciaSemAvaliacao(){
        
        EntityManager em = getEntityManager();
        
        Query q = em.createNativeQuery("Select * from Ocorrencia o where o.idOcorrencia not in (select a.idOcorrencia from Avaliacao a where o.idOcorrencia = a.idOcorrencia)", Ocorrencia.class);
        
        return q.getResultList();
    }
        
        
     public List<Ocorrencia> getOcorrenciaSemAcompanhamento() {

        EntityManager em = getEntityManager();

        Query q = em.createNativeQuery("Select * from Ocorrencia o where o.idOcorrencia not in (select a.idOcorrencia from Avaliacao a where o.idOcorrencia = a.idOcorrencia) and o.idOcorrencia not in (select ac.idOcorrencia from Acompanhamento ac where o.idOcorrencia = ac.idOcorrencia)", Ocorrencia.class);

        return q.getResultList();
    }


    
}
