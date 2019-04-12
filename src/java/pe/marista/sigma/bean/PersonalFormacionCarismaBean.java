package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import javax.xml.registry.infomodel.PersonName;

public class PersonalFormacionCarismaBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalFormacionCarisma;
    private String nombreInstitucion;
    private CodigoBean tipoFormacion;
    private String horas;
    private Date fechaIni;
    private Date fechaFin;
    private String obs;
    private PaisBean paisBean;
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
    
    public String getNombreInstitucion() {
        return nombreInstitucion;
    }

    public void setNombreInstitucion(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public CodigoBean getTipoFormacion() {
        if (tipoFormacion==null) {
            tipoFormacion= new CodigoBean();
        }
        return tipoFormacion;
    }

    public void setTipoFormacion(CodigoBean tipoFormacion) {
        this.tipoFormacion = tipoFormacion;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public PaisBean getPaisBean() {
        if (paisBean==null) {
            paisBean= new PaisBean();
        }
        return paisBean;
    }

    public void setPaisBean(PaisBean paisBean) {
        this.paisBean = paisBean;
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

    public Integer getIdPersonalFormacionCarisma() {
        return idPersonalFormacionCarisma;
    }

    public void setIdPersonalFormacionCarisma(Integer idPersonalFormacionCarisma) {
        this.idPersonalFormacionCarisma = idPersonalFormacionCarisma;
    }
    
}
