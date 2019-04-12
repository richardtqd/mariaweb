/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class NotasOpeFecVencRepBean implements Serializable {

    private Date fechavenc;
    private String label;
    private String sumImporteOp;
    private String sumMoraOp;
    private String totalOp;
    private String titulo;
    private String tituloOp;
    
    private JRBeanCollectionDataSource listaDetCobrosPensiones;

    public Date getFechavenc() {
        return fechavenc;
    }

    public void setFechavenc(Date fechavenc) {
        this.fechavenc = fechavenc;
    }

    public String getSumImporteOp() {
        return sumImporteOp;
    }

    public void setSumImporteOp(String sumImporteOp) {
        this.sumImporteOp = sumImporteOp;
    }

    public String getSumMoraOp() {
        return sumMoraOp;
    }

    public void setSumMoraOp(String sumMoraOp) {
        this.sumMoraOp = sumMoraOp;
    }

    public String getTotalOp() {
        return totalOp;
    }

    public void setTotalOp(String totalOp) {
        this.totalOp = totalOp;
    }

    public JRBeanCollectionDataSource getListaDetCobrosPensiones() {
        return listaDetCobrosPensiones;
    }

    public void setListaDetCobrosPensiones(List<DetCobrosPensionesRepBean> listaDetCobrosPensiones) {
        this.listaDetCobrosPensiones = new JRBeanCollectionDataSource(listaDetCobrosPensiones);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloOp() {
        return tituloOp;
    }

    public void setTituloOp(String tituloOp) {
        this.tituloOp = tituloOp;
    }

}
