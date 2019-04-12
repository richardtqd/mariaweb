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
public class PersonalFormacionRepBean implements Serializable{
    private String institucion;
    private String periodo;
    private String nivel;
    private String grado; 
    private String descripGrado; 
    private String textoCarreraTitulo; 

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getDescripGrado() {
        return descripGrado;
    }

    public void setDescripGrado(String descripGrado) {
        this.descripGrado = descripGrado;
    }

    public String getTextoCarreraTitulo() {
        return textoCarreraTitulo;
    }

    public void setTextoCarreraTitulo(String textoCarreraTitulo) {
        this.textoCarreraTitulo = textoCarreraTitulo;
    }
    
    
}
