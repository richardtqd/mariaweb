/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class DetDetDocEgresoRepBean {

    private Integer idSolRC;//   
    private String concepto; //
    private String nombreCR; //
    private String glosa; //
    private String nomEnt; //
    private String nombreBco; //
    private String montoPagado; //
    private Double montoTotal;// 
    private String cuentaD;//
    private String ctabco;//
    private JRBeanCollectionDataSource listaDetDetDocEgresoRepBean;

    private String detraccion;//    
    private String nroNotaCredito;
    private String dsctoNotCred;
    
    public Integer getIdSolRC() {
        return idSolRC;
    }

    public void setIdSolRC(Integer idSolRC) {
        this.idSolRC = idSolRC;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    } 

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(String cuentaD) {
        this.cuentaD = cuentaD;
    }

    public String getNomEnt() {
        return nomEnt;
    }

    public void setNomEnt(String nomEnt) {
        this.nomEnt = nomEnt;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getNombreBco() {
        return nombreBco;
    }

    public void setNombreBco(String nombreBco) {
        this.nombreBco = nombreBco;
    }

    public String getNombreCR() {
        return nombreCR;
    }

    public void setNombreCR(String nombreCR) {
        this.nombreCR = nombreCR;
    }

    public String getCtabco() {
        return ctabco;
    }

    public void setCtabco(String ctabco) {
        this.ctabco = ctabco;
    }

    public JRBeanCollectionDataSource getListaDetDetDocEgresoRepBean() {
        return listaDetDetDocEgresoRepBean;
    }

    public void setListaDetDetDocEgresoRepBean(List<DetDetDocEgresoRepBean> listaDetDetDocEgresoRepBean) {
        this.listaDetDetDocEgresoRepBean = new JRBeanCollectionDataSource(listaDetDetDocEgresoRepBean);
    }

    public String getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getDetraccion() {
        return detraccion;
    }

    public void setDetraccion(String detraccion) {
        this.detraccion = detraccion;
    }
   
    public String getNroNotaCredito() {
        return nroNotaCredito;
    }

    public void setNroNotaCredito(String nroNotaCredito) {
        this.nroNotaCredito = nroNotaCredito;
    }

    public String getDsctoNotCred() {
        return dsctoNotCred;
    }

    public void setDsctoNotCred(String dsctoNotCred) {
        this.dsctoNotCred = dsctoNotCred;
    }
}
