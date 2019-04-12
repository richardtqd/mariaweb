/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS002
 */
public class DetActividadBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private UnidadOrganicaBean unidadOrganicaBean;
    private Integer anio;
    private Integer meta;
    private ObjOperativoBean objOperativoBean;
    private ActividadBean actividadBean;
    private Integer idDetActividad;
    private PlanContableBean planContableBean;
    private String descripcion;
    private BigDecimal importe;
    private Integer mes;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private CodigoBean tipoPeriodo;

    //Ayuda
    private Integer idObjOperativo;
    private Integer idUniOrg;
    private String meses;
    private Integer contador;
    private BigDecimal ejecutado;

    //Ayuda Pres
    private Integer con;
    private Integer cuenta;
    private String nomCuenta;
    private Integer numActividades;
    private BigDecimal presProg;
    private BigDecimal presExec;

    private String labelMes;

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

    public Integer getIdDetActividad() {
        return idDetActividad;
    }

    public void setIdDetActividad(Integer idDetActividad) {
        this.idDetActividad = idDetActividad;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
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

    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public Integer getIdObjOperativo() {
        return idObjOperativo;
    }

    public void setIdObjOperativo(Integer idObjOperativo) {
        this.idObjOperativo = idObjOperativo;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public String getMeses() {
        if (mes != null) {
            if (mes.equals(1)) {
                meses = MaristaConstantes.ENERO;
                return meses;
            }
            if (mes.equals(2)) {
                meses = MaristaConstantes.FEBRERO;
                return meses;
            }
            if (mes.equals(3)) {
                meses = MaristaConstantes.MARZO;
                return meses;
            }
            if (mes.equals(4)) {
                meses = MaristaConstantes.ABRIL;
                return meses;
            }
            if (mes.equals(5)) {
                meses = MaristaConstantes.MAYO;
                return meses;
            }
            if (mes.equals(6)) {
                meses = MaristaConstantes.JUNIO;
                return meses;
            }
            if (mes.equals(7)) {
                meses = MaristaConstantes.JULIO;
                return meses;
            }
            if (mes.equals(8)) {
                meses = MaristaConstantes.AGOSTO;
                return meses;
            }
            if (mes.equals(9)) {
                meses = MaristaConstantes.SETIEMBRE;
                return meses;
            }
            if (mes.equals(10)) {
                meses = MaristaConstantes.OCTUBRE;
                return meses;
            }
            if (mes.equals(11)) {
                meses = MaristaConstantes.NOVIEMBRE;
                return meses;
            }
            if (mes.equals(12)) {
                meses = MaristaConstantes.DICIEMBRE;
                return meses;
            }
        }
        return meses;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public Integer getCon() {
        return con;
    }

    public void setCon(Integer con) {
        this.con = con;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNomCuenta() {
        return nomCuenta;
    }

    public void setNomCuenta(String nomCuenta) {
        this.nomCuenta = nomCuenta;
    }

    public Integer getNumActividades() {
        return numActividades;
    }

    public void setNumActividades(Integer numActividades) {
        this.numActividades = numActividades;
    }

    public BigDecimal getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(BigDecimal ejecutado) {
        this.ejecutado = ejecutado;
    }

    public BigDecimal getPresProg() {
        return presProg;
    }

    public void setPresProg(BigDecimal presProg) {
        this.presProg = presProg;
    }

    public BigDecimal getPresExec() {
        return presExec;
    }

    public void setPresExec(BigDecimal presExec) {
        this.presExec = presExec;
    }

    public CodigoBean getTipoPeriodo() {
        if (tipoPeriodo == null) {
            tipoPeriodo = new CodigoBean();
        }
        return tipoPeriodo;
    }

    public void setTipoPeriodo(CodigoBean tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    public String getLabelMes() {
        return labelMes;
    }

    public void setLabelMes(String labelMes) {
        this.labelMes = labelMes;
    }

}
