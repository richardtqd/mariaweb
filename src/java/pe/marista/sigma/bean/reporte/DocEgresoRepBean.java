/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.joda.time.DateTime;

/**
 *
 * @author MS001
 */
public class DocEgresoRepBean {

    private Integer nroDocEgreso;
    private String uniNeg;
    private String nroDocEgresoStr;
    private String nomEnt;
    private String nomBanco;
    private String fecha;
    private String numCheque;
    private String labelModoPago;
    private String simbolo;
    private String autorizadores;
    private String montoTotalVista;
    private String textoMonto;
    private Double montoTotal;
    private Double tc;
    private JRBeanCollectionDataSource listaDetDocEgresoRepBean;
    private String nombreUniNeg;// 
    private String rucUniNeg;//
    private String mes;//

    //cheque
    private String nomRespCheque;
    private String glosa;
    private Double montoPagadoSol;
    private Double montoPagadoDol;
    private Double montoTotSol;
    private Double montoTotDol;
    private Date fechaInicio;
    private Date fechaFin;

    private Integer idTipoConcepto;
    private Integer idConcepto;
    private String nomTipoConcepto;// 
    private String nomConcepto;// 
    private String cuenta;// 
    private String modoPago;//  
    private String tipoSolicitud;//  
    private String tipoDocEgreso;//  
    private String descripTransfCta;//  
    
    private String creaFecha;

    //Cambio
    private String estadoCheque;
    
    //ayuda lpm
    private Integer moneda;
    private String tipoMedioPago;
    private String nroDoc; 
    private Double montoPagado;
    

    public Integer getNroDocEgreso() {
        return nroDocEgreso;
    }

    public void setNroDocEgreso(Integer nroDocEgreso) {
        this.nroDocEgreso = nroDocEgreso;
    }

    public String getNomEnt() {
        return nomEnt;
    }

    public void setNomEnt(String nomEnt) {
        this.nomEnt = nomEnt;
    }

    public String getNomBanco() {
        return nomBanco;
    }

    public void setNomBanco(String nomBanco) {
        this.nomBanco = nomBanco;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public JRBeanCollectionDataSource getListaDetDocEgresoRepBean() {
        return listaDetDocEgresoRepBean;
    }

    public void setListaDetDocEgresoRepBean(List<DetDocEgresoRepBean> listaDetDocEgresoRepBean) {
        this.listaDetDocEgresoRepBean = new JRBeanCollectionDataSource(listaDetDocEgresoRepBean);
    }

    public String getTextoMonto() {
        return textoMonto;
    }

    public void setTextoMonto(String textoMonto) {
        this.textoMonto = textoMonto;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getTc() {
        return tc;
    }

    public void setTc(Double tc) {
        this.tc = tc;
    }

    public String getNroDocEgresoStr() {
        return nroDocEgresoStr;
    }

    public void setNroDocEgresoStr(String nroDocEgresoStr) {
        this.nroDocEgresoStr = nroDocEgresoStr;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getAutorizadores() {
        return autorizadores;
    }

    public void setAutorizadores(String autorizadores) {
        this.autorizadores = autorizadores;
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

    public String getNomRespCheque() {
        return nomRespCheque;
    }

    public void setNomRespCheque(String nomRespCheque) {
        this.nomRespCheque = nomRespCheque;
    }

    public Double getMontoPagadoSol() {
        return montoPagadoSol;
    }

    public void setMontoPagadoSol(Double montoPagadoSol) {
        this.montoPagadoSol = montoPagadoSol;
    }

    public Double getMontoPagadoDol() {
        return montoPagadoDol;
    }

    public void setMontoPagadoDol(Double montoPagadoDol) {
        this.montoPagadoDol = montoPagadoDol;
    }

    public Double getMontoTotSol() {
        return montoTotSol;
    }

    public void setMontoTotSol(Double montoTotSol) {
        this.montoTotSol = montoTotSol;
    }

    public Double getMontoTotDol() {
        return montoTotDol;
    }

    public void setMontoTotDol(Double montoTotDol) {
        this.montoTotDol = montoTotDol;
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

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getMontoTotalVista() {
        return montoTotalVista;
    }

    public void setMontoTotalVista(String montoTotalVista) {
        this.montoTotalVista = montoTotalVista;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getEstadoCheque() {
        return estadoCheque;
    }

    public void setEstadoCheque(String estadoCheque) {
        this.estadoCheque = estadoCheque;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getLabelModoPago() {
        return labelModoPago;
    }

    public void setLabelModoPago(String labelModoPago) {
        this.labelModoPago = labelModoPago;
    }

    public String getTipoDocEgreso() {
        return tipoDocEgreso;
    }

    public void setTipoDocEgreso(String tipoDocEgreso) {
        this.tipoDocEgreso = tipoDocEgreso;
    }

    public String getDescripTransfCta() {
        return descripTransfCta;
    }

    public void setDescripTransfCta(String descripTransfCta) {
        this.descripTransfCta = descripTransfCta;
    }  

    public String getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(String creaFecha) {
        this.creaFecha = creaFecha;
    }

    public Integer getMoneda() {
        return moneda;
    }

    public void setMoneda(Integer moneda) {
        this.moneda = moneda;
    }

    public String getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(String tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }  

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }
}
