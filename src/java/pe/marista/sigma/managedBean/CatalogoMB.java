package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.dom4j.CDATA;
import org.primefaces.context.RequestContext;
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
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.InventarioActivoService;
import pe.marista.sigma.service.InventarioAlmacenService;

@ManagedBean
@ViewScoped
public class CatalogoMB extends BaseMB implements Serializable {
    // private CatalogoFamiliaBean catalogoFamiliaBean;
    // private List<CatalogoFamiliaBean> listaCatalogoFamiliaBean;
    //private CatalogoCategoriaBean catalogoCategoriaBean;
    //private List<CatalogoCategoriaBean> listaCatalogoCategoriaBean;
    //private List<CatalogoCategoriaBean> listaCatalogoCategoriaFiltroBean;

    private CatalogoBean catalogoBean;
    private CatalogoBean catalogoFiltroBean;
    private List<CatalogoBean> listaCatalogoFiltroBean;

    private List<CatalogoBean> listaCatalogoBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaUnidadMedidaBean;
    private List<CodigoBean> listaTipoUnidadMedidaBean;
    //private Integer idCatalogoFamilia;
    private InventarioAlmacenBean inventarioAlmacenBean;//AUMENTE

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

    // public List<CatalogoCategoriaBean> getListaCatalogoCategoriaFiltroBean() {
    //   return listaCatalogoCategoriaFiltroBean;
    //}
    //public void setListaCatalogoCategoriaFiltroBean(List<CatalogoCategoriaBean> listaCatalogoCategoriaFiltroBean) {
    //    this.listaCatalogoCategoriaFiltroBean = listaCatalogoCategoriaFiltroBean;
    //}
    // public Integer getIdCatalogoFamilia() {
    //     return idCatalogoFamilia;
    // }
    // public void setIdCatalogoFamilia(Integer idCatalogoFamilia) {
    //     this.idCatalogoFamilia = idCatalogoFamilia;
    // }
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

