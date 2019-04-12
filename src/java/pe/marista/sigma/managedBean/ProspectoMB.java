package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AdmisionService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.FamiliarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.bean.PersonaBean;

/**
 *
 * @author Administrador
 */
public class ProspectoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ProspectoMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaDocPer();
            listaDocPer = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            PaisService paisService = BeanFactory.getPaisService();
            getListaNacionalidad();
            listaNacionalidad = paisService.obtenerPais();
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademico();
            listaGradoAcademico = gradoAcademicoService.obtenerTodosMatri();
            getListaStatusFiltroEst();
            listaStatusFiltroEst = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_EST));
//            FamiliarEstudianteBean padreEstudianteBean = new FamiliarEstudianteBean();
//            FamiliarService familiarService = BeanFactory.getFamiliarService();
            getPadreEstudianteBean().setTipoParentescoBean(codigoService.obtenerPorCodigo(new CodigoBean(MaristaConstantes.COD_PAPA)));

            getMadreEstudianteBean().setTipoParentescoBean(codigoService.obtenerPorCodigo(new CodigoBean(MaristaConstantes.COD_MAMA)));
//            padreEstudianteBean=familiarService.obtenerFamiliarPorParentesco(padreEstudianteBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private EstudianteBean estudianteBean;
    private EstudianteBean estudianteVistaBean;
    private String idEstudiante;
    private EstudianteBean estudianteFiltroBean;
    private PersonalBean personalBean;
    private List<CodigoBean> listaStatusFiltroEst;
    private List<EstudianteBean> listaEstudianteBean;
    private List<CodigoBean> listaDocPer;
    private List<PaisBean> listaNacionalidad;
    private List<GradoAcademicoBean> listaGradoAcademico;
    private FamiliarEstudianteBean padreEstudianteBean;
    private FamiliarEstudianteBean madreEstudianteBean;
    private UsuarioBean usuarioLogin;
    private List<FamiliarEstudianteBean> listaFamiliarEstudianteBean;

    public void copiarCodigoEnNroDoc() {
        String codigo = null;
        codigo = getEstudianteBean().getPersonaBean().getIdPersona();
        if (codigo != null) {
            estudianteBean.getPersonaBean().setNroDoc(codigo);
        }
    }

    public void copiarCodigoEnNroDocPadres() {
        String codigo = null;
        codigo = padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona();
        if (codigo != null) {
            padreEstudianteBean.getFamiliarBean().getPersonaBean().setNroDoc(codigo);
        }
        codigo = madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona();
        if (codigo != null) {
            madreEstudianteBean.getFamiliarBean().getPersonaBean().setNroDoc(codigo);
        }
    }

    public void limpiarEstudiante() {
        estudianteBean = new EstudianteBean();
        padreEstudianteBean = new FamiliarEstudianteBean();
        madreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void limpiarPadres() {
        padreEstudianteBean = new FamiliarEstudianteBean();
        madreEstudianteBean = new FamiliarEstudianteBean();
    }

    public void rowSelect(SelectEvent event) {
        try {
            estudianteBean.setCollapsed(false);
            estudianteBean = (EstudianteBean) event.getObject();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean = estudianteService.obtenerEstPorId(estudianteBean);
            this.idEstudiante = estudianteBean.getPersonaBean().getIdPersona();
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
            for (int i = 0; i < listaFamiliarEstudianteBean.size(); i++) {
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                    padreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
                if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                    madreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                }
            }

//            FamiliaService familiaService = BeanFactory.getFamiliaService();
//            estudianteBean.getFamiliaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());     
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void guardarEstudiante() {
//        try {
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            EstudianteBean estudiante = new EstudianteBean();
//            estudiante = estudianteService.obtenerEstPorId(estudianteBean); 
//            if (estudiante == null) {
//                insertarEstudiante();
//            } else {
//                modificarEstudiante(); 
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void guardarEstudiante() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estudiante2 = new EstudianteBean();
            estudiante2 = estudianteService.obtenerEstPorId(estudianteBean);
            if (estudiante2 != null) {
                modificarEstudiante();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void comprobarPersona() {
        try {
            if (!estudianteBean.getPersonaBean().getApepat().equals("")
                    && estudianteBean.getPersonaBean().getApepat() != null
                    && !estudianteBean.getPersonaBean().getApemat().equals("")
                    && estudianteBean.getPersonaBean().getApemat() != null
                    && !estudianteBean.getPersonaBean().getNombre().equals("")
                    && estudianteBean.getPersonaBean().getNombre() != null
                    && estudianteBean.getGradoHabilitado().getIdGradoAcademico() != null
                    && !estudianteBean.getPersonaBean().getIdPersona().equals("")
                    && estudianteBean.getPersonaBean().getIdPersona() != null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                Boolean resultadoPost=false;
                resultadoPost = estudianteService.obtenerEstudianteActivoProspecto(estudianteBean.getPersonaBean().getIdPersona(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (resultadoPost == null) {
                    resultadoPost=false;
                } 
                if (resultadoPost == false) {
                    estudianteBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    estudianteVistaBean = estudianteService.obtenerEstPorId(estudianteBean);
                    if (estudianteVistaBean != null) {
                        RequestContext.getCurrentInstance().addCallbackParam("error", true);
                    } else {
                        insertarEstudiante();
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("Alumno ya fue admitido y esta activo para este periodo.");
                }
            } else {
                new MensajePrime().addInformativeMessagePer("Le falta ingresar algunos de los campo requerido");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEstudiante() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                GradoAcademicoBean grado = new GradoAcademicoBean();
                grado = gradoAcademicoService.obtenerPorId(estudianteBean.getGradoHabilitado());
//                estudianteBean.setPersonaBean(personaBean);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                estudianteBean.setCreaPor(usuarioLogin.getUsuario());
                estudianteBean.getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
                estudianteBean.getPersonaBean().setGradoAcademicoBean(grado);
                estudianteService.insertarEstudiante(estudianteBean, "postulante", usuarioLogin);
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarEstudiante() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteBean.setModiPor(usuarioLogin.getUsuario());
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteService.modificarEstudiante(estudianteBean, "postulante", usuarioLogin);
                estudianteBean.setModiPor(usuarioLogin.getUsuario());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void buscarPorDNI() {
        try {
            int estado = 0;
            if (estudianteBean.getPersonaBean().getIdPersona() != null && !estudianteBean.getPersonaBean().getIdPersona().equals("")) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                EstudianteBean bean;
                estudianteBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                bean = estudianteService.obtenerEstPorId(estudianteBean);
                if (bean != null) {
                    new MensajePrime().addInformativeMessagePer("msjSiDNI");
                    estudianteBean = bean;
                    FamiliarService familiarService = BeanFactory.getFamiliarService();
                    listaFamiliarEstudianteBean = familiarService.obtenerFamiliarEstPorEst(estudianteBean.getPersonaBean().getIdPersona());
                    for (int i = 0; i < listaFamiliarEstudianteBean.size(); i++) {
                        if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_PAPA)) {
                            padreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                        }
                        if (listaFamiliarEstudianteBean.get(i).getTipoParentescoBean().getCodigo().equals(MaristaConstantes.COD_MAMA)) {
                            madreEstudianteBean = listaFamiliarEstudianteBean.get(i);
                        }
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjNoDNI");
                    String idPersona = estudianteBean.getPersonaBean().getIdPersona();
//                    estudianteBean = new EstudianteBean();
                    estudianteBean.getPersonaBean().setIdPersona(idPersona);
                }
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessagePer("msjBusxDNI");
                String idPersona = estudianteBean.getPersonaBean().getIdPersona();
//                estudianteBean = new EstudianteBean();
                estudianteBean.getPersonaBean().setIdPersona(idPersona);
//                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
            PersonaBean personaBean = new PersonaBean();
            PersonaService personaService = BeanFactory.getPersonaService();
            personaBean = personaService.obtenerPersonaPorFiltroProspecto(estudianteBean.getPersonaBean());
            estudianteBean.setPersonaBean(personaBean);
            copiarCodigoEnNroDoc();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudiante() {
        try {
            int estado = 0;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
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
            if (estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().setIdGradoAcademico(estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiantePros(estudianteFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteEspecial() {
        try {
            int estado = 0;
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
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
            if (estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().setIdGradoAcademico(estudianteFiltroBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                estudianteFiltroBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudianteEspecial(estudianteFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String actualizarNacionalidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                PaisService paisService = BeanFactory.getPaisService();
                listaNacionalidad = paisService.obtenerPais();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarPadres() {
        try {
            FamiliarService familiarService = BeanFactory.getFamiliarService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            estudianteBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            padreEstudianteBean.setEstudianteBean(estudianteBean);
            CodigoBean codigo = new CodigoBean();
            codigo.setCodigo(MaristaConstantes.COD_PAPA);
            codigo.setTipoCodigoBean(new TipoCodigoBean(MaristaConstantes.TIP_PARENTESCO));
            padreEstudianteBean.setTipoParentescoBean(codigoService.obtenerPorCodigo(codigo));

            madreEstudianteBean.setEstudianteBean(estudianteBean);
            codigo.setCodigo(MaristaConstantes.COD_MAMA);
            madreEstudianteBean.setTipoParentescoBean(codigoService.obtenerPorCodigo(codigo));
            familiarService.guardarPadres(padreEstudianteBean, madreEstudianteBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            if (ex.getMessage().contains(MensajesBackEnd.getValueOfKey("errorDupliPadres", null))) {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("errorDupliPadres", null));
                GLTLog.writeError(this.getClass(), ex);
            } else {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ex);
            }

        }
    }

    //Metodos Especiales
    public void comprobarPersonaEspecial() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            estudianteBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            estudianteVistaBean = estudianteService.obtenerEstPorId(estudianteBean);
            if (estudianteVistaBean != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                insertarEstudianteEspecial();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarEstudianteEspecial() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                GradoAcademicoBean grado = new GradoAcademicoBean();
                grado = gradoAcademicoService.obtenerPorId(estudianteBean.getPersonaBean().getGradoAcademicoBean());
//                estudianteBean.setPersonaBean(personaBean);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                estudianteBean.setCreaPor(usuarioLogin.getUsuario());
                estudianteBean.getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
                estudianteBean.getPersonaBean().setGradoAcademicoBean(grado);
                estudianteService.insertarEstudianteEspecial(estudianteBean, "Activo");
                insertarMatriculaEspecial(estudianteBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void insertarMatriculaEspecial(EstudianteBean estudianteBean) {
        try {
            GregorianCalendar fechaActual = new GregorianCalendar();
            System.out.println(">>> anio " + fechaActual.get(Calendar.YEAR));
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            MatriculaBean matriculaBean = new MatriculaBean();
            matriculaBean.getUnidadNegocioBean().setUniNeg(estudianteBean.getUnidadNegocioBean().getUniNeg());
            matriculaBean.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
            matriculaBean.setFechaMatricula(fechaActual.getTime());
            matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
            matriculaBean.getTipoMatriculaBean().setIdCodigo(MaristaConstantes.COD_EST_MAT);
            matriculaBean.setAnio(fechaActual.get(Calendar.YEAR));
            matriculaBean.setFlgMatricula(false);
            matriculaBean.setCreaPor(estudianteBean.getCreaPor());
            matriculaService.insertarMatriculaEspecial(matriculaBean);
            generaMatriculaEspecial(matriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarEstudianteEspecial() {
        try {
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estudiante2 = new EstudianteBean();
            estudiante2 = estudianteService.obtenerEstPorId(estudianteBean);
            if (estudiante2 != null) {
                modificarEstudianteEspecial();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String modificarEstudianteEspecial() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                estudianteBean.setModiPor(usuarioLogin.getUsuario());
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                estudianteService.modificarEstudiante(estudianteBean, "Activo", usuarioLogin);
                estudianteBean.setModiPor(usuarioLogin.getUsuario());
                estudianteFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void generaMatriculaEspecial(MatriculaBean matriculaBean) {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            Integer idMatricula = matriculaService.obtenerMaxId(matriculaBean);
            matriculaBean.setIdMatricula(idMatricula);
            matriculaBean.setCrIni(111);
            matriculaBean.setCrPri(211);
            matriculaBean.setCrSec(311);
            matriculaBean.setCrBac(400);
            matriculaService.generaMatriculaEspecial(matriculaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //End
    //Getter y Setter
    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
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

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
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

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
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

    public List<GradoAcademicoBean> getListaGradoAcademico() {
        if (listaGradoAcademico == null) {
            listaGradoAcademico = new ArrayList<>();
        }
        return listaGradoAcademico;
    }

    public void setListaGradoAcademico(List<GradoAcademicoBean> listaGradoAcademico) {
        this.listaGradoAcademico = listaGradoAcademico;
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

    public UsuarioBean getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public EstudianteBean getEstudianteVistaBean() {
        if (estudianteVistaBean == null) {
            estudianteVistaBean = new EstudianteBean();
        }
        return estudianteVistaBean;
    }

    public void setEstudianteVistaBean(EstudianteBean estudianteVistaBean) {
        this.estudianteVistaBean = estudianteVistaBean;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

}
