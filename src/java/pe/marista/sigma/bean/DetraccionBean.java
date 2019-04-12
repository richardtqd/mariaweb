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
public class DetraccionBean implements Serializable {

    private Integer idDetraccion;
    private String descripcion;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Double valor;
    private String statusVista;

    public Integer getIdDetraccion() {
        return idDetraccion;
    }

    public void setIdDetraccion(Integer idDetraccion) {
        this.idDetraccion = idDetraccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getStatus() {
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatusVista() {
        if (status != null) {
            if (status == true) {
                statusVista = MaristaConstantes.Activo;
            }
            if (status == false) {
                statusVista = MaristaConstantes.Inactivo;
            }
        } 
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }
}
