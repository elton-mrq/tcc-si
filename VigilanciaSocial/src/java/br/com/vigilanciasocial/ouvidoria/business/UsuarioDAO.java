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
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import br.com.vigilanciasocial.ouvidoria.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Elton
 */
public class UsuarioDAO implements Serializable {

    public UsuarioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getOcorrenciaList() == null) {
            usuario.setOcorrenciaList(new ArrayList<Ocorrencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ocorrencia> attachedOcorrenciaList = new ArrayList<Ocorrencia>();
            for (Ocorrencia ocorrenciaListOcorrenciaToAttach : usuario.getOcorrenciaList()) {
                ocorrenciaListOcorrenciaToAttach = em.getReference(ocorrenciaListOcorrenciaToAttach.getClass(), ocorrenciaListOcorrenciaToAttach.getIdOcorrencia());
                attachedOcorrenciaList.add(ocorrenciaListOcorrenciaToAttach);
            }
            usuario.setOcorrenciaList(attachedOcorrenciaList);
            em.persist(usuario);
            for (Ocorrencia ocorrenciaListOcorrencia : usuario.getOcorrenciaList()) {
                Usuario oldIdUsuarioOfOcorrenciaListOcorrencia = ocorrenciaListOcorrencia.getIdUsuario();
                ocorrenciaListOcorrencia.setIdUsuario(usuario);
                ocorrenciaListOcorrencia = em.merge(ocorrenciaListOcorrencia);
                if (oldIdUsuarioOfOcorrenciaListOcorrencia != null) {
                    oldIdUsuarioOfOcorrenciaListOcorrencia.getOcorrenciaList().remove(ocorrenciaListOcorrencia);
                    oldIdUsuarioOfOcorrenciaListOcorrencia = em.merge(oldIdUsuarioOfOcorrenciaListOcorrencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<Ocorrencia> ocorrenciaListOld = persistentUsuario.getOcorrenciaList();
            List<Ocorrencia> ocorrenciaListNew = usuario.getOcorrenciaList();
            List<String> illegalOrphanMessages = null;
            for (Ocorrencia ocorrenciaListOldOcorrencia : ocorrenciaListOld) {
                if (!ocorrenciaListNew.contains(ocorrenciaListOldOcorrencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ocorrencia " + ocorrenciaListOldOcorrencia + " since its idUsuario field is not nullable.");
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
            usuario.setOcorrenciaList(ocorrenciaListNew);
            usuario = em.merge(usuario);
            for (Ocorrencia ocorrenciaListNewOcorrencia : ocorrenciaListNew) {
                if (!ocorrenciaListOld.contains(ocorrenciaListNewOcorrencia)) {
                    Usuario oldIdUsuarioOfOcorrenciaListNewOcorrencia = ocorrenciaListNewOcorrencia.getIdUsuario();
                    ocorrenciaListNewOcorrencia.setIdUsuario(usuario);
                    ocorrenciaListNewOcorrencia = em.merge(ocorrenciaListNewOcorrencia);
                    if (oldIdUsuarioOfOcorrenciaListNewOcorrencia != null && !oldIdUsuarioOfOcorrenciaListNewOcorrencia.equals(usuario)) {
                        oldIdUsuarioOfOcorrenciaListNewOcorrencia.getOcorrenciaList().remove(ocorrenciaListNewOcorrencia);
                        oldIdUsuarioOfOcorrenciaListNewOcorrencia = em.merge(oldIdUsuarioOfOcorrenciaListNewOcorrencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ocorrencia> ocorrenciaListOrphanCheck = usuario.getOcorrenciaList();
            for (Ocorrencia ocorrenciaListOrphanCheckOcorrencia : ocorrenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Ocorrencia " + ocorrenciaListOrphanCheckOcorrencia + " in its ocorrenciaList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuario logar(String email, String senha){
        
        EntityManager em = getEntityManager();
        
        try {
           // String sql = "Select * From usuario where email = '" + email + "'";
          //  Query q = em.createQuery("sql");
            Query q = em.createQuery("Select u from Usuario u where u.email = :email and u.senha = :senha");
            q.setParameter("email", email);
            q.setParameter("senha", senha);
            Usuario u = (Usuario) q.getSingleResult();
            return u;
        } catch (Exception e) {
            Usuario u = null;
            return u;
        }finally{
            em.close();
        }
   }

    
}
