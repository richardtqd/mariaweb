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
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.component.datatable.DataTable;
import pe.marista.sigma.MaristaConstantes;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentaFacturaBean;
import pe.marista.sigma.bean.DetOrdenCompraBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.OrdenCompraService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.service.RegistroCompraService;
import pe.marista.sigma.service.DetRegistroCompraService;
import pe.marista.sigma.bean.DetRegistroCompraBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.reporte.DetRegistroCompraGeneral;
import pe.marista.sigma.bean.reporte.DetRegistroCompraRepBean;
import pe.marista.sigma.service.DetraccionService;
import pe.marista.sigma.service.OrdenCompraDetalleService;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.reporte.RegistroCompraRepBean;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTCalculadoraCR;

public class RegistroCompraMB extends BaseMB implements Serializable {

    private List<OrdenCompraBean> listaOrdenCompraBean;
    private List<RegistroCompraBean> listaRegistroCompraBean;
    private OrdenCompraBean ordenCompraBean;
    private OrdenCompraBean ordenCompraFiltroBean;
    private RegistroCompraBean registroCompraFiltoBean;
    private Calendar fechaOrden;
    private String fechaOrdenView;
    //private List<EntidadSedeBean> listaEntidadSedeBean;
    private List<EntidadBean> listaEntidadBean;
    //  private EntidadSedeBean entidadSedeBean;
    private List<CodigoBean> listaTipoDocBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoNumeroFactura;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<CodigoBean> listaTipoStatusRegCBean;
    // private List<CodigoBean> listaTipoSolicitudBean;
    private List<TipoSolicitudBean> listaTipoSolicitudBean;
    private List<CodigoBean> listaTipoPrioridadBean;
//    private List<CodigoBean> listaTipoCategoriaBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private List<OrdenCompraDetalleBean> listaOrdenCompraDetalleBean;
    private List<DetRegistroCompraBean> listaDetalleRegistroCompraBean;
    private EntidadBean entidadBean;
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//    private Integer igv = 18;
    private Double sumaImporte = 0.00;
    private Double sumaFact = 0.00;
    private Double montoGeneralRegistro = 0.00;
    private Double sumaTotal = 0.00;
    private CodigoBean tipoDocBean;
    private CodigoBean tipoMonedaBean;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoSolicitudBean;
    private CodigoBean tipoStatusRegCBean;
    private CodigoBean tipoPrioridadBean;
    private UsuarioBean usuarioSessionBean;
    private RegistroCompraBean registroCompraBean;
    private DetRegistroCompraBean detRegistroCompraBean;
    private Integer idTipoStatusRegC;
    private List<RegistroCompraBean> listaRegistroCompraAutorizado;
    private Calendar fechaActual;
    private List<DetraccionBean> listaDetraccionBean;
    private List<CodigoBean> listTipoPrioridadFacturaBean;

    private FacturaCompraBean facturaCompraBean;
    private FacturaCompraBean facturaCompraFiltroBean;
    private List<FacturaCompraBean> listaFacturaCompraBean;
    private List<DetRequerimientoCRBean> listaRequerimientoCRBean;
    private List<FacturaCompraBean> listaFacturaCompraDocEgresoBean;
    private List<FacturaCompraBean> listaSesionFacturaCompraBean;
    private PlanContableBean planContableBean;
    //ayuda
    private String montoTotal;
    private Double montoDetraccion = 0.00;
    private Double subTotal = 0.00;
    //ayuda
    private Boolean flgUnica = false;
    private Boolean flgMultiple = false;
    private Boolean flgAgregar = false;
    private Boolean mostrarDetraccion = false;
    private Double igvResultado = 0.00;

    private Integer idTipoDistribucion = 0;
    //Documentos 
//    private Boolean flgRecibo = false;
//    private Boolean flgFactura = false;
//    private Boolean flgBoleta = false;
    private Integer idTipoDoc;
    private OrdenCompraDetalleBean ordenCompraDetalleBean;
    private int valor = 1;

    //CR REGISTRO COMPRA
    private DetRequerimientoCRBean detRequerimientoCRBean;
    private DetRegistroCompraCRBean detRegistroCompraCRBean;
    private List<DetRegistroCompraCRBean> listaDetRegistroCompraCRBean;
    private List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean;

//    private List<DocEgresoBean> listaDocEgresoBean;
    //CR Multi
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;
    private List<CodigoBean> listaTipoDistribucionCRBean;

    private List<DetRequerimientoCRBean> listaDetRequerimientoCRTopBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;

    private List<CodigoBean> listaTipoPagoBean;

    //Cuenta
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private TipoConceptoBean tipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<ConceptoBean> listaConceptoBean;
    private Integer idTipoEstado;
    private String uniNeg;
    private CuentaFacturaBean cuentaFacturaBean;
    private List<CuentaFacturaBean> listaCuentaFacturaBean;
    private List<CuentaFacturaBean> listaCuentasBean;
    private Integer nroFactAyuda = 0;
    private Double montoTotalFact = new Double("0.0");
    private Boolean flgDetalleActivo = false;
    private Boolean flgDoc = false;

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

    public List<CodigoBean> getListaTipoPrioridadBean() {
        return listaTipoPrioridadBean;
    }

    public void setListaTipoPrioridadBean(List<CodigoBean> listaTipoPrioridadBean) {
        this.listaTipoPrioridadBean = listaTipoPrioridadBean;
    }
//
//    public List<CodigoBean> getListaTipoOrdenBean() {
//        return listaTipoOrdenBean;
//    }
//
//    public void setListaTipoOrdenBean(List<CodigoBean> listaTipoOrdenBean) {
//        this.listaTipoOrdenBean = listaTipoOrdenBean;
//    }

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
            listaOrdenCompraDetalleBean = new ArrayList<>();
            listaDetalleRegistroCompraBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerTodosCompra(solicitudLogisticoBean);

            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            // listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaRegistroCompraBean = registroCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            EntidadService entidadService = BeanFactory.getEntidadService();
//          listaEntidadSedeBean = entidadService.obtenerProveedorPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 
            //listaEntidadBean = entidadService.obtenerProveedorPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDoc"));
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
//            listaFacturaCompraBean = registroCompraService.obtenerTodosPorUniNegFact(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaRequerimientoCRBean = solicitudLogisticoService.ObtenerPorIdCRDis(solicitudLogisticoBean);
            listaTipoNumeroFactura = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoNumeroFactura"));
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoSolicitudBean = tipoSolicitudService.obtenerPorAmbitoPorUniNeg((new TipoSolicitudBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean(), MaristaConstantes.COD_REQ_LOG)));
//            listaTipoOrdenBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoOrdenCompra"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
            listaTipoStatusRegCBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoStatusRegC"));
            fechaActual = new GregorianCalendar();
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));

            getRegistroCompraFiltoBean().setFechaInicio(fechaActual.getTime());
            getRegistroCompraFiltoBean().setFechaFin(fechaActual.getTime());
            getRegistroCompraFiltoBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            for (CodigoBean bean : listaTipoStatusRegCBean) {
                if (bean.getCodigo().equals("Pendiente")) {
                    getRegistroCompraBean().getTipoStatusRegCBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoStatusRegC = bean.getIdCodigo();
                }
            }
