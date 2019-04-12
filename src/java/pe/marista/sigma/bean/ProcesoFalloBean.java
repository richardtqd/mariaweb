/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author JC
 */
public class ProcesoFalloBean implements Serializable {

    private String uniNeg;
    private Integer idProcesoFallo;
    private String nombre;
    private String apePat;
    private String apeMat;
    private String idEstudiante;
    private String codigo;
    private Integer grado;
    private String nombreGrado;
    private Integer anio;
    private String mes;
    private String fechaVista;
    private BigDecimal monto;
    private Date fecha;

    //Claves Foraneas
    private Integer idProcesoBanco;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getGrado() {
        return grado;
    }

    public void setGrado(Integer grado) {
        this.grado = grado;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(String fechaVista) {
        this.fechaVista = fechaVista;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdProcesoFallo() {
        return idProcesoFallo;
    }

    public void setIdProcesoFallo(Integer idProcesoFallo) {
        this.idProcesoFallo = idProcesoFallo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

}
