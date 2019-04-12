/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Time;
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
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.component.api.UIColumn;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoErrorBean;
import pe.marista.sigma.bean.ProcesoFalloBean;
import pe.marista.sigma.bean.ProcesoFileBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.ProcesoFinalBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ProcesoEnvioRepBean;
import pe.marista.sigma.bean.reporte.ProcesoRecuperacionRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.CronogramaPagoService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoErrorService;
import pe.marista.sigma.service.ProcesoFalloService;
import pe.marista.sigma.service.ProcesoFileService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.service.ReporteRechazoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS-005
 */
public class ProcesoBancoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ProcesoBancoMB
     */
    @PostConstruct
    public void ProcesoBancoMB() {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaMesesForSup();
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
//            listaMatriculaBean = matriculaService.obtenerMatriculaUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            getListaTipoConceptoBean();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();

            getCuentasPorCobrarFiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaEntidad();
            listaEntidad = entidadService.obtenerFlgFinanciero(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaMoneda();
            listaMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
            getListaTipoFile();
            listaTipoFile = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.Tip_File_Com));

            getListaTipoArchivo();
            listaTipoArchivo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.Tip_Archivo));
            listaTipoArchivo.remove(1);
            ponerProceso();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            fechaEnvio = procesoBancoService.obtenerFechaActual();
            listaTipoEstatusCuenta = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CUENTA));

            getCuentasPorCobrarFiltroBean();
            getCuentasPorCobrarFiltroBean().setAnios(MaristaConstantes.Anios_Banco);
            getCuentasPorCobrarFiltroBean().setDia(MaristaConstantes.Dias_Banco);

            getCuentaFiltroBean();
            getProcesoBanco();
//            getProcesoBancoFiltroBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
//            getProcesoBancoFiltroBean().setDia(MaristaConstantes.Dias_Banco.toString());
            getCronogramaPagoBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            listaCronogramaPagoBean = cronogramaPagoService.obtenerCronogramaAnio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCronogramaPagoBean().getAnio());
            obtenerVistaFile();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoFiltroBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoFiltroBean().setFechaFin(fechaActual.getTime());
            getProcesoBanco().setFecha(fechaActual.getTime());
            getCuentaFiltroBean().setFechaInicio(fechaActual.getTime());
            getCuentaFiltroBean().setFechaFin(fechaActual.getTime());
            getListaMesAll();
            obtenerListaMeses();
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                setFlgUniNeg(true);
            } else {
                setFlgUniNeg(false);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Proceso Banco
    private ProcesoBancoBean procesoBancoBean;
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private List<ProcesoBancoBean> listaProcesoBancoFiltroBean;
    private List<ProcesoBancoBean> listaProcesoBanco;
    private ProcesoBancoBean procesoBancoFiltroBean;
    private ProcesoBancoBean procesoBanco;

    //Proceso Envio
    private ProcesoEnvioBean procesoEnvioBean;
    private ProcesoEnvioBean procesoEnvioFiltroBean;
    private List<ProcesoEnvioBean> listaProcesoEnvioBean;
    private List<ProcesoEnvioBean> listaEnvioFiltroBean;
    private StreamedContent content;
    private List<ProcesoEnvioBean> listaProcesoEnvioFiltroBean;
    private ProcesoEnvioBean procesoEnvio;

    //ProcesoError
    private ProcesoErrorBean procesoErrorBean;
    private List<ProcesoErrorBean> listaProcesoErrorBean;

    //ProcesoError
    private ProcesoFalloBean procesoFalloBean;
    private List<ProcesoFalloBean> listaProcesoFalloBean;

    //CuentaBancoBean
    private CuentaBancoBean cuentaBancoBean;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private List<CuentaBancoBean> listaCuentaBanco;

    //Ayuda
    private EntidadBean entidadBean;
    private List<EntidadBean> listaEntidadBean;
    private List<Object> listaAnosRender;
    private ConceptoBean conceptoBean;
    private List<ConceptoBean> listaconceptoBean;
    private List<CodigoBean> listaMoneda;

    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private CuentasPorCobrarBean cuentasPorCobrarFiltroBean;
    private List<CuentasPorCobrarBean> listaCtasXCobrarBean;
    private List<CuentasPorCobrarBean> listaCtaFiltro;
    private List<CuentasPorCobrarBean> listaCuentasCobrarBean;
    private CuentasPorCobrarBean cuentasFiltroBean;

    private EstudianteBean estudianteBean;
    private EstudianteBean estudianteFiltroBean;
    private List<EstudianteBean> listaEstudianteBean;
    private List<EstudianteBean> listaEstudianteFiltro;

    private PersonaBean personaBean;
    private List<PersonaBean> listaPersonaBean;

    private PersonaBean personaFiltroBean;
    private Date fechaRecup;
    private String nomRecuperacion;

    //Archivo Fila
    private ProcesoFileBean archivoFile;
    private List<ProcesoFileBean> listaProcesoFile;
    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private List<ProcesoRecuperacionBean> listaProcesoRecuperacion;

    private ProcesoFileBean procesoFileBean;
    private List<ProcesoFileBean> listaProcesoFileBean;
    private ProcesoFileBean procesoFileFiltro;

    private MatriculaBean matriculaBean;
    private MatriculaBean matriculaFiltroBean;
    private List<MatriculaBean> listaMatriculaBean;

    private ProcesoRecuperacionBean procesoRecuperacion;
    private List<ProcesoRecuperacionBean> listaProRec;
    private List<ProcesoRecuperacionBean> listaRecuperacion;

    private List<EntidadBean> listaEntidad;

    //Drap Drog
    private List<ProcesoFilesBean> listaProcesoFilesBean;
    private TreeNode availableColumns;
    private List<Object> listaColumnas;
    private List<Object> listaColumnasCab;
    private Integer columna;
    private List<ProcesoFilesBean> listaProDetEnv;
    private List<ProcesoFilesBean> listaProCabEnv;
    private List<ProcesoFilesBean> listaProIntEnv;
    private ProcesoFilesBean procesoFilesBean;
    private ProcesoFinalBean procesoFinalBean;
    private Boolean disabled;
    private Integer valorExec;
    private List<CodigoBean> listaTipoFile;
    private List<CodigoBean> listaTipoArchivo;
    private Integer f1 = 0, f2 = 0, f3 = 0;
    private Integer input = 0;
    private List<String> listaNoConstante;
    private Integer error = 0;

    //DESTINO
    //private String destination = "\\\\PRO_BD\\C\\Temporal\\";
    private String destination = "D:\\";

    private String user;
    private String pass;

    private UploadedFile file;
    private String ruta = "C:\\pruebas";

    //Ayuda
    private Integer var1 = 1;
    private Integer var2 = 2;
    private Integer var3 = 3;
    private Integer total = 0;
    private Integer total2 = 0;
    private Integer montoTotal = 0;

    private Calendar fechaActual;
    private boolean mostrarTabla = true;
    private boolean mostrarPanelBus = false;
    private boolean mostrarAcc;
    private Integer mostrarAc = 0;
    private boolean mostrarMatricula = false;
    private boolean mostrarPanel = false;//true
    private boolean mostrarRec = false;
    private boolean activarBoton = true;
    private boolean mostrarLayout = false;
    private boolean mostrarLayoutPrincipal = false;
    private boolean mostrarBoton = false;
    private Integer indexAccNuevo = 1;
//    private boolean mostrarBoton2 = false;
    private boolean mostrarBotonS = true;
    private boolean valFlgEnvio = true;
    private boolean flgEnvio = true;
    private boolean flgGrabar = false;
    private boolean flgStatus;
    private boolean mostrarAlerta = false;
    private Integer envio = 0;
    private Integer totalEnvios = 0;
    private Integer totalErroneos = 0;
//    private List<Object> listaMeses;
    private Map<String, String> listaMeses;
    private String mes;
    private String idMes;
//    private BigDecimal flgDeuda;
    private String flgDeuda;
    private Map<String, String> listaDeuda;
    private TipoConceptoBean tipoConceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;

    //Datos de Fecha
    private int datoAnio;
    private int datoMes;
    private int datoDia;
    private int fecha;
    //Datos de Tiempo
    private int datoHora;
    private int datoMinuto;
    private int hora;
    private String ruc;

    private UsuarioBean usuarioSessionBean;

    private Integer var = 0;
    private Integer res = 0;
    private Integer fail = 0;
    private Integer pos = -1;

    private Integer idFile;// IDFile
//    private HashMap<Integer, List<Object>> listaProBancos;
    private List<Contenedor> listaProBancos;
    private List<Contenedor> listaProEnvios;
    private HashMap<Integer, List<Object>> listaFiles;
    private HashMap<Integer, List<Object>> listaFilesCab;
    private HashMap<Integer, List<Object>> listaFilesInt;

    private Map<String, Integer> listaProcesos;
    private String fechaEnvio;
    private Integer filas = 0;
    private Integer filasCab = 0;
    private Integer filasInt = 0;
    private Integer mesEnvio;

    private List<CodigoBean> listaTipoEstatusCuenta;
    private Boolean proView;
    private Boolean proView2;
    private CuentasPorCobrarBean cuentaFiltroBean;
    private List<Contenedor> listaContenedorEnvio;
    private List<Contenedor> listaContenedorEnvioCabecera;
    private List<Contenedor> listaContenedorEnvioIntermedio;
    private Boolean disableBotonDescarga = true;
    private Boolean disableBotonGuardar = false;
    private ProcesoBancoBean procesoBancoGen;
    private Integer idProceso = 0;
    private String rucEntidad = "";
    private Boolean disabledExporter = false;
    private String tipoRegistro;
    private CronogramaPagoBean cronogramaPagoBean;
    private List<CronogramaPagoBean> listaCronogramaPagoBean;
    private Integer tipoEnvio;
    private Integer selEnvio;
    private Boolean vistaFile;

    private Integer flgEstado;
    private List<MesBean> listaMesAll;
    private List<Integer> listaMesSel;
    private boolean flgUniNeg;

    public void ponerCuentaBanco(SelectEvent event) {
        try {
            cuentaBancoBean = (CuentaBancoBean) event.getObject();
            procesoEnvio.getProcesoBancoBean().setCuentaBancoBean(cuentaBancoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarProcesoBanco() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelect(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoBanco() {
        try {
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoBean = procesoBancoService.obtenerProcesoBanco();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizarLista(AjaxBehaviorEvent event) {
        try {
            int a = procesoBancoBean.getAnio();
            int b = 2040;
            listaAnosRender = new ArrayList<>();
            for (int i = a; i <= b; i++) {
                listaAnosRender.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTotal() {
        try {
            listaProcesoEnvioFiltroBean = new ArrayList<>();
            int montoTotal = getTotal();
            int montoTotal2 = getTotal2();
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getMonto().intValue();
                montoTotal = montoTotal + listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getMonto().intValue();
                System.out.println(montoTotal);
                System.out.println(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getMonto());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProcesoPorUniNeg() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoBancoPorId(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoBean = procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarProcesoBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoBancoBean.setCreaPor(beanUsuarioSesion.getUsuario());
                procesoBancoBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                procesoBancoService.insertarProcesoBanco(procesoBancoBean);
                listaProcesoBancoBean = procesoBancoService.obtenerProcesoBanco();
                limpiarProcesoBanco();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarProcesoBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoBancoBean.setModiPor(beanUsuarioSesion.getUsuario());
                procesoBancoService.modificarProcesoBanco(procesoBancoBean);
                listaProcesoBancoBean = procesoBancoService.obtenerProcesoBanco();
                limpiarProcesoBanco();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String eliminarProcesoBanco() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
//                procesoBancoService.eliminarProcesoBanco(procesoBancoBean.getIdProcesoBanco());
                listaProcesoBancoBean = procesoBancoService.obtenerProcesoBanco();
                limpiarProcesoBanco();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarProcesoBanco() {
        try {
            if (procesoBancoBean.getIdProcesoBanco() == null) {
                insertarProcesoBanco();
            } else {
                modificarProcesoBanco();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroProceso() {
        try {
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerProcesoBanco();
//            if (procesoBancoFiltroBean.getFlgProceso() != null) {
//                procesoBancoFiltroBean.setFlgProceso(procesoBancoFiltroBean.getFlgProceso());
//                procesoBancoFiltroBean.setFlgProceso(procesoBancoFiltroBean.isFlgProceso());
//            }
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoBancoFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (procesoBancoFiltroBean.getFlgProceso() != null) {
                procesoBancoFiltroBean.setFlgProceso(procesoBancoFiltroBean.getFlgProceso());
            }
            if (procesoBancoFiltroBean.getNombre() != null) {
                procesoBancoFiltroBean.setNombre(procesoBancoFiltroBean.getNombre());
            }
            if (procesoBancoFiltroBean.getEntidadBean().getRuc() != null) {
                procesoBancoFiltroBean.getEntidadBean().setRuc(procesoBancoFiltroBean.getEntidadBean().getRuc());
            }
            if (procesoBancoFiltroBean.getAnio() != null) {
                procesoBancoFiltroBean.setAnio(procesoBancoFiltroBean.getAnio());
            }
            if (procesoBancoFiltroBean.getMes() != null) {
                procesoBancoFiltroBean.setMes(procesoBancoFiltroBean.getMes());
            }
            if (procesoBancoFiltroBean.getDia() != null) {
                procesoBancoFiltroBean.setDia(procesoBancoFiltroBean.getDia());
            }
            if (procesoBancoFiltroBean.getFechaInicio() != null) {
                procesoBancoFiltroBean.setFechaInicio(procesoBancoFiltroBean.getFechaInicio());
            }
            if (procesoBancoFiltroBean.getFechaFin() != null) {
                procesoBancoFiltroBean.setFechaFin(procesoBancoFiltroBean.getFechaFin());
            }
            listaProcesoBancoFiltroBean = procesoBancoService.filtrarProceso(procesoBancoFiltroBean);
            if (listaProcesoBancoFiltroBean.isEmpty()) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
//            mostrarLayout = true;
            mostrarLayoutPrincipal = true;
            indexAccNuevo = 1;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void pornerFlgProceso() {
        try {
            System.out.println(procesoBancoFiltroBean.getFlgProceso());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //=================================Proceso Envio=========================================//
    public void obtenerProcesoEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = new ArrayList<>();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEnvio() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaEnvioFiltroBean = procesoEnvioService.obtenerEnvio();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioBean = new ProcesoEnvioBean();
            procesoEnvioBean.setFlgEnvio(((ProcesoEnvioBean) event.getObject()).getFlgEnvio());
//            procesoEnvioBean.setMora(((ProcesoEnvioBean) event.getObject()).getMora());
//            procesoEnvioBean.setMonto(((ProcesoEnvioBean) event.getObject()).getMonto());
            procesoEnvioBean.setTipoRegistro(((ProcesoEnvioBean) event.getObject()).getTipoRegistro());
            procesoEnvioBean.setIdProcesoEnvio(((ProcesoEnvioBean) event.getObject()).getIdProcesoEnvio());
            procesoEnvioBean.setModiPor(beanUsuarioSesion.getUsuario());
            procesoEnvioService.modificarStatusEnvio(procesoEnvioBean);
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            FacesMessage msg = new FacesMessage("Envio Editado con Exito:", ((ProcesoEnvioBean) event.getObject()).getProcesoBancoBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            procesoEnvioBean = new ProcesoEnvioBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edici贸n Cancelada", ((ProcesoEnvioBean) event.getObject()).getProcesoBancoBean().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void cambiarValFlgEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            obtenerProcesoEnvioPorId2(procesoBancoBean);
            procesoEnvioBean = new ProcesoEnvioBean();
            if (valFlgEnvio) {
                for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                    listaProcesoEnvioFiltroBean.get(i).setFlgEnvio(true);
                    listaProcesoEnvioFiltroBean.get(i).setModiPor(beanUsuarioSesion.getUsuario());
                    procesoEnvioService.modificarStatus(listaProcesoEnvioFiltroBean.get(i));
                }
            } else {
                for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                    listaProcesoEnvioFiltroBean.get(i).setFlgEnvio(false);
                    listaProcesoEnvioFiltroBean.get(i).setModiPor(beanUsuarioSesion.getUsuario());
                    procesoEnvioService.modificarStatus(listaProcesoEnvioFiltroBean.get(i));
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorProcesoBanco() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarProcesoEnvio() {
        try {
            procesoEnvioBean = new ProcesoEnvioBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarProcesoEnvio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ProcesoEnvioBean procesoEnvio = new ProcesoEnvioBean();
                procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
                procesoEnvioService.insertarProcesoEnvio(procesoEnvio, cuentasPorCobrarBean, listaCtaFiltro, listaProcesoEnvioBean);
                listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                limpiarProcesoEnvio();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void exportarArchivo() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean = new ProcesoEnvioBean();
            int n = 0;
            int m = 0;
            int monto = procesoEnvioBean.getTotal();
//            monto = procesoEnvioBean.getTotal();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print("CC");
                    }
                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    }
//                    else {
//                    pw.print("193");

                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());

//                        pw.print("   ");
//                    }
                    pw.print("0");//Moneda
//                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
//                        pw.print(" ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());//Cambio
//                        pw.print("0");
//                    }
                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
//                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());
                        pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }
                    pw.print("C");//Tipo Validacion
                    pw.print("CCEGNE" + listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioBean.get(i).getFechaEmision() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCreaFecha()));
                    }
                    pw.print(i);//Registros
                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)) {
                    m = m + 1;
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } //                    if(listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)){
                    //                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "DD");
                    //                    }
                    else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "DD");
                    }
//                    pw.print("193");
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
//                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
//                        pw.print("   ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());
//                    }

                    pw.print("0");//Moneda 
//                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null && listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
//                        pw.print(" ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());
//                        pw.print("0");
//                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
//                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta().toString().replaceAll(" ", ""));
//                        pw.print("1428600");
                        pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null) {
                        pw.print("              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null) {
                        pw.print("                                        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null) {
                        pw.print("                              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago()));
//                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().replaceAll(" ", ""));//Cambio
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null) {
                        pw.print("        ");
                    } else {
                        pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc()));
                    }

                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        int sub = listaProcesoEnvioBean.get(i).getMonto().intValue();
                        monto = monto + sub;
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }

                    if (listaProcesoEnvioBean.get(i).getMora() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMora());
                    }
                    pw.println("\n");

//                pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());//
//                pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());//
//                pw.print(listaProcesoEnvioBean.get(i).getMoneda());// 
//                pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());//
//                pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getIdEstudiante());//
//                pw.print(procesoEnvioBean.getIdDiscente().getPersonaBean().getNombreCompleto());//
//                pw.print(listaProcesoEnvioBean.get(i).getInfoRetorno());//
//                pw.print(listaProcesoEnvioBean.get(i).getFechaEmision());//
//                pw.print(listaProcesoEnvioBean.get(i).getFechaVenc());//
//                pw.print(listaProcesoEnvioBean.get(i).getMonto());//
//                pw.println(listaProcesoEnvioBean.get(i).getMora());  
                    System.out.println(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                    System.out.println("Exporte Exitoso");
//                System.out.println(procesoEnvioBean.getTotal());
                    System.out.println(monto);
                }
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioBean.get(i).getFlgEnvio().equals(false)) {
                    pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().equals(""));
                    pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().equals("")));
                    pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc().toString().equals("")));
                    pw.print(listaProcesoEnvioBean.get(i).getMonto().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getMora().toString().equals(""));
                    pw.println("\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }
            int s = n + m;
            System.out.println(procesoEnvioBean.getTotal());
            System.out.println(monto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void descargaEnvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
//            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaProcesoEnvioBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(
                            new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");

                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
        } catch (Exception err) {
            // Nothing
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void descargaArchivo() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            insertarEnvioMasivo();
//            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
//            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaProcesoEnvioBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
            pw = new PrintWriter(fichero);
//            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean = new ProcesoEnvioBean();
            int n = 0;
            int m = 0;
            int monto = procesoEnvioBean.getTotal();
//            monto = procesoEnvioBean.getTotal();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                totalEnvios = listaProcesoEnvioBean.get(i).getMonto().intValue() + totalEnvios;
                totalErroneos = listaProcesoEnvioBean.get(i).getProcesoBancoBean().getRegError();
            }

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print("CC");
                    }
                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    }
//                    else {
//                    pw.print("193");

                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());

//                        pw.print("   ");
//                    }
                    pw.print("0");//Moneda
//                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
//                        pw.print(" ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());//Cambio
//                        pw.print("0");
//                    }
                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
//                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());
                        pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }
                    pw.print("C");//Tipo Validacion
                    pw.print("CCEGNE" + listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioBean.get(i).getFechaEmision() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCreaFecha()));
                    }
                    pw.print(i);//Registros
                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(totalEnvios);
                    }
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)) {
                    m = m + 1;
                    pw.print("DD");
//                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
//                        pw.print("  ");
//                    } //                    if(listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)){
//                    //                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "DD");
//                    //                    }
//                    else {
//                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "DD");
//                    }
//                    pw.print("193");
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
//                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
//                        pw.print("   ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());
//                    }

                    pw.print("0");//Moneda 
//                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null && listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
//                        pw.print(" ");
//                    } else {
////                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());
//                        pw.print("0");
//                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
//                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta().toString().replaceAll(" ", ""));
//                        pw.print("1428600");
                        pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null) {
                        pw.print("              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null) {
                        pw.print("                                        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null) {
                        pw.print("                              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago()));
//                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().replaceAll(" ", ""));//Cambio
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null) {
                        pw.print("        ");
                    } else {
                        pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc()));
                    }

                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        int sub = listaProcesoEnvioBean.get(i).getMonto().intValue();
                        monto = monto + sub;
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }

                    if (listaProcesoEnvioBean.get(i).getMora() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMora());
                    }
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } //                    if(listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)){
                    //                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "DD");
                    //                    }
                    else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());
                    }
                    pw.println("\n");

