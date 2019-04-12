/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS-001
 */
public class TipoEnvioUniNegBean implements Serializable {

    private Integer idTipoEnvioUniNeg;
    private String uniNeg;
    private String nombreTipoEnvio;
    private String valorCab;
    private String valor;
    private Boolean status;
    private Boolean flgDefault;
    private Boolean flgFechaReq;
    private String creaPor;
    private String creaFecha;
    private String modiPor;
    private String flgFechaReqStr;
    
    private Integer idTipoStatusCtaCte;
    private String codCtaCte;

     

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getNombreTipoEnvio() {
        return nombreTipoEnvio;
    }

    public void setNombreTipoEnvio(String nombreTipoEnvio) {
        this.nombreTipoEnvio = nombreTipoEnvio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    public Boolean getFlgDefault() {
        return flgDefault;
    }

    public void setFlgDefault(Boolean flgDefault) {
        this.flgDefault = flgDefault;
    }

    public Boolean getFlgFechaReq() {
        return flgFechaReq;
    }

    public void setFlgFechaReq(Boolean flgFechaReq) {
        this.flgFechaReq = flgFechaReq;
    }

    public Integer getIdTipoStatusCtaCte() {
        return idTipoStatusCtaCte;
    }

    public void setIdTipoStatusCtaCte(Integer idTipoStatusCtaCte) {
        this.idTipoStatusCtaCte = idTipoStatusCtaCte;
    }

    public String getCodCtaCte() {
        return codCtaCte;
    }

    public void setCodCtaCte(String codCtaCte) {
        this.codCtaCte = codCtaCte;
    }

    public Integer getIdTipoEnvioUniNeg() {
        return idTipoEnvioUniNeg;
    }

    public void setIdTipoEnvioUniNeg(Integer idTipoEnvioUniNeg) {
        this.idTipoEnvioUniNeg = idTipoEnvioUniNeg;
    }

    public String getFlgFechaReqStr() {
        return flgFechaReqStr;
    }

    public void setFlgFechaReqStr(String flgFechaReqStr) {
        this.flgFechaReqStr = flgFechaReqStr;
    } 

    public String getValorCab() {
        return valorCab;
    }

    public void setValorCab(String valorCab) {
        this.valorCab = valorCab;
    }
    
    
     
}
