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
public class NivelTipoAccesoBean implements Serializable {

    private String uniNeg;
    private Integer nivel;//cr o cuenta
    private Integer anio;// 
    private String tipoNivel;
    private CodigoBean tipoAccesoBean;
    private Boolean status;
    private String statusVista;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Integer cantCr;
    private Integer cantCc;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(String tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    public CodigoBean getTipoAccesoBean() {
        if (tipoAccesoBean == null) {
            tipoAccesoBean = new CodigoBean();
        }
        return tipoAccesoBean;
    }

    public void setTipoAccesoBean(CodigoBean tipoAccesoBean) {
        this.tipoAccesoBean = tipoAccesoBean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCantCr() {
        return cantCr;
    }

    public void setCantCr(Integer cantCr) {
        this.cantCr = cantCr;
    }

    public Integer getCantCc() {
        return cantCc;
    }

    public void setCantCc(Integer cantCc) {
        this.cantCc = cantCc;
    }

    public String getStatusVista() {
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

}