package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class PersonalDesvinculacionBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;
    private Integer idPersonalDesvinculacion;
    private String motivo;
    private Date fechaDesvinculacion;
    private String cartaRenuncia;
    private String encuesta;
    private String cartaDespido;
    private String compensacionGracia;
    private String liquidacion;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
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

    public Integer getIdPersonalDesvinculacion() {
        return idPersonalDesvinculacion;
    }

    public void setIdPersonalDesvinculacion(Integer idPersonalDesvinculacion) {
        this.idPersonalDesvinculacion = idPersonalDesvinculacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaDesvinculacion() {
        return fechaDesvinculacion;
    }

    public void setFechaDesvinculacion(Date fechaDesvinculacion) {
        this.fechaDesvinculacion = fechaDesvinculacion;
    }

    public String getCartaRenuncia() {
        return cartaRenuncia;
    }

    public void setCartaRenuncia(String cartaRenuncia) {
        this.cartaRenuncia = cartaRenuncia;
    }

    public String getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(String encuesta) {
        this.encuesta = encuesta;
    }

    public String getCartaDespido() {
        return cartaDespido;
    }

    public void setCartaDespido(String cartaDespido) {
        this.cartaDespido = cartaDespido;
    }

    public String getCompensacionGracia() {
        return compensacionGracia;
    }

    public void setCompensacionGracia(String compensacionGracia) {
        this.compensacionGracia = compensacionGracia;
    }

    public String getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(String liquidacion) {
        this.liquidacion = liquidacion;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
}
