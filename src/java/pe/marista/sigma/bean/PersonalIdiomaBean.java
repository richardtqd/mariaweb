package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class PersonalIdiomaBean implements Serializable {

    private Integer idPersonalIdioma;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean; //id
    private CodigoBean tipoIdiomaBean;//id
    private CodigoBean tipoNivelBean;//id nivel idioma
//    private EntidadBean entidadBean;//id
    private String centroEstudio;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private boolean collapsed = true;

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public Integer getIdPersonalIdioma() {
        return idPersonalIdioma;
    }

    public void setIdPersonalIdioma(Integer idPersonalIdioma) {
        this.idPersonalIdioma = idPersonalIdioma;
    }

//    public EntidadBean getEntidadBean() {
//        if (entidadBean == null) {
//            entidadBean = new EntidadBean();
//        }
//        return entidadBean;
//    }
//
//    public void setEntidadBean(EntidadBean entidadBean) {
//        this.entidadBean = entidadBean;
//    }
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

    public CodigoBean getTipoIdiomaBean() {
        if (tipoIdiomaBean == null) {
            tipoIdiomaBean = new CodigoBean();
        }
        return tipoIdiomaBean;
    }

    public void setTipoIdiomaBean(CodigoBean tipoIdiomaBean) {
        this.tipoIdiomaBean = tipoIdiomaBean;
    }

    public CodigoBean getTipoNivelBean() {
        if (tipoNivelBean == null) {
            tipoNivelBean = new CodigoBean();
        }
        return tipoNivelBean;
    }

    public void setTipoNivelBean(CodigoBean tipoNivelBean) {
        this.tipoNivelBean = tipoNivelBean;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public String getCentroEstudio() {
        return centroEstudio;
    }

    public void setCentroEstudio(String centroEstudio) {
        this.centroEstudio = centroEstudio;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
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

}
