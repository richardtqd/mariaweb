package pe.marista.sigma.managedBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
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
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.AlumnosIngresantesRetiradosRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaNivelRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccionRepBean;
import pe.marista.sigma.bean.reporte.EstudianteAlergiasRepBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadPadresRepBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadRepBean;
import pe.marista.sigma.bean.reporte.EstudianteGeneralRepBean;
import pe.marista.sigma.bean.reporte.EstudianteMedicamentosRepBean;
import pe.marista.sigma.bean.reporte.EstudianteNacimientoRepBean;
import pe.marista.sigma.bean.reporte.EstudianteRepBean;
import pe.marista.sigma.bean.reporte.EstudianteTraumaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteVacunasRepBean;
import pe.marista.sigma.bean.reporte.EvaluacionDesempenoRepBean;
import pe.marista.sigma.bean.reporte.FamiliarEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FamiliarRepBean;
import pe.marista.sigma.bean.reporte.FichaClinicaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FichaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.ListaAlumnosRepBean;
import pe.marista.sigma.bean.reporte.ResponsableEconomicoRepBean;
import pe.marista.sigma.bean.reporte.SeguimientoEDRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EstudianteAlergiaService;
import pe.marista.sigma.service.EstudianteEnfermedadService;
import pe.marista.sigma.service.EstudianteMedicamentoService;
import pe.marista.sigma.service.EstudianteNacimientoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.EstudianteTraumaService;
import pe.marista.sigma.service.EstudianteVacunaService;
import pe.marista.sigma.service.EvaluacionDesempenoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class EstudianteRepMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ImpresionMasivaComprobanteMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaAnioFiltroMatricula();
            for (int i = Calendar.getInstance().get(Calendar.YEAR) - 2; i <= Calendar.getInstance().get(Calendar.YEAR) + 2; i++) {
                listaAnioFiltroMatricula.add(i);
            }
            getMatriculaFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            getMatriculaFiltroAfterBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            this.fechaMatricula = new GregorianCalendar();
            getMatriculaFiltroAfterBean().setFechaMatricula(fechaMatricula.getTime());
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            getMatriculaFiltroAfterBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

            fechaActual = new GregorianCalendar();
            getMatriculaFiltroBean().setFechaInicio(fechaActual.getTime());
            getMatriculaFiltroBean().setFechaFin(fechaActual.getTime());
            getMatriculaFiltroBean().setFechaMatricula(fechaActual.getTime());
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            fechaActual = new GregorianCalendar();
            getMatriculaFiltroBean().setFechaMatricula(fechaActual.getTime());
            getMatriculaFiltroBean().setFlgMatriculaVista(1);
            getMatriculaFiltroAfterBean().setFlgMatriculaVista(1);
            listaMatricula();
            listaMatriculaOpciones();

            Calendar miCalendario = Calendar.getInstance();
            getMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR));

            SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yyyy");
            Integer anio1 = getMatriculaBean().getAnio();
            String fechaI = "01-03-" + anio1.toString();
            Date fechaInicio = formatoDiaCompleto.parse(fechaI);
            String dateCompleto = formatoDiaCompleto.format(new Date());
            Date fechaFin = formatoDiaCompleto.parse(dateCompleto);
            getMatriculaBean().setFechaInicioClases(fechaInicio);
            getMatriculaBean().setFechaFinFiltro(fechaFin);
            matriculaListadoAlBean = new MatriculaBean();
            matriculaListadoAlBean.setAnio(miCalendario.get(Calendar.YEAR)); 
            anio = miCalendario.get(Calendar.YEAR);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }
    private List<EstudianteBean> listaEstudianteBean;
    private Boolean flgTodos;
    private Boolean flgEstComprobanteMes;
    private Boolean flgEstEsp;
    private Boolean flgPorNivelGrado;
    private MatriculaBean matriculaFiltroBean;
    private List<Integer> listaAnioFiltroMatricula;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private Boolean flgEstEspMatricula;
    private List<MatriculaBean> listaMatriculaEstudianteMasivoBean;
    private List<MatriculaBean> listaMatriculaEstudianteMasivoAfterBean;
    private UsuarioBean usuarioLogin;

    private Boolean flgTodosAfter;
    private Boolean flgPorNivelGradoAfter;
    private Boolean flgEstEspAfter;
    private MatriculaBean matriculaFiltroAfterBean;
    private Calendar fechaActual;
    private String fechaI;
    private String fechaF;
    private Calendar fechaMatricula;

    //fichaEstudiante
    private FamiliarEstudianteBean familiarEstudianteBean;
    private FamiliarBean familiarBean;
    private EstudianteBean estudianteBean;
    private MatriculaBean matriculaBean;
    private Integer matricula;
    private Map<String, Integer> listaMatricula;
    private Map<String, Integer> listaMatriculaOpciones;
    private MatriculaBean matriculaListadoAlBean;
    private Boolean flgLista = false;
    private Integer anio;

    //Lógica de Negocio
    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
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
                matriculaFiltroAfterBean.setEstudianteBean(null);
                matriculaFiltroAfterBean.getEstudianteBean().setGradoHabilitado(null);
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
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
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
                matriculaFiltroAfterBean.setEstudianteBean(null);
                matriculaFiltroAfterBean.getEstudianteBean().setGradoHabilitado(null);
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

    public void verificarFiltroNivelGradoAfter() {
        try {
            if (this.flgPorNivelGradoAfter == true) {
                this.flgTodosAfter = false;
                this.flgEstEspAfter = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMatricula() {
        listaMatricula = new LinkedHashMap<>();
        listaMatricula.put(MensajesBackEnd.getValueOfKey("etiquetaEstuMat", null), 1);
        listaMatricula.put(MensajesBackEnd.getValueOfKey("etiquetaEstuNoMat", null), 2);
        listaMatricula.put(MensajesBackEnd.getValueOfKey("etiquetaTodos", null), 3);
        listaMatricula = Collections.unmodifiableMap(listaMatricula);
    }

    public void listaMatriculaOpciones() {
        listaMatriculaOpciones = new LinkedHashMap<>();
        listaMatriculaOpciones.put(MensajesBackEnd.getValueOfKey("etiquetaMatriculadoRetirados", null), 1);
        listaMatriculaOpciones.put(MensajesBackEnd.getValueOfKey("etiquetaMatriculadosActivos", null), 2);
        listaMatriculaOpciones.put(MensajesBackEnd.getValueOfKey("etiquetaEstuNoMat", null), 3);
        listaMatriculaOpciones.put(MensajesBackEnd.getValueOfKey("etiquetaTodos", null), 4);
        listaMatriculaOpciones = Collections.unmodifiableMap(listaMatriculaOpciones);
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
//                matriculaFiltroBean.setAnioFin(null); 
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(matriculaFiltroBean.getFechaInicio());
                Integer filtroAnio = calendar.get(Calendar.YEAR);
                matriculaFiltroBean.setCreaPor(matriculaFiltroBean.getCreaPor());
                matriculaFiltroBean.setAnio(filtroAnio);
                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroBean);
            } else {
//                Calendar miCalendario = Calendar.getInstance();
//                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(matriculaFiltroBean.getFechaInicio());
                Integer filtroAnio = calendar.get(Calendar.YEAR);
                matriculaFiltroBean.setCreaPor(matriculaFiltroBean.getCreaPor());
                matriculaFiltroBean.setAnio(filtroAnio);
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
                if (matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroBean);
                    if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroEstudiantePorUsu() {
        try {
            int estado = 1;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaFiltroBean.setCreaPor(usuarioLogin.getUsuario());
            setFechaI(null);
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteMatriculadosPorUsu(matriculaFiltroBean);
            } else {
                matriculaFiltroBean.setAnioFin(null);
//                Calendar miCalendario = Calendar.getInstance();
//                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR));
                if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (matriculaFiltroBean.getFechaInicio() != null) {
                    Timestamp t = new Timestamp(matriculaFiltroBean.getFechaInicio().getTime());
                    t.setHours(0);
                    t.setMinutes(0);
                    t.setSeconds(0);
                    matriculaFiltroBean.setFechaInicio(t);
                    estado = 1;

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String f = sdf.format(matriculaFiltroBean.getFechaInicio());
                    setFechaI(f);

                }
                if (matriculaFiltroBean.getFechaFin() != null) {
                    Timestamp u = new Timestamp(matriculaFiltroBean.getFechaFin().getTime());
                    u.setHours(23);
                    u.setMinutes(59);
                    u.setSeconds(59);
                    matriculaFiltroBean.setFechaFin(u);
                    estado = 1;

                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                    String f2 = sdf2.format(matriculaFiltroBean.getFechaFin());
                    setFechaF(f2);
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
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteMatriculadosPorUsu(matriculaFiltroBean);
                    if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTablaParaExcel() {
        try {
            int estado = 1;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaFiltroBean.setCreaPor(usuarioLogin.getUsuario());
            setFechaI(null);
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(matriculaFiltroBean.getFechaMatricula());
                Integer filtroAnio = calendar.get(Calendar.YEAR);
                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerTablaParaExcel(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, matriculaFiltroBean.getFechaInicio(), matriculaFiltroBean.getFechaFin(), matricula);
            } else {
                matriculaFiltroBean.setAnioFin(null);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(matriculaFiltroBean.getFechaMatricula());
                Integer filtroAnio = calendar.get(Calendar.YEAR);
//                Calendar miCalendario = Calendar.getInstance();
//                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR));
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
                if (matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudianteMasivoBean = matriculaService.obtenerTablaParaExcel(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, matriculaFiltroBean.getFechaInicio(), matriculaFiltroBean.getFechaFin(), matricula);
                    if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

//    public void obtenerListasExcel() {
//        try {
////            if (matriculaListadoAlBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null
////                    && !matriculaListadoAlBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)
////                    && matriculaListadoAlBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico()!= null
////                    && !matriculaListadoAlBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico().equals(0)
////                    
////                    ) {
////                
////            }
//            MatriculaService matriculaService = BeanFactory.getMatriculaService();
//            MatriculaBean matrix = new MatriculaBean();
//            matrix.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaListadoAlBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
//            matrix.getEstudianteBean().getGradoHabilitado().setIdGradoAcademico(matriculaListadoAlBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico());
//            matrix.setSeccion(matriculaListadoAlBean.getSeccion());
//            matrix.setAnio(matriculaListadoAlBean.getAnio());
//            matrix.setMatricula(matriculaListadoAlBean.getMatricula());
//            matrix.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerListaEstudiantesMatr(matrix);
//            if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
//                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                listaMatriculaEstudianteMasivoBean = new ArrayList<>();
//            }
//            this.flgLista = true;
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//    }
    public void exportXlsLista() throws IOException {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();

            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteListaAlumnos.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<ListaAlumnosRepBean> listaAlumnos = new ArrayList<>();
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer anio = matriculaListadoAlBean.getAnio();
            Integer flgMa = matricula;
            Integer grado = matriculaListadoAlBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico();
            String seccion = matriculaListadoAlBean.getSeccion();
            Integer nivel = matriculaListadoAlBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico();
            listaAlumnos = matriculaService.obtenerListaEstudiantesMatr(uniNeg, anio, flgMa, grado, nivel, seccion);
            if (!listaAlumnos.isEmpty()) {
                for (int g = 0; g < listaAlumnos.size(); g++) {
                    List<ListaAlumnosRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = matriculaService.obtenerListaEstudiantesMatrDetalle(uniNeg, anio, flgMa,
                            listaAlumnos.get(g).getIdGrado(), listaAlumnos.get(g).getIdNivel(),
                            listaAlumnos.get(g).getSeccion());
                    listaAlumnos.get(g).setListaDetalle(listaDetalle);
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaAlumnos);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
//            JasperReport report = JasperCompileManager.compileReport(rutaRep + "\\" + narchivo);
            JasperPrint print = JasperFillManager.fillReport(reporte, parametros, jrbc);
            OutputStream out = response.getOutputStream();
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            JRXlsExporter exporterXLS = new JRXlsExporter();

            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.exportReport();

            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=Listado_Estudiantes" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".xls");
            response.setContentType("application/vnd.ms-excel");
            response.setContentLength(arrayOutputStream.toByteArray().length);
//            out = response.getOutputStream();
//
            out.write(arrayOutputStream.toByteArray());
            out.flush();
            out.close();

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }

    }

    public void obtenerFiltroEstudianteMasivoAfter() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodosAfter == true) {
                matriculaFiltroAfterBean.setAnioFin(null);
                listaMatriculaEstudianteMasivoAfterBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroAfterBean);
            } else {
                Calendar miCalendario = Calendar.getInstance();
                matriculaFiltroAfterBean.setAnioFin(miCalendario.get(Calendar.YEAR));
                if (matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                    matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getIdPersona());
                    estado = 1;
                }
                if (matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApepat());
                    estado = 1;
                }
                if (matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getApemat());
                    estado = 1;
                }
                if (matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroAfterBean.getEstudianteBean().getPersonaBean().getNombre());
                    estado = 1;
                }
                if (matriculaFiltroAfterBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroAfterBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroAfterBean.getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroAfterBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroAfterBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !matriculaFiltroAfterBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroAfterBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaFiltroAfterBean.getGradoAcademicoBean().getIdGradoAcademico());
                    estado = 1;
                } else if (estado == 0 && flgTodosAfter == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudianteMasivoAfterBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudianteMasivoAfterBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroAfterBean);
                    if (listaMatriculaEstudianteMasivoAfterBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoAfterBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudianteMasivoBean = new ArrayList<>();
        flgEstEsp = false;
        flgPorNivelGrado = false;
        flgTodos = false;
//        setFlgMatriPend(0);
//        setFlgReProceso(0);

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerGradoAcaBasicaLista() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaListadoAlBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirEstudianteGenPdf() {
        ServletOutputStream out = null;
        try {

            EntidadBean entidadBean = new EntidadBean();
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            entidadBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
//            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteEstudianteGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            List<EstudianteGeneralRepBean> listaEstudianteGeneralRepBean = new ArrayList<>();
            listaEstudianteGeneralRepBean = matriculaService.obtenerImpresionPorDeUsuarioMatPorGrado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnio(), matriculaFiltroBean.getCreaPor(), matriculaFiltroBean.getFechaInicio(), matriculaFiltroBean.getFechaFin(), matriculaFiltroBean);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteGeneralRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirEstudianteGenPorUsuPdf() {
        ServletOutputStream out = null;
        try {

//            EntidadBean entidadBean = new EntidadBean();
//            EntidadService entidadService = BeanFactory.getEntidadService();
//            entidadBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            entidadBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
//            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteEstudianteGeneralUsu.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<EstudianteGeneralRepBean> listaEstudianteGeneralRepBean = new ArrayList<>();
            for (MatriculaBean rep : listaMatriculaEstudianteMasivoBean) {
                EstudianteGeneralRepBean estudianteGeneralRepBean = new EstudianteGeneralRepBean();
                estudianteGeneralRepBean.setNomUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getNombreUniNeg());
                estudianteGeneralRepBean.setTotal(listaMatriculaEstudianteMasivoBean.size());
//                estudianteGeneralRepBean.setDireccion("ddd");
//                estudianteGeneralRepBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
                estudianteGeneralRepBean.setIdestudiante(rep.getEstudianteBean().getIdEstudiante());
                estudianteGeneralRepBean.setNombre(rep.getEstudianteBean().getPersonaBean().getNombreCompleto());
                estudianteGeneralRepBean.setGrado(rep.getGradoAcademicoBean().getNombre());
                estudianteGeneralRepBean.setSeccion(rep.getSeccion());
//                estudianteGeneralRepBean.setEstado(rep.getFlgMatriVista());
                String i = rep.getFechaInicio().toString();
                String f = rep.getFechaInicio().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date1 = sdf.format(i);
                String date2 = sdf.format(f);
//                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp());
//                estudianteGeneralRepBean.setFechaInicio(date1);
//                estudianteGeneralRepBean.setFechaFin(date2);
                listaEstudianteGeneralRepBean.add(estudianteGeneralRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteGeneralRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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
    }

    public void imprimirFormatoPdf2() {
        ServletOutputStream out = null;
        try {
            matriculaFiltroBean.setAnioFin(null);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(matriculaFiltroBean.getFechaMatricula());
            Integer filtroAnio = calendar.get(Calendar.YEAR);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteEstudianteGeneralUsu.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EstudianteGeneralRepBean> listaEstudianteGeneralRepBean = new ArrayList<>();
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerTablaParaExcel(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), filtroAnio, matriculaFiltroBean.getFechaInicio(), matriculaFiltroBean.getFechaFin(), matricula);
            for (MatriculaBean rep : listaMatriculaEstudianteMasivoBean) {
                rep = matriculaFiltroBean;
                EstudianteGeneralRepBean estudianteGeneralRepBean = new EstudianteGeneralRepBean();
                estudianteGeneralRepBean.setNomUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getNombreUniNeg());
                estudianteGeneralRepBean.setUsuario(rep.getCreaPor());
                estudianteGeneralRepBean.setTotal(listaMatriculaEstudianteMasivoBean.size());
//                estudianteGeneralRepBean.setDireccion("ddd");
//                estudianteGeneralRepBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
                estudianteGeneralRepBean.setIdestudiante(rep.getEstudianteBean().getIdEstudiante());
                estudianteGeneralRepBean.setNombre(rep.getEstudianteBean().getPersonaBean().getNombreCompleto());
                estudianteGeneralRepBean.setGrado(rep.getGradoAcademicoBean().getNombre());
                estudianteGeneralRepBean.setSeccion(rep.getSeccion());
//                String i = matriculaFiltroBean.getFechaInicio().toString();

                if (!rep.getFechaMatricula().equals(null)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String f = sdf.format(rep.getFechaMatricula());
                    estudianteGeneralRepBean.setFechaMatricula(f.toString());
                } else {
                    estudianteGeneralRepBean.setFechaMatricula("-");
                }

                if (getFechaI() != null) {
                    estudianteGeneralRepBean.setFechaInicio(getFechaI());
                }
                if (getFechaF() != null) {
                    estudianteGeneralRepBean.setFechaFin(getFechaF());
                }
//                estudianteGeneralRepBean.setFechaInicio(date1);
//                estudianteGeneralRepBean.setFechaFin(date2);
                listaEstudianteGeneralRepBean.add(estudianteGeneralRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteGeneralRepBean);
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
    //    public void imprimirEstudiantePorIdPdf(EstudianteBean estudianteBean) {
    //        ServletOutputStream out = null;
    //        try {
    //            EstudianteService estudianteService = BeanFactory.getEstudianteService();
    //            List<EstudianteRepBean> listaEstudianteRepBean = estudianteService.obtenerEstudianteRepPorId(estudianteBean);
    //            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    //            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/repFichaEstudiante.jasper");
    //            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    //            String absoluteWebPath = externalContext.getRealPath("/");
    //            File file = new File(archivoJasper);
    //            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
    //            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteRepBean);
    //            Map<String, Object> parametros = new HashMap<>();
    //            String ruta = absoluteWebPath + "reportes\\jasper\\";
    //            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
    //            parametros.put("SUBREPORT_DIR", ruta);
    //            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
    //            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
    //            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
    //            response.reset();
    //            response.setContentType("application/pdf");
    //            response.setContentLength(bytes.length);
    //            out = response.getOutputStream();
    //            out.write(bytes);
    //            out.flush(); 
    //        } catch (Exception e) {
    //            new MensajePrime().addErrorGeneralMessage();
    //            GLTLog.writeError(this.getClass(), e);
    //        } finally {
    //            try {
    //                if (out != null) {
    //                    out.close();
    //                }
    //            } catch (Exception ettt) {
    //                new MensajePrime().addErrorGeneralMessage();
    //                GLTLog.writeError(this.getClass(), ettt);
    //            }
    //        }
    //    }

    public void imprimirTodosPdf(String idEstudiante, String uniNeg) {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteFichaEst.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<FichaEstudianteRepBean> listaFichaEstCabeceraRepBean = new ArrayList<>();
            listaFichaEstCabeceraRepBean = estudianteService.obtenerFichaEstudianteCabecera(idEstudiante, uniNeg);

            if (!listaFichaEstCabeceraRepBean.isEmpty()) {
                for (int i = 0; i < listaFichaEstCabeceraRepBean.size(); i++) {
                    List<FamiliarEstudianteRepBean> listaFamiliares = new ArrayList<>();
                    listaFamiliares = estudianteService.obtenerDetPadres(idEstudiante, uniNeg);
                    listaFichaEstCabeceraRepBean.get(0).setListaPadres(listaFamiliares);
                    if (!listaFamiliares.isEmpty()) {
                        for (int j = 0; j < listaFichaEstCabeceraRepBean.get(0).getListaPadres().getData().size(); j++) {
                            List<ResponsableEconomicoRepBean> listaResponsableEconomicoRepBeans = new ArrayList<>();
                            listaResponsableEconomicoRepBeans = estudianteService.obtenerResponEconomico(idEstudiante, uniNeg);
                            listaFamiliares.get(j).setListaResEco(listaResponsableEconomicoRepBeans);

                            List<FamiliarRepBean> familiarRepBean = new ArrayList<>();
                            familiarRepBean = familiarService.obtenerFamiliarRep(idEstudiante, listaFamiliares.get(j).getIdFamiliar(), listaFamiliares.get(j).getTipoParentesco(), uniNeg);
                            listaFamiliares.get(j).setListaFamiliares(familiarRepBean);
                            listaFichaEstCabeceraRepBean.get(0).setListaPadres(listaFamiliares);
                        }
                    }
                }

                //responsableEconomico
//                for (int i = 0; i < listaFichaEstCabeceraRepBean.size(); i++) {
//                    List<ResponsableEconomicoRepBean> listaResponsableEconomicoRepBeans = new ArrayList<>();
//                    listaResponsableEconomicoRepBeans = estudianteService.obtenerResponEconomico(idEstudiante, uniNeg);
//                    listaFichaEstCabeceraRepBean.get(0).setListaResEco(listaResponsableEconomicoRepBeans);
//                }
                //fin
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaFichaEstCabeceraRepBean);
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

    public void imprimirTodosPdfIngrRetirTotal() {
        ServletOutputStream out = null;

        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTotalAlumnosIngresantesRetirados.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer anio = matriculaBean.getAnio();
            Date fechaInicioClases = matriculaBean.getFechaInicioClases();
            Date fechaFinFiltro = matriculaBean.getFechaFinFiltro();
            String usuario = usuarioLogin.getUsuario();
            List<AlumnosIngresantesRetiradosRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = matriculaService.obtenerCabeceraIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro);
            listaCabecera.get(0).setUsuario(usuario);
            if (!listaCabecera.isEmpty()) {
//                for (int m = 0; m < listaCabecera.get(0).getListaNiveles().getData().size(); m++) {
                List<AlumnosIngresantesRetiradosRepBean> listaNiveles = new ArrayList<>();
                listaNiveles = matriculaService.obtenerNivelesIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro);
                listaCabecera.get(0).setListaNiveles(listaNiveles);
                if (!listaNiveles.isEmpty()) {
                    for (int i = 0; i < listaNiveles.size(); i++) {
                        List<AlumnosIngresantesRetiradosRepBean> listaDetalle = new ArrayList<>();
                        listaDetalle = matriculaService.obtenerDetalleIngresantesRetiradosTotal(
                                uniNeg, anio, fechaInicioClases, fechaFinFiltro, listaNiveles.get(i).getIdNivelAcademico());
                        listaNiveles.get(i).setListaDetalle(listaDetalle);
                        listaCabecera.get(0).setListaNiveles(listaNiveles);
                        if (!listaDetalle.isEmpty()) {
                            for (int j = 0; j < listaDetalle.size(); j++) {
                                List<AlumnosIngresantesRetiradosRepBean> listaSubDetalle = new ArrayList<>();
                                listaSubDetalle = matriculaService.obtenerSubDetalleIngresantesRetiradosTotal(
                                        uniNeg, anio, fechaInicioClases, fechaFinFiltro, listaDetalle.get(j).getIdGrado());
                                listaDetalle.get(j).setListaSubDetalle(listaSubDetalle);
                                listaNiveles.get(i).setListaDetalle(listaDetalle);
                                listaCabecera.get(0).setListaNiveles(listaNiveles);
                            }
//                            }
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirTodosPdfFichaClinica(String idEstudiante, String uniNeg) {
        ServletOutputStream out = null;
        Integer id = 0;

        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteAlergiaService estudianteAlergiasService = BeanFactory.getEstudianteAlergiaService();
            EstudianteNacimientoService estudianteNacimientoService = BeanFactory.getEstudianteNacimientoService();
            EstudianteVacunaService estudianteVacunaService = BeanFactory.getEstudianteVacunaService();
            EstudianteMedicamentoService estudianteMedicamentoService = BeanFactory.getEstudianteMedicamentoService();
            EstudianteEnfermedadService estudianteEnfermedadService = BeanFactory.getEstudianteEnfermedadService();
            EstudianteTraumaService estudianteTraumaService = BeanFactory.getEstudianteTraumaService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteFichaClinicaEstudiante.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<FichaClinicaEstudianteRepBean> listaFichaClinicaEstudianteRepBean = new ArrayList<>();
            listaFichaClinicaEstudianteRepBean = estudianteService.obtenerFichaClinicaEstudiante(idEstudiante, uniNeg);

            if (!listaFichaClinicaEstudianteRepBean.isEmpty()) {
                for (int i = 0; i < listaFichaClinicaEstudianteRepBean.size(); i++) {
                    List<EstudianteAlergiasRepBean> listaEstudianteAlergiasRepBean = new ArrayList<>();
                    listaEstudianteAlergiasRepBean = estudianteAlergiasService.obtenerAlergias(idEstudiante, uniNeg);
                    listaFichaClinicaEstudianteRepBean.get(0).setListaAlergias(listaEstudianteAlergiasRepBean);
                    if (!listaEstudianteAlergiasRepBean.isEmpty()) {
                        for (int j = 0; j < listaFichaClinicaEstudianteRepBean.get(0).getListaAlergias().getData().size(); j++) {
                            List<EstudianteNacimientoRepBean> listaEstudianteNacimientoRepBean = new ArrayList<>();
                            listaEstudianteNacimientoRepBean = estudianteNacimientoService.obtenerEstudianteNacimiento(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaNacimiento(listaEstudianteNacimientoRepBean);
                            //Vacunas
                            List<EstudianteVacunasRepBean> listaEstudianteVacunasRepBean = new ArrayList<>();
                            listaEstudianteVacunasRepBean = estudianteVacunaService.obtenerVacunas(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaVacunas(listaEstudianteVacunasRepBean);
                            listaFichaClinicaEstudianteRepBean.get(0).setListaAlergias(listaEstudianteAlergiasRepBean);
                            //Medicamentos
                            List<EstudianteMedicamentosRepBean> listaEstudianteMedicamentosRepBean = new ArrayList<>();
                            listaEstudianteMedicamentosRepBean = estudianteMedicamentoService.obtenerMedicamentos(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaMedicamentos(listaEstudianteMedicamentosRepBean);
                            listaFichaClinicaEstudianteRepBean.get(0).setListaAlergias(listaEstudianteAlergiasRepBean);
                            //Enfermedades
                            List<EstudianteEnfermedadRepBean> list = new ArrayList<>();
                            list = estudianteEnfermedadService.obtenerEstudianteEnfermedad(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaEnfermedades(list);
                            listaFichaClinicaEstudianteRepBean.get(0).setListaAlergias(listaEstudianteAlergiasRepBean);
                            //Enfermedad Padres
                            List<EstudianteEnfermedadPadresRepBean> estudianteEnfermedadPadresRepBean = new ArrayList<>();
                            estudianteEnfermedadPadresRepBean = estudianteService.obtenerEstudianteInfoEnfPadres(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaEnfermedadPadres(estudianteEnfermedadPadresRepBean);
                            //Traumas
                            List<EstudianteTraumaRepBean> listaEstudianteTraumaRepBean = new ArrayList<>();
                            listaEstudianteTraumaRepBean = estudianteTraumaService.obtenerTraumas(idEstudiante, uniNeg);
                            listaEstudianteAlergiasRepBean.get(j).setListaTraumas(listaEstudianteTraumaRepBean);
                            listaFichaClinicaEstudianteRepBean.get(0).setListaAlergias(listaEstudianteAlergiasRepBean);
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaFichaClinicaEstudianteRepBean);
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

    public void imprimirEstudiantePorIdPdf(EstudianteBean estudianteBean) {
        ServletOutputStream out = null;
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            List<EstudianteRepBean> listaEstudianteRepBean = estudianteService.obtenerEstudianteRepPorId(estudianteBean);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/repFichaEstudiante.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteRepBean);
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

    public void imprimirEstudianteGenPdf(Object object) {
        ServletOutputStream out = null;
        try {
            MatriculaBean matriculaBean = new MatriculaBean();
            EntidadBean entidadBean = new EntidadBean();
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            entidadBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
            entidadBean = entidadService.obtenerEntidadPorId(entidadBean);

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reoporteEstudianteGeneral.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<EstudianteGeneralRepBean> listaEstudianteGeneralRepBean = new ArrayList<>();
            for (MatriculaBean rep : listaMatriculaEstudianteMasivoBean) {
                EstudianteGeneralRepBean estudianteGeneralRepBean = new EstudianteGeneralRepBean();
                estudianteGeneralRepBean.setNomUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudianteGeneralRepBean.setTotal(0);
                estudianteGeneralRepBean.setDireccion("ddd");
                estudianteGeneralRepBean.setRuc(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getRuc());
                estudianteGeneralRepBean.setIdestudiante(rep.getEstudianteBean().getIdEstudiante());
                estudianteGeneralRepBean.setNombre(rep.getEstudianteBean().getPersonaBean().getNombreCompleto());
                estudianteGeneralRepBean.setGrado(rep.getGradoAcademicoVista());
                estudianteGeneralRepBean.setSeccion(rep.getSeccion());
                estudianteGeneralRepBean.setEstado(rep.getFlgMatriVista());
                listaEstudianteGeneralRepBean.add(estudianteGeneralRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEstudianteGeneralRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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
    }

    ///////////reporte
    public void imprimirEstadisticaMatricula(Integer anio, Integer flg, Date fechaInicio, Date fechaFin) {
        ServletOutputStream out = null;
        try {
//            Integer flg = 1; 
            String tipFor = MaristaConstantes.TIP_FOR_BAS;
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteEstadisticaMatriculaInsc.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            //0.nivels
            List<EstMatriculaRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = matriculaService.obtenerEstadisticaMatriculaCabecera(uniNeg, anio, tipFor, flg, fechaInicio, fechaFin);
            //1.-Lista de niveles
            if (!listaCabecera.isEmpty()) {
                for (int g = 0; g < listaCabecera.size(); g++) {
                    List<EstMatriculaNivelRepBean> listaNiveles = new ArrayList<>();
                    listaNiveles = matriculaService.obtenerNiveles(uniNeg, anio, tipFor, flg, fechaInicio, fechaFin);
                    listaCabecera.get(0).setListaNiveles(listaNiveles);
                    //2.-Obtener gradoPorNiveles
                    if (!listaNiveles.isEmpty()) {
                        for (int i = 0; i < listaNiveles.size(); i++) {
                            List<EstMatriculaGradoAcaRepBean> listaGradoAcaPorNivel = new ArrayList<>();
                            listaGradoAcaPorNivel = matriculaService.obtenerGradosAcademicoPorNivel(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel(), flg, fechaInicio, fechaFin);
                            listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
                            listaCabecera.get(0).setListaNiveles(listaNiveles);
                            //3.- obtener secciones por grado academico       
                            if (!listaGradoAcaPorNivel.isEmpty()) {
                                for (int j = 0; j < listaGradoAcaPorNivel.size(); j++) {
                                    List<EstMatriculaSeccGradoAcaRepBean> listaSeccionesPorGradoAca = new ArrayList<>();
                                    listaSeccionesPorGradoAca = matriculaService.obtenerSeccionesPorGradoAca(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel(), listaGradoAcaPorNivel.get(j).getIdGradoAcademico(), flg, fechaInicio, fechaFin);
                                    listaGradoAcaPorNivel.get(j).setListaGradoAcaSeccion(listaSeccionesPorGradoAca);
                                    listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
                                    listaCabecera.get(0).setListaNiveles(listaNiveles);
                                    //4. obtener detalle por seccion
                                    if (!listaSeccionesPorGradoAca.isEmpty()) {
                                        for (int k = 0; k < listaSeccionesPorGradoAca.size(); k++) {
                                            List<EstMatriculaSeccionRepBean> listaDetSeccionPorGradoAcaySecc = new ArrayList<>();
                                            listaDetSeccionPorGradoAcaySecc = matriculaService.obtenerDetSeccionPorGradoAca(uniNeg, anio, tipFor, listaNiveles.get(i).getNivel(), listaGradoAcaPorNivel.get(j).getIdGradoAcademico(), listaSeccionesPorGradoAca.get(k).getSeccion(), flg, fechaInicio, fechaFin);
                                            listaSeccionesPorGradoAca.get(k).setListaSeccion(listaDetSeccionPorGradoAcaySecc);
                                            listaGradoAcaPorNivel.get(j).setListaGradoAcaSeccion(listaSeccionesPorGradoAca);
                                            listaNiveles.get(i).setListaGradoAcademico(listaGradoAcaPorNivel);
                                            listaCabecera.get(0).setListaNiveles(listaNiveles);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

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

    //Getter y Setter
    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
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

    public Boolean getFlgEstComprobanteMes() {
        if (flgEstComprobanteMes == null) {
            flgEstComprobanteMes = Boolean.FALSE;
        }
        return flgEstComprobanteMes;
    }

    public void setFlgEstComprobanteMes(Boolean flgEstComprobanteMes) {
        this.flgEstComprobanteMes = flgEstComprobanteMes;
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

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
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

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
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

    public UsuarioBean getUsuarioLogin() {
        if (usuarioLogin == null) {
            usuarioLogin = new UsuarioBean();
        }
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
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

    public List<MatriculaBean> getListaMatriculaEstudianteMasivoAfterBean() {
        if (listaMatriculaEstudianteMasivoAfterBean == null) {
            listaMatriculaEstudianteMasivoAfterBean = new ArrayList<>();
        }
        return listaMatriculaEstudianteMasivoAfterBean;
    }

    public void setListaMatriculaEstudianteMasivoAfterBean(List<MatriculaBean> listaMatriculaEstudianteMasivoAfterBean) {
        this.listaMatriculaEstudianteMasivoAfterBean = listaMatriculaEstudianteMasivoAfterBean;
    }

    public MatriculaBean getMatriculaFiltroAfterBean() {
        if (matriculaFiltroAfterBean == null) {
            matriculaFiltroAfterBean = new MatriculaBean();
        }
        return matriculaFiltroAfterBean;
    }

    public void setMatriculaFiltroAfterBean(MatriculaBean matriculaFiltroAfterBean) {
        this.matriculaFiltroAfterBean = matriculaFiltroAfterBean;
    }

    public String getFechaI() {
        return fechaI;
    }

    public void setFechaI(String fechaI) {
        this.fechaI = fechaI;
    }

    public String getFechaF() {
        return fechaF;
    }

    public void setFechaF(String fechaF) {
        this.fechaF = fechaF;
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

    public FamiliarBean getFamiliarBean() {
        if (familiarBean == null) {
            familiarBean = new FamiliarBean();
        }
        return familiarBean;
    }

    public void setFamiliarBean(FamiliarBean familiarBean) {
        this.familiarBean = familiarBean;
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

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public Calendar getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Calendar fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public Map<String, Integer> getListaMatricula() {
        return listaMatricula;
    }

    public void setListaMatricula(Map<String, Integer> listaMatricula) {
        this.listaMatricula = listaMatricula;
    }

    public MatriculaBean getMatriculaListadoAlBean() {
        if (matriculaListadoAlBean == null) {
            matriculaListadoAlBean = new MatriculaBean();
        }
        return matriculaListadoAlBean;
    }

    public void setMatriculaListadoAlBean(MatriculaBean matriculaListadoAlBean) {
        this.matriculaListadoAlBean = matriculaListadoAlBean;
    }

    public Boolean getFlgLista() {
        return flgLista;
    }

    public void setFlgLista(Boolean flgLista) {
        this.flgLista = flgLista;
    }

    public Map<String, Integer> getListaMatriculaOpciones() {
        return listaMatriculaOpciones;
    }

    public void setListaMatriculaOpciones(Map<String, Integer> listaMatriculaOpciones) {
        this.listaMatriculaOpciones = listaMatriculaOpciones;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

}
