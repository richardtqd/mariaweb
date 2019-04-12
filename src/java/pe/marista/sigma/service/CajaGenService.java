/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.reporte.CajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.CajaGenRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtaRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtasRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralRepBean;
import pe.marista.sigma.bean.reporte.DetCajaGenCierreRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBeanDesglosado;
import pe.marista.sigma.bean.reporte.PruebaRepBean;
import pe.marista.sigma.dao.CajaGenDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS002
 */
public class CajaGenService {

    private CajaGenDAO cajaGenDAO;

    public CajaGenDAO getCajaGenDAO() {
        return cajaGenDAO;
    }

    public void setCajaGenDAO(CajaGenDAO cajaGenDAO) {
        this.cajaGenDAO = cajaGenDAO;
    }

    public List<CajaGenRepBean> obtenerCajaGen(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.obtenerCajaGen(cajaGenBean);
    }

    public List<CajaGenRepBean> obtenerCajaGenPorCta(String uniNeg, List<Integer> idcajagen, List<Integer> idcaja) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorCta(uniNeg, idcajagen, idcaja);
    }

    public List<CajaGeneralRepBean> obtenerCajaGeneralPorCtaFor(String uniNeg, List<Integer> idcajagen, Integer flgGen) throws Exception {
        return cajaGenDAO.obtenerCajaGeneralPorCtaFor(uniNeg, idcajagen, flgGen);
    }

    public List<CajaGeneralCtaRepBean> obtenerDetCajaGeneralPorCtaFor(String uniNeg, List<Integer> idcajagen, Integer mora) throws Exception {
        return cajaGenDAO.obtenerDetCajaGeneralPorCtaFor(uniNeg, idcajagen, mora);
    }

    public List<CajaGenRepBean> obtenerCajaGenDesglosadoTop1(String uniNeg, List<Integer> idcajagen, List<Integer> idcaja) throws Exception {
        return cajaGenDAO.obtenerCajaGenDesglosadoTop1(uniNeg, idcajagen, idcaja);
    }

    public List<CajaGenRepBean> obtenerDetalleCajaGenDesglosado(String uniNeg, List<Integer> idcajagen, List<Integer> idcaja, String modo) throws Exception {
        return cajaGenDAO.obtenerDetalleCajaGenDesglosado(uniNeg, idcajagen, idcaja, modo);
    }

    public List<DocIngresoRepBeanDesglosado> obtenerCajaGenDesglosado(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCajaGenDesglosado(uniNeg, idcajagen);
    }

    public List<CajaGenRepBean> obtenerCajaGenPorCtaGen(String uniNeg, List<Integer> idcajagen, List<Integer> idcaja) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorCtaGen(uniNeg, idcajagen, idcaja);
    }

    public CajaGenBean verificarApertura(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.verificarApertura(cajaGenBean);
    }

    public CajaGenBean verificarCierre(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.verificarCierre(cajaGenBean);
    }

    public CajaGenBean verificarCierreEvento(CajaGenBean cajaGenBean) throws Exception {
        cajaGenBean.setFlgTipoCajaGen(Boolean.FALSE);
        return cajaGenDAO.verificarCierre(cajaGenBean);
    }

    public CajaGenBean verificarCierreCajaGen(Integer idCajaGen, String uniNeg) throws Exception {
        return cajaGenDAO.verificarCierreCajaGen(idCajaGen, uniNeg);
    }

    public CajaGenBean verificarRegistrosCajaDocEgre(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.verificarRegistrosCajaDocEgre(cajaGenBean);
    }

    public CajaGenBean verificarRegistrosCajaDocIng(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.verificarRegistrosCajaDocIng(cajaGenBean);
    }

    @Transactional
    public void insertarCajaGen(CajaGenBean cajaGenBean, TipoCambioBean tipoCambioBean) throws Exception {
        cajaGenDAO.insertarCajaGen(cajaGenBean);
        TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
        TipoCambioBean tipo = new TipoCambioBean();
        tipo = tipoCambioBean;
        tipo.setCreaPor(cajaGenBean.getCreaPor());
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
        String date = formato.format(tipoCambioBean.getFechaTc());
        tipo.setCreaFecha(formato.parse(date));
        tipo.setStatus(true);
        tipoCambioService.insertar(tipo);
    }

    @Transactional
    public void modificarCierre(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarCierre(cajaGenBean);
    }

    @Transactional
    public void modificarApertura(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarApertura(cajaGenBean);
    }

    @Transactional
    public void modificarIngresoSolYDol(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarIngresoSolYDol(cajaGenBean);
    }

    @Transactional
    public void modificarEgresoSolYDol(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarEgresoSolYDol(cajaGenBean);
    }

    public List<EntidadBean> obtenerBancosDeposito(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.obtenerBancosDeposito(cajaGenBean);
    }

    @Transactional
    public void modificarDepColegio(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarDepSoles(cajaGenBean);
        cajaGenDAO.modificarDepDolares(cajaGenBean);
        cajaGenDAO.modificarDepCongreSoles(cajaGenBean);
        cajaGenDAO.modificarDepCongreDolares(cajaGenBean);
    }

    @Transactional
    public void modificarDepSoles(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarDepSoles(cajaGenBean);
    }

    @Transactional
    public void modificarDepDolares(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarDepDolares(cajaGenBean);
    }

    //CONGREGACION
    @Transactional
    public void modificarDepCongreSoles(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarDepCongreSoles(cajaGenBean);
    }

    @Transactional
    public void modificarDepCongreDolares(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarDepCongreDolares(cajaGenBean);
    }

    public CajaGenBean obtenerPorId(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.obtenerPorId(cajaGenBean);
    }

    public CajaGenBean obtenerUltimaCajaAbierta(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.obtenerUltimaCajaAbierta(cajaGenBean);
    }

    public CajaGenBean obtenerUltimaCajaAbiertaApertura(CajaGenBean cajaGenBean) throws Exception {
        cajaGenBean.setFlgTipoCajaGen(Boolean.TRUE);
        return cajaGenDAO.obtenerUltimaCajaAbiertaApertura(cajaGenBean);
    }

    public CajaGenBean obtenerCajaDepositoDiaAnterior(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.obtenerCajaDepositoDiaAnterior(cajaGenBean);
    }

    public List<PruebaRepBean> consulta() throws Exception {
        return cajaGenDAO.consulta();
    }

    public List<CajaGenBean> obtenerAperturasCajaPorCajero(String usuario, String uniNeg, Integer flgTipoCajaGen) throws Exception {
        return cajaGenDAO.obtenerAperturasCajaPorCajero(usuario, uniNeg, flgTipoCajaGen);
    }

    public List<CajaGenBean> obtenerCierresCajaPorCajero(@Param("usuario") String usuario, @Param("uniNeg") String uniNeg, @Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin) throws Exception {
        return cajaGenDAO.obtenerCierresCajaPorCajero(usuario, uniNeg, fechaInicio, fechaFin);
    }

    public List<CajaGenCierreRepBean> obtenerCierresCajaPorFechaTop1(String uniNeg, String fechaInicio, String fechaFin) throws Exception {
        return cajaGenDAO.obtenerCierresCajaPorFechaTop1(uniNeg, fechaInicio, fechaFin);
    }

    public List<CajaGenCierreRepBean> obtenerCierresCajaPorFecha(String uniNeg, String fechaInicio, String fechaFin) throws Exception {
        return cajaGenDAO.obtenerCierresCajaPorFecha(uniNeg, fechaInicio, fechaFin);
    }

    public List<DetCajaGenCierreRepBean> obtenerCierresCajaPorFechaUsu(String uniNeg, String fechaInicio, String fechaFin, String usuario, Integer idCaja) throws Exception {
        return cajaGenDAO.obtenerCierresCajaPorFechaUsu(uniNeg, fechaInicio, fechaFin, usuario, idCaja);
    }

    public List<CajaGenRepBean> obtenerCajaGenPorDetalleFor(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorDetalleFor(uniNeg, idcajagen);
    }

    public List<CajaGenRepBean> obtenerCajaGeneralPorDetalleFor(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCajaGeneralPorDetalleFor(uniNeg, idcajagen);
    }

    public List<CajaGenRepBean> obtenerCajaGeneralPorDetalleForIncMora(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCajaGeneralPorDetalleForIncMora(uniNeg, idcajagen);
    }

    //rep cuenta
    public List<CajaGeneralCtasRepBean> obtenerCuentasCajaGeneralFor(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCuentasCajaGeneralFor(uniNeg, idcajagen);
    }

    public List<CajaGeneralCtaRepBean> obtenerDetallePorCtaFor(String uniNeg, List<Integer> idcajagen, Integer cta) throws Exception {
        return cajaGenDAO.obtenerDetallePorCtaFor(uniNeg, idcajagen, cta);
    }

    //MODIFICAR CAJA GEN EVENTO
    @Transactional
    public void modificarIngresoSolYDolEvento(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarIngresoSolYDolEvento(cajaGenBean);
    }

    public CajaGenBean verificarAperturaCajaEvento(CajaGenBean cajaGenBean) throws Exception {
        return cajaGenDAO.verificarAperturaCajaEvento(cajaGenBean);
    }

    //HISTORIAL CAJA GEN EVENTO
    public List<CajaGenBean> obtenerCierresCajaPorCajeroEvento(String usuario, String uniNeg, Date fechaInicio, Date fechaFin) throws Exception {
        return cajaGenDAO.obtenerCierresCajaPorCajeroEvento(usuario, uniNeg, fechaInicio, fechaFin);
    }

    //MODIFICAR CAJA GEN
    @Transactional
    public void modificarCierreEvento(CajaGenBean cajaGenBean) throws Exception {
        cajaGenDAO.modificarCierreEvento(cajaGenBean);
    }

    //REPORTE DE ARQUEO DE EVENTOS
    public List<CajaGenRepBean> obtenerCajaGenPorDetalleForEvento(String uniNeg, List<Integer> idcajagen) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorDetalleForEvento(uniNeg, idcajagen);
    }

    //REPORTE DE CAJA GEN TALLER 
    public List<CajaGenRepBean> obtenerCajaGenPorDetalleForTaller(String uniNeg, Date fecIni, Date fecFin) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorDetalleForTaller(uniNeg, fecIni, fecFin);
    }

    //REPORTE DE CAJA GEN TALLER CUENTA 
    public List<CajaGeneralRepBean> obtenerCajaGenPorDetalleCtaForTaller(String uniNeg, Date fecIni, Date fecFin) throws Exception {
        return cajaGenDAO.obtenerCajaGenPorDetalleCtaForTaller(uniNeg, fecIni, fecFin);
    }

    //REPORTE POR CUENTAS
    public List<CajaGeneralCtasRepBean> obtenerCuentasCajaGeneralForTaller(String uniNeg, Date fecIni, Date fecFin) throws Exception {
        return cajaGenDAO.obtenerCuentasCajaGeneralForTaller(uniNeg, fecIni, fecFin);
    }

    //REPORTE DETALLADO POR CUENTAS
    public List<CajaGeneralCtaRepBean> obtenerDetallePorCtaForTaller(String uniNeg, Date fecIni, Date fecFin, Integer cuenta) throws Exception {
        return cajaGenDAO.obtenerDetallePorCtaForTaller(uniNeg, fecIni, fecFin, cuenta);
    }

    public void actualizarCajaGenAuto(Integer idCajaGen, String uniNeg) throws Exception {
        cajaGenDAO.actualizarCajaGenAuto(idCajaGen, uniNeg);
    }    
    
}
