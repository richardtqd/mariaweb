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
public class DocIngresoBean implements Serializable {

    private Integer idDocIngreso;
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private ImpresoraCajaBean impresoraCajaBean;//impresora.impresora "impresora" -- idTipoDoc.idTipoDoc.idCodigo "idTipoDoc"-- cajaBean.idCaja "idCaja"-- impresora.serie "serie"-- impresora.actual "nroDoc"
    private PersonaBean idDiscente; // idPersona "idDiscente" -- nombreCompleto "discente" 
//    private PersonalBean personalBean; // idPersonal 
    private CuentasPorCobrarBean cuentasPorCobrarBean; // matriculaBean.seccion "seccion"-- matriculaBean.gradoAcademicoBean.idGradoAcademico "idGradoAcademico" -- estudianteBecaBean.idEstudianteBeca "idEstudianteBeca"
    private PersonaBean familiarBean; //idPersona "idResPago"-- nombreCompleto "resPago"
    private CajaGenBean cajaGenBean; //idCajaGen "idCajaGen"
    private String ruc;
    private String seccion;
    private Integer anio;
    private Integer idGradoAcademico;
    private String nombreGradoAca;
    private String direccion;
    private String telefono;
    private String obs;
    private CodigoBean idTipoLugarPago;//idCodigo
    private CodigoBean idTipoModoPago;//idCodigo   
    private CodigoBean idTipoDoc;//idCodigo   
    private Date fechaPago;
    private CodigoBean idTipoMoneda;//idCodigo
    private boolean status;
    private boolean chequeo;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date creaFechaStatus;
    private String creaStatus;
    private Integer flgAnulado;
    private String nroDocIni;
    private String nroDocFin;
    private String nroRecIni;

    //ayuda
    private String codDiscente;
    private String nombreDiscente;
    private String uniNeg;
    private String serie;
    private String serieNroDoc;
    private String nroDoc;
    private Date fechaInicio;
    private Date FechaFin;
    private Double montoPagado;
    private Double tc;
    private String fechaPagoVista;
    private String tipoMoneda;
    private String caja;
    private String obsAyuda;

    //cambios para pogar POS y efectivo
    private Double montoPos1;
    private Double montoPos2;
    private Double montoEfectivoSol;
    private Double montoEfectivoDol;
    private Double montoPagadoSol;
    private Double montoPagadoDol;

    private TipoCambioBean tipoCambioBean;
    private CodigoBean tipoStatusDocIng;//idCodigo

    private Boolean estadoReg;
    //ayuda
    private String ids;

    private Integer idCtaxCobrar;
    //Rendicion
    private PersonalBean personalBean;
    private String flgMasivo;

    //Ayuda Reporte Banco
    private String concepto;
    private String grado;
    private Integer anioRep;
    private String mesVista;
    private String codigo;
    private Integer flgMas;
    private BigDecimal mora;
    private Integer flgImpresion;
    private String vistaImpresion;

    /* CAMPO DE AYUDA */
    private String nomResPago;
    private String idRespPago;
    private Integer idEstudianteBeca;
    private String beca;
    private Integer idCajaGenAnulado;
    private String nombreCajaAnulado;

    private Boolean flgCajaGenNull;
    private Boolean flgCajaGenAnuladoNull;
    private Boolean flgFechaPagoNull;
    private Boolean flgFechaEstNull;
    private Integer estadoRegIng;

    private Integer mes;
    private String vistaTipo;
    //impresion
    private Integer estadoImpresion;
    private Boolean flgImpresionMasiva;
    private Date fechaImpresion;
    
    //Para Talleres
    private String referenciaCuenta;

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
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

    public ImpresoraCajaBean getImpresoraCajaBean() {
        if (impresoraCajaBean == null) {
            impresoraCajaBean = new ImpresoraCajaBean();
        }
        return impresoraCajaBean;
    }

    public void setImpresoraCajaBean(ImpresoraCajaBean impresoraCajaBean) {
        this.impresoraCajaBean = impresoraCajaBean;
    }

    public PersonaBean getIdDiscente() {
        if (idDiscente == null) {
            idDiscente = new PersonaBean();
        }
        return idDiscente;
    }

