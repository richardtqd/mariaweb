/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class MovilidadBean implements Serializable{
    
    private UnidadNegocioBean uniNeg;
    private String idmovilidad;
    private String nroautorizacion;
    private String color;
    private String creaPor;
    private Date creafecha;
    private String modipor;
    private String modiver; 
  

    public String getIdmovilidad() {
        return idmovilidad;
    }

    public void setIdmovilidad(String idmovilidad) {
        this.idmovilidad = idmovilidad;
    }

    public String getNroautorizacion() {
        return nroautorizacion;
    }

    public void setNroautorizacion(String nroautorizacion) {
        this.nroautorizacion = nroautorizacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public UnidadNegocioBean getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(UnidadNegocioBean uniNeg) {
        this.uniNeg = uniNeg;
    } 
    
    public String getModipor() {
        return modipor;
    }

    public void setModipor(String modipor) {
        this.modipor = modipor;
    }

    public String getModiver() {
        return modiver;
    }

    public void setModiver(String modiver) {
        this.modiver = modiver;
    }

    public Date getCreafecha() {
        return creafecha;
    }

    public void setCreafecha(Date creafecha) {
        this.creafecha = creafecha;
    }
    
}
