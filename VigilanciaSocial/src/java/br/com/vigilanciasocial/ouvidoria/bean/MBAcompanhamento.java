/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.AcompanhamentoDAO;
import br.com.vigilanciasocial.ouvidoria.business.OcorrenciaDAO;
import br.com.vigilanciasocial.ouvidoria.entity.Acompanhamento;
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
public class MBAcompanhamento {

    List ocorrencias;
    List acompanhamentos;
    Ocorrencia ocorrencia = new Ocorrencia();
    Acompanhamento acompanhamento = new Acompanhamento();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    AcompanhamentoDAO acompAdo = new AcompanhamentoDAO(emf);
    OcorrenciaDAO ocorrenciaAdo = new OcorrenciaDAO(emf);
    boolean exibeTable;
    boolean exibePainel;

    public boolean isExibeTable() {
        return exibeTable;
    }

    public void setExibeTable(boolean exibeTable) {
        this.exibeTable = exibeTable;
    }

    public boolean isExibePainel() {
        return exibePainel;
    }

    public void setExibePainel(boolean exibePainel) {
        this.exibePainel = exibePainel;
    }
    
    

    public List getOcorrencias() {
        return ocorrencias;
    }

    public void setOcorrencias(List ocorrencias) {
        this.ocorrencias = ocorrencias;
    }

    public List getAcompanhamentos() {
        return acompanhamentos;
    }

    public void setAcompanhamentos(List acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Acompanhamento getAcompanhamento() {
        return acompanhamento;
    }

    public void setAcompanhamento(Acompanhamento acompanhamento) {
        this.acompanhamento = acompanhamento;
    }
    
    public Ocorrencia carregaOcorrencia(Ocorrencia o){
        this.ocorrencia = o;
        exibeTable = false;
        exibePainel = true;
        return ocorrencia;
    }
    
    public void cancelarAcompanhamento(){
    exibeTable = true;
    exibePainel = false;
   // return 
}
    
    public String salvarAcompanhamento(){
        FacesContext context = FacesContext.getCurrentInstance();
        this.acompanhamento.setIdOcorrencia(ocorrencia);
        try {
            acompAdo.create(acompanhamento);
            FacesMessage message = new FacesMessage("Sucesso", "Acompanhamento Registrado com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage("Erro", "Acompanhamento não Registrado!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
        }
        
        return "acompanhamento.xhtml?faces-redirect=true";
    }
    
    public MBAcompanhamento() {
        ocorrencias = ocorrenciaAdo.getOcorrenciaSemAvaliacao();
        acompanhamentos = acompAdo.findAcompanhamentoEntities();
        exibeTable = true;
        exibePainel = false;
    }
    
}
