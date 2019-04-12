package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.component.datatable.DataTable;
import pe.marista.sigma.MaristaConstantes;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.reporte.DetOrdenCompraGeneral;
import pe.marista.sigma.bean.reporte.DetOrdenCompraRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CotizacionService;
import pe.marista.sigma.service.DetCotizacionService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.OrdenCompraDetalleService;
import pe.marista.sigma.service.OrdenCompraService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.reporte.ContratoAdquisicionRepBean;
import pe.marista.sigma.bean.reporte.DetFiltroProveItemRepBean;
import pe.marista.sigma.bean.reporte.DetContratoAdquisicionRepBean;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.DetraccionService;
import pe.marista.sigma.util.GLTCalculadoraCR;

public class OrdenCompraMB extends BaseMB implements Serializable {

    private List<OrdenCompraBean> listaOrdenCompraBean;
    private OrdenCompraBean ordenCompraBean;
    private OrdenCompraBean ordenCompraFiltroBean;
    private OrdenCompraDetalleBean ordenCompraDetalleFiltroBean;
    private Calendar fechaOrden;
    private String fechaOrdenView;
    //private List<EntidadSedeBean> listaEntidadSedeBean;
    private List<EntidadBean> listaEntidadBean;
    //  private EntidadSedeBean entidadSedeBean;
    private List<CodigoBean> listaTipoFormaPagoBean;
    private List<CodigoBean> listaTipoPagoBean;
    private List<CodigoBean> listaTipoPrioridadBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private List<OrdenCompraDetalleBean> listaOrdenCompraDetalleBean;
    private EntidadBean entidadBean;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Double sumaImporte = 0.00D;
    private Double montoCadaUnoMate = 0.00;
    private Double montoGeneralOrden = 0.00;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoPrioridadBean;
    private UsuarioBean usuarioSessionBean;
    private CodigoBean codigoBean;
    private List<SolicitudLogisticoBean> listaSolicitudServiciosBean;
    private List<SolicitudLogisticoBean> listaSolicitudActivosMaBean;
    private OrdenCompraDetalleBean ordenCompraDetalleBean;
    private Boolean mostrarPdf = false;
    private List<ViewEntidadBean> listaViewEntidadProveedorBean;
    private Calendar fechaActual;

    //Cotizaciones
    private List<CotizacionBean> listaCotizacionActivosMaBean;
    private List<CotizacionBean> lsitaCotizacionesAceptadas;
    private Integer idTipoFormaPago;
    //Ayuda 
    private List<OrdenCompraDetalleBean> listaOrdenCompraActDetalleBean;

    //ayuda Cotizaciones o sin Cotizaciones
    private Boolean flgCotizaciones = false;
    private Boolean flgSolicitudes = false;
    private CotizacionBean cotizacionFiltroBean;
    private List<CotizacionBean> listaCotizacionBean = null;
    private CotizacionBean cotizacionBean;
    private List<DetCotizacionBean> listaDetCotizacionBean;
    private Boolean flgTipoOrdenCompra = false; //0=Cotizaciones 1=Solicitudes
    private DetCotizacionBean detCotizacionBean;
    private Integer idRequerimiento;

    //Adelanto ayuda
    private Boolean flgAdelanto;
    private FacturaCompraBean facturaSessionBean;
    private Integer idTipoDoc;
    private List<DetraccionBean> listaDetraccionBean;
    private List<FacturaCompraBean> listaSesionFacturaCompraBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoDocBean;
    private CodigoBean tipoDoc;
    private String montoTotal;
    private Double montoDetraccion = 0.00;
    private Double igvResultado = 0.00;
    private Double subTotal = 0.00;

    private FacturaCompraBean facturaCompraBean;
    private List<CodigoBean> listaTipoDistribucionCRBean;
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
    private List<CodigoBean> listaTipoStatusRegCBean;
    private Integer idTipoStatusRegC;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private Integer idTipoDistribucion = 0;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;
    private List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean;
    private DetRegistroCompraCRBean detRegistroCompraCRBean;
    private Boolean flgDetalleActivo = false;
    private Boolean flgGrabarAc =false;

    private Double sumaDeImportes = 0.0;

    //getter and setter
    public OrdenCompraBean getOrdenCompraFiltroBean() {
        if (ordenCompraFiltroBean == null) {
            ordenCompraFiltroBean = new OrdenCompraBean();
        }
        return ordenCompraFiltroBean;
    }

    public void setOrdenCompraFiltroBean(OrdenCompraBean ordenCompraFiltroBean) {
        this.ordenCompraFiltroBean = ordenCompraFiltroBean;
    }

    public SimpleDateFormat getFormato() {
        return formato;
    }

    public void setFormato(SimpleDateFormat formato) {
        this.formato = formato;
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

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
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

    public List<OrdenCompraDetalleBean> getListaOrdenCompraDetalleBean() {
        if (listaOrdenCompraDetalleBean == null) {
            listaOrdenCompraDetalleBean = new ArrayList<>();
        }
        return listaOrdenCompraDetalleBean;
    }

    public void setListaOrdenCompraDetalleBean(List<OrdenCompraDetalleBean> listaOrdenCompraDetalleBean) {
        this.listaOrdenCompraDetalleBean = listaOrdenCompraDetalleBean;
    }

    public List<SolicitudLogDetalleBean> getListaSolicitudLogDetalleBean() {
        if (listaSolicitudLogDetalleBean == null) {
            listaSolicitudLogDetalleBean = new ArrayList<>();
        }
        return listaSolicitudLogDetalleBean;
    }

    public void setListaSolicitudLogDetalleBean(List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean) {
        this.listaSolicitudLogDetalleBean = listaSolicitudLogDetalleBean;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean == null) {
            solicitudLogisticoBean = new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    }

    public SolicitudLogDetalleBean getSolicitudLogDetalleBean() {
        if (solicitudLogDetalleBean == null) {
            solicitudLogDetalleBean = new SolicitudLogDetalleBean();
        }
        return solicitudLogDetalleBean;
    }

    public void setSolicitudLogDetalleBean(SolicitudLogDetalleBean solicitudLogDetalleBean) {
        this.solicitudLogDetalleBean = solicitudLogDetalleBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoBean() {
        return listaSolicitudLogisticoBean;
    }

    public void setListaSolicitudLogisticoBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoBean) {
        this.listaSolicitudLogisticoBean = listaSolicitudLogisticoBean;
    }

    public List<CodigoBean> getListaTipoFormaPagoBean() {
        return listaTipoFormaPagoBean;
    }

    public void setListaTipoFormaPagoBean(List<CodigoBean> listaTipoFormaPagoBean) {
        this.listaTipoFormaPagoBean = listaTipoFormaPagoBean;
    }

    public List<CodigoBean> getListaTipoPrioridadBean() {
        return listaTipoPrioridadBean;
    }

    public void setListaTipoPrioridadBean(List<CodigoBean> listaTipoPrioridadBean) {
        this.listaTipoPrioridadBean = listaTipoPrioridadBean;
    }

    //  public EntidadSedeBean getEntidadSedeBean() {
    //    if (entidadSedeBean == null) {
    //      entidadSedeBean = new EntidadSedeBean();
    // }
    //return entidadSedeBean;
    //}
    //public void setEntidadSedeBean(EntidadSedeBean entidadSedeBean) {
    //     this.entidadSedeBean = entidadSedeBean;
    //}
    //public List<EntidadSedeBean> getListaEntidadSedeBean() {
    //  return listaEntidadSedeBean;
    //}
    // public void setListaEntidadSedeBean(List<EntidadSedeBean> listaEntidadSedeBean) {
    //   this.listaEntidadSedeBean = listaEntidadSedeBean;
    //}
    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }

    public String getFechaOrdenView() {
        return fechaOrdenView;
    }

    public void setFechaOrdenView(String fechaOrdenView) {
        this.fechaOrdenView = fechaOrdenView;
    }

    public Calendar getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Calendar fechaOrden) {
        this.fechaOrden = fechaOrden;
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

    public List<OrdenCompraBean> getListaOrdenCompraBean() {
        if (listaOrdenCompraBean == null) {
            listaOrdenCompraBean = new ArrayList<>();
        }
        return listaOrdenCompraBean;
    }

    public void setListaOrdenCompraBean(List<OrdenCompraBean> listaOrdenCompraBean) {
        this.listaOrdenCompraBean = listaOrdenCompraBean;
    }

    @PostConstruct
    public void cargaDatos() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            listaCotizacionBean = new ArrayList<>();

            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerTodosCompra(solicitudLogisticoBean);
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
//            listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 
            EntidadService entidadService = BeanFactory.getEntidadService();
//          listaEntidadSedeBean = entidadService.obtenerProveedorPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 

//            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuasolicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBrioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getEntidadBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);
            listaViewEntidadProveedorBean = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_PROVEEDOR, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoFormaPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoModoPago"));
            listaTipoPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPago"));
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoBean);
//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoBean);

