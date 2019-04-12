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
public class PersonalEnfermedadBean implements Serializable {

    private Integer idPersonalEnfermedad;
    private UnidadNegocioBean unidadNegocioBean;
    private EnfermedadBean enfermedadBean;//id
    private PersonalBean personalBean; //id
    private Integer edadInicio;
    private String obs;
    private CodigoBean tipoStatusEnfermedadBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private boolean collapsed = true;

    public Integer getIdPersonalEnfermedad() {
        return idPersonalEnfermedad;
    }

    public void setIdPersonalEnfermedad(Integer idPersonalEnfermedad) {
        this.idPersonalEnfermedad = idPersonalEnfermedad;
    }

    public EnfermedadBean getEnfermedadBean() {
        if (enfermedadBean == null) {
            enfermedadBean = new EnfermedadBean();
        }
        return enfermedadBean;
    }

    public void setEnfermedadBean(EnfermedadBean enfermedadBean) {
        this.enfermedadBean = enfermedadBean;
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

    public Integer getEdadInicio() {
        return edadInicio;
    }

    public void setEdadInicio(Integer edadInicio) {
        this.edadInicio = edadInicio;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public CodigoBean getTipoStatusEnfermedadBean() {
        if (tipoStatusEnfermedadBean == null) {
            tipoStatusEnfermedadBean = new CodigoBean();
        }
        return tipoStatusEnfermedadBean;
    }

    public void setTipoStatusEnfermedadBean(CodigoBean tipoStatusEnfermedadBean) {
        this.tipoStatusEnfermedadBean = tipoStatusEnfermedadBean;
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

}
