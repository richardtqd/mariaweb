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
public class DocumentoCargoBean implements Serializable {

    private CargoBean cargoBean;
    private DocumentoBean documentoBean;
    private Boolean flgObligatorio;
    private CodigoBean tipoCopiaBean;
    private Boolean status = true;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    //vista
    private String flgObligatorioVista;
    private String statusVista;

    public CargoBean getCargoBean() {
        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public DocumentoBean getDocumentoBean() {
        if (documentoBean == null) {
            documentoBean = new DocumentoBean();
        }
        return documentoBean;
    }

    public void setDocumentoBean(DocumentoBean documentoBean) {
        this.documentoBean = documentoBean;
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

    public CodigoBean getTipoCopiaBean() {
        if (tipoCopiaBean == null) {
            tipoCopiaBean = new CodigoBean();
        }
        return tipoCopiaBean;
    }

    public void setTipoCopiaBean(CodigoBean tipoCopiaBean) {
        this.tipoCopiaBean = tipoCopiaBean;
    }

    public String getFlgObligatorioVista() {
        if (flgObligatorio != null) {
            if (flgObligatorio == true) {
                flgObligatorioVista = "SI";
            }
            if (flgObligatorio == false) {
                flgObligatorioVista = "NO";
            }
        } else {
            flgObligatorioVista = "NO";
        }
        return flgObligatorioVista;
    }

    public void setFlgObligatorioVista(String flgObligatorioVista) {
        this.flgObligatorioVista = flgObligatorioVista;
    }

    public String getStatusVista() {
        if (status != null) {

            if (status == true) {
                statusVista = MaristaConstantes.ESTADO_ACTIVO_DES;
            }
            if (status == false) {
                statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
            }
        } else {
            statusVista = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return statusVista;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgObligatorio() {
        return flgObligatorio;
    }

    public void setFlgObligatorio(Boolean flgObligatorio) {
        this.flgObligatorio = flgObligatorio;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
