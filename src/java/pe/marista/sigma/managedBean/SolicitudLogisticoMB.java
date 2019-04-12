package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;
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
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.DetRequerimientoCRBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.InventarioActivoBean;
import pe.marista.sigma.bean.InventarioAlmacenBean;
import pe.marista.sigma.bean.MovimientoAlmacenBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.DetDespachoRepBean;
import pe.marista.sigma.bean.reporte.SolicitudLogisticoRepBean;
import pe.marista.sigma.service.BloqueoService;
import pe.marista.sigma.service.CentroCostoService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.InventarioActivoService;
import pe.marista.sigma.service.InventarioAlmacenService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.util.GLTCalculadoraCR;

public class SolicitudLogisticoMB extends BaseMB implements Serializable {

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
    private Double sumaImporte = 0.00D;
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
    private PersonalBean personalBean;
    //ayuda
    private Boolean flgDivision = false;
    private Boolean flgPondarizado = false;
    private Boolean flgPersonalizado = false;
    //ayuda
    private Boolean flgActivo = false;
    private Boolean flgAlmacen = false;
    private Boolean flgServicio = false;
    private Boolean flgTransporte = false;

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
//    private List<CodigoBean> listaTipoDistribucionCRBean;
    private CodigoBean tipoDistribucionBean;
    private Double cantidadDivision = 0.00;
    private Integer cantidadPonderacion = 0;

    private Integer idTipoPrioridad;
    private Integer idTipoSolicitudAutoriza;
    private Double cantidadPerzonalizada = 0.00;

    private double ponderacionI = 0;
    private double ponderacionP = 0;
    private double ponderacionS = 0;
    private double ponderacionB = 0;
    private Boolean flgEstado = false;
    //lista matricula
    private List<ViewMatriculaBean> listaViewMatriculaBean;
    private ViewMatriculaBean viewMatriculaBean;

    //CR Multi
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;
    private List<CodigoBean> listaTipoDistribucionCRBean;

    private List<DetRequerimientoCRBean> listaDetRequerimientoCRTopBean;

    private DetRequerimientoCRBean detRequerimientoCRBean;
    private List<PresupuestoBean> listaPresupuestoBean;

    private List<DetActividadBean> listaDetActividadBean;
//    private CodigoB

    //ayuda
    private Boolean flgFiltroPersonal = false;
    private Boolean flgFiltroPersona = false;
    private Boolean flgFiltroProve = false;
    private Boolean flgSoli = true;
    private Boolean flgIgualSoli = false;
    private String idTipoSol;
    private String idTipoRespCheque;
    private Integer idSolicitud;
    private Boolean flgGestorSoli = false;
    private Boolean flgCategoria = false;

    //Ayuda
    private String idRequerimiento = null;
    private Boolean flgCantidad = false;

    //TIPOS DE CATEGORIA
    private Integer idTipoCategoria;
    private Integer tipoMaterial = MaristaConstantes.Id_Categoria_Almacen;
    private Integer tipoActivo = MaristaConstantes.Id_Categoria_Activo;
    private Integer tipoServicio = MaristaConstantes.Id_Categoria_Servicio;

    private Boolean flgUniNeg;

    private List<MovimientoAlmacenBean> listaMovimientoAlmacenBean;
    private Integer flgTipoCatalogo;

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
    public void SolicitudLogisticoMB() {
        try {
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaSolicitudLogisticoBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = new ArrayList<>();
            listaSolicitudLogisticoAprobBean = new ArrayList<>();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            getListaConceptoUniNegBean();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorEgr(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();
            //---------- solicitud -------------
            CodigoService codigoService = BeanFactory.getCodigoService();
            PersonalService personalService = BeanFactory.getPersonalService();
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            EntidadService entidadService = BeanFactory.getEntidadService();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoEstadoBean = codigoService.obtenerPorTipoSol(new TipoCodigoBean("TipoStatusReq"));
            listaTipoEstadoDespachoBean = codigoService.obtenerPorTipoDes(new TipoCodigoBean("TipoStatusReq"));
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
            listaTipoEstadoDetalleBean = codigoService.obtenerPorTipoDespacho(new TipoCodigoBean("TipoStatusDetReq"));
            listaTipoSolicitudBean = tipoSolicitudService.obtenerPorAmbitoPorUniNeg((new TipoSolicitudBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean(), MaristaConstantes.COD_REQ_LOG)));
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaPersonalBean = personalService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaUsuarioBean = usuarioService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaCentroResponsabilidadInicialBean = centroResponsabilidadService.obtenerCentroInicial();
//            listaCentroResponsabilidadPrimariaBean = centroResponsabilidadService.obtenerCentroPrimaria();
//            listaCentroResponsabilidadSecundariaBean = centroResponsabilidadService.obtenerCentroSecundaria();
//            listaCentroResponsabilidadBachillerBean = centroResponsabilidadService.obtenerCentroBachiller();
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));

            for (CodigoBean bean : listaTipoPrioridadBean) {
                if (bean.getCodigo().equals("Baja")) {
                    getSolicitudLogisticoBean().getTipoPrioridadBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoPrioridad = bean.getIdCodigo();
                }
            }
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                for (TipoSolicitudBean bean : listaTipoSolicitudBean) {
                    if (bean.getNombre().equals("Requerimiento sin Autorizacion")) {
                        getSolicitudLogisticoBean().getTipoSolicitudBean().setIdTipoSolicitud(bean.getIdTipoSolicitud());
                        this.idTipoSolicitudAutoriza = bean.getIdTipoSolicitud();
                    }
                }
            } else {
                for (TipoSolicitudBean bean : listaTipoSolicitudBean) {
                    if (bean.getNombre().equals("Requerimiento con Autorizacion")) {
                        getSolicitudLogisticoBean().getTipoSolicitudBean().setIdTipoSolicitud(bean.getIdTipoSolicitud());
                        this.idTipoSolicitudAutoriza = bean.getIdTipoSolicitud();
                    }
                }
            }
            getListaCentroResponsabilidadBean();
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, getListaCentroResponsabilidadBeanB());

//  UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            //  listaUnidadOrganicaBean = unidadOrganicaService.obtenerPorUnidadNegocio(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ActividadService actividadService = BeanFactory.getActividadService();
            listaActividadBean = actividadService.obtenerPorUnidadNegocioUniOrg(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaObjOperativoBean = objetivoOperativoService.obtenerTodos();
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            listaPlanOperativoBean = planOperativoService.obtenerPorUnidadNegocioUniOrg(usuarioSessionBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getSolicitudLogisticoBean().setSolicitante(usuarioSessionBean.getPersonalBean().getNombreCompleto());
            getSolicitudLogisticoBean().setPersonalBean(usuarioSessionBean.getPersonalBean());
//            String idRequerimiento = null;
            idRequerimiento = solicitudLogisticoService.obtenerUltimoSoli(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idRequerimiento.equals(null)) {
                getSolicitudLogisticoBean().setNroSolicitud(idRequerimiento);
            }

            //paso el estado Pendiente por defecto de lista cargada - Sugerido por Cole-Champs
            for (CodigoBean bean : listaTipoEstadoBean) {
                if (bean.getCodigo().equals("Pendiente")) {
                    getSolicitudLogisticoBean().getTipoEstadoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoEstado = bean.getIdCodigo();
                }
            }

            System.out.println("categoria: " + solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo());
            this.cargarDatosSession();

            //metodos de cargado para el manto catalogo
            //this.obtenerTodosCF();
            //this.obtenerTodosCC();
            //this.obtenerTodosCat();
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)
                    || usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)
                    || usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                setFlgUniNeg(true);
            } else {
                setFlgUniNeg(false);
            }
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

