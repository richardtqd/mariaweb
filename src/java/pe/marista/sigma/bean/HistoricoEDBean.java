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
public class HistoricoEDBean implements  Serializable{
    
    private String nombre;
    private String unineg;
    private float  ED_2016;
    private float  ED_2017;
    private float  ED_2018;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public float getED_2016() {
        return ED_2016;
    }

    public void setED_2016(float ED_2016) {
        this.ED_2016 = ED_2016;
    }

    public float getED_2017() {
        return ED_2017;
    }

    public void setED_2017(float ED_2017) {
        this.ED_2017 = ED_2017;
    }

    public float getED_2018() {
        return ED_2018;
    }

    public void setED_2018(float ED_2018) {
        this.ED_2018 = ED_2018;
    }
    
    
    
}
