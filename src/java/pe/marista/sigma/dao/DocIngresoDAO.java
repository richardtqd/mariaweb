/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DescuentoTallerBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoDiscenteBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoABRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.bean.reporte.ReciboPensionRepBean;

/**
 *
 * @author MS002
 */
public interface DocIngresoDAO {

    public List<CuentasPorCobrarBean> obtenerCuentaPorMatricula(String idPersona) throws Exception; 

    public List<PersonaBean> buscarPersona(PersonaBean personaBean) throws Exception;

    public List<ConceptoUniNegBean> obtenerConcepto(PersonaBean personaBean) throws Exception;

    public TipoDiscenteBean obtenerTipoDiscente(@Param("idPersona") String idPersona, @Param("anio") Integer anio, @Param("uniNeg") String uniNeg) throws Exception;

    public PersonaBean buscarPersonaPostulante(@Param("parms") Map<String, Object> parms) throws Exception;

    public PersonaBean buscarPersonaInscPostulante(@Param("parms") Map<String, Object> parms) throws Exception;

    public PersonaBean buscarPersonaMatriculado(@Param("parms") Map<String, Object> parms) throws Exception;

    public PersonaBean buscarPersonaCurVacacional(@Param("parms") Map<String, Object> parms) throws Exception;

    public List<ImpresoraBean> obtenerImpresoraCajero(CajeroCajaBean cajeroCajaBean) throws Exception;

    public List<ImpresoraBean> obtenerImpresoraCajeroPensiones(CajeroCajaBean cajeroCajaBean) throws Exception;

    public List<ImpresoraBean> obtenerImpresoraCajeroDocEgreso(CajeroCajaBean cajeroCajaBean) throws Exception;

