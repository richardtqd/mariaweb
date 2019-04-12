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
public class ProcesoErrorBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private ProcesoBancoBean procesoBancoBean;
    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private ProcesoEnvioBean procesoEnvioBean;
    private String descripcion;
    private Integer idTtipoError;
    private Integer flgError;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
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

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if(procesoRecuperacionBean == null){
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public ProcesoEnvioBean getProcesoEnvioBean() {
        if(procesoEnvioBean == null){
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTtipoError() {
        return idTtipoError;
    }

    public void setIdTtipoError(Integer idTtipoError) {
        this.idTtipoError = idTtipoError;
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
 
}
