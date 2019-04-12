/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pe.marista.sigma.MaristaConstantes;

public class RegistroCompraBean implements Serializable{

    private Integer idRegistroCompra;
    private UnidadNegocioBean unidadNegocioBean;
    private Integer anio;
    private OrdenCompraBean ordenCompraBean;
    private Date fechaEmision;
    private CodigoBean tipoDocBean;
    private Integer idtipodoc;
    private String serieDoc;
    private String nroDoc;
    private CodigoBean tipoMonedaBean;
    private Double importe;
//    private Float descuento;
    private Double igv;
    private Float impuesto;
    private Double total;
    private Float retencion;
    private EntidadBean entidadBean;
    private Boolean flgDonacion;
    private String glosa;
    private Date fechaVenc;
    private String obsVenc;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoNumeroFacturaBean;
    private CodigoBean tipoPrioridadBean;
    private CodigoBean tipoStatusRegCBean;
    private CodigoBean codigoBean;
    private PersonalBean idAutoriza1;
    private PersonalBean idAutoriza2;
    private PersonalBean idAutoriza3;
    private Boolean flgAutoriza1;
    private Boolean flgAutoriza2;
    private Boolean flgAutoriza3;
    private Date fecAutoriza1;
    private Date fecAutoriza2;
    private Date fecAutoriza3;
    private Date fechaProg;
    private Date fechaPago;
    private Double montoPago;
    private Float saldo;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean flgRecibido;
    private String objeto;
    private TipoSolicitudBean tipoSolicitudBean;
    private Integer idTipoSolicitud;
    private String idPaso;
    private FacturaCompraBean facturaCompraBean;
    private Double montoGeneralRegistro;
    private String nroRegistro;
    //cr
    

    private List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean;
    private Integer codDistribucion;
    private DetRegistroCompraCRBean detRequerimientoCRBean;

//    private List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean;
//    private Integer codDistribucion;
//    private DetRegistroCompraCRBean detRequerimientoCRBean;

    
    public RegistroCompraBean() {
        this.fechaEmision = new Date();
        this.igv = MaristaConstantes.VALOR_IGV;
    }

    public Integer getIdRegistroCompra() {
        return idRegistroCompra;
    }

    public void setIdRegistroCompra(Integer idRegistroCompra) {
        this.idRegistroCompra = idRegistroCompra;
    }

    public UnidadNegocioBean getRegistroCompraBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setRegistroCompraBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

//    public Float getDescuento() {
//        return descuento;
//    }
//
//    public void setDescuento(Float descuento) {
//        this.descuento = descuento;
//    }
    public Float getRetencion() {
        return retencion;
    }

