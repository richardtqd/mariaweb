/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

/**
 *
 * @author MS001
 */
public class CajaGeneralCtaRepBean {

    private Integer cuenta;
    private Integer mora;
    private Integer flgGen;
    private String nombreCta;
    private Double montoPorCtaDolares;
    private Double montoPorCtaSoles;

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombreCta() {
        return nombreCta;
    }

    public void setNombreCta(String nombreCta) {
        this.nombreCta = nombreCta;
    }

    public Double getMontoPorCtaDolares() {
        return montoPorCtaDolares;
    }

    public void setMontoPorCtaDolares(Double montoPorCtaDolares) {
        this.montoPorCtaDolares = montoPorCtaDolares;
    }

    public Double getMontoPorCtaSoles() {
        return montoPorCtaSoles;
    }

    public void setMontoPorCtaSoles(Double montoPorCtaSoles) {
        this.montoPorCtaSoles = montoPorCtaSoles;
    }

    public Integer getMora() {
        return mora;
    }

    public void setMora(Integer mora) {
        this.mora = mora;
    }

    public Integer getFlgGen() {
        return flgGen;
    }

    public void setFlgGen(Integer flgGen) {
        this.flgGen = flgGen;
    }

}
