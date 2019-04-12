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
public class PrespuestoCabeceraRepBean {
    private String inicio;
    private Double presupuestoProg;
    private JRBeanCollectionDataSource listaInicio;
    private String titulo;
    private String nombreUniNeg;
    private String rucColegio;
    private String rutaImagen; 
    private JRBeanCollectionDataSource listaCuentas;
    private JRBeanCollectionDataSource listaCentroResponsabilidad;
    private String RUC;
    private String nombrePlanContable;
    private Integer cuenta;
    
    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public Double getPresupuestoProg() {
        return presupuestoProg;
    }

    public void setPresupuestoProg(Double presupuestoProg) {
        this.presupuestoProg = presupuestoProg;
    }
    
    public JRBeanCollectionDataSource getListaInicio() {
        return listaInicio;
    }

    public void setListaInicio(List<PresupuestoNewInicioRepBean> listaInicio) {
        this.listaInicio = new JRBeanCollectionDataSource(listaInicio);
    } 
    
     public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucColegio() {
        return rucColegio;
    }

    public void setRucColegio(String rucColegio) {
        this.rucColegio = rucColegio;
    }
    
    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
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

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public String getNombrePlanContable() {
        return nombrePlanContable;
    }

    public void setNombrePlanContable(String nombrePlanContable) {
        this.nombrePlanContable = nombrePlanContable;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }
}
