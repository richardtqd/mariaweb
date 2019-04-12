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
public class VistaBean implements Serializable {

    private PerfilModuloBean perfilModuloBean;
    private UsuarioBean usuarioBean;
    private String usuario;
    private Integer idPerfil;

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public PerfilModuloBean getPerfilModuloBean() {
        if(perfilModuloBean==null){
            perfilModuloBean = new PerfilModuloBean();
        }
        return perfilModuloBean;
    }

    public void setPerfilModuloBean(PerfilModuloBean perfilModuloBean) {
        this.perfilModuloBean = perfilModuloBean;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

}
