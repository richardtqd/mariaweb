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
 * @author Administrador
 */
public class DocumentoBean implements Serializable {

    private Integer idDocumento;
//    private UnidadNegocioBean unidadNegocioBean;
    private String nombre;
    private CodigoBean tipoCatDocBean;
    private boolean flgCaduca = false;
    private boolean status = true;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    //vista
    private String flgCaducaVista;
    private String statusVista;

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isFlgCaduca() {
        return flgCaduca;
    }

    public void setFlgCaduca(boolean flgCaduca) {
        this.flgCaduca = flgCaduca;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    public String getFlgCaducaVista() {
        if (flgCaduca == true) {
            flgCaducaVista = MaristaConstantes.SI;
        }
        if (flgCaduca == false) {
            flgCaducaVista = MaristaConstantes.NO;
        }
        return flgCaducaVista;
    }

    public void setFlgCaducaVista(String flgCaducaVista) {
        this.flgCaducaVista = flgCaducaVista;
    }

    public String getStatusVista() {
        if (status == true) {
            statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if (status == false) {
            statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public CodigoBean getTipoCatDocBean() {
        if (tipoCatDocBean == null) {
            tipoCatDocBean = new CodigoBean();
        }
        return tipoCatDocBean;
    }

    public void setTipoCatDocBean(CodigoBean tipoCatDocBean) {
        this.tipoCatDocBean = tipoCatDocBean;
    }

//    public UnidadNegocioBean getUnidadNegocioBean() {
//        if (unidadNegocioBean==null) {
//            unidadNegocioBean= new UnidadNegocioBean();
//        }
//        return unidadNegocioBean;
//    }
//
//    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
//        this.unidadNegocioBean = unidadNegocioBean;
//    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

}
