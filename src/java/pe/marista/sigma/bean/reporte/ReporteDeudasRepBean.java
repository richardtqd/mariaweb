/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author JC
 */
public class ReporteDeudasRepBean implements Serializable {

    private String nombreUniNeg;
    private String ruc;
    private String titulo;
    private String idestudiante;
    private Integer anio;
    private BigDecimal deuda;
    private String grado;
    private String mes;
    private BigDecimal monto;
    private BigDecimal mora;
    private BigDecimal montoTotal;
    private BigDecimal SubMonto;
    private BigDecimal montoMora;
    private String nombreFull;
    private String codEstudiante;
    private String concepto;
    private String fecha;
    private String uniNeg;

    //CAMPOS DE AYUDA
    private String estadoest;
    private BigDecimal Matricula;
    private BigDecimal Marzo;
    private BigDecimal Abril;
    private BigDecimal Mayo;
    private BigDecimal Junio;
    private BigDecimal Julio;
    private BigDecimal Agosto;
    private BigDecimal Setiembre;
    private BigDecimal Octubre;
    private BigDecimal Noviembre;
    private BigDecimal Diciembre;

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getMontoMora() {
        return montoMora;
    }

    public void setMontoMora(BigDecimal montoMora) {
        this.montoMora = montoMora;
    }

    public BigDecimal getSubMonto() {
        return SubMonto;
    }

    public void setSubMonto(BigDecimal SubMonto) {
        this.SubMonto = SubMonto;
    }

    public String getNombreFull() {
        return nombreFull;
    }

    public void setNombreFull(String nombreFull) {
        this.nombreFull = nombreFull;
    }

    public String getCodEstudiante() {
        return codEstudiante;
    }

    public void setCodEstudiante(String codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    //CAMPOS DE AYUDA
    public BigDecimal getMatricula() {
        return Matricula;
    }

    public void setMatricula(BigDecimal Matricula) {
        this.Matricula = Matricula;
    }

    public BigDecimal getMarzo() {
        return Marzo;
    }

    public void setMarzo(BigDecimal Marzo) {
        this.Marzo = Marzo;
    }

    public BigDecimal getAbril() {
        return Abril;
    }

    public void setAbril(BigDecimal Abril) {
        this.Abril = Abril;
    }

    public BigDecimal getMayo() {
        return Mayo;
    }

    public void setMayo(BigDecimal Mayo) {
        this.Mayo = Mayo;
    }

    public BigDecimal getJunio() {
        return Junio;
    }

    public void setJunio(BigDecimal Junio) {
        this.Junio = Junio;
    }

    public BigDecimal getJulio() {
        return Julio;
    }

    public void setJulio(BigDecimal Julio) {
        this.Julio = Julio;
    }

    public BigDecimal getAgosto() {
        return Agosto;
    }

    public void setAgosto(BigDecimal Agosto) {
        this.Agosto = Agosto;
    }

    public BigDecimal getSetiembre() {
        return Setiembre;
    }

    public void setSetiembre(BigDecimal Setiembre) {
        this.Setiembre = Setiembre;
    }

    public BigDecimal getOctubre() {
        return Octubre;
    }

    public void setOctubre(BigDecimal Octubre) {
        this.Octubre = Octubre;
    }

    public BigDecimal getNoviembre() {
        return Noviembre;
    }

    public void setNoviembre(BigDecimal Noviembre) {
        this.Noviembre = Noviembre;
    }

    public BigDecimal getDiciembre() {
        return Diciembre;
    }

    public void setDiciembre(BigDecimal Diciembre) {
        this.Diciembre = Diciembre;
    }

    public String getEstadoest() {
        return estadoest;
    }

    public void setEstadoest(String estadoest) {
        this.estadoest = estadoest;
    }

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
