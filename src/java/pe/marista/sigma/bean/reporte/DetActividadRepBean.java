/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.math.BigDecimal;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS002
 */
public class DetActividadRepBean {

    private String uniNeg;
    private Integer idActividad;
    private String nombre;
    private BigDecimal ingreso;
    private BigDecimal egreso;
    private String responsable;
    private String nomOO;
    private String nomPo;
    private String nomInd;
    private String creaFecha;
    private String creaHora;

    //Lista Detalle
    private JRBeanCollectionDataSource listaDetalle;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getEgreso() {
        return egreso;
    }

    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNomOO() {
        return nomOO;
    }

    public void setNomOO(String nomOO) {
        this.nomOO = nomOO;
    }

    public String getNomPo() {
        return nomPo;
    }

    public void setNomPo(String nomPo) {
        this.nomPo = nomPo;
    }

    public String getNomInd() {
        return nomInd;
    }

    public void setNomInd(String nomInd) {
        this.nomInd = nomInd;
    }

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCreaHora() {
        return creaHora;
    }

    public void setCreaHora(String creaHora) {
        this.creaHora = creaHora;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetActividadRepSubBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

}