//    public void obtenerUsuarioPorId(UsuarioBean bean) {
//        try {
//            UsuarioService usuarioService = BeanFactory.getUsuarioService();
//            usuarioBean = usuarioService.buscarPorId(bean.getUsuario());
//            //Paso los valores del bean del popup al bean de guardar
//            solicitudLogisticoBean.setPersonalBean(usuarioBean.getPersonalBean());
//            solicitudLogisticoBean.setUnidadOrganicaBean(usuarioBean.getPersonalBean().getUnidadOrganicaBean());
//
//            ActividadService actividadService = BeanFactory.getActividadService();
//            listaActividadBean = actividadService.obtenerPorUnidadNegocioUniOrg(usuarioBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerPersonalPorId(PersonalBean bean) {
        try {
            System.out.println("entró obtenerPersonalPorId()");
            PersonalService personalService = BeanFactory.getPersonalService();
            personalBean = personalService.buscarPorIdPersonalLogistico(bean.getIdPersonal(), bean.getUnidadOrganicaBean().getNombreUniOrg());
            //Paso los valores del bean del popup al bean de guardar
            solicitudLogisticoBean.setPersonalBean(personalBean);
            solicitudLogisticoBean.setUnidadOrganicaBean(personalBean.getUnidadOrganicaBean());

            ActividadService actividadService = BeanFactory.getActividadService();
            listaActividadBean = actividadService.obtenerPorUnidadNegocioUniOrg(personalBean.getUnidadOrganicaBean().getIdUniOrg(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            solicitudLogisticoBean = new SolicitudLogisticoBean();
            solicitudLogDetalleBean = new SolicitudLogDetalleBean();
            listaTipoSolicitudBean = new ArrayList<>();
            listaConceptoUniNegBean = new ArrayList<>();
            listaTipoCategoriaBean = new ArrayList<>();
            listaTipoEstadoBean = new ArrayList<>();
            listaTipoPrioridadBean = new ArrayList<>();
            listaTipoConceptoBean = new ArrayList<>();
            tipoConceptoBean = new TipoConceptoBean();
            listaTipoCategoriaBean = new ArrayList<>();
            fechaSolicitudView = "";
            sumaImporte = 0.00D;
            solicitudLogisticoBean.setTitulo(new SolicitudLogisticoBean().getTitulo());
            this.cargarDatosSession();
            getListaCentroResponsabilidadBean();

            //---Cambio johanC
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            //---Cambio johanC

            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, listaCentroResponsabilidadBeanB);
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            listaTipoCategoriaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoCategoria"));
            listaTipoEstadoBean = codigoService.obtenerPorTipoSol(new TipoCodigoBean("TipoStatusReq"));
            listaTipoEstadoDespachoBean = codigoService.obtenerPorTipoDes(new TipoCodigoBean("TipoStatusReq"));
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoMoneda"));
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoPrioridad"));
            listaTipoEstadoDetalleBean = codigoService.obtenerPorTipoDespacho(new TipoCodigoBean("TipoStatusDetReq"));
            listaTipoSolicitudBean = tipoSolicitudService.obtenerPorAmbitoPorUniNeg((new TipoSolicitudBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean(), MaristaConstantes.COD_REQ_LOG)));

            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
//            getListaConceptoUniNegBean();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorEgr(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            getTipoConceptoBean();
            //ListaTipoConcepto
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConceptoSalida();
            for (CodigoBean bean : listaTipoPrioridadBean) {
                if (bean.getCodigo().equals("Baja")) {
                    getSolicitudLogisticoBean().getTipoPrioridadBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoPrioridad = bean.getIdCodigo();
                }
            }
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                for (TipoSolicitudBean bean : listaTipoSolicitudBean) {
                    if (bean.getNombre().equals("Requerimiento sin Autorizacion")) {
                        getSolicitudLogisticoBean().getTipoSolicitudBean().setIdTipoSolicitud(bean.getIdTipoSolicitud());
                        this.idTipoSolicitudAutoriza = bean.getIdTipoSolicitud();
                    }
                }
            } else {
                for (TipoSolicitudBean bean : listaTipoSolicitudBean) {
                    if (bean.getNombre().equals("Requerimiento con Autorizacion")) {
                        getSolicitudLogisticoBean().getTipoSolicitudBean().setIdTipoSolicitud(bean.getIdTipoSolicitud());
                        this.idTipoSolicitudAutoriza = bean.getIdTipoSolicitud();
                    }
                }
            }
            //ListaTipoEstado
            for (CodigoBean bean : listaTipoEstadoBean) {
                if (bean.getCodigo().equals("Pendiente")) {
                    getSolicitudLogisticoBean().getTipoEstadoBean().setIdCodigo(bean.getIdCodigo());
                    this.idTipoEstado = bean.getIdCodigo();
                }
            }
            //Trayendo el nroRequerimiento
