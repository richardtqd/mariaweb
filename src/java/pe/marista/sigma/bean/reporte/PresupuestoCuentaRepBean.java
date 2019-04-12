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
public class PresupuestoCuentaRepBean {

    private Integer nroCuenta;
    private String nombreCuenta;
    private Double presupuestoProg;
    private JRBeanCollectionDataSource listaCentroResponsabilidad;

    public Integer getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(Integer nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
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

}
