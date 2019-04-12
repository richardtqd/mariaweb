/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

/**
 *
 * @author MS001
 */
public class DetalleNivelCargo {
    
    private String unineg;
    private String codper;
    private Integer idtiponivelescolegio;
    private Integer idcargo;   
    private String nombre;
    private String cargo;
    private float autoevaluacion;
    private float promedio;

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getCodper() {
        return codper;
    }

    public void setCodper(String codper) {
        this.codper = codper;
    }

    public Integer getIdtiponivelescolegio() {
        return idtiponivelescolegio;
    }

    public void setIdtiponivelescolegio(Integer idtiponivelescolegio) {
        this.idtiponivelescolegio = idtiponivelescolegio;
    }

    public Integer getIdcargo() {
        return idcargo;
    }

    public void setIdcargo(Integer idcargo) {
        this.idcargo = idcargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public float getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(float autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }
    
    
}
