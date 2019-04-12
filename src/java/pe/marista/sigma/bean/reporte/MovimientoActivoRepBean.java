/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class MovimientoActivoRepBean {
    private Integer idMovimientoactivo;
    private Timestamp fechaMov;
    private Timestamp fechaRetorno;
    private String Observacion;
    private String item;
    private String nombreUniNeg;
    private String motivo;
    private String nombreCompletoOri;
    private String nombreCompletoDes;
    private String nombreOrgOr;
    private String nombreOrgDes;
    private String nombreMocAc;
    private String nombreDuracion;
    private String nombreUnidad;
    private String webUnidad;
    private String correoUnidad;
    private String telefonoUnidad;
    private String direccionUnidad;
    private String distritoUnidad;
    private String paisUnidad;
    private String rucUnidad;
  
    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombreCompletoOri() {
        return nombreCompletoOri;
    }

    public void setNombreCompletoOri(String nombreCompletoOri) {
        this.nombreCompletoOri = nombreCompletoOri;
    }

    public String getNombreCompletoDes() {
        return nombreCompletoDes;
    }

    public void setNombreCompletoDes(String nombreCompletoDes) {
        this.nombreCompletoDes = nombreCompletoDes;
    }

    public String getNombreOrgOr() {
        return nombreOrgOr;
    }

    public void setNombreOrgOr(String nombreOrgOr) {
        this.nombreOrgOr = nombreOrgOr;
    }

    public String getNombreOrgDes() {
        return nombreOrgDes;
    }

    public void setNombreOrgDes(String nombreOrgDes) {
        this.nombreOrgDes = nombreOrgDes;
    }

    public String getNombreMocAc() {
        return nombreMocAc;
    }

    public void setNombreMocAc(String nombreMocAc) {
        this.nombreMocAc = nombreMocAc;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getWebUnidad() {
        return webUnidad;
    }

    public void setWebUnidad(String webUnidad) {
        this.webUnidad = webUnidad;
    }

    public String getCorreoUnidad() {
        return correoUnidad;
    }

    public void setCorreoUnidad(String correoUnidad) {
        this.correoUnidad = correoUnidad;
    }

    public String getTelefonoUnidad() {
        return telefonoUnidad;
    }

    public void setTelefonoUnidad(String telefonoUnidad) {
        this.telefonoUnidad = telefonoUnidad;
    }

    public String getDireccionUnidad() {
        return direccionUnidad;
    }

    public void setDireccionUnidad(String direccionUnidad) {
        this.direccionUnidad = direccionUnidad;
    }

    public String getDistritoUnidad() {
        return distritoUnidad;
    }

    public void setDistritoUnidad(String distritoUnidad) {
        this.distritoUnidad = distritoUnidad;
    }

    public String getPaisUnidad() {
        return paisUnidad;
    }

    public void setPaisUnidad(String paisUnidad) {
        this.paisUnidad = paisUnidad;
    }

    public String getNombreDuracion() {
        return nombreDuracion;
    }

    public void setNombreDuracion(String nombreDuracion) {
        this.nombreDuracion = nombreDuracion;
    }

    public Integer getIdMovimientoactivo() {
        return idMovimientoactivo;
    }

    public void setIdMovimientoactivo(Integer idMovimientoactivo) {
        this.idMovimientoactivo = idMovimientoactivo;
    }

    public String getRucUnidad() {
        return rucUnidad;
    }

    public void setRucUnidad(String rucUnidad) {
        this.rucUnidad = rucUnidad;
    }

    public Timestamp getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Timestamp fechaMov) {
        this.fechaMov = fechaMov;
    }

    public Timestamp getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Timestamp fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    } 
    
}
