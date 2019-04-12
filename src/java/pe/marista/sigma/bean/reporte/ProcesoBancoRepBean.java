/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

/**
 *
 * @author JC
 */
public class ProcesoBancoRepBean implements Serializable {

    private String unineg;
    private String codDiscente;
    private String nombreDiscente;
    private String fechaPago;
    private String lugarPago;
    private BigDecimal montoEfectivoSol;
    private BigDecimal totSoles;
    private GregorianCalendar fechaImp;
    private String grado;
    private String concepto;

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public String getCodDiscente() {
        return codDiscente;
    }

    public void setCodDiscente(String codDiscente) {
        this.codDiscente = codDiscente;
    }

    public String getNombreDiscente() {
        return nombreDiscente;
    }

    public void setNombreDiscente(String nombreDiscente) {
        this.nombreDiscente = nombreDiscente;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getLugarPago() {
        return lugarPago;
    }

    public void setLugarPago(String lugarPago) {
        this.lugarPago = lugarPago;
    }

    public BigDecimal getMontoEfectivoSol() {
        return montoEfectivoSol;
    }

    public void setMontoEfectivoSol(BigDecimal montoEfectivoSol) {
        this.montoEfectivoSol = montoEfectivoSol;
    }

    public BigDecimal getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(BigDecimal totSoles) {
        this.totSoles = totSoles;
    }

    public GregorianCalendar getFechaImp() {
        return fechaImp;
    }

    public void setFechaImp(GregorianCalendar fechaImp) {
        this.fechaImp = fechaImp;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

}
