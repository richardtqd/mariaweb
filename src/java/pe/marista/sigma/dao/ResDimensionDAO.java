/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CantidadBean;
import pe.marista.sigma.bean.ConGeneralBean;
import pe.marista.sigma.bean.GrPieChartBean;
import pe.marista.sigma.bean.HistoricoBean;
import pe.marista.sigma.bean.PoblacionBean;
import pe.marista.sigma.bean.PoblacionCons;
import pe.marista.sigma.bean.PoblacionGrOcuBean;
import pe.marista.sigma.bean.ResDimensionBean;
import pe.marista.sigma.bean.ResSatGeneralBean;
import pe.marista.sigma.bean.SatisfaccionGrlBean;
import pe.marista.sigma.bean.SugerenciasBean;
import pe.marista.sigma.bean.UniNegBean;

/**
 *
 * @author MS001
 */
public interface ResDimensionDAO {

    /* RESULTADO POR DIMENSIONES */
    /*public List<ResDimensionBean> sp_mc_resultadoxdimensiones(String unineg) throws Exception; */
    public List<ResDimensionBean> sp_mc_resultadoxdimensiones(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception; 
    /* GRUPOS OCUPACIONALES - NO DOCENTES*/
    public List<GrPieChartBean> sp_mc_grp_ocupacionales_no_docentes(@Param("idTipoPersonal") Integer idTipoPersonal,@Param("idTipoArea") Integer idTipoArea,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception; 
    
    /* GRUPOS OCUPACIONALES - DIRECTIVOS - ADMINISTRATIVOS - FORMATIVOS*/
    public List<ResDimensionBean> sp_mc_grp_ocupacionales_dir_adm_for(@Param("idTipoPersonal") Integer idTipoPersonal,@Param("idTipoArea") Integer idTipoArea,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception; 
    
    /* GRUPOS OCUPACIONALES - SATISFACCION GENERAL */
   /* public List<ResSatGeneralBean> sp_mc_grp_ocupacionales_general(String unineg) throws Exception; */
    public List<ResSatGeneralBean> sp_mc_grp_ocupacionales_general(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception; 
    
    /* SATISFACCION GENERAL PIE*/
    public List<GrPieChartBean> sp_mc_pie_satisfaccion_general(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception; 
   
    /* GRUPOS OCUPACIONALES - MANTENIMIENTO */
    public List<ResDimensionBean> sp_mc_grp_ocupacionales_mantenimiento(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;             
    
    /* SATISFACCION GENERAL - HISTORICO */
    public List<HistoricoBean> sp_mc_general_historico(String unineg) throws Exception;     
    
    /* GRUPOS OCUPACIONALES - DOCENTES */
    public List<GrPieChartBean> sp_mc_grp_ocupacionales_docentes(@Param("idTipoPersonal") Integer idTipoPersonal,@Param("idNivelAcademico") Integer idNivelAcademico,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;             
   
    /* GRUPOS OCUPACIONALES - INICIAL - PRIMARIA - SECUNDARIA */
    public List<ResDimensionBean> sp_mc_grp_ocupacionales_ini_pri_sec(@Param("idTipoPersonal") Integer idTipoPersonal,@Param("idNivelAcademico") Integer idNivelAcademico,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;                 
    
    /* TIPO DE SEXO */
    public List<GrPieChartBean> sp_mc_tipo_sexo(@Param("sexo") Integer sexo,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;                 

    /* TIPO DE EDAD */
    public List<GrPieChartBean> sp_mc_rango_de_edades(@Param("idEdad") Integer idEdad,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;

    /* TIEMPO DE SERVICIO */
    public List<GrPieChartBean> sp_mc_tiempo_de_servicio(@Param("idTiempoServicio") Integer idTiempoServicio,@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;    
    
    /* LISTA UNIDADES DE NEGOCIO */
    public List<UniNegBean> sp_mc_unidadnegocio() throws Exception;
    
    /* PIE ADMINISTRATIDO  PARA  EL SECTOR */
    public List<GrPieChartBean> sp_mc_grp_ocupacionales_sector_adm(Integer anio) throws Exception;
    
    /* CONSOLIDADO GENERAL POR DIMENSION Y UNIDAD DE NEGOCIO */
    public List<ConGeneralBean> sp_mc_sat_general_unineg(@Param("idDimension") String idDimension,@Param("anio") Integer anio) throws Exception;
    
    /* CONSOLIDADO GENERAL POR DIMENSIONES */
    public List<ConGeneralBean> sp_mc_sat_general_dimensiones(@Param("idDimension") String idDimension,@Param("anio") Integer anio) throws Exception;   

    /*SATISFACCION GENERAL  ANIO  ACTUAL*/
    public List<SatisfaccionGrlBean> sp_mc_satisfaccion_general_actual(Integer anio) throws Exception;
    
    /*SUGERENCIAS*/
    public List<SugerenciasBean> sp_mc_sugerencias(Integer anio) throws Exception;
    
    /*CANTIDAD DE ENCUESTADOS */
    public List<PoblacionBean> sp_mc_cant_poblacion(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;    
    
    /*CANTIDAD DE ENCUESTADOS POR GRUPOS OCUPACIONALES */
    public List<PoblacionGrOcuBean> sp_mc_grp_ocupacionales_encuestados(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;   
    
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_sexo(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;   
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_rang_edad(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;   
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_time_ser(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;   
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_grp_ocupacional(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;       
   
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_nivel_academico(@Param("unineg") String unineg,@Param("idNivelAcademico") Integer idNivelAcademico,@Param("anio") Integer anio) throws Exception;            
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_area(@Param("unineg") String unineg,@Param("idtipoarea") Integer idtipoarea,@Param("anio") Integer anio) throws Exception;                
    
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_per_mant(@Param("unineg") String unineg,@Param("anio") Integer anio) throws Exception;   
    
    /* TABLAS  DE CANTIDADES DE CONSOLIDADOS*/
    public List<CantidadBean> sp_mc_cant_poblacion_consolidado(Integer anio) throws Exception;   
    public List<PoblacionCons> sp_mc_cant_poblacion_cons_unineg(Integer anio) throws Exception;   
    public List<PoblacionCons> sp_mc_cant_poblacion_cons_dim(@Param("iddimension") String idDimension,@Param("anio") Integer anio) throws Exception;   
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_cons_grupoocu(@Param("iddimension") String idDimension,@Param("anio") Integer anio) throws Exception;   
    
    
    public List<GrPieChartBean> sp_mc_grp_ocupacionales_no_docentes_mant(@Param("unineg") String unineg,@Param("idTipoPersonal") Integer idTipoPersonal,@Param("anio") Integer anio) throws Exception;     
    public List<UniNegBean> sp_mc_desc_unidadnegocio(String unineg) throws Exception;  
    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_grp_nivel(@Param("anio") Integer anio,@Param("unineg") String unineg) throws Exception;     
    
    public List<CantidadBean> sp_mc_cargar_tbl_poblaciones() throws Exception; 
    
    public List<SatisfaccionGrlBean> sp_mc_satisfaccion_general_actual_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("anio") Integer anio) throws Exception;
    
    public List<ResDimensionBean> sp_mc_resultadoxdimensiones_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("anio") Integer anio) throws Exception; 
    
    public List<ConGeneralBean> sp_mc_sat_general_dimensiones_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("idDimension") String idDimension,@Param("anio") Integer anio) throws Exception;   

    public List<ConGeneralBean> sp_mc_sat_general_unineg_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("idDimension") String idDimension,@Param("anio") Integer anio) throws Exception;

    public List<PoblacionCons> sp_mc_cant_poblacion_cons_unineg_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("anio") Integer anio) throws Exception;   

    public List<CantidadBean> sp_mc_cant_poblacion_consolidado_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("anio") Integer anio) throws Exception;   
    
    public List<PoblacionCons> sp_mc_cant_poblacion_cons_dim_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("iddimension") String idDimension,@Param("anio") Integer anio) throws Exception;   

    public List<PoblacionGrOcuBean> sp_mc_cant_poblacion_cons_grupoocu_estatal_particular(@Param("tip_colegio") String tip_colegio,@Param("iddimension") String idDimension,@Param("anio") Integer anio) throws Exception;   
        

    
}
