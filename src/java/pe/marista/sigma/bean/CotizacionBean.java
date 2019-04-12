/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CotizacionBean {
     
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idCotizacion;
    private EntidadBean entidadBean;
    private CatalogoBean catalogoBean;
    private CodigoBean tipoPagoBean;
    private Double cantidad;
    private Double totalItem;
    private Double subTotal;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer anio;
    private Double montoRef;
    private CodigoBean tipoCategoriaBean;
    private String obs;
    private Date fechaCotizacion;
    private Boolean flgAceptado;
    private OrdenCompraBean ordenCompraBean;
    private CodigoBean tipoPrioridadBean;
    private Double montoCadaUnoMate;
    private String estado;
    private String nroCotiPro;
    private Double igvCoti=0.0;
    private Double importePorTodo=0.0;
    
    private String nroCotizacion;
//ayuda
    private Integer idRequerimiento;
    private String idCoti;
    public UnidadNegocioBean getUnidadNegocioBean() {
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean==null) {
            entidadBean= new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
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

    public CodigoBean getTipoPagoBean() {
        if (tipoPagoBean==null) {
            tipoPagoBean= new CodigoBean();
        }
        return tipoPagoBean;
    }

    public void setTipoPagoBean(CodigoBean tipoPagoBean) {
        this.tipoPagoBean = tipoPagoBean;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Double totalItem) {
        this.totalItem = totalItem;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Double getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(Double montoRef) {
        this.montoRef = montoRef;
    }
    
      public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean==null) {
            tipoCategoriaBean= new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Date getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Date fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
    }

    public Boolean getFlgAceptado() {
        if(flgAceptado != null)
        {
            if(flgAceptado.equals(true))
            {
                estado= "Aprobado";
            }
            else
            {
                estado = "Desaprobado";
            }
        }
        return flgAceptado;
    }

    public void setFlgAceptado(Boolean flgAceptado) {
        this.flgAceptado = flgAceptado;
    }

    public OrdenCompraBean getOrdenCompraBean() {
        if (ordenCompraBean==null) {
            ordenCompraBean= new OrdenCompraBean(); 
        }
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    public CodigoBean getTipoPrioridadBean() {
        if (tipoPrioridadBean==null) {
            tipoPrioridadBean = new CodigoBean();
        }
        return tipoPrioridadBean;
    }

    public void setTipoPrioridadBean(CodigoBean tipoPrioridadBean) {
        this.tipoPrioridadBean = tipoPrioridadBean;
    } 

    public Double getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Double montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public String getIdCoti() {
        return idCoti;
    }

    public void setIdCoti(String idCoti) {
        this.idCoti = idCoti;
    }

    public String getNroCotizacion() {
        return nroCotizacion;
    }

    public void setNroCotizacion(String nroCotizacion) {
        this.nroCotizacion = nroCotizacion;
    }

    public String getNroCotiPro() {
        return nroCotiPro;
    }

    public void setNroCotiPro(String nroCotiPro) {
        this.nroCotiPro = nroCotiPro;
    }

    public Double getIgvCoti() {
        return igvCoti;
    }

    public void setIgvCoti(Double igvCoti) {
        this.igvCoti = igvCoti;
    }

    public Double getImportePorTodo() {
        return importePorTodo;
    }

    public void setImportePorTodo(Double importePorTodo) {
        this.importePorTodo = importePorTodo;
    }

  
    
}
