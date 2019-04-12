/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class RecEnvBean implements Serializable {

    private Integer idRecEnv;
    private String uniNeg;
    private Date fechaProceso;
    private Integer idDocIngreso;
    private String codigoCliente;
    private String nombreAlumno;
    private String codigoCuota;
    private String importe;
    private String mora;
    private String fechaPago;
    private String filler;
    private String formaPago;
    private String oficinaPago;
    private String oficinaAbono;
    private String nroTerminal;
    private String ctaAbono;
    private String moneda;
    private String fechaVenc;
    private String codReferencial;
    private String tipoServicio;

    //Ayuda
    private String dato;
    
    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getCodigoCuota() {
        return codigoCuota;
    }

    public void setCodigoCuota(String codigoCuota) {
        this.codigoCuota = codigoCuota;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getOficinaPago() {
        return oficinaPago;
    }

    public void setOficinaPago(String oficinaPago) {
        this.oficinaPago = oficinaPago;
    }

    public String getCtaAbono() {
        return ctaAbono;
    }

    public void setCtaAbono(String ctaAbono) {
        this.ctaAbono = ctaAbono;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(String fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public String getCodReferencial() {
        return codReferencial;
    }

    public void setCodReferencial(String codReferencial) {
        this.codReferencial = codReferencial;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getNroTerminal() {
        return nroTerminal;
    }

    public void setNroTerminal(String nroTerminal) {
        this.nroTerminal = nroTerminal;
    }

    public String getOficinaAbono() {
        return oficinaAbono;
    }

    public void setOficinaAbono(String oficinaAbono) {
        this.oficinaAbono = oficinaAbono;
    }

    public Integer getIdRecEnv() {
        return idRecEnv;
    }

    public void setIdRecEnv(Integer idRecEnv) {
        this.idRecEnv = idRecEnv;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
 
}
