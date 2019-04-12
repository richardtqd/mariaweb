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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.marista.sigma.MaristaConstantes;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
//import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EntidadSedeBean;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.reporte.DetOrdenCompraGeneral;
import pe.marista.sigma.bean.reporte.DetCotizacionRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.DetCotizacionService;
import pe.marista.sigma.service.CotizacionService;
import pe.marista.sigma.service.OrdenCompraService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class CotizacionMB extends BaseMB implements Serializable {

    private List<CotizacionBean> listaCotizacionBean;
    private CotizacionBean cotizacionBean;
    private CotizacionBean cotizacionFiltroBean;
    private Calendar fechaCotizacion;
    private String fechaCotizacionView;
    //private List<EntidadSedeBean> listaEntidadSedeBean;
    private List<EntidadBean> listaEntidadBean;
    //  private EntidadSedeBean entidadSedeBean;
    private List<CodigoBean> listaTipoFormaPagoBean;
    private List<CodigoBean> listaTipoPrioridadBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private List<DetCotizacionBean> listaDetCotizacionBean;
    private EntidadBean entidadBean;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Double sumaImporte = 0.0;
    private Double sumaTotalCoti = 0.0;
    private Double montoCadaUnoMate = 0.0;
    private Double montoGeneralOrden = 0.0;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoPrioridadBean;
    private UsuarioBean usuarioSessionBean;
    private CodigoBean codigoBean;
    private List<SolicitudLogisticoBean> listaSolicitudServiciosBean;
    private List<SolicitudLogisticoBean> listaSolicitudActivosMaBean;
    private DetCotizacionBean detCotizacionBean;
    private DetCotizacionBean detCotizacionBeanAyuda;
    private Boolean mostrarPdf = false;
    private List<ViewEntidadBean> listaViewEntidadProveedorBean;
    private Calendar fechaActual;
    private Integer idRequerimiento;
    //ayuda
    private Boolean flgActivo = false;
    private Boolean flgAlmacen = false;
    private Boolean flgServicio = false;

    private Integer idTipoPago;

    //Ayuda
    private Boolean flgTransporte = false;
    //Ayuda 
    private List<DetCotizacionBean> listaOrdenCompraActDetalleBean;
    private OrdenCompraBean ordenCompraBean;

    private List<DetCotizacionBean> listaCotizacionPorId;
    private Boolean valCotizacionAceptada;
    private List<DetCotizacionBean> listaBuscadoDetCotizacionBean;

    //getter and setter
    public CotizacionBean getCotizacionFiltroBean() {
        if (cotizacionFiltroBean == null) {
            cotizacionFiltroBean = new CotizacionBean();
        }
        return cotizacionFiltroBean;
    }

    public void setCotizacionFiltroBean(CotizacionBean cotizacionFiltroBean) {
        this.cotizacionFiltroBean = cotizacionFiltroBean;
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

    public List<DetCotizacionBean> getListaDetCotizacionBean() {
        if (listaDetCotizacionBean == null) {
            listaDetCotizacionBean = new ArrayList<>();
        }
        return listaDetCotizacionBean;
    }

    public void setListaDetCotizacionBean(List<DetCotizacionBean> listaDetCotizacionBean) {
        this.listaDetCotizacionBean = listaDetCotizacionBean;
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

    public String getFechaCotizacionView() {
        return fechaCotizacionView;
    }

    public void setFechaCotizacionView(String fechaCotizacionView) {
        this.fechaCotizacionView = fechaCotizacionView;
    }

    public Calendar getFechaCotizacion() {
        return fechaCotizacion;
    }

    public void setFechaCotizacion(Calendar fechaCotizacion) {
        this.fechaCotizacion = fechaCotizacion;
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

    public List<CotizacionBean> getListaCotizacionBean() {
        if (listaCotizacionBean == null) {
            listaCotizacionBean = new ArrayList<>();
        }
        return listaCotizacionBean;
    }

    public void setListaCotizacionBean(List<CotizacionBean> listaCotizacionBean) {
        this.listaCotizacionBean = listaCotizacionBean;
    }

    @PostConstruct
    public void cargaDatos() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetCotizacionBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerTodosCompra(solicitudLogisticoBean);

            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
//            listaCotizacionBean = cotizacionService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            EntidadService entidadService = BeanFactory.getEntidadService();
//          listaEntidadSedeBean = entidadService.obtenerProveedorPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 

//            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getEntidadBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);
            listaViewEntidadProveedorBean = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_PROVEEDOR, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoFormaPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoModoPago"));
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoBean);
//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoBean);
//            listaSolicitudActivosMaBean = solicitudLogisticoService.obtenerTodosSolicitud(solicitudLogisticoBean);
//            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosSolDes(solicitudLogisticoBean);
//            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosServicio(solicitudLogisticoBean); 
            fechaActual = new GregorianCalendar();
            getCotizacionFiltroBean().setFechaInicio(fechaActual.getTime());
            getCotizacionFiltroBean().setFechaFin(fechaActual.getTime());
            getCotizacionFiltroBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getCotizacionBean().getTipoPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoPago = bean.getIdCodigo();
                }
            }
            String idCoti = null;
            idCoti = cotizacionService.obtenerUltimoCotizacion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idCoti.equals(null)) {
                getCotizacionBean().setNroCotizacion(idCoti);
            }
            this.cargarDatosSession();
            getCotizacionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Lógica de negocio
    public void cargarDatosSession() {
        try {
            //Paso los valores de sesion al bean cotizacionBean
            this.fechaCotizacion = new GregorianCalendar();
            getCotizacionBean().setAnio(fechaCotizacion.get(Calendar.YEAR));
            getCotizacionBean().setFechaCotizacion(fechaCotizacion.getTime());
            getCotizacionBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCotizacionesSolicitud() {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudActivosMaBean = solicitudLogisticoService.obtenerTodosSolicitud(solicitudLogisticoBean);
            listaSolicitudLogDetalleBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiar() {
        try {
            cotizacionBean = new CotizacionBean();
            listaDetCotizacionBean = new ArrayList<>();
            fechaCotizacionView = "";
            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            listaDetCotizacionBean = new ArrayList<>();
            this.cargarDatosSession();
            getCotizacionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarProveedor() {
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            cotizacionBean = new CotizacionBean();
            fechaCotizacionView = "";
            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getCotizacionBean().getTipoPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoPago = bean.getIdCodigo();
                }
            }
            String idCoti = null;
            idCoti = cotizacionService.obtenerUltimoCotizacion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idCoti.equals(null)) {
                getCotizacionBean().setNroCotizacion(idCoti);
            }
            this.cargarDatosSession();
            getCotizacionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCoti() {
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            cotizacionBean = new CotizacionBean();
            fechaCotizacionView = "";
            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getCotizacionBean().getTipoPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoPago = bean.getIdCodigo();
                }
            }
            String idCoti = null;
            idCoti = cotizacionService.obtenerUltimoCotizacion(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idCoti.equals(null)) {
                getCotizacionBean().setNroCotizacion(idCoti);
            }

            this.cargarDatosSession();
            getCotizacionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCotizacionPorId = new ArrayList<>();
            listaDetCotizacionBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {

            montoCadaUnoMate = 0.0;
            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            EntidadService entidadService = BeanFactory.getEntidadService();
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();

            cotizacionBean = (CotizacionBean) event.getObject();
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorId(cotizacionBean.getEntidadBean());
            cotizacionBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la orden de compra
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrden(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = cotizacionBean.getMontoRef();
            sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
            montoCadaUnoMate = cotizacionBean.getMontoCadaUnoMate();

            fechaCotizacionView = formato.format(cotizacionBean.getFechaCotizacion());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void obtenerPorId(CotizacionBean bean) {
//        try {
//            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
//            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
//            EntidadService entidadService = BeanFactory.getEntidadService();
//
//            cotizacionBean = new CotizacionBean();
//            sumaImporte = 0.0;
//            montoCadaUnoMate = 0.0;
//            
//            cotizacionBean = cotizacionService.obtenerPorId(bean);
//            //obtengo el proveedor correspondiente
//            entidadBean = entidadService.obtenerEntidadPorId(cotizacionBean.getEntidadBean());
//            cotizacionBean.setEntidadBean(entidadBean);
//            //obtengo el detalle de la orden de compra
//            listaDetCotizacionBean = detCotizacionService.obtenerPorOrden(cotizacionBean.getIdCotizacion());
//            sumaImporte = cotizacionBean.getMontoRef(); 
//
//            //fechaCotizacionView = formato.format(cotizacionBean.getFechaCotizacion()); 
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
    public void obtenerPorId(DetCotizacionBean bean) {
        try {
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            detCotizacionBean = bean;
            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            montoCadaUnoMate = 0.0;
            getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detCotizacionService.obtenerRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDetCotizacionBean().setCotizacionBean(bean.getCotizacionBean());
            detCotizacionBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cotizacionBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cotizacionBean = cotizacionService.obtenerPorId(detCotizacionBean.getCotizacionBean());

            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(cotizacionBean.getEntidadBean().getRuc());
            entidad.setNombre(cotizacionBean.getEntidadBean().getNombre());
            entidad.setDireccion(cotizacionBean.getEntidadBean().getDireccion());
            cotizacionBean.setEntidadBean(entidad);

            //obtengo el detalle de la orden de compra 
            detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrden(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDetCotizacionBean().getSolicitudLogisticoBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
            listaCotizacionPorId = detCotizacionService.obtenerListaPorIdSolicitud2(detCotizacionBean);
            sumaImporte = cotizacionBean.getMontoRef();
            sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
            montoCadaUnoMate = cotizacionBean.getMontoCadaUnoMate();

            idRequerimiento = detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento();

            if (listaDetCotizacionBean.get(0).getCatalogoBean().getItem().equals(MaristaConstantes.servicioTransporte)) {
                switch (detCotizacionBean.getCatalogoBean().getItem()) {
                    case "Servicio Transporte":
                        this.flgTransporte = true;
                        break;
                    default:
                        this.flgTransporte = false;

                }
                System.out.println("flg" + flgTransporte);
            } else {
                this.flgTransporte = false;
                System.out.println("flg2" + flgTransporte);
            }
            //fechaCotizacionView = formato.format(cotizacionBean.getFechaCotizacion()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCotizacion(DetCotizacionBean bean) {
        try {
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            montoCadaUnoMate = 0.0;
            getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detCotizacionService.obtenerRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDetCotizacionBean().setCotizacionBean(bean.getCotizacionBean());
            cotizacionBean = cotizacionService.obtenerPorId(detCotizacionBean.getCotizacionBean());

            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(cotizacionBean.getEntidadBean().getRuc());
            entidad.setNombre(cotizacionBean.getEntidadBean().getNombre());
            entidad.setDireccion(cotizacionBean.getEntidadBean().getDireccion());
            cotizacionBean.setEntidadBean(entidad);

            //obtengo el detalle de la orden de compra 
            detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
            listaCotizacionPorId = detCotizacionService.obtenerListaPorIdSolicitud2(detCotizacionBean);
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrdenSoli(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            sumaImporte = cotizacionBean.getMontoRef();
            sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
            montoCadaUnoMate = cotizacionBean.getMontoCadaUnoMate();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCotizacionId(DetCotizacionBean bean) {
        try {
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            EntidadService entidadService = BeanFactory.getEntidadService();

            sumaImporte = 0.0;
            sumaTotalCoti = 0.0;
            montoCadaUnoMate = 0.0;
            getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detCotizacionService.obtenerRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDetCotizacionBean().setCotizacionBean(bean.getCotizacionBean());
            cotizacionBean = cotizacionService.obtenerPorId(detCotizacionBean.getCotizacionBean());

            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(cotizacionBean.getEntidadBean().getRuc());
            entidad.setNombre(cotizacionBean.getEntidadBean().getNombre());
            entidad.setDireccion(cotizacionBean.getEntidadBean().getDireccion());
            cotizacionBean.setEntidadBean(entidad);

            //obtengo el detalle de la orden de compra 
            detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrdenSoli(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            sumaImporte = cotizacionBean.getMontoRef();
            sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
            montoCadaUnoMate = cotizacionBean.getMontoCadaUnoMate();

            imprimirTodosPdf();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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
            listaBuscadoDetCotizacionBean = new ArrayList<>();
//            cotizacionFiltroBean = new CotizacionBean();
            detCotizacionBean = new DetCotizacionBean();
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            cotizacionFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        Integer estado = 0;
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
            if (detCotizacionBean.getSolicitudLogisticoBean().getNroSolicitud() != null && detCotizacionBean.getSolicitudLogisticoBean().getNroSolicitud() != null) {
                detCotizacionBean.getSolicitudLogisticoBean().setNroSolicitud(detCotizacionBean.getSolicitudLogisticoBean().getNroSolicitud());
                estado = 1;
            }
            if (detCotizacionBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(detCotizacionBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detCotizacionBean.setFechaInicio(t);
                estado = 1;
            }
            if (detCotizacionBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(detCotizacionBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detCotizacionBean.setFechaFin(u);
                estado = 1;

            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaBuscadoDetCotizacionBean = new ArrayList<>();
            }

            if (estado == 1) {
                getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaBuscadoDetCotizacionBean = detCotizacionService.obtenerTodosM(detCotizacionBean);
                if (listaBuscadoDetCotizacionBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }

//            detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
//            getDetCotizacionBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id= detCotizacionService.obtenerRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            getDetCotizacionBean().getSolicitudLogisticoBean().setIdRequerimiento(id);
//            listaCotizacionPorId = detCotizacionService.obtenerTodosM(detCotizacionBean);
//            if (listaCotizacionBean.isEmpty()) {
//                listaCotizacionBean = new ArrayList<>();
//                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCatalogo() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(cotizacionBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
            switch (tipoCategoriaBean.getCodigo()) {
                case "Material":
                    this.flgActivo = false;
                    this.flgAlmacen = true;
                    this.flgServicio = false;
                    break;
                case "Activo Fijo":
                    this.flgActivo = true;
                    this.flgAlmacen = false;
                    this.flgServicio = false;
                    break;
                case "Servicio":
                    this.flgActivo = false;
                    this.flgAlmacen = false;
                    this.flgServicio = true;
                    break;
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
            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();

//            id = detCotizacionService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cotizacionBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detCotizacionService.obtenerUltimoCoti(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaDetCotizacionBean = detCotizacionService.obtenerPorOrdenSoli(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCotizaciones.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetCotizacionRepBean> listaDetalleCotizacionBean = new ArrayList<>();
            listaDetalleCotizacionBean= detCotizacionService.obtenerPorOrdenPrimero(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetalleCotizacionBean); 
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
//    public void imprimirTodosPdfGeneral() {
//        ServletOutputStream out = null;
//        Integer id = 0;
//        try {
//            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();
//
////            id = detCotizacionService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
////            id = detCotizacionService.obtenerUltimo(cotizacionBean.getIdCotizacion());
//            listaDetCotizacionBean = detCotizacionService.obtenerTodos(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompra.jasper");
//            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//            String absoluteWebPath = externalContext.getRealPath("/");
//            File file = new File(archivoJasper);
//            List<DetOrdenCompraGeneral> listaDetCotizacionBean = new ArrayList<>();
//            for (int i = 0; i < listaDetCotizacionBean.size(); i++) {
//                DetOrdenCompraGeneral detOrdenCompraGeneral = new DetOrdenCompraGeneral();
//                detOrdenCompraGeneral.setIdOrdenCompra(listaDetCotizacionBean.get(i).getCotizacionBean().getIdCotizacion());
//                detOrdenCompraGeneral.setNombreUniNeg(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
//                detOrdenCompraGeneral.setNombreOrden(listaDetCotizacionBean.get(i).getCotizacionBean().getEntidadBean().getNombre());
//                detOrdenCompraGeneral.setRucOrden(listaDetCotizacionBean.get(i).getCotizacionBean().getEntidadBean().getRuc());
//                detOrdenCompraGeneral.setDireccionOrden(listaDetCotizacionBean.get(i).getCotizacionBean().getEntidadBean().getDireccion());
//                detOrdenCompraGeneral.setFechaCotizacion(listaDetCotizacionBean.get(i).getCotizacionBean().getFechaCotizacion());
//                detOrdenCompraGeneral.setCategoria(listaDetCotizacionBean.get(i).getCotizacionBean().getTipoCategoriaBean().getCodigo());
//                detOrdenCompraGeneral.setTipoMoneda(listaDetCotizacionBean.get(i).getTipoMonedaBean().getCodigo());
//                detOrdenCompraGeneral.setUniNeg(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getUniNeg());
//                detOrdenCompraGeneral.setItem(listaDetCotizacionBean.get(i).getCatalogoBean().getItem());
//                detOrdenCompraGeneral.setCantidad(listaDetCotizacionBean.get(i).getCantidad());
//                detOrdenCompraGeneral.setImporte(listaDetCotizacionBean.get(i).getImporte());
//                detOrdenCompraGeneral.setMontoCadaUnoMate(listaDetCotizacionBean.get(i).getCotizacionBean().getMontoCadaUnoMate());
//                detOrdenCompraGeneral.setSumaImporteFinal(listaDetCotizacionBean.get(i).getCotizacionBean().getMontoRef());
//                detOrdenCompraGeneral.setDireccionUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
//                detOrdenCompraGeneral.setFormaPago(listaDetCotizacionBean.get(i).getCotizacionBean().getTipoFormaPagoBean().getCodigo());
//                detOrdenCompraGeneral.setNombreUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getNombre());
//                detOrdenCompraGeneral.setRucUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getRuc());
//                detOrdenCompraGeneral.setDistritoUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
//                detOrdenCompraGeneral.setPaisUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
//                detOrdenCompraGeneral.setTelefonoUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
//                detOrdenCompraGeneral.setCorreoUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
//                detOrdenCompraGeneral.setWebUnidad(listaDetCotizacionBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
//                detOrdenCompraGeneral.setMontoGeneralOrden(listaDetCotizacionBean.get(i).getCotizacionBean().getMontoGeneralOrden());
////                }
//                listaDetCotizacionBean.add(detOrdenCompraGeneral);
//
//            }
//            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
//            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetCotizacionBean);
//            Map<String, Object> parametros = new HashMap<>();
//            String ruta = absoluteWebPath + "reportes\\jasper\\";
//            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("SUBREPORT_DIR", ruta);
//            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            parametros.put("USUARIO", ub.getUsuario());
//
//            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            out = response.getOutputStream();
//            out.write(bytes);
//            out.flush();
//
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (Exception ettt) {
//                new MensajePrime().addErrorGeneralMessage();
//                GLTLog.writeError(this.getClass(), ettt);
//            }
//        }
//        // Inform JSF that it doesn't need to handle response.
//        // This is very important, otherwise you will get the following exception in the logs:
//        // java.lang.IllegalStateException: Cannot forward after response has been committed.
//        FacesContext.getCurrentInstance().responseComplete();
//    }

    public String grabar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
                CotizacionService cotizacionService = BeanFactory.getCotizacionService();
                DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();

                if (cotizacionBean.getIdCotizacion() == null) {
                    cotizacionBean.setCreaPor(usuarioSessionBean.getUsuario());
                    cotizacionBean.setNroCotizacion(solicitudLogisticoBean.getNroSolicitud());
                    detCotizacionBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
                    detCotizacionBean.setCatalogoBean(listaSolicitudLogDetalleBean.get(0).getCatalogoBean());
                    detCotizacionBean.getCatalogoBean().setIdCatalogo(listaSolicitudLogDetalleBean.get(0).getCatalogoBean().getIdCatalogo());
                    getCotizacionBean().setIdRequerimiento(solicitudLogisticoBean.getIdRequerimiento());
                    cotizacionService.insertar(cotizacionBean);
                    detCotizacionService.insertar(cotizacionBean.getIdCotizacion(), cotizacionBean.getAnio(), listaDetCotizacionBean, listaSolicitudLogDetalleBean, detCotizacionBean, cotizacionBean);
                    System.out.println("ok");
                } else {
//                    cotizacionBean.setMontoRef(sumaImporte); 
                    cotizacionBean.setCreaPor(usuarioSessionBean.getUsuario());
                    cotizacionService.modificar(cotizacionBean);
                    detCotizacionService.eliminar(cotizacionBean);
                    detCotizacionService.insertar(cotizacionBean.getIdCotizacion(), cotizacionBean.getAnio(), listaDetCotizacionBean, listaSolicitudLogDetalleBean, detCotizacionBean, cotizacionBean);

                }
                for (CodigoBean bean : listaTipoFormaPagoBean) {
                    if (bean.getCodigo().equals("Cheque")) {
                        getCotizacionBean().getTipoPagoBean().setIdCodigo(bean.getIdCodigo());
                        this.idTipoPago = bean.getIdCodigo();
                    }
                }
//                cotizacionFiltroBean.setObs(cotizacionBean.getObs());
                listaCotizacionBean = cotizacionService.obtenerTodosM(cotizacionFiltroBean);
                System.out.println("size: " + listaCotizacionBean.size());
                if (detCotizacionBean.getIdDetCotizacion() != null) {
                    getCotizacionBean().setIdRequerimiento(detCotizacionBean.getSolicitudLogisticoBean().getIdRequerimiento());
                }
                listaCotizacionPorId = detCotizacionService.obtenerListaPorIdSolicitud2(detCotizacionBean);
                listaBuscadoDetCotizacionBean = detCotizacionService.obtenerTodosM(detCotizacionBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaDetCotizacionBean = detCotizacionService.obtenerPorOrdenSoli(cotizacionBean.getIdCotizacion(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void cambiarValCotizacionAprobada(Integer id) {
        try {

            for (DetCotizacionBean detCotizacion : listaCotizacionPorId) {
                if (id.equals(detCotizacion.getIdCotizacion())) {
                    detCotizacion.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    detCotizacion.getCotizacionBean().setFlgAceptado(true);
                } else {
                    detCotizacion.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    detCotizacion.getCotizacionBean().setFlgAceptado(false);
                }
                CotizacionService cotizacionService = BeanFactory.getCotizacionService();
                cotizacionService.changeStatusCoti(listaCotizacionPorId);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String anadirProveedor() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CotizacionService cotizacionService = BeanFactory.getCotizacionService();
                DetCotizacionService detCotizacionService = BeanFactory.getDetCotizacionService();

                if (cotizacionBean.getIdCotizacion() == null) {
                    cotizacionBean.getEntidadBean().setRuc(pagina);
                    cotizacionService.insertar(cotizacionBean);
                    detCotizacionService.insertar(cotizacionBean.getIdCotizacion(), cotizacionBean.getAnio(), listaDetCotizacionBean, listaSolicitudLogDetalleBean, detCotizacionBean, cotizacionBean);
                    System.out.println("ok");
                } else {
//                    cotizacionBean.setMontoRef(sumaImporte); 
                    cotizacionBean.setCreaPor(usuarioSessionBean.getUsuario());
                    cotizacionService.modificar(cotizacionBean);
                    detCotizacionService.eliminar(cotizacionBean);
                    detCotizacionService.insertar(cotizacionBean.getIdCotizacion(), cotizacionBean.getAnio(), listaDetCotizacionBean, listaSolicitudLogDetalleBean, detCotizacionBean, cotizacionBean);

                }
                listaCotizacionBean = cotizacionService.obtenerTodosM(cotizacionFiltroBean);
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
            for (DetCotizacionBean detalle : listaDetCotizacionBean) {
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

    public void calcularImportePorItem(DetCotizacionBean bean) {
        try {
            cotizacionBean.getTipoCategoriaBean().setCodigo(solicitudLogisticoBean.getTipoCategoriaBean().getCodigo());
            if (cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (bean.getImporteAnterior());
//                sumaImporte = sumaImporte + (bean.getCantidad() * bean.getImporte());
                sumaImporte = sumaImporte + (bean.getImporte());

            } else//Material o Bien
            {
//                sumaImporte = (bean.getCantidad() * bean.getImporte());
//                sumaImporte = sumaImporte + (bean.getC.antidadAnterior() * bean.getImporteAnterior());
                sumaImporte = sumaImporte + (bean.getCantidad() * bean.getImporte());
                sumaImporte = sumaImporte - (bean.getCantidadAnterior() * bean.getImporteAnterior());
            } 
            bean.setCantidadAnterior(bean.getCantidad());
            bean.setImporteAnterior(bean.getImporte());
            calgularTotalCotIgv();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calgularTotalCotIgv() {
        try {

            sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularImportePorUno(DetCotizacionBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")) {

                montoCadaUnoMate = (bean.getCantidad() * bean.getImporte());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularPorCadaUno(DetCotizacionBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                montoCadaUnoMate = (bean.getCantidad() * bean.getImporte());
            } else//Material o Bien
            {
                montoCadaUnoMate = (bean.getCantidad() * bean.getImporte());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItem(SolicitudLogDetalleBean bean) {
        Integer cantProp = 0;
        Double cantUno = 0.0;
        try {

            DetCotizacionBean detCotizacionBean = new DetCotizacionBean();
            cotizacionBean.setNroCotizacion(bean.getSolicitudLogisticoBean().getNroSolicitud());
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                //Aqui valido por el estado:Solo se agrega los items con estos estados
                if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                {

                    cantProp = (bean.getCantidadSolicitada() - bean.getCantidadEntregada());
                    cantUno = (detCotizacionBean.getCantidad() * detCotizacionBean.getImporte());

                    //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                    if (!validarItemPorAgregar(bean)) {
                        //detCotizacionBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        detCotizacionBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        detCotizacionBean.setCantidadAnterior(cantProp);
                        detCotizacionBean.setCantidad(cantProp);
                        detCotizacionBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        detCotizacionBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        detCotizacionBean.setCatalogoBean(bean.getCatalogoBean());
                        detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        detCotizacionBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());

                        listaDetCotizacionBean.add(detCotizacionBean);
                        sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                    } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                    {
                        for (DetCotizacionBean detalle : listaDetCotizacionBean) {
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
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else// quiere decir que la solicitud es de tipo servicio
            {
                if (!validarItemPorAgregar(bean)) {
                    //detCotizacionBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    detCotizacionBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                    detCotizacionBean.setCantidad(bean.getCantidadSolicitada());
                    detCotizacionBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                    detCotizacionBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                    detCotizacionBean.setCatalogoBean(bean.getCatalogoBean());
                    detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                    detCotizacionBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    listaDetCotizacionBean.add(detCotizacionBean);
                    sumaImporte = sumaImporte + (bean.getCatalogoBean().getPrecioRef());

                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }

            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaDetCotizacionBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItems() {
        Integer cantProp = 0;
        Double cantUno = 0.0;
        try {
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                DetCotizacionBean detCotizacionBean = new DetCotizacionBean();
                cotizacionBean.setNroCotizacion(solicitudLogisticoBean.getNroSolicitud());
                if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                        || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                    //Aqui valido por el estado:Solo se agrega los items con estos estados
                    if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                    {
                        //Aqui valido por la cantidad a pedir

                        cantProp = (bean.getCantidadSolicitada() - bean.getCantidadEntregada());
                        cantUno = (detCotizacionBean.getCantidad() * detCotizacionBean.getImporte());
                        //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                        if (!validarItemPorAgregar(bean)) {
                            //detCotizacionBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                            detCotizacionBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                            detCotizacionBean.setCantidadAnterior(cantProp);
                            detCotizacionBean.setCantidad(cantProp);
                            detCotizacionBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                            detCotizacionBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                            detCotizacionBean.setCatalogoBean(bean.getCatalogoBean());
                            detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                            detCotizacionBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                            listaDetCotizacionBean.add(detCotizacionBean);
                            sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                        } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                        {
                            for (DetCotizacionBean detalle : listaDetCotizacionBean) {
                                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                                    //Si es el mismo item(idCatalogo) pero de distinta solicitud, se suma las cantidades
                                    if (!Objects.equals(detalle.getSolicitudLogisticoBean().getIdRequerimiento(), bean.getSolicitudLogisticoBean().getIdRequerimiento())) {
                                        detalle.setCantidad(detalle.getCantidad() + cantProp);
                                        sumaImporte = sumaImporte + (cantProp * bean.getCatalogoBean().getPrecioRef());
                                    }
                                }
                            }
                        }
//                        }
                    }
                    sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                    sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else// quiere decir que la solicitud es de tipo servicio
                {
                    if (!validarItemPorAgregar(bean)) {
                        //detCotizacionBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        detCotizacionBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        detCotizacionBean.setCantidad(bean.getCantidadSolicitada());
                        detCotizacionBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        detCotizacionBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        detCotizacionBean.setCatalogoBean(bean.getCatalogoBean());
                        detCotizacionBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        detCotizacionBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        detCotizacionBean.getSolicitudLogDetalleBean().setFechaSalida(bean.getFechaSalida());
                        detCotizacionBean.getSolicitudLogDetalleBean().setDestinoServicio(bean.getDestinoServicio());
                        detCotizacionBean.getSolicitudLogDetalleBean().setHoraSalida(bean.getHoraSalida());
                        detCotizacionBean.getSolicitudLogDetalleBean().setHoraRegreso(bean.getHoraRegreso());
                        listaDetCotizacionBean.add(detCotizacionBean);
                        sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                        cargarCatalogo();
                    }
                }
                if (detCotizacionBean.getCatalogoBean().getItem().equals(MaristaConstantes.servicioTransporte)) {
                    switch (detCotizacionBean.getCatalogoBean().getItem()) {
                        case "Servicio Transporte":
                            this.flgTransporte = true;
                            break;
                        default:
                            this.flgTransporte = false;

                    }
                    System.out.println("flg" + flgTransporte);
                } else {
                    this.flgTransporte = false;
                    System.out.println("flg2" + flgTransporte);
                }
                sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                sumaTotalCoti = (double) Math.round((sumaImporte + cotizacionBean.getIgvCoti()) * 100) / 100;
                cotizacionBean.setNroCotizacion(bean.getSolicitudLogisticoBean().getNroSolicitud());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaDetCotizacionBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
            }

            calgularTotalCotIgv();
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

            cotizacionBean.setTipoCategoriaBean(tipoCategoriaBean);//Paso el tipo de orden

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarItem(DetCotizacionBean bean) {
        try {

            sumaImporte = sumaImporte - (bean.getCantidad() * bean.getImporte());

            listaDetCotizacionBean.remove(bean);

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
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();

            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(bean.getIdRequerimiento(), bean.getUnidadNegocioBean().getUniNeg());
            //obtengo el detalle de la solicitud

            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

            cotizacionBean.getTipoCategoriaBean().setIdCodigo(bean.getTipoCategoriaBean().getIdCodigo());
            cotizacionBean.getTipoPrioridadBean().setIdCodigo(bean.getTipoPrioridadBean().getIdCodigo());
            cotizacionBean.setObs(bean.getTitulo());
//            cotizacionBean.setNroCotizacion(bean.getNroSolicitud());
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
    //    cotizacionBean.setEntidadSedeBean(entidadSedeBean);
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
            cotizacionBean.setEntidadBean(entidadBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarEntidades() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();

            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
//            if (cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Servicio")
//                    || cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Material")
//                    || cotizacionBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {

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

    public DetCotizacionBean getDetCotizacionBean() {
        if (detCotizacionBean == null) {
            detCotizacionBean = new DetCotizacionBean();
        }
        return detCotizacionBean;
    }

    public void setDetCotizacionBean(DetCotizacionBean detCotizacionBean) {
        this.detCotizacionBean = detCotizacionBean;
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
        return idTipoPago;
    }

    public void setIdTipoFormaPago(Integer idTipoPago) {
        this.idTipoPago = idTipoPago;
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

    public List<DetCotizacionBean> getListaCotizacionPorId() {
        if (listaCotizacionPorId == null) {
            listaCotizacionPorId = new ArrayList<>();
        }
        return listaCotizacionPorId;
    }

    public void setListaCotizacionPorId(List<DetCotizacionBean> listaCotizacionPorId) {
        this.listaCotizacionPorId = listaCotizacionPorId;
    }

    public Boolean getValCotizacionAceptada() {
        return valCotizacionAceptada;
    }

    public void setValCotizacionAceptada(Boolean valCotizacionAceptada) {
        this.valCotizacionAceptada = valCotizacionAceptada;
    }

    public Integer getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(Integer idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public DetCotizacionBean getDetCotizacionBeanAyuda() {
        if (detCotizacionBeanAyuda == null) {
            detCotizacionBeanAyuda = new DetCotizacionBean();
        }
        return detCotizacionBeanAyuda;
    }

    public void setDetCotizacionBeanAyuda(DetCotizacionBean detCotizacionBeanAyuda) {
        this.detCotizacionBeanAyuda = detCotizacionBeanAyuda;
    }

    public Boolean getFlgActivo() {
        return flgActivo;
    }

    public void setFlgActivo(Boolean flgActivo) {
        this.flgActivo = flgActivo;
    }

    public Boolean getFlgAlmacen() {
        return flgAlmacen;
    }

    public void setFlgAlmacen(Boolean flgAlmacen) {
        this.flgAlmacen = flgAlmacen;
    }

    public Boolean getFlgServicio() {
        return flgServicio;
    }

    public void setFlgServicio(Boolean flgServicio) {
        this.flgServicio = flgServicio;
    }

    public List<DetCotizacionBean> getListaBuscadoDetCotizacionBean() {
        if (listaBuscadoDetCotizacionBean == null) {
            listaBuscadoDetCotizacionBean = new ArrayList<>();
        }
        return listaBuscadoDetCotizacionBean;
    }

    public void setListaBuscadoDetCotizacionBean(List<DetCotizacionBean> listaBuscadoDetCotizacionBean) {
        this.listaBuscadoDetCotizacionBean = listaBuscadoDetCotizacionBean;
    }

    public Boolean getFlgTransporte() {
        return flgTransporte;
    }

    public void setFlgTransporte(Boolean flgTransporte) {
        this.flgTransporte = flgTransporte;
    }

    public Double getSumaTotalCoti() {
        return sumaTotalCoti;
    }

    public void setSumaTotalCoti(Double sumaTotalCoti) {
        this.sumaTotalCoti = sumaTotalCoti;
    }

}
