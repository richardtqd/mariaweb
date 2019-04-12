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
 * @author Administrador
 */
public class PersonalContratoBean implements Serializable {

    private Integer idPersonalContrato;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean; //idPersonal
    private Date fecIni;
    private Date fecFin;
    private CodigoBean tipoContratoBean; //idTipoContratoBean
    private String documento;
    private String creaPor;
    private String obs;
    private Date creaFecha;
    private String modiPor;
    private boolean collapsed = true;
    
    private Boolean diaLunes;
    private Boolean diaMartes;
    private Boolean diaMiercoles;
    private Boolean diaJueves;
    private Boolean diaViernes;
    private Boolean diaSabado;
    
    private String horaIniLunes;
    private String horaIniMartes;
    private String horaIniMiercoles;
    private String horaIniJueves;
    private String horaIniViernes;
    private String horaIniSabado;
    
    private String horaFinLunes;
    private String horaFinMartes;
    private String horaFinMiercoles;
    private String horaFinJueves;
    private String horaFinViernes;
    private String horaFinSabado;
    
    private String horaRefrigerioLunes;
    private String horaRefrigerioMartes;
    private String horaRefrigerioMiercoles;
    private String horaRefrigerioJueves;
    private String horaRefrigerioViernes;
    private String horaRefrigerioSabado;
    private Integer periodo;
    private String tiempoContrato; 
    
    public Integer getIdPersonalContrato() {
        return idPersonalContrato;
    }

    public void setIdPersonalContrato(Integer idPersonalContrato) {
        this.idPersonalContrato = idPersonalContrato;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public CodigoBean getTipoContratoBean() {
        if (tipoContratoBean == null) {
            tipoContratoBean = new CodigoBean();
        }
        return tipoContratoBean;
    }

    public void setTipoContratoBean(CodigoBean tipoContratoBean) {
        this.tipoContratoBean = tipoContratoBean;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getDiaLunes() {
        return diaLunes;
    }

    public void setDiaLunes(Boolean diaLunes) {
        this.diaLunes = diaLunes;
    }

    public Boolean getDiaMartes() {
        return diaMartes;
    }

    public void setDiaMartes(Boolean diaMartes) {
        this.diaMartes = diaMartes;
    }

    public Boolean getDiaMiercoles() {
        return diaMiercoles;
    }

    public void setDiaMiercoles(Boolean diaMiercoles) {
        this.diaMiercoles = diaMiercoles;
    }

    public Boolean getDiaJueves() {
        return diaJueves;
    }

    public void setDiaJueves(Boolean diaJueves) {
        this.diaJueves = diaJueves;
    }

    public Boolean getDiaViernes() {
        return diaViernes;
    }

    public void setDiaViernes(Boolean diaViernes) {
        this.diaViernes = diaViernes;
    }

    public Boolean getDiaSabado() {
        return diaSabado;
    }

    public void setDiaSabado(Boolean diaSabado) {
        this.diaSabado = diaSabado;
    }

    public String getHoraIniLunes() {
        return horaIniLunes;
    }

    public void setHoraIniLunes(String horaIniLunes) {
        this.horaIniLunes = horaIniLunes;
    }

    public String getHoraIniMartes() {
        return horaIniMartes;
    }

    public void setHoraIniMartes(String horaIniMartes) {
        this.horaIniMartes = horaIniMartes;
    }

    public String getHoraIniMiercoles() {
        return horaIniMiercoles;
    }

    public void setHoraIniMiercoles(String horaIniMiercoles) {
        this.horaIniMiercoles = horaIniMiercoles;
    }

    public String getHoraIniJueves() {
        return horaIniJueves;
    }

    public void setHoraIniJueves(String horaIniJueves) {
        this.horaIniJueves = horaIniJueves;
    }

    public String getHoraIniViernes() {
        return horaIniViernes;
    }

    public void setHoraIniViernes(String horaIniViernes) {
        this.horaIniViernes = horaIniViernes;
    }

    public String getHoraIniSabado() {
        return horaIniSabado;
    }

    public void setHoraIniSabado(String horaIniSabado) {
        this.horaIniSabado = horaIniSabado;
    }

    public String getHoraFinLunes() {
        return horaFinLunes;
    }

    public void setHoraFinLunes(String horaFinLunes) {
        this.horaFinLunes = horaFinLunes;
    }

    public String getHoraFinMartes() {
        return horaFinMartes;
    }

    public void setHoraFinMartes(String horaFinMartes) {
        this.horaFinMartes = horaFinMartes;
    }

    public String getHoraFinMiercoles() {
        return horaFinMiercoles;
    }

    public void setHoraFinMiercoles(String horaFinMiercoles) {
        this.horaFinMiercoles = horaFinMiercoles;
    }

    public String getHoraFinJueves() {
        return horaFinJueves;
    }

    public void setHoraFinJueves(String horaFinJueves) {
        this.horaFinJueves = horaFinJueves;
    }

    public String getHoraFinViernes() {
        return horaFinViernes;
    }

    public void setHoraFinViernes(String horaFinViernes) {
        this.horaFinViernes = horaFinViernes;
    }

    public String getHoraFinSabado() {
        return horaFinSabado;
    }

    public void setHoraFinSabado(String horaFinSabado) {
        this.horaFinSabado = horaFinSabado;
    }

    public String getHoraRefrigerioLunes() {
        return horaRefrigerioLunes;
    }

    public void setHoraRefrigerioLunes(String horaRefrigerioLunes) {
        this.horaRefrigerioLunes = horaRefrigerioLunes;
    }

    public String getHoraRefrigerioMartes() {
        return horaRefrigerioMartes;
    }

    public void setHoraRefrigerioMartes(String horaRefrigerioMartes) {
        this.horaRefrigerioMartes = horaRefrigerioMartes;
    }

    public String getHoraRefrigerioMiercoles() {
        return horaRefrigerioMiercoles;
    }

    public void setHoraRefrigerioMiercoles(String horaRefrigerioMiercoles) {
        this.horaRefrigerioMiercoles = horaRefrigerioMiercoles;
    }

    public String getHoraRefrigerioJueves() {
        return horaRefrigerioJueves;
    }

    public void setHoraRefrigerioJueves(String horaRefrigerioJueves) {
        this.horaRefrigerioJueves = horaRefrigerioJueves;
    }

    public String getHoraRefrigerioViernes() {
        return horaRefrigerioViernes;
    }

    public void setHoraRefrigerioViernes(String horaRefrigerioViernes) {
        this.horaRefrigerioViernes = horaRefrigerioViernes;
    }

    public String getHoraRefrigerioSabado() {
        return horaRefrigerioSabado;
    }

    public void setHoraRefrigerioSabado(String horaRefrigerioSabado) {
        this.horaRefrigerioSabado = horaRefrigerioSabado;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getTiempoContrato() {
        return tiempoContrato;
    }

    public void setTiempoContrato(String tiempoContrato) {
        this.tiempoContrato = tiempoContrato;
    }
 

}
