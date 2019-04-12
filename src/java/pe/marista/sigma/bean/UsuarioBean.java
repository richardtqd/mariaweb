/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class UsuarioBean implements Serializable {

    private String usuario;
    private String usuarioUniNeg;
    private String clave;
    private String clave2;//confirmacion
    private String claveAnterior;
    private int status;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private PersonalBean personalBean;//idPersona
    //Ayuda Edicion
    private Integer edita;
    private boolean estado;
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getStatusVista() {
        if (status == 1) {
            return MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if (status == 0) {
            return MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return MaristaConstantes.SIN_ESTADO;
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
 
    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Integer getEdita() {
        return edita;
    }

    public void setEdita(Integer edita) {
        this.edita = edita;
    }

    public boolean isEstado() {
        if (status == 0) {
            estado = false;
            return false;
        }
        if (status == 1) {
            estado = true;
            return true;
        }
        return estado;
    }

    public boolean getEstado2() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getStatus() {
        return status;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getUsuarioUniNeg() {
        return usuarioUniNeg;
    }

    public void setUsuarioUniNeg(String usuarioUniNeg) {
        this.usuarioUniNeg = usuarioUniNeg;
    }

    public String getClave2() {
        return clave2;
    }

    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }

    public String getClaveAnterior() {
        return claveAnterior;
    }

    public void setClaveAnterior(String claveAnterior) {
        this.claveAnterior = claveAnterior;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
    
    
}
