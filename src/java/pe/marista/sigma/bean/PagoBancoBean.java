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
 * @author MS001
 */
public class PagoBancoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idPagoBanco;
    private CodigoBean idTipoServicioIngBean;
    private CodigoBean idTipoDoc;
    private ImpresoraBean impresoraBean;
    private String serie;
    private String nroDoc;
    private String idDiscente;
    private String discente;
    private Integer anio;
    private Integer codigo;
    private String obs;
    private CodigoBean idTipoLugarPago;
    private CodigoBean idTipoModoPago;
    private Date fechaPago;
    private CodigoBean idTipoMoneda;
    private CodigoBean tipoStatusPagoBanco;
    private Date creaFechaStatus;
    private String creaStatus;
    private Date fechaVenc;
    private CuentaBancoBean cuentaBancoBean;
    private ConceptoBean conceptoBean;
    private BigDecimal monto;
    private BigDecimal dscto;
    private CodigoBean idTipoDscto;//idCodigo
    private BigDecimal montoPagado;
    private Integer cuentaD;
    private Integer cuentaH;
    private Integer cr;
    private BigDecimal montoSoles;
    private BigDecimal montoDolares;
    private String referencia;
    private ProgramacionBean programacionBean;
    private ProgramacionDsctoBean programacionBeanDscto;
    private Integer cantidad;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String glosa;
    private Integer idProcesoBancoEnv;
    private Integer idProcesoBancoRec;
    //ayuda 
    private BigDecimal dsctoTipoDicente;
    private BigDecimal montoConDscto;
    private Boolean estadoReg;
    private Integer estadoRegIng;
    private Integer flgImpreso;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdPagoBanco() {
        return idPagoBanco;
    }

    public void setIdPagoBanco(Integer idPagoBanco) {
        this.idPagoBanco = idPagoBanco;
    }

    public CodigoBean getIdTipoServicioIngBean() {
        if (idTipoServicioIngBean == null) {
            idTipoServicioIngBean = new CodigoBean();
        }
        return idTipoServicioIngBean;
    }

    public void setIdTipoServicioIngBean(CodigoBean idTipoServicioIngBean) {
        this.idTipoServicioIngBean = idTipoServicioIngBean;
    }

    public CodigoBean getIdTipoDoc() {
        if (idTipoDoc == null) {
            idTipoDoc = new CodigoBean();
        }
        return idTipoDoc;
    }

    public void setIdTipoDoc(CodigoBean idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public ImpresoraBean getImpresoraBean() {
        if (impresoraBean == null) {
            impresoraBean = new ImpresoraBean();
        }
        return impresoraBean;
    }

    public void setImpresoraBean(ImpresoraBean impresoraBean) {
        this.impresoraBean = impresoraBean;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(String idDiscente) {
        this.idDiscente = idDiscente;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public CodigoBean getIdTipoLugarPago() {
        if (idTipoLugarPago == null) {
            idTipoLugarPago = new CodigoBean();
        }
        return idTipoLugarPago;
    }

    public void setIdTipoLugarPago(CodigoBean idTipoLugarPago) {
        this.idTipoLugarPago = idTipoLugarPago;
    }

    public CodigoBean getIdTipoModoPago() {
        if (idTipoModoPago == null) {
            idTipoModoPago = new CodigoBean();
        }
        return idTipoModoPago;
    }

    public void setIdTipoModoPago(CodigoBean idTipoModoPago) {
        this.idTipoModoPago = idTipoModoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public CodigoBean getIdTipoMoneda() {
        if (idTipoMoneda == null) {
            idTipoMoneda = new CodigoBean();
        }
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(CodigoBean idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
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

    public Integer getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(Integer cuentaD) {
        this.cuentaD = cuentaD;
    }

    public Integer getCuentaH() {
        return cuentaH;
    }

    public void setCuentaH(Integer cuentaH) {
        this.cuentaH = cuentaH;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
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

    public ProgramacionDsctoBean getProgramacionBeanDscto() {
        if (programacionBeanDscto == null) {
            programacionBeanDscto = new ProgramacionDsctoBean();
        }
        return programacionBeanDscto;
    }

    public void setProgramacionBeanDscto(ProgramacionDsctoBean programacionBeanDscto) {
        this.programacionBeanDscto = programacionBeanDscto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public BigDecimal getDsctoTipoDicente() {
        return dsctoTipoDicente;
    }

    public void setDsctoTipoDicente(BigDecimal dsctoTipoDicente) {
        this.dsctoTipoDicente = dsctoTipoDicente;
    }

    public BigDecimal getMontoConDscto() {
        return montoConDscto;
    }

    public void setMontoConDscto(BigDecimal montoConDscto) {
        this.montoConDscto = montoConDscto;
    }

    public Integer getIdProcesoBancoEnv() {
        return idProcesoBancoEnv;
    }

    public void setIdProcesoBancoEnv(Integer idProcesoBancoEnv) {
        this.idProcesoBancoEnv = idProcesoBancoEnv;
    }

    public Integer getIdProcesoBancoRec() {
        return idProcesoBancoRec;
    }

    public void setIdProcesoBancoRec(Integer idProcesoBancoRec) {
        this.idProcesoBancoRec = idProcesoBancoRec;
    }

    public Date getCreaFechaStatus() {
        return creaFechaStatus;
    }

    public void setCreaFechaStatus(Date creaFechaStatus) {
        this.creaFechaStatus = creaFechaStatus;
    }

    public String getCreaStatus() {
        return creaStatus;
    }

    public void setCreaStatus(String creaStatus) {
        this.creaStatus = creaStatus;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public CodigoBean getTipoStatusPagoBanco() {
        if (tipoStatusPagoBanco == null) {
            tipoStatusPagoBanco = new CodigoBean();
        }
        return tipoStatusPagoBanco;
    }

    public void setTipoStatusPagoBanco(CodigoBean tipoStatusPagoBanco) {
        this.tipoStatusPagoBanco = tipoStatusPagoBanco;
    }

    public Boolean getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Boolean estadoReg) {
        this.estadoReg = estadoReg;
    }

    public Integer getEstadoRegIng() {
        return estadoRegIng;
    }

    public void setEstadoRegIng(Integer estadoRegIng) {
        this.estadoRegIng = estadoRegIng;
    }

    public Integer getFlgImpreso() {
        return flgImpreso;
    }

    public void setFlgImpreso(Integer flgImpreso) {
        this.flgImpreso = flgImpreso;
    }

}
