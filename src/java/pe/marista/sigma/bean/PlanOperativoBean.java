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
public class PlanOperativoBean implements Serializable {

    private String idPlanOperativo;
    private UnidadNegocioBean unidadNegocioBean; // uniNeg
    private UnidadOrganicaBean unidadOrganicaBean; //idUniOrg   
    private Integer uniNeg;
    private Integer idUniOrg;
    private AnioBean anioBean;
    private Integer anio;
    private String nombre;//nombrePlanOperativo
    private String aprobadoPor;
    private String creaPor; // antes creaPor
    private Date creaFecha;//antes creaFecha
    private String modiPor;
    private String modiVer;

//    public PlanOperativoBean(Integer idPlanOperativo, AnioBean anioBean, UnidadNegocioBean unidadNegocioBean, String nombre) {
////        this.idPlanOperativo = idPlanOperativo;
//        this.anioBean = anioBean;
//        this.unidadNegocioBean = unidadNegocioBean;
////        this.codigoPlanOperativo = codigoPlanOperativo;
//        this.nombre = nombre;
//    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if(unidadOrganicaBean == null){
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public AnioBean getAnioBean() {
        if(anioBean == null){
            anioBean = new AnioBean();
        }
        return anioBean;
    }

    public void setAnioBean(AnioBean anioBean) {
        this.anioBean = anioBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
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

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(Integer uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getIdPlanOperativo() {
        return idPlanOperativo;
    }

    public void setIdPlanOperativo(String idPlanOperativo) {
        this.idPlanOperativo = idPlanOperativo;
    }

      
    
}
