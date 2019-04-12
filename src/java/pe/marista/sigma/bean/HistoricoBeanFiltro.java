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
public class HistoricoBeanFiltro implements Serializable{
    
    
    private Float porSatisfecho;
    private Float porMedSatisfecho;
    private Float porNoSatisfecho;

    /**
     * @return the porSatisfecho
     */
    public Float getPorSatisfecho() {
        return porSatisfecho;
    }

    /**
     * @param porSatisfecho the porSatisfecho to set
     */
    public void setPorSatisfecho(Float porSatisfecho) {
        this.porSatisfecho = porSatisfecho;
    }

    /**
     * @return the porMedSatisfecho
     */
    public Float getPorMedSatisfecho() {
        return porMedSatisfecho;
    }

    /**
     * @param porMedSatisfecho the porMedSatisfecho to set
     */
    public void setPorMedSatisfecho(Float porMedSatisfecho) {
        this.porMedSatisfecho = porMedSatisfecho;
    }

    /**
     * @return the porNoSatisfecho
     */
    public Float getPorNoSatisfecho() {
        return porNoSatisfecho;
    }

    /**
     * @param porNoSatisfecho the porNoSatisfecho to set
     */
    public void setPorNoSatisfecho(Float porNoSatisfecho) {
        this.porNoSatisfecho = porNoSatisfecho;
    }
    
    
    
}