    public List<CodigoBean> obtenerTipDocumentoPorImpresora(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idCaja") Integer idCaja) throws Exception;

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraPensiones(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idCaja") Integer idCaja, @Param("tipoDoc") String tipoDoc) throws Exception;

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraComprobante(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idCaja") Integer idCaja, @Param("tipoDoc") String tipoDoc) throws Exception;

    public ImpresoraCajaBean obtenerDetalleTipoDoc(@Param("impresora") String impresora, @Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("idTipoDoc") Integer idTipoDoc, @Param("idCaja") Integer idCaja) throws Exception;

    public void insertarDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public void insertarDocIngresoDetalle(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public void anularDetDocIngresoPorIdDocIng(DocIngresoBean docIngresoBean) throws Exception;

    public DetDocIngresoBean obtenerDetDocIngresoBeanPorId(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public void modificarPagoCtasXCobrar(CuentasPorCobrarBean cuentasPorCobrarBean) throws Exception;

    public void modificarImpresoraActual(ImpresoraBean impresoraBean) throws Exception;

    public CajaGenBean obtenerCajaGeneral(CajaBean cajaBean) throws Exception;

    public List<DocIngresoBean> obtenerFiltroDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public DocIngresoBean obtenerDocIngresoPorId(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public DocIngresoBean obtenerDocIngresoPorIdFull(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public DocIngresoBean obtenerDocIngresoCuotaPorDiscente(@Param("idDiscente") String idDiscente, @Param("uniNeg") String uniNeg, @Param("concepto") String concepto) throws Exception;

    ///////////////////Reportes////////////////////////////////////////////////// 
    public List<DocIngresoRepBean> obtenerDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public List<DocIngresoRepBean> obtenerFormatoDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public List<DocIngresoRepBean> obtenerFormatoDocIngresoFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<DetDocIngresoRepBean> obtenerDetalleDocIngreso(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetDocIngresoBean> reporteDelDia(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public List<DetDocIngresoRepBean> obtenerFiltroDetDocIngreso(DetDocIngresoRepBean detDocIngresoRepBean) throws Exception;

    public List<DetDocIngresoRepBean> obtenerFiltroDetPagoMatricula(DetDocIngresoRepBean detDocIngresoRepBean) throws Exception;

    //nuevo formato
    public List<DetDocIngresoRepBean> obtenerFormatoDetalleDocIngreso(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public List<DetDocIngresoRepBean> obtenerFormatoDetalleDocIngresoConDscto(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg, @Param("flgDscto") Integer flgDscto) throws Exception;

    public void cambiarEstadoDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public void cambiarDatBasicosDocIngreso(DocIngresoBean docIngresoBean) throws Exception;

    public void cambiarDatBasicosDocIngresoBecaTotal(DocIngresoBean docIngresoBean) throws Exception;

    public void cambiarDatBasicosDocIngresoBeca(DocIngresoBean docIngresoBean) throws Exception;

    public void cambiarDatBasicosDocIngresoBecaToCambio(DocIngresoBean docIngresoBean) throws Exception;

    public void cambiarGradoAcademicoEnDocIng(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarDocIngFull(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarDetalleDocIngFull(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public void modificarDetalleDocIngDes(@Param("idCtasxCobrar") Integer idCtasxCobrar, @Param("uniNeg") String uniNeg, @Param("dsctoBeca") BigDecimal dsctoBeca) throws Exception;

    public void modificarDetalleDocIngDesBecaTotal(@Param("idCtasxCobrar") Integer idCtasxCobrar, @Param("uniNeg") String uniNeg, @Param("dsctoBeca") BigDecimal dsctoBeca) throws Exception;

    public void modificarDetalleDocIngDesCambioBeca(@Param("idCtasxCobrar") Integer idCtasxCobrar, @Param("uniNeg") String uniNeg, @Param("dsctoBeca") BigDecimal dsctoBeca, @Param("idDocIngreso") Integer idDocIngreso) throws Exception;

    //
    public DocIngresoBean validarSerieNroDoc(@Param("serie") String serie, @Param("nroDoc") String nroDoc,
            @Param("impresora") String impresora, @Param("uniNeg") String uniNeg) throws Exception;

    public void modificarDocIngresoPension(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarDetDocIngresoPension(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public void modificarDetDocIngresoPensionAnulacion(DocIngresoBean docIngresoBean) throws Exception;

    public Integer obtenerDocIngresoPorCtaCobrar(@Param("idCtaXCobrar") Integer idCtaXCobrar, @Param("uniNeg") String uniNeg) throws Exception;
//masivo
//    public List<DocIngresoBean> obtenerDocIngreso(@Param("uniNeg") String uniNeg, @Param("idDiscente") String idDiscente,@Param("anio") Integer anio,@Param("mes") Integer mes) throws Exception;
    //formato de recibo cuando no esta generado

    public List<DocIngresoRepBean> obtenerRecDocIngresoFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<DocIngresoRepBean> obtenerRecDocIngresoForSinOrderBy(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids, @Param("orden") Integer orden) throws Exception;
    
    public List<DocIngresoRepBean> obtenerRecDocIngresoForSinOrderByUnico(@Param("uniNeg") String uniNeg, @Param("ids") Integer ids, @Param("orden") Integer orden) throws Exception;

    public List<DocIngresoABRepBean> obtenerRecDocIngresoABFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<DocIngresoRepBean> obtenerRecDocIngresoForForSimple(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public Integer obtenerFormatoDocIngresoPension(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngreso(@Param("uniNeg") String uniNeg, @Param("idDocIngreso") Integer idDocIngreso, @Param("mora") Integer mora, @Param("dscto") Integer dscto, @Param("beca") Integer beca) throws Exception;

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMora(@Param("uniNeg") String uniNeg, @Param("idDocIngreso") Integer idDocIngreso, @Param("mora") Integer mora, @Param("dscto") Integer dscto, @Param("beca") Integer beca) throws Exception;

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMas(@Param("uniNeg") String uniNeg, @Param("idDocIngreso") Integer idDocIngreso, @Param("mora") Integer mora, @Param("dscto") Integer dscto, @Param("beca") Integer beca, @Param("infoMonto") Integer infoMonto) throws Exception;

    public List<ReciboPensionRepBean> obtenerReporteMasivo(@Param("listaEstudiantes") List<String> listaEstudiantes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("flgMasivo") String flgMasivo, @Param("flgMas") Integer flgMas) throws Exception;

    public List<ReciboPensionRepBean> obtenerReporteMasivoIngreso(@Param("listaEstudiantes") List<String> listaEstudiantes, @Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("flgMasivo") String flgMasivo, @Param("flgMas") Integer flgMas) throws Exception;

    public List<DocIngresoRepBean> obtenerReporteMasivoIng(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("flgMasivo") String flgMasivo, @Param("flgMas") Integer flgMas, @Param("matriculaBean") MatriculaBean matriculaBean) throws Exception;

    //Reporte Banco
    public List<DocIngresoBean> obtenerFiltroDocIngresoBanco(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarEstadoImpreso(DocIngresoBean docIngresoBean) throws Exception;

    public Integer obtenerNumAnulados(DocIngresoBean docIngresoBean) throws Exception;

    public Object eliminarRecibo(@Param("uniNeg") String uniNeg, @Param("nroDocIni") Integer nroDocIni, @Param("nroDocFin") Integer nroDocFin, @Param("nroRecIni") Integer nroRecIni, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("creaPor") String creaPor) throws Exception;

    public List<DocIngresoBean> obtenerRecibos(MatriculaBean matriculaBean) throws Exception;

    //Reporte Masivo
    public void actualizarRecibosMasivos(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio, @Param("mes") Integer mes, @Param("flgMasivo") String flgMasivo, @Param("flgMas") Integer flgMas, @Param("matriculaBean") MatriculaBean matriculaBean) throws Exception;

    public Integer obtenerMaxNroDoc(DocIngresoBean docIngresoBean) throws Exception;

    public Integer obtenerMaxNroDocSerie(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarNroDocSerie(DocIngresoBean docIngresoBean) throws Exception;

    public List<DetDocIngresoBean> obtenerDetoDocId(@Param("uniNeg") String uniNeg, @Param("idCtasXCobrar") Integer idCtasXCobrar) throws Exception;

    //Para Actualizar el descueto de beca de detDocIngreso
    public List<DetDocIngresoBean> obtenerDetoDocIdNew(@Param("uniNeg") String uniNeg, @Param("idCtasXCobrar") Integer idCtasXCobrar) throws Exception;

    public List<DocIngresoBean> obtenerFiltroDocIngresoMasivo(DocIngresoBean docIngresoBean) throws Exception;

    public List<DocIngresoRepBean> obtenerRecDocIngresoForMasivo(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<DetDocIngresoRepBean> obtenerRecDocIngresoForMasivo(@Param("uniNeg") String uniNeg, @Param("idDocIngreso") Integer idDocIngreso, @Param("mora") Integer mora, @Param("dscto") Integer dscto, @Param("beca") Integer beca, @Param("infoMonto") Integer infoMonto) throws Exception;

    public List<DetDocIngresoRepBean> obtenerRecDetDocIngresoMasivo(@Param("uniNeg") String uniNeg, @Param("idDocIngreso") Integer idDocIngreso, @Param("mora") Integer mora, @Param("dscto") Integer dscto, @Param("beca") Integer beca, @Param("infoMonto") Integer infoMonto) throws Exception;

    //nuevos
    public DetDocIngresoBean obtenerDetDocIngPorDocIngreso(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public Integer cantDetPorIdDocIngreso(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg) throws Exception;

    public void cambiarFechaImpresion(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> lista) throws Exception;

    public void cambiarDsctoBeca(@Param("uniNeg") String uniNeg, @Param("dsctoBeca") BigDecimal dsctoBeca, @Param("idDocIngreso") Integer idDocIngreso) throws Exception;

    public void ponerNroReciboMora(@Param("uniNeg") String uniNeg, @Param("idRecibosMora") Integer idRecibosMora, @Param("idDocIngreso") Integer idDocIngreso) throws Exception;
    
    public void modificarRespPagDocIng(@Param("idRespPago") String idRespPago,@Param("nomResPago") String nomResPago, @Param("idDiscente") String idDiscente,@Param("uniNeg") String uniNeg, 
            @Param("anio") Integer anio, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin, @Param("idGradoAcademico") Integer idGradoAcademico) throws Exception;

    public void modificarDocIngresoRetificacion(DocIngresoBean docIngresoBean) throws Exception;

    public void modificarDetDocIngresoRetificacion(DetDocIngresoBean detDocIngresoBean) throws Exception;

    public List<DocIngresoBean> obtenerDocIngresosSinNroDoc(@Param("uniNeg") String uniNeg, @Param("anio") Integer anio) throws Exception;

    public void ponerNroDoc(DocIngresoBean docIngresoBean) throws Exception;

    public List<DescuentoTallerBean> obtenerDescuentosHabilitados(DescuentoTallerBean descuentoTallerBean) throws Exception;

    public void modificarConceptoCambioGrado(@Param("idDocIngreso") Integer idDocIngreso, @Param("uniNeg") String uniNeg, @Param("idGradoAcademico") Integer idGradoAcademico) throws Exception;
    
    //Modificaciones
    public void modificarMontosDocIng(@Param("uniNeg") String uniNeg, @Param("idEstudiante") String idEstudiante, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin, @Param("anio") Integer anio, @Param("monto") Double monto, @Param("idGradoAcademico") Integer idGradoAcademico) throws Exception;
    
    public void modificarMontosDocIngActivosCambioGrado(@Param("uniNeg") String uniNeg, @Param("idEstudiante") String idEstudiante, @Param("mesInicio") Integer mesInicio, @Param("mesFin") Integer mesFin, @Param("anio") Integer anio, @Param("monto") Double monto,@Param("idGradoAcademico")  Integer idGradoAcademico) throws Exception;
    
    //CambiarCaja modificar
    public DocIngresoBean obtenerMontosAntiguaCajaGen(@Param("idCajaGen") Integer idCajaGen) throws Exception;
    
    //Talleres web
    
    public List<DocIngresoBean> obtenerTalleresWeb(
            @Param("uniNeg") String uniNeg,
            @Param("anio") Integer anio, 
            @Param("orden") Integer orden, 
            @Param("serie") String serie,
            @Param("nroDoc") String nroDoc,
            @Param("nombre") String nombre, 
            @Param("flgRecImp") Integer flgRecImp
    ) throws Exception;
}
