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
public class DetDocEgresoRepBean {

    private Integer idSolRC;//   
    private String concepto; //
    private String nombreCR; //
    private String glosa; //
    private String nomEnt; //
    private String nombreBco; //
    private Double montoPagado; //
    private Double montoTotal;// 
    private Double montoPagadoTot;// 
    private Double montoPagadoTotCta;// 
    private String montoPagadoTotConDet;// 
    private String cuentaD;//
    private String ctabco;//
    private String nombreCtaBco;//
    private JRBeanCollectionDataSource listaDetDetDocEgresoRepBean;
    private Integer detra;//   
    private Integer gara;//   
    private Integer countDetra;//   
    private Integer nroLista;//    
    private String idSoli;

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

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
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

    public Double getMontoPagadoTot() {
        return montoPagadoTot;
    }

    public void setMontoPagadoTot(Double montoPagadoTot) {
        this.montoPagadoTot = montoPagadoTot;
    }

    public Integer getDetra() {
        return detra;
    }

    public void setDetra(Integer detra) {
        this.detra = detra;
    }

    public String getNombreCtaBco() {
        return nombreCtaBco;
    }

    public void setNombreCtaBco(String nombreCtaBco) {
        this.nombreCtaBco = nombreCtaBco;
    }

    public Double getMontoPagadoTotCta() {
        return montoPagadoTotCta;
    }

    public void setMontoPagadoTotCta(Double montoPagadoTotCta) {
        this.montoPagadoTotCta = montoPagadoTotCta;
    }

    public String getMontoPagadoTotConDet() {
        return montoPagadoTotConDet;
    }

    public void setMontoPagadoTotConDet(String montoPagadoTotConDet) {
        this.montoPagadoTotConDet = montoPagadoTotConDet;
    }

    public Integer getCountDetra() {
        return countDetra;
    }

    public void setCountDetra(Integer countDetra) {
        this.countDetra = countDetra;
    }

    public Integer getNroLista() {
        return nroLista;
    }

    public void setNroLista(Integer nroLista) {
        this.nroLista = nroLista;
    }

    public Integer getGara() {
        return gara;
    }

    public void setGara(Integer gara) {
        this.gara = gara;
    }  
    
    public String getIdSoli() {
        return idSoli;
    }

    public void setIdSoli(String idSoli) {
        this.idSoli = idSoli;
    }
}
