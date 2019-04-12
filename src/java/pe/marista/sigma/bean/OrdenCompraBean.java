package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

public class OrdenCompraBean implements Serializable {

    private Integer idOrdenCompra;
    private UnidadNegocioBean unidadNegocioBean;
    private EntidadBean entidadBean;
  //  private EntidadSedeBean entidadSedeBean;
    private CodigoBean tipoFormaPagoBean;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoPrioridadBean;
    private Integer anio;
    private Date fechaOrden;
    private Double montoRef;
    private String creaPor;
    private Date creaFecha;
    private String obs;
    private Date fechaInicio;
    private Date fechaFin;
    private CodigoBean codigoBean;
    private String modiPor;
    private String numeroFactura;
    private Float importeFinal;
    private CodigoBean tipoStatusRegCBean;
    private Double importePropuesto=0.00;
    //ayuda
    private Double montoCadaUnoMate;
    private Double montoCadaUnoSer;
    private Double montoGeneralOrden;
    private CotizacionBean cotizacionBean;
    private String atencion;
    
    private Double adelantoAyuda;
    private Double igv= 18.00;
    
    private String nombreCheque;
    
    //Ayuda ID +10 - 0
    private String idOrden;
    //Creacion de Campos para el Adelanto
    private Boolean flgAdelanto;
    private Double importeAdelanto = 0.0;
    private FacturaCompraBean facturaCompraBean;
    private Date fechaEntrega;
    private Date fechaTermina;
    private String nroCompra;
    private String nroCotiPro;
    private String lugarEntrega;
    private CodigoBean tipoPagoBean; 
    
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public Double getMontoRef() {
        return montoRef;
    }

    public void setMontoRef(Double montoRef) {
        this.montoRef = montoRef;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(Integer idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
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

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

  //  public EntidadSedeBean getEntidadSedeBean() {
    //    if (entidadSedeBean == null) {
      //      entidadSedeBean = new EntidadSedeBean();
       // }
    //    return entidadSedeBean;
    //}

    //public void setEntidadSedeBean(EntidadSedeBean entidadSedeBean) {
     //   this.entidadSedeBean = entidadSedeBean;
    //}

    public CodigoBean getTipoFormaPagoBean() {
        if (tipoFormaPagoBean == null) {
            tipoFormaPagoBean = new CodigoBean();
        }
        return tipoFormaPagoBean;
    }

    public void setTipoFormaPagoBean(CodigoBean tipoFormaPagoBean) {
        this.tipoFormaPagoBean = tipoFormaPagoBean;
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

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Float getImporteFinal() {
        return importeFinal;
    }

    public void setImporteFinal(Float importeFinal) {
        this.importeFinal = importeFinal;
    }

    public CodigoBean getCodigoBean() {
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean==null) {
            tipoCategoriaBean= new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
    }

    public Double getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Double montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public Double getMontoCadaUnoSer() {
        return montoCadaUnoSer;
    }

    public void setMontoCadaUnoSer(Double montoCadaUnoSer) {
        this.montoCadaUnoSer = montoCadaUnoSer;
    }

    public Double getMontoGeneralOrden() {
        return montoGeneralOrden;
    }

    public void setMontoGeneralOrden(Double montoGeneralOrden) {
        this.montoGeneralOrden = montoGeneralOrden;
    }

    public CodigoBean getTipoStatusRegCBean() {
        if (tipoStatusRegCBean==null) {
            tipoStatusRegCBean = new CodigoBean();
        }
        return tipoStatusRegCBean;
    }

    public void setTipoStatusRegCBean(CodigoBean tipoStatusRegCBean) {
        this.tipoStatusRegCBean = tipoStatusRegCBean;
    }

    public CotizacionBean getCotizacionBean() {
        if (cotizacionBean==null) {
            cotizacionBean=new CotizacionBean();
        }
                
        return cotizacionBean;
    }

    public void setCotizacionBean(CotizacionBean cotizacionBean) {
        this.cotizacionBean = cotizacionBean;
    } 

    public Boolean getFlgAdelanto() {
        return flgAdelanto;
    }

    public void setFlgAdelanto(Boolean flgAdelanto) {
        this.flgAdelanto = flgAdelanto;
    }

    public Double getImporteAdelanto() {
        return importeAdelanto;
    }

    public void setImporteAdelanto(Double importeAdelanto) {
        this.importeAdelanto = importeAdelanto;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean ==null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public String getNombreCheque() {
        return nombreCheque;
    }

    public void setNombreCheque(String nombreCheque) {
        this.nombreCheque = nombreCheque;
    }

    public Double getImportePropuesto() {
        return importePropuesto;
    }

    public void setImportePropuesto(Double importePropuesto) {
        this.importePropuesto = importePropuesto;
    }

    public Double getAdelantoAyuda() {
        return adelantoAyuda;
    }

    public void setAdelantoAyuda(Double adelantoAyuda) {
        this.adelantoAyuda = adelantoAyuda;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getNroCompra() {
        return nroCompra;
    }

    public void setNroCompra(String nroCompra) {
        this.nroCompra = nroCompra;
    }

    public String getNroCotiPro() {
        return nroCotiPro;
    }

    public void setNroCotiPro(String nroCotiPro) {
        this.nroCotiPro = nroCotiPro;
    }

    public Date getFechaTermina() {
        return fechaTermina;
    }

    public void setFechaTermina(Date fechaTermina) {
        this.fechaTermina = fechaTermina;
    }

    public String getAtencion() {
        return atencion;
    }

    public void setAtencion(String atencion) {
        this.atencion = atencion;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public CodigoBean getTipoPagoBean() {
        if (tipoPagoBean== null) {
            tipoPagoBean= new CodigoBean();
        }
        return tipoPagoBean;
    }

    public void setTipoPagoBean(CodigoBean tipoPagoBean) {
        this.tipoPagoBean = tipoPagoBean;
    }  
}
