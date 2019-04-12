/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class DetalleIndicadorBean implements Serializable{
    
    private Integer idTipoNivelesColegio;
    private String codper;
    private String nombre;
    private String cargo;
    private Float promedio;

    public String getCodper() {
        return codper;
    }

    public void setCodper(String codper) {
        this.codper = codper;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }

    public Integer getIdTipoNivelesColegio() {
        return idTipoNivelesColegio;
    }

    public void setIdTipoNivelesColegio(Integer idTipoNivelesColegio) {
        this.idTipoNivelesColegio = idTipoNivelesColegio;
    }



}
