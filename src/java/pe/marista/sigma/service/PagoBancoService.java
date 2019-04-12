/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.PagoBancoBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.reporte.ArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.bean.reporte.DetArqueoPagoBcoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoTallerRepBean;
import pe.marista.sigma.bean.reporte.PagoBancoRepBean;
import pe.marista.sigma.bean.reporte.TalleresWebRepBean;
import pe.marista.sigma.dao.DocIngresoDAO;
import pe.marista.sigma.dao.PagoBancoDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS001
 */
public class PagoBancoService {

    private PagoBancoDAO pagoBancoDAO;

    @Transactional
    public void insertarPagoBancoNewCodigo(PagoBancoBean pagoBancoBean, Integer estadoCupos) throws Exception {
        ProgramacionService programacionService = BeanFactory.getProgramacionService();
        ImpresoraService impresoraService = BeanFactory.getImpresoraService();
        ImpresoraBean impresora = new ImpresoraBean();
        Integer actual = null;
        impresora = impresoraService.obtenerPorNombre(pagoBancoBean.getUnidadNegocioBean().getUniNeg(), MaristaConstantes.IMPRESORA_WEB);
        actual = impresora.getActual();
        Integer estado = validarCodigo(actual, pagoBancoBean.getUnidadNegocioBean().getUniNeg());
        if (estado.equals(0)) {
            pagoBancoBean.setSerie(impresora.getSerie());
            pagoBancoBean.setNroDoc(impresora.getActual().toString());
            pagoBancoBean.setCodigo(impresora.getActual());
            pagoBancoDAO.insertarPagoBanco(pagoBancoBean);
            System.out.println("3: " + pagoBancoBean.getCodigo());
            impresora.setActual(actual + 1);
            impresora.setUnidadNegocioBean(pagoBancoBean.getUnidadNegocioBean());
            DocIngresoDAO docIngresoDAO = BeanFactory.getDocIngresoDAO();
            docIngresoDAO.modificarImpresoraActual(impresora);
            if (estadoCupos.equals(1)) {
                programacionService.modificarProgramacionCupos(pagoBancoBean.getProgramacionBean());
            }
        } else {
            insertarPagoBancoNewCodigo(pagoBancoBean, 1);
        }
    }

    @Transactional
    public void insertarPagoBanco(PagoBancoBean pagoBancoBean) throws Exception {
        pagoBancoDAO.insertarPagoBanco(pagoBancoBean);
    }

    public Integer validarCodigo(Integer codigo, String uniNeg) {
        return pagoBancoDAO.validarCodigo(codigo, uniNeg);
    }

