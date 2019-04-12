package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentaFacturaBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.dao.MensajeDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.managedBean.RegistroCompraMB;
import pe.marista.sigma.managedBean.SolicitudCajaCHMB;
import pe.marista.sigma.managedBean.SolicitudLogisticoMB;
import pe.marista.sigma.util.Mailing;

/**
 *
 * @author Administrador
 */
public class MensajeService {

    private MensajeDAO mensajeDAO;
    private SolicitudCajaCHDAO solicitudCajaCHDAO;

    public MensajeDAO getMensajeDAO() {

        return mensajeDAO;
    }

    public void setMensajeDAO(MensajeDAO mensajeDAO) {
        this.mensajeDAO = mensajeDAO;
    }

    public List<MensajeBean> obtenerMensajePorOwner(MensajeBean mensajeBean) throws Exception {
        return mensajeDAO.obtenerMensajePorOwner(mensajeBean);
    }

    //mis mensajes 
    public List<MensajeBean> obtenerTodosLosMensajesPorPersonal(PersonalBean personalBean) throws Exception {
        return mensajeDAO.obtenerTodosLosMensajesPorPersonal(personalBean);
    }

    public List<MensajeBean> obtenerMensajePorOwnerRecibidos(MensajeBean mensajeBean) throws Exception {
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_SOL_PENDIENTE_MSJE);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        return mensajeDAO.obtenerMensajePorOwnerRecibidos(mensajeBean);
    }

    public List<MensajeBean> obtenerMensajePorOwneAtendidos(MensajeBean mensajeBean) throws Exception {
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        return mensajeDAO.obtenerMensajePorOwnerAtendidos(mensajeBean);
    }

    public List<MensajeBean> enviarMensajePapelera(MensajeBean mensajeBean) throws Exception {
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ELIMINADO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        return mensajeDAO.enviarMensajePapelera(mensajeBean);
    }

    public List<MensajeBean> obtenerMensajePorFiltro(MensajeBean mensajeBean) throws Exception {
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        return mensajeDAO.obtenerMensajePorFiltro(mensajeBean);
    }

    @Transactional
    public void autorizarMensajeSolCajaCh(MensajeBean mensajeBean) throws Exception {
        mensajeDAO.autorizarMensajeSolCajaCh(mensajeBean);
        //CAMBIANDO EL ESTADO DE MENSAJE A ATENDIDO
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        mensajeDAO.actualizarFechaAccionStatusMsje(mensajeBean);

    }

    @Transactional
    public void autorizarCompleto(MensajeBean mensajeBean, SolicitudCajaCHMB solicitudCajaCHMB) throws Exception {
        Integer idmodopago = null;
        if (solicitudCajaCHMB.getSolicitudCajaCHBean().getIdTipoModoPago() == null) {
            idmodopago = MaristaConstantes.CODIGO_CHEQUE;
        } else {
            idmodopago = solicitudCajaCHMB.getSolicitudCajaCHBean().getIdTipoModoPago();
        }
        mensajeBean.getTipoModoPago().setIdCodigo(idmodopago);
        mensajeBean.setNumCuenta(solicitudCajaCHMB.getSolicitudCajaCHBean().getNumCuenta());
        mensajeBean.setRucBanco(solicitudCajaCHMB.getSolicitudCajaCHBean().getRucBanco());
        autorizarMensajeSolCajaCh(mensajeBean);
        SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
        SolicitudCajaCHBean solicitudCajaCHBean = solicitudCajaCHMB.getSolicitudCajaCHBean();
        solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(solicitudCajaCHBean);
        solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHMB.getSolicitudCajaCHBean().getMontoAprobado());
        solicitudCajaCHBean.setObs(solicitudCajaCHMB.getSolicitudCajaCHBean().getObs());
        solicitudCajaCHBean.setMotivo(solicitudCajaCHMB.getSolicitudCajaCHBean().getMotivo());