//            String idRequerimiento = null;
            idRequerimiento = solicitudLogisticoService.obtenerUltimoSoli(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!idRequerimiento.equals(null)) {
                getSolicitudLogisticoBean().setNroSolicitud(idRequerimiento);
            }
            listaSolicitudLogDetalleBean = new ArrayList<>();
            this.flgEstado = false;
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
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTipNombre(tipoConceptoBean.getIdTipoConcepto(),tipoConceptoBean.getNombre(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (tipoConceptoBean.getIdTipoConcepto() == MaristaConstantes.id_Transporte) {
//                tipoConceptoBean.setNombre(MaristaConstantes.transporte);
//
//                switch (tipoConceptoBean.getNombre()) {
//                    case "Transporte correos y gastos de viaje":
//                        this.flgTransporte = true;
//                        break;
//
//                }
//            }
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

    public void obtenerCuentaConcepto(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

//            DetActividadService detActividadService = BeanFactory.getDetActividadService();
//            for (int i = 0; i < listaConceptoUniNegBean.size(); i++) {
//                System.out.println("1: " + solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto());
//                System.out.println("2: " + listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto());
//                if (solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
//                        && listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto().toString().equals(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getIdConcepto().toString())) {
//                    solicitudLogDetalleBean.setConceptoUniNegBean(listaConceptoUniNegBean.get(i));
//                    valor = 2;
//                }
//            }
            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);

            solicitudLogDetalleBean.setConceptoUniNegBean(con);

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
//            if (tipoConceptoBean.getIdTipoConcepto() == MaristaConstantes.id_Transporte) {
//                tipoConceptoBean.setNombre(MaristaConstantes.transporte);
//
//                switch (tipoConceptoBean.getNombre()) {
//                    case "Transporte correos y gastos de viaje":
//                        this.flgTransporte = true;
//                        break;
//
//                }
//            }
            System.out.println(tipoConceptoBean.getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirTodosPdfSolicitud() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            solicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = solicitudLogisticoDetalleService.obtenerUltimo(solicitudLogisticoBean.getIdRequerimiento());
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportSolicitudLogistico.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");

            File file = new File(archivoJasper);
            List<SolicitudLogisticoRepBean> listaDetSolicitudLogBean = new ArrayList<>();
            listaDetSolicitudLogBean = solicitudLogisticoDetalleService.obtenerSolicitudPDF(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetSolicitudLogBean);
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

    public void imprimirTodosPdfSolicitudTransporte() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UnidadNegocioBean unidadNegocioBean = new UnidadNegocioBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            solicitudLogisticoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = solicitudLogisticoDetalleService.obtenerUltimo(solicitudLogisticoBean.getIdRequerimiento());
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportSolicitudTransporte.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");

            File file = new File(archivoJasper);
            List<SolicitudLogisticoRepBean> listaDetSolicitudLogBean = new ArrayList<>();
            listaDetSolicitudLogBean = solicitudLogisticoDetalleService.obtenerSolicitudTransportePDF(solicitudLogisticoBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetSolicitudLogBean);
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
            solicitudLogisticoFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void obtenerPorFiltroAprobacion() {
        try {
            int estado = 0;
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            if (solicitudLogisticoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudLogisticoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudLogisticoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (solicitudLogisticoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudLogisticoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudLogisticoFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (solicitudLogisticoFiltroBean.getPersonalBean().getNombreCompleto() != null && !solicitudLogisticoFiltroBean.getPersonalBean().getNombreCompleto().trim().equals("")) {
                solicitudLogisticoFiltroBean.getPersonalBean().setNombreCompleto(solicitudLogisticoFiltroBean.getPersonalBean().getNombreCompleto());
                estado = 1;
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
            getSolicitudLogisticoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerTodosMDespacho(solicitudLogisticoFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String actualizarCatalogo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CatalogoService catalogoService = BeanFactory.getCatalogoService();

                catalogoBean = catalogoService.obtenerCatalogoPorNombre(tipoCategoriaBean.getCodigo());
                switch (tipoCategoriaBean.getCodigo()) {
                    case "Material":
                        listaCatalogoBean = catalogoService.obtenerMateriales(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        this.flgActivo = false;
                        this.flgAlmacen = true;
                        this.flgServicio = false;
                        break;
                    case "Activo Fijo":
                        listaCatalogoBean = catalogoService.obtenerActivos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        this.flgActivo = true;
                        this.flgAlmacen = false;
                        this.flgServicio = false;
                        break;
                    case "Servicio":
                        listaCatalogoBean = catalogoService.obtenerServicios();
                        this.flgActivo = false;
                        this.flgAlmacen = false;
                        this.flgServicio = true;
                        break;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
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

            solicitudLogisticoBean.setMontoI(0.00);
            solicitudLogisticoBean.setMontoP(0.00);
            solicitudLogisticoBean.setMontoS(0.00);
            solicitudLogisticoBean.setMontoB(0.00);

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

            Double cantidadI = new Double("0.00");
            Double cantidadP = new Double("0.00");
            Double cantidadS = new Double("0.00");
            Double cantidadB = new Double("0.00");

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
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(idTipoEstado, uniNeg);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
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
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            solicitudLogisticoBean = new SolicitudLogisticoBean();
            DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
            sumaImporte = 0.00D;
            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(solBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //obtengo la activida,objetivo y plan correspondiente
//            actividadBean = actividadService.obtenerPorId(solicitudLogisticoBean.getActividadBean().getIdActividad());
            solicitudLogisticoBean.setActividadBean(actividadBean);

            if (solicitudLogisticoBean.getTipoEstadoBean().getCodigo().equals(MaristaConstantes.COD_AUTORI)) {
                listaTipoEstadoBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoStatusReq"));
            }
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
//               
                sumaImporte = bean.getSumaImporte();//paso el total del detalle a la variable global 
                break;
            }
            this.flgEstado = true;
//            mostarPanelDistribucion();
//            distribuir();
            //fechaSolicitudView = formato.format(solicitudLogisticoBean.getFechaSolicitud());
            cargarCatalogo();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdAprobacion(SolicitudLogisticoBean solBean) {
        try {
            Integer cuenta = null;
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            ActividadService actividadService = BeanFactory.getActividadService();
//            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
//            CodigoService codigoService = BeanFactory.getCodigoService();
            solicitudLogisticoBean = new SolicitudLogisticoBean();
            solicitudLogDetalleBean = new SolicitudLogDetalleBean();
//            DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
            sumaImporte = 0.00D;

            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(solBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("flg..." + solicitudLogisticoBean.getFlgAutorizar());
            //obtengo la activida,objetivo y plan correspondiente
            actividadBean = actividadService.obtenerPorId(solicitudLogisticoBean.getActividadBean().getIdActividad());
            solicitudLogisticoBean.setActividadBean(actividadBean);
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = new ArrayList<>();
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
            CodigoBean mon = new CodigoBean();
            if (listaSolicitudLogDetalleBean.get(0).getTipoMonedaBean().getIdCodigo() != null) {
                System.out.println("moneda");
                mon = listaSolicitudLogDetalleBean.get(0).getTipoMonedaBean();
                getSolicitudLogisticoBean().setTipoMoneda(mon.getCodigo());
                getSolicitudLogisticoBean().setIdTipoMoneda(mon.getIdCodigo());
            } else {
                System.out.println("no hay moneda");

            }
//            solicitudLogisticoBean.setListaDetRequerimientoCRBean(solBean.getListaDetRequerimientoCRBean());
            listaDetRequerimientoCRTopBean = solicitudLogisticoService.ObtenerPorIdCR(solicitudLogisticoBean);
            if (!listaDetRequerimientoCRTopBean.isEmpty()) {
                for (DetRequerimientoCRBean detReq : getListaDetRequerimientoCRTopBean()) {
                    solicitudLogisticoBean.setCodDistribucion(detReq.getTipoDistribucion().getIdCodigo());
                }
            }

            //3.-Crear Lista Respuesta
            List<DetRequerimientoCRBean> listaDetRequerimiento = new ArrayList<>();
            listaDetRequerimiento = solicitudLogisticoService.ObtenerPorIdCRDis(solicitudLogisticoBean);

            solicitudLogisticoBean.setListaDetRequerimientoCRBean(listaDetRequerimiento);

//          solicitudLogisticoBean.setCodDistribucion(detRequerimientoCRBean.getTipoDistribucion().getIdCodigo());
//          listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
            //Obtener Cr-Requerimiento
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerInCrReque(solBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.obtenerOutCrReque(solBean.getIdRequerimiento(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

//            List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
//            solicitudLogisticoBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                tipoConceptoBean.setIdTipoConcepto(bean.getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
//                tipoConceptoBean.setNombre(bean.getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getNombre());
                obtenerConceptoPorTipo();
                solicitudLogDetalleBean.getConceptoUniNegBean().setConceptoBean(bean.getConceptoUniNegBean().getConceptoBean());
//                solicitudLogDetalleBean.getCatalogoBean().getInventarioAlmacenBean().setStockActual(bean.getCatalogoBean().getInventarioAlmacenBean().getStockActual());
                sumaImporte = bean.getSumaImporte();//paso el total del detalle a la variable global
                cuenta = bean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta();
                break;
            }
            if (cuenta != null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                listaPresupuestoBean = presupuestoService.obtenerPresupuestoCuenta(cuenta, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2016);
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
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
//            id = ordenCompraDetalleService.obtenerUltimo(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            id = solicitudLogisticoDetalleService.obtenerUltimo(solicitudLogisticoBean.getIdRequerimiento());
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorRequerimiento(id);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repDespacho.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetDespachoRepBean> listaDetSolicitudLogisticoBean = new ArrayList<>();
            for (int i = 0; i < listaSolicitudLogDetalleBean.size(); i++) {
                DetDespachoRepBean detDespachoRepBean = new DetDespachoRepBean();
                detDespachoRepBean.setIdRequerimiento(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getIdRequerimiento());
                detDespachoRepBean.setNombreCompleto(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getPersonalBean().getNombreCompleto());
                detDespachoRepBean.setNombreUniOrg(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadOrganicaBean().getNombreUniOrg());
                detDespachoRepBean.setTipoReq(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getTipoCategoriaBean().getCodigo());
                detDespachoRepBean.setIdCatalogo(listaSolicitudLogDetalleBean.get(i).getCatalogoBean().getIdCatalogo());
                detDespachoRepBean.setItem(listaSolicitudLogDetalleBean.get(i).getCatalogoBean().getItem());
                detDespachoRepBean.setCantidadSolicitada(listaSolicitudLogDetalleBean.get(i).getCantidadSolicitada());
                detDespachoRepBean.setCantidadEntregada(listaSolicitudLogDetalleBean.get(i).getCantidadEntregada());
                detDespachoRepBean.setDireccionUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getDireccion());
                detDespachoRepBean.setPaisUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getPaisBean().getNombre());
                detDespachoRepBean.setDistritoUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getDistritoBean().getNombre());
                detDespachoRepBean.setTelefonoUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getTelefono());
                detDespachoRepBean.setCorreoUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getCorreo());
                detDespachoRepBean.setWebUnidad(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getEntidadBean().getUrl());
                detDespachoRepBean.setNombreUniNeg(listaSolicitudLogDetalleBean.get(i).getSolicitudLogisticoBean().getUnidadNegocioBean().getNombreUniNeg());
                detDespachoRepBean.setEstadoEstadoNombre(listaSolicitudLogDetalleBean.get(i).getTipoEstadoBean().getCodigo());
//                }
                listaDetSolicitudLogisticoBean.add(detDespachoRepBean);

            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetSolicitudLogisticoBean);
//            Map<String, Object> parametros = new HashMap<>();
//            String ruta = absoluteWebPath + "reportes\\jasper\\";
//            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("SUBREPORT_DIR", ruta);
//            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            parametros.put("USUARIO", ub.getUsuario());
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

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

    public void obtenerPorIdAtencion(SolicitudLogisticoBean solBean) {
        try {
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            ActividadService actividadService = BeanFactory.getActividadService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

            solicitudLogisticoBean = new SolicitudLogisticoBean();
            sumaImporte = 0.00D;

            solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(solBean.getIdRequerimiento(), solBean.getUnidadNegocioBean().getUniNeg());
            //obtengo la activida,objetivo y plan correspondiente
            actividadBean = actividadService.obtenerPorId(solicitudLogisticoBean.getActividadBean().getIdActividad());
            solicitudLogisticoBean.setActividadBean(actividadBean);
            //obtengo el detalle de la solicitud
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.obtenerPorSolicitud(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());

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
                sumaImporte = sumaImporte - (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
            } else {
                sumaImporte = sumaImporte - (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
            }

            listaSolicitudLogDetalleBean.remove(bean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaCr() {
        CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
        List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
        listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
    }

    //Centro de Responsabilidad
    public void distribuir() {
        try {
            //1.-Mappear Dual a Lista
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
//            DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad = new DualListModel<>();
//            dualCentroResponsabilidad=dualCentroResponsabilidadBean;
            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
//            for (int i = 0; i < dualCentroResponsabilidadBean.getTarget().size(); i++) {
//                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
//                Object objeto = dualCentroResponsabilidadBean.getTarget().get(i);
//                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
//                listaCentroResponsabilidad.add(cr);
//            }
            //2.-Invocar calculadora
            for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                if (solicitudLogisticoBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                        solicitudLogisticoBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, solicitudLogisticoBean.getImportePropuesto());
                        break;
                    }
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                        solicitudLogisticoBean.setImportePropuesto(solicitudLogisticoBean.getImportePropuesto());
                        new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, solicitudLogisticoBean.getImportePropuesto());
                        break;
                    }
                    if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                        break;
                    }
                }
            }
            //3.-Crear Lista Respuesta
            List<DetRequerimientoCRBean> listaDetRequerimientoCRBean = new ArrayList<>();
            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                DetRequerimientoCRBean detRequerimientoCRBean = new DetRequerimientoCRBean();
                detRequerimientoCRBean.setSolicitudLogisticoBean(solicitudLogisticoBean);
                detRequerimientoCRBean.setTipoDistribucion(new CodigoBean(solicitudLogisticoBean.getCodDistribucion()));
                detRequerimientoCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                detRequerimientoCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                listaDetRequerimientoCRBean.add(detRequerimientoCRBean);
            }
            solicitudLogisticoBean.setListaDetRequerimientoCRBean(listaDetRequerimientoCRBean);
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
        for (DetRequerimientoCRBean detRequerimientoCRBean : solicitudLogisticoBean.getListaDetRequerimientoCRBean()) {
            sum = sum + detRequerimientoCRBean.getValorD();
        }
        if (Objects.equals(sum, solicitudLogisticoBean.getImporte())) {
            return true;
        }
        return rpta;
    }

    public void cargarCatalogo() {
        try {
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));

            //listaCatalogoBean = catalogoService.obtenerPorFiltro(catalogoBean);
            listaCatalogoBean = new ArrayList<>();
            //   catalogoFamiliaBean = catalogoService.obtenerCatalogoFamiliaPorNombre(tipoSolicitudBean.getCodigo()); 
            catalogoBean = catalogoService.obtenerCatalogoPorNombre(tipoCategoriaBean.getCodigo());
            switch (tipoCategoriaBean.getCodigo()) {
                case "Material":
                    listaCatalogoBean = catalogoService.obtenerMateriales(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    this.flgActivo = false;
                    this.flgAlmacen = true;
                    this.flgServicio = false;
                    this.flgCategoria = true;
                    break;
                case "Activo Fijo":
                    listaCatalogoBean = catalogoService.obtenerActivos(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    this.flgActivo = true;
                    this.flgAlmacen = false;
                    this.flgServicio = false;
                    this.flgCategoria = true;
                    break;
                case "Servicio":
                    listaCatalogoBean = catalogoService.obtenerServicios();
                    this.flgActivo = false;
                    this.flgAlmacen = false;
                    this.flgServicio = true;
                    this.flgCategoria = true;
                    break;
                case "":
                    this.flgCategoria = false;
            }
            if (tipoCategoriaBean.getCodigo() == null && tipoCategoriaBean.getCodigo().equals("")) {
                this.flgCategoria = false;
            }
            System.out.println("categoria: " + tipoCategoriaBean.getCodigo());

//            listaSolicitudLogDetalleBean = new ArrayList<>();
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

    public void calcularImportePorItem() {
        try {

            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean tipoCategoriaBean = new CodigoBean();

            tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
            solicitudLogisticoBean.setTipoCategoriaBean(tipoCategoriaBean);
            sumaImporte = 0.0;
            Integer est = 1;
            for (SolicitudLogDetalleBean lista : listaSolicitudLogDetalleBean) {
                System.out.println("");
                sumaImporte = sumaImporte + lista.getCantidadSolicitada() * lista.getCatalogoBean().getPrecioRef();
                if (lista.getCantidadSolicitada() != null) {
                    if (lista.getCantidadSolicitada() == 0) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                }
            }
            if (est.equals(1)) {
                this.flgCantidad = Boolean.FALSE;
            } else {
                this.flgCantidad = Boolean.TRUE;
            }
            System.out.println("cantidad: " + flgCantidad);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//     public void asignarFacturaOC(RowEditEvent event) {
//            for (int i = 0; i < listaOrdenCompraDetalleBean.size(); i++) {
//                OrdenCompraDetalleBean ordenCompra = new OrdenCompraDetalleBean();
//                ordenCompra.setIdFacturaCompra(((OrdenCompraDetalleBean) event.getObject()).getIdFacturaCompra());
//                if (listaOrdenCompraDetalleBean.get(i).getIdDetOrdenCompra().equals(((OrdenCompraDetalleBean) event.getObject()).getIdDetOrdenCompra())) {
//                    String cadena[] = ordenCompra.getIdFacturaCompra().split("-");
//                    String cadena2 = cadena[0];
//                    String cadena3 = cadena[1];
//                    listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setSerieDoc(cadena2);
//                    listaOrdenCompraDetalleBean.get(i).getFacturaCompraBean().setNroDoc(cadena3);
//                    listaOrdenCompraDetalleBean.get(i).setCantidadRecibida(((OrdenCompraDetalleBean) event.getObject()).getCantidadRecibida());
//                    listaOrdenCompraDetalleBean.get(i).setImporte(((OrdenCompraDetalleBean) event.getObject()).getImporte());
    public String asignarEstado(SolicitudLogDetalleBean beanSD) {
        String pagina = null;
        Integer stockActual;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
//                for(int i=0; i<listaSolicitudLogDetalleBean.size();i++){
                for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                    Integer cantParcial = 0;
                    Integer cantidadMovi = 0;
                    if (bean.getIdDetRequerimiento().equals(beanSD.getIdDetRequerimiento())) {

                        //Los estados que se le puede asignar a un item es:Asignado Total,Asignado Parcial, Nada
                        CodigoService codigoService = BeanFactory.getCodigoService();
                        CodigoBean tipoEstadoBean = new CodigoBean();
//                        InventarioActivoService inventarioActivoService = BeanFactory.getInventarioActivoService();
//                        InventarioAlmacenService inventarioAlmacenService = BeanFactory.getInventarioAlmacenService();
                        InventarioActivoBean inventarioActivoBean = new InventarioActivoBean();
                        InventarioAlmacenBean inventarioAlmacenBean = new InventarioAlmacenBean();
                        bean = beanSD;//:)
                        if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")) {
                            cantParcial = bean.getCantidadEntregadaParcial();
                        } else {
                            cantParcial = 0;
                        }
//                                                                                                                     . .                                 . .
                        //pensando... 
                        if (bean.getCantidadEntregada() >= bean.getCantidadSolicitada())// quiere decir que es Asignado Total
                        {
                            if (bean.getSolicitudLogisticoBean().getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                                if (bean.getStockAyuda() >= bean.getCantidadEntregada()) {
                                    if (bean.getCantidadSolicitada() == bean.getCantidadEntregada() + cantParcial) {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    } else {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    }
                                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    bean.setTipoEstadoBean(tipoEstadoBean);
                                    stockActual = (bean.getStockAyuda() - bean.getCantidadEntregada());
//                                    bean.getCatalogoBean().getInventarioActivoBean().setStockActual(stockActual);
                                    bean.setStockAyuda(stockActual);//vista
                                    inventarioActivoBean.setStockActual(stockActual);//est epara sirve?
                                    bean.getCatalogoBean().getInventarioActivoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    bean.setCantidadMovi(bean.getCantidadEntregada());
                                    System.out.println(bean.getCantidadMovi());
                                    bean.setCantidadEntregada(bean.getCantidadEntregada() + cantParcial);
                                    //                                inventarioActivoService.modificarStockActual(bean.getCatalogoBean().getInventarioActivoBean()); 

                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
                                    bean.setCantidadEntregada(0);
                                    onRowCancel(beanSD);
                                }

                            } else {
                                if (bean.getStockAyuda() >= bean.getCantidadEntregada()) {
//                                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    if (bean.getCantidadSolicitada() == bean.getCantidadEntregada() + cantParcial) {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    } else {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    }
                                    bean.setTipoEstadoBean(tipoEstadoBean);
                                    stockActual = (bean.getStockAyuda() - bean.getCantidadEntregada());
//                                    bean.getCatalogoBean().getInventarioAlmacenBean().setStockActual(stockActual);
                                    bean.setStockAyuda(stockActual);//vista
                                    inventarioAlmacenBean.setStockActual(stockActual);
                                    bean.setCantidadMovi(bean.getCantidadEntregada());
                                    System.out.println(bean.getCantidadMovi());
                                    bean.getCatalogoBean().getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    bean.getCatalogoBean().setIdCatalogo(bean.getCatalogoBean().getIdCatalogo());
                                    bean.setCantidadEntregada(bean.getCantidadEntregada() + cantParcial);
//                                inventarioAlmacenService.modificarStockActual(bean.getCatalogoBean().getInventarioAlmacenBean()); 
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
                                    bean.setCantidadEntregada(0);
                                    onRowCancel(beanSD);
                                }
                            }
                        } else// quiere decir que es Asignado Parcial
                        {

//                    if ((bean.getCantidadSolicitada() + bean.getMaterialBean().getStockMin()) <= bean.getMaterialBean().getStockActual()) {
//                        //quiere decir que tiene stock, aun asi le entrega menos
//                        RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
//                    }
                            if (bean.getSolicitudLogisticoBean().getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {
                                if (bean.getStockAyuda() >= bean.getCantidadEntregada()) {
//                                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    if (bean.getCantidadSolicitada() == bean.getCantidadEntregada() + cantParcial) {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    } else {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    }
                                    bean.setTipoEstadoBean(tipoEstadoBean);
                                    stockActual = (bean.getStockAyuda() - bean.getCantidadEntregada());
//                                    bean.getCatalogoBean().getInventarioActivoBean().setStockActual(stockActual);
                                    bean.setStockAyuda(stockActual);//vista
                                    inventarioActivoBean.setStockActual(stockActual);
                                    bean.getCatalogoBean().getInventarioActivoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    bean.setCantidadMovi(bean.getCantidadEntregada());
                                    System.out.println(bean.getCantidadMovi());
                                    bean.setCantidadEntregada(bean.getCantidadEntregada() + cantParcial);
//                                inventarioActivoService.modificarStockActual(bean.getCatalogoBean().getInventarioActivoBean()); 
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
                                    bean.setCantidadEntregada(0);
                                    onRowCancel(beanSD);
                                }
                            } else {
                                if (bean.getStockAyuda() >= bean.getCantidadEntregada()) {
//                                    tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    if (bean.getCantidadSolicitada() == bean.getCantidadEntregada() + cantParcial) {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Total", new TipoCodigoBean("TipoStatusDetReq")));
                                    } else {
                                        tipoEstadoBean = codigoService.obtenerPorCodigo(new CodigoBean(0, "Asignado Parcial", new TipoCodigoBean("TipoStatusDetReq")));
                                    }
                                    bean.setTipoEstadoBean(tipoEstadoBean);
                                    stockActual = (bean.getStockAyuda() - bean.getCantidadEntregada());
//                                    bean.getCatalogoBean().getInventarioAlmacenBean().setStockActual(stockActual);
                                    bean.setStockAyuda(stockActual);//vista
                                    inventarioAlmacenBean.setStockActual(stockActual);
                                    bean.getCatalogoBean().getInventarioAlmacenBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    bean.getCatalogoBean().setIdCatalogo(bean.getCatalogoBean().getIdCatalogo());
                                    bean.setCantidadMovi(bean.getCantidadEntregada());
                                    System.out.println(bean.getCantidadMovi());
                                    bean.setCantidadEntregada(bean.getCantidadEntregada() + cantParcial);
//                                inventarioAlmacenService.modificarStockActual(bean.getCatalogoBean().getInventarioAlmacenBean());
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("validaStockDisponible", true);
                                    bean.setCantidadEntregada(0);
                                    onRowCancel(beanSD);
                                }
                            }

                        }
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

    public void onRowCancel(SolicitudLogDetalleBean beanSD) {
        FacesMessage msg = new FacesMessage("Error", beanSD.getIdDetRequerimiento().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void calcularStock(SolicitudLogDetalleBean bean) {
        try {
            int stockActual;

            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Material")) {

                stockActual = (bean.getCatalogoBean().getInventarioActivoBean().getStockActual() - bean.getCantidadEntregada());
            } else if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Activo Fijo")) {

                stockActual = (bean.getCatalogoBean().getInventarioAlmacenBean().getStockActual() - bean.getCantidadEntregada());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void agregarItems(CatalogoBean bean) {
        try {
            CodigoBean tipoCategoriaBean = new CodigoBean();
            SolicitudLogDetalleBean solicitudLogDetalleBean = new SolicitudLogDetalleBean();
            CodigoService codigoService = BeanFactory.getCodigoService();

            System.out.println("flgServicio: " + flgTransporte);
            //Si es false, quiere decir que no ha sido agregado ese item seleccionado
//            if (tipoConceptoBean.getIdTipoConcepto() == MaristaConstantes.id_Transporte) {
//                tipoConceptoBean.setNombre(MaristaConstantes.transporte);
//
//                switch (tipoConceptoBean.getNombre()) {
//                    case "Transporte correos y gastos de viaje":
//                        this.flgTransporte = true;
//                        break;
//
//                }
//            }
//            if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals('Servicio'))
//            {
//                this.
//            }
//          
//            
//                    listaCatalogoBean = catalogoService.obtenerServicios();
//                    this.flgActivo = false;
//                    this.flgAlmacen = false;
//                    this.flgServicio = true;
//                    break;
            if (!validarItemPorAgregar(bean)) {
                tipoCategoriaBean = codigoService.obtenerPorId(new CodigoBean(solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo(), "", ""));
                solicitudLogisticoBean.setTipoCategoriaBean(tipoCategoriaBean);

                if (solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                    solicitudLogDetalleBean.setCantidadSolicitada(1);
                    sumaImporte = sumaImporte + (solicitudLogDetalleBean.getCantidadSolicitada() * bean.getPrecioRef());
                }

                solicitudLogDetalleBean.setCatalogoBean(bean);
                listaSolicitudLogDetalleBean.add(solicitudLogDetalleBean);
            }

            Integer est = 1;
            for (SolicitudLogDetalleBean lista : listaSolicitudLogDetalleBean) {
                System.out.println("");
                if (!solicitudLogisticoBean.getTipoCategoriaBean().getCodigo().equals("Servicio")) {
                    sumaImporte = sumaImporte + lista.getCantidadSolicitada() * lista.getCatalogoBean().getPrecioRef();
                }
                if (lista.getCantidadSolicitada() != null) {
                    if (lista.getCantidadSolicitada() == 0) {
                        est = 0;
                    } else {
                        est = 1;
                    }
                }
            }
            if (est.equals(1)) {
                this.flgCantidad = Boolean.FALSE;
            } else {
                this.flgCantidad = Boolean.TRUE;
            }
            System.out.println("flgCantidad: " + flgCantidad);
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
                    if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Parcial")) {
                        CodigoBean tipoEstado = new CodigoBean();
                        tipoEstado = codigoService.obtenerPorCodigo(new CodigoBean(0, "Proceso", new TipoCodigoBean("TipoStatusReq")));
                        solicitudLogisticoBean.getTipoEstadoBean().setIdCodigo(tipoEstado.getIdCodigo());
                    }
                    if (bean.getTipoEstadoBean().getCodigo().equals("Asignado Total")) {
                        CodigoBean tipoEstado = new CodigoBean();
                        tipoEstado = codigoService.obtenerPorCodigo(new CodigoBean(0, "Atendido", new TipoCodigoBean("TipoStatusReq")));
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
                if (solicitudLogisticoBean.getPersonalBean().getNombreCompleto() != null && !solicitudLogisticoBean.getPersonalBean().getNombreCompleto().equals("")
                        && !solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo().equals(0) && solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo() != null
                        && solicitudLogisticoBean.getTitulo() != null && !solicitudLogisticoBean.getTitulo().equals("")
                        && solicitudLogisticoBean.getTipoEstadoBean().getIdCodigo() != null && !solicitudLogisticoBean.getTipoEstadoBean().getIdCodigo().equals(0)
                        && solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoSolicitud() != null && !solicitudLogisticoBean.getTipoSolicitudBean().getIdTipoSolicitud().equals(0)
                        && solicitudLogisticoBean.getTipoPrioridadBean().getIdCodigo() != null && !solicitudLogisticoBean.getTipoPrioridadBean().getIdCodigo().equals(0)
                        && sumaImporte != null && !sumaImporte.equals(0.0)
                        && solicitudLogisticoBean.getFechaSolicitud() != null
                        && solicitudLogisticoBean.getImportePropuesto() != null && !solicitudLogisticoBean.getImportePropuesto().equals(0.0)) {
                    if (solicitudLogisticoBean.getIdRequerimiento() == null) {
                        solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
                        if (solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo().equals(MaristaConstantes.Id_Categoria_Almacen)
                                || solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo().equals(MaristaConstantes.Id_Categoria_Activo)) {
                            solicitudLogisticoBean.setFlgTransporte(Boolean.FALSE);
                        }
                        solicitudLogisticoService.insertar(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
//                    solicitudLogisticoDetalleService.insertar(solicitudLogisticoBean.getIdRequerimiento(), listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                    } else {
                        this.asignarEstadoPorItems();
                        solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaDBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaHBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
                        if (solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo().equals(MaristaConstantes.Id_Categoria_Almacen)
                                || solicitudLogisticoBean.getTipoCategoriaBean().getIdCodigo().equals(MaristaConstantes.Id_Categoria_Activo)) {
                            solicitudLogisticoBean.setFlgTransporte(Boolean.FALSE);
                        }
                        solicitudLogisticoService.modificarSOL(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
//                    solicitudLogisticoDetalleService.eliminar(solicitudLogisticoBean.getIdRequerimiento());
//                    solicitudLogisticoDetalleService.insertar(solicitudLogDetalleBean);

//                     solicitudLogisticoService.insertar(solicitudLogisticoBean,listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                    }
//                limpiar();
//                distribuir();
                    listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);
                    listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerTodos();//lista cuando se atiende la solicitud
                    if (flgTipoCatalogo != null) {
                        if (flgTipoCatalogo.equals(0)) {
                            solicitudLogisticoDetalleService.ejecutarDetalleProcedure(solicitudLogDetalleBean);
                        }
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                this.limpiar();
                } else {
                    new MensajePrime().addInformativeMessagePer("Llene todos los campos requeridoss");
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String grabarDespacho() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();

                if (solicitudLogisticoBean.getIdRequerimiento() == null) {
                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
                    solicitudLogisticoService.insertar(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                } else {
                    this.asignarEstadoPorItems();
                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
                    solicitudLogisticoService.modificarDES(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                }
//                limpiar();
//                distribuir();
                listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);
                listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerTodos();//lista cuando se atiende la solicitud
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

    public String grabarServicio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();

                if (solicitudLogisticoBean.getIdRequerimiento() == null) {
                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
                    solicitudLogisticoService.insertarServicio(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
//                   solicitudLogisticoDetalleService.insertar(solicitudLogisticoBean.getIdRequerimiento(), listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                } else {
                    this.asignarEstadoPorItems();
                    solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().setConceptoBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaDBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
//                    solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().setPlanContableCuentaHBean(solicitudLogDetalleBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
                    solicitudLogisticoService.modificarSOL(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
//                    solicitudLogisticoDetalleService.eliminar(solicitudLogisticoBean.getIdRequerimiento());
//                    solicitudLogisticoDetalleService.insertar(solicitudLogDetalleBean);

//                     solicitudLogisticoService.insertar(solicitudLogisticoBean,listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                }
//                limpiar();
//                distribuir();
                listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorFiltro(solicitudLogisticoFiltroBean);
                listaSolicitudLogisticoDespBean = solicitudLogisticoService.obtenerTodos();//lista cuando se atiende la solicitud
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

    public String grabarAprobacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                solicitudLogisticoBean.setCreaPor(usuarioSessionBean.getUsuario());
                solicitudLogisticoService.modificarSOL(solicitudLogisticoBean, listaSolicitudLogDetalleBean, solicitudLogDetalleBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void grabarRequ() {
        try {
            System.out.println(listaCentroResponsabilidadBeanB.size());
            System.out.println(listaCentroResponsabilidadBean.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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
                solicitudLogisticoService.modificar(solicitudLogisticoBean);

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

    /* METODOS SUBIDA DE ARCHIVO */
    public void cargarCsv(FileUploadEvent event) {
        try {
            if (getIdTipoCategoria() != null) {
                copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
                setFlgTipoCatalogo(0);
            } else {
                new MensajePrime().selTipoSolicitud();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }
    /* METODOS SUBIDA DE ARCHIVO */

    public void cargarExcel(FileUploadEvent event) {
        try {

            // Specify the file path which you want to create or write
            File src = new File("./testdata/test.xlsx");
            // Load the file
            FileInputStream fis = new FileInputStream(src);
            // load the workbook
            XSSFWorkbook wb = new XSSFWorkbook(fis);

            // get the sheet which you want to modify or create
            XSSFSheet sh1 = wb.getSheetAt(0);

            // getRow specify which row we want to read and getCell which column
            System.out.println(sh1.getRow(0).getCell(0).getStringCellValue());

            System.out.println(sh1.getRow(0).getCell(1).getStringCellValue());

            System.out.println(sh1.getRow(1).getCell(0).getStringCellValue());

            System.out.println(sh1.getRow(1).getCell(1).getStringCellValue());

            System.out.println(sh1.getRow(2).getCell(0).getStringCellValue());

            System.out.println(sh1.getRow(2).getCell(1).getStringCellValue());

            // here createCell will create column
            // and setCellvalue will set the value
            sh1.getRow(0).createCell(2).setCellValue("2.41.0");

            sh1.getRow(1).createCell(2).setCellValue("2.5");

            sh1.getRow(2).createCell(2).setCellValue("2.39");

            // here we need to specify where you want to save file
            FileOutputStream fout = new FileOutputStream(new File("location of file/filename.xlsx"));

            // finally write content 
            wb.write(fout);

            // close the file
            fout.close(); 
        } catch (Exception e) { 
            System.out.println(e.getMessage()); 
        } 
    }

    /**
     * A dirty simple program that reads an Excel file.
     *
     * @author www.codejava.net
     *
     */
    public void convertirXlsCsv2(FileUploadEvent event) throws IOException {

        String excelFilePath = "C:\\libro1.xlsx";
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        break;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }

        inputStream.close();
    }

    public void convertirXlsCsv(FileUploadEvent event) {
        StringBuffer data = new StringBuffer();
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            HSSFWorkbook workbook = new HSSFWorkbook(miArchivo);
            HSSFSheet sheet = workbook.getSheetAt(0);

            System.out.println(">>>>>> EMPEZANDO-----------------------------------------------------");
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            System.out.println(">>>" + destination);
            //MOVIENDO DATA
            String fileName = "inventario.csv";
            File out = new File(destination + fileName);
            //CREANDO XLS Y CSV
            FileOutputStream fos = new FileOutputStream(out);

            Cell cell;
            Row row;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            data.append("" + ",");
                            break;
                        default:
                            data.append(cell + ",");
                    }
                    data.append('\n');
                }
                fos.write(data.toString().getBytes());
//                fos.close();
            }

            System.out.println(">>>>>> CONVIRTIENDO-----------------------------------------------------");
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CatalogoBean catalogoBean = new CatalogoBean();
            catalogoBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            catalogoBean.setObjFile(fileName);
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.execProInventario(catalogoBean);
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                System.out.println(">>>" + bean.getCantidadSolicitada());
                System.out.println(">>>" + bean.getCatalogoBean().getPrecioRef());
                System.out.println("---------------------------------------------------");
                if (getIdTipoCategoria().equals(MaristaConstantes.Id_Categoria_Servicio)) {
                    solicitudLogDetalleBean.setCantidadSolicitada(1);
                }
                sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            System.out.println(">>>" + destination);
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            CatalogoService catalogoService = BeanFactory.getCatalogoService();
            CatalogoBean catalogoBean = new CatalogoBean();
            catalogoBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            catalogoBean.setObjFile(fileName);
            catalogoBean.setIdTipoCategoria(getIdTipoCategoria());
            SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
            if (flgTransporte) {
                listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.execProInventarioTrans(catalogoBean);
            } else {
                listaSolicitudLogDetalleBean = solicitudLogisticoDetalleService.execProInventario(catalogoBean);
            }
            for (SolicitudLogDetalleBean bean : listaSolicitudLogDetalleBean) {
                System.out.println(">>>" + bean.getCantidadSolicitada());
                System.out.println(">>>" + bean.getCatalogoBean().getPrecioRef());
                System.out.println("---------------------------------------------------");
                if (tipoServicio != null) {
                    if (getIdTipoCategoria().equals(MaristaConstantes.Id_Categoria_Servicio)) {
                        bean.setCantidadSolicitada(1);
                    }
                }
                sumaImporte = sumaImporte + (bean.getCantidadSolicitada() * bean.getCatalogoBean().getPrecioRef());
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void subirArchivo(String fileName, InputStream in) {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            System.out.println(">>>" + destination);
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ponerTipoSolicitud() {
        try {
            if (getIdTipoCategoria() != null) {
                System.out.println(">>>" + getIdTipoCategoria());
                getSolicitudLogisticoBean().getTipoCategoriaBean().setIdCodigo(getIdTipoCategoria());
                if (getIdTipoCategoria().equals(MaristaConstantes.Id_Categoria_Almacen)) {
                    this.flgActivo = false;
                    this.flgAlmacen = true;
                    this.flgServicio = false;
                } else if (getIdTipoCategoria().equals(MaristaConstantes.Id_Categoria_Activo)) {
                    this.flgActivo = true;
                    this.flgAlmacen = false;
                    this.flgServicio = false;
                } else if (getIdTipoCategoria().equals(MaristaConstantes.Id_Categoria_Servicio)) {
                    this.flgActivo = false;
                    this.flgAlmacen = false;
                    this.flgServicio = true;
                }
            }
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

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidadBean() {
        if (dualCentroResponsabilidadBean == null) {
            dualCentroResponsabilidadBean = new DualListModel<>();
        }
        return dualCentroResponsabilidadBean;
    }

    public void setDualCentroResponsabilidadBean(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean) {
        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
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

    public DataTable getDataTable() {
        if (dataTable == null) {
            dataTable = new DataTable();
        }
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
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

    public List<DetRequerimientoCRBean> getListaDetRequerimientoCRTopBean() {
        if (listaDetRequerimientoCRTopBean == null) {
            listaDetRequerimientoCRTopBean = new ArrayList<>();
        }
        return listaDetRequerimientoCRTopBean;
    }

    public void setListaDetRequerimientoCRTopBean(List<DetRequerimientoCRBean> listaDetRequerimientoCRTopBean) {
        this.listaDetRequerimientoCRTopBean = listaDetRequerimientoCRTopBean;
    }

    public Integer getIdTipoPrioridad() {
        return idTipoPrioridad;
    }

    public void setIdTipoPrioridad(Integer idTipoPrioridad) {
        this.idTipoPrioridad = idTipoPrioridad;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();

        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public List<PresupuestoBean> getListaPresupuestoBean() {
        if (listaPresupuestoBean == null) {
            listaPresupuestoBean = new ArrayList<>();
        }
        return listaPresupuestoBean;
    }

    public void setListaPresupuestoBean(List<PresupuestoBean> listaPresupuestoBean) {
        this.listaPresupuestoBean = listaPresupuestoBean;
    }

    public List<DetActividadBean> getListaDetActividadBean() {
        if (listaDetActividadBean == null) {
            listaDetActividadBean = new ArrayList<>();
        }
        return listaDetActividadBean;
    }

    public void setListaDetActividadBean(List<DetActividadBean> listaDetActividadBean) {
        this.listaDetActividadBean = listaDetActividadBean;
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

    public List<MovimientoAlmacenBean> getListaMovimientoAlmacenBean() {
        return listaMovimientoAlmacenBean;
    }

    public void setListaMovimientoAlmacenBean(List<MovimientoAlmacenBean> listaMovimientoAlmacenBean) {
        if (listaMovimientoAlmacenBean == null) {
            listaMovimientoAlmacenBean = new ArrayList<>();
        }
        this.listaMovimientoAlmacenBean = listaMovimientoAlmacenBean;
    }

    public Boolean getFlgTransporte() {
        return flgTransporte;
    }

    public void setFlgTransporte(Boolean flgTransporte) {
        this.flgTransporte = flgTransporte;
    }

    public String getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(String idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public Integer getIdTipoCategoria() {
        return idTipoCategoria;
    }

    public void setIdTipoCategoria(Integer idTipoCategoria) {
        this.idTipoCategoria = idTipoCategoria;
    }

    public Integer getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(Integer tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Integer getTipoActivo() {
        return tipoActivo;
    }

    public void setTipoActivo(Integer tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public Integer getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(Integer tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Boolean getFlgUniNeg() {
        return flgUniNeg;
    }

    public void setFlgUniNeg(Boolean flgUniNeg) {
        this.flgUniNeg = flgUniNeg;
    }

    public Integer getFlgTipoCatalogo() {
        return flgTipoCatalogo;
    }

    public void setFlgTipoCatalogo(Integer flgTipoCatalogo) {
        this.flgTipoCatalogo = flgTipoCatalogo;
    }

    public Integer getIdTipoSolicitudAutoriza() {
        return idTipoSolicitudAutoriza;
    }

    public void setIdTipoSolicitudAutoriza(Integer idTipoSolicitudAutoriza) {
        this.idTipoSolicitudAutoriza = idTipoSolicitudAutoriza;
    }

    public Boolean getFlgCantidad() {
        return flgCantidad;
    }

    public void setFlgCantidad(Boolean flgCantidad) {
        this.flgCantidad = flgCantidad;
    }

    public Boolean getFlgEstado() {
        return flgEstado;
    }

    public void setFlgEstado(Boolean flgEstado) {
        this.flgEstado = flgEstado;
    }

    public Boolean getFlgCategoria() {
        return flgCategoria;
    }

    public void setFlgCategoria(Boolean flgCategoria) {
        this.flgCategoria = flgCategoria;
    }

}
