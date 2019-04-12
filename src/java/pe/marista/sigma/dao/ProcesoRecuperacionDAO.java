/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;

/**
 *
 * @author MS-005
 */
public interface ProcesoRecuperacionDAO {

    public List<ProcesoRecuperacionBean> obtenerProcesoRec() throws Exception;

    public List<ProcesoRecuperacionBean> obtenerProcesoRecPorUniNeg(String uniNeg) throws Exception;

    public List<ProcesoRecuperacionBean> obtenerPorProcesoBanco(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProcesoRecuperacionBean> obtenerListaPorId(Integer idProcesoRecup) throws Exception;

    public List<ProcesoRecuperacionBean> obtenerError(String uniNeg) throws Exception; //Obtener Error

    public List<ProcesoRecuperacionBean> obtenerFiltroRecuperacion(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception;

    public ProcesoRecuperacionBean obtenerRecupPorId(Integer idProcesoRecup) throws Exception;

    public Integer obtenerDeudaTotal(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg, @Param("idEstudiante") Integer idEstudiante) throws Exception;

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception;

    public void insertarrecuperacion(String file) throws Exception;

    public void insertarRecuperacion(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception;

    public void elimnarRecuperacionPorBanco(Integer idProcesoBanco) throws Exception;

    public void modificarConcilia(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception;

    public Integer obtenerDeudaConciliaRecup(@Param("idProcesoRecup") Integer idProcesoRecup, @Param("idDiscente") String idDiscente) throws Exception;//Cambio

//    public List<ProcesoRecuperacionBean> obtenerNoProcesados(Integer flgConcilia) throws Exception;
    public List<ProcesoRecuperacionBean> obtenerNoProcesados(@Param("flgConcilia") Integer flgConcilia, @Param("uniNeg") String uniNeg) throws Exception;

    public Object execProAsiento(@Param("uniNeg") String uniNeg, @Param("objeto") String objeto, @Param("idObjeto") Integer idObjeto, @Param("creaPor") String creaPor, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    //Ayuda
    public Float obtenerMontoTotal(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("idTipoFile") Integer idTipoFile, @Param("flgProceso") Integer flgProceso, @Param("ruc") String ruc, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerTotalRegistros(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("ruc") String ruc, @Param("uniNeg") String uniNeg) throws Exception;

    //TALLER SAN LUIS
    public Object execProRecuperacionTaller(ProcesoBancoBean procesoBancoBean) throws Exception;

}
