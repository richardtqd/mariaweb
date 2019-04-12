/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.util.Date;
//
public class DetCotizacionBean {
    
    private UnidadNegocioBean unidadNegocioBean;
    private CotizacionBean cotizacionBean;
    private Integer idDetCotizacion;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private Integer cantidad =0;
    private CodigoBean tipoMonedaBean;
    private Double importe= 0.0;
    private CatalogoBean catalogoBean;
    private String creaPor;
    private Date creaFecha;
    private Integer idCotizacion;
    private String modiPor;
    private Integer idRequerimiento;
    private Integer idDetRequerimiento;
    private Integer anio;
    private Double importeAnterior =0.0;
    private Integer cantidadAnterior =0;
    private Double sumaImporte =0.0;    
    private Date fechaInicio;
    private Date fechaFin;
    
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public CotizacionBean getCotizacionBean() {
        if (cotizacionBean==null) {
            cotizacionBean= new CotizacionBean();
        }
        return cotizacionBean;
    }

    public void setCotizacionBean(CotizacionBean cotizacionBean) {
        this.cotizacionBean = cotizacionBean;
    }

    public Integer getIdDetCotizacion() {
        return idDetCotizacion;
    }

    public void setIdDetCotizacion(Integer idDetCotizacion) {
        this.idDetCotizacion = idDetCotizacion;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean==null) {
            solicitudLogisticoBean= new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    } 

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean==null) {
            tipoMonedaBean= new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean==null) {
            catalogoBean= new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
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

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public SolicitudLogDetalleBean getSolicitudLogDetalleBean() {
        if (solicitudLogDetalleBean==null) {
            solicitudLogDetalleBean= new SolicitudLogDetalleBean();
        }
                 
        return solicitudLogDetalleBean;
    }

    public void setSolicitudLogDetalleBean(SolicitudLogDetalleBean solicitudLogDetalleBean) {
        this.solicitudLogDetalleBean = solicitudLogDetalleBean;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    } 

    public Integer getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(Integer cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }  

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public Double getImporteAnterior() {
        return importeAnterior;
    }

    public void setImporteAnterior(Double importeAnterior) {
        this.importeAnterior = importeAnterior;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    
}
