package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FichaBean implements Serializable {

    private Integer idFicha;
    private UnidadNegocioBean unidadNegocioBean;
    private CodigoBean tipoStatusFicha;
    private String serie;
    private Integer nroficha;
    private PaganteBean paganteBean;
    private CajaGenBean cajaGenBean;
    private CodigoBean tipoDoc;
    private CajaBean cajaBean;
    private CodigoBean tipoModoPago;
    private BigDecimal monto;
    private BigDecimal montoPagado;
    private Date fechaPago;
    private CodigoBean tipoMoneda;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date modiFecha;
    private String modiVer;
    private ConceptoBean conceptoBean;
    private ConceptoUniNegBean conceptoUniNegBean;
    private Boolean flgAnulado;
    private ImpresoraCajaBean impresoraCajaBean;
    private TipoCambioBean tipoCambioBean;
    private String referencia;
    private Double montoPos1;
    private Double montoPos2;
    private Double montoEfectivoSol;
    private Double montoEfectivoDol;
    private Integer nroDoc;

    //AYUDA
    private String fechaPagoVista;
    private Integer idTipoPagante;
    private String disabled;
    private Integer contador;
    private BigDecimal totalPro;
    private String nomFamilia;
    private Integer totalPagFa;
    private BigDecimal totalFa;
    private String nombreGrado;
    private String seccion;
    private String grado;
    private String nivel;
    private String data;

    private Date fechaIni;
    private Date fechaFin;

    private String nomFile;
    private Integer flgAdicional;
    private String codigo;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public CodigoBean getTipoStatusFicha() {
        if (tipoStatusFicha == null) {
            tipoStatusFicha = new CodigoBean();
        }
        return tipoStatusFicha;
    }

    public void setTipoStatusFicha(CodigoBean tipoStatusFicha) {
        this.tipoStatusFicha = tipoStatusFicha;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Integer getNroficha() {
        return nroficha;
    }

    public void setNroficha(Integer nroficha) {
        this.nroficha = nroficha;
    }

    public PaganteBean getPaganteBean() {
        if (paganteBean == null) {
            paganteBean = new PaganteBean();
        }
        return paganteBean;
    }

    public void setPaganteBean(PaganteBean paganteBean) {
        this.paganteBean = paganteBean;
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

    public CodigoBean getTipoDoc() {
        if (tipoDoc == null) {
            tipoDoc = new CodigoBean();
        }
        return tipoDoc;
    }

    public void setTipoDoc(CodigoBean tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public CajaBean getCajaBean() {
        if (cajaBean == null) {
            cajaBean = new CajaBean();
        }
        return cajaBean;
    }

    public void setCajaBean(CajaBean cajaBean) {
        this.cajaBean = cajaBean;
    }

    public CodigoBean getTipoModoPago() {
        if (tipoModoPago == null) {
            tipoModoPago = new CodigoBean();
        }
        return tipoModoPago;
    }

    public void setTipoModoPago(CodigoBean tipoModoPago) {
        this.tipoModoPago = tipoModoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public CodigoBean getTipoMoneda() {
        if (tipoMoneda == null) {
            tipoMoneda = new CodigoBean();
        }
        return tipoMoneda;
    }

    public void setTipoMoneda(CodigoBean tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
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

    public Date getModiFecha() {
        return modiFecha;
    }

    public void setModiFecha(Date modiFecha) {
        this.modiFecha = modiFecha;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Integer getIdFicha() {
        return idFicha;
    }

    public void setIdFicha(Integer idFicha) {
        this.idFicha = idFicha;
    }

    public String getFechaPagoVista() {
        return fechaPagoVista;
    }

    public void setFechaPagoVista(String fechaPagoVista) {
        this.fechaPagoVista = fechaPagoVista;
    }

    public Integer getIdTipoPagante() {
        return idTipoPagante;
    }

    public void setIdTipoPagante(Integer idTipoPagante) {
        this.idTipoPagante = idTipoPagante;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(BigDecimal montoPagado) {
        this.montoPagado = montoPagado;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
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

    public Boolean getFlgAnulado() {
        return flgAnulado;
    }

    public void setFlgAnulado(Boolean flgAnulado) {
        this.flgAnulado = flgAnulado;
    }

    public ImpresoraCajaBean getImpresoraCajaBean() {
        if (impresoraCajaBean == null) {
            impresoraCajaBean = new ImpresoraCajaBean();
        }
        return impresoraCajaBean;
    }

    public void setImpresoraCajaBean(ImpresoraCajaBean impresoraCajaBean) {
        this.impresoraCajaBean = impresoraCajaBean;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
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

    public Double getMontoEfectivoSol() {
        return montoEfectivoSol;
    }

    public void setMontoEfectivoSol(Double montoEfectivoSol) {
        this.montoEfectivoSol = montoEfectivoSol;
    }

    public Double getMontoEfectivoDol() {
        return montoEfectivoDol;
    }

    public void setMontoEfectivoDol(Double montoEfectivoDol) {
        this.montoEfectivoDol = montoEfectivoDol;
    }

    public Integer getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(Integer nroDoc) {
        this.nroDoc = nroDoc;
    }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public BigDecimal getTotalPro() {
        return totalPro;
    }

    public void setTotalPro(BigDecimal totalPro) {
        this.totalPro = totalPro;
    }

    public String getNomFile() {
        return nomFile;
    }

    public void setNomFile(String nomFile) {
        this.nomFile = nomFile;
    }

    public String getNomFamilia() {
        return nomFamilia;
    }

    public void setNomFamilia(String nomFamilia) {
        this.nomFamilia = nomFamilia;
    }

    public Integer getTotalPagFa() {
        return totalPagFa;
    }

    public void setTotalPagFa(Integer totalPagFa) {
        this.totalPagFa = totalPagFa;
    }

    public BigDecimal getTotalFa() {
        return totalFa;
    }

    public void setTotalFa(BigDecimal totalFa) {
        this.totalFa = totalFa;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getFlgAdicional() {
        return flgAdicional;
    }

    public void setFlgAdicional(Integer flgAdicional) {
        this.flgAdicional = flgAdicional;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
