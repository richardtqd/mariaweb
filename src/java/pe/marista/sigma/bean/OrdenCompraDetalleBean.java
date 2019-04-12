package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class OrdenCompraDetalleBean implements Serializable {

    private Integer idDetOrdenCompra;
    private UnidadNegocioBean unidadNegocioBean;
    private AnioBean anioBean;
    private Integer anio;
    private OrdenCompraBean ordenCompraBean;
    private Integer idOrdenCompra;
    private Integer idDetRequerimiento;
    private CodigoBean tipoUniMedBean;
    private Integer cantidad = 0;
    private Integer cantidadAnterior = 0;
    private CatalogoBean catalogoBean;
    private CodigoBean tipoMonedaBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private Integer idRequerimiento;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private String creaPor;
    private Date creaFecha;
    private Double sumaImporte = 0.0;
    private Double importe = 0.0;
    private Double importeAnterior = 0.0;
//    private boolean itemFact;
    private CodigoBean codigoBean;
    private FacturaCompraBean facturaCompraBean;
    private Integer cantidadRecibida;
//ayuda
    private String idFacturaCompra;
    private String serieDoc;
    private String nroDoc;
    private CotizacionBean cotizacionBean;
    private DetCotizacionBean detCotizacionBean;
    
    //ayuda Filtro Proveedores
    private Date fechaInicio;
    private Date fechaFin;
    
    private Boolean validarCatalogo;

    private String categoria;
    
//     public OrdenCompraDetalleBean(){
//    this.itemFact= false;
//    }
    public Double getImporteAnterior() {
        return importeAnterior;
    }

    public void setImporteAnterior(Double importeAnterior) {
        this.importeAnterior = importeAnterior;
    }

    public Integer getCantidadAnterior() {
        return cantidadAnterior;
    }

    public void setCantidadAnterior(Integer cantidadAnterior) {
        this.cantidadAnterior = cantidadAnterior;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
    }

    public CodigoBean getTipoUniMedBean() {
        if (tipoUniMedBean == null) {
            tipoUniMedBean = new CodigoBean();
        }
        return tipoUniMedBean;
    }

    public void setTipoUniMedBean(CodigoBean tipoUniMedBean) {
        this.tipoUniMedBean = tipoUniMedBean;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean == null) {
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public Integer getIdDetOrdenCompra() {
        return idDetOrdenCompra;
    }

    public void setIdDetOrdenCompra(Integer idDetOrdenCompra) {
        this.idDetOrdenCompra = idDetOrdenCompra;
    }

    public OrdenCompraBean getOrdenCompraBean() {
        if (ordenCompraBean == null) {
            ordenCompraBean = new OrdenCompraBean();
        }
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    public Integer getIdDetRequerimiento() {
        return idDetRequerimiento;
    }

    public void setIdDetRequerimiento(Integer idDetRequerimiento) {
        this.idDetRequerimiento = idDetRequerimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null) {
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean == null) {
            solicitudLogisticoBean = new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
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

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public AnioBean getAnioBean() {
        if (anioBean == null) {
            anioBean = new AnioBean();
        }
        return anioBean;
    }

    public void setAnioBean(AnioBean anioBean) {
        this.anioBean = anioBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public SolicitudLogDetalleBean getSolicitudLogDetalleBean() {
        if (solicitudLogDetalleBean == null) {
            solicitudLogDetalleBean = new SolicitudLogDetalleBean();
        }
        return solicitudLogDetalleBean;
    }

    public void setSolicitudLogDetalleBean(SolicitudLogDetalleBean solicitudLogDetalleBean) {
        this.solicitudLogDetalleBean = solicitudLogDetalleBean;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

//    public boolean isRecibido() {
//        return recibido;
//    }
//
//    public void setRecibido(boolean recibido) {
//        this.recibido = recibido;
//    }
    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean == null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public String getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(String idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    public String getSerieDoc() {
        return serieDoc;
    }

    public void setSerieDoc(String serieDoc) {
        this.serieDoc = serieDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
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

    public DetCotizacionBean getDetCotizacionBean() {
        if (detCotizacionBean==null) {
            detCotizacionBean= new DetCotizacionBean();
        }
        return detCotizacionBean;
    }

    public void setDetCotizacionBean(DetCotizacionBean detCotizacionBean) {
        this.detCotizacionBean = detCotizacionBean;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getValidarCatalogo() {
        return validarCatalogo;
    }

    public void setValidarCatalogo(Boolean validarCatalogo) {
        this.validarCatalogo = validarCatalogo;
    }
    
}
