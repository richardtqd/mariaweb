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
public class TelefonoBean implements Serializable{
    
    private Integer idTelefono;
    private PersonalBean personalBean; // idPersonal
    private CodigoBean codigoIdTipoOperador; //idTipoCodigo
    private String telefono;
    private String extension;

    public Integer getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Integer idTelefono) {
        this.idTelefono = idTelefono;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
    }return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public CodigoBean getCodigoIdTipoOperador() {
        if (codigoIdTipoOperador == null) {
            codigoIdTipoOperador = new CodigoBean();
    }return codigoIdTipoOperador;
    }

    public void setCodigoIdTipoOperador(CodigoBean codigoIdTipoOperador) {
        this.codigoIdTipoOperador = codigoIdTipoOperador;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    
    
    
}
