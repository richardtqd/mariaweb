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
public class MovimientoAlmacenBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private CatalogoBean catalogoBean;
    private Integer idMovimientoAlmacen;
    private Integer nroMovimiento;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private Date fechaMov;
    private Integer cantidad;
    private CodigoBean tipoUniMedBean;
    private String entregadoPor;
    private String recibidoPor;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private PersonalBean personalBean;
    private PersonaBean personaBean;
    private String idTipoSolicitante;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private Integer idTipoSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private String idPersonalSol;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
     private String idTipoRecibido;//PRO:PROVEEDOR,PER:PERSONA,COL:COLABORADOR-TRABAJADOR-PERSONAL
    private String idRecibido;
    private String nomRecibido;
    private Integer stockAnterior;
    private String idMovimientoAyuda; 
    
    //ayuda
    private Integer cantidadSession;
    
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean== null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
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

    public Integer getIdMovimientoAlmacen() {
        return idMovimientoAlmacen;
    }

    public void setIdMovimientoAlmacen(Integer idMovimientoAlmacen) {
        this.idMovimientoAlmacen = idMovimientoAlmacen;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean== null) {
            solicitudLogisticoBean= new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    }

    public SolicitudLogDetalleBean getSolicitudLogDetalleBean() {
        if (solicitudLogDetalleBean== null) {
            solicitudLogDetalleBean= new SolicitudLogDetalleBean();
        }
        return solicitudLogDetalleBean;
    }

    public void setSolicitudLogDetalleBean(SolicitudLogDetalleBean solicitudLogDetalleBean) {
        this.solicitudLogDetalleBean = solicitudLogDetalleBean;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }
        
    public CodigoBean getTipoUniMedBean() {
        if (tipoUniMedBean== null) {
            tipoUniMedBean = new CodigoBean();
        }
        return tipoUniMedBean;
    }

    public void setTipoUniMedBean(CodigoBean tipoUniMedBean) {
        this.tipoUniMedBean = tipoUniMedBean;
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

    public void setRecibidopor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }
    
    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
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
    
    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if (inventarioAlmacenBean == null) {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
    } 

    public PersonalBean getPersonalBean() {
        if (personalBean== null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean== null) {
            personaBean= new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public String getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(String idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public Integer getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(Integer idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public String getIdPersonalSol() {
        return idPersonalSol;
    }

    public void setIdPersonalSol(String idPersonalSol) {
        this.idPersonalSol = idPersonalSol;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getIdTipoRecibido() {
        return idTipoRecibido;
    }

    public void setIdTipoRecibido(String idTipoRecibido) {
        this.idTipoRecibido = idTipoRecibido;
    }

    public String getIdRecibido() {
        return idRecibido;
    }

    public void setIdRecibido(String idRecibido) {
        this.idRecibido = idRecibido;
    }

    public String getNomRecibido() {
        return nomRecibido;
    }

    public void setNomRecibido(String nomRecibido) {
        this.nomRecibido = nomRecibido;
    }

    public Integer getNroMovimiento() {
        return nroMovimiento;
    }

    public void setNroMovimiento(Integer nroMovimiento) {
        this.nroMovimiento = nroMovimiento;
    }

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public void setStockAnterior(Integer stockAnterior) {
        this.stockAnterior = stockAnterior;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getIdMovimientoAyuda() {
        return idMovimientoAyuda;
    }

    public void setIdMovimientoAyuda(String idMovimientoAyuda) {
        this.idMovimientoAyuda = idMovimientoAyuda;
    }

    public Integer getCantidadSession() {
        return cantidadSession;
    }

    public void setCantidadSession(Integer cantidadSession) {
        this.cantidadSession = cantidadSession;
    } 
     
    
}
