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
public class DetEstadoCtaRepBean implements Serializable{
    
 
    private String mes;
    private String facturado;
    private String dsctobeca;
    private String importe;
    private String mora;
    private String dscto;
    private String canceladoSinMora;
    private String canceladoConM;
    private String porcentajeImp;
    private String saldo;
    private String porcentajeSaldo; 


    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getFacturado() {
        return facturado;
    }

    public void setFacturado(String facturado) {
        this.facturado = facturado;
    }

    public String getDsctobeca() {
        return dsctobeca;
    }

    public void setDsctobeca(String dsctobeca) {
        this.dsctobeca = dsctobeca;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
    }

    public String getDscto() {
        return dscto;
    }

    public void setDscto(String dscto) {
        this.dscto = dscto;
    }

    public String getCanceladoSinMora() {
        return canceladoSinMora;
    }

    public void setCanceladoSinMora(String canceladoSinMora) {
        this.canceladoSinMora = canceladoSinMora;
    }

    public String getCanceladoConM() {
        return canceladoConM;
    }

    public void setCanceladoConM(String canceladoConM) {
        this.canceladoConM = canceladoConM;
    }

    public String getPorcentajeImp() {
        return porcentajeImp;
    }

    public void setPorcentajeImp(String porcentajeImp) {
        this.porcentajeImp = porcentajeImp;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getPorcentajeSaldo() {
        return porcentajeSaldo;
    }

    public void setPorcentajeSaldo(String porcentajeSaldo) {
        this.porcentajeSaldo = porcentajeSaldo;
    }
    
    
    
}
