package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PersonalDescansoMedicoBean implements Serializable {
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalDescansoMedico;
    private Date fechaIni;
    private Date fechaFin;
    private String motivo;
    private String diagnostico;
    private String nombreEntidadSalud;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Integer nroDias;
    private CodigoBean tipoDescansoBean;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean==null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean==null) {
            personalBean= new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Integer getIdPersonalDescansoMedico() {
        return idPersonalDescansoMedico;
    }

    public void setIdPersonalDescansoMedico(Integer idPersonalDescansoMedico) {
        this.idPersonalDescansoMedico = idPersonalDescansoMedico;
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getNombreEntidadSalud() {
        return nombreEntidadSalud;
    }

    public void setNombreEntidadSalud(String nombreEntidadSalud) {
        this.nombreEntidadSalud = nombreEntidadSalud;
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

    public Integer getNroDias() {
        return nroDias;
    }

    public void setNroDias(Integer nroDias) {
        this.nroDias = nroDias;
    }

    public CodigoBean getTipoDescansoBean() {
        if (tipoDescansoBean==null) {
            tipoDescansoBean= new CodigoBean();
        }
        return tipoDescansoBean;
    }

    public void setTipoDescansoBean(CodigoBean tipoDescansoBean) {
        this.tipoDescansoBean = tipoDescansoBean;
    }
    
}
