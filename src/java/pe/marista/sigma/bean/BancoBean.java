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
public class BancoBean implements Serializable{

    private String cuenta;
    private String nombre;
    private String moneda;
    private String nroCuenta;
    private String nroCheque;
    
    private PersonalBean personalBean;
    private ProveedorBean proveedorBean;
    

    public BancoBean() {
      
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public BancoBean(String cuenta, String nombre, String moneda, String nroCuenta, String nroCheque) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.moneda = moneda;
        this.nroCuenta = nroCuenta;
        this.nroCheque = nroCheque;
    }

    public BancoBean(String nombre) {
        this.nombre = nombre;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public ProveedorBean getProveedorBean() {
        if (proveedorBean == null) {
            proveedorBean = new ProveedorBean();
        }return proveedorBean;
    }

    public void setProveedorBean(ProveedorBean proveedorBean) {
        this.proveedorBean = proveedorBean;
    }
    
    
    
}
