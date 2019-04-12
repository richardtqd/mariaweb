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
public class EstudianteMovilidadBean implements Serializable{
    
    private EstudianteBean estudianteBean;
    private MovilidadBean movilidadBean;
    private String  obs; 

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean == null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public MovilidadBean getMovilidadBean() {
        if(movilidadBean == null){
            movilidadBean = new MovilidadBean();
        }
        return movilidadBean;
    }

    public void setMovilidadBean(MovilidadBean movilidadBean) {
        this.movilidadBean = movilidadBean;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
}
