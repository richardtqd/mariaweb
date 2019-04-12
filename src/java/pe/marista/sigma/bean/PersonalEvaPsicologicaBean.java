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
 * @author MS-001
 */
public class PersonalEvaPsicologicaBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalEvaPsicologica;
    private Date fechaIni;
    private Date fechaFin;
    private String descripcion;
    private String nombreTerapeuta;
    private String numColegiaturaTerapeuta;
    private String nombreCentro;
    private String rucCentro;
    private String telefonoCentro;
    private String creaPor;
    private String creaFecha;
    private String modiPor;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean==null) {
            personalBean= new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Integer getIdPersonalEvaPsicologica() {
        return idPersonalEvaPsicologica;
    }

    public void setIdPersonalEvaPsicologica(Integer idPersonalEvaPsicologica) {
        this.idPersonalEvaPsicologica = idPersonalEvaPsicologica;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreTerapeuta() {
        return nombreTerapeuta;
    }

    public void setNombreTerapeuta(String nombreTerapeuta) {
        this.nombreTerapeuta = nombreTerapeuta;
    }

    public String getNumColegiaturaTerapeuta() {
        return numColegiaturaTerapeuta;
    }

    public void setNumColegiaturaTerapeuta(String numColegiaturaTerapeuta) {
        this.numColegiaturaTerapeuta = numColegiaturaTerapeuta;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getRucCentro() {
        return rucCentro;
    }

    public void setRucCentro(String rucCentro) {
        this.rucCentro = rucCentro;
    }

    public String getTelefonoCentro() {
        return telefonoCentro;
    }

    public void setTelefonoCentro(String telefonoCentro) {
        this.telefonoCentro = telefonoCentro;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
    
}
