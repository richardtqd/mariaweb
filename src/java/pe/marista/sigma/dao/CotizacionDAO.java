/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CotizacionBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;

/**
 *
 * @author Administrador
 */
public interface CotizacionDAO {

    public List<CotizacionBean> obtenerTodosPorUniNeg(String uniNeg) throws Exception;

    public CotizacionBean obtenerPorId(CotizacionBean cotizacionBean) throws Exception;
    
    public CotizacionBean obtenerRucPorReq(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;
    
    public CotizacionBean obtenerPorIdCotiParaOrden(Integer idCotizacion) throws Exception;
 
    public List<CotizacionBean> obtenerListaPorId(CotizacionBean cotizacionBean) throws Exception;

    public List<CotizacionBean> obtenerPorFiltro(CotizacionBean orden) throws Exception;

    public void insertar(CotizacionBean cotizacionBean) throws Exception;

    public void actualizar(CotizacionBean cotizacionBean) throws Exception;
    
    public void changeStatusCoti(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg,@Param("flgAceptado") Boolean flgAceptado,@Param("modiPor") String modiPor) throws Exception;

    public Integer obtenerUltimo(String uniNeg) throws Exception;
    
    public Integer obtenerUltimoCoti(@Param("idCotizacion") Integer idCotizacion, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CotizacionBean> obtenerTodosSolDes(CotizacionBean bean) throws Exception;

    public List<CotizacionBean> obtenerTodosM(CotizacionBean bean) throws Exception;

    public SolicitudLogisticoBean obtenerPorSol(SolicitudLogisticoBean solicitudLogisticoBean) throws Exception;
    
    public List<CotizacionBean> obtenerTodosMCotizacion(CotizacionBean bean) throws Exception;
    
    public List<CotizacionBean> obtenerTodosMCotizacionAceptadas(CotizacionBean bean) throws Exception;
    
    public String obtenerUltimoCotizacion(String uniNeg) throws Exception;
}
