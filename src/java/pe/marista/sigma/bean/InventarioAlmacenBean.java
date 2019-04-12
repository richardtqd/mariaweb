package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class InventarioAlmacenBean implements Serializable {

    // private Integer idInventarioAlmacen;
    private UnidadNegocioBean unidadNegocioBean;
    private String uniNeg;
    private CatalogoBean catalogoBean;
    private CodigoBean codigoBean;
    private Integer idCatalogo;
    private Integer idAlmacen;
    private EntidadSedeBean entidadSedeBean;
    private EntidadBean entidadBean;
    private CodigoBean tipoUniMedBean;
    private Integer idTipoUniMed;
    private String tipoUniMed;
    private Integer stockActual;
    private Integer stockMin;
    private Float stockMax;
    private Double precioRef;
    private String observacion;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer nroAlmacen;

    private String stockVista;
    private String alerta;

    private Integer stockAntiguo = 0;
    private Integer stockNuevo = 0;

    private Integer stockAyuda;

    private String color;

    public InventarioAlmacenBean() {
    }

//    public InventarioAlmacenBean(Integer idCatalogo, Integer idTipoUniMed, Integer stockActual,
//            Integer stockMin, Float stockMax, Double precioRef, String observacion, String creaPor, Date creaFecha) {
//       // this.idInventarioAlmacen = idInventarioAlmacen;
//
//        this.idCatalogo = idCatalogo;
//        this.idTipoUniMed = idTipoUniMed;
//        this.stockActual = stockActual;
//        this.stockMin = stockMin;
//        this.stockMax = stockMax;
//        this.precioRef = precioRef;
//        this.observacion = observacion;
//        this.creaPor = creaPor;
//        this.creaFecha = creaFecha;
//    }
    //   public Integer getIdInventarioAlmacen() {
    //     return idInventarioAlmacen;
    //}
    //public void setIdInventarioAlmacen(Integer idInventarioAlmacen) {
    //  this.idInventarioAlmacen = idInventarioAlmacen;
    //}
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EntidadSedeBean getEntidadSedeBean() {
        if (entidadSedeBean == null) {
            entidadSedeBean = new EntidadSedeBean();
        }
        return entidadSedeBean;
    }

    public void setEntidadSedeBean(EntidadSedeBean entidadSedeBean) {
        this.entidadSedeBean = entidadSedeBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();

        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

    public Float getStockMax() {
        return stockMax;
    }

    public void setStockMax(Float stockMax) {
        this.stockMax = stockMax;
    }

    public Double getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(Double precioRef) {
        this.precioRef = precioRef;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getFechaCrea() {
        return creaFecha;
    }

    public void setFechaCrea(Date creaFecha) {
        this.creaFecha = creaFecha;
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

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public String getTipoUniMed() {
        return tipoUniMed;
    }

    public void setTipoUniMed(String tipoUniMed) {
        this.tipoUniMed = tipoUniMed;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getStockVista() {
        if (stockActual != null) {
            stockVista = stockActual.toString();
        }
        return stockVista;
    }

    public void setStockVista(String stockVista) {
        this.stockVista = stockVista;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public Integer getStockAntiguo() {
        return stockAntiguo;
    }

    public void setStockAntiguo(Integer stockAntiguo) {
        this.stockAntiguo = stockAntiguo;
    }

    public Integer getStockNuevo() {
        return stockNuevo;
    }

    public void setStockNuevo(Integer stockNuevo) {
        this.stockNuevo = stockNuevo;
    }

    public Integer getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(Integer idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public Integer getNroAlmacen() {
        return nroAlmacen;
    }

    public void setNroAlmacen(Integer nroAlmacen) {
        this.nroAlmacen = nroAlmacen;
    }

    public Integer getStockAyuda() {
        return stockAyuda;
    }

    public void setStockAyuda(Integer stockAyuda) {
        this.stockAyuda = stockAyuda;
    }

    //CAMBIO DE BEAN
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
