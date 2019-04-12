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
 * @author MS002
 */
public class DetEsquelaBean implements Serializable {

    private Integer idDetEsquela;
    private EsquelaBean esquelaBean;
    private UnidadNegocioBean unidadNegocioBean;
    private EstudianteBean estudianteBean;
    private MatriculaBean matriculaBean;
    private Integer status;
    private String mensaje;
    private String fechaActual;
    private Integer fechaFull;
    private String creaPor;
    private Date creaFecha;
    private String fecha;
    private String modiPor;
    private String modiVer;

    //NUEVOS CAMPOS
    private String idObjeto;
    private String objeto;
    private String asunto;
    private Integer idTipoRemitente;

    //Ayuda
    private String destino;
    private String destinoPer;
    private String correo;
    private Integer idTipoEstado;

    public EsquelaBean getEsquelaBean() {
        if (esquelaBean == null) {
            esquelaBean = new EsquelaBean();
        }
        return esquelaBean;
    }

    public void setEsquelaBean(EsquelaBean esquelaBean) {
        this.esquelaBean = esquelaBean;
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

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public Integer getIdDetEsquela() {
        return idDetEsquela;
    }

    public void setIdDetEsquela(Integer idDetEsquela) {
        this.idDetEsquela = idDetEsquela;
    }

    public Integer getFechaFull() {
        return fechaFull;
    }

    public void setFechaFull(Integer fechaFull) {
        this.fechaFull = fechaFull;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDestinoPer() {
        return destinoPer;
    }

    public void setDestinoPer(String destinoPer) {
        this.destinoPer = destinoPer;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(String idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getIdTipoRemitente() {
        return idTipoRemitente;
    }

    public void setIdTipoRemitente(Integer idTipoRemitente) {
        this.idTipoRemitente = idTipoRemitente;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

}
