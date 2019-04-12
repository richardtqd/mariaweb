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
public class NotasOperacionRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private Integer mes;
    private JRBeanCollectionDataSource listaFechasCobros;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucUniNeg() {
        return rucUniNeg;
    }

    public void setRucUniNeg(String rucUniNeg) {
        this.rucUniNeg = rucUniNeg;
    }

    public JRBeanCollectionDataSource getListaFechasCobros() {
        return listaFechasCobros;
    }

    public void setListaFechasCobros(List<NotasOpeFecCobroRepBean> listaFechasCobros) {
        this.listaFechasCobros = new JRBeanCollectionDataSource(listaFechasCobros);
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

}
