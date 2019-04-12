/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

/**
 *
 * @author MS001
 */
public class PersonalDependienteRepBean {
    private String nombreDependiente;
    private String parentesco;
    private String nroDoc;
    private String telefono;
    private String fecNac;
    private Integer edad;

    public String getNombreDependiente() {
        return nombreDependiente;
    }

    public void setNombreDependiente(String nombreDependiente) {
        this.nombreDependiente = nombreDependiente;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecNac() {
        return fecNac;
    }

    public void setFecNac(String fecNac) {
        this.fecNac = fecNac;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

   
}
