/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class PerfilUnidadNegocioBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//idUniNeg
    private PerfilBean perfilBean;//idPerfil
    private int status;
    
    //Pruebas
    private String nombreUniNeg;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public PerfilBean getPerfilBean() {
        if (perfilBean == null) {
            perfilBean = new PerfilBean();
        }
        return perfilBean;
    }

    public void setPerfilBean(PerfilBean perfilBean) {
        this.perfilBean = perfilBean;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }
    
}
