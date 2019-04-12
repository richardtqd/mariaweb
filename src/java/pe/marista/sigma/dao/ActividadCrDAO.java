/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.ActividadCrBean;

/**
 *
 * @author MS002
 */
public interface ActividadCrDAO {

    public List<ActividadCrBean> obtenerActividadCr(String uniNeg) throws Exception;

    public List<ActividadCrBean> obtenerGrafoPres(String uniNeg) throws Exception;

    public List<ActividadCrBean> obtenerPresupuestoCr(String uniNeg) throws Exception;

    public List<ActividadCrBean> obtenerPresupuestoCrId(@Param("uniNeg") String uniNeg, @Param("cr") Integer cr) throws Exception;

    public List<ActividadCrBean> obtenerActividadCrId(ActividadCrBean actividadCrBean) throws Exception;

    public List<ActividadCrBean> obtenerDetActividadCrId(ActividadCrBean actividadCrBean) throws Exception;

    public List<ActividadCrBean> obtenerPorCuenta(Integer cuenta) throws Exception;

    public List<ActividadCrBean> obtenerDibujoPresupuestoCr(@Param("cr") Integer cr, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ActividadCrBean> obtenerCrPorActividad(@Param("cuenta") Integer cuenta) throws Exception;

    public List<ActividadCrBean> obtenerSubCr(@Param("idActividad") Integer idActividad, @Param("cr") Integer cr, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ActividadCrBean> obtenerSubCrCuenta(@Param("idActividad") Integer idActividad, @Param("cr") Integer cr, @Param("uniNeg") String uniNeg, @Param("cuenta") Integer cuenta) throws Exception;

    public Integer obetenerPresupuestoGeneralExec(@Param("cr") Integer cr, @Param("uniNeg") String uniNeg, @Param("cuenta") Integer cuenta) throws Exception;

    public void insertarActividadCr(ActividadCrBean actividadCrBean) throws Exception;

    public void insertarActividadCrCuenta(ActividadCrBean actividadCrBean) throws Exception;

    public void modificarActividadCr(ActividadCrBean actividadCrBean) throws Exception;

    public void modificarCuentaSub(ActividadCrBean actividadCrBean) throws Exception;

    public void eliminarActividadCr(ActividadCrBean actividadCrBean) throws Exception;

    public void eliminarActCr(ActividadCrBean actividadCrBean) throws Exception;

    public void eliminarActividadCrCuenta(ActividadCrBean actividadCrBean) throws Exception;

    public ActividadCrBean obtenerPorId(ActividadCrBean actividadCrBean) throws Exception;

    public void insertarActCr(ActividadCrBean actividadCrBean) throws Exception;

}
