/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.managedBean;

import javax.annotation.PostConstruct;
import pe.marista.sigma.bean.EstudianteBean;

/**
 *
 * @author Administrador
 */

public class PruebaMB {

    /**
     * Creates a new instance of PruebaMB
     */
    @PostConstruct
    public void PruebaMB() {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
    }
    private EstudianteBean estudianteBean;

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean==null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }
    
}
