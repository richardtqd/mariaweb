/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS001
 */
public class PagosEmitidosRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String fecha;
    private String numero;
    private String nomRespCheque;
    private String montoPagadoSol;
    private String montoPagadoDol;
    private String glosa;
    private String modoPago;
    private String montoChequeSol;
    private String montoChequeDol;
    private String montoTransSol;
    private String montoTransDol;
    private String montoSol;
    private String montoDol;
    private String detraccionSol;
    private String detraccionDol;
    private String montoTotSol;
    private String montoTotDol;
    private String cantAnulados;
    private String textoFiltro;

    private Date fechaInicio;
    private Date fechaFin;
    private String uniNeg;
    private Integer idTipoConcepto;
    private Integer idConcepto;
    private String nomTipoConcepto;// 
    private String nomConcepto;// 
    private Boolean flgAnulados;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucUniNeg() {
        return rucUniNeg;
    }

    public void setRucUniNeg(String rucUniNeg) {
        this.rucUniNeg = rucUniNeg;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomRespCheque() {
        return nomRespCheque;
    }

    public void setNomRespCheque(String nomRespCheque) {
        this.nomRespCheque = nomRespCheque;
    }

    public String getMontoPagadoSol() {
        return montoPagadoSol;
    }

    public void setMontoPagadoSol(String montoPagadoSol) {
        this.montoPagadoSol = montoPagadoSol;
    }

    public String getMontoPagadoDol() {
        return montoPagadoDol;
    }

    public void setMontoPagadoDol(String montoPagadoDol) {
        this.montoPagadoDol = montoPagadoDol;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
    }

    public String getMontoChequeSol() {
        return montoChequeSol;
    }

    public void setMontoChequeSol(String montoChequeSol) {
        this.montoChequeSol = montoChequeSol;
    }

    public String getMontoChequeDol() {
        return montoChequeDol;
    }

    public void setMontoChequeDol(String montoChequeDol) {
        this.montoChequeDol = montoChequeDol;
    }

    public String getMontoTransSol() {
        return montoTransSol;
    }

    public void setMontoTransSol(String montoTransSol) {
        this.montoTransSol = montoTransSol;
    }

    public String getMontoTransDol() {
        return montoTransDol;
    }

    public void setMontoTransDol(String montoTransDol) {
        this.montoTransDol = montoTransDol;
    }

    public String getMontoSol() {
        return montoSol;
    }

    public void setMontoSol(String montoSol) {
        this.montoSol = montoSol;
    }

    public String getMontoDol() {
        return montoDol;
    }

    public void setMontoDol(String montoDol) {
        this.montoDol = montoDol;
    }

    public String getDetraccionSol() {
        return detraccionSol;
    }

    public void setDetraccionSol(String detraccionSol) {
        this.detraccionSol = detraccionSol;
    }

    public String getDetraccionDol() {
        return detraccionDol;
    }

    public void setDetraccionDol(String detraccionDol) {
        this.detraccionDol = detraccionDol;
    }

    public String getMontoTotSol() {
        return montoTotSol;
    }

    public void setMontoTotSol(String montoTotSol) {
        this.montoTotSol = montoTotSol;
    }

    public String getMontoTotDol() {
        return montoTotDol;
    }

    public void setMontoTotDol(String montoTotDol) {
        this.montoTotDol = montoTotDol;
    }

    public String getCantAnulados() {
        return cantAnulados;
    }

    public void setCantAnulados(String cantAnulados) {
        this.cantAnulados = cantAnulados;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNomTipoConcepto() {
        return nomTipoConcepto;
    }

    public void setNomTipoConcepto(String nomTipoConcepto) {
        this.nomTipoConcepto = nomTipoConcepto;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public Boolean getFlgAnulados() {
        return flgAnulados;
    }

    public void setFlgAnulados(Boolean flgAnulados) {
        this.flgAnulados = flgAnulados;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

}
