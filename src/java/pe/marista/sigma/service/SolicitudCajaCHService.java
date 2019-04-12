package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.dao.CajaChicaDAO;
import pe.marista.sigma.dao.MensajeDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.Mailing;

/**
 *
 * @author Administrador
 */
public class SolicitudCajaCHService {

    private SolicitudCajaCHDAO solicitudCajaCHDAO;
    private MensajeDAO mensajeDAO;

    //Lógica de Negocio
    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCH() throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudCajaCH();
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorUniNeg(String uniNeg) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudCajaCHBeanPorUniNeg(uniNeg);
    }

    public SolicitudCajaCHBean obtenerSolicitudCajaCHBeanPorId(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudCajaCHBeanPorId(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorPersonal(PersonalBean personalBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudCajaCHBeanPorPersonal(personalBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudCajaCHBeanPorGestor(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudCajaCHBeanPorGestor(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroPersonal(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltro(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltro(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroMenorA(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        if (solicitudCajaCHBean.getFlgSoles() != null) {
            if (solicitudCajaCHBean.getFlgSoles().equals(1)) {
                solicitudCajaCHBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
            } else {
                solicitudCajaCHBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_DOLARES);
            }
        } else {
            solicitudCajaCHBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
        }
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltroMenorA(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestor(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltroGestor(solicitudCajaCHBean);
    }

    public List<DetSolicitudCajaChCRBean> obtenerDetSolcitudCajaChCRPorSolCaj(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerDetSolcitudCajaChCRPorSolCaj(solicitudCajaCHBean);
    }

    @Transactional
    public void insertarSolicitudCajaCH(SolicitudCajaCHBean solicitudCajaCHBean, String origen, MensajeBean mensajeBean, CajaChicaBean cajaChicaBean) throws Exception {
        if (origen == null) {
            solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_CCH);
        } else {

            if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud() == null) {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_CONTRA_PAG);
                solicitudCajaCHBean.getTipoSolicitudBean().setUnidadNegocioBean(solicitudCajaCHBean.getUnidadNegocioBean());
                TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                solicitudCajaCHBean.setTipoSolicitudBean(tipoSolicitudService.obtenerTipoSolicitudPorNombre(solicitudCajaCHBean.getTipoSolicitudBean()));
            }
            System.out.println("solicitudCajaCHBean.getIdTipoSolicitante() ... " + solicitudCajaCHBean.getIdTipoSolicitante());
            solicitudCajaCHBean.setIdTipoSolicitante("COL");
            solicitudCajaCHBean.setIdTipoSol(1);
            solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
            solicitudCajaCHBean.getEntidadBean().setRuc(null);
            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            TipoSolicitudBean tsb = new TipoSolicitudBean();
            tsb.setUnidadNegocioBean(solicitudCajaCHBean.getUnidadNegocioBean());
            tsb.setIdTipoSolicitud(solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud());
            tsb = tipoSolicitudService.obtenerTipoSolicitudPorId(tsb);

            if (tsb.getNombre().equals(MaristaConstantes.CONTRA_PAGO)) {
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(18705);
//                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_AUTORIZADO_SOL);
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
                System.out.println(">>>monto: " + solicitudCajaCHBean.getMonto());
            } else {
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_CCH);
            }

        }

//        if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre() == null) {
//            solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_ARENDIR);
//        }
        //Solo genera solicitud caja chica personal
        solicitudCajaCHBean.setIdTipoSol(1);
        solicitudCajaCHBean.setIdTipoSolicitante("COL");
        solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
        solicitudCajaCHBean.getEntidadBean().setRuc(null);
//        System.out.println("uni: " + solicitudCajaCHBean.getTipoSolicitudBean().getUnidadNegocioBean().getNombreUniNeg());
        System.out.println("solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);");
        solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);
        for (int i = 0; i < solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().size(); i++) {
            DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
            detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
            detSolicitudCajaChCRBean.setCentroResponsabilidadBean(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getCentroResponsabilidadBean());
            detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
            detSolicitudCajaChCRBean.setValor(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getValorD());
            detSolicitudCajaChCRBean.setCreaPor(solicitudCajaCHBean.getCreaPor());
            solicitudCajaCHDAO.insertarDetSolicitudCajaChCR(detSolicitudCajaChCRBean);
        }
        solicitudCajaCHBean.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
        solicitudCajaCHDAO.llamarProGetAutorizadores(solicitudCajaCHBean);
        //Solo para actulizar Reposicon Caja Chica
        if (cajaChicaBean != null) {
            CajaChicaDAO cajaChicaDAO = BeanFactory.getCajaChicaDAO();
            cajaChicaDAO.modificarIdSolRep(cajaChicaBean);
        }
//        mensajeDAO.llamarAlDuende(mensajeBean);
    }

    @Transactional
    public void insertarSolicitudCajaCHGen(SolicitudCajaCHBean solicitudCajaCHBean, String origen, MensajeBean mensajeBean, CajaChicaBean cajaChicaBean, String tipo) throws Exception {
        TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
        if (origen == null) {
            solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_CCH);
        } else {
            solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
//            System.out.println(">>>monto: " + solicitudCajaCHBean.getMonto());
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre() == null) {
            if (tipo.equals(MaristaConstantes.TIP_SOL_GEN)) {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_GEN);
            } else {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_ARENDIR);
            }
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre() != null) {
            TipoSolicitudBean tipoSoli = new TipoSolicitudBean();
            solicitudCajaCHBean.getTipoSolicitudBean().setUnidadNegocioBean(solicitudCajaCHBean.getUnidadNegocioBean());
            tipoSoli = tipoSolicitudService.obtenerTipoSolicitudPorNombre(solicitudCajaCHBean.getTipoSolicitudBean());
            System.out.println("nombre..." + tipoSoli.getIdTipoAutoriza1());
            if (tipoSoli.getIdTipoAutoriza1() == null) {
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
            }
        }
//        System.out.println("uni: " + solicitudCajaCHBean.getTipoSolicitudBean().getUnidadNegocioBean().getNombreUniNeg());
        switch (solicitudCajaCHBean.getIdTipoSolicitante()) {
            case "COL":
                solicitudCajaCHBean.setIdTipoSol(1);
                solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                solicitudCajaCHBean.getEntidadBean().setRuc(null);
                break;
            case "PER":
                solicitudCajaCHBean.setIdTipoSol(2);
                solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                solicitudCajaCHBean.getEntidadBean().setRuc(null);
                break;
            case "PRO":
                solicitudCajaCHBean.setIdTipoSol(3);
                solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                break;
        }

        solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);
        if (solicitudCajaCHBean.getFlgTieneCr() == false) {
            for (int i = 0; i < solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().size(); i++) {
                DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
                detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                detSolicitudCajaChCRBean.setCentroResponsabilidadBean(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getCentroResponsabilidadBean());
                detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
                detSolicitudCajaChCRBean.setValor(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getValorD());
                detSolicitudCajaChCRBean.setCreaPor(solicitudCajaCHBean.getCreaPor());
                solicitudCajaCHDAO.insertarDetSolicitudCajaChCR(detSolicitudCajaChCRBean);
            }
        } else {
            System.out.println("No tiene CR :D");
        }
        solicitudCajaCHBean.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
        solicitudCajaCHDAO.llamarProGetAutorizadores(solicitudCajaCHBean);

        //Solo para actulizar Reposicon Caja Chica
        if (cajaChicaBean != null) {
            CajaChicaDAO cajaChicaDAO = BeanFactory.getCajaChicaDAO();
            cajaChicaDAO.modificarIdSolRep(cajaChicaBean);
        }

        TipoSolicitudBean tipoSoli = new TipoSolicitudBean();
        tipoSoli.setIdTipoSolicitud(solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud());
//        tiposo

        if (solicitudCajaCHBean.getIdAutorizaPer1Bean().getCorreoCor() != null && !solicitudCajaCHBean.getIdAutorizaPer1Bean().getCorreoCor().trim().equals("")) {
            new Mailing().enviarCorreoMensaje(solicitudCajaCHBean);
        }
    }

    @Transactional
    public void insertarSolicitudCajaCHGenBarina(SolicitudCajaCHBean solicitudCajaCHBean, String origen, MensajeBean mensajeBean, CajaChicaBean cajaChicaBean, String tipo) throws Exception {
        TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
        if (origen == null) {
            solicitudCajaCHBean.getTipoStatusSolCajaChBean().setIdCodigo(MaristaConstantes.COD_PENDIENTE_CCH);
        } else {
            solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
//            System.out.println(">>>monto: " + solicitudCajaCHBean.getMonto());
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre() == null) {
            if (tipo.equals(MaristaConstantes.TIP_SOL_GEN)) {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_GEN);
            } else {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_ARENDIR);
            }
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre() != null) {
            TipoSolicitudBean tipoSoli = new TipoSolicitudBean();
            solicitudCajaCHBean.getTipoSolicitudBean().setUnidadNegocioBean(solicitudCajaCHBean.getUnidadNegocioBean());
            tipoSoli = tipoSolicitudService.obtenerTipoSolicitudPorNombre(solicitudCajaCHBean.getTipoSolicitudBean());
            System.out.println("nombre..." + tipoSoli.getIdTipoAutoriza1());
            if (tipoSoli.getIdTipoAutoriza1() == null) {
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
            }
        }
