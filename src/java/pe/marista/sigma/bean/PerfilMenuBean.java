
package pe.marista.sigma.bean;

import java.io.Serializable;

public class PerfilMenuBean implements Serializable
{
    private PerfilUsuarioBean perfilUsuarioBean;
    private String nomMenu;
    
    public PerfilMenuBean()
    {}
    public PerfilMenuBean(PerfilUsuarioBean perfilUsuarioBean, String nomMenu) {
        this.perfilUsuarioBean = perfilUsuarioBean;
        this.nomMenu = nomMenu;
    }

    public PerfilUsuarioBean getPerfilUsuarioBean() {
        return perfilUsuarioBean;
    }

    public void setPerfilUsuarioBean(PerfilUsuarioBean perfilUsuarioBean) {
        this.perfilUsuarioBean = perfilUsuarioBean;
    }

    public String getNomMenu() {
        return nomMenu;
    }

    public void setNomMenu(String nomMenu) {
        this.nomMenu = nomMenu;
    }
    
}
