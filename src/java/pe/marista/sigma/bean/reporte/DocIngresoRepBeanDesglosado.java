/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS002
 */
public class DocIngresoRepBeanDesglosado implements Serializable {

    private Double subTotalSol;
    private Double subTotalDol;
    private String modo;
    private JRBeanCollectionDataSource listaDetDetDocIngresoRepBean;

    public Double getSubTotalSol() {
        return subTotalSol;
    }

    public void setSubTotalSol(Double subTotalSol) {
        this.subTotalSol = subTotalSol;
    }

    public Double getSubTotalDol() {
        return subTotalDol;
    }

    public void setSubTotalDol(Double subTotalDol) {
        this.subTotalDol = subTotalDol;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public JRBeanCollectionDataSource getListaDetDetDocIngresoRepBean() {
        return listaDetDetDocIngresoRepBean;
    }

    public void setListaDetDetDocIngresoRepBean(List<CajaGenRepBean> listaDetDetDocIngresoRepBean) {
        this.listaDetDetDocIngresoRepBean = new JRBeanCollectionDataSource(listaDetDetDocIngresoRepBean);
    }

}
