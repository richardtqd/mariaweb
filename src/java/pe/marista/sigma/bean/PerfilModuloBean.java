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
public class PerfilModuloBean implements Serializable{
    private PerfilBean perfilBean;//idPerfil
    private ModuloBean moduloBean;
    private byte status;
    //Ayuda
//    private String usuario;
    private Integer idPerfil;
    private Integer idModulo;
    private String nodo;
    private String url;
    private Integer idTipoNodo;
    private Integer idModuloPadre;
    private String creaPor;
    private String modiPor;

    public PerfilBean getPerfilBean() {
        if(perfilBean == null){
            perfilBean = new PerfilBean();
        }
        return perfilBean;
    }

    public void setPerfilBean(PerfilBean perfilBean) {
        this.perfilBean = perfilBean;
    }

    public ModuloBean getModuloBean() {
        if(moduloBean == null){
            moduloBean = new ModuloBean();
        }
        return moduloBean;
    }

    public void setModuloBean(ModuloBean moduloBean) {
        this.moduloBean = moduloBean;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Integer getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Integer idModulo) {
        this.idModulo = idModulo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIdTipoNodo() {
        return idTipoNodo;
    }

    public void setIdTipoNodo(Integer idTipoNodo) {
        this.idTipoNodo = idTipoNodo;
    }

    public Integer getIdModuloPadre() {
        return idModuloPadre;
    }

    public void setIdModuloPadre(Integer idModuloPadre) {
        this.idModuloPadre = idModuloPadre;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
 
}