//            listaSolicitudActivosMaBean = solicitudLogisticoService.obtenerTodosM(solicitudLogisticoBean);
            listaCotizacionActivosMaBean = cotizacionService.obtenerTodosMCotizacion(cotizacionBean);
            lsitaCotizacionesAceptadas = cotizacionService.obtenerTodosMCotizacionAceptadas(cotizacionBean);
            listaTipoStatusRegCBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoStatusRegC"));
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
            for (CodigoBean bean : listaTipoStatusRegCBean) {
                if (bean.getCodigo().equals("Pendiente")) {
                    getOrdenCompraBean().getTipoStatusRegCBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoStatusRegC = bean.getIdCodigo();
                }
            }
//            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosSolDes(solicitudLogisticoBean);
//            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosServicio(solicitudLogisticoBean);

            String idOrden = null;
            idOrden = ordenCompraService.obtenerUltimoOrden(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idOrden.equals(null)) {
                getOrdenCompraBean().setNroCompra(idOrden);
            }
            fechaActual = new GregorianCalendar();
            getOrdenCompraFiltroBean().setFechaInicio(fechaActual.getTime());
            getOrdenCompraFiltroBean().setFechaFin(fechaActual.getTime());
            getOrdenCompraFiltroBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoFormaPago = bean.getIdCodigo();
                }
            }
            this.cargarDatosSession();
            getOrdenCompraFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosCr() {
        try {

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCentroResponsabilidadBean();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
            listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCROrden(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
            }
            if (!listaDetRequerimientoCRBean.isEmpty()) {
                idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
            }

            getListaCentroResponsabilidadBeanB();
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInOrden(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cod != null) {
                facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListaActivos() {
        try {

            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudActivosMaBean = solicitudLogisticoService.obtenerTodosM(solicitudLogisticoBean);
            listaSolicitudLogDetalleBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListasFactura() {
        try {
            getFacturaSessionBean();
            getFacturaSessionBean().setFlgIGV(true);

            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            getListaDetraccionBean();
            listaDetraccionBean = detraccionService.obtenerTodosActivos();
//
//            getListaTipoPrioridadBean();
//            listTipoPrioridadFacturaBean = listaTipoPrioridadBean;
//            getListaSesionFacturaCompraBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDoc"));
            for (CodigoBean bean : listaTipoDocBean) {
                if (bean.getCodigo().equals("Factura")) {
                    tipoDoc.setIdCodigo(bean.getIdCodigo());
                    this.idTipoDoc = bean.getIdCodigo();
                }
                elegirDoc();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarIGV() {
        try {

            if (tipoDoc.getIdCodigo().equals(MaristaConstantes.COD_DOC_REC)) {
                if (facturaSessionBean.getFlgIGV() == true) {
                    facturaSessionBean.setIgv(MaristaConstantes.VALOR_IGV_RECIBO_NEGATIVO_8);
                } else {
                    facturaSessionBean.setIgv(MaristaConstantes.VALOR_IGV_RECIBO_POSITIVO_0);
                }
            } else if (tipoDoc.getIdCodigo().equals(MaristaConstantes.COD_DOC_FACT)) {
                if (facturaSessionBean.getFlgIGV() == true) {
                    facturaSessionBean.setIgv(MaristaConstantes.VALOR_IGV);
                } else {
                    facturaSessionBean.setIgv(MaristaConstantes.VALOR_IGV_0);
                }
            } else {
                facturaSessionBean.setIgv(MaristaConstantes.VALOR_IGV_0);
            }
            obtenerIgv();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetracionFactura() {
        try {
            Double total = 0.00;
            String tot = "";
            this.montoTotal = "";
            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            DetraccionBean detraccion = new DetraccionBean();
            obtenerIgv();
            detraccion = detraccionService.obtenerPorId(facturaSessionBean.getDetraccionBean());//no tiene valor
            if (detraccion != null) {
                double monDouble = 0;
                double mon = 0;
                monDouble = facturaSessionBean.getImpuesto();
//                montoDetraccion = docEgresoBean.getSolicitudCajaCHBean().getMontoAprobado() * (detraccion.getValor() / 100);

                if (detraccion.getDescripcion().equals("Retención RH")) {
                    montoDetraccion = monDouble * (detraccion.getValor() / 100);
                    montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                } else {
                    montoDetraccion = monDouble * (detraccion.getValor() / 100);
                    montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                    montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                }

                mon = (double) Math.round((monDouble - montoDetraccion) * 100) / 100;
                facturaSessionBean.setMontoPago((mon));
                facturaSessionBean.getDetraccionBean().setValor(detraccion.getValor());
                distribuir();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void elegirDoc() {

        try {
            if (tipoDoc.getIdCodigo() != null) {
                facturaSessionBean.setFlgRecibo(false);
                facturaSessionBean.setFlgFactura(false);
                facturaSessionBean.setFlgBoleta(false);

                if (tipoDoc.getIdCodigo().equals(MaristaConstantes.COD_DOC_REC)) {
                    facturaSessionBean.setFlgRecibo(true);

                } else if (tipoDoc.getIdCodigo().equals(MaristaConstantes.COD_DOC_FACT)) {
                    facturaSessionBean.setFlgFactura(true);

                } else {
                    facturaSessionBean.setFlgBoleta(true);

                }
                cambiarIGV();
            } else {
                facturaSessionBean.setFlgRecibo(false);
                facturaSessionBean.setFlgFactura(false);
                facturaSessionBean.setFlgBoleta(false);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerIgv() {
        try {
            if (facturaSessionBean != null) {
                if (facturaSessionBean.getImporte() != null && facturaSessionBean.getIgv() >= 0) {
                    igvResultado = facturaSessionBean.getImporte() * (facturaSessionBean.getIgv() / 100);
                    facturaSessionBean.setIgvResultado(igvResultado);
                    subTotal = facturaSessionBean.getImporte() + (facturaSessionBean.getImporte() * (facturaSessionBean.getIgv() / 100));
                    facturaSessionBean.setImpuesto(subTotal);
                    facturaSessionBean.setMontoPago(subTotal);
                }
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void guardarFacturaVista() {
        try {
            if (facturaSessionBean.getSerieDoc() != null && facturaSessionBean.getNroDoc() != null && tipoDoc.getIdCodigo() != null
                    && facturaSessionBean.getTipoPrioridadBean().getIdCodigo() != null && facturaSessionBean.getIgv() != null
                    && facturaSessionBean.getImporte() != null && facturaSessionBean.getTipoMonedaBean().getIdCodigo() != null) {
                facturaSessionBean.setCreaPor(usuarioSessionBean.getUsuario());
                facturaSessionBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                facturaSessionBean.setSerieDoc(facturaSessionBean.getSerieDoc().trim());
                facturaSessionBean.setNroDoc(facturaSessionBean.getNroDoc().trim());
                listaSesionFacturaCompraBean.add(facturaSessionBean);
                facturaSessionBean = new FacturaCompraBean();
            } else {
                new MensajePrime().addInformativeMessagePer("msjLlenarDatosFactura");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //LÃ³gica de negocio
    public void cargarDatosSession() {
        try {
            //Paso los valores de sesion al bean ordenCompraBean
            this.fechaOrden = new GregorianCalendar();
            getOrdenCompraBean().setAnio(fechaOrden.get(Calendar.YEAR));
            getOrdenCompraBean().setFechaOrden(fechaOrden.getTime());
            getOrdenCompraBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiar() {
        try {
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            ordenCompraBean = new OrdenCompraBean();
            listaOrdenCompraDetalleBean = new ArrayList<>();
            fechaOrdenView = "";
            sumaImporte = 0.00;
            listaOrdenCompraDetalleBean = new ArrayList<>();
            this.cargarDatosSession();
            getOrdenCompraFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoFormaPago = bean.getIdCodigo();
                }
            }
            String idOrden = null;
            idOrden = ordenCompraService.obtenerUltimoOrden(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idOrden.equals(null)) {
                getOrdenCompraBean().setNroCompra(idOrden);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {

            montoCadaUnoMate = 0.00;
            sumaImporte = 0.00;
            EntidadService entidadService = BeanFactory.getEntidadService();
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();

            ordenCompraBean = (OrdenCompraBean) event.getObject();
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorId(ordenCompraBean.getEntidadBean());
            ordenCompraBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoCadaUnoMate = ordenCompraBean.getMontoCadaUnoMate();

            fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCoti() {
        try {
            if (flgTipoOrdenCompra == true) {
                flgCotizaciones = true;
                flgSolicitudes = false;
            } else {
                flgCotizaciones = false;
                flgSolicitudes = true;
            }
        } catch (Exception e) {
        }
    }

    //AYUDAS DE COTIZACVION
    public void obtenerPorFiltroCotizaciones() {
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            if (cotizacionFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(cotizacionFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                cotizacionFiltroBean.setFechaInicio(t);
            }
            if (cotizacionFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(cotizacionFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                cotizacionFiltroBean.setFechaFin(u);
            }
            if (cotizacionFiltroBean.getIdRequerimiento() != null) {
                cotizacionFiltroBean.setIdRequerimiento(cotizacionFiltroBean.getIdRequerimiento());
            }
//            listaCotizacionBean = cotizacionService.obtenerTodosSolDes(cotizacionFiltroBean);
//            listaCotizacionBean = cotizacionService.obtenerPorFiltro(cotizacionFiltroBean);
            listaCotizacionBean = cotizacionService.obtenerTodosM(cotizacionFiltroBean);
            if (listaCotizacionBean.isEmpty()) {
                listaCotizacionBean = new ArrayList<>();
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Centro de Responsabilidad
    public void distribuir() {
        try {
            //1.-Mappear Dual a Lista
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
            //2.-Invocar calculadora
            for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                if (facturaCompraBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                        facturaCompraBean.setMontoPago(facturaSessionBean.getMontoPago());
                        new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, facturaSessionBean.getMontoPago());
                        break;
                    }
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                        facturaCompraBean.setMontoPago(facturaSessionBean.getMontoPago());
                        new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, facturaSessionBean.getMontoPago());
                        break;
                    }
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                        break;
                    }
                }
            }
            //3.-Crear Lista Respuesta
            List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                DetRegistroCompraCRBean detRequerimientoCRBean = new DetRegistroCompraCRBean();
                detRequerimientoCRBean.setOrdenCompraBean(ordenCompraBean);
                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(facturaCompraBean.getCodDistribucion()));
                detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
            }
            facturaCompraBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private DataTable dataTable;

    public boolean validarMontoPer() throws Exception {
        boolean rpta = false;
        Double sum = 0d;
//        List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean = (List<DetSolicitudCajaChCRBean>) dataTable.getValue();
        for (DetRegistroCompraCRBean detRequerimientoCRBean : facturaCompraBean.getListaDetRequerimientoCRBean()) {
            sum = sum + detRequerimientoCRBean.getValorD();
        }
        if (Objects.equals(sum, ordenCompraBean.getImporteAdelanto())) {
            return true;
        }
        return rpta;
    }

    //ayuda obtener cotizaciones
    public void obtenerPorIdCotizaciones(CotizacionBean cotizacionBean) {
        try {
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            cotizacionBean = cotizacionService.obtenerPorIdCotiParaOrden(cotizacionBean.getIdCotizacion());
            System.out.println(">>>>" + cotizacionBean.getEntidadBean().getRuc());
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorIdCot(cotizacionBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cotizacionBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la Cotizacion 
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrden(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = cotizacionBean.getMontoRef();

            ordenCompraBean.getTipoCategoriaBean().setIdCodigo(cotizacionBean.getTipoCategoriaBean().getIdCodigo());
            ordenCompraBean.setObs(cotizacionBean.getObs());
            ordenCompraBean.getEntidadBean().setRuc(cotizacionBean.getEntidadBean().getRuc());
            ordenCompraBean.getEntidadBean().setNombre(cotizacionBean.getEntidadBean().getNombre());
            ordenCompraBean.getEntidadBean().setDireccion(cotizacionBean.getEntidadBean().getDireccion());
            ordenCompraBean.getTipoFormaPagoBean().setIdCodigo(cotizacionBean.getTipoPagoBean().getIdCodigo());
            ordenCompraBean.getTipoPrioridadBean().setIdCodigo(cotizacionBean.getTipoPrioridadBean().getIdCodigo());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(OrdenCompraBean bean) {
        try {
            this.flgGrabarAc=true;
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            ordenCompraBean = new OrdenCompraBean();
            sumaImporte = 0.00;
            montoCadaUnoMate = 0.00;
            bean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean = ordenCompraService.obtenerPorId(bean);
            //obtengo el proveedor correspondiente
            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(ordenCompraBean.getEntidadBean().getRuc());
            entidad.setNombre(ordenCompraBean.getEntidadBean().getNombre());
            entidad.setDireccion(ordenCompraBean.getEntidadBean().getDireccion());
            ordenCompraBean.setIdOrdenCompra(ordenCompraBean.getIdOrdenCompra());
//            entidadBean = entidadService.obtenerEntidadPorIdCot(ordenCompraBean.getEntidadBean().getRuc(),usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean.setEntidadBean(entidad);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoCadaUnoMate = ordenCompraBean.getMontoCadaUnoMate();
            if (ordenCompraBean.getFlgAdelanto() != null) {
                if (ordenCompraBean.getFlgAdelanto()) {
                    this.flgAdelanto = true;
                } else {
                    this.flgAdelanto = false;
                }
            }
            this.flgGrabarAc=true;
            //fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void validarMonto() {
        if (ordenCompraBean.getImporteAdelanto() > 0) {
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            facturaSessionBean.setImporte(ordenCompraBean.getImporteAdelanto());
        } else {
            new MensajePrime().addInformativeMessagePer("msjAgregarMontoAdelanto");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
        }

        cargarListasFactura();

    }

    //ADELANTO
    public void elegirAdelanto() {
        try {
            if (this.flgAdelanto == true) {
                ordenCompraBean.setFlgAdelanto(true);
                this.flgAdelanto = true;
            } else {
                this.flgAdelanto = false;
                ordenCompraBean.setFlgAdelanto(false);
                ordenCompraBean.setImporteAdelanto(0.00);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularAdelanto() {
        Double importeAyuda = 0.00;
        Double importeIgv = 0.00;
        try {
            if (this.flgAdelanto == true) {
                importeIgv = (ordenCompraBean.getImporteAdelanto() * ordenCompraBean.getIgv()) / 100;
                importeAyuda = ordenCompraBean.getImporteAdelanto() + importeIgv;
                ordenCompraBean.setImporteAdelanto(importeAyuda);
            } else {
                this.flgAdelanto = false;
                ordenCompraBean.setImporteAdelanto(ordenCompraBean.getImporteAdelanto());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String actualizarEntidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
                listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void limpiarOrdenFiltro() {
        try {
            listaOrdenCompraBean = new ArrayList<>();
            ordenCompraFiltroBean = new OrdenCompraBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getOrdenCompraFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            if (ordenCompraFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(ordenCompraFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                ordenCompraFiltroBean.setFechaInicio(t);
            }
            if (ordenCompraFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(ordenCompraFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                ordenCompraFiltroBean.setFechaFin(u);
            }
//            listaOrdenCompraBean = ordenCompraService.obtenerTodosSolDes(ordenCompraFiltroBean);
//            listaOrdenCompraBean = ordenCompraService.obtenerPorFiltro(ordenCompraFiltroBean);
            ordenCompraFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraBean = ordenCompraService.obtenerTodosM(ordenCompraFiltroBean);
            if (listaOrdenCompraBean.isEmpty()) {
                listaOrdenCompraBean = new ArrayList<>();
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroProveedores() {
        try {
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            if (ordenCompraDetalleFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(ordenCompraDetalleFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                ordenCompraDetalleFiltroBean.setFechaInicio(t);
            }
            if (ordenCompraDetalleFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(ordenCompraDetalleFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                ordenCompraDetalleFiltroBean.setFechaFin(u);
            }
            if (ordenCompraDetalleFiltroBean.getOrdenCompraBean().getEntidadBean().getNombre() != null) {
                ordenCompraDetalleFiltroBean.getOrdenCompraBean().getEntidadBean().getNombre();
            }
            if (ordenCompraDetalleFiltroBean.getCatalogoBean().getItem() != null) {
                ordenCompraDetalleFiltroBean.getCatalogoBean().getItem();
            }
            ordenCompraDetalleFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerTodosProveedores(ordenCompraDetalleFiltroBean);
            if (listaOrdenCompraDetalleBean.isEmpty()) {
                listaOrdenCompraDetalleBean = new ArrayList<>();
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroItems() {
        try {
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            if (ordenCompraDetalleFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(ordenCompraDetalleFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                ordenCompraDetalleFiltroBean.setFechaInicio(t);
            }
            if (ordenCompraDetalleFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(ordenCompraDetalleFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                ordenCompraDetalleFiltroBean.setFechaFin(u);
            }
            if (ordenCompraDetalleFiltroBean.getCatalogoBean().getItem() != null) {
                ordenCompraDetalleFiltroBean.getCatalogoBean().getItem();
            }
            ordenCompraDetalleFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerTodosItems(ordenCompraDetalleFiltroBean);
            if (listaOrdenCompraDetalleBean.isEmpty()) {
                listaOrdenCompraDetalleBean = new ArrayList<>();
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularImportePorItem() {
        try {

            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean tipoCategoriaBean = new CodigoBean();

            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(ordenCompraBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
            solicitudLogisticoBean.setTipoCategoriaBean(tipoCategoriaBean);
            sumaImporte = 0.0;
            for (OrdenCompraDetalleBean lista : listaOrdenCompraDetalleBean) {
                sumaImporte = sumaImporte + lista.getCantidad() * lista.getImporte();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            ordenCompraBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = ordenCompraDetalleService.obtenerUltimo(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompra.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");

            File file = new File(archivoJasper);
            List<DetOrdenCompraRepBean> listaDetOrdenCompraBean = new ArrayList<>();
            listaDetOrdenCompraBean = ordenCompraDetalleService.obtenerOrdenCompra(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetOrdenCompraBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosPdfContrato() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = ordenCompraDetalleService.obtenerUltimo(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCantratoAdquisicion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            System.out.println("ruta: " + archivoJasper);
            File file = new File(archivoJasper);
            List<ContratoAdquisicionRepBean> listaDetContratoBean = new ArrayList<>();
            listaDetContratoBean = ordenCompraDetalleService.obtenerContratoAdqui(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());

            List<DetContratoAdquisicionRepBean> listaDetDetContratoBean = new ArrayList<>();
            listaDetDetContratoBean = ordenCompraDetalleService.obtenerDetContratoAdquisicion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());
            listaDetContratoBean.get(0).setListaContrato(listaDetDetContratoBean);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetContratoBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosPdfContratoWord() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {

            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = ordenCompraDetalleService.obtenerUltimo(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCantratoAdquisicion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            System.out.println("ruta: " + archivoJasper);
            File file = new File(archivoJasper);
            List<ContratoAdquisicionRepBean> listaDetContratoBean = new ArrayList<>();
            listaDetContratoBean = ordenCompraDetalleService.obtenerContratoAdqui(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());

            List<DetContratoAdquisicionRepBean> listaDetDetContratoBean = new ArrayList<>();
            listaDetDetContratoBean = ordenCompraDetalleService.obtenerDetContratoAdquisicion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());
            listaDetContratoBean.get(0).setListaContrato(listaDetDetContratoBean);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetContratoBean);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);

            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + jasperPrint.getName() + ".docx");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();

//            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            out = response.getOutputStream();
//            out.write(bytes);
//            out.flush();
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirFiltroPdf() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            ordenCompraBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrdenFiltro(ordenCompraDetalleFiltroBean);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteFiltroProveItem.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetFiltroProveItemRepBean> listaDetOrdenCompraBean = new ArrayList<>();
            for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
                DetFiltroProveItemRepBean detFiltroProveItemRepBean = new DetFiltroProveItemRepBean();
                Timestamp t = new Timestamp(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getFechaOrden().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detFiltroProveItemRepBean.setFechaorden(t);
                detFiltroProveItemRepBean.setIdordencompra(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getIdOrdenCompra());
                detFiltroProveItemRepBean.setNombreUniNeg(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                detFiltroProveItemRepBean.setNombreOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getEntidadBean().getNombre());
                detFiltroProveItemRepBean.setRucOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getEntidadBean().getRuc());
                detFiltroProveItemRepBean.setItem(listaOrdenCompraDetalleBean.get(i).getCatalogoBean().getItem());
                detFiltroProveItemRepBean.setPrecioOrden(listaOrdenCompraDetalleBean.get(i).getImporte());
                detFiltroProveItemRepBean.setDireccionUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
                detFiltroProveItemRepBean.setDistritoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                detFiltroProveItemRepBean.setPaisUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
                detFiltroProveItemRepBean.setTelefonoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
                detFiltroProveItemRepBean.setCorreoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
                detFiltroProveItemRepBean.setWebUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
//                }
                listaDetOrdenCompraBean.add(detFiltroProveItemRepBean);

            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetOrdenCompraBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosPdfGeneral() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();

//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = ordenCompraDetalleService.obtenerUltimo(ordenCompraBean.getIdOrdenCompra());
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerTodos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompraGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetOrdenCompraGeneral> listaDetOrdenCompraBean = new ArrayList<>();
            for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
                DetOrdenCompraGeneral detOrdenCompraGeneral = new DetOrdenCompraGeneral();
                detOrdenCompraGeneral.setIdOrdenCompra(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getIdOrdenCompra());
                detOrdenCompraGeneral.setNombreUniNeg(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                detOrdenCompraGeneral.setNombreOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getEntidadBean().getNombre());
                detOrdenCompraGeneral.setRucOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getEntidadBean().getRuc());
                detOrdenCompraGeneral.setDireccionOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getEntidadBean().getDireccion());
                detOrdenCompraGeneral.setFechaOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getFechaOrden());
                detOrdenCompraGeneral.setCategoria(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getTipoCategoriaBean().getCodigo());
                detOrdenCompraGeneral.setTipoMoneda(listaOrdenCompraDetalleBean.get(i).getTipoMonedaBean().getCodigo());
                detOrdenCompraGeneral.setUniNeg(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getUniNeg());
                detOrdenCompraGeneral.setItem(listaOrdenCompraDetalleBean.get(i).getCatalogoBean().getItem());
                detOrdenCompraGeneral.setCantidad(listaOrdenCompraDetalleBean.get(i).getCantidad());
                detOrdenCompraGeneral.setImporte(listaOrdenCompraDetalleBean.get(i).getImporte());
                detOrdenCompraGeneral.setMontoCadaUnoMate(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getMontoCadaUnoMate());
                detOrdenCompraGeneral.setSumaImporteFinal(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getMontoRef());
                detOrdenCompraGeneral.setDireccionUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
                detOrdenCompraGeneral.setFormaPago(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getTipoFormaPagoBean().getCodigo());
                detOrdenCompraGeneral.setNombreUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getNombre());
                detOrdenCompraGeneral.setRucUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getRuc());
                detOrdenCompraGeneral.setDistritoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                detOrdenCompraGeneral.setPaisUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
                detOrdenCompraGeneral.setTelefonoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
                detOrdenCompraGeneral.setCorreoUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
                detOrdenCompraGeneral.setWebUnidad(listaOrdenCompraDetalleBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
                detOrdenCompraGeneral.setMontoGeneralOrden(listaOrdenCompraDetalleBean.get(i).getOrdenCompraBean().getMontoGeneralOrden());
                detOrdenCompraGeneral.setTipoUniMed(listaOrdenCompraDetalleBean.get(i).getCatalogoBean().getTipoUnidadMedidaBean().getCodigo());
//                }
                listaDetOrdenCompraBean.add(detOrdenCompraGeneral);

            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetOrdenCompraBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public String grabar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
                OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                if (ordenCompraBean.getIdOrdenCompra() == null) {
                    if (ordenCompraBean.getNroCompra() != null && !ordenCompraBean.getNroCompra().equals("")
                            && ordenCompraBean.getTipoCategoriaBean().getIdCodigo() != null && !ordenCompraBean.getTipoCategoriaBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getTipoPrioridadBean().getIdCodigo() != null && !ordenCompraBean.getTipoPrioridadBean().getIdCodigo().equals(0)
                            && sumaImporte != null && !sumaImporte.equals(0.0)
                            && ordenCompraBean.getImportePropuesto() != null && !ordenCompraBean.getImportePropuesto().equals(0.0)
                            && ordenCompraBean.getLugarEntrega() != null && !ordenCompraBean.getLugarEntrega().equals("")
                            && ordenCompraBean.getAtencion() != null && !ordenCompraBean.getAtencion().equals("")
                            && ordenCompraBean.getTipoFormaPagoBean().getIdCodigo() != null && !ordenCompraBean.getTipoFormaPagoBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getObs() != null && !ordenCompraBean.getObs().equals("")
                            && ordenCompraBean.getTipoPagoBean().getIdCodigo() != null && !ordenCompraBean.getTipoPagoBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getFechaEntrega() != null
                            && ordenCompraBean.getEntidadBean().getRuc() != null && !ordenCompraBean.getEntidadBean().getRuc().equals("")
                            && ordenCompraBean.getEntidadBean().getNombre() != null && !ordenCompraBean.getEntidadBean().getNombre().equals("")) {
                        ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                        ordenCompraBean.getTipoStatusRegCBean().setIdCodigo(MaristaConstantes.COD_COMPRADO_REGISTRO_COMPRA);
                        solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_Comprado_Parcial);
                        solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                        ordenCompraService.insertar(ordenCompraBean, solicitudLogisticoBean, listaSesionFacturaCompraBean, getDetRegistroCompraCRBean());
                        ordenCompraDetalleService.insertar(ordenCompraBean.getIdOrdenCompra(), ordenCompraBean.getAnio(), listaOrdenCompraDetalleBean);
                        SolicitudLogisticoDetalleService soli = BeanFactory.getSolicitudLogisticoDetalleService();
                        for (OrdenCompraDetalleBean orden : listaOrdenCompraDetalleBean) {
                            orden.getSolicitudLogDetalleBean().setIdRequerimiento(orden.getSolicitudLogisticoBean().getIdRequerimiento());
                            orden.getSolicitudLogDetalleBean().setIdDetRequerimiento(orden.getSolicitudLogDetalleBean().getIdDetRequerimiento());
                            soli.modificarFlgCompraTotal(orden.getSolicitudLogDetalleBean());
                        }
                        for (OrdenCompraDetalleBean orden2 : listaOrdenCompraDetalleBean) {
                            Integer idsol = orden2.getSolicitudLogisticoBean().getIdRequerimiento();
                            String uniNeg = ordenCompraBean.getUnidadNegocioBean().getUniNeg();
                            SolicitudLogDetalleBean solre = new SolicitudLogDetalleBean();
                            solre.setIdRequerimiento(idsol);
                            solre.getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(uniNeg);
                            listaSolicitudLogDetalleBean = soli.obtenerPorSolicitud(idsol, uniNeg);
                            for (SolicitudLogDetalleBean so : listaSolicitudLogDetalleBean) {
                                if (so.getFlgComprado() == false) {
                                    solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_COMPRAREQUERIMIENTO);
                                    solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                                    solicitudLogisticoBean.setIdRequerimiento(idsol);
                                    solicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(orden2.getUnidadNegocioBean().getUniNeg());
                                    solicitudLogisticoService.cambiarEstadoSolicitudLogComprado(solicitudLogisticoBean);
                                } else {
                                    solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_Comprado_Parcial);
                                    solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                                    solicitudLogisticoBean.setIdRequerimiento(idsol);
                                    solicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(orden2.getUnidadNegocioBean().getUniNeg());
                                    solicitudLogisticoService.cambiarEstadoSolicitudLogParcial(solicitudLogisticoBean);
                                }
                            }
                        }
                        for (CodigoBean bean : listaTipoFormaPagoBean) {
                            if (bean.getCodigo().equals("Cheque")) {
                                getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                                this.idTipoFormaPago = bean.getIdCodigo();
                            }
                        }
                        listaOrdenCompraBean = ordenCompraService.obtenerTodosM(ordenCompraFiltroBean);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        System.out.println("ok");
                        this.flgGrabarAc=true;
                    } else {
                        new MensajePrime().addInformativeMessagePer("Llene todos los campos requeridoss");
                        this.flgGrabarAc=false;
                    }
                } else {
                    if (ordenCompraBean.getNroCompra() != null && !ordenCompraBean.getNroCompra().equals("")
                            && ordenCompraBean.getTipoCategoriaBean().getIdCodigo() != null && !ordenCompraBean.getTipoCategoriaBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getTipoPrioridadBean().getIdCodigo() != null && !ordenCompraBean.getTipoPrioridadBean().getIdCodigo().equals(0)
                            && sumaImporte != null && !sumaImporte.equals(0.0)
                            && ordenCompraBean.getImportePropuesto() != null && !ordenCompraBean.getImportePropuesto().equals(0.0)
                            && ordenCompraBean.getLugarEntrega() != null && !ordenCompraBean.getLugarEntrega().equals("")
                            && ordenCompraBean.getAtencion() != null && !ordenCompraBean.getAtencion().equals("")
                            && ordenCompraBean.getTipoFormaPagoBean().getIdCodigo() != null && !ordenCompraBean.getTipoFormaPagoBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getObs() != null && !ordenCompraBean.getObs().equals("")
                            && ordenCompraBean.getTipoPagoBean().getIdCodigo() != null && !ordenCompraBean.getTipoPagoBean().getIdCodigo().equals(0)
                            && ordenCompraBean.getFechaEntrega() != null
                            && ordenCompraBean.getEntidadBean().getRuc() != null && !ordenCompraBean.getEntidadBean().getRuc().equals("")
                            && ordenCompraBean.getEntidadBean().getNombre() != null && !ordenCompraBean.getEntidadBean().getNombre().equals("")) {
//                    ordenCompraBean.setMontoRef(sumaImporte);
                        ordenCompraBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        ordenCompraBean.setMontoCadaUnoMate(montoCadaUnoMate);
                        ordenCompraBean.setMontoGeneralOrden(montoGeneralOrden);
                        ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                        ordenCompraService.modificar(ordenCompraBean);
                        ordenCompraDetalleService.eliminar(ordenCompraBean);
                        ordenCompraDetalleService.insertar(ordenCompraBean.getIdOrdenCompra(), ordenCompraBean.getAnio(), listaOrdenCompraDetalleBean);
                        for (CodigoBean bean : listaTipoFormaPagoBean) {
                            if (bean.getCodigo().equals("Cheque")) {
                                getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                                this.idTipoFormaPago = bean.getIdCodigo();
                            }
                        }
                        listaOrdenCompraBean = ordenCompraService.obtenerTodosM(ordenCompraFiltroBean);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("Llene todos los campos requeridoss");
                    }
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String grabarCotizacionOrden() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
                OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();

                if (ordenCompraBean.getIdOrdenCompra() == null) {
                    ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                    ordenCompraBean.getTipoStatusRegCBean().setIdCodigo(MaristaConstantes.COD_COMPRADO_REGISTRO_COMPRA);
                    ordenCompraService.insertarCotizacionOrden(ordenCompraBean, listaDetCotizacionBean);
                    ordenCompraDetalleService.insertarCotizacionOrden(ordenCompraBean.getIdOrdenCompra(), ordenCompraBean.getAnio(), listaOrdenCompraDetalleBean);
                    System.out.println("ok");

                } else {
//                    ordenCompraBean.setMontoRef(sumaImporte);
                    ordenCompraBean.setMontoCadaUnoMate(montoCadaUnoMate);
                    ordenCompraBean.setMontoGeneralOrden(montoGeneralOrden);
                    ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                    ordenCompraService.modificar(ordenCompraBean);
                    ordenCompraDetalleService.eliminar(ordenCompraBean);
                    ordenCompraDetalleService.insertarCotizacionOrden(ordenCompraBean.getIdOrdenCompra(), ordenCompraBean.getAnio(), listaOrdenCompraDetalleBean);
//               for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
//                        listaOrdenCompraActDetalleBean.add(listaOrdenCompraDetalleBean.get(i));
//                    } 
                }
                for (CodigoBean bean : listaTipoFormaPagoBean) {
                    if (bean.getCodigo().equals("Cheque")) {
                        getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                        this.idTipoFormaPago = bean.getIdCodigo();
                    }
                }
                listaOrdenCompraBean = ordenCompraService.obtenerTodosM(ordenCompraFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public Boolean validarItemPorAgregar(SolicitudLogDetalleBean bean) {
        Boolean valor = false;
        try {
            //Para que lo agregue no debe existir en el detalle de esa orden de compra que esta
            //armando ni en las ordenes de compra que haya sido generada
            for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                    valor = true;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    //DETCOTIZACION
    public Boolean validarItemPorAgregarCotizacion(DetCotizacionBean bean) {
        Boolean valor = false;
        try {
            //Para que lo agregue no debe existir en el detalle de esa orden de compra que esta
            //armando ni en las ordenes de compra que haya sido generada
            for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                    valor = true;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void calcularImportePorItem(OrdenCompraDetalleBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (bean.getImporteAnterior());
                sumaImporte = sumaImporte + (bean.getImporte());

            } else//Material o Bien
            {
                sumaImporte = sumaImporte + (bean.getCantidad() * bean.getImporte());
                sumaImporte = sumaImporte - (bean.getCantidadAnterior() * bean.getImporteAnterior());

            }

            bean.setCantidadAnterior(bean.getCantidad());
            bean.setImporteAnterior(bean.getImporte());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularImportePorItemCotizacion(OrdenCompraDetalleBean bean) {
        try {
            if (cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (bean.getImporteAnterior());
                sumaImporte = sumaImporte + (bean.getImporte());

            } else//Material o Bien
            {
                sumaImporte = sumaImporte + (bean.getCantidad() * bean.getImporte());
                sumaImporte = sumaImporte - (bean.getCantidadAnterior() * bean.getImporteAnterior());

            }

            bean.setCantidadAnterior(bean.getCantidad());
            bean.setImporteAnterior(bean.getImporte());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularImportePorUno(OrdenCompraDetalleBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")) {

                montoCadaUnoMate = (bean.getCantidad() * bean.getImporte());
            }
            bean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularPorCadaUno(OrdenCompraDetalleBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                montoCadaUnoMate = (bean.getImporte());
            } else//Material o Bien
            {
                montoCadaUnoMate = (bean.getCantidad() * bean.getImporte());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificarItemDetalleOrdenCompra() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
//            ordenCompraDetalleBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            ordenCompraDetalleBean.getCatalogoBean().setItem(ordenCompraDetalleBean.getCatalogoBean().getItem());  
            for (OrdenCompraDetalleBean listasol : listaOrdenCompraDetalleBean) {
                if (listasol.getValidarCatalogo() == true) {
                    for (OrdenCompraDetalleBean lista : listaOrdenCompraDetalleBean) {
                        catalogoService.modificarItemDetalleOrdenCompra(lista.getCatalogoBean());
                    }
                } else {
                    for (OrdenCompraDetalleBean lista : listaOrdenCompraDetalleBean) {
                        catalogoService.modificarItemBlockDetalleOrdenCompra(lista.getCatalogoBean());
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItem(SolicitudLogDetalleBean bean) {
        Integer cantProp = 0;
        Double cantUno = 0.00;
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();
//            ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                //Aqui valido por el estado:Solo se agrega los items con estos estados
                if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                {

                    cantProp = (bean.getCantidadSolicitada() - bean.getCantidadEntregada());
                    cantUno = (ordenCompraDetalleBean.getCantidad() * ordenCompraDetalleBean.getImporte());

                    //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                    if (!validarItemPorAgregar(bean)) {
                        //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                        ordenCompraDetalleBean.setCantidad(cantProp);
                        ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);
                        ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                        ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFlgComprado(true);
                        bean.setFlgComprado(true);
                        listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                        sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                    } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                    {
                        for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                            if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                                //Si es el mismo item(idCatalogo) pero de distinta solicitud, se suma las cantidades
                                if (!Objects.equals(detalle.getSolicitudLogisticoBean().getIdRequerimiento(), bean.getSolicitudLogisticoBean().getIdRequerimiento())) {
                                    detalle.setCantidad(detalle.getCantidad() + cantProp);
                                    sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                                }
                            }
                        }
                    }
                }
                System.out.println("comprado " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getFlgComprado());
                System.out.println("comprado " + bean.getFlgComprado());
                CotizacionBean coti = new CotizacionBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                coti = cotizacionService.obtenerRucPorReq(ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println("coti" + coti);
                if (coti != null) {
                    if (coti != null && sumaImporte != 0) {
                        ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                        ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                    } else {
                        ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                    }
                    if (coti != null && sumaImporte == 0) {
                        ordenCompraBean.setImportePropuesto(coti.getImportePorTodo());
                        ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                    } else {
                        ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        ordenCompraBean.setNroCotiPro(ordenCompraBean.getNroCotiPro());
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

                System.out.println("lista " + listaOrdenCompraDetalleBean.size());

//                Double importePropuestoAyuda = (solicitudLogisticoBean.getImportePropuesto());
//                if (listaOrdenCompraDetalleBean.size() >= 2) {
//                    Integer nroDetalle = listaOrdenCompraDetalleBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
//
//                    if (listaOrdenCompraDetalleBean.get(1).getSolicitudLogisticoBean().getIdRequerimiento() != nroDetalle) {
//                        
//                        sumaDeImportes = sumaDeImportes + importePropuestoAyuda;
//                        System.out.println("suma Importes: " + sumaDeImportes);
//                    }
//                }
//                if (mostrarPdf) {
//                    
//                }   
//                ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFlgComprado(true);
            } else// quiere decir que la solicitud es de tipo servicio
            {
                if (!validarItemPorAgregar(bean)) {
                    //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                    ordenCompraDetalleBean.setCantidad(0);
                    ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);
                    ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                    ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                    sumaImporte = sumaImporte + (bean.getCatalogoBean().getPrecioRef());

                }
                CotizacionBean coti = new CotizacionBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                coti = cotizacionService.obtenerRucPorReq(ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println("coti" + coti);
                if (coti != null) {
                    if (coti != null && sumaDeImportes != 0) {
                        ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                        ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                    } else {
                        ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                    }
                    if (coti != null && sumaImporte == 0) {
                        ordenCompraBean.setImportePropuesto(coti.getImportePorTodo());
                        ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                    } else {
                        ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        ordenCompraBean.setNroCotiPro(ordenCompraBean.getNroCotiPro());
                    }
                }
//                ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }

            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaOrdenCompraDetalleBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
                this.flgDetalleActivo = true;
                System.out.println("flg: " + flgDetalleActivo);

            }

            listaSolicitudLogDetalleBean.remove(bean);
            System.out.println("flgcom: " + bean.getFlgComprado());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItems() {
        Integer cantProp = 0;
        Double cantUno = 0.00;
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();
                ordenCompraBean.getTipoCategoriaBean().setIdCodigo(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo());
                if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                        || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                    //Aqui valido por el estado:Solo se agrega los items con estos estados
                    if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                    {
                        //Aqui valido por la cantidad a pedir

                        cantProp = (bean.getCantidadSolicitada() - bean.getCantidadEntregada());
                        cantUno = (ordenCompraDetalleBean.getCantidad() * ordenCompraDetalleBean.getImporte());
                        //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                        if (!validarItemPorAgregar(bean)) {
                            //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                            ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                            ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                            ordenCompraDetalleBean.setCantidad(cantProp);
                            ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);
                            ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                            ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                            ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                            ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                            ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                            ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
                            System.out.println("1:" + ordenCompraDetalleBean.getValidarCatalogo());
                            System.out.println("2:" + bean.getValidarCatalogo());
                            listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                            sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                        } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                        {
                            for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                                    //Si es el mismo item(idCatalogo) pero de distinta solicitud, se suma las cantidades
                                    if (!Objects.equals(detalle.getSolicitudLogisticoBean().getIdRequerimiento(), bean.getSolicitudLogisticoBean().getIdRequerimiento())) {
                                        detalle.setCantidad(detalle.getCantidad() + cantProp);
                                        sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                                    }
                                }
                            }
                        }

                        CotizacionBean coti = new CotizacionBean();

                        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                        coti = cotizacionService.obtenerRucPorReq(ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        System.out.println("coti" + coti);
                        if (coti != null) {
                            if (coti != null && sumaImporte != 0) {
                                sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                                ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                                ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                                ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
                            } else {
                                sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                                if (coti.getFlgAceptado() == true) {
                                    ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                                } else {
                                    ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                                }
                            }
                            if (coti != null && sumaImporte == 0) {
                                ordenCompraBean.setImportePropuesto(coti.getImportePorTodo());
                                ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                                ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
                            } else {
                                if (coti.getFlgAceptado() == true) {
                                    ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                                } else {
                                    ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                                    ordenCompraBean.setNroCotiPro(ordenCompraBean.getNroCotiPro());
                                }
                            }
                        }
                        System.out.println("nroCoti: " + ordenCompraBean.getNroCotiPro());
                        System.out.println("Id Cotizacion: " + ordenCompraBean.getCotizacionBean().getIdCotizacion());
                    }
                    sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else// quiere decir que la solicitud es de tipo servicio
                {
                    if (!validarItemPorAgregar(bean)) {
                        //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        ordenCompraDetalleBean.setCantidad(0);
                        ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);
                        ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                        ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                        sumaImporte = sumaImporte + (bean.getCatalogoBean().getPrecioRef());
                        System.out.println("imp pro " + solicitudLogisticoBean.getImportePropuesto());
                        CotizacionBean coti = new CotizacionBean();
                        Integer idrequerimiento = ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento();
                        coti = cotizacionService.obtenerRucPorReq(idrequerimiento, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        if (coti != null && sumaDeImportes != 0) {
                            ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                            ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                            ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
                            ordenCompraBean.getEntidadBean().setRuc(coti.getEntidadBean().getRuc());
                            ordenCompraBean.getEntidadBean().setDireccion(coti.getEntidadBean().getDireccion());
                            ordenCompraBean.getEntidadBean().setNombre(coti.getEntidadBean().getNombre());
                        } else {
                            ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        }
                        if (coti != null && sumaImporte == 0) {
                            ordenCompraBean.setImportePropuesto(coti.getImportePorTodo());
                            ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                            ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
                            ordenCompraBean.getEntidadBean().setRuc(coti.getEntidadBean().getRuc());
                            ordenCompraBean.getEntidadBean().setDireccion(coti.getEntidadBean().getDireccion());
                            ordenCompraBean.getEntidadBean().setNombre(coti.getEntidadBean().getNombre());
                        } else {
                            ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                            ordenCompraBean.setNroCotiPro(ordenCompraBean.getNroCotiPro());
                        }
                    }
                }
                System.out.println("nroCoti: " + ordenCompraBean.getNroCotiPro());
                System.out.println("Id Cotizacion: " + ordenCompraBean.getCotizacionBean().getIdCotizacion());
                sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
//                ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaOrdenCompraDetalleBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
                this.flgDetalleActivo = true;
                System.out.println("flg: " + flgDetalleActivo);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //DetCotizacion
    public void agregarItemsCotizacion() {
        Integer cantProp = 0;
        Double cantUno = 0.00;
        try {
            for (DetCotizacionBean bean : listaDetCotizacionBean) {
                OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();

                //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                if (!validarItemPorAgregarCotizacion(bean)) {
                    //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.setCotizacionBean(bean.getCotizacionBean());
                    ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                    ordenCompraDetalleBean.setCantidad(cantProp);
                    ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoMate);
                    ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                    ordenCompraDetalleBean.getCotizacionBean().setIdCotizacion(bean.getCotizacionBean().getIdCotizacion());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.setCantidad(bean.getCantidad());
                    ordenCompraDetalleBean.setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                    ordenCompraDetalleBean.setIdDetRequerimiento(bean.getSolicitudLogDetalleBean().getIdDetRequerimiento());
                    ordenCompraDetalleBean.getCotizacionBean().setIdCotizacion(bean.getCotizacionBean().getIdCotizacion());
                    ordenCompraDetalleBean.getDetCotizacionBean().setIdDetCotizacion(bean.getIdDetCotizacion());

                    listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                    sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                {
                    for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                        if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                            //Si es el mismo item(idCatalogo) pero de distinta solicitud, se suma las cantidades
                            if (!Objects.equals(detalle.getCotizacionBean().getIdCotizacion(), bean.getCotizacionBean().getIdCotizacion())) {
                                detalle.setCantidad(detalle.getCantidad() + cantProp);
                                detalle.setIdRequerimiento(detalle.getDetCotizacionBean().getSolicitudLogDetalleBean().getIdRequerimiento());
                                detalle.setIdDetRequerimiento(detalle.getDetCotizacionBean().getSolicitudLogDetalleBean().getIdDetRequerimiento());
                                sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                            }
                        }
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void asignarDatosSolParaOrdenCompra() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();

            //Asigno el tipo de orden de compra deacuerdo al tipo de requerimiento
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                tipoCategoriaBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Compra", new TipoCodigoBean("TipoCategoriaCompra")));
            } else {
                tipoCategoriaBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Servicio", new TipoCodigoBean("TipoCategoriaCompra")));
            }

            ordenCompraBean.setTipoCategoriaBean(tipoCategoriaBean);//Paso el tipo de orden

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //detCotizacion a Orden de Compra
    public void asignarDatosSolParaOrdenCompraCotizacion() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();

            //Asigno el tipo de orden de compra deacuerdo al tipo de requerimiento
            if (cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                tipoCategoriaBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Compra", new TipoCodigoBean("TipoCategoriaCompra")));
            } else {
                tipoCategoriaBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Servicio", new TipoCodigoBean("TipoCategoriaCompra")));
            }

            ordenCompraBean.setTipoCategoriaBean(tipoCategoriaBean);//Paso el tipo de orden

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarItem(OrdenCompraDetalleBean bean) {
        try {

            sumaImporte = sumaImporte - (bean.getCantidad() * bean.getImporte());

            listaOrdenCompraDetalleBean.remove(bean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectDetalle(SelectEvent event) {
        try {
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();

            solicitudLogisticoBean = (SolicitudLogisticoBean) event.getObject();
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetalleSolPorId(SolicitudLogisticoBean bean) {
        try {
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(bean.getIdRequerimiento(), bean.getUnidadNegocioBean().getUniNeg());
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitudCompra(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
            CotizacionBean coti = new CotizacionBean();
            coti = cotizacionService.obtenerRucPorReq(bean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (coti != null) {
                EntidadBean entidad = new EntidadBean();
                entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                entidad.setRuc(coti.getEntidadBean().getRuc());
                entidad = entidadService.obtenerEntidadPorId(entidad);
                if (entidad != null) {
                    ordenCompraBean.setEntidadBean(entidad);
                }
            }
            ordenCompraBean.getTipoCategoriaBean().setIdCodigo(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo());
            ordenCompraBean.getTipoPrioridadBean().setIdCodigo(solicitudLogisticoBean.getTipoPrioridadBean().getIdCodigo());
            ordenCompraBean.setObs(bean.getTitulo());

            //VALIDAR DETALLE 
            if (listaOrdenCompraDetalleBean != null) {
                if (!listaOrdenCompraDetalleBean.isEmpty()) {
                    List<OrdenCompraDetalleBean> lista = new ArrayList<>();
                    lista = listaOrdenCompraDetalleBean;
                    for (OrdenCompraDetalleBean list : lista) {
                        if (!listaSolicitudLogDetalleBean.isEmpty()) {
                            for (SolicitudLogDetalleBean listaSoli : listaSolicitudLogDetalleBean) {
                                System.out.println("id det-" + listaSoli.getIdDetRequerimiento());
                                System.out.println("id oc-" + list.getSolicitudLogDetalleBean().getIdDetRequerimiento());
                                if (list.getSolicitudLogDetalleBean().getIdDetRequerimiento().equals(listaSoli.getIdDetRequerimiento())) {
                                    listaSolicitudLogDetalleBean.remove(listaSoli);
                                    System.out.println("remove");
                                }
                                break;
                            }
                        }
                    }
                }

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //  public void obtenerSedePorId(EntidadSedeBean bean) {
//    try {
//      EntidadService entidadService = BeanFactory.getEntidadService();
//            entidadSedeBean = entidadService.obtenerEntidadSedePorId(bean);
//Paso los valores del bean del popup al bean de guardar
//    ordenCompraBean.setEntidadSedeBean(entidadSedeBean);
//} catch (Exception ex) {
//  new MensajePrime().addErrorGeneralMessage();
//GLTLog.writeError(this.getClass(), ex);
// }
//}
    public void obtenerEntidadPorId(EntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();

            entidadBean = entidadService.obtenerEntidadPorId(bean);

            //Paso los valores del bean del popup al bean de guardar
            ordenCompraBean.setEntidadBean(entidadBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarEntidades() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();

            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
//            if (ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Servicio")
//                    || ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Material")
//                    || ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {

            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);

//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<ViewEntidadBean> getListaViewEntidadProveedorBean() {
        if (listaViewEntidadProveedorBean == null) {
            listaViewEntidadProveedorBean = new ArrayList<>();
        }
        return listaViewEntidadProveedorBean;
    }

    public void setListaViewEntidadProveedorBean(List<ViewEntidadBean> listaViewEntidadProveedorBean) {
        this.listaViewEntidadProveedorBean = listaViewEntidadProveedorBean;
    }

    public List<CodigoBean> getListaTipoCategoriaBean() {
        if (listaTipoCategoriaBean == null) {
            listaTipoCategoriaBean = new ArrayList<>();
        }
        return listaTipoCategoriaBean;
    }

    public void setListaTipoCategoriaBean(List<CodigoBean> listaTipoCategoriaBean) {
        this.listaTipoCategoriaBean = listaTipoCategoriaBean;
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

    public CodigoBean getCodigoBean() {
        if (codigoBean == null) {
            codigoBean = new CodigoBean();
        }
        return codigoBean;
    }

    public void setCodigoBean(CodigoBean codigoBean) {
        this.codigoBean = codigoBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudServiciosBean() {
        if (listaSolicitudServiciosBean == null) {
            listaSolicitudServiciosBean = new ArrayList<>();
        }
        return listaSolicitudServiciosBean;
    }

    public void setListaSolicitudServiciosBean(List<SolicitudLogisticoBean> listaSolicitudServiciosBean) {
        this.listaSolicitudServiciosBean = listaSolicitudServiciosBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudActivosMaBean() {
        if (listaSolicitudActivosMaBean == null) {
            listaSolicitudActivosMaBean = new ArrayList<>();
        }
        return listaSolicitudActivosMaBean;
    }

    public void setListaSolicitudActivosMaBean(List<SolicitudLogisticoBean> listaSolicitudActivosMaBean) {
        this.listaSolicitudActivosMaBean = listaSolicitudActivosMaBean;
    }

    public OrdenCompraDetalleBean getOrdenCompraDetalleBean() {
        if (ordenCompraDetalleBean == null) {
            ordenCompraDetalleBean = new OrdenCompraDetalleBean();
        }
        return ordenCompraDetalleBean;
    }

    public void setOrdenCompraDetalleBean(OrdenCompraDetalleBean ordenCompraDetalleBean) {
        this.ordenCompraDetalleBean = ordenCompraDetalleBean;
    }

    public Boolean getMostrarPdf() {
        return mostrarPdf;
    }

    public void setMostrarPdf(Boolean mostrarPdf) {
        this.mostrarPdf = mostrarPdf;
    }

    public Double getMontoCadaUnoMate() {
        return montoCadaUnoMate;
    }

    public void setMontoCadaUnoMate(Double montoCadaUnoMate) {
        this.montoCadaUnoMate = montoCadaUnoMate;
    }

    public Double getMontoGeneralOrden() {
        return montoGeneralOrden;
    }

    public void setMontoGeneralOrden(Double montoGeneralOrden) {
        this.montoGeneralOrden = montoGeneralOrden;
    }

    public Integer getIdTipoFormaPago() {
        return idTipoFormaPago;
    }

    public void setIdTipoFormaPago(Integer idTipoFormaPago) {
        this.idTipoFormaPago = idTipoFormaPago;
    }

    public Boolean getFlgCotizaciones() {
        return flgCotizaciones;
    }

    public void setFlgCotizaciones(Boolean flgCotizaciones) {
        this.flgCotizaciones = flgCotizaciones;
    }

    public Boolean getFlgSolicitudes() {
        return flgSolicitudes;
    }

    public void setFlgSolicitudes(Boolean flgSolicitudes) {
        this.flgSolicitudes = flgSolicitudes;
    }

    public CotizacionBean getCotizacionFiltroBean() {
        if (cotizacionFiltroBean == null) {
            cotizacionFiltroBean = new CotizacionBean();
        }
        return cotizacionFiltroBean;
    }

    public void setCotizacionFiltroBean(CotizacionBean cotizacionFiltroBean) {
        this.cotizacionFiltroBean = cotizacionFiltroBean;
    }

    public List<CotizacionBean> getListaCotizacionBean() {
        if (listaCotizacionBean == null) {
            listaCotizacionBean = new ArrayList<>();
        }
        return listaCotizacionBean;
    }

    public void setListaCotizacionBean(List<CotizacionBean> listaCotizacionBean) {
        this.listaCotizacionBean = listaCotizacionBean;
    }

    public CotizacionBean getCotizacionBean() {
        if (cotizacionBean == null) {
            cotizacionBean = new CotizacionBean();
        }
        return cotizacionBean;
    }

    public void setCotizacionBean(CotizacionBean cotizacionBean) {
        this.cotizacionBean = cotizacionBean;
    }

    public List<DetCotizacionBean> getListaDetCotizacionBean() {
        if (listaDetCotizacionBean == null) {
            listaDetCotizacionBean = new ArrayList<>();
        }
        return listaDetCotizacionBean;
    }

    public void setListaDetCotizacionBean(List<DetCotizacionBean> listaDetCotizacionBean) {
        this.listaDetCotizacionBean = listaDetCotizacionBean;
    }

    public Boolean getFlgTipoOrdenCompra() {
        return flgTipoOrdenCompra;
    }

    public void setFlgTipoOrdenCompra(Boolean flgTipoOrdenCompra) {
        this.flgTipoOrdenCompra = flgTipoOrdenCompra;
    }

    public List<CotizacionBean> getListaCotizacionActivosMaBean() {
        return listaCotizacionActivosMaBean;
    }

    public void setListaCotizacionActivosMaBean(List<CotizacionBean> listaCotizacionActivosMaBean) {
        this.listaCotizacionActivosMaBean = listaCotizacionActivosMaBean;
    }

    public List<CotizacionBean> getLsitaCotizacionesAceptadas() {
        if (lsitaCotizacionesAceptadas == null) {
            lsitaCotizacionesAceptadas = new ArrayList<>();
        }
        return lsitaCotizacionesAceptadas;
    }

    public void setLsitaCotizacionesAceptadas(List<CotizacionBean> lsitaCotizacionesAceptadas) {
        this.lsitaCotizacionesAceptadas = lsitaCotizacionesAceptadas;
    }

    public DetCotizacionBean getDetCotizacionBean() {
        if (detCotizacionBean == null) {
            detCotizacionBean = new DetCotizacionBean();
        }
        return detCotizacionBean;
    }

    public void setDetCotizacionBean(DetCotizacionBean detCotizacionBean) {
        this.detCotizacionBean = detCotizacionBean;
    }

    public OrdenCompraDetalleBean getOrdenCompraDetalleFiltroBean() {
        if (ordenCompraDetalleFiltroBean == null) {
            ordenCompraDetalleFiltroBean = new OrdenCompraDetalleBean();
        }
        return ordenCompraDetalleFiltroBean;
    }

    public void setOrdenCompraDetalleFiltroBean(OrdenCompraDetalleBean ordenCompraDetalleFiltroBean) {
        this.ordenCompraDetalleFiltroBean = ordenCompraDetalleFiltroBean;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Boolean getFlgAdelanto() {
        return flgAdelanto;
    }

    public void setFlgAdelanto(Boolean flgAdelanto) {
        this.flgAdelanto = flgAdelanto;
    }

    public FacturaCompraBean getFacturaSessionBean() {
        if (facturaSessionBean == null) {
            facturaSessionBean = new FacturaCompraBean();
        }

        return facturaSessionBean;
    }

    public void setFacturaSessionBean(FacturaCompraBean facturaSessionBean) {
        this.facturaSessionBean = facturaSessionBean;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public List<DetraccionBean> getListaDetraccionBean() {
        if (listaDetraccionBean == null) {
            listaDetraccionBean = new ArrayList<>();
        }
        return listaDetraccionBean;
    }

    public void setListaDetraccionBean(List<DetraccionBean> listaDetraccionBean) {
        this.listaDetraccionBean = listaDetraccionBean;
    }

    public List<FacturaCompraBean> getListaSesionFacturaCompraBean() {
        if (listaSesionFacturaCompraBean == null) {
            listaSesionFacturaCompraBean = new ArrayList<>();

        }
        return listaSesionFacturaCompraBean;
    }

    public void setListaSesionFacturaCompraBean(List<FacturaCompraBean> listaSesionFacturaCompraBean) {
        this.listaSesionFacturaCompraBean = listaSesionFacturaCompraBean;
    }

    public List<CodigoBean> getListaTipoMonedaBean() {
        if (listaTipoMonedaBean == null) {
            listaTipoMonedaBean = new ArrayList<>();
        }
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public List<CodigoBean> getListaTipoDocBean() {
        if (listaTipoDocBean == null) {
            listaTipoDocBean = new ArrayList<>();
        }
        return listaTipoDocBean;
    }

    public void setListaTipoDocBean(List<CodigoBean> listaTipoDocBean) {
        this.listaTipoDocBean = listaTipoDocBean;
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

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getMontoDetraccion() {
        return montoDetraccion;
    }

    public void setMontoDetraccion(Double montoDetraccion) {
        this.montoDetraccion = montoDetraccion;
    }

    public Double getIgvResultado() {
        return igvResultado;
    }

    public void setIgvResultado(Double igvResultado) {
        this.igvResultado = igvResultado;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
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

    public List<CodigoBean> getListaTipoDistribucionCRBean() {
        if (listaTipoDistribucionCRBean == null) {
            listaTipoDistribucionCRBean = new ArrayList<>();
        }
        return listaTipoDistribucionCRBean;
    }

    public void setListaTipoDistribucionCRBean(List<CodigoBean> listaTipoDistribucionCRBean) {
        this.listaTipoDistribucionCRBean = listaTipoDistribucionCRBean;
    }

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidadBean() {
        if (dualCentroResponsabilidadBean == null) {
            dualCentroResponsabilidadBean = new DualListModel<>();
        }
        return dualCentroResponsabilidadBean;
    }

    public void setDualCentroResponsabilidadBean(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean) {
        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
    }

    public List<CodigoBean> getListaTipoStatusRegCBean() {
        if (listaTipoStatusRegCBean == null) {
            listaTipoStatusRegCBean = new ArrayList<>();
        }
        return listaTipoStatusRegCBean;
    }

    public void setListaTipoStatusRegCBean(List<CodigoBean> listaTipoStatusRegCBean) {
        this.listaTipoStatusRegCBean = listaTipoStatusRegCBean;
    }

    public Integer getIdTipoStatusRegC() {
        return idTipoStatusRegC;
    }

    public void setIdTipoStatusRegC(Integer idTipoStatusRegC) {
        this.idTipoStatusRegC = idTipoStatusRegC;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public void setListaCentroResponsabilidadBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
        this.listaCentroResponsabilidadBean = listaCentroResponsabilidadBean;
    }

    public Integer getIdTipoDistribucion() {
        return idTipoDistribucion;
    }

    public void setIdTipoDistribucion(Integer idTipoDistribucion) {
        this.idTipoDistribucion = idTipoDistribucion;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanB() {
        if (listaCentroResponsabilidadBeanB == null) {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
        }
        return listaCentroResponsabilidadBeanB;
    }

    public void setListaCentroResponsabilidadBeanB(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB) {
        this.listaCentroResponsabilidadBeanB = listaCentroResponsabilidadBeanB;
    }

    public List<DetRegistroCompraCRBean> getListaDetRequerimientoCRBean() {
        return listaDetRequerimientoCRBean;
    }

    public void setListaDetRequerimientoCRBean(List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean) {
        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
    }

    public DetRegistroCompraCRBean getDetRegistroCompraCRBean() {
        if (detRegistroCompraCRBean == null) {
            detRegistroCompraCRBean = new DetRegistroCompraCRBean();
        }
        return detRegistroCompraCRBean;
    }

    public void setDetRegistroCompraCRBean(DetRegistroCompraCRBean detRegistroCompraCRBean) {
        this.detRegistroCompraCRBean = detRegistroCompraCRBean;
    }

    public Double getSumaDeImportes() {
        return sumaDeImportes;
    }

    public void setSumaDeImportes(Double sumaDeImportes) {
        this.sumaDeImportes = sumaDeImportes;
    }

    public List<CodigoBean> getListaTipoPagoBean() {
        if (listaTipoPagoBean == null) {
            listaTipoPagoBean = new ArrayList<>();
        }
        return listaTipoPagoBean;
    }

    public void setListaTipoPagoBean(List<CodigoBean> listaTipoPagoBean) {
        this.listaTipoPagoBean = listaTipoPagoBean;
    }

    public Boolean getFlgDetalleActivo() {
        return flgDetalleActivo;
    }

    public void setFlgDetalleActivo(Boolean flgDetalleActivo) {
        this.flgDetalleActivo = flgDetalleActivo;
    }

    public Boolean getFlgGrabarAc() {
        return flgGrabarAc;
    }

    public void setFlgGrabarAc(Boolean flgGrabarAc) {
        this.flgGrabarAc = flgGrabarAc;
    }

}
