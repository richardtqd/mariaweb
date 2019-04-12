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
public class EstMatriculaNivelRepBean implements Serializable {

    private String nivel;
//    private String titulo;
    private Integer cantM;
    private Integer cantF;
    private Integer cantTotal;
    private String nombreUniNeg;
    private String rucUniNeg;
    private String titulo;
    private String pie;
    private JRBeanCollectionDataSource listaGradoAcademico;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
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

    public JRBeanCollectionDataSource getListaGradoAcademico() {
        return listaGradoAcademico;
    }

    public void setListaGradoAcademico(List<EstMatriculaGradoAcaRepBean> listaGradoAcademico) {
        this.listaGradoAcademico = new JRBeanCollectionDataSource(listaGradoAcademico);
    }
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

}
