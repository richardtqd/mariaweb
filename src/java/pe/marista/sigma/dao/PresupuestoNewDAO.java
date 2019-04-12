/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.DetPresupuestoNewBean;
import pe.marista.sigma.bean.PresupuestoNewBean;
import pe.marista.sigma.bean.reporte.PrespuestoCabeceraRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCentroRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCuentaRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoDetalleRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoNewInicioRepBean;

/**
 *
 * @author Administrador
 */
public interface PresupuestoNewDAO {

    //Presupuesto nuevo
    public void insertarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception;

    public void modificarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception;

    public void eliminarPresupuestoCrCuentas(PresupuestoNewBean presupuestoBean) throws Exception;

    public PresupuestoNewBean obtenerPresupuesto(PresupuestoNewBean presupuestoBean) throws Exception;

    public List<PresupuestoNewBean> obtenerListaPresupuesto(PresupuestoNewBean presupuestoBean) throws Exception;

    public List<PresupuestoNewBean> obtenerListaPresupuestoVista(PresupuestoNewBean presupuestoBean) throws Exception;
//Reportes Por Cuenta 

    public List<PrespuestoCabeceraRepBean> obtenerCabecera(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta) throws Exception;
    
    public List<PrespuestoCabeceraRepBean> obtenerCabeceraIngresos(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta) throws Exception;

    public List<PresupuestoCuentaRepBean> obtenerCuentas(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta, @Param("inicio") String inicio) throws Exception;

    public List<PresupuestoCentroRepBean> obtenerCentroResp(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta, @Param("cuenta") Integer cuenta) throws Exception;

    //Reportes Por Centro de Responsabilidad
    public List<PresupuestoCuentaRepBean> obtenerCuentasResp(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta, @Param("cr") Integer cr) throws Exception;

    public List<PresupuestoCentroRepBean> obtenerCentroRespResp(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta, @Param("inicio") String inicio) throws Exception;

    public List<PresupuestoNewInicioRepBean> obtenerInicioCentroRespResp(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta) throws Exception;

    public List<PresupuestoNewInicioRepBean> obtenerInicioCuentas(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("tipoCuenta") String tipoCuenta) throws Exception;

    public List<PresupuestoNewBean> obtenerPresupuestoPorCRAnio(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    //Gilmar  
    public PresupuestoNewBean SP_obtenerPresupuestoPorId(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin) throws Exception;

    //RESUMEN SIN LAS QUE NO ESTAN PRESUPUESTADAS
    public List<PresupuestoNewBean> SP_obtenerPresupuestoPorCRAnio(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCr") List<Integer> cr,
            @Param("listCc") List<Integer> cc,
            @Param("flg") Integer flg,
            @Param("flg2") Integer flg2,
            @Param("flg3") Integer flg3,
            @Param("verpor") Integer verpor) throws Exception;

    //DETALLE X CUENTA
    public List<DetPresupuestoNewBean> SP_obtenerDetalleGastosCrCuenta(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin) throws Exception;

    //DETALLE X LISTA
    public List<DetPresupuestoNewBean> SP_obtenerDetalleGastosCrCuentaList(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCr") List<Integer> cr,
            @Param("listCc") List<Integer> cc,
            @Param("flg") Integer flg,
            @Param("flg2") Integer flg2,
            @Param("flg3") Integer flg3,
            @Param("verpor") Integer verpor) throws Exception;
    
    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuesto(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCc") List<Integer> cc) throws Exception;
    
    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoDetallado(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCc") List<Integer> cc) throws Exception;
    
    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoCabecera(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCc") List<Integer> cc) throws Exception;
    
    public List<DetPresupuestoNewBean> SP_obtenerIngresosPresupuestoMesaMes(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("listCc") List<Integer> cc) throws Exception;

    public List<DetPresupuestoNewBean> SP_obtenerConsolidado(
            @Param("presupuesto") PresupuestoNewBean presupuesto,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin) throws Exception;

    public List<PresupuestoDetalleRepBean> obtenerTituloDetalle(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<PresupuestoDetalleRepBean> obtenerEjecutado(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("cuenta") Integer cuenta) throws Exception;

    public List<PresupuestoDetalleRepBean> obtenerPorcentaje(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("cuenta") Integer cuenta) throws Exception;

    public List<PresupuestoDetalleRepBean> obtenerUtilizado(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("cuenta") Integer cuenta) throws Exception;

    public List<PresupuestoDetalleRepBean> obtenerDisponible(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("cuenta") Integer cuenta) throws Exception;

}
