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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Elton
 */
@Entity
@Table(name = "ocorrencia")
@NamedQueries({
    @NamedQuery(name = "Ocorrencia.findAll", query = "SELECT o FROM Ocorrencia o")})
public class Ocorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOcorrencia")
    private Integer idOcorrencia;
    @Basic(optional = false)
    @Lob
    @Column(name = "descOcorrencia")
    private String descOcorrencia;
    @Basic(optional = false)
    @Column(name = "dtDenuncia")
    @Temporal(TemporalType.DATE)
    private Date dtDenuncia;
    @Column(name = "horaDenuncia")
    @Temporal(TemporalType.TIME)
    private Date horaDenuncia;
    @Column(name = "dtOcorrencia")
    @Temporal(TemporalType.DATE)
    private Date dtOcorrencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOcorrencia")
    private List<Acompanhamento> acompanhamentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOcorrencia")
    private List<Avaliacao> avaliacaoList;
    @JoinColumn(name = "idAgressor", referencedColumnName = "idAgressor")
    @ManyToOne(optional = false)
    private Agressor idAgressor;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @JoinColumn(name = "idVitma", referencedColumnName = "idVitima")
    @ManyToOne(optional = false)
    private Vitima idVitma;
    @JoinColumn(name = "Segmento_idSegmento", referencedColumnName = "idSegmento")
    @ManyToOne
    private Segmento segmentoidSegmento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ocorrenciaidOcorrencia")
    private List<Encaminhar> encaminharList;

    public Ocorrencia() {
    }

    public Ocorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public Ocorrencia(Integer idOcorrencia, String descOcorrencia, Date dtDenuncia) {
        this.idOcorrencia = idOcorrencia;
        this.descOcorrencia = descOcorrencia;
        this.dtDenuncia = dtDenuncia;
    }

    public Integer getIdOcorrencia() {
        return idOcorrencia;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
        this.idOcorrencia = idOcorrencia;
    }

    public String getDescOcorrencia() {
        return descOcorrencia;
    }

    public void setDescOcorrencia(String descOcorrencia) {
        this.descOcorrencia = descOcorrencia;
    }

    public Date getDtDenuncia() {
        return dtDenuncia;
    }

    public void setDtDenuncia(Date dtDenuncia) {
        this.dtDenuncia = dtDenuncia;
    }

    public Date getHoraDenuncia() {
        return horaDenuncia;
    }

    public void setHoraDenuncia(Date horaDenuncia) {
        this.horaDenuncia = horaDenuncia;
    }

    public Date getDtOcorrencia() {
        return dtOcorrencia;
    }

    public void setDtOcorrencia(Date dtOcorrencia) {
        this.dtOcorrencia = dtOcorrencia;
    }

    public List<Acompanhamento> getAcompanhamentoList() {
        return acompanhamentoList;
    }

    public void setAcompanhamentoList(List<Acompanhamento> acompanhamentoList) {
        this.acompanhamentoList = acompanhamentoList;
    }

    public List<Avaliacao> getAvaliacaoList() {
        return avaliacaoList;
    }

    public void setAvaliacaoList(List<Avaliacao> avaliacaoList) {
        this.avaliacaoList = avaliacaoList;
    }

    public Agressor getIdAgressor() {
        return idAgressor;
    }

    public void setIdAgressor(Agressor idAgressor) {
        this.idAgressor = idAgressor;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Vitima getIdVitma() {
        return idVitma;
    }

    public void setIdVitma(Vitima idVitma) {
        this.idVitma = idVitma;
    }

    public Segmento getSegmentoidSegmento() {
        return segmentoidSegmento;
    }

    public void setSegmentoidSegmento(Segmento segmentoidSegmento) {
        this.segmentoidSegmento = segmentoidSegmento;
    }

    public List<Encaminhar> getEncaminharList() {
        return encaminharList;
    }

    public void setEncaminharList(List<Encaminhar> encaminharList) {
        this.encaminharList = encaminharList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOcorrencia != null ? idOcorrencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocorrencia)) {
            return false;
        }
        Ocorrencia other = (Ocorrencia) object;
        if ((this.idOcorrencia == null && other.idOcorrencia != null) || (this.idOcorrencia != null && !this.idOcorrencia.equals(other.idOcorrencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia[ idOcorrencia=" + idOcorrencia + " ]";
    }
    
}
