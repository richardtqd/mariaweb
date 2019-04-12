package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.DeclaracionJuradaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaNivelRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccionRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public final class MatriculaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of MatriculaMB
     */
    @PostConstruct
    public void MatriculaMB() {
        try {

            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                this.flgChamps = true;
            } else {
                this.flgChamps = false;
            }
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();

            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademico();
            listaNivelAcademico = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            //filtros
            getMatriculaFiltroBean();
            //inicializo el año  
            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnioIni(miCalendario.get(Calendar.YEAR));
//            getMatriculaFiltroBean().setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getViewMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR));

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCodigoBean();
            listaCodigoBean = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MATRICULA));

            getListaAnioMatricula();
            for (int i = MaristaConstantes.ANO_INI_DEFAULT_COLE; i <= miCalendario.get(Calendar.YEAR) + 3; i++) {
                listaAnioMatricula.add(i);
            }

            getListaAnioFiltroMatricula();
            for (int i = miCalendario.get(Calendar.YEAR) - 1; i <= miCalendario.get(Calendar.YEAR) + 10; i++) {
                listaAnioFiltroMatricula.add(i);
            }

            //fecha actual
            fechaActual = new GregorianCalendar();
            getMatriculaBean().setFechaMatricula(fechaActual.getTime());

            //es super admin? 
            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLogin);
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
            actualizarAnioFinFiltroPorProgramacion();
            this.fechaMatricula = new GregorianCalendar();
            getMatriculaFiltroBean().setFechaMatricula(fechaMatricula.getTime());
            getListaParentescoFam1();
            listaParentescoFam1 = codigoService.obtenerParentescoConTodo();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean; //lista de estudiantes matriculados en el historico 
    private List<MatriculaBean> listaMatriculaFlgFlaseBean; // lista de los postulantes o estudiantes no matriculados
    private List<MatriculaBean> listaMatriculaFlgTrueBean;
    private List<MatriculaBean> listaMatriculaFiltroBean;

    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<ProgramacionBean> listaProgramacionBean;
    private List<CodigoBean> listaCodigoBean;
    private MatriculaBean matriculaBean;
    private ProgramacionBean programacionBean;
    private EstudianteBean estudianteBean;
    private boolean comodin;
    private List<TipoConceptoBean> listaTipoConceptoBean;

    //Estudiante filtro
