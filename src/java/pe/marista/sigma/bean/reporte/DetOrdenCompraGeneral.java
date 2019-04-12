/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class DetOrdenCompraGeneral {
    
    private Integer idOrdenCompra;
    private String nombreUniNeg;
    private String nombreOrden;
    private String rucOrden;
    private String direccionOrden;
    private Date fechaOrden;
    private String categoria;
    private String tipoMoneda;
    private String item;
    private Integer cantidad;
    private Double importe;
    private Double montoRef;
    private String direccionUnidad;
    private String formaPago;
    private String nombreUnidad;
    private String rucUnidad;
    private String distritoUnidad;
    private String paisUnidad;
    private String telefonoUnidad;
    private String correoUnidad;
    private String webUnidad;
    private String uniNeg;
    private Double sumaImporte;
    private Integer idDetRequerimiento;
    private Integer idRequerimiento;
    private Double montoCadaUnoMate;
    private Double montoCadaUnoSer;
    private Double sumaImporteFinal;
    private Double montoGeneralOrden;
    private String tipoUniMed;

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getNombreOrden() {
        return nombreOrden;
    }

    public void setNombreOrden(String nombreOrden) {
        this.nombreOrden = nombreOrden;
    }

    public String getRucOrden() {
        return rucOrden;
    }

    public void setRucOrden(String rucOrden) {
        this.rucOrden = rucOrden;
    }

    public String getDireccionOrden() {
        return direccionOrden;
    }

    public void setDireccionOrden(String direccionOrden) {
        this.direccionOrden = direccionOrden;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(Double montoRef) {
        this.montoRef = montoRef;
    }

    public String getDireccionUnidad() {
        return direccionUnidad;
    }

    public void setDireccionUnidad(String direccionUnidad) {
        this.direccionUnidad = direccionUnidad;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getRucUnidad() {
        return rucUnidad;
    }

    public void setRucUnidad(String rucUnidad) {
        this.rucUnidad = rucUnidad;
    }

    public String getDistritoUnidad() {
        return distritoUnidad;
    }

    public void setDistritoUnidad(String distritoUnidad) {
        this.distritoUnidad = distritoUnidad;
    }

    public String getPaisUnidad() {
        return paisUnidad;
    }

    public void setPaisUnidad(String paisUnidad) {
        this.paisUnidad = paisUnidad;
    }

    public String getTelefonoUnidad() {
        return telefonoUnidad;
    }

    public void setTelefonoUnidad(String telefonoUnidad) {
        this.telefonoUnidad = telefonoUnidad;
    }

    public String getCorreoUnidad() {
        return correoUnidad;
    }

    public void setCorreoUnidad(String correoUnidad) {
        this.correoUnidad = correoUnidad;
    }

    public String getWebUnidad() {
        return webUnidad;
    }

    public void setWebUnidad(String webUnidad) {
        this.webUnidad = webUnidad;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Double getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Double montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public Double getMontoCadaUnoSer() {
        return montoCadaUnoSer;
    }

    public void setMontoCadaUnoSer(Double montoCadaUnoSer) {
        this.montoCadaUnoSer = montoCadaUnoSer;
    }

    public Double getSumaImporteFinal() {
        return sumaImporteFinal;
    }

    public void setSumaImporteFinal(Double sumaImporteFinal) {
        this.sumaImporteFinal = sumaImporteFinal;
    }

    public Double getMontoGeneralOrden() {
        return montoGeneralOrden;
    }

    public void setMontoGeneralOrden(Double montoGeneralOrden) {
        this.montoGeneralOrden = montoGeneralOrden;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }
}
