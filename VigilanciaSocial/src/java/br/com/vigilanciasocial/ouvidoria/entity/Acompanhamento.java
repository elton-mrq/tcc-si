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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "acompanhamento")
@NamedQueries({
    @NamedQuery(name = "Acompanhamento.findAll", query = "SELECT a FROM Acompanhamento a")})
public class Acompanhamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAcomp")
    private Integer idAcomp;
    @Basic(optional = false)
    @Column(name = "dtAcomp")
    @Temporal(TemporalType.DATE)
    private Date dtAcomp;
    @Lob
    @Column(name = "evolucao")
    private String evolucao;
    @JoinColumn(name = "idOcorrencia", referencedColumnName = "idOcorrencia")
    @ManyToOne(optional = false)
    private Ocorrencia idOcorrencia;

    public Acompanhamento() {
    }

    public Acompanhamento(Integer idAcomp) {
        this.idAcomp = idAcomp;
    }

    public Acompanhamento(Integer idAcomp, Date dtAcomp) {
        this.idAcomp = idAcomp;
        this.dtAcomp = dtAcomp;
    }

    public Integer getIdAcomp() {
        return idAcomp;
    }

    public void setIdAcomp(Integer idAcomp) {
        this.idAcomp = idAcomp;
    }

    public Date getDtAcomp() {
        return dtAcomp;
    }

    public void setDtAcomp(Date dtAcomp) {
        this.dtAcomp = dtAcomp;
    }

    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }

    public Ocorrencia getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Ocorrencia idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAcomp != null ? idAcomp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acompanhamento)) {
            return false;
        }
        Acompanhamento other = (Acompanhamento) object;
        if ((this.idAcomp == null && other.idAcomp != null) || (this.idAcomp != null && !this.idAcomp.equals(other.idAcomp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Acompanhamento[ idAcomp=" + idAcomp + " ]";
    }
    
}