//        System.out.println("uni: " + solicitudCajaCHBean.getTipoSolicitudBean().getUnidadNegocioBean().getNombreUniNeg());
        switch (solicitudCajaCHBean.getIdTipoSolicitante()) {
            case "COL":
                solicitudCajaCHBean.setIdTipoSol(1);
                solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                solicitudCajaCHBean.getEntidadBean().setRuc(null);
                break;
            case "PER":
                solicitudCajaCHBean.setIdTipoSol(2);
                solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                solicitudCajaCHBean.getEntidadBean().setRuc(null);
                break;
            case "PRO":
                solicitudCajaCHBean.setIdTipoSol(3);
                solicitudCajaCHBean.getPersonaBean().setIdPersona(null);
                solicitudCajaCHBean.getPersonalBean().setIdPersonal(null);
                break;
        }
        solicitudCajaCHDAO.insertarSolicitudCajaCH1(solicitudCajaCHBean);
        if (solicitudCajaCHBean.getFlgTieneCr() == false) {
            for (int i = 0; i < solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().size(); i++) {
                DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
                detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                detSolicitudCajaChCRBean.setCentroResponsabilidadBean(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getCentroResponsabilidadBean());
                detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
                detSolicitudCajaChCRBean.setValor(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getValorD());
                detSolicitudCajaChCRBean.setCreaPor(solicitudCajaCHBean.getCreaPor());
                solicitudCajaCHDAO.insertarDetSolicitudCajaChCR(detSolicitudCajaChCRBean);
            }
        } else {
            System.out.println("No tiene CR :D");
        }
        solicitudCajaCHBean.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
        solicitudCajaCHDAO.llamarProGetAutorizadores(solicitudCajaCHBean);

        //Solo para actulizar Reposicon Caja Chica
        if (cajaChicaBean != null) {
            CajaChicaDAO cajaChicaDAO = BeanFactory.getCajaChicaDAO();
            cajaChicaDAO.modificarIdSolRep(cajaChicaBean);
        }

        TipoSolicitudBean tipoSoli = new TipoSolicitudBean();
        tipoSoli.setIdTipoSolicitud(solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud());
