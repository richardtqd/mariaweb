/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

/**
 *
 * @author Administrador
 */
public class DetCotizacionRepBean {

    private String uniNeg;
    private Integer idCotizacion;
    private Integer cantidad;
    private String item;
    private String tipoMoneda;
    private String nombreUniNeg;
    private String categoria;
    private String formaPagoOrden;
    private String rucOrden;
    private String nombreOrden;
    private String direccionOrden;
    private String nombreUnidad;
    private Integer distritoUnidad;
    private String webUnidad;
    private String correoUnidad;
    private String telefonoUnidad;
    private String direccionUnidad;
    private String nombreDistrito;
    private String paisUnidad;
    private Double sumaImporte;
    private Double montoRef;
    private Double montoCadaUnoMate;
    private Double importe;
    private Boolean flgAceptado;
    private String estado;
    private String tipoUniMed;
    private Integer idrequerimiento;
    private String rutaImagen;
    
    private String nroCotizacion;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFormaPagoOrden() {
        return formaPagoOrden;
    }

    public void setFormaPagoOrden(String formaPagoOrden) {
        this.formaPagoOrden = formaPagoOrden;
    }

    public String getRucOrden() {
        return rucOrden;
    }

    public void setRucOrden(String rucOrden) {
        this.rucOrden = rucOrden;
    }

    public String getNombreOrden() {
        return nombreOrden;
    }

    public void setNombreOrden(String nombreOrden) {
        this.nombreOrden = nombreOrden;
    }

    public String getDireccionOrden() {
        return direccionOrden;
    }

    public void setDireccionOrden(String direccionOrden) {
        this.direccionOrden = direccionOrden;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public Integer getDistritoUnidad() {
        return distritoUnidad;
    }

    public void setDistritoUnidad(Integer distritoUnidad) {
        this.distritoUnidad = distritoUnidad;
    }

    public String getWebUnidad() {
        return webUnidad;
    }

    public void setWebUnidad(String webUnidad) {
        this.webUnidad = webUnidad;
    }

    public String getCorreoUnidad() {
        return correoUnidad;
    }

    public void setCorreoUnidad(String correoUnidad) {
        this.correoUnidad = correoUnidad;
    }

    public String getTelefonoUnidad() {
        return telefonoUnidad;
    }

    public void setTelefonoUnidad(String telefonoUnidad) {
        this.telefonoUnidad = telefonoUnidad;
    }

    public String getDireccionUnidad() {
        return direccionUnidad;
    }

    public void setDireccionUnidad(String direccionUnidad) {
        this.direccionUnidad = direccionUnidad;
    }

    public String getNombreDistrito() {
        return nombreDistrito;
    }

    public void setNombreDistrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

    public String getPaisUnidad() {
        return paisUnidad;
    }

    public void setPaisUnidad(String paisUnidad) {
        this.paisUnidad = paisUnidad;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public Double getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(Double montoRef) {
        this.montoRef = montoRef;
    }

    public Double getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Double montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Boolean getFlgAceptado() {
        if (flgAceptado != null) {
            if (flgAceptado.equals(true)) {
                estado = "Aprobado";
            } else {
                if (flgAceptado.equals(false)) {
                    estado = "Desaprobado";
                }
            }
        }
        return flgAceptado;
    }

    public void setFlgAceptado(Boolean flgAceptado) {
        this.flgAceptado = flgAceptado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public Integer getIdrequerimiento() {
        return idrequerimiento;
    }

    public void setIdrequerimiento(Integer idrequerimiento) {
        this.idrequerimiento = idrequerimiento;
    }

    public String getNroCotizacion() {
        return nroCotizacion;
    }

    public void setNroCotizacion(String nroCotizacion) {
        this.nroCotizacion = nroCotizacion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    
}
