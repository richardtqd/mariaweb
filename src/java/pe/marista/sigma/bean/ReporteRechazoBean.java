/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.util.Date;

/**
 *
 * @author MS002
 */
public class ReporteRechazoBean {
    
    private Integer idReporteRechazo;
    private UnidadNegocioBean unidadNegocioBean;
    private EstudianteBean estudianteBean;
    private ProcesoBancoBean procesoBancoBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private Integer flgError;
    private String descripcion;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    
    //Ayuda
    private String dia;
    private String mes;
    private String anio;

    public Integer getIdReporteRechazo() {
        return idReporteRechazo;
    }

    public void setIdReporteRechazo(Integer idReporteRechazo) {
        this.idReporteRechazo = idReporteRechazo;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean == null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public ProcesoBancoBean getProcesoBancoBean() {
        if(procesoBancoBean == null){
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if(cuentasPorCobrarBean == null){
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if(procesoRecuperacionBean == null){
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public Integer getFlgError() {
        return flgError;
    }

    public void setFlgError(Integer flgError) {
        this.flgError = flgError;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
    
}
