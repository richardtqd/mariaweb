package pe.marista.sigma.bean.reporte;

import java.util.Date;
public class DetOrdenCompraRepBean {
  
    private String moneda;
    private String atencion;
    private String nroCotiPro;
    private String horaRegreso;
    private String horaSalida;
    private String fechaSalida;
    private String destinoServicio;
    private String nroCompra;
    private String administradora;
    private String idOrdenCompra;
    private String anio;
    private String fecha;  
    private Integer cantidad;
    private String item; 
    private Double importe;
    private String tipoMoneda;
    private String nombreUniNeg;
    private String categoria;
    private String formaPago;
    private String rucOrden;
    private String nombreOrden;
    private String direccionOrden;
    private String rucUnidad;
    private String nombreUnidad; 
    private String webUnidad; 
    private String correoUnidad;
    private String telefonoUnidad;
    private String direccionUnidad;
    private String distritoUnidad;
    private String paisUnidad; 
    private String importeAdelanto;
    private Double importePropuesto; 
    private Double sumaImporte;
    private Double sumaImporteFinal;
    private Double montoRef; 
    private Double montoCadaUnoMate;
    private Double montoCadaUnoSer;
    private String tipoUniMed;
    private String fechaEntregaServicio;
    private String fechaEntrega;
    private String nombreComercial;
    private String nombreAutorizador;
    private String cargoAutorizador;
    private String tipoPago;
    private String lugarEntrega; 
    private String rutaImagen;
    
    //Ayuda(No esta en el reporte)
    private Date fechaOrden; 
    private String uniNeg; 
    private Integer idDetRequerimiento;
    private Integer idRequerimiento;  
    private String nombrecheque; 
    private String nombreCompleto;
    private String descripcion; 
    private String igv;
    
    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
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

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
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
    
    public String getImporteAdelanto() {
        return importeAdelanto;
    }

    public void setImporteAdelanto(String importeAdelanto) {
        this.importeAdelanto = importeAdelanto;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public String getNombrecheque() {
        return nombrecheque;
    }

    public void setNombrecheque(String nombrecheque) {
        this.nombrecheque = nombrecheque;
    }

    public Double getImportePropuesto() {
        return importePropuesto;
    }

    public void setImportePropuesto(Double importePropuesto) {
        this.importePropuesto = importePropuesto;
    }

    public String getAdministradora() {
        return administradora;
    }

    public void setAdministradora(String administradora) {
        this.administradora = administradora;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraRegreso() {
        return horaRegreso;
    }

    public void setHoraRegreso(String horaRegreso) {
        this.horaRegreso = horaRegreso;
    }

    public String getDestinoServicio() {
        return destinoServicio;
    }

    public void setDestinoServicio(String destinoServicio) {
        this.destinoServicio = destinoServicio;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNroCompra() {
        return nroCompra;
    }

    public void setNroCompra(String nroCompra) {
        this.nroCompra = nroCompra;
    }

    public String getNroCotiPro() {
        return nroCotiPro;
    }

    public void setNroCotiPro(String nroCotiPro) {
        this.nroCotiPro = nroCotiPro;
    }

    public String getFechaEntregaServicio() {
        return fechaEntregaServicio;
    }

    public void setFechaEntregaServicio(String fechaEntregaServicio) {
        this.fechaEntregaServicio = fechaEntregaServicio;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreAutorizador() {
        return nombreAutorizador;
    }

    public void setNombreAutorizador(String nombreAutorizador) {
        this.nombreAutorizador = nombreAutorizador;
    } 

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public String getCargoAutorizador() {
        return cargoAutorizador;
    }

    public void setCargoAutorizador(String cargoAutorizador) {
        this.cargoAutorizador = cargoAutorizador;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    
}
