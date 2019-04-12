package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class EnvioMB extends BaseMB implements Serializable {

    @PostConstruct
    public void EnvioMB() {
        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cargarDatos();
            selFil = 0;
            selFiltro();

            fecha = new GregorianCalendar();

            /* OBTENER NOMBRE DE ARCHIVO */
            GregorianCalendar fechaActual = new GregorianCalendar();
            nombreFile = obtenerNombreArchivo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fechaActual.getTime());

            /* OBTENIENDO LEYENDA DE ARCHIVO */
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesCabeceraBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), 2, MaristaConstantes.FileCabecera);
            listaProcesoFilesIntermedioBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), 2, MaristaConstantes.FileIntermedio);
            listaProcesoFilesDetalleBean = procesoFilesService.obtenerFileProceso(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), 2, MaristaConstantes.FileDetalle);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES
    private ProcesoEnvioBean procesoEnvioBean;
    private UsuarioBean usuarioSessionBean;
    private ProcesoBancoBean procesoBancoBean;

    //LISTAS
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoBean> listaConceptoBean;
    private List<MesBean> listaMesAll;
    private List<CodigoBean> listaTipoStatusCtaCte;
    private List<Contenedor> listaProEnvios;
    private List<Contenedor> listaProEnviosCab;
    private List<Contenedor> listaProEnviosInt;
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private Map<String, Integer> listaProcesos;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<ProcesoFilesBean> listaProcesoFilesCabeceraBean;
    private List<ProcesoFilesBean> listaProcesoFilesIntermedioBean;
    private List<ProcesoFilesBean> listaProcesoFilesDetalleBean;
    private List<CuentasPorCobrarBean> listaCuentaEnvio;

    //VARIABLES DE AYUDA
    private Integer selFil;
    private Boolean flgConFec;
    private Boolean flgConAnM;
    private Integer filasCab;
    private Integer filasInt;
    private Integer filas;
    private StreamedContent content;
    private String nombreFile;
    private Boolean disabledExporter = false;
    private String tipoRegistro;
    private Integer idProcesoBanco;
    private Integer finalizado; // 1 => SI || 0 => NO
    private Boolean flgConfirm;
    private Calendar fecha;

    public void obtenerPorTipo() {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            getListaConceptoBean();
            listaConceptoBean = conceptoService.obtenerPorTipo(getProcesoEnvioBean().getConceptoBean().getTipoConceptoBean());
            getProcesoEnvioBean().getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(getProcesoEnvioBean().getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatos() {
        try {
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoEnvioBean();
            getProcesoEnvioBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setFechaIni(fechaActual.getTime());
            getProcesoEnvioBean().setFechaFin(fechaActual.getTime());
            getProcesoEnvioBean().setCreaPor(usuarioSessionBean.getUsuario());
            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_INTERBANK);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_SCOTIABANK);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_BCP);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_BCP);
            } else if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_BARINA)) {
                getProcesoEnvioBean().setRuc(MaristaConstantes.RUC_INTERBANK);
            }
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;
            getProcesoEnvioBean().setIdProcesoBanco(idProcesoBanco);
            getProcesoEnvioBean().setTipoMoneda(0);
            getProcesoEnvioBean().setTipoRegistro("A");

            //TIPOS DE CONCEPTO
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            getListaTipoConceptoBean();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();
            CodigoService codigoService = BeanFactory.getCodigoService();

            //ESTADO DE CUENTA CORRIENTE
            getListaTipoStatusCtaCte();
            listaTipoStatusCtaCte = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE));

            obtenerListaMeses();

            //OBTENIENDO PROCESO DE BANCO
            getProcesoBancoBean();
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoBancoBean().setFlgProceso(1);
            getProcesoBancoBean().setFecha(fechaActual.getTime());
            getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            ponerProceso();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void selFiltro() {
        try {
            if (selFil.equals(0)) {
                flgConFec = true;
                flgConAnM = false;
                procesoEnvioBean.setMeses(0);
                procesoEnvioBean.setAnios(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
                cargarDatos();
            } else if (selFil.equals(1)) {
                flgConAnM = true;
                flgConFec = false;
                procesoEnvioBean.setFechaIni(null);
                procesoEnvioBean.setFechaFin(null);
                procesoEnvioBean.setAnios(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerListaMeses() {
        try {
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
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarEnvio() {
        try {
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            String date = fechaHora.format(fecha.getTime());
            Integer res = 0;
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
//            Object object = new Object();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg()) + 1;

            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");

            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                String fecha1 = fecha.format(procesoEnvioBean.getFechaIni());
                String fecha2 = fecha.format(procesoEnvioBean.getFechaFin());
                System.out.println("fecha1..." + fecha1);
                System.out.println("fecha2..." + fecha2);
                if (fecha1.equals(fecha2)) {
                    nombreFile = obtenerNombreArchivo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoEnvioBean.getFechaIni());
                } else {
                    GregorianCalendar fechaActual = new GregorianCalendar();
                    nombreFile = obtenerNombreArchivo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fechaActual.getTime());
                }
                System.out.println("nombre " + nombreFile);

            }
            if (selFil.equals(0)) {
                procesoEnvioBean.setValor(selFil); /* POR FECHAS */

                if (procesoEnvioBean.getFechaIni() != null) {
                    procesoEnvioBean.setFechaIni(procesoEnvioBean.getFechaIni());
                    res = 1;
                }
                if (procesoEnvioBean.getFechaFin() != null) {
                    procesoEnvioBean.setFechaFin(procesoEnvioBean.getFechaFin());
                    res = 1;
                }
                if (procesoEnvioBean.getCodigo() != null && !procesoEnvioBean.getCodigo().equals("")) {
                    procesoEnvioBean.setCodigo(procesoEnvioBean.getCodigo());
                    res = 1;
                }
                if (procesoEnvioBean.getIdEstudiante() != null && !procesoEnvioBean.getIdEstudiante().equals("")) {
                    procesoEnvioBean.setIdEstudiante(procesoEnvioBean.getIdEstudiante());
                    res = 1;
                }
                if (procesoEnvioBean.getNombres() != null && !procesoEnvioBean.getNombres().equals("")) {
                    procesoEnvioBean.setNombres(procesoEnvioBean.getNombres());
                    res = 1;
                }
                if (res == 1) {
                    if (procesoEnvioBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                        if (procesoEnvioBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto().equals(MaristaConstantes.TIP_CONCEPTO_TALLER)) {
                            listaProEnvios = procesoEnvioService.obtenerEnvioUniNegBancoTaller(procesoEnvioBean);
                        } else {
                            procesoEnvioBean.setFecha(date.replace("-", ""));
                            listaProEnviosCab = procesoEnvioService.obtenerEnvioUniNegCab(procesoEnvioBean);
                            listaProEnvios = procesoEnvioService.obtenerEnvioUniNeg(procesoEnvioBean);
                        }
                    } else {
                        procesoEnvioBean.setFecha(date.replace("-", ""));
                        listaProEnvios = procesoEnvioService.obtenerEnvioUniNeg(procesoEnvioBean);
                        listaProEnviosCab = procesoEnvioService.obtenerEnvioUniNegCab(procesoEnvioBean);
                    }
                    if (listaProEnvios.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaProEnvios = new ArrayList<>();
                    } else if (!listaProEnvios.isEmpty()) {
                        setDisabledExporter(true);
                        setFlgConfirm(false);
                        ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                        filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoEnvioBean().getRuc());
                        filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoEnvioBean().getRuc());
                        filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoEnvioBean().getRuc());
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaProEnvios = new ArrayList<>();
                }
            } else if (selFil.equals(1)) {
                procesoEnvioBean.setValor(selFil); /* POR AÑO Y MES */

                if (procesoEnvioBean.getTipoEstadoCta().getIdCodigo() != null) {
                    procesoEnvioBean.getTipoEstadoCta().setIdCodigo(procesoEnvioBean.getTipoEstadoCta().getIdCodigo());
                    res = 1;
                }
                if (procesoEnvioBean.getCodigo() != null && !procesoEnvioBean.getCodigo().equals("")) {
                    procesoEnvioBean.setCodigo(procesoEnvioBean.getCodigo());
                    res = 1;
                }
                if (procesoEnvioBean.getIdEstudiante() != null && !procesoEnvioBean.getIdEstudiante().equals("")) {
                    procesoEnvioBean.setIdEstudiante(procesoEnvioBean.getIdEstudiante());
                    res = 1;
                }
                if (procesoEnvioBean.getNombres() != null && !procesoEnvioBean.getNombres().equals("")) {
                    procesoEnvioBean.setNombres(procesoEnvioBean.getNombres());
                    res = 1;
                }
                if (procesoEnvioBean.getMeses() != null) {
                    procesoEnvioBean.setMeses(procesoEnvioBean.getMeses());
                    res = 1;
                }
                if (procesoEnvioBean.getAnios() != null) {
                    procesoEnvioBean.setAnios(procesoEnvioBean.getAnios());
                    res = 1;
                }
                if (res == 1) {
                    procesoEnvioBean.setFecha(date.replace("-", ""));
                    listaProEnvios = procesoEnvioService.obtenerEnvioUniNeg(procesoEnvioBean);
                    //listaProEnviosCab = procesoEnvioService.obtenerEnvioUniNegCab(procesoEnvioBean);
                    if (listaProEnvios.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaProEnvios = new ArrayList<>();
                    } else if (!listaProEnvios.isEmpty()) {
                        setDisabledExporter(true);
                        ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                        filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoEnvioBean().getRuc());
                        filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoEnvioBean().getRuc());
                        filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoEnvioBean().getRuc());
                    }
                } else if (res == 0) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaProEnvios = new ArrayList<>();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEnvio() {
        try {
            procesoEnvioBean = new ProcesoEnvioBean();
            listaProEnvios = new ArrayList<>();
            listaProEnviosCab = new ArrayList<>();
            listaProEnviosInt = new ArrayList<>();
            cargarDatos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarProcesoBanco() {
        try {
            Integer totalRegistros = 0, fileDetalle = 0;
            Float montoRecuperado;
            fileDetalle = MaristaConstantes.FileDetalle;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            montoRecuperado = procesoEnvioService.obtenerMontoTotal(idProcesoBanco, fileDetalle, 2, procesoEnvioBean.getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            totalRegistros = procesoEnvioService.obtenerTotalRegistros(idProcesoBanco, procesoEnvioBean.getRuc(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean = procesoBancoService.obtenerProcesoBancoId(idProcesoBanco, usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (procesoBancoBean == null) {
                procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                procesoBancoBean.setFecha(fechaActual.getTime());
                nombreFile = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                procesoBancoBean.setNombre(nombreFile);
                procesoBancoBean.setTipoArchivo(tipoRegistro);
                procesoBancoBean.setFlgProceso(1);
                procesoBancoBean.setRegEnv(totalRegistros);
                procesoBancoBean.setMontoEnv(montoRecuperado);
                procesoBancoBean.setCreaPor(usuarioSessionBean.getUsuario());
                procesoBancoService.insertarProcesoBanco(procesoBancoBean);
            } else if (procesoBancoBean != null) {
                nombreFile = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                procesoBancoBean.setNombre(nombreFile);
                procesoBancoBean.setTipoArchivo(tipoRegistro);
                procesoBancoBean.setRegEnv(totalRegistros);
                procesoBancoBean.setMontoEnv(montoRecuperado);
                procesoBancoBean.setModiPor(usuarioSessionBean.getUsuario());
                procesoBancoService.actualizarProcesoBanco(procesoBancoBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

            listaContenedorDeta = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3);
            listaContenedorInte = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileIntermedio, 2);
            listaContenedorCabe = procesoFinalService.obtenerEnvioBancos(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), getProcesoEnvioBean().getIdProcesoBanco(), MaristaConstantes.FileCabecera, 1);
            if (!listaContenedorDeta.isEmpty()) {
                if (!listaContenedorCabe.isEmpty()) {
                    System.out.println(">>> ENTRO CAB");
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
                    System.out.println(">>> ENTRO INT");
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
                    System.out.println(">>> ENTRO DET");
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
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", nombreFile + ".txt");
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

    public String obtenerNombreArchivo(String uniNeg, Date fecha) {
        String nombre = "";
        try {
            String fechaProceso = "";
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBancoBean.setFecha(fecha);
            procesoBancoBean.setFlgProceso(1);
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = "COB".concat(fechaProceso).concat("");
                    ;
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = fechaProceso;
                    break;
                case MaristaConstantes.UNI_NEG_SANJOH:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = "CREP".concat(fechaProceso).concat("");
                    break;
                case MaristaConstantes.UNI_NEG_SANLUI:
                    fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                    nombre = "CREP".concat(fechaProceso).concat("");
                    break;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return nombre;
    }

    public void crearXls(Object document) {
        try {
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoBancoBean.setFlgProceso(1);
            procesoBancoBean.setIdProcesoBanco(procesoEnvioBean.getIdProcesoBanco());
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            Integer reg = procesoBancoService.obtenerNumeroRegistros(procesoBancoBean);
            System.out.println(">>>>" + reg);
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            ProcesoFilesBean procesoFilesBean = new ProcesoFilesBean();
            procesoFilesBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoFilesBean.setFlgProceso(2);
            HSSFWorkbook wb = (HSSFWorkbook) document;
            HSSFSheet sheet = wb.getSheetAt(0);
            CreationHelper createHelper = wb.getCreationHelper();
            if (reg != null && !reg.equals(0)) {
                for (int j = 0; j <= reg; j++) {
                    HSSFRow header = sheet.getRow(j);
                    for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
                        System.out.println(">>>>" + header.getPhysicalNumberOfCells() + " // " + i);
                        if (j == 0) {
                            HSSFCell cell = null;
                            System.out.println("CABECERA >>>>" + i);
                            //OBTENIENDO CABECERA POR UNINEG
                            if (usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                                switch (i) {
                                    case 0:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("TIPO DE ARCHIVO");
                                        break;
                                    case 1:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CODIGO DE DEUDOR");
                                        break;
                                    case 2:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CODIGO DE CUOTA");
                                        break;
                                    case 3:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("NOMBRE DE DEUDOR");
                                        break;
                                    case 4:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("REFERENCIA 1");
                                        break;
                                    case 5:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("REFERENCIA 2");
                                        break;
                                    case 6:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("EMISIÓN");
                                        break;
                                    case 7:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("VENCIMIENTO");
                                        break;
                                    case 8:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("DOCUMENTO");
                                        break;
                                    case 9:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("MONEDA");
                                        break;
                                    case 10:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 1");
                                        break;
                                    case 11:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 2");
                                        break;
                                    case 12:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 3");
                                        break;
                                    case 13:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 4");
                                        break;
                                    case 14:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 5");
                                        break;
                                    case 15:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 6");
                                        break;
                                    case 16:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("CONCEPTO 7");
                                        break;
                                    case 17:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("TIPO DE REGISTRO");
                                        break;
                                    default:
                                        System.out.println(">>>> celda" + i);
                                        cell = header.getCell(i);
                                        cell.setCellValue("Encabezado #" + i);
                                }
                            }
                        } else {
                            procesoFilesBean.setPosicionItem(i + 1);
                            Integer tipoDato = procesoFilesService.obtenerPosTipoFile(procesoFilesBean);
                            System.out.println(">>>>" + tipoDato);
                            if (tipoDato.equals(MaristaConstantes.TIPO_FILE_FECHA)) {
                                HSSFCell cell = header.getCell(i);
                                CellStyle cellStyle = wb.createCellStyle();
                                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                                cell.setCellStyle(cellStyle);
                                System.out.println(">>>>>>" + header.getCell(i));
                            } else if (tipoDato.equals(MaristaConstantes.TIPO_FILE_HORA)) {
                                HSSFCell cell = header.getCell(i);
                                CellStyle cellStyle = wb.createCellStyle();
                                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm:ss"));
                                cell.setCellStyle(cellStyle);
                                System.out.println(">>>>>>" + header.getCell(i));
                            } else if (tipoDato.equals(MaristaConstantes.TIPO_FILE_MONEDA)) {
                                HSSFCell cell = header.getCell(i);
                                CellStyle cellStyle = wb.createCellStyle();
                                if (cell.toString() != null && !cell.toString().equals("")) {
                                    cell.setCellValue(Double.parseDouble(header.getCell(i).toString()));
                                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#.#"));
                                    cell.setCellStyle(cellStyle);
                                } else {
                                    cell.setCellValue(Double.parseDouble("0.0"));
                                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.0"));
                                    cell.setCellStyle(cellStyle);
                                }
                                System.out.println(">>>>>>" + header.getCell(i));
                            }
                        }
                    }
                }
            } else {
                System.out.println(">>>> nada");
            }
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

    public void obtenerFiltroProceso() {
        try {
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (procesoBancoBean.getFlgProceso() != null) {
                procesoBancoBean.setFlgProceso(procesoBancoBean.getFlgProceso());
                res = 1;
            }
            if (procesoBancoBean.getNombre() != null) {
                procesoBancoBean.setNombre(procesoBancoBean.getNombre());
                res = 1;
            }
            if (procesoBancoBean.getEntidadBean().getRuc() != null) {
                procesoBancoBean.getEntidadBean().setRuc(procesoBancoBean.getEntidadBean().getRuc());
                res = 1;
            }
            if (procesoBancoBean.getAnio() != null) {
                procesoBancoBean.setAnio(procesoBancoBean.getAnio());
                res = 1;
            }
            if (procesoBancoBean.getMes() != null) {
                procesoBancoBean.setMes(procesoBancoBean.getMes());
                res = 1;
            }
            if (procesoBancoBean.getDia() != null) {
                procesoBancoBean.setDia(procesoBancoBean.getDia());
                res = 1;
            }
            if (procesoBancoBean.getFechaInicio() != null) {
                procesoBancoBean.setFechaInicio(procesoBancoBean.getFechaInicio());
                res = 1;
            }
            if (procesoBancoBean.getFechaFin() != null) {
                procesoBancoBean.setFechaFin(procesoBancoBean.getFechaFin());
                res = 1;
            }
            if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProcesoBancoBean = new ArrayList<>();
            } else if (res == 1) {
                listaProcesoBancoBean = procesoBancoService.filtrarProceso(procesoBancoBean);
                if (listaProcesoBancoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProcesoBancoBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroBanco() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
            listaProcesoBancoBean = new ArrayList<>();
            listaProEnvios = new ArrayList<>();
            listaProEnviosCab = new ArrayList<>();
            listaProEnviosInt = new ArrayList<>();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoEnvioPorId(Object object, Integer dato) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            idProcesoBanco = procesoBancoBean.getIdProcesoBanco();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
            getProcesoEnvioBean().setIdProcesoBanco(procesoBancoBean.getIdProcesoBanco());
            nombreFile = obtenerNombreArchivo(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getFecha());
            System.out.println(">>>>" + procesoBancoBean.getIdProcesoBanco());
            if (dato.equals(1)) {
                obtenerFileEnvio(procesoBancoBean);
            } else if (dato.equals(2)) {
                obtenerCuentaPorEnvio(procesoBancoBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFileEnvio(ProcesoBancoBean procesoBancoBean) {
        try {
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
            if (listaProEnvios.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaProEnvios = new ArrayList<>();
            } else if (!listaProEnvios.isEmpty()) {
                setDisabledExporter(true);
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                filas = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileDetalle, getProcesoEnvioBean().getRuc());
                filasCab = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileCabecera, getProcesoEnvioBean().getRuc());
                filasInt = procesoFilesService.obtenerNumFiles(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2, MaristaConstantes.FileIntermedio, getProcesoEnvioBean().getRuc());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentaPorEnvio(ProcesoBancoBean procesoBancoBean) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println(">>>" + procesoBancoBean.getIdProcesoBanco());
            cuentasPorCobrarBean.getProcesoEnvioBean().setIdProcesoEnvio(procesoBancoBean.getIdProcesoBanco());
            listaCuentasPorCobrarBean = cuentasPorCobrarService.obtenerCuentaEnvio(cuentasPorCobrarBean);
        } catch (Exception err) {
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
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                procesoBancoService.eliminarProcesoBancoMas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                obtenerFiltroProceso();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void actualizarFiltroMasivo() {
        try {
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            if (flgConfirm) {
                ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
                procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                procesoBancoBean.setIdProcesoBanco(getProcesoEnvioBean().getIdProcesoBanco());
                procesoBancoBean.setFlgEnvio(true);
                procesoBancoService.actualizarFiltroMasivo(procesoBancoBean);
                listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
            } else if (!flgConfirm) {
                ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
                procesoBancoBean.getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                procesoBancoBean.setIdProcesoBanco(getProcesoEnvioBean().getIdProcesoBanco());
                procesoBancoBean.setFlgEnvio(false);
                procesoBancoService.actualizarFiltroMasivo(procesoBancoBean);
                listaProEnvios = procesoFinalService.execProListaBanco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getProcesoEnvioBean().getRuc(), procesoBancoBean.getIdProcesoBanco(), MaristaConstantes.FileDetalle, 3, 2);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //GET Y SET
    public ProcesoEnvioBean getProcesoEnvioBean() {
        if (procesoEnvioBean == null) {
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
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

    public List<ConceptoBean> getListaConceptoBean() {
        if (listaConceptoBean == null) {
            listaConceptoBean = new ArrayList<>();
        }
        return listaConceptoBean;
    }

    public void setListaConceptoBean(List<ConceptoBean> listaConceptoBean) {
        this.listaConceptoBean = listaConceptoBean;
    }

    public UsuarioBean getUsuarioSessionBean() {
        if (usuarioSessionBean == null) {
            usuarioSessionBean = new UsuarioBean();
        }
        return usuarioSessionBean;
    }

    public void setUsuarioSessionBean(UsuarioBean usuarioSessionBean) {
        this.usuarioSessionBean = usuarioSessionBean;
    }

    public Integer getSelFil() {
        return selFil;
    }

    public void setSelFil(Integer selFil) {
        this.selFil = selFil;
    }

    public Boolean getFlgConFec() {
        return flgConFec;
    }

    public void setFlgConFec(Boolean flgConFec) {
        this.flgConFec = flgConFec;
    }

    public Boolean getFlgConAnM() {
        return flgConAnM;
    }

    public void setFlgConAnM(Boolean flgConAnM) {
        this.flgConAnM = flgConAnM;
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

    public List<CodigoBean> getListaTipoStatusCtaCte() {
        if (listaTipoStatusCtaCte == null) {
            listaTipoStatusCtaCte = new ArrayList<>();
        }
        return listaTipoStatusCtaCte;
    }

    public void setListaTipoStatusCtaCte(List<CodigoBean> listaTipoStatusCtaCte) {
        this.listaTipoStatusCtaCte = listaTipoStatusCtaCte;
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

    public List<Contenedor> getListaProEnviosCab() {
        if (listaProEnviosCab == null) {
            listaProEnviosCab = new ArrayList<>();
        }
        return listaProEnviosCab;
    }

    public void setListaProEnviosCab(List<Contenedor> listaProEnviosCab) {
        this.listaProEnviosCab = listaProEnviosCab;
    }

    public List<Contenedor> getListaProEnviosInt() {
        if (listaProEnviosInt == null) {
            listaProEnviosInt = new ArrayList<>();
        }
        return listaProEnviosInt;
    }

    public void setListaProEnviosInt(List<Contenedor> listaProEnviosInt) {
        this.listaProEnviosInt = listaProEnviosInt;
    }

    public Integer getFilasCab() {
        return filasCab;
    }

    public void setFilasCab(Integer filasCab) {
        this.filasCab = filasCab;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getFilasInt() {
        return filasInt;
    }

    public void setFilasInt(Integer filasInt) {
        this.filasInt = filasInt;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public String getNombreFile() {
        return nombreFile;
    }

    public void setNombreFile(String nombreFile) {
        this.nombreFile = nombreFile;
    }

    public Boolean getDisabledExporter() {
        return disabledExporter;
    }

    public void setDisabledExporter(Boolean disabledExporter) {
        this.disabledExporter = disabledExporter;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public Integer getFinalizado() {
        return finalizado;
    }

    public void setFinalizado(Integer finalizado) {
        this.finalizado = finalizado;
    }

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

    public Map<String, Integer> getListaProcesos() {
        if (listaProcesos == null) {
            listaProcesos = new HashMap<>();
        }
        return listaProcesos;
    }

    public void setListaProcesos(Map<String, Integer> listaProcesos) {
        this.listaProcesos = listaProcesos;
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

    public List<ProcesoFilesBean> getListaProcesoFilesCabeceraBean() {
        if (listaProcesoFilesCabeceraBean == null) {
            listaProcesoFilesCabeceraBean = new ArrayList<>();
        }
        return listaProcesoFilesCabeceraBean;
    }

    public void setListaProcesoFilesCabeceraBean(List<ProcesoFilesBean> listaProcesoFilesCabeceraBean) {
        this.listaProcesoFilesCabeceraBean = listaProcesoFilesCabeceraBean;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesIntermedioBean() {
        if (listaProcesoFilesIntermedioBean == null) {
            listaProcesoFilesIntermedioBean = new ArrayList<>();
        }
        return listaProcesoFilesIntermedioBean;
    }

    public void setListaProcesoFilesIntermedioBean(List<ProcesoFilesBean> listaProcesoFilesIntermedioBean) {
        this.listaProcesoFilesIntermedioBean = listaProcesoFilesIntermedioBean;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesDetalleBean() {
        if (listaProcesoFilesDetalleBean == null) {
            listaProcesoFilesDetalleBean = new ArrayList<>();
        }
        return listaProcesoFilesDetalleBean;
    }

    public void setListaProcesoFilesDetalleBean(List<ProcesoFilesBean> listaProcesoFilesDetalleBean) {
        this.listaProcesoFilesDetalleBean = listaProcesoFilesDetalleBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentaEnvio() {
        if (listaCuentaEnvio == null) {
            listaCuentaEnvio = new ArrayList<>();
        }
        return listaCuentaEnvio;
    }

    public void setListaCuentaEnvio(List<CuentasPorCobrarBean> listaCuentaEnvio) {
        this.listaCuentaEnvio = listaCuentaEnvio;
    }

    public Boolean getFlgConfirm() {
        return flgConfirm;
    }

    public void setFlgConfirm(Boolean flgConfirm) {
        this.flgConfirm = flgConfirm;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

}
