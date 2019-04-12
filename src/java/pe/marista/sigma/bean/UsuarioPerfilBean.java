package pe.marista.sigma.bean;

import java.io.Serializable;

public class UsuarioPerfilBean implements Serializable {

    private UsuarioBean usuarioBean;//codUsuario
    private PerfilUsuarioBean perfilUsuarioBean;//codPerfil

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public PerfilUsuarioBean getPerfilUsuarioBean() {
        if (perfilUsuarioBean == null) {
            perfilUsuarioBean = new PerfilUsuarioBean();
        }
        return perfilUsuarioBean;
    }

    public void setPerfilUsuarioBean(PerfilUsuarioBean perfilUsuarioBean) {
        this.perfilUsuarioBean = perfilUsuarioBean;
    }
}
