/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;

/**
 *
 * @author Administrador
 */
public interface AsientoDAO {

    public void insertarAsiento(AsientoBean asientoBean) throws Exception;

    public void cambiarEstadoAsiento(AsientoBean asientoBean) throws Exception;

    public List<CentroResponsabilidadBean> obtenerCRLiquidacion(AsientoBean asientoBean) throws Exception;

    public List<AsientoBean> obtenerCRLiq(@Param("idObjeto") Integer idObjeto, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarAsientoAnulacion(@Param("uniNeg") String uniNeg, @Param("idObjeto") Integer idObjeto, @Param("modiPor") String modiPor,@Param("objeto") String objeto) throws Exception;
}
