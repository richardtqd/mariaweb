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
public class LiquidacionRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String nombreSolicitante;
    private String montoAprobado;
    private String montoDev;
    private String montoTotalLiq;
    private String diferencia;
    private String cr;
    private String cuentad;
    private String montoLiq;
    private String numComprobante;
    private String moneda;
    private String glosa;
    private String proveedor;
    private String fechaLiq;
    private String numCheque;
    private String fechaDocE;
    private String fechaDoc;
    private Integer totalComprobantes;
    private Integer idCajaChicaLiquidacion;
    private JRBeanCollectionDataSource listaDetLiquidacionRepBean;

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

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public String getMontoAprobado() {
        return montoAprobado;
    }

    public void setMontoAprobado(String montoAprobado) {
        this.montoAprobado = montoAprobado;
    }

    public String getMontoDev() {
        return montoDev;
    }

    public void setMontoDev(String montoDev) {
        this.montoDev = montoDev;
    }

    public String getMontoTotalLiq() {
        return montoTotalLiq;
    }

    public void setMontoTotalLiq(String montoTotalLiq) {
        this.montoTotalLiq = montoTotalLiq;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getCuentad() {
        return cuentad;
    }

    public void setCuentad(String cuentad) {
        this.cuentad = cuentad;
    }

    public String getMontoLiq() {
        return montoLiq;
    }

    public void setMontoLiq(String montoLiq) {
        this.montoLiq = montoLiq;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaLiq() {
        return fechaLiq;
    }

    public void setFechaLiq(String fechaLiq) {
        this.fechaLiq = fechaLiq;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getFechaDocE() {
        return fechaDocE;
    }

    public void setFechaDocE(String fechaDocE) {
        this.fechaDocE = fechaDocE;
    }

    public String getFechaDoc() {
        return fechaDoc;
    }

    public void setFechaDoc(String fechaDoc) {
        this.fechaDoc = fechaDoc;
    }

    public Integer getTotalComprobantes() {
        return totalComprobantes;
    }

    public void setTotalComprobantes(Integer totalComprobantes) {
        this.totalComprobantes = totalComprobantes;
    }

    public Integer getIdCajaChicaLiquidacion() {
        return idCajaChicaLiquidacion;
    }

    public void setIdCajaChicaLiquidacion(Integer idCajaChicaLiquidacion) {
        this.idCajaChicaLiquidacion = idCajaChicaLiquidacion;
    }

    public JRBeanCollectionDataSource getListaDetLiquidacionRepBean() {
        return listaDetLiquidacionRepBean;
    }

    public void setListaDetLiquidacionRepBean(List<LiquidacionRepBean> listaDetLiquidacionRepBean) {
        this.listaDetLiquidacionRepBean = new JRBeanCollectionDataSource(listaDetLiquidacionRepBean);
    }
}
