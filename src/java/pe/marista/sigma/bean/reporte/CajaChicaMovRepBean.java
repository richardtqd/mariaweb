/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class CajaChicaMovRepBean implements Serializable {

    private String uniNeg;
    private String nombreUniNeg;
    private String codigo;
    private Date fecApertura;
    private Date fecCierre;
    private Double aperturasol;
    private Double devueltoSol;
    private Double utilizadosol;
    private Double saldosol;
    private Double aperturadol;
    private Double devueltoDol;
    private Double utilizadodol;
    private Double saldoDol;
    private String motivo;
    private String nombreTS;
    private Date fecPago;
    private Boolean flgMov;
    private String nombreCompleto;
    private String nombreCompletoCajero;
    private String codigoMoneda;
    private Double monto;
    private Date creafecha;
    private Double montoEntSoles;
    private Double montoSalSoles;
    private Double montoEntDolares;
    private Double montoSalDolares;

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(Date fecApertura) {
        this.fecApertura = fecApertura;
    }

    public Date getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(Date fecCierre) {
        this.fecCierre = fecCierre;
    }

    public Double getAperturasol() {
        return aperturasol;
    }

    public void setAperturasol(Double aperturasol) {
        this.aperturasol = aperturasol;
    }

    public Double getDevueltoSol() {
        return devueltoSol;
    }

    public void setDevueltoSol(Double devueltoSol) {
        this.devueltoSol = devueltoSol;
    }

    public Double getUtilizadosol() {
        return utilizadosol;
    }

    public void setUtilizadosol(Double utilizadosol) {
        this.utilizadosol = utilizadosol;
    }

    public Double getSaldosol() {
        return saldosol;
    }

    public void setSaldosol(Double saldosol) {
        this.saldosol = saldosol;
    }

    public Double getAperturadol() {
        return aperturadol;
    }

    public void setAperturadol(Double aperturadol) {
        this.aperturadol = aperturadol;
    }

    public Double getDevueltoDol() {
        return devueltoDol;
    }

    public void setDevueltoDol(Double devueltoDol) {
        this.devueltoDol = devueltoDol;
    }

    public Double getUtilizadodol() {
        return utilizadodol;
    }

    public void setUtilizadodol(Double utilizadodol) {
        this.utilizadodol = utilizadodol;
    }

    public Double getSaldoDol() {
        return saldoDol;
    }

    public void setSaldoDol(Double saldoDol) {
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

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

    public Boolean getFlgMov() {
        return flgMov;
    }

    public void setFlgMov(Boolean flgMov) {
        this.flgMov = flgMov;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompletoCajero() {
        return nombreCompletoCajero;
    }

    public void setNombreCompletoCajero(String nombreCompletoCajero) {
        this.nombreCompletoCajero = nombreCompletoCajero;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getCreafecha() {
        return creafecha;
    }

    public void setCreafecha(Date creafecha) {
        this.creafecha = creafecha;
    }

    public Double getMontoEntSoles() {
//        if (montoEntSoles != null) {
//            System.out.println("uno: "+montoEntSoles);
////            String decimal= new DecimalFormat("###,##0.00").format(montoEntSoles);
//            String decimal= String.format( "%.2f", montoEntSoles);
//            System.out.println("dos: "+decimal);
//            return decimal;
//        }
        return montoEntSoles;
    }

    public void setMontoEntSoles(Double montoEntSoles) {
        this.montoEntSoles = montoEntSoles;
    }

    public Double getMontoSalSoles() {
        return montoSalSoles;
    }

    public void setMontoSalSoles(Double montoSalSoles) {
        this.montoSalSoles = montoSalSoles;
    }

    public Double getMontoEntDolares() {
//        if (montoEntDolares != null) {
//            System.out.println("uno: "+montoEntDolares);
////            String decimal= new DecimalFormat("###,##0.00").format(montoEntDolares);
//            String decimal= String.format( "%.2f", montoEntDolares);
//            System.out.println("dos: "+decimal);
//            return decimal;
//        }
        return montoEntDolares;
    }

    public void setMontoEntDolares(Double montoEntDolares) {
        this.montoEntDolares = montoEntDolares;
    }

    public Double getMontoSalDolares() {
        return montoSalDolares;
    }

    public void setMontoSalDolares(Double montoSalDolares) {
        this.montoSalDolares = montoSalDolares;
    }

}
