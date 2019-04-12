package pe.marista.sigma.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CajaChicaLiquidacionBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.LiquidacionRepBean;
import pe.marista.sigma.dao.AsientoDAO;
import pe.marista.sigma.dao.CajaChicaLiquidacionDAO;
import pe.marista.sigma.dao.CajaChicaMovDAO;
import pe.marista.sigma.dao.ConceptoDAO;
import pe.marista.sigma.dao.DocEgresoDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.GLTCalculadoraCR;

/**
 *
 * @author Administrador
 */
public class CajaChicaLiquidacionService {

    private CajaChicaLiquidacionDAO cajaChicaLiquidacionDAO;
    private SolicitudCajaCHDAO solicitudCajaCHDAO;
    private CajaChicaMovDAO cajaChicaMovDAO;

    public CajaChicaLiquidacionDAO getCajaChicaLiquidacionDAO() {
        return cajaChicaLiquidacionDAO;
    }

    public void setCajaChicaLiquidacionDAO(CajaChicaLiquidacionDAO cajaChicaLiquidacionDAO) {
        this.cajaChicaLiquidacionDAO = cajaChicaLiquidacionDAO;
    }

    @Transactional
    public void insertarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean, List<CentroResponsabilidadBean> listaCR, Double tc) throws Exception {
        AsientoService asientoService = BeanFactory.getAsientoService();
        ConceptoService conceptoService = BeanFactory.getConceptoService();
        ConceptoBean conceptoBean = new ConceptoBean();
        AsientoBean asientoBean = new AsientoBean();
        CodigoBean cod = new CodigoBean();

        if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().
                    setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh());
        }

        conceptoBean = conceptoService.obtenerConceptoCuentasPorId(cajaChicaLiquidacionBean.getConceptoBean());
        cajaChicaLiquidacionBean.setCuentaD(conceptoBean.getPlanContableCuentaDBean().getCuenta());
        cajaChicaLiquidacionBean.setCuentaH(conceptoBean.getPlanContableCuentaHBean().getCuenta());
////        cajaChicaLiquidacionDAO.insertarCajaChicaLiquidacion2(cajaChicaLiquidacionBean);
        insertarCajaChicaLiq(cajaChicaLiquidacionBean);
//
        if (conceptoBean.getFlgTieneCrVista().equals("Sí")) {
            asientoBean.setUnidadNegocioBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getUnidadNegocioBean());
            asientoBean.setIdObjeto(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion());
            asientoBean.setObjeto(MaristaConstantes.OBJ_LIQUIDACION);

            cod.setIdCodigo(1);
            asientoBean.setPlanContableCuentaDBean(conceptoBean.getPlanContableCuentaDBean());
            asientoBean.setPlanContableCuentaHBean(conceptoBean.getPlanContableCuentaHBean());
            asientoBean.setNumeroComprobante(cajaChicaLiquidacionBean.getNroDoc());
            asientoBean.setRuc(cajaChicaLiquidacionBean.getRuc());
//            if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean().getIdCodigo() == null) {
//                cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean().setIdCodigo(14901);
//            }
//            asientoBean.setTipoMoneda(cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean());
            asientoBean.setTipoDoc(cajaChicaLiquidacionBean.getTipoDoc());
            asientoBean.setCreaPor(cajaChicaLiquidacionBean.getCreaPor());
            asientoBean.setTc(tc);
            asientoBean.setTipoOpe(cod);
            asientoBean.setFechaOpe(new Date());
            if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
                asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getFechaDoc());
