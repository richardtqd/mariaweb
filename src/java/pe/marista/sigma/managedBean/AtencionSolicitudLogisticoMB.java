package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.bean.ActivoFijoBean;
import pe.marista.sigma.bean.CatalogoBean;
import pe.marista.sigma.bean.CatalogoCategoriaBean;
import pe.marista.sigma.bean.CatalogoFamiliaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ObjOperativoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanObjActOperativaBean;
import pe.marista.sigma.bean.PlanOperativoBean;
import pe.marista.sigma.bean.SolicitudLogDetalleBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.CatalogoCategoriaService;
import pe.marista.sigma.service.CatalogoFamiliaService;
import pe.marista.sigma.service.CatalogoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.LoginService;
import pe.marista.sigma.service.ObjetivoOperativoService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.PlanOperativoService;
import pe.marista.sigma.service.SolicitudLogisticoDetalleService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.service.CentroCostoService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.MatriculaService;

public class AtencionSolicitudLogisticoMB extends BaseMB implements Serializable {

    //-----------------------------------------------
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoAprobBean;
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoDespBean;
    private List<CatalogoBean> listaCatalogoBean;
    private CatalogoBean catalogoBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private SolicitudLogisticoBean solicitudLogisticoFiltroBean;
    //private List<CodigoBean> listaTipoSolicitudBean;
    private List<CodigoBean> listaTipoCategoriaBean;
    private List<CodigoBean> listaTipoEstadoBean;
    private List<CodigoBean> listaTipoEstadoDespachoBean;
    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoPrioridadBean;
    private List<CodigoBean> listaTipoEstadoDetalleBean;
    private Calendar fechaSolicitud;
    private String fechaSolicitudView;
    private ObjOperativoBean objOperativoBean;
    private List<PersonalBean> listaPersonalBean;
    private List<UsuarioBean> listaUsuarioBean;
    private UsuarioBean usuarioBean;//El objeto usuarioBean viene con PersonalBean
    private UsuarioBean usuarioSessionBean;//El objeto usuarioBean viene con PersonalBean
    private List<PersonalBean> listaPersonalCompleteBean;
    private List<PersonalBean> listaPersonalFiltroBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaCompleteBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaFiltroBean;
    private List<PlanOperativoBean> listaPlanOperativoBean;
    private List<ObjOperativoBean> listaObjOperativoBean;
    private List<ActividadBean> listaActividadBean;
    private Integer idUniOrg;
    private String uniNeg;
    private ActividadBean actividadBean;
    private Integer anio;
    //private CodigoBean tipoSolicitudBean;
    private CodigoBean tipoCategoriaBean;
    private List<SolicitudLogDetalleBean> listaSolicitudLogDetalleBean;
    private Integer idTipoEstado;
    private Double sumaImporte = 0.0;
    private CatalogoFamiliaBean catalogoFamiliaBean;
    //variables para el manto de Catalogo, Categoria y Familia
    //private CatalogoFamiliaBean catalogoFamiliaBean;
    private List<CatalogoFamiliaBean> listaCatalogoFamiliaBean;
    private CatalogoCategoriaBean catalogoCategoriaBean;
    private List<CatalogoCategoriaBean> listaCatalogoCategoriaBean;
    private List<CatalogoCategoriaBean> listaCatalogoCategoriaFiltroBean;
    //private CatalogoBean catalogoBean;
    //private List<CatalogoBean> listaCatalogoBean;
    //private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaUnidadMedidaBean;
    private Integer idCatalogoFamilia;
    private PlanOperativoBean planOperativoBean;
    private Integer idObjOperativo;
    private CodigoBean codigoBean;
    private SolicitudLogDetalleBean solicitudLogDetalleBean;
    private TipoSolicitudBean tipoSolicitudBean;
    private List<TipoSolicitudBean> listaTipoSolicitudBean;
    private ConceptoBean conceptoBean;
    private Integer valor = 1;

    //ayuda
    private Boolean flgDivision = false;
    private Boolean flgPondarizado = false;
    private Boolean flgPersonalizado = false;

    //getter and setter
    //concepto
    private List<ConceptoBean> listaConceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private TipoConceptoBean tipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private PlanContableBean planContableBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadInicialBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadPrimariaBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadSecundariaBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBachillerBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private List<CodigoBean> listaTipoDistribucionCRBean;
    private CodigoBean tipoDistribucionBean;
    private Double cantidadDivision = 0.0;
    private Integer cantidadPonderacion = 0;

    private Double cantidadPerzonalizada = 0.0;

    private double ponderacionI = 0;
    private double ponderacionP = 0;
    private double ponderacionS = 0;
    private double ponderacionB = 0;
    //lista matricula
    private List<ViewMatriculaBean> listaViewMatriculaBean;
    private ViewMatriculaBean viewMatriculaBean;

//    private CodigoB
    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
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

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
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

    public List<CatalogoFamiliaBean> getListaCatalogoFamiliaBean() {
        return listaCatalogoFamiliaBean;
    }

    public void setListaCatalogoFamiliaBean(List<CatalogoFamiliaBean> listaCatalogoFamiliaBean) {
        this.listaCatalogoFamiliaBean = listaCatalogoFamiliaBean;
    }

    public CatalogoCategoriaBean getCatalogoCategoriaBean() {
        if (catalogoCategoriaBean == null) {
            catalogoCategoriaBean = new CatalogoCategoriaBean();
        }
        return catalogoCategoriaBean;
    }

    public void setCatalogoCategoriaBean(CatalogoCategoriaBean catalogoCategoriaBean) {
        this.catalogoCategoriaBean = catalogoCategoriaBean;
    }

    public List<CatalogoCategoriaBean> getListaCatalogoCategoriaBean() {
        return listaCatalogoCategoriaBean;
    }

    public void setListaCatalogoCategoriaBean(List<CatalogoCategoriaBean> listaCatalogoCategoriaBean) {
        this.listaCatalogoCategoriaBean = listaCatalogoCategoriaBean;
    }

