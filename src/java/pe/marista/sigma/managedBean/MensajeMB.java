package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DetRegistroCompraBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.RegistroCompraBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.SolicitudLogisticoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DetRegistroCompraService;
import pe.marista.sigma.service.MensajeService;
import pe.marista.sigma.service.RegistroCompraService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.SolicitudLogisticoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class MensajeMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of MensajeMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CodigoService codigoService = BeanFactory.getCodigoService();
//            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            getListaTipoPrioridadBean();
            listaTipoPrioridadBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PRIORIDAD));

            getMensajeFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaActual = new GregorianCalendar();
            getMensajeFiltroBean().setFechaInicio(fechaActual.getTime());
            getMensajeFiltroBean().setFechaFin(fechaActual.getTime());
            getMensajeFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());

//            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorIdSoli(solicitudLogisticoBean.getPersonalBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private MensajeBean mensajeBean;
    private MensajeBean mensajeFiltroBean;
    private List<MensajeBean> listaMensajeBean;
    private List<MensajeBean> listaMensajeRecibidosBean;
    private List<MensajeBean> listaMensajeAtendidosBean;
    private List<MensajeBean> listaMensajePapeleraBean;
    private List<CodigoBean> listaTipoPrioridadBean;
    private UsuarioBean usuarioLogin;
    private Calendar fechaActual;
    private Boolean flgAutorizado;

    //Listas de las tablas de mensaje para Aprobarlas
    private List<SolicitudLogisticoBean> listaSolicitudLogisticoBean;
    private List<FacturaCompraBean> listaFacturaBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaChicaBean;
    private SolicitudLogisticoBean solicitudLogisticoBean;
    private FacturaCompraBean facturaCompraBean;
    private SolicitudCajaCHBean solicitudCajaCHBean;
    private List<MensajeBean> listaMisMensajesBean;
    private List<MensajeBean> listaMensajesGeneralBean;
    private Boolean flgSolicitudPagada=false;
//    private SolicitudCajaCHBean solicitudCajaCHBean;

    //Logica de Negocio y Aplicacion
