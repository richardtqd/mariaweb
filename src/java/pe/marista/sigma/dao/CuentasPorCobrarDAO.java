/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.ModificacionesBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.reporte.ConciliaGradoRepBean;
import pe.marista.sigma.bean.reporte.ConciliaNivelRepBean;
import pe.marista.sigma.bean.reporte.ConciliaRepBean;
import pe.marista.sigma.bean.reporte.CuentasPorCobrarRepBean;
import pe.marista.sigma.bean.reporte.DetCobrosPensionesRepBean;
import pe.marista.sigma.bean.reporte.DetEstadoCtaRepBean;
import pe.marista.sigma.bean.reporte.DetResumenIngRepBean;
import pe.marista.sigma.bean.reporte.EstadoCtaRepBean;
import pe.marista.sigma.bean.reporte.EstadoRecibosRepBean;
import pe.marista.sigma.bean.reporte.EstudianteSaldoPivotRepBean;
import pe.marista.sigma.bean.reporte.EstudianteSaldoRepBean;
import pe.marista.sigma.bean.reporte.NotasOpeFecCobroRepBean;
import pe.marista.sigma.bean.reporte.NotasOpeFecVencRepBean;
import pe.marista.sigma.bean.reporte.NotasOperacionRepBean;
import pe.marista.sigma.bean.reporte.PensionesPorCobrarLPMRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionNivelDetalleRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionNivelesRepBean;
import pe.marista.sigma.bean.reporte.ProvicionPensionRepBean;
import pe.marista.sigma.bean.reporte.ResumenIngRepBean;
import pe.marista.sigma.bean.reporte.ResumenMatriculaRepBean;
import pe.marista.sigma.bean.reporte.SaldoPensionesRepBean;
import pe.marista.sigma.bean.reporte.VerificacionIngresoPlanillaRepBean;

/**
 *
 * @author MS002
 */
public interface CuentasPorCobrarDAO {

