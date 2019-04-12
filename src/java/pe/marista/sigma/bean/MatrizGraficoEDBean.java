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
public class MatrizGraficoEDBean implements Serializable{
    
    private Integer idevaluadoevaluador;
    private String nombre;
    private String unineg;
    private String idcargo;
    private Integer anio;
    private float cardinales;
    private float especificas;
    private float  prom;
    private float autoevaluacion;
    
    public Integer getIdevaluadoevaluador() {
        return idevaluadoevaluador;
    }

    public void setIdevaluadoevaluador(Integer idevaluadoevaluador) {
        this.idevaluadoevaluador = idevaluadoevaluador;
    }

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

    public String getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(String idcargo) {
        this.idcargo = idcargo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public float getCardinales() {
        return cardinales;
    }

    public void setCardinales(float cardinales) {
        this.cardinales = cardinales;
    }

    public float getEspecificas() {
        return especificas;
    }

    public void setEspecificas(float especificas) {
        this.especificas = especificas;
    }

    public float getProm() {
        return prom;
    }

    public void setProm(float prom) {
        this.prom = prom;
    }

    public float getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(float autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }

    
    
}
