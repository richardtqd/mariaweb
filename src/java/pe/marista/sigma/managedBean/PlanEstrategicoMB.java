/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.IndicadorBean;
import pe.marista.sigma.bean.IndicadorObjEspecificoBean;
import pe.marista.sigma.bean.LineaEstrategicaBean;
import pe.marista.sigma.bean.ObjetivoEspecificoBean;
import pe.marista.sigma.bean.ObjetivoEstrategicaBean;
import pe.marista.sigma.bean.ObjetivoEstrategicoDetBean;
import pe.marista.sigma.bean.PeriodoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanEstrategicoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.IndicadorService;
import pe.marista.sigma.service.LineaEstrategicaService;
import pe.marista.sigma.service.ObjetivoEstrategicoDetService;
import pe.marista.sigma.service.ObjetivoEstrategicoService;
import pe.marista.sigma.service.PeriodoService;
import pe.marista.sigma.service.PlanEstrategicoService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class PlanEstrategicoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void PlanEstrategicoMB() {
        try {
            cargarAno();
            obtenerPersonas();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            //Poner UniNeg Plan
            getPlanEstrategicoFiltroBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();

            //Lista Linea Estrategica
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();

            listaLineaEstrategicaFiltro = lineaEstrategicaService.obtenerLineaEstrategica();
            //Objetivo Estrategico
            getListaPlanEstPorObjetivo();
            listaPlanEstPorObjetivo = planEstrategicoService.obtenerTodos();

//                getListaLineaEstPorPlan();
//                listaLineaEstPorPlan = lineaEstrategicaService.obtenerLineaEstrategica();
//                obtenerTodosObjetivoEstrategico();
            CodigoService codigoService = BeanFactory.getCodigoService();
            //Tipo Actividad
            getListaTipoValor();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));

            //Indicador 
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            getListaIndicadorBean();
//            listaIndicadorBean = indicadorService.obtenerTodos();
            listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);

//            getLineaPeridoBean();
//            lineaPeridoBean = lineaEstrategicaService.obtenerLineaEstrategica();
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();

            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            getListaObjDetPeriodoBean();
            listaObjDetPeriodoBean = objetivoEstrategicoDetService.obtenerTodos();

            CodigoService valorService = BeanFactory.getCodigoService();
            getListaCodigoValor();
            listaCodigoValor = valorService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));

            //Tipo Unidad de Medida
            getListaUniMedCodigo();
            listaUniMedCodigo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_UNIMED_OPER));

            //TipoIndicador
            getListaTipoIndicador();
            listaTipoIndicador = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Indicador));

            //TipoUsoIndicador
            getListaTipoUsoIndicador();
            listaTipoUsoIndicador = codigoService.obtenerPorTipoEst(new TipoCodigoBean(MaristaConstantes.TIP_UsoIndicador));
            obtenerCodigoIndicador();

            fechaActual = new GregorianCalendar();
            GregorianCalendar fecha = new GregorianCalendar();
            getPlanEstrategicoBean();
            getPlanEstrategicoBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPlanEstrategicoBean().setFecInicio(fecha.getTime());
            getPlanEstrategicoBean().setFecTermino(fecha.getTime());

            //SETEANDO PLAN ESTRATEGICO
            getPlanEstrategicoFiltroBean();
            getPlanEstrategicoFiltroBean().setAnioIni(Integer.parseInt(MaristaConstantes.Anios_Banco));
            getPlanEstrategicoFiltroBean().setAnioFin(Integer.parseInt(MaristaConstantes.Anios_Banco));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//===========================Inicio Private =================================================// 

    private List<PlanEstrategicoBean> listaPlanEstrategicoBean;
    private PlanEstrategicoBean planEstrategicoBean;
    private PlanEstrategicoBean planEstrategicoFiltroBean;
    private List<Object> listaAnos;
    private List<Object> listaAnosRender;
    private List<PersonalBean> listaPersonalBean;
    private Map<String, Integer> mapPersonaBean;
    private List<UnidadNegocioBean> listUnidadNegocioBean;
    private Map<String, String> mapUnidadNegocio;
    private LineaEstrategicaBean lineaEstrategicaBean;
    private LineaEstrategicaBean lineaEstrategica;
    private List<LineaEstrategicaBean> listaLineaEstrategicaBean;
    private Map<String, Integer> mapPlanEstrategicoBean;
    private List<PeriodoBean> listPeriodoBean1;
    private Map<String, Integer> mapLineaEstrategicaBean;
    private List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean;
    private List<ObjetivoEstrategicaBean> listA;
    private List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean2;
    private List<IndicadorObjEspecificoBean> listIndicadorObjEspecificoBean;
    private ObjetivoEstrategicaBean objetivoEstrategicaBean;
    private ObjetivoEspecificoBean objetivoEspecificoBean;
    private IndicadorBean indicadorBean;
    private List<IndicadorBean> listIndicadorBean;

    //Ayuda 
    private List<CodigoBean> listaTipoValor;
    private IndicadorBean indicadorFiltroBean;
    private List<IndicadorBean> listaIndicadorBean;

    //Plan Estrategico 
    private List<PlanEstrategicoBean> listaPlanEstrategicoFiltro;

    //Linea Estrategica
    private List<LineaEstrategicaBean> listaLineaEstrategicaFiltro;
    private List<LineaEstrategicaBean> listaLineaEstPorPlan;

    //Objetivo Estrategico
    private ObjetivoEstrategicaBean objetivoEstrategicoBean;
    private List<ObjetivoEstrategicaBean> listaObjetivoEstrategicoBean;//Segundo Objetivo Estrategico
    private List<PlanEstrategicoBean> listaPlanEstPorObjetivo;

    //Objetivo Estrategico Det
    private List<ObjetivoEstrategicoDetBean> listaObjetivoEstrategicaDetBean;
    private ObjetivoEstrategicoDetBean objetivoEstrategicaDetBean;
    private ObjetivoEstrategicoDetBean objetivoEstrategicaDetBean2;
    private List<PlanEstrategicoBean> listaPlanFiltro;
    private List<LineaEstrategicaBean> listaLineaPorPlan;
    private List<ObjetivoEstrategicaBean> listaObjPorLinea;
    private List<ObjetivoEstrategicoDetBean> listaObjPerBean;
    private List<Integer> listaAnios;

    //Periodo
    private PeriodoBean periodoBean;
    private List<PeriodoBean> listaPeriodoBean;
    private List<LineaEstrategicaBean> lineaPeridoBean;
    private List<ObjetivoEstrategicaBean> lineaObjPeriodoBean;
    private List<ObjetivoEstrategicoDetBean> listaObjDetPeriodoBean;
    private List<ObjetivoEstrategicoDetBean> listaObjDetPeriodoBean2;
    private List<PeriodoBean> listaPeriodoFiltroBean;
    private List<CodigoBean> listaCodigoValor;
    private List<PeriodoBean> listaPeriodoAnios;
    private List<Object> listaAniosPeriodo;
    private Integer flg;

    //Indicador
    private List<CodigoBean> listaUniMedCodigo;
    private List<CodigoBean> listaTipoUsoIndicador;
    private List<CodigoBean> listaTipoIndicador;

    //VARIABLES GLOBALES
    private Calendar fechaActual;

