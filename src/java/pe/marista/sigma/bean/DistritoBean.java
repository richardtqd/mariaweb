/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class DistritoBean implements Serializable {

    private Integer idDistrito;
    private ProvinciaBean provinciaBean;
    private String nombre;
 
    public Integer getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(Integer idDistrito) {
        this.idDistrito = idDistrito;
    }

    public ProvinciaBean getProvinciaBean() {
        if (provinciaBean == null) {
            provinciaBean = new ProvinciaBean();
        }
        return provinciaBean;
    }

    public void setProvinciaBean(ProvinciaBean provinciaBean) {
        this.provinciaBean = provinciaBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
