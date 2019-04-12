/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS-005
 */
public class UniNegUniOrgBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String statusVista;
    private Boolean flgSeleccionar;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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

    public String getStatusVista() {
        if (status != null) {
            if (status.equals(true)) {
                statusVista = MaristaConstantes.Activo;
            } else {
                statusVista = MaristaConstantes.Inactivo;
            }
        } else {
            statusVista = MaristaConstantes.SinEstado;
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public Boolean getFlgSeleccionar() {
        return flgSeleccionar;
    }

    public void setFlgSeleccionar(Boolean flgSeleccionar) {
        this.flgSeleccionar = flgSeleccionar;
    }

}
