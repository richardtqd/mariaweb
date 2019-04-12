/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class ActividadCrBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private Integer anio;
    private ObjOperativoBean objOperativoBean;
    private ActividadBean actividadBean;
    private DetActividadBean detActividadBean;
    private PlanContableBean planContableBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private CodigoBean tipoValor;
    private Integer meta;
    private BigDecimal importe;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Ayuda
    private Integer idObjOperativo;
    private Integer idActividad;
    private Integer count;
    private Integer posicion;
    private Integer exec;
    private BigDecimal ejecutado;

    //AYUDA
    private Integer codDistribucion;
    private Float monto;
    private Double valorD;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public ObjOperativoBean getObjOperativoBean() {
        if (objOperativoBean == null) {
            objOperativoBean = new ObjOperativoBean();
        }
        return objOperativoBean;
    }

    public void setObjOperativoBean(ObjOperativoBean objOperativoBean) {
        this.objOperativoBean = objOperativoBean;
    }

    public ActividadBean getActividadBean() {
        if (actividadBean == null) {
            actividadBean = new ActividadBean();
        }
        return actividadBean;
    }

    public void setActividadBean(ActividadBean actividadBean) {
        this.actividadBean = actividadBean;
    }

    public DetActividadBean getDetActividadBean() {
        if (detActividadBean == null) {
            detActividadBean = new DetActividadBean();
        }
        return detActividadBean;
    }

    public void setDetActividadBean(DetActividadBean detActividadBean) {
        this.detActividadBean = detActividadBean;
    }

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
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

    public CodigoBean getTipoValor() {
        if (tipoValor == null) {
            tipoValor = new CodigoBean();
        }
        return tipoValor;
    }

    public void setTipoValor(CodigoBean tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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

    public Integer getIdObjOperativo() {
        return idObjOperativo;
    }

    public void setIdObjOperativo(Integer idObjOperativo) {
        this.idObjOperativo = idObjOperativo;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getExec() {
        return exec;
    }

    public void setExec(Integer exec) {
        this.exec = exec;
    }

    public BigDecimal getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(BigDecimal ejecutado) {
        this.ejecutado = ejecutado;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Double getValorD() {
        return valorD;
    }

    public void setValorD(Double valorD) {
        this.valorD = valorD;
    }

}
