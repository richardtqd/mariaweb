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
 * @author Administrador
 */
public class PersonalProcesoJudicialBean implements Serializable {

    private Integer idPersonalProcesoJudicial;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean; //idPersonal
    private CodigoBean tipoProcesoJudicialBean; //idTipoProcesoJudicial
    private CodigoBean tipoRetencionBean; //idTipoRetencion
    private CodigoBean tipoValorBean; //idTipoValor
    private BigDecimal valor;
    private String beneficiario;
    private String cuenta;
    private Date fecIni;
    private Date FecFin;
    private String obs;
    private CodigoBean tipoModoPagoBean; //idTipoModoPago
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    
    private boolean collapsed = true;

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getIdPersonalProcesoJudicial() {
        return idPersonalProcesoJudicial;
    }

    public void setIdPersonalProcesoJudicial(Integer idPersonalProcesoJudicial) {
        this.idPersonalProcesoJudicial = idPersonalProcesoJudicial;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return FecFin;
    }

    public void setFecFin(Date FecFin) {
        this.FecFin = FecFin;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public CodigoBean getTipoProcesoJudicialBean() {
        if (tipoProcesoJudicialBean == null) {
            tipoProcesoJudicialBean = new CodigoBean();
        }
        return tipoProcesoJudicialBean;
    }

    public void setTipoProcesoJudicialBean(CodigoBean tipoProcesoJudicialBean) {
        this.tipoProcesoJudicialBean = tipoProcesoJudicialBean;
    }

    public CodigoBean getTipoRetencionBean() {
        if (tipoRetencionBean == null) {
            tipoRetencionBean = new CodigoBean();
        }
        return tipoRetencionBean;
    }

    public void setTipoRetencionBean(CodigoBean tipoRetencionBean) {
        this.tipoRetencionBean = tipoRetencionBean;
    }

    public CodigoBean getTipoValorBean() {
        if (tipoValorBean == null) {
            tipoValorBean = new CodigoBean();
        }
        return tipoValorBean;
    }

    public void setTipoValorBean(CodigoBean tipoValorBean) {
        this.tipoValorBean = tipoValorBean;
    }

    public CodigoBean getTipoModoPagoBean() {
        if (tipoModoPagoBean == null) {
            tipoModoPagoBean = new CodigoBean();
        }
        return tipoModoPagoBean;
    }

    public void setTipoModoPagoBean(CodigoBean tipoModoPagoBean) {
        this.tipoModoPagoBean = tipoModoPagoBean;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

}
