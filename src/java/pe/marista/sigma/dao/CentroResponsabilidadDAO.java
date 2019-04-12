/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.ViewMatriculaBean;

/**
 *
 * @author Administrador
 */
public interface CentroResponsabilidadDAO {

    public List<CentroResponsabilidadBean> obtenerCentroResponsabilidad() throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroResponsabilidadVista() throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroResNivel3() throws Exception;

    public List<CentroResponsabilidadBean> obtenerIdTipoCR(Integer idTipoGrupoCR) throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroPorCodigo(Integer idTipoGrupoCR) throws Exception;

    public CentroResponsabilidadBean obtenerCentroResPorNombre(String nombre) throws Exception;

    public void insertarCentroResponsabilidad(CentroResponsabilidadBean centroResponsabilidadBean) throws Exception;

    public void modificarCentroResponsabilidad(CentroResponsabilidadBean centroResponsabilidadBean) throws Exception;

    public Integer eliminarCentroResposabilidad(Integer cr) throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroInicial() throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroPrimaria() throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroSecundaria() throws Exception;

    public List<CentroResponsabilidadBean> obtenerCentroBachiller() throws Exception;

    public ViewMatriculaBean obtenerTotales(ViewMatriculaBean viewMatriculaBean) throws Exception;

//    
//    public List<CentroCostoBean> obtenerTodos() throws Exception;  
//   
//    public List<CentroCostoBean> obtenerPorFiltro(CentroCostoBean centroCostoBean) throws Exception;
//
//    public CentroCostoBean buscarPorId(int id) throws Exception;
//
//    public void insertar(CentroCostoBean centroCostoBean) throws Exception;
//    
//    public void actualizar(CentroCostoBean centroCostoBean) throws Exception;
//    
//    public void eliminar(CentroCostoBean centroCostoBean) throws Exception;
    public CentroResponsabilidadBean obtenerCRPorId(Integer cr) throws Exception;

    //Cambio ACt_CR
    public List<CentroResponsabilidadBean> obtenerCrInAct(@Param("idActividad") Integer idActividad) throws Exception;

    public List<CentroResponsabilidadBean> obtenerCrOutAct(@Param("idActividad") Integer idActividad) throws Exception;

    public List<CentroResponsabilidadBean> obtenerPresCr(@Param("uniNeg") String uniNeg) throws Exception;

    //Cr Requerimiento Reequerimiento
    public List<CentroResponsabilidadBean> obtenerInCrReque(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerOutCrReque(@Param("idRequerimiento") Integer idRequerimiento, @Param("uniNeg") String uniNeg) throws Exception;

    //Cr Requerimiento Reequerimiento
    public List<CentroResponsabilidadBean> obtenerInCrRegistro(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerOutCrRegistro(@Param("idRegistroCompra") Integer idRegistroCompra, @Param("uniNeg") String uniNeg) throws Exception;

    //Cr Requerimiento Sol caja ch
    public List<CentroResponsabilidadBean> obtenerInCrSolCaj(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerOutCrSolCaj(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerOutCrLiq(@Param("idSolicitudCajaCh") Integer idSolicitudCajaCh, @Param("uniNeg") String uniNeg) throws Exception;

    //FILTRO
    public List<CentroResponsabilidadBean> obtenerCrPorNivelAcademico(String nomNivAca) throws Exception;

    //uniorg
    public List<CentroResponsabilidadBean> obtenerPorUniOrg(Integer idUniOrg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerPorUniOrgPadre(Integer idUniOrg) throws Exception;

    //CAMBIO CR POR DETACTIVIDAD
    //Cambio ACt_CR
    public List<CentroResponsabilidadBean> obtenerCrInDetAct(DetActividadBean detActividadBean) throws Exception;

    public List<CentroResponsabilidadBean> obtenerCrOutDetAct(DetActividadBean detActividadBean) throws Exception;

    //Cr Requerimiento Reequerimiento
    public List<CentroResponsabilidadBean> obtenerInCrAcceso(@Param("idTipoAcceso") Integer idTipoAcceso, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerOutCrAcceso(@Param("idTipoAcceso") Integer idTipoAcceso, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CentroResponsabilidadBean> obtenerInCrAccesoPorNivel(@Param("list") List<Integer> ids, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;
     

}
