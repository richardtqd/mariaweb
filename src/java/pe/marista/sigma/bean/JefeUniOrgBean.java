/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS001
 */
public class JefeUniOrgBean implements Serializable {

    private Integer idJefeUniOrg;
    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private PersonalBean personalBean;
    private CargoBean cargoBean;

    private Date fecIni;
    private Date fecTer;
    private Boolean status;
    private String obs;
    private Date creaFecha;
    private String creaPor;
    private String modiPor;

    //Ayuda
    private String statusVista;
    private String fecIniVista;
    private String fecTermVista;

    public Integer getIdJefeUniOrg() {
        return idJefeUniOrg;
    }

    public void setIdJefeUniOrg(Integer idJefeUniOrg) {
        this.idJefeUniOrg = idJefeUniOrg;
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

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
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

    public CargoBean getCargoBean() {
        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecTer() {
        return fecTer;
    }

    public void setFecTer(Date fecTer) {
        this.fecTer = fecTer;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public String getFecIniVista() {
        if (fecIni != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fecIni);
            fecIniVista = date;
        } else {
            fecIniVista = MaristaConstantes.SIN_FECHA;
        }

        return fecIniVista;
    }

    public void setFecIniVista(String fecIniVista) {
        this.fecIniVista = fecIniVista;
    }

    public String getFecTermVista() {
         if (fecTer != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(fecTer);
            fecTermVista = date;
        } else {
            fecTermVista = MaristaConstantes.SIN_FECHA;
        }
        return fecTermVista;
    }

    public void setFecTermVista(String fecTermVista) {
        this.fecTermVista = fecTermVista;
    }

}
