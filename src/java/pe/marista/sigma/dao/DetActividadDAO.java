/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.PlanContableBean;

/**
 *
 * @author MS002
 */
public interface DetActividadDAO {

    public List<DetActividadBean> obtenerDetActividad(String uniNeg) throws Exception;

    public List<DetActividadBean> obtenerPresupuesto(String uniNeg) throws Exception;

    public List<DetActividadBean> obtenerDetaPorID(Integer idActividad) throws Exception;

    public List<DetActividadBean> obtenerCuentaPorId(Integer cuenta) throws Exception;

    public List<DetActividadBean> obtenerMesImporte(@Param("idActividad") Integer idActividad, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetActividadBean> obtenerActCR(@Param("idActividad") Integer idActividad, @Param("cr") Integer cr, @Param("uniNeg") String uniNeg) throws Exception;

    public void insertarDetActividad(DetActividadBean detActividadBean) throws Exception;

    public void modificarDetActividad(DetActividadBean detActividadBean) throws Exception;

    public void modificarDetaAct(DetActividadBean detActividadBean) throws Exception;

    public void eliminarDetActividad(Integer idDetActividad) throws Exception;

    public DetActividadBean obtenerPorId(Integer idDetActividad) throws Exception;

    public List<DetActividadBean> obtenerListaPorId(Integer idDetActividad) throws Exception;

    public List<DetActividadBean> obtenerPorUniOrg(@Param("uniNeg") String uniNeg, @Param("idUniOrg") Integer idUniOrg) throws Exception;

    //Plan Contable
    public List<PlanContableBean> obtenerPlanContableFiltro(PlanContableBean planContableBean) throws Exception;

    public List<PlanContableBean> obtenerPlanContable() throws Exception;

    public PlanContableBean obtenerPlanPorId(Integer cuenta) throws Exception;

    public List<DetActividadBean> obtenerCuentas(@Param("uniNeg") String uniNeg, @Param("cuenta") String cuenta) throws Exception;

    //CAMBIOS DE METODOS
    public List<DetActividadBean> obtenerDetaPorActividad(DetActividadBean detActividadBean) throws Exception;
    
    //OBTENER MONTO DE ACTIVIDAD
    public BigDecimal obtenerImporteDetActividad(DetActividadBean detActividadBean) throws Exception;

}
