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
public class ED_DetalleCompetencias implements  Serializable{
    
    private float cardinal;
    private float especifico;

    public float getCardinal() {
        return cardinal;
    }

    public void setCardinal(float cardinal) {
        this.cardinal = cardinal;
    }

    public float getEspecifico() {
        return especifico;
    }

    public void setEspecifico(float especifico) {
        this.especifico = especifico;
    }

    
}
