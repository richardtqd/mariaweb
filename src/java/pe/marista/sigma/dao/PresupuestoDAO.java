/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.reporte.CuentaRepBean;

/**
 *
 * @author MS002
 */
public interface PresupuestoDAO {

    public List<PresupuestoBean> obtenerPresupesto(String uniNeg) throws Exception;

    public List<PresupuestoBean> obtenerPresupuestoFiltro(PresupuestoBean presupuestoBean) throws Exception;

    //Cambio de DAO
    public List<PresupuestoBean> obtenerPresupuestoCuenta(@Param("cuenta") Integer cuenta, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Integer obtenerPresExec(@Param("uniNeg") String uniNeg, @Param("cuenta") Integer cuenta) throws Exception;

    public void insertarPresupesto(PresupuestoBean presupuestoBean) throws Exception;

    public void insertarPresupestoPlan(PresupuestoBean presupuestoBean) throws Exception;

    public void modificarPresupesto(PresupuestoBean presupuestoBean) throws Exception;

    public void modificarPresupestoPlan(PresupuestoBean presupuestoBean) throws Exception;

    public void modificarDatosPresupuesto(PresupuestoBean presupuestoBean) throws Exception;

    public void modificarPresupuestoExec(PresupuestoBean presupuestoBean) throws Exception;

    public PresupuestoBean obtenerPorId(Integer idPresupuesto) throws Exception;

    public PresupuestoBean obtenerPresPorId(@Param("uniNeg") String uniNeg, @Param("cuenta") Integer cuenta, @Param("anio") Integer anio) throws Exception;

    public List<PresupuestoBean> obtenerListaPresID(Integer idPresupuesto) throws Exception;

    public void eliminarPresupesto(Integer idPresupuesto) throws Exception;

    public void eliminarPresupestoProg(PresupuestoBean presupuestoBean) throws Exception;

    public Object execProPres(@Param("uniNeg") String uniNeg) throws Exception;

    //REPORTES DE PRESUPUESTO 
    public List<CuentaRepBean> obtenerListaCuentaFiltro(CuentaRepBean cuentaRepBean) throws Exception;

    public List<CuentaRepBean> obtenerListaCuentaFiltroRep(CuentaRepBean cuentaRepBean) throws Exception;

    public Object execPresupuesto(PresupuestoBean presupuestoBean) throws Exception;

    //FILTRAR PRESUPUESTO
    public List<PresupuestoBean> filtrarPresupuesto(PresupuestoBean presupuestoBean) throws Exception;

    public void eliminarPresupestoNuevo(PresupuestoBean presupuestoBean) throws Exception;

    //OBTENER POR CUENTA
    public PresupuestoBean obtenerPorCuenta(PresupuestoBean presupuestoBean) throws Exception;

}
