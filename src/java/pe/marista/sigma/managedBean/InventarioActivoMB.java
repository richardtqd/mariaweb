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
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
//import org.jfree.chart.block.Arrangement;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.InventarioActivoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.bean.UsuarioBean;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.MovimientoActivoBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.reporte.InventarioActivoGeneralRepBean;
import pe.marista.sigma.service.MovimientoActivoService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.service.MotivoMovimientoService;
import pe.marista.sigma.service.InventarioActivoService;

public class InventarioActivoMB implements Serializable {

    private List<InventarioActivoBean> listaInventarioActivo;
    private List<CatalogoBean> listaCatalogoBean;
    private Integer idInventarioActivo;
    private Integer idCatalogo;
    private InventarioActivoBean inventarioActivoBean;
    private CatalogoBean catalogoBean;
    private CatalogoBean catalogoFiltroBean;
    private InventarioActivoBean inventarioActivoFiltroBean;
    private UsuarioBean usuarioSessionBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<CodigoBean> listaTipoUniMedBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<EntidadBean> listaEntidadBean;
    private List<CodigoBean> listaTipoMovBean;
    private List<MotivoMovimientoBean> listaTipoMotivoBean;
    private List<CodigoBean> listaTipoDuracionBean;
    private List<CodigoBean> listaTipoMovActivo;
    private List<CodigoBean> listaTipoDuracion;
    //Cambios de MovimientoActivo
    private Integer idTipoMov;
    private Integer idTipoMotivo;
    private Integer idTipoDuracion;
    private MotivoMovimientoBean motivoMovimientoBean;
    private List<MovimientoActivoBean> listaMovimientoActivoBean;
    private List<MotivoMovimientoBean> listaMotivoMovimientoBean;

    //Cambios de SolicitudLogistico
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogisticoBean solicitudLogisticoFiltroBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoAprobBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoDespBean;
    private MovimientoActivoBean movimientoActivoBean;
    private List<UsuarioBean> listaUsuarioBean;
    private UsuarioBean usuarioBean;//El objeto usuarioBean viene con PersonalBean
    //ayuda
    private Boolean flgDuracion = false;
    private MovimientoActivoBean movimientoActivoFiltroBean;
    private List<MovimientoActivoBean> listaMovimientoActivoFiltroBean;
    private List<InventarioActivoBean> listaInventarioActivoBean;
    private Calendar fechaActual;

    private EntidadBean entidadBean;
    private Integer idTipoUniMed;
    private Integer idTipoMoneda;
    @PostConstruct
    public void InventarioActivoMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getInventarioActivoBean();
//            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
//            getListaCatalogoBean(); 
//            listaCatalogoBean = inventarioActivoService.ObtenerIAPorItem(this.idCatalogo);
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoCategoriaBean();
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            getListaTipoMonedaBean();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            getListaTipoUniMedBean();
            listaTipoUniMedBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUnidadMedida"));
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            getListaInventarioActivo();
            listaInventarioActivo = inventarioActivoService.obtenerTodos();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            getListaCatalogoBean();
            listaCatalogoBean = catalogoService.obtenerTodos();
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidadBean();
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaTipoMovActivo = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMovActivo"));
            listaTipoMovBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMovActivo"));
            listaTipoDuracionBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDuracion"));
//            listaTipoDuracionBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDuracion"));

            getInventarioActivoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getInventarioActivoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            MovimientoActivoService movimientoActivoService = BeanFactory.getMovimientoActivoService();
            getListaMovimientoActivoBean();
            listaMovimientoActivoBean = movimientoActivoService.ObtenerTodos();

