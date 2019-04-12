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
public class IdiomasBean implements Serializable{
    private PersonalBean personalBean;//id
    private Integer idIdioma;
    private CodigoBean tipoNivel; //idTipoNivelEstudio

    public PersonalBean getPersonalBean() {
         if (personalBean == null) {
            personalBean = new PersonalBean();
    }return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Integer getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(Integer idIdioma) {
        this.idIdioma = idIdioma;
    }

    public CodigoBean getTipoNivel() {
        if (tipoNivel == null) {
            tipoNivel = new CodigoBean();
    }
        return tipoNivel;
    }

    public void setTipoNivel(CodigoBean tipoNivel) {
        this.tipoNivel = tipoNivel;
    }
    
    
    
}
