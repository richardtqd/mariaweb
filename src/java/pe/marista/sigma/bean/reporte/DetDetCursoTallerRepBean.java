/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class DetDetCursoTallerRepBean implements Serializable {

    private String discente;
    private String fecha;
    private String montoPagado;
    private String taller;
    private String montoPagadoPorTaller;
    private Integer nroItem;

    //AYUDA TALLER
    private String nrodoc;

    public DetDetCursoTallerRepBean() {
    }

    public DetDetCursoTallerRepBean(String discente, String fecha, String montoPagado, String taller, String montoPagadoPorTaller) {
        this.discente = discente;
        this.fecha = fecha;
        this.montoPagado = montoPagado;
        this.taller = taller;
        this.montoPagadoPorTaller = montoPagadoPorTaller;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getTaller() {
        return taller;
    }

    public void setTaller(String taller) {
        this.taller = taller;
    }

    public String getMontoPagadoPorTaller() {
        return montoPagadoPorTaller;
    }

    public void setMontoPagadoPorTaller(String montoPagadoPorTaller) {
        this.montoPagadoPorTaller = montoPagadoPorTaller;
    }

    public Integer getNroItem() {
        return nroItem;
    }

    public void setNroItem(Integer nroItem) {
        this.nroItem = nroItem;
    }

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

}
