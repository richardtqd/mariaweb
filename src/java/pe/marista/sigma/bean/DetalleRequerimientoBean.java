/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
class DetalleRequerimientoBean implements Serializable{
    
    private Integer iddetrequerimiento;
    private UnidadNegocioBean unidadNegocioBean;
  //  private RequerimientoBean requerimientoBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private Integer idTipoUniMed;
    private CatalogoBean catalogoBean;
    private Integer cuenta;
    private Float cantidadEntregada;
    private Float cantidadSolicitada;
    private Float precioRef;
    private Integer idTipoMoneda;
    private String creadoPor;
    private Date fechaCrea;
    private Boolean  flgChequeo;
    private Integer idTipoStatusDetReq;

    public Integer getIddetrequerimiento() {
        return iddetrequerimiento;
    }

    public void setIddetrequerimiento(Integer iddetrequerimiento) {
        this.iddetrequerimiento = iddetrequerimiento;
    }

  //  public RequerimientoBean getRequerimientoBean() {
    //    return requerimientoBean;
    //}

    //public void setRequerimientoBean(RequerimientoBean requerimientoBean) {
      //  this.requerimientoBean = requerimientoBean;
    //}

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public CatalogoBean getCatalogoBean() {
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Float getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Float cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public Float getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Float cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Float getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(Float precioRef) {
        this.precioRef = precioRef;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Boolean getFlgChequeo() {
        return flgChequeo;
    }

    public void setFlgChequeo(Boolean flgChequeo) {
        this.flgChequeo = flgChequeo;
    }

    public Integer getIdTipoStatusDetReq() {
        return idTipoStatusDetReq;
    }

    public void setIdTipoStatusDetReq(Integer idTipoStatusDetReq) {
        this.idTipoStatusDetReq = idTipoStatusDetReq;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean==null)
        {
            unidadNegocioBean=new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if(solicitudLogisticoBean==null)
        {
            solicitudLogisticoBean=new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    }
    
}
