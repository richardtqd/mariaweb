/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CajeroCajaBean implements Serializable {

    private CajaBean cajaBean;
    private UsuarioBean usuarioBean;
    private UnidadNegocioBean unidadNegocioBean;
    private Date fecIni;
    private Date fecFin;
    private boolean status;
    private Date creaFecha;
    private String creaPor;
    private String modiPor;
    //ayuda apertura -cierre
    private Date fechaApertura = new Date();;
    private Double montoInicial;
    private Date fechaCierre = new Date();;
    private Double montoCierre;
    private String Servicio;
    private Double monto;
    private String nroDoc;
    private Double Saldo;
 
    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }
    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(Double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Double getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(Double montoCierre) {
        this.montoCierre = montoCierre;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String Servicio) {
        this.Servicio = Servicio;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double Saldo) {
        this.Saldo = Saldo;
    }

    public CajeroCajaBean() {
    }

    public CajeroCajaBean(String Servicio, Double monto, String nroDoc) {
        this.Servicio = Servicio;
        this.monto = monto;
        this.nroDoc = nroDoc;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CajaBean getCajaBean() {
        if(cajaBean == null){
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }

    public UsuarioBean getUsuarioBean() {
        if(usuarioBean == null){
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
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
