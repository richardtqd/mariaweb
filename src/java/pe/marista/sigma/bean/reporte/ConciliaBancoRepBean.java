/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author JC
 */
public class ConciliaBancoRepBean implements Serializable {

    private String uniNeg;
    private Integer id;
    private Integer idProcesoBanco;
    private Integer mes;
    private String mesVista;
    private Integer anio;
    private String idEstudiante;
    private String codigo;
    private String nombreCompleto;
    private BigDecimal montoPagado;
    private BigDecimal pagado;
    private String fechaPagoVista;
    private BigDecimal montoRecup;
    private BigDecimal moraRecup;
    private String cuota;
    private Integer regEnv;
    private Integer regError;
    private String fechaOpeVista;
    private String nomConcepto;

    /* CAMPOS FILTROS */
    private Date fechIni;
    private Date fechaFin;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getMesVista() {
        return mesVista;
    }

    public void setMesVista(String mesVista) {
        this.mesVista = mesVista;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getPagado() {
        return pagado;
    }

    public void setPagado(BigDecimal pagado) {
        this.pagado = pagado;
    }

    public String getFechaPagoVista() {
        return fechaPagoVista;
    }

    public void setFechaPagoVista(String fechaPagoVista) {
        this.fechaPagoVista = fechaPagoVista;
    }

    public BigDecimal getMontoRecup() {
        return montoRecup;
    }

    public void setMontoRecup(BigDecimal montoRecup) {
        this.montoRecup = montoRecup;
    }

    public Integer getRegEnv() {
        return regEnv;
    }

    public void setRegEnv(Integer regEnv) {
        this.regEnv = regEnv;
    }

    public Integer getRegError() {
        return regError;
    }

    public void setRegError(Integer regError) {
        this.regError = regError;
    }

    public String getFechaOpeVista() {
        return fechaOpeVista;
    }

    public void setFechaOpeVista(String fechaOpeVista) {
        this.fechaOpeVista = fechaOpeVista;
    }

    public Date getFechIni() {
        return fechIni;
    }

    public void setFechIni(Date fechIni) {
        this.fechIni = fechIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public BigDecimal getMoraRecup() {
        return moraRecup;
    }

    public void setMoraRecup(BigDecimal moraRecup) {
        this.moraRecup = moraRecup;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

}
