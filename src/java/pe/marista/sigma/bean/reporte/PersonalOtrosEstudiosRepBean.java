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
public class PersonalOtrosEstudiosRepBean implements Serializable{
    private String institucion;
    private String periodo;
    private String cursoSem;
    private String nroCreditos; 
    private String nroHoras;  

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

    public String getCursoSem() {
        return cursoSem;
    }

    public void setCursoSem(String cursoSem) {
        this.cursoSem = cursoSem;
    }

    public String getNroCreditos() {
        return nroCreditos;
    }

    public void setNroCreditos(String nroCreditos) {
        this.nroCreditos = nroCreditos;
    }

    public String getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(String nroHoras) {
        this.nroHoras = nroHoras;
    }
 
    
}
