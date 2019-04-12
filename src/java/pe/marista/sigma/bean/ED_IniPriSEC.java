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
public class ED_IniPriSEC implements  Serializable{
    
    private Integer idcodigo;
    private String descripcion;
    private float promedio;
    private float ponderado;

    public Integer getIdcodigo() {
        return idcodigo;
    }

    public void setIdcodigo(Integer idcodigo) {
        this.idcodigo = idcodigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }

    public float getPonderado() {
        return ponderado;
    }

    public void setPonderado(float ponderado) {
        this.ponderado = ponderado;
    }

    
}
