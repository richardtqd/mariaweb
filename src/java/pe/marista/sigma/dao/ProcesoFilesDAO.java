/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProcesoFilesBean;

/**
 *
 * @author MS002
 */
public interface ProcesoFilesDAO {

    public List<ProcesoFilesBean> obtenerProcesosFiles(String uniNeg) throws Exception;

    public List<ProcesoFilesBean> obtenerProcesosFilesDeta(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile) throws Exception;

//Obteniendo Listas
    public List<ProcesoFilesBean> obtenerProcesosFilesDetaCab(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile) throws Exception;

    public List<ProcesoFilesBean> obtenerProcesosFilesDetaDet(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile) throws Exception;

    public List<ProcesoFilesBean> obtenerProcesosFilesConFormula(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile, @Param("txt") String txt) throws Exception;

    public String obtenerValor(@Param("txt") String txt) throws Exception;
//End

//Obteniendo Listas
    public List<ProcesoFilesBean> obtenerProcesosFilesDetaCabPro(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile, @Param("flgProceso") Integer flgProceso) throws Exception;

    public List<ProcesoFilesBean> obtenerProcesosFilesDetaDetPro(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile, @Param("flgProceso") Integer flgProceso) throws Exception;

    public List<ProcesoFilesBean> obtenerProcesosFilesIntDetPro(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile, @Param("flgProceso") Integer flgProceso) throws Exception;
//End

//Obteniendo Lista para Proceso
    public List<ProcesoFilesBean> obtenerFileProceso(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("flgProceso") Integer flgProceso, @Param("idTipoFile") Integer idTipoFile) throws Exception;
    public List<ProcesoFilesBean> obtenerFileProcesoVer2(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("flgProceso") Integer flgProceso, @Param("idTipoFile") Integer idTipoFile) throws Exception;
//End 

    public Integer obtenerUltimaPosicion(@Param("uniNeg") String uniNeg, @Param("idTipoFile") Integer idTipoFile, @Param("ruc") String ruc, @Param("flgProceso") Integer flgProceso) throws Exception;

    public Integer obtenerMaxPosFin(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc) throws Exception;

    public ProcesoFilesBean obtenerFilesId(Integer idFile) throws Exception;

    public ProcesoFilesBean obtenerDetaFilesId(@Param("uniNeg") String uniNeg, @Param("idFile") Integer idFile) throws Exception;

    public void insertarProcesoFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarProcesosFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarPosicionFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarCabeceraFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarDetallesFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarSuperFile(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarPosicionesFiles(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void eliminarProcesosFile(ProcesoFilesBean procesoFilesBean) throws Exception;

    public void modificarDisabled(ProcesoFilesBean procesoFilesBean) throws Exception;

    public Integer obtenerNumFiles(@Param("uniNeg") String uniNeg, @Param("flg") Integer flg, @Param("idTipoFile") Integer idtipofile, @Param("ruc") String ruc) throws Exception;

    public Integer obtenerPosTipoFile(ProcesoFilesBean procesoFilesBean) throws Exception;

}
