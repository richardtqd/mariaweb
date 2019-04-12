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
public class EstMatriculaRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String titulo;
    private Integer cantM;
    private Integer cantF;
    private Integer cantTotal;
    private String pie;
    private JRBeanCollectionDataSource listaNiveles;
 

    public JRBeanCollectionDataSource getListaNiveles() {
        return listaNiveles;
    }

    public void setListaNiveles(List<EstMatriculaNivelRepBean> listaNiveles) {
        this.listaNiveles = new JRBeanCollectionDataSource(listaNiveles);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

}
