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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.MensajeBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaChicaService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.service.TipoSolicitudService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.util.GLTCalculadoraCR;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class SolicitudCajaCHMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of SolicitudCajaCHMB
     */
    @PostConstruct
    public void SolicitudCajaCHMB() {
        long a = System.currentTimeMillis();
        try {
            System.out.println("@PostConstruct SolicitudCajaCHMB");
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getSolicitudCajaCHBean();
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConcepto();

            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            getListaConceptoUniNegBean();

            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivos();

            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            getListaUnidadOrganicaBean();
            listaUnidadOrganicaBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
            getListaTipoSolicitudBean();
            listaTipoSolicitudBean = tipoSolicitudService.obtenerSolGenCajaCH(MaristaConstantes.TIP_SOL_GEN, MaristaConstantes.A_RENDIR, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (TipoSolicitudBean lista : listaTipoSolicitudBean) {
                System.out.println("tip " + lista.getNombre());
                if (lista.getNombre().equals(MaristaConstantes.TIP_SOL_GEN)) {
                    lista.setNombre(MaristaConstantes.TIP_SOL_GEN + " - " + "Pago Proveedores  ");
                }
                break;
            }

            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            getListaCentroResponsabilidadBean();
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaPlanContableBean = planContableService.obtenerPlanContable();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListTipoMoneda();
            listTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
//            getListTipoSolicitud();
//            listTipoSolicitud = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STA_CCH));

            getListTipoPrioridad();
            listTipoPrioridad = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PRIORIDAD));

            //Validar perfil gestor solicitudes
            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLogin);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.GESTOR_SOLI)) {
                    this.flgGestorSoli = true;
                    this.flgMostarListSoli = false;
                    break;
                } else {
                    this.flgGestorSoli = false;
                    this.flgMostarListSoli = true;
                }
            }
//            if (this.flgGestorSoli == false) {
            solicitudCajaCHBean.setNombreSolicitante(usuarioLogin.getPersonalBean().getNombreCompleto());
            solicitudCajaCHBean.setPersonalBean(usuarioLogin.getPersonalBean());

//            }
            cargarUniOrgUniNeg();

            getListTipoStatusSolCajaCh();
            fechaActual = new GregorianCalendar();
            getSolicitudCajaCHBean().setFechaSol(fechaActual.getTime());
            getSolicitudCajaCHFiltroBean().setFechaInicio(fechaActual.getTime());
            getSolicitudCajaCHFiltroBean().setFechaFin(fechaActual.getTime());
            listTipoStatusSolCajaCh = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STA_CCH));
            getSolicitudCajaCHFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            getSolicitudCajaCHFiltroBean().setCreaPor(usuarioLogin.getUsuario());
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, getListaCentroResponsabilidadBeanB());
            listaTipoCajaChica = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIPO_CAJACH));

            CodigoBean codigoPrioridad = new CodigoBean();
            codigoPrioridad = codigoService.obtenerPorCodigo(new CodigoBean(0, "Media", new TipoCodigoBean(MaristaConstantes.TIP_PRIORIDAD)));
            getSolicitudCajaCHBean().setTipoPrioridadBean(codigoPrioridad);
            setIdTipoSol("COL");
            solicitudCajaCHBean.setIdTipoSolicitante(idTipoSol);
            changeTipo("soli");
            getListaSolicitudCajaDocEgresoBean();
//            Double i = 0.00;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        long b = System.currentTimeMillis();
        System.out.println("time out: " + (b - a));
    }
    //Varialbes y Propiedades
    private SolicitudCajaCHBean solicitudCajaCHBean;
    private SolicitudCajaCHBean solicitudCajaCHFiltroBean;
    private List<SolicitudCajaCHBean> listSolicitudCajaSolicitanteBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaDocEgresoBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaCHFiltroBean;
    private List<SolicitudCajaCHBean> listSolicitudGestorBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private TipoConceptoBean tipoConceptoBean;
    private ConceptoBean conceptoBean;
    private List<CodigoBean> listTipoMoneda;
    private Integer idTipoMoneda = MaristaConstantes.COD_SOLES;
    private List<CodigoBean> listTipoPrioridad;
    private List<CodigoBean> listTipoSolicitud;
    private List<CodigoBean> listTipoStatusSolCajaCh;
    private List<TipoSolicitudBean> listaTipoSolicitudBean;
    private List<UniNegUniOrgBean> listaUnidadOrganicaBean;
    private Boolean flgAnular;
    //usuario
    private UsuarioBean usuarioLogin;
