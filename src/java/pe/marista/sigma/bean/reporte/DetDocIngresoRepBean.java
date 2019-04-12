/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class DetDocIngresoRepBean implements Serializable {

    private Integer idDocIngreso;
    private String nombre;
    private Integer cuentaD;
    private Double montoPagado;
    private String cantidad;
    private Double importe;
    private Double montoTot;
    private String textoMonto;
    private String tipo;
    private String moneda;
    //movimientos
    private String uniNeg;
    private String nombreUniNeg;
    private Integer idDetDocIngreso;
    private Integer cuenta;
    private Integer idTipoConcepto;
    private String nomTipoConcepto;
    private Integer idConcepto;
    private String nomConcepto;
    private String nomCuenta;
    private String referencia;
    private Date fechaInicio;
    private Date fechaFin;
    private String fechaVista;
    private String discente;
    private Double sumTotDolares;
    private Double sumTotSoles;
    private Double montoSoles;
    private Double montoDolares;
    private String serieNroDoc;
    private String estadoDocIng;

    private String montoVista;
    private String montoPagadoVista;
    private String strMontoPagado;
    private String strImporte;

    //CAMBIOS REPORTE MASIVO
    private String totalPago;
     private String cuentaDsctoBeca;
    private String labelDsctoBeca;
    private String qr;
    private String numOperacion;
    private Double mora;
    private Double monto;
    private Double montoDscto;
    private Double dsctoBeca;
    private String referencial;

    private String modoPagoVista;
    private Double montoPos1;
    private Double montoPos2;
    private Double montoEfectivo;
    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(Integer cuentaD) {
        this.cuentaD = cuentaD;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTextoMonto() {
        return textoMonto;
    }

    public void setTextoMonto(String textoMonto) {
        this.textoMonto = textoMonto;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getMontoTot() {
        return montoTot;
    }

    public void setMontoTot(Double montoTot) {
        this.montoTot = montoTot;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    ////////////////////////////////
    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
    }

    public String getNomTipoConcepto() {
        return nomTipoConcepto;
    }

    public void setNomTipoConcepto(String nomTipoConcepto) {
        this.nomTipoConcepto = nomTipoConcepto;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(String fechaVista) {
        this.fechaVista = fechaVista;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public Double getSumTotDolares() {
        return sumTotDolares;
    }

    public void setSumTotDolares(Double sumTotDolares) {
        this.sumTotDolares = sumTotDolares;
    }

    public Double getSumTotSoles() {
        return sumTotSoles;
    }

    public void setSumTotSoles(Double sumTotSoles) {
        this.sumTotSoles = sumTotSoles;
    }

    public Double getMontoSoles() {
        return montoSoles;
    }

    public void setMontoSoles(Double montoSoles) {
        this.montoSoles = montoSoles;
    }

    public Double getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(Double montoDolares) {
        this.montoDolares = montoDolares;
    }

    public String getSerieNroDoc() {
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getEstadoDocIng() {
        return estadoDocIng;
    }

    public void setEstadoDocIng(String estadoDocIng) {
        this.estadoDocIng = estadoDocIng;
    }

    public String getMontoVista() {
        return montoVista;
    }

    public void setMontoVista(String montoVista) {
        this.montoVista = montoVista;
    }

    public String getMontoPagadoVista() {
        return montoPagadoVista;
    }

    public void setMontoPagadoVista(String montoPagadoVista) {
        this.montoPagadoVista = montoPagadoVista;
    }

    public Integer getIdDetDocIngreso() {
        return idDetDocIngreso;
    }

    public void setIdDetDocIngreso(Integer idDetDocIngreso) {
        this.idDetDocIngreso = idDetDocIngreso;
    }

    public String getStrMontoPagado() {
        return strMontoPagado;
    }

    public void setStrMontoPagado(String strMontoPagado) {
        this.strMontoPagado = strMontoPagado;
    }

    public String getStrImporte() {
        return strImporte;
    }

    public void setStrImporte(String strImporte) {
        this.strImporte = strImporte;
    }

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
    }

    public String getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(String totalPago) {
        this.totalPago = totalPago;
    }

//    public String getDsctobeca() {
//        return dsctobeca;
//    }
//
//    public void setDsctobeca(String dsctobeca) {
//        this.dsctobeca = dsctobeca;
//    }

    public String getCuentaDsctoBeca() {
        return cuentaDsctoBeca;
    }

    public void setCuentaDsctoBeca(String cuentaDsctoBeca) {
        this.cuentaDsctoBeca = cuentaDsctoBeca;
    }

    public String getLabelDsctoBeca() {
        return labelDsctoBeca;
    }

    public void setLabelDsctoBeca(String labelDsctoBeca) {
        this.labelDsctoBeca = labelDsctoBeca;
    }

    public String getNomCuenta() {
        return nomCuenta;
    }

    public void setNomCuenta(String nomCuenta) {
        this.nomCuenta = nomCuenta;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }   

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getMontoDscto() {
        return montoDscto;
    }

    public void setMontoDscto(Double montoDscto) {
        this.montoDscto = montoDscto;
    }

    public Double getDsctoBeca() {
        return dsctoBeca;
    }

    public void setDsctoBeca(Double dsctoBeca) {
        this.dsctoBeca = dsctoBeca;
    }

    public String getReferencial() {
        return referencial;
    }

    public void setReferencial(String referencial) {
        this.referencial = referencial;
    }

    public String getModoPagoVista() {
        return modoPagoVista;
    }

    public void setModoPagoVista(String modoPagoVista) {
        this.modoPagoVista = modoPagoVista;
    }

    public Double getMontoPos1() {
        return montoPos1;
    }

    public void setMontoPos1(Double montoPos1) {
        this.montoPos1 = montoPos1;
    }

    public Double getMontoPos2() {
        return montoPos2;
    }

    public void setMontoPos2(Double montoPos2) {
        this.montoPos2 = montoPos2;
    }

    public Double getMontoEfectivo() {
        return montoEfectivo;
    }

    public void setMontoEfectivo(Double montoEfectivo) {
        this.montoEfectivo = montoEfectivo;
    }
 

}
