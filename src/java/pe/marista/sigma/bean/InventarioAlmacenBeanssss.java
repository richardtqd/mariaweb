/*
 * To change this license header choose License Headers in Project Properties.
 * To change this template file choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class InventarioAlmacenBeanssss implements Serializable {
    
    private Integer idinventarioAlmacen;
      private UnidadNegocioBean unidadNegocioBean; 
      private CatalogoBean catalogoBean;
      private Integer idTipoUnimed;
      private Float stockActual;
      private Float stockMin;
      private Float stockMax;
      private Float precioRef;
      private String observacion;
      private String creaPor;
      private Date fechaCrea;

    public Integer getIdinventarioAlmacen() {
        return idinventarioAlmacen;
    }

    public void setIdinventarioAlmacen(Integer idinventarioAlmacen) {
        this.idinventarioAlmacen = idinventarioAlmacen;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public CatalogoBean getCatalogoBean() {
        if(catalogoBean == null){
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public Integer getIdTipoUnimed() {
        return idTipoUnimed;
    }

    public void setIdTipoUnimed(Integer idTipoUnimed) {
        this.idTipoUnimed = idTipoUnimed;
    }

    public Float getStockActual() {
        return stockActual;
    }

    public void setStockActual(Float stockActual) {
        this.stockActual = stockActual;
    }

    public Float getStockMin() {
        return stockMin;
    }

    public void setStockMin(Float stockMin) {
        this.stockMin = stockMin;
    }

    public Float getStockMax() {
        return stockMax;
    }

    public void setStockMax(Float stockMax) {
        this.stockMax = stockMax;
    }

    public Float getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(Float precioRef) {
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
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }
          
}
