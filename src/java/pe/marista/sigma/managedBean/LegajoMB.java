package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.print.DocFlavor;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.jfree.data.time.TimePeriodValue;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.bean.CargoBean;
import pe.marista.sigma.bean.CargoUniNegBean;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.CarreraBean;
import pe.marista.sigma.bean.CarreraSubAreaBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.PersonalContratoBean;
import pe.marista.sigma.bean.DepartamentoBean;
import pe.marista.sigma.bean.PersonalDependienteBean;
import pe.marista.sigma.bean.DistritoBean;
import pe.marista.sigma.bean.DocumentoBean;
import pe.marista.sigma.bean.DocumentoCargoBean;
import pe.marista.sigma.bean.EnfermedadBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PersonalFormacionBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonalAlergiaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalCargoBean;
import pe.marista.sigma.bean.PersonalDatosHistoricoBean;
import pe.marista.sigma.bean.PersonalDescansoMedicoBean;
import pe.marista.sigma.bean.PersonalDesvinculacionBean;
import pe.marista.sigma.bean.PersonalDocumentoBean;
import pe.marista.sigma.bean.PersonalEnfermedadBean;
import pe.marista.sigma.bean.PersonalEvaPsicologicaBean;
import pe.marista.sigma.bean.PersonalExperienciaBean;
import pe.marista.sigma.bean.PersonalFormacionCarismaBean;
import pe.marista.sigma.bean.PersonalIdiomaBean;
import pe.marista.sigma.bean.PersonalInformacionBancariaBean;
import pe.marista.sigma.bean.PersonalOtrosEstudiosBean;
import pe.marista.sigma.bean.PersonalPDFBean;
import pe.marista.sigma.bean.PersonalProcesoJudicialBean;
import pe.marista.sigma.bean.PersonalVacacionesBean;
import pe.marista.sigma.bean.PersonalVoluntariadoBean;
import pe.marista.sigma.bean.PlanillaBean;
import pe.marista.sigma.bean.ProvinciaBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewEntidadBean;
import pe.marista.sigma.bean.reporte.ConceptoPlanillaRepBean;
import pe.marista.sigma.bean.reporte.LegajoRepBean;
import pe.marista.sigma.bean.reporte.PersonalCargoRepBean;
import pe.marista.sigma.bean.reporte.PersonalContratoRepBean;
import pe.marista.sigma.bean.reporte.PersonalDependienteRepBean;
import pe.marista.sigma.bean.reporte.PersonalDocumentoRepBean;
import pe.marista.sigma.bean.reporte.PersonalExperienciaRepBean;
import pe.marista.sigma.bean.reporte.PersonalFormacionRepBean;
import pe.marista.sigma.bean.reporte.PersonalIdiomaRepBean;
import pe.marista.sigma.bean.reporte.PersonalOtrosEstudiosRepBean;
import pe.marista.sigma.bean.reporte.PersonalProcesoJudicialRepBean;
import pe.marista.sigma.bean.reporte.PersonalRepBean;
import pe.marista.sigma.bean.reporte.PlanillaCuentasCRRepBean;
import pe.marista.sigma.bean.reporte.PlanillaPersonalCRRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.CargoService;
import pe.marista.sigma.service.CargoUniNegService;
import pe.marista.sigma.service.CarreraAreaService;
import pe.marista.sigma.service.CarreraService;
import pe.marista.sigma.service.CarreraSubAreaService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DistritoService;
import pe.marista.sigma.service.DocumentoCargoService;
import pe.marista.sigma.service.EnfermedadService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonalAlergiaService;
import pe.marista.sigma.service.PersonalCargoService;
import pe.marista.sigma.service.PersonalContratoService;
import pe.marista.sigma.service.PersonalDependienteService;
import pe.marista.sigma.service.PersonalDocumentoService;
import pe.marista.sigma.service.PersonalEnfermedadService;
import pe.marista.sigma.service.PersonalExperienciaService;
import pe.marista.sigma.service.PersonalFormacionService;
import pe.marista.sigma.service.PersonalIdiomaService;
import pe.marista.sigma.service.PersonalOtrosEstudiosService;
import pe.marista.sigma.service.PersonalProcesoJudicialService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.TipoFormacionService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaCifra;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class LegajoMB extends BaseMB implements Serializable {

    @PostConstruct
    public void LegajoMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getPersonalBean();
            getPersonalFiltroBean();
            getPersonalFiltroDepBean();
            getPersonalFiltroBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            getPersonalFiltroDepBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

            getPersonalRepFiltroBean();
            getPersonalRepFiltroBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaUniNegUniOrgBean();
            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            listaUniNegUniOrgBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            setIdTipoSol("COL");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean beanUsuarioSesion;
    private List<PersonalBean> listaPersonalFiltroBean;
    private List<PersonalBean> listaPersonalBean;
    private PersonalBean personalBean;
    private PersonalBean personalFiltroBean;
    private PersonalRepBean personalRepFiltroBean;
    private List<PersonalRepBean> listaPersonaRepFiltroBean;
    private PersonalBean personalFiltroDepBean;
    private List<CodigoBean> listaDocPer;
    private List<PaisBean> listaNacionalidad;
    private List<PaisBean> listaPaisEmisionDoc;
    private List<CodigoBean> listaEstadoCivil;
    private List<PaisBean> listaPaisNacimiento;
    private List<CodigoBean> listaVivienda;
    private List<CodigoBean> listaCasa;
    private List<CodigoBean> listaTipoSeguroPer;
    private List<CodigoBean> listaTipoPensionPer;
    private List<CodigoBean> listaStatusCasa;
    private List<ViewEntidadBean> listaEntidadEPS;
    private List<ViewEntidadBean> listaEntidadPension;
    private List<ViewEntidadBean> listaEntidadEPSNew;
    private List<ViewEntidadBean> listaEntidadPensionNew;
//    private List<EntidadBean> listaEntidadEPS;
//    private List<EntidadBean> listaEntidadPension;
    private List<CodigoBean> listaOcupacion;
    private GradoAcademicoBean gradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademico;
//    private List<EntidadBean> listaEntidadPorRubro; 
    private List<PersonalBean> listaPersonalPorUniNegBean;

    //PersonalFormacionBasica
    private PersonalFormacionBean personalFormacionBasicaBean;
    private List<PersonalFormacionBean> listaPersonalFormacionBasicaBean;
    private List<PaisBean> listaPaisFormacionBasica;
    private List<CodigoBean> listaTipoEstudios;
    private List<CodigoBean> listaTipoModalidadEst;
    private List<CodigoBean> listaTipoOtrosEstudios;
    private List<CodigoBean> listaTipoFinanciamiento;
    private List<ViewEntidadBean> listaEntidadFormacionBasica;
//    private List<EntidadBean> listaEntidadFormacionBasica;
    private List<Integer> listaAnosIniFormacionBasica;
    private List<Integer> listaAnosFinFormacionBasica;
    private List<NivelAcademicoBean> listaNivelAcademicoBasica;
    private List<GradoAcademicoBean> listaGradoAcademicoFormacionBasica;
    private Map<String, Integer> listaMesesForBasMap;
    //PersonalFormacionSuperior
    private PersonalFormacionBean personalFormacionSuperiorBean;
    private List<PersonalFormacionBean> listaPersonalFormacionSuperiorBean;
    private List<PaisBean> listaPaisFormacionSuperior;
    private List<ViewEntidadBean> listaEntidadFormacionSuperior;
//    private List<EntidadBean> listaEntidadFormacionSuperior;
    private EntidadBean entidadFormacionSuperior;
    private List<TipoFormacionBean> listaTipoFormacionSuperior;
    private List<NivelAcademicoBean> listaNivelAcademicoSuperior;
    private List<GradoAcademicoBean> listaGradoAcademicoFormacionSuperior;
    private List<Integer> listaAnosIniFormacionSuperior;
    private List<Integer> listaAnosFinFormacionSuperior;
    private Map<String, Integer> listaMesesForSupMap;

    //carrera
    private List<CarreraAreaBean> listaFormacionSuperiorCarreraArea;
    private List<CarreraSubAreaBean> listaFormacionSuperiorCarreraSubArea;
    private List<CarreraBean> listaFormacionSuperiorCarrera;
    private CarreraBean formacionSuperiorCarreraBean;

    //PersonalOtrosEstudios
    private PersonalOtrosEstudiosBean personalOtrosEstudiosBean;
    private List<PersonalOtrosEstudiosBean> listaPersonalOtrosEstudiosBean;
    private List<PaisBean> listaPaisOtrosEstudios;
//    private List<ViewEntidadBean> listaEntidadOtrosEstudios;
//    private List<EntidadBean> listaEntidadOtrosEstudios;
    private List<Integer> listaAnosIniOtrosEstudios;
    private List<Integer> listaAnosFinOtrosEstudios;
    private Map<String, Integer> listaMesesOtrosEstudiosMap;

    //PersonalIdioma
    private PersonalIdiomaBean personalIdiomaBean;
    private List<PersonalIdiomaBean> listaPersonalIdiomaBean;
    private List<CodigoBean> listaTipoIdioma;
    private List<CodigoBean> listaNivel;
//    private List<ViewEntidadBean> listaEntidadIdioma;
//    private List<EntidadBean> listaEntidadIdioma;

    //Distritos
    private List<DepartamentoBean> listaDepartamentoNaci;
    private List<ProvinciaBean> listaProvinciaNaci;
    private List<DistritoBean> listaDistritoNaci;
    private List<DepartamentoBean> listaDepartamentoDomi;
    private List<ProvinciaBean> listaProvinciaDomi;
    private List<DistritoBean> listaDistritoDomi;

    //PersonalDependiente
    private PersonalDependienteBean personalDependienteBean;
    private List<PersonalDependienteBean> listaPersonalDependienteBean;
    private PersonalDependienteBean personalOtrosDependienteBean;
    private List<CodigoBean> listaParentesco;
    private List<CodigoBean> listaOtrosParentesco;
    private List<CodigoBean> listaEstadoCivilDependiente;
    private List<CodigoBean> listaDocPerDependiente;
    private List<PaisBean> listaNacionalidadDependiente;
    //Carrera Dependiente
    private List<TipoFormacionBean> listaTipoFormacionDependiente;
    private List<NivelAcademicoBean> listaNivelAcademicoDependiente;
    private List<GradoAcademicoBean> listaGradoAcademicoDependiente;
    private List<CarreraBean> listaCarreraDependiente;
    private List<CarreraAreaBean> listaCarreraAreaDependiente;
    private List<CarreraSubAreaBean> listaCarreraSubAreaDependiente;
    private CarreraBean carreraBeanDependiente;

    //PersonaCargo
    private PersonalCargoBean personalCargoBean;
    private List<PersonalCargoBean> listaPersonalCargoBean;

    //UN-UO
    private List<UniNegUniOrgBean> listaUniNegUniOrgBean;
    private List<UniNegUniOrgBean> listaUniNegUniOrgBeanPersonal;
    private UniNegUniOrgBean uniNegUniOrgBean;

    private List<CargoUniNegBean> listaCargoUniNegBean;
    private CargoUniNegBean cargoUniNegBean;

    private List<CargoBean> listaCargoBean;
    private CargoBean cargoBean;

    //PersonalEnfermedad
    private PersonalEnfermedadBean personalEnfermedadBean;
    private List<PersonalEnfermedadBean> listaPersonalEnfermedadBean;
    private EnfermedadBean enfermedadBean;
    private List<EnfermedadBean> listaEnfermedadBean;
    private List<CodigoBean> listaTipoEnfermedad;
    private List<CodigoBean> listaStatusEnfermedadBean;

    //PersonalExperiencia
    private PersonalExperienciaBean personalExperienciaBean;
    private List<PersonalExperienciaBean> listaPersonalExperienciaBean;
    private List<Integer> listaAnosIniExp;
    private List<Integer> listaAnosFinExp;
    private Map<String, Integer> listaMesesExpMap;

    //PersonalContrato
    private PersonalContratoBean personalContratoBean;
    private List<PersonalContratoBean> listaPersonalContratoBean;
    private List<CodigoBean> listaTipoContrato;
    private List<CodigoBean> listaTipoPlanillaColegio;
    //PersonalContrato-Vaca
    private PersonalContratoBean personalVacacionesBean;
    private List<PersonalVacacionesBean> listaPersonalVacacionesBean;

    //PersonalProcesoJudicial
    private PersonalProcesoJudicialBean personalProcesoJudicialBean;
    private List<PersonalProcesoJudicialBean> listaPersonalProcesoJudicialBean;
    private List<CodigoBean> listaTipoProcesoJudicial;
    private List<CodigoBean> listaTipoRetencion;
    private List<CodigoBean> listaTipoValor;
    private List<CodigoBean> listaTipoModoPago;

    //PersonalAlergia
    private PersonalAlergiaBean personalAlergiaBean;
    private List<PersonalAlergiaBean> listaPersonalAlergiaBean;
    private List<CodigoBean> listaTipoAlergia;

    //PersonalDocumento
    private PersonalDocumentoBean personalDocumentoBean;
    private List<PersonalDocumentoBean> listaPersonalDocumentoBean;
    private List<DocumentoBean> listaDocumentoBean;
    private List<DocumentoCargoBean> listaDocumentoCargoBean;
    private DocumentoBean documentoBean;
    private DocumentoCargoBean documentoCargoBean;

    private List<PersonalBean> listaPersonalCumpleaniosBean;

    private List<CentroResponsabilidadBean> listaCR1;
    private List<CentroResponsabilidadBean> listaCR2;
    private List<CentroResponsabilidadBean> listaCR3;
    private List<CentroResponsabilidadBean> listaCR4;
    private List<CentroResponsabilidadBean> listaCR5;
    private Boolean flgPaisPeru = true;

    private Boolean flgFiltroPersonal = false;
    private Boolean flgFiltroPersona = false;
    private Boolean collapsed = false;
    private Boolean flgSoli = true;
    private String idTipoSol;
    private Integer sumaPorcentaje = 0;
    private Boolean flgGrabar;
    private Boolean flgGrabarStatus = false;
    private List<CodigoBean> listaNivelIns;
    private Integer mes;
    private Integer[] selectedIdTipoAcceso;
    private List<MesBean> listaMesAll;
    private Integer anio;
    private Map<String, Integer> listaReporte;
    private Integer orden;
    private Boolean flgTodosLista;
    private List<UsuarioBean> listaUsuarioBean;
    private Boolean valAdmTodos = false;

    private List<CodigoBean> listaTipoVia;
    private List<CodigoBean> listaTipoZona;
    private List<CodigoBean> listaTipoFormacion;
    private PersonalFormacionCarismaBean personalFormacionCarismaBean;
    private List<PersonalFormacionCarismaBean> listPersonalForCarismaBean;

    private String menorEdad;
    private Boolean flgMedicamentos = false;
    private PersonalDescansoMedicoBean personalDescansoMedicoBean;
    private List<PersonalDescansoMedicoBean> listaPersonalDescansoMedico;
    private List<PersonalDescansoMedicoBean> listaPersonalInasistencia;
    private List<PersonalDescansoMedicoBean> listaPersonalAccidenteLaboral;

    private List<PersonalEvaPsicologicaBean> listaPersonalEvaPsicologicaBean;
    private PersonalEvaPsicologicaBean personalEvaPsicologicaBean;
    private Boolean flgLunes = false;
    private Boolean flgMartes = false;
    private Boolean flgMiercoles = false;
    private Boolean flgJueves = false;
    private Boolean flgViernes = false;
    private Boolean flgSabado = false;

    private Long diasVista;

    private List<PersonalInformacionBancariaBean> listaInformacionBancariaBean;
    private PersonalInformacionBancariaBean personalInformacionBancariaBean;

    private PersonalDesvinculacionBean personalDesvinculacionBean;
    private List<PersonalDesvinculacionBean> listaPersonalDesvinculacionBean;
    private Boolean flgTutor;
    private Boolean flgDocente;
    private String horasCalculadasLunes;
    private String horasCalculadasMartes;
    private String horasCalculadasMiercoles;
    private String horasCalculadasJueves;
    private String horasCalculadasViernes;
    private String horasCalculadasSabado;

    private String totalHorasGlobal;
    private Boolean flgHabilitarVacaciones = false;
    private String trabajoMenor = "";

    private List<PersonalPDFBean> listaPersonalPDFBean;
    private List<CodigoBean> listaTipoObjetoSubirPDF;
    private PersonalPDFBean personalPDFBean;
    private Integer periodo;

    private Boolean flgFalse = false;
    private List<PersonalVoluntariadoBean> listaPersonalVoluntariadoBean;
    private PersonalVoluntariadoBean personalVoluntariadoBean;

    private List<PersonalDatosHistoricoBean> listaPersonalDatosHistoricoBean;
    private PersonalDatosHistoricoBean personalDatosHistoricoBean;

    private Boolean flgTrabajoAltoRiesgo = false;
    private Boolean flgOcultarGradoAcademico = true;
    private Boolean flgEnProceso = false;
    private Boolean flgSoloCulminado = false;

    private Boolean flgFinanciamiento = false;
    private Boolean flgCertificado = false;

    private List<CodigoBean> listaTipoGrupoOcupacional;

    public void settearTipoSoli(String tp) {
        try {
            this.flgFiltroPersonal = true;
            this.flgFiltroPersona = false;

            if (tp.equals("solicitante")) {
//                if (getIdTipoSol() == null) {
//                    setIdTipoSol("COL");
//                }
//                this.flgSoli = true;
                changeTipo("soli");
            } else {
//                this.flgSoli = false;
//                changeTipo("resp");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void aaa() {
        try {
            System.out.println(getPersonalFiltroDepBean().getCodPer());
            System.out.println(getPersonalFiltroDepBean().getApepat());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void cargarPersonal() {
        try {
            PersonalService personalService = BeanFactory.getPersonalService();
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            List<UsuarioBean> listaUsuarioSesion = new ArrayList<>();
            listaUsuarioBean = new ArrayList<>();
            PersonalBean personal = new PersonalBean();
            personal.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalBean = personalService.obtenerPersonalUsuarios(personal);
            listaUsuarioSesion = usuarioService.obtenerUsuariosActivos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

//            for (int g = 0; g < listaUsuarioBean.size(); g++) {
            for (UsuarioBean usu : listaUsuarioSesion) {
                usu.setClave(new MaristaCifra().decrypt(usu.getClave()));
//                    listaUsuarioBean.set(g, usu);
                listaUsuarioBean.add(usu);
            }
//            }
            //                    usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave())); 
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void cambiarValAdmTodos() {
        try {
            if (valAdmTodos) {
                for (PersonalBean per : listaPersonalBean) {
                    per.setFlgSeleccion(true);
                }
            } else {
                for (PersonalBean per : listaPersonalBean) {
                    per.setFlgSeleccion(false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarUsuario() {
        String pagina = null;
        try {
            if (pagina == null) {
                String contrase馻;
                UsuarioService usuarioService = BeanFactory.getUsuarioService();
                for (PersonalBean per : listaPersonalBean) {
                    UsuarioBean usuarioBean = new UsuarioBean();
                    usuarioBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    PersonalBean personal = new PersonalBean();
                    LegajoService legajoService = BeanFactory.getLegajoService();
                    personal.setIdPersonal(per.getIdPersonal());
                    personal.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    personal = legajoService.obtenerLegajoId(personal);
                    usuarioBean.setUsuario(personal.getNroDoc());
                    contrase馻 = personal.getNroDoc().substring(4, 8);
                    usuarioBean.setClave(contrase馻);
                    usuarioBean.setStatus(1);
                    usuarioBean.getPersonalBean().setIdPersonal(per.getIdPersonal());
                    usuarioBean.getPersonalBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (personal.getStatusVista().equals(true)) {
                        if (per.getFlgSeleccion() == true) {
                            UsuarioBean usuario = new UsuarioBean();
                            usuario = usuarioService.obtenerUsuarioIgual(personal.getIdPersonal().toString());
                            if (usuario == null) {
                                usuarioService.insertarUsuarioMasivo(usuarioBean);
                            }
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("El usuario ya no labora, si desea crear usuario activarlo");
                        System.out.println("No se inserto ");
                    }
//                listaUsuarioBean = usuarioService.obtenerTodos(); 
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void cambiarValAdmUnoPorUno() {
        try {

            for (PersonalBean per : listaPersonalBean) {
                if (per.getFlgSeleccion() == true) {
                    per.setFlgSeleccion(true);
                } else {
                    per.setFlgSeleccion(false);
                }

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void changeTipo(String tipo) {
        try {
            String tipoSol = "";
            if (tipo.equals("soli")) {
                tipoSol = idTipoSol;
            } else {

            }
            switch (tipoSol) {
                case "PER":
                    this.flgFiltroPersona = true;
                    this.flgFiltroPersonal = false;
                    PersonaMB personaMB = (PersonaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("personaMB");
                    if (personaMB != null) {
                        personaMB.limpiarPersonaFiltro();
                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("personaMB", personaMB);
                    }
                    break;
                case "COL":
                    this.flgFiltroPersonal = true;
                    this.flgFiltroPersona = false;
                    personalFiltroDepBean = new PersonalBean();
                    getPersonalFiltroDepBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaPersonalFiltroBean = new ArrayList<>();
//                    personalFiltroDepBean = new PersonalBean();
//                    LegajoMB legajoMB = (LegajoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("legajoMB");
//                    if (legajoMB != null) {

//                    limpiarPersonalFiltro2();
//                        FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("legajoMB", legajoMB);
//                    }
                    break;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarDatos() {
        try {
            obtenerPersonalPorUnidadNegocio();
            //----------------------------Personal------------------------------
            //Pais
            PaisService paisService = BeanFactory.getPaisService();
            getListaNacionalidad();
            listaNacionalidad = paisService.obtenerPais();
            getListaPaisEmisionDoc();
            listaPaisEmisionDoc = paisService.obtenerPais();
            getListaPaisNacimiento();
            listaPaisNacimiento = paisService.obtenerPais();
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademico = gradoAcademicoService.obtenerTodosIniPriSec();
            //Lista C贸digos
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoObjetoSubirPDF();
            listaTipoObjetoSubirPDF = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_objeto_PDB));
            getListaDocPer();
            listaDocPer = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            getListaEstadoCivil();
            listaEstadoCivil = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_EST_CIV));
            getListaVivienda();
            listaVivienda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VIVIENDA));
            getListaTipoVia();
            listaTipoVia = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VIA));
            getListaTipoFormacion();
            listaTipoFormacion = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_FORMACIONCARISMA));
            getListaTipoZona();
            listaTipoZona = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ZONA));
            getListaCasa();
            listaCasa = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CASA));
            getListaStatusCasa();
            listaStatusCasa = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CASA));

            getListaOcupacion();
            listaOcupacion = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_OCUPACION));

            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelCong));
            } else {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelColegio));
            }

            getListaTipoSeguroPer();
            listaTipoSeguroPer = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_SEGURO_PER));
            getListaTipoPensionPer();
            listaTipoPensionPer = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_SEGURO_PER));

//            Entidad
            getListaEntidadEPS();
            EntidadService entidadService = BeanFactory.getEntidadService();
            listaEntidadEPS = entidadService.obtenerEntidadPorVista(MaristaConstantes.VIEW_ENT_SAL);
            getListaEntidadPension();
            listaEntidadPension = entidadService.obtenerEntidadPorVista(MaristaConstantes.VIEW_ENT_PROV);

            //Distrito
            DistritoService distritoService = BeanFactory.getDistritoService();
            getListaDepartamentoNaci();
            listaDepartamentoNaci = distritoService.obtenerDepartamentos();
            getListaDepartamentoDomi();
            listaDepartamentoDomi = listaDepartamentoNaci;

            //Dependientes
            getListaDocPerDependiente();
            listaDocPerDependiente = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            getListaEstadoCivilDependiente();
            listaEstadoCivilDependiente = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_EST_CIV));
            getListaNacionalidadDependiente();
            listaNacionalidadDependiente = paisService.obtenerPais();
            getListaParentesco();
            listaParentesco = codigoService.obtenerPorTipoDependiente(new TipoCodigoBean(MaristaConstantes.TIP_PARENTESCO));
            getListaOtrosParentesco();
            listaOtrosParentesco = codigoService.obtenerPorTipoOtrosDependiente(new TipoCodigoBean(MaristaConstantes.TIP_PARENTESCO));
            TipoFormacionService tipoFormacionDep = BeanFactory.getTipoFormacionService();

            getListaTipoFormacionDependiente();
            listaTipoFormacionDependiente = tipoFormacionDep.obtenerTodos();

            //Cargos
            cargarUniOrgYCargoPorUniNeg();

            //Enfermedad
            EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
            getListaEnfermedadBean();
            listaEnfermedadBean = enfermedadService.obtenerEnfermedad();
            getListaStatusEnfermedadBean();
            listaStatusEnfermedadBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_ENF));
            getListaTipoEnfermedad();
            listaTipoEnfermedad = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ENFERMEDAD));

            //Experiencia
            cargarAnoExp();
            listaMesesExp();

            //Contrato
            getListaTipoContrato();
            listaTipoContrato = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_CONTRATO));

            //Planilla Colegio
            getListaTipoPlanillaColegio();
            listaTipoPlanillaColegio = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PLANILLA_COLEGIO));

            //Grupo Ocupacional
            getListaTipoGrupoOcupacional();
            listaTipoGrupoOcupacional = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Grupo_Ocupacional));

            //Proceso Judicial
            getListaTipoProcesoJudicial();
            listaTipoProcesoJudicial = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PROJUD));
            getListaTipoRetencion();
            listaTipoRetencion = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_RETENCION));
            getListaTipoValor();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));
            getListaTipoModoPago();
            listaTipoModoPago = codigoService.obtenerCodigoDocEgreso();
            //Alergia
            getListaTipoAlergia();
            listaTipoAlergia = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ALERGIA));

            //Formacion Basica
            getListaEntidadFormacionBasica();
            listaEntidadFormacionBasica = entidadService.obtenerEntidadPorVistaPorUniNeg(MaristaConstantes.VIEW_ENT_SUP, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaTipoEstudios();
            listaTipoEstudios = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Estudios));
            getListaTipoOtrosEstudios();
            listaTipoOtrosEstudios = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_OtrosEstudios));
            getListaTipoFinanciamiento();
            listaTipoFinanciamiento = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Financiamiento));
            getListaTipoModalidadEst();
            listaTipoModalidadEst = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_Modalidad_Estudios));
            getListaPaisFormacionBasica();
            listaPaisFormacionBasica = paisService.obtenerPais();
            cargarAnoFormacionBasica();
            listaMesesForBas();
            NivelAcademicoService nivelAcademicoServiceEstBas = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBasica();
            listaNivelAcademicoBasica = nivelAcademicoServiceEstBas.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            //Formacion Superior
            getListaEntidadFormacionSuperior();
            listaEntidadFormacionSuperior = listaEntidadFormacionBasica;
            getListaPaisFormacionSuperior();
            listaPaisFormacionSuperior = paisService.obtenerPais();

            TipoFormacionService tipoFormacionServiceEstSup = BeanFactory.getTipoFormacionService();
            getListaTipoFormacionSuperior();
            listaTipoFormacionSuperior = tipoFormacionServiceEstSup.obtenerTipoFormacionSuperior(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            cargarAnoFormacionSuperior();
            listaMesesForSup();

            //Otros Estudios
//            getListaEntidadOtrosEstudios();
//            listaEntidadOtrosEstudios = listaEntidadFormacionBasica;
            getListaPaisOtrosEstudios();
            listaPaisOtrosEstudios = paisService.obtenerPais();
            cargarAnoOtrosEstudios();
            listaMesesOtrosEstudios();

            //Idioma    
            getListaTipoIdioma();
            listaTipoIdioma = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_IDIOMA));
            getListaNivel();
            listaNivel = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NIVEL_IDIOMA));