//===========================Final Private =================================================// 
//===========================Metodo get y Set =================================================// 
    public ObjetivoEstrategicaBean getObjetivoEstrategicaBean() {
        if (objetivoEstrategicaBean == null) {
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicaBean;
    }

    public void setObjetivoEstrategicaBean(ObjetivoEstrategicaBean objetivoEstrategicaBean) {
        this.objetivoEstrategicaBean = objetivoEstrategicaBean;
    }

    public ObjetivoEspecificoBean getObjetivoEspecificoBean() {
        if (objetivoEspecificoBean == null) {
            objetivoEspecificoBean = new ObjetivoEspecificoBean();
        }
        return objetivoEspecificoBean;
    }

    public void setObjetivoEspecificoBean(ObjetivoEspecificoBean objetivoEspecificoBean) {
        this.objetivoEspecificoBean = objetivoEspecificoBean;
    }

    public List<PlanEstrategicoBean> getListaPlanEstrategicoBean() {
        return listaPlanEstrategicoBean;
    }

    public void setListaPlanEstrategicoBean(List<PlanEstrategicoBean> listaPlanEstrategicoBean) {
        this.listaPlanEstrategicoBean = listaPlanEstrategicoBean;
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

    public PlanEstrategicoBean getPlanEstrategicoFiltroBean() {
        if (planEstrategicoFiltroBean == null) {
            planEstrategicoFiltroBean = new PlanEstrategicoBean();
        }
        return planEstrategicoFiltroBean;
    }

    public void setPlanEstrategicoFiltroBean(PlanEstrategicoBean planEstrategicoFiltroBean) {
        this.planEstrategicoFiltroBean = planEstrategicoFiltroBean;
    }

    public List<Object> getListaAnos() {
        return listaAnos;
    }

    public void setListaAnos(List<Object> listaAnos) {
        this.listaAnos = listaAnos;
    }

    public List<PersonalBean> getListaPersonalBean() {
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
    }

    public Map<String, Integer> getMapPersonaBean() {
        return mapPersonaBean;
    }

    public void setMapPersonaBean(Map<String, Integer> mapPersonaBean) {
        this.mapPersonaBean = mapPersonaBean;
    }

    public List<UnidadNegocioBean> getListUnidadNegocioBean() {
        return listUnidadNegocioBean;
    }

    public void setListUnidadNegocioBean(List<UnidadNegocioBean> listUnidadNegocioBean) {
        this.listUnidadNegocioBean = listUnidadNegocioBean;
    }

    public Map<String, String> getMapUnidadNegocio() {
        return mapUnidadNegocio;
    }

    public void setMapUnidadNegocio(Map<String, String> mapUnidadNegocio) {
        this.mapUnidadNegocio = mapUnidadNegocio;
    }

    public List<Object> getListaAnosRender() {
        return listaAnosRender;
    }

    public void setListaAnosRender(List<Object> listaAnosRender) {
        this.listaAnosRender = listaAnosRender;
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

    public Map<String, Integer> getMapPlanEstrategicoBean() {
        return mapPlanEstrategicoBean;
    }

    public void setMapPlanEstrategicoBean(Map<String, Integer> mapPlanEstrategicoBean) {
        this.mapPlanEstrategicoBean = mapPlanEstrategicoBean;
    }

    public List<PeriodoBean> getListPeriodoBean1() {
        return listPeriodoBean1;
    }

    public void setListPeriodoBean1(List<PeriodoBean> listPeriodoBean1) {
        this.listPeriodoBean1 = listPeriodoBean1;
    }

    public Map<String, Integer> getMapLineaEstrategicaBean() {
        return mapLineaEstrategicaBean;
    }

    public void setMapLineaEstrategicaBean(Map<String, Integer> mapLineaEstrategicaBean) {
        this.mapLineaEstrategicaBean = mapLineaEstrategicaBean;
    }

    public List<ObjetivoEstrategicaBean> getListaObjetivoEstrategicaBean() {
        return listaObjetivoEstrategicaBean;
    }

    public void setListaObjetivoEstrategicaBean(List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean) {
        this.listaObjetivoEstrategicaBean = listaObjetivoEstrategicaBean;
    }

    public List<ObjetivoEstrategicaBean> getListA() {
        return listA;
    }

    public void setListA(List<ObjetivoEstrategicaBean> listA) {
        this.listA = listA;
    }

    public List<ObjetivoEstrategicaBean> getListaObjetivoEstrategicaBean2() {
        return listaObjetivoEstrategicaBean2;
    }

    public void setListaObjetivoEstrategicaBean2(List<ObjetivoEstrategicaBean> listaObjetivoEstrategicaBean2) {
        this.listaObjetivoEstrategicaBean2 = listaObjetivoEstrategicaBean2;
    }

    public List<IndicadorObjEspecificoBean> getListIndicadorObjEspecificoBean() {
        return listIndicadorObjEspecificoBean;
    }

    public void setListIndicadorObjEspecificoBean(List<IndicadorObjEspecificoBean> listIndicadorObjEspecificoBean) {
        this.listIndicadorObjEspecificoBean = listIndicadorObjEspecificoBean;
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

    public List<IndicadorBean> getListIndicadorBean() {
        return listIndicadorBean;
    }

    public void setListIndicadorBean(List<IndicadorBean> listIndicadorBean) {
        this.listIndicadorBean = listIndicadorBean;
    }

    public LineaEstrategicaBean getLineaEstrategica() {
        if (lineaEstrategica == null) {
            lineaEstrategica = new LineaEstrategicaBean();
        }
        return lineaEstrategica;
    }

    public void setLineaEstrategica(LineaEstrategicaBean lineaEstrategica) {
        this.lineaEstrategica = lineaEstrategica;
    }

    public ObjetivoEstrategicaBean getObjetivoEstrategicoBean() {
        if (objetivoEstrategicoBean == null) {
            objetivoEstrategicoBean = new ObjetivoEstrategicaBean();
        }
        return objetivoEstrategicoBean;
    }

    public void setObjetivoEstrategicoBean(ObjetivoEstrategicaBean objetivoEstrategicoBean) {
        this.objetivoEstrategicoBean = objetivoEstrategicoBean;
    }

    public List<ObjetivoEstrategicaBean> getListaObjetivoEstrategicoBean() {
        if (listaObjetivoEstrategicoBean == null) {
            listaObjetivoEstrategicoBean = new ArrayList<>();
        }
        return listaObjetivoEstrategicoBean;
    }

    public void setListaObjetivoEstrategicoBean(List<ObjetivoEstrategicaBean> listaObjetivoEstrategicoBean) {
        this.listaObjetivoEstrategicoBean = listaObjetivoEstrategicoBean;
    }

    public List<PlanEstrategicoBean> getListaPlanEstrategicoFiltro() {
        if (listaPlanEstrategicoFiltro == null) {
            listaPlanEstrategicoFiltro = new ArrayList<>();
        }
        return listaPlanEstrategicoFiltro;
    }

    public void setListaPlanEstrategicoFiltro(List<PlanEstrategicoBean> listaPlanEstrategicoFiltro) {
        this.listaPlanEstrategicoFiltro = listaPlanEstrategicoFiltro;
    }

    public List<LineaEstrategicaBean> getListaLineaEstrategicaFiltro() {
        if (listaLineaEstrategicaFiltro == null) {
            listaLineaEstrategicaFiltro = new ArrayList<>();
        }
        return listaLineaEstrategicaFiltro;
    }

    public void setListaLineaEstrategicaFiltro(List<LineaEstrategicaBean> listaLineaEstrategicaFiltro) {
        this.listaLineaEstrategicaFiltro = listaLineaEstrategicaFiltro;
    }

    public List<LineaEstrategicaBean> getListaLineaEstPorPlan() {
        if (listaLineaEstPorPlan == null) {
            listaLineaEstPorPlan = new ArrayList<>();
        }
        return listaLineaEstPorPlan;
    }

    public void setListaLineaEstPorPlan(List<LineaEstrategicaBean> listaLineaEstPorPlan) {
        this.listaLineaEstPorPlan = listaLineaEstPorPlan;
    }

    public List<PlanEstrategicoBean> getListaPlanEstPorObjetivo() {
        if (listaPlanEstPorObjetivo == null) {
            listaPlanEstPorObjetivo = new ArrayList<>();
        }
        return listaPlanEstPorObjetivo;
    }

    public void setListaPlanEstPorObjetivo(List<PlanEstrategicoBean> listaPlanEstPorObjetivo) {
        this.listaPlanEstPorObjetivo = listaPlanEstPorObjetivo;

    }

    public List<ObjetivoEstrategicoDetBean> getListaObjetivoEstrategicaDetBean() {
        if (listaObjetivoEstrategicaDetBean == null) {
            listaObjetivoEstrategicaDetBean = new ArrayList<>();
        }
        return listaObjetivoEstrategicaDetBean;
    }

    public void setListaObjetivoEstrategicaDetBean(List<ObjetivoEstrategicoDetBean> listaObjetivoEstrategicaDetBean) {
        this.listaObjetivoEstrategicaDetBean = listaObjetivoEstrategicaDetBean;
    }

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicaDetBean() {
        if (objetivoEstrategicaDetBean == null) {
            objetivoEstrategicaDetBean = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicaDetBean;
    }

    public void setObjetivoEstrategicaDetBean(ObjetivoEstrategicoDetBean objetivoEstrategicaDetBean) {
        this.objetivoEstrategicaDetBean = objetivoEstrategicaDetBean;
    }

    public List<PlanEstrategicoBean> getListaPlanFiltro() {
        if (listaPlanFiltro == null) {
            listaPlanFiltro = new ArrayList<>();
        }
        return listaPlanFiltro;
    }

    public void setListaPlanFiltro(List<PlanEstrategicoBean> listaPlanFiltro) {
        this.listaPlanFiltro = listaPlanFiltro;
    }

    public List<LineaEstrategicaBean> getListaLineaPorPlan() {
        if (listaLineaPorPlan == null) {
            listaLineaPorPlan = new ArrayList<>();
        }
        return listaLineaPorPlan;
    }

    public void setListaLineaPorPlan(List<LineaEstrategicaBean> listaLineaPorPlan) {
        this.listaLineaPorPlan = listaLineaPorPlan;
    }

    public List<ObjetivoEstrategicaBean> getListaObjPorLinea() {
        if (listaObjPorLinea == null) {
            listaObjPorLinea = new ArrayList<>();
        }
        return listaObjPorLinea;
    }

    public void setListaObjPorLinea(List<ObjetivoEstrategicaBean> listaObjPorLinea) {
        this.listaObjPorLinea = listaObjPorLinea;
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

    public PeriodoBean getPeriodoBean() {
        if (periodoBean == null) {
            periodoBean = new PeriodoBean();
        }
        return periodoBean;
    }

    public void setPeriodoBean(PeriodoBean periodoBean) {
        this.periodoBean = periodoBean;
    }

    public List<PeriodoBean> getListaPeriodoBean() {
        if (listaPeriodoBean == null) {
            listaPeriodoBean = new ArrayList<>();
        }
        return listaPeriodoBean;
    }

    public void setListaPeriodoBean(List<PeriodoBean> listaPeriodoBean) {
        this.listaPeriodoBean = listaPeriodoBean;
    }

    public List<LineaEstrategicaBean> getLineaPeridoBean() {
        if (lineaPeridoBean == null) {
            lineaPeridoBean = new ArrayList<>();
        }
        return lineaPeridoBean;
    }

    public void setLineaPeridoBean(List<LineaEstrategicaBean> lineaPeridoBean) {
        this.lineaPeridoBean = lineaPeridoBean;
    }

    public List<ObjetivoEstrategicaBean> getLineaObjPeriodoBean() {
        if (lineaObjPeriodoBean == null) {
            lineaObjPeriodoBean = new ArrayList<>();
        }
        return lineaObjPeriodoBean;
    }

    public void setLineaObjPeriodoBean(List<ObjetivoEstrategicaBean> lineaObjPeriodoBean) {
        this.lineaObjPeriodoBean = lineaObjPeriodoBean;
    }

    public List<ObjetivoEstrategicoDetBean> getListaObjDetPeriodoBean() {
        if (listaObjDetPeriodoBean == null) {
            listaObjDetPeriodoBean = new ArrayList<>();
        }
        return listaObjDetPeriodoBean;
    }

    public void setListaObjDetPeriodoBean(List<ObjetivoEstrategicoDetBean> listaObjDetPeriodoBean) {
        this.listaObjDetPeriodoBean = listaObjDetPeriodoBean;
    }

    public List<PeriodoBean> getListaPeriodoFiltroBean() {
        if (listaPeriodoFiltroBean == null) {
            listaPeriodoFiltroBean = new ArrayList<>();
        }
        return listaPeriodoFiltroBean;
    }

    public void setListaPeriodoFiltroBean(List<PeriodoBean> listaPeriodoFiltroBean) {
        this.listaPeriodoFiltroBean = listaPeriodoFiltroBean;
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

    public List<ObjetivoEstrategicoDetBean> getListaObjDetPeriodoBean2() {
        if (listaObjDetPeriodoBean2 == null) {
            listaObjDetPeriodoBean2 = new ArrayList<>();
        }
        return listaObjDetPeriodoBean2;
    }

    public void setListaObjDetPeriodoBean2(List<ObjetivoEstrategicoDetBean> listaObjDetPeriodoBean2) {
        this.listaObjDetPeriodoBean2 = listaObjDetPeriodoBean2;
    }

    public ObjetivoEstrategicoDetBean getObjetivoEstrategicaDetBean2() {
        if (objetivoEstrategicaDetBean2 == null) {
            objetivoEstrategicaDetBean2 = new ObjetivoEstrategicoDetBean();
        }
        return objetivoEstrategicaDetBean2;
    }

    public void setObjetivoEstrategicaDetBean2(ObjetivoEstrategicoDetBean objetivoEstrategicaDetBean2) {
        this.objetivoEstrategicaDetBean2 = objetivoEstrategicaDetBean2;
    }

    public List<CodigoBean> getListaUniMedCodigo() {
        if (listaUniMedCodigo == null) {
            listaUniMedCodigo = new ArrayList<>();
        }
        return listaUniMedCodigo;
    }

    public void setListaUniMedCodigo(List<CodigoBean> listaUniMedCodigo) {
        this.listaUniMedCodigo = listaUniMedCodigo;
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

    public List<CodigoBean> getListaTipoIndicador() {
        if (listaTipoIndicador == null) {
            listaTipoIndicador = new ArrayList<>();
        }
        return listaTipoIndicador;
    }

    public void setListaTipoIndicador(List<CodigoBean> listaTipoIndicador) {
        this.listaTipoIndicador = listaTipoIndicador;
    }

    public List<ObjetivoEstrategicoDetBean> getListaObjPerBean() {
        if (listaObjPerBean == null) {
            listaObjPerBean = new ArrayList<>();
        }
        return listaObjPerBean;
    }

    public void setListaObjPerBean(List<ObjetivoEstrategicoDetBean> listaObjPerBean) {
        this.listaObjPerBean = listaObjPerBean;
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

    public List<PeriodoBean> getListaPeriodoAnios() {
        if (listaPeriodoAnios == null) {
            listaPeriodoAnios = new ArrayList<>();
        }
        return listaPeriodoAnios;
    }

    public void setListaPeriodoAnios(List<PeriodoBean> listaPeriodoAnios) {
        this.listaPeriodoAnios = listaPeriodoAnios;
    }

    public List<Object> getListaAniosPeriodo() {
        if (listaAniosPeriodo == null) {
            listaAniosPeriodo = new ArrayList<>();
        }
        return listaAniosPeriodo;
    }

    public void setListaAniosPeriodo(List<Object> listaAniosPeriodo) {
        this.listaAniosPeriodo = listaAniosPeriodo;
    }

    public Integer getFlg() {
        return flg;
    }

    public void setFlg(Integer flg) {
        this.flg = flg;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

//==================================Final Get y SEt======================================//
    //Metodos Plan EstratÃ©gico
    public void limpiarPlanEstrategico() {
        planEstrategicoBean = new PlanEstrategicoBean();
        listaAnos = new ArrayList<>();
        listaAnosRender = new ArrayList<>();
        cargarAno();
    }

    public void obtenerTodos() {
        try {
//            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
//            listaPlanEstrategicoBean = planEstrategicoService.obtenerTodos();
//            mapPlanEstrategicoBean = new HashMap<>();
//            for (PlanEstrategicoBean plan : listaPlanEstrategicoBean) {
//                mapPlanEstrategicoBean.put(plan.getNombrePlanEstrategico(), plan.getIdPlanEstrategico());
//            }
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            listaPlanEstrategicoBean = planEstrategicoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorFiltro() {
        try {
            Integer res = 0;
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            if (planEstrategicoFiltroBean.getAnioIni() != null) {
                planEstrategicoFiltroBean.setAnioIni(planEstrategicoFiltroBean.getAnioIni());
                res = 1;
            }
            if (planEstrategicoFiltroBean.getAnioFin() != null) {
                planEstrategicoFiltroBean.setAnioFin(planEstrategicoFiltroBean.getAnioFin());
                res = 1;
            }
            if (planEstrategicoFiltroBean.getNombre() != null) {
                planEstrategicoFiltroBean.setNombre(planEstrategicoFiltroBean.getNombre().toUpperCase().trim());
                res = 1;
            }
            if (res == 1) {
                listaPlanEstrategicoBean = planEstrategicoService.obtenerPorFiltro(planEstrategicoFiltroBean);
                if (listaPlanEstrategicoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPlanEstrategicoBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPlanEstrategicoBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
//            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
//            planEstrategicoBean = planEstrategicoService.buscarPorId(idPlanEstrategico);
//            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
//            planEstrategicoBean.setAnoIniciocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnoInicio())));
//            planEstrategicoBean.setAnoTerminocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnoTermino())));

            planEstrategicoBean = (PlanEstrategicoBean) object;
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            planEstrategicoBean = planEstrategicoService.buscarPorId(getPlanEstrategicoBean().getIdPlanEstrategico());
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            planEstrategicoBean.setAnioIniciocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioInicio())));
            planEstrategicoBean.setAnioTerminocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioTermino())));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarPlanEstrategico() {
        try {
            if (planEstrategicoBean.getIdPlanEstrategico() == null) {
                insertarPlanEstrategico();
            } else {
                modificarPlanEstrategico();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarPlanEstrategico() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                SimpleDateFormat formato2 = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
                planEstrategicoBean.setCreaPor(user.getUsuario());
                planEstrategicoBean.setCreaFecha(formato.parse(date));
                planEstrategicoBean.setAnioInicio(formato2.parse(String.valueOf(planEstrategicoBean.getAnioIniciocom())));
                planEstrategicoBean.setAnioTermino(formato2.parse(String.valueOf(planEstrategicoBean.getAnioTerminocom())));
                planEstrategicoBean.setUnidadNegocioBean(user.getPersonalBean().getUnidadNegocioBean());
                planEstrategicoService.insertar(planEstrategicoBean);
                listaPlanEstrategicoBean = planEstrategicoService.obtenerTodosUniNeg(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                obtenerCodigo();
                limpiarPlanEstrategico();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPlanEstrategico() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                obtenerCodigo();
                PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                planEstrategicoBean.setAnioInicio(formato.parse(String.valueOf(planEstrategicoBean.getAnioIni())));
                planEstrategicoBean.setAnioTermino(formato.parse(String.valueOf(planEstrategicoBean.getAnioFin())));
                planEstrategicoBean.setModiPor(user.getUsuario());
                planEstrategicoService.actualizar(planEstrategicoBean);
//                listaPlanEstrategicoBean = planEstrategicoService.obtenerTodos();
                obtenerPorFiltro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                obtenerCodigo();
                limpiarPlanEstrategico();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPlanEstrategico() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
                planEstrategicoService.eliminar(planEstrategicoBean.getIdPlanEstrategico());
                listaPlanEstrategicoBean = planEstrategicoService.obtenerTodosUniNeg(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPlanEstrategico();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerPlanId(Object object) {
        try {
            planEstrategicoBean = (PlanEstrategicoBean) object;
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            planEstrategicoBean = planEstrategicoService.obtenerPlanId(planEstrategicoBean.getIdPlanEstrategico());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAno() {
        try {
            int a = 2040;
            int b = 2014;
            listaAnos = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnos.add(i);
            }

            listaAnosRender = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosRender.add(i);
            }
        } catch (Exception e) {
        }
    }

    public final void obtenerCodigo() {
        try {
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            String baseCodigo = "PE - ";
            if (planEstrategicoBean == null) {
                planEstrategicoBean = new PlanEstrategicoBean();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                planEstrategicoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            }
            String codigo = planEstrategicoService.obtenerUltimoCodigo(planEstrategicoBean);
            Integer cod = Integer.parseInt(codigo) + 1;
            String codigo2 = String.format("%03d", cod);
//            planEstrategicoBean.setCodigoPlanEstrategico(baseCodigo.concat(codigo2));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public final void obtenerPersonas() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaPersonalBean = usuarioService.obtenerPersonalFiltro(null);
            mapPersonaBean = new LinkedHashMap<String, Integer>();
            for (PersonalBean persona : listaPersonalBean) {
                mapPersonaBean.put(persona.getNombre() + " " + persona.getApepat() + " " + persona.getApemat(), new Integer(persona.getIdPersonal()));
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public final void obtenerUnidadNegocio() {
//        try {
//            UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
//            listUnidadNegocioBean = unidadNegocioService.obtenerTodos();
//            mapUnidadNegocio = new LinkedHashMap<String, String>();
//            for (UnidadNegocioBean unidad : listUnidadNegocioBean) {
//                mapUnidadNegocio.put(unidad.getNombreUniNeg(), unidad.getUniNeg());
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void actualizarLista2(AjaxBehaviorEvent event) {
        try {
            int a = 0;
            if (planEstrategicoBean.getAnioIni() == null) {
                a = planEstrategicoBean.getAnioIniciocom();
                System.out.println("Anio Inicio Nulo");
            } else {
                a = planEstrategicoBean.getAnioIni();
            }
            int b = 2040;
            listaAnosRender = new ArrayList<>();
            System.out.println(a);
            System.out.println(b);
            for (int i = a; i <= b; i++) {
                listaAnosRender.add(i);
            }
            System.out.println("Ejecuto Cambio");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizarLista() {
        try {
            int a = 0;
            if (planEstrategicoBean.getAnioIni() == null) {
                a = planEstrategicoBean.getAnioIniciocom();
                System.out.println("Anio Inicio Nulo");
            } else {
                a = planEstrategicoBean.getAnioIni();
            }

            if (planEstrategicoBean.getAnioFin() == null) {
                planEstrategicoBean.getAnioTerminocom();
            }

            int b = 2040;
            listaAnosRender = new ArrayList<>();
            System.out.println(a);
            System.out.println(b);
            for (int i = a + 1; i <= b; i++) {
                listaAnosRender.add(i);
            }
            for (int j = 0; j < listaAnosRender.size(); j++) {
                System.out.println(listaAnosRender.get(1));
                break;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            planEstrategicoBean.setCollapsed(false);
            planEstrategicoBean = (PlanEstrategicoBean) event.getObject();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            planEstrategicoBean.setAnioIniciocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioInicio())));
            planEstrategicoBean.setAnioTerminocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioTermino())));
            planEstrategicoFiltroBean.setIdPlanEstrategico(planEstrategicoFiltroBean.getIdPlanEstrategico());
            planEstrategicoBean.setNombre(planEstrategicoBean.getNombre());
            planEstrategicoBean.setIdPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico());
            lineaEstrategicaBean.setPlanEstrategicoBean(lineaEstrategicaBean.getPlanEstrategicoBean());
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            planEstrategicoBean = planEstrategicoService.obtenerPorId(planEstrategicoBean);
//            lineaEstrategicaBean = (LineaEstrategicaBean) event.getObject();
//            objetivoEstrategicoBean = (ObjetivoEstrategicaBean) event.getObject();

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            listaObjetivoEstrategicoBean = new ArrayList<>();
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            getListaObjetivoEstrategicoBean();
            listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaLineaEstrategicaBean = new ArrayList<>();
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            getListaLineaEstrategicaBean();
            listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaObjetivoEstrategicaDetBean = new ArrayList<>();
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            getListaObjetivoEstrategicaDetBean();
            listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaPeriodoFiltroBean = new ArrayList<>();
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            getListaPeriodoFiltroBean();
            listaPeriodoFiltroBean = periodoService.obtenerPorPlan(planEstrategicoBean.getIdPlanEstrategico());

            getListaObjPorLinea();
            listaObjPorLinea = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaLineaEstPorPlan();
            listaLineaEstPorPlan = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getLineaPeridoBean();
            lineaPeridoBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaObjPerBean();
            listaObjPerBean = objetivoEstrategicoDetService.obtenerDetPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            cargarAnio();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorIdPlan(Object obj) {
        try {
            planEstrategicoBean.setCollapsed(false);
            planEstrategicoBean = (PlanEstrategicoBean) obj;
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            planEstrategicoBean.setAnioIniciocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioInicio())));
            planEstrategicoBean.setAnioTerminocom(Integer.parseInt(formato.format(planEstrategicoBean.getAnioTermino())));
            planEstrategicoFiltroBean.setIdPlanEstrategico(planEstrategicoFiltroBean.getIdPlanEstrategico());
            planEstrategicoBean.setNombre(planEstrategicoBean.getNombre());
            planEstrategicoBean.setIdPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico());
            lineaEstrategicaBean.setPlanEstrategicoBean(lineaEstrategicaBean.getPlanEstrategicoBean());
            PlanEstrategicoService planEstrategicoService = BeanFactory.getPlanEstrategicoService();
            planEstrategicoBean = planEstrategicoService.obtenerPorId(planEstrategicoBean);

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            listaObjetivoEstrategicoBean = new ArrayList<>();
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            getListaObjetivoEstrategicoBean();
            listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaLineaEstrategicaBean = new ArrayList<>();
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            getListaLineaEstrategicaBean();
            listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaObjetivoEstrategicaDetBean = new ArrayList<>();
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            getListaObjetivoEstrategicaDetBean();
            listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaPeriodoFiltroBean = new ArrayList<>();
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            getListaPeriodoFiltroBean();
            listaPeriodoFiltroBean = periodoService.obtenerPorPlan(planEstrategicoBean.getIdPlanEstrategico());

            getListaObjPorLinea();
            listaObjPorLinea = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaLineaEstPorPlan();
            listaLineaEstPorPlan = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getLineaPeridoBean();
            lineaPeridoBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaObjPerBean();
            listaObjPerBean = objetivoEstrategicoDetService.obtenerDetPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            cargarAnio();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Linea Estrategica
    public void limpiarLineaEstrategico() {
        lineaEstrategicaBean = new LineaEstrategicaBean();
    }

    public void obtenerLineaEstrategica() {
        try {
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaEstrategica();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerPorIdLineaEstrategica(int id) {//Obtener Por el Objeto
//        try {
//            PlanEstrategicoService lineaEstrategicaService = BeanFactory.getPlanEstrategicoService();
//            lineaEstrategicaBean = lineaEstrategicaService.obtenerPorId(id);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //====================================Linea Estrategica======================================//
    public void obtenerLineaPorId(Object object) {//Faltante Linea estrategica Service
        try {
            lineaEstrategicaBean = (LineaEstrategicaBean) object;
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            lineaEstrategicaBean = lineaEstrategicaService.obtenerPorId(getLineaEstrategicaBean().getIdLinea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarLineaEstrategico() {
        if (lineaEstrategicaBean.getIdLinea() == null) {
            insertarLineaEstrategico();
        } else {
            modificarLineaEstrategico();
        }
    }

    public String insertarLineaEstrategico() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                lineaEstrategicaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                lineaEstrategicaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                lineaEstrategicaBean.getPlanEstrategicoBean().setIdPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico());
                lineaEstrategicaService.insertarLineaEstrategica(lineaEstrategicaBean);
                limpiarLineaEstrategico();
                listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                lineaPeridoBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarLineaEstrategico() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                lineaEstrategicaService.actualizarLineaEstrategica(lineaEstrategicaBean);
                lineaEstrategicaBean.setModiPor(beanUsuarioSesion.getUsuario());
                listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarLineaEstrategico();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarLineaEstrategica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
                lineaEstrategicaService.eliminarLineaEstrategica(lineaEstrategicaBean.getIdLinea());
                listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String cambiarEstadoCajero() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PlanEstrategicoService lineaEstrategicaService = BeanFactory.getPlanEstrategicoService();
                if (lineaEstrategicaBean.getStatus().equals("Activo")) {
                    lineaEstrategicaBean.setEstado(false);
                } else {
                    lineaEstrategicaBean.setEstado(true);
                }
                lineaEstrategicaService.cambiarEstado(lineaEstrategicaBean);
                listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaEstrategica();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelect2(SelectEvent event) {
        try {
            lineaEstrategicaBean = (LineaEstrategicaBean) event.getObject();
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            lineaEstrategicaBean = lineaEstrategicaService.obtenerPorId(lineaEstrategicaBean.getIdLinea());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerLinea(Object linea) {
        try {
            lineaEstrategicaBean = (LineaEstrategicaBean) linea;
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            lineaEstrategicaBean = lineaEstrategicaService.obtenerPorId(lineaEstrategicaBean.getIdLinea());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
                if (lineaEstrategicaBean.getEstado() == false && lineaEstrategicaBean.getStatus().equals("Activo")) {
                    lineaEstrategicaBean.setStatus("Inactivo");
                } else {
                    if (lineaEstrategicaBean.getEstado() == false && lineaEstrategicaBean.getStatus().equals("Inactivo")) {
                        lineaEstrategicaBean.setStatus("Activo");
                    } else {
                        System.out.println("-------------");
                    }
                }
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                lineaEstrategicaService.cambiarEstado(lineaEstrategicaBean);
                listaLineaEstrategicaBean = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public final void obtenerCodigoLinea() {
        try {
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            String baseCodigo = "LE - ";
            if (lineaEstrategicaBean == null) {
                lineaEstrategicaBean = new LineaEstrategicaBean();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                lineaEstrategicaBean.getPlanEstrategicoBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            }
            String codigo = lineaEstrategicaService.obtenerUltimoCodigo(lineaEstrategicaBean.getPlanEstrategicoBean().getUnidadNegocioBean().getUniNeg());
            Integer cod = Integer.parseInt(codigo) + 1;
            String codigo2 = String.format("%03d", cod);
//            lineaEstrategicaBean.setCodigoLinea(baseCodigo.concat(codigo2));

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerLineaPorPlanEstrategico() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaLineaEstrategicaFiltro = new ArrayList<>();
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            listaLineaEstrategicaFiltro = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(objetivoEstrategicaBean.getPlanEstrategicoBean().getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect3(SelectEvent event) {
        try {
            objetivoEstrategicaBean = (ObjetivoEstrategicaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect4(SelectEvent event) {
        try {
            objetivoEspecificoBean = (ObjetivoEspecificoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectIndicador(SelectEvent event) {
        try {
            indicadorFiltroBean = (IndicadorBean) event.getObject();
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
            objetivoEstrategicaDetBean.setIndicadorBean(indicadorFiltroBean);
            objetivoEstrategicaDetBean.getCodigoBean().setIdCodigo(indicadorFiltroBean.getCodigoTipoValor().getIdCodigo());
            objetivoEstrategicaDetBean.setMeta(indicadorFiltroBean.getMeta());
            periodoBean.setIndicadorBean(indicadorFiltroBean);
            System.out.println(periodoBean.getIndicadorBean().getIdIndicador());
            System.out.println(indicadorFiltroBean.getMeta());
            System.out.println(indicadorFiltroBean.getCodigoTipoValor().getIdCodigo());
            System.out.println("Llego");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //================================Objetivo Estrategico==========================================
    public void obtenerTodosObjetivoEstrategico() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoEstrategico(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerObjetivoPorId(Object object) {//Faltante Linea estrategica Service
        try {
            objetivoEstrategicaBean = (ObjetivoEstrategicaBean) object;
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            objetivoEstrategicaBean = objetivoEstrategicoService.obtenerPorId(objetivoEstrategicaBean.getIdObjEstrategico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerObjetivoPorId2(Object object) {
        try {
            objetivoEstrategicaBean = (ObjetivoEstrategicaBean) object;
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            objetivoEstrategicaBean = objetivoEstrategicoService.obtenerPorId(objetivoEstrategicaBean.getIdObjEstrategico());
            objetivoEstrategicaDetBean.setPlanEstrategicoBean(objetivoEstrategicaBean.getPlanEstrategicoBean());
            objetivoEstrategicaDetBean.setLineaEstrategicaBean(objetivoEstrategicaBean.getLineaEstrategicaBean());
            objetivoEstrategicaDetBean.setObjetivoEstrategicaBean(objetivoEstrategicaBean);
            System.out.println(objetivoEstrategicaDetBean.getLineaEstrategicaBean().getIdLinea());
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetalles(objetivoEstrategicaBean.getIdObjEstrategico());
            cargarAnio();
            listaAniosPeriodo = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarObjetivoEstrategico() {
        try {
            objetivoEstrategicaBean = new ObjetivoEstrategicaBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarObjetivoEstrategico() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objetivoEstrategicaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                objetivoEstrategicaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                objetivoEstrategicaBean.setPlanEstrategicoBean(planEstrategicoBean);
                objetivoEstrategicoService.insertarObjetivoEstrategico(objetivoEstrategicaBean);
                listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                lineaObjPeriodoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarObjetivoEstrategico();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarObjetivoEstrategico() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objetivoEstrategicoService.modificarObjetivoEstrategico(objetivoEstrategicaBean);
                objetivoEstrategicaBean.setModiPor(beanUsuarioSesion.getUsuario());
                listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarObjetivoEstrategico();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarObjetivoEstrategico() {
        try {
            if (objetivoEstrategicaBean.getIdObjEstrategico() == null) {
                insertarObjetivoEstrategico();
            } else {
                modificarObjetivoEstrategico();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void obtenerCodObjetivoEst() {
        try {
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            String baseCodigo = "OE - ";
            if (objetivoEstrategicaBean == null) {
                objetivoEstrategicoBean = new ObjetivoEstrategicaBean();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objetivoEstrategicoBean.getLineaEstrategicaBean().getPlanEstrategicoBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            }
            String codigo = objetivoEstrategicoService.obtenerCodObjetivoEstrategivo(objetivoEstrategicoBean.getLineaEstrategicaBean().getPlanEstrategicoBean().getUnidadNegocioBean().getUniNeg());
            Integer cod = Integer.parseInt(codigo) + 1;
            String codigo2 = String.format("%03d", cod);
//            objetivoEstrategicoBean.setCodigoObjEstrategico(baseCodigo.concat(codigo2));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminarObjetivoEstrategico() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
                objetivoEstrategicoService.eliminarObjetivoEstrategico(objetivoEstrategicaBean.getIdObjEstrategico());
                listaObjetivoEstrategicoBean = objetivoEstrategicoService.obtenerObjetivoPorPlan(planEstrategicoBean.getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    //================================Final==========================================
    //==================================Obj Estrategico Det===========================================//
    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            Integer inicio = planEstrategicoBean.getAnioIni();
            Integer fin = planEstrategicoBean.getAnioFin();
            listaAnios = new ArrayList<>();
            for (int i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarObjDet() {
        try {
            objetivoEstrategicaDetBean = new ObjetivoEstrategicoDetBean();
            objetivoEstrategicaDetBean.setPlanEstrategicoBean(objetivoEstrategicaBean.getPlanEstrategicoBean());
            objetivoEstrategicaDetBean.setLineaEstrategicaBean(objetivoEstrategicaBean.getLineaEstrategicaBean());
            objetivoEstrategicaDetBean.setObjetivoEstrategicaBean(objetivoEstrategicaBean);
            periodoBean = new PeriodoBean();
            listaAniosPeriodo = new ArrayList<>();
            cargarAnio();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTodosObjDet() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetPorPlan(objetivoEstrategicaDetBean.getPlanEstrategicoBean().getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarObjDet() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objetivoEstrategicaDetBean.setCreaPor(beanUsuarioSesion.getUsuario());
                objetivoEstrategicaDetBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                objetivoEstrategicaDetBean.setPlanEstrategicoBean(planEstrategicoBean);

                //Obteniendo Codigo
                CodigoBean codigoBean = new CodigoBean();
                CodigoBean codigo = new CodigoBean();
                Integer meta = 0;
                CodigoService codigoService = BeanFactory.getCodigoService();
                codigoBean = codigoService.obtenerPorCodigo(new CodigoBean(15701, "Ordinal", new TipoCodigoBean(MaristaConstantes.TIP_VALOR)));
                codigo = codigoService.obtenerPorCodigo(new CodigoBean(15702, "Porcentual", new TipoCodigoBean(MaristaConstantes.TIP_VALOR)));
                DecimalFormat RedondearADos = new DecimalFormat("####.##");
                //Validando Codigo
//                if (objetivoEstrategicaDetBean.getCodigoBean().getIdCodigo().equals(codigoBean.getIdCodigo())) {
//                    meta = objetivoEstrategicaDetBean.getMeta();
//                }
//                if (objetivoEstrategicaDetBean.getCodigoBean().getIdCodigo().equals(codigo.getIdCodigo())) {
//                    meta = objetivoEstrategicaDetBean.getMeta() / 100;
//                }
                System.out.println(">>>>" + meta);
                objetivoEstrategicoDetService.insertarObjDet(objetivoEstrategicaDetBean);
//                listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetPorPlan(planEstrategicoBean.getIdPlanEstrategico());
                listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetalles(objetivoEstrategicaBean.getIdObjEstrategico());

                //Insertando en Periodo
                ObjetivoEstrategicoDetBean objDet = new ObjetivoEstrategicoDetBean();
                objDet = objetivoEstrategicoDetService.obetenerUltimoDet(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                PeriodoService periodoService = BeanFactory.getPeriodoService();
//                System.out.println(periodoBean.getAnio());
                periodoBean.getObjetivoEstrategicoDetBean().setIdObjEstrategicoDet(objDet.getIdObjEstrategicoDet());
                periodoBean.getObjetivoEstrategicaBean().setIdObjEstrategico(objDet.getObjetivoEstrategicaBean().getIdObjEstrategico());
                periodoBean.getUniNeg().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                periodoBean.getPlanEstrategicoBean().setIdPlanEstrategico(objDet.getPlanEstrategicoBean().getIdPlanEstrategico());
                periodoBean.getLineaEstrategicaBean().setIdLinea(objDet.getLineaEstrategicaBean().getIdLinea());
                periodoBean.setIdIndicador(objDet.getIndicadorBean().getIdIndicador());
//                periodoBean.setAnio(periodoBean.getAnio()); 
                periodoBean.getCodigoValor().setIdCodigo(objDet.getCodigoBean().getIdCodigo());
                periodoBean.setMeta(objDet.getMeta());
                periodoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                periodoService.insertarPeriodoAnio(periodoBean, listaAniosPeriodo);
                limpiarObjDet();
                cargarAnio();
                listaAniosPeriodo = new ArrayList<>();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarObjDet() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                System.out.println(objetivoEstrategicaDetBean.getIdObjEstrategicoDet());
                ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                objetivoEstrategicaDetBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                objetivoEstrategicaDetBean.setModiPor(beanUsuarioSesion.getUsuario());
                objetivoEstrategicoDetService.modificarObjDet(objetivoEstrategicaDetBean);
                listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetalles(objetivoEstrategicaBean.getIdObjEstrategico());

                //Obteniendo Periodo
                PeriodoService periodoService = BeanFactory.getPeriodoService();
                PeriodoBean periodo = new PeriodoBean();
//                periodo = periodoService.obtenerPorDet(objetivoEstrategicaDetBean.getIdObjEstrategicoDet(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                List<PeriodoBean> listaPeriodo = new ArrayList<>();
                listaPeriodo = periodoService.obtenerPorDetalles(objetivoEstrategicaDetBean.getIdObjEstrategicoDet(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                for (PeriodoBean periodoAnio : listaPeriodo) {
                    periodoAnio.setMeta(objetivoEstrategicaDetBean.getMeta());
                    periodoAnio.getIndicadorBean().setIdIndicador(objetivoEstrategicaDetBean.getIndicadorBean().getIdIndicador());
                    periodoAnio.getCodigoValor().setIdCodigo(objetivoEstrategicaDetBean.getCodigoBean().getIdCodigo());
                    periodoAnio.setModiPor(beanUsuarioSesion.getUsuario());
                    periodoService.modidificarPeriodoDeta(periodoAnio, listaAniosPeriodo, flg);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarObjDet();
                cargarAnio();
                listaAniosPeriodo = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarObjDet() {
        try {
            if (objetivoEstrategicaDetBean.getIdObjEstrategicoDet() == null) {
                insertarObjDet();
            } else {
                modificarObjDet();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerListaAnio() {
        try {
            if (periodoBean.getAnio() != null) {
                if (listaAniosPeriodo == null) {
                    listaAniosPeriodo = new ArrayList<>();
                    listaAniosPeriodo.add(periodoBean.getAnio());
                } else {
                    if (listaAniosPeriodo != null) {
                        listaAniosPeriodo.add(periodoBean.getAnio());
                    }
                }
                if (listaAnios.size() > 0) {
                    for (Integer i = 0; i < listaAnios.size(); i++) {
                        for (Integer j = 0; j < listaAniosPeriodo.size(); j++) {
                            if (listaAnios.get(i).equals(listaAniosPeriodo.get(j))) {
                                listaAnios.remove(listaAnios.get(i));
                            }
                        }
                    }
                } else {
                    System.out.println("Lista vacia");
                }
                flg = 1;
                System.out.println("anios" + listaAnios.size());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarListaAnio(Integer anio) {
        try {
            periodoBean.setAnio(anio);
            listaAniosPeriodo.remove(periodoBean.getAnio());
            for (Integer i = listaAnios.size() - 1; i >= 0; i--) {
                if (periodoBean.getAnio() < listaAnios.get(i)) {
                    for (int j = 0; j < listaAnios.size(); j++) {
                        if (listaAnios.get(j).equals(periodoBean.getAnio())) {
                            listaAnios.remove(listaAnios.get(j));
                        }
                    }
                    listaAnios.add(i, periodoBean.getAnio());
                }
            }
            flg = 0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarBean() {
        objetivoEstrategicaDetBean = new ObjetivoEstrategicoDetBean();
    }

    public void rowSelectObjDet(SelectEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetBean deta = (ObjetivoEstrategicoDetBean) event.getObject();
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
//            objetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerPorId(deta.getIdObjEstrategicoDet()); 
//            objetivoEstrategicaDetBean.setResponsable(objetivoEstrategicaDetBean.getResponsable());
//            objetivoEstrategicaDetBean.setMeta(objetivoEstrategicaDetBean.getMeta());
//            objetivoEstrategicaDetBean.setIndicadorBean(objetivoEstrategicaDetBean.getIndicadorBean());
//            objetivoEstrategicaDetBean.setLineaEstrategicaBean(objetivoEstrategicaDetBean.getLineaEstrategicaBean());
//            objetivoEstrategicaDetBean.setPlanEstrategicoBean(objetivoEstrategicaDetBean.getPlanEstrategicoBean());
//            objetivoEstrategicaDetBean.setObjetivoEstrategicaBean(objetivoEstrategicaDetBean.getObjetivoEstrategicaBean());
//            System.out.println(objetivoEstrategicaDetBean.getIdObjEstrategicoDet());
//            System.out.println(objetivoEstrategicaDetBean.getIdObjEstrategicoDet());
            objetivoEstrategicaDetBean.getIndicadorBean().getCodigoTipoValor().setIdCodigo(deta.getIndicadorBean().getCodigoTipoValor().getIdCodigo());
            objetivoEstrategicaDetBean.getIndicadorBean().getCodigoTipoValor().setValor(deta.getIndicadorBean().getCodigoTipoValor().getValor());
            cargarAnio();
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            List<PeriodoBean> listaPeriodo = new ArrayList<>();
            listaPeriodo = periodoService.obtenerPorDetalles(deta.getIdObjEstrategicoDet(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaAniosPeriodo = new ArrayList<>();
            for (PeriodoBean periodo : listaPeriodo) {
                if (deta.getIdObjEstrategicoDet().equals(periodo.getObjetivoEstrategicoDetBean().getIdObjEstrategicoDet())) {
                    listaAniosPeriodo.add(periodo.getAnio());
                    for (int i = 0; i < listaAnios.size(); i++) {
                        for (int j = 0; j < listaAniosPeriodo.size(); j++) {
                            if (listaAnios.get(i).equals(listaAniosPeriodo.get(j))) {
                                listaAnios.remove(listaAnios.get(i));
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminarObjDet() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
                objetivoEstrategicoDetService.eliminarObjDet(objetivoEstrategicaDetBean.getIdObjEstrategicoDet());
                listaObjetivoEstrategicaDetBean = objetivoEstrategicoDetService.obtenerDetalles(objetivoEstrategicaBean.getIdObjEstrategico());

                //Eliminando Periodo
                PeriodoService periodoService = BeanFactory.getPeriodoService();
                periodoService.eliminarPeriodo(periodoBean);

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerObjDetPorId(Object object) {
        try {
            objetivoEstrategicaDetBean = (ObjetivoEstrategicoDetBean) object;
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            objetivoEstrategicoDetService.obtenerPorId(objetivoEstrategicaDetBean.getIdObjEstrategicoDet());

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            periodoBean = periodoService.obtenerPorDet(objetivoEstrategicaDetBean.getIdObjEstrategicoDet(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerLineaPorPlan() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaLineaEstPorPlan = new ArrayList<>();
            LineaEstrategicaService lineaEstrategicaService = BeanFactory.getLineaEstrategicaService();
            listaLineaEstPorPlan = lineaEstrategicaService.obtenerLineaPorPlanEstrategico(objetivoEstrategicaDetBean.getPlanEstrategicoBean().getIdPlanEstrategico(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerObjPorLinea() {
        try {
            listaObjPorLinea = new ArrayList<>();
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            listaObjPorLinea = objetivoEstrategicoService.obtenerObjPorLinea(objetivoEstrategicaDetBean.getLineaEstrategicaBean().getIdLinea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPlan(Object object) {
        try {
            PlanEstrategicoBean plan = (PlanEstrategicoBean) object;
            lineaEstrategicaBean.setPlanEstrategicoBean(plan);
            objetivoEstrategicaDetBean.setPlanEstrategicoBean(plan);
            objetivoEstrategicaBean.setPlanEstrategicoBean(plan);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//=============================================================================//
//=================================Periodo===========================================//   
    public void obtenerPeriodo() {
        try {
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            listaPeriodoBean = periodoService.obtenerPeriodo();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPeriodo() {
        try {
            periodoBean = new PeriodoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorPlan() {
        try {
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            listaPeriodoFiltroBean = periodoService.obtenerPorPlan(periodoBean.getPlanEstrategicoBean().getIdPlanEstrategico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPeriodo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PeriodoService periodoService = BeanFactory.getPeriodoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                periodoBean.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                periodoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                periodoBean.setPlanEstrategicoBean(planEstrategicoBean);
                periodoService.insertarPeriodo(periodoBean);
                listaPeriodoFiltroBean = periodoService.obtenerPorPlan(periodoBean.getPlanEstrategicoBean().getIdPlanEstrategico());
                limpiarPeriodo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarPeriodo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PeriodoService periodoService = BeanFactory.getPeriodoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                periodoBean.setModiPor(beanUsuarioSesion.getUsuario());
                periodoService.modificarPeriodo(periodoBean);
                listaPeriodoFiltroBean = periodoService.obtenerPorPlan(planEstrategicoBean.getIdPlanEstrategico());
                limpiarPeriodo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarPeriodo() {
        try {
            if (periodoBean.getIdPeriodo() == null) {
                insertarPeriodo();
            } else {
                modificarPeriodo();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminarPeriodo() {
        String pagina = null;
        try {
            if (pagina == null) {
                PeriodoService periodoService = BeanFactory.getPeriodoService();
                periodoService.eliminarPeriodo(periodoBean);
                listaPeriodoFiltroBean = periodoService.obtenerPorPlan(planEstrategicoBean.getIdPlanEstrategico());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerPorIdPeriodo(Object objeto) {
        try {
            periodoBean = (PeriodoBean) objeto;
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            periodoService.obtenerPorId(periodoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPeriodoPorId(Object object) {
        try {
            periodoBean = (PeriodoBean) object;
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            periodoService.obtenerPorId(getPeriodoBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectPeriodo(SelectEvent event) {
        try {
            PeriodoBean per = (PeriodoBean) event.getObject();
            PeriodoService periodoService = BeanFactory.getPeriodoService();
            periodoBean = periodoService.obtenerPeriodoPorId(per.getIdPeriodo());
//            periodoBean.setCodigoValor(per.getCodigoValor());
//            periodoBean.setPlanEstrategicoBean(per.getPlanEstrategicoBean());
//            periodoBean.setLineaEstrategicaBean(per.getLineaEstrategicaBean());
//            periodoBean.setObjetivoEstrategicaBean(per.getObjetivoEstrategicaBean());
//            periodoBean.setObjetivoEstrategicoDetBean(per.getObjetivoEstrategicoDetBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerIndicadorPeriodo(Object object) {
        try {
            IndicadorBean ind = (IndicadorBean) object;
            periodoBean.setIndicadorBean(ind);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerObjPorLineaPeriodo() {
        try {
            lineaObjPeriodoBean = new ArrayList<>();
            ObjetivoEstrategicoService objetivoEstrategicoService = BeanFactory.getObjetivoEstrategicoService();
            lineaObjPeriodoBean = objetivoEstrategicoService.obtenerObjPorLinea(periodoBean.getLineaEstrategicaBean().getIdLinea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetObjPeriodo() {
        try {
            listaObjDetPeriodoBean2 = new ArrayList<>();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            listaObjDetPeriodoBean2 = objetivoEstrategicoDetService.obtenerDetPorObj(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), periodoBean.getObjetivoEstrategicaBean().getIdObjEstrategico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerDetallesObjPeriodo(Object object) {
        try {
            ObjetivoEstrategicoDetBean det = (ObjetivoEstrategicoDetBean) object;
            ObjetivoEstrategicoDetService objetivoEstrategicoDetService = BeanFactory.getObjetivoEstrategicoDetService();
            objetivoEstrategicaDetBean2 = objetivoEstrategicoDetService.obtenerPorId(det.getIdObjEstrategicoDet());
            System.out.println(objetivoEstrategicaDetBean2.getIdObjEstrategicoDet());
            System.out.println(objetivoEstrategicaDetBean2.getIndicadorBean().getIdIndicador());
            System.out.println(objetivoEstrategicaDetBean2.getMeta());
            periodoBean.setMeta(objetivoEstrategicaDetBean2.getMeta());
            periodoBean.getCodigoValor().setIdCodigo(objetivoEstrategicaDetBean2.getCodigoBean().getIdCodigo());
            periodoBean.setObjetivoEstrategicoDetBean(objetivoEstrategicaDetBean2);
            periodoBean.getObjetivoEstrategicoDetBean().setIndicadorBean(objetivoEstrategicaDetBean2.getIndicadorBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //=================================Final Periodo===========================================//
    //===============================  Indicador   ===========================================//
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
                listaIndicadorBean = indicadorService.obtenerTodos();
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

    public void limpiarIndicadorBean() {
        try {
            indicadorBean = new IndicadorBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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
}
