/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS002
 */
public class CuentasPorCobrarBean implements Serializable {

    private Integer idCtasXCobrar;
    private Integer anio;
    private Integer mes;
    private BigDecimal monto;
    private BigDecimal tasaInteres;
    private BigDecimal mora;
    private BigDecimal dscto;
    private BigDecimal montoPagado;
    private BigDecimal deuda;
    private Date fechaVenc;
    private Date fechaPago;
    private BigDecimal dsctoBeca;
    private String docRefProvision;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private EstudianteBean estudianteBean;//idEstudiante
    private MatriculaBean matriculaBean;//idMatricula    
    private ConceptoBean conceptoBean;//idConcepto
    private CodigoBean idTipoStatusCtaCte;//idCodigo
    private CentroResponsabilidadBean centroResponsabilidadBean;//cr
    private PlanContableBean cuentaD;//cuenta
    private PlanContableBean cuentaH;//cuenta
    private DocIngresoBean docIngresoBean;//idDocIngreso  
    private CodigoBean idTipoMoneda;//idCodigo   
    private CodigoBean idTipoDscto;//idCodigo
    private CodigoBean idTipoMotivoDscto; //idCodigo     
    private EstudianteBecaBean estudianteBecaBean;//idEstudianteBeca
    private CodigoBean idTipoBeca;//idCodigo     
    private ProcesoEnvioBean procesoEnvioBean;//idProcesoEnvio
    private ProcesoRecuperacionBean procesoRecuperacionBean;//idProcesoRecup

    private BigDecimal condonacion;
    private CodigoBean idTipoMotivoCondonacion;
    private String check = "false";
    private String grado;
    private String seccion;
    private String nomMes;
    private String alerta;
    private String disabled;
    private BigDecimal montoAPagar;
    private String meses;
    private String dias;
    private String anios;
    private Boolean flgEnvio;
    private Boolean flgRecGenerado;

    //Ayuda
    private Integer envios;
    private Integer totalEnvios;
    private Integer totalErroneos;
    private Integer dia;
    private String statusProvision;
    private Boolean flgDistinct;
    private String fechaActual;
    private String fechaTope;
    private String estadoEst;
    private String creaDscto;
    private String obs;
    private Date dsctoFecha;
    private Integer idEstadoCuenta;

    private Date fechaInicio;
    private Date fechaFin;
    private String fechaPagoVista;
    private Boolean estadoConcilia;
    private Double porcentajeDscto;
    private Double tasainteres;

    private Boolean flgFechaPagoNull;
    private Boolean flgFechaDsctoNull;
    private Boolean flgFechaVencNull;
    private Boolean idProcesoBancoNull;

    private Integer idProcesoBanco;
    private String nombreProcesoBanco;
    private String montoPalabras;
    private String fechaPalabras;
    private String nomBanco;
    private Integer tipoRecep;
    private String correoRec;

    private Date horaCorte;
    private String referenciaCuenta;

    private String nomSeccion;
    
    //Para ordenar la lista
    private Integer orden;
    private Integer recibosMora;
    
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EstudianteBecaBean getEstudianteBecaBean() {
        if (estudianteBecaBean == null) {
            estudianteBecaBean = new EstudianteBecaBean();
        }
        return estudianteBecaBean;
    }

