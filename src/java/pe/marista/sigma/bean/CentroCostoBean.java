/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class CentroCostoBean implements Serializable {

    private CentroCostoBean centroCostoBean;//idCentroCostoPadre
    private String codigo;
    private Integer idCentroCosto;
    private String nombre;
    private Integer nivel;
    private Integer idCentroCostoPadre;
    

    public Integer getIdCentroCosto() {
        return idCentroCosto;
    }

    public void setIdCentroCosto(Integer idCentroCosto) {
        this.idCentroCosto = idCentroCosto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public CentroCostoBean getCentroCostoBean() {
        if(centroCostoBean == null)
        {
            centroCostoBean = new CentroCostoBean();
        }
        return centroCostoBean;
    }

    public void setCentroCostoBean(CentroCostoBean centroCostoBean) {
        this.centroCostoBean = centroCostoBean;
    }

    public Integer getIdCentroCostoPadre() {
        return idCentroCostoPadre;
    }

    public void setIdCentroCostoPadre(Integer idCentroCostoPadre) {
        this.idCentroCostoPadre = idCentroCostoPadre;
    }


}
