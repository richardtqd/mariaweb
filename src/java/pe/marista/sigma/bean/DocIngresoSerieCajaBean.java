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
public class DocIngresoSerieCajaBean implements Serializable{
    private DocIngresoSerieBean uniNeg;//unidadNegocioBean.uniNeg
    private DocIngresoSerieBean idTipoDoc;//idTipoDoc.idCodigo
    private DocIngresoSerieBean serie;//serie
    private CajaBean cajaBean;//idCaja
    private boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    public CajaBean getCajaBean() {
        if(cajaBean == null)
        {
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }


    public DocIngresoSerieBean getIdTipoDoc() {
        if(idTipoDoc == null)
        {
            idTipoDoc = new DocIngresoSerieBean();
        }
        return idTipoDoc;
    }

    public void setIdTipoDoc(DocIngresoSerieBean idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public DocIngresoSerieBean getSerie() {
        if(serie == null)
        {
            serie = new DocIngresoSerieBean();
        }
        return serie;
    }

    public void setSerie(DocIngresoSerieBean serie) {
        this.serie = serie;
    }

    public DocIngresoSerieBean getUniNeg() {
        if(uniNeg == null)
        {
            uniNeg = new DocIngresoSerieBean();
        }
        return uniNeg;
    }

    public void setUniNeg(DocIngresoSerieBean uniNeg) {
        this.uniNeg = uniNeg;
    }

    public boolean getStatus() {
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
