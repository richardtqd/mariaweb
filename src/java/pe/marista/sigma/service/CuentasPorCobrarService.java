/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DocIngresoBean;
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
import pe.marista.sigma.dao.CuentasPorCobrarDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS002
 */
public class CuentasPorCobrarService {

    private CuentasPorCobrarDAO cuentasPorCobrarDAO;

    public CuentasPorCobrarDAO getCuentasPorCobrarDAO() {
        return cuentasPorCobrarDAO;
    }

    public void setCuentasPorCobrarDAO(CuentasPorCobrarDAO cuentasPorCobrarDAO) {
        this.cuentasPorCobrarDAO = cuentasPorCobrarDAO;
    }

    public List<PersonaBean> obtenerMatriculados() throws Exception {
        return cuentasPorCobrarDAO.obtenerMatriculados();
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorMat(String idEstudiante, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorMat(idEstudiante, uniNeg);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatAnio(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorMatAnio(idEstudiante, uniNeg, anio);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatAnioDscBeca(String idEstudiante, String uniNeg, Integer anio, Integer mesInicio, Integer mesFin) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorMatAnioDscBeca(idEstudiante, uniNeg, anio, mesInicio, mesFin);
    }

    public List<EstudianteBean> obtenerMatriculadosPorPeriodo(MatriculaBean matriculaBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerMatriculadosPorPeriodo(matriculaBean);
    }

    public List<CuentasPorCobrarBean> obtenerCtaCtePorEstudiantePorAnio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerCtaCtePorEstudiantePorAnio(cuentasPorCobrarBean);
    }

    public List<CuentasPorCobrarBean> obtenerCtaCtePorEstudiante(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerCtaCtePorEstudiante(cuentasPorCobrarBean);
    }

    public List<EstudianteBean> obtenerFiltroEstudianteMatriculado(MatriculaBean matriculaBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerFiltroEstudianteMatriculado(matriculaBean);
    }

    public List<CuentasPorCobrarBean> obtenerDeudaEstudiante(String idEstudiante) throws Exception {
        return cuentasPorCobrarDAO.obtenerDeudaEstudiante(idEstudiante);
    }

    public List<CuentasPorCobrarBean> obtenerDeudaEstudiantePorAnio(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerDeudaEstudiantePorAnio(idEstudiante, uniNeg, anio);
    }

    public CuentasPorCobrarBean obtenerCuentaPorId(Integer idCtasXCobrar, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorId(idCtasXCobrar, uniNeg);
    }

    public CuentasPorCobrarBean validarDocIngresoEnCtaCte(Integer idDocIngreso, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.validarDocIngresoEnCtaCte(idDocIngreso, uniNeg);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorIdDocIngreso(Integer idDocIngreso, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorIdDocIngreso(idDocIngreso, uniNeg);
    }

    public Integer obtenerUltimoMesPago(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerUltimoMesPago(idEstudiante, uniNeg, anio);
    }

    public Integer obtenerSgteMesPagoAft(String idEstudiante, String uniNeg, Integer anio, Integer mes) throws Exception {
        return cuentasPorCobrarDAO.obtenerSgteMesPagoAft(idEstudiante, uniNeg, anio, mes);
    }

    public Integer obtenerSgteMesPagoBef(String idEstudiante, String uniNeg, Integer anio, Integer mes) throws Exception {
        return cuentasPorCobrarDAO.obtenerSgteMesPagoBef(idEstudiante, uniNeg, anio, mes);
    }

    public Integer obtenerSgteMesPagoAnterior(String idEstudiante, String uniNeg, Integer anio, Integer mes) throws Exception {
        return cuentasPorCobrarDAO.obtenerSgteMesPagoAnterior(idEstudiante, uniNeg, anio, mes);
    }

    public void modificarCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarCuenta(cuentasPorCobrarBean);
    }

    public void asignarDscto(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.asignarDscto(cuentasPorCobrarBean);
    }

    @Transactional
    public void modificarCuentaPorEnvio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarCuentaPorEnvio(cuentasPorCobrarBean);
    }

