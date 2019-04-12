package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaMovBean;
import pe.marista.sigma.bean.CajaChicaSaldoBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.bean.reporte.CajaChMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCRRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCentroRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovSoliRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadSubRepBean;
import pe.marista.sigma.bean.reporte.CrDetalladitoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AsientoService;
import pe.marista.sigma.service.CajaChicaLiquidacionService;
import pe.marista.sigma.service.CajaChicaMovService;
import pe.marista.sigma.service.CajaChicaService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTCalculadoraCR;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class CajaChicaMovMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CajaChicaMovMB
     */
    @PostConstruct
    public void init() {
        try {

            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (!validarCajaChica()) {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("logOut");
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("errorCajero", null));
            } else {
                CajaChicaMovMB cajaChicaMovMB = (CajaChicaMovMB) new MaristaUtils().sesionObtenerObjeto("solicitud");
                new MaristaUtils().sesionColocarObjeto("solicitud", null);
                if (cajaChicaMovMB != null) {
                    setCajaChicaBean(cajaChicaMovMB.getCajaChicaBean());
//                    getCajaChicaMovBean().setSolicitudCajaCHBean(cajaChicaMovMB.getCajaChicaMovBean().getSolicitudCajaCHBean());
                    setCajaChicaMovBean(cajaChicaMovMB.getCajaChicaMovBean());
                    collapse = true;
                    listaCajaChicaMovBean = cajaChicaMovMB.getListaCajaChicaMovBean();

                }
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);
                CodigoService codigoService = BeanFactory.getCodigoService();
                getListTipoMoneda();
                listTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
                getCajaChicaMovBean().setAnio(2015);
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                VistaBean vista = new VistaBean();
                vista.getPerfilModuloBean().getPerfilBean().setNombre(MaristaConstantes.PER_CAJERO_CCH
                );
                vista.setUsuarioBean(usuarioLogin);
                listaCajeroBean = usuarioService.obtenerUsuarioPorPerfil(vista);
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                getListaCentroResponsabilidadBean();
                listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
                TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
                getTipoConceptoBean();
                listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConcepto();

                TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                getListaTipoSolicitudBean();
                listaTipoSolicitudBean = tipoSolicitudService.obtenerSolGenCajaCH(MaristaConstantes.TIP_SOL_GEN, MaristaConstantes.A_RENDIR, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private CajaChicaMovBean cajaChicaMovBean;
    private List<CajaChicaMovBean> listaCajaChicaMovBean;
    private List<TipoSolicitudBean> listaTipoSolicitudBean;
    private UsuarioBean usuarioLogin;
    private List<CajaChicaBean> listaCajaChicaBean;
    private CajaChicaBean cajaChicaBean;
    private SolicitudCajaCHBean solicitudCajaCHFiltroBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaCHFiltroBean;
    private List<CodigoBean> listTipoMoneda;
    private List<VistaBean> listaCajeroBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private TipoConceptoBean tipoConceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private CajaChicaSaldoBean cajaChicaSaldoBean;
    private Boolean collapse;
    private SolicitudCajaCHBean solicitudCajaCHBean;
//    private CajaCh
    private Boolean flgAnular;

    //Metodos Getter y Setter
    public void obtenerCajaChicaMov() {
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public boolean validarCajaChica() {
        try {
            PerfilService perfilService = BeanFactory.getPerfilService();
            PerfilBean perfilBean = new PerfilBean();
            perfilBean.setNombre(MaristaConstantes.PER_CAJERO_CCH);
            perfilBean = perfilService.obtenerPerfilPorNombre(perfilBean);
            VistaBean vistaBean = new VistaBean();
            if (perfilBean.getIdPerfil() != null) {
                vistaBean.setIdPerfil(perfilBean.getIdPerfil());
                vistaBean.setUsuario(usuarioLogin.getUsuario());
                List<VistaBean> listaVistaBean = new ArrayList<>();
                listaVistaBean = perfilService.validaPerfil(vistaBean);
                return !listaVistaBean.isEmpty();
            }

        } catch (Exception e) {
        }
        return true;
    }

    public void insertarCajaChicaMov() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                System.out.println("insertarCajaChicaMov");
                CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                cajaChicaMovBean.setCreaPor(usuarioLogin.getUsuario());
                cajaChicaMovBean.setCajaChicaBean(cajaChicaBean);
                cajaChicaMovService.insertarCajaChicaMov(cajaChicaMovBean);
                cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
                listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
                limpiarCajaChicaMovBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificarCajaChicaMov() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                System.out.println("modificarCajaChicaMov");
                CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
                cajaChicaMovBean.setModiPor(usuarioLogin.getUsuario());
                cajaChicaMovBean.setCajaChicaBean(cajaChicaBean);
                cajaChicaMovService.modificarCajaChicaMov(cajaChicaMovBean);
                listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarCajaChicaMov() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
                cajaChicaMovService.eliminarCajaChicaMov(cajaChicaMovBean);
                listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCajaChicaMovPorId(Object objeto) {
        try {
            cajaChicaMovBean = (CajaChicaMovBean) objeto;
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorId(cajaChicaMovBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cajaChicaMovBean = (CajaChicaMovBean) event.getObject();
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorId(cajaChicaMovBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerSolicitud(Object objeto) {
        try {
            System.out.println("ponerSolicitud");
            boolean comodin = true;
            List<DetSolicitudCajaChCRBean> lista = new ArrayList<>();
            SolicitudCajaCHBean bean = (SolicitudCajaCHBean) objeto;
            lista = bean.getListaDetSolicitudCajaChCRBean();
            SolicitudCajaCHService cHService = BeanFactory.getSolicitudCajaCHService();
            bean = cHService.obtenerSolicitudCajaCHBeanPorId(bean);
            if (lista != null) {
                bean.setListaDetSolicitudCajaChCRBean(lista);
            }

            if (bean.getTipoMonedaBean().getCodigo() != null) {
                if (bean.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)) {
                    if (bean.getMontoAprobado() > cajaChicaBean.getMontoMaxMovSol()) {
                        comodin = false;
                    }
                }
                if (bean.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)) {
                    if (bean.getMontoAprobado() > cajaChicaBean.getMontoMaxMovDol()) {
                        comodin = false;
                    }
                }
            }

            if (comodin) {
                System.out.println("ponerSolicitud : comodin true");
//            cajaChicaMovBean.setFecOrden(new Date());
                if (bean.getNivelAutoriza() == 1) {
                    getCajaChicaMovBean().setFecOrden(bean.getFecAutoriza1());
                    if (getCajaChicaMovBean().getFecOrden() == null) {
                        getCajaChicaMovBean().setFecOrden(bean.getCreaFecha());
                    }
                } else if (bean.getNivelAutoriza() == 2) {
                    getCajaChicaMovBean().setFecOrden(bean.getFecAutoriza2());
                } else if (bean.getNivelAutoriza() == 3) {
                    getCajaChicaMovBean().setFecOrden(bean.getFecAutoriza3());
                }
                getCajaChicaMovBean().setSolicitudCajaCHBean(bean);
                getCajaChicaMovBean().setMonto(bean.getMontoAprobado());
                getCajaChicaMovBean().setTipoMonedaBean(bean.getTipoMonedaBean());
                getCajaChicaMovBean().setMotivo(bean.getMotivo());
                getCajaChicaMovBean().setFecPago(bean.getFechaSol());
            } else {
                System.out.println("ponerSolicitud : comodin false");
                new MensajePrime().addInformativeMessagePer("msjMontoMaxSolicitud");
            }

            for (DetSolicitudCajaChCRBean lista2 : cajaChicaMovBean.getSolicitudCajaCHBean().getListaDetSolicitudCajaChCRBean()) {
                System.out.println("lista2..." + lista2.getCentroResponsabilidadBean().getCr() + "..." + lista2.getValorD());
            }

//            cajaChicaMovBean.setFecOrden(bean.getFechaSol());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajaChicaMovBean() {
        cajaChicaMovBean = new CajaChicaMovBean();
    }

    public void guardarCajaChicaMov() {
        try {
            if (validarSaldo(getCajaChicaBean(), getCajaChicaMovBean().getTipoMonedaBean().getCodigo(), getCajaChicaMovBean().getMonto())) {
                if (cajaChicaMovBean.getIdCajaChicaMov() == null) {
                    insertarCajaChicaMov();
                } else {
                    modificarCajaChicaMov();
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjExcedeSaldo");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public boolean obtenerSaldo() {
//        try {
//            for (CajaChicaMovBean listaCajaChicaMovBean1 : listaCajaChicaMovBean) {
//                if (listaCajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)) {
//                    if (validarSaldo(getCajaChicaBean().getUtilizadoSol() + listaCajaChicaMovBean1.getMonto(), getCajaChicaBean().getAperturaSol())) {
//                        getCajaChicaBean().setUtilizadoSol(getCajaChicaBean().getUtilizadoSol() + listaCajaChicaMovBean1.getMonto());
//                    } else {
////                        new MensajePrime().addInformativeMessagePer("msjExcedeSaldo");
//                        return false;
//                    }
//                }
//                if (listaCajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)) {
//                    getCajaChicaBean().setUtilizadoDol(getCajaChicaBean().getUtilizadoDol() + listaCajaChicaMovBean1.getMonto());
//                }
//            }
//            getCajaChicaBean().setSaldoSol(getCajaChicaBean().getAperturaSol() - getCajaChicaBean().getUtilizadoSol());
//            getCajaChicaBean().setSaldoDol(getCajaChicaBean().getAperturaDol() - getCajaChicaBean().getUtilizadoDol());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return true;
//    }
    public boolean validarSaldo(CajaChicaBean cajaChicaActual, String tipoMoneda, Double montoAprobado) {
        try {
            if (tipoMoneda != null) {
                if (tipoMoneda.equals(MaristaConstantes.PAGO_MON_SOL)) {
                    if (cajaChicaActual.getSaldoSol() > 0 && cajaChicaActual.getSaldoSol() >= montoAprobado) {
                        cajaChicaActual.setUtilizadoSol(cajaChicaActual.getUtilizadoSol() + montoAprobado);
                        cajaChicaActual.setSaldoSol(cajaChicaActual.getSaldoSol() - montoAprobado);
                        return true;
                    } else {
                        return false;
                    }
                }
                if (tipoMoneda.equals(MaristaConstantes.PAGO_MON_DOL)) {
                    if (cajaChicaActual.getSaldoDol() > 0 && cajaChicaActual.getSaldoDol() >= montoAprobado) {
                        cajaChicaActual.setUtilizadoDol(cajaChicaActual.getUtilizadoDol() + montoAprobado);
                        cajaChicaActual.setSaldoDol(cajaChicaActual.getSaldoDol() - montoAprobado);
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return false;
    }

    //Filtro
    public void limpiarSolicitudCajaCHFiltroBean() {
        solicitudCajaCHFiltroBean = new SolicitudCajaCHBean();
        listaSolicitudCajaCHFiltroBean = new ArrayList<>();
    }

    public void obtenerSolicitudPorFiltro() {
        try {
            solicitudCajaCHFiltroBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            if (solicitudCajaCHFiltroBean.getPersonalBean().getApepat() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getApepat().equals("")) {
                solicitudCajaCHFiltroBean.getPersonalBean().setApepat(solicitudCajaCHFiltroBean.getPersonalBean().getApepat().toUpperCase().trim());
            }
            if (solicitudCajaCHFiltroBean.getPersonalBean().getApemat() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getApemat().equals("")) {
                solicitudCajaCHFiltroBean.getPersonalBean().setApemat(solicitudCajaCHFiltroBean.getPersonalBean().getApemat().toUpperCase().trim());
            }
            if (solicitudCajaCHFiltroBean.getPersonalBean().getNombre() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getNombre().equals("")) {
                solicitudCajaCHFiltroBean.getPersonalBean().setNombre(solicitudCajaCHFiltroBean.getPersonalBean().getNombre().toUpperCase().trim());
            }
            if (solicitudCajaCHFiltroBean.getMotivo() != null && !solicitudCajaCHFiltroBean.getMotivo().equals("")) {
                solicitudCajaCHFiltroBean.setMotivo(solicitudCajaCHFiltroBean.getMotivo().toUpperCase().trim());
            }
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            solicitudCajaCHFiltroBean.setTipoStatusSolCajaChBean(new CodigoBean(MaristaConstantes.COD_SOL_AUTORIZADO));
            listaSolicitudCajaCHFiltroBean = solicitudCajaCHService.obtenerSolicitudPorFiltro(solicitudCajaCHFiltroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    public void obtenerConceptoPorTipo() {
//        try {
//            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
////            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//    public void obtenerCuentaConcepto() {
//        try {
//            for (int i = 0; i < listaConceptoUniNegBean.size(); i++) {
//                if (cajaChicaMovBean.getConceptoBean().getIdConcepto() != null
//                        && listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto().toString().equals(cajaChicaMovBean.getConceptoBean().getIdConcepto().toString())) {
////                    programacionBean.getAmbienteBean().setAforo(listaAmbienteBean.get(i).getAforo());
////                    programacionBean.getConceptoBean().setPlanContableCuentaDBean(listaConceptoUniNegBean.get(i).getConceptoBean().getPlanContableCuentaDBean());
//                    cajaChicaMovBean.setConceptoBean(listaConceptoUniNegBean.get(i).getConceptoBean());
////                    valor = 2;
//                }
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }

    public void abriCierreCaja() {
        fechCierre = new Date();
        montoSalidaSoles = 0d;
        montoEntradaSoles = 0d;

        for (CajaChicaMovBean cajaChicaMovBean1 : listaCajaChicaMovBean) {
            if (cajaChicaMovBean1.getFlgMov() == 0 && cajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals("Soles")) {
                montoSalidaSoles = montoSalidaSoles + cajaChicaMovBean1.getMonto();
            }
        }
        for (CajaChicaMovBean cajaChicaMovBean1 : listaCajaChicaMovBean) {
            if (cajaChicaMovBean1.getFlgMov() == 1 && cajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals("Soles")) {
                montoEntradaSoles = montoEntradaSoles + cajaChicaMovBean1.getMonto();
            }
        }
        totalSoles = cajaChicaBean.getAperturaSol() + montoEntradaSoles - montoSalidaSoles;

        montoSalidaDolares = 0d;
        montoEntradaDolares = 0d;
        for (CajaChicaMovBean cajaChicaMovBean1 : listaCajaChicaMovBean) {
            if (cajaChicaMovBean1.getFlgMov() == 0 && cajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals("Dolares")) {
                montoSalidaDolares = montoSalidaDolares + cajaChicaMovBean1.getMonto();
            }
        }
        for (CajaChicaMovBean cajaChicaMovBean1 : listaCajaChicaMovBean) {
            if (cajaChicaMovBean1.getFlgMov() == 1 && cajaChicaMovBean1.getTipoMonedaBean().getCodigo().equals("Dolares")) {
                montoEntradaDolares = montoEntradaDolares + cajaChicaMovBean1.getMonto();
            }
        }
        totalDolares = cajaChicaBean.getAperturaDol() + montoEntradaDolares - montoSalidaDolares;
    }
    private Date fechCierre;
    private Double montoSalidaSoles;
    private Double montoEntradaSoles;
    private Double montoSalidaDolares;
    private Double montoEntradaDolares;
    private Double totalSoles;
    private Double totalDolares;

    public void obtenerCajaChica() {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaBean = cajaChicaService.obtenerCajaChica(cajaChicaBean);

//            List<CajaChicaBean> listaAbierto = cajaChicaService.obtenerCajaChicaAbierto(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (listaAbierto.size() == 1) {
//                setCajaChicaBean(listaAbierto.get(0));
//                CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
//                CajaChicaMovBean cajaChicaMov = new CajaChicaMovBean();
//                cajaChicaMov.setCajaChicaBean(cajaChicaBean);
//                listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMov);
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void cerrarCajaChica() {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean.setFecCierre(fechCierre);
            cajaChicaService.cerrarCaja(cajaChicaBean);
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
            CajaChicaMB cajaChicaMB = (CajaChicaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMB");
            cajaChicaMB.obtenerCajaChica(null);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMB", cajaChicaMB);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    //Nuevo Cambios Caja Chica.
    public void limpiarBeanSolicitud() {
        try {
            getCajaChicaMovBean().setSolicitudCajaCHBean(new SolicitudCajaCHBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    //REportes
    public void imprimirCajaChicaMovPdf(String var) {
        ServletOutputStream out = null;
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = null;
            switch (var) {
                case "2":
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/repCierreCajaChica.jasper");
                    System.out.println("cierre movimientos");
                    break;
                case "1":
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/repCajaChicaMov.jasper");
                    System.out.println("movimientos");
                    break;
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaChicaMovRepBean> listaCajaChicaMovRepBean = new ArrayList<>();
//            cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaMovRepBean = cajaChicaMovService.obtenerCajaChicaMovRep(cajaChicaBean);
            CajaChicaMovRepBean cajaChicaMovRepBean = new CajaChicaMovRepBean();
            if (listaCajaChicaMovRepBean == null || listaCajaChicaMovRepBean.isEmpty()) {
                cajaChicaMovRepBean.setFecApertura(cajaChicaBean.getFecApertura());
                cajaChicaMovRepBean.setFecCierre(cajaChicaBean.getFecCierre());
                cajaChicaMovRepBean.setNombreCompletoCajero(cajaChicaBean.getPersonalBean().getNombreCompleto());
                cajaChicaMovRepBean.setAperturasol(cajaChicaBean.getAperturaSol());
                cajaChicaMovRepBean.setAperturadol(cajaChicaBean.getAperturaDol());
                cajaChicaMovRepBean.setMontoEntSoles(0d);
                cajaChicaMovRepBean.setMontoEntDolares(0d);
                cajaChicaMovRepBean.setMontoSalSoles(0d);
                cajaChicaMovRepBean.setMontoSalDolares(0d);
                cajaChicaMovRepBean.setDevueltoSol(0d);
                cajaChicaMovRepBean.setDevueltoDol(0d);
                cajaChicaMovRepBean.setUtilizadosol(0d);
                cajaChicaMovRepBean.setUtilizadodol(0d);
                cajaChicaMovRepBean.setSaldosol(cajaChicaBean.getSaldoSol());
                cajaChicaMovRepBean.setSaldoDol(cajaChicaBean.getSaldoDol());
                cajaChicaMovRepBean.setCodigo(cajaChicaBean.getUnidadNegocioBean().getCodigoBean().getCodigo());
                cajaChicaMovRepBean.setNombreUniNeg(cajaChicaBean.getUnidadNegocioBean().getNombreUniNeg());
                System.out.println("co: " + cajaChicaBean.getUnidadNegocioBean().getCodigoBean().getCodigo());
                System.out.println("nom: " + cajaChicaBean.getUnidadNegocioBean().getNombreUniNeg());
                listaCajaChicaMovRepBean = new ArrayList<>();
                listaCajaChicaMovRepBean.add(cajaChicaMovRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaChicaMovRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirCajaChicaMovPdfFormato(String var) {
        ServletOutputStream out = null;
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = null;
            switch (var) {
                case "1":
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/repCierreCajaChicaFormato.jasper");
                    System.out.println("movimientos");
                    break;
                case "2":
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/repCierreCajaChicaFormato.jasper");
                    System.out.println("cierre movimientos");
                    break;

            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaChMovRepBean> listaCajaChicaMovRepBean = new ArrayList<>();
//            cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaMovRepBean = cajaChicaMovService.obtenerCajaChicaMovRepNewFor(cajaChicaBean);
            if (!listaCajaChicaMovRepBean.isEmpty()) {
                for (int j = 0; j < listaCajaChicaMovRepBean.size(); j++) {
                    List<CajaChMovRepBean> listaDet = new ArrayList<>();
                    listaDet = cajaChicaMovService.obtenerCajaChicaDetalle(cajaChicaBean.getUnidadNegocioBean().getUniNeg()
                            ,listaCajaChicaMovRepBean.get(j).getIdSolicitudCajaCH());
                    listaCajaChicaMovRepBean.get(j).setListaDetalle(listaDet);
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaChicaMovRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirMovimientosPorCr() {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCajaChicaDetalle.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaChicaMovCRRepBean> listaCabeza = new ArrayList<>();
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer idCaja = cajaChicaBean.getIdCajaChica();
            listaCabeza = cajaChicaMovService.obtenerCajaChicaCentros(uniNeg, idCaja);
            if (!listaCabeza.isEmpty()) {
                List<CajaChicaMovSoliRepBean> listaMovimientosCaja = new ArrayList<>();
                listaMovimientosCaja = cajaChicaMovService.obtenerCajaChicaSoliCentros(uniNeg, idCaja);
                listaCabeza.get(0).setListaMovimientosCaja(listaMovimientosCaja);
                if (!listaMovimientosCaja.isEmpty()) {
                    for (int j = 0; j < listaCabeza.get(0).getListaMovimientosCaja().getData().size(); j++) {
                        List<CajaChicaMovCentroRepBean> listaCentros = new ArrayList<>();
                        listaCentros = cajaChicaMovService.obtenerCajaChicaCRCentros(uniNeg, listaMovimientosCaja.get(j).getIdSolicitudCajaCh());
                        listaMovimientosCaja.get(j).setListaCentros(listaCentros);
                        listaCabeza.get(0).setListaMovimientosCaja(listaMovimientosCaja);
                    }
                }
                List<CrDetalladitoRepBean> listaDetalladito = new ArrayList<>();
                listaDetalladito = cajaChicaMovService.obtenerCRDetalladito(uniNeg, idCaja);
                listaCabeza.get(0).setListaCRDetalladito(listaDetalladito);
            }

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirMovimientosMovilidadCajaCH() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCajaCHMovilidad.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaChicaMovilidadRepBean> listaCabeza = new ArrayList<>();
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer idCaja = cajaChicaBean.getIdCajaChica();
            listaCabeza = cajaChicaMovService.obtenerCajaChicaMovCabecera(uniNeg, idCaja);
            //recorrido de la cabecera con el detalle
            if (!listaCabeza.isEmpty()) {
                for (int j = 0; j < listaCabeza.size(); j++) {
                    List<CajaChicaMovilidadSubRepBean> listaDet = new ArrayList<>();
                    listaDet = cajaChicaMovService.obtenerCajaChicaMovDetalle(uniNeg, listaCabeza.get(j).getIdpersonal(), idCaja);
                    listaCabeza.get(j).setListaDetalle(listaDet);

                }
            }

            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeza);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            System.out.println("Ruta:" + ruta);
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    //Reposicion Caja Chica
    public void prepRepoCajaChi() {
        try {
            if (cajaChicaBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh() == null) {
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
                conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_REP_CAJA_CH);
                conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);
                getSolicitudCajaCHBean().setConceptoUniNegBean(conceptoUniNegBean);
                solicitudCajaCHBean.setPersonalBean(cajaChicaBean.getPersonalCajeroBean());
                solicitudCajaCHBean.getPersonaBean().setNombreCompleto(cajaChicaBean.getPersonalCajeroBean().getNombreCompleto());
                solicitudCajaCHBean.setNombreSolicitante(cajaChicaBean.getPersonalCajeroBean().getNombreCompleto());

                solicitudCajaCHBean.setNomRespCheque(cajaChicaBean.getPersonalCajeroBean().getNombreCompleto());
                solicitudCajaCHBean.setIdRespCheque(cajaChicaBean.getPersonalCajeroBean().getIdPersonal().toString());
                solicitudCajaCHBean.setIdTipoRespCheque("COL");
                UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
                UnidadOrganicaBean uob = unidadOrganicaService.obtenerUnidadOrganicaPorId(solicitudCajaCHBean.getPersonalBean().getUnidadOrganicaBean());
                if (uob != null) {
                    solicitudCajaCHBean.getPersonalBean().setUnidadOrganicaBean(uob);
                }
                solicitudCajaCHBean.setFechaSol(new Date());
                if (cajaChicaBean.getAperturaSol() >= 0) {
//                    solicitudCajaCHBean.setMonto(cajaChicaBean.getAperturaSol() - cajaChicaBean.getSaldoSol());
                    if (cajaChicaBean.getDevueltoSol() == null) {
                        cajaChicaBean.setDevueltoSol(new Double(0.0));
                    }
                    solicitudCajaCHBean.setMonto(cajaChicaBean.getUtilizadoSol() - cajaChicaBean.getDevueltoSol());
                }
                if (cajaChicaBean.getAperturaDol() >= 0) {
//                    solicitudCajaCHBean.setMonto2(cajaChicaBean.getAperturaDol() - cajaChicaBean.getSaldoDol());
                    if (cajaChicaBean.getDevueltoDol() == null) {
                        cajaChicaBean.setDevueltoDol(new Double(0.0));
                    }
                    solicitudCajaCHBean.setMonto2(cajaChicaBean.getUtilizadoDol() - cajaChicaBean.getDevueltoDol());
                }
                solicitudCajaCHBean.setMotivo(MensajesBackEnd.getValueOfKey("etiquetaRepCajaCH", null));
            } else {
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                cajaChicaBean.getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(cajaChicaBean.getSolicitudCajaCHBean());
                solicitudCajaCHBean.getPersonalBean().setNombreCompleto(solicitudCajaCHBean.getNombreSolicitante());
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void pasarMovimiento(CajaChicaMovBean bean) {
        try {
            setCajaChicaMovBean(bean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh();
            System.out.println("id sol: " + bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
            System.out.println("monto Aprobado: " + bean.getMonto());
            System.out.println("moneda: " + bean.getCajaChicaBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo());
            System.out.println("id caja: " + bean.getCajaChicaBean().getIdCajaChica());
            System.out.println("id caja: " + getCajaChicaBean().getSaldoDol());
            System.out.println("id caja: " + getCajaChicaBean().getSaldoSol());
            System.out.println("id caja: " + getCajaChicaBean().getUtilizadoDol());
            System.out.println("id caja: " + getCajaChicaBean().getUtilizadoSol());
            System.out.println("aa: " + bean.getCajaChicaBean().getFecCierre());
            System.out.println("aa: " + bean.getCajaChicaBean().getFecCierreVista());
            System.out.println("b: " + getCajaChicaBean().getFecCierre());
            System.out.println("b: " + getCajaChicaBean().getFecCierreVista());
            System.out.println("sip");
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaMovService.obtenerCajaChicaMovPorId(bean);
            System.out.println("extorno: " + bean.getExtorno());
            if (bean.getExtorno() == null) {
                if (bean.getCajaChicaBean().getFecCierre() == null) {
                    this.flgAnular = true;
                } else {
                    this.flgAnular = false;
                }
            } else if (bean.getExtorno() == true) {
                this.flgAnular = false;
            } else if (bean.getExtorno() == false) {
                if (bean.getCajaChicaBean().getFecCierre() == null) {
                    this.flgAnular = true;
                } else {
                    this.flgAnular = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarMovimiento(CajaChicaMovBean bean) {
        try {
            System.out.println("id sol: " + bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
            System.out.println("monto Aprobado: " + bean.getMonto());
            System.out.println("moneda: " + bean.getCajaChicaBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo());
            System.out.println("id caja: " + bean.getCajaChicaBean().getIdCajaChica());
            System.out.println("saldo Dol: " + getCajaChicaBean().getSaldoDol());
            System.out.println("saldo sol: " + getCajaChicaBean().getSaldoSol());
            System.out.println("saldo sol: " + bean.getMotivo());
            System.out.println("utilizado dol: " + getCajaChicaBean().getUtilizadoDol());
            System.out.println("utilizado sol: " + getCajaChicaBean().getUtilizadoSol());
            //Modificar Caja Chica.
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            Double utilizado;
            Double saldo;
            if (bean.getCajaChicaBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                utilizado = getCajaChicaBean().getUtilizadoSol() - bean.getMonto();
                saldo = getCajaChicaBean().getSaldoSol() + bean.getMonto();
                cajaChicaService.modificarCajaChicaAnulacionSoles(usuarioLogin.getUsuario(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getCajaChicaBean().getIdCajaChica(), utilizado, saldo);
            } else {
                utilizado = getCajaChicaBean().getUtilizadoDol() - bean.getMonto();
                saldo = getCajaChicaBean().getSaldoDol() + bean.getMonto();
                cajaChicaService.modificarCajaChicaAnulacionDolares(usuarioLogin.getUsuario(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getCajaChicaBean().getIdCajaChica(), utilizado, saldo);
            }
            //Modificar Caja Chica Liquidacion
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            cajaChicaLiquidacionService.modificarCajaChicaLiquidacionAnulacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getUsuario());
            //Modificar  Caja Chica Mov
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaMovService.modificarCajaChicaMovAnulacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getUsuario(), bean.getMotivo());
            //Modificar Caja Chica Saldo
            cajaChicaService.modificarCajaChicaSaldoAnulacion(usuarioLogin.getUsuario(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getCajaChicaBean().getIdCajaChica(), saldo, bean.getCajaChicaBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo());
            //Modificar Solicitud Cr
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();

            solicitudCajaCHService.modificarDetSolicitudCrAnulacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getUsuario());
            //Modificar Solicitud Caja Chica
            solicitudCajaCHService.modificarSolicitudAnulacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLogin.getUsuario(), bean.getMotivo());
            //Modificar Asiento
            AsientoService asientoService = BeanFactory.getAsientoService();
            String objeto;
            objeto = "MT_CajaChicaMov";
            asientoService.modificarAsientoAnulacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), bean.getIdCajaChicaMov(), usuarioLogin.getUsuario(), objeto);
            listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorCCH(bean);
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
            setCajaChicaBean(cajaChicaBean);
            limpiar(cajaChicaBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiar(CajaChicaBean cajaChic) {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChic);
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            List<CajaChicaMovBean> listaCajaChicaMov = new ArrayList<>();
            CajaChicaMovBean cajaChicaMovBean = new CajaChicaMovBean();
            cajaChicaMovBean.setCajaChicaBean(cajaChicaBean);
            listaCajaChicaMov = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMovBean); 
            setCajaChicaBean(cajaChicaBean);
            setCajaChicaMovBean(new CajaChicaMovBean());
            setListaCajaChicaMovBean(listaCajaChicaMov);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void insertarReposicionCajaCH() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud() == null) {
                    TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                    TipoSolicitudBean tsb = new TipoSolicitudBean();
                    tsb.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    tsb.setNombre(MaristaConstantes.CONTRA_PAGO);
                    tsb = tipoSolicitudService.obtenerTipoSolicitudPorNombre(tsb);
                    solicitudCajaCHBean.setTipoSolicitudBean(tsb);

                }
                List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCR = new ArrayList<>();
                if(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)){
                for (CentroResponsabilidadBean cr : MaristaConstantes.LISTA_CR_ADM_SECTOR) {
                    DetSolicitudCajaChCRBean bean = new DetSolicitudCajaChCRBean();
                    bean.setCentroResponsabilidadBean(cr);
                    bean.getTipoDistribucion().setIdCodigo(MaristaConstantes.Id_Division);
                    listaDetSolicitudCajaChCR.add(bean);
                }
                }else{
                for (CentroResponsabilidadBean cr : MaristaConstantes.LISTA_CR_ADM) {
                    DetSolicitudCajaChCRBean bean = new DetSolicitudCajaChCRBean();
                    bean.setCentroResponsabilidadBean(cr);
                    bean.getTipoDistribucion().setIdCodigo(MaristaConstantes.Id_Division);
                    listaDetSolicitudCajaChCR.add(bean);
                }}
                new GLTCalculadoraCR().calcularPorDivisionDet(listaDetSolicitudCajaChCR, solicitudCajaCHBean.getMonto());
                solicitudCajaCHBean.setCodDistribucion(MaristaConstantes.Id_Division);
                solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolicitudCajaChCR);
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                cajaChicaBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                solicitudCajaCHBean.getPersonalBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
//                solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaCHService.insertarSolicitudCajaCH(solicitudCajaCHBean, "rep", null, cajaChicaBean);
                solicitudCajaCHBean = new SolicitudCajaCHBean();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
                cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarReposicionCajaCH() {
        solicitudCajaCHBean = new SolicitudCajaCHBean();
        prepRepoCajaChi();
    }
    //Getter y Setter

    public CajaChicaMovBean getCajaChicaMovBean() {
        if (cajaChicaMovBean == null) {
            cajaChicaMovBean = new CajaChicaMovBean();
        }
        return cajaChicaMovBean;
    }

    public void setCajaChicaMovBean(CajaChicaMovBean cajaChicaMovBean) {
        this.cajaChicaMovBean = cajaChicaMovBean;
    }

    public List<CajaChicaMovBean> getListaCajaChicaMovBean() {
        if (listaCajaChicaMovBean == null) {
            listaCajaChicaMovBean = new ArrayList<>();
        }
        return listaCajaChicaMovBean;
    }

    public void setListaCajaChicaMovBean(List<CajaChicaMovBean> listaCajaChicaMovBean) {
        this.listaCajaChicaMovBean = listaCajaChicaMovBean;
    }

    public UsuarioBean getUsuarioLogin() {
        if (usuarioLogin == null) {
            usuarioLogin = new UsuarioBean();
        }
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public List<CajaChicaBean> getListaCajaChicaBean() {
        if (listaCajaChicaBean == null) {
            listaCajaChicaBean = new ArrayList<>();
        }
        return listaCajaChicaBean;
    }

    public void setListaCajaChicaBean(List<CajaChicaBean> listaCajaChicaBean) {
        this.listaCajaChicaBean = listaCajaChicaBean;
    }

    public CajaChicaBean getCajaChicaBean() {
        if (cajaChicaBean == null) {
            cajaChicaBean = new CajaChicaBean();
        }
        return cajaChicaBean;
    }

    public void setCajaChicaBean(CajaChicaBean cajaChicaBean) {
        this.cajaChicaBean = cajaChicaBean;
    }

    public SolicitudCajaCHBean getSolicitudCajaCHFiltroBean() {
        if (solicitudCajaCHFiltroBean == null) {
            solicitudCajaCHFiltroBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHFiltroBean;
    }

    public void setSolicitudCajaCHFiltroBean(SolicitudCajaCHBean solicitudCajaCHFiltroBean) {
        this.solicitudCajaCHFiltroBean = solicitudCajaCHFiltroBean;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudCajaCHFiltroBean() {
        if (listaSolicitudCajaCHFiltroBean == null) {
            listaSolicitudCajaCHFiltroBean = new ArrayList<>();
        }
        return listaSolicitudCajaCHFiltroBean;
    }

    public void setListaSolicitudCajaCHFiltroBean(List<SolicitudCajaCHBean> listaSolicitudCajaCHFiltroBean) {
        this.listaSolicitudCajaCHFiltroBean = listaSolicitudCajaCHFiltroBean;
    }

    public List<CodigoBean> getListTipoMoneda() {
        if (listTipoMoneda == null) {
            listTipoMoneda = new ArrayList<>();
        }
        return listTipoMoneda;
    }

    public void setListTipoMoneda(List<CodigoBean> listTipoMoneda) {
        this.listTipoMoneda = listTipoMoneda;
    }

    public List<VistaBean> getListaCajeroBean() {
        if (listaCajeroBean == null) {
            listaCajeroBean = new ArrayList<>();
        }
        return listaCajeroBean;
    }

    public void setListaCajeroBean(List<VistaBean> listaCajeroBean) {
        this.listaCajeroBean = listaCajeroBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public void setListaCentroResponsabilidadBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
        this.listaCentroResponsabilidadBean = listaCentroResponsabilidadBean;
    }

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public Date getFechCierre() {
        return fechCierre;
    }

    public void setFechCierre(Date fechCierre) {
        this.fechCierre = fechCierre;
    }

    public Double getMontoSalidaSoles() {
        return montoSalidaSoles;
    }

    public void setMontoSalidaSoles(Double montoSalidaSoles) {
        this.montoSalidaSoles = montoSalidaSoles;
    }

    public Double getMontoEntradaSoles() {
        return montoEntradaSoles;
    }

    public void setMontoEntradaSoles(Double montoEntradaSoles) {
        this.montoEntradaSoles = montoEntradaSoles;
    }

    public Double getMontoSalidaDolares() {
        return montoSalidaDolares;
    }

    public void setMontoSalidaDolares(Double montoSalidaDolares) {
        this.montoSalidaDolares = montoSalidaDolares;
    }

    public Double getMontoEntradaDolares() {
        return montoEntradaDolares;
    }

    public void setMontoEntradaDolares(Double montoEntradaDolares) {
        this.montoEntradaDolares = montoEntradaDolares;
    }

    public Double getTotalSoles() {
        return totalSoles;
    }

    public void setTotalSoles(Double totalSoles) {
        this.totalSoles = totalSoles;
    }

    public Double getTotalDolares() {
        return totalDolares;
    }

    public void setTotalDolares(Double totalDolares) {
        this.totalDolares = totalDolares;
    }

    public CajaChicaSaldoBean getCajaChicaSaldoBean() {
        if (cajaChicaSaldoBean == null) {
            cajaChicaSaldoBean = new CajaChicaSaldoBean();
        }
        return cajaChicaSaldoBean;
    }

    public void setCajaChicaSaldoBean(CajaChicaSaldoBean cajaChicaSaldoBean) {
        this.cajaChicaSaldoBean = cajaChicaSaldoBean;
    }

    public Boolean getCollapse() {
        return collapse;
    }

    public void setCollapse(Boolean collapse) {
        this.collapse = collapse;
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

    public List<TipoSolicitudBean> getListaTipoSolicitudBean() {
        if (listaTipoSolicitudBean == null) {
            listaTipoSolicitudBean = new ArrayList();
        }
        return listaTipoSolicitudBean;
    }

    public void setListaTipoSolicitudBean(List<TipoSolicitudBean> listaTipoSolicitudBean) {
        this.listaTipoSolicitudBean = listaTipoSolicitudBean;
    }

    public Boolean getFlgAnular() {
        return flgAnular;
    }

    public void setFlgAnular(Boolean flgAnular) {
        this.flgAnular = flgAnular;
    }

}
