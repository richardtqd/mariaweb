 
package pe.marista.sigma.bean.reporte;
 
import java.sql.Timestamp;

public class DetFiltroProveItemRepBean {
    private Timestamp fechaorden;
    private int idordencompra;
    private String rucOrden;
    private String nombreOrden;
    private String item;
    private String nombreUniNeg;
    private String webUnidad;
    private String correoUnidad;
    private String telefonoUnidad;
    private String direccionUnidad;
    private String distritoUnidad;
    private String paisUnidad; 
    private Double precioOrden;

    public Timestamp getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Timestamp fechaorden) {
        this.fechaorden = fechaorden;
    }

    public int getIdordencompra() {
        return idordencompra;
    }

    public void setIdordencompra(int idordencompra) {
        this.idordencompra = idordencompra;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
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

    public Double getPrecioOrden() {
        return precioOrden;
    }

    public void setPrecioOrden(Double precioOrden) {
        this.precioOrden = precioOrden;
    } 
}
