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
public class SatisfaccionGrlBean implements Serializable{
    
    private String nombre;
    private Float porSatisfecho;
    private Float porMedSatisfecho;
    private Float porNoSatisfecho;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPorSatisfecho() {
        return porSatisfecho;
    }

    public void setPorSatisfecho(Float porSatisfecho) {
        this.porSatisfecho = porSatisfecho;
    }

    public Float getPorMedSatisfecho() {
        return porMedSatisfecho;
    }

    public void setPorMedSatisfecho(Float porMedSatisfecho) {
        this.porMedSatisfecho = porMedSatisfecho;
    }

    public Float getPorNoSatisfecho() {
        return porNoSatisfecho;
    }

    public void setPorNoSatisfecho(Float porNoSatisfecho) {
        this.porNoSatisfecho = porNoSatisfecho;
    }
    
    
    
    
}