//        tiposo

        if (solicitudCajaCHBean.getIdAutorizaPer1Bean().getCorreoCor() != null && !solicitudCajaCHBean.getIdAutorizaPer1Bean().getCorreoCor().trim().equals("")) {
            new Mailing().enviarCorreoMensaje(solicitudCajaCHBean);
        }
    }

    @Transactional
    public void insertarSolicitudCajaCHAprobacion(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.eliminarDetSolicitudCajaChCR(solicitudCajaCHBean.getIdSolicitudCajaCh(), solicitudCajaCHBean.getUnidadNegocioBean().getUniNeg());
        for (int i = 0; i < solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().size(); i++) {
            DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
            detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
            detSolicitudCajaChCRBean.setCentroResponsabilidadBean(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getCentroResponsabilidadBean());
            detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
            detSolicitudCajaChCRBean.setValor(solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean().get(i).getValorD());
            detSolicitudCajaChCRBean.setCreaPor(solicitudCajaCHBean.getCreaPor());
            solicitudCajaCHDAO.insertarDetSolicitudCajaChCR(detSolicitudCajaChCRBean);
        }
    }

    @Transactional
    public void modificarSolicitudCajaCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.modificarSolicitudCajaCH(solicitudCajaCHBean);
    }

    @Transactional
    public void modificarMotivo(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.modificarMotivo(solicitudCajaCHBean);
    }

    @Transactional
    public void cambiarEstadoPagadoSolicitudCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_PAGADO);
        solicitudCajaCHDAO.cambiarEstadoPagadoSolicitudCCh(solicitudCajaCHBean);
    }

    @Transactional
    public void cambiarEstadoAutorizadoSolicitudCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
        solicitudCajaCHDAO.cambiarEstadoPagadoSolicitudCCh(solicitudCajaCHBean);
    }

    @Transactional
    public void eliminarDetSolicitudCajaChCR(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        solicitudCajaCHDAO.eliminarDetSolicitudCajaChCR(idSolicitudCajaCh, uniNeg);
    }

    @Transactional
    public void anularSolicitudCCH(SolicitudCajaCHBean solicitudCajaCHBean, MensajeBean mensajeBean) throws Exception {
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
        solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_ANULADO);
        solicitudCajaCHDAO.anularSolicitudCCH(solicitudCajaCHBean);

        mensajeBean.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
        mensajeBean.setIdObjeto(solicitudCajaCHBean.getIdSolicitudCajaCh());
        mensajeBean.getUnidadNegocioBean().setUniNeg(solicitudCajaCHBean.getUnidadNegocioBean().getUniNeg());
