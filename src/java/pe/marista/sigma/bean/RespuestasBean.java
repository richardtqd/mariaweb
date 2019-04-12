/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class RespuestasBean implements Serializable {

    private Integer idRespuesta;
    private PreguntaBean preguntaBean;
    private Integer orden;
    private String rta1;
    private String rta2;
    private String rta3;
    private String rta4;

    private String rta1Vista;
    private String rta2Vista;
    private String rta3Vista;
    private String rta4Vista;
    //ayuda
    private Integer ordenUltimo;

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public PreguntaBean getPreguntaBean() {
        if (preguntaBean == null) {
            preguntaBean = new PreguntaBean();
        }
        return preguntaBean;
    }

    public void setPreguntaBean(PreguntaBean preguntaBean) {
        this.preguntaBean = preguntaBean;
    }

    public String getRta1() {
        return rta1;
    }

    public void setRta1(String rta1) {
        this.rta1 = rta1;
    }

    public String getRta2() {
        return rta2;
    }

    public void setRta2(String rta2) {
        this.rta2 = rta2;
    }

    public String getRta3() {
        return rta3;
    }

    public void setRta3(String rta3) {
        this.rta3 = rta3;
    }

    public String getRta4() {
        return rta4;
    }

    public void setRta4(String rta4) {
        this.rta4 = rta4;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getOrdenUltimo() {
        return ordenUltimo;
    }

    public void setOrdenUltimo(Integer ordenUltimo) {
        this.ordenUltimo = ordenUltimo;
    }

    public String getRta1Vista() {
        if (rta1 == null) {
            rta1Vista = "-";
        } else {
            rta1Vista = rta1;
        }
        return rta1Vista;
    }

    public void setRta1Vista(String rta1Vista) {
        this.rta1Vista = rta1Vista;
    }

    public String getRta2Vista() {
        if (rta2 == null) {
            rta2Vista = "-";
        } else {
            rta2Vista = rta2;
        }
        return rta2Vista;
    }

    public void setRta2Vista(String rta2Vista) {
        this.rta2Vista = rta2Vista;
    }

    public String getRta3Vista() {
        if (rta3 == null) {
            rta3Vista = "-";
        } else {
            rta3Vista = rta3;
        }
        return rta3Vista;
    }

    public void setRta3Vista(String rta3Vista) {
        this.rta3Vista = rta3Vista;
    }

    public String getRta4Vista() {
        if (rta4 == null) {
            rta4Vista = "-";
        } else {
            rta4Vista = rta4;
        }
        return rta4Vista;
    }

    public void setRta4Vista(String rta4Vista) {
        this.rta4Vista = rta4Vista;
    }

}
