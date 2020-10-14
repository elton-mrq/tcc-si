/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "encaminhar")
@NamedQueries({
    @NamedQuery(name = "Encaminhar.findAll", query = "SELECT e FROM Encaminhar e")})
public class Encaminhar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idEncaminhamento")
    private Integer idEncaminhamento;
    @Basic(optional = false)
    @Column(name = "dtEncaminhamento")
    @Temporal(TemporalType.DATE)
    private Date dtEncaminhamento;
    @JoinColumn(name = "Ocorrencia_idOcorrencia", referencedColumnName = "idOcorrencia")
    @ManyToOne(optional = false)
    private Ocorrencia ocorrenciaidOcorrencia;
    @JoinColumn(name = "Orgaoresp_idOrgaoresp", referencedColumnName = "idOrgaoResp")
    @ManyToOne(optional = false)
    private Orgaoresp orgaorespidOrgaoresp;

    public Encaminhar() {
    }

    public Encaminhar(Integer idEncaminhamento) {
        this.idEncaminhamento = idEncaminhamento;
    }

    public Encaminhar(Integer idEncaminhamento, Date dtEncaminhamento) {
        this.idEncaminhamento = idEncaminhamento;
        this.dtEncaminhamento = dtEncaminhamento;
    }

    public Integer getIdEncaminhamento() {
        return idEncaminhamento;
    }

    public void setIdEncaminhamento(Integer idEncaminhamento) {
        this.idEncaminhamento = idEncaminhamento;
    }

    public Date getDtEncaminhamento() {
        return dtEncaminhamento;
    }

    public void setDtEncaminhamento(Date dtEncaminhamento) {
        this.dtEncaminhamento = dtEncaminhamento;
    }

    public Ocorrencia getOcorrenciaidOcorrencia() {
        return ocorrenciaidOcorrencia;
    }

    public void setOcorrenciaidOcorrencia(Ocorrencia ocorrenciaidOcorrencia) {
        this.ocorrenciaidOcorrencia = ocorrenciaidOcorrencia;
    }

    public Orgaoresp getOrgaorespidOrgaoresp() {
        return orgaorespidOrgaoresp;
    }

    public void setOrgaorespidOrgaoresp(Orgaoresp orgaorespidOrgaoresp) {
        this.orgaorespidOrgaoresp = orgaorespidOrgaoresp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEncaminhamento != null ? idEncaminhamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encaminhar)) {
            return false;
        }
        Encaminhar other = (Encaminhar) object;
        if ((this.idEncaminhamento == null && other.idEncaminhamento != null) || (this.idEncaminhamento != null && !this.idEncaminhamento.equals(other.idEncaminhamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Encaminhar[ idEncaminhamento=" + idEncaminhamento + " ]";
    }
    
}
