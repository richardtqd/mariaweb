/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class ModuloBean implements Serializable {

    private Integer idModulo;
    private Integer idTipoNodo;
    private String nodo;
    private String descrip;
    private Integer idModuloPadre;
    private String url;
    private String folder;
    private String icono;
    private int status;
    private Integer posicion;
    private String creaPor;
    private Date creaFecha;
    //Ayudas
    private String nombrePadre;
    private Boolean moduloDisable;
    private Date modiFecha;

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public Integer getIdModuloPadre() {
        return idModuloPadre;
    }

    public void setIdModuloPadre(Integer idModuloPadre) {
        this.idModuloPadre = idModuloPadre;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Integer getIdTipoNodo() {
        return idTipoNodo;
    }

    public void setIdTipoNodo(Integer idTipoNodo) {
        this.idTipoNodo = idTipoNodo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getNombrePadre() {
        return nombrePadre;
    }

    public void setNombrePadre(String nombrePadre) {
        this.nombrePadre = nombrePadre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getModuloDisable() {
        return moduloDisable;
    }

    public void setModuloDisable(Boolean moduloDisable) {
        this.moduloDisable = moduloDisable;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Date getModiFecha() {
        return modiFecha;
    }

    public void setModiFecha(Date modiFecha) {
        this.modiFecha = modiFecha;
    }

}
