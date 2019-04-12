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
public class PreguntaxCompetenciaBean  implements  Serializable{
    
    private Integer idpregunta;
    private Integer idcompetencia;
    private Integer idcodigo;
    private String idtipocompetencia;

    public Integer getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(Integer idpregunta) {
        this.idpregunta = idpregunta;
    }

    public Integer getIdcompetencia() {
        return idcompetencia;
    }

    public void setIdcompetencia(Integer idcompetencia) {
        this.idcompetencia = idcompetencia;
    }

    public Integer getIdcodigo() {
        return idcodigo;
    }

    public void setIdcodigo(Integer idcodigo) {
        this.idcodigo = idcodigo;
    }

    public String getIdtipocompetencia() {
        return idtipocompetencia;
    }

    public void setIdtipocompetencia(String idtipocompetencia) {
        this.idtipocompetencia = idtipocompetencia;
    }


    
}
