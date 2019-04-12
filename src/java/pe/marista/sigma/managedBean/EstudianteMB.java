package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CodigoColegioBean;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.EnfermedadBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.EntidadSedeBean;
import pe.marista.sigma.bean.EstudianteAlergiaBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBloqueoBean;
import pe.marista.sigma.bean.EstudianteDocumentoBean;
import pe.marista.sigma.bean.EstudianteEnfermedadBean;
import pe.marista.sigma.bean.EstudianteInfoBean;
import pe.marista.sigma.bean.EstudianteMedicamentoBean;
import pe.marista.sigma.bean.EstudianteNacimientoBean;
import pe.marista.sigma.bean.EstudianteSeguroBean;
import pe.marista.sigma.bean.EstudianteTraumaBean;
import pe.marista.sigma.bean.EstudianteVacunaBean;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MovilidadBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.ProvinciaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.EstudianteBloqueoRepBean;
import pe.marista.sigma.bean.reporte.EstudianteBloqueosRepBean;
import pe.marista.sigma.bean.reporte.EstudianteMatriculaRepBean;
import pe.marista.sigma.dao.FamiliaDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AdmisionService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DistritoService;
import pe.marista.sigma.service.EnfermedadService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EstudianteAlergiaService;
import pe.marista.sigma.service.EstudianteBloqueoService;
import pe.marista.sigma.service.EstudianteEnfermedadService;
import pe.marista.sigma.service.EstudianteMedicamentoService;
import pe.marista.sigma.service.EstudianteNacimientoService;
import pe.marista.sigma.service.EstudianteSeguroService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.EstudianteTraumaService;
import pe.marista.sigma.service.EstudianteVacunaService;
import pe.marista.sigma.service.FamiliaService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.MovilidadService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class EstudianteMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of EstudianteMB
     */
    @PostConstruct
    public void estudianteMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            //Persona Inicio
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaDocPer();
            listaDocPer = codigoService.obtenerPorTipoDocumento(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademico();
            listaGradoAcademico = gradoAcademicoService.obtenerTodosMatri();
            PaisService paisService = BeanFactory.getPaisService();
            getListaNacionalidad();
            listaNacionalidad = paisService.obtenerPais();
            listaPaisNac = listaNacionalidad;
            getListaStatusEst();
            listaStatusEst = codigoService.obtenerCodigoStatusPost();
            getListaStatusFiltroEst();
            listaStatusFiltroEst = listaStatusEst;
            getListaStatusEstBlo();
            listaStatusEstBlo = codigoService.obtenerPorStatusEst(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_EST_BLO));
            getListaStatusFiltroEstBlo();
            listaStatusFiltroEstBlo = listaStatusEstBlo;
            getListaProcedencia();
            listaProcedencia = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_INGRESO_ESTUDIANTE));
            getListaGradoIngreso();
            listaGradoIngreso = listaGradoAcademico;
            getListaMovilidad();
            listaMovilidad = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOVI));
//            AÃ±o actual
//            Calendar miCalendario = Calendar.getInstance();
            getEstudianteBean();
//            //fecha actual
//            fechaActual = new GregorianCalendar();
//            getEstudianteBean().setFechaIngreso(fechaActual.getTime());
            getListaIdioma();
            listaIdioma = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_IDIOMA));
            getListaViaDomicilio();
            listaViaDomicilio = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VIA_DOMI));
//            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().trim().equals("BARINA")) {
//                getListaSeguroBean();
//                listaSeguroBean = codigoService.obtenerPorTipoSeguro(new TipoCodigoBean(MaristaConstantes.TIP_Seguro));
//            } else {
            getListaSeguroBean();
            listaSeguroBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Seguro));
//            }
            getListaCambioColegio();
            listaCambioColegio = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CAMB_COLE));
            getListaMotivoCambio();
            listaMotivoCambio = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOTIVO_CAMB));
            getListaStatusBloqueo();
            listaStatusBloqueo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Status_Bloqueo));
            listaGradoAcademicoCambio = listaGradoAcademico;
            getListaEstadoCivilPadres();
            listaEstadoCivilPadres = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_EST_CIV));
            getListaEstadoCivilFamiliar();
            listaEstadoCivilFamiliar = listaEstadoCivilPadres;
            getListaViveCon();
            listaViveCon = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VIVE_CON));
            //
            DistritoService distritoService = BeanFactory.getDistritoService();
            getListaDepartamentoNaci();
            listaDepartamentoNaci = distritoService.obtenerDepartamentos();
            getListaDepartamentoDomi();
            listaDepartamentoDomi = listaDepartamentoNaci;
            //
            getListaTipoProfesionBean();
            listaTipoPagoSeguroBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PAGOSEGURO));
            getListaTipoOcupacionBean();
            listaTipoOcupacionBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_OCUPACION));
            getListaTipoParentescoBean();
            listaTipoParentescoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PARENTESCO));
            //info
            getListaParentescoFam1();
            listaParentescoFam1 = codigoService.obtenerParentescoSinPadres();
            getListaParentescoFam2();
            listaParentescoFam2 = listaParentescoFam1;
            getListaTipoCargoBean();
            listaTipoCargoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CARGO));
            //
            getListaEnfermedadBean();
            EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
            listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
            getListaTipoAlergiaBean();
            listaTipoAlergiaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ALERGIA));
            getListaTipoEdadBean();
            listaTipoEdadBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_EDAD));
            getListaTipoVacunaBean();
            listaTipoVacunaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VACUNA));
            listaGrupSanguineo();
            listaFactorSanguineo();
            //periodo de ingreso - periodo seguros
            cargarAnioPeriodo();
            //Cambio
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaViewEntidadSupBean();
            listaViewEntidadSupBean = entidadService.obtenerEntidadPorVista(MaristaConstantes.VIEW_ENT_SAL);
            getListaCodigoBean();
            listaCodigoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_SEGURO));
            listaRespPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
            getListaGradoAcademicoHabilitado();
            listaGradoAcademicoHabilitado = gradoAcademicoService.obtenerTodosMatri();
            //Validar Super Admin

            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLoginBean);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.SUPER_ADMIN)) {
                    personalBean = new PersonalBean();
                    personalBean.setFlgSuperAdmin(true);
                    flgSuperAdmin = true;
                    UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
                    getListaUnidadNegocioBean();
                    listaUnidadNegocioBean = unidadNegocioService.obtenerTodos();
                    break;
                } else {
                    flgSuperAdmin = false;
                    personalBean = new PersonalBean();
                    personalBean.setFlgSuperAdmin(false);
                }
            }
            getEstudianteFiltroBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //Cargar lima por defecto
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();
//            ObtenerEstudianteMovilidad();
//            getEstudianteMovilidadBean();

//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            getListaEstudianteM();
//            listaEstudianteM = estudianteMovilidadService.obtenerEstudiante();
//            getListaMovilidadE();
//            listaMovilidadE = estudianteMovilidadService.obtenerMovilidad();
//            getListaPlacaMovilidad();
//            listaPlacaMovilidad = movilidadService.obtenerListaMovilidad();
//
//            getListaAutorizacionMovilidad();
//            listaAutorizacionMovilidad = movilidadService.obtenerAutorizacion();
//            getListaEstudianteMovilidadBean();
//            obtenerPorFiltro();//Cambios
            //Cambio
            getListaEstudianteEnfermedadBean();
            obtenerDistritoNaci();

            CodigoBean cb = new CodigoBean();
            cb.setCodigo(MaristaConstantes.COD_PAPA);
            cb.setTipoCodigoBean(new TipoCodigoBean(MaristaConstantes.TIP_PARENTESCO));
            getPadreEstudianteBean().setTipoParentescoBean(codigoService.obtenerPorCodigo(cb));
            cb.setCodigo(MaristaConstantes.COD_MAMA);
            getMadreEstudianteBean().setTipoParentescoBean(codigoService.obtenerPorCodigo(cb));
            getEstudianteBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            //ayuda Fecha Bloqueos
            this.fechaBloqueo = new GregorianCalendar();
            getEstudianteBloqueoBean().setAnio(fechaBloqueo.get(Calendar.YEAR));
            getEstudianteBloqueoBean().setFechaBloqueo(fechaBloqueo.getTime());

            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
//            System.out.println("anio a " + anio);
            getListaAnioFiltroMatricula();
            for (int i = Calendar.getInstance().get(Calendar.YEAR) - 2; i <= Calendar.getInstance().get(Calendar.YEAR) + 2; i++) {
                listaAnioFiltroMatricula.add(i);
            }

            Integer anio1 = anio - 100;
            Integer anio2 = anio - 18;
            fechaNacIni = "01/01/" + anio1.toString();
            fechaNacFin = "31/12/" + anio2.toString();
//            System.out.println("fechas 1  " + fechaNacIni);
//            System.out.println("fechas 2  " + fechaNacFin);

            getBloqueoFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            getBloqueoFiltroAfterBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            getBloqueoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            getBloqueoFiltroAfterBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            //            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
//            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaEstudianteBloqueoBean= estudianteBloqueoService.obtenerBloqueoFiltro(estudianteBloqueoBean);

//            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() != MaristaConstantes.UNI_NEG_SANLUI) {
//                for (CodigoBean bean : listaSeguroBean) {
//                    if (bean.getCodigo().equals("Seguro del Colegio")) {
//                        getEstudianteInfoBean().getTipoSeguroBean().setIdCodigo(bean.getIdCodigo());
//                        this.idTipoSeguro = bean.getIdCodigo();
//                    }
//                }
//            }
            getCodigoColegioBean();
            getCodigoColegioBean().setAnio(anio);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private Integer idTipoSeguro;
    private Boolean flgQuieroDatosPadres = false;
    private Boolean flgQuieroDatosMadres = false;
    private List<EstudianteBean> listaEstudianteBean;
    private EstudianteBean estudianteBean;
    private EstudianteBean estudianteVista;
    private List<CodigoBean> listaStatusEst;
    private List<CodigoBean> listaStatusEstBlo;
    private List<CodigoBean> listaStatusFiltroEst;
    private List<CodigoBean> listaStatusFiltroEstBlo;
    private List<CodigoBean> listaProcedencia;
    private List<GradoAcademicoBean> listaGradoIngreso;
    private List<Integer> listaPeriodoIngreso;
    private List<CodigoBean> listaIdioma;
    private List<CodigoBean> listaViaDomicilio;
    private List<PaisBean> listaPaisNac;
    private List<CodigoBean> listaMovilidad;
    //Persona
    private PersonaBean personaBean;
    private List<PersonaBean> listaPersonaBean;
    private List<CodigoBean> listaDocPer;
    private List<GradoAcademicoBean> listaGradoAcademico;
    private List<PaisBean> listaNacionalidad;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private EstudianteBean estudianteFiltroBean;
    private EstudianteInfoBean estudianteInfoBean;
    //EstudianteInfo
    private List<CodigoBean> listaMotivoCambio;
    private List<CodigoBean> listaCambioColegio;
    private List<GradoAcademicoBean> listaGradoAcademicoCambio;
    private List<CodigoBean> listaEstadoCivilPadres;
    private List<CodigoBean> listaEstadoCivilFamiliar;
    private List<CodigoBean> listaViveCon;
    private List<CodigoBean> listaParentescoFam1;
    private List<CodigoBean> listaParentescoFam2;

    //Distritos
    private List<DepartamentoBean> listaDepartamentoNaci;
    private List<ProvinciaBean> listaProvinciaNaci;
    private List<DistritoBean> listaDistritoNaci;
    private List<DepartamentoBean> listaDepartamentoDomi;
    private List<ProvinciaBean> listaProvinciaDomi;
    private List<DistritoBean> listaDistritoDomi;
    //Familiares
    private FamiliarEstudianteBean familiarEstudianteBean;

    private List<FamiliarEstudianteBean> listaFamiliarEstudianteBean;
    private List<CodigoBean> listaTipoProfesionBean;
    private List<CodigoBean> listaTipoOcupacionBean;
    private List<EntidadSedeBean> listaColegioBean;
    private List<CodigoBean> listaTipoParentescoBean;
    private List<EntidadBean> listaEntidadColegioBean;
    private boolean comodin;
    private List<EntidadBean> listaFamCentTraEntBean;
    private List<EntidadSedeBean> listaFamCentTraSedeBean;
    private List<CodigoBean> listaTipoCargoBean;

    //Estado Medico
    private EstudianteEnfermedadBean estudianteEnfermedadBean;
    private EstudianteMedicamentoBean estudianteMedicamentoBean;
    private EstudianteAlergiaBean estudianteAlergiaBean;
    private EstudianteTraumaBean estudianteTraumaBean;
    private List<EnfermedadBean> listaEnfermedadBean;
    private List<EstudianteEnfermedadBean> listaEstudianteEnfermedadBean;
    private List<EstudianteMedicamentoBean> listaEstudianteMedicamentoBean;
    private List<EstudianteAlergiaBean> listaEstudianteAlergiaBean;
    private List<EstudianteTraumaBean> listaEstudianteTraumaBean;
    private List<CodigoBean> listaTipoAlergiaBean;
    private String idPersona;
    private Map<String, String> listaFactorSanMap;
    private Map<String, String> listaGrupSanMap;

    //Estudiante Seguro
    private EstudianteSeguroBean estudianteSeguroBean;
    private List<EstudianteSeguroBean> listaEstudianteSeguroBean;
    private List<CodigoBean> listaCodigoBean;
    private List<ViewEntidadBean> listaViewEntidadSupBean;
    private List<GradoAcademicoBean> listaGradoAcademicoHabilitado;
    private List<Integer> listaPeriodoSeguro;
    //Movilidad
    private MovilidadBean movilidadBean;
    private List<MovilidadBean> listaMovilidadBean;
    private String idmovilidad;
    private List<MovilidadBean> listaPlacaMovilidad;
    private List<MovilidadBean> listaAutorizacionMovilidad;
    private MovilidadBean movilidadFiltro;
    private Boolean flgPaisPeru = true;
//    private EstudianteMovilidadBean estudianteMovilidad;

    //EstudianteMovilidad
