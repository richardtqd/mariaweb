/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProcesoErrorBean;

/**
 *
 * @author MS002
 */
public interface ProcesoErrorDAO {

    public List<ProcesoErrorBean> filtrarError(@Param("uniNeg") String uniNeg, @Param("creaFecha") String creaFecha) throws Exception;

    public List<ProcesoErrorBean> obtenerError(@Param("uniNeg") String uniNeg, @Param("flgError") Integer flgError) throws Exception;

    public List<ProcesoErrorBean> obtenerPorProcesoBanco(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarError(ProcesoErrorBean procesoErrorBean) throws Exception;

    public ProcesoErrorBean obtenerPorId(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

}
