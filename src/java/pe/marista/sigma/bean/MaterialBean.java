
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;


public class MaterialBean implements Serializable
{
   // private Integer idInventarioAlmacen;
    private Integer idCatalogo;
    private UnidadNegocioBean unidadNegocioBean;
    private CatalogoBean catalogoBean;
    private CodigoBean tipoUnidadMedidaBean;
    private Integer stockActual;
    private Integer stockMin;
    private Integer stockMax;
    private Double precioRef;
    private String observacion;
    private String creaPor;
    private Date creaFecha;

   // public Integer getIdInventarioAlmacen() {
     //   return idInventarioAlmacen;
    //}

   // public void setIdInventarioAlmacen(Integer idInventarioAlmacen) {
   //     this.idInventarioAlmacen = idInventarioAlmacen;
   // }

    //public UnidadNegocioBean getUnidadNegocioBean() {
      //  if (unidadNegocioBean == null)
        //{ unidadNegocioBean = new UnidadNegocioBean(); }
       // return unidadNegocioBean;
    //}

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null)
        { catalogoBean = new CatalogoBean(); }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public CodigoBean getTipoUnidadMedidaBean() {
        if (tipoUnidadMedidaBean == null)
        { tipoUnidadMedidaBean = new CodigoBean(); }
        return tipoUnidadMedidaBean;
    }

    public void setTipoUnidadMedidaBean(CodigoBean tipoUnidadMedidaBean) {
        this.tipoUnidadMedidaBean = tipoUnidadMedidaBean;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

    public Integer getStockMax() {
        return stockMax;
    }

    public void setStockMax(Integer stockMax) {
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

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }
    
    
    
    
}
