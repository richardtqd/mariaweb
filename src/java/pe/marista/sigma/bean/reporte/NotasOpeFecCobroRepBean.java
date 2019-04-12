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
public class NotasOpeFecCobroRepBean implements Serializable {

    private Date fechacobro;
    private String titulo;
    private String tituloOp;
    private JRBeanCollectionDataSource listaFechasVencimiento;

    public Date getFechacobro() {
        return fechacobro;
    }

    public void setFechacobro(Date fechacobro) {
        this.fechacobro = fechacobro;
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

    public JRBeanCollectionDataSource getListaFechasVencimiento() {
        return listaFechasVencimiento;
    }

    public void setListaFechasVencimiento(List<NotasOpeFecVencRepBean> listaFechasVencimiento) {
        this.listaFechasVencimiento = new JRBeanCollectionDataSource(listaFechasVencimiento);
    }

}
