/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.UsuarioDAO;
import br.com.vigilanciasocial.ouvidoria.entity.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Elton
 */
@ManagedBean
@SessionScoped
public class MBLogin {
    
    Usuario usuarioLogado = new Usuario();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    UsuarioDAO usuarioDAO = new UsuarioDAO(emf);

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    public String dologin(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        String email = usuarioLogado.getEmail();
        String senha = usuarioLogado.getSenha();
        
        Usuario u = usuarioDAO.logar(email, senha);
        
        if(u != null){
            this.usuarioLogado = u;
            
            return "ouvidoria/servicos.jsf?faces-redirect=true"; 
        }else{
            FacesMessage message = new FacesMessage("Erro","Usuário ou Senha Inválidos!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
            return "login";
        }
        
    }  
    
    public String doLogout(){
        
        FacesContext.getCurrentInstance().getExternalContext();
        return "index";
    }

    public MBLogin() {
    }
    
}
