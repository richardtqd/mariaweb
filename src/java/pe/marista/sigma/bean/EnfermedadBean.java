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
public class EnfermedadBean implements Serializable{
    
    private Integer idEnfermedad;
    private String enfermedad;
    private CodigoBean tipoEnfermedadBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
    
    public Integer getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Integer idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public CodigoBean getTipoEnfermedadBean() {
        if(tipoEnfermedadBean == null)
        {
            tipoEnfermedadBean = new CodigoBean();
        }
        return tipoEnfermedadBean;
    }

    public void setTipoEnfermedadBean(CodigoBean tipoEnfermedadBean) {
        this.tipoEnfermedadBean = tipoEnfermedadBean;
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
    
}