    public void setIdDiscente(PersonaBean idDiscente) {
        this.idDiscente = idDiscente;
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

    public PersonaBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new PersonaBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(PersonaBean familiarBean) {
        this.familiarBean = familiarBean;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isChequeo() {
        return chequeo;
    }

    public void setChequeo(boolean chequeo) {
        this.chequeo = chequeo;
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

    public CajaGenBean getCajaGenBean() {
        if (cajaGenBean == null) {
            cajaGenBean = new CajaGenBean();
        }
        return cajaGenBean;
    }

    public void setCajaGenBean(CajaGenBean cajaGenBean) {
        this.cajaGenBean = cajaGenBean;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public TipoCambioBean getTipoCambioBean() {
        if (tipoCambioBean == null) {
            tipoCambioBean = new TipoCambioBean();
        }
        return tipoCambioBean;
    }

    public void setTipoCambioBean(TipoCambioBean tipoCambioBean) {
        this.tipoCambioBean = tipoCambioBean;
    }

    public CodigoBean getTipoStatusDocIng() {
        if (tipoStatusDocIng == null) {
            tipoStatusDocIng = new CodigoBean();
        }
        return tipoStatusDocIng;
    }

    public void setTipoStatusDocIng(CodigoBean tipoStatusDocIng) {
        this.tipoStatusDocIng = tipoStatusDocIng;
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

    public String getCodDiscente() {
        return codDiscente;
    }

    public void setCodDiscente(String codDiscente) {
        this.codDiscente = codDiscente;
    }

    public String getNombreDiscente() {
        return nombreDiscente;
    }

    public void setNombreDiscente(String nombreDiscente) {
        this.nombreDiscente = nombreDiscente;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
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

    public Double getTc() {
        return tc;
    }

    public void setTc(Double tc) {
        this.tc = tc;
    }

    public String getSerieNroDoc() {
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public String getFechaPagoVista() {
        return fechaPagoVista;
    }

    public void setFechaPagoVista(String fechaPagoVista) {
        this.fechaPagoVista = fechaPagoVista;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
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

    public String getObsAyuda() {
        return obsAyuda;
    }

    public void setObsAyuda(String obsAyuda) {
        this.obsAyuda = obsAyuda;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Double getMontoPos1() {
        return montoPos1;
    }

    public void setMontoPos1(Double montoPos1) {
        this.montoPos1 = montoPos1;
    }

    public Double getMontoPos2() {
        return montoPos2;
    }

    public void setMontoPos2(Double montoPos2) {
        this.montoPos2 = montoPos2;
    }

    public Double getMontoEfectivoSol() {
        return montoEfectivoSol;
    }

    public void setMontoEfectivoSol(Double montoEfectivoSol) {
        this.montoEfectivoSol = montoEfectivoSol;
    }

    public Double getMontoEfectivoDol() {
        return montoEfectivoDol;
    }

    public void setMontoEfectivoDol(Double montoEfectivoDol) {
        this.montoEfectivoDol = montoEfectivoDol;
    }

    public Double getMontoPagadoSol() {
        return montoPagadoSol;
    }

    public void setMontoPagadoSol(Double montoPagadoSol) {
        this.montoPagadoSol = montoPagadoSol;
    }

    public Double getMontoPagadoDol() {
        return montoPagadoDol;
    }

    public void setMontoPagadoDol(Double montoPagadoDol) {
        this.montoPagadoDol = montoPagadoDol;
    }

    public Integer getIdCtaxCobrar() {
        return idCtaxCobrar;
    }

    public void setIdCtaxCobrar(Integer idCtaxCobrar) {
        this.idCtaxCobrar = idCtaxCobrar;
    }

    public Boolean getEstadoReg() {
        return estadoReg;
    }

    public void setEstadoReg(Boolean estadoReg) {
        this.estadoReg = estadoReg;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getFlgMasivo() {
        return flgMasivo;
    }

    public void setFlgMasivo(String flgMasivo) {
        this.flgMasivo = flgMasivo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public Integer getAnioRep() {
        return anioRep;
    }

    public void setAnioRep(Integer anioRep) {
        this.anioRep = anioRep;
    }

    public Integer getFlgAnulado() {
        return flgAnulado;
    }

    public void setFlgAnulado(Integer flgAnulado) {
        this.flgAnulado = flgAnulado;
    }

    public String getNroDocIni() {
        return nroDocIni;
    }

    public void setNroDocIni(String nroDocIni) {
        this.nroDocIni = nroDocIni;
    }

    public String getNroDocFin() {
        return nroDocFin;
    }

    public void setNroDocFin(String nroDocFin) {
        this.nroDocFin = nroDocFin;
    }

    public String getNroRecIni() {
        return nroRecIni;
    }

    public void setNroRecIni(String nroRecIni) {
        this.nroRecIni = nroRecIni;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMesVista() {
        return mesVista;
    }

    public void setMesVista(String mesVista) {
        this.mesVista = mesVista;
    }

    public Integer getFlgMas() {
        return flgMas;
    }

    public void setFlgMas(Integer flgMas) {
        this.flgMas = flgMas;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

    public Integer getFlgImpresion() {
        return flgImpresion;
    }

    public void setFlgImpresion(Integer flgImpresion) {
        this.flgImpresion = flgImpresion;
    }

    public String getNomResPago() {
        return nomResPago;
    }

    public void setNomResPago(String nomResPago) {
        this.nomResPago = nomResPago;
    }

    public String getVistaImpresion() {
        return vistaImpresion;
    }

    public void setVistaImpresion(String vistaImpresion) {
        this.vistaImpresion = vistaImpresion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getIdRespPago() {
        return idRespPago;
    }

    public void setIdRespPago(String idRespPago) {
        this.idRespPago = idRespPago;
    }

    public Integer getIdEstudianteBeca() {
        return idEstudianteBeca;
    }

    public void setIdEstudianteBeca(Integer idEstudianteBeca) {
        this.idEstudianteBeca = idEstudianteBeca;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public Integer getIdCajaGenAnulado() {
        return idCajaGenAnulado;
    }

    public void setIdCajaGenAnulado(Integer idCajaGenAnulado) {
        this.idCajaGenAnulado = idCajaGenAnulado;
    }

    public String getNombreCajaAnulado() {
        return nombreCajaAnulado;
    }

    public void setNombreCajaAnulado(String nombreCajaAnulado) {
        this.nombreCajaAnulado = nombreCajaAnulado;
    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getNombreGradoAca() {
        return nombreGradoAca;
    }

    public void setNombreGradoAca(String nombreGradoAca) {
        this.nombreGradoAca = nombreGradoAca;
    }

    public Boolean getFlgCajaGenNull() {
        return flgCajaGenNull;
    }

    public void setFlgCajaGenNull(Boolean flgCajaGenNull) {
        this.flgCajaGenNull = flgCajaGenNull;
    }

    public Boolean getFlgCajaGenAnuladoNull() {
        return flgCajaGenAnuladoNull;
    }

    public void setFlgCajaGenAnuladoNull(Boolean flgCajaGenAnuladoNull) {
        this.flgCajaGenAnuladoNull = flgCajaGenAnuladoNull;
    }

    public Boolean getFlgFechaPagoNull() {
        return flgFechaPagoNull;
    }

    public void setFlgFechaPagoNull(Boolean flgFechaPagoNull) {
        this.flgFechaPagoNull = flgFechaPagoNull;
    }

    public Boolean getFlgFechaEstNull() {
        return flgFechaEstNull;
    }

    public void setFlgFechaEstNull(Boolean flgFechaEstNull) {
        this.flgFechaEstNull = flgFechaEstNull;
    }

    public Integer getEstadoRegIng() {
        return estadoRegIng;
    }

    public void setEstadoRegIng(Integer estadoRegIng) {
        this.estadoRegIng = estadoRegIng;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getVistaTipo() {
        return vistaTipo;
    }

    public void setVistaTipo(String vistaTipo) {
        this.vistaTipo = vistaTipo;
    }

    public Integer getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(Integer estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }

    public Boolean getFlgImpresionMasiva() {
        return flgImpresionMasiva;
    }

    public void setFlgImpresionMasiva(Boolean flgImpresionMasiva) {
        this.flgImpresionMasiva = flgImpresionMasiva;
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public String getReferenciaCuenta() {
        return referenciaCuenta;
    }

    public void setReferenciaCuenta(String referenciaCuenta) {
        this.referenciaCuenta = referenciaCuenta;
    }

}