    @Transactional
    public void modificarCuentaFull(CuentasPorCobrarBean cuentasPorCobrarBean, String usuario) throws Exception {
        cuentasPorCobrarDAO.modificarCuentaFull(cuentasPorCobrarBean);
        MatriculaService matriculaService = BeanFactory.getMatriculaService();
        cuentasPorCobrarBean.getMatriculaBean().setAnio(cuentasPorCobrarBean.getAnio());
        matriculaService.modificarMatriculaGradoAcaPorCta(cuentasPorCobrarBean.getMatriculaBean(), usuario, cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean());
        if (cuentasPorCobrarBean.getDocIngresoBean().getIdDocIngreso() != null) {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            DocIngresoBean doc = new DocIngresoBean();
            doc.setIdDocIngreso(cuentasPorCobrarBean.getDocIngresoBean().getIdDocIngreso());
            doc.setUnidadNegocioBean(cuentasPorCobrarBean.getUnidadNegocioBean());
            doc.getCuentasPorCobrarBean().getMatriculaBean().setGradoAcademicoBean(cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean());
//            doc.setModiPor(usuario);
            docIngresoService.cambiarGradoAcademicoEnDocIng(doc);
        }
    }

    @Transactional
    public void cambiarEstadoCtaPorCobrar(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.cambiarEstadoCtaPorCobrar(cuentasPorCobrarBean);
    }

    @Transactional
    public void actualizarCuentaBanco(String uniNeg, Integer idProcesoBanco) throws Exception {
        cuentasPorCobrarDAO.actualizarCuentaBanco(uniNeg, idProcesoBanco);
    }

    public List<CuentasPorCobrarRepBean> obtenerEstadoCtaPorAnio(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerEstadoCtaPorAnio(idEstudiante, uniNeg, anio);
    }

    public List<EstadoCtaRepBean> ObtenerInformeEstadoCtaGen(Integer mes, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.ObtenerInformeEstadoCtaGen(mes, uniNeg, anio);
    }

    public List<DetEstadoCtaRepBean> ObtenerInformeEstadoCtaGenPorMes(Integer mes, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.ObtenerInformeEstadoCtaGenPorMes(mes, uniNeg, anio);
    }

    @Transactional
    public void modificarCuentaDocIngreso(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarCuentaDocIngreso(cuentasPorCobrarBean);
    }

    public List<ResumenIngRepBean> obtenerResumenIngPorAnio(Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago) throws Exception {
        return cuentasPorCobrarDAO.obtenerResumenIngPorAnio(mes, uniNeg, anio, idTipoLugarPago);
    }

    public List<DetResumenIngRepBean> obtenerResumenIngPorAnioPorDia(Integer dia, Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago) throws Exception {
        return cuentasPorCobrarDAO.obtenerResumenIngPorAnioPorDia(dia, mes, uniNeg, anio, idTipoLugarPago);
    }

    public Integer obtenerUltimoDiaMes(Integer mes, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerUltimoDiaMes(mes, anio);
    }

    public List<SaldoPensionesRepBean> obtenerSaldoPenionesPorFecha(Integer flg, String uniNeg, Integer anio, Integer idTipoStatusCtaCte, String idEstudiante, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String nro, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerSaldoPenionesPorFecha(flg, uniNeg, anio, idTipoStatusCtaCte, idEstudiante, fecha, mes, idNivel, idGrado, nro, secc);
    }

    public List<EstudianteSaldoRepBean> obtenerEstudianteConSaldoPorFecha(String uniNeg, Integer anio, Integer idTipoStatusCtaCte, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerEstudianteConSaldoPorFecha(uniNeg, anio, idTipoStatusCtaCte, fecha, mes, idNivel, idGrado, secc);
    }

    public List<NotasOperacionRepBean> obtenerNotaOperacion(String uniNeg, Integer mes) throws Exception {
        return cuentasPorCobrarDAO.obtenerNotaOperacion(uniNeg, mes);
    }

