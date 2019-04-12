package pe.marista.sigma.bean.reporte;

import java.sql.Timestamp;

 /**
 *
 * @author Meylin
 */
public class MovimientoAlmacenRepBean {
    private Integer nroMovimiento;
    private Timestamp fechaMov;
    private Integer cantidad;
    private String entregadoPor;
    private String recibidoPor;
    private String tipoUniMed;
    private Integer idCatalogo;
    private String item;
    private Integer nroAlmacen;
    private String nombreUniNeg;
    private String direccionUnidad;
    private String telefonoUnidad;
    private String correoUnidad;
    private String webUnidad;
    private String distritoUnidad;
    private String paisUnidad;
    private Integer stockAnterior;
    private Integer stockActual;
    private String rutaImagen;

    public Integer getNroMovimiento() {
        return nroMovimiento;
    }

    public void setNroMovimiento(Integer nroMovimiento) {
        this.nroMovimiento = nroMovimiento;
    }

    public Timestamp getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Timestamp fechaMov) {
        this.fechaMov = fechaMov;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getEntregadoPor() {
        return entregadoPor;
    }

    public void setEntregadoPor(String entregadoPor) {
        this.entregadoPor = entregadoPor;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }

    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getNroAlmacen() {
        return nroAlmacen;
    }

    public void setNroAlmacen(Integer nroAlmacen) {
        this.nroAlmacen = nroAlmacen;
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

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(Integer stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
}
