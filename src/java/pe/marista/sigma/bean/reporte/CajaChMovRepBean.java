/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class CajaChMovRepBean implements Serializable{
    private String uniNeg;
    private String nombreUniNeg;
    private String rucUniNeg;
    private String tipoCaja;
    private String nombreCompletoCajero;
    private String fecApertura;
    private String fecCierre;
    private String aperturaSol;
    private String devueltoSol;
    private String utilizadoSol;
    private String saldoSol;
    private String aperturaDol;
    private String devueltoDol;
    private String utilizadoDol;
    private String saldoDol;
    private String motivo;
    private String nombreTS;
    private String fecPago;
    private String flgMov;
    private String monto;
    private String nombreCompleto;    
    private String montoEntSoles;
    private String montoSalSoles;
    private String montoEntDolares;
    private String montoSalDolares;
    private String sumSoles;
    private String sumDolares;
    private String montoSoles;
    private String montoDolares;
    private String tipo;
    private String obs;
    
    private String cuentaCR; 
    private JRBeanCollectionDataSource listaDetalle;
    private Integer idSolicitudCajaCH;
    
    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucUniNeg() {
        return rucUniNeg;
    }

    public void setRucUniNeg(String rucUniNeg) {
        this.rucUniNeg = rucUniNeg;
    }

    public String getTipoCaja() {
        return tipoCaja;
    }

    public void setTipoCaja(String tipoCaja) {
        this.tipoCaja = tipoCaja;
    }

    public String getNombreCompletoCajero() {
        return nombreCompletoCajero;
    }

    public void setNombreCompletoCajero(String nombreCompletoCajero) {
        this.nombreCompletoCajero = nombreCompletoCajero;
    }

    public String getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(String fecApertura) {
        this.fecApertura = fecApertura;
    }

    public String getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(String fecCierre) {
        this.fecCierre = fecCierre;
    }

    public String getAperturaSol() {
        return aperturaSol;
    }

    public void setAperturaSol(String aperturaSol) {
        this.aperturaSol = aperturaSol;
    }

    public String getDevueltoSol() {
        return devueltoSol;
    }

    public void setDevueltoSol(String devueltoSol) {
        this.devueltoSol = devueltoSol;
    }

    public String getUtilizadoSol() {
        return utilizadoSol;
    }

    public void setUtilizadoSol(String utilizadoSol) {
        this.utilizadoSol = utilizadoSol;
    }

    public String getSaldoSol() {
        return saldoSol;
    }

    public void setSaldoSol(String saldoSol) {
        this.saldoSol = saldoSol;
    }

    public String getAperturaDol() {
        return aperturaDol;
    }

    public void setAperturaDol(String aperturaDol) {
        this.aperturaDol = aperturaDol;
    }

    public String getDevueltoDol() {
        return devueltoDol;
    }

    public void setDevueltoDol(String devueltoDol) {
        this.devueltoDol = devueltoDol;
    }

    public String getUtilizadoDol() {
        return utilizadoDol;
    }

    public void setUtilizadoDol(String utilizadoDol) {
        this.utilizadoDol = utilizadoDol;
    }

    public String getSaldoDol() {
        return saldoDol;
    }

    public void setSaldoDol(String saldoDol) {
        this.saldoDol = saldoDol;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNombreTS() {
        return nombreTS;
    }

    public void setNombreTS(String nombreTS) {
        this.nombreTS = nombreTS;
    }

    public String getFecPago() {
        return fecPago;
    }

    public void setFecPago(String fecPago) {
        this.fecPago = fecPago;
    }

    public String getFlgMov() {
        return flgMov;
    }

    public void setFlgMov(String flgMov) {
        this.flgMov = flgMov;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMontoEntSoles() {
        return montoEntSoles;
    }

    public void setMontoEntSoles(String montoEntSoles) {
        this.montoEntSoles = montoEntSoles;
    }

    public String getMontoSalSoles() {
        return montoSalSoles;
    }

    public void setMontoSalSoles(String montoSalSoles) {
        this.montoSalSoles = montoSalSoles;
    }

    public String getMontoEntDolares() {
        return montoEntDolares;
    }

    public void setMontoEntDolares(String montoEntDolares) {
        this.montoEntDolares = montoEntDolares;
    }

    public String getMontoSalDolares() {
        return montoSalDolares;
    }

    public void setMontoSalDolares(String montoSalDolares) {
        this.montoSalDolares = montoSalDolares;
    }

    public String getSumSoles() {
        return sumSoles;
    }

    public void setSumSoles(String sumSoles) {
        this.sumSoles = sumSoles;
    }

    public String getSumDolares() {
        return sumDolares;
    }

    public void setSumDolares(String sumDolares) {
        this.sumDolares = sumDolares;
    }

    public String getMontoSoles() {
        return montoSoles;
    }

    public void setMontoSoles(String montoSoles) {
        this.montoSoles = montoSoles;
    }

    public String getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(String montoDolares) {
        this.montoDolares = montoDolares;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<CajaChMovRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    } 
 
    public String getCuentaCR() {
        return cuentaCR;
    }

    public void setCuentaCR(String cuentaCR) {
        this.cuentaCR = cuentaCR;
    }

    public Integer getIdSolicitudCajaCH() {
        return idSolicitudCajaCH;
    }

    public void setIdSolicitudCajaCH(Integer idSolicitudCajaCH) {
        this.idSolicitudCajaCH = idSolicitudCajaCH;
    }
}