    public List<NotasOpeFecCobroRepBean> obtenerNotaOperacionPorFechaCobros(Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago, Date fechaCobro) throws Exception {
        return cuentasPorCobrarDAO.obtenerNotaOperacionPorFechaCobros(mes, uniNeg, anio, idTipoLugarPago, fechaCobro);
    }

    public List<NotasOpeFecVencRepBean> obtenerNotaOperacionPorFechaVenc(Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago, Date fechaPago) throws Exception {
        return cuentasPorCobrarDAO.obtenerNotaOperacionPorFechaVenc(mes, uniNeg, anio, idTipoLugarPago, fechaPago);
    }

    public List<DetCobrosPensionesRepBean> obtenerDetNotaOperacionPorFechaVenc(Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago, Date fechaPago, Date fechaVenc) throws Exception {
        return cuentasPorCobrarDAO.obtenerDetNotaOperacionPorFechaVenc(mes, uniNeg, anio, idTipoLugarPago, fechaPago, fechaVenc);
    }
    //ReporteProvisionesPensiones

    public List<ProvicionPensionRepBean> obtenerProvisionCabecera(Integer anio, Integer mes, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerProvisionCabecera(anio, mes, uniNeg);
    }

    public List<ProvicionPensionNivelesRepBean> obtenerProvisionNiveles(Integer anio, Integer mes, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerProvisionNiveles(anio, mes, uniNeg);
    }

    public List<ProvicionPensionNivelDetalleRepBean> obtenerProvisionNivelesDetalle(Integer anio, Integer mes, String nivel, String uniNeg) throws Exception {
        return cuentasPorCobrarDAO.obtenerProvisionNivelesDetalle(anio, mes, nivel, uniNeg);
    }

    public CuentasPorCobrarBean obtenerSqlExcel(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerSqlExcel(cuentasPorCobrarBean);
    }

    //CONCILIA 
    public List<CuentasPorCobrarBean> filtrarConcilia(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.filtrarConcilia(cuentasPorCobrarBean);
    }

    public void modificarConcilia(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarConcilia(cuentasPorCobrarBean);
    }

