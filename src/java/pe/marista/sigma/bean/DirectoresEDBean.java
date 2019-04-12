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
public class DirectoresEDBean implements Serializable{
    
    private String unineg;
    private String unidad;
    private float cardinal;
    private float especifica;

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getCardinal() {
        return cardinal;
    }

    public void setCardinal(float cardinal) {
        this.cardinal = cardinal;
    }

    public float getEspecifica() {
        return especifica;
    }

    public void setEspecifica(float especifica) {
        this.especifica = especifica;
    }

    
    
}
