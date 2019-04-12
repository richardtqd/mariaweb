/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class CentroResponsabilidadBean implements Serializable {

    private Integer cr;
    private String nombre;
    private String idTipoGrupoCR;
    private CodigoBean tipoGrupoCRBean; //idTipoGrupoCRBean
    private Integer nivel;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    //ayuda
    private Double montoDistribucion;
    private Integer idtipoCR;

    //Cambio
    private List<PlanContableBean> listaPlanContableBean;
    private String creaFechaAc;
    private String creaHoraAc;

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    //ayuda      
    private String tipoNivelCR;
    private Integer idCentroResPadre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
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

    public CodigoBean getTipoGrupoCRBean() {
        if (tipoGrupoCRBean == null) {
            tipoGrupoCRBean = new CodigoBean();
        }
        return tipoGrupoCRBean;
    }

    public void setTipoGrupoCRBean(CodigoBean tipoGrupoCRBean) {
        this.tipoGrupoCRBean = tipoGrupoCRBean;
    }

    public String getTipoNivelCR() {
        return tipoNivelCR;
    }

    public void setTipoNivelCR(String tipoNivelCR) {
        this.tipoNivelCR = tipoNivelCR;
    }

    public Integer getIdCentroResPadre() {
        return idCentroResPadre;
    }

    public void setIdCentroResPadre(Integer idCentroResPadre) {
        this.idCentroResPadre = idCentroResPadre;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getIdTipoGrupoCR() {
        return idTipoGrupoCR;
    }

    public void setIdTipoGrupoCR(String idTipoGrupoCR) {
        this.idTipoGrupoCR = idTipoGrupoCR;
    }

    public Double getMontoDistribucion() {
        return montoDistribucion;
    }

    public void setMontoDistribucion(Double montoDistribucion) {
        this.montoDistribucion = montoDistribucion;
    }

//    Cambio
    public CentroResponsabilidadBean() {
        listaPlanContableBean = new ArrayList<>();
    }

//    public PlanContableBean getCr(int plan) {
//        return listaPlanContableBean.get(plan);
//    }

    public String getCreaFechaAc() {
        return creaFechaAc;
    }

    public void setCreaFechaAc(String creaFechaAc) {
        this.creaFechaAc = creaFechaAc;
    }

    public String getCreaHoraAc() {
        return creaHoraAc;
    }

    public void setCreaHoraAc(String creaHoraAc) {
        this.creaHoraAc = creaHoraAc;
    }

    public CentroResponsabilidadBean(Integer cr) {
        this.cr = cr;
    }

    public Integer getIdtipoCR() {
        return idtipoCR;
    }

    public void setIdtipoCR(Integer idtipoCR) {
        this.idtipoCR = idtipoCR;
    }

}
