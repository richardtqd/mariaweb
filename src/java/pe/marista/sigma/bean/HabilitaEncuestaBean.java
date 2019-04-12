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
public class HabilitaEncuestaBean implements Serializable{
    
    private Integer idevaluadoevaluador;
    private String evaluador;
    private String cargoevaluador;
    private String evaluado;
    private String cargoevaluado;
    private boolean flag;

    
    public Integer getIdevaluadoevaluador() {
        return idevaluadoevaluador;
    }

    public void setIdevaluadoevaluador(Integer idevaluadoevaluador) {
        this.idevaluadoevaluador = idevaluadoevaluador;
    }

    public String getEvaluador() {
        return evaluador;
    }

    public void setEvaluador(String evaluador) {
        this.evaluador = evaluador;
    }

    public String getCargoevaluador() {
        return cargoevaluador;
    }

    public void setCargoevaluador(String cargoevaluador) {
        this.cargoevaluador = cargoevaluador;
    }

    public String getEvaluado() {
        return evaluado;
    }

    public void setEvaluado(String evaluado) {
        this.evaluado = evaluado;
    }

    public String getCargoevaluado() {
        return cargoevaluado;
    }

    public void setCargoevaluado(String cargoevaluado) {
        this.cargoevaluado = cargoevaluado;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    
    
}
