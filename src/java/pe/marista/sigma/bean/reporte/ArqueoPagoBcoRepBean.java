/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS001
 */
public class ArqueoPagoBcoRepBean implements Serializable{
    private Date fecha;
    private String strFecha;
    private String creaPor;
    private String cajero;
    private String cant;
    private String totalRecaudado;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(String totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

    public String getStrFecha() {
        return strFecha;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

     
    
    
}
