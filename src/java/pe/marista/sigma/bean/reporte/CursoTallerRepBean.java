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
public class CursoTallerRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String taller;
    private String total;
    private JRBeanCollectionDataSource listaDetalleTalleres;
    private String textoFiltro; 

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

    public String getTaller() {
        return taller;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public JRBeanCollectionDataSource getListaDetalleTalleres() {
        return listaDetalleTalleres;
    }

    public void setListaDetalleTalleres(List<DetCursoTallerRepBean> listaDetalleTalleres) {
        this.listaDetalleTalleres = new JRBeanCollectionDataSource(listaDetalleTalleres);
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    
}
