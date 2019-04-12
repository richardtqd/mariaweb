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
public class EsquelaBean implements Serializable {

    private Integer idEsquela;
    private UnidadNegocioBean unidadNegocioBean;
    private String titulo;
    private String mensaje;
//    private EstudianteBean estudianteBean;
    private Integer flgRecEnvio;
    private String destino;
    private CodigoBean tipoEsquelaBean;
    private String mes;
    private String datoMes;
    private String nombreDestino;
    private String idDestino;
    private Integer tipoDestino;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Flg de Envios
    private Integer flgenviopapa;
    private Integer flgenviomama;
    private Integer flgenvioapo;
    private Integer flgenviorespago;
    private Integer num;
    private Integer numOk;
    private Integer numFa;

    private Integer tipoAccion = 1;

    public Integer getIdEsquela() {
        return idEsquela;
    }

    public void setIdEsquela(Integer idEsquela) {
        this.idEsquela = idEsquela;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

//    public EstudianteBean getEstudianteBean() {
//        if(estudianteBean == null){
//            estudianteBean = new EstudianteBean();
//        }
//        return estudianteBean;
//    }
//
//    public void setEstudianteBean(EstudianteBean estudianteBean) {
//        this.estudianteBean = estudianteBean;
//    }
    public CodigoBean getTipoEsquelaBean() {
        if (tipoEsquelaBean == null) {
            tipoEsquelaBean = new CodigoBean();
        }
        return tipoEsquelaBean;
    }

    public void setTipoEsquelaBean(CodigoBean tipoEsquelaBean) {
        this.tipoEsquelaBean = tipoEsquelaBean;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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

    public String getDatoMes() {
        return datoMes;
    }

    public void setDatoMes(String datoMes) {
        this.datoMes = datoMes;
    }

    public Integer getFlgRecEnvio() {
        return flgRecEnvio;
    }

    public void setFlgRecEnvio(Integer flgRecEnvio) {
        this.flgRecEnvio = flgRecEnvio;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getFlgenviopapa() {
        return flgenviopapa;
    }

    public void setFlgenviopapa(Integer flgenviopapa) {
        this.flgenviopapa = flgenviopapa;
    }

    public Integer getFlgenviomama() {
        return flgenviomama;
    }

    public void setFlgenviomama(Integer flgenviomama) {
        this.flgenviomama = flgenviomama;
    }

    public Integer getFlgenvioapo() {
        return flgenvioapo;
    }

    public void setFlgenvioapo(Integer flgenvioapo) {
        this.flgenvioapo = flgenvioapo;
    }

    public Integer getFlgenviorespago() {
        return flgenviorespago;
    }

    public void setFlgenviorespago(Integer flgenviorespago) {
        this.flgenviorespago = flgenviorespago;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNumOk() {
        return numOk;
    }

    public void setNumOk(Integer numOk) {
        this.numOk = numOk;
    }

    public Integer getNumFa() {
        return numFa;
    }

    public void setNumFa(Integer numFa) {
        this.numFa = numFa;
    }

    public Integer getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(Integer tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }

    public Integer getTipoDestino() {
        return tipoDestino;
    }

    public void setTipoDestino(Integer tipoDestino) {
        this.tipoDestino = tipoDestino;
    }

}
