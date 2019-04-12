/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author MS-005
 */
public class ProcesoRecuperacionBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private ProcesoBancoBean procesoBancoBean;
    private PersonaBean personaBean;
    private EstudianteBean estudianteBean;
    private ConceptoBean conceptoBean;
//    private Boolean moneda;
    private Integer moneda;
    private String idMoneda;
    private Float monto;
    private Float mora;
//    private Float montoPagado;
    private Float montoRecup;
    private Float montoEnv;
    private Date fechaPago;
//    private Date fechaPago;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private Integer cuentaAfiliada;
    private String datoAdicionalDep;
    private Date fechaVen;
    private String datoPension;
//    private String fechaVen;
    private Integer agencia;
    private Integer numOperacion;
    private String referencia;
    private String terminal;
    private String medioAtencion;
    private Time horaAtencion;
//    private Boolean flgConcilia;
    private Integer flgConcilia;
    private String concilia;
    private String dia;
    private String mes;
    private String anio;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiver;
    private Integer idProcesoRecup;
    private Integer total;

    private String file;
    private boolean comodin = false;
    private String operacion;
    private BigDecimal montoEnviado;
    private String hora;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
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

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
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

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getMora() {
        return mora;
    }

    public void setMora(Float mora) {
        this.mora = mora;
    }

//    public Date getFechaPago() {
//        return fechaPago;
//    }
//
//    public void setFechaPago(Date fechaPago) {
//        this.fechaPago = fechaPago;
//    }
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

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiver() {
        return modiver;
    }

    public void setModiver(String modiver) {
        this.modiver = modiver;
    }

    public Integer getCuentaAfiliada() {
        return cuentaAfiliada;
    }

    public void setCuentaAfiliada(Integer cuentaAfiliada) {
        this.cuentaAfiliada = cuentaAfiliada;
    }

    public String getDatoAdicionalDep() {
        return datoAdicionalDep;
    }

    public void setDatoAdicionalDep(String datoAdicionalDep) {
        this.datoAdicionalDep = datoAdicionalDep;
    }

//    public Date getFechaVen() {
//        return fechaVen;
//    }
//
//    public void setFechaVen(Date fechaVen) {
//        this.fechaVen = fechaVen;
//    }
    public Integer getAgencia() {
        return agencia;
    }

    public void setAgencia(Integer agencia) {
        this.agencia = agencia;
    }

    public Integer getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(Integer numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getMedioAtencion() {
        return medioAtencion;
    }

    public void setMedioAtencion(String medioAtencion) {
        this.medioAtencion = medioAtencion;
    }

    public Time getHoraAtencion() {
        return horaAtencion;
    }

    public void setHoraAtencion(Time horaAtencion) {
        this.horaAtencion = horaAtencion;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getIdProcesoRecup() {
        return idProcesoRecup;
    }

    public void setIdProcesoRecup(Integer idProcesoRecup) {
        this.idProcesoRecup = idProcesoRecup;
    }

//    public Boolean getMoneda() {
//        return moneda;
//    }
//
//    public void setMoneda(Boolean moneda) {
//        this.moneda = moneda;
//    }
    public Integer getMoneda() {
        return moneda;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public Float getMontoRecup() {
        return montoRecup;
    }

    public void setMontoRecup(Float montoRecup) {
        this.montoRecup = montoRecup;
    }

    public Float getMontoEnv() {
        return montoEnv;
    }

    public void setMontoEnv(Float montoEnv) {
        this.montoEnv = montoEnv;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaVen() {
        return fechaVen;
    }

    public void setFechaVen(Date fechaVen) {
        this.fechaVen = fechaVen;
    }

//    public Boolean getFlgConcilia() {
//        return flgConcilia;
//    }
//
//    public void setFlgConcilia(Boolean flgConcilia) {
//        this.flgConcilia = flgConcilia;
//    }
    public Integer getFlgConcilia() {
        return flgConcilia;
    }

    public void setFlgConcilia(Integer flgConcilia) {
        this.flgConcilia = flgConcilia;
    }

    public boolean isComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    public String getConcilia() {
        if (flgConcilia != null) {
            if (flgConcilia.equals(1)) {
                concilia = "PAGADO";
                return concilia;
            } else {
                concilia = "NO PAGADO";
                return concilia;
            }
        }
        if (flgConcilia == null) {
            concilia = "NO PAGADO";
            return concilia;
        }
        return concilia;
    }

    public void setConcilia(String concilia) {
        this.concilia = concilia;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getDatoPension() {
        return datoPension;
    }

    public void setDatoPension(String datoPension) {
        this.datoPension = datoPension;
    }

    public BigDecimal getMontoEnviado() {
        return montoEnviado;
    }

    public void setMontoEnviado(BigDecimal montoEnviado) {
        this.montoEnviado = montoEnviado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
 
}
