package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetEsquelaBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.NotiMasivaRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DetEsquelaService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EsquelaService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class EnvioMasivoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void EnvioMasivoMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaAnioFiltroMatricula();
            for (int i = Calendar.getInstance().get(Calendar.YEAR) - 2; i <= Calendar.getInstance().get(Calendar.YEAR) + 2; i++) {
                listaAnioFiltroMatricula.add(i);
            }

            //LISTA DE GRADOS ACADEMICOS
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();

            //LISTA DE NIVEL ACADEMICO
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademico();
            listaNivelAcademico = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            //OBTENIENDO MATRICULA
            getMatriculaFiltroBean();
            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnioIni(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);
            getViewMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);

            UnidadOrganicaService unidadOrganciOrganicaService = BeanFactory.getUnidadOrganicaService();
            getListaUnidadOrganicaBean();
            listaUnidadOrganicaBean = unidadOrganciOrganicaService.obtenerTodos();

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCodigoBean();
            listaCodigoBean = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MATRICULA));

            //PERSONAL
            getPersonalFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //PERSONA-EXTERNA
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //ENTIDAD
            getEntidadFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //FILTRO DE PAGANTES
            setTipPagante(1);
            cambiarFiltro();

            //CARGAR FLG TODOS
            cargarFlgTodos();

            //OBTENER MESES
            listaMesesForSup();

            //LISTA TIPO NIVEL ACADEMICO
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            //TIPO DE ACCION DE ENVIO
            setTipAccion(0);

            //OBTENER VISTA DE ENVIO
            flgRedactar = true;

            getListaTipoEstado();
            listaTipoEstado = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE));

            getIdTipoEstado();

            //TIPO DE AVISO DE ESQUELA
            getListaTipoAviso();
            listaTipoAviso = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ESQUELA));

            getEsquelaBean();
            //DATOS DE CUENTAS POR COBRAR
            getCuentasPorCobrar();
            getCuentasPorCobrar().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            /* CARGAR ESQUELA DE CONF. */
            setMensajeObj("");

            /* LISTA DE MESES */
            getListaMesAll();
            obtenerMeses();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES
    private MatriculaBean matriculaFiltroBean;
    private EsquelaBean esquelaBean;
    private UsuarioBean usuarioLogin;
    private ViewMatriculaBean viewMatriculaBean;
    private PersonalBean personalFiltroBean;
    private PersonaBean personaFiltroBean;
    private EntidadBean entidadFiltroBean;
    private CuentasPorCobrarBean cuentasPorCobrar;
    private DetEsquelaBean detEsquelaBean;

    //VARIABLES DE MENSAJE
    private String mensajeObj = "";
    private String obj = "";
    private String tituloObj = "";

    //LISTAS
    private List<MatriculaBean> listaMatriculaEstudianteMasivoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean;
    private Integer[] listaMeses;
    private Map<String, Integer> listaMesesExpMap;
    private List<EsquelaBean> listaEsquelaBean;
    private List<NivelAcademicoBean> listaNivelAcademico;
    private List<ViewMatriculaBean> listaViewMatriculaBean;
    private List<CodigoBean> listaCodigoBean;
    private List<PersonalBean> listaPersonalFiltroBean;
    private List<PersonaBean> listaPersonaFiltroBean;
    private List<EntidadBean> listaEntidadFiltroBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private List<Integer> listaAnioFiltroMatricula;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<MatriculaBean> listaMatriculaPrevioBean;
    private List<DetEsquelaBean> listaDetEsquelaBean;
    private List<CodigoBean> listaTipoEstado;
    private List<CodigoBean> listaTipoAviso;
    private List<EsquelaBean> listaEsquelaAviso;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<MesBean> listaMesAll;

    //VARIABLES
    private Boolean flgEstSinPro;
    private Boolean flgPa;
    private Boolean flgMa;
    private Boolean flgAp;
    private Boolean flgResPa;
    private Integer anio;
    private Boolean renderEstudiante;
    private Boolean renderPersonal;
    private Boolean renderPersona;
    private Boolean renderEntidad;
    private Integer tipPagante = 0;
    private Integer tipAccion;
    private Integer flgMesPension = 0;
    private Integer mesSelect;

    private Boolean flgByCole = false;

    //FLG ESTUDIANTES
    private Boolean flgTodos;
    private Boolean flgEstComprobanteMes;
    private Boolean flgEstEsp;
    private Boolean flgPorNivelGrado;
    private Boolean flgPension;

    //FLG PERSONAL
    private Boolean flgTodosPer;
    private Boolean flgPerComprobanteMes;
    private Boolean flgPerEsp;
    private Boolean flgPerUniOrg;

    //FLG EXTERNO
    private Boolean flgTodosExt;
    private Boolean flgExtEsp;

    //FlG ENTIDAD
    private Boolean flgTodosEnt;
    private Boolean flgEntEsp;

    //FLG RENDER MASIVOS
    private Boolean flgRedactar;
    private Boolean flgEnviado;
    private Boolean flgImportante;
    private Boolean flgBorrador;

    //TIPO DE ESTADO CUENTA
    private Integer idTipoEstado;

    //METODOS
    public void cargarFlgTodos() {
        try {
            flgTodos = true;
            flgTodosEnt = true;
            flgTodosExt = true;
            flgTodosPer = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarFiltro() {
        try {
            if (tipPagante.equals(1)) {
                flgTodos = false;
                renderEstudiante = true;
                renderPersonal = false;
                renderPersona = false;
                renderEntidad = false;

                //PERSONAL
                personalFiltroBean = new PersonalBean();
                listaPersonalFiltroBean = new ArrayList<>();
                //EXTERNO
                personaFiltroBean = new PersonaBean();
                listaPersonaFiltroBean = new ArrayList<>();
                //ENTIDAD
                entidadFiltroBean = new EntidadBean();
                listaEntidadFiltroBean = new ArrayList<>();
                cargarDatos();
            } else if (tipPagante.equals(2)) {
                flgTodosPer = false;
                renderEstudiante = false;
                renderPersonal = true;
                renderPersona = false;
                renderEntidad = false;

                //ESTUDIANTE
                matriculaFiltroBean = new MatriculaBean();
                listaMatriculaEstudianteMasivoBean = new ArrayList<>();

                //EXTERNO
                personaFiltroBean = new PersonaBean();
                listaPersonaFiltroBean = new ArrayList<>();
                //ENTIDAD
                entidadFiltroBean = new EntidadBean();
                listaEntidadFiltroBean = new ArrayList<>();
                cargarDatos();
                flgPension = false;
            } else if (tipPagante.equals(3)) {
                flgTodosExt = false;
                renderEstudiante = false;
                renderPersonal = false;
                renderPersona = true;
                renderEntidad = false;

                //ESTUDIANTE
                matriculaFiltroBean = new MatriculaBean();
                listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                //PERSONAL
                personalFiltroBean = new PersonalBean();
                listaPersonalFiltroBean = new ArrayList<>();
                //ENTIDAD
                entidadFiltroBean = new EntidadBean();
                listaEntidadFiltroBean = new ArrayList<>();
                cargarDatos();
                flgPension = false;
            } else if (tipPagante.equals(4)) {
                flgTodosEnt = false;
                renderEstudiante = false;
                renderPersonal = false;
                renderPersona = false;
                renderEntidad = true;

                //ESTUDIANTE
                matriculaFiltroBean = new MatriculaBean();
                listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                //PERSONAL
                personalFiltroBean = new PersonalBean();
                listaPersonalFiltroBean = new ArrayList<>();
                //EXTERNO
                personaFiltroBean = new PersonaBean();
                listaPersonaFiltroBean = new ArrayList<>();
                cargarDatos();
                flgPension = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatos() {
        try {
            //MATRICULA FILTRO 
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //PERSONAL FILTRO
            getPersonalFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //PERSONA FILTRO
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //ENTIDAD FILTRO
            getEntidadFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
//                this.flgPension = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
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
//                this.flgPension = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
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
//                this.flgPension = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFlgPension() {
        try {
            getFlgPension();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltro(Integer objeto) {
        try {
            if (objeto.equals(1)) {
                obtenerFiltroEstudianteMasivo();
            } else if (objeto.equals(2)) {
                obtenerPersonalFiltroMasivo();
            } else if (objeto.equals(3)) {
                obtenerExternoFiltroMasivo();
            } else if (objeto.equals(4)) {
                obtenerEntidadFiltroMasivo();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            System.out.println(">>>>" + matriculaFiltroBean.getFlgMatriculaVista());
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                if (!getFlgPension()) {
                    System.out.println(">>>>>> Filtro sin pension");
                    listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroBean);
                } else if (getFlgPension()) {
                    if (cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo() != null && cuentasPorCobrar.getTipoRecep() != null) {
                        System.out.println(">>>>> Filtro con pension");
                        cuentasPorCobrar.getIdTipoStatusCtaCte().setIdCodigo(cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo());
                        matriculaFiltroBean.setFlgMatriculaVista(matriculaFiltroBean.getFlgMatriculaVista());
                        matriculaFiltroBean.setIdTipoEstado(cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo());
//                        if (flgByCole = false) {
//                            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaFiltroBean, listaMeses);
//                        } else {
//                            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeudaCole(matriculaFiltroBean, listaMeses);
//                        }
                        listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaFiltroBean, listaMeses);
                    } else if (cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo() == null || cuentasPorCobrar.getTipoRecep() != null) {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                    }
                }
            } else {
                Calendar miCalendario = Calendar.getInstance();
                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR));
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
                    if (!getFlgPension()) {
                        System.out.println(">>>>>> Filtro sin pension");
                        listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroBean);
                    } else if (getFlgPension()) {
                        if (cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo() != null) {
                            System.out.println(">>>>> Filtro con pension");
                            cuentasPorCobrar.getIdTipoStatusCtaCte().setIdCodigo(cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo());
                            matriculaFiltroBean.setFlgMatriculaVista(matriculaFiltroBean.getFlgMatriculaVista());
                            matriculaFiltroBean.setIdTipoEstado(cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo());
//                            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaFiltroBean, listaMeses);
//                            if (flgByCole = false) {
//                                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaFiltroBean, listaMeses);
//                            } else {
//                                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeudaCole(matriculaFiltroBean, listaMeses);
//                            }
                            listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaFiltroBean, listaMeses);
                        } else if (cuentasPorCobrar.getIdTipoStatusCtaCte().getIdCodigo() == null) {
                            new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                            listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                        }
                    }
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

    public void obtenerPersonalFiltroMasivo() {
        try {
            Integer res = 0;
            PersonalService personalService = BeanFactory.getPersonalService();
            if (flgTodosPer) {
                listaPersonalFiltroBean = personalService.obtenerPorUnidadNegocio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            } else if (!flgTodosPer) {
                if (personalFiltroBean.getNombre() != null && !personalFiltroBean.getNombre().equals("")) {
                    personalFiltroBean.setNombre(personalFiltroBean.getNombre());
                    res = 1;
                }
                if (personalFiltroBean.getApepat() != null && !personalFiltroBean.getApepat().equals("")) {
                    personalFiltroBean.setApepat(personalFiltroBean.getApepat());
                    res = 1;
                }
                if (personalFiltroBean.getApemat() != null && !personalFiltroBean.getApemat().equals("")) {
                    personalFiltroBean.setApemat(personalFiltroBean.getApemat());
                    res = 1;
                }
                if (personalFiltroBean.getNroDoc() != null && !personalFiltroBean.getNroDoc().equals("")) {
                    personalFiltroBean.setNroDoc(personalFiltroBean.getNroDoc());
                    res = 1;
                }
                if (personalFiltroBean.getCodPer() != null && !personalFiltroBean.getCodPer().equals("")) {
                    personalFiltroBean.setNroDoc(personalFiltroBean.getCodPer());
                    res = 1;
                }
                if (res == 1) {
                    listaPersonalFiltroBean = personalService.filtrarPersonal(personalFiltroBean);
                    if (listaPersonalFiltroBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaPersonalFiltroBean = new ArrayList<>();
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaPersonalFiltroBean = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerExternoFiltroMasivo() {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            Integer res = 0;
            if (flgTodosExt) {
                listaPersonaFiltroBean = personaService.obtenerPersonaPorFiltroValorados(personaFiltroBean);
            } else if (!flgTodosExt) {
                if (personaFiltroBean.getIdPersona() != null && !personaFiltroBean.getIdPersona().equals("")) {
                    personaFiltroBean.setIdPersona(personaFiltroBean.getIdPersona());
                    res = 1;
                }
                if (personaFiltroBean.getNombre() != null && !personaFiltroBean.getNombre().equals("")) {
                    personaFiltroBean.setNombre(personaFiltroBean.getNombre());
                    res = 1;
                }
                if (personaFiltroBean.getApemat() != null && !personaFiltroBean.getApemat().equals("")) {
                    personaFiltroBean.setApemat(personaFiltroBean.getApemat());
                    res = 1;
                }
                if (personaFiltroBean.getApepat() != null && !personaFiltroBean.getApepat().equals("")) {
                    personaFiltroBean.setApepat(personaFiltroBean.getApepat());
                    res = 1;
                }
                if (res == 1) {
                    listaPersonaFiltroBean = personaService.obtenerPersonaPorFiltroValorados(personaFiltroBean);
                    if (listaPersonaFiltroBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaPersonaFiltroBean = new ArrayList<>();
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaPersonaFiltroBean = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEntidadFiltroMasivo() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            Integer res = 0;
            if (flgTodosEnt) {
                listaEntidadFiltroBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
            } else if (!flgTodosEnt) {
                if (entidadFiltroBean.getRuc() != null && !entidadFiltroBean.getRuc().equals("")) {
                    entidadFiltroBean.setRuc(entidadFiltroBean.getRuc());
                    res = 1;
                }
                if (entidadFiltroBean.getNombre() != null && !entidadFiltroBean.getNombre().equals("")) {
                    entidadFiltroBean.setNombre(entidadFiltroBean.getNombre());
                    res = 1;
                }
                if (res == 1) {
                    listaEntidadFiltroBean = entidadService.obtenerEntidadPorFiltro(entidadFiltroBean);
                    if (listaEntidadFiltroBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaEntidadFiltroBean = new ArrayList<>();
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaEntidadFiltroBean = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerLimpiaFiltro(Integer objeto) {
        try {
            if (objeto.equals(1)) {
                limpiarEstudianteMatriculaMasivo();
            } else if (objeto.equals(2)) {
                limpiarPersonalMasivo();
            } else if (objeto.equals(3)) {
                limpiarExternoMasivo();
            } else if (objeto.equals(4)) {
                limpiarEntidadMasivo();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        try {
            matriculaFiltroBean = new MatriculaBean();
            listaMatriculaEstudianteMasivoBean = new ArrayList<>();
            flgEstEsp = false;
            flgEstSinPro = false;
            flgPorNivelGrado = false;
            flgTodos = false;
            flgPension = false;
            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            getCuentasPorCobrar().setTipoRecep(null);
            getCuentasPorCobrar().getIdTipoStatusCtaCte().setIdCodigo(null);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalMasivo() {
        try {
            personalFiltroBean = new PersonalBean();
            listaPersonalFiltroBean = new ArrayList<>();
            flgPerEsp = false;
            flgPerUniOrg = false;
            flgTodosPer = false;
            Calendar miCalendario = Calendar.getInstance();
            getPersonalFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarExternoMasivo() {
        try {
            personaFiltroBean = new PersonaBean();
            listaPersonaFiltroBean = new ArrayList<>();
            flgExtEsp = false;
            flgTodosExt = false;
            Calendar miCalendario = Calendar.getInstance();
            getPersonaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarEntidadMasivo() {
        try {
            entidadFiltroBean = new EntidadBean();
            listaEntidadFiltroBean = new ArrayList<>();
            flgEntEsp = false;
            flgTodosEnt = false;
            Calendar miCalendario = Calendar.getInstance();
            getEntidadFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cambiarEstado1() {
        try {
            flgPa = true;
            flgMa = false;
            flgAp = false;
            flgResPa = false;
            esquelaBean.setFlgRecEnvio(1);
            esquelaBean.setFlgenviopapa(1);
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            listaEsquelaBean = esquelaService.obtenerEsquelaFiltro(esquelaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarEstado2() {
        try {
            flgMa = true;
            flgPa = false;
            flgAp = false;
            flgResPa = false;
            esquelaBean.setFlgRecEnvio(2);
            esquelaBean.setFlgenviomama(1);
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            listaEsquelaBean = esquelaService.obtenerEsquelaFiltro(esquelaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarEstado3() {
        try {
            flgAp = true;
            flgMa = false;
            flgPa = false;
            flgResPa = false;
            esquelaBean.setFlgRecEnvio(3);
            esquelaBean.setFlgenvioapo(1);
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            listaEsquelaBean = esquelaService.obtenerEsquelaFiltro(esquelaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarEstado4() {
        try {
            flgResPa = true;
            flgAp = false;
            flgMa = false;
            flgPa = false;
            esquelaBean.setFlgRecEnvio(4);
            esquelaBean.setFlgenviorespago(1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerAnioMat() {
        try {
            getMatriculaFiltroBean().setAnioIni(getMatriculaFiltroBean().getAnioIni());
            setAnio(getMatriculaFiltroBean().getAnioIni());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //PERSONAL
    public void verificarFiltroTodosPer() {
        try {
            if (this.flgTodosPer == true) {
                this.flgPerUniOrg = false;
                this.flgPerEsp = false;
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroPerEsp() {
        try {
            if (this.flgPerEsp == true) {
                this.flgTodosPer = false;
                this.flgPerUniOrg = false;
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroPerUniOrg() {
        try {
            if (this.flgPerUniOrg == true) {
                this.flgTodosPer = false;
                this.flgPerEsp = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //EXTERNO
    public void verificarFiltroTodosExt() {
        try {
            if (this.flgTodosExt == true) {
                this.flgExtEsp = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroExtEsp() {
        try {
            if (this.flgExtEsp == true) {
                this.flgTodosExt = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //ENTIDAD
    public void verificarFiltroTodosEnt() {
        try {
            if (this.flgTodosEnt == true) {
                this.flgEntEsp = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEntEsp() {
        try {
            if (this.flgEntEsp == true) {
                this.flgTodosEnt = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorId(Integer dato, Object object, Integer valor) {
        try {
            if (dato.equals(1)) { //ESTUDIANTE
                MatriculaBean matriculaBean = (MatriculaBean) object;
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
                if (valor.equals(1)) {//ENVIAR POR OBJ EST
                    if (flgPension) {
                        enviarMensajeNotiPensionObj(matriculaBean);
                    } else if (!flgPension) {
                        enviarMailEstudianteObj(matriculaBean);
                    }
                } else if (valor.equals(2)) {//IMPRIMIR POR OBJ EST
                    imprimirNotificacionMasivaObj(matriculaBean);
                } else if (valor.equals(3)) {
                    obtenerCuentaCte(matriculaBean);
                }
            } else if (dato.equals(2)) { //PERSONAL
                PersonalBean personalBean = (PersonalBean) object;
//                PersonalService personalService = BeanFactory.getPersonalService();
//                personalBean = personalService.buscarPorId(personalBean.getIdPersonal());
                enviarMailPersonalObj(personalBean);
            } else if (dato.equals(3)) { //EXTERNO
                PersonaBean personaBean = (PersonaBean) object;
                PersonaService personaService = BeanFactory.getPersonaService();
                personaBean = personaService.obtenerPersPorId(personaBean);
                enviarMailExternoObj(personaBean);
            } else if (dato.equals(4)) { //ENTIDAD
                EntidadBean entidadBean = (EntidadBean) object;
                EntidadService entidadService = BeanFactory.getEntidadService();
                entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
                enviarMailEntidadObj(entidadBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMesesForSup() {
        try {
            listaMesesExpMap = new LinkedHashMap<>();
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMatricula", null), 2);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
            listaMesesExpMap = Collections.unmodifiableMap(listaMesesExpMap);
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    //METODOS MASIVOS
    public void obtenerListaMeses() {
        try {
            setFlgMesPension(1);
            System.out.println(">>>" + getFlgMesPension());
            getIdTipoEstado();
            System.out.println(">>>" + getIdTipoEstado());
            if (listaMeses.length != 0) {
                if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                    CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                    List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
                    List<Integer> listaIds = new ArrayList<>();
                    for (int i = 0; i < listaMeses.length; i++) {
                        listaIds.add(listaMeses[i]);
                    }
                    for (MatriculaBean matricula : listaMatriculaEstudianteMasivoBean) {
                        CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
                        cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        cuentasPorCobrarBean.setAnio(matricula.getAnio());
                        cuentasPorCobrarBean.getIdTipoStatusCtaCte().setIdCodigo(idTipoEstado);
                        cuentasPorCobrarBean.getEstudianteBean().setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                        listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensaje(cuentasPorCobrarBean, listaIds);
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void enviarMailMasivo() {
        try {
            /*
             TIPO DE ACCION
             0=> Redactar
             1=> Enviados
             2=> Importantes
             3=> Borrador
             */
            System.out.println(">>>" + getTipPagante());
            if (getTipAccion() != null) {
                if (getTipAccion().equals(0)) {
                    if (getTipPagante().equals(1)) {//ENVIO ESTUDIANTE
                        if (getFlgMesPension() != null) {
                            if (getFlgMesPension() == 1) {
//                                enviarMailEstudianteMesPension();
                            } else if (getFlgMesPension() == 0) {
                                enviarMailEstudiante();
                            }
                        }
                    } else if (getTipPagante().equals(2)) {//ENVIO PERSONAL
                        enviarMailPersonal();
                    } else if (getTipPagante().equals(3)) {//ENVIO EXTERNO
                        enviarMailExterno();
                    } else if (getTipPagante().equals(4)) {//ENVIO ENTIDAD
                        enviarMailEntidad();
                    }
                }
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public static Boolean isCorreoValido(String str) {
        return (str.matches("^[_A-Za-z0-9-]+(\\." + "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$") && str.equals("") == false);
    }

    /*
        
     TIPO DE REMITENTE
    
     1 => MATRICULA
     2 => PERSONAL
     3 => EXTERNO
     4 => ENTIDAD
    
     */
    //METODOS DE ENVIO MASIVO
    public void enviarMailEstudiante() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                Integer count =0;
                Integer size=listaMatriculaEstudianteMasivoBean.size();
                for (MatriculaBean matricula : listaMatriculaEstudianteMasivoBean) {
                    count=count+1;
                    System.out.println(">>> " +count+ " de "+size+"  "  + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                    if (matricula.getEstudianteBean().getRespPagoBean().getCorreo() != null) {
                        if (!matricula.getEstudianteBean().getRespPagoBean().getCorreo().equals("")) {
                            if (isCorreoValido(matricula.getEstudianteBean().getRespPagoBean().getCorreo()) == true) {
                                System.out.println("Entrada OK, introdujo el correo " + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                                DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                                detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                                detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                                detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                                detEsquelaBean.setIdTipoRemitente(1);
                                detEsquelaBean.setStatus(1);
                                detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                                detEsquelaBean.setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                                detEsquelaBean.setEstudianteBean(matricula.getEstudianteBean());
                                detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                            } else {
                                System.out.println("Entrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                            }
                        } else {
                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxEntrada no válida, Formato de E-mail del Padre no válido " + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        }
                    } else if (matricula.getEstudianteBean().getRespPagoBean().getCorreo() == null) {
                        //ERRORES
                        System.out.println("correo null ----->" + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                        detEsquelaBean.setIdTipoRemitente(1);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        detEsquelaBean.setEstudianteBean(matricula.getEstudianteBean());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    } else {
                        System.out.println("correo null ----->" + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                        detEsquelaBean.setIdTipoRemitente(1);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        detEsquelaBean.setEstudianteBean(matricula.getEstudianteBean());
                        detEsquelaService.enviarMailError(detEsquelaBean);

                    }
                }
                //enviar al admi
                LegajoService legajoService = BeanFactory.getLegajoService();
                String correo = legajoService.obtenerCorreoCorPorPersonal(MaristaConstantes.UNI_ORG_ADM, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                if (correo != null) {
                    System.out.println("correo adm:" + correo);
                    DetEsquelaBean detEsquela = new DetEsquelaBean();
                    detEsquela.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    detEsquela.setAsunto(esquelaBean.getTitulo());
                    detEsquela.setMensaje(esquelaBean.getMensaje());
//                detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                    detEsquela.setIdTipoRemitente(1);
                    detEsquela.setStatus(1);
                    detEsquela.setCreaPor(usuarioLogin.getUsuario());
                    detEsquela.setCorreo(correo);
                    detEsquelaService.enviarMailEstudianteConf(detEsquela);
                }

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaMatriculaEstudianteMasivoBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /* ENVIO DE ESTUDIANTE POR OBJ */
    public void enviarMailEstudianteObj(MatriculaBean matriculaBean) {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            List<MatriculaBean> listaMatriculaBean = new ArrayList<>();
            listaMatriculaBean.add(matriculaBean);
            if (!listaMatriculaBean.isEmpty()) {
                for (MatriculaBean matricula : listaMatriculaBean) {
                    System.out.println(">>>" + matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                    if (!matricula.getEstudianteBean().getRespPagoBean().getCorreo().equals("")
                            && matricula.getEstudianteBean().getRespPagoBean().getCorreo() != null) {
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                        detEsquelaBean.setIdTipoRemitente(1);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (matricula.getEstudianteBean().getRespPagoBean().getCorreo().equals("")
                            || matricula.getEstudianteBean().getRespPagoBean().getCorreo() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(matricula.getIdMatricula().toString());
                        detEsquelaBean.setIdTipoRemitente(1);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(matricula.getEstudianteBean().getRespPagoBean().getCorreo());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaMatriculaEstudianteMasivoBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void mostrarVistaPrevia() {
        try {
            if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                for (MatriculaBean matricula : listaMatriculaEstudianteMasivoBean) {
                    matricula.setMensaje(esquelaBean.getMensaje());
                }
                setListaMatriculaPrevioBean(listaMatriculaEstudianteMasivoBean);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void enviarMailPersonal() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            if (!listaPersonalFiltroBean.isEmpty()) {
                for (PersonalBean personal : listaPersonalFiltroBean) {
                    System.out.println(">>>>" + personal.getCorreoCor());
                    if (!personal.getCorreoCor().equals("") && personal.getCorreoCor() != null) {
                        System.out.println(">>>" + personal.getCorreoCor());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(personal.getIdPersonal().toString());
                        detEsquelaBean.setIdTipoRemitente(2);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(personal.getCorreoCor());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (personal.getCorreoCor().equals("") || personal.getCorreoCor() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(personal.getIdPersonal().toString());
                        detEsquelaBean.setIdTipoRemitente(2);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaPersonalFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /* ENVIAR POR PERSONAL OBJ */
    public void enviarMailPersonalObj(PersonalBean personalBean) {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            List<PersonalBean> listaPersonalBean = new ArrayList<>();
            System.out.println(">>>>>" + personalBean.getCorreoCor());
            System.out.println(">>>>>" + personalBean.getCorreoPer());
            listaPersonalBean.add(personalBean);
            if (!listaPersonalBean.isEmpty()) {
                for (PersonalBean personal : listaPersonalBean) {
                    if (!personal.getCorreoCor().equals("") && personal.getCorreoCor() != null) {
                        System.out.println(">>>" + personal.getCorreoCor());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(personal.getIdPersonal().toString());
                        detEsquelaBean.setIdTipoRemitente(2);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(personal.getCorreoCor());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (personal.getCorreoCor().equals("") || personal.getCorreoCor() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(personal.getIdPersonal().toString());
                        detEsquelaBean.setIdTipoRemitente(2);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaPersonalFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void enviarMailExterno() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            if (!listaPersonaFiltroBean.isEmpty()) {
                for (PersonaBean persona : listaPersonaFiltroBean) {
                    if (!persona.getCorreo().equals("") && persona.getCorreo() != null) {
                        System.out.println(">>>" + persona.getCorreo());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(persona.getIdPersona());
                        detEsquelaBean.setIdTipoRemitente(3);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(persona.getCorreo());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (persona.getCorreo().equals("") || persona.getCorreo() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(persona.getIdPersona());
                        detEsquelaBean.setIdTipoRemitente(3);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaPersonaFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonaFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /* ENVIO DE EXTERNO POR OBJ */
    public void enviarMailExternoObj(PersonaBean personaBean) {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            List<PersonaBean> listaPersonaBean = new ArrayList<>();
            listaPersonaBean.add(personaBean);
            if (!listaPersonaBean.isEmpty()) {
                for (PersonaBean persona : listaPersonaBean) {
                    if (!persona.getCorreo().equals("") && persona.getCorreo() != null) {
                        System.out.println(">>>" + persona.getCorreo());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(persona.getIdPersona());
                        detEsquelaBean.setIdTipoRemitente(3);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(persona.getCorreo());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (persona.getCorreo().equals("") || persona.getCorreo() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(persona.getIdPersona());
                        detEsquelaBean.setIdTipoRemitente(3);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaPersonaFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonaFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void enviarMailEntidad() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            if (!listaEntidadFiltroBean.isEmpty()) {
                for (EntidadBean entidad : listaEntidadFiltroBean) {
                    if (!entidad.getCorreo().equals("") && entidad.getCorreo() != null) {
                        System.out.println(">>>" + entidad.getCorreo());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(entidad.getRuc());
                        detEsquelaBean.setIdTipoRemitente(4);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(entidad.getCorreo());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (entidad.getCorreo().equals("") || entidad.getCorreo() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(entidad.getRuc());
                        detEsquelaBean.setIdTipoRemitente(4);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaEntidadFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaEntidadFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /* ENVIAR MAIL ENTIDAD POR OBJ */
    public void enviarMailEntidadObj(EntidadBean entidadBean) {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            List<EntidadBean> listaEntidadBean = new ArrayList<>();
            listaEntidadBean.add(entidadBean);
            if (!listaEntidadBean.isEmpty()) {
                for (EntidadBean entidad : listaEntidadBean) {
                    if (!entidad.getCorreo().equals("") && entidad.getCorreo() != null) {
                        System.out.println(">>>" + entidad.getCorreo());
                        //CORRECTOS
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(entidad.getRuc());
                        detEsquelaBean.setIdTipoRemitente(4);
                        detEsquelaBean.setStatus(1);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaBean.setCorreo(entidad.getCorreo());
                        detEsquelaService.enviarMailEstudiante(detEsquelaBean);
                    } else if (entidad.getCorreo().equals("") || entidad.getCorreo() == null) {
                        //ERRORES
                        DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                        detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        detEsquelaBean.setAsunto(esquelaBean.getTitulo());
                        detEsquelaBean.setMensaje(esquelaBean.getMensaje());
                        detEsquelaBean.setIdObjeto(entidad.getRuc());
                        detEsquelaBean.setIdTipoRemitente(4);
                        detEsquelaBean.setStatus(0);
                        detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                        detEsquelaService.enviarMailError(detEsquelaBean);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else if (listaEntidadFiltroBean.isEmpty()) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaEntidadFiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarMailMasivo() {
        try {

        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    //FILTRO DE TIPOS DE MENSAJE
    public void obtenerListaMensaje() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
            detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
            if (getTipAccion() != null) {
                if (getTipAccion().equals(0)) {
                    flgRedactar = true;
                    flgEnviado = false;
                    flgImportante = false;
                    flgBorrador = false;
                    getEsquelaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaDetEsquelaBean = new ArrayList<>();
                } else if (getTipAccion().equals(1)) { //ENVIADOS
                    flgRedactar = false;
                    flgEnviado = true;
                    flgImportante = false;
                    flgBorrador = false;
                    detEsquelaBean.setIdTipoEstado(1);
                    listaDetEsquelaBean = detEsquelaService.obtenerMensajesPorTipo(detEsquelaBean);
                    if (listaDetEsquelaBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        listaDetEsquelaBean = new ArrayList<>();
                    }
                } else if (getTipAccion().equals(2)) { //IMPORTANTE
                    flgRedactar = false;
                    flgEnviado = false;
                    flgImportante = true;
                    flgBorrador = false;
                    detEsquelaBean.setIdTipoEstado(2);
                    listaDetEsquelaBean = detEsquelaService.obtenerMensajesPorTipo(detEsquelaBean);
                    if (listaDetEsquelaBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        listaDetEsquelaBean = new ArrayList<>();
                    }
                } else if (getTipAccion().equals(3)) { //BORRADOR
                    flgRedactar = false;
                    flgEnviado = false;
                    flgImportante = false;
                    flgBorrador = true;
                    detEsquelaBean.setIdTipoEstado(3);
                    listaDetEsquelaBean = detEsquelaService.obtenerMensajesPorTipo(detEsquelaBean);
                    if (listaDetEsquelaBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        listaDetEsquelaBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void enviarMensajeNotiPension() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
            Integer accion = esquelaService.obtenerMaxAccion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (flgPension) {
                List<Integer> listaIds = new ArrayList<>();
                for (int i = 0; i < listaMeses.length; i++) {
                    listaIds.add(listaMeses[i]);
                }
                for (MatriculaBean estudiante : listaMatriculaEstudianteMasivoBean) {
                    cuentasPorCobrar.getEstudianteBean().setIdEstudiante(estudiante.getEstudianteBean().getIdEstudiante());
                    cuentasPorCobrar.setAnio(matriculaFiltroBean.getAnio());
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensaje(cuentasPorCobrar, listaIds);
                    if (!listaCuentasPorCobrarBean.isEmpty()) {
                        String titulo = "", asunto = "";
                        for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                            asunto = detEsquelaService.execProEsquelaMasivo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuenta.getEstudianteBean().getIdEstudiante(), cuenta.getAnio(), cuenta.getMes(), mensajeObj).toString();
                            //VALIDANDO TIPOS DE MENSAJE
//                            if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo() != null) {
//                                if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo().equals(MaristaConstantes.COD_TIP_ESQUELA_PENSI)) {
//                                    titulo = "PAGO EXITOSO: PENSION DE " + cuenta.getNomMes().toUpperCase() + " - " + cuenta.getAnio().toString();
//                                    asunto = "<p style='font-family: \"Gill Sans Extrabold\", Helvetica, sans-serif;text-align:justify;' >\n"
//                                            + "Buenas Sr(es).<br/>\n"
//                                            + "Se le notifica que el pago realizado por el mes de " + cuenta.getNomMes() + " fue exitoso.<br/>\n"
//                                            + "Agradecemos su puntualidad.\n"
//                                            + "</p>";
//                                } else if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo().equals(MaristaConstantes.COD_TIP_ESQUELA_DEUDA)) {
//                                    titulo = "NOTIFICACIÓN DE PAGO: PENSION DE " + cuenta.getNomMes().toUpperCase() + " - " + cuenta.getAnio().toString();
//                                    asunto = "<p style='font-family: \"Gill Sans Extrabold\", Helvetica, sans-serif;text-align:justify;' >\n"
//                                            + "Buenas Sr(es).<br/>\n"
//                                            + "A la fecha esta pendiente la pensión de " + cuenta.getNomMes() + " la cual vencio el día " + cuenta.getFechaVenc().toString() + ",<br/>\n"
//                                            + "esperamos el pronto pago, cuyo monto es de " + cuenta.getMonto().toString() + "\n"
//                                            + "</p>";
//                                }
//                            }
                            //OBTENIENDO MENSAJES
                            DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                            System.out.println(">>>>>" + cuenta.getCorreoRec());
                            detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            detEsquelaBean.setCorreo(cuenta.getCorreoRec());
                            if (cuenta.getCorreoRec() != null && !cuenta.getCorreoRec().equals("")) {
                                detEsquelaBean.setIdTipoEstado(0);
                            } else {
                                detEsquelaBean.setIdTipoEstado(1);
                            }
                            detEsquelaBean.setAsunto(tituloObj);
                            detEsquelaBean.setMensaje(mensajeObj);
                            detEsquelaBean.setIdObjeto(cuenta.getMatriculaBean().getIdMatricula().toString());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setIdTipoRemitente(1);
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                            detEsquelaService.insertarEnvioMasivo(detEsquelaBean);
                        }
                        limpiarNotiPension();
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void enviarMensajeNotiPensionObj(MatriculaBean matriculaBean) {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
            List<MatriculaBean> listaMatricula = new ArrayList<>();
            Integer accion = esquelaService.obtenerMaxAccion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaMatricula.add(matriculaBean);
            if (flgPension) {
                List<Integer> listaIds = new ArrayList<>();
                for (int i = 0; i < listaMeses.length; i++) {
                    listaIds.add(listaMeses[i]);
                }
                for (MatriculaBean estudiante : listaMatricula) {
                    cuentasPorCobrar.getEstudianteBean().setIdEstudiante(estudiante.getEstudianteBean().getIdEstudiante());
                    cuentasPorCobrar.setAnio(matriculaFiltroBean.getAnio());
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensaje(cuentasPorCobrar, listaIds);
                    if (!listaCuentasPorCobrarBean.isEmpty()) {
                        String titulo = "", asunto = "";
                        for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                            asunto = detEsquelaService.execProEsquelaMasivo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuenta.getEstudianteBean().getIdEstudiante(), cuenta.getAnio(), cuenta.getMes(), mensajeObj).toString();
                            //VALIDANDO TIPOS DE MENSAJE
//                            if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo() != null) {
//                                if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo().equals(MaristaConstantes.COD_TIP_ESQUELA_PENSI)) {
//                                    titulo = "PAGO EXITOSO: PENSION DE " + cuenta.getNomMes().toUpperCase() + " - " + cuenta.getAnio().toString();
//                                    asunto = "<p style='font-family: \"Gill Sans Extrabold\", Helvetica, sans-serif;text-align:justify;' >\n"
//                                            + "Buenas Sr(es).<br/>\n"
//                                            + "Se le notifica que el pago realizado por el mes de " + cuenta.getNomMes() + " fue exitoso.<br/>\n"
//                                            + "Agradecemos su puntualidad.\n"
//                                            + "</p>";
//                                } else if (getEsquelaBean().getTipoEsquelaBean().getIdCodigo().equals(MaristaConstantes.COD_TIP_ESQUELA_DEUDA)) {
//                                    titulo = "NOTIFICACIÓN DE PAGO: PENSION DE " + cuenta.getNomMes().toUpperCase() + " - " + cuenta.getAnio().toString();
//                                    asunto = "<p style='font-family: \"Gill Sans Extrabold\", Helvetica, sans-serif;text-align:justify;' >\n"
//                                            + "Buenas Sr(es).<br/>\n"
//                                            + "A la fecha esta pendiente la pensión de " + cuenta.getNomMes() + " la cual vencio el día " + cuenta.getFechaVenc().toString() + ",<br/>\n"
//                                            + "esperamos el pronto pago, cuyo monto es de " + cuenta.getMonto().toString() + "\n"
//                                            + "</p>";
//                                }
//                            }
                            //OBTENIENDO MENSAJES
                            DetEsquelaBean detEsquelaBean = new DetEsquelaBean();
                            System.out.println(">>>>>" + cuenta.getCorreoRec());
                            detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            detEsquelaBean.setCorreo(cuenta.getCorreoRec());
                            if (cuenta.getCorreoRec() != null && !cuenta.getCorreoRec().equals("")) {
                                detEsquelaBean.setIdTipoEstado(0);
                            } else {
                                detEsquelaBean.setIdTipoEstado(1);
                            }
                            detEsquelaBean.setAsunto(tituloObj);
                            detEsquelaBean.setMensaje(asunto);
                            detEsquelaBean.setIdObjeto(cuenta.getMatriculaBean().getIdMatricula().toString());
                            detEsquelaBean.getEstudianteBean().setIdEstudiante(cuenta.getEstudianteBean().getIdEstudiante());
                            detEsquelaBean.setIdTipoRemitente(1);
                            detEsquelaBean.setStatus(1);
                            detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                            detEsquelaService.insertarEnvioMasivo(detEsquelaBean);
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdMasivo(Object object) {
        try {
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaBean = (EsquelaBean) object;
            esquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            esquelaBean = esquelaService.obtenerPorIdEsq(esquelaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificarMensajeMasivo() {
        try {
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaBean.setModiPor(usuarioLogin.getUsuario());
            esquelaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            esquelaService.actualizarEsquela(esquelaBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarMensaje() {
        try {
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaService.eliminarEsquelaMasivo(esquelaBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirNotificacionMasivaObj(MatriculaBean matricula) {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/RepNotiMasiva.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            List<Integer> listaIds = new ArrayList<>();
            List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
            List<NotiMasivaRepBean> listaNotiMasivaRepBean = new ArrayList<>();

            //DATOS DE MATRICULA
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            List<MatriculaBean> listaMatricula = new ArrayList<>();
            MatriculaBean matriculaBean = new MatriculaBean();
            matriculaBean = matriculaService.obtenerMatriculaPorId(matricula);
            listaMatricula.add(matriculaBean);

            for (int i = 0; i < listaMeses.length; i++) {
                listaIds.add(listaMeses[i]);
            }
            for (MatriculaBean estudiante : listaMatricula) {
                cuentasPorCobrar.getEstudianteBean().setIdEstudiante(estudiante.getEstudianteBean().getIdEstudiante());
                cuentasPorCobrar.setAnio(matriculaFiltroBean.getAnio());
                listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensaje(cuentasPorCobrar, listaIds);
                if (!listaCuentasPorCobrarBean.isEmpty()) {
                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                        NotiMasivaRepBean noti = new NotiMasivaRepBean();
                        noti.setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        noti.setCodigo(cuenta.getEstudianteBean().getCodigo());
                        noti.setMonto(cuenta.getMonto().toString());
                        noti.setMontoPalabras(cuenta.getMontoPalabras());
                        noti.setFechaPago(cuenta.getFechaPalabras());
                        noti.setConcepto(cuenta.getConceptoBean().getNombre());
                        noti.setAnio(cuenta.getAnio().toString());
                        noti.setNomBanco(cuenta.getNomBanco());
                        noti.setGrado(cuenta.getGrado());
                        noti.setNombres(cuenta.getEstudianteBean().getPersonaBean().getNombreCompleto().toUpperCase());
                        noti.setReferenciaCuenta(cuenta.getReferenciaCuenta());
                        listaNotiMasivaRepBean.add(noti);
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaNotiMasivaRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
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
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void imprimirNotificacionMasiva() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/RepNotiMasiva.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            List<Integer> listaIds = new ArrayList<>();
            List<CuentasPorCobrarBean> listaCuentasPorCobrarBean = new ArrayList<>();
            List<NotiMasivaRepBean> listaNotiMasivaRepBean = new ArrayList<>();
            for (int i = 0; i < listaMeses.length; i++) {
                listaIds.add(listaMeses[i]);
            }
            for (MatriculaBean estudiante : listaMatriculaEstudianteMasivoBean) {
                cuentasPorCobrar.getEstudianteBean().setIdEstudiante(estudiante.getEstudianteBean().getIdEstudiante());
                cuentasPorCobrar.setAnio(matriculaFiltroBean.getAnio());
                if (flgByCole = false) {
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensaje(cuentasPorCobrar, listaIds);
                } else {
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaMensajeCole(cuentasPorCobrar, listaIds);
                }
                if (!listaCuentasPorCobrarBean.isEmpty()) {
                    Integer n = 0;
                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                        NotiMasivaRepBean noti = new NotiMasivaRepBean();
                        n++;
                        noti.setUniNeg(cuenta.getUnidadNegocioBean().getUniNeg());
                        noti.setCodigo(cuenta.getEstudianteBean().getCodigo());
                        noti.setMonto(cuenta.getMonto().toString());
                        noti.setMontoPalabras(cuenta.getMontoPalabras());
                        noti.setFechaPago(cuenta.getFechaPalabras());
                        noti.setConcepto(cuenta.getConceptoBean().getNombre());
                        noti.setAnio(cuenta.getAnio().toString());
                        noti.setNomBanco(cuenta.getNomBanco());
                        noti.setGrado(cuenta.getGrado());
                        noti.setNombres(cuenta.getEstudianteBean().getPersonaBean().getNombreCompleto().toUpperCase());
                        noti.setReferenciaCuenta(cuenta.getReferenciaCuenta());
                        listaNotiMasivaRepBean.add(noti);
                    }
                    System.out.println(">>>>>>>" + n);
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaNotiMasivaRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
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
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void obtenerCuentaCte(MatriculaBean matriculaBean) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cuenta = new CuentasPorCobrarBean();
            /* OBTENIENDO CUENTA POR ANIO */
            cuenta.getUnidadNegocioBean().setUniNeg(matriculaBean.getUnidadNegocioBean().getUniNeg());
            cuenta.setAnio(matriculaBean.getAnio());
            cuenta.getEstudianteBean().getPersonaBean().setIdPersona(matriculaBean.getEstudianteBean().getIdEstudiante());
            listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCtaCtePorEstudiantePorAnio(cuenta);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ponerEsquelaMasivo() {
        try {
            System.out.println(">>>>" + getObj());
            if (mensajeObj != null) {
                mensajeObj += mensajeObj + getObj();
            }
            System.out.println(">>>>" + getMensajeObj());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarNotiPension() {
        try {
            tituloObj = "";
            mensajeObj = "";
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerPorIdMensaje(Object object, Integer dato) {
        try {
            detEsquelaBean = (DetEsquelaBean) object;
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            detEsquelaBean = detEsquelaService.obtenerPorId(detEsquelaBean.getIdDetEsquela());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerMeses() {
        try {
            listaMesAll.add(new MesBean(2, MaristaConstantes.FEBRERO));
            listaMesAll.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMesAll.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMesAll.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMesAll.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMesAll.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMesAll.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMesAll.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMesAll.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMesAll.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMesAll.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ponerMeses() {
        try {
            listaMeses[0] = getMesSelect();
            System.out.println(">>>" + listaMeses.length);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void ponerMensajeBorrador() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            detEsquelaBean.setIdTipoEstado(3);
            detEsquelaBean.setModiPor(usuarioLogin.getUsuario());
            detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detEsquelaService.modificarEstadoBorrador(detEsquelaBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
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

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
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

    public Boolean getFlgPa() {
        return flgPa;
    }

    public void setFlgPa(Boolean flgPa) {
        this.flgPa = flgPa;
    }

    public Boolean getFlgMa() {
        return flgMa;
    }

    public void setFlgMa(Boolean flgMa) {
        this.flgMa = flgMa;
    }

    public Boolean getFlgAp() {
        return flgAp;
    }

    public void setFlgAp(Boolean flgAp) {
        this.flgAp = flgAp;
    }

    public Boolean getFlgResPa() {
        return flgResPa;
    }

    public void setFlgResPa(Boolean flgResPa) {
        this.flgResPa = flgResPa;
    }

    public Integer[] getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Integer[] listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Map<String, Integer> getListaMesesExpMap() {
        if (listaMesesExpMap == null) {
            listaMesesExpMap = new HashMap<>();
        }
        return listaMesesExpMap;
    }

    public void setListaMesesExpMap(Map<String, Integer> listaMesesExpMap) {
        this.listaMesesExpMap = listaMesesExpMap;
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

    public EsquelaBean getEsquelaBean() {
        if (esquelaBean == null) {
            esquelaBean = new EsquelaBean();
        }
        return esquelaBean;
    }

    public void setEsquelaBean(EsquelaBean esquelaBean) {
        this.esquelaBean = esquelaBean;
    }

    public List<EsquelaBean> getListaEsquelaBean() {
        if (listaEsquelaBean == null) {
            listaEsquelaBean = new ArrayList<>();
        }
        return listaEsquelaBean;
    }

    public void setListaEsquelaBean(List<EsquelaBean> listaEsquelaBean) {
        this.listaEsquelaBean = listaEsquelaBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
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

    public List<ViewMatriculaBean> getListaViewMatriculaBean() {
        if (listaViewMatriculaBean == null) {
            listaViewMatriculaBean = new ArrayList<>();
        }
        return listaViewMatriculaBean;
    }

    public void setListaViewMatriculaBean(List<ViewMatriculaBean> listaViewMatriculaBean) {
        this.listaViewMatriculaBean = listaViewMatriculaBean;
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

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
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

    public EntidadBean getEntidadFiltroBean() {
        if (entidadFiltroBean == null) {
            entidadFiltroBean = new EntidadBean();
        }
        return entidadFiltroBean;
    }

    public void setEntidadFiltroBean(EntidadBean entidadFiltroBean) {
        this.entidadFiltroBean = entidadFiltroBean;
    }

    public List<PersonalBean> getListaPersonalFiltroBean() {
        if (listaPersonalFiltroBean == null) {
            listaPersonalFiltroBean = new ArrayList<>();
        }
        return listaPersonalFiltroBean;
    }

    public void setListaPersonalFiltroBean(List<PersonalBean> listaPersonalFiltroBean) {
        this.listaPersonalFiltroBean = listaPersonalFiltroBean;
    }

    public List<PersonaBean> getListaPersonaFiltroBean() {
        if (listaPersonaFiltroBean == null) {
            listaPersonaFiltroBean = new ArrayList<>();
        }
        return listaPersonaFiltroBean;
    }

    public void setListaPersonaFiltroBean(List<PersonaBean> listaPersonaFiltroBean) {
        this.listaPersonaFiltroBean = listaPersonaFiltroBean;
    }

    public List<EntidadBean> getListaEntidadFiltroBean() {
        if (listaEntidadFiltroBean == null) {
            listaEntidadFiltroBean = new ArrayList<>();
        }
        return listaEntidadFiltroBean;
    }

    public void setListaEntidadFiltroBean(List<EntidadBean> listaEntidadFiltroBean) {
        this.listaEntidadFiltroBean = listaEntidadFiltroBean;
    }

    public Boolean getRenderEstudiante() {
        return renderEstudiante;
    }

    public void setRenderEstudiante(Boolean renderEstudiante) {
        this.renderEstudiante = renderEstudiante;
    }

    public Boolean getRenderPersonal() {
        return renderPersonal;
    }

    public void setRenderPersonal(Boolean renderPersonal) {
        this.renderPersonal = renderPersonal;
    }

    public Boolean getRenderPersona() {
        return renderPersona;
    }

    public void setRenderPersona(Boolean renderPersona) {
        this.renderPersona = renderPersona;
    }

    public Boolean getRenderEntidad() {
        return renderEntidad;
    }

    public void setRenderEntidad(Boolean renderEntidad) {
        this.renderEntidad = renderEntidad;
    }

    public Integer getTipPagante() {
        return tipPagante;
    }

    public void setTipPagante(Integer tipPagante) {
        this.tipPagante = tipPagante;
    }

    public Boolean getFlgEstComprobanteMes() {
        return flgEstComprobanteMes;
    }

    public void setFlgEstComprobanteMes(Boolean flgEstComprobanteMes) {
        this.flgEstComprobanteMes = flgEstComprobanteMes;
    }

    public Boolean getFlgTodosPer() {
        return flgTodosPer;
    }

    public void setFlgTodosPer(Boolean flgTodosPer) {
        this.flgTodosPer = flgTodosPer;
    }

    public Boolean getFlgPerComprobanteMes() {
        return flgPerComprobanteMes;
    }

    public void setFlgPerComprobanteMes(Boolean flgPerComprobanteMes) {
        this.flgPerComprobanteMes = flgPerComprobanteMes;
    }

    public Boolean getFlgPerEsp() {
        return flgPerEsp;
    }

    public void setFlgPerEsp(Boolean flgPerEsp) {
        this.flgPerEsp = flgPerEsp;
    }

    public Boolean getFlgPerUniOrg() {
        return flgPerUniOrg;
    }

    public void setFlgPerUniOrg(Boolean flgPerUniOrg) {
        this.flgPerUniOrg = flgPerUniOrg;
    }

    public Boolean getFlgTodosExt() {
        return flgTodosExt;
    }

    public void setFlgTodosExt(Boolean flgTodosExt) {
        this.flgTodosExt = flgTodosExt;
    }

    public Boolean getFlgExtEsp() {
        return flgExtEsp;
    }

    public void setFlgExtEsp(Boolean flgExtEsp) {
        this.flgExtEsp = flgExtEsp;
    }

    public Boolean getFlgTodosEnt() {
        return flgTodosEnt;
    }

    public void setFlgTodosEnt(Boolean flgTodosEnt) {
        this.flgTodosEnt = flgTodosEnt;
    }

    public Boolean getFlgEntEsp() {
        return flgEntEsp;
    }

    public void setFlgEntEsp(Boolean flgEntEsp) {
        this.flgEntEsp = flgEntEsp;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaBean() {
        if (listaUnidadOrganicaBean == null) {
            listaUnidadOrganicaBean = new ArrayList<>();
        }
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UnidadOrganicaBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
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

    public List<Integer> getListaAnioFiltroMatricula() {
        if (listaAnioFiltroMatricula == null) {
            listaAnioFiltroMatricula = new ArrayList<>();
        }
        return listaAnioFiltroMatricula;
    }

    public void setListaAnioFiltroMatricula(List<Integer> listaAnioFiltroMatricula) {
        this.listaAnioFiltroMatricula = listaAnioFiltroMatricula;
    }

    public Boolean getFlgPension() {
        return flgPension;
    }

    public void setFlgPension(Boolean flgPension) {
        this.flgPension = flgPension;
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

    public Integer getTipAccion() {
        return tipAccion;
    }

    public void setTipAccion(Integer tipAccion) {
        this.tipAccion = tipAccion;
    }

    public Integer getFlgMesPension() {
        return flgMesPension;
    }

    public void setFlgMesPension(Integer flgMesPension) {
        this.flgMesPension = flgMesPension;
    }

    public List<MatriculaBean> getListaMatriculaPrevioBean() {
        if (listaMatriculaPrevioBean == null) {
            listaMatriculaPrevioBean = new ArrayList<>();
        }
        return listaMatriculaPrevioBean;
    }

    public void setListaMatriculaPrevioBean(List<MatriculaBean> listaMatriculaPrevioBean) {
        this.listaMatriculaPrevioBean = listaMatriculaPrevioBean;
    }

    public List<DetEsquelaBean> getListaDetEsquelaBean() {
        if (listaDetEsquelaBean == null) {
            listaDetEsquelaBean = new ArrayList<>();
        }
        return listaDetEsquelaBean;
    }

    public void setListaDetEsquelaBean(List<DetEsquelaBean> listaDetEsquelaBean) {
        this.listaDetEsquelaBean = listaDetEsquelaBean;
    }

    public Boolean getFlgRedactar() {
        return flgRedactar;
    }

    public void setFlgRedactar(Boolean flgRedactar) {
        this.flgRedactar = flgRedactar;
    }

    public Boolean getFlgEnviado() {
        return flgEnviado;
    }

    public void setFlgEnviado(Boolean flgEnviado) {
        this.flgEnviado = flgEnviado;
    }

    public Boolean getFlgImportante() {
        return flgImportante;
    }

    public void setFlgImportante(Boolean flgImportante) {
        this.flgImportante = flgImportante;
    }

    public Boolean getFlgBorrador() {
        return flgBorrador;
    }

    public void setFlgBorrador(Boolean flgBorrador) {
        this.flgBorrador = flgBorrador;
    }

    public List<CodigoBean> getListaTipoEstado() {
        if (listaTipoEstado == null) {
            listaTipoEstado = new ArrayList<>();
        }
        return listaTipoEstado;
    }

    public void setListaTipoEstado(List<CodigoBean> listaTipoEstado) {
        this.listaTipoEstado = listaTipoEstado;
    }

    public Integer getIdTipoEstado() {
        return idTipoEstado;
    }

    public void setIdTipoEstado(Integer idTipoEstado) {
        this.idTipoEstado = idTipoEstado;
    }

    public List<CodigoBean> getListaTipoAviso() {
        return listaTipoAviso;
    }

    public void setListaTipoAviso(List<CodigoBean> listaTipoAviso) {
        this.listaTipoAviso = listaTipoAviso;
    }

    public List<EsquelaBean> getListaEsquelaAviso() {
        if (listaEsquelaAviso == null) {
            listaEsquelaAviso = new ArrayList<>();
        }
        return listaEsquelaAviso;
    }

    public void setListaEsquelaAviso(List<EsquelaBean> listaEsquelaAviso) {
        this.listaEsquelaAviso = listaEsquelaAviso;
    }

    public CuentasPorCobrarBean getCuentasPorCobrar() {
        if (cuentasPorCobrar == null) {
            cuentasPorCobrar = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrar;
    }

    public void setCuentasPorCobrar(CuentasPorCobrarBean cuentasPorCobrar) {
        this.cuentasPorCobrar = cuentasPorCobrar;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
    }

    public String getMensajeObj() {
        return mensajeObj;
    }

    public void setMensajeObj(String mensajeObj) {
        this.mensajeObj = mensajeObj;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getTituloObj() {
        return tituloObj;
    }

    public void setTituloObj(String tituloObj) {
        this.tituloObj = tituloObj;
    }

    public DetEsquelaBean getDetEsquelaBean() {
        if (detEsquelaBean == null) {
            detEsquelaBean = new DetEsquelaBean();
        }
        return detEsquelaBean;
    }

    public void setDetEsquelaBean(DetEsquelaBean detEsquelaBean) {
        this.detEsquelaBean = detEsquelaBean;
    }

    public List<MesBean> getListaMesAll() {
        if (listaMesAll == null) {
            listaMesAll = new ArrayList<>();
        }
        return listaMesAll;
    }

    public void setListaMesAll(List<MesBean> listaMesAll) {
        this.listaMesAll = listaMesAll;
    }

    public Integer getMesSelect() {
        return mesSelect;
    }

    public void setMesSelect(Integer mesSelect) {
        this.mesSelect = mesSelect;
    }

    public Boolean getFlgByCole() {
        return flgByCole;
    }

    public void setFlgByCole(Boolean flgByCole) {
        this.flgByCole = flgByCole;
    }

}
