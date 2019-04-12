
package pe.marista.sigma.bean;

import java.io.Serializable;

public class PerfilUsuarioBean implements Serializable
{
    private Integer codPerfil;
    private String nomPerfil;

    public PerfilUsuarioBean()
    {}

    public PerfilUsuarioBean(Integer codPerfil, String nomPerfil) {
        this.codPerfil = codPerfil;
        this.nomPerfil = nomPerfil;
    }
   
    public Integer getCodPerfil() {
        return codPerfil;
    }

    public void setCodPerfil(Integer codPerfil) {
        this.codPerfil = codPerfil;
    }

    public String getNomPerfil() {
        return nomPerfil;
    }

    public void setNomPerfil(String nomPerfil) {
        this.nomPerfil = nomPerfil;
    }
    
}
