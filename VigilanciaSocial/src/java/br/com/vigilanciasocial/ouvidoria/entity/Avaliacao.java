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
@Table(name = "avaliacao")
@NamedQueries({
    @NamedQuery(name = "Avaliacao.findAll", query = "SELECT a FROM Avaliacao a")})
public class Avaliacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idAvaliacao")
    private Integer idAvaliacao;
    @Basic(optional = false)
    @Column(name = "avaliacao")
    private String avaliacao;
    @Basic(optional = false)
    @Lob
    @Column(name = "descAvaliacao")
    private String descAvaliacao;
    @Basic(optional = false)
    @Column(name = "dtAvaliacao")
    @Temporal(TemporalType.DATE)
    private Date dtAvaliacao;
    @JoinColumn(name = "idOcorrencia", referencedColumnName = "idOcorrencia")
    @ManyToOne(optional = false)
    private Ocorrencia idOcorrencia;

    public Avaliacao() {
    }

    public Avaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Avaliacao(Integer idAvaliacao, String avaliacao, String descAvaliacao, Date dtAvaliacao) {
        this.idAvaliacao = idAvaliacao;
        this.avaliacao = avaliacao;
        this.descAvaliacao = descAvaliacao;
        this.dtAvaliacao = dtAvaliacao;
    }

    public Integer getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Integer idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescAvaliacao() {
        return descAvaliacao;
    }

    public void setDescAvaliacao(String descAvaliacao) {
        this.descAvaliacao = descAvaliacao;
    }

    public Date getDtAvaliacao() {
        return dtAvaliacao;
    }

    public void setDtAvaliacao(Date dtAvaliacao) {
        this.dtAvaliacao = dtAvaliacao;
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
        hash += (idAvaliacao != null ? idAvaliacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Avaliacao)) {
            return false;
        }
        Avaliacao other = (Avaliacao) object;
        if ((this.idAvaliacao == null && other.idAvaliacao != null) || (this.idAvaliacao != null && !this.idAvaliacao.equals(other.idAvaliacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Avaliacao[ idAvaliacao=" + idAvaliacao + " ]";
    }
    
}
