package pe.marista.sigma.managedBean;

import java.awt.Dimension;
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
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.marista.sigma.MaristaConstantes;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.DetCotizacionBean;
import pe.marista.sigma.bean.DetRegistroCompraCRBean;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.OrdenCompraBean;
import pe.marista.sigma.bean.OrdenCompraDetalleBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.OrdenCompraDetalleService;
import pe.marista.sigma.service.OrdenCompraService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.reporte.ContratoRepBean;
import pe.marista.sigma.bean.reporte.DetContratoRepBean;
import pe.marista.sigma.bean.reporte.DetOrdenCompraRepBean;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CotizacionService;
import pe.marista.sigma.service.DetraccionService;

public class OrdenCompraServicioMB extends BaseMB implements Serializable {

    private List<OrdenCompraBean> listaOrdenCompraBean;
    private OrdenCompraBean ordenCompraBean;
    private OrdenCompraBean ordenCompraFiltroBean;
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
    private Double sumaImporte = 0.00;
    private Double montoCadaUnoSer = 0.00;
    private CodigoBean tipoCategoriaBean;
    private CodigoBean tipoPrioridadBean;
    private UsuarioBean usuarioSessionBean;
    private CodigoBean codigoBean;
    private List<SolicitudLogisticoBean> listaSolicitudServiciosBean;
    private List<SolicitudLogisticoBean> listaSolicitudActivosMaBean;
    private Double igvResultado = 0.00;
    private List<CodigoBean> listaTipoMonedaBean;
    private String montoTotal;
    private Double montoDetraccion = 0.00;
    private Double subTotal = 0.00;
    //Creacion por Campos de Adelanto
    private Boolean flgAdelanto;
    private Boolean flgGrabarAc = false;
    private Boolean flgDetalleActivo = false;
    private Integer idTipoDoc;
    private List<DetraccionBean> listaDetraccionBean;
    private List<FacturaCompraBean> listaSesionFacturaCompraBean;
    private DetRegistroCompraCRBean detRegistroCompraCRBean;

    //Ayuda
    private Boolean flgTransporte = false;

    private FacturaCompraBean facturaSessionBean;

    //entidad
    private List<ViewEntidadBean> listaViewEntidadProveedorBean;
    private Integer idTipoFormaPago;

    private CodigoBean tipoDoc;
    private List<CodigoBean> listaTipoDocBean;
    //ayuda Cotizaciones
    private DetCotizacionBean detCotizacionBean;

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
        if (listaTipoFormaPagoBean == null) {
            listaTipoFormaPagoBean = new ArrayList<>();
        }
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
    public void ordenCompraServicioMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaOrdenCompraDetalleBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getSolicitudLogisticoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerTodosCompra(solicitudLogisticoBean);

            OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
//            listaOrdenCompraBean = ordenCompraService.obtenerTodosPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            EntidadService entidadService = BeanFactory.getEntidadService();
//          listaEntidadSedeBean = entidadService.obtenerProveedorPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()); 

//            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getEntidadBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);
            listaViewEntidadProveedorBean = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_PROVEEDOR, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoFormaPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoModoPago"));
            listaTipoPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPago"));
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));

            String idOrden = null;
            idOrden = ordenCompraService.obtenerUltimoOrden(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idOrden.equals(null)) {
                getOrdenCompraBean().setNroCompra(idOrden);
            }
