/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class DetDiaResumenIngRepBean implements Serializable {

    private Integer dia;
    private Integer anioAnterior;
    private Integer anioAnteriorAnt;
    private JRBeanCollectionDataSource listaDetalle;

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetResumenIngRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getAnioAnterior() {
        return anioAnterior;
    }

    public void setAnioAnterior(Integer anioAnterior) {
        this.anioAnterior = anioAnterior;
    }

    public Integer getAnioAnteriorAnt() {
        return anioAnteriorAnt;
    }

    public void setAnioAnteriorAnt(Integer anioAnteriorAnt) {
        this.anioAnteriorAnt = anioAnteriorAnt;
    }

}