//        solicitudCajaCHBean.setResChequeBean(solicitudCajaCHMB.getSolicitudCajaCHBean().getResChequeBean());
        solicitudCajaCHBean.setIdTipoRespCheque(solicitudCajaCHMB.getSolicitudCajaCHBean().getIdTipoRespCheque());
        solicitudCajaCHBean.setIdRespCheque(solicitudCajaCHMB.getSolicitudCajaCHBean().getIdRespCheque());
        solicitudCajaCHBean.setNomRespCheque(solicitudCajaCHMB.getSolicitudCajaCHBean().getNomRespCheque());
        solicitudCajaCHBean.setConceptoUniNegBean(solicitudCajaCHMB.getSolicitudCajaCHBean().getConceptoUniNegBean());
        solicitudCajaCHMB.insertarSolicitudCajaCHAprobacion();
        solicitudCajaCHService.cambiarEstadoSolicitudMontoAprobadoCCh(solicitudCajaCHBean);
        System.out.println("ok");
    }

    //registro compra
    @Transactional
    public void autorizarMensajeRegistroCompra(MensajeBean mensajeBean) throws Exception {
        mensajeDAO.autorizarMensajeRegistroCompra(mensajeBean);
        //CAMBIANDO EL ESTADO DE MENSAJE A ATENDIDO
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        mensajeDAO.actualizarFechaAccionStatusMsje(mensajeBean);

    }

    @Transactional
    public void autorizarMensajeFacturaCompra(MensajeBean mensajeBean) throws Exception {
        mensajeDAO.autorizarMensajeFacturaCompra(mensajeBean);
        //CAMBIANDO EL ESTADO DE MENSAJE A ATENDIDO
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        mensajeDAO.actualizarFechaAccionStatusMsje(mensajeBean);

    }

    @Transactional
    public void autorizarCompletoRC(MensajeBean mensajeBean, RegistroCompraMB registroCompraMB) throws Exception {
        autorizarMensajeRegistroCompra(mensajeBean);
        RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
        RegistroCompraBean registroCompraBean = registroCompraMB.getRegistroCompraBean();
        registroCompraBean = registroCompraService.obtenerPorId(registroCompraBean);
        registroCompraService.cambiarEstadoSolicitudRC(registroCompraBean);
        System.out.println("ok :D");
    }

    @Transactional
    public void autorizarCompletoFC(MensajeBean mensajeBean, RegistroCompraMB registroCompraMB) throws Exception {
        Integer idmodopago = null;
        if (registroCompraMB.getFacturaCompraBean().getIdTipoModoPago() == null) {
            idmodopago = MaristaConstantes.CODIGO_CHEQUE;
        } else {
            idmodopago = registroCompraMB.getFacturaCompraBean().getIdTipoModoPago();
        }

        RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
        mensajeBean.getTipoModoPago().setIdCodigo(idmodopago);
        autorizarMensajeFacturaCompra(mensajeBean);
        FacturaCompraBean facturaCompra = registroCompraMB.getFacturaCompraBean();

        facturaCompra = registroCompraService.obtenerPorIdFactura(facturaCompra);
        registroCompraService.cambiarEstadoFacturaCompra(facturaCompra);

        registroCompraService.eliminarCuentaFact(facturaCompra.getIdFacturaCompra(), facturaCompra.getUnidadNegocioBean().getUniNeg());

        for (CuentaFacturaBean lisFactura : registroCompraMB.getListaCuentaFacturaBean()) {
            System.out.println("cr" + lisFactura.getCentroResponsabilidadBean().getCr());
            lisFactura.setFacturaCompraBean(facturaCompra);
            lisFactura.getUnidadNegocioBean().setUniNeg(facturaCompra.getUnidadNegocioBean().getUniNeg());
            lisFactura.getFacturaCompraBean().setIdFacturaCompra(lisFactura.getFacturaCompraBean().getIdFacturaCompra());
            lisFactura.setCreaPor(registroCompraMB.getUsuarioSessionBean().getUsuario());
            registroCompraService.insertarCuentaFact(lisFactura);

        }

        System.out.println("ok :D");
    }

    //requerimiento
    @Transactional
    public void autorizarMensajeSolLog(MensajeBean mensajeBean) throws Exception {
        mensajeDAO.autorizarMensajeSolLog(mensajeBean);
        //CAMBIANDO EL ESTADO DE MENSAJE A ATENDIDO
        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
        mensajeDAO.actualizarFechaAccionStatusMsje(mensajeBean);

    }

    @Transactional
    public void autorizarCompletoLog(MensajeBean mensajeBean, SolicitudLogisticoMB solicitudLogisticoMB) throws Exception {
        LegajoService legajoService = BeanFactory.getLegajoService();
        String correo;
        Boolean flgAuto = mensajeBean.getFlgAutoriza();
        autorizarMensajeSolLog(mensajeBean);
        SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
        solicitudLogisticoMB.grabarAprobacion();
        SolicitudLogisticoBean solicitudLogisticoBean = solicitudLogisticoMB.getSolicitudLogisticoBean();
        solicitudLogisticoBean = solicitudLogisticoService.obtenerPorId(solicitudLogisticoBean.getIdRequerimiento(), solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
        solicitudLogisticoBean.setImportePropuesto(solicitudLogisticoMB.getSolicitudLogisticoBean().getImportePropuesto());
        solicitudLogisticoService.cambiarEstadoSolicitudLog(solicitudLogisticoBean);
        correo = legajoService.obtenerCorreoCorPorPersonal(MaristaConstantes.UNI_ORG_LOGISTICA, solicitudLogisticoBean.getUnidadNegocioBean().getUniNeg());
        System.out.println(correo);
        //cambiar por correo -->"sigma@gmail.com"
        if (flgAuto.equals(Boolean.TRUE)) {
            new Mailing().enviarMensajeAutorizado("sigma@gmail.com", solicitudLogisticoBean);
            new Mailing().enviarMensajeAutorizado(correo, solicitudLogisticoBean);
        } else {
            new Mailing().enviarMensajeDesAutorizado("sigma@gmail.com", solicitudLogisticoBean);
            new Mailing().enviarMensajeDesAutorizado(correo, solicitudLogisticoBean);
        }
        System.out.println("ok :D xd");
    }

    public MensajeBean obtenerMensajePorId(MensajeBean mensajeBean) throws Exception {
        return mensajeDAO.obtenerMensajePorId(mensajeBean);
    }

    public SolicitudCajaCHDAO getSolicitudCajaCHDAO() {
        return solicitudCajaCHDAO;
    }

    public void setSolicitudCajaCHDAO(SolicitudCajaCHDAO solicitudCajaCHDAO) {
        this.solicitudCajaCHDAO = solicitudCajaCHDAO;
    }

    public String obtenerMensajePorFiltroTodos(String uniNeg) throws Exception {
        return mensajeDAO.obtenerMensajePorFiltroTodos(uniNeg);
    }

    public List<MensajeBean> obtenerTodosLosMensajes(PersonalBean personalBean) throws Exception {
        return mensajeDAO.obtenerTodosLosMensajes(personalBean);
    }
    
}
