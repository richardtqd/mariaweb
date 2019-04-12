/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ObjetivoEstrategicaBean;

/**
 *
 * @author MS002
 */
public interface ObjetivoEstrategicoDAO {
    
    public List<ObjetivoEstrategicaBean> obtenerObjetivoEstrategico(String uniNeg) throws Exception;
    
    public List<ObjetivoEstrategicaBean> obtenerObjetivoPorPlan(@Param("idPlanEstrategico") Integer idPlanEstrategico, @Param("uniNeg") String uniNeg) throws Exception;
    
    public List<ObjetivoEstrategicaBean> obtenerObjPorLinea(Integer idLinea) throws Exception;
    
    public void insertarObjetivoEstrategico(ObjetivoEstrategicaBean objetivoEstrategicoBean) throws Exception;
    
    public void modificarObjetivoEstrategico(ObjetivoEstrategicaBean objetivoEstrategicaBean) throws Exception;
    
    public void eliminarObjetivoEstrategico(Integer idObjEstrategico) throws Exception;
    
    public ObjetivoEstrategicaBean obtenerPorId(Integer idObjEstrategico) throws Exception;
    
    public String obtenerCodObjetivoEstrategivo(String uniNeg) throws Exception;
    
}
