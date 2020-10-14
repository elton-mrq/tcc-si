/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.AgressorDAO;
import br.com.vigilanciasocial.ouvidoria.business.EncaminharDAO;
import br.com.vigilanciasocial.ouvidoria.business.EndvitimaDAO;
import br.com.vigilanciasocial.ouvidoria.business.OcorrenciaDAO;
import br.com.vigilanciasocial.ouvidoria.business.OrgaorespDAO;
import br.com.vigilanciasocial.ouvidoria.business.SegmentoDAO;
import br.com.vigilanciasocial.ouvidoria.business.TelvitmaDAO;
import br.com.vigilanciasocial.ouvidoria.business.VitimaDAO;
import br.com.vigilanciasocial.ouvidoria.business.exceptions.IllegalOrphanException;
import br.com.vigilanciasocial.ouvidoria.entity.AbstractEntity;
import br.com.vigilanciasocial.ouvidoria.entity.Agressor;
import br.com.vigilanciasocial.ouvidoria.entity.Encaminhar;
import br.com.vigilanciasocial.ouvidoria.entity.Endvitima;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp;
import br.com.vigilanciasocial.ouvidoria.entity.Segmento;
import br.com.vigilanciasocial.ouvidoria.entity.Telvitma;
import br.com.vigilanciasocial.ouvidoria.entity.Vitima;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Elton
 */
@ManagedBean
@ViewScoped
public class MBOcorrencia extends AbstractEntity {

    private String orgSelecionado;
    private String agressorSelecionado;
    private String segSelecionado;
    Ocorrencia ocorrencia = new Ocorrencia();
    Agressor agressor = new Agressor();
    Segmento segmento = new Segmento();
    Orgaoresp orgResp = new Orgaoresp();
    Vitima vitima = new Vitima();
    Endvitima endVitima = new Endvitima();
    Telvitma tel1 = new Telvitma();
    Telvitma tel2 = new Telvitma();
    Encaminhar encaminhamento = new Encaminhar();
    List agressores = new ArrayList();
    List segmentos = new ArrayList();
    List orgaos = new ArrayList();
    List ocorrenciasSemAcomp = new ArrayList();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    OcorrenciaDAO ocorrenciaAdo = new OcorrenciaDAO(emf);
    AgressorDAO agressorAdo = new AgressorDAO(emf);
    SegmentoDAO segmentoAdo = new SegmentoDAO(emf);
    OrgaorespDAO orgaoAdo = new OrgaorespDAO(emf);
    VitimaDAO vitimaAdo = new VitimaDAO(emf);
    EndvitimaDAO endVitimaAdo = new EndvitimaDAO(emf);
    TelvitmaDAO telVitimaAdo = new TelvitmaDAO(emf);
    EncaminharDAO encaminhamentoAdo = new EncaminharDAO(emf);
    private boolean exibeConsulta;
    private boolean exibeOcorrencia;
    private boolean exibeBotao;
    private boolean exibeTab;
    private int ativaTab;
    private boolean novaVitima;

    

    public String getOrgSelecionado() {
        return orgSelecionado;
    }

    public void setOrgSelecionado(String orgSelecionado) {
        this.orgSelecionado = orgSelecionado;
    }

    public boolean isNovaOcorrencia() {
        return novaVitima;
    }

    public void setNovaOcorrencia(boolean novaOcorrencia) {
        this.novaVitima = novaOcorrencia;
    }
       
    public int getAtivaTab() {
        return ativaTab;
    }

    public void setAtivaTab(int ativaTab) {
        this.ativaTab = ativaTab;
    }

    public boolean isExibeTab() {
        return exibeTab;
    }

    public void setExibeTab(boolean exibeTab) {
        this.exibeTab = exibeTab;
    }

    public boolean isExibeBotao() {
        return exibeBotao;
    }

    public void setExibeBotao(boolean exibeBotao) {
        this.exibeBotao = exibeBotao;
    }

    public String getAgressorSelecionado() {
        return agressorSelecionado;
    }

    public void setAgressorSelecionado(String agressorSelecionado) {
        this.agressorSelecionado = agressorSelecionado;
    }

    public String getSegSelecionado() {
        return segSelecionado;
    }

    public void setSegSelecionado(String segSelecionado) {
        this.segSelecionado = segSelecionado;
    }

    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public Agressor getAgressor() {
        return agressor;
    }

    public void setAgressor(Agressor agressor) {
        this.agressor = agressor;
    }

    public Segmento getSegmento() {
        return segmento;
    }

    public void setSegmento(Segmento segmento) {
        this.segmento = segmento;
    }

    public Orgaoresp getOrgResp() {
        return orgResp;
    }

    public Vitima getVitima() {
        return vitima;
    }

    public void setVitima(Vitima vitima) {
        this.vitima = vitima;
    }

    public Endvitima getEndVitima() {
        return endVitima;
    }

    public void setEndVitima(Endvitima endVitima) {
        this.endVitima = endVitima;
    }

    public Telvitma getTel1() {
        return tel1;
    }

    public void setTel1(Telvitma tel1) {
        this.tel1 = tel1;
    }

