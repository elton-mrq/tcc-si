/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.UsuarioDAO;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;
import br.com.vigilanciasocial.ouvidoria.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;



@ManagedBean
@ViewScoped
public class MBUsuario {

    Usuario usuario = new Usuario();
    List usuarios = new ArrayList();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    UsuarioDAO usuarioAdo = new UsuarioDAO(emf);
    List<Usuario> listUsuario;

    public List getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
    }

        
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }
    
    public List listarUsuario(){
        
        listUsuario = usuarioAdo.findUsuarioEntities();
        return this.listUsuario;
    }
    
    public void gravaUsuario(){
        
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            usuarioAdo.create(usuario);
            FacesMessage message = new FacesMessage("Sucesso", "Usuário Cadastrado com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
            
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Erro", "Não foi possível incluir o usuário!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
            
        }        
        usuario = new Usuario();
    }
    
     public void atualizarUsuario(){
       
        FacesContext context = FacesContext.getCurrentInstance();
       
        try {
            UsuarioDAO user = new UsuarioDAO(emf);
            user.edit(this.usuario);
            FacesMessage message = new FacesMessage("Sucesso", "Usuário Alterado com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage("Erro", "Usuário não Alterado!" + ex);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
        }

    }
 
    
    public void removerUsuario(Usuario u) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        //Recuperando a Sessão do Usuario Logado
        ExternalContext extContext
                = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        MBLogin loginBean = (MBLogin) session.getAttribute("mBLogin");

        if (usuario.getIdUsuario() == loginBean.usuarioLogado.getIdUsuario()) {
            FacesMessage message = new FacesMessage("Impossivel Remover:", "Usuário Logado!");
            message.setSeverity(FacesMessage.SEVERITY_FATAL);
            context.addMessage(null, message);
        } else {
            try {
                usuarioAdo.destroy(u.getIdUsuario());
                FacesMessage message = new FacesMessage("Sucesso", "Usuário Removido com Sucesso!");
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                context.addMessage(null, message);
                usuarios = usuarioAdo.findUsuarioEntities();
            } catch (IllegalOrphanException ex) {
                FacesMessage message = new FacesMessage("Erro", "Usuário Viculado a Ocorrência!");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(null, message);

            } catch (NonexistentEntityException ex) {
                FacesMessage message = new FacesMessage("Erro", "Usuário já Removido!");
                message.setSeverity(FacesMessage.SEVERITY_FATAL);
                context.addMessage(null, message);
            }

        }

    }
    
    public String cancelarInclusao(){
        return "servicos.xhtml?faces-redirect=true";
    }
    
    
    
    public MBUsuario() {
        usuarios = new UsuarioDAO(emf).findUsuarioEntities();
        usuario = new Usuario();
    }
    
    
    
}
