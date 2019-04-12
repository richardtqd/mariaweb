/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import jdk.nashorn.internal.objects.annotations.Constructor;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.MeterGaugeChartModel;
import pe.marista.sigma.bean.AnioHistBean;
import pe.marista.sigma.bean.CantidadBean;
import pe.marista.sigma.bean.Cargos;
import pe.marista.sigma.bean.CargosEvaluadorBean;
import pe.marista.sigma.bean.DatosPersonalBean;
import pe.marista.sigma.bean.DetalleIndicadorBean;
import pe.marista.sigma.bean.DetalleNivelCargo;
import pe.marista.sigma.bean.DirectoresEDBean;
import pe.marista.sigma.bean.DirectoresPromBean;
import pe.marista.sigma.bean.ED_DetalleComObservables;
import pe.marista.sigma.bean.ED_DetalleCompetencias;
import pe.marista.sigma.bean.ED_DetallexNivel;
import pe.marista.sigma.bean.ED_HistoricoBean;
import pe.marista.sigma.bean.ED_IniPriSEC;
import pe.marista.sigma.bean.ED_PersonalBean;
import pe.marista.sigma.bean.ED_TipoNiveles;
import pe.marista.sigma.bean.EstadoBean;
import pe.marista.sigma.bean.EvaRepIndividualBean;
import pe.marista.sigma.bean.EvaRepIndividualPlanBean;
import pe.marista.sigma.bean.EvaluacionBean;
import pe.marista.sigma.bean.EvaluacionDesempenoBean;
import pe.marista.sigma.bean.EvaluadoBean;
import pe.marista.sigma.bean.FichaEntrevista;
import pe.marista.sigma.bean.FichaRetroConsolidado;
import pe.marista.sigma.bean.FichaRetroalimentacionBean;
import pe.marista.sigma.bean.HabilitaEncuestaBean;
import pe.marista.sigma.bean.HistoricoEDBean;
import pe.marista.sigma.bean.HistoricoEDListBean;
import pe.marista.sigma.bean.IndicadoresBean;
import pe.marista.sigma.bean.IndicadoresPlanilla;
import pe.marista.sigma.bean.MatrizGraficoEDBean;
import pe.marista.sigma.bean.PersonalEDBean;
import pe.marista.sigma.bean.PreguntasBean;
import pe.marista.sigma.bean.PreguntaxCompetenciaBean;
import pe.marista.sigma.bean.ProgresoBean;
import pe.marista.sigma.bean.RepConsolidado;
import pe.marista.sigma.bean.ResumenEvaDesempeno;
import pe.marista.sigma.bean.UniNegBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.UsuarioPerfilBean;
import pe.marista.sigma.bean.reporte.AlertasEvaluacionDesempenoRepBean;
import pe.marista.sigma.bean.reporte.EvaluacionDesempenoRepBean;
import pe.marista.sigma.bean.reporte.FamiliarEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FamiliarRepBean;
import pe.marista.sigma.bean.reporte.ResponsableEconomicoRepBean;
import pe.marista.sigma.bean.reporte.ResumenIngRepBean;
import pe.marista.sigma.bean.reporte.SeguimientoEDRepBean;
import pe.marista.sigma.factory.BeanFactory;
import static pe.marista.sigma.managedBean.ResDimensionMB.rec_titulo_grp_ocupa;
import static pe.marista.sigma.managedBean.ResDimensionMB.uniNeg;
import pe.marista.sigma.service.EvaluacionDesempenoService;
import pe.marista.sigma.service.ResDimensionService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS001
 */
@ManagedBean
@RequestScoped
public class EvaluacionDesempenoMB extends BaseMB implements Serializable {

    private List<PreguntasBean> listaPreguntas;
    private List<DatosPersonalBean> listaDatosEvaluador;
    private List<PreguntaxCompetenciaBean> listaPreguntasEvaluado;
    private List<HabilitaEncuestaBean> lista_habilitar_Encuesta;
    private List<CargosEvaluadorBean> listaCargosEvaluador;
    private List<ProgresoBean> recuperaProgreso, updateProgreso;
    private List<PreguntasBean> lista_espiritu_familia, lista_sencillez, lista_solidaridad, lista_amor_trabajo;
    /* DIRECTIVO*/
    private List<PreguntasBean> lista_dir_liderazgo, lista_dir_gestion, lista_dir_conflictos, lista_dir_responsabilidad, lista_dir_confidencialidad;
    /* NO DOCENTE*/
    private List<PreguntasBean> lista_nodocente_evangeliza, lista_nodocente_capacidad, lista_nodocente_trabajo, lista_nodocente_planificacion, lista_nodocente_confidencialidad;
    /* ADMINISTRATIVO*/
    private List<PreguntasBean> lista_adm_evangeliza, lista_adm_gestion, lista_adm_trabajo, lista_adm_proactividad, lista_adm_confiabilidad;
    /* DOCENTE - ENTRENADOR - AUXILIAR*/
    private List<PreguntasBean> lista_dea_evangeliza, lista_dea_compromiso, lista_dea_trabajo, lista_dea_planificacion, lista_dea_confidencialidad;
    /* MANTENIMIENTO*/

    private List<PreguntasBean> lista_man_evangeliza, lista_man_distribucion, lista_man_trabajo, lista_man_dinamismo, lista_man_implementos;
    private List<EvaluacionBean> listaEvaluaciones;
    private List<EvaluacionBean> validaEncuestasCompletas;
    /* private List<PreguntasBean> lista_man_evangeliza, lista_man_distribucion, lista_man_trabajo, lista_man_dinamismo, lista_man_implementos;
     private List<EvaluacionBean> listaEvaluaciones;*/
    private List<EstadoBean> listaEstados;
    private List<PreguntasBean> selectedRespuesta;
    private String iduniorg;
    private float progreso;
    private HorizontalBarChartModel HoriResDirectivos, HoriResDirectivosProm, HoriResDirectivosPromActual, HoriResDirectoresED, HoriResDirectoresProm;
    private List<MatrizGraficoEDBean> listaResDirectores, listaResDirectoresHis;
    private List<DirectoresPromBean> listaResDirectoresPromHis;
    /* EVALUADO-EVALUADOR*/
    private EvaluacionDesempenoBean evaluacionDesempenoBean;
    private List<EvaluacionDesempenoBean> listaEvaluacionDesempeno;
    private PersonalEDBean personalEDBean;
    //Evaluado
    private DualListModel<PersonalEDBean> dualEvaluadoBean;
    private List<PersonalEDBean> listaEvaluadoBeanB;
    private List<PersonalEDBean> listaPersonalEDBeanEvaluado;
    private List<PersonalEDBean> listaPersonalSessionEvaluadoBean;
    //Evaluador
    private DualListModel<PersonalEDBean> dualEvaluadorBean;
    private List<PersonalEDBean> listaEvaluadorBeanB;
    private List<PersonalEDBean> listaPersonalEDBeanEvaluador;
    private List<PersonalEDBean> listaPersonalSessionEvaluadorBean;
    private String disabled = "true";
    private String codPerEvaluador, codPerEvaluado;
    private UsuarioBean usuarioLoginBean;
    private Integer anio, idgrupoOcuEvaluado, cantPregCardinales, cantPregEspecificas, idEvaluadoEvaluador, flagEvaluacion, anioIndicador;
    private String flag, nombreEvaluado, apellidoEvaluado, cargoEvaludo, uniNegEvaluado, fechaActual;
    private String nombreEvaluador, apellidoEvaluador, cargoEvaluador, obraEvaluador, nroEvaluados, fotoEvaluado;
    private List<HistoricoEDBean> listaResDirectoresProm;
    private List<UniNegBean> listaUnidadNegocio, listaUniNegHistorico;
    /* private Integer anio, idgrupoOcuEvaluado, cantPregCardinales, cantPregEspecificas, idEvaluadoEvaluador;
     private String flag, nombreEvaluado, apellidoEvaluado, cargoEvaludo, uniNegEvaluado, fechaActual;
     private String nombreEvaluador, apellidoEvaluador, cargoEvaluador, obraEvaluador, nroEvaluados, fotoEvaluado;*/
    private List<EvaluadoBean> listaDatosEvaluado;
    private String nombre_unidad;
    private Map<String, Integer> listaReporte;
    private Integer orden, idcargoEvaluador, cont_ceros, idEstado, cant_Preguntas_evaluado;
    private Integer tot_ceros, tot_uno, tot_dos, tot_tres, tot_cuatro, tot_vacios,
            tot_ceros_dir, tot_uno_dir, tot_dos_dir, tot_tres_dir, tot_cuatro_dir, tot_vacios_dir,
            tot_ceros_nodoc, tot_uno_nodoc, tot_dos_nodoc, tot_tres_nodoc, tot_cuatro_nodoc, tot_vacios_nodoc,
            tot_ceros_adm, tot_uno_adm, tot_dos_adm, tot_tres_adm, tot_cuatro_adm, tot_vacios_adm,
            tot_ceros_doc, tot_uno_doc, tot_dos_doc, tot_tres_doc, tot_cuatro_doc, tot_vacios_doc,
            tot_ceros_man, tot_uno_man, tot_dos_man, tot_tres_man, tot_cuatro_man, tot_vacios_man;
    private boolean flagEncuesta, flgcargoprinEvaluado;
    private Boolean flgALL = false;
    private Boolean flgEncuesta = false;
    private Boolean flgNiveles = false;
    private EvaluacionBean evaluacionBean;
    private HabilitaEncuestaBean habilitaEncuestaBean;
    private Boolean flgProgreso = false;
    private List<PersonalEDBean> listaPersonalSessionBean;
    private Integer index = 0;
    private Integer index_especifica = 0;
    private Integer index_prin = 0;
    private Integer flg_save = 0;
    private boolean flgevalcompletas = false;
    private String uniNegHis = "";
    private boolean flgindicaciones = false;
    private String nrodoc;
    private Integer flgimprimir;
    private List<UnidadNegocioBean> listaUniNeg;
    private UnidadNegocioBean unidadNegocioBean;
    private List<HistoricoEDListBean> listaHistoricoProm, recuperaHistoricoProm;
    private HistoricoEDListBean historicoEDListBean;
    private String nombreHis, cargoHis, obreHis, uninegHis, nom_per_Indicador;
    private Integer anioHis, idcargoHis, id_Indicador, grp_Ocu_Indicador, tipo_Planilla_Indicador;
    private float promedioHis;
    private ED_HistoricoBean ed_HistoricoBean;
    private List<DirectoresEDBean> listaDirectoresED;
    private Map<Integer, Integer> anioslist;
    private List<IndicadoresBean> listaGrupoOcupacional;
    private List<IndicadoresBean> listaTipoPlanilla;
    private List<IndicadoresBean> listaIndicadores;
    private List<DetalleIndicadorBean> listaDetIndicadores;
    private Boolean flgAsignar = false;
    public static String uniNegRecupera = "";
    private String unidadSector = "";
    private static String query = "";
    private String tituloIndicador;
    private List<ED_DetalleComObservables> listademo;
    private BarChartModel barNiveles; //barIniPriSec  barDirectivos
    private List<ED_DetalleCompetencias> listaAutoPromedio;
    private String param_codper = "";
    private Integer param_idcargo;
    private Integer param_detNivel;
    private List<ED_IniPriSEC> listaNiveles;
    private List<ED_DetallexNivel> listaDetalleNiveles;
    private ED_DetallexNivel nivelBean;
    private List<ED_IniPriSEC> listaIniPriSec, listaDirectivos;
    private LineChartModel modelCompetencia;
    private boolean flgGrafCompetencia = false;
    private List<ED_PersonalBean> listaVistaReporte;
    private Integer flgindividual;
    private String ima_individual, ima_consolidado;
    private ED_PersonalBean personalBean;
    private List<DetalleIndicadorBean> selectedIndicadores;
    private boolean flgindi = false;
    public Integer valor_indicador = 0;
    public String cbo_tipo_rep_indiviadual;
    private boolean flgrepIndividual = false;
    private boolean flgrepIndividual_01 = false;
    private List<DetalleNivelCargo> listaDetalleNivelesCargo;
    private String param_unineg_cargo = "";
    private String param_codper_cargo = "";
    private boolean flgGrafCompetencia_cargo = false;
    private DetalleNivelCargo detalleNivelCargo;
    private String paramTitle = "";
    private Integer var_reporte, var_reporteCons;
    private Integer flg_consolidado;
    private List<AnioHistBean> listaAnioIndicadores;
    

