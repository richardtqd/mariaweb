package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PersonalVoluntariadoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalVoluntariado;
    private Date fechaVoluntariado;
    private String descripcion;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

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

    public Integer getIdPersonalVoluntariado() {
        return idPersonalVoluntariado;
    }

    public void setIdPersonalVoluntariado(Integer idPersonalVoluntariado) {
        this.idPersonalVoluntariado = idPersonalVoluntariado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Date getFechaVoluntariado() {
        return fechaVoluntariado;
    }

    public void setFechaVoluntariado(Date fechaVoluntariado) {
        this.fechaVoluntariado = fechaVoluntariado;
    }
}
