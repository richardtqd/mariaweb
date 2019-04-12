/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS002
 */
public class TipoConceptoBean implements Serializable {

    private Integer idTipoConcepto;
    private String nombre;
    private String flgIngreso;
    private String creaPor;
    private Date creaFecha;

    private Integer idTipoConceptoVista;
    private String nomTipoConceptoVista;

    private Integer idConceptoVista;
    private String nomConceptoVista;

    public TipoConceptoBean() {
    }

    public TipoConceptoBean(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFlgIngreso() {
        return flgIngreso;
    }

    public String getFlgIngresoVista() {
        if (flgIngreso.equals("0")) {
            return MaristaConstantes.EGRESO;
        } else {
            return MaristaConstantes.INGRESO;
        }
    }

    public void setFlgIngreso(String flgIngreso) {
        this.flgIngreso = flgIngreso;
    }

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
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

    public Integer getIdTipoConceptoVista() {
        return idTipoConceptoVista;
    }

    public void setIdTipoConceptoVista(Integer idTipoConceptoVista) {
        this.idTipoConceptoVista = idTipoConceptoVista;
    }

    public String getNomTipoConceptoVista() {
        return nomTipoConceptoVista;
    }

    public void setNomTipoConceptoVista(String nomTipoConceptoVista) {
        this.nomTipoConceptoVista = nomTipoConceptoVista;
    }

    public Integer getIdConceptoVista() {
        return idConceptoVista;
    }

    public void setIdConceptoVista(Integer idConceptoVista) {
        this.idConceptoVista = idConceptoVista;
    }

    public String getNomConceptoVista() {
        return nomConceptoVista;
    }

    public void setNomConceptoVista(String nomConceptoVista) {
        this.nomConceptoVista = nomConceptoVista;
    }
}
