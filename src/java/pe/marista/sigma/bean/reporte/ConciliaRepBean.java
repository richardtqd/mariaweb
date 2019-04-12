/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConciliaRepBean implements Serializable {

    private String uniNeg;
    private Integer idProcesoBanco;
    private BigDecimal totalRecuperado;
    private BigDecimal totalProcesado;
    private BigDecimal sumRecuperado;
    private BigDecimal sumProcesado;
    private Integer anio;
    private String rucBanco;
    private String nomBanco;
    private Date fechaOp;
    private String fechaOpVista;
 
    //Para Conculta
    private Integer flgProceso;
    private Date fecIni;
    private Date fecFin;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public BigDecimal getTotalRecuperado() {
        return totalRecuperado;
    }

    public void setTotalRecuperado(BigDecimal totalRecuperado) {
        this.totalRecuperado = totalRecuperado;
    }

    public BigDecimal getTotalProcesado() {
        return totalProcesado;
    }

    public void setTotalProcesado(BigDecimal totalProcesado) {
        this.totalProcesado = totalProcesado;
    }

    public BigDecimal getSumRecuperado() {
        return sumRecuperado;
    }

    public void setSumRecuperado(BigDecimal sumRecuperado) {
        this.sumRecuperado = sumRecuperado;
    }

    public BigDecimal getSumProcesado() {
        return sumProcesado;
    }

    public void setSumProcesado(BigDecimal sumProcesado) {
        this.sumProcesado = sumProcesado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getRucBanco() {
        return rucBanco;
    }

    public void setRucBanco(String rucBanco) {
        this.rucBanco = rucBanco;
    }

    public String getNomBanco() {
        return nomBanco;
    }

    public void setNomBanco(String nomBanco) {
        this.nomBanco = nomBanco;
    }

    public Date getFechaOp() {
        return fechaOp;
    }

    public void setFechaOp(Date fechaOp) {
        this.fechaOp = fechaOp;
    }

    public String getFechaOpVista() {
        return fechaOpVista;
    }

    public void setFechaOpVista(String fechaOpVista) {
        this.fechaOpVista = fechaOpVista;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public Integer getFlgProceso() {
        return flgProceso;
    }

    public void setFlgProceso(Integer flgProceso) {
        this.flgProceso = flgProceso;
    }

}
