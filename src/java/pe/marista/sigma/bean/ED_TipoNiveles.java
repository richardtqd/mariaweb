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
public class ED_TipoNiveles implements Serializable{
    
    private Integer idTipoNivelesColegio;
    private String  competencia;
    private float puntaje;

    public Integer getIdTipoNivelesColegio() {
        return idTipoNivelesColegio;
    }

    public void setIdTipoNivelesColegio(Integer idTipoNivelesColegio) {
        this.idTipoNivelesColegio = idTipoNivelesColegio;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }
    
    
    
    
}
