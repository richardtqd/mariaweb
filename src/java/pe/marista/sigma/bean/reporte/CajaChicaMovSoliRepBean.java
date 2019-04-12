/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class CajaChicaMovSoliRepBean {

    private String fechaSol;
    private String nomRespCheque;
    private String motivo;
    private Double monto;
    private String moneda;
    private JRBeanCollectionDataSource listaCentros;
    private Integer idSolicitudCajaCh;

    public String getFechaSol() {
        return fechaSol;
    }

    public void setFechaSol(String fechaSol) {
        this.fechaSol = fechaSol;
    } 

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public JRBeanCollectionDataSource getListaCentros() {
        return listaCentros;
    }

    public void setListaCentros(List<CajaChicaMovCentroRepBean> listaCentros) {
        this.listaCentros = new JRBeanCollectionDataSource(listaCentros);
    }

    public Integer getIdSolicitudCajaCh() {
        return idSolicitudCajaCh;
    }

    public void setIdSolicitudCajaCh(Integer idSolicitudCajaCh) {
        this.idSolicitudCajaCh = idSolicitudCajaCh;
    }

    public String getNomRespCheque() {
        return nomRespCheque;
    }

    public void setNomRespCheque(String nomRespCheque) {
        this.nomRespCheque = nomRespCheque;
    }
    
}
