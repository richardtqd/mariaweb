/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Contenedor implements Serializable {

    private Object valor;
    private List<Contenedor> listaContenedor; 

    public Contenedor() {
    }

    public Contenedor(Object valor) {
        this.valor = valor;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public List<Contenedor> getListaContenedor() {
        if (listaContenedor == null) {
            listaContenedor = new ArrayList<>();
        }
        return listaContenedor;
    }

    public void setListaContenedor(List<Contenedor> listaContenedor) {
        this.listaContenedor = listaContenedor;
    }

}
