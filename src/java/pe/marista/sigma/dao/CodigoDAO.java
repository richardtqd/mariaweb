/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.TipoCodigoBean;

/**
 *
 * @author Administrador
 */
public interface CodigoDAO {
    public List<CodigoBean> obtenerPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoSeguro(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoEst(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoOpe(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoSol(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoDes(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoDocumento(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoCat(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoMamaApoVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoPapaApoVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoPapaMamaVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoPapaVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoMamaVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoApoVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoNingunoVive(TipoCodigoBean tipoCodigoBean) throws Exception;
    
    public List<CodigoBean> obtenerPorTipoDespacho(TipoCodigoBean tipoCodigoBean) throws Exception; 
    public List<MotivoMovimientoBean> obtenerMotivoPorMov(Integer idTipoMov) throws Exception;
    public List<CodigoBean> obtenerDuracion(Integer idTipoDuracion) throws Exception;
    public CodigoBean obtenerPorId(CodigoBean codigoBean) throws Exception;
    public CodigoBean obtenerPorCodigo(CodigoBean codigoBean) throws Exception;
    public CodigoBean obtenerPorCodigoDisCR(@Param("id") Integer id, @Param("uniNeg") String uniNeg);   
    public CodigoBean obtenerPorCodigoDisCRReq(@Param("id") Integer id, @Param("uniNeg") String uniNeg);   
    public MotivoMovimientoBean obtenerMotivoPorId(Integer idTipoMotivo) throws Exception;
    public void insertar(CodigoBean codigoBean) throws Exception;
    public void actualizar(CodigoBean codigoBean) throws Exception;
    public void eliminar(CodigoBean codigoBean) throws Exception;
    public void eliminarPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerTodos() throws Exception;
    public List<CodigoBean> funcionObtenerPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> funcionObtenerPorTipoSoloInsc(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerParentescoSinPadres(@Param("parms")Map<String, Object> parms) throws Exception;    
    public List<CodigoBean> obtenerParentescoConTodo(@Param("parms")Map<String, Object> parms) throws Exception;    
    public List<CodigoBean> obtenerCodigoDocIngreso(@Param("parms")Map<String, Object> parms) throws Exception;     
    public List<CodigoBean> obtenerCodigoDocEgreso(@Param("parms")Map<String, Object> parms) throws Exception;    
    public List<CodigoBean> obtenerCodigoStatusPost(@Param("parms")Map<String, Object> parms) throws Exception;    
//    public List<CodigoBean> obtenerCodigoMonedas(@Param("parms")Map<String, Object> parms) throws Exception;  
    public List<CodigoBean> obtenerMotivoPorDuracion(Integer idTipoDuracion) throws Exception;
    public List<CodigoBean> obtenerTemporal() throws Exception;
    public List<CodigoBean> obtenerPermanente() throws Exception;
    public List<CodigoBean> obtenerPorStatusEst(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorStatusEstModificaciones(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorIdLista(String codigo) throws Exception;
    public List<CodigoBean> obtenerPorTipoRecibo(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoSoles(TipoCodigoBean tipoCodigoBean) throws Exception;
    
    public List<CodigoBean> obtenerPorTipoDependiente(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoOtrosDependiente(TipoCodigoBean tipoCodigoBean) throws Exception;
    public List<CodigoBean> obtenerPorTipoStatusCtaCte(TipoCodigoBean tipoCodigoBean) throws Exception;
}
