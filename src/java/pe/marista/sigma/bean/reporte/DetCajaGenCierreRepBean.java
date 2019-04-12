/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class DetCajaGenCierreRepBean implements Serializable {
    
      private String nombreCaja;
      private String nombrePersonal;
      private String fecApertura;
      private String fecCierre;
      private String ingresoSol;
      private String ingresoDol;
      private String ingresoPos1;
      private String ingresoPos2;
      private String fecDeposito;
      
      private String numOperacionSol;
      private String numOperacionDol;
      private String montoDepositoSol;
      private String montoDepositoDol;
      private String diferenciaSol;
      private String diferenciaDol;      
      
      //totales
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
      

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
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
    
    public String getFecDeposito() {
        return fecDeposito;
    }

    public void setFecDeposito(String fecDeposito) {
        this.fecDeposito = fecDeposito;
    }

    public String getNumOperacionSol() {
        return numOperacionSol;
    }

    public void setNumOperacionSol(String numOperacionSol) {
        this.numOperacionSol = numOperacionSol;
    }

    public String getNumOperacionDol() {
        return numOperacionDol;
    }

    public void setNumOperacionDol(String numOperacionDol) {
        this.numOperacionDol = numOperacionDol;
    }

    public String getMontoDepositoSol() {
        return montoDepositoSol;
    }

    public void setMontoDepositoSol(String montoDepositoSol) {
        this.montoDepositoSol = montoDepositoSol;
    }

    public String getMontoDepositoDol() {
        return montoDepositoDol;
    }

    public void setMontoDepositoDol(String montoDepositoDol) {
        this.montoDepositoDol = montoDepositoDol;
    }

    public String getDiferenciaSol() {
        return diferenciaSol;
    }

    public void setDiferenciaSol(String diferenciaSol) {
        this.diferenciaSol = diferenciaSol;
    }

    public String getDiferenciaDol() {
        return diferenciaDol;
    }

    public void setDiferenciaDol(String diferenciaDol) {
        this.diferenciaDol = diferenciaDol;
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
      
}
