/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
//import pe.marista.sigma.managedBean.BaseMB;

/**
 *
 * @author MS-005
 */
public class ProcesoEnvioBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idProcesoEnvio;
    private ProcesoBancoBean procesoBancoBean;
    private EstudianteBean idDiscente;
    private String codigoDiscente;
    private String nomDiscente;
    private String infoRetorno;
    private Date fechaEmision;
    private Date fechaVenc;
    private Boolean moneda;
    private BigDecimal monto;
    private BigDecimal mora;
    private String tipoRegistro = "R";
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private ConceptoBean conceptoBean;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Ayuda
    private String idMoneda;
    private int codMoneda;
    private Integer total;//
    private Date fechaComodin;
    private Date fechaComodinIn;
    private Integer subProc;
    private Boolean flgEnvio = true;
    private Boolean flgGrabar;
    private String flgDeuda;
    private String anio;
    private String mes;
    private String dia;
    private String hora;
    private String minuto;
    private String segundo;
    private String statusEnvio;
    private String operacion;

    /* ENVIO POR OPERACION */
    private String uniNeg;
    private String ruc;
    private Integer idProcesoBanco;
    private String idEstudiante;
    private String codigo;
    private String nombres;
    private Date fechaIni;
    private Date fechaFin;
    private Integer idTipoConcepto;
    private Integer idConcepto;

    private String cuota;
    private Integer anios;
    private Integer meses;
    private CodigoBean tipoEstadoCta;
    private Integer valor;
    private Integer tipoMoneda;
    private String fecha;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdProcesoEnvio() {
        return idProcesoEnvio;
    }

    public void setIdProcesoEnvio(Integer idProcesoEnvio) {
        this.idProcesoEnvio = idProcesoEnvio;
    }

    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
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

//    public PersonaBean getDiscenteBean() {
//        if(discenteBean == null){
//            discenteBean = new PersonaBean();
//        }
//        return discenteBean;
//    }
//
//    public void setDiscenteBean(PersonaBean discenteBean) {
//        this.discenteBean = discenteBean;
//    }
    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
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

//    public EstudianteBean getEstudianteBean() {
//        if(estudianteBean == null){
//            estudianteBean = new EstudianteBean();
//        }
//        return estudianteBean;
//    }
//
//    public void setEstudianteBean(EstudianteBean estudianteBean) {
//        this.estudianteBean = estudianteBean;
//    }    
//    public EstudianteBean getDiscenteBean() {
//        if(discenteBean == null){
//            discenteBean = new EstudianteBean();
//        }
//        return discenteBean;
//    }
//
//    public void setDiscenteBean(EstudianteBean discenteBean) {
//        this.discenteBean = discenteBean;
//    }       
    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

//    public PersonaBean getDiscenteBean() {
//        if(discenteBean == null){
//            discenteBean = new PersonaBean();
//        }
//        return discenteBean;
//    }
//
//    public void setDiscenteBean(PersonaBean discenteBean) {
//        this.discenteBean = discenteBean;
//    }    
    public EstudianteBean getIdDiscente() {
        if (idDiscente == null) {
            idDiscente = new EstudianteBean();
        }
        return idDiscente;
    }

    public void setIdDiscente(EstudianteBean idDiscente) {
        this.idDiscente = idDiscente;
    }

    public String getNomDiscente() {
        return nomDiscente;
    }

    public void setNomDiscente(String nomDiscente) {
        this.nomDiscente = nomDiscente;
    }

    public String getInfoRetorno() {
        return infoRetorno;
    }

    public void setInfoRetorno(String infoRetorno) {
        this.infoRetorno = infoRetorno;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Boolean getMoneda() {
        return moneda;
    }

    public void setMoneda(Boolean moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }

//    public EntidadBean getEntidadBean() {
//        if(entidadBean == null){
//            entidadBean = new EntidadBean();
//        }
//        return entidadBean;
//    }
//
//    public void setEntidadBean(EntidadBean entidadBean) {
//        this.entidadBean = entidadBean;
//    }    
    public String getIdMoneda() {
        if (moneda != null) {
            if (moneda == false) {
                idMoneda = "Soles";
            } else {
                idMoneda = "Dolares";
            }
        }
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public int getCodMoneda() {
        if (moneda != null) {
            if (moneda == true) {
                codMoneda = 1;
                return codMoneda;
            } else {
                codMoneda = 0;
                return codMoneda;
            }
        }
        return codMoneda;
    }

    public void setCodMoneda(int codMoneda) {
        this.codMoneda = codMoneda;
    }

    public Integer getTotal() {
        if (total == null) {
            total = 0;
        }
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getFechaComodin() {
        return fechaComodin;
    }

    public void setFechaComodin(Date fechaComodin) {
        this.fechaComodin = fechaComodin;
    }

    public Date getFechaComodinIn() {
        return fechaComodinIn;
    }

    public void setFechaComodinIn(Date fechaComodinIn) {
        this.fechaComodinIn = fechaComodinIn;
    }

    public Integer getSubProc() {
        return subProc;
    }

    public void setSubProc(Integer subProc) {
        this.subProc = subProc;
    }

    public Boolean getFlgEnvio() {
        return flgEnvio;
    }

    public void setFlgEnvio(Boolean flgEnvio) {
        this.flgEnvio = flgEnvio;
    }

    public String getFlgDeuda() {
        return flgDeuda;
    }

    public void setFlgDeuda(String flgDeuda) {
        this.flgDeuda = flgDeuda;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Boolean getFlgGrabar() {
        return flgGrabar;
    }

    public void setFlgGrabar(Boolean flgGrabar) {
        this.flgGrabar = flgGrabar;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public String getSegundo() {
        return segundo;
    }

    public void setSegundo(String segundo) {
        this.segundo = segundo;
    }

    public String getStatusEnvio() {
        if (flgEnvio != null) {
            if (flgEnvio.equals(true)) {
                statusEnvio = "ENVIADO";
                return statusEnvio;
            } else {
                statusEnvio = "NO ENVIADO";
                return statusEnvio;
            }
        }
        return statusEnvio;
    }

    public void setStatusEnvio(String statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getCodigoDiscente() {
        return codigoDiscente;
    }

    public void setCodigoDiscente(String codigoDiscente) {
        this.codigoDiscente = codigoDiscente;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public Integer getAnios() {
        return anios;
    }

    public void setAnios(Integer anios) {
        this.anios = anios;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }

    public CodigoBean getTipoEstadoCta() {
        if (tipoEstadoCta == null) {
            tipoEstadoCta = new CodigoBean();
        }
        return tipoEstadoCta;
    }

    public void setTipoEstadoCta(CodigoBean tipoEstadoCta) {
        this.tipoEstadoCta = tipoEstadoCta;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Integer tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
