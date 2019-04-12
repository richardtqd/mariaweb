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
public class EstMatriculaSeccGradoAcaRepBean implements Serializable {

    private String seccion;
    private Integer cantM;
    private Integer cantF;
    private Integer cantTotal;
    private JRBeanCollectionDataSource listaSeccion;

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Integer getCantM() {
        return cantM;
    }

    public void setCantM(Integer cantM) {
        this.cantM = cantM;
    }

    public Integer getCantF() {
        return cantF;
    }

    public void setCantF(Integer cantF) {
        this.cantF = cantF;
    }

    public Integer getCantTotal() {
        return cantTotal;
    }

    public void setCantTotal(Integer cantTotal) {
        this.cantTotal = cantTotal;
    }

    public JRBeanCollectionDataSource getListaSeccion() {
        return listaSeccion;
    }

    public void setListaSeccion(List<EstMatriculaSeccionRepBean> listaSeccion) {
        this.listaSeccion = new JRBeanCollectionDataSource(listaSeccion);
    }

}
