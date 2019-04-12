/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class ProcesoRecuperacionRepBean implements Serializable{
    
    private String idpersona;
    private String apepat;
    private String apemat;
    private String nombre;
    private Float montorecup;
    private String fechapago;
    private String datoPension;

    public String getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(String idpersona) {
        this.idpersona = idpersona;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getMontorecup() {
        return montorecup;
    }

    public void setMontorecup(Float montorecup) {
        this.montorecup = montorecup;
    }

    public String getFechapago() {
        return fechapago;
    }

    public void setFechapago(String fechapago) {
        this.fechapago = fechapago;
    }

    public String getDatoPension() {
        return datoPension;
    }

    public void setDatoPension(String datoPension) {
        this.datoPension = datoPension;
    }
    
}