//    public void obtenerMensajePorOwner() {
//        try {
//            MensajeService mensajeService = BeanFactory.getMensajeService();
//            getMensajeBean();
//            mensajeBean.setPersonalBean(usuarioLogin.getPersonalBean());
//            mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//            listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwner(mensajeBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerFiltroMensaje(String statusMsj) {
        try {
            int estado = 0;
            MensajeService mensajeService = BeanFactory.getMensajeService();
            if (mensajeFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(mensajeFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                mensajeFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (mensajeFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(mensajeFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                mensajeFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (mensajeFiltroBean.getIdObjeto() != null && !mensajeFiltroBean.getIdObjeto().equals(0)) {
                mensajeFiltroBean.setIdObjeto(mensajeFiltroBean.getIdObjeto());
                estado = 1;
            }
            if (mensajeFiltroBean.getTipoStatusMensajeBean().getIdCodigo() != null && !mensajeFiltroBean.getTipoStatusMensajeBean().getIdCodigo().equals(0)) {
                mensajeFiltroBean.getTipoStatusMensajeBean().setIdCodigo(mensajeFiltroBean.getTipoStatusMensajeBean().getIdCodigo());
                estado = 1;
            }
            if (mensajeFiltroBean.getTipoPrioridad().getIdCodigo() != null && !mensajeFiltroBean.getTipoPrioridad().getIdCodigo().equals(0)) {
                mensajeFiltroBean.getTipoPrioridad().setIdCodigo(mensajeFiltroBean.getTipoPrioridad().getIdCodigo());
                estado = 1;
            }
            if (mensajeFiltroBean.getFlgAutoriza() != null) {
                mensajeFiltroBean.setFlgAutoriza(mensajeFiltroBean.getFlgAutoriza());
                estado = 1;
            }
            if (mensajeFiltroBean.getAsunto() != null && !mensajeFiltroBean.getAsunto().equals("")) {
                mensajeFiltroBean.setAsunto(mensajeFiltroBean.getAsunto().toUpperCase().trim());
                estado = 1;
            }
            if (mensajeFiltroBean.getSolicitanteBean().getApepat() != null && !mensajeFiltroBean.getSolicitanteBean().getApepat().equals("")) {
                mensajeFiltroBean.getSolicitanteBean().setApepat(mensajeFiltroBean.getSolicitanteBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (mensajeFiltroBean.getSolicitanteBean().getApemat() != null && !mensajeFiltroBean.getSolicitanteBean().getApemat().equals("")) {
                mensajeFiltroBean.getSolicitanteBean().setApemat(mensajeFiltroBean.getSolicitanteBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (mensajeFiltroBean.getSolicitanteBean().getNombre() != null && !mensajeFiltroBean.getSolicitanteBean().getNombre().equals("")) {
                mensajeFiltroBean.getSolicitanteBean().setNombre(mensajeFiltroBean.getSolicitanteBean().getNombre().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                if (statusMsj.equals("atendidos")) {
                    listaMensajeAtendidosBean = new ArrayList<>();
                }
            }
            if (estado == 1) {
                if (statusMsj.equals("atendidos")) {
                    mensajeFiltroBean.getTipoStatusMensajeBean().setCodigo(MaristaConstantes.COD_ATENTIDO);
                    listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                    if (listaMensajeAtendidosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMensajePorOwnerRecibidos() {
        try {
            MensajeService mensajeService = BeanFactory.getMensajeService();
            SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
            getMensajeBean();
            mensajeBean.setPersonalBean(usuarioLogin.getPersonalBean());
            mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
            listaMisMensajesBean = mensajeService.obtenerTodosLosMensajesPorPersonal(mensajeBean.getPersonalBean());
            listaMensajesGeneralBean = mensajeService.obtenerTodosLosMensajes(mensajeBean.getPersonalBean());
            listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorIdSoli(usuarioLogin.getPersonalBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarMensajeFiltro() {

        mensajeFiltroBean = new MensajeBean();
        listaMensajeAtendidosBean = new ArrayList<>();
        getMensajeFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        fechaActual = new GregorianCalendar();
        getMensajeFiltroBean().setFechaInicio(fechaActual.getTime());
        getMensajeFiltroBean().setFechaFin(fechaActual.getTime());
        getMensajeFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
    }

    public String autorizarMensajeSolCajaCh(String auto) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                if (mensajeBean.getTipoStatusMensajeBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE_MSJE)) {

                SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
                MensajeService mensajeService = BeanFactory.getMensajeService();
                int estado = 1;
                System.out.println("autorizarMensajeSolCajaCh(String auto) getMensajeBean().getNivelAutoriza()..." + getMensajeBean().getNivelAutoriza());
                System.out.println("autorizarMensajeSolCajaCh(String auto) getMensajeBean().getNivelAutoriza()..." + getMensajeBean().getIdMensaje());
                if (getMensajeBean().getNivelAutoriza() == 1 && solicitudCajaCHMB.getSolicitudCajaCHBean().getFlgAutoriza2() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                } else if (getMensajeBean().getNivelAutoriza() == 2 && solicitudCajaCHMB.getSolicitudCajaCHBean().getFlgAutoriza3() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                }
                if (estado == 1 && solicitudCajaCHMB.getSolicitudCajaCHBean().getMontoAprobado() != null) {
                    if (auto.equals("autoriza")) {
                        mensajeBean.setFlgAutoriza(true);
                    } else {
                        mensajeBean.setFlgAutoriza(false);
                        solicitudCajaCHMB.getSolicitudCajaCHBean().setMontoAprobado(new Double(0));
                    }
                    Integer est = 0;
                    Integer crMonto0 = 1;
                    if (solicitudCajaCHMB.getSolicitudCajaCHBean().getListaDetSolicitudCajaChCRBean().size() < 0) {
                        if (mensajeBean.getFlgAutoriza() == true) {
                            Double sum = 0d;
                            for (DetSolicitudCajaChCRBean detSolicitudCajaChCRBean : solicitudCajaCHMB.getSolicitudCajaCHBean().getListaDetSolicitudCajaChCRBean()) {
                                if (detSolicitudCajaChCRBean.getValorD().equals(0.0)) {
                                    crMonto0 = 0;
                                }
                                sum = sum + (double) Math.rint(detSolicitudCajaChCRBean.getValorD() * 100D) / 100D;
                            }
                            double rounded = (double) Math.round(sum * 100D) / 100D;
                            if (Objects.equals(rounded, solicitudCajaCHMB.getSolicitudCajaCHBean().getMontoAprobado())) {
                                est = 1;
                            }
                        } else {
                            est = 1;
                            crMonto0 = 1;
                        }
                        if (est.equals(1)) {
                            if (crMonto0.equals(1)) {
                                mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                                mensajeService.autorizarCompleto(mensajeBean, solicitudCajaCHMB);
//                mensajeService.autorizarMensajeSolCajaCh(mensajeBean);
                                listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
                                listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else {
                                new MensajePrime().addInformativeMessagePer("etiquetaMontoCeroDist");
                                System.out.println("monto cero");
                            }
                        } else {
                            new MensajePrime().addInformativeMessagePer("msjSumDistPer");
                            System.out.println("000000000000000000000000");
                        }
                    } else {
                        mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                        mensajeService.autorizarCompleto(mensajeBean, solicitudCajaCHMB);
                        System.out.println("no hay centro de responsabilidad :D");
                        listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
                        listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    }

                }
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionAnulada", false);
//                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String autorizarMensajeRegistroCompra(String auto) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                if (mensajeBean.getTipoStatusMensajeBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE_MSJE)) {

                RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
                MensajeService mensajeService = BeanFactory.getMensajeService();
                int estado = 1;
                if (mensajeBean.getNivelAutoriza() == 1 && registroCompraMB.getRegistroCompraBean().getFlgAutoriza2() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                } else if (mensajeBean.getNivelAutoriza() == 2 && registroCompraMB.getRegistroCompraBean().getFlgAutoriza3() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                }
                if (auto.equals("autoriza")) {
                    mensajeBean.setFlgAutoriza(true);
                } else {
                    mensajeBean.setFlgAutoriza(false);
                }
                mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                mensajeService.autorizarCompletoRC(mensajeBean, registroCompraMB);
                listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
                listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionAnulada", false);
//                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String autorizarMensajeFacturaCompra(String auto) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                if (mensajeBean.getTipoStatusMensajeBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE_MSJE)) {

                RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
                MensajeService mensajeService = BeanFactory.getMensajeService();
                int estado = 1;
                if (mensajeBean.getNivelAutoriza() == 1 && registroCompraMB.getFacturaCompraBean().getFlgAutoriza2() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                } else if (mensajeBean.getNivelAutoriza() == 2 && registroCompraMB.getFacturaCompraBean().getFlgAutoriza3() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                }
                if (auto.equals("autoriza")) {
                    mensajeBean.setFlgAutoriza(true);
                } else {
                    mensajeBean.setFlgAutoriza(false);
                }
                mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                mensajeService.autorizarCompletoFC(mensajeBean, registroCompraMB);
                listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
                listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionAnulada", false);
//                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String autorizarMensajeRequerimiento(String auto) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                if (mensajeBean.getTipoStatusMensajeBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE_MSJE)) {

                SolicitudLogisticoMB solicitudLogisticoMB = (SolicitudLogisticoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudLogisticoMB");
                MensajeService mensajeService = BeanFactory.getMensajeService();
                int estado = 1;
                if (mensajeBean.getNivelAutoriza() == 1 && solicitudLogisticoMB.getSolicitudLogisticoBean().getFlgAutoriza2() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                } else if (mensajeBean.getNivelAutoriza() == 2 && solicitudLogisticoMB.getSolicitudLogisticoBean().getFlgAutoriza3() != null) {
                    estado = 0;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionNoModi", true);
                }
                if (auto.equals("autoriza")) {
                    mensajeBean.setFlgAutoriza(true);
                } else {
                    mensajeBean.setFlgAutoriza(false);
                }
                mensajeBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                mensajeService.autorizarCompletoLog(mensajeBean, solicitudLogisticoMB);
                listaMensajeRecibidosBean = mensajeService.obtenerMensajePorOwnerRecibidos(mensajeBean);
                listaMensajeAtendidosBean = mensajeService.obtenerMensajePorFiltro(mensajeFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionAnulada", false);
//                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerMensajePorId(Object objeto) {
        try { 
            mensajeBean = (MensajeBean) objeto;
            MensajeService mensajeService = BeanFactory.getMensajeService();
            mensajeBean = mensajeService.obtenerMensajePorId(mensajeBean);
            ponerMensajeEnSolicitud();
 
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerMensajeEnSolicitud() {
        try {
            if (mensajeBean.getObjeto() != null && mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_CAJACH)) {
                MensajeMB mensajeMB = (MensajeMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mensajeMB");
                mensajeMB.setMensajeBean(mensajeBean);
                CodigoService codigoService = BeanFactory.getCodigoService();
                SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
                List<CodigoBean> lista = new ArrayList<>();
                lista = codigoService.obtenerCodigoDocEgreso();
                solicitudCajaCHMB.setListaTipoPagoBean(lista);
                System.out.println("llegp sol. caja ch");
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                SolicitudCajaCHBean solCajaCHBean2 = new SolicitudCajaCHBean();
                solCajaCHBean2.setIdSolicitudCajaCh(mensajeBean.getIdObjeto());
                solCajaCHBean2.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());

                solCajaCHBean2 = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(solCajaCHBean2);

                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCR(solCajaCHBean2.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    if (cod.getIdCodigo() != null) {
                        solCajaCHBean2.setCodDistribucion(cod.getIdCodigo());
                    } else {
                        System.out.println("null xds");
                    }

                }

                solicitudCajaCHMB.obtenerSolicitudCajaChPorIdAutorizacion(solCajaCHBean2);

                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudCajaCHMB", solicitudCajaCHMB);
                System.out.println("ok");
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaCajaCh", true);
                if(solCajaCHBean2.getTipoStatusSolCajaChBean().getIdCodigo().equals(MaristaConstantes.solicitud_Pagada)){
                    flgSolicitudPagada=true;
                } else {
                    flgSolicitudPagada=false;
                }
            }
            if (mensajeBean.getObjeto() != null && mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_REG_COMPRA)) {
                MensajeMB mensajeMB = (MensajeMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mensajeMB");
                mensajeMB.setMensajeBean(mensajeBean);
                RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
                System.out.println("llegá sol. reg. compra");
                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
                RegistroCompraBean registroCompraBean2 = new RegistroCompraBean();
                registroCompraBean2.setIdRegistroCompra(mensajeBean.getIdObjeto());
                registroCompraBean2.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
                registroCompraBean2 = registroCompraService.obtenerPorId(registroCompraBean2);
                registroCompraMB.setRegistroCompraBean(registroCompraBean2);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("registroCompraMB", registroCompraMB);
                System.out.println("ok");
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaSolLog", true);
               
            }
            if (mensajeBean.getObjeto() != null && mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_LOG)) {
                MensajeMB mensajeMB = (MensajeMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mensajeMB");
                mensajeMB.setMensajeBean(mensajeBean);
                SolicitudLogisticoMB solicitudLogisticoMB = (SolicitudLogisticoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudLogisticoMB");
                System.out.println("llego sol. reg");
//                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
//                SolicitudLogisticoDetalleService solicitudLogisticoDetalleService = BeanFactory.getSolicitudLogisticoDetalleService();
                SolicitudLogisticoBean solicitudLogisticoBean2 = new SolicitudLogisticoBean();
                solicitudLogisticoBean2.setIdRequerimiento(mensajeBean.getIdObjeto());
                solicitudLogisticoBean2.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
//                solicitudLogisticoBean2 = solicitudLogisticoService.obtenerP1orId(solicitudLogisticoBean2.getIdRequerimiento());
                solicitudLogisticoMB.obtenerPorIdAprobacion(solicitudLogisticoBean2);

//                solicitudLogisticoMB.setSolicitudLogisticoBean(solicitudLogisticoBean2);
//                List<SolicitudLogDetalleBean> listaDetSolLog = new ArrayList<>();
//                listaDetSolLog=solicitudLogisticoDetalleService.obtenerPorRequerimiento(solicitudLogisticoBean2.getIdRequerimiento());
//                solicitudLogisticoMB.setListaSolicitudLogDetalleBean(listaDetSolLog);
                
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudLogisticoMB", solicitudLogisticoMB);
                System.out.println("ok");
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaReq", true);
            }
            if (mensajeBean.getObjeto() != null && mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_FACT_COMPRA)) {
                MensajeMB mensajeMB = (MensajeMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("mensajeMB");
                mensajeMB.setMensajeBean(mensajeBean);
                CodigoService codigoService = BeanFactory.getCodigoService();
                RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
                List<CodigoBean> lista = new ArrayList<>();
                lista = codigoService.obtenerCodigoDocEgreso();
                registroCompraMB.setListaTipoPagoBean(lista);

                System.out.println("llegó factura compra");
                RegistroCompraService registroCompraService = BeanFactory.getRegistroCompraService();
                DetRegistroCompraService detRegistroCompraService = BeanFactory.getDetRegistroCompraService();
                FacturaCompraBean facturaCompraBean2 = new FacturaCompraBean();
                facturaCompraBean2.setIdFacturaCompra(mensajeBean.getIdObjeto());
                facturaCompraBean2.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
                facturaCompraBean2 = registroCompraService.obtenerPorIdFacturaVer2(facturaCompraBean2);
//                facturaCompraBean2 = registroCompraService.obtenerPorIdFactura(facturaCompraBean2);
                if (facturaCompraBean2.getIdTipoModoPago() == null) {
                    facturaCompraBean2.setIdTipoModoPago(MaristaConstantes.CODIGO_CHEQUE);
                    facturaCompraBean2.setCodModoPago(MaristaConstantes.COD_CHEQUE);
                }
                registroCompraMB.setFacturaCompraBean(facturaCompraBean2);
                List<DetRegistroCompraBean> listaDetRegCom = new ArrayList<>();
                listaDetRegCom = detRegistroCompraService.obtenerPorFactura(facturaCompraBean2);
                registroCompraMB.setListaDetalleRegistroCompraBean(listaDetRegCom);
                registroCompraMB.cargarDatosCrAprobacion(facturaCompraBean2.getIdFacturaCompra());
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("registroCompraMB", registroCompraMB);
                
//                if(facturaCompraBean2.getTipoStatusFacturaBean().getIdCodigo().equals(MaristaConstantes.solicitud_Pagada_Log)){
//                    flgSolicitudPagada=true;
//                } else {
//                    flgSolicitudPagada=false;
//                }
                System.out.println("ok");
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaFact", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Filtro de todas las Solicitudes Aceptadas
    public void ObtenerSolicitudesAprobadas() {
        try {

            if (mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_LOG)) {
                SolicitudLogisticoService solicitudLogisticoService = BeanFactory.getSolicitudLogisticoService();
                SolicitudLogisticoBean solBean = new SolicitudLogisticoBean();

//                solBean.setIdRequerimiento(mensajeBean.getIdObjeto());
                solBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                solBean.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
                listaSolicitudLogisticoBean = solicitudLogisticoService.obtenerPorIdSoli(solBean.getPersonalBean());

            }
            if (mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_FACT_COMPRA)) {
                RegistroCompraService registroService = BeanFactory.getRegistroCompraService();
                FacturaCompraBean factBean = new FacturaCompraBean();

//                factBean.setIdFacturaCompra(mensajeBean.getIdObjeto());
                factBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                factBean.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
                listaFacturaBean = registroService.obtenerPorIdFact(factBean);
            }
            if (mensajeBean.getObjeto().equals(MaristaConstantes.OBJ_SOL_CAJACH)) {
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                SolicitudCajaCHBean cajaBean = new SolicitudCajaCHBean();

//                cajaBean.setIdSolicitudCajaCh(mensajeBean.getIdObjeto());
                cajaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                cajaBean.getUnidadNegocioBean().setUniNeg(mensajeBean.getUnidadNegocioBean().getUniNeg());
                listaSolicitudCajaChicaBean = solicitudCajaCHService.obtenerPorIdCaja(cajaBean.getPersonalBean());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //GEtter y Setter
    public MensajeBean getMensajeBean() {
        if (mensajeBean == null) {
            mensajeBean = new MensajeBean();
        }
        return mensajeBean;
    }

    public void setMensajeBean(MensajeBean mensajeBean) {
        this.mensajeBean = mensajeBean;
    }

    public List<MensajeBean> getListaMensajeBean() {
        if (listaMensajeBean == null) {
            listaMensajeBean = new ArrayList<>();
        }
        return listaMensajeBean;
    }

    public void setListaMensajeBean(List<MensajeBean> listaMensajeBean) {
        this.listaMensajeBean = listaMensajeBean;
    }

    public List<MensajeBean> getListaMensajeRecibidosBean() {
        if (listaMensajeRecibidosBean == null) {
            listaMensajeRecibidosBean = new ArrayList<>();
        }
        return listaMensajeRecibidosBean;
    }

    public void setListaMensajeRecibidosBean(List<MensajeBean> listaMensajeRecibidosBean) {
        this.listaMensajeRecibidosBean = listaMensajeRecibidosBean;
    }

    public List<MensajeBean> getListaMensajeAtendidosBean() {
        if (listaMensajeAtendidosBean == null) {
            listaMensajeAtendidosBean = new ArrayList<>();
        }
        return listaMensajeAtendidosBean;
    }

    public void setListaMensajeAtendidosBean(List<MensajeBean> listaMensajeAtendidosBean) {
        this.listaMensajeAtendidosBean = listaMensajeAtendidosBean;
    }

    public List<MensajeBean> getListaMensajePapeleraBean() {
        if (listaMensajePapeleraBean == null) {
            listaMensajePapeleraBean = new ArrayList<>();
        }
        return listaMensajePapeleraBean;
    }

    public void setListaMensajePapeleraBean(List<MensajeBean> listaMensajePapeleraBean) {
        this.listaMensajePapeleraBean = listaMensajePapeleraBean;
    }

    public MensajeBean getMensajeFiltroBean() {
        if (mensajeFiltroBean == null) {
            mensajeFiltroBean = new MensajeBean();
        }
        return mensajeFiltroBean;
    }

    public void setMensajeFiltroBean(MensajeBean mensajeFiltroBean) {
        this.mensajeFiltroBean = mensajeFiltroBean;
    }

    public List<CodigoBean> getListaTipoPrioridadBean() {
        if (listaTipoPrioridadBean == null) {
            listaTipoPrioridadBean = new ArrayList<>();
        }
        return listaTipoPrioridadBean;
    }

    public void setListaTipoPrioridadBean(List<CodigoBean> listaTipoPrioridadBean) {
        this.listaTipoPrioridadBean = listaTipoPrioridadBean;
    }

    public Boolean getFlgAutorizado() {
        return flgAutorizado;
    }

    public void setFlgAutorizado(Boolean flgAutorizado) {
        this.flgAutorizado = flgAutorizado;
    }

    public List<SolicitudLogisticoBean> getListaSolicitudLogisticoBean() {
        if (listaSolicitudLogisticoBean == null) {
            listaSolicitudLogisticoBean = new ArrayList<>();
        }
        return listaSolicitudLogisticoBean;
    }

    public void setListaSolicitudLogisticoBean(List<SolicitudLogisticoBean> listaSolicitudLogisticoBean) {
        this.listaSolicitudLogisticoBean = listaSolicitudLogisticoBean;
    }

    public List<FacturaCompraBean> getListaFacturaBean() {
        if (listaFacturaBean == null) {
            listaFacturaBean = new ArrayList<>();
        }
        return listaFacturaBean;
    }

    public void setListaFacturaBean(List<FacturaCompraBean> listaFacturaBean) {
        this.listaFacturaBean = listaFacturaBean;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudCajaChicaBean() {
        if (listaSolicitudCajaChicaBean == null) {
            listaSolicitudCajaChicaBean = new ArrayList<>();
        }
        return listaSolicitudCajaChicaBean;
    }

    public void setListaSolicitudCajaChicaBean(List<SolicitudCajaCHBean> listaSolicitudCajaChicaBean) {
        this.listaSolicitudCajaChicaBean = listaSolicitudCajaChicaBean;
    }

    public SolicitudLogisticoBean getSolicitudLogisticoBean() {
        if (solicitudLogisticoBean == null) {
            solicitudLogisticoBean = new SolicitudLogisticoBean();
        }
        return solicitudLogisticoBean;
    }

    public void setSolicitudLogisticoBean(SolicitudLogisticoBean solicitudLogisticoBean) {
        this.solicitudLogisticoBean = solicitudLogisticoBean;
    }

    public FacturaCompraBean getFacturaCompraBean() {
        if (facturaCompraBean == null) {
            facturaCompraBean = new FacturaCompraBean();
        }
        return facturaCompraBean;
    }

    public void setFacturaCompraBean(FacturaCompraBean facturaCompraBean) {
        this.facturaCompraBean = facturaCompraBean;
    }

    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if (solicitudCajaCHBean == null) {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
    }

    public List<MensajeBean> getListaMisMensajesBean() {
        if (listaMisMensajesBean == null) {
            listaMisMensajesBean = new ArrayList<>();
        }
        return listaMisMensajesBean;
    }

    public void setListaMisMensajesBean(List<MensajeBean> listaMisMensajesBean) {
        this.listaMisMensajesBean = listaMisMensajesBean;
    }

    public List<MensajeBean> getListaMensajesGeneralBean() {
        if (listaMensajesGeneralBean==null) {
            listaMensajesGeneralBean= new ArrayList<>();
        }
        return listaMensajesGeneralBean;
    }

    public void setListaMensajesGeneralBean(List<MensajeBean> listaMensajesGeneralBean) {
        this.listaMensajesGeneralBean = listaMensajesGeneralBean;
    }

    public Boolean getFlgSolicitudPagada() {
        return flgSolicitudPagada;
    }

    public void setFlgSolicitudPagada(Boolean flgSolicitudPagada) {
        this.flgSolicitudPagada = flgSolicitudPagada;
    }

}
