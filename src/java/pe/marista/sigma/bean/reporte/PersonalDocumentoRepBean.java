/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class PersonalDocumentoRepBean  implements Serializable{
    private String nombreDoc;
    private String tipoCopia;
    private String fechaCaduca;
    private String original;
    private String obligatorio;
    private String fechaPresentacion;
    private String estado;

    public String getNombreDoc() {
        return nombreDoc;
    }

    public void setNombreDoc(String nombreDoc) {
        this.nombreDoc = nombreDoc;
    }

    public String getTipoCopia() {
        return tipoCopia;
    }

    public void setTipoCopia(String tipoCopia) {
        this.tipoCopia = tipoCopia;
    }

    public String getFechaCaduca() {
        return fechaCaduca;
    }

    public void setFechaCaduca(String fechaCaduca) {
        this.fechaCaduca = fechaCaduca;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(String obligatorio) {
        this.obligatorio = obligatorio;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
