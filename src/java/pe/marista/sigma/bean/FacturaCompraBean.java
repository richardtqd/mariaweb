package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.marista.sigma.MaristaConstantes;

public class FacturaCompraBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idFacturaCompra;
    private RegistroCompraBean registroCompraBean;

    private String serieDoc;
    private String nroDoc;

    private CodigoBean tipoMonedaBean;

    private DetraccionBean detraccionBean;

    private CodigoBean tipoStatusFacturaBean;
    private TipoSolicitudBean tipoSolicitudBean;
    private CodigoBean tipoPrioridadBean;

    //idAutoriza1
    private PersonalBean idAutorizaPer1Bean;
    private Boolean flgAutoriza1;
    private Date fecAutoriza1;
    //idAutoriza2
    private PersonalBean idAutorizaPer2Bean;
    private Boolean flgAutoriza2;
    private Date fecAutoriza2;
    //idAutoriza3
    private PersonalBean idAutorizaPer3Bean;
    private Boolean flgAutoriza3;
    private Date fecAutoriza3;

    private Integer nivelAutoriza;
    private Date fechaVenc;
    private String obsVenc;
    private Date fechaProg;

    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String serieNroDoc;

    private Boolean flgFactura;
    private Boolean flgBoleta;
    private Boolean flgRecibo;

    //ayuda
    private Date fechaInicio;
    private Date fechaFin;
    private String objeto;

    private Boolean flgIGV;

    private OrdenCompraBean ordenCompraBean;

    //AYUDA 
    private Integer idCompra;
    private String glosaCompra;
    private String tipoCompra;
    private String rucCompra;
    private String entidadCompra;

    private String tablaVista;

    private Boolean flgAdelanto;

    //NUEVO
    private List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean;
    private Integer codDistribucion;
    private DetRegistroCompraCRBean detRequerimientoCRBean;
    private String glosa;

    //ayuda
    private Integer idTipoModoPago;
    private String codModoPago;

    private Integer nroFact = 0;

    //Cuenta
    private ConceptoUniNegBean conceptoUniNegBean;
    private PlanContableBean planContableCuentaDSoliBean;
    private PlanContableBean planContableCuentaHSoliBean;
    private List<CuentaFacturaBean> listaCuentaFacturaBean;

    private String nroNotaCredito;
    private String solicitante;

    //Montos
    private Double importeSinIgv;
    private Double igv; // porcentaje
    private Double impuesto;
    private Double importe;
    private Double valorDetraccion;
    private Double montoPago;

    private Double igvResultado;
    private Double detraccion;
    private Double dsctoNotCred;
    private Boolean flgAutorizar;

    public FacturaCompraBean() {
    }

    public String getSerieNroDoc() {
        StringBuilder sb = new StringBuilder();
        if (serieDoc != null) {
            sb.append(serieDoc).append("-");
        }
        if (nroDoc != null) {
            sb.append(nroDoc);
        }

        serieNroDoc = sb.toString();
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public DetraccionBean getDetraccionBean() {
        if (detraccionBean == null) {
            detraccionBean = new DetraccionBean();
        }
        return detraccionBean;
    }

    public void setDetraccionBean(DetraccionBean detraccionBean) {
        this.detraccionBean = detraccionBean;
    }

    public Double getValorDetraccion() {
        return valorDetraccion;
    }

    public void setValorDetraccion(Double valorDetraccion) {
        this.valorDetraccion = valorDetraccion;
    }

    public CodigoBean getTipoStatusFacturaBean() {
        if (tipoStatusFacturaBean == null) {
            tipoStatusFacturaBean = new CodigoBean();
        }
        return tipoStatusFacturaBean;
    }

    public void setTipoStatusFacturaBean(CodigoBean tipoStatusFacturaBean) {
        this.tipoStatusFacturaBean = tipoStatusFacturaBean;
    }

    public TipoSolicitudBean getTipoSolicitudBean() {
        if (tipoSolicitudBean == null) {
            tipoSolicitudBean = new TipoSolicitudBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public CodigoBean getTipoPrioridadBean() {
        if (tipoPrioridadBean == null) {
            tipoPrioridadBean = new CodigoBean();
        }
        return tipoPrioridadBean;
    }

    public void setTipoPrioridadBean(CodigoBean tipoPrioridadBean) {
        this.tipoPrioridadBean = tipoPrioridadBean;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Date getFechaProg() {
        return fechaProg;
    }

    public void setFechaProg(Date fechaProg) {
        this.fechaProg = fechaProg;
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

    public Integer getIdFacturaCompra() {
        return idFacturaCompra;
    }

    public void setIdFacturaCompra(Integer idFacturaCompra) {
        this.idFacturaCompra = idFacturaCompra;
    }

    public RegistroCompraBean getRegistroCompraBean() {
        if (registroCompraBean == null) {
            registroCompraBean = new RegistroCompraBean();
        }
        return registroCompraBean;
    }

    public void setRegistroCompraBean(RegistroCompraBean registroCompraBean) {
        this.registroCompraBean = registroCompraBean;
    }

    public String getSerieDoc() {
        return serieDoc;
    }

    public void setSerieDoc(String serieDoc) {
        this.serieDoc = serieDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public CodigoBean getTipoMonedaBean() {
        if (tipoMonedaBean == null) {
            tipoMonedaBean = new CodigoBean();
        }
        return tipoMonedaBean;
    }

    public void setTipoMonedaBean(CodigoBean tipoMonedaBean) {
        this.tipoMonedaBean = tipoMonedaBean;
    }

    public PersonalBean getIdAutorizaPer1Bean() {
        if (idAutorizaPer1Bean == null) {
            idAutorizaPer1Bean = new PersonalBean();
        }
        return idAutorizaPer1Bean;
    }

    public void setIdAutorizaPer1Bean(PersonalBean idAutorizaPer1Bean) {
        this.idAutorizaPer1Bean = idAutorizaPer1Bean;
    }

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public Date getFecAutoriza1() {
        return fecAutoriza1;
    }

    public void setFecAutoriza1(Date fecAutoriza1) {
        this.fecAutoriza1 = fecAutoriza1;
    }

    public PersonalBean getIdAutorizaPer2Bean() {
        if (idAutorizaPer2Bean == null) {
            idAutorizaPer2Bean = new PersonalBean();
        }
        return idAutorizaPer2Bean;
    }

    public void setIdAutorizaPer2Bean(PersonalBean idAutorizaPer2Bean) {
        this.idAutorizaPer2Bean = idAutorizaPer2Bean;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public Date getFecAutoriza2() {
        return fecAutoriza2;
    }

    public void setFecAutoriza2(Date fecAutoriza2) {
        this.fecAutoriza2 = fecAutoriza2;
    }

    public PersonalBean getIdAutorizaPer3Bean() {
        if (idAutorizaPer3Bean == null) {
            idAutorizaPer3Bean = new PersonalBean();
        }
        return idAutorizaPer3Bean;
    }

    public void setIdAutorizaPer3Bean(PersonalBean idAutorizaPer3Bean) {
        this.idAutorizaPer3Bean = idAutorizaPer3Bean;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
    }

    public Date getFecAutoriza3() {
        return fecAutoriza3;
    }

    public void setFecAutoriza3(Date fecAutoriza3) {
        this.fecAutoriza3 = fecAutoriza3;
    }

    public Integer getNivelAutoriza() {
        return nivelAutoriza;
    }

    public void setNivelAutoriza(Integer nivelAutoriza) {
        this.nivelAutoriza = nivelAutoriza;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgFactura() {
        return flgFactura;
    }

    public void setFlgFactura(Boolean flgFactura) {
        this.flgFactura = flgFactura;
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

    public String getObsVenc() {
        return obsVenc;
    }

    public void setObsVenc(String obsVenc) {
        this.obsVenc = obsVenc;
    }

    public Double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Double montoPago) {
        this.montoPago = montoPago;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }

    public Boolean getFlgIGV() {
        return flgIGV;
    }

    public void setFlgIGV(Boolean flgIGV) {
        this.flgIGV = flgIGV;
    }

    public Boolean getFlgRecibo() {
        return flgRecibo;
    }

    public void setFlgRecibo(Boolean flgRecibo) {
        this.flgRecibo = flgRecibo;
    }

    public Boolean getFlgBoleta() {
        return flgBoleta;
    }

    public void setFlgBoleta(Boolean flgBoleta) {
        this.flgBoleta = flgBoleta;
    }

    public Double getIgvResultado() {
        return igvResultado;
    }

    public void setIgvResultado(Double igvResultado) {
        this.igvResultado = igvResultado;
    }

    public OrdenCompraBean getOrdenCompraBean() {
        if (ordenCompraBean == null) {
            ordenCompraBean = new OrdenCompraBean();
        }
        return ordenCompraBean;
    }

    public void setOrdenCompraBean(OrdenCompraBean ordenCompraBean) {
        this.ordenCompraBean = ordenCompraBean;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public String getGlosaCompra() {
        return glosaCompra;
    }

    public void setGlosaCompra(String glosaCompra) {
        this.glosaCompra = glosaCompra;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public String getRucCompra() {
        return rucCompra;
    }

    public void setRucCompra(String rucCompra) {
        this.rucCompra = rucCompra;
    }

    public String getEntidadCompra() {
        return entidadCompra;
    }

    public void setEntidadCompra(String entidadCompra) {
        this.entidadCompra = entidadCompra;
    }

    public String getTablaVista() {
        if (registroCompraBean != null) {
            if (registroCompraBean.getIdRegistroCompra() != null) {
                tablaVista = MaristaConstantes.OBJ_SOL_REG_COMPRA;
            } else {
                tablaVista = MaristaConstantes.OBJ_SOL_ORD_COMPRA;
            }
        } else {
            tablaVista = MaristaConstantes.OBJ_SOL_ORD_COMPRA;
        }
        return tablaVista;
    }

    public void setTablaVista(String tablaVista) {
        this.tablaVista = tablaVista;
    }

    public Double getDetraccion() {
        return detraccion;
    }

    public void setDetraccion(Double detraccion) {
        this.detraccion = detraccion;
    }

    //NUEVOS
    public List<DetRegistroCompraCRBean> getListaDetRequerimientoCRBean() {
        if (listaDetRequerimientoCRBean == null) {
            listaDetRequerimientoCRBean = new ArrayList<>();
        }
        return listaDetRequerimientoCRBean;
    }

    public void setListaDetRequerimientoCRBean(List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean) {
        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
    }

    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public DetRegistroCompraCRBean getDetRequerimientoCRBean() {
        if (detRequerimientoCRBean == null) {
            detRequerimientoCRBean = new DetRegistroCompraCRBean();
        }
        return detRequerimientoCRBean;
    }

    public void setDetRequerimientoCRBean(DetRegistroCompraCRBean detRequerimientoCRBean) {
        this.detRequerimientoCRBean = detRequerimientoCRBean;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Boolean getFlgAdelanto() {
        return flgAdelanto;
    }

    public void setFlgAdelanto(Boolean flgAdelanto) {
        this.flgAdelanto = flgAdelanto;
    }

    public Integer getIdTipoModoPago() {
        return idTipoModoPago;
    }

    public void setIdTipoModoPago(Integer idTipoModoPago) {
        this.idTipoModoPago = idTipoModoPago;
    }

    public String getCodModoPago() {
        return codModoPago;
    }

    public void setCodModoPago(String codModoPago) {
        this.codModoPago = codModoPago;
    }

    public Integer getNroFact() {
        return nroFact;
    }

    public void setNroFact(Integer nroFact) {
        this.nroFact = nroFact;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public PlanContableBean getPlanContableCuentaHSoliBean() {
        if (planContableCuentaHSoliBean == null) {
            planContableCuentaHSoliBean = new PlanContableBean();
        }
        return planContableCuentaHSoliBean;
    }

    public void setPlanContableCuentaHSoliBean(PlanContableBean planContableCuentaHSoliBean) {
        this.planContableCuentaHSoliBean = planContableCuentaHSoliBean;
    }

    public PlanContableBean getPlanContableCuentaDSoliBean() {
        if (planContableCuentaDSoliBean == null) {
            planContableCuentaDSoliBean = new PlanContableBean();

        }
        return planContableCuentaDSoliBean;
    }

    public void setPlanContableCuentaDSoliBean(PlanContableBean planContableCuentaDSoliBean) {
        this.planContableCuentaDSoliBean = planContableCuentaDSoliBean;
    }

    public List<CuentaFacturaBean> getListaCuentaFacturaBean() {
        if (listaCuentaFacturaBean == null) {
            listaCuentaFacturaBean = new ArrayList<>();
        }
        return listaCuentaFacturaBean;
    }

    public void setListaCuentaFacturaBean(List<CuentaFacturaBean> listaCuentaFacturaBean) {
        this.listaCuentaFacturaBean = listaCuentaFacturaBean;
    }

    public Double getImporteSinIgv() {
        return importeSinIgv;
    }

    public void setImporteSinIgv(Double importeSinIgv) {
        this.importeSinIgv = importeSinIgv;
    }

    public Double getDsctoNotCred() {
        return dsctoNotCred;
    }

    public void setDsctoNotCred(Double dsctoNotCred) {
        this.dsctoNotCred = dsctoNotCred;
    }

    public String getNroNotaCredito() {
        return nroNotaCredito;
    }

    public void setNroNotaCredito(String nroNotaCredito) {
        this.nroNotaCredito = nroNotaCredito;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Boolean getFlgAutorizar() {
        return flgAutorizar;
    }

    public void setFlgAutorizar(Boolean flgAutorizar) {
        this.flgAutorizar = flgAutorizar;
    }
    
    
}
