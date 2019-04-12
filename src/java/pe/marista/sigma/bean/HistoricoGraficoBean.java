/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

/**
 *
 * @author MS001
 */
public class HistoricoGraficoBean {
    
     private String nombre;
     private float porSatisfecho;

    /**
     * @return the porSatisfecho
     */
    public float getPorSatisfecho() {
        return porSatisfecho;
    }

    /**
     * @param porSatisfecho the porSatisfecho to set
     */
    public void setPorSatisfecho(float porSatisfecho) {
        this.porSatisfecho = porSatisfecho;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
