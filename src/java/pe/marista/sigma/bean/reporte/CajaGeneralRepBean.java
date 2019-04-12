/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class CajaGeneralRepBean implements Serializable {

    private String nombreUniNeg;
    private String nomCaja;
    private String nomCompletoPersonal;
    private String usuario;
    private String nomCompletoSupervisa;
    private String fecApertura;
    private String fecCierre;
    private String aperturaSol;
    private String aperturaDol;
    private String ingresoSol;
    private String ingresoDol;
    private String ingresoPos1;
    private String ingresoPos2;
    private String montoBanco;
    private String sumTotSoles;
    private String sumTotDolares;
    private String tc;
    private Integer mora;
    private String txtFiltro;

    //AYUDA
    private String fechaIniVista;
    private String fechaFinVista;

    private JRBeanCollectionDataSource listaCuentas;
//    private JRBeanCollectionDataSource listaDetalle;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getNomCaja() {
        return nomCaja;
    }

    public void setNomCaja(String nomCaja) {
        this.nomCaja = nomCaja;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(String fecApertura) {
        this.fecApertura = fecApertura;
    }

    public String getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(String fecCierre) {
        this.fecCierre = fecCierre;
    }

    public String getAperturaSol() {
        return aperturaSol;
    }

    public void setAperturaSol(String aperturaSol) {
        this.aperturaSol = aperturaSol;
    }

    public String getAperturaDol() {
        return aperturaDol;
    }

    public void setAperturaDol(String aperturaDol) {
        this.aperturaDol = aperturaDol;
    }

    public String getIngresoSol() {
        return ingresoSol;
    }

    public void setIngresoSol(String ingresoSol) {
        this.ingresoSol = ingresoSol;
    }

    public String getIngresoDol() {
        return ingresoDol;
    }

    public void setIngresoDol(String ingresoDol) {
        this.ingresoDol = ingresoDol;
    }

    public String getMontoBanco() {
        return montoBanco;
    }

    public void setMontoBanco(String montoBanco) {
        this.montoBanco = montoBanco;
    }

    public String getSumTotSoles() {
        return sumTotSoles;
    }

    public void setSumTotSoles(String sumTotSoles) {
        this.sumTotSoles = sumTotSoles;
    }

    public String getSumTotDolares() {
        return sumTotDolares;
    }

    public void setSumTotDolares(String sumTotDolares) {
        this.sumTotDolares = sumTotDolares;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public Integer getMora() {
        return mora;
    }

    public void setMora(Integer mora) {
        this.mora = mora;
    }
//
//    public JRBeanCollectionDataSource getListaDetalle() {
//        return listaDetalle;
//    }
//
//    public void setListaDetalle(List<CajaGeneralCtaRepBean> listaDetalle) {
//        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
//    }

    public String getNomCompletoPersonal() {
        return nomCompletoPersonal;
    }

    public void setNomCompletoPersonal(String nomCompletoPersonal) {
        this.nomCompletoPersonal = nomCompletoPersonal;
    }

    public String getNomCompletoSupervisa() {
        return nomCompletoSupervisa;
    }

    public void setNomCompletoSupervisa(String nomCompletoSupervisa) {
        this.nomCompletoSupervisa = nomCompletoSupervisa;
    }

    public String getIngresoPos1() {
        return ingresoPos1;
    }

    public void setIngresoPos1(String ingresoPos1) {
        this.ingresoPos1 = ingresoPos1;
    }

    public String getIngresoPos2() {
        return ingresoPos2;
    }

    public void setIngresoPos2(String ingresoPos2) {
        this.ingresoPos2 = ingresoPos2;
    }

    public String getTxtFiltro() {
        return txtFiltro;
    }

    public void setTxtFiltro(String txtFiltro) {
        this.txtFiltro = txtFiltro;
    }

    public JRBeanCollectionDataSource getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<CajaGeneralCtasRepBean> listaCuentas) {
        this.listaCuentas = new JRBeanCollectionDataSource(listaCuentas);
    }

    public String getFechaIniVista() {
        return fechaIniVista;
    }

    public void setFechaIniVista(String fechaIniVista) {
        this.fechaIniVista = fechaIniVista;
    }

    public String getFechaFinVista() {
        return fechaFinVista;
    }

    public void setFechaFinVista(String fechaFinVista) {
        this.fechaFinVista = fechaFinVista;
    }

}
