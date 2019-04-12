package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class EventoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idEvento;
    private String nombre;
    private Integer estado;
    private Date fechaIni;
    private Date fechaFin;
    private Integer cantidad;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private Integer anio;
    private Integer flgDefault;

    //AYUDA
    private String estadoEvento;
    private String fechaIniVista;
    private String fechaFinVista;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(String estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public String getFechaIniVista() {
        return fechaIniVista;
    }

    public void setFechaIniVista(String fechaIniVista) {
        this.fechaIniVista = fechaIniVista;
    }

    public String getFechaFinVista() {
        return fechaFinVista;
    }

    public void setFechaFinVista(String fechaFinVista) {
        this.fechaFinVista = fechaFinVista;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getFlgDefault() {
        return flgDefault;
    }

    public void setFlgDefault(Integer flgDefault) {
        this.flgDefault = flgDefault;
    }

}
