package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.bean.PaisBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.PaisService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class PersonaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of PersonaMB
     */
    @PostConstruct
    public void PersonaMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            Integer anio1 = Integer.parseInt(date) - 100;
            Integer anio2 = Integer.parseInt(date) - 18;
            fechaNacIni = "01/01/" + anio1.toString();
            fechaNacFin = "31/12/" + anio2.toString();
            System.out.println("fechas 1  " + fechaNacIni);
            System.out.println("fechas 2  " + fechaNacFin);
            //CodigoBean bean = codigoService.obtenerPorCodigo(MaristaConstantes.COD_DNI);
            getPersonaBean();
            //personaBean.setIdTipoDocPer(bean);

            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPersonaFiltroSolBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    private PersonaBean personaBean;
    private List<PersonaBean> listaPersonaBean;
    private List<CodigoBean> listaDocPer;
    private List<GradoAcademicoBean> listaGradoAcademico;
    private List<PaisBean> listaNacionalidad;
    private List<CodigoBean> listaTipoPersona;
    private PersonaBean personaVista;
    private PersonaBean personaFiltroBean;
    private List<UnidadNegocioBean> listaUnidadNegocioBean;
    private List<PerfilBean> listaPerfilBean;
    private PersonalBean personalBean;
    private Boolean flgGenCod;
    private UsuarioBean usuarioLogin;
    private String fechaNacIni;
    private String fechaNacFin;

    //Ayuda
    private PersonaBean personaFiltroSolBean;
    private List<PersonaBean> listaPersonaFiltroBean;

    public void ponerPersonaEnSolicitudCCH(Object persona, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            personaBean = (PersonaBean) persona;
            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
            if (flgSoli.equals(true)) {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setPersonaBean(personaBean);
                solicitudCajaCHMB.getSolicitudCajaCHBean().getPersonalBean().getUnidadOrganicaBean().setNombreUniOrg("-");
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNombreSolicitante(personaBean.getNombreCompletoDesdeApellidos());
                if (idTipoSol != null) {
                    solicitudCajaCHMB.getSolicitudCajaCHBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                solicitudCajaCHMB.getSolicitudCajaCHBean().setIdRespCheque(personaBean.getIdPersona());
                solicitudCajaCHMB.getSolicitudCajaCHBean().setNomRespCheque(personaBean.getNombreCompletoDesdeApellidos());
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

    public void ponerPersonaEnDependiente(Object persona) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            personaBean = (PersonaBean) persona;
            personaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            personaBean = personaService.obtenerPersPorId(personaBean);
            LegajoMB legajoMB = (LegajoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("legajoMB");

            legajoMB.getPersonalDependienteBean().setApepat(personaBean.getApepat());
            legajoMB.getPersonalDependienteBean().setApepat(personaBean.getApepat());
            legajoMB.getPersonalDependienteBean().setApemat(personaBean.getApemat());
            legajoMB.getPersonalDependienteBean().setNombre(personaBean.getNombre());
            legajoMB.getPersonalDependienteBean().setSexo(personaBean.getSexo());
//            legajoMB.getPersonalDependienteBean().setTipoEstadoCivilBean(personaBean.get);
            legajoMB.getPersonalDependienteBean().setTipoDocPerBean(personaBean.getIdTipoDocPer());
            legajoMB.getPersonalDependienteBean().setNroDoc(personaBean.getNroDoc());
            legajoMB.getPersonalDependienteBean().setFecNac(personaBean.getFecNac());
            legajoMB.getPersonalDependienteBean().setCollapsed(false);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("legajoMB", legajoMB);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaEnMovimientoAlmacen(Object persona, Boolean flgSoli, String idTipoSol, String idTipoResp) {
        try {
            personaBean = (PersonaBean) persona;
            InventarioAlmacenMB inventarioAlmacenMB = (InventarioAlmacenMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("inventarioAlmacenMB");
            if (flgSoli.equals(true)) {
                inventarioAlmacenMB.getMovimientoAlmacenBean().setPersonaBean(personaBean);
                inventarioAlmacenMB.getMovimientoAlmacenBean().getPersonalBean().getUnidadOrganicaBean().setNombreUniOrg("-");
                inventarioAlmacenMB.getMovimientoAlmacenBean().setEntregadoPor(personaBean.getNombreCompletoDesdeApellidos());
                if (idTipoSol != null) {
                    inventarioAlmacenMB.getMovimientoAlmacenBean().setIdTipoSolicitante(idTipoSol);
                }
            } else {
                inventarioAlmacenMB.getMovimientoAlmacenBean().setIdRecibido(personaBean.getIdPersona());
                inventarioAlmacenMB.getMovimientoAlmacenBean().setRecibidopor(personaBean.getNombreCompletoDesdeApellidos());
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

    public void copiarCodigoEnNroDoc() {
        String codigo = null;
        codigo = personaBean.getIdPersona();
        if (codigo != null) {
            personaBean.setNroDoc(codigo);
        }
    }

    public void generarCodigoPersona() {
        try {
            if (flgGenCod.equals(true)) {
                PersonaService personaService = BeanFactory.getPersonaService();
                String cod = null;
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                Integer anio = new Integer(date);
                cod = personaService.generarCodigoPersona(anio, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                getPersonaBean().setIdPersona(cod.toString());
                getPersonaBean().setNroDoc(null);
            } else {
                getPersonaBean().setIdPersona(null);
                getPersonaBean().setNroDoc(null);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosFrmPersona() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaDocPer();
            listaDocPer = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOC_PER));
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademico();
            listaGradoAcademico = gradoAcademicoService.obtenerTodos();
            PaisService paisService = BeanFactory.getPaisService();
            getListaNacionalidad();
            listaNacionalidad = paisService.obtenerPais();

            //ES SUPER ADMIN (?)
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
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

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersona() {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            listaPersonaBean = personaService.obtenerPersona();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPersonaPorUniNeg() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonaService personaService = BeanFactory.getPersonaService();
            listaPersonaBean = personaService.obtenerPersonaPorUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersona(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (personaBean.getIdPersona() != null && !personaBean.getIdPersona().equals("")) {
                    PersonaService personaService = BeanFactory.getPersonaService();
                    UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    personaBean.setCreaPor(usuarioBean.getUsuario());
                    if (personaBean.getNroDoc() != null) {
                        if (personaBean.getNroDoc().equals("")) {
                            personaBean.setNroDoc(null);
                        }
                    } else {
                        personaBean.setNroDoc(null);
                    }

                    personaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                    personaService.insertarPersona(personaBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    if (tipo != null) {
                        DocIngresoMB docIngresoMB = (DocIngresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docIngresoMB");
                        docIngresoMB.setPersonaBean(personaBean);
                        docIngresoMB.rowSelectObject(personaBean);
                    }
                    limpiarPersona();
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

    public String modificarPersona(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personaBean.setModiPor(usuarioBean.getUsuario());
                PersonaService personaService = BeanFactory.getPersonaService();
                personaService.modificarPersona(personaBean);
//                listaPersonaBean = personaService.obtenerPersonaPorUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                if (tipo != null && !tipo.equals("")) {
                    DocIngresoMB docIngresoMB = (DocIngresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("DocIngresoMB");
                    docIngresoMB.setPersonaBean(personaBean);
                    docIngresoMB.rowSelectObject(personaBean);
                }

                limpiarPersona();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarPersona() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                PersonaService personaService = BeanFactory.getPersonaService();
                personaService.eliminarPersona(personaBean.getIdPersona());
//                listaPersonaBean = personaService.obtenerPersonaPorUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersona();
                this.flgGenCod = false;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void comprobarPersona() {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            personaVista = personaService.obtenerPersPorId(personaBean);
            if (personaVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                if (personaBean.getIdPersonaOld() == null) {
                    insertarPersona(null);
                } else {
                    modificarPersona(null);
                }

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void comprobarPersonaDocIng(String tipo) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            personaVista = personaService.obtenerPersPorId(personaBean);
            if (personaVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else {
                if (personaBean.getIdPersonaOld() == null) {
                    insertarPersona(tipo);
                } else {
                    modificarPersona(tipo);
                }

            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarPersona() {
        try {
//            CodigoService codigoService = BeanFactory.getCodigoService();
//            CodigoBean bean = codigoService.obtenerPorId(personaBean.getIdTipoDocPer());
//            PersonaService personaService = BeanFactory.getPersonaService();
//            PersonaBean persona = new PersonaBean();
//            persona = personaService.obtenerPersPorId(personaBean.getIdPersona());
            if (personaBean.getIdPersonaOld() != null) {
                modificarPersona(null);
            } else {
                if (personaVista != null) {
                    insertarPersona(null);
                } else {
                    modificarPersona(null);
                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            personaBean = (PersonaBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerPersona(Object persona) {
        try {
            personaBean = (PersonaBean) persona;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaFamiliar(Object persona) {
        try {
            personaBean = (PersonaBean) persona;
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            estudianteMB.getFamiliarEstudianteBean().getFamiliarBean().setPersonaBean(personaBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaDinamico(Object persona, String origen) {
        try {
            personaBean = (PersonaBean) persona;
            if (origen.equals("responsable")) {
                EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
                estudianteMB.getEstudianteBean().setRespPagoBean(personaBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
            }
            if (origen.equals("personaCurso")) {
                CursoTallerMB cursoTallerMB = (CursoTallerMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cursoTallerMB");
                cursoTallerMB.setPersonaBean(personaBean);
                FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("cursoTallerMB", cursoTallerMB);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonaPostulante(Object persona) {
        try {
            personaBean = (PersonaBean) persona;
            EstudianteMB estudianteMB = (EstudianteMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("estudianteMB");
            System.out.println("");
            estudianteMB.getEstudianteBean().setPersonaBean(personaBean);
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("estudianteMB", estudianteMB);
            System.out.println("okokok");
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estu = new EstudianteBean(); 
            estu.getPersonaBean().setIdPersona(personaBean.getIdPersona());
            estu.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String codigo = estudianteService.existeCodigo(estu);
            System.out.println("codigo: " + codigo);
            estudianteMB.getEstudianteBean().setCodigo(codigo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersona() {
        personaBean = new PersonaBean();
        this.flgGenCod = false;
    }

    public void obtenerPorFiltro(String tipo) {
        try {
            int estado = 0;
            PersonaService personaService = BeanFactory.getPersonaService();
            if (personaFiltroBean.getIdPersona() != null && !personaFiltroBean.getIdPersona().equals("")) {
                personaFiltroBean.setIdPersona(personaFiltroBean.getIdPersona().trim());
                estado = 1;
            }
            if (personaFiltroBean.getApepat() != null && !personaFiltroBean.getApepat().equals("")) {
                personaFiltroBean.setApepat(personaFiltroBean.getApepat().trim());
                estado = 1;
            }
            if (personaFiltroBean.getApemat() != null && !personaFiltroBean.getApemat().equals("")) {
                personaFiltroBean.setApemat(personaFiltroBean.getApemat().trim());
                estado = 1;
            }
            if (personaFiltroBean.getNombre() != null && !personaFiltroBean.getNombre().equals("")) {
                personaFiltroBean.setNombre(personaFiltroBean.getNombre().trim());
                estado = 1;
            }
            if (personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                personaFiltroBean.getGradoAcademicoBean().setIdGradoAcademico(personaFiltroBean.getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonaBean = new ArrayList<>();
            }
            if (estado == 1) {
                if (tipo.equals("prospecto")) {
                    listaPersonaBean = personaService.obtenerPersonaProspectoPorFiltro(personaFiltroBean);
                } else {
                    listaPersonaBean = personaService.obtenerPersonaPorFiltro(personaFiltroBean);

                }
                if (listaPersonaBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonaFiltro() {
        try {
            listaPersonaBean = new ArrayList<>();
            personaFiltroBean = new PersonaBean();
            getPersonaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Ayuda
    public void obtenerPersonaFiltroSol() {
        try {
            int estado = 0;
            PersonaService personaService = BeanFactory.getPersonaService();
            if (personaFiltroSolBean.getIdPersona() != null && !personaFiltroSolBean.getIdPersona().equals("")) {
                personaFiltroSolBean.setIdPersona(personaFiltroSolBean.getIdPersona().trim());
                estado = 1;
            }
            if (personaFiltroSolBean.getApepat() != null && !personaFiltroSolBean.getApepat().equals("")) {
                personaFiltroSolBean.setApepat(personaFiltroSolBean.getApepat().trim());
                estado = 1;
            }
            if (personaFiltroSolBean.getApemat() != null && !personaFiltroSolBean.getApemat().equals("")) {
                personaFiltroSolBean.setApemat(personaFiltroSolBean.getApemat().trim());
                estado = 1;
            }
            if (personaFiltroSolBean.getNombre() != null && !personaFiltroSolBean.getNombre().equals("")) {
                personaFiltroSolBean.setNombre(personaFiltroSolBean.getNombre().trim());
                estado = 1;
            }
            if (personaFiltroSolBean.getGradoAcademicoBean().getIdGradoAcademico() != null && !personaFiltroSolBean.getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                personaFiltroSolBean.getGradoAcademicoBean().setIdGradoAcademico(personaFiltroSolBean.getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonaBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonaFiltroBean = personaService.obtenerPersonaPorFiltro(personaFiltroSolBean);
                if (listaPersonaFiltroBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonaFiltroSol() {
        try {
            personaFiltroSolBean = new PersonaBean();
            listaPersonaFiltroBean = new ArrayList<>();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getPersonaFiltroSolBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void ponerPersonaEnSolicitudCCH(Object personal) {
//        try {
//            personaFiltroSolBean = (PersonaBean) personal;
//            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
////            
////            solicitudCajaCHMB.getSolicitudCajaCHBean().getPersonalBean().setNombreCompleto(personaFiltroSolBean.getNombreCompleto());
////            
////            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("solicitudCajaCHMB", solicitudCajaCHMB);
////            
//            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //Metodos Getter y Setter
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

    public List<CodigoBean> getListaTipoPersona() {
        if (listaTipoPersona == null) {
            listaTipoPersona = new ArrayList<>();
        }
        return listaTipoPersona;
    }

    public void setListaTipoPersona(List<CodigoBean> listaTipoPersona) {
        this.listaTipoPersona = listaTipoPersona;
    }

    public PersonaBean getPersonaVista() {
        if (personaVista == null) {
            personaVista = new PersonaBean();
        }
        return personaVista;
    }

    public void setPersonaVista(PersonaBean personaVista) {
        this.personaVista = personaVista;
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

    public List<PerfilBean> getListaPerfilBean() {
        if (listaPerfilBean == null) {
            listaPerfilBean = new ArrayList<>();
        }
        return listaPerfilBean;
    }

    public void setListaPerfilBean(List<PerfilBean> listaPerfilBean) {
        this.listaPerfilBean = listaPerfilBean;
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

    public Boolean getFlgGenCod() {
        return flgGenCod;
    }

    public void setFlgGenCod(Boolean flgGenCod) {
        this.flgGenCod = flgGenCod;
    }

    public PersonaBean getPersonaFiltroSolBean() {
        if (personaFiltroSolBean == null) {
            personaFiltroSolBean = new PersonaBean();
        }
        return personaFiltroSolBean;
    }

    public void setPersonaFiltroSolBean(PersonaBean personaFiltroSolBean) {
        this.personaFiltroSolBean = personaFiltroSolBean;
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

}
