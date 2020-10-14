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
@Table(name = "telvitma")
@NamedQueries({
    @NamedQuery(name = "Telvitma.findAll", query = "SELECT t FROM Telvitma t")})
public class Telvitma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTelVitma")
    private Integer idTelVitma;
    @Column(name = "ddd")
    private String ddd;
    @Column(name = "telVitima")
    private String telVitima;
    @JoinColumn(name = "idVitima", referencedColumnName = "idVitima")
    @ManyToOne(optional = false)
    private Vitima idVitima;

    public Telvitma() {
    }

    public Telvitma(Integer idTelVitma) {
        this.idTelVitma = idTelVitma;
    }

    public Integer getIdTelVitma() {
        return idTelVitma;
    }

    public void setIdTelVitma(Integer idTelVitma) {
        this.idTelVitma = idTelVitma;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getTelVitima() {
        return telVitima;
    }

    public void setTelVitima(String telVitima) {
        this.telVitima = telVitima;
    }

    public Vitima getIdVitima() {
        return idVitima;
    }

    public void setIdVitima(Vitima idVitima) {
        this.idVitima = idVitima;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTelVitma != null ? idTelVitma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telvitma)) {
            return false;
        }
        Telvitma other = (Telvitma) object;
        if ((this.idTelVitma == null && other.idTelVitma != null) || (this.idTelVitma != null && !this.idTelVitma.equals(other.idTelVitma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Telvitma[ idTelVitma=" + idTelVitma + " ]";
    }
    
}
