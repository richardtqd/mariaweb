/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.reporte.ConciliaRepBean;
import pe.marista.sigma.bean.reporte.ProcesosBancosRepBean;
import pe.marista.sigma.bean.reporte.ReporteDeudasRepBean;

/**
 *
 * @author MS-005
 */
public interface ProcesoBancoDAO {

    public List<ProcesoBancoBean> obtenerProcesoBanco() throws Exception;

    public List<ProcesoBancoBean> obtenerPorTipo(@Param("nombre") String nombre, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProcesoBancoBean> obtenerPorRec(@Param("nombre") String nombre, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProcesoBancoBean> obtenerPorEnvio(@Param("nombre") String nombre, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerNombreDuplicado(@Param("nombre") String nombre, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;//0:no existe, 1 :sí existe

    public Integer verificarEstructuraProcesoBanco(@Param("uniNeg") String uniNeg, @Param("flgProceso") Integer flgProceso) throws Exception;//0:no existe, 1 :sí existe

    public List<ProcesoBancoBean> obtenerUltimaLista(Integer idProcesoBanco) throws Exception;

    public void insertarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception;

    public void modificarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception;

    public void modificarProcesoBancoVer2(ProcesoBancoBean procesoBancoBean) throws Exception;
    public void modificarProcesoBancoVer3(ProcesoBancoBean procesoBancoBean) throws Exception;

    public void modificarErroresProc(ProcesoBancoBean procesoBancoBean) throws Exception;

    public void eliminarProcesoBanco(Integer idProcesoBanco) throws Exception;

    public List<ProcesoBancoBean> obtenerPorId(Integer idProcesoBanco) throws Exception;

    public List<ProcesoBancoBean> obtenerPorIdBanco(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public ProcesoBancoBean obtenerProcesoBancoId(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public ProcesoBancoBean obtenerProcesoBancoIdVer2(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public ProcesoBancoBean obtenerProcBancoPorId(Integer idProcesoBanco) throws Exception;

    public ProcesoBancoBean obterProcBancoPorId(Integer idProcesoBanco) throws Exception;

    public List<ProcesoBancoBean> obtenerPorUniNeg(String uniNeg) throws Exception;

    public List<ProcesoBancoBean> filtrarProceso(ProcesoBancoBean procesoBancoBean) throws Exception;

    public List<ProcesoBancoBean> filtrarProcesoVer2(ProcesoBancoBean procesoBancoBean) throws Exception;

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception;

    public List<ProcesoBancoBean> obtenerTipoProceso(@Param("parms") Map<Integer, Object> parms) throws Exception;

    public String obtenerFechaActual() throws Exception;

    public Integer obtenerFecha(@Param("var") Integer var) throws Exception;

    public Integer obtenerIdProcesoBancoMax(@Param("uniNeg") String uniNeg, @Param("flgProceso") Integer flgProceso) throws Exception;

    /* Reporte Proceso Banco */
    public List<ProcesosBancosRepBean> obtenerReporteProcesosBancos(@Param("parms") Map<String, Object> parms) throws Exception;

    public void eliminarProcesoBancoMas(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public List<ReporteDeudasRepBean> obtenerReporteDeuda(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    /* CONCILIA BANCO */
    public List<ConciliaRepBean> obtenerBancoConcilia(ConciliaRepBean conciliaRepBean) throws Exception;

    /* OBTENER FECHA DE PROCESO */
    public String obtenerFechaProceso(ProcesoBancoBean procesoBancoBean) throws Exception;
    /* OBTENER FECHA DE PROCESO REC */

    public String obtenerFechaProcesoBcoSigma(ProcesoBancoBean procesoBancoBean) throws Exception;

    public String obtenerNombreProceso(ProcesoBancoBean procesoBancoBean) throws Exception;

    /* REPORTE DE EXCEL */
    public Integer obtenerNumeroRegistros(ProcesoBancoBean procesoBancoBean) throws Exception;

    /* MODIFICAR PROCESO BANCO */
    public void modificarBancoCuenta(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("flgProceso") Integer flgProceso) throws Exception;

    public void actualizarProcesoBanco(ProcesoBancoBean procesoBancoBean) throws Exception;

    /* HORA DE CORTE */
    public List<ReporteDeudasRepBean> obtenerHoraCorte(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<ReporteDeudasRepBean> obtenerReporteDeudaHor(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void actualizarFiltroMasivo(ProcesoBancoBean procesoBancoBean) throws Exception;

}
