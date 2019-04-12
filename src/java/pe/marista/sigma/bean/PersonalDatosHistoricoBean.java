package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PersonalDatosHistoricoBean implements Serializable {
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalHistorico;
    private String direccion;
    private CodigoBean tipoEstadoCivil;
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

    public Integer getIdPersonalHistorico() {
        return idPersonalHistorico;
    }

    public void setIdPersonalHistorico(Integer idPersonalHistorico) {
        this.idPersonalHistorico = idPersonalHistorico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CodigoBean getTipoEstadoCivil() {
        if (tipoEstadoCivil==null) {
            tipoEstadoCivil= new CodigoBean();
        }
        return tipoEstadoCivil;
    }

    public void setTipoEstadoCivil(CodigoBean tipoEstadoCivil) {
        this.tipoEstadoCivil = tipoEstadoCivil;
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
    
}
