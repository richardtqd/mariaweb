/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class ConceptoCRBean implements Serializable{
    
    private Integer idConceptoCR;
    private ConceptoBean conceptoBean;//idConceptoCR
    private CentroResponsabilidadBean centroResponsabilidadBean;//idCR
    private Integer ccnivel1;
    private Integer ccnivel2;
    private Double cobertura;

    public Integer getIdConceptoCR() {
        return idConceptoCR;
    }

    public void setIdConceptoCR(Integer idConceptoCR) {
        this.idConceptoCR = idConceptoCR;
    }

    public ConceptoBean getConceptoBean() {
        if(conceptoBean == null)
        {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if(centroResponsabilidadBean == null)
        {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public Integer getCcnivel1() {
        return ccnivel1;
    }

    public void setCcnivel1(Integer ccnivel1) {
        this.ccnivel1 = ccnivel1;
    }

    public Integer getCcnivel2() {
        return ccnivel2;
    }

    public void setCcnivel2(Integer ccnivel2) {
        this.ccnivel2 = ccnivel2;
    }

    public Double getCobertura() {
        return cobertura;
    }

    public void setCobertura(Double cobertura) {
        this.cobertura = cobertura;
    }
    
    
    
}