//            for (CodigoBean bean : listaTipoDocBean) {
//                if (bean.getCodigo().equals("Factura")) {
//                    getRegistroCompraBean().getTipoDocBean().setIdCodigo(bean.getIdCodigo());
//                    this.idTipoDoc = bean.getIdCodigo();
//                }
//               elegirDoc();
//            }
            //filtros
            fechaActual = new GregorianCalendar();
            getFacturaCompraFiltroBean().setFechaInicio(fechaActual.getTime());
            getFacturaCompraFiltroBean().setFechaFin(fechaActual.getTime());
            getFacturaCompraFiltroBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

            //filtro OC
            getOrdenCompraFiltroBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            this.cargarDatosSession();

            //Trayendo el nroRequerimiento
            String idNroRegistro = null;
            idNroRegistro = registroCompraService.obtenerUltimoRegistro(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idNroRegistro.equals(null)) {
                getRegistroCompraBean().setNroRegistro(idNroRegistro);
            }

            //Cuentas
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            getListaConceptoUniNegBean();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorEgr(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();

            getCuentaFacturaBean();
            System.out.println("fin PostConstruct");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerporSerie() {
//        try {
//            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
//            listaFacturaCompraBean = registroCompraService.obtenerSerie(registroCompraBean.getFacturaCompraBean().getSerieDoc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void cargarOrdenesParaRegiistro() {
        try {
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            listaOrdenCompraBean = ordenCompraService.obtenerPorFiltro(ordenCompraBean);
            listaSolicitudLogDetalleBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosCr() {
        try {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            listaCentroResponsabilidadBean = new ArrayList<>();

            if (registroCompraBean.getIdRegistroCompra() == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                getListaCentroResponsabilidadBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
                listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                    listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
                    System.out.println(lista.getCentroResponsabilidadBean().getTipoNivelCR());
                }
                if (!listaDetRequerimientoCRBean.isEmpty()) {
                    idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                }

                getListaCentroResponsabilidadBeanB();
                listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInRegistro(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
            } else {
                CodigoService codigoService = BeanFactory.getCodigoService();
                getListaCentroResponsabilidadBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
                listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                    listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
                    System.out.println(lista.getCentroResponsabilidadBean().getTipoNivelCR());
                }
                if (!listaDetRequerimientoCRBean.isEmpty()) {
                    idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                }

                getListaCentroResponsabilidadBeanB();
                listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
            }
//            if (!listaCuentaFacturaBean.isEmpty()) {
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            cuentaFacturaBean.getUnidadNegocioBean().setUniNeg(registroCompraBean.getUnidadNegocioBean().getUniNeg());
            cuentaFacturaBean.getFacturaCompraBean().getRegistroCompraBean().setIdRegistroCompra(registroCompraBean.getIdRegistroCompra());
            listaCuentaFacturaBean = registroCompraService.obtenerCuentaFact(cuentaFacturaBean);
            facturaCompraBean.getRegistroCompraBean().setIdRegistroCompra(registroCompraBean.getIdRegistroCompra());
            listaSesionFacturaCompraBean = registroCompraService.obtenerPorIdFact(facturaCompraBean);
//            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosCrAprobacion() {
        try {
            System.out.println("public void cargarDatosCrAprobacion()");
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            listaCentroResponsabilidadBean = new ArrayList<>();
            if (registroCompraBean.getIdRegistroCompra() == null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                getListaCentroResponsabilidadBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
                listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                    listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
                    System.out.println(lista.getCentroResponsabilidadBean().getTipoNivelCR());
                }
                if (!listaDetRequerimientoCRBean.isEmpty()) {
                    idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                }

                getListaCentroResponsabilidadBeanB();
                listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInRegistro(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
            } else {
                CodigoService codigoService = BeanFactory.getCodigoService();
                getListaCentroResponsabilidadBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
                listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                    listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
                    System.out.println(lista.getCentroResponsabilidadBean().getTipoNivelCR());
                }
                if (!listaDetRequerimientoCRBean.isEmpty()) {
                    idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                }

                getListaCentroResponsabilidadBeanB();
                listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosCrAprobacion(Integer idFact) {
        try {
            System.out.println("entró xd public void cargarDatosCrAprobacion(Integer idFact)");
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            listaCentroResponsabilidadBean = new ArrayList<>();

            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cod != null) {
                facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
            }

            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            listaCuentaFacturaBean = registroCompraService.obtenerCuentaFactPorIdFactura(idFact, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaConcepto(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);

            cuentaFacturaBean.setConceptoUniNegBean(con);

            System.out.println(idConcepto);
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            Integer id = null;
            id = conceptoUniNegService.obtenerTipoPorIdConcepto(idConcepto, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!id.equals(null)) {
                TipoConceptoBean tipo = new TipoConceptoBean();
                tipo = tipoConceptoService.obtenerTipoConceptoPorId(id);
                if (!tipo.equals(null)) {
                    tipoConceptoBean = tipo;
                }
            }
            cuentaFacturaBean.getConceptoBean().setIdConcepto(idConcepto);
            System.out.println("concepto: " + cuentaFacturaBean.getConceptoBean().getIdConcepto());
            System.out.println(tipoConceptoBean.getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String actualizarTipoConcepto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
                listaConceptoBean = conceptoService.obtenerConcepto();
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdConceptoVista(), uniNeg);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void cargarFact() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            if (registroCompraBean.getTipoNumeroFacturaBean().getIdCodigo() == 19601) {

                this.flgMultiple = false;
                this.flgUnica = true;
            } else {
                this.flgMultiple = true;
                this.flgUnica = false;
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarMas() {
        try {
//            CodigoService codigoService = BeanFactory.getCodigoService();
            if (registroCompraBean.getTipoNumeroFacturaBean().getIdCodigo() == 19602) {

                this.flgAgregar = true;
            } else {
                this.flgAgregar = false;
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarRecepcionFiltro() {
        try {

            listaFacturaCompraBean = new ArrayList<>();
            facturaCompraFiltroBean = new FacturaCompraBean();
            fechaActual = new GregorianCalendar();
            getListaFacturaCompraDocEgresoBean();
//            getFacturaCompraFiltroBean().setFechaInicio(fechaActual.getTime());
            getFacturaCompraFiltroBean().setFechaFin(fechaActual.getTime());
            getFacturaCompraFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroRCompra() {
        try {
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            if (registroCompraFiltoBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(registroCompraFiltoBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                registroCompraFiltoBean.setFechaInicio(t);
            }
            if (registroCompraFiltoBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(registroCompraFiltoBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                registroCompraFiltoBean.setFechaFin(u);
            }

//            registroCompraFiltoBean.setIdPaso("Autorizado");//este id identifica que tipo de filtro va realizar 
//            listaRegistroCompraBean = registroCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaRegistroCompraBean = registroCompraService.obtenerPorFiltroRC(registroCompraFiltoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //filtro autorizados
    public void obtenerPorFiltroRCompraAutorizados() {
        try {
            int estado = 0;
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            if (registroCompraFiltoBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(registroCompraFiltoBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                registroCompraFiltoBean.setFechaInicio(t);
                estado = 1;
            }
            if (registroCompraFiltoBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(registroCompraFiltoBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                registroCompraFiltoBean.setFechaFin(u);
                estado = 1;
            }
            if (registroCompraFiltoBean.getIdRegistroCompra() != null && !registroCompraFiltoBean.getIdRegistroCompra().equals(0)) {
                registroCompraFiltoBean.setIdRegistroCompra(registroCompraFiltoBean.getIdRegistroCompra());
                estado = 1;
            }
            if (registroCompraFiltoBean.getNroDoc() != null && !registroCompraFiltoBean.getNroDoc().equals("")) {
                registroCompraFiltoBean.setNroDoc(registroCompraFiltoBean.getNroDoc().trim());
                estado = 1;
            }

            if (registroCompraFiltoBean.getSerieDoc() != null && !registroCompraFiltoBean.getSerieDoc().equals("")) {
                registroCompraFiltoBean.setSerieDoc(registroCompraFiltoBean.getSerieDoc());
                estado = 1;
            }
            if (registroCompraFiltoBean.getEntidadBean().getRuc() != null && !registroCompraFiltoBean.getEntidadBean().getRuc().equals("")) {
                registroCompraFiltoBean.getEntidadBean().setRuc(registroCompraFiltoBean.getEntidadBean().getRuc());
                estado = 1;
            }
            if (registroCompraFiltoBean.getEntidadBean().getNombre() != null && !registroCompraFiltoBean.getEntidadBean().getNombre().equals("")) {
                registroCompraFiltoBean.getEntidadBean().setNombre(registroCompraFiltoBean.getEntidadBean().getNombre());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaRegistroCompraAutorizado = new ArrayList<>();
            }
            if (estado == 1) {
                listaRegistroCompraAutorizado = registroCompraService.obtenerPorFiltroRCAutorizados(registroCompraFiltoBean);
                if (listaRegistroCompraAutorizado.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
            listaRegistroCompraAutorizado = registroCompraService.obtenerPorFiltroRCAutorizados(registroCompraFiltoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String modificarAprobacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            CodigoBean tipoStatusRegCBean = new CodigoBean();
            if (pagina == null) {
                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
                CodigoService codigoService = BeanFactory.getCodigoService();

                tipoStatusRegCBean = codigoService.obtenerPorCodigo(new CodigoBean(0, registroCompraBean.getTipoStatusRegCBean().getCodigo(), new TipoCodigoBean("TipoStatusRegC")));
                //catalogoBean = catalogoService.obtenerPorCatalogo(catalogoBean);
                registroCompraBean.setTipoStatusRegCBean(tipoStatusRegCBean);
                registroCompraService.modificarAprobacion(registroCompraBean);

                listaRegistroCompraAutorizado = registroCompraService.obtenerTodosAprob();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void FiltroSolRegistroC() {
        try {
            int estado = 0;
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            if (registroCompraFiltoBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(registroCompraFiltoBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                registroCompraFiltoBean.setFechaInicio(t);
                estado = 1;
            }
            if (registroCompraFiltoBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(registroCompraFiltoBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                registroCompraFiltoBean.setFechaFin(u);
                estado = 1;
            }
            if (registroCompraFiltoBean.getIdRegistroCompra() != null && !registroCompraFiltoBean.getIdRegistroCompra().equals("")) {
                registroCompraFiltoBean.setIdRegistroCompra(registroCompraFiltoBean.getIdRegistroCompra());
                estado = 1;
            }
            if (registroCompraFiltoBean.getNroDoc() != null && !registroCompraFiltoBean.getNroDoc().equals("")) {
                registroCompraFiltoBean.setNroDoc(registroCompraFiltoBean.getNroDoc().trim());
                estado = 1;
            }
            if (registroCompraFiltoBean.getTipoStatusRegCBean().getIdCodigo() != null && !registroCompraFiltoBean.getTipoStatusRegCBean().getIdCodigo().equals(0)) {
                registroCompraFiltoBean.getTipoStatusRegCBean().setIdCodigo(registroCompraFiltoBean.getTipoStatusRegCBean().getIdCodigo());
                estado = 1;
            } else if (estado == 0) {

                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaRegistroCompraBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaRegistroCompraBean = registroCompraService.obtenerPorFiltroRC(registroCompraFiltoBean);
                if (listaRegistroCompraBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerFacturas(Object fact) {

        try {
            facturaCompraBean = (FacturaCompraBean) fact;

            facturaCompraBean.setSerieDoc(facturaCompraBean.getSerieDoc());
            facturaCompraBean.setNroDoc(facturaCompraBean.getNroDoc());
            facturaCompraBean.setMontoPago(facturaCompraBean.getMontoPago());
            facturaCompraBean.setFlgFactura(facturaCompraBean.getFlgFactura());

            System.out.println("ok");

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);

        }
    }

    public void ponerRegistroCompra(Object registro) {
        try {
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            OrdenCompraService ordenCoEntidadService = BeanFactory.getOrdenCompraService();
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            registroCompraBean = (RegistroCompraBean) registro;

            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(registroCompraBean.getOrdenCompraBean().getEntidadBean().getRuc());
            entidad.setNombre(registroCompraBean.getOrdenCompraBean().getEntidadBean().getNombre());
            entidad.setDireccion(registroCompraBean.getOrdenCompraBean().getEntidadBean().getDireccion());

//            entidadBean = entidadService.obtenerEntidadPorIdCot(ordenCompraBean.getEntidadBean().getRuc(),usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            registroCompraBean.getOrdenCompraBean().setEntidadBean(entidad);
//            entidadBean = entidadService.obtenerEntidadPorIdCot(registroCompraBean.getOrdenCompraBean().getEntidadBean().getRuc(),usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            getOrdenCompraBean();
            ordenCompraBean = ordenCoEntidadService.obtenerPorId(registroCompraBean.getOrdenCompraBean());
            registroCompraBean.setEntidadBean(entidadBean);
            facturaCompraBean = registroCompraService.obtenerPorIdFactura(facturaCompraBean);
            registroCompraBean.setFacturaCompraBean(facturaCompraBean);
            registroCompraBean.getFacturaCompraBean().setSerieDoc(registroCompraBean.getFacturaCompraBean().getSerieDoc());
            registroCompraBean.getFacturaCompraBean().setNroDoc(registroCompraBean.getFacturaCompraBean().getNroDoc());
            registroCompraBean.getFacturaCompraBean().setMontoPago(registroCompraBean.getFacturaCompraBean().getMontoPago());

//obtengo el detalle de la orden de compra
            //obtengo el detalle de la orden de compra
//            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra());
            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorOrden(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = registroCompraBean.getImporte();
            montoGeneralRegistro = registroCompraBean.getMontoGeneralRegistro();

            sumaTotal = sumaImporte + (sumaImporte * (registroCompraBean.getIgv() / 100));

            registroCompraBean.setTotal(sumaTotal);
//            registroCompraBean.setMontoPago(sumaTotal);
            sumaImporte = registroCompraBean.getImporte();

//            cargarFact();
//            cargarMas();
//            ponerFacturas(registro);
            this.flgDetalleActivo = true;
            listaOrdenCompraDetalleBean = new ArrayList<>();
            System.out.println("Excelente");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificarFacturaVista() {
        try {
            setFacturaCompraBean(listaSesionFacturaCompraBean.get(facturaCompraBean.getNroFact()));
//            setListaCuentaFacturaBean(listaSesionFacturaCompraBean.get(facturaCompraBean.getNroFact()).getListaCuentaFacturaBean());
            listaCuentaFacturaBean = facturaCompraBean.getListaCuentaFacturaBean();
//            montoDetraccion= facturaCompraBean.getValorDetraccion();
            setMontoDetraccion(facturaCompraBean.getValorDetraccion());
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            cuentaFacturaBean.getUnidadNegocioBean().setUniNeg(registroCompraBean.getUnidadNegocioBean().getUniNeg());
            cuentaFacturaBean.getFacturaCompraBean().getRegistroCompraBean().setIdRegistroCompra(registroCompraBean.getIdRegistroCompra());
            listaCuentaFacturaBean = registroCompraService.obtenerCuentaFact(cuentaFacturaBean);
            System.out.println("Excelente");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarFacturaVista() {
        try {
            listaSesionFacturaCompraBean.remove(facturaCompraBean);
            System.out.println("Excelente");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarCuentaVista(Integer n, CuentaFacturaBean cuentaFactura) {
        try {
            listaCuentaFacturaBean.remove(cuentaFactura);

            Double montoFactura = 0.0;
            System.out.println("sizeeee eliminacion..." + listaCuentaFacturaBean.size());
            for (CuentaFacturaBean cta : listaCuentaFacturaBean) {
                if (cta.getValor() != null) {
                    montoFactura = montoFactura + cta.getValor();
                }
            }
            facturaCompraBean.setImporte(montoFactura);
            System.out.println("facturaCompraBean..." + facturaCompraBean.getImporte());
            System.out.println("Excelente");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarCuentaVistaAprobacion(Integer n, CuentaFacturaBean cuentaFactura) {
        try {
            listaCuentaFacturaBean.remove(cuentaFactura);

            Double montoFactura = 0.0;
            System.out.println("sizeeee eliminacion..." + listaCuentaFacturaBean.size());
            for (CuentaFacturaBean cta : listaCuentaFacturaBean) {
                if (cta.getValor() != null) {
                    montoFactura = montoFactura + cta.getValor();
                }
            }
//            facturaCompraBean.setImporte(montoFactura);
            System.out.println("facturaCompraBean..." + facturaCompraBean.getImporte());
            System.out.println("Excelente");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarFacturas(RegistroCompraBean bean) {
        try {
            CodigoBean tipoNumeroFactura = new CodigoBean();
            RegistroCompraBean registroCompraBean = new RegistroCompraBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            //Si es false, quiereu decir que no ha sido agregado ese item seleccionado
            if (registroCompraBean.getTipoNumeroFacturaBean().getCodigo().equals("Factura Multiple")) {

                registroCompraBean.setMontoPago(bean.getMontoPago());
                registroCompraBean.setSerieDoc(bean.getSerieDoc());
                registroCompraBean.setNroDoc(bean.getNroDoc());
                listaRegistroCompraBean.add(registroCompraBean);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Lógica de negocio
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
            ordenCompraBean = new OrdenCompraBean();
            registroCompraBean = new RegistroCompraBean();
            listaOrdenCompraDetalleBean = new ArrayList<>();
            listaDetalleRegistroCompraBean = new ArrayList<>();
            listaSesionFacturaCompraBean = new ArrayList<>();
            detRegistroCompraBean = new DetRegistroCompraBean();
            facturaCompraBean = new FacturaCompraBean();
            fechaOrdenView = "";
            sumaImporte = 0.00;
            sumaTotal = 0.00;
            this.flgDetalleActivo = false;
            this.cargarDatosSession();
            getRegistroCompraFiltoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            sumaImporte = 0.00;
            EntidadService entidadService = BeanFactory.getEntidadService();
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();

            ordenCompraBean = (OrdenCompraBean) event.getObject();
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorIdCot(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorOrden(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = ordenCompraBean.getMontoRef();
            igvResultado = facturaCompraBean.getImporte() * (facturaCompraBean.getIgv() / 100);
            facturaCompraBean.setIgvResultado(igvResultado);
            montoGeneralRegistro = registroCompraBean.getMontoGeneralRegistro();
            fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(OrdenCompraBean bean) {
        try {
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
//            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            ordenCompraBean = new OrdenCompraBean();
            sumaImporte = 0.00;
            montoGeneralRegistro = 0.00;

            ordenCompraBean = ordenCompraService.obtenerPorId(bean);
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorIdCot(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorOrden(registroCompraBean.getIdRegistroCompra());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoGeneralRegistro = registroCompraBean.getMontoGeneralRegistro();
            igvResultado = facturaCompraBean.getImporte() * (facturaCompraBean.getIgv() / 100);
            facturaCompraBean.setIgvResultado(igvResultado);

            //fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorIdRC(OrdenCompraBean bean) {
//        String moneda;
        try {
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            bean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean = new OrdenCompraBean();
            sumaImporte = 0.00;
            ordenCompraBean = ordenCompraService.obtenerPorId(bean);
            //obtengo el proveedor correspondiente
//            entidadBean = entidadService.obtenerEntidadPorId(ordenCompraBean.getEntidadBean());  
            ordenCompraBean.setObs(ordenCompraBean.getObs());
            ordenCompraBean.setImporteAdelanto(ordenCompraBean.getImporteAdelanto());
            ordenCompraBean.setEntidadBean(ordenCompraBean.getEntidadBean());
            registroCompraBean.setOrdenCompraBean(ordenCompraBean);
            registroCompraBean.setNroRegistro(ordenCompraBean.getNroCompra());
//            detRegistroCompraBean.getRegistroCompraBean().getTipoMonedaBean();
//obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            ordenCompraDetalleBean = ordenCompraDetalleService.obtenerPorId(ordenCompraDetalleBean);
//            setOrdenCompraDetalleBean(ordenCompraDetalleBean);
//            detRegistroCompraBean.setRegistroCompraBean(registroCompraBean);
//            detRegistroCompraBean.setOrdenCompraDetalleBean(ordenCompraDetalleBean);
//            getOrdenCompraDetalleBean();
//            ordenCompraDetalleBean = ordenCompraDetalleService.obtenerPorId(detRegistroCompraBean.getOrdenCompraDetalleBean());
//            listaRequerimientoCRBean = solicitudLogisticoService.ObtenerPorIdCRDis(ordenCompraDetalleBean.getSolicitudLogisticoBean());
//            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorOrden(registroCompraBean.getIdRegistroCompra());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoGeneralRegistro = registroCompraBean.getMontoGeneralRegistro();
            sumaTotal = sumaImporte + (sumaImporte * (registroCompraBean.getIgv() / 100));
//             igvResultado = facturaCompraBean.getImporte() * (facturaCompraBean.getIgv() / 100);
//             facturaCompraBean.setIgvResultado(igvResultado);
            registroCompraBean.setTotal(sumaTotal);
//            registroCompraBean.setMontoPago(sumaTotal);
            Integer est = 1;
            for (OrdenCompraDetalleBean lista : listaOrdenCompraDetalleBean) {
                if (lista.getFacturaCompraBean().getSerieNroDoc() != null) {
                    if (lista.getFacturaCompraBean().getSerieNroDoc() == null || lista.getFacturaCompraBean().getSerieNroDoc().equals("")) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                }
            }
            if (est.equals(1)) {
                this.flgDetalleActivo = Boolean.TRUE;
            } else {
                this.flgDetalleActivo = Boolean.FALSE;
            }
            System.out.println("flgDetalleActivo: " + flgDetalleActivo);
            this.flgDoc = true;
            //fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltro() {
        try {
            System.out.println("xxxxxxxxxxxxxxx");
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

            // listaOrdenCompraBean = ordenCompraService.obtenerPorFiltro(ordenCompraFiltroBean);
            listaOrdenCompraBean = ordenCompraService.obtenerPorFiltro(ordenCompraFiltroBean);
            System.out.println("zzzzzzzzzzzzzzzzzz");
            if (listaOrdenCompraBean.isEmpty()) {
                System.out.println("nulllllllllllllllllllll");
            } else {
                System.out.println("size " + listaOrdenCompraBean.size());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroRC() {
        try {
            int estado = 0;
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            if (registroCompraFiltoBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(registroCompraFiltoBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                registroCompraFiltoBean.setFechaInicio(t);
                estado = 1;
            }
            if (registroCompraFiltoBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(registroCompraFiltoBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                registroCompraFiltoBean.setFechaFin(u);
                estado = 1;
            }
//            if (registroCompraFiltoBean.getIdRegistroCompra() != null && !registroCompraFiltoBean.getIdRegistroCompra().equals(0)) {
//                registroCompraFiltoBean.setIdRegistroCompra(registroCompraFiltoBean.getIdRegistroCompra());
//                estado = 1;
//            }
//            listaRegistroCompraBean = registroCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaRegistroCompraBean = registroCompraService.obtenerPorFiltroRC(registroCompraFiltoBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String grabar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
                DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
                OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
                EntidadService entidadService = BeanFactory.getEntidadService();

                if (registroCompraBean.getIdRegistroCompra() == null) {
                    registroCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                    registroCompraBean.setImporte(ordenCompraBean.getMontoRef());
                    registroCompraBean.setTotal(sumaTotal);
                    registroCompraBean.setMontoPago(sumaTotal);
                    registroCompraBean.setMontoGeneralRegistro(montoGeneralRegistro);
                    registroCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    registroCompraBean.setNroRegistro(ordenCompraBean.getNroCompra());
                    if (registroCompraBean.getFlgRecibido() == true) {
                        registroCompraBean.getTipoStatusRegCBean().setIdCodigo(MaristaConstantes.COD_REGISTRADO_REGISTRO_COMPRA);
                    }
//                    entidadBean = entidadService.obtenerEntidadPorId(ordenCompraBean.getEntidadBean());
                    registroCompraBean.setEntidadBean(ordenCompraBean.getEntidadBean());
                    DetRegistroCompraBean detRegistroCompra = new DetRegistroCompraBean();
//                    registroCompraService.insertar(registroCompraBean, listaOrdenCompraDetalleBean, detRegistroCompra, listaSesionFacturaCompraBean);
                    registroCompraService.insertar(registroCompraBean, listaOrdenCompraDetalleBean, detRegistroCompraBean, listaSesionFacturaCompraBean, ordenCompraBean, detRegistroCompraCRBean, facturaCompraBean,
                            listaCuentaFacturaBean, cuentaFacturaBean);
                    System.out.println("ok");
                } else {
                    registroCompraService.modificar(registroCompraBean, listaDetalleRegistroCompraBean, detRegistroCompraBean, listaSesionFacturaCompraBean, ordenCompraBean, detRegistroCompraCRBean, facturaCompraBean,
                            listaCuentaFacturaBean, cuentaFacturaBean);
                    registroCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    detRegistroCompraService.eliminar(registroCompraBean.getIdRegistroCompra());
//                    DetRegistroCompraBean detRegistroCompraBean = new DetRegistroCompraBean();
//                     detRegistroCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    detRegistroCompraService.insertar(registroCompraBean, listaOrdenCompraDetalleBean, detRegistroCompraBean);
                }
                //  listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaRegistroCompraBean = registroCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                this.limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

//    public String eliminarCargo() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
//                ordenCompraDetalleService.eliminar(ordenCompraBean);
//                listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(idOrdenCompra);
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
//    
//    public String grabarFactura() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//
//                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
//                DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
//
//                if (facturaCompraBean.getIdFacturaCompra() == null) {
//                    facturaCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    facturaCompraBean.setSerieDoc(facturaCompraBean.getRegistroCompraBean().getSerieDoc());
//                    facturaCompraBean.setNroDoc(facturaCompraBean.getRegistroCompraBean().getNroDoc());
//                    facturaCompraBean.setMontoPago(facturaCompraBean.getRegistroCompraBean().getMontoPago());
//                    facturaCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
//                    facturaCompraBean.getRegistroCompraBean().setIdRegistroCompra(facturaCompraBean.getRegistroCompraBean().getIdRegistroCompra());
//
//                    registroCompraService.insertarFactura(facturaCompraBean);
//
//                    System.out.println("ok");
//                } else {
//                    facturaCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
//                    facturaCompraBean.setIdFacturaCompra(facturaCompraBean.getIdFacturaCompra());
//                    facturaCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    registroCompraService.modificarFactura(facturaCompraBean); 
//                }
//                //  listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listaFacturaCompraBean = registroCompraService.obtenerTodosPorUniNegFact(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                this.limpiar();
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
    public String grabarFactura() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {

                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
                DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();

                if (facturaCompraBean.getIdFacturaCompra() == null) {
                    facturaCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                    facturaCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    facturaCompraBean.getRegistroCompraBean().setIdRegistroCompra(registroCompraBean.getIdRegistroCompra());
                    registroCompraService.insertarFactura(facturaCompraBean);
                    System.out.println("ok");
                } else {
                    facturaCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    facturaCompraBean.setIdFacturaCompra(facturaCompraBean.getIdFacturaCompra());
                    facturaCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                    registroCompraService.modificarFactura(facturaCompraBean);
                }
                //  listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaFacturaCompraBean = registroCompraService.obtenerTodosPorUniNegFact(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void elegirDoc() {

        try {
            if (registroCompraBean.getTipoDocBean().getIdCodigo() != null) {
                facturaCompraBean.setFlgRecibo(false);
                facturaCompraBean.setFlgFactura(false);
                facturaCompraBean.setFlgBoleta(false);

                if (registroCompraBean.getTipoDocBean().getIdCodigo().equals(MaristaConstantes.COD_DOC_REC)) {
                    facturaCompraBean.setFlgRecibo(true);
                    System.out.println("rec");
                    System.out.println(facturaCompraBean.getFlgRecibo());
                    System.out.println(facturaCompraBean.getFlgFactura());
                    System.out.println(facturaCompraBean.getFlgBoleta());
                    System.out.println("---");

                } else if (registroCompraBean.getTipoDocBean().getIdCodigo().equals(MaristaConstantes.COD_DOC_FACT)) {
                    facturaCompraBean.setFlgFactura(true);
                    System.out.println("fac");
                    System.out.println(facturaCompraBean.getFlgRecibo());
                    System.out.println(facturaCompraBean.getFlgFactura());
                    System.out.println(facturaCompraBean.getFlgBoleta());
                    System.out.println("---");

                } else {
                    facturaCompraBean.setFlgBoleta(true);
                    System.out.println("bol");
                    System.out.println(facturaCompraBean.getFlgRecibo());
                    System.out.println(facturaCompraBean.getFlgFactura());
                    System.out.println(facturaCompraBean.getFlgBoleta());
                    System.out.println("---");
                }
                cambiarIGV();
            } else {
                facturaCompraBean.setFlgRecibo(false);
                facturaCompraBean.setFlgFactura(false);
                facturaCompraBean.setFlgBoleta(false);
                System.out.println("null");
                System.out.println(facturaCompraBean.getFlgRecibo());
                System.out.println(facturaCompraBean.getFlgFactura());
                System.out.println(facturaCompraBean.getFlgBoleta());
                System.out.println("---");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListasFactura() {
        try {
            getFacturaCompraBean();
            getFacturaCompraBean().setFlgIGV(true);

            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            getListaDetraccionBean();
            listaDetraccionBean = detraccionService.obtenerTodosActivos();

            getListaTipoPrioridadBean();
            listTipoPrioridadFacturaBean = listaTipoPrioridadBean;
            getListaSesionFacturaCompraBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDoc"));
            for (CodigoBean bean : listaTipoDocBean) {
                if (bean.getCodigo().equals("Factura")) {
                    getRegistroCompraBean().getTipoDocBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoDoc = bean.getIdCodigo();
                }
                elegirDoc();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListasFacturaOrden() {
        try {
            getFacturaCompraBean();
            getFacturaCompraBean().setFlgIGV(true);

            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            getListaDetraccionBean();
            listaDetraccionBean = detraccionService.obtenerTodosActivos();

            getListaTipoPrioridadBean();
            listTipoPrioridadFacturaBean = listaTipoPrioridadBean;
            getListaSesionFacturaCompraBean();
            getListaDetRegistroCompraCRBean();
            getListaDetRequerimientoCRBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDoc"));
            for (CodigoBean bean : listaTipoDocBean) {
                if (bean.getCodigo().equals("Factura")) {
                    getRegistroCompraBean().getTipoDocBean().setIdCodigo(bean.getIdCodigo());
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

            if (registroCompraBean.getTipoDocBean().getIdCodigo().equals(MaristaConstantes.COD_DOC_REC)) {
                if (facturaCompraBean.getFlgIGV() == true) {
                    facturaCompraBean.setIgv(MaristaConstantes.VALOR_IGV_RECIBO_NEGATIVO_8);
                } else {
                    facturaCompraBean.setIgv(MaristaConstantes.VALOR_IGV_RECIBO_POSITIVO_0);
                }
            } else if (registroCompraBean.getTipoDocBean().getIdCodigo().equals(MaristaConstantes.COD_DOC_FACT)) {
                if (facturaCompraBean.getFlgIGV() == true) {
                    facturaCompraBean.setIgv(MaristaConstantes.VALOR_IGV);
                } else {
                    facturaCompraBean.setIgv(MaristaConstantes.VALOR_IGV_0);
                }
            } else {
                facturaCompraBean.setIgv(MaristaConstantes.VALOR_IGV_0);
            }
//            if(facturaCompraBean.getImporte() != null)
//            { 
////            facturaCompraBean.setImporteSinIgv((double) Math.round((facturaCompraBean.getImporte() / 1.18) * 100) / 100);
////            facturaCompraBean.setImpuesto((double) Math.round((facturaCompraBean.getImporte() - facturaCompraBean.getImporteSinIgv()) * 100) / 100);
////            System.out.println("SinIgv: " + facturaCompraBean.getImporteSinIgv());
////            System.out.println("Impuesto: " + facturaCompraBean.getImpuesto());
//            if (facturaCompraBean.getFlgIGV() == true) {
//                facturaCompraBean.setImporteSinIgv((double) Math.round((facturaCompraBean.getImporte() / 1.18) * 100) / 100);
//                facturaCompraBean.setImpuesto((double) Math.round((facturaCompraBean.getImporte() - facturaCompraBean.getImporteSinIgv()) * 100) / 100);
//            } else {
//                facturaCompraBean.setImporteSinIgv((double) Math.round(facturaCompraBean.getImporte() * 100) / 100);
//                facturaCompraBean.setImpuesto(0.00);
//            }
//            System.out.println("SinIgv: " + facturaCompraBean.getImporteSinIgv());
//            System.out.println("Impuesto: " + facturaCompraBean.getImpuesto());
//            }
//            obtenerDetracionFactura();
            obtenerIgv();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerIgv() {
        try {
            if (facturaCompraBean != null) {
                if (getFacturaCompraBean().getImporte() != null && getFacturaCompraBean().getIgv() >= 0) {
                    igvResultado = facturaCompraBean.getImporte() * (facturaCompraBean.getIgv() / 100);
                    facturaCompraBean.setIgvResultado(igvResultado);
                    subTotal = facturaCompraBean.getImporte() + (facturaCompraBean.getImporte() * (facturaCompraBean.getIgv() / 100));
                    facturaCompraBean.setImpuesto(subTotal);
                    if (getFacturaCompraBean().getDetraccionBean().getIdDetraccion() != null) {
                        facturaCompraBean.setMontoPago(subTotal);
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerDetracionFactura() {
        try {
            Double total = 0.00;
            String tot = "";
            this.montoTotal = "";
            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            DetraccionBean detraccion = new DetraccionBean();
//            facturaCompraBean = new FacturaCompraBean();
//            obtenerIgv(); //Lo saque porque el flgigv solo sera referencial y ya no calculara.
            detraccion = detraccionService.obtenerPorId(facturaCompraBean.getDetraccionBean());//no tiene valor
            if (detraccion != null) {
                double monDouble = 0;
                double mon = 0;
                monDouble = facturaCompraBean.getImporte();
                if (listaOrdenCompraDetalleBean.get(0).getCatalogoBean().getTipoMonedaBean().getCodigo().equals("Soles")) {
                    if (detraccion.getDescripcion().equals("Retención RH")) {
                        montoDetraccion = monDouble * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                    } else {
                        montoDetraccion = monDouble * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                        montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                    }
                }
                if (listaOrdenCompraDetalleBean.get(0).getCatalogoBean().getTipoMonedaBean().getCodigo().equals("Dolares")) {
                    TipoCambioService cambio = BeanFactory.getTipoCambioService();
                    Integer tipocambio;
                    tipocambio = cambio.obtenerUltimoTipCambio();
                    TipoCambioBean tipoCambioBean = new TipoCambioBean();
                    tipoCambioBean.setIdTipoMoneda(tipocambio);
                    tipoCambioBean = cambio.buscarPorId(tipoCambioBean);
                    if (detraccion.getDescripcion().equals("Retención RH")) {
                        montoDetraccion = (monDouble * tipoCambioBean.getTcVenta().doubleValue()) * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                    } else {
                        montoDetraccion = (monDouble * tipoCambioBean.getTcVenta().doubleValue()) * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                        montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                    }
                }
                mon = (double) Math.round((monDouble - montoDetraccion) * 100) / 100;
                facturaCompraBean.setMontoPago((mon));
                facturaCompraBean.getDetraccionBean().setValor(montoDetraccion);
                facturaCompraBean.setImporteSinIgv((double) Math.round((facturaCompraBean.getImporte() / 1.18) * 100) / 100);
                facturaCompraBean.setImpuesto((double) Math.round((facturaCompraBean.getImporte() - facturaCompraBean.getImporteSinIgv()) * 100) / 100);
                System.out.println("SinIgv: " + facturaCompraBean.getImporteSinIgv());
                System.out.println("Impuesto: " + facturaCompraBean.getImpuesto());
                if (facturaCompraBean.getFlgIGV() == true) {
                    facturaCompraBean.setImporteSinIgv((double) Math.round((facturaCompraBean.getImporte() / 1.18) * 100) / 100);
                    facturaCompraBean.setImpuesto((double) Math.round((facturaCompraBean.getImporte() - facturaCompraBean.getImporteSinIgv()) * 100) / 100);
                } else {
                    facturaCompraBean.setImporteSinIgv((double) Math.round(facturaCompraBean.getImporte() * 100) / 100);
                    facturaCompraBean.setImpuesto((double) Math.round((facturaCompraBean.getImporte() - facturaCompraBean.getImporteSinIgv()) * 100) / 100);
                }
                System.out.println("SinIgv: " + facturaCompraBean.getImporteSinIgv());
                System.out.println("Impuesto: " + facturaCompraBean.getImpuesto());
//                distribuir();  
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarFacturaVista() {
        try {
            if (facturaCompraBean.getSerieDoc() != null && facturaCompraBean.getNroDoc() != null && registroCompraBean.getTipoDocBean().getIdCodigo() != null
                    //                    && facturaCompraBean.getTipoPrioridadBean().getIdCodigo() != null 
                    && facturaCompraBean.getIgv() != null
                    && facturaCompraBean.getImporte() != null && facturaCompraBean.getTipoMonedaBean().getIdCodigo() != null) {

                facturaCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                facturaCompraBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                facturaCompraBean.setSerieDoc(facturaCompraBean.getSerieDoc().trim());
                facturaCompraBean.setNroDoc(facturaCompraBean.getNroDoc().trim());
                facturaCompraBean.setImpuesto(facturaCompraBean.getImpuesto());
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
                if (facturaCompraBean.getIdFacturaCompra() == null) {
                    if (facturaCompraBean.getNroFact().equals(0)) {
                        Integer factAyuda = listaSesionFacturaCompraBean.size() + 1;
                        facturaCompraBean.setNroFact(factAyuda);
                        System.out.println("sdfgh" + factAyuda);
//                    Integer cantCuenta1 = listaCuentaFacturaBean.size();
//                    listaCuentaFacturaBean.get(factAyuda - 1).setNroFact(facturaCompraBean.getNroFact()); 
//                    listaCuentaFacturaBean.get
                        facturaCompraBean.setValorDetraccion(montoDetraccion);
                        listaSesionFacturaCompraBean.add(facturaCompraBean);
                        listaSesionFacturaCompraBean.get(factAyuda - 1).setListaCuentaFacturaBean(listaCuentaFacturaBean);
                    } else {
                        listaSesionFacturaCompraBean.remove(facturaCompraBean);
                        Integer factAyuda = listaSesionFacturaCompraBean.size() + 1;
                        facturaCompraBean.setNroFact(factAyuda);
                        System.out.println("qwert" + factAyuda);
//                    listaCuentaFacturaBean.get(factAyuda - 1).setNroFact(facturaCompraBean.getNroFact());
                        facturaCompraBean.setValorDetraccion(montoDetraccion);
                        listaSesionFacturaCompraBean.add(facturaCompraBean);
                        listaSesionFacturaCompraBean.get(factAyuda - 1).setListaCuentaFacturaBean(listaCuentaFacturaBean);
                    }
                } else {
                    listaSesionFacturaCompraBean.remove(facturaCompraBean);
                    Integer factAyuda = listaSesionFacturaCompraBean.size() + 1;
                    facturaCompraBean.setNroFact(factAyuda);
                    System.out.println("qwert" + factAyuda);
//                    listaCuentaFacturaBean.get(factAyuda - 1).setNroFact(facturaCompraBean.getNroFact());
                    facturaCompraBean.setValorDetraccion(montoDetraccion);
                    listaSesionFacturaCompraBean.add(facturaCompraBean);
                    listaSesionFacturaCompraBean.get(factAyuda - 1).setListaCuentaFacturaBean(listaCuentaFacturaBean);
                }
                System.out.println("impuesto: " + facturaCompraBean.getImpuesto());
//                nroFactAyuda = facturaCompraBean.getNroFact();
                facturaCompraBean = new FacturaCompraBean();
                listaCuentaFacturaBean = new ArrayList<>();
                montoDetraccion = 0.0;
                cargarListasFactura();
                CodigoBean cod2 = new CodigoBean();
                cod2 = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod2 != null) {
                    facturaCompraBean.setCodDistribucion(cod2.getIdCodigo());
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjLlenarDatosFactura");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarConceptoVista() {
        try {
            if (cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta() != null) {
                System.out.println("cta " + cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta());
                for (DetRegistroCompraCRBean listaCr : facturaCompraBean.getListaDetRequerimientoCRBean()) {
                    CuentaFacturaBean cuentaBean = new CuentaFacturaBean();
                    cuentaBean.setPlanContableDBean(cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
                    cuentaBean.setPlanContableHBean(cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
                    cuentaBean.setCentroResponsabilidadBean(listaCr.getCentroResponsabilidadBean());
                    cuentaBean.setValor(listaCr.getValorD());
                    cuentaBean.getConceptoBean().setIdConcepto(cuentaFacturaBean.getConceptoBean().getIdConcepto());
                    CodigoService codigoService = BeanFactory.getCodigoService();
                    CodigoBean codigo = new CodigoBean();
                    codigo.setIdCodigo(facturaCompraBean.getCodDistribucion());
                    codigo = codigoService.obtenerPorId(codigo);
                    if (codigo != null) {
                        cuentaBean.setTipoDistribucion(codigo);
                    }
                    System.out.println("importe " + facturaCompraBean.getImporte());
                    listaCuentaFacturaBean.add(cuentaBean);
                    System.out.println("concepto Cuenta: " + cuentaBean.getConceptoBean().getIdConcepto());
                }
                Double montoFactura = 0.0;
                for (CuentaFacturaBean cta : listaCuentaFacturaBean) {
                    if (cta.getValor() != null) {
                        montoFactura = montoFactura + cta.getValor();
                        montoFactura = (double) Math.round((montoFactura) * 100) / 100;
                    }
                }
                facturaCompraBean.setImporte(montoFactura);

                TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                listaConceptoUniNegBean = new ArrayList<>();
                listaTipoConceptoBean = new ArrayList<>();
                tipoConceptoBean = new TipoConceptoBean();
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorEgr(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();
                //
                listaDetRequerimientoCRBean = new ArrayList<>();
                cuentaFacturaBean = new CuentaFacturaBean();
                facturaCompraBean.setListaDetRequerimientoCRBean(null);

            } else {
                new MensajePrime().addInformativeMessagePer("msjLlenarDatosFactura");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarConceptoVistaAprobacion() {
        try {
            Integer cuenta = cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta();
            if (cuenta != null) {
                System.out.println("cta " + cuenta);
                Boolean fglCta = Boolean.FALSE;
                for (CuentaFacturaBean lista : listaCuentaFacturaBean) {
                    if (lista.getPlanContableDBean().getCuenta().equals(cuenta)) {
                        fglCta = Boolean.TRUE;
                        System.out.println("cuenta ya esta en la lista");
                    }
                    break;
                }

                if (fglCta.equals(Boolean.TRUE)) {
                    quitarCuenta(listaCuentaFacturaBean, cuenta);
                }

                for (DetRegistroCompraCRBean listaCr : facturaCompraBean.getListaDetRequerimientoCRBean()) {
                    CuentaFacturaBean cuentaBean = new CuentaFacturaBean();
                    cuentaBean.setPlanContableDBean(cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
                    cuentaBean.setPlanContableHBean(cuentaFacturaBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
                    cuentaBean.setCentroResponsabilidadBean(listaCr.getCentroResponsabilidadBean());
                    cuentaBean.setValor(listaCr.getValorD());
                    cuentaBean.getConceptoBean().setIdConcepto(cuentaFacturaBean.getConceptoBean().getIdConcepto());
                    CodigoService codigoService = BeanFactory.getCodigoService();
                    CodigoBean codigo = new CodigoBean();
                    codigo.setIdCodigo(facturaCompraBean.getCodDistribucion());
                    codigo = codigoService.obtenerPorId(codigo);
                    if (codigo != null) {
                        cuentaBean.setTipoDistribucion(codigo);
                    }
                    System.out.println("importe " + facturaCompraBean.getImporte());
                    listaCuentaFacturaBean.add(cuentaBean);
                    System.out.println("concepto Cuenta: " + cuentaBean.getConceptoBean().getIdConcepto());
                }

                TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                listaConceptoUniNegBean = new ArrayList<>();
                listaTipoConceptoBean = new ArrayList<>();
                tipoConceptoBean = new TipoConceptoBean();
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorEgr(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();
                //
                listaDetRequerimientoCRBean = new ArrayList<>();
                cuentaFacturaBean = new CuentaFacturaBean();
                facturaCompraBean.setListaDetRequerimientoCRBean(null);

                /// null campos                
                listaCentroResponsabilidadBeanB = new ArrayList<>();
                listaCentroResponsabilidadBean = new ArrayList<>();

                CodigoService codigoService = BeanFactory.getCodigoService();
                getListaCentroResponsabilidadBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
                listaDetRequerimientoCRBean = solicitudLogisticoService.obtenerCRRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                for (DetRequerimientoCRBean lista : listaDetRequerimientoCRBean) {
                    listaCentroResponsabilidadBean.add(lista.getCentroResponsabilidadBean());
                    System.out.println(lista.getCentroResponsabilidadBean().getTipoNivelCR());
                }
                if (!listaDetRequerimientoCRBean.isEmpty()) {
                    idTipoDistribucion = listaDetRequerimientoCRBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                }

                getListaCentroResponsabilidadBeanB();
                listaCentroResponsabilidadBeanB = solicitudLogisticoService.obtenerCRInRegistro(registroCompraBean.getOrdenCompraBean().getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCRReq(idTipoDistribucion, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    facturaCompraBean.setCodDistribucion(cod.getIdCodigo());
                }
                ///
            } else {
                new MensajePrime().addInformativeMessagePer("msjLlenarDatosFactura");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //quitando la cuenta de la factura aprobada
    public void quitarCuenta(List<CuentaFacturaBean> lista, Integer cuenta) {
        try {
            for (CuentaFacturaBean lis : listaCuentaFacturaBean) {
                if (lis.getPlanContableDBean().getCuenta().equals(cuenta)) {
                    System.out.println("eliminado..." + cuenta);
                    listaCuentaFacturaBean.remove(lis);
                    break;
                }
            }

            for (CuentaFacturaBean lista2 : listaCuentaFacturaBean) {
                if (lista2.getPlanContableDBean().getCuenta().equals(cuenta)) {
                    System.out.println("cuenta ya esta en la lista222");
                    quitarCuenta(listaCuentaFacturaBean, cuenta);
                    break;
                }

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarFactura() {
        try {
            if (registroCompraBean.getIdRegistroCompra() == null) {
                if (listaSesionFacturaCompraBean.size() == 1) {
                    String nroDoc = null;
                    nroDoc = listaSesionFacturaCompraBean.get(0).getSerieNroDoc();
                    if (nroDoc != null) {
                        for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
                            String cadena[] = nroDoc.split("-");
                            String cadena2 = cadena[0];
                            String cadena3 = cadena[1];
                            listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setSerieDoc(cadena2);
                            listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setNroDoc(cadena3);
                            listaOrdenCompraDetalleBean.get(i).setCantidadRecibida(listaOrdenCompraDetalleBean.get(i).getCantidad());
                            listaOrdenCompraDetalleBean.get(i).setIdFacturaCompra(nroDoc);
                        }
                    }
                }
            } else {
                if (listaSesionFacturaCompraBean.size() == 1) {
                    String nroDoc = null;
                    nroDoc = listaSesionFacturaCompraBean.get(0).getSerieNroDoc();
                    if (nroDoc != null) {
                        for (int i = 0; i < listaDetalleRegistroCompraBean.size(); i++) {
                            String cadena[] = nroDoc.split("-");
                            String cadena2 = cadena[0];
                            String cadena3 = cadena[1];
                            listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().setSerieDoc(cadena2);
                            listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().setNroDoc(cadena3);
                            listaDetalleRegistroCompraBean.get(i).setCantidadRecibida(listaDetalleRegistroCompraBean.get(i).getCantidad());
//                        listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().setIdFacturaCompra(nroDoc.compareTo(uniNeg));
                        }
                    }
                }
            }
            Integer est = 1;
            for (OrdenCompraDetalleBean lista : listaOrdenCompraDetalleBean) {
                if (lista.getFacturaCompraBean().getSerieNroDoc() != null) {
                    if (lista.getFacturaCompraBean().getSerieNroDoc() == null || lista.getFacturaCompraBean().getSerieNroDoc().equals("")) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                }
            }
            if (est.equals(1)) {
                this.flgDetalleActivo = Boolean.TRUE;
            } else {
                this.flgDetalleActivo = Boolean.FALSE;
            }
            System.out.println("flgDetalleActivo: " + flgDetalleActivo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public Boolean validarItemPorAgregar(SolicitudLogDetalleBean bean) {
        Boolean valor = false;
        try {
            //Para que lo agregue no debe existir en el detalle de esa orden de compra que esta
            //armando ni en las ordenes de compra que haya sido generada
            for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
//                    detalle.getFacturaCompraBean().setIdFacturaCompra(detalle.getFacturaCompraBean().getIdFacturaCompra());
                    valor = true;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void asignarFacturaOC(RowEditEvent event) {
        try {
            for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
                OrdenCompraDetalleBean ordenCompra = new OrdenCompraDetalleBean();
                ordenCompra.setIdFacturaCompra(((OrdenCompraDetalleBean) event.getObject()).getIdFacturaCompra());
                if (listaOrdenCompraDetalleBean.get(i).getIdDetOrdenCompra().equals(((OrdenCompraDetalleBean) event.getObject()).getIdDetOrdenCompra()) && ordenCompra != null) {
                    String cadena[] = ordenCompra.getIdFacturaCompra().split("-");
                    String cadena2 = cadena[0];
                    String cadena3 = cadena[1];
                    listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setSerieDoc(cadena2);
                    listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setNroDoc(cadena3);
                    listaOrdenCompraDetalleBean.get(i).setCantidadRecibida(((OrdenCompraDetalleBean) event.getObject()).getCantidadRecibida());
                    listaOrdenCompraDetalleBean.get(i).setImporte(((OrdenCompraDetalleBean) event.getObject()).getImporte());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void asignarCuentaCR(Integer centroResponsabilidad, Integer n) {
        try {//al detalle le quiere poner una factura
            //cuenta         cr 
//            Integer crSession=facturaCompraBean.getListaDetRequerimientoCRBean().get(n).getCentroResponsabilidadBean().getCr();
            System.out.println("cr: " + centroResponsabilidad);
            System.out.println("n: " + n);
            System.out.println("size: " + listaCuentaFacturaBean.size());
            for (int j = 0; j < listaCuentaFacturaBean.size(); j++) {
                if (n.equals(j)) {
                    System.out.println("equals: " + n);
                    if (!listaCuentaFacturaBean.isEmpty()) {
                        System.out.println("!is empty: " + n);
                        listaCuentaFacturaBean.get(j).getCentroResponsabilidadBean().setCr(centroResponsabilidad);
                        listaCuentaFacturaBean.get(j).setValor(facturaCompraBean.getListaDetRequerimientoCRBean().get(j).getValorD());
                        break;
                    } else {
                        System.out.println("null: ");
                    }

                } else {
                    System.out.println("equals: " + n);
                }
//                System.out.println("n: " + j + " cr: " + listaCuentaFacturaBean.get(j).getCentroResponsabilidadBean().getCr());
            }
//                      listaCuentaFacturaBean.get(i).getCentroResponsabilidadBean().setCr(registroCompraBean.getDetRequerimientoCRBean().getCentroResponsabilidadBean().getCr());

//            System.out.println("cr: " + listaCuentaFacturaBean.get(n).getCentroResponsabilidadBean().getCr());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {

            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();

            OrdenCompraDetalleBean ordenCompraDetalle = new OrdenCompraDetalleBean();
//            RegistroCompraBean registroCompraBean = new RegistroCompraBean();
//            FacturaCompraBean facturaCompra = new FacturaCompraBean();

            //Seteando los ID's
            ordenCompraDetalle.setUnidadNegocioBean(((OrdenCompraDetalleBean) event.getObject()).getUnidadNegocioBean());
            ordenCompraDetalle.setOrdenCompraBean(((OrdenCompraDetalleBean) event.getObject()).getOrdenCompraBean());
            ordenCompraDetalle.setIdDetOrdenCompra(((OrdenCompraDetalleBean) event.getObject()).getIdDetOrdenCompra());
            ordenCompraDetalle.getFacturaCompraBean().setIdFacturaCompra(((OrdenCompraDetalleBean) event.getObject()).getFacturaCompraBean().getIdFacturaCompra());
            ordenCompraDetalleService.modificarFactura(ordenCompraDetalle);
            FacesMessage msg = new FacesMessage("Registro Modificado:", ((OrdenCompraDetalleBean) event.getObject()).getFacturaCompraBean().getSerieNroDoc());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
//            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detRegistroCompraService.obtenerUltimo(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorRegistro(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repRegistro.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<RegistroCompraRepBean> listRegistroCompraRepBean = new ArrayList<>();
            listRegistroCompraRepBean = registroCompraService.obtenerRegistroCompraCabecera(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!listRegistroCompraRepBean.isEmpty()) {
                List<RegistroCompraRepBean> listDetRegistroCompraRepBean = new ArrayList<>();
                listDetRegistroCompraRepBean = registroCompraService.obtenerRegistroCompraCabecera(registroCompraBean.getIdRegistroCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listRegistroCompraRepBean.get(0).setListaDetalle(listDetRegistroCompraRepBean);
                if (!listDetRegistroCompraRepBean.isEmpty()) {
                    for (int j = 0; j < listRegistroCompraRepBean.get(0).getListaDetalle().getData().size(); j++) {
                        List<DetRegistroCompraRepBean> listaDetRegistroCompraBean = new ArrayList<>();
                        listaDetRegistroCompraBean = detRegistroCompraService.obtenerDetFacturaCompra(new Integer(listRegistroCompraRepBean.get(j).getIdfacturacompra()), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listDetRegistroCompraRepBean.get(j).setListaDetalle2(listaDetRegistroCompraBean);
                        listRegistroCompraRepBean.get(0).setListaDetalle(listDetRegistroCompraRepBean);
                    }
                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listRegistroCompraRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
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
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            id = detRegistroCompraService.obtenerUltimo(registroCompraBean.getIdRegistroCompra());
            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerTodos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteRegistroCompraGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetRegistroCompraGeneral> listaDetRegistroCompraBean = new ArrayList<>();
            for (int i = 0; i < listaDetalleRegistroCompraBean.size(); i++) {
                DetRegistroCompraGeneral detRegistroCompraGeneral = new DetRegistroCompraGeneral();
                detRegistroCompraGeneral.setRuc(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getEntidadBean().getRuc());
                detRegistroCompraGeneral.setDescripcion(listaDetalleRegistroCompraBean.get(i).getDescripcion());
                detRegistroCompraGeneral.setRegCompra(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getIdRegistroCompra());
                detRegistroCompraGeneral.setCantidadDetOrden(listaDetalleRegistroCompraBean.get(i).getOrdenCompraDetalleBean().getCantidad());
                detRegistroCompraGeneral.setImporteDetOrden(listaDetalleRegistroCompraBean.get(i).getOrdenCompraDetalleBean().getImporte());
                detRegistroCompraGeneral.setDocumento(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getTipoDocBean().getCodigo());
                detRegistroCompraGeneral.setMoneda(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getTipoMonedaBean().getCodigo());
                detRegistroCompraGeneral.setItem(listaDetalleRegistroCompraBean.get(i).getCatalogoBean().getItem());
                detRegistroCompraGeneral.setCatalogo(listaDetalleRegistroCompraBean.get(i).getCatalogoBean().getIdCatalogo());
                detRegistroCompraGeneral.setOrdenCompra(listaDetalleRegistroCompraBean.get(i).getOrdenCompraBean().getIdOrdenCompra());
//                detRegistroCompraGeneral.setFechaorden(listaDetalleRegistroCompraBean.get(i).getOrdenCompraBean().getFechaOrden());
                detRegistroCompraGeneral.setNombreProveedor(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getEntidadBean().getNombre());
                detRegistroCompraGeneral.setMontoCadaUnoMate(listaDetalleRegistroCompraBean.get(i).getOrdenCompraBean().getMontoCadaUnoMate());
                detRegistroCompraGeneral.setNombreUniNeg(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                detRegistroCompraGeneral.setSerieNroDoc(listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().getSerieNroDoc());
                detRegistroCompraGeneral.setMontopago(listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().getMontoPago());
                detRegistroCompraGeneral.setGlosa(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getGlosa());
                detRegistroCompraGeneral.setImporte(listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().getImporte());
                detRegistroCompraGeneral.setValordetraccion(listaDetalleRegistroCompraBean.get(i).getFacturaCompraBean().getValorDetraccion());
                detRegistroCompraGeneral.setDireccionUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
                detRegistroCompraGeneral.setPaisUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
                detRegistroCompraGeneral.setDistritoUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                detRegistroCompraGeneral.setTelefonoUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
                detRegistroCompraGeneral.setCorreoUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
                detRegistroCompraGeneral.setWebUnidad(listaDetalleRegistroCompraBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
                detRegistroCompraGeneral.setMontoGeneralRegistro(listaDetalleRegistroCompraBean.get(i).getRegistroCompraBean().getMontoGeneralRegistro());
                detRegistroCompraGeneral.setTipoUniMed(listaDetalleRegistroCompraBean.get(i).getCatalogoBean().getTipoUnidadMedidaBean().getCodigo());

//                }
                listaDetRegistroCompraBean.add(detRegistroCompraGeneral);

            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetRegistroCompraBean);
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

    public void calcularImportePorItem(OrdenCompraDetalleBean bean) {
        try {

            if (ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (bean.getImporteAnterior());
                sumaImporte = sumaImporte + (bean.getImporte());
            } else//Material o Bien
            {
                sumaImporte = sumaImporte - (bean.getCantidadAnterior() * bean.getImporteAnterior());
                sumaImporte = sumaImporte + (bean.getCantidad() * bean.getImporte());
            }
            bean.setCantidadAnterior(bean.getCantidad());
            bean.setImporteAnterior(bean.getImporte());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularImporteTotal(RegistroCompraBean bean) {
        try {
            sumaTotal = sumaTotal + ((bean.getImporte() * bean.getIgv()) / 100);
            registroCompraBean.setTotal(sumaTotal);
            registroCompraBean.setMontoPago(sumaTotal);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItem(SolicitudLogDetalleBean bean) {
        try {

            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                //Aqui valido por el estado:Solo se agrega los items con estos estados
                if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                        || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                {
                    //Aqui valido por la cantidad a pedir
                    if ((bean.getCantidadSolicitada() + bean.getMaterialBean().getStockMin()) >= bean.getMaterialBean().getStockActual()) {
                        //Aqui es el calculo del item de la cantidad a pedir propuesta por el sistema
                        Integer cantProp = (bean.getCantidadSolicitada() - bean.getMaterialBean().getStockActual()) + bean.getMaterialBean().getStockMin();

                        //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                        if (!validarItemPorAgregar(bean)) {
                            //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                            ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                            ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                            ordenCompraDetalleBean.setCantidad(cantProp);
                            ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                            ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                            ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                            ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                            ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
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
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else// quiere decir que la solicitud es de tipo servicio
            {
                if (!validarItemPorAgregar(bean)) {
                    //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                    ordenCompraDetalleBean.setCantidad(0);
                    ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                    ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                    sumaImporte = sumaImporte + (bean.getCatalogoBean().getPrecioRef());
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }

            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaOrdenCompraDetalleBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItems() {
        try {
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();

                if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                        || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                    //Aqui valido por el estado:Solo se agrega los items con estos estados
                    if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals(""))// osea es aprobado pero todavia no revisado
                    {
                        //Aqui valido por la cantidad a pedir
                        if ((bean.getCantidadSolicitada() + bean.getMaterialBean().getStockMin()) >= bean.getMaterialBean().getStockActual()) {
                            //Aqui es el calculo del item de la cantidad a pedir propuesta por el sistema
                            Integer cantProp = (bean.getCantidadSolicitada() - bean.getMaterialBean().getStockActual()) + bean.getMaterialBean().getStockMin();

                            //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                            if (!validarItemPorAgregar(bean)) {
                                //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                                ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                                ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                                ordenCompraDetalleBean.setCantidad(cantProp);
                                ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                                ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                                ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                                ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
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
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else// quiere decir que la solicitud es de tipo servicio
                {
                    if (!validarItemPorAgregar(bean)) {
                        //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        ordenCompraDetalleBean.setCantidad(0);
                        ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                        ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                        sumaImporte = sumaImporte + (bean.getCatalogoBean().getPrecioRef());
                    }
                }

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
            //Asigno datos del la solicitud a la orden de compra si tiene como minimo 1 item
            if (listaOrdenCompraDetalleBean.size() >= 1) {
                this.asignarDatosSolParaOrdenCompra();
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
                // tipoOrdenBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Compra", new TipoCodigoBean("TipoOrdenCompra")));
            } else {
                //  tipoOrdenBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Servicio", new TipoCodigoBean("TipoOrdenCompra")));
            }

            //  ordenCompraBean.setTipoOrdenBean(tipoOrdenBean);//Paso el tipo de orden
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarItem(OrdenCompraDetalleBean bean) {
        try {
            if (ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (bean.getImporte());
            } else//sino es compra
            {
                sumaImporte = sumaImporte - (bean.getCantidad() * bean.getImporte());
            }
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

            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(bean.getIdRequerimiento(), bean.getUnidadNegocioBean().getUniNeg());
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

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
//            entidadSedeBean = entidadService.obtenerEntidadSedePorId(bean);

            //Paso los valores del bean del popup al bean de guardar
            ordenCompraBean.setEntidadBean(entidadBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Boolean validarFacturaPorAgregar(FacturaCompraBean bean) {
        Boolean valor = false;
        try {
            for (FacturaCompraBean detalle : listaFacturaCompraDocEgresoBean) {
                if (Objects.equals(detalle.getIdFacturaCompra(), bean.getIdFacturaCompra())) {
                    valor = true;
                    System.out.println("Objects.equals(detalle.getIdFacturaCompra(), bean.getIdFacturaCompra())");
                }
                if (!Objects.equals(detalle.getRegistroCompraBean().getEntidadBean().getRuc(), bean.getRegistroCompraBean().getEntidadBean().getRuc())) {
                    valor = true;
                    System.out.println("Objects.equals(detalle.getRegistroCompraBean().getEntidadBean().getRuc(), bean.getRegistroCompraBean().getEntidadBean().getRuc())");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void ponerRegistroCompraEnDocEgreso(FacturaCompraBean fact) {
        try {
            facturaCompraBean = (FacturaCompraBean) fact;
            DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");
            docEgresoMB.limpiarDocEgreso();
            docEgresoMB.validarTipoPago();
            System.out.println("");
            docEgresoMB.setFlgIdTipoDoc(false);
            docEgresoMB.getDocEgresoBean().setIdTipoDoc(0);
            docEgresoMB.getDocEgresoBean().getSolicitudCajaCHBean().setTipoMonedaBean(fact.getTipoMonedaBean());
            docEgresoMB.setOrigen(0);
            if (!validarFacturaPorAgregar(fact)) {
                listaFacturaCompraDocEgresoBean.add(facturaCompraBean);
                listaFacturaCompraBean.remove(facturaCompraBean);
            }
            docEgresoMB.getDocEgresoBean().getFacturaCompraBean().setRegistroCompraBean(facturaCompraBean.getRegistroCompraBean());
            docEgresoMB.getDocEgresoBean().setGlosa(facturaCompraBean.getGlosa());
            docEgresoMB.getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().setRuc(facturaCompraBean.getRucCompra());
            docEgresoMB.getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().setNombre(facturaCompraBean.getEntidadCompra());
            docEgresoMB.getDocEgresoBean().getTipoPagoBean().setIdCodigo(facturaCompraBean.getIdTipoModoPago());
            docEgresoMB.getDocEgresoBean().getTipoPagoBean().setCodigo(facturaCompraBean.getCodModoPago());
            docEgresoMB.getDocEgresoBean().getTipoMonedaBean().setIdCodigo(facturaCompraBean.getTipoMonedaBean().getIdCodigo());
            docEgresoMB.getDocEgresoBean().getTipoMonedaBean().setCodigo(facturaCompraBean.getTipoMonedaBean().getCodigo());
            docEgresoMB.getDocEgresoBean().getFacturaCompraBean().setIdFacturaCompra(facturaCompraBean.getIdFacturaCompra()); 
            docEgresoMB.setListaDocEgresoFacturaBean(listaFacturaCompraDocEgresoBean);
            docEgresoMB.obtenerCambio();
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("docEgresoMB", docEgresoMB);
            docEgresoMB.obtenerCuentaBancoPorTipMoneda();
            docEgresoMB.mostarTipoPago();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            System.out.println("ok :D");

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFacturaAutorizadoPorFiltro() {
        try {
            int estado = 0;
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            if (facturaCompraFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(facturaCompraFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                facturaCompraFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (facturaCompraFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(facturaCompraFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                facturaCompraFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (facturaCompraFiltroBean.getRegistroCompraBean().getIdRegistroCompra() != null && !facturaCompraFiltroBean.getRegistroCompraBean().getIdRegistroCompra().equals(0)) {
                facturaCompraFiltroBean.getRegistroCompraBean().setIdRegistroCompra(facturaCompraFiltroBean.getRegistroCompraBean().getIdRegistroCompra());
                estado = 1;
            }
            if (facturaCompraFiltroBean.getNroDoc() != null && !facturaCompraFiltroBean.getNroDoc().equals("")) {
                facturaCompraFiltroBean.setNroDoc(facturaCompraFiltroBean.getNroDoc().trim());
                estado = 1;
            }

            if (facturaCompraFiltroBean.getSerieDoc() != null && !facturaCompraFiltroBean.getSerieDoc().equals("")) {
                facturaCompraFiltroBean.setSerieDoc(facturaCompraFiltroBean.getSerieDoc());
                estado = 1;
            }
            if (facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getRuc() != null && !facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getRuc().equals("")) {
                facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().setRuc(facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getRuc());
                estado = 1;
            }
            if (facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getNombre() != null && !facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getNombre().equals("")) {
                facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().setNombre(facturaCompraFiltroBean.getRegistroCompraBean().getEntidadBean().getNombre());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaFacturaCompraBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaFacturaCompraBean = registroCompraService.obtenerFacturaAutorizadoPorFiltro(facturaCompraFiltroBean);
                if (listaFacturaCompraBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
            listaFacturaCompraBean = registroCompraService.obtenerFacturaAutorizadoPorFiltro(facturaCompraFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void monto() {
        try {
            System.out.println("monto: " + getCuentaFacturaBean().getValorCuenta());
            if (cuentaFacturaBean.getValorCuenta() != null && !cuentaFacturaBean.getValorCuenta().equals(0.0)) {
                if (listaCentroResponsabilidadBean.size() >= 1) {
                    distribuir();
                }
            } else {
                new MensajePrime().addInformativeMessagePer("No ingresó el importe, por favor ingrese.");
                facturaCompraBean.setListaDetRequerimientoCRBean(null);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarListaCr() {
        try {
            System.out.println("validarListaCr ");
            Integer estado = 0;
            montoTotalFact = new Double("0.0");

            if (!listaCuentaFacturaBean.isEmpty()) {
                for (CuentaFacturaBean lista : listaCuentaFacturaBean) {
                    Double monto = new Double("0.0");
                    monto = (double) Math.round((lista.getValor()) * 100) / 100;
                    montoTotalFact = montoTotalFact + monto;
                }
                System.out.println("montoTotalFact " + montoTotalFact + "--" + getFacturaCompraBean().getImporte());
                if (montoTotalFact.equals(getFacturaCompraBean().getImporte())) {
                    estado = 1;
                }
                if (estado.equals(1)) {
                    System.out.println("if (listaCuentaFacturaBean != null) ");
//                    for (CuentaFacturaBean lista : listaCuentasBean) {
//                        System.out.println("cuentassssss" + lista.getCentroResponsabilidadBean().getCr());
//                    } 
                    if (listaCuentaFacturaBean.size() > 0) {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionIncorrecta", true);
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjoMontoFAcIncorrecto");
                    RequestContext.getCurrentInstance().addCallbackParam("operacionIncorrecta", false);
                    System.out.println("El monto de la factura no es igual a la sumatoria de centros de responsabilidad");
                }
            } else {
                System.out.println("if (listaCuentaFacturaBean == null) ");
                RequestContext.getCurrentInstance().addCallbackParam("operacionIncorrecta", true);
            }
            System.out.println("monto: " + getCuentaFacturaBean().getValorCuenta());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void revertirModi() {
        try {
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            listaCuentasBean = registroCompraService.obtenerCuentaFactPorIdFactura(getFacturaCompraBean().getIdFacturaCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CuentaFacturaBean c : listaCuentasBean) {
                System.out.println("cargarDatosCrAprobacion idFact CRRRRRRRRRRRRRRRRRR " + c.getCentroResponsabilidadBean().getCr());
            }
            if (!listaCuentasBean.isEmpty()) {
                System.out.println("revirtiendo modi" + listaCuentasBean.size());
                //temporal si es caso revierte la modificacion
                listaCuentaFacturaBean = listaCuentasBean;
                facturaCompraBean = registroCompraService.obtenerPorIdFacturaVer2(facturaCompraBean);
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdFacturaVer2(FacturaCompraBean fact) {
        try {
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
            fact.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            facturaCompraBean = registroCompraService.obtenerPorIdFacturaVer2(fact);
            listaDetalleRegistroCompraBean = new ArrayList<>();
            listaDetalleRegistroCompraBean = detRegistroCompraService.obtenerPorFactura(fact);
            listaCuentaFacturaBean = registroCompraService.obtenerCuentaFactPorIdFactura(fact.getIdFacturaCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Centro de Responsabilidad
    public void distribuir() {
        try {
            System.out.println("monto: " + getCuentaFacturaBean().getValorCuenta());
            Double valor = 0.00;
            if (cuentaFacturaBean.getValorCuenta() != null && !cuentaFacturaBean.getValorCuenta().equals(0.0)) {
                if (facturaCompraBean.getImporte() != null) {
                    valor = (cuentaFacturaBean.getValorCuenta() + facturaCompraBean.getImporte());
                    if (valor > ordenCompraBean.getImportePropuesto()) {
                        new MensajePrime().addInformativeMessagePer("El importe sobrepasa el importe Propuesto en la Orden de Compra.");
                        facturaCompraBean.setListaDetRequerimientoCRBean(null);
                    } else {
                        Double monto = getCuentaFacturaBean().getValorCuenta();
                        System.out.println("monto: " + monto);
                        //1.-Mappear Dual a Lista
                        if (monto == null) {
                            System.out.println("null: " + monto);
                        } else {
//                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
                            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
                            for (CentroResponsabilidadBean list : listaCentroResponsabilidad) {
                                System.out.println(list.getIdTipoGrupoCR());
                            }
                            //2.-Invocar calculadora
                            for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                                if (facturaCompraBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                        facturaCompraBean.setMontoPago(monto);
                                        new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, monto);
                                        break;
                                    }
                                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                        facturaCompraBean.setMontoPago(monto);
                                        new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, monto);
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
                                detRequerimientoCRBean.setRegistroCompraBean(registroCompraBean);
                                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(facturaCompraBean.getCodDistribucion()));
                                detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                                detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                                listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
                            }
                            facturaCompraBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
                        }
                    }
                } else {
                    valor = cuentaFacturaBean.getValorCuenta();
                    if (valor > ordenCompraBean.getImportePropuesto()) {
                        new MensajePrime().addInformativeMessagePer("El importe sobrepasa el importe Propuesto en la Orden de Compra.");
                        facturaCompraBean.setListaDetRequerimientoCRBean(null);
                    } else {
                        Double monto = getCuentaFacturaBean().getValorCuenta();
                        System.out.println("monto: " + monto);
                        //1.-Mappear Dual a Lista
                        if (monto == null) {
                            System.out.println("null: " + monto);
                        } else {
//                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
                            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
                            for (CentroResponsabilidadBean list : listaCentroResponsabilidad) {
                                System.out.println(list.getIdTipoGrupoCR());
                            }
                            //2.-Invocar calculadora
                            for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                                if (facturaCompraBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                        facturaCompraBean.setMontoPago(monto);
                                        new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, monto);
                                        break;
                                    }
                                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                        facturaCompraBean.setMontoPago(monto);
                                        new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, monto);
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
                                detRequerimientoCRBean.setRegistroCompraBean(registroCompraBean);
                                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(facturaCompraBean.getCodDistribucion()));
                                detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                                detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                                listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
                            }
                            facturaCompraBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessagePer("No ingreso el importe, por favor ingrese.");
                facturaCompraBean.setListaDetRequerimientoCRBean(null);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void distribuirCuandoHayNotaC(FacturaCompraBean fact, Double monto) {
        try {
            RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
            listaCuentaFacturaBean = registroCompraService.obtenerCuentaFactNota(fact.getIdFacturaCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
             String distribuir = registroCompraService.obtenerCuentaDistribucion(fact.getIdFacturaCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("distribuir "+distribuir);
             List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
            List<CuentaFacturaBean> listaCuenta = new ArrayList<>();
            listaCuenta = registroCompraService.obtenerCuentaDistribucionCr(fact.getIdFacturaCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CuentaFacturaBean cue : listaCuenta) {
//                List<CentroResponsabilidadBean> listCentro = new ArrayList<>();
                CentroResponsabilidadBean c = new CentroResponsabilidadBean();
                c.setCr(cue.getCentroResponsabilidadBean().getCr());
                c.setNombre(cue.getCentroResponsabilidadBean().getNombre());
                c.setIdTipoGrupoCR(cue.getCentroResponsabilidadBean().getIdTipoGrupoCR());
                c.setTipoNivelCR(cue.getCentroResponsabilidadBean().getIdTipoGrupoCR());
                System.out.println("tipogrupo: "+c.getIdtipoCR());
                System.out.println("2222222222: "+c.getTipoNivelCR());
//                listCentro.add(c);
                listaCentroResponsabilidad.add(c);
                System.out.println("cantidad: "+listaCentroResponsabilidad.size());
            }
            Double montoA = monto;
            System.out.println("monto: " + montoA);
            for (CuentaFacturaBean cuenta : listaCuentaFacturaBean) {
                if (distribuir.equals(MaristaConstantes.TIP_DIST_PON)) {
                    cuenta.setValor(montoA);
                    new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, montoA);
                    break;
                }
                if (distribuir.equals(MaristaConstantes.TIP_DIST_DIV)) {
                    facturaCompraBean.setMontoPago(montoA);
                    new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, montoA);
                    break;
                }
                if (distribuir.equals(MaristaConstantes.TIP_DIST_PER)) {
                    break;
                }
            }
            for(CentroResponsabilidadBean resp : listaCentroResponsabilidad)
            {
                cuentaFacturaBean.setValor(resp.getMontoDistribucion());
                cuentaFacturaBean.getFacturaCompraBean().setIdFacturaCompra(fact.getIdFacturaCompra());
                cuentaFacturaBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cuentaFacturaBean.getCentroResponsabilidadBean().setCr(resp.getCr());
                registroCompraService.modificarCuentaFactValorNoC(cuentaFacturaBean.getValor(), cuentaFacturaBean.getFacturaCompraBean().getIdFacturaCompra(), cuentaFacturaBean.getUnidadNegocioBean().getUniNeg(),
                        cuentaFacturaBean.getCentroResponsabilidadBean().getCr());
            }
            
            //3.-Crear Lista Respuesta
//            List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
//            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
//                DetRegistroCompraCRBean detRequerimientoCRBean = new DetRegistroCompraCRBean();
//                detRequerimientoCRBean.setRegistroCompraBean(registroCompraBean);
//                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(facturaCompraBean.getCodDistribucion()));
//                detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
//                detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
//                listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
//            }
//            facturaCompraBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Centro de Responsabilidad
    public void distribuirAprobacion() {
        try {
            Double monto = getCuentaFacturaBean().getValorCuenta();
            System.out.println("monto: " + monto);
            //1.-Mappear Dual a Lista
            if (monto == null) {
                System.out.println("null: " + monto);
                new MensajePrime().addInformativeMessagePer("msjImpMayCero");
            } else {
                if (monto > 0) {
                    //                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                    List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
                    listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
                    for (CentroResponsabilidadBean list : listaCentroResponsabilidad) {
                        System.out.println(list.getIdTipoGrupoCR());
                    }
                    //2.-Invocar calculadora
                    for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                        if (facturaCompraBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                facturaCompraBean.setMontoPago(monto);
                                new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, monto);
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                facturaCompraBean.setMontoPago(monto);
                                new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, monto);
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
                        detRequerimientoCRBean.setRegistroCompraBean(registroCompraBean);
                        detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(facturaCompraBean.getCodDistribucion()));
                        detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                        detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                        listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
                    }
                    facturaCompraBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjImpMayCero");
                }
            }
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
        if (Objects.equals(sum, registroCompraBean.getImporte())) {
            return true;
        }
        return rpta;
    }

    //MÉTODOS GETTER AND SETTER
    public CodigoBean getTipoDocBean() {
        if (tipoDocBean == null) {
            tipoDocBean = new CodigoBean();
        }
        return tipoDocBean;
    }

    public void setTipoDocBean(CodigoBean tipoDocBean) {
        this.tipoDocBean = tipoDocBean;
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

    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean == null) {
            tipoCategoriaBean = new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
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

    public List<CodigoBean> getListaTipoMonedaBean() {
        if (listaTipoMonedaBean == null) {
            listaTipoMonedaBean = new ArrayList<>();
        }
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
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

    public UsuarioBean getUsuarioSessionBean() {
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
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

    public List<RegistroCompraBean> getListaRegistroCompraBean() {
        if (listaRegistroCompraBean == null) {
            listaRegistroCompraBean = new ArrayList<>();
        }
        return listaRegistroCompraBean;
    }

    public void setListaRegistroCompraBean(List<RegistroCompraBean> listaRegistroCompraBean) {
        this.listaRegistroCompraBean = listaRegistroCompraBean;
    }

    public List<DetRegistroCompraBean> getListaDetalleRegistroCompraBean() {
        if (listaDetalleRegistroCompraBean == null) {
            listaDetalleRegistroCompraBean = new ArrayList<>();
        }
        return listaDetalleRegistroCompraBean;
    }

    public void setListaDetalleRegistroCompraBean(List<DetRegistroCompraBean> listaDetalleRegistroCompraBean) {
        this.listaDetalleRegistroCompraBean = listaDetalleRegistroCompraBean;
    }

    public Double getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(Double sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public RegistroCompraBean getRegistroCompraFiltoBean() {
        if (registroCompraFiltoBean == null) {
            registroCompraFiltoBean = new RegistroCompraBean();
        }
        return registroCompraFiltoBean;
    }

    public void setRegistroCompraFiltoBean(RegistroCompraBean registroCompraFiltoBean) {
        this.registroCompraFiltoBean = registroCompraFiltoBean;
    }

    public DetRegistroCompraBean getDetRegistroCompraBean() {
        if (detRegistroCompraBean == null) {
            detRegistroCompraBean = new DetRegistroCompraBean();
        }
        return detRegistroCompraBean;
    }

    public void setDetRegistroCompraBean(DetRegistroCompraBean detRegistroCompraBean) {
        this.detRegistroCompraBean = detRegistroCompraBean;
    }

    public CodigoBean getTipoSolicitudBean() {
        if (tipoSolicitudBean == null) {
            tipoSolicitudBean = new CodigoBean();
        }
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(CodigoBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public List<TipoSolicitudBean> getListaTipoSolicitudBean() {
        return listaTipoSolicitudBean;
    }

    public void setListaTipoSolicitudBean(List<TipoSolicitudBean> listaTipoSolicitudBean) {
        this.listaTipoSolicitudBean = listaTipoSolicitudBean;
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

    public CodigoBean getTipoStatusRegCBean() {
        if (tipoStatusRegCBean == null) {
            tipoStatusRegCBean = new CodigoBean();
        }
        return tipoStatusRegCBean;
    }

    public void setTipoStatusRegCBean(CodigoBean tipoStatusRegCBean) {
        this.tipoStatusRegCBean = tipoStatusRegCBean;
    }

    public Integer getIdTipoStatusRegC() {
        return idTipoStatusRegC;
    }

    public void setIdTipoStatusRegC(Integer idTipoStatusRegC) {
        this.idTipoStatusRegC = idTipoStatusRegC;
    }

    public List<RegistroCompraBean> getListaRegistroCompraAutorizado() {
        if (listaRegistroCompraAutorizado == null) {
            listaRegistroCompraAutorizado = new ArrayList<>();
        }
        return listaRegistroCompraAutorizado;
    }

    public void setListaRegistroCompraAutorizado(List<RegistroCompraBean> listaRegistroCompraAutorizado) {
        this.listaRegistroCompraAutorizado = listaRegistroCompraAutorizado;
    }

    public List<CodigoBean> getListaTipoNumeroFactura() {
        if (listaTipoNumeroFactura == null) {
            listaTipoNumeroFactura = new ArrayList<>();
        }
        return listaTipoNumeroFactura;
    }

    public void setListaTipoNumeroFactura(List<CodigoBean> listaTipoNumeroFactura) {
        this.listaTipoNumeroFactura = listaTipoNumeroFactura;
    }

    public Boolean getFlgUnica() {
        return flgUnica;
    }

    public void setFlgUnica(Boolean flgUnica) {
        this.flgUnica = flgUnica;
    }

    public Boolean getFlgMultiple() {
        return flgMultiple;
    }

    public void setFlgMultiple(Boolean flgMultiple) {
        this.flgMultiple = flgMultiple;
    }

    public Boolean getFlgAgregar() {
        return flgAgregar;
    }

    public void setFlgAgregar(Boolean flgAgregar) {
        this.flgAgregar = flgAgregar;
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

    public List<FacturaCompraBean> getListaFacturaCompraBean() {
        if (listaFacturaCompraBean == null) {
            listaFacturaCompraBean = new ArrayList<>();
        }
        return listaFacturaCompraBean;
    }

    public void setListaFacturaCompraBean(List<FacturaCompraBean> listaFacturaCompraBean) {
        this.listaFacturaCompraBean = listaFacturaCompraBean;
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

    public List<DetraccionBean> getListaDetraccionBean() {
        if (listaDetraccionBean == null) {
            listaDetraccionBean = new ArrayList<>();
        }
        return listaDetraccionBean;
    }

    public void setListaDetraccionBean(List<DetraccionBean> listaDetraccionBean) {
        this.listaDetraccionBean = listaDetraccionBean;
    }

    public List<CodigoBean> getListTipoPrioridadFacturaBean() {
        if (listTipoPrioridadFacturaBean == null) {
            listTipoPrioridadFacturaBean = new ArrayList<>();
        }
        return listTipoPrioridadFacturaBean;
    }

    public void setListTipoPrioridadFacturaBean(List<CodigoBean> listTipoPrioridadFacturaBean) {
        this.listTipoPrioridadFacturaBean = listTipoPrioridadFacturaBean;
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

    public Boolean getMostrarDetraccion() {
        return mostrarDetraccion;
    }

    public void setMostrarDetraccion(Boolean mostrarDetraccion) {
        this.mostrarDetraccion = mostrarDetraccion;
    }

    public Double getMontoGeneralRegistro() {
        return montoGeneralRegistro;
    }

    public void setMontoGeneralRegistro(Double montoGeneralRegistro) {
        this.montoGeneralRegistro = montoGeneralRegistro;
    }

    public FacturaCompraBean getFacturaCompraFiltroBean() {
        if (facturaCompraFiltroBean == null) {
            facturaCompraFiltroBean = new FacturaCompraBean();
        }
        return facturaCompraFiltroBean;
    }

    public void setFacturaCompraFiltroBean(FacturaCompraBean facturaCompraFiltroBean) {
        this.facturaCompraFiltroBean = facturaCompraFiltroBean;
    }

    public List<FacturaCompraBean> getListaFacturaCompraDocEgresoBean() {
        if (listaFacturaCompraDocEgresoBean == null) {
            listaFacturaCompraDocEgresoBean = new ArrayList<>();
        }
        return listaFacturaCompraDocEgresoBean;
    }

    public void setListaFacturaCompraDocEgresoBean(List<FacturaCompraBean> listaFacturaCompraDocEgresoBean) {
        this.listaFacturaCompraDocEgresoBean = listaFacturaCompraDocEgresoBean;
    }

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
    }

    public List<DetRequerimientoCRBean> getListaRequerimientoCRBean() {

        return listaRequerimientoCRBean;
    }

    public void setListaRequerimientoCRBean(List<DetRequerimientoCRBean> listaRequerimientoCRBean) {
        this.listaRequerimientoCRBean = listaRequerimientoCRBean;
    }

    public Double getIgvResultado() {
        return igvResultado;
    }

    public void setIgvResultado(Double igvResultado) {
        this.igvResultado = igvResultado;
    }

    public DetRequerimientoCRBean getDetRequerimientoCRBean() {
        if (detRequerimientoCRBean == null) {
            detRequerimientoCRBean = new DetRequerimientoCRBean();
        }
        return detRequerimientoCRBean;
    }

    public void setDetRequerimientoCRBean(DetRequerimientoCRBean detRequerimientoCRBean) {
        this.detRequerimientoCRBean = detRequerimientoCRBean;
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

    public List<DetRequerimientoCRBean> getListaDetRequerimientoCRTopBean() {
        if (listaDetRequerimientoCRTopBean == null) {
            listaDetRequerimientoCRTopBean = new ArrayList<>();
        }
        return listaDetRequerimientoCRTopBean;
    }

    public void setListaDetRequerimientoCRTopBean(List<DetRequerimientoCRBean> listaDetRequerimientoCRTopBean) {
        this.listaDetRequerimientoCRTopBean = listaDetRequerimientoCRTopBean;
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

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanB() {
        if (listaCentroResponsabilidadBeanB == null) {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
        }
        return listaCentroResponsabilidadBeanB;
    }

    public void setListaCentroResponsabilidadBeanB(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB) {
        this.listaCentroResponsabilidadBeanB = listaCentroResponsabilidadBeanB;
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

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public Double getSumaFact() {
        return sumaFact;
    }

    public void setSumaFact(Double sumaFact) {
        this.sumaFact = sumaFact;
    }

    public List<DetRegistroCompraCRBean> getListaDetRegistroCompraCRBean() {
        if (listaDetRegistroCompraCRBean == null) {
            listaDetRegistroCompraCRBean = new ArrayList<>();
        }
        return listaDetRegistroCompraCRBean;
    }

    public void setListaDetRegistroCompraCRBean(List<DetRegistroCompraCRBean> listaDetRegistroCompraCRBean) {
        this.listaDetRegistroCompraCRBean = listaDetRegistroCompraCRBean;
    }

    public List<DetRegistroCompraCRBean> getListaDetRequerimientoCRBean() {
        return listaDetRequerimientoCRBean;
    }

    public void setListaDetRequerimientoCRBean(List<DetRegistroCompraCRBean> listaDetRequerimientoCRBean) {
        this.listaDetRequerimientoCRBean = listaDetRequerimientoCRBean;
    }

    public Integer getIdTipoDistribucion() {
        return idTipoDistribucion;
    }

    public void setIdTipoDistribucion(Integer idTipoDistribucion) {
        this.idTipoDistribucion = idTipoDistribucion;
    }

    public List<CodigoBean> getListaTipoPagoBean() {
        if (listaTipoPagoBean == null) {
            listaTipoPagoBean = new ArrayList();
        }
        return listaTipoPagoBean;
    }

    public void setListaTipoPagoBean(List<CodigoBean> listaTipoPagoBean) {
        this.listaTipoPagoBean = listaTipoPagoBean;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();

        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();

        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public List<ConceptoBean> getListaConceptoBean() {
        if (listaConceptoBean == null) {
            listaConceptoBean = new ArrayList<>();
        }
        return listaConceptoBean;
    }

    public void setListaConceptoBean(List<ConceptoBean> listaConceptoBean) {
        this.listaConceptoBean = listaConceptoBean;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public CuentaFacturaBean getCuentaFacturaBean() {
        if (cuentaFacturaBean == null) {
            cuentaFacturaBean = new CuentaFacturaBean();
        }
        return cuentaFacturaBean;
    }

    public void setCuentaFacturaBean(CuentaFacturaBean cuentaFacturaBean) {
        this.cuentaFacturaBean = cuentaFacturaBean;
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

    public Integer getNroFactAyuda() {
        return nroFactAyuda;
    }

    public void setNroFactAyuda(Integer nroFactAyuda) {
        this.nroFactAyuda = nroFactAyuda;
    }

    public List<CuentaFacturaBean> getListaCuentasBean() {
        if (listaCuentasBean == null) {
            listaCuentasBean = new ArrayList<>();
        }
        return listaCuentasBean;
    }

    public void setListaCuentasBean(List<CuentaFacturaBean> listaCuentasBean) {
        this.listaCuentasBean = listaCuentasBean;
    }

    public Double getMontoTotalFact() {
        return montoTotalFact;
    }

    public void setMontoTotalFact(Double montoTotalFact) {
        this.montoTotalFact = montoTotalFact;
    }

    public Boolean getFlgDetalleActivo() {
        return flgDetalleActivo;
    }

    public void setFlgDetalleActivo(Boolean flgDetalleActivo) {
        this.flgDetalleActivo = flgDetalleActivo;
    }

    public Boolean getFlgDoc() {
        return flgDoc;
    }

    public void setFlgDoc(Boolean flgDoc) {
        this.flgDoc = flgDoc;
    }

}