//    private List<UnidadNegocioBean> listaUnidadNegocioBean; 
    private List<PerfilBean> listaPerfilBean;
    private Boolean flgGestorSoli = false;
    private Boolean flgMostarListSoli = false;
    private Boolean mostrarPanel = false;
    private List<UniNegUniOrgBean> listaUniNegUniOrgBean;

    private Calendar fechaActual;
    //CR Multi
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
//    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanA;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;
    private List<CodigoBean> listaTipoDistribucionCRBean;

    private List<PresupuestoBean> listaPresupuestoBean;
    public Boolean flgLimiteMin = Boolean.FALSE;
    //Limite TipoCajaChica
    public List<CodigoBean> listaTipoCajaChica;
    public CajaChicaBean cajaChicaBean;
    private List<CodigoBean> listaTipoPagoBean;

    //Ayuda
    private Boolean flgFiltroPersonal = false;
    private Boolean flgFiltroPersona = false;
    private Boolean flgFiltroProve = false;
    private Boolean flgSoli = true;
    private Boolean flgIgualSoli = false;
    private String idTipoSol;
    private String idTipoRespCheque;
    private Integer idSolicitud;
    private Double montoTotDis = new Double("0.0");
    private List<CuentaBancoBean> listaCuentaBancoBean;
    //Ayuda para barinaga
    private Boolean flgCr = false;
    private Boolean flgArendir = false;
    private String flgRenderCr = "true";
    private List<SolicitudCajaCHBean> listaSolicitudesEnSesio;
    private List<PlanContableBean> listaPlanContableBean;

    public void cargarUniOrgUniNeg() {
        try {

            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            if (usuarioLogin != null) {
                listaUniNegUniOrgBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void convertMayus(String txt) {
        try {
            solicitudCajaCHBean.setMotivo(null);
            solicitudCajaCHBean.setMotivo(txt.toUpperCase());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroSolicitudCajaCh() {
        try {
            int estado = 0;
            this.mostrarPanel = true;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            if (solicitudCajaCHFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudCajaCHFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudCajaCHFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudCajaCHFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudCajaCHFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdSolicitudCajaCh() != null && !solicitudCajaCHFiltroBean.getIdSolicitudCajaCh().equals(0)) {
                solicitudCajaCHFiltroBean.getPersonalBean().setCodPer(solicitudCajaCHFiltroBean.getPersonalBean().getCodPer());
                estado = 1;
            }
//            if (solicitudCajaCHFiltroBean.getPersonalBean().getCodPer() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getCodPer().equals("")) {
//                solicitudCajaCHFiltroBean.getPersonalBean().setCodPer(solicitudCajaCHFiltroBean.getPersonalBean().getCodPer());
//                estado = 1;
//            }
//            if (solicitudCajaCHFiltroBean.getPersonalBean().getApepat() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getApepat().equals("")) {
//                solicitudCajaCHFiltroBean.getPersonalBean().setApepat(solicitudCajaCHFiltroBean.getPersonalBean().getApepat().toUpperCase().trim());
//                estado = 1;
//            }
//            if (solicitudCajaCHFiltroBean.getPersonalBean().getApemat() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getApemat().equals("")) {
//                solicitudCajaCHFiltroBean.getPersonalBean().setApemat(solicitudCajaCHFiltroBean.getPersonalBean().getApemat().toUpperCase().trim());
//                estado = 1;
//            }
//            if (solicitudCajaCHFiltroBean.getPersonalBean().getNombre() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getNombre().equals("")) {
//                solicitudCajaCHFiltroBean.getPersonalBean().setNombre(solicitudCajaCHFiltroBean.getPersonalBean().getNombre().toUpperCase().trim());
//                estado = 1;
//            }
            if (solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().setIdUniOrg(solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNombreSolicitante() != null && !solicitudCajaCHFiltroBean.getNombreSolicitante().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNombreSolicitante(solicitudCajaCHFiltroBean.getNombreSolicitante());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNomRespCheque() != null && !solicitudCajaCHFiltroBean.getNomRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNomRespCheque(solicitudCajaCHFiltroBean.getNomRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdPersonalSol() != null && !solicitudCajaCHFiltroBean.getIdPersonalSol().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdPersonalSol(solicitudCajaCHFiltroBean.getIdPersonalSol());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                if (flgGestorSoli == true) {
                    listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestor(solicitudCajaCHFiltroBean);
                }
                if (flgMostarListSoli == true) {
                    getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
                    listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);
                }
                if (flgMostarListSoli == false && flgGestorSoli == true) {
                    listSolicitudCajaSolicitanteBean = new ArrayList<>();
                }
                if (listSolicitudCajaSolicitanteBean.isEmpty() && listSolicitudGestorBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroSolicitudCajaChPorActividad() {
        try {
            int estado = 0;
            this.mostrarPanel = true;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            if (solicitudCajaCHFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudCajaCHFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudCajaCHFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudCajaCHFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudCajaCHFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getMotivo() != null && !solicitudCajaCHFiltroBean.getMotivo().trim().equals("")) {
                solicitudCajaCHFiltroBean.setMotivo(solicitudCajaCHFiltroBean.getMotivo());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNomRespCheque() != null && !solicitudCajaCHFiltroBean.getNomRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNomRespCheque(solicitudCajaCHFiltroBean.getNomRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestorPorActividad(solicitudCajaCHFiltroBean);
                if (listSolicitudGestorBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    solicitudCajaCHFiltroBean.setMontoTotalSoles(0.00);
                    solicitudCajaCHFiltroBean.setMontoTotalDolares(0.00);
                }
                for (SolicitudCajaCHBean sol : listSolicitudGestorBean) {
                    solicitudCajaCHFiltroBean.setMontoTotalSoles(sol.getMontoTotalSoles());
                    solicitudCajaCHFiltroBean.setMontoTotalDolares(sol.getMontoTotalDolares());
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroSolicitudCajaChPorActividadAdmi() {
        try {
            int estado = 0;
            Double montoSoles = 0.0;
            Double montoDolares = 0.0;
            this.mostrarPanel = true;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            if (solicitudCajaCHFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudCajaCHFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudCajaCHFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudCajaCHFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudCajaCHFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getMotivo() != null && !solicitudCajaCHFiltroBean.getMotivo().trim().equals("")) {
                solicitudCajaCHFiltroBean.setMotivo(solicitudCajaCHFiltroBean.getMotivo());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNomRespCheque() != null && !solicitudCajaCHFiltroBean.getNomRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNomRespCheque(solicitudCajaCHFiltroBean.getNomRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getCentroResponsabilidadBean().getCr() != null && !solicitudCajaCHFiltroBean.getCentroResponsabilidadBean().getCr().equals(0)) {
                solicitudCajaCHFiltroBean.getCentroResponsabilidadBean().setCr(solicitudCajaCHFiltroBean.getCentroResponsabilidadBean().getCr());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta() != null && !solicitudCajaCHFiltroBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta().equals(0)) {
                solicitudCajaCHFiltroBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().setCuenta(solicitudCajaCHFiltroBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestorPorActividadAdmi(solicitudCajaCHFiltroBean);
                if (listSolicitudGestorBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    solicitudCajaCHFiltroBean.setMontoTotalSoles(0.00);
                    solicitudCajaCHFiltroBean.setMontoTotalDolares(0.00);
                }
                for (SolicitudCajaCHBean sol : listSolicitudGestorBean) {
                    if (sol.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.COD_Soles_Cod)) {
                        montoSoles = sol.getMontoAprobado() + montoSoles;
                        solicitudCajaCHFiltroBean.setMontoTotalSoles(montoSoles);
                    } else {
                        montoDolares = sol.getMontoAprobado() + montoDolares;
                        solicitudCajaCHFiltroBean.setMontoTotalDolares(montoDolares);
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroSolicitudPorConcepto() {
        try {
            int estado = 1;
            this.mostrarPanel = true;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            if (solicitudCajaCHFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudCajaCHFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudCajaCHFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudCajaCHFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudCajaCHFiltroBean.setFechaFin(u);
                estado = 1;
            }

            solicitudCajaCHFiltroBean.getConceptoUniNegBean().getConceptoBean().setIdConcepto(11014);

            if (solicitudCajaCHFiltroBean.getIdSolicitudCajaCh() != null && !solicitudCajaCHFiltroBean.getIdSolicitudCajaCh().equals(0)) {
                solicitudCajaCHFiltroBean.getPersonalBean().setCodPer(solicitudCajaCHFiltroBean.getPersonalBean().getCodPer());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().setIdUniOrg(solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNombreSolicitante() != null && !solicitudCajaCHFiltroBean.getNombreSolicitante().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNombreSolicitante(solicitudCajaCHFiltroBean.getNombreSolicitante());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getNomRespCheque() != null && !solicitudCajaCHFiltroBean.getNomRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNomRespCheque(solicitudCajaCHFiltroBean.getNomRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdPersonalSol() != null && !solicitudCajaCHFiltroBean.getIdPersonalSol().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdPersonalSol(solicitudCajaCHFiltroBean.getIdPersonalSol());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
                estado = 1;
            }
            if (solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo() != null && !solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo().equals(0)) {
                solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().setIdCodigo(solicitudCajaCHFiltroBean.getTipoStatusSolCajaChBean().getIdCodigo());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
//                getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
                getSolicitudCajaCHFiltroBean().getPersonalBean().setIdPersonal(null);
                listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);

                if (listSolicitudCajaSolicitanteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSolicitudPorFiltro() {//autorizados
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            List<CajaChicaBean> listaAbierto = new ArrayList<>();
            List<CajaChicaBean> listaCerrado = new ArrayList<>();//ojo     
            getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaAbierto = cajaChicaService.obtenerCajaChicaAbierto(cajaChicaBean);
//            if (!listaAbierto.isEmpty() && listaAbierto.size() == 1) {
            if (getSolicitudCajaCHFiltroBean().getTipoMonedaBean().getIdCodigo() == (null)) {
                System.out.println("getSolicitudCajaCHFiltroBean().getTipoMonedaBean().getIdCodigo()");
            } else {
                if (getSolicitudCajaCHFiltroBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    solicitudCajaCHFiltroBean.setFlgSoles(1);
                } else {
                    solicitudCajaCHFiltroBean.setFlgSoles(0);
                }
            }
            System.out.println("solicitudCajaCHFiltroBean.getNomRespCheque()>>>>>>>>>" + solicitudCajaCHFiltroBean.getNomRespCheque());
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            solicitudCajaCHFiltroBean.setTipoStatusSolCajaChBean(new CodigoBean(MaristaConstantes.COD_SOL_AUTORIZADO));
            if (!listaAbierto.isEmpty() && listaAbierto.size() == 1) {
                System.out.println("lleguo");
                solicitudCajaCHFiltroBean.setMontoMaxMovSol(listaAbierto.get(0).getMontoMaxMovSol());
                solicitudCajaCHFiltroBean.setMontoMaxMovDol(listaAbierto.get(0).getMontoMaxMovDol());
            } else {
                listaCerrado = cajaChicaService.obtenerUltimaCajaChicaCerrada(cajaChicaBean);
                if (!listaCerrado.isEmpty() && listaCerrado.size() == 1) {
                    solicitudCajaCHFiltroBean.setMontoMaxMovSol(listaCerrado.get(0).getMontoMaxMovSol());
                    solicitudCajaCHFiltroBean.setMontoMaxMovDol(listaCerrado.get(0).getMontoMaxMovDol());
                } else {
                    flgLimiteMin = Boolean.FALSE;
                }
            }
            if (!flgLimiteMin) {
                solicitudCajaCHFiltroBean.setMontoMaxMovSol(0d);
                solicitudCajaCHFiltroBean.setMontoMaxMovDol(0d);
            }
            solicitudCajaCHFiltroBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            if (solicitudCajaCHFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(solicitudCajaCHFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                solicitudCajaCHFiltroBean.setFechaInicio(t);
            }
            if (solicitudCajaCHFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(solicitudCajaCHFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                solicitudCajaCHFiltroBean.setFechaFin(u);
            }

            if (solicitudCajaCHFiltroBean.getIdSolicitudCajaCh() != null && !solicitudCajaCHFiltroBean.getIdSolicitudCajaCh().equals(0)) {
                solicitudCajaCHFiltroBean.setIdSolicitudCajaCh(solicitudCajaCHFiltroBean.getIdSolicitudCajaCh());
            }
            if (solicitudCajaCHFiltroBean.getNombreSolicitante() != null && !solicitudCajaCHFiltroBean.getNombreSolicitante().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNombreSolicitante(solicitudCajaCHFiltroBean.getNombreSolicitante());
            }
            if (solicitudCajaCHFiltroBean.getNomRespCheque() != null && !solicitudCajaCHFiltroBean.getNomRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setNomRespCheque(solicitudCajaCHFiltroBean.getNomRespCheque());
            }
            if (solicitudCajaCHFiltroBean.getIdPersonalSol() != null && !solicitudCajaCHFiltroBean.getIdPersonalSol().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdPersonalSol(solicitudCajaCHFiltroBean.getIdPersonalSol());
            }
            if (solicitudCajaCHFiltroBean.getIdRespCheque() != null && !solicitudCajaCHFiltroBean.getIdRespCheque().trim().equals("")) {
                solicitudCajaCHFiltroBean.setIdRespCheque(solicitudCajaCHFiltroBean.getIdRespCheque());
            }
            if (solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg() != null && !solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().setIdUniOrg(solicitudCajaCHFiltroBean.getPersonalBean().getUnidadOrganicaBean().getIdUniOrg());
            }
            if (solicitudCajaCHFiltroBean.getMotivo() != null && !solicitudCajaCHFiltroBean.getMotivo().equals("")) {
                solicitudCajaCHFiltroBean.setMotivo(solicitudCajaCHFiltroBean.getMotivo().toUpperCase().trim());
            }
            listaSolicitudCajaCHFiltroBean = solicitudCajaCHService.obtenerSolicitudPorFiltroMenorA(solicitudCajaCHFiltroBean);
            System.out.println("size..." + listaSolicitudCajaCHFiltroBean.size());
            if (listaSolicitudCajaCHFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaSolicitudCajaCHFiltroBean = new ArrayList<>();
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            } else {
//                new MensajePrime().addInformativeMessagePer("msjCajaChicaAbierto");
//            }

            DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");
            if (docEgresoMB.getListaSolicitudCajaChBean() != null) {
                if (!docEgresoMB.getListaSolicitudCajaChBean().isEmpty()) {
                    List<SolicitudCajaCHBean> lista = new ArrayList<>();
                    lista = docEgresoMB.getListaSolicitudCajaChBean();
                    for (SolicitudCajaCHBean list : lista) {
                        if (!listaSolicitudCajaCHFiltroBean.isEmpty()) {
                            for (SolicitudCajaCHBean listaSoli : listaSolicitudCajaCHFiltroBean) {
                                if (list.getIdSolCaja().equals(listaSoli.getIdSolicitudCajaCh())) {
                                    listaSolicitudCajaCHFiltroBean.remove(listaSoli);
                                }
                                break;
                            }
                        }
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void traerCajaChicaTipo() {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            List<CajaChicaBean> lista = cajaChicaService.obtenerCajaChicaAbierto(cajaChicaBean);
            if (Objects.equals(lista, 1)) {
                cajaChicaBean = lista.get(0);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarSolicitudCajaCH() {
        try {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
            listaCentroResponsabilidadBeanB = new ArrayList<>();
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBean, getListaCentroResponsabilidadBeanB());
            tipoConceptoBean = new TipoConceptoBean();
            listaConceptoUniNegBean = new ArrayList<>();
            fechaActual = new GregorianCalendar();
            getSolicitudCajaCHBean().setFechaSol(fechaActual.getTime());
//            if (this.flgGestorSoli == false) {
//            solicitudCajaCHBean.setPersonalBean(usuarioLogin.getPersonalBean());
//            }
            solicitudCajaCHBean.setNombreSolicitante(usuarioLogin.getPersonalBean().getNombreCompleto());
            solicitudCajaCHBean.setPersonalBean(usuarioLogin.getPersonalBean());
            LegajoService legajoService = BeanFactory.getLegajoService();
            PersonalBean uniOrg = new PersonalBean();
            uniOrg = legajoService.obtenerUniOrgPersonalPorId(usuarioLogin.getPersonalBean());
            if (uniOrg != null) {
                solicitudCajaCHBean.getPersonalBean().setUnidadOrganicaBean(uniOrg.getUnidadOrganicaBean());
            }
            setIdTipoSol("COL");
            solicitudCajaCHBean.setIdTipoSolicitante(idTipoSol);
            changeTipo("soli");
            this.flgIgualSoli = false;
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoPrioridad = new CodigoBean();
            codigoPrioridad = codigoService.obtenerPorCodigo(new CodigoBean(0, "Media", new TipoCodigoBean(MaristaConstantes.TIP_PRIORIDAD)));
            getSolicitudCajaCHBean().setTipoPrioridadBean(codigoPrioridad);

            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            getListaConceptoUniNegBean();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivos();
            this.montoTotDis = new Double("0.0");
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarFiltroSolicitudCajaCH() {
        solicitudCajaCHFiltroBean = new SolicitudCajaCHBean();
        getSolicitudCajaCHFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        getSolicitudCajaCHFiltroBean().setCreaPor(usuarioLogin.getUsuario());
        fechaActual = new GregorianCalendar();
        getSolicitudCajaCHFiltroBean().setFechaInicio(fechaActual.getTime());
        getSolicitudCajaCHFiltroBean().setFechaFin(fechaActual.getTime());
        listSolicitudCajaSolicitanteBean = new ArrayList<>();
        listSolicitudGestorBean = new ArrayList<>();
        listaSolicitudCajaCHFiltroBean = new ArrayList<>();
    }

    public void limpiarFiltroSolicitudCajaCH2() {
        solicitudCajaCHFiltroBean = new SolicitudCajaCHBean();
        getSolicitudCajaCHFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        getSolicitudCajaCHFiltroBean().setCreaPor(usuarioLogin.getUsuario());
        fechaActual = new GregorianCalendar();
//        getSolicitudCajaCHFiltroBean().setFechaInicio(fechaActual.getTime());
        getSolicitudCajaCHFiltroBean().setFechaFin(fechaActual.getTime());
        listSolicitudCajaSolicitanteBean = new ArrayList<>();
        listSolicitudGestorBean = new ArrayList<>();
        listaSolicitudCajaCHFiltroBean = new ArrayList<>();
        getSolicitudCajaCHFiltroBean().getTipoMonedaBean().setIdCodigo(null);
    }

    public String insertarSolicitudCajaCH(String origen) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (solicitudCajaCHBean.getPersonalBean().getIdPersonal() != null) {
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                    MensajeBean mensaje = new MensajeBean();
                    if (origen != null) {
                        System.out.println("insertarSolicitudCajaCH oringen 1");
                        boolean comodin = true;
                        boolean fix = false;
                        CajaChicaMovMB cajaChicaMov = (CajaChicaMovMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMovMB");
//                        System.out.println("1; " + cajaChicaMov.getCajaChicaBean().getMontoMaxMovSol());
//                        System.out.println("2; " + solicitudCajaCHBean.getMonto());
                        if (solicitudCajaCHBean.getTipoMonedaBean().getIdCodigo().toString().equals(MaristaConstantes.COD_SOLES.toString())) {
                            if (solicitudCajaCHBean.getMonto() > cajaChicaMov.getCajaChicaBean().getMontoMaxMovSol()) {
                                comodin = false;
                            }
                        }
                        if (solicitudCajaCHBean.getTipoMonedaBean().getIdCodigo().toString().equals(MaristaConstantes.COD_DOLARES.toString())) {
                            if (solicitudCajaCHBean.getMonto() > cajaChicaMov.getCajaChicaBean().getMontoMaxMovDol()) {
                                comodin = false;
                            }
                        }
                        if (!validarMontoPer()) {
                            comodin = false;
                            fix = true;
                        }
                        if (comodin) {
                            System.out.println("insertarSolicitudCajaCH comodin true 1");
                            TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                            TipoSolicitudBean tipoSolicitudBean = new TipoSolicitudBean();
                            tipoSolicitudBean.setNombre(MaristaConstantes.CONTRA_PAGO);
                            tipoSolicitudBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//                        TipoCodigoBean tipoCodigoBean = new TipoCodigoBean(MaristaConstantes.)
                            tipoSolicitudBean = tipoSolicitudService.obtenerTipoSolicitudPorNombre(tipoSolicitudBean);
                            solicitudCajaCHBean.setTipoSolicitudBean(tipoSolicitudBean);
                            solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
                            TipoCodigoBean tipoCodigoBean = new TipoCodigoBean(MaristaConstantes.TIP_STATUS_SOL);
                            CodigoBean bean = new CodigoBean(null, MaristaConstantes.COD_AUTORI, tipoCodigoBean);
                            CodigoService codigoService = BeanFactory.getCodigoService();
                            bean = codigoService.obtenerPorCodigo(bean);
                            solicitudCajaCHBean.setTipoStatusSolCajaChBean(bean);
                            solicitudCajaCHService.insertarSolicitudCajaCH(solicitudCajaCHBean, "1", mensaje, null);
                            CajaChicaMovMB cajaChicaMovMB = (CajaChicaMovMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMovMB");
                            cajaChicaMovMB.ponerSolicitud(solicitudCajaCHBean);
                            cajaChicaMovMB.guardarCajaChicaMov();
                            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMovMB", cajaChicaMovMB);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            limpiarSolicitudCajaCH();
                        } else {
                            if (fix) {
                                new MensajePrime().addInformativeMessagePer("msjSumDistPer");
                            } else {
                                new MensajePrime().addInformativeMessagePer("msjMontoMaxSolicitud");
                            }

                        }
                    } else {
                        solicitudCajaCHService.insertarSolicitudCajaCH(solicitudCajaCHBean, null, mensaje, null);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        limpiarSolicitudCajaCH();
                    }
//                    if (flgGestorSoli == true) {
//                        listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestor(solicitudCajaCHFiltroBean);
//                    }
//                    if (flgMostarListSoli == false) {
//                        getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
//                        listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);
//                    }
//                    if (flgMostarListSoli == false && flgGestorSoli == true) {
//                        listSolicitudCajaSolicitanteBean = new ArrayList<>();
//                    }
//                    if (listSolicitudCajaSolicitanteBean.isEmpty() && listSolicitudGestorBean.isEmpty()) {
//                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                    }
                } else {
                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeSolicitanteRequerido", null));
//                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarSolicitudCajaCHGen(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                System.out.println(solicitudCajaCHBean.getPersonalBean().getIdPersonal());
//                System.out.println(solicitudCajaCHBean.getPersonaBean().getIdPersona());
//                System.out.println(solicitudCajaCHBean.getEntidadBean().getRuc());
                if (solicitudCajaCHBean.getPersonalBean().getIdPersonal() != null || solicitudCajaCHBean.getPersonaBean().getIdPersona() != null
                        || solicitudCajaCHBean.getEntidadBean().getRuc() != null) {
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//                    if (!idTipoSol.equals("") && idTipoSol != null) {
//                        solicitudCajaCHBean.setIdTipoSolicitante(idTipoSol);
//                    }
//                    if (idTipoRespCheque != null) {
//                        if (!idTipoRespCheque.equals("") && idTipoRespCheque != null) {
//                            solicitudCajaCHBean.setIdTipoRespCheque(idTipoRespCheque);
//                        }
//                    }
                    solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                    MensajeBean mensaje = new MensajeBean();
                    solicitudCajaCHBean.setFlgTieneCr(flgCr);
                    solicitudCajaCHService.insertarSolicitudCajaCHGen(solicitudCajaCHBean, null, mensaje, null, tipo);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    this.idSolicitud = null;
                    if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                        this.idSolicitud = solicitudCajaCHBean.getIdSolicitudCajaCh();
                    }

                    limpiarSolicitudCajaCH();

                } else {
                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeSolicitanteRequerido", null));
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarSolicitudCajaCHGenBarinaga(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                System.out.println(solicitudCajaCHBean.getPersonalBean().getIdPersonal());
//                System.out.println(solicitudCajaCHBean.getPersonaBean().getIdPersona());
//                System.out.println(solicitudCajaCHBean.getEntidadBean().getRuc());
                if (solicitudCajaCHBean.getPersonalBean().getIdPersonal() != null || solicitudCajaCHBean.getPersonaBean().getIdPersona() != null
                        || solicitudCajaCHBean.getEntidadBean().getRuc() != null) {
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                    MensajeBean mensaje = new MensajeBean();
                    System.out.println("motivo: " + solicitudCajaCHBean.getMotivo());
                    solicitudCajaCHBean.setFlgTieneCr(flgCr);
                    solicitudCajaCHService.insertarSolicitudCajaCHGenBarina(solicitudCajaCHBean, null, mensaje, null, tipo);
                    solicitudCajaCHBean.setIdSolCaja(solicitudCajaCHBean.getIdSolicitudCajaCh());
                    solicitudCajaCHBean.setGlosaDoc(solicitudCajaCHBean.getMotivo());
                    solicitudCajaCHBean.setFlgTieneCr(flgCr);
                    List<SolicitudCajaCHBean> lisSol = new ArrayList<>();
                    lisSol.add(solicitudCajaCHBean);
                    DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");
                    List<DocEgresoBean> listaDocEgresoMB = new ArrayList<>();
//                    docEgresoMB.setListaSolicitudCajaChBean(lisSol);
                    for (SolicitudCajaCHBean solicitud : lisSol) {
                        docEgresoMB.getListaSolicitudCajaChBean().add(solicitud);
                    }

                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    this.idSolicitud = null;
                    if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                        this.idSolicitud = solicitudCajaCHBean.getIdSolicitudCajaCh();
                    }

                    limpiarSolicitudCajaCH();

                } else {
                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeSolicitanteRequerido", null));
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarSolicitudCajaCHAprobacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    solicitudCajaCHBean.setCreaPor(usuarioLogin.getUsuario());
                    solicitudCajaCHService.insertarSolicitudCajaCHAprobacion(solicitudCajaCHBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    limpiarSolicitudCajaCH();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void validarMonto(Double montoSolMax, Double montoDolMax) {
        try {
            if (solicitudCajaCHBean.getTipoMonedaBean().getIdCodigo().toString().equals(MaristaConstantes.COD_SOLES.toString())) {
                if (solicitudCajaCHBean.getMonto() > montoSolMax) {
                    new MensajePrime().addInformativeMessagePer("msjMontoMaxSolicitud");
                }
            }
            if (solicitudCajaCHBean.getTipoMonedaBean().getIdCodigo().toString().equals(MaristaConstantes.COD_DOLARES.toString())) {
                if (solicitudCajaCHBean.getMonto() > montoDolMax) {
                    new MensajePrime().addInformativeMessagePer("msjMontoMaxSolicitud");
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public String modificarSolicitudCajaCH() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                solicitudCajaCHBean.setModiPor(usuarioLogin.getUsuario());
                solicitudCajaCHService.modificarSolicitudCajaCH(solicitudCajaCHBean);

//                if (flgGestorSoli == true) {
//                    listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestor(solicitudCajaCHFiltroBean);
//                }
//                if (flgMostarListSoli == false) {
//                    getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
//                    listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);
//                }
//                if (flgMostarListSoli == false && flgGestorSoli == true) {
//                    listSolicitudCajaSolicitanteBean = new ArrayList<>();
//                }
//                if (listSolicitudCajaSolicitanteBean.isEmpty() && listSolicitudGestorBean.isEmpty()) {
//                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarSolicitudCajaCH();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String anularSolicitudCCH() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (solicitudCajaCHBean.getTipoStatusSolCajaChBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE) && flgAnular == true) {
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHBean.setModiPor(usuarioLogin.getUsuario());
                    MensajeBean mensaje = new MensajeBean();
                    solicitudCajaCHService.anularSolicitudCCH(solicitudCajaCHBean, mensaje);
                    if (flgGestorSoli == true) {
                        listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestor(solicitudCajaCHFiltroBean);
                    }
                    if (flgMostarListSoli == true) {
                        getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
                        listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);
                    }
                    if (flgMostarListSoli == false && flgGestorSoli == true) {
                        listSolicitudCajaSolicitanteBean = new ArrayList<>();
                    }
                    if (listSolicitudCajaSolicitanteBean.isEmpty() && listSolicitudGestorBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    limpiarSolicitudCajaCH();
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

//    public String eliminarSolicitudCajaCH() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
//                solicitudCajaCHService.eliminarSolicitudCajaCH(solicitudCajaCHBean);
////                if (flgGestorSoli == true) {
////                    listSolicitudGestorBean = solicitudCajaCHService.obtenerSolicitudPorFiltroGestor(solicitudCajaCHFiltroBean);
////                }
////                if (flgMostarListSoli == false) {
////                    getSolicitudCajaCHFiltroBean().setPersonalBean(usuarioLogin.getPersonalBean());
////                    listSolicitudCajaSolicitanteBean = solicitudCajaCHService.obtenerSolicitudPorFiltroPersonal(solicitudCajaCHFiltroBean);
////                }
////                if (flgMostarListSoli == false && flgGestorSoli == true) {
////                    listSolicitudCajaSolicitanteBean = new ArrayList<>();
////                }
////                if (listSolicitudCajaSolicitanteBean.isEmpty() && listSolicitudGestorBean.isEmpty()) {
////                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
////                }
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
    public void obtenerSolicitudCajaChPorId(Object objeto) {
        try {
            solicitudCajaCHBean = (SolicitudCajaCHBean) objeto;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(solicitudCajaCHBean);
            obtenerConceptoPorTipo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaVista", true);
            if (solicitudCajaCHBean.getTipoStatusSolCajaChBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE)
                    && solicitudCajaCHBean.getFlgAutoriza1() == null && solicitudCajaCHBean.getFlgAutoriza2() == null && solicitudCajaCHBean.getFlgAutoriza3() == null) {
                flgAnular = true;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                flgAnular = false;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
            }

            //3.-Crear Lista Respuesta
            List<DetSolicitudCajaChCRBean> listaDetSolCR = new ArrayList<>();
            listaDetSolCR = solicitudCajaCHService.obtenerDetSolcitudCajaChCRPorSolCaj(solicitudCajaCHBean);
            solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolCR);

            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerInCrSolCaj(solicitudCajaCHBean.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.obtenerOutCrSolCaj(solicitudCajaCHBean.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

            if (solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta() != null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                listaPresupuestoBean = presupuestoService.obtenerPresupuestoCuenta(solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2016);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSolicitudCajaChPorIdAutorizacion(Object objeto) {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            solicitudCajaCHBean = (SolicitudCajaCHBean) objeto;
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(solicitudCajaCHBean);
            System.out.println("idTipoSolicitante..." + getSolicitudCajaCHBean().getIdTipoSolicitante());
            this.idTipoSol = getSolicitudCajaCHBean().getIdTipoSolicitante();
            if (getSolicitudCajaCHBean().getIdTipoModoPago() != null) {
                System.out.println("nadaaa");
            } else {
                getSolicitudCajaCHBean().setIdTipoModoPago(MaristaConstantes.CODIGO_CHEQUE);
                getSolicitudCajaCHBean().setCodModoPago(MaristaConstantes.COD_CHEQUE);
            }
            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorCodigoDisCR(solicitudCajaCHBean.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cod != null) {
                solicitudCajaCHBean.setCodDistribucion(cod.getIdCodigo());
            }
            if (solicitudCajaCHBean.getMontoAprobado() == null) {
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMonto());
            } else {
                solicitudCajaCHBean.setMontoAprobado(solicitudCajaCHBean.getMontoAprobado());
            }
//            obtenerConceptoPorTipo();
            obtenerConceptoPorTipoMsj();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaVista", true);
            if (solicitudCajaCHBean.getTipoStatusSolCajaChBean().getCodigo().equals(MaristaConstantes.COD_SOL_PENDIENTE)
                    && solicitudCajaCHBean.getFlgAutoriza1() == null && solicitudCajaCHBean.getFlgAutoriza2() == null && solicitudCajaCHBean.getFlgAutoriza3() == null) {
                flgAnular = true;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                flgAnular = false;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
            }

            //3.-Crear Lista Respuesta
            List<DetSolicitudCajaChCRBean> listaDetSolCR = new ArrayList<>();
            listaDetSolCR = solicitudCajaCHService.obtenerDetSolcitudCajaChCRPorSolCaj(solicitudCajaCHBean);
            solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolCR);

            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerInCrSolCaj(solicitudCajaCHBean.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.obtenerOutCrSolCaj(solicitudCajaCHBean.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

            if (solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta() != null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                listaPresupuestoBean = presupuestoService.obtenerPresupuestoCuenta(solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2016);
            }

            if (!Objects.equals(solicitudCajaCHBean.getMontoAprobado(), solicitudCajaCHBean.getMonto())) {
                distribuirAprobacion();
            }

            obtenerCuentaBancoPorTipMoneda();
            if (solicitudCajaCHBean.getCodDistribucion()==19503) {
                this.montoDistribucion();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verCuentaContable() {
        try {
            if (solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta() != null) {
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                listaPresupuestoBean = presupuestoService.obtenerPresupuestoCuenta(solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean().getCuenta(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2016);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void distribuirCR() {
        try {
//            if (!Objects.equals(solicitudCajaCHBean.getMontoAprobado(), solicitudCajaCHBean.getMonto())) {
            distribuirAprobacion();
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verModoP(Integer id) {
        try {
            System.out.println("modedaa" + id);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void settearCta() {
        try {
//            System.out.println("getIdTipoModoPago()->" + getSolicitudCajaCHBean().getIdTipoModoPago());
            if (getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_CHEQUE)
                    || getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_TRANSFERENCIA)
                    || getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_CARTAORDEN)) {
                System.out.println("sí es che o transo carta");
            } else {
                System.out.println("no es cheq ni trans");
                getSolicitudCajaCHBean().setRucBanco(null);
                getSolicitudCajaCHBean().setNumCuenta(null);
                listaCuentaBancoBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaBancoPorTipMoneda() {
        System.out.println("obtenerCuentaBancoPorTipMoneda");
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            CodigoBean cod = new CodigoBean();
            CodigoService codigo = BeanFactory.getCodigoService();
            cod = codigo.obtenerPorId(getSolicitudCajaCHBean().getTipoMonedaBean());

            System.out.println("CH/ O TR/");
            listaCuentaBancoBean = cuentaBancoService.obtenerBancoPorTipMonedaBcoColegio(MaristaConstantes.TIP_MON, cod.getCodigo(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("lista " + listaCuentaBancoBean.size());
            if (listaCuentaBancoBean.size() == 1) {
                if (getSolicitudCajaCHBean().getNumCuenta() == null) {
                    getSolicitudCajaCHBean().setNumCuenta(listaCuentaBancoBean.get(0).getNumCuenta());
                    getSolicitudCajaCHBean().setRucBanco(listaCuentaBancoBean.get(0).getEntidadBancoBean().getRuc());
                }
//                obtenerRuc();
            } else {
                if (getSolicitudCajaCHBean().getIdTipoModoPago() == null) {
                    getSolicitudCajaCHBean().setIdTipoModoPago(MaristaConstantes.CODIGO_CHEQUE);
                    getSolicitudCajaCHBean().setCodModoPago("Cheque");
                }
                System.out.println("entró..." + getSolicitudCajaCHBean().getIdTipoModoPago() + "-" + getSolicitudCajaCHBean().getCodModoPago());
                CodigoBean tipoCta = new CodigoBean();

                if (getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_CHEQUE)) {
                    tipoCta.setCodigo(MaristaConstantes.COD_TIPO_CTA_CTE);
                } else if (getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_TRANSFERENCIA)) {
                    tipoCta.setCodigo(MaristaConstantes.COD_TIPO_AHORROS);
                } else if (getSolicitudCajaCHBean().getIdTipoModoPago().equals(MaristaConstantes.CODIGO_CARTAORDEN)) {
                    tipoCta.setCodigo(MaristaConstantes.COD_TIPO_AHORROS);
                }
                System.out.println("tipoCta setCodigo " + tipoCta.getCodigo());
                tipoCta.getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_CUENTA_BCO.toString());
                tipoCta = codigo.obtenerPorCodigo(tipoCta);
                CuentaBancoBean cta = new CuentaBancoBean();
                cta.getEntidadBancoBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                cta.setTipoMonedaBean(getSolicitudCajaCHBean().getTipoMonedaBean());
                cta.setFlgCobranza(Boolean.FALSE);
                cta.setFlgCtaCongre(Boolean.FALSE);
                if (tipoCta != null) {
                    cta.setTipoCuentaBancoBean(tipoCta);
                }

                List<CuentaBancoBean> lista = new ArrayList<>();
                lista = cuentaBancoService.obtenerCuentaPorTipo(cta);
                if (lista != null) {
                    if (!lista.isEmpty()) {
                        if (lista.size() == 1) {
                            cta = lista.get(0);
                        }
                    }
                }
//                cta = cuentaBancoService.obtenerCuentaPorTipo(cta);
                System.out.println("cta " + cta.getNumCuenta());
                if (cta != null) {
                    cta.setTipoCuentaBancoBean(tipoCta);
                    getSolicitudCajaCHBean().setNumCuenta(cta.getNumCuenta());
                    getSolicitudCajaCHBean().setRucBanco(cta.getEntidadBancoBean().getRuc());
//                    obtenerRuc();
                }
            }
            settearCta();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarSolicitudCajaCH() {
        try {
            if (validarMontoPer()) {
                if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                    modificarSolicitudCajaCH();
                } else {
                    insertarSolicitudCajaCH(null);
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjSumDistPer");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarSolicitudCajaCHGeneral(String tipo) {
        try {
            if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud() != null
                    && !solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud().equals(0)) {
                if (!solicitudCajaCHBean.getIdRespCheque().trim().equals("")
                        && solicitudCajaCHBean.getIdRespCheque().trim() != null) {
                    if (this.flgCr == false) {
                        if (validarMontoPer()) {
                            if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                                modificarSolicitudCajaCH();
                            } else {
                                insertarSolicitudCajaCHGen(tipo);
                            }
                        } else {
                            new MensajePrime().addInformativeMessagePer("msjSumDistPer");
                        }
                    } else {
                        if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                            modificarSolicitudCajaCH();
                        } else {
                            insertarSolicitudCajaCHGen(tipo);
                        }
                    }
                } else {
                    String tipoSoli = "El campo 'A la orden' de es requerido";
                    new MensajePrime().addInformativeMessagePer(tipoSoli);
                }
            } else {
                String tipoSoli = "El campo Tipo Solicitud es requerido";
                new MensajePrime().addInformativeMessagePer(tipoSoli);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            solicitudCajaCHBean = (SolicitudCajaCHBean) event.getObject();
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            solicitudCajaCHBean = solicitudCajaCHService.obtenerSolicitudCajaCHBeanPorId(solicitudCajaCHBean);
            obtenerConceptoPorTipo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipoMsj() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(getSolicitudCajaCHBean().getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            verCuentaContable();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCTA(Integer idConcepto) {
        try {
//            System.out.println("ID 1 "+GET);
            System.out.println("ID 1 " + idConcepto);
//            System.out.println("cta D "+concepto.getPlanContableCuentaDBean().getCuenta());
//            System.out.println("cta H "+concepto.getPlanContableCuentaHBean().getCuenta());
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);
            solicitudCajaCHBean.setConceptoUniNegBean(con);
            System.out.println("ID 2 " + getSolicitudCajaCHBean().getConceptoUniNegBean().getConceptoBean().getIdConcepto());
            System.out.println("ID 2 " + getSolicitudCajaCHBean().getConceptoUniNegBean().getConceptoBean().getNombre());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoPorConcepto(Integer idConcepto) {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            this.flgRenderCr = conceptoService.validarSiTieneCr(idConcepto);
            System.out.println("flgRenderCr--->" + flgRenderCr);
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            Integer id = null;
            id = conceptoUniNegService.obtenerTipoPorIdConcepto(idConcepto, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!id.equals(null)) {
                TipoConceptoBean tipo = new TipoConceptoBean();
                tipo = tipoConceptoService.obtenerTipoConceptoPorId(id);
                if (!tipo.equals(null)) {
                    tipoConceptoBean = tipo;
                }
            }
            if (tipoConceptoBean.getNombre().equals(MaristaConstantes.Entregas_A_RENDIR_2)) {
                TipoSolicitudService soli = BeanFactory.getTipoSolicitudService();
                TipoSolicitudBean tipso = new TipoSolicitudBean();
                tipso.setNombre(MaristaConstantes.A_RENDIR);
                tipso.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                tipso = soli.obtenerTipoSolicitudPorNombre(tipso);
                solicitudCajaCHBean.setTipoSolicitudBean(tipso);

            } else {
                solicitudCajaCHBean.getTipoSolicitudBean().setNombre(MaristaConstantes.TIP_SOL_GEN);
            }
            System.out.println("tipoSolicitud: " + solicitudCajaCHBean.getTipoSolicitudBean().getNombre());
            verCuentaContable();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    public void cambiarTipoSol(Integer idTipoSol,Integer idSol) {
        try {
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            SolicitudCajaCHBean sol = new SolicitudCajaCHBean();
             sol.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
             sol.setIdSolicitudCajaCh(idSol);
             sol.getTipoSolicitudBean().setIdTipoSolicitud(idTipoSol);
             solicitudCajaCHService.modificarTipoSol(sol);
             obtenerFiltroSolicitudCajaCh();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoPorConceptoPorArendir() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            ConceptoService concepto = BeanFactory.getConceptoService();
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            TipoSolicitudService tipoS = BeanFactory.getTipoSolicitudService();
            TipoSolicitudBean sol = new TipoSolicitudBean();
            sol.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sol.setIdTipoSolicitud(solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud());
            sol = tipoS.obtenerTipoSolicitudPorId(sol);
            if (sol.getNombre().equals(MaristaConstantes.A_RENDIR)) {
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUniArendir(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                Integer id = null;
                Integer idCon = null;
                System.out.println("conceppto: "+listaConceptoUniNegBean.get(0).getConceptoBean().getIdConcepto());
                id = conceptoUniNegService.obtenerTipoPorIdConcepto(listaConceptoUniNegBean.get(0).getConceptoBean().getIdConcepto(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (!id.equals(null)) {
                    TipoConceptoBean tipo = new TipoConceptoBean();
                    tipo = tipoConceptoService.obtenerTipoConceptoPorId(id);
                    if (!tipo.equals(null)) {
                        tipoConceptoBean = tipo;
                    }
                }
                this.flgArendir = Boolean.TRUE;
            } else {
                this.flgArendir = Boolean.FALSE;
                listaTipoConceptoBean = new ArrayList<>();
                listaConceptoUniNegBean = new ArrayList<>();
                tipoConceptoBean = new TipoConceptoBean();
                solicitudCajaCHBean.setConceptoUniNegBean(null);
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorUni(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConcepto();
            }
            if (this.flgArendir.equals(Boolean.TRUE)) {
                if (getIdTipoRespCheque() != null) {
                    if (!getIdTipoRespCheque().equals("COL")) {
                        solicitudCajaCHBean.setIdRespCheque(null);
                        solicitudCajaCHBean.setNomRespCheque(null);
                        this.flgFiltroPersonal = true;
                        this.flgFiltroPersona = false;
                        this.flgFiltroProve = false;
                        setIdTipoRespCheque("COL");
                        System.out.println("xxx");
                    }
                }

            } else {
                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("CHAMPS")) {
                    this.flgFiltroPersonal = false;
                    this.flgFiltroPersona = false;
                    this.flgFiltroProve = true;
                    setIdTipoRespCheque("PRO");
                    System.out.println("xxx");
                }
            }
//            idCon=listaConceptoUniNegBean.get(0).getConceptoBean().getIdConcepto();
//            if (!idCon.equals(null)) {
//                ConceptoBean conc = new ConceptoBean();
//                 conc = concepto.obtenerConceptoPorIdCon(idCon);
//                if (!conc.equals(null)) {
//                    conceptoBean = conc;
//                }
//            }
//             
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerSolicitudArendirEnDocEgreso(Object solicitud) {
        try {
            solicitudCajaCHBean = (SolicitudCajaCHBean) solicitud;
            DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");
            docEgresoMB.limpiarDocEgreso();
            System.out.println("");
            docEgresoMB.setMostrarDetraccion(true);
            docEgresoMB.cargarListaDetraccion();
            docEgresoMB.setFlgIdTipoDoc(true);
            docEgresoMB.getDocEgresoBean().setIdTipoDoc(1);
            docEgresoMB.getDocEgresoBean().setIdTipoDocEgreso("A");
            docEgresoMB.getDocEgresoBean().getSolicitudCajaCHBean().setTipoMonedaBean(solicitudCajaCHBean.getTipoMonedaBean());
            docEgresoMB.getDocEgresoBean().setTipoMonedaBean(solicitudCajaCHBean.getTipoMonedaBean());
            docEgresoMB.getDocEgresoBean().setSolicitudCajaCHBean(solicitudCajaCHBean);
            docEgresoMB.setOrigen(1);//doc egreso
            if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre().equals(MaristaConstantes.CONTRA_PAGO)) {
                docEgresoMB.getDocEgresoBean().getSolicitudCajaCHBean().setMontoAprobado(solicitudCajaCHBean.getMonto());
            }
            docEgresoMB.obtenerCambio();
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("docEgresoMB", docEgresoMB);
            docEgresoMB.obtenerCuentaBancoPorTipMoneda();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerSoliCajaEnDocEgreso(SolicitudCajaCHBean soli) {
        try {
            solicitudCajaCHBean = (SolicitudCajaCHBean) soli;
            DocEgresoMB docEgresoMB = (DocEgresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docEgresoMB");

            if (!validarSoliPorAgregar(soli)) {
                docEgresoMB.limpiarDocEgresoCaja();
                docEgresoMB.setFlgIdTipoDoc(true);
                docEgresoMB.cargarListaDetraccion();
                docEgresoMB.getDocEgresoBean().setIdTipoDoc(1);
                docEgresoMB.validarTipoPago();
                docEgresoMB.setOrigen(1);
//                solicitudCajaCHBean.setDocRefAyuda("-");
                solicitudCajaCHBean.getDetraccionBean().setDescripcion("Seleccionar");
                solicitudCajaCHBean.getTipoDocBean().setCodigo("Seleccionar");
                solicitudCajaCHBean.setGarantia(new Double("0.0"));
                System.out.println("modo:" + solicitudCajaCHBean.getIdTipoModoPago());
                listaSolicitudCajaDocEgresoBean.add(solicitudCajaCHBean);
                if (listaSolicitudCajaCHFiltroBean != null) {
                    listaSolicitudCajaCHFiltroBean.remove(solicitudCajaCHBean);
                }
                if (getSolicitudCajaCHBean().getTipoSolicitudBean().getNombre().equals(MaristaConstantes.A_RENDIR)) {
                    docEgresoMB.setFlgSolGeneral(Boolean.FALSE);
                } else if (getSolicitudCajaCHBean().getTipoSolicitudBean().getNombre().equals(MaristaConstantes.TIP_SOL_GEN)) {
                    docEgresoMB.setFlgSolGeneral(Boolean.TRUE);
                } else {
                    docEgresoMB.setFlgSolGeneral(Boolean.FALSE);
                }
                docEgresoMB.getDocEgresoBean().setSolicitudCajaCHBean(solicitudCajaCHBean);
                docEgresoMB.getDocEgresoBean().setTipoMonedaBean(solicitudCajaCHBean.getTipoMonedaBean());
                docEgresoMB.getDocEgresoBean().getTipoPagoBean().setIdCodigo(solicitudCajaCHBean.getIdTipoModoPago());
                docEgresoMB.getDocEgresoBean().getTipoPagoBean().setCodigo(solicitudCajaCHBean.getCodModoPago());
                docEgresoMB.mostarTipoPago();
                docEgresoMB.setListaSolicitudCajaChBean(listaSolicitudCajaDocEgresoBean);
                docEgresoMB.obtenerCambio();
                docEgresoMB.obtenerCuentaBancoPorTipMoneda();
                if (solicitudCajaCHBean.getNumCuenta() == null) {
                    System.out.println("no llegó solicitudCajaCHBean.getNumCuenta()");
                } else {
                    docEgresoMB.getDocEgresoBean().getCuentaBancoBean().setNumCuenta(solicitudCajaCHBean.getNumCuenta());
                }
                if (solicitudCajaCHBean.getNumCuenta() == null) {
                    System.out.println("no llegó solicitudCajaCHBean.getRucBanco()");
                } else {
                    docEgresoMB.getDocEgresoBean().getCuentaBancoBean().getEntidadBancoBean().setRuc(solicitudCajaCHBean.getRucBanco());
                }

                if (solicitudCajaCHBean.getNumCuenta() == null) {
                    System.out.println("solicitudCajaCHBean.getNumCuenta()==null");
                } else {
                    docEgresoMB.obtenerRuc();
                }

                System.out.println("solicitudCajaCHBean.getNumCuenta()----" + solicitudCajaCHBean.getNumCuenta());
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("docEgresoMB", docEgresoMB);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                System.out.println("ok :D");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Boolean validarSoliPorAgregar(SolicitudCajaCHBean soli) {
        Boolean valor = false;
        try {
            if (listaSolicitudCajaDocEgresoBean != null) {
                if (!listaSolicitudCajaDocEgresoBean.isEmpty()) {
                    for (SolicitudCajaCHBean detalle : listaSolicitudCajaDocEgresoBean) {
                        if (!Objects.equals(detalle.getIdRespCheque(), soli.getIdRespCheque())) {
                            valor = true;
                        }
                        if (Objects.equals(detalle.getIdSolCaja(), soli.getIdSolicitudCajaCh())) {
                            valor = true;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    //Centro de Responsabilidad
    public void distribuir() {
        try {
            this.montoTotDis = new Double("0.0");
            //1.-Mappear Dual a Lista
//            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
//            DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad = new DualListModel<>();
//            dualCentroResponsabilidad=dualCentroResponsabilidadBean;
            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
//            for (int i = 0; i < dualCentroResponsabilidadBean.getTarget().size(); i++) {
//                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
//                Object objeto = dualCentroResponsabilidadBean.getTarget().get(i);
//                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
//                listaCentroResponsabilidad.add(cr);
//            }
            if (listaCentroResponsabilidad != null) {
                if (!listaCentroResponsabilidad.isEmpty()) {
                    //2.-Invocar calculadora
                    for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                        if (solicitudCajaCHBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, solicitudCajaCHBean.getMonto());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, solicitudCajaCHBean.getMonto());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                                new GLTCalculadoraCR().calcularPorPersonalizacion(listaCentroResponsabilidad);
                                break;
                            }
                        }
                    }
                    //3.-Crear Lista Respuesta
                    List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean = new ArrayList<>();
                    for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                        DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
                        detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                        detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
                        detSolicitudCajaChCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                        detSolicitudCajaChCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                        listaDetSolicitudCajaChCRBean.add(detSolicitudCajaChCRBean);
                    }
                    solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolicitudCajaChCRBean);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                    solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(null);
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(null);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void distribuirCuandoHayNotaC(SolicitudCajaCHBean sol, Double monto) {
        try {
            SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
            List<DetSolicitudCajaChCRBean> listaDetSolCR = new ArrayList<>();
            sol.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaDetSolCR = solicitudCajaCHService.obtenerDetSolcitudCajaChCRPorSolCaj(sol);
            String distribuir = solicitudCajaCHService.obtenerDetSolcitudCajaChCRNotaCredito(sol.getIdSolicitudCajaCh(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("distribuir " + distribuir);
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
            List<DetSolicitudCajaChCRBean> listaDetSolCR2 = new ArrayList<>();
            for (DetSolicitudCajaChCRBean soliCr : listaDetSolCR) {
                CentroResponsabilidadBean c = new CentroResponsabilidadBean();
                c.setCr(soliCr.getCentroResponsabilidadBean().getCr());
                c.setNombre(soliCr.getCentroResponsabilidadBean().getNombre());
                c.setTipoNivelCR(soliCr.getCentroResponsabilidadBean().getIdTipoGrupoCR());
                c.setMontoDistribucion(soliCr.getValor());
                System.out.println("tipogrupo: " + c.getTipoNivelCR());
                listaCentroResponsabilidad.add(c);
                System.out.println("cantidad: " + listaCentroResponsabilidad.size());
            }

            Double montoA = monto;
            System.out.println("monto: " + montoA);
            for (DetSolicitudCajaChCRBean detSoli : listaDetSolCR) {

                if (distribuir.equals(MaristaConstantes.TIP_DIST_PON)) {
                    new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, montoA);
                    break;
                }
                if (distribuir.equals(MaristaConstantes.TIP_DIST_DIV)) {
                    new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, montoA);
                    break;
                }
                if (distribuir.equals(MaristaConstantes.TIP_DIST_PER)) {
                    new GLTCalculadoraCR().calcularPorPersonalizacion(listaCentroResponsabilidad);
                    break;
                }
            }
            for (CentroResponsabilidadBean resp : listaCentroResponsabilidad) {
                DetSolicitudCajaChCRBean detalle = new DetSolicitudCajaChCRBean();
                detalle.setValor(resp.getMontoDistribucion());
                detalle.getSolicitudCajaCHBean().setIdSolicitudCajaCh(sol.getIdSolicitudCajaCh());
                detalle.getSolicitudCajaCHBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                detalle.getCentroResponsabilidadBean().setCr(resp.getCr());
                solicitudCajaCHService.modificarDetalleCrValorNoC(detalle.getValor(), detalle.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), detalle.getSolicitudCajaCHBean().getUnidadNegocioBean().getUniNeg(),
                        detalle.getCentroResponsabilidadBean().getCr());
            }

//            //3.-Crear Lista Respuesta
//            List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean = new ArrayList<>();
//            for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
//                DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
//                detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
//                detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
//                detSolicitudCajaChCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
//                detSolicitudCajaChCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
//                listaDetSolicitudCajaChCRBean.add(detSolicitudCajaChCRBean);
//            }
//            solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolicitudCajaChCRBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMonto(Double monto) {
        System.out.println(monto);
        solicitudCajaCHBean.setMontoAprobado(monto);
    }

    public void distribuirAprobacion() {
        try {
            //1.-Mappear Dual a Lista
//            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
//            DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad = new DualListModel<>();
//            dualCentroResponsabilidad=dualCentroResponsabilidadBean;
            List<DetSolicitudCajaChCRBean> listaDis = new ArrayList<>();
            listaDis = solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean();
            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
//            for (int i = 0; i < dualCentroResponsabilidadBean.getTarget().size(); i++) {
//                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
//                Object objeto = dualCentroResponsabilidadBean.getTarget().get(i);
//                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
//                listaCentroResponsabilidad.add(cr);
//            } 
            String codigo = "";
            if (listaCentroResponsabilidad != null) {
                if (!listaCentroResponsabilidad.isEmpty()) {
                    //2.-Invocar calculadora
                    for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                        if (solicitudCajaCHBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                codigo = tipoDistribucionCRBean.getCodigo();
                                new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, solicitudCajaCHBean.getMontoAprobado());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                codigo = tipoDistribucionCRBean.getCodigo();
                                new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, solicitudCajaCHBean.getMontoAprobado());
                                System.out.println(solicitudCajaCHBean.getMontoAprobado());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                                codigo = tipoDistribucionCRBean.getCodigo();
                                new GLTCalculadoraCR().calcularPorPersonalizacion(listaCentroResponsabilidad);
                                break;
                            }

                        }
                    }
                    //3.-Crear Lista Respuesta
                    List<DetSolicitudCajaChCRBean> listaDetSolicitudCajaChCRBean = new ArrayList<>();
                    for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                        DetSolicitudCajaChCRBean detSolicitudCajaChCRBean = new DetSolicitudCajaChCRBean();
                        detSolicitudCajaChCRBean.setSolicitudCajaCHBean(solicitudCajaCHBean);
                        detSolicitudCajaChCRBean.setTipoDistribucion(new CodigoBean(solicitudCajaCHBean.getCodDistribucion()));
                        detSolicitudCajaChCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                        detSolicitudCajaChCRBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                        listaDetSolicitudCajaChCRBean.add(detSolicitudCajaChCRBean);
                    }
                    System.out.println("codigo " + codigo);
                    if (codigo.equals(MaristaConstantes.TIP_DIST_PER)) {
                        System.out.println("TIP_DIST_PER");
                        for (DetSolicitudCajaChCRBean list : listaDetSolicitudCajaChCRBean) {
                            for (DetSolicitudCajaChCRBean listD : listaDis) {
                                if (list.getCentroResponsabilidadBean().getCr().equals(listD.getCentroResponsabilidadBean().getCr())) {
                                    System.out.println("entro cr igual");
                                    list.setValorD(listD.getValorD());
                                } else {
                                    System.out.println("no entro cr no igual");
                                }
                                break;
                            }
                        }
                    }
                    solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(listaDetSolicitudCajaChCRBean);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                    solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(null);
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                solicitudCajaCHBean.setListaDetSolicitudCajaChCRBean(null);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private DataTable dataTable;

    public boolean validarMontoPer() throws Exception {
        boolean rpta = false;
        Double sum = 0d;
        for (DetSolicitudCajaChCRBean detSolicitudCajaChCRBean : solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean()) {
            sum = sum + (double) Math.rint(detSolicitudCajaChCRBean.getValorD() * 100D) / 100D;
        }
        double rounded = (double) Math.round(sum * 100D) / 100D;
        if (Objects.equals(rounded, solicitudCajaCHBean.getMonto())) {
            return true;
        }
        return rpta;
    }

    public void obtenerTipoDistribucion() throws Exception {
        for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
            if (solicitudCajaCHBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                    montoDistribucion();
                    break;
                } else {
                    System.out.println("no entró");
                }
            }
        }
    }

    public void montoDistribucion() throws Exception {

        Double sum = 0d;
        for (DetSolicitudCajaChCRBean detSolicitudCajaChCRBean : solicitudCajaCHBean.getListaDetSolicitudCajaChCRBean()) {
            sum = sum + (double) Math.rint(detSolicitudCajaChCRBean.getValorD() * 100D) / 100D;
        }
        Double rounded = (double) Math.round(sum * 100D) / 100D;
        this.montoTotDis = rounded;
        RequestContext.getCurrentInstance().update("frmDatosSoliCCH:pnlDistribucion2");
        RequestContext.getCurrentInstance().update("frmSolCajaCh:pnlDistribucion2");
        System.out.println("montoTotDis " + montoTotDis);
    }

    public void crBarina() {
        setFlgCr(flgCr);
        System.out.println("si chau xD: " + flgCr);
    }

    public void changeTipo(String tipo) {
        try {
            String tipoSol = "";
            if (tipo.equals("soli")) {
                tipoSol = idTipoSol;
            } else {
                tipoSol = idTipoRespCheque;
            }
            switch (tipoSol) {
                case "PER":
                    this.flgFiltroPersona = true;
                    this.flgFiltroPersonal = false;
                    this.flgFiltroProve = false;
                    PersonaMB personaMB = (PersonaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("personaMB");
                    if (personaMB != null) {
                        personaMB.limpiarPersonaFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("personaMB", personaMB);
                    }
                    break;
                case "COL":
                    this.flgFiltroPersonal = true;
                    this.flgFiltroPersona = false;
                    this.flgFiltroProve = false;
                    LegajoMB legajoMB = (LegajoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("legajoMB");
                    if (legajoMB != null) {
                        legajoMB.limpiarPersonalFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("legajoMB", legajoMB);
                    }
                    break;
                case "PRO":
                    this.flgFiltroProve = true;
                    this.flgFiltroPersonal = false;
                    this.flgFiltroPersona = false;
                    EntidadMB entidadMB = (EntidadMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("entidadMB");
                    if (entidadMB != null) {
                        entidadMB.limpiarFiltroEntidad();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("entidadMB", entidadMB);
                    }
                    break;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void settearTipoSoli(String tp) {
        try {
            this.flgFiltroPersonal = true;
            this.flgFiltroPersona = false;
            this.flgFiltroProve = false;
            if (tp.equals("solicitante")) {
                this.flgArendir = Boolean.FALSE;
                if (getIdTipoSol() == null) {
                    setIdTipoSol("COL");
                }
                this.flgSoli = true;
                changeTipo("soli");
            } else {
                TipoSolicitudService tipoSolicitudService = BeanFactory.getTipoSolicitudService();
                TipoSolicitudBean tipo = new TipoSolicitudBean();
                if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud() != null) {
                    solicitudCajaCHBean.getTipoSolicitudBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    tipo = (solicitudCajaCHBean.getTipoSolicitudBean());
                    tipo = tipoSolicitudService.obtenerTipoSolicitudPorId(tipo);
                } else {
                    solicitudCajaCHBean.getTipoSolicitudBean().setNombre("");
                }
                if (tipo != null) {
                    solicitudCajaCHBean.getTipoSolicitudBean().setNombre(tipo.getNombre());
                } else {
                    solicitudCajaCHBean.getTipoSolicitudBean().setNombre("");
                }
                if (getSolicitudCajaCHBean().getTipoSolicitudBean().getNombre() == null) {
                    this.flgArendir = Boolean.FALSE;
                } else {
                    if (solicitudCajaCHBean.getTipoSolicitudBean().getNombre().equals(MaristaConstantes.A_RENDIR)) {
                        this.flgArendir = Boolean.TRUE;
                    } else {
                        this.flgArendir = Boolean.FALSE;
                    }
                }

                if (getIdTipoRespCheque() == null) {
                    setIdTipoRespCheque("COL");
                }
                if (this.flgArendir.equals(Boolean.TRUE)) {
                    this.flgFiltroPersonal = true;
                    this.flgFiltroPersona = false;
                    this.flgFiltroProve = false;
                    setIdTipoRespCheque("COL");
                    System.out.println("xxx");
                } else {
                    if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("CHAMPS")) {
                        this.flgFiltroPersonal = false;
                        this.flgFiltroPersona = false;
                        this.flgFiltroProve = true;
                        setIdTipoRespCheque("PRO");
                        System.out.println("xxx");
                    }
                }
                this.flgSoli = false;
                changeTipo("resp");
            }
            System.out.println("this.flgArendir: " + this.flgArendir);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void respChequeIgualSoli() {
        try {
//            System.out.println(getIdTipoSol()+"/"+"getSolicitudCajaCHBean().getPersonalBean().getIdPersonal().toString()___"+getSolicitudCajaCHBean().getPersonalBean().getIdPersonal().toString());
            if (this.flgIgualSoli.equals(true)) {
                if (getIdTipoSol() != null) {
                    switch (getIdTipoSol()) {
                        case "COL":
                            if (getSolicitudCajaCHBean().getPersonalBean().getIdPersonal().toString() != null) {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getPersonalBean().getIdPersonal().toString());
                            } else {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getIdPersonalSol());
                            }
                            if (getSolicitudCajaCHBean().getPersonalBean().getNombreCompleto() != null) {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getPersonalBean().getNombreCompleto());
                            } else {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getNombreSolicitante());
                            }
                            break;
                        case "PER":
                            if (getSolicitudCajaCHBean().getPersonaBean().getIdPersona().toString() != null) {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getPersonaBean().getIdPersona());
                            } else {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getIdPersonalSol());
                            }
                            if (getSolicitudCajaCHBean().getPersonaBean().getNombreCompletoDesdeApellidos() != null) {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getPersonaBean().getNombreCompletoDesdeApellidos());
                            } else {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getNombreSolicitante());
                            }
                            break;
                        case "PRO":
                            if (getSolicitudCajaCHBean().getEntidadBean().getRuc() != null) {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getEntidadBean().getRuc());
                            } else {
                                getSolicitudCajaCHBean().setIdRespCheque(getSolicitudCajaCHBean().getIdPersonalSol());
                            }
                            if (getSolicitudCajaCHBean().getEntidadBean().getNombre() != null) {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getEntidadBean().getNombre());
                            } else {
                                getSolicitudCajaCHBean().setNomRespCheque(getSolicitudCajaCHBean().getNombreSolicitante());
                            }
                            break;
                    }
                    getSolicitudCajaCHBean().setIdTipoRespCheque(getIdTipoSol());
                }
            } else {
                getSolicitudCajaCHBean().setIdRespCheque(null);
                getSolicitudCajaCHBean().setNomRespCheque(null);
                getSolicitudCajaCHBean().setIdTipoRespCheque(null);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    //Metodos Getter y Setter
    public SolicitudCajaCHBean getSolicitudCajaCHBean() {
        if (solicitudCajaCHBean == null) {
            solicitudCajaCHBean = new SolicitudCajaCHBean();
        }
        return solicitudCajaCHBean;
    }

    public void setSolicitudCajaCHBean(SolicitudCajaCHBean solicitudCajaCHBean) {
        this.solicitudCajaCHBean = solicitudCajaCHBean;
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

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
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

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
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

    public List<CodigoBean> getListTipoSolicitud() {
        if (listTipoSolicitud == null) {
            listTipoSolicitud = new ArrayList<>();
        }
        return listTipoSolicitud;
    }

    public void setListTipoSolicitud(List<CodigoBean> listTipoSolicitud) {
        this.listTipoSolicitud = listTipoSolicitud;
    }

    public List<TipoSolicitudBean> getListaTipoSolicitudBean() {
        if (listaTipoSolicitudBean == null) {
            listaTipoSolicitudBean = new ArrayList<>();
        }
        return listaTipoSolicitudBean;
    }

    public void setListaTipoSolicitudBean(List<TipoSolicitudBean> listaTipoSoicitudBean) {
        this.listaTipoSolicitudBean = listaTipoSolicitudBean;
    }

    public List<UniNegUniOrgBean> getListaUnidadOrganicaBean() {
        if (listaUnidadOrganicaBean == null) {
            listaUnidadOrganicaBean = new ArrayList<>();
        }
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UniNegUniOrgBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
    }

    public List<CodigoBean> getListTipoPrioridad() {
        if (listTipoPrioridad == null) {
            listTipoPrioridad = new ArrayList<>();
        }
        return listTipoPrioridad;
    }

    public void setListTipoPrioridad(List<CodigoBean> listTipoPrioridad) {
        this.listTipoPrioridad = listTipoPrioridad;
    }

    public Boolean getFlgAnular() {
        return flgAnular;
    }

    public void setFlgAnular(Boolean flgAnular) {
        this.flgAnular = flgAnular;
    }

//    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
//        if (listaUnidadNegocioBean == null) {
//            listaUnidadNegocioBean = new ArrayList<>();
//        }
//        return listaUnidadNegocioBean;
//    }
//
//    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
//        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
//    }
    public List<PerfilBean> getListaPerfilBean() {
        if (listaPerfilBean == null) {
            listaPerfilBean = new ArrayList<>();
        }
        return listaPerfilBean;
    }

    public void setListaPerfilBean(List<PerfilBean> listaPerfilBean) {
        this.listaPerfilBean = listaPerfilBean;
    }

    public Boolean getFlgGestorSoli() {
        return flgGestorSoli;
    }

    public void setFlgGestorSoli(Boolean flgGestorSoli) {
        this.flgGestorSoli = flgGestorSoli;
    }

    public List<SolicitudCajaCHBean> getListSolicitudCajaSolicitanteBean() {
        if (listSolicitudCajaSolicitanteBean == null) {
            listSolicitudCajaSolicitanteBean = new ArrayList<>();
        }
        return listSolicitudCajaSolicitanteBean;
    }

    public void setListSolicitudCajaSolicitanteBean(List<SolicitudCajaCHBean> listSolicitudCajaSolicitanteBean) {
        this.listSolicitudCajaSolicitanteBean = listSolicitudCajaSolicitanteBean;
    }

    public List<SolicitudCajaCHBean> getListSolicitudGestorBean() {
        if (listSolicitudGestorBean == null) {
            listSolicitudGestorBean = new ArrayList<>();
        }
        return listSolicitudGestorBean;
    }

    public void setListSolicitudGestorBean(List<SolicitudCajaCHBean> listSolicitudGestorBean) {
        this.listSolicitudGestorBean = listSolicitudGestorBean;
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

    public List<UniNegUniOrgBean> getListaUniNegUniOrgBean() {
        if (listaUniNegUniOrgBean == null) {
            listaUniNegUniOrgBean = new ArrayList<>();
        }
        return listaUniNegUniOrgBean;
    }

    public void setListaUniNegUniOrgBean(List<UniNegUniOrgBean> listaUniNegUniOrgBean) {
        this.listaUniNegUniOrgBean = listaUniNegUniOrgBean;
    }

    public List<CodigoBean> getListTipoStatusSolCajaCh() {
        if (listTipoStatusSolCajaCh == null) {
            listTipoStatusSolCajaCh = new ArrayList<>();
        }
        return listTipoStatusSolCajaCh;
    }

    public void setListTipoStatusSolCajaCh(List<CodigoBean> listTipoStatusSolCajaCh) {
        this.listTipoStatusSolCajaCh = listTipoStatusSolCajaCh;
    }

    public Boolean getMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(Boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public Boolean getFlgMostarListSoli() {
        return flgMostarListSoli;
    }

    public void setFlgMostarListSoli(Boolean flgMostarListSoli) {
        this.flgMostarListSoli = flgMostarListSoli;
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

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidadBean() {
        if (dualCentroResponsabilidadBean == null) {
            dualCentroResponsabilidadBean = new DualListModel<>();
        }
        return dualCentroResponsabilidadBean;
    }

    public void setDualCentroResponsabilidadBean(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean) {
        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
    }

//    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanA() {
//        if (listaCentroResponsabilidadBeanA == null) {
//            listaCentroResponsabilidadBeanA = new ArrayList<>();
//        }
//        return listaCentroResponsabilidadBeanA;
//    }
//
//    public void setListaCentroResponsabilidadBeanA(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanA) {
//        this.listaCentroResponsabilidadBeanA = listaCentroResponsabilidadBeanA;
//    }
    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanB() {
        if (listaCentroResponsabilidadBeanB == null) {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
        }
        return listaCentroResponsabilidadBeanB;
    }

    public void setListaCentroResponsabilidadBeanB(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB) {
        this.listaCentroResponsabilidadBeanB = listaCentroResponsabilidadBeanB;
    }

    public List<CodigoBean> getListaTipoDistribucionCRBean() {
        if (listaTipoDistribucionCRBean == null) {
            listaTipoDistribucionCRBean = new ArrayList<>();
        }
        return listaTipoDistribucionCRBean;
    }

    public void setListaTipoDistribucionCRBean(List<CodigoBean> listaTipoDistribucionCRBean) {
        this.listaTipoDistribucionCRBean = listaTipoDistribucionCRBean;
    }

    public DataTable getDataTable() {
        if (dataTable == null) {
            dataTable = new DataTable();
        }
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public List<PresupuestoBean> getListaPresupuestoBean() {
        if (listaPresupuestoBean == null) {
            listaPresupuestoBean = new ArrayList<>();
        }
        return listaPresupuestoBean;
    }

    public void setListaPresupuestoBean(List<PresupuestoBean> listaPresupuestoBean) {
        this.listaPresupuestoBean = listaPresupuestoBean;
    }

    public Boolean getFlgLimiteMin() {
        if (flgLimiteMin == null) {
            flgLimiteMin = Boolean.FALSE;
        }
        return flgLimiteMin;
    }

    public void setFlgLimiteMin(Boolean flgLimiteMin) {
        this.flgLimiteMin = flgLimiteMin;
    }

    public List<CodigoBean> getListaTipoCajaChica() {
        if (listaTipoCajaChica == null) {
            listaTipoCajaChica = new ArrayList<>();
        }
        return listaTipoCajaChica;
    }

    public void setListaTipoCajaChica(List<CodigoBean> listaTipoCajaChica) {
        this.listaTipoCajaChica = listaTipoCajaChica;
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

    public Boolean getFlgFiltroPersona() {
        return flgFiltroPersona;
    }

    public void setFlgFiltroPersona(Boolean flgFiltroPersona) {
        this.flgFiltroPersona = flgFiltroPersona;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Boolean getFlgFiltroPersonal() {
        return flgFiltroPersonal;
    }

    public void setFlgFiltroPersonal(Boolean flgFiltroPersonal) {
        this.flgFiltroPersonal = flgFiltroPersonal;
    }

    public Boolean getFlgFiltroProve() {
        return flgFiltroProve;
    }

    public void setFlgFiltroProve(Boolean flgFiltroProve) {
        this.flgFiltroProve = flgFiltroProve;
    }

    public Boolean getFlgSoli() {
        return flgSoli;
    }

    public void setFlgSoli(Boolean flgSoli) {
        this.flgSoli = flgSoli;
    }

    public Boolean getFlgIgualSoli() {
        return flgIgualSoli;
    }

    public void setFlgIgualSoli(Boolean flgIgualSoli) {
        this.flgIgualSoli = flgIgualSoli;
    }

    public String getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(String idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public String getIdTipoRespCheque() {
        return idTipoRespCheque;
    }

    public void setIdTipoRespCheque(String idTipoRespCheque) {
        this.idTipoRespCheque = idTipoRespCheque;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudCajaDocEgresoBean() {
        if (listaSolicitudCajaDocEgresoBean == null) {
            listaSolicitudCajaDocEgresoBean = new ArrayList<>();
        }
        return listaSolicitudCajaDocEgresoBean;
    }

    public void setListaSolicitudCajaDocEgresoBean(List<SolicitudCajaCHBean> listaSolicitudCajaDocEgresoBean) {
        this.listaSolicitudCajaDocEgresoBean = listaSolicitudCajaDocEgresoBean;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public List<CodigoBean> getListaTipoPagoBean() {
        if (listaTipoPagoBean == null) {
            listaTipoPagoBean = new ArrayList();
        }
        return listaTipoPagoBean;
    }

    public void setListaTipoPagoBean(List<CodigoBean> listaTipoPagoBean) {
        this.listaTipoPagoBean = listaTipoPagoBean;
    }

    public List<CuentaBancoBean> getListaCuentaBancoBean() {
        if (listaCuentaBancoBean == null) {
            listaCuentaBancoBean = new ArrayList<>();
        }
        return listaCuentaBancoBean;
    }

    public void setListaCuentaBancoBean(List<CuentaBancoBean> listaCuentaBancoBean) {
        this.listaCuentaBancoBean = listaCuentaBancoBean;
    }

    public Double getMontoTotDis() {
        return montoTotDis;
    }

    public void setMontoTotDis(Double montoTotDis) {
        this.montoTotDis = montoTotDis;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public void guardarSolicitudCajaCHGeneralBarina(String tipo) {
        try {
            if (solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud() != null
                    && !solicitudCajaCHBean.getTipoSolicitudBean().getIdTipoSolicitud().equals(0)) {
                if (flgCr == false) {
                    if (validarMontoPer()) {
                        if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                            modificarSolicitudCajaCH();
                        } else {
                            insertarSolicitudCajaCHGenBarinaga(tipo);
                        }
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjSumDistPer");
                    }
                } else {
                    if (solicitudCajaCHBean.getIdSolicitudCajaCh() != null) {
                        modificarSolicitudCajaCH();
                    } else {
                        insertarSolicitudCajaCHGenBarinaga(tipo);
                    }
                }
            } else {
                String tipoSoli = "El campo Tipo Solicitud es requerido";
                new MensajePrime().addInformativeMessagePer(tipoSoli);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Boolean getFlgCr() {
        return flgCr;
    }

    public void setFlgCr(Boolean flgCr) {
        this.flgCr = flgCr;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudesEnSesio() {
        if (listaSolicitudesEnSesio == null) {
            listaSolicitudesEnSesio = new ArrayList<>();
        }
        return listaSolicitudesEnSesio;
    }

    public void setListaSolicitudesEnSesio(List<SolicitudCajaCHBean> listaSolicitudesEnSesio) {
        this.listaSolicitudesEnSesio = listaSolicitudesEnSesio;
    }

    public String getFlgRenderCr() {
        return flgRenderCr;
    }

    public void setFlgRenderCr(String flgRenderCr) {
        this.flgRenderCr = flgRenderCr;
    }

    public Boolean getFlgArendir() {
        return flgArendir;
    }

    public void setFlgArendir(Boolean flgArendir) {
        this.flgArendir = flgArendir;
    }

    public List<PlanContableBean> getListaPlanContableBean() {
        if (listaPlanContableBean == null) {
            listaPlanContableBean = new ArrayList<>();
        }
        return listaPlanContableBean;
    }

    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
        this.listaPlanContableBean = listaPlanContableBean;
    }
}
