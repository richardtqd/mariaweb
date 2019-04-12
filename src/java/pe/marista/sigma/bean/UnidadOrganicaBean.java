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
public class UnidadOrganicaBean implements Serializable{
    
    private Integer idUniOrg;   
    private String nombreUniOrg;
    private String codigoUniOrg;
    private UnidadOrganicaBean unidadOrganicaPadreBean;//idUniOrgPadre
    private CodigoBean tipoUniOrgBean; //idTipoUniOrg
//    private UnidadNegocioBean unidadNegocioBean;//idUniNeg
    private String creaPor;
    private Date creaFecha;
    
    
     public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

//    public UnidadNegocioBean getUnidadNegocioBean() {
//        if(unidadNegocioBean == null)
//        {
//            unidadNegocioBean = new UnidadNegocioBean();
//        }
//        return unidadNegocioBean;
//    }
//
//    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
//        this.unidadNegocioBean = unidadNegocioBean;
//    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public String getCodigoUniOrg() {
        return codigoUniOrg;
    }

    public void setCodigoUniOrg(String codigoUniOrg) {
        this.codigoUniOrg = codigoUniOrg;
    }

    public UnidadOrganicaBean getUnidadOrganicaPadreBean() {
        if (unidadOrganicaPadreBean == null) {
            unidadOrganicaPadreBean = new UnidadOrganicaBean();
        }return unidadOrganicaPadreBean;
    }

    public void setUnidadOrganicaPadreBean(UnidadOrganicaBean unidadOrganicaPadreBean) {
        this.unidadOrganicaPadreBean = unidadOrganicaPadreBean;
    }

    public CodigoBean getTipoUniOrgBean() {
        if (tipoUniOrgBean == null) {
            tipoUniOrgBean = new CodigoBean();
        }
        return tipoUniOrgBean;
    }

    public void setTipoUniOrgBean(CodigoBean tipoUniOrgBean) {
        this.tipoUniOrgBean = tipoUniOrgBean;
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
