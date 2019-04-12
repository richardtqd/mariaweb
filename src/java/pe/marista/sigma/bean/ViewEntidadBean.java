/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable; 

/**
 *
 * @author MS002
 */
public class ViewEntidadBean implements Serializable{
    private String ruc;
    private String nombre; 
    private String uniNeg; 
    private String entidadVista; 
    private Integer idPais;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEntidadVista() {
        return entidadVista;
    }

    public void setEntidadVista(String entidadVista) {
        this.entidadVista = entidadVista;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }
    
    
    
            
}
