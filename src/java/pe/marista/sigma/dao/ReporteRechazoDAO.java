/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ReporteRechazoBean;

/**
 *
 * @author MS002
 */
public interface ReporteRechazoDAO {
    
    public List<ReporteRechazoBean> obtenerReporteRechazo(String uniNeg) throws Exception;
    
    public List<ReporteRechazoBean> obtenerReporteRechazoPorId(@Param("uniNeg")String uniNeg,@Param("idReporteRechazo")Integer idReporteRechazo) throws Exception; 
    
    public List<ReporteRechazoBean> obtenerRechazoPorCuenta(@Param("uniNeg")String uniNeg,@Param("idCtasXCobrar")Integer idCtasXCobrar) throws Exception; 
    
    public Integer obtenerCantidadRechazos(@Param("uniNeg") String uniNeg,@Param("idProcesoBanco")Integer idProcesoBanco) throws Exception;
    
    public Integer obtenerMaxIdRechazo(String uniNeg) throws Exception;
    
    public Integer obtenerMinIdRechazo(String uniNeg) throws Exception;
    
    public void insertarReporteRechazo(ReporteRechazoBean reporteRechazoBean) throws Exception;
    
    public void modificarStatusError(ReporteRechazoBean reporteRechazoBean) throws Exception;
     
}
