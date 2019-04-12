/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CuotaIngresoBean implements Serializable{
    
    private UnidadNegocioBean unidadNegocioBean;
    private Integer idCuotaIngreso;
    private CajaCuotaIngresoBean cajaCuotaIngresoBean;
    private String Impresora;
    private CodigoBean tipoDocBean;
    private String serie;
    private Integer nroDoc;
    private PersonaBean personaBean;
    private String discente;
    private Integer anio;
    private CodigoBean tipoLugarPagoBean;
    private CodigoBean tipoModoPago;
    private Date fechaPago;
    private CodigoBean tipoMonedaBean;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Double montoEfectivoSol;
    private Boolean flgAnulado;
    private CajaCuotaIngresoBean cajaCuotaIngresoAnulado;
    private ConceptoBean conceptoBean;
    private PlanContableBean cuentaD;
    private PlanContableBean cuentaH;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private String referencia;
    private String fechaPagoView;
    private CodigoBean tipoStatusDocIngBean;
    private String serienrodoc;
    private Date fechaInicio;
    private Date FechaFin;
    private String fechaVista;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean== null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdCuotaIngreso() {
        return idCuotaIngreso;
    }

    public void setIdCuotaIngreso(Integer idCuotaIngreso) {
        this.idCuotaIngreso = idCuotaIngreso;
    }

    public CajaCuotaIngresoBean getCajaCuotaIngresoBean() {
        if (cajaCuotaIngresoBean== null) {
            cajaCuotaIngresoBean= new CajaCuotaIngresoBean();
        }
        return cajaCuotaIngresoBean;
    }

    public void setCajaCuotaIngresoBean(CajaCuotaIngresoBean cajaCuotaIngresoBean) {
        this.cajaCuotaIngresoBean = cajaCuotaIngresoBean;
    }

    public String getImpresora() {
        return Impresora;
    }

    public void setImpresora(String Impresora) {
        this.Impresora = Impresora;
    }

    public CodigoBean getTipoDocBean() {
        if (tipoDocBean== null) {
            tipoDocBean= new CodigoBean();
        }
        return tipoDocBean;
    }

    public void setTipoDocBean(CodigoBean tipoDocBean) {
        this.tipoDocBean = tipoDocBean;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(Integer nroDoc) {
        this.nroDoc = nroDoc;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean==null) {
            personaBean= new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    } 

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public CodigoBean getTipoLugarPagoBean() {
        if (tipoLugarPagoBean==null) {
            tipoLugarPagoBean= new CodigoBean();
        }
        return tipoLugarPagoBean;
    }

    public void setTipoLugarPagoBean(CodigoBean tipoLugarPagoBean) {
        this.tipoLugarPagoBean = tipoLugarPagoBean;
    }

    public CodigoBean getTipoModoPago() {
        if (tipoModoPago== null) {
            tipoModoPago= new CodigoBean();
        }
        return tipoModoPago;
    }

    public void setTipoModoPago(CodigoBean tipoModoPago) {
        this.tipoModoPago = tipoModoPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean== null) {
            tipoMonedaBean= new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Double getMontoEfectivoSol() {
        return montoEfectivoSol;
    }

    public void setMontoEfectivoSol(Double montoEfectivoSol) {
        this.montoEfectivoSol = montoEfectivoSol;
    }

    public Boolean getFlgAnulado() {
        return flgAnulado;
    }

    public void setFlgAnulado(Boolean flgAnulado) {
        this.flgAnulado = flgAnulado;
    }

    public CajaCuotaIngresoBean getCajaCuotaIngresoAnulado() {
        if (cajaCuotaIngresoAnulado==null) {
            cajaCuotaIngresoAnulado= new CajaCuotaIngresoBean();
        }
        return cajaCuotaIngresoAnulado;
    }

    public void setCajaCuotaIngresoAnulado(CajaCuotaIngresoBean cajaCuotaIngresoAnulado) {
        this.cajaCuotaIngresoAnulado = cajaCuotaIngresoAnulado;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean==null) {
            conceptoBean= new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public PlanContableBean getCuentaD() {
        if (cuentaD==null) {
            cuentaD= new PlanContableBean();
        }
        return cuentaD;
    }

    public void setCuentaD(PlanContableBean cuentaD) {
        this.cuentaD = cuentaD;
    }

    public PlanContableBean getCuentaH() {
        if (cuentaD==null) {
            cuentaD= new PlanContableBean();
        }
        return cuentaH;
    }

    public void setCuentaH(PlanContableBean cuentaH) {
        if (cuentaH==null) {
            cuentaH= new PlanContableBean();
        }
        this.cuentaH = cuentaH;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean==null) {
            centroResponsabilidadBean= new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public String getFechaPagoView() {
        return fechaPagoView;
    }

    public void setFechaPagoView(String fechaPagoView) {
        this.fechaPagoView = fechaPagoView;
    }

    public CodigoBean getTipoStatusDocIngBean() {
        if (tipoStatusDocIngBean==null) {
            tipoStatusDocIngBean= new CodigoBean();
        }
        return tipoStatusDocIngBean;
    }

    public void setTipoStatusDocIngBean(CodigoBean tipoStatusDocIngBean) {
        this.tipoStatusDocIngBean = tipoStatusDocIngBean;
    }

    public String getSerienrodoc() {
        return serienrodoc;
    }

    public void setSerienrodoc(String serienrodoc) {
        this.serienrodoc = serienrodoc;
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

    public String getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(String fechaVista) {
        this.fechaVista = fechaVista;
    }

   
 
       
    
}
