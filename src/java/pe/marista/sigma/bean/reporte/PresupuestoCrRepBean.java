/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS002
 */
public class PresupuestoCrRepBean {

    private Integer cr;
    private String nombre;
    private Integer nivel;
    private String creaFechaAc;
    private String creaHoraAc;

    //Ayuda
    private JRBeanCollectionDataSource listaDetalle;

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getCreaFechaAc() {
        return creaFechaAc;
    }

    public void setCreaFechaAc(String creaFechaAc) {
        this.creaFechaAc = creaFechaAc;
    }

    public String getCreaHoraAc() {
        return creaHoraAc;
    }

    public void setCreaHoraAc(String creaHoraAc) {
        this.creaHoraAc = creaHoraAc;
    }
     
    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<PresupuestoCrRepSubBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

}