    //Metodo add. añade elementos a nuestra lista
    @PostConstruct
    public void EvaluacionDesempenoMB() {
        try {
            cargar();
            promedioHis = 0;
            anioHis = 0;
            grp_Ocu_Indicador = 0;
            tipo_Planilla_Indicador = 0;
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            evDesempenoService.sp_ed_cargar_matriz_historico();
            evDesempenoService.sp_ed_insert_historico();
            evDesempenoService.sp_ed_cargar_matriz_autoevaluacion();

            Date date = new Date();
            Calendar miCalendario = Calendar.getInstance();
            //setAnioIndicador(miCalendario.get(Calendar.YEAR));
            //Caso 2: obtener la fecha y salida por pantalla con formato:
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            //System.out.println("Fecha: " + dateFormat.format(date));
            System.out.println("Fecha: " + dateFormat.format(date));
            modelCompetencia = obtenerCompetencias("46869866", 21);
            modelCompetencia.setTitle("Evaluación por Competencias");
            modelCompetencia.setAnimate(true);
            //modelCompetencia.setLegendPosition("c");
            modelCompetencia.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
            modelCompetencia.setShowPointLabels(true);
            modelCompetencia.setExtender("chartLineExtender");
            modelCompetencia.setSeriesColors("FFA500,00CED1");
            modelCompetencia.getAxes().put(AxisType.X, new CategoryAxis("Competencias"));
            Axis yAxis = modelCompetencia.getAxis(AxisType.Y);
            yAxis.setLabel("Puntajes");
            yAxis.setMin(0);
            yAxis.setMax(5);
            fechaActual = dateFormat.format(date);
            lista_habilitar_Encuesta = evDesempenoService.sp_ed_lista_encuestas_editar(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            //System.out.println("Fecha: " + dateFormat.format(date));
            //fechaActual = dateFormat.format(date);
            listaUniNegHistorico = evDesempenoService.sp_ed_lista_unineg_historico();
            flgimprimir = 0;
            setUniNegHis("BARINA");
            listaHistoricoProm = evDesempenoService.sp_ed_lista_historico("all");
            anioslist = new HashMap<Integer, Integer>();
            anioslist.put(2016, 2016);
            anioslist.put(2017, 2017);
            listaGrupoOcupacional = evDesempenoService.sp_ed_tipo_planilla(309);
            listaTipoPlanilla = evDesempenoService.sp_ed_tipo_planilla(302);
            listaIndicadores = evDesempenoService.sp_ed_lista_indicadores(tipo_Planilla_Indicador);
            obtenerTipoNiveles();
            //obtenerTipoNivelDocentes();
            //obtenerTipoNivelDirectivos();
            listaVistaReporte = evDesempenoService.sp_ed_vista_reportes_EvaDes();
            ima_individual = "menu_chart1";
            ima_consolidado = "chart_pastel2_black";
            var_reporte = 0;
            var_reporteCons = 0;
            tipo_Planilla_Indicador = 0;
            flg_consolidado = evDesempenoService.sp_ed_flg_consolidado(usuarioLoginBean.getPersonalBean().getIdPersonal(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(" id personal --> " + usuarioLoginBean.getPersonalBean().getIdPersonal() + " uineg --> " + usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaAnioIndicadores =evDesempenoService.sp_anio_indicadores();
            // paramTitle="Personal por G.O";
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void rowSelectEncuesta(Object event) {
        try {
            EvaluacionDesempenoService evaluacionService = BeanFactory.getEvaluacionDesempenoService();
            evaluacionBean = (EvaluacionBean) event;
            Integer idEvaluedoEvaluadorRec = evaluacionBean.getIdevaluadoevaluador();
            Integer idgrupo = evaluacionService.sp_ed_idgrupoocupacional(evaluacionBean.getIdevaluadoevaluador(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            cant_Preguntas_evaluado = 0;
            listaDatosEvaluado = evaluacionService.sp_datos_evaluado(idEvaluedoEvaluadorRec, idgrupo, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);

            nombreEvaluado = listaDatosEvaluado.get(0).getNombreEvaluado();
            apellidoEvaluado = listaDatosEvaluado.get(0).getApellidoEvaluado();
            cargoEvaludo = listaDatosEvaluado.get(0).getNombreCargoEvaluado();
            idgrupoOcuEvaluado = listaDatosEvaluado.get(0).getIdgrupoOcuEvaluado();
            cantPregCardinales = listaDatosEvaluado.get(0).getCantPreCardinales();
            cantPregEspecificas = listaDatosEvaluado.get(0).getCantPregEspecificas();
            idEvaluadoEvaluador = listaDatosEvaluado.get(0).getIdEvaluadoEvaluador();
            uniNegEvaluado = listaDatosEvaluado.get(0).getUninegEvaluado();
            recuperaProgreso = evaluacionService.sp_ed_existe_encuesta(idEvaluadoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            //System.out.println("recupera evaluado.............." + recuperaProgreso.isEmpty());
            if (recuperaProgreso.isEmpty() || recuperaProgreso.get(0).getProgreso() == 0) {
                progreso = 0;
                flagEncuesta = false;
            } else {
                progreso = recuperaProgreso.get(0).getProgreso();
                flagEncuesta = true;
            }
            codPerEvaluado = listaDatosEvaluado.get(0).getCodperevaluado();
            flgcargoprinEvaluado = listaDatosEvaluado.get(0).isFlgcargoprincipal();

            Integer flgcargoPrin = 0;  // 1 - true --->  principal
            if (flgcargoprinEvaluado) {
                flgcargoPrin = 1;
            }

            listaPreguntasEvaluado = evaluacionService.sp_ed_cant_preg_grupoocupacional(listaDatosEvaluado.get(0).getIdgrupoOcuEvaluado(), flgcargoPrin, anio);
            //System.out.println("id grupo ocupacional..............."+ listaDatosEvaluado.get(0).getIdgrupoOcuEvaluado() +"  flgprincipal..........."+flgcargoPrin);
            cant_Preguntas_evaluado = listaPreguntasEvaluado.size();
            fotoEvaluado = evaluacionService.obtenerFotoPersonal(codPerEvaluado, uniNegEvaluado);
            //System.out.println("f/" + rutaFotoEvaluado);
            /*cardinales*/
            lista_espiritu_familia = evaluacionService.sp_ed_lista_preguntas(1, "30906", anio);
            lista_sencillez = evaluacionService.sp_ed_lista_preguntas(2, "30906", anio);
            lista_solidaridad = evaluacionService.sp_ed_lista_preguntas(3, "30906", anio);
            lista_amor_trabajo = evaluacionService.sp_ed_lista_preguntas(4, "30906", anio);

            if (flgcargoprinEvaluado == true) {
                if (idgrupoOcuEvaluado == 30901) {
                    System.out.println(" directivo");
                    /* Directivos */
                    lista_dir_liderazgo = evaluacionService.sp_ed_lista_preguntas(5, "30901", anio);
                    lista_dir_gestion = evaluacionService.sp_ed_lista_preguntas(6, "30901", anio);
                    lista_dir_conflictos = evaluacionService.sp_ed_lista_preguntas(7, "30901", anio);
                    lista_dir_responsabilidad = evaluacionService.sp_ed_lista_preguntas(8, "30901", anio);
                    lista_dir_confidencialidad = evaluacionService.sp_ed_lista_preguntas(9, "30901", anio);

                } else if (idgrupoOcuEvaluado == 30902) {
                    System.out.println("docente");
                    /* Docente - Auxiliar - Entrenador*/
                    lista_dea_evangeliza = evaluacionService.sp_ed_lista_preguntas(18, "30902", anio);
                    lista_dea_compromiso = evaluacionService.sp_ed_lista_preguntas(19, "30902", anio);
                    lista_dea_trabajo = evaluacionService.sp_ed_lista_preguntas(12, "30902", anio);
                    lista_dea_planificacion = evaluacionService.sp_ed_lista_preguntas(13, "30902", anio);
                    lista_dea_confidencialidad = evaluacionService.sp_ed_lista_preguntas(9, "30902", anio);

                } else if (idgrupoOcuEvaluado == 30903) {
                    System.out.println("no docente ");
                    /*No Docente*/
                    lista_nodocente_evangeliza = evaluacionService.sp_ed_lista_preguntas(10, "30903", anio);
                    lista_nodocente_capacidad = evaluacionService.sp_ed_lista_preguntas(11, "30903", anio);
                    lista_nodocente_trabajo = evaluacionService.sp_ed_lista_preguntas(12, "30903", anio);
                    lista_nodocente_planificacion = evaluacionService.sp_ed_lista_preguntas(13, "30903", anio);
                    lista_nodocente_confidencialidad = evaluacionService.sp_ed_lista_preguntas(9, "30903", anio);

                } else if (idgrupoOcuEvaluado == 30904) {
                    System.out.println("administrador ");
                    /* Administrativo */
                    lista_adm_evangeliza = evaluacionService.sp_ed_lista_preguntas(14, "30904", anio);
                    lista_adm_gestion = evaluacionService.sp_ed_lista_preguntas(15, "30904", anio);
                    lista_adm_trabajo = evaluacionService.sp_ed_lista_preguntas(12, "30904", anio);
                    lista_adm_proactividad = evaluacionService.sp_ed_lista_preguntas(16, "30904", anio);
                    lista_adm_confiabilidad = evaluacionService.sp_ed_lista_preguntas(17, "30904", anio);

                } else if (idgrupoOcuEvaluado == 30905) {
                    System.out.println(" mantenimiennto");
                    /*Mantenimiento*/

                    lista_man_evangeliza = evaluacionService.sp_ed_lista_preguntas(23, "30905", anio);
                    lista_man_distribucion = evaluacionService.sp_ed_lista_preguntas(20, "30905", anio);
                    lista_man_trabajo = evaluacionService.sp_ed_lista_preguntas(24, "30905", anio);
                    lista_man_dinamismo = evaluacionService.sp_ed_lista_preguntas(21, "30905", anio);
                    lista_man_implementos = evaluacionService.sp_ed_lista_preguntas(22, "30905", anio);

                }
            }
            index = 0;
            flg_save = 0;
            index_especifica = 0;
            index_prin = 0;
            //listaDatosEvaluado.get(0).getCantPreCardinales() + listaDatosEvaluado.get(0).getCantPregEspecificas();
            //System.out.println("cantidad preguntas evaluado " + cant_Preguntas_evaluado);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarGraficos(String uniNeg1) throws Exception {
        setUniNegHis(uniNeg1);
        uniNegRecupera = uniNeg1;
        //System.out.println(" unidad recuperada .........................." + getUniNegHis());
        obtenerComDirectoresBarHor(getUniNegHis());
        obtenerPromDirectoresBarHor(getUniNegHis());
        obtenerComDirectoresPromBarHor(getUniNegHis());
        listaDirectoresED = new ArrayList<DirectoresEDBean>();
        listaResDirectoresPromHis = new ArrayList<DirectoresPromBean>();
        flgimprimir = 0;
    }

    public String datosdelevaluador() throws Exception {
        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        String codper = usuarioLoginBean.getPersonalBean().getNroDoc();
        String cad = codper;
        codper = "";
        codper = cad;
        return codper;

    }

    public void cargarGraficosColegios() throws Exception {
        listaResDirectores = new ArrayList<MatrizGraficoEDBean>();
        listaResDirectoresProm = new ArrayList<HistoricoEDBean>();
        listaResDirectoresHis = new ArrayList<MatrizGraficoEDBean>();
        obtenerHoriResDirectoresEDBarHor();
        obtenerComDirectoresPromGralHor();
        flgimprimir = 1;
    }

    public void rowSelectFoto() {
        try {

            //EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            String rutaFoto = "";
            //rutaFoto = evaluacionDesempenoService.obtenerFotoPersonal(usuarioLoginBean.getPersonalBean().getCodPer(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //System.out.println("f/" + rutaFoto);
            //evaluacionDesempenoBean.setFoto(rutaFoto);
            listaDatosEvaluador = new ArrayList<DatosPersonalBean>();
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            codPerEvaluador = usuarioLoginBean.getPersonalBean().getCodPer();
            idgrupoOcuEvaluado = 0;
            progreso = 0;
            flagEncuesta = false;

            cont_ceros = 0;
            idEstado = 2;
            tot_ceros = 0;
            tot_cuatro = 0;
            tot_vacios = 0;
            tot_ceros_dir = 0;
            tot_cuatro_dir = 0;
            tot_vacios_dir = 0;
            tot_ceros_nodoc = 0;
            tot_cuatro_nodoc = 0;
            tot_vacios_nodoc = 0;
            tot_ceros_adm = 0;
            tot_cuatro_adm = 0;
            tot_vacios_adm = 0;
            tot_ceros_doc = 0;
            tot_cuatro_doc = 0;
            tot_vacios_doc = 0;
            tot_ceros_man = 0;
            tot_cuatro_man = 0;
            tot_vacios_man = 0;
            List<CantidadBean> a = evDesempenoService.sp_ed_cargar_estados(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaDatosEvaluador = evDesempenoService.sp_datos_personal_default(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            //System.out.println("aaa" + listaDatosEvaluador.size());
            if (!listaDatosEvaluador.isEmpty() || listaDatosEvaluador.size() != 0) {

                listaCargosEvaluador = evDesempenoService.sp_ed_cargos_evaluador(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);

                if (!listaCargosEvaluador.isEmpty() || listaCargosEvaluador.size() != 0) {
                    idcargoEvaluador = listaCargosEvaluador.get(0).getIdCargoEvaluador();
                    flgEncuesta = false;
                    nombreEvaluador = listaDatosEvaluador.get(0).getNombre();
                    apellidoEvaluador = listaDatosEvaluador.get(0).getApellidos();
                    cargoEvaluador = listaDatosEvaluador.get(0).getNombreCargo();
                    obraEvaluador = listaDatosEvaluador.get(0).getNombreUniNeg();
                    //String rutaFoto = "";
                    rutaFoto = evDesempenoService.obtenerFotoPersonal(usuarioLoginBean.getPersonalBean().getCodPer(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    System.out.println("f/" + rutaFoto);
                    evaluacionDesempenoBean.setFoto(rutaFoto);
                } else {
                    flgEncuesta = true;
                }
                if (listaCargosEvaluador.size() > 1) {
                    flgindicaciones = true;
                }
                changeEvaluaciones();

            } else {
                flgEncuesta = true;
            }

            Integer nroEvalCompletas = 0;
            flgevalcompletas = false;
            validaEncuestasCompletas = evDesempenoService.sp_ed_evaluaciones_completas(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (validaEncuestasCompletas.size() == 0) {
                validaEncuestasCompletas = evDesempenoService.sp_ed_evaluaciones_completas_default(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                flgevalcompletas = false;
                flgindicaciones = false;
                /*cardinales*/
                lista_espiritu_familia = evDesempenoService.sp_ed_lista_preguntas(1, "30906", anio);
                lista_sencillez = evDesempenoService.sp_ed_lista_preguntas(2, "30906", anio);
                lista_solidaridad = evDesempenoService.sp_ed_lista_preguntas(3, "30906", anio);
                lista_amor_trabajo = evDesempenoService.sp_ed_lista_preguntas(4, "30906", anio);
                //System.out.println("aaa"+evaluacionDesempenoBean.getFoto());
            } else {
                for (int i = 0; i < validaEncuestasCompletas.size(); i++) {
                    if (validaEncuestasCompletas.get(i).getFlag() == 0) {
                        nroEvalCompletas += 1;
                    }
                }
            }
            if (nroEvalCompletas == validaEncuestasCompletas.size()) {
                flgevalcompletas = true;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectHistorico(Object event) {
        try {
            promedioHis = 0;
            EvaluacionDesempenoService evaluacionService = BeanFactory.getEvaluacionDesempenoService();
            historicoEDListBean = (HistoricoEDListBean) event;
            nombreHis = historicoEDListBean.getNombrePersonal();
            idcargoHis = historicoEDListBean.getIdCargo();
            cargoHis = historicoEDListBean.getNombreCargo();
            obreHis = historicoEDListBean.getNombreUniNeg();
            uninegHis = historicoEDListBean.getUniNeg();
            anioHis = 2016;
            Float res;
            res = evaluacionService.sp_ed_recupera_promedio(uninegHis, nombreHis, idcargoHis, anioHis);
            if (res == null) {
                promedioHis = 0;
            } else {
                promedioHis = res;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargar() throws Exception {

        usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
        flag = "G";
        evaluacionDesempenoBean = new EvaluacionDesempenoBean();
        listaEvaluadoBeanB = new ArrayList<>();
        listaEvaluadorBeanB = new ArrayList<>();
        //Evaluado
        PersonalEDBean perEvaluado = new PersonalEDBean();
        perEvaluado.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        listaPersonalEDBeanEvaluado = evaluacionDesempenoService.obtenerEvaluacionDesempenoActivos(uniNeg);
        dualEvaluadoBean = new DualListModel<>(listaPersonalEDBeanEvaluado, getListaEvaluadoBeanB());
        //Evaluador
        PersonalEDBean perEvaluador = new PersonalEDBean();
        perEvaluador.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        listaPersonalEDBeanEvaluador = evaluacionDesempenoService.obtenerEvaluacionDesempenoActivos(uniNeg);
        dualEvaluadorBean = new DualListModel<>(listaPersonalEDBeanEvaluador, getListaEvaluadorBeanB());

//            EvaluacionDesempenoBean eva = new EvaluacionDesempenoBean();
//            eva.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            evaluacionDesempenoBean = evaluacionDesempenoService.obtenerEvluacionDesempeno(eva);
        Calendar miCalendario = Calendar.getInstance();
        setAnio(miCalendario.get(Calendar.YEAR));
        EvaluacionDesempenoBean eva = new EvaluacionDesempenoBean();
        eva.getUnidadNegocioBean().setUniNeg(uniNeg);
        listaEvaluacionDesempeno = evaluacionDesempenoService.obtenerListaEvluacionDesempeno(eva);
        listaReporte();
        UnidadNegocioService unineg = BeanFactory.getUnidadNegocioService();
        listaUniNeg = unineg.obtenerTodos();

        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
            unidadNegocioBean = new UnidadNegocioBean();
            unidadNegocioBean.setUniNeg("SECTOR");
        }

    }

    public void listaReporte() {
        listaReporte = new LinkedHashMap<>();
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenAlfEvaluadores", null), 1);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenAlfEvaluadoresGrupoOcu", null), 2);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenAlfEvaluados", null), 3);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenCargoEvaluadores", null), 4);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenCargoEvaluadoresxGrupoOcu", null), 5);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenCargoEvaluados", null), 6);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenAlfEvaluadoresNiveles", null), 7);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenCargoEvaluadoresxNiveles", null), 8);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaProgresoEvaluaciones", null), 9);
        listaReporte = Collections.unmodifiableMap(listaReporte);
    }

    public void imprimirPDFEvaluadoresEvaluados() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteEvaluadoresEvaluados.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaluacionDesempenoRepBean> listaEvaluacion = new ArrayList<>();
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }
            if (orden >= 1 && orden <= 6) {
                listaEvaluacion = evaluacionDesempenoService.obtenerReporteEvaluadoresEvaluados(uniNeg, anio, orden);
                listaEvaluacion.get(0).setUsuario(usuarioLoginBean.getUsuario());
            }
            if (orden == 7 || orden == 8) {
                listaEvaluacion = evaluacionDesempenoService.obtenerReporteEvaluadoresEvaluadosNiveles(uniNeg, anio, orden);
                listaEvaluacion.get(0).setUsuario(usuarioLoginBean.getUsuario());
            }

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEvaluacion);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void exportXlsAll() throws IOException {
        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();

            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteEvaluadoresEvaluadosExcel.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaluacionDesempenoRepBean> listaEvaluacion = new ArrayList<>();
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }
            if (orden >= 1 && orden <= 6) {
                listaEvaluacion = evaluacionDesempenoService.obtenerReporteEvaluadoresEvaluados(uniNeg, anio, orden);
            }
            if (orden == 7 || orden == 8) {
                listaEvaluacion = evaluacionDesempenoService.obtenerReporteEvaluadoresEvaluadosNiveles(uniNeg, anio, orden);
            }

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEvaluacion);

            Map<String, Object> parametros = new HashMap<>();
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void imprimirPDFProgresoEvaluaciones() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteProgresoEvaluacionesPDF.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaluacionDesempenoRepBean> listaEvaluacion = new ArrayList<>();
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }
            listaEvaluacion = evaluacionDesempenoService.obtenerProgresoDeEvaluaciones(uniNeg, anio);

            if (!listaEvaluacion.isEmpty()) {
//                for (int g = 0; g < listaEvaluacion.size(); g++) {
                List<SeguimientoEDRepBean> listaSeguimiento = new ArrayList<>();
                listaSeguimiento = evaluacionDesempenoService.obtenerSeguimientoEvaluacionDesempeno(uniNeg, anio);

                listaEvaluacion.get(0).setListaGeneral(listaSeguimiento);
                listaEvaluacion.get(0).setUsuario(usuarioLoginBean.getUsuario());

//                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEvaluacion);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void exportXlsProgresoEvaluaciones() throws IOException {
        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();

            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteProgresoEvaluacionesExcel.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaluacionDesempenoRepBean> listaEvaluacion = new ArrayList<>();
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }
            listaEvaluacion = evaluacionDesempenoService.obtenerProgresoDeEvaluaciones(uniNeg, anio);
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEvaluacion);

            Map<String, Object> parametros = new HashMap<>();
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void imprimirPDFAlertaEvaluacionDesempeno() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteAlertasEvaluacionDesempeno.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }

            List<AlertasEvaluacionDesempenoRepBean> listaAlerta = new ArrayList<>();
            listaAlerta = evaluacionDesempenoService.obtenerAlertasEvaluacionDesempeno(uniNeg, anio);
            if (!listaAlerta.isEmpty()) {
                listaAlerta.get(0).setUsuario(usuarioLoginBean.getUsuario());
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaAlerta);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFSinEvaluacionDesempeno() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteSinEvaluacionDesempeno.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String uniNeg;
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("SECTOR")) {
                uniNeg = unidadNegocioBean.getUniNeg();
            } else {
                uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            }

            List<EvaluacionDesempenoRepBean> listaSinEvaluacion = new ArrayList<>();
            listaSinEvaluacion = evaluacionDesempenoService.obtenerSinEvaluacionDesempeno(uniNeg, anio);
            listaSinEvaluacion.get(0).setUsuario(usuarioLoginBean.getUsuario());
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaSinEvaluacion);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Evaluadores_Evaluados_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void obtenerReporte() {
        try {
            if (orden >= 1 && orden <= 6) {
                this.flgALL = true;
                this.flgNiveles = false;
                this.flgProgreso = false;
            }
            if (orden == 7 || orden == 8) {
                this.flgNiveles = true;
                this.flgALL = false;
                this.flgProgreso = false;
            }
            if (orden == 9) {
                this.flgProgreso = true;
                this.flgALL = false;
                this.flgNiveles = false;
            }
            System.out.println("reporte: " + orden);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerObra() {
        try {

            System.out.println("obra: " + unidadNegocioBean.getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            evaluacionDesempenoBean = (EvaluacionDesempenoBean) event.getObject();

            listaEvaluadoBeanB = new ArrayList<>();
            listaEvaluadorBeanB = new ArrayList<>();

            dualEvaluadorBean = null;
            listaPersonalSessionEvaluadorBean = evaluacionDesempenoService.obtenerEvaluador(evaluacionDesempenoBean);
//            dualEvaluadorBean = new DualListModel<>(listaPersonalEDBeanEvaluador, listaEvaluadorBeanB);

            for (int i = 0; i < listaPersonalSessionEvaluadorBean.size(); i++) {
                listaEvaluadorBeanB.add(listaPersonalSessionEvaluadorBean.get(i));
                for (int j = 0; j < listaPersonalEDBeanEvaluador.size(); j++) {
                    if (Objects.equals(listaPersonalEDBeanEvaluador.get(j).getCodigoPer(), listaPersonalSessionEvaluadorBean.get(i).getCodigoPer())) {
                        listaPersonalEDBeanEvaluador.remove(j);
                    }
                }
            }
            dualEvaluadorBean = new DualListModel<>(listaPersonalEDBeanEvaluador, listaEvaluadorBeanB);

            listaPersonalSessionEvaluadoBean = evaluacionDesempenoService.obtenerEvaluado(evaluacionDesempenoBean);
//            listaEvaluadoBeanB = evaluacionDesempenoService.obtenerEvaluado(evaluacionDesempenoBean);
            for (int i = 0; i < listaPersonalSessionEvaluadoBean.size(); i++) {
                listaEvaluadoBeanB.add(listaPersonalSessionEvaluadoBean.get(i));
                for (int j = 0; j < listaPersonalEDBeanEvaluado.size(); j++) {
                    if (Objects.equals(listaPersonalEDBeanEvaluado.get(j).getCodigoPer(), listaPersonalSessionEvaluadoBean.get(i).getCodigoPer())) {
                        listaPersonalEDBeanEvaluado.remove(j);
                    }
                }
            }
            dualEvaluadoBean = new DualListModel<>(listaPersonalEDBeanEvaluado, listaEvaluadoBeanB);
            flag = "A";
            disabled = "false";
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void changeEvaluaciones() throws Exception {

        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

        listaEvaluaciones = evDesempenoService.sp_ed_lista_evaluaciones(usuarioLoginBean.getPersonalBean().getNroDoc(), idcargoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        if (listaEvaluaciones.size() == 0 || listaEvaluaciones.isEmpty()) {
            listaEvaluaciones = evDesempenoService.sp_ed_lista_evaluaciones_default(usuarioLoginBean.getPersonalBean().getNroDoc(), idcargoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        }
        nroEvaluados = String.valueOf(listaEvaluaciones.size());
        listaDatosEvaluador = evDesempenoService.sp_datos_personal(usuarioLoginBean.getPersonalBean().getNroDoc(), idcargoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        if (!listaDatosEvaluador.isEmpty()) {
            nombreEvaluador = listaDatosEvaluador.get(0).getNombre();
            apellidoEvaluador = listaDatosEvaluador.get(0).getApellidos();
            cargoEvaluador = listaDatosEvaluador.get(0).getNombreCargo();
            obraEvaluador = listaDatosEvaluador.get(0).getNombreUniNeg();
            //flagEvaluacion=listaDatosEvaluador.get(0).ge;
            //listaEvaluaciones = evDesempenoService.sp_ed_lista_evaluaciones(usuarioLoginBean.getPersonalBean().getNroDoc(),idcargoEvaluador);
            //nroEvaluados=String.valueOf(listaEvaluaciones.size());           
            listaEstados = evDesempenoService.sp_ed_lista_estados(usuarioLoginBean.getPersonalBean().getNroDoc(), listaDatosEvaluador.get(0).getIdCargo(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        }
    }

    public String eliminarEvaluador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
                evaluacionDesempenoService.eliminarEvaluadorAll(evaluacionDesempenoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiar();
                cargar();
                //listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarEvaluadoEvaluador() {
        if (dualEvaluadorBean.getTarget().size() <= 1) {
            System.out.println("is null");
            insertarEvaluacionDesempeno();
            System.out.println("is not null");
        } else {
            new MensajePrime().addInformativeMessagePer("No se permite insertar mas de un evaluador por el grupo de evaluados ingresados...");
        }
    }

    public String insertarEvaluacionDesempeno() {
        String pagina = null;
        try {
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            String usuario = usuarioLoginBean.getUsuario();
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            if (dualEvaluadoBean.getTarget().size() > 0) {
                if (dualEvaluadorBean.getTarget().size() > 0) {

                    evaluacionDesempenoService.insertarEDVer2(anio, uniNeg, usuario,
                            dualEvaluadoBean.getTarget(), dualEvaluadorBean.getTarget());
                    EvaluacionDesempenoBean eva = new EvaluacionDesempenoBean();
                    eva.getUnidadNegocioBean().setUniNeg(uniNeg);
                    listaEvaluacionDesempeno = evaluacionDesempenoService.obtenerListaEvluacionDesempeno(eva);
                    flag = "G";
                } else {
                    new MensajePrime().addInformativeMessagePer("Le falta Ingresar los Evaluados");
                }

            } else {
                new MensajePrime().addInformativeMessagePer("Le falta Ingresar al Evaluador");
            }

        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEvaluacionDesempeno() {
        String pagina = null;
        try {
            if (pagina == null) {
                //String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
                EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
                evaluacionDesempenoService.eliminarEvaluadorAll(evaluacionDesempenoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                insertarEvaluacionDesempeno();
                limpiar();
                cargar();
                //flag="G";
                //RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void actualizaIndex_prin() {
        try {

            selectedRespuesta = new ArrayList<PreguntasBean>();

            if (index == 3) {
                for (PreguntasBean dataItem : lista_amor_trabajo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_amor_trabajo.size()) {
                    index_prin += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void habilitarEncuesta(Object ed_encuesta) {
        try {

            habilitaEncuestaBean = (HabilitaEncuestaBean) ed_encuesta;
            //EvaluacionDesempenoService evDesempenoService  = BeanFactory.getEvaluacionDesempenoService();            

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void listaEncuestasCompletas() throws Exception {
        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        lista_habilitar_Encuesta = evDesempenoService.sp_ed_lista_encuestas_editar(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
        List<CantidadBean> a = evDesempenoService.sp_ed_cargar_estados(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
                Integer idevaluadoEvaluador_delete = habilitaEncuestaBean.getIdevaluadoevaluador();
                evDesempenoService.ed_updateEncuesta(idevaluadoEvaluador_delete);
                listaEncuestasCompletas();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                habilitaEncuestaBean = new HabilitaEncuestaBean();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiar() {
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaPersonalEDBeanEvaluado = new ArrayList<>();
            listaPersonalEDBeanEvaluador = new ArrayList<>();
            listaEvaluadoBeanB = new ArrayList<>();
            listaEvaluadorBeanB = new ArrayList<>();
            PersonalEDBean perEvaluado = new PersonalEDBean();
            perEvaluado.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalEDBeanEvaluado = evaluacionDesempenoService.obtenerEvaluacionDesempenoActivos(uniNeg);
            PersonalEDBean perEvaluador = new PersonalEDBean();
            perEvaluador.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalEDBeanEvaluador = evaluacionDesempenoService.obtenerEvaluacionDesempenoActivos(uniNeg);
            dualEvaluadorBean = new DualListModel<>(listaPersonalEDBeanEvaluador, getListaEvaluadorBeanB());
            dualEvaluadoBean = new DualListModel<>(listaPersonalEDBeanEvaluado, getListaEvaluadoBeanB());
            flag = "G";

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizaIndex() {
        try {

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();

            if (index == 0) {
                for (PreguntasBean dataItem : lista_espiritu_familia) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_espiritu_familia.size()) {
                    index += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } else if (index == 1) {
                for (PreguntasBean dataItem : lista_sencillez) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_sencillez.size()) {
                    index += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } else if (index == 2) {
                for (PreguntasBean dataItem : lista_solidaridad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_solidaridad.size()) {
                    index += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            /*else if(index==3){
             for (PreguntasBean dataItem : lista_amor_trabajo) {
             if(dataItem.getIdrespuesta()!=null){         
             selectedRespuesta.add(dataItem);
             }
             }   
             if (selectedRespuesta.size()==lista_amor_trabajo.size()) {        
             index+=1;
             }else{
             FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
             FacesContext.getCurrentInstance().addMessage(null, message);  
             }     
             }     */
            if (cant_Preguntas_evaluado == 21) {
                flg_save = 1;
            } else {
                flg_save = 0;
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizaIndex_Especifico_dir() {
        try {

            Integer i = index_especifica;
            selectedRespuesta = new ArrayList<PreguntasBean>();
            /*Directivo*/
            if (index_especifica == 0) {
                for (PreguntasBean dataItem : lista_dir_liderazgo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dir_liderazgo.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 1) {
                for (PreguntasBean dataItem : lista_dir_gestion) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dir_gestion.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 2) {
                for (PreguntasBean dataItem : lista_dir_conflictos) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dir_conflictos.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 3) {
                for (PreguntasBean dataItem : lista_dir_responsabilidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dir_responsabilidad.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 4) {
                for (PreguntasBean dataItem : lista_dir_confidencialidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dir_confidencialidad.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            index_especifica = i;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizaIndex_Especifico_nodoc() {
        try {
            Integer i = index_especifica;
            selectedRespuesta = new ArrayList<PreguntasBean>();
            /*No Docente*/

            if (index_especifica == 0) {
                for (PreguntasBean dataItem : lista_nodocente_evangeliza) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_nodocente_evangeliza.size()) {
                    i += 1;
                    //System.out.println("valor  index_especifica new..." + index_especifica);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación. nodoc");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 1) {
                for (PreguntasBean dataItem : lista_nodocente_capacidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_nodocente_capacidad.size()) {
                    i += 1;
                    System.out.println("valor  index_especifica new..." + index_especifica);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.nodoc");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 2) {
                for (PreguntasBean dataItem : lista_nodocente_trabajo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_nodocente_trabajo.size()) {
                    i += 1;
                    System.out.println("valor  index_especifica new..." + index_especifica);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.nodoc");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 3) {
                for (PreguntasBean dataItem : lista_nodocente_planificacion) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_nodocente_planificacion.size()) {
                    i += 1;
                    System.out.println("valor  index_especifica new..." + index_especifica);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.nodoc");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 4) {
                for (PreguntasBean dataItem : lista_nodocente_confidencialidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_nodocente_confidencialidad.size()) {
                    i += 1;
                    System.out.println("valor  index_especifica new..." + index_especifica);
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            index_especifica = i;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizaIndex_Especifico_adm() {
        try {
            Integer i = index_especifica;
            selectedRespuesta = new ArrayList<PreguntasBean>();
            /*No Docente*/
            if (index_especifica == 0) {
                for (PreguntasBean dataItem : lista_adm_evangeliza) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_adm_evangeliza.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 1) {
                for (PreguntasBean dataItem : lista_adm_gestion) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_adm_gestion.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 2) {
                for (PreguntasBean dataItem : lista_adm_trabajo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_adm_trabajo.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 3) {
                for (PreguntasBean dataItem : lista_adm_proactividad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_adm_proactividad.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 4) {
                for (PreguntasBean dataItem : lista_adm_confiabilidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_adm_confiabilidad.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            index_especifica = i;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizaIndex_Especifico_dea() {
        try {
            Integer i = index_especifica;
            selectedRespuesta = new ArrayList<PreguntasBean>();
            /*Docente*/
            if (index_especifica == 0) {
                for (PreguntasBean dataItem : lista_dea_evangeliza) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dea_evangeliza.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 1) {
                for (PreguntasBean dataItem : lista_dea_compromiso) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dea_compromiso.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 2) {
                for (PreguntasBean dataItem : lista_dea_trabajo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }

                }
                if (selectedRespuesta.size() == lista_dea_trabajo.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 3) {
                for (PreguntasBean dataItem : lista_dea_planificacion) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dea_planificacion.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }
            if (index_especifica == 4) {
                for (PreguntasBean dataItem : lista_dea_confidencialidad) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_dea_confidencialidad.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            index_especifica = i;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizaIndex_Especifico_mant() {
        try {
            Integer i = index_especifica;
            selectedRespuesta = new ArrayList<PreguntasBean>();
            /*No Docente*/
            if (index_especifica == 0) {
                for (PreguntasBean dataItem : lista_man_evangeliza) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_man_evangeliza.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 1) {
                for (PreguntasBean dataItem : lista_man_distribucion) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_man_distribucion.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 2) {
                for (PreguntasBean dataItem : lista_man_trabajo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_man_trabajo.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 3) {
                for (PreguntasBean dataItem : lista_man_dinamismo) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_man_dinamismo.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            if (index_especifica == 4) {
                for (PreguntasBean dataItem : lista_man_implementos) {
                    if (dataItem.getIdrespuesta() != null) {
                        selectedRespuesta.add(dataItem);
                    }
                }
                if (selectedRespuesta.size() == lista_man_implementos.size()) {
                    i += 1;
                } else {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            }

            index_especifica = i;

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void calculo_ceros_cuatro_vacios() throws Exception {

        try {

            float cont_ceros_total, cont_uno_total, cont_dos_total, cont_tres_total, cont_cuatro_total, cont_vacios_total;

            cont_ceros_total = 0;
            cont_uno_total = 0;
            cont_dos_total = 0;
            cont_tres_total = 0;
            cont_cuatro_total = 0;
            cont_vacios_total = 0;
            //boolean swicth =false;

//          int index = cant_ceros_cardinales().indexOf("/");
            int index_1 = cant_ceros_cardinales().indexOf("/");
            int index_2 = cant_ceros_cardinales().indexOf("%");
            int index_3 = cant_ceros_cardinales().indexOf("$");
            int index_4 = cant_ceros_cardinales().indexOf("@");
            tot_ceros = Integer.parseInt(cant_ceros_cardinales().toString().substring(0, index_1));
            tot_uno = Integer.parseInt(cant_ceros_cardinales().toString().substring(index_1 + 1, index_2));
            tot_dos = Integer.parseInt(cant_ceros_cardinales().toString().substring(index_2 + 1, index_3));
            tot_tres = Integer.parseInt(cant_ceros_cardinales().toString().substring(index_3 + 1, index_4));
            tot_cuatro = Integer.parseInt(cant_ceros_cardinales().toString().substring(index_4 + 1, cant_ceros_cardinales().toString().length()));
            //tot_vacios =cantPregCardinales-(tot_ceros+tot_cuatro);
            //System.out.println("ceros -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);                
            cont_ceros_total = (float) (tot_ceros);
            cont_uno_total = (float) (tot_uno);
            cont_dos_total = (float) (tot_dos);
            cont_tres_total = (float) (tot_tres);
            cont_cuatro_total = (float) (tot_cuatro);

            if (flgcargoprinEvaluado == true) {
                if (idgrupoOcuEvaluado.equals(30901)) {

                    /* Directivos */
                    int index_1_dir = cant_ceros_Directivos().indexOf("/");
                    int index_2_dir = cant_ceros_Directivos().indexOf("%");
                    int index_3_dir = cant_ceros_Directivos().indexOf("$");
                    int index_4_dir = cant_ceros_Directivos().indexOf("@");
                    tot_ceros_dir = Integer.parseInt(cant_ceros_Directivos().toString().substring(0, index_1_dir));
                    tot_uno_dir = Integer.parseInt(cant_ceros_Directivos().toString().substring(index_1_dir + 1, index_2_dir));
                    tot_dos_dir = Integer.parseInt(cant_ceros_Directivos().toString().substring(index_2_dir + 1, index_3_dir));
                    tot_tres_dir = Integer.parseInt(cant_ceros_Directivos().toString().substring(index_3_dir + 1, index_4_dir));
                    tot_cuatro_dir = Integer.parseInt(cant_ceros_Directivos().toString().substring(index_4_dir + 1, cant_ceros_Directivos().toString().length()));

                    //cont_vacios_total =(cantPregCardinales+ cantPregEspecificas)-(tot_ceros+tot_uno+tot_dos+tot_tres+tot_cuatro+tot_ceros_dir+tot_uno_dir+tot_dos_dir+tot_tres_dir+tot_cuatro_dir);                
                    cont_ceros_total = (float) (tot_ceros + tot_ceros_dir);
                    cont_uno_total = (float) (tot_uno + tot_uno_dir);
                    cont_dos_total = (float) (tot_dos + tot_dos_dir);
                    cont_tres_total = (float) (tot_tres + tot_tres_dir);
                    cont_cuatro_total = (float) (tot_cuatro + tot_cuatro_dir);
                    //System.out.println("ceros dir -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);                
                } else if (idgrupoOcuEvaluado.equals(30902)) {

                    /* Docentes */
                    int index_1_doc = cant_ceros_Docentes_Auxiliares_Entrenadores().indexOf("/");
                    int index_2_doc = cant_ceros_Docentes_Auxiliares_Entrenadores().indexOf("%");
                    int index_3_doc = cant_ceros_Docentes_Auxiliares_Entrenadores().indexOf("$");
                    int index_4_doc = cant_ceros_Docentes_Auxiliares_Entrenadores().indexOf("@");
                    tot_ceros_doc = Integer.parseInt(cant_ceros_Docentes_Auxiliares_Entrenadores().toString().substring(0, index_1_doc));
                    tot_uno_doc = Integer.parseInt(cant_ceros_Docentes_Auxiliares_Entrenadores().toString().substring(index_1_doc + 1, index_2_doc));
                    tot_dos_doc = Integer.parseInt(cant_ceros_Docentes_Auxiliares_Entrenadores().toString().substring(index_2_doc + 1, index_3_doc));
                    tot_tres_doc = Integer.parseInt(cant_ceros_Docentes_Auxiliares_Entrenadores().toString().substring(index_3_doc + 1, index_4_doc));
                    tot_cuatro_doc = Integer.parseInt(cant_ceros_Docentes_Auxiliares_Entrenadores().toString().substring(index_4_doc + 1, cant_ceros_Docentes_Auxiliares_Entrenadores().toString().length()));

                    //cont_vacios_total =(cantPregCardinales+ cantPregEspecificas)-(tot_ceros+tot_uno+tot_dos+tot_tres+tot_cuatro+tot_ceros_doc+tot_uno_doc+tot_dos_doc+tot_tres_doc+tot_cuatro_doc);                
                    cont_ceros_total = (float) (tot_ceros + tot_ceros_doc);
                    cont_uno_total = (float) (tot_uno + tot_uno_doc);
                    cont_dos_total = (float) (tot_dos + tot_dos_doc);
                    cont_tres_total = (float) (tot_tres + tot_tres_doc);
                    cont_cuatro_total = (float) (tot_cuatro + tot_cuatro_doc);
                    //System.out.println("ceros  doc -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);                
                } else if (idgrupoOcuEvaluado.equals(30903)) {

                    /* No Docentes */
                    int index_1_nodoc = cant_ceros_No_Docentes().indexOf("/");
                    int index_2_nodoc = cant_ceros_No_Docentes().indexOf("%");
                    int index_3_nodoc = cant_ceros_No_Docentes().indexOf("$");
                    int index_4_nodoc = cant_ceros_No_Docentes().indexOf("@");
                    tot_ceros_nodoc = Integer.parseInt(cant_ceros_No_Docentes().toString().substring(0, index_1_nodoc));
                    tot_uno_nodoc = Integer.parseInt(cant_ceros_No_Docentes().toString().substring(index_1_nodoc + 1, index_2_nodoc));
                    tot_dos_nodoc = Integer.parseInt(cant_ceros_No_Docentes().toString().substring(index_2_nodoc + 1, index_3_nodoc));
                    tot_tres_nodoc = Integer.parseInt(cant_ceros_No_Docentes().toString().substring(index_3_nodoc + 1, index_4_nodoc));
                    tot_cuatro_nodoc = Integer.parseInt(cant_ceros_No_Docentes().toString().substring(index_4_nodoc + 1, cant_ceros_No_Docentes().toString().length()));
                    //cont_vacios_total =(cantPregCardinales+ cantPregEspecificas)-(tot_ceros+tot_uno+tot_dos+tot_tres+tot_cuatro+tot_ceros_nodoc+tot_uno_nodoc+tot_dos_nodoc+tot_tres_nodoc+tot_cuatro_nodoc);                
                    cont_ceros_total = (float) (tot_ceros + tot_ceros_nodoc);
                    cont_uno_total = (float) (tot_uno + tot_uno_nodoc);
                    cont_dos_total = (float) (tot_dos + tot_dos_nodoc);
                    cont_tres_total = (float) (tot_tres + tot_tres_nodoc);
                    cont_cuatro_total = (float) (tot_cuatro + tot_cuatro_nodoc);
                    /*System.out.println("ceros no doc  -> " +tot_ceros +" especifica......."+ tot_ceros_nodoc);
                     System.out.println("uno no doc  -> " +tot_uno +" especifica......."+ tot_uno_nodoc);
                     System.out.println("dos no doc  -> " +tot_dos +" especifica......."+ tot_dos_nodoc);
                     System.out.println("tres no doc  -> " +tot_tres +" especifica......."+ tot_tres_nodoc);
                     System.out.println("cuatro no doc  -> " +tot_ceros +" especifica......."+ tot_cuatro_nodoc);
                     System.out.println("cardinales  -> " + cant_ceros_No_Docentes() +"  ....especificas  ... " +cant_ceros_cardinales());*/
                } else if (idgrupoOcuEvaluado.equals(30904)) {

                    /* Administrativos */
                    int index_1_adm = cant_ceros_Administrativo().indexOf("/");
                    int index_2_adm = cant_ceros_Administrativo().indexOf("%");
                    int index_3_adm = cant_ceros_Administrativo().indexOf("$");
                    int index_4_adm = cant_ceros_Administrativo().indexOf("@");
                    tot_ceros_adm = Integer.parseInt(cant_ceros_Administrativo().toString().substring(0, index_1_adm));
                    tot_uno_adm = Integer.parseInt(cant_ceros_Administrativo().toString().substring(index_1_adm + 1, index_2_adm));
                    tot_dos_adm = Integer.parseInt(cant_ceros_Administrativo().toString().substring(index_2_adm + 1, index_3_adm));
                    tot_tres_adm = Integer.parseInt(cant_ceros_Administrativo().toString().substring(index_3_adm + 1, index_4_adm));
                    tot_cuatro_adm = Integer.parseInt(cant_ceros_Administrativo().toString().substring(index_4_adm + 1, cant_ceros_Administrativo().toString().length()));
                    //cont_vacios_total =(cantPregCardinales+ cantPregEspecificas)-(tot_ceros+tot_uno+tot_dos+tot_tres+tot_cuatro+tot_ceros_adm+tot_uno_adm+tot_dos_adm+tot_tres_adm+tot_cuatro_adm);                 

                    cont_ceros_total = (float) (tot_ceros + tot_ceros_adm);
                    cont_uno_total = (float) (tot_uno + tot_uno_adm);
                    cont_dos_total = (float) (tot_dos + tot_dos_adm);
                    cont_tres_total = (float) (tot_tres + tot_tres_adm);
                    cont_cuatro_total = (float) (tot_cuatro + tot_cuatro_adm);
                    //System.out.println("ceros  adm -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);                
                } else if (idgrupoOcuEvaluado.equals(30905)) {

                    /* Mantenimiento */
                    int index_1_man = cant_ceros_Mantenimiento().indexOf("/");
                    int index_2_man = cant_ceros_Mantenimiento().indexOf("%");
                    int index_3_man = cant_ceros_Mantenimiento().indexOf("$");
                    int index_4_man = cant_ceros_Mantenimiento().indexOf("@");
                    tot_ceros_man = Integer.parseInt(cant_ceros_Mantenimiento().toString().substring(0, index_1_man));
                    tot_uno_man = Integer.parseInt(cant_ceros_Mantenimiento().toString().substring(index_1_man + 1, index_2_man));
                    tot_dos_man = Integer.parseInt(cant_ceros_Mantenimiento().toString().substring(index_2_man + 1, index_3_man));
                    tot_tres_man = Integer.parseInt(cant_ceros_Mantenimiento().toString().substring(index_3_man + 1, index_4_man));
                    tot_cuatro_man = Integer.parseInt(cant_ceros_Mantenimiento().toString().substring(index_4_man + 1, cant_ceros_Mantenimiento().toString().length()));

                    //cont_vacios_total  =(cantPregCardinales+ cantPregEspecificas)-(tot_ceros+tot_uno+tot_dos+tot_tres+tot_cuatro+tot_ceros_man+tot_uno_man+tot_dos_man+tot_tres_man+tot_cuatro_man);  
                    cont_ceros_total = (float) (tot_ceros + tot_ceros_man);
                    cont_uno_total = (float) (tot_uno + tot_uno_man);
                    cont_dos_total = (float) (tot_dos + tot_dos_man);
                    cont_tres_total = (float) (tot_tres + tot_tres_man);
                    cont_cuatro_total = (float) (tot_cuatro + tot_cuatro_man);
                    // System.out.println("ceros man -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);                
                }
            }

            cont_vacios_total = cant_Preguntas_evaluado - (cont_ceros_total + cont_uno_total + cont_dos_total + cont_tres_total + cont_cuatro_total);

            //System.out.println("ceros -> " +cont_ceros_total +" Uno......."+ cont_uno_total+" Dos ..."+ cont_dos_total+" Tres ..."+ cont_tres_total +"cuatro .." +cont_cuatro_total+" vacios .." +cont_vacios_total);
            // System.out.println(" veririfca ..."+ (cantPregCardinales+ cantPregEspecificas)+"....resuletas ..." + (cont_ceros_total+cont_uno_total+cont_dos_total+cont_tres_total+cont_cuatro_total));
            if (cont_vacios_total > 0) {
                int vacios_tot = (int) cont_vacios_total;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alerta", "Por favor, responda todas las preguntas de la evaluación.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                //swicth=true;
            } else {
                getSelected_general();
                /*float porce_cero = (cont_ceros_total/cant_Preguntas_evaluado)*100;
                 float porce_cuatro = (cont_cuatro_total/cant_Preguntas_evaluado)*100;
                 if(porce_cero>75){
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "El porcentaje de respuestas 0 debe ser menor al 75%, porcentaje actual:" + porce_cero +"%");
                 FacesContext.getCurrentInstance().addMessage(null, message);   
                 swicth=true;
                 }      
                 if(porce_cuatro>75){
                 FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "El porcentaje de respuestas 4 debe ser menor al 75%, porcentaje actual:" + porce_cuatro +"%");
                 FacesContext.getCurrentInstance().addMessage(null, message);   
                 swicth=true;
                 } */
            }
            /*if(swicth==false){
             getSelected_general();
             } */
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void getSelected_general() throws Exception {
        try {

            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            competenciasCardinales();
            if (flgcargoprinEvaluado == true) {
                if (idgrupoOcuEvaluado.equals(30901)) {
                    competenciasEspecificas_Directivos();
                } else if (idgrupoOcuEvaluado.equals(30902)) {
                    competenciasEspecificas_Docentes_Auxiliares_Entrenadores();
                } else if (idgrupoOcuEvaluado.equals(30903)) {
                    competenciasEspecificas_No_Docentes();
                } else if (idgrupoOcuEvaluado.equals(30904)) {
                    competenciasEspecificas_Administrativo();
                } else if (idgrupoOcuEvaluado.equals(30905)) {
                    competenciasEspecificas_Mantenimiento();
                }
            }
            updateProgreso = evDesempenoService.sp_ed_update_progreso(idEvaluadoEvaluador, 100, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            recuperaProgreso = evDesempenoService.sp_ed_existe_encuesta(idEvaluadoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (recuperaProgreso.isEmpty() || recuperaProgreso.get(0).getProgreso() == 0) {
                progreso = 0;
                flagEncuesta = false;
            } else {
                progreso = recuperaProgreso.get(0).getProgreso();
                flagEncuesta = true;
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            limpiarEncuesta();
            List<CantidadBean> a = evDesempenoService.sp_ed_cargar_estados(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaEstados = evDesempenoService.sp_ed_lista_estados(usuarioLoginBean.getPersonalBean().getNroDoc(), listaDatosEvaluador.get(0).getIdCargo(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaEvaluaciones = evDesempenoService.sp_ed_lista_evaluaciones(usuarioLoginBean.getPersonalBean().getNroDoc(), idcargoEvaluador, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            validaEncuestasCompletas = evDesempenoService.sp_ed_evaluaciones_completas(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            Integer nroEvalCompletas = 0;
            if (validaEncuestasCompletas.size() == 0) {
                validaEncuestasCompletas = evDesempenoService.sp_ed_evaluaciones_completas_default(usuarioLoginBean.getPersonalBean().getNroDoc(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            }
            for (int i = 0; i < validaEncuestasCompletas.size(); i++) {
                if (validaEncuestasCompletas.get(i).getFlag() == 0) {
                    nroEvalCompletas += 1;
                }
            }
            if (nroEvalCompletas == validaEncuestasCompletas.size()) {
                flgevalcompletas = true;
            }
            //listaCargosEvaluador = evDesempenoService.sp_ed_cargos_evaluador(usuarioLoginBean.getPersonalBean().getNroDoc()); 
            if (listaCargosEvaluador.size() > 1) {
                flgindicaciones = true;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEncuesta() {
        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            lista_espiritu_familia = new ArrayList<>();
            lista_sencillez = new ArrayList<>();
            lista_solidaridad = new ArrayList<>();
            lista_amor_trabajo = new ArrayList<>();
            /*Cardinales*/
            lista_espiritu_familia = evDesempenoService.sp_ed_lista_preguntas(1, "30906", anio);
            lista_sencillez = evDesempenoService.sp_ed_lista_preguntas(2, "30906", anio);
            lista_solidaridad = evDesempenoService.sp_ed_lista_preguntas(3, "30906", anio);
            lista_amor_trabajo = evDesempenoService.sp_ed_lista_preguntas(4, "30906", anio);
            tot_ceros = 0;
            tot_cuatro = 0;
            tot_vacios = 0;

            if (idgrupoOcuEvaluado.equals(30901)) {
                lista_dir_liderazgo = new ArrayList<>();
                lista_dir_gestion = new ArrayList<>();
                lista_dir_conflictos = new ArrayList<>();
                lista_dir_responsabilidad = new ArrayList<>();
                lista_dir_confidencialidad = new ArrayList<>();
                /*Directivo*/
                lista_dir_liderazgo = evDesempenoService.sp_ed_lista_preguntas(5, idgrupoOcuEvaluado.toString(), anio);
                lista_dir_gestion = evDesempenoService.sp_ed_lista_preguntas(6, idgrupoOcuEvaluado.toString(), anio);
                lista_dir_conflictos = evDesempenoService.sp_ed_lista_preguntas(7, idgrupoOcuEvaluado.toString(), anio);
                lista_dir_responsabilidad = evDesempenoService.sp_ed_lista_preguntas(8, idgrupoOcuEvaluado.toString(), anio);
                lista_dir_confidencialidad = evDesempenoService.sp_ed_lista_preguntas(9, idgrupoOcuEvaluado.toString(), anio);
                tot_ceros_dir = 0;
                tot_cuatro_dir = 0;
                tot_vacios_dir = 0;

            } else if (idgrupoOcuEvaluado.equals(30902)) {
                lista_dea_evangeliza = new ArrayList<>();
                lista_dea_compromiso = new ArrayList<>();
                lista_dea_trabajo = new ArrayList<>();
                lista_dea_planificacion = new ArrayList<>();
                lista_dea_confidencialidad = new ArrayList<>();
                /* Docente - Auxiliar - Entrenador*/
                lista_dea_evangeliza = evDesempenoService.sp_ed_lista_preguntas(18, idgrupoOcuEvaluado.toString(), anio);
                lista_dea_compromiso = evDesempenoService.sp_ed_lista_preguntas(19, idgrupoOcuEvaluado.toString(), anio);
                lista_dea_trabajo = evDesempenoService.sp_ed_lista_preguntas(12, idgrupoOcuEvaluado.toString(), anio);
                lista_dea_planificacion = evDesempenoService.sp_ed_lista_preguntas(13, idgrupoOcuEvaluado.toString(), anio);
                lista_dea_confidencialidad = evDesempenoService.sp_ed_lista_preguntas(9, idgrupoOcuEvaluado.toString(), anio);
                tot_ceros_doc = 0;
                tot_cuatro_doc = 0;
                tot_vacios_doc = 0;

            } else if (idgrupoOcuEvaluado.equals(30903)) {
                lista_nodocente_evangeliza = new ArrayList<>();
                lista_nodocente_capacidad = new ArrayList<>();
                lista_nodocente_trabajo = new ArrayList<>();
                lista_nodocente_planificacion = new ArrayList<>();
                lista_nodocente_confidencialidad = new ArrayList<>();
                /*No Docente*/
                lista_nodocente_evangeliza = evDesempenoService.sp_ed_lista_preguntas(10, idgrupoOcuEvaluado.toString(), anio);
                lista_nodocente_capacidad = evDesempenoService.sp_ed_lista_preguntas(11, idgrupoOcuEvaluado.toString(), anio);
                lista_nodocente_trabajo = evDesempenoService.sp_ed_lista_preguntas(12, idgrupoOcuEvaluado.toString(), anio);
                lista_nodocente_planificacion = evDesempenoService.sp_ed_lista_preguntas(13, idgrupoOcuEvaluado.toString(), anio);
                lista_nodocente_confidencialidad = evDesempenoService.sp_ed_lista_preguntas(9, idgrupoOcuEvaluado.toString(), anio);
                tot_ceros_nodoc = 0;
                tot_cuatro_nodoc = 0;
                tot_vacios_nodoc = 0;

            } else if (idgrupoOcuEvaluado.equals(30904)) {
                lista_adm_evangeliza = new ArrayList<>();
                lista_adm_gestion = new ArrayList<>();
                lista_adm_trabajo = new ArrayList<>();
                lista_adm_proactividad = new ArrayList<>();
                lista_adm_confiabilidad = new ArrayList<>();
                /* Administrativo */
                lista_adm_evangeliza = evDesempenoService.sp_ed_lista_preguntas(14, idgrupoOcuEvaluado.toString(), anio);
                lista_adm_gestion = evDesempenoService.sp_ed_lista_preguntas(15, idgrupoOcuEvaluado.toString(), anio);
                lista_adm_trabajo = evDesempenoService.sp_ed_lista_preguntas(12, idgrupoOcuEvaluado.toString(), anio);
                lista_adm_proactividad = evDesempenoService.sp_ed_lista_preguntas(16, idgrupoOcuEvaluado.toString(), anio);
                lista_adm_confiabilidad = evDesempenoService.sp_ed_lista_preguntas(17, idgrupoOcuEvaluado.toString(), anio);
                tot_ceros_adm = 0;
                tot_cuatro_adm = 0;
                tot_vacios_adm = 0;

            } else if (idgrupoOcuEvaluado.equals(30905)) {
                lista_man_evangeliza = new ArrayList<>();
                lista_man_distribucion = new ArrayList<>();
                lista_man_trabajo = new ArrayList<>();
                lista_man_dinamismo = new ArrayList<>();
                lista_man_implementos = new ArrayList<>();
                /*Mantenimiento*/
                lista_man_evangeliza = evDesempenoService.sp_ed_lista_preguntas(23, idgrupoOcuEvaluado.toString(), anio);
                lista_man_distribucion = evDesempenoService.sp_ed_lista_preguntas(20, idgrupoOcuEvaluado.toString(), anio);
                lista_man_trabajo = evDesempenoService.sp_ed_lista_preguntas(24, idgrupoOcuEvaluado.toString(), anio);
                lista_man_dinamismo = evDesempenoService.sp_ed_lista_preguntas(21, idgrupoOcuEvaluado.toString(), anio);
                lista_man_implementos = evDesempenoService.sp_ed_lista_preguntas(22, idgrupoOcuEvaluado.toString(), anio);
                tot_ceros_man = 0;
                tot_cuatro_man = 0;
                tot_vacios_man = 0;

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    /**
     * **************************** COMPETENCIAS CARDINALES
     * *****************************************
     */
    public void competenciasCardinales() {
        getSelected_espiritu();
        getSelected_Sencillez();
        getSelected_Solidaridad();
        getSelected_amor_Trabajo();
    }

    public String cant_ceros_cardinales() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_espiritu_familia) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_sencillez) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_solidaridad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_amor_trabajo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_espiritu() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_espiritu_familia) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 1, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_Sencillez() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_sencillez) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 2, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_Solidaridad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_solidaridad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 3, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_amor_Trabajo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_amor_trabajo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 4, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    /**
     * **************************** COMPETENCIAS ESPECIFICAS
     * *****************************************
     */
    /* Directivo */
    public void competenciasEspecificas_Directivos() {
        getSelected_dir_liderazgo();
        getSelected_dir_gestion();
        getSelected_dir_conflictos();
        getSelected_dir_responsabilidad();
        getSelected_dir_confidencialidad();
    }

    public String cant_ceros_Directivos() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_liderazgo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dir_gestion) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dir_conflictos) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dir_responsabilidad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dir_confidencialidad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_dir_liderazgo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_liderazgo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //System.out.println(" resultado ==> " + selectedRespuesta.get(h).getIdpregunta() + " --- " + selectedRespuesta.get(h).getIdrespuesta());
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 5, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_dir_gestion() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_gestion) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //System.out.println(" resultado ==> " + selectedRespuesta.get(h).getIdpregunta() + " --- " + selectedRespuesta.get(h).getIdrespuesta());                
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 6, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,6,iduniorg,null,0,null,null,null,null);              
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_dir_conflictos() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_conflictos) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,7,iduniorg,null,0,null,null,null,null);              			
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 7, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_dir_responsabilidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_responsabilidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,8,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 8, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_dir_confidencialidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dir_confidencialidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,9,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 9, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    /**
     * ***********************************************************************************************
     */
    /* No Docente */
    public void competenciasEspecificas_No_Docentes() {
        getSelected_nodocente_evangeliza();
        getSelected_nodocente_capacidad();
        getSelected_nodocente_trabajo();
        getSelected_nodocente_planificacion();
        getSelected_nodocente_confidencialidad();
    }

    public String cant_ceros_No_Docentes() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_evangeliza) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_nodocente_capacidad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_nodocente_trabajo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_nodocente_planificacion) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_nodocente_confidencialidad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }

            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }

            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_nodocente_evangeliza() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_evangeliza) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,5,iduniorg,null,0,null,null,null,null);              		
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 10, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_nodocente_capacidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_capacidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,6,iduniorg,null,0,null,null,null,null);              			
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 11, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //
    }

    public String getSelected_nodocente_trabajo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_trabajo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,7,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 12, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_nodocente_planificacion() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_planificacion) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                System.out.println(" resultado ==> " + selectedRespuesta.get(h).getIdpregunta() + " --- " + selectedRespuesta.get(h).getIdrespuesta());
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,8,iduniorg,null,0,null,null,null,null);               				
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 13, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_nodocente_confidencialidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_nodocente_confidencialidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                System.out.println(" resultado ==> " + selectedRespuesta.get(h).getIdpregunta() + " --- " + selectedRespuesta.get(h).getIdrespuesta());
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,9,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 9, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    /**
     * ***********************************************************************************************
     */
    /* Administrativo */
    public void competenciasEspecificas_Administrativo() {
        getSelected_adm_evangeliza();
        getSelected_adm_gestion_tiempo();
        getSelected_adm_trabajo();
        getSelected_adm_proactividad();
        getSelected_adm_confiabilidad();
    }

    public String cant_ceros_Administrativo() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_evangeliza) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_adm_gestion) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_adm_trabajo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_adm_proactividad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_adm_confiabilidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_adm_evangeliza() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_evangeliza) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                System.out.println(" resultado ==> " + selectedRespuesta.get(h).getIdpregunta() + " --- " + selectedRespuesta.get(h).getIdrespuesta());
//                      evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,5,iduniorg,null,0,null,null,null,null);              		
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 14, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_adm_gestion_tiempo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_gestion) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,6,iduniorg,null,0,null,null,null,null);                				
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 15, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_adm_trabajo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_trabajo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,7,iduniorg,null,0,null,null,null,null);              				
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 12, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_adm_proactividad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_proactividad) {
                selectedRespuesta.add(dataItem);
            }

            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,16,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 16, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_adm_confiabilidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_adm_confiabilidad) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,17,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 17, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    /**
     * ***********************************************************************************************
     */
    /* Docente/Auxiliares/Entrenadores */
    public void competenciasEspecificas_Docentes_Auxiliares_Entrenadores() {
        getSelected_doc_evangeliza_educando();
        getSelected_doc_compromiso_estudiante();
        getSelected_doc_trabajo();
        getSelected_doc_planificacion_org();
        getSelected_doc_confidencialidad();
    }

    public String cant_ceros_Docentes_Auxiliares_Entrenadores() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_compromiso) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dea_confidencialidad) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dea_planificacion) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_dea_trabajo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }

            }
            for (PreguntasBean dataItem : lista_dea_evangeliza) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_doc_evangeliza_educando() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_evangeliza) {
                selectedRespuesta.add(dataItem);
            }

            for (int h = 0; h < selectedRespuesta.size(); h++) {
//                      evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,5,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 18, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_doc_compromiso_estudiante() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_compromiso) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,6,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 19, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_doc_trabajo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_trabajo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,7,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 12, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_doc_planificacion_org() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_planificacion) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,16,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 13, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_doc_confidencialidad() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_dea_confidencialidad) {
                selectedRespuesta.add(dataItem);
            }

            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,17,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 9, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public List<MatrizGraficoEDBean> getListaResDirectoresHis() {
        return listaResDirectoresHis;
    }

    public void setListaResDirectoresHis(List<MatrizGraficoEDBean> listaResDirectoresHis) {
        this.listaResDirectoresHis = listaResDirectoresHis;
    }

    public List<UniNegBean> getListaUniNegHistorico() {
        return listaUniNegHistorico;
    }

    public void setListaUniNegHistorico(List<UniNegBean> listaUniNegHistorico) {
        this.listaUniNegHistorico = listaUniNegHistorico;
    }

    /**
     * ***********************************************************************************************
     */
    /* Mantenimiento*/
    public void competenciasEspecificas_Mantenimiento() {
        getSelected_mant_evangeliza();
        getSelected_mant_distribucion_tiempo();
        getSelected_mant_trabajo();
        getSelected_mant_dinamismo();
        getSelected_mant_cuidado_impl();
    }

    public String cant_ceros_Mantenimiento() {

        Integer cont_ceros, cont_uno, cont_dos, cont_tres, cont_cuatro;
        cont_ceros = 0;
        cont_uno = 0;
        cont_dos = 0;
        cont_tres = 0;
        cont_cuatro = 0;
        try {
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_evangeliza) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_man_distribucion) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_man_trabajo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_man_dinamismo) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (PreguntasBean dataItem : lista_man_implementos) {
                if (dataItem.getIdrespuesta() != null) {
                    selectedRespuesta.add(dataItem);
                }
            }
            for (int i = 0; i < selectedRespuesta.size(); i++) {
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 0) {
                    cont_ceros += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 1) {
                    cont_uno += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 2) {
                    cont_dos += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 3) {
                    cont_tres += 1;
                }
                if (selectedRespuesta.get(i).getIdrespuesta() != null && selectedRespuesta.get(i).getIdrespuesta() == 4) {
                    cont_cuatro += 1;
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cont_ceros.toString() + "/" + cont_uno.toString() + "%" + cont_dos.toString() + "$" + cont_tres.toString() + "@" + cont_cuatro.toString(); //.               

    }

    public String getSelected_mant_evangeliza() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_evangeliza) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
//                      evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,5,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 23, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_mant_distribucion_tiempo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_distribucion) {
                selectedRespuesta.add(dataItem);
            }

            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,6,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 20, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_mant_trabajo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_trabajo) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,7,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 24, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_mant_dinamismo() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_dinamismo) {
                selectedRespuesta.add(dataItem);
            }

            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,16,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 21, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getSelected_mant_cuidado_impl() {

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();

            // Items seleccionados.
            selectedRespuesta = new ArrayList<PreguntasBean>();
            for (PreguntasBean dataItem : lista_man_implementos) {
                selectedRespuesta.add(dataItem);
            }
            for (int h = 0; h < selectedRespuesta.size(); h++) {
                //evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(),selectedRespuesta.get(h).getIdrespuesta(),0,17,iduniorg,null,0,null,null,null,null);              
                evDesempenoService.insertEncuesta(selectedRespuesta.get(h).getIdpregunta(), selectedRespuesta.get(h).getIdrespuesta(), 0, 22, idEstado, progreso, 0, uniNegEvaluado, fechaActual, idEvaluadoEvaluador, anio, usuarioLoginBean.getUsuario());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return "selected"; //.
    }

    public String getLastYearTotal() {
        int total = 0;

        for (EstadoBean sale : getListaEstados()) {
            total += sale.getCantidad();
        }
        return String.valueOf(total);
    }

    /**
     * ************************************* REPORTES GRAFICOS
     * *******************************************
     */
    public HorizontalBarChartModel obtenerComDirectoresBarHor(String uniNegocio) throws Exception {
        HoriResDirectivos = new HorizontalBarChartModel();
        ChartSeries cardinal = new ChartSeries();
        cardinal.setLabel("C. Cardinal");
        ChartSeries especifica = new ChartSeries();
        especifica.setLabel("C. Especifica");

        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
        listaUnidadNegocio = resDimensionService.sp_mc_desc_unidadnegocio(uniNegocio);
        nombre_unidad = listaUnidadNegocio.get(0).getNombreUniNeg();

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaResDirectores = evDesempenoService.sp_ed_grafico_directores(uniNegocio, anio);

            if (!listaResDirectores.isEmpty()) {
                for (MatrizGraficoEDBean resDirectores : listaResDirectores) {
                    cardinal.set(resDirectores.getNombre(), resDirectores.getCardinales());
                    especifica.set(resDirectores.getNombre(), resDirectores.getEspecificas());
                }
                HoriResDirectivos.setTitle("Directivos - " + nombre_unidad);
                HoriResDirectivos.setLegendPosition("ne");
                HoriResDirectivos.setAnimate(true);
                HoriResDirectivos.setShowPointLabels(true);
                HoriResDirectivos.setExtender("chartHorizontalExtender1");
                HoriResDirectivos.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                HoriResDirectivos.setSeriesColors("EF5350,0288D1");
                Axis xAxis = HoriResDirectivos.getAxis(AxisType.X);
                xAxis.setMin(0);
                xAxis.setTickInterval("1");
                xAxis.setMax(5);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDirectivos.addSeries(cardinal);
        HoriResDirectivos.addSeries(especifica);
        return HoriResDirectivos;
    }

    public HorizontalBarChartModel obtenerComDirectoresPromBarHor(String uniNegocio) throws Exception {
        HoriResDirectivosPromActual = new HorizontalBarChartModel();
        ChartSeries promedio = new ChartSeries();
        promedio.setLabel("Promedio");
        ChartSeries autoevaluacion = new ChartSeries();
        autoevaluacion.setLabel("Autoevaluación");

        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
        listaUnidadNegocio = resDimensionService.sp_mc_desc_unidadnegocio(uniNegocio);
        nombre_unidad = listaUnidadNegocio.get(0).getNombreUniNeg();

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaResDirectoresHis = evDesempenoService.sp_ed_grafico_directores(uniNegocio, anio);

            if (!listaResDirectoresHis.isEmpty()) {
                for (MatrizGraficoEDBean resDirectores : listaResDirectoresHis) {
                    promedio.set(resDirectores.getNombre(), resDirectores.getProm());
                    autoevaluacion.set(resDirectores.getNombre(), resDirectores.getAutoevaluacion());
                }
                HoriResDirectivosPromActual.setTitle("Promedio General " + nombre_unidad);
                HoriResDirectivosPromActual.setLegendPosition("ne");
                HoriResDirectivosPromActual.setAnimate(true);
                HoriResDirectivosPromActual.setShowPointLabels(true);
                HoriResDirectivosPromActual.setExtender("chartHorizontalExtender1");
                HoriResDirectivosPromActual.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                HoriResDirectivosPromActual.setSeriesColors("FFC107,04B4AE");
                Axis xAxis = HoriResDirectivosPromActual.getAxis(AxisType.X);
                xAxis.setMin(0);
                xAxis.setTickInterval("1");
                xAxis.setMax(5);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDirectivosPromActual.addSeries(promedio);
        HoriResDirectivosPromActual.addSeries(autoevaluacion);
        return HoriResDirectivosPromActual;
    }

    public HorizontalBarChartModel obtenerPromDirectoresBarHor(String uniNegocio) throws Exception {
        HoriResDirectivosProm = new HorizontalBarChartModel();
        ChartSeries ED_2016 = new ChartSeries();
        ED_2016.setLabel("2016");
        ChartSeries ED_2017 = new ChartSeries();
        ED_2017.setLabel("2017");
        ChartSeries ED_2018 = new ChartSeries();
        ED_2018.setLabel("2018");
        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
        listaUnidadNegocio = resDimensionService.sp_mc_desc_unidadnegocio(uniNegocio);
        nombre_unidad = listaUnidadNegocio.get(0).getNombreUniNeg();

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaResDirectoresProm = evDesempenoService.sp_ed_historico_directores(uniNegocio);
            //System.out.println(" lista recuperada medio .........................." + listaResDirectoresProm.size());
            if (!listaResDirectoresProm.isEmpty()) {
                for (HistoricoEDBean ResDirectoresHisProm : listaResDirectoresProm) {
                    ED_2018.set(ResDirectoresHisProm.getNombre(), ResDirectoresHisProm.getED_2018());
                    ED_2017.set(ResDirectoresHisProm.getNombre(), ResDirectoresHisProm.getED_2017());
                    ED_2016.set(ResDirectoresHisProm.getNombre(), ResDirectoresHisProm.getED_2016());
                }
                HoriResDirectivosProm.setTitle("Promedio General - " + nombre_unidad);
                HoriResDirectivosProm.setLegendPosition("ne");
                HoriResDirectivosProm.setAnimate(true);
                HoriResDirectivosProm.setShowPointLabels(true);
                HoriResDirectivosProm.setExtender("chartHorizontalExtender1");
                HoriResDirectivosProm.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                HoriResDirectivosProm.setSeriesColors("EF5350,0288D1,2EFE9A");
                Axis xAxis = HoriResDirectivosProm.getAxis(AxisType.X);
                xAxis.setMin(0);
                xAxis.setTickInterval("1");
                xAxis.setMax(5);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDirectivosProm.addSeries(ED_2016);
        HoriResDirectivosProm.addSeries(ED_2017);
        HoriResDirectivosProm.addSeries(ED_2018);
        return HoriResDirectivosProm;
    }

    public void rowSelectReg(String Unineg) {
        try {

            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaHistoricoProm = evDesempenoService.sp_ed_lista_historico(Unineg);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public HorizontalBarChartModel obtenerHoriResDirectoresEDBarHor() throws Exception {
        HoriResDirectoresED = new HorizontalBarChartModel();
        ChartSeries cardinales = new ChartSeries();
        cardinales.setLabel("C. Cardinal");
        ChartSeries especificas = new ChartSeries();
        especificas.setLabel("C. Especifica");

        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaDirectoresED = evDesempenoService.sp_ed_directores();
            //System.out.println(" lista recuperada arriba .........................." + listaResDirectores.size());
            if (!listaDirectoresED.isEmpty()) {
                for (DirectoresEDBean resDirectoresED : listaDirectoresED) {
                    cardinales.set(resDirectoresED.getUnidad(), resDirectoresED.getCardinal());
                    especificas.set(resDirectoresED.getUnidad(), resDirectoresED.getEspecifica());
                }
                HoriResDirectoresED.setTitle("Directores 2018");
                HoriResDirectoresED.setLegendPosition("ne");
                //HoriResDirectoresED.setStacked(true);
                HoriResDirectoresED.setAnimate(true);
                HoriResDirectoresED.setShowPointLabels(true);
                HoriResDirectoresED.setExtender("chartHorizontalExtender1");
                HoriResDirectoresED.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                Axis xAxis = HoriResDirectoresED.getAxis(AxisType.X);
                xAxis.setMin(0);
                xAxis.setTickInterval("1");
                xAxis.setMax(5);

                //horizontalBaHistorico.setBarPadding(250);FFD700
                HoriResDirectoresED.setSeriesColors("EF5350,0288D1");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDirectoresED.addSeries(cardinales);
        HoriResDirectoresED.addSeries(especificas);
        return HoriResDirectoresED;
    }

    public HorizontalBarChartModel obtenerComDirectoresPromGralHor() throws Exception {
        HoriResDirectoresProm = new HorizontalBarChartModel();
        ChartSeries promedio = new ChartSeries();
        promedio.setLabel("Promedio");
        ChartSeries autoevaluacion = new ChartSeries();
        autoevaluacion.setLabel("Autoevaluación");

        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();

        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaResDirectoresPromHis = evDesempenoService.sp_ed_grafico_directores_unidadnegocio();
            if (!listaResDirectoresPromHis.isEmpty()) {
                for (DirectoresPromBean resDirectores : listaResDirectoresPromHis) {
                    promedio.set(resDirectores.getNombreUniNeg(), resDirectores.getPromedio());
                    autoevaluacion.set(resDirectores.getNombreUniNeg(), resDirectores.getAutoevaluacion());
                }
                HoriResDirectoresProm.setTitle("Promedio General Directores 2018");
                HoriResDirectoresProm.setLegendPosition("ne");
                //HoriResDirectores.setStacked(true);
                HoriResDirectoresProm.setAnimate(true);
                HoriResDirectoresProm.setShowPointLabels(true);
                HoriResDirectoresProm.setExtender("chartHorizontalExtender1");
                HoriResDirectoresProm.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                HoriResDirectoresProm.setSeriesColors("FFCA28,04B4AE");
                Axis xAxis = HoriResDirectoresProm.getAxis(AxisType.X);
                xAxis.setMin(0);
                xAxis.setTickInterval("1");
                xAxis.setMax(5);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDirectoresProm.addSeries(promedio);
        HoriResDirectoresProm.addSeries(autoevaluacion);
        return HoriResDirectoresProm;
    }

    public String grabarRegHistorico() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
                //System.out.print("unineg ... "+uniNegHis+"  cargo ....."+idcargoHis.toString() +"   anio select ...."+anioHis+"   promedio......"+promedioHis);
                Integer cad = evaluacionDesempenoService.sp_ed_update_Promedio_Historico(uniNegHis, idcargoHis, anioHis, promedioHis);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaHistoricoProm = evaluacionDesempenoService.sp_ed_lista_historico(uniNegHis);
                //listaUsuariosConCaja = cajeroService.obtenerUsuarioConCaja(user.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void onHistoricoChange() throws Exception {
        Float res;
        EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        res = evaluacionDesempenoService.sp_ed_recupera_promedio(uninegHis, nombreHis, idcargoHis, anioHis);
        if (res == null) {
            promedioHis = 0;
        } else {
            promedioHis = res;
        }
    }

    public void recargarGraficosED() {

        try {

            //String parametro = (String) new MaristaUtils().requestObtenerObjeto("unid");
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            String parametro = uniNegRecupera;
            System.out.print(" unidad de negocio recuperada ........." + parametro);
            listaUnidadNegocio = resDimensionService.sp_mc_desc_unidadnegocio(parametro);
            nombre_unidad = listaUnidadNegocio.get(0).getNombreUniNeg();
            obtenerComDirectoresBarHor(parametro);
            obtenerPromDirectoresBarHor(parametro);
            obtenerComDirectoresPromBarHor(parametro);
            unidadSector = uniNegRecupera;

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void recargarGraficosConsED() {

        try {

            obtenerHoriResDirectoresEDBarHor();
            obtenerComDirectoresPromGralHor();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onListaIndicadores() {
        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaIndicadores = evDesempenoService.sp_ed_lista_indicadores(tipo_Planilla_Indicador);
            System.out.println("..............." + tipo_Planilla_Indicador);
            valor_indicador = tipo_Planilla_Indicador;
            if (tipo_Planilla_Indicador == 0) {
                flgindi = false;
            } else {
                flgindi = true;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectIndicador() {
        try {
            listaDetIndicadores = new ArrayList<>();
            String tipoPlanillaIndicador, uniNegRI, nombre_indicador;
            //grupoOcuIndicador grupoOcuIndicador = "";
            tipoPlanillaIndicador = "";
            nombre_indicador = "";
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            uniNegRI = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            tituloIndicador = evDesempenoService.sp_ed_nombreindicador(id_Indicador);
            //uninegIndicador=" and p.unineg='"+ uniNegRI +"'";
            System.out.print(" indica ......" + nom_per_Indicador);
            if (nom_per_Indicador.equalsIgnoreCase("")) {
                nombre_indicador = " ";
            }
            if (!nom_per_Indicador.equalsIgnoreCase("")) {
                nombre_indicador = " and  p.apepat+' '+p.apemat+' '+p.nombre like + '%" + nom_per_Indicador + "%' ";
            }
            if (!tipo_Planilla_Indicador.equals(0)) {
                tipoPlanillaIndicador = " and p.idTipoNivelesColegio=" + String.valueOf(tipo_Planilla_Indicador);
            } else {
                tipoPlanillaIndicador = " ";
            }

            //grupoOcuIndicador
            query = nombre_indicador + tipoPlanillaIndicador;
            String flgasigna = "";
            if (flgAsignar.equals(false)) {
                flgasigna = "0";
            } else {
                flgasigna = "1";
            }
            System.out.print(" QUERYYYY ........." + query + " unineg " + uniNegRI + " id_Indicador " + id_Indicador + " anioIndicador " + anioIndicador + " anioIndicador   " + anioIndicador);
            listaDetIndicadores = evDesempenoService.sp_ed_lista_filtros_dinamicos(query, uniNegRI, id_Indicador.toString(), anioIndicador.toString(), flgasigna);
            System.out.print(" LISTADO ........." + listaDetIndicadores.size());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiaIndicador() {
        try {

            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            String uniNegRI;
            uniNegRI = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            id_Indicador = 1;
            //grp_Ocu_Indicador = 0;
            tipo_Planilla_Indicador = 0;
            flgAsignar = false;
            tituloIndicador = evDesempenoService.sp_ed_nombreindicador(id_Indicador);
            listaDetIndicadores = evDesempenoService.sp_ed_lista_filtros_dinamicos("", uniNegRI, id_Indicador.toString(), anioIndicador.toString(), "0");

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertDetallesIndicadores() {
        String pagina = null;
        try {
            if (pagina == null) {

                EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                String uninegRI1 = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String fechacrea = sdf.format(new Date());
                selectedIndicadores = new ArrayList<DetalleIndicadorBean>();

                for (DetalleIndicadorBean dataItem : listaDetIndicadores) {
                    if (dataItem.getPromedio() != null) {
                        selectedIndicadores.add(dataItem);
                    }
                }
                for (int i = 0; i < selectedIndicadores.size(); i++) {
                    Integer indicador = id_Indicador;
                    Integer anio = anioIndicador;
                    Integer validaPersonal = evaluacionDesempenoService.sp_ed_consulta_detalle_indicador(selectedIndicadores.get(i).getCodper(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), selectedIndicadores.get(i).getIdTipoNivelesColegio(), indicador, anio);
                    if (validaPersonal == 0) {
                        evaluacionDesempenoService.sp_ed_insert_detalle_indicador(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), selectedIndicadores.get(i).getCodper(), selectedIndicadores.get(i).getIdTipoNivelesColegio(), indicador, selectedIndicadores.get(i).getPromedio(), usuarioLoginBean.getUsuario(), "NULL", fechacrea, anio);
                    } else {
                        evaluacionDesempenoService.sp_ed_update_detalle_indicador(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), selectedIndicadores.get(i).getCodper(), selectedIndicadores.get(i).getIdTipoNivelesColegio(), indicador, selectedIndicadores.get(i).getPromedio(), anio);
                    }
                }
                /*for (int i = 0; i < listaDetIndicadores.size(); i++) {
                    Integer idTipoNivelesColegio = listaDetIndicadores.get(i).getIdTipoNivelesColegio();
                    String codper = listaDetIndicadores.get(i).getCodper();
                    String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
                    Integer indicador = id_Indicador;
                    Float prom;
                    if (listaDetIndicadores.get(i).getPromedio() == null) {
                        prom = Float.parseFloat("0");
                    } else {
                        prom = listaDetIndicadores.get(i).getPromedio();
                    }
                    Integer anio = anioIndicador;
                    //if (!listaDetIndicadores.get(i).getPromedio().equalsIgnoreCase("") || !listaDetIndicadores.get(i).getPromedio().equalsIgnoreCase("NULL")) {
                    Integer validaPersonal = evaluacionDesempenoService.sp_ed_consulta_detalle_indicador(codper, unineg, idTipoNivelesColegio, indicador, anio);
                    if (validaPersonal == 0) {
                        evaluacionDesempenoService.sp_ed_insert_detalle_indicador(unineg, codper, idTipoNivelesColegio, indicador, prom, usuarioLoginBean.getUsuario(), "NULL", fechacrea, anio);
                    } else {
                        evaluacionDesempenoService.sp_ed_update_detalle_indicador(unineg, codper, idTipoNivelesColegio, indicador, prom, anio);
                    }
                    //}
                }*/
                String flgasigna = "";
                if (flgAsignar.equals(false)) {
                    flgasigna = "0";
                } else {
                    flgasigna = "1";
                }
                tituloIndicador = evaluacionDesempenoService.sp_ed_nombreindicador(id_Indicador);
                listaDetIndicadores = evaluacionDesempenoService.sp_ed_lista_filtros_dinamicos(query, uninegRI1, id_Indicador.toString(), anioIndicador.toString(), flgasigna);

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    /*  demos  code */
    private LineChartModel obtenerCompetencias(String codpersonalg, Integer param_idcargo) {
        System.out.print("obtener competencia codperrr.. " + codpersonalg);
        // codpersonalg=param_codper;
        LineChartModel model = new LineChartModel();
        ChartSeries autoevaluacion = new ChartSeries();
        autoevaluacion.setLabel("Autoevaluación");
        ChartSeries promedio = new ChartSeries();
        promedio.setLabel("Promedio");
        try {
            String cod = codpersonalg;
            Integer cargo = param_idcargo;
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listademo = evDesempenoService.sp_ed_detallecompetencias(cod, cargo);
            System.out.print("cantidad de lineas " + listademo.size());
            for (ED_DetalleComObservables res : listademo) {
                autoevaluacion.set(res.getCompetencia(), res.getAutoEvaluacion());
                promedio.set(res.getCompetencia(), res.getPromedio());
            }
            //model.setLegendPosition("c");
            /*barResDimension.setShowDatatip(true); 82E0AA,ff7f7f,FFC107,E91E63,29B6F6,66FF00,607D8B  */
            model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
            model.setSeriesColors("FFA500,00CED1");
            model.setShowPointLabels(true);
            model.setAnimate(true);
            model.setExtender("chartLineExtender");
            model.getAxes().put(AxisType.X, new CategoryAxis("Competencias"));
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Puntajes");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        model.addSeries(promedio);
        model.addSeries(autoevaluacion);
        return model;
    }

    public void rowSelectGrafico(Object event) {
        flgGrafCompetencia = true;
        System.out.print("flgGrafCompetencia..............." + flgGrafCompetencia);
        try {
//          EvaluacionDesempenoService evaluacionService = BeanFactory.getEvaluacionDesempenoService(); 
            detalleNivelCargo = new DetalleNivelCargo();
            detalleNivelCargo = (DetalleNivelCargo) event;
            //System.out.print("codigo codperrrrrrrrrrrrrrrrrrrr..." + nivelBean.getCodper());
            param_codper = detalleNivelCargo.getCodper();
            param_idcargo = detalleNivelCargo.getIdcargo();
            modelCompetencia = obtenerCompetencias(param_codper, param_idcargo);
            modelCompetencia.setTitle("Evaluación por Competencias");
            modelCompetencia.setAnimate(true);
            //modelCompetencia.setLegendPosition("c");
            modelCompetencia.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
            modelCompetencia.setShowPointLabels(true);
            modelCompetencia.setExtender("chartLineExtender");
            modelCompetencia.getAxes().put(AxisType.X, new CategoryAxis("Competencias"));
            Axis yAxis = modelCompetencia.getAxis(AxisType.Y);
            yAxis.setLabel("Puntajes");
            yAxis.setMin(0);
            yAxis.setMax(5);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public BarChartModel obtenerTipoNiveles() {
        barNiveles = new BarChartModel();
        ChartSeries Promedio = new ChartSeries();
        Promedio.setLabel("Promedio ");
        ChartSeries Ponderado = new ChartSeries();
        Ponderado.setLabel("Promedio Ponderado");
        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaNiveles = evDesempenoService.sp_ed_tiponiveles();
            /*String  val_no_satisfecho;*/
            if (!listaNiveles.isEmpty()) {
                for (ED_IniPriSEC resnivelBar : listaNiveles) {
                    Ponderado.set(resnivelBar.getDescripcion(), resnivelBar.getPonderado());
                    Promedio.set(resnivelBar.getDescripcion(), resnivelBar.getPromedio());
                }
                barNiveles.setTitle("Resultado por G.O");
                barNiveles.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true); 82E0AA,ff7f7f,FFC107,E91E63,29B6F6,66FF00,607D8B  */
                barNiveles.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                barNiveles.setShowPointLabels(true);
                barNiveles.setAnimate(true);
                /*FF1493,8B008B,8A2BE2,1E90FF,00CED1,00FFFF,3CB371*/
 /*8B0000,FA8072,FFDEAD,FFF6AD,7FFFD4,1E90FF,8B008B
                 ,ACFA58,F3F781,ffc256,FF7F50,C71585*/
                barNiveles.setSeriesColors("FFA500,00CED1");
                barNiveles.setExtender("chartBarExtender"); //
                Axis yAxis = barNiveles.getAxis(AxisType.Y);
                yAxis.setMin(0);
                yAxis.setTickInterval("1");
                yAxis.setMax(5);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barNiveles.addSeries(Promedio);
        barNiveles.addSeries(Ponderado);
        return barNiveles;
    }

    public void itemSelect1(ItemSelectEvent event) throws Exception {
        flgGrafCompetencia = false;
        param_detNivel = 0;
        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
        //String GO=evDesempenoService.sp_ed_nombre_GO(); 
        if (unineg.equalsIgnoreCase("CHAMPS") || unineg.equalsIgnoreCase("BARINA") || unineg.equalsIgnoreCase("SANJOC")
                || unineg.equalsIgnoreCase("SANLUI") ) {
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
                param_detNivel = 30204;
                paramTitle = "Inicial";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
                param_detNivel = 30204;
                paramTitle = "Inicial";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 0) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 1) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 0) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 1) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 0) {
                param_detNivel = 30206;
                paramTitle = "Entrenadores";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 1) {
                param_detNivel = 30206;
                paramTitle = "Entrenadores";
            }
            if (event.getItemIndex() == 6 && event.getSeriesIndex() == 0) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
            if (event.getItemIndex() == 6 && event.getSeriesIndex() == 1) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
        } else if (unineg.equalsIgnoreCase("CHAMPC")) {
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 0) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 1) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 0) {
                param_detNivel = 30206;
                paramTitle = "Entrenadores";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 1) {
                param_detNivel = 30206;
                paramTitle = "Entrenadores";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 0) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 1) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
        } else if (unineg.equalsIgnoreCase("SANJOH") || unineg.equalsIgnoreCase("STAROS") || unineg.equalsIgnoreCase("STAMAR")) {
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
                param_detNivel = 30204;
                paramTitle = "Inicial";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
                param_detNivel = 30204;
                paramTitle = "Inicial";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 0) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 1) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 0) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 1) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 0) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
            if (event.getItemIndex() == 5 && event.getSeriesIndex() == 1) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
        } else if (unineg.equalsIgnoreCase("SANJOS")) {
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
                param_detNivel = 30201;
                paramTitle = "Directivos";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 0) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 1) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 0) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
            if (event.getItemIndex() == 4 && event.getSeriesIndex() == 1) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
        } else if (unineg.equalsIgnoreCase("CRISTO")) {
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
                param_detNivel = 30203;
                paramTitle = "Primaria";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
                param_detNivel = 30202;
                paramTitle = "Secundaria";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
                param_detNivel = 30205;
                paramTitle = "Administrativo";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 0) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
            if (event.getItemIndex() == 3 && event.getSeriesIndex() == 1) {
                param_detNivel = 30207;
                paramTitle = "Servicios / Mantenimiento";
            }
        }
        listaDetalleNiveles = evDesempenoService.sp_ed_detallexnivel(param_detNivel);
        listaDetalleNivelesCargo = new ArrayList<>();
    }

    /*public void itemSelect2(ItemSelectEvent event) throws Exception {
        flgGrafCompetencia = false;
        param_detNivel = 0;
        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        if (event.getItemIndex() == 2 && event.getSeriesIndex() == 0) {
            param_detNivel = 30202;
        }
        if (event.getItemIndex() == 2 && event.getSeriesIndex() == 1) {
            param_detNivel = 30202;
        }
        if (event.getItemIndex() == 1 && event.getSeriesIndex() == 0) {
            param_detNivel = 30203;
        }
        if (event.getItemIndex() == 1 && event.getSeriesIndex() == 1) {
            param_detNivel = 30203;
        }
        if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
            param_detNivel = 30204;
        }
        if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
            param_detNivel = 30204;
        }
        listaDetalleNiveles = evDesempenoService.sp_ed_detallexnivel(param_detNivel);
        listaDetalleNivelesCargo = new ArrayList<>();        
    }*/
 /* public BarChartModel obtenerTipoNivelDocentes() {
        barIniPriSec = new BarChartModel();
        ChartSeries promedio = new ChartSeries();
        promedio.setLabel("Promedio");
        ChartSeries ponderado = new ChartSeries();
        ponderado.setLabel("Promedio Ponderado");
        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaIniPriSec = evDesempenoService.sp_ed_grafico_docentes();
            String  val_no_satisfecho;
            if (!listaIniPriSec.isEmpty()) {
                for (ED_IniPriSEC resDocBar : listaIniPriSec) {
                    promedio.set(resDocBar.getDescripcion(), resDocBar.getPromedio());
                    ponderado.set(resDocBar.getDescripcion(), resDocBar.getPonderado());
                }
                barIniPriSec.setTitle("Promedio Docentes (Inicial - Primaria - Secundaria)");
                barIniPriSec.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true);   
                barIniPriSec.setShowPointLabels(true);
                barIniPriSec.setAnimate(true);
                barIniPriSec.setExtender("chartBarSatisfaccionExtender"); //
                barIniPriSec.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                Axis yAxis = barIniPriSec.getAxis(AxisType.Y);
                yAxis.setMin(0);
                yAxis.setTickInterval("1");
                yAxis.setMax(5);
                barIniPriSec.setSeriesColors("FFCA28,04B4AE");

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barIniPriSec.addSeries(promedio);
        barIniPriSec.addSeries(ponderado);
        return barIniPriSec;
    }*/
    public void viewPanelIndividual() {
        try {

            flgindividual = 1;
            ima_individual = "menu_chart1";
            ima_consolidado = "chart_pastel2_black";

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void viewPanelConsolidado() {
        try {

            flgindividual = 0;
            ima_individual = "menu_chart1_black";
            ima_consolidado = "chart_pastel2";

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirPDFIndividual(Object event) {
        ServletOutputStream out = null;

        try {
            personalBean = (ED_PersonalBean) event;
            String cargo = personalBean.getCargo();
            String codper = personalBean.getCodper();
            String unineg = personalBean.getUnineg();
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repIndividual.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaRepIndividualBean> listaRepIndividual = new ArrayList<>();
            listaRepIndividual = evaluacionDesempenoService.sp_reporte_individual(unineg, codper);

            if (!listaRepIndividual.isEmpty()) {
                //for (int i = 0; i < listaRepIndividual.size(); i++) {
                List<Cargos> listacargos = new ArrayList<>();
                listacargos = evaluacionDesempenoService.sp_ed_lista_cargos(codper, unineg);
                System.out.println("Lista cargos" + listacargos.size());
                listaRepIndividual.get(0).setListaCargos(listacargos);
                //}
                //for (int j = 0; j < listaRepIndividual.size(); j++) {
                List<ResumenEvaDesempeno> listaResumenEva = new ArrayList<>();
                listaResumenEva = evaluacionDesempenoService.sp_ed_resultados_eva_resumen(codper, unineg);
                System.out.println("Lista resumen" + listaResumenEva.size());
                listaRepIndividual.get(0).setListaResumen(listaResumenEva);
                //}                
            }

            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepIndividual);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Individual_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFIndicadoresPlanilla(Integer tipo_Planilla) {
        ServletOutputStream out = null;
        List<IndicadoresPlanilla> listaRepIndicadorPlanilla = new ArrayList<>();
        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repIndicadoresPlanilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            System.out.println("indicador : " + valor_indicador);
            listaRepIndicadorPlanilla = evaluacionDesempenoService.sp_ed_Rep_Indicadores_Planilla(valor_indicador);
            System.out.println("cantidad de lista  : " + listaRepIndicadorPlanilla.size());
            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepIndicadorPlanilla);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Individual_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFFichaRetroalimentacion(Object event) {
        ServletOutputStream out = null;
        List<FichaRetroalimentacionBean> fichaRetroalimentacion = new ArrayList<>();
        try {
            personalBean = (ED_PersonalBean) event;
            String codper = personalBean.getCodper();
            String unineg = personalBean.getUnineg();
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repFichaRetroAlimentacion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            System.out.println("codper : " + codper + "  unineg : " + unineg);
            fichaRetroalimentacion = evaluacionDesempenoService.sp_ed_ficha_retroalimentacion(codper, unineg);
            if (!fichaRetroalimentacion.isEmpty()) {
                List<Cargos> listacargos = new ArrayList<>();
                listacargos = evaluacionDesempenoService.sp_ed_lista_cargos(codper, unineg);
                fichaRetroalimentacion.get(0).setListaCargos(listacargos);
            }
            System.out.println("cantidad de lista retro : " + fichaRetroalimentacion.size());
            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(fichaRetroalimentacion);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Individual_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFIndividual_Planilla() {
        ServletOutputStream out = null;

        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repIndividual_Planilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EvaRepIndividualPlanBean> listaRepIndividual_Planilla = new ArrayList<>();
            String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaRepIndividual_Planilla = evaluacionDesempenoService.sp_ed_repIndividual_planilla(unineg);

            if (!listaRepIndividual_Planilla.isEmpty()) {
                for (int i = 0; i < listaRepIndividual_Planilla.size(); i++) {
                    List<Cargos> listacargos = new ArrayList<>();
                    listacargos = evaluacionDesempenoService.sp_ed_lista_cargos(listaRepIndividual_Planilla.get(i).getCodper(), unineg);
                    System.out.println("Lista cargos" + listacargos.size());
                    listaRepIndividual_Planilla.get(i).setListaCargos(listacargos);

                    /*List<ResumenEvaDesempeno> listaResumenEva = new ArrayList<>();
                    listaResumenEva = evaluacionDesempenoService.sp_ed_resultados_eva_resumen(listaRepIndividual_Planilla.get(i).getCodper(), unineg);
                    System.out.println("Lista resumen" + listaResumenEva.size());
                    listaRepIndividual_Planilla.get(i).setListaResumen(listaResumenEva);*/
                }
            }

            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepIndividual_Planilla);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Rep_Individual_Planilla_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFficha_Entrevista() {
        ServletOutputStream out = null;

        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/report6_subrep2_FichaRetro.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<FichaEntrevista> listaRepFicha_Entrevista = new ArrayList<>();
            String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaRepFicha_Entrevista = evaluacionDesempenoService.sp_ed_rep_fichaEntravista(unineg);

            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepFicha_Entrevista);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Rep_Ficha_Entrevista" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirPDFconsolidado() {
        ServletOutputStream out = null;

        try {

            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repConsolidado_Planilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<RepConsolidado> listaRepConsolidado = new ArrayList<>();
            String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaRepConsolidado = evaluacionDesempenoService.sp_ed_rep_consolidado(unineg);

            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepConsolidado);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Rep_Consolidado" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void rowSelectGraficoCargos(Object event) {
        flgGrafCompetencia = false;
        System.out.print("flgGrafCompetencia..............." + flgGrafCompetencia);
        try {
            EvaluacionDesempenoService evaluacionService = BeanFactory.getEvaluacionDesempenoService();
            flgGrafCompetencia_cargo = false;
            System.out.println("flag   ... :" + flgGrafCompetencia);
            nivelBean = (ED_DetallexNivel) event;
            param_codper_cargo = nivelBean.getCodper();
            param_unineg_cargo = nivelBean.getUnineg();
            listaDetalleNivelesCargo = evaluacionService.sp_ed_detallexcargo(param_codper_cargo, param_unineg_cargo);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onrowSelectGraficoCargos(SelectEvent event) {
        flgGrafCompetencia = false;
        System.out.print("flgGrafCompetencia..............." + flgGrafCompetencia);
        try {
            EvaluacionDesempenoService evaluacionService = BeanFactory.getEvaluacionDesempenoService();
            flgGrafCompetencia_cargo = true;
            System.out.println("flag   ... :" + flgGrafCompetencia);
            System.out.println("UNINEG ... :" + nivelBean.getUnineg());
            param_codper_cargo = nivelBean.getCodper();
            param_unineg_cargo = nivelBean.getUnineg();
            listaDetalleNivelesCargo = evaluacionService.sp_ed_detallexcargo(param_codper_cargo, param_unineg_cargo);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirPDFFRetroCon() {
        ServletOutputStream out = null;
        List<FichaRetroConsolidado> fichaRetroalimentacionCon = new ArrayList<>();
        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repFichaRetroAlimentacionCon.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            //System.out.println("codper : " + codper + "  unineg : " + unineg);
            String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            fichaRetroalimentacionCon = evaluacionDesempenoService.sp_ed_ficha_retroalimentacionConsolidado(unineg);
            if (!fichaRetroalimentacionCon.isEmpty()) {
                for (int i = 0; i < fichaRetroalimentacionCon.size(); i++) {
                    List<Cargos> listacargos = new ArrayList<>();
                    listacargos = evaluacionDesempenoService.sp_ed_lista_cargos(fichaRetroalimentacionCon.get(i).getCodper(), unineg);
                    fichaRetroalimentacionCon.get(i).setListaCargos(listacargos);
                }
            }
            System.out.println("cantidad de lista retro : " + fichaRetroalimentacionCon.size());
            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(fichaRetroalimentacionCon);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Reporte_Ficha_RetroCon_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    /*  public BarChartModel obtenerTipoNivelDirectivos() {
        barDirectivos = new BarChartModel();
        ChartSeries promedio = new ChartSeries();
        promedio.setLabel("Promedio");
        ChartSeries ponderado = new ChartSeries();
        ponderado.setLabel("Promedio Ponderado");
        try {
            EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            listaDirectivos = evDesempenoService.sp_ed_grafico_directivos();
            String  val_no_satisfecho;
            if (!listaDirectivos.isEmpty()) {
                for (ED_IniPriSEC resDirBar : listaDirectivos) {
                    promedio.set(resDirBar.getDescripcion(), resDirBar.getPromedio());
                    ponderado.set(resDirBar.getDescripcion(), resDirBar.getPonderado());
                }
                barDirectivos.setTitle("Promedio Directivos");
                barDirectivos.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true);   
                barDirectivos.setShowPointLabels(true);
                barDirectivos.setAnimate(true);
                barDirectivos.setExtender("chartBarSatisfaccionExtender"); //
                barDirectivos.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                Axis yAxis = barDirectivos.getAxis(AxisType.Y);
                yAxis.setMin(0);
                yAxis.setTickInterval("1");
                yAxis.setMax(5);
                barDirectivos.setSeriesColors("FFCA28,04B4AE");

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barDirectivos.addSeries(promedio);
        barDirectivos.addSeries(ponderado);
        return barDirectivos;
    }*/

 /*public void itemSelect3(ItemSelectEvent event) throws Exception {
        flgGrafCompetencia = false;
        param_detNivel = 0;
        EvaluacionDesempenoService evDesempenoService = BeanFactory.getEvaluacionDesempenoService();
        if (event.getItemIndex() == 0 && event.getSeriesIndex() == 0) {
            param_detNivel = 30201;
        }
        if (event.getItemIndex() == 0 && event.getSeriesIndex() == 1) {
            param_detNivel = 30201;
        }
        listaDetalleNiveles = evDesempenoService.sp_ed_detallexnivel(param_detNivel);
        listaDetalleNivelesCargo = new ArrayList<>();        
    }*/
    public void exportXlsLista() throws IOException {

        List<IndicadoresPlanilla> listaRepIndicadorPlanilla = new ArrayList<>();

        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repIndicadoresPlanillaall.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);

            System.out.println("indicador : " + valor_indicador);
            listaRepIndicadorPlanilla = evaluacionDesempenoService.sp_ed_Rep_Indicadores_Planilla(valor_indicador);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepIndicadorPlanilla);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Listado_Estudiantes" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void exportXlsConsolidadoLista() throws IOException {

        List<IndicadoresPlanilla> listaRepIndicadorPlanilla = new ArrayList<>();

        try {
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repConsolidado_XLS.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<RepConsolidado> listaRepConsolidadoxls = new ArrayList<>();
            String unineg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaRepConsolidadoxls = evaluacionDesempenoService.sp_ed_rep_consolidado(unineg);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepConsolidadoxls);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Listado_Consolidado" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void imprimirPDFficha_EntrevistaIn(Object event) {
        ServletOutputStream out = null;
        //List<FichaRetroalimentacionBean> fichaRetroalimentacion = new ArrayList<>();        
        try {
            personalBean = (ED_PersonalBean) event;
            String codper = personalBean.getCodper();
            String unineg = personalBean.getUnineg();
            EvaluacionDesempenoService evaluacionDesempenoService = BeanFactory.getEvaluacionDesempenoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/report6_subrep2_FichaRetro.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<FichaEntrevista> listaRepFicha_EntrevistaIn = new ArrayList<>();
            listaRepFicha_EntrevistaIn = evaluacionDesempenoService.sp_ed_fichaEntrevista(codper, unineg);

            //System.out.println("lista tamaño :" + listaRepIndividual.size()); 
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRepFicha_EntrevistaIn);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Rep_Ficha_EntrevistaIn" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public List<AnioHistBean> getListaAnioIndicadores() {
        return listaAnioIndicadores;
    }

    public void setListaAnioIndicadores(List<AnioHistBean> listaAnioIndicadores) {
        this.listaAnioIndicadores = listaAnioIndicadores;
    }

    public Integer getFlg_consolidado() {
        return flg_consolidado;
    }

    public void setFlg_consolidado(Integer flg_consolidado) {
        this.flg_consolidado = flg_consolidado;
    }

    public void onListaReportes() {
        try {
            var_reporte = var_reporte;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onListaReportesCons() {
        try {
            var_reporteCons = var_reporteCons;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Integer getVar_reporteCons() {
        return var_reporteCons;
    }

    public void setVar_reporteCons(Integer var_reporteCons) {
        this.var_reporteCons = var_reporteCons;
    }

    public Integer getVar_reporte() {
        return var_reporte;
    }

    public void setVar_reporte(Integer var_reporte) {
        this.var_reporte = var_reporte;
    }

    public String getParamTitle() {
        return paramTitle;
    }

    public void setParamTitle(String paramTitle) {
        this.paramTitle = paramTitle;
    }

    public List<ED_IniPriSEC> getListaDirectivos() {
        return listaDirectivos;
    }

    public void setListaDirectivos(List<ED_IniPriSEC> listaDirectivos) {
        this.listaDirectivos = listaDirectivos;
    }

    public boolean isFlgGrafCompetencia_cargo() {
        return flgGrafCompetencia_cargo;
    }

    public void setFlgGrafCompetencia_cargo(boolean flgGrafCompetencia_cargo) {
        this.flgGrafCompetencia_cargo = flgGrafCompetencia_cargo;
    }

    public List<DetalleNivelCargo> getListaDetalleNivelesCargo() {
        return listaDetalleNivelesCargo;
    }

    public void setListaDetalleNivelesCargo(List<DetalleNivelCargo> listaDetalleNivelesCargo) {
        this.listaDetalleNivelesCargo = listaDetalleNivelesCargo;
    }

    public String getParam_unineg_cargo() {
        return param_unineg_cargo;
    }

    public void setParam_unineg_cargo(String param_unineg_cargo) {
        this.param_unineg_cargo = param_unineg_cargo;
    }

    public String getParam_codper_cargo() {
        return param_codper_cargo;
    }

    public void setParam_codper_cargo(String param_codper_cargo) {
        this.param_codper_cargo = param_codper_cargo;
    }

    public boolean isFlgrepIndividual_01() {
        return flgrepIndividual_01;
    }

    public void setFlgrepIndividual_01(boolean flgrepIndividual_01) {
        this.flgrepIndividual_01 = flgrepIndividual_01;
    }

    public String getCbo_tipo_rep_indiviadual() {
        return cbo_tipo_rep_indiviadual;
    }

    public void setCbo_tipo_rep_indiviadual(String cbo_tipo_rep_indiviadual) {
        this.cbo_tipo_rep_indiviadual = cbo_tipo_rep_indiviadual;
    }

    public boolean isFlgrepIndividual() {
        return flgrepIndividual;
    }

    public void setFlgrepIndividual(boolean flgrepIndividual) {
        this.flgrepIndividual = flgrepIndividual;
    }

    public Integer getValor_indicador() {
        return valor_indicador;
    }

    public void setValor_indicador(Integer valor_indicador) {
        this.valor_indicador = valor_indicador;
    }

    public List<DetalleIndicadorBean> getSelectedIndicadores() {
        return selectedIndicadores;
    }

    public void setSelectedIndicadores(List<DetalleIndicadorBean> selectedIndicadores) {
        this.selectedIndicadores = selectedIndicadores;
    }

    public boolean isFlgindi() {
        return flgindi;
    }

    public void setFlgindi(boolean flgindi) {
        this.flgindi = flgindi;
    }

    public ED_PersonalBean getPersonalBean() {
        return personalBean;
    }

    public void setPersonalBean(ED_PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public String getIma_individual() {
        return ima_individual;
    }

    public void setIma_individual(String ima_individual) {
        this.ima_individual = ima_individual;
    }

    public String getIma_consolidado() {
        return ima_consolidado;
    }

    public void setIma_consolidado(String ima_consolidado) {
        this.ima_consolidado = ima_consolidado;
    }

    public Integer getFlgindividual() {
        return flgindividual;
    }

    public void setFlgindividual(Integer flgindividual) {
        this.flgindividual = flgindividual;
    }

    public List<ED_PersonalBean> getListaVistaReporte() {
        return listaVistaReporte;
    }

    public void setListaVistaReporte(List<ED_PersonalBean> listaVistaReporte) {
        this.listaVistaReporte = listaVistaReporte;
    }

    public boolean isFlgGrafCompetencia() {
        return flgGrafCompetencia;
    }

    public void setFlgGrafCompetencia(boolean flgGrafCompetencia) {
        this.flgGrafCompetencia = flgGrafCompetencia;
    }

    public Integer getParam_idcargo() {
        return param_idcargo;
    }

    public void setParam_idcargo(Integer param_idcargo) {
        this.param_idcargo = param_idcargo;
    }

    public LineChartModel getModelCompetencia() {
        return modelCompetencia;
    }

    public void setModelCompetencia(LineChartModel modelCompetencia) {
        this.modelCompetencia = modelCompetencia;
    }

    public List<ED_DetalleComObservables> getListademo() {
        return listademo;
    }

    public void setListademo(List<ED_DetalleComObservables> listademo) {
        this.listademo = listademo;
    }

    public BarChartModel getBarNiveles() {
        return barNiveles;
    }

    public void setBarNiveles(BarChartModel barNiveles) {
        this.barNiveles = barNiveles;
    }

    public List<ED_DetalleCompetencias> getListaAutoPromedio() {
        return listaAutoPromedio;
    }

    public void setListaAutoPromedio(List<ED_DetalleCompetencias> listaAutoPromedio) {
        this.listaAutoPromedio = listaAutoPromedio;
    }

    public String getParam_codper() {
        return param_codper;
    }

    public void setParam_codper(String param_codper) {
        this.param_codper = param_codper;
    }

    public Integer getParam_detNivel() {
        return param_detNivel;
    }

    public void setParam_detNivel(Integer param_detNivel) {
        this.param_detNivel = param_detNivel;
    }

    public List<ED_IniPriSEC> getListaNiveles() {
        return listaNiveles;
    }

    public void setListaNiveles(List<ED_IniPriSEC> listaNiveles) {
        this.listaNiveles = listaNiveles;
    }

    public List<ED_DetallexNivel> getListaDetalleNiveles() {
        return listaDetalleNiveles;
    }

    public void setListaDetalleNiveles(List<ED_DetallexNivel> listaDetalleNiveles) {
        this.listaDetalleNiveles = listaDetalleNiveles;
    }

    public ED_DetallexNivel getNivelBean() {
        return nivelBean;
    }

    public void setNivelBean(ED_DetallexNivel nivelBean) {
        this.nivelBean = nivelBean;
    }

    public List<ED_IniPriSEC> getListaIniPriSec() {
        return listaIniPriSec;
    }

    public void setListaIniPriSec(List<ED_IniPriSEC> listaIniPriSec) {
        this.listaIniPriSec = listaIniPriSec;
    }

    public String getUnidadSector() {
        return unidadSector;
    }

    public void setUnidadSector(String unidadSector) {
        this.unidadSector = unidadSector;
    }

    public Integer getFlgimprimir() {
        return flgimprimir;
    }

    public void setFlgimprimir(Integer flgimprimir) {
        this.flgimprimir = flgimprimir;
    }

    public String getTituloIndicador() {
        return tituloIndicador;
    }

    public void setTituloIndicador(String tituloIndicador) {
        this.tituloIndicador = tituloIndicador;
    }

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        EvaluacionDesempenoMB.query = query;
    }

    public static String getUniNegRecupera() {
        return uniNegRecupera;
    }

    public static void setUniNegRecupera(String uniNegRecupera) {
        EvaluacionDesempenoMB.uniNegRecupera = uniNegRecupera;
    }

    public Boolean getFlgAsignar() {
        return flgAsignar;
    }

    public void setFlgAsignar(Boolean flgAsignar) {
        this.flgAsignar = flgAsignar;
    }

    public Integer getAnioIndicador() {
        return anioIndicador;
    }

    public void setAnioIndicador(Integer anioIndicador) {
        this.anioIndicador = anioIndicador;
    }

    public String getNom_per_Indicador() {
        return nom_per_Indicador;
    }

    public void setNom_per_Indicador(String nom_per_Indicador) {
        this.nom_per_Indicador = nom_per_Indicador;
    }

    public Integer getId_Indicador() {
        return id_Indicador;
    }

    public void setId_Indicador(Integer id_Indicador) {
        this.id_Indicador = id_Indicador;
    }

    public Integer getGrp_Ocu_Indicador() {
        return grp_Ocu_Indicador;
    }

    public void setGrp_Ocu_Indicador(Integer grp_Ocu_Indicador) {
        this.grp_Ocu_Indicador = grp_Ocu_Indicador;
    }

    public Integer getTipo_Planilla_Indicador() {
        return tipo_Planilla_Indicador;
    }

    public void setTipo_Planilla_Indicador(Integer tipo_Planilla_Indicador) {
        this.tipo_Planilla_Indicador = tipo_Planilla_Indicador;
    }

    public List<DetalleIndicadorBean> getListaDetIndicadores() {
        return listaDetIndicadores;
    }

    public void setListaDetIndicadores(List<DetalleIndicadorBean> listaDetIndicadores) {
        this.listaDetIndicadores = listaDetIndicadores;
    }

    public List<IndicadoresBean> getListaGrupoOcupacional() {
        return listaGrupoOcupacional;
    }

    public void setListaGrupoOcupacional(List<IndicadoresBean> listaGrupoOcupacional) {
        this.listaGrupoOcupacional = listaGrupoOcupacional;
    }

    public List<IndicadoresBean> getListaTipoPlanilla() {
        return listaTipoPlanilla;
    }

    public void setListaTipoPlanilla(List<IndicadoresBean> listaTipoPlanilla) {
        this.listaTipoPlanilla = listaTipoPlanilla;
    }

    public List<IndicadoresBean> getListaIndicadores() {
        return listaIndicadores;
    }

    public void setListaIndicadores(List<IndicadoresBean> listaIndicadores) {
        this.listaIndicadores = listaIndicadores;
    }

    public HorizontalBarChartModel getHoriResDirectoresED() {
        return HoriResDirectoresED;
    }

    public void setHoriResDirectoresED(HorizontalBarChartModel HoriResDirectoresED) {
        this.HoriResDirectoresED = HoriResDirectoresED;
    }

    public List<DirectoresEDBean> getListaDirectoresED() {
        return listaDirectoresED;
    }

    public void setListaDirectoresED(List<DirectoresEDBean> listaDirectoresED) {
        this.listaDirectoresED = listaDirectoresED;
    }

    public ED_HistoricoBean getEd_HistoricoBean() {
        return ed_HistoricoBean;
    }

    public void setEd_HistoricoBean(ED_HistoricoBean ed_HistoricoBean) {
        this.ed_HistoricoBean = ed_HistoricoBean;
    }

    public String getNombreHis() {
        return nombreHis;
    }

    public void setNombreHis(String nombreHis) {
        this.nombreHis = nombreHis;
    }

    public String getCargoHis() {
        return cargoHis;
    }

    public void setCargoHis(String cargoHis) {
        this.cargoHis = cargoHis;
    }

    public String getObreHis() {
        return obreHis;
    }

    public void setObreHis(String obreHis) {
        this.obreHis = obreHis;
    }

    public String getUninegHis() {
        return uninegHis;
    }

    public void setUninegHis(String uninegHis) {
        this.uninegHis = uninegHis;
    }

    public Integer getAnioHis() {
        return anioHis;
    }

    public void setAnioHis(Integer anioHis) {
        this.anioHis = anioHis;
    }

    public float getPromedioHis() {
        return promedioHis;
    }

    public void setPromedioHis(float promedioHis) {
        this.promedioHis = promedioHis;
    }

    public HistoricoEDListBean getHistoricoEDListBean() {
        return historicoEDListBean;
    }

    public void setHistoricoEDListBean(HistoricoEDListBean historicoEDListBean) {
        this.historicoEDListBean = historicoEDListBean;
    }

    public List<HistoricoEDListBean> getListaHistoricoProm() {
        return listaHistoricoProm;
    }

    public void setListaHistoricoProm(List<HistoricoEDListBean> listaHistoricoProm) {
        this.listaHistoricoProm = listaHistoricoProm;
    }

    public HorizontalBarChartModel getHoriResDirectoresProm() {
        return HoriResDirectoresProm;
    }

    public void setHoriResDirectoresProm(HorizontalBarChartModel HoriResDirectoresProm) {
        this.HoriResDirectoresProm = HoriResDirectoresProm;
    }

    public List<HistoricoEDBean> getListaResDirectoresProm() {
        return listaResDirectoresProm;
    }

    public void setListaResDirectoresProm(List<HistoricoEDBean> listaResDirectoresProm) {
        this.listaResDirectoresProm = listaResDirectoresProm;
    }

    public List<UniNegBean> getListaUnidadNegocio() {
        return listaUnidadNegocio;
    }

    public void setListaUnidadNegocio(List<UniNegBean> listaUnidadNegocio) {
        this.listaUnidadNegocio = listaUnidadNegocio;
    }

    public List<MatrizGraficoEDBean> getListaResDirectores() {
        return listaResDirectores;
    }

    public String getNombre_unidad() {
        return nombre_unidad;
    }

    public void setNombre_unidad(String nombre_unidad) {
        this.nombre_unidad = nombre_unidad;
    }

    /**
     * *****************************************************************************************************
     */
    public void setListaResDirectores(List<MatrizGraficoEDBean> listaResDirectores) {
        this.listaResDirectores = listaResDirectores;
    }

    public String getFotoEvaluado() {
        return fotoEvaluado;
    }

    public void setFotoEvaluado(String fotoEvaluado) {
        this.fotoEvaluado = fotoEvaluado;
    }

    public Integer getFlg_save() {
        return flg_save;
    }

    public void setFlg_save(Integer flg_save) {
        this.flg_save = flg_save;
    }

    public Integer getIndex_especifica() {
        return index_especifica;
    }

    public void setIndex_especifica(Integer index_especifica) {
        this.index_especifica = index_especifica;
    }

    public List<EvaluacionBean> getValidaEncuestasCompletas() {
        return validaEncuestasCompletas;
    }

    public void setValidaEncuestasCompletas(List<EvaluacionBean> validaEncuestasCompletas) {
        this.validaEncuestasCompletas = validaEncuestasCompletas;
    }

    public boolean isFlgevalcompletas() {
        return flgevalcompletas;
    }

    public void setFlgevalcompletas(boolean flgevalcompletas) {
        this.flgevalcompletas = flgevalcompletas;
    }

    /**
     * ***********************************************************************************************
     */
    public String getApellidoEvaluador() {
        return apellidoEvaluador;
    }

    public void setApellidoEvaluador(String apellidoEvaluador) {
        this.apellidoEvaluador = apellidoEvaluador;
    }

    public EvaluacionBean getEvaluacionBean() {
        return evaluacionBean;
    }

    public Integer getCantPregCardinales() {
        return cantPregCardinales;
    }

    public void setCantPregCardinales(Integer cantPregCardinales) {
        this.cantPregCardinales = cantPregCardinales;
    }

    public Integer getCantPregEspecificas() {
        return cantPregEspecificas;
    }

    public void setCantPregEspecificas(Integer cantPregEspecificas) {
        this.cantPregEspecificas = cantPregEspecificas;
    }

    public Integer getIdcargoEvaluador() {
        return idcargoEvaluador;
    }

    public void setIdcargoEvaluador(Integer idcargoEvaluador) {
        this.idcargoEvaluador = idcargoEvaluador;
    }

    public String getCodPerEvaluador() {
        return codPerEvaluador;
    }

    public void setCodPerEvaluador(String codPerEvaluador) {
        this.codPerEvaluador = codPerEvaluador;
    }

    public String getCodPerEvaluado() {
        return codPerEvaluado;
    }

    public void setCodPerEvaluado(String codPerEvaluado) {
        this.codPerEvaluado = codPerEvaluado;
    }

    public boolean isFlgcargoprinEvaluado() {
        return flgcargoprinEvaluado;
    }

    public void setFlgcargoprinEvaluado(boolean flgcargoprinEvaluado) {
        this.flgcargoprinEvaluado = flgcargoprinEvaluado;
    }

    /**
     * ***********************************************************************************************
     */
    public void setEvaluacionBean(EvaluacionBean evaluacionBean) {
        this.evaluacionBean = evaluacionBean;
    }

    public List<PreguntasBean> getListaPreguntas() {
        if (listaPreguntas == null) {
            listaPreguntas = new ArrayList<>();
        }
        return listaPreguntas;
    }

    public void setListaPreguntas(List<PreguntasBean> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    /**
     * @return the listaEvaluaciones
     */
    public List<EvaluacionBean> getListaEvaluaciones() {
        if (listaEvaluaciones == null) {
            listaEvaluaciones = new ArrayList<>();
        }
        return listaEvaluaciones;
    }

    /**
     * @param listaEvaluaciones the listaEvaluaciones to set
     */
    public void setListaEvaluaciones(List<EvaluacionBean> listaEvaluaciones) {
        this.listaEvaluaciones = listaEvaluaciones;
    }

    public List<EstadoBean> getListaEstados() {
        if (listaEstados == null) {
            listaEstados = new ArrayList<>();
        }
        return listaEstados;
    }

    public void setListaEstados(List<EstadoBean> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<PreguntasBean> getselectedRespuesta() {
        if (selectedRespuesta == null) {
            selectedRespuesta = new ArrayList<>();
        }
        return selectedRespuesta;
    }

    public void setselectedRespuesta(List<PreguntasBean> selectedDataList) {
        this.selectedRespuesta = selectedDataList;
    }

    public List<PreguntasBean> getLista_espiritu_familia() {
        if (lista_espiritu_familia == null) {
            lista_espiritu_familia = new ArrayList<>();
        }
        return lista_espiritu_familia;
    }

    public void setLista_espiritu_familia(List<PreguntasBean> lista_espiritu_familia) {
        this.lista_espiritu_familia = lista_espiritu_familia;
    }

    public List<PreguntasBean> getLista_sencillez() {
        if (lista_sencillez == null) {
            lista_sencillez = new ArrayList<>();
        }
        return lista_sencillez;
    }

    public void setLista_sencillez(List<PreguntasBean> lista_sencillez) {
        this.lista_sencillez = lista_sencillez;
    }

    public List<PreguntasBean> getLista_solidaridad() {
        if (lista_solidaridad == null) {
            lista_solidaridad = new ArrayList<>();
        }
        return lista_solidaridad;
    }

    public void setLista_solidaridad(List<PreguntasBean> lista_solidaridad) {
        this.lista_solidaridad = lista_solidaridad;
    }

    public List<PreguntasBean> getLista_amor_trabajo() {
        if (lista_amor_trabajo == null) {
            lista_amor_trabajo = new ArrayList<>();
        }
        return lista_amor_trabajo;
    }

    public void setLista_amor_trabajo(List<PreguntasBean> lista_amor_trabajo) {
        this.lista_amor_trabajo = lista_amor_trabajo;
    }

    public List<PreguntasBean> getSelectedRespuesta() {
        if (selectedRespuesta == null) {
            selectedRespuesta = new ArrayList<>();
        }
        return selectedRespuesta;
    }

    public void setSelectedRespuesta(List<PreguntasBean> selectedRespuesta) {
        this.selectedRespuesta = selectedRespuesta;
    }

    public String getIduniorg() {
        return iduniorg;
    }

    public void setIduniorg(String iduniorg) {
        this.iduniorg = iduniorg;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public String getApellidoEvaluado() {
        return apellidoEvaluado;
    }

    public void setApellidoEvaluado(String apellidoEvaluado) {
        this.apellidoEvaluado = apellidoEvaluado;
    }

    public String getCargoEvaludo() {
        return cargoEvaludo;
    }

    public void setCargoEvaludo(String cargoEvaludo) {
        this.cargoEvaludo = cargoEvaludo;
    }

    public Integer getIdgrupoOcuEvaluado() {
        return idgrupoOcuEvaluado;
    }

    public void setIdgrupoOcuEvaluado(Integer idgrupoOcuEvaluado) {
        this.idgrupoOcuEvaluado = idgrupoOcuEvaluado;
    }

    public List<ProgresoBean> getRecuperaProgreso() {
        return recuperaProgreso;
    }

    public void setRecuperaProgreso(List<ProgresoBean> recuperaProgreso) {
        this.recuperaProgreso = recuperaProgreso;
    }

    public List<ProgresoBean> getUpdateProgreso() {
        return updateProgreso;
    }

    public void setUpdateProgreso(List<ProgresoBean> updateProgreso) {
        this.updateProgreso = updateProgreso;
    }

    public float getProgreso() {
        return progreso;
    }

    public void setProgreso(float progreso) {
        this.progreso = progreso;
    }

    public Integer getIdEvaluadoEvaluador() {
        return idEvaluadoEvaluador;
    }

    public void setIdEvaluadoEvaluador(Integer idEvaluadoEvaluador) {
        this.idEvaluadoEvaluador = idEvaluadoEvaluador;
    }

    public String getUniNegEvaluado() {
        return uniNegEvaluado;
    }

    public void setUniNegEvaluado(String uniNegEvaluado) {
        this.uniNegEvaluado = uniNegEvaluado;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isFlagEncuesta() {
        return flagEncuesta;
    }

    public void setFlagEncuesta(boolean flagEncuesta) {
        this.flagEncuesta = flagEncuesta;
    }

    public List<CargosEvaluadorBean> getListaCargosEvaluador() {
        return listaCargosEvaluador;
    }

    public void setListaCargosEvaluador(List<CargosEvaluadorBean> listaCargosEvaluador) {
        this.listaCargosEvaluador = listaCargosEvaluador;
    }

    public String getNroEvaluados() {
        return nroEvaluados;
    }

    public void setNroEvaluados(String nroEvaluados) {
        this.nroEvaluados = nroEvaluados;
    }

    public Integer getTot_ceros_dir() {
        return tot_ceros_dir;
    }

    public void setTot_ceros_dir(Integer tot_ceros_dir) {
        this.tot_ceros_dir = tot_ceros_dir;
    }

    public Integer getTot_cuatro_dir() {
        return tot_cuatro_dir;
    }

    public void setTot_cuatro_dir(Integer tot_cuatro_dir) {
        this.tot_cuatro_dir = tot_cuatro_dir;
    }

    public Integer getTot_vacios_dir() {
        return tot_vacios_dir;
    }

    public void setTot_vacios_dir(Integer tot_vacios_dir) {
        this.tot_vacios_dir = tot_vacios_dir;
    }

    public Integer getTot_ceros_nodoc() {
        return tot_ceros_nodoc;
    }

    public void setTot_ceros_nodoc(Integer tot_ceros_nodoc) {
        this.tot_ceros_nodoc = tot_ceros_nodoc;
    }

    public Integer getTot_cuatro_nodoc() {
        return tot_cuatro_nodoc;
    }

    public void setTot_cuatro_nodoc(Integer tot_cuatro_nodoc) {
        this.tot_cuatro_nodoc = tot_cuatro_nodoc;
    }

    public Integer getTot_vacios_nodoc() {
        return tot_vacios_nodoc;
    }

    public void setTot_vacios_nodoc(Integer tot_vacios_nodoc) {
        this.tot_vacios_nodoc = tot_vacios_nodoc;
    }

    public Integer getTot_ceros_adm() {
        return tot_ceros_adm;
    }

    public void setTot_ceros_adm(Integer tot_ceros_adm) {
        this.tot_ceros_adm = tot_ceros_adm;
    }

    public Integer getTot_cuatro_adm() {
        return tot_cuatro_adm;
    }

    public void setTot_cuatro_adm(Integer tot_cuatro_adm) {
        this.tot_cuatro_adm = tot_cuatro_adm;
    }

    public Integer getTot_vacios_adm() {
        return tot_vacios_adm;
    }

    public void setTot_vacios_adm(Integer tot_vacios_adm) {
        this.tot_vacios_adm = tot_vacios_adm;
    }

    public Integer getTot_ceros_doc() {
        return tot_ceros_doc;
    }

    public void setTot_ceros_doc(Integer tot_ceros_doc) {
        this.tot_ceros_doc = tot_ceros_doc;
    }

    public Integer getTot_cuatro_doc() {
        return tot_cuatro_doc;
    }

    public void setTot_cuatro_doc(Integer tot_cuatro_doc) {
        this.tot_cuatro_doc = tot_cuatro_doc;
    }

    public Integer getTot_vacios_doc() {
        return tot_vacios_doc;
    }

    public void setTot_vacios_doc(Integer tot_vacios_doc) {
        this.tot_vacios_doc = tot_vacios_doc;
    }

    public Integer getTot_ceros_man() {
        return tot_ceros_man;
    }

    public void setTot_ceros_man(Integer tot_ceros_man) {
        this.tot_ceros_man = tot_ceros_man;
    }

    public Integer getTot_cuatro_man() {
        return tot_cuatro_man;
    }

    public void setTot_cuatro_man(Integer tot_cuatro_man) {
        this.tot_cuatro_man = tot_cuatro_man;
    }

    public Integer getTot_vacios_man() {
        return tot_vacios_man;
    }

    public void setTot_vacios_man(Integer tot_vacios_man) {
        this.tot_vacios_man = tot_vacios_man;
    }

    public String getUniNegHis() {
        return uniNegHis;
    }

    public void setUniNegHis(String uniNegHis) {
        this.uniNegHis = uniNegHis;
    }

    public Integer getIdcargoHis() {
        return idcargoHis;
    }

    public void setIdcargoHis(Integer idcargoHis) {
        this.idcargoHis = idcargoHis;
    }

    /**
     * *******************************
     */
    public List<PreguntasBean> getLista_dir_liderazgo() {
        if (lista_dir_liderazgo == null) {
            lista_dir_liderazgo = new ArrayList<>();
        }
        return lista_dir_liderazgo;
    }

    public void setLista_dir_liderazgo(List<PreguntasBean> lista_dir_liderazgo) {
        this.lista_dir_liderazgo = lista_dir_liderazgo;
    }

    public List<PreguntasBean> getLista_dir_gestion() {
        if (lista_dir_gestion == null) {
            lista_dir_gestion = new ArrayList<>();
        }
        return lista_dir_gestion;
    }

    public void setLista_dir_gestion(List<PreguntasBean> lista_dir_gestion) {
        this.lista_dir_gestion = lista_dir_gestion;
    }

    public List<PreguntasBean> getLista_dir_conflictos() {
        if (lista_dir_conflictos == null) {
            lista_dir_conflictos = new ArrayList<>();
        }
        return lista_dir_conflictos;
    }

    public void setLista_dir_conflictos(List<PreguntasBean> lista_dir_conflictos) {
        this.lista_dir_conflictos = lista_dir_conflictos;
    }

    public List<PreguntasBean> getLista_dir_responsabilidad() {
        if (lista_dir_responsabilidad == null) {
            lista_dir_responsabilidad = new ArrayList<>();
        }
        return lista_dir_responsabilidad;
    }

    public void setLista_dir_responsabilidad(List<PreguntasBean> lista_dir_responsabilidad) {
        this.lista_dir_responsabilidad = lista_dir_responsabilidad;
    }

    public List<PreguntasBean> getLista_dir_confidencialidad() {
        if (lista_dir_confidencialidad == null) {
            lista_dir_confidencialidad = new ArrayList<>();
        }
        return lista_dir_confidencialidad;
    }

    public void setLista_dir_confidencialidad(List<PreguntasBean> lista_dir_confidencialidad) {
        this.lista_dir_confidencialidad = lista_dir_confidencialidad;
    }

    public List<PreguntasBean> getLista_nodocente_evangeliza() {
        if (lista_nodocente_evangeliza == null) {
            lista_nodocente_evangeliza = new ArrayList<>();
        }
        return lista_nodocente_evangeliza;
    }

    public void setLista_nodocente_evangeliza(List<PreguntasBean> lista_nodocente_evangeliza) {
        this.lista_nodocente_evangeliza = lista_nodocente_evangeliza;
    }

    public List<PreguntasBean> getLista_nodocente_capacidad() {
        if (lista_nodocente_capacidad == null) {
            lista_nodocente_capacidad = new ArrayList<>();
        }
        return lista_nodocente_capacidad;
    }

    public void setLista_nodocente_capacidad(List<PreguntasBean> lista_nodocente_capacidad) {
        this.lista_nodocente_capacidad = lista_nodocente_capacidad;
    }

    public List<PreguntasBean> getLista_nodocente_trabajo() {
        if (lista_nodocente_trabajo == null) {
            lista_nodocente_trabajo = new ArrayList<>();
        }
        return lista_nodocente_trabajo;
    }

    public void setLista_nodocente_trabajo(List<PreguntasBean> lista_nodocente_trabajo) {
        this.lista_nodocente_trabajo = lista_nodocente_trabajo;
    }

    public List<PreguntasBean> getLista_nodocente_planificacion() {
        if (lista_nodocente_planificacion == null) {
            lista_nodocente_planificacion = new ArrayList<>();
        }
        return lista_nodocente_planificacion;
    }

    public void setLista_nodocente_planificacion(List<PreguntasBean> lista_nodocente_planificacion) {
        this.lista_nodocente_planificacion = lista_nodocente_planificacion;
    }

    public List<PreguntasBean> getLista_nodocente_confidencialidad() {
        if (lista_nodocente_confidencialidad == null) {
            lista_nodocente_confidencialidad = new ArrayList<>();
        }
        return lista_nodocente_confidencialidad;
    }

    public void setLista_nodocente_confidencialidad(List<PreguntasBean> lista_nodocente_confidencialidad) {
        this.lista_nodocente_confidencialidad = lista_nodocente_confidencialidad;
    }

    public List<PreguntasBean> getLista_adm_evangeliza() {
        if (lista_adm_evangeliza == null) {
            lista_adm_evangeliza = new ArrayList<>();
        }
        return lista_adm_evangeliza;
    }

    public void setLista_adm_evangeliza(List<PreguntasBean> lista_adm_evangeliza) {
        this.lista_adm_evangeliza = lista_adm_evangeliza;
    }

    public List<PreguntasBean> getLista_adm_gestion() {
        if (lista_adm_gestion == null) {
            lista_adm_gestion = new ArrayList<>();
        }
        return lista_adm_gestion;
    }

    public void setLista_adm_gestion(List<PreguntasBean> lista_adm_gestion) {
        this.lista_adm_gestion = lista_adm_gestion;
    }

    public List<PreguntasBean> getLista_adm_trabajo() {
        if (lista_adm_trabajo == null) {
            lista_adm_trabajo = new ArrayList<>();
        }
        return lista_adm_trabajo;
    }

    public void setLista_adm_trabajo(List<PreguntasBean> lista_adm_trabajo) {
        this.lista_adm_trabajo = lista_adm_trabajo;
    }

    public List<PreguntasBean> getLista_adm_proactividad() {
        if (lista_adm_proactividad == null) {
            lista_adm_proactividad = new ArrayList<>();
        }
        return lista_adm_proactividad;
    }

    public void setLista_adm_proactividad(List<PreguntasBean> lista_adm_proactividad) {
        this.lista_adm_proactividad = lista_adm_proactividad;
    }

    public List<PreguntasBean> getLista_adm_confiabilidad() {
        if (lista_adm_confiabilidad == null) {
            lista_adm_confiabilidad = new ArrayList<>();
        }
        return lista_adm_confiabilidad;
    }

    public void setLista_adm_confiabilidad(List<PreguntasBean> lista_adm_confiabilidad) {
        this.lista_adm_confiabilidad = lista_adm_confiabilidad;
    }

    public List<PreguntasBean> getLista_dea_evangeliza() {
        if (lista_dea_evangeliza == null) {
            lista_dea_evangeliza = new ArrayList<>();
        }
        return lista_dea_evangeliza;
    }

    public void setLista_dea_evangeliza(List<PreguntasBean> lista_dea_evangeliza) {
        this.lista_dea_evangeliza = lista_dea_evangeliza;
    }

    public List<PreguntasBean> getLista_dea_compromiso() {
        if (lista_dea_compromiso == null) {
            lista_dea_compromiso = new ArrayList<>();
        }
        return lista_dea_compromiso;
    }

    public void setLista_dea_compromiso(List<PreguntasBean> lista_dea_compromiso) {
        this.lista_dea_compromiso = lista_dea_compromiso;
    }

    public List<PreguntasBean> getLista_dea_trabajo() {
        if (lista_dea_trabajo == null) {
            lista_dea_trabajo = new ArrayList<>();
        }
        return lista_dea_trabajo;
    }

    public void setLista_dea_trabajo(List<PreguntasBean> lista_dea_trabajo) {
        this.lista_dea_trabajo = lista_dea_trabajo;
    }

    public List<PreguntasBean> getLista_dea_planificacion() {
        if (lista_dea_planificacion == null) {
            lista_dea_planificacion = new ArrayList<>();
        }
        return lista_dea_planificacion;
    }

    public void setLista_dea_planificacion(List<PreguntasBean> lista_dea_planificacion) {
        this.lista_dea_planificacion = lista_dea_planificacion;
    }

    public List<PreguntasBean> getLista_dea_confidencialidad() {
        if (lista_dea_confidencialidad == null) {
            lista_dea_confidencialidad = new ArrayList<>();
        }
        return lista_dea_confidencialidad;
    }

    public void setLista_dea_confidencialidad(List<PreguntasBean> lista_dea_confidencialidad) {
        this.lista_dea_confidencialidad = lista_dea_confidencialidad;
    }

    public List<PreguntasBean> getLista_man_evangeliza() {
        if (lista_man_evangeliza == null) {
            lista_man_evangeliza = new ArrayList<>();
        }
        return lista_man_evangeliza;
    }

    public void setLista_man_evangeliza(List<PreguntasBean> lista_man_evangeliza) {
        this.lista_man_evangeliza = lista_man_evangeliza;
    }

    public List<PreguntasBean> getLista_man_distribucion() {
        if (lista_man_distribucion == null) {
            lista_man_distribucion = new ArrayList<>();
        }
        return lista_man_distribucion;
    }

    public void setLista_man_distribucion(List<PreguntasBean> lista_man_distribucion) {
        this.lista_man_distribucion = lista_man_distribucion;
    }

    public List<PreguntasBean> getLista_man_trabajo() {
        if (lista_man_trabajo == null) {
            lista_man_trabajo = new ArrayList<>();
        }
        return lista_man_trabajo;
    }

    public void setLista_man_trabajo(List<PreguntasBean> lista_man_trabajo) {
        this.lista_man_trabajo = lista_man_trabajo;
    }

    public List<PreguntasBean> getLista_man_dinamismo() {
        if (lista_man_dinamismo == null) {
            lista_man_dinamismo = new ArrayList<>();
        }
        return lista_man_dinamismo;
    }

    public void setLista_man_dinamismo(List<PreguntasBean> lista_man_dinamismo) {
        this.lista_man_dinamismo = lista_man_dinamismo;
    }

    public List<PreguntasBean> getLista_man_implementos() {
        if (lista_man_implementos == null) {
            lista_man_implementos = new ArrayList<>();
        }
        return lista_man_implementos;
    }

    public void setLista_man_implementos(List<PreguntasBean> lista_man_implementos) {
        this.lista_man_implementos = lista_man_implementos;
    }

    public List<DatosPersonalBean> getListaDatosEvaluador() {
        if (listaDatosEvaluador == null) {
            listaDatosEvaluador = new ArrayList<>();
        }
        return listaDatosEvaluador;
    }

    public void setListaDatosEvaluador(List<DatosPersonalBean> listaDatosEvaluador) {
        this.listaDatosEvaluador = listaDatosEvaluador;
    }

    public List<EvaluadoBean> getListaDatosEvaluado() {
        return listaDatosEvaluado;
    }

    public void setListaDatosEvaluado(List<EvaluadoBean> listaDatosEvaluado) {
        this.listaDatosEvaluado = listaDatosEvaluado;
    }

    public UsuarioBean getUsuarioLoginBean() {
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getapellidoEvaluador() {
        return apellidoEvaluador;
    }

    public void setapellidoEvaluador(String apellidoEvaluador) {
        this.apellidoEvaluador = apellidoEvaluador;
    }

    public String getCargoEvaluador() {
        return cargoEvaluador;
    }

    public void setCargoEvaluador(String cargoEvaluador) {
        this.cargoEvaluador = cargoEvaluador;
    }

    public String getObraEvaluador() {
        return obraEvaluador;
    }

    public void setObraEvaluador(String obraEvaluador) {
        this.obraEvaluador = obraEvaluador;
    }

    public List<PreguntaxCompetenciaBean> getListaPreguntasEvaluado() {
        return listaPreguntasEvaluado;
    }

    public void setListaPreguntasEvaluado(List<PreguntaxCompetenciaBean> listaPreguntasEvaluado) {
        this.listaPreguntasEvaluado = listaPreguntasEvaluado;
    }

    public Integer getCont_ceros() {
        return cont_ceros;
    }

    public void setCont_ceros(Integer cont_ceros) {
        this.cont_ceros = cont_ceros;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getCant_Preguntas_evaluado() {
        return cant_Preguntas_evaluado;
    }

    public void setCant_Preguntas_evaluado(Integer cant_Preguntas_evaluado) {
        this.cant_Preguntas_evaluado = cant_Preguntas_evaluado;
    }

    public Integer getTot_ceros() {
        return tot_ceros;
    }

    public void setTot_ceros(Integer tot_ceros) {
        this.tot_ceros = tot_ceros;
    }

    public Integer getTot_cuatro() {
        return tot_cuatro;
    }

    public void setTot_cuatro(Integer tot_cuatro) {
        this.tot_cuatro = tot_cuatro;
    }

    public Integer getTot_vacios() {
        return tot_vacios;
    }

    public void setTot_vacios(Integer tot_vacios) {
        this.tot_vacios = tot_vacios;
    }

    public List<HabilitaEncuestaBean> getLista_habilitar_Encuesta() {
        return lista_habilitar_Encuesta;
    }

    public void setLista_habilitar_Encuesta(List<HabilitaEncuestaBean> lista_habilitar_Encuesta) {
        this.lista_habilitar_Encuesta = lista_habilitar_Encuesta;
    }

    public HabilitaEncuestaBean getHabilitaEncuestaBean() {
        return habilitaEncuestaBean;
    }

    public void setHabilitaEncuestaBean(HabilitaEncuestaBean habilitaEncuestaBean) {
        this.habilitaEncuestaBean = habilitaEncuestaBean;
    }

    public Boolean getFlgEncuesta() {
        return flgEncuesta;
    }

    public void setFlgEncuesta(Boolean flgEncuesta) {
        this.flgEncuesta = flgEncuesta;
    }

    public Integer getFlagEvaluacion() {
        return flagEvaluacion;
    }

    public void setFlagEvaluacion(Integer flagEvaluacion) {
        this.flagEvaluacion = flagEvaluacion;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex_prin() {
        return index_prin;
    }

    public void setIndex_prin(Integer index_prin) {
        this.index_prin = index_prin;
    }

    public void setListaPersonalSessionEvaluadorBean(List<PersonalEDBean> listaPersonalSessionEvaluadorBean) {
        this.listaPersonalSessionEvaluadorBean = listaPersonalSessionEvaluadorBean;
    }

    public List<PersonalEDBean> getListaPersonalSessionEvaluadorBean() {
        if (listaPersonalSessionEvaluadorBean == null) {
            listaPersonalSessionEvaluadorBean = new ArrayList<>();
        }
        return listaPersonalSessionEvaluadorBean;
    }

    public HorizontalBarChartModel getHoriResDirectivos() {
        return HoriResDirectivos;
    }

    public void setHoriResDirectivos(HorizontalBarChartModel HoriResDirectivos) {
        this.HoriResDirectivos = HoriResDirectivos;
    }

    public HorizontalBarChartModel getHoriResDirectivosProm() {
        return HoriResDirectivosProm;
    }

    public void setHoriResDirectivosProm(HorizontalBarChartModel HoriResDirectivosProm) {
        this.HoriResDirectivosProm = HoriResDirectivosProm;
    }

    public HorizontalBarChartModel getHoriResDirectivosPromActual() {
        return HoriResDirectivosPromActual;
    }

    public void setHoriResDirectivosPromActual(HorizontalBarChartModel HoriResDirectivosPromActual) {
        this.HoriResDirectivosPromActual = HoriResDirectivosPromActual;
    }

    public List<DirectoresPromBean> getListaResDirectoresPromHis() {
        return listaResDirectoresPromHis;
    }

    public void setListaResDirectoresPromHis(List<DirectoresPromBean> listaResDirectoresPromHis) {
        this.listaResDirectoresPromHis = listaResDirectoresPromHis;
    }

    /**
     * ************************************* INICIO MEY
     * ************************************************
     */
    /**
     * ************************************* INICIO MEY
     * ************************************************
     */
    public EvaluacionDesempenoBean getEvaluacionDesempenoBean() {
        if (evaluacionDesempenoBean == null) {
            evaluacionDesempenoBean = new EvaluacionDesempenoBean();
        }
        return evaluacionDesempenoBean;
    }

    public void setEvaluacionDesempenoBean(EvaluacionDesempenoBean evaluacionDesempenoBean) {
        this.evaluacionDesempenoBean = evaluacionDesempenoBean;
    }

    public PersonalEDBean getPersonalEDBean() {
        if (personalEDBean == null) {
            personalEDBean = new PersonalEDBean();
        }
        return personalEDBean;
    }

    public void setPersonalEDBean(PersonalEDBean personalEDBean) {
        this.personalEDBean = personalEDBean;
    }

    public DualListModel<PersonalEDBean> getDualEvaluadoBean() {
        if (dualEvaluadoBean == null) {
            dualEvaluadoBean = new DualListModel();
        }
        return dualEvaluadoBean;
    }

    public void setDualEvaluadoBean(DualListModel<PersonalEDBean> dualEvaluadoBean) {
        this.dualEvaluadoBean = dualEvaluadoBean;
    }

    public List<PersonalEDBean> getListaEvaluadoBeanB() {
        if (listaEvaluadoBeanB == null) {
            listaEvaluadoBeanB = new ArrayList<>();
        }
        return listaEvaluadoBeanB;
    }

    public void setListaEvaluadoBeanB(List<PersonalEDBean> listaEvaluadoBeanB) {
        this.listaEvaluadoBeanB = listaEvaluadoBeanB;
    }

    public List<PersonalEDBean> getListaPersonalSessionBean() {
        if (listaPersonalSessionBean == null) {
            listaPersonalSessionBean = new ArrayList<>();
        }
        return listaPersonalSessionBean;
    }

    public void setListaPersonalSessionBean(List<PersonalEDBean> listaPersonalSessionBean) {
        this.listaPersonalSessionBean = listaPersonalSessionBean;
    }

    public List<PersonalEDBean> getListaPersonalEDBeanEvaluado() {
        if (listaPersonalEDBeanEvaluado == null) {
            listaPersonalEDBeanEvaluado = new ArrayList<>();
        }
        return listaPersonalEDBeanEvaluado;
    }

    public void setListaPersonalEDBeanEvaluado(List<PersonalEDBean> listaPersonalEDBeanEvaluado) {
        this.listaPersonalEDBeanEvaluado = listaPersonalEDBeanEvaluado;
    }

    public DualListModel<PersonalEDBean> getDualEvaluadorBean() {
        if (dualEvaluadorBean == null) {
            dualEvaluadorBean = new DualListModel<>();
        }
        return dualEvaluadorBean;
    }

    public void setDualEvaluadorBean(DualListModel<PersonalEDBean> dualEvaluadorBean) {
        this.dualEvaluadorBean = dualEvaluadorBean;
    }

    public List<PersonalEDBean> getListaEvaluadorBeanB() {
        if (listaEvaluadorBeanB == null) {
            listaEvaluadorBeanB = new ArrayList<>();
        }
        return listaEvaluadorBeanB;
    }

    public void setListaEvaluadorBeanB(List<PersonalEDBean> listaEvaluadorBeanB) {
        this.listaEvaluadorBeanB = listaEvaluadorBeanB;
    }

    public List<PersonalEDBean> getListaPersonalEDBeanEvaluador() {
        if (listaPersonalEDBeanEvaluador == null) {
            listaPersonalEDBeanEvaluador = new ArrayList<>();
        }
        return listaPersonalEDBeanEvaluador;
    }

    public void setListaPersonalEDBeanEvaluador(List<PersonalEDBean> listaPersonalEDBeanEvaluador) {
        this.listaPersonalEDBeanEvaluador = listaPersonalEDBeanEvaluador;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public List<EvaluacionDesempenoBean> getListaEvaluacionDesempeno() {
        if (listaEvaluacionDesempeno == null) {
            listaEvaluacionDesempeno = new ArrayList<>();
        }
        return listaEvaluacionDesempeno;
    }

    public void setListaEvaluacionDesempeno(List<EvaluacionDesempenoBean> listaEvaluacionDesempeno) {
        this.listaEvaluacionDesempeno = listaEvaluacionDesempeno;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;

    }

    public Integer getTot_uno() {
        return tot_uno;
    }

    public void setTot_uno(Integer tot_uno) {
        this.tot_uno = tot_uno;
    }

    public Integer getTot_dos() {
        return tot_dos;
    }

    public void setTot_dos(Integer tot_dos) {
        this.tot_dos = tot_dos;
    }

    public Integer getTot_tres() {
        return tot_tres;
    }

    public void setTot_tres(Integer tot_tres) {
        this.tot_tres = tot_tres;
    }

    public Integer getTot_uno_dir() {
        return tot_uno_dir;
    }

    public void setTot_uno_dir(Integer tot_uno_dir) {
        this.tot_uno_dir = tot_uno_dir;
    }

    public Integer getTot_dos_dir() {
        return tot_dos_dir;
    }

    public void setTot_dos_dir(Integer tot_dos_dir) {
        this.tot_dos_dir = tot_dos_dir;
    }

    public Integer getTot_tres_dir() {
        return tot_tres_dir;
    }

    public void setTot_tres_dir(Integer tot_tres_dir) {
        this.tot_tres_dir = tot_tres_dir;
    }

    public Integer getTot_uno_nodoc() {
        return tot_uno_nodoc;
    }

    public void setTot_uno_nodoc(Integer tot_uno_nodoc) {
        this.tot_uno_nodoc = tot_uno_nodoc;
    }

    public Integer getTot_dos_nodoc() {
        return tot_dos_nodoc;
    }

    public void setTot_dos_nodoc(Integer tot_dos_nodoc) {
        this.tot_dos_nodoc = tot_dos_nodoc;
    }

    public Integer getTot_tres_nodoc() {
        return tot_tres_nodoc;
    }

    public void setTot_tres_nodoc(Integer tot_tres_nodoc) {
        this.tot_tres_nodoc = tot_tres_nodoc;
    }

    public Integer getTot_uno_adm() {
        return tot_uno_adm;
    }

    public void setTot_uno_adm(Integer tot_uno_adm) {
        this.tot_uno_adm = tot_uno_adm;
    }

    public Integer getTot_dos_adm() {
        return tot_dos_adm;
    }

    public void setTot_dos_adm(Integer tot_dos_adm) {
        this.tot_dos_adm = tot_dos_adm;
    }

    public Integer getTot_tres_adm() {
        return tot_tres_adm;
    }

    public void setTot_tres_adm(Integer tot_tres_adm) {
        this.tot_tres_adm = tot_tres_adm;
    }

    public Integer getTot_uno_doc() {
        return tot_uno_doc;
    }

    public void setTot_uno_doc(Integer tot_uno_doc) {
        this.tot_uno_doc = tot_uno_doc;
    }

    public Integer getTot_dos_doc() {
        return tot_dos_doc;
    }

    public void setTot_dos_doc(Integer tot_dos_doc) {
        this.tot_dos_doc = tot_dos_doc;
    }

    public Integer getTot_tres_doc() {
        return tot_tres_doc;
    }

    public void setTot_tres_doc(Integer tot_tres_doc) {
        this.tot_tres_doc = tot_tres_doc;
    }

    public Integer getTot_uno_man() {
        return tot_uno_man;
    }

    public void setTot_uno_man(Integer tot_uno_man) {
        this.tot_uno_man = tot_uno_man;
    }

    public Integer getTot_dos_man() {
        return tot_dos_man;
    }

    public void setTot_dos_man(Integer tot_dos_man) {
        this.tot_dos_man = tot_dos_man;
    }

    public Integer getTot_tres_man() {
        return tot_tres_man;
    }

    public void setTot_tres_man(Integer tot_tres_man) {
        this.tot_tres_man = tot_tres_man;
    }

    public boolean isFlgindicaciones() {
        return flgindicaciones;
    }

    public void setFlgindicaciones(boolean flgindicaciones) {
        this.flgindicaciones = flgindicaciones;
    }

    /**
     * *************************************************************************************************
     */
    public Map<String, Integer> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(Map<String, Integer> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getFlgALL() {
        return flgALL;
    }

    public void setFlgALL(Boolean flgALL) {
        this.flgALL = flgALL;
    }

    public Boolean getFlgNiveles() {
        return flgNiveles;
    }

    public void setFlgNiveles(Boolean flgNiveles) {
        this.flgNiveles = flgNiveles;
    }

    public List<PersonalEDBean> getListaPersonalSessionEvaluadoBean() {
        if (listaPersonalSessionEvaluadoBean == null) {
            listaPersonalSessionEvaluadoBean = new ArrayList<>();
        }
        return listaPersonalSessionEvaluadoBean;
    }

    public void setListaPersonalSessionEvaluadoBean(List<PersonalEDBean> listaPersonalSessionEvaluadoBean) {
        this.listaPersonalSessionEvaluadoBean = listaPersonalSessionEvaluadoBean;
    }

    public Boolean getFlgProgreso() {
        return flgProgreso;
    }

    public void setFlgProgreso(Boolean flgProgreso) {
        this.flgProgreso = flgProgreso;
    }

    public List<UnidadNegocioBean> getListaUniNeg() {
        if (listaUniNeg == null) {
            listaUniNeg = new ArrayList<>();
        }
        return listaUniNeg;
    }

    public void setListaUniNeg(List<UnidadNegocioBean> listaUniNeg) {
        this.listaUniNeg = listaUniNeg;
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

    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

    public List<HistoricoEDListBean> getRecuperaHistoricoProm() {
        return recuperaHistoricoProm;
    }

    public void setRecuperaHistoricoProm(List<HistoricoEDListBean> recuperaHistoricoProm) {
        this.recuperaHistoricoProm = recuperaHistoricoProm;
    }

    public Map<Integer, Integer> getAnioslist() {
        return anioslist;
    }

    public void setAnioslist(Map<Integer, Integer> anioslist) {
        this.anioslist = anioslist;
    }

    public DetalleNivelCargo getDetalleNivelCargo() {
        if (detalleNivelCargo == null) {
            detalleNivelCargo = new DetalleNivelCargo();
        }
        return detalleNivelCargo;
    }

    public void setDetalleNivelCargo(DetalleNivelCargo detalleNivelCargo) {
        this.detalleNivelCargo = detalleNivelCargo;
    }

}
