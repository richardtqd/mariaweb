/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class DocEgresoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer nroDocEgreso;
    private Integer idDocEgreso;
    private FacturaCompraBean facturaCompraBean;//idFacturaCompra, ruc
    private RegistroCompraBean registroCompraBean;//idRegistroCompra, ruc
//    private ImpresoraCajaBean impresoraCajaBean;//uniNeg,idCaja,impresora
    private CajeroCajaBean cajeroCajaBean;//idCaja,usuario,uniNeg
    private DetraccionBean detraccionBean;
    private Double valorDetraccion;
    private String nroDoc;

    private EntidadBean entidadBean;//ruc
    private String glosa;//registroCompraBEan
    private CodigoBean tipoMonedaBean;

    private BigDecimal montoPagado;
    private CodigoBean tipoPagoBean;
    private CuentaBancoBean cuentaBancoBean;//rucBanco,numCuenta
    private String numCheque;
    //cuentaD
    //cuentaH
    private CentroResponsabilidadBean centroResponsabilidadBean;//cr
    private String nroDocRef;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private String idTipoDocEgreso;// R=registro compra; A=a rendir
    private SolicitudCajaCHBean solicitudCajaCHBean;

    //ayuda
    private Date fechaInicio;
    private Date FechaFin;
    private String numChequeVista;
    private String nombreVista;
    private String rucVista;
    private Integer idTipoDoc;//ayuda

    private CajaGenBean cajaGenBean; //idCajaGen "idCajaGen"
    private Boolean flgRendicion;
    private Double montoTotal;
    private Boolean flgAnulado;

    //ayuda reporte
    private Float montoTotalDocE;
    private Float montoCadaUno;
    private String docRef;
    private String textoMonto;
    private String nombreSolicitante;

    //ayuda 
    private Integer idCompraDocE;
    private TipoCambioBean tipoCambioBean;

    private String fecha;
    private String montoPagadoStr;

    private CodigoBean tipoDocBean;
    private String serie;
    private String descripTransfCta;
    private Double garantia;
    private Double dsctoNotCred;
    private String nroNotaCredito;

    public DocEgresoBean() {
        CodigoBean cod = new CodigoBean();
        cod.setIdCodigo(14901);
        cod.setTipoCodigoBean(null);
        this.tipoMonedaBean = cod;
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

    public Integer getIdDocEgreso() {
        return idDocEgreso;
    }

    public void setIdDocEgreso(Integer idDocEgreso) {
        this.idDocEgreso = idDocEgreso;
    }

    public CajeroCajaBean getCajeroCajaBean() {
        if (cajeroCajaBean == null) {
            cajeroCajaBean = new CajeroCajaBean();
        }
        return cajeroCajaBean;
    }

    public void setCajeroCajaBean(CajeroCajaBean cajeroCajaBean) {
        this.cajeroCajaBean = cajeroCajaBean;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

//    public PersonalBean getPersonalBean() {
//        if (personalBean==null) {
//            personalBean= new PersonalBean();
//        }
//        return personalBean;
//    }
//
//    public void setPersonalBean(PersonalBean personalBean) {
//        this.personalBean = personalBean;
//    }
    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
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

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public CodigoBean getTipoPagoBean() {
        if (tipoPagoBean == null) {
            tipoPagoBean = new CodigoBean();
        }
        return tipoPagoBean;
    }

    public void setTipoPagoBean(CodigoBean tipoPagoBean) {
        this.tipoPagoBean = tipoPagoBean;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }
//
//    public ImpresoraCajaBean getImpresoraCajaBean() {
//        if (impresoraCajaBean == null) {
//            impresoraCajaBean = new ImpresoraCajaBean();
//        }
//        return impresoraCajaBean;
//    }
//
//    public void setImpresoraCajaBean(ImpresoraCajaBean impresoraCajaBean) {
//        this.impresoraCajaBean = impresoraCajaBean;
//    }

    public RegistroCompraBean getRegistroCompraBean() {
        if (registroCompraBean == null) {
            registroCompraBean = new RegistroCompraBean();
        }
        return registroCompraBean;
    }

    public void setRegistroCompraBean(RegistroCompraBean registroCompraBean) {
        this.registroCompraBean = registroCompraBean;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return FechaFin;
    }

    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
    }

    public String getNumCheque() {
        return numCheque;
    }

    public void setNumCheque(String numCheque) {
        this.numCheque = numCheque;
    }

    public String getNumChequeVista() {
        if (numCheque == null) {
            numChequeVista = MaristaConstantes.SIN_NUM_CHEQUE;
        }
        if (numCheque != null) {
            numChequeVista = numCheque.toString();
        }
        return numChequeVista;
    }

    public void setNumChequeVista(String numChequeVista) {
        this.numChequeVista = numChequeVista;
    }

    public CajaGenBean getCajaGenBean() {
        if (cajaGenBean == null) {
            cajaGenBean = new CajaGenBean();
        }
        return cajaGenBean;
    }

    public void setCajaGenBean(CajaGenBean cajaGenBean) {
        this.cajaGenBean = cajaGenBean;
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

    public String getNroDocRef() {
        return nroDocRef;
    }

    public void setNroDocRef(String nroDocRef) {
        this.nroDocRef = nroDocRef;
    }

    public String getIdTipoDocEgreso() {
        return idTipoDocEgreso;
    }

    public void setIdTipoDocEgreso(String idTipoDocEgreso) {
        this.idTipoDocEgreso = idTipoDocEgreso;
    }

    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if (solicitudCajaCHBean == null) {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
    }

    public String getNombreVista() {
        return nombreVista;
    }

    public void setNombreVista(String nombreVista) {
        this.nombreVista = nombreVista;
    }

    public String getRucVista() {
        return rucVista;
    }

    public void setRucVista(String rucVista) {
        this.rucVista = rucVista;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public Boolean getFlgRendicion() {
        return flgRendicion;
    }

    public String getFlgRendicionVista() {
        if (flgRendicion != null && flgRendicion) {
            return MaristaConstantes.SI;
        } else {
            return MaristaConstantes.NO;
        }
    }

    public void setFlgRendicion(Boolean flgRendicion) {
        this.flgRendicion = flgRendicion;
    }

    public Integer getNroDocEgreso() {
        return nroDocEgreso;
    }

    public void setNroDocEgreso(Integer nroDocEgreso) {
        this.nroDocEgreso = nroDocEgreso;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Float getMontoTotalDocE() {
        return montoTotalDocE;
    }

    public void setMontoTotalDocE(Float montoTotalDocE) {
        this.montoTotalDocE = montoTotalDocE;
    }

    public Float getMontoCadaUno() {
        return montoCadaUno;
    }

    public void setMontoCadaUno(Float montoCadaUno) {
        this.montoCadaUno = montoCadaUno;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public TipoCambioBean getTipoCambioBean() {
        if (tipoCambioBean == null) {
            tipoCambioBean = new TipoCambioBean();
        }
        return tipoCambioBean;
    }

    public void setTipoCambioBean(TipoCambioBean tipoCambioBean) {
        this.tipoCambioBean = tipoCambioBean;
    }

    public Integer getIdCompraDocE() {
        return idCompraDocE;
    }

    public void setIdCompraDocE(Integer idCompraDocE) {
        this.idCompraDocE = idCompraDocE;
    }

    public String getTextoMonto() {
        return textoMonto;
    }

    public void setTextoMonto(String textoMonto) {
        this.textoMonto = textoMonto;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Boolean getFlgAnulado() {
        return flgAnulado;
    }

    public void setFlgAnulado(Boolean flgAnulado) {
        this.flgAnulado = flgAnulado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMontoPagadoStr() {
        return montoPagadoStr;
    }

    public void setMontoPagadoStr(String montoPagadoStr) {
        this.montoPagadoStr = montoPagadoStr;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean == null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public CodigoBean getTipoDocBean() {
        if (tipoDocBean == null) {
            tipoDocBean = new CodigoBean();
        }
        return tipoDocBean;
    }

    public void setTipoDocBean(CodigoBean tipoDocBean) {
        this.tipoDocBean = tipoDocBean;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDescripTransfCta() {
        return descripTransfCta;
    }

    public void setDescripTransfCta(String descripTransfCta) {
        this.descripTransfCta = descripTransfCta;
    }

    public Double getGarantia() {
        if (garantia == null) {
            garantia = 0.0;
        }
        return garantia;
    }

    public void setGarantia(Double garantia) {
        this.garantia = garantia;
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

}
