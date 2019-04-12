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
 * @author MS002
 */
public class CajaBean implements Serializable {

    private Integer idCaja;    
    private UnidadNegocioBean unidadNegocioBean;//idUniNeg
    private String nombre;
    private String hostIp;
    private String hostName;
    private String mac;
    private boolean status;
    private Date creaFecha;
    private String creaPor;
    private String modiPor;
    //Ayuda Estado
    private boolean estado;
    //Ayuda ip
    private String ip1;
    private String ip2;
    private String ip3;
    private String ip4;
    //Ayuda mac
    private String mac1;
    private String mac2;
    private String mac3;
    private String mac4;
    private String mac5;
    private String mac6;  

    //ayuda apertura -cierre
    private Date fechaApertura = new Date();
    private Double montoInicial;
    
    private Date fechaCierre = new Date();
    private Double montoCierre;
    
    
    public CajaBean(String nombre) {
        this.nombre = nombre;
    }
    public CajaBean() {

    }
    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String codigo) {
        this.nombre = codigo;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public boolean getEstadoActual() {
        return estado;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getIp1() {
        return ip1;
    }

    public void setIp1(String ip1) {
        this.ip1 = ip1;
    }

    public String getIp2() {
        return ip2;
    }

    public void setIp2(String ip2) {
        this.ip2 = ip2;
    }

    public String getIp3() {
        return ip3;
    }

    public void setIp3(String ip3) {
        this.ip3 = ip3;
    }

    public String getIp4() {
        return ip4;
    }

    public void setIp4(String ip4) {
        this.ip4 = ip4;
    }
    public String getIpCompleto(){
        StringBuilder sb = new StringBuilder();
        sb.append(ip1).append(".").append(ip2).append(".").append(ip3).append(".").append(ip4);
        return sb.toString();
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getMac1() {
        return mac1;
    }

    public void setMac1(String mac1) {
        this.mac1 = mac1;
    }

    public String getMac2() {
        return mac2;
    }

    public void setMac2(String mac2) {
        this.mac2 = mac2;
    }

    public String getMac3() {
        return mac3;
    }

    public void setMac3(String mac3) {
        this.mac3 = mac3;
    }

    public String getMac4() {
        return mac4;
    }

    public void setMac4(String mac4) {
        this.mac4 = mac4;
    }

    public String getMac5() {
        return mac5;
    }

    public void setMac5(String mac5) {
        this.mac5 = mac5;
    }

    public String getMac6() {
        return mac6;
    }

    public void setMac6(String mac6) {
        this.mac6 = mac6;
    }
    
    public String getMacCompleto(){
        StringBuilder sb = new StringBuilder();
        sb.append(mac1).append(".").append(mac2).append(".").append(mac3).append(".").append(mac4).append(".").append(mac5).append(".").append(mac6);
        return sb.toString();
    }
    
}
