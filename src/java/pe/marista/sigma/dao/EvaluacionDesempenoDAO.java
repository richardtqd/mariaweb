/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;
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
import pe.marista.sigma.bean.reporte.AlertasEvaluacionDesempenoRepBean;
import pe.marista.sigma.bean.reporte.EvaluacionDesempenoRepBean;
import pe.marista.sigma.bean.reporte.SeguimientoEDRepBean;

/**
 *
 * @author MS001
 */
public interface EvaluacionDesempenoDAO {

    /*LISTADO DE PREGUNTAS*/
    public List<PreguntasBean> sp_ed_lista_preguntas(@Param("idcompetencia") Integer idcompetencia, @Param("iduniorg") String iduniorg, @Param("anio") Integer anio) throws Exception;
    /*LISTADO DE EVALUACIONES*/

    public List<EvaluacionBean> sp_ed_lista_evaluaciones(@Param("codigoEvaluador") String codigoEvaluador, @Param("idcargoEvaluador") Integer idcargoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EvaluacionBean> sp_ed_lista_evaluaciones_default(@Param("codigoEvaluador") String codigoEvaluador, @Param("idcargoEvaluador") Integer idcargoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EvaluacionBean> sp_ed_evaluaciones_completas(@Param("codigoEvaluador") String codigoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EvaluacionBean> sp_ed_evaluaciones_completas_default(@Param("codigoEvaluador") String codigoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    /*LISTADO DE ESTADOS*/
    public List<EstadoBean> sp_ed_lista_estados(@Param("codigoEvaluador") String codigoEvaluador, @Param("idcargoEvaluador") Integer idcargoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<DatosPersonalBean> sp_datos_personal(@Param("codPer") String codPer, @Param("idcargo") Integer idcargo, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<DatosPersonalBean> sp_datos_personal_default(@Param("codPer") String codPer, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;


    //public List<EvaluadoBean> sp_datos_evaluado(@Param("idEvaluadoEvaluador") Integer idEvaluadoEvaluador,@Param("idgrupo") Integer idgrupo) throws Exception;

    public List<EvaluadoBean> sp_datos_evaluado(@Param("idEvaluadoEvaluador") Integer idEvaluadoEvaluador, @Param("idgrupo") Integer idgrupo, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Integer sp_ed_idgrupoocupacional(@Param("idevaluadoevaluador") Integer idevaluadoevaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<PreguntaxCompetenciaBean> sp_ed_cant_preg_grupoocupacional(@Param("idtipogrupoOcupacional") Integer idtipogrupoOcupacional, @Param("flgcargoocuprincipal") Integer flgcargoocuprincipal, @Param("anio") Integer anio) throws Exception;

    public List<ProgresoBean> sp_ed_existe_encuesta(@Param("idevaluadoevaluador") Integer idevaluadoevaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<ProgresoBean> sp_ed_update_progreso(@Param("idevaluadoevaluador") Integer idevaluadoevaluador, @Param("progreso") float progreso, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<CargosEvaluadorBean> sp_ed_cargos_evaluador(@Param("codigoEvaluador") String codigoEvaluador, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<HabilitaEncuestaBean> sp_ed_lista_encuestas_editar(@Param("unineg") String unineg, @Param("anio") Integer anio) throws Exception;

    public void ed_updateEncuesta(@Param("idevaluadoevaluador") Integer idevaluadoevaluador) throws Exception;

    public List<CantidadBean> sp_ed_cargar_estados(@Param("unineg") String unineg, @Param("anio") Integer anio) throws Exception;

    public void insertEncuesta(@Param("idpregunta") Integer idpregunta,
            @Param("respuesta") Integer respuesta,
            @Param("prom_respuesta") float prom_respuesta,
            @Param("idcompetencia") Integer idcompetencia,
            @Param("idestado") Integer idestado,
            @Param("progreso") float progreso,
            @Param("flag") Integer flag,
            @Param("unineg") String unineg,
            @Param("fecha") String fecha,
            @Param("idevaluadoevaluador") Integer idevaluadoevaluador,
            @Param("anio") Integer anio,
            @Param("creaPor") String creaPor) throws Exception;

    public List<PersonalEDBean> obtenerEvaluacionDesempenoActivos(@Param("uniNeg") String uniNeg) throws Exception;

    public EvaluacionDesempenoBean obtenerEvluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public List<EvaluacionDesempenoBean> obtenerListaEvluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public String obtenerPersonalYaIngresadoDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public List<EvaluacionDesempenoRepBean> obtenerReporteEvaluadoresEvaluados(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("orden") Integer orden) throws Exception;

    public List<AlertasEvaluacionDesempenoRepBean> obtenerAlertasEvaluacionDesempeno(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<SeguimientoEDRepBean> obtenerSeguimientoEvaluacionDesempeno(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EvaluacionDesempenoRepBean> obtenerReporteEvaluadoresEvaluadosNiveles(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("orden") Integer orden) throws Exception;

    public List<EvaluacionDesempenoRepBean> obtenerProgresoDeEvaluaciones(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public void insertarEvaluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public List<PersonalEDBean> obtenerEvaluador(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public List<PersonalEDBean> obtenerEvaluado(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public void eliminarEvaluadorAll(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception;

    public String obtenerFotoPersonal(@Param("codPer") String codPer, @Param("uniNeg") String uniNeg) throws Exception;

    public List<EvaluacionDesempenoRepBean> obtenerSinEvaluacionDesempeno(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;
    public  List<MatrizGraficoEDBean> sp_ed_grafico_directores(@Param("unineg") String unineg, @Param("anio") Integer anio) throws Exception;
	  
    public  List<HistoricoEDBean> sp_ed_historico_directores(@Param("unineg") String unineg) throws Exception;
    public  List<UniNegBean> sp_ed_lista_unineg_historico() throws Exception;
    //public Integer sp_ed_idgrupoocupacional(@Param("idevaluadoevaluador") Integer idevaluadoevaluador) throws Exception;            
    public  List<HistoricoEDListBean> sp_ed_lista_historico(@Param("unineg") String unineg) throws Exception;    
    
    public Integer sp_ed_update_Promedio_Historico(@Param("unineg") String unineg,@Param("idcargo") Integer idcargo,@Param("anio") Integer anio,@Param("promedio") float promedio) throws Exception;
    public List<DirectoresEDBean> sp_ed_directores() throws Exception;
    public Integer sp_ed_cargar_matriz_historico() throws Exception;
    public List<DirectoresPromBean> sp_ed_grafico_directores_unidadnegocio() throws Exception;
    
    public Float sp_ed_recupera_promedio(@Param("unineg") String unineg,@Param("nombre") String nombre,@Param("idcargo") Integer idcargo,@Param("anio") Integer anio) throws Exception; 
    
    public List<IndicadoresBean> sp_ed_tipo_planilla(@Param("idTipo") Integer idTipo) throws Exception;    
    public List<IndicadoresBean> sp_ed_lista_indicadores(@Param("tipo_planilla") Integer tipo_planilla) throws Exception;   
    
    public List<DetalleIndicadorBean> sp_ed_lista_filtros_dinamicos(@Param("cad") String cad,
            @Param("unineg") String unineg,
            @Param("idindicador") String idindicador,
            @Param("anio") String anio,
            @Param("flgAsigna") String flgAsigna) throws Exception;    
    
    public Integer sp_ed_insert_detalle_indicador(@Param("unineg") String unineg,
            @Param("codper") String codper,
            @Param("idTipoNivelesColegio") Integer idTipoNivelesColegio,
            @Param("idindicador") Integer idindicador,
            @Param("promedio") Float promedio,
            @Param("creapor") String creapor,
            @Param("modipor") String modipor,
            @Param("creafecha") String creafecha,
            @Param("anio") Integer anio) throws Exception;    
    public Integer sp_ed_update_detalle_indicador(@Param("unineg") String unineg,
            @Param("codper") String codper,
            @Param("idTipoNivelesColegio") Integer idTipoNivelesColegio,
            @Param("idindicador") Integer idindicador,
            @Param("promedio") Float promedio,
            @Param("anio") Integer anio) throws Exception;     
    public Integer sp_ed_consulta_detalle_indicador(@Param("codper") String codper,
            @Param("unineg") String unineg,
            @Param("idTipoNivelesColegio") Integer idTipoNivelesColegio,            
            @Param("idindicador") Integer idindicador,
            @Param("anio") Integer anio) throws Exception;
    public String sp_ed_nombreindicador(@Param("idindicador") Integer idindicador) throws Exception;
    public Integer sp_ed_insert_historico() throws Exception;
    public Integer sp_ed_cargar_matriz_autoevaluacion() throws Exception;
        /// demos 
    
    public List<ED_PersonalBean> lista_demo() throws Exception;   
    public List<ED_DetalleCompetencias> sp_ed_detalle_cardinal_especifica(@Param("unineg") String unineg,
                @Param("nombre") String nombre,
                @Param("apepat") String apepat,
                @Param("apemat") String apemat) throws Exception;   
    public List<ED_DetalleComObservables> sp_ed_detallecompetencias(@Param("codper") String codper,@Param("idcargo") Integer idcargo) throws Exception;   
    
    public List<ED_IniPriSEC> sp_ed_tiponiveles() throws Exception;   
    public List<ED_DetallexNivel> sp_ed_detallexnivel(@Param("idTipoNivelesColegio") Integer idTipoNivelesColegio) throws Exception;       
    public List<ED_IniPriSEC> sp_ed_grafico_docentes() throws Exception;   
    // reporte vista
    public List<ED_PersonalBean> sp_ed_vista_reportes_EvaDes() throws Exception;   
    public List<EvaRepIndividualBean> sp_reporte_individual(@Param("unineg") String unineg,
                @Param("codper") String codper) throws Exception;    
    public List<Cargos> sp_ed_lista_cargos(@Param("codper") String codper,
                @Param("unineg") String unineg) throws Exception;    
    public List<ResumenEvaDesempeno> sp_ed_resultados_eva_resumen(@Param("codper") String codper,
                @Param("unineg") String unineg) throws Exception;  
    
    public List<IndicadoresPlanilla> sp_ed_Rep_Indicadores_Planilla(@Param("tipo_planilla") Integer tipo_planilla) throws Exception;             
    public List<FichaRetroalimentacionBean> sp_ed_ficha_retroalimentacion(@Param("codper") String codper,
                @Param("unineg") String unineg) throws Exception;      
    
    public List<EvaRepIndividualPlanBean> sp_ed_repIndividual_planilla(@Param("unineg") String unineg) throws Exception;            
    public List<FichaEntrevista> sp_ed_rep_fichaEntravista(@Param("unineg") String unineg) throws Exception;            
    public List<RepConsolidado> sp_ed_rep_consolidado(@Param("unineg") String unineg) throws Exception;                
  
    public List<DetalleNivelCargo> sp_ed_detallexcargo(@Param("codper") String codper,
                @Param("unineg") String unineg) throws Exception;          
    public List<ED_IniPriSEC> sp_ed_grafico_directivos() throws Exception;      
    public List<FichaRetroConsolidado> sp_ed_ficha_retroalimentacionConsolidado(@Param("unineg") String unineg) throws Exception;          

    public List<FichaEntrevista> sp_ed_fichaEntrevista(@Param("codper") String codper,@Param("unineg") String unineg) throws Exception;            
    public Integer sp_ed_flg_consolidado(@Param("idpersonal") Integer idpersonal,@Param("unineg") String unineg) throws Exception;                          

    public List<AnioHistBean> sp_anio_indicadores() throws Exception;            
}
