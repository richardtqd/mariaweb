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
public class NotaAbonoRepBean implements Serializable {

    private String uniNeg;

    private BigDecimal montoFebrero;
    private BigDecimal montoMarzo;
    private BigDecimal montoAbril;
    private BigDecimal montoMayo;
    private BigDecimal montoJunio;
    private BigDecimal montoJulio;
    private BigDecimal montoAgosto;
    private BigDecimal montoSetiembre;
    private BigDecimal montoOctubre;
    private BigDecimal montoNoviembre;
    private BigDecimal montoDiciembre;

    private String fecha;
    private String fechaVista;
    private Integer anio;
    private BigDecimal totalMora;
    private BigDecimal subTotal;
    private BigDecimal total;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public BigDecimal getMontoFebrero() {
        return montoFebrero;
    }

    public void setMontoFebrero(BigDecimal montoFebrero) {
        this.montoFebrero = montoFebrero;
    }

    public BigDecimal getMontoMarzo() {
        return montoMarzo;
    }

    public void setMontoMarzo(BigDecimal montoMarzo) {
        this.montoMarzo = montoMarzo;
    }

    public BigDecimal getMontoAbril() {
        return montoAbril;
    }

    public void setMontoAbril(BigDecimal montoAbril) {
        this.montoAbril = montoAbril;
    }

    public BigDecimal getMontoMayo() {
        return montoMayo;
    }

    public void setMontoMayo(BigDecimal montoMayo) {
        this.montoMayo = montoMayo;
    }

    public BigDecimal getMontoJunio() {
        return montoJunio;
    }

    public void setMontoJunio(BigDecimal montoJunio) {
        this.montoJunio = montoJunio;
    }

    public BigDecimal getMontoJulio() {
        return montoJulio;
    }

    public void setMontoJulio(BigDecimal montoJulio) {
        this.montoJulio = montoJulio;
    }

    public BigDecimal getMontoAgosto() {
        return montoAgosto;
    }

    public void setMontoAgosto(BigDecimal montoAgosto) {
        this.montoAgosto = montoAgosto;
    }

    public BigDecimal getMontoSetiembre() {
        return montoSetiembre;
    }

    public void setMontoSetiembre(BigDecimal montoSetiembre) {
        this.montoSetiembre = montoSetiembre;
    }

    public BigDecimal getMontoOctubre() {
        return montoOctubre;
    }

    public void setMontoOctubre(BigDecimal montoOctubre) {
        this.montoOctubre = montoOctubre;
    }

    public BigDecimal getMontoNoviembre() {
        return montoNoviembre;
    }

    public void setMontoNoviembre(BigDecimal montoNoviembre) {
        this.montoNoviembre = montoNoviembre;
    }

    public BigDecimal getMontoDiciembre() {
        return montoDiciembre;
    }

    public void setMontoDiciembre(BigDecimal montoDiciembre) {
        this.montoDiciembre = montoDiciembre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotalMora() {
        return totalMora;
    }

    public void setTotalMora(BigDecimal totalMora) {
        this.totalMora = totalMora;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(String fechaVista) {
        this.fechaVista = fechaVista;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

}
