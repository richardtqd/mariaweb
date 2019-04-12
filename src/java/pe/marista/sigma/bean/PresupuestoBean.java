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
public class PresupuestoBean implements Serializable {

    private Integer idPresupuesto;
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private Integer anio;
    private CodigoBean codigoBean; // idTipoPresupuesto
    private String nombrePresupuesto;
    private String descPresupuesto;
    private BigDecimal presupuestoProg;
    private BigDecimal presupuestoEjec;
    private BigDecimal presupuestoTope;
    private String ctaContable;
    private PlanContableBean planContableBean;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private String modiVer;
    private BigDecimal saldo;
    private BigDecimal porcentajeSaldo;

    //Ayuda
    private String uniNeg;
    private Integer cuenta;

    public PresupuestoBean(Integer idPresupuesto, UnidadNegocioBean unidadNegocioBean, Integer anio, String nombrePresupuesto, String descPresupuesto, BigDecimal presupuestoProg, BigDecimal presupuestoEjec, BigDecimal presupuestoTope) {
        this.idPresupuesto = idPresupuesto;
        this.unidadNegocioBean = unidadNegocioBean;
        this.anio = anio;
        this.nombrePresupuesto = nombrePresupuesto;
        this.descPresupuesto = descPresupuesto;
        this.presupuestoProg = presupuestoProg;
        this.presupuestoEjec = presupuestoEjec;
        this.presupuestoTope = presupuestoTope;
    }

    public PresupuestoBean() {
    }

    public Integer getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Integer idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public CodigoBean getCodigoBean() {
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public String getNombrePresupuesto() {
        return nombrePresupuesto;
    }

    public void setNombrePresupuesto(String nombrePresupuesto) {
        this.nombrePresupuesto = nombrePresupuesto;
    }

    public String getDescPresupuesto() {
        return descPresupuesto;
    }

    public void setDescPresupuesto(String descPresupuesto) {
        this.descPresupuesto = descPresupuesto;
    }

    public BigDecimal getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(BigDecimal presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }

    public BigDecimal getPresupuestoEjec() {
        return presupuestoEjec;
    }

    public void setPresupuestoEjec(BigDecimal presupuestoEjec) {
        this.presupuestoEjec = presupuestoEjec;
    }

    public BigDecimal getPresupuestoTope() {
        return presupuestoTope;
    }

    public void setPresupuestoTope(BigDecimal presupuestoTope) {
        this.presupuestoTope = presupuestoTope;
    }

    public String getCtaContable() {
        return ctaContable;
    }

    public void setCtaContable(String ctaContable) {
        this.ctaContable = ctaContable;
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

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getPorcentajeSaldo() {
        return porcentajeSaldo;
    }

    public void setPorcentajeSaldo(BigDecimal porcentajeSaldo) {
        this.porcentajeSaldo = porcentajeSaldo;
    }

}