//    private EstudianteMovilidadBean estudianteMovilidadBean;
//    private List<EstudianteMovilidadBean> listaEstudianteMovilidadBean;
//    private List<EstudianteMovilidadBean> estudianteMovilidadFiltroBean;
//    private List<EstudianteMovilidadBean> listaEstudianteM;
//    private List<EstudianteMovilidadBean> listaMovilidadE;
    private FamiliaBean familiaBean;
    private List<FamiliaBean> listaFamiliaBean;
    //Super admin
    private PersonalBean personalBean;
    private List<PerfilBean> listaPerfilBean;
    private UsuarioBean usuarioLoginBean;
    //Familiares
    private FamiliarEstudianteBean padreEstudianteBean;
    private FamiliarEstudianteBean madreEstudianteBean;
    private String tipoFam;

    //ayuda
    private Calendar fechaActual;
    //documento admisiÃ³n - estudiante 
    private List<EstudianteDocumentoBean> listaEstudianteDocumentoBean;
    private List<CodigoBean> listaTipoPagoSeguroBean;

    private boolean flgOtros;

    private List<CodigoBean> listaSeguroBean;
    private List<CodigoBean> listaRespPago;

    //Postulante existe en Admision
    private Boolean flgExisteAdmison;
    private PersonaBean responsableBean;
    private List<FamiliarEstudianteBean> listaFamiliarEstudianteRespBean;
    private MatriculaBean matriculaBean;
    private Boolean bloqueoMatricula = Boolean.FALSE;

    private Boolean flgCorreo;
    //EstudianteBloqueo
    private EstudianteBloqueoBean estudianteBloqueoBean;
    private Calendar fechaBloqueo;
    private Boolean flgFiltroPersonal = false;
    private Boolean flgSoli = true;
    private String idTipoSol;
    private Boolean flgGestorSoli = false;
    private List<EstudianteBloqueoBean> listaEstudianteBloqueoBean;
    private List<CodigoBean> listaStatusBloqueo;

    private Boolean flgTodosAfter;
    private Boolean flgPorNivelGradoAfter;
    private Boolean flgEstEspAfter;
    private EstudianteBloqueoBean bloqueoFiltroAfterBean;
    private Boolean flgTodos;
    private Boolean flgEstEsp;
    private Boolean flgPorNivelGrado;
    private Boolean flgPorStatusEst;
    private EstudianteBloqueoBean bloqueoFiltroBean;
    private Boolean flgEstEspMatricula;
    private List<Integer> listaAnioFiltroMatricula;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;

    //EstudianteNacimiento
    private EstudianteNacimientoBean estudianteNacimientoBean;

    // EstudianteVacuna
    private EstudianteVacunaBean estudianteVacunaBean;
    private List<EstudianteVacunaBean> listaEstudianteVacunaBean;
    private List<CodigoBean> listaTipoEdadBean;
    private List<CodigoBean> listaTipoVacunaBean;
    private List<EstudianteVacunaBean> listaEstudianteVacunasEstBean;

    private Integer anio;

    private Boolean flgDniCorrectoPapa = false;
    private Boolean flgDniCorrectoMapa = false;
    private Boolean flgDniCorrectoApo = false;
    private Boolean flgCorreoCorrectoPapa = false;
    private Boolean flgCorreoCorrectoMama = false;
    private Boolean flgCorreoCorrectoApo = false;
    private Boolean flgGrabar = false;

    private Boolean result;

    private Integer numeroCodPer = 0;
    private Integer numeroCodMa = 0;
    private Integer numeroCodFa = 0;
    private String fechaNacIni;
    private String fechaNacFin;

    private List<CodigoColegioBean> listaCodigoColeBean;
    private CodigoColegioBean codigoColegioBean;
    //ayudaexcel
    private List<MatriculaBean> listaMatriculaBean;
    //
    private List<MatriculaBean> listaMatriculaEstudianteMasivoBean;

    //AYUDA PERFIL
    private Boolean flgSuperAdmin = false;
    private Boolean flgReingresante = false;

    private Boolean flgMatriculado = false;

    //Logica de Negocio
    public static Boolean isNumeric(String str) {
        return (str.matches("[+-]?\\d*(\\.\\d+)?") && str.equals("") == false);
    }

    public void validarDNI(String str, String tipo) {
        System.out.println("str ---" + str);
        System.out.println("tipo ---" + tipo);
        if (str == null) {
            System.out.println("str nulllllllllll" + str);
        } else {
            if (!str.equals(null) && !str.equals("") && !tipo.equals(null)) {
                if (tipo.equals("padre")) {
                    if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                        if (isNumeric(str) == true) {
                            System.out.println("Entrada OK, introdujo el número " + str);
                            flgDniCorrectoPapa = true;
                            getPadreEstudianteBean().getFamiliarBean().getPersonaBean().setIdPersona(str);
                        } else {
                            System.out.println("Entrada no válida, no introdujo un número " + str);
                            flgDniCorrectoPapa = false;
                        }
                    } else {
                        flgDniCorrectoPapa = true;
                        System.out.println("Carnet de Extranjeria");
                    }
                } else if (tipo.equals("madre")) {
                    if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                        if (isNumeric(str) == true) {
                            System.out.println("Entrada OK, introdujo el número " + str);
                            flgDniCorrectoMapa = true;
                            getMadreEstudianteBean().getFamiliarBean().getPersonaBean().setIdPersona(str);
                        } else {
                            System.out.println("Entrada no válida, no introdujo un número " + str);
                            flgDniCorrectoMapa = false;
                        }
                    } else {
                        flgDniCorrectoMapa = true;
                        System.out.println("Carnet de Extranjeria");
                    }
                } else if (tipo.equals("otro")) {
                    if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                        if (isNumeric(str) == true) {
                            System.out.println("Entrada OK, introdujo el número " + str);
                            flgDniCorrectoApo = true;
                            getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().setIdPersona(str);
                        } else {
                            System.out.println("Entrada no válida, no introdujo un número " + str);
                            flgDniCorrectoApo = false;
                        }
                    } else {
                        System.out.println("Carnet de Extranjeria");
                        flgDniCorrectoApo = true;
                    }
                }
            }
        }
    }

    public static Boolean isCorreoValido(String str) {
        return (str.matches("^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$") && str.equals("") == false);
    }

    public void validarCorreo(String str, String tipo) {
        System.out.println("str  ---" + str);
        System.out.println("tipo ---" + tipo);
        if (str == null) {
            System.out.println("str nulllllllllll" + str);
        } else {
            if (!str.equals(null) && !str.equals("") && !tipo.equals(null)) {
                if (tipo.equals("padre")) {
                    if (isCorreoValido(str) == true) {
                        System.out.println("Entrada OK, introdujo el correo " + str);
                        flgCorreoCorrectoPapa = true;
                    } else {
                        System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + str);
                        flgCorreoCorrectoPapa = false;
                    }
                }
                if (tipo.equals("madre")) {
                    if (isCorreoValido(str) == true) {
                        System.out.println("Entrada OK, introdujo el número " + str);
                        flgCorreoCorrectoMama = true;
                    } else {
                        System.out.println("Entrada no válida, Formato de E-mail de la Madre no válido " + str);
                        flgCorreoCorrectoMama = false;
                    }
                }
                if (tipo.equals("otro")) {
                    if (isCorreoValido(str) == true) {
                        System.out.println("Entrada OK, introdujo el número " + str);
                        flgCorreoCorrectoApo = true;
                    } else {
                        System.out.println("Entrada no válida, Formato de E-mail del Apoderado no válido " + str);
                        flgCorreoCorrectoApo = false;
                    }
                }
            }
        }
    }

    public void obtenerPaisPorDefectoPeru() {

        PaisService paisService = BeanFactory.getPaisService();
        Integer cod = null;
        cod = estudianteBean.getPaisNaciBean().getIdPais();
        PaisBean pais = new PaisBean();
//        pais = paisService.obtenerPaisPorDefectoPeru(nombre);
        if (cod.equals(MaristaConstantes.COD_PERU)) {
            estudianteBean.getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().setIdDepartamento(MaristaConstantes.DEP_LIMA);
            estudianteBean.getIdDistritoNaci().getProvinciaBean().setIdProvincia(MaristaConstantes.PROV_LIMA);
            estudianteBean.getIdDistritoNaci().setIdDistrito(null);
            this.flgPaisPeru = true;
        } else {
            this.flgPaisPeru = false;
            estudianteBean.getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().setIdDepartamento(null);
            estudianteBean.getIdDistritoNaci().getProvinciaBean().setIdProvincia(null);
            estudianteBean.getIdDistritoNaci().setIdDistrito(null);
        }
    }

    public void copiarCodigoEnNroDoc() {
        String codigo = null;
        codigo = estudianteBean.getPersonaBean().getIdPersona();
        if (codigo != null) {
            estudianteBean.getPersonaBean().setNroDoc(codigo);
        }
    }

    public void obtenerEstudiante() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            listaEstudianteBean = estudianteService.obtenerEstudiante();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAlumnoReingreso() {
        try {
//          if(estudianteBean.getTipoIngresoEst().get){}
            System.out.println("ss " + flgReingresante);
            System.out.println("reingreso: " + estudianteBean.getTipoIngresoEst().getCodigo());
            System.out.println("reingreso2: " + estudianteBean.getTipoIngresoEst().getIdCodigo());
            if (estudianteBean.getTipoIngresoEst().getIdCodigo().equals(MaristaConstantes.COD_REINGRESO)) {
                this.flgReingresante = true;

                System.out.println("aa:" + flgReingresante);
                System.out.println("entro");
            } else {
                this.flgReingresante = false;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePost() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            listaEstudianteBean = estudianteService.obtenerEstudiantePost();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorUniNeg() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            if (beanUsuarioSesion != null) {
                listaEstudianteBean = estudianteService.obtenerEstudiantePorUniNeg((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePostPorUniNeg() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            listaEstudianteBean = estudianteService.obtenerEstudiantePostPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiante() {
        try {
            int estado = 1;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteFiltroBean.setAnio(anio);
            if (estudianteFiltroBean.getCodigo() != null && !estudianteFiltroBean.getCodigo().equals("")) {
                estudianteFiltroBean.setCodigo(estudianteFiltroBean.getCodigo());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getIdPersona() != null && !estudianteFiltroBean.getPersonaBean().getIdPersona().equals("")) {
                estudianteFiltroBean.getPersonaBean().setIdPersona(estudianteFiltroBean.getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApepat() != null && !estudianteFiltroBean.getPersonaBean().getApepat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApepat(estudianteFiltroBean.getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApemat() != null && !estudianteFiltroBean.getPersonaBean().getApemat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApemat(estudianteFiltroBean.getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getNombre() != null && !estudianteFiltroBean.getPersonaBean().getNombre().equals("")) {
                estudianteFiltroBean.getPersonaBean().setNombre(estudianteFiltroBean.getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getTipoStatusEst().getIdCodigo() != null && !estudianteFiltroBean.getTipoStatusEst().getIdCodigo().equals(0)) {
                estudianteFiltroBean.getTipoStatusEst().setIdCodigo(estudianteFiltroBean.getTipoStatusEst().getIdCodigo());
                estado = 1;
            } //            if (estudianteFiltroBean.getIngresoEst().getIdCodigo() != null && !estudianteFiltroBean.getIngresoEst().getIdCodigo().equals(0)) {
            //                estudianteFiltroBean.getIngresoEst().setIdCodigo(estudianteFiltroBean.getIngresoEst().getIdCodigo());
            //                estado = 1;
            //            }
            else if (estado == 0) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                estudianteFiltroBean.setUsuarioMatricula(matriculaBean.getCreaPor());
                estudianteFiltroBean.setAnio(anio);
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteFicha() {
        try {
            int estado = 1;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteFiltroBean.setAnio(anio);
            if (estudianteFiltroBean.getCodigo() != null && !estudianteFiltroBean.getCodigo().equals("")) {
                estudianteFiltroBean.setCodigo(estudianteFiltroBean.getCodigo());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getIdPersona() != null && !estudianteFiltroBean.getPersonaBean().getIdPersona().equals("")) {
                estudianteFiltroBean.getPersonaBean().setIdPersona(estudianteFiltroBean.getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApepat() != null && !estudianteFiltroBean.getPersonaBean().getApepat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApepat(estudianteFiltroBean.getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApemat() != null && !estudianteFiltroBean.getPersonaBean().getApemat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApemat(estudianteFiltroBean.getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getNombre() != null && !estudianteFiltroBean.getPersonaBean().getNombre().equals("")) {
                estudianteFiltroBean.getPersonaBean().setNombre(estudianteFiltroBean.getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getTipoStatusEst().getIdCodigo() != null && !estudianteFiltroBean.getTipoStatusEst().getIdCodigo().equals(0)) {
                estudianteFiltroBean.getTipoStatusEst().setIdCodigo(estudianteFiltroBean.getTipoStatusEst().getIdCodigo());
                estado = 1;
            } //            if (estudianteFiltroBean.getIngresoEst().getIdCodigo() != null && !estudianteFiltroBean.getIngresoEst().getIdCodigo().equals(0)) {
            //                estudianteFiltroBean.getIngresoEst().setIdCodigo(estudianteFiltroBean.getIngresoEst().getIdCodigo());
            //                estado = 1;
            //            }
            else if (estado == 0) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                estudianteFiltroBean.setUsuarioMatricula(matriculaBean.getCreaPor());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudianteFicha(estudianteFiltroBean);
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudianteFicha(estudianteFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiantePost() {
        try {
            int estado = 0;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            if (estudianteFiltroBean.getAnioIngreso() != null && !estudianteFiltroBean.getAnioIngreso().equals(0)) {
                estudianteFiltroBean.setAnioIngreso(estudianteFiltroBean.getAnioIngreso());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getIdPersona() != null && !estudianteFiltroBean.getPersonaBean().getIdPersona().equals("")) {
                estudianteFiltroBean.getPersonaBean().setIdPersona(estudianteFiltroBean.getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApepat() != null && !estudianteFiltroBean.getPersonaBean().getApepat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApepat(estudianteFiltroBean.getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getApemat() != null && !estudianteFiltroBean.getPersonaBean().getApemat().equals("")) {
                estudianteFiltroBean.getPersonaBean().setApemat(estudianteFiltroBean.getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (estudianteFiltroBean.getPersonaBean().getNombre() != null && !estudianteFiltroBean.getPersonaBean().getNombre().equals("")) {
                estudianteFiltroBean.getPersonaBean().setNombre(estudianteFiltroBean.getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            } //            if (estudianteFiltroBean.getStatusEst().getIdCodigo() != null && !estudianteFiltroBean.getStatusEst().getIdCodigo().equals(0)) {
            //                estudianteFiltroBean.getStatusEst().setIdCodigo(estudianteFiltroBean.getStatusEst().getIdCodigo());
            //                estado = 1;
            //            }
            //            if (estudianteFiltroBean.getIngresoEst().getIdCodigo() != null && !estudianteFiltroBean.getIngresoEst().getIdCodigo().equals(0)) {
            //                estudianteFiltroBean.getIngresoEst().setIdCodigo(estudianteFiltroBean.getIngresoEst().getIdCodigo());
            //                estado = 1;
            //            }
            else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaEstudianteBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEstudiante(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
//                estudianteBean.setPersonaBean(personaBean);
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                if (estudianteBean.getRefeLugarNaci().equals("")) {
                    estudianteBean.setRefeLugarNaci(null);
                }
                System.out.println("codigo 1: " + estudianteBean.getCodigo());
                estudianteBean.getPersonaBean().setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                estudianteBean.setCreaPor(usuarioBean.getUsuario());
                estudianteBean.getPersonaBean().setCreaPor(usuarioBean.getUsuario());
                estudianteService.insertarEstudiante(estudianteBean, tipo, usuarioLoginBean);
                if (tipo.equals("postulante")) {
//                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                } else {
//                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudiante(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                if (estudianteBean.getTipoRespPago().getIdCodigo() != null) {
                    if (estudianteBean.getTipoRespPago().getIdCodigo() == 17401) {
                        estudianteBean.getRespPagoBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    }
                    if (estudianteBean.getTipoRespPago().getIdCodigo() == 17402) {
                        estudianteBean.getRespPagoBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    }
                    if (estudianteBean.getTipoRespPago().getIdCodigo() == 17403) {
                        estudianteBean.getRespPagoBean().setIdPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    }
                }
                System.out.println("codigo 1: " + estudianteBean.getCodigo());
                estudianteBean.getGradoHabilitado().setIdGradoAcademico(estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
                estudianteService.modificarEstudiante(estudianteBean, tipo, usuarioLoginBean);
//                System.out.println("insertarEstudianteSeguroRapidoVer2xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                insertarEstudianteSeguroRapidoVer2();
                estudianteBean.setModiPor(usuarioBean.getUsuario());
                if (tipo.equals("postulante")) {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                } else {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String actualizarGrupoSanguineo(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteService.actualizarGrupoSanguineo(estudianteBean);
                if (tipo.equals("postulante")) {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                } else {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String actualizarEmfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
                listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void listaFactorSanguineo() {
        listaFactorSanMap = new LinkedHashMap<>();
        listaFactorSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaRHpos", null), "RH+");
        listaFactorSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaRHneg", null), "RH-");
        listaFactorSanMap = Collections.unmodifiableMap(listaFactorSanMap);
    }

    public void listaGrupSanguineo() {
        listaGrupSanMap = new LinkedHashMap<>();
        listaGrupSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaO", null), "O");
        listaGrupSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaA", null), "A");
        listaGrupSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaB", null), "B");
        listaGrupSanMap.put(MensajesBackEnd.getValueOfKey("etiquetaAB", null), "AB");
        listaGrupSanMap = Collections.unmodifiableMap(listaGrupSanMap);
    }

    public String actualizarEntidadSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
//                listaEntidadBean = entidadService.obtenerTodosSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerPais() {
        try {
            PaisService paisService = BeanFactory.getPaisService();
            PaisBean paisBean = new PaisBean();
            listaNacionalidad = paisService.obtenerPais();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String eliminarEstudiante(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
//                estudianteService.eliminarEstudiante(estudianteBean.getIdEstudiante());
                if (tipo.equals("postulante")) {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                } else {
                    listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePost(estudianteFiltroBean);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void cargarAnioPeriodo() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int inicio = MaristaConstantes.ANO_INI_DEFAULT_COLE;
            int fin = miCalendario.get(Calendar.YEAR) + 5;
            listaPeriodoIngreso = new ArrayList<>();
            listaPeriodoSeguro = new ArrayList<>();
            for (int i = inicio; i <= fin; i++) {
                listaPeriodoIngreso.add(i);
                listaPeriodoSeguro.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarEstudiante(String tipo) {
        try {
            if (tipo.equals("postulante")) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                EstudianteBean estudiante1 = new EstudianteBean();
                estudianteBean.setAnio(anio);
                System.out.println("codigo 1: " + estudianteBean.getCodigo());
                estudiante1 = estudianteService.obtenerEstPorId(estudianteBean);
                if (estudiante1 != null) {
                    modificarEstudiante(tipo);
                } else {
                    estudiante1 = new EstudianteBean();
                    insertarEstudiante(tipo);
                }
            } else {
                if (estudianteBean.getPersonaBean().getIdPersona() != null) {
                    modificarEstudiante(tipo);
                } else {
                    insertarEstudiante(tipo);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarEstudianteRapido(String tipo) {
        try {
            System.out.println("sdfghj");
            if (tipo.equals("postulante")) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                EstudianteBean estudiante1 = new EstudianteBean();
                estudianteBean.setAnio(anio);
                estudiante1 = estudianteService.obtenerEstPorId(estudianteBean);
                if (estudiante1 != null) {
                    modificarEstudiante(tipo);
                } else {
                    estudiante1 = new EstudianteBean();
                    insertarEstudiante(tipo);
                }
            } else {
                if (estudianteBean.getPersonaBean().getIdPersona() != null) {
                    modificarEstudiante(tipo);
                } else {
                    insertarEstudiante(tipo);
                }
            }
            //   guardarFamiliar(tipo);
            guardarEstudianteInfo(tipo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void isValidarMensajeFaltCampos() {
        try {
            Integer limite = 5;

            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() == null
                    || padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")) {
                String apePat = "Falta Apellido Paterno del Papá";
                new MensajePrime().addInformativeMessagePer(apePat);
            }
            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() == null
                    || padreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")) {
                String apeMat = "Falta Apellido Materno del Papá";
                new MensajePrime().addInformativeMessagePer(apeMat);
            }
            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() == null
                    || padreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")) {
                String nombre = "Falta Nombre del Papá";
                new MensajePrime().addInformativeMessagePer(nombre);
            }
            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null) {
                String tipodni = "Falta Tipo Documento del Papá";
                new MensajePrime().addInformativeMessagePer(tipodni);
            }
            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() == null
                    || padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                String dni = "Falta DNI del Papá";
                new MensajePrime().addInformativeMessagePer(dni);
            }
            if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() == null) {
                String fecNac = "Falta Fecha Nacimiento del Papá";
                new MensajePrime().addInformativeMessagePer(fecNac);
            }
            if (padreEstudianteBean.getFamiliarBean().getDireccion() == null
                    || padreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
                String direccion = "Falta Direccion del Papá";
                new MensajePrime().addInformativeMessagePer(direccion);
            } else {
                if (padreEstudianteBean.getFamiliarBean().getDireccion().length() < limite) {
                    String tamañoDireccion = "Mas información de la direccion del Padre";
                    new MensajePrime().addInformativeMessagePer(tamañoDireccion);
                }
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() == null
                    || madreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")) {
                String apePat = "Falta Apellido Paterno del Mamá";
                new MensajePrime().addInformativeMessagePer(apePat);
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() == null
                    || madreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")) {
                String apeMat = "Falta Apellido Materno del Mamá";
                new MensajePrime().addInformativeMessagePer(apeMat);
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() == null
                    || madreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")) {
                String nombre = "Falta Nombre del Mamá";
                new MensajePrime().addInformativeMessagePer(nombre);
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null) {
                String tipodni = "Falta Tipo Documento del Mamá";
                new MensajePrime().addInformativeMessagePer(tipodni);
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() == null
                    || madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                String dni = "Falta DNI del Mamá";
                new MensajePrime().addInformativeMessagePer(dni);
            }
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() == null) {
                String fecNac = "Falta Fecha Nacimiento del Mamá";
                new MensajePrime().addInformativeMessagePer(fecNac);
            }
            if (madreEstudianteBean.getFamiliarBean().getDireccion() == null
                    || madreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
                String direccion = "Falta Direccion del Mamá";
                new MensajePrime().addInformativeMessagePer(direccion);
            } else {
                if (madreEstudianteBean.getFamiliarBean().getDireccion().length() < limite) {
                    String tamañoDireccion = "Mas información de la direccion de la Madre";
                    new MensajePrime().addInformativeMessagePer(tamañoDireccion);
                }
            }
            System.out.println("familiar: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat());
            if (!familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")
                    || !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")
                    || !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")
                    || !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                if (familiarEstudianteBean.getFamiliarBean().getDireccion() == null
                        || familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")) {
                    String apePat = "Falta Apellido Paterno del Apoderado";
                    new MensajePrime().addInformativeMessagePer(apePat);
                }
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() == null
                        || familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")) {
                    String apeMat = "Falta Apellido Materno del Apoderado";
                    new MensajePrime().addInformativeMessagePer(apeMat);
                }
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() == null
                        || familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")) {
                    String nombre = "Falta Nombre del Apoderado";
                    new MensajePrime().addInformativeMessagePer(nombre);
                }
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null
                        || familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 0) {
                    System.out.println("tipodni: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo());
                    String tipodni = "Falta Tipo Documento del Apoderado";
                    new MensajePrime().addInformativeMessagePer(tipodni);
                }
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() == null
                        || familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                    String dni = "Falta DNI del Apoderado";
                    new MensajePrime().addInformativeMessagePer(dni);
                }
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() == null) {
                    String fecNac = "Falta Fecha Nacimiento del Apoderado";
                    new MensajePrime().addInformativeMessagePer(fecNac);
                }
                if (familiarEstudianteBean.getFamiliarBean().getDireccion() == null
                        || familiarEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
                    String direccion = "Falta Direccion del Apoderado";
                    new MensajePrime().addInformativeMessagePer(direccion);
                } else {
                    if (familiarEstudianteBean.getFamiliarBean().getDireccion().length() < limite) {
                        String tamañoDireccion = "Mas información de la direccion del Apoderado";
                        new MensajePrime().addInformativeMessagePer(tamañoDireccion);
                    }
                }
                if (familiarEstudianteBean.getTipoParentescoBean().getIdCodigo() == null
                        || familiarEstudianteBean.getTipoParentescoBean().getIdCodigo() == 0) {
                    String parentezco = "Falta Parentezco del Apoderado";
                    new MensajePrime().addInformativeMessagePer(parentezco);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarPrimerRapido(String tipo) {
        try {
            Integer limite = 5;
            System.out.println("padre: " + padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("madre: " + madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("apo: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("apo: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
            System.out.println("fec xx" + estudianteBean.getPersonaBean().getFecNac());
            System.out.println("sex xx" + estudianteBean.getPersonaBean().getSexo());
            Integer estadPad = 0;
            Integer estadoMad = 0;
            Integer estadoApo = 0;
//            Integer obs = 0;
            System.out.println("distrito" + estudianteBean.getIdDistritoDomi().getIdDistrito());
//            Integer estadoApo = 0;
            validarDNI(padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc(), "padre");
            validarDNI(madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc(), "madre");
            validarDNI(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc(), "otro");
            validarCorreo(padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo(), "padre");
            validarCorreo(madreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo(), "madre");
            validarCorreo(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo(), "otro");
            if (estudianteBean.getViaDomi() != null && !estudianteBean.getViaDomi().trim().equals("")
                    && estudianteBean.getIdDistritoDomi().getIdDistrito() != null && !estudianteBean.getIdDistritoDomi().getIdDistrito().equals(0)
                    && estudianteBean.getTipoRespPago().getIdCodigo() != null && !estudianteBean.getTipoRespPago().getIdCodigo().equals(0)
                    && estudianteInfoBean.getTipoSeguroBean().getIdCodigo() != null && !estudianteInfoBean.getTipoSeguroBean().getIdCodigo().equals(0)
                    && estudianteBean.getViaDomi().length() > limite //                    && estudianteInfoBean.getViveCon().getIdCodigo() != null && !estudianteInfoBean.getViveCon().getIdCodigo().equals(0)
                    ) {
//                if (estudianteInfoBean.getViveCon().getIdCodigo() == 14606) {
//                    if (estudianteInfoBean.getObsFamiliar() == null || estudianteInfoBean.getObsFamiliar().equals("")) {
//                        new MensajePrime().addInformativeMessagePer("Falta llenar la Observacion de con quien vive");
//                        obs = 0;
//                    } else {
//                        obs = 1;
//                    }
//                }
//                obs = 1;
                System.out.println("apo: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                System.out.println("apo: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")
                        || getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null) {
                    System.out.println("entro getFamiliarEstudianteBean :D");
                    if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() != null
                            && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")
                            && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                            && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")
                            && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() != null
                            && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")
                            && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() != null
                            && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                            && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")
                            && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() != null
                            && familiarEstudianteBean.getFamiliarBean().getDireccion() != null
                            && !familiarEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")
                            && familiarEstudianteBean.getFamiliarBean().getFlgVive() != null
                            && familiarEstudianteBean.getTipoParentescoBean().getIdCodigo() != null
                            && familiarEstudianteBean.getFamiliarBean().getDireccion().length() > limite) {
                        guardarFamiliarEstudianteRapido(familiarEstudianteBean);
//                        FamiliaService familiaService = BeanFactory.getFamiliaService();
//                        familiaBean.setIdFamilia(familiarEstudianteBean.getEstudianteBean().getFamiliaBean().getIdFamilia());
//                        familiaBean.getUnidadNegocioBean().setUniNeg(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().getUniNeg());
//                        familiaService.modificarFamiliaRapido(familiaBean);
                        estadoApo = 1;
                    }
                } else {
                    estadoApo = 0;
//                    new MensajePrime().addInformativeMessagePer("msjFaltaCampos");
                    System.out.println("no entra getFamiliarEstudianteBean :D");
//                    if (!padreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
//                        new MensajePrime().addInformativeMessagePer("Mas información de la direccion del Apoderado");
//                    }
                }
                if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null
                        && !getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() != null
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() != null
                        //                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo() != null
                        //                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getDireccion() != null
                        && !padreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")
                        //                        && padreEstudianteBean.getFamiliarBean().getTelefonoCelular() != null
                        //                        && !padreEstudianteBean.getFamiliarBean().getTelefonoCelular().trim().equals("")
                        && padreEstudianteBean.getFamiliarBean().getFlgVive() != null
                        //                        && padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals(estudianteBean.getPersonaBean().getApepat())
                        && padreEstudianteBean.getFamiliarBean().getDireccion().length() > limite) {
                    System.out.println("entro getPadreEstudianteBean :D");
                    if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo() != null
                            || !padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                        flgCorreo = true;
                    }
                    getPadreEstudianteBean().getTipoParentescoBean().setIdCodigo(12402);
                    guardarFamiliarEstudianteRapido(padreEstudianteBean);
                    estadPad = 1;
                } else if (flgQuieroDatosPadres == true) {
//                    guardarFamiliarEstudianteRapido(padreEstudianteBean);
                    flgDniCorrectoPapa = true;
                    estadPad = 1;
                    System.out.println("entro sin datos papa");
                } else {
                    estadPad = 0;
//                    isValidarMensajeFaltCampos();
//                    new MensajePrime().addInformativeMessagePer("Padres no coinciden en el mismo apellido");
                    System.out.println("no entra getPadreEstudianteBean :D");
//                    if (!padreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
//                        new MensajePrime().addInformativeMessagePer("Mas información de la direccion del Padre");
//                    }
                }

                if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getApemat().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getApepat().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getNombre().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() != null
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getFecNac() != null
                        //                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo() != null
                        //                        && !getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getDireccion() != null
                        && !getMadreEstudianteBean().getFamiliarBean().getDireccion().trim().equals("")
                        //                        && madreEstudianteBean.getFamiliarBean().getTelefonoCelular() != null
                        //                        && !getMadreEstudianteBean().getFamiliarBean().getTelefonoCelular().trim().equals("")
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() != null
                        //                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals(estudianteBean.getPersonaBean().getApepat())
                        && madreEstudianteBean.getFamiliarBean().getDireccion().length() > limite) {
                    System.out.println("entro getMadreEstudianteBean :D");

                    getMadreEstudianteBean().getTipoParentescoBean().setIdCodigo(12403);
                    System.out.println("codigo..." + getPadreEstudianteBean().getTipoParentescoBean().getIdCodigo());
                    guardarFamiliarEstudianteRapido(madreEstudianteBean);
                    estadoMad = 1;
                } else if (flgQuieroDatosMadres == true) {
//                    guardarFamiliarEstudianteRapido(madreEstudianteBean);
                    flgDniCorrectoMapa = true;
                    estadoMad = 1;
                    System.out.println("entro sin datos papa");
                } else {
                    estadoMad = 0;
//                    new MensajePrime().addInformativeMessagePer("msjFaltaCampos");
                    System.out.println("no entra getMadreEstudianteBean :D");
//                    new MensajePrime().addInformativeMessagePer("Padres no coinciden en el mismo apellido");
//                    if (!madreEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
//                        new MensajePrime().addInformativeMessagePer("Mas información de la direccion de la Madre");
//                    }
                }

                System.out.println("estadoApo: " + estadoApo);
                System.out.println("estadoApo: " + estadoMad);
                System.out.println("estadoApo: " + estadPad);
                if (estadoApo.equals(0)) {
                    if (estadPad.equals(1) || estadoMad.equals(1) || flgDniCorrectoMapa == true || flgDniCorrectoPapa == true) {
                        if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals("")
                                && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat().trim().equals("")
                                && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre().trim().equals("")
                                && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")
                                && familiarEstudianteBean.getFamiliarBean().getDireccion().trim().equals("")) {
                            System.out.println("xxxxxxxxxxxx1");
                            if (estudianteBean.getViaDomi().length() > limite) {
                                guardarEstudianteRapido("estudiante");
                                if (estudianteBean.getFamiliaBean().getIdFamilia() != null) {
                                    FamiliaService familiaService = BeanFactory.getFamiliaService();
                                    FamiliaBean familia = new FamiliaBean();
                                    familia.setIdFamilia(padreEstudianteBean.getEstudianteBean().getFamiliaBean().getIdFamilia());
                                    familia.getUnidadNegocioBean().setUniNeg(padreEstudianteBean.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().getUniNeg());
                                    familia.setModiPor(usuarioLoginBean.getUsuario());
                                    familia.getPadreBean().getPersonaBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                                    familia.getMadreBean().getPersonaBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                                    familiaService.modificarFamiliaRapido(familia);
                                }
                            } else {
                                new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                            }
                            flgGrabar = true;
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else {
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                            isValidarMensajeFaltCampos();
                            if (estudianteBean.getViaDomi().length() > limite) {

                            } else {
                                new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                            }
                        }
                        //operacionCorrecta
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
//                        new MensajePrime().addInformativeMessagePer("msjFaltaCampos");
                        if (flgDniCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI del Papá no son números");
                        }
                        if (flgDniCorrectoMapa == false && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI de la Mamá no son números");
                        }
                        if (flgCorreoCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail del Papá formato incorrecto");
                        }
                        if (flgCorreoCorrectoMama == false && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail de la Mamá formato incorrecto");
                        }
                        isValidarMensajeFaltCampos();
                        if (estudianteBean.getViaDomi().length() > limite) {

                        } else {
                            new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                        }
                    }
                } else {
                    if (estadPad.equals(1) && estadoMad.equals(1) && estadoApo.equals(1) && flgDniCorrectoMapa == true && flgDniCorrectoPapa == true && flgDniCorrectoApo == true) {
                        System.out.println("xxxxxxxxxxxx2");
                        if (estudianteBean.getViaDomi().length() > limite) {
                            guardarEstudianteRapido("estudiante");
                            if (estudianteBean.getFamiliaBean().getIdFamilia() != null) {
                                FamiliaService familiaService = BeanFactory.getFamiliaService();
                                FamiliaBean familia = new FamiliaBean();
                                familia.setIdFamilia(padreEstudianteBean.getEstudianteBean().getFamiliaBean().getIdFamilia());
                                familia.getUnidadNegocioBean().setUniNeg(padreEstudianteBean.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().getUniNeg());
                                familia.setModiPor(usuarioLoginBean.getUsuario());
                                familia.getPadreBean().getPersonaBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                                familia.getMadreBean().getPersonaBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                                familiaService.modificarFamiliaRapido(familia);
                            }
                        } else {
                            new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                        }
                        flgGrabar = true;
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        //operacionCorrecta
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
//                        new MensajePrime().addInformativeMessagePer("msjFaltaCampos");
                        if (flgDniCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI del Papá no son números");
                        }
                        if (flgDniCorrectoMapa == false && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI de la Mamá no son números");
                        }
                        if (flgDniCorrectoApo == false && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI del Apoderado no son números");
                        }
                        if (flgCorreoCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail del Papá formato incorrecto");
                        }
                        if (flgCorreoCorrectoMama == false && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail de la Mamá formato incorrecto");
                        }
                        if (flgCorreoCorrectoApo == false && !familiarEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                            new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail del Apoderado formato incorrecto");
                        }
                        isValidarMensajeFaltCampos();
                        if (estudianteBean.getViaDomi().length() > limite) {

                        } else {
                            new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                        }
                    }
                }
            } else {
                if (estudianteBean.getViaDomi() == null || estudianteBean.getViaDomi().trim().equals("")) {
                    new MensajePrime().addInformativeMessagePer("Falta llenar Domicilio del alumno");
                }
                if (estudianteBean.getTipoRespPago().getIdCodigo() == null || estudianteBean.getTipoRespPago().getIdCodigo().equals(0)) {
                    new MensajePrime().addInformativeMessagePer("Falta llenar Responsable de Pago del alumno");
                }
                if (estudianteBean.getIdDistritoDomi().getIdDistrito() == null || estudianteBean.getIdDistritoDomi().getIdDistrito().equals(0)) {
                    new MensajePrime().addInformativeMessagePer("Falta llenar el Distrito del Alumno");
                }
                if (estudianteInfoBean.getTipoSeguroBean().getIdCodigo() == null || estudianteInfoBean.getTipoSeguroBean().getIdCodigo().equals(0)) {
                    new MensajePrime().addInformativeMessagePer("Falta llenar Tipo de Seguro del alumno");
                }
//                if (estudianteInfoBean.getViveCon().getIdCodigo() == null || estudianteInfoBean.getViveCon().getIdCodigo().equals(0)) {
//                    new MensajePrime().addInformativeMessagePer("Falta llenar Con quien vive el Alumno");
//                }
                if (flgDniCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                    new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI del Papá no son números");
                }
                if (flgDniCorrectoMapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().trim().equals("")) {
                    new MensajePrime().addInformativeMessagePer("Entrada no válida, el DNI de la Mamá no son números");
                }
                if (flgCorreoCorrectoPapa == false && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                    new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail del Papá formato incorrecto");
                }
                if (flgCorreoCorrectoMama == false && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getCorreo().trim().equals("")) {
                    new MensajePrime().addInformativeMessagePer("Entrada no válida, el E-mail de la Mamá formato incorrecto");
                }
//                if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals(estudianteBean.getPersonaBean().getApepat())
//                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getApepat().trim().equals(estudianteBean.getPersonaBean().getApemat())) {
//                    isValidarMensajeFaltCampos();
//                    System.out.println("papás:si");
//                } else {
//                    System.out.println("papás:no");
//                    new MensajePrime().addInformativeMessagePer("Padres no coinciden en el mismo apellido");
//                }
                if (estudianteBean.getViaDomi().length() > limite) {
                } else {
                    new MensajePrime().addInformativeMessagePer("Mas información de la direccion del estudiante");
                }
                isValidarMensajeFaltCampos();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verSexo(int sex) {
        try {
            System.out.println("sex " + sex);
            estudianteBean.getPersonaBean().setSexo(sex);
            System.out.println("sex 2" + estudianteBean.getPersonaBean().getSexo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verFecha(Date fecha) {
        try {
            System.out.println("fec " + fecha);
            estudianteBean.getPersonaBean().setFecNac(fecha);
            System.out.println("fec 2" + estudianteBean.getPersonaBean().getFecNac());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void comprobarEstudiante(String tipo) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteVista = estudianteService.obtenerEstPorId(estudianteBean);
            if (estudianteVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                insertarEstudiante(tipo);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            String rutaFoto = "";
            rutaFoto = personaService.obtenerFoto(estudianteBean.getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String statusEst = estudianteBean.getStatusEst();
            estudianteBean.setCollapsed(false);
            estudianteBean = (EstudianteBean) event.getObject();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            //Si esta matriculado para desabilitar la seccion y cambio de estado del alumno
            Integer stadoMatricula = estudianteService.obtenerSiAlumnoEsMatriculado(estudianteBean.getPersonaBean().getIdPersona(), anio);
            if (stadoMatricula != null) {
                if (stadoMatricula == 1) {
                    flgMatriculado = true;
                } else {
                    flgMatriculado = false;
                }
            }
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            estudianteBean.getPersonaBean().setFoto(rutaFoto);
            if (statusEst != null) {
                estudianteBean.setStatusEst(statusEst);
                if (!statusEst.equals("Matriculado")) {
                    estudianteBean.setStatusBtnMatricula("true");
                } else {
                    estudianteBean.setStatusBtnMatricula("false");
                }
            } else {
                estudianteBean.setStatusBtnMatricula("false");
            }
            estudianteInfoBean = estudianteService.obtenerEstudianteInfoPorId(estudianteBean.getPersonaBean().getIdPersona());
            if (estudianteInfoBean != null) {
                estudianteBean.setSexoEstudiante(estudianteInfoBean.getSexoEstudiante());
                estudianteBean.getIdDistritoNaci().getProvinciaBean().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getProvinciaBean().getNombre());
                estudianteBean.getIdDistritoNaci().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getNombre());
                estudianteBean.getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().getNombre());
                estudianteBean.getTipoRespPago().setIdCodigo(estudianteInfoBean.getEstudianteBean().getTipoRespPago().getIdCodigo());
            }
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
            listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteNacimientoService estudianteNacimientoService = BeanFactory.getEstudianteNacimientoService();
            estudianteNacimientoBean = estudianteNacimientoService.obtenerEstNacimientoPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
//            listaEstudianteVacunaBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteBean.getPersonaBean().getIdPersona()); 
            listaEstudianteVacunasEstBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
            listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
            listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
            listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            //verificar lista
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteBean);
            //fichamtricula
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroActivoPorEst(estudianteBean);
            if (listaEstudianteSeguroBean.size() == 1) {
                for (EstudianteSeguroBean lista : listaEstudianteSeguroBean) {
                    estudianteSeguroBean = lista;
                    System.out.println("ruc" + getEstudianteSeguroBean().getEntidadBean().getRuc());
                }
            }
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaBloqueo = new GregorianCalendar();
            estudianteBloqueoBean.setFechaBloqueo(fechaBloqueo.getTime());
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
//            estudianteBloqueoBean = estudianteBloqueoService.obtenerEstudianteBloqueo(estudianteBloqueoBean);
//            FamiliaService familiaService = BeanFactory.getFamiliaService();
            estudianteBean.getFamiliaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            estudianteSeguroBean= estudianteSeguroService.obtenerEstudianteSeguroSelect(estudianteBean);
//            familiaBean = familiaService.obtenerFamiliaPorId(estudianteBean.getFamiliaBean());
            //Familiar
            for (int i = 0; i < listaFamiliarEstudianteBean.size(); i++) {
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                    padreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                    madreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
            }
            System.out.println("padre: " + padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("padre: " + madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("padre: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            //Lista sin padres
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(estudianteBean.getPersonaBean().getIdPersona());

            mostarStatusEst();
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();
            listaFamiliarEstudianteRespBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
            //            obtenerEstudianteMovilidad();
            //mostrar link seguimiento adm.
            validarPostulanteAdmision();
            validarPadreVive();
            System.out.println(estudianteBean.getFoto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectBloqueo(SelectEvent event) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            String rutaFoto = "";
            rutaFoto = personaService.obtenerFoto(estudianteBean.getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String statusEst = estudianteBean.getStatusEst();
            estudianteBean.setCollapsed(false);
            estudianteBean = (EstudianteBean) event.getObject();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            estudianteBean.getPersonaBean().setFoto(rutaFoto);
            if (statusEst != null) {
                estudianteBean.setStatusEst(statusEst);
                if (!statusEst.equals("Matriculado")) {
                    estudianteBean.setStatusBtnMatricula("true");
                } else {
                    estudianteBean.setStatusBtnMatricula("false");
                }
            } else {
                estudianteBean.setStatusBtnMatricula("false");
            }
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaBloqueo = new GregorianCalendar();
            estudianteBloqueoBean.setFechaBloqueo(fechaBloqueo.getTime());
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
            estudianteBean.getFamiliaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            mostarStatusEst();
            validarPostulanteAdmision();
            System.out.println(estudianteBean.getFoto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetalleBloqueo(Object objeto) {
        try {
            estudianteBean = (EstudianteBean) objeto;
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaBloqueo = new GregorianCalendar();
            estudianteBloqueoBean.setFechaBloqueo(fechaBloqueo.getTime());
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrectaVista", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarPorDNI() {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            personaBean = personaService.obtenerPersPorId(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarStatusFamiliar() {
        try {
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            familiarService.modificarFamiliarEstudianteStatus(familiarEstudianteBean);
            familiarEstudianteBean = new FamiliarEstudianteBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarPorDNIver2(String tipo, String nroDoc) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            PersonaBean perso = new PersonaBean();
            perso.setIdPersona(nroDoc);
            perso.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            perso = personaService.obtenerPersPorIdRapido(perso);
            if (tipo.equals("otro")) {
                if (perso != null) {
                    getFamiliarEstudianteBean().getFamiliarBean().setDireccion(perso.getDireccion());
                    getFamiliarEstudianteBean().getFamiliarBean().setTelefonoCelular(perso.getTelefonoCelular());
                    getFamiliarEstudianteBean().getFamiliarBean().setFlgVive(perso.getFlgVive());
                    getFamiliarEstudianteBean().getTipoParentescoBean().setCodigo(perso.getParentesco());
                    getFamiliarEstudianteBean().getFamiliarBean().setProfesion(perso.getProfesion());
                }
                getFamiliarEstudianteBean().getFamiliarBean().setPersonaBean(perso);
            }
            if (tipo.equals("madre")) {
                if (perso != null) {
                    getMadreEstudianteBean().getFamiliarBean().setDireccion(perso.getDireccion());
                    getMadreEstudianteBean().getFamiliarBean().setTelefonoCelular(perso.getTelefonoCelular());
                    getMadreEstudianteBean().getFamiliarBean().setFlgVive(perso.getFlgVive());
                    getMadreEstudianteBean().getTipoParentescoBean().setCodigo(perso.getParentesco());
                    getMadreEstudianteBean().getFamiliarBean().setProfesion(perso.getProfesion());
                }
                getMadreEstudianteBean().getFamiliarBean().setPersonaBean(perso);
            }
            if (tipo.equals("padre")) {
                if (perso != null) {
                    getPadreEstudianteBean().getFamiliarBean().setDireccion(perso.getDireccion());
                    getPadreEstudianteBean().getFamiliarBean().setTelefonoCelular(perso.getTelefonoCelular());
                    getPadreEstudianteBean().getFamiliarBean().setFlgVive(perso.getFlgVive());
                    getPadreEstudianteBean().getTipoParentescoBean().setCodigo(perso.getParentesco());
                    getPadreEstudianteBean().getFamiliarBean().setProfesion(perso.getProfesion());
                }
                getPadreEstudianteBean().getFamiliarBean().setPersonaBean(perso);
            }
            copiarCodigoEnNroDoc(tipo, nroDoc);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarPorDNIFamiliarEst(String tipo, String nroDoc) {
        try {
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            if (tipo.equals("otro")) {
                familiarEstudianteBean = familiarService.obtenerFamiliarEstPorFamiliar(nroDoc);
            }
            if (tipo.equals("madre")) {
                madreEstudianteBean = familiarService.obtenerFamiliarEstPorFamiliar(nroDoc);
            }
            if (tipo.equals("padre")) {
                padreEstudianteBean = familiarService.obtenerFamiliarEstPorFamiliar(nroDoc);
            }

            copiarCodigoEnNroDoc(tipo, nroDoc);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void copiarCodigoEnNroDoc(String tipo, String nroDoc) {
//        String codigo = null;
//        codigo = getFami().getPersonaBean().getIdPersona();
        if (tipo.equals("otro")) {
            getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().setNroDoc(nroDoc);
        }
        if (tipo.equals("madre")) {
            getMadreEstudianteBean().getFamiliarBean().getPersonaBean().setNroDoc(nroDoc);
        }
        if (tipo.equals("padre")) {
            getPadreEstudianteBean().getFamiliarBean().getPersonaBean().setNroDoc(nroDoc);
        }
    }

    public void rowSelectRapido(SelectEvent event) {
        try {
            flgGrabar = false;
            familiarEstudianteBean = new FamiliarEstudianteBean();
            madreEstudianteBean = new FamiliarEstudianteBean();
            padreEstudianteBean = new FamiliarEstudianteBean();
            PersonaService personaService = BeanFactory.getPersonaService();
            String rutaFoto = "";
            rutaFoto = personaService.obtenerFoto(estudianteBean.getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String statusEst = estudianteBean.getStatusEst();
            estudianteBean.setCollapsed(false);
            estudianteBean = (EstudianteBean) event.getObject();
            estudianteBean.setAnio(anio);
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean = estudianteService.obtenerEstPorIdMatricula(estudianteBean);
            estudianteBean.getPersonaBean().setFoto(rutaFoto);
            if (statusEst != null) {
                estudianteBean.setStatusEst(statusEst);
//                if (!statusEst.equals("Matriculado") && !statusEst.equals("Bloqueado")) {
                if (statusEst.equals("Activo")) {
                    estudianteBean.setStatusBtnMatricula("true");
                } else {
                    estudianteBean.setStatusBtnMatricula("false");
                }
            } else {
                estudianteBean.setStatusBtnMatricula("false");
            }
            estudianteInfoBean = estudianteService.obtenerEstudianteInfoPorId(estudianteBean.getPersonaBean().getIdPersona());
            estudianteBean.setSexoEstudiante(estudianteInfoBean.getSexoEstudiante());
            estudianteBean.getIdDistritoNaci().getProvinciaBean().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getProvinciaBean().getNombre());
            estudianteBean.getIdDistritoNaci().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getNombre());
            estudianteBean.getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().setNombre(estudianteInfoBean.getEstudianteBean().getIdDistritoNaci().getProvinciaBean().getDepartamentoBean().getNombre());
            estudianteBean.getTipoRespPago().setIdCodigo(estudianteInfoBean.getEstudianteBean().getTipoRespPago().getIdCodigo());
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstRapido(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
            listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteNacimientoService estudianteNacimientoService = BeanFactory.getEstudianteNacimientoService();
            estudianteNacimientoBean = estudianteNacimientoService.obtenerEstNacimientoPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
//            listaEstudianteVacunaBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteBean.getPersonaBean().getIdPersona()); 
            listaEstudianteVacunasEstBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
            listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
            listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
            listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            //verificar lista
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteBean);
            //fichamtricula
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroActivoPorEst(estudianteBean);
            if (listaEstudianteSeguroBean.size() == 1) {

                for (EstudianteSeguroBean lista : listaEstudianteSeguroBean) {
                    estudianteSeguroBean = lista;
                    System.out.println("ruc" + getEstudianteSeguroBean().getEntidadBean().getRuc());
                }
            }
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaBloqueo = new GregorianCalendar();
            estudianteBloqueoBean.setFechaBloqueo(fechaBloqueo.getTime());
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
            estudianteBean.getFamiliaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            //Familiar
            for (int i = 0; i < listaFamiliarEstudianteBean.size(); i++) {
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                    padreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                    madreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_Apoderado)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_hrn)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_tia)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_abue)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PRIMO)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_SOBRINO)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_REPRESENTANTE)
                        || listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_HIJA)) {
                    familiarEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
            }
            System.out.println("madre: " + padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("padre: " + madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            System.out.println("apoderado: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
            //Lista sin padres
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(estudianteBean.getPersonaBean().getIdPersona());

            ponerFamEst(familiarEstudianteBean);
            mostarStatusEst();
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();

            //mostrar link seguimiento adm.
            validarPostulanteAdmision();
            validarPadreVive();
            System.out.println(estudianteBean.getFoto());
//            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() == MaristaConstantes.UNI_NEG_BARINA) {
//                for (CodigoBean bean : listaSeguroBean) {
//                    if (bean.getCodigo().equals("Seguro del Colegio")) {
//                        getEstudianteInfoBean().getTipoSeguroBean().setIdCodigo(bean.getIdCodigo());
//                        this.idTipoSeguro = bean.getIdCodigo();
//                    }
//                }
//            }
//            if (estudianteInfoBean.getViveCon().getIdCodigo() != null) {
//                if (estudianteInfoBean.getViveCon().getIdCodigo() == 14606) {
//                    this.flgOtros = true;
//                    System.out.println("si es otro");
//                } else {
//                    this.flgOtros = false;
//                    System.out.println("no es otro");
//                }
//            } else {
//                this.flgOtros = false;
//                System.out.println("no es otro 2");
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarListaCodigoColegio() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            listaCodigoColeBean = estudianteService.obtenerCodigoPorFiltro(codigoColegioBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarCodigoColegio() {
        try {
            EstudianteService est = BeanFactory.getEstudianteService();
            CodigoColegioBean cod = new CodigoColegioBean();
            cod.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cod.setCodigo(codigoColegioBean.getCodigo());
            cod = est.obtenerCodigoColegioPorCodigo(codigoColegioBean);
            if (cod != null) {
                modificarCodigoColegio();
            } else {
                insertarCodigoColegio();
                System.out.println("null");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void limpiarCodigoColegio() {
        codigoColegioBean = new CodigoColegioBean();
    }

    public String insertarCodigoColegio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                String iniString = codigoColegioBean.getCodigo();
                Integer iniInt = Integer.parseInt(iniString);
                Integer actInt = Integer.parseInt(codigoColegioBean.getCodigoActual());
                if (iniInt <= actInt
                        && actInt >= iniInt) {
                    EstudianteService estudianteService = BeanFactory.getEstudianteService();
                    codigoColegioBean.setCreaPor(usuarioLoginBean.getUsuario());
                    codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    estudianteService.insertarCodigoColegio(codigoColegioBean);
                    limpiarCodigoColegio();
                    codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    listaCodigoColeBean = estudianteService.obtenerCodigoPorFiltro(codigoColegioBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    new MensajePrime().addInformativeMessagePer("mensajeCodError");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCodigoColegio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                String codigoActual= codigoColegioBean.getCodigoActual();
                String iniString = codigoColegioBean.getCodigo();
                Integer iniInt = Integer.parseInt(iniString);
                Integer actInt = Integer.parseInt(codigoColegioBean.getCodigoActual());
                System.out.println("numero entero: " + actInt);
                if (iniInt <= actInt
                        && actInt >= iniInt) {
                    EstudianteService estudianteService = BeanFactory.getEstudianteService();
                    codigoColegioBean.setModiPor(usuarioLoginBean.getUsuario());
                    codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    estudianteService.modificarCodigoColegio(codigoColegioBean);
                    limpiarCodigoColegio();
                    codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    listaCodigoColeBean = estudianteService.obtenerCodigoPorFiltro(codigoColegioBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    new MensajePrime().addInformativeMessagePer("mensajeChequeError");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCodigoColegio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteService.eliminarCodigoColegio(codigoColegioBean);
                limpiarCodigoColegio();
                codigoColegioBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                listaCodigoColeBean = estudianteService.obtenerCodigoPorFiltro(codigoColegioBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void rowSelectCodigoCole(SelectEvent event) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            codigoColegioBean = (CodigoColegioBean) event.getObject();
            codigoColegioBean = estudianteService.obtenerCodigoColegioPorId(codigoColegioBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerPorceso(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void limpiarEstudiante() {
        estudianteBean = new EstudianteBean();

        AdmisionMB admisionMB = (AdmisionMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("admisionMB");
        admisionMB.limpiarPostulanteAdmision();
    }

    public void limpiarFiltroEstudiante() {
        estudianteBean = new EstudianteBean();
        padreEstudianteBean = new FamiliarEstudianteBean();
        madreEstudianteBean = new FamiliarEstudianteBean();
        estudianteSeguroBean = new EstudianteSeguroBean();
        estudianteInfoBean = new EstudianteInfoBean();
        familiarEstudianteBean = new FamiliarEstudianteBean();
        estudianteFiltroBean = new EstudianteBean();
        listaEstudianteBean = new ArrayList<>();
        listaFamiliarEstudianteBean = new ArrayList<>();
        estudianteFiltroBean.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            estudianteBean.setFile(event.getFile());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Persona
    public void guardarPersona() {
        try {
            if (estudianteBean.getPersonaBean().getIdPersona() != null) {
                modificarPersona();
            } else {
                insertarPersona();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersona() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonaService personaService = BeanFactory.getPersonaService();
                personaService.insertarPersona(estudianteBean.getPersonaBean());
                listaPersonaBean = personaService.obtenerPersona();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudiante();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarPersona() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonaService personaService = BeanFactory.getPersonaService();
                personaService.modificarPersona(estudianteBean.getPersonaBean());
                listaPersonaBean = personaService.obtenerPersona();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudiante();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void limpiarPersona() {
        personaBean = new PersonaBean();
    }

    public void obtenerEstudiantePorId(Object estudiante) {
        try {
            estudianteBean.setCollapsed(false);
            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setAnio(anio);
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            estudianteInfoBean = estudianteService.obtenerEstudianteInfoPorId(estudianteBean.getPersonaBean().getIdPersona());
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
            listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
            listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
            listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
            listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteBean.getPersonaBean().getIdPersona());
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteBean);
//            FamiliaService familiaService = BeanFactory.getFamiliaService();
            estudianteBean.getFamiliaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            familiaBean = familiaService.obtenerFamiliaPorId(estudianteBean.getFamiliaBean());
            //Familiar

            for (int i = 0; i < listaFamiliarEstudianteBean.size(); i++) {
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                    padreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                    madreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
            }
            //Lista sin padres
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(estudianteBean.getPersonaBean().getIdPersona());

            mostarStatusEst();
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //EstudianteInfo
    public String insertarEstudianteInfo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteInfoBean.setEstudianteBean(estudianteBean);
                estudianteInfoBean.setCreaPor(usuarioLoginBean.getUsuario());
                estudianteBean.setModiPor(usuarioLoginBean.getUsuario());
                estudianteService.insertarEstudianteInfo(estudianteInfoBean, estudianteBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteInfo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteInfoBean.setEstudianteBean(estudianteBean);
                estudianteInfoBean.setModiPor(usuarioLoginBean.getUsuario());
                estudianteBean.setModiPor(usuarioLoginBean.getUsuario());
//                if (estudianteBean.getRespPagoBean().getSexo() == 1) {
//                    estudianteBean.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_PAPA_id);
//                } else if (estudianteBean.getRespPagoBean().getSexo() == 0) {
//                    estudianteBean.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_MAMA_id);
//                } else {
//                    estudianteBean.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_Apoderado_id);
//                }
                estudianteService.modificarEstudianteInfo(estudianteInfoBean, estudianteBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerEstudianteInfoPorId() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteInfoBean = estudianteService.obtenerEstudianteInfoPorId(estudianteBean.getPersonaBean().getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerSedeParroquiaBautizo() {
//        try {
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaParroquiaBautizo = entidadService.obtenerEntidadSedePorEntidad(estudianteInfoBean.getParroquiaBautizo().getEntidadBean().getIdEntidad());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void guardarEstudianteInfo(String tipo) {
        try {
            if (tipo.equals("postulante")) {
                estudianteInfoBean.setFlgBautizo(null);
                estudianteInfoBean.setFlgPrimeraComunion(null);
                estudianteInfoBean.setFlgVidaReligiosa(null);
                estudianteInfoBean.setFlgConfirmacion(null);
            }
            if (estudianteInfoBean.getEstudianteBean().getPersonaBean().getIdPersona() != null
                    && !estudianteInfoBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                modificarEstudianteInfo();
            } else {
                insertarEstudianteInfo();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstudianteInfo() {
        estudianteInfoBean = new EstudianteInfoBean();
    }

//    public void filtraEntidadParroquiaBau() {
//        try {
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaParroquiaBautizo = entidadService.obtenerEntidadSedePorEntidad(estudianteInfoBean.getParroquiaBautizo().getEntidadBean().getIdEntidad());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void filtraEntidadParroquiaAsi() {
//        try {
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaParroquiaAsiste = entidadService.obtenerEntidadSedePorEntidad(estudianteInfoBean.getParroquiaAsiste().getEntidadBean1().getIdEntidad());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void filtraEntidadColegioPro() {
//        try {
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaColegioProcedencia = entidadService.obtenerEntidadSedePorEntidad(idColegioProceEntidad);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //Distritos
    public void obtenerProvinciaNaci() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaNaci = distritoService.obtenerProvinciaPorDep(estudianteBean.getIdDistritoNaci().getProvinciaBean().getDepartamentoBean());
//            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistritoNaci() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoNaci = distritoService.obtenerDistritoPorProv(estudianteBean.getIdDistritoNaci().getProvinciaBean());
//            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProvinciaDomi() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaDomi = distritoService.obtenerProvinciaPorDep(estudianteBean.getIdDistritoDomi().getProvinciaBean().getDepartamentoBean());
//            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistritoDomi() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoDomi = distritoService.obtenerDistritoPorProv(estudianteBean.getIdDistritoDomi().getProvinciaBean());
//            distritoBean = new DistritoBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerflgPadrees() {
        System.out.println("flgPadre: " + flgQuieroDatosPadres);
        System.out.println("flgMadre: " + flgQuieroDatosMadres);
    }

    //Familiares
    public void limpiarFamiliarEstudiante() {
        familiarEstudianteBean = new FamiliarEstudianteBean();
    }

    public void limpiarPadreEstudianteBean() {
        padreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void limpiarMadreEstudianteBean() {
        madreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void guardarFamiliar(String tipo) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            Integer id = estudianteService.obtenerIdTipoResp(estudianteBean.getPersonaBean().getIdPersona());
            Integer tipoResp;
            if (tipo.equals(MaristaConstantes.COD_PAPA)) {
                tipoResp = 17401;
                padreEstudianteBean.getTipoParentescoBean().setIdCodigo(MaristaConstantes.CODIGO_PADRE);
                padreEstudianteBean.getTipoParentescoBean().setCodigo(MaristaConstantes.COD_PAPA);
                padreEstudianteBean.setDniFamiliar(padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                padreEstudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
                guardarFamiliarEstudiante(padreEstudianteBean);
                if (id != null) {
                    if (id.equals(tipoResp)) {
                        EstudianteBean est = new EstudianteBean();
                        est.getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                        est.setModiPor(usuarioLoginBean.getUsuario());
                        est.getRespPagoBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                        estudianteService.modificarSoloDniRespPago(est);
                    }
                }
            }
            if (tipo.equals(MaristaConstantes.COD_MAMA)) {
                tipoResp = 17402;
                madreEstudianteBean.getTipoParentescoBean().setIdCodigo(MaristaConstantes.CODIGO_MADRE);
                madreEstudianteBean.getTipoParentescoBean().setCodigo(MaristaConstantes.COD_MAMA);
                madreEstudianteBean.setDniFamiliar(madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                madreEstudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
                guardarFamiliarEstudiante(madreEstudianteBean);
                if (id != null) {
                    if (id.equals(tipoResp)) {
                        EstudianteBean est = new EstudianteBean();
                        est.getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                        est.setModiPor(usuarioLoginBean.getUsuario());
                        est.getRespPagoBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                        estudianteService.modificarSoloDniRespPago(est);
                    }
                }
            }
            if (tipo.equals(MaristaConstantes.COD_OTRO)) {
                tipoResp = 17403;
                familiarEstudianteBean.setDniFamiliar(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                familiarEstudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
                guardarFamiliarEstudiante(familiarEstudianteBean);
                if (id != null) {
                    if (id.equals(tipoResp)) {
                        EstudianteBean est = new EstudianteBean();
                        est.getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
                        est.setModiPor(usuarioLoginBean.getUsuario());
                        est.getRespPagoBean().setIdPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
                        estudianteService.modificarSoloDniRespPago(est);
                    }
                }
            }

            Integer idFamilia;
            idFamilia = estudianteService.obtenerEstPorIdFamilia(estudianteBean.getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (idFamilia == null) {
                if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().equals("")
                        && madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().equals("")) {
                    insertarFamilias();
                } else if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !padreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().equals("")
                        && flgQuieroDatosMadres == true) {
                    insertarFamilias();
                } else if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc() != null
                        && !madreEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc().equals("")
                        && flgQuieroDatosPadres == true) {
                    insertarFamilias();
                } else if (flgQuieroDatosMadres == true && flgQuieroDatosPadres == true) {
                    insertarFamilias();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void insertarFamilias() {
        try {
            FamiliaService familiaService = BeanFactory.getFamiliaService();
//            FamiliaBean fam = new FamiliaBean();
//            fam.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            fam.setMadreBean(madreEstudianteBean.getFamiliarBean());
//            fam.setPadreBean(padreEstudianteBean.getFamiliarBean());
//            FamiliaBean familiar = new FamiliaBean();
            Boolean familiar = familiaService.obtenerFamiliaPorPadresFam(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona(), madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona(),
                    usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (familiar == null) {
                familiar = false;
            }
            if (familiar == false) {
                if (estudianteBean.getFamiliaBean().getIdFamilia() == null) {
                    System.out.println("Entro yeah");
                    FamiliaBean familiaBean = new FamiliaBean();
                    familiaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    familiaBean.setNombre(estudianteBean.getPersonaBean().getApepat() + " " + estudianteBean.getPersonaBean().getApemat());
                    familiaBean.setStatus(Boolean.TRUE);
                    familiaBean.setCreaPor(usuarioLoginBean.getUsuario());
                    familiaBean.getMadreBean().getPersonaBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    familiaBean.getPadreBean().getPersonaBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    familiaService.insertarFamiliaRapido(familiaBean);

                    Integer flgOpcion;
                    if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona() == null) {
                        flgOpcion = 2;
                    } else if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona() == null) {
                        flgOpcion = 3;
                    } else {
                        flgOpcion = 1;
                    }
                    Integer idFamilia = familiaService.obtenerFamiliaId(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona(), madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona(),
                            usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), flgOpcion);
                    EstudianteService estudianteService = BeanFactory.getEstudianteService();
                    EstudianteBean estudiante = new EstudianteBean();
                    estudiante.getFamiliaBean().setIdFamilia(idFamilia);
                    estudiante.setModiPor(usuarioLoginBean.getUsuario());
                    estudiante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    estudiante.setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                    estudianteService.modificarFamiliaEstudiante(estudiante);
                }else{
                    System.out.println("Entro segundo yeah");
                    FamiliaBean familiaBean = new FamiliaBean();
                    familiaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    familiaBean.getMadreBean().getPersonaBean().setIdPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    familiaBean.getPadreBean().getPersonaBean().setIdPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
                    familiaBean.setModiPor(usuarioLoginBean.getUsuario());
                    familiaBean.setIdFamilia(estudianteBean.getFamiliaBean().getIdFamilia());
                    familiaService.modificarFamiliaRapido(familiaBean);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void corregirMatricula() {

        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            MatriculaBean matricula = new MatriculaBean();
            matricula.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            matricula.setIdMatricula(matriculaBean.getIdMatricula());
            matricula.getEstudianteBean().setIdEstudiante(estudianteBloqueoBean.getEstudianteBean().getIdEstudiante());
            matricula.setAnio(anio);
            matriculaService.corregirMatriculaOff(matricula);
            listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String guardarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudiante) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                familiarEstudiante.setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.getFamiliarBean().setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.setEstudianteBean(estudianteBean);
                familiarEstudiante.getFamiliarBean().getPersonaBean().setIdPersona(familiarEstudiante.getFamiliarBean().getPersonaBean().getNroDoc());
                familiarService.guardarFamiliarEstudiante(familiarEstudiante);
                listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(familiarEstudiante.getEstudianteBean().getPersonaBean().getIdPersona());
//                limpiarFamiliarEstudiante();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaFamiliarEstudianteRespBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());

                if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12402) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Padre");
                } else if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12403) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Madre");
                } else if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12404) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Hermano(a)");
                } else if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12405) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Tío(a)");
                } else if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12406) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Abuelo(a)");
                } else if (familiarEstudiante.getTipoParentescoBean().getIdCodigo() == 12407) {
                    familiarEstudiante.getTipoParentescoBean().setCodigo("Primo(a)");
                }
                if (estudianteBean.getFamiliaBean().getIdFamilia() != null) {
                    System.out.println("parentezco: " + familiarEstudiante.getTipoParentescoBean().getCodigo());
                    if (familiarEstudiante.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)
                            || familiarEstudiante.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                        FamiliaService familia = BeanFactory.getFamiliaService();
                        FamiliaBean familiaB = new FamiliaBean();
                        familiaB.setModiPor(usuarioLoginBean.getUsuario());
                        familiaB.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        familiaB.setIdFamilia(estudianteBean.getFamiliaBean().getIdFamilia());
                        if (familiarEstudiante.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                            familiaB.getPadreBean().getPersonaBean().setIdPersona(familiarEstudiante.getFamiliarBean().getPersonaBean().getIdPersona());
                            familia.modificarFamiliaRapidoPapa(familiaB);
                        } else if (familiarEstudiante.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                            familiaB.getMadreBean().getPersonaBean().setIdPersona(familiarEstudiante.getFamiliarBean().getPersonaBean().getIdPersona());
                            familia.modificarFamiliaRapidoMama(familiaB);
                        }
                    } else {
                        System.out.println("No es padre de familia, es apoderado");
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String guardarFamiliarEstudianteRapido(FamiliarEstudianteBean familiarEstudiante) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                familiarEstudiante.setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.getFamiliarBean().setCreaPor(usuarioLoginBean.getUsuario());
                familiarEstudiante.setEstudianteBean(estudianteBean);
                familiarEstudiante.getFamiliarBean().getPersonaBean().setIdPersona(familiarEstudiante.getFamiliarBean().getPersonaBean().getNroDoc());
                familiarService.guardarFamiliarEstudianteRapido(familiarEstudiante);
                listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(familiarEstudiante.getEstudianteBean().getPersonaBean().getIdPersona());
//                limpiarFamiliarEstudiante();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

//    public String modificarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudiante) {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                FamiliarService familiarService = BeanFactory.getFamiliarService();
//                familiarEstudianteBean.setModiPor(usuarioLoginBean.getUsuario());
//                familiarEstudianteBean.getFamiliarBean().setModiPor(usuarioLoginBean.getUsuario());
//                familiarService.modificarFamiliarEstudiante(familiarEstudiante);
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
    public String eliminarFamiliarEstudiante() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                familiarService.eliminarFamiliarEst(familiarEstudianteBean);
                listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEstSinPadres(familiarEstudianteBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                limpiarFamiliarEst();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void rowSelectFamiliarEstudiante(SelectEvent event) {
        try {
            familiarEstudianteBean = (FamiliarEstudianteBean) event.getObject();
            obtenerColegioPorSede();
            comodin = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void colocarFamiliarEst(Object objeto) {
        try {
            familiarEstudianteBean = (FamiliarEstudianteBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    ///////////////////////////////////////fin familiar77777777777777777777777777
    public void handleFileUploadFam(FileUploadEvent event) {
        try {
            familiarEstudianteBean.getFamiliarBean().setFile(event.getFile());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerColegioPorSede() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaColegioBean = entidadService.obtenerEntidadSedePorEntidad(familiarEstudianteBean.getFamiliarBean().getEntidadSedeBean().getEntidadBean().getIdEntidad());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public List<PersonaBean> completePersonaFam(String query) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            List<PersonaBean> listaPersonaTodosBean = personaService.obtenerPersona();
            List<PersonaBean> listaPersonaFiltroBean = new ArrayList<>();
            for (int i = 0; i < listaPersonaTodosBean.size(); i++) {
                PersonaBean bean = listaPersonaTodosBean.get(i);
                if (bean.getNombreCompleto().toLowerCase().contains(query.toLowerCase())) {
                    listaPersonaFiltroBean.add(bean);
                }
            }
            return listaPersonaFiltroBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return null;
    }

    public void cambiarComodin() {
        comodin = false;
    }

    public void ponerFamEst(Object objeto) {
        try {
            familiarEstudianteBean = (FamiliarEstudianteBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFamCentTraPorSede() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaFamCentTraSedeBean = entidadService.obtenerEntidadSedePorEntidad(familiarCentroTrabajoBean.getEntidadSedeBean().getEntidadBean().getIdEntidad());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//Datos Medicos
    public void obtenerEstEnfermedadPorEst() {
        try {
            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
            listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getIdPersona());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarEstEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteEnfermedadBean.setCreaPor(usuarioBean.getUsuario());
                estudianteEnfermedadBean.setEstudianteBean(estudianteBean);
                estudianteEnfermedadService.insertarEstEnfermedad(estudianteEnfermedadBean);
                listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarEstudianteNacimiento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteNacimientoService estudianteNacimientoService = BeanFactory.getEstudianteNacimientoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteNacimientoBean.getUnidadNegocioBean().setUniNeg(estudianteBean.getPersonaBean().getUnidadNegocioBean().getUniNeg());
                estudianteNacimientoBean.setCreaPor(usuarioBean.getUsuario());
                estudianteNacimientoBean.setEstudianteBean(estudianteBean);
                estudianteNacimientoService.insertarEstudianteNacimiento(estudianteNacimientoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                limpiarEstNacimiento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarEstudianteVacuna() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteVacunaBean.getUnidadNegocioBean().setUniNeg(estudianteBean.getPersonaBean().getUnidadNegocioBean().getUniNeg());
                estudianteVacunaBean.setCreaPor(usuarioBean.getUsuario());
                estudianteVacunaBean.setEstudianteBean(estudianteBean);
                estudianteVacunaService.insertarEstudianteVacuna(estudianteVacunaBean);
                listaEstudianteVacunasEstBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteBean.getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
            limpiarEstVacuna();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
//                estudianteEnfermedadBean.setEstudianteBean(estudianteBean);
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteEnfermedadBean.setModiPor(usuarioBean.getUsuario());
                estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudianteEnfermedadService.modificarEstEnfermedad(estudianteEnfermedadBean);
                listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteNacimiento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteNacimientoService estudianteNacimientoService = BeanFactory.getEstudianteNacimientoService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteNacimientoBean.setModiPor(usuarioBean.getUsuario());
                estudianteNacimientoBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudianteNacimientoService.modificarEstudianteNacimiento(estudianteNacimientoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteVacuna() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteVacunaBean.setModiPor(usuarioBean.getUsuario());
                estudianteVacunaBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudianteVacunaService.modificarEstudianteVacuna(estudianteVacunaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
//                estudianteEnfermedadBean.setEstudianteBean(estudianteBean);
                estudianteEnfermedadService.eliminarEstEnfermedad(estudianteEnfermedadBean);
                listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarEstEnfermedad() {
        try {
            if (estudianteEnfermedadBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstEnfermedad();
            } else {
                insertarEstEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verTipoDoc(String tipo) {
        try {
            if (tipo == null) {
                numeroCodPer = 8;
                numeroCodMa = 8;
                numeroCodFa = 8;
                System.out.println("tipo null");
            } else {
                if (tipo.equals("padre")) {
                    if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null) {
                        numeroCodPer = 8;
                    } else {
                        if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                            numeroCodPer = 8;
                        }
                        if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10702) {
                            numeroCodPer = 12;
                        }
                        if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10703) {
                            numeroCodPer = 12;
                        }
                        if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10704) {
                            numeroCodPer = 15;
                        }
                    }
                    System.out.println("cant p " + numeroCodPer);
                } else if (tipo.equals("madre")) {
                    if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null) {
                        numeroCodMa = 8;
                    } else {
                        if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                            numeroCodMa = 8;
                        }
                        if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10702) {
                            numeroCodMa = 12;
                        }
                        if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10703) {
                            numeroCodMa = 12;
                        }
                        if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10704) {
                            numeroCodMa = 15;
                        }
                    }
                } else if (tipo.equals("otro")) {
                    if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == null) {
                        numeroCodFa = 8;
                    } else {
                        if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10701) {
                            numeroCodFa = 8;
                        }
                        if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10702) {
                            numeroCodFa = 12;
                        }
                        if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10703) {
                            numeroCodFa = 12;
                        }
                        if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdTipoDocPer().getIdCodigo() == 10704) {
                            numeroCodFa = 15;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarPadreVive() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            if (estudianteBean.getPersonaBean().getIdPersona() != null) {
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && padreEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() != null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() != null
                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() == false
                        && padreEstudianteBean.getFamiliarBean().getFlgVive() == false) {
                    listaRespPago = codigoService.obtenerPorTipoApoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() == null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() == null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() == null
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() == false
                        && padreEstudianteBean.getFamiliarBean().getFlgVive() == false) {
                    listaRespPago = codigoService.obtenerPorTipoNingunoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApemat() != null
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNombre() != null
                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == false
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() == false
                        && padreEstudianteBean.getFamiliarBean().getFlgVive() == false) {
                    listaRespPago = codigoService.obtenerPorTipoNingunoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (padreEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipoPapaApoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (padreEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && madreEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipoPapaMamaVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (madreEstudianteBean.getFamiliarBean().getFlgVive() == true
                        && familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipoMamaApoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (padreEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipoPapaVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                } else if (madreEstudianteBean.getFamiliarBean().getFlgVive() == true) {
                    listaRespPago = codigoService.obtenerPorTipoMamaVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
//                } else if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getApepat() != null
//                        && familiarEstudianteBean.getFamiliarBean().getFlgVive() == true) {
//                    listaRespPago = codigoService.obtenerPorTipoApoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
                }
            } else {
                listaRespPago = codigoService.obtenerPorTipoNingunoVive(new TipoCodigoBean(MaristaConstantes.TIP_RES_PAGO));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarEstNacimiento() {
        try {
            if (estudianteNacimientoBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstudianteNacimiento();
            } else {
                insertarEstudianteNacimiento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarEstVacuna() {
        try {
            if (estudianteVacunaBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstudianteVacuna();
            } else {
                insertarEstudianteVacuna();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerEstEnfermedadPorEst(){
//        try {
//            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
//            listaEstudianteEnfermedadBean = estudianteEnfermedadService.obtenerEstEnfermedadPorEst(estudianteBean.getIdEstudiante());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }        
//    }
    public void rowSelectEstEnfermedad(SelectEvent event) {
        try {
            estudianteEnfermedadBean = (EstudianteEnfermedadBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstEnf(Object objeto) {
        try {
            estudianteEnfermedadBean = (EstudianteEnfermedadBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstEnfermedad() {
        estudianteEnfermedadBean = new EstudianteEnfermedadBean();
    }

    public void limpiarEstNacimiento() {
        estudianteNacimientoBean = new EstudianteNacimientoBean();
    }

    public void limpiarEstVacuna() {
        estudianteVacunaBean = new EstudianteVacunaBean();
    }

    //Medicamentos
    public String insertarEstMedicamento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteMedicamentoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                estudianteMedicamentoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                estudianteMedicamentoBean.setEstudianteBean(estudianteBean);//c
                estudianteMedicamentoService.insertarEstMedicamento(estudianteMedicamentoBean);
                listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteMedicamentoBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstMedicamento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstMedicamento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteMedicamentoBean.setModiPor(beanUsuarioSesion.getUsuario());
//                estudianteMedicamentoBean.setEstudianteBean(estudianteBean);
                estudianteMedicamentoService.modificarEstMedicamento(estudianteMedicamentoBean);
                listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteMedicamentoBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstMedicamento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstMedicamento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
//                estudianteMedicamentoBean.setEstudianteBean(estudianteBean);
                estudianteMedicamentoService.eliminarEstMedicamento(estudianteMedicamentoBean);
                listaEstudianteMedicamentoBean = estudianteMedicamentoService.obtenerEstMedicamentoPorEst(estudianteMedicamentoBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstMedicamento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstVacuna() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
//                estudianteMedicamentoBean.setEstudianteBean(estudianteBean);
                estudianteVacunaService.eliminarEstMedicamento(estudianteVacunaBean);
                listaEstudianteVacunaBean = estudianteVacunaService.obtenerEstVacunaPorEst(estudianteMedicamentoBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstMedicamento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarEstMedicamento() {
        try {
            if (estudianteMedicamentoBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstMedicamento();
            } else {
                insertarEstMedicamento();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectEstMedicamento(SelectEvent event) {
        try {
            estudianteMedicamentoBean = (EstudianteMedicamentoBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectEstVacuna(SelectEvent event) {
        try {
            estudianteVacunaBean = (EstudianteVacunaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstMedicamento(Object objeto) {
        try {
            estudianteMedicamentoBean = (EstudianteMedicamentoBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerEstVacuna(Object objeto) {
        try {
            estudianteVacunaBean = (EstudianteVacunaBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstMedicamento() {
        estudianteMedicamentoBean = new EstudianteMedicamentoBean();
    }

    //Alergias
    public String insertarEstAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteAlergiaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                estudianteAlergiaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                estudianteAlergiaBean.setEstudianteBean(estudianteBean);
                estudianteAlergiaService.insertarEstAlergia(estudianteAlergiaBean);
                listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteAlergiaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstAlergia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteAlergiaBean.setModiPor(beanUsuarioSesion.getUsuario());
//                estudianteAlergiaBean.setEstudianteBean(estudianteBean);
                estudianteAlergiaService.modificarEstAlergia(estudianteAlergiaBean);
                listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteAlergiaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstAlergia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteAlergiaService estudianteAlergiaService = BeanFactory.getEstudianteAlergiaService();
//                estudianteAlergiaBean.setEstudianteBean(estudianteBean);
                estudianteAlergiaService.eliminarEstAlergia(estudianteAlergiaBean);
                listaEstudianteAlergiaBean = estudianteAlergiaService.obtenerEstAlergiaPorEst(estudianteAlergiaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstAlergia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarEstAlergia() {
        try {
            if (estudianteAlergiaBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstAlergia();
            } else {
                insertarEstAlergia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectEstAlergia(SelectEvent event) {
        try {
            estudianteAlergiaBean = (EstudianteAlergiaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstAlergia(Object objeto) {
        try {
            estudianteAlergiaBean = (EstudianteAlergiaBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstAlergia() {
        estudianteAlergiaBean = new EstudianteAlergiaBean();
    }

    //Traumas
    public String insertarEstTrauma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteTraumaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                estudianteTraumaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());//Cambio
                estudianteTraumaBean.setEstudianteBean(estudianteBean);
                estudianteTraumaService.insertarEstTrauma(estudianteTraumaBean);
                listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteTraumaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstTrauma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstTrauma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                estudianteTraumaBean.setEstudianteBean(estudianteBean);
                estudianteTraumaBean.setModiPor(beanUsuarioSesion.getUsuario());
                estudianteTraumaService.modificarEstTrauma(estudianteTraumaBean);
                listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteTraumaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstTrauma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarEstTrauma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
//                estudianteTraumaBean.setEstudianteBean(estudianteBean);
                estudianteTraumaService.eliminarEstTrauma(estudianteTraumaBean);
                listaEstudianteTraumaBean = estudianteTraumaService.obtenerEstTraumaPorEst(estudianteTraumaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstTrauma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarEstTrauma() {
        try {
            if (estudianteTraumaBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                modificarEstTrauma();
            } else {
                insertarEstTrauma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectEstTrauma(SelectEvent event) {
        try {
            estudianteTraumaBean = (EstudianteTraumaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstTrauma(Object objeto) {
        try {
            estudianteTraumaBean = (EstudianteTraumaBean) objeto;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstTrauma() {
        estudianteTraumaBean = new EstudianteTraumaBean();
    }

    public List<PersonaBean> completePersona(String query) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<PersonaBean> listaPersonaTodosBean = personaService.obtenerPersonaAdmPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<PersonaBean> listaPersonaFiltroBean = new ArrayList<>();
            for (int i = 0; i < listaPersonaTodosBean.size(); i++) {
                PersonaBean bean = listaPersonaTodosBean.get(i);
                if (bean.getNombreCompleto().toLowerCase().contains(query.toLowerCase())) {
                    listaPersonaFiltroBean.add(bean);
                }
            }
            return listaPersonaFiltroBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return null;
    }

    public void selectPersonal() {
        try {
//            estudianteBean.getPersonaBean().getIdPersona();
            PersonaService personaService = BeanFactory.getPersonaService();
            if (idPersona != null) {
                PersonaBean persona = new PersonaBean();
                persona.setIdPersona(idPersona);
                persona.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                estudianteBean.setPersonaBean(personaService.obtenerPersPorId(persona));
                RequestContext.getCurrentInstance().update(":tabDatosPostulante");
                System.out.println("jajja");
            } else {
                System.out.println("hummm");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void onItemSelect(SelectEvent event) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            PersonaBean persona = (PersonaBean) event.getObject();
            estudianteBean.setPersonaBean(persona);
            System.out.println("");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //excel
    public void obtenerFiltroMatriculadosPorUsuario() {
        try {
            int estado = 0;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            if (matriculaBean.getFechaMatricula() != null) {
                Timestamp t = new Timestamp(matriculaBean.getFechaMatricula().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                estudianteBean.setFechaMatricula(t);
            }
            if (matriculaBean.getAnio() != null && !matriculaBean.getAnio().equals(0)) {
                estudianteBean.setAnio(matriculaBean.getAnio());
            }
            if (matriculaBean.getCreaPor() != null && !matriculaBean.getCreaPor().equals("")) {
                estudianteBean.setCreaPorAyuda(matriculaBean.getCreaPor().toUpperCase().trim());
                estado = 1;
            }
            System.out.println("es" + estudianteBean.getPersonaBean().getIdPersona());
            System.out.println("es" + matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
            listaEstudianteBean = estudianteService.obtenerFiltroMatriculadosPorUsuario(estudianteBean);
            estudianteInfoBean = estudianteService.obtenerEstudianteInfoPorId(listaEstudianteBean.get(0).getPersonaBean().getIdPersona());
            estudianteBean.setTipoSeguro(estudianteInfoBean.getTipoSeguroBean().getCodigo());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Estudiante Seguro    //Estudiante Seguro    //Estudiante Seguro    //Estudiante Seguro    //Estudiante Seguro    //Estudiante Seguro    //Estudiante Seguro
    public void rowSelectEstSeg(SelectEvent event) {
        try {
            estudianteSeguroBean = (EstudianteSeguroBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstudianteSeguro(Object estudianteSeguro) {
        try {
            estudianteSeguroBean = (EstudianteSeguroBean) estudianteSeguro;
            EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
            estudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorId(estudianteSeguroBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                if (estudianteSeguroBean.isStatus()) {
                    estudianteSeguroBean.setStatus(Boolean.FALSE);
                } else {
                    estudianteSeguroBean.setStatus(Boolean.TRUE);
                }
                estudianteSeguroService.cambiarEstadoEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarEstudianteSeguro() {
        estudianteSeguroBean = new EstudianteSeguroBean();
    }

    public String eliminarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroService.eliminarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                limpiarEstudianteSeguro();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteSeguroBean.setCreaPor(usuarioBean.getUsuario());
                estudianteSeguroBean.setEstudianteBean(estudianteBean);
                estudianteSeguroService.insertarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarEstudianteSeguroRapido() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteSeguroBean.setCreaPor(usuarioBean.getUsuario());
                estudianteSeguroBean.setEstudianteBean(estudianteBean);
                if (estudianteSeguroBean.getEntidadBean().getRuc().equals("20131257750")) {
                    estudianteSeguroBean.getCodigoBean().setIdCodigo(13301);
                } else {
                    estudianteSeguroBean.getCodigoBean().setIdCodigo(13302);
                }
                estudianteSeguroService.insertarEstudianteSeguroRapido(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteSeguro() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroService.modificarEstudianteSeguro(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudianteSeguroRapido() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroService.modificarEstudianteSeguroRapido(estudianteSeguroBean);
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarEstudianteSeguroRapidoVer2() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteSeguroService estudianteSeguroService = BeanFactory.getEstudianteSeguroService();
                estudianteSeguroBean.setEstudianteBean(estudianteBean);
                estudianteSeguroService.insertarEstudianteSeguroRapidoVer2(estudianteSeguroBean, usuarioLoginBean.getUsuario());
                listaEstudianteSeguroBean = estudianteSeguroService.obtenerEstudianteSeguroPorEst(estudianteSeguroBean.getEstudianteBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarEstudianteSeguro();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void ObtenerCodigoColegio(CodigoColegioBean bean) {
        try {
            setCodigoColegioBean(bean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void quitarItem(CodigoColegioBean bean) {
        try {
            EstudianteService est = BeanFactory.getEstudianteService();
            est.eliminarCodigoColegio(bean);
            listaCodigoColeBean.remove(bean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarEstudianteSeguro() {
        if (estudianteSeguroBean.getIdEstudianteSeguro() != null) {
            modificarEstudianteSeguro();
        } else {
            insertarEstudianteSeguro();
        }
    }

    public void guardarEstudianteSeguroRapido() {
        if (estudianteSeguroBean.getIdEstudianteSeguro() != null) {
            modificarEstudianteSeguroRapido();
        } else {
            insertarEstudianteSeguroRapido();
        }
    }

//Estudiante Movilidad==========================================================
//    public void obtenerEstudianteMovilidad() {
//        try {
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerEstudianteMovilidad(estudianteBean.getPersonaBean().getIdPersona());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void cargarDatos() {
//        try {
//            EstudianteBean estudianteBean = new EstudianteBean();
//            MovilidadBean movilidadBean = new MovilidadBean();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public String insertarEstudianteMovilidad() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//                estudianteMovilidadBean.setEstudianteBean(estudianteBean);
//                estudianteMovilidadBean.setMovilidadBean(movilidadBean);
//                estudianteMovilidadService.insertarEstudianteMovilidad(estudianteMovilidadBean);
//                listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerEstudianteMovilidad(estudianteBean.getPersonaBean().getIdPersona());
//                limpiarEstudianteMovilidad();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
//
//    public void limpiarMovilidad() {
//        movilidadBean = new MovilidadBean();
//    }
//
//    public String insertaMov() {
//        String pagina = null;
//        try {
//            if (pagina == null) {
//                MovilidadService movilidadService = BeanFactory.getMovilidadService();
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//                movilidadBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());
//                movilidadBean.setCreaPor(usuarioBean.getUsuario());
//                movilidadService.InsertarMovilidad(movilidadBean);
//                limpiarMovilidad();
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
//
//    public void inserta() {
//        try {
//            if (movilidadBean.getIdmovilidad() != null && estudianteBean.getPersonaBean().getIdPersona() != null) {
//                insertaMov();
//            } else {
//                if (estudianteMovilidadBean.getMovilidadBean().getIdmovilidad() != null) {
//                    insertarEstudianteMovilidad();
//                }
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public String actualizarEstudianteMovilidad() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//                estudianteMovilidadService.actualizarEstudianteMovilidad(estudianteMovilidadBean);
//                listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerEstudianteMovilidad(estudianteBean.getPersonaBean().getIdPersona());
//                limpiarEstudianteMovilidad();
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
//
//    public String eliminarEstudianteMovilidad() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//                estudianteMovilidadService.eliminarEstudianteMovilidad(estudianteMovilidadBean);
//                listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerEstudianteMovilidad(this.idPersona);
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception err) {
//            pagina = null;
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//        return pagina;
//    }
//
//    public void guardarEstudianteMovilidad() {
//        try {
//            if (estudianteMovilidadBean.getMovilidadBean().getIdmovilidad() == null) {
//                insertarEstudianteMovilidad();
//            } else {
//                actualizarEstudianteMovilidad();
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void obtenerPorId(Object object) {
//        try {
//            estudianteMovilidadBean = (EstudianteMovilidadBean) object;
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            if (estudianteMovilidadBean.getEstudianteBean().getIdEstudiante() != null
//                    && estudianteMovilidadBean.getMovilidadBean().getIdmovilidad() != null) {
//                estudianteMovilidadBean = estudianteMovilidadService.obtenerPorId(estudianteMovilidadBean);
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void obtenerPorIdMov(Object object) {
//        try {
//            movilidadBean = (MovilidadBean) object;
//            MovilidadService movilidadService = BeanFactory.getMovilidadService();
//            movilidadBean = movilidadService.ObtenerMovilidadPorId(this.idmovilidad);
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void ponerMov(Object object) {
//        movilidadBean = (MovilidadBean) object;
//    }
//
//    public void ponerMovEs(Object object) {
//        estudianteMovilidadBean = (EstudianteMovilidadBean) object;
//    }
//
//    public void obtenerMovilidadPorId(Object object) {
//        try {
//            if (estudianteMovilidadBean != null) {
//                estudianteMovilidadBean = (EstudianteMovilidadBean) object;
//                EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//                estudianteMovilidadFiltroBean = estudianteMovilidadService.obtenerMovilidadPorId(estudianteMovilidadBean.getMovilidadBean().getIdmovilidad());
//                setEstudianteMovilidadBean(estudianteMovilidadBean);
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void obtenerEstudiantePorid(Object object) {
//        try {
//            estudianteMovilidadBean = (EstudianteMovilidadBean) object;
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            estudianteMovilidadFiltroBean = estudianteMovilidadService.obtenerEstudiantePorId(estudianteMovilidadBean.getEstudianteBean().getPersonaBean().getIdPersona());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void limpiarEstudianteMovilidad() {
//        try {
//            estudianteMovilidadBean = new EstudianteMovilidadBean();
//            movilidadBean = new MovilidadBean();
//            estudianteBean = new EstudianteBean();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//    public void rowSelectEstudianteMovilidad(SelectEvent event) {
//        try {
//            estudianteMovilidadBean = (EstudianteMovilidadBean) event.getObject();
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//     public void obtenerMasFiltro() {
//        try {
//            EstudianteBean listaEmpleado = new EstudianteBean();
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            if (estudianteMovilidadBean.getMovilidadBean().getIdmovilidad() != null) {
//                estudianteBean.setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
//                movilidadBean.setIdmovilidad(movilidadBean.getIdmovilidad());
//                setEstudianteMovilidadFiltroBean(estudianteMovilidadFiltroBean);
//            }
//            listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerMFiltro(getEstudianteMovilidadBean());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//
//    public void ponerEstudianteMovilidad(Object object) {
//        try {
//            estudianteMovilidadBean = (EstudianteMovilidadBean) object;
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            estudianteMovilidadBean = estudianteMovilidadService.obtenerPorId(estudianteMovilidadBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void onRowEdit(RowEditEvent event) {
//        try {
//            EstudianteMovilidadService estudianteMovilidadService = BeanFactory.getEstudianteMovilidadService();
//            estudianteMovilidadBean = new EstudianteMovilidadBean();
//            estudianteMovilidadBean.setEstudianteBean(((EstudianteMovilidadBean) event.getObject()).getEstudianteBean());
//            estudianteMovilidadBean.setMovilidadBean(((EstudianteMovilidadBean) event.getObject()).getMovilidadBean());
//            estudianteMovilidadBean.setObs(((EstudianteMovilidadBean) event.getObject()).getObs());
//            estudianteMovilidadService.actualizarEstudianteMovilidad(estudianteMovilidadBean);
//            listaEstudianteMovilidadBean = estudianteMovilidadService.obtenerEstudianteMovilidad(estudianteBean.getPersonaBean().getIdPersona());
//            FacesMessage msg = new FacesMessage("Tabla Editada:", ((EstudianteMovilidadBean) event.getObject()).getEstudianteBean().getIdEstudiante());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//            estudianteMovilidadBean = new EstudianteMovilidadBean();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("EdiciÃ³n Cancelada", ((EstudianteMovilidadBean) event.getObject()).getEstudianteBean().getIdEstudiante());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
    public void mostarViveCon() {
        try {
            if (estudianteInfoBean.getEstadoCivilPadres().getIdCodigo() == 11105
                    || estudianteInfoBean.getEstadoCivilPadres().getIdCodigo() == 11106
                    || estudianteInfoBean.getEstadoCivilPadres().getIdCodigo() == 11107
                    || estudianteInfoBean.getEstadoCivilPadres().getIdCodigo() == 11108) {
                estudianteInfoBean.setViveConVista(true);
                estudianteInfoBean.setFecViveConVista(true);
            } else {
                estudianteInfoBean.setViveConVista(false);
                estudianteInfoBean.setFecViveConVista(false);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostarStatusEst() {
        try {
            if (estudianteBean.getTipoStatusEst().getIdCodigo() != null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorId(estudianteBean.getTipoStatusEst());
                if (estudianteBean.getTipoStatusEst().getIdCodigo() == 18004
                        || estudianteBean.getTipoStatusEst().getIdCodigo() == 18007) {
                    estudianteBean.setStatusEstVista(false);
                    estudianteBean.setStatusEstVista(false);
                    estudianteBean.setTipoStatusEst(cod);
                } else {
                    estudianteBean.setStatusEstVista(true);
                    estudianteBean.setStatusEstVista(true);
                    estudianteBean.setTipoStatusEst(cod);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorFiltro() {
        try {
            MovilidadService movilidadService = BeanFactory.getMovilidadService();
            if (movilidadBean.getIdmovilidad() != null) {
                movilidadBean.setIdmovilidad(movilidadBean.getIdmovilidad().trim());
            }
            listaMovilidadBean = movilidadService.obtenerFiltro(movilidadBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void cargarFormulario() {
//        Object parametro = new MaristaUtils().requestObtenerObjeto("caniari");
//        if (parametro != null) {
//            EstudianteBean bean = new EstudianteBean();
//            String idPersona = (String) parametro;
//            bean.getPersonaBean().setIdPersona(idPersona);
//            obtenerEstudiantePorId(bean);
//        }
//    }
    public void cargarFormulario() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        try {
            if (parametro != null) {
//          
                EstudianteBean bean = new EstudianteBean();
                bean.getPersonaBean().setIdPersona(parametro);
                bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                obtenerEstudiantePorId(bean);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarFormularioMat() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        try {
            if (parametro != null) {
//          
                EstudianteBean bean = new EstudianteBean();
                bean.getPersonaBean().setIdPersona(parametro);
                bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                obtenerEstudiantePorId(bean);

            }
            System.out.println("anio 1 " + anio);
            Integer mes = ((Calendar.getInstance().get((Calendar.MONTH))));;
            if (mes.equals(11) || mes.equals(12)) {
                System.out.println("mes " + mes);
                anio = anio + 1;
                System.out.println("anio 2 " + anio);
            }
            verTipoDoc(null);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //pintar estudiante en admisionMB
    public void ponerPersonaPostulanteEnAdmision() {
        try {
            if (estudianteBean.getPersonaBean().getIdPersona() != null
                    && estudianteBean.getPersonaBean().getNroDoc() != null
                    && !estudianteBean.getPersonaBean().getNroDoc().equals("")) {
                AdmisionMB admisionMB = (AdmisionMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("admisionMB");
                admisionMB.getAdmisionBean().setEstudianteBean(estudianteBean);
                //paso el grado acadÃ©mico al que postula al grado acadÃ©mico de admisiÃ³n
                admisionMB.getAdmisionBean().getGradoAcademicoBean().setIdGradoAcademico(estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
//                EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
//                listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(estudianteBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("admisionMB", admisionMB);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeDNIobli", null));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //pintar estudiante en filtro
//    public void ponerEstudianteEnFiltro(Object estudiante) {
//        try {
//            estudianteFiltroBean = (EstudianteBean) estudiante;
//            MatriculaMB matriculaMB = (MatriculaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("matriculaMB");
//            matriculaMB.getEstudianteFiltroBean().getPersonaBean().setIdPersona(estudianteFiltroBean.getPersonaBean().getIdPersona());
//            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("matriculaMB", matriculaMB);
//            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            System.out.println("est. en filtro");
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public String validarPostulanteAdmision() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
                AdmisionBean admision = new AdmisionBean();
                admision = admisionService.validarPostuEnAdmision(estudianteBean);
                if (comodin) {

                }
                if (admision != null) {
                    this.flgExisteAdmison = true;
                } else {
                    this.flgExisteAdmison = false;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void cargarMatriEst() {
        try {
//            String parametro = (String) new MaristaUtils().requestObtenerObjeto("codEst");
//            if (parametro != null) {

            getMatriculaBean().getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
            getMatriculaBean().getEstudianteBean().getPersonaBean().setApepat(null);
            getMatriculaBean().getEstudianteBean().getPersonaBean().setApemat(null);
            getMatriculaBean().getEstudianteBean().getPersonaBean().setNombre(null);
            getMatriculaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            System.out.println("anio: " + anio);
            //a el año actual
            getMatriculaBean().setAnio(anio);//por mientras
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            List<MatriculaBean> listaMatriculaFlgFlaseBean = new ArrayList<>();
            //valido si esta matriculado 
            listaMatriculaFlgFlaseBean = matriculaService.obtenerFiltroNoMatriculados(matriculaBean);

            if (listaMatriculaFlgFlaseBean != null && !listaMatriculaFlgFlaseBean.isEmpty()) {
                //no esta matriculado
                System.out.println("lista no vacia: ");
                setMatriculaBean(listaMatriculaFlgFlaseBean.get(0));
                if (Objects.equals(getMatriculaBean().getEstudianteBean().getTipoStatusEst().getIdCodigo(), MaristaConstantes.COD_ESTUDIANTE_ACTIVO)) {
                    matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
                    fechaActual = new GregorianCalendar();
                    getMatriculaBean().setFechaMatricula(fechaActual.getTime());
                    setBloqueoMatricula(Boolean.FALSE);
//                    MatriculaService matriculaService = BeanFactory.getMatriculaService();
                    matriculaBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    matriculaBean.setCreaPor(usuarioLoginBean.getUsuario());
                    matriculaBean.setModiPor(usuarioLoginBean.getUsuario());
                    matriculaBean.setFlgMatricula(Boolean.TRUE);
//                matriculaBean.setModiPor(usuarioLogin.getUsuario());
                    matriculaService.modificarMatricula(matriculaBean);
                    //grabar datos de padre,madre y otros 
//                    System.out.println("padre: " + padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
//                    System.out.println("padre: " + madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
//                    System.out.println("padre: " + familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());

//                    if (getPadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null) {
//                        System.out.println("entro getPadreEstudianteBean :D");
//                        guardarFamiliarEstudiante(padreEstudianteBean);
//                    } else {
//                        System.out.println("no entra getPadreEstudianteBean :D");
//                    }
//                    if (getMadreEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null) {
//                        System.out.println("entro getMadreEstudianteBean :D");
//                        guardarFamiliarEstudiante(madreEstudianteBean);
//                    } else {
//                        System.out.println("no entra getMadreEstudianteBean :D");
//                    }
//                    if (getFamiliarEstudianteBean().getFamiliarBean().getPersonaBean().getIdPersona() != null) {
//                        System.out.println("entro getFamiliarEstudianteBean :D");
//                        guardarFamiliarEstudiante(familiarEstudianteBean);
//                    } else {
//                        System.out.println("no entra getFamiliarEstudianteBean :D");
//                    }
//                    guardarEstudianteRapido("estudiante");
//                matriculaBean.setModiPor(usuarioLogin.getUsuario());
//                limpiarMatricula();
//                limpiarFiltroNoMatriculados();
//                limpiarFiltroMatriculados();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    System.out.println("msjCondionado");
                    new MensajePrime().addInformativeMessagePer("msjCondionado");
                    setBloqueoMatricula(Boolean.TRUE);
                }
            } else {
                System.out.println("y amatri");
                new MensajePrime().addInformativeMessagePer(MensajesBackEnd.getValueOfKey("msjAlumnoYaMatri", null));
                setBloqueoMatricula(Boolean.TRUE);
            }
            estudianteBean = new EstudianteBean();
            estudianteInfoBean = new EstudianteInfoBean();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
            //  guardarEstudianteRapido("Estudiante");
//            }
            limpiarEstudiante();
            limpiarFiltroEstudiante();
            verTipoDoc(null);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Para Estudiante Bloqueo
    public void changeTipo(String tipo) {
        try {
            String tipoSol = "";
            if (tipo.equals("soli")) {
                tipoSol = idTipoSol;
            }
            switch (tipoSol) {
                case "COL":
                    this.flgFiltroPersonal = true;
                    LegajoMB legajoMB = (LegajoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("legajoMB");
                    if (legajoMB != null) {
                        legajoMB.limpiarPersonalFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("legajoMB", legajoMB);
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
            if (tp.equals("solicitante")) {
                if (getIdTipoSol() == null) {
                    setIdTipoSol("COL");
                }
                this.flgSoli = true;
                changeTipo("soli");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void viveCon() {
        try {
            if (estudianteInfoBean.getViveCon().getIdCodigo() == 14606) {
                this.flgOtros = true;
                System.out.println("si es otro");
            } else {
                this.flgOtros = false;
                System.out.println("no es otro");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void obtenerVacunaPorEdad() {
        try {
//            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21701) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_Recien_Nacido);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21702) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_2Meses);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21703) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_2Meses);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21704) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_6Meses);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21705) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_1Año);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21706) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_1Año_6Meses);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21707) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_2Años);
            }
            if (estudianteVacunaBean.getTipoEdad().getIdCodigo() == 21708) {
                listaTipoVacunaBean = codigoService.obtenerPorIdLista(MaristaConstantes.vacuna_3Años_6Meses);
            }

            if (listaTipoVacunaBean.size() == 1) {
                estudianteVacunaBean.getTipoVacunas().setIdCodigo(listaTipoVacunaBean.get(0).getIdCodigo());
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String guardarEstudianteBloqueo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();

                if (getEstudianteBloqueoBean().getIdEstudianteBloqueo() == null) {
                    estudianteBloqueoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    System.out.print(estudianteBean.getPersonaBean().getIdPersona());
                    estudianteBloqueoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
                    listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
                    estudianteBloqueoBean.setAnio(anio);
                    estudianteBloqueoService.insertar(estudianteBloqueoBean, listaEstudianteBloqueoBean, estudianteBean);

                }
//                    else {
//                    inventarioAlmacenBean.setModiPor(usuarioSessionBean.getUsuario());
//                    inventarioAlmacenService.actualizar(inventarioAlmacenBean, getCatalogoBean(), usuarioSessionBean);
//                    listaInventarioAlmacenBean = inventarioAlmacenService.obtenerPorFiltroIAlmacen(inventarioAlmacenFiltroBean);
//                    obtenerBanderas();
//                }  
                listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(estudianteBloqueoBean);
                limpiarEstudianteBloqueo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void actualizarTablaBloqueo(EstudianteBloqueoBean bloqueoBean, Integer n) {
        try {
//            Integer idCodigo=0;
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
//            estudianteBloqueoBean= estudianteBloqueoService.obtenerEstudianteBloqueo(estudianteBloqueoBean);
            bloqueoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            bloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
            Integer id = 0;
            id = bloqueoBean.getTipoStatusBloqueoBean().getIdCodigo();
            if (id == 20901) {
                bloqueoBean.getTipoStatusBloqueoBean().setCodigo(MaristaConstantes.bloqueo_Activo);
            }
            if (id == 20902) {
                bloqueoBean.getTipoStatusBloqueoBean().setCodigo(MaristaConstantes.bloqueo_Resuelto);
            }
//            listaStatusBloqueo= codigoService.obtener
            bloqueoBean.setModiPor(usuarioLoginBean.getUsuario());
            listaEstudianteBloqueoBean.get(n).setFechaSolucion(new Date());
//            n=bloqueoBean.getFechaSolucion().compareTo(bloqueoBean.getFechaSolucion());
            System.out.println(n + ": " + listaEstudianteBloqueoBean.get(n).getFechaSolucion());
            estudianteBloqueoService.actualizar(bloqueoBean, listaEstudianteBloqueoBean, estudianteBean);
            listaEstudianteBloqueoBean = new ArrayList<>();
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(bloqueoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstudianteBloqueo() {
        estudianteBloqueoBean = new EstudianteBloqueoBean();
        this.fechaBloqueo = new GregorianCalendar();
        getEstudianteBloqueoBean().setAnio(fechaBloqueo.get(Calendar.YEAR));
        getEstudianteBloqueoBean().setFechaBloqueo(fechaBloqueo.getTime());

    }

    public void imprimirTodosPdfGeneral() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteBloqueoEstudiante.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EstudianteBloqueoRepBean> listaTitutlo = new ArrayList<>();
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaTitutlo = estudianteBloqueoService.obtenerCabecera(uniNeg, anio);
            if (!listaTitutlo.isEmpty()) {
                for (int j = 0; j < listaTitutlo.size(); j++) {
                    List<EstudianteBloqueoRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = estudianteBloqueoService.obtenerDetalle(uniNeg, anio, listaTitutlo.get(j).getIdEstudiante());
                    listaTitutlo.get(j).setListaDetalle(listaDetalle);
                }
            }
            System.out.println("imagenRuta:" + absoluteWebPath);
            System.out.println("jasper: " + archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaTitutlo);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=SaldoPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                this.flgPorStatusEst = false;
                bloqueoFiltroBean.setEstudianteBean(null);
                bloqueoFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodosAfter() {
        try {
            if (this.flgTodosAfter == true) {
                this.flgPorNivelGradoAfter = false;
                this.flgEstEspMatricula = false;
                this.flgPorStatusEst = false;
                bloqueoFiltroAfterBean.setEstudianteBean(null);
                bloqueoFiltroAfterBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void verificarFiltroEstEsp() {
        try {
            if (this.flgEstEsp == true) {
                this.flgTodos = false;
                this.flgPorNivelGrado = false;
                this.flgPorStatusEst = false;
                bloqueoFiltroBean.setEstudianteBean(null);
                bloqueoFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEspAfter() {
        try {
            if (this.flgEstEspAfter == true) {
                this.flgTodosAfter = false;
                this.flgPorNivelGradoAfter = false;
                this.flgPorStatusEst = false;
                bloqueoFiltroAfterBean.setEstudianteBean(null);
                bloqueoFiltroAfterBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroNivelGrado() {
        try {
            if (this.flgPorNivelGrado == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
                this.flgPorStatusEst = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroNivelGradoAfter() {
        try {
            if (this.flgPorNivelGradoAfter == true) {
                this.flgTodosAfter = false;
                this.flgEstEspAfter = false;
                this.flgPorStatusEst = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroStatusEst() {
        try {
            if (this.flgPorStatusEst == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
                this.flgPorNivelGrado = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroStatusEstAfter() {
        try {
            if (this.flgPorStatusEst == true) {
                this.flgTodosAfter = false;
                this.flgEstEspAfter = false;
                this.flgPorNivelGradoAfter = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            bloqueoFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (flgTodos == true) {
                bloqueoFiltroBean.setAnio(null);
                listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerFiltroEstudianteMasivo(bloqueoFiltroBean);
            } else {
                Calendar miCalendario = Calendar.getInstance();
                bloqueoFiltroBean.setAnio(miCalendario.get(Calendar.YEAR));
                if (bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    bloqueoFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    bloqueoFiltroBean.getEstudianteBean().getPersonaBean().setApepat(bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    bloqueoFiltroBean.getEstudianteBean().getPersonaBean().setApemat(bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    bloqueoFiltroBean.getEstudianteBean().getPersonaBean().setNombre(bloqueoFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().setIdGradoAcademico(bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                }
                if (bloqueoFiltroBean.getTipoStatusEstBean().getIdCodigo() != null && !bloqueoFiltroBean.getTipoStatusEstBean().getIdCodigo().equals(0)) {
                    bloqueoFiltroBean.getTipoStatusEstBean().setIdCodigo(bloqueoFiltroBean.getTipoStatusEstBean().getIdCodigo());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaEstudianteBloqueoBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerFiltroEstudianteMasivo(bloqueoFiltroBean);
                    if (listaEstudianteBloqueoBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaEstudianteBloqueoBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirTodosPdfMatricula() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteMatriculaFC.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");

            File file = new File(archivoJasper);
            List<EstudianteMatriculaRepBean> listaMatricula = new ArrayList<>();
            listaMatricula = estudianteService.obtenerAlumnoMatricula(estudianteBean.getPersonaBean().getIdPersona(), uniNeg, anio);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMatricula);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
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

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(bloqueoFiltroBean.getEstudianteBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        bloqueoFiltroBean = new EstudianteBloqueoBean();
        listaEstudianteBloqueoBean = new ArrayList<>();
        flgEstEsp = false;
        flgPorNivelGrado = false;
        flgTodos = false;
//        setFlgMatriPend(0);
//        setFlgReProceso(0);

        Calendar miCalendario = Calendar.getInstance();
        getBloqueoFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getBloqueoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

    }

    //Metodos Getter y Setter
    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public List<CodigoBean> getListaStatusEst() {
        if (listaStatusEst == null) {
            listaStatusEst = new ArrayList<>();
        }
        return listaStatusEst;
    }

    public void setListaStatusEst(List<CodigoBean> listaStatusEst) {
        this.listaStatusEst = listaStatusEst;
    }

    public List<CodigoBean> getListaProcedencia() {
        if (listaProcedencia == null) {
            listaProcedencia = new ArrayList<>();
        }
        return listaProcedencia;
    }

    public void setListaProcedencia(List<CodigoBean> listaProcedencia) {
        this.listaProcedencia = listaProcedencia;
    }

    public List<GradoAcademicoBean> getListaGradoIngreso() {
        if (listaGradoIngreso == null) {
            listaGradoIngreso = new ArrayList<>();
        }
        return listaGradoIngreso;
    }

    public void setListaGradoIngreso(List<GradoAcademicoBean> listaGradoIngreso) {
        this.listaGradoIngreso = listaGradoIngreso;
    }

    public List<Integer> getListaPeriodoIngreso() {
        if (listaPeriodoIngreso == null) {
            listaPeriodoIngreso = new ArrayList<>();
        }
        return listaPeriodoIngreso;
    }

    public void setListaPeriodoIngreso(List<Integer> listaPeriodoIngreso) {
        this.listaPeriodoIngreso = listaPeriodoIngreso;
    }

    public List<CodigoBean> getListaIdioma() {
        if (listaIdioma == null) {
            listaIdioma = new ArrayList<>();
        }
        return listaIdioma;
    }

    public void setListaIdioma(List<CodigoBean> listaIdioma) {
        this.listaIdioma = listaIdioma;
    }

    public List<CodigoBean> getListaViaDomicilio() {
        if (listaViaDomicilio == null) {
            listaViaDomicilio = new ArrayList<>();
        }
        return listaViaDomicilio;
    }

    public void setListaViaDomicilio(List<CodigoBean> listaViaDomicilio) {
        this.listaViaDomicilio = listaViaDomicilio;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public List<PersonaBean> getListaPersonaBean() {
        if (listaPersonaBean == null) {
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
    }

    public List<CodigoBean> getListaDocPer() {
        if (listaDocPer == null) {
            listaDocPer = new ArrayList<>();
        }
        return listaDocPer;
    }

    public void setListaDocPer(List<CodigoBean> listaDocPer) {
        this.listaDocPer = listaDocPer;
    }

    public List<GradoAcademicoBean> getListaGradoAcademico() {
        if (listaGradoAcademico == null) {
            listaGradoAcademico = new ArrayList<>();
        }
        return listaGradoAcademico;
    }

    public void setListaGradoAcademico(List<GradoAcademicoBean> listaGradoAcademico) {
        this.listaGradoAcademico = listaGradoAcademico;
    }

    public List<PaisBean> getListaNacionalidad() {
        if (listaNacionalidad == null) {
            listaNacionalidad = new ArrayList<>();
        }
        return listaNacionalidad;
    }

    public void setListaNacionalidad(List<PaisBean> listaNacionalidad) {
        this.listaNacionalidad = listaNacionalidad;
    }

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        if (listaUnidadNegocioBean == null) {
            listaUnidadNegocioBean = new ArrayList<>();
        }
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

    public EstudianteBean getEstudianteFiltroBean() {
        if (estudianteFiltroBean == null) {
            estudianteFiltroBean = new EstudianteBean();
        }
        return estudianteFiltroBean;
    }

    public void setEstudianteFiltroBean(EstudianteBean estudianteFiltroBean) {
        this.estudianteFiltroBean = estudianteFiltroBean;
    }

    public EstudianteInfoBean getEstudianteInfoBean() {
        if (estudianteInfoBean == null) {
            estudianteInfoBean = new EstudianteInfoBean();
        }
        return estudianteInfoBean;
    }

    public void setEstudianteInfoBean(EstudianteInfoBean estudianteInfoBean) {
        this.estudianteInfoBean = estudianteInfoBean;
    }

    public List<CodigoBean> getListaEstadoCivilPadres() {
        if (listaEstadoCivilPadres == null) {
            listaEstadoCivilPadres = new ArrayList<>();
        }
        return listaEstadoCivilPadres;
    }

    public void setListaEstadoCivilPadres(List<CodigoBean> listaEstadoCivilPadres) {
        this.listaEstadoCivilPadres = listaEstadoCivilPadres;
    }

    public List<CodigoBean> getListaViveCon() {
        if (listaViveCon == null) {
            listaViveCon = new ArrayList<>();
        }
        return listaViveCon;
    }

    public void setListaViveCon(List<CodigoBean> listaViveCon) {
        this.listaViveCon = listaViveCon;
    }

//    public Integer getIdColegioProceEntidad() {
//        return idColegioProceEntidad;
//    }
//
//    public void setIdColegioProceEntidad(Integer idColegioProceEntidad) {
//        this.idColegioProceEntidad = idColegioProceEntidad;
//    }
    public List<DepartamentoBean> getListaDepartamentoNaci() {
        if (listaDepartamentoNaci == null) {
            listaDepartamentoNaci = new ArrayList<>();
        }
        return listaDepartamentoNaci;
    }

    public void setListaDepartamentoNaci(List<DepartamentoBean> listaDepartamentoNaci) {
        this.listaDepartamentoNaci = listaDepartamentoNaci;
    }

    public List<ProvinciaBean> getListaProvinciaNaci() {
        if (listaProvinciaNaci == null) {
            listaProvinciaNaci = new ArrayList<>();
        }
        return listaProvinciaNaci;
    }

    public void setListaProvinciaNaci(List<ProvinciaBean> listaProvinciaNaci) {
        this.listaProvinciaNaci = listaProvinciaNaci;
    }

    public List<DistritoBean> getListaDistritoNaci() {
        if (listaDistritoNaci == null) {
            listaDistritoNaci = new ArrayList<>();
        }
        return listaDistritoNaci;
    }

    public void setListaDistritoNaci(List<DistritoBean> listaDistritoNaci) {
        this.listaDistritoNaci = listaDistritoNaci;
    }

    public List<DepartamentoBean> getListaDepartamentoDomi() {
        if (listaDepartamentoDomi == null) {
            listaDepartamentoDomi = new ArrayList<>();
        }
        return listaDepartamentoDomi;
    }

    public void setListaDepartamentoDomi(List<DepartamentoBean> listaDepartamentoDomi) {
        this.listaDepartamentoDomi = listaDepartamentoDomi;
    }

    public List<ProvinciaBean> getListaProvinciaDomi() {
        if (listaProvinciaDomi == null) {
            listaProvinciaDomi = new ArrayList<>();
        }
        return listaProvinciaDomi;
    }

    public void setListaProvinciaDomi(List<ProvinciaBean> listaProvinciaDomi) {
        this.listaProvinciaDomi = listaProvinciaDomi;
    }

    public List<DistritoBean> getListaDistritoDomi() {
        if (listaDistritoDomi == null) {
            listaDistritoDomi = new ArrayList<>();
        }
        return listaDistritoDomi;
    }

    public void setListaDistritoDomi(List<DistritoBean> listaDistritoDomi) {
        this.listaDistritoDomi = listaDistritoDomi;
    }

    public FamiliarEstudianteBean getFamiliarEstudianteBean() {
        if (familiarEstudianteBean == null) {
            familiarEstudianteBean = new FamiliarEstudianteBean();
        }
        return familiarEstudianteBean;
    }

    public void setFamiliarEstudianteBean(FamiliarEstudianteBean familiarEstudianteBean) {
        this.familiarEstudianteBean = familiarEstudianteBean;
    }

    public List<FamiliarEstudianteBean> getListaFamiliarEstudianteBean() {
        if (listaFamiliarEstudianteBean == null) {
            listaFamiliarEstudianteBean = new ArrayList<>();
        }
        return listaFamiliarEstudianteBean;
    }

    public void setListaFamiliarEstudianteBean(List<FamiliarEstudianteBean> listaFamiliarEstudianteBean) {
        this.listaFamiliarEstudianteBean = listaFamiliarEstudianteBean;
    }

    public List<CodigoBean> getListaTipoProfesionBean() {
        if (listaTipoProfesionBean == null) {
            listaTipoProfesionBean = new ArrayList<>();
        }
        return listaTipoProfesionBean;
    }

    public void setListaTipoProfesionBean(List<CodigoBean> listaTipoProfesionBean) {
        this.listaTipoProfesionBean = listaTipoProfesionBean;
    }

    public List<CodigoBean> getListaTipoOcupacionBean() {
        if (listaTipoOcupacionBean == null) {
            listaTipoOcupacionBean = new ArrayList<>();
        }
        return listaTipoOcupacionBean;
    }

    public void setListaTipoOcupacionBean(List<CodigoBean> listaTipoOcupacionBean) {
        this.listaTipoOcupacionBean = listaTipoOcupacionBean;
    }

    public List<EntidadSedeBean> getListaColegioBean() {
        if (listaColegioBean == null) {
            listaColegioBean = new ArrayList<>();
        }
        return listaColegioBean;
    }

    public void setListaColegioBean(List<EntidadSedeBean> listaColegioBean) {
        this.listaColegioBean = listaColegioBean;
    }

    public List<CodigoBean> getListaTipoParentescoBean() {
        if (listaTipoParentescoBean == null) {
            listaTipoParentescoBean = new ArrayList<>();
        }
        return listaTipoParentescoBean;
    }

    public void setListaTipoParentescoBean(List<CodigoBean> listaTipoParentescoBean) {
        this.listaTipoParentescoBean = listaTipoParentescoBean;
    }

    public List<EntidadBean> getListaEntidadColegioBean() {
        if (listaEntidadColegioBean == null) {
            listaEntidadColegioBean = new ArrayList<>();
        }
        return listaEntidadColegioBean;
    }

    public void setListaEntidadColegioBean(List<EntidadBean> listaEntidadColegioBean) {
        this.listaEntidadColegioBean = listaEntidadColegioBean;
    }

    public boolean isComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    public List<EntidadBean> getListaFamCentTraEntBean() {
        if (listaFamCentTraEntBean == null) {
            listaFamCentTraEntBean = new ArrayList<>();
        }
        return listaFamCentTraEntBean;
    }

    public void setListaFamCentTraEntBean(List<EntidadBean> listaFamCentTraEntBean) {
        this.listaFamCentTraEntBean = listaFamCentTraEntBean;
    }

    public List<EntidadSedeBean> getListaFamCentTraSedeBean() {
        if (listaFamCentTraSedeBean == null) {
            listaFamCentTraSedeBean = new ArrayList<>();
        }
        return listaFamCentTraSedeBean;
    }

    public void setListaFamCentTraSedeBean(List<EntidadSedeBean> listaFamCentTraSedeBean) {
        this.listaFamCentTraSedeBean = listaFamCentTraSedeBean;
    }

    public List<CodigoBean> getListaTipoCargoBean() {
        if (listaTipoCargoBean == null) {
            listaTipoCargoBean = new ArrayList<>();
        }
        return listaTipoCargoBean;
    }

    public void setListaTipoCargoBean(List<CodigoBean> listaTipoCargoBean) {
        this.listaTipoCargoBean = listaTipoCargoBean;
    }

    public EstudianteEnfermedadBean getEstudianteEnfermedadBean() {
        if (estudianteEnfermedadBean == null) {
            estudianteEnfermedadBean = new EstudianteEnfermedadBean();
        }
        return estudianteEnfermedadBean;
    }

    public void setEstudianteEnfermedadBean(EstudianteEnfermedadBean estudianteEnfermedadBean) {
        this.estudianteEnfermedadBean = estudianteEnfermedadBean;
    }

    public EstudianteMedicamentoBean getEstudianteMedicamentoBean() {
        if (estudianteMedicamentoBean == null) {
            estudianteMedicamentoBean = new EstudianteMedicamentoBean();
        }
        return estudianteMedicamentoBean;
    }

    public void setEstudianteMedicamentoBean(EstudianteMedicamentoBean estudianteMedicamentoBean) {
        this.estudianteMedicamentoBean = estudianteMedicamentoBean;
    }

    public EstudianteAlergiaBean getEstudianteAlergiaBean() {
        if (estudianteAlergiaBean == null) {
            estudianteAlergiaBean = new EstudianteAlergiaBean();
        }
        return estudianteAlergiaBean;
    }

    public void setEstudianteAlergiaBean(EstudianteAlergiaBean estudianteAlergiaBean) {
        this.estudianteAlergiaBean = estudianteAlergiaBean;
    }

    public EstudianteTraumaBean getEstudianteTraumaBean() {
        if (estudianteTraumaBean == null) {
            estudianteTraumaBean = new EstudianteTraumaBean();
        }
        return estudianteTraumaBean;
    }

    public void setEstudianteTraumaBean(EstudianteTraumaBean estudianteTraumaBean) {
        this.estudianteTraumaBean = estudianteTraumaBean;
    }

    public List<EnfermedadBean> getListaEnfermedadBean() {
        if (listaEnfermedadBean == null) {
            listaEnfermedadBean = new ArrayList<>();
        }
        return listaEnfermedadBean;
    }

    public void setListaEnfermedadBean(List<EnfermedadBean> listaEnfermedadBean) {
        this.listaEnfermedadBean = listaEnfermedadBean;
    }

    public List<EstudianteEnfermedadBean> getListaEstudianteEnfermedadBean() {
        if (listaEstudianteEnfermedadBean == null) {
            listaEstudianteEnfermedadBean = new ArrayList<>();
        }
        return listaEstudianteEnfermedadBean;
    }

    public void setListaEstudianteEnfermedadBean(List<EstudianteEnfermedadBean> listaEstudianteEnfermedadBean) {
        this.listaEstudianteEnfermedadBean = listaEstudianteEnfermedadBean;
    }

    public List<EstudianteMedicamentoBean> getListaEstudianteMedicamentoBean() {
        if (listaEstudianteMedicamentoBean == null) {
            listaEstudianteMedicamentoBean = new ArrayList<>();
        }
        return listaEstudianteMedicamentoBean;
    }

    public void setListaEstudianteMedicamentoBean(List<EstudianteMedicamentoBean> listaEstudianteMedicamentoBean) {
        this.listaEstudianteMedicamentoBean = listaEstudianteMedicamentoBean;
    }

    public List<EstudianteAlergiaBean> getListaEstudianteAlergiaBean() {
        if (listaEstudianteAlergiaBean == null) {
            listaEstudianteAlergiaBean = new ArrayList<>();
        }
        return listaEstudianteAlergiaBean;
    }

    public void setListaEstudianteAlergiaBean(List<EstudianteAlergiaBean> listaEstudianteAlergiaBean) {
        this.listaEstudianteAlergiaBean = listaEstudianteAlergiaBean;
    }

    public List<EstudianteTraumaBean> getListaEstudianteTraumaBean() {
        if (listaEstudianteTraumaBean == null) {
            listaEstudianteTraumaBean = new ArrayList<>();
        }
        return listaEstudianteTraumaBean;
    }

    public void setListaEstudianteTraumaBean(List<EstudianteTraumaBean> listaEstudianteTraumaBean) {
        this.listaEstudianteTraumaBean = listaEstudianteTraumaBean;
    }

    public List<CodigoBean> getListaTipoAlergiaBean() {
        if (listaTipoAlergiaBean == null) {
            listaTipoAlergiaBean = new ArrayList<>();
        }
        return listaTipoAlergiaBean;
    }

    public void setListaTipoAlergiaBean(List<CodigoBean> listaTipoAlergiaBean) {
        this.listaTipoAlergiaBean = listaTipoAlergiaBean;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public EstudianteSeguroBean getEstudianteSeguroBean() {
        if (estudianteSeguroBean == null) {
            estudianteSeguroBean = new EstudianteSeguroBean();
        }
        return estudianteSeguroBean;
    }

    public void setEstudianteSeguroBean(EstudianteSeguroBean estudianteSeguroBean) {
        this.estudianteSeguroBean = estudianteSeguroBean;
    }

    public List<EstudianteSeguroBean> getListaEstudianteSeguroBean() {
        if (listaEstudianteSeguroBean == null) {
            listaEstudianteSeguroBean = new ArrayList<>();
        }
        return listaEstudianteSeguroBean;
    }

    public void setListaEstudianteSeguroBean(List<EstudianteSeguroBean> listaEstudianteSeguroBean) {
        this.listaEstudianteSeguroBean = listaEstudianteSeguroBean;
    }

    public List<CodigoBean> getListaCodigoBean() {
        if (listaCodigoBean == null) {
            listaCodigoBean = new ArrayList<>();
        }
        return listaCodigoBean;
    }

    public void setListaCodigoBean(List<CodigoBean> listaCodigoBean) {
        this.listaCodigoBean = listaCodigoBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoHabilitado() {
        if (listaGradoAcademicoHabilitado == null) {
            listaGradoAcademicoHabilitado = new ArrayList<>();
        }

        return listaGradoAcademicoHabilitado;
    }

    public void setListaGradoAcademicoHabilitado(List<GradoAcademicoBean> listaGradoAcademicoHabilitado) {
        this.listaGradoAcademicoHabilitado = listaGradoAcademicoHabilitado;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public List<PerfilBean> getListaPerfilBean() {
        if (listaPerfilBean == null) {
            listaPerfilBean = new ArrayList<>();
        }
        return listaPerfilBean;
    }

    public void setListaPerfilBean(List<PerfilBean> listaPerfilBean) {
        this.listaPerfilBean = listaPerfilBean;
    }

    public MovilidadBean getMovilidadBean() {
        if (movilidadBean == null) {
            movilidadBean = new MovilidadBean();
        }
        return movilidadBean;
    }

    public void setMovilidadBean(MovilidadBean movilidadBean) {
        this.movilidadBean = movilidadBean;
    }

    public List<MovilidadBean> getListaMovilidadBean() {
        if (listaMovilidadBean == null) {
            listaMovilidadBean = new ArrayList<>();
        }
        return listaMovilidadBean;
    }

    public void setListaMovilidadBean(List<MovilidadBean> listaMovilidadBean) {
        this.listaMovilidadBean = listaMovilidadBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

//    public EstudianteMovilidadBean getEstudianteMovilidadBean() {
//        if (estudianteMovilidadBean == null) {
//            estudianteMovilidadBean = new EstudianteMovilidadBean();
//        }
//        return estudianteMovilidadBean;
//    }
//
//    public void setEstudianteMovilidadBean(EstudianteMovilidadBean estudianteMovilidadBean) {
//        this.estudianteMovilidadBean = estudianteMovilidadBean;
//    }
//
//    public List<EstudianteMovilidadBean> getListaEstudianteMovilidadBean() {
//        if (listaEstudianteMovilidadBean == null) {
//            listaEstudianteMovilidadBean = new ArrayList<>();
//        }
//        return listaEstudianteMovilidadBean;
//    }
//
//    public void setListaEstudianteMovilidadBean(List<EstudianteMovilidadBean> listaEstudianteMovilidadBean) {
//        this.listaEstudianteMovilidadBean = listaEstudianteMovilidadBean;
//    }
//
//    public List<EstudianteMovilidadBean> getEstudianteMovilidadFiltroBean() {
//        if (estudianteMovilidadFiltroBean == null) {
//            estudianteMovilidadFiltroBean = new ArrayList<>();
//        }
//        return estudianteMovilidadFiltroBean;
//    }
//
//    public void setEstudianteMovilidadFiltroBean(List<EstudianteMovilidadBean> estudianteMovilidadFiltroBean) {
//        this.estudianteMovilidadFiltroBean = estudianteMovilidadFiltroBean;
//    }
    public String getIdmovilidad() {
        return idmovilidad;
    }

    public void setIdmovilidad(String idmovilidad) {
        this.idmovilidad = idmovilidad;
    }

    public List<CodigoBean> getListaMotivoCambio() {
        if (listaMotivoCambio == null) {
            listaMotivoCambio = new ArrayList<>();
        }
        return listaMotivoCambio;
    }

    public void setListaMotivoCambio(List<CodigoBean> listaMotivoCambio) {
        this.listaMotivoCambio = listaMotivoCambio;
    }

    public List<CodigoBean> getListaCambioColegio() {
        if (listaCambioColegio == null) {
            listaCambioColegio = new ArrayList<>();
        }
        return listaCambioColegio;
    }

    public void setListaCambioColegio(List<CodigoBean> listaCambioColegio) {
        this.listaCambioColegio = listaCambioColegio;
    }

//    public List<EstudianteMovilidadBean> getListaEstudianteM() {
//        if (listaEstudianteM == null) {
//            listaEstudianteM = new ArrayList<>();
//        }
//        return listaEstudianteM;
//    }
//
//    public void setListaEstudianteM(List<EstudianteMovilidadBean> listaEstudianteM) {
//        this.listaEstudianteM = listaEstudianteM;
//    }
//
//    public List<EstudianteMovilidadBean> getListaMovilidadE() {
//        if (listaMovilidadE == null) {
//            listaMovilidadE = new ArrayList<>();
//        }
//        return listaMovilidadE;
//    }
//
//    public void setListaMovilidadE(List<EstudianteMovilidadBean> listaMovilidadE) {
//        this.listaMovilidadE = listaMovilidadE;
//    }
    public List<MovilidadBean> getListaPlacaMovilidad() {
        if (listaPlacaMovilidad == null) {
            listaPlacaMovilidad = new ArrayList<>();
        }
        return listaPlacaMovilidad;
    }

    public void setListaPlacaMovilidad(List<MovilidadBean> listaPlacaMovilidad) {
        this.listaPlacaMovilidad = listaPlacaMovilidad;
    }

    public List<MovilidadBean> getListaAutorizacionMovilidad() {
        if (listaAutorizacionMovilidad == null) {
            listaAutorizacionMovilidad = new ArrayList<>();
        }
        return listaAutorizacionMovilidad;
    }

    public void setListaAutorizacionMovilidad(List<MovilidadBean> listaAutorizacionMovilidad) {
        this.listaAutorizacionMovilidad = listaAutorizacionMovilidad;
    }

    public MovilidadBean getMovilidadFiltro() {
        if (movilidadFiltro == null) {
            movilidadFiltro = new MovilidadBean();
        }
        return movilidadFiltro;
    }

    public void setMovilidadFiltro(MovilidadBean movilidadFiltro) {
        this.movilidadFiltro = movilidadFiltro;
    }

//    public EstudianteMovilidadBean getEstudianteMovilidad() {
//        if (estudianteMovilidad == null) {
//            estudianteMovilidad = new EstudianteMovilidadBean();
//        }
//        return estudianteMovilidad;
//    }
//
//    public void setEstudianteMovilidad(EstudianteMovilidadBean estudianteMovilidad) {
//        this.estudianteMovilidad = estudianteMovilidad;
//    }
    public FamiliaBean getFamiliaBean() {
        if (familiaBean == null) {
            familiaBean = new FamiliaBean();
        }
        return familiaBean;
    }

    public void setFamiliaBean(FamiliaBean familiaBean) {
        this.familiaBean = familiaBean;
    }

    public List<FamiliaBean> getListaFamiliaBean() {
        if (listaFamiliaBean == null) {
            listaFamiliaBean = new ArrayList<>();
        }
        return listaFamiliaBean;
    }

    public void setListaFamiliaBean(List<FamiliaBean> listaFamiliaBean) {
        this.listaFamiliaBean = listaFamiliaBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoCambio() {
        if (listaGradoAcademicoCambio == null) {
            listaGradoAcademicoCambio = new ArrayList<>();
        }
        return listaGradoAcademicoCambio;
    }

    public void setListaGradoAcademicoCambio(List<GradoAcademicoBean> listaGradoAcademicoCambio) {
        this.listaGradoAcademicoCambio = listaGradoAcademicoCambio;
    }

    public List<CodigoBean> getListaEstadoCivilFamiliar() {
        if (listaEstadoCivilFamiliar == null) {
            listaEstadoCivilFamiliar = new ArrayList<>();
        }
        return listaEstadoCivilFamiliar;
    }

    public void setListaEstadoCivilFamiliar(List<CodigoBean> listaEstadoCivilFamiliar) {
        this.listaEstadoCivilFamiliar = listaEstadoCivilFamiliar;
    }

    public Map<String, String> getListaFactorSanMap() {
        return listaFactorSanMap;
    }

    public void setListaFactorSanMap(Map<String, String> listaFactorSanMap) {
        this.listaFactorSanMap = listaFactorSanMap;
    }

    public Map<String, String> getListaGrupSanMap() {
        return listaGrupSanMap;
    }

    public void setListaGrupSanMap(Map<String, String> listaGrupSanMap) {
        this.listaGrupSanMap = listaGrupSanMap;
    }

    public List<CodigoBean> getListaStatusFiltroEst() {
        if (listaStatusFiltroEst == null) {
            listaStatusFiltroEst = new ArrayList<>();
        }
        return listaStatusFiltroEst;
    }

    public void setListaStatusFiltroEst(List<CodigoBean> listaStatusFiltroEst) {
        this.listaStatusFiltroEst = listaStatusFiltroEst;
    }

    public EstudianteBean getEstudianteVista() {
        if (estudianteVista == null) {
            estudianteVista = new EstudianteBean();
        }
        return estudianteVista;
    }

    public void setEstudianteVista(EstudianteBean estudianteVista) {
        this.estudianteVista = estudianteVista;
    }

    public List<PaisBean> getListaPaisNac() {
        if (listaPaisNac == null) {
            listaPaisNac = new ArrayList<>();
        }
        return listaPaisNac;
    }

    public void setListaPaisNac(List<PaisBean> listaPaisNac) {
        this.listaPaisNac = listaPaisNac;
    }

    public List<CodigoBean> getListaMovilidad() {
        if (listaMovilidad == null) {
            listaMovilidad = new ArrayList<>();
        }
        return listaMovilidad;
    }

    public void setListaMovilidad(List<CodigoBean> listaMovilidad) {
        this.listaMovilidad = listaMovilidad;
    }

    public List<CodigoBean> getListaParentescoFam1() {
        if (listaParentescoFam1 == null) {
            listaParentescoFam1 = new ArrayList<>();
        }
        return listaParentescoFam1;
    }

    public void setListaParentescoFam1(List<CodigoBean> listaParentescoFam1) {
        this.listaParentescoFam1 = listaParentescoFam1;
    }

    public List<CodigoBean> getListaParentescoFam2() {
        if (listaParentescoFam2 == null) {
            listaParentescoFam2 = new ArrayList<>();
        }
        return listaParentescoFam2;
    }

    public void setListaParentescoFam2(List<CodigoBean> listaParentescoFam2) {
        this.listaParentescoFam2 = listaParentescoFam2;
    }

    public List<ViewEntidadBean> getListaViewEntidadSupBean() {
        if (listaViewEntidadSupBean == null) {
            listaViewEntidadSupBean = new ArrayList<>();
        }
        return listaViewEntidadSupBean;
    }

    public void setListaViewEntidadSupBean(List<ViewEntidadBean> listaViewEntidadSupBean) {
        this.listaViewEntidadSupBean = listaViewEntidadSupBean;
    }

    public List<Integer> getListaPeriodoSeguro() {
        if (listaPeriodoSeguro == null) {
            listaPeriodoSeguro = new ArrayList<>();
        }
        return listaPeriodoSeguro;
    }

    public void setListaPeriodoSeguro(List<Integer> listaPeriodoSeguro) {
        this.listaPeriodoSeguro = listaPeriodoSeguro;
    }

    public List<EstudianteDocumentoBean> getListaEstudianteDocumentoBean() {
        if (listaEstudianteDocumentoBean == null) {
            listaEstudianteDocumentoBean = new ArrayList<>();
        }
        return listaEstudianteDocumentoBean;
    }

    public void setListaEstudianteDocumentoBean(List<EstudianteDocumentoBean> listaEstudianteDocumentoBean) {
        this.listaEstudianteDocumentoBean = listaEstudianteDocumentoBean;
    }

    public UsuarioBean getUsuarioLoginBean() {

        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public FamiliarEstudianteBean getPadreEstudianteBean() {
        if (padreEstudianteBean == null) {
            padreEstudianteBean = new FamiliarEstudianteBean();
        }
        return padreEstudianteBean;
    }

    public void setPadreEstudianteBean(FamiliarEstudianteBean padreEstudianteBean) {
        this.padreEstudianteBean = padreEstudianteBean;
    }

    public FamiliarEstudianteBean getMadreEstudianteBean() {
        if (madreEstudianteBean == null) {
            madreEstudianteBean = new FamiliarEstudianteBean();
        }
        return madreEstudianteBean;
    }

    public void setMadreEstudianteBean(FamiliarEstudianteBean madreEstudianteBean) {
        this.madreEstudianteBean = madreEstudianteBean;
    }

    public String getTipoFam() {
        return tipoFam;
    }

    public void setTipoFam(String tipoFam) {
        this.tipoFam = tipoFam;
    }

    public Boolean getFlgExisteAdmison() {
        return flgExisteAdmison;
    }

    public void setFlgExisteAdmison(Boolean flgExisteAdmison) {
        this.flgExisteAdmison = flgExisteAdmison;
    }

//    public PersonaBean getResponsableBean() {
//        if (responsableBean == null) {
//            responsableBean = new PersonaBean();
//        }
//        return responsableBean;
//    }
//
//    public void setResponsableBean(PersonaBean responsableBean) {
//        this.responsableBean = responsableBean;
//    }
    public Boolean getFlgPaisPeru() {
        return flgPaisPeru;
    }

    public void setFlgPaisPeru(Boolean flgPaisPeru) {
        this.flgPaisPeru = flgPaisPeru;
    }

    public List<FamiliarEstudianteBean> getListaFamiliarEstudianteRespBean() {
        if (listaFamiliarEstudianteRespBean == null) {
            listaFamiliarEstudianteRespBean = new ArrayList<>();
        }
        return listaFamiliarEstudianteRespBean;
    }

    public void setListaFamiliarEstudianteRespBean(List<FamiliarEstudianteBean> listaFamiliarEstudianteRespBean) {
        this.listaFamiliarEstudianteRespBean = listaFamiliarEstudianteRespBean;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public Boolean getBloqueoMatricula() {
        return bloqueoMatricula;
    }

    public void setBloqueoMatricula(Boolean bloqueoMatricula) {
        this.bloqueoMatricula = bloqueoMatricula;
    }

    public EstudianteBloqueoBean getEstudianteBloqueoBean() {
        if (estudianteBloqueoBean == null) {
            estudianteBloqueoBean = new EstudianteBloqueoBean();
        }
        return estudianteBloqueoBean;
    }

    public void setEstudianteBloqueoBean(EstudianteBloqueoBean estudianteBloqueoBean) {
        this.estudianteBloqueoBean = estudianteBloqueoBean;
    }

    public Calendar getFechaBloqueo() {
        return fechaBloqueo;
    }

    public void setFechaBloqueo(Calendar fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    public Boolean getFlgFiltroPersonal() {
        return flgFiltroPersonal;
    }

    public void setFlgFiltroPersonal(Boolean flgFiltroPersonal) {
        this.flgFiltroPersonal = flgFiltroPersonal;
    }

    public Boolean getFlgSoli() {
        return flgSoli;
    }

    public void setFlgSoli(Boolean flgSoli) {
        this.flgSoli = flgSoli;
    }

    public String getIdTipoSol() {
        return idTipoSol;
    }

    public void setIdTipoSol(String idTipoSol) {
        this.idTipoSol = idTipoSol;
    }

    public Boolean getFlgGestorSoli() {
        return flgGestorSoli;
    }

    public void setFlgGestorSoli(Boolean flgGestorSoli) {
        this.flgGestorSoli = flgGestorSoli;
    }

    public List<EstudianteBloqueoBean> getListaEstudianteBloqueoBean() {
        if (listaEstudianteBloqueoBean == null) {
            listaEstudianteBloqueoBean = new ArrayList<>();
        }
        return listaEstudianteBloqueoBean;
    }

    public void setListaEstudianteBloqueoBean(List<EstudianteBloqueoBean> listaEstudianteBloqueoBean) {
        this.listaEstudianteBloqueoBean = listaEstudianteBloqueoBean;
    }

    public List<CodigoBean> getListaStatusBloqueo() {
        if (listaStatusBloqueo == null) {
            listaStatusBloqueo = new ArrayList<>();
        }
        return listaStatusBloqueo;
    }

    public void setListaStatusBloqueo(List<CodigoBean> listaStatusBloqueo) {
        this.listaStatusBloqueo = listaStatusBloqueo;
    }

    public Boolean getFlgTodosAfter() {
        return flgTodosAfter;
    }

    public void setFlgTodosAfter(Boolean flgTodosAfter) {
        this.flgTodosAfter = flgTodosAfter;
    }

    public Boolean getFlgPorNivelGradoAfter() {
        return flgPorNivelGradoAfter;
    }

    public void setFlgPorNivelGradoAfter(Boolean flgPorNivelGradoAfter) {
        this.flgPorNivelGradoAfter = flgPorNivelGradoAfter;
    }

    public Boolean getFlgEstEspAfter() {
        return flgEstEspAfter;
    }

    public void setFlgEstEspAfter(Boolean flgEstEspAfter) {
        this.flgEstEspAfter = flgEstEspAfter;
    }

    public Boolean getFlgTodos() {
        if (flgTodos == null) {
            flgTodos = Boolean.FALSE;
        }
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
    }

    public Boolean getFlgEstEsp() {
        if (flgEstEsp == null) {
            flgEstEsp = Boolean.FALSE;
        }
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getFlgPorNivelGrado() {
        if (flgPorNivelGrado == null) {
            flgPorNivelGrado = Boolean.FALSE;
        }
        return flgPorNivelGrado;
    }

    public void setFlgPorNivelGrado(Boolean flgPorNivelGrado) {
        this.flgPorNivelGrado = flgPorNivelGrado;
    }

    public EstudianteBloqueoBean getBloqueoFiltroBean() {
        if (bloqueoFiltroBean == null) {
            bloqueoFiltroBean = new EstudianteBloqueoBean();
        }
        return bloqueoFiltroBean;
    }

    public void setBloqueoFiltroBean(EstudianteBloqueoBean bloqueoFiltroBean) {
        this.bloqueoFiltroBean = bloqueoFiltroBean;
    }

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
    }

    public EstudianteBloqueoBean getBloqueoFiltroAfterBean() {
        if (bloqueoFiltroAfterBean == null) {
            bloqueoFiltroAfterBean = new EstudianteBloqueoBean();
        }
        return bloqueoFiltroAfterBean;
    }

    public void setBloqueoFiltroAfterBean(EstudianteBloqueoBean bloqueoFiltroAfterBean) {
        this.bloqueoFiltroAfterBean = bloqueoFiltroAfterBean;
    }

    public List<Integer> getListaAnioFiltroMatricula() {
        if (listaAnioFiltroMatricula == null) {
            listaAnioFiltroMatricula = new ArrayList<>();
        }
        return listaAnioFiltroMatricula;
    }

    public void setListaAnioFiltroMatricula(List<Integer> listaAnioFiltroMatricula) {
        this.listaAnioFiltroMatricula = listaAnioFiltroMatricula;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoBean() {
        if (listaNivelAcademicoBean == null) {
            listaNivelAcademicoBean = new ArrayList<>();
        }
        return listaNivelAcademicoBean;
    }

    public void setListaNivelAcademicoBean(List<NivelAcademicoBean> listaNivelAcademicoBean) {
        this.listaNivelAcademicoBean = listaNivelAcademicoBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        if (listaGradoAcademicoFiltroBean == null) {
            listaGradoAcademicoFiltroBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
    }

    public Boolean getFlgPorStatusEst() {
        return flgPorStatusEst;
    }

    public void setFlgPorStatusEst(Boolean flgPorStatusEst) {
        this.flgPorStatusEst = flgPorStatusEst;
    }

    public List<CodigoBean> getListaStatusEstBlo() {
        if (listaStatusEstBlo == null) {
            listaStatusEstBlo = new ArrayList<>();
        }
        return listaStatusEstBlo;
    }

    public void setListaStatusEstBlo(List<CodigoBean> listaStatusEstBlo) {
        this.listaStatusEstBlo = listaStatusEstBlo;
    }

    public List<CodigoBean> getListaStatusFiltroEstBlo() {
        if (listaStatusFiltroEstBlo == null) {
            listaStatusFiltroEstBlo = new ArrayList<>();
        }
        return listaStatusFiltroEstBlo;
    }

    public void setListaStatusFiltroEstBlo(List<CodigoBean> listaStatusFiltroEstBlo) {
        this.listaStatusFiltroEstBlo = listaStatusFiltroEstBlo;
    }

    public EstudianteNacimientoBean getEstudianteNacimientoBean() {
        if (estudianteNacimientoBean == null) {
            estudianteNacimientoBean = new EstudianteNacimientoBean();
        }
        return estudianteNacimientoBean;
    }

    public void setEstudianteNacimientoBean(EstudianteNacimientoBean estudianteNacimientoBean) {
        this.estudianteNacimientoBean = estudianteNacimientoBean;
    }

    public EstudianteVacunaBean getEstudianteVacunaBean() {
        if (estudianteVacunaBean == null) {
            estudianteVacunaBean = new EstudianteVacunaBean();
        }
        return estudianteVacunaBean;
    }

    public void setEstudianteVacunaBean(EstudianteVacunaBean estudianteVacunaBean) {
        this.estudianteVacunaBean = estudianteVacunaBean;
    }

    public List<EstudianteVacunaBean> getListaEstudianteVacunaBean() {
        if (listaEstudianteVacunaBean == null) {
            listaEstudianteVacunaBean = new ArrayList<>();
        }
        return listaEstudianteVacunaBean;
    }

    public void setListaEstudianteVacunaBean(List<EstudianteVacunaBean> listaEstudianteVacunaBean) {
        this.listaEstudianteVacunaBean = listaEstudianteVacunaBean;
    }

    public List<CodigoBean> getListaTipoEdadBean() {
        if (listaTipoEdadBean == null) {
            listaTipoEdadBean = new ArrayList<>();
        }
        return listaTipoEdadBean;
    }

    public void setListaTipoEdadBean(List<CodigoBean> listaTipoEdadBean) {
        this.listaTipoEdadBean = listaTipoEdadBean;
    }

    public List<CodigoBean> getListaTipoVacunaBean() {
        if (listaTipoVacunaBean == null) {
            listaTipoVacunaBean = new ArrayList<>();
        }
        return listaTipoVacunaBean;
    }

    public void setListaTipoVacunaBean(List<CodigoBean> listaTipoVacunaBean) {
        this.listaTipoVacunaBean = listaTipoVacunaBean;
    }

    public List<EstudianteVacunaBean> getListaEstudianteVacunasEstBean() {
        if (listaEstudianteVacunasEstBean == null) {
            listaEstudianteVacunasEstBean = new ArrayList<>();
        }
        return listaEstudianteVacunasEstBean;
    }

    public void setListaEstudianteVacunasEstBean(List<EstudianteVacunaBean> listaEstudianteVacunasEstBean) {
        this.listaEstudianteVacunasEstBean = listaEstudianteVacunasEstBean;
    }

    public List<CodigoBean> getListaTipoPagoSeguroBean() {
        if (listaTipoPagoSeguroBean == null) {
            listaTipoPagoSeguroBean = new ArrayList<>();
        }
        return listaTipoPagoSeguroBean;
    }

    public void setListaTipoPagoSeguroBean(List<CodigoBean> listaTipoPagoSeguroBean) {
        this.listaTipoPagoSeguroBean = listaTipoPagoSeguroBean;
    }

    public List<CodigoBean> getListaRespPago() {
        if (listaRespPago == null) {
            listaRespPago = new ArrayList<>();
        }
        return listaRespPago;
    }

    public void setListaRespPago(List<CodigoBean> listaRespPago) {
        this.listaRespPago = listaRespPago;
    }

    public List<CodigoBean> getListaSeguroBean() {
        if (listaSeguroBean == null) {
            listaSeguroBean = new ArrayList<>();
        }
        return listaSeguroBean;
    }

    public void setListaSeguroBean(List<CodigoBean> listaSeguroBean) {
        this.listaSeguroBean = listaSeguroBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getNumeroCodPer() {
        return numeroCodPer;
    }

    public void setNumeroCodPer(Integer numeroCodPer) {
        this.numeroCodPer = numeroCodPer;
    }

    public Integer getNumeroCodMa() {
        return numeroCodMa;
    }

    public void setNumeroCodMa(Integer numeroCodMa) {
        this.numeroCodMa = numeroCodMa;
    }

    public Integer getNumeroCodFa() {
        return numeroCodFa;
    }

    public void setNumeroCodFa(Integer numeroCodFa) {
        this.numeroCodFa = numeroCodFa;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getFlgCorreo() {
        return flgCorreo;
    }

    public void setFlgCorreo(Boolean flgCorreo) {
        this.flgCorreo = flgCorreo;
    }

    public String getFechaNacIni() {
        return fechaNacIni;
    }

    public void setFechaNacIni(String fechaNacIni) {
        this.fechaNacIni = fechaNacIni;
    }

    public String getFechaNacFin() {
        return fechaNacFin;
    }

    public void setFechaNacFin(String fechaNacFin) {
        this.fechaNacFin = fechaNacFin;
    }

    public Boolean getFlgDniCorrectoPapa() {
        return flgDniCorrectoPapa;
    }

    public void setFlgDniCorrectoPapa(Boolean flgDniCorrectoPapa) {
        this.flgDniCorrectoPapa = flgDniCorrectoPapa;
    }

    public Boolean getFlgDniCorrectoMapa() {
        return flgDniCorrectoMapa;
    }

    public void setFlgDniCorrectoMapa(Boolean flgDniCorrectoMapa) {
        this.flgDniCorrectoMapa = flgDniCorrectoMapa;
    }

    public Boolean getFlgDniCorrectoApo() {
        return flgDniCorrectoApo;
    }

    public void setFlgDniCorrectoApo(Boolean flgDniCorrectoApo) {
        this.flgDniCorrectoApo = flgDniCorrectoApo;
    }

    public Boolean getFlgGrabar() {
        return flgGrabar;
    }

    public void setFlgGrabar(Boolean flgGrabar) {
        this.flgGrabar = flgGrabar;
    }

    public Boolean getFlgCorreoCorrectoPapa() {
        return flgCorreoCorrectoPapa;
    }

    public void setFlgCorreoCorrectoPapa(Boolean flgCorreoCorrectoPapa) {
        this.flgCorreoCorrectoPapa = flgCorreoCorrectoPapa;
    }

    public Boolean getFlgCorreoCorrectoMama() {
        return flgCorreoCorrectoMama;
    }

    public void setFlgCorreoCorrectoMama(Boolean flgCorreoCorrectoMama) {
        this.flgCorreoCorrectoMama = flgCorreoCorrectoMama;
    }

    public Boolean getFlgCorreoCorrectoApo() {
        return flgCorreoCorrectoApo;
    }

    public void setFlgCorreoCorrectoApo(Boolean flgCorreoCorrectoApo) {
        this.flgCorreoCorrectoApo = flgCorreoCorrectoApo;
    }

    public List<MatriculaBean> getListaMatriculaEstudianteMasivoBean() {
        if (listaMatriculaEstudianteMasivoBean == null) {
            listaMatriculaEstudianteMasivoBean = new ArrayList<>();
        }
        return listaMatriculaEstudianteMasivoBean;
    }

    public void setListaMatriculaEstudianteMasivoBean(List<MatriculaBean> listaMatriculaEstudianteMasivoBean) {
        this.listaMatriculaEstudianteMasivoBean = listaMatriculaEstudianteMasivoBean;
    }

    public List<MatriculaBean> getListaMatriculaBean() {
        if (listaMatriculaBean == null) {
            listaMatriculaBean = new ArrayList<>();
        }
        return listaMatriculaBean;
    }

    public void setListaMatriculaBean(List<MatriculaBean> listaMatriculaBean) {
        this.listaMatriculaBean = listaMatriculaBean;
    }

    public Integer getIdTipoSeguro() {
        return idTipoSeguro;
    }

    public void setIdTipoSeguro(Integer idTipoSeguro) {
        this.idTipoSeguro = idTipoSeguro;
    }

    public Boolean getFlgQuieroDatosPadres() {
        return flgQuieroDatosPadres;
    }

    public void setFlgQuieroDatosPadres(Boolean flgQuieroDatosPadres) {
        this.flgQuieroDatosPadres = flgQuieroDatosPadres;
    }

    public Boolean getFlgQuieroDatosMadres() {
        return flgQuieroDatosMadres;
    }

    public void setFlgQuieroDatosMadres(Boolean flgQuieroDatosMadres) {
        this.flgQuieroDatosMadres = flgQuieroDatosMadres;
    }

    public boolean isFlgOtros() {
        return flgOtros;
    }

    public void setFlgOtros(boolean flgOtros) {
        this.flgOtros = flgOtros;
    }

    public Boolean getFlgSuperAdmin() {
        return flgSuperAdmin;
    }

    public void setFlgSuperAdmin(Boolean flgSuperAdmin) {
        this.flgSuperAdmin = flgSuperAdmin;
    }

    public List<CodigoColegioBean> getListaCodigoColeBean() {
        if (listaCodigoColeBean == null) {
            listaCodigoColeBean = new ArrayList<>();
        }
        return listaCodigoColeBean;
    }

    public void setListaCodigoColeBean(List<CodigoColegioBean> listaCodigoColeBean) {
        this.listaCodigoColeBean = listaCodigoColeBean;
    }

    public CodigoColegioBean getCodigoColegioBean() {
        if (codigoColegioBean == null) {
            codigoColegioBean = new CodigoColegioBean();
        }
        return codigoColegioBean;
    }

    public void setCodigoColegioBean(CodigoColegioBean codigoColegioBean) {
        this.codigoColegioBean = codigoColegioBean;
    }

    public Boolean getFlgReingresante() {
        return flgReingresante;
    }

    public void setFlgReingresante(Boolean flgReingresante) {
        this.flgReingresante = flgReingresante;
    }

    public Boolean getFlgMatriculado() {
        return flgMatriculado;
    }

    public void setFlgMatriculado(Boolean flgMatriculado) {
        this.flgMatriculado = flgMatriculado;
    }

}
