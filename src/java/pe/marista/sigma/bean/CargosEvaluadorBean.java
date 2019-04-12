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
public class CargosEvaluadorBean implements Serializable{
    
    private Integer idCargoEvaluador;
    private String nombre;

    public Integer getIdCargoEvaluador() {
        return idCargoEvaluador;
    }

    public void setIdCargoEvaluador(Integer idCargoEvaluador) {
        this.idCargoEvaluador = idCargoEvaluador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
