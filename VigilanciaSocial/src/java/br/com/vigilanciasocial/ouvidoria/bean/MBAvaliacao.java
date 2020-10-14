/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.AvaliacaoDAO;
import br.com.vigilanciasocial.ouvidoria.business.OcorrenciaDAO;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.entity.Avaliacao;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Elton
 */
@ManagedBean
@ViewScoped
public class MBAvaliacao {
    
    List ocorrencias;
    Ocorrencia ocorrencia = new Ocorrencia();
    Avaliacao avaliacao = new Avaliacao();
    boolean exibirTabel;
    boolean exibirPanel;
    List avaliadas;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    OcorrenciaDAO ocorrenciaAdo = new OcorrenciaDAO(emf);
    AvaliacaoDAO avaliacaoAdo = new AvaliacaoDAO(emf);

    public boolean isExibirTabel() {
        return exibirTabel;
    }

    public void setExibirTabel(boolean exibirTabel) {
        this.exibirTabel = exibirTabel;
    }

        
    public boolean isExibirPanel() {
        return exibirPanel;
    }

    public void setExibirPanel(boolean exibirPanel) {
        this.exibirPanel = exibirPanel;
    }

            
    public List getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public List getAvaliadas() {
        return avaliadas;
    }

    public void setAvaliadas(List avaliadas) {
        this.avaliadas = avaliadas;
    }
    
    
    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }
   
    
      public Ocorrencia carregaOcorrencia(Ocorrencia o) {
        this.ocorrencia = o;
        exibirTabel = false;
        exibirPanel = true;
        return ocorrencia;
    }
      
      public String salvarAvaliacao() throws Exception{
          
          FacesContext context = FacesContext.getCurrentInstance();
          
          this.avaliacao.setIdOcorrencia(ocorrencia);
        try {
            avaliacaoAdo.create(avaliacao);
             exibirTabel = true;
             exibirPanel = false;
             FacesMessage message = new FacesMessage("Avaliação Registrada com Sucesso!");
             message.setSeverity(FacesMessage.SEVERITY_INFO);
             context.addMessage("Sucesso", message);
             return "avaliacao.xhtml?faces-redirect=true";
        } catch (IllegalOrphanException ex) {
            FacesMessage message = new FacesMessage("Avaliação não Registrada!");
             message.setSeverity(FacesMessage.SEVERITY_ERROR);
             context.addMessage("Sucesso", message);
             return "avaliacao.xhtml?faces-redirect=true";
        }
          
           
      }

    public void cancelarAvaliacao(){
        exibirTabel = true;
        exibirPanel = false;
    }

    public MBAvaliacao() {
        
        ocorrencias = ocorrenciaAdo.getOcorrenciaSemAvaliacao();
        avaliadas = avaliacaoAdo.findAvaliacaoEntities();
        exibirTabel = true;
        exibirPanel = false;
    }
    
}
