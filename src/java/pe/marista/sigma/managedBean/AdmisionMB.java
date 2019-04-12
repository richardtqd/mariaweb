package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteDocumentoBean;
import pe.marista.sigma.bean.EstudianteInfoBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.AdmisionEstudiantesRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AdmisionService;
import pe.marista.sigma.service.BecaService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EstudianteDocumentoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class AdmisionMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of AdmisionMB
     */
    @PostConstruct
    public void AdmisionMB() {
        try {
            //sesión del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            getListaProgramacionBean();
            listaProgramacionBean = programacionService.obtenerPrograAdmisionUniNeg(new ProgramacionBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean(), MaristaConstantes.COD_ADMISION));

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCodigoBean();
            listaCodigoBean = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_POST));
            getListaCodigoSoloInscBean();
            listaCodigoSoloInscBean = codigoService.funcionObtenerPorTipoSoloInsc(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_POST));
            cargarAnio();
//            getPersonaFiltroBean();
            //fecha actual
            fechaActual = new GregorianCalendar();
            getAdmisionBean().setFechaInscripcion(fechaActual.getTime());
            getAdmisionBean().setFechaIngreso(fechaActual.getTime());

            //Año actual
            Calendar miCalendario = Calendar.getInstance();
            getAdmisionFiltroBean().getProgramacionBean().getProcesoBean().setAnio(miCalendario.get(Calendar.YEAR));

            //lista de grupos
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            getListaGrupoAdmisionBean();
            listaGrupoAdmisionBean = admisionService.obtenerListaDistinctAdm(MaristaConstantes.ADM_GRUPO);

            //Validar Super Admin 
            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLoginBean);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.SUPER_ADMIN)) {
                    personalBean = new PersonalBean();
                    personalBean.setFlgSuperAdmin(true);
                    UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
                    getListaUnidadNegocioBean();
                    listaUnidadNegocioBean = unidadNegocioService.obtenerTodos();
                    break;
                } else {
                    personalBean = new PersonalBean();
                    personalBean.setFlgSuperAdmin(false);
                }
            }
            getAdmisionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Varialbes y Propiedades
    private AdmisionBean admisionBean;
    private List<AdmisionBean> listaAdmisionBean;
    private List<PersonaBean> listaPersonaBean;
    private PersonaBean personaBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<ProgramacionBean> listaProgramacionBean;
    private List<CodigoBean> listaCodigoBean;
    private List<CodigoBean> listaCodigoSoloInscBean;
    private EstudianteBean estudianteBean;
    private ProgramacionBean programacionBean;
    private boolean comodin;
    private Boolean valAdmTodos;
    private List<Integer> listaAnios;
    private AdmisionBean admisionFiltroBean;
    private final Integer num = 0;
    private PersonaBean personaFiltroBean;
    private EstudianteBean estudianteFiltroBean;
    private Calendar fechaActual;
    private List<EstudianteBean> listaEstudianteBean;
    private List<AdmisionBean> listaGrupoAdmisionBean;//lista de grupos

    //super admin
    private PersonalBean personalBean;
    private List<PerfilBean> listaPerfilBean;
    private UsuarioBean usuarioLoginBean;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;

    //Postulante existe en Admision
    private Boolean flgExisteAdmison;

    //documento admisión - estudiante
    private EstudianteDocumentoBean estudianteDocumentoBean;
    private List<EstudianteDocumentoBean> listaEstudianteDocumentoBean;
    private Boolean flgEmail = false;
    private Boolean flgAll = false;

    public void cargarFormulario() {
        String parametro = (String) new MaristaUtils().requestObtenerObjeto("caniari");
        String parametro2 = (String) new MaristaUtils().requestObtenerObjeto("caniari2");
        try {
            if (parametro != null) {
                getAdmisionFiltroBean().getEstudianteBean().getPersonaBean().setIdPersona(parametro);
                getAdmisionFiltroBean().getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(parametro2);
                getAdmisionFiltroBean().getProgramacionBean().getProcesoBean().setAnio(null);

                AdmisionService admisionService = BeanFactory.getAdmisionService();
                //En la busqueda, se ordena por periodo
                listaAdmisionBean = admisionService.obtenerAdmisionFiltro(admisionFiltroBean);
                if (!listaAdmisionBean.isEmpty()) {
                    setAdmisionBean(listaAdmisionBean.get(0));
                    EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
                    listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona(), admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg(), admisionBean.getProgramacionBean().getProcesoBean().getAnio());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

//        if (parametro != null) {
//            EstudianteBean bean = new EstudianteBean();
//            String idPersona = (String) parametro;
//            String uniNeg = (String) parametro2;
//            bean.getPersonaBean().setIdPersona(idPersona);
//            bean.getPersonaBean().getUnidadNegocioBean().setUniNeg(uniNeg);
//            obtenerEstudiantePorId(bean);
//        }
    }

    public void obtenerEstudiantePorId(Object estudiante) {
        try {
            estudianteBean = (EstudianteBean) estudiante;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
             estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            getAdmisionBean().setEstudianteBean(estudianteBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerAdmisionPorPostu(Object admision) {
//        try {
//            admisionBean = (AdmisionBean) admision;
//            AdmisionService admisionService = BeanFactory.getAdmisionService();
//            admisionBean = admisionService.obtenerAdmisionPorPostu(admisionBean);
//            EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
//            listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
//    public void obtenerPorFiltro() {
//        try {
//            int estado = 0;
//            PersonaService personaService = BeanFactory.getPersonaService();
//            if (personaFiltroBean.getIdPersona() != null && !personaFiltroBean.getIdPersona().equals("")) {
//                personaFiltroBean.setIdPersona(personaFiltroBean.getIdPersona().trim());
//                estado = 1;
//            }
//            if (personaFiltroBean.getApepat() != null && !personaFiltroBean.getApepat().equals("")) {
//                personaFiltroBean.setApepat(personaFiltroBean.getApepat().trim());
//                estado = 1;
//            }
//            if (personaFiltroBean.getApemat() != null && !personaFiltroBean.getApemat().equals("")) {
//                personaFiltroBean.setApemat(personaFiltroBean.getApemat().trim());
//                estado = 1;
//            }
//            if (personaFiltroBean.getNombre() != null && !personaFiltroBean.getNombre().equals("")) {
//                personaFiltroBean.setNombre(personaFiltroBean.getNombre().trim());
//                estado = 1;
//            }
//            if (personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
//                personaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
//                estado = 1;
//            } else if (estado == 0) {
//                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
//            }
//            if (estado == 1) {
//                listaPersonaBean = personaService.obtenerPersonaPorFiltro(personaFiltroBean);
//                if (listaPersonaBean.isEmpty()) {
//                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                }
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//
//    public void obtenerPorFiltroEst() {
//        try {
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            estudianteFiltroBean.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
////            PersonaService personaService = BeanFactory.getPersonaService();
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            if (estudianteFiltroBean.getPersonaBean().getIdPersona() != null && !personaFiltroBean.getIdPersona().equals("")) {
//                estudianteFiltroBean.getPersonaBean().setIdPersona(estudianteFiltroBean.getPersonaBean().getIdPersona().trim());
//            }
//            if (estudianteFiltroBean.getPersonaBean().getApepat() != null && !estudianteFiltroBean.getPersonaBean().equals("")) {
//                estudianteFiltroBean.getPersonaBean().setApepat(estudianteFiltroBean.getPersonaBean().getApepat().trim());
//            }
//            if (estudianteFiltroBean.getPersonaBean().getApemat() != null && !estudianteFiltroBean.getPersonaBean().getApemat().equals("")) {
//                estudianteFiltroBean.getPersonaBean().setApemat(estudianteFiltroBean.getPersonaBean().getApemat().trim());
//            }
//            if (estudianteFiltroBean.getPersonaBean().getNombre() != null && !estudianteFiltroBean.getPersonaBean().getNombre().equals("")) {
//                estudianteFiltroBean.getPersonaBean().setNombre(estudianteFiltroBean.getPersonaBean().getNombre().trim());
//            }
//            if (estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
//                estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().setIdGradoAcademico(estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
//            }
////            listaPersonaBean = personaService.obtenerPersonaPorFiltro(personaFiltroBean);
//            listaEstudianteBean = estudianteService.obtenerEstudiante();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void limpiarPersonaFiltro() {
        try {
            listaPersonaBean = new ArrayList<>();
            personaFiltroBean = new PersonaBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int inicio = MaristaConstantes.ANO_INI_DEFAULT_COLE;
            int fin = miCalendario.get(Calendar.YEAR) + 5;
            listaAnios = new ArrayList<>();
            for (int i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarAdmision() {
        admisionBean = new AdmisionBean();
        listaEstudianteDocumentoBean = new ArrayList<>();
        listaAdmisionBean = new ArrayList<>();
        admisionFiltroBean = new AdmisionBean();
        getAdmisionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        Calendar miCalendario = Calendar.getInstance();
        getAdmisionFiltroBean().getProgramacionBean().getProcesoBean().setAnio(miCalendario.get(Calendar.YEAR));
//        programacionBean = new ProgramacionBean();
//        estudianteBean = new EstudianteBean();
//        comodin = false;
    }

    public void limpiarAprobacionAdmision() {
        admisionBean = new AdmisionBean();
        listaAdmisionBean = new ArrayList<>();
        admisionFiltroBean = new AdmisionBean();
        getAdmisionFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        Calendar miCalendario = Calendar.getInstance();
        getAdmisionFiltroBean().getProgramacionBean().getProcesoBean().setAnio(miCalendario.get(Calendar.YEAR));
        fechaActual = new GregorianCalendar();
        getAdmisionBean().setFechaIngreso(fechaActual.getTime());

    }

    public void imprimirBecados() {
        ServletOutputStream out = null;
        try {
            
             AdmisionService admisionService = BeanFactory.getAdmisionService();
             HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteAdmiPorA�o.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");

            File file = new File(archivoJasper);
            List<AdmisionEstudiantesRepBean> listaDetSolicitudLogBean = new ArrayList<>();
            listaDetSolicitudLogBean = admisionService.obtenerAlumnosPorAnio(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), admisionFiltroBean.getProgramacionBean().getProcesoBean().getAnio());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetSolicitudLogBean);
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
    
    public void limpiarPostulanteAdmision() {
        admisionBean = new AdmisionBean();
        listaEstudianteDocumentoBean = new ArrayList<>();
        fechaActual = new GregorianCalendar();
        getAdmisionBean().setFechaInscripcion(fechaActual.getTime());
        comodin = false;
    }

    public void obtenerAdmision() {
        try {
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            listaAdmisionBean = admisionService.obtenerAdmision();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAdmisionPorUniNeg() {
        try {
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            listaAdmisionBean = admisionService.obtenerAdmisionPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public String insertarAdmision() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                AdmisionService admisionService = BeanFactory.getAdmisionService(); 
//                admisionBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                admisionBean.setProgramacionBean(programacionBean); 
//                admisionBean.setEstudianteBean(estudianteBean);
//                admisionBean = admisionService.obtenerAdmisionPorIdPeriodo(admisionBean);
//                if (admisionBean == null) {
////                    admisionBean.setEstudianteBean(estudianteBean);
////                    admisionBean.setProgramacionBean(programacionBean);
////                    admisionBean.getGradoAcademicoBean().setIdGradoAcademico(admisionBean.getGradoAcademicoBean().getIdGradoAcademico()); 
//                    admisionBean.setCreaPor(usuarioLoginBean.getUsuario());
//                    admisionService.insertarAdmision(admisionBean);
//                    listaAdmisionBean = admisionService.obtenerAdmisionPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
////                    EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
////                    listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(estudianteDocumentoBean);
//                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
////                   limpiarAdmision(); 
//                } else {
//                    limpiarAdmision();
//                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeAdmiYaReg", null));
//                }
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }
    public String insertarAdmision() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                AdmisionService admisionService = BeanFactory.getAdmisionService();
//                AdmisionBean admision = new AdmisionBean();
//                admisionBean.getProgramacionBean().getProcesoBean().setAnio(programacionBean.getAnio());
//                admisionBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//                admision = admisionService.obtenerAdmisionPorIdPeriodo(admisionBean);
//                if (admision == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
//                    admisionBean.getProgramacionBean().getProcesoBean().setAnio(programacionBean.getProcesoBean().getAnio());
                admisionBean.setProgramacionBean(programacionBean);
                admisionBean.setCreaPor(usuarioLoginBean.getUsuario());
                admisionBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                admisionBean.setFlgEmail(flgEmail);
                admisionService.insertarAdmision(admisionBean);
                listaAdmisionBean = admisionService.obtenerAdmisionPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
                listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona(), admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg(), admisionBean.getProgramacionBean().getProcesoBean().getAnio());
                listaAdmisionBean = admisionService.obtenerAdmisionFiltro(admisionFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                flgExisteAdmison = true;
//                    limpiarPostulanteAdmision();
//                }
            } else {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeAdmiYaReg", null));
                RequestContext.getCurrentInstance().addCallbackParam("errorAdm", true);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String validarAdmisionPorPerido() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
                AdmisionBean admision = new AdmisionBean();
                admisionBean.getProgramacionBean().getProcesoBean().setAnio(programacionBean.getProcesoBean().getAnio());
                admisionBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                admision = admisionService.obtenerAdmisionPorIdPeriodo(admisionBean);
                if (admision == null) {
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
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
//    public String llamarGenerarDocPorAdmision() {
//        String pagina = null;
//        try {
//            pagina = new MaristaUtils().validaUsuarioSesion();
//            if (pagina == null) {
//                AdmisionService admisionService = BeanFactory.getAdmisionService();
////                admisionBean.setEstudianteBean(estudianteBean);
//                admisionBean.setProgramacionBean(programacionBean);
//                admisionBean.getGradoAcademicoBean().setIdGradoAcademico(admisionBean.getGradoAcademicoBean().getIdGradoAcademico());
//                admisionBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                admisionService.llamarGenerarDocPorAdmision(admisionBean);
//                EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
//                listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(estudianteDocumentoBean);
//                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//        return pagina;
//    }

    public String modificarAdmision() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
//                admisionBean.setAnio(programacionBean.getProcesoBean().getAnio());
                admisionBean.setModiPor(usuarioLoginBean.getUsuario());
                admisionBean.setFlgEmail(flgEmail);
                admisionService.modificarAdmision(admisionBean);
                listaAdmisionBean = admisionService.obtenerAdmisionFiltro(admisionFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                limpiarAdmision();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarAdmision() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
                admisionService.eliminarAdmision(admisionBean);
//                listaAdmisionBean = admisionService.obtenerAdmision();
//                listaAdmisionBean = admisionService.obtenerAdmisionPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarAdmision() {
        if (admisionBean.getIdAdmision() != null) {
            modificarAdmision();
        } else {
            insertarAdmision();
        }
    }

    public void ponerEmail() {
        try {
            System.out.println(">>>> flg" + flgEmail);
            System.out.println(">>>> bean" + admisionBean.getFlgEmail());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            admisionBean = (AdmisionBean) event.getObject();
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            admisionBean = admisionService.obtenerAdmisionPorPostu(admisionBean);
//            getProgramacionBean();
//            programacionBean=admisionBean.getProgramacionBean();
//            ProgramacionService programacionService = BeanFactory.getProgramacionService();
//            admisionBean = programacionService.obtenerPrograPorId(admisionBean.getProgramacionBean());
//            PersonaService personaService = BeanFactory.getPersonaService();
//            personaBean = personaService.obtenerPersPorId(personaBean.getIdPersona());
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
            listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona(), admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg(), admisionBean.getProgramacionBean().getProcesoBean().getAnio());
            comodin = true;
            if (listaEstudianteDocumentoBean.isEmpty()) {
                listaEstudianteDocumentoBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerAdmision(Object persona) {
        try {
            admisionBean = (AdmisionBean) persona;
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            admisionBean = admisionService.obtenerAdmisionPorId(admisionBean);
            admisionBean.setEstudianteBean(estudianteBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<EstudianteBean> completeEstudiante(String query) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<EstudianteBean> listaEstudianteTodosBean = estudianteService.obtenerEstudianteAminPorUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<EstudianteBean> listaEstudianteFiltroBean = new ArrayList<>();
            for (int i = 0; i < listaEstudianteTodosBean.size(); i++) {
                EstudianteBean bean = listaEstudianteTodosBean.get(i);
                if (bean.getPersonaBean().getNombreCompleto().toLowerCase().contains(query.toLowerCase())) {
                    bean.setIdEstudiante(bean.getPersonaBean().getIdPersona());
                    listaEstudianteFiltroBean.add(bean);
                }
            }
            return listaEstudianteFiltroBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return null;
    }

    //Programacion
    public void rowSelectProgramacion(SelectEvent event) {
        try {
            programacionBean = (ProgramacionBean) event.getObject();
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            programacionBean = programacionService.obtenerPrograPorId(programacionBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Rowselect Persona
    public void rowSelectPersona(SelectEvent event) {
        try {
            personaBean = (PersonaBean) event.getObject();
            PersonaService personaService = BeanFactory.getPersonaService();
            personaBean = personaService.obtenerPersPorId(personaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectEstudiante(SelectEvent event) {
        try {
            estudianteBean = (EstudianteBean) event.getObject();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarComodin() {
        comodin = false;
    }

    public void cambiarFlgAll(Boolean flg) {
        System.out.println("flg..." + flg);
        this.flgAll = flg;
        System.out.println("flgAll..." + flgAll);

    }

    public void obtenerPeriodoProceso() {
        try {
            for (int i = 0; i < listaProgramacionBean.size(); i++) {
                if (admisionFiltroBean.getProgramacionBean().getIdProgramacion() != null
                        && listaProgramacionBean.get(i).getIdProgramacion().toString().equals(admisionFiltroBean.getProgramacionBean().getIdProgramacion().toString())) {
                    admisionFiltroBean.getProgramacionBean().getProcesoBean().setAnio(listaProgramacionBean.get(i).getAnio());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Aprobacion de Postulantes //Aprobacion de Postulantes //Aprobacion de Postulantes //Aprobacion de Postulantes //Aprobacion de Postulantes //Aprobacion de Postulantes //Aprobacion de Postulantes
    //filtro para aprobar a los postulantes
    public void obtenerAdmisionFiltro() {
        try {
            int estado = 1;
            AdmisionService admisionService = BeanFactory.getAdmisionService();
            if (admisionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !admisionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals(0)) {
                admisionFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(admisionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().trim());
                estado = 1;
            }
            if (admisionFiltroBean.getAnio() != null && !admisionFiltroBean.getAnio().equals(0)) {
                admisionFiltroBean.setAnio(admisionFiltroBean.getAnio());
            }
            if (admisionFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !admisionFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                admisionFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(admisionFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            }
            if (admisionFiltroBean.getProgramacionBean().getIdProgramacion() != null && !admisionFiltroBean.getProgramacionBean().getIdProgramacion().equals(0)) {
                admisionFiltroBean.getProgramacionBean().setIdProgramacion(admisionFiltroBean.getProgramacionBean().getIdProgramacion());
                estado = 1;
            }
            if (admisionFiltroBean.getCodigoBean().getIdCodigo() != null && !admisionFiltroBean.getCodigoBean().getIdCodigo().equals(0)) {
                admisionFiltroBean.getCodigoBean().setIdCodigo(admisionFiltroBean.getCodigoBean().getIdCodigo());
                estado = 1;
            }
            if (admisionFiltroBean.getDocRefeIngreso() != null && !admisionFiltroBean.getDocRefeIngreso().equals("")) {
                admisionFiltroBean.setDocRefeIngreso(admisionFiltroBean.getDocRefeIngreso());
                estado = 1;
            }
            if (admisionFiltroBean.getEstudianteBean().getPersonaBean().getNombreCompleto() != null && !admisionFiltroBean.getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")) {
                admisionFiltroBean.getEstudianteBean().getPersonaBean().setNombreCompleto(admisionFiltroBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
                estado = 1;
            }
            if (admisionFiltroBean.getGrupo() != null && !admisionFiltroBean.getGrupo().equals("")) {
                admisionFiltroBean.setGrupo(admisionFiltroBean.getGrupo());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listaAdmisionBean = admisionService.obtenerAdmisionFiltro(admisionFiltroBean);
                if (!listaAdmisionBean.isEmpty()) {
                    DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                    for (AdmisionBean lista : listaAdmisionBean) {
                        lista.setEstadoAprobacion(Boolean.FALSE);
                        System.out.println("flg apro..." + lista.getEstadoAprobacion());
                        DocIngresoBean doc = new DocIngresoBean();
                        doc = docIngresoService.obtenerDocIngresoCuotaPorDiscente(lista.getEstudianteBean().getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.NOM_CON_CUOTA_ING);
                        if (doc != null) {
                            if (doc.getIdDocIngreso() != null && !doc.getIdDocIngreso().equals(0)) {
                                lista.setPagoCuotaIng(MaristaConstantes.SI);
                            } else {
                                lista.setPagoCuotaIng(MaristaConstantes.NO);
                            }
                        } else {
                            lista.setPagoCuotaIng(MaristaConstantes.NO);
                        }

                    }
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String aprobarPostulante() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                AdmisionService admisionService = BeanFactory.getAdmisionService();
                EstudianteBean estudiante = new EstudianteBean();
                estudiante.setFechaIngreso(admisionBean.getFechaIngreso());
//                if (admisionBean.getEstadoAprobacion() == true) {
                System.out.println("flg..." + this.flgAll);
                for(AdmisionBean admi : listaAdmisionBean){
                    if (admi.getEstadoAprobacion() == true) {
                    admisionService.aprobarPostulante(listaAdmisionBean, admisionBean, estudiante, usuarioLoginBean, this.flgAll);
                    }
                }
                 
//                listaAdmisionBean = admisionService.obtenerAdmision();
//                listaAdmisionBean = admisionService.obtenerAdmisionPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String darCheckDocumentoAdmision() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteDocumentoService estudianteDocumentoService = BeanFactory.getEstudianteDocumentoService();
                estudianteDocumentoService.darCheckDocumentoAdmision(listaEstudianteDocumentoBean, estudianteDocumentoBean, admisionBean);
                CodigoBean cb = new CodigoBean(admisionBean.getCodigoBean().getCodigo());
                cb.setTipoCodigoBean(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_POST));
                CodigoService codigoService = BeanFactory.getCodigoService();
                cb = codigoService.obtenerPorCodigo(cb);
                admisionBean.setCodigoBean(cb);
                listaEstudianteDocumentoBean = estudianteDocumentoService.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona(), admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg(), admisionBean.getProgramacionBean().getProcesoBean().getAnio());
                AdmisionService admisionService = BeanFactory.getAdmisionService();
                listaAdmisionBean = admisionService.obtenerAdmisionFiltro(admisionFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void cambiarValAdmTodos() {
        try {
            if (valAdmTodos) {
                for (AdmisionBean admision : listaAdmisionBean) {
                    admision.setEstadoAprobacion(true);
                    CodigoBean codigoBean = new CodigoBean();
                    codigoBean.setCodigo(MaristaConstantes.COD_ADMITIDO);
                    admision.setCodigoBean(codigoBean);
                }
            } else {
                for (AdmisionBean admision : listaAdmisionBean) {
                    admision.setEstadoAprobacion(false);
                    CodigoBean codigoBean = new CodigoBean();
                    codigoBean.setCodigo(MaristaConstantes.COD_NO_ADMITIDO);
                    admision.setCodigoBean(codigoBean);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Metodos Getter y Setter
    public AdmisionBean getAdmisionBean() {
        if (admisionBean == null) {
            admisionBean = new AdmisionBean();
        }
        return admisionBean;
    }

    public void setAdmisionBean(AdmisionBean admisionBean) {
        this.admisionBean = admisionBean;
    }

    public List<AdmisionBean> getListaAdmisionBean() {
        if (listaAdmisionBean == null) {
            listaAdmisionBean = new ArrayList<>();
        }
        return listaAdmisionBean;
    }

    public void setListaAdmisionBean(List<AdmisionBean> listaAdmisionBean) {
        this.listaAdmisionBean = listaAdmisionBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<ProgramacionBean> getListaProgramacionBean() {
        if (listaProgramacionBean == null) {
            listaProgramacionBean = new ArrayList<>();
        }
        return listaProgramacionBean;
    }

    public void setListaProgramacionBean(List<ProgramacionBean> listaProgramacionBean) {
        this.listaProgramacionBean = listaProgramacionBean;
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

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public boolean getComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public List<Integer> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public AdmisionBean getAdmisionFiltroBean() {
        if (admisionFiltroBean == null) {
            admisionFiltroBean = new AdmisionBean();
        }
        return admisionFiltroBean;
    }

    public void setAdmisionFiltroBean(AdmisionBean admisionFiltroBean) {
        this.admisionFiltroBean = admisionFiltroBean;
    }

    public Integer getNum() {
        return num;
    }

    public List<PersonaBean> getListaPersonaBean() {
        return listaPersonaBean;
    }

    public void setListaPersonaBean(List<PersonaBean> listaPersonaBean) {
        this.listaPersonaBean = listaPersonaBean;
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

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
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

    public EstudianteDocumentoBean getEstudianteDocumentoBean() {
        if (estudianteDocumentoBean == null) {
            estudianteDocumentoBean = new EstudianteDocumentoBean();
        }
        return estudianteDocumentoBean;
    }

    public void setEstudianteDocumentoBean(EstudianteDocumentoBean estudianteDocumentoBean) {
        this.estudianteDocumentoBean = estudianteDocumentoBean;
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

    public List<UnidadNegocioBean> getListaUnidadNegocioBean() {
        if (listaUnidadNegocioBean == null) {
            listaUnidadNegocioBean = new ArrayList<>();
        }
        return listaUnidadNegocioBean;
    }

    public void setListaUnidadNegocioBean(List<UnidadNegocioBean> listaUnidadNegocioBean) {
        this.listaUnidadNegocioBean = listaUnidadNegocioBean;
    }

    public List<AdmisionBean> getListaGrupoAdmisionBean() {
        if (listaGrupoAdmisionBean == null) {
            listaGrupoAdmisionBean = new ArrayList<>();
        }
        return listaGrupoAdmisionBean;
    }

    public void setListaGrupoAdmisionBean(List<AdmisionBean> listaGrupoAdmisionBean) {
        this.listaGrupoAdmisionBean = listaGrupoAdmisionBean;
    }

    public Boolean getFlgExisteAdmison() {
        return flgExisteAdmison;
    }

    public void setFlgExisteAdmison(Boolean flgExisteAdmison) {
        this.flgExisteAdmison = flgExisteAdmison;
    }

    public Boolean getFlgEmail() {
        return flgEmail;
    }

    public void setFlgEmail(Boolean flgEmail) {
        this.flgEmail = flgEmail;
    }

    public List<CodigoBean> getListaCodigoSoloInscBean() {
        if (listaCodigoSoloInscBean == null) {
            listaCodigoSoloInscBean = new ArrayList<>();
        }
        return listaCodigoSoloInscBean;
    }

    public void setListaCodigoSoloInscBean(List<CodigoBean> listaCodigoSoloInscBean) {
        this.listaCodigoSoloInscBean = listaCodigoSoloInscBean;
    }

    public Boolean getFlgAll() {
        return flgAll;
    }

    public void setFlgAll(Boolean flgAll) {
        this.flgAll = flgAll;
    }

}