//    private EstudianteBean estudianteFiltroBean;
//    private List<EstudianteBean> listaEstudiantesMasivosBean;
    private MatriculaBean matriculaFiltroBean;
    private List<Integer> listaAnioMatricula;
    private List<Integer> listaAnioFiltroMatricula;

    //ayuda
    private Calendar fechaActual;
    private Date fechaHoy;
    private String alerta;
    private String texto;

    //Super admin
    private PersonalBean personalBean;
    private List<PerfilBean> listaPerfilBean;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private Boolean flgHabiMatricula;
    private UsuarioBean usuarioLogin;

    //filtros
    private List<NivelAcademicoBean> listaNivelAcademico;
    private Boolean flgTodos = false;
    private Boolean flgEstSinPro;
    private Boolean flgPorNivelGrado;
    private Boolean flgEstEsp;
    private Boolean valAdmTodos = true;
    private Boolean flgTodosMatriculados;
    private Boolean flgEstEspMatricula;
    private Boolean flgChamps = false;
    private List<FamiliarEstudianteBean> listaFamiliarEstudianteRespBean;

    //estadisticas
    private List<ViewMatriculaBean> listaViewMatriculaBean;
    private ViewMatriculaBean viewMatriculaBean;

    //cr
    private List<CentroResponsabilidadBean> listaCrInicialBean;
    private List<CentroResponsabilidadBean> listaCrPrimariaBean;
    private List<CentroResponsabilidadBean> listaCrSecundariaBean;
    private List<CentroResponsabilidadBean> listaCrBachillerBean;
    private Integer crIni;
    private Integer crPri;
    private Integer crSec;
    private Integer crBac;
    private Boolean bloqueoMatricula = Boolean.FALSE;
    private Boolean flgUniSanLui = false;

    private Calendar fechaMatricula;

    private GradoAcademicoBean gradoAyudaCambio;
    private Boolean flgMostrarLista = false;
    private List<CodigoBean> listaParentescoFam1;
    private Boolean flgMatriculado = false;

    public void actualizarAnioFinFiltroPorProgramacion() { //obtener el anio siguiente
        try {

            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            getListaProgramacionBean();
//            System.out.println(MaristaConstantes.COD_MATRICULA);
//            listaProgramacionBean = programacionService.obtenerPrograPorTipo(MaristaConstantes.COD_MATRICULA);
            listaProgramacionBean = programacionService.obtenerProgPorTipoPorAnioPorUniNeg(MaristaConstantes.TIP_COD_PROC, MaristaConstantes.COD_MATRICULA, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnioIni());
            if (listaProgramacionBean.isEmpty()) {
                listaProgramacionBean = new ArrayList<>();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                TipoConceptoBean tipoConceptoBean = new TipoConceptoBean();
                tipoConceptoBean.setIdTipoConcepto(100);
                listaTipoConceptoBean = tipoConceptoService.obtenerListaTipoConceptoPorId(tipoConceptoBean.getIdTipoConcepto());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarCRs() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCrInicialBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_INI);
            listaCrPrimariaBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_PRI);
            listaCrSecundariaBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_SEC);
            listaCrBachillerBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(MaristaConstantes.COD_CR_BACH);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodosMatricula() {
        try {
            if (this.flgTodosMatriculados == true) {
                this.flgEstEspMatricula = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.setGradoAcademicoBean(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEspMatricula() {
        try {
            if (this.flgEstEspMatricula == true) {
                this.flgTodosMatriculados = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.setGradoAcademicoBean(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstSinPro() {
        try {
            if (this.flgEstSinPro == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                TipoConceptoBean tipoConceptoBean = new TipoConceptoBean();
                tipoConceptoBean.setIdTipoConcepto(100);
                listaTipoConceptoBean = tipoConceptoService.obtenerListaTipoConceptoPorId(tipoConceptoBean.getIdTipoConcepto());
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
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEsp() {
        try {
            if (this.flgEstEsp == true) {
                this.flgTodos = false;
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Metodos de aplicacion
    public void obtenerFiltroNoMatriculados() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (matriculaFiltroBean.getAnio() != null && !matriculaFiltroBean.getAnio().equals(0)) {
                matriculaFiltroBean.setAnio(matriculaFiltroBean.getAnio());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setIdEstudiante(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listaMatriculaFlgFlaseBean = matriculaService.obtenerFiltroNoMatriculados(matriculaFiltroBean);
                if (listaMatriculaFlgFlaseBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaMatriculaFlgFlaseBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Metodos de aplicacion
    public void obtenerFiltroMatriculadosPorUsuario() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (matriculaFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(matriculaFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                matriculaFiltroBean.setFechaInicio(t);
            }
            if (matriculaFiltroBean.getAnio() != null && !matriculaFiltroBean.getAnio().equals(0)) {
                matriculaFiltroBean.setAnio(matriculaFiltroBean.getAnio());
            }
            if (matriculaFiltroBean.getCreaPor() != null && !matriculaFiltroBean.getCreaPor().equals("")) {
                matriculaFiltroBean.setCreaPor(matriculaFiltroBean.getCreaPor().toUpperCase().trim());
                estado = 1;
            }
            listaMatriculaFiltroBean = matriculaService.obtenerFiltroMatriculadosPorUsuario(matriculaFiltroBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroMatriculados() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (matriculaFiltroBean.getAnio() != null && !matriculaFiltroBean.getAnio().equals(0)) {
                matriculaFiltroBean.setAnio(matriculaFiltroBean.getAnio());
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().setIdEstudiante(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listaMatriculaFlgTrueBean = matriculaService.obtenerFiltroMatriculados(matriculaFiltroBean);
                if (listaMatriculaFlgTrueBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaMatriculaFlgTrueBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
            } else {
//                Calendar miCalendario = Calendar.getInstance();
//                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
                matriculaFiltroBean.setAnioFin(matriculaFiltroBean.getAnioIni() + 1);
                estado = 1;
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().setIdGradoAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getSeccion() != null && !matriculaFiltroBean.getEstudianteBean().getSeccion().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().setSeccion(matriculaFiltroBean.getEstudianteBean().getSeccion());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteMasivoSeccion() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            matriculaFiltroBean.setAnio(matriculaFiltroBean.getAnio());
            //todos va a significar en este caso los ingresantes que no tienen seccion 
            if (flgTodos == true) {
                System.out.println("entro todos");
                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoSeccion(matriculaFiltroBean);
                listaMatriculaEstudiantesMasivosBean.equals(true);
            } else {
                if (matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getSeccion() != null && !matriculaFiltroBean.getSeccion().equals("")) {
                    matriculaFiltroBean.setSeccion(matriculaFiltroBean.getSeccion());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoSeccion(matriculaFiltroBean);
                    listaMatriculaEstudiantesMasivosBean.equals(true);
                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }
                }
                if (estado == 1) {
                    System.out.println("entro por grados");
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoSeccion(matriculaFiltroBean);
                    listaMatriculaEstudiantesMasivosBean.equals(true);
                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String modificarEstudiante() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                for (MatriculaBean matriculaLista : listaMatriculaEstudiantesMasivosBean) {
                    if (matriculaLista.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaLista.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        EstudianteService estudianteService = BeanFactory.getEstudianteService();

                        MatriculaBean matricula = new MatriculaBean();
                        // modificando seccion en la matricula
                        matricula.setSeccion(matriculaLista.getSeccion());
                        matricula.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        matricula.getEstudianteBean().getPersonaBean().setIdPersona(matriculaLista.getEstudianteBean().getPersonaBean().getIdPersona());
                        matricula.setAnio(matriculaFiltroBean.getAnio());
                        matriculaService.modificarMatriculaSeccion(matricula);
                        //Modificando seccion en estudiante
                        estudianteService.modificarEstudianteSeccion(matricula.getEstudianteBean().getPersonaBean().getIdPersona(), matricula.getSeccion());
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerFiltroEstudianteMasivoLista() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoLista(matriculaFiltroBean);
                for (int i = 0; i < listaMatriculaEstudiantesMasivosBean.size(); i++) {
                    ponerEstudianteEnMatricula(listaMatriculaEstudiantesMasivosBean.get(i));
                    listaMatriculaEstudiantesMasivosBean.get(i).setGradoAcademicoBean(matriculaFiltroBean.getGradoAcademicoBean());
                }
            } else {
//                Calendar miCalendario = Calendar.getInstance();
//                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
                matriculaFiltroBean.setAnioFin(matriculaFiltroBean.getAnioIni() + 1);
                estado = 1;
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().setIdGradoAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getSeccion() != null && !matriculaFiltroBean.getEstudianteBean().getSeccion().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().setSeccion(matriculaFiltroBean.getEstudianteBean().getSeccion());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoLista(matriculaFiltroBean);
//                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
//                        for (MatriculaBean ma : listaMatriculaEstudiantesMasivosBean) {
//                            ponerEstudianteEnMatricula(ma);
//                            ma.setGradoAcademicoBean(matriculaFiltroBean.getGradoAcademicoBean());
//                        }
                    if (listaMatriculaEstudiantesMasivosBean.size() > 0) {
                        for (int i = 0; i < listaMatriculaEstudiantesMasivosBean.size(); i++) {
                            ponerEstudianteEnMatricula(listaMatriculaEstudiantesMasivosBean.get(i));
                            listaMatriculaEstudiantesMasivosBean.get(i).setGradoAcademicoBean(matriculaFiltroBean.getGradoAcademicoBean());
                        }
                    } else {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }

                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerEstudianteEnMatricula(Object matricula) {
        try {
            matriculaFiltroBean = (MatriculaBean) matricula;
            Integer estado = 0;
            switch (matriculaFiltroBean.getGradoAcademicoBean().getNombre()) {
                case MaristaConstantes.PreInicial_4_anios:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_3_aï¿½os);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_3_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Inicial_5_anios:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_4_aï¿½os);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_4_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Primero_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Inicial_5_aï¿½os);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Inicial_5_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Segundo_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Tercero_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Cuarto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Quinto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Sexto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Quinto_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Quinto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Primero_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Sexto_Primaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Sexto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Segundo_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Tercero_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Cuarto_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Cuarto_Bach_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Quinto_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Quinto_Bach_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Bach_Secundaria);
                    matriculaFiltroBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Bach_Secundaria);
                    estado = 1;
                    break;
                default:
                    break;
            }

            if (estado == 1) {
                obtenerGradoAcaPorNombre();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaPorNombre() {
        try {
//            gradoAcademicoBean =(GradoAcademicoBean)grado;
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            gradoAyudaCambio = gradoAcademicoService.obtenerPorIdNombre(matriculaFiltroBean.getGradoAcademicoBean().getNombre());
            matriculaFiltroBean.setGradoAcademicoBean(gradoAyudaCambio);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroMatriculaMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodosMatriculados == true) {
                matriculaFiltroBean.setGradoAcademicoBean(null);
//                matriculaFiltroBean.setAnio(null);
                listaMatriculaFlgFlaseBean = matriculaService.obtenerFiltroMatriculaMasivo(matriculaFiltroBean);
                if (listaMatriculaFlgFlaseBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaMatriculaFlgFlaseBean = new ArrayList<>();
                }
            } else {
                Calendar miCalendario = Calendar.getInstance();
//                if (matriculaFiltroBean.getAnio() != null && !matriculaFiltroBean.getAnio().equals(0)) {
//                    matriculaFiltroBean.setAnio(miCalendario.get(Calendar.YEAR) + 1);
//                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().setIdEstudiante(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().toUpperCase().trim());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                } else if (estado == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
                if (estado == 1) {
                    listaMatriculaFlgFlaseBean = matriculaService.obtenerFiltroMatriculaMasivo(matriculaFiltroBean);
                    if (listaMatriculaFlgFlaseBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaFlgFlaseBean = new ArrayList<>();
                    }
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarValAdmTodos() {
        try {
            if (valAdmTodos) {
                for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
                    matricula.getEstudianteBean().setEstadoAprobacion(true);
                }
            } else {
                for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
                    matricula.getEstudianteBean().setEstadoAprobacion(false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarValAdmUnoPorUno() {
        try {

            for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
                System.out.println("estado: " + matricula.getEstudianteBean().getEstadoAprobacion());
                if (matricula.getEstudianteBean().getEstadoAprobacion() == true) {
                    matricula.getEstudianteBean().setEstadoAprobacion(true);
                    System.out.println("entro");
                } else {
                    matricula.getEstudianteBean().setEstadoAprobacion(false);
                    System.out.println("Nancy");
                }

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaBasicaSeccion() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaBasicaMatricula() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarMatricula() {
        matriculaBean = new MatriculaBean();
        programacionBean = new ProgramacionBean();
        estudianteBean = new EstudianteBean();
        comodin = false;

        fechaActual = new GregorianCalendar();
        getMatriculaBean().setFechaMatricula(fechaActual.getTime());
        this.texto = null;
        this.alerta = null;
        if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                if (matriculaBean.isFlgMatricula() == true) {
                    flgMatriculado = true;
                }else{
                    flgMatriculado = false;
                }
            } else {
                flgMatriculado = false;
            }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        flgEstEsp = false;
        flgEstSinPro = false;
        flgPorNivelGrado = false;
        flgTodos = false;
        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
    }

    public void limpiarFiltroNoMatriculados() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaFlgFlaseBean = new ArrayList<>();
        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
    }

    public void limpiarFiltroMatriculados() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaFlgTrueBean = new ArrayList<>();
        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
    }

    public void obtenerMatricula() {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaFlgFlaseBean = matriculaService.obtenerMatricula();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarMatriculaMasivo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (crIni != null || crPri != null || crSec == null || crBac == null) {
                    if (matriculaBean.getProgramacionBean().getIdProgramacion() != null && !matriculaBean.getProgramacionBean().getIdProgramacion().equals(0)) {
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        ProgramacionService programacionService = BeanFactory.getProgramacionService();
                        MatriculaBean matricula = new MatriculaBean();
                        programacionBean = new ProgramacionBean();
                        programacionBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                        programacionBean.setIdProgramacion(matriculaBean.getProgramacionBean().getIdProgramacion());
                        programacionBean = programacionService.obtenerPrograPorId(programacionBean);
                        matriculaBean.setProgramacionBean(programacionBean);
                        matriculaService.insertarMatriculaMasivo(matricula, matriculaBean, listaMatriculaEstudiantesMasivosBean, usuarioLogin, crIni, crPri, crSec, crBac);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarMatricula() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                MatriculaBean matricula = new MatriculaBean();
                matricula.setAnio(programacionBean.getProcesoBean().getAnio());
                matricula.setEstudianteBean(estudianteBean);
                matricula = matriculaService.obtenerMatriculaPorIdPeriodo(matricula);
                if (matricula == null) {
                    matriculaBean.setEstudianteBean(estudianteBean);
//                    matriculaBean.setProgramacionBean(programacionBean);
                    matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    matriculaBean.setCreaPor(usuarioLogin.getUsuario());
                    matriculaService.insertarMatricula(matriculaBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    limpiarMatricula();
                    limpiarFiltroNoMatriculados();
                } else {
                    limpiarMatricula();
                    new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajeMatriYaReg", null));
                    limpiarMatricula();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarMatricula() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                matriculaBean.setModiPor(usuarioLogin.getUsuario());
//                matriculaBean.setModiPor(usuarioLogin.getUsuario());
                System.out.println("matricu: " + matriculaBean.getSeccion());
                matriculaService.modificarMatricula(matriculaBean);
                String idEstudiante = matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona();
                String seccion = matriculaBean.getSeccion();
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteService.modificarEstudianteSeccion(idEstudiante, seccion);
//                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
//                    EstudianteBean est = new EstudianteBean();
//                    est.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                    est.setModiPor(usuarioLogin.getUsuario());
//                    est.getPersonaBean().setIdPersona(idEstudiante);
//                    est.getRespPagoBean().setIdPersona(matriculaBean.getEstudianteBean().getRespPagoBean().getIdPersona());
//                    FamiliarService familiarEstudianteService = BeanFactory.getFamiliarService();
//                    FamiliarEstudianteBean fam = new FamiliarEstudianteBean();
//                    fam.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                    fam.getEstudianteBean().getPersonaBean().setIdPersona(idEstudiante);
//                    fam.getFamiliarBean().getPersonaBean().setIdPersona(matriculaBean.getEstudianteBean().getRespPagoBean().getIdPersona());
//                    FamiliarEstudianteBean fam2 = new FamiliarEstudianteBean();
//                    fam2 = familiarEstudianteService.obtenerFamiliarEstPorId(fam);
//                    if (fam2.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
//                        est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_PAPA_id);
//                    } else if (fam2.getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
//                        est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_MAMA_id);
//                    } else {
//                        est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_Apoderado_id);
//                    }
//
//                    estudianteService.modificarEstudianteRespMat(est);
//                }
//                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                EstudianteBean est = new EstudianteBean();
                est.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                est.setModiPor(usuarioLogin.getUsuario());
                est.getPersonaBean().setIdPersona(idEstudiante); 
                matriculaBean.getEstudianteBean().getTipoRespPago().setIdCodigo(Integer.parseInt(matriculaBean.getEstudianteBean().getTipoRespPago().getCodigo()));
                String idDniRespPago;
                idDniRespPago=estudianteService.obtenerIdResPago(idEstudiante, matriculaBean.getEstudianteBean().getTipoRespPago().getIdCodigo());
                System.out.println("ver: "+matriculaBean.getEstudianteBean().getTipoRespPago().getIdCodigo());
                if (matriculaBean.getEstudianteBean().getTipoRespPago().getIdCodigo().equals(MaristaConstantes.CODIGO_MADRE)) {
                    est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_MAMA_id); 
                    est.getRespPagoBean().setIdPersona(idDniRespPago);
                } else if (matriculaBean.getEstudianteBean().getTipoRespPago().getIdCodigo().equals(MaristaConstantes.CODIGO_PADRE)) {
                    est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_PAPA_id); 
                    est.getRespPagoBean().setIdPersona(idDniRespPago);
                } else {
                    est.getTipoRespPago().setIdCodigo(MaristaConstantes.COD_Apoderado_id);
                    est.getRespPagoBean().setIdPersona(idDniRespPago);
                }

                estudianteService.modificarEstudianteRespMat(est);
//                }
//                matriculaBean.setModiPor(usuarioLogin.getUsuario());
//                limpiarMatricula();
//                limpiarFiltroNoMatriculados();
//                limpiarFiltroMatriculados();
                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    if (matriculaBean.isFlgMatricula() == true) {
                        flgMatriculado = true;
                        System.out.println("ingreso");
                    }
                } else {
                    System.out.println("no Ingreso");
                    flgMatriculado = false;
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarMatricula() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaService.eliminarMatricula(matriculaBean);
                matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                Calendar miCalendario = Calendar.getInstance();
                matriculaBean.setAnio(miCalendario.get(Calendar.YEAR));
//              
                limpiarMatricula();
                limpiarFiltroNoMatriculados();
                limpiarFiltroMatriculados();

                getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
                getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarMatricula() {
        if (matriculaBean.getIdMatricula() != null) {
            modificarMatricula();
        } else {
            insertarMatricula();
        }
    }
    public void obtenerCodigo() {
        System.out.println("codigo: "+matriculaBean.getEstudianteBean().getTipoRespPago().getCodigo());
        System.out.println("idCodigo: "+matriculaBean.getEstudianteBean().getTipoRespPago().getIdCodigo());
    }

    public void rowSelect(SelectEvent event) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            matriculaBean = (MatriculaBean) event.getObject();
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
            fechaActual = new GregorianCalendar();
            fechaHoy = new Date();
            List<CuentasPorCobrarBean> ListaCtasXCobrar = cuentasPorCobrarService.obtenerCuentaPorMat(matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CuentasPorCobrarBean cuenta : ListaCtasXCobrar) {
                if (cuenta.getFechaVenc().getTime() < fechaHoy.getTime() && cuenta.getFechaPago() == null) {
                    this.alerta = "/resources/images/rojo.png";
                    this.texto = "Estudiante tiene cuentas pendientes por pagar";
                    this.flgHabiMatricula = true;
                }
            }

//            ProgramacionService programacionService = BeanFactory.getProgramacionService();
//            programacionBean = programacionService.obtenerPrograPorId(matriculaBean.getProgramacionBean().getIdProgramacion());
//            comodin = true;
            getMatriculaBean().setFechaMatricula(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectMatri(SelectEvent event) {
        try {
            if (Objects.equals(matriculaBean.getEstudianteBean().getTipoStatusEst().getIdCodigo(), MaristaConstantes.COD_ESTUDIANTE_ACTIVO)) {
                FamiliarService familiarService = BeanFactory.getFamiliarService();
                CodigoService codigoService = BeanFactory.getCodigoService();
                matriculaBean = (MatriculaBean) event.getObject();
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
                fechaActual = new GregorianCalendar();
                getMatriculaBean().setFechaMatricula(fechaActual.getTime());
                setBloqueoMatricula(Boolean.FALSE);
                listaFamiliarEstudianteRespBean = familiarService.obtenerFamiliarEstPorEst(matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
                System.out.println("respPago: " + matriculaBean.getEstudianteBean().getTipoRespPago().getCodigo());
//                getListaParentescoFam1();
//                listaParentescoFam1 = codigoService.obtenerParentescoConTodo();
                System.out.println("matr: " + matriculaBean.isFlgMatricula());
//                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
//                    this.flgUniSanLui = true;
//                }

            } else {
                System.out.println("msjCondionado");
                new MensajePrime().addInformativeMessagePer(MensajesBackEnd.getValueOfKey("msjCondionado", null));
                setBloqueoMatricula(Boolean.TRUE);
            }
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                if (matriculaBean.isFlgMatricula() == true) {
                    flgMatriculado = true;
                }else{
                    flgMatriculado = false;
                }
            } else {
                flgMatriculado = false;
            }
//            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerMatricula(Object matricula) {
        try {
            matriculaBean = (MatriculaBean) matricula;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
//            matriculaBean.setEstudianteBean(estudianteBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<EstudianteBean> completeEstudiante(String query) {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<EstudianteBean> listaEstudianteTodosBean = estudianteService.obtenerEstudianteMatPorUniNeg(MaristaConstantes.COD_ADMITIDO, usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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
            ProgramacionBean programacion = (ProgramacionBean) event.getObject();
//            programacionBean = (ProgramacionBean) event.getObject();
            if (programacion.getOcupados() < programacion.getMax()) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                programacionBean = programacionService.obtenerPrograPorId(programacion);
            } else {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajePrograNoDisp", null));
                programacionBean = new ProgramacionBean();
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //estadisticas
    //Metodos de aplicacion
    public void verEstadisticasMatricula() {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            viewMatriculaBean.setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaViewMatriculaBean = matriculaService.verEstadisticasMatriculaPorAnio(viewMatriculaBean);
            obtenerTotales();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String cambiarGradoMatricula() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                if (GradoAcademicoBean.getNombre().equals(MaristaConstantes.Inicial_5_aï¿½os)) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaBean.setModiPor(usuarioLogin.getUsuario());
                matriculaService.modificarMatriculaGradoAcaLista(matriculaBean, usuarioLogin, listaMatriculaEstudiantesMasivosBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                } else {
//                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", false);
//                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerTotales() {
        Integer totalIni = 0;
        Integer totalPri = 0;
        Integer totalSec = 0;
        try {
            viewMatriculaBean.setTotalIni(0);
            viewMatriculaBean.setTotalPri(0);
            viewMatriculaBean.setTotalSec(0);
            for (ViewMatriculaBean viewMatricula : getListaViewMatriculaBean()) {
                switch (viewMatricula.getNivel()) {
                    case MaristaConstantes.NIV_ACA_INI:
                        totalIni = totalIni + viewMatricula.getTotal();
                        viewMatriculaBean.setTotalIni(totalIni);
                        break;
                    case MaristaConstantes.NIV_ACA_PRI:
                        totalPri = totalPri + viewMatricula.getTotal();
                        viewMatriculaBean.setTotalPri(totalPri);
                        break;
                    case MaristaConstantes.NIV_ACA_SEC:
                        totalSec = totalSec + viewMatricula.getTotal();
                        viewMatriculaBean.setTotalSec(totalSec);
                        break;
                }
            }
            viewMatriculaBean.setTotalIniPriSec(totalIni + totalPri + totalSec);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void cambiarComodin() {
        comodin = false;
    }

    //onload
    public void cargarMatriEst() {
        try {
            String parametro = (String) new MaristaUtils().requestObtenerObjeto("codEst");
            if (parametro != null) {
                getMatriculaFiltroBean().getEstudianteBean().getPersonaBean().setIdPersona(parametro);
                System.out.println("anio: " + Calendar.getInstance().get(Calendar.YEAR));
                getMatriculaFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
                obtenerFiltroNoMatriculados();
                if (listaMatriculaFlgFlaseBean != null && !listaMatriculaFlgFlaseBean.isEmpty()) {
                    setMatriculaBean(listaMatriculaFlgFlaseBean.get(0));
                }
                if (Objects.equals(getMatriculaBean().getEstudianteBean().getTipoStatusEst().getIdCodigo(), MaristaConstantes.COD_ESTUDIANTE_ACTIVO)) {
                    MatriculaService matriculaService = BeanFactory.getMatriculaService();
                    matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
                    fechaActual = new GregorianCalendar();
                    getMatriculaBean().setFechaMatricula(fechaActual.getTime());
                    setBloqueoMatricula(Boolean.FALSE);
                } else {
                    System.out.println("msjCondionado");
                    new MensajePrime().addInformativeMessagePer(MensajesBackEnd.getValueOfKey("msjCondionado", null));
                    setBloqueoMatricula(Boolean.TRUE);
                }
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirPdfDeclaracionJurada() {
        ServletOutputStream out = null;
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDeclaracionJurada.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DeclaracionJuradaRepBean> listaDeclaracion = new ArrayList<>();
            listaDeclaracion = matriculaService.obtenerDeclaracionJuradaCabecera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio());
            if (!listaDeclaracion.isEmpty()) {
                for (int i = 0; i < listaDeclaracion.size(); i++) {
                    List<DeclaracionJuradaRepBean> listaPrimera = new ArrayList<>();
                    listaPrimera = matriculaService.obtenerDeclaracionJuradaPrimera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio(),
                            matriculaBean.getEstudianteBean().getIdEstudiante());
                    listaDeclaracion.get(i).setListaPrimera(listaPrimera);

                    List<DeclaracionJuradaRepBean> listaSegunda = new ArrayList<>();
                    listaSegunda = matriculaService.obtenerDeclaracionJuradaPrimera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio(),
                            matriculaBean.getEstudianteBean().getIdEstudiante());
                    listaDeclaracion.get(0).setListaSegunda(listaSegunda);
//                    listaDeclaracion.get(0).setListaPrimera(listaPrimera);

                    List<DeclaracionJuradaRepBean> listaTercero = new ArrayList<>();
                    listaTercero = matriculaService.obtenerDeclaracionJuradaSegunda(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio(),
                            matriculaBean.getEstudianteBean().getIdEstudiante());
                    listaDeclaracion.get(0).setListaTercero(listaTercero);
//                    listaDeclaracion.get(0).setListaSegunda(listaSegunda);
//                    listaDeclaracion.get(0).setListaPrimera(listaPrimera);

                    List<DeclaracionJuradaRepBean> listaCuarto = new ArrayList<>();
                    listaCuarto = matriculaService.obtenerDeclaracionJuradaTercera(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio(),
                            matriculaBean.getEstudianteBean().getIdEstudiante());
                    listaDeclaracion.get(0).setListaCuarto(listaCuarto);
                    listaDeclaracion.get(0).setListaTercero(listaTercero);
                    listaDeclaracion.get(0).setListaSegunda(listaSegunda);
                    listaDeclaracion.get(0).setListaPrimera(listaPrimera);

                }
            }
            System.out.println("absoluteWebPath:" + absoluteWebPath);
//,matriculaBean.getEstudianteBean().getIdEstudiante()
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDeclaracion);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=Declaracion_Jurada_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    ///////////reporte
    public void imprimirEstadisticaMatricula(String uniNeg, Integer anio, String tipFor) {
        ServletOutputStream out = null;
        try {
            tipFor = MaristaConstantes.TIP_FOR_BAS;
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/XXXXXXXXXXXXXXX.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            //0.nivels
            List<EstMatriculaRepBean> listaCabecera = new ArrayList<>();
//            listaCabecera = matriculaService.obtenerEstadisticaMatriculaCabecera(uniNeg, anio);
            //1.-Lista de niveles 
//            listaCabecera = matriculaService.obtenerEstadisticaMatriculaCabecera(uniNeg, anio);

//            if (!listaCabecera.isEmpty()) {
//                for (int g = 0; g < listaCabecera.size(); g++) {
//                    List<EstMatriculaNivelRepBean> listaNiveles = new ArrayList<>();
//                    listaNiveles = matriculaService.obtenerNiveles(uniNeg, anio, tipFor);
//                    //2.-Obtener gradoPorNiveles
//                    if (!listaNiveles.isEmpty()) {
//                        for (int i = 0; i < listaNiveles.size(); i++) {
//                            List<EstMatriculaGradoAcaRepBean> listaGradoAcaPorNivel = new ArrayList<>();
//                            listaGradoAcaPorNivel = matriculaService.obtenerGradosAcademicoPorNivel(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel());
//                            listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
//                            listaCabecera.get(0).setListaNiveles(listaNiveles);
//                            //3.- obtener secciones por grado academico       
//                            if (!listaGradoAcaPorNivel.isEmpty()) {
//                                for (int j = 0; j < listaGradoAcaPorNivel.size(); j++) {
//                                    List<EstMatriculaSeccGradoAcaRepBean> listaSeccionesPorGradoAca = new ArrayList<>();
//                                    listaSeccionesPorGradoAca = matriculaService.obtenerSeccionesPorGradoAca(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel(), listaGradoAcaPorNivel.get(j).getIdGradoAcademico());
//                                    listaGradoAcaPorNivel.get(j).setListaGradoAcaSeccion(listaSeccionesPorGradoAca);
//                                    listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
//                                    listaCabecera.get(0).setListaNiveles(listaNiveles);
//                                    //4. obtener detalle por seccion
//                                    if (!listaSeccionesPorGradoAca.isEmpty()) {
//                                        for (int k = 0; k < listaSeccionesPorGradoAca.size(); k++) {
//                                            List<EstMatriculaSeccionRepBean> listaDetSeccionPorGradoAcaySecc = new ArrayList<>();
//                                            listaDetSeccionPorGradoAcaySecc = matriculaService.obtenerDetSeccionPorGradoAca(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel(), listaGradoAcaPorNivel.get(j).getIdGradoAcademico(), listaSeccionesPorGradoAca.get(k).getSeccion());
//                                            listaSeccionesPorGradoAca.get(k).setListaSeccion(listaDetSeccionPorGradoAcaySecc);
//                                            listaGradoAcaPorNivel.get(j).setListaGradoAcaSeccion(listaSeccionesPorGradoAca);
//                                            listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
//                                            listaCabecera.get(0).setListaNiveles(listaNiveles);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//                }
//            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
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

    //GEtter y Setter
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

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
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

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public boolean isComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

//    public EstudianteBean getEstudianteFiltroBean() {
//        if (estudianteFiltroBean == null) {
//            estudianteFiltroBean = new EstudianteBean();
//        }
//        return estudianteFiltroBean;
//    }
//
//    public void setEstudianteFiltroBean(EstudianteBean estudianteFiltroBean) {
//        this.estudianteFiltroBean = estudianteFiltroBean;
//    }
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

    public List<MatriculaBean> getListaMatriculaFlgFlaseBean() {
        if (listaMatriculaFlgFlaseBean == null) {
            listaMatriculaFlgFlaseBean = new ArrayList<>();
        }
        return listaMatriculaFlgFlaseBean;
    }

    public void setListaMatriculaFlgFlaseBean(List<MatriculaBean> listaMatriculaFlgFlaseBean) {
        this.listaMatriculaFlgFlaseBean = listaMatriculaFlgFlaseBean;
    }

    public List<MatriculaBean> getListaMatriculaFlgTrueBean() {
        if (listaMatriculaFlgTrueBean == null) {
            listaMatriculaFlgTrueBean = new ArrayList<>();
        }
        return listaMatriculaFlgTrueBean;
    }

    public void setListaMatriculaFlgTrueBean(List<MatriculaBean> listaMatriculaFlgTrueBean) {
        this.listaMatriculaFlgTrueBean = listaMatriculaFlgTrueBean;
    }

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {

        this.matriculaFiltroBean = matriculaFiltroBean;
    }

    public List<Integer> getListaAnioMatricula() {
        if (listaAnioMatricula == null) {
            listaAnioMatricula = new ArrayList<>();
        }
        return listaAnioMatricula;
    }

    public void setListaAnioMatricula(List<Integer> listaAnioMatricula) {
        this.listaAnioMatricula = listaAnioMatricula;
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

    public Date getFechaHoy() {
        return fechaHoy;
    }

    public void setFechaHoy(Date fechaHoy) {
        this.fechaHoy = fechaHoy;
    }

    public String getAlerta() {
        return alerta;
    }

    public void setAlerta(String alerta) {
        this.alerta = alerta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getFlgHabiMatricula() {
        return flgHabiMatricula;
    }

    public void setFlgHabiMatricula(Boolean flgHabiMatricula) {
        this.flgHabiMatricula = flgHabiMatricula;
    }

//    public List<EstudianteBean> getListaEstudiantesMasivosBean() {
//        if (listaEstudiantesMasivosBean == null) {
//            listaEstudiantesMasivosBean = new ArrayList<>();
//        }
//        return listaEstudiantesMasivosBean;
//    }
//
//    public void setListaEstudiantesMasivosBean(List<EstudianteBean> listaEstudiantesMasivosBean) {
//        this.listaEstudiantesMasivosBean = listaEstudiantesMasivosBean;
//    }
    public Boolean getFlgEstSinPro() {
        return flgEstSinPro;
    }

    public void setFlgEstSinPro(Boolean flgEstSinPro) {
        this.flgEstSinPro = flgEstSinPro;
    }

    public Boolean getFlgPorNivelGrado() {
        return flgPorNivelGrado;
    }

    public void setFlgPorNivelGrado(Boolean flgPorNivelGrado) {
        this.flgPorNivelGrado = flgPorNivelGrado;
    }

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
    }

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
        if (listaMatriculaEstudiantesMasivosBean == null) {
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBean;
    }

    public void setListaMatriculaEstudiantesMasivosBean(List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) {
        this.listaMatriculaEstudiantesMasivosBean = listaMatriculaEstudiantesMasivosBean;
    }

    public List<ViewMatriculaBean> getListaViewMatriculaBean() {
        if (listaViewMatriculaBean == null) {
            listaViewMatriculaBean = new ArrayList<>();
        }
        return listaViewMatriculaBean;
    }

    public void setListaViewMatriculaBean(List<ViewMatriculaBean> listaViewMatriculaBean) {
        this.listaViewMatriculaBean = listaViewMatriculaBean;
    }

    public ViewMatriculaBean getViewMatriculaBean() {
        if (viewMatriculaBean == null) {
            viewMatriculaBean = new ViewMatriculaBean();
        }
        return viewMatriculaBean;
    }

    public void setViewMatriculaBean(ViewMatriculaBean viewMatriculaBean) {
        this.viewMatriculaBean = viewMatriculaBean;
    }

    public Boolean getFlgTodosMatriculados() {
        return flgTodosMatriculados;
    }

    public void setFlgTodosMatriculados(Boolean flgTodosMatriculados) {
        this.flgTodosMatriculados = flgTodosMatriculados;
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

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
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

    public List<CentroResponsabilidadBean> getListaCrInicialBean() {
        if (listaCrInicialBean == null) {
            listaCrInicialBean = new ArrayList<>();
        }
        return listaCrInicialBean;
    }

    public void setListaCrInicialBean(List<CentroResponsabilidadBean> listaCrInicialBean) {
        this.listaCrInicialBean = listaCrInicialBean;
    }

    public List<CentroResponsabilidadBean> getListaCrPrimariaBean() {
        if (listaCrPrimariaBean == null) {
            listaCrPrimariaBean = new ArrayList<>();
        }
        return listaCrPrimariaBean;
    }

    public void setListaCrPrimariaBean(List<CentroResponsabilidadBean> listaCrPrimariaBean) {
        this.listaCrPrimariaBean = listaCrPrimariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCrSecundariaBean() {
        if (listaCrSecundariaBean == null) {
            listaCrSecundariaBean = new ArrayList<>();
        }
        return listaCrSecundariaBean;
    }

    public void setListaCrSecundariaBean(List<CentroResponsabilidadBean> listaCrSecundariaBean) {
        this.listaCrSecundariaBean = listaCrSecundariaBean;
    }

    public List<CentroResponsabilidadBean> getListaCrBachillerBean() {
        if (listaCrBachillerBean == null) {
            listaCrBachillerBean = new ArrayList<>();
        }
        return listaCrBachillerBean;
    }

    public void setListaCrBachillerBean(List<CentroResponsabilidadBean> listaCrBachillerBean) {
        this.listaCrBachillerBean = listaCrBachillerBean;
    }

    public Integer getCrIni() {
        return crIni;
    }

    public void setCrIni(Integer crIni) {
        this.crIni = crIni;
    }

    public Integer getCrPri() {
        return crPri;
    }

    public void setCrPri(Integer crPri) {
        this.crPri = crPri;
    }

    public Integer getCrSec() {
        return crSec;
    }

    public void setCrSec(Integer crSec) {
        this.crSec = crSec;
    }

    public Integer getCrBac() {
        return crBac;
    }

    public void setCrBac(Integer crBac) {
        this.crBac = crBac;
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

    public Boolean getFlgChamps() {
        return flgChamps;
    }

    public void setFlgChamps(Boolean flgChamps) {
        this.flgChamps = flgChamps;
    }

    public Boolean getBloqueoMatricula() {
        return bloqueoMatricula;
    }

    public void setBloqueoMatricula(Boolean bloqueoMatricula) {
        this.bloqueoMatricula = bloqueoMatricula;
    }

    public Calendar getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Calendar fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public List<MatriculaBean> getListaMatriculaFiltroBean() {
        if (listaMatriculaFiltroBean == null) {
            listaMatriculaFiltroBean = new ArrayList<>();
        }
        return listaMatriculaFiltroBean;
    }

    public void setListaMatriculaFiltroBean(List<MatriculaBean> listaMatriculaFiltroBean) {
        this.listaMatriculaFiltroBean = listaMatriculaFiltroBean;
    }

    public GradoAcademicoBean getGradoAyudaCambio() {
        if (gradoAyudaCambio == null) {
            gradoAyudaCambio = new GradoAcademicoBean();
        }
        return gradoAyudaCambio;
    }

    public void setGradoAyudaCambio(GradoAcademicoBean gradoAyudaCambio) {
        this.gradoAyudaCambio = gradoAyudaCambio;
    }

    public Boolean getFlgMostrarLista() {
        return flgMostrarLista;
    }

    public void setFlgMostrarLista(Boolean flgMostrarLista) {
        this.flgMostrarLista = flgMostrarLista;
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

    public Boolean getFlgUniSanLui() {
        return flgUniSanLui;
    }

    public void setFlgUniSanLui(Boolean flgUniSanLui) {
        this.flgUniSanLui = flgUniSanLui;
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

    public Boolean getFlgMatriculado() {
        return flgMatriculado;
    }

    public void setFlgMatriculado(Boolean flgMatriculado) {
        this.flgMatriculado = flgMatriculado;
    }
}
