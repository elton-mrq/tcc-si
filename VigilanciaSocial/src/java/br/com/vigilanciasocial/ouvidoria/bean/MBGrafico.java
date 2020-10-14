/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.vigilanciasocial.ouvidoria.bean;

import br.com.vigilanciasocial.ouvidoria.business.OcorrenciaDAO;
import br.com.vigilanciasocial.ouvidoria.entity.Ocorrencia;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Elton
 */
@ManagedBean
@RequestScoped
public class MBGrafico {
    
    Ocorrencia ocorrencia = new Ocorrencia();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("VigilanciaSocialPU");
    OcorrenciaDAO ocorrenciaAdo = new OcorrenciaDAO(emf);
   
    PieChartModel pieModel1, pieModel2;
    
  

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public void setPieModel1(PieChartModel pieModel1) {
        this.pieModel1 = pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    public void setPieModel2(PieChartModel pieModel2) {
        this.pieModel2 = pieModel2;
    }
    
    
    
    public Ocorrencia getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        this.ocorrencia = ocorrencia;
    }
    
    public void listarSegmento(){
        List<Ocorrencia> lista;
        
        try {
            lista = ocorrenciaAdo.findOcorrenciaEntities();
            gerarGraficoSegmento(lista);
        } catch (Exception e) {
        }
    }
    
    public void gerarGraficoSegmento(List<Ocorrencia> lista){
        pieModel1 = new PieChartModel();
        
        for(Ocorrencia o: lista){
            pieModel1.set(o.getSegmentoidSegmento().getDescSegmento(), o.getSegmentoidSegmento().getIdSegmento());
            
        }
        
        pieModel1.setTitle("Denúncia por Segmento");
        pieModel1.setLegendPosition("e");
        pieModel1.setFill(false);
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(250);        
       
    }
    
   
  
    
  
    public MBGrafico() {
                
    }
    
}
