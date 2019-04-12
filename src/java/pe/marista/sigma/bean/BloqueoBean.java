package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class BloqueoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idBloqueo;
    private Integer anio;
    private Integer flgConf;
    private String descripcion;
    private String creapor;
    private Date creaFecha;
    private String modiPor;
    private Date modiFecha;
    private Integer posicion;
    private CodigoBean tipoStatusBloqueo;
    private Integer idResponsable;
    private PersonalBean personalBean;
    private String objFile;

    //AYUDA
    private String estado;
    private Integer tipoBloqueo;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getIdBloqueo() {
        return idBloqueo;
    }

    public void setIdBloqueo(Integer idBloqueo) {
        this.idBloqueo = idBloqueo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getFlgConf() {
        return flgConf;
    }

    public void setFlgConf(Integer flgConf) {
        this.flgConf = flgConf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreapor() {
        return creapor;
    }

    public void setCreapor(String creapor) {
        this.creapor = creapor;
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

    public Date getModiFecha() {
        return modiFecha;
    }

    public void setModiFecha(Date modiFecha) {
        this.modiFecha = modiFecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public CodigoBean getTipoStatusBloqueo() {
        if (tipoStatusBloqueo == null) {
            tipoStatusBloqueo = new CodigoBean();
        }
        return tipoStatusBloqueo;
    }

    public void setTipoStatusBloqueo(CodigoBean tipoStatusBloqueo) {
        this.tipoStatusBloqueo = tipoStatusBloqueo;
    }

    public Integer getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Integer idResponsable) {
        this.idResponsable = idResponsable;
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

    public String getObjFile() {
        return objFile;
    }

    public void setObjFile(String objFile) {
        this.objFile = objFile;
    }

    public Integer getTipoBloqueo() {
        return tipoBloqueo;
    }

    public void setTipoBloqueo(Integer tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

}
