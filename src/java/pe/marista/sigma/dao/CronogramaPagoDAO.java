/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.reporte.CronogramaPagoRepBean; 

/**
 *
 * @author MS002
 */
public interface CronogramaPagoDAO {

    public List<CronogramaPagoBean> obtenerCronogramaPago(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public void insertarCronogramaPago(CronogramaPagoBean cronogramaPagoBean) throws Exception;

    public void actualizarCronogramaPago(CronogramaPagoBean cronogramaPagoBean) throws Exception;

    public CronogramaPagoBean obtenerIdCronograma(@Param("uniNeg") String uniNeg, @Param("idCronogramaPago") Integer idCronogramaPago) throws Exception;

    public CronogramaPagoBean validarCronograma(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("fecha") String fecha) throws Exception;

    public List<CronogramaPagoBean> obtenerCronogramaAnio(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<CronogramaPagoRepBean> obtenerCronogramaAnioRep(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

}
