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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "telorgaoresp")
@NamedQueries({
    @NamedQuery(name = "Telorgaoresp.findAll", query = "SELECT t FROM Telorgaoresp t")})
public class Telorgaoresp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTel")
    private Integer idTel;
    @Basic(optional = false)
    @Column(name = "dddTel")
    private String dddTel;
    @Column(name = "telefone")
    private String telefone;
    @JoinColumn(name = "idOrgResp", referencedColumnName = "idOrgaoResp")
    @ManyToOne(optional = false)
    private Orgaoresp idOrgResp;

    public Telorgaoresp() {
    }

    public Telorgaoresp(Integer idTel) {
        this.idTel = idTel;
    }

    public Telorgaoresp(Integer idTel, String dddTel) {
        this.idTel = idTel;
        this.dddTel = dddTel;
    }

    public Integer getIdTel() {
        return idTel;
    }

    public void setIdTel(Integer idTel) {
        this.idTel = idTel;
    }

    public String getDddTel() {
        return dddTel;
    }

    public void setDddTel(String dddTel) {
        this.dddTel = dddTel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
        hash += (idTel != null ? idTel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telorgaoresp)) {
            return false;
        }
        Telorgaoresp other = (Telorgaoresp) object;
        if ((this.idTel == null && other.idTel != null) || (this.idTel != null && !this.idTel.equals(other.idTel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Telorgaoresp[ idTel=" + idTel + " ]";
    }
    
}
