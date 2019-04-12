/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.reporte.CajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.CajaGenRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtaRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtasRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralRepBean;
import pe.marista.sigma.bean.reporte.DetCajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBeanDesglosado;
import pe.marista.sigma.bean.reporte.PruebaRepBean;

/**
 *
 * @author MS002
 */
public interface CajaGenDAO {

    public void insertarCajaGen(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean verificarApertura(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean verificarCierreCajaGen(@Param("idCajaGen") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public CajaGenBean verificarCierre(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean obtenerPorId(CajaGenBean cajaGenBean) throws Exception;

    public void modificarCierre(CajaGenBean cajaGenBean) throws Exception;

    public void modificarApertura(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean verificarRegistrosCajaDocIng(CajaGenBean cajaGenBean) throws Exception;// verificar si la caja ya tiene registros en doc ingreso o egreso

    public CajaGenBean verificarRegistrosCajaDocEgre(CajaGenBean cajaGenBean) throws Exception;// verificar si la caja ya tiene registros en doc ingreso o egreso

    public CajaGenBean verificarCierreDiaAnterior(CajaGenBean cajaGenBean) throws Exception;

    public void modificarIngresoSolYDol(CajaGenBean cajaGenBean) throws Exception;

    public void modificarEgresoSolYDol(CajaGenBean cajaGenBean) throws Exception;

    public List<EntidadBean> obtenerBancosDeposito(CajaGenBean cajaGenBean) throws Exception;

    public void modificarDepSoles(CajaGenBean cajaGenBean) throws Exception;

    public void modificarDepDolares(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean obtenerUltimaCajaAbierta(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean obtenerUltimaCajaAbiertaApertura(CajaGenBean cajaGenBean) throws Exception;

    public CajaGenBean obtenerCajaDepositoDiaAnterior(CajaGenBean cajaGenBean) throws Exception;

    //congregacion
    public void modificarDepCongreSoles(CajaGenBean cajaGenBean) throws Exception;

    public void modificarDepCongreDolares(CajaGenBean cajaGenBean) throws Exception;

    //reportes 
    public List<CajaGenRepBean> obtenerCajaGen(CajaGenBean cajaGenBean) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGenDesglosadoTop1(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("lista") List<Integer> idcaja) throws Exception;

    public List<DocIngresoRepBeanDesglosado> obtenerCajaGenDesglosado(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    public List<CajaGenRepBean> obtenerDetalleCajaGenDesglosado(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("lista") List<Integer> idcaja, @Param("modo") String modo) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGenPorCta(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("lista") List<Integer> idcaja) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGenPorCtaGen(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("lista") List<Integer> idcaja) throws Exception;

    public List<PruebaRepBean> consulta() throws Exception;

    //reportes apertura y cierre 
    public List<CajaGenBean> obtenerAperturasCajaPorCajero(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("flgTipoCajaGen") Integer flgTipoCajaGen) throws Exception;//historial de apertura de caja x cajero

    public List<CajaGenBean> obtenerCierresCajaPorCajero(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;//historial de cierres de caja 

    public List<CajaGenBean> obtenerAperturasCajaGeneral(CajaGenBean cajaGenBean) throws Exception;//historial de apertura de caja x cajero

    public List<CajaGenBean> obtenerCierresCajaGeneral(CajaGenBean cajaGenBean) throws Exception;//historial de cierres de caja 

    //reporte de cierres de caja
    public List<CajaGenCierreRepBean> obtenerCierresCajaPorFechaTop1(@Param("uniNeg") String uniNeg, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin) throws Exception;//historial de cierres de caja 

    public List<CajaGenCierreRepBean> obtenerCierresCajaPorFecha(@Param("uniNeg") String uniNeg, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin) throws Exception;//historial de cierres de caja 

    public List<DetCajaGenCierreRepBean> obtenerCierresCajaPorFechaUsu(
            @Param("uniNeg") String uniNeg,
            @Param("fechaInicio") String fechaInicio,
            @Param("fechaFin") String fechaFin,
            @Param("usuario") String usuario,
            @Param("idCaja") Integer idCaja) throws Exception;//histrial de cierres de caja 

    public List<CajaGeneralRepBean> obtenerCajaGeneralPorCtaFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("flgGen") Integer flgGen) throws Exception;

    //Reporte por Cuenta anterior mat y pension 
    public List<CajaGeneralCtaRepBean> obtenerDetCajaGeneralPorCtaFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, Integer mora) throws Exception;

    //METODO REPORTE TALLER CUENTA
    public List<CajaGeneralRepBean> obtenerCajaGenPorDetalleCtaForTaller(@Param("uniNeg") String uniNeg, @Param("fecIni") Date fecIni, @Param("fecFin") Date fecFin) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGenPorDetalleFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    //METODO REPORTE TALLER
    public List<CajaGenRepBean> obtenerCajaGenPorDetalleForTaller(@Param("uniNeg") String uniNeg, @Param("fecIni") Date fecIni, @Param("fecFin") Date fecFin) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGeneralPorDetalleFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    public List<CajaGenRepBean> obtenerCajaGeneralPorDetalleForIncMora(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    //Reporte por cuenta new
    public List<CajaGeneralCtasRepBean> obtenerCuentasCajaGeneralFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    public List<CajaGeneralCtaRepBean> obtenerDetallePorCtaFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen, @Param("cta") Integer cta) throws Exception;

    //MODIFICAR CAJAGEN-EVENTO
    public void modificarIngresoSolYDolEvento(CajaGenBean cajaGenBean) throws Exception;

    //VERIFICAR CAJA EVENTO
    public CajaGenBean verificarAperturaCajaEvento(CajaGenBean cajaGenBean) throws Exception;

    //HISTORIAL DE CAJA EVENTO
    public List<CajaGenBean> obtenerCierresCajaPorCajeroEvento(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception;

    //CIERRE DE CAJA EVENTO
    public void modificarCierreEvento(CajaGenBean cajaGenBean) throws Exception;

    //ARQUEO DE CAJA DE EVENTOS
    public List<CajaGenRepBean> obtenerCajaGenPorDetalleForEvento(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> idcajagen) throws Exception;

    //APERTURA CAJA EVENTOS
    public List<CajaGenBean> obtenerAperturasCajaPorCajeroEvento(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("flgTipoCajaGen") Integer flgTipoCajaGen) throws Exception;//historial de apertura de caja x cajero

    //CONSOLIDADO POR CUENTA
    public List<CajaGeneralCtasRepBean> obtenerCuentasCajaGeneralForTaller(@Param("uniNeg") String uniNeg, @Param("fecIni") Date fecIni, @Param("fecFin") Date fecFin) throws Exception;

    public List<CajaGeneralCtaRepBean> obtenerDetallePorCtaForTaller(@Param("uniNeg") String uniNeg, @Param("fecIni") Date fecIni, @Param("fecFin") Date fecFin, @Param("cuenta") Integer cuenta) throws Exception;
    
    public void actualizarCajaGenAuto(@Param("idCajaGen") Integer idCajaGen, @Param("uniNeg") String uniNeg) throws Exception;
}