//            asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getFechaSol());
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo() != null) {
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                        asientoBean.setMonedaOrigen("S");
                        asientoBean.getTipoMoneda().setIdCodigo(14901);
                        
                    } else {
                        asientoBean.setMonedaOrigen("D");
                        asientoBean.getTipoMoneda().setIdCodigo(14902);
                    }
                } else {
                    asientoBean.setMonedaOrigen("S");
                    asientoBean.getTipoMoneda().setIdCodigo(14901);
                }
            } else {
//            asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getFechaSol());
                asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getFechaDoc());
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo() != null) {
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                        asientoBean.setMonedaOrigen("S");
                        asientoBean.getTipoMoneda().setIdCodigo(14901);
                    } else {
                        asientoBean.setMonedaOrigen("D");
                        asientoBean.getTipoMoneda().setIdCodigo(14902);
                    }
                } else {
                    asientoBean.setMonedaOrigen("S");
                    asientoBean.getTipoMoneda().setIdCodigo(14901);
                }
            }
            Calendar fecha = Calendar.getInstance();
            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            asientoBean.setAnio(año);
            asientoBean.setMes(mes);

            for (AsientoBean listaCR1 : cajaChicaLiquidacionBean.getListaCajaChicaLiquidacionCRBean()) {
                asientoBean.setMonto(listaCR1.getMonto());
                asientoBean.setCreaFecha(new Date());
                asientoBean.setCentroResponsabilidadBean(listaCR1.getCentroResponsabilidadBean());
                asientoBean.setStatus(Boolean.TRUE);
                asientoService.insertarAsiento(asientoBean);
                System.out.println("entró insertarAsiento() :" + listaCR1.getCentroResponsabilidadBean().getCr());
                asientoBean.setMonto(0d);
            }
        } else {
            System.out.println("No tiene CR :D");
        }
        //
    }

    @Transactional
    public void insertarCajaChicaLiq(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        System.out.println("entró :O insert1");
        cajaChicaLiquidacionDAO.insertarCajaChicaLiquidacion(cajaChicaLiquidacionBean);
    }

    @Transactional
    public void modificarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        cajaChicaLiquidacionDAO.modificarCajaChicaLiquidacion(cajaChicaLiquidacionBean);
        AsientoDAO asientoDAO = BeanFactory.getAsientoDAO();
        AsientoBean asiento = new AsientoBean();
        asiento.setUnidadNegocioBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getUnidadNegocioBean());
        asiento.setStatus(Boolean.FALSE);
        asiento.setObjeto(MaristaConstantes.OBJ_LIQUIDACION);
        asiento.setIdObjeto(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion());
        asiento.setModiPor(cajaChicaLiquidacionBean.getModiPor());
        asientoDAO.cambiarEstadoAsiento(asiento);

        TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
        TipoCambioBean tipoCambio = new TipoCambioBean();
        Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
        tipoCambio.setIdTipoMoneda(IdTipoMoneda);
        tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
        //eliminado e insertando elasiento
        AsientoService asientoService = BeanFactory.getAsientoService();
        ConceptoService conceptoService = BeanFactory.getConceptoService();
        ConceptoBean conceptoBean = new ConceptoBean();
        AsientoBean asientoBean = new AsientoBean();
        CodigoBean cod = new CodigoBean();

        if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().
                    setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh());
        }

        conceptoBean = conceptoService.obtenerConceptoCuentasPorId(cajaChicaLiquidacionBean.getConceptoBean());

        asientoBean.setUnidadNegocioBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getUnidadNegocioBean());
        asientoBean.setIdObjeto(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion());
        asientoBean.setObjeto(MaristaConstantes.OBJ_LIQUIDACION);

        cod.setIdCodigo(1);
        asientoBean.setPlanContableCuentaDBean(conceptoBean.getPlanContableCuentaDBean());
        asientoBean.setPlanContableCuentaHBean(conceptoBean.getPlanContableCuentaHBean());
        asientoBean.setNumeroComprobante(cajaChicaLiquidacionBean.getNroDoc());
        asientoBean.setRuc(cajaChicaLiquidacionBean.getRuc());
        if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean().getIdCodigo() == null) {
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean().setIdCodigo(14901);
        }
        asientoBean.setTipoMoneda(cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean());
        asientoBean.setTipoDoc(cajaChicaLiquidacionBean.getTipoDoc());
        asientoBean.setCreaPor(cajaChicaLiquidacionBean.getCreaPor());
        asientoBean.setTc(tipoCambio.getTcVenta().doubleValue());
        asientoBean.setTipoOpe(cod);
        asientoBean.setFechaOpe(new Date());
        if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
            asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getFechaSol());
            if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo() != null) {
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    asientoBean.setMonedaOrigen("S");
                } else {
                    asientoBean.setMonedaOrigen("D");
                }
            } else {
                asientoBean.setMonedaOrigen("S");
            }
        } else {
            asientoBean.setFechaDoc(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getFechaSol());
            if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo() != null) {
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    asientoBean.setMonedaOrigen("S");
                } else {
                    asientoBean.setMonedaOrigen("D");
                }
            } else {
                asientoBean.setMonedaOrigen("S");
            }
        }
        Calendar fecha = Calendar.getInstance();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        asientoBean.setAnio(año);
        asientoBean.setMes(mes);
        for (AsientoBean listaCR1 : cajaChicaLiquidacionBean.getListaCajaChicaLiquidacionCRBean()) {
            asientoBean.setMonto(listaCR1.getMonto());
            asientoBean.setCreaFecha(new Date());
            asientoBean.setCentroResponsabilidadBean(listaCR1.getCentroResponsabilidadBean());
            asientoBean.setStatus(Boolean.TRUE);
            asientoService.insertarAsiento(asientoBean);
            System.out.println("entró insertarAsiento() :" + listaCR1.getCentroResponsabilidadBean().getCr());
            asientoBean.setMonto(0d);
        }
    }

    @Transactional
    public void eliminarCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        AsientoDAO asientoDAO = BeanFactory.getAsientoDAO();
        AsientoBean asiento = new AsientoBean();
        asiento.setUnidadNegocioBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getUnidadNegocioBean());
        asiento.setStatus(Boolean.FALSE);
        asiento.setObjeto(MaristaConstantes.OBJ_LIQUIDACION);
        asiento.setIdObjeto(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion());
        asiento.setModiPor(cajaChicaLiquidacionBean.getModiPor());
        asientoDAO.cambiarEstadoAsiento(asiento);
        cajaChicaLiquidacionDAO.eliminarCajaChicaLiquidacion(cajaChicaLiquidacionBean);
    }

    @Transactional
    public void rendirCuentas(Boolean operacion1, UsuarioBean usuarioLogin, CajaChicaLiquidacionBean cajaChicaLiquidacionBean, Double montoDiferencia, Double montoMaxSol, Double montoMaxDol) throws Exception {
        Integer idSol = null;//idSolicitud 
        if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
            idSol = cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh();
        }
        if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
            idSol = cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh();
        }
        //Operacion 1
        if (operacion1) {
            //1.- Gererando solicitud
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            TipoSolicitudBean tipoSolicitudBean = new TipoSolicitudBean();
            if (montoDiferencia <= montoMaxSol || montoDiferencia <= montoMaxDol) {
                tipoSolicitudBean.setNombre(MaristaConstantes.CONTRA_PAGO);
            } else {
                tipoSolicitudBean.setNombre(MaristaConstantes.TIP_SOL_GEN);
            }

            tipoSolicitudBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            tipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorNombre(tipoSolicitudBean);
            SolicitudCajaCHBean solicitudCajaCHBean = new SolicitudCajaCHBean();
            SolicitudCajaCHBean soli = new SolicitudCajaCHBean();

            if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                soli = solicitudCajaCHDAO.obtenerTipoSolicitanteResCheque(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());
                /////
                //3.- Cambiando estado rendicion
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                    //añadido
                    CodigoBean cb = new CodigoBean(MaristaConstantes.COD_REND_FINALIZADO);
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setTipoEstRend(cb);
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    solicitudCajaCHService.modificarTipoEstRend(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());
                    //fin
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().setFlgRendicion(Boolean.TRUE);
                    cajaChicaMovDAO.cambiarEstadoRendicionMov(cajaChicaLiquidacionBean.getCajaChicaMovBean());
                }
                //// 
                TipoSolicitudBean tipoSolConAuto = new TipoSolicitudBean();
                tipoSolConAuto = tipoSolicitudService.validarTipSolConAuto(tipoSolicitudBean);

                if (tipoSolConAuto == null) {
                    solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_AUTORIZADO_SOL);
                } else {
                    solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_CCH);
                }
                solicitudCajaCHBean.setTipoSolicitudBean(tipoSolicitudBean);
                solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                solicitudCajaCHBean.setMonto(montoDiferencia);
                solicitudCajaCHBean.setMontoAprobado(montoDiferencia);
                if (soli != null) {
                    switch (solicitudCajaCHBean.getIdTipoSolicitante()) {
                        case "COL":
                            solicitudCajaCHBean.setIdTipoSol(1);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                            break;
                        case "PER":
                            solicitudCajaCHBean.setIdTipoSol(2);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(soli.getIdPersonalSol());
                            break;
                        case "PRO":
                            solicitudCajaCHBean.setIdTipoSol(3);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(soli.getIdPersonalSol());
                            break;
                        default:
                            solicitudCajaCHBean.setIdTipoSol(1);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                    }
                } else {
                    solicitudCajaCHBean.setIdTipoSol(1);
                    solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                    solicitudCajaCHBean.getEntidadBean().setRuc(null);
                    solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(solicitudCajaCHBean.getMotivo()).append(" ").append(MaristaConstantes.MSJ_DIF_SOL);
                System.out.println(solicitudCajaCHBean.getMotivo());
                System.out.println(MaristaConstantes.MSJ_DIF_SOL);
                System.out.println(sb.toString());
                solicitudCajaCHBean.setMotivo(sb.toString());
                solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);
                cajaChicaLiquidacionBean.getCajaChicaMovBean().setSolicitudCajaCHBean(solicitudCajaCHBean);
            }
            if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
