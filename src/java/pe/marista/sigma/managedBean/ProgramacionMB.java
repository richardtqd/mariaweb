package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AmbienteBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.DetProgramacionDsctoBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.ProcesoBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.ProgramacionDsctoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AmbienteService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.ProcesoService;
import pe.marista.sigma.service.ProgramacionDsctoService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ProgramacionMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ProgramacionMB
     */
    @PostConstruct
    public void init() {
        try {

            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConcepto();
            ProcesoService procesoService = BeanFactory.getProcesoService();
            getListaProcesoBean();
            listaProcesoBean = procesoService.obtenerProcesoActivos(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            AmbienteService ambienteService = BeanFactory.getAmbienteService();
            getListaAmbienteBean();
            listaAmbienteBean = ambienteService.obtenerAmbientePorUnidadNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
//            getListaCentroResponsabilidadBean();
//            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
//            CodigoService codigoService = BeanFactory.getCodigoService();
//            listTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON)); 
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademico();
            listaGradoAcademico = gradoAcademicoService.obtenerTodosIniPriSec();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Varialbes y Propiedades
    private ProgramacionBean programacionBean;
    private List<ProgramacionBean> listaProgramacionBean;
    private List<ProgramacionBean> listaProgramacionSessionBean;
//    private List<ConceptoBean> listaConceptoBean;
    private List<ProcesoBean> listaProcesoBean;
    private List<AmbienteBean> listaAmbienteBean;
//    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private TipoConceptoBean tipoConceptoBean;
    private Integer valor = 1;
//    private List<CodigoBean> listTipoMoneda;
    private List<GradoAcademicoBean> listaGradoAcademico;
    private List<CodigoBean> listaTipoValor;

    private UsuarioBean usuarioLoginBean;

//    private DualListModel<ProgramacionBean> dualCentroResponsabilidadBean;
    private DualListModel<ProgramacionBean> dualProgramacionBean;
    private List<ProgramacionBean> listaProgramacionBeanB;
    private ProgramacionDsctoBean programacionDsctoBean;
    private Integer anio;
    private Boolean flgComboPorcentual = false;
    private Boolean flgComboOrdinal = false;
    private List<DetProgramacionDsctoBean> listaDetProDscto;

    public void limpiarProgramacion() {
        programacionBean = new ProgramacionBean();
        tipoConceptoBean = new TipoConceptoBean();
        listaConceptoUniNegBean = new ArrayList<>();
    }

    public void cargarProgramacionPaquete() {
        try {
            System.out.println("entro 1");
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            ProgramacionBean prog = new ProgramacionBean();
            prog.getConceptoUniNegBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProgramacionBean = programacionService.obtenerProgramacionActivos(prog);
            dualProgramacionBean = new DualListModel<>(listaProgramacionBean, getListaProgramacionBeanB());
            Calendar miCalendario = Calendar.getInstance();
            setAnio(miCalendario.get(Calendar.YEAR));
            getListaTipoValor();
            getProgramacionDsctoBean();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));
            listaDetProDscto = programacionDsctoService.obtenerTodosPorUniNegDetalle(uniNeg);
//            obtenerTipoValor();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoValor() {
        try {
            System.out.println("getCodigo: " + programacionDsctoBean.getTipoValorBean().getIdCodigo());
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoBean = new CodigoBean();
            codigoBean = codigoService.obtenerPorId(new CodigoBean(programacionDsctoBean.getTipoValorBean().getIdCodigo(), "", ""));
            if (codigoBean.getCodigo().equals(MaristaConstantes.Porcentual)) {
                this.flgComboPorcentual = true;
                this.flgComboOrdinal = false;
                System.out.println("as");
            } else if (codigoBean.getCodigo().equals(MaristaConstantes.Ordinal)) {
                this.flgComboPorcentual = false;
                this.flgComboOrdinal = true;
                System.out.println("yu");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardarPaquetes() {
        if (programacionDsctoBean.getIdProgramacionDscto() == null) {
            System.out.println("is null");
            insertarProgramacionDscto();
        } else {
            System.out.println("is not null");
            modificarProgramacionDscto();
        }
    }

    public String insertarProgramacionDscto() {
        String pagina = null;
        try {
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            if (programacionDsctoBean.getDescripcion() != null && !programacionDsctoBean.getDescripcion().trim().equals("")) {
                if (programacionDsctoBean.getTipoValorBean().getIdCodigo() != null && !programacionDsctoBean.getTipoValorBean().getIdCodigo().equals(0)) {
                    if (programacionDsctoBean.getCantProgramaciones() != null && !programacionDsctoBean.getCantProgramaciones().equals(0)) {
                        if (dualProgramacionBean.getTarget().size() > 0) {
                            if (programacionDsctoBean.getValorUnitario() != null && !programacionDsctoBean.getValorUnitario().equals(0)) {
                                ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();

                                ProgramacionDsctoBean pro = new ProgramacionDsctoBean();
                                pro.getUnidadNegocioBean().setUniNeg(uniNeg);
                                pro.setDescripcion(programacionDsctoBean.getDescripcion());
                                pro.setAnio(anio);
                                pro.setCantProgramaciones(programacionDsctoBean.getCantProgramaciones());
                                pro.getTipoValorBean().setIdCodigo(programacionDsctoBean.getTipoValorBean().getIdCodigo());
                                pro.setValorUnitario(programacionDsctoBean.getValorUnitario());
                                pro.setValor(programacionDsctoBean.getValorUnitario());
                                pro.setStatus(programacionDsctoBean.getStatus());
                                pro.setCreaPor(usuarioLoginBean.getUsuario());
                                pro.setFlgEstudiante(programacionDsctoBean.getFlgEstudiante());
                                programacionDsctoService.insertarProgramacionDsctoVer2(pro, dualProgramacionBean.getTarget());

                                listaDetProDscto = programacionDsctoService.obtenerTodosPorUniNegDetalle(uniNeg);

                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else {
                                new MensajePrime().addInformativeMessagePer("Le falta Ingresar el monto o el porcentaje");
                            }
                        } else {
                            new MensajePrime().addInformativeMessagePer("Le falta Formar los paquetes");
                        }
                    } else {
                        new MensajePrime().addInformativeMessagePer("Le falta ingresar la cantidad de Programaciones");
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("Le falta ingresar el tipo de Valor");
                }
            } else {
                new MensajePrime().addInformativeMessagePer("Le falta ingresar la descripcion");
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarProgramacionDscto() {
        String pagina = null;
        try {
            if (pagina == null) {
                System.out.println("id: " + programacionDsctoBean.getIdProgramacionDscto());
                String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
                Integer idProgramacionDscto = programacionDsctoBean.getIdProgramacionDscto();
                ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();

                ProgramacionDsctoBean proDET = new ProgramacionDsctoBean();
                proDET.getUnidadNegocioBean().setUniNeg(uniNeg);
                proDET.setIdProgramacionDscto(idProgramacionDscto);
                proDET.setDescripcion(programacionDsctoBean.getDescripcion());
                proDET.setAnio(anio);
                proDET.setCantProgramaciones(programacionDsctoBean.getCantProgramaciones());
                proDET.getTipoValorBean().setIdCodigo(programacionDsctoBean.getTipoValorBean().getIdCodigo());
                proDET.setValorUnitario(programacionDsctoBean.getValorUnitario());
                proDET.setValor(programacionDsctoBean.getValorUnitario());
                proDET.setStatus(programacionDsctoBean.getStatus());
                proDET.setCreaPor(usuarioLoginBean.getUsuario());
                proDET.setFlgEstudiante(programacionDsctoBean.getFlgEstudiante());
                programacionDsctoService.modificarProgramacionDscto(proDET);

                DetProgramacionDsctoBean pro = new DetProgramacionDsctoBean();
                pro.getUnidadNegocioBean().setUniNeg(uniNeg);
                pro.getTipoValorBean().setIdCodigo(programacionDsctoBean.getTipoValorBean().getIdCodigo());
                pro.setValor(programacionDsctoBean.getValorUnitario());
                pro.setStatus(programacionDsctoBean.getStatus());
                pro.setCreaPor(usuarioLoginBean.getUsuario());
                pro.setModiPor(usuarioLoginBean.getUsuario());
                pro.getProgramacionDsctoBean().setIdProgramacionDscto(idProgramacionDscto);
                programacionDsctoService.modificarProDsctoVer2(pro, dualProgramacionBean.getTarget());
                listaDetProDscto = programacionDsctoService.obtenerTodosPorUniNegDetalle(uniNeg);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiar() {
        try {
            programacionDsctoBean = new ProgramacionDsctoBean();
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            listaProgramacionBean = new ArrayList<>();
            listaProgramacionBeanB= new ArrayList<>();
            ProgramacionBean prog = new ProgramacionBean();
            prog.getConceptoUniNegBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProgramacionBean = programacionService.obtenerProgramacionActivos(prog);
            dualProgramacionBean = new DualListModel<>(listaProgramacionBean, listaProgramacionBeanB);
            this.flgComboOrdinal = false;
            this.flgComboPorcentual = false;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(DetProgramacionDsctoBean bean) {
        try {
            System.out.println("id: " + bean.getProgramacionDsctoBean().getIdProgramacionDscto());
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer idProgramacionDscto = bean.getProgramacionDsctoBean().getIdProgramacionDscto();
            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
            ProgramacionService programacionService = BeanFactory.getProgramacionService();

            listaProgramacionBean = programacionService.obtenerProgramacionImput(uniNeg, idProgramacionDscto);
            listaProgramacionBeanB = programacionService.obtenerProgramacionOutput(uniNeg, idProgramacionDscto);
            dualProgramacionBean = new DualListModel<>(listaProgramacionBean, listaProgramacionBeanB);
            ProgramacionDsctoBean pro = new ProgramacionDsctoBean();
            pro.getUnidadNegocioBean().setUniNeg(uniNeg);
            pro.setIdProgramacionDscto(bean.getProgramacionDsctoBean().getIdProgramacionDscto());
            programacionDsctoBean = programacionDsctoService.obtenerProgramacionDsctoPorId(idProgramacionDscto, uniNeg);
            obtenerTipoValor();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerConceptoPorCategoria() {
//        try {
////            ConceptoService conceptoService = BeanFactory.getConceptoService();
////            listaConceptoBean = conceptoService.obtenerPorTipo(conceptoCategoriaBean);
//            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorCat(conceptoCategoriaBean.getIdConceptoCategoria(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerProgramacion() {
        try {
            System.out.println("entro 2");
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            programacionBean = new ProgramacionBean();
            programacionBean.getConceptoUniNegBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            listaProgramacionBean = programacionService.obtenerProgramacionPorUniNeg(programacionBean);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionActivos() {
        try {
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            listaProgramacionBean = programacionService.obtenerProgPorTipoActivos(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarProgramacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
//                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                programacionBean.setCreaPor(usuarioLoginBean.getUsuario());
                programacionBean.setAnio(programacionBean.getProcesoBean().getAnio());
                programacionBean.getConceptoUniNegBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                programacionService.insertarProgramacion(programacionBean);
                listaProgramacionBean = programacionService.obtenerProgramacionPorUniNeg(programacionBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarProgramacion();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarProgramacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                programacionBean.setModiPor(beanUsuarioSesion.getUsuario());
                programacionService.modificarProgramacion(programacionBean);
                listaProgramacionBean = programacionService.obtenerProgramacionPorUniNeg(programacionBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarProgramacion();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarProgramacion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                programacionService.eliminarProgramacion(programacionBean);
                listaProgramacionBean = programacionService.obtenerProgramacionPorUniNeg(programacionBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String cambiarEstado() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                if (programacionBean.getStatus()) {
                    programacionBean.setStatus(Boolean.FALSE);
                } else {
                    programacionBean.setStatus(Boolean.TRUE);
                }
                programacionService.cambiarEstado(programacionBean);
                listaProgramacionBean = programacionService.obtenerProgramacionPorUniNeg(programacionBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarProgramacion();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarProgramacion() {
        if (programacionBean.getIdProgramacion() != null) {
            modificarProgramacion();
        } else {
            insertarProgramacion();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            programacionBean = (ProgramacionBean) event.getObject();
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            programacionBean = programacionService.obtenerPrograPorId(programacionBean);
            tipoConceptoBean = programacionBean.getConceptoUniNegBean().getConceptoBean().getTipoConceptoBean();
            obtenerConceptoPorTipo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerProgramacion(Object programacion) {
        try {
            programacionBean = (ProgramacionBean) programacion;
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            programacionBean = programacionService.obtenerPrograPorId(programacionBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAforoAmbiente() {
        try {
            for (int i = 0; i < listaAmbienteBean.size(); i++) {
                if (programacionBean.getAmbienteBean().getIdAmbiente() != null
                        && listaAmbienteBean.get(i).getIdAmbiente() == programacionBean.getAmbienteBean().getIdAmbiente()) {
                    programacionBean.getAmbienteBean().setAforo(listaAmbienteBean.get(i).getAforo());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaConcepto(Integer a) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(tipoConceptoBean.getIdTipoConcepto(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaConceptoUniNegBean.size(); i++) {
                programacionBean.setPrecio(0.0);
                if (listaConceptoUniNegBean.get(i).getConceptoBean().getIdConcepto().toString().equals(a.toString())) {
                    programacionBean.setConceptoUniNegBean(listaConceptoUniNegBean.get(i));
                    programacionBean.setPrecio(listaConceptoUniNegBean.get(i).getImporte().doubleValue());
                    valor = 2;
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPeriodoProceso() {
        try {
            for (int i = 0; i < listaProcesoBean.size(); i++) {
                if (programacionBean.getProcesoBean().getIdProceso() != null
                        && listaProcesoBean.get(i).getIdProceso().toString().equals(programacionBean.getProcesoBean().getIdProceso().toString())) {
                    programacionBean.getProcesoBean().setAnio(listaProcesoBean.get(i).getAnio());
                    programacionBean.getProcesoBean().getCodigoBean().setIdCodigo(listaProcesoBean.get(i).getCodigoBean().getIdCodigo());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionRegistro() {
        try {
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            listaProgramacionSessionBean = new ArrayList<>();
            for (ProgramacionBean listaProgramacionBean1 : listaProgramacionBean) {
                if (listaProgramacionBean1.getFlgInscrito().equals(true)) {
                    //                if (listaProgramacionBean.get(i).getFlgInscrito().equals(true) && listaProgramacionBean.get(i).getProcesoBean().getCodigoBean().getIdCodigo().equals(12803)) {
                    ProgramacionBean progra = new ProgramacionBean();
                    progra.setIdProgramacion(listaProgramacionBean1.getIdProgramacion());
                    progra.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    progra = programacionService.obtenerPrograPorId(progra);
                    listaProgramacionSessionBean.add(progra);
//                    CursoTallerMB cursoTallerMB = (CursoTallerMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("cursoTallerMB");
//                    cursoTallerMB.setListaProgramacionBean(listaProgramacionSessionBean); 
                    break;
                } else {
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    //Metodos Getter y Setter

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
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

//    public List<ConceptoBean> getListaConceptoBean() {
//        if (listaConceptoBean == null) {
//            listaConceptoBean = new ArrayList<>();
//        }
//        return listaConceptoBean;
//    }
//    
//    public void setListaConceptoBean(List<ConceptoBean> listaConceptoBean) {
//        this.listaConceptoBean = listaConceptoBean;
//    }
    public List<ProcesoBean> getListaProcesoBean() {
        if (listaProcesoBean == null) {
            listaProcesoBean = new ArrayList<>();
        }
        return listaProcesoBean;
    }

    public void setListaProcesoBean(List<ProcesoBean> listaProcesoBean) {
        this.listaProcesoBean = listaProcesoBean;
    }

    public List<AmbienteBean> getListaAmbienteBean() {
        if (listaAmbienteBean == null) {
            listaAmbienteBean = new ArrayList<>();
        }
        return listaAmbienteBean;
    }

    public void setListaAmbienteBean(List<AmbienteBean> listaAmbienteBean) {
        this.listaAmbienteBean = listaAmbienteBean;
    }

//    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
//        if (listaCentroResponsabilidadBean == null) {
//            listaCentroResponsabilidadBean = new ArrayList<>();
//        }
//        return listaCentroResponsabilidadBean;
//    }
//
//    public void setListaCentroResponsabilidadBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
//        this.listaCentroResponsabilidadBean = listaCentroResponsabilidadBean;
//    }
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

//    public List<CodigoBean> getListTipoMoneda() {
//        if (listTipoMoneda == null) {
//            listTipoMoneda = new ArrayList<>();
//        }
//        return listTipoMoneda;
//    }
//
//    public void setListTipoMoneda(List<CodigoBean> listTipoMoneda) {
//        this.listTipoMoneda = listTipoMoneda;
//    }
    public List<GradoAcademicoBean> getListaGradoAcademico() {
        if (listaGradoAcademico == null) {
            listaGradoAcademico = new ArrayList<>();
        }
        return listaGradoAcademico;
    }

    public void setListaGradoAcademico(List<GradoAcademicoBean> listaGradoAcademico) {
        this.listaGradoAcademico = listaGradoAcademico;
    }

    public List<ProgramacionBean> getListaProgramacionSessionBean() {
        if (listaProgramacionSessionBean == null) {
            listaProgramacionSessionBean = new ArrayList<>();
        }
        return listaProgramacionSessionBean;
    }

    public void setListaProgramacionSessionBean(List<ProgramacionBean> listaProgramacionSessionBean) {
        this.listaProgramacionSessionBean = listaProgramacionSessionBean;
    }

//    public DualListModel<ProgramacionBean> getDualCentroResponsabilidadBean() {
//        if (dualCentroResponsabilidadBean == null) {
//            dualCentroResponsabilidadBean = new DualListModel<>();
//        }
//        return dualCentroResponsabilidadBean;
//    }
//
//    public void setDualCentroResponsabilidadBean(DualListModel<ProgramacionBean> dualCentroResponsabilidadBean) {
//        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
//    }
    public List<ProgramacionBean> getListaProgramacionBeanB() {
        if (listaProgramacionBeanB == null) {
            listaProgramacionBeanB = new ArrayList<>();
        }
        return listaProgramacionBeanB;
    }

    public void setListaProgramacionBeanB(List<ProgramacionBean> listaProgramacionBeanB) {
        this.listaProgramacionBeanB = listaProgramacionBeanB;
    }

    public DualListModel<ProgramacionBean> getDualProgramacionBean() {
        if (dualProgramacionBean == null) {
            dualProgramacionBean = new DualListModel<>();
        }
        return dualProgramacionBean;
    }

    public void setDualProgramacionBean(DualListModel<ProgramacionBean> dualProgramacionBean) {
        this.dualProgramacionBean = dualProgramacionBean;
    }

    public ProgramacionDsctoBean getProgramacionDsctoBean() {
        if (programacionDsctoBean == null) {
            System.err.println("entroooooó");
            programacionDsctoBean = new ProgramacionDsctoBean();
        }
        return programacionDsctoBean;
    }

    public void setProgramacionDsctoBean(ProgramacionDsctoBean programacionDsctoBean) {
        this.programacionDsctoBean = programacionDsctoBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
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

    public Boolean getFlgComboPorcentual() {
        return flgComboPorcentual;
    }

    public void setFlgComboPorcentual(Boolean flgComboPorcentual) {
        this.flgComboPorcentual = flgComboPorcentual;
    }

    public Boolean getFlgComboOrdinal() {
        return flgComboOrdinal;
    }

    public void setFlgComboOrdinal(Boolean flgComboOrdinal) {
        this.flgComboOrdinal = flgComboOrdinal;
    }

    public List<DetProgramacionDsctoBean> getListaDetProDscto() {
        if (listaDetProDscto == null) {
            listaDetProDscto = new ArrayList<>();
        }
        return listaDetProDscto;
    }

    public void setListaDetProDscto(List<DetProgramacionDsctoBean> listaDetProDscto) {
        this.listaDetProDscto = listaDetProDscto;
    }

}
