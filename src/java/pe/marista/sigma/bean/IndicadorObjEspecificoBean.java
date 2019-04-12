/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author MS002
 */
public class IndicadorObjEspecificoBean implements Serializable{
    private Integer idIndicadorObjEspecifico;
    private ObjetivoEspecificoBean objetivoEspecificoBean;//idObjetivoEspecifico
    private IndicadorBean indicadorBean;//idIndicador
    private CodigoBean codigoBean;//idTipoMeta - idCodigo
    private Integer meta;
    private String responsable;
    private List<PeriodoBean> listaPeriodo;

    public IndicadorObjEspecificoBean(Integer idIndicadorObjEspecifico, ObjetivoEspecificoBean objetivoEspecificoBean, IndicadorBean indicadorBean, CodigoBean codigoBean, Integer meta) {
        this.idIndicadorObjEspecifico = idIndicadorObjEspecifico;
        this.objetivoEspecificoBean = objetivoEspecificoBean;
        this.indicadorBean = indicadorBean;
        this.codigoBean = codigoBean;
        this.meta = meta;
    }

    public IndicadorObjEspecificoBean() {
    }

    
    public Integer getIdIndicadorObjEspecifico() {
        return idIndicadorObjEspecifico;
    }

    public void setIdIndicadorObjEspecifico(Integer idIndicadorObjEspecifico) {
        this.idIndicadorObjEspecifico = idIndicadorObjEspecifico;
    }

    public IndicadorBean getIndicadorBean() {
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
    }

    public ObjetivoEspecificoBean getObjetivoEspecificoBean() {
        return objetivoEspecificoBean;
    }

    public void setObjetivoEspecificoBean(ObjetivoEspecificoBean objetivoEspecificoBean) {
        this.objetivoEspecificoBean = objetivoEspecificoBean;
    }

    public CodigoBean getCodigoBean() {
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }


    public Integer getMeta() {
        return meta;
    }

    public void setMeta(Integer meta) {
        this.meta = meta;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List<PeriodoBean> getListaPeriodo() {
        return listaPeriodo;
    }

    public void setListaPeriodo(List<PeriodoBean> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }
    
    
    
}