    public List<PersonaBean> obtenerMatriculados() throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaPorMat(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatAnio(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatAnioDscBeca(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin) throws Exception;

    public List<EstudianteBean> obtenerMatriculadosPorPeriodo(MatriculaBean matriculaBean) throws Exception;

    public List<EstudianteBean> obtenerFiltroEstudianteMatriculado(MatriculaBean matriculaBean) throws Exception;

    public CuentasPorCobrarBean obtenerCuentaPorId(@Param("idCtasXCobrar") Integer idCtasXCobrar, @Param("uniNeg") String uniNeg) throws Exception;

    public CuentasPorCobrarBean validarDocIngresoEnCtaCte(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaPorIdDocIngreso(@Param("idDocIngreso") Integer idCtasXCobrar, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerUltimoMesPago(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Integer obtenerSgteMesPagoAft(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public Integer obtenerSgteMesPagoBef(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public Double validarMonto(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public Integer obtenerSgteMesPagoAnterior(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public Integer obtenerAnioDeuda(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte) throws Exception;

    public void modificarCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void asignarDscto(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarCuentaPorEnvio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void cambiarEstadoCtaPorCobrar(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCtaCtePorEstudiantePorAnio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCtaCtePorEstudiante(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerDeudaEstudiante(@Param("idEstudiante") String idEstudiante) throws Exception;

    public List<CuentasPorCobrarBean> obtenerDeudaEstudiantePorAnio(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<Integer> obtenerIdDocIngreso(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<Integer> obtenerRecibosGenerados(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    //Masivo por mes
    public List<CuentasPorCobrarBean> obtenerCtasXCobrarbSegunMeses(@Param("listaMes") List<Integer> listaMes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    public List<Integer> obtenerCtasXCobrarModificaciones(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCtasXCobrarXMesesNoGenerado(@Param("listaMes") List<Integer> listaMes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    //Reportes
    public List<CuentasPorCobrarRepBean> obtenerEstadoCtaPorAnio(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EstadoCtaRepBean> ObtenerInformeEstadoCtaGen(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<EstadoCtaRepBean> ObtenerInformeEstadoCtaGenRangoFecha(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaFin") Date fechaFin) throws Exception;

    public List<DetEstadoCtaRepBean> ObtenerInformeEstadoCtaGenPorMes(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public List<DetEstadoCtaRepBean> ObtenerInformeEstadoCtaGenPorMesRangoFecha(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaFin") Date fechaFin) throws Exception;

    public void actualizarCuentaBanco(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    public void cambiarEstadoCtaPorCobrarModificaciones(@Param("uniNeg") String uniNeg, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("idEstudiante") String idEstudiante, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin, @Param("anio") Integer anio, @Param("monto") Double monto) throws Exception;

    public void cambiarEstadoCtaPorCobrarModificacionesActivosCambGr(@Param("uniNeg") String uniNeg, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("idEstudiante") String idEstudiante, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin, @Param("anio") Integer anio, @Param("monto") Double monto) throws Exception;

    public void modificarCuentaDocIngreso(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //resumen de Cuenta
    public List<ResumenIngRepBean> obtenerResumenIngPorAnio(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago) throws Exception;

    public List<DetResumenIngRepBean> obtenerResumenIngPorAnioPorDia(@Param("dia") Integer dia, @Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago) throws Exception;

    public List<DetResumenIngRepBean> obtenerResumenIngPorAnioPorDiaExcel(@Param("dia") Integer dia, @Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago) throws Exception;

    public Integer obtenerUltimoDiaMes(@Param("mes") Integer mes, @Param("anio") Integer anio) throws Exception;

    public List<EstudianteSaldoPivotRepBean> obtenerSaldoPenionesPorFechaPivot(@Param("flg") Integer flg, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("list") List<String> idEstudiante, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("secc") String secc) throws Exception;

    public List<EstudianteSaldoPivotRepBean> obtenerSaldoPenionesPorFechaPivotLetra(@Param("flg") Integer flg, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("list") List<String> idEstudiante, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("secc") String secc) throws Exception;

    public List<SaldoPensionesRepBean> obtenerSaldoPenionesPorFecha(@Param("flg") Integer flg, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("idEstudiante") String idEstudiante, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("nro") String nro, @Param("secc") String secc) throws Exception;

    public List<EstudianteSaldoRepBean> obtenerEstudianteConSaldoPorFecha(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("secc") String secc) throws Exception;

    //notas de ope
    public List<NotasOperacionRepBean> obtenerNotaOperacion(@Param("uniNeg") String uniNeg, @Param("mes") Integer mes) throws Exception;

    public List<NotasOpeFecCobroRepBean> obtenerNotaOperacionPorFechaCobros(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago, @Param("fechaCobro") Date fechaCobro) throws Exception;

    public List<NotasOpeFecVencRepBean> obtenerNotaOperacionPorFechaVenc(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago, @Param("fechaPago") Date fechaPago) throws Exception;

    public List<DetCobrosPensionesRepBean> obtenerDetNotaOperacionPorFechaVenc(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoLugarPago") Integer idTipoLugarPago, @Param("fechaPago") Date fechaPago, @Param("fechaVenc") Date fechaVenc) throws Exception;

    //ReporteProvisionPension
    public List<ProvicionPensionRepBean> obtenerProvisionCabecera(@Param("anio") Integer anio, @Param("mes") Integer mes, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProvicionPensionNivelesRepBean> obtenerProvisionNiveles(@Param("anio") Integer anio, @Param("mes") Integer mes, @Param("uniNeg") String uniNeg) throws Exception;

    public List<ProvicionPensionNivelDetalleRepBean> obtenerProvisionNivelesDetalle(@Param("anio") Integer anio, @Param("mes") Integer mes, @Param("nivel") String nivel, @Param("uniNeg") String uniNeg) throws Exception;

    //CONCILIACION
    public List<CuentasPorCobrarBean> filtrarConcilia(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> filtrarConciliaAmbos(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarConcilia(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public CuentasPorCobrarBean obtenerSqlExcel(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //LISTA DE PROCESOS DE BANCOS
    public List<CuentasPorCobrarBean> obtenerCuentaEnvio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaRecuperacion(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarCuentaFull(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //LISTA DE MESES PARA MENSAJE
    public List<CuentasPorCobrarBean> obtenerCuentaMensaje(@Param("cuentasPorCobrarBean") CuentasPorCobrarBean cuentasPorCobrarBean, @Param("list") List<Integer> ids) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaMensajeCole(@Param("cuentasPorCobrarBean") CuentasPorCobrarBean cuentasPorCobrarBean, @Param("list") List<Integer> ids) throws Exception;

    public void modificarCuentaMovimiento(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //REPORTE DE CONCILIADOS
    public List<ConciliaRepBean> obtenerReporteConcilia(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista, @Param("orden") Integer orden, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("ctsXCobrar") Integer ctsXCobrar) throws Exception;

    public List<ConciliaRepBean> obtenerReporteConcilia2(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista, @Param("orden") Integer orden, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("ctsXCobrar") Integer ctsXCobrar) throws Exception;

    public List<ConciliaRepBean> obtenerReporteConcilia3(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista, @Param("orden") Integer orden, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("ctsXCobrar") Integer ctsXCobrar) throws Exception;

    public List<ConciliaRepBean> obtenerReporteConcilia4(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista, @Param("orden") Integer orden, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("ctsXCobrar") Integer ctsXCobrar) throws Exception;

    public List<ConciliaRepBean> obtenerReporteConcilia5(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista, @Param("orden") Integer orden, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("ctsXCobrar") Integer ctsXCobrar) throws Exception;

    //REPORTE DE CONCILIADOS POR NIVEL
    public List<ConciliaNivelRepBean> obtenerReporteConciliaNivel(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //REPORTE DE CONCILIADOS POR NIVEL POR TOTAL
    public List<ConciliaNivelRepBean> obtenerReporteConciliaNivelTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //REPORTE DE CONCILIADOS POR GRADO
    public List<ConciliaGradoRepBean> obtenerReporteConciliaGrado(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //REPORTE DE CONCILIADOS POR GRADO POR TOTAL
    public List<ConciliaGradoRepBean> obtenerReporteConciliaGradoTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    //MODIFICAR MORA
    public void modificarMora(MatriculaBean matriculaBean) throws Exception;

    //Recibos
    public List<CuentasPorCobrarBean> obtenerRecibosParaImprimirATiempo(
            @Param("uniNeg") String uniNeg,
            @Param("idTipoLugarPago") Integer idTipoLugarPago,
            @Param("periodo") Integer periodo,
            @Param("list") List<Integer> lista,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<CuentasPorCobrarBean> obtenerRecibosParaImprimirDespuesFecha(
            @Param("uniNeg") String uniNeg,
            @Param("idTipoLugarPago") Integer idTipoLugarPago,
            @Param("periodo") Integer periodo,
            @Param("mesPago") Integer mesPago,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("periodoInicio") Integer periodoInicio,
            @Param("periodoFin") Integer periodoFin,
            @Param("list") List<Integer> lista,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<CuentasPorCobrarBean> obtenerRecibosAll(
            @Param("uniNeg") String uniNeg,
            @Param("idTipoLugarPago") Integer idTipoLugarPago,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("periodoInicio") Integer periodoInicio,
            @Param("periodoFin") Integer periodoFin,
            @Param("list") List<Integer> lista,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<CuentasPorCobrarBean> obtenerRecibosDeudores(
            @Param("uniNeg") String uniNeg,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("periodoInicio") Integer periodoInicio,
            @Param("periodoFin") Integer periodoFin,
            @Param("list") List<Integer> lista,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<CuentasPorCobrarBean> obtenerRecibosSinServicio(
            @Param("uniNeg") String uniNeg,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("periodoInicio") Integer periodoInicio,
            @Param("periodoFin") Integer periodoFin,
            @Param("list") List<Integer> lista,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<CuentasPorCobrarBean> obtenerRecibosAllMoras(
            @Param("uniNeg") String uniNeg,
            @Param("idTipoLugarPago") Integer idTipoLugarPago,
            @Param("fechaIni") Date fechaIni,
            @Param("fechaFin") Date fechaFin,
            @Param("orden") Integer orden,
            @Param("idNivelAcademico") Integer idNivelAcademico,
            @Param("idGradoAcademico") Integer idGradoAcademico,
            @Param("seccion") String seccion,
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre,
            @Param("periodoInicio") Integer periodoInicio,
            @Param("periodoFin") Integer periodoFin,
            @Param("list") List<Integer> lista,
            @Param("recibosMora") Integer recibosMora,
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;

    public List<SaldoPensionesRepBean> obtenerSaldoPenionesPorFechaLetra(@Param("flg") Integer flg, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("idEstudiante") String idEstudiante, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("nro") String nro, @Param("secc") String secc) throws Exception;

    public List<EstudianteSaldoRepBean> obtenerEstudianteConSaldoPorFechaLetra(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("idTipoStatusCtaCte") Integer idTipoStatusCtaCte, @Param("fecha") Date fecha, @Param("mes") Integer mes, @Param("idNivel") Integer idNivel, @Param("idGrado") Integer idGrado, @Param("secc") String secc) throws Exception;

    public Integer pagoMesAnterior(@Param("uniNeg") String uniNeg, @Param("idCta") Integer idCta) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaPorIdBecaDespues(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public void modificarConceptoCambioDeGrado(@Param("uniNeg") String uniNeg, @Param("idGradoAcademico") Integer idGradoAcademico, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante) throws Exception;

    public void insertarModificaciones(ModificacionesBean modificacionesBean) throws Exception;

    public List<PensionesPorCobrarLPMRepBean> obtenerPenionesPorCobrarLPM(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaCorte") Date fechaCorte,@Param("mesFin") Integer mesFin) throws Exception;

    public List<PensionesPorCobrarLPMRepBean> obtenerPenionesPagadasLPM(@Param("mes") Integer mes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("fechaCorte") Date fechaCorte,@Param("mesFin") Integer mesFin) throws Exception;

    public List<EstadoRecibosRepBean> obtenerEstadosRecibos(@Param("opcion") Integer opcion, @Param("mes") String mes, @Param("uniNeg") String uniNeg, @Param("anio") String anio) throws Exception;
    
    public List<ResumenMatriculaRepBean> obtenerResumenMatricula(@Param("opcion") Integer opcion, @Param("uniNeg") String uniNeg, @Param("anio") String anio, @Param("fechaCorte") Date fechaCorte) throws Exception;
    
    public List<ResumenMatriculaRepBean> obtenerResumenMatriculaGeneral(@Param("opcion") Integer opcion, @Param("uniNeg") String uniNeg, @Param("anio") String anio, @Param("fechaCorte") Date fechaCorte) throws Exception;

    public List<VerificacionIngresoPlanillaRepBean> obtenerVerificacionPlanilla(@Param("opcion") Integer opcion, @Param("uniNeg") String uniNeg, @Param("anio") String anio) throws Exception;

    public List<VerificacionIngresoPlanillaRepBean> obtenerVerificacionPlanillaCTS(@Param("opcion") Integer opcion, @Param("uniNeg") String uniNeg, @Param("anio") String anio) throws Exception;

    public List<EstadoRecibosRepBean> obtenerCabeceraEstadoRecibo(@Param("anio") String anio) throws Exception;
}