    public void setEstudianteBecaBean(EstudianteBecaBean estudianteBecaBean) {
        this.estudianteBecaBean = estudianteBecaBean;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
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

    public DocIngresoBean getDocIngresoBean() {
        if (docIngresoBean == null) {
            docIngresoBean = new DocIngresoBean();
        }
        return docIngresoBean;
    }

    public void setDocIngresoBean(DocIngresoBean docIngresoBean) {
        this.docIngresoBean = docIngresoBean;
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

    public BigDecimal getMonto() {
        if (monto == null) {
            monto = new BigDecimal(0.0);
        }
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMontoPagado() {
        if (montoPagado == null) {
            montoPagado = new BigDecimal(0.0);
        }
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public BigDecimal getDeuda() {
        if (deuda == null) {
            deuda = new BigDecimal(0.0);
        }
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    public BigDecimal getMora() {
        if (mora == null) {
            mora = new BigDecimal(0.0);
        }
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getDscto() {
        if (dscto == null) {
            dscto = new BigDecimal(0.0);
        }
        return dscto;
    }

    public void setDscto(BigDecimal dscto) {
        this.dscto = dscto;
    }

    public BigDecimal getCondonacion() {
        if (condonacion == null) {
            condonacion = new BigDecimal(0.0);
        }
        return condonacion;
    }

    public void setCondonacion(BigDecimal condonacion) {
        this.condonacion = condonacion;
    }

    public CodigoBean getIdTipoMotivoCondonacion() {
        if (idTipoMotivoCondonacion == null) {
            idTipoMotivoCondonacion = new CodigoBean();
        }
        return idTipoMotivoCondonacion;
    }

    public void setIdTipoMotivoCondonacion(CodigoBean idTipoMotivoCondonacion) {
        this.idTipoMotivoCondonacion = idTipoMotivoCondonacion;
    }

    public String getDocRefProvision() {
        return docRefProvision;
    }

    public void setDocRefProvision(String docRefProvision) {
        this.docRefProvision = docRefProvision;
    }

    public String getNomMes() {
        if (mes != null) {
            if (mes == 1) {
                nomMes = MaristaConstantes.ENERO;
                return nomMes;
            }
            if (mes == 2) {
                nomMes = MaristaConstantes.FEBRERO;
                return nomMes;
            }
            if (mes == 3) {
                nomMes = MaristaConstantes.MARZO;
                return nomMes;
            }
            if (mes == 4) {
                nomMes = MaristaConstantes.ABRIL;
                return nomMes;
            }
            if (mes == 5) {
                nomMes = MaristaConstantes.MAYO;
                return nomMes;
            }
            if (mes == 6) {
                nomMes = MaristaConstantes.JUNIO;
                return nomMes;
            }
            if (mes == 7) {
                nomMes = MaristaConstantes.JULIO;
                return nomMes;
            }
            if (mes == 8) {
                nomMes = MaristaConstantes.AGOSTO;
                return nomMes;
            }
            if (mes == 9) {
                nomMes = MaristaConstantes.SETIEMBRE;
                return nomMes;
            }
            if (mes == 10) {
                nomMes = MaristaConstantes.OCTUBRE;
                return nomMes;
            }
            if (mes == 11) {
                nomMes = MaristaConstantes.NOVIEMBRE;
                return nomMes;
            }
            if (mes == 12) {
                nomMes = MaristaConstantes.DICIEMBRE;
                return nomMes;
            }
        } else {
            nomMes = MaristaConstantes.FEBRERO;
            return nomMes;
        }

        return nomMes;
    }

    public CodigoBean getIdTipoStatusCtaCte() {
        if (idTipoStatusCtaCte == null) {
            idTipoStatusCtaCte = new CodigoBean();
        }
        return idTipoStatusCtaCte;
    }

    public void setIdTipoStatusCtaCte(CodigoBean idTipoStatusCtaCte) {
        this.idTipoStatusCtaCte = idTipoStatusCtaCte;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
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

    public Integer getIdCtasXCobrar() {
        return idCtasXCobrar;
    }

    public void setIdCtasXCobrar(Integer idCtasXCobrar) {
        this.idCtasXCobrar = idCtasXCobrar;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
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

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
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
            if (getEstudianteBecaBean().getIdEstudianteBeca() == null) {
                idTipoMotivoDscto = getEstudianteBecaBean().getIdTipoMotivoBeca();
            }
        }
        return idTipoMotivoDscto;
    }

    public void setIdTipoMotivoDscto(CodigoBean idTipoMotivoDscto) {
        this.idTipoMotivoDscto = idTipoMotivoDscto;
    }

    public CodigoBean getIdTipoBeca() {
        if (idTipoBeca == null) {
            idTipoBeca = new CodigoBean();
        }
        return idTipoBeca;
    }

    public void setIdTipoBeca(CodigoBean idTipoBeca) {
        this.idTipoBeca = idTipoBeca;
    }

    public BigDecimal getDsctoBeca() {
        if (dsctoBeca == null) {
            dsctoBeca = new BigDecimal("0.0");
        }
        return dsctoBeca;
    }

    public void setDsctoBeca(BigDecimal dsctoBeca) {
        this.dsctoBeca = dsctoBeca;
    }

    public ProcesoEnvioBean getProcesoEnvioBean() {
        if (procesoEnvioBean == null) {
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public BigDecimal getMontoAPagar() {
        if (getIdTipoStatusCtaCte() != null) {
            if (getIdTipoStatusCtaCte().getCodigo() != null) {
                if (getIdTipoStatusCtaCte().getCodigo().equals(MaristaConstantes.COD_CTACTE_PAGADO)) {
                    montoAPagar = new BigDecimal(0.0);
                } else {
                    montoAPagar = getMonto().add(getMora()).subtract(getDsctoBeca()).subtract(getDscto());
                }
            } else {
                montoAPagar = new BigDecimal(0.0);
            }
        } else {
            montoAPagar = new BigDecimal(0.0);
        }
        return montoAPagar;
    }

    public String getMeses() {
        if (mes != null) {
            if (mes == 1) {
                meses = "Enero";
                return meses;
            }
            if (mes == 2) {
                meses = "Febrero";
                return meses;
            }
            if (mes == 3) {
                meses = "Marzo";
                return meses;
            }
            if (mes == 4) {
                meses = "Abril";
                return meses;
            }
            if (mes == 5) {
                meses = "Mayo";
                return meses;
            }
            if (mes == 6) {
                meses = "Junio";
                return meses;
            }
            if (mes == 7) {
                meses = "Julio";
                return meses;
            }
            if (mes == 8) {
                meses = "Agosto";
                return meses;
            }
            if (mes == 9) {
                meses = "Septiembre";
                return meses;
            }
            if (mes == 10) {
                meses = "Octubre";
                return meses;
            }
            if (mes == 11) {
                meses = "Noviembre";
                return meses;
            }
            if (mes == 12) {
                meses = "Diciembre";
                return meses;
            }

            if (mes != null) {
            } else {
                meses = "---";
                return meses;
            }
        }

        return meses;
    }

    public void setMeses(String meses) {
        this.meses = meses;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getAnios() {
        return anios;
    }

    public void setAnios(String anios) {
        this.anios = anios;
    }

    public Boolean getFlgEnvio() {
        return flgEnvio;
    }

    public void setFlgEnvio(Boolean flgEnvio) {
        this.flgEnvio = flgEnvio;
    }

    public Integer getEnvios() {
        return envios;
    }

    public void setEnvios(Integer envios) {
        this.envios = envios;
    }

    public Integer getTotalEnvios() {
        return totalEnvios;
    }

    public void setTotalEnvios(Integer totalEnvios) {
        this.totalEnvios = totalEnvios;
    }

    public Integer getTotalErroneos() {
        return totalErroneos;
    }

    public void setTotalErroneos(Integer totalErroneos) {
        this.totalErroneos = totalErroneos;
    }

    public String getStatusProvision() {
        if (flgEnvio != null) {
            if (flgEnvio.equals(true)) {
                statusProvision = "Provisionado";
                return statusProvision;
            } else {
                statusProvision = "No Provisionado";
                return statusProvision;
            }
        }
        return statusProvision;
    }

    public void setStatusProvision(String statusProvision) {
        this.statusProvision = statusProvision;
    }

    public Boolean getFlgDistinct() {
        return flgDistinct;
    }

    public void setFlgDistinct(Boolean flgDistinct) {
        this.flgDistinct = flgDistinct;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getFechaTope() {
        return fechaTope;
    }

    public void setFechaTope(String fechaTope) {
        this.fechaTope = fechaTope;
    }

    public String getCreaDscto() {
        return creaDscto;
    }

    public void setCreaDscto(String creaDscto) {
        this.creaDscto = creaDscto;
    }

    public Date getDsctoFecha() {
        return dsctoFecha;
    }

    public void setDsctoFecha(Date dsctoFecha) {
        this.dsctoFecha = dsctoFecha;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getEstadoEst() {
        return estadoEst;
    }

    public void setEstadoEst(String estadoEst) {
        this.estadoEst = estadoEst;
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

    public Integer getIdEstadoCuenta() {
        return idEstadoCuenta;
    }

    public void setIdEstadoCuenta(Integer idEstadoCuenta) {
        this.idEstadoCuenta = idEstadoCuenta;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Boolean getFlgRecGenerado() {
        return flgRecGenerado;
    }

    public void setFlgRecGenerado(Boolean flgRecGenerado) {
        this.flgRecGenerado = flgRecGenerado;
    }

    public String getFechaPagoVista() {
        return fechaPagoVista;
    }

    public void setFechaPagoVista(String fechaPagoVista) {
        this.fechaPagoVista = fechaPagoVista;
    }

    public Boolean getEstadoConcilia() {
        return estadoConcilia;
    }

    public void setEstadoConcilia(Boolean estadoConcilia) {
        this.estadoConcilia = estadoConcilia;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public Double getPorcentajeDscto() {
        return porcentajeDscto;
    }

    public void setPorcentajeDscto(Double porcentajeDscto) {
        this.porcentajeDscto = porcentajeDscto;
    }

    public Double getTasainteres() {
        return tasainteres;
    }

    public void setTasainteres(Double tasainteres) {
        this.tasainteres = tasainteres;
    }

    public Boolean getFlgFechaPagoNull() {
        return flgFechaPagoNull;
    }

    public void setFlgFechaPagoNull(Boolean flgFechaPagoNull) {
        this.flgFechaPagoNull = flgFechaPagoNull;
    }

    public Boolean getFlgFechaDsctoNull() {
        return flgFechaDsctoNull;
    }

    public void setFlgFechaDsctoNull(Boolean flgFechaDsctoNull) {
        this.flgFechaDsctoNull = flgFechaDsctoNull;
    }

    public Boolean getFlgFechaVencNull() {
        return flgFechaVencNull;
    }

    public void setFlgFechaVencNull(Boolean flgFechaVencNull) {
        this.flgFechaVencNull = flgFechaVencNull;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public String getNombreProcesoBanco() {
        return nombreProcesoBanco;
    }

    public void setNombreProcesoBanco(String nombreProcesoBanco) {
        this.nombreProcesoBanco = nombreProcesoBanco;
    }

    public Boolean getIdProcesoBancoNull() {
        return idProcesoBancoNull;
    }

    public void setIdProcesoBancoNull(Boolean idProcesoBancoNull) {
        this.idProcesoBancoNull = idProcesoBancoNull;
    }

    public Date getHoraCorte() {
        return horaCorte;
    }

    public void setHoraCorte(Date horaCorte) {
        this.horaCorte = horaCorte;
    }

    public String getMontoPalabras() {
        return montoPalabras;
    }

    public void setMontoPalabras(String montoPalabras) {
        this.montoPalabras = montoPalabras;
    }

    public String getFechaPalabras() {
        return fechaPalabras;
    }

    public void setFechaPalabras(String fechaPalabras) {
        this.fechaPalabras = fechaPalabras;
    }

    public String getNomBanco() {
        return nomBanco;
    }

    public void setNomBanco(String nomBanco) {
        this.nomBanco = nomBanco;
    }

    public Integer getTipoRecep() {
        return tipoRecep;
    }

    public void setTipoRecep(Integer tipoRecep) {
        this.tipoRecep = tipoRecep;
    }

    public String getCorreoRec() {
        return correoRec;
    }

    public void setCorreoRec(String correoRec) {
        this.correoRec = correoRec;
    }

    public String getReferenciaCuenta() {
        return referenciaCuenta;
    }

    public void setReferenciaCuenta(String referenciaCuenta) {
        this.referenciaCuenta = referenciaCuenta;
    }

    public String getNomSeccion() {
        return nomSeccion;
    }

    public void setNomSeccion(String nomSeccion) {
        this.nomSeccion = nomSeccion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getRecibosMora() {
        return recibosMora;
    }

    public void setRecibosMora(Integer recibosMora) {
        this.recibosMora = recibosMora;
    }

    

}