    @Transactional
    public void insertarDocIngresoPagoBanco(PagoBancoBean pagoBancoBean, List<PagoBancoBean> listaDetPagoBanco,
            List<ProgramacionBean> listaProgramacionSessionBean, Boolean flgInsPer, PersonaBean personaBean) throws Exception {
        CodigoService codigoService = BeanFactory.getCodigoService();
        ProgramacionService programacionService = BeanFactory.getProgramacionService();
        List<Integer> lista = new ArrayList<>();
        Integer estadoCupos = 0;
        if (listaProgramacionSessionBean != null) {
            if (!listaProgramacionSessionBean.isEmpty()) {
                for (ProgramacionBean prog : listaProgramacionSessionBean) {
                    lista.add(prog.getIdProgramacion());
                }
            }
        }
        if (lista.size() > 0) {
            System.out.println(">0");
            estadoCupos = programacionService.obtenerCuposPrograPorIdFor(pagoBancoBean.getUnidadNegocioBean().getUniNeg(), lista);
        }
        //si hay cupos
        if (estadoCupos.equals(1)) {

            CodigoBean codigoStatusPagoBanco = new CodigoBean();
            codigoStatusPagoBanco = codigoService.obtenerPorCodigo(new CodigoBean(0, "Emitido", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_PAGO_BCO)));
            pagoBancoBean.setTipoStatusPagoBanco(codigoStatusPagoBanco);

            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            ImpresoraBean impresora = new ImpresoraBean();
            impresora = impresoraService.obtenerPorNombre(pagoBancoBean.getUnidadNegocioBean().getUniNeg(), MaristaConstantes.IMPRESORA_WEB);
            if (impresora != null) {
                if (flgInsPer.equals(Boolean.TRUE)) {
                    PersonaService personaService = BeanFactory.getPersonaService();
                    List<PersonaBean> listaPersonaBean = personaService.obtenerPersonaPorFiltro(personaBean);
                    if (listaPersonaBean.size() > 0) {
                        System.out.println("ya existe");
                    } else {
                        System.out.println("no existe");
                        String cod = null;
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                        String date = formato.format(new Date());
                        Integer anio = new Integer(date);
                        cod = personaService.generarCodigoPersona(anio, pagoBancoBean.getUnidadNegocioBean().getUniNeg());
                        System.out.println("cod: " + cod);
                        personaBean.setIdPersona(cod);
                        personaBean.setCreaPor(pagoBancoBean.getCreaPor());
                        personaBean.setCreaFecha(new Date());
                        personaBean.setUnidadNegocioBean(pagoBancoBean.getUnidadNegocioBean());
                        personaService.insertarPersona(personaBean);
                        pagoBancoBean.setIdDiscente(cod);
                    }
                }

                for (PagoBancoBean detalle : listaDetPagoBanco) {
                    detalle.setUnidadNegocioBean(pagoBancoBean.getUnidadNegocioBean());
                    detalle.getIdTipoServicioIngBean().setIdCodigo(3);
                    detalle.setIdTipoDoc(pagoBancoBean.getIdTipoDoc());
                    detalle.setImpresoraBean(impresora);
                    detalle.setIdDiscente(pagoBancoBean.getIdDiscente());
                    detalle.setDiscente(pagoBancoBean.getDiscente());
                    detalle.setAnio(pagoBancoBean.getAnio());
                    detalle.setObs(pagoBancoBean.getObs());
                    detalle.setIdTipoLugarPago(pagoBancoBean.getIdTipoLugarPago());
                    detalle.setIdTipoModoPago(pagoBancoBean.getIdTipoModoPago());
                    detalle.setFechaPago(new Date());
                    detalle.setIdTipoMoneda(pagoBancoBean.getIdTipoMoneda());

                    if (pagoBancoBean.getTipoStatusPagoBanco() != null) {
                        detalle.setTipoStatusPagoBanco(pagoBancoBean.getTipoStatusPagoBanco());
                    }
                    Date fechaVenc = null;
                    fechaVenc = obtenerFechaVencimiento(pagoBancoBean.getUnidadNegocioBean().getUniNeg(), "13:00:00", 0, new Date());
                    System.out.println("fcv: " + fechaVenc);
                    detalle.setFechaVenc(fechaVenc);
                    detalle.getCuentaBancoBean().setNumCuenta("9999");
                    detalle.setCantidad(1);
                    if (detalle.getIdTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                        detalle.setMontoSoles(detalle.getMontoPagado());
                        detalle.setMontoDolares(new BigDecimal(0));
                    }
                    if (detalle.getIdTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                        detalle.setMontoSoles(new BigDecimal(0));
                        detalle.setMontoDolares(detalle.getMontoPagado());
                    }

                    Integer actual = null;
                    impresora = impresoraService.obtenerPorNombre(pagoBancoBean.getUnidadNegocioBean().getUniNeg(), MaristaConstantes.IMPRESORA_WEB);
                    actual = impresora.getActual();
                    System.out.println("actual..." + actual);
                    Integer estado = validarCodigo(actual, pagoBancoBean.getUnidadNegocioBean().getUniNeg());
                    System.out.println("estado " + estado);
//                    if (estado.equals(0)) {
                        detalle.setSerie(impresora.getSerie());
                        detalle.setNroDoc(impresora.getActual().toString());
                        detalle.setCodigo(impresora.getActual());
                        pagoBancoDAO.insertarPagoBanco(detalle);
                        System.out.println("id. pago bco.. " + detalle.getIdPagoBanco());
                        System.out.println("3: " + detalle.getCodigo());
                        impresora.setActual(actual + 1);
                        impresora.setUnidadNegocioBean(pagoBancoBean.getUnidadNegocioBean());

                        programacionService.modificarProgramacionCupos(detalle.getProgramacionBean());
//                    } else {
//                        insertarPagoBancoNewCodigo(detalle, 1);
//                    }
                }
                DocIngresoDAO docIngresoDAO = BeanFactory.getDocIngresoDAO();
                docIngresoDAO.modificarImpresoraActual(impresora);
//                System.out.println("actual new" + impresora.getActual());
                System.out.println("-------------FIN--------------------");
                pagoBancoBean.setEstadoRegIng(1);
            } else {
                pagoBancoBean.setEstadoRegIng(0);
            }
        } else {
            pagoBancoBean.setEstadoRegIng(3);
        }
        System.out.println("end-->" + pagoBancoBean.getEstadoRegIng());
    }

    public Date obtenerFechaVencimiento(String uniNeg, String horaCorte, Integer cantHoras, Date fecha) {
        System.out.println("obtenerFechaVencimiento");
        Date fechaV = null;
        Integer flgFechaNoLab = 0;
        Integer count = 0;
        //1.-obtengo la fecha de vecimiento nivel 1
        fechaV = pagoBancoDAO.obtenerFechaVenc(uniNeg, horaCorte, cantHoras, fecha);
        System.out.println("fechaV " + fechaV);
        //2.-valido se la fecha de venc es no laborable
        if (fechaV != null) {
            System.out.println("validarFechaVencNoLaborable");
            flgFechaNoLab = pagoBancoDAO.validarFechaVencNoLaborable(uniNeg, fechaV);
            System.out.println("flgFechaNoLab " + flgFechaNoLab);
            if (flgFechaNoLab.equals(1)) { //si es no lab
                //3.- new fecha de venc 
                fechaV = obtenerNewFechaVenc(uniNeg, horaCorte, cantHoras, fechaV, count);
            } else {//dia lab
//             fechaV=fechaV; 
            }
        }
        return fechaV;
    }

    public Date obtenerNewFechaVenc(String uniNeg, String horaCorte, Integer cantHoras, Date fecha, Integer count) {
        System.out.println("obtenerNewFechaVenc");
        Date newFechaV = null;
        Integer flgFechaNoLab = 0;
//        count = 0;
        newFechaV = pagoBancoDAO.obtenerNewFechaVenc(uniNeg, horaCorte, cantHoras, fecha, count);
        System.out.println("newFechaV " + newFechaV);
        flgFechaNoLab = pagoBancoDAO.validarFechaVencNoLaborable(uniNeg, newFechaV);
        System.out.println("flgFechaNoLabAAA " + flgFechaNoLab);
        if (flgFechaNoLab.equals(1)) {
            count = count + 1;
            newFechaV = obtenerNewFechaVenc(uniNeg, horaCorte, cantHoras, fecha, count);
        }
        return newFechaV;
    }

    public List<ArqueoPagoBcoRepBean> obtenerArqueoPagoBancoPorUniNeg(String uniNeg, String usuario, Date fechaInicio, Date fechaFin) throws Exception {
        return pagoBancoDAO.obtenerArqueoPagoBancoPorUniNeg(uniNeg, usuario, fechaInicio, fechaFin);
    }

    public List<DetArqueoPagoBcoRepBean> obtenerDetArqPagoBanco(String uniNeg, String usuario, String cant, String montoTotal, Date fecha, Integer flgMonto) throws Exception {
        return pagoBancoDAO.obtenerDetArqPagoBanco(uniNeg, usuario, cant, montoTotal, fecha, flgMonto);
    }

    public List<PagoBancoRepBean> reImprimirReciboTaller(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        return pagoBancoDAO.reImprimirReciboTaller(docIngresoTallerRepBean);
    }

