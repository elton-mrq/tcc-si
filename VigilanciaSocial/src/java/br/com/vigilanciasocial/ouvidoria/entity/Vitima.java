/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "vitima")
@NamedQueries({
    @NamedQuery(name = "Vitima.findAll", query = "SELECT v FROM Vitima v")})
public class Vitima implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idVitima")
    private Integer idVitima;
    @Column(name = "nomeVitima")
    private String nomeVitima;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "dtNasc")
    @Temporal(TemporalType.DATE)
    private Date dtNasc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVitma")
    private List<Ocorrencia> ocorrenciaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idVitima")
    private Endvitima endvitima;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idVitima")
    private List<Telvitma> telvitmaList;

    public Vitima() {
    }

    public Vitima(Integer idVitima) {
        this.idVitima = idVitima;
    }

    public Integer getIdVitima() {
        return idVitima;
    }

    public void setIdVitima(Integer idVitima) {
        this.idVitima = idVitima;
    }

    public String getNomeVitima() {
        return nomeVitima;
    }

    public void setNomeVitima(String nomeVitima) {
        this.nomeVitima = nomeVitima;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public List<Ocorrencia> getOcorrenciaList() {
        return ocorrenciaList;
    }

    public void setOcorrenciaList(List<Ocorrencia> ocorrenciaList) {
        this.ocorrenciaList = ocorrenciaList;
    }

    public Endvitima getEndvitima() {
        return endvitima;
    }

    public void setEndvitima(Endvitima endvitima) {
        this.endvitima = endvitima;
    }

    public List<Telvitma> getTelvitmaList() {
        return telvitmaList;
    }

    public void setTelvitmaList(List<Telvitma> telvitmaList) {
        this.telvitmaList = telvitmaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVitima != null ? idVitima.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vitima)) {
            return false;
        }
        Vitima other = (Vitima) object;
        if ((this.idVitima == null && other.idVitima != null) || (this.idVitima != null && !this.idVitima.equals(other.idVitima))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Vitima[ idVitima=" + idVitima + " ]";
    }
    
}
