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
public class CajaChicaMovCRRepBean {
    private String nombreUniNeg;
    private String rucColegio;    
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
    private Integer cr;
    private String nombreCr;
    private Double valor;
    private JRBeanCollectionDataSource listaMovimientosCaja;
    private JRBeanCollectionDataSource listaCRDetalladito;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucColegio() {
        return rucColegio;
    }

    public void setRucColegio(String rucColegio) {
        this.rucColegio = rucColegio;
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
 
    public JRBeanCollectionDataSource getListaMovimientosCaja() {
        return listaMovimientosCaja;
    }

    public void setListaMovimientosCaja(List<CajaChicaMovSoliRepBean> listaMovimientosCaja) {
        this.listaMovimientosCaja = new JRBeanCollectionDataSource(listaMovimientosCaja);
    } 
    
    public JRBeanCollectionDataSource getListaCRDetalladito() {
        return listaCRDetalladito;
    }

    public void setListaCRDetalladito(List<CrDetalladitoRepBean> listaCRDetalladito) {
        this.listaCRDetalladito = new JRBeanCollectionDataSource(listaCRDetalladito);
    } 

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getNombreCr() {
        return nombreCr;
    }

    public void setNombreCr(String nombreCr) {
        this.nombreCr = nombreCr;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    
    
    
}
