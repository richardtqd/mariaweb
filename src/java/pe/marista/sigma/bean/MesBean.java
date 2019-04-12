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
public class MesBean implements Serializable{
    private Integer valor;
    private String descripcion;
    private Boolean flg;

    public MesBean(Integer valor, String descripcion) {
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public MesBean() {
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getFlg() {
        return flg;
    }

    public void setFlg(Boolean flg) {
        this.flg = flg;
    }
       
}
