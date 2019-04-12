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
public class InventarioActivoGeneralRepBean {
    
    private Integer idInventarioActivo;
    private String marca;
    private String modelo;
    private Integer stockactual;
    private Double preciocompra;
    private Boolean flgdonacion;
    private String item;
    private String nombreProveedor;
    private String tipoMoneda;
    private String tipoUnidadMedida;
    private String nombreUniNeg;
    private String direccionUnidad;
    private String telefonoUnidad;
    private String correoUnidad;
    private String webUnidad;
    private String distritoUnidad;
    private String paisUnidad;
    private String estado;

    public Integer getIdInventarioActivo() {
        return idInventarioActivo;
    }

    public void setIdInventarioActivo(Integer idInventarioActivo) {
        this.idInventarioActivo = idInventarioActivo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getStockactual() {
        return stockactual;
    }

    public void setStockactual(Integer stockactual) {
        this.stockactual = stockactual;
    }

    public Double getPreciocompra() {
        return preciocompra;
    }

    public void setPreciocompra(Double preciocompra) {
        this.preciocompra = preciocompra;
    }

    public Boolean getFlgdonacion() {
        if (flgdonacion != null) {
            if (flgdonacion.equals(true)) {
                estado = "Si";
            } else {
                if (flgdonacion.equals(false)) {
                    estado = "No";
                }
            }
        }
        return flgdonacion;
    }

    public void setFlgdonacion(Boolean flgdonacion) {
        this.flgdonacion = flgdonacion;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getTipoUnidadMedida() {
        return tipoUnidadMedida;
    }

    public void setTipoUnidadMedida(String tipoUnidadMedida) {
        this.tipoUnidadMedida = tipoUnidadMedida;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getDireccionUnidad() {
        return direccionUnidad;
    }

    public void setDireccionUnidad(String direccionUnidad) {
        this.direccionUnidad = direccionUnidad;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
