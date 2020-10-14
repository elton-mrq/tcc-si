/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.EndorgaorespDAO;
import br.com.vigilanciasocial.ouvidoria.business.OrgaorespDAO;
import br.com.vigilanciasocial.ouvidoria.business.TelorgaorespDAO;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.NonexistentEntityException;

import br.com.vigilanciasocial.ouvidoria.entity.Endorgaoresp;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import br.com.vigilanciasocial.ouvidoria.entity.Telorgaoresp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MBOrgResp {
    
    Orgaoresp orgResp = new Orgaoresp();
    Endorgaoresp endOrgResp = new Endorgaoresp();
    Telorgaoresp tel1 = new Telorgaoresp();
    Telorgaoresp tel2 = new Telorgaoresp();
    List orgaos = new ArrayList();
    List tipoRedes = new ArrayList();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    OrgaorespDAO orgRespAdo = new OrgaorespDAO(emf);
    EndorgaorespDAO endAdo = new EndorgaorespDAO(emf);
    TelorgaorespDAO telAdo = new TelorgaorespDAO(emf);

    public Orgaoresp getOrgResp() {
        return orgResp;
    }

    public void setOrgResp(Orgaoresp orgResp) {
        this.orgResp = orgResp;
    }

    public Endorgaoresp getEndOrgResp() {
        return endOrgResp;
    }

    public void setEndOrgResp(Endorgaoresp endOrgResp) {
        this.endOrgResp = endOrgResp;
    }

    public Telorgaoresp getTel1() {
        return tel1;
    }

    public void setTel1(Telorgaoresp tel1) {
        this.tel1 = tel1;
    }

    public Telorgaoresp getTel2() {
        return tel2;
    }

    public void setTel2(Telorgaoresp tel2) {
        this.tel2 = tel2;
    }

    public OrgaorespDAO getOrgRespAdo() {
        return orgRespAdo;
    }

    public void setOrgRespAdo(OrgaorespDAO orgRespAdo) {
        this.orgRespAdo = orgRespAdo;
    }

    public EndorgaorespDAO getEndAdo() {
        return endAdo;
    }

    public void setEndAdo(EndorgaorespDAO endAdo) {
        this.endAdo = endAdo;
    }

    public TelorgaorespDAO getTelAdo() {
        return telAdo;
    }

    public void setTelAdo(TelorgaorespDAO telAdo) {
        this.telAdo = telAdo;
    }
    
    

    public List getOrgaos() {
        return orgaos;
    }

    public void setOrgaos(List orgaos) {
        this.orgaos = orgaos;
    }

    public List getTipoRedes() {
        return tipoRedes;
    }

    public void setTipoRedes(List tipoRedes) {
        this.tipoRedes = tipoRedes;
    }
    
    

    public MBOrgResp() {
        orgaos = new OrgaorespDAO(emf).findOrgaorespEntities();
        
    }
    
    public void salvarOrgao(){
        
        FacesContext context = FacesContext.getCurrentInstance();
   
        orgRespAdo.create(orgResp); 
        
        int idOrg = orgRespAdo.getUltidorgao();
        orgResp = orgRespAdo.findOrgaoresp(idOrg);
    
        endOrgResp.setIdOrgResp(orgResp);
        try {
            endAdo.create(endOrgResp);
            tel1.setIdOrgResp(orgResp);
            telAdo.create(tel1);

            tel2.setIdOrgResp(orgResp);
            telAdo.create(tel2);
            FacesMessage message = new FacesMessage("Sucesso", "Órgão Registrado com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
            orgResp = new Orgaoresp();
            endOrgResp = new Endorgaoresp();
            tel1 = new Telorgaoresp();
            tel2 = new Telorgaoresp();
           // return "formCadOrgao?faces-redirect=true";
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(MBOrgResp.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage message = new FacesMessage("Erro", "Órgão não Registrado!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
           // return "formCadOrgao?faces-redirect=true";
        }
     
        
    }
    
    public String cancelarInclusao(){
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Cancelado", "Operação Cancelada!");
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage(null, message);
         return "servicos?faces-redirect=true";
    }
    
    public void CarregaTelEndOrgao(Orgaoresp orgao){
        
     this.orgResp = orgao;
     
     List<Telorgaoresp> telefones = telAdo.getfindTelorgaoresp(orgao);
     this.tel1.setIdTel(telefones.get(0).getIdTel());
     this.tel1.setDddTel(telefones.get(0).getDddTel());
     this.tel1.setTelefone(telefones.get(0).getTelefone());
     this.tel1.setIdOrgResp(orgResp);
     
     this.tel2.setIdTel(telefones.get(1).getIdTel());
     this.tel2.setDddTel(telefones.get(1).getDddTel());
     this.tel2.setTelefone(telefones.get(1).getTelefone());
     this.tel2.setIdOrgResp(orgResp);
     
     this.orgResp.setTelorgaorespList(telefones);
     
     Endorgaoresp endereco = endAdo.getEndOrgao(orgao);
     this.endOrgResp.setIdEndOrgaoResp(endereco.getIdEndOrgaoResp());
     this.endOrgResp.setTipoLog(endereco.getTipoLog());
     this.endOrgResp.setNomeLog(endereco.getNomeLog());
     this.endOrgResp.setNrLog(endereco.getNrLog());
     this.endOrgResp.setBairro(endereco.getBairro());
     this.endOrgResp.setCep(endereco.getCep());
     this.endOrgResp.setCidade(endereco.getCidade());
     this.endOrgResp.setUf(endereco.getUf());
     this.endOrgResp.setIdOrgResp(orgResp);
     
     this.orgResp.setEndorgaoresp(endOrgResp);
    }
    
    public void atualizaOrgao() throws NonexistentEntityException{
       FacesContext context = FacesContext.getCurrentInstance();
        try {
            orgRespAdo.edit(orgResp);
            endAdo.edit(endOrgResp);
            telAdo.edit(tel1);
            telAdo.edit(tel2);
            FacesMessage message = new FacesMessage("Orgão Responsável Atualizado com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
           
        } catch (Exception ex) {
            Logger.getLogger(MBOrgResp.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage message = new FacesMessage("Não foi possível alterar!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
        }
        
    }
    
    public String removeOrgao(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            endAdo.destroy(endOrgResp.getIdEndOrgaoResp());
            telAdo.destroy(tel1.getIdTel());
            telAdo.destroy(tel2.getIdTel());
            orgRespAdo.destroy(orgResp.getIdOrgaoResp());           
            FacesMessage message = new FacesMessage("Sucesso","Orgão Responsável Excluído com Sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
            orgaos = orgRespAdo.findOrgaorespEntities();
            return "formCadOrgao?faces-redirect=true";
        } catch (IllegalOrphanException e) {
             FacesMessage message = new FacesMessage("Erro","Orgão Responsável não Excluído!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
            return "formCadOrgao?faces-redirect=true";
        } catch (NonexistentEntityException e) {
            FacesMessage message = new FacesMessage("Erro","Orgão Responsável não Excluído!");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, message);
            return "formCadOrgao?faces-redirect=true";
        }
        
    }
    
}
