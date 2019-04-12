/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class TipoCambioBean implements Serializable {

    private Integer idTipoMoneda;
    private Date fechaTc;
    private String fecha;
    private BigDecimal tcVenta;
    private BigDecimal tcCompra;
    private boolean status;
    private String creaPor;
    private Date creaFecha;
    private boolean estado;

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Date getFechaTc() {
        return fechaTc;
    }

    public void setFechaTc(Date fechaTc) {
        this.fechaTc = fechaTc;
    }

    public BigDecimal getTcVenta() {
        return tcVenta;
    }

    public void setTcVenta(BigDecimal tcVenta) {
        this.tcVenta = tcVenta;
    }

    public BigDecimal getTcCompra() {
        return tcCompra;
    }

    public void setTcCompra(BigDecimal tcCompra) {
        this.tcCompra = tcCompra;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean getEstadoActual() {
        return estado;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
