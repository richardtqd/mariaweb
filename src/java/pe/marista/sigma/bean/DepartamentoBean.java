/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class DepartamentoBean implements Serializable{
    private Integer idDepartamento;
    private String nombre;

    public DepartamentoBean() {
        this.idDepartamento = MaristaConstantes.DEP_LIMA;
    }
    
    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
