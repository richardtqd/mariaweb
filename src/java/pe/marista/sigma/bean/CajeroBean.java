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
 * @author MS002
 */
public class CajeroBean implements Serializable {  

    private Integer idCajero;
    private PersonalBean personalBean;//idPersona
    private String token;
    private boolean status;
    private Date fecIni;
    private Date fecFin;
    private String nombrePersonal;

    public CajeroBean(boolean status, Date fecIni, Date fecFin, String nombrePersonal, String nombre) {
        this.status = status;
        this.fecIni = fecIni;
        this.fecFin = fecFin;
        this.nombrePersonal = nombrePersonal;
        this.nombre = nombre;
    }

    //
    private String nombre;

    public Integer getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(Integer idCajero) {
        this.idCajero = idCajero;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public CajeroBean(Integer idCajero, String nombre) {
        this.idCajero = idCajero;
        this.nombre = nombre;
    }

    public CajeroBean() {
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
