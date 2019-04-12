/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class IndicadorBean implements Serializable {

    private Integer idIndicador;
    private String nombre;
    private String codigo;
    private CodigoBean codigoTiPoIndicador; // idTipoIndicador - idCodigo
    private String formula;
    private CodigoBean codigoTiPoUso; //idTipoUsoIndicador - idCodigo
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private String idTipoIndicador; // comodin
    private String idTipoUsoIndicador; //comodin
    private CodigoBean codigoTipoValor; //idTipoValor - idCodigo
    private Integer meta;
    
    public Integer getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CodigoBean getCodigoTiPoIndicador() {
        if(codigoTiPoIndicador == null){
            codigoTiPoIndicador = new CodigoBean();
        }
        return codigoTiPoIndicador;
    }

    public void setCodigoTiPoIndicador(CodigoBean codigoTiPoIndicador) {
        this.codigoTiPoIndicador = codigoTiPoIndicador;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public CodigoBean getCodigoTiPoUso() {
        if(codigoTiPoUso == null){
            codigoTiPoUso = new CodigoBean();
        }
        return codigoTiPoUso;
    }

    public void setCodigoTiPoUso(CodigoBean codigoTiPoUso) {
        this.codigoTiPoUso = codigoTiPoUso;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getFechaCrea() {
        return creaFecha;
    }

    public void setFechaCrea(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getIdTipoIndicador() {
        return idTipoIndicador;
    }

    public void setIdTipoIndicador(String idTipoIndicador) {
        this.idTipoIndicador = idTipoIndicador;
    }

    public String getIdTipoUsoIndicador() {
        return idTipoUsoIndicador;
    }

    public void setIdTipoUsoIndicador(String idTipoUsoIndicador) {
        this.idTipoUsoIndicador = idTipoUsoIndicador;
    }

    public CodigoBean getCodigoTipoValor() {
        if(codigoTipoValor == null){
            codigoTipoValor = new CodigoBean();
        }
        return codigoTipoValor;
    }

    public void setCodigoTipoValor(CodigoBean codigoTipoValor) {
        this.codigoTipoValor = codigoTipoValor;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }
     
}