////        mensajeDAO.obtenerMensajePorIdTabla(mensajeBean);
//        mensajeBean.getTipoStatusMensajeBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_MSJE);
//        mensajeBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ELIMINADO);
//        mensajeDAO.cambiarStatusMsjeAnulado(mensajeBean);
        mensajeDAO.eliminarMensaje(mensajeBean);
    }

    @Transactional
    public void cambiarEstadoSolicitudMontoAprobadoCCh(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        Boolean flgStatusAutorizado = null; //solicitud autorizada
        Integer nivelAutoriza = null;
        solicitudCajaCHBean.setIdRespCheque(String.valueOf(solicitudCajaCHBean.getIdRespCheque().toString()));
        System.out.println("solicitudCajaCHBean.getIdRespCheque().toString()..." + solicitudCajaCHBean.getIdRespCheque().toString());
        //obteniendo el nivel de autorizadores de la solicitud
        if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza2() == null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 1;
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza3() == null) {
            nivelAutoriza = 2;
        }
        if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza3() != null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza1() != null && solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoAutoriza2() != null) {
            nivelAutoriza = 3;
        }

        //comparando el nivel de autorizadores con su flag correspondiente
        if (nivelAutoriza == 1) {
            if (solicitudCajaCHBean.getFlgAutoriza1() != null) {
                if (solicitudCajaCHBean.getFlgAutoriza1() == true) {
                    flgStatusAutorizado = true;
                } else {
                    flgStatusAutorizado = false;
                }
            }
        }
        if (nivelAutoriza == 2) {
            if (solicitudCajaCHBean.getFlgAutoriza1() != null) {
                if (solicitudCajaCHBean.getFlgAutoriza1() == true) {
                    if (solicitudCajaCHBean.getFlgAutoriza2() != null) {
                        if (solicitudCajaCHBean.getFlgAutoriza2() == true) {
                            flgStatusAutorizado = true;
                        } else {
                            flgStatusAutorizado = false;
                        }
                    }
                }
            }
        }
        if (nivelAutoriza == 3) {
            if (solicitudCajaCHBean.getFlgAutoriza1() != null && solicitudCajaCHBean.getFlgAutoriza2() != null && solicitudCajaCHBean.getFlgAutoriza3() != null
                    && solicitudCajaCHBean.getFlgAutoriza1() == true && solicitudCajaCHBean.getFlgAutoriza2() == true && solicitudCajaCHBean.getFlgAutoriza3() == true) {
                flgStatusAutorizado = true;
            } else {
                flgStatusAutorizado = false;
            }
        }

        //cambiando el estado estado de la solicicitud, true=autorizado y flase=no autorizado
        if (flgStatusAutorizado != null) {
            if (flgStatusAutorizado == true) {
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMontoAprobado());
            }
            if (flgStatusAutorizado == false) {
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
                solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_NO_AUTORIZADO);
                solicitudCajaCHBean.setMontoAprobado(0.0);
            }
        }
        if (flgStatusAutorizado == null) {
            solicitudCajaCHBean.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_CCH);
            solicitudCajaCHBean.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_PENDIENTE);
        }
        solicitudCajaCHDAO.cambiarEstadoSolicitudMontoAprobadoCCh(solicitudCajaCHBean);
    }

    @Transactional
    public void eliminarSolicitudCajaCH(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.eliminarSolicitudCajaCH(solicitudCajaCHBean);
    }

    //Rendicin
    @Transactional
    public void modificarTipoEstRend(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.modificarTipoEstRend(solicitudCajaCHBean);
    }

    public SolicitudCajaCHBean obtenerPorTipoEstRendPorId(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        return solicitudCajaCHDAO.obtenerPorTipoEstRendPorId(idSolicitudCajaCh, uniNeg);
    }

    public List<SolicitudCajaCHBean> obtenerPorTipoEstRend(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerPorTipoEstRend(solicitudCajaCHBean);
    }

    //GEtter y Setter 
    public SolicitudCajaCHDAO getSolicitudCajaCHDAO() {
        return solicitudCajaCHDAO;
    }

    public void setSolicitudCajaCHDAO(SolicitudCajaCHDAO solicitudCajaCHDAO) {
        this.solicitudCajaCHDAO = solicitudCajaCHDAO;
    }

    public MensajeDAO getMensajeDAO() {
        return mensajeDAO;
    }

    public void setMensajeDAO(MensajeDAO mensajeDAO) {
        this.mensajeDAO = mensajeDAO;
    }

    // mis solicitudes, mensaje, lista de CajaChica para obtener la lista de operaciones realizadas
    public List<SolicitudCajaCHBean> obtenerPorIdCaja(PersonalBean bean) throws Exception {
        return solicitudCajaCHDAO.obtenerPorIdCaja(bean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestorPorActividad(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltroGestorPorActividad(solicitudCajaCHBean);
    }

    public List<SolicitudCajaCHBean> obtenerSolicitudPorFiltroGestorPorActividadAdmi(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        return solicitudCajaCHDAO.obtenerSolicitudPorFiltroGestorPorActividadAdmi(solicitudCajaCHBean);
    }

    public void modificarDetSolicitudCrAnulacion(String uniNeg, Integer idSolicitudCajaCh, String modiPor) throws Exception {
        solicitudCajaCHDAO.modificarDetSolicitudCrAnulacion(uniNeg, idSolicitudCajaCh, modiPor);
    }

    public void modificarSolicitudAnulacion(String uniNeg, Integer idSolicitudCajaCh, String modiPor, String motivo) throws Exception {
        solicitudCajaCHDAO.modificarSolicitudAnulacion(uniNeg, idSolicitudCajaCh, modiPor, motivo);
    }

    public String obtenerDetSolcitudCajaChCRNotaCredito(Integer idSolicitudCajaCh, String uniNeg) throws Exception {
        return solicitudCajaCHDAO.obtenerDetSolcitudCajaChCRNotaCredito(idSolicitudCajaCh, uniNeg);
    }

    public void modificarDetalleCrValorNoC(Double valor, Integer idSolicitudCajaCh, String uniNeg, Integer cr) throws Exception {
        solicitudCajaCHDAO.modificarDetalleCrValorNoC(valor, idSolicitudCajaCh, uniNeg, cr);
    }

    public void modificarTipoSol(SolicitudCajaCHBean solicitudCajaCHBean) throws Exception {
        solicitudCajaCHDAO.modificarTipoSol(solicitudCajaCHBean);
    }
 
    
} 
