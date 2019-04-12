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
public class PresUniOrgRepBean implements Serializable {

    private String idUniOrg;
    private String nombreUniOrg;
    private String presupuestoTotal;
    private String PresupuestoEjec;
    private String totalPro;
    private String totalEjec;
    private String nombreUniNeg;
    private String ruc;
    private Integer anio;

    private JRBeanCollectionDataSource listaDetalle;
    private JRBeanCollectionDataSource listaDetallePresUniOrg;

    public String getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(String idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public String getPresupuestoTotal() {
        return presupuestoTotal;
    }

    public void setPresupuestoTotal(String presupuestoTotal) {
        this.presupuestoTotal = presupuestoTotal;
    }

    public String getPresupuestoEjec() {
        return PresupuestoEjec;
    }

    public void setPresupuestoEjec(String PresupuestoEjec) {
        this.PresupuestoEjec = PresupuestoEjec;
    }

    public String getTotalPro() {
        return totalPro;
    }

    public void setTotalPro(String totalPro) {
        this.totalPro = totalPro;
    }

    public String getTotalEjec() {
        return totalEjec;
    }

    public void setTotalEjec(String totalEjec) {
        this.totalEjec = totalEjec;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<PresUniOrgRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public JRBeanCollectionDataSource getListaDetallePresUniOrg() {
        return listaDetallePresUniOrg;
    }

    public void setListaDetallePresUniOrg(List<DetPresUniOrgRepBean> listaDetallePresUniOrg) {
        this.listaDetallePresUniOrg = new JRBeanCollectionDataSource(listaDetallePresUniOrg);;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

}