    public void setRetencion(Float retencion) {
        this.retencion = retencion;
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

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getIdtipodoc() {
        return idtipodoc;
    }

    public void setIdtipodoc(Integer idtipodoc) {
        this.idtipodoc = idtipodoc;
    }

    public String getSerieDoc() {
        return serieDoc;
    }

    public void setSerieDoc(String serieDoc) {
        this.serieDoc = serieDoc;
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

    public Float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Float impuesto) {
        this.impuesto = impuesto;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public String getObsVenc() {
        return obsVenc;
    }

    public void setObsVenc(String obsVenc) {
        this.obsVenc = obsVenc;
    }

    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean == null) {
            tipoCategoriaBean = new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
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

    public Boolean getFlgAutoriza1() {
        return flgAutoriza1;
    }

    public void setFlgAutoriza1(Boolean flgAutoriza1) {
        this.flgAutoriza1 = flgAutoriza1;
    }

    public Boolean getFlgAutoriza2() {
        return flgAutoriza2;
    }

    public void setFlgAutoriza2(Boolean flgAutoriza2) {
        this.flgAutoriza2 = flgAutoriza2;
    }

    public Boolean getFlgAutoriza3() {
        return flgAutoriza3;
    }

    public void setFlgAutoriza3(Boolean flgAutoriza3) {
        this.flgAutoriza3 = flgAutoriza3;
    }

    public Date getFecAutoriza1() {
        return fecAutoriza1;
    }

    public void setFecAutoriza1(Date fecAutoriza1) {
        this.fecAutoriza1 = fecAutoriza1;
    }

    public Date getFecAutoriza2() {
        return fecAutoriza2;
    }

    public void setFecAutoriza2(Date fecAutoriza2) {
        this.fecAutoriza2 = fecAutoriza2;
    }

    public Date getFecAutoriza3() {
        return fecAutoriza3;
    }

    public void setFecAutoriza3(Date fecAutoriza3) {
        this.fecAutoriza3 = fecAutoriza3;
    }

    public Date getFechaProg() {
        return fechaProg;
    }

    public void setFechaProg(Date fechaProg) {
        this.fechaProg = fechaProg;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(Double montoPago) {
        this.montoPago = montoPago;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
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

    public CodigoBean getTipoDocBean() {
        if (tipoDocBean == null) {
            tipoDocBean = new CodigoBean();
        }
        return tipoDocBean;
    }

    public void setTipoDocBean(CodigoBean tipoDocBean) {
        this.tipoDocBean = tipoDocBean;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getIgv() {
        return igv;
    }

    public void setIgv(Double igv) {
        this.igv = igv;
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

    public Boolean getFlgDonacion() {
        return flgDonacion;
    }

    public void setFlgDonacion(Boolean flgDonacion) {
        this.flgDonacion = flgDonacion;
    }

    public Boolean getFlgRecibido() {
        return flgRecibido;
    }

    public void setFlgRecibido(Boolean flgRecibido) {
        this.flgRecibido = flgRecibido;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public TipoSolicitudBean getTipoSolicitudBean() {
        if(tipoSolicitudBean ==null)
        {
            tipoSolicitudBean = new TipoSolicitudBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public CodigoBean getTipoStatusRegCBean() {
        if(tipoStatusRegCBean ==null)
        {
            tipoStatusRegCBean = new CodigoBean();
        }
        return tipoStatusRegCBean;
    }

    public void setTipoStatusRegCBean(CodigoBean tipoStatusRegCBean) {
        this.tipoStatusRegCBean = tipoStatusRegCBean;
    }

    public String getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(String idPaso) {
        this.idPaso = idPaso;
    }

    public PersonalBean getIdAutoriza1() {
        if(idAutoriza1 ==null)
        {
            idAutoriza1 = new PersonalBean();
        }
        return idAutoriza1;
    }

    public void setIdAutoriza1(PersonalBean idAutoriza1) {
        this.idAutoriza1 = idAutoriza1;
    }

    public PersonalBean getIdAutoriza2() {
        if(idAutoriza2 ==null)
        {
            idAutoriza2 = new PersonalBean();
        }
        return idAutoriza2;
    }

    public void setIdAutoriza2(PersonalBean idAutoriza2) {
        this.idAutoriza2 = idAutoriza2;
    }

    public PersonalBean getIdAutoriza3() {
        if(idAutoriza3 ==null)
        {
            idAutoriza3 = new PersonalBean();
        }
        return idAutoriza3;
    }

    public void setIdAutoriza3(PersonalBean idAutoriza3) {
        this.idAutoriza3 = idAutoriza3;
    }

    public CodigoBean getTipoNumeroFacturaBean() {
        if (tipoNumeroFacturaBean== null) {
            tipoNumeroFacturaBean = new CodigoBean();
        }
        return tipoNumeroFacturaBean;
    }

    public void setTipoNumeroFacturaBean(CodigoBean tipoNumeroFacturaBean) {
        this.tipoNumeroFacturaBean = tipoNumeroFacturaBean;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean==null) {
            facturaCompraBean= new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public Double getMontoGeneralRegistro() {
        return montoGeneralRegistro;
    }

    public void setMontoGeneralRegistro(Double montoGeneralRegistro) {
        this.montoGeneralRegistro = montoGeneralRegistro;
 
    } 
    
    public Integer getCodDistribucion() {
        return codDistribucion;
    }

    public void setCodDistribucion(Integer codDistribucion) {
        this.codDistribucion = codDistribucion;
    }

    public List<DetRegistroCompraCRBean> getListaDetRequerimientoCRBean() {
        if (listaDetRequerimientoCRBean==null) {
            listaDetRequerimientoCRBean = new ArrayList<>();
        }
        return listaDetRequerimientoCRBean;
    }

    public void setListaDetRequerimientoCRBean(List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean) {
        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
    }

    public DetRegistroCompraCRBean getDetRequerimientoCRBean() {
        if (detRequerimientoCRBean==null) {
            detRequerimientoCRBean= new DetRegistroCompraCRBean();
        }
        return detRequerimientoCRBean;
    }

    public String getNroRegistro() {
        return nroRegistro;
    }

    public void setNroRegistro(String nroRegistro) {
        this.nroRegistro = nroRegistro;
    }


    } 
    
//    public Integer getCodDistribucion() {
//        return codDistribucion;
//    }
//
//    public void setCodDistribucion(Integer codDistribucion) {
//        this.codDistribucion = codDistribucion;
//    }

//    public List<DetRegistroCompraCRBean> getListaDetRequerimientoCRBean() {
//        if (listaDetRequerimientoCRBean==null) {
//            listaDetRequerimientoCRBean = new ArrayList<>();
//        }
//        return listaDetRequerimientoCRBean;
//    }
//
//    public void setListaDetRequerimientoCRBean(List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean) {
//        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
//    }

//    public DetRegistroCompraCRBean getDetRequerimientoCRBean() {
//        if (detRequerimientoCRBean==null) {
//            detRequerimientoCRBean= new DetRegistroCompraCRBean();
//        }
//        return detRequerimientoCRBean;
//    }
//
//    public void setDetRequerimientoCRBean(DetRegistroCompraCRBean detRequerimientoCRBean) {
//        this.detRequerimientoCRBean = detRequerimientoCRBean;
//    }
 
