/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class FuncionBean implements Serializable{
    
    private Integer idFuncion;
    private String codigo;
    private String nombre;
    private CodigoBean tipoFuncion; //idTipoFuncion

    public Integer getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     

    public CodigoBean getTipoFuncion() {
        if (tipoFuncion == null) {
            tipoFuncion = new CodigoBean();
        }
        return tipoFuncion;
    }

    public void setTipoFuncion(CodigoBean tipoFuncion) {
        this.tipoFuncion = tipoFuncion;
    }
    
    
}
