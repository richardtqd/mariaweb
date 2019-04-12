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
public class CodigoBean implements Serializable {

    private Integer nroItem;
    private Integer idCodigo;
    private String idtipogrupocr;
    private TipoCodigoBean tipoCodigoBean; //idTipoCodigo
    private String codigo;
    private String valor;
    private int flagMarista;
    private Integer orden;
    private Integer idTipo; //idTipo 
     private String creaPor;
    private Date creaFecha;
    private String modiPor;
    
    private String codigoAyuda;

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
 

    public CodigoBean(String codigo) {
        this.codigo = codigo;

    }
    public CodigoBean(Integer idCodigo) {
        this.idCodigo = idCodigo;

    }
  

    public CodigoBean(Integer idCodigo, String codigo, String valor) {
        this.idCodigo = idCodigo;
        this.codigo = codigo;
        this.valor = valor;
    }

    public CodigoBean(Integer idCodigo, String codigo, TipoCodigoBean tipoCodigoBean) {
        this.idCodigo = idCodigo;
        this.codigo = codigo;
        this.tipoCodigoBean = tipoCodigoBean;
    }

    public CodigoBean() {
    }

    public Integer getNroItem() {
        return nroItem;
    }

    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public TipoCodigoBean getTipoCodigoBean() {
        if (tipoCodigoBean == null) {
            tipoCodigoBean = new TipoCodigoBean();
        }
        return tipoCodigoBean;
    }

    public void setTipoCodigoBean(TipoCodigoBean tipoCodigoBean) {
        this.tipoCodigoBean = tipoCodigoBean;
    }

    public String getIdtipogrupocr() {
        return idtipogrupocr;
    }

    public void setIdtipogrupocr(String idtipogrupocr) {
        this.idtipogrupocr = idtipogrupocr;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getFlagMarista() {
        return flagMarista;
    }

    public void setFlagMarista(int flagMarista) {
        this.flagMarista = flagMarista;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCodigoAyuda() {
        return codigoAyuda;
    }

    public void setCodigoAyuda(String codigoAyuda) {
        this.codigoAyuda = codigoAyuda;
    }

}
