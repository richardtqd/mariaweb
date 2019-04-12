/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ProcesoFalloBean;

/**
 *
 * @author JC
 */
public interface ProcesoFalloDAO {

    public List<ProcesoFalloBean> obtenerFalloUniNeg(ProcesoFalloBean procesoFalloBean) throws Exception;

    public List<ProcesoFalloBean> obtenerFalloPorBanco(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

}
