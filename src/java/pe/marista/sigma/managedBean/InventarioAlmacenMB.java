package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
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
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.InventarioAlmacenService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.InventarioAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenRepBean;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EntidadService;

public class InventarioAlmacenMB extends BaseMB implements Serializable {

    private List<InventarioAlmacenBean> listaInventarioAlmacenBean;
    private List<CatalogoBean> listaCatalogoBean;
    private CatalogoBean catalogoBean;
    private List<CodigoBean> listaTipoUniMed;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private InventarioAlmacenBean inventarioAlmacenBean;
    private UnidadNegocioBean unidadNegocioBean;
    private Double Precio = 0.0;
    private List<EntidadBean> listaEntidadBean;
    private EntidadBean entidadBean;
    private UsuarioBean usuarioSessionBean;
    private CodigoBean codigoBean;
    private List<CodigoBean> listaTipoUnidadMedidaBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private InventarioAlmacenBean inventarioAlmacenFiltroBean;
    private Calendar fechaActual;

    private MovimientoAlmacenBean movimientoAlmacenBean;
    private List<MovimientoAlmacenBean> listaMovimientoAlmacenBean;
    private Boolean flgFiltroPersonal = false;
    private Boolean flgFiltroPersona = false;
    private Boolean flgFiltroProve = false;
    private Boolean flgSoli = true;
    private Boolean flgIgualSoli = false;
    private String idTipoSol;
    private String idTipoRespCheque;
    private Integer idSolicitud;
    private Boolean flgGestorSoli = false;
    private Calendar fechaSolicitud;
    private Integer StockAntiguo = 0;

    private Integer idTipoUniMed;
    private Integer idTipoMoneda;

    //CAMBIO A PROBAR
    private List<InventarioAlmacenBean> listaColorAlmacen;
    private InventarioActivoBean inventarioActivoBean;
    private CatalogoBean catalogoFiltroBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private Boolean flgInvAlm;
    private Boolean flgInvAct;
    private Boolean flgServ;

