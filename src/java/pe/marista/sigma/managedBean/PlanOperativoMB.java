/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import com.lowagie.text.Document;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.component.tree.Tree;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.Visibility;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.bean.ActividadCrBean;
import pe.marista.sigma.bean.AnioBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.IndicadorBean;
import pe.marista.sigma.bean.LineaEstrategicaBean;
import pe.marista.sigma.bean.ObjOperativoBean;
import pe.marista.sigma.bean.ObjetivoEspecificoBean;
import pe.marista.sigma.bean.ObjetivoEstrategicaBean;
import pe.marista.sigma.bean.ObjetivoEstrategicoDetBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PlanEstrategicoBean;
import pe.marista.sigma.bean.PlanOperativoBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetActividadRepBean;
import pe.marista.sigma.bean.reporte.DetActividadRepSubBean;
import pe.marista.sigma.bean.reporte.PresupuestoCrRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCrRepSubBean;
import pe.marista.sigma.bean.reporte.PresupuestoGeneralRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadCrService;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.IndicadorService;
import pe.marista.sigma.service.LineaEstrategicaService;
import pe.marista.sigma.service.ObjetivoEstrategicoDetService;
import pe.marista.sigma.service.ObjetivoEstrategicoService;
import pe.marista.sigma.service.ObjetivoOperativoService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PlanEstrategicoService;
import pe.marista.sigma.service.PlanOperativoService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.PresupuestoUniOrgService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class PlanOperativoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void PlanOperativoMB() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerCodObjOperativo();
//            cargarAnio();
            //Lista Unidad de NEgocio
            UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
            getListaUnidadNegocioBean();
            listaUnidadNegocioBean = unidadNegocioService.obtenerTodos();

            //Lista Unidad Organica
            UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            getListaUniOrg();
            listaUniOrg = unidadOrganicaService.obtenerTodos();

            //Lista Plan Operativo
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            getListaPlanObjetivo();
            listaPlanObjetivo = planOperativoService.obtenerTodos();

            CodigoService codigoService = BeanFactory.getCodigoService();
            //Tipo Actividad
            getListaActividadCodigo();
            listaActividadCodigo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ACTIVIDAD));

            //Tipo Unidad de Medida
            getListaUniMedCodigo();
            listaUniMedCodigo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_UNIMED_OPER));

            //Filtro Plan Operativo
            getPlanOperativoFiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Lista Anio
            getListaAnioBean();
            getObjetivoEstrategicoDetFiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Filtro Actividad
            getObjOperativoFiltro().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Datos Estrategicos
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            getListaPlanEstrategicoBean();
            listaPlanEstrategicoBean = planEstrategicoService.obtenerTodosUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Lista Tipo Tarea
            getListaTipoTarea();
            listaTipoTarea = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_TAREA));

            //Lista ObjEstrategico Det 
//            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
//            getListaObjetivoEstrategicoDetBean();
//            listaObjetivoEstrategicoDetBean = objetivoEstrategicoDetService.obtenerTodos();
            //Plan Contable
            listaTipoGrupoCuenta = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_GRUPO_CTA));
            listaTipoCuenta = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CTA));

            listaMesesForSup();
//            getPlanFiltroContableBean(); 

            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            getListPresupuestoBean();
            listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Tipo_Valor
            CodigoService valorService = BeanFactory.getCodigoService();
            getListaCodigoValor();
            listaCodigoValor = valorService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));

            //Lista Dual Centro_Responsabilidad
//            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
//            getListCR(); 
//            listCR = centroResponsabilidadService.obtenerCrOutAct();
//            getListCRB();
//            listCRB = centroResponsabilidadService.obtenerCrInAct();
//            dualDetActCr = new DualListModel<>(listCR, listCRB); 
            //Modificar Presupuesto