//            getListaEntidadIdioma();
//            listaEntidadIdioma = listaEntidadFormacionBasica;

            // Periodo
            Calendar miCalendario = Calendar.getInstance();
            periodo = miCalendario.get(Calendar.YEAR);
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosTipoNiveles() {
        try {
            List<Integer> listaTipoAccesoUsu = new ArrayList<>();
            CodigoService codigoService = BeanFactory.getCodigoService();
            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelCong));
            } else {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelColegio));
            }
            Integer size = listaTipoAccesoUsu.size();
            selectedIdTipoAcceso = new Integer[size];
            Integer count = 0;
            for (Integer lis : listaTipoAccesoUsu) {
                selectedIdTipoAcceso[count] = lis;
                count = count + 1;
            }
            Calendar miCalendario = Calendar.getInstance();
            setAnio(miCalendario.get(Calendar.YEAR));
            getListaMesAll();
            obtenerListaMeses();
            listaReporte();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void listaReporte() {
        listaReporte = new LinkedHashMap<>();
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePersonalAc", null), 1);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePersonalBa", null), 2);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaPlanillaCuentas", null), 3);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReporteTotalesTipo", null), 4);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaTipoPlaniCR", null), 5);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaTrabajadorCr", null), 6);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePlanillaCts", null), 7);
        listaReporte = Collections.unmodifiableMap(listaReporte);
    }

    public void cargarDatosReporte() {
        try {

            //----------------------------Personal------------------------------
            //Pais
            PaisService paisService = BeanFactory.getPaisService();
            getListaNacionalidad();
            listaNacionalidad = paisService.obtenerPais();
            getListaPaisEmisionDoc();
            listaPaisEmisionDoc = paisService.obtenerPais();
            getListaPaisNacimiento();
            listaPaisNacimiento = paisService.obtenerPais();

            //Lista C贸digos
            CodigoService codigoService = BeanFactory.getCodigoService();

            getListaEstadoCivil();
            listaEstadoCivil = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_EST_CIV));

            //Distrito
            DistritoService distritoService = BeanFactory.getDistritoService();

            getListaDepartamentoDomi();
            listaDepartamentoDomi = distritoService.obtenerDepartamentos();;

            //Cargos
            cargarUniOrgYCargoPorUniNeg();

            //Idioma    
            getListaTipoIdioma();
            listaTipoIdioma = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_IDIOMA));
            getListaNivel();
            listaNivel = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NIVEL_IDIOMA));