//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoBean);
//            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosSolDes(solicitudLogisticoBean);

            for (CodigoBean bean : listaTipoFormaPagoBean) {
                if (bean.getCodigo().equals("Cheque")) {
                    getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoFormaPago = bean.getIdCodigo();
                }
            }

            this.cargarDatosSession();
            getOrdenCompraFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Boolean getFlgTransporte() {
        return flgTransporte;
    }

    public void setFlgTransporte(Boolean flgTransporte) {
        this.flgTransporte = flgTransporte;
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

    public Boolean getFlgGrabarAc() {
        return flgGrabarAc;
    }

    public void setFlgGrabarAc(Boolean flgGrabarAc) {
        this.flgGrabarAc = flgGrabarAc;
    }

    public Boolean getFlgDetalleActivo() {
        return flgDetalleActivo;
    }

    public void setFlgDetalleActivo(Boolean flgDetalleActivo) {
        this.flgDetalleActivo = flgDetalleActivo;
    }

    public class Ventana extends JFrame {

        private Dimension dim;

        public Ventana() {
            //con esto obtienes en tamano en en x y y de tu monitor
            dim = super.getToolkit().getScreenSize();
            super.setSize(dim);
            super.setUndecorated(true);
            super.setVisible(true);
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
//            facturaSessionBean = new FacturaCompraBean();
            obtenerIgv();
            detraccion = detraccionService.obtenerPorId(facturaSessionBean.getDetraccionBean());
            montoDetraccion = (facturaSessionBean.getImpuesto()) * (detraccion.getValor() / 100);
            montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
            facturaSessionBean.setMontoPago((facturaSessionBean.getImpuesto() - montoDetraccion));
            facturaSessionBean.getDetraccionBean().setValor(detraccion.getValor());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarSolicitudesServicio() {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            listaSolicitudServiciosBean = solicitudLogisticoService.obtenerTodosSolDes(solicitudLogisticoBean);
            listaSolicitudLogDetalleBean = new ArrayList<>();
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
            String idOrden = null;
            idOrden = ordenCompraService.obtenerUltimoOrden(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idOrden.equals(null)) {
                getOrdenCompraBean().setNroCompra(idOrden);
            }
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

    public void rowSelect(SelectEvent event) {
        try {

            sumaImporte = 0.00;
            montoCadaUnoSer = 0.00;
            EntidadService entidadService = BeanFactory.getEntidadService();
            OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();

            ordenCompraBean = (OrdenCompraBean) event.getObject();
            //obtengo el proveedor correspondiente
            entidadBean = entidadService.obtenerEntidadPorId(ordenCompraBean.getEntidadBean());
            ordenCompraBean.setEntidadBean(entidadBean);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoCadaUnoSer = ordenCompraBean.getMontoCadaUnoSer();

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
            EntidadService entidadService = BeanFactory.getEntidadService();
            bean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean = new OrdenCompraBean();
            sumaImporte = 0.00;
            montoCadaUnoSer = 0.00;

            ordenCompraBean = ordenCompraService.obtenerPorId(bean);
            //obtengo el proveedor correspondiente
            EntidadBean entidad = new EntidadBean();
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            entidad.setRuc(ordenCompraBean.getEntidadBean().getRuc());
            entidad.setNombre(ordenCompraBean.getEntidadBean().getNombre());
            entidad.setDireccion(ordenCompraBean.getEntidadBean().getDireccion());
//            entidadBean = entidadService.obtenerEntidadPorIdCot(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ordenCompraBean.setEntidadBean(entidad);
            //obtengo el detalle de la orden de compra
            listaOrdenCompraDetalleBean = ordenCompraDetalleService.obtenerPorOrden(ordenCompraBean.getIdOrdenCompra(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaImporte = ordenCompraBean.getMontoRef();
            montoCadaUnoSer = ordenCompraBean.getMontoCadaUnoSer();
            if (ordenCompraBean.getFlgAdelanto() != null) {
                if (ordenCompraBean.getFlgAdelanto()) {
                    this.flgAdelanto = true;
                } else {
                    this.flgAdelanto = false;
                }
            }
            getSolicitudLogDetalleBean();
            OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();
            ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(solicitudLogDetalleBean.getIdDetRequerimiento());
            if (solicitudLogDetalleBean.getConceptoUniNegBean() != null) {
                System.out.println("concepto uni neg");
                SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
                Integer idReq = listaOrdenCompraDetalleBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento();
                String uniNeg = usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
                System.out.println("concepto uni neg" + listaOrdenCompraDetalleBean.get(0).getSolicitudLogisticoBean().getIdRequerimiento());
                System.out.println("concepto uni neg" + listaOrdenCompraDetalleBean.get(0).getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getIdRequerimiento());
                listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(idReq, uniNeg);

                ordenCompraDetalleBean.getSolicitudLogDetalleBean().setConceptoUniNegBean(listaSolicitudLogDetalleBean.get(0).getConceptoUniNegBean());
            }
            System.out.println("idtipp " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
//            if (ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() == MaristaConstantes.id_Transporte) {
//                ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().setNombre(MaristaConstantes.transporte);
//
//                switch (ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getNombre()) {
//                    case "Transporte correos y gastos de viaje":
//                        this.flgTransporte = true;
//                        break;
//                    default:
//                        this.flgTransporte = false;
//
//                }
//                System.out.println("flg" + flgTransporte);
//            } else {
//                this.flgTransporte = false;
//                System.out.println("flg2" + flgTransporte);
//            } 
//            if (listaOrdenCompraDetalleBean.get(0).getCatalogoBean().getItem().equals(MaristaConstantes.servicioTransporte) || listaOrdenCompraDetalleBean.get(0).getCatalogoBean().getItem().equals(MaristaConstantes.transporte)) {
//                switch (listaOrdenCompraDetalleBean.get(0).getCatalogoBean().getItem()) {
//                    case "Servicio Transporte":
//                        this.flgTransporte = true;
//                        break;
//                    case "Transporte":
//                        this.flgTransporte = true;
//                        break;
//                    default:
//                        this.flgTransporte = false;
//
//                }
//                System.out.println("flg" + flgTransporte);
//            } else {
//                this.flgTransporte = false;
//                System.out.println("flg2" + flgTransporte);
//            }
            if (listaOrdenCompraDetalleBean.get(0).getSolicitudLogisticoBean().getFlgTransporte() == true) {
                this.flgTransporte = true;
            } else {
                this.flgTransporte = false;
            }
            this.flgGrabarAc=true;
            System.out.println("s" + solicitudLogDetalleBean.getSolicitudLogisticoBean().getFlgTransporte());
            //fechaOrdenView = formato.format(ordenCompraBean.getFechaOrden()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

    public void obtenerPorFiltroSolServicios() {
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

            ordenCompraFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaOrdenCompraBean = ordenCompraService.obtenerTodosSolDes(ordenCompraFiltroBean);
//            listaOrdenCompraBean = ordenCompraService.obtenerPorFiltro(ordenCompraFiltroBean);

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
                OrdenCompraService ordenCompraService = BeanFactory.getOrdenCompraService();
                OrdenCompraDetalleService ordenCompraDetalleService = BeanFactory.getOrdenCompraDetalleService();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
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
                        && ordenCompraBean.getEntidadBean().getRuc() != null && !ordenCompraBean.getEntidadBean().getRuc().equals("")
                        && ordenCompraBean.getEntidadBean().getNombre() != null && !ordenCompraBean.getEntidadBean().getNombre().equals("")) {
                    if (ordenCompraBean.getIdOrdenCompra() == null) {
                        ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                        ordenCompraBean.getTipoStatusRegCBean().setIdCodigo(MaristaConstantes.COD_COMPRADO_REGISTRO_COMPRA);
                        solicitudLogisticoBean.getTipoEstadoBean().setCodigo(MaristaConstantes.COD_Comprado_Parcial);
                        solicitudLogisticoBean.getTipoEstadoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REQ);
                        ordenCompraService.insertar(ordenCompraBean, solicitudLogisticoBean, listaSesionFacturaCompraBean, detRegistroCompraCRBean);
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
                        System.out.println("ok");
                        this.flgGrabarAc = true;
                    } else {
//                    ordenCompraBean.setMontoRef(sumaImporte);
                        ordenCompraBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        ordenCompraBean.setMontoCadaUnoSer(montoCadaUnoSer);
                        ordenCompraService.modificar(ordenCompraBean);
                        ordenCompraBean.setCreaPor(usuarioSessionBean.getUsuario());
                        ordenCompraDetalleService.eliminar(ordenCompraBean);
                        ordenCompraDetalleService.insertar(ordenCompraBean.getIdOrdenCompra(), ordenCompraBean.getAnio(), listaOrdenCompraDetalleBean);
                    }
                    for (CodigoBean bean : listaTipoFormaPagoBean) {
                        if (bean.getCodigo().equals("Cheque")) {
                            getOrdenCompraBean().getTipoFormaPagoBean().setIdCodigo(bean.getIdCodigo());
                            this.idTipoFormaPago = bean.getIdCodigo();
                        }
                    }
                    listaOrdenCompraBean = ordenCompraService.obtenerTodosSolDes(ordenCompraFiltroBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("Llene todos los campos requeridoss");
                    this.flgGrabarAc = false;
                }
//                this.limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

//    public void cargarMonto() {
//        Double m = 0d;
//        m = ordenCompraBean.getImporteAdelanto();
//        ponerMontoAdelantoEnFact(m);
//    }
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

//    public void ponerMontoAdelantoEnFact(Double montoAdelanto) {
//        try {
//
//            RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
//
//            registroCompraMB.getFacturaCompraBean().setImporte(montoAdelanto);
//
//            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("registroCompraMB", registroCompraMB);
//            registroCompraMB.cargarListasFacturaOrden();
//            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            System.out.println("ok xd");
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
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

    public void imprimirTodosPdf() {
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
            String archivoJasper = "";
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompra.jasper");
            } else {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompraServicio.jasper");
            }
//            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteOrdenCompraServicio.jasper");
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

    public void imprimirTodosPdfServicio() {
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
                    getRequest()).getServletContext().getRealPath("/reportes/reporteServicio.jasper");
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
                    getRequest()).getServletContext().getRealPath("/reportes/reporteContratoServicio.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<ContratoRepBean> listaDetContratoBean = new ArrayList<>();
            listaDetContratoBean = ordenCompraDetalleService.obtenerContrato(ordenCompraBean.getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());

            List<DetContratoRepBean> listaDetDetContratoBean = new ArrayList<>();
            listaDetDetContratoBean = ordenCompraDetalleService.obtenerDetContrato(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ordenCompraBean.getIdOrdenCompra());
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

                    //Aqui valido por la cantidad a pedir
//                    if ((bean.getCantidadSolicitada() + bean.getMaterialBean().getStockMin()) >= bean.getMaterialBean().getStockActual()) {
//                        //Aqui es el calculo del item de la cantidad a pedir propuesta por el sistema
//                        Integer cantProp = (bean.getCantidadSolicitada() - bean.getMaterialBean().getStockActual()) + bean.getMaterialBean().getStockMin();
                    cantProp = (bean.getCantidadSolicitada());
                    //Si es false, quiere decir que no ha sido agregado ese item seleccionado
                    if (!validarItemPorAgregar(bean)) {
                        //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        ordenCompraDetalleBean.setCantidadAnterior(cantProp);
                        ordenCompraDetalleBean.setCantidad(cantProp);
                        ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoSer);
                        ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                        ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFlgComprado(true);
                        bean.setFlgComprado(true);
                        listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                        sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                    } else //si existe se suma la cantidad de los mismos items pero de distintas solicitudes
                    {
                        for (OrdenCompraDetalleBean detalle : listaOrdenCompraDetalleBean) {
                            if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                                //Si es el mismo item(idCatalogo) pero de distinta solicitud, se suma las cantidades
                                if (!Objects.equals(detalle.getSolicitudLogisticoBean().getIdRequerimiento(), bean.getSolicitudLogisticoBean().getIdRequerimiento())) {
                                    detalle.setCantidad(detalle.getCantidad());
                                    sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                                }
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
                        ordenCompraBean.setImportePropuesto(sumaImporte);
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
            } else// quiere decir que la solicitud es de tipo servicio
            {
                if (!validarItemPorAgregar(bean)) {
                    //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                    ordenCompraDetalleBean.setCantidad(bean.getCantidadSolicitada());
                    ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoSer);
                    ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                    ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                    ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFechaSalida(bean.getFechaSalida());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setHoraSalida(bean.getHoraSalida());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setHoraRegreso(bean.getHoraRegreso());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setDestinoServicio(bean.getDestinoServicio());
                    ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFlgComprado(true);
                    bean.setFlgComprado(true);
                    listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                    sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                }
                CotizacionBean coti = new CotizacionBean();
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                Integer idrequerimiento = bean.getIdDetRequerimiento();
                System.out.println("idRequerimiento: " + idrequerimiento);
                System.out.println("is2: " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getIdRequerimiento());
                System.out.println("id3: " + ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento());
                coti = cotizacionService.obtenerRucPorReq(idrequerimiento, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println("coti" + coti);
                if (coti != null) {
                    if (coti != null && sumaImporte != 0) {
                        ordenCompraBean.setImportePropuesto(sumaImporte);
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
                //                if (ordenCompraDetalleBean.getSolicitudLogisticoBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto()== MaristaConstantes.id_Transporte) {
                //                ordenCompraDetalleBean.getSolicitudLogisticoBean().getConceptoBean().getTipoConceptoBean().setNombre(MaristaConstantes.transporte);
                //
                //                switch (ordenCompraDetalleBean.getSolicitudLogisticoBean().getConceptoBean().getTipoConceptoBean().getNombre()) {
                //                    case "Transporte correos y gastos de viaje":
                //                        this.flgTransporte = true;
                //                        break;
                //
                //                }
                //            }
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
        try {
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                OrdenCompraDetalleBean ordenCompraDetalleBean = new OrdenCompraDetalleBean();
//                ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
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
                                ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoSer);
                                ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                                ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                                ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                                ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                                ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                                ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
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
                    System.out.println("aquí 1");
                    CotizacionBean coti = new CotizacionBean();
//                    SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                    coti = cotizacionService.obtenerRucPorReq(ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    System.out.println("coti" + coti);
                    if (coti != null) {
                        if (coti != null && sumaImporte != 0) {
                            sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
                            ordenCompraBean.setImportePropuesto(sumaImporte + coti.getIgvCoti());
                            ordenCompraBean.setNroCotiPro(coti.getNroCotiPro());
                            ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
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
                } else// quiere decir que la solicitud es de tipo servicio
                {
                    if (!validarItemPorAgregar(bean)) {
                        //ordenCompraDetalleBean.setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.setSolicitudLogisticoBean(bean.getSolicitudLogisticoBean());
                        ordenCompraDetalleBean.setCantidad(bean.getCantidadSolicitada());
                        ordenCompraDetalleBean.getOrdenCompraBean().setMontoCadaUnoMate(montoCadaUnoSer);
                        ordenCompraDetalleBean.setImporteAnterior(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setImporte(bean.getCatalogoBean().getPrecioRef());
                        ordenCompraDetalleBean.setCatalogoBean(bean.getCatalogoBean());
                        ordenCompraDetalleBean.getSolicitudLogisticoBean().setIdRequerimiento(bean.getSolicitudLogisticoBean().getIdRequerimiento());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setIdDetRequerimiento(bean.getIdDetRequerimiento());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setFechaSalida(bean.getFechaSalida());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setHoraSalida(bean.getHoraSalida());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setHoraRegreso(bean.getHoraRegreso());
                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().setDestinoServicio(bean.getDestinoServicio());
                        ordenCompraDetalleBean.setValidarCatalogo(bean.getValidarCatalogo());
//                        ordenCompraDetalleBean.getSolicitudLogDetalleBean().getSolicitudLogisticoBean().setFlgTransporte(bean.getSolicitudLogisticoBean().getFlgTransporte());
                        listaOrdenCompraDetalleBean.add(ordenCompraDetalleBean);
                        sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                    }
                }
                CotizacionBean coti = new CotizacionBean();
//                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                Integer idrequerimiento = ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento();
                System.out.println("idRequerimiento: " + idrequerimiento);
                System.out.println("is2: " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getIdRequerimiento());
                System.out.println("id3: " + ordenCompraDetalleBean.getSolicitudLogisticoBean().getIdRequerimiento());
                coti = cotizacionService.obtenerRucPorReq(idrequerimiento, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println("coti" + coti);
                if (coti != null) {
                    if (coti != null && sumaImporte != 0) {
                        System.out.println("entro");
                        sumaImporte = (double) Math.round(sumaImporte * 100) / 100;
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
                        ordenCompraBean.getEntidadBean().setRuc(coti.getEntidadBean().getRuc());
                        ordenCompraBean.getEntidadBean().setDireccion(coti.getEntidadBean().getDireccion());
                        ordenCompraBean.getEntidadBean().setNombre(coti.getEntidadBean().getNombre());
                        ordenCompraBean.getCotizacionBean().setIdCotizacion(coti.getIdCotizacion());
                    } else {
                        ordenCompraBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        ordenCompraBean.setNroCotiPro(ordenCompraBean.getNroCotiPro());
                    }
                }

                System.out.println("nroCoti: " + ordenCompraBean.getNroCotiPro());
                System.out.println("Id Cotizacion: " + ordenCompraBean.getCotizacionBean().getIdCotizacion());
                System.out.println("oc imp pro " + ordenCompraBean.getImportePropuesto());
//                ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
                if (bean.getConceptoUniNegBean() != null) {
                    System.out.println("concepto uni neg");
                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().setConceptoUniNegBean(bean.getConceptoUniNegBean());
                }
                System.out.println("idtipp " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
//                if (ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() == MaristaConstantes.id_Transporte) {
//                    ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().setNombre(MaristaConstantes.transporte);
//
//                    switch (ordenCompraDetalleBean.getSolicitudLogDetalleBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getNombre()) {
//                        case "Transporte correos y gastos de viaje":
//                            this.flgTransporte = true;
//                            break;
//                        default:
//                            this.flgTransporte = false;
//
//                    }
//                    System.out.println("flg" + flgTransporte);
//                } else {
//                    this.flgTransporte = false;
//                    System.out.println("flg2" + flgTransporte);
//                }

//                if (ordenCompraDetalleBean.getCatalogoBean().getItem().equals(MaristaConstantes.servicioTransporte) || ordenCompraDetalleBean.getCatalogoBean().getItem().equals(MaristaConstantes.transporte)) {
//                    switch (ordenCompraDetalleBean.getCatalogoBean().getItem()) {
//                        case "Servicio Transporte":
//                            this.flgTransporte = true;
//                            break;
//                        case "Transporte":
//                            this.flgTransporte = true;
//                            break;
//                        default:
//                            this.flgTransporte = false;
//
//                    }
//                    System.out.println("flg" + flgTransporte);
//                } else {
//                    this.flgTransporte = false;
//                    System.out.println("flg2" + flgTransporte);
//                }
                if (solicitudLogisticoBean.getFlgTransporte() == true) {
                    this.flgTransporte = true;
                } else {
                    this.flgTransporte = false;
                }
                System.out.println("transporte: " + ordenCompraDetalleBean.getSolicitudLogDetalleBean().getSolicitudLogisticoBean().getFlgTransporte());
                System.out.println("aquí 2");
//                ordenCompraBean.setNroCompra(bean.getSolicitudLogisticoBean().getNroSolicitud());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                sumaImporte = (double) Math.round(sumaImporte * 100) / 100;

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

    public void quitarItem(OrdenCompraDetalleBean bean) {
        try {

            sumaImporte = sumaImporte - (bean.getImporte());
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
            CotizacionService cotizacionService = BeanFactory.getCotizacionService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(bean.getIdRequerimiento(), bean.getUnidadNegocioBean().getUniNeg());
            System.out.println("ip: " + solicitudLogisticoBean.getImportePropuesto());
//obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitudCompra(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

            ordenCompraBean.getTipoCategoriaBean().setIdCodigo(bean.getTipoCategoriaBean().getIdCodigo());
            ordenCompraBean.getTipoPrioridadBean().setIdCodigo(bean.getTipoPrioridadBean().getIdCodigo());
            ordenCompraBean.setObs(bean.getTitulo());
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
            if (ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Servicio")
                    || ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || ordenCompraBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);

            }

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

    public Double getMontoCadaUnoSer() {
        return montoCadaUnoSer;
    }

    public void setMontoCadaUnoSer(Double montoCadaUnoSer) {
        this.montoCadaUnoSer = montoCadaUnoSer;
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

    public UsuarioBean getUsuarioSessionBean() {
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

    public Boolean getFlgAdelanto() {
        return flgAdelanto;
    }

    public void setFlgAdelanto(Boolean flgAdelanto) {
        this.flgAdelanto = flgAdelanto;
    }

    public Integer getIdTipoFormaPago() {
        return idTipoFormaPago;
    }

    public void setIdTipoFormaPago(Integer idTipoFormaPago) {
        this.idTipoFormaPago = idTipoFormaPago;
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

    public Double getIgvResultado() {
        return igvResultado;
    }

    public void setIgvResultado(Double igvResultado) {
        this.igvResultado = igvResultado;
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

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
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

    public List<DetraccionBean> getListaDetraccionBean() {
        if (listaDetraccionBean == null) {
            listaDetraccionBean = new ArrayList<>();
        }
        return listaDetraccionBean;
    }

    public void setListaDetraccionBean(List<DetraccionBean> listaDetraccionBean) {
        this.listaDetraccionBean = listaDetraccionBean;
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

    public Integer getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(Integer idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
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

    public DetRegistroCompraCRBean getDetRegistroCompraCRBean() {
        if (detRegistroCompraCRBean == null) {
            detRegistroCompraCRBean = new DetRegistroCompraCRBean();
        }
        return detRegistroCompraCRBean;
    }

    public void setDetRegistroCompraCRBean(DetRegistroCompraCRBean detRegistroCompraCRBean) {
        this.detRegistroCompraCRBean = detRegistroCompraCRBean;
    }

}
