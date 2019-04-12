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
public class DirectoresPromBean implements  Serializable{
    
    private String  unineg;
    private String  nombreUniNeg;
    private float   promedio;
    private float   autoevaluacion;

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

    public float getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(float autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }
    
    
}
