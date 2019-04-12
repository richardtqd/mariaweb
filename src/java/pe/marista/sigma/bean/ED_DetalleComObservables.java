/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class ED_DetalleComObservables implements Serializable{
    
    private Integer idcompetencia;
    private String competencia;
    private Float autoEvaluacion;
    private Float promedio;

    public Integer getIdcompetencia() {
        return idcompetencia;
    }

    public void setIdcompetencia(Integer idcompetencia) {
        this.idcompetencia = idcompetencia;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Float getAutoEvaluacion() {
        return autoEvaluacion;
    }

    public void setAutoEvaluacion(Float autoEvaluacion) {
        this.autoEvaluacion = autoEvaluacion;
    }

    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }


    
}