//
//    public Date obtenerFechaVenc(String uniNeg, String horaCorte, Integer cantHoras, Date fecha) {
//        return pagoBancoDAO.obtenerFechaVenc(uniNeg, horaCorte, cantHoras, fecha);
//    }
//
//    public Integer validarFechaVencNoLaborable(String uniNeg, Date fecha) {
//        return pagoBancoDAO.validarFechaVencNoLaborable(uniNeg, fecha);
//    }
    public List<PagoBancoRepBean> obtenerPagoBancoFor(String uniNeg, List<Integer> ids) throws Exception {
        return pagoBancoDAO.obtenerPagoBancoFor(uniNeg, ids);
    }

    public List<DocIngresoTallerRepBean> obtenerConciliaPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        return pagoBancoDAO.obtenerConciliaPagoBanco(docIngresoTallerRepBean);
    }

    public DocIngresoTallerRepBean obtenerPorIdPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        return pagoBancoDAO.obtenerPorIdPagoBanco(docIngresoTallerRepBean);
    }

    @Transactional
    public void modificarPagoBanco(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        pagoBancoDAO.modificarPagoBanco(docIngresoTallerRepBean);
    }

    public List<PagoBancoRepBean> reImprimirReciboTallerMasivo(String uniNeg, List<Integer> lista) throws Exception {
        return pagoBancoDAO.reImprimirReciboTallerMasivo(uniNeg, lista);
    }

    public List<CobranzaValoradoRepBean> reImprimirReciboTallerMasivoLibre(String uniNeg, List<Integer> lista) throws Exception {
        return pagoBancoDAO.reImprimirReciboTallerMasivoLibre(uniNeg, lista);
    }

    public Integer obtenerMaxNroDoc(String serie) throws Exception {
        return pagoBancoDAO.obtenerMaxNroDoc(serie);
    }

    @Transactional
    public void modificarNumRecibo(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        pagoBancoDAO.modificarNumRecibo(docIngresoTallerRepBean);
    }

    public List<CobranzaValoradoRepBean> obtenerCabeceraPagoBancoLibre(DocIngresoTallerRepBean docIngresoTallerRepBean) throws Exception {
        return pagoBancoDAO.obtenerCabeceraPagoBancoLibre(docIngresoTallerRepBean);
    }

    public List<CobranzaValoradoRepBean> obtenerDetallePagoBancoLibre(String uniNeg, Integer idEstado, String idDiscente, Boolean flgImpresion) throws Exception {
        return pagoBancoDAO.obtenerDetallePagoBancoLibre(uniNeg, idEstado, idDiscente, flgImpresion);
    }

    //set and get
    public PagoBancoDAO getPagoBancoDAO() {
        return pagoBancoDAO;
    }

    public void setPagoBancoDAO(PagoBancoDAO pagoBancoDAO) {
        this.pagoBancoDAO = pagoBancoDAO;
    }

    public List<TalleresWebRepBean> obtenerPagoTalleresWeb(String uniNeg, List<Integer> ids) throws Exception {
        return pagoBancoDAO.obtenerPagoTalleresWeb(uniNeg, ids);
    }

    public List<TalleresWebRepBean> obtenerPagoTalleresWebDetalle(String uniNeg, List<Integer> ids) throws Exception {
        return pagoBancoDAO.obtenerPagoTalleresWebDetalle(uniNeg, ids);
    }

}
