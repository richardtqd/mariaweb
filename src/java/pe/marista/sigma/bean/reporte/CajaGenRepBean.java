/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Administrador
 */
public class CajaGenRepBean implements Serializable {

    private String nomCaja;
    private String usuario;
    private String nombreUniNeg;
    private String unineg;
    private String nomCompletoPersonal;
    private String nomCompletoSupervisa;
    private Timestamp fecApertura;
    private Timestamp fecCierre;
    private Double aperturaSol;
    private Double aperturaDol;
    private Double ingresoSol;
    private Double ingresoDol;
    private Double ingresoPos1;
    private Double ingresoPos2;
    private Double egresoSol;
    private Double egresoDol;
    private Double saldoSol;
    private Double saldoDol;
    private String codTipDoc;
    private String codTipMon;
    private String codTipPag;
    private String serieNroDoc;
    private String idDiscente;
    private String discente;
    private Double montoPagado;

    private Double tc;
    private Double totDolares;
    private Double sumTotSoles;
    private Double sumTotDolares;
    private Double totSoles;
    private Integer cuentaD;
    private String nomConcepto;
    private JRBeanCollectionDataSource lista;
    private JRBeanCollectionDataSource listaDesglosado;

    //por cuenta 
    private Double montoPorCtaDolares;
    private Double montoPorCtaSoles;
    private String nombreCtaD;
    private String nomTipoConcepto;
    private BigDecimal montoPagadoSol;
    private BigDecimal montoPagadoDol;
    private BigDecimal montoBanco;

    private String nombreCta;
    private Integer cuenta;
    private Integer flgMora;
    private Date fechapago;
    private String fechaPagoStr;
    private String txtFiltro;

    //AYUDA
    private String fechaPagoVista;
    private String fechaIniVista;
    private String fechaFinVista;

    public String getNomCaja() {
        return nomCaja;
    }

