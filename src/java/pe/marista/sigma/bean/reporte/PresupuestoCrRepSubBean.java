/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.math.BigDecimal;

/**
 *
 * @author MS002
 */
public class PresupuestoCrRepSubBean {

    private Integer numCuenta;
    private String nomPlanCu;
    private Integer cr;
    private String nombreCR;
    private BigDecimal importe;
    private String ejecutado;

    public Integer getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(Integer numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getNomPlanCu() {
        return nomPlanCu;
    }

    public void setNomPlanCu(String nomPlanCu) {
        this.nomPlanCu = nomPlanCu;
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

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(String ejecutado) {
        this.ejecutado = ejecutado;
    }

}
