/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class PerfilRepBean implements Serializable{
    
    private String nombre;
    private boolean status;
    private String creapor;
    private Timestamp creafecha;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreapor() {
        return creapor;
    }

    public void setCreapor(String creapor) {
        this.creapor = creapor;
    }

    public Timestamp getCreafecha() {
        return creafecha;
    }

    public void setCreafecha(Timestamp creafecha) {
        this.creafecha = creafecha;
    }
    
    
    
}
