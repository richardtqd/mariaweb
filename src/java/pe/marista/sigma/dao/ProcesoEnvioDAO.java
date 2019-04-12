/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.TipoConceptoBean;

/**
 *
 * @author MS-005
 */
public interface ProcesoEnvioDAO {

    public List<ProcesoEnvioBean> obtenerProcesoEnvio() throws Exception;

    public void insertarProcesoEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void insertarEnvioMasivo(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void modificarProcesoEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void modificarStatusEnvio(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void modificarStatus(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void modificarStatusGraba(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public void eliminarProcesoEnvio(Integer idProcesoEnvio) throws Exception;

    public void obtenerEnvioPorId() throws Exception;

    public void eliminarProcesoPorBanco(Integer idProcesoBanco) throws Exception;

    //public void insertarMasivoEnvio(@Param("uniNeg") String uniNeg, @Param("idDiscente") String idDiscente, @Param("creaPor") String creaPor, @Param("registro") String registro, @Param("moneda") Integer moneda, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("mes") Integer mes) throws Exception;
    public List<ProcesoEnvioBean> obtenerEnvioPorFiltro(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerEnvioCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerEnvioCuentaRes(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<CuentasPorCobrarBean> obtenerEnvioCuentaResDes(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<ProcesoEnvioBean> obtenerPorId(Integer idProcesoEnvio) throws Exception;

    public List<ProcesoEnvioBean> obtenerPorUnineg(String uniNeg) throws Exception;

    public List<PersonaBean> buscarPersona(PersonaBean personaBean) throws Exception;

    public List<TipoConceptoBean> obtenerTipoConcepto(Integer idTipoConcepto) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentas(String uniNeg) throws Exception;

//    public List<CuentasPorCobrarBean> obtenerCuentas(@Param("mes")String mes,@Param("uniNeg")String uniNeg) throws Exception;
    public List<CuentasPorCobrarBean> buscarCuentas(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarStatusEnvioCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

//    public void modificarStatusEnvioCuentaTrueFalse(@Param("uniNeg")String uniNeg,@Param("flgEnvio")Boolean flgEnvio,@Param("idCtasXCobrar")Integer idCtasXCobrar,@Param("listaCtaFiltro")List<CuentasPorCobrarBean> listaCtaFiltro) throws Exception; 
    public void modificarStatusEnvioCuentaTrueFalse(@Param("uniNeg") String uniNeg, @Param("flgEnvio") Boolean flgEnvio, @Param("listaCtaFiltro") List<CuentasPorCobrarBean> listaCtaFiltro) throws Exception;

    public void modificarStatusCuenta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarCuentaCambio(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public List<ProcesoEnvioBean> obtenerEnvio() throws Exception;

//    public List<ProcesoEnvioBean> obtenerPorProcesoBanco(Integer idProcesoBanco) throws Exception;
    public List<ProcesoEnvioBean> obtenerPorProcesoBanco(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerDeudaConciliaCta(@Param("idProcesoRecup") Integer idProcesoRecup, @Param("idEstudiante") String idEstudiante) throws Exception;//Cambio

    public Integer obtenerDeudaEstudiante(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("idDiscente") String idDiscente) throws Exception;

    public Integer obtenerSumaImporte(@Param("uniNeg") String uniNeg, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

//    public List<CuentasPorCobrarBean> obtenerCuentaEstudiante(String idEstudiante) throws Exception;
    public List<CuentasPorCobrarBean> obtenerCuentaEstudiante(@Param("uniNeg") String uniNeg, @Param("idEstudiante") String idEstudiante) throws Exception;

    public CuentasPorCobrarBean obtenerCtaEstudiante(@Param("uniNeg") String uniNeg, @Param("idEstudiante") String idEstudiante, @Param("anio") Integer anio, @Param("mes") Integer mes) throws Exception;

    public List<CuentasPorCobrarBean> obtenerResTrans(@Param("uniNeg") String uniNeg, @Param("idCtasXCobrar") Integer idCtasXCobrar, @Param("idCtasXCobrarDes") Integer idCtasXCobrarDes) throws Exception;

    public List<CuentasPorCobrarBean> obtenerCuentaId(Integer idCtasXCobrar) throws Exception;

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception;

    public ProcesoEnvioBean obtenerPorBanco(Integer idProcesoEnvio) throws Exception;

    public Object llamarProChangeEnvioCta(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public Object insertarMasivoEnvio(@Param("uniNeg") String uniNeg, @Param("idDiscente") String idDiscente, @Param("creaPor") String creaPor, @Param("registro") String registro, @Param("moneda") Integer moneda, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("mes") Integer mes) throws Exception;

    public Float obtenerSumaEnvio(@Param("uniNeg") String uniNeg, @Param("anio") String anio, @Param("idProcesoBanco") Integer idProcesoBanco) throws Exception;

    /* ENVIO OPERACION */
    public Object execProEnvioOp(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Object execProEnvioPro(@Param("uniNeg") String uniNeg, @Param("ruc") String ruc, @Param("idProcesoBanco") Integer idProcesoBanco, @Param("anio") Integer anio, @Param("idEstudiante") String idEstudiante, @Param("codigo") String codigo, @Param("nombreCompleto") String nombreCompleto, @Param("idTipoConcepto") Integer idTipoConcepto, @Param("idConcepto") Integer idConcepto, @Param("creaPor") String creaPor, @Param("idTipoRegistro") String idTipoRegistro, @Param("estado") Integer estado, @Param("mes") Integer mes) throws Exception;

    /* PROCEDURE ENVIO BANCO */
    public Object execProEnvioProFechas(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Object execProEnvioProAnio(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    /* MODIFICAR CUOTA */
    public void modificarCuota(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    /* CREAR XLS */
    public Integer obtenerNumFilas(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Float obtenerMontoTotal(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("idTipoFile") Integer idTipoFile, @Param("flgProceso") Integer flgProceso, @Param("ruc") String ruc, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer obtenerTotalRegistros(@Param("idProcesoBanco") Integer idProcesoBanco, @Param("ruc") String ruc, @Param("uniNeg") String uniNeg) throws Exception;

    public Object execProUniNeg(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Object execProUniNegCab(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Object execProUniNegTaller(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    public Object execProTalleres(ProcesoEnvioBean procesoEnvioBean) throws Exception;

    //FILTRAR CUENTA CORRIENTE
    public List<CuentasPorCobrarBean> buscarCuentaCorriente(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

}
