/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "segmento")
@NamedQueries({
    @NamedQuery(name = "Segmento.findAll", query = "SELECT s FROM Segmento s")})
public class Segmento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSegmento")
    private Integer idSegmento;
    @Column(name = "descSegmento")
    private String descSegmento;
    @OneToMany(mappedBy = "segmentoidSegmento")
    private List<Ocorrencia> ocorrenciaList;

    public Segmento() {
    }

    public Segmento(Integer idSegmento) {
        this.idSegmento = idSegmento;
    }

    public Integer getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(Integer idSegmento) {
        this.idSegmento = idSegmento;
    }

    public String getDescSegmento() {
        return descSegmento;
    }

    public void setDescSegmento(String descSegmento) {
        this.descSegmento = descSegmento;
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
        hash += (idSegmento != null ? idSegmento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Segmento)) {
            return false;
        }
        Segmento other = (Segmento) object;
        if ((this.idSegmento == null && other.idSegmento != null) || (this.idSegmento != null && !this.idSegmento.equals(other.idSegmento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Segmento[ idSegmento=" + idSegmento + " ]";
    }
    
}