//                cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                soli = solicitudCajaCHDAO.obtenerTipoSolicitanteResCheque(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                /////
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
                    //añadido
                    CodigoBean cb = new CodigoBean(MaristaConstantes.COD_REND_FINALIZADO);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setTipoEstRend(cb);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    solicitudCajaCHService.modificarTipoEstRend(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                    //fin
                    cajaChicaLiquidacionBean.getDocEgresoBean().setFlgRendicion(Boolean.TRUE);
                    DocEgresoDAO docEgresoDAO = BeanFactory.getDocEgresoDAO();
                    docEgresoDAO.cambiarEstadoRendicionDoc(cajaChicaLiquidacionBean.getDocEgresoBean());
                }
                /////
                solicitudCajaCHBean.setTipoSolicitudBean(tipoSolicitudBean);
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_AUTORIZADO_SOL);
                solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                solicitudCajaCHBean.setCreaFecha(new Date());
                solicitudCajaCHBean.setMonto(montoDiferencia);
                solicitudCajaCHBean.setMontoAprobado(montoDiferencia);
                if (soli != null) {
                    switch (solicitudCajaCHBean.getIdTipoSolicitante()) {
                        case "COL":
                            solicitudCajaCHBean.setIdTipoSol(1);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                            break;
                        case "PER":
                            solicitudCajaCHBean.setIdTipoSol(2);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(soli.getIdPersonalSol());
                            break;
                        case "PRO":
                            solicitudCajaCHBean.setIdTipoSol(3);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(soli.getIdPersonalSol());
                            break;
                        default:
                            solicitudCajaCHBean.setIdTipoSol(1);
                            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                            solicitudCajaCHBean.getEntidadBean().setRuc(null);
                            solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                    }
                } else {
                    solicitudCajaCHBean.setIdTipoSol(1);
                    solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                    solicitudCajaCHBean.getEntidadBean().setRuc(null);
                    solicitudCajaCHBean.getPersonalBean().setIdPersonal(new Integer(soli.getIdPersonalSol()));
                }
                StringBuilder sb = new StringBuilder();
                sb.append(solicitudCajaCHBean.getMotivo()).append(" ").append(MaristaConstantes.MSJ_DIF_SOL);
                System.out.println(solicitudCajaCHBean.getMotivo());
                System.out.println(MaristaConstantes.MSJ_DIF_SOL);
                System.out.println(sb.toString());
                solicitudCajaCHBean.setMotivo(sb.toString());
                solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);
                cajaChicaLiquidacionBean.getDocEgresoBean().setSolicitudCajaCHBean(solicitudCajaCHBean);
            }

            //2.- Generando CR
            DetSolicitudCajaChCRBean bean = new DetSolicitudCajaChCRBean();
