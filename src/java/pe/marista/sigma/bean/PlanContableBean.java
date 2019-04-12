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
 * @author MS002
 */
public class PlanContableBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer cuenta;
    private String nombre;
    private CodigoBean idTipoCuenta;
    private CodigoBean idTipoCategoriaActa;
//    private CodigoBean idTipoElemento;//Cambio a tipocategoriaActa
    private int nivel;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;

    //Ayuda
    private CodigoBean idTipoGrupoCta;
    private Boolean flgNull;
    private Integer col;
    private BigDecimal importe;
    private String nombrePlan;
    private Double valorPresu;
    
    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CodigoBean getIdTipoCuenta() {
        if (idTipoCuenta == null) {
            idTipoCuenta = new CodigoBean();
        }
        return idTipoCuenta;
    }

    public void setIdTipoCuenta(CodigoBean idTipoCuenta) {
        this.idTipoCuenta = idTipoCuenta;
    }

//    public CodigoBean getIdTipoElemento() {
//        if(idTipoElemento == null)
//        {
//            idTipoElemento = new CodigoBean();
//        }
//        return idTipoElemento;
//    }
//
//    public void setIdTipoElemento(CodigoBean idTipoElemento) {
//        this.idTipoElemento = idTipoElemento;
//    }
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
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

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public CodigoBean getIdTipoCategoriaActa() {
        if (idTipoCategoriaActa == null) {
            idTipoCategoriaActa = new CodigoBean();
        }
        return idTipoCategoriaActa;
    }

    public void setIdTipoCategoriaActa(CodigoBean idTipoCategoriaActa) {
        this.idTipoCategoriaActa = idTipoCategoriaActa;
    }

    public CodigoBean getIdTipoGrupoCta() {
        if (idTipoGrupoCta == null) {
            idTipoGrupoCta = new CodigoBean();
        }
        return idTipoGrupoCta;
    }

    public void setIdTipoGrupoCta(CodigoBean idTipoGrupoCta) {
        this.idTipoGrupoCta = idTipoGrupoCta;
    }

    public Boolean getFlgNull() {
        return flgNull;
    }

    public void setFlgNull(Boolean flgNull) {
        this.flgNull = flgNull;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public Double getValorPresu() {
        return valorPresu;
    }

    public void setValorPresu(Double valorPresu) {
        this.valorPresu = valorPresu;
    }

}
