package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
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
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.dao.DocIngresoDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class ImpresionMasivaComprobanteMB extends BaseMB implements Serializable {

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
//            getListaMesBusqueda();  
            obtenerListaMeses();
//            autenticarCajero();
            obtenerImpresora();
            getMatriculaFiltroBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            getMatriculaFiltroBean().setFlgMatriculaVista(1);
            getMatriculaFiltroAfterBean().setAnio(Calendar.getInstance().get(Calendar.YEAR));
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            listaNivelAcademicoAfterBean = getListaNivelAcademicoBean();

            getMatriculaFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            getMatriculaFiltroAfterBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            getListaMesAll();
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
            setFlgTodosAfter(Boolean.TRUE);
            setFlgTodos(Boolean.TRUE);
            getMatriculaFiltroAfterBean();
            getMatriculaFiltroAfterBean().setTipoImpresion(0);
            getMatriculaFiltroAfterBean().setFlgMasivo(null);
            getMatriculaFiltroAfterBean().setFlgMatriculaVista(1);
//            getMatriculaFiltroAfterBean().setFlgMatricula(true);

            getMatriculaFiltroBean();
//            getMatriculaFiltroBean().setFlgMatricula(true);

            getFormatoRecibo();
            setFormatoRecibo(1);
            getListaImpresoraBean();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }
    private List<EstudianteBean> listaEstudianteBean;
    private Boolean flgTodos;
    private Boolean flgSinNumeroDoc = false;
    private Boolean flgEstComprobanteMes;
    private Boolean flgEstEsp;
    private Boolean flgPorNivelGrado;
    private MatriculaBean matriculaFiltroBean;
    private List<Integer> listaAnioFiltroMatricula;
    private List<MesBean> listaMesBusqueda;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<NivelAcademicoBean> listaNivelAcademicoAfterBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroAfterBean;
    private Boolean flgEstEspMatricula;
    private List<MatriculaBean> listaMatriculaEstudianteMasivoBean;
    private List<MatriculaBean> listaMatriculaEstudianteMasivoAfterBean;
    private List<MatriculaBean> listaMatriculaEstudianteMasivoImpBean;
    private UsuarioBean usuarioLogin;

    private Boolean flgTodosAfter;
    private Boolean flgPorNivelGradoAfter;
    private Boolean flgEstEspAfter;
    private MatriculaBean matriculaFiltroAfterBean;
    private List<Integer> listaMesSel;
    private Integer mesSelect;
    private Integer mesSelectBuscador;
    private List<MesBean> listaMesAll;
    private ImpresoraBean impresoraBean;
    private List<ImpresoraBean> listaImpresoraBean;
    private Integer siImprimir;
    private Integer noImprimir;

    //CAMBIO
    private DocIngresoBean docIngresoBean;
    private List<DocIngresoBean> listaDocIngresoBean;
    private List<DocIngresoBean> listaDocIngRec;
    private UsuarioBean usuarioLoginBean;

    //Lógica de Negocio
    //AYUDA
    private String flgMasivo = "n";
    private Integer totalAnulados = 0;
    private Integer formatoRecibo;

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                this.flgSinNumeroDoc = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaMeses() {
        try {
            getListaMesBusqueda();
            listaMesBusqueda.add(new MesBean(2, MaristaConstantes.FEBRERO));
            listaMesBusqueda.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMesBusqueda.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMesBusqueda.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMesBusqueda.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMesBusqueda.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMesBusqueda.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMesBusqueda.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMesBusqueda.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMesBusqueda.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMesBusqueda.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroSinNroDoc() {
        try {
            if (this.flgSinNumeroDoc == true) {
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                this.flgTodos = false;
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
            if (this.flgTodosAfter) {
                this.flgPorNivelGradoAfter = false;
                this.flgEstEspAfter = false;
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
            if (this.flgEstEspAfter) {
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
            if (this.flgPorNivelGradoAfter) {
                this.flgTodosAfter = false;
                this.flgEstEspAfter = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null); 
                listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteGenerarRecibo(matriculaFiltroBean);
            } else {
                if (flgSinNumeroDoc == true) {
                    matriculaFiltroBean.setMes(mesSelectBuscador);
                    listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteGenerarSinRecibo(matriculaFiltroBean);
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
                        listaMatriculaEstudianteMasivoBean = matriculaService.obtenerFiltroEstudianteGenerarRecibo(matriculaFiltroBean);
                        if (listaMatriculaEstudianteMasivoBean.isEmpty()) {
                            new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                            listaMatriculaEstudianteMasivoBean = new ArrayList<>();
                        }
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroEstudianteMasivoAfter() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodosAfter == true) {
                if (!matriculaFiltroAfterBean.getFlgMasivo().equals("") && matriculaFiltroAfterBean.getFlgMasivo() != null) {
                    matriculaFiltroAfterBean.setFlgMasivo(getMatriculaFiltroAfterBean().getFlgMasivo());
                    if (matriculaFiltroAfterBean.getFlgMasivo().equals("b")) {
                        matriculaFiltroAfterBean.setFlgMas(1);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("g")) {
                        matriculaFiltroAfterBean.setFlgMas(2);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("c")) {
                        matriculaFiltroAfterBean.setFlgMas(3);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("d")) {
                        matriculaFiltroAfterBean.setFlgMas(4);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("a")) {
                        matriculaFiltroAfterBean.setFlgMas(5);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("s")) {
                        matriculaFiltroAfterBean.setFlgMas(6);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("ca")) {
                        matriculaFiltroAfterBean.setFlgMas(7);
                    }
                    estado = 1;
                }
                matriculaFiltroAfterBean.setAnioFin(null);
                if (estado == 1) {
                    listaMatriculaEstudianteMasivoAfterBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoAfter(matriculaFiltroAfterBean);
                    if (listaMatriculaEstudianteMasivoAfterBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoAfterBean = new ArrayList<>();
                    }
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
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
                if (matriculaFiltroAfterBean.getMes() != null) {
                    matriculaFiltroAfterBean.setMes(matriculaFiltroAfterBean.getMes());
                    estado = 1;
                }
                if (!matriculaFiltroAfterBean.getFlgMasivo().equals("") && matriculaFiltroAfterBean.getFlgMasivo() != null) {
                    matriculaFiltroAfterBean.setFlgMasivo(getMatriculaFiltroAfterBean().getFlgMasivo());
                    if (matriculaFiltroAfterBean.getFlgMasivo().equals("b")) {
                        matriculaFiltroAfterBean.setFlgMas(1);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("g")) {
                        matriculaFiltroAfterBean.setFlgMas(2);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("c")) {
                        matriculaFiltroAfterBean.setFlgMas(3);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("d")) {
                        matriculaFiltroAfterBean.setFlgMas(4);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("a")) {
                        matriculaFiltroAfterBean.setFlgMas(5);
                    } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("s")) {
                        matriculaFiltroAfterBean.setFlgMas(6);
                    }
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
                    matriculaFiltroAfterBean.setMes(mesSelect);
                    listaMatriculaEstudianteMasivoAfterBean = matriculaService.obtenerFiltroEstudianteImpCompMasivoAfter(matriculaFiltroAfterBean);
//                    List<CronogramaPagoBean> listaCronogramaPagoBean = new ArrayList<>();
//                    CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
//                    listaCronogramaPagoBean = cronogramaPagoService.obtenerCronogramaPago(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroAfterBean.getAnio());
//                    createDynamicColumns(listaCronogramaPagoBean);
                    if (listaMatriculaEstudianteMasivoAfterBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudianteMasivoAfterBean = new ArrayList<>();
                    }
                } else {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
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

    public void obtenerGradoAcaBasicaAfter() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroAfterBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroAfterBean.getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public String generaComprobanteMasivo() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                if (impresoraBean.getImpresora() != null) {
                    System.out.println("recibo: " + impresoraBean.getIdTipoDoc().getIdCodigo());
                    if (!getListaMesSel().isEmpty()) {
                        DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                        Integer anio = matriculaFiltroBean.getAnio();
                        docIngresoService.generaComprobanteMasivo(getListaMatriculaEstudianteMasivoBean(), getListaMesSel(), usuarioLogin.getUsuario(), impresoraBean, anio);
                        List<DocIngresoBean> listDocIngreso = new ArrayList<>();
                        String uniNeg = listaMatriculaEstudianteMasivoBean.get(0).getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg();
                        listDocIngreso = docIngresoService.obtenerDocIngresosSinNroDoc(uniNeg, anio);
                        for (DocIngresoBean docIngreso : listDocIngreso) {
                            docIngreso.setSerie(impresoraBean.getSerie());
                            docIngreso.setNroDoc(impresoraBean.getActual().toString());
                            docIngreso.getUnidadNegocioBean().setUniNeg(uniNeg);
                            ImpresoraCajaBean impresoraCajaBean = new ImpresoraCajaBean();
                            if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
                                impresoraCajaBean.setImpresora(impresoraBean);
                                impresoraCajaBean.getImpresora().setActual(impresoraBean.getActual() + 1);
                                ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                                impresoraService.cambiarNro(impresoraBean);
                            }
                            docIngreso.setImpresoraCajaBean(impresoraCajaBean);
                            docIngresoService.ponerNroDoc(docIngreso);
                        }

                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjReqMes");
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjReqImp");
                }

            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return pagina;
    }

    public String obtenerImpresora() {
        String pagina = null;
        try {

            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            listaImpresoraBean = new ArrayList<>();
            System.out.println("unineg " + usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaImpresoraBean = impresoraService.obtenerImpPensiones();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            pagina = null;
        }
        return pagina;
    }

    public String autenticarCajero() {
        String pagina = null;
        try {
            CajaGenBean cajaGenBean = new CajaGenBean();
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            cajeroCajaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuarioLogin);
////-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//            System.out.println("ip-cliente" + remoteAddress);

//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("ip-servidor" + localHost.getHostAddress());
            cajeroCajaBean.getCajaBean().setHostIp(localHost.getHostAddress());
            cajeroCajaBean = cajeroService.autenticarUsuarioConCaja(cajeroCajaBean);
            listaImpresoraBean = new ArrayList<>();
            if (cajeroCajaBean != null) {
                SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
                String dateCompleto = formatoDiaCompleto.format(new Date());
                cajaGenBean.setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCajaBean(cajeroCajaBean.getCajaBean());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                cajaGenBean.setAnio(Integer.parseInt(date));
                cajaGenBean.setUsuarioBean(usuarioLogin);
                cajaGenBean.setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarApertura(cajaGenBean);
//                if (cajaGeneral != null) {
//                    System.out.println("idCajaGen: "+cajaGeneral.getIdCajaGen());
//                    cajaGenBean = cajaGeneral;
//                }
                if (cajaGeneral == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
//                    new MensajePrime().addInformativeMessage("jejejeje");
                } else if (cajaGeneral.getFecCierre() != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeYaCerrada", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                } else {
                    String rutaCajero = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/mantenimientos/mantCobranza.xhtml");
                    new MaristaUtils().sesionColocarObjeto("ruta_cajero", rutaCajero);
                    pagina = "toRoot";
                    listaImpresoraBean = docIngresoService.obtenerImpresoraCajero(cajeroCajaBean);
//                    if (listaImpresoraBean.size() == 1) {
//                        getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
//                        obtenerTipDoc();
//                    }
                }
            } else {
                FacesContext fc = FacesContext.getCurrentInstance();
                ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();
                nav.performNavigation("logOut");
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("errorCajero", null));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            pagina = null;
        }
        return pagina;
    }

    public void traerImrpesora() {
        try {
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            impresoraBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            impresoraBean.setIdTipoDoc(new CodigoBean(MaristaConstantes.COD_DOC_REC));
            String impresora = impresoraBean.getImpresora();
            impresoraBean = impresoraService.buscarPorId(impresoraBean);
            if (impresoraBean == null) {
                new MensajePrime().addInformativeMessagePer("etiquetaConfImpRec");
                impresoraBean = new ImpresoraBean();
                impresoraBean.setImpresora(impresora);
                impresoraBean.setActual(null);
                impresoraBean.setSerie(null);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void confirmaImpresion() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                if (listaMatriculaEstudianteMasivoAfterBean != null && !listaMatriculaEstudianteMasivoAfterBean.isEmpty()) {
                    if (mesSelect != null && mesSelect != 0) {
                        siImprimir = 0;
                        noImprimir = 0;
                        for (int i = 0; i < listaMatriculaEstudianteMasivoAfterBean.size(); i++) {
                            for (int j = 0; j < listaMatriculaEstudianteMasivoAfterBean.get(i).getListaCuentas().size(); j++) {
//                                System.out.println("mes: "+mesSelect+" : "+listaMatriculaEstudianteMasivoAfterBean.get(i).getListaCuentas().get(j));
                                if (Objects.equals(mesSelect, listaMatriculaEstudianteMasivoAfterBean.get(i).getListaCuentas().get(j))) {
                                    getListaMatriculaEstudianteMasivoImpBean().add(listaMatriculaEstudianteMasivoAfterBean.get(i));
                                    siImprimir++;
                                    break;
                                } else {
                                    if (j == listaMatriculaEstudianteMasivoAfterBean.get(i).getListaCuentas().size() - 1) {
                                        noImprimir++;
                                    }
                                }
                            }
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjSelMes");
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjSelMatriculados");
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

//    public void generaImpresioManual() {
//        try {
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            List<ReciboPensionRepBean> listaReciboPensionRepBean = docIngresoService.obtenerReporteMasivo(listaMatriculaEstudianteMasivoImpBean, mesSelect, matriculaFiltroAfterBean.getFlgMasivo(), matriculaFiltroAfterBean.getFlgMas());
//            if (!listaReciboPensionRepBean.isEmpty()) {
//                PrinterMatrix printer = new PrinterMatrix();
//                Extenso e = new Extenso();
//                e.setNumber(120);
//                printer.setOutSize(35, 100);
//                for (ReciboPensionRepBean recibo : listaReciboPensionRepBean) {
//                    //Formato Recibo
//                    //Primera Línea
//                    printer.printTextWrap(8, 9, 11, 45, recibo.getDiscente());
//                    printer.printTextWrap(8, 9, 57, 87, recibo.getDiscenteCop());
//
//                    //Segunda Línea
//                    printer.printTextWrap(10, 11, 6, 14, recibo.getCodigoColegio());
//                    printer.printTextWrap(10, 11, 15, 20, recibo.getGrado());
//                    printer.printTextWrap(10, 11, 21, 29, recibo.getSeccion());
//                    printer.printTextWrap(10, 11, 33, 34, "x");
//                    printer.printTextWrap(10, 11, 43, 44, "x");
//                    printer.printTextWrap(11, 12, 33, 34, "x");
//                    printer.printTextWrap(11, 12, 43, 44, "x");
//
//                    printer.printTextWrap(10, 11, 52, 59, recibo.getCodigoColegioCop());
//                    printer.printTextWrap(10, 11, 60, 66, recibo.getGradoCop());
//                    printer.printTextWrap(10, 11, 67, 75, recibo.getSeccionCop());
//                    printer.printTextWrap(10, 11, 78, 80, "x");
//                    printer.printTextWrap(10, 11, 88, 90, "x");
//                    printer.printTextWrap(11, 12, 78, 80, "x");
//                    printer.printTextWrap(11, 12, 88, 90, "x");
//
//                    //Tercera Línea
//                    printer.printTextWrap(16, 15, 6, 15, recibo.getCorrespondientea());
//                    printer.printTextWrap(16, 15, 16, 26, recibo.getVencimiento());
//                    printer.printTextWrap(16, 15, 27, 35, recibo.getNombreBeca());
//
//                    printer.printTextWrap(16, 15, 52, 61, recibo.getCorrespondienteaCop());
//                    printer.printTextWrap(16, 15, 62, 72, recibo.getVencimientoCop());
//                    printer.printTextWrap(16, 15, 73, 81, recibo.getNombreBecaCop());
//
//                    //Cuarta Línea
//                    printer.printTextWrap(21, 20, 6, 14, recibo.getCuentad().toString());
//                    printer.printTextWrap(21, 20, 15, 44, recibo.getNombreConcepto());
//                    printer.printTextWrap(21, 20, 45, 52, recibo.getMonto());
//
//                    printer.printTextWrap(24, 23, 6, 14, recibo.getCuentaDsctoBeca().toString());
//                    printer.printTextWrap(24, 23, 15, 44, recibo.getLabelDsctoBeca());
//                    printer.printTextWrap(24, 23, 45, 52, recibo.getDsctoBeca());
//
//                    printer.printTextWrap(21, 20, 55, 63, recibo.getCuentadCop().toString());
//                    printer.printTextWrap(21, 20, 64, 93, recibo.getNombreConceptoCop());
//                    printer.printTextWrap(21, 20, 94, 101, recibo.getMontoCop());
//
//                    printer.printTextWrap(24, 23, 55, 63, recibo.getCuentaDsctoBecaCop().toString());
//                    printer.printTextWrap(24, 23, 64, 93, recibo.getLabelDsctoBecaCop());
//                    printer.printTextWrap(24, 23, 94, 101, recibo.getDsctoBecaCop());
//
//                    //Quinta Línea
//                    printer.printTextWrap(28, 27, 6, 40, recibo.getEmision());
//                    printer.printTextWrap(28, 27, 41, 48, recibo.getMontoTotal());
//
//                    printer.printTextWrap(28, 27, 51, 85, recibo.getEmisionCop());
//                    printer.printTextWrap(28, 27, 86, 94, recibo.getMontoTotalCop());
//                    printer.toFile("impresion.txt");
//                    FileInputStream inputStream = null;
//                }
//                //End Recibo
//                printer.toFile("impresion.txt");
//                FileInputStream inputStream = null;
//                try {
//                    inputStream = new FileInputStream("impresion.txt");
//                    int content = 0;
//                    while ((content = inputStream.read()) != -1) {
//                        System.out.print((char) content);
//                    }
//                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
//                }
//                if (inputStream == null) {
//                    return;
//                }
//                DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc document = new SimpleDoc(inputStream, docFormat, null);
//                PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
//                PrintService ps[] = PrintServiceLookup.lookupPrintServices(docFormat, attributeSet);
//                PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
//                PrintService service = ServiceUI.printDialog(null, 200, 200, ps, defaultPrintService, docFormat, attributeSet);
//                if (defaultPrintService != null) {
//                    DocPrintJob printJob = defaultPrintService.createPrintJob();
//                    try {
//                        inputStream = new FileInputStream("impresion.txt");
//                    } catch (FileNotFoundException ex) {
//                        printJob.print(document, attributeSet);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//                //End Recibo 
//            } else {
//                new MensajePrime().addInformativeMessagePer("msjSelMatriculados");
//            }
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//    }
//    public void generaImpresioManual() {
//        try {
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            List<ReciboPensionRepBean> listaReciboPensionRepBean = docIngresoService.obtenerReporteMasivo(listaMatriculaEstudianteMasivoImpBean, mesSelect, matriculaFiltroAfterBean.getFlgMasivo(), matriculaFiltroAfterBean.getFlgMas());
//            if (!listaReciboPensionRepBean.isEmpty()) {
//                PrinterMatrix printer = new PrinterMatrix();
//                Extenso e = new Extenso();
//                e.setNumber(120);
//                printer.setOutSize(60, 100);
//                for (ReciboPensionRepBean recibo : listaReciboPensionRepBean) {
//                    //Formato Recibo
//                    //Primera Línea
//                    printer.printTextWrap(8, 9, 11, 45, recibo.getDiscente());
//                    printer.printTextWrap(8, 9, 57, 87, recibo.getDiscenteCop());
//
//                    //Segunda Línea
//                    printer.printTextWrap(10, 11, 6, 14, recibo.getCodigoColegio());
//                    printer.printTextWrap(10, 11, 15, 20, recibo.getGrado());
//                    printer.printTextWrap(10, 11, 21, 29, recibo.getSeccion());
//                    printer.printTextWrap(10, 11, 33, 34, "x");
//                    printer.printTextWrap(10, 11, 43, 44, "x");
//                    printer.printTextWrap(11, 12, 33, 34, "x");
//                    printer.printTextWrap(11, 12, 43, 44, "x");
//
//                    printer.printTextWrap(10, 11, 52, 59, recibo.getCodigoColegioCop());
//                    printer.printTextWrap(10, 11, 60, 66, recibo.getGradoCop());
//                    printer.printTextWrap(10, 11, 67, 75, recibo.getSeccionCop());
//                    printer.printTextWrap(10, 11, 78, 80, "x");
//                    printer.printTextWrap(10, 11, 88, 90, "x");
//                    printer.printTextWrap(11, 12, 78, 80, "x");
//                    printer.printTextWrap(11, 12, 88, 90, "x");
//
//                    //Tercera Línea
//                    printer.printTextWrap(16, 15, 6, 15, recibo.getCorrespondientea());
//                    printer.printTextWrap(16, 15, 16, 26, recibo.getVencimiento());
//                    printer.printTextWrap(16, 15, 27, 35, recibo.getNombreBeca());
//
//                    printer.printTextWrap(16, 15, 52, 61, recibo.getCorrespondienteaCop());
//                    printer.printTextWrap(16, 15, 62, 72, recibo.getVencimientoCop());
//                    printer.printTextWrap(16, 15, 73, 81, recibo.getNombreBecaCop());
//
//                    //Cuarta Línea
//                    printer.printTextWrap(21, 20, 6, 14, recibo.getCuentad().toString());
//                    printer.printTextWrap(21, 20, 15, 40, recibo.getNombreConcepto());
//                    printer.printTextWrap(21, 20, 41, 48, recibo.getMonto());
//
//                    printer.printTextWrap(24, 23, 6, 14, recibo.getCuentaDsctoBeca().toString());
//                    printer.printTextWrap(24, 23, 15, 40, recibo.getLabelDsctoBeca());
//                    printer.printTextWrap(24, 23, 41, 48, recibo.getDsctoBeca());
//
//                    printer.printTextWrap(21, 20, 51, 59, recibo.getCuentadCop().toString());
//                    printer.printTextWrap(21, 20, 60, 85, recibo.getNombreConceptoCop());
//                    printer.printTextWrap(21, 20, 86, 94, recibo.getMontoCop());
//
//                    printer.printTextWrap(24, 23, 51, 59, recibo.getCuentaDsctoBecaCop().toString());
//                    printer.printTextWrap(24, 23, 60, 85, recibo.getLabelDsctoBecaCop());
//                    printer.printTextWrap(24, 23, 86, 94, recibo.getDsctoBecaCop());
//
//                    //Quinta Línea
//                    printer.printTextWrap(28, 27, 6, 40, recibo.getEmision());
//                    printer.printTextWrap(28, 27, 41, 48, recibo.getMontoTotal());
//
//                    printer.printTextWrap(28, 27, 51, 85, recibo.getEmisionCop());
//                    printer.printTextWrap(28, 27, 86, 94, recibo.getMontoTotalCop());
//                }
//                //End Recibo
//                printer.toFile("impresion.txt");
//                FileInputStream inputStream = null;
//                try {
//                    inputStream = new FileInputStream("impresion.txt");
//                    int content = 0;
//                    while ((content = inputStream.read()) != -1) {
//                        System.out.print((char) content);
//                    }
//                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
//                }
//                if (inputStream == null) {
//                    return;
//                }
//                DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc document = new SimpleDoc(inputStream, docFormat, null);
//                PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
//                PrintService ps[] = PrintServiceLookup.lookupPrintServices(docFormat, attributeSet);
//                PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
//                PrintService service = ServiceUI.printDialog(null, 200, 200, ps, defaultPrintService, docFormat, attributeSet);
//                if (defaultPrintService != null) {
//                    DocPrintJob printJob = defaultPrintService.createPrintJob();
//                    try {
////                        printJob.print(document, attributeSet);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                } else {
//                    System.err.println("No existen impresoras instaladas");
//                }
//            } else {
//                new MensajePrime().addInformativeMessagePer("msjSelMatriculados");
//            }
//        } catch (Exception e) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), e);
//        }
//    }
    public void generarImpresion() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//                List<ReciboPensionRepBean> listaReciboPensionRepBean = docIngresoService.obtenerReporteMasivoIngreso(listaMatriculaEstudianteMasivoImpBean, mesSelect, matriculaFiltroAfterBean.getFlgMasivo(), matriculaFiltroAfterBean.getFlgMas());
                //OBTENIENDO CABECERA DE RECIBO
                System.out.println(">>>>>>" + matriculaFiltroAfterBean.getFlgMas() + " /// " + matriculaFiltroAfterBean.getFlgMasivo());
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("CHAMPS")) {
                    listaDocIngresoRep = docIngresoService.obtenerReporteMasivoIng(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroAfterBean.getAnio(), mesSelect, matriculaFiltroAfterBean.getFlgMasivo(), matriculaFiltroAfterBean.getFlgMas(), matriculaFiltroAfterBean);
                } else {
                    listaDocIngresoRep = docIngresoService.obtenerReporteMasivoIng(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroAfterBean.getAnio(), mesSelect, matriculaFiltroAfterBean.getFlgMasivo(), matriculaFiltroAfterBean.getFlgMas(), matriculaFiltroAfterBean);
                }
                //OBTENIENDO DETALLE DE RECIBO
                DocIngresoBean doc = new DocIngresoBean();
                doc.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                //MODIFICAR doc.setSerie(MaristaConstantes.serie_numdoc); Y TIPODOC
                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                    doc.setSerie(MaristaConstantes.serie_numdoc_1);
                } else {
                    doc.setSerie(MaristaConstantes.serie_numdoc);
                }
                if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("CHAMPS")) {
                    doc.setSerie("003");
                }
                Integer numDoc = docIngresoService.obtenerMaxNroDoc(doc), var = 0;
                if (!listaDocIngresoRep.isEmpty()) {
                    //VALIDANDO RECIBOS IMPRESOS / NO IMPRESOS
                    if (matriculaFiltroAfterBean.getTipoImpresion().equals(2)) {
                        /* ACTUALIZA DOCINGRESO */
                        for (int j = 0; j < listaDocIngresoRep.size(); j++) {
                            if (listaDocIngresoRep.get(j).getIdDocIngreso().equals(11661)) {
                                System.out.println(">>>>>" + listaDocIngresoRep.get(j).getMora());
                                System.out.println(">>>>>" + listaDocIngresoRep.get(j).getDscto());
                            }
                            var++;
                            Integer nrodoc = numDoc + var;
                            DocIngresoBean docIngresoBean = new DocIngresoBean();
                            docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            docIngresoBean.setIdDocIngreso(listaDocIngresoRep.get(j).getIdDocIngreso());
                            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                                docIngresoBean.setSerie(MaristaConstantes.serie_numdoc_1);
                            } else {
                                docIngresoBean.setSerie(MaristaConstantes.serie_numdoc);
                            }

                            docIngresoBean.setFlgImpresion(2);
                            docIngresoBean.setNroDoc(nrodoc.toString());
                            docIngresoBean.getIdTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                            docIngresoService.modificarNroDocSerie(docIngresoBean);
                            listaDocIngresoRep.get(j).setSerie(docIngresoBean.getSerie());
                            listaDocIngresoRep.get(j).setNroDoc(docIngresoBean.getNroDoc());
                            String formatString = String.format("%%0%dd", 7);
                            String formattedString = String.format(formatString, Integer.parseInt(docIngresoBean.getNroDoc()));
                            listaDocIngresoRep.get(j).setSerieNroDoc(docIngresoBean.getSerie().concat(" - " + formattedString));
                        }
                    }
                    //Obteniendo Recibos
                    if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                        List<Integer> lista = new ArrayList<>();
                        for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                            lista.add(listaDocIngresoRep.get(i).getIdDocIngreso());
                        }
                        System.out.println("size ids" + lista.size());

                        listaDocIngresoRep = new ArrayList<>();
                        listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoFor(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista);
                        System.out.println("size " + listaDocIngresoRep.size());
                        if (!listaDocIngresoRep.isEmpty()) {
                            for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                                System.out.println("Nro: " + i + " de " + listaDocIngresoRep.size() + " /qr" + listaDocIngresoRep.get(i).getQr());
                                List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                                //SIN MMORA
                                listaDocIngresoRep.get(i).setMora(0);
                                listaDocIngresoRep.get(i).setDscto(0);
                                listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca());
                                listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                            }
                        }
                    } else {
                        for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                            List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                            listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngresoMas(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca(), listaDocIngresoRep.get(i).getInfoMonto());
                            listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                        }
                    }
                    // CAMBIO DE RECIBO
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    String archivoJasper = "";
//                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
//                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecSANJOCA5.jasper");
                    System.out.println(">>>>" + getFormatoRecibo());
                    if (getFormatoRecibo().equals(1)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecSANJOCA5.jasper");
                        if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                            archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS_v2.jasper");
                        }
                    } else if (getFormatoRecibo().equals(3)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteSANJOCA5.jasper");
                    } else if (getFormatoRecibo().equals(2)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteUsuarioSANJOCA5.jasper");
                    }
                    if (matriculaFiltroAfterBean.getFlgMasivo().equals("d")) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteSANJOCA5.jasper");
                    }
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);

                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    parametros.put("SUBREPORT_DIR", ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "inline; filename=ReciboMasivo" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                    response.setHeader("Cache-Control", "cache, must-revalidate");
                    response.setHeader("Pragma", "public");
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                } else if (listaDocIngresoRep.isEmpty()) {
                    RequestContext.getCurrentInstance().addCallbackParam("sinrecibos", true);
                    System.out.println(">>>>" + "sinrecibos");
                }
            }
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
//    private void createDynamicColumns(List<CronogramaPagoBean> listaCronogramaPagoBean) {
//        columns = new ArrayList<>();   
//        for(CronogramaPagoBean bean : listaCronogramaPagoBean) {    
//                columns.add(new ColumnModel(bean.getDesMes().toUpperCase(), bean.getDesMes()));   
//        }
//    }

    public void anularRecibo() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalAnulados = docIngresoService.obtenerNumAnulados(docIngresoBean);
            System.out.println(">>>" + totalAnulados);
            if (totalAnulados != 0) {
                RequestContext.getCurrentInstance().addCallbackParam("anulados", true);
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void eliminarRecibo() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            Object valor = new Object();
            valor = docIngresoService.eliminarRecibo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), Integer.parseInt(docIngresoBean.getNroDocIni()), Integer.parseInt(docIngresoBean.getNroDocFin()), Integer.parseInt(docIngresoBean.getNroRecIni()), matriculaFiltroAfterBean.getAnio(), mesSelect, usuarioLogin.getUsuario());
            docIngresoBean = new DocIngresoBean();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void verNroRecibo(Object object) {
        try {
            MatriculaBean matriculaBean = (MatriculaBean) object;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            if (matriculaFiltroAfterBean.getFlgMasivo().equals("d")) {
                matriculaBean.setFlgMasivo("g");
            } else if (matriculaFiltroAfterBean.getFlgMasivo().equals("c")) {
                matriculaBean.setFlgMasivo(null);
            } else {
                matriculaBean.setFlgMasivo(matriculaFiltroAfterBean.getFlgMasivo());
            }
            listaDocIngRec = docIngresoService.obtenerRecibos(matriculaBean);
            System.out.println("lista >>>" + listaDocIngRec.size());
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public String generarReciboEstudiante(Object object) {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        try {
            if (pagina == null) {
                if (impresoraBean.getImpresora() != null) {
                    if (!getListaMesSel().isEmpty()) {
                        List<MatriculaBean> listaMatricula = new ArrayList<>();
                        MatriculaBean matriculaBean = (MatriculaBean) object;
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
                        listaMatricula.add(matriculaBean);
                        DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                        Integer anio = matriculaFiltroBean.getAnio();
                        docIngresoService.generaComprobanteMasivo(listaMatricula, getListaMesSel(), usuarioLogin.getUsuario(), impresoraBean, anio);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjReqMes");
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjReqImp");
                }

            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
        return pagina;
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

    public List<Integer> getListaMesSel() {
        if (listaMesSel == null) {
            listaMesSel = new ArrayList<>();
        }
        return listaMesSel;
    }

    public void setListaMesSel(List<Integer> listaMesSel) {
        this.listaMesSel = listaMesSel;
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

    public ImpresoraBean getImpresoraBean() {
        if (impresoraBean == null) {
            impresoraBean = new ImpresoraBean();
        }
        return impresoraBean;
    }

    public void setImpresoraBean(ImpresoraBean impresoraBean) {
        this.impresoraBean = impresoraBean;
    }

    public List<ImpresoraBean> getListaImpresoraBean() {
        if (listaImpresoraBean == null) {
            listaImpresoraBean = new ArrayList<>();
        }
        return listaImpresoraBean;
    }

    public void setListaImpresoraBean(List<ImpresoraBean> listaImpresoraBean) {
        this.listaImpresoraBean = listaImpresoraBean;
    }

    public Integer getMesSelect() {
        return mesSelect;
    }

    public void setMesSelect(Integer mesSelect) {
        this.mesSelect = mesSelect;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoAfterBean() {
        if (listaNivelAcademicoAfterBean == null) {
            listaNivelAcademicoAfterBean = new ArrayList<>();
        }
        return listaNivelAcademicoAfterBean;
    }

    public void setListaNivelAcademicoAfterBean(List<NivelAcademicoBean> listaNivelAcademicoAfterBean) {
        this.listaNivelAcademicoAfterBean = listaNivelAcademicoAfterBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroAfterBean() {
        if (listaGradoAcademicoFiltroAfterBean == null) {
            listaGradoAcademicoFiltroAfterBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroAfterBean;
    }

    public void setListaGradoAcademicoFiltroAfterBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroAfterBean) {
        this.listaGradoAcademicoFiltroAfterBean = listaGradoAcademicoFiltroAfterBean;
    }

    public Integer getSiImprimir() {
        return siImprimir;
    }

    public void setSiImprimir(Integer siImprimir) {
        this.siImprimir = siImprimir;
    }

    public Integer getNoImprimir() {
        return noImprimir;
    }

    public void setNoImprimir(Integer noImprimir) {
        this.noImprimir = noImprimir;
    }

    public List<MatriculaBean> getListaMatriculaEstudianteMasivoImpBean() {
        if (listaMatriculaEstudianteMasivoImpBean == null) {
            listaMatriculaEstudianteMasivoImpBean = new ArrayList<>();
        }
        return listaMatriculaEstudianteMasivoImpBean;
    }

    public void setListaMatriculaEstudianteMasivoImpBean(List<MatriculaBean> listaMatriculaEstudianteMasivoImpBean) {
        this.listaMatriculaEstudianteMasivoImpBean = listaMatriculaEstudianteMasivoImpBean;
    }

    //AYUDA
    public String getFlgMasivo() {
        return flgMasivo;
    }

    public void setFlgMasivo(String flgMasivo) {
        this.flgMasivo = flgMasivo;
    }

    public DocIngresoBean getDocIngresoBean() {
        if (docIngresoBean == null) {
            docIngresoBean = new DocIngresoBean();
        }
        return docIngresoBean;
    }

    public void setDocIngresoBean(DocIngresoBean docIngresoBean) {
        this.docIngresoBean = docIngresoBean;
    }

    public List<DocIngresoBean> getListaDocIngresoBean() {
        if (listaDocIngresoBean == null) {
            listaDocIngresoBean = new ArrayList<>();
        }
        return listaDocIngresoBean;
    }

    public void setListaDocIngresoBean(List<DocIngresoBean> listaDocIngresoBean) {
        this.listaDocIngresoBean = listaDocIngresoBean;
    }

    public Integer getTotalAnulados() {
        return totalAnulados;
    }

    public void setTotalAnulados(Integer totalAnulados) {
        this.totalAnulados = totalAnulados;
    }

    public List<DocIngresoBean> getListaDocIngRec() {
        if (listaDocIngRec == null) {
            listaDocIngRec = new ArrayList<>();
        }
        return listaDocIngRec;
    }

    public void setListaDocIngRec(List<DocIngresoBean> listaDocIngRec) {
        this.listaDocIngRec = listaDocIngRec;
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

    public Integer getFormatoRecibo() {
        return formatoRecibo;
    }

    public void setFormatoRecibo(Integer formatoRecibo) {
        this.formatoRecibo = formatoRecibo;
    }

    public Boolean getFlgSinNumeroDoc() {
        return flgSinNumeroDoc;
    }

    public void setFlgSinNumeroDoc(Boolean flgSinNumeroDoc) {
        this.flgSinNumeroDoc = flgSinNumeroDoc;
    }

    public Integer getMesSelectBuscador() {
        return mesSelectBuscador;
    }

    public void setMesSelectBuscador(Integer mesSelectBuscador) {
        this.mesSelectBuscador = mesSelectBuscador;
    }

    public List<MesBean> getListaMesBusqueda() {
        if (listaMesBusqueda == null) {
            listaMesBusqueda = new ArrayList<>();
        }
        return listaMesBusqueda;
    }

    public void setListaMesBusqueda(List<MesBean> listaMesBusqueda) {
        this.listaMesBusqueda = listaMesBusqueda;
    }

}
