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
public class ImpresoraCajaBean implements Serializable {

    private ImpresoraBean uniNeg;//unidadNegocioBean.uniNeg
    private CajaBean cajaBean;//idCaja
    private ImpresoraBean impresora;//impresora
    private ImpresoraBean idTipoDoc;//idTipoDoc.idCodigo
    private boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    //validar 

    public ImpresoraBean getUniNeg() {
        if (uniNeg == null) {
            uniNeg = new ImpresoraBean();
        }
        return uniNeg;
    }

    public void setUniNeg(ImpresoraBean uniNeg) {
        this.uniNeg = uniNeg;
    }

    public CajaBean getCajaBean() {
        if (cajaBean == null) {
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }

    public ImpresoraBean getImpresora() {
        if (impresora == null) {
            impresora = new ImpresoraBean();
        }
        return impresora;
    }

    public void setImpresora(ImpresoraBean impresora) {
        this.impresora = impresora;
    }

    public ImpresoraBean getIdTipoDoc() {
        if (idTipoDoc == null) {
            idTipoDoc = new ImpresoraBean();
        }
        return idTipoDoc;
    }

    public void setIdTipoDoc(ImpresoraBean idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

   
}
