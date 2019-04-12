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
public class AsientoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idAsiento;
    private Integer idObjeto;
    private String objeto;
    private PlanContableBean planContableCuentaDBean;
    private PlanContableBean planContableCuentaHBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private Double monto;
    private String numeroComprobante;
    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private String ruc;
    private Double tc;
    private String monedaOrigen;
    private CodigoBean tipoMoneda;
    private CodigoBean tipoOpe;
    private Date fechaOpe;
    private Date fechaDoc;
    private Integer anio;
    private Integer mes;
    private CodigoBean tipoDoc;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Integer codDistribucion;
    private Boolean status;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Integer idAsiento) {
        this.idAsiento = idAsiento;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(String numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if (procesoRecuperacionBean == null) {
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Double getTc() {
        return tc;
    }

    public void setTc(Double tc) {
        this.tc = tc;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public CodigoBean getTipoMoneda() {
        if (tipoMoneda == null) {
            tipoMoneda = new CodigoBean();
        }
        return tipoMoneda;
    }

    public void setTipoMoneda(CodigoBean tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public CodigoBean getTipoOpe() {
        if (tipoOpe == null) {
            tipoOpe = new CodigoBean();
        }
        return tipoOpe;
    }

    public void setTipoOpe(CodigoBean tipoOpe) {
        this.tipoOpe = tipoOpe;
    }

    public Date getFechaOpe() {
        return fechaOpe;
    }

    public void setFechaOpe(Date fechaOpe) {
        this.fechaOpe = fechaOpe;
    }

    public Date getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(Date fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public CodigoBean getTipoDoc() {
        if (tipoDoc == null) {
            tipoDoc = new CodigoBean();
        }
        return tipoDoc;
    }

    public void setTipoDoc(CodigoBean tipoDoc) {
        this.tipoDoc = tipoDoc;
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

    public PlanContableBean getPlanContableCuentaDBean() {
        if (planContableCuentaDBean == null) {
            planContableCuentaDBean = new PlanContableBean();
        }
        return planContableCuentaDBean;
    }

    public void setPlanContableCuentaDBean(PlanContableBean planContableCuentaDBean) {
        this.planContableCuentaDBean = planContableCuentaDBean;
    }

    public PlanContableBean getPlanContableCuentaHBean() {
        if (planContableCuentaHBean == null) {
            planContableCuentaHBean = new PlanContableBean();
        }
        return planContableCuentaHBean;
    }

    public void setPlanContableCuentaHBean(PlanContableBean planContableCuentaHBean) {
        this.planContableCuentaHBean = planContableCuentaHBean;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
