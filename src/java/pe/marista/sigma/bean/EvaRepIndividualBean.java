/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class EvaRepIndividualBean implements Serializable{
    
    private String unineg;
    private String nombreunineg;
    private String grupoOcupacional;
    private String tipoPlanilla;
    private String codper;
    private String nombre;
    //private String cargo;
    private String tipo;
    private String nombre_competencia;
    private String preguntas;
    private String promedio;
    private String autoevaluacion;
    private JRBeanCollectionDataSource listaCargos;    
    private JRBeanCollectionDataSource listaResumen;
    /*private String promedio_tab;
    private String ponderacion_tab;*/
    
    
    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getGrupoOcupacional() {
        return grupoOcupacional;
    }

    public void setGrupoOcupacional(String grupoOcupacional) {
        this.grupoOcupacional = grupoOcupacional;
    }

    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }


    public String getCodper() {
        return codper;
    }

    public void setCodper(String codper) {
        this.codper = codper;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre_competencia() {
        return nombre_competencia;
    }

    public void setNombre_competencia(String nombre_competencia) {
        this.nombre_competencia = nombre_competencia;
    }


    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }

    public String getPromedio() {
        return promedio;
    }

    public void setPromedio(String promedio) {
        this.promedio = promedio;
    }


    public String getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(String autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }

    public JRBeanCollectionDataSource getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargos> listaCargos) {
        this.listaCargos = new JRBeanCollectionDataSource(listaCargos);
    }
    
    public JRBeanCollectionDataSource getListaResumen() {
        return listaResumen;
    }

    public void setListaResumen(List<ResumenEvaDesempeno> listaResumen) {
        this.listaResumen = new JRBeanCollectionDataSource(listaResumen);
    }

    public String getNombreunineg() {
        return nombreunineg;
    }

    public void setNombreunineg(String nombreunineg) {
        this.nombreunineg = nombreunineg;
    }

    
}
