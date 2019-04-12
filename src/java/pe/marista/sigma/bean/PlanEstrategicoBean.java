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
public class PlanEstrategicoBean implements Serializable {

    private Integer idPlanEstrategico;
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
//    private String codigoPlanEstrategico;
    private String nombre;
    private Date fecInicio;
    private Date fecTermino;
    private Integer anioIniciocom;//comodin
    private Integer anioTerminocom;//comodin
    private Date anioInicio;
    private Date anioTermino;
    private String descripcion;
    private String status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private boolean collapsed;

    //Ayuda
    private Integer anioIni;
    private Integer anioFin;
    private Integer mesIni;
    private Integer mesFin;
    private String nombrePlanEstrategicoSub;
    private String descripcionSub;

    public Integer getIdPlanEstrategico() {
        return idPlanEstrategico;
    }

    public void setIdPlanEstrategico(Integer idPlanEstrategico) {
        this.idPlanEstrategico = idPlanEstrategico;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(Date fecInicio) {
        this.fecInicio = fecInicio;
    }

    public Date getFecTermino() {
        return fecTermino;
    }

    public void setFecTermino(Date fecTermino) {
        this.fecTermino = fecTermino;
    }

    public Integer getAnioIniciocom() {
        return anioIniciocom;
    }

    public void setAnioIniciocom(Integer anioIniciocom) {
        this.anioIniciocom = anioIniciocom;
    }

    public Integer getAnioTerminocom() {
        return anioTerminocom;
    }

    public void setAnioTerminocom(Integer anioTerminocom) {
        this.anioTerminocom = anioTerminocom;
    }

    public Date getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(Date anioInicio) {
        this.anioInicio = anioInicio;
    }

    public Date getAnioTermino() {
        return anioTermino;
    }

    public void setAnioTermino(Date anioTermino) {
        this.anioTermino = anioTermino;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public Integer getAnioIni() {
        return anioIni;
    }

    public void setAnioIni(Integer anioIni) {
        this.anioIni = anioIni;
    }

    public Integer getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(Integer anioFin) {
        this.anioFin = anioFin;
    }

    public Integer getMesIni() {
        return mesIni;
    }

    public void setMesIni(Integer mesIni) {
        this.mesIni = mesIni;
    }

    public Integer getMesFin() {
        return mesFin;
    }

    public void setMesFin(Integer mesFin) {
        this.mesFin = mesFin;
    }

    public String getNombrePlanEstrategicoSub() {
        return nombrePlanEstrategicoSub;
    }

    public void setNombrePlanEstrategicoSub(String nombrePlanEstrategicoSub) {
        this.nombrePlanEstrategicoSub = nombrePlanEstrategicoSub;
    }

    public String getDescripcionSub() {
        return descripcionSub;
    }

    public void setDescripcionSub(String descripcionSub) {
        this.descripcionSub = descripcionSub;
    }

}
