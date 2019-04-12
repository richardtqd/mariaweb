package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.bean.EventoTipoPaganteBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.EventoTipoPaganteService;
import pe.marista.sigma.service.FichaService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PaganteService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class ImpresionMasivaValoradosMB extends BaseMB implements Serializable {

    @PostConstruct
    public void ImpresionMasivaValoradosMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaAnioFiltroMatricula();
            for (int i = Calendar.getInstance().get(Calendar.YEAR) - 2; i <= Calendar.getInstance().get(Calendar.YEAR) + 2; i++) {
                listaAnioFiltroMatricula.add(i);
            }
            setFlgTodos(true);
            getMatriculaFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            UnidadOrganicaService unidadOrganciOrganicaService = BeanFactory.getUnidadOrganicaService();
            getListaUnidadOrganicaBean();
            listaUnidadOrganicaBean = unidadOrganciOrganicaService.obtenerTodos();

            EventoService eventoService = BeanFactory.getEventoService();
            getListaEventoBean();
            getEventoBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaEventoBean = eventoService.obtener(eventoBean);
            getListaAfterEventoBean();
            listaAfterEventoBean = eventoService.obtener(eventoBean);

            //PERSONAL
            getPersonalFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //PERSONA-EXTERNA
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //ENTIDAD
            getEntidadFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //EVENTOTIPOPAGANTE
            getEventoTipoPaganteBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //FILTRO DE PAGANTES
            setTipPagante(1);
            cambiarFiltro();

            //PAGANTE
            getPaganteBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPaganteFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //TIPO DE ASIGNACION
            setTipAsignacion(1);

            //LISTA DE TIPOS DE ESTADO DE FICHAS
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEstadoFicha();
            listaTipoEstadoFicha = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_FICHA));
            getListaTipoEstadoFiltroFicha();
            listaTipoEstadoFiltroFicha = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_FICHA));

            //FORMA DE ASIGNACION
            setSelTipAsigEsp(1);
            obtenerTipoAsigForma();

            //TIPO DE ASIGNACION FICHA
            setTipAsignacionFicha(0);

            //FICHA FILTRO
            getFichaFiltroBean();
            getFichaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaFiltroBean().getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CLASES
    private UsuarioBean usuarioLoginBean;
    private MatriculaBean matriculaFiltroBean;
    private PersonalBean personalFiltroBean;
    private EntidadBean entidadFiltroBean;
    private PersonaBean personaFiltroBean;
    private PaganteBean paganteBean;
    private PaganteBean paganteView;
    private EventoBean eventoBean;
    private EventoTipoPaganteBean eventoTipoPaganteBean;
    private PaganteBean paganteFiltroBean;
    private FichaBean fichaFiltroBean;
    private FichaBean fichaVistaBean;

    //LISTAS
    private List<MatriculaBean> listaMatriculaEstudianteMasivoBean;
    private List<PersonalBean> listaPersonalFiltroBean;
    private List<EntidadBean> listaEntidadFiltroBean;
    private List<PersonaBean> listaPersonaFiltroBean;
    private List<Integer> listaAnioFiltroMatricula;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private List<PaganteBean> listaPaganteBean;
    private List<EventoBean> listaEventoBean;
    private List<EventoTipoPaganteBean> listaEventoTipoPaganteBean;
    private List<PaganteBean> listaPagantefiltroBean;
    private List<EventoBean> listaAfterEventoBean;
    private List<EventoTipoPaganteBean> listaAfterEventoTipoPaganteBean;
    private List<FichaBean> listaFichaAfterBean;
    private List<CodigoBean> listaTipoEstadoFicha;
    private List<CodigoBean> listaTipoEstadoFiltroFicha;

    //AYUDA
    //FLG ESTUDIANTES
    private Boolean flgTodos;
    private Boolean flgEstComprobanteMes;
    private Boolean flgEstEsp;
    private Boolean flgPorNivelGrado;

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

    //RENDER
    private Boolean renderEstudiante;
    private Boolean renderPersonal;
    private Boolean renderPersona;
    private Boolean renderEntidad;
    private Integer tipPagante = 0;
    private Integer tipAsignacion = 0;
    private Integer tipPaganteAfter = 0;

    //NUMEROS ALTER FICHAS
    private Integer nroIni = 0;
    private Integer nroFin = 0;
    private Integer nroAsig = 0;
    private Boolean flgRenderEsp;
    private Boolean valConTodos;
    private Integer selTipAsigEsp = null;
    private Integer tipAsignacionFicha;
    private Integer tipAsignacionFichaPop;

    //PRUEBA ROWSELECT
    private MatriculaBean matriculaSelectBean;

    //VARIABLES DE AYUDA
    private Integer valDonado = 0;
    private Integer numeroFicha;

    //METODOS DE CLASE
    public void cambiarFiltro() {
        try {
            if (tipPagante.equals(1)) {
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
            } else if (tipPagante.equals(3)) {
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
            } else if (tipPagante.equals(4)) {
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
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatos() {
        try {
            //ESTUDIANTE
            getMatriculaFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //PERSONAL
            getPersonalFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //EXTERNO
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //ENTIDAD
            getEntidadFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //ESTUDIANTE
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
            flgPorNivelGrado = false;
            flgTodos = false;
            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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
            getPersonalFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
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
            getPersonaFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
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
            getEntidadFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteImpCompMasivo(matriculaFiltroBean);
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

    public void obtenerPersonalFiltroMasivo() {
        try {
            Integer res = 0;
            PersonalService personalService = BeanFactory.getPersonalService();
            if (flgTodosPer) {
                listaPersonalFiltroBean = personalService.obtenerPorUnidadNegocio(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerAsignacion() {
        try {
            if (tipAsignacion.equals(1)) {
                if (tipPagante.equals(1)) {
                    asignarEstudianteMasivo();
                } else if (tipPagante.equals(2)) {
                    asignarPersonalMasivo();
                } else if (tipPagante.equals(3)) {
                    asignarExternoMasivo();
                } else if (tipPagante.equals(4)) {
                    asignarEntidadMasivo();
                } else {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            } else if (tipAsignacion.equals(2)) {
                if (selTipAsigEsp.equals(2)) {
                    obtenerNroAsigna();
                    if (numeroFicha == null) {
                        if (tipPagante.equals(1)) {
                            asignarEstudianteMasivoEspecial();
                        } else if (tipPagante.equals(2)) {
                            asignarPersonalMasivoEspecial();
                        } else if (tipPagante.equals(3)) {
                            asignarExternoMasivoEspecial();
                        } else if (tipPagante.equals(4)) {
                            asignarEntidadMasivoEspecial();
                        } else {
                            new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        }
                    } else if (numeroFicha != null) {
                        new MensajePrime().addMessageNumber();
                    }
                } else if (selTipAsigEsp.equals(1)) {
                    if (tipPagante.equals(1)) {
                        asignarEstudianteMasivoEspecial();
                    } else if (tipPagante.equals(2)) {
                        asignarPersonalMasivoEspecial();
                    } else if (tipPagante.equals(3)) {
                        asignarExternoMasivoEspecial();
                    } else if (tipPagante.equals(4)) {
                        asignarEntidadMasivoEspecial();
                    } else {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarEstudianteMasivo() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                for (MatriculaBean estudiante : listaMatriculaEstudianteMasivoBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(estudiante.getIdMatricula().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagEst(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(estudiante.getIdMatricula().toString());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(estudiante.getEstudianteBean().getPersonaBean().getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(estudiante.getIdMatricula().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarPersonalMasivo() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaPersonalFiltroBean.isEmpty()) {
                for (PersonalBean personal : listaPersonalFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(personal.getIdPersonal().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagPer(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(personal.getIdPersonal().toString());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(personal.getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES

                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }

                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarExternoMasivo() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaPersonaFiltroBean.isEmpty()) {
                for (PersonaBean externo : listaPersonaFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(externo.getIdPersona());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagExt(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(externo.getIdPersona());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(externo.getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES

                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarEntidadMasivo() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaEntidadFiltroBean.isEmpty()) {
                for (EntidadBean entidad : listaEntidadFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(entidad.getRuc());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagEnt(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(entidad.getRuc());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(entidad.getNombre());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES

                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsignaciones != 0) {
                            for (int i = 0; i < nroAsignaciones; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                Integer numSerie = obtenerMaxNroDoc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.serie_numdoc, getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                if (numSerie.equals(0)) {
                                    ficha.setNroficha(nroIni);
                                } else if (!numSerie.equals(0)) {
                                    ficha.setNroficha(numSerie + 1);
                                }
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //VALIDANDO NUMERO DE ASIGNACION
    public Integer validarNumeroFicha() {
        Integer num = 0;
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroficha(nroIni);
            num = fichaService.obtenerNroFicha(ficha);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return num;
    }

    //ASIGNACION ESPECIAL
    public void asignarEstudianteMasivoEspecial() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                for (MatriculaBean estudiante : listaMatriculaEstudianteMasivoBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(estudiante.getIdMatricula().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagEst(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(estudiante.getIdMatricula().toString());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(estudiante.getEstudianteBean().getPersonaBean().getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(estudiante.getIdMatricula().toString());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(estudiante.getIdMatricula().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarPersonalMasivoEspecial() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaPersonalFiltroBean.isEmpty()) {
                for (PersonalBean personal : listaPersonalFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(personal.getIdPersonal().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagPer(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(personal.getIdPersonal().toString());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(personal.getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);
                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(personal.getIdPersonal().toString());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(personal.getIdPersonal().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES

                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarExternoMasivoEspecial() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaPersonaFiltroBean.isEmpty()) {
                for (PersonaBean externo : listaPersonaFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(externo.getIdPersona());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagExt(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(externo.getIdPersona());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(externo.getNombreCompleto());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(externo.getIdPersona().toString());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(externo.getIdPersona().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        if (getSelTipAsigEsp().equals(2)) {
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarEntidadMasivoEspecial() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            PaganteService paganteService = BeanFactory.getPaganteService();
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (!listaEntidadFiltroBean.isEmpty()) {
                for (EntidadBean entidad : listaEntidadFiltroBean) {
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(entidad.getRuc());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante = paganteService.obtenerPorIdPagEnt(pagante);
                    if (pagante == null) {
                        System.out.println(">>>>> No existe");
                        //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                        pagante = new PaganteBean();
                        //INSERTANDO PAGANTE
                        pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        pagante.setIdPagante(entidad.getRuc());
                        pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        pagante.setNomPagante(entidad.getNombre());
                        pagante.setCreaPor(usuarioLoginBean.getUsuario());
                        paganteService.insertar(pagante);

                        //OBTENIENDO NUMERO DE ASIGNACIONES
                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        if (getSelTipAsigEsp().equals(2)) {
//                            Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(entidad.getRuc());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(1)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(entidad.getRuc());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    } else if (pagante != null) {
                        System.out.println(">>>>> existe");
                        //SI EXISTE PAGANTE INSERTA FICHAS
                        //OBTENIENDO NUMERO DE ASIGNACIONES

                        EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                        eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                        eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                        Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                        if (getSelTipAsigEsp().equals(2)) {
//                            Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                            if (nroAsig != 0) {
                                for (int i = 0; i < nroAsig; i++) {
                                    FichaBean ficha = new FichaBean();
                                    //INSERTANDO FICHA
                                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    ficha.setSerie(MaristaConstantes.serie_numdoc);
                                    ficha.setNroficha(nroIni);
                                    ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    ficha.setMonto(conceptoUniNegBean.getImporte());
                                    ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    if (tipAsignacionFicha.equals(1)) { // DONACION
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                        ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                    ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(ficha);
                                    nroIni++;
                                }
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            }
                        } else if (getSelTipAsigEsp().equals(2)) {
                            Integer numero = validarNumeroFicha();
                            if (numero == null) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //ASIGNACION POR OBJETO
    public void asignarPorObj(Object object, Integer dato) {
        try {
            if (dato.equals(1)) {
                MatriculaBean matricula = (MatriculaBean) object;
                asignacionPorObjEstudiante(matricula);
            } else if (dato.equals(2)) {
                PersonalBean personal = (PersonalBean) object;
                asignacionPorObjPersonal(personal);
            } else if (dato.equals(3)) {
                PersonaBean externo = (PersonaBean) object;
                asignacionPorObjExterno(externo);
            } else if (dato.equals(4)) {
                EntidadBean entidad = (EntidadBean) object;
                asignacionPorObjEntidad(entidad);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignacionPorObjEstudiante(MatriculaBean matriculaBean) {
        try {
            if (matriculaBean != null) {
                /* OBTENIENDO CONCEPTO */
                ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

                PaganteService paganteService = BeanFactory.getPaganteService();
                FichaService fichaService = BeanFactory.getFichaService();

                PaganteBean pagante = new PaganteBean();
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(matriculaBean.getIdMatricula().toString());
                pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                pagante = paganteService.obtenerPorIdPagEst(pagante);
                if (pagante == null) {
                    System.out.println(">>>>> No existe");
                    //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                    pagante = new PaganteBean();
                    //INSERTANDO PAGANTE
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(matriculaBean.getIdMatricula().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    pagante.setNomPagante(matriculaBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
                    pagante.setCreaPor(usuarioLoginBean.getUsuario());
                    paganteService.insertar(pagante);
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(matriculaBean.getIdMatricula().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(matriculaBean.getIdMatricula().toString());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                } else if (pagante != null) {
                    System.out.println(">>>>> existe");
                    //SI EXISTE PAGANTE INSERTA FICHAS
                    //OBTENIENDO NUMERO DE ASIGNACIONES
                    EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                    eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                }
            } else {
                new MensajePrime().addErrorGeneralMessage();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignacionPorObjPersonal(PersonalBean personalBean) {
        try {
            if (personalBean != null) {
                /* OBTENIENDO CONCEPTO */
                ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

                PaganteService paganteService = BeanFactory.getPaganteService();
                EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
                FichaService fichaService = BeanFactory.getFichaService();
                PaganteBean pagante = new PaganteBean();
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(personalBean.getIdPersonal().toString());
                pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                pagante = paganteService.obtenerPorIdPagPer(pagante);
                if (pagante == null) {
                    System.out.println(">>>>> No existe");
                    //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                    pagante = new PaganteBean();
                    //INSERTANDO PAGANTE
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(personalBean.getIdPersonal().toString());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    pagante.setNomPagante(personalBean.getNombreCompleto());
                    pagante.setCreaPor(usuarioLoginBean.getUsuario());
                    paganteService.insertar(pagante);
                    //OBTENIENDO NUMERO DE ASIGNACIONES
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(personalBean.getIdPersonal().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(personalBean.getIdPersonal().toString());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                } else if (pagante != null) {
                    System.out.println(">>>>> existe");
                    //SI EXISTE PAGANTE INSERTA FICHAS
                    //OBTENIENDO NUMERO DE ASIGNACIONES

                    EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                    eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                }
            } else {
                new MensajePrime().addErrorGeneralMessage();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignacionPorObjExterno(PersonaBean personaBean) {
        try {
            if (personaBean != null) {
                /* OBTENIENDO CONCEPTO */
                ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

                PaganteService paganteService = BeanFactory.getPaganteService();
                EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
                FichaService fichaService = BeanFactory.getFichaService();
                PaganteBean pagante = new PaganteBean();
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(personaBean.getIdPersona());
                pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                pagante = paganteService.obtenerPorIdPagExt(pagante);
                if (pagante == null) {
                    System.out.println(">>>>> No existe");
                    //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                    pagante = new PaganteBean();
                    //INSERTANDO PAGANTE
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(personaBean.getIdPersona());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    pagante.setNomPagante(personaBean.getNombreCompleto());
                    pagante.setCreaPor(usuarioLoginBean.getUsuario());
                    paganteService.insertar(pagante);

                    //OBTENIENDO NUMERO DE ASIGNACIONES
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(personaBean.getIdPersona().toString());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(personaBean.getIdPersona().toString());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                } else if (pagante != null) {
                    System.out.println(">>>>> existe");
                    //SI EXISTE PAGANTE INSERTA FICHAS
                    //OBTENIENDO NUMERO DE ASIGNACIONES
                    EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                    eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    if (getSelTipAsigEsp().equals(2)) {
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                }
            } else {
                new MensajePrime().addErrorGeneralMessage();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignacionPorObjEntidad(EntidadBean entidadBean) {
        try {
            if (entidadBean != null) {
                /* OBTENIENDO CONCEPTO */
                ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
                ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
                conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

                PaganteService paganteService = BeanFactory.getPaganteService();
                EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
                FichaService fichaService = BeanFactory.getFichaService();
                PaganteBean pagante = new PaganteBean();
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(entidadBean.getRuc());
                pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                pagante = paganteService.obtenerPorIdPagEnt(pagante);
                if (pagante == null) {
                    System.out.println(">>>>> No existe");
                    //SI NO EXISTE PAGANTE INSERTA PAGANTE Y FICHAS 
                    pagante = new PaganteBean();
                    //INSERTANDO PAGANTE
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(entidadBean.getRuc());
                    pagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    pagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    pagante.setNomPagante(entidadBean.getNombre());
                    pagante.setCreaPor(usuarioLoginBean.getUsuario());
                    paganteService.insertar(pagante);

                    //OBTENIENDO NUMERO DE ASIGNACIONES
                    EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                    eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                    if (getSelTipAsigEsp().equals(2)) {
//                            Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(entidadBean.getRuc());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(1)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(entidadBean.getRuc());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                } else if (pagante != null) {
                    System.out.println(">>>>> existe");
                    //SI EXISTE PAGANTE INSERTA FICHAS
                    //OBTENIENDO NUMERO DE ASIGNACIONES

                    EventoTipoPaganteBean eventoTipoPagante = new EventoTipoPaganteBean();
                    eventoTipoPagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    eventoTipoPagante.getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    eventoTipoPagante.getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                    Integer nroAsignaciones = eventoTipoPaganteService.obtenerNumeroAsig(eventoTipoPagante);
                    if (getSelTipAsigEsp().equals(2)) {
//                            Integer nroIni = eventoTipoPaganteService.obtenerNumeroIni(eventoTipoPagante);
                        if (nroAsig != 0) {
                            for (int i = 0; i < nroAsig; i++) {
                                FichaBean ficha = new FichaBean();
                                //INSERTANDO FICHA
                                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                ficha.setSerie(MaristaConstantes.serie_numdoc);
                                ficha.setNroficha(nroIni);
                                ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                ficha.setMonto(conceptoUniNegBean.getImporte());
                                ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                if (tipAsignacionFicha.equals(1)) { // DONACION
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                    ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                                ficha.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaService.insertar(ficha);
                                nroIni++;
                            }
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        }
                    } else if (getSelTipAsigEsp().equals(2)) {
                        Integer numero = validarNumeroFicha();
                        if (numero == null) {
                            FichaBean ficha = new FichaBean();
                            //INSERTANDO FICHA
                            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            ficha.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            ficha.setSerie(MaristaConstantes.serie_numdoc);
                            ficha.setNroficha(nroIni);
                            ficha.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            ficha.setMonto(conceptoUniNegBean.getImporte());
                            ficha.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            if (tipAsignacionFicha.equals(1)) { // DONACION
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFicha.equals(0)) { // PENDIENTE
                                ficha.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            ficha.setIdTipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                            ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                            ficha.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(ficha);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }
                }
            } else {
                new MensajePrime().addErrorGeneralMessage();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTipoPagante() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            System.out.println(">>>>" + paganteBean.getEventoBean().getIdEvento());
            eventoTipoPaganteBean.getEventoBean().setIdEvento(paganteBean.getEventoBean().getIdEvento());
            listaEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTipoPaganteAfter() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            System.out.println(">>>>" + paganteFiltroBean.getEventoBean().getIdEvento());
            eventoTipoPaganteBean.getEventoBean().setIdEvento(paganteFiltroBean.getEventoBean().getIdEvento());
            listaAfterEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public Integer obtenerMaxNroDoc(String uniNeg, String serie, Integer idTipoPagante) {
        Integer nro = 0;
        try {
            FichaBean fichaNro = new FichaBean();
            fichaNro.getUnidadNegocioBean().setUniNeg(uniNeg);
            fichaNro.setSerie(serie);
            fichaNro.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(idTipoPagante);
            FichaService fichaService = BeanFactory.getFichaService();
            nro = fichaService.obtenerMaxNro(fichaNro);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return nro;
    }

    public void limpiarFiltroPagante() {
        try {
            paganteFiltroBean = new PaganteBean();
            listaPagantefiltroBean = new ArrayList<>();
            paganteFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void filtrarPaganteMasivo() {
        try {
            PaganteService paganteService = BeanFactory.getPaganteService();
            Integer res = 0;
            if (getPaganteFiltroBean().getNroDoc() != null && !getPaganteFiltroBean().getNroDoc().equals("")) {
                getPaganteFiltroBean().setNroDoc(getPaganteFiltroBean().getNroDoc());
                res = 1;
            }
            if (getPaganteFiltroBean().getNomPagante() != null && !getPaganteFiltroBean().getNomPagante().equals("")) {
                getPaganteFiltroBean().setNomPagante(getPaganteFiltroBean().getNomPagante());
                res = 1;
            }
            if (getPaganteFiltroBean().getEventoBean().getIdEvento() != null) {
                getPaganteFiltroBean().getEventoBean().setIdEvento(getPaganteFiltroBean().getEventoBean().getIdEvento());
                res = 1;
            }
            if (getPaganteFiltroBean().getNroFicha() != null) {
                getPaganteFiltroBean().setNroFicha(getPaganteFiltroBean().getNroFicha());
                res = 1;
            }
            if (res == 1) {
                if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante() != null) {
                    if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                        listaPagantefiltroBean = paganteService.filtrarPaganteEst(getPaganteFiltroBean());
                    } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(2)) {
                        listaPagantefiltroBean = paganteService.filtrarPagantePer(getPaganteFiltroBean());
                    } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(3)) {
                        listaPagantefiltroBean = paganteService.filtrarPaganteExt(getPaganteFiltroBean());
                    } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(4)) {
                        listaPagantefiltroBean = paganteService.filtrarPaganteEnt(getPaganteFiltroBean());
                    }
                } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante() == null) {
                    listaPagantefiltroBean = paganteService.filtrarPaganteObj(getPaganteFiltroBean());
                }
                if (listaPagantefiltroBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPagantefiltroBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPagantefiltroBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerPorIdPagante(Object object) {
        try {
            PaganteBean pagante = (PaganteBean) object;
            FichaBean ficha = new FichaBean();
            PaganteService paganteService = BeanFactory.getPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante() != null) {
                if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                    pagante = paganteService.obtenerPorIdPagEst(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getMatriculaBean().setIdMatricula(Integer.parseInt(pagante.getIdPagante()));
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEst(ficha);
                    filtrarEstadoFicha();
                } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(2)) {
                    pagante = paganteService.obtenerPorIdPagPer(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getPersonalBean().setIdPersonal(Integer.parseInt(pagante.getIdPagante()));
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPagantePer(ficha);
                    filtrarEstadoFicha();
                } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(3)) {
                    pagante = paganteService.obtenerPorIdPagExt(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getPersonaBean().setIdPersona(pagante.getIdPagante());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteExt(ficha);
                    filtrarEstadoFicha();
                } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante().equals(4)) {
                    pagante = paganteService.obtenerPorIdPagEnt(pagante);
                    paganteView = pagante;
                    System.out.println(">>>>" + pagante.getIdPagante());
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                    ficha.getPaganteBean().getEntidadBean().setRuc(pagante.getIdPagante());
                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEnt(ficha);
                    filtrarEstadoFicha();
                }
            } else if (getPaganteFiltroBean().getTipoPaganteBean().getIdtipoPagante() == null) {
                pagante = paganteService.obtenerPorId(pagante);
                System.out.println(">>>>" + pagante.getIdPagante());
                System.out.println(">>>>" + pagante.getTipoPaganteBean().getIdtipoPagante());
                paganteView = pagante;
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                listaFichaAfterBean = fichaService.obtenerFichaPorPaganteObj(ficha);
                filtrarEstadoFicha();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //OBTENER TIPO DE ASIGNACION
    public void obtenerTipoAsig() {
        try {
            if (paganteBean.getTipoPaganteBean().getIdtipoPagante() != null) {
                paganteBean.getTipoPaganteBean().setIdtipoPagante(paganteBean.getTipoPaganteBean().getIdtipoPagante());
            }
            if (getTipAsignacion().equals(2)) {
                setFlgRenderEsp(true);
                setTipAsignacion(getTipAsignacion());
                System.out.println(">>>>" + getTipAsignacion());
            } else {
                setFlgRenderEsp(false);
                setTipAsignacion(getTipAsignacion());
                System.out.println(">>>>" + getTipAsignacion());
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //OBTENER TIPO DE FORMA DE ASIGNACION
    public void obtenerTipoAsigForma() {
        try {
            nroIni = 0;
            nroFin = 0;
            nroAsig = 0;
            setSelTipAsigEsp(getSelTipAsigEsp());
            System.out.println(">>>>>" + getSelTipAsigEsp());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //OBTENER NUMERO FINAL
    public void obtenerNroAsigna() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroficha(nroIni);
            Integer numero = fichaService.obtenerNroFicha(ficha);
            numeroFicha = numero;
            if (tipPagante.equals(1)) {
                if (!listaMatriculaEstudianteMasivoBean.isEmpty()) {
                    if (numero == null) {
                        //listaMatriculaEstudianteMasivoBean.size()
                        nroFin = ((nroAsig * (1)) + nroIni) - 1;
                    } else if (numero != null) {
                        new MensajePrime().addMessageNumber();
                    }
                } else {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            } else if (tipPagante.equals(2)) {
                if (!listaPersonalFiltroBean.isEmpty()) {
                    if (numero == null) {
                        //listaPersonalFiltroBean.size()
                        nroFin = ((nroAsig * (1)) + nroIni) - 1;
                    } else if (numero != null) {
                        new MensajePrime().addMessageNumber();
                    }
                } else {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            } else if (tipPagante.equals(3)) {
                if (!listaPersonaFiltroBean.isEmpty()) {
                    if (numero == null) {
                        //listaPersonaFiltroBean.size()
                        nroFin = ((nroAsig * (1)) + nroIni) - 1;
                    } else if (numero != null) {
                        new MensajePrime().addMessageNumber();
                    }
                } else {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            } else if (tipPagante.equals(4)) {
                if (!listaEntidadFiltroBean.isEmpty()) {
                    if (numero == null) {
                        //listaEntidadFiltroBean.size()
                        nroFin = ((nroAsig * (1)) + nroIni) - 1;
                    } else if (numero != null) {
                        new MensajePrime().addMessageNumber();
                    }
                } else {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            System.out.println(">>>" + numero);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    /* CAMBIAR ESTADO DE FICHAS */
    public void rowEditFicha(RowEditEvent event) {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean fichaBean = new FichaBean();
            fichaBean.getTipoStatusFicha().setIdCodigo(((FichaBean) event.getObject()).getTipoStatusFicha().getIdCodigo());
            fichaBean.setModiPor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichaBean.setIdFicha(((FichaBean) event.getObject()).getIdFicha());
            fichaService.modificarEstado(fichaBean);
            obtenerPorIdPagante(paganteView);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarEstadoFicha() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.getPaganteBean().setIdPagante(paganteView.getIdPagante());
            ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
            ficha.getTipoStatusFicha().setIdCodigo(fichaFiltroBean.getTipoStatusFicha().getIdCodigo());
            listaFichaAfterBean = fichaService.obtenerPorTipoEstado(ficha);
            if (listaFichaAfterBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorIdFicha(Object object) {
        try {
            FichaBean fichaBean = (FichaBean) object;
            fichaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            FichaService fichaService = BeanFactory.getFichaService();
            fichaVistaBean = fichaService.obtenerPorIdFicha(fichaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminarFicha() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            fichaVistaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichaService.eliminarFicha(fichaVistaBean);
            obtenerPorIdPagante(paganteView);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //VER ASIGNACIONES FILTRO PAGANTES
    public void obtenerPaganteAsigBefore(Object object, Integer dato) {
        try {
            /*
             1 => estudiante
             2 => personal
             3 => externo
             4 => entidad
             */
            PaganteBean pagante = new PaganteBean();
            FichaBean ficha = new FichaBean();
            PaganteService paganteService = BeanFactory.getPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (dato.equals(1)) {
                pagante = new PaganteBean();
                ficha = new FichaBean();
                MatriculaBean matriculaBean = (MatriculaBean) object;
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(matriculaBean.getIdMatricula().toString());
                pagante.getTipoPaganteBean().setIdtipoPagante(1);
                pagante = paganteService.obtenerPorIdPagEst(pagante);
                paganteView = pagante;
                //OBTENIENDO FICHA
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().getMatriculaBean().setIdMatricula(pagante.getMatriculaBean().getIdMatricula());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(1);
                listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEst(ficha);
                if (!listaFichaAfterBean.isEmpty()) {
                    filtrarEstadoFicha();
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFichaAfterBean = new ArrayList<>();
                }
            } else if (dato.equals(2)) {
                pagante = new PaganteBean();
                ficha = new FichaBean();
                PersonalBean personalBean = (PersonalBean) object;
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(personalBean.getIdPersonal().toString());
                pagante.getTipoPaganteBean().setIdtipoPagante(2);
                pagante = paganteService.obtenerPorIdPagPer(pagante);
                paganteView = pagante;
                //OBTENIENDO FICHA
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().getPersonalBean().setIdPersonal(pagante.getPersonalBean().getIdPersonal());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(2);
                listaFichaAfterBean = fichaService.obtenerFichaPorPagantePer(ficha);
                if (!listaFichaAfterBean.isEmpty()) {
                    filtrarEstadoFicha();
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFichaAfterBean = new ArrayList<>();
                }
            } else if (dato.equals(3)) {
                pagante = new PaganteBean();
                ficha = new FichaBean();
                PersonaBean personaBean = (PersonaBean) object;
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(personaBean.getIdPersona());
                pagante.getTipoPaganteBean().setIdtipoPagante(3);
                pagante = paganteService.obtenerPorIdPagExt(pagante);
                paganteView = pagante;
                //OBTENIENDO FICHA
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().getPersonaBean().setIdPersona(personaBean.getIdPersona());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(3);
                listaFichaAfterBean = fichaService.obtenerFichaPorPaganteExt(ficha);
                if (!listaFichaAfterBean.isEmpty()) {
                    filtrarEstadoFicha();
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFichaAfterBean = new ArrayList<>();
                }
            } else if (dato.equals(4)) {
                pagante = new PaganteBean();
                ficha = new FichaBean();
                EntidadBean entidadBean = (EntidadBean) object;
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(entidadBean.getRuc());
                pagante.getTipoPaganteBean().setIdtipoPagante(4);
                pagante = paganteService.obtenerPorIdPagEnt(pagante);
                paganteView = pagante;
                //OBTENIENDO FICHA
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(4);
                listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEnt(ficha);
                if (!listaFichaAfterBean.isEmpty()) {
                    filtrarEstadoFicha();
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFichaAfterBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void asignarFichasEsp(Object object) {
        try {
            PaganteBean pagante = (PaganteBean) object;
            paganteView = pagante;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerNroAsignaPop() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroficha(nroIni);
            Integer numero = fichaService.obtenerNroFicha(ficha);
            if (paganteView.getTipoPaganteBean().getIdtipoPagante() != null) {
                if (tipAsignacionFichaPop != null) {
                    if (tipAsignacionFichaPop.equals(1)) {
                        Integer numDonado = obtenerNumDonado();
                        if (numDonado == 0) {
                            if (numero == null) {
                                FichaBean fichaPago = new FichaBean();
                                //INSERTANDO FICHA
                                fichaPago.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                fichaPago.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                fichaPago.setSerie(MaristaConstantes.serie_numdoc);
                                fichaPago.setNroficha(nroIni);
                                if (tipAsignacionFichaPop.equals(1)) {
                                    fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                } else if (tipAsignacionFichaPop.equals(0)) {
                                    fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                }
                                fichaPago.setMonto(conceptoUniNegBean.getImporte());
                                fichaPago.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                fichaPago.setIdTipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                                fichaPago.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                                fichaPago.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                fichaPago.setCreaPor(usuarioLoginBean.getUsuario());
                                fichaPago.setFlgAdicional(1);
                                fichaService.insertar(fichaPago);
                                filtrarPaganteMasivo();
                                setPaganteView(paganteView);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                                nroIni = 0;
                                nroFin = 0;
                                tipAsignacion = null;
                            } else if (numero != null) {
                                new MensajePrime().addMessageNumber();
                            }
                        } else if (numDonado != 0) {
                            if (valDonado.equals(0)) {
                                RequestContext.getCurrentInstance().addCallbackParam("donado", true);
                            } else if (!valDonado.equals(0)) {
                                if (numero == null) {
                                    FichaBean fichaPago = new FichaBean();
                                    //INSERTANDO FICHA
                                    fichaPago.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    fichaPago.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                                    fichaPago.setSerie(MaristaConstantes.serie_numdoc);
                                    fichaPago.setNroficha(nroIni);
                                    if (tipAsignacionFichaPop.equals(1)) {
                                        fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                                    } else if (tipAsignacionFichaPop.equals(0)) {
                                        fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                                    }
                                    fichaPago.setMonto(conceptoUniNegBean.getImporte());
                                    fichaPago.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                                    fichaPago.setIdTipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                                    fichaPago.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                                    fichaPago.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                                    fichaPago.setCreaPor(usuarioLoginBean.getUsuario());
                                    fichaService.insertar(fichaPago);
                                    filtrarPaganteMasivo();
                                    setPaganteView(paganteView);
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                                    nroIni = 0;
                                    nroFin = 0;
                                    tipAsignacion = null;
                                    setValDonado(0);
                                } else if (numero != null) {
                                    setValDonado(0);
                                    new MensajePrime().addMessageNumber();
                                }
                            }
                        }
                    } else if (tipAsignacionFichaPop.equals(0)) {
                        if (numero == null) {
                            FichaBean fichaPago = new FichaBean();
                            //INSERTANDO FICHA
                            fichaPago.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            fichaPago.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            fichaPago.setSerie(MaristaConstantes.serie_numdoc);
                            fichaPago.setNroficha(nroIni);
                            if (tipAsignacionFichaPop.equals(1)) {
                                fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                            } else if (tipAsignacionFichaPop.equals(0)) {
                                fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                            }
                            fichaPago.setMonto(conceptoUniNegBean.getImporte());
                            fichaPago.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                            fichaPago.setIdTipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                            fichaPago.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                            fichaPago.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                            fichaPago.setCreaPor(usuarioLoginBean.getUsuario());
                            fichaService.insertar(fichaPago);
                            filtrarPaganteMasivo();
                            setPaganteView(paganteView);
                            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            nroIni = 0;
                            nroFin = 0;
                            tipAsignacion = null;
                        } else if (numero != null) {
                            new MensajePrime().addMessageNumber();
                        }
                    }

                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void permitirDonado() {
        try {
            setValDonado(1);
            obtenerNroAsignaPop();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public Integer obtenerNumDonado() {
        Integer res = 0;
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean fichaDon = new FichaBean();
            fichaDon.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichaDon.getPaganteBean().setIdPagante(paganteView.getIdPagante());
            fichaDon.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
            fichaDon.getTipoStatusFicha().setCodigo(MaristaConstantes.COD_FICHA_STATUS_DONADO);
            fichaDon.getTipoStatusFicha().getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_STATUS_FICHA);
            res = fichaService.obtenerNumFichaDon(fichaDon);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return res;
    }

    //GET Y SET
    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
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

    public List<MatriculaBean> getListaMatriculaEstudianteMasivoBean() {
        if (listaMatriculaEstudianteMasivoBean == null) {
            listaMatriculaEstudianteMasivoBean = new ArrayList<>();
        }
        return listaMatriculaEstudianteMasivoBean;
    }

    public void setListaMatriculaEstudianteMasivoBean(List<MatriculaBean> listaMatriculaEstudianteMasivoBean) {
        this.listaMatriculaEstudianteMasivoBean = listaMatriculaEstudianteMasivoBean;
    }

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
    }

    public Boolean getFlgEstComprobanteMes() {
        return flgEstComprobanteMes;
    }

    public void setFlgEstComprobanteMes(Boolean flgEstComprobanteMes) {
        this.flgEstComprobanteMes = flgEstComprobanteMes;
    }

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getFlgPorNivelGrado() {
        return flgPorNivelGrado;
    }

    public void setFlgPorNivelGrado(Boolean flgPorNivelGrado) {
        this.flgPorNivelGrado = flgPorNivelGrado;
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

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        if (listaGradoAcademicoFiltroBean == null) {
            listaGradoAcademicoFiltroBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
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

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
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

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
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

    public List<EntidadBean> getListaEntidadFiltroBean() {
        if (listaEntidadFiltroBean == null) {
            listaEntidadFiltroBean = new ArrayList<>();
        }
        return listaEntidadFiltroBean;
    }

    public void setListaEntidadFiltroBean(List<EntidadBean> listaEntidadFiltroBean) {
        this.listaEntidadFiltroBean = listaEntidadFiltroBean;
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

    public List<UnidadOrganicaBean> getListaUnidadOrganicaBean() {
        if (listaUnidadOrganicaBean == null) {
            listaUnidadOrganicaBean = new ArrayList<>();
        }
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UnidadOrganicaBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
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

    public PaganteBean getPaganteBean() {
        if (paganteBean == null) {
            paganteBean = new PaganteBean();
        }
        return paganteBean;
    }

    public void setPaganteBean(PaganteBean paganteBean) {
        this.paganteBean = paganteBean;
    }

    public List<PaganteBean> getListaPaganteBean() {
        if (listaPaganteBean == null) {
            listaPaganteBean = new ArrayList<>();
        }
        return listaPaganteBean;
    }

    public void setListaPaganteBean(List<PaganteBean> listaPaganteBean) {
        this.listaPaganteBean = listaPaganteBean;
    }

    public EventoBean getEventoBean() {
        if (eventoBean == null) {
            eventoBean = new EventoBean();
        }
        return eventoBean;
    }

    public void setEventoBean(EventoBean eventoBean) {
        this.eventoBean = eventoBean;
    }

    public List<EventoBean> getListaEventoBean() {
        if (listaEventoBean == null) {
            listaEventoBean = new ArrayList<>();
        }
        return listaEventoBean;
    }

    public void setListaEventoBean(List<EventoBean> listaEventoBean) {
        this.listaEventoBean = listaEventoBean;
    }

    public EventoTipoPaganteBean getEventoTipoPaganteBean() {
        if (eventoTipoPaganteBean == null) {
            eventoTipoPaganteBean = new EventoTipoPaganteBean();
        }
        return eventoTipoPaganteBean;
    }

    public void setEventoTipoPaganteBean(EventoTipoPaganteBean eventoTipoPaganteBean) {
        this.eventoTipoPaganteBean = eventoTipoPaganteBean;
    }

    public List<EventoTipoPaganteBean> getListaEventoTipoPaganteBean() {
        if (listaEventoTipoPaganteBean == null) {
            listaEventoTipoPaganteBean = new ArrayList<>();
        }
        return listaEventoTipoPaganteBean;
    }

    public void setListaEventoTipoPaganteBean(List<EventoTipoPaganteBean> listaEventoTipoPaganteBean) {
        this.listaEventoTipoPaganteBean = listaEventoTipoPaganteBean;
    }

    public PaganteBean getPaganteFiltroBean() {
        if (paganteFiltroBean == null) {
            paganteFiltroBean = new PaganteBean();
        }
        return paganteFiltroBean;
    }

    public void setPaganteFiltroBean(PaganteBean paganteFiltroBean) {
        this.paganteFiltroBean = paganteFiltroBean;
    }

    public List<PaganteBean> getListaPagantefiltroBean() {
        if (listaPagantefiltroBean == null) {
            listaPagantefiltroBean = new ArrayList<>();
        }
        return listaPagantefiltroBean;
    }

    public void setListaPagantefiltroBean(List<PaganteBean> listaPagantefiltroBean) {
        this.listaPagantefiltroBean = listaPagantefiltroBean;
    }

    public List<EventoBean> getListaAfterEventoBean() {
        if (listaAfterEventoBean == null) {
            listaAfterEventoBean = new ArrayList<>();
        }
        return listaAfterEventoBean;
    }

    public void setListaAfterEventoBean(List<EventoBean> listaAfterEventoBean) {
        this.listaAfterEventoBean = listaAfterEventoBean;
    }

    public List<EventoTipoPaganteBean> getListaAfterEventoTipoPaganteBean() {
        if (listaAfterEventoTipoPaganteBean == null) {
            listaAfterEventoTipoPaganteBean = new ArrayList<>();
        }
        return listaAfterEventoTipoPaganteBean;
    }

    public void setListaAfterEventoTipoPaganteBean(List<EventoTipoPaganteBean> listaAfterEventoTipoPaganteBean) {
        this.listaAfterEventoTipoPaganteBean = listaAfterEventoTipoPaganteBean;
    }

    public Integer getTipPaganteAfter() {
        return tipPaganteAfter;
    }

    public void setTipPaganteAfter(Integer tipPaganteAfter) {
        this.tipPaganteAfter = tipPaganteAfter;
    }

    public List<FichaBean> getListaFichaAfterBean() {
        if (listaFichaAfterBean == null) {
            listaFichaAfterBean = new ArrayList<>();
        }
        return listaFichaAfterBean;
    }

    public void setListaFichaAfterBean(List<FichaBean> listaFichaAfterBean) {
        this.listaFichaAfterBean = listaFichaAfterBean;
    }

    public PaganteBean getPaganteView() {
        if (paganteView == null) {
            paganteView = new PaganteBean();
        }
        return paganteView;
    }

    public void setPaganteView(PaganteBean paganteView) {
        this.paganteView = paganteView;
    }

    public Integer getTipAsignacion() {
        return tipAsignacion;
    }

    public void setTipAsignacion(Integer tipAsignacion) {
        this.tipAsignacion = tipAsignacion;
    }

    public Integer getNroIni() {
        return nroIni;
    }

    public void setNroIni(Integer nroIni) {
        this.nroIni = nroIni;
    }

    public Integer getNroFin() {
        return nroFin;
    }

    public void setNroFin(Integer nroFin) {
        this.nroFin = nroFin;
    }

    public Integer getNroAsig() {
        return nroAsig;
    }

    public void setNroAsig(Integer nroAsig) {
        this.nroAsig = nroAsig;
    }

    public Boolean getFlgRenderEsp() {
        return flgRenderEsp;
    }

    public void setFlgRenderEsp(Boolean flgRenderEsp) {
        this.flgRenderEsp = flgRenderEsp;
    }

    public Boolean getValConTodos() {
        return valConTodos;
    }

    public void setValConTodos(Boolean valConTodos) {
        this.valConTodos = valConTodos;
    }

    public List<CodigoBean> getListaTipoEstadoFicha() {
        if (listaTipoEstadoFicha == null) {
            listaTipoEstadoFicha = new ArrayList<>();
        }
        return listaTipoEstadoFicha;
    }

    public void setListaTipoEstadoFicha(List<CodigoBean> listaTipoEstadoFicha) {
        this.listaTipoEstadoFicha = listaTipoEstadoFicha;
    }

    public FichaBean getFichaFiltroBean() {
        if (fichaFiltroBean == null) {
            fichaFiltroBean = new FichaBean();
        }
        return fichaFiltroBean;
    }

    public void setFichaFiltroBean(FichaBean fichaFiltroBean) {
        this.fichaFiltroBean = fichaFiltroBean;
    }

    public List<CodigoBean> getListaTipoEstadoFiltroFicha() {
        if (listaTipoEstadoFiltroFicha == null) {
            listaTipoEstadoFiltroFicha = new ArrayList<>();
        }
        return listaTipoEstadoFiltroFicha;
    }

    public void setListaTipoEstadoFiltroFicha(List<CodigoBean> listaTipoEstadoFiltroFicha) {
        this.listaTipoEstadoFiltroFicha = listaTipoEstadoFiltroFicha;
    }

    public Integer getSelTipAsigEsp() {
        return selTipAsigEsp;
    }

    public void setSelTipAsigEsp(Integer selTipAsigEsp) {
        this.selTipAsigEsp = selTipAsigEsp;
    }

    public FichaBean getFichaVistaBean() {
        if (fichaVistaBean == null) {
            fichaVistaBean = new FichaBean();
        }
        return fichaVistaBean;
    }

    public void setFichaVistaBean(FichaBean fichaVistaBean) {
        this.fichaVistaBean = fichaVistaBean;
    }

    public Integer getTipAsignacionFicha() {
        return tipAsignacionFicha;
    }

    public void setTipAsignacionFicha(Integer tipAsignacionFicha) {
        this.tipAsignacionFicha = tipAsignacionFicha;
    }

    public MatriculaBean getMatriculaSelectBean() {
        if (matriculaSelectBean == null) {
            matriculaSelectBean = new MatriculaBean();
        }
        return matriculaSelectBean;
    }

    public void setMatriculaSelectBean(MatriculaBean matriculaSelectBean) {
        this.matriculaSelectBean = matriculaSelectBean;
    }

    public Integer getTipAsignacionFichaPop() {
        return tipAsignacionFichaPop;
    }

    public void setTipAsignacionFichaPop(Integer tipAsignacionFichaPop) {
        this.tipAsignacionFichaPop = tipAsignacionFichaPop;
    }

    public Integer getValDonado() {
        return valDonado;
    }

    public void setValDonado(Integer valDonado) {
        this.valDonado = valDonado;
    }

    public Integer getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(Integer numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

}
