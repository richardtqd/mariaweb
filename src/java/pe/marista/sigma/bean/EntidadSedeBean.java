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
public class EntidadSedeBean implements Serializable
{
    private Integer idEntidadSede;
    private EntidadBean entidadBean; // id
    private DistritoBean distritoBean; //id
    private String nombre;
    private String direccion;
    private String telefono;
    private String correo;
    private String urbanizacion;
    //AyudaBusqueda
    private EntidadBean entidadBean1;//parroquia
    private EntidadBean entidadBean2;//colegioPro
    private Integer nroItem;

    public Integer getNroItem() {
        return nroItem;
    }

    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
    }
    
    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public DistritoBean getDistritoBean() {
        if (distritoBean == null) {
            distritoBean = new DistritoBean();
        }
        return distritoBean;
    }

    public void setDistritoBean(DistritoBean distritoBean) {
        this.distritoBean = distritoBean;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUrbanizacion() {
        return urbanizacion;
    }

    public void setUrbanizacion(String urbanizacion) {
        this.urbanizacion = urbanizacion;
    }

    public Integer getIdEntidadSede() {
        return idEntidadSede;
    }

    public void setIdEntidadSede(Integer idEntidadSede) {
        this.idEntidadSede = idEntidadSede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EntidadBean getEntidadBean1() {
        if(entidadBean1==null){
            entidadBean1 = new EntidadBean();
        }
        return entidadBean1;
    }

    public void setEntidadBean1(EntidadBean entidadBean1) {
        this.entidadBean1 = entidadBean1;
    }

    public EntidadBean getEntidadBean2() {
        if(entidadBean2==null){
            entidadBean2 = new EntidadBean();
        }
        return entidadBean2;
    }

    public void setEntidadBean2(EntidadBean entidadBean2) {
        this.entidadBean2 = entidadBean2;
    }

}
