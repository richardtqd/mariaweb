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
public class EstMatriculaGradoAcaRepBean implements Serializable {
    
    private String nivel;
    private Integer idGradoAcademico;
    private String nombreGradoAca;
    private Integer cantM;
    private Integer cantF;
    private Integer cantTotal;
    private JRBeanCollectionDataSource listaGradoAcaSeccion;

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getNombreGradoAca() {
        return nombreGradoAca;
    }

    public void setNombreGradoAca(String nombreGradoAca) {
        this.nombreGradoAca = nombreGradoAca;
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

    public JRBeanCollectionDataSource getListaGradoAcaSeccion() {
        return listaGradoAcaSeccion;
    }

    public void setListaGradoAcaSeccion(List<EstMatriculaSeccGradoAcaRepBean>listaGradoAcaSeccion) {
        this.listaGradoAcaSeccion = new JRBeanCollectionDataSource(listaGradoAcaSeccion);
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

}
