/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MS002
 */
public class ObjetivoEspecificoBean implements Serializable{
    private Integer idObjEspecifico;
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private String codigoObjEspecifico;
    private String nombreObjEspecifico;
    private String descripObjEspecifico;
    private String statusObjEspecifico;
    private List<IndicadorObjEspecificoBean> listIndicadorObjEspecificoBean;

    public ObjetivoEspecificoBean(Integer idObjEspecifico, ObjetivoEstrategicaBean objetivoEstrategicaBean, String codigoObjEspecifico, String nombreObjEspecifico) {
        this.idObjEspecifico = idObjEspecifico;
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
        this.codigoObjEspecifico = codigoObjEspecifico;
        this.nombreObjEspecifico = nombreObjEspecifico;
    }

    public ObjetivoEspecificoBean(Integer idObjEspecifico, ObjetivoEstrategicaBean objetivoEstrategicaBean, String codigoObjEspecifico, String nombreObjEspecifico, String descripObjEspecifico, String statusObjEspecifico) {
        this.idObjEspecifico = idObjEspecifico;
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
        this.codigoObjEspecifico = codigoObjEspecifico;
        this.nombreObjEspecifico = nombreObjEspecifico;
        this.descripObjEspecifico = descripObjEspecifico;
        this.statusObjEspecifico = statusObjEspecifico;
    }

    public ObjetivoEspecificoBean() {
    }
    
    
    public Integer getIdObjEspecifico() {
        return idObjEspecifico;
    }

    public void setIdObjEspecifico(Integer idObjEspecifico) {
        this.idObjEspecifico = idObjEspecifico;
    }

    public ObjetivoEstrategicaBean getObjetivoEstrategicaBean() {
        if(objetivoEstrategicaBean == null)
        {
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicaBean;
    }

    public void setObjetivoEstrategicaBean(ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public String getCodigoObjEspecifico() {
        return codigoObjEspecifico;
    }

    public void setCodigoObjEspecifico(String codigoObjEspecifico) {
        this.codigoObjEspecifico = codigoObjEspecifico;
    }

    public String getNombreObjEspecifico() {
        return nombreObjEspecifico;
    }

    public void setNombreObjEspecifico(String nombreObjEspecifico) {
        this.nombreObjEspecifico = nombreObjEspecifico;
    }

    public String getDescripObjEspecifico() {
        return descripObjEspecifico;
    }

    public void setDescripObjEspecifico(String descripObjEspecifico) {
        this.descripObjEspecifico = descripObjEspecifico;
    }

    public String getStatusObjEspecifico() {
        return statusObjEspecifico;
    }

    public void setStatusObjEspecifico(String statusObjEspecifico) {
        this.statusObjEspecifico = statusObjEspecifico;
    }

    public List<IndicadorObjEspecificoBean> getListIndicadorObjEspecificoBean() {
        return listIndicadorObjEspecificoBean;
    }

    public void setListIndicadorObjEspecificoBean(List<IndicadorObjEspecificoBean> listIndicadorObjEspecificoBean) {
        this.listIndicadorObjEspecificoBean = listIndicadorObjEspecificoBean;
    }

    
    
    
}
