/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ObjetivoEstrategicoDetBean;

/**
 *
 * @author MS002
 */
public interface ObjetivoEstrategicoDetDAO {
    
    public List<ObjetivoEstrategicoDetBean> obtenerTodos() throws Exception;
    
    public List<ObjetivoEstrategicoDetBean> obtenerDetalles(Integer idObjEstrategico) throws Exception;
    
    public ObjetivoEstrategicoDetBean obetenerUltimoDet(String uniNeg) throws Exception;
    
    public void insertarObjDet(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception;
    
    public void modificarObjDet(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception;
    
    public void eliminarObjDet(Integer idObjEstrategicoDet) throws Exception;
    
    public ObjetivoEstrategicoDetBean obtenerPorId(Integer idObjEstrategicoDet) throws Exception;
    
    public List<ObjetivoEstrategicoDetBean> obtenerListaPorId(Integer idObjEstrategicoDet) throws Exception;
    
    public List<ObjetivoEstrategicoDetBean> obtenerDetPorPlan(@Param("idPlanEstrategico") Integer idPlanEstrategico, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<ObjetivoEstrategicoDetBean> obtenerEstDetFiltro(ObjetivoEstrategicoDetBean objetivoEstrategicoDetBean) throws Exception;
    
    public List<ObjetivoEstrategicoDetBean> obtenerDetPorObj(@Param("uniNeg") String uniNeg,@Param("idObjEstrategico") Integer idObjEstrategico);
    
}
