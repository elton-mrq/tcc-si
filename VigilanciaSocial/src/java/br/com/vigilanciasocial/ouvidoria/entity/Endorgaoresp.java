/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "endorgaoresp")
@NamedQueries({
    @NamedQuery(name = "Endorgaoresp.findAll", query = "SELECT e FROM Endorgaoresp e")})
public class Endorgaoresp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEndOrgaoResp")
    private Integer idEndOrgaoResp;
    @Column(name = "tipoLog")
    private String tipoLog;
    @Column(name = "nomeLog")
    private String nomeLog;
    @Column(name = "nrLog")
    private String nrLog;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cep")
    private String cep;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
    @JoinColumn(name = "idOrgResp", referencedColumnName = "idOrgaoResp")
    @OneToOne(optional = false)
    private Orgaoresp idOrgResp;

    public Endorgaoresp() {
    }

    public Endorgaoresp(Integer idEndOrgaoResp) {
        this.idEndOrgaoResp = idEndOrgaoResp;
    }

    public Integer getIdEndOrgaoResp() {
        return idEndOrgaoResp;
    }

    public void setIdEndOrgaoResp(Integer idEndOrgaoResp) {
        this.idEndOrgaoResp = idEndOrgaoResp;
    }

    public String getTipoLog() {
        return tipoLog;
    }

    public void setTipoLog(String tipoLog) {
        this.tipoLog = tipoLog;
    }

    public String getNomeLog() {
        return nomeLog;
    }

    public void setNomeLog(String nomeLog) {
        this.nomeLog = nomeLog;
    }

    public String getNrLog() {
        return nrLog;
    }

    public void setNrLog(String nrLog) {
        this.nrLog = nrLog;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Orgaoresp getIdOrgResp() {
        return idOrgResp;
    }

    public void setIdOrgResp(Orgaoresp idOrgResp) {
        this.idOrgResp = idOrgResp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEndOrgaoResp != null ? idEndOrgaoResp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endorgaoresp)) {
            return false;
        }
        Endorgaoresp other = (Endorgaoresp) object;
        if ((this.idEndOrgaoResp == null && other.idEndOrgaoResp != null) || (this.idEndOrgaoResp != null && !this.idEndOrgaoResp.equals(other.idEndOrgaoResp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Endorgaoresp[ idEndOrgaoResp=" + idEndOrgaoResp + " ]";
    }
    
}
