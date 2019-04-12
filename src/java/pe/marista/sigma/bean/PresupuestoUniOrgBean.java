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
public class PresupuestoUniOrgBean implements Serializable {

    private Integer idPresupuestoUniOrg;
    private UnidadOrganicaBean unidadOrganicaBean; //idUniOrg
    private String unidadOrganicaNombre;//comodin
    private PresupuestoBean presupuestoBean;// idPresupuesto
    private BigDecimal presupuestoTope;
    private BigDecimal presupuestoProg;
    private BigDecimal presupuestoEjec;
    private PlanContableBean planContableBean;
    private UnidadNegocioBean unidadNegocioBean;
    private PlanOperativoBean planOperativoBean;
    private Integer anio;
    private Integer idPresupuesto;
    private Integer cuenta;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private BigDecimal saldo;
    private BigDecimal porcentajeSaldo;

    private BigDecimal presupuestoejec;

    //Ayuda
    private String mod;
    private String uniNeg;

    //Reporte de Presupuesto Unidad Organica
    private Integer id;
    private Integer iduniorg;
    private String nombreuniorg;
    private BigDecimal importe;
    private String unidadOrganica;
    private Integer numActividad;
    private Integer numSubActividad;
    private String creaFechaAc;
    private String creaHoraAc;

    public PresupuestoUniOrgBean(Integer idPresupuestoUniOrg, String unidadOrganicaNombre, PresupuestoBean presupuestoBean, BigDecimal presupuestoTope, BigDecimal presupuestoProg) {
        this.idPresupuestoUniOrg = idPresupuestoUniOrg;
        this.unidadOrganicaNombre = unidadOrganicaNombre;
        this.presupuestoBean = presupuestoBean;
        this.presupuestoTope = presupuestoTope;
        this.presupuestoProg = presupuestoProg;
    }

    public PresupuestoUniOrgBean() {
    }

    public String getUnidadOrganicaNombre() {
        return unidadOrganicaNombre;
    }

    public Integer getIdPresupuestoUniOrg() {
        return idPresupuestoUniOrg;
    }

    public void setIdPresupuestoUniOrg(Integer idPresupuestoUniOrg) {
        this.idPresupuestoUniOrg = idPresupuestoUniOrg;
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

    public PresupuestoBean getPresupuestoBean() {
        if (presupuestoBean == null) {
            presupuestoBean = new PresupuestoBean();
        }
        return presupuestoBean;
    }

    public void setPresupuestoBean(PresupuestoBean presupuestoBean) {
        this.presupuestoBean = presupuestoBean;
    }

    public BigDecimal getPresupuestoTope() {
        return presupuestoTope;
    }

    public void setPresupuestoTope(BigDecimal presupuestoTope) {
        this.presupuestoTope = presupuestoTope;
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

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
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

    public PlanOperativoBean getPlanOperativoBean() {
        if (planOperativoBean == null) {
            planOperativoBean = new PlanOperativoBean();
        }
        return planOperativoBean;
    }

    public void setPlanOperativoBean(PlanOperativoBean planOperativoBean) {
        this.planOperativoBean = planOperativoBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(Integer idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    //Get y Set de Ayuda
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIduniorg() {
        return iduniorg;
    }

    public void setIduniorg(Integer iduniorg) {
        this.iduniorg = iduniorg;
    }

    public String getNombreuniorg() {
        return nombreuniorg;
    }

    public void setNombreuniorg(String nombreuniorg) {
        this.nombreuniorg = nombreuniorg;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getUnidadOrganica() {
        return unidadOrganica;
    }

    public void setUnidadOrganica(String unidadOrganica) {
        this.unidadOrganica = unidadOrganica;
    }

    public Integer getNumActividad() {
        return numActividad;
    }

    public void setNumActividad(Integer numActividad) {
        this.numActividad = numActividad;
    }

    public Integer getNumSubActividad() {
        return numSubActividad;
    }

    public void setNumSubActividad(Integer numSubActividad) {
        this.numSubActividad = numSubActividad;
    }

    public String getCreaFechaAc() {
        return creaFechaAc;
    }

    public void setCreaFechaAc(String creaFechaAc) {
        this.creaFechaAc = creaFechaAc;
    }

    public String getCreaHoraAc() {
        return creaHoraAc;
    }

    public void setCreaHoraAc(String creaHoraAc) {
        this.creaHoraAc = creaHoraAc;
    }

    public BigDecimal getPresupuestoejec() {
        return presupuestoejec;
    }

    public void setPresupuestoejec(BigDecimal presupuestoejec) {
        this.presupuestoejec = presupuestoejec;
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
