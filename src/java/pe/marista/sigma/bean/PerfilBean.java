/*
 * To change this template, choose Tools | Templates
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
public class PerfilBean implements Serializable {

    private Integer idPerfil;
    private String nombre;
    private int status;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    //Ayuda
    private boolean estado;

   

    public PerfilBean() {
    }
   
    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getStatusVista() {
        if (status == 1) {
            return MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if (status == 0) {
            return MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return MaristaConstantes.SIN_ESTADO;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
}