//            bean.setSolicitudCajaCHBean(solicitudCajaCHBean);
            //anadido
            bean.getSolicitudCajaCHBean().setIdSolicitudCajaCh(idSol);
            bean.getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            //finn
            List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean = solicitudCajaCHDAO.obtenerDetSolcitudCajaChCRPorSol(bean);
            List<CentroResponsabilidadBean> listaCR = new ArrayList<>();
            for (DetSolicitudCajaChCRBean beanDet : listaDetSolicitudCajaChCRBean) {
                System.out.println("tipoNivelCR: "+ beanDet.getCentroResponsabilidadBean().getTipoNivelCR());
                System.out.println("tipoNivelCR2: "+ beanDet.getCentroResponsabilidadBean().getIdTipoGrupoCR());
                beanDet.getCentroResponsabilidadBean().setTipoNivelCR(beanDet.getCentroResponsabilidadBean().getIdTipoGrupoCR());
                listaCR.add(beanDet.getCentroResponsabilidadBean());
            }
            if (!listaDetSolicitudCajaChCRBean.isEmpty()) {
                if (listaDetSolicitudCajaChCRBean.get(0).getTipoDistribucion().getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                    new GLTCalculadoraCR().calcularPorPonderacion(listaCR, montoDiferencia);
                }
                if (listaDetSolicitudCajaChCRBean.get(0).getTipoDistribucion().getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                    new GLTCalculadoraCR().calcularPorDivision(listaCR, montoDiferencia);
                }
                if (listaDetSolicitudCajaChCRBean.get(0).getTipoDistribucion().getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                    new GLTCalculadoraCR().calcularPorDivision(listaCR, montoDiferencia);
                }
            }
            Integer idTipoDis = solicitudCajaCHDAO.obtenerTipoDistribucion(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCR) {
                DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
                detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                CodigoBean cod = new CodigoBean();
                if (idTipoDis != null) {
                    cod.setIdCodigo(idTipoDis);
                    detSolicitudCajaChCRBean.setTipoDistribucion(cod);
                } else {
                    cod.setIdCodigo(MaristaConstantes.Id_Division);
                    detSolicitudCajaChCRBean.setTipoDistribucion(cod);
                }
                System.out.println("cr: "+ centroResponsabilidadBean.getCr());
                detSolicitudCajaChCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                detSolicitudCajaChCRBean.setValor(centroResponsabilidadBean.getMontoDistribucion());
//                detSolicitudCajaChCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                detSolicitudCajaChCRBean.setCreaPor(usuarioLogin.getUsuario());
                solicitudCajaCHDAO.insertarDetSolicitudCajaChCR(detSolicitudCajaChCRBean);
            }
        }
    }

    public CajaChicaLiquidacionBean obtenerCajaChicaLiquidacionPorId(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        return cajaChicaLiquidacionDAO.obtenerCajaChicaLiquidacionPorId(cajaChicaLiquidacionBean);
    }

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacion(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        return cajaChicaLiquidacionDAO.obtenerCajaChicaLiquidacion(cajaChicaLiquidacionBean);
    }

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacionPorMov(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        List<CajaChicaLiquidacionBean> lista = cajaChicaLiquidacionDAO.obtenerCajaChicaLiquidacionPorMov(cajaChicaLiquidacionBean);
        return lista;
    }

    public List<CajaChicaLiquidacionBean> obtenerCajaChicaLiquidacionPorDoc(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) throws Exception {
        List<CajaChicaLiquidacionBean> lista = cajaChicaLiquidacionDAO.obtenerCajaChicaLiquidacionPorDoc(cajaChicaLiquidacionBean);
//        insert(cajaChicaLiquidacionBean);
        return lista;
    }

    public List<LiquidacionRepBean> obtenerLiquidacion(String uniNeg, Integer idObjeto) throws Exception {
        return cajaChicaLiquidacionDAO.obtenerLiquidacion(uniNeg, idObjeto);
    }

    public List<LiquidacionRepBean> obtenerDetLiquidacion(String uniNeg, Integer idObjeto, Integer idCajaChicaLiquidacion) throws Exception {
        return cajaChicaLiquidacionDAO.obtenerDetLiquidacion(uniNeg, idObjeto, idCajaChicaLiquidacion);
    }

    public SolicitudCajaCHDAO getSolicitudCajaCHDAO() {
        return solicitudCajaCHDAO;
    }

    public void setSolicitudCajaCHDAO(SolicitudCajaCHDAO solicitudCajaCHDAO) {
        this.solicitudCajaCHDAO = solicitudCajaCHDAO;
    }

    public CajaChicaMovDAO getCajaChicaMovDAO() {
        return cajaChicaMovDAO;
    }

    public void setCajaChicaMovDAO(CajaChicaMovDAO cajaChicaMovDAO) {
        this.cajaChicaMovDAO = cajaChicaMovDAO;
    }

    public void modificarCajaChicaLiquidacionAnulacion(String uniNeg, Integer idSolicitudCajaCh, String modiPor) throws Exception {
        cajaChicaLiquidacionDAO.modificarCajaChicaLiquidacionAnulacion(uniNeg, idSolicitudCajaCh, modiPor);
    }

}