    //PROCESO DE ENVIO
    public List<CuentasPorCobrarBean> obtenerCuentaEnvio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaEnvio(cuentasPorCobrarBean);
    }

    //PROCESO DE RECUPERACION
    public List<CuentasPorCobrarBean> obtenerCuentaRecuperacion(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaRecuperacion(cuentasPorCobrarBean);
    }

    //CUENTA POR MENSAJE
    public List<CuentasPorCobrarBean> obtenerCuentaMensaje(CuentasPorCobrarBean cuentasPorCobrarBean, List<Integer> ids) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaMensaje(cuentasPorCobrarBean, ids);
    }

    public Integer obtenerAnioDeuda(String idEstudiante, String uniNeg, Integer idTipoStatusCtaCte) throws Exception {
        return cuentasPorCobrarDAO.obtenerAnioDeuda(idEstudiante, uniNeg, idTipoStatusCtaCte);
    }

    @Transactional
    public void modificarCuentaMovimiento(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarCuentaMovimiento(cuentasPorCobrarBean);
    }

    /* REPORTE DE CONCILIADOS */
    public List<ConciliaRepBean> obtenerReporteConcilia(String uniNeg, List<Integer> lista, Integer orden, Date fechaInicio, Date fechaFin, Integer ctsXCobrar) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConcilia(uniNeg, lista, orden, fechaInicio, fechaFin, ctsXCobrar);
    }

    public List<ConciliaNivelRepBean> obtenerReporteConciliaNivel(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConciliaNivel(cuentasPorCobrarBean);
    }

    public List<ConciliaGradoRepBean> obtenerReporteConciliaGrado(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConciliaGrado(cuentasPorCobrarBean);
    }

    public List<ConciliaNivelRepBean> obtenerReporteConciliaNivelTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConciliaNivelTotal(cuentasPorCobrarBean);
    }

    public List<ConciliaGradoRepBean> obtenerReporteConciliaGradoTotal(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConciliaGradoTotal(cuentasPorCobrarBean);
    }

    @Transactional
    public void modificarMora(MatriculaBean matriculaBean) throws Exception {
        cuentasPorCobrarDAO.modificarMora(matriculaBean);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaMensajeCole(CuentasPorCobrarBean cuentasPorCobrarBean, List<Integer> ids) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaMensajeCole(cuentasPorCobrarBean, ids);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosParaImprimirATiempo(String uniNeg, Integer idTipoLugarPago, Integer periodo, List<Integer> lista, Date fechaIni, Date fechaFin, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosParaImprimirATiempo(uniNeg, idTipoLugarPago, periodo, lista, fechaIni, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, flgRecImp);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosParaImprimirDespuesFecha(String uniNeg, Integer idTipoLugarPago, Integer periodo, Integer mesPago, Date fechaIni, Date fechaFin, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer periodoInicio, Integer periodoFin, List<Integer> lista, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosParaImprimirDespuesFecha(uniNeg, idTipoLugarPago, periodo, mesPago, fechaIni, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, lista, flgRecImp);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosAll(String uniNeg, Integer idTipoLugarPago, Date fechaIni, Date fechaFin, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer periodoInicio, Integer periodoFin, List<Integer> lista, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosAll(uniNeg, idTipoLugarPago, fechaIni, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, lista, flgRecImp);
    }

    public List<ConciliaRepBean> obtenerReporteConcilia2(String uniNeg, List<Integer> lista, Integer orden, Date fechaInicio, Date fechaFin, Integer ctsXCobrar) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConcilia2(uniNeg, lista, orden, fechaInicio, fechaFin, ctsXCobrar);
    }

    public List<ConciliaRepBean> obtenerReporteConcilia3(String uniNeg, List<Integer> lista, Integer orden, Date fechaInicio, Date fechaFin, Integer ctsXCobrar) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConcilia3(uniNeg, lista, orden, fechaInicio, fechaFin, ctsXCobrar);
    }

    public List<ConciliaRepBean> obtenerReporteConcilia4(String uniNeg, List<Integer> lista, Integer orden, Date fechaInicio, Date fechaFin, Integer ctsXCobrar) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConcilia4(uniNeg, lista, orden, fechaInicio, fechaFin, ctsXCobrar);
    }

    public List<ConciliaRepBean> obtenerReporteConcilia5(String uniNeg, List<Integer> lista, Integer orden, Date fechaInicio, Date fechaFin, Integer ctsXCobrar) throws Exception {
        return cuentasPorCobrarDAO.obtenerReporteConcilia5(uniNeg, lista, orden, fechaInicio, fechaFin, ctsXCobrar);
    }

    public List<CuentasPorCobrarBean> filtrarConciliaAmbos(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        return cuentasPorCobrarDAO.filtrarConciliaAmbos(cuentasPorCobrarBean);
    }

    public List<SaldoPensionesRepBean> obtenerSaldoPenionesPorFechaLetra(Integer flg, String uniNeg, Integer anio, Integer idTipoStatusCtaCte, String idEstudiante, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String nro, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerSaldoPenionesPorFechaLetra(flg, uniNeg, anio, idTipoStatusCtaCte, idEstudiante, fecha, mes, idNivel, idGrado, nro, secc);
    }

    public List<EstudianteSaldoRepBean> obtenerEstudianteConSaldoPorFechaLetra(String uniNeg, Integer anio, Integer idTipoStatusCtaCte, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerEstudianteConSaldoPorFechaLetra(uniNeg, anio, idTipoStatusCtaCte, fecha, mes, idNivel, idGrado, secc);
    }

    public Integer pagoMesAnterior(String uniNeg, Integer idCta) throws Exception {
        return cuentasPorCobrarDAO.pagoMesAnterior(uniNeg, idCta);
    }

    public List<CuentasPorCobrarBean> obtenerCuentaPorIdBecaDespues(String idEstudiante, String uniNeg, Integer anio, Integer mes) throws Exception {
        return cuentasPorCobrarDAO.obtenerCuentaPorIdBecaDespues(idEstudiante, uniNeg, anio, mes);
    }

    public List<DetResumenIngRepBean> obtenerResumenIngPorAnioPorDiaExcel(Integer dia, Integer mes, String uniNeg, Integer anio, Integer idTipoLugarPago) throws Exception {
        return cuentasPorCobrarDAO.obtenerResumenIngPorAnioPorDiaExcel(dia, mes, uniNeg, anio, idTipoLugarPago);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosAllMoras(String uniNeg, Integer idTipoLugarPago, Date fechaIni, Date fechaFin, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer periodoInicio, Integer periodoFin, List<Integer> lista, Integer recibosMora, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosAllMoras(uniNeg, idTipoLugarPago, fechaIni, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, lista, recibosMora, flgRecImp);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosDeudores(String uniNeg, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer periodoInicio, Integer periodoFin, List<Integer> lista, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosDeudores(uniNeg, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, lista, flgRecImp);
    }

    public void modificarConceptoCambioDeGrado(String uniNeg, Integer idGradoAcademico, Integer anio, String idEstudiante) throws Exception {
        cuentasPorCobrarDAO.modificarConceptoCambioDeGrado(uniNeg, idGradoAcademico, anio, idEstudiante);
    }

    public List<Integer> obtenerIdDocIngreso(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerIdDocIngreso(idEstudiante, uniNeg, anio);
    }

    public List<EstadoCtaRepBean> ObtenerInformeEstadoCtaGenRangoFecha(Integer mes, String uniNeg, Integer anio, Date fechaFin) throws Exception {
        return cuentasPorCobrarDAO.ObtenerInformeEstadoCtaGenRangoFecha(mes, uniNeg, anio, fechaFin);
    }

    public List<DetEstadoCtaRepBean> ObtenerInformeEstadoCtaGenPorMesRangoFecha(Integer mes, String uniNeg, Integer anio, Date fechaFin) throws Exception {
        return cuentasPorCobrarDAO.ObtenerInformeEstadoCtaGenPorMesRangoFecha(mes, uniNeg, anio, fechaFin);
    }

    public void cambiarEstadoCtaPorCobrarModificaciones(String uniNeg, Integer idTipoStatusCtaCte, String idEstudiante, Integer mesInicio, Integer mesFin, Integer anio, Double monto) throws Exception {
        cuentasPorCobrarDAO.cambiarEstadoCtaPorCobrarModificaciones(uniNeg, idTipoStatusCtaCte, idEstudiante, mesInicio, mesFin, anio, monto);
    }

    public List<Integer> obtenerCtasXCobrarModificaciones(String uniNeg, Integer anio, String idEstudiante, Integer mesInicio, Integer mesFin) throws Exception {
        return cuentasPorCobrarDAO.obtenerCtasXCobrarModificaciones(uniNeg, anio, idEstudiante, mesInicio, mesFin);
    }

    public void insertarModificaciones(ModificacionesBean modificacionesBean) throws Exception {
        cuentasPorCobrarDAO.insertarModificaciones(modificacionesBean);
    }

    public List<Integer> obtenerRecibosGenerados(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosGenerados(idEstudiante, uniNeg, anio);
    }

    public Double validarMonto(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return cuentasPorCobrarDAO.validarMonto(idEstudiante, uniNeg, anio);
    }

    public List<EstudianteSaldoPivotRepBean> obtenerSaldoPenionesPorFechaPivot(Integer flg, String uniNeg, Integer anio, Integer idTipoStatusCtaCte, List<String> idEstudiante, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerSaldoPenionesPorFechaPivot(flg, uniNeg, anio, idTipoStatusCtaCte, idEstudiante, fecha, mes, idNivel, idGrado, secc);
    }

    public List<EstudianteSaldoPivotRepBean> obtenerSaldoPenionesPorFechaPivotLetra(Integer flg, String uniNeg, Integer anio, Integer idTipoStatusCtaCte, List<String> idEstudiante, Date fecha, Integer mes, Integer idNivel, Integer idGrado, String secc) throws Exception {
        return cuentasPorCobrarDAO.obtenerSaldoPenionesPorFechaPivotLetra(flg, uniNeg, anio, idTipoStatusCtaCte, idEstudiante, fecha, mes, idNivel, idGrado, secc);
    }

    public List<PensionesPorCobrarLPMRepBean> obtenerPenionesPorCobrarLPM(Integer mes, String uniNeg, Integer anio,Date fechaCorte,Integer mesFin) throws Exception {
        return cuentasPorCobrarDAO.obtenerPenionesPorCobrarLPM(mes, uniNeg, anio,fechaCorte,mesFin);
    }

    public List<PensionesPorCobrarLPMRepBean> obtenerPenionesPagadasLPM(Integer mes, String uniNeg, Integer anio, Date fechaCorte,Integer mesFin) throws Exception {
        return cuentasPorCobrarDAO.obtenerPenionesPagadasLPM(mes, uniNeg, anio,fechaCorte,mesFin);
    }

    public List<CuentasPorCobrarBean> obtenerCtasXCobrarbSegunMeses(List<Integer> listaMes, String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return cuentasPorCobrarDAO.obtenerCtasXCobrarbSegunMeses(listaMes, uniNeg, anio, idEstudiante);
    }

    public List<CuentasPorCobrarBean> obtenerCtasXCobrarXMesesNoGenerado(List<Integer> listaMes, String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return cuentasPorCobrarDAO.obtenerCtasXCobrarXMesesNoGenerado(listaMes, uniNeg, anio, idEstudiante);
    }

    public void modificarCuentaFull(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception {
        cuentasPorCobrarDAO.modificarCuentaFull(cuentasPorCobrarBean);
    }

    public List<CuentasPorCobrarBean> obtenerRecibosSinServicio(String uniNeg, Integer orden, Integer idNivelAcademico, Integer idGradoAcademico, String seccion, String serie, String nroDoc, String nombre, Integer periodoInicio, Integer periodoFin, List<Integer> lista, Integer flgRecImp) throws Exception {
        return cuentasPorCobrarDAO.obtenerRecibosSinServicio(uniNeg, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, lista, flgRecImp);
    }

    public List<EstadoRecibosRepBean> obtenerEstadosRecibos(Integer opcion, String mes, String uniNeg, String anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerEstadosRecibos(opcion, mes, uniNeg, anio);
    }

    public List<EstadoRecibosRepBean> obtenerCabeceraEstadoRecibo(String anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerCabeceraEstadoRecibo(anio);
    }

    public List<VerificacionIngresoPlanillaRepBean> obtenerVerificacionPlanilla(Integer opcion, String uniNeg, String anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerVerificacionPlanilla(opcion, uniNeg, anio);
    }

    public List<VerificacionIngresoPlanillaRepBean> obtenerVerificacionPlanillaCTS(Integer opcion, String uniNeg, String anio) throws Exception {
        return cuentasPorCobrarDAO.obtenerVerificacionPlanillaCTS(opcion, uniNeg, anio);
    }

    public void cambiarEstadoCtaPorCobrarModificacionesActivosCambGr(String uniNeg, Integer idTipoStatusCtaCte, String idEstudiante, Integer mesInicio, Integer mesFin, Integer anio, Double monto) throws Exception {
        cuentasPorCobrarDAO.cambiarEstadoCtaPorCobrarModificacionesActivosCambGr(uniNeg, idTipoStatusCtaCte, idEstudiante, mesInicio, mesFin, anio, monto);
    }

    public List<ResumenMatriculaRepBean> obtenerResumenMatricula(Integer opcion, String uniNeg, String anio, Date fechaCorte) throws Exception {
        return cuentasPorCobrarDAO.obtenerResumenMatricula(opcion, uniNeg, anio, fechaCorte);
    }

    public List<ResumenMatriculaRepBean> obtenerResumenMatriculaGeneral(Integer opcion, String uniNeg, String anio, Date fechaCorte) throws Exception {
        return cuentasPorCobrarDAO.obtenerResumenMatriculaGeneral(opcion, uniNeg, anio, fechaCorte);
    }

}
