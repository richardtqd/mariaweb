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
 * @author Administrador
 */
public class PresupuestoNewInicioRepBean {
 
    private JRBeanCollectionDataSource listaCuentas; 
    private JRBeanCollectionDataSource listaCentroResponsabilidad;
    private Double presupuestoProg;
    private String inicio; 
    private Integer cr;
    private String nombreCR;
 
    public JRBeanCollectionDataSource getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<PresupuestoCuentaRepBean> listaCuentas) {
        this.listaCuentas = new JRBeanCollectionDataSource(listaCuentas);
    } 

    public JRBeanCollectionDataSource getListaCentroResponsabilidad() {
        return listaCentroResponsabilidad;
    }

    public void setListaCentroResponsabilidad(List<PresupuestoCentroRepBean> listaCentroResponsabilidad) {
        this.listaCentroResponsabilidad = new JRBeanCollectionDataSource(listaCentroResponsabilidad);
    }

    public Double getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(Double presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getNombreCR() {
        return nombreCR;
    }

    public void setNombreCR(String nombreCR) {
        this.nombreCR = nombreCR;
    }
}
