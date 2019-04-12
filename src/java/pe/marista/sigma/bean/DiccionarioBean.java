/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS-005
 */
public class DiccionarioBean implements Serializable{
    private Integer idObjeto;
    private String tabla;
    private String columna;
    private String tipo;
    private int precision;
    private int maxLong ;
    private String nulos;
    private String identity;
    private String descripcion;
    private String descrip;
     
    private String foreignkey;
    private String tablaRef;
    private String columnaRef;
    private boolean status;
    
    private String nomStatus;

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }
 
    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getMaxLong() {
        return maxLong;
    }

    public void setMaxLong(int maxLong) {
        this.maxLong = maxLong;
    }

    public String getNulos() {
        return nulos;
    }

    public void setNulos(String nulos) {
        this.nulos = nulos;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    

    public String getTablaRef() {
        return tablaRef;
    }

    public void setTablaRef(String tablaRef) {
        this.tablaRef = tablaRef;
    }

    public String getColumnaRef() {
        return columnaRef;
    }

    public void setColumnaRef(String columnaRef) {
        this.columnaRef = columnaRef;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getForeignkey() {
        return foreignkey;
    }

    public void setForeignkey(String foreignkey) {
        this.foreignkey = foreignkey;
    }

    public String getNomStatus() {
        if (status == true) {
            nomStatus = "Activo";
            return nomStatus;
        }
        if (status == false) {
            nomStatus = "Inactivo";
            return nomStatus;
        }
        return nomStatus;
    }

    public void setNomStatus(String nomStatus) {
        this.nomStatus = nomStatus;
    }
            
    
            
    
}
