/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "orgaoresp")
@NamedQueries({
    @NamedQuery(name = "Orgaoresp.findAll", query = "SELECT o FROM Orgaoresp o")})
public class Orgaoresp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrgaoResp")
    private Integer idOrgaoResp;
    @Basic(optional = false)
    @Column(name = "nomeOrgao")
    private String nomeOrgao;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "codSuas")
    private BigInteger codSuas;
    @Column(name = "tipoRede")
    private String tipoRede;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOrgResp")
    private List<Telorgaoresp> telorgaorespList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orgaorespidOrgaoresp")
    private List<Encaminhar> encaminharList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idOrgResp")
    private Endorgaoresp endorgaoresp;

    public Orgaoresp() {
    }

    public Orgaoresp(Integer idOrgaoResp) {
        this.idOrgaoResp = idOrgaoResp;
    }

    public Orgaoresp(Integer idOrgaoResp, String nomeOrgao, String email) {
        this.idOrgaoResp = idOrgaoResp;
        this.nomeOrgao = nomeOrgao;
        this.email = email;
    }

    public Integer getIdOrgaoResp() {
        return idOrgaoResp;
    }

    public void setIdOrgaoResp(Integer idOrgaoResp) {
        this.idOrgaoResp = idOrgaoResp;
    }

    public String getNomeOrgao() {
        return nomeOrgao;
    }

    public void setNomeOrgao(String nomeOrgao) {
        this.nomeOrgao = nomeOrgao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getCodSuas() {
        return codSuas;
    }

    public void setCodSuas(BigInteger codSuas) {
        this.codSuas = codSuas;
    }

    public String getTipoRede() {
        return tipoRede;
    }

    public void setTipoRede(String tipoRede) {
        this.tipoRede = tipoRede;
    }

    public List<Telorgaoresp> getTelorgaorespList() {
        return telorgaorespList;
    }

    public void setTelorgaorespList(List<Telorgaoresp> telorgaorespList) {
        this.telorgaorespList = telorgaorespList;
    }

    public List<Encaminhar> getEncaminharList() {
        return encaminharList;
    }

    public void setEncaminharList(List<Encaminhar> encaminharList) {
        this.encaminharList = encaminharList;
    }

    public Endorgaoresp getEndorgaoresp() {
        return endorgaoresp;
    }

    public void setEndorgaoresp(Endorgaoresp endorgaoresp) {
        this.endorgaoresp = endorgaoresp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrgaoResp != null ? idOrgaoResp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orgaoresp)) {
            return false;
        }
        Orgaoresp other = (Orgaoresp) object;
        if ((this.idOrgaoResp == null && other.idOrgaoResp != null) || (this.idOrgaoResp != null && !this.idOrgaoResp.equals(other.idOrgaoResp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Orgaoresp[ idOrgaoResp=" + idOrgaoResp + " ]";
    }
    
}
