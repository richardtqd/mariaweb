/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import org.springframework.transaction.annotation.Transactional;
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
import pe.marista.sigma.dao.EvaluacionDesempenoDAO;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public class EvaluacionDesempenoService {

    private EvaluacionDesempenoDAO evaluacionDesempenoDAO;

    public List<PreguntasBean> sp_ed_lista_preguntas(Integer idcompetencia, String iduniorg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_preguntas(idcompetencia, iduniorg, anio);
    }

    public List<EvaluacionBean> sp_ed_lista_evaluaciones(String codigoEvaluador, Integer idcargoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_evaluaciones(codigoEvaluador, idcargoEvaluador, uniNeg, anio);
    }

    public List<EstadoBean> sp_ed_lista_estados(String codigoEvaluador, Integer idcargoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_estados(codigoEvaluador, idcargoEvaluador, uniNeg, anio);
    }

    public List<PreguntaxCompetenciaBean> sp_ed_cant_preg_grupoocupacional(Integer idtipogrupoOcupacional, Integer flgcargoocuprincipal, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_cant_preg_grupoocupacional(idtipogrupoOcupacional, flgcargoocuprincipal, anio);
    }

    public List<EvaluacionBean> sp_ed_evaluaciones_completas(String codigoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_evaluaciones_completas(codigoEvaluador, uniNeg, anio);
    }

    public List<EvaluacionBean> sp_ed_lista_evaluaciones_default(String codigoEvaluador, Integer idcargoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_evaluaciones_default(codigoEvaluador, idcargoEvaluador, uniNeg, anio);
    }

    public List<EvaluacionBean> sp_ed_evaluaciones_completas_default(String codigoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_evaluaciones_completas_default(codigoEvaluador, uniNeg, anio);
    }


    public List<MatrizGraficoEDBean> sp_ed_grafico_directores(String unineg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_grafico_directores(unineg, anio);
    }

    @Transactional
    public void insertEncuesta(Integer idpregunta, Integer respuesta, float prom_respuesta, Integer idcompetencia, Integer idestado, float progreso, Integer flag, String unineg, String fecha, Integer idevaluadoevaluador, Integer anio, String creaPor) throws Exception {
        evaluacionDesempenoDAO.insertEncuesta(idpregunta, respuesta, prom_respuesta, idcompetencia, idestado, progreso, flag, unineg, fecha, idevaluadoevaluador, anio, creaPor);
    }

    public EvaluacionDesempenoDAO getEvaluacionDesempenoDAO() {
        return evaluacionDesempenoDAO;
    }

    public List<ProgresoBean> sp_ed_existe_encuesta(Integer idevaluadoevaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_existe_encuesta(idevaluadoevaluador, uniNeg, anio);
    }

    public List<ProgresoBean> sp_ed_update_progreso(Integer idevaluadoevaluador, float progreso, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_update_progreso(idevaluadoevaluador, progreso, uniNeg, anio);
    }

    public List<HabilitaEncuestaBean> sp_ed_lista_encuestas_editar(String unineg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_encuestas_editar(unineg, anio);
    }

    @Transactional
    public void ed_updateEncuesta(Integer idevaluadoevaluador) throws Exception {
        evaluacionDesempenoDAO.ed_updateEncuesta(idevaluadoevaluador);
    }

    public List<CantidadBean> sp_ed_cargar_estados(String unineg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_cargar_estados(unineg, anio);
    }

    public List<DatosPersonalBean> sp_datos_personal_default(String codPer, String unineg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_datos_personal_default(codPer, unineg, anio);
    }
    public void setEvaluacionDesempenoDAO(EvaluacionDesempenoDAO EvaluacionDesempenoDAO) {
        this.evaluacionDesempenoDAO = EvaluacionDesempenoDAO;
    }

    public List<DatosPersonalBean> sp_datos_personal(String codPer, Integer idcargo, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_datos_personal(codPer, idcargo, uniNeg, anio);
    }

    public List<EvaluadoBean> sp_datos_evaluado(Integer idEvaluadoEvaluador, Integer idgrupo, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_datos_evaluado(idEvaluadoEvaluador, idgrupo, uniNeg, anio);
    }

    public Integer sp_ed_idgrupoocupacional(Integer idevaluadoevaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_idgrupoocupacional(idevaluadoevaluador, uniNeg, anio);
    }


    public List<PersonalEDBean> obtenerEvaluacionDesempenoActivos(String uniNeg) throws Exception {
        return evaluacionDesempenoDAO.obtenerEvaluacionDesempenoActivos(uniNeg);
    }

    public EvaluacionDesempenoBean obtenerEvluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        return evaluacionDesempenoDAO.obtenerEvluacionDesempeno(evaluacionDesempenoBean);
    }

    public void insertarEvaluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        evaluacionDesempenoDAO.insertarEvaluacionDesempeno(evaluacionDesempenoBean);
    }

    @Transactional
    public void eliminarEvaluadorAll(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        evaluacionDesempenoDAO.eliminarEvaluadorAll(evaluacionDesempenoBean);
    }

    public List<CargosEvaluadorBean> sp_ed_cargos_evaluador(String codigoEvaluador, String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_cargos_evaluador(codigoEvaluador, uniNeg, anio);
    }

    public void insertarEDVer2(Integer anio, String uniNeg, String usuario, List<PersonalEDBean> listaEvaluado, List<PersonalEDBean> listaEvaluador) throws Exception {

        for (Object objecto1 : listaEvaluado) {
            EvaluacionDesempenoBean eva = new EvaluacionDesempenoBean();
            System.out.println("evaluado: " + objecto1.toString());
            String codPer = "";
//            Integer idUniOrg = 0;
            Integer idCargo = 0;
            int index = objecto1.toString().indexOf("@");
            int index2 = objecto1.toString().indexOf("/");
            codPer = (objecto1.toString().substring(0, index));
//            idUniOrg = (new Integer(objecto1.toString().substring(index + 1, index2)));
//            idCargo = (new Integer(objecto1.toString().substring(index2 + 1, objecto1.toString().length())));
            idCargo = (new Integer(objecto1.toString().substring(index2 + 1, objecto1.toString().length())));
//            idCargo = (new Integer(objecto1.toString().substring(index + 1)));
            System.out.println("cod:" + codPer);
//            System.out.println("idUniOrg:" + idUniOrg);
            System.out.println("idCargo:" + idCargo);
            eva.getCodigoEvaluado().setCodigoPer(codPer);
            eva.getCargoEvaluadoBean().setIdCargo(idCargo);
            for (Object objecto2 : listaEvaluador) {
                System.out.println("evaluador: " + objecto2.toString());
                String codPer2 = "";
//                Integer idUniOrg2 = 0;
                Integer idCargo2 = 0;
                int indexE1 = objecto2.toString().indexOf("@");
                int indexE2 = objecto2.toString().indexOf("/");
                codPer2 = (objecto2.toString().substring(0, indexE1));
//                idUniOrg2 = (new Integer(objecto2.toString().substring(indexE1 + 1, indexE2)));
//                idCargo2 = (new Integer(objecto2.toString().substring(indexE2 + 1, objecto2.toString().length())));
                idCargo2 = (new Integer(objecto2.toString().substring(indexE2 + 1, objecto2.toString().length())));
//                idCargo2 = (new Integer(objecto2.toString().substring(indexE1 + 1)));
                System.out.println("cod2:" + codPer2);
//                System.out.println("idUniOrg2:" + idUniOrg2);
                System.out.println("idCargo2:" + idCargo2);
//                //Evaluador
                eva.getCodigoEvaluador().setCodigoPer(codPer2);
                eva.getCargoEvaluadorBean().setIdCargo(idCargo2);

                eva.getUnidadNegocioBean().setUniNeg(uniNeg);
                eva.setStatus(Boolean.TRUE);
                eva.setAnio(anio);
                eva.setCreaPor(usuario);

                String resultado = evaluacionDesempenoDAO.obtenerPersonalYaIngresadoDesempeno(eva);
                if (resultado == null) {
                    evaluacionDesempenoDAO.insertarEvaluacionDesempeno(eva);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("Uno de los registros ingresados ya fue registrado anteriormente en SIGMA");

                }
            }
        }

    }

    public Float sp_ed_recupera_promedio(String unineg, String nombre, Integer idcargo, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_recupera_promedio(unineg, nombre, idcargo, anio);
    }

    public List<EvaluacionDesempenoBean> obtenerListaEvluacionDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        return evaluacionDesempenoDAO.obtenerListaEvluacionDesempeno(evaluacionDesempenoBean);
    }

    public List<PersonalEDBean> obtenerEvaluador(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        return evaluacionDesempenoDAO.obtenerEvaluador(evaluacionDesempenoBean);
    }

    public List<PersonalEDBean> obtenerEvaluado(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        return evaluacionDesempenoDAO.obtenerEvaluado(evaluacionDesempenoBean);
    }

    public List<EvaluacionDesempenoRepBean> obtenerReporteEvaluadoresEvaluados(String uniNeg, Integer anio, Integer orden) throws Exception {
        return evaluacionDesempenoDAO.obtenerReporteEvaluadoresEvaluados(uniNeg, anio, orden);
    }

    public List<EvaluacionDesempenoRepBean> obtenerReporteEvaluadoresEvaluadosNiveles(String uniNeg, Integer anio, Integer orden) throws Exception {
        return evaluacionDesempenoDAO.obtenerReporteEvaluadoresEvaluadosNiveles(uniNeg, anio, orden);
    }

    public String obtenerPersonalYaIngresadoDesempeno(EvaluacionDesempenoBean evaluacionDesempenoBean) throws Exception {
        return evaluacionDesempenoDAO.obtenerPersonalYaIngresadoDesempeno(evaluacionDesempenoBean);
    }

    public String obtenerFotoPersonal(String codPer, String uniNeg) throws Exception {
        return evaluacionDesempenoDAO.obtenerFotoPersonal(codPer, uniNeg);
    }

    public List<EvaluacionDesempenoRepBean> obtenerProgresoDeEvaluaciones(String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.obtenerProgresoDeEvaluaciones(uniNeg, anio);
    }

    public List<AlertasEvaluacionDesempenoRepBean> obtenerAlertasEvaluacionDesempeno(String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.obtenerAlertasEvaluacionDesempeno(uniNeg, anio);
    }

    public List<SeguimientoEDRepBean> obtenerSeguimientoEvaluacionDesempeno(String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.obtenerSeguimientoEvaluacionDesempeno(uniNeg, anio);
    }

    public List<EvaluacionDesempenoRepBean> obtenerSinEvaluacionDesempeno(String uniNeg, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.obtenerSinEvaluacionDesempeno(uniNeg, anio);
    }

    public List<HistoricoEDBean> sp_ed_historico_directores(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_historico_directores(unineg);
    }

    public List<UniNegBean> sp_ed_lista_unineg_historico() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_unineg_historico();
    }

    public List<HistoricoEDListBean> sp_ed_lista_historico(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_historico(unineg);
    }

    public Integer sp_ed_update_Promedio_Historico(String unineg, Integer idcargo, Integer anio, float promedio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_update_Promedio_Historico(unineg, idcargo, anio, promedio);
    }

    public List<DirectoresEDBean> sp_ed_directores() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_directores();
    }

    public Integer sp_ed_cargar_matriz_historico() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_cargar_matriz_historico();
    }

    public List<DirectoresPromBean> sp_ed_grafico_directores_unidadnegocio() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_grafico_directores_unidadnegocio();
    }

    public List<IndicadoresBean> sp_ed_tipo_planilla(Integer idTipo) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_tipo_planilla(idTipo);
    }

    public List<IndicadoresBean> sp_ed_lista_indicadores(Integer tipo_planilla) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_indicadores(tipo_planilla);
    }
 
    public List<DetalleIndicadorBean> sp_ed_lista_filtros_dinamicos(String cad, String unineg, String idindicador, String anio, String flgAsigna) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_filtros_dinamicos(cad, unineg, idindicador, anio, flgAsigna);
    }

    public Integer sp_ed_insert_detalle_indicador(String unineg, String codper, Integer idTipoNivelesColegio, Integer idindicador, Float promedio, String creapor, String modipor, String creafecha, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_insert_detalle_indicador(unineg, codper, idTipoNivelesColegio, idindicador, promedio, creapor, modipor, creafecha, anio);
    }

    public Integer sp_ed_update_detalle_indicador(String unineg, String codper, Integer idTipoNivelesColegio, Integer idindicador, Float promedio, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_update_detalle_indicador(unineg, codper, idTipoNivelesColegio, idindicador, promedio, anio);
    }

    public Integer sp_ed_consulta_detalle_indicador(String codper, String unineg, Integer idTipoNivelesColegio, Integer idindicador, Integer anio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_consulta_detalle_indicador(codper, unineg, idTipoNivelesColegio, idindicador, anio);
    }

    public String sp_ed_nombreindicador(Integer idindicador) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_nombreindicador(idindicador);
    }

    public Integer sp_ed_insert_historico() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_insert_historico();
    }

    public Integer sp_ed_cargar_matriz_autoevaluacion() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_cargar_matriz_autoevaluacion();
    }

    public List<IndicadoresPlanilla> sp_ed_Rep_Indicadores_Planilla(Integer tipo_planilla) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_Rep_Indicadores_Planilla(tipo_planilla);
    }

    
   //  query demos 
    public List<ED_PersonalBean> lista_demo() throws Exception {
        return evaluacionDesempenoDAO.lista_demo();
    }

    public List<ED_DetalleCompetencias> sp_ed_detalle_cardinal_especifica(String unineg, String nombre, String apepat, String apemat) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_detalle_cardinal_especifica(unineg, nombre, apepat, apemat);
    }

    public List<ED_DetalleComObservables> sp_ed_detallecompetencias(String codper,Integer idcargo) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_detallecompetencias(codper,idcargo);
    }

    public List<ED_IniPriSEC> sp_ed_tiponiveles() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_tiponiveles();
    }

    public List<ED_DetallexNivel> sp_ed_detallexnivel(Integer idTipoNivelesColegio) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_detallexnivel(idTipoNivelesColegio);
    }

    public List<ED_IniPriSEC> sp_ed_grafico_docentes() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_grafico_docentes();
    }
        //// reporte vista

    public List<ED_PersonalBean> sp_ed_vista_reportes_EvaDes() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_vista_reportes_EvaDes();
    }

    public List<EvaRepIndividualBean> sp_reporte_individual(String unineg, String codper) throws Exception {
        return evaluacionDesempenoDAO.sp_reporte_individual(unineg, codper);
    }

    public List<Cargos> sp_ed_lista_cargos(String codper, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_lista_cargos(codper, unineg);
    }

    public List<ResumenEvaDesempeno> sp_ed_resultados_eva_resumen(String codper, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_resultados_eva_resumen(codper, unineg);
    }

    public List<FichaRetroalimentacionBean> sp_ed_ficha_retroalimentacion(String codper, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_ficha_retroalimentacion(codper, unineg);
    }

    public List<EvaRepIndividualPlanBean> sp_ed_repIndividual_planilla(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_repIndividual_planilla(unineg);
    }

    public List<FichaEntrevista> sp_ed_rep_fichaEntravista(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_rep_fichaEntravista(unineg);
    }

    public List<RepConsolidado> sp_ed_rep_consolidado(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_rep_consolidado(unineg);
    }

    public List<DetalleNivelCargo> sp_ed_detallexcargo(String codper, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_detallexcargo(codper, unineg);
    }

    public List<ED_IniPriSEC> sp_ed_grafico_directivos() throws Exception {
        return evaluacionDesempenoDAO.sp_ed_grafico_directivos();
    }

    public List<FichaRetroConsolidado> sp_ed_ficha_retroalimentacionConsolidado(String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_ficha_retroalimentacionConsolidado(unineg);
    }

    public List<FichaEntrevista> sp_ed_fichaEntrevista(String codper, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_fichaEntrevista(codper, unineg);
    }

    public Integer sp_ed_flg_consolidado(Integer idpersonal, String unineg) throws Exception {
        return evaluacionDesempenoDAO.sp_ed_flg_consolidado(idpersonal, unineg);
    }

    public List<AnioHistBean> sp_anio_indicadores() throws Exception {
        return evaluacionDesempenoDAO.sp_anio_indicadores();
    }
    
}
