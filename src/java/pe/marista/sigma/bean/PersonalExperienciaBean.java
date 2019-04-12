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
public class PersonalExperienciaBean implements Serializable {

    private Integer idPersonalExperiencia;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;//id
    private String empresa;
    private String industria;
    private String area;
    private String cargo;

    private Integer mesIni;
    private Integer mesFin;
    private Integer anioIni;
    private Integer anioFin;
    private String funcion;
    private String obs;

    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    //MES
    private boolean collapsed = true;

    public Integer getIdPersonalExperiencia() {
        return idPersonalExperiencia;
    }

    public void setIdPersonalExperiencia(Integer idPersonalExperiencia) {
        this.idPersonalExperiencia = idPersonalExperiencia;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getMesIni() {
        return mesIni;
    }

    public void setMesIni(Integer mesIni) {
        this.mesIni = mesIni;
    }

    public Integer getMesFin() {
        return mesFin;
    }

    public void setMesFin(Integer mesFin) {
        this.mesFin = mesFin;
    }

    public Integer getAnioIni() {
        return anioIni;
    }

    public void setAnioIni(Integer anioIni) {
        this.anioIni = anioIni;
    }

    public Integer getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(Integer anioFin) {
        this.anioFin = anioFin;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

}