    public void setNomCaja(String nomCaja) {
        this.nomCaja = nomCaja;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNomCompletoPersonal() {
        return nomCompletoPersonal;
    }

    public void setNomCompletoPersonal(String nomCompletoPersonal) {
        this.nomCompletoPersonal = nomCompletoPersonal;
    }

    public String getNomCompletoSupervisa() {
        return nomCompletoSupervisa;
    }

    public void setNomCompletoSupervisa(String nomCompletoSupervisa) {
        this.nomCompletoSupervisa = nomCompletoSupervisa;
    }

    public Double getAperturaSol() {
        return aperturaSol;
    }

    public void setAperturaSol(Double aperturaSol) {
        this.aperturaSol = aperturaSol;
    }

    public Double getAperturaDol() {
        return aperturaDol;
    }

    public void setAperturaDol(Double aperturaDol) {
        this.aperturaDol = aperturaDol;
    }

    public Double getIngresoSol() {
        return ingresoSol;
    }

    public void setIngresoSol(Double ingresoSol) {
        this.ingresoSol = ingresoSol;
    }

    public Double getIngresoDol() {
        return ingresoDol;
    }

    public void setIngresoDol(Double ingresoDol) {
        this.ingresoDol = ingresoDol;
    }

    public Double getIngresoPos1() {
        return ingresoPos1;
    }

    public void setIngresoPos1(Double ingresoPos1) {
        this.ingresoPos1 = ingresoPos1;
    }

    public Double getIngresoPos2() {
        return ingresoPos2;
    }

    public void setIngresoPos2(Double ingresoPos2) {
        this.ingresoPos2 = ingresoPos2;
    }

    public Double getSaldoSol() {
        return saldoSol;
    }

    public void setSaldoSol(Double saldoSol) {
        this.saldoSol = saldoSol;
    }

    public Double getSaldoDol() {
        return saldoDol;
    }

    public void setSaldoDol(Double saldoDol) {
        this.saldoDol = saldoDol;
    }

    public String getCodTipDoc() {
        return codTipDoc;
    }

    public void setCodTipDoc(String codTipDoc) {
        this.codTipDoc = codTipDoc;
    }

    public String getCodTipMon() {
        return codTipMon;
    }

    public void setCodTipMon(String codTipMon) {
        this.codTipMon = codTipMon;
    }

    public String getCodTipPag() {
        return codTipPag;
    }

    public void setCodTipPag(String codTipPag) {
        this.codTipPag = codTipPag;
    }

    public String getSerieNroDoc() {
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public String getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(String idDiscente) {
        this.idDiscente = idDiscente;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public Integer getCuentaD() {
        return cuentaD;
    }

    public void setCuentaD(Integer cuentaD) {
        this.cuentaD = cuentaD;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public Timestamp getFecApertura() {
        return fecApertura;
    }

    public void setFecApertura(Timestamp fecApertura) {
        this.fecApertura = fecApertura;
    }

    public Double getEgresoSol() {
        return egresoSol;
    }

    public void setEgresoSol(Double egresoSol) {
        this.egresoSol = egresoSol;
    }

    public Double getEgresoDol() {
        return egresoDol;
    }

    public void setEgresoDol(Double egresoDol) {
        this.egresoDol = egresoDol;
    }

    public JRBeanCollectionDataSource getLista() {
        return lista;
    }

    public void setLista(List<PruebaRepBean> lista) {
        this.lista = new JRBeanCollectionDataSource(lista);
    }

    public String getNombreCtaD() {
        return nombreCtaD;
    }

    public void setNombreCtaD(String nombreCtaD) {
        this.nombreCtaD = nombreCtaD;
    }

    public String getNomTipoConcepto() {
        return nomTipoConcepto;
    }

    public void setNomTipoConcepto(String nomTipoConcepto) {
        this.nomTipoConcepto = nomTipoConcepto;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public BigDecimal getMontoPagadoSol() {
        return montoPagadoSol;
    }

    public void setMontoPagadoSol(BigDecimal montoPagadoSol) {
        this.montoPagadoSol = montoPagadoSol;
    }

    public BigDecimal getMontoPagadoDol() {
        return montoPagadoDol;
    }

    public void setMontoPagadoDol(BigDecimal montoPagadoDol) {
        this.montoPagadoDol = montoPagadoDol;
    }

    public Double getTc() {
        return tc;
    }

    public void setTc(Double tc) {
        this.tc = tc;
    }

    public String getUnineg() {
        return unineg;
    }

    public void setUnineg(String unineg) {
        this.unineg = unineg;
    }

    public Double getMontoPorCtaDolares() {
        return montoPorCtaDolares;
    }

    public void setMontoPorCtaDolares(Double montoPorCtaDolares) {
        this.montoPorCtaDolares = montoPorCtaDolares;
    }

    public Double getMontoPorCtaSoles() {
        return montoPorCtaSoles;
    }

    public void setMontoPorCtaSoles(Double montoPorCtaSoles) {
        this.montoPorCtaSoles = montoPorCtaSoles;
    }

    public Double getTotDolares() {
        return totDolares;
    }

    public void setTotDolares(Double totDolares) {
        this.totDolares = totDolares;
    }

    public Double getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(Double totSoles) {
        this.totSoles = totSoles;
    }

    public Double getSumTotSoles() {
        return sumTotSoles;
    }

    public void setSumTotSoles(Double sumTotSoles) {
        this.sumTotSoles = sumTotSoles;
    }

    public Double getSumTotDolares() {
        return sumTotDolares;
    }

    public void setSumTotDolares(Double sumTotDolares) {
        this.sumTotDolares = sumTotDolares;
    }

    public String getNombreCta() {
        return nombreCta;
    }

    public void setNombreCta(String nombreCta) {
        this.nombreCta = nombreCta;
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public Timestamp getFecCierre() {
        return fecCierre;
    }

    public void setFecCierre(Timestamp fecCierre) {
        this.fecCierre = fecCierre;
    }

    public BigDecimal getMontoBanco() {
        return montoBanco;
    }

    public void setMontoBanco(BigDecimal montoBanco) {
        this.montoBanco = montoBanco;
    }

    public Integer getFlgMora() {
        return flgMora;
    }

    public void setFlgMora(Integer flgMora) {
        this.flgMora = flgMora;
    }

    public JRBeanCollectionDataSource getListaDesglosado() {
        return listaDesglosado;
    }

    public void setListaDesglosado(List<DocIngresoRepBeanDesglosado> listaDesglosado) {
        this.listaDesglosado = new JRBeanCollectionDataSource(listaDesglosado);
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public String getFechaPagoStr() {
        return fechaPagoStr;
    }

    public void setFechaPagoStr(String fechaPagoStr) {
        this.fechaPagoStr = fechaPagoStr;
    }

    public String getTxtFiltro() {
        return txtFiltro;
    }

    public void setTxtFiltro(String txtFiltro) {
        this.txtFiltro = txtFiltro;
    }

    public String getFechaPagoVista() {
        return fechaPagoVista;
    }

    public void setFechaPagoVista(String fechaPagoVista) {
        this.fechaPagoVista = fechaPagoVista;
    }

    public String getFechaIniVista() {
        return fechaIniVista;
    }

    public void setFechaIniVista(String fechaIniVista) {
        this.fechaIniVista = fechaIniVista;
    }

    public String getFechaFinVista() {
        return fechaFinVista;
    }

    public void setFechaFinVista(String fechaFinVista) {
        this.fechaFinVista = fechaFinVista;
    }

}
