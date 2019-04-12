/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PagoBancoBean;
import pe.marista.sigma.bean.reporte.ArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.bean.reporte.DetArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoTallerRepBean;
import pe.marista.sigma.bean.reporte.PagoBancoRepBean;
import pe.marista.sigma.bean.reporte.TalleresWebRepBean;

/**
 *
 * @author MS001
 */
public interface PagoBancoDAO {

    public void insertarPagoBanco(PagoBancoBean pagoBancoBean) throws Exception;

    public Integer validarCodigo(@Param("codigo") Integer codigo, @Param("uniNeg") String uniNeg);

    public Date obtenerFechaVenc(
            @Param("uniNeg") String uniNeg,
            @Param("horaCorte") String horaCorte,
            @Param("cantHoras") Integer cantHoras,
            @Param("fecha") Date fecha);

    public Integer validarFechaVencNoLaborable(
            @Param("uniNeg") String uniNeg,
            @Param("fecha") Date fecha);

    public Date obtenerNewFechaVenc(
            @Param("uniNeg") String uniNeg,
            @Param("horaCorte") String horaCorte,
            @Param("cantHoras") Integer cantHoras,
            @Param("fecha") Date fecha,
            @Param("count") Integer count);

    public List<PagoBancoRepBean> obtenerPagoBancoFor(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;
    
    public List<TalleresWebRepBean> obtenerPagoTalleresWeb(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;
    
    public List<TalleresWebRepBean> obtenerPagoTalleresWebDetalle(@Param("uniNeg") String uniNeg, @Param("list") List<Integer> ids) throws Exception;

    public List<ArqueoPagoBcoRepBean> obtenerArqueoPagoBancoPorUniNeg(@Param("uniNeg") String uniNeg, @Param("usuario") String usuario,
            @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fecha) throws Exception;

    public List<DetArqueoPagoBcoRepBean> obtenerDetArqPagoBanco(
            @Param("uniNeg") String uniNeg,
            @Param("usuario") String usuario,
            @Param("cant") String cant,
            @Param("montoTotal") String montoTotal,
            @Param("fecha") Date fecha,
            @Param("flgMonto") Integer flgMonto) throws Exception;

    //METODOS DE CONSILIACIÓN
    public List<DocIngresoTallerRepBean> obtenerConciliaPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

    public List<PagoBancoRepBean> reImprimirReciboTaller(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

    public DocIngresoTallerRepBean obtenerPorIdPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

    public void modificarPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

    public List<PagoBancoRepBean> reImprimirReciboTallerMasivo(@Param("uniNeg") String uniNeg, @Param("lista") List<Integer> lista) throws Exception;

    public List<CobranzaValoradoRepBean> reImprimirReciboTallerMasivoLibre(@Param("uniNeg") String uniNeg, @Param("lista") List<Integer> lista) throws Exception;

    public Integer obtenerMaxNroDoc(String serie) throws Exception;

    public void modificarNumRecibo(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

    public List<CobranzaValoradoRepBean> obtenerCabeceraPagoBancoLibre(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception;

//    public List<CobranzaValoradoRepBean> obtenerDetallePagoBancoLibre(CobranzaValoradoRepBean cobranzaValoradoRepBean) throws Exception;
    public List<CobranzaValoradoRepBean> obtenerDetallePagoBancoLibre(@Param("uniNeg") String uniNeg, @Param("idEstado") Integer idEstado, @Param("idDiscente") String idDiscente, @Param("flgImpresion") Boolean flgImpresion) throws Exception;

}
