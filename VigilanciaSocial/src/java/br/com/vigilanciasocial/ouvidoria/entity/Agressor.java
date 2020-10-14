/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "agressor")
@NamedQueries({
    @NamedQuery(name = "Agressor.findAll", query = "SELECT a FROM Agressor a")})
public class Agressor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAgressor")
    private Integer idAgressor;
    @Column(name = "descParentesco")
    private String descParentesco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAgressor")
    private List<Ocorrencia> ocorrenciaList;

    public Agressor() {
    }

    public Agressor(Integer idAgressor) {
        this.idAgressor = idAgressor;
    }

    public Integer getIdAgressor() {
        return idAgressor;
    }

    public void setIdAgressor(Integer idAgressor) {
        this.idAgressor = idAgressor;
    }

    public String getDescParentesco() {
        return descParentesco;
    }

    public void setDescParentesco(String descParentesco) {
        this.descParentesco = descParentesco;
    }

    public List<Ocorrencia> getOcorrenciaList() {
        return ocorrenciaList;
    }

    public void setOcorrenciaList(List<Ocorrencia> ocorrenciaList) {
        this.ocorrenciaList = ocorrenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAgressor != null ? idAgressor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agressor)) {
            return false;
        }
        Agressor other = (Agressor) object;
        if ((this.idAgressor == null && other.idAgressor != null) || (this.idAgressor != null && !this.idAgressor.equals(other.idAgressor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Agressor[ idAgressor=" + idAgressor + " ]";
    }
    
}