    public Telvitma getTel2() {
        return tel2;
    }

    public void setTel2(Telvitma tel2) {
        this.tel2 = tel2;
    }

    public void setOrgResp(Orgaoresp orgResp) {
        this.orgResp = orgResp;
    }

    public Encaminhar getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(Encaminhar encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public List getAgressores() {
        return agressores;
    }

    public void setAgressores(List agressores) {
        this.agressores = agressores;
    }

    public List getOcorrenciasSemAcomp() {
        return ocorrenciasSemAcomp;
    }

    public void setOcorrenciasSemAcomp(List ocorrenciasSemAcomp) {
        this.ocorrenciasSemAcomp = ocorrenciasSemAcomp;
    }
    
    

    public List getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List segmentos) {
        this.segmentos = segmentos;
    }

    public List getOrgaos() {
        return orgaos;
    }

    public void setOrgaos(List orgaos) {
        this.orgaos = orgaos;
    }

    public OcorrenciaDAO getOcorrenciaAdo() {
        return ocorrenciaAdo;
    }

    public void setOcorrenciaAdo(OcorrenciaDAO ocorrenciaAdo) {
        this.ocorrenciaAdo = ocorrenciaAdo;
    }

    public AgressorDAO getAgressorAdo() {
        return agressorAdo;
    }

    public void setAgressorAdo(AgressorDAO agressorAdo) {
        this.agressorAdo = agressorAdo;
    }

    public SegmentoDAO getSegmentoAdo() {
        return segmentoAdo;
    }

    public void setSegmentoAdo(SegmentoDAO segmentoAdo) {
        this.segmentoAdo = segmentoAdo;
    }

    public OrgaorespDAO getOrgaoAdo() {
        return orgaoAdo;
    }

    public void setOrgaoAdo(OrgaorespDAO orgaoAdo) {
        this.orgaoAdo = orgaoAdo;
    }

    public VitimaDAO getVitimaAdo() {
        return vitimaAdo;
    }

    public void setVitimaAdo(VitimaDAO vitimaAdo) {
        this.vitimaAdo = vitimaAdo;
    }

    public EndvitimaDAO getEndVitimaAdo() {
        return endVitimaAdo;
    }

    public void setEndVitimaAdo(EndvitimaDAO endVitimaAdo) {
        this.endVitimaAdo = endVitimaAdo;
    }

    public TelvitmaDAO getTelVitimaAdo() {
        return telVitimaAdo;
    }

    public void setTelVitimaAdo(TelvitmaDAO telVitimaAdo) {
        this.telVitimaAdo = telVitimaAdo;
    }

    public EncaminharDAO getEncaminhamentoAdo() {
        return encaminhamentoAdo;
    }

    public void setEncaminhamentoAdo(EncaminharDAO encaminhamentoAdo) {
        this.encaminhamentoAdo = encaminhamentoAdo;
    }


    public boolean isExibeConsulta() {
        return exibeConsulta;
    }

    public void setExibeConsulta(boolean exibeConsulta) {
        this.exibeConsulta = exibeConsulta;
    }

    public boolean isExibeOcorrencia() {
        return exibeOcorrencia;
    }

    public void setExibeOcorrencia(boolean exibeOcorrencia) {
        this.exibeOcorrencia = exibeOcorrencia;
    }