    public List<CatalogoCategoriaBean> getListaCatalogoCategoriaFiltroBean() {
        return listaCatalogoCategoriaFiltroBean;
    }

    public void setListaCatalogoCategoriaFiltroBean(List<CatalogoCategoriaBean> listaCatalogoCategoriaFiltroBean) {
        this.listaCatalogoCategoriaFiltroBean = listaCatalogoCategoriaFiltroBean;
    }

    public List<CodigoBean> getListaUnidadMedidaBean() {
        return listaUnidadMedidaBean;
    }

    public void setListaUnidadMedidaBean(List<CodigoBean> listaUnidadMedidaBean) {
        this.listaUnidadMedidaBean = listaUnidadMedidaBean;
    }

    public Integer getIdCatalogoFamilia() {
        return idCatalogoFamilia;
    }

    public void setIdCatalogoFamilia(Integer idCatalogoFamilia) {
        this.idCatalogoFamilia = idCatalogoFamilia;
    }

    public CatalogoFamiliaBean getCatalogoFamiliaBean() {
        if (catalogoFamiliaBean == null) {
            catalogoFamiliaBean = new CatalogoFamiliaBean();
        }
        return catalogoFamiliaBean;
    }

    public void setCatalogoFamiliaBean(CatalogoFamiliaBean catalogoFamiliaBean) {
        this.catalogoFamiliaBean = catalogoFamiliaBean;
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

    public List<CodigoBean> getListaTipoEstadoDespachoBean() {
        return listaTipoEstadoDespachoBean;
    }

    public void setListaTipoEstadoDespachoBean(List<CodigoBean> listaTipoEstadoDespachoBean) {
        this.listaTipoEstadoDespachoBean = listaTipoEstadoDespachoBean;
    }

    public List<CodigoBean> getListaTipoEstadoDetalleBean() {
        return listaTipoEstadoDetalleBean;
    }

    public void setListaTipoEstadoDetalleBean(List<CodigoBean> listaTipoEstadoDetalleBean) {
        this.listaTipoEstadoDetalleBean = listaTipoEstadoDetalleBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoDespBean() {
        return listaSolicitudLogisticoDespBean;
    }

    public void setListaSolicitudLogisticoDespBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoDespBean) {
        this.listaSolicitudLogisticoDespBean = listaSolicitudLogisticoDespBean;
    }

    public Double getSumaImporte() {
        return sumaImporte;
    }

    public void setSumaImporte(Double sumaImporte) {
        this.sumaImporte = sumaImporte;
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

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public String getFechaSolicitudView() {
        return fechaSolicitudView;
    }

    public void setFechaSolicitudView(String fechaSolicitudView) {
        this.fechaSolicitudView = fechaSolicitudView;
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

    //   public CodigoBean getTipoSolicitudBean() {
    //     if (tipoSolicitudBean == null) {
    //       tipoSolicitudBean = new CodigoBean();
    //  }
    // return tipoSolicitudBean;
    // }
    // public void setTipoSolicitudBean(CodigoBean tipoSolicitudBean) {
    //    this.tipoSolicitudBean = tipoSolicitudBean;
    //}
    public CodigoBean getTipoCategoriaBean() {
        if (tipoCategoriaBean == null) {
            tipoCategoriaBean = new CodigoBean();
        }
        return tipoCategoriaBean;
    }

    public void setTipoCategoriaBean(CodigoBean tipoCategoriaBean) {
        this.tipoCategoriaBean = tipoCategoriaBean;
    }

    public Calendar getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Calendar fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public ActividadBean getActividadBean() {
        if (actividadBean == null) {
            actividadBean = new ActividadBean();
        }
        return actividadBean;
    }

    public void setActividadBean(ActividadBean actividadBean) {
        this.actividadBean = actividadBean;
    }

    public List<PlanOperativoBean> getListaPlanOperativoBean() {
        return listaPlanOperativoBean;
    }

    public void setListaPlanOperativoBean(List<PlanOperativoBean> listaPlanOperativoBean) {
        this.listaPlanOperativoBean = listaPlanOperativoBean;
    }

    public List<ObjOperativoBean> getListaObjOperativoBean() {
        if (listaObjOperativoBean == null) {
            listaObjOperativoBean = new ArrayList<>();
        }
        return listaObjOperativoBean;
    }

    public void setListaObjOperativoBean(List<ObjOperativoBean> listaObjOperativoBean) {
        this.listaObjOperativoBean = listaObjOperativoBean;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public List<ActividadBean> getListaActividadBean() {
        if (listaActividadBean == null) {
            listaActividadBean = new ArrayList<>();
        }
        return listaActividadBean;
    }

    public void setListaActividadBean(List<ActividadBean> listaActividadBean) {
        this.listaActividadBean = listaActividadBean;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaCompleteBean() {
        return listaUnidadOrganicaCompleteBean;
    }

    public void setListaUnidadOrganicaCompleteBean(List<UnidadOrganicaBean> listaUnidadOrganicaCompleteBean) {
        this.listaUnidadOrganicaCompleteBean = listaUnidadOrganicaCompleteBean;
    }

    public List<PersonalBean> getListaPersonalFiltroBean() {
        return listaPersonalFiltroBean;
    }

    public void setListaPersonalFiltroBean(List<PersonalBean> listaPersonalFiltroBean) {
        this.listaPersonalFiltroBean = listaPersonalFiltroBean;
    }

    public List<PersonalBean> getListaPersonalCompleteBean() {
        return listaPersonalCompleteBean;
    }

    public void setListaPersonalCompleteBean(List<PersonalBean> listaPersonalCompleteBean) {
        this.listaPersonalCompleteBean = listaPersonalCompleteBean;
    }

    public List<PersonalBean> getListaPersonalBean() {
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
    }

    public List<CodigoBean> getListaTipoPrioridadBean() {
        return listaTipoPrioridadBean;
    }

    public void setListaTipoPrioridadBean(List<CodigoBean> listaTipoPrioridadBean) {
        this.listaTipoPrioridadBean = listaTipoPrioridadBean;
    }

    public List<CodigoBean> getListaTipoMonedaBean() {
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public List<CodigoBean> getListaTipoEstadoBean() {
        return listaTipoEstadoBean;
    }

    public void setListaTipoEstadoBean(List<CodigoBean> listaTipoEstadoBean) {
        this.listaTipoEstadoBean = listaTipoEstadoBean;
    }

    //  public List<CodigoBean> getListaTipoSolicitudBean() {
    //    return listaTipoSolicitudBean;
    //}
    //public void setListaTipoSolicitudBean(List<CodigoBean> listaTipoSolicitudBean) {
    //  this.listaTipoSolicitudBean = listaTipoSolicitudBean;
    // }
    public List<CodigoBean> getListaTipoCategoriaBean() {
        if (listaTipoCategoriaBean == null) {
            listaTipoCategoriaBean = new ArrayList<>();
        }
        return listaTipoCategoriaBean;
    }

    public void setListaTipoCategoriaBean(List<CodigoBean> listaTipoCategoriaBean) {
        this.listaTipoCategoriaBean = listaTipoCategoriaBean;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaFiltroBean() {
        return listaUnidadOrganicaFiltroBean;
    }

    public void setListaUnidadOrganicaFiltroBean(List<UnidadOrganicaBean> listaUnidadOrganicaFiltroBean) {
        this.listaUnidadOrganicaFiltroBean = listaUnidadOrganicaFiltroBean;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaBean() {
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UnidadOrganicaBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
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

    public List<CatalogoBean> getListaCatalogoBean() {
        if (listaCatalogoBean == null) {
            listaCatalogoBean = new ArrayList<>();
        }
        return listaCatalogoBean;
    }

    public void setListaCatalogoBean(List<CatalogoBean> listaCatalogoBean) {
        this.listaCatalogoBean = listaCatalogoBean;
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

    public ObjOperativoBean getObjOperativoBean() {
        if (objOperativoBean == null) {
            objOperativoBean = new ObjOperativoBean();
        }
        return objOperativoBean;
    }

    public void setObjOperativoBean(ObjOperativoBean objOperativoBean) {
        this.objOperativoBean = objOperativoBean;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoBean() {
        return listaSolicitudLogisticoBean;
    }

    public void setListaSolicitudLogisticoBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoBean) {
        this.listaSolicitudLogisticoBean = listaSolicitudLogisticoBean;
    }

    public PlanOperativoBean getPlanOperativoBean() {
        if (planOperativoBean == null) {
            planOperativoBean = new PlanOperativoBean();
        }
        return planOperativoBean;
    }

    public void setPlanOperativoBean(PlanOperativoBean planOperativoBean) {
        this.planOperativoBean = planOperativoBean;
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

    @PostConstruct
    public void AtencionSolicitudLogisticoMB() {
        try {
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaSolicitudLogisticoBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            listaSolicitudLogisticoAprobBean = new ArrayList<>();
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();
            //---------- solicitud -------------
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoEstadoBean = codigoService.obtenerPorTipoSol(new TipoCodigoBean("TipoStatusReq"));
            listaTipoEstadoDespachoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoStatusReq"));
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
            listaTipoEstadoDetalleBean = codigoService.obtenerPorTipoDespacho(new TipoCodigoBean("TipoStatusDetReq"));
            listaTipoSolicitudBean = tipoSolicitudService.obtenerPorAmbitoPorUniNeg((new TipoSolicitudBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean(), MaristaConstantes.COD_REQ_LOG)));
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaUsuarioBean = usuarioService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadInicialBean = centroResponsabilidadService.obtenerCentroInicial();
            listaCentroResponsabilidadPrimariaBean = centroResponsabilidadService.obtenerCentroPrimaria();
            listaCentroResponsabilidadSecundariaBean = centroResponsabilidadService.obtenerCentroSecundaria();
            listaCentroResponsabilidadBachillerBean = centroResponsabilidadService.obtenerCentroBachiller();
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
            //  UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            //  listaUnidadOrganicaBean = unidadOrganicaService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ActividadService actividadService = BeanFactory.getActividadService();

            listaActividadBean = actividadService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaObjOperativoBean = objetivoOperativoService.obtenerTodos();
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            listaPlanOperativoBean = planOperativoService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //paso el estado borrador por defecto de lista cargada
            for (CodigoBean bean : listaTipoEstadoBean) {
                if (bean.getCodigo().equals("Borrador")) {
                    getSolicitudLogisticoBean().getTipoEstadoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoEstado = bean.getIdCodigo();
                }
            }

            this.cargarDatosSession();

            //metodos de cargado para el manto catalogo
            //this.obtenerTodosCF();
            //this.obtenerTodosCC();
            //this.obtenerTodosCat();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void cargarDatosSession() {
        try {
            //Paso los valores de sesion al bean solicitudLogisticoBean
            this.fechaSolicitud = new GregorianCalendar();
            getSolicitudLogisticoBean().setAnio(fechaSolicitud.get(Calendar.YEAR));
            getSolicitudLogisticoBean().setFechaSolicitud(fechaSolicitud.getTime());
            getSolicitudLogisticoBean().setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            getSolicitudLogisticoBean().setUnidadOrganicaBean(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean());
            getSolicitudLogisticoBean().setPersonalBean(usuarioSessionBean.getPersonalBean());
            //this.fechaSolicitudView = formato.format(fechaSolicitud.getTime());//fecha de vista 

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerUsuarioPorId(UsuarioBean bean) {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            usuarioBean = usuarioService.buscarPorId(bean.getUsuario());
            //Paso los valores del bean del popup al bean de guardar
            solicitudLogisticoBean.setPersonalBean(usuarioBean.getPersonalBean());
            solicitudLogisticoBean.setUnidadOrganicaBean(usuarioBean.getPersonalBean().getUnidadOrganicaBean());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerActPorId(ActividadBean bean) {
        try {
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadBean = actividadService.obtenerPorId(bean.getIdActividad());
            //Paso los valores del bean del popup al bean de guardar
            solicitudLogisticoBean.setActividadBean(actividadBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdObj(ObjOperativoBean bean) {
        try {
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            objOperativoBean = objetivoOperativoService.obtenerPorIdObj(bean.getIdObjOperativo());
            //Paso los valores del bean del popup al bean de guardar
            solicitudLogisticoBean.setObjOperativoBean(objOperativoBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //---------- metodos para la solicitud ----------------
    public void limpiar() {
        try {
            solicitudLogisticoBean = new SolicitudLogisticoBean();
            solicitudLogDetalleBean = new SolicitudLogDetalleBean();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            fechaSolicitudView = "";
            sumaImporte = 0.0;
            this.cargarDatosSession();
            solicitudLogisticoBean.getTipoEstadoBean().setIdCodigo(this.idTipoEstado);
            tipoConceptoBean = new TipoConceptoBean();
            solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean();
            solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean();
            solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean();
            

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //limpiar CR
    public void LimpiarCR() {
        try {
            solicitudLogisticoBean.setCentroRespInicialBean(null);
            solicitudLogisticoBean.setCentroRespPrimariaBean(null);
            solicitudLogisticoBean.setCentroRespSecundariaBean(null);
            solicitudLogisticoBean.setCentroRespBachillerBean(null);
            solicitudLogisticoBean.setTipoDistribucionBean(null);
            solicitudLogisticoBean.setMontoI(null);
            solicitudLogisticoBean.setMontoP(null);
            solicitudLogisticoBean.setMontoS(null);
            solicitudLogisticoBean.setMontoB(null);

        } catch (Exception e) {
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

    public void limpiarSolicitudFiltro() {
        try {
            listaSolicitudLogisticoBean = new ArrayList<>();
            solicitudLogisticoFiltroBean = new SolicitudLogisticoBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getSolicitudLogisticoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaConcepto() {
        try {
            for (int i = 0; i < listaConceptoUniNegBean.size(); i++) {
                System.out.println("1: " + solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto());
                System.out.println("2: " + listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto());
                if (solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
                        && listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto().toString().equals(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto().toString())) {
                    solicitudLogDetalleBean.setConceptoUniNegBean(listaConceptoUniNegBean.get(i));
                    valor = 2;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroGeneracion() {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            if (solicitudLogisticoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudLogisticoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudLogisticoFiltroBean.setFechaInicio(t);
            }
            if (solicitudLogisticoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudLogisticoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudLogisticoFiltroBean.setFechaFin(u);
            }

            solicitudLogisticoFiltroBean.setIdPaso("Generacion");//este id identifica que tipo de filtro va realizar 
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroAprobacion() {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            if (solicitudLogisticoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudLogisticoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudLogisticoFiltroBean.setFechaInicio(t);
            }
            if (solicitudLogisticoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudLogisticoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudLogisticoFiltroBean.setFechaFin(u);
            }

            solicitudLogisticoFiltroBean.setIdPaso("Aprobacion");//este id identifica que tipo de filtro va realizar  
            listaSolicitudLogisticoAprobBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltroDespacho() {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            if (solicitudLogisticoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudLogisticoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudLogisticoFiltroBean.setFechaInicio(t);
            }
            if (solicitudLogisticoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudLogisticoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudLogisticoFiltroBean.setFechaFin(u);
            }

            solicitudLogisticoFiltroBean.setIdPaso("Despacho");//este id identifica que tipo de filtro va realizar  
            listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ObtenerDivision() {
        try {

            double crI = 0;
            double crP = 0;
            double crS = 0;
            double crB = 0;
            double division = 0;

            if (solicitudLogisticoBean.getCentroRespInicialBean().getCr() != null) {
                crI = 1;
            }
            if (solicitudLogisticoBean.getCentroRespPrimariaBean().getCr() != null) {
                crP = 1;
            }
            if (solicitudLogisticoBean.getCentroRespSecundariaBean().getCr() != null) {
                crS = 1;
            }
            if (solicitudLogisticoBean.getCentroRespBachillerBean().getCr() != null) {
                crB = 1;
            }
            division = (crI + crP + crS + crB);

            solicitudLogisticoBean.setMontoI(0.0);
            solicitudLogisticoBean.setMontoP(0.0);
            solicitudLogisticoBean.setMontoS(0.0);
            solicitudLogisticoBean.setMontoB(0.0);

            cantidadDivision = sumaImporte / division;

            if (solicitudLogisticoBean.getCentroRespInicialBean().getCr() != null) {
                solicitudLogisticoBean.setMontoI(cantidadDivision);
            }
            if (solicitudLogisticoBean.getCentroRespPrimariaBean().getCr() != null) {
                solicitudLogisticoBean.setMontoP(cantidadDivision);
            }
            if (solicitudLogisticoBean.getCentroRespSecundariaBean().getCr() != null) {
                solicitudLogisticoBean.setMontoS(cantidadDivision);
            }
            if (solicitudLogisticoBean.getCentroRespBachillerBean().getCr() != null) {
                solicitudLogisticoBean.setMontoB(cantidadDivision);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ObtenerPonderacion() {
        try {

            Double cantidadI = new Double("0.0");
            Double cantidadP = new Double("0.0");
            Double cantidadS = new Double("0.0");
            Double cantidadB = new Double("0.0");

            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            Calendar miCalendario = Calendar.getInstance();
            getViewMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR));
            viewMatriculaBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaViewMatriculaBean = matriculaService.verEstadisticasMatriculaPorAnio(viewMatriculaBean);
            obtenerTotales();
            int inicial = viewMatriculaBean.getTotalIni();
            int primaria = viewMatriculaBean.getTotalPri();
            int secundaria = viewMatriculaBean.getTotalSec();
            int bachiller = viewMatriculaBean.getTotalSecB();

            //suma de cantidad  alumnos totales
//                cantidadPonderacion = totalI+totalP+totalS+totalB;
            cantidadPonderacion = inicial + primaria + secundaria + bachiller;

//            // ponderado de cada uno
            cantidadI = (inicial / Double.valueOf(cantidadPonderacion.toString()));
            cantidadP = primaria / Double.valueOf(cantidadPonderacion.toString());
            cantidadS = secundaria / Double.valueOf(cantidadPonderacion.toString());
            cantidadB = bachiller / Double.valueOf(cantidadPonderacion.toString());

            //ponderado por montoImporte
            ponderacionI = sumaImporte * cantidadI;
            ponderacionP = sumaImporte * cantidadP;
            ponderacionS = sumaImporte * cantidadS;
            ponderacionB = sumaImporte * cantidadB;

            ponderacionI = (double) Math.round(ponderacionI * 100) / 100;
            ponderacionP = (double) Math.round(ponderacionP * 100) / 100;
            ponderacionS = (double) Math.round(ponderacionS * 100) / 100;
            ponderacionB = (double) Math.round(ponderacionB * 100) / 100;

            solicitudLogisticoBean.setCentroRespInicialBean(null);
            solicitudLogisticoBean.setCentroRespPrimariaBean(null);
            solicitudLogisticoBean.setCentroRespSecundariaBean(null);
            solicitudLogisticoBean.setCentroRespBachillerBean(null);

            if (solicitudLogisticoBean.getCentroRespInicialBean().getCr() == null) {
                solicitudLogisticoBean.setMontoI(ponderacionI);
            }
            if (solicitudLogisticoBean.getCentroRespPrimariaBean().getCr() == null) {
                solicitudLogisticoBean.setMontoP(ponderacionP);
            }
            if (solicitudLogisticoBean.getCentroRespSecundariaBean().getCr() == null) {
                solicitudLogisticoBean.setMontoS(ponderacionS);
            }
            if (solicitudLogisticoBean.getCentroRespBachillerBean().getCr() == null) {
                solicitudLogisticoBean.setMontoB(ponderacionB);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ObtenerPersonalizada() {
        try {

            solicitudLogisticoBean.setCentroRespInicialBean(null);
            solicitudLogisticoBean.setCentroRespPrimariaBean(null);
            solicitudLogisticoBean.setCentroRespSecundariaBean(null);
            solicitudLogisticoBean.setCentroRespBachillerBean(null);
            solicitudLogisticoBean.setMontoI(null);
            solicitudLogisticoBean.setMontoP(null);
            solicitudLogisticoBean.setMontoS(null);
            solicitudLogisticoBean.setMontoB(null);

            solicitudLogisticoBean.getMontoI();
            solicitudLogisticoBean.getMontoP();
            solicitudLogisticoBean.getMontoS();
            solicitudLogisticoBean.getMontoB();

//            cantidadPerzonalizada = solicitudLogisticoBean.getMontoI() + solicitudLogisticoBean.getMontoP() + solicitudLogisticoBean.getMontoS() + solicitudLogisticoBean.getMontoB();
            if (solicitudLogisticoBean.getCentroRespInicialBean().getCr() != null) {
                solicitudLogisticoBean.setMontoI(solicitudLogisticoBean.getMontoI());
            }
            if (solicitudLogisticoBean.getCentroRespPrimariaBean().getCr() != null) {
                solicitudLogisticoBean.setMontoP(solicitudLogisticoBean.getMontoP());
            }
            if (solicitudLogisticoBean.getCentroRespSecundariaBean().getCr() != null) {
                solicitudLogisticoBean.setMontoS(solicitudLogisticoBean.getMontoS());
            }
            if (solicitudLogisticoBean.getCentroRespBachillerBean().getCr() != null) {
                solicitudLogisticoBean.setMontoB(solicitudLogisticoBean.getMontoB());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTotales() {
        Integer totalIni = 0;
        Integer totalPri = 0;
        Integer totalSec = 0;
        Integer totalSecB = 0;
        try {
            viewMatriculaBean.setTotalIni(0);
            viewMatriculaBean.setTotalPri(0);
            viewMatriculaBean.setTotalSec(0);
            for (ViewMatriculaBean viewMatricula : getListaViewMatriculaBean()) {
                switch (viewMatricula.getNivel()) {
                    case MaristaConstantes.NIV_ACA_INI:
                        totalIni = totalIni + viewMatricula.getTotal();
                        viewMatriculaBean.setTotalIni(totalIni);
                        break;
                    case MaristaConstantes.NIV_ACA_PRI:
                        totalPri = totalPri + viewMatricula.getTotal();
                        viewMatriculaBean.setTotalPri(totalPri);
                        break;
                    case MaristaConstantes.NIV_ACA_SEC:
                        if (viewMatricula.getGrado().equals(MaristaConstantes.NIV_ACA_SEC_BAC_5)
                                || viewMatricula.getGrado().equals(MaristaConstantes.NIV_ACA_SEC_BAC_4)) {
                            totalSecB = totalSecB + viewMatricula.getTotal();
                            viewMatriculaBean.setTotalSecB(totalSecB);
                        } else {

                            totalSec = totalSec + viewMatricula.getTotal();
                            viewMatriculaBean.setTotalSec(totalSec);
                        }
                        break;
                }
            }
//            viewMatriculaBean.setTotalIniPriSec(totalIni + totalPri + totalSec);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void mostarPanelDistribucion() {
        try {

            if (solicitudLogisticoBean.getTipoDistribucionBean().getIdCodigo() != null) {
                if (solicitudLogisticoBean.getTipoDistribucionBean().getIdCodigo() == 19501) {
                    this.flgDivision = true;
                    this.flgPondarizado = false;
                    this.flgPersonalizado = false;
                    ObtenerDivision();
                }
                if (solicitudLogisticoBean.getTipoDistribucionBean().getIdCodigo() == 19502) {
                    this.flgDivision = false;
                    this.flgPondarizado = true;
                    this.flgPersonalizado = false;
                    ObtenerPonderacion();
                }
                if (solicitudLogisticoBean.getTipoDistribucionBean().getIdCodigo() == 19503) {
                    this.flgDivision = false;
                    this.flgPondarizado = false;
                    this.flgPersonalizado = true;
                    ObtenerPersonalizada();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostar(String tipo) {
        try {
            if (tipo.equals("ini")) {
                solicitudLogisticoBean.getCentroRespInicialBean().setCr(solicitudLogisticoBean.getCentroRespInicialBean().getCr());
            }
            if (tipo.equals("pri")) {
                solicitudLogisticoBean.getCentroRespPrimariaBean().setCr(solicitudLogisticoBean.getCentroRespPrimariaBean().getCr());
            }
            if (tipo.equals("sec")) {
                solicitudLogisticoBean.getCentroRespSecundariaBean().setCr(solicitudLogisticoBean.getCentroRespSecundariaBean().getCr());
            }
            if (tipo.equals("bac")) {
                solicitudLogisticoBean.getCentroRespBachillerBean().setCr(solicitudLogisticoBean.getCentroRespBachillerBean().getCr());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(SolicitudLogisticoBean solBean) {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            ActividadService actividadService = BeanFactory.getActividadService();  
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

            solicitudLogisticoBean = new SolicitudLogisticoBean();
            sumaImporte = 0.0;

            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(solBean.getIdRequerimiento(),solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
            //obtengo la activida,objetivo y plan correspondiente
            actividadBean = actividadService.obtenerPorId(solicitudLogisticoBean.getActividadBean().getIdActividad());
            solicitudLogisticoBean.setActividadBean(actividadBean);
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(),solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
           
            
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
//               
               sumaImporte = bean.getSumaImporte();//paso el total del detalle a la variable global
                break;
            }
            
            mostarPanelDistribucion();
            //fechaSolicitudView = formato.format(solicitudLogisticoBean.getFechaSolicitud());
            this.cargarCatalogo();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void quitarItem(SolicitudLogDetalleBean bean) {
        try {
            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                sumaImporte = sumaImporte - (1 * bean.getCatalogoBean().getPrecioRef());
            } else {
                sumaImporte = sumaImporte - (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
            }

            listaSolicitudLogDetalleBean.remove(bean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarCatalogo() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CodigoService codigoService = BeanFactory.getCodigoService();

            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));

            //listaCatalogoBean = catalogoService.obtenerPorFiltro(catalogoBean);
            listaCatalogoBean = new ArrayList<>();
            //   catalogoFamiliaBean = catalogoService.obtenerCatalogoFamiliaPorNombre(tipoSolicitudBean.getCodigo()); 

            catalogoBean = catalogoService.obtenerCatalogoPorNombre(tipoCategoriaBean.getCodigo());
            switch (tipoCategoriaBean.getCodigo()) {
                case "Material":
                    listaCatalogoBean = catalogoService.obtenerMateriales(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    break;
                case "Activo Fijo":
                    listaCatalogoBean = catalogoService.obtenerActivos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    break;
                case "Servicio":
                    listaCatalogoBean = catalogoService.obtenerServicios();
                    break;
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Boolean validarItemPorAgregar(CatalogoBean bean) {
        Boolean valor = false;
        try {
            for (SolicitudLogDetalleBean detalle : listaSolicitudLogDetalleBean) {
                if (Objects.equals(detalle.getCatalogoBean().getIdCatalogo(), bean.getIdCatalogo())) {
                    valor = true;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void calcularImportePorItem(SolicitudLogDetalleBean bean) {
        try {

            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean tipoCategoriaBean = new CodigoBean();

            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
            solicitudLogisticoBean.setTipoCategoriaBean(tipoCategoriaBean);

            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")
                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo") //                    || solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")
                    ) {
                sumaImporte = sumaImporte - (bean.getCantidadSolicitadaAnterior() * bean.getCatalogoBean().getPrecioRef());
                sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
                bean.setCantidadSolicitadaAnterior(bean.getCantidadSolicitada());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String asignarEstado(SolicitudLogDetalleBean bean) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                //Los estados que se le puede asignar a un item es:Asignado Total,Asignado Parcial, Nada
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean tipoEstadoBean = new CodigoBean();

                if (bean.getCantidadEntregada() >= bean.getCantidadSolicitada())// quiere decir que es Asignado Total
                {
                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                    bean.setTipoEstadoBean(tipoEstadoBean);
                } else// quiere decir que es Asignado Parcial
                {
                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                    bean.setTipoEstadoBean(tipoEstadoBean);
                    if ((bean.getCantidadSolicitada() + bean.getMaterialBean().getStockMin()) <= bean.getMaterialBean().getStockActual()) {
                        //quiere decir que tiene stock, aun asi le entrega menos
                        RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
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

    public void agregarItems(CatalogoBean bean) {
        try {
            CodigoBean tipoCategoriaBean = new CodigoBean();
            SolicitudLogDetalleBean solicitudLogDetalleBean = new SolicitudLogDetalleBean();
            CodigoService codigoService = BeanFactory.getCodigoService();
            //Si es false, quiere decir que no ha sido agregado ese item seleccionado
            if (!validarItemPorAgregar(bean)) {
                tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
                solicitudLogisticoBean.setTipoCategoriaBean(tipoCategoriaBean);

                if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                    sumaImporte = sumaImporte + bean.getPrecioRef();
                }

                solicitudLogDetalleBean.setCatalogoBean(bean);
                listaSolicitudLogDetalleBean.add(solicitudLogDetalleBean);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void asignarEstadoPorItems() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();

            //Asignar el estado de la cabecera segun los estados de su detalle
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                if (bean.getTipoEstadoBean().getCodigo() != null && !bean.getTipoEstadoBean().getCodigo().equals("")) {
                    if (bean.getTipoEstadoBean().getCodigo().equals("Nada") || bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")
                            || bean.getTipoEstadoBean().getCodigo().equals("Asignado Total")) {
                        CodigoBean tipoEstado = new CodigoBean();
                        tipoEstado = codigoService.obtenerPorCodigo(new CodigoBean(0, "Proceso", new TipoCodigoBean("TipoStatusReq")));
                        solicitudLogisticoBean.getTipoEstadoBean().setIdCodigo(tipoEstado.getIdCodigo());
                    }
                    if (bean.getTipoEstadoBean().getCodigo().equals("Entrega Parcial")) {
                        CodigoBean tipoEstado = new CodigoBean();
                        tipoEstado = codigoService.obtenerPorCodigo(new CodigoBean(0, "Parcial", new TipoCodigoBean("TipoStatusReq")));
                        solicitudLogisticoBean.getTipoEstadoBean().setIdCodigo(tipoEstado.getIdCodigo());
                    }
                    if (bean.getTipoEstadoBean().getCodigo().equals("Entrega Total")) {
                        CodigoBean tipoEstado = new CodigoBean();
                        tipoEstado = codigoService.obtenerPorCodigo(new CodigoBean(0, "Atendido", new TipoCodigoBean("TipoStatusReq")));
                        solicitudLogisticoBean.getTipoEstadoBean().setIdCodigo(tipoEstado.getIdCodigo());
                    }
                }
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
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();

//                if (solicitudLogisticoBean.getIdRequerimiento() == null) {
//                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    solicitudLogisticoService.insertar(solicitudLogisticoBean);
//                    solicitudLogisticoDetalleService.insertar(solicitudLogisticoBean.getIdRequerimiento(), listaSolicitudLogDetalleBean,solicitudLogDetalleBean);
//                } else {
//                    this.asignarEstadoPorItems();
//                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
////                    solicitudLogDetalleBean.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
////                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaDBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
////                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaHBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
//
//                    solicitudLogisticoService.modificar(solicitudLogisticoBean);
//                    solicitudLogisticoDetalleService.eliminar(solicitudLogisticoBean.getIdRequerimiento());
//                    solicitudLogisticoDetalleService.insertar(solicitudLogisticoBean.getIdRequerimiento(), listaSolicitudLogDetalleBean,solicitudLogDetalleBean);
//                }
//                listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);
//                listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerTodos();//lista cuando se atiende la solicitud
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                this.limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String agregarDetalleShow() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                grabar();
                cargarCatalogo();
//                limpiar();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarAprobacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            CodigoBean tipoEstadoBean = new CodigoBean();
            if (pagina == null) {
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                CodigoService codigoService = BeanFactory.getCodigoService();
                solicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, solicitudLogisticoBean.getTipoEstadoBean().getCodigo(), new TipoCodigoBean("TipoStatusReq")));
                //catalogoBean = catalogoService.obtenerPorCatalogo(catalogoBean);
                solicitudLogisticoBean.setTipoEstadoBean(tipoEstadoBean);
//                solicitudLogisticoService.modificar(solicitudLogisticoBean);

                listaSolicitudLogisticoAprobBean = solicitudLogisticoService.obtenerTodosAprob(solicitudLogisticoBean);
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

    public void obtenerObjetivoPorPlanOpe() {
        try {
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            listaObjOperativoBean = objetivoOperativoService.obtenerPorPlanOpe(this.idObjOperativo);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    // ++++++++++++++++++++++++ METODOS PARA EL MANTO DE CATALOGO, CATEGORIA Y FAMILIA +++++++++++++++++++
    //--------------------------------------- Metodos de CatalogoFamilia ---------------------------
    public void limpiarCF() {
        catalogoFamiliaBean = new CatalogoFamiliaBean();
    }

    public void obtenerTodosCF() {
        try {
            CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();
            //    listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCF(Integer id) {
        try {
            CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();
            //     catalogoFamiliaBean = catalogoFamiliaService.obtenerPorId(id); 
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCF(SelectEvent event) {
        try {
            catalogoFamiliaBean = (CatalogoFamiliaBean) event.getObject();
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
                CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();

                if (catalogoFamiliaBean.getIdCatalogoFamilia() == null) //        { catalogoFamiliaService.insertar(catalogoFamiliaBean); } 
                //        else 
                //        { catalogoFamiliaService.modificar(catalogoFamiliaBean); }
                //        listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();
                {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
                this.limpiarCF();
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
                CatalogoFamiliaService catalogoFamiliaService = BeanFactory.getCatalogoFamiliaService();

                //         catalogoFamiliaService.eliminar(catalogoFamiliaBean);
                //       listaCatalogoFamiliaBean = catalogoFamiliaService.obtenerTodos();
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
    public void limpiarCC() {
        catalogoCategoriaBean = new CatalogoCategoriaBean();
    }

    public void obtenerTodosCC() {
        try {
            CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdCC(Integer id) {
        try {
            CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            catalogoCategoriaBean = catalogoCategoriaService.obtenerPorId(id);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectCC(SelectEvent event) {
        try {
            catalogoCategoriaBean = (CatalogoCategoriaBean) event.getObject();
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
                CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();

                if (catalogoCategoriaBean.getIdCatalogoCategoria() == null) {
                    catalogoCategoriaService.insertar(catalogoCategoriaBean);
                } else {
                    catalogoCategoriaService.modificar(catalogoCategoriaBean);
                }

                listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarCC();
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
                CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();

                catalogoCategoriaService.eliminar(catalogoCategoriaBean);
                listaCatalogoCategoriaBean = catalogoCategoriaService.obtenerTodos();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                this.limpiarCC();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
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
                    //             catalogoService.actualizar(catalogoBean);
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
            CatalogoCategoriaService catalogoCategoriaService = BeanFactory.getCatalogoCategoriaService();
            listaCatalogoCategoriaFiltroBean = catalogoCategoriaService.obtenerPorFamilia(this.idCatalogoFamilia);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public SimpleDateFormat getFormato() {
        return formato;
    }

    public void setFormato(SimpleDateFormat formato) {
        this.formato = formato;
    }

    public Integer getIdObjOperativo() {
        return idObjOperativo;
    }

    public void setIdObjOperativo(Integer idObjOperativo) {
        this.idObjOperativo = idObjOperativo;
    }

    public TipoSolicitudBean getTipoSolicitudBean() {
        return tipoSolicitudBean;
    }

    public void setTipoSolicitudBean(TipoSolicitudBean tipoSolicitudBean) {
        this.tipoSolicitudBean = tipoSolicitudBean;
    }

    public List<TipoSolicitudBean> getListaTipoSolicitudBean() {
        return listaTipoSolicitudBean;
    }

    public void setListaTipoSolicitudBean(List<TipoSolicitudBean> listaTipoSolicitudBean) {
        this.listaTipoSolicitudBean = listaTipoSolicitudBean;
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

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
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

    public PlanContableBean getPlanContableBean() {
        if (planContableBean == null) {
            planContableBean = new PlanContableBean();
        }
        return planContableBean;
    }

    public void setPlanContableBean(PlanContableBean planContableBean) {
        this.planContableBean = planContableBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadInicialBean() {
        if (listaCentroResponsabilidadInicialBean == null) {
            listaCentroResponsabilidadInicialBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadInicialBean;
    }

    public void setListaCentroResponsabilidadInicialBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadInicialBean) {
        this.listaCentroResponsabilidadInicialBean = listaCentroResponsabilidadInicialBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadPrimariaBean() {
        if (listaCentroResponsabilidadPrimariaBean == null) {
            listaCentroResponsabilidadPrimariaBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadPrimariaBean;
    }

    public void setListaCentroResponsabilidadPrimariaBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadPrimariaBean) {
        this.listaCentroResponsabilidadPrimariaBean = listaCentroResponsabilidadPrimariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadSecundariaBean() {
        if (listaCentroResponsabilidadSecundariaBean == null) {
            listaCentroResponsabilidadSecundariaBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadSecundariaBean;
    }

    public void setListaCentroResponsabilidadSecundariaBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadSecundariaBean) {
        this.listaCentroResponsabilidadSecundariaBean = listaCentroResponsabilidadSecundariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBachillerBean() {
        if (listaCentroResponsabilidadBachillerBean == null) {
            listaCentroResponsabilidadBachillerBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBachillerBean;
    }

    public void setListaCentroResponsabilidadBachillerBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBachillerBean) {
        this.listaCentroResponsabilidadBachillerBean = listaCentroResponsabilidadBachillerBean;
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

    public CodigoBean getTipoDistribucionBean() {
        if (tipoDistribucionBean == null) {
            tipoDistribucionBean = new CodigoBean();
        }
        return tipoDistribucionBean;
    }

    public void setTipoDistribucionBean(CodigoBean tipoDistribucionBean) {
        this.tipoDistribucionBean = tipoDistribucionBean;
    }

    public Boolean getFlgDivision() {
        return flgDivision;
    }

    public void setFlgDivision(Boolean flgDivision) {
        this.flgDivision = flgDivision;
    }

    public Boolean getFlgPersonalizado() {
        return flgPersonalizado;
    }

    public void setFlgPersonalizado(Boolean flgPersonalizado) {
        this.flgPersonalizado = flgPersonalizado;
    }

    public void setFlgPondarizado(Boolean flgPondarizado) {
        this.flgPondarizado = flgPondarizado;
    }

    public Boolean getFlgPondarizado() {
        return flgPondarizado;
    }

    public List<ViewMatriculaBean> getListaViewMatriculaBean() {
        if (listaViewMatriculaBean == null) {
            listaViewMatriculaBean = new ArrayList<>();
        }
        return listaViewMatriculaBean;
    }

    public void setListaViewMatriculaBean(List<ViewMatriculaBean> listaViewMatriculaBean) {
        this.listaViewMatriculaBean = listaViewMatriculaBean;
    }

    public ViewMatriculaBean getViewMatriculaBean() {
        if (viewMatriculaBean == null) {
            viewMatriculaBean = new ViewMatriculaBean();
        }
        return viewMatriculaBean;
    }

    public void setViewMatriculaBean(ViewMatriculaBean viewMatriculaBean) {
        this.viewMatriculaBean = viewMatriculaBean;
    }

    public Double getCantidadDivision() {
        return cantidadDivision;
    }

    public void setCantidadDivision(Double cantidadDivision) {
        this.cantidadDivision = cantidadDivision;
    }

    public Double getCantidadPerzonalizada() {
        return cantidadPerzonalizada;
    }

    public void setCantidadPerzonalizada(Double cantidadPerzonalizada) {
        this.cantidadPerzonalizada = cantidadPerzonalizada;
    }

    public Integer getCantidadPonderacion() {
        return cantidadPonderacion;
    }

    public void setCantidadPonderacion(Integer cantidadPonderacion) {
        this.cantidadPonderacion = cantidadPonderacion;
    }

    public double getPonderacionI() {
        return ponderacionI;
    }

    public void setPonderacionI(double ponderacionI) {
        this.ponderacionI = ponderacionI;
    }

    public double getPonderacionP() {
        return ponderacionP;
    }

    public void setPonderacionP(double ponderacionP) {
        this.ponderacionP = ponderacionP;
    }

    public double getPonderacionS() {
        return ponderacionS;
    }

    public void setPonderacionS(double ponderacionS) {
        this.ponderacionS = ponderacionS;
    }

    public double getPonderacionB() {
        return ponderacionB;
    }

    public void setPonderacionB(double ponderacionB) {
        this.ponderacionB = ponderacionB;
    }

}
