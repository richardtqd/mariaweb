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
 * @author MS001
 */
public class ChequeBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idCheque;
    private CuentaBancoBean cuentaBancoBean;
    private String nombre;
    private String impresora;
    private String inicio;
    private String fin;
    private String actual;
    private Boolean status;
    private String obs;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;

    //ayuda
    private String vistaStatus;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdCheque() {
        return idCheque;
    }

    public void setIdCheque(Integer idCheque) {
        this.idCheque = idCheque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getActual() {
        return actual;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
    }

    public String getVistaStatus() {
        if (status != null) {
            if (status == true) {
                vistaStatus = MaristaConstantes.Activo;
            } else {
                vistaStatus = MaristaConstantes.Inactivo;
            }
        }
        return vistaStatus;
    }

    public void setVistaStatus(String vistaStatus) {
        this.vistaStatus = vistaStatus;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

}