//                pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());//
//                pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());//
//                pw.print(listaProcesoEnvioBean.get(i).getMoneda());// 
//                pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());//
//                pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getIdEstudiante());//
//                pw.print(procesoEnvioBean.getIdDiscente().getPersonaBean().getNombreCompleto());//
//                pw.print(listaProcesoEnvioBean.get(i).getInfoRetorno());//
//                pw.print(listaProcesoEnvioBean.get(i).getFechaEmision());//
//                pw.print(listaProcesoEnvioBean.get(i).getFechaVenc());//
//                pw.print(listaProcesoEnvioBean.get(i).getMonto());//
//                pw.println(listaProcesoEnvioBean.get(i).getMora());  
                    System.out.println(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                    System.out.println("Exporte Exitoso");
//                System.out.println(procesoEnvioBean.getTotal());
                    System.out.println(monto);
                }
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioBean.get(i).getFlgEnvio().equals(false)) {
                    pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().equals(""));
                    pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().equals("")));
                    pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc().toString().equals("")));
                    pw.print(listaProcesoEnvioBean.get(i).getMonto().toString().equals(""));
                    pw.print(listaProcesoEnvioBean.get(i).getMora().toString().equals(""));
                    pw.println("\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }
            int s = n + m;
            System.out.println(procesoEnvioBean.getTotal());
            System.out.println(monto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(
                            new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");

                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
            activarBoton = false;
            mostrarBoton = false;
//            retirarLista();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void descargaEnvios() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
//            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaProcesoEnvioBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(
                            new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");

                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
        } catch (Exception err) {
            // Nothing
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Descarga Opcional
    public void descargarEnvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean = new ProcesoEnvioBean();
            int n = 0;
            int m = 0;
            int monto = procesoEnvioBean.getTotal();
//            monto = procesoEnvioBean.getTotal();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print("CC");
                    }
                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    }
//                    else {
////                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().trim());
//                        pw.print("   ");
//                    }
                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());//Cambio
                    }
                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());
                    }
                    pw.print("C");//Tipo Validacion
                    pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioBean.get(i).getFechaEmision() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getFechaEmision());
                    }
                    pw.print(i);//Registros
                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    m = m + 1;
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "D");
                    }

                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());
                    }

                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null && listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta().toString().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null) {
                        pw.print("              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null) {
                        pw.print("                                        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null) {
                        pw.print("                              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago()));
//                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().replaceAll(" ", ""));//Cambio
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null) {
                        pw.print("        ");
                    } else {
                        pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc()));
                    }

                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        int sub = listaProcesoEnvioBean.get(i).getMonto().intValue();
                        monto = monto + sub;
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }

                    if (listaProcesoEnvioBean.get(i).getMora() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMora());
                    }
                    pw.println("\n");

                    System.out.println(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                    System.out.println("Exporte Exitoso");
//                System.out.println(procesoEnvioBean.getTotal());
                    System.out.println(monto);
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }
            content = new DefaultStreamedContent(new FileInputStream(new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");
            int s = n + m;
            System.out.println(procesoEnvioBean.getTotal());
            System.out.println(monto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(
                            new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");

                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
        } catch (Exception err) {
            // Nothing
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //Descarga Opcional
    public void descargarEnvios() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean = new ProcesoEnvioBean();
            int n = 0;
            int m = 0;
            int monto = procesoEnvioBean.getTotal();
//            monto = procesoEnvioBean.getTotal();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print("CC");
                    }
                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    }
//                    else {
////                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().trim());
//                        pw.print("   ");
//                    }
                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());//Cambio
                    }
                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());
                    }
                    pw.print("C");//Tipo Validacion
                    pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioBean.get(i).getFechaEmision() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getFechaEmision());
                    }
                    pw.print(i);//Registros
                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\n");
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));

            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
                if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    m = m + 1;
                    if (listaProcesoEnvioBean.get(i).getTipoRegistro() == null) {
                        pw.print("  ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getTipoRegistro() + "D");
                    }

                    if (listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg() == null) {
                        pw.print("   ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getUnidadNegocioBean().getUniNeg());
                    }

                    if (listaProcesoEnvioBean.get(i).getIdMoneda() == null && listaProcesoEnvioBean.get(i).getIdMoneda() == null) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCodMoneda());
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta().toString().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null) {
                        pw.print("              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null) {
                        pw.print("                                        ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null) {
                        pw.print("                              ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", ""));
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago() == null) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago()));
//                    pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().replaceAll(" ", ""));//Cambio
                    }

                    if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null) {
                        pw.print("        ");
                    } else {
                        pw.println(formato.format(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getFechaVenc()));
                    }

                    if (listaProcesoEnvioBean.get(i).getMonto() == null) {
                        pw.print("               ");
                    } else {
                        int sub = listaProcesoEnvioBean.get(i).getMonto().intValue();
                        monto = monto + sub;
                        pw.print(listaProcesoEnvioBean.get(i).getMonto());
                    }

                    if (listaProcesoEnvioBean.get(i).getMora() == null) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioBean.get(i).getMora());
                    }
                    pw.println("\n");

                    System.out.println(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                    System.out.println("Exporte Exitoso");
//                System.out.println(procesoEnvioBean.getTotal());
                    System.out.println(monto);
                }
                if (listaProcesoEnvioBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }
            content = new DefaultStreamedContent(new FileInputStream(new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");
            int s = n + m;
            System.out.println(procesoEnvioBean.getTotal());
            System.out.println(monto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void ponerCuenta(Object object) {
        try {
            CuentaBancoBean cuenta = (CuentaBancoBean) object;
//            ProcesoEnvioBean envio = (ProcesoEnvioBean) object;
            procesoEnvio.getProcesoBancoBean().setCuentaBancoBean(cuenta);
//            setProcesoEnvio(envio);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstudiantes(Object object) {
        try {
            EstudianteBean estudiante = (EstudianteBean) object;
//            ProcesoEnvioBean envio = (ProcesoEnvioBean) object;
            procesoEnvioFiltroBean.setIdDiscente(estudiante);
//            setProcesoEnvio(envio);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerEstudianteFiltro(SelectEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            MatriculaBean matricula = (MatriculaBean) event.getObject();
            cuentasPorCobrarFiltroBean.getEstudianteBean().setPersonaBean(matricula.getEstudianteBean().getPersonaBean());
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCtaFiltro = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matricula.getEstudianteBean().getIdEstudiante());
            for (int i = 0; i < listaCtaFiltro.size(); i++) {
                if (listaCtaFiltro.get(i).getFlgEnvio().equals(false)) {
                    if (i == 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CAMBIO REALIZADO", ""));
                    }
                    listaCtaFiltro.get(i).getIdCtasXCobrar();
                    listaCtaFiltro.get(i).setFlgEnvio(true);
                    procesoEnvioService.modificarStatusEnvioCuenta(listaCtaFiltro.get(i));
                } else {
                    if (listaCtaFiltro.get(i).getFlgEnvio().equals(true)) {
                        if (i == 1) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "CAMBIO REALIZADO", ""));
                        }
                        listaCtaFiltro.get(i).getIdCtasXCobrar();
                        listaCtaFiltro.get(i).setFlgEnvio(false);
                        procesoEnvioService.modificarStatusEnvioCuenta(listaCtaFiltro.get(i));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "CAMBIO NO REALIZADO", ""));
                    }
                }
            }
            Integer res = 0;
            for (CuentasPorCobrarBean ctas : listaCtaFiltro) {
                if (ctas.getIdCtasXCobrar() != null && ctas.getFlgEnvio().equals(false)) {
                    res = ctas.getMonto().intValue() + res;
                } else {
                    if (ctas.getIdCtasXCobrar() != null && ctas.getFlgEnvio().equals(true)) {
                        res = ctas.getMonto().intValue() + res;
                    }
                }
            }
            mostrarLayout = true;
            envio = listaCtaFiltro.size();
            totalEnvios = res;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEnvio() {
        try {
            procesoEnvio = new ProcesoEnvioBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarMasivoEnvio() {
        String pagina = null;
        try {
            Integer codProBanco = 0, codBanco = 0, money = 0, meses = 0, an = 0;
            String anios = "";
            Float monto = null;
            java.util.Date fecha = new Date();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            codProBanco = procesoEnvioService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            if (procesoEnvio.getAnio() == null) {
                anios = "";
                an = 0;
            } else {
                anios = procesoEnvio.getAnio();
                an = Integer.parseInt(procesoEnvio.getAnio());
            }

            if (listaCtaFiltro.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NO SE REALIZO LA OPERACI覰", ""));
            } else {
                if (!listaCtaFiltro.isEmpty()) {
                    money = (procesoEnvio.getMoneda()) ? 1 : 0;
                    //Insercion Proceso Envio
                    for (CuentasPorCobrarBean cta : listaCtaFiltro) {
                        procesoEnvioService.insertarMasivoEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cta.getEstudianteBean().getIdEstudiante(), beanUsuarioSesion.getUsuario(), procesoEnvio.getTipoRegistro(), money, codBanco, mesEnvio);
                    }
                    monto = procesoEnvioService.obtenerSumaEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anios, codBanco);
                    //Insercion Proceso Banco
                    procesoEnvioService.insertarMasivoBanco(procesoEnvio, listaCtaFiltro.size(), monto, an, fechaEnvio);
                    limpiarBusqueda();
                    mostrarTabla = false;
                    mostrarBoton = true;
                    mostrarBotonS = false;
                    listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codBanco);
                    indexAccNuevo = 1;
                    listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String insertarEnvioMasivo() {
        String pagina = null;
        Integer codProBanco = 0;
        Integer codBanco = 0;
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            codProBanco = procesoEnvioService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
//            obtenerEnvioPorFiltro();
//            listaProcesoEnvioBean = procesoEnvioService.obtenerEnvioPorFiltro(procesoEnvioFiltroBean);
            listaProcesoBancoFiltroBean = new ArrayList<>();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //Lista de Cuentas
            //listaCtaFiltro = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (listaCtaFiltro.isEmpty()) { //|| listaProcesoEnvioBean.isEmpty()
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NO SE REALIZO LA OPERACI覰", ""));
            } else {
                for (int i = 0; i < listaCtaFiltro.size(); i++) {
                    procesoEnvio.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                    procesoEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
                    procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(codProBanco);
                    procesoBancoBean.setCodUniNeg(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                    procesoBancoBean.setRuc(procesoEnvio.getProcesoBancoBean().getCuentaBancoBean().getEntidadBancoBean().getRuc());
                    procesoBancoBean.setFecha(procesoEnvio.getFechaEmision());
                    procesoEnvio.getProcesoBancoBean().setIdProcesoBanco(codBanco);
                    procesoEnvioService.insertarEnvioMasivo(procesoEnvio, cuentasPorCobrarBean, listaCtaFiltro, listaProcesoEnvioBean);
//                    limpiarEnvio();
                    limpiarBusqueda();
                    mostrarTabla = false;
                    mostrarBoton = true;
                    mostrarBotonS = false;
//                      mostrarBoton2 = false;
                    listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codBanco);
                    indexAccNuevo = 1;
                    obtenerProcesoEnvioPorId2(procesoBancoBean);
                    listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                    listaProcesoEnvioBean = procesoEnvioService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    Integer a = 0, b = 0, c = 0, d = 0, sum = 0;
                    Integer j;
                    for (j = 0; j < listaProcesoEnvioFiltroBean.size(); j++) {
                        if (listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getPersonaBean().getNombre() == null) {
                            a++;
                        }
                        if (listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getPersonaBean().getApepat() == null) {
                            b++;
                        }
                        if (listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getPersonaBean().getApemat() == null) {
                            c++;
                        }
                        if (listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getPersonaBean().getIdPersona() == null) {
                            d++;
                        }
                    }
                    sum = a + b + c + d;
                    //Registro de Errores
                    procesoBancoBean.setIdProcesoBanco(codProBanco);
                    procesoBancoBean.setRegError(sum);
                    procesoBancoService.modificarErroresProc(procesoBancoBean);
                    System.out.println("=>  " + a);
                    System.out.println("=>  " + b);
                    System.out.println("=>  " + c);
                    System.out.println("=>  " + d);
                    System.out.println("=>  " + sum);
                    System.out.println("Operacion Correcta");
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    System.out.println("Operacion Exitosa");
                    break;
                }
            }
//            for (int i = 0; i < listaCtaFiltro.size(); i++) {
//                if (listaCtaFiltro.get(i).getFlgEnvio().equals(true)) {
//                    listaCtaFiltro.get(i).setFlgEnvio(false);
//                    procesoEnvioService.modificarStatusEnvioCuenta(listaCtaFiltro.get(i));
//                }
//            }
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            procesoFinalService.execProErrores(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), codProBanco + 1, beanUsuarioSesion.getUsuario(), 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerUltimo() {
        try {
            Integer codProBanco = 0;
            Integer codBanco = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codProBanco = procesoEnvioService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codProBanco);
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(codProBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("Operacion Exitosa");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String modificarProcesoEnvio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoEnvioBean = new ProcesoEnvioBean();
            procesoEnvioBean.setModiPor(beanUsuarioSesion.getUsuario());
            procesoEnvioService.modificarProcesoEnvio(procesoEnvioBean);
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                limpiarProcesoEnvio();
            System.out.println("Modificacion exitosa");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String actualizarEnvio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerProcesoEnvioPorId(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            procesoRecuperacionService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarMasivo() {
        String pagina = null;
        try {
            if (pagina == null) {
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                Integer codBanco;
                ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
                listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
                listaProRec = procesoRecuperacionService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                procesoBancoService.eliminarProcesoBancoMas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                cuentasPorCobrarService.actualizarCuentaBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                obtenerFiltroProceso();
                listaProEnvios = new ArrayList<>();
                procesoBancoFiltroBean = new ProcesoBancoBean();
                listaProRec = new ArrayList<>();
                listaProcesoEnvioFiltroBean = new ArrayList<>();
                procesoBancoBean = new ProcesoBancoBean();
                listaProEnvios = new ArrayList<>();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerProcesoEnvioPorId2(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            Integer flgProceso = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProRec = procesoRecuperacionService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (procesoBancoBean.getFlgProceso().equals(1)) {
                listaProEnvios = procesoFinalService.execProListaBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
                filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, procesoBancoBean.getRuc());
                filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, procesoBancoBean.getRuc());
                filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, procesoBancoBean.getRuc());
            } else {
                if (procesoBancoBean.getFlgProceso().equals(0)) {
                    listaProEnvios = procesoFinalService.execProListaBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 1);
                    filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, procesoBancoBean.getRuc());
                    filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileCabecera, procesoBancoBean.getRuc());
                    filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileIntermedio, procesoBancoBean.getRuc());
                }
            }
            System.out.println("elementos: " + filas + " / " + filasCab + " / " + filasInt);
            if (procesoBancoBean.getRegError() != null) {
                if (procesoBancoBean.getRegError().equals(0)) {
                    error = 0;
                } else {
                    if (!procesoBancoBean.getRegError().equals(0)) {
                        error = 1;
                    }
                }
            }
            idProceso = procesoBancoBean.getIdProcesoBanco();
            rucEntidad = procesoBancoBean.getRuc();
//            generaTabla();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarProcesoEnvio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                procesoEnvioService.eliminarProcesoEnvio(procesoEnvioBean.getIdProcesoEnvio());
                listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void buscarPersonal() {
        try {
            listaPersonaBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            personaFiltroBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            listaPersonaBean = procesoEnvioService.buscarPersona(personaFiltroBean);
            listaCtasXCobrarBean = new ArrayList<>();
            listaconceptoBean = new ArrayList<>();
            personaBean = new PersonaBean();
            personaBean.setColl(false);
            if (listaPersonaBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarCuentas() {
        try {
            listaCtasXCobrarBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cuentasFiltroBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            listaCtasXCobrarBean = procesoEnvioService.buscarCuentas(cuentasFiltroBean);
            listaCtasXCobrarBean = new ArrayList<>();
            if (listaCtasXCobrarBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //==============================================================================
    public void obtenerProcesoRecuperacion() {
        try {
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProRec = procesoRecuperacionService.obtenerProcesoRec();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerRecuperacion() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaRecuperacion = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void upload(FileUploadEvent event) {

//        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatricula();
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
//            byte[] b = file.getContents();
            byte[] b = new byte[(int) file.getSize()];
            miArchivo.read(b);
            miArchivo.close();
            String resultado = new String(b);
            System.out.println(" Lo  que lei es: " + resultado);
            System.out.println(resultado);
            Float s;

            listaProRec = new ArrayList<>();
            listaProcesoBancoFiltroBean = new ArrayList<>();
            String[] arreglo = resultado.split("\n");

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");

            //Datos de Cabecera
            String celda111 = "";//C1
            String celda121 = "";//C2
//            Integer codsuc = Integer.parseInt(celda121);
            String celda131 = "";//C3
//            Integer tipoMoneda = Integer.parseInt(celda131);
            String celda141 = "";//C4
//            Integer cuenta = Integer.parseInt(celda141);
            String celda151 = "";// 
            String celda161 = "";//C6
            String celda171 = "";//C7
            String celda181 = "";//C8
//            Float total = Float.parseFloat(celda181);
            String celda191 = "";//C9
            String celda122 = "";//C10
            String celda123 = "";//C11
            String celda124 = "";//C12
            //Datos del Detalle
            String celda112 = "";//1
            String celda1220 = "";//2
            String celda132 = "";//3
            String celda142 = "";//4
            String celda152 = "";//5
            String celda162 = "";//6
            String celda172 = "";//7
            String celda182 = "";//8
            String celda192 = "";//9
            String celda103 = "";//10
            String celda113 = "";//11
            String celda1230 = "";//12
            String celda133 = "";//13
            String celda143 = "";//14
            String celda153 = "";//15
            String celda163 = "";//16
            String celda173 = "";//17
            String celda183 = "";//18
            String celda193 = "";//19
            String celda104 = "";//20
            String celda114 = "";//21
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date horaCorte;
            Integer a = 0;
            Integer a0 = 0;
            Integer a1 = 0;
            Integer a2 = 0;
            Integer fa = 0;
            Integer fa1 = 0;
            Integer num;
            num = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            String sfdg = a.toString();

//            for (int j = 0; j < listaMatriculaBean.size(); j++) {
            for (int i = 0; i < arreglo.length; i++) {
                String txtRegistro = arreglo[i].substring(0, 2);
                String txtSucursal = arreglo[i].substring(2, 5);
                String txtNumCuenta = arreglo[i].substring(6, 13);
                String txtCodAlumon = arreglo[i].substring(13, 27);
                if (arreglo.length > i || !txtRegistro.equals("CC") || !txtSucursal.equals("193") || !txtNumCuenta.equals("1428600")) {
                    System.out.println("Paso");
                    ProcesoRecuperacionBean procesoRecuperacion = new ProcesoRecuperacionBean();
                    String celda11 = arreglo[i].substring(0, 2);
                    if (celda11.equals("CC")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo Correcto", ""));
                        a1 = a1 + 1;
                        System.out.println("-----------------Cabecera---------------------");
                        celda111 = arreglo[i].substring(0, 2);//1   TIPOrEGISTRO celda111, codsuc ,cuenta ,celda161,celda171,celda191
                        celda121 = arreglo[i].substring(2, 5);//2   CODSUCURSAL
                        Integer codsuc = Integer.parseInt(celda121);
                        celda131 = arreglo[i].substring(5, 6);//3   MONEDA
                        Integer tipoMoneda = Integer.parseInt(celda131);
                        celda141 = arreglo[i].substring(6, 13);//4  NUMCUENTA
                        Integer cuenta = Integer.parseInt(celda141);
                        celda151 = arreglo[i].substring(13, 14);//5 TIPOVALIDACION
                        celda161 = arreglo[i].substring(14, 22);//6 FECHA PROCESO
                        celda171 = arreglo[i].substring(22, 31);//7 CANTIDAD TOTAL
                        celda181 = arreglo[i].substring(31, 46);//8 MONTO
                        Float total = Float.parseFloat(celda181);
                        celda191 = arreglo[i].substring(46, 50);//9 CODBCP
                        celda122 = arreglo[i].substring(50, 57);//10 CASILLA
                        celda123 = arreglo[i].substring(57, 62);//11
                        celda124 = arreglo[i].substring(62, 200);//12
                        String f1 = celda161.substring(0, 4);
                        String f2 = celda161.substring(4, 6);
                        String f3 = celda161.substring(6, 8);
                        String fecha = f1 + "-" + f2 + "-" + f3;
                        //Proceso Recuperacion
                        procesoRecuperacion.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        procesoRecuperacion.getProcesoBancoBean().setIdProcesoBanco(num);//a Cambiar
                        procesoRecuperacion.getEstudianteBean().setIdEstudiante(null);//a Cambiar
                        procesoRecuperacion.getConceptoBean().setIdConcepto(null);//a cambiar
                        procesoRecuperacion.setIdMoneda(celda131);//a cambiar
                        procesoRecuperacion.setMonto(null);//a cambiar
                        procesoRecuperacion.setMora(null);//a cambiar
                        procesoRecuperacion.setMonto(total);//a cambiar
                        procesoRecuperacion.setMontoEnv(total);
                        procesoRecuperacion.setMontoRecup(total);
                        procesoRecuperacion.setFechaPago(formato.parse(fecha));//a cambiar
                        procesoRecuperacion.getCuentasPorCobrarBean().setIdCtasXCobrar(null);//a 
                        procesoRecuperacion.setCuentaAfiliada(cuenta);//a cambiar
                        procesoRecuperacion.setDatoAdicionalDep(null);//a cambiar
                        procesoRecuperacion.setFechaVen(null);//a cambiar
                        procesoRecuperacion.setAgencia(codsuc);//a cambiar
                        procesoRecuperacion.setNumOperacion(i + 1);//a cambiar
                        procesoRecuperacion.setReferencia(celda151);//a cambiar
                        procesoRecuperacion.setTerminal(celda191);//a cambiar
                        procesoRecuperacion.setMedioAtencion(null);//a cambiar
                        procesoRecuperacion.setHoraAtencion(null);//a cambiar
                        procesoRecuperacion.setCreaPor(beanUsuarioSesion.getUsuario());//a cambiar
//                        listaProRec.add(procesoRecuperacion);
//                        procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
                    } else {
                        Integer n = 1;
                        a2 = a2 + 1;
                        int var = getMontoTotal();
                        Integer f = procesoRecuperacion.getProcesoBancoBean().getIdProcesoBanco();
                        var = var + 1;
                        //Proceso Recuperacion
                        System.out.println("-----------------Detalle---------------------" + i);
                        celda112 = arreglo[i].substring(0, 2);//1 tipoReg
                        celda1220 = arreglo[i].substring(2, 5);//2 codSuc
                        celda132 = arreglo[i].substring(5, 6);//3 moneda
                        Integer moneda = Integer.parseInt(celda132);
                        celda142 = arreglo[i].substring(6, 13);//4 numcuenta
                        Integer cuenta2 = Integer.parseInt(celda142);
                        celda152 = arreglo[i].substring(20, 27);//5 codAlu
                        celda162 = arreglo[i].substring(27, 57);//6 datoAdi

                        celda172 = arreglo[i].substring(57, 65);//7 fecPago
                        String f11 = celda172.substring(0, 4);
                        String f21 = celda172.substring(4, 6);
                        String f31 = celda172.substring(6, 8);
                        String fecha1 = f11 + "-" + f21 + "-" + f31;

                        celda182 = arreglo[i].substring(65, 73);//8 fecVen
                        String f13 = celda172.substring(0, 4);
                        String f23 = celda172.substring(4, 6);
                        String f33 = celda172.substring(6, 8);
                        String fecha2 = f13 + "-" + f23 + "-" + f33;

                        celda192 = arreglo[i].substring(73, 86);//9 importe
                        Float monto = Float.parseFloat(celda192);
                        celda103 = arreglo[i].substring(88, 103);//10 mora
                        Float mora = Float.parseFloat(celda103);
                        celda113 = arreglo[i].substring(103, 116);//11 total
                        Float montoPagado = Float.parseFloat(celda113);
                        celda1230 = arreglo[i].substring(118, 124);//12 codAge
                        Integer agencia = Integer.parseInt(celda1230);
                        celda133 = arreglo[i].substring(124, 130);//13 numOpe
                        Integer numOpe = Integer.parseInt(celda133);
                        celda143 = arreglo[i].substring(130, 152);//14 refe
                        celda153 = arreglo[i].substring(152, 158);//15 teti
                        celda163 = arreglo[i].substring(158, 168);//16 medio
                        celda173 = arreglo[i].substring(168, 174);//17 hora
                        Time horaAtencion = new Time(Long.parseLong(celda173));
                        System.out.println("Atencion =====>  " + horaAtencion);
//                    Date date = sdf.parse(celda173);
////                    java.sql.Time horario = new java.sql.Time(date.getHours());
//                    Time hora = new Time(sdf.parse("23:44").getTime());
                        celda183 = arreglo[i].substring(174, 184);//18 numCheque
                        celda193 = arreglo[i].substring(184, 186);//19 codBanco
                        celda104 = arreglo[i].substring(186, 196);//20 cargo
                        celda114 = arreglo[i].substring(196, 200);//21 filler

                        s = montoPagado + mora + monto;

                        procesoRecuperacion.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        procesoRecuperacion.getProcesoBancoBean().setIdProcesoBanco(num);//a Cambiar
                        procesoRecuperacion.getEstudianteBean().setIdEstudiante(celda152);//a Cambiar 
                        procesoRecuperacion.getConceptoBean().setIdConcepto(null);//a cambiar
//                    procesoRecuperacion.setIdMoneda(celda132);//a cambiar moneda
                        procesoRecuperacion.setMoneda(Integer.parseInt(celda132));
//                        procesoRecuperacion.setMonto(monto);//a cambiar monto
                        procesoRecuperacion.setMora(mora);//a cambiar mora
                        procesoRecuperacion.setMonto(monto);//a cambiar montoPagado
                        procesoRecuperacion.setMontoEnv(null);//a cambiar montoPagado
                        procesoRecuperacion.setMontoRecup(montoPagado);//a cambiar montoPagado antes s
                        procesoRecuperacion.setFechaPago(formato.parse(fecha1));//a cambiar fechaPago
//                    procesoRecuperacion.setFechaPago(null);//a cambiar fechaPago
                        procesoRecuperacion.getCuentasPorCobrarBean().setIdCtasXCobrar(null);//a 
                        procesoRecuperacion.setCuentaAfiliada(cuenta2);//a cambiar
                        procesoRecuperacion.setDatoAdicionalDep(celda162);//a cambiar
//                    procesoRecuperacion.setFechaVen(formato.parse(celda182));//a cambiar 
                        procesoRecuperacion.setFechaVen(formato.parse(fecha2));//a cambiar fechaVen
                        procesoRecuperacion.setAgencia(agencia);//a cambiar
                        procesoRecuperacion.setNumOperacion(numOpe);//a cambiar numOpe
                        procesoRecuperacion.setReferencia(celda143);//a cambiar refe
                        procesoRecuperacion.setTerminal(celda153);//a cambiar
                        procesoRecuperacion.setMedioAtencion(celda163);//a cambiar
                        procesoRecuperacion.setHoraAtencion(horaAtencion);//a cambiar
                        procesoRecuperacion.setCreaPor(beanUsuarioSesion.getUsuario());//a cambiar
                        //A cambiar
//                    ProcesoBancoBean procesoBanco = new ProcesoBancoBean();
//                    procesoBanco.setRegEnv(a2);
                        listaProRec.add(procesoRecuperacion);
                        procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
                        System.out.println(var);
                        System.out.println(s);
                        for (int k = 0; k < listaProRec.size(); k++) {
                            if (celda111 != null && celda121 != null && celda141 != null && celda161 != null && celda171 != null && celda191 != null) {
                                fa++;
                                System.out.println("Recup:   ======================");
                                System.out.println(fa);
                                break;
                            } else {
                                fa1++;
                                System.out.println("Errores r:   ======================");
                                System.out.println(fa1);
                                break;
                            }
                        }
                        System.out.println("Respuesta" + fa);
                        System.out.println("Respuesta" + fa1);
                    }
                } else {
                    System.out.println("No Paso");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo InCorrecto", ""));
                }
//                listaProRec.add(procesoRecuperacion);
//                procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
            }
//            }

            for (int i = 0; i < arreglo.length; i++) {
                ProcesoBancoBean procesoBanco = new ProcesoBancoBean();
                String celda11 = arreglo[i].substring(0, 2);
                String txtRegistro = arreglo[i].substring(0, 2);
                String txtSucursal = arreglo[i].substring(2, 5);
                String txtNumCuenta = arreglo[i].substring(6, 13);
                String txtCodAlumon = arreglo[i].substring(13, 27);
                if (arreglo.length > i || !celda11.equals("CC") || !txtSucursal.equals("193") || !txtNumCuenta.equals("1428600")) {
                    System.out.println("Paso");
                    if (celda11.equals("CC")) {
                        a = a + 1;
                    }
                    if (celda11.equals("CC")) {
                        a0 = a0 + 1;
                        System.out.println("-----------------Cabecera Banco---------------------");
                        celda111 = arreglo[i].substring(0, 1);//1   TIPOrEGISTRO 
                        celda121 = arreglo[i].substring(2, 5);//2   CODSUCURSAL
                        Integer codsuc = Integer.parseInt(celda121);
                        celda131 = arreglo[i].substring(5, 6);//3   MONEDA
                        Integer tipoMoneda = Integer.parseInt(celda131);
                        celda141 = arreglo[i].substring(6, 13);//4  NUMCUENTA
                        Integer cuenta = Integer.parseInt(celda141);
                        celda151 = arreglo[i].substring(13, 14);//5 TIPOVALIDACION
                        celda161 = arreglo[i].substring(14, 22);//6 FECHA PROCESO
                        String fec1 = celda161.substring(0, 4);
                        String fec2 = celda161.substring(4, 6);
                        String fec3 = celda161.substring(6, 8);
                        String fecha = fec1 + "-" + fec2 + "-" + fec3;
//                    SimpleDateFormat formato = new SimpleDateFormat( "yyyy-MM-dd");
                        Date date = formato.parse(fecha);
//                            20150317
//                    procesoBanco.setFecha(format.parse(fecha));//a cambiar fechaPago
                        celda171 = arreglo[i].substring(22, 31);//7 CANTIDAD TOTAL
                        celda181 = arreglo[i].substring(31, 46);//8 MONTO
                        Float total = Float.parseFloat(celda181);
                        celda191 = arreglo[i].substring(46, 50);//9 CODBCP
                        Integer codUniNeg = Integer.parseInt(celda191);
                        celda122 = arreglo[i].substring(50, 57);//10 CASILLA
                        celda123 = arreglo[i].substring(57, 62);//11
                        celda124 = arreglo[i].substring(62, 200);//12
                        celda173 = arreglo[i].substring(168, 174);//17 hora
                        Date horaActual = new Date(System.currentTimeMillis());
                        String horaCorteProceso = (horaActual.getHours() + ":" + horaActual.getMinutes() + ":" + horaActual.getSeconds());
                        System.out.println("Corte====>   " + horaCorteProceso);
                        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
                        Date horaCorteProc = formatter.parse(horaCorteProceso);
                        System.out.println(a);
                        //Proceso Banco
                        procesoBanco.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        procesoBanco.setAnio(Integer.parseInt(fec1));
                        procesoBanco.setRuc("20100047218");
                        procesoBanco.setNumCuenta("1428600");
                        procesoBanco.setFlgProceso(0);
                        procesoBanco.setMoneda(false);
                        procesoBanco.setCodUniNeg(codUniNeg.toString());
                        procesoBanco.setNombre("Recuperacion");
                        procesoBanco.setTipoArchivo(celda111);
                        procesoBanco.setFecha(date);
                        procesoBanco.setRegEnv(a2);
                        procesoBanco.setRegError(0);
                        procesoBanco.setMontoRecup(total);
                        procesoBanco.setMontoEnv(total);
                        procesoBanco.setHoraCorte(horaCorteProc);//cambio
                        procesoBanco.setCreaPor(beanUsuarioSesion.getUsuario());//a cambiar
                        listaProcesoBancoFiltroBean.add(procesoBanco);
                        procesoBancoService.insertarProcesoBanco(procesoBanco);
                        for (int j = 0; j < listaProcesoBancoFiltroBean.size(); j++) {
                            if (celda111 != null && celda121 != null && celda141 != null && celda161 != null && celda191 != null && celda181 != null) {
                                System.out.println("Bien:    ======================");
                                System.out.println(j);
                                break;
                            } else {
                                System.out.println("Errores b:    ======================");
                                System.out.println(j);
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("No Paso");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo InCorrecto", ""));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo Proceso: " + a0, "Se inserto " + a0 + " nuevo Proceso"));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Titulo: " + a1, "Un Nuevo Titulo" + a1 + ""));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevos Registros:" + a2, "Nuevos Registros:" + a2));
            if (!fa.equals(0)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registros Corectos" + fa, ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registros No Corectos" + fa1, ""));
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.addCallbackParam("Proceso", a0);
            context.addCallbackParam("Titulo", a1);
            context.addCallbackParam("Registros", a2);

//            listaProRec = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            Integer codBanco;
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codBanco = procesoRecuperacionService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codBanco);
            listaProRec = procesoRecuperacionService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            codBanco = procesoRecuperacionService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaProRec = procesoRecuperacionService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            mostrarRec = true;
            mostrarLayout = true;

            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCtasXCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Modificar Cuentas Por Cobrar
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));

            CodigoBean bean = new CodigoBean();
            bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
            bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));
//            Integer codigo = codigoService.obtenerPorTipo(new MaristaConstantes.);
            for (ProcesoRecuperacionBean rec : listaProRec) {
                listaCtasXCobrarBean = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rec.getEstudianteBean().getIdEstudiante());
                for (CuentasPorCobrarBean ctas : listaCtasXCobrarBean) {
                    //Obteniendo Fecha de Recuperacion
                    String fechaRecup = formato.format(rec.getFechaVen());
                    String fechaRecup1[] = fechaRecup.split("-");
                    String fechaRecup11 = fechaRecup1[0];
                    String fechaRecup12 = fechaRecup1[1];
                    String fechaRecup13 = fechaRecup11 + fechaRecup12;

                    String fechaCuenta = formato.format(ctas.getFechaVenc());
                    String fechaCuenta1[] = fechaCuenta.split("-");
                    String fechaCuenta11 = fechaCuenta1[0];
                    String fechaCuenta12 = fechaCuenta1[1];
                    String fechaCuenta13 = fechaCuenta11 + fechaCuenta12;

                    if (fechaRecup13.equals(fechaCuenta13) && ctas.getEstudianteBean().getIdEstudiante().equals(rec.getEstudianteBean().getIdEstudiante())) {
                        BigDecimal montoPagado = new BigDecimal(rec.getMontoRecup());
                        ctas.setMontoPagado(montoPagado);
                        ctas.getProcesoRecuperacionBean().setIdProcesoRecup(rec.getIdProcesoRecup());
                        ctas.setModiPor(beanUsuarioSesion.getUsuario());
                        cuentasPorCobrarService.modificarCuenta(ctas);
                    }
                    if (fechaRecup13.equals(fechaCuenta13) && ctas.getEstudianteBean().getIdEstudiante().equals(rec.getEstudianteBean().getIdEstudiante())
                            && rec.getIdProcesoRecup() != null && rec.getProcesoBancoBean().getIdProcesoBanco() != null
                            && ctas.getIdTipoStatusCtaCte().getIdCodigo() != null
                            && !ctas.getIdTipoStatusCtaCte().getIdCodigo().equals(codigoTipoStatusCtaCte.getIdCodigo())) {
                        rec.setFlgConcilia(1);
                        rec.getCuentasPorCobrarBean().setIdCtasXCobrar(ctas.getIdCtasXCobrar());
                        rec.getConceptoBean().setIdConcepto(ctas.getConceptoBean().getIdConcepto());
                        System.out.println(">>>>>>>>>>>>>>>>>>>" + ctas.getMonto());
                        rec.setMontoEnviado(ctas.getMonto());
                        rec.setModiPor(beanUsuarioSesion.getUsuario());
                        procesoRecuperacionService.modificarConcilia(rec);
                    }
                }
            }

            Integer n = 0;
            for (int i = 0; i < listaCtasXCobrarBean.size(); i++) {
                for (int j = 0; j < listaProRec.size(); j++) {
                    BigDecimal montoPagado = new BigDecimal(listaProRec.get(j).getMontoRecup());
//                    BigDecimal mora = new BigDecimal(listaProRec.get(j).getMora());
                    if (!listaCtasXCobrarBean.get(i).getEstudianteBean().getPersonaBean().getIdPersona().equals(listaProRec.get(j).getEstudianteBean().getPersonaBean().getIdPersona())) {
                        System.out.println("======================================================");
                        n = n + 1;
                        System.out.println("Dato: " + n);
                    }
                    if (!listaCtasXCobrarBean.get(i).getEstudianteBean().getPersonaBean().getIdPersona().equals(listaProRec.get(j).getEstudianteBean().getPersonaBean().getIdPersona())) {
                        System.out.println("======================================================");
                        Integer m = listaProRec.size();
                        Integer resul = m - n;
                        System.out.println("Totales: " + resul);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos No Procesados:" + resul, resul.toString()));
                    }
                    break;
                }
                break;
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Archivo InCorrecto", err.toString()));
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void leer() {
        File f = new File("D:\\Texto.txt");
        BufferedReader entrada;
        List<String> list = new ArrayList<String>();
        try {
            entrada = new BufferedReader(new FileReader(f));
            String linea;
            String a;

            String substring = "";
            String substring12 = "";
            String substring11 = "";
            String substring10 = "";
            String substring9 = "";
            String substring8 = "";
            String substring7 = "";
            String substring6 = "";
            String substring5 = "";
            String substring4 = "";
            String substring3 = "";
            String substring2 = "";

            list = Files.readAllLines(f.toPath(), Charset.defaultCharset());
            while (entrada.ready()) {
                linea = entrada.readLine();
                substring = linea.substring(1, 3);
                if (substring == "CC") {
                    substring2 = linea.substring(1, 3);
                    System.out.println("Cabecera");
                }
                if (substring == "DD") {
                    substring3 = linea.substring(1, 3);
                    System.out.println("Detalle");
                }
            }
            System.out.println(substring);
            System.out.println(substring2);
            System.out.println(substring3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Prueba de Recuperacion
    public void obtenerFileRecuperacion(FileUploadEvent event) {
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            System.out.println("nombre txt:");
//            byte[] b = file.getContents();
            byte[] b = new byte[(int) file.getSize()];
            miArchivo.read(b);
            miArchivo.close();
            String resultado = new String(b);
            String[] arreglo = resultado.split("\n");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();

            //Declarando Variables
            String ruc = procesoBanco.getRuc();
            //Obteniendo ubicaciones de los archivos
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));

            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, 1, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta

            List<String> listaCabeceraBanco = new ArrayList<>(); //Cabecera de Archivo
            List<String> listaDetalleBanco = new ArrayList<>(); //Detalle de Archivo
            List<ProcesoRecuperacionBean> listaRecuperacion = new ArrayList<>();

            List<List<String>> listaDetallesElementos = new ArrayList<>();
//            HashMap<Integer, String> listaProBancos = new HashMap<Integer, String>();

            Integer cabecera = 0;
            Integer afterListCab = 0;
            Integer beforeListCab = 0;
            afterListCab = listaProcesoFilesBeanDeta.size();

            Integer idProcesoBanco = 0;
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoBancoBean bancoBean = new ProcesoBancoBean();
            ProcesoRecuperacionBean recuperacionBean = new ProcesoRecuperacionBean();//Detalle de Archivo

            String[][] rpta = new String[listaProcesoFilesBean.size()][arreglo.length];
            String[][] rptas = new String[listaProcesoFilesBeanDeta.size()][arreglo.length];

            // Insertando ProcesoBanco ==============================================================================================================================================//              
            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)
                    || beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                if (!listaProcesoFilesBean.isEmpty() || !listaProcesoFilesBeanDeta.isEmpty()) {
                    for (int i = 0; i < arreglo.length; i++) {
                        String fila = arreglo[i];
                        if (!listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                rptas[k][i] = secciona(fila, listaProcesoFilesBeanDeta.get(k).getPosicionIni(), listaProcesoFilesBeanDeta.get(k).getPosicionFin());
                            }
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        var++;
                        if (!listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                String filaD = rptas[k][i];
                                if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                    String fec1 = filaD.substring(0, 4);
                                    String fec2 = filaD.substring(4, 6);
                                    String fec3 = filaD.substring(6, 8);
                                    String fecha = fec1 + "-" + fec2 + "-" + fec3;
                                    listaDetalleBanco.add(fecha);
                                } else {
                                    if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                        if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                                            String money = filaD.substring(0, filaD.length() - 2);
                                            System.out.println(">>>>>" + money);
                                            listaDetalleBanco.add(money);
                                        } else {
                                            String dinero = filaD.substring(0, filaD.length() - 2);
                                            String dineroAfter = filaD.substring(filaD.length() - 2, filaD.length());
                                            System.out.println(dinero);
                                            System.out.println(">>>>>");
                                            System.out.println(dineroAfter);
                                            listaDetalleBanco.add(dinero.concat(".").concat(dineroAfter));
                                        }
                                    } else {
                                        listaDetalleBanco.add(filaD);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                if (!listaProcesoFilesBean.isEmpty() || !listaProcesoFilesBeanDeta.isEmpty()) {
                    for (int i = 0; i < arreglo.length; i++) {
                        String fila = arreglo[i];
                        if (i == 0 && !listaProcesoFilesBean.isEmpty()) {
                            for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                                rpta[j][0] = secciona(fila, listaProcesoFilesBean.get(j).getPosicionIni(), listaProcesoFilesBean.get(j).getPosicionFin());
                            }
                        } else if (i != 0 && !listaProcesoFilesBeanDeta.isEmpty()) {
                            for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                rptas[k][i] = secciona(fila, listaProcesoFilesBeanDeta.get(k).getPosicionIni(), listaProcesoFilesBeanDeta.get(k).getPosicionFin());
                            }
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        var++;
                        if (i == 0 && !listaProcesoFilesBean.isEmpty()) {
                            for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                                String filaC = rpta[j][0];
                                listaCabeceraBanco.add(filaC);
                            }
                        } else {
                            if (i != 0 && !listaProcesoFilesBeanDeta.isEmpty()) {
                                for (int k = 0; k < listaProcesoFilesBeanDeta.size(); k++) {
                                    String filaD = rptas[k][i];
                                    if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                        String fec1 = filaD.substring(0, 4);
                                        String fec2 = filaD.substring(4, 6);
                                        String fec3 = filaD.substring(6, 8);
                                        String fecha = fec1 + "-" + fec2 + "-" + fec3;
                                        listaDetalleBanco.add(fecha);
                                    } else {
                                        if (listaProcesoFilesBeanDeta.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                            if (beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                                                String money = filaD.substring(0, filaD.length() - 2);
                                                System.out.println(">>>>>" + money);
                                                listaDetalleBanco.add(money);
                                            } else {
                                                String dinero = filaD.substring(0, filaD.length() - 2);
                                                String dineroAfter = filaD.substring(filaD.length() - 2, filaD.length());
                                                System.out.println(dinero);
                                                System.out.println(">>>>>");
                                                System.out.println(dineroAfter);
                                                listaDetalleBanco.add(dinero.concat(".").concat(dineroAfter));
                                            }
                                        } else {
                                            listaDetalleBanco.add(filaD);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //Numero de Columnas del Archivo
            Integer numFilas = 0;
            numFilas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, ruc);

            List<String> listaDeta = new ArrayList<>();
            Integer a = 0;
            Integer c = 0;
            Integer d = 0;
            d = listaProcesoFilesBeanDeta.size();
            c = listaDetalleBanco.size() / listaProcesoFilesBeanDeta.size();
            for (int i = 0; i < c; i++) {
                ProcesoRecuperacionBean recuperacion = new ProcesoRecuperacionBean();
                listaDeta = listaDetalleBanco.subList(a, d);
                if (!listaDeta.isEmpty()) {
                    listaDetallesElementos.add(listaDeta);
                }
                a = a + numFilas;
                d = d + numFilas;
            }

            /* Ejecutando SP_Recuperacion */
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            procesoFinalService.execProcesoRecup(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), listaProcesoFilesBeanDeta.size(), idProcesoBanco);

            System.out.println("banco => " + idProcesoBanco);
            Integer dato = 0;
            String rucBanco = "";
            rucBanco = procesoBanco.getRuc();
            for (int i = 0; i < listaDetallesElementos.size(); i++) {
                obtenerFileId();
                for (int j = 0; j < listaDetallesElementos.get(i).size(); j++) {
                    procesoFinalService.execProcesoRecupIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), (idProcesoBanco + 1), listaDetallesElementos.get(i).get(j), (j + 1), idFile, beanUsuarioSesion.getUsuario());
                }
            }

            Integer posicion = 0;
            cabecera = generarRecuperacion(file.getFileName());

            /* Obteniendo Listas Actualizadas */
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(idProcesoBanco + 1);
//            listaProBancos = procesoFinalService.obtenerIdList(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco + 1);
//            if (!listaProBancos.isEmpty()) {
//                for (int i = 1; i <= listaProBancos.size(); i++) {
//                    procesoFinalService.execProConcilia(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco + 1, Integer.parseInt(listaProBancos.get(i).getListaContenedor().get(0).toString()), beanUsuarioSesion.getUsuario());
//                }
//            }
            procesoFinalService.execProCtaCte(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), ruc, idProcesoBanco + 1, beanUsuarioSesion.getUsuario());

            Integer proceso = 0;
            proceso = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoFinalService.execProErrores(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), proceso, beanUsuarioSesion.getUsuario(), 2);
//            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(idProcesoBanco + 1);
            //Actualizando CuentasXCobrar
            //Modificar Cuentas Por Cobrar 

            CodigoBean codigoTipoStatusCtaCte = new CodigoBean();
            codigoTipoStatusCtaCte = codigoService.obtenerPorCodigo(new CodigoBean(0, "Pagado", new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE)));
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();

            /* Grabando en el Asiento */
            procesoFinalService.execProAsiento(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", (idProcesoBanco + 1), beanUsuarioSesion.getUsuario(), (idProcesoBanco + 1));
            CodigoBean bean = new CodigoBean();
            bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
            bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));

            //Enviando Log de Errores
            ReporteRechazoService reporteRechazoService = BeanFactory.getReporteRechazoService();
            fail = reporteRechazoService.obtenerCantidadRechazos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idProcesoBanco);

            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cabecera == 1) {
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            }

            // End ==============================================================================================================================================//
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void generarAsiento() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Integer idProcesoBanco = 0;
            ProcesoBancoService procesoBancoService = new ProcesoBancoService();
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            idProcesoBanco = procesoBancoService.obtenerIdProcesoBancoMax(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 0);
            procesoRecuperacionService.execProAsiento(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_ProcesoRecup", 0, "", idProcesoBanco);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public Integer generarRecuperacion(String nombre) {
        Integer cabecera = 0;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            Integer idProcesoBanco = 0, totalRegistros = 0, fileDetalle = 0;
            Float montoRecuperado;
            fileDetalle = MaristaConstantes.FileDetalle;
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            montoRecuperado = procesoRecuperacionService.obtenerMontoTotal((idProcesoBanco + 1), fileDetalle, 1, procesoBanco.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalRegistros = procesoRecuperacionService.obtenerTotalRegistros((idProcesoBanco + 1), procesoBanco.getRuc(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //Insertando Registros
            procesoBanco.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBanco.setCodUniNeg(procesoBanco.getCodUniNeg());
            procesoBanco.setNumCuenta(procesoBanco.getNumCuenta());
            procesoBanco.setMoneda(procesoBanco.getMoneda());
            procesoBanco.setRuc(procesoBanco.getRuc());
            procesoBanco.setFecha(fechaRecup);
            if (nombre != null) {
                procesoBanco.setNombre(nombre);
            } else {
                procesoBanco.setNombre("-");
            }
            procesoBanco.setFlgProceso(0);
            procesoBanco.setTipoArchivo("C");
            procesoBanco.setRegEnv(totalRegistros);
            procesoBanco.setMontoRecup(montoRecuperado);
            procesoBanco.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoBancoService.insertarProcesoBanco(procesoBanco);
            limpiarRecuperacion();
            cabecera = 1;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cabecera;
    }

    public void obtenerFechaRecup() {
        try {
            setFechaRecup(procesoBanco.getFecha());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerNombre() {
        try {
            setNomRecuperacion(procesoBanco.getNombre());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarRecuperacion() {
        try {
            procesoBanco = new ProcesoBancoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarRuc() {
        try {
            Integer moneda = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            if (procesoBanco.getMoneda() == null) {
                procesoBanco.setMoneda(false);
                moneda = 14901;
            } else {
                if (procesoBanco.getMoneda() != null) {
                    if (procesoBanco.getMoneda()) {
                        moneda = 14902;
                    } else {
                        if (!procesoBanco.getMoneda()) {
                            moneda = 14901;
                        }
                    }
                }
            }
            listaCuentaBanco = cuentaBancoService.obtenerPorRuc(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc(), moneda);
            if (!listaCuentaBanco.isEmpty()) {
                RequestContext.getCurrentInstance().addCallbackParam("openModalCodUniNeg", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaBanco(Object object) {
        try {
            CuentaBancoBean cuentaBancoBean = (CuentaBancoBean) object;
            procesoBanco.setCodUniNeg(cuentaBancoBean.getCodUniNeg());
            procesoBanco.setNumCuenta(cuentaBancoBean.getNumCuenta());
            rucEntidad = cuentaBancoBean.getEntidadBancoBean().getRuc();
            System.out.println("ruc: " + rucEntidad);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFileId() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Integer id = 0;
            id = procesoFinalService.obtenerMaxIdFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBanco.getRuc());
            idFile = id + 1;
            setIdFile(idFile);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public static String secciona(String cadena, Integer inicio, Integer fin) {
        String cadenaRpta = null;
        cadenaRpta = cadena.substring((inicio - 1), fin);
        System.out.println(">>>>> " + cadenaRpta);
        return cadenaRpta;
    }

    public void insertRecup(List<String> listaDetalleBanco, List<ProcesoFilesBean> listaProcesoFilesBeanDeta, Integer idProcesoBanco) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            List<String> listaDeta = new ArrayList<>();
            Integer a = 0;
            Integer b = 0;
            Integer c = 0;
            c = listaDetalleBanco.size() / listaProcesoFilesBeanDeta.size();
            System.out.println(">>" + c);
            for (int i = 0; i < c; i++) {
                ProcesoRecuperacionBean recuperacion = new ProcesoRecuperacionBean();
                System.out.println(">>: " + i);
                listaDeta = listaDetalleBanco.subList(a, listaProcesoFilesBeanDeta.size());
                if (!listaDeta.isEmpty()) {
                    recuperacion.setMoneda(Integer.parseInt(listaDeta.get(2))); //3    
                    recuperacion.setCuentaAfiliada(Integer.parseInt(listaDeta.get(3)));//4 
                    recuperacion.getEstudianteBean().setIdEstudiante(listaDetalleBanco.get(4).substring(7, 14));//5   
                    recuperacion.setDatoAdicionalDep(listaDeta.get(5));//6    
                    recuperacion.setFechaPago(formato.parse(listaDeta.get(6)));//7  
                    recuperacion.setFechaVen(formato.parse(listaDeta.get(7)));//8  
                    recuperacion.setMonto(Float.parseFloat(listaDeta.get(8).substring(0, listaDeta.get(8).length() - 2)));//9  
                    recuperacion.setMora(Float.parseFloat(listaDeta.get(9).substring(0, listaDeta.get(9).length() - 2)));
                    recuperacion.setMontoRecup(Float.parseFloat(listaDeta.get(10).substring(0, listaDeta.get(10).length() - 2)));//10  
                    recuperacion.setAgencia(Integer.parseInt(listaDetalleBanco.get(11)));//11  
                    recuperacion.setNumOperacion(Integer.parseInt(listaDeta.get(12)));//12  
                    recuperacion.setReferencia(listaDeta.get(13));//13  
                    recuperacion.setTerminal(listaDeta.get(14));//14  
                    recuperacion.setMedioAtencion(listaDeta.get(15));//15    
                    recuperacion.setHora(listaDeta.get(16));//16  
                    recuperacion.getProcesoBancoBean().setIdProcesoBanco(idProcesoBanco + 1);
                    recuperacion.setCreaPor(beanUsuarioSesion.getUsuario());
                    recuperacion.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    procesoRecuperacionService.insertarRecuperacion(recuperacion);
                    System.out.println("idEstudiante: " + listaDetalleBanco.get(4).substring(7, 14));
                }
                System.out.println("lista: " + listaDetalleBanco.size());
                listaDetalleBanco.subList(a, listaProcesoFilesBeanDeta.size()).clear();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //================================ Proceso File==========================================
    public void obtenerPorFiltro() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
            if (procesoFileFiltro.getEntidadBean().getNombre() != null && beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg() != null) {
                procesoFileFiltro.setEntidadBean(procesoFileFiltro.getEntidadBean());
                procesoFileFiltro.setFlgProceso(procesoFileFiltro.getFlgProceso());
                procesoFileFiltro.setTipoReg(procesoFileFiltro.getTipoReg());
//                procesoFileFiltro.setNumLinea(procesoFileFiltro.getNumLinea());
                procesoFileFiltro.setPosicion(procesoFileFiltro.getPosicion());
                procesoFileFiltro.setCampo(procesoFileFiltro.getCampo());
                procesoFileFiltro.setLongitud(procesoFileFiltro.getLongitud());
                procesoFileFiltro.setIdTipoDato(procesoFileFiltro.getIdTipoDato());
                procesoFileFiltro.setValor(procesoFileFiltro.getValor());
                procesoFileFiltro.setDescripcion(procesoFileFiltro.getDescripcion());
                listaProcesoFileBean = procesoFileService.obtenerPorFiltro(procesoFileFiltro);
            } else if (procesoFileFiltro.getEntidadBean().getNombre() == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
//                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private DefaultTableModel model;

    public void aregarColumns(Object object) {
        try {
            procesoFileBean = (ProcesoFileBean) object;
            model = new DefaultTableModel();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //==========================================================================
    public void buscarXML() {
//        Document doc = new Document();
//        Element root = new Element("sqldata");
//        doc.setRootElement(root);
//        int columna = listaProcesoRecuperacion.size();
//        for (int n = 0; n < columna; n++) {
//            procesoRecuperacionBean.setUnidadNegocioBean(procesoRecuperacionBean.getUnidadNegocioBean());
//            procesoRecuperacionBean.setProcesoBancoBean(procesoRecuperacionBean.getProcesoBancoBean());
//            procesoRecuperacionBean.setCuentasPorCobrarBean(procesoRecuperacionBean.getCuentasPorCobrarBean());
//            procesoRecuperacionBean.setEstudianteBean(procesoRecuperacionBean.getEstudianteBean());
//            procesoRecuperacionBean.setConceptoBean(procesoRecuperacionBean.getConceptoBean());
//            procesoRecuperacionBean.setMonedaCodigo(procesoRecuperacionBean.getMonedaCodigo());
//            procesoRecuperacionBean.setMonto(procesoRecuperacionBean.getMonto());
//            Element elt = new Element(pass, Namespace.NO_NAMESPACE);
//            listaProcesoRecuperacion = new ArrayList<>();
//        }
//        Element row = new Element("row");
//        row.setChildren(listaProcesoRecuperacion);
//        root.addContent(row);

//        Document xml = null;
//        SAXBuilder builder = new SAXBuilder();
//        try {
//            xml = builder.build(new File(".././resources/xml/envio.xml"));
//            Element roott = xml.getRootElement();
//            List envios = roott.getChildren();
//            Iterator itr = envios.iterator();
//            while (itr.hasNext()) {
//                Element envio = (Element) itr.next();
//                String a = "procesoRecuperacionBean.setUnidadNegocioBean(procesoRecuperacionBean.getUnidadNegocioBean())" + envio.getChildText("T1");
//                String b = "procesoRecuperacionBean.setProcesoBancoBean(procesoRecuperacionBean.getProcesoBancoBean())" + envio.getChildText("T1");
//                String c = "procesoRecuperacionBean.setCuentasPorCobrarBean(procesoRecuperacionBean.getCuentasPorCobrarBean())" + envio.getChildText("T1");
//                String d = "procesoRecuperacionBean.setEstudianteBean(procesoRecuperacionBean.getEstudianteBean())" + envio.getChildText("T1");
//
//                procesoRecuperacionBean.setProcesoBancoBean(procesoRecuperacionBean.getProcesoBancoBean());
//            }
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } 
//        Document xml = null;
//        SAXBuilder builder = new SAXBuilder();
//        try {
//            xml = builder.build(new File(".././resources/xml/envio.xml"));
//            Element roott = xml.getRootElement();
//            List envios = roott.getChildren();
//            Iterator itr = envios.iterator();
//            while (itr.hasNext()) {
//                Element envio = (Element) itr.next();
//                String a = "procesoRecuperacionBean.setUnidadNegocioBean(procesoRecuperacionBean.getUnidadNegocioBean())" + envio.getChildText("T1");
//                String b = "procesoRecuperacionBean.setProcesoBancoBean(procesoRecuperacionBean.getProcesoBancoBean())" + envio.getChildText("T1");
//                String c = "procesoRecuperacionBean.setCuentasPorCobrarBean(procesoRecuperacionBean.getCuentasPorCobrarBean())" + envio.getChildText("T1");
//                String d = "procesoRecuperacionBean.setEstudianteBean(procesoRecuperacionBean.getEstudianteBean())" + envio.getChildText("T1");
//
//                procesoRecuperacionBean.setProcesoBancoBean(procesoRecuperacionBean.getProcesoBancoBean());
//            }
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void handleFileUpload() {
        if (file != null) {
            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void convertjava() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\testing.txt"))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
            }
        } catch (IOException ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void cargar(String fileName, InputStream in) {
//        try {
//            OutputStream out = new FileOutputStream(new File(ruta + fileName));
//            int reader = 0;
//            byte[] bytes = new byte[(int) getFile().getSize()];
//        } catch (Exception e) {
//
//        }
//    }
    public void ProcesoReuperacion() {
        try {
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            listaProRec = procesoRecuperacionService.obtenerProcesoRec();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarProcesoRecuperacion() {
        String pagina = null;
        String ruta = procesoRecuperacion.getFile();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null && procesoRecuperacion.getFile() != null) {
                ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
                procesoRecuperacionService.insertarrecuperacion(procesoRecuperacion.getFile());
                System.out.println("Insercion valida");
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                System.out.println("Insercion fallida");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void insertarRecuperacionFile() {
        FileInputStream miArchivo = null;
        try {
            File file = new File("D:\\BD\\texto.txt");
            miArchivo = new FileInputStream(file);
            byte[] b = new byte[(int) file.length()];
            miArchivo.read(b);
            miArchivo.close();
            String resultado = new String(b);
            System.out.println(" Lo  que lei es: " + resultado);
            System.out.println(resultado);
            listaProRec = new ArrayList<>();
            String[] arreglo = resultado.split("\n");

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DateFormat sdf = new SimpleDateFormat("hh:mm:ss");

            //Datos de Cabecera
            String celda111 = "";//C1
            String celda121 = "";//C2
//            Integer codsuc = Integer.parseInt(celda121);
            String celda131 = "";//C3
//            Integer tipoMoneda = Integer.parseInt(celda131);
            String celda141 = "";//C4
//            Integer cuenta = Integer.parseInt(celda141);
            String celda151 = "";//C5
            String celda161 = "";//C6
            String celda171 = "";//C7
            String celda181 = "";//C8
//            Float total = Float.parseFloat(celda181);
            String celda191 = "";//C9
            String celda122 = "";//C10
            String celda123 = "";//C11
            String celda124 = "";//C12

            //Datos del Detalle
            String celda112 = "";//1
            String celda1220 = "";//2
            String celda132 = "";//3
            String celda142 = "";//4
            String celda152 = "";//5
            String celda162 = "";//6
            String celda172 = "";//7
            String celda182 = "";//8
            String celda192 = "";//9
            String celda103 = "";//10
            String celda113 = "";//11
            String celda1230 = "";//12
            String celda133 = "";//13
            String celda143 = "";//14
            String celda153 = "";//15
            String celda163 = "";//16
            String celda173 = "";//17
            String celda183 = "";//18
            String celda193 = "";//19
            String celda104 = "";//20
            String celda114 = "";//21

            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            Integer a = 0;
            String sfdg = a.toString();

            for (int i = 0; i < arreglo.length; i++) {
                ProcesoRecuperacionBean procesoRecuperacion = new ProcesoRecuperacionBean();
                String celda11 = arreglo[i].substring(0, 2);
                if (celda11.equals("CC")) {
                    System.out.println("-----------------Cabecera---------------------");
                    celda111 = arreglo[i].substring(0, 2);//1   TIPOrEGISTRO
                    celda121 = arreglo[i].substring(2, 5);//2   CODSUCURSAL
                    Integer codsuc = Integer.parseInt(celda121);
                    celda131 = arreglo[i].substring(5, 6);//3   MONEDA
                    Integer tipoMoneda = Integer.parseInt(celda131);
                    celda141 = arreglo[i].substring(6, 13);//4  NUMCUENTA
                    Integer cuenta = Integer.parseInt(celda141);
                    celda151 = arreglo[i].substring(13, 14);//5 TIPOVALIDACION
                    celda161 = arreglo[i].substring(14, 22);//6 FECHA PROCESO
                    celda171 = arreglo[i].substring(22, 31);//7 CANTIDAD TOTAL
                    celda181 = arreglo[i].substring(31, 46);//8 MONTO
                    Float total = Float.parseFloat(celda181);
                    celda191 = arreglo[i].substring(46, 50);//9 CODBCP
                    celda122 = arreglo[i].substring(50, 57);//10 CASILLA
                    celda123 = arreglo[i].substring(57, 62);//11
                    celda124 = arreglo[i].substring(62, 200);//12

                    procesoRecuperacion.getUnidadNegocioBean().setUniNeg("SANLUI");
                    procesoRecuperacion.getProcesoBancoBean().setIdProcesoBanco(null);//a Cambiar
                    procesoRecuperacion.getEstudianteBean().setIdEstudiante(null);//a Cambiar
                    procesoRecuperacion.getConceptoBean().setIdConcepto(null);//a cambiar
                    procesoRecuperacion.setIdMoneda(celda131);//a cambiar
                    procesoRecuperacion.setMonto(null);//a cambiar
                    procesoRecuperacion.setMora(null);//a cambiar
                    procesoRecuperacion.setMonto(total);//a cambiar
                    procesoRecuperacion.setMontoEnv(total);//a cambiar
                    procesoRecuperacion.setMontoRecup(total);//a cambiar
                    procesoRecuperacion.setFechaPago(null);//a cambiar
                    procesoRecuperacion.getCuentasPorCobrarBean().setIdCtasXCobrar(null);//a cambiar
                    procesoRecuperacion.setCuentaAfiliada(cuenta);//a cambiar
                    procesoRecuperacion.setDatoAdicionalDep(null);//a cambiar
                    procesoRecuperacion.setFechaVen(null);//a cambiar
                    procesoRecuperacion.setAgencia(codsuc);//a cambiar
                    procesoRecuperacion.setNumOperacion(i);//a cambiar
                    procesoRecuperacion.setReferencia(celda151);//a cambiar
                    procesoRecuperacion.setTerminal(celda191);//a cambiar
                    procesoRecuperacion.setMedioAtencion(null);//a cambiar
                    procesoRecuperacion.setHoraAtencion(null);//a cambiar
                    procesoRecuperacion.setCreaPor(beanUsuarioSesion.getUsuario());//a cambiar
                    listaProRec.add(procesoRecuperacion);
                    procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
                } else {
                    System.out.println("-----------------Detalle---------------------" + i);
                    celda112 = arreglo[i].substring(0, 2);//1 tipoReg
                    celda1220 = arreglo[i].substring(2, 5);//2 codSuc
                    celda132 = arreglo[i].substring(5, 6);//3 moneda
                    Integer moneda = Integer.parseInt(celda132);
                    celda142 = arreglo[i].substring(6, 13);//4 numcuenta
                    Integer cuenta2 = Integer.parseInt(celda142);
                    celda152 = arreglo[i].substring(13, 27);//5 codAlu
                    celda162 = arreglo[i].substring(27, 57);//6 datoAdi
                    celda172 = arreglo[i].substring(57, 65);//7 fecPago
                    celda182 = arreglo[i].substring(65, 73);//8 fecVen
                    celda192 = arreglo[i].substring(73, 88);//9 importe
                    Float monto = Float.parseFloat(celda192);
                    celda103 = arreglo[i].substring(88, 103);//10 mora
                    Float mora = Float.parseFloat(celda103);
                    celda113 = arreglo[i].substring(103, 118);//11 total
                    Float montoPagado = Float.parseFloat(celda113);
                    celda1230 = arreglo[i].substring(118, 124);//12 codAge
                    Integer agencia = Integer.parseInt(celda1230);
                    celda133 = arreglo[i].substring(124, 130);//13 numOpe
                    Integer numOpe = Integer.parseInt(celda133);
                    celda143 = arreglo[i].substring(130, 152);//14 refe
                    celda153 = arreglo[i].substring(152, 158);//15 teti
                    celda163 = arreglo[i].substring(158, 168);//16 medio
                    celda173 = arreglo[i].substring(168, 174);//17 hora
//                    Date hora = sdf.parse(celda173);
//                    Time hora = new Time(sdf.parse("23:44").getTime());
                    celda183 = arreglo[i].substring(174, 184);//18 numCheque
                    celda193 = arreglo[i].substring(184, 186);//19 codBanco
                    celda104 = arreglo[i].substring(186, 196);//20 cargo
                    celda114 = arreglo[i].substring(196, 200);//21 filler

                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");

                    procesoRecuperacion.getUnidadNegocioBean().setUniNeg("SANLUI");
                    procesoRecuperacion.getProcesoBancoBean().setIdProcesoBanco(1);//a Cambiar
                    procesoRecuperacion.getEstudianteBean().setIdEstudiante(celda152);//a Cambiar codAlu
                    procesoRecuperacion.getConceptoBean().setIdConcepto(null);//a cambiar
                    procesoRecuperacion.setIdMoneda(celda132);//a cambiar moneda
                    procesoRecuperacion.setMonto(monto);//a cambiar monto
                    procesoRecuperacion.setMora(mora);//a cambiar mora
                    procesoRecuperacion.setMonto(montoPagado);//a cambiar montoPagado
                    procesoRecuperacion.setMontoEnv(montoPagado);//a cambiar montoPagado
                    procesoRecuperacion.setMontoRecup(montoPagado);//a cambiar montoPagado
//                    procesoRecuperacion.setFechaPago(celda172);//a cambiar fechaPago
//                    procesoRecuperacion.setFechaPago(formato.parse(celda172));//a cambiar fechaPago
                    procesoRecuperacion.setFechaPago(null);//a cambiar fechaPago
                    procesoRecuperacion.getCuentasPorCobrarBean().setIdCtasXCobrar(null);//a cambiar
                    procesoRecuperacion.setCuentaAfiliada(cuenta2);//a cambiar
                    procesoRecuperacion.setDatoAdicionalDep(celda162);//a cambiar
//                    procesoRecuperacion.setFechaVen(celda182);//a cambiar fechaVen
//                    procesoRecuperacion.setFechaVen(formato.parse(celda182));//a cambiar fechaVen
                    procesoRecuperacion.setFechaVen(null);//a cambiar fechaVen
                    procesoRecuperacion.setAgencia(agencia);//a cambiar
                    procesoRecuperacion.setNumOperacion(numOpe);//a cambiar numOpe
                    procesoRecuperacion.setReferencia(celda143);//a cambiar refe
                    procesoRecuperacion.setTerminal(celda153);//a cambiar
                    procesoRecuperacion.setMedioAtencion(celda163);//a cambiar
                    procesoRecuperacion.setHoraAtencion(null);//a cambiar
                    procesoRecuperacion.setCreaPor(beanUsuarioSesion.getUsuario());//a cambiar
                    listaProRec.add(procesoRecuperacion);
                    procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
                }
//                listaProRec = procesoRecuperacionService.obtenerProcesoRec();
                listaProRec = procesoRecuperacionService.obtenerProcesoRecPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                listaProRec.add(procesoRecuperacion);
//                procesoRecuperacionService.insertarRecuperacion(procesoRecuperacion);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //========================================Proceso_File=====================================
    public void obtenerProcesoFile() {
        try {
            ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
            listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorBanco(Object object) {
        try {
            procesoFileBean = (ProcesoFileBean) object;
            EntidadService entidadService = BeanFactory.getEntidadService();
            entidadService.obtenerEntidadPorId(entidadBean);
            ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
            listaProcesoFileBean = procesoFileService.obtenerPorBanco(entidadBean.getRuc());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarProcesoFile() {
        try {
            procesoFileBean = new ProcesoFileBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectFile(SelectEvent event) {
        try {
            procesoFileBean = (ProcesoFileBean) event.getObject();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void nuevoFile() {
        try {
            procesoFileBean = new ProcesoFileBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);

        }
    }

    public String guardarFile() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
                procesoFileBean.setCreaPor(beanUsuarioSesion.getUsuario());
                procesoFileService.insertarProcesoFile(procesoFileBean);
                listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
                limpiarProcesoFile();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarProcesoFile() {
        String pagina = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            if (pagina == null) {
                ProcesoFileService procesoFileService = BeanFactory.getProcesoFileService();
                procesoFileBean.setModiPor(beanUsuarioSesion.getUsuario());
                procesoFileBean.setModiPor(beanUsuarioSesion.getUsuario());
                procesoFileService.modificarProcesoFile(procesoFileBean);
                listaProcesoFileBean = procesoFileService.obtenerProcesoFile();
                limpiarProcesoFile();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardar() {
        try {
            if (procesoFileBean.getEntidadBean().getRuc() != null) {
                modificarProcesoFile();
            } else {
                guardarFile();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //=============================================================================
    //=============================Poner============================================
    public void ponerProceso(Object object) {
        try {
            ProcesoBancoBean prod = (ProcesoBancoBean) object;
            procesoEnvioBean.setProcesoBancoBean(prod);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerCuentas(Object object) {
        try {
            CuentasPorCobrarBean ctas = (CuentasPorCobrarBean) object;
            procesoEnvioBean.setCuentasPorCobrarBean(ctas);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerConcepto(Object object) {
        try {
            ConceptoBean con = (ConceptoBean) object;
            procesoEnvioBean.setConceptoBean(con);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerEstudiante(Object object) {
        try {
            EstudianteBean est = (EstudianteBean) object;
            procesoEnvioBean.setIdDiscente(est);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void grabar() {
        try {

        } catch (Exception e) {

        }

    }

    //=======================================================================================//
    //======================================Proceso de Envio=================================================//
    public void obtenerEstudiante() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            listaEstudianteBean = estudianteService.obtenerEstudianteMatPorUniNeg(MaristaConstantes.COD_ADMITIDO, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuenta() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();

            //------Ejemplo :)
//            Integer a = 0;
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            a = procesoEnvioService.obtenerMaxIdProcesoBanco(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            a++;
//            ProcesoEnvioBean procesoEnvioBe = new ProcesoEnvioBean();
//            procesoEnvioBe.setIdProcesoEnvio(a);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCtaFiltro = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEstudiantePorId(Object object) {
        try {
            matriculaBean = (MatriculaBean) object;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaService.obtenerMatriculaPorId(matriculaBean);
//            estudianteBean = (EstudianteBean) object;
//            EstudianteService estudianteService = BeanFactory.getEstudianteService();
//            estudianteService.obtenerEstPorId(estudianteBean);
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            CuentasPorCobrarBean cta = (CuentasPorCobrarBean) object;
            listaCtaFiltro = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void matriculados() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatriculaUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void buscarEstudiante() {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());

            int estado = 0;
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase().trim());
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
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
//            else if (estudianteFiltroBean.getPersonaBean().getNombre() == null) {
//                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
//                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            if (estado == 1) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
//                listaEstudianteBean = estudianteService.obtenerFiltroEstudiante(estudianteFiltroBean);
                listaMatriculaBean = matriculaService.obtenerFiltroMatriculados(matriculaFiltroBean);
                if (listaEstudianteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PrimeFaces Rocks."));
            }
            if (listaEstudianteBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerEnvioPorFiltro() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            String fecha = formato.format(procesoEnvioFiltroBean.getCuentasPorCobrarBean().getFechaVenc());
            if (procesoEnvioFiltroBean.getIdDiscente().getPersonaBean().getIdPersona() != null && !procesoEnvioFiltroBean.getIdDiscente().getPersonaBean().getIdPersona().equals("")) {
                procesoEnvioFiltroBean.getIdDiscente().getPersonaBean().setIdPersona(procesoEnvioFiltroBean.getIdDiscente().getPersonaBean().getIdPersona());
            }
//            if (procesoEnvioFiltroBean.getIdDiscente().getIdEstudiante() != null && !procesoEnvioFiltroBean.getIdDiscente().getIdEstudiante().equals("")) {
//                procesoEnvioFiltroBean.getIdDiscente().setIdEstudiante(procesoEnvioFiltroBean.getIdDiscente().getIdEstudiante());
//            }
            if (procesoEnvioFiltroBean.getConceptoBean().getIdConcepto() != null && !procesoEnvioFiltroBean.getConceptoBean().getIdConcepto().equals("")) {
                procesoEnvioFiltroBean.getConceptoBean().setIdConcepto(procesoEnvioFiltroBean.getConceptoBean().getIdConcepto());

            }
            if (procesoEnvioFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null && !procesoEnvioFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto().equals("")) {
                procesoEnvioFiltroBean.getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(procesoEnvioFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
            }
            if (procesoEnvioFiltroBean.getAnio() != null && !procesoEnvioFiltroBean.getAnio().equals("")) {
                procesoEnvioFiltroBean.setAnio(procesoEnvioFiltroBean.getAnio());
            }
            if (procesoEnvioFiltroBean.getMes() != null && !procesoEnvioFiltroBean.getMes().equals("")) {
                procesoEnvioFiltroBean.setMes(procesoEnvioFiltroBean.getMes());
            }
            if (procesoEnvioFiltroBean.getDia() != null && !procesoEnvioFiltroBean.getDia().equals("")) {
                procesoEnvioFiltroBean.setDia(procesoEnvioFiltroBean.getDia());
            }
//            if (procesoEnvioFiltroBean.getFechaComodinIn() != null) {
//                Timestamp t = new Timestamp(procesoEnvioFiltroBean.getFechaComodinIn().getTime());
//                t.setHours(0);
//                t.setMinutes(0);
//                t.setSeconds(0);
//                procesoEnvioFiltroBean.setFechaComodinIn(t);
//
//            }
//            if (procesoEnvioFiltroBean.getFechaComodin() != null) {
//                Timestamp u = new Timestamp(procesoEnvioFiltroBean.getFechaComodin().getTime());
//                u.setHours(23);
//                u.setMinutes(59);
//                u.setSeconds(59);
//                procesoEnvioFiltroBean.setFechaComodin(u);
//            }
            if (procesoEnvioFiltroBean.getFlgEnvio() != null) {
                procesoEnvioFiltroBean.setFlgEnvio(procesoEnvioFiltroBean.getFlgEnvio());
            } else if (listaProcesoEnvioBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Encontraron Datos"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "NO HAY DATOS"));
            }

            listaProcesoEnvioBean = procesoEnvioService.obtenerEnvioPorFiltro(procesoEnvioFiltroBean);
            Integer sum = 0;
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getMonto() != null && !listaProcesoEnvioBean.get(i).getMonto().equals(0)) {
                    totalEnvios = listaProcesoEnvioBean.get(i).getMonto().intValue() + totalEnvios;
                } else {
                    totalEnvios = 0;
                }
            }
            envio = listaProcesoEnvioBean.size();
            mostrarTabla = false;
            mostrarPanelBus = true;
            mostrarAcc = false;
            mostrarPanel = false;
            mostrarAc = 1;
            mostrarLayout = true;
            mostrarMatricula = true;
            System.out.println(envio);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEnvioCuenta() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            totalEnvios = 0;
            if (cuentasPorCobrarFiltroBean.getUnidadNegocioBean().getUniNeg() == null || !cuentasPorCobrarFiltroBean.getUnidadNegocioBean().getUniNeg().equals("")) {
                cuentasPorCobrarFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            if (cuentasPorCobrarFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !cuentasPorCobrarFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                cuentasPorCobrarFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(cuentasPorCobrarFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
            }
            if (cuentasPorCobrarFiltroBean.getConceptoBean().getIdConcepto() != null && !cuentasPorCobrarFiltroBean.getConceptoBean().getIdConcepto().equals("")) {
                cuentasPorCobrarFiltroBean.getConceptoBean().setIdConcepto(cuentasPorCobrarFiltroBean.getConceptoBean().getIdConcepto());
            }
            if (cuentasPorCobrarFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null && !cuentasPorCobrarFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto().equals("")) {
                cuentasPorCobrarFiltroBean.getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(cuentasPorCobrarFiltroBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
            }
            if (cuentasPorCobrarFiltroBean.getAnio() != null && !cuentasPorCobrarFiltroBean.getAnio().equals("")) {
                cuentasPorCobrarFiltroBean.setAnio(cuentasPorCobrarFiltroBean.getAnio());
            }
            if (cuentasPorCobrarFiltroBean.getDia() != null && !cuentasPorCobrarFiltroBean.getDia().equals("")) {
                cuentasPorCobrarFiltroBean.setDia(cuentasPorCobrarFiltroBean.getDia());
            }
            if (cuentasPorCobrarFiltroBean.getMes() != null && !cuentasPorCobrarFiltroBean.getMes().equals(0)) {
                setMesEnvio(cuentasPorCobrarFiltroBean.getMes());
                cuentasPorCobrarFiltroBean.setMes(cuentasPorCobrarFiltroBean.getMes());
            }
            if (cuentasPorCobrarFiltroBean.getIdEstadoCuenta() != null && !cuentasPorCobrarFiltroBean.getIdEstadoCuenta().equals(0)) {
                cuentasPorCobrarFiltroBean.setIdEstadoCuenta(cuentasPorCobrarFiltroBean.getIdEstadoCuenta());
            }
            listaCtaFiltro = procesoEnvioService.obtenerEnvioCuenta(cuentasPorCobrarFiltroBean);
            if (listaCtaFiltro.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
            Integer sum = 0;
            for (int i = 0; i < listaCtaFiltro.size(); i++) {
                if (listaCtaFiltro.get(i).getMonto() != null && listaCtaFiltro.get(i).getFlgEnvio().equals(true) && listaCtaFiltro.get(i).getFlgEnvio() != null) {
                    totalEnvios = listaCtaFiltro.get(i).getMonto().intValue() + totalEnvios;
                }
            }

            envio = listaCtaFiltro.size();
//            obtenerTotalImporte();
            mostrarTabla = false;
            mostrarPanelBus = true;
            mostrarAcc = false;
            mostrarPanel = false;
            mostrarAc = 1;
            mostrarLayout = true;
            mostrarMatricula = true;
            mostrarAlerta = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerRegEnviados() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            getListaCtaFiltro();
            listaCtaFiltro = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            envio = listaCtaFiltro.size();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTotalImporte() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            getListaCtaFiltro();
            listaCtaFiltro = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaCtaFiltro.size(); i++) {
//                totalEnvios = listaProcesoEnvioBean.get(i).getMonto().intValue() + totalEnvios;
                totalEnvios = listaCtaFiltro.get(i).getMonto().intValue() + totalEnvios;
            }
            System.out.println(totalEnvios);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTotalError() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            getListaCtaFiltro();
            listaCtaFiltro = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaCtaFiltro.size(); i++) {
//                totalErroneos = listaProcesoBancoFiltroBean.get(i).getRegError().intValue() + totalErroneos;
                totalErroneos = listaCtaFiltro.get(i).getProcesoEnvioBean().getProcesoBancoBean().getRegError() + totalErroneos;
            }
            System.out.println(totalErroneos);
        } catch (Exception e) {

        }
    }

//    public void obtenerMeses() {
//        try {
//            listaMeses = new ArrayList<>();
//            listaMeses.add("Enero");
//            listaMeses.add("Febrero");
//            listaMeses.add("Marzo");
//            listaMeses.add("Abril");
//            listaMeses.add("Mayo");
//            listaMeses.add("Junio");
//            listaMeses.add("Julio");
//            listaMeses.add("Agosto");
//            listaMeses.add("Setiembre");
//            listaMeses.add("Octubre");
//            listaMeses.add("Noviembre");
//            listaMeses.add("Diciembre");
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }
    public void cargarMeses() {
        try {
            String dato = procesoBanco.getIdMes();
            for (int i = 0; i <= 12; i++) {
//                if (i == 1) {
//                    dato = "1";
//                }
//                if (i == 2) {
//                    dato = 2;
//                }
//                if (i == 3) {
//                    dato = 3;
//                }
//                if (i == 4) {
//                    dato = 4;
//                }
//                if (i == 5) {
//                    dato = 5;
//                }
//                if (i == 6) {
//                    dato = 6;
//                }
//                if (i == 7) {
//                    dato = 7;
//                }
//                if (i == 8) {
//                    dato = 8;
//                }
//                if (i == 9) {
//                    dato = 9;
//                }
//                if (i == 10) {
//                    dato = 10;
//                }
//                if (i == 11) {
//                    dato = 11;
//                }
//                if (i == 12) {
//                    dato = 12;
//                }
                System.out.println(listaMeses.get(i));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMesesForSup() {
        listaMeses = new LinkedHashMap<>();
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), "1");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), "2");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), "3");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), "4");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), "5");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), "6");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), "7");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), "8");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), "9");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), "10");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), "11");
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), "12");
        listaMeses = Collections.unmodifiableMap(listaMeses);
    }

    public void obtenerDeuda() {
        try {
            Integer var = 0;
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getDeuda().intValue() == 0) {
                    listaProcesoEnvioBean.get(i).setFlgDeuda("Cumplido");
                } else {
                    listaProcesoEnvioBean.get(i).setFlgDeuda("Deudor");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDeuda() {
        try {
            listaDeuda = new LinkedHashMap<>();
            listaDeuda.put(MensajesBackEnd.getValueOfKey("Cumplido", null), "Cumplido");
            listaDeuda.put(MensajesBackEnd.getValueOfKey("Deudor", null), "Deudor");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFecha() {
        try {
            Calendar fecha = new GregorianCalendar();
            datoAnio = fecha.get(Calendar.YEAR);
            datoMes = fecha.get(Calendar.MONTH);
            datoDia = fecha.get(Calendar.DAY_OF_MONTH);
            datoHora = fecha.get(Calendar.HOUR_OF_DAY);
            System.out.println("Fecha Actual: "
                    + datoAnio + "/" + datoMes + "/" + datoDia);
            System.out.printf("Hora Actual: %02d:%02d %n",
                    datoHora, datoMinuto);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void retirarLista() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaProcesoEnvioBean = new ArrayList<>();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                if (listaProcesoEnvioBean.get(i).getFlgEnvio().equals(true)) {
                    listaProcesoEnvioBean.get(i).setFlgEnvio(false);
                    modificarProcesoEnvio();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerUltimoEnvio() {
        try {
            Integer codBanco;
            Integer codBancoEnvio;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            codBancoEnvio = procesoEnvioService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codBanco);
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarBusqueda() {
        try {
            //        procesoEnvioFiltroBean = new ProcesoEnvioBean();
            listaCtaFiltro = new ArrayList<>();
            procesoEnvio = new ProcesoEnvioBean();
            cuentasPorCobrarFiltroBean = new CuentasPorCobrarBean();
            mostrarMatricula = false;
            envio = 0;
            totalEnvios = 0;
            totalErroneos = 0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTipoConcepto() {
        try {
            for (int i = 0; i < listaconceptoBean.size(); i++) {
                if (listaconceptoBean.get(i).getTipoConceptoBean().getIdTipoConcepto() != null
                        && listaconceptoBean.get(i).getTipoConceptoBean().getIdTipoConcepto().equals(listaTipoConceptoBean.get(i).getIdTipoConcepto())) {
                    listaconceptoBean.get(i).setTipoConceptoBean(tipoConceptoBean);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPorTipo() {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            getListaconceptoBean();
            listaconceptoBean = conceptoService.obtenerPorTipo(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean());
            getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
        } catch (Exception err) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Por favor Revisar"));
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void descargaArchivoFiltro(Object object) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        Integer codProBanco = 0;
        Integer codBanco = 0;
        try {
            obtenerProcesoEnvioPorId2(procesoBancoBean);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            File archivo = new File("Envio.txt");
//            fichero = new FileWriter("\\\\Tes_bd\\pro\\Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            int n = 0;
            int m = 0;
            Integer respuestaDato = 0;
            Integer respuestaDato1 = 0;
            Integer n1 = 0;
            Integer n2 = 0;
            Integer n3 = 0;
            Integer n4 = 0;
            Integer n5 = 0;
            Integer lista = listaProcesoEnvioFiltroBean.size();

            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
//                totalEnvios = listaProcesoEnvioFiltroBean.get(i).getMonto().intValue() + totalEnvios;
//                totalErroneos = listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getRegError();
            }
            System.out.println("===========================================");
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print("  ");
                    } else {
                        String space01;
                        String reg01 = "CC";
                        space01 = String.format("%-2s", reg01);
                        pw.print(reg01);
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print("   ");
                    }
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                    pw.print("0");
                    if (listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print("       ");
                    } else {
//                        pw.print(listaProcesoEnvioBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta());
                        String space02;
                        String reg02 = listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString();
                        space02 = String.format("%-7s", reg02);
                        pw.print(space02);
                    }
                    pw.print("c");//Tipo Validacion
//                    pw.print("CCEGNE" + listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getNombreUniNeg() == null) {
                        pw.print("                                        ");
                    } else {
                        String space03;
                        String reg03 = listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getNombreUniNeg();
                        space03 = String.format("%-34s", reg03);
                        pw.print("CCEGNE" + space03);
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getCreaFecha() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print("        ");
                    } else {
                        String space04;
                        String dat03 = formato.format(listaProcesoEnvioFiltroBean.get(i).getCreaFecha());
                        String fec01 = dat03.substring(0, 2);
                        String fec02 = dat03.substring(3, 5);
                        String fec03 = dat03.substring(6, 10);
                        String resul02 = fec03 + fec02 + fec01;
                        space04 = String.format("%-8s", resul02);
                        pw.print(space04);
                    }
                    if (!listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        String listaDat01 = "0";
                        String listaDat11 = "";
                        for (int j = 0; j < 9; j++) {
                            listaDat11 = listaDat11 + listaDat01;
                        }
                        pw.print(String.format("%-9s", listaDat11));
                    } else {
                        String space05 = String.format("%-9s", lista);
                        Integer lista1 = space05.length() - lista.toString().length();
                        String listaDat = "0";
                        String listaDat1 = "";
                        for (int j = 0; j < lista1; j++) {
                            listaDat1 = listaDat + listaDat1;
                        }
                        String data = listaDat1 + space05.replaceAll(" ", "");
                        pw.print(String.format("%-9s", data));//Registros
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getMonto() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print("               ");
                    } else {
                        Integer res = 0;
                        for (ProcesoEnvioBean env : listaProcesoEnvioFiltroBean) {
                            Integer totales = 0;
                            if (env.getMonto() != null && env.getFlgEnvio().equals(true)) {
                                Integer sumatoria = 0;
                                res = env.getMonto().intValue() + res;
                            }
                        }
                        String valor = res + "00";
                        System.out.println(valor);
                        String space03;
                        String reg03 = valor;
//                        String rg031 = reg03.replace(".", ":");
//                        String cadena[] = rg031.split(":");
//                        String rg032 = cadena[0];
//                        String rg033 = cadena[1];
//                        String reg0334 = rg032 + rg033;
                        space03 = String.format("%-15s", reg03);
                        Integer dat1 = space03.length() - reg03.length();
                        String dat2 = "0";
                        String dat3 = "";
                        for (int j = 0; j < dat1; j++) {
                            dat3 = dat2 + dat3;
                        }
                        String dat4 = dat3 + space03.replaceAll(" ", "");
                        pw.print(dat4);
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\r");
                } else {
                    respuestaDato1 = respuestaDato1 + 1;
                }
//                }
            }
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                System.out.println(">>>>>" + listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getIdProcesoBanco());
                System.out.println(">>>>>" + listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getNumCuenta());
                if (listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getNumCuenta() != null) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
//                if (listaProcesoEnvioFiltroBean.isEmpty() || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
//                    fichero.close();
//                    pw.print("Error de descarga no se encontro datos");
//                } else {
                    if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(true)) {
                        m = m + 1;
                        if (!listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("  ");
                            n1 = n1 + 0;
                        } else {
                            String space1;
                            String reg = "DD";
                            space1 = String.format("%-2s", reg);
                            pw.print(space1);
                        }
                        if (!listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("  ");
                            n1 = n1 + 0;
                        } else {
                            String space2;
                            String reg2 = listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg().toString();
                            space2 = String.format("%-3s", reg2);
                            pw.print(space2);
                        }
                        if (!listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("  ");
                            n1 = n1 + 0;
                        } else {
                            String space3;
                            String reg3 = "0";
                            space3 = String.format("%-1s", reg3);
                            pw.print(space3);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("1428600");
                            n1 = n1 + 0;
                        } else {
                            String space4;
                            String reg4 = listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString();
                            space4 = String.format("%-7s", reg4);
                            pw.print(space4);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("              ");
                            n1 = n1 + 0;
                        } else {
                            String space5;
                            String reg5 = listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().trim();
                            space5 = String.format("%-14s", reg5);
                            pw.print("0000000" + space5.trim());
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("                                        ");
                            n1 = n1 + 0;
                        } else {
                            String space6;
                            String reg6 = listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().replaceAll(" ", "").replace(",", "");
                            space6 = String.format("%-40s", reg6);
                            pw.print(space6);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("                              ");
                            n1 = n1 + 0;
                        } else {
                            String space7;
                            String reg7 = listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", "").replace(".", "").replace("-", "");
                            space7 = String.format("%-30s", reg7);
                            pw.print(space7);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getCreaFecha() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                            pw.print("        ");
                            pw.print("REVISAR!");
                        } else {
                            String dato2 = formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getCreaFecha());//FechaCupon - fechaPago
                            String fec01 = dato2.substring(0, 2);
                            String fec02 = dato2.substring(3, 5);
                            String fec03 = dato2.substring(6, 10);
                            String resul1 = fec03 + fec02 + fec01;
                            pw.print(resul1);
                            //formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaPago())
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                        pw.print("        ");
                            pw.print("REVISAR!");
                        } else {
                            String dato1 = formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc());
                            String fec1 = dato1.substring(0, 2);
                            String fec2 = dato1.substring(3, 5);
                            String fec3 = dato1.substring(6, 10);
                            String resul2 = fec3 + fec2 + fec1;
                            pw.print(resul2);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getMonto() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                        pw.print("               ");
                            String mdat01 = "0";
                            String mdat02 = "";
                            Integer mTope0 = 15;
                            String mora = "";
                            listaProcesoEnvioFiltroBean.get(i).setMonto(new BigDecimal("0"));
                            BigDecimal a1 = listaProcesoEnvioFiltroBean.get(i).getMonto();
                            if (listaProcesoEnvioFiltroBean.get(i) != null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(true)) {
                                if (a1.toString().equals("0")) {
                                    for (int j = 0; j < mTope0; j++) {
                                        mdat02 = mdat02 + mdat01;
                                    }
                                }
                            } else {
                                if (listaProcesoEnvioFiltroBean.get(i) == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                                    pw.print("               ");
                                    break;
                                }
                            }
                            System.out.println(mdat02);
                            pw.print(mdat02);
                            n1 = n1 + 0;
                        } else {
                            int sub = listaProcesoEnvioFiltroBean.get(i).getMonto().intValue();
//                        monto = monto + sub;
                            String space8;
                            String reg8 = listaProcesoEnvioFiltroBean.get(i).getMonto().toString();
                            String rg81 = reg8.replace(".", ":");
                            String cadena[] = rg81.split(":");
                            String rg82 = cadena[0];
                            String rg83 = cadena[1];
                            String reg84 = rg82 + rg83;
                            System.out.println(reg84);
                            space8 = String.format("%-15s", reg84);
                            Integer resTope1 = space8.length() - reg84.length();
                            String dat01 = "0";
                            String dat02 = "";
                            if (resTope1 > 0) {
                                for (Integer j = 0; j <= resTope1; j++) {
                                    dat02 = dat02 + dat01;
                                }
                            } else {
                                dat02 = "000000000000000";
                            }
                            System.out.println(dat02);
                            String parcial2 = dat02 + space8.replaceAll(" ", "");
                            pw.print(parcial2);
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getMora() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                        pw.print("               ");
                            String mdat1 = "0";
                            String mdat2 = "";
                            Integer mTope = 15;
                            String mora = "";
                            listaProcesoEnvioFiltroBean.get(i).setMora(new BigDecimal("0"));
                            BigDecimal a = listaProcesoEnvioFiltroBean.get(i).getMora();
                            if (listaProcesoEnvioFiltroBean.get(i) != null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(true)) {
                                if (a.toString().equals("0")) {
                                    for (int j = 0; j < mTope; j++) {
                                        mdat2 = mdat2 + mdat1;
                                    }
                                }
                            } else {
                                if (listaProcesoEnvioFiltroBean.get(i) == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                                    pw.print("               ");
                                    break;
                                }
                            }
                            System.out.println(mdat2);
                            pw.print(mdat2);
                            n1 = n1 + 0;
                        } else {
                            String space9;
                            String reg9 = listaProcesoEnvioFiltroBean.get(i).getMora().toString();
                            String reg91 = listaProcesoEnvioFiltroBean.get(i).getMora().toString().replace(".", ":");
                            String cadena2[] = reg91.split(":");
                            String reg92 = cadena2[0];
                            String reg93 = cadena2[1];
                            String reg94 = reg92 + reg93;
                            space9 = String.format("%-15s", reg94);
                            Integer resTope = space9.length() - reg94.length();
                            System.out.println(">>>>>>>" + resTope);
                            String dat1 = "0";
                            String dat2 = "";
                            if (resTope > 0) {
                                for (Integer j = 0; j <= resTope; j++) {
                                    dat2 = dat2 + dat1;
                                }
                            } else {
                                if (resTope.equals(15)) {
                                    dat2 = "000000000000000";
                                }
                            }
                            System.out.println(dat2);
                            String parcial = space9.replace(" ", "") + dat2;
                            System.out.println(parcial);
                            pw.print(parcial);
                            n1 = n1 + 0;
                        }
                        if (!listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("         ");
                            n1 = n1 + 0;
                        } else {
                            pw.print("00000000");
                        }
                        if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
                            pw.print("  ");
                            n1 = n1 + 0;
                        } else {
                            String space10;
                            String reg10 = listaProcesoEnvioFiltroBean.get(i).getTipoRegistro();
                            space10 = String.format("%-1s", reg10);
                            pw.print(space10);
                        }
                        pw.println("\r");
                        System.out.println(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                        System.out.println("Exporte Exitoso");

                        ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                        listaProcesoEnvioFiltroBean.get(i).setFlgEnvio(false);
                        procesoEnvioService.modificarStatus(listaProcesoEnvioFiltroBean.get(i));
                        System.out.println("Cambio Exitoso");
                    }
                } else {
                    respuestaDato = respuestaDato + 1;
                }
//                }
//                if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) || !listaProcesoEnvioFiltroBean.get(i).getFlgEnvio()) {
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro().equals(""));
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg().toString().equals(""));
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString().equals(""));
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().equals(""));
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().equals(""));
//                    pw.print(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().equals(""));
////                    pw.print(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().equals("")));
////                    pw.println(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc().toString().equals("")));
////                    pw.print(listaProcesoEnvioFiltroBean.get(i).getMonto().toString().equals(""));
////                    pw.print(listaProcesoEnvioFiltroBean.get(i).getMora().toString().equals(""));
//                    pw.println("\n");
//                }
            }
            System.out.println("========>" + respuestaDato);
            System.out.println("========>" + respuestaDato1);
            int s = n + m;
            System.out.println("========>" + n1);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se encontraron" + n1 + "errores"));
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "text1");
                    //     \\\\PRO_BD\\c\\Temporal\\Envio.txt
                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
            activarBoton = false;
            mostrarBoton = false;
//            mostrarBoton2 = true;
//            retirarLista();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void DownloadFile(Object object) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            Integer n = 0;

            obtenerProcesoEnvioPorId2(procesoBancoBean);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            Integer lista = listaProcesoEnvioFiltroBean.size();

            //Obteniendo Archivo
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));
            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getEntidadBean().getRuc(), 2, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getEntidadBean().getRuc(), 2, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta
            Integer h = -1;
            Integer g = -1;

            Object fila = null;
            List<String[][]> listaArreglo = new ArrayList<>();
            List<Object> listaObject = new ArrayList<>();//Lista de Cabecera
            List<Object> listaDetalle = new ArrayList<>();
            List<List<Object>> lis = new ArrayList<>();
            List<List<Object>> list = new ArrayList<>();
            Integer cant = listaProcesoEnvioFiltroBean.size();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();

            //Cabecera de Archivo
            for (int i = 0; i < 1; i++) {
                Integer suma = procesoEnvioService.obtenerSumaImporte(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getIdProcesoBanco());
                listaObject.add("CC");
                listaObject.add(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                listaObject.add(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCodMoneda());
                listaObject.add(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                listaObject.add("c");
                listaObject.add(listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getNombreUniNeg());
                if (listaProcesoEnvioFiltroBean.get(i).getCreaFecha() != null) {
                    String space04;
                    String dat03 = formato.format(listaProcesoEnvioFiltroBean.get(i).getCreaFecha());
                    String fec01 = dat03.substring(0, 2);
                    String fec02 = dat03.substring(3, 5);
                    String fec03 = dat03.substring(6, 10);
                    String resul02 = fec03 + fec02 + fec01;
                    listaObject.add(resul02);
                }
                listaObject.add(cant);
                listaObject.add(suma);
                listaObject.add(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro());
                listaObject.add("");
                lis.add(listaObject);
            }

            String[][] rpta = new String[listaProcesoFilesBean.size()][lis.size()];

            //Listando Cabecera
            if (!listaProcesoFilesBean.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    fila = lis.get(i);
                    for (int j = 0; j < listaProcesoFilesBean.size(); j++) {
                        rpta[j][i] = mapearEnvio(fila);
                    }
                }
            }

            //Detalle de Archivos
            for (int j = 0; j < listaProcesoEnvioFiltroBean.size(); j++) {
                listaDetalle.add("DD");
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getProcesoBancoBean().getCodMoneda());
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getIdEstudiante());
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getIdDiscente().getPersonaBean().getNombreCompleto());
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getConceptoBean().getNombre());
                listaDetalle.add(formato.format(listaProcesoEnvioFiltroBean.get(j).getCuentasPorCobrarBean().getCreaFecha()));
                listaDetalle.add(formato.format(listaProcesoEnvioFiltroBean.get(j).getCuentasPorCobrarBean().getFechaVenc()));
                Integer monto = 0;
                Integer mora = 0;
                if (listaProcesoEnvioFiltroBean.get(j).getMonto() != null) {
                    monto = listaProcesoEnvioFiltroBean.get(j).getMonto().intValue();
                } else {
                    monto = 0;
                }
                if (listaProcesoEnvioFiltroBean.get(j).getMora() != null) {
                    mora = listaProcesoEnvioFiltroBean.get(j).getMora().intValue();
                } else {
                    mora = 0;
                }
                Integer suma = monto + mora;
                listaDetalle.add(suma);
                listaDetalle.add(mora);
                listaDetalle.add(monto);
                listaDetalle.add(listaProcesoEnvioFiltroBean.get(j).getTipoRegistro());
                listaDetalle.add("");
                list.add(listaDetalle);
                listaDetalle = new ArrayList<>();
            }

            String[][] rptas = new String[listaProcesoFilesBeanDeta.size()][list.size()];

            //Listando Detalles
            if (!listaProcesoFilesBeanDeta.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    fila = list.get(i);
                    for (int j = 0; j < listaProcesoFilesBeanDeta.size(); j++) {
                        rptas[j][i] = mapearEnvio(fila);
                    }
                }
            }

            if (!listaProcesoFilesBean.isEmpty()) {
                for (int i = 0; i < lis.size(); i++) {
                    for (int j = 0; j <= listaProcesoFilesBean.size(); j++) {
                        if (j == listaProcesoFilesBean.size()) {
                            System.out.println("iguakes");
                            pw.println("");
                        } else {
                            String lon = "%-".concat(listaProcesoFilesBean.get(j).getLongitud().toString().concat("s"));
                            System.out.println(">>>>" + String.format(lon, lis.get(i).get(j)));
                            pw.print(String.format(lon, lis.get(i).get(j)).replace("-", ""));
                        }
                    }
                }
            }

            if (!listaProcesoFilesBeanDeta.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j <= listaProcesoFilesBeanDeta.size(); j++) {
                        if (j == listaProcesoFilesBeanDeta.size()) {
                            System.out.println("iguakes");
                            pw.println("\r");
                        } else {
                            String lon = "%-".concat(listaProcesoFilesBeanDeta.get(j).getLongitud().toString().concat("s"));
                            System.out.println(">>>>" + String.format(lon, list.get(i).get(j)) + listaProcesoFilesBeanDeta.get(j).getNombre() + listaProcesoFilesBeanDeta.get(j).getLongitud());
                            pw.print(String.format(lon, list.get(i).get(j)).replace("-", ""));
                        }
                    }
                }
            }

            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "text1");
            System.out.println("Descarga en Proceso");

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public String mapearEnvio(Object cadena) {
        Object fila = "";
        try {
            fila = cadena;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return fila.toString();
    }

    public void saltarPosiciones(String elemento) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoFileCab = new CodigoBean();
            codigoTipoFileCab = codigoService.obtenerPorCodigo(new CodigoBean(20001, "Cabecera", new TipoCodigoBean(MaristaConstantes.file_Cabecera)));
            CodigoBean codigoTipoFileDet = new CodigoBean();
            codigoTipoFileDet = codigoService.obtenerPorCodigo(new CodigoBean(20002, "Detalle", new TipoCodigoBean(MaristaConstantes.file_Detalle)));

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            List<ProcesoFilesBean> listaProcesoFilesBeanDeta = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getEntidadBean().getRuc(), 2, codigoTipoFileCab.getIdCodigo());//Archivo Vuelta
            listaProcesoFilesBeanDeta = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getEntidadBean().getRuc(), 2, codigoTipoFileDet.getIdCodigo());//Archivo Vuelta
            pos++;
            System.out.println("pos: " + pos);
            for (int i = 0; i < listaProcesoFilesBean.size(); i++) {
                if (pos.equals(i)) {
                    if (elemento == null) {
                        elemento = "";
                        String var = "%-".concat(listaProcesoFilesBean.get(pos).getLongitud().toString()).concat("s");
                        System.out.println(">>>" + var);
                        System.out.println(">>>" + elemento);
                        pw.print(elemento);
                    } else {
                        String var = "%-".concat(listaProcesoFilesBean.get(pos).getLongitud().toString()).concat("s");
                        System.out.println(">>>" + var);
                        System.out.println(">>>" + elemento);
                        pw.print(elemento);
                    }
                    break;
                }
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "Envio");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void descargaArchivoReenvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        Integer codProBanco = 0;
        Integer codBanco = 0;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaProcesoEnvioFiltroBean = new ArrayList<>();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            codProBanco = procesoEnvioService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            codBanco = procesoBancoService.obtenerMaxIdProcesoBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichero = new FileWriter("\\\\PRO_BD\\c\\Temporal\\Envio.txt");
            pw = new PrintWriter(fichero);
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorId(codBanco);
            listaProcesoEnvioFiltroBean = procesoEnvioService.obtenerPorProcesoBanco(codBanco, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean = new ProcesoEnvioBean();
            int n = 0;
            int m = 0;
            int monto = procesoEnvioBean.getTotal();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                totalEnvios = listaProcesoEnvioFiltroBean.get(i).getMonto().intValue() + totalEnvios;
                totalErroneos = listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getRegError();
            }
            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                n = n + 1;
                if (i == 1) {
                    if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("  ");
                    } else {
                        pw.print("CC");
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("   ");
                    }
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                    pw.print("0");//Moneda

                    if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }
                    pw.print("C");//Tipo Validacion
                    pw.print("CCEGNE" + listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getNombreUniNeg().trim());
                    if (listaProcesoEnvioFiltroBean.get(i).getFechaEmision() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioFiltroBean.get(i).getCreaFecha()));
                    }
                    pw.print(i);//Registros
                    if (listaProcesoEnvioFiltroBean.get(i).getMonto() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("               ");
                    } else {
                        pw.print(totalEnvios);
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print(" ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro());
                    }
                    pw.println("                                                                              " + "\n");
                }
                if (listaProcesoEnvioFiltroBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso una Cabecera:" + n));

            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
//                ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
                if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                    m = m + 1;
                    pw.print("DD");
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg());
                    pw.print("0");//Moneda 
                    if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getCuentaD().getCuenta() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("       ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta());
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("              ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().replaceAll(" ", ""));
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("                                        ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().replaceAll(" ", ""));
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("                              ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().replaceAll(" ", ""));
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaPago() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("        ");
                    } else {
                        pw.print(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaPago()));
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("        ");
                    } else {
                        pw.println(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc()));
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getMonto() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("               ");
                    } else {
                        int sub = listaProcesoEnvioFiltroBean.get(i).getMonto().intValue();
                        monto = monto + sub;
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getMonto());
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getMora() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("               ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getMora());
                    }
                    if (listaProcesoEnvioFiltroBean.get(i).getTipoRegistro() == null && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                        pw.print("  ");
                    } else {
                        pw.print(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro());
                    }
                    pw.println("\n");
                    System.out.println(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getIdPersona());
                    System.out.println("Exporte Exitoso");
                    System.out.println(monto);
                }

                if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg()) && listaProcesoEnvioFiltroBean.get(i).getFlgEnvio().equals(false)) {
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getTipoRegistro().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getCodUniNeg().toString().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getProcesoBancoBean().getCuentaBancoBean().getNumCuenta().toString().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getIdPersona().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getIdDiscente().getPersonaBean().getNombreCompleto().trim().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getConceptoBean().getNombre().equals(""));
                    pw.print(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaPago().toString().equals("")));
                    pw.println(formato.format(listaProcesoEnvioFiltroBean.get(i).getCuentasPorCobrarBean().getFechaVenc().toString().equals("")));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getMonto().toString().equals(""));
                    pw.print(listaProcesoEnvioFiltroBean.get(i).getMora().toString().equals(""));
                    pw.println("\n");
                }
                if (listaProcesoEnvioFiltroBean.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "No se Pudo realizar la operaci贸n"));
                }
            }
            int s = n + m;
            System.out.println(procesoEnvioBean.getTotal());
            System.out.println(monto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Proceso total de Detalles:" + m));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Se Procesaron:" + s));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Monto Total a Enviar:" + monto));
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);

            for (int i = 0; i < listaProcesoEnvioFiltroBean.size(); i++) {
                if (listaProcesoEnvioFiltroBean.get(i).getUnidadNegocioBean().getUniNeg().equals(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg())) {
                    content = new DefaultStreamedContent(new FileInputStream(
                            new File("\\\\PRO_BD\\c\\Temporal\\Envio.txt")), "txt", "text1");

                    System.out.println("Descarga en Proceso");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Descarga Interrumpida"));
                }
            }
            activarBoton = false;
            mostrarBoton = false;
//            mostrarBoton2 = true;
//            retirarLista();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void cambiarValStatusTodos() {
        try {
            if (this.flgEnvio == false) {
                for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
                    envio.setFlgEnvio(false);
                }
            } else {
                for (ProcesoEnvioBean envio : listaProcesoEnvioBean) {
                    envio.setFlgEnvio(true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroBanco() {
        try {
            procesoBancoFiltroBean = new ProcesoBancoBean();
            listaProcesoBancoFiltroBean = new ArrayList<>();
            listaProRec = new ArrayList<>();
            listaProcesoEnvioFiltroBean = new ArrayList<>();
            procesoBancoBean = new ProcesoBancoBean();
            indexAccNuevo = 1;
            listaProEnvios = new ArrayList<>();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoFiltroBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoFiltroBean().setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostraBoton2() {
        try {
            mostrarBotonS = true;
//            procesoBanco = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBanco().setFecha(fechaActual.getTime());
            setFechaRecup(fechaActual.getTime());
            System.out.println(">>>" + procesoBanco.getFecha());
            setDisableBotonGuardar(false);
            setProView(true);
            setProView2(false);
            limpiarProcesoBancoReg();
            System.out.println(">>>1" + proView);
            System.out.println(">>>2" + proView2);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarBoton3() {
        try {
//            procesoBanco = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBanco().setFecha(fechaActual.getTime());
            setFechaRecup(fechaActual.getTime());
            System.out.println(">>>" + procesoBanco.getFecha());
            setDisableBotonGuardar(false);
            setProView2(true);
            setProView(false);
            limpiarProcesoBancoReg();
            System.out.println(">>>1" + proView);
            System.out.println(">>>2" + proView2);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarStatusGraba() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioService.modificarStatusGraba(procesoEnvioBean);
            listaProcesoEnvioBean = procesoEnvioService.obtenerPorUnineg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (flgGrabar) {
                for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                    listaProcesoEnvioBean.get(i).setFlgGrabar(true);
                    procesoEnvioService.modificarStatusGraba(listaProcesoEnvioBean.get(i));
                    flgGrabar = true;
                }
            } else {
                if (!flgGrabar) {
                    for (int i = 0; i < listaProcesoEnvioBean.size(); i++) {
                        listaProcesoEnvioBean.get(i).setFlgGrabar(false);
                        procesoEnvioService.modificarStatusGraba(listaProcesoEnvioBean.get(i));
                        flgGrabar = false;
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarStatusCuentas() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioService.modificarEstadoEnvio(listaCtaFiltro, flgStatus);
//            if (flgStatus) {
//                for (int i = 0; i < listaCtaFiltro.size(); i++) {
//                    listaCtaFiltro.get(i).getIdCtasXCobrar();
//                    listaCtaFiltro.get(i).setFlgEnvio(true);
//                    procesoEnvioService.modificarStatusEnvioCuenta(listaCtaFiltro.get(i));
//                }
////                procesoEnvioService.modificarStatusEnvioCuentaTrueFalse(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), true, listaCtaFiltro);
//            } else {
//                if (!flgStatus) {
//                    for (int i = 0; i < listaCtaFiltro.size(); i++) {
//                        listaCtaFiltro.get(i).getIdCtasXCobrar();
//                        listaCtaFiltro.get(i).setFlgEnvio(false);
//                        procesoEnvioService.modificarStatusEnvioCuenta(listaCtaFiltro.get(i));
//                    }
////                    procesoEnvioService.modificarStatusEnvioCuentaTrueFalse(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), false, listaCtaFiltro);
//                }
//            } 
            Integer res = 0;
            for (CuentasPorCobrarBean ctas : listaCtaFiltro) {
                if (ctas.getIdCtasXCobrar() != null && ctas.getFlgEnvio().equals(false)) {
                    res = ctas.getMonto().intValue() + res;
                } else {
                    if (ctas.getIdCtasXCobrar() != null && ctas.getFlgEnvio().equals(true)) {
                        res = ctas.getMonto().intValue() + res;
                    }
                }
            }
            res = totalEnvios;
            obtenerEnvioCuenta();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowEdit2(RowEditEvent event) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean.setFlgEnvio(((CuentasPorCobrarBean) event.getObject()).getFlgEnvio());
            cuentasPorCobrarBean.setModiPor(beanUsuarioSesion.getUsuario());
            cuentasPorCobrarBean.setIdCtasXCobrar(((CuentasPorCobrarBean) event.getObject()).getIdCtasXCobrar());
            procesoEnvioService.modificarStatusEnvioCuenta(cuentasPorCobrarBean);
            listaCtaFiltro = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            FacesMessage msg = new FacesMessage("Cambio exitoso: ", ((CuentasPorCobrarBean) event.getObject()).getProcesoEnvioBean().getProcesoBancoBean().getNombre());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onRowCancel2(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edici贸n Cancelada", ((CuentasPorCobrarBean) event.getObject()).getProcesoEnvioBean().getProcesoBancoBean().getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void activarBusqueda() {
        try {
            mostrarAc = 0;
            mostrarLayout = false;
            mostrarMatricula = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void activarBusquedaProceso() {
        try {
            mostrarLayoutPrincipal = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            listaCtasXCobrarBean = procesoEnvioService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (int i = 0; i < listaCtasXCobrarBean.size(); i++) {
                listaCtasXCobrarBean.get(i).setFlgEnvio(true);
                listaCtasXCobrarBean.get(i).setModiPor(beanUsuarioSesion.getUsuario());
                procesoEnvioService.modificarStatusEnvioCuenta(listaCtasXCobrarBean.get(i));
                System.out.println("===>>>" + i);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirEnvioPdf() {
        ServletOutputStream out = null;
        try {
            obtenerProcesoEnvioPorId2(procesoBancoBean);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteAprobadosEnvio.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<ProcesoEnvioRepBean> listaProcesoEnvioRepBean = new ArrayList<>();
            for (ProcesoEnvioBean proEnvio : listaProcesoEnvioFiltroBean) {
                String apepat = proEnvio.getIdDiscente().getPersonaBean().getApepat().substring(0, 1);
                String apepatResto = apepat.concat(proEnvio.getIdDiscente().getPersonaBean().getApepat().substring(1, proEnvio.getIdDiscente().getPersonaBean().getApepat().length()).toLowerCase());
                String apemat = proEnvio.getIdDiscente().getPersonaBean().getApemat().substring(0, 1);
                String apematResto = apemat.concat(proEnvio.getIdDiscente().getPersonaBean().getApemat().substring(1, proEnvio.getIdDiscente().getPersonaBean().getApemat().length()).toLowerCase());

                ProcesoEnvioRepBean envioReporte = new ProcesoEnvioRepBean();
                envioReporte.setIdPersona(proEnvio.getIdDiscente().getPersonaBean().getIdPersona());
                envioReporte.setApepat(apepatResto);
                envioReporte.setApemat(apematResto);
                envioReporte.setNombrePersona(proEnvio.getIdDiscente().getPersonaBean().getNombre());
                envioReporte.setMonto(proEnvio.getMonto());
                envioReporte.setNomConcepto(proEnvio.getConceptoBean().getNombre());
                envioReporte.setEstadoEnvio(proEnvio.getStatusEnvio());
                listaProcesoEnvioRepBean.add(envioReporte);
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaProcesoEnvioRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\jasper\\";
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
    }

    public void imprimirRecupPdf() {
        ServletOutputStream out = null;
        try {
            obtenerProcesoEnvioPorId2(procesoBancoBean);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteAprobadosRecuperacion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<ProcesoRecuperacionRepBean> listaProcesoRecuperacionRepBean = new ArrayList<>();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            for (ProcesoRecuperacionBean proReporte : listaProRec) {
                String fecha = formato.format(proReporte.getFechaPago());
                String apepat = proReporte.getEstudianteBean().getPersonaBean().getApepat().substring(0, 1);
                String apepatResto = apepat.concat(proReporte.getEstudianteBean().getPersonaBean().getApepat().substring(1, proReporte.getEstudianteBean().getPersonaBean().getApepat().length()).toLowerCase());
                String apemat = proReporte.getEstudianteBean().getPersonaBean().getApemat().substring(0, 1);
                String apematResto = apemat.concat(proReporte.getEstudianteBean().getPersonaBean().getApemat().substring(1, proReporte.getEstudianteBean().getPersonaBean().getApemat().length()).toLowerCase());
                ProcesoRecuperacionRepBean recupReporte = new ProcesoRecuperacionRepBean();
                recupReporte.setIdpersona(proReporte.getEstudianteBean().getPersonaBean().getIdPersona());
                recupReporte.setNombre(proReporte.getEstudianteBean().getPersonaBean().getNombre());
                recupReporte.setApepat(apepatResto);
                recupReporte.setApemat(apematResto);
                recupReporte.setMontorecup(proReporte.getMontoRecup());
                recupReporte.setDatoPension(proReporte.getDatoPension());
                recupReporte.setFechapago(fecha);
                listaProcesoRecuperacionRepBean.add(recupReporte);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaProcesoRecuperacionRepBean);
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
    }

    public void obtenerColumnas(Object object, Integer file) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            procesoFilesBean = (ProcesoFilesBean) object;
            List<Object> ListaRow = new ArrayList<>();
            List<List<Object>> lista = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            if (file.equals(1)) {
                switch (procesoFilesBean.getColumnaCab()) {
                    case "A駉":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "anio", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "RUC":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "ruc", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Fecha":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "creaFecha", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Nombre de Proceso":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "nombre", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Codigo de UniNeg":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "codunineg", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Numero de Cuenta":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "numcuenta", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Tipo de Moneda":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "moneda", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Registros enviados":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "regenv", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Monto Enviado":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "montoenv", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Hora":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "horacorte", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "Tipo de Archivo":
                        procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                        procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "tipoarchivo", 1, "", beanUsuarioSesion.getUsuario());
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        break;

                    case "otros":
                        RequestContext.getCurrentInstance().addCallbackParam("otro", true);
                        listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
                        setValorExec(1);
                }
            }

            if (file.equals(2)) {
                switch (procesoFilesBean.getColumna()) {
                    case "Codigo de Estudiante":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "iddiscente", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Codigo Antiguo":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "codigodiscente", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Nombres de Estudiante":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "nomdiscente", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Inf. de Retorno":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "inforetorno", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Fecha de Emisi髇":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "fechaemision", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Fecha de Vencimiento":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "fechavenc", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Tipo de Moneda":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "moneda", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Importe":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "monto", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Mora":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "mora", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Tipo de Registro":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "tiporegistro", 1, "", 0);
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "Concepto":
                        procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        break;
                    case "otros":
                        RequestContext.getCurrentInstance().addCallbackParam("otro", true);
                        listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
                        setValorExec(2);
                }
            }

            if (file.equals(3)) {
                switch (procesoFilesBean.getColumnaCab()) {
                    case "A駉":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "anio", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "RUC":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "ruc", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Fecha":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "creaFecha", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Nombre de Proceso":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "nombre", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Codigo de UniNeg":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "codunineg", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Numero de Cuenta":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "numcuenta", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Tipo de Moneda":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "moneda", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Registros enviados":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "regenv", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Monto Enviado":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "montoenv", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Hora":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "horacorte", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Tipo de Archivo":
                        procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                        procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "tipoarchivo", 1, "", beanUsuarioSesion.getUsuario());
                        RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        break;

                    case "Otros":
                        RequestContext.getCurrentInstance().addCallbackParam("otro", true);
                        listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
                        setValorExec(3);
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerColumnasOtros() {
        try {
            Integer valor = procesoFinalBean.getValorConstante();;
            valor = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            if (valorExec.equals(1)) {
                procesoFinalService.execProEnvioCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
                procesoFinalService.execProEnvioCabIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "", valor, procesoFinalBean.getConstante(), beanUsuarioSesion.getUsuario());
                listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
            }
            if (valorExec.equals(2)) {
                procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "", valor, procesoFinalBean.getConstante(), 0);
                procesoFilesBean.setValidate(1);
                procesoFilesBean.setModiPor(beanUsuarioSesion.getUsuario());
                procesoFilesService.modificarDisabled(procesoFilesBean);
                listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
            }
            if (valorExec.equals(3)) {
                procesoFinalService.execProEnvioInt(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProIntEnv.size(), procesoBancoBean.getIdProcesoBanco());
                procesoFinalService.execProEnvioIntIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), procesoFilesBean.getPosicionItem(), "", valor, procesoFinalBean.getConstante(), beanUsuarioSesion.getUsuario());
                listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
            }
            listaColumnas = procesoFinalService.obtenerColumnas();
            limpiarValorConstante();
            RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerColumnasNoConstantes(Integer index) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            System.out.println(">>>" + index);
            if (index != 0) {
                procesoFinalService.execProEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), listaProDetEnv.size(), procesoBancoBean.getIdProcesoBanco());
                procesoFinalService.execProEnvioIns(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getUsuario(), procesoFilesBean.getPosicionItem(), "", 0, "", index);
                RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerConstante() {
        try {
            setInput(0);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerNoConstante() {
        try {
            listaNoConstante = new ArrayList<>();
            listaNoConstante.add(0, "Nombres del Padre");
            listaNoConstante.add(1, "Nombres de la Madre");
            listaNoConstante.add(2, "Nombres del Responsable de Pago");
            listaNoConstante.add(3, "DNI del Padre");
            listaNoConstante.add(4, "DNI de la Madre");
            listaNoConstante.add(5, "DNI del Responsable de Pago");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarValor() {
        try {
            System.out.println(">>>>" + columna);
            if (columna.equals(20101)
                    || columna.equals(20102)
                    || columna.equals(20105)) {
                setInput(1);
            } else {
                if (columna.equals(20103)) {
                    setInput(2);
                } else {
                    if (columna.equals(20104)) {
                        setInput(3);
                    }
                }
            }
            System.out.println(">>>>" + input);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarFile() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaFilesCab = procesoFinalService.obtenerCabFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProCabEnv.size());
            listaFiles = procesoFinalService.obtenerFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco());
            listaFilesInt = procesoFinalService.obtenerIntFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco(), listaProIntEnv.size());
            List<ProcesoFilesBean> listaDetalle = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaDetalle = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
            if (!listaFilesCab.isEmpty() && listaFilesCab != null) {
                for (int k = 0; k < listaFilesCab.get(1).size(); k++) {
                    pw.print(listaFilesCab.get(1).get(k));
                }
                pw.println();
            }
            if (!listaFilesInt.isEmpty() && listaFilesInt != null) {
                for (int k = 0; k < listaFilesInt.get(1).size(); k++) {
                    pw.print(listaFilesInt.get(1).get(k));
                }
                pw.println();
            }
            System.out.println("lista " + listaFiles.size());
            if (!listaFiles.isEmpty() && listaFiles != null) {
                for (int i = 1; i <= listaFiles.size(); i++) {
                    for (int j = 0; j < listaFiles.get(i).size(); j++) {
                        pw.print(listaFiles.get(i).get(j));
                    }
                    pw.println();
                }
                pw.close();
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "text1");
//            if (!listaFilesCab.isEmpty() && listaFilesCab != null && !listaFiles.isEmpty() && listaFiles != null) {
//                for (int k = 0; k < listaFilesCab.get(1).size(); k++) {
//                    pw.print(listaFilesCab.get(1).get(k));
//                }
//                pw.println();
//                for (int i = 1; i < listaFiles.size(); i++) {
//                    for (int j = 0; j < listaFiles.get(i).size(); j++) {
//                        pw.print(listaFiles.get(i).get(j));
//                    }
//                    pw.println();
//                }
//                pw.close();
//                content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "text1");
//            } else {
//                pw.print("Configure su Archivo.....");
//                content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "text1");
//            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void mostrarFilesEnvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            List<Contenedor> listaContenedorCabe = new ArrayList<>();
            List<Contenedor> listaContenedorInte = new ArrayList<>();

            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            listaContenedorInte = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
            listaContenedorCabe = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
            if (listaContenedorDeta.isEmpty() || listaContenedorInte.isEmpty() || listaContenedorCabe.isEmpty()) {
                if (!listaContenedorCabe.isEmpty()) {
                    for (int i = 0; i < listaContenedorCabe.size(); i++) {
                        for (int j = 0; j < listaContenedorCabe.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorCabe.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorCabe.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorCabe.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorCabe.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                    pw.println();
                }
                if (!listaContenedorInte.isEmpty()) {
                    for (int i = 0; i < listaContenedorInte.size(); i++) {
                        for (int j = 0; j < listaContenedorInte.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorInte.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorInte.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorInte.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorInte.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                    pw.println();
                }
                if (!listaContenedorDeta.isEmpty()) {
                    for (int i = 0; i < listaContenedorDeta.size(); i++) {
                        for (int j = 0; j < listaContenedorDeta.get(i).getListaContenedor().size(); j++) {
                            if (j < (listaContenedorDeta.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                            } else {
                                if (j == (listaContenedorDeta.get(i).getListaContenedor().size() - 1)) {
                                    pw.print(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                                    pw.println();
                                }
                            }
                        }
                    }
                }
                pw.close();
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "Envio");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void eliminarFile() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            procesoFinalService.eliminarArchivoFile(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), procesoBancoBean.getIdProcesoBanco());
            RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarValorConstante() {
        try {
            procesoFinalBean = new ProcesoFinalBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDisabled() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            List<ProcesoFilesBean> listaDetalle = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaDetalle = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
            Integer disabled = 0;
            Integer res = 0;

            res = listaDetalle.size() - disabled;
            listaProDetEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20002);
            listaProCabEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20001);
            listaProIntEnv = procesoFilesService.obtenerFileProceso(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getRuc(), 2, 20003);
            if (!listaProDetEnv.isEmpty()) {
                setF1(1);
            } else {
                setF1(0);
            }
            if (!listaProCabEnv.isEmpty()) {
                setF2(1);
            } else {
                setF2(0);
            }
            if (!listaProIntEnv.isEmpty()) {
                setF3(1);
            } else {
                setF3(0);
            }
            System.out.println(">>>" + f1 + f2 + f3);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    //Lovon
    private DataTable dataTable;

    public void generaTabla() throws Exception {
//        dataTable.setValue(listaProBancos);
//        List<UIColumn> listaColumnas = new ArrayList<>();
        UIColumn column = new Column();
        HtmlOutputText texto1 = new HtmlOutputText();
        texto1.setValue("ejejeje");
        column.getChildren().add(texto1);
//        listaColumnas.add(column);
//        column.
//        dataTable.setColumns(listaColumnas);
        getDataTable().getChildren().add((UIComponent) column);
        System.out.println("GEnerooooooooooooo");
    }

    public void obtenerProcesoError() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoErrorService procesoErrorService = BeanFactory.getProcesoErrorService();
            listaProcesoErrorBean = procesoErrorService.obtenerPorProcesoBanco(procesoBancoBean.getIdProcesoBanco(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            ProcesoFalloService procesoFalloService = BeanFactory.getProcesoFalloService();
            listaProcesoFalloBean = procesoFalloService.obtenerFalloPorBanco(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaBanco() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            listaCuentaBancoBean = cuentaBancoService.obtenerCuentaPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerProceso() {
        try {
            listaProcesos = new LinkedHashMap<>();
            listaProcesos.put("Envio", 1);
            listaProcesos.put("Recepcion", 0);
            listaProcesos = Collections.unmodifiableMap(listaProcesos);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizarAnioMatricula() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            matriculaFiltroBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            listaMatriculaBean = matriculaService.obtenerMatriculaUniNegAnio(matriculaFiltroBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* Metodos Ordenados */
    public void setearValores() {
        try {
            if (getCuentaFiltroBean().getEstudianteBean().getCodigo() != null) {
                getCuentaFiltroBean().getEstudianteBean().setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
            }
            if (getCuentaFiltroBean().getEstudianteBean().getIdEstudiante() != null) {
                getCuentaFiltroBean().getEstudianteBean().setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
            }
            if (getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                getCuentaFiltroBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
            }
            if (getCuentaFiltroBean().getFechaInicio() != null) {
                getCuentaFiltroBean().setFechaInicio(getCuentaFiltroBean().getFechaInicio());
            }
            if (getCuentaFiltroBean().getFechaFin() != null) {
                getCuentaFiltroBean().setFechaFin(getCuentaFiltroBean().getFechaFin());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void setearValoresBanco() {
        try {

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void buscarEstudianteCuentas(Integer dato) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            Boolean res = validarCampos(dato);
            ProcesoEnvioBean proEnvio = new ProcesoEnvioBean();
            proEnvio.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            proEnvio.setIdProcesoBanco(idProceso);
            proEnvio.setRuc(rucEntidad);
            proEnvio.setCreaPor(beanUsuarioSesion.getUsuario());
            String fechaIni = "", fechaFin = "";
            if (res) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            } else {
                if (dato.equals(1)) {
                    SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
                    if (!getCuentaFiltroBean().getEstudianteBean().getCodigo().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getCodigo() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                        proEnvio.setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getIdEstudiante().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getIdEstudiante() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                        proEnvio.setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                        getCuentaFiltroBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                        proEnvio.setNombres(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                        proEnvio.setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getIdConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                        proEnvio.setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                    } else {
                        proEnvio.setIdConcepto(0);
                    }
                    if (getCuentaFiltroBean().getFechaInicio() != null) {
                        getCuentaFiltroBean().setFechaInicio(getCuentaFiltroBean().getFechaInicio());
                        proEnvio.setFechaIni(getCuentaFiltroBean().getFechaInicio());
                    }
                    if (getCuentaFiltroBean().getFechaFin() != null) {
                        getCuentaFiltroBean().setFechaFin(getCuentaFiltroBean().getFechaFin());
                        proEnvio.setFechaFin(getCuentaFiltroBean().getFechaFin());
                    }
                    String fechafin = obtenerFechaEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());;
                    String fecha = obtenerFechaEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
//                    procesoFinalService.execProEnvioProCur(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, getCuentaFiltroBean().getEstudianteBean().getIdEstudiante(), getCuentaFiltroBean().getEstudianteBean().getCodigo(), getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto(), beanUsuarioSesion.getUsuario(), dt.format(getCuentaFiltroBean().getFechaInicio()), dt.format(getCuentaFiltroBean().getFechaFin()), getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), getCuentaFiltroBean().getConceptoBean().getIdConcepto());

                    ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                    listaContenedorEnvio = procesoEnvioService.execProEnvioOperacion(proEnvio);
                    listaContenedorEnvioCabecera = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
                    listaContenedorEnvioIntermedio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
//                    if (selEnvio.equals(1)) {
//                        listaContenedorEnvio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
//                    } else if (selEnvio.equals(0)) {
//                        listaContenedorEnvio = procesoFinalService.obtenerEnvioBancosCurCod(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
//                    }
                    setDisabledExporter(true);
                }
                if (dato.equals(2)) {
                    System.out.println(">>>> " + 2);
                    SimpleDateFormat dt1 = new SimpleDateFormat("yyyyy-dd-mm");
                    if (!getCuentaFiltroBean().getEstudianteBean().getCodigo().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getCodigo() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setCodigo(getCuentaFiltroBean().getEstudianteBean().getCodigo());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getIdEstudiante().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getIdEstudiante() != null) {
                        getCuentaFiltroBean().getEstudianteBean().setIdEstudiante(getCuentaFiltroBean().getEstudianteBean().getIdEstudiante());
                    }
                    if (!getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")
                            && getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                        getCuentaFiltroBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                    }
                    if (getCuentaFiltroBean().getConceptoBean().getIdConcepto() != null) {
                        getCuentaFiltroBean().getConceptoBean().setIdConcepto(getCuentaFiltroBean().getConceptoBean().getIdConcepto());
                    }
                    if (getCuentaFiltroBean().getFechaInicio() != null) {
                        getCuentaFiltroBean().setFechaInicio(getCuentaFiltroBean().getFechaInicio());
                    }
                    if (getCuentaFiltroBean().getFechaFin() != null) {
                        getCuentaFiltroBean().setFechaFin(getCuentaFiltroBean().getFechaFin());
                    }
                    if (tipoRegistro != null) {
                        setTipoRegistro(getTipoRegistro());
                    }
                    if (flgEstado != null) {
                        setFlgEstado(getFlgEstado());
                    }
                    if (getCuentaFiltroBean().getMes() != null) {
                        getCuentaFiltroBean().setMes(getCuentaFiltroBean().getMes());
                    }
                    String fecha = obtenerFechaEnvio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
//                    procesoFinalService.execProEnvioPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, getCuentaFiltroBean().getFechaVenc(), getCuentaFiltroBean().getFechaInicio(), null, getCuentaFiltroBean().getEstudianteBean().getIdEstudiante(), getCuentaFiltroBean().getEstudianteBean().getCodigo(), getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto(), getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), getCuentaFiltroBean().getConceptoBean().getIdConcepto(), beanUsuarioSesion.getUsuario(), getTipoRegistro(), getFlgEstado());
                    ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
                    procesoEnvioService.execProEnvioPro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, getCronogramaPagoBean().getAnio(), getCuentaFiltroBean().getEstudianteBean().getIdEstudiante(), getCuentaFiltroBean().getEstudianteBean().getCodigo(), getCuentaFiltroBean().getEstudianteBean().getPersonaBean().getNombreCompleto(), getCuentaFiltroBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), getCuentaFiltroBean().getConceptoBean().getIdConcepto(), beanUsuarioSesion.getUsuario(), getTipoRegistro(), getFlgEstado(), getCuentaFiltroBean().getMes());
                    procesoFinalService.execProEnvioProCab(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, beanUsuarioSesion.getUsuario(), fecha, getTipoRegistro());
                    listaContenedorEnvioCabecera = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileCabecera, 1);
                    listaContenedorEnvioIntermedio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileIntermedio, 2);
                    listaContenedorEnvio = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
                    setDisabledExporter(true);
                }
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                filas = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, rucEntidad);
                filasCab = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, rucEntidad);
                filasInt = procesoFilesService.obtenerNumFiles(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, rucEntidad);
                System.out.println(">>>" + listaContenedorEnvioCabecera.size());
                System.out.println(">>>" + listaContenedorEnvioIntermedio.size());
                System.out.println(">>>" + listaContenedorEnvio.size());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String obtenerFechaEnvio(String uniNeg) {
        String fecha = "";
        try {
            /* Nota: Por el Momento solo se tienen fechas de Operacion de SANLUI */
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_SANLUI:
                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                    fecha = dt.format(getFechaRecup());
                    break;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return fecha;
    }

    public Boolean validarCampos(Integer dato) {
        Boolean res = false;
        try {
            if (dato.equals(2)) {
//                if (getCuentaFiltroBean().getFechaInicio() == null) {
//                    res = true;
//                }
//                if (getCuentaFiltroBean().getFechaVenc() == null) {
//                    res = true;
//                }
            } else {
                if (dato.equals(1)) {
                    if (getCuentaFiltroBean().getFechaInicio() == null) {
                        res = true;
                    }
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return res;
    }

    public void limpiarBusquedaEnvio() {
        try {
            cuentaFiltroBean = new CuentasPorCobrarBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentaFiltroBean().setFechaInicio(fechaActual.getTime());
            getCuentaFiltroBean().setFechaFin(fechaActual.getTime());
            listaContenedorEnvio = new ArrayList<>();
            listaContenedorEnvioCabecera = new ArrayList<>();
            listaContenedorEnvioIntermedio = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarProcesoBancoReg() {
        try {
            procesoBanco = new ProcesoBancoBean();
//            setTipoRegistro(null);
            setSelEnvio(null);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void insertarProcesoBancoEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            setTipoEnvio(procesoBanco.getTipoEnvio());
            System.out.println(">>>" + tipoEnvio);
            procesoBanco.setFlgProceso(1);
            procesoBanco.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBanco.setCreaPor(beanUsuarioSesion.getUsuario());
            procesoBanco.setTipoArchivo("C");
            procesoBancoService.insertarProcesoBanco(procesoBanco);
            RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
            limpiarProcesoBancoReg();
            idProceso = procesoBancoService.obtenerIdProcesoBancoMax(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1);
            disableBotonDescarga = false;
            disableBotonGuardar = true;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void descargarArchivoEnvio(Object document) {
        try {
            Boolean res = validarCampos(2);
            if (res) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            } else {
                HSSFWorkbook wb = (HSSFWorkbook) document;
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFRow header = sheet.getRow(0);
                HSSFCellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cerrarFiltroEnvio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            procesoFinalService.modificarEnvioFiltro(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.Defecto_Importe);
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesoBancoFiltroBean = procesoBancoService.obtenerPorIdBanco(idProceso, beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaContenedorEnvio = new ArrayList<>();
            listaContenedorEnvioCabecera = new ArrayList<>();
            listaContenedorEnvioIntermedio = new ArrayList<>();
            if (tipoEnvio.equals(1)) {
                RequestContext.getCurrentInstance().addCallbackParam("operacioncorrecta", true);
            } else if (tipoEnvio.equals(0)) {
                RequestContext.getCurrentInstance().addCallbackParam("operacioncorrectaOp", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerVistaPrevia() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            System.out.println(">>>>" + tipoEnvio);
            if (tipoEnvio.equals(1)) {
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                listaTipoConceptoBean = tipoConceptoService.obtenerPorTipoProcesoBanco();
                RequestContext.getCurrentInstance().addCallbackParam("cuenta", true);
            } else {
                if (tipoEnvio.equals(0)) {
                    TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                    listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();
                    RequestContext.getCurrentInstance().addCallbackParam("cuentaCur", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirReporteBanco() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDetActividad.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarCronograma() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            getCronogramaPagoBean().setAnio(getCronogramaPagoBean().getAnio());
            System.out.println(">>> anio " + getCronogramaPagoBean().getAnio());
            listaCronogramaPagoBean = cronogramaPagoService.obtenerCronogramaAnio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCronogramaPagoBean().getAnio());
            if (listaCronogramaPagoBean.isEmpty()) {
                System.out.println("lista vacia");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerVistaFile() {
        try {
            switch (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    setVistaFile(true);
                    break;
                case MaristaConstantes.UNI_NEG_SANLUI:
                    setVistaFile(true);
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    setVistaFile(false);
                    break;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaMeses() {
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
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarCuota() {
        try {
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setCuota(getProcesoBanco().getCuota());
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            procesoEnvioService.modificarCuota(procesoEnvioBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void crearXls() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat tiempo = new SimpleDateFormat("HH:mm:ss");
            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, 2, MaristaConstantes.FileDetalle);
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            Integer num = 1;
//            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setIdProcesoBanco(idProceso);
            num = procesoEnvioService.obtenerNumFilas(procesoEnvioBean);
            File file = new File("C:\\Users\\CPD\\Documents\\CJ\\Proyectos\\APACHE\\SIGMA_ENVIO.xls");
            if (file.exists()) {
                file.delete();
            }
            Workbook libro = new HSSFWorkbook();
            file.createNewFile();
            FileOutputStream archivo = new FileOutputStream(file);
            Sheet hoja = libro.createSheet("ENVIO_SIGMA");
            if (!listaProcesoFilesBean.isEmpty() && !listaContenedorDeta.isEmpty()) {
                for (int f = 0; f < listaProcesoFilesBean.size(); f++) {
                    Row fila = hoja.createRow(f);
                    System.out.println(">>>>>" + listaProcesoFilesBean.get(f).getTipoDato().getIdCodigo());
                    for (int i = 0; i < listaContenedorDeta.get(f).getListaContenedor().size(); i++) {
                        Cell celda = fila.createCell(i);
                        System.out.println(">>>>>" + listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo() + " // " + listaContenedorDeta.get(f).getListaContenedor().get(i).getValor());
                        if (listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo().equals(20101)) {
                            if (listaContenedorDeta.get(f).getListaContenedor().get(i).getValor() != null
                                    && !listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().equals("")) {
                                celda.setCellValue(listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().toString());
                            } else {
                                celda.setCellValue("");
                            }
                        } else if (listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo().equals(20102)) {
                            if (listaContenedorDeta.get(f).getListaContenedor().get(i).getValor() != null
                                    && !listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().equals("")) {
                                celda.setCellValue(Integer.parseInt(listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().toString()));
                            } else {
                                celda.setCellValue(Integer.parseInt("0"));
                            }
                        } else if (listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo().equals(20103)) {
                            if (listaContenedorDeta.get(f).getListaContenedor().get(i).getValor() != null
                                    && !listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().equals("")) {
                                celda.setCellValue(formato.parse(listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().toString()));
                            } else {
                                celda.setCellValue("");
                            }
                        } else if (listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo().equals(20104)) {
                            if (listaContenedorDeta.get(f).getListaContenedor().get(i).getValor() != null
                                    && !listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().equals("")) {
                                celda.setCellValue(tiempo.parse(listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().toString()));
                            } else {
                                celda.setCellValue("");
                            }
                        } else if (listaProcesoFilesBean.get(i).getTipoDato().getIdCodigo().equals(20105)) {
                            if (listaContenedorDeta.get(f).getListaContenedor().get(i).getValor() != null
                                    && !listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().equals("")) {
                                celda.setCellValue(Double.parseDouble(listaContenedorDeta.get(f).getListaContenedor().get(i).getValor().toString()));
                            } else {
                                celda.setCellValue(Double.parseDouble("0"));
                            }
                        }
                    }
                }
            }
            libro.write(archivo);
            archivo.close();
            content = new DefaultStreamedContent(new FileInputStream(file), "xls", "ENVIO_SIGMA");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void generaHoja(Object document) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat tiempo = new SimpleDateFormat("HH:mm:ss");
            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, 2, MaristaConstantes.FileDetalle);
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            Integer num = 0;
            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setIdProcesoBanco(idProceso);
            num = procesoEnvioService.obtenerNumFilas(procesoEnvioBean);
            File file = new File("C:\\Users\\CPD\\Documents\\CJ\\Proyectos\\APACHE\\SIGMA_ENVIO.xls");
//            File file = new File("SIGMA_ENVIO.xls");
            if (file.exists()) {
                file.delete();
            }
//            HSSFWorkbook wb = (HSSFWorkbook) document;
//            Workbook libro = new HSSFWorkbook();
            Workbook libro = (Workbook) document;
            file.createNewFile();
            FileOutputStream archivo = new FileOutputStream(file);
            Sheet hoja = libro.createSheet("ENVIO_SIGMA");
            if (!listaProcesoFilesBean.isEmpty() && !listaContenedorDeta.isEmpty()) {
                for (int i = 0; i < num; i++) {
                    Row fila = hoja.createRow(i);
                    for (int j = 0; j < listaContenedorDeta.get(i).getListaContenedor().size(); j++) {
                        Cell celda = fila.createCell(j);
                        System.out.println(">>>" + listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                        for (int k = 0; k < listaProcesoFilesBean.size(); k++) {
                            if (j != 0) {
                                if (j == k) {
                                    if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20101)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString());
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20102)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Integer.parseInt(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Integer.parseInt("0"));
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(formato.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20104)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(tiempo.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Double.parseDouble(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Double.parseDouble("0"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            libro.write(archivo);
            archivo.close();
            content = new DefaultStreamedContent(new FileInputStream(file), "application/xls", "ENVIO_SIGMA");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void postXls(Object document) {
        try {
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow header = sheet.getRow(0);
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM/dd/yyyy"));

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat tiempo = new SimpleDateFormat("HH:mm:ss");

            List<ProcesoFilesBean> listaProcesoFilesBean = new ArrayList<>();
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, 2, MaristaConstantes.FileDetalle);
            List<Contenedor> listaContenedorDeta = new ArrayList<>();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), rucEntidad, idProceso, MaristaConstantes.FileDetalle, 3);
            ProcesoEnvioBean procesoEnvioBean = new ProcesoEnvioBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            Integer num = 0;
            procesoEnvioBean.setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setIdProcesoBanco(idProceso);
            num = procesoEnvioService.obtenerNumFilas(procesoEnvioBean);

            if (!listaProcesoFilesBean.isEmpty() && !listaContenedorDeta.isEmpty()) {
                /* CREANDO DETALLE */
                for (int i = 0; i < num; i++) {
                    HSSFRow fila = sheet.createRow(i);
                    for (int j = 0; j < listaContenedorDeta.get(i).getListaContenedor().size(); j++) {
                        HSSFCell celda = fila.createCell(j);
                        System.out.println(">>>" + listaContenedorDeta.get(i).getListaContenedor().get(j).getValor());
                        for (int k = 0; k < listaProcesoFilesBean.size(); k++) {
                            if (j != 0) {
                                if (j == k) {
                                    if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20101)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString());
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20102)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Integer.parseInt(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Integer.parseInt("0"));
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20103)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(formato.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                            celda.setCellStyle(cellStyle);
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20104)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(tiempo.parse(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue("");
                                        }
                                    } else if (listaProcesoFilesBean.get(k).getTipoDato().getIdCodigo().equals(20105)) {
                                        if (listaContenedorDeta.get(i).getListaContenedor().get(j).getValor() != null
                                                && !listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().equals("")) {
                                            celda.setCellValue(Double.parseDouble(listaContenedorDeta.get(i).getListaContenedor().get(j).getValor().toString()));
                                        } else {
                                            celda.setCellValue(Double.parseDouble("0"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Integer hoja = sheet.getLastRowNum();
                System.out.println(">>>>" + hoja);
                HSSFRow ultimo = sheet.getRow(num + 1);
                sheet.removeRow(ultimo);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //=======================================================================================//
    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoFiltroBean() {
        return listaProcesoBancoFiltroBean;
    }

    public void setListaProcesoBancoFiltroBean(List<ProcesoBancoBean> listaProcesoBancoFiltroBean) {
        this.listaProcesoBancoFiltroBean = listaProcesoBancoFiltroBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public List<EntidadBean> getListaEntidadBean() {
        if (listaEntidadBean == null) {
            listaEntidadBean = new ArrayList<>();
        }
        return listaEntidadBean;
    }

    public void setListaEntidadBean(List<EntidadBean> listaEntidadBean) {
        this.listaEntidadBean = listaEntidadBean;
    }

    public List<CodigoBean> getListaMoneda() {
        if (listaMoneda == null) {
            listaMoneda = new ArrayList<>();
        }
        return listaMoneda;
    }

    public void setListaMoneda(List<CodigoBean> listaMoneda) {
        this.listaMoneda = listaMoneda;
    }

    public ProcesoEnvioBean getProcesoEnvioBean() {
        if (procesoEnvioBean == null) {
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
    }

    public List<ProcesoEnvioBean> getListaProcesoEnvioBean() {
        if (listaProcesoEnvioBean == null) {
            listaProcesoEnvioBean = new ArrayList<>();
        }
        return listaProcesoEnvioBean;
    }

    public void setListaProcesoEnvioBean(List<ProcesoEnvioBean> listaProcesoEnvioBean) {
        this.listaProcesoEnvioBean = listaProcesoEnvioBean;
    }

    public List<Object> getListaAnosRender() {
        if (listaAnosRender == null) {
            listaAnosRender = new ArrayList<>();
        }
        return listaAnosRender;
    }

    public void setListaAnosRender(List<Object> listaAnosRender) {
        this.listaAnosRender = listaAnosRender;
    }

    public ConceptoBean getConceptoBean() {
        if (conceptoBean == null) {
            conceptoBean = new ConceptoBean();
        }
        return conceptoBean;
    }

    public void setConceptoBean(ConceptoBean conceptoBean) {
        this.conceptoBean = conceptoBean;
    }

    public List<ConceptoBean> getListaconceptoBean() {
        if (listaconceptoBean == null) {
            listaconceptoBean = new ArrayList<>();
        }
        return listaconceptoBean;
    }

    public void setListaconceptoBean(List<ConceptoBean> listaconceptoBean) {
        this.listaconceptoBean = listaconceptoBean;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public List<CuentasPorCobrarBean> getListaCtasXCobrarBean() {
        if (listaCtasXCobrarBean == null) {
            listaCtasXCobrarBean = new ArrayList<>();
        }
        return listaCtasXCobrarBean;
    }

    public void setListaCtasXCobrarBean(List<CuentasPorCobrarBean> listaCtasXCobrarBean) {
        this.listaCtasXCobrarBean = listaCtasXCobrarBean;
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

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
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

    public CuentasPorCobrarBean getCuentasFiltroBean() {
        if (cuentasFiltroBean == null) {
            cuentasFiltroBean = new CuentasPorCobrarBean();
        }
        return cuentasFiltroBean;
    }

    public void setCuentasFiltroBean(CuentasPorCobrarBean cuentasFiltroBean) {
        this.cuentasFiltroBean = cuentasFiltroBean;
    }

    public ProcesoFileBean getArchivoFile() {
        if (archivoFile == null) {
            archivoFile = new ProcesoFileBean();
        }
        return archivoFile;
    }

    public void setArchivoFile(ProcesoFileBean archivoFile) {
        this.archivoFile = archivoFile;
    }

    public List<ProcesoFileBean> getListaProcesoFile() {
        if (listaProcesoFile == null) {
            listaProcesoFile = new ArrayList<>();
        }
        return listaProcesoFile;
    }

    public void setListaProcesoFile(List<ProcesoFileBean> listaProcesoFile) {
        this.listaProcesoFile = listaProcesoFile;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if (procesoRecuperacionBean == null) {
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public List<ProcesoRecuperacionBean> getListaProcesoRecuperacion() {
        if (listaProcesoRecuperacion == null) {
            listaProcesoRecuperacion = new ArrayList<>();
        }
        return listaProcesoRecuperacion;
    }

    public void setListaProcesoRecuperacion(List<ProcesoRecuperacionBean> listaProcesoRecuperacion) {
        this.listaProcesoRecuperacion = listaProcesoRecuperacion;
    }

    public ProcesoFileBean getProcesoFileBean() {
        if (procesoFileBean == null) {
            procesoFileBean = new ProcesoFileBean();
        }
        return procesoFileBean;
    }

    public void setProcesoFileBean(ProcesoFileBean procesoFileBean) {
        this.procesoFileBean = procesoFileBean;
    }

    public List<ProcesoFileBean> getListaProcesoFileBean() {
        if (listaProcesoFileBean == null) {
            listaProcesoFileBean = new ArrayList<>();
        }
        return listaProcesoFileBean;
    }

    public void setListaProcesoFileBean(List<ProcesoFileBean> listaProcesoFileBean) {
        this.listaProcesoFileBean = listaProcesoFileBean;
    }

    public ProcesoFileBean getProcesoFileFiltro() {
        if (procesoFileFiltro == null) {
            procesoFileFiltro = new ProcesoFileBean();
        }
        return procesoFileFiltro;
    }

    public void setProcesoFileFiltro(ProcesoFileBean procesoFileFiltro) {
        this.procesoFileFiltro = procesoFileFiltro;
    }

    public UploadedFile getFile() {
        if (file == null) {
            file = new UploadedFile() {

                @Override
                public String getFileName() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public InputStream getInputstream() throws IOException {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public long getSize() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public byte[] getContents() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getContentType() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

//                @Override
//                public void write(String string) throws Exception {
//                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
                @Override
                public void write(String string) throws Exception {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

            };
        }
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacion() {
        if (procesoRecuperacion == null) {
            procesoRecuperacion = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacion;
    }

    public void setProcesoRecuperacion(ProcesoRecuperacionBean procesoRecuperacion) {
        this.procesoRecuperacion = procesoRecuperacion;
    }

    public List<ProcesoRecuperacionBean> getListaProRec() {
        if (listaProRec == null) {
            listaProRec = new ArrayList<>();
        }
        return listaProRec;
    }

    public void setListaProRec(List<ProcesoRecuperacionBean> listaProRec) {
        this.listaProRec = listaProRec;
    }

    public Integer getVar1() {
        return var1;
    }

    public void setVar1(Integer var1) {
        this.var1 = var1;
    }

    public Integer getVar2() {
        return var2;
    }

    public void setVar2(Integer var2) {
        this.var2 = var2;
    }

    public Integer getVar3() {
        return var3;
    }

    public void setVar3(Integer var3) {
        this.var3 = var3;
    }

    public List<EntidadBean> getListaEntidad() {
        if (listaEntidad == null) {
            listaEntidad = new ArrayList<>();
        }
        return listaEntidad;
    }

    public void setListaEntidad(List<EntidadBean> listaEntidad) {
        this.listaEntidad = listaEntidad;
    }

    public List<ProcesoEnvioBean> getListaEnvioFiltroBean() {
        if (listaEnvioFiltroBean == null) {
            listaEnvioFiltroBean = new ArrayList<>();
        }
        return listaEnvioFiltroBean;
    }

    public void setListaEnvioFiltroBean(List<ProcesoEnvioBean> listaEnvioFiltroBean) {
        this.listaEnvioFiltroBean = listaEnvioFiltroBean;
    }

    public List<CuentasPorCobrarBean> getListaCtaFiltro() {
        if (listaCtaFiltro == null) {
            listaCtaFiltro = new ArrayList<>();
        }
        return listaCtaFiltro;
    }

    public void setListaCtaFiltro(List<CuentasPorCobrarBean> listaCtaFiltro) {
        this.listaCtaFiltro = listaCtaFiltro;
    }

    public List<EstudianteBean> getListaEstudianteFiltro() {
        if (listaEstudianteFiltro == null) {
            listaEstudianteFiltro = new ArrayList<>();
        }
        return listaEstudianteFiltro;
    }

    public void setListaEstudianteFiltro(List<EstudianteBean> listaEstudianteFiltro) {
        this.listaEstudianteFiltro = listaEstudianteFiltro;
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

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
    }

    public List<MatriculaBean> getListaMatriculaBean() {
        if (listaMatriculaBean == null) {
            listaMatriculaBean = new ArrayList<>();
        }
        return listaMatriculaBean;
    }

    public void setListaMatriculaBean(List<MatriculaBean> listaMatriculaBean) {
        this.listaMatriculaBean = listaMatriculaBean;
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

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void setModel(DefaultTableModel model) {
        this.model = model;
    }

    public ProcesoBancoBean getProcesoBancoFiltroBean() {
        if (procesoBancoFiltroBean == null) {
            procesoBancoFiltroBean = new ProcesoBancoBean();
        }
        return procesoBancoFiltroBean;
    }

    public void setProcesoBancoFiltroBean(ProcesoBancoBean procesoBancoFiltroBean) {
        this.procesoBancoFiltroBean = procesoBancoFiltroBean;
    }

    public ProcesoBancoBean getProcesoBanco() {
        if (procesoBanco == null) {
            procesoBanco = new ProcesoBancoBean();
        }
        return procesoBanco;
    }

    public void setProcesoBanco(ProcesoBancoBean procesoBanco) {
        this.procesoBanco = procesoBanco;
    }

    public List<ProcesoEnvioBean> getListaProcesoEnvioFiltroBean() {
        if (listaProcesoEnvioFiltroBean == null) {
            listaProcesoEnvioFiltroBean = new ArrayList<>();
        }
        return listaProcesoEnvioFiltroBean;
    }

    public void setListaProcesoEnvioFiltroBean(List<ProcesoEnvioBean> listaProcesoEnvioFiltroBean) {
        this.listaProcesoEnvioFiltroBean = listaProcesoEnvioFiltroBean;
    }

//    public List<Object> getListaAnosRender() {
//        return listaAnosRender;
//    }
//
//    public void setListaAnosRender(List<Object> listaAnosRender) {
//        this.listaAnosRender = listaAnosRender;
//    }
//
//    public List<Object> getListaAnos() {
//        return listaAnos;
//    }
//
//    public void setListaAnos(List<Object> listaAnos) {
//        this.listaAnos = listaAnos;
//    }
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal2() {
        return total2;
    }

    public void setTotal2(Integer total2) {
        this.total2 = total2;
    }

    public ProcesoEnvioBean getProcesoEnvioFiltroBean() {
        if (procesoEnvioFiltroBean == null) {
            procesoEnvioFiltroBean = new ProcesoEnvioBean();
        }
        return procesoEnvioFiltroBean;
    }

    public void setProcesoEnvioFiltroBean(ProcesoEnvioBean procesoEnvioFiltroBean) {
        this.procesoEnvioFiltroBean = procesoEnvioFiltroBean;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public ProcesoEnvioBean getProcesoEnvio() {
        if (procesoEnvio == null) {
            procesoEnvio = new ProcesoEnvioBean();
        }
        return procesoEnvio;
    }

    public void setProcesoEnvio(ProcesoEnvioBean procesoEnvio) {
        this.procesoEnvio = procesoEnvio;
    }

    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
    }

    public List<CuentaBancoBean> getListaCuentaBancoBean() {
        if (listaCuentaBancoBean == null) {
            listaCuentaBancoBean = new ArrayList<>();
        }
        return listaCuentaBancoBean;
    }

    public void setListaCuentaBancoBean(List<CuentaBancoBean> listaCuentaBancoBean) {
        this.listaCuentaBancoBean = listaCuentaBancoBean;
    }

    public boolean isMostrarTabla() {
        return mostrarTabla;
    }

    public void setMostrarTabla(boolean mostrarTabla) {
        this.mostrarTabla = mostrarTabla;
    }

    public Integer getEnvio() {
        return envio;
    }

    public void setEnvio(Integer envio) {
        this.envio = envio;
    }

    public Integer getTotalEnvios() {
        return totalEnvios;
    }

    public void setTotalEnvios(Integer totalEnvios) {
        this.totalEnvios = totalEnvios;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Map<String, String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getIdMes() {
        return idMes;
    }

    public void setIdMes(String idMes) {
        this.idMes = idMes;
    }

    public String getFlgDeuda() {
        return flgDeuda;
    }

    public void setFlgDeuda(String flgDeuda) {
        this.flgDeuda = flgDeuda;
    }

    public Map<String, String> getListaDeuda() {
        return listaDeuda;
    }

    public void setListaDeuda(Map<String, String> listaDeuda) {
        this.listaDeuda = listaDeuda;
    }

    public boolean isMostrarPanelBus() {
        return mostrarPanelBus;
    }

    public void setMostrarPanelBus(boolean mostrarPanelBus) {
        this.mostrarPanelBus = mostrarPanelBus;
    }

    public int getDatoAnio() {
        return datoAnio;
    }

    public void setDatoAnio(int datoAnio) {
        this.datoAnio = datoAnio;
    }

    public int getDatoMes() {
        return datoMes;
    }

    public void setDatoMes(int datoMes) {
        this.datoMes = datoMes;
    }

    public int getDatoDia() {
        return datoDia;
    }

    public void setDatoDia(int datoDia) {
        this.datoDia = datoDia;
    }

    public int getDatoHora() {
        return datoHora;
    }

    public void setDatoHora(int datoHora) {
        this.datoHora = datoHora;
    }

    public int getDatoMinuto() {
        return datoMinuto;
    }

    public void setDatoMinuto(int datoMinuto) {
        this.datoMinuto = datoMinuto;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public boolean isMostrarAcc() {
        return mostrarAcc;
    }

    public void setMostrarAcc(boolean mostrarAcc) {
        this.mostrarAcc = mostrarAcc;
    }

    public boolean isMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public List<ProcesoRecuperacionBean> getListaRecuperacion() {
        if (listaRecuperacion == null) {
            listaRecuperacion = new ArrayList<>();
        }
        return listaRecuperacion;
    }

    public void setListaRecuperacion(List<ProcesoRecuperacionBean> listaRecuperacion) {
        this.listaRecuperacion = listaRecuperacion;
    }

    public boolean isMostrarRec() {
        return mostrarRec;
    }

    public void setMostrarRec(boolean mostrarRec) {
        this.mostrarRec = mostrarRec;
    }

    public Integer getMostrarAc() {
        return mostrarAc;
    }

    public void setMostrarAc(Integer mostrarAc) {
        this.mostrarAc = mostrarAc;
    }

    public boolean isActivarBoton() {
        return activarBoton;
    }

    public void setActivarBoton(boolean activarBoton) {
        this.activarBoton = activarBoton;
    }

    public boolean isMostrarLayout() {
        return mostrarLayout;
    }

    public void setMostrarLayout(boolean mostrarLayout) {
        this.mostrarLayout = mostrarLayout;
    }

    public boolean isMostrarMatricula() {
        return mostrarMatricula;
    }

    public void setMostrarMatricula(boolean mostrarMatricula) {
        this.mostrarMatricula = mostrarMatricula;
    }

    public boolean isMostrarBoton() {
        return mostrarBoton;
    }

    public void setMostrarBoton(boolean mostrarBoton) {
        this.mostrarBoton = mostrarBoton;
    }

    public boolean isMostrarBotonS() {
        return mostrarBotonS;
    }

    public void setMostrarBotonS(boolean mostrarBotonS) {
        this.mostrarBotonS = mostrarBotonS;
    }

    public Integer getTotalErroneos() {
        return totalErroneos;
    }

    public void setTotalErroneos(Integer totalErroneos) {
        this.totalErroneos = totalErroneos;
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

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBanco() {
        if (listaProcesoBanco == null) {
            listaProcesoBanco = new ArrayList<>();
        }
        return listaProcesoBanco;
    }

    public void setListaProcesoBanco(List<ProcesoBancoBean> listaProcesoBanco) {
        this.listaProcesoBanco = listaProcesoBanco;
    }

//    public boolean isMostrarBoton2() {
//        return mostrarBoton2;
//    }
//
//    public void setMostrarBoton2(boolean mostrarBoton2) {
//        this.mostrarBoton2 = mostrarBoton2;
//    }
    public boolean isValFlgEnvio() {
        return valFlgEnvio;
    }

    public void setValFlgEnvio(boolean valFlgEnvio) {
        this.valFlgEnvio = valFlgEnvio;
    }

    public boolean isFlgEnvio() {
        return flgEnvio;
    }

    public void setFlgEnvio(boolean flgEnvio) {
        this.flgEnvio = flgEnvio;
    }

    public boolean isFlgGrabar() {
        return flgGrabar;
    }

    public void setFlgGrabar(boolean flgGrabar) {
        this.flgGrabar = flgGrabar;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarFiltroBean() {
        if (cuentasPorCobrarFiltroBean == null) {
            cuentasPorCobrarFiltroBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarFiltroBean;
    }

    public void setCuentasPorCobrarFiltroBean(CuentasPorCobrarBean cuentasPorCobrarFiltroBean) {
        this.cuentasPorCobrarFiltroBean = cuentasPorCobrarFiltroBean;
    }

    public boolean isMostrarLayoutPrincipal() {
        return mostrarLayoutPrincipal;
    }

    public void setMostrarLayoutPrincipal(boolean mostrarLayoutPrincipal) {
        this.mostrarLayoutPrincipal = mostrarLayoutPrincipal;
    }

    public List<CuentasPorCobrarBean> getListaCuentasCobrarBean() {
        if (listaCuentasCobrarBean == null) {
            listaCuentasCobrarBean = new ArrayList<>();
        }
        return listaCuentasCobrarBean;
    }

    public void setListaCuentasCobrarBean(List<CuentasPorCobrarBean> listaCuentasCobrarBean) {
        this.listaCuentasCobrarBean = listaCuentasCobrarBean;
    }

    public boolean isFlgStatus() {
        return flgStatus;
    }

    public void setFlgStatus(boolean flgStatus) {
        this.flgStatus = flgStatus;
    }

    public boolean isMostrarAlerta() {
        return mostrarAlerta;
    }

    public void setMostrarAlerta(boolean mostrarAlerta) {
        this.mostrarAlerta = mostrarAlerta;
    }

    public Integer getIndexAccNuevo() {
        return indexAccNuevo;
    }

    public void setIndexAccNuevo(Integer indexAccNuevo) {
        this.indexAccNuevo = indexAccNuevo;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getVar() {
        return var;
    }

    public void setVar(Integer var) {
        this.var = var;
    }

    public Integer getRes() {
        return res;
    }

    public void setRes(Integer res) {
        this.res = res;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public List<Contenedor> getListaProBancos() {
        if (listaProBancos == null) {
            listaProBancos = new ArrayList<>();
        }
        return listaProBancos;
    }

    public void setListaProBancos(List<Contenedor> listaProBancos) {
        this.listaProBancos = listaProBancos;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesBean() {
        if (listaProcesoFilesBean == null) {
            listaProcesoFilesBean = new ArrayList<>();
        }
        return listaProcesoFilesBean;
    }

    public void setListaProcesoFilesBean(List<ProcesoFilesBean> listaProcesoFilesBean) {
        this.listaProcesoFilesBean = listaProcesoFilesBean;
    }

    public TreeNode getAvailableColumns() {
        return availableColumns;
    }

    public void setAvailableColumns(TreeNode availableColumns) {
        this.availableColumns = availableColumns;
    }

    public List<Object> getListaColumnas() {
        if (listaColumnas == null) {
            listaColumnas = new ArrayList<>();
        }
        return listaColumnas;
    }

    public void setListaColumnas(List<Object> listaColumnas) {
        this.listaColumnas = listaColumnas;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public List<ProcesoFilesBean> getListaProDetEnv() {
        return listaProDetEnv;
    }

    public void setListaProDetEnv(List<ProcesoFilesBean> listaProDetEnv) {
        this.listaProDetEnv = listaProDetEnv;
    }

    public ProcesoFilesBean getProcesoFilesBean() {
        if (procesoFilesBean == null) {
            procesoFilesBean = new ProcesoFilesBean();
        }
        return procesoFilesBean;
    }

    public void setProcesoFilesBean(ProcesoFilesBean procesoFilesBean) {
        this.procesoFilesBean = procesoFilesBean;
    }

    public ProcesoFinalBean getProcesoFinalBean() {
        if (procesoFinalBean == null) {
            procesoFinalBean = new ProcesoFinalBean();
        }
        return procesoFinalBean;
    }

    public void setProcesoFinalBean(ProcesoFinalBean procesoFinalBean) {
        this.procesoFinalBean = procesoFinalBean;
    }

    public HashMap<Integer, List<Object>> getListaFiles() {
        if (listaFiles == null) {
            listaFiles = new HashMap<>();
        }
        return listaFiles;
    }

    public void setListaFiles(HashMap<Integer, List<Object>> listaFiles) {
        this.listaFiles = listaFiles;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<ProcesoFilesBean> getListaProCabEnv() {
        if (listaProCabEnv == null) {
            listaProCabEnv = new ArrayList<>();
        }
        return listaProCabEnv;
    }

    public void setListaProCabEnv(List<ProcesoFilesBean> listaProCabEnv) {
        this.listaProCabEnv = listaProCabEnv;
    }

    public List<Object> getListaColumnasCab() {
        if (listaColumnasCab == null) {
            listaColumnasCab = new ArrayList<>();
        }
        return listaColumnasCab;
    }

    public void setListaColumnasCab(List<Object> listaColumnasCab) {
        this.listaColumnasCab = listaColumnasCab;
    }

    public Integer getValorExec() {
        return valorExec;
    }

    public void setValorExec(Integer valorExec) {
        this.valorExec = valorExec;
    }

    public HashMap<Integer, List<Object>> getListaFilesCab() {
        if (listaFilesCab == null) {
            listaFilesCab = new HashMap<>();
        }
        return listaFilesCab;
    }

    public void setListaFilesCab(HashMap<Integer, List<Object>> listaFilesCab) {
        this.listaFilesCab = listaFilesCab;
    }

    public List<CodigoBean> getListaTipoFile() {
        if (listaTipoFile == null) {
            listaTipoFile = new ArrayList<>();
        }
        return listaTipoFile;
    }

    public void setListaTipoFile(List<CodigoBean> listaTipoFile) {
        this.listaTipoFile = listaTipoFile;
    }

    public Integer getF1() {
        return f1;
    }

    public void setF1(Integer f1) {
        this.f1 = f1;
    }

    public Integer getF2() {
        return f2;
    }

    public void setF2(Integer f2) {
        this.f2 = f2;
    }

    public Integer getF3() {
        return f3;
    }

    public void setF3(Integer f3) {
        this.f3 = f3;
    }

    public List<ProcesoFilesBean> getListaProIntEnv() {
        if (listaProIntEnv == null) {
            listaProIntEnv = new ArrayList<>();
        }
        return listaProIntEnv;
    }

    public void setListaProIntEnv(List<ProcesoFilesBean> listaProIntEnv) {
        this.listaProIntEnv = listaProIntEnv;
    }

    public List<CodigoBean> getListaTipoArchivo() {
        if (listaTipoArchivo == null) {
            listaTipoArchivo = new ArrayList<>();
        }
        return listaTipoArchivo;
    }

    public void setListaTipoArchivo(List<CodigoBean> listaTipoArchivo) {
        this.listaTipoArchivo = listaTipoArchivo;
    }

    public Integer getInput() {
        return input;
    }

    public void setInput(Integer input) {
        this.input = input;
    }

    public List<String> getListaNoConstante() {
        if (listaNoConstante == null) {
            listaNoConstante = new ArrayList<>();
        }
        return listaNoConstante;
    }

    public void setListaNoConstante(List<String> listaNoConstante) {
        this.listaNoConstante = listaNoConstante;
    }

    public DataTable getDataTable() {
        if (dataTable == null) {
            dataTable = new DataTable();
        }
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public ProcesoErrorBean getProcesoErrorBean() {
        if (procesoErrorBean == null) {
            procesoErrorBean = new ProcesoErrorBean();
        }
        return procesoErrorBean;
    }

    public void setProcesoErrorBean(ProcesoErrorBean procesoErrorBean) {
        this.procesoErrorBean = procesoErrorBean;
    }

    public List<ProcesoErrorBean> getListaProcesoErrorBean() {
        if (listaProcesoErrorBean == null) {
            listaProcesoErrorBean = new ArrayList<>();
        }
        return listaProcesoErrorBean;
    }

    public void setListaProcesoErrorBean(List<ProcesoErrorBean> listaProcesoErrorBean) {
        this.listaProcesoErrorBean = listaProcesoErrorBean;
    }

    public List<CuentaBancoBean> getListaCuentaBanco() {
        if (listaCuentaBanco == null) {
            listaCuentaBanco = new ArrayList<>();
        }
        return listaCuentaBanco;
    }

    public void setListaCuentaBanco(List<CuentaBancoBean> listaCuentaBanco) {
        this.listaCuentaBanco = listaCuentaBanco;
    }

    public Date getFechaRecup() {
        return fechaRecup;
    }

    public void setFechaRecup(Date fechaRecup) {
        this.fechaRecup = fechaRecup;
    }

    public String getNomRecuperacion() {
        return nomRecuperacion;
    }

    public void setNomRecuperacion(String nomRecuperacion) {
        this.nomRecuperacion = nomRecuperacion;
    }

    public Map<String, Integer> getListaProcesos() {
        if (listaProcesos == null) {
            listaProcesos = new HashMap<>();
        }
        return listaProcesos;
    }

    public void setListaProcesos(Map<String, Integer> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public List<CodigoBean> getListaTipoEstatusCuenta() {
        return listaTipoEstatusCuenta;
    }

    public void setListaTipoEstatusCuenta(List<CodigoBean> listaTipoEstatusCuenta) {
        this.listaTipoEstatusCuenta = listaTipoEstatusCuenta;
    }

    public Integer getMesEnvio() {
        return mesEnvio;
    }

    public void setMesEnvio(Integer mesEnvio) {
        this.mesEnvio = mesEnvio;
    }

    public Boolean getProView() {
        return proView;
    }

    public void setProView(Boolean proView) {
        this.proView = proView;
    }

    public Boolean getProView2() {
        return proView2;
    }

    public void setProView2(Boolean proView2) {
        this.proView2 = proView2;
    }

    public CuentasPorCobrarBean getCuentaFiltroBean() {
        if (cuentaFiltroBean == null) {
            cuentaFiltroBean = new CuentasPorCobrarBean();
        }
        return cuentaFiltroBean;
    }

    public void setCuentaFiltroBean(CuentasPorCobrarBean cuentaFiltroBean) {
        this.cuentaFiltroBean = cuentaFiltroBean;
    }

    public List<Contenedor> getListaContenedorEnvio() {
        if (listaContenedorEnvio == null) {
            listaContenedorEnvio = new ArrayList<>();
        }
        return listaContenedorEnvio;
    }

    public void setListaContenedorEnvio(List<Contenedor> listaContenedorEnvio) {
        this.listaContenedorEnvio = listaContenedorEnvio;
    }

    public Boolean getDisableBotonDescarga() {
        return disableBotonDescarga;
    }

    public void setDisableBotonDescarga(Boolean disableBotonDescarga) {
        this.disableBotonDescarga = disableBotonDescarga;
    }

    public ProcesoBancoBean getProcesoBancoGen() {
        return procesoBancoGen;
    }

    public void setProcesoBancoGen(ProcesoBancoBean procesoBancoGen) {
        this.procesoBancoGen = procesoBancoGen;
    }

    public Boolean getDisableBotonGuardar() {
        return disableBotonGuardar;
    }

    public void setDisableBotonGuardar(Boolean disableBotonGuardar) {
        this.disableBotonGuardar = disableBotonGuardar;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public String getRucEntidad() {
        return rucEntidad;
    }

    public void setRucEntidad(String rucEntidad) {
        this.rucEntidad = rucEntidad;
    }

    public Boolean getDisabledExporter() {
        return disabledExporter;
    }

    public void setDisabledExporter(Boolean disabledExporter) {
        this.disabledExporter = disabledExporter;
    }

    public List<Contenedor> getListaProEnvios() {
        if (listaProEnvios == null) {
            listaProEnvios = new ArrayList<>();
        }
        return listaProEnvios;
    }

    public void setListaProEnvios(List<Contenedor> listaProEnvios) {
        this.listaProEnvios = listaProEnvios;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public CronogramaPagoBean getCronogramaPagoBean() {
        if (cronogramaPagoBean == null) {
            cronogramaPagoBean = new CronogramaPagoBean();
        }
        return cronogramaPagoBean;
    }

    public void setCronogramaPagoBean(CronogramaPagoBean cronogramaPagoBean) {
        this.cronogramaPagoBean = cronogramaPagoBean;
    }

    public List<CronogramaPagoBean> getListaCronogramaPagoBean() {
        if (listaCronogramaPagoBean == null) {
            listaCronogramaPagoBean = new ArrayList<>();
        }
        return listaCronogramaPagoBean;
    }

    public void setListaCronogramaPagoBean(List<CronogramaPagoBean> listaCronogramaPagoBean) {
        this.listaCronogramaPagoBean = listaCronogramaPagoBean;
    }

    public List<Contenedor> getListaContenedorEnvioCabecera() {
        if (listaContenedorEnvioCabecera == null) {
            listaContenedorEnvioCabecera = new ArrayList<>();
        }
        return listaContenedorEnvioCabecera;
    }

    public void setListaContenedorEnvioCabecera(List<Contenedor> listaContenedorEnvioCabecera) {
        this.listaContenedorEnvioCabecera = listaContenedorEnvioCabecera;
    }

    public List<Contenedor> getListaContenedorEnvioIntermedio() {
        if (listaContenedorEnvioIntermedio == null) {
            listaContenedorEnvioIntermedio = new ArrayList<>();
        }
        return listaContenedorEnvioIntermedio;
    }

    public void setListaContenedorEnvioIntermedio(List<Contenedor> listaContenedorEnvioIntermedio) {
        this.listaContenedorEnvioIntermedio = listaContenedorEnvioIntermedio;
    }

    public Integer getFilasCab() {
        return filasCab;
    }

    public void setFilasCab(Integer filasCab) {
        this.filasCab = filasCab;
    }

    public Integer getFilasInt() {
        return filasInt;
    }

    public void setFilasInt(Integer filasInt) {
        this.filasInt = filasInt;
    }

    public Integer getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(Integer tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Integer getSelEnvio() {
        return selEnvio;
    }

    public void setSelEnvio(Integer selEnvio) {
        this.selEnvio = selEnvio;
    }

    public Boolean getVistaFile() {
        return vistaFile;
    }

    public void setVistaFile(Boolean vistaFile) {
        this.vistaFile = vistaFile;
    }

    public List<ProcesoFalloBean> getListaProcesoFalloBean() {
        if (listaProcesoFalloBean == null) {
            listaProcesoFalloBean = new ArrayList<>();
        }
        return listaProcesoFalloBean;
    }

    public void setListaProcesoFalloBean(List<ProcesoFalloBean> listaProcesoFalloBean) {
        this.listaProcesoFalloBean = listaProcesoFalloBean;
    }

    public ProcesoFalloBean getProcesoFalloBean() {
        if (procesoFalloBean == null) {
            procesoFalloBean = new ProcesoFalloBean();
        }
        return procesoFalloBean;
    }

    public void setProcesoFalloBean(ProcesoFalloBean procesoFalloBean) {
        this.procesoFalloBean = procesoFalloBean;
    }

    public Integer getFlgEstado() {
        return flgEstado;
    }

    public void setFlgEstado(Integer flgEstado) {
        this.flgEstado = flgEstado;
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

    public List<Integer> getListaMesSel() {
        if (listaMesSel == null) {
            listaMesSel = new ArrayList<>();
        }
        return listaMesSel;
    }

    public void setListaMesSel(List<Integer> listaMesSel) {
        this.listaMesSel = listaMesSel;
    }

    public boolean isFlgUniNeg() {
        return flgUniNeg;
    }

    public void setFlgUniNeg(boolean flgUniNeg) {
        this.flgUniNeg = flgUniNeg;
    }

}
