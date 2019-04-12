/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
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
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetEsquelaBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.DetEsquelaRepBean;
import pe.marista.sigma.bean.reporte.EnvioAlumno;
import pe.marista.sigma.bean.reporte.MasivoCartaUnoBean;
import pe.marista.sigma.bean.reporte.SubReporteMasivoCartaUno;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DetEsquelaService;
import pe.marista.sigma.service.EsquelaService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.Mailing;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class DetEsquelaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DetEsquelaMB
     */
    @PostConstruct
    public void DetEsquelaMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
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
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);
            getViewMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaCodigoBean();
            listaCodigoBean = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MATRICULA));

            getListaAnioMatricula();
            for (int i = MaristaConstantes.ANO_INI_DEFAULT_COLE; i <= miCalendario.get(Calendar.YEAR) + 3; i++) {
                listaAnioMatricula.add(i);
            }

            getListaAnioFiltroMatricula();
            for (int i = miCalendario.get(Calendar.YEAR) - 1; i <= miCalendario.get(Calendar.YEAR); i++) {
                listaAnioFiltroMatricula.add(i);
            }

            //fecha actual
            fechaActual = new GregorianCalendar();

            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            getListaEsquelaBean();
            listaEsquelaBean = esquelaService.obtenerEsquela(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMatriculaBean().setFechaMatricula(fechaActual.getTime());

            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            getFechaFull();
            fechaFull = detEsquelaService.obtenerTotalPorDia(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getListaDetEsquelaBean();
//            listaDetEsquelaBean = detEsquelaService.obtenerTodos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaDetEsquelaBean = detEsquelaService.obtenerDetalles(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaMesesForSup();
            getEsquelaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<ProgramacionBean> listaProgramacionBean;
    private List<CodigoBean> listaCodigoBean;
    private MatriculaBean matriculaBean;
    private ProgramacionBean programacionBean;
    private EstudianteBean estudianteBean;
    private boolean comodin;

    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBeanOk;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBeanFail;

    private UsuarioBean usuarioLogin;
    private List<ViewMatriculaBean> listaViewMatriculaBean;
    private ViewMatriculaBean viewMatriculaBean;

    private Calendar fechaActual;
    private Date fechaHoy;
    private String alerta;
    private String texto;

    //Lista Matricula
    private MatriculaBean matriculaFiltroBean;
    private List<MatriculaBean> listaMatriculaFlgFlaseBean;

    private List<Integer> listaAnioMatricula;
    private List<Integer> listaAnioFiltroMatricula;

    private List<NivelAcademicoBean> listaNivelAcademico;
    private Boolean flgTodos;
    private Boolean flgEstSinPro;
    private Boolean flgPorNivelGrado;
    private Boolean flgEstEsp;
    private Boolean valAdmTodos = true;
    private Boolean flgTodosMatriculados;
    private Boolean flgEstEspMatricula;
    private Boolean flgPa;
    private Boolean flgMa;
    private Boolean flgAp;
    private Boolean flgResPa;
    private Boolean flgOk;
    private Boolean flgFail;
    private Boolean mostrarFiltroSel = false;
    private Boolean mostrarBoton;

    //Esquela
    private EsquelaBean esquelaBean;
    private List<EsquelaBean> listaEsquelaBean;
    private Integer fechaFull = 0;
    private String datoFecha;

    private DetEsquelaBean detEsquelaBean;
    private DetEsquelaBean DetEsquelaFiltroBean;
    private List<DetEsquelaBean> listaDetEsquelaBean;
    private List<DetEsquelaBean> listaDetEsquelaOkBean;
    private List<DetEsquelaBean> listaDetEsquelaFailBean;
    private List<DetEsquelaBean> listaFiltroMen;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;

    //Ayuda
    private Boolean botonEnviar = true;
    private Boolean botonCancel = true;
    private Integer tipoEnvio;
    private Boolean envio = false;
    private Integer[] listaMeses;
    private Map<String, Integer> listaMesesExpMap;
    private List<CuentasPorCobrarBean> listaCuentas;
    private BigDecimal monto;
    private String asunto;
    private String mensaje;
    private String men;
    private String fecha;
    private Integer anio;
    private Boolean vista;
    private Boolean vistaCmd;

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
    }

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
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

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public Boolean getFlgTodosMatriculados() {
        return flgTodosMatriculados;
    }

    public void setFlgTodosMatriculados(Boolean flgTodosMatriculados) {
        this.flgTodosMatriculados = flgTodosMatriculados;
    }

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
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

    public List<Integer> getListaAnioFiltroMatricula() {
        if (listaAnioFiltroMatricula == null) {
            listaAnioFiltroMatricula = new ArrayList<>();
        }
        return listaAnioFiltroMatricula;
    }

    public void setListaAnioFiltroMatricula(List<Integer> listaAnioFiltroMatricula) {
        this.listaAnioFiltroMatricula = listaAnioFiltroMatricula;
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

    public UsuarioBean getUsuarioLogin() {
        if (usuarioLogin == null) {
            usuarioLogin = new UsuarioBean();
        }
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
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

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
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

    public DetEsquelaBean getDetEsquelaBean() {
        if (detEsquelaBean == null) {
            detEsquelaBean = new DetEsquelaBean();
        }
        return detEsquelaBean;
    }

    public void setDetEsquelaBean(DetEsquelaBean detEsquelaBean) {
        this.detEsquelaBean = detEsquelaBean;
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

    public List<DetEsquelaBean> getListaDetEsquelaOkBean() {
        if (listaDetEsquelaOkBean == null) {
            listaDetEsquelaOkBean = new ArrayList<>();
        }
        return listaDetEsquelaOkBean;
    }

    public void setListaDetEsquelaOkBean(List<DetEsquelaBean> listaDetEsquelaOkBean) {
        this.listaDetEsquelaOkBean = listaDetEsquelaOkBean;
    }

    public List<DetEsquelaBean> getListaDetEsquelaFailBean() {
        if (listaDetEsquelaFailBean == null) {
            listaDetEsquelaFailBean = new ArrayList<>();
        }
        return listaDetEsquelaFailBean;
    }

    public void setListaDetEsquelaFailBean(List<DetEsquelaBean> listaDetEsquelaFailBean) {
        this.listaDetEsquelaFailBean = listaDetEsquelaFailBean;
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

    public Boolean getFlgOk() {
        return flgOk;
    }

    public void setFlgOk(Boolean flgOk) {
        this.flgOk = flgOk;
    }

    public Boolean getFlgFail() {
        return flgFail;
    }

    public void setFlgFail(Boolean flgFail) {
        this.flgFail = flgFail;
    }

    public Integer getFechaFull() {
        return fechaFull;
    }

    public void setFechaFull(Integer fechaFull) {
        this.fechaFull = fechaFull;
    }

    public Boolean getMostrarFiltroSel() {
        return mostrarFiltroSel;
    }

    public void setMostrarFiltroSel(Boolean mostrarFiltroSel) {
        this.mostrarFiltroSel = mostrarFiltroSel;
    }

    public String getDatoFecha() {
        return datoFecha;
    }

    public void setDatoFecha(String datoFecha) {
        this.datoFecha = datoFecha;
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

    public DetEsquelaBean getDetEsquelaFiltroBean() {
        if (DetEsquelaFiltroBean == null) {
            DetEsquelaFiltroBean = new DetEsquelaBean();
        }
        return DetEsquelaFiltroBean;
    }

    public void setDetEsquelaFiltroBean(DetEsquelaBean DetEsquelaFiltroBean) {
        this.DetEsquelaFiltroBean = DetEsquelaFiltroBean;
    }

    public Boolean getMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(Boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public Boolean getBotonEnviar() {
        return botonEnviar;
    }

    public void setBotonEnviar(Boolean botonEnviar) {
        this.botonEnviar = botonEnviar;
    }

    public Boolean getBotonCancel() {
        return botonCancel;
    }

    public void setBotonCancel(Boolean botonCancel) {
        this.botonCancel = botonCancel;
    }

    public Integer getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(Integer tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
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

    public Integer[] getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Integer[] listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public List<CuentasPorCobrarBean> getListaCuentas() {
        if (listaCuentas == null) {
            listaCuentas = new ArrayList<>();
        }
        return listaCuentas;
    }

    public void setListaCuentas(List<CuentasPorCobrarBean> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<DetEsquelaBean> getListaFiltroMen() {
        if (listaFiltroMen == null) {
            listaFiltroMen = new ArrayList<>();
        }
        return listaFiltroMen;
    }

    public void setListaFiltroMen(List<DetEsquelaBean> listaFiltroMen) {
        this.listaFiltroMen = listaFiltroMen;
    }

    public String getMen() {
        return men;
    }

    public void setMen(String men) {
        this.men = men;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBeanOk() {
        if (listaMatriculaEstudiantesMasivosBeanOk == null) {
            listaMatriculaEstudiantesMasivosBeanOk = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBeanOk;
    }

    public void setListaMatriculaEstudiantesMasivosBeanOk(List<MatriculaBean> listaMatriculaEstudiantesMasivosBeanOk) {
        this.listaMatriculaEstudiantesMasivosBeanOk = listaMatriculaEstudiantesMasivosBeanOk;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBeanFail() {
        if (listaMatriculaEstudiantesMasivosBeanFail == null) {
            listaMatriculaEstudiantesMasivosBeanFail = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBeanFail;
    }

    public void setListaMatriculaEstudiantesMasivosBeanFail(List<MatriculaBean> listaMatriculaEstudiantesMasivosBeanFail) {
        this.listaMatriculaEstudiantesMasivosBeanFail = listaMatriculaEstudiantesMasivosBeanFail;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Boolean getVista() {
        return vista;
    }

    public void setVista(Boolean vista) {
        this.vista = vista;
    }

    public Boolean getVistaCmd() {
        return vistaCmd;
    }

    public void setVistaCmd(Boolean vistaCmd) {
        this.vistaCmd = vistaCmd;
    }

//    ================================================================================================================================
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

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgEstSinPro = false;
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

    public void verificarFiltroEstSinPro() {
        try {
            if (this.flgEstSinPro == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
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

    public void obtenerAnioMat() {
        try {
            getMatriculaFiltroBean().setAnioIni(getMatriculaFiltroBean().getAnioIni());
            setAnio(getMatriculaFiltroBean().getAnioIni());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizarAnioFinFiltroPorProgramacion() { //obtener el anio siguiente
        try {

            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            getListaProgramacionBean();
//            System.out.println(MaristaConstantes.COD_MATRICULA);
//            listaProgramacionBean = programacionService.obtenerPrograPorTipo(MaristaConstantes.COD_MATRICULA);
            listaProgramacionBean = programacionService.obtenerProgPorTipoPorAnioPorUniNeg(MaristaConstantes.TIP_COD_PROC, MaristaConstantes.COD_MATRICULA, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnioIni());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            if (matriculaFiltroBean.getDato() != null) {
                if (matriculaFiltroBean.getDato().equals(1)) {
                    UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    MatriculaService matriculaService = BeanFactory.getMatriculaService();
                    if (flgTodos == true) {
                        matriculaFiltroBean.setAnioFin(null);
                        listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
                    } else {
                        Calendar miCalendario = Calendar.getInstance();
                        matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
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
                            listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoDeudor(matriculaFiltroBean);
                            if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                                listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                            }
                        }
                    }
                } else {
                    if (matriculaFiltroBean.getDato().equals(2)) {
                        UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                        matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                        MatriculaService matriculaService = BeanFactory.getMatriculaService();
                        if (flgTodos == true) {
                            matriculaFiltroBean.setAnioFin(null);
                            listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
                        } else {
                            Calendar miCalendario = Calendar.getInstance();
                            matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
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
                    }
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

    public void limpiarEstudianteMatriculaMasivo() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        flgEstEsp = false;
        flgEstSinPro = false;
        flgPorNivelGrado = false;
        flgTodos = false;
    }

    public void obtenerPorId(Object object) {
        try {
            EsquelaBean esque = (EsquelaBean) object;
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            esquelaBean = esquelaService.obtenerPorId(esque.getIdEsquela());
            detEsquelaBean.setEsquelaBean(esque);
            botonEnviar = false;
            botonCancel = false;
            String meses = "";
            setAsunto(esquelaBean.getTitulo());
            meses = obtenerMeses();
            meses = esquelaBean.getMensaje() + men;
            detEsquelaBean.getEsquelaBean().setMensaje(meses);
            setMensaje(meses);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarEnvioMasivo(Integer dato) {
        String pagina = null;
        Integer i;
        Integer j;
        String var = "";
        String var2 = "";
        String var3 = "";
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            detEsquelaBean = new DetEsquelaBean();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            if (pagina == null) {
                if (flgAp.equals(true) || flgMa.equals(true) || flgPa.equals(true) || flgResPa.equals(true)) {
                    detEsquelaBean.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    detEsquelaBean.getEsquelaBean().setIdEsquela(esquelaBean.getIdEsquela());
                    detEsquelaBean.setCreaPor(usuarioLogin.getUsuario());
                    detEsquelaService.insertarDetEsquela(detEsquelaBean, listaMatriculaEstudiantesMasivosBean, flgPa, flgMa, flgAp, flgResPa, esquelaBean, dato);
                    if (dato.equals(1)) {
                        limpiarEsquela();
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    new MensajePrime().addInformativeMessageSearch("Seleccionar Destinatario");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerMenUniNeg() {
        try {
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            listaDetEsquelaBean = detEsquelaService.obtenerTodos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            listaEsquelaBean = esquelaService.obtenerEsquela(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetallesEsquela(Object object) {
        try {
            esquelaBean = (EsquelaBean) object;
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            listaDetEsquelaOkBean = detEsquelaService.obtenerListaOk("", usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), esquelaBean.getIdEsquela());
            listaDetEsquelaFailBean = detEsquelaService.obtenerListaFail(null, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), esquelaBean.getIdEsquela());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarDetalles() {
        try {
            listaDetEsquelaFailBean = new ArrayList<>();
            listaDetEsquelaOkBean = new ArrayList<>();
            flgOk = false;
            flgFail = false;
            mostrarFiltroSel = false;
            mostrarBoton = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaOk() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
//            listaDetEsquelaOkBean = detEsquelaService.obtenerListaOk(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaFail() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
//            listaDetEsquelaFailBean = detEsquelaService.obtenerListaFail(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

    public void verificarRegOk() {
        try {
            obtenerDetallesMsg(detEsquelaBean);
            flgOk = true;
            flgFail = false;
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            datoFecha = detEsquelaBean.getFecha();
            listaDetEsquelaOkBean = detEsquelaService.obtenerPorFecha(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarRegFail() {
        try {
            obtenerDetallesMsg(detEsquelaBean);
            flgOk = false;
            flgFail = true;
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            datoFecha = detEsquelaBean.getFecha();
            listaDetEsquelaFailBean = detEsquelaService.obtenerPorFecha(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void obtenerLogEnvio(Object object) {
//        try {
//            DetEsquelaBean detEsque = (DetEsquelaBean) object;
//            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
////            detEsquelaBean = detEsquelaService.obtenerPorId(detEsque.getIdDetEsquela());
//            System.out.println(detEsque.getIdDetEsquela());
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            flgOk = true;
//            flgFail = false;
//            mostrarFiltroSel = true;
//            listaDetEsquelaOkBean = detEsquelaService.obtenerListaOk(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaDetEsquelaFailBean = detEsquelaService.obtenerListaFail(detEsquelaBean.getCreaFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            System.out.println(detEsque.getIdDetEsquela());
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
    public void obtenerDetallesMsg(Object object) {
        try {
            detEsquelaBean = (DetEsquelaBean) object;
            System.out.println(detEsquelaBean.getFecha());
            setFecha(detEsquelaBean.getFecha());
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            flgOk = true;
            flgFail = false;
            mostrarFiltroSel = true;
            mostrarBoton = true;
//            if (flgOk.equals(true) && flgFail.equals(false)) {
//                System.out.println(flgOk + "Correctos");
//                datoFecha = detEsquelaBean.getFecha();
//                listaDetEsquelaOkBean = detEsquelaService.obtenerPorFecha(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);
//            }
            listaFiltroMen = detEsquelaService.obtenerPorFechaMen(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (flgOk.equals(false) && flgFail.equals(true)) {
//                System.out.println(flgFail + "Errores");
//                datoFecha = detEsquelaBean.getFecha();
//                listaDetEsquelaOkBean = detEsquelaService.obtenerPorFecha(detEsquelaBean.getFecha(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0);
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cancelarEnvioMasivo() {
        try {
            detEsquelaBean = new DetEsquelaBean();
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
            botonCancel = true;
            botonEnviar = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerMsgId(Object object) {
        try {
            DetEsquelaBean det = (DetEsquelaBean) object;
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();
            DetEsquelaFiltroBean = detEsquelaService.obtenerPorTitulo(det.getEsquelaBean().getTitulo());
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaEstudiantesMasivosBeanOk = matriculaService.obtenerAlumnoEsquela(getFecha(), det.getEsquelaBean().getIdEsquela(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);
            listaMatriculaEstudiantesMasivosBeanFail = matriculaService.obtenerAlumnoEsquela(getFecha(), det.getEsquelaBean().getIdEsquela(), usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0);
            asunto = det.getEsquelaBean().getTitulo();
            mensaje = det.getEsquelaBean().getMensaje();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirTodosPdf() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/repDetEsquela.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<DetEsquelaRepBean> listaDetEsquelaRepBean = new ArrayList<>();
            if (flgOk == true) {
                obtenerDetallesMsg(detEsquelaBean);
                verificarRegOk();
                for (DetEsquelaBean EsqueOk : listaDetEsquelaOkBean) {
                    if (!listaDetEsquelaOkBean.isEmpty()) {
                        DetEsquelaRepBean detEsqueOk = new DetEsquelaRepBean();
                        detEsqueOk.setCodEstudiante(EsqueOk.getEstudianteBean().getIdEstudiante());
                        detEsqueOk.setNombreCompleto(EsqueOk.getEstudianteBean().getPersonaBean().getNombreCompleto());
                        detEsqueOk.setCorreo(EsqueOk.getEstudianteBean().getPersonaBean().getCorreo());
                        detEsqueOk.setTitulo(EsqueOk.getEsquelaBean().getTitulo());
                        detEsqueOk.setFecha(EsqueOk.getFecha());
                        detEsqueOk.setFechaActual(EsqueOk.getFechaActual());
                        listaDetEsquelaRepBean.add(detEsqueOk);
                    }
                    if (listaDetEsquelaOkBean.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se Enviaron Mensajes Correctos"));
                    }
                }
            }
            if (flgFail == true) {
                obtenerDetallesMsg(detEsquelaBean);
                verificarRegOk();
                for (DetEsquelaBean EsqueFail : listaDetEsquelaFailBean) {
                    if (!listaDetEsquelaFailBean.isEmpty()) {
                        DetEsquelaRepBean detEsqueFail = new DetEsquelaRepBean();
                        detEsqueFail.setCodEstudiante(EsqueFail.getEstudianteBean().getIdEstudiante());
                        detEsqueFail.setNombreCompleto(EsqueFail.getEstudianteBean().getPersonaBean().getNombreCompleto());
                        detEsqueFail.setCorreo(EsqueFail.getEstudianteBean().getPersonaBean().getCorreo());
                        detEsqueFail.setTitulo(EsqueFail.getEsquelaBean().getTitulo());
                        detEsqueFail.setFecha(EsqueFail.getFecha());
                        detEsqueFail.setFechaActual(EsqueFail.getFechaActual());
                        listaDetEsquelaRepBean.add(detEsqueFail);
                    }
                    if (listaDetEsquelaFailBean.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se Enviaron Mensajes Erroneos"));
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDetEsquelaRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("SUBREPORT_DIR", ruta);
//            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            parametros.put("USUARIO", ub.getUsuario());

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

    public void listaMesesForSup() {
        try {
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
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public void obtenerDeuda() {
        try {
            for (Object value : listaMesesExpMap.values()) {
                for (Integer mes : listaMeses) {
                    if (mes.equals(Integer.parseInt(value.toString()))) {
                        envio = true;
                    } else {
                        envio = false;
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void activarMeses() {
        try {
            if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19701)) {
                envio = false;
                matriculaFiltroBean.setDato(2);
            } else {
                if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19702)) {
                    envio = true;
                    matriculaFiltroBean.setDato(1);
                    
                } else {
                    if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19703)) {
                        envio = false;
                        matriculaFiltroBean.setDato(2);
                    }
                }
            }
            EsquelaService esquelaService = BeanFactory.getEsquelaService();
            listaEsquelaBean = esquelaService.obtenerEsquelaFiltro(esquelaBean);
            System.out.println(">>>" + listaEsquelaBean.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDeudaEst(Object object) {
        try {
            matriculaBean = (MatriculaBean) object;
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            listaCuentas = cuentasPorCobrarService.obtenerDeudaEstudiante(matriculaBean.getEstudianteBean().getIdEstudiante());
            for (int i = 0; i < listaCuentas.size(); i++) {
                monto = listaCuentas.get(i).getMonto();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarMes() {
        try {
            int estado = 0;
            if (matriculaFiltroBean.getDato().equals(1)) {
                if (listaMeses.length != 0) {
                    UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                    matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    MatriculaService matriculaService = BeanFactory.getMatriculaService();
                    if (flgTodos == true) {
                        getMatriculaFiltroBean().setAnioIni(getMatriculaFiltroBean().getAnioIni());
                        System.out.println(">>>>" + matriculaFiltroBean.getAnioIni());
                        matriculaFiltroBean.setAnioFin(null);
                        listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoDeudorMes(matriculaFiltroBean, listaMeses);
                    } else {
                        Calendar miCalendario = Calendar.getInstance();
                        matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
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
                            listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoDeudorMes(matriculaFiltroBean, listaMeses);
                            if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                                listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                            }
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTablaMes(Integer[] listaMeses){
        try {
            String table = "";
            for (int i = 0; i < listaMeses.length; i++) {
                switch(listaMeses[i]){
                    case 1:table += "<table>";break;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void imprimir() {
        try {
            if (listaMeses == null || listaMeses.length == 0) {
                insertarMenEsquela();
                imprimirMesajeNativo();
            } else {
                if (listaMeses != null && listaMeses.length > 0) {
                    insertarMenEsquela();
                    imprimirMensajes();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirMesajeNativo() {
        ServletOutputStream out = null;
        try {
            insertarEnvioMasivo(2);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCartaMasiva.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MasivoCartaUnoBean> listaMasivoCartaUnoBean = new ArrayList<>();
            MasivoCartaUnoBean masivoCartaUnoBean = new MasivoCartaUnoBean();
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();

            //Lista de Mensaje
            DetEsquelaBean detEsquela = new DetEsquelaBean();
            detEsquela.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detEsquela.getEsquelaBean().setIdEsquela(esquelaBean.getIdEsquela());
            listaMasivoCartaUnoBean = detEsquelaService.obtenerListaEsquelaRep(detEsquela);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMasivoCartaUnoBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
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

    public void imprimirMensajes() {
        ServletOutputStream out = null;
        try {
            insertarEnvioMasivo(2);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMasivoCartaUno.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MasivoCartaUnoBean> listaMasivoCartaUnoBean = new ArrayList<>();
            MasivoCartaUnoBean masivoCartaUnoBean = new MasivoCartaUnoBean();
            DetEsquelaService detEsquelaService = BeanFactory.getDetEsquelaService();

            //Lista de Mensaje
            DetEsquelaBean detEsquela = new DetEsquelaBean();
            detEsquela.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detEsquela.getEsquelaBean().setIdEsquela(esquelaBean.getIdEsquela());
            listaMasivoCartaUnoBean = detEsquelaService.obtenerListaEsquelaRep(detEsquela);

            //Sub-Lista de Mensaje
            if (!listaMasivoCartaUnoBean.isEmpty()) {
                for (MasivoCartaUnoBean masivo : listaMasivoCartaUnoBean) {
                    List<SubReporteMasivoCartaUno> listaSubReporteMasivoCartaUno = new ArrayList<>();
                    listaSubReporteMasivoCartaUno = detEsquelaService.obtenerListaDeuda(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), masivo.getIdEstudiante(), anio, listaMeses);
                    masivo.setListaDetalle(listaSubReporteMasivoCartaUno);
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMasivoCartaUnoBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
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

    public void imprimirMensajeMasivo() {
        ServletOutputStream out = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd");
            DateFormat dateFormatY = new SimpleDateFormat("yyyy");
            String mes = "";
            String dia = "";
            String fechaMen = "";
            java.util.Date fecha = new Date();
//            Calendar fecha = new GregorianCalendar();
            switch (fecha.getMonth()) {
                case 1:
                    mes = "Enero";
                    break;
                case 2:
                    mes = "Febrero";
                    break;
                case 3:
                    mes = "Marzo";
                    break;
                case 4:
                    mes = "Abril";
                    break;
                case 5:
                    mes = "Mayo";
                    break;
                case 6:
                    mes = "Junio";
                    break;
                case 7:
                    mes = "Julio";
                    break;
                case 8:
                    mes = "Agosto";
                    break;
                case 9:
                    mes = "Setiembre";
                    break;
                case 10:
                    mes = "Octubre";
                    break;
                case 11:
                    mes = "Noviembre";
                    break;
                case 12:
                    mes = "Diciembre";
                    break;
            }
            fechaMen = dateFormat.format(fecha.getDay()).concat(" de ").concat(mes).concat(" de ").concat(dateFormatY.format(fecha.getYear()));
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = "";
            if (listaMeses == null || listaMeses.length == 0) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCartaMasiva.jasper");
            } else {
                if (listaMeses.length == 1) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMasivoCartaDos.jasper");
                } else {
                    if (listaMeses.length == 2) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMasivoCartaTres.jasper");
                    } else {
                        if (listaMeses.length == 3) {
                            archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMasivoCartaCuatro.jasper");
                        } else {
                            if (listaMeses.length >= 4) {
                                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteMasivoCartaUno.jasper");
                            }
                        }
                    }
                }
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<MasivoCartaUnoBean> listaMasivoCartaUnoBean = new ArrayList<>();
//            String strippedText = mensaje.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
            for (int i = 0; i < listaMatriculaEstudiantesMasivosBean.size(); i++) {
                MasivoCartaUnoBean masivoCartaUnoBean = new MasivoCartaUnoBean();
                masivoCartaUnoBean.setUniNegPer(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                System.out.println(">>>>" + esquelaBean.getFlgenviorespago());
                if (esquelaBean.getFlgenviopapa() != null && esquelaBean.getFlgenviopapa().equals(1)) {
                    masivoCartaUnoBean.setNombreFamilia(listaMatriculaEstudiantesMasivosBean.get(i).getNomPap());
                }
                if (esquelaBean.getFlgenviomama() != null && esquelaBean.getFlgenviomama().equals(1)) {
                    masivoCartaUnoBean.setNombreFamilia(listaMatriculaEstudiantesMasivosBean.get(i).getNomMam());
                }
                if (esquelaBean.getFlgenviorespago() != null && esquelaBean.getFlgenviorespago().equals(1)) {
                    masivoCartaUnoBean.setNombreFamilia(listaMatriculaEstudiantesMasivosBean.get(i).getNomRes());
                }
                if (esquelaBean.getFlgenvioapo() != null && esquelaBean.getFlgenvioapo().equals(1)) {
                    masivoCartaUnoBean.setNombreFamilia(listaMatriculaEstudiantesMasivosBean.get(i).getNomApo());
                }
                masivoCartaUnoBean.setDireccion(listaMatriculaEstudiantesMasivosBean.get(i).getEstudianteBean().getViaDomi());
                masivoCartaUnoBean.setDistrito(listaMatriculaEstudiantesMasivosBean.get(i).getUnidadNegocioBean().getDistritoBean().getNombre());
                masivoCartaUnoBean.setAsunto(esquelaBean.getTitulo());
                masivoCartaUnoBean.setMensaje(esquelaBean.getMensaje());
                masivoCartaUnoBean.setFecha(fechaMen.toUpperCase());
                listaMasivoCartaUnoBean.add(masivoCartaUnoBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaMasivoCartaUnoBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
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

    public String obtenerMeses() {
        StringBuilder msj = new StringBuilder();
        try {
            if (listaMeses != null) {
                if (listaMeses.length != 0) {
                    msj.append("<p>Pensiones: <span>");
                    for (Integer meses : listaMeses) {
                        switch (meses) {
                            case 1:
                                msj.append(" <b>Enero</b> ");
                                break;
                            case 2:
                                msj.append(" <b>Febrero</b> ");
                                break;
                            case 3:
                                msj.append(" <b>Marzo</b> ");
                                break;
                            case 4:
                                msj.append(" <b>Abril</b> ");
                                break;
                            case 5:
                                msj.append(" <b>Mayo</b> ");
                                break;
                            case 6:
                                msj.append(" <b>Junio</b> ");
                                break;
                            case 7:
                                msj.append(" <b>Julio</b> ");
                                break;
                            case 8:
                                msj.append(" <b>Agosto</b> ");
                                break;
                            case 9:
                                msj.append(" <b>Setiembre</b> ");
                                break;
                            case 10:
                                msj.append(" <b>Octubre</b> ");
                                break;
                            case 11:
                                msj.append(" <b>Noviembre</b> ");
                                break;
                            case 12:
                                msj.append(" <b>Diciembre</b> ");
                                break;
                        }
                    }
                    msj.append("</span></p>");
                } else {
                    msj.append("");
                }
            } else {
                msj.append("");
            }
            men = msj.toString();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return msj.toString();
    }

    public void imprimirOk() {
        ServletOutputStream out = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd");
            DateFormat dateFormatY = new SimpleDateFormat("yyyy");
            String mes = "";
            String dia = "";
            String fechaMen = "";
            java.util.Date fecha = new Date();
//            Calendar fecha = new GregorianCalendar(); 
            fechaMen = dateFormat.format(fecha.getDay()).concat(" de ").concat(mes).concat(" de ").concat(dateFormatY.format(fecha.getYear()));
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteEnvioAlumnos.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EnvioAlumno> listaEnvioAlumno = new ArrayList<>();
            for (int i = 0; i < listaMatriculaEstudiantesMasivosBeanOk.size(); i++) {
                EnvioAlumno envioAlumno = new EnvioAlumno();
                envioAlumno.setIdEstudiante(listaMatriculaEstudiantesMasivosBeanOk.get(i).getEstudianteBean().getIdEstudiante());
                envioAlumno.setNombre(listaMatriculaEstudiantesMasivosBeanOk.get(i).getEstudianteBean().getPersonaBean().getNombre());
                envioAlumno.setApellidoPat(listaMatriculaEstudiantesMasivosBeanOk.get(i).getEstudianteBean().getPersonaBean().getApepat());
                envioAlumno.setApellidoMat(listaMatriculaEstudiantesMasivosBeanOk.get(i).getEstudianteBean().getPersonaBean().getApemat());
                envioAlumno.setNombreGraAcaHab(listaMatriculaEstudiantesMasivosBeanOk.get(i).getEstudianteBean().getGradoAcademicoBean().getNombre());
                envioAlumno.setSeccion(listaMatriculaEstudiantesMasivosBeanOk.get(i).getSeccion());
                envioAlumno.setAsunto(asunto);
                envioAlumno.setMensaje(mensaje);
                listaEnvioAlumno.add(envioAlumno);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEnvioAlumno);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
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

    public void imprimirFail() {
        ServletOutputStream out = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd");
            DateFormat dateFormatY = new SimpleDateFormat("yyyy");
            String mes = "";
            String dia = "";
            String fechaMen = "";
            java.util.Date fecha = new Date();
//            Calendar fecha = new GregorianCalendar(); 
            fechaMen = dateFormat.format(fecha.getDay()).concat(" de ").concat(mes).concat(" de ").concat(dateFormatY.format(fecha.getYear()));
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteEnvioAlumnos.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<EnvioAlumno> listaEnvioAlumno = new ArrayList<>();
            for (int i = 0; i < listaMatriculaEstudiantesMasivosBeanFail.size(); i++) {
                EnvioAlumno envioAlumno = new EnvioAlumno();
                envioAlumno.setIdEstudiante(listaMatriculaEstudiantesMasivosBeanFail.get(i).getEstudianteBean().getIdEstudiante());
                envioAlumno.setNombre(listaMatriculaEstudiantesMasivosBeanFail.get(i).getEstudianteBean().getPersonaBean().getNombre());
                envioAlumno.setApellidoPat(listaMatriculaEstudiantesMasivosBeanFail.get(i).getEstudianteBean().getPersonaBean().getApepat());
                envioAlumno.setApellidoMat(listaMatriculaEstudiantesMasivosBeanFail.get(i).getEstudianteBean().getPersonaBean().getApemat());
                envioAlumno.setNombreGraAcaHab(listaMatriculaEstudiantesMasivosBeanFail.get(i).getEstudianteBean().getGradoAcademicoBean().getNombre());
                envioAlumno.setSeccion(listaMatriculaEstudiantesMasivosBeanFail.get(i).getSeccion());
                envioAlumno.setAsunto(asunto);
                envioAlumno.setMensaje(mensaje);
                listaEnvioAlumno.add(envioAlumno);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEnvioAlumno);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
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

    //Metodos Nuevos
    public void guardar() {
        try {
            if (esquelaBean.getIdEsquela() != null) {
                modificarEsquela();
            } else {
                insertarMenEsquela();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarMenEsquela() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                esquelaBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                esquelaBean.setTipoAccion(esquelaBean.getTipoAccion());
                esquelaBean.setCreaPor(beanUsuarioSesion.getUsuario());
                esquelaService.insertarEsquela(esquelaBean);
                Integer idEsquela = esquelaService.obtenerMaxEsquela(esquelaBean.getUnidadNegocioBean().getUniNeg());
                esquelaBean = esquelaService.obtenerPorId(idEsquela);
                esquelaBean.setIdEsquela(idEsquela);
                System.out.println(">>>>" + esquelaBean.getTipoAccion());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarEsquela() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EsquelaService esquelaService = BeanFactory.getEsquelaService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                esquelaBean.setModiPor(beanUsuarioSesion.getUsuario());
                esquelaService.modificarEsquela(esquelaBean);
                listaEsquelaBean = esquelaService.obtenerEsquela(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                limpiarEsquela();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void limpiarEsquela() {
        try {
            esquelaBean = new EsquelaBean();
            limpiarEstudianteMatriculaMasivo();
            envio = false;
            flgAp = false;
            flgPa = false;
            flgMa = false;
            flgResPa = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTextArea() {
        try {
            getEsquelaBean().setTipoAccion(getEsquelaBean().getTipoAccion());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

}