    @PostConstruct
    public void InventarioAlmacenMB() {
        Integer id = 0;
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            listaInventarioAlmacenBean = new ArrayList<>();
            obtenerTodosInventario();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
//            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            listaTipoUnidadMedidaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUnidadMedida"));
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            getListaCatalogoBean();
            getListaEntidadBean();
            listaCatalogoBean = catalogoService.obtenerTodos();
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getInventarioAlmacenFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario();
            for (CodigoBean bean : listaTipoUnidadMedidaBean) {
                if (bean.getCodigo().equals("Unidad")) {
                    getInventarioAlmacenBean().getTipoUniMedBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
            for (CodigoBean bean : listaTipoMonedaBean) {
                if (bean.getCodigo().equals("Soles")) {
                    getInventarioAlmacenBean().getCatalogoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
            getMovimientoAlmacenBean().setEntregadoPor(usuarioSessionBean.getPersonalBean().getNombreCompleto());
            getMovimientoAlmacenBean().setPersonalBean(usuarioSessionBean.getPersonalBean());
            listaCatalogoBean = catalogoService.obtenerMateriales(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCatalogoBean();
            this.fechaSolicitud = new GregorianCalendar();
            getMovimientoAlmacenBean().setFechaMov(fechaSolicitud.getTime());
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            String idInventario = null;
            idInventario = inventarioAlmacenService.obtenerUltimo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idInventario.equals(null)) {
                getMovimientoAlmacenBean().setIdMovimientoAyuda(idInventario);
            }
            //CAMBIO A PROBAR
            getListaColorAlmacen();
            getCatalogoFiltroBean();
            getInventarioActivoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void obtenerCatalogoPorId(CatalogoBean bean) {
//        try {
//            getCatalogoBean();
//            CatalogoService catalogoService = BeanFactory.getCatalogoService();
//            catalogoBean = catalogoService.obtenerCatalogoPorId(bean);
//            inventarioAlmacenBean.setCatalogoBean(catalogoBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void ponerCatalogo(Object object) {
        try {
            CatalogoBean carDep = (CatalogoBean) object;
            inventarioAlmacenBean.setCatalogoBean(carDep);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<InventarioAlmacenBean> getListaInventarioAlmacenBean() {
        if (listaInventarioAlmacenBean == null) {
            listaInventarioAlmacenBean = new ArrayList<>();
        }
        return listaInventarioAlmacenBean;
    }

    public void setListaInventarioAlmacenBean(List<InventarioAlmacenBean> listaInventarioAlmacenBean) {
        this.listaInventarioAlmacenBean = listaInventarioAlmacenBean;
    }

    public List<CatalogoBean> getListaCatalogoBean() {
        if (listaCatalogoBean == null) {
            listaCatalogoBean = new ArrayList<>();
        }
        return listaCatalogoBean;
    }

    public void setListaCatalogoBean(List<CatalogoBean> listaCatalogoBean) {
        this.listaCatalogoBean = listaCatalogoBean;
    }

    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null) {
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
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

    public List<CodigoBean> getListaTipoUniMed() {
        return listaTipoUniMed;
    }

    public void setListaTipoUniMed(List<CodigoBean> listaTipoUniMed) {
        this.listaTipoUniMed = listaTipoUniMed;
    }

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if (inventarioAlmacenBean == null) {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
    }

    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

//    @PostConstruct
//    public void cargaDatos()
//    {
//        try
//        {
////            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//            getInventarioAlmacenBean().setStockActual(Float.NaN);
//            getInventarioAlmacenBean().setStockMax(Float.NaN);
//            getInventarioAlmacenBean().setStockMin(Float.NaN);
//            getInventarioAlmacenBean().setCatalogoBean(catalogoBean);
//            
//        }catch(Exception e){
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//        
//    }
    //----- Metodos Inventario Almacen ------
    public void obtenerPorId(InventarioActivoBean inventarioActivoBean) {
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            inventarioAlmacenBean = new InventarioAlmacenBean();
            inventarioAlmacenBean = inventarioAlmacenService.obtenerPorId(inventarioAlmacenBean.getIdAlmacen());

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //   public void obtenerCatalogoPorId(CatalogoBean bean) {
    //     try {
    //       CatalogoService catalogoService = BeanFactory.getCatalogoService();
//            catalogoBean = catalogoService.buscarPorId(bean.getItem());
    // } catch (Exception ex) {
    //   new MensajePrime().addErrorGeneralMessage();
    // GLTLog.writeError(this.getClass(), ex);
    //}
    //}
    public void obtenerEntidadPorId(EntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidad = new EntidadBean();

            entidad.setRuc(bean.getRuc());
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

            entidadBean = entidadService.obtenerEntidadPorId(entidad);
            //entidadBean = entidadService.buscarPorId(bean.getRuc());
            inventarioAlmacenBean.getCatalogoBean().setEntidadBean(entidadBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosInventario() {

        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario(inventarioAlmacenBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTodosInventarioPorUniNeg() {

        try {
            int estado = 0;
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
            inventarioAlmacen.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
            for (InventarioAlmacenBean almacen : listaInventarioAlmacenBean) {
                if (almacen.getStockMin() > almacen.getStockActual()) {
                    estado = 1;
                }

                Integer actual = almacen.getStockActual();
                Integer min = almacen.getStockMin();
                Integer porc;
                porc = (((actual * min) * min) / 100);

                if (porc >= 50) {
                    almacen.setAlerta("/resources/images/verde.png");
                } else if (porc <= 49 && porc >= 11) {
                    almacen.setAlerta("/resources/images/amarillo.png");
                } else {
                    almacen.setAlerta("/resources/images/rojo.png");
                }
            }

            if (estado == 1) {
                new MensajePrime().addInformativeMessagePer("msjStockMin");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirTodosPdfGeneral() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();

            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteInventarioAlmacen.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<InventarioAlmacenGeneralRepBean> listaInventarioAlmacenGeneralRepBean = new ArrayList<>();
            listaInventarioAlmacenGeneralRepBean = inventarioAlmacenService.obtenerTodosReporte(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaInventarioAlmacenGeneralRepBean);
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

    public void obtenerBanderas() {

        try {

            for (InventarioAlmacenBean almacen : listaInventarioAlmacenBean) {
                Integer actual = almacen.getStockActual();
                Integer min = almacen.getStockMin();
                Integer porc;
                porc = (((actual * min) * min) / 100);

                if (porc >= 50) {
                    almacen.setAlerta("/resources/images/verde.png");
                } else if (porc <= 49 && porc >= 11) {
                    almacen.setAlerta("/resources/images/amarillo.png");
                } else {
                    almacen.setAlerta("/resources/images/rojo.png");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            inventarioAlmacenBean = (InventarioAlmacenBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerInventario(Object Inventario) {
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            inventarioAlmacenBean = (InventarioAlmacenBean) Inventario;
            inventarioAlmacenBean = inventarioAlmacenService.obtenerPorCatalogo(inventarioAlmacenBean);

            entidadBean = entidadService.obtenerEntidadPorIdCot(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            inventarioAlmacenBean.getCatalogoBean().setEntidadBean(entidad);
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

    public String actualizarCat() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                listaCatalogoBean = catalogoService.obtenerTodos();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void limpiarInventario() {
        try {
            inventarioAlmacenBean = new InventarioAlmacenBean();
            for (CodigoBean bean : listaTipoUnidadMedidaBean) {
                if (bean.getCodigo().equals("Unidad")) {
                    getInventarioAlmacenBean().getTipoUniMedBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
            for (CodigoBean bean : listaTipoMonedaBean) {
                if (bean.getCodigo().equals("Soles")) {
                    getInventarioAlmacenBean().getCatalogoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
            getCatalogoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarMovimiento() {
        try {
            movimientoAlmacenBean = new MovimientoAlmacenBean();
            getCatalogoBean();
            getMovimientoAlmacenBean().setEntregadoPor(usuarioSessionBean.getPersonalBean().getNombreCompleto());
            getMovimientoAlmacenBean().setPersonalBean(usuarioSessionBean.getPersonalBean());
            this.fechaSolicitud = new GregorianCalendar();
            getMovimientoAlmacenBean().setFechaMov(fechaSolicitud.getTime());
            String idInventario = null;
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            idInventario = inventarioAlmacenService.obtenerUltimo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idInventario.equals(null)) {
                getMovimientoAlmacenBean().setIdMovimientoAyuda(idInventario);
            }
            listaMovimientoAlmacenBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarInventarioAlmacenFiltro() {
        try {
            listaInventarioAlmacenBean = new ArrayList<>();
            inventarioAlmacenFiltroBean = new InventarioAlmacenBean();
            fechaActual = new GregorianCalendar();
            getInventarioAlmacenFiltroBean().setFechaInicio(fechaActual.getTime());
            getInventarioAlmacenFiltroBean().setFechaFin(fechaActual.getTime());
            getInventarioAlmacenFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroAlmacen() {
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            if (inventarioAlmacenFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(inventarioAlmacenFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                inventarioAlmacenFiltroBean.setFechaInicio(t);
            }
            if (inventarioAlmacenFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(inventarioAlmacenFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                inventarioAlmacenFiltroBean.setFechaFin(u);
            }
            if (inventarioAlmacenFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(inventarioAlmacenFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                inventarioAlmacenFiltroBean.setFechaFin(u);
            }
            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertar() {

        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                inventarioAlmacenService.insertar(inventarioAlmacenBean, catalogoBean, usuarioSessionBean);
//                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario(inventarioAlmacenBean);
                limpiarInventario();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return pagina;
    }

    public String actualizar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                InventarioAlmacenBean inventarioAlmacenBean = new InventarioAlmacenBean();
                inventarioAlmacenService.actualizar(inventarioAlmacenBean, catalogoBean, usuarioSessionBean);
//                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodosInventario(inventarioAlmacenBean);
                limpiarInventario();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return pagina;
    }

    public Boolean validarItemPorAgregar(InventarioAlmacenBean bean) {
        Boolean valor = false;
        try {
            for (MovimientoAlmacenBean inventario : listaMovimientoAlmacenBean) {
                if (Objects.equals(inventario.getCatalogoBean().getIdCatalogo(), bean.getCatalogoBean().getIdCatalogo())) {
                    valor = true;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void changeTipo(String tipo) {
        try {
            String tipoSol = "";
            if (tipo.equals("soli")) {
                tipoSol = idTipoSol;
            } else {
                tipoSol = idTipoRespCheque;
            }
            switch (tipoSol) {
                case "PER":
                    this.flgFiltroPersona = true;
                    this.flgFiltroPersonal = false;
                    this.flgFiltroProve = false;
                    PersonaMB personaMB = (PersonaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("personaMB");
                    if (personaMB != null) {
                        personaMB.limpiarPersonaFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("personaMB", personaMB);
                    }
                    break;
                case "COL":
                    this.flgFiltroPersonal = true;
                    this.flgFiltroPersona = false;
                    this.flgFiltroProve = false;
                    LegajoMB legajoMB = (LegajoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("legajoMB");
                    if (legajoMB != null) {
                        legajoMB.limpiarPersonalFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("legajoMB", legajoMB);
                    }
                    break;
                case "PRO":
                    this.flgFiltroProve = true;
                    this.flgFiltroPersonal = false;
                    this.flgFiltroPersona = false;
                    EntidadMB entidadMB = (EntidadMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("entidadMB");
                    if (entidadMB != null) {
                        entidadMB.limpiarFiltroEntidad();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("entidadMB", entidadMB);
                    }
                    break;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//            id = inventarioAlmacenService.obtenerUltimoPDF(movimientoAlmacenBean.getNroMovimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaMovimientoAlmacenBean = inventarioAlmacenService.obtenerUltimoMovPDF(movimientoAlmacenBean.getNroMovimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteMovimientoAlmacen.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MovimientoAlmacenRepBean> listaMovimiento = new ArrayList<>();
            listaMovimiento = inventarioAlmacenService.obtenerUltimoMovPDF2(movimientoAlmacenBean.getNroMovimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMovimiento);
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

    public void imprimirMovimientoAlmacen() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMovimientoAlmacen.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MovimientoAlmacenRepBean> listaMovimiento = new ArrayList<>();
            listaMovimiento = inventarioAlmacenService.obtenerReporteMovAlmacen(movimientoAlmacenBean.getNroMovimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMovimiento);
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosGeneralMoviPdf() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();

            listaMovimientoAlmacenBean = inventarioAlmacenService.obtenerTodosMovi(movimientoAlmacenBean.getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteMovimientoAlmacenGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MovimientoAlmacenGeneralRepBean> listaMovimientoGeneral = new ArrayList<>();
            listaMovimientoGeneral = inventarioAlmacenService.obtenerTodosReporteMov(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMovimientoGeneral);
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

    public void imprimirMovimiento() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMovimientoAlmacenGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            listaMovimientoAlmacenBean = inventarioAlmacenService.obtenerUltimoMovPDF(movimientoAlmacenBean.getNroMovimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<MovimientoAlmacenGeneralRepBean> listaMovimientoGeneral = new ArrayList<>();
            System.out.println(">>>" + listaMovimientoAlmacenBean.size());
            for (int i = 0; i < listaMovimientoAlmacenBean.size(); i++) {
                MovimientoAlmacenGeneralRepBean movimientoAlmacenGeneralRepBean = new MovimientoAlmacenGeneralRepBean();
                movimientoAlmacenGeneralRepBean.setNroMovimiento(listaMovimientoAlmacenBean.get(i).getNroMovimiento());
                Timestamp t = new Timestamp(listaMovimientoAlmacenBean.get(i).getFechaMov().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                movimientoAlmacenGeneralRepBean.setFechaMov(t);
                movimientoAlmacenGeneralRepBean.setCantidad(listaMovimientoAlmacenBean.get(i).getCantidad());
                movimientoAlmacenGeneralRepBean.setEntregadoPor(listaMovimientoAlmacenBean.get(i).getEntregadoPor());
                movimientoAlmacenGeneralRepBean.setNombreUniNeg(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                movimientoAlmacenGeneralRepBean.setRecibidoPor(listaMovimientoAlmacenBean.get(i).getRecibidoPor());
                movimientoAlmacenGeneralRepBean.setTipoUniMed(listaMovimientoAlmacenBean.get(i).getTipoUniMedBean().getCodigo());
                movimientoAlmacenGeneralRepBean.setIdCatalogo(listaMovimientoAlmacenBean.get(i).getCatalogoBean().getIdCatalogo());
                movimientoAlmacenGeneralRepBean.setItem(listaMovimientoAlmacenBean.get(i).getCatalogoBean().getItem());
                movimientoAlmacenGeneralRepBean.setNroAlmacen(listaMovimientoAlmacenBean.get(i).getInventarioAlmacenBean().getNroAlmacen());
                movimientoAlmacenGeneralRepBean.setWebUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
                movimientoAlmacenGeneralRepBean.setCorreoUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
                movimientoAlmacenGeneralRepBean.setTelefonoUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
                movimientoAlmacenGeneralRepBean.setDireccionUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
                movimientoAlmacenGeneralRepBean.setDistritoUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                movimientoAlmacenGeneralRepBean.setPaisUnidad(listaMovimientoAlmacenBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
                movimientoAlmacenGeneralRepBean.setStockAnterior(listaMovimientoAlmacenBean.get(i).getStockAnterior());
                movimientoAlmacenGeneralRepBean.setStockActual(listaMovimientoAlmacenBean.get(i).getInventarioAlmacenBean().getStockActual());
                listaMovimientoGeneral.add(movimientoAlmacenGeneralRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMovimientoGeneral);
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
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void settearTipoSoli(String tp) {
        try {
            this.flgFiltroPersonal = true;
            this.flgFiltroPersona = false;
            this.flgFiltroProve = false;
            if (tp.equals("solicitante")) {
                if (getIdTipoSol() == null) {
                    setIdTipoSol("COL");
                }
                this.flgSoli = true;
                changeTipo("soli");
            } else {
                if (getIdTipoRespCheque() == null) {
                    setIdTipoRespCheque("COL");
                }
                this.flgSoli = false;
                changeTipo("resp");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void agregarItems(InventarioAlmacenBean bean) {
        try {
            MovimientoAlmacenBean mov = new MovimientoAlmacenBean();
            mov.setCatalogoBean(bean.getCatalogoBean());
            mov.getInventarioAlmacenBean().setIdAlmacen(bean.getIdAlmacen());
            mov.getInventarioAlmacenBean().setStockActual(bean.getStockActual());
            mov.getInventarioAlmacenBean().setStockAyuda(bean.getStockAyuda());
            mov.getInventarioAlmacenBean().setNroAlmacen(bean.getNroAlmacen());
            //Si es false, quiere decir que no ha sido agregado ese item seleccionado
            if (!validarItemPorAgregar(bean)) {
                System.out.println("-->" + bean.getCatalogoBean().getIdCatalogo());
                System.out.println("-->" + bean.getNroAlmacen());
                System.out.println("-->" + bean.getStockAyuda());
                movimientoAlmacenBean.setCatalogoBean(bean.getCatalogoBean());
                movimientoAlmacenBean.getCatalogoBean().setIdCatalogo(bean.getCatalogoBean().getIdCatalogo());
                movimientoAlmacenBean.setInventarioAlmacenBean(bean);
                movimientoAlmacenBean.getInventarioAlmacenBean().setNroAlmacen(bean.getNroAlmacen());
                movimientoAlmacenBean.getInventarioAlmacenBean().setStockActual(bean.getStockActual());
                movimientoAlmacenBean.getInventarioAlmacenBean().setStockAyuda(bean.getStockAyuda());
                movimientoAlmacenBean.getInventarioAlmacenBean().setIdAlmacen(bean.getIdAlmacen());
                listaMovimientoAlmacenBean.add(mov);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregar(CatalogoBean bean) {
        try {
            System.out.println("-->" + bean.getIdCatalogo());
            System.out.println("-->" + bean.getItem());
            inventarioAlmacenBean.getCatalogoBean().setIdCatalogo(bean.getIdCatalogo());
            inventarioAlmacenBean.getCatalogoBean().setItem(bean.getItem());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void quitarItem(MovimientoAlmacenBean bean) {
        try {

            listaMovimientoAlmacenBean.remove(bean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarTablaInventario(RowEditEvent event) {
        Integer nuevosItems = 0;
        Integer stockNuevo = 0;
        try {
//            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//            inventarioAlmacenBean.getCatalogoBean().setIdCatalogo(inventarioAlmacenBean.getCatalogoBean().getIdCatalogo());
//            inventarioAlmacenBean.getCatalogoBean().getEntidadBean().setRuc(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
//            inventarioAlmacenBean.setStockActual(inventarioAlmacenBean.getStockActual());
//            inventarioAlmacenBean.setStockMin(inventarioAlmacenBean.getStockMin());
//            listaInventarioAlmacenBean.add(inventarioAlmacenBean);
//            inventarioAlmacenService.insertar(inventarioAlmacenBean, catalogoBean, usuarioSessionBean);
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            inventarioAlmacenBean = new InventarioAlmacenBean();
            //Seteando los ID's
            inventarioAlmacenBean = (((InventarioAlmacenBean) event.getObject()));

            EntidadBean entidad = new EntidadBean();
            entidad.setRuc(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

            entidad = entidadService.obtenerEntidadPorId(entidad);
            inventarioAlmacenBean.getCatalogoBean().setEntidadBean(entidad);
//            inventarioAlmacenBean.setStockActual(((InventarioAlmacenBean) event.getObject()).getStockActual());
//            inventarioAlmacenBean.setStockMin(((InventarioAlmacenBean) event.getObject()).getStockMin());
            if (inventarioAlmacenBean.getStockNuevo() > 0 || inventarioAlmacenBean.getStockNuevo() != 0) {
                stockNuevo = inventarioAlmacenBean.getStockNuevo() + inventarioAlmacenBean.getStockAntiguo();
                inventarioAlmacenBean.setStockActual(stockNuevo);
                inventarioAlmacenBean.setStockAntiguo(stockNuevo);
                inventarioAlmacenBean.setStockNuevo(0);
            }

            InventarioAlmacenBean inventario = new InventarioAlmacenBean();
            System.out.println(">>>" + (((InventarioAlmacenBean) event.getObject()).getNroAlmacen()));
            System.out.println(">>>" + inventarioAlmacenBean.getStockNuevo());
            System.out.println(">>>" + inventarioAlmacenBean.getStockActual());
            System.out.println(">>>" + inventarioAlmacenBean.getStockNuevo());
            inventarioAlmacenBean.setNroAlmacen((((InventarioAlmacenBean) event.getObject()).getNroAlmacen()));
            inventario = inventarioAlmacenService.obtenerColorAlmacenPorNro(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), inventarioAlmacenBean.getNroAlmacen());
            inventarioAlmacenBean.setNroAlmacen(inventario.getNroAlmacen());
//            inventarioAlmacenBean.setColor(inventario.getColor());
            inventarioAlmacenService.actualizar(inventarioAlmacenBean, inventarioAlmacenBean.getCatalogoBean(), usuarioSessionBean);
            inventarioAlmacenBean.getCatalogoBean().getEntidadBean().setRuc(((InventarioAlmacenBean) event.getObject()).getCatalogoBean().getEntidadBean().getRuc());
            System.out.println((((InventarioAlmacenBean) event.getObject()).getCatalogoBean().getEntidadBean().getRuc()));
            System.out.println((((InventarioAlmacenBean) event.getObject()).getCatalogoBean().getEntidadBean().getNombre()));
            System.out.println(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            System.out.println(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getNombre());
            obtenerBanderas();
            obtenerTodosInventarioPorUniNeg();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onRowCancel(MovimientoAlmacenBean beanSD) {
//        FacesMessage msg = new FacesMessage("Error", beanSD.getIdMovimientoAlmacen().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void actualizarCantidad(MovimientoAlmacenBean movimientoAlmacenBean, Integer n) {
        try {
            Integer stockActual = 0;
            Integer cantUps = 0;
            if (movimientoAlmacenBean.getCantidadSession() == null) {
                movimientoAlmacenBean.setCantidadSession(1);
                cantUps = movimientoAlmacenBean.getCantidadSession();
                cantUps = cantUps + 1;
            } else {
                cantUps = movimientoAlmacenBean.getCantidadSession();
                if (movimientoAlmacenBean.getInventarioAlmacenBean().getStockActual() == movimientoAlmacenBean.getInventarioAlmacenBean().getStockAyuda()) {
                    cantUps = cantUps + 1;
                }
            }
            if (movimientoAlmacenBean.getInventarioAlmacenBean().getStockActual() >= movimientoAlmacenBean.getCantidad()) {
                if (movimientoAlmacenBean.getCantidadSession() == cantUps) {
                    stockActual = (movimientoAlmacenBean.getInventarioAlmacenBean().getStockActual() + cantUps) - movimientoAlmacenBean.getCantidad();
                } else {
                    stockActual = movimientoAlmacenBean.getInventarioAlmacenBean().getStockActual() - movimientoAlmacenBean.getCantidad();
                }
                movimientoAlmacenBean.setCantidadSession(movimientoAlmacenBean.getCantidad());
                movimientoAlmacenBean.getInventarioAlmacenBean().setStockActual(stockActual);
                movimientoAlmacenBean.setCantidad(movimientoAlmacenBean.getCantidad());
                listaMovimientoAlmacenBean.get(n).getInventarioAlmacenBean().setStockActual(stockActual);

                System.out.println("stock act lista->" + listaMovimientoAlmacenBean.get(n).getInventarioAlmacenBean().getStockActual());
                System.out.println("stock act->" + stockActual);
                System.out.println("stock cant->" + movimientoAlmacenBean.getCantidad());
                System.out.println(movimientoAlmacenBean.getCatalogoBean().getIdCatalogo());

                for (MovimientoAlmacenBean lista : listaMovimientoAlmacenBean) {
                    System.out.println("stock a ->" + lista.getInventarioAlmacenBean().getStockActual());
                }

            } else {
                RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
                System.out.println("StockAyuda-->" + movimientoAlmacenBean.getInventarioAlmacenBean().getStockAyuda());
                movimientoAlmacenBean.getInventarioAlmacenBean().setStockActual(movimientoAlmacenBean.getInventarioAlmacenBean().getStockAyuda());
                stockActual = movimientoAlmacenBean.getInventarioAlmacenBean().getStockActual();
                movimientoAlmacenBean.setCantidad(0);
                onRowCancel(movimientoAlmacenBean);
            }
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
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                CatalogoBean catalogo = new CatalogoBean();
//                catalogo = catalogoBean;
                if (inventarioAlmacenBean.getIdAlmacen() == null) {
                    inventarioAlmacenBean.setCreaPor(usuarioSessionBean.getUsuario());
                    inventarioAlmacenBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    inventarioAlmacenBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
//                    inventarioAlmacenBean.setCatalogoBean(catalogoBean);
                    inventarioAlmacenService.insertar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);
                    obtenerBanderas();
                } else {
                    inventarioAlmacenBean.setModiPor(usuarioSessionBean.getUsuario());
                    inventarioAlmacenService.actualizar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);
                    obtenerBanderas();
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String grabarMovimiento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
//                CatalogoBean catalogo = new CatalogoBean();
//                catalogo = catalogoBean;
                System.out.println("MET" + getMovimientoAlmacenBean().getNroMovimiento());
                if (getMovimientoAlmacenBean().getNroMovimiento() == null) {
                    movimientoAlmacenBean.setCreaPor(usuarioSessionBean.getUsuario());
                    movimientoAlmacenBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
//                    inventarioAlmacenBean.setCatalogoBean(catalogoBean);
                    inventarioAlmacenService.insertarMovimiento(movimientoAlmacenBean, listaMovimientoAlmacenBean, usuarioSessionBean);
                }
//                    else {
//                    inventarioAlmacenBean.setModiPor(usuarioSessionBean.getUsuario());
//                    inventarioAlmacenService.actualizar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);
//                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);
//                    obtenerBanderas();
//                }  
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);
                obtenerBanderas();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void setearColor(Object color) {
        try {
            InventarioAlmacenBean inventario = (InventarioAlmacenBean) color;
            inventarioAlmacenBean.setNroAlmacen(inventario.getNroAlmacen());
            inventarioAlmacenBean.setColor(inventario.getColor());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* NUEVOS METODOS */
    public void obtenerTodosInventarioPorUniNegNew() {
        try {
            int estado = 0;
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
            inventarioAlmacen.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            if (usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null) {
                System.out.println(">>>>" + usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                if (listaInventarioAlmacenBean.isEmpty()) {
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                }
            } else {
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
            }
            for (InventarioAlmacenBean almacen : listaInventarioAlmacenBean) {
                if (almacen.getStockMin() > almacen.getStockActual()) {
                    estado = 1;
                }
                Integer actual = almacen.getStockActual();
                Integer min = almacen.getStockMin();
                Integer porc;
                porc = (((actual * min) * min) / 100);
                if (porc >= 50) {
                    almacen.setAlerta("/resources/images/verde.png");
                } else if (porc <= 49 && porc >= 11) {
                    almacen.setAlerta("/resources/images/amarillo.png");
                } else {
                    almacen.setAlerta("/resources/images/rojo.png");
                }
            }
            if (estado == 1) {
                new MensajePrime().addInformativeMessagePer("msjStockMin");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostarPanelCatalogo() {
        try {
            if (catalogoBean.getTipoCategoriaBean() != null) {
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == MaristaConstantes.Id_Categoria_Almacen) {
                    this.flgInvAlm = true;
                    this.flgInvAct = false;
                    this.flgServ = false;
                }
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo().equals(this)) {
                    this.flgInvAlm = false;
                    this.flgInvAct = true;
                    this.flgServ = false;
                }
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo().equals(MaristaConstantes.Id_Categoria_Servicio)) {
                    this.flgInvAlm = false;
                    this.flgInvAct = false;
                    this.flgServ = true;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double Precio) {
        this.Precio = Precio;
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

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
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

    public List<CodigoBean> getListaTipoUnidadMedidaBean() {
        return listaTipoUnidadMedidaBean;
    }

    public void setListaTipoUnidadMedidaBean(List<CodigoBean> listaTipoUnidadMedidaBean) {
        this.listaTipoUnidadMedidaBean = listaTipoUnidadMedidaBean;
    }

    public InventarioAlmacenBean getInventarioAlmacenFiltroBean() {
        if (inventarioAlmacenFiltroBean == null) {
            inventarioAlmacenFiltroBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenFiltroBean;
    }

    public void setInventarioAlmacenFiltroBean(InventarioAlmacenBean inventarioAlmacenFiltroBean) {
        this.inventarioAlmacenFiltroBean = inventarioAlmacenFiltroBean;
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

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public MovimientoAlmacenBean getMovimientoAlmacenBean() {
        if (movimientoAlmacenBean == null) {
            movimientoAlmacenBean = new MovimientoAlmacenBean();
        }
        return movimientoAlmacenBean;
    }

    public void setMovimientoAlmacenBean(MovimientoAlmacenBean movimientoAlmacenBean) {
        this.movimientoAlmacenBean = movimientoAlmacenBean;
    }

    public List<MovimientoAlmacenBean> getListaMovimientoAlmacenBean() {
        if (listaMovimientoAlmacenBean == null) {
            listaMovimientoAlmacenBean = new ArrayList<>();
        }
        return listaMovimientoAlmacenBean;
    }

    public void setListaMovimientoAlmacenBean(List<MovimientoAlmacenBean> listaMovimientoAlmacenBean) {
        this.listaMovimientoAlmacenBean = listaMovimientoAlmacenBean;
    }

    public Boolean getFlgFiltroPersonal() {
        return flgFiltroPersonal;
    }

    public void setFlgFiltroPersonal(Boolean flgFiltroPersonal) {
        this.flgFiltroPersonal = flgFiltroPersonal;
    }

    public Boolean getFlgFiltroPersona() {
        return flgFiltroPersona;
    }

    public void setFlgFiltroPersona(Boolean flgFiltroPersona) {
        this.flgFiltroPersona = flgFiltroPersona;
    }

    public Boolean getFlgFiltroProve() {
        return flgFiltroProve;
    }

    public void setFlgFiltroProve(Boolean flgFiltroProve) {
        this.flgFiltroProve = flgFiltroProve;
    }

    public Boolean getFlgSoli() {
        return flgSoli;
    }

    public void setFlgSoli(Boolean flgSoli) {
        this.flgSoli = flgSoli;
    }

    public Boolean getFlgIgualSoli() {
        return flgIgualSoli;
    }

    public void setFlgIgualSoli(Boolean flgIgualSoli) {
        this.flgIgualSoli = flgIgualSoli;
    }

    public String getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(String idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public String getIdTipoRespCheque() {
        return idTipoRespCheque;
    }

    public void setIdTipoRespCheque(String idTipoRespCheque) {
        this.idTipoRespCheque = idTipoRespCheque;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Boolean getFlgGestorSoli() {
        return flgGestorSoli;
    }

    public void setFlgGestorSoli(Boolean flgGestorSoli) {
        this.flgGestorSoli = flgGestorSoli;
    }

    public Calendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Calendar fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Integer getStockAntiguo() {
        return StockAntiguo;
    }

    public void setStockAntiguo(Integer StockAntiguo) {
        this.StockAntiguo = StockAntiguo;
    }

    //CAMBIO A PROBAR
    public List<InventarioAlmacenBean> getListaColorAlmacen() {
        if (listaColorAlmacen == null) {
            listaColorAlmacen = new ArrayList<>();
        }
        return listaColorAlmacen;
    }

    public void setListaColorAlmacen(List<InventarioAlmacenBean> listaColorAlmacen) {
        this.listaColorAlmacen = listaColorAlmacen;
    }

    public InventarioActivoBean getInventarioActivoBean() {
        if (inventarioActivoBean == null) {
            inventarioActivoBean = new InventarioActivoBean();
        }
        return inventarioActivoBean;
    }

    public void setInventarioActivoBean(InventarioActivoBean inventarioActivoBean) {
        this.inventarioActivoBean = inventarioActivoBean;
    }

    public CatalogoBean getCatalogoFiltroBean() {
        if (catalogoFiltroBean == null) {
            catalogoFiltroBean = new CatalogoBean();
        }
        return catalogoFiltroBean;
    }

    public void setCatalogoFiltroBean(CatalogoBean catalogoFiltroBean) {
        this.catalogoFiltroBean = catalogoFiltroBean;
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

    public Boolean getFlgInvAlm() {
        return flgInvAlm;
    }

    public void setFlgInvAlm(Boolean flgInvAlm) {
        this.flgInvAlm = flgInvAlm;
    }

    public Boolean getFlgInvAct() {
        return flgInvAct;
    }

    public void setFlgInvAct(Boolean flgInvAct) {
        this.flgInvAct = flgInvAct;
    }

    public Boolean getFlgServ() {
        return flgServ;
    }

    public void setFlgServ(Boolean flgServ) {
        this.flgServ = flgServ;
    }

}
