package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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
import org.dom4j.CDATA;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CatalogoCategoriaBean;
import pe.marista.sigma.bean.CatalogoFamiliaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CatalogoCategoriaService;
import pe.marista.sigma.service.CatalogoFamiliaService;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.reporte.InventarioAlmacenGeneralRepBean;
import pe.marista.sigma.bean.reporte.MovimientoAlmacenGeneralRepBean;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.InventarioActivoService;
import pe.marista.sigma.service.InventarioAlmacenService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.service.UnidadOrganicaService;

public class CatalogoNewMB extends BaseMB implements Serializable {

    @PostConstruct
    public void cargaDatos() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCatalogoBean = new ArrayList<>();
            getListaViewEntidadProveedorBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            listaTipoUniMedBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUniMed"));
            listaTipoUnidadMedidaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUnidadMedida"));
            listaUnidadMedidaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoUnidadMedida"));
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaViewEntidadProveedorBean = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_PROVEEDOR, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CodigoBean bean : listaTipoMonedaBean) {
                if (bean.getCodigo().equals("Soles")) {
                    getCatalogoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoMoneda = bean.getIdCodigo();
                }
            }
            for (CodigoBean bean : listaTipoUnidadMedidaBean) {
                if (bean.getCodigo().equals("Unidad")) {
                    getCatalogoBean().getTipoUnidadMedidaBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoUniMed = bean.getIdCodigo();
                }
            }
            this.obtenerTodosCat();
            getListaCatalogoFiltroBean();
            getEntidadBeanNew();
            getEntidadBeanNew().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getInventarioAlmacenFiltroBean();
            getInventarioAlmacenFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPersonalFiltroBean();
            getPersonalFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            listaUniNegUniOrgBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            EntidadBean entidad = new EntidadBean();
            entidad.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEntidadBean = entidadService.obtenerEntidadPorFiltro(entidad);

            UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            listaUnidadOrganicaBean = unidadOrganicaService.obtenerTodos();

            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioSessionBean);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.ENCARGADO_LOG)) {
                    this.flgLogistica = true;
                    break;
                } else {
                    this.flgLogistica = false;
                }
            }

            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
            if (!flgLogistica) {
                System.out.println(">>> SIN PERFIL LOGISTICO");
                if (usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null) {
                    getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                    if (listaInventarioAlmacenBean.isEmpty()) {
                        listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                    }
                } else {
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                }
            } else if (flgLogistica) {
                System.out.println(">>> PERFIL LOGISTICO");
                getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                if (listaInventarioAlmacenBean.isEmpty()) {
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                }
            }
            obtenerBanderas();

            //MOVIMIENTOS DE ALMACEN
            String idInventario = null;
            this.fechaSolicitud = new GregorianCalendar();
            getMovimientoAlmacenBean().setFechaMov(fechaSolicitud.getTime());
            getMovimientoAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMovimientoAlmacenBean().setFechaMov(fechaSolicitud.getTime());
            idInventario = inventarioAlmacenService.obtenerUltimo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idInventario.equals(null)) {
                getMovimientoAlmacenBean().setIdMovimientoAyuda(idInventario);
            }
            getInventarioAlmacenBean();
            getInventarioAlmacenBean().getCatalogoBean().getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
            setFiltroAlmacen(1);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES Y LISTAS
    private CatalogoBean catalogoBean;
    private CatalogoBean catalogoFiltroBean;
    private List<CatalogoBean> listaCatalogoFiltroBean;

    private List<CatalogoBean> listaCatalogoBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaUnidadMedidaBean;
    private List<CodigoBean> listaTipoUnidadMedidaBean;
    private InventarioAlmacenBean inventarioAlmacenBean;

    private UsuarioBean usuarioSessionBean;
    private Double precioRef;
    private List<CodigoBean> listaTipoUniMedBean;
    private InventarioActivoBean inventarioActivoBean;
    private List<InventarioAlmacenBean> listaInventarioAlmacenBean;
    private List<InventarioActivoBean> listaInventarioActivoBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<EntidadBean> listaEntidadBean;
    private List<ViewEntidadBean> listaViewEntidadProveedorBean;
    private EntidadBean entidadBean;

    private Integer idTipoMoneda;
    private Integer idTipoUniMed;
    //ayuda
    private Boolean flgInvAlm = false;
    private Boolean flgInvAct = false;
    private Boolean flgServ = false;

    private EntidadBean entidadBeanNew;
    private InventarioAlmacenBean inventarioAlmacenFiltroBean;
    private List<MovimientoAlmacenBean> listaMovimientoAlmacenBean;
    private List<InventarioAlmacenBean> listaColorAlmacen;

    private String idTipoSol;
    private String idTipoRespCheque;

    private Boolean flgFiltroPersonal = false;
    private Boolean flgFiltroPersona = false;
    private Boolean flgFiltroProve = false;
    private Boolean flgSoli = true;
    private Boolean flgIgualSoli = false;

    private PersonalBean personalFiltroBean;
    private List<UniNegUniOrgBean> listaUniNegUniOrgBean;
    private List<PersonalBean> listaPersonalBean;

    private MovimientoAlmacenBean movimientoAlmacenBean;
    private InventarioAlmacenBean inventarioAlmacen;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private List<PerfilBean> listaPerfilBean;
    private Boolean flgLogistica = false;
    private Calendar fechaSolicitud;
    private Boolean flgGestorSoli = false;
    private InventarioAlmacenBean inventarioFiltroBean;
    private EntidadBean entidadFiltroBean;
    private List<PersonaBean> listaPersonaBean;
    private PersonaBean personaFiltroBean;

    //AYUDA
    private Integer filtroAlmacen;

    //METODOS 
    public void rowSelect(SelectEvent event) {
        try {

            catalogoBean = (CatalogoBean) event.getObject();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            mostarPanelCatalogo();
            catalogoBean = catalogoService.obtenerCatalogoPorId(catalogoBean);
            if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18201) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                inventarioAlmacenBean = inventarioAlmacenService.obtenerPorId(catalogoBean.getIdCatalogo());
            } else {
                InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
                inventarioActivoBean = inventarioActivoService.obtenerInventarioPorId(catalogoBean.getInventarioActivoBean().getIdInventarioActivo());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //AUMENTE
    public void obtenerCatalotoPorId(CatalogoBean bean) {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean = catalogoService.obtenerPorId(bean);
            catalogoBean.setInventarioAlmacenBean(inventarioAlmacenBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //METODOS CATALOGO
    public void limpiarCat() {
        try {
            catalogoBean = new CatalogoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosCat() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaCatalogoBean = catalogoService.obtenerCatalogoPorAlmacen(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCat(CatalogoBean catBean) {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean = catalogoService.obtenerPorId(catBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCat(SelectEvent event) {
        try {
            catalogoBean = (CatalogoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarCat() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                catalogoService.eliminar(catalogoBean);
                listaCatalogoBean = catalogoService.obtenerCatalogoPorAlmacen(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarCat();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarFiltroCatalogo() {
        try {
            catalogoFiltroBean = new CatalogoBean();
            listaCatalogoFiltroBean = new ArrayList<>();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public String actualizarCatalogo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                listaCatalogoBean = catalogoService.obtenerCatalogoPorAlmacen(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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
            catalogoBean.setEntidadBean(entidadBean);
            inventarioAlmacenBean.getCatalogoBean().setEntidadBean(entidadBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String actualizarEntidades() {
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

    public void mostarPanelCatalogo() {
        try {
            if (catalogoBean.getTipoCategoriaBean() != null) {
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18201) {
                    this.flgInvAlm = true;
                    this.flgInvAct = false;
                    this.flgServ = false;
                }
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18202) {
                    this.flgInvAlm = false;
                    this.flgInvAct = true;
                    this.flgServ = false;
                }
                if (catalogoBean.getTipoCategoriaBean().getIdCodigo() == 18203) {
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

    public String grabar() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
                InventarioActivoBean inventarioActivo = new InventarioActivoBean();
                inventarioActivo = inventarioActivoBean;
                inventarioAlmacen = inventarioAlmacenBean;

                if (catalogoBean.getIdCatalogo() == null) {
                    CatalogoService catalogoService = BeanFactory.getCatalogoService();
                    catalogoBean.setCreaPor(usuarioSessionBean.getUsuario());
                    catalogoService.insertar(catalogoBean, inventarioAlmacen, inventarioActivo, usuarioSessionBean);

                } else {
                    CatalogoService catalogoService = BeanFactory.getCatalogoService();
                    catalogoBean.setModiPor(usuarioSessionBean.getUsuario());
                    catalogoService.actualizar(catalogoBean, inventarioAlmacen, inventarioActivo, usuarioSessionBean);

                }
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                listaCatalogoBean = catalogoService.obtenerCatalogoPorAlmacen(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarCatalogo();
                limpiarInventarioActivo();
                limpiarInventarioAlmacen();
                for (CodigoBean bean : listaTipoMonedaBean) {
                    if (bean.getCodigo().equals("Soles")) {
                        getCatalogoBean().getTipoMonedaBean().setIdCodigo(bean.getIdCodigo());
                        this.idTipoMoneda = bean.getIdCodigo();
                    }
                }
                for (CodigoBean bean : listaTipoUnidadMedidaBean) {
                    if (bean.getCodigo().equals("Unidad")) {
                        getCatalogoBean().getTipoUnidadMedidaBean().setIdCodigo(bean.getIdCodigo());
                        this.idTipoUniMed = bean.getIdCodigo();
                    }
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

    public void limpiarCatalogo() {
        try {
            catalogoBean = new CatalogoBean();
            precioRef = 0.0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarInventarioAlmacen() {
        try {
            inventarioAlmacenBean = new InventarioAlmacenBean();
            precioRef = 0.0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarInventarioActivo() {
        try {
            inventarioActivoBean = new InventarioActivoBean();
            precioRef = 0.0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //METODOS DE ENTIDAD
    public void comprobarEntidad() {
        try {
            EntidadBean entidad = new EntidadBean();
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidad.getUnidadNegocioBean().setUniNeg(entidadBeanNew.getUnidadNegocioBean().getUniNeg());
            entidad.setRuc(entidadBeanNew.getRuc());
            entidad = entidadService.obtenerEntidadPorId(entidad);
            if (entidad != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                insertarEntidad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void insertarEntidad() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadBeanNew.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            entidadBeanNew.setCreaPor(usuarioSessionBean.getUsuario());
            entidadService.insertarEntidad(entidadBeanNew);
            limpiarEntidad();
            listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEntidad() {
        try {
            entidadBeanNew = new EntidadBean();
            entidadBeanNew.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //METODOS INVENTARIO ALMACEN
    public String grabarInventario() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                CatalogoBean catalogo = new CatalogoBean();
//                catalogo = catalogoBean;
                validarCatalogo(getCatalogoBean());
                if (inventarioAlmacenBean.getIdAlmacen() == null) {
                    inventarioAlmacenBean.setCreaPor(usuarioSessionBean.getUsuario());
                    inventarioAlmacenBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    inventarioAlmacenBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    inventarioAlmacenService.insertar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);

                    if (usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null) {
                        System.out.println(">>>>" + usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                        getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                        listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                        if (listaInventarioAlmacenBean.isEmpty()) {
                            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(getInventarioAlmacenBean());
                        }
                    } else {
                        listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(getInventarioAlmacenBean());
                    }

                    obtenerBanderas();
                } else {
                    inventarioAlmacenBean.setModiPor(usuarioSessionBean.getUsuario());
                    inventarioAlmacenService.actualizar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);

                    InventarioAlmacenBean inventarioAlmacen = new InventarioAlmacenBean();
                    if (usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null) {
                        System.out.println(">>>>" + usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                        getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                        listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                        if (listaInventarioAlmacenBean.isEmpty()) {
                            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                        }
                    } else {
                        listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                    }

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

    public void validarCatalogo(Object obj) {
        try {
            System.out.println(">>>" + inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            System.out.println(">>>" + catalogoBean.getEntidadBean().getRuc());
            System.out.println(">>>" + catalogoBean.getIdCatalogo());
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean.getEntidadBean().setRuc(inventarioAlmacenBean.getCatalogoBean().getEntidadBean().getRuc());
            catalogoService.modificarPorEntidad(catalogoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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

    //REPORTES
    public void imprimirTodosPdfGeneral() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();

            listaInventarioAlmacenBean = inventarioAlmacenService.obtenerTodos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteInventarioAlmacen.jasper");
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosGeneralMoviPdf() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();

            listaMovimientoAlmacenBean = inventarioAlmacenService.obtenerTodosMovi(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMovimientoAlmacenGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MovimientoAlmacenGeneralRepBean> listaMovimientoGeneral = new ArrayList<>();
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
//                }
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

    public void modificarTablaInventario(RowEditEvent event) {
        Integer nuevosItems = 0;
        Integer stockNuevo = 0;
        try {
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
            inventarioAlmacenBean.setNroAlmacen((((InventarioAlmacenBean) event.getObject()).getNroAlmacen()));
//            inventario = inventarioAlmacenService.obtenerColorAlmacenPorNro(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), inventarioAlmacenBean.getNroAlmacen());
//            inventarioAlmacenBean.setNroAlmacen(inventario.getNroAlmacen());
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

    public void obtenerFiltroPersonal() {
        try {
            int estado = 0;
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalFiltroBean.getCodPer() != null && !personalFiltroBean.getCodPer().equals("")) {
                personalFiltroBean.setCodPer(personalFiltroBean.getCodPer().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApepat() != null && !personalFiltroBean.getApepat().equals("")) {
                personalFiltroBean.setApepat(personalFiltroBean.getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApemat() != null && !personalFiltroBean.getApemat().equals("")) {
                personalFiltroBean.setApemat(personalFiltroBean.getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getNombre() != null && !personalFiltroBean.getNombre().equals("")) {
                personalFiltroBean.setNombre(personalFiltroBean.getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg() != null && !personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonalBean = legajoService.obtenerFiltroPersonal(personalFiltroBean);
                if (listaPersonalBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void agregar(CatalogoBean bean) {
        try {
            inventarioAlmacenBean.getCatalogoBean().setIdCatalogo(bean.getIdCatalogo());
            inventarioAlmacenBean.getCatalogoBean().setItem(bean.getItem());
            if (bean.getEntidadBean().getRuc() != null) {
                inventarioAlmacenBean.getCatalogoBean().setEntidadBean(bean.getEntidadBean());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object obj) {
        try {
            inventarioAlmacen = (InventarioAlmacenBean) obj;
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            inventarioAlmacen = inventarioAlmacenService.obtenerPorId(inventarioAlmacen.getIdAlmacen());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarInventario() {
        try {
            InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
            inventarioAlmacenService.eliminarInvAlmacen(inventarioAlmacen.getIdAlmacen());
            if (usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null) {
                getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                if (listaInventarioAlmacenBean.isEmpty()) {
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
                }
            } else {
                listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacen);
            }
            obtenerBanderas();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String grabarMovimiento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                System.out.println("MET" + getMovimientoAlmacenBean().getNroMovimiento());
                if (getMovimientoAlmacenBean().getNroMovimiento() == null) {
                    movimientoAlmacenBean.setCreaPor(usuarioSessionBean.getUsuario());
                    movimientoAlmacenBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
                    inventarioAlmacenService.insertarMovimiento(movimientoAlmacenBean, listaMovimientoAlmacenBean, usuarioSessionBean);
                }
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

    public void limpiarMovimiento() {
        try {
            movimientoAlmacenBean = new MovimientoAlmacenBean();
            getCatalogoBean();
            getMovimientoAlmacenBean().setEntregadoPor(usuarioSessionBean.getPersonalBean().getNombreCompleto());
            getMovimientoAlmacenBean().setPersonalBean(usuarioSessionBean.getPersonalBean());
            this.fechaSolicitud = new GregorianCalendar();
            getMovimientoAlmacenBean().setFechaMov(fechaSolicitud.getTime());
            listaMovimientoAlmacenBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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

    //METODOS DE MOVIMIENTO ACTIVO
    public void limpiarPersonalFiltro() {
        try {
            personalFiltroBean = new PersonalBean();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            personalFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonaFiltro() {
        try {
            listaPersonaBean = new ArrayList<>();
            personaFiltroBean = new PersonaBean();
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroEntidad() {
        try {
            entidadFiltroBean = new EntidadBean();
            entidadFiltroBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            listaEntidadBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarAlmacen() {
        try {
            if (getFiltroAlmacen() != null) {
                InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (filtroAlmacen.equals(1)) {
                    getInventarioAlmacenBean().setNroAlmacen(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerAlmacenPorNro(getInventarioAlmacenBean());
                    obtenerBanderas();
                } else if (filtroAlmacen.equals(2)) {
                    getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(getInventarioAlmacenBean());
                    obtenerBanderas();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //METODOS GET Y SET
    public List<CodigoBean> getListaTipoMonedaBean() {
        return listaTipoMonedaBean;
    }

    public Double getPrecioRef() {
        return precioRef;
    }

    public void setPrecioRef(Double precioRef) {
        this.precioRef = precioRef;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public List<CodigoBean> getListaUnidadMedidaBean() {
        return listaUnidadMedidaBean;
    }

    public void setListaUnidadMedidaBean(List<CodigoBean> listaUnidadMedidaBean) {
        this.listaUnidadMedidaBean = listaUnidadMedidaBean;
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

    public InventarioAlmacenBean getInventarioAlmacenBean() {
        if (inventarioAlmacenBean == null) {
            inventarioAlmacenBean = new InventarioAlmacenBean();
        }
        return inventarioAlmacenBean;
    }

    public void setInventarioAlmacenBean(InventarioAlmacenBean inventarioAlmacenBean) {
        this.inventarioAlmacenBean = inventarioAlmacenBean;
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

    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
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

    public List<CodigoBean> getListaTipoUniMedBean() {
        if (listaTipoUniMedBean == null) {
            listaTipoUniMedBean = new ArrayList<>();
        }
        return listaTipoUniMedBean;
    }

    public void setListaTipoUniMedBean(List<CodigoBean> listaTipoUniMedBean) {
        this.listaTipoUniMedBean = listaTipoUniMedBean;
    }

    public List<CodigoBean> getListaTipoUnidadMedidaBean() {
        if (listaTipoUnidadMedidaBean == null) {
            listaTipoUnidadMedidaBean = new ArrayList<>();
        }
        return listaTipoUnidadMedidaBean;
    }

    public void setListaTipoUnidadMedidaBean(List<CodigoBean> listaTipoUnidadMedidaBean) {
        this.listaTipoUnidadMedidaBean = listaTipoUnidadMedidaBean;
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

    public List<InventarioActivoBean> getListaInventarioActivoBean() {
        if (listaInventarioActivoBean == null) {
            listaInventarioActivoBean = new ArrayList<>();
        }
        return listaInventarioActivoBean;
    }

    public void setListaInventarioActivoBean(List<InventarioActivoBean> listaInventarioActivoBean) {
        this.listaInventarioActivoBean = listaInventarioActivoBean;
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

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
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

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public Integer getIdTipoUniMed() {
        return idTipoUniMed;
    }

    public void setIdTipoUniMed(Integer idTipoUniMed) {
        this.idTipoUniMed = idTipoUniMed;
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

    public List<CatalogoBean> getListaCatalogoFiltroBean() {
        return listaCatalogoFiltroBean;
    }

    public void setListaCatalogoFiltroBean(List<CatalogoBean> listaCatalogoFiltroBean) {
        this.listaCatalogoFiltroBean = listaCatalogoFiltroBean;
    }

    public EntidadBean getEntidadBeanNew() {
        if (entidadBeanNew == null) {
            entidadBeanNew = new EntidadBean();
        }
        return entidadBeanNew;
    }

    public void setEntidadBeanNew(EntidadBean entidadBeanNew) {
        this.entidadBeanNew = entidadBeanNew;
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

    public List<MovimientoAlmacenBean> getListaMovimientoAlmacenBean() {
        if (listaMovimientoAlmacenBean == null) {
            listaMovimientoAlmacenBean = new ArrayList<>();
        }
        return listaMovimientoAlmacenBean;
    }

    public void setListaMovimientoAlmacenBean(List<MovimientoAlmacenBean> listaMovimientoAlmacenBean) {
        this.listaMovimientoAlmacenBean = listaMovimientoAlmacenBean;
    }

    public List<InventarioAlmacenBean> getListaColorAlmacen() {
        if (listaColorAlmacen == null) {
            listaColorAlmacen = new ArrayList<>();
        }
        return listaColorAlmacen;
    }

    public void setListaColorAlmacen(List<InventarioAlmacenBean> listaColorAlmacen) {
        this.listaColorAlmacen = listaColorAlmacen;
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

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrgBean() {
        if (listaUniNegUniOrgBean == null) {
            listaUniNegUniOrgBean = new ArrayList<>();
        }
        return listaUniNegUniOrgBean;
    }

    public void setListaUniNegUniOrgBean(List<UniNegUniOrgBean> listaUniNegUniOrgBean) {
        this.listaUniNegUniOrgBean = listaUniNegUniOrgBean;
    }

    public List<PersonalBean> getListaPersonalBean() {
        if (listaPersonalBean == null) {
            listaPersonalBean = new ArrayList<>();
        }
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
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

    public InventarioAlmacenBean getInventarioAlmacen() {
        if (inventarioAlmacen == null) {
            inventarioAlmacen = new InventarioAlmacenBean();
        }
        return inventarioAlmacen;
    }

    public void setInventarioAlmacen(InventarioAlmacenBean inventarioAlmacen) {
        this.inventarioAlmacen = inventarioAlmacen;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaBean() {
        if (listaUnidadOrganicaBean == null) {
            listaUnidadOrganicaBean = new ArrayList<>();
        }
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UnidadOrganicaBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
    }

    public List<PerfilBean> getListaPerfilBean() {
        if (listaPerfilBean == null) {
            listaPerfilBean = new ArrayList<>();
        }
        return listaPerfilBean;
    }

    public void setListaPerfilBean(List<PerfilBean> listaPerfilBean) {
        this.listaPerfilBean = listaPerfilBean;
    }

    public Boolean getFlgLogistica() {
        return flgLogistica;
    }

    public void setFlgLogistica(Boolean flgLogistica) {
        this.flgLogistica = flgLogistica;
    }

    public Calendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Calendar fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Boolean getFlgGestorSoli() {
        return flgGestorSoli;
    }

    public void setFlgGestorSoli(Boolean flgGestorSoli) {
        this.flgGestorSoli = flgGestorSoli;
    }

    public InventarioAlmacenBean getInventarioFiltroBean() {
        if (inventarioFiltroBean == null) {
            inventarioFiltroBean = new InventarioAlmacenBean();
        }
        return inventarioFiltroBean;
    }

    public void setInventarioFiltroBean(InventarioAlmacenBean inventarioFiltroBean) {
        this.inventarioFiltroBean = inventarioFiltroBean;
    }

    public EntidadBean getEntidadFiltroBean() {
        return entidadFiltroBean;
    }

    public void setEntidadFiltroBean(EntidadBean entidadFiltroBean) {
        this.entidadFiltroBean = entidadFiltroBean;
    }

    public List<PersonaBean> getListaPersonaBean() {
        if (listaPersonaBean == null) {
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
    }

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
    }

    public Integer getFiltroAlmacen() {
        return filtroAlmacen;
    }

    public void setFiltroAlmacen(Integer filtroAlmacen) {
        this.filtroAlmacen = filtroAlmacen;
    }

}