    public String actualizarCatalogo() {
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

    public void obtenerEntidadPorId(ViewEntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidad = new EntidadBean();

            entidad.setRuc(bean.getRuc());
            entidad.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());

            entidadBean = entidadService.obtenerEntidadPorId(entidad);
            //entidadBean = entidadService.buscarPorId(bean.getRuc());
            catalogoBean.setEntidadBean(entidadBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    // public CatalogoCategoriaBean getCatalogoCategoriaBean() {
    //   if(catalogoCategoriaBean == null)
    //   { catalogoCategoriaBean = new CatalogoCategoriaBean(); }
    //   return catalogoCategoriaBean;
    //}
    //public void setCatalogoCategoriaBean(CatalogoCategoriaBean catalogoCategoriaBean) {
    //  this.catalogoCategoriaBean = catalogoCategoriaBean;
    //}
    //public List<CatalogoCategoriaBean> getListaCatalogoCategoriaBean() {
    //  return listaCatalogoCategoriaBean;
    //}
    //public void setListaCatalogoCategoriaBean(List<CatalogoCategoriaBean> listaCatalogoCategoriaBean) {
    //  this.listaCatalogoCategoriaBean = listaCatalogoCategoriaBean;
    //}
    // public CatalogoFamiliaBean getCatalogoFamiliaBean() {
    //    if(catalogoFamiliaBean == null)
    //    { catalogoFamiliaBean = new CatalogoFamiliaBean(); }
    //    return catalogoFamiliaBean;
    // }
    // public void setCatalogoFamiliaBean(CatalogoFamiliaBean catalogoFamiliaBean) {
    //     this.catalogoFamiliaBean = catalogoFamiliaBean;
    // }
    //public List<CatalogoFamiliaBean> getListaCatalogoFamiliaBean() {
    //  return listaCatalogoFamiliaBean;
    //}
    //public void setListaCatalogoFamiliaBean(List<CatalogoFamiliaBean> listaCatalogoFamiliaBean) {
    //    this.listaCatalogoFamiliaBean = listaCatalogoFamiliaBean;
    //}
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
//                    inventarioAlmacen.setStockActual(inventarioAlmacen.getStockActual()); 
                    catalogoService.insertar(catalogoBean, inventarioAlmacen, inventarioActivo, usuarioSessionBean);

                } else {
                    CatalogoService catalogoService = BeanFactory.getCatalogoService();
                    catalogoBean.setModiPor(usuarioSessionBean.getUsuario());
                    catalogoService.actualizar(catalogoBean, inventarioAlmacen, inventarioActivo, usuarioSessionBean);

                }
                CatalogoService catalogoService = BeanFactory.getCatalogoService();
                listaCatalogoBean = catalogoService.obtenerTodos();
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

    public CatalogoBean getCatalogoFiltroBean() {
        if (catalogoFiltroBean == null) {
            catalogoFiltroBean = new CatalogoBean();
        }
        return catalogoFiltroBean;
    }

    public void setCatalogoFiltroBean(CatalogoBean catalogoFiltroBean) {
        this.catalogoFiltroBean = catalogoFiltroBean;
    }

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
//            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(entidadBean);
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

            this.obtenerTodosCF();
            this.obtenerTodosCC();
            this.obtenerTodosCat();
            getListaCatalogoFiltroBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //--------------------------------------- Metodos de CatalogoFamilia ---------------------------
    public void limpiarCF() {
        //    catalogoFamiliaBean = new CatalogoFamiliaBean();
    }

    public void obtenerTodosCF() {
        try {
            //     CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();
            //      listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCF(Integer id) {
        try {
            //    CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();
            //     catalogoFamiliaBean = catalogoFamiliaService.obtenerPorId(id); 
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCF(SelectEvent event) {
        try {
            //    catalogoFamiliaBean = (CatalogoFamiliaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

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

    public String guardarCF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
             //   CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();

                //    if (catalogoFamiliaBean.getIdCatalogoFamilia() == null)
                //   { catalogoFamiliaService.insertar(catalogoFamiliaBean); } 
                //   else 
                //   { catalogoFamiliaService.modificar(catalogoFamiliaBean); }
                //   listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                //   this.limpiarCF();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCF() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
            //    CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();

                //   catalogoFamiliaService.eliminar(catalogoFamiliaBean);
                //    listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarCF();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //--------------------------------------- Metodos de CatalogoCategoria ---------------------------
    //public void limpiarCC()
    // {
    //     catalogoCategoriaBean = new CatalogoCategoriaBean();
    //}
    public void obtenerTodosCC() {
        try {
            //      CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            //      listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCC(Integer id) {
        try {
            //      CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            //      catalogoCategoriaBean = catalogoCategoriaService.obtenerPorId(id); 
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCC(SelectEvent event) {
        try {
            //      catalogoCategoriaBean = (CatalogoCategoriaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String guardarCC() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
      //          CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();

                //          if (catalogoCategoriaBean.getIdCatalogoCategoria() == null)
                //          { catalogoCategoriaService.insertar(catalogoCategoriaBean); } 
                //          else 
                //          { catalogoCategoriaService.modificar(catalogoCategoriaBean); }
                //          listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                //          this.limpiarCC();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarCC() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                //          CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
                //          
                //          catalogoCategoriaService.eliminar(catalogoCategoriaBean);
                //          listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                //          this.limpiarCC(); 
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //AUMENTE
    public void obtenerCatalotoPorId(CatalogoBean bean) {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            catalogoBean = catalogoService.obtenerPorId(bean);
            //entidadBean = entidadService.buscarPorId(bean.getRuc());
            catalogoBean.setInventarioAlmacenBean(inventarioAlmacenBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //--------------------------------------- Metodos de Catalogo ---------------------------
    public void limpiarCat() {
        catalogoBean = new CatalogoBean();
    }

    public void obtenerTodosCat() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            listaCatalogoBean = catalogoService.obtenerTodos();

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

    public String guardarCat() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();

                if (catalogoBean.getIdCatalogo() == null) {
//                    catalogoService.insertar(catalogoBean);
                } else {
//                    catalogoService.actualizar(catalogoBean);
                }

                listaCatalogoBean = catalogoService.obtenerTodos();
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

    public String eliminarCat() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();

                catalogoService.eliminar(catalogoBean);
                listaCatalogoBean = catalogoService.obtenerTodos();
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

    public void obtenerCategoriaPorFamilia() {
        try {
            //      CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            //      listaCatalogoCategoriaFiltroBean = catalogoCategoriaService.obtenerPorFamilia(this.idCatalogoFamilia);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Metodos Nuevos
//    public void obtenerPorFiltroCat() {
//        try {
//            if(catalogoFiltroBean.getItem() != null && !catalogoFiltroBean.getItem().equals("")) {
//                catalogoFiltroBean.setItem(catalogoFiltroBean.getItem());
//            }
//            if(catalogoFiltroBean.getTipoCategoriaBean().getIdCodigo() != null && !catalogoFiltroBean.getTipoCategoriaBean().getIdCodigo().equals(0)) {
//                catalogoFiltroBean.getTipoCategoriaBean().setIdCodigo(catalogoFiltroBean.getTipoCategoriaBean().getIdCodigo());
//            }
//            if(catalogoFiltroBean.getTipoUnidadMedidaBean().getIdCodigo() != null && !catalogoFiltroBean.getTipoUnidadMedidaBean().getIdCodigo().equals(0)) {
//                catalogoFiltroBean.getTipoUnidadMedidaBean().setIdCodigo(catalogoFiltroBean.getTipoUnidadMedidaBean().getIdCodigo());
//            }
//            if(catalogoFiltroBean.getEntidadBean().getRuc() != null && !catalogoFiltroBean.getEntidadBean().getRuc().equals("")) {
//                catalogoFiltroBean.getEntidadBean().setRuc(catalogoFiltroBean.getEntidadBean().getRuc());
//            }
//            if(catalogoFiltroBean.getTipoMonedaBean().getIdCodigo() != null && !catalogoFiltroBean.getTipoMonedaBean().getIdCodigo().equals(0)) {
//                catalogoFiltroBean.getTipoMonedaBean().setIdCodigo(catalogoFiltroBean.getTipoMonedaBean().getIdCodigo());
//            }
//            CatalogoService catalogoService = BeanFactory.getCatalogoService();
//            listaCatalogoFiltroBean = catalogoService.obtenerPorFiltroCat(catalogoFiltroBean);
//            if (listaCatalogoFiltroBean.isEmpty()) {
//                System.out.println("lista vacia");
//            }
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//    }

    public void limpiarFiltroCatalogo() {
        try {
            catalogoFiltroBean = new CatalogoBean();
            listaCatalogoFiltroBean = new ArrayList<>();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
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

}
