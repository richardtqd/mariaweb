/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PeriodoBean;

/**
 *
 * @author MS002
 */
public interface PeriodoDAO {
    
    public List<PeriodoBean> obtenerPeriodo() throws Exception;
    
    public PeriodoBean obtenerPerPorId(@Param("uniNeg") String uniNeg,@Param("idPlanEstrategico")Integer idPlanEstrategico,@Param("idLinea")Integer idLinea,@Param("idObjEstrategico")Integer idObjEstrategico,@Param("idObjEstrategicoDet")Integer idObjEstrategicoDet,@Param("anio")Integer anio) throws Exception;
    
    public PeriodoBean obtenerPorDet(@Param("idObjEstrategicoDet")Integer idObjEstrategicoDet,@Param("uniNeg") String uniNeg) throws Exception;
    
    public List<PeriodoBean> obtenerPorDetalles(@Param("idObjEstrategicoDet")Integer idObjEstrategicoDet,@Param("uniNeg") String uniNeg) throws Exception;
    
    public void insertarPeriodo(PeriodoBean periodoBean) throws Exception;

    public void modificarPeriodo(PeriodoBean periodoBean) throws Exception;
    
    public void modificarPer(PeriodoBean periodoBean) throws Exception;
    
    public void eliminarPeriodo(PeriodoBean periodoBean) throws Exception;
    
    public PeriodoBean obtenerPorId(PeriodoBean periodoBean) throws Exception;
    
    public PeriodoBean obtenerPeriodoPorId(Integer idPeriodo) throws Exception;
    
    public List<PeriodoBean> obtenerPorPlan(Integer idPlanEstrategico) throws Exception;
  
}
