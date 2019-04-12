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
public class EvaluadoBean implements Serializable{
    
    private Integer idEvaluadoEvaluador;
    private String codigoEvaluado;  
    private String codigoEvaluador;           
    private Integer idCargoEvaluado; 
    private String nombreCargoEvaluado;
    private Integer idCargoEvaluador; 
    private String nombreEvaluado;                           
    private String apellidoEvaluado;                                              
    private String uninegEvaluado; 
    private String nombreUniNegEvaluado;   
    private Integer idgrupoOcuEvaluado;   
    private String nombregrupoEvaluado;
    private Integer cantPregEspecificas;
    private Integer cantPreCardinales;
    private String codperevaluado;
    private boolean flgcargoprincipal;

    public Integer getIdEvaluadoEvaluador() {
        return idEvaluadoEvaluador;
    }

    public void setIdEvaluadoEvaluador(Integer idEvaluadoEvaluador) {
        this.idEvaluadoEvaluador = idEvaluadoEvaluador;
    }

    public String getCodigoEvaluado() {
        return codigoEvaluado;
    }

    public void setCodigoEvaluado(String codigoEvaluado) {
        this.codigoEvaluado = codigoEvaluado;
    }

    public String getCodigoEvaluador() {
        return codigoEvaluador;
    }

    public void setCodigoEvaluador(String codigoEvaluador) {
        this.codigoEvaluador = codigoEvaluador;
    }

    public Integer getIdCargoEvaluado() {
        return idCargoEvaluado;
    }

    public void setIdCargoEvaluado(Integer idCargoEvaluado) {
        this.idCargoEvaluado = idCargoEvaluado;
    }

    public String getNombreCargoEvaluado() {
        return nombreCargoEvaluado;
    }

    public void setNombreCargoEvaluado(String nombreCargoEvaluado) {
        this.nombreCargoEvaluado = nombreCargoEvaluado;
    }

    public Integer getIdCargoEvaluador() {
        return idCargoEvaluador;
    }

    public void setIdCargoEvaluador(Integer idCargoEvaluador) {
        this.idCargoEvaluador = idCargoEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public String getApellidoEvaluado() {
        return apellidoEvaluado;
    }

    public void setApellidoEvaluado(String apellidoEvaluado) {
        this.apellidoEvaluado = apellidoEvaluado;
    }

    public String getUninegEvaluado() {
        return uninegEvaluado;
    }

    public void setUninegEvaluado(String uninegEvaluado) {
        this.uninegEvaluado = uninegEvaluado;
    }

    public String getNombreUniNegEvaluado() {
        return nombreUniNegEvaluado;
    }

    public void setNombreUniNegEvaluado(String nombreUniNegEvaluado) {
        this.nombreUniNegEvaluado = nombreUniNegEvaluado;
    }

    public Integer getIdgrupoOcuEvaluado() {
        return idgrupoOcuEvaluado;
    }

    public void setIdgrupoOcuEvaluado(Integer idgrupoOcuEvaluado) {
        this.idgrupoOcuEvaluado = idgrupoOcuEvaluado;
    }

    public String getNombregrupoEvaluado() {
        return nombregrupoEvaluado;
    }

    public void setNombregrupoEvaluado(String nombregrupoEvaluado) {
        this.nombregrupoEvaluado = nombregrupoEvaluado;
    }

    public Integer getCantPregEspecificas() {
        return cantPregEspecificas;
    }

    public void setCantPregEspecificas(Integer cantPregEspecificas) {
        this.cantPregEspecificas = cantPregEspecificas;
    }

    public Integer getCantPreCardinales() {
        return cantPreCardinales;
    }

    public void setCantPreCardinales(Integer cantPreCardinales) {
        this.cantPreCardinales = cantPreCardinales;
    }

    public String getCodperevaluado() {
        return codperevaluado;
    }

    public void setCodperevaluado(String codperevaluado) {
        this.codperevaluado = codperevaluado;
    }

    public boolean isFlgcargoprincipal() {
        return flgcargoprincipal;
    }

    public void setFlgcargoprincipal(boolean flgcargoprincipal) {
        this.flgcargoprincipal = flgcargoprincipal;
    }

    
}