    //Método carrega os dados da vitima em seus respctivos objetos    
    public void carregaVitima() {

        String nomeVitima;
        Date dtNasc;
        FacesContext context = FacesContext.getCurrentInstance();

        nomeVitima = vitima.getNomeVitima();
        dtNasc = vitima.getDtNasc();

        vitima = vitimaAdo.getVitima(nomeVitima, dtNasc);

        if (vitima != null) {

            endVitima = endVitimaAdo.getEndVitima(vitima);
            
            List<Telvitma> t = telVitimaAdo.telefones(vitima);

            if (!t.isEmpty()) {
                if (t.size() == 2) {
                    tel1.setIdVitima(t.get(0).getIdVitima());
                    tel1.setIdTelVitma(t.get(0).getIdTelVitma());
                    tel1.setDdd(t.get(0).getDdd());
                    tel1.setTelVitima(t.get(0).getTelVitima());

                    tel2.setIdVitima(t.get(1).getIdVitima());
                    tel2.setIdTelVitma(t.get(1).getIdTelVitma());
                    tel2.setDdd(t.get(1).getDdd());
                    tel2.setTelVitima(t.get(1).getTelVitima());
                } else if (t.size() == 1) {
                    if (t.get(0).getTelVitima().length() == 9) {
                        tel1.setIdVitima(t.get(0).getIdVitima());
                        tel1.setIdTelVitma(t.get(0).getIdTelVitma());
                        tel1.setDdd(t.get(0).getDdd());
                        tel1.setTelVitima(t.get(0).getTelVitima());
                    } else {
                        tel2.setIdVitima(t.get(0).getIdVitima());
                        tel2.setIdTelVitma(t.get(0).getIdTelVitma());
                        tel2.setDdd(t.get(0).getDdd());
                        tel2.setTelVitima(t.get(0).getTelVitima());
                    }
                }
            }
            FacesMessage message = new FacesMessage("Confirmação", "Vitima Localizada!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
            exibeOcorrencia = true;
            exibeConsulta = false;
            exibeBotao = true;
            exibeTab = false;
            novaVitima = false;
        } else {
            FacesMessage message = new FacesMessage("Confirmação", "A Vitima não foi Localizada!");
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            context.addMessage(null, message);
            cancelaVitima();
            this.vitima = new Vitima();
        }

    }

    //Caso não se tratar da mesma pessoa pesquisada
    public void cancelaVitima() {
        exibeOcorrencia = true;
        exibeConsulta = false;
        exibeBotao = false;
        exibeTab = true;
        ativaTab = 0;
        novaVitima = true;
    }

    //Caso se tratar da mesma pessoa pesquisada
    public void confirmaVitma() {
        exibeTab = true;
        ativaTab = 0;
        exibeBotao = false;
        novaVitima = false;
    }

    //Método responsável por salvar uma nova ocorrencia (Caso a vitima ja existe
    //será editado os dados da vitima e uma nova ocorrencia será inserida para 
    //essa vitima.
    public String salvarOcorrencia() throws IllegalOrphanException, Exception {

        FacesContext context = FacesContext.getCurrentInstance();
        
        //Recuperando a Sessão do Usuario Logado               
        ExternalContext extContext
                = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) extContext.getSession(true);
        MBLogin loginBean = (MBLogin) session.getAttribute("mBLogin");

        Integer idAgressor = Integer.parseInt(agressorSelecionado);
        agressor = agressorAdo.findAgressor(idAgressor);

        Integer idSegmento = Integer.parseInt(segSelecionado);
        segmento = segmentoAdo.findSegmento(idSegmento);

        Integer idOrgao = Integer.parseInt(orgSelecionado);
        orgResp = orgaoAdo.findOrgaoresp(idOrgao);

        encaminhamento.setOrgaorespidOrgaoresp(orgResp);

        ocorrencia.setIdUsuario(loginBean.usuarioLogado);

        ocorrencia.setSegmentoidSegmento(segmento);

        ocorrencia.setIdAgressor(agressor); 

        //Salvar Vitima
        if (novaVitima == true) {

            vitimaAdo.create(vitima);

            int idVitima = vitimaAdo.getUltVitma();
            vitima = vitimaAdo.findVitima(idVitima);

            endVitima.setIdVitima(vitima);
            endVitimaAdo.create(endVitima);

            if (tel1 != null) {
                tel1.setIdVitima(vitima);
                telVitimaAdo.create(tel1);
            }

            ocorrencia.setIdVitma(vitima);

            ocorrenciaAdo.create(ocorrencia);

            int idOcorrencia = ocorrenciaAdo.getUltOcorrencia();
            ocorrencia = ocorrenciaAdo.findOcorrencia(idOcorrencia);

            encaminhamento.setOcorrenciaidOcorrencia(ocorrencia);
                       
            encaminhamento.setOcorrenciaidOcorrencia(ocorrencia);

            
           encaminhamentoAdo.create(encaminhamento);
           
           FacesMessage message = new FacesMessage("Sucesso", "Ocorrência gravada com sucesso!");
           message.setSeverity(FacesMessage.SEVERITY_INFO);
           context.addMessage(null, message);

            return "ocorrencias.xhtml?faces-redirect=true";
            //Editar Vitima e salvar nova ocorrencia
        } else {

            vitimaAdo.edit(vitima);

            if (endVitima != null) {

                endVitimaAdo.edit(endVitima);
            } else {
                endVitimaAdo.create(endVitima);
            }
            if (tel1.getIdTelVitma() != null) {
                tel1.setIdVitima(vitima);
                telVitimaAdo.edit(tel1);
            } else {
                tel1.setIdVitima(vitima);
                telVitimaAdo.create(tel1);
            }

            if (tel2.getIdTelVitma() != null) {
                tel2.setIdVitima(vitima);
                telVitimaAdo.edit(tel2);
            } else {
                tel2.setIdVitima(vitima);
                telVitimaAdo.create(tel2);
            }

            ocorrencia.setIdVitma(vitima);

            ocorrenciaAdo.create(ocorrencia);

            int idOcorrencia = ocorrenciaAdo.getUltOcorrencia();
            ocorrencia = ocorrenciaAdo.findOcorrencia(idOcorrencia);

            encaminhamento.setOcorrenciaidOcorrencia(ocorrencia);

            encaminhamentoAdo.create(encaminhamento);

            FacesMessage message = new FacesMessage("Sucesso", "Ocorrência gravada com sucesso!");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            context.addMessage(null, message);
            return "ocorrencias.xhtml?faces-redirect=true";
        }
    }


    public MBOcorrencia() {
        agressores = agressorAdo.findAgressorEntities();
        segmentos = segmentoAdo.findSegmentoEntities();
        orgaos = orgaoAdo.findOrgaorespEntities();
        ocorrenciasSemAcomp = ocorrenciaAdo.getOcorrenciaSemAcompanhamento();
        exibeConsulta = true;
        exibeOcorrencia = false;
    }

}
