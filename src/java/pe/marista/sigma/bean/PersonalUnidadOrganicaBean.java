/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class PersonalUnidadOrganicaBean implements Serializable{
    
    private Integer idPersonaUniOrg;
    private PersonalBean personalBean;//id
    private UnidadOrganicaBean unidadOrganicaBean;//id
    private FuncionBean funcionBean; //id
     
    private Date fecIni;
    private Date fecTer;
    private String obs;
    private String creaPor;
    private Date creaFecha;

    public Integer getIdPersonaUniOrg() {
        return idPersonaUniOrg;
    }

    public void setIdPersonaUniOrg(Integer idPersonaUniOrg) {
        this.idPersonaUniOrg = idPersonaUniOrg;
    }

    public PersonalBean getPersonalBean() {
       if (personalBean == null) {
            personalBean = new PersonalBean();
    }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
    }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public FuncionBean getFuncionBean() {
        if (funcionBean == null) {
            funcionBean = new FuncionBean();
    }   return funcionBean;
    }

    public void setFuncionBean(FuncionBean funcionBean) {
        this.funcionBean = funcionBean;
    }
 
    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecTer() {
        return fecTer;
    }

    public void setFecTer(Date fecTer) {
        this.fecTer = fecTer;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
    
    
    
    
}