//            getListPresupuestoExecBean();
//            listPresupuestoExecBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            for (PresupuestoBean pres : listPresupuestoExecBean) {
//                System.out.println(pres.getUniNeg());
//                Integer dato = 0;
//                dato = presupuestoService.obtenerPresExec(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), pres.getPlanContableBean().getCuenta());
//                if (dato == null || dato.equals(0)) {
//                    dato = 0;
//                    BigDecimal p = new BigDecimal(dato);
//                    pres.setPresupuestoEjec(p);
//                } else {
//                    BigDecimal q = new BigDecimal(dato);
//                    pres.setPresupuestoEjec(q);
//                }
//                presupuestoService.modificarPresupuestoExec(pres);
//            }
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            getListPlanContableBean();
            listPlanContableBean = planContableService.obtenerPlanFiltro();

            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            getListaDetActividadPresupuestoBean();
            listaDetActividadPresupuestoBean = detActividadService.obtenerPresupuesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            getListCrAct();
            listCrAct = actividadCrService.obtenerPresupuestoCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            crearDocumento();

            getListCrActividad();
            listCrActividad = actividadCrService.obtenerGrafoPres(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaTipoUsoIndicador();
            listaTipoUsoIndicador = codigoService.obtenerPorTipoOpe(new TipoCodigoBean(MaristaConstantes.TIP_UsoIndicador));

            obtenerCodigoIndicador();

            getListaIndicadorBean();
//            listaIndicadorBean = indicadorService.obtenerTodos();
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);

            getListaTipoValor();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));

            getListaUnidadOrganica();
            listaUnidadOrganica = unidadOrganicaService.obtenerTodos();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String url[] = origRequest.getRequestURL().toString().split("mantPresupuestoGrafoUniOrg.xhtml");
            String url1 = url[0];
            recurso = url1.replace("mantPlanOperativo.xhtml", "mantPresupuestoGrafo.xhtml");
            System.out.println("nombre del recurso: " + recurso);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private PlanOperativoBean planOperativoBean;
    private ObjOperativoBean objOperativoBean;

    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private List<PlanEstrategicoBean> listPlanEstrategicoBean;
    private List<LineaEstrategicaBean> listLineaEstrategicaBean;
    private List<ObjetivoEstrategicaBean> listObjetivoEstrategicaBean;
    private List<IndicadorBean> listIndicadorBean;
    private List<PlanOperativoBean> listPlanOperativoBean;
    private List<ObjOperativoBean> listObjOperativoBean;

    private List<ObjetivoEspecificoBean> listObjetivoEspecificoBean;
    private List<UnidadOrganicaBean> listUnidadOrganicaBean;
    private UnidadNegocioBean unidadNegocioBean;

    //Plan Estrategico
    private PlanEstrategicoBean planEstrategicoBean;
    private List<PlanEstrategicoBean> listaPlanEstrategicoBean;

    //Linea Estrategica
    private LineaEstrategicaBean lineaEstrategicaBean;
    private List<LineaEstrategicaBean> listaLineaEstrategicaBean;

    //Obj. Estrategico
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean;

    //Bean de Ayuda
    private CodigoBean codigoBean;
    private List<CodigoBean> listaActividadCodigo;
    private List<CodigoBean> listaUniMedCodigo;
    private AnioBean anioBean;
    private List<AnioBean> listaAnioBean;

    //Unidad Organica
    private UnidadOrganicaBean unidadOrganicaBean;
    private List<UnidadOrganicaBean> listaUniOrg;

    //Plan Operativo
    private PlanOperativoBean planOperativo;
    private PlanOperativoBean planOperativo2;
    private List<PlanOperativoBean> listaPlanOperativo;
    private PlanOperativoBean planOperativoFiltroBean;
    private List<PlanOperativoBean> listaplanOperativoFiltroBean;
    private List<Integer> listaAnios;

    //Objetivo Operativo
    private ObjOperativoBean objOperativo;
    private List<ObjOperativoBean> listaObjetivoOperativo;
    private List<ObjOperativoBean> listaObjetivoActOperativo;
    private List<PlanOperativoBean> listaPlanObjetivo;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private ObjOperativoBean objOperativoFiltro;
    private List<ObjOperativoBean> listaObjetivoOperativoFiltro;

    private Tree root;
    private TreeNode rootNode = new DefaultTreeNode(getObjetivoEstrategicoDetBean().getPlanEstrategicoBean().getNombre(), null);

    //ObjDetEStrategico
    private ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean;
    private ObjetivoEstrategicoDetBean objetivoEstrategicoDetFiltroBean;
    private List<ObjetivoEstrategicoDetBean> listaObjetivoEstrategicoDetBean;
    private List<ObjetivoEstrategicoDetBean> listaSubObjetivoEstrategicoDetBean;

    //Actividad
    private ActividadBean actividadBean;
    private ActividadBean actividadFiltro;
    private ActividadBean actividadFiltroBean;
    private List<ActividadBean> listActividadBean;
    private List<CodigoBean> listaTipoTarea;
    private Boolean mostrarActv = false;
    private IndicadorBean indicadorFiltroBean;
    private List<IndicadorBean> listaIndicadorBean;
    private IndicadorBean indicadorBean;
    private List<CodigoBean> listaTipoUsoIndicador;
    private List<CodigoBean> listaTipoValor;
    private String resultado = "";

    //Plan Contable
    private PlanContableBean planContableBean;
    private PlanContableBean planFiltroContableBean;
    private List<PlanContableBean> listaPlanContableBean;
    private List<CodigoBean> listaTipoGrupoCuenta;
    private List<CodigoBean> listaTipoCuenta;
    private PlanContableBean planContableFiltroBean;
    private List<PlanContableBean> listPlan;

    //Persona
    private PersonaBean personaBean;
    private List<PersonaBean> listaPersonaBean;

    //Detalle Actividad
    private DetActividadBean detActividadBean;
    private List<DetActividadBean> listaDetActividadBean;
    private Map<String, Integer> listaMesesExpMap;
    private String var = " para el año ";
    private DualListModel<CentroResponsabilidadBean> dualDetActCr;
    private DualListModel<Object> dualDetActCr2;//Object
    private List<CentroResponsabilidadBean> listCR;
    private List<CentroResponsabilidadBean> listCRB;
    private List<DetActividadBean> listaDetActMes;
    private DetActividadBean detActMes;
    private Integer importeSubActividad;
    private Integer anio;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private CentroResponsabilidadBean centroResponsabilidadBean;
    private List<DetActividadBean> listaDetActividadPresupuestoBean;

    //Presupuesto
    private PresupuestoBean presupuestoBean;
    private List<PresupuestoBean> listPresupuestoBean;
    private List<PresupuestoBean> listPresFiltroBean;
    private PlanContableBean planPresupuestoBean;
    private List<PlanContableBean> listaPlanPresupuestoBean;
    private String parametro;
    private String parametro2;
    private Integer parametro3;
    private List<PresupuestoBean> listPresupuestoExecBean;
    private List<PlanContableBean> listPlanContableBean;

    //Presupuesto/UniOrg 
    private PlanContableBean planContablePresUo;
    private List<PlanContableBean> listPlanContablePresUo;
    private PresupuestoBean presupuestoFiltroUOBean;
    private List<PresupuestoBean> listPresupuestoFiltroUOBean;
    private PresupuestoUniOrgBean presupuestoUniOrgBean;
    private List<PresupuestoUniOrgBean> listPresupuestoUniOrgBean;
    private BarChartModel animatedModel2;
    private PieChartModel pieModel;
    private BarChartModel barModel;
    private ConceptoBean conceptoBean;
    private List<ConceptoBean> listConceptoBean;
    private List<PresupuestoUniOrgBean> listPresUoFiltroBean;
    private BarChartModel barModel2;
    private DetActividadBean filtroDetActividadBean;
    private List<DetActividadBean> listFiltroDetActividadBean;
    private Integer presupuesto;
    private List<ActividadCrBean> listaPresupuesto;

    //Actividad CR -> include Presupuesto/UniOrg
    private DetActividadBean detActivadadPresBean;
    private List<DetActividadBean> listDetActivadadPresBean;
    private ActividadCrBean actividadCrBean;
    private List<ActividadCrBean> listActividadCrBean;
    private List<ActividadCrBean> listActividadCrBean2;
    private List<CodigoBean> listaCodigoValor;
    private List<ActividadCrBean> listActividadCrCuentaBean;
    private Boolean mostrarSub;
    private String idCr;
    private Integer idActividad;
    private String nomActividad;
    private String nomCr;
    private List<ActividadCrBean> listCrAct;
    private List<ActividadCrBean> listCrActividad;
    private List<ActividadCrBean> listaCrA;
    private ActividadCrBean crA;
    private Integer exec = 0;

    //Ayuda
    private Boolean panelPlan;
    private Boolean panelLin;
    private Boolean panelObj;
    private Boolean panelRes;
    private String recurso;

    //Personal
    private List<PersonalBean> listaPersonalBean;
    private List<UnidadOrganicaBean> listaUnidadOrganica;

    //===========================Get y Set===============================================
    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public List<UnidadOrganicaBean> getListUnidadOrganicaBean() {
        if (listUnidadOrganicaBean == null) {
            listUnidadOrganicaBean = new ArrayList<>();
        }
        return listUnidadOrganicaBean;
    }

    public void setListUnidadOrganicaBean(List<UnidadOrganicaBean> listUnidadOrganicaBean) {
        this.listUnidadOrganicaBean = listUnidadOrganicaBean;
    }

    public List<ObjetivoEspecificoBean> getListObjetivoEspecificoBean() {
        return listObjetivoEspecificoBean;
    }

    public void setListObjetivoEspecificoBean(List<ObjetivoEspecificoBean> listObjetivoEspecificoBean) {
        this.listObjetivoEspecificoBean = listObjetivoEspecificoBean;
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

    public PlanOperativoBean getPlanOperativoBean() {
        if (planOperativoBean == null) {
            planOperativoBean = new PlanOperativoBean();
        }
        return planOperativoBean;
    }

    public void setPlanOperativoBean(PlanOperativoBean planOperativoBean) {
        this.planOperativoBean = planOperativoBean;
    }

    public List<ActividadBean> getListActividadBean() {
        return listActividadBean;
    }

    public void setListActividadBean(List<ActividadBean> listActividadBean) {
        this.listActividadBean = listActividadBean;
    }

    public List<PlanEstrategicoBean> getListPlanEstrategicoBean() {
        return listPlanEstrategicoBean;
    }

    public void setListPlanEstrategicoBean(List<PlanEstrategicoBean> listPlanEstrategicoBean) {
        this.listPlanEstrategicoBean = listPlanEstrategicoBean;
    }

    public List<LineaEstrategicaBean> getListLineaEstrategicaBean() {
        return listLineaEstrategicaBean;
    }

    public void setListLineaEstrategicaBean(List<LineaEstrategicaBean> listLineaEstrategicaBean) {
        this.listLineaEstrategicaBean = listLineaEstrategicaBean;
    }

    public List<ObjetivoEstrategicaBean> getListObjetivoEstrategicaBean() {
        return listObjetivoEstrategicaBean;
    }

    public void setListObjetivoEstrategicaBean(List<ObjetivoEstrategicaBean> listObjetivoEstrategicaBean) {
        this.listObjetivoEstrategicaBean = listObjetivoEstrategicaBean;
    }

    public List<IndicadorBean> getListIndicadorBean() {
        return listIndicadorBean;
    }

    public void setListIndicadorBean(List<IndicadorBean> listIndicadorBean) {
        this.listIndicadorBean = listIndicadorBean;
    }

    public List<PlanOperativoBean> getListPlanOperativoBean() {
        return listPlanOperativoBean;
    }

    public void setListPlanOperativoBean(List<PlanOperativoBean> listPlanOperativoBean) {
        this.listPlanOperativoBean = listPlanOperativoBean;
    }

    public List<ObjOperativoBean> getListObjOperativoBean() {
        return listObjOperativoBean;
    }

    public void setListObjOperativoBean(List<ObjOperativoBean> listObjOperativoBean) {
        this.listObjOperativoBean = listObjOperativoBean;
    }

    public List<PresupuestoBean> getListPresupuestoBean() {
        return listPresupuestoBean;
    }

    public void setListPresupuestoBean(List<PresupuestoBean> listPresupuestoBean) {
        this.listPresupuestoBean = listPresupuestoBean;
    }

    public List<PresupuestoUniOrgBean> getListPresupuestoUniOrgBean() {
        return listPresupuestoUniOrgBean;
    }

    public void setListPresupuestoUniOrgBean(List<PresupuestoUniOrgBean> listPresupuestoUniOrgBean) {
        this.listPresupuestoUniOrgBean = listPresupuestoUniOrgBean;
    }

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
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

    public PresupuestoBean getPresupuestoBean() {
        if (presupuestoBean == null) {
            presupuestoBean = new PresupuestoBean();
        }
        return presupuestoBean;
    }

    public void setPresupuestoBean(PresupuestoBean presupuestoBean) {
        this.presupuestoBean = presupuestoBean;
    }

    public PresupuestoUniOrgBean getPresupuestoUniOrgBean() {
        if (presupuestoUniOrgBean == null) {
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        }
        return presupuestoUniOrgBean;
    }

    public void setPresupuestoUniOrgBean(PresupuestoUniOrgBean presupuestoUniOrgBean) {
        this.presupuestoUniOrgBean = presupuestoUniOrgBean;
    }

    public PlanOperativoBean getPlanOperativo() {
        if (planOperativo == null) {
            planOperativo = new PlanOperativoBean();
        }
        return planOperativo;
    }

    public void setPlanOperativo(PlanOperativoBean planOperativo) {
        this.planOperativo = planOperativo;
    }

    public List<PlanOperativoBean> getListaPlanOperativo() {
        if (listaPlanOperativo == null) {
            listaPlanOperativo = new ArrayList<>();
        }
        return listaPlanOperativo;
    }

    public void setListaPlanOperativo(List<PlanOperativoBean> listaPlanOperativo) {
        this.listaPlanOperativo = listaPlanOperativo;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public List<UnidadOrganicaBean> getListaUniOrg() {
        if (listaUniOrg == null) {
            listaUniOrg = new ArrayList<>();
        }
        return listaUniOrg;
    }

    public void setListaUniOrg(List<UnidadOrganicaBean> listaUniOrg) {
        this.listaUniOrg = listaUniOrg;
    }

    public ObjOperativoBean getObjOperativo() {
        if (objOperativo == null) {
            objOperativo = new ObjOperativoBean();
        }
        return objOperativo;
    }

    public void setObjOperativo(ObjOperativoBean objOperativo) {
        this.objOperativo = objOperativo;
    }

    public List<ObjOperativoBean> getListaObjetivoOperativo() {
        if (listaObjetivoOperativo == null) {
            listaObjetivoOperativo = new ArrayList<>();
        }
        return listaObjetivoOperativo;
    }

    public void setListaObjetivoOperativo(List<ObjOperativoBean> listaObjetivoOperativo) {
        this.listaObjetivoOperativo = listaObjetivoOperativo;
    }

    public List<PlanOperativoBean> getListaPlanObjetivo() {
        if (listaPlanObjetivo == null) {
            listaPlanObjetivo = new ArrayList<>();
        }
        return listaPlanObjetivo;
    }

    public void setListaPlanObjetivo(List<PlanOperativoBean> listaPlanObjetivo) {
        if (listaPlanObjetivo == null) {
            listaPlanObjetivo = new ArrayList<>();
        }
        this.listaPlanObjetivo = listaPlanObjetivo;
    }

    public List<CodigoBean> getListaActividadCodigo() {
        return listaActividadCodigo;
    }

    public void setListaActividadCodigo(List<CodigoBean> listaActividadCodigo) {
        this.listaActividadCodigo = listaActividadCodigo;
    }

    public List<CodigoBean> getListaUniMedCodigo() {
        return listaUniMedCodigo;
    }

    public void setListaUniMedCodigo(List<CodigoBean> listaUniMedCodigo) {
        this.listaUniMedCodigo = listaUniMedCodigo;
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

    public AnioBean getAnioBean() {
        if (anioBean == null) {
            anioBean = new AnioBean();
        }
        return anioBean;
    }

    public void setAnioBean(AnioBean anioBean) {
        this.anioBean = anioBean;
    }

    public List<AnioBean> getListaAnioBean() {
        if (listaAnioBean == null) {
            listaAnioBean = new ArrayList<>();
        }
        return listaAnioBean;
    }

    public void setListaAnioBean(List<AnioBean> listaAnioBean) {
        this.listaAnioBean = listaAnioBean;
    }

    public PlanOperativoBean getPlanOperativoFiltroBean() {
        if (planOperativoFiltroBean == null) {
            planOperativoFiltroBean = new PlanOperativoBean();
        }
        return planOperativoFiltroBean;
    }

    public void setPlanOperativoFiltroBean(PlanOperativoBean planOperativoFiltroBean) {
        this.planOperativoFiltroBean = planOperativoFiltroBean;
    }

    public List<PlanOperativoBean> getListaplanOperativoFiltroBean() {
        if (listaplanOperativoFiltroBean == null) {
            listaplanOperativoFiltroBean = new ArrayList<>();
        }
        return listaplanOperativoFiltroBean;
    }

    public void setListaplanOperativoFiltroBean(List<PlanOperativoBean> listaplanOperativoFiltroBean) {
        this.listaplanOperativoFiltroBean = listaplanOperativoFiltroBean;
    }

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicoDetBean() {
        if (objetivoEstrategicoDetBean == null) {
            objetivoEstrategicoDetBean = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicoDetBean;
    }

    public void setObjetivoEstrategicoDetBean(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) {
        this.objetivoEstrategicoDetBean = objetivoEstrategicoDetBean;
    }

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicoDetFiltroBean() {
        if (objetivoEstrategicoDetFiltroBean == null) {
            objetivoEstrategicoDetFiltroBean = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicoDetFiltroBean;
    }

    public void setObjetivoEstrategicoDetFiltroBean(ObjetivoEstrategicoDetBean objetivoEstrategicoDetFiltroBean) {
        this.objetivoEstrategicoDetFiltroBean = objetivoEstrategicoDetFiltroBean;
    }

    public List<ObjetivoEstrategicoDetBean> getListaObjetivoEstrategicoDetBean() {
        if (listaObjetivoEstrategicoDetBean == null) {
            listaObjetivoEstrategicoDetBean = new ArrayList<>();
        }
        return listaObjetivoEstrategicoDetBean;
    }

    public void setListaObjetivoEstrategicoDetBean(List<ObjetivoEstrategicoDetBean> listaObjetivoEstrategicoDetBean) {
        this.listaObjetivoEstrategicoDetBean = listaObjetivoEstrategicoDetBean;
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

    public List<ObjetivoEstrategicoDetBean> getListaSubObjetivoEstrategicoDetBean() {
        if (listaSubObjetivoEstrategicoDetBean == null) {
            listaSubObjetivoEstrategicoDetBean = new ArrayList<>();
        }
        return listaSubObjetivoEstrategicoDetBean;
    }

    public void setListaSubObjetivoEstrategicoDetBean(List<ObjetivoEstrategicoDetBean> listaSubObjetivoEstrategicoDetBean) {
        this.listaSubObjetivoEstrategicoDetBean = listaSubObjetivoEstrategicoDetBean;
    }

    public ObjOperativoBean getObjOperativoFiltro() {
        if (objOperativoFiltro == null) {
            objOperativoFiltro = new ObjOperativoBean();
        }
        return objOperativoFiltro;
    }

    public void setObjOperativoFiltro(ObjOperativoBean objOperativoFiltro) {
        this.objOperativoFiltro = objOperativoFiltro;
    }

    public List<ObjOperativoBean> getListaObjetivoOperativoFiltro() {
        if (listaObjetivoOperativoFiltro == null) {
            listaObjetivoOperativoFiltro = new ArrayList<>();
        }
        return listaObjetivoOperativoFiltro;
    }

    public void setListaObjetivoOperativoFiltro(List<ObjOperativoBean> listaObjetivoOperativoFiltro) {
        this.listaObjetivoOperativoFiltro = listaObjetivoOperativoFiltro;
    }

    public PersonaBean getPersonaBean() {
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public List<PersonaBean> getListaPersonaBean() {
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
    }

    public PlanEstrategicoBean getPlanEstrategicoBean() {
        if (planEstrategicoBean == null) {
            planEstrategicoBean = new PlanEstrategicoBean();
        }
        return planEstrategicoBean;
    }

    public void setPlanEstrategicoBean(PlanEstrategicoBean planEstrategicoBean) {
        this.planEstrategicoBean = planEstrategicoBean;
    }

    public List<PlanEstrategicoBean> getListaPlanEstrategicoBean() {
        if (listaPlanEstrategicoBean == null) {
            listaPlanEstrategicoBean = new ArrayList<>();
        }
        return listaPlanEstrategicoBean;
    }

    public void setListaPlanEstrategicoBean(List<PlanEstrategicoBean> listaPlanEstrategicoBean) {
        this.listaPlanEstrategicoBean = listaPlanEstrategicoBean;
    }

    public LineaEstrategicaBean getLineaEstrategicaBean() {
        if (lineaEstrategicaBean == null) {
            lineaEstrategicaBean = new LineaEstrategicaBean();
        }
        return lineaEstrategicaBean;
    }

    public void setLineaEstrategicaBean(LineaEstrategicaBean lineaEstrategicaBean) {
        this.lineaEstrategicaBean = lineaEstrategicaBean;
    }

    public List<LineaEstrategicaBean> getListaLineaEstrategicaBean() {
        if (listaLineaEstrategicaBean == null) {
            listaLineaEstrategicaBean = new ArrayList<>();
        }
        return listaLineaEstrategicaBean;
    }

    public void setListaLineaEstrategicaBean(List<LineaEstrategicaBean> listaLineaEstrategicaBean) {
        this.listaLineaEstrategicaBean = listaLineaEstrategicaBean;
    }

    public ObjetivoEstrategicaBean getObjetivoEstrategicaBean() {
        if (objetivoEstrategicaBean == null) {
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicaBean;
    }

    public void setObjetivoEstrategicaBean(ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public List<ObjetivoEstrategicaBean> getListaObjetivoEstrategicaBean() {
        if (listaObjetivoEstrategicaBean == null) {
            listaObjetivoEstrategicaBean = new ArrayList<>();
        }
        return listaObjetivoEstrategicaBean;
    }

    public void setListaObjetivoEstrategicaBean(List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean) {
        this.listaObjetivoEstrategicaBean = listaObjetivoEstrategicaBean;
    }

    public List<CodigoBean> getListaTipoTarea() {
        if (listaTipoTarea == null) {
            listaTipoTarea = new ArrayList<>();
        }
        return listaTipoTarea;
    }

    public void setListaTipoTarea(List<CodigoBean> listaTipoTarea) {
        this.listaTipoTarea = listaTipoTarea;
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

    public PlanContableBean getPlanFiltroContableBean() {
        if (planFiltroContableBean == null) {
            planFiltroContableBean = new PlanContableBean();
        }
        return planFiltroContableBean;
    }

    public void setPlanFiltroContableBean(PlanContableBean planFiltroContableBean) {
        this.planFiltroContableBean = planFiltroContableBean;
    }

    public List<PlanContableBean> getListaPlanContableBean() {
        if (listaPlanContableBean == null) {
            listaPlanContableBean = new ArrayList<>();
        }
        return listaPlanContableBean;
    }

    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
        this.listaPlanContableBean = listaPlanContableBean;
    }

    public List<CodigoBean> getListaTipoGrupoCuenta() {
        if (listaTipoGrupoCuenta == null) {
            listaTipoGrupoCuenta = new ArrayList<>();
        }
        return listaTipoGrupoCuenta;
    }

    public void setListaTipoGrupoCuenta(List<CodigoBean> listaTipoGrupoCuenta) {
        this.listaTipoGrupoCuenta = listaTipoGrupoCuenta;
    }

    public List<CodigoBean> getListaTipoCuenta() {
        if (listaTipoCuenta == null) {
            listaTipoCuenta = new ArrayList<>();
        }
        return listaTipoCuenta;
    }

    public void setListaTipoCuenta(List<CodigoBean> listaTipoCuenta) {
        this.listaTipoCuenta = listaTipoCuenta;
    }

    public DetActividadBean getDetActividadBean() {
        if (detActividadBean == null) {
            detActividadBean = new DetActividadBean();
        }
        return detActividadBean;
    }

    public void setDetActividadBean(DetActividadBean detActividadBean) {
        this.detActividadBean = detActividadBean;
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

    public Map<String, Integer> getListaMesesExpMap() {
        return listaMesesExpMap;
    }

    public void setListaMesesExpMap(Map<String, Integer> listaMesesExpMap) {
        this.listaMesesExpMap = listaMesesExpMap;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public PlanContableBean getPlanPresupuestoBean() {
        if (planPresupuestoBean == null) {
            planPresupuestoBean = new PlanContableBean();
        }
        return planPresupuestoBean;
    }

    public void setPlanPresupuestoBean(PlanContableBean planPresupuestoBean) {
        this.planPresupuestoBean = planPresupuestoBean;
    }

    public List<PlanContableBean> getListaPlanPresupuestoBean() {
        if (listaPlanPresupuestoBean == null) {
            listaPlanPresupuestoBean = new ArrayList<>();
        }
        return listaPlanPresupuestoBean;
    }

    public void setListaPlanPresupuestoBean(List<PlanContableBean> listaPlanPresupuestoBean) {
        this.listaPlanPresupuestoBean = listaPlanPresupuestoBean;
    }

    public BarChartModel getAnimatedModel2() {
        if (animatedModel2 == null) {
            animatedModel2 = new BarChartModel();
        }
        return animatedModel2;
    }

    public void setAnimatedModel2(BarChartModel animatedModel2) {
        this.animatedModel2 = animatedModel2;
    }

    public PieChartModel getPieModel() {
        if (pieModel == null) {
            pieModel = new PieChartModel();
        }
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public BarChartModel getBarModel() {
        if (barModel == null) {
            barModel = new BarChartModel();
        }
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public List<PresupuestoBean> getListPresFiltroBean() {
        if (listPresFiltroBean == null) {
            listPresFiltroBean = new ArrayList<>();
        }
        return listPresFiltroBean;
    }

    public void setListPresFiltroBean(List<PresupuestoBean> listPresFiltroBean) {
        this.listPresFiltroBean = listPresFiltroBean;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getParametro2() {
        return parametro2;
    }

    public void setParametro2(String parametro2) {
        this.parametro2 = parametro2;
    }

    public PresupuestoBean getPresupuestoFiltroUOBean() {
        if (presupuestoFiltroUOBean == null) {
            presupuestoFiltroUOBean = new PresupuestoBean();
        }
        return presupuestoFiltroUOBean;
    }

    public void setPresupuestoFiltroUOBean(PresupuestoBean presupuestoFiltroUOBean) {
        this.presupuestoFiltroUOBean = presupuestoFiltroUOBean;
    }

    public List<PresupuestoBean> getListPresupuestoFiltroUOBean() {
        if (listPresupuestoFiltroUOBean == null) {
            listPresupuestoFiltroUOBean = new ArrayList<>();
        }
        return listPresupuestoFiltroUOBean;
    }

    public void setListPresupuestoFiltroUOBean(List<PresupuestoBean> listPresupuestoFiltroUOBean) {
        this.listPresupuestoFiltroUOBean = listPresupuestoFiltroUOBean;
    }

    public PlanContableBean getPlanContablePresUo() {
        if (planContablePresUo == null) {
            planContablePresUo = new PlanContableBean();
        }
        return planContablePresUo;
    }

    public void setPlanContablePresUo(PlanContableBean planContablePresUo) {
        this.planContablePresUo = planContablePresUo;
    }

    public List<PlanContableBean> getListPlanContablePresUo() {
        if (listPlanContablePresUo == null) {
            listPlanContablePresUo = new ArrayList<>();
        }
        return listPlanContablePresUo;
    }

    public void setListPlanContablePresUo(List<PlanContableBean> listPlanContablePresUo) {
        this.listPlanContablePresUo = listPlanContablePresUo;
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

    public List<ConceptoBean> getListConceptoBean() {
        if (listConceptoBean == null) {
            listConceptoBean = new ArrayList<>();
        }
        return listConceptoBean;
    }

    public void setListConceptoBean(List<ConceptoBean> listConceptoBean) {
        this.listConceptoBean = listConceptoBean;
    }

    public List<PresupuestoUniOrgBean> getListPresUoFiltroBean() {
        if (listPresUoFiltroBean == null) {
            listPresUoFiltroBean = new ArrayList<>();
        }
        return listPresUoFiltroBean;
    }

    public void setListPresUoFiltroBean(List<PresupuestoUniOrgBean> listPresUoFiltroBean) {
        this.listPresUoFiltroBean = listPresUoFiltroBean;
    }

    public BarChartModel getBarModel2() {
        if (barModel2 == null) {
            barModel2 = new BarChartModel();
        }
        return barModel2;
    }

    public void setBarModel2(BarChartModel barModel2) {
        this.barModel2 = barModel2;
    }

    public DetActividadBean getDetActivadadPresBean() {
        if (detActivadadPresBean == null) {
            detActivadadPresBean = new DetActividadBean();
        }
        return detActivadadPresBean;
    }

    public void setDetActivadadPresBean(DetActividadBean detActivadadPresBean) {
        this.detActivadadPresBean = detActivadadPresBean;
    }

    public List<DetActividadBean> getListDetActivadadPresBean() {
        if (listDetActivadadPresBean == null) {
            listDetActivadadPresBean = new ArrayList<>();
        }
        return listDetActivadadPresBean;
    }

    public void setListDetActivadadPresBean(List<DetActividadBean> listDetActivadadPresBean) {
        this.listDetActivadadPresBean = listDetActivadadPresBean;
    }

    public ActividadCrBean getActividadCrBean() {
        if (actividadCrBean == null) {
            actividadCrBean = new ActividadCrBean();
        }
        return actividadCrBean;
    }

    public void setActividadCrBean(ActividadCrBean actividadCrBean) {
        this.actividadCrBean = actividadCrBean;
    }

    public List<ActividadCrBean> getListActividadCrBean() {
        if (listActividadCrBean == null) {
            listActividadCrBean = new ArrayList<>();
        }
        return listActividadCrBean;
    }

    public void setListActividadCrBean(List<ActividadCrBean> listActividadCrBean) {
        this.listActividadCrBean = listActividadCrBean;
    }

    public List<CodigoBean> getListaCodigoValor() {
        if (listaCodigoValor == null) {
            listaCodigoValor = new ArrayList<>();
        }
        return listaCodigoValor;
    }

    public void setListaCodigoValor(List<CodigoBean> listaCodigoValor) {
        this.listaCodigoValor = listaCodigoValor;
    }

    public DetActividadBean getFiltroDetActividadBean() {
        if (filtroDetActividadBean == null) {
            filtroDetActividadBean = new DetActividadBean();
        }
        return filtroDetActividadBean;
    }

    public void setFiltroDetActividadBean(DetActividadBean filtroDetActividadBean) {
        this.filtroDetActividadBean = filtroDetActividadBean;
    }

    public List<DetActividadBean> getListFiltroDetActividadBean() {
        if (listFiltroDetActividadBean == null) {
            listFiltroDetActividadBean = new ArrayList<>();
        }
        return listFiltroDetActividadBean;
    }

    public void setListFiltroDetActividadBean(List<DetActividadBean> listFiltroDetActividadBean) {
        this.listFiltroDetActividadBean = listFiltroDetActividadBean;
    }

    public Integer getParametro3() {
        return parametro3;
    }

    public void setParametro3(Integer parametro3) {
        this.parametro3 = parametro3;
    }

    public Integer getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Integer presupuesto) {
        this.presupuesto = presupuesto;
    }

    public DualListModel<CentroResponsabilidadBean> getDualDetActCr() {
        if (dualDetActCr == null) {
            dualDetActCr = new DualListModel<>();
        }
        return dualDetActCr;
    }

    public void setDualDetActCr(DualListModel<CentroResponsabilidadBean> dualDetActCr) {
        this.dualDetActCr = dualDetActCr;
    }

    public List<CentroResponsabilidadBean> getListCR() {
        if (listCR == null) {
            listCR = new ArrayList<>();
        }
        return listCR;
    }

    public void setListCR(List<CentroResponsabilidadBean> listCR) {
        this.listCR = listCR;
    }

    public List<CentroResponsabilidadBean> getListCRB() {
        if (listCRB == null) {
            listCRB = new ArrayList<>();
        }
        return listCRB;
    }

    public void setListCRB(List<CentroResponsabilidadBean> listCRB) {
        this.listCRB = listCRB;
    }

    public DualListModel<Object> getDualDetActCr2() {
        if (dualDetActCr2 == null) {
            dualDetActCr2 = new DualListModel<>();
        }
        return dualDetActCr2;
    }

    public void setDualDetActCr2(DualListModel<Object> dualDetActCr2) {
        this.dualDetActCr2 = dualDetActCr2;
    }

    public List<ActividadCrBean> getListActividadCrBean2() {
        if (listActividadCrBean2 == null) {
            listActividadCrBean2 = new ArrayList<>();
        }
        return listActividadCrBean2;
    }

    public void setListActividadCrBean2(List<ActividadCrBean> listActividadCrBean2) {
        this.listActividadCrBean2 = listActividadCrBean2;
    }

    public Tree getRoot() {
        if (root == null) {
            root = new Tree();
        }
        return root;
    }

    public void setRoot(Tree root) {
        this.root = root;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
    }

    public List<PresupuestoBean> getListPresupuestoExecBean() {
        if (listPresupuestoExecBean == null) {
            listPresupuestoExecBean = new ArrayList<>();
        }
        return listPresupuestoExecBean;
    }

    public void setListPresupuestoExecBean(List<PresupuestoBean> listPresupuestoExecBean) {
        this.listPresupuestoExecBean = listPresupuestoExecBean;
    }

    public List<ActividadCrBean> getListActividadCrCuentaBean() {
        if (listActividadCrCuentaBean == null) {
            listActividadCrCuentaBean = new ArrayList<>();
        }
        return listActividadCrCuentaBean;
    }

    public void setListActividadCrCuentaBean(List<ActividadCrBean> listActividadCrCuentaBean) {
        this.listActividadCrCuentaBean = listActividadCrCuentaBean;
    }

    public Boolean getMostrarSub() {
        return mostrarSub;
    }

    public void setMostrarSub(Boolean mostrarSub) {
        this.mostrarSub = mostrarSub;
    }

    public String getIdCr() {
        return idCr;
    }

    public void setIdCr(String idCr) {
        this.idCr = idCr;
    }

    public Integer getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Integer idActividad) {
        this.idActividad = idActividad;
    }

    public List<ObjOperativoBean> getListaObjetivoActOperativo() {
        if (listaObjetivoActOperativo == null) {
            listaObjetivoActOperativo = new ArrayList<>();
        }
        return listaObjetivoActOperativo;
    }

    public void setListaObjetivoActOperativo(List<ObjOperativoBean> listaObjetivoActOperativo) {
        this.listaObjetivoActOperativo = listaObjetivoActOperativo;
    }

    public Boolean getMostrarActv() {
        return mostrarActv;
    }

    public void setMostrarActv(Boolean mostrarActv) {
        this.mostrarActv = mostrarActv;
    }

    public ActividadBean getActividadFiltroBean() {
        if (actividadFiltroBean == null) {
            actividadFiltroBean = new ActividadBean();
        }
        return actividadFiltroBean;
    }

    public void setActividadFiltroBean(ActividadBean actividadFiltroBean) {
        this.actividadFiltroBean = actividadFiltroBean;
    }

    public List<PlanContableBean> getListPlanContableBean() {
        if (listPlanContableBean == null) {
            listPlanContableBean = new ArrayList<>();
        }
        return listPlanContableBean;
    }

    public void setListPlanContableBean(List<PlanContableBean> listPlanContableBean) {
        this.listPlanContableBean = listPlanContableBean;
    }

    public PlanContableBean getPlanContableFiltroBean() {
        if (planContableFiltroBean == null) {
            planContableFiltroBean = new PlanContableBean();
        }
        return planContableFiltroBean;
    }

    public void setPlanContableFiltroBean(PlanContableBean planContableFiltroBean) {
        this.planContableFiltroBean = planContableFiltroBean;
    }

    public String getNomActividad() {
        return nomActividad;
    }

    public void setNomActividad(String nomActividad) {
        this.nomActividad = nomActividad;
    }

    public String getNomCr() {
        return nomCr;
    }

    public void setNomCr(String nomCr) {
        this.nomCr = nomCr;
    }

    public List<DetActividadBean> getListaDetActMes() {
        return listaDetActMes;
    }

    public void setListaDetActMes(List<DetActividadBean> listaDetActMes) {
        this.listaDetActMes = listaDetActMes;
    }

    public DetActividadBean getDetActMes() {
        return detActMes;
    }

    public void setDetActMes(DetActividadBean detActMes) {
        this.detActMes = detActMes;
    }

    public Integer getImporteSubActividad() {
        return importeSubActividad;
    }

    public void setImporteSubActividad(Integer importeSubActividad) {
        this.importeSubActividad = importeSubActividad;
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

    public CentroResponsabilidadBean getCentroResponsabilidadBean() {
        if (centroResponsabilidadBean == null) {
            centroResponsabilidadBean = new CentroResponsabilidadBean();
        }
        return centroResponsabilidadBean;
    }

    public void setCentroResponsabilidadBean(CentroResponsabilidadBean centroResponsabilidadBean) {
        this.centroResponsabilidadBean = centroResponsabilidadBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public List<DetActividadBean> getListaDetActividadPresupuestoBean() {
        if (listaDetActividadPresupuestoBean == null) {
            listaDetActividadPresupuestoBean = new ArrayList<>();
        }
        return listaDetActividadPresupuestoBean;
    }

    public void setListaDetActividadPresupuestoBean(List<DetActividadBean> listaDetActividadPresupuestoBean) {
        this.listaDetActividadPresupuestoBean = listaDetActividadPresupuestoBean;
    }

    public List<PlanContableBean> getListPlan() {
        if (listPlan == null) {
            listPlan = new ArrayList<>();
        }
        return listPlan;
    }

    public void setListPlan(List<PlanContableBean> listPlan) {
        this.listPlan = listPlan;
    }

    public List<ActividadCrBean> getListCrAct() {
        if (listCrAct == null) {
            listCrAct = new ArrayList<>();
        }
        return listCrAct;
    }

    public void setListCrAct(List<ActividadCrBean> listCrAct) {
        this.listCrAct = listCrAct;
    }

    public Boolean getPanelPlan() {
        return panelPlan;
    }

    public void setPanelPlan(Boolean panelPlan) {
        this.panelPlan = panelPlan;
    }

    public Boolean getPanelLin() {
        return panelLin;
    }

    public void setPanelLin(Boolean panelLin) {
        this.panelLin = panelLin;
    }

    public Boolean getPanelObj() {
        return panelObj;
    }

    public void setPanelObj(Boolean panelObj) {
        this.panelObj = panelObj;
    }

    public List<ActividadCrBean> getListCrActividad() {
        if (listCrActividad == null) {
            listCrActividad = new ArrayList<>();
        }
        return listCrActividad;
    }

    public void setListCrActividad(List<ActividadCrBean> listCrActividad) {
        this.listCrActividad = listCrActividad;
    }

    public List<ActividadCrBean> getListaCrA() {
        if (listaCrA == null) {
            listaCrA = new ArrayList<>();
        }
        return listaCrA;
    }

    public void setListaCrA(List<ActividadCrBean> listaCrA) {
        this.listaCrA = listaCrA;
    }

    public ActividadCrBean getCrA() {
        if (crA == null) {
            crA = new ActividadCrBean();
        }
        return crA;
    }

    public void setCrA(ActividadCrBean crA) {
        this.crA = crA;
    }

    public Integer getExec() {
        return exec;
    }

    public void setExec(Integer exec) {
        this.exec = exec;
    }

    public List<Integer> getListaAnios() {
        if (listaAnios == null) {
            listaAnios = new ArrayList<>();
        }
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public IndicadorBean getIndicadorFiltroBean() {
        if (indicadorFiltroBean == null) {
            indicadorFiltroBean = new IndicadorBean();
        }
        return indicadorFiltroBean;
    }

    public void setIndicadorFiltroBean(IndicadorBean indicadorFiltroBean) {
        this.indicadorFiltroBean = indicadorFiltroBean;
    }

    public List<IndicadorBean> getListaIndicadorBean() {
        if (listaIndicadorBean == null) {
            listaIndicadorBean = new ArrayList<>();
        }
        return listaIndicadorBean;
    }

    public void setListaIndicadorBean(List<IndicadorBean> listaIndicadorBean) {
        this.listaIndicadorBean = listaIndicadorBean;
    }

    public IndicadorBean getIndicadorBean() {
        if (indicadorBean == null) {
            indicadorBean = new IndicadorBean();
        }
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
    }

    public List<CodigoBean> getListaTipoUsoIndicador() {
        if (listaTipoUsoIndicador == null) {
            listaTipoUsoIndicador = new ArrayList<>();
        }
        return listaTipoUsoIndicador;
    }

    public void setListaTipoUsoIndicador(List<CodigoBean> listaTipoUsoIndicador) {
        this.listaTipoUsoIndicador = listaTipoUsoIndicador;
    }

    public ActividadBean getActividadFiltro() {
        if (actividadFiltro == null) {
            actividadFiltro = new ActividadBean();
        }
        return actividadFiltro;
    }

    public void setActividadFiltro(ActividadBean actividadFiltro) {
        this.actividadFiltro = actividadFiltro;
    }

    public List<CodigoBean> getListaTipoValor() {
        if (listaTipoValor == null) {
            listaTipoValor = new ArrayList<>();
        }
        return listaTipoValor;
    }

    public void setListaTipoValor(List<CodigoBean> listaTipoValor) {
        this.listaTipoValor = listaTipoValor;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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

    public List<UnidadOrganicaBean> getListaUnidadOrganica() {
        if (listaUnidadOrganica == null) {
            listaUnidadOrganica = new ArrayList<>();
        }
        return listaUnidadOrganica;
    }

    public void setListaUnidadOrganica(List<UnidadOrganicaBean> listaUnidadOrganica) {
        this.listaUnidadOrganica = listaUnidadOrganica;
    }

    public Boolean getPanelRes() {
        return panelRes;
    }

    public void setPanelRes(Boolean panelRes) {
        this.panelRes = panelRes;
    }

    public List<ActividadCrBean> getListaPresupuesto() {
        if (listaPresupuesto == null) {
            listaPresupuesto = new ArrayList<>();
        }
        return listaPresupuesto;
    }

    public void setListaPresupuesto(List<ActividadCrBean> listaPresupuesto) {
        this.listaPresupuesto = listaPresupuesto;
    }

    //============================Plan Operativo==============================//
    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int inicio = MaristaConstantes.ANO_INI_DEFAULT_COLE;
            int fin = miCalendario.get(Calendar.YEAR) + 5;
            listaAnios = new ArrayList<>();
            for (Integer i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPlanOperativo() {
        try {
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            listaPlanOperativo = planOperativoService.obtenerTodos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorUniNeg() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            listaPlanOperativo = planOperativoService.obtenerPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltro() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            if (planOperativoFiltroBean.getUnidadOrganicaBean().getIdUniOrg() != null && !planOperativoFiltroBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("")) {
                planOperativoFiltroBean.getUnidadOrganicaBean().setIdUniOrg(planOperativoFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
            }
            if (planOperativoFiltroBean.getAnio() != null && !planOperativoFiltroBean.getAnio().toString().equals("")) {
                planOperativoFiltroBean.setAnio(planOperativoFiltroBean.getAnio());
            }
            if (planOperativoFiltroBean.getNombre() != null && !planOperativoFiltroBean.getNombre().equals("")) {
                planOperativoFiltroBean.setNombre(planOperativoFiltroBean.getNombre().toUpperCase().trim());
            }
            listaPlanOperativo = planOperativoService.obtenerPorFiltro(planOperativoFiltroBean);
            if (listaPlanOperativo.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarPlanOperativo() {
        try {
            planOperativo = new PlanOperativoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarPlanOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                planOperativo.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                planOperativo.setCreaPor(beanUsuarioSesion.getUsuario());
                planOperativoService.insertarPlanOperativo(planOperativo);
                listaPlanOperativo = planOperativoService.obtenerPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                obtenerCodigoPlanOperativo();
                limpiarPlanOperativo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPlanOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                planOperativo.setModiPor(beanUsuarioSesion.getUsuario());
                planOperativoService.modificarPlanOperativo(planOperativo);
                limpiarPlanOperativo();
                obtenerPorFiltro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPlanOperativo() {
        try {
//            if (planOperativo.getUnidadNegocioBean() == null && planOperativo.getUnidadOrganicaBean() == null) {
//                insertarPlanOperativo();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            if (planOperativo.getIdPlanOperativo() == null) {
                insertarPlanOperativo();
            } else {
                modificarPlanOperativo();
            }
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCodigoPlanOperativo() {
        try {
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            String baseCodigo = "PO - ";
            if (planOperativo == null) {
                planOperativo = new PlanOperativoBean();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                planOperativo.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            }
            String codigo = planOperativoService.obtenerCodigoPlanOperativo(planOperativo.getUnidadNegocioBean().getUniNeg());
            Integer cod = Integer.parseInt(codigo) + 1;
            String codigo2 = String.format("%03d", cod);
//            planOperativo.setCodigoPlanOperativo(baseCodigo.concat(codigo2));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            planOperativo = (PlanOperativoBean) object;
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            planOperativoService.obtenerPlanOperativoPorId(planOperativo);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarPlanOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
                planOperativoService.eliminarPlanOperativoBean(planOperativo);
                obtenerPorFiltro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPlanOpeId(Object object) {
        try {
            planOperativo = (PlanOperativoBean) object;
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            planOperativo = planOperativoService.obtenerPlanOperativoPorId(planOperativo);
            System.out.println(planOperativo.getUnidadOrganicaBean().getIdUniOrg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPlanOpeId2(Object object) {
        try {
            planOperativo = (PlanOperativoBean) object;
            PlanOperativoService planOperativoService = BeanFactory.getPlanOperativoService();
            planOperativo = planOperativoService.obtenerPlanOperativoPorId(planOperativo);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //===========================Fin de Plan Operativo=======================================//
    //================================Objetivo Operativo========================================//
    public void obtenerObjOperativo() {
        try {
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            listaObjetivoOperativo = objetivoOperativoService.obtenerTodos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarObjeOperativo() {
        try {
            objOperativoBean = new ObjOperativoBean();
            objOperativoBean.setPlanOperativoBean(planOperativoBean);
            obtenerCodObjOperativo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarObjOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Integer var = 0;
            if (pagina == null) {
                ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
                objOperativoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                objOperativoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                objetivoOperativoService.insertarObjetivoOperativo(objOperativoBean);
                var = objetivoOperativoService.obtenerMaxIdObjOperativo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listaObjetivoOperativo = objetivoOperativoService.obtenerPorMaxId(var, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaObjetivoOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaObjetivoActOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                obtenerCodObjOperativo();
                limpiarObjeOperativo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarObjOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Integer var = 0;
            if (pagina == null) {
                ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
                objOperativoBean.setModiPor(beanUsuarioSesion.getUsuario());
                objetivoOperativoService.modificarObjetivoOperativo(objOperativoBean);
                var = objetivoOperativoService.obtenerMaxIdObjOperativo(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listaObjetivoOperativo = objetivoOperativoService.obtenerPorMaxId(var, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaObjetivoOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaObjetivoActOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                obtenerCodObjOperativo();
                limpiarObjeOperativo();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardar() {
        try {
            if (objOperativoBean.getIdObjOperativo() == null) {
                insertarObjOperativo();
            } else {
                modificarObjOperativo();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCodObjOperativo() {
        try {
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            String baseCodigo = "OO-";
            if (objOperativoBean == null) {
                objOperativoBean = new ObjOperativoBean();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objOperativoBean.getPlanOperativoBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            }
            Integer codigo = objetivoOperativoService.obtenerUltimoCodigo(objOperativoBean.getPlanOperativoBean().getUnidadNegocioBean().getUniNeg());
            Integer cod = codigo + 1;
            String codigo2 = String.format("%03d", cod);
            objOperativoBean.setCodigo(baseCodigo.concat(codigo2));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerObjEstDet() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            if (objetivoEstrategicoDetFiltroBean.getResponsable() != null && !objetivoEstrategicoDetFiltroBean.getResponsable().equals("")) {
                objetivoEstrategicoDetFiltroBean.setResponsable(objetivoEstrategicoDetFiltroBean.getResponsable());
            }
            if (objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().setNombre(objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre());
            }
            if (objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().setNombre(objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre());
            }
            if (objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().setNombre(objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre());
            }
            listaObjetivoEstrategicoDetBean = objEstrategicoDetService.obtenerEstDetFiltro(objetivoEstrategicoDetFiltroBean);
            if (listaObjetivoEstrategicoDetBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "NO HAY DATOS"));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerObjEstrategicos(Object object) {
        try {
            ObjetivoEstrategicoDetBean det = (ObjetivoEstrategicoDetBean) object;
            objOperativoBean.setObjetivoEstrategicoDetBean(det);
            objOperativoBean.getObjetivoEstrategicoDetBean().getPlanEstrategicoBean().setIdPlanEstrategico(det.getPlanEstrategicoBean().getIdPlanEstrategico());
            objOperativoBean.getObjetivoEstrategicoDetBean().getLineaEstrategicaBean().setIdLinea(det.getLineaEstrategicaBean().getIdLinea());
            objOperativoBean.getObjetivoEstrategicoDetBean().getObjetivoEstrategicaBean().setIdObjEstrategico(det.getObjetivoEstrategicaBean().getIdObjEstrategico());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerObjOperativoPorId(Object object) {
        try {
            ObjOperativoBean objOpe = (ObjOperativoBean) object;
            ObjetivoOperativoService objOperativoService = BeanFactory.getObjetivoOperativoService();
            objOperativoBean = objOperativoService.obtenerPorId(objOpe.getIdObjOperativo());
            System.out.println(objOpe.getIdObjOperativo());
            System.out.println(objOpe.getNombre());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerObjActPorId(Object object) {
        try {
            ObjOperativoBean objOpe = (ObjOperativoBean) object;
            ObjetivoOperativoService objOperativoService = BeanFactory.getObjetivoOperativoService();
            objOperativoBean = objOperativoService.obtenerPorId(objOpe.getIdObjOperativo());
            actividadBean.setObjOperativoBean(objOperativoBean);
            actividadBean.getObjOperativoBean().setIdObjOperativo(objOperativoBean.getIdObjOperativo());
            actividadBean.getObjOperativoBean().setLineaEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getLineaEstrategicaBean());
            actividadBean.getObjOperativoBean().setObjetivoEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getObjetivoEstrategicaBean());
            actividadBean.getObjOperativoBean().setPlanEstrategicoBean(objOperativoBean.getObjetivoEstrategicoDetBean().getPlanEstrategicoBean());
            ActividadService actividadService = BeanFactory.getActividadService();
            listActividadBean = actividadService.obtenerPorObjOpe(objOperativoBean.getIdObjOperativo());
            mostrarActv = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarObjOperativo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoOperativoService objOperativoService = BeanFactory.getObjetivoOperativoService();
                objOperativoService.eliminarObjetivoOperativo(objOperativoBean.getIdObjOperativo());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaObjetivoOperativo = objOperativoService.obtenerTodos();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //===============================Actividad=========================================//
    public void obtenerPorFiltroActividad() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            if (objOperativoFiltro.getObjetivoEstrategicoDetBean().getResponsable() != null && !objOperativoFiltro.getObjetivoEstrategicoDetBean().getResponsable().equals("")) {
                objOperativoFiltro.getObjetivoEstrategicoDetBean().setResponsable(objOperativoFiltro.getObjetivoEstrategicoDetBean().getResponsable());
                System.out.println(objOperativoFiltro.getObjetivoEstrategicoDetBean().getResponsable());
            }
            listaObjetivoOperativoFiltro = objetivoOperativoService.obtenerPorFiltroActividad(objOperativoFiltro);
            if (listaObjetivoOperativoFiltro.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "NO HAY DATOS"));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltroActividad2() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            if (objetivoEstrategicoDetFiltroBean.getResponsable() != null && !objetivoEstrategicoDetFiltroBean.getResponsable().equals("")) {
                objetivoEstrategicoDetFiltroBean.setResponsable(objetivoEstrategicoDetFiltroBean.getResponsable());
            }
            if (objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().setNombre(objetivoEstrategicoDetFiltroBean.getObjetivoEstrategicaBean().getNombre());
            }
            if (objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().setNombre(objetivoEstrategicoDetFiltroBean.getLineaEstrategicaBean().getNombre());
            }
            if (objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre() != null && !objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre().equals("")) {
                objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().setNombre(objetivoEstrategicoDetFiltroBean.getPlanEstrategicoBean().getNombre());
            }
            listaObjetivoEstrategicoDetBean = objEstrategicoDetService.obtenerEstDetFiltro(objetivoEstrategicoDetFiltroBean);
            if (listaObjetivoEstrategicoDetBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "NO HAY DATOS"));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerLineaPorPlan(Object object) {
        try {
            PlanEstrategicoBean plan = (PlanEstrategicoBean) object;
            LineaEstrategicaService lineaEstragicaService = BeanFactory.getLineaEstrategicaService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaLineaEstrategicaBean = lineaEstragicaService.obtenerLineaPorPlanEstrategico(plan.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (listaLineaEstrategicaBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
            listaObjetivoEstrategicaBean = new ArrayList<>();
            panelPlan = true;
            panelLin = false;
            panelRes = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerObjPorLinea(Object object) {
        try {
            lineaEstrategicaBean = (LineaEstrategicaBean) object;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoService objEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            listaObjetivoEstrategicaBean = objEstrategicoService.obtenerObjPorLinea(lineaEstrategicaBean.getIdLinea());
            if (listaObjetivoEstrategicaBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
            panelPlan = true;
            panelLin = true;
            panelObj = false;
            panelRes = true;
//            LineaEstrategicaService lineaEstragicaService = BeanFactory.getLineaEstrategicaService();
//            listaLineaEstrategicaBean = lineaEstragicaService.obtenerListaPorId(linea.getIdLinea(),beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarActividad() {
        String pagina = null;
        Integer id = 0;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                System.out.println(actividadBean.getIndicadorBean().getIdIndicador());
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                actividadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                actividadService.insertarActividad(actividadBean);
                id = actividadService.obtenerMaxId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listActividadBean = actividadService.obtenerListaPorId(id, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listActividadBean = actividadService.obtenerPorObjOpe(objOperativoBean.getIdObjOperativo());
                actividadBean = new ActividadBean();
                actividadBean.setObjOperativoBean(objOperativoBean);
                actividadBean.getObjOperativoBean().setIdObjOperativo(objOperativoBean.getIdObjOperativo());
                actividadBean.getObjOperativoBean().setLineaEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getLineaEstrategicaBean());
                actividadBean.getObjOperativoBean().setObjetivoEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getObjetivoEstrategicaBean());
                actividadBean.getObjOperativoBean().setPlanEstrategicoBean(objOperativoBean.getObjetivoEstrategicoDetBean().getPlanEstrategicoBean());
                actividadBean.setIndicadorBean(actividadBean.getIndicadorBean());
//                obtenerCodigoPlanOperativo(); 
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insetarActividad2() {
        String pagina = null;
        try {
            Integer id = 0;
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                actividadBean = new ActividadBean();
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                actividadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                actividadService.insertarActividad(actividadBean);
                id = actividadService.obtenerMaxId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listActividadBean = actividadService.obtenerListaPorId(id, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                obtenerCodigoPlanOperativo(); 
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadBean.setModiPor(beanUsuarioSesion.getUsuario());
                actividadService.actualizarActividad(actividadBean);
//                listActividadBean = actividadService.obtenerListaPorId(actividadBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listActividadBean = actividadService.obtenerPorObjOpe(objOperativoBean.getIdObjOperativo());
                actividadBean = new ActividadBean();

                actividadBean.setObjOperativoBean(objOperativoBean);
                actividadBean.getObjOperativoBean().setIdObjOperativo(objOperativoBean.getIdObjOperativo());
                actividadBean.getObjOperativoBean().setLineaEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getLineaEstrategicaBean());
                actividadBean.getObjOperativoBean().setObjetivoEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getObjetivoEstrategicaBean());
                actividadBean.getObjOperativoBean().setPlanEstrategicoBean(objOperativoBean.getObjetivoEstrategicoDetBean().getPlanEstrategicoBean());
                actividadBean.setIndicadorBean(actividadBean.getIndicadorBean());
//                actividadBean.setIndicadorBean(objOperativoBean.getObjetivoEstrategicoDetBean().getIndicadorBean());

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarActividad() {
        try {
            if (actividadBean.getIdActividad() != null) {
                modificarActividad();
            } else {
                insertarActividad();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerActividadId(Object object) {
        try {
            ActividadBean act = (ActividadBean) object;
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadBean = actividadService.obtenerPorId(act.getIdActividad());
            actividadBean.getObjOperativoBean().setPlanOperativoBean(actividadBean.getObjOperativoBean().getPlanOperativoBean());
            System.out.println(actividadBean.getIdActividad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerActividadId2(Object object) {
        try {
            ActividadBean act = (ActividadBean) object;
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadFiltroBean = actividadService.obtenerPorId(act.getIdActividad());
            actividadFiltroBean.getObjOperativoBean().setPlanOperativoBean(actividadFiltroBean.getObjOperativoBean().getPlanOperativoBean());
            actividadFiltroBean.setIdActividad(actividadFiltroBean.getIdActividad());
            actividadFiltroBean.setNombre(actividadFiltroBean.getNombre());
            actividadFiltroBean.setIngreso(actividadFiltroBean.getIngreso());
            actividadFiltroBean.setEgreso(actividadFiltroBean.getEgreso());
//            detActividadBean.getObjOperativoBean().setIdObjOperativo(actividadBean.getObjOperativoBean().getIdObjOperativo());
            System.out.println(actividadFiltroBean.getIdActividad());
            System.out.println(actividadFiltroBean.getObjOperativoBean().getIdObjOperativo());

            //ListaActividad
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            listaDetActividadBean = detActividadService.obtenerDetaPorID(actividadFiltroBean.getIdActividad());

            //Dual_List_Det_Act
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listCR = centroResponsabilidadService.obtenerCrOutAct(actividadFiltroBean.getIdActividad());
            listCRB = centroResponsabilidadService.obtenerCrInAct(actividadFiltroBean.getIdActividad());
            dualDetActCr = new DualListModel<>(listCR, listCRB);

            //Obtener ImporteXMes
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDetActMes = detActividadService.obtenerMesImporte(actividadFiltroBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            resultado = actividadService.obtenerActExec(actividadFiltroBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Integer obtenerMeses() {
        try {
            listaMesesExpMap.size();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return listaMesesExpMap.size();
    }

    public void obtenerActividadId3(Object object) {
        try {
            ActividadBean act = (ActividadBean) object;
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadBean = actividadService.obtenerPorId(act.getIdActividad());
            actividadBean.getObjOperativoBean().setPlanOperativoBean(actividadBean.getObjOperativoBean().getPlanOperativoBean());
            actividadBean.setIdActividad(actividadBean.getIdActividad());
            actividadBean.setNombre(actividadBean.getNombre());
            actividadBean.setIngreso(actividadBean.getIngreso());
            actividadBean.setEgreso(actividadBean.getEgreso());
//            detActividadBean.getObjOperativoBean().setIdObjOperativo(actividadBean.getObjOperativoBean().getIdObjOperativo());
            System.out.println(actividadBean.getIdActividad());
            System.out.println(actividadBean.getObjOperativoBean().getIdObjOperativo());

            //ListaActividad
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            listaDetActividadBean = detActividadService.obtenerDetaPorID(actividadBean.getIdActividad());

            //Dual_List_Det_Act
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listCR = centroResponsabilidadService.obtenerCrOutAct(actividadBean.getIdActividad());
            listCRB = centroResponsabilidadService.obtenerCrInAct(actividadBean.getIdActividad());
            dualDetActCr = new DualListModel<>(listCR, listCRB);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadService.eliminarActividad(actividadBean.getIdActividad());
//                listActividadBean = actividadService.obtenerTodos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listActividadBean = actividadService.obtenerPorObjOpe(objOperativoBean.getIdObjOperativo());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarActividad() {
        try {
            actividadBean = new ActividadBean();
            listActividadBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void nuevaActividad() {
        try {
            actividadBean = new ActividadBean();
            actividadBean.setObjOperativoBean(objOperativoBean);
            actividadBean.getObjOperativoBean().setIdObjOperativo(objOperativoBean.getIdObjOperativo());
            actividadBean.getObjOperativoBean().setLineaEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getLineaEstrategicaBean());
            actividadBean.getObjOperativoBean().setObjetivoEstrategicaBean(objOperativoBean.getObjetivoEstrategicoDetBean().getObjetivoEstrategicaBean());
            actividadBean.getObjOperativoBean().setPlanEstrategicoBean(objOperativoBean.getObjetivoEstrategicoDetBean().getPlanEstrategicoBean());
//            actividadBean.setIndicadorBean(objOperativoBean.getObjetivoEstrategicoDetBean().getIndicadorBean());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarSubAct(Object object) {
        try {
            actividadBean = (ActividadBean) object;
            System.out.println(actividadBean.getIdActividad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarActividad() {
        try {
            listActividadBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirPdfActividad() {
        ServletOutputStream out = null;
        try {
            if (listActividadBean.size() > 0) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                DetActividadService detActividadService = BeanFactory.getDetActividadService();
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDetActividad.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);

                List<DetActividadRepBean> listaDetARepBean = new ArrayList<>();

                for (ActividadBean actividad : listActividadBean) {
                    DetActividadRepBean deta = new DetActividadRepBean();
                    deta.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    deta.setIdActividad(actividad.getIdActividad());
                    deta.setNombre(actividad.getNombre());
                    deta.setEgreso(actividad.getEgreso());
                    deta.setIngreso(actividad.getIngreso());
                    deta.setResponsable(actividad.getResponsable());
                    deta.setNomOO(actividad.getObjOperativoBean().getNombre());
                    deta.setNomPo(actividad.getObjOperativoBean().getPlanOperativoBean().getNombre());
                    deta.setNomInd(actividad.getIndicadorBean().getNombre());
                    deta.setCreaFecha(actividad.getCreaFechaAc());
                    deta.setCreaHora(actividad.getCreaHoraAc());
                    List<DetActividadBean> listaDetAcBean = new ArrayList<>();
                    listaDetAcBean = detActividadService.obtenerDetaPorID(actividad.getIdActividad());
                    List<DetActividadRepSubBean> listaDetActividadRepSubBean = new ArrayList<>();
                    for (DetActividadBean detaSub : listaDetAcBean) {
                        DetActividadRepSubBean detaSubBean = new DetActividadRepSubBean();
                        detaSubBean.setDescripcion(detaSub.getDescripcion());
                        detaSubBean.setImporte(detaSub.getImporte());
                        detaSubBean.setMes(detaSub.getMes());
                        detaSubBean.setNombreActividad(detaSub.getActividadBean().getNombre());
                        detaSubBean.setIdActividad(detaSub.getActividadBean().getIdActividad());
                        detaSubBean.setCuenta(detaSub.getPlanContableBean().getCuenta());
                        listaDetActividadRepSubBean.add(detaSubBean);
                    }
                    deta.setListaDetalle(listaDetActividadRepSubBean);
                    listaDetARepBean.add(deta);
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetARepBean);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                System.out.println("path: " + absoluteWebPath);
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);
                UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
                return;
            } else {
                if (listActividadBean.isEmpty()) {
                    RequestContext.getCurrentInstance().addCallbackParam("error", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //====================================Detalle de Actividad====================================//
    public void obtenerFiltrolanContable() {
        try {
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
//            listaPlanContableBean = detActividadService.obtenerPlanContable();
//            planFiltroContableBean = new PlanContableBean();
            if (planFiltroContableBean.getCuenta() != null && !planFiltroContableBean.getCuenta().equals(0)) {
                planFiltroContableBean.setFlgNull(false);
                planFiltroContableBean.setCuenta(planFiltroContableBean.getCuenta());
            } else {
                planFiltroContableBean.setFlgNull(true);
            }
            if (planFiltroContableBean.getIdTipoGrupoCta().getIdCodigo() != null && !planFiltroContableBean.getIdTipoGrupoCta().getIdCodigo().equals("")) {
                planFiltroContableBean.setFlgNull(false);
                planFiltroContableBean.getIdTipoGrupoCta().setCodigo(planFiltroContableBean.getIdTipoGrupoCta().getCodigo());
            } else {
                planFiltroContableBean.setFlgNull(true);
            }
            if (planFiltroContableBean.getIdTipoCuenta().getIdCodigo() != null && !planFiltroContableBean.getIdTipoCuenta().getIdCodigo().equals("")) {
                planFiltroContableBean.setFlgNull(false);
                planFiltroContableBean.getIdTipoCuenta().setCodigo(planFiltroContableBean.getIdTipoCuenta().getCodigo());
            } else {
                planFiltroContableBean.setFlgNull(true);
            }
            listaPlanContableBean = detActividadService.obtenerPlanContableFiltro(planFiltroContableBean);
            if (listaPlanContableBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            } else {
                System.out.println(listaPlanContableBean.size());
                System.out.println("Completa");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarDetaActividad() {
        try {
            detActividadBean = new DetActividadBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCuentaFiltro() {
        try {
            planFiltroContableBean = new PlanContableBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuenta(Object object) {
        try {
            planContableBean = (PlanContableBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            planContableBean = detActividadService.obtenerPlanPorId(planContableBean.getCuenta());
            System.out.println(planContableBean.getCuenta());
            insertarDetaActividad();
            modificarActividadCr();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarDetaActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (detActividadBean.getImporte().intValue() != 0) {
                    DetActividadService detActividadService = BeanFactory.getDetActividadService();
                    UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    System.out.println(planOperativoBean.getAnio());
                    obtenerActividadId2(actividadFiltroBean);
                    detActividadBean.getActividadBean().setIdActividad(actividadFiltroBean.getIdActividad());
                    detActividadBean.setIdObjOperativo(actividadFiltroBean.getObjOperativoBean().getIdObjOperativo());
                    detActividadBean.setAnio(planOperativoBean.getAnio());
                    detActividadBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    detActividadBean.setIdUniOrg(actividadFiltroBean.getIdUniOrg());
                    detActividadBean.setMeta(actividadFiltroBean.getMeta());
                    detActividadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    detActividadService.insertarDetActividad(detActividadBean);
//                listaDetActividadBean = detActividadService.obtenerDetaPorID(actividadFiltroBean.getIdActividad()); 
                    Integer cr = Integer.parseInt(idCr);
                    ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                    List<ActividadCrBean> listaCrBean = new ArrayList<>();
                    listaCrBean = actividadCrService.obtenerSubCrCuenta(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), detActividadBean.getPlanContableBean().getCuenta());
                    if (listaCrBean.isEmpty()) {
                        Integer cuenta = detActividadBean.getPlanContableBean().getCuenta();
                        listaCrBean = actividadCrService.obtenerSubCr(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        actividadCrService.insertarActividadCrCuenta(listaCrBean, cuenta);
                    }
                    listaDetActividadBean = detActividadService.obtenerActCR(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    System.out.println(listaDetActividadBean.size());

                    //Obtener Importe
                    Integer dato = 0;
                    for (DetActividadBean deta : listaDetActividadBean) {
                        dato = deta.getImporte().intValue() + dato;
                    }
                    importeSubActividad = dato;
                    anio = planOperativoBean.getAnio();
                    //Obtener ImporteXMes 
                    listaDetActMes = detActividadService.obtenerMesImporte(actividadFiltroBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    //limpiarCuentaFiltro();

                    //Insertando en Presupuesto y PresupuestoUo
                    System.out.println("cuenta" + detActividadBean.getPlanContableBean().getCuenta());
                    Integer cuentaPres = detActividadBean.getPlanContableBean().getCuenta();
                    PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                    presupuestoService.insertarPresupestoPlan(listaDetActividadBean, detActividadBean.getPlanContableBean().getCuenta(), anio, planOperativoBean.getUnidadOrganicaBean().getIdUniOrg());

                    //Actualizando Tablas Presupuesto
                    listCrAct = actividadCrService.obtenerPresupuestoCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listCrActividad = actividadCrService.obtenerGrafoPres(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    limpiarDetActividad();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    limpiarDetActividad();
                    RequestContext.getCurrentInstance().addCallbackParam("error", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void listaMesesForSup() {
        listaMesesExpMap = new LinkedHashMap<>();
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMesesExpMap = Collections.unmodifiableMap(listaMesesExpMap);
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            detActividadBean = new DetActividadBean();
            detActividadBean.setImporte(((DetActividadBean) event.getObject()).getImporte());
            detActividadBean.setMes(((DetActividadBean) event.getObject()).getMes());
            detActividadBean.setDescripcion(((DetActividadBean) event.getObject()).getDescripcion());
            detActividadBean.setIdDetActividad(((DetActividadBean) event.getObject()).getIdDetActividad());
            detActividadService.modificarDetaAct(detActividadBean);
//            listaDetActividadBean = detActividadService.obtenerDetaPorID(actividadFiltroBean.getIdActividad());
            Integer cr = Integer.parseInt(idCr);
            listaDetActividadBean = detActividadService.obtenerActCR(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(listaDetActividadBean.size());
            System.out.println("Cambio Exitoso");
            FacesMessage msg = new FacesMessage("Cambio exitoso: Detalle de Actividad", ((DetActividadBean) event.getObject()).getActividadBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            detActividadBean = new DetActividadBean();

            //Actualizando Lista de Presupuesto ACtividad
            listaDetActMes = detActividadService.obtenerMesImporte(actividadFiltroBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Cambio Cancelado: Detalle de Actividad", ((DetActividadBean) event.getObject()).getActividadBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetaActividad(Object object) {
        try {
            detActividadBean = (DetActividadBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            detActividadBean = detActividadService.obtenerPorId(detActividadBean.getIdDetActividad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarDetActiviadad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                Integer cr = Integer.parseInt(idCr);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                DetActividadService detActividadService = BeanFactory.getDetActividadService();

                //Modificando Presupuesto y PresupuestoUniOrg
                List<DetActividadBean> listaDeta = new ArrayList<>();
                listaDeta = detActividadService.obtenerListaPorId(detActividadBean.getIdDetActividad());
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                presupuestoService.modificarPresupuestoProg(listaDeta);

                //Eliminado en Actividad_CR
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                actividadCrService.eliminarActividadCrXDetAct(listaDeta);

                //Eliminando en DetActividad
                detActividadService.eliminarDetActividad(detActividadBean.getIdDetActividad());
//                listaDetActividadBean = detActividadService.obtenerDetaPorID(actividadBean.getIdActividad());  
                listaDetActividadBean = detActividadService.obtenerActCR(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDetActMes = detActividadService.obtenerMesImporte(actividadFiltroBean.getIdActividad(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println(listaDetActividadBean.size());
                limpiarDetActividad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarDetActividad() {
        try {
            detActividadBean = new DetActividadBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //=================================Presupuesto=======================================//
    public void obtenerFiltroPresupuestoContable() {
        try {
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
//            listaPlanContableBean = detActividadService.obtenerPlanContable();
//            planFiltroContableBean = new PlanContableBean();
            if (planPresupuestoBean.getCuenta() != null && !planPresupuestoBean.getCuenta().equals(0)) {
                planPresupuestoBean.setFlgNull(false);
                planPresupuestoBean.setCuenta(planPresupuestoBean.getCuenta());
            } else {
                planPresupuestoBean.setFlgNull(true);
            }
            if (planPresupuestoBean.getIdTipoGrupoCta().getIdCodigo() != null && !planPresupuestoBean.getIdTipoGrupoCta().getIdCodigo().equals("")) {
                planPresupuestoBean.setFlgNull(false);
                planPresupuestoBean.getIdTipoGrupoCta().setCodigo(planPresupuestoBean.getIdTipoGrupoCta().getCodigo());
            } else {
                planPresupuestoBean.setFlgNull(true);
            }
            if (planPresupuestoBean.getIdTipoCuenta().getIdCodigo() != null && !planPresupuestoBean.getIdTipoCuenta().getIdCodigo().equals("")) {
                planPresupuestoBean.setFlgNull(false);
                planPresupuestoBean.getIdTipoCuenta().setCodigo(planPresupuestoBean.getIdTipoCuenta().getCodigo());
            } else {
                planPresupuestoBean.setFlgNull(true);
            }
            listaPlanPresupuestoBean = detActividadService.obtenerPlanContableFiltro(planPresupuestoBean);
            if (listaPlanPresupuestoBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            } else {
                System.out.println(listaPlanPresupuestoBean.size());
                System.out.println("Completa");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaPlan(Object object) {
        try {
            planPresupuestoBean = (PlanContableBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            planPresupuestoBean = detActividadService.obtenerPlanPorId(planPresupuestoBean.getCuenta());
            insertarPresupuestoPlan();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarPresupuestoPlan() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                Integer pres = 0;
                Integer press = 0;
                DetActividadService detActividadService = BeanFactory.getDetActividadService();
                presupuestoBean = new PresupuestoBean();
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                pres = presupuestoService.obtenerPresExec(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), planPresupuestoBean.getCuenta());
                if (pres == null || pres.equals(0)) {
                    pres = 0;
                    BigDecimal presExecs = new BigDecimal(pres);
                    presupuestoBean.setPresupuestoEjec(presExecs);
                } else {
                    BigDecimal presExec = new BigDecimal(pres);
                    presupuestoBean.setPresupuestoEjec(presExec);
                }
                presupuestoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuestoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                presupuestoBean.setAnio(planOperativoBean.getAnio());
                presupuestoBean.getPlanContableBean().setCuenta(planPresupuestoBean.getCuenta());
                presupuestoService.insertarPresupesto(presupuestoBean);
                listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarPlanPresupuesto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarPlanPresupuesto() {
        try {
            planPresupuestoBean = new PlanContableBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPresPorId(Object object) {
        try {
            presupuestoBean = (PresupuestoBean) object;
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoBean = presupuestoService.obtenerPorId(presupuestoBean.getIdPresupuesto());
            System.out.println(presupuestoBean.getIdPresupuesto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarPresupuesto() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                presupuestoService.eliminarPresupesto(presupuestoBean.getIdPresupuesto());
                listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void onRowEditPres(RowEditEvent event) {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            presupuestoBean = new PresupuestoBean();
            presupuestoBean.setPresupuestoProg(((PresupuestoBean) event.getObject()).getPresupuestoProg());
            presupuestoBean.setPresupuestoTope(((PresupuestoBean) event.getObject()).getPresupuestoTope());
            presupuestoBean.setPresupuestoEjec(((PresupuestoBean) event.getObject()).getPresupuestoEjec());
            presupuestoBean.setModiPor(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuestoBean.setIdPresupuesto(((PresupuestoBean) event.getObject()).getIdPresupuesto());
            presupuestoService.modificarDatosPresupuesto(presupuestoBean);
            listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("Cambio Exitoso");
            FacesMessage msg = new FacesMessage("Cambio exitoso: Presupuesto", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            detActividadBean = new DetActividadBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancelPres(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Cambio Cancelado: Presupuesto", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaPlanAct(Object object) {
        try {
//            presupuestoBean = (PresupuestoBean) object;
            planPresupuestoBean = (PlanContableBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            listFiltroDetActividadBean = detActividadService.obtenerCuentaPorId(planPresupuestoBean.getCuenta());
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            listActividadCrCuentaBean = actividadCrService.obtenerCrPorActividad(planPresupuestoBean.getCuenta());
            if (listFiltroDetActividadBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
            //Presupuesto Plan
            Integer pres = 0;
            for (DetActividadBean det : listFiltroDetActividadBean) {
                pres = det.getImporte().intValue() + pres;
            }
            presupuesto = pres;
            System.out.println(">>>>>" + presupuesto);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerGrafoPorId(Object object) {
        try {
            presupuestoBean = (PresupuestoBean) object;
            new MaristaUtils().sesionColocarObjeto("gilmar", presupuestoBean.getIdPresupuesto());
//            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
//            listPresFiltroBean = presupuestoService.obtenerListaPresID(presupuestoBean.getIdPresupuesto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarGrafo() {
        parametro = (String) new MaristaUtils().requestObtenerObjeto("glam");
        parametro2 = (String) new MaristaUtils().requestObtenerObjeto("glam2");
        try {
            if (parametro != null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                listPresFiltroBean = presupuestoService.obtenerListaPresID(Integer.parseInt(parametro));
//                graficarLine(listPresFiltroBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerId() {
        try {
            parametro = (String) new MaristaUtils().requestObtenerObjeto("glam");
            System.out.println(parametro);
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            listPresFiltroBean = presupuestoService.obtenerListaPresID(Integer.parseInt(parametro));
            graficarLine();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String graficar() {
        pieModel = new PieChartModel();
        try {
            pieModel.set("11111", 123);
            pieModel.set("11111", 1234);
            pieModel.setTitle("Presupuesto Programado");
            pieModel.setLegendPosition("e");
            pieModel.setFill(true);
            pieModel.setDiameter(150);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "";
    }

    public String graficarLine() {
        barModel = new BarChartModel();
        try {
            Integer gilmar = (Integer) new MaristaUtils().sesionObtenerObjeto("gilmar");
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            listPresFiltroBean = presupuestoService.obtenerListaPresID(gilmar);
            new MaristaUtils().sesionColocarObjeto("gilmar", null);
            Integer exec = 0;
            ChartSeries cProg = new ChartSeries();
            ChartSeries cTope = new ChartSeries();
            ChartSeries cExec = new ChartSeries();
            if (listPresFiltroBean == null) {
                System.out.println("La lista esta vacia");
            } else {
                for (PresupuestoBean pres : listPresFiltroBean) {
                    System.out.println(pres.getPresupuestoProg().intValue());
                    System.out.println(pres.getPresupuestoTope().intValue());
                    if (pres.getPresupuestoProg() == null) {
                        System.out.println("Pres Programado Nulo");
                        cProg.setLabel("Presupuesto Programado");
                        cProg.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoProg());
                    } else {
                        cProg.setLabel("Presupuesto Programado");
                        cProg.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoProg().intValue());
                    }
                    if (pres.getPresupuestoTope() == null) {
                        System.out.println("Pres Tope Nulo");
                        cTope.setLabel("Presupuesto Tope");
                        cTope.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoTope());
                    } else {
                        cTope.setLabel("Presupuesto Tope");
                        cTope.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoTope().intValue());
                    }
                    if (pres.getPresupuestoEjec() == null) {
                        System.out.println("Pres Exec Nulo");
                        cExec.setLabel("Presupuesto Ejecutado");
                        cExec.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoEjec());
                    } else {
                        cExec.setLabel("Presupuesto Ejecutado");
                        cExec.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoEjec().intValue());
                    }
                }
            }
            barModel.addSeries(cProg);
            barModel.addSeries(cTope);
            barModel.addSeries(cExec);
            barModel.setTitle("Presupuesto");
            barModel.setLegendPosition("ne");
            Axis xAxis = barModel.getAxis(AxisType.X);
            xAxis.setLabel("Numero de Cuenta");
            Axis yAxis = barModel.getAxis(AxisType.Y);
            yAxis.setLabel("Presupuesto");
            yAxis.setMin(0);
            yAxis.setMax(4000);
            parametro3 = 1;
        } catch (Exception err) {
            parametro3 = 0;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "";
    }

    public void validarMetodo() {
        try {
            System.out.println(">>>>>>>>>>>>" + parametro3);
            if (parametro3 == null) {
                parametro3 = 0;
            }
            if (parametro3 == 1) {
                graficarLine();
                System.out.println("Hay Data");
            } else {
                System.out.println("No hubo data");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void crearGrafo() {
        try {
            animatedModel2 = obtenerMostrarGrafo();
            animatedModel2.setTitle("Bar Charts");
            animatedModel2.setAnimate(true);
            animatedModel2.setLegendPosition("ne");
            animatedModel2.getAxis(AxisType.Y).setMin(0);
            animatedModel2.getAxis(AxisType.Y).setMax(2500000);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public BarChartModel obtenerMostrarGrafo() {
        BarChartModel model = new BarChartModel();
        try {
            String numCuenta = presupuestoBean.getPlanContableBean().getCuenta().toString();
            System.out.println(numCuenta);
            System.out.println(presupuestoBean.getPresupuestoProg());
            System.out.println(presupuestoBean.getPresupuestoTope());
            ChartSeries presProgramado = new ChartSeries();
            presProgramado.setLabel("Presupuesto Programado");
            presProgramado.set(numCuenta, presupuestoBean.getPresupuestoProg());
            ChartSeries presTope = new ChartSeries();
            presTope.setLabel("Presupuesto Tope");
            presTope.set(numCuenta, presupuestoBean.getPresupuestoTope());
            model.addSeries(presProgramado);
            model.addSeries(presTope);
            System.out.println("grabo grafo");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return model;
    }

    public void showDialog() {
        RequestContext.getCurrentInstance().openDialog("pieChartDrillDownDialog");
    }

    //====================================Plan Por UniOrg====================================//
    public void obtenerPlanUniOrg() {
        try {

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroPresupuestoUo() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
//            listaPlanContableBean = detActividadService.obtenerPlanContable();
//            planFiltroContableBean = new PlanContableBean();
            if (presupuestoFiltroUOBean.getPlanContableBean().getCuenta() != null && !presupuestoFiltroUOBean.getPlanContableBean().getCuenta().equals(0)) {
                presupuestoFiltroUOBean.getPlanContableBean().setCuenta(presupuestoFiltroUOBean.getPlanContableBean().getCuenta());
            }
            if (presupuestoFiltroUOBean.getPlanContableBean().getIdTipoGrupoCta().getIdCodigo() != null && !presupuestoFiltroUOBean.getPlanContableBean().getIdTipoGrupoCta().getIdCodigo().toString().equals("")) {
                presupuestoFiltroUOBean.getPlanContableBean().getIdTipoGrupoCta().setCodigo(presupuestoFiltroUOBean.getPlanContableBean().getIdTipoGrupoCta().getCodigo());
            }
            if (presupuestoFiltroUOBean.getPlanContableBean().getIdTipoCuenta().getIdCodigo() != null && !presupuestoFiltroUOBean.getPlanContableBean().getIdTipoCuenta().getIdCodigo().toString().equals("")) {
                presupuestoFiltroUOBean.getPlanContableBean().getIdTipoCuenta().setCodigo(presupuestoFiltroUOBean.getPlanContableBean().getIdTipoCuenta().getCodigo());
            }
            listPresupuestoFiltroUOBean = presupuestoService.obtenerPresupuestoFiltro(presupuestoFiltroUOBean);
            if (listPresupuestoFiltroUOBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            } else {
                System.out.println(listPresupuestoFiltroUOBean.size());
                System.out.println("Completa");
            }

//            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
//            DetActividadService detActividadService = BeanFactory.getDetActividadService();
////            listaPlanContableBean = detActividadService.obtenerPlanContable();
////            planFiltroContableBean = new PlanContableBean(); planContablePresUo listPlanContablePresUo
//            if (planContablePresUo.getCuenta() != null && !planContablePresUo.getCuenta().equals(0)) {
//                planContablePresUo.setCuenta(planContablePresUo.getCuenta());
//            }
//            if (planContablePresUo.getIdTipoGrupoCta().getIdCodigo() != null && !planContablePresUo.getIdTipoGrupoCta().getIdCodigo().toString().equals("")) {
//                planContablePresUo.getIdTipoGrupoCta().setCodigo(planContablePresUo.getIdTipoGrupoCta().getCodigo());
//            }
//            if (planContablePresUo.getIdTipoCuenta().getIdCodigo() != null && !planContablePresUo.getIdTipoCuenta().getIdCodigo().toString().equals("")) {
//                planContablePresUo.getIdTipoCuenta().setCodigo(planContablePresUo.getIdTipoCuenta().getCodigo());
//            }
//            listPlanContablePresUo = detActividadService.obtenerPlanContableFiltro(planContablePresUo);
//            if (listPlanContablePresUo.isEmpty()) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
//            } else {
//                System.out.println(listPlanContablePresUo.size());
//                System.out.println("Completa");
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroPresUo() {
        try {
            presupuestoFiltroUOBean = new PresupuestoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPresUo(Object object) {
        try {
            presupuestoFiltroUOBean = (PresupuestoBean) object;
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoFiltroUOBean = presupuestoService.obtenerPorId(presupuestoFiltroUOBean.getIdPresupuesto());
            System.out.println(presupuestoFiltroUOBean.getPlanContableBean().getCuenta());
            System.out.println(presupuestoFiltroUOBean.getIdPresupuesto());
            presupuestoUniOrgBean.setCuenta(presupuestoFiltroUOBean.getPlanContableBean().getCuenta());
            presupuestoUniOrgBean.setIdPresupuesto(presupuestoFiltroUOBean.getIdPresupuesto());
            presupuestoUniOrgBean.setAnio(presupuestoFiltroUOBean.getAnio());
            presupuestoUniOrgBean.setPresupuestoProg(presupuestoFiltroUOBean.getPresupuestoProg());
            presupuestoUniOrgBean.setPresupuestoTope(presupuestoFiltroUOBean.getPresupuestoTope());
            presupuestoUniOrgBean.setPresupuestoEjec(presupuestoFiltroUOBean.getPresupuestoEjec());
            insertarPresUo2();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPresUo2(Object object) {
        try {
            planContablePresUo = (PlanContableBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            planContablePresUo = detActividadService.obtenerPlanPorId(planContablePresUo.getCuenta());
            insertarPresUo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarPresUo2() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
                presupuestoUniOrgBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuestoUniOrgBean.setCreaPor(beanUsuarioSesion.getUsuario());
                presupuestoUniOrgBean.getUnidadOrganicaBean().setIdUniOrg(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg());
                presupuestoUniOrgService.insertarPresupuestoUniOrg(presupuestoUniOrgBean);
                listPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), planOperativoBean.getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarPresUo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DetActividadService detActividadService = BeanFactory.getDetActividadService();
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                presupuestoBean = new PresupuestoBean();
                presupuestoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                presupuestoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                presupuestoBean.setAnio(planOperativoBean.getAnio());
                presupuestoBean.getPlanContableBean().setCuenta(planPresupuestoBean.getCuenta());
                presupuestoService.insertarPresupesto(presupuestoBean);
                listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarPlanPresupuesto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPresUoId(Object object) {
        try {
            presupuestoUniOrgBean = (PresupuestoUniOrgBean) object;
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            presupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorId(presupuestoUniOrgBean);
            System.out.println(presupuestoUniOrgBean.getCuenta());
            System.out.println(presupuestoUniOrgBean.getUnidadOrganicaBean().getIdUniOrg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String elimimarPresUo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
                presupuestoUniOrgService.eliminarPresupuestoUniOrg(presupuestoUniOrgBean);
                listPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), planOperativoBean.getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerGrafoPorId2(Object object) {
        try {
            presupuestoUniOrgBean = (PresupuestoUniOrgBean) object;
            new MaristaUtils().sesionColocarObjeto("gilmar", presupuestoUniOrgBean);
//            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
//            listPresFiltroBean = presupuestoService.obtenerListaPresID(presupuestoBean.getIdPresupuesto()); 
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String graficarLine2() {
        barModel2 = new BarChartModel();
        try {
            PresupuestoUniOrgBean gilmar = (PresupuestoUniOrgBean) new MaristaUtils().sesionObtenerObjeto("gilmar");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listPresUoFiltroBean = presupuestoUniOrgService.obtenerListaPorId(gilmar);
            new MaristaUtils().sesionColocarObjeto("gilmar", null);
            Integer exec = 0;
            ChartSeries cProg = new ChartSeries();
            ChartSeries cTope = new ChartSeries();
            ChartSeries cExec = new ChartSeries();
            if (listPresUoFiltroBean == null) {
                System.out.println("La lista esta vacia");
            } else {
                for (PresupuestoUniOrgBean pres : listPresUoFiltroBean) {
                    System.out.println(pres.getPresupuestoProg().intValue());
                    System.out.println(pres.getPresupuestoTope().intValue());
                    if (pres.getPresupuestoProg() == null) {
                        System.out.println("Pres Programado Nulo");
                        cProg.setLabel("Presupuesto Programado");
                        cProg.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoProg());
                    } else {
                        cProg.setLabel("Presupuesto Programado");
                        cProg.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoProg().intValue());
                    }
                    if (pres.getPresupuestoTope() == null) {
                        System.out.println("Pres Tope Nulo");
                        cTope.setLabel("Presupuesto Tope");
                        cTope.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoTope());
                    } else {
                        cTope.setLabel("Presupuesto Tope");
                        cTope.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoTope().intValue());
                    }
                    if (pres.getPresupuestoEjec() == null) {
                        System.out.println("Pres Exec Nulo");
                        cExec.setLabel("Presupuesto Ejecutado");
                        cExec.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoEjec());
                    } else {
                        cExec.setLabel("Presupuesto Ejecutado");
                        cExec.set("Numero de Cuenta:" + pres.getPlanContableBean().getCuenta().toString(), pres.getPresupuestoEjec().intValue());
                    }
                }
            }
            barModel2.addSeries(cProg);
            barModel2.addSeries(cTope);
            barModel2.addSeries(cExec);
            barModel2.setTitle("Presupuesto");
            barModel2.setLegendPosition("ne");
            Axis xAxis = barModel2.getAxis(AxisType.X);
            xAxis.setLabel("Numero de Cuenta");
            Axis yAxis = barModel2.getAxis(AxisType.Y);
            yAxis.setLabel("Presupuesto");
            yAxis.setMin(0);
            yAxis.setMax(50000);
            parametro3 = 1;
        } catch (Exception err) {
            parametro3 = 0;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "";
    }

    public void obtenerActCuenta(SelectEvent event) {
        try {
            presupuestoUniOrgBean = (PresupuestoUniOrgBean) event.getObject();
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            presupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorId(presupuestoUniOrgBean);
            //Obtener Cuenta por Actividades 
            DetActividadService deatActividadService = BeanFactory.getDetActividadService();
            listFiltroDetActividadBean = deatActividadService.obtenerCuentaPorId(presupuestoUniOrgBean.getPlanContableBean().getCuenta());
            Integer pres = 0;
            for (DetActividadBean det : listFiltroDetActividadBean) {
                pres = det.getImporte().intValue() + pres;
            }
            presupuesto = pres;
            System.out.println(">>>>>" + presupuesto);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowEditActCuenta(RowEditEvent event) {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            System.out.println(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoProg());
            System.out.println(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoTope());
            //Editando Presupuesto_Uni_Org
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
            presupuestoBean = new PresupuestoBean();
            presupuestoUniOrgBean.setPresupuestoProg(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoProg());
            presupuestoUniOrgBean.setPresupuestoTope(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoTope());
            presupuestoUniOrgBean.setModiPor(beanUsuarioSesion.getUsuario());
            presupuestoUniOrgBean.setAnio(((PresupuestoUniOrgBean) event.getObject()).getAnio());
            presupuestoUniOrgBean.getUnidadOrganicaBean().setIdUniOrg(((PresupuestoUniOrgBean) event.getObject()).getUnidadOrganicaBean().getIdUniOrg());
            presupuestoUniOrgBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuestoUniOrgBean.setCuenta(((PresupuestoUniOrgBean) event.getObject()).getCuenta());

            //Editando Presupuesto 
            presupuestoBean.setPresupuestoProg(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoProg());
            presupuestoBean.setPresupuestoTope(((PresupuestoUniOrgBean) event.getObject()).getPresupuestoTope());
            presupuestoBean.setModiPor(beanUsuarioSesion.getUsuario());
            presupuestoBean.setIdPresupuesto(((PresupuestoUniOrgBean) event.getObject()).getIdPresupuesto());

            presupuestoUniOrgService.modificarPresupuestoUniOrg(presupuestoUniOrgBean);
            System.out.println("Presupuesto_Uni_Org Editado");

            presupuestoService.modificarDatosPresupuesto(presupuestoBean);
            System.out.println("Presupuesto Editado");

            listPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), planOperativoBean.getUnidadNegocioBean().getUniNeg());
            listPresupuestoBean = presupuestoService.obtenerPresupesto(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            System.out.println("Cambio Exitoso");
            FacesMessage msg = new FacesMessage("Cambio Exitoso", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
            presupuestoBean = new PresupuestoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancelActCuenta(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Cambio Cancelado", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Actividad_CR
    public void obtenerDetaActividadCR(Object object) {
        try {
            detActividadBean = (DetActividadBean) object;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            detActividadBean = detActividadService.obtenerPorId(detActividadBean.getIdDetActividad());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void asignarActCr() {
        try {
            //detActividadBean -> actividadBean 
            //Det_Actividad 
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            actividadCrBean = new ActividadCrBean();
            //Obtener Lista_Dual_CR
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<ActividadCrBean> listaActividadCrBean = new ArrayList<>();
            System.out.println(dualDetActCr.getTarget().size());
            for (int i = 0; i < dualDetActCr.getTarget().size(); i++) {
                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
                Object objeto = dualDetActCr.getTarget().get(i);
                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
                actividadCrBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                actividadCrBean.getUnidadOrganicaBean().setIdUniOrg(actividadFiltroBean.getIdUniOrg());
                actividadCrBean.setAnio(actividadFiltroBean.getAnio());
                actividadCrBean.setIdObjOperativo(actividadFiltroBean.getObjOperativoBean().getIdObjOperativo());
                actividadCrBean.setIdActividad(actividadFiltroBean.getIdActividad());
//                actividadCrBean.getPlanContableBean().setCuenta(detActividadBean.getPlanContableBean().getCuenta());
                actividadCrBean.getCentroResponsabilidadBean().setCr(cr.getCr());
                actividadCrBean.setMeta(actividadFiltroBean.getMeta());
//                actividadCrBean.setImporte(detActividadBean.getImporte());
                actividadCrBean.setCreaPor(beanUsuarioSesion.getUsuario());
                System.out.println(cr.getCr());
                System.out.println(actividadCrBean.getCentroResponsabilidadBean().getCr());
                System.out.println("select: " + dualDetActCr.getTarget().size());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarActCr() {
        String pagina = null;
        try {
            System.out.println(">>>" + actividadFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
            if (pagina == null) {
                asignarActCr();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                listActividadCrBean = actividadCrService.obtenerActividadCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaCentroResponsabilidad = centroResponsabilidadService.obtenerCentroResponsabilidad();
                actividadCrService.insertarActividadCr(actividadCrBean, dualDetActCr.getTarget());
                listCR = centroResponsabilidadService.obtenerCrOutAct(actividadFiltroBean.getIdActividad());
                listCRB = centroResponsabilidadService.obtenerCrInAct(actividadFiltroBean.getIdActividad());
                dualDetActCr = new DualListModel<>(listCR, listCRB);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarActividadCr() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                actividadCrBean.setModiPor(beanUsuarioSesion.getUsuario());
                actividadCrBean.getPlanContableBean().setCuenta(planContableBean.getCuenta());
                actividadCrService.modificarActividadCr(actividadCrBean, idCr, idActividad);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //========================================================================//
    public void rowSelect(SelectEvent event) {
        try {
            planOperativoBean = (PlanOperativoBean) event.getObject();
            objOperativoBean.getPlanOperativoBean().setNombre(planOperativoBean.getNombre());
            objOperativoBean.getPlanOperativoBean().getUnidadOrganicaBean().setIdUniOrg(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg());
            objOperativoBean.getPlanOperativoBean().setAnio(planOperativoBean.getAnio());
            objOperativoBean.getIdObjOperativo();
            System.out.println(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg());
            //Lista Obj. Operativo por Plan Operativo
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            actividadBean.getObjOperativoBean().setPlanOperativoBean(planOperativoBean);
            listaObjetivoOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //Lista Actividad
            ActividadService actividadService = BeanFactory.getActividadService();
            listActividadBean = actividadService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            presupuestoUniOrgBean.setUnidadOrganicaBean(planOperativoBean.getUnidadOrganicaBean());
            presupuestoBean.setAnio(planOperativoBean.getAnio());
            //Lista de PResupuesto UniOrg
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
//            listPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //ListaObjAct
            listaObjetivoActOperativo = objetivoOperativoService.obtenerPorPlanOperativo(planOperativoBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Lista Concepto
            String modulo = planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString();
            if (planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("5")) {
                modulo = "ML";
            }
            if (planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("205")) {
                modulo = "MT";
            }
            if (planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("206")) {
                modulo = "MO";
            }
            if (planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("400")
                    || planOperativoBean.getUnidadOrganicaBean().getIdUniOrg().toString().equals("300")) {
                modulo = "ME";
            }
            System.out.println(modulo);
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            listConceptoBean = conceptoService.obtenerPresupuestoTipo(modulo);
            mostrarActv = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerObjOperativo(Object object) {
        try {
            ObjOperativoBean obj = (ObjOperativoBean) object;
            System.out.println("Llego Objeto");
            System.out.println(obj.getIdObjOperativo());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect2(SelectEvent event) {
        try {
            objOperativoBean = (ObjOperativoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect3(SelectEvent event) {
        try {
            actividadBean = (ActividadBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectObjOperativo(SelectEvent event) {
        try {
            ObjOperativoBean objOpe = (ObjOperativoBean) event.getObject();
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            objOperativoBean = objetivoOperativoService.obtenerPorId(objOpe.getIdObjOperativo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerDetallesEstrategicos(Object object) {
        try {
            ObjetivoEstrategicoDetBean objO = (ObjetivoEstrategicoDetBean) object;
            actividadBean.getObjOperativoBean().setObjetivoEstrategicoDetBean(objO);
            ObjetivoOperativoService objetivoOperativoService = BeanFactory.getObjetivoOperativoService();
            objOperativoBean = objetivoOperativoService.obtenerPorObjDet(objO.getIdObjEstrategicoDet());
//            System.out.println(objOperativoBean.getIdObjOperativo());
//            actividadBean.getObjOperativoBean().setIdObjOperativo(objOperativoBean.getIdObjOperativo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowToggle(ToggleEvent event) {
        try {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row State " + event.getVisibility(), "Model:" + ((ObjetivoEstrategicoDetBean) event.getData()).getLineaEstrategicaBean());
//            FacesContext.getCurrentInstance().addMessage(null, msg);

//            listaSubObjetivoEstrategicoDetBean = new ArrayList<>();
//            ObjetivoEstrategicoDetBean objDet = (ObjetivoEstrategicoDetBean) object;
//            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
//            listaSubObjetivoEstrategicoDetBean = objetivoEstrategicoDetService.obtenerListaPorId(objetivoEstrategicoDetBean.getIdObjEstrategicoDet());
//            if (event.getVisibility() == Visibility.VISIBLE) {
            objetivoEstrategicoDetBean = (ObjetivoEstrategicoDetBean) event.getData();
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaSubObjetivoEstrategicoDetBean = objetivoEstrategicoDetService.obtenerListaPorId(objetivoEstrategicoDetBean.getIdObjEstrategicoDet());
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowToggle2(ToggleEvent event) {
        try {
            objetivoEstrategicaBean = (ObjetivoEstrategicaBean) event.getData();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaObjetivoEstrategicoDetBean = objetivoEstrategicoDetService.obtenerDetPorObj(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), objetivoEstrategicaBean.getIdObjEstrategico());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorObjEstrategico(Object object) {
        try {
            objetivoEstrategicaBean = (ObjetivoEstrategicaBean) object;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaObjetivoEstrategicoDetBean = objetivoEstrategicoDetService.obtenerDetPorObj(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), objetivoEstrategicaBean.getIdObjEstrategico());
            panelPlan = true;
            panelLin = true;
            panelObj = true;
            panelRes = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectActividad(SelectEvent event) {
        try {
            ActividadBean act = (ActividadBean) event.getObject();
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadBean = actividadService.obtenerPorId(act.getIdActividad());
            actividadBean.getObjOperativoBean().setPlanOperativoBean(act.getObjOperativoBean().getPlanOperativoBean());
            actividadBean.getObjOperativoBean().setObjetivoEstrategicoDetBean(act.getObjOperativoBean().getObjetivoEstrategicoDetBean());
            actividadBean.setIndicadorBean(act.getIndicadorBean());
            actividadBean.getUnidadOrganicaBean().setIdUniOrg(act.getUnidadOrganicaBean().getIdUniOrg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void crearArbol() {
//        getObjetivoEstrategicoDetBean();
//        TreeNode root = new DefaultTreeNode(ObjetivoEstrategicoDetBean(objetivoEstrategicoDetBean.getObjetivoEstrategicaBean().getLineaEstrategicaBean().getPlanEstrategicoBean().getNombre(),
//                objetivoEstrategicoDetBean.getObjetivoEstrategicaBean().getLineaEstrategicaBean().getNombre(),
//                objetivoEstrategicoDetBean.getObjetivoEstrategicaBean().getNombre()), null);
//    }
    //Cerrado de Tablas de Plan Contable
    public void cerrarPresupuesto() {
        try {
            listaPlanPresupuestoBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarPresupuesto2() {
        try {
            listPresupuestoFiltroUOBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarPresupuesto3() {
        try {
            listaPlanContableBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onSelect(SelectEvent event) {
        try {
            idCr = event.getObject().toString();
            Integer cr = Integer.parseInt(idCr);
            idActividad = actividadFiltroBean.getIdActividad();

            //Cambios
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listCRB = centroResponsabilidadService.obtenerCrInAct(actividadFiltroBean.getIdActividad());
            for (CentroResponsabilidadBean second : listCRB) {
                if (second.getCr().equals(cr)) {
                    RequestContext.getCurrentInstance().addCallbackParam("abrirCr", true);
                    break;
                }
            }

            mostrarSub = true;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            listaDetActividadBean = detActividadService.obtenerActCR(idActividad, cr, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Obteniendo Nombre Cr 
            CentroResponsabilidadBean centroResponsabilidadBean = new CentroResponsabilidadBean();
            centroResponsabilidadBean = centroResponsabilidadService.obtenerCRPorId(cr);
            nomCr = centroResponsabilidadBean.getNombre();

            //Obteniendo Nombre De Actividad
            DetActividadBean detActividadBean = new DetActividadBean();
            detActividadBean = detActividadService.obtenerPorId(idActividad);
            ActividadBean act = new ActividadBean();
            ActividadService actividadService = BeanFactory.getActividadService();
            act = actividadService.obtenerPorId(idActividad);
            if (act != null) {
                nomActividad = act.getNombre();
            } else {
                nomActividad = "";
            }
            Integer dato = 0;
            for (DetActividadBean deta : listaDetActividadBean) {
                dato = deta.getImporte().intValue() + dato;
            }
            importeSubActividad = dato;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarCuenta() {
        try {
            System.out.println(detActividadBean.getPlanContableBean().getCuenta());
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listPlanContableBean = planContableService.obtenerPlanFiltroCuenta(detActividadBean.getPlanContableBean().getCuenta());
            System.out.println(listPlanContableBean.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //========================================================================//
    public int getCuentaCount() {
        return listPlan.size();
    }

    public void crearDocumento() {
        try {
            //TreeNode
            List<ObjetivoEstrategicoDetBean> listaDet = new ArrayList<>();
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaDet = objetivoEstrategicoDetService.obtenerTodos();
            for (ObjetivoEstrategicoDetBean det : listaDet) {
                TreeNode documents = new DefaultTreeNode(det.getPlanEstrategicoBean().getNombre(), det.getPlanEstrategicoBean().getDescripcion(), this.rootNode);
                TreeNode document01 = new DefaultTreeNode(det.getLineaEstrategicaBean().getNombre(), det.getLineaEstrategicaBean().getDescripcion(), documents);
                TreeNode document02 = new DefaultTreeNode(det.getObjetivoEstrategicaBean().getNombre(), det.getObjetivoEstrategicaBean().getDescripcion(), documents);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarPlanEst() {
        try {
            listaPlanEstrategicoBean = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarLinEst() {
        try {
            listaLineaEstrategicaBean = new ArrayList<>();
            listaObjetivoEstrategicaBean = new ArrayList<>();
            panelPlan = false;
            panelObj = true;
            panelLin = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarObjEst() {
        try {
            listaObjetivoEstrategicaBean = new ArrayList<>();
            panelObj = true;
            panelLin = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarResponsable() {
        try {
            listaObjetivoEstrategicoDetBean = new ArrayList<>();
            panelRes = true;
            panelObj = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void graficarPres(Object object) {
        try {
            actividadCrBean = (ActividadCrBean) object;
            System.out.println(actividadCrBean.getCentroResponsabilidadBean().getCr());
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            exec = actividadCrService.obetenerPresupuestoGeneralExec(actividadCrBean.getCentroResponsabilidadBean().getCr(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            System.out.println("exec=>>>"+exec);
            new MaristaUtils().sesionColocarObjeto("gilmarr", actividadCrBean);
//            graficarPresupuesto();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public PieChartModel graficarPresupuesto() {
        pieModel = new PieChartModel();
        try {
            ActividadCrBean gilmarr = (ActividadCrBean) new MaristaUtils().sesionObtenerObjeto("gilmarr");
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            if (gilmarr != null) {
                if (listaCrA == null) {
                    listaCrA = new ArrayList<>();
                    listaCrA = actividadCrService.obtenerDibujoPresupuestoCr(gilmarr.getCentroResponsabilidadBean().getCr(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (!listaCrA.isEmpty()) {
                        for (ActividadCrBean acr : listaCrA) {
                            pieModel.set(acr.getPlanContableBean().getNombre(), acr.getImporte().intValue());
                        }
                        pieModel.setTitle("Presupuesto/C.R.");
                        pieModel.setLegendPosition("w");
                        pieModel.setFill(true);
                        pieModel.setDiameter(150);
                        pieModel.setExtender("pieExtender");
                        new MaristaUtils().sesionColocarObjeto("gilmarr", null);
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieModel;
    }

    public void obtenerGrafo(ItemSelectEvent e) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrBean gilmarr = (ActividadCrBean) new MaristaUtils().sesionObtenerObjeto("gilmarr");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            Integer dato = -1;
//            ActividadCrBean crA = new ActividadCrBean();
            for (int j = 0; j < listaCrA.size(); j++) {
                dato = dato + 1;
                if (dato.equals(e.getItemIndex())) {
                    exec = actividadCrService.obetenerPresupuestoGeneralExec(listaCrA.get(dato).getCentroResponsabilidadBean().getCr(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaCrA.get(dato).getPlanContableBean().getCuenta());
                    System.out.println("execcccc===>>>" + exec);
                    listaCrA.get(dato).getPlanContableBean().getCuenta();
                    crA.getPlanContableBean().setCuenta(listaCrA.get(dato).getPlanContableBean().getCuenta());
                    crA.setImporte(listaCrA.get(dato).getImporte());
                    crA.setExec(exec);
                    System.out.println(dato);
                    System.out.println("Cuenta: " + listaCrA.get(dato).getPlanContableBean().getCuenta());
                    System.out.println("Importe: " + listaCrA.get(dato).getImporte());
                    System.out.println("Cuenta1: " + crA.getPlanContableBean().getCuenta());
                    System.out.println("Importe1: " + crA.getImporte());
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void openDialog() {
        try {
            Map<String, Object> options = new HashMap<String, Object>();
            options.put("modal", true);
            options.put("draggable", false);
            options.put("resizable", false);
            options.put("contentWidth", 500);
            options.put("contentHeight", 100);
            options.put("includeViewParams", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<PresupuestoGeneralRepBean> listaPresupuestoRepBean = new ArrayList<>();
            for (ActividadCrBean acr : listCrAct) {
                PresupuestoGeneralRepBean presupuestoGeneralRepBean = new PresupuestoGeneralRepBean();
                presupuestoGeneralRepBean.setCr(acr.getCentroResponsabilidadBean().getCr());
                presupuestoGeneralRepBean.setCuenta(acr.getPlanContableBean().getCuenta());
                presupuestoGeneralRepBean.setImporte(acr.getImporte());
                presupuestoGeneralRepBean.setNombre(acr.getCentroResponsabilidadBean().getNombre());
                presupuestoGeneralRepBean.setNombrePlan(acr.getPlanContableBean().getNombre());
                listaPresupuestoRepBean.add(presupuestoGeneralRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresupuestoRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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

    public void imprimirPresupuestoCr() {
        ServletOutputStream out = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoCr.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            //Inicializando Lista de Rep CrActividad
            List<PresupuestoCrRepBean> listaPresupuestoCrRepBean = new ArrayList<>();
            List<CentroResponsabilidadBean> listaCentro = new ArrayList<>();

            //Poblando Lista Cr en ActividadCr
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentro = centroResponsabilidadService.obtenerPresCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CentroResponsabilidadBean centro : listaCentro) {
                PresupuestoCrRepBean presupuestoCrRepBean = new PresupuestoCrRepBean();
                presupuestoCrRepBean.setCr(centro.getCr());
                presupuestoCrRepBean.setNivel(centro.getNivel());
                presupuestoCrRepBean.setNombre(centro.getNombre());
                presupuestoCrRepBean.setCreaFechaAc(centro.getCreaFechaAc());
                presupuestoCrRepBean.setCreaHoraAc(centro.getCreaHoraAc());
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                listCrAct = actividadCrService.obtenerPresupuestoCrId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), centro.getCr());
                List<PresupuestoCrRepSubBean> listaPresupuestoCrRepSubBean = new ArrayList<>();
                for (ActividadCrBean acr : listCrAct) {
                    PresupuestoCrRepSubBean presSub = new PresupuestoCrRepSubBean();
                    presSub.setCr(acr.getCentroResponsabilidadBean().getCr());
                    presSub.setImporte(acr.getImporte());
                    presSub.setNomPlanCu(acr.getPlanContableBean().getNombre());
                    presSub.setNombreCR(acr.getCentroResponsabilidadBean().getNombre());
                    presSub.setNumCuenta(acr.getPlanContableBean().getCuenta());
                    listaPresupuestoCrRepSubBean.add(presSub);
                }
                presupuestoCrRepBean.setListaDetalle(listaPresupuestoCrRepSubBean);
                listaPresupuestoCrRepBean.add(presupuestoCrRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresupuestoCrRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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
        }
    }

    //===================================Indicador=====================================//
    public void onRowEditIndicador(RowEditEvent event) {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            indicadorFiltroBean = new IndicadorBean();
            indicadorFiltroBean.setIdIndicador(((IndicadorBean) event.getObject()).getIdIndicador());
            indicadorFiltroBean.setNombre(((IndicadorBean) event.getObject()).getNombre());
            indicadorFiltroBean.getCodigoTiPoIndicador().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTiPoIndicador().getIdCodigo());
            indicadorFiltroBean.setFormula(((IndicadorBean) event.getObject()).getFormula());
            indicadorFiltroBean.setModiPor(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            indicadorFiltroBean.getCodigoTiPoUso().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTiPoUso().getIdCodigo());
            indicadorFiltroBean.getCodigoTipoValor().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTipoValor().getIdCodigo());
            indicadorFiltroBean.setMeta(((IndicadorBean) event.getObject()).getMeta());
            indicadorService.actualizar(indicadorFiltroBean);
//            listaIndicadorBean = indicadorService.obtenerTodos();
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                System.out.println(14401);
            }
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                System.out.println(14402);
            }
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                System.out.println(14403);
            }
            System.out.println("Cambio Exitoso");
            FacesMessage msg = new FacesMessage("Cambio exitoso: Indicador", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            indicadorFiltroBean = new IndicadorBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancelIndicador(RowEditEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Cambio Cancelado", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarIndicador() {
        try {
            System.out.println(indicadorBean.getCodigoTiPoUso().getIdCodigo());
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            listaIndicadorBean = indicadorService.obtenerPorTipoUso(indicadorBean.getCodigoTiPoUso().getIdCodigo());
            System.out.println(listaIndicadorBean.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerIndicador(Object object) {
        try {
            indicadorFiltroBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorFiltroBean = indicadorService.buscarPorId(indicadorFiltroBean.getIdIndicador());
            System.out.println(indicadorFiltroBean.getIdIndicador());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerIndicador(Object object) {
        try {
            indicadorFiltroBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorFiltroBean = indicadorService.buscarPorId(indicadorFiltroBean.getIdIndicador());
            actividadBean.setIndicadorBean(indicadorFiltroBean);
            actividadBean.getIndicadorBean().setIdIndicador(indicadorFiltroBean.getIdIndicador());
            System.out.println(actividadBean.getIndicadorBean().getIdIndicador());
            System.out.println("Llego");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCodigoIndicador() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            String codigo = "IND-";
            if (indicadorBean == null) {
                indicadorBean = new IndicadorBean();
            }
            String cod = indicadorService.obtenerCodigo(indicadorBean.getCodigo());
            System.out.println("ind. cod" + cod);
            Integer var = Integer.parseInt(cod) + 1;
            String base = String.format("%03d", var);
            indicadorBean.setCodigo(codigo.concat(base));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarIndicadorBean() {
        try {
            indicadorBean = new IndicadorBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarIndicador() {
        try {
            if (indicadorBean.getIdIndicador() == null) {
                insertarIndicador();
            } else {
                modificarIndicador();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setCreaPor(user.getUsuario());
                indicadorBean.setFechaCrea(formato.parse(date));
                indicadorService.insertar(indicadorBean);
                CodigoBean codigoUso = new CodigoBean();
                CodigoService codigoService = BeanFactory.getCodigoService();
                System.out.println(indicadorBean.getCodigoTiPoUso().getIdCodigo());
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                    System.out.println(14401);
                }
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14402);
                }
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14403);
                }
//                listaIndicadorBean = indicadorService.obtenerTodos();
                limpiarIndicadorBean();
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setModiPor(user.getUsuario());
                indicadorService.actualizar(indicadorBean);
//                listaIndicadorBean = indicadorService.obtenerTodos();
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                    System.out.println(14401);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14402);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14403);
                }
                limpiarIndicadorBean();
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarIndicador() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorService.eliminar(indicadorFiltroBean.getIdIndicador());
//                listaIndicadorBean = indicadorService.obtenerTodos();
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                    System.out.println(14401);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14402);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14403);
                }
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //Persona 
    public void obtenerUniOrgPersonal() {
        try {
            System.out.println(">>>" + actividadBean.getUnidadOrganicaBean().getIdUniOrg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Ver Presupuesto
    public void verPresupuesto() {

    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

}
