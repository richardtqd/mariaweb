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
public class HistoricoEDListBean implements Serializable{
    
    private Integer idhistorico;
    private String uniNeg;
    private String nombreUniNeg;
    private String nombrePersonal;
    private Integer idCargo;
    private String nombreCargo;    
    private float ED_2016;
    private float ED_2017;   

    public Integer getIdhistorico() {
        return idhistorico;
    }

    public void setIdhistorico(Integer idhistorico) {
        this.idhistorico = idhistorico;
    }
    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public float getED_2016() {
        return ED_2016;
    }

    public void setED_2016(float ED_2016) {
        this.ED_2016 = ED_2016;
    }

    public float getED_2017() {
        return ED_2017;
    }

    public void setED_2017(float ED_2017) {
        this.ED_2017 = ED_2017;
    }
    
    
}
