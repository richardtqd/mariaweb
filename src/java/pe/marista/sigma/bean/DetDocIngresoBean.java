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
public class DetDocIngresoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private DocIngresoBean docIngresoBean;//idDocIngreso
    private Integer idDetDocIngreso;
    private Integer idDocIngreso;
    private ConceptoBean conceptoBean;//idConcepto
    private BigDecimal monto;
    private BigDecimal dscto;
    private BigDecimal dsctoTipoDicente;
    private BigDecimal dsctoBeca;
    private BigDecimal montoPagado;
    private BigDecimal montoConDscto;
    private CodigoBean idTipoDscto;//idCodigo
    private CodigoBean idTipoMotivoDscto;//idCodigo
    private PlanContableBean cuentaD;//cuenta
    private PlanContableBean cuentaH;//cuenta
    private CentroResponsabilidadBean centroResponsabilidadBean;//cr
    private CuentasPorCobrarBean cuentasPorCobrarBean;//idCtasXCobrar
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date fechaInicio;
    private Date fechaFin;

    private BigDecimal montoSoles;
    private BigDecimal montoDolares;
    private BigDecimal mora;
    private String referencia;
    //AYUDA
    private String idDicente;
    private String uniNegDet;

    private ProgramacionBean programacionBean;

    /* CAMPOS DE AYUDA */ 
    private Integer idCtasXCobrar;
    private Integer mes;  
    
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public DocIngresoBean getDocIngresoBean() {
        if (docIngresoBean == null) {
            docIngresoBean = new DocIngresoBean();
        }
        return docIngresoBean;
    }

    public void setDocIngresoBean(DocIngresoBean docIngresoBean) {
        this.docIngresoBean = docIngresoBean;
    }

    public Integer getIdDetDocIngreso() {
        return idDetDocIngreso;
    }

    public void setIdDetDocIngreso(Integer idDetDocIngreso) {
        this.idDetDocIngreso = idDetDocIngreso;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getDscto() {
        return dscto;
    }

    public void setDscto(BigDecimal dscto) {
        this.dscto = dscto;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public CodigoBean getIdTipoDscto() {
        if (idTipoDscto == null) {
            idTipoDscto = new CodigoBean();
        }
        return idTipoDscto;
    }

    public void setIdTipoDscto(CodigoBean idTipoDscto) {
        this.idTipoDscto = idTipoDscto;
    }

    public CodigoBean getIdTipoMotivoDscto() {
        if (idTipoMotivoDscto == null) {
            idTipoMotivoDscto = new CodigoBean();
        }
        return idTipoMotivoDscto;
    }

    public void setIdTipoMotivoDscto(CodigoBean idTipoMotivoDscto) {
        this.idTipoMotivoDscto = idTipoMotivoDscto;
    }

    public PlanContableBean getCuentaD() {
        if (cuentaD == null) {
            cuentaD = new PlanContableBean();
        }
        return cuentaD;
    }

    public void setCuentaD(PlanContableBean cuentaD) {
        this.cuentaD = cuentaD;
    }

    public PlanContableBean getCuentaH() {
        if (cuentaH == null) {
            cuentaH = new PlanContableBean();
        }
        return cuentaH;
    }

    public void setCuentaH(PlanContableBean cuentaH) {
        this.cuentaH = cuentaH;
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

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
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

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getIdDicente() {
        return idDicente;
    }

    public void setIdDicente(String idDicente) {
        this.idDicente = idDicente;
    }

    public BigDecimal getDsctoBeca() {
        return dsctoBeca;
    }

    public void setDsctoBeca(BigDecimal dsctoBeca) {
        this.dsctoBeca = dsctoBeca;
    }

    public BigDecimal getMontoSoles() {
        return montoSoles;
    }

    public void setMontoSoles(BigDecimal montoSoles) {
        this.montoSoles = montoSoles;
    }

    public BigDecimal getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(BigDecimal montoDolares) {
        this.montoDolares = montoDolares;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
    }

    public Integer getIdCtasXCobrar() {
        return idCtasXCobrar;
    }

    public void setIdCtasXCobrar(Integer idCtasXCobrar) {
        this.idCtasXCobrar = idCtasXCobrar;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getUniNegDet() {
        return uniNegDet;
    }

    public void setUniNegDet(String uniNegDet) {
        this.uniNegDet = uniNegDet;
    }

    public BigDecimal getMontoConDscto() {
        return montoConDscto;
    }

    public void setMontoConDscto(BigDecimal montoConDscto) {
        this.montoConDscto = montoConDscto;
    }

    public BigDecimal getDsctoTipoDicente() {
        return dsctoTipoDicente;
    }

    public void setDsctoTipoDicente(BigDecimal dsctoTipoDicente) {
        this.dsctoTipoDicente = dsctoTipoDicente;
    }
 
 
}
