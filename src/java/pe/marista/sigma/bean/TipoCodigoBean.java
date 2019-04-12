/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS002
 */
public class TipoCodigoBean implements Serializable {

    private String idTipoCodigo;
    private String descripcion;
    private Integer edita;
    private Integer idTipo;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    public TipoCodigoBean(String idTipoCodigo) {
        this.idTipoCodigo = idTipoCodigo;
    }

    public TipoCodigoBean(String idTipoCodigo, String descripcion) {
        this.idTipoCodigo = idTipoCodigo;
        this.descripcion = descripcion;
    }

  

    //Detalles
    private List<CodigoBean> listaCodigoBean;

    public TipoCodigoBean() {

    }

    public String getIdTipoCodigo() {
        return idTipoCodigo;
    }

    public void setIdTipoCodigo(String idTipoCodigo) {
        this.idTipoCodigo = idTipoCodigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CodigoBean> getListaCodigoBean() {
        if (listaCodigoBean == null) {
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    }

    public Integer getEdita() {
        return edita;
    }

    public void setEdita(Integer edita) {
        this.edita = edita;
    }

    public Integer getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
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
