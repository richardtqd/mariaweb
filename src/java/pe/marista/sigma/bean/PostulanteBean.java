/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class PostulanteBean implements Serializable{
    
    private Integer codUsuario;
    private String nombre;
    private String apepat;
    private String apemat;
    private String dni;
    private String grado;
    private String nivel;

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public PostulanteBean(Integer codUsuario, String nombre, String apepat, String apemat, String dni, String grado, String nivel) {
        this.codUsuario = codUsuario;
        this.nombre = nombre;
        this.apepat = apepat;
        this.apemat = apemat;
        this.dni = dni;
        this.grado = grado;
        this.nivel = nivel;
    }


    public PostulanteBean() {
    }
    
    
    
}
