/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class FichaRetroalimentacionBean {

    private String unineg;
    private String nombreunineg;
    private String codper;
    private String nombre;
    private String nivel;
    private String tipo_competencia;
    private String competencia;
    private Float promedio;
    private Float autoevaluacion;
    private Float prom_ponderado;
    private String peso;
    private JRBeanCollectionDataSource listaCargos;
    private Float promedio_tab;
    private Float ponderacion_tab;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTipo_competencia() {
        return tipo_competencia;
    }

    public void setTipo_competencia(String tipo_competencia) {
        this.tipo_competencia = tipo_competencia;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Float getPromedio() {
        return promedio;
    }

    public void setPromedio(Float promedio) {
        this.promedio = promedio;
    }

    public Float getAutoevaluacion() {
        return autoevaluacion;
    }

    public void setAutoevaluacion(Float autoevaluacion) {
        this.autoevaluacion = autoevaluacion;
    }

    public Float getProm_ponderado() {
        return prom_ponderado;
    }

    public void setProm_ponderado(Float prom_ponderado) {
        this.prom_ponderado = prom_ponderado;
    }

    public JRBeanCollectionDataSource getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<Cargos> listaCargos) {
        this.listaCargos = new JRBeanCollectionDataSource(listaCargos);
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getNombreunineg() {
        return nombreunineg;
    }

    public void setNombreunineg(String nombreunineg) {
        this.nombreunineg = nombreunineg;
    }

    public Float getPromedio_tab() {
        return promedio_tab;
    }

    public void setPromedio_tab(Float promedio_tab) {
        this.promedio_tab = promedio_tab;
    }

    public Float getPonderacion_tab() {
        return ponderacion_tab;
    }

    public void setPonderacion_tab(Float ponderacion_tab) {
        this.ponderacion_tab = ponderacion_tab;
    }

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getCodper() {
        return codper;
    }

    public void setCodper(String codper) {
        this.codper = codper;
    }

}
