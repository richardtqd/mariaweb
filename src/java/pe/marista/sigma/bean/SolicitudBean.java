/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class SolicitudBean implements Serializable
{
    private String tipoCategoria;
    private String cuentaContable;
    private String concepto;
    private String centroCosto;
    private String moneda;
    Double importe;
    private Integer codSolicitud;
    private Date fecha;
    private Date fechaVen;
    private String estado;
    private String acreedor;
    private String proveedor;
    private String ruc;
    private String modalidadPago;
    
    

    public SolicitudBean(String tipoCategoria, String cuentaContable, String concepto, String centroCosto, String moneda, Double importe) {
        this.tipoCategoria = tipoCategoria;
        this.cuentaContable = cuentaContable;
        this.concepto = concepto;
        this.centroCosto = centroCosto;
        this.moneda = moneda;
        this.importe = importe;
    }

    public SolicitudBean(String modalidadPago,String tipoCategoria, String cuentaContable, String concepto, String centroCosto, String moneda, Double importe, Integer codSolicitud, Date fecha, String proveedor,String acreedor,Date fechaVen,String estado, String ruc) {
        this.modalidadPago=modalidadPago;
        this.tipoCategoria = tipoCategoria;
        this.cuentaContable = cuentaContable;
        this.concepto = concepto;
        this.centroCosto = centroCosto;
        this.moneda = moneda;
        this.importe = importe;
        this.codSolicitud = codSolicitud;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.acreedor=acreedor;
        this.fechaVen=fechaVen;
        this.estado=estado;
        this.ruc=ruc;
    }

    

    
    public Integer getCodSolicitud() {
        return codSolicitud;
    }

    public void setCodSolicitud(Integer codSolicitud) {
        this.codSolicitud = codSolicitud;
    }
    
    public String getTipoSolicitud() {
        return tipoCategoria;
    }

    public void setTipoSolicitud(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getCuentaContable() {
        return cuentaContable;
    }

    public void setCuentaContable(String cuentaContable) {
        this.cuentaContable = cuentaContable;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCentroCosto() {
        return centroCosto;
    }

    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(String acreedor) {
        this.acreedor = acreedor;
    }

    public Date getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(Date fechaVen) {
        this.fechaVen = fechaVen;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public SolicitudBean() {
    }

    public String getModalidadPago() {
        return modalidadPago;
    }

    public void setModalidadPago(String modalidadPago) {
        this.modalidadPago = modalidadPago;
    }
    
    
}
