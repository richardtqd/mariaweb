/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.CantidadBean;
import pe.marista.sigma.bean.ConGeneralBean;
import pe.marista.sigma.bean.GrPieChartBean;
import pe.marista.sigma.bean.HistoricoBean;
import pe.marista.sigma.bean.HistoricoEDListBean;
import pe.marista.sigma.bean.PoblacionBean;
import pe.marista.sigma.bean.PoblacionCons;
import pe.marista.sigma.bean.PoblacionGrOcuBean;
import pe.marista.sigma.bean.ResDimensionBean;
import pe.marista.sigma.bean.ResSatGeneralBean;
import pe.marista.sigma.bean.SatisfaccionGrlBean;
import pe.marista.sigma.bean.SugerenciasBean;
import pe.marista.sigma.bean.UniNegBean;
import pe.marista.sigma.dao.ResDimensionDAO;

/**
 *
 * @author MS001
 */
public class ResDimensionService {
    
    private ResDimensionDAO resDimensionDAO;

    /* RESULTADOS POR DIMENSION */
    public List<ResDimensionBean> sp_mc_resultadoxdimensiones(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_resultadoxdimensiones(unineg, anio);
    }
    
    /* GRUPOS OCUPACIONALES - NO DOCENTES */
    public List<GrPieChartBean> sp_mc_grp_ocupacionales_no_docentes(Integer idTipoPersonal, Integer idTipoArea,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, unineg, anio);
    }
 
    
    /* GRUPOS OCUPACIONALES - DIRECTIVOS - ADMINISTRATIVOS - FORMATIVOS */
    public List<ResDimensionBean> sp_mc_grp_ocupacionales_dir_adm_for(Integer idTipoPersonal,Integer idTipoArea,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal,idTipoArea,unineg, anio);
    }    
    
    /* GRUPOS OCUPACIONALES - SATISFACCION GENERAL */
    public List<ResSatGeneralBean> sp_mc_grp_ocupacionales_general(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_general(unineg,anio);
    }        
    
    /* SATISFACCION GENERAL PIE */

    public List<GrPieChartBean> sp_mc_pie_satisfaccion_general(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_pie_satisfaccion_general(unineg,anio);
    }    
   
    /* GRUPOS OCUPACIONALES - MANTENIMIENTO */
    public List<ResDimensionBean> sp_mc_grp_ocupacionales_mantenimiento(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_mantenimiento(unineg,anio);
    }    
    
    /* SATISFACCION GENERAL - HISTORICO */
    public List<HistoricoBean> sp_mc_general_historico(String unineg) throws Exception {
        return resDimensionDAO.sp_mc_general_historico(unineg);
    }     

    public List<GrPieChartBean> sp_mc_grp_ocupacionales_docentes(Integer idTipoPersonal, Integer idNivelAcademico,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_docentes(idTipoPersonal, idNivelAcademico,unineg,anio);
    }

    public List<ResDimensionBean> sp_mc_grp_ocupacionales_ini_pri_sec(Integer idTipoPersonal, Integer idNivelAcademico, String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_ini_pri_sec(idTipoPersonal, idNivelAcademico, unineg, anio);
    }

    public List<GrPieChartBean> sp_mc_tipo_sexo(Integer sexo,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_tipo_sexo(sexo,unineg, anio);
    }

    public List<GrPieChartBean> sp_mc_rango_de_edades(Integer idEdad,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_rango_de_edades(idEdad,unineg, anio);
    }

    public List<GrPieChartBean> sp_mc_tiempo_de_servicio(Integer idTiempoServicio,String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_tiempo_de_servicio(idTiempoServicio,unineg,anio);
    }

    public List<UniNegBean> sp_mc_unidadnegocio() throws Exception {
        return resDimensionDAO.sp_mc_unidadnegocio();
    }

    public List<GrPieChartBean> sp_mc_grp_ocupacionales_sector_adm(Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_sector_adm(anio);
    }

    public List<ConGeneralBean> sp_mc_sat_general_unineg(String idDimension,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_sat_general_unineg(idDimension,anio);
    }

    public List<ConGeneralBean> sp_mc_sat_general_dimensiones(String idDimension,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_sat_general_dimensiones(idDimension,anio);
    }

    public List<SatisfaccionGrlBean> sp_mc_satisfaccion_general_actual(Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_satisfaccion_general_actual(anio);
    }

    public List<SugerenciasBean> sp_mc_sugerencias(Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_sugerencias(anio);
    }

    public List<PoblacionBean> sp_mc_cant_poblacion(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_grp_ocupacionales_encuestados(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_encuestados(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_sexo(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_sexo(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_rang_edad(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_rang_edad(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_time_ser(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_time_ser(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_grp_ocupacional(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_grp_ocupacional(unineg,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_nivel_academico(String unineg, Integer idNivelAcademico,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_nivel_academico(unineg, idNivelAcademico,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_area(String unineg, Integer idtipoarea,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_area(unineg, idtipoarea,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_per_mant(String unineg,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_per_mant(unineg,anio);
    }

    public List<CantidadBean> sp_mc_cant_poblacion_consolidado(Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_consolidado(anio);
    }

    public List<PoblacionCons> sp_mc_cant_poblacion_cons_dim(String iddimension,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_dim(iddimension,anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_cons_grupoocu(String iddimension,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_grupoocu(iddimension,anio);
    }

    public List<PoblacionCons> sp_mc_cant_poblacion_cons_unineg(Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_unineg(anio);
    }

    public List<GrPieChartBean> sp_mc_grp_ocupacionales_no_docentes_mant(String unineg, Integer idTipoPersonal,Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_grp_ocupacionales_no_docentes_mant(unineg, idTipoPersonal,anio);
    }

    public List<UniNegBean> sp_mc_desc_unidadnegocio(String unineg) throws Exception {
        return resDimensionDAO.sp_mc_desc_unidadnegocio(unineg);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_grp_nivel(Integer anio, String unineg) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_grp_nivel(anio, unineg);
    }

    public List<CantidadBean> sp_mc_cargar_tbl_poblaciones() throws Exception {
        return resDimensionDAO.sp_mc_cargar_tbl_poblaciones();
    }

    public List<SatisfaccionGrlBean> sp_mc_satisfaccion_general_actual_estatal_particular(String tip_colegio, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_satisfaccion_general_actual_estatal_particular(tip_colegio, anio);
    }

    public List<ResDimensionBean> sp_mc_resultadoxdimensiones_estatal_particular(String tip_colegio, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_resultadoxdimensiones_estatal_particular(tip_colegio, anio);
    }

    public List<ConGeneralBean> sp_mc_sat_general_dimensiones_estatal_particular(String tip_colegio, String idDimension, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_sat_general_dimensiones_estatal_particular(tip_colegio, idDimension, anio);
    }

    public List<ConGeneralBean> sp_mc_sat_general_unineg_estatal_particular(String tip_colegio, String idDimension, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_sat_general_unineg_estatal_particular(tip_colegio, idDimension, anio);
    }

    public List<PoblacionCons> sp_mc_cant_poblacion_cons_unineg_estatal_particular(String tip_colegio, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_unineg_estatal_particular(tip_colegio, anio);
    }

    public List<CantidadBean> sp_mc_cant_poblacion_consolidado_estatal_particular(String tip_colegio, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_consolidado_estatal_particular(tip_colegio, anio);
    }

    public List<PoblacionCons> sp_mc_cant_poblacion_cons_dim_estatal_particular(String tip_colegio, String idDimension, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_dim_estatal_particular(tip_colegio, idDimension, anio);
    }

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(String tip_colegio, String idDimension, Integer anio) throws Exception {
        return resDimensionDAO.sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(tip_colegio, idDimension, anio);
    }



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /* ResDcimensionDAO  set and  get*/
    
    public ResDimensionDAO getResDimensionDAO() {
        return resDimensionDAO;
    }

    public void setResDimensionDAO(ResDimensionDAO resDimensionDAO) {
        this.resDimensionDAO = resDimensionDAO;
    }

    public List<HistoricoEDListBean> sp_ed_lista_historico(String all) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
