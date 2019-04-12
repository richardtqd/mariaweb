/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class LineaEstrategicaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idLinea;
    private PlanEstrategicoBean planEstrategicoBean;
    private String nombre;
    private String descripcion;
    private String status;
    private boolean estado;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    
    //Ayuda
    private String nombreLineaSub;
    private String descripLineaSub;
    
    public LineaEstrategicaBean(Integer idLinea, PlanEstrategicoBean planEstrategicoBean, String nombre, String descripcion, String status) {
        this.idLinea = idLinea;
        this.planEstrategicoBean = planEstrategicoBean;
        this.nombre= nombre;
        this.descripcion = descripcion;
        this.status = status;
    }

    public LineaEstrategicaBean() {
    }

    public Integer getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }

    public PlanEstrategicoBean getPlanEstrategicoBean() {
        if (planEstrategicoBean == null) {
            planEstrategicoBean = new PlanEstrategicoBean();
        }
        return planEstrategicoBean;
    }

    public void setPlanEstrategicoBean(PlanEstrategicoBean planEstrategicoBean) {
        this.planEstrategicoBean = planEstrategicoBean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isEstado() {
        if (status.equals("Inactivo")) { 
            estado = false;
            return false;
        }
        if (status.equals("Activo")) {
            estado = true;
            return true;
        }
        return estado;
    }

    public boolean getEstado(){
       return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }  

    public UnidadNegocioBean getUnidadNegocioBean() {
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getNombreLineaSub() {
        return nombreLineaSub;
    }

    public void setNombreLineaSub(String nombreLineaSub) {
        this.nombreLineaSub = nombreLineaSub;
    }

    public String getDescripLineaSub() {
        return descripLineaSub;
    }

    public void setDescripLineaSub(String descripLineaSub) {
        this.descripLineaSub = descripLineaSub;
    }
     
}
