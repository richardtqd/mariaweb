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
public class DocIngresoSerieBean implements Serializable{
    
    private UnidadNegocioBean unidadNegocioBean;
    private CodigoBean idTipoDoc;
    private String serie;
    private Integer inicio;
    private Integer fin;
    private Integer actual;
    private boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String edit="false";
    private String idCompleto;//comodin    
    
    public CodigoBean getIdTipoDoc() {
        if(idTipoDoc == null)
        {
            idTipoDoc = new CodigoBean();
        }
        return idTipoDoc;
    }

    public void setIdTipoDoc(CodigoBean idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public Integer getFin() {
        return fin;
    }

    public void setFin(Integer fin) {
        this.fin = fin;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        this.actual = actual;
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

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null)
        {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }
    
//    
//    public String getIdCompleto(){
//        StringBuilder sb = new StringBuilder();
//        sb.append(serie).append(".").append(unidadNegocioBean).append(".").append(idTipoDoc);
//        return sb.toString();
//    }

    public void setIdCompleto(String idCompleto) {
        this.idCompleto = idCompleto;
    }

    public String getIdCompleto() {
        return idCompleto;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
    
    
}
