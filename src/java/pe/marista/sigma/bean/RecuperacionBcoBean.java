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
 * @author MS-001
 */
public class RecuperacionBcoBean implements Serializable {

    private String uniNeg;
    private String txt;
    private Integer idTipoErrorBanco;
    private String codigoError;
    private String codigo;
    private String nombre;
    private Date fechaPago;
    private Date fechaVenc;
    private Double mora;
    private Double montoPension;
    private Integer idCtasXcobrar;

    private Date fechaPagoCta;
    private Double montoPensionCta;
    private Integer mesPens;
    private Integer idCtaPensAnt;
    private Integer pagoMesAnt;
    private Integer idDocIngreso;

    private String mesPension;
    private String NGS;
    private String anio;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public Double getMontoPension() {
        return montoPension;
    }

    public void setMontoPension(Double montoPension) {
        this.montoPension = montoPension;
    }

    public Integer getIdCtasXcobrar() {
        return idCtasXcobrar;
    }

    public void setIdCtasXcobrar(Integer idCtasXcobrar) {
        this.idCtasXcobrar = idCtasXcobrar;
    }

    public Date getFechaPagoCta() {
        return fechaPagoCta;
    }

    public void setFechaPagoCta(Date fechaPagoCta) {
        this.fechaPagoCta = fechaPagoCta;
    }

    public Double getMontoPensionCta() {
        return montoPensionCta;
    }

    public void setMontoPensionCta(Double montoPensionCta) {
        this.montoPensionCta = montoPensionCta;
    }

    public Integer getMesPens() {
        return mesPens;
    }

    public void setMesPens(Integer mesPens) {
        this.mesPens = mesPens;
    }

    public Integer getIdCtaPensAnt() {
        return idCtaPensAnt;
    }

    public void setIdCtaPensAnt(Integer idCtaPensAnt) {
        this.idCtaPensAnt = idCtaPensAnt;
    }

    public Integer getPagoMesAnt() {
        return pagoMesAnt;
    }

    public void setPagoMesAnt(Integer pagoMesAnt) {
        this.pagoMesAnt = pagoMesAnt;
    }

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Integer getIdTipoErrorBanco() {
        return idTipoErrorBanco;
    }

    public void setIdTipoErrorBanco(Integer idTipoErrorBanco) {
        this.idTipoErrorBanco = idTipoErrorBanco;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getMesPension() {
        return mesPension;
    }

    public void setMesPension(String mesPension) {
        this.mesPension = mesPension;
    }

    public String getNGS() {
        return NGS;
    }

    public void setNGS(String NGS) {
        this.NGS = NGS;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
