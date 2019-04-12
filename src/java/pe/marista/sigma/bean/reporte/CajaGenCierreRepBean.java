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
public class CajaGenCierreRepBean implements Serializable {

    private String nombreUniNeg;
    private String ruc;
    private String usuario;
    private Integer idCaja;
    private String fechaInicio;
    private String fechaFin;

    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaDetalleCajaGenCierre;
    
    //totales GENERAL
      private String montoSoles;
      private String montoDolares;
      private String montoVisa;
      private String montoMC;
      private String montoDepositadoSol;
      private String montoPorDepositarSol;
      private String montoDepositadoDol;
      private String montoPorDepositarDol;
      private String montoDiferenciaSol;      
      private String montoDiferenciaDol; 
      
      //numeros de cja
      
      private Integer totCajaAperturadas;
      private Integer totCajaCerradas;
      private Integer totCajaPorCerrar;
      private Integer totCajaFecDeposito;
      private Integer totCajaPorDepositar;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<CajaGenCierreRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaDetalleCajaGenCierre() {
        return listaDetalleCajaGenCierre;
    }

    public void setListaDetalleCajaGenCierre(List<DetCajaGenCierreRepBean> listaDetalleCajaGenCierre) {
        this.listaDetalleCajaGenCierre = new JRBeanCollectionDataSource(listaDetalleCajaGenCierre);
    }

    public String getMontoSoles() {
        return montoSoles;
    }

    public void setMontoSoles(String montoSoles) {
        this.montoSoles = montoSoles;
    }

    public String getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(String montoDolares) {
        this.montoDolares = montoDolares;
    }

    public String getMontoVisa() {
        return montoVisa;
    }

    public void setMontoVisa(String montoVisa) {
        this.montoVisa = montoVisa;
    }

    public String getMontoMC() {
        return montoMC;
    }

    public void setMontoMC(String montoMC) {
        this.montoMC = montoMC;
    }

    public String getMontoDepositadoSol() {
        return montoDepositadoSol;
    }

    public void setMontoDepositadoSol(String montoDepositadoSol) {
        this.montoDepositadoSol = montoDepositadoSol;
    }

    public String getMontoPorDepositarSol() {
        return montoPorDepositarSol;
    }

    public void setMontoPorDepositarSol(String montoPorDepositarSol) {
        this.montoPorDepositarSol = montoPorDepositarSol;
    }

    public String getMontoDepositadoDol() {
        return montoDepositadoDol;
    }

    public void setMontoDepositadoDol(String montoDepositadoDol) {
        this.montoDepositadoDol = montoDepositadoDol;
    }

    public String getMontoPorDepositarDol() {
        return montoPorDepositarDol;
    }

    public void setMontoPorDepositarDol(String montoPorDepositarDol) {
        this.montoPorDepositarDol = montoPorDepositarDol;
    }

    public String getMontoDiferenciaSol() {
        return montoDiferenciaSol;
    }

    public void setMontoDiferenciaSol(String montoDiferenciaSol) {
        this.montoDiferenciaSol = montoDiferenciaSol;
    }

    public String getMontoDiferenciaDol() {
        return montoDiferenciaDol;
    }

    public void setMontoDiferenciaDol(String montoDiferenciaDol) {
        this.montoDiferenciaDol = montoDiferenciaDol;
    }

    public Integer getTotCajaAperturadas() {
        return totCajaAperturadas;
    }

    public void setTotCajaAperturadas(Integer totCajaAperturadas) {
        this.totCajaAperturadas = totCajaAperturadas;
    }

    public Integer getTotCajaCerradas() {
        return totCajaCerradas;
    }

    public void setTotCajaCerradas(Integer totCajaCerradas) {
        this.totCajaCerradas = totCajaCerradas;
    }

    public Integer getTotCajaPorCerrar() {
        return totCajaPorCerrar;
    }

    public void setTotCajaPorCerrar(Integer totCajaPorCerrar) {
        this.totCajaPorCerrar = totCajaPorCerrar;
    }

    public Integer getTotCajaFecDeposito() {
        return totCajaFecDeposito;
    }

    public void setTotCajaFecDeposito(Integer totCajaFecDeposito) {
        this.totCajaFecDeposito = totCajaFecDeposito;
    }

    public Integer getTotCajaPorDepositar() {
        return totCajaPorDepositar;
    }

    public void setTotCajaPorDepositar(Integer totCajaPorDepositar) {
        this.totCajaPorDepositar = totCajaPorDepositar;
    }

}