//            getListaEntidadIdioma();
//            listaEntidadIdioma = listaEntidadFormacionBasica;

            obtenerProvinciaDomiRep();
            obtenerDistritoDomiRep();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonalEnSolicitudCCH(Object personal, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            personalBean = (PersonalBean) personal;
            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
            if (flgSoli.equals(true)) {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setPersonalBean(personalBean);
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNombreSolicitante(personalBean.getNombreCompleto());
                if (idTipoSol != null) {
                    solicitudCajaCHMB.getSolicitudCajaCHBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setIdRespCheque(personalBean.getIdPersonal().toString());
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNomRespCheque(personalBean.getNombreCompleto());
                if (idTipoResp != null) {
                    solicitudCajaCHMB.getSolicitudCajaCHBean().setIdTipoRespCheque(idTipoResp);
                }
            }

            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudCajaCHMB", solicitudCajaCHMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerReporte() {
        try {
            System.out.println("reporte: " + orden);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonalEnDependiente(Object personal) {
        try {
            LegajoService legajoService = BeanFactory.getLegajoService();
            PersonalBean dep = (PersonalBean) personal;
            dep.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            dep = legajoService.obtenerLegajoId(dep);
            personalDependienteBean.setApepat(dep.getApepat());
            personalDependienteBean.setApemat(dep.getApemat());
            personalDependienteBean.setNombre(dep.getNombre());
            personalDependienteBean.setSexo(dep.getSexo());
            personalDependienteBean.setTipoEstadoCivilBean(dep.getTipoEstadoCivilBean());
            personalDependienteBean.setTipoDocPerBean(dep.getTipoDocPerBean());
            personalDependienteBean.setNroDoc(dep.getNroDoc());
            personalDependienteBean.setFecNac(dep.getFecNac());
            personalDependienteBean.setCollapsed(false);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaEnMovimientoAlmacen(Object personal, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            personalBean = (PersonalBean) personal;
            InventarioAlmacenMB inventarioAlmacenMB = (InventarioAlmacenMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("inventarioAlmacenMB");
            if (flgSoli.equals(true)) {
                inventarioAlmacenMB.getMovimientoAlmacenBean().setPersonalBean(personalBean);
                inventarioAlmacenMB.getMovimientoAlmacenBean().setEntregadoPor(personalBean.getNombreCompleto());
                if (idTipoSol != null) {
                    inventarioAlmacenMB.getMovimientoAlmacenBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                inventarioAlmacenMB.getMovimientoAlmacenBean().setIdRecibido(personalBean.getIdPersonal().toString());
                inventarioAlmacenMB.getMovimientoAlmacenBean().setRecibidopor(personalBean.getNombreCompleto());
                if (idTipoResp != null) {
                    inventarioAlmacenMB.getMovimientoAlmacenBean().setIdTipoRecibido(idTipoResp);
                }
            }

            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("inventarioAlmacenMB", inventarioAlmacenMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaEnEstudianteBloqueoResponsable(Object personal, Boolean flgSoli, String idTipoSol) {
        try {
            personalBean = (PersonalBean) personal;
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            if (flgSoli.equals(true)) {
                estudianteMB.getEstudianteBloqueoBean().setPersonalBean(personalBean);
                estudianteMB.getEstudianteBloqueoBean().setResponsable(personalBean.getNombreCompleto());
                if (idTipoSol != null) {
                    estudianteMB.getEstudianteBloqueoBean().setIdTipoSolicitante(idTipoSol);
                }
            }
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaEnMovimientoSolLog(Object personal, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            personalBean = (PersonalBean) personal;
            SolicitudLogisticoMB solicitudLogisticoMB = (SolicitudLogisticoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudLogisticoMB");
            if (flgSoli.equals(true)) {
                solicitudLogisticoMB.getSolicitudLogisticoBean().setPersonalBean(personalBean);
                solicitudLogisticoMB.getSolicitudLogisticoBean().setSolicitante(personalBean.getNombreCompleto());
                if (idTipoSol != null) {
                    solicitudLogisticoMB.getSolicitudLogisticoBean().setIdTipoSolicitante(idTipoSol);
                }
            }
            solicitudLogisticoMB.getSolicitudLogisticoBean().setPersonalBean(personalBean);
            solicitudLogisticoMB.getSolicitudLogisticoBean().setUnidadOrganicaBean(personalBean.getUnidadOrganicaBean());

            ActividadService actividadService = BeanFactory.getActividadService();
            List<ActividadBean> lista = new ArrayList<>();
            lista = actividadService.obtenerPorUnidadNegocioUniOrg(personalBean.getUnidadOrganicaBean().getIdUniOrg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(lista.size());
            solicitudLogisticoMB.setListaActividadBean(lista);
            System.out.println(solicitudLogisticoMB.getListaActividadBean().size());
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudLogisticoMB", solicitudLogisticoMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonalDinamico(Object personal, String origen) {
        try {
            personalBean = (PersonalBean) personal;
            if (origen.equals("cajaChica")) {
                CajaChicaMB cajaChicaMB = (CajaChicaMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMB");
                if (cajaChicaMB != null) {
                    cajaChicaMB.getCajaChicaBean().setPersonalBean(personalBean);
                    FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMB", cajaChicaMB);
                }
            }
            if (origen.equals("cajaGen")) {
                CajaGenMB cajaGenMB = (CajaGenMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaGenMB");
                cajaGenMB.getCajaGenBean().setIdSupervisa(personalBean);
                cajaGenMB.getCajaGenBeanDeposito().setIdSupervisa(personalBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaGenMB", cajaGenMB);
            }
            if (origen.equals("cuotaIngreso")) {
                CuotaIngresoMB cuotaIngresoMB = (CuotaIngresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cuotaIngresoMB");
                cuotaIngresoMB.getCajaCuotaIngresoBean().setSupervizaBean(personalBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cuotaIngresoMB", cuotaIngresoMB);
            }
            if (origen.equals("movLogisticoOri")) {
                MovimientoActivoMB movimientoActivoMB = (MovimientoActivoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("movimientoActivoMB");
                movimientoActivoMB.getMovimientoActivoBean().setRespOrigenBean(personalBean);
                movimientoActivoMB.getMovimientoActivoBean().setUniOrgOrigenBean(personalBean.getUnidadOrganicaBean());
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("movimientoActivoMB", movimientoActivoMB);
            }
            if (origen.equals("movLogisticoDes")) {
                MovimientoActivoMB movimientoActivoMB = (MovimientoActivoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("movimientoActivoMB");
                movimientoActivoMB.getMovimientoActivoBean().setResoDestinoBean(personalBean);
                movimientoActivoMB.getMovimientoActivoBean().setUniOrgDestinoBean(personalBean.getUnidadOrganicaBean());
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("movimientoActivoMB", movimientoActivoMB);
            }
            if (origen.equals("solCajaCh")) {
                SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
                solicitudCajaCHMB.getSolicitudCajaCHBean().setResChequeBean(personalBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudCajaCHMB", solicitudCajaCHMB);
            }
            if (origen.equals("jefeUniOrg")) {
                JefeUniOrgMB jefeUniOrgMB = (JefeUniOrgMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("jefeUniOrgMB");
                jefeUniOrgMB.getJefeUniOrgBean().setPersonalBean(personalBean);
//                jefeUniOrgMB.getJefeUniOrgBean().setUnidadOrganicaBean(personalBean.getUnidadOrganicaBean());

                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                personalBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

                PersonalCargoBean perCargo = new PersonalCargoBean();
                perCargo = personalCargoService.obtenerCargoActivoPorPersonal(personalBean);
                if (perCargo.getCargoBean().getIdCargo() != null) {
                    jefeUniOrgMB.getJefeUniOrgBean().setCargoBean(perCargo.getCargoBean());
                }

                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("jefeUniOrgMB", jefeUniOrgMB);
            }
//            if (origen.equals("cajaChicaMov")) {
//                CajaChicaMovMB cajaChicaMovMB = (CajaChicaMovMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cajaChicaMovMB");
//                cajaChicaMovMB.getCajaChicaMovBean().setPersonalBean(personalBean);
//                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cajaChicaMovMB", cajaChicaMovMB);
//            }

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String obtenerCargoActivoPorPersonal(PersonalBean personal) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                personal.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalCargoService.obtenerCargoActivoPorPersonal(personal);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void ponerPersonalAutoriza1EnTipoSolicitud(Object personal) {
        try {
            personalBean = (PersonalBean) personal;
            TipoSolicitudMB tipoSolicitudMB = (TipoSolicitudMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("tipoSolicitudMB");
            System.out.println("lleg贸 Personal1");
            tipoSolicitudMB.getTipoSolicitudBean().setIdAutorizaPer1Bean(personalBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("tipoSolicitudMB", tipoSolicitudMB);
            System.out.println("ok");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonalAutoriza2EnTipoSolicitud(Object personal) {
        try {
            personalBean = (PersonalBean) personal;
            TipoSolicitudMB tipoSolicitudMB = (TipoSolicitudMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("tipoSolicitudMB");
            System.out.println("lleg贸 Personal2");
            tipoSolicitudMB.getTipoSolicitudBean().setIdAutorizaPer2Bean(personalBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("tipoSolicitudMB", tipoSolicitudMB);
            System.out.println("ok");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonalAutoriza3EnTipoSolicitud(Object personal) {
        try {
            personalBean = (PersonalBean) personal;
            TipoSolicitudMB tipoSolicitudMB = (TipoSolicitudMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("tipoSolicitudMB");
            System.out.println("lleg贸 Personal3");
            tipoSolicitudMB.getTipoSolicitudBean().setIdAutorizaPer3Bean(personalBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("tipoSolicitudMB", tipoSolicitudMB);
            System.out.println("ok");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalFiltro() {
        try {
            personalFiltroBean = new PersonalBean();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            personalFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalCumpleFiltro() {
        try {
            personalFiltroBean = new PersonalBean();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            personalFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalCumpleaniosBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalRepFiltro() {
        try {
            personalRepFiltroBean = new PersonalRepBean();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            personalRepFiltroBean.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonaRepFiltroBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalFiltro2() {
        try {
            personalFiltroDepBean = new PersonalBean();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getPersonalFiltroDepBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPersonalFiltroDepBean().setCodPer(null);
            getPersonalFiltroDepBean().setApepat(null);
            getPersonalFiltroDepBean().setApemat(null);
            getPersonalFiltroDepBean().setNombre(null);
            getPersonalFiltroDepBean().setUnidadOrganicaBean(null);
            getPersonalFiltroDepBean().getUnidadOrganicaBean().setIdUniOrg(null);
            listaPersonalFiltroBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void calcularEdad() {
        try {
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
            menorEdad = personalDependienteService.obtenerDependienteFechaNacimiento(personalDependienteBean.getFecNac());
            if (Integer.parseInt(menorEdad) < 12) {
                menorEdad = "ES MENOR DE EDAD, TIENE " + menorEdad;
            } else {
                menorEdad = "";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Actualizar Entidad  
    public void actualizanEntidadEnsenanza() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            getListaEntidadFormacionBasica();
            listaEntidadFormacionBasica = entidadService.obtenerEntidadPorVista(MaristaConstantes.VIEW_ENT_SUP);
            getListaEntidadFormacionSuperior();
            listaEntidadFormacionSuperior = listaEntidadFormacionBasica;
//            getListaEntidadOtrosEstudios();
//            listaEntidadOtrosEstudios =  listaEntidadFormacionBasica;
//            getListaEntidadIdioma();
//            listaEntidadIdioma =  listaEntidadFormacionBasica;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    Documento
    public void obtenerDocPorCargo() {
        try {
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            listaDocumentoCargoBean = documentoCargoService.obtenerDocumentoPorCargo(personalCargoBean.getCargoBean().getIdCargo());
            CargoService cargoService = BeanFactory.getCargoService();
            String codigo = cargoService.obtenerGrupoOcupacional(personalCargoBean.getCargoBean().getIdCargo());
            System.out.println("grupoO: " + codigo);
            personalCargoBean.getCargoBean().getTipoGrupoOcupacionalBean().setCodigo(codigo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectDocumentoCargo(SelectEvent event) {
        try {
            DocumentoCargoService documentoCargoService = BeanFactory.getDocumentoCargoService();
            documentoCargoBean = (DocumentoCargoBean) event.getObject();
            documentoCargoBean = documentoCargoService.obtenerDocumentoCargoPorId(documentoCargoBean);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Formacion Dependiente//
    public void ponerCarreraDependiente(Object object) {
        CarreraBean carDep = (CarreraBean) object;
        personalDependienteBean.setCarreraBean(carDep);
    }

    public void obtenerNivelAcaDependiente() {
        try {
            NivelAcademicoService nivelAcademicoServiceEstSup = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoDependiente = nivelAcademicoServiceEstSup.obtenerPorTipoFormacionSinIniPriSec(personalDependienteBean.getGradoAcademicoBean().getNivelAcademicoBean().getTipoFormacionBean().getIdTipoFormacion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    

    public void obtenerNivelAcaOtroDependiente() {
        try {
            NivelAcademicoService nivelAcademicoServiceEstSup = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoDependiente = nivelAcademicoServiceEstSup.obtenerPorTipoFormacionSinIniPriSec(personalOtrosDependienteBean.getGradoAcademicoBean().getNivelAcademicoBean().getTipoFormacionBean().getIdTipoFormacion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraAreaDependientePorNivelAca() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaCarreraAreaDependiente = carreraAreaService.obtenerCarreraAreaPorNivelAcademico(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//    

    public void obtenerCarreraAreaOtrosDependientePorNivelAca() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaCarreraAreaDependiente = carreraAreaService.obtenerCarreraAreaPorNivelAcademico(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSubCarreraAreaDependiente() {
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaCarreraSubAreaDependiente = carreraSubAreaService.obtenerCarreraSubAreaPorCarreraArea(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSubCarreraAreaOtrosDependiente() {
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaCarreraSubAreaDependiente = carreraSubAreaService.obtenerCarreraSubAreaPorCarreraArea(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorNivelAcademicoDependiente() {
        try {
            CarreraService carreraServiceForSup = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraServiceForSup.obtenerCarreraPorNivelAcademico(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorNivelAcademicoOtrosDependiente() {
        try {
            CarreraService carreraServiceForSup = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraServiceForSup.obtenerCarreraPorNivelAcademico(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorAreaDependiente() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraService.obtenerCarreraPorCarreraArea(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorAreaOtrosDependiente() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraService.obtenerCarreraPorCarreraArea(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorSubAreaDependiente() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraService.obtenerCarreraPorCarreraSubArea(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getIdCarreraSubArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorSubAreaOtrosDependiente() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaCarreraDependiente = carreraService.obtenerCarreraPorCarreraSubArea(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getIdCarreraSubArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaDependiente() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoDependiente = gradoAcademicoService.obtenerGradoAcaPorNivelAca(personalDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaOtrosDependiente() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoDependiente = gradoAcademicoService.obtenerGradoAcaPorNivelAca(personalOtrosDependienteBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Formacion Basica Grado//
    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFormacionBasica = gradoAcademicoService.obtenerGradoAcaPorNivelAca(personalFormacionBasicaBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Formacion Superior Grado//
    public void obtenerNivelAcaSuperior() {
        try {
            NivelAcademicoService nivelAcademicoServiceEstSup = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoSuperior = nivelAcademicoServiceEstSup.obtenerPorTipoFormacionSinIniPriSec(personalFormacionSuperiorBean.getGradoAcademicoBean().getNivelAcademicoBean().getTipoFormacionBean().getIdTipoFormacion());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGradoAcaSuperior() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFormacionSuperior = gradoAcademicoService.obtenerGradoAcaPorNivelAca(personalFormacionSuperiorBean.getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    // ---------------Carrera Formacion Superior -------------------------//
    public void obtenerCarreraAreaSuperiorPorNivelAca() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaFormacionSuperiorCarreraArea = carreraAreaService.obtenerCarreraAreaPorNivelAcademico(personalFormacionSuperiorBean.getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraAreaSuperiorPorGradoAca() {
        try {
            CarreraAreaService carreraAreaService = BeanFactory.getCarreraAreaService();
            listaFormacionSuperiorCarreraArea = carreraAreaService.obtenerCarreraAreaPorGradoAcademico(personalFormacionSuperiorBean.getGradoAcademicoBean().getIdGradoAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerSubCarreraAreaSuperior() {
        try {
            CarreraSubAreaService carreraSubAreaService = BeanFactory.getCarreraSubAreaService();
            listaFormacionSuperiorCarreraSubArea = carreraSubAreaService.obtenerCarreraSubAreaPorCarreraArea(personalFormacionSuperiorBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorNivelAcademicoSuperior() {
        try {
            CarreraService carreraServiceForSup = BeanFactory.getCarreraService();
            listaFormacionSuperiorCarrera = carreraServiceForSup.obtenerCarreraPorNivelAcademico(personalFormacionSuperiorBean.getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorGradoAca() {
        try {
            CarreraService carreraServiceForSup = BeanFactory.getCarreraService();
            listaFormacionSuperiorCarrera = carreraServiceForSup.obtenerCarreraPorGradoAca(personalFormacionSuperiorBean.getGradoAcademicoBean().getIdGradoAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorAreaSuperior() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaFormacionSuperiorCarrera = carreraService.obtenerCarreraPorCarreraArea(personalFormacionSuperiorBean.getCarreraBean().getCarreraSubAreaBean().getCarreraAreaBean().getIdCarreraArea());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorSubAreaSuperior() {
        try {
            CarreraService carreraService = BeanFactory.getCarreraService();
            listaFormacionSuperiorCarrera = carreraService.obtenerCarreraPorCarreraSubArea(personalFormacionSuperiorBean.getCarreraBean().getCarreraSubAreaBean().getIdCarreraSubArea());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCarrera() {
        formacionSuperiorCarreraBean = new CarreraBean();
    }

    public void limpiarCarreraDependiente() {
        carreraBeanDependiente = new CarreraBean();
    }
    //-------------fin carrera---------------------------//

    //meses for bas
    public void listaMesesForBas() {
        listaMesesForBasMap = new LinkedHashMap<>();
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMesesForBasMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMesesForBasMap = Collections.unmodifiableMap(listaMesesForBasMap);
    }

    //meses for sup
    public void listaMesesForSup() {
        listaMesesForSupMap = new LinkedHashMap<>();
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMesesForSupMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMesesForSupMap = Collections.unmodifiableMap(listaMesesForSupMap);
    }

    public void listaMesesOtrosEstudios() {
        listaMesesOtrosEstudiosMap = new LinkedHashMap<>();
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMesesOtrosEstudiosMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMesesOtrosEstudiosMap = Collections.unmodifiableMap(listaMesesOtrosEstudiosMap);
    }

    public void listaMesesExp() {
        listaMesesExpMap = new LinkedHashMap<>();
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
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
    }

    public void obtenerListaMeses() {
        try {
            listaMesAll.add(new MesBean(1, MaristaConstantes.ENERO));
            listaMesAll.add(new MesBean(2, MaristaConstantes.MES_FEBRERO));
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
            listaMesAll.add(new MesBean(13, MaristaConstantes.JULIO_GRATI));
            listaMesAll.add(new MesBean(14, MaristaConstantes.DICIEMBRE_GRATI));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------Distrito-----------------------------//
    public void obtenerProvinciaNaci() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaNaci = distritoService.obtenerProvinciaPorDep(personalBean.getDistritoNacBean().getProvinciaBean().getDepartamentoBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistritoNaci() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoNaci = distritoService.obtenerDistritoPorProv(personalBean.getDistritoNacBean().getProvinciaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProvinciaDomi() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaProvinciaDomi = distritoService.obtenerProvinciaPorDep(personalBean.getDistritoDomBean().getProvinciaBean().getDepartamentoBean());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCodigoViaZona(Integer n) {
        try {
            if (n == 1) {
                if (personalBean.getTipoViaBean().getIdCodigo() != null
                        && !personalBean.getTipoViaBean().getIdCodigo().equals(0)) {
                    Integer codigoVia = personalBean.getTipoViaBean().getIdCodigo();
                    codigoVia = (codigoVia % 100);
                    String codigoViaS = codigoVia.toString();
                    if (codigoViaS.length() == 1) {
                        personalBean.getTipoViaBean().setCodigoAyuda("0" + codigoVia);
                    } else {
                        personalBean.getTipoViaBean().setCodigoAyuda(codigoVia.toString());
                    }
                }
            }
            if (n == 2) {
//                Integer codigoZona = personalBean.getTipoZonaBean().getIdCodigo();
//                personalBean.getTipoZonaBean().setIdCodigo(codigoZona % 100); 
                if (personalBean.getTipoZonaBean().getIdCodigo() != null
                        && !personalBean.getTipoZonaBean().getIdCodigo().equals(0)) {
                    Integer codigoZona = personalBean.getTipoZonaBean().getIdCodigo();
                    codigoZona = (codigoZona % 100);
                    String codigoZonaS = codigoZona.toString();
                    if (codigoZonaS.length() == 1) {
                        personalBean.getTipoZonaBean().setCodigoAyuda("0" + codigoZona);
                    } else {
                        personalBean.getTipoZonaBean().setCodigoAyuda(codigoZona.toString());
                    }
                }
            }
            if (n == 3) {
                if (!personalBean.getTipoDocPerBean().getIdCodigo().equals(0)
                        && personalBean.getTipoDocPerBean().getIdCodigo() != null) {
                    Integer codigoDNI = personalBean.getTipoDocPerBean().getIdCodigo();
                    codigoDNI = (codigoDNI % 100);
                    String codigoDNIS = codigoDNI.toString();
                    if (codigoDNIS.length() == 1) {
                        personalBean.getTipoDocPerBean().setCodigoAyuda("0" + codigoDNI);
                    } else {
                        personalBean.getTipoDocPerBean().setCodigoAyuda(codigoDNI.toString());
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProvinciaDomiRep() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            DepartamentoBean dep = new DepartamentoBean();
            dep.setIdDepartamento(personalRepFiltroBean.getIdDepartamento());
            listaProvinciaDomi = distritoService.obtenerProvinciaPorDep(dep);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistritoDomi() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            listaDistritoDomi = distritoService.obtenerDistritoPorProv(personalBean.getDistritoDomBean().getProvinciaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDistritoDomiRep() {
        try {
            DistritoService distritoService = BeanFactory.getDistritoService();
            ProvinciaBean prov = new ProvinciaBean();
            prov.setIdProvincia(personalRepFiltroBean.getIdProvincia());
            listaDistritoDomi = distritoService.obtenerDistritoPorProv(prov);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerStatus() {
        try {
            System.out.println("chau");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //-------------- Fin Distrito-----------------------------//
//    public void ponerPersonal(Object personal) {
//        try {
//            personalBean = (PersonalBean) personal;
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
    public String insertarPersonal() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LegajoService legajoService = BeanFactory.getLegajoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personalBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                legajoService.insertarPersonal(personalBean);
                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                if (!personalVoluntariadoBean.getDescripcion().equals("") && personalVoluntariadoBean.getDescripcion() != null
                        && !personalVoluntariadoBean.getFechaVoluntariado().equals("") && personalVoluntariadoBean.getFechaVoluntariado() != null) {
                    System.out.println("entro");
                    System.out.println("fecha: " + personalVoluntariadoBean.getFechaVoluntariado());
                    PersonalVoluntariadoBean per = new PersonalVoluntariadoBean();
                    per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    per.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                    per.setDescripcion(personalVoluntariadoBean.getDescripcion());
                    per.setFechaVoluntariado(personalVoluntariadoBean.getFechaVoluntariado());
                    per.setCreaPor(beanUsuarioSesion.getUsuario());
                    legajoService.insertarPersonalVoluntariado(per);
                    listaPersonalVoluntariadoBean = legajoService.obtenerPersonalVoluntariadoLista(per);
                }
                PersonalDatosHistoricoBean perVista = new PersonalDatosHistoricoBean();
                perVista = legajoService.obtenerUltimoDirecEstadoCivil(personalBean.getIdPersonal(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (perVista == null) {
                    PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
                    historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                    historico.setDireccion(personalBean.getDomicilio());
                    historico.setCreaPor(beanUsuarioSesion.getUsuario());
                    historico.getTipoEstadoCivil().setIdCodigo(personalBean.getTipoEstadoCivilBean().getIdCodigo());
                    legajoService.insertarPersonalHistorico(historico);
                    listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
                } else {
                    if (!perVista.getDireccion().equals("") && perVista.getDireccion() != null
                            && !perVista.getTipoEstadoCivil().getIdCodigo().equals(0) && perVista.getTipoEstadoCivil().getIdCodigo() != null) {
                        if (!perVista.getDireccion().equals(personalBean.getDomicilio()) || !perVista.getTipoEstadoCivil().getIdCodigo().equals(personalBean.getTipoEstadoCivilBean().getIdCodigo())) {
                            if (!personalBean.getDomicilio().equals("") && personalBean.getDomicilio() != null
                                    && !personalBean.getTipoEstadoCivilBean().getIdCodigo().equals(0) && personalBean.getTipoEstadoCivilBean().getIdCodigo() != null) {
                                PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
                                historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                                historico.setDireccion(personalBean.getDomicilio());
                                historico.setCreaPor(beanUsuarioSesion.getUsuario());
                                historico.getTipoEstadoCivil().setIdCodigo(personalBean.getTipoEstadoCivilBean().getIdCodigo());
                                legajoService.insertarPersonalHistorico(historico);
                                listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonal() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LegajoService legajoService = BeanFactory.getLegajoService();
                legajoService.modificarPersonal(personalBean);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

                personalBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (!personalVoluntariadoBean.getDescripcion().equals("") && personalVoluntariadoBean.getDescripcion() != null
                        && !personalVoluntariadoBean.getFechaVoluntariado().equals("") && personalVoluntariadoBean.getFechaVoluntariado() != null) {
                    PersonalVoluntariadoBean per = new PersonalVoluntariadoBean();
                    per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    per.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                    per.setDescripcion(personalVoluntariadoBean.getDescripcion());
                    per.setFechaVoluntariado(personalVoluntariadoBean.getFechaVoluntariado());
                    per.setCreaPor(beanUsuarioSesion.getUsuario());
                    legajoService.insertarPersonalVoluntariado(per);
                    listaPersonalVoluntariadoBean = legajoService.obtenerPersonalVoluntariadoLista(per);
                }
                PersonalDatosHistoricoBean perVista = new PersonalDatosHistoricoBean();
                perVista = legajoService.obtenerUltimoDirecEstadoCivil(personalBean.getIdPersonal(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (perVista == null) {
                    PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
                    historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                    historico.setDireccion(personalBean.getDomicilio());
                    historico.setCreaPor(beanUsuarioSesion.getUsuario());
                    historico.getTipoEstadoCivil().setIdCodigo(personalBean.getTipoEstadoCivilBean().getIdCodigo());
                    legajoService.insertarPersonalHistorico(historico);
                    listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
                } else {
                    if (!perVista.getDireccion().equals("") && perVista.getDireccion() != null
                            && !perVista.getTipoEstadoCivil().getIdCodigo().equals(0) && perVista.getTipoEstadoCivil().getIdCodigo() != null) {
                        if (!perVista.getDireccion().equals(personalBean.getDomicilio()) || !perVista.getTipoEstadoCivil().getIdCodigo().equals(personalBean.getTipoEstadoCivilBean().getIdCodigo())) {
                            if (!personalBean.getDomicilio().equals("") && personalBean.getDomicilio() != null
                                    && !personalBean.getTipoEstadoCivilBean().getIdCodigo().equals(0) && personalBean.getTipoEstadoCivilBean().getIdCodigo() != null) {

                                PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
                                historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
                                historico.setDireccion(personalBean.getDomicilio());
                                historico.setCreaPor(beanUsuarioSesion.getUsuario());
                                historico.getTipoEstadoCivil().setIdCodigo(personalBean.getTipoEstadoCivilBean().getIdCodigo());
                                legajoService.insertarPersonalHistorico(historico);
                                listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarLegajo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LegajoService legajoService = BeanFactory.getLegajoService();
                legajoService.eliminarPersonal(personalBean);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

                personalBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarLegajo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalService per = BeanFactory.getPersonalService();

                if (personalBean.getIdPersonal() != null) {
                    modificarPersonal();
                } else {
                    String dni = per.validarDni(personalBean.getNroDoc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (dni == null) {
                        insertarPersonal();
                    } else {
                        new MensajePrime().addInformativeMessagePer("Personal ya Existe");
                    }
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarLegajo() {

        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        personalBean = new PersonalBean();
        personalBean.setCreaPor(beanUsuarioSesion.getUsuario());
        personalBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
        limpiarPersonalFormacionBasica();
        limpiarPersonalFormacionSuperior();
        limpiarPersonalIdioma();
        limpiarPersonalOtrosEstudios();
        limpiarPersonalDependiente();
        limpiarPersonalAlergia();
        limpiarPersonalEnfermedad();
        limpiarPersonalContrato();
        limpiarPersonalCargo();
        limpiarPersonalExperiencia();
        limpiarPersonalProcesoJudicial();
        limpiarPersonalDocumento();
        listaPersonalDependienteBean = new ArrayList<>();
        listaPersonalCargoBean = new ArrayList<>();
        listaPersonalEnfermedadBean = new ArrayList<>();
        listaPersonalExperienciaBean = new ArrayList<>();
        listaPersonalContratoBean = new ArrayList<>();
        listaPersonalProcesoJudicialBean = new ArrayList<>();
        listaPersonalAlergiaBean = new ArrayList<>();
        listaPersonalFormacionBasicaBean = new ArrayList<>();
        listaPersonalFormacionSuperiorBean = new ArrayList<>();
        listaPersonalIdiomaBean = new ArrayList<>();
        listaPersonalOtrosEstudiosBean = new ArrayList<>();
        listaPersonalDocumentoBean = new ArrayList<>();
    }

    public void obtenerLegajoId(Object object) {
        try {
            personalBean = (PersonalBean) object;
            LegajoService legajoService = BeanFactory.getLegajoService();
            personalBean = legajoService.obtenerLegajoId(personalBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String cambiarEstadoPersonal() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LegajoService legajoService = BeanFactory.getLegajoService();
                if (personalBean.getEstadoActual()) {
                    personalBean.setStatus(true);
                } else {
                    personalBean.setStatus(false);
                }
                legajoService.cambiarEstado(personalBean);
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personalBean = new PersonalBean();
                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerTodos() {
        try {
            LegajoService legajoService = BeanFactory.getLegajoService();
            listaPersonalBean = legajoService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalPorUnidadNegocio() {
        try {
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (beanUsuarioSesion != null) {
//                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
                Integer estado = 0;
                //validar perfil
                PerfilService perfilService = BeanFactory.getPerfilService();
                List<PerfilBean> listaPerfilBean = perfilService.obtenerUsarioPerfil(beanUsuarioSesion);
                for (int i = 0; i < listaPerfilBean.size(); i++) {
                    if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.ENCARGADO_RRHH)) {
                        estado = 1;
                        break;
                    } else {
                        estado = 0;
                    }
                }
                if (estado.equals(1)) {
                    listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());

                } else {
                    listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocioPorId(beanUsuarioSesion.getUsuario(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (!listaPersonalPorUniNegBean.isEmpty()) {
                        rowSelectPorPersonal(listaPersonalPorUniNegBean.get(0));
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroPersonal() {
        try {
            int estado = 0;
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalFiltroBean.getCodPer() != null && !personalFiltroBean.getCodPer().equals("")) {
                personalFiltroBean.setCodPer(personalFiltroBean.getCodPer().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApepat() != null && !personalFiltroBean.getApepat().equals("")) {
                personalFiltroBean.setApepat(personalFiltroBean.getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApemat() != null && !personalFiltroBean.getApemat().equals("")) {
                personalFiltroBean.setApemat(personalFiltroBean.getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getNombre() != null && !personalFiltroBean.getNombre().equals("")) {
                personalFiltroBean.setNombre(personalFiltroBean.getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg() != null && !personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonalBean = legajoService.obtenerFiltroPersonal(personalFiltroBean);
                if (listaPersonalBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroPersonal2() {
        try {
            System.out.println(personalFiltroDepBean.getCodPer());
            int estado = 0;
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalFiltroDepBean.getCodPer() != null && !personalFiltroDepBean.getCodPer().equals("")) {
                personalFiltroDepBean.setCodPer(personalFiltroDepBean.getCodPer().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroDepBean.getApepat() != null && !personalFiltroDepBean.getApepat().equals("")) {
                personalFiltroDepBean.setApepat(personalFiltroDepBean.getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroDepBean.getApemat() != null && !personalFiltroDepBean.getApemat().equals("")) {
                personalFiltroDepBean.setApemat(personalFiltroDepBean.getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroDepBean.getNombre() != null && !personalFiltroDepBean.getNombre().equals("")) {
                personalFiltroDepBean.setNombre(personalFiltroDepBean.getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroDepBean.getUnidadOrganicaBean().getIdUniOrg() != null && !personalFiltroDepBean.getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                personalFiltroDepBean.getUnidadOrganicaBean().setIdUniOrg(personalFiltroDepBean.getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalFiltroBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonalFiltroBean = legajoService.obtenerFiltroPersonal(personalFiltroDepBean);
                if (listaPersonalFiltroBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroPersonalRep() {
        try {
            String textoFiltro = "";
            StringBuilder sb = new StringBuilder();

            int estado = 1;
            LegajoService legajoService = BeanFactory.getLegajoService();
            personalRepFiltroBean.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (personalRepFiltroBean.getFechaIngIniDate() != null) {
                Timestamp t = new Timestamp(personalRepFiltroBean.getFechaIngIniDate().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                personalRepFiltroBean.setFechaIngIniDate(t);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(personalRepFiltroBean.getFechaIngIniDate());
                sb.append("Fecha de Ingreso: ").append(date).append(" al ");
                estado = 1;
            }
            if (personalRepFiltroBean.getFechaIngFinDate() != null) {
                Timestamp u = new Timestamp(personalRepFiltroBean.getFechaIngFinDate().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                personalRepFiltroBean.setFechaIngFinDate(u);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(personalRepFiltroBean.getFechaIngFinDate());
                sb.append(date).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getFechaBajaIniDate() != null) {
                Timestamp t = new Timestamp(personalRepFiltroBean.getFechaBajaIniDate().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                personalRepFiltroBean.setFechaBajaIniDate(t);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(personalRepFiltroBean.getFechaBajaIniDate());
                sb.append("Fecha de Baja: ").append(date).append(" al ");
                estado = 1;
            }
            if (personalRepFiltroBean.getFechaBajaFinDate() != null) {
                Timestamp u = new Timestamp(personalRepFiltroBean.getFechaBajaFinDate().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                personalRepFiltroBean.setFechaBajaFinDate(u);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(personalRepFiltroBean.getFechaBajaFinDate());
                sb.append(date).append(", ");
                estado = 1;
            }

            if (personalRepFiltroBean.getIdUniOrg() != null && !personalRepFiltroBean.getIdUniOrg().equals(0)) {
                personalRepFiltroBean.setIdUniOrg(personalRepFiltroBean.getIdUniOrg());
                UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
                UnidadOrganicaBean unidadOrganica = new UnidadOrganicaBean();
                unidadOrganica.setIdUniOrg(personalRepFiltroBean.getIdUniOrg());
                unidadOrganica = unidadOrganicaService.obtenerUnidadOrganicaPorId(unidadOrganica);
                estado = 1;
                sb.append("Unidad Org醤ica: ").append(unidadOrganica.getNombreUniOrg()).append(", ");
            }
            if (personalRepFiltroBean.getIdCargo() != null && !personalRepFiltroBean.getIdCargo().equals(0)) {
                personalRepFiltroBean.setIdCargo(personalRepFiltroBean.getIdCargo());
                CargoService cargoService = BeanFactory.getCargoService();
                CargoBean cargo = new CargoBean();
                cargo = cargoService.obtenerCargoPorId(personalRepFiltroBean.getIdCargo());
                sb.append("Cargo: ").append(cargo.getNombre()).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getSexo() != null && !personalRepFiltroBean.getSexo().equals("")) {
                personalRepFiltroBean.setSexo(personalRepFiltroBean.getSexo().trim());
                String sexo = "";
                if (personalRepFiltroBean.getSexo().equals("1")) {
                    sexo = "Masculino";
                }
                if (personalRepFiltroBean.getSexo().equals("0")) {
                    sexo = "Femenino";
                }
                sb.append("Sexo: ").append(sexo).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getFlgPadres() != null && personalRepFiltroBean.getFlgPadres().equals(Boolean.TRUE)) {
                personalRepFiltroBean.setFlgPadres(personalRepFiltroBean.getFlgPadres());
                sb.append("Padres");
                String sexoH = "";
                if (personalRepFiltroBean.getFlgPadresConHijosH() != null) {
                    if (personalRepFiltroBean.getFlgPadresConHijosH().equals(Boolean.TRUE)) {
                        sexoH = "Con hijos";
                    }
                    if (personalRepFiltroBean.getFlgPadresConHijosH().equals(Boolean.FALSE)) {
                        sexoH = "Con hijas";
                    }
                    sb.append(": ").append(sexoH).append(", ");
                } else {
                    sb.append(sexoH).append(", ");
                }
                estado = 1;
            }
            if (personalRepFiltroBean.getFlgMadres() != null && personalRepFiltroBean.getFlgMadres().equals(Boolean.TRUE)) {
                personalRepFiltroBean.setFlgMadres(personalRepFiltroBean.getFlgMadres());
                sb.append("Madres");
                String sexoH = "";
                if (personalRepFiltroBean.getFlgMadresConHijosH() != null) {
                    if (personalRepFiltroBean.getFlgMadresConHijosH().equals(Boolean.TRUE)) {
                        sexoH = "Con hijos";
                    }
                    if (personalRepFiltroBean.getFlgMadresConHijosH().equals(Boolean.FALSE)) {
                        sexoH = "Con hijas";
                    }
                    sb.append(": ").append(sexoH).append(", ");
                } else {
                    sb.append(sexoH).append(", ");
                }
                estado = 1;
            }
            if (personalRepFiltroBean.getEstado() != null && !personalRepFiltroBean.getEstado().equals("")) {
                personalRepFiltroBean.setEstado(personalRepFiltroBean.getEstado().trim());
                String est = "";
                if (personalRepFiltroBean.getEstado().equals("1")) {
                    est = "Activo";
                }
                if (personalRepFiltroBean.getEstado().equals("0")) {
                    est = "Inactivo";
                }
                sb.append("Estado: ").append(est).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getIdPaisNacionalidad() != null && !personalRepFiltroBean.getIdPaisNacionalidad().equals(0)) {
                personalRepFiltroBean.setIdPaisNacionalidad(personalRepFiltroBean.getIdPaisNacionalidad());
                PaisService paisService = BeanFactory.getPaisService();
                PaisBean pais = new PaisBean();
                pais = paisService.obtenerPaisPorId(personalRepFiltroBean.getIdPaisNacionalidad());
                sb.append("Nacionalidad: ").append(pais.getNacionalidad()).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getIdPaisNacimiento() != null && !personalRepFiltroBean.getIdPaisNacimiento().equals(0)) {
                personalRepFiltroBean.setIdPaisNacimiento(personalRepFiltroBean.getIdPaisNacimiento());
                PaisService paisService = BeanFactory.getPaisService();
                PaisBean pais = new PaisBean();
                pais = paisService.obtenerPaisPorId(personalRepFiltroBean.getIdPaisNacimiento());
                sb.append("Pa韘 de Nac.: ").append(pais.getNombre()).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getIdDistritoDomi() != null && !personalRepFiltroBean.getIdDistritoDomi().equals(0)) {
                personalRepFiltroBean.setIdDistritoDomi(personalRepFiltroBean.getIdDistritoDomi());
                DistritoService distritoService = BeanFactory.getDistritoService();
                DistritoBean distrito = new DistritoBean();
                distrito = distritoService.obtenerDistritoPorId(personalRepFiltroBean.getIdDistritoDomi());
                sb.append("Distrito domicilio.: ").append(distrito.getNombre()).append(", ");
                estado = 1;
            }
            if (personalRepFiltroBean.getEstadoCivil() != null && !personalRepFiltroBean.getEstadoCivil().equals("")) {
                personalRepFiltroBean.setEstadoCivil(personalRepFiltroBean.getEstadoCivil().trim());
                sb.append("Estado Civil: ").append(personalRepFiltroBean.getEstadoCivil());
                estado = 1;
            } else if (estado == 0) {
                this.collapsed = Boolean.FALSE;
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonaRepFiltroBean = new ArrayList<>();
            }
            textoFiltro = sb.toString();
            if (estado == 1) {
//                this.collapsed = Boolean.TRUE;
                if (personalRepFiltroBean.getFlgStatusFiltro() == true) {
                    System.out.println("sin filtro-- todos");
                } else {
                    personalRepFiltroBean.setFlgStatus(personalRepFiltroBean.getFlgStatus());
                }
                listaPersonaRepFiltroBean = legajoService.obtenerPersonalRepPorFiltro(personalRepFiltroBean);
                for (PersonalRepBean perso : listaPersonaRepFiltroBean) {
                    perso.setTextoFiltro("Filtros: " + textoFiltro);
                }
                if (listaPersonaRepFiltroBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerNacionalidadPersonal() {
        try {
            for (int i = 0; i < listaPaisNacimiento.size(); i++) {
                if (personalBean.getPaisNacimientoBean().getIdPais() != null
                        && listaPaisNacimiento.get(i).getIdPais().toString().equals(personalBean.getPaisNacimientoBean().getIdPais().toString())) {
                    personalBean.getPaisNacionalidadBean().setIdPais(listaPaisNacimiento.get(i).getIdPais());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoPublicaPri(Integer n) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            if (n == 1) {
                listaEntidadEPSNew = new ArrayList<>();
                if (personalBean.getTipoSeguroPersonalBean().getIdCodigo().equals(15901)) {
                    listaEntidadEPSNew = entidadService.obtenerEntidadPorVistaLegajoNew(MaristaConstantes.VIEW_ENT_SAL, 11002);
                } else if (personalBean.getTipoSeguroPersonalBean().getIdCodigo().equals(15902)) {
                    listaEntidadEPSNew = entidadService.obtenerEntidadPorVistaLegajoNew(MaristaConstantes.VIEW_ENT_SAL, 11001);
                }
            }
            if (n == 2) {
                listaEntidadPensionNew = new ArrayList<>();
                if (personalBean.getTipoPensionPersonal().getIdCodigo().equals(15901)) {
                    listaEntidadPensionNew = entidadService.obtenerEntidadPorVistaLegajoNew(MaristaConstantes.VIEW_ENT_PROV, 11002);
                } else if (personalBean.getTipoPensionPersonal().getIdCodigo().equals(15902)) {
                    listaEntidadPensionNew = entidadService.obtenerEntidadPorVistaLegajoNew(MaristaConstantes.VIEW_ENT_PROV, 11001);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPlanillaColegio(Integer IdCodigo) {
        try {
            System.out.println("codigo: " + IdCodigo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void sumaPorcentaje() {
        try {
            if (personalBean.getIdPersonal() != null) {
                sumaPorcentaje = (personalBean.getCr1Porc() + personalBean.getCr2Porc() + personalBean.getCr3Porc() + personalBean.getCr4Porc() + personalBean.getCr5Porc());
                System.out.println("suma: " + sumaPorcentaje);
                if (sumaPorcentaje == 100) {
                    this.flgGrabar = true;
                } else if (sumaPorcentaje > 100) {
                    this.flgGrabar = false;
                    new MensajePrime().addInformativeMessagePer("etiquetaPasa");
                } else {
                    this.flgGrabar = false;
                    new MensajePrime().addInformativeMessagePer("etiquetaBajo");
                }
            } else {
                this.flgGrabar = false;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGrabar() {
        try {
            if (personalBean.getIdPersonal() != null) {
                if (personalBean.getStatusVista() == false) {
                    this.flgGrabarStatus = true;
                    this.flgGrabar = false;
                } else {
                    this.flgGrabarStatus = false;
                    this.flgGrabar = false;
                }
            } else {
                this.flgGrabarStatus = false;
                this.flgGrabar = false;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirTodosPdfActivos(Integer status) {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePersonalCRActivos.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<PlanillaPersonalCRRepBean> listaCRPlanillaCabecera = new ArrayList<>();
            List<PlanillaPersonalCRRepBean> listaCRPlanillaCabecera2 = new ArrayList<>();

            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCabecera = personalService.obtenerPlanillaPersonalCR(uniNeg, status, listaTipoAccesoConsiderar.get(j));

                for (PlanillaPersonalCRRepBean listaCR : listaCRPlanillaCabecera) {
                    listaCRPlanillaCabecera2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCabecera2.size());

                }
            }
            if (!listaCRPlanillaCabecera2.isEmpty()) {
                for (int j = 0; j < listaCRPlanillaCabecera2.size(); j++) {
                    List<PlanillaPersonalCRRepBean> listaCRPlanilla = new ArrayList<>();
                    listaCRPlanilla = personalService.obtenerPlanillaPersonalCRDetalle(uniNeg, status, listaCRPlanillaCabecera2.get(j).getId());
                    listaCRPlanillaCabecera2.get(j).setListaDetPlanilla(listaCRPlanilla);

                    List<PlanillaPersonalCRRepBean> listaCRActivosSinNada = new ArrayList<>();
                    listaCRActivosSinNada = personalService.obtenerPlanillaPersonalCRDetalleSinNada(uniNeg, status, listaCRPlanillaCabecera2.get(j).getId());
                    listaCRPlanillaCabecera2.get(j).setListaSinNada(listaCRActivosSinNada);

                    List<PlanillaPersonalCRRepBean> listaTotales = new ArrayList<>();
                    List<PlanillaPersonalCRRepBean> listaTotalesAyuda = new ArrayList<>();
                    for (int k = 0; k < listaCRPlanillaCabecera2.size(); k++) {
                        listaTotalesAyuda = personalService.obtenerPlanillaPersonalCRTotales(uniNeg, status, listaCRPlanillaCabecera2.get(k).getId());
                        for (PlanillaPersonalCRRepBean ayuda : listaTotalesAyuda) {
                            listaTotales.add(ayuda);
                        }
                        listaCRPlanillaCabecera2.get(j).setListaTotales(listaTotales);
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCabecera2);
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCuentas() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTipoPlanillaCR.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR2 = new ArrayList<>();
            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCR = personalService.obtenerPlanillaCuenta(uniNeg, listaTipoAccesoConsiderar.get(j), mes, anio);

                for (ConceptoPlanillaRepBean listaCR : listaCRPlanillaCR) {
                    listaCRPlanillaCR2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCR2.size());
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR2);
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCrPlanilla() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCRPlanilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR2 = new ArrayList<>();
            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCR = personalService.obtenerPlanillaCRCabecera(uniNeg, mes, listaTipoAccesoConsiderar.get(j), anio);

                for (ConceptoPlanillaRepBean listaCR : listaCRPlanillaCR) {
                    listaCRPlanillaCR2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCR2.size());
                }
            }

            if (!listaCRPlanillaCR2.isEmpty()) {
                for (int m = 0; m < listaCRPlanillaCR2.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = personalService.obtenerPlanillaCRDetalle(uniNeg, mes, listaCRPlanillaCR2.get(m).getIdTipoNivel(), anio);
                    listaCRPlanillaCR2.get(m).setListaPersonal(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ConceptoPlanillaRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = personalService.obtenerPlanillaCRSubDetalle(uniNeg, mes, listaDetalle.get(s).getIdTipoNivel(), anio, listaDetalle.get(s).getCr());
                            listaDetalle.get(s).setListaDetallePersonal(listaSubDetalle);
                            listaCRPlanillaCR2.get(m).setListaPersonal(listaDetalle);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR2);
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCrTrabajador() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTrabajadorCR.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            listaCRPlanillaCR = personalService.obtenerTrabajadorCRCabecera(uniNeg, mes, anio);
            if (!listaCRPlanillaCR.isEmpty()) {
                for (int m = 0; m < listaCRPlanillaCR.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = personalService.obtenerTrabajadorCRDetalle(uniNeg, mes, anio, listaCRPlanillaCR.get(m).getIdObjeto());
                    listaCRPlanillaCR.get(m).setListaPersonal(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ConceptoPlanillaRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = personalService.obtenerTrabajadorCRSubDetalle(uniNeg, mes, anio, listaDetalle.get(s).getCr(), listaDetalle.get(s).getIdObjeto());
                            listaDetalle.get(s).setListaDetallePersonal(listaSubDetalle);
                            listaCRPlanillaCR.get(m).setListaPersonal(listaDetalle);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR);
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirTodosPdfRemuneracion() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteLpmRemuneraciones.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<ConceptoPlanillaRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = personalService.obtenerPlanillaRemuneracionesCabecera(uniNeg, mes, anio);
            System.out.println("anio " + anio);
            if (!listaCabecera.isEmpty()) {
                for (int m = 0; m < listaCabecera.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaNiveles = new ArrayList<>();
                    listaNiveles = personalService.obtenerPlanillaRemuneracionesId(uniNeg, mes, listaCabecera.get(m).getIdTipoNivel(), anio);
                    listaCabecera.get(m).setListaPersonal(listaNiveles);
                    if (!listaNiveles.isEmpty()) {
                        for (int s = 0; s < listaNiveles.size(); s++) {
                            System.out.println("numero: " + listaNiveles.size());
                            List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                            listaDetalle = personalService.obtenerPlanillaRemuneracionesDetalle(uniNeg, listaNiveles.get(s).getIdTipoNivel(), listaNiveles.get(s).getIdObjeto(), mes, anio);
                            listaNiveles.get(s).setListaDetallePersonal(listaDetalle);
                            listaCabecera.get(m).setListaPersonal(listaNiveles);
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void guardarPlanillaCentros(Integer idPersonal) {
        try {
//            if (personalBean.getStatusVista() == true) {
            PersonalService personalService = BeanFactory.getPersonalService();
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalBean.getStatusVista() == true) {
                if (personalBean.getCodPer() != null && !personalBean.getCodPer().trim().equals("")) {
                    if (personalBean.getTipoNivelInsBean().getIdCodigo() != null && !personalBean.getTipoNivelInsBean().getIdCodigo().equals(0)) {
//                        if (personalBean.getStatusVista() == true) {
                        if (personalBean.getCr1Porc() != null && !personalBean.getCr1Porc().equals(0)
                                && personalBean.getCr1().getCr() == null) {
                            System.out.println("entro2");
                            new MensajePrime().addInformativeMessagePer("Falta llenar Centro Resp. 1");
                        } else if ((personalBean.getCr1().getCr() != null && personalBean.getCr1Porc() == null) || (personalBean.getCr1().getCr() != null && personalBean.getCr1Porc().equals(0))) {
                            new MensajePrime().addInformativeMessagePer("Falta llenar el porcentaje del Centro Resp. 1");
                        } else {
                            if (personalBean.getCr2Porc() != null && !personalBean.getCr2Porc().equals(0)
                                    && personalBean.getCr2().getCr() == null) {
                                new MensajePrime().addInformativeMessagePer("Falta llenar Centro Resp. 2");
                            } else if ((personalBean.getCr2().getCr() != null && personalBean.getCr2Porc() == null) || (personalBean.getCr2().getCr() != null && personalBean.getCr2Porc().equals(0))) {
                                new MensajePrime().addInformativeMessagePer("Falta llenar el porcentaje del Centro Resp. 2");
                            } else {
                                if (personalBean.getCr3Porc() != null && !personalBean.getCr3Porc().equals(0)
                                        && personalBean.getCr3().getCr() == null) {
                                    new MensajePrime().addInformativeMessagePer("Falta llenar Centro Resp. 3");
                                } else if ((personalBean.getCr3().getCr() != null && personalBean.getCr3Porc() == null) || (personalBean.getCr3().getCr() != null && personalBean.getCr3Porc().equals(0))) {
                                    new MensajePrime().addInformativeMessagePer("Falta llenar el porcentaje del Centro Resp. 3");
                                } else {
                                    if (personalBean.getCr4Porc() != null && !personalBean.getCr4Porc().equals(0)
                                            && personalBean.getCr4().getCr() == null) {
                                        new MensajePrime().addInformativeMessagePer("Falta llenar Centro Resp. 4");
                                    } else if ((personalBean.getCr4().getCr() != null && personalBean.getCr4Porc() == null) || (personalBean.getCr4().getCr() != null && personalBean.getCr4Porc().equals(0))) {
                                        new MensajePrime().addInformativeMessagePer("Falta llenar el porcentaje del Centro Resp. 4");
                                    } else {
                                        if (personalBean.getCr5Porc() != null && !personalBean.getCr5Porc().equals(0)
                                                && personalBean.getCr5().getCr() == null) {
                                            new MensajePrime().addInformativeMessagePer("Falta llenar Centro Resp. 5");
                                        } else if ((personalBean.getCr5().getCr() != null && personalBean.getCr5Porc() == null) || (personalBean.getCr5().getCr() != null && personalBean.getCr5Porc().equals(0))) {
                                            new MensajePrime().addInformativeMessagePer("Falta llenar el porcentaje del Centro Resp. 5");
                                        } else {
                                            System.out.println("personal: " + idPersonal);
                                            PersonalBean per = new PersonalBean();
                                            per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                            per.setIdPersonal(idPersonal);
//                                        per.getTipoOcupacionBean().setIdCodigo(personalBean.getTipoOcupacionBean().getIdCodigo());
                                            per.getCr1().setCr(personalBean.getCr1().getCr());
                                            per.getCr2().setCr(personalBean.getCr2().getCr());
                                            per.getCr3().setCr(personalBean.getCr3().getCr());
                                            per.getCr4().setCr(personalBean.getCr4().getCr());
                                            per.getCr5().setCr(personalBean.getCr5().getCr());
//                                        per.getUnidadOrganicaBean().setIdUniOrg(personalBean.getUnidadOrganicaBean().getIdUniOrg());
                                            per.setCr1Porc(personalBean.getCr1Porc());
                                            per.setCr2Porc(personalBean.getCr2Porc());
                                            per.setCr3Porc(personalBean.getCr3Porc());
                                            per.setCr4Porc(personalBean.getCr4Porc());
                                            per.setCr5Porc(personalBean.getCr5Porc());
                                            per.setModiPor(beanUsuarioSesion.getUsuario());
                                            per.setCodPer(personalBean.getCodPer());
                                            per.setStatus(personalBean.getStatusVista());
                                            per.getTipoNivelInsBean().setIdCodigo(personalBean.getTipoNivelInsBean().getIdCodigo());
                                            personalService.modificarPlanillaCentros(per);
                                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                                            listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
                                        }
                                    }
                                }
                            }
                        }
//                        } else {
//                            PersonalBean per = new PersonalBean();
//                            per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                            per.setIdPersonal(idPersonal);
//                            per.setModiPor(beanUsuarioSesion.getUsuario());
//                            per.setCodPer(personalBean.getCodPer());
//                            per.getTipoNivelInsBean().setIdCodigo(personalBean.getTipoNivelInsBean().getIdCodigo());
//                            personalService.modificarPlanillaCentros(per);
//                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                            listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
//                        }
                    } else {
                        System.out.println("no entro");
                        new MensajePrime().addInformativeMessagePer("Falta llenar el TIPO DEL NIVEL");
                    }
                } else {
                    System.out.println("no entro");
                    new MensajePrime().addInformativeMessagePer("Falta llenar el CODIGO DEL TRABAJADOR");
                }
            } else {
                PersonalBean per = new PersonalBean();
                per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                per.setIdPersonal(idPersonal);
                per.setModiPor(beanUsuarioSesion.getUsuario());
                per.setCodPer(personalBean.getCodPer());
                per.getTipoNivelInsBean().setIdCodigo(personalBean.getTipoNivelInsBean().getIdCodigo());
                per.setStatus(personalBean.getStatusVista());
                personalService.modificarPlanillaCentros(per);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaPersonalPorUniNegBean = legajoService.obtenerPersonalPorUnidadNegocio((beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean()).getUniNeg());
            }
//            } else {
//                System.out.println("no entro");
//                new MensajePrime().addInformativeMessagePer("Falta llenar algunos datos Requeridos.");
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void settearNullFlgHijos(String P) {
        try {
            if (P.equals("papa")) {
                getPersonalRepFiltroBean().setFlgPadresConHijosH(null);
            } else {
                getPersonalRepFiltroBean().setFlgMadresConHijosH(null);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPaisPorDefectoPeru() {

        PaisService paisService = BeanFactory.getPaisService();
        Integer cod = null;
        cod = getPersonalBean().getPaisNacimientoBean().getIdPais();
        PaisBean pais = new PaisBean();
//        pais = paisService.obtenerPaisPorDefectoPeru(nombre);
        if (cod.equals(MaristaConstantes.COD_PERU)) {
            getPersonalBean().getDistritoNacBean().getProvinciaBean().getDepartamentoBean().setIdDepartamento(MaristaConstantes.DEP_LIMA);
            getPersonalBean().getDistritoNacBean().getProvinciaBean().setIdProvincia(MaristaConstantes.PROV_LIMA);
            getPersonalBean().getDistritoNacBean().setIdDistrito(null);
            this.flgPaisPeru = true;
        } else {
            this.flgPaisPeru = false;
            getPersonalBean().getDistritoNacBean().getProvinciaBean().getDepartamentoBean().setIdDepartamento(null);
            getPersonalBean().getDistritoNacBean().getProvinciaBean().setIdProvincia(null);
            getPersonalBean().getDistritoNacBean().setIdDistrito(null);
        }
    }

    //
    public void rowSelect(SelectEvent event) {
        try {

            personalBean = (PersonalBean) event.getObject();
            LegajoService legajoService = BeanFactory.getLegajoService();
            personalBean = legajoService.obtenerLegajoId(personalBean);
            personalBean.setCollapsed(true);

            flgCertificado = false;
            flgFinanciamiento = false;
            //foto Personal
            LegajoService Legajo = BeanFactory.getLegajoService();
            String rutaFoto = "";
            setTrabajoMenor("");
            rutaFoto = Legajo.obtenerFotoPersonal(personalBean.getCodPer(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("foto: " + rutaFoto);
            personalBean.setFoto(rutaFoto);
            this.flgMedicamentos = false;
            this.flgLunes = false;
            this.flgMartes = false;
            this.flgMiercoles = false;
            this.flgJueves = false;
            this.flgViernes = false;
            this.flgSabado = false;
            this.flgHabilitarVacaciones = false;
            PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
            personalFormacionCarismaBean = new PersonalFormacionCarismaBean();
            personalFormacionCarismaBean.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            personalFormacionCarismaBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listPersonalForCarismaBean = personalFormacionService.obtenerPersonalFormacionCarismaLista(personalFormacionCarismaBean);
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalDescansoMedicoBean = new PersonalDescansoMedicoBean();
            personalDescansoMedicoBean.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            personalDescansoMedicoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalDescansoMedico = personalAlergiaService.obtenerPersonalDescansoMedico(personalDescansoMedicoBean);
            listaPersonalInasistencia = personalAlergiaService.obtenerPersonalInasistencia(personalDescansoMedicoBean);
            listaPersonalAccidenteLaboral = personalAlergiaService.obtenerPersonalAccidente(personalDescansoMedicoBean);
            personalEvaPsicologicaBean = new PersonalEvaPsicologicaBean();
            personalEvaPsicologicaBean.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            personalEvaPsicologicaBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologica(personalEvaPsicologicaBean);
            PersonalVoluntariadoBean per = new PersonalVoluntariadoBean();
            per.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            per.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            listaPersonalVoluntariadoBean = legajoService.obtenerPersonalVoluntariadoLista(per);
            PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
            historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
            cargarTrabajoRiesgoso();
//            personalDependienteBean = personalDependienteService.obtenerPersonalDependientePorId(personalBean.getIdPersonal());
            listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
            if (!listaPersonalDependienteBean.isEmpty()) {
                personalDependienteBean.setCollapsed(false);
            }
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
//            personalCargoBean = personalCargoService.obtenerPersonalCargoPorId(personalBean.getIdPersonal());
            listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);

            PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
//            personalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorId(personalBean.getIdPersonal());
            listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);

            PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
//            personalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorId(personalBean.getIdPersonal());
            listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
            cargarAnoExp();

            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
//            personalContratoBean = personalContratoService.obtenerPersonalContratoPorId(personalBean.getIdPersonal());
            listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);
//            for (PersonalContratoBean contrato : listaPersonalContratoBean) {
            for (PersonalContratoBean contrato : listaPersonalContratoBean) {
                contrato.setTiempoContrato(personalContratoService.obtenerPersonalTiempoContrato(personalBean.getIdPersonal(), contrato.getPeriodo()));
            }
            listaPersonalVacacionesBean = personalContratoService.obtenerPersonalVacacionesPorPersonal(personalBean);
            personalInformacionBancariaBean = new PersonalInformacionBancariaBean();
            personalInformacionBancariaBean = personalContratoService.obtenerPersonalBancariaPorPersonal(personalBean);
            listaPersonalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorPersonal(personalBean);
            PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
//            personalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorId(personalBean.getIdPersonal());
            listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);

//            personalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorId(personalBean.getIdPersonal());
            listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);

//            personalFormacionBasicaBean = personalFormacionService.obtenerPersonalFormacionBasicaPorId(personalBean.getIdPersonal());
            listaPersonalFormacionBasicaBean = personalFormacionService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);

//            personalFormacionSuperiorBean = personalFormacionService.obtenerPersonalFormacionSuperiorPorId(personalBean.getIdPersonal());
            listaPersonalFormacionSuperiorBean = personalFormacionService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);

            PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
//            personalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorId(personalBean.getIdPersonal());
            listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);

            PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
//            personalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorId(personalBean.getIdPersonal());
            listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);

            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
//            personalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorId(personalBean.getIdPersonal());
            listaPersonalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorPersonal(personalBean);

            listaPersonalPDFBean = personalDocumentoService.obtenerPersonalPDFPorPersonal(personalBean);
            obtenerCodigoViaZona(1);
            obtenerCodigoViaZona(2);
            obtenerCodigoViaZona(3);
            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();
            obtenerCRPorUniOrg();
            limpiarPersonalFormacionBasica();
            limpiarPersonalFormacionSuperior();
            limpiarPersonalIdioma();
            limpiarPersonalOtrosEstudios();
            limpiarPersonalDependiente();
            limpiarPersonalAlergia();
            limpiarPersonalEnfermedad();
            limpiarPersonalContrato();
            limpiarPersonalVacaciones();
            limpiarPersonalCargo();
            limpiarPersonalExperiencia();
            limpiarPersonalProcesoJudicial();
            limpiarPersonalDocumento();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectPlanillaCentro(SelectEvent event) {
        try {
            PersonalService personalService = BeanFactory.getPersonalService();
            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            personalBean = (PersonalBean) event.getObject();
            String uniNeg = beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer idPersonal = personalBean.getIdPersonal();
            personalBean = personalService.obtenerCentrosPlanilla(idPersonal, uniNeg);
            obtenerCRPorUniOrg();
            listaUniNegUniOrgBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            sumaPorcentaje();
            if (personalBean.getStatusVista() == true) {
                this.flgGrabarStatus = false;
                if (sumaPorcentaje == 100) {
                    this.flgGrabar = true;
                } else {
                    this.flgGrabar = false;
                }
            } else {
                this.flgGrabarStatus = true;
                if (sumaPorcentaje == 100) {
                    this.flgGrabar = true;
                } else {
                    this.flgGrabar = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectPorPersonal(PersonalBean personal) {
        try {

            personalBean = personal;
            LegajoService legajoService = BeanFactory.getLegajoService();
            personalBean = legajoService.obtenerLegajoId(personalBean);
            getPersonalBean().setCollapsed(true);
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
//            personalDependienteBean = personalDependienteService.obtenerPersonalDependientePorId(personalBean.getIdPersonal());
            listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
            if (!listaPersonalDependienteBean.isEmpty()) {
                getPersonalDependienteBean().setCollapsed(false);
            }
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
//            personalCargoBean = personalCargoService.obtenerPersonalCargoPorId(personalBean.getIdPersonal());
            listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);

            PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
//            personalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorId(personalBean.getIdPersonal());
            listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);

            PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
//            personalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorId(personalBean.getIdPersonal());
            listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
            cargarAnoExp();

            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
//            personalContratoBean = personalContratoService.obtenerPersonalContratoPorId(personalBean.getIdPersonal());
            listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);

            listaPersonalVacacionesBean = personalContratoService.obtenerPersonalVacacionesPorPersonal(personalBean);

            PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
//            personalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorId(personalBean.getIdPersonal());
            listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);

            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
//            personalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorId(personalBean.getIdPersonal());
            listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);

            PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
//            personalFormacionBasicaBean = personalFormacionService.obtenerPersonalFormacionBasicaPorId(personalBean.getIdPersonal());
            listaPersonalFormacionBasicaBean = personalFormacionService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);

//            personalFormacionSuperiorBean = personalFormacionService.obtenerPersonalFormacionSuperiorPorId(personalBean.getIdPersonal());
            listaPersonalFormacionSuperiorBean = personalFormacionService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);

            PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
//            personalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorId(personalBean.getIdPersonal());
            listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);

            PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
//            personalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorId(personalBean.getIdPersonal());
            listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);

            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
//            personalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorId(personalBean.getIdPersonal());
            listaPersonalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorPersonal(personalBean);

            obtenerProvinciaDomi();
            obtenerDistritoDomi();
            obtenerProvinciaNaci();
            obtenerDistritoNaci();
            obtenerCRPorUniOrg();
            limpiarPersonalFormacionBasica();
            limpiarPersonalFormacionSuperior();
            limpiarPersonalIdioma();
            limpiarPersonalOtrosEstudios();
            limpiarPersonalDependiente();
            limpiarPersonalAlergia();
            limpiarPersonalEnfermedad();
            limpiarPersonalContrato();
            limpiarPersonalVacaciones();
            limpiarPersonalCargo();
            limpiarPersonalExperiencia();
            limpiarPersonalProcesoJudicial();
            limpiarPersonalDocumento();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------- Inicio PersonalDependiente-----------------------
    public void limpiarPersonalDependiente() {
        personalDependienteBean = new PersonalDependienteBean();
        personalOtrosDependienteBean = new PersonalDependienteBean();
    }

    public void mostrarPanelPersonalDep() {
        personalDependienteBean.setCollapsed(false);
    }

    public void limpiarPersonalDepYmostrarPanelPersonalDep() {
        personalDependienteBean = new PersonalDependienteBean();
        personalDependienteBean.setCollapsed(false);
        menorEdad = "";
    }

    public void limpiarPersonalDepYmostrarPanelPersonalOtrosDep() {
        personalOtrosDependienteBean = new PersonalDependienteBean();
        personalOtrosDependienteBean.setCollapsed(false);
        menorEdad = "";
    }

    public void obtenerPersonalDependientePorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
            listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
            limpiarPersonalDependiente();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalDependientePorId(Object object) {
        try {
            personalDependienteBean = (PersonalDependienteBean) object;
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
            personalDependienteBean = personalDependienteService.obtenerPersonalDependientePorId(getPersonalDependienteBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalDependiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (!personalDependienteBean.getNombre().equals("") && personalDependienteBean.getNombre() != null
                        && !personalDependienteBean.getApemat().equals("") && personalDependienteBean.getApemat() != null
                        && !personalDependienteBean.getApepat().equals("") && personalDependienteBean.getApepat() != null
                        && !personalDependienteBean.getTipoEstadoCivilBean().getIdCodigo().equals(0) && personalDependienteBean.getTipoEstadoCivilBean().getIdCodigo() != null
                        && !personalDependienteBean.getTipoDocPerBean().getIdCodigo().equals(0) && personalDependienteBean.getTipoDocPerBean().getIdCodigo() != null
                        && !personalDependienteBean.getNroDoc().equals("") && personalDependienteBean.getNroDoc() != null
                        && !personalDependienteBean.getTipoParentescoBean().getIdCodigo().equals(0) && personalDependienteBean.getTipoParentescoBean().getIdCodigo() != null) {
                    PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                    personalDependienteBean.setPersonalBean(personalBean);
                    personalDependienteBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    personalDependienteBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    personalDependienteService.insertarPersonalDependiente(personalDependienteBean);
                    listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
                    limpiarPersonalDependiente();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("Falta llenar alg鷑 dato de los campos requeridos");
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarPersonalOtrosDependiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (!personalOtrosDependienteBean.getNombre().equals("") && personalOtrosDependienteBean.getNombre() != null
                        && !personalOtrosDependienteBean.getApemat().equals("") && personalOtrosDependienteBean.getApemat() != null
                        && !personalOtrosDependienteBean.getApepat().equals("") && personalOtrosDependienteBean.getApepat() != null
                        && !personalOtrosDependienteBean.getTipoEstadoCivilBean().getIdCodigo().equals(0) && personalOtrosDependienteBean.getTipoEstadoCivilBean().getIdCodigo() != null
                        && !personalOtrosDependienteBean.getTipoDocPerBean().getIdCodigo().equals(0) && personalOtrosDependienteBean.getTipoDocPerBean().getIdCodigo() != null
                        && !personalOtrosDependienteBean.getNroDoc().equals("") && personalOtrosDependienteBean.getNroDoc() != null
                        && !personalOtrosDependienteBean.getTipoParentescoBean().getIdCodigo().equals(0) && personalOtrosDependienteBean.getTipoParentescoBean().getIdCodigo() != null) {
                    PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                    personalOtrosDependienteBean.setPersonalBean(personalBean);
                    personalOtrosDependienteBean.setCreaPor(beanUsuarioSesion.getUsuario());
                    personalOtrosDependienteBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    personalDependienteService.insertarPersonalDependiente(personalOtrosDependienteBean);
                    listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
                    limpiarPersonalDependiente();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessagePer("Falta llenar alg鷑 dato de los campos requeridos");
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalDependiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                personalDependienteBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalDependienteService.modificarPersonalDependiente(personalDependienteBean);
                listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
                limpiarPersonalDependiente();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalOtrosDependiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                personalOtrosDependienteBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalDependienteService.modificarPersonalDependiente(personalOtrosDependienteBean);
                listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
                limpiarPersonalDependiente();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalDependiente() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                personalDependienteService.eliminarPersonalDependiente(personalDependienteBean);
                listaPersonalDependienteBean = personalDependienteService.obtenerPersonalDependientePorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalDependiente();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalDependiente() {

        try {
            if (personalDependienteBean.getIdPersonalDependiente() == null) {
                insertarPersonalDependiente();
            } else {
                modificarPersonalDependiente();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void guardarPersonalOtrosDependiente() {

        try {
            if (personalOtrosDependienteBean.getIdPersonalDependiente() == null) {
                insertarPersonalOtrosDependiente();
            } else {
                modificarPersonalOtrosDependiente();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalDependiente(SelectEvent event) {
        try {
            PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
            personalDependienteBean = (PersonalDependienteBean) event.getObject();
            if (personalDependienteBean.getTipoParentescoBean().getCodigo().equals("Hijo(a)")) {
                personalOtrosDependienteBean = new PersonalDependienteBean();
                personalDependienteBean = personalDependienteService.obtenerPersonalDependientePorId(personalDependienteBean);
                menorEdad = personalDependienteService.obtenerDependienteFechaNacimiento(personalDependienteBean.getFecNac());
                if (Integer.parseInt(menorEdad) < 12) {
                    menorEdad = "ES MENOR DE EDAD, TIENE " + menorEdad;
                } else {
                    menorEdad = "";
                }
            } else {
                personalOtrosDependienteBean = personalDependienteService.obtenerPersonalDependientePorId(personalDependienteBean);
                personalDependienteBean = new PersonalDependienteBean();
            }

            mostrarPanelPersonalDep();
            obtenerGradoAcaDependiente();
            obtenerNivelAcaDependiente();
            obtenerCarreraPorNivelAcademicoSuperior();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarHistorico() {

        try {
            LegajoService legajoService = BeanFactory.getLegajoService();
            PersonalDatosHistoricoBean historico = new PersonalDatosHistoricoBean();
            historico.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            historico.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            listaPersonalDatosHistoricoBean = legajoService.obtenerPersonalHistoricoLista(historico);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }
    //--------------------- Fin PersonalDependiente-----------------------

    //--------------------- Inicio PersonalCargo--------------------------
    public void limpiarPersonalCargo() {
        personalCargoBean = new PersonalCargoBean();
        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        personalCargoBean.setCreaPor(beanUsuarioSesion.getUsuario());
        personalCargoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
    }

    public void limpiarPersonalCargoYmostrarPanelEnf() {
        personalCargoBean = new PersonalCargoBean();
        personalCargoBean.setCollapsed(false);
    }

    public void obtenerPersonalCargoPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
            listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);
            limpiarPersonalCargo();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalCargoPorId(Object object) {
        try {
            personalCargoBean = (PersonalCargoBean) object;
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
            personalCargoBean = personalCargoService.obtenerPersonalCargoPorId(getPersonalCargoBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {

                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personalCargoBean.setPersonalBean(personalBean);
                personalCargoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalCargoBean.setCreaPor(beanUsuarioSesion.getUsuario());

                PersonalDocumentoBean personalDocumento = new PersonalDocumentoBean();
//                personalDocumento.setCreaPor(beanUsuarioSesion.getUsuario());
                personalCargoService.insertarPersonalCargo(personalCargoBean, listaDocumentoCargoBean, personalDocumento, beanUsuarioSesion);
                listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);
                listaPersonalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorPersonal(personalBean);
                limpiarPersonalCargo();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }

        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                personalCargoBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalCargoService.modificarPersonalCargo(personalCargoBean);
                listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalCargo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                personalCargoService.eliminarPersonalCargo(personalCargoBean);
                listaPersonalCargoBean = personalCargoService.obtenerPersonalCargoPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalCargo();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalCargo() {

        try {
            PersonalService personalService = BeanFactory.getPersonalService();
            if (personalCargoBean.getIdPersonalCargo() == null) {
                insertarPersonalCargo();
                personalService.modificarSueldoBasicoEstatal(personalBean);
            } else {
                modificarPersonalCargo();
                personalService.modificarSueldoBasicoEstatal(personalBean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectPersonalCargo(SelectEvent event) {
        try {
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
            personalCargoBean = (PersonalCargoBean) event.getObject();
            personalCargoBean = personalCargoService.obtenerPersonalCargoPorId(personalCargoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerAsigCargo(Object object) {
        CargoUniNegBean carUniNeg = (CargoUniNegBean) object;
        personalCargoBean.setAsigCargo(carUniNeg.getAsigCargo());
    }

    public void ponerCatCargo(Object object) {
        CargoBean cargo = (CargoBean) object;
    }

    //------------CARGOS----------------//
    public void cargarUniOrgYCargoPorUniNeg() {
        try {
            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            CargoUniNegService cargoUniNegService = BeanFactory.getCargoUniNegService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (beanUsuarioSesion != null) {
                if (listaUniNegUniOrgBean == null) {
                    listaUniNegUniOrgBean = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                }
                listaUniNegUniOrgBeanPersonal = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaCargoUniNegBean = cargoUniNegService.obtenerCargoUniNegPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAsigCargo() {
        try {
//            for (int i = 0; i < listaCargoUniNegBean.size(); i++) {
//                if (personalCargoBean.getCargoBean().getIdCargo() != null
//                        && listaCargoUniNegBean.get(i).getCargoBean().getIdCargo().toString().equals(personalCargoBean.getCargoBean().getIdCargo().toString())) {
//                    personalCargoBean.setAsigCargo(listaCargoUniNegBean.get(i).getAsigCargo());
//                    break;
//                }
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCargosTutorDocente() {
        try {
            if (personalCargoBean.getCargoBean().getIdCargo() == 45) {
                this.flgTutor = true;
                this.flgDocente = false;
            }
            if (personalCargoBean.getCargoBean().getIdCargo() == 21) {
                this.flgTutor = false;
                this.flgDocente = true;
            } else {
                this.flgDocente = false;
                this.flgTutor = false;
            }
            System.out.println("cargo: " + personalCargoBean.getCargoBean().getIdCargo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoCategCargo() {
        try {
            for (int i = 0; i < listaCargoUniNegBean.size(); i++) {
                if (personalCargoBean.getCargoBean().getIdCargo() != null
                        && listaCargoUniNegBean.get(i).getCargoBean().getIdCargo().toString().equals(personalCargoBean.getCargoBean().getIdCargo().toString())) {
                    personalCargoBean.getCargoBean().getTipoCategoriaCargoBean().setCodigo(listaCargoUniNegBean.get(i).getCargoBean().getTipoCategoriaCargoBean().getCodigo());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectPersonalCargos(SelectEvent event) {
        try {
            PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
            personalCargoBean = (PersonalCargoBean) event.getObject();
            personalCargoBean = personalCargoService.obtenerPersonalCargoPorId(personalCargoBean);
            CargoService cargoService = BeanFactory.getCargoService();
            String codigo = cargoService.obtenerGrupoOcupacional(personalCargoBean.getCargoBean().getIdCargo());
            System.out.println("grupoO: " + codigo);
            personalCargoBean.getCargoBean().getTipoGrupoOcupacionalBean().setCodigo(codigo);
            obtenerCargosTutorDocente();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    //--------------------- Fin PersonalCargo-----------------------
    //--------------------- Inicio PersonalEnfermedad-----------------------
    public void limpiarPersonalEnfermedad() {
        personalEnfermedadBean = new PersonalEnfermedadBean();
    }

    public void mostrarPanelEnf() {
        personalEnfermedadBean.setCollapsed(false);
    }

    public void limpiarPersonalEnfermedadYmostrarPanelEnf() {
        personalEnfermedadBean = new PersonalEnfermedadBean();
        personalEnfermedadBean.setCollapsed(false);
    }

    public void obtenerPersonalEnfermedadPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
            listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);
            limpiarPersonalEnfermedad();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalEnfermedadPorId(Object object) {
        try {
            personalEnfermedadBean = (PersonalEnfermedadBean) object;
            PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
            personalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorId(getPersonalEnfermedadBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
                personalEnfermedadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalEnfermedadBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

                personalEnfermedadBean.setPersonalBean(personalBean);
                personalEnfermedadService.insertarPersonalEnfermedad(personalEnfermedadBean);
                listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);
                limpiarPersonalEnfermedad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
                personalEnfermedadBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalEnfermedadService.modificarPersonalEnfermedad(personalEnfermedadBean);
                listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalEnfermedad();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalEnfermedad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
                personalEnfermedadService.eliminarPersonalEnfermedad(personalEnfermedadBean);
                listaPersonalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalEnfermedad();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalEnfermedad() {
        try {
            if (personalEnfermedadBean.getIdPersonalEnfermedad() == null) {
                insertarPersonalEnfermedad();
            } else {
                modificarPersonalEnfermedad();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
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

    public void obtenerEnfermedadPorTipo() {
        try {
            EnfermedadService enfermedadService = BeanFactory.getEnfermedadService();
            listaEnfermedadBean = enfermedadService.obtenerEnfermedadPorTipo(personalEnfermedadBean.getEnfermedadBean().getTipoEnfermedadBean().getIdCodigo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelectPersonalEnfermedad(SelectEvent event) {
        try {
            PersonalEnfermedadService personalEnfermedadService = BeanFactory.getPersonalEnfermedadService();
            personalEnfermedadBean = (PersonalEnfermedadBean) event.getObject();
            personalEnfermedadBean = personalEnfermedadService.obtenerPersonalEnfermedadPorId(personalEnfermedadBean);
            mostrarPanelEnf();
            obtenerEnfermedadPorTipo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //--------------------- Fin PersonalEnfermedad---------------------------

    //--------------------- Inicio PersonalExperiencia-----------------------
    public void limpiarPersonalExperiencia() {
        personalExperienciaBean = new PersonalExperienciaBean();
        cargarAnoExp();
    }

    public void obtenerPersonalExperienciaPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
            listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
            limpiarPersonalExperiencia();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalExperienciaPorId(Object object) {
        try {

            personalExperienciaBean = (PersonalExperienciaBean) object;
            PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
            personalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorId(getPersonalExperienciaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalExperiencia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
                personalExperienciaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalExperienciaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

                personalExperienciaBean.setPersonalBean(personalBean);
                personalExperienciaService.insertarPersonalExperiencia(personalExperienciaBean);
                listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
                limpiarPersonalExperiencia();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalExperiencia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
                personalExperienciaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalExperienciaService.modificarPersonalExperiencia(personalExperienciaBean);
                listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalExperiencia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
                personalExperienciaService.eliminarPersonalExperiencia(personalExperienciaBean);
                listaPersonalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalExperiencia();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalExperiencia() {
        try {
            if (personalExperienciaBean.getIdPersonalExperiencia() == null) {
                insertarPersonalExperiencia();
            } else {
                modificarPersonalExperiencia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalExperiencia(SelectEvent event) {
        try {
            PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
            personalExperienciaBean = (PersonalExperienciaBean) event.getObject();
            personalExperienciaBean = personalExperienciaService.obtenerPersonalExperienciaPorId(personalExperienciaBean);
            cargarAnoExp();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAnoExp() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int b = MaristaConstantes.ANO_INI_DEFAULT;
            int a = miCalendario.get(Calendar.YEAR);
            listaAnosIniExp = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosIniExp.add(i);
            }
            listaAnosFinExp = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosFinExp.add(i);
            }
        } catch (Exception e) {
        }
    }

    public void actualizarListaExperiencia(AjaxBehaviorEvent event) {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int a = personalExperienciaBean.getAnioIni();
            int b = miCalendario.get(Calendar.YEAR);
            listaAnosFinExp = new ArrayList<>();
            for (int i = a; i <= b; i++) {
                listaAnosFinExp.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public final void obtenerAniosTipoOtrosEstudios() {
        try {
            if (personalOtrosEstudiosBean.getTipoOtrosEstudiosBean().getIdCodigo().equals(31603)
                    || personalOtrosEstudiosBean.getTipoOtrosEstudiosBean().getIdCodigo().equals(31604)
                    || personalOtrosEstudiosBean.getTipoOtrosEstudiosBean().getIdCodigo().equals(31605)) {
                Date fecha = new Date();
                Calendar miCalendario = Calendar.getInstance();
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                int b = miCalendario.get(Calendar.YEAR) - 10;
                int a = Integer.parseInt(formato.format(fecha));
                listaAnosIniOtrosEstudios = new ArrayList<>();
                for (int i = b; i <= a; i++) {
                    listaAnosIniOtrosEstudios.add(i);
                }
                listaAnosFinOtrosEstudios = new ArrayList<>();
                for (int i = b; i <= a; i++) {
                    listaAnosFinOtrosEstudios.add(i);
                }
            } else {
                cargarAnoOtrosEstudios();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------- Fin PersonalExperiencia-----------------------
    //--------------------- Inicio PersonalContrato-----------------------
    public void limpiarPersonalContrato() {
        personalContratoBean = new PersonalContratoBean();
    }

    public void limpiarPersonalVacaciones() {
        personalVacacionesBean = new PersonalContratoBean();
    }

    public void mostrarPanelCon() {
        personalContratoBean = new PersonalContratoBean();
    }

    public void limpiarPersonalContratoYmostrarPanelCon() {
        personalContratoBean = new PersonalContratoBean();
        personalContratoBean.setCollapsed(false);
    }

    public void limpiarPersonalVacacionesYmostrarPanelCon() {
        personalVacacionesBean = new PersonalContratoBean();
        personalVacacionesBean.setCollapsed(false);
    }

    public void obtenerPersonalContratoPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);
            limpiarPersonalContrato();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalContratoPorId(Object object) {
        try {
            personalContratoBean = (PersonalContratoBean) object;
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalContratoBean = personalContratoService.obtenerPersonalContratoPorId(getPersonalContratoBean());
            cargarDiasContrato(1);
            cargarDiasContrato(2);
            cargarDiasContrato(3);
            cargarDiasContrato(4);
            cargarDiasContrato(5);
            cargarDiasContrato(6);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalVacacionesPorId(Object object) {
        try {
            personalVacacionesBean = (PersonalContratoBean) object;
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalVacacionesBean = personalContratoService.obtenerPersonalContratoPorId(getPersonalVacacionesBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalContrato() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalContratoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalContratoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalContratoBean.setPeriodo(periodo);
                personalContratoBean.setPersonalBean(personalBean);
                personalContratoService.insertarPersonalContrato(personalContratoBean);
                listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);
                for (PersonalContratoBean contrato : listaPersonalContratoBean) {
                    contrato.setTiempoContrato(personalContratoService.obtenerPersonalTiempoContrato(personalBean.getIdPersonal(), contrato.getPeriodo()));
                }
                limpiarPersonalContrato();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalContrato() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalContratoBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalContratoService.modificarPersonalContrato(personalContratoBean);
                listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);
                limpiarPersonalContrato();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarPersonalVacaciones() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalVacacionesBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalVacacionesBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

                personalVacacionesBean.setPersonalBean(personalBean);
                personalContratoService.insertarPersonalContrato(personalVacacionesBean);
                listaPersonalVacacionesBean = personalContratoService.obtenerPersonalVacacionesPorPersonal(personalBean);
                limpiarPersonalContrato();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalVacaciones() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalVacacionesBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalContratoService.modificarPersonalContrato(personalContratoBean);
                listaPersonalVacacionesBean = personalContratoService.obtenerPersonalVacacionesPorPersonal(personalBean);
                limpiarPersonalContrato();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalContrato() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalContratoService.eliminarPersonalContrato(personalContratoBean);
                listaPersonalContratoBean = personalContratoService.obtenerPersonalContratoPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalContrato();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalVacaciones() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalContratoService.eliminarPersonalContrato(personalVacacionesBean);
                listaPersonalVacacionesBean = personalContratoService.obtenerPersonalVacacionesPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalContrato();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalContrato() {

        try {
            if (personalContratoBean.getIdPersonalContrato() == null) {
                insertarPersonalContrato();
            } else {
                modificarPersonalContrato();
            }
            this.flgHabilitarVacaciones = false;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void guardarPersonalVacaciones() {

        try {
            if (personalVacacionesBean.getIdPersonalContrato() == null) {
                insertarPersonalVacaciones();
            } else {
                modificarPersonalVacaciones();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalContrato(SelectEvent event) {
        try {
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalContratoBean = (PersonalContratoBean) event.getObject();
            personalContratoBean = personalContratoService.obtenerPersonalContratoPorId(personalContratoBean);
            cargarDiasContrato(1);
            cargarDiasContrato(2);
            cargarDiasContrato(3);
            cargarDiasContrato(4);
            cargarDiasContrato(5);
            cargarDiasContrato(6);
            calcularHoras();
            calcularTotalHoras();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void calcularTotalHoras() {
        try {
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            totalHorasGlobal = personalContratoService.obtenerPersonalTotalHorasTrabajadas(personalBean.getIdPersonal(), personalContratoBean.getIdPersonalContrato());
            String horaTope = "20:00";
            String horaGlobal = totalHorasGlobal;

            horaGlobal = horaGlobal.replace(":", "");
            horaTope = horaTope.replace(":", "");

            System.out.println("1: " + horaGlobal);
            System.out.println("2: " + horaTope);
            if (Integer.valueOf(horaGlobal) > Integer.valueOf(horaTope)) {
                this.flgHabilitarVacaciones = true;
            } else {
                this.flgHabilitarVacaciones = false;
                setTrabajoMenor("No trabaja mas de 20 horas a la semana!!!");
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //--------------------- Fin PersonalContrato-----------------------
//--------------------- Inicio PersonalProcesoJudicial-----------------------

    public void limpiarPersonalProcesoJudicial() {
        personalProcesoJudicialBean = new PersonalProcesoJudicialBean();
    }

    public void obtenerPersonalProcesoJudicialPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
            listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);
            limpiarPersonalProcesoJudicial();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalProcesoJudicialPorId(Object object) {
        try {
            personalProcesoJudicialBean = (PersonalProcesoJudicialBean) object;
            PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
            personalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorId(getPersonalProcesoJudicialBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalProcesoJudicial() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
                personalProcesoJudicialBean.setCreaPor(beanUsuarioSesion.getUsuario());

                personalProcesoJudicialBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalProcesoJudicialBean.setPersonalBean(personalBean);
                personalProcesoJudicialService.insertarPersonalProcesoJudicial(personalProcesoJudicialBean);
                listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);
                limpiarPersonalProcesoJudicial();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalProcesoJudicial() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
                personalProcesoJudicialBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalProcesoJudicialService.modificarPersonalProcesoJudicial(personalProcesoJudicialBean);
                listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalProcesoJudicial() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
                personalProcesoJudicialService.eliminarPersonalProcesoJudicial(personalProcesoJudicialBean);
                listaPersonalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalProcesoJudicial();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalProcesoJudicial() {

        try {
            if (personalProcesoJudicialBean.getIdPersonalProcesoJudicial() == null) {
                insertarPersonalProcesoJudicial();
            } else {
                modificarPersonalProcesoJudicial();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalProcesoJudicial(SelectEvent event) {
        try {
            PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
            personalProcesoJudicialBean = (PersonalProcesoJudicialBean) event.getObject();
            personalProcesoJudicialBean = personalProcesoJudicialService.obtenerPersonalProcesoJudicialPorId(personalProcesoJudicialBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------- Fin PersonalProcesoJudicial-----------------------
    //--------------------- Inicio PersonalDesvinculacion-----------------------
    public void limpiarPersonalDesvinculacion() {
        personalDesvinculacionBean = new PersonalDesvinculacionBean();
    }

    public void obtenerPersonalDesvinculacionPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            listaPersonalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorPersonal(personalBean);
            limpiarPersonalProcesoJudicial();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalDesvinculacionPorId(Object object) {
        try {
            personalDesvinculacionBean = (PersonalDesvinculacionBean) object;
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorId(personalDesvinculacionBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalDesvinculacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalDesvinculacionBean.setCreaPor(beanUsuarioSesion.getUsuario());

                personalDesvinculacionBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalDesvinculacionBean.setPersonalBean(personalBean);
                personalContratoService.insertarPersonalDesvinculacion(personalDesvinculacionBean);
                listaPersonalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorPersonal(personalBean);
                limpiarPersonalDesvinculacion();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalDesvinculacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalDesvinculacionBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalContratoService.modificarPersonalDesvinculacion(personalDesvinculacionBean);
                listaPersonalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorPersonal(personalBean);
                limpiarPersonalDesvinculacion();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalDesvinculacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalContratoService.eliminarPersonalDesvinculacion(personalDesvinculacionBean);
                listaPersonalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalProcesoJudicial();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalDesvinculacion() {

        try {
            if (personalDesvinculacionBean.getIdPersonalDesvinculacion() == null) {
                insertarPersonalDesvinculacion();
            } else {
                modificarPersonalDesvinculacion();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalDesvinculacion(SelectEvent event) {
        try {
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalDesvinculacionBean = (PersonalDesvinculacionBean) event.getObject();
            personalDesvinculacionBean = personalContratoService.obtenerPersonalDesvinculacionPorId(personalDesvinculacionBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------- Fin PersonalDesvinculacion-----------------------
    //--------------------- Inicio PersonalAlergia-----------------------
    public void limpiarPersonalAlergia() {
        personalAlergiaBean = new PersonalAlergiaBean();
    }

    public void mostrarPanelAle() {
        personalAlergiaBean.setCollapsed(false);
    }

    public void mostrarPanelVaca() {
        personalVacacionesBean.setCollapsed(false);
    }

    public void limpiarPersonalEnfermedadYmostrarPanelAle() {
        personalAlergiaBean = new PersonalAlergiaBean();
        personalAlergiaBean.setCollapsed(false);
    }

    public void limpiarPersonalVacacionesYmostrarPanelVac() {
        personalVacacionesBean = new PersonalContratoBean();
        personalVacacionesBean.setCollapsed(false);
    }

    public void obtenerPersonalAlergiaPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);
            limpiarPersonalAlergia();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalAlergiaPorId(Object object) {
        try {

            personalAlergiaBean = (PersonalAlergiaBean) object;
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorId(getPersonalAlergiaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalDescansoMedico(Object object) {
        try {

            personalDescansoMedicoBean = (PersonalDescansoMedicoBean) object;
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalDescansoMedicoBean = personalAlergiaService.obtenerPersonalDescansoMedicoPorId(personalDescansoMedicoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalEvaPsicologica(Object object) {
        try {

            personalEvaPsicologicaBean = (PersonalEvaPsicologicaBean) object;
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologicaPorId(personalEvaPsicologicaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalAlergiaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalAlergiaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalAlergiaBean.setPersonalBean(personalBean);
                personalAlergiaService.insertarPersonalAlergia(personalAlergiaBean);
                listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);
                limpiarPersonalAlergia();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalAlergiaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalAlergiaService.modificarPersonalAlergia(personalAlergiaBean);
                listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);
                limpiarPersonalAlergia();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalAlergia() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalAlergiaService.eliminarPersonalAlergia(personalAlergiaBean);
                listaPersonalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalAlergia();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalAlergia() {
        try {
            if (personalAlergiaBean.getIdPersonalAlergia() == null) {
                insertarPersonalAlergia();
            } else {
                modificarPersonalAlergia();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalAlergia(SelectEvent event) {
        try {
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalAlergiaBean = (PersonalAlergiaBean) event.getObject();
            personalAlergiaBean = personalAlergiaService.obtenerPersonalAlergiaPorId(personalAlergiaBean);
            mostrarPanelAle();
            cargarMedicamentos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void rowSelectPersonalDescansoMedico(SelectEvent event) {
        try {
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalDescansoMedicoBean = (PersonalDescansoMedicoBean) event.getObject();
            personalDescansoMedicoBean = personalAlergiaService.obtenerPersonalDescansoMedicoPorId(personalDescansoMedicoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void limpiarPersonalDescansoMedico() {
        personalDescansoMedicoBean = new PersonalDescansoMedicoBean();
    }

    public void guardarPersonalDescansoMedico(Integer numero) {
        try {
            if (personalDescansoMedicoBean.getIdPersonalDescansoMedico() == null) {
                insertarPersonalDescansoMedico(numero);
            } else {
                modificarPersonalDescansoMedico(numero);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String insertarPersonalDescansoMedico(Integer numero) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalDescansoMedicoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalDescansoMedicoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalDescansoMedicoBean.setPersonalBean(personalBean);
                if (numero == 1) {
                    personalDescansoMedicoBean.getTipoDescansoBean().setIdCodigo(MaristaConstantes.COD_Descanso_Medico);
                    personalAlergiaService.insertarPersonalDescansoMedico(personalDescansoMedicoBean);
                    listaPersonalDescansoMedico = personalAlergiaService.obtenerPersonalDescansoMedico(personalDescansoMedicoBean);
                } else if (numero == 2) {
                    personalDescansoMedicoBean.getTipoDescansoBean().setIdCodigo(MaristaConstantes.COD_Inasistencia_Medico);
                    personalAlergiaService.insertarPersonalDescansoMedico(personalDescansoMedicoBean);
                    listaPersonalInasistencia = personalAlergiaService.obtenerPersonalInasistencia(personalDescansoMedicoBean);
                } else if (numero == 3) {
                    personalDescansoMedicoBean.getTipoDescansoBean().setIdCodigo(MaristaConstantes.COD_Accidente_Laboral);
                    personalAlergiaService.insertarPersonalDescansoMedico(personalDescansoMedicoBean);
                    listaPersonalAccidenteLaboral = personalAlergiaService.obtenerPersonalAccidente(personalDescansoMedicoBean);
                }

                limpiarPersonalDescansoMedico();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalDescansoMedico(Integer numero) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalDescansoMedicoBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalAlergiaService.modificarPersonalDescansoMedico(personalDescansoMedicoBean);
                if (numero == 1) {
                    listaPersonalDescansoMedico = personalAlergiaService.obtenerPersonalDescansoMedico(personalDescansoMedicoBean);
                } else if (numero == 2) {
                    listaPersonalInasistencia = personalAlergiaService.obtenerPersonalInasistencia(personalDescansoMedicoBean);
                } else if (numero == 3) {
                    listaPersonalAccidenteLaboral = personalAlergiaService.obtenerPersonalAccidente(personalDescansoMedicoBean);
                }
                limpiarPersonalDescansoMedico();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalFactorSanguineo() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                LegajoService legajoService = BeanFactory.getLegajoService();
                personalBean.setModiPor(beanUsuarioSesion.getUsuario());
                legajoService.modificarPersonalSangre(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalDescansoMedico(Integer numero) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalAlergiaService.eliminarPersonalDescansoMedico(personalDescansoMedicoBean);
                if (numero == 1) {
                    listaPersonalDescansoMedico = personalAlergiaService.obtenerPersonalDescansoMedico(personalDescansoMedicoBean);
                } else if (numero == 2) {
                    listaPersonalInasistencia = personalAlergiaService.obtenerPersonalInasistencia(personalDescansoMedicoBean);
                } else if (numero == 3) {
                    listaPersonalAccidenteLaboral = personalAlergiaService.obtenerPersonalAccidente(personalDescansoMedicoBean);
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalDescansoMedico();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelectPersonalEvaPsicologica(SelectEvent event) {
        try {
            PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
            personalEvaPsicologicaBean = (PersonalEvaPsicologicaBean) event.getObject();
            personalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologicaPorId(personalEvaPsicologicaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void limpiarPersonalEvaPsicologica() {
        personalEvaPsicologicaBean = new PersonalEvaPsicologicaBean();
    }

    public void guardarPersonalEvaPsicologica() {
        try {
            if (personalEvaPsicologicaBean.getIdPersonalEvaPsicologica() == null) {
                insertarPersonalEvaPsicologica();
            } else {
                modificarPersonalEvaPsicologica();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String insertarPersonalEvaPsicologica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalEvaPsicologicaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalEvaPsicologicaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalEvaPsicologicaBean.setPersonalBean(personalBean);
                personalAlergiaService.insertarPersonalEvaPsicologica(personalEvaPsicologicaBean);
                listaPersonalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologica(personalEvaPsicologicaBean);
                limpiarPersonalEvaPsicologica();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalEvaPsicologica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalEvaPsicologicaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalAlergiaService.modificarPersonalEvaPsicologica(personalEvaPsicologicaBean);
                listaPersonalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologica(personalEvaPsicologicaBean);
                limpiarPersonalEvaPsicologica();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalEvaPsicologica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalAlergiaService personalAlergiaService = BeanFactory.getPersonalAlergiaService();
                personalAlergiaService.eliminarPersonalEvaPsicologica(personalEvaPsicologicaBean);
                listaPersonalEvaPsicologicaBean = personalAlergiaService.obtenerPersonalEvaPsicologica(personalEvaPsicologicaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalEvaPsicologica();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void cargarMedicamentos() {
        try {
            if (personalAlergiaBean.getFlgMedicamentos() == true) {
                this.flgMedicamentos = true;
            } else {
                this.flgMedicamentos = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarDias(Date fecIni, Date fecFin) {
        try {
            long startTime = fecIni.getTime();
            long endTime = fecFin.getTime();
            long diffTime = endTime - startTime;
            long diffDays = diffTime / (1000 * 60 * 60 * 24);
            System.out.println("dias " + diffDays);
            diasVista = diffDays;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarDiasContrato(Integer n) {
        try {
            if (n == 1) {
                if (personalContratoBean.getDiaLunes() == true) {
                    this.flgLunes = true;
                } else {
                    this.flgLunes = false;
                    personalContratoBean.setHoraIniLunes("");
                    personalContratoBean.setHoraFinLunes("");
                    personalContratoBean.setHoraRefrigerioLunes("");
                    setHorasCalculadasLunes("");
                }
            }
            if (n == 2) {
                if (personalContratoBean.getDiaMartes() == true) {
                    this.flgMartes = true;
                } else {
                    this.flgMartes = false;
                    personalContratoBean.setHoraIniMartes("");
                    personalContratoBean.setHoraFinMartes("");
                    personalContratoBean.setHoraRefrigerioMartes("");
                    setHorasCalculadasMartes("");
                }
            }
            if (n == 3) {
                if (personalContratoBean.getDiaMiercoles() == true) {
                    this.flgMiercoles = true;
                } else {
                    this.flgMiercoles = false;
                    personalContratoBean.setHoraIniMiercoles("");
                    personalContratoBean.setHoraFinMiercoles("");
                    personalContratoBean.setHoraRefrigerioMiercoles("");
                    setHorasCalculadasMiercoles("");
                }
            }
            if (n == 4) {
                if (personalContratoBean.getDiaJueves() == true) {
                    this.flgJueves = true;
                } else {
                    this.flgJueves = false;
                    personalContratoBean.setHoraIniJueves("");
                    personalContratoBean.setHoraFinJueves("");
                    personalContratoBean.setHoraRefrigerioJueves("");
                    setHorasCalculadasJueves("");
                }
            }
            if (n == 5) {
                if (personalContratoBean.getDiaViernes() == true) {
                    this.flgViernes = true;
                } else {
                    this.flgViernes = false;
                    personalContratoBean.setHoraIniViernes("");
                    personalContratoBean.setHoraFinViernes("");
                    personalContratoBean.setHoraRefrigerioViernes("");
                    setHorasCalculadasViernes("");
                }
            }
            if (n == 6) {
                if (personalContratoBean.getDiaSabado() == true) {
                    this.flgSabado = true;
                } else {
                    this.flgSabado = false;
                    personalContratoBean.setHoraIniSabado("");
                    personalContratoBean.setHoraFinSabado("");
                    personalContratoBean.setHoraRefrigerioSabado("");
                    setHorasCalculadasSabado("");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarTrabajoRiesgoso() {
        try {
            if (personalBean.getFlgTrabajoAltoRiesgo() == true) {
                this.flgTrabajoAltoRiesgo = true;
            } else {
                this.flgTrabajoAltoRiesgo = false;
                personalBean.setNroPolizaSctr("");
                personalBean.setNombreCompaniaSctr("");
                personalBean.setBeneficiariosSctr("");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarEstudios(Integer numero) {
        try {
            this.flgOcultarGradoAcademico = null;
            if (numero == 1) {
                if (personalFormacionSuperiorBean.getFlgProceso() == true) {
                    this.flgOcultarGradoAcademico = false;
                    this.flgEnProceso = true;
                    this.flgSoloCulminado = null;
                } else {
                    this.flgOcultarGradoAcademico = true;
                    this.flgEnProceso = false;
                    this.flgSoloCulminado = false;
                }
            }
            if (numero == 2) {
                if (personalFormacionSuperiorBean.getFlgSoloConcluido() == true) {
                    this.flgOcultarGradoAcademico = false;
                    this.flgEnProceso = null;
                    this.flgSoloCulminado = true;
                } else {
                    this.flgOcultarGradoAcademico = true;
                    this.flgEnProceso = false;
                    this.flgSoloCulminado = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void tieneCertificadoFinanciamiento(Integer numero) {
        try {
            if (numero == 1) {
                if (personalOtrosEstudiosBean.getFlgCertificado() == true) {
                    flgCertificado = true;
                } else {
                    flgCertificado = false;
                }
            }
            if (numero == 2) {
                if (personalOtrosEstudiosBean.getTipoFinanciamientoBean().getIdCodigo().equals(31703)) {
                    flgFinanciamiento = true;
                } else {
                    flgFinanciamiento = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void cargarTipoFlgMaristaEstudios() {
        try {
            System.out.println("flgMarista: " + personalFormacionBasicaBean.getFlgMarista());
            if (personalFormacionBasicaBean.getFlgMarista() == false) {
                personalFormacionBasicaBean.setFlgMarista(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void calcularHoras() {
        try {
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            if (personalContratoBean.getHoraIniLunes() != null && personalContratoBean.getHoraFinLunes() != null) {
                horasCalculadasLunes = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniLunes(), personalContratoBean.getHoraFinLunes());
            }
            if (personalContratoBean.getHoraIniMartes() != null && personalContratoBean.getHoraFinMartes() != null) {
                horasCalculadasMartes = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniMartes(), personalContratoBean.getHoraFinMartes());
            }
            if (personalContratoBean.getHoraIniMiercoles() != null && personalContratoBean.getHoraFinMiercoles() != null) {
                horasCalculadasMiercoles = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniMiercoles(), personalContratoBean.getHoraFinMiercoles());
            }
            if (personalContratoBean.getHoraIniJueves() != null && personalContratoBean.getHoraFinJueves() != null) {
                horasCalculadasJueves = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniJueves(), personalContratoBean.getHoraFinJueves());
            }
            if (personalContratoBean.getHoraIniViernes() != null && personalContratoBean.getHoraFinViernes() != null) {
                horasCalculadasViernes = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniViernes(), personalContratoBean.getHoraFinViernes());
            }
            if (personalContratoBean.getHoraIniSabado() != null && personalContratoBean.getHoraFinSabado() != null) {
                horasCalculadasSabado = personalContratoService.obtenerPersonalHorasTrabajadas(personalContratoBean.getHoraIniSabado(), personalContratoBean.getHoraFinSabado());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }

    public void guardarPersonalBancaria() {
        try {
            if (personalInformacionBancariaBean.getIdPersonalInformacionBancaria() == null) {
                insertarPersonalBancaria();
            } else {
                modificarPersonalBancaria();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String insertarPersonalBancaria() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalInformacionBancariaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalInformacionBancariaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalInformacionBancariaBean.setPersonalBean(personalBean);
                personalContratoService.insertarPersonalBancaria(personalInformacionBancariaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalBancaria() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                personalInformacionBancariaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalContratoService.modificarPersonalBancaria(personalInformacionBancariaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelectPersonalVacaciones(SelectEvent event) {
        try {
            PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
            personalVacacionesBean = (PersonalContratoBean) event.getObject();
            personalVacacionesBean = personalContratoService.obtenerPersonalContratoPorId(personalVacacionesBean);
            mostrarPanelVaca();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }

    }
    //--------------------- Fin PersonalAlergia-----------------------

    //--------------------- Inicio PersonalFormacionBasica-----------------------
    public void limpiarPersonalFormacionBasica() {
        personalFormacionBasicaBean = new PersonalFormacionBean();
        cargarAnoFormacionBasica();
    }

    public void mostrarPanelForBas() {
        personalFormacionBasicaBean.setCollapsed(false);
    }

    public void limpiarPersonalFormacionBasicaYmostrarPanelForBas() {
        personalFormacionBasicaBean = new PersonalFormacionBean();
        personalFormacionBasicaBean.setCollapsed(false);
        cargarAnoFormacionBasica();
    }

    public void obtenerPersonalFormacionBasicaPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalFormacionService personalFormacionBasicaService = BeanFactory.getPersonalFormacionService();
            listaPersonalFormacionBasicaBean = personalFormacionBasicaService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);
            limpiarPersonalFormacionBasica();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalFormacionBasicaPorId(Object object) {
        try {
            personalFormacionBasicaBean = (PersonalFormacionBean) object;
            PersonalFormacionService personalFormacionBasicaService = BeanFactory.getPersonalFormacionService();
            personalFormacionBasicaBean = personalFormacionBasicaService.obtenerPersonalFormacionBasicaPorId(getPersonalFormacionBasicaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalFormacionCarismaPorId(Object object) {
        try {
            personalFormacionCarismaBean = (PersonalFormacionCarismaBean) object;
            PersonalFormacionService personalFormacionCarismaticaService = BeanFactory.getPersonalFormacionService();
            personalFormacionCarismaBean = personalFormacionCarismaticaService.obtenerPersonalFormacionCarismaID(getPersonalFormacionCarismaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalFormacionBasica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {

//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personalFormacionBasicaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalFormacionBasicaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                PersonalFormacionService personalFormacionBasicaService = BeanFactory.getPersonalFormacionService();
                personalFormacionBasicaBean.setPersonalBean(personalBean);
                personalFormacionBasicaService.insertarPersonalFormacionBasica(personalFormacionBasicaBean);
                listaPersonalFormacionBasicaBean = personalFormacionBasicaService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);
                limpiarPersonalFormacionBasica();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalFormacionBasica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionBasicaService = BeanFactory.getPersonalFormacionService();
                personalFormacionBasicaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalFormacionBasicaService.modificarPersonalFormacionBasica(personalFormacionBasicaBean);
                listaPersonalFormacionBasicaBean = personalFormacionBasicaService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);
                limpiarPersonalFormacionBasica();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalFormacionBasica() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionBasicaService = BeanFactory.getPersonalFormacionService();
                personalFormacionBasicaService.eliminarPersonalFormacionBasica(personalFormacionBasicaBean);
                listaPersonalFormacionBasicaBean = personalFormacionBasicaService.obtenerPersonalFormacionBasicaPorPersonalNew(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalFormacionBasica();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalFormacionBasica() {

        try {
            if (personalFormacionBasicaBean.getIdPersonalFormacion() == null) {
                insertarPersonalFormacionBasica();
            } else {
                modificarPersonalFormacionBasica();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalFormacionBasica(SelectEvent event) {
        try {
            PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
            personalFormacionBasicaBean = (PersonalFormacionBean) event.getObject();
            personalFormacionBasicaBean = personalFormacionService.obtenerPersonalFormacionBasicaPorId(personalFormacionBasicaBean);
            mostrarPanelForBas();
            obtenerGradoAcaBasica();
            actualizarListaFormacionBasica(event);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectPersonalFormacionCarisma(SelectEvent event) {
        try {
            PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
            personalFormacionCarismaBean = (PersonalFormacionCarismaBean) event.getObject();
            personalFormacionCarismaBean = personalFormacionService.obtenerPersonalFormacionCarismaID(personalFormacionCarismaBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAnoFormacionBasica() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int b = MaristaConstantes.ANO_INI_DEFAULT;
            int a = miCalendario.get(Calendar.YEAR);
            listaAnosIniFormacionBasica = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosIniFormacionBasica.add(i);
            }
            listaAnosFinFormacionBasica = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosFinFormacionBasica.add(i);
            }
        } catch (Exception e) {
        }
    }

    public void actualizarListaFormacionBasica(AjaxBehaviorEvent event) {
        try {

            Calendar miCalendario = Calendar.getInstance();
            int a = personalFormacionBasicaBean.getAnioIni();
            int b = miCalendario.get(Calendar.YEAR);
            listaAnosFinFormacionBasica = new ArrayList<>();
            for (int i = a; i <= b; i++) {
                listaAnosFinFormacionBasica.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPaisFormacionBasica() {
        try {
            for (int i = 0; i < listaEntidadFormacionBasica.size(); i++) {
                if (personalFormacionBasicaBean.getEntidadBean().getRuc() != null
                        && listaEntidadFormacionBasica.get(i).getRuc().equals(personalFormacionBasicaBean.getEntidadBean().getRuc())) {
                    personalFormacionBasicaBean.getPaisBean().setIdPais(listaEntidadFormacionBasica.get(i).getIdPais());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //--------------------- Fin PersonalFormacion Basica---------------------------
    //--------------------- Inicio PersonalFormacion Superior-----------------------
    public void limpiarPersonalFormacionSuperior() {
        personalFormacionSuperiorBean = new PersonalFormacionBean();
        cargarAnoFormacionSuperior();
    }

    public void mostrarPanelForSup() {
        personalFormacionSuperiorBean.setCollapsed(false);
    }

    public void limpiarPersonalFormacionSuperiorYmostrarPanelForSup() {
        personalFormacionSuperiorBean = new PersonalFormacionBean();
        personalFormacionSuperiorBean.setCollapsed(false);
        cargarAnoFormacionSuperior();
    }

    public void obtenerPersonalFormacionSuperiorPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalFormacionService personalFormacionSuperiorService = BeanFactory.getPersonalFormacionService();
            listaPersonalFormacionSuperiorBean = personalFormacionSuperiorService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);
            limpiarPersonalFormacionSuperior();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalFormacionSuperiorPorId(Object object) {
        try {
            personalFormacionSuperiorBean = (PersonalFormacionBean) object;
            PersonalFormacionService personalFormacionSuperiorService = BeanFactory.getPersonalFormacionService();
            personalFormacionSuperiorBean = personalFormacionSuperiorService.obtenerPersonalFormacionSuperiorPorId(getPersonalFormacionSuperiorBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalObjetoPDF() {
        try {
            System.out.println("objeto " + personalPDFBean.getIdObjeto());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalFormacionSuperior() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personalFormacionSuperiorBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalFormacionSuperiorBean.setCreaPor(beanUsuarioSesion.getUsuario());
                PersonalFormacionService personalFormacionSuperiorService = BeanFactory.getPersonalFormacionService();
                personalFormacionSuperiorBean.setPersonalBean(personalBean);
                personalFormacionSuperiorService.insertarPersonalFormacionSuperior(personalFormacionSuperiorBean);
                listaPersonalFormacionSuperiorBean = personalFormacionSuperiorService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);
                limpiarPersonalFormacionSuperior();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalFormacionSuperior() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionSuperiorService = BeanFactory.getPersonalFormacionService();
                personalFormacionSuperiorBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalFormacionSuperiorService.modificarPersonalFormacionSuperior(personalFormacionSuperiorBean);
                listaPersonalFormacionSuperiorBean = personalFormacionSuperiorService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);
                limpiarPersonalFormacionSuperior();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalFormacionSuperior() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionSuperiorService = BeanFactory.getPersonalFormacionService();
                personalFormacionSuperiorService.eliminarPersonalFormacionSuperior(personalFormacionSuperiorBean);
                listaPersonalFormacionSuperiorBean = personalFormacionSuperiorService.obtenerPersonalFormacionSuperiorPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalFormacionSuperior();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalFormacionSuperior() {
        try {
            if (personalFormacionSuperiorBean.getIdPersonalFormacion() == null) {
                insertarPersonalFormacionSuperior();
            } else {
                modificarPersonalFormacionSuperior();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCsv(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
//            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\";
            String destination = "/SigmaDocumentos////archivosPDFLegajo////";
            System.out.println(">>>" + destination);
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            //INSERTANDO EN LA TABLA PERSONALPDF
            PersonalPDFBean personalPDF = new PersonalPDFBean();
            personalPDF.setCreaPor(beanUsuarioSesion.getUsuario());
            personalPDF.setNombrePdf(fileName);
            for (CodigoBean codi : listaTipoObjetoSubirPDF) {
                if (codi.getIdCodigo().equals(personalPDFBean.getIdObjeto())) {
                    personalPDF.setObjeto(codi.getCodigo());
                }
            }
            personalPDF.setIdObjeto(personalPDFBean.getIdObjeto());
            personalPDF.getPersonalBean().setIdPersonal(personalBean.getIdPersonal());
            personalPDF.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
            personalDocumentoService.insertarPersonalPDF(personalPDF);
            listaPersonalPDFBean = personalDocumentoService.obtenerPersonalPDFPorPersonal(personalBean);
            personalPDFBean.setIdObjeto(null);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirTodosPdfDocumentosLegajo(PersonalPDFBean personalPDFBean) {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            File ficheroXLS = new File("C:\\SigmaDocumentos\\archivosPDFLegajo\\" + personalPDFBean.getNombrePdf());
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;

            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = "application/vnd.ms-excel";
                //String contentType = "application/pdf";
                HttpServletResponse response
                        = (HttpServletResponse) ctx.getExternalContext().getResponse();

                response.setContentType(contentType);

                response.setHeader("Content-Disposition",
                        "attachment;filename=\"" + fileName + "\"");

                out = response.getOutputStream();
                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                System.out.println("\nDescargado\n");
                ctx.responseComplete();
            }

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void rowSelectPersonalFormacionSuperior(SelectEvent event) {
        try {

            PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
            personalFormacionSuperiorBean = (PersonalFormacionBean) event.getObject();
            personalFormacionSuperiorBean = personalFormacionService.obtenerPersonalFormacionSuperiorPorId(personalFormacionSuperiorBean);
            mostrarPanelForSup();
            actualizarListaFormacionSuperior(event);
            obtenerGradoAcaSuperior();
            obtenerNivelAcaSuperior();
            obtenerCarreraPorNivelAcademicoSuperior();
            obtenerCarreraAreaSuperiorPorGradoAca();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAnoFormacionSuperior() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int b = MaristaConstantes.ANO_INI_DEFAULT;
            int a = miCalendario.get(Calendar.YEAR);
            listaAnosIniFormacionSuperior = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosIniFormacionSuperior.add(i);
            }
            listaAnosFinFormacionSuperior = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosFinFormacionSuperior.add(i);
            }
        } catch (Exception e) {
        }
    }

    public void actualizarListaFormacionSuperior(AjaxBehaviorEvent event) {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int a = personalFormacionSuperiorBean.getAnioIni();
            int b = miCalendario.get(Calendar.YEAR);
            listaAnosFinFormacionSuperior = new ArrayList<>();
            for (int i = a; i <= b; i++) {
                listaAnosFinFormacionSuperior.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCarreraPorId(Object object) {
        try {
            formacionSuperiorCarreraBean = (CarreraBean) object;
            CarreraService carreraService = BeanFactory.getCarreraService();
            formacionSuperiorCarreraBean = carreraService.obtenerCarreraPorId(personalFormacionSuperiorBean.getCarreraBean().getIdCarrera());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerCarrera(Object object) {
        CarreraBean car = (CarreraBean) object;
        personalFormacionSuperiorBean.setCarreraBean(car);
    }

    public void obtenerPaisFormacionSuperior() {
        try {
            for (int i = 0; i < listaEntidadFormacionSuperior.size(); i++) {
                if (personalFormacionSuperiorBean.getEntidadBean().getRuc() != null
                        && listaEntidadFormacionSuperior.get(i).getRuc().equals(personalFormacionSuperiorBean.getEntidadBean().getRuc())) {
                    personalFormacionSuperiorBean.getPaisBean().setIdPais(listaEntidadFormacionSuperior.get(i).getIdPais());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //--------------------- Inicio PersonalOtrosEstudios Superior-----------------------
    public void limpiarPersonalOtrosEstudios() {
        personalOtrosEstudiosBean = new PersonalOtrosEstudiosBean();
        cargarAnoOtrosEstudios();
    }

    public void mostrarPanelForOtrosEst() {
        personalOtrosEstudiosBean.setCollapsed(false);
    }

    public void limpiarPersonalOtrosEstudiosYmostrarPanelForOtrosEst() {
        personalOtrosEstudiosBean = new PersonalOtrosEstudiosBean();
        personalOtrosEstudiosBean.setCollapsed(false);
        cargarAnoOtrosEstudios();
    }

    public void obtenerPersonalOtrosEstudiosPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
            listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);
            limpiarPersonalOtrosEstudios();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalOtrosEstudiosPorId(Object object) {
        try {
            personalOtrosEstudiosBean = (PersonalOtrosEstudiosBean) object;
            PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
            personalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorId(getPersonalOtrosEstudiosBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalOtrosEstudios() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin"); 
                PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
                personalOtrosEstudiosBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalOtrosEstudiosBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalOtrosEstudiosBean.setPersonalBean(personalBean);
                System.out.println("----->");
                System.out.println("-" + personalOtrosEstudiosBean.getCentroEstudio());
                personalOtrosEstudiosService.insertarPersonalOtrosEstudios(personalOtrosEstudiosBean);
                listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);
                limpiarPersonalOtrosEstudios();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalOtrosEstudios() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
                personalOtrosEstudiosBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalOtrosEstudiosService.modificarPersonalOtrosEstudios(personalOtrosEstudiosBean);
                listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);
                limpiarPersonalOtrosEstudios();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalOtrosEstudios() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
                personalOtrosEstudiosService.eliminarPersonalOtrosEstudios(personalOtrosEstudiosBean);
                listaPersonalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalOtrosEstudios();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalOtrosEstudios() {

        try {
            if (personalOtrosEstudiosBean.getIdPersonalOtrosEstudios() == null) {
                insertarPersonalOtrosEstudios();
            } else {
                modificarPersonalOtrosEstudios();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void rowSelectPersonalOtrosEstudios(SelectEvent event) {
        try {
            PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
            personalOtrosEstudiosBean = (PersonalOtrosEstudiosBean) event.getObject();
            personalOtrosEstudiosBean = personalOtrosEstudiosService.obtenerPersonalOtrosEstudiosPorId(personalOtrosEstudiosBean);

            mostrarPanelForOtrosEst();
            actualizarListaOtrosEstudios(event);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAnoOtrosEstudios() {
        try {
            Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            int b = MaristaConstantes.ANO_INI_DEFAULT;
            int a = Integer.parseInt(formato.format(fecha));
            listaAnosIniOtrosEstudios = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosIniOtrosEstudios.add(i);
            }
            listaAnosFinOtrosEstudios = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnosFinOtrosEstudios.add(i);
            }
        } catch (Exception e) {
        }
    }

    public void actualizarListaOtrosEstudios(AjaxBehaviorEvent event) {
        try {
            Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            int a = personalOtrosEstudiosBean.getAnioIni();
            int b = Integer.parseInt(formato.format(fecha));
            listaAnosFinOtrosEstudios = new ArrayList<>();
            for (int i = a; i <= b; i++) {
                listaAnosFinOtrosEstudios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerPaisOtrosEstudios() {
//        try {
//            for (int i = 0; i < listaEntidadOtrosEstudios.size(); i++) {
//                if (personalOtrosEstudiosBean.getEntidadBean().getRuc() != null
//                        && listaEntidadOtrosEstudios.get(i).getRuc().equals(personalOtrosEstudiosBean.getEntidadBean().getRuc())) {
//                    personalOtrosEstudiosBean.getPaisBean().setIdPais(listaEntidadOtrosEstudios.get(i).getIdPais());
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //--------------------- Fin PersonalFormacionSuperior---------------------------
    //--------------------- Inicio PersonalIdioma-----------------------
    public void limpiarPersonalIdioma() {
        personalIdiomaBean = new PersonalIdiomaBean();
    }

    public void mostrarPanelIdioma() {
        personalIdiomaBean.setCollapsed(false);
    }

    public void limpiarPersonalIdiomaYmostrarPanelIdioma() {
        personalIdiomaBean = new PersonalIdiomaBean();
        personalIdiomaBean.setCollapsed(false);
    }

    public void limpiarPersonalFormacionCarisma() {
        personalFormacionCarismaBean = new PersonalFormacionCarismaBean();
    }

    public void obtenerPersonalIdiomaPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
            listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);
            limpiarPersonalIdioma();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalIdiomaPorId(Object object) {
        try {
            personalIdiomaBean = (PersonalIdiomaBean) object;
            PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
            personalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorId(getPersonalIdiomaBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalIdioma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
                personalIdiomaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalIdiomaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalIdiomaBean.setPersonalBean(personalBean);
                personalIdiomaService.insertarPersonalIdioma(personalIdiomaBean);
                listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);
                limpiarPersonalIdioma();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalIdioma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
                personalIdiomaService.modificarPersonalIdioma(personalIdiomaBean);
                listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);
                limpiarPersonalIdioma();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalIdioma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
                personalIdiomaService.eliminarPersonalIdioma(personalIdiomaBean);
                listaPersonalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorPersonal(personalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonalIdioma();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarPersonalIdioma() {

        try {
            if (personalIdiomaBean.getIdPersonalIdioma() == null) {
                insertarPersonalIdioma();
            } else {
                modificarPersonalIdioma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarPersonalFormacionCarisma() {

        try {
            if (personalFormacionCarismaBean.getIdPersonalFormacionCarisma() == null) {
                insertarPersonalFormacionCarisma();
            } else {
                modificarPersonalFormacionCarisma();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonalFormacionCarisma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
                personalFormacionCarismaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                personalFormacionCarismaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                personalFormacionCarismaBean.setPersonalBean(personalBean);
                personalFormacionService.insertarPersonalFormacionCarisma(personalFormacionCarismaBean);
                listPersonalForCarismaBean = personalFormacionService.obtenerPersonalFormacionCarismaLista(personalFormacionCarismaBean);
                limpiarPersonalFormacionCarisma();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarPersonalFormacionCarisma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionCarismaService = BeanFactory.getPersonalFormacionService();
                personalFormacionCarismaService.eliminarPersonalFormacionCarisma(personalFormacionCarismaBean);
                listPersonalForCarismaBean = personalFormacionCarismaService.obtenerPersonalFormacionCarismaLista(personalFormacionCarismaBean);
                limpiarPersonalFormacionCarisma();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarPersonalFormacionCarisma() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
                personalFormacionCarismaBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                personalFormacionCarismaBean.setModiPor(beanUsuarioSesion.getUsuario());
                personalFormacionService.modificarPersonalFormacionCarisma(personalFormacionCarismaBean);
                listPersonalForCarismaBean = personalFormacionService.obtenerPersonalFormacionCarismaLista(personalFormacionCarismaBean);
                limpiarPersonalFormacionCarisma();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void rowSelectPersonalIdioma(SelectEvent event) {
        try {
            PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
            personalIdiomaBean = (PersonalIdiomaBean) event.getObject();
            personalIdiomaBean = personalIdiomaService.obtenerPersonalIdiomaPorId(personalIdiomaBean);
            mostrarPanelIdioma();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //--------------------- Fin PersonalIdioma---------------------------
    //--------------------- Inicio PersonalDocumento-----------------------
    public void limpiarPersonalDocumento() {
        personalDocumentoBean = new PersonalDocumentoBean();
    }

    public void obtenerPersonalDocumentoPorPersonal(Object object) {
        try {
            personalBean = (PersonalBean) object;
            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
            listaPersonalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorPersonal(personalBean);
            limpiarPersonalDocumento();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonalDocumentoPorId(Object object) {
        try {
            personalDocumentoBean = (PersonalDocumentoBean) object;
            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
            personalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorId(getPersonalDocumentoBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //modificar dataTable 
    public void onRowEdit(RowEditEvent event) {
        try {
            PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();
            personalDocumentoBean = new PersonalDocumentoBean();
            //seteo el ID
            personalDocumentoBean.setIdPersonalDocumento(((PersonalDocumentoBean) event.getObject()).getIdPersonalDocumento());
            personalDocumentoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

            personalDocumentoBean.setFechaCaduca(((PersonalDocumentoBean) event.getObject()).getFechaCaduca());
            personalDocumentoBean.setFlgPresentacion(((PersonalDocumentoBean) event.getObject()).getFlgPresentacion());
            personalDocumentoBean.setFechaPresentacion(((PersonalDocumentoBean) event.getObject()).getFechaPresentacion());
//            if ((((PersonalDocumentoBean) event.getObject()).getFlgPresentacion()) == true
//                    && (((PersonalDocumentoBean) event.getObject()).getFechaPresentacion() != null)) {
//                personalDocumentoBean.setStatus(Boolean.TRUE);
//            }
//            if ((((PersonalDocumentoBean) event.getObject()).getFlgPresentacion()) == false) {
//                personalDocumentoBean.setFechaPresentacion(null);
//                personalDocumentoBean.setStatus(Boolean.FALSE);
//            }
            if ((((PersonalDocumentoBean) event.getObject()).getFechaPresentacion() != null)) {
                personalDocumentoBean.setStatus(Boolean.TRUE);
                personalDocumentoBean.setFlgPresentacion(Boolean.TRUE);
            } else {
                personalDocumentoBean.setFechaPresentacion(null);
                personalDocumentoBean.setStatus(Boolean.FALSE);
            }

            personalDocumentoBean.setModiPor(beanUsuarioSesion.getUsuario());
            personalDocumentoService.modificarPersonalDocumento(personalDocumentoBean);

            listaPersonalDocumentoBean = personalDocumentoService.obtenerPersonalDocumentoPorPersonal(personalBean);
            FacesMessage msg = new FacesMessage("Registro Modificado:", ((PersonalDocumentoBean) event.getObject()).getDocumentoBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            documentoCargoBean = new DocumentoCargoBean();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edici髇 Cancelada", ((PersonalDocumentoBean) event.getObject()).getDocumentoBean().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void obtenerCRPorUniOrg() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCR1 = new ArrayList<>();
            listaCR2 = listaCR1;
            listaCR3 = listaCR1;
            listaCR4 = listaCR1;
            listaCR5 = listaCR1;
            listaCR1 = centroResponsabilidadService.obtenerPorUniOrg(personalBean.getUnidadOrganicaBean().getIdUniOrg());
            if (listaCR1.isEmpty()) {
                listaCR1 = centroResponsabilidadService.obtenerPorUniOrgPadre(personalBean.getUnidadOrganicaBean().getIdUniOrg());
                if (listaCR1.isEmpty()) {
                    listaCR1 = centroResponsabilidadService.obtenerCentroResponsabilidad();
                }
            }
            listaCR2 = listaCR1;
            listaCR3 = listaCR1;
            listaCR4 = listaCR1;
            listaCR5 = listaCR1;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //--------------------- Fin PersonalDocumento---------------------------
    public void imprimirRepPersonal() {
        // reportes////////////////////////////////////////// 
        ServletOutputStream out = null;
        try {
            if (listaPersonaRepFiltroBean != null) {
                if (!listaPersonaRepFiltroBean.isEmpty()) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                            getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteColaboradores.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    List<PersonalRepBean> lista = new ArrayList<>();
                    lista = listaPersonaRepFiltroBean;
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(lista);
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
                }
            }
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

    public void imprimirFichaPersonal(Integer idPersonalRep) {
        // reportes////////////////////////////////////////// 
        ServletOutputStream out = null;
        try {
//            if (listaPersonaRepFiltroBean != null) {
//                if (!listaPersonaRepFiltroBean.isEmpty()) {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteLegajoPersonal.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            LegajoService legajoService = BeanFactory.getLegajoService();
            List<LegajoRepBean> listaFichaPersonal = new ArrayList<>();
            String uniNeg = beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaFichaPersonal = legajoService.obtenerFichaPersonal(idPersonalRep, uniNeg);
            if (listaFichaPersonal != null) {
                PersonalDependienteService personalDependienteService = BeanFactory.getPersonalDependienteService();
                PersonalFormacionService personalFormacionService = BeanFactory.getPersonalFormacionService();
                PersonalOtrosEstudiosService personalOtrosEstudiosService = BeanFactory.getPersonalOtrosEstudiosService();
                PersonalIdiomaService personalIdiomaService = BeanFactory.getPersonalIdiomaService();
                PersonalCargoService personalCargoService = BeanFactory.getPersonalCargoService();
                PersonalExperienciaService personalExperienciaService = BeanFactory.getPersonalExperienciaService();
                PersonalContratoService personalContratoService = BeanFactory.getPersonalContratoService();
                PersonalProcesoJudicialService personalProcesoJudicialService = BeanFactory.getPersonalProcesoJudicialService();
                PersonalDocumentoService personalDocumentoService = BeanFactory.getPersonalDocumentoService();

                List<PersonalDependienteRepBean> listaDependienteRep = new ArrayList<>();
                listaDependienteRep = personalDependienteService.obtenerDependientePorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalDependientes(listaDependienteRep);

                List<PersonalFormacionRepBean> listaFormacionBasicaRep = new ArrayList<>();
                listaFormacionBasicaRep = personalFormacionService.obtenerFormacionBasicaPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalFormacionBasica(listaFormacionBasicaRep);

                List<PersonalFormacionRepBean> listaFormacionSuperiorRep = new ArrayList<>();
                listaFormacionSuperiorRep = personalFormacionService.obtenerFormacionSuperiorPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalFormacionSuperior(listaFormacionSuperiorRep);

                List<PersonalOtrosEstudiosRepBean> listaOtrosEstudiosRep = new ArrayList<>();
                listaOtrosEstudiosRep = personalOtrosEstudiosService.obtenerOtrosEstudiosPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalOtrosEstudios(listaOtrosEstudiosRep);

                List<PersonalIdiomaRepBean> listaIdiomaRep = new ArrayList<>();
                listaIdiomaRep = personalIdiomaService.obtenerIdiomaPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalIdioma(listaIdiomaRep);

                List<PersonalContratoRepBean> listaContratoRep = new ArrayList<>();
                listaContratoRep = personalContratoService.obtenerContratoPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalContrato(listaContratoRep);

                List<PersonalContratoRepBean> listaVacaRep = new ArrayList<>();
                listaVacaRep = personalContratoService.obtenerVacacionesPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalVacaciones(listaVacaRep);

                List<PersonalCargoRepBean> listaCargoRep = new ArrayList<>();
                listaCargoRep = personalCargoService.obtenerCargoPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalCargo(listaCargoRep);

                List<PersonalExperienciaRepBean> listaExpRep = new ArrayList<>();
                listaExpRep = personalExperienciaService.obtenerExperienciaPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalExperiencia(listaExpRep);

                List<PersonalProcesoJudicialRepBean> listaProcesoJudRep = new ArrayList<>();
                listaProcesoJudRep = personalProcesoJudicialService.obtenerProcesoJudicialPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalProcesoJudicial(listaProcesoJudRep);

                List<PersonalDocumentoRepBean> listaDocRep = new ArrayList<>();
                listaDocRep = personalDocumentoService.obtenerDocumentoPorPersonalRep(idPersonalRep, uniNeg);
                listaFichaPersonal.get(0).setListaPersonalDocumento(listaDocRep);
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaFichaPersonal);
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
//                }
//            }
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

//m茅todos getter y setter
    public List<PersonalBean> getListaPersonalBean() {
        if (listaPersonalBean == null) {
            listaPersonalBean = new ArrayList<>();
        }
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
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

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
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

    public List<PaisBean> getListaNacionalidad() {
        if (listaNacionalidad == null) {
            listaNacionalidad = new ArrayList<>();
        }
        return listaNacionalidad;
    }

    public void setListaNacionalidad(List<PaisBean> listaNacionalidad) {
        this.listaNacionalidad = listaNacionalidad;
    }

    public List<CodigoBean> getListaEstadoCivil() {
        if (listaEstadoCivil == null) {
            listaEstadoCivil = new ArrayList<>();
        }
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<CodigoBean> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<PaisBean> getListaPaisNacimiento() {
        if (listaPaisNacimiento == null) {
            listaPaisNacimiento = new ArrayList<>();
        }
        return listaPaisNacimiento;
    }

    public void setListaPaisNacimiento(List<PaisBean> listaPaisNacimiento) {
        this.listaPaisNacimiento = listaPaisNacimiento;
    }

    public List<CodigoBean> getListaVivienda() {
        if (listaVivienda == null) {
            listaVivienda = new ArrayList<>();
        }
        return listaVivienda;
    }

    public void setListaVivienda(List<CodigoBean> listaVivienda) {
        this.listaVivienda = listaVivienda;
    }

    public List<CodigoBean> getListaCasa() {
        if (listaCasa == null) {
            listaCasa = new ArrayList<>();
        }
        return listaCasa;
    }

    public void setListaCasa(List<CodigoBean> listaCasa) {
        this.listaCasa = listaCasa;
    }

    public List<CodigoBean> getListaStatusCasa() {
        if (listaStatusCasa == null) {
            listaStatusCasa = new ArrayList<>();
        }
        return listaStatusCasa;
    }

    public void setListaStatusCasa(List<CodigoBean> listaStatusCasa) {
        this.listaStatusCasa = listaStatusCasa;
    }

    public List<ViewEntidadBean> getListaEntidadEPS() {
        if (listaEntidadEPS == null) {
            listaEntidadEPS = new ArrayList<>();
        }
        return listaEntidadEPS;
    }

    public void setListaEntidadEPS(List<ViewEntidadBean> listaEntidadEPS) {
        this.listaEntidadEPS = listaEntidadEPS;
    }

    public List<ViewEntidadBean> getListaEntidadPension() {
        if (listaEntidadPension == null) {
            listaEntidadPension = new ArrayList<>();
        }
        return listaEntidadPension;
    }

    public void setListaEntidadPension(List<ViewEntidadBean> listaEntidadPension) {
        this.listaEntidadPension = listaEntidadPension;
    }

//    public List<CodigoBean> getListaAsegurado() {
//        if (listaAsegurado == null) {
//            listaAsegurado = new ArrayList<>();
//        }
//        return listaAsegurado;
//    }
//
//    public void setListaAsegurado(List<CodigoBean> listaAsegurado) {
//        this.listaAsegurado = listaAsegurado;
//    }
    public List<CodigoBean> getListaOcupacion() {
        if (listaOcupacion == null) {
            listaOcupacion = new ArrayList<>();
        }
        return listaOcupacion;
    }

    public void setListaOcupacion(List<CodigoBean> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
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

    public List<CodigoBean> getListaParentesco() {
        if (listaParentesco == null) {
            listaParentesco = new ArrayList<>();
        }
        return listaParentesco;
    }

    public void setListaParentesco(List<CodigoBean> listaParentesco) {
        this.listaParentesco = listaParentesco;
    }

    public PersonalDependienteBean getPersonalDependienteBean() {
        if (personalDependienteBean == null) {
            personalDependienteBean = new PersonalDependienteBean();
        }
        return personalDependienteBean;
    }

    public void setPersonalDependienteBean(PersonalDependienteBean personalDependienteBean) {
        this.personalDependienteBean = personalDependienteBean;
    }

    public List<PersonalDependienteBean> getListaPersonalDependienteBean() {
        if (listaPersonalDependienteBean == null) {
            listaPersonalDependienteBean = new ArrayList<>();
        }
        return listaPersonalDependienteBean;
    }

    public void setListaPersonalDependienteBean(List<PersonalDependienteBean> listaPersonalDependienteBean) {
        this.listaPersonalDependienteBean = listaPersonalDependienteBean;
    }

//    public List<CarreraAreaBean> getListaCarreraArea() {
//        if (listaCarreraArea == null) {
//            listaCarreraArea = new ArrayList<>();
//        }
//        return listaCarreraArea;
//    }
//
//    public void setListaCarreraArea(List<CarreraAreaBean> listaCarreraArea) {
//        this.listaCarreraArea = listaCarreraArea;
//    }
//
////    public CarreraAreaBean getCarreraAreaBean() {
////        if (carreraAreaBean == null) {
////            carreraAreaBean = new CarreraAreaBean();
////        }
////        return carreraAreaBean;
////    }
////
////    public void setCarreraAreaBean(CarreraAreaBean carreraAreaBean) {
////        this.carreraAreaBean = carreraAreaBean;
////    }
////
////    public CarreraSubAreaBean getCarreraSubAreaBean() {
////        if (carreraSubAreaBean == null) {
////            carreraSubAreaBean = new CarreraSubAreaBean();
////        }
////        return carreraSubAreaBean;
////    }
////
////    public void setCarreraSubAreaBean(CarreraSubAreaBean carreraSubAreaBean) {
////        this.carreraSubAreaBean = carreraSubAreaBean;
////    }
    //cumplea駉s
    public void obtenerFiltroCumpleaniosPersonal() {
        try {
            int estado = 1;
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalFiltroBean.getCodPer() != null && !personalFiltroBean.getCodPer().equals("")) {
                personalFiltroBean.setCodPer(personalFiltroBean.getCodPer().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApepat() != null && !personalFiltroBean.getApepat().equals("")) {
                personalFiltroBean.setApepat(personalFiltroBean.getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApemat() != null && !personalFiltroBean.getApemat().equals("")) {
                personalFiltroBean.setApemat(personalFiltroBean.getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getNombre() != null && !personalFiltroBean.getNombre().equals("")) {
                personalFiltroBean.setNombre(personalFiltroBean.getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg() != null && !personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            } //            if (getPersonalFiltroBean().getFechaInicio() != null) {
            //                Timestamp t = new Timestamp(personalFiltroBean.getFechaInicio().getTime());
            //                t.setHours(0);
            //                t.setMinutes(0);
            //                t.setSeconds(0);
            //                getPersonalFiltroBean().setFechaInicio(t);
            //                estado = 1;
            //            }
            //            if (getPersonalFiltroBean().getFechaFin() != null) {
            //                Timestamp u = new Timestamp(personalFiltroBean.getFechaFin().getTime());
            //                u.setHours(23);
            //                u.setMinutes(59);
            //                u.setSeconds(59);
            //                getPersonalFiltroBean().setFechaFin(u);
            //                estado = 1;
            //            }
            if (personalFiltroBean.getMes() != null && !personalFiltroBean.getMes().equals(0)) {
                personalFiltroBean.setMes(personalFiltroBean.getMes());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalCumpleaniosBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonalCumpleaniosBean = legajoService.obtenerCumpleaniosPersonal(personalFiltroBean);
                if (listaPersonalCumpleaniosBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
            System.out.println("fec I:" + getPersonalFiltroBean().getFechaInicio());
            System.out.println("fec FI:" + getPersonalFiltroBean().getFechaFin());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCumpleaniosPersonal() {
        try {
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            LegajoService legajoService = BeanFactory.getLegajoService();
            SimpleDateFormat formato = new SimpleDateFormat("MM");
            String mes = formato.format(new Date());
            System.out.println("mes " + mes);
            getPersonalFiltroBean().setMes(new Integer(mes));
            if (beanUsuarioSesion != null) {
                getPersonalFiltroBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                listaPersonalCumpleaniosBean = legajoService.obtenerCumpleaniosPersonal(personalFiltroBean);
            }
            listaMesesExp();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

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

    public PersonalCargoBean getPersonalCargoBean() {
        if (personalCargoBean == null) {
            personalCargoBean = new PersonalCargoBean();
        }
        return personalCargoBean;
    }

    public void setPersonalCargoBean(PersonalCargoBean personalCargoBean) {
        this.personalCargoBean = personalCargoBean;
    }

    public List<PersonalCargoBean> getListaPersonalCargoBean() {
        if (listaPersonalCargoBean == null) {
            listaPersonalCargoBean = new ArrayList<>();
        }
        return listaPersonalCargoBean;
    }

    public void setListaPersonalCargoBean(List<PersonalCargoBean> listaPersonalCargoBean) {
        this.listaPersonalCargoBean = listaPersonalCargoBean;
    }

    public PersonalEnfermedadBean getPersonalEnfermedadBean() {
        if (personalEnfermedadBean == null) {
            personalEnfermedadBean = new PersonalEnfermedadBean();
        }
        return personalEnfermedadBean;
    }

    public void setPersonalEnfermedadBean(PersonalEnfermedadBean personalEnfermedadBean) {
        this.personalEnfermedadBean = personalEnfermedadBean;
    }

    public List<PersonalEnfermedadBean> getListaPersonalEnfermedadBean() {
        if (listaPersonalEnfermedadBean == null) {
            listaPersonalEnfermedadBean = new ArrayList<>();
        }
        return listaPersonalEnfermedadBean;
    }

    public void setListaPersonalEnfermedadBean(List<PersonalEnfermedadBean> listaPersonalEnfermedadBean) {
        this.listaPersonalEnfermedadBean = listaPersonalEnfermedadBean;
    }

    public EnfermedadBean getEnfermedadBean() {
        if (enfermedadBean == null) {
            enfermedadBean = new EnfermedadBean();
        }
        return enfermedadBean;
    }

    public void setEnfermedadBean(EnfermedadBean enfermedadBean) {
        this.enfermedadBean = enfermedadBean;
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

    public List<CodigoBean> getListaStatusEnfermedadBean() {
        if (listaStatusEnfermedadBean == null) {
            listaStatusEnfermedadBean = new ArrayList<>();
        }
        return listaStatusEnfermedadBean;
    }

    public void setListaStatusEnfermedadBean(List<CodigoBean> listaStatusEnfermedadBean) {
        this.listaStatusEnfermedadBean = listaStatusEnfermedadBean;
    }

    public PersonalExperienciaBean getPersonalExperienciaBean() {
        if (personalExperienciaBean == null) {
            personalExperienciaBean = new PersonalExperienciaBean();
        }
        return personalExperienciaBean;
    }

    public void setPersonalExperienciaBean(PersonalExperienciaBean personalExperienciaBean) {
        this.personalExperienciaBean = personalExperienciaBean;
    }

    public List<PersonalExperienciaBean> getListaPersonalExperienciaBean() {
        if (listaPersonalExperienciaBean == null) {
            listaPersonalExperienciaBean = new ArrayList<>();
        }
        return listaPersonalExperienciaBean;
    }

    public void setListaPersonalExperienciaBean(List<PersonalExperienciaBean> listaPersonalExperienciaBean) {
        this.listaPersonalExperienciaBean = listaPersonalExperienciaBean;
    }

    public PersonalContratoBean getPersonalContratoBean() {
        if (personalContratoBean == null) {
            personalContratoBean = new PersonalContratoBean();
        }
        return personalContratoBean;
    }

    public void setPersonalContratoBean(PersonalContratoBean personalContratoBean) {
        this.personalContratoBean = personalContratoBean;
    }

    public List<PersonalContratoBean> getListaPersonalContratoBean() {
        if (listaPersonalContratoBean == null) {
            listaPersonalContratoBean = new ArrayList<>();
        }
        return listaPersonalContratoBean;
    }

    public void setListaPersonalContratoBean(List<PersonalContratoBean> listaPersonalContratoBean) {
        this.listaPersonalContratoBean = listaPersonalContratoBean;
    }

    public List<CodigoBean> getListaTipoContrato() {
        if (listaTipoContrato == null) {
            listaTipoContrato = new ArrayList<>();
        }
        return listaTipoContrato;
    }

    public void setListaTipoContrato(List<CodigoBean> listaTipoContrato) {
        this.listaTipoContrato = listaTipoContrato;
    }

    public PersonalProcesoJudicialBean getPersonalProcesoJudicialBean() {
        if (personalProcesoJudicialBean == null) {
            personalProcesoJudicialBean = new PersonalProcesoJudicialBean();
        }
        return personalProcesoJudicialBean;
    }

    public void setPersonalProcesoJudicialBean(PersonalProcesoJudicialBean personalProcesoJudicialBean) {
        this.personalProcesoJudicialBean = personalProcesoJudicialBean;
    }

    public List<PersonalProcesoJudicialBean> getListaPersonalProcesoJudicialBean() {
        if (listaPersonalProcesoJudicialBean == null) {
            listaPersonalProcesoJudicialBean = new ArrayList<>();
        }
        return listaPersonalProcesoJudicialBean;
    }

    public void setListaPersonalProcesoJudicialBean(List<PersonalProcesoJudicialBean> listaPersonalProcesoJudicialBean) {
        this.listaPersonalProcesoJudicialBean = listaPersonalProcesoJudicialBean;
    }

    public List<CodigoBean> getListaTipoProcesoJudicial() {
        if (listaTipoProcesoJudicial == null) {
            listaTipoProcesoJudicial = new ArrayList<>();
        }
        return listaTipoProcesoJudicial;
    }

    public void setListaTipoProcesoJudicial(List<CodigoBean> listaTipoProcesoJudicial) {
        this.listaTipoProcesoJudicial = listaTipoProcesoJudicial;
    }

    public List<CodigoBean> getListaTipoRetencion() {
        if (listaTipoRetencion == null) {
            listaTipoRetencion = new ArrayList<>();
        }
        return listaTipoRetencion;
    }

    public void setListaTipoRetencion(List<CodigoBean> listaTipoRetencion) {
        this.listaTipoRetencion = listaTipoRetencion;
    }

    public List<CodigoBean> getListaTipoValor() {
        if (listaTipoValor == null) {
            listaTipoValor = new ArrayList<>();
        }
        return listaTipoValor;
    }

    public void setListaTipoValor(List<CodigoBean> listaTipoValor) {
        this.listaTipoValor = listaTipoValor;
    }

    public List<CodigoBean> getListaTipoModoPago() {
        if (listaTipoModoPago == null) {
            listaTipoModoPago = new ArrayList<>();
        }
        return listaTipoModoPago;
    }

    public void setListaTipoModoPago(List<CodigoBean> listaTipoModoPago) {
        this.listaTipoModoPago = listaTipoModoPago;
    }

    public List<CodigoBean> getListaTipoEnfermedad() {
        if (listaTipoEnfermedad == null) {
            listaTipoEnfermedad = new ArrayList<>();
        }
        return listaTipoEnfermedad;
    }

    public void setListaTipoEnfermedad(List<CodigoBean> listaTipoEnfermedad) {
        this.listaTipoEnfermedad = listaTipoEnfermedad;
    }

    public PersonalAlergiaBean getPersonalAlergiaBean() {
        if (personalAlergiaBean == null) {
            personalAlergiaBean = new PersonalAlergiaBean();
        }
        return personalAlergiaBean;
    }

    public void setPersonalAlergiaBean(PersonalAlergiaBean personalAlergiaBean) {
        this.personalAlergiaBean = personalAlergiaBean;
    }

    public List<PersonalAlergiaBean> getListaPersonalAlergiaBean() {
        if (listaPersonalAlergiaBean == null) {
            listaPersonalAlergiaBean = new ArrayList<>();
        }
        return listaPersonalAlergiaBean;
    }

    public void setListaPersonalAlergiaBean(List<PersonalAlergiaBean> listaPersonalAlergiaBean) {
        this.listaPersonalAlergiaBean = listaPersonalAlergiaBean;
    }

    public List<CodigoBean> getListaTipoAlergia() {
        if (listaTipoAlergia == null) {
            listaTipoAlergia = new ArrayList<>();
        }
        return listaTipoAlergia;
    }

    public void setListaTipoAlergia(List<CodigoBean> listaTipoAlergia) {
        this.listaTipoAlergia = listaTipoAlergia;
    }

    public PersonalIdiomaBean getPersonalIdiomaBean() {
        if (personalIdiomaBean == null) {
            personalIdiomaBean = new PersonalIdiomaBean();
        }
        return personalIdiomaBean;
    }

    public void setPersonalIdiomaBean(PersonalIdiomaBean personalIdiomaBean) {
        this.personalIdiomaBean = personalIdiomaBean;
    }

    public List<PersonalIdiomaBean> getListaPersonalIdiomaBean() {
        if (listaPersonalIdiomaBean == null) {
            listaPersonalIdiomaBean = new ArrayList<>();
        }
        return listaPersonalIdiomaBean;
    }

    public void setListaPersonalIdiomaBean(List<PersonalIdiomaBean> listaPersonalIdiomaBean) {
        this.listaPersonalIdiomaBean = listaPersonalIdiomaBean;
    }

    public List<CodigoBean> getListaTipoIdioma() {
        if (listaTipoIdioma == null) {
            listaTipoIdioma = new ArrayList<>();
        }
        return listaTipoIdioma;
    }

    public void setListaTipoIdioma(List<CodigoBean> listaTipoIdioma) {
        this.listaTipoIdioma = listaTipoIdioma;
    }

    public List<CodigoBean> getListaNivel() {
        if (listaNivel == null) {
            listaNivel = new ArrayList<>();
        }
        return listaNivel;
    }

    public void setListaNivel(List<CodigoBean> listaNivel) {
        this.listaNivel = listaNivel;
    }

    public List<PersonalBean> getListaPersonalPorUniNegBean() {
        if (listaPersonalPorUniNegBean == null) {
            listaPersonalPorUniNegBean = new ArrayList<>();
        }
        return listaPersonalPorUniNegBean;
    }

    public void setListaPersonalPorUniNegBean(List<PersonalBean> listaPersonalPorUniNegBean) {
        this.listaPersonalPorUniNegBean = listaPersonalPorUniNegBean;
    }

    public CargoUniNegBean getCargoUniNegBean() {
        if (cargoUniNegBean == null) {
            cargoUniNegBean = new CargoUniNegBean();
        }
        return cargoUniNegBean;
    }

    public void setCargoUniNegBean(CargoUniNegBean cargoUniNegBean) {
        this.cargoUniNegBean = cargoUniNegBean;
    }

    public PersonalFormacionBean getPersonalFormacionBasicaBean() {
        if (personalFormacionBasicaBean == null) {
            personalFormacionBasicaBean = new PersonalFormacionBean();
        }
        return personalFormacionBasicaBean;
    }

    public void setPersonalFormacionBasicaBean(PersonalFormacionBean personalFormacionBasicaBean) {
        this.personalFormacionBasicaBean = personalFormacionBasicaBean;
    }

    public List<PersonalFormacionBean> getListaPersonalFormacionBasicaBean() {
        if (listaPersonalFormacionBasicaBean == null) {
            listaPersonalFormacionBasicaBean = new ArrayList<>();
        }
        return listaPersonalFormacionBasicaBean;
    }

    public void setListaPersonalFormacionBasicaBean(List<PersonalFormacionBean> listaPersonalFormacionBasicaBean) {
        this.listaPersonalFormacionBasicaBean = listaPersonalFormacionBasicaBean;
    }

    public List<PaisBean> getListaPaisFormacionBasica() {
        if (listaPaisFormacionBasica == null) {
            listaPaisFormacionBasica = new ArrayList<>();
        }
        return listaPaisFormacionBasica;
    }

    public void setListaPaisFormacionBasica(List<PaisBean> listaPaisFormacionBasica) {
        this.listaPaisFormacionBasica = listaPaisFormacionBasica;
    }

    public List<ViewEntidadBean> getListaEntidadFormacionBasica() {
        if (listaEntidadFormacionBasica == null) {
            listaEntidadFormacionBasica = new ArrayList<>();
        }
        return listaEntidadFormacionBasica;
    }

    public void setListaEntidadFormacionBasica(List<ViewEntidadBean> listaEntidadFormacionBasica) {
        this.listaEntidadFormacionBasica = listaEntidadFormacionBasica;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFormacionBasica() {
        if (listaGradoAcademicoFormacionBasica == null) {
            listaGradoAcademicoFormacionBasica = new ArrayList<>();
        }
        return listaGradoAcademicoFormacionBasica;
    }

    public void setListaGradoAcademicoFormacionBasica(List<GradoAcademicoBean> listaGradoAcademicoFormacionBasica) {
        this.listaGradoAcademicoFormacionBasica = listaGradoAcademicoFormacionBasica;
    }

    public List<Integer> getListaAnosIniFormacionBasica() {
        if (listaAnosIniFormacionBasica == null) {
            listaAnosIniFormacionBasica = new ArrayList<>();
        }
        return listaAnosIniFormacionBasica;
    }

    public void setListaAnosIniFormacionBasica(List<Integer> listaAnosIniFormacionBasica) {
        this.listaAnosIniFormacionBasica = listaAnosIniFormacionBasica;
    }

    public List<Integer> getListaAnosFinFormacionBasica() {
        if (listaAnosFinFormacionBasica == null) {
            listaAnosFinFormacionBasica = new ArrayList<>();
        }
        return listaAnosFinFormacionBasica;
    }

    public void setListaAnosFinFormacionBasica(List<Integer> listaAnosFinFormacionBasica) {
        this.listaAnosFinFormacionBasica = listaAnosFinFormacionBasica;
    }

    public PersonalFormacionBean getPersonalFormacionSuperiorBean() {
        if (personalFormacionSuperiorBean == null) {
            personalFormacionSuperiorBean = new PersonalFormacionBean();
        }

        return personalFormacionSuperiorBean;
    }

    public void setPersonalFormacionSuperiorBean(PersonalFormacionBean personalFormacionSuperiorBean) {
        this.personalFormacionSuperiorBean = personalFormacionSuperiorBean;
    }

    public List<PersonalFormacionBean> getListaPersonalFormacionSuperiorBean() {
        if (listaPersonalFormacionSuperiorBean == null) {
            listaPersonalFormacionSuperiorBean = new ArrayList<>();
        }
        return listaPersonalFormacionSuperiorBean;
    }

    public void setListaPersonalFormacionSuperiorBean(List<PersonalFormacionBean> listaPersonalFormacionSuperiorBean) {
        this.listaPersonalFormacionSuperiorBean = listaPersonalFormacionSuperiorBean;
    }

    public List<PaisBean> getListaPaisFormacionSuperior() {
        if (listaPaisFormacionSuperior == null) {
            listaPaisFormacionSuperior = new ArrayList<>();
        }
        return listaPaisFormacionSuperior;
    }

    public void setListaPaisFormacionSuperior(List<PaisBean> listaPaisFormacionSuperior) {
        this.listaPaisFormacionSuperior = listaPaisFormacionSuperior;
    }

    public List<ViewEntidadBean> getListaEntidadFormacionSuperior() {
        if (listaEntidadFormacionSuperior == null) {
            listaEntidadFormacionSuperior = new ArrayList<>();
        }
        return listaEntidadFormacionSuperior;
    }

    public void setListaEntidadFormacionSuperior(List<ViewEntidadBean> listaEntidadFormacionSuperior) {
        this.listaEntidadFormacionSuperior = listaEntidadFormacionSuperior;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFormacionSuperior() {
        if (listaGradoAcademicoFormacionSuperior == null) {
            listaGradoAcademicoFormacionSuperior = new ArrayList<>();
        }
        return listaGradoAcademicoFormacionSuperior;
    }

    public void setListaGradoAcademicoFormacionSuperior(List<GradoAcademicoBean> listaGradoAcademicoFormacionSuperior) {
        this.listaGradoAcademicoFormacionSuperior = listaGradoAcademicoFormacionSuperior;
    }

    public List<Integer> getListaAnosIniFormacionSuperior() {
        if (listaAnosIniFormacionSuperior == null) {
            listaAnosIniFormacionSuperior = new ArrayList<>();
        }
        return listaAnosIniFormacionSuperior;
    }

    public void setListaAnosIniFormacionSuperior(List<Integer> listaAnosIniFormacionSuperior) {
        this.listaAnosIniFormacionSuperior = listaAnosIniFormacionSuperior;
    }

    public List<Integer> getListaAnosFinFormacionSuperior() {
        if (listaAnosFinFormacionSuperior == null) {
            listaAnosFinFormacionSuperior = new ArrayList<>();
        }
        return listaAnosFinFormacionSuperior;
    }

    public void setListaAnosFinFormacionSuperior(List<Integer> listaAnosFinFormacionSuperior) {
        this.listaAnosFinFormacionSuperior = listaAnosFinFormacionSuperior;
    }

    public List<CarreraAreaBean> getListaFormacionSuperiorCarreraArea() {
        if (listaFormacionSuperiorCarreraArea == null) {
            listaFormacionSuperiorCarreraArea = new ArrayList<>();
        }
        return listaFormacionSuperiorCarreraArea;
    }

    public void setListaFormacionSuperiorCarreraArea(List<CarreraAreaBean> listaFormacionSuperiorCarreraArea) {
        this.listaFormacionSuperiorCarreraArea = listaFormacionSuperiorCarreraArea;
    }

    public List<CarreraSubAreaBean> getListaFormacionSuperiorCarreraSubArea() {
        if (listaFormacionSuperiorCarreraSubArea == null) {
            listaFormacionSuperiorCarreraSubArea = new ArrayList<>();
        }
        return listaFormacionSuperiorCarreraSubArea;
    }

    public void setListaFormacionSuperiorCarreraSubArea(List<CarreraSubAreaBean> listaFormacionSuperiorCarreraSubArea) {
        this.listaFormacionSuperiorCarreraSubArea = listaFormacionSuperiorCarreraSubArea;
    }

    public List<CarreraBean> getListaFormacionSuperiorCarrera() {
        if (listaFormacionSuperiorCarrera == null) {
            listaFormacionSuperiorCarrera = new ArrayList<>();
        }
        return listaFormacionSuperiorCarrera;
    }

    public void setListaFormacionSuperiorCarrera(List<CarreraBean> listaFormacionSuperiorCarrera) {
        this.listaFormacionSuperiorCarrera = listaFormacionSuperiorCarrera;
    }

    public CarreraBean getFormacionSuperiorCarreraBean() {
        if (formacionSuperiorCarreraBean == null) {
            formacionSuperiorCarreraBean = new CarreraBean();
        }
        return formacionSuperiorCarreraBean;
    }

    public void setFormacionSuperiorCarreraBean(CarreraBean formacionSuperiorCarreraBean) {
        this.formacionSuperiorCarreraBean = formacionSuperiorCarreraBean;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoBasica() {
        if (listaNivelAcademicoBasica == null) {
            listaNivelAcademicoBasica = new ArrayList<>();
        }
        return listaNivelAcademicoBasica;
    }

    public void setListaNivelAcademicoBasica(List<NivelAcademicoBean> listaNivelAcademicoBasica) {
        this.listaNivelAcademicoBasica = listaNivelAcademicoBasica;
    }

    public List<TipoFormacionBean> getListaTipoFormacionSuperior() {
        if (listaTipoFormacionSuperior == null) {
            listaTipoFormacionSuperior = new ArrayList<>();
        }
        return listaTipoFormacionSuperior;
    }

    public void setListaTipoFormacionSuperior(List<TipoFormacionBean> listaTipoFormacionSuperior) {
        this.listaTipoFormacionSuperior = listaTipoFormacionSuperior;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoSuperior() {
        if (listaNivelAcademicoSuperior == null) {
            listaNivelAcademicoSuperior = new ArrayList<>();
        }
        return listaNivelAcademicoSuperior;
    }

    public void setListaNivelAcademicoSuperior(List<NivelAcademicoBean> listaNivelAcademicoSuperior) {
        this.listaNivelAcademicoSuperior = listaNivelAcademicoSuperior;
    }

    public EntidadBean getEntidadFormacionSuperior() {
        return entidadFormacionSuperior;
    }

    public void setEntidadFormacionSuperior(EntidadBean entidadFormacionSuperior) {
        this.entidadFormacionSuperior = entidadFormacionSuperior;
    }

    public Map<String, Integer> getListaMesesForSupMap() {
        return listaMesesForSupMap;
    }

    public void setListaMesesForSupMap(Map<String, Integer> listaMesesForSupMap) {
        this.listaMesesForSupMap = listaMesesForSupMap;
    }

    public Map<String, Integer> getListaMesesForBasMap() {
        return listaMesesForBasMap;
    }

    public void setListaMesesForBasMap(Map<String, Integer> listaMesesForBasMap) {
        this.listaMesesForBasMap = listaMesesForBasMap;
    }

    public PersonalOtrosEstudiosBean getPersonalOtrosEstudiosBean() {
        if (personalOtrosEstudiosBean == null) {
            personalOtrosEstudiosBean = new PersonalOtrosEstudiosBean();
        }
        return personalOtrosEstudiosBean;
    }

    public void setPersonalOtrosEstudiosBean(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) {
        this.personalOtrosEstudiosBean = personalOtrosEstudiosBean;
    }

    public List<PersonalOtrosEstudiosBean> getListaPersonalOtrosEstudiosBean() {
        if (listaPersonalOtrosEstudiosBean == null) {
            listaPersonalOtrosEstudiosBean = new ArrayList<>();
        }
        return listaPersonalOtrosEstudiosBean;
    }

    public void setListaPersonalOtrosEstudiosBean(List<PersonalOtrosEstudiosBean> listaPersonalOtrosEstudiosBean) {
        this.listaPersonalOtrosEstudiosBean = listaPersonalOtrosEstudiosBean;
    }

    public List<PaisBean> getListaPaisOtrosEstudios() {
        if (listaPaisOtrosEstudios == null) {
            listaPaisOtrosEstudios = new ArrayList<>();
        }
        return listaPaisOtrosEstudios;
    }

    public void setListaPaisOtrosEstudios(List<PaisBean> listaPaisOtrosEstudios) {
        this.listaPaisOtrosEstudios = listaPaisOtrosEstudios;
    }

    public List<Integer> getListaAnosIniOtrosEstudios() {
        if (listaAnosIniOtrosEstudios == null) {
            listaAnosIniOtrosEstudios = new ArrayList<>();
        }
        return listaAnosIniOtrosEstudios;
    }

    public void setListaAnosIniOtrosEstudios(List<Integer> listaAnosIniOtrosEstudios) {
        this.listaAnosIniOtrosEstudios = listaAnosIniOtrosEstudios;
    }

    public List<Integer> getListaAnosFinOtrosEstudios() {
        if (listaAnosFinOtrosEstudios == null) {
            listaAnosFinOtrosEstudios = new ArrayList<>();
        }
        return listaAnosFinOtrosEstudios;
    }

    public void setListaAnosFinOtrosEstudios(List<Integer> listaAnosFinOtrosEstudios) {
        this.listaAnosFinOtrosEstudios = listaAnosFinOtrosEstudios;
    }

    public Map<String, Integer> getListaMesesOtrosEstudiosMap() {
        return listaMesesOtrosEstudiosMap;
    }

    public void setListaMesesOtrosEstudiosMap(Map<String, Integer> listaMesesOtrosEstudiosMap) {
        this.listaMesesOtrosEstudiosMap = listaMesesOtrosEstudiosMap;
    }

    public List<Integer> getListaAnosIniExp() {
        if (listaAnosIniExp == null) {
            listaAnosIniExp = new ArrayList<>();
        }
        return listaAnosIniExp;
    }

    public void setListaAnosIniExp(List<Integer> listaAnosIniExp) {
        this.listaAnosIniExp = listaAnosIniExp;
    }

    public List<Integer> getListaAnosFinExp() {
        if (listaAnosFinExp == null) {
            listaAnosFinExp = new ArrayList<>();
        }
        return listaAnosFinExp;
    }

    public void setListaAnosFinExp(List<Integer> listaAnosFinExp) {
        this.listaAnosFinExp = listaAnosFinExp;
    }

    public List<CodigoBean> getListaEstadoCivilDependiente() {
        if (listaEstadoCivilDependiente == null) {
            listaEstadoCivilDependiente = new ArrayList<>();
        }
        return listaEstadoCivilDependiente;
    }

    public void setListaEstadoCivilDependiente(List<CodigoBean> listaEstadoCivilDependiente) {
        this.listaEstadoCivilDependiente = listaEstadoCivilDependiente;
    }

    public List<CodigoBean> getListaDocPerDependiente() {
        if (listaDocPerDependiente == null) {
            listaDocPerDependiente = new ArrayList<>();
        }
        return listaDocPerDependiente;
    }

    public void setListaDocPerDependiente(List<CodigoBean> listaDocPerDependiente) {
        this.listaDocPerDependiente = listaDocPerDependiente;
    }

    public List<PaisBean> getListaNacionalidadDependiente() {
        if (listaNacionalidadDependiente == null) {
            listaNacionalidadDependiente = new ArrayList<>();
        }
        return listaNacionalidadDependiente;
    }

    public void setListaNacionalidadDependiente(List<PaisBean> listaNacionalidadDependiente) {
        this.listaNacionalidadDependiente = listaNacionalidadDependiente;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoDependiente() {
        if (listaGradoAcademicoDependiente == null) {
            listaGradoAcademicoDependiente = new ArrayList<>();
        }
        return listaGradoAcademicoDependiente;
    }

    public void setListaGradoAcademicoDependiente(List<GradoAcademicoBean> listaGradoAcademicoDependiente) {
        this.listaGradoAcademicoDependiente = listaGradoAcademicoDependiente;
    }

    public List<CarreraBean> getListaCarreraDependiente() {
        if (listaCarreraDependiente == null) {
            listaCarreraDependiente = new ArrayList<>();
        }
        return listaCarreraDependiente;
    }

    public void setListaCarreraDependiente(List<CarreraBean> listaCarreraDependiente) {
        this.listaCarreraDependiente = listaCarreraDependiente;
    }

    public List<TipoFormacionBean> getListaTipoFormacionDependiente() {
        if (listaTipoFormacionDependiente == null) {
            listaTipoFormacionDependiente = new ArrayList<>();
        }
        return listaTipoFormacionDependiente;
    }

    public void setListaTipoFormacionDependiente(List<TipoFormacionBean> listaTipoFormacionDependiente) {
        this.listaTipoFormacionDependiente = listaTipoFormacionDependiente;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoDependiente() {
        if (listaNivelAcademicoDependiente == null) {
            listaNivelAcademicoDependiente = new ArrayList<>();
        }
        return listaNivelAcademicoDependiente;
    }

    public void setListaNivelAcademicoDependiente(List<NivelAcademicoBean> listaNivelAcademicoDependiente) {
        this.listaNivelAcademicoDependiente = listaNivelAcademicoDependiente;
    }

    public List<CarreraAreaBean> getListaCarreraAreaDependiente() {
        if (listaCarreraAreaDependiente == null) {
            listaCarreraAreaDependiente = new ArrayList<>();
        }
        return listaCarreraAreaDependiente;
    }

    public void setListaCarreraAreaDependiente(List<CarreraAreaBean> listaCarreraAreaDependiente) {
        this.listaCarreraAreaDependiente = listaCarreraAreaDependiente;
    }

    public List<CarreraSubAreaBean> getListaCarreraSubAreaDependiente() {
        if (listaCarreraSubAreaDependiente == null) {
            listaCarreraSubAreaDependiente = new ArrayList<>();
        }
        return listaCarreraSubAreaDependiente;
    }

    public void setListaCarreraSubAreaDependiente(List<CarreraSubAreaBean> listaCarreraSubAreaDependiente) {
        this.listaCarreraSubAreaDependiente = listaCarreraSubAreaDependiente;
    }

    public CarreraBean getCarreraBeanDependiente() {
        if (carreraBeanDependiente == null) {
            carreraBeanDependiente = new CarreraBean();
        }
        return carreraBeanDependiente;
    }

    public void setCarreraBeanDependiente(CarreraBean carreraBeanDependiente) {
        this.carreraBeanDependiente = carreraBeanDependiente;
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

    public UniNegUniOrgBean getUniNegUniOrgBean() {
        if (uniNegUniOrgBean == null) {
            uniNegUniOrgBean = new UniNegUniOrgBean();
        }
        return uniNegUniOrgBean;
    }

    public void setUniNegUniOrgBean(UniNegUniOrgBean uniNegUniOrgBean) {
        this.uniNegUniOrgBean = uniNegUniOrgBean;
    }

    public List<CargoUniNegBean> getListaCargoUniNegBean() {
        if (listaCargoUniNegBean == null) {
            listaCargoUniNegBean = new ArrayList<>();
        }
        return listaCargoUniNegBean;
    }

    public void setListaCargoUniNegBean(List<CargoUniNegBean> listaCargoUniNegBean) {
        this.listaCargoUniNegBean = listaCargoUniNegBean;
    }

    public Map<String, Integer> getListaMesesExpMap() {
        return listaMesesExpMap;
    }

    public void setListaMesesExpMap(Map<String, Integer> listaMesesExpMap) {
        this.listaMesesExpMap = listaMesesExpMap;
    }

    public CargoBean getCargoBean() {
        if (cargoBean == null) {
            cargoBean = new CargoBean();
        }
        return cargoBean;
    }

    public void setCargoBean(CargoBean cargoBean) {
        this.cargoBean = cargoBean;
    }

    public PersonalDocumentoBean getPersonalDocumentoBean() {
        if (personalDocumentoBean == null) {
            personalDocumentoBean = new PersonalDocumentoBean();
        }
        return personalDocumentoBean;
    }

    public void setPersonalDocumentoBean(PersonalDocumentoBean personalDocumentoBean) {
        this.personalDocumentoBean = personalDocumentoBean;
    }

    public List<PersonalDocumentoBean> getListaPersonalDocumentoBean() {
        if (listaPersonalDocumentoBean == null) {
            listaPersonalDocumentoBean = new ArrayList<>();
        }
        return listaPersonalDocumentoBean;
    }

    public void setListaPersonalDocumentoBean(List<PersonalDocumentoBean> listaPersonalDocumentoBean) {
        this.listaPersonalDocumentoBean = listaPersonalDocumentoBean;
    }

    public List<DocumentoBean> getListaDocumentoBean() {
        if (listaDocumentoBean == null) {
            listaDocumentoBean = new ArrayList<>();
        }
        return listaDocumentoBean;
    }

    public void setListaDocumentoBean(List<DocumentoBean> listaDocumentoBean) {
        this.listaDocumentoBean = listaDocumentoBean;
    }

    public List<DocumentoCargoBean> getListaDocumentoCargoBean() {
        if (listaDocumentoCargoBean == null) {
            listaDocumentoCargoBean = new ArrayList<>();
        }
        return listaDocumentoCargoBean;
    }

    public void setListaDocumentoCargoBean(List<DocumentoCargoBean> listaDocumentoCargoBean) {
        this.listaDocumentoCargoBean = listaDocumentoCargoBean;
    }

    public DocumentoBean getDocumentoBean() {
        if (documentoBean == null) {
            documentoBean = new DocumentoBean();
        }
        return documentoBean;
    }

    public void setDocumentoBean(DocumentoBean documentoBean) {
        this.documentoBean = documentoBean;
    }

    public DocumentoCargoBean getDocumentoCargoBean() {
        if (documentoCargoBean == null) {
            documentoCargoBean = new DocumentoCargoBean();
        }
        return documentoCargoBean;
    }

    public void setDocumentoCargoBean(DocumentoCargoBean documentoCargoBean) {
        this.documentoCargoBean = documentoCargoBean;
    }

    public List<CargoBean> getListaCargoBean() {
        if (listaCargoBean == null) {
            listaCargoBean = new ArrayList<>();
        }
        return listaCargoBean;
    }

    public void setListaCargoBean(List<CargoBean> listaCargoBean) {
        this.listaCargoBean = listaCargoBean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrgBeanPersonal() {
        if (listaUniNegUniOrgBeanPersonal == null) {
            listaUniNegUniOrgBeanPersonal = new ArrayList<>();
        }
        return listaUniNegUniOrgBeanPersonal;
    }

    public void setListaUniNegUniOrgBeanPersonal(List<UniNegUniOrgBean> listaUniNegUniOrgBeanPersonal) {
        this.listaUniNegUniOrgBeanPersonal = listaUniNegUniOrgBeanPersonal;
    }

    public List<CodigoBean> getListaTipoSeguroPer() {
        if (listaTipoSeguroPer == null) {
            listaTipoSeguroPer = new ArrayList<>();
        }
        return listaTipoSeguroPer;
    }

    public void setListaTipoSeguroPer(List<CodigoBean> listaTipoSeguroPer) {
        this.listaTipoSeguroPer = listaTipoSeguroPer;
    }

    public List<PersonalBean> getListaPersonalCumpleaniosBean() {
        if (listaPersonalCumpleaniosBean == null) {
            listaPersonalCumpleaniosBean = new ArrayList<>();
        }
        return listaPersonalCumpleaniosBean;
    }

    public void setListaPersonalCumpleaniosBean(List<PersonalBean> listaPersonalCumpleaniosBean) {
        this.listaPersonalCumpleaniosBean = listaPersonalCumpleaniosBean;
    }

    public List<CentroResponsabilidadBean> getListaCR1() {
        if (listaCR1 == null) {
            listaCR1 = new ArrayList<>();
        }
        return listaCR1;
    }

    public void setListaCR1(List<CentroResponsabilidadBean> listaCR1) {
        this.listaCR1 = listaCR1;
    }

    public List<CentroResponsabilidadBean> getListaCR2() {
        if (listaCR2 == null) {
            listaCR2 = new ArrayList<>();
        }
        return listaCR2;
    }

    public void setListaCR2(List<CentroResponsabilidadBean> listaCR2) {
        this.listaCR2 = listaCR2;
    }

    public List<CentroResponsabilidadBean> getListaCR3() {
        if (listaCR3 == null) {
            listaCR3 = new ArrayList<>();
        }
        return listaCR3;
    }

    public void setListaCR3(List<CentroResponsabilidadBean> listaCR3) {
        this.listaCR3 = listaCR3;
    }

    public List<CentroResponsabilidadBean> getListaCR4() {
        if (listaCR4 == null) {
            listaCR4 = new ArrayList<>();
        }
        return listaCR4;
    }

    public void setListaCR4(List<CentroResponsabilidadBean> listaCR4) {
        this.listaCR4 = listaCR4;
    }

    public List<CentroResponsabilidadBean> getListaCR5() {
        if (listaCR5 == null) {
            listaCR5 = new ArrayList<>();
        }
        return listaCR5;
    }

    public void setListaCR5(List<CentroResponsabilidadBean> listaCR5) {
        this.listaCR5 = listaCR5;
    }

    public Boolean getFlgPaisPeru() {
        return flgPaisPeru;
    }

    public void setFlgPaisPeru(Boolean flgPaisPeru) {
        this.flgPaisPeru = flgPaisPeru;
    }

    public Boolean getFlgFiltroPersonal() {
        return flgFiltroPersonal;
    }

    public void setFlgFiltroPersonal(Boolean flgFiltroPersonal) {
        this.flgFiltroPersonal = flgFiltroPersonal;
    }

    public Boolean getFlgFiltroPersona() {
        return flgFiltroPersona;
    }

    public void setFlgFiltroPersona(Boolean flgFiltroPersona) {
        this.flgFiltroPersona = flgFiltroPersona;
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

    public List<PersonalBean> getListaPersonalFiltroBean() {
        if (listaPersonalFiltroBean == null) {
            listaPersonalFiltroBean = new ArrayList<>();
        }
        return listaPersonalFiltroBean;
    }

    public void setListaPersonalFiltroBean(List<PersonalBean> listaPersonalFiltroBean) {
        this.listaPersonalFiltroBean = listaPersonalFiltroBean;
    }

    public PersonalBean getPersonalFiltroDepBean() {
        if (personalFiltroDepBean == null) {
            personalFiltroDepBean = new PersonalBean();
        }
        return personalFiltroDepBean;
    }

    public void setPersonalFiltroDepBean(PersonalBean personalFiltroDepBean) {
        this.personalFiltroDepBean = personalFiltroDepBean;
    }

    public PersonalRepBean getPersonalRepFiltroBean() {
        if (personalRepFiltroBean == null) {
            personalRepFiltroBean = new PersonalRepBean();
        }
        return personalRepFiltroBean;
    }

    public void setPersonalRepFiltroBean(PersonalRepBean personalRepFiltroBean) {
        this.personalRepFiltroBean = personalRepFiltroBean;
    }

    public List<PersonalRepBean> getListaPersonaRepFiltroBean() {
        if (listaPersonaRepFiltroBean == null) {
            listaPersonaRepFiltroBean = new ArrayList();
        }
        return listaPersonaRepFiltroBean;
    }

    public void setListaPersonaRepFiltroBean(List<PersonalRepBean> listaPersonaRepFiltroBean) {
        this.listaPersonaRepFiltroBean = listaPersonaRepFiltroBean;
    }

    public Boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }

    public PersonalContratoBean getPersonalVacacionesBean() {
        if (personalVacacionesBean == null) {
            personalVacacionesBean = new PersonalContratoBean();
        }
        return personalVacacionesBean;
    }

    public void setPersonalVacacionesBean(PersonalContratoBean personalVacacionesBean) {
        this.personalVacacionesBean = personalVacacionesBean;
    }

    public List<PersonalVacacionesBean> getListaPersonalVacacionesBean() {
        if (listaPersonalVacacionesBean == null) {
            listaPersonalVacacionesBean = new ArrayList<>();
        }
        return listaPersonalVacacionesBean;
    }

    public void setListaPersonalVacacionesBean(List<PersonalVacacionesBean> listaPersonalVacacionesBean) {
        this.listaPersonalVacacionesBean = listaPersonalVacacionesBean;
    }

    public Integer getSumaPorcentaje() {
        return sumaPorcentaje;
    }

    public void setSumaPorcentaje(Integer sumaPorcentaje) {
        this.sumaPorcentaje = sumaPorcentaje;
    }

    public Boolean getFlgGrabar() {
        return flgGrabar;
    }

    public void setFlgGrabar(Boolean flgGrabar) {
        this.flgGrabar = flgGrabar;
    }

    public Boolean getFlgGrabarStatus() {
        return flgGrabarStatus;
    }

    public void setFlgGrabarStatus(Boolean flgGrabarStatus) {
        this.flgGrabarStatus = flgGrabarStatus;
    }

    public List<CodigoBean> getListaNivelIns() {
        if (listaNivelIns == null) {
            listaNivelIns = new ArrayList<>();
        }
        return listaNivelIns;
    }

    public void setListaNivelIns(List<CodigoBean> listaNivelIns) {
        this.listaNivelIns = listaNivelIns;
    }

    public Integer[] getSelectedIdTipoAcceso() {
        return selectedIdTipoAcceso;
    }

    public void setSelectedIdTipoAcceso(Integer[] selectedIdTipoAcceso) {
        this.selectedIdTipoAcceso = selectedIdTipoAcceso;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
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

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Map<String, Integer> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(Map<String, Integer> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getFlgTodosLista() {
        return flgTodosLista;
    }

    public void setFlgTodosLista(Boolean flgTodosLista) {
        this.flgTodosLista = flgTodosLista;
    }

    public List<UsuarioBean> getListaUsuarioBean() {
        if (listaUsuarioBean == null) {
            listaUsuarioBean = new ArrayList<>();
        }
        return listaUsuarioBean;
    }

    public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
        this.listaUsuarioBean = listaUsuarioBean;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public List<CodigoBean> getListaTipoVia() {
        if (listaTipoVia == null) {
            listaTipoVia = new ArrayList<>();
        }
        return listaTipoVia;
    }

    public void setListaTipoVia(List<CodigoBean> listaTipoVia) {
        this.listaTipoVia = listaTipoVia;
    }

    public List<CodigoBean> getListaTipoZona() {
        if (listaTipoZona == null) {
            listaTipoZona = new ArrayList<>();
        }
        return listaTipoZona;
    }

    public void setListaTipoZona(List<CodigoBean> listaTipoZona) {
        this.listaTipoZona = listaTipoZona;
    }

    public List<PaisBean> getListaPaisEmisionDoc() {
        if (listaPaisEmisionDoc == null) {
            listaPaisEmisionDoc = new ArrayList<>();
        }
        return listaPaisEmisionDoc;
    }

    public void setListaPaisEmisionDoc(List<PaisBean> listaPaisEmisionDoc) {
        this.listaPaisEmisionDoc = listaPaisEmisionDoc;
    }

    public List<CodigoBean> getListaTipoPensionPer() {
        if (listaTipoPensionPer == null) {
            listaTipoPensionPer = new ArrayList<>();
        }
        return listaTipoPensionPer;
    }

    public void setListaTipoPensionPer(List<CodigoBean> listaTipoPensionPer) {
        this.listaTipoPensionPer = listaTipoPensionPer;
    }

    public List<ViewEntidadBean> getListaEntidadEPSNew() {
        if (listaEntidadEPSNew == null) {
            listaEntidadEPSNew = new ArrayList<>();
        }
        return listaEntidadEPSNew;
    }

    public void setListaEntidadEPSNew(List<ViewEntidadBean> listaEntidadEPSNew) {
        this.listaEntidadEPSNew = listaEntidadEPSNew;
    }

    public List<ViewEntidadBean> getListaEntidadPensionNew() {
        if (listaEntidadPensionNew == null) {
            listaEntidadPensionNew = new ArrayList<>();
        }
        return listaEntidadPensionNew;
    }

    public void setListaEntidadPensionNew(List<ViewEntidadBean> listaEntidadPensionNew) {
        this.listaEntidadPensionNew = listaEntidadPensionNew;
    }

    public List<CodigoBean> getListaTipoFormacion() {
        if (listaTipoFormacion == null) {
            listaTipoFormacion = new ArrayList<>();
        }
        return listaTipoFormacion;
    }

    public void setListaTipoFormacion(List<CodigoBean> listaTipoFormacion) {
        this.listaTipoFormacion = listaTipoFormacion;
    }

    public PersonalFormacionCarismaBean getPersonalFormacionCarismaBean() {
        if (personalFormacionCarismaBean == null) {
            personalFormacionCarismaBean = new PersonalFormacionCarismaBean();
        }
        return personalFormacionCarismaBean;
    }

    public void setPersonalFormacionCarismaBean(PersonalFormacionCarismaBean personalFormacionCarismaBean) {
        this.personalFormacionCarismaBean = personalFormacionCarismaBean;
    }

    public List<PersonalFormacionCarismaBean> getListPersonalForCarismaBean() {
        if (listPersonalForCarismaBean == null) {
            listPersonalForCarismaBean = new ArrayList<>();
        }
        return listPersonalForCarismaBean;
    }

    public void setListPersonalForCarismaBean(List<PersonalFormacionCarismaBean> listPersonalForCarismaBean) {
        this.listPersonalForCarismaBean = listPersonalForCarismaBean;
    }

    public String getMenorEdad() {
        return menorEdad;
    }

    public void setMenorEdad(String menorEdad) {
        this.menorEdad = menorEdad;
    }

    public Boolean getFlgMedicamentos() {
        return flgMedicamentos;
    }

    public void setFlgMedicamentos(Boolean flgMedicamentos) {
        this.flgMedicamentos = flgMedicamentos;
    }

    public PersonalDescansoMedicoBean getPersonalDescansoMedicoBean() {
        if (personalDescansoMedicoBean == null) {
            personalDescansoMedicoBean = new PersonalDescansoMedicoBean();
        }
        return personalDescansoMedicoBean;
    }

    public void setPersonalDescansoMedicoBean(PersonalDescansoMedicoBean personalDescansoMedicoBean) {
        this.personalDescansoMedicoBean = personalDescansoMedicoBean;
    }

    public List<PersonalDescansoMedicoBean> getListaPersonalDescansoMedico() {
        if (listaPersonalDescansoMedico == null) {
            listaPersonalDescansoMedico = new ArrayList<>();
        }
        return listaPersonalDescansoMedico;
    }

    public void setListaPersonalDescansoMedico(List<PersonalDescansoMedicoBean> listaPersonalDescansoMedico) {
        this.listaPersonalDescansoMedico = listaPersonalDescansoMedico;
    }

    public List<PersonalEvaPsicologicaBean> getListaPersonalEvaPsicologicaBean() {
        if (listaPersonalEvaPsicologicaBean == null) {
            listaPersonalEvaPsicologicaBean = new ArrayList<>();
        }
        return listaPersonalEvaPsicologicaBean;
    }

    public void setListaPersonalEvaPsicologicaBean(List<PersonalEvaPsicologicaBean> listaPersonalEvaPsicologicaBean) {
        this.listaPersonalEvaPsicologicaBean = listaPersonalEvaPsicologicaBean;
    }

    public PersonalEvaPsicologicaBean getPersonalEvaPsicologicaBean() {
        if (personalEvaPsicologicaBean == null) {
            personalEvaPsicologicaBean = new PersonalEvaPsicologicaBean();
        }
        return personalEvaPsicologicaBean;
    }

    public void setPersonalEvaPsicologicaBean(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) {
        this.personalEvaPsicologicaBean = personalEvaPsicologicaBean;
    }

    public Boolean getFlgMartes() {
        return flgMartes;
    }

    public void setFlgMartes(Boolean flgMartes) {
        this.flgMartes = flgMartes;
    }

    public Boolean getFlgMiercoles() {
        return flgMiercoles;
    }

    public void setFlgMiercoles(Boolean flgMiercoles) {
        this.flgMiercoles = flgMiercoles;
    }

    public Boolean getFlgJueves() {
        return flgJueves;
    }

    public void setFlgJueves(Boolean flgJueves) {
        this.flgJueves = flgJueves;
    }

    public Boolean getFlgViernes() {
        return flgViernes;
    }

    public void setFlgViernes(Boolean flgViernes) {
        this.flgViernes = flgViernes;
    }

    public Boolean getFlgSabado() {
        return flgSabado;
    }

    public void setFlgSabado(Boolean flgSabado) {
        this.flgSabado = flgSabado;
    }

    public Boolean getFlgLunes() {
        return flgLunes;
    }

    public void setFlgLunes(Boolean flgLunes) {
        this.flgLunes = flgLunes;
    }

    public Long getDiasVista() {
        return diasVista;
    }

    public void setDiasVista(Long diasVista) {
        this.diasVista = diasVista;
    }

    public List<PersonalInformacionBancariaBean> getListaInformacionBancariaBean() {
        if (listaInformacionBancariaBean == null) {
            listaInformacionBancariaBean = new ArrayList<>();
        }
        return listaInformacionBancariaBean;
    }

    public void setListaInformacionBancariaBean(List<PersonalInformacionBancariaBean> listaInformacionBancariaBean) {
        this.listaInformacionBancariaBean = listaInformacionBancariaBean;
    }

    public PersonalInformacionBancariaBean getPersonalInformacionBancariaBean() {
        if (personalInformacionBancariaBean == null) {
            personalInformacionBancariaBean = new PersonalInformacionBancariaBean();
        }
        return personalInformacionBancariaBean;
    }

    public void setPersonalInformacionBancariaBean(PersonalInformacionBancariaBean personalInformacionBancariaBean) {
        this.personalInformacionBancariaBean = personalInformacionBancariaBean;
    }

    public PersonalDesvinculacionBean getPersonalDesvinculacionBean() {
        if (personalDesvinculacionBean == null) {
            personalDesvinculacionBean = new PersonalDesvinculacionBean();
        }
        return personalDesvinculacionBean;
    }

    public void setPersonalDesvinculacionBean(PersonalDesvinculacionBean personalDesvinculacionBean) {
        this.personalDesvinculacionBean = personalDesvinculacionBean;
    }

    public List<PersonalDesvinculacionBean> getListaPersonalDesvinculacionBean() {
        if (listaPersonalDesvinculacionBean == null) {
            listaPersonalDesvinculacionBean = new ArrayList<>();
        }
        return listaPersonalDesvinculacionBean;
    }

    public void setListaPersonalDesvinculacionBean(List<PersonalDesvinculacionBean> listaPersonalDesvinculacionBean) {
        this.listaPersonalDesvinculacionBean = listaPersonalDesvinculacionBean;
    }

    public Boolean getFlgTutor() {
        return flgTutor;
    }

    public void setFlgTutor(Boolean flgTutor) {
        this.flgTutor = flgTutor;
    }

    public Boolean getFlgDocente() {
        return flgDocente;
    }

    public void setFlgDocente(Boolean flgDocente) {
        this.flgDocente = flgDocente;
    }

    public String getHorasCalculadasLunes() {
        return horasCalculadasLunes;
    }

    public void setHorasCalculadasLunes(String horasCalculadasLunes) {
        this.horasCalculadasLunes = horasCalculadasLunes;
    }

    public String getHorasCalculadasMartes() {
        return horasCalculadasMartes;
    }

    public void setHorasCalculadasMartes(String horasCalculadasMartes) {
        this.horasCalculadasMartes = horasCalculadasMartes;
    }

    public String getHorasCalculadasMiercoles() {
        return horasCalculadasMiercoles;
    }

    public void setHorasCalculadasMiercoles(String horasCalculadasMiercoles) {
        this.horasCalculadasMiercoles = horasCalculadasMiercoles;
    }

    public String getHorasCalculadasJueves() {
        return horasCalculadasJueves;
    }

    public void setHorasCalculadasJueves(String horasCalculadasJueves) {
        this.horasCalculadasJueves = horasCalculadasJueves;
    }

    public String getHorasCalculadasViernes() {
        return horasCalculadasViernes;
    }

    public void setHorasCalculadasViernes(String horasCalculadasViernes) {
        this.horasCalculadasViernes = horasCalculadasViernes;
    }

    public String getHorasCalculadasSabado() {
        return horasCalculadasSabado;
    }

    public void setHorasCalculadasSabado(String horasCalculadasSabado) {
        this.horasCalculadasSabado = horasCalculadasSabado;
    }

    public String getTotalHorasGlobal() {
        return totalHorasGlobal;
    }

    public void setTotalHorasGlobal(String totalHorasGlobal) {
        this.totalHorasGlobal = totalHorasGlobal;
    }

    public Boolean getFlgHabilitarVacaciones() {
        return flgHabilitarVacaciones;
    }

    public void setFlgHabilitarVacaciones(Boolean flgHabilitarVacaciones) {
        this.flgHabilitarVacaciones = flgHabilitarVacaciones;
    }

    public String getTrabajoMenor() {
        return trabajoMenor;
    }

    public void setTrabajoMenor(String trabajoMenor) {
        this.trabajoMenor = trabajoMenor;
    }

    public List<PersonalPDFBean> getListaPersonalPDFBean() {
        if (listaPersonalPDFBean == null) {
            listaPersonalPDFBean = new ArrayList<>();
        }
        return listaPersonalPDFBean;
    }

    public void setListaPersonalPDFBean(List<PersonalPDFBean> listaPersonalPDFBean) {
        this.listaPersonalPDFBean = listaPersonalPDFBean;
    }

    public List<CodigoBean> getListaTipoObjetoSubirPDF() {
        if (listaTipoObjetoSubirPDF == null) {
            listaTipoObjetoSubirPDF = new ArrayList<>();
        }
        return listaTipoObjetoSubirPDF;
    }

    public void setListaTipoObjetoSubirPDF(List<CodigoBean> listaTipoObjetoSubirPDF) {
        this.listaTipoObjetoSubirPDF = listaTipoObjetoSubirPDF;
    }

    public PersonalPDFBean getPersonalPDFBean() {
        if (personalPDFBean == null) {
            personalPDFBean = new PersonalPDFBean();
        }
        return personalPDFBean;
    }

    public void setPersonalPDFBean(PersonalPDFBean personalPDFBean) {
        this.personalPDFBean = personalPDFBean;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Boolean getFlgFalse() {
        return flgFalse;
    }

    public void setFlgFalse(Boolean flgFalse) {
        this.flgFalse = flgFalse;
    }

    public List<PersonalVoluntariadoBean> getListaPersonalVoluntariadoBean() {
        if (listaPersonalVoluntariadoBean == null) {
            listaPersonalVoluntariadoBean = new ArrayList<>();
        }
        return listaPersonalVoluntariadoBean;
    }

    public void setListaPersonalVoluntariadoBean(List<PersonalVoluntariadoBean> listaPersonalVoluntariadoBean) {
        this.listaPersonalVoluntariadoBean = listaPersonalVoluntariadoBean;
    }

    public PersonalVoluntariadoBean getPersonalVoluntariadoBean() {
        if (personalVoluntariadoBean == null) {
            personalVoluntariadoBean = new PersonalVoluntariadoBean();
        }
        return personalVoluntariadoBean;
    }

    public void setPersonalVoluntariadoBean(PersonalVoluntariadoBean personalVoluntariadoBean) {
        this.personalVoluntariadoBean = personalVoluntariadoBean;
    }

    public List<PersonalDatosHistoricoBean> getListaPersonalDatosHistoricoBean() {
        if (listaPersonalDatosHistoricoBean == null) {
            listaPersonalDatosHistoricoBean = new ArrayList<>();
        }
        return listaPersonalDatosHistoricoBean;
    }

    public void setListaPersonalDatosHistoricoBean(List<PersonalDatosHistoricoBean> listaPersonalDatosHistoricoBean) {
        this.listaPersonalDatosHistoricoBean = listaPersonalDatosHistoricoBean;
    }

    public PersonalDatosHistoricoBean getPersonalDatosHistoricoBean() {
        if (personalDatosHistoricoBean == null) {
            personalDatosHistoricoBean = new PersonalDatosHistoricoBean();
        }
        return personalDatosHistoricoBean;
    }

    public void setPersonalDatosHistoricoBean(PersonalDatosHistoricoBean personalDatosHistoricoBean) {
        this.personalDatosHistoricoBean = personalDatosHistoricoBean;
    }

    public PersonalDependienteBean getPersonalOtrosDependienteBean() {
        if (personalOtrosDependienteBean == null) {
            personalOtrosDependienteBean = new PersonalDependienteBean();
        }
        return personalOtrosDependienteBean;
    }

    public void setPersonalOtrosDependienteBean(PersonalDependienteBean personalOtrosDependienteBean) {
        this.personalOtrosDependienteBean = personalOtrosDependienteBean;
    }

    public List<CodigoBean> getListaOtrosParentesco() {
        if (listaOtrosParentesco == null) {
            listaOtrosParentesco = new ArrayList<>();
        }
        return listaOtrosParentesco;
    }

    public void setListaOtrosParentesco(List<CodigoBean> listaOtrosParentesco) {
        this.listaOtrosParentesco = listaOtrosParentesco;
    }

    public List<PersonalDescansoMedicoBean> getListaPersonalInasistencia() {
        if (listaPersonalInasistencia == null) {
            listaPersonalInasistencia = new ArrayList<>();
        }
        return listaPersonalInasistencia;
    }

    public void setListaPersonalInasistencia(List<PersonalDescansoMedicoBean> listaPersonalInasistencia) {
        this.listaPersonalInasistencia = listaPersonalInasistencia;
    }

    public List<PersonalDescansoMedicoBean> getListaPersonalAccidenteLaboral() {
        if (listaPersonalAccidenteLaboral == null) {
            listaPersonalAccidenteLaboral = new ArrayList<>();
        }
        return listaPersonalAccidenteLaboral;
    }

    public void setListaPersonalAccidenteLaboral(List<PersonalDescansoMedicoBean> listaPersonalAccidenteLaboral) {
        this.listaPersonalAccidenteLaboral = listaPersonalAccidenteLaboral;
    }

    public Boolean getFlgTrabajoAltoRiesgo() {
        return flgTrabajoAltoRiesgo;
    }

    public void setFlgTrabajoAltoRiesgo(Boolean flgTrabajoAltoRiesgo) {
        this.flgTrabajoAltoRiesgo = flgTrabajoAltoRiesgo;
    }

    public Boolean getFlgOcultarGradoAcademico() {
        return flgOcultarGradoAcademico;
    }

    public void setFlgOcultarGradoAcademico(Boolean flgOcultarGradoAcademico) {
        this.flgOcultarGradoAcademico = flgOcultarGradoAcademico;
    }

    public Boolean getFlgEnProceso() {
        return flgEnProceso;
    }

    public void setFlgEnProceso(Boolean flgEnProceso) {
        this.flgEnProceso = flgEnProceso;
    }

    public Boolean getFlgSoloCulminado() {
        return flgSoloCulminado;
    }

    public void setFlgSoloCulminado(Boolean flgSoloCulminado) {
        this.flgSoloCulminado = flgSoloCulminado;
    }

    public List<CodigoBean> getListaTipoEstudios() {
        if (listaTipoEstudios == null) {
            listaTipoEstudios = new ArrayList<>();
        }
        return listaTipoEstudios;
    }

    public void setListaTipoEstudios(List<CodigoBean> listaTipoEstudios) {
        this.listaTipoEstudios = listaTipoEstudios;
    }

    public List<CodigoBean> getListaTipoModalidadEst() {
        if (listaTipoModalidadEst == null) {
            listaTipoModalidadEst = new ArrayList<>();
        }
        return listaTipoModalidadEst;
    }

    public void setListaTipoModalidadEst(List<CodigoBean> listaTipoModalidadEst) {
        this.listaTipoModalidadEst = listaTipoModalidadEst;
    }

    public List<CodigoBean> getListaTipoOtrosEstudios() {
        if (listaTipoOtrosEstudios == null) {
            listaTipoOtrosEstudios = new ArrayList<>();
        }

        return listaTipoOtrosEstudios;
    }

    public void setListaTipoOtrosEstudios(List<CodigoBean> listaTipoOtrosEstudios) {
        this.listaTipoOtrosEstudios = listaTipoOtrosEstudios;
    }

    public List<CodigoBean> getListaTipoFinanciamiento() {
        if (listaTipoFinanciamiento == null) {
            listaTipoFinanciamiento = new ArrayList<>();
        }

        return listaTipoFinanciamiento;
    }

    public void setListaTipoFinanciamiento(List<CodigoBean> listaTipoFinanciamiento) {
        this.listaTipoFinanciamiento = listaTipoFinanciamiento;
    }

    public Boolean getFlgFinanciamiento() {
        return flgFinanciamiento;
    }

    public void setFlgFinanciamiento(Boolean flgFinanciamiento) {
        this.flgFinanciamiento = flgFinanciamiento;
    }

    public Boolean getFlgCertificado() {
        return flgCertificado;
    }

    public void setFlgCertificado(Boolean flgCertificado) {
        this.flgCertificado = flgCertificado;
    }

    public List<CodigoBean> getListaTipoPlanillaColegio() {
        if (listaTipoPlanillaColegio == null) {
            listaTipoPlanillaColegio = new ArrayList<>();
        }
        return listaTipoPlanillaColegio;
    }

    public void setListaTipoPlanillaColegio(List<CodigoBean> listaTipoPlanillaColegio) {
        this.listaTipoPlanillaColegio = listaTipoPlanillaColegio;
    }

    public List<CodigoBean> getListaTipoGrupoOcupacional() {
        if (listaTipoGrupoOcupacional == null) {
            listaTipoGrupoOcupacional = new ArrayList<>();
        }
        return listaTipoGrupoOcupacional;
    }

    public void setListaTipoGrupoOcupacional(List<CodigoBean> listaTipoGrupoOcupacional) {
        this.listaTipoGrupoOcupacional = listaTipoGrupoOcupacional;
    }

}
