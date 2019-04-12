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
public class ResumenEvaDesempeno {
    
    private String codper;
    private Float promedio; 
    private Float ponderacion;

    public String getCodper() {
        return codper;
    }

    public void setCodper(String codper) {
        this.codper = codper;
    }


    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }

    public Float getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Float ponderacion) {
        this.ponderacion = ponderacion;
    }


    
}
