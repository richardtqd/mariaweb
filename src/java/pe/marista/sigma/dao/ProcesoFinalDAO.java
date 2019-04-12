/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoFinalBean;
import pe.marista.sigma.bean.reporte.ConciliaBancoRepBean;
import pe.marista.sigma.bean.reporte.NotaAbonoRepBean;
import pe.marista.sigma.bean.reporte.PagosUniNegRepBean;
import pe.marista.sigma.bean.reporte.ProcesoFalloRepBean;
import pe.marista.sigma.bean.reporte.ReportePagoRepBean;

/**
 *
 * @author MS002
 */
public interface ProcesoFinalDAO {

    /* Proceso_Recuperacion */
    public void insertarProcesoFinal(ProcesoFinalBean procesoFinalBean) throws Exception;

    public Object execProRecup(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("valor") String valor, @Param("posicion") Integer posicion) throws Exception;

    public Object execProcesoRecup(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("sizeFile") Integer sizeFile, @Param("idprocesobanco") Integer idprocesobanco) throws Exception;

    public Object execProcesoRecupIns(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idprocesobanco") Integer idprocesobanco, @Param("elemento") String elemento, @Param("posicion") Integer posicion, @Param("id") Integer id, @Param("creaPor") String creaPor) throws Exception;

    public Object execProConcilia(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("id") Integer id, @Param("modiPor") String modiPor) throws Exception;

    public Integer obtenerMaxIdFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc) throws Exception;

    public List<Object> obtenerListaBancos(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public Integer obtenerPosItem(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("post") Integer post, @Param("flgProceso") Integer flgProceso) throws Exception;

    /* Proceso_Envio */
    public void execProEnvio(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("sizeFile") Integer sizeFile, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void execProEnvioIns(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("creaPor") String creaPor, @Param("posicion") Integer posicion, @Param("nomColumna") String nomColumna, @Param("constante") Integer constante, @Param("valorCons") String valorCons, @Param("noConstante") Integer noConstante) throws Exception;

    public void execProEnvioInsCons(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("creaPor") String creaPor, @Param("posicion") Integer posicion, @Param("nomColumna") String nomColumna, @Param("constante") Integer constante, @Param("valorCons") String valorCons) throws Exception;

    public List<Object> execProEnvioFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public Integer obtenerDisabledItem(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void execProEnvioCab(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("sizeFile") Integer sizeFile) throws Exception;

    public void execProEnvioCabIns(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("posicion") Integer posicion, @Param("nomColumna") String nomColumna, @Param("constante") Integer constante, @Param("valorCons") String valorCons, @Param("creaPor") String creaPor) throws Exception;

    public void execProEnvioCabInsFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("sizeFile") Integer sizeFile) throws Exception;

    public Integer obtenerPosItemCab(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("post") Integer post, @Param("flgProceso") Integer flgProceso) throws Exception;

    public void eliminarArchivoFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void eliminarFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("res") Integer res) throws Exception;

    public void execProErrores(@Param("uniNeg") String uniNeg, @Param("idprocesobanco") Integer idprocesobanco, @Param("creaPor") String creaPor, @Param("proceso") Integer proceso) throws Exception;

    public void execProEnvioInt(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("sizeFile") Integer sizeFile, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void execProEnvioIntIns(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("posicion") Integer posicion, @Param("nomColumna") String nomColumna, @Param("constante") Integer constante, @Param("valorCons") String valorCons, @Param("creaPor") String creaPor) throws Exception;

    public void execProEnvioCabIntFile(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("sizeFile") Integer sizeFile) throws Exception;

    public Integer obtenerPosItemInt(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("post") Integer post, @Param("flgProceso") Integer flgProceso) throws Exception;

    public Object execProCtaCte(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("modiPor") String modiPor) throws Exception;

    public Object execProEnvioPro(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("fechaVenc") Date fechaVenc, @Param("fechaInic") Date fechaInic, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante, @Param("codigo") String codigo, @Param("nombreCompleto") String nombreCompleto, @Param("idTipoConcepto") Integer idTipoConcepto, @Param("idConcepto") Integer idConcepto, @Param("creaPor") String creaPor, @Param("idTipoRegistro") String idTipoRegistro, @Param("estado") Integer estado) throws Exception;

    public Object execProEnvioProCab(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("creaPor") String creaPor, @Param("fecha") String fecha, @Param("registroDefecto") String registroDefecto) throws Exception;

    public Object execProEnvioProCur(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("idEstudiante") String idEstudiante, @Param("codigo") String codigo, @Param("nombres") String nombres, @Param("creaPor") String creaPor, @Param("fechaInic") String fechaInic, @Param("fecha") String fecha, @Param("idTipoConcepto") Integer idTipoConcepto, @Param("idConcepto") Integer idConcepto) throws Exception;

    public void modificarEnvioFiltro(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("defectoImporte") Integer defectoImporte) throws Exception;

    /*Asiento*/
    public Object execProAsiento(@Param("uniNeg") String uniNeg, @Param("objeto") String objeto, @Param("idObjeto") Integer idObjeto, @Param("creaPor") String creaPor, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    /* Reporte Pago */
    public List<ReportePagoRepBean> obtenerTotalRepBanco(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    /* Reporte Pago Deuda*/
    public List<ReportePagoRepBean> obtenerTotalRepBancoDeuda(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    /* Reporte de Pago Total Anual */
    public Object execProGenRepPago(@Param("uniNeg") String uniNeg, @Param("fecIni") Date fecIni, @Param("fecFin") Date fecFin, @Param("idTipoRepPago") Integer idTipoRepPago) throws Exception;

    public List<PagosUniNegRepBean> obtenerRepPago(PagosUniNegRepBean pagosUniNegRepBean) throws Exception;

    /* Reporte de pagos Nota de Abono */
    public List<NotaAbonoRepBean> obtenerRepNotaAbono(ProcesoBancoBean procesoBancoBean) throws Exception;

    /* Reporte de ProcesoFallo */
    public List<ProcesoFalloRepBean> obtenerListaFallo(ProcesoFalloRepBean procesoFalloRepBean);

    /* CONCILIA BANCOS */
    public List<ConciliaBancoRepBean> obtenerListaDetConcilia(ConciliaBancoRepBean conciliaBancoRepBean) throws Exception;

    public List<ConciliaBancoRepBean> obtenerListaDetConciliaFallo(ConciliaBancoRepBean conciliaBancoRepBean) throws Exception;

    /* PROCEDURE_RECUPERACIONN_CTACTE_PARTIDO */
    /* PRO_CTA_CTE1 */
    public Object execProCtaCte1(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("modiPor") String modiPor) throws Exception;

    /* PRO_CTA_CTE2 */
    public Object execProCtaCte2(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("modiPor") String modiPor) throws Exception;

    /* PRO_ERRORES */
    public Object execProError(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("modiPor") String modiPor) throws Exception;
}