            //Cambios de Solicitud Logistico
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaUsuarioBean = usuarioService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CodigoBean bean : listaTipoMonedaBean) {
                if (bean.getCodigo().equals("Soles")) {
                    getInventarioActivoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoMoneda = bean.getIdCodigo();
                }
            }
            for (CodigoBean bean : listaTipoUniMedBean) {
                if (bean.getCodigo().equals("Unidad")) {
                    getInventarioActivoBean().getTipoUniMedBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ObtenerCatalogo() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaCatalogoBean = catalogoService.obtenerTodos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            inventarioActivoBean = (InventarioActivoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void ponerActivos(Object activo) {
        try {
          InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            inventarioActivoBean = (InventarioActivoBean) activo;
            inventarioActivoBean = inventarioActivoService.obtenerPorCatalogo(inventarioActivoBean);
         
            entidadBean = entidadService.obtenerEntidadPorIdCot(inventarioActivoBean.getCatalogoBean().getEntidadBean().getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            inventarioAlmacenBean.getCatalogoBean().setEntidadBean(entidad);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarInventarioActivo() {
        try {
            inventarioActivoBean = new InventarioActivoBean();
            for (CodigoBean bean : listaTipoMonedaBean) {
                if (bean.getCodigo().equals("Soles")) {
                    getInventarioActivoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoMoneda = bean.getIdCodigo();
                }
            }
            for (CodigoBean bean : listaTipoUniMedBean) {
                if (bean.getCodigo().equals("Unidad")) {
                    getInventarioActivoBean().getTipoUniMedBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltroIA() {
        try {
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            if (inventarioActivoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(inventarioActivoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                inventarioActivoFiltroBean.setFechaInicio(t);
            }
            if (inventarioActivoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(inventarioActivoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                inventarioActivoFiltroBean.setFechaFin(u);
            }
            listaInventarioActivoBean = inventarioActivoService.obtenerPorFiltroIA(inventarioActivoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarInventarioFiltro() {
        try {
            listaInventarioActivoBean = new ArrayList<>();
            inventarioActivoFiltroBean = new InventarioActivoBean();
            fechaActual = new GregorianCalendar();
            getInventarioActivoFiltroBean().setFechaInicio(fechaActual.getTime());
            getInventarioActivoFiltroBean().setFechaFin(fechaActual.getTime());
            getInventarioActivoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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
                InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                if (inventarioActivoBean.getCatalogoBean().getIdCatalogo() == null) {
                    inventarioActivoBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    inventarioActivoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    inventarioActivoBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
//                    inventarioAlmacenBean.setCatalogoBean(catalogoBean);
                    inventarioActivoService.insertar(inventarioActivoBean,getCatalogoBean(),usuarioSessionBean);

                } else {
                    inventarioActivoBean.setModiPor(usuarioSessionBean.getUsuario());
                    inventarioActivoService.actualizar(inventarioActivoBean,getCatalogoBean(),usuarioSessionBean);
                }
                listaInventarioActivoBean = inventarioActivoService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ObtenerIAPorItem() {
        try {
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            listaCatalogoBean = inventarioActivoService.ObtenerIAPorItem(this.idCatalogo);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerInventarioPorId(Object object) {
        try {
            inventarioActivoBean = (InventarioActivoBean) object;
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            inventarioActivoBean = inventarioActivoService.obtenerInventarioPorId(getInventarioActivoBean().getIdInventarioActivo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerInventario(Object object) {
        try {

            CatalogoBean catalogo = (CatalogoBean) object;
//            inventarioActivoBean.getCatalogoBean().setItem(catalogo.getItem());
            inventarioActivoBean.setCatalogoBean(catalogo);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerInventarioActivo(Object object) {
        try {

            inventarioActivoBean = (InventarioActivoBean) object;
//             inventarioActivoBean.setCatalogoBean(catalogoBean);
            movimientoActivoBean.getInventarioActivoBean().setCatalogoBean(catalogoBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltro() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (catalogoFiltroBean.getItem() != null && !catalogoFiltroBean.getItem().equals("")) {
                catalogoFiltroBean.setItem(catalogoFiltroBean.getItem());
            } else if (listaCatalogoBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Encontraron Datos"));
            }
            listaCatalogoBean = catalogoService.obtenerPorFiltro(catalogoFiltroBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltroInventarioActivo() {
        try {
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (inventarioActivoFiltroBean.getCatalogoBean().getItem() != null && !inventarioActivoFiltroBean.getCatalogoBean().getItem().equals("")) {
                inventarioActivoFiltroBean.getCatalogoBean().setItem(inventarioActivoFiltroBean.getCatalogoBean().getItem());
            } else if (listaInventarioActivo.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Encontraron Datos"));
            }
            listaInventarioActivo = inventarioActivoService.obtenerPorFiltroInventario(inventarioActivoFiltroBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Cambios de MovimientoActivo
    public void obtenerMotivoPorMov() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
//            listaTipoMotivoBean = codigoService.obtenerMotivoPorMov(movimientoActivoBean.getMotivoMovimientoBean().getIdMovimientoMotivo());
//            listaTipoMotivoBean = codigoService.obtenerMotivoPorMov(this.idTipoMov);
            listaTipoMotivoBean = codigoService.obtenerMotivoPorMov(movimientoActivoBean.getTipoMovActivoBean().getIdCodigo());
//          
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerMotivoPorId() {
        try {
//            CodigoService codigoService = BeanFactory.getCodigoService();
            MotivoMovimientoService motivoMovimientoService = BeanFactory.getMotivoMovimientoService();
//            motivoMovimientoBean = codigoService.obtenerMotivoPorId(this.idTipoMotivo);movimientoActivoBean.motivoMovimientoBean.idMovimientoMotivo
//            motivoMovimientoBean = codigoService.obtenerMotivoPorId(motivoMovimientoBean.getIdMovimientoMotivo());
//            listaTipoMotivoBean = codigoService.obtenerMotivoPorMov(movimientoActivoBean.getMotivoMovimientoBean().getIdMovimientoMotivo());
//            motivoMovimientoBean = codigoService.obtenerMotivoPorId(motivoMovimientoBean.getIdMovimientoMotivo());
//              motivoMovimientoBean = codigoService.obtenerMotivoPorId(this.idTipoMotivo);
            listaMotivoMovimientoBean = motivoMovimientoService.obtenerMotivo(movimientoActivoBean.getMotivoMovimientoBean().getIdMovimientoMotivo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarMovimiento() {
        try {
            motivoMovimientoBean = new MotivoMovimientoBean();
            movimientoActivoBean = new MovimientoActivoBean();
            inventarioActivoBean = new InventarioActivoBean();
            movimientoActivoBean.getTipoMovActivoBean().setIdCodigo(this.idTipoMotivo);
            movimientoActivoBean.getTipoMovActivoBean().setIdCodigo(this.idTipoMov);
            movimientoActivoBean.getTipoDuracionBean().setIdCodigo(this.idTipoDuracion);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MovimientoActivoService movimientoActivoService = BeanFactory.getMovimientoActivoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                movimientoActivoBean.setCreaPor(usuarioBean.getUsuario());
                movimientoActivoBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                movimientoActivoBean.setInventarioActivoBean(inventarioActivoBean);
                movimientoActivoBean.setMotivoMovimientoBean(motivoMovimientoBean);
                movimientoActivoService.insertar(movimientoActivoBean);
                listaMovimientoActivoBean = movimientoActivoService.ObtenerTodos();
                limpiarMovimiento();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //limpiar filtro movimientos
    public void limpiarMovimientosFiltro() {
        try {
//            movimientoActivoBean = new MovimientoActivoBean();
            listaMovimientoActivoBean = new ArrayList<>();
            movimientoActivoFiltroBean = new MovimientoActivoBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getMovimientoActivoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerMovimientos(MovimientoActivoBean movimiento) {
        try {
            MovimientoActivoService movimientoActivoService = BeanFactory.getMovimientoActivoService();
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            MotivoMovimientoService motivoMovimientoService = BeanFactory.getMotivoMovimientoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            movimientoActivoBean = (MovimientoActivoBean) movimiento;
            movimientoActivoBean = movimientoActivoService.ObtenerPorIdMovimiento(movimiento);
//            motivoMovimientoBean = motivoMovimientoService.obtenerMotivoPorId(motivoMovimientoBean.getIdMovimientoMotivo());
            movimientoActivoBean.getTipoMovActivoBean().getCodigo();
            listaTipoMotivoBean = codigoService.obtenerMotivoPorMov(movimientoActivoBean.getTipoMovActivoBean().getIdCodigo());
//            motivoMovimientoBean.getTipoMovimientoActivoBean().getCodigo();
            movimientoActivoBean.setInventarioActivoBean(inventarioActivoBean);
//            movimientoActivoBean.getInventarioActivoBean().setIdInventarioActivo(idInventarioActivo);
            inventarioActivoBean = inventarioActivoService.obtenerInventarioPorId(idInventarioActivo);
            movimientoActivoBean.getInventarioActivoBean().setCatalogoBean(catalogoBean);
//            inventarioActivoBean.getCatalogoBean().getIdCatalogo();
            movimientoActivoBean.getRespOrigenBean().getNombreCompleto();
            movimientoActivoBean.getResoDestinoBean().getNombreCompleto();
            movimientoActivoBean.getUniOrgDestinoBean().getNombreUniOrg();
            movimientoActivoBean.getUniOrgOrigenBean().getNombreUniOrg();

            System.out.println("Excelente");
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

    public void obtenerEntidadPorId(EntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidad = new EntidadBean();

            entidad.setRuc(bean.getRuc());
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

            entidadBean = entidadService.obtenerEntidadPorId(entidad);
            //entidadBean = entidadService.buscarPorId(bean.getRuc());
            inventarioActivoBean.setEntidadBean(entidadBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    public void imprimirTodosPdfGeneral() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();

            listaInventarioActivoBean = inventarioActivoService.obtenerTodosActivos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteInventarioActivo.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<InventarioActivoGeneralRepBean> listaInventarioActivoGeneralRepBean = new ArrayList<>();
            for (int i = 0; i < listaInventarioActivoBean.size(); i++) {
                InventarioActivoGeneralRepBean inventarioActivoGeneral = new InventarioActivoGeneralRepBean();
                inventarioActivoGeneral.setIdInventarioActivo(listaInventarioActivoBean.get(i).getIdInventarioActivo());
                inventarioActivoGeneral.setMarca(listaInventarioActivoBean.get(i).getMarca());
                inventarioActivoGeneral.setModelo(listaInventarioActivoBean.get(i).getModelo());
                inventarioActivoGeneral.setStockactual(listaInventarioActivoBean.get(i).getStockActual());
                inventarioActivoGeneral.setPreciocompra(listaInventarioActivoBean.get(i).getPreciocompra());
                inventarioActivoGeneral.setEstado(listaInventarioActivoBean.get(i).getEstado());
                inventarioActivoGeneral.setItem(listaInventarioActivoBean.get(i).getCatalogoBean().getItem());
                inventarioActivoGeneral.setNombreProveedor(listaInventarioActivoBean.get(i).getEntidadBean().getNombre());
                inventarioActivoGeneral.setTipoMoneda(listaInventarioActivoBean.get(i).getTipoMonedaBean().getCodigo());
                inventarioActivoGeneral.setTipoUnidadMedida(listaInventarioActivoBean.get(i).getTipoUniMedBean().getCodigo()); 
                inventarioActivoGeneral.setNombreUniNeg(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                inventarioActivoGeneral.setDireccionUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getDireccion());
                inventarioActivoGeneral.setTelefonoUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getTelefono());
                inventarioActivoGeneral.setCorreoUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getCorreo());
                inventarioActivoGeneral.setWebUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getUrl());
                inventarioActivoGeneral.setDistritoUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                inventarioActivoGeneral.setPaisUnidad(listaInventarioActivoBean.get(i).getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
//                }
                listaInventarioActivoGeneralRepBean.add(inventarioActivoGeneral);

            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaInventarioActivoGeneralRepBean);
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
    
    public void obtenerPorFiltroMovimientos() {
        try {

            MovimientoActivoService movimientoActivoService = BeanFactory.getMovimientoActivoService();

            if (movimientoActivoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(movimientoActivoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                movimientoActivoFiltroBean.setFechaInicio(t);
            }
            if (movimientoActivoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(movimientoActivoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                movimientoActivoFiltroBean.setFechaFin(u);
            }

            listaMovimientoActivoBean = movimientoActivoService.obtenerPorFiltroMovimientos(movimientoActivoBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosInventarioPorUniNeg() {
        try {
            int estado = 0;
            InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
            InventarioActivoBean inventarioActivo = new InventarioActivoBean();
            inventarioActivo.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            listaInventarioActivoBean = inventarioActivoService.obtenerPorFiltroIActivo(inventarioActivo);
            for (InventarioActivoBean activo : listaInventarioActivoBean) {
                if (activo.getStockActual() != null) {
                    if (activo.getStockActual() < 6) {
                        estado = 1;
                    }
                }

                Integer actual = activo.getStockActual();

                if (actual >= 50) {
                    activo.setAlerta("/resources/images/verde.png");
                } else if (actual <= 49 && actual >= 10) {
                    activo.setAlerta("/resources/images/amarillo.png");
                } else {
                    activo.setAlerta("/resources/images/rojo.png");
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

    public void mostarPanelDuracion() {
        try {
            if (movimientoActivoBean.getTipoDuracionBean() != null) {
                if (movimientoActivoBean.getTipoDuracionBean().getIdCodigo() == 10801) {
                    this.flgDuracion = true;
                } else {
                    this.flgDuracion = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerUsuarioPorId(UsuarioBean bean) {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            usuarioBean = usuarioService.buscarPorId(bean.getUsuario());
            //Paso los valores del bean del popup al bean de guardar
            movimientoActivoBean.setResoDestinoBean(usuarioBean.getPersonalBean());
            movimientoActivoBean.setRespOrigenBean(usuarioBean.getPersonalBean());
            movimientoActivoBean.setUniOrgOrigenBean(usuarioBean.getPersonalBean().getUnidadOrganicaBean());
            movimientoActivoBean.setUniOrgDestinoBean(usuarioBean.getPersonalBean().getUnidadOrganicaBean());

//            solicitudLogisticoBean.setPersonalBean(usuarioBean.getPersonalBean());
//            solicitudLogisticoBean.setUnidadOrganicaBean(usuarioBean.getPersonalBean().getUnidadOrganicaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostrarPanel() {
        try {
//            if (inventarioActivoBean.getidTipoD) {
//                
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void obtenerInventarioPorId(Integer idInventarioActivo)
//    public void insertar (InventarioActivoBean inventarioActivoBean) 
//    public List<InventarioActivoBean> obtenerTodos() 
    public CatalogoBean getCatalogoBean() {
        if (catalogoBean == null) {
            catalogoBean = new CatalogoBean();
        }
        return catalogoBean;
    }

    public void setCatalogoBean(CatalogoBean catalogoBean) {
        this.catalogoBean = catalogoBean;
    }

    public Integer getIdInventarioActivo() {
        return idInventarioActivo;
    }

    public void setIdInventarioActivo(Integer idInventarioActivo) {
        this.idInventarioActivo = idInventarioActivo;
    }

    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
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

    public List<CatalogoBean> getListaCatalogoBean() {
        if (listaCatalogoBean == null) {
            listaCatalogoBean = new ArrayList<>();
        }
        return listaCatalogoBean;
    }

    public void setListaCatalogoBean(List<CatalogoBean> listaCatalogoBean) {
        this.listaCatalogoBean = listaCatalogoBean;
    }

    public Integer getIdcatalogo() {
        return idCatalogo;
    }

    public void setIdcatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public List<InventarioActivoBean> getListaInventarioActivo() {
        if (listaInventarioActivo == null) {
            listaInventarioActivo = new ArrayList<>();
        }
        return listaInventarioActivo;
    }

    public void setListaInventarioActivo(List<InventarioActivoBean> listaInventarioActivo) {
        this.listaInventarioActivo = listaInventarioActivo;
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

    public List<CodigoBean> getListaTipoUniMedBean() {
        if (listaTipoUniMedBean == null) {
            listaTipoUniMedBean = new ArrayList<>();
        }
        return listaTipoUniMedBean;
    }

    public void setListaTipoUniMedBean(List<CodigoBean> listaTipoUniMedBean) {
        this.listaTipoUniMedBean = listaTipoUniMedBean;
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

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
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

    public List<CodigoBean> getListaTipoMovBean() {
        if (listaTipoMovBean == null) {
            listaTipoMovBean = new ArrayList<>();
        }
        return listaTipoMovBean;
    }

    public void setListaTipoMovBean(List<CodigoBean> listaTipoMovBean) {
        this.listaTipoMovBean = listaTipoMovBean;
    }

    public List<MotivoMovimientoBean> getListaTipoMotivoBean() {
        if (listaTipoMotivoBean == null) {
            listaTipoMotivoBean = new ArrayList<>();
        }
        return listaTipoMotivoBean;
    }

    public void setListaTipoMotivoBean(List<MotivoMovimientoBean> listaTipoMotivoBean) {
        this.listaTipoMotivoBean = listaTipoMotivoBean;
    }

    public List<CodigoBean> getListaTipoDuracionBean() {
        if (listaTipoDuracionBean == null) {
            listaTipoDuracionBean = new ArrayList<>();
        }
        return listaTipoDuracionBean;
    }

    public void setListaTipoDuracionBean(List<CodigoBean> listaTipoDuracionBean) {
        this.listaTipoDuracionBean = listaTipoDuracionBean;
    }

    public Integer getIdTipoMov() {
        return idTipoMov;
    }

    public void setIdTipoMov(Integer idTipoMov) {
        this.idTipoMov = idTipoMov;
    }

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public MotivoMovimientoBean getMotivoMovimientoBean() {
        if (motivoMovimientoBean == null) {
            motivoMovimientoBean = new MotivoMovimientoBean();
        }
        return motivoMovimientoBean;
    }

    public void setMotivoMovimientoBean(MotivoMovimientoBean motivoMovimientoBean) {
        this.motivoMovimientoBean = motivoMovimientoBean;
    }

    public List<MovimientoActivoBean> getListaMovimientoActivoBean() {
        if (listaMovimientoActivoBean == null) {
            listaMovimientoActivoBean = new ArrayList<>();
        }
        return listaMovimientoActivoBean;
    }

    public void setListaMovimientoActivoBean(List<MovimientoActivoBean> listaMovimientoActivoBean) {
        this.listaMovimientoActivoBean = listaMovimientoActivoBean;
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

    public SolicitudLogisticoBean getSolicitudLogisticoFiltroBean() {
        if (solicitudLogisticoFiltroBean == null) {
            solicitudLogisticoFiltroBean = new SolicitudLogisticoBean();
        }
        return solicitudLogisticoFiltroBean;
    }

    public void setSolicitudLogisticoFiltroBean(SolicitudLogisticoBean solicitudLogisticoFiltroBean) {
        this.solicitudLogisticoFiltroBean = solicitudLogisticoFiltroBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoBean() {
        if (listaSolicitudLogisticoBean == null) {
            listaSolicitudLogisticoBean = new ArrayList<>();
        }
        return listaSolicitudLogisticoBean;
    }

    public void setListaSolicitudLogisticoBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoBean) {
        this.listaSolicitudLogisticoBean = listaSolicitudLogisticoBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoAprobBean() {
        if (listaSolicitudLogisticoAprobBean == null) {
            listaSolicitudLogisticoAprobBean = new ArrayList<>();
        }
        return listaSolicitudLogisticoAprobBean;
    }

    public void setListaSolicitudLogisticoAprobBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoAprobBean) {
        this.listaSolicitudLogisticoAprobBean = listaSolicitudLogisticoAprobBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoDespBean() {
        if (listaSolicitudLogisticoDespBean == null) {
            listaSolicitudLogisticoDespBean = new ArrayList<>();
        }
        return listaSolicitudLogisticoDespBean;
    }

    public void setListaSolicitudLogisticoDespBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoDespBean) {
        this.listaSolicitudLogisticoDespBean = listaSolicitudLogisticoDespBean;
    }

    public MovimientoActivoBean getMovimientoActivoBean() {
        if (movimientoActivoBean == null) {
            movimientoActivoBean = new MovimientoActivoBean();
        }
        return movimientoActivoBean;
    }

    public void setMovimientoActivoBean(MovimientoActivoBean movimientoActivoBean) {
        this.movimientoActivoBean = movimientoActivoBean;
    }

    public List<UsuarioBean> getListaUsuarioBean() {
        if (listaUsuarioBean == null) {
            listaUsuarioBean = new ArrayList<>();
        }
        return listaUsuarioBean;
    }

    public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
        this.listaUsuarioBean = listaUsuarioBean;
    }

    public InventarioActivoBean getInventarioActivoFiltroBean() {
        if (inventarioActivoFiltroBean == null) {
            inventarioActivoFiltroBean = new InventarioActivoBean();
        }
        return inventarioActivoFiltroBean;
    }

    public void setInventarioActivoFiltroBean(InventarioActivoBean inventarioActivoFiltroBean) {
        this.inventarioActivoFiltroBean = inventarioActivoFiltroBean;
    }

    public List<CodigoBean> getListaTipoMovActivo() {
        if (listaTipoMovActivo == null) {
            listaTipoMovActivo = new ArrayList<>();
        }
        return listaTipoMovActivo;
    }

    public void setListaTipoMovActivo(List<CodigoBean> listaTipoMovActivo) {
        this.listaTipoMovActivo = listaTipoMovActivo;
    }

    public List<CodigoBean> getListaTipoDuracion() {
        if (listaTipoDuracion == null) {
            listaTipoDuracion = new ArrayList<>();
        }
        return listaTipoDuracion;
    }

    public void setListaTipoDuracion(List<CodigoBean> listaTipoDuracion) {
        this.listaTipoDuracion = listaTipoDuracion;
    }

    public Boolean getFlgDuracion() {
        return flgDuracion;
    }

    public void setFlgDuracion(Boolean flgDuracion) {
        this.flgDuracion = flgDuracion;
    }

    public MovimientoActivoBean getMovimientoActivoFiltroBean() {
        if (movimientoActivoFiltroBean == null) {

            movimientoActivoFiltroBean = new MovimientoActivoBean();

        }
        return movimientoActivoFiltroBean;
    }

    public void setMovimientoActivoFiltroBean(MovimientoActivoBean movimientoActivoFiltroBean) {
        this.movimientoActivoFiltroBean = movimientoActivoFiltroBean;
    }

    public List<MovimientoActivoBean> getListaMovimientoActivoFiltroBean() {
        if (listaMovimientoActivoFiltroBean == null) {

            listaMovimientoActivoFiltroBean = new ArrayList<>();

        }
        return listaMovimientoActivoFiltroBean;
    }

    public void setListaMovimientoActivoFiltroBean(List<MovimientoActivoBean> listaMovimientoActivoFiltroBean) {
        this.listaMovimientoActivoFiltroBean = listaMovimientoActivoFiltroBean;
    }

    public Integer getIdTipoDuracion() {
        return idTipoDuracion;
    }

    public void setIdTipoDuracion(Integer idTipoDuracion) {
        this.idTipoDuracion = idTipoDuracion;
    }

    public List<MotivoMovimientoBean> getListaMotivoMovimientoBean() {
        if (listaMotivoMovimientoBean == null) {
            listaMotivoMovimientoBean = new ArrayList<>();
        }
        return listaMotivoMovimientoBean;
    }

    public void setListaMotivoMovimientoBean(List<MotivoMovimientoBean> listaMotivoMovimientoBean) {
        this.listaMotivoMovimientoBean = listaMotivoMovimientoBean;
    }

    public List<InventarioActivoBean> getListaInventarioActivoBean() {
        if (listaInventarioActivoBean == null) {
            listaInventarioActivoBean = new ArrayList<>();
        }
        return listaInventarioActivoBean;
    }

    public void setListaInventarioActivoBean(List<InventarioActivoBean> listaInventarioActivoBean) {
        this.listaInventarioActivoBean = listaInventarioActivoBean;
    }

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean ==null) {
            entidadBean= new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

}
