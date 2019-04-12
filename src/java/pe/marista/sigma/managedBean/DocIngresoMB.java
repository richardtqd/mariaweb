/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DescuentoTallerBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DetProgramacionDsctoBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.DocIngresoSerieBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.ProgramacionDsctoBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.TipoDiscenteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.bean.reporte.ProcesoBancoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DocIngresoSerieService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.PerfilService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.ProgramacionDsctoService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class DocIngresoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocIngresoMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DocIngresoMB docIngreso = (DocIngresoMB) new MaristaUtils().sesionObtenerObjeto("docIngresoRendMB");
            if (docIngreso != null) {
                setDocIngresoBean(docIngreso.getDocIngresoBean());
                setListDetDocIngreso(docIngreso.getListDetDocIngreso());
                new MaristaUtils().sesionColocarObjeto("docIngresoRendMB", null);
            }
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = new ArrayList<>();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoIngreso();

            DocIngresoSerieService docIngresoSerieService = BeanFactory.getDocIngresoSerieService();
            docIngresoSerieBean = docIngresoSerieService.obtenerActual();
            numActual = String.format("%07d", docIngresoSerieBean.getActual());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaLugarPago = new ArrayList<>();
            mapLugarPago = new LinkedHashMap<>();
            listaLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            for (CodigoBean codigo : listaLugarPago) {
                mapLugarPago.put(codigo.getCodigo(), codigo.getIdCodigo());
            }
            listaModoPago = new ArrayList<>();
            listaModoPago = codigoService.obtenerCodigoDocIngreso();
            listaMoneda = new ArrayList<>();
            listaMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            getPersonaFiltroBean();
            getPersonaBean();
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                getPersonaBean().setFoto("/resources/images/noFoto.bmp");
            } else {
                getPersonaBean().setFoto("/resources/images/noFoto.jpg");
            }
//            System.out.println("foto..." + getPersonaBean().getFoto());
            Calendar miCalendario = Calendar.getInstance();
            anio = miCalendario.get(Calendar.YEAR);
            this.setFlgPagoParDol(false);
            setTipIndividuo(1);
            getListaProgramacionDsctoBean();
//            generarCodigoPersona();
            System.out.println("fin cosntructor docIngresoMB!");
//            setFlgGenCod(Boolean.FALSE);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    private PersonaBean personaFiltroBean;
    private List<PersonaBean> listaPersonaBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private PersonaBean personaBean;
    private PersonalBean personalBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private TipoDiscenteBean tipoDiscenteBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<DetDocIngresoBean> listDetDocIngreso;
    private List<ConceptoUniNegBean> listaConceptos;
    private DocIngresoSerieBean docIngresoSerieBean;
    private String numActual;
    private List<CodigoBean> listaTipoDocumento;
    private Map<String, Integer> mapTipoDocumento;
    private List<ImpresoraBean> listaImpresora;
    private DocIngresoBean docIngresoBean;
    private DetDocIngresoBean detDocIngresoBean;
    private ImpresoraCajaBean impresoraCajaBean;
    private List<FamiliarEstudianteBean> listaFamiliar;
    private Map<String, String> mapFamiliar;
    private String idPersona;
    private List<CodigoBean> listaLugarPago;
    private Map<String, Integer> mapLugarPago;
    private List<CodigoBean> listaModoPago;
    private List<CodigoBean> listaMoneda;
    private TipoCambioBean tipoCambio;
    private String montoTotal;
    private Double montoTotalDouble;
    private String montoParcialDol; // obtener el tipo de cambio de un monto parcial en dolares
    private Double montoParcialDolDouble;// obtener el tipo de cambio de un monto parcial en dolares
    private Double montoTotalDolDouble = new Double("0.00");//monto Efectivo dolares
    private Double montoTotalSolDouble = new Double("0.00");     //monto Efectivo soles
    private Double montoTotalPos1Double = new Double("0.00");    //monto Efectivo soles
    private Double montoTotalPos2Double = new Double("0.00");    //monto Efectivo soles
    private CajaGenBean cajaGenBean;
    private List<CodigoBean> listaTipoStatusCtaCte;
    private Integer cr;

    private List<ProgramacionBean> listaProgramacionBean;
    private List<ProgramacionBean> listaProgramacionSessionBean;

    //ayuda
    private Boolean flgModPagoAmbos;
    private Boolean flgModPOS;
    private Boolean flgPagoParDol;
//    private Boolean flgIngCongregacion;
    private Boolean flgFactura;
    private Boolean flgPension = false;
    private Boolean flgSerGen = false;
    private Boolean flgAnulado = false;

    //ayuda pos
    private Boolean posVisa = false;
    private Boolean posMC = false;
    private Boolean flgSistemas = false;
    //congregacion
    private Double montoCongregacionSolDouble = new Double("0.00");    //monto Efectivo soles
    private Double montoCongregacionDolDouble = new Double("0.00");    //monto Efectivo dálares 
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
    private UsuarioBean usuarioLoginBean;
    private Integer anio;
    private ProgramacionBean programacionBean;
    private ConceptoUniNegBean conceptoUniNegBean;

    private DocIngresoBean docIngresoFiltroBean;
    private List<DocIngresoBean> listDocIngresoBean;
    private List<CodigoBean> listaTipoEstadpDocIng;
    private List<CodigoBean> listaTipoLugarPago;
    private Calendar fechaActual;
    private CodigoBean tipoMoneda;

    //reportes
    private Double totPOS1 = (0.0);
    private Double totPOS2 = (0.0);
    private Double totSoles = (0.0);
    private Double tolDolares = (0.0);
    private Double tolDolaresConvSoles = (0.0);
    private Double totalSol = (0.0);
    private Double totalDol = (0.0);
    //Personal
    private Integer tipIndividuo = 1;
    private Boolean flgSoloEst = true;
    private List<PersonalBean> listaPersonalFiltroBean;
    private PersonalBean personalFiltroBean;

    private DocEgresoBean docEgresoBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaCHBean;

    private List<PerfilBean> listaPerfilBean;
    private List<Integer> listaIdsDocIngreso;
    private Boolean valAdmTodos;

    private DetDocIngresoRepBean detDocIngresoRepFiltroBean;
    private DetDocIngresoRepBean detDocIngresoRepBean;
    private List<DetDocIngresoRepBean> listaDetDocIngresoRepBean;
    private List<PlanContableBean> listaPlanContable;

    private TreeNode root;
    private TreeNode selectedNode;
    private Boolean serGenCollapsed;
    private Integer addMes;

    private List<ProgramacionDsctoBean> listaProgramacionDsctoBean;
    private BigDecimal dscto = new BigDecimal(0);

    //AYUDA FLG
    private Boolean flgDisableGenCod = Boolean.FALSE;
    private Boolean flgGenCod = Boolean.TRUE;

    private Integer selFil;
    private Boolean flgConFec;
    private Boolean flgConAnM;

    private List<MesBean> listaMesAll;
    private Boolean disableCuenta;

    private Integer estadoImpresion;
    private Integer formatoRecibo;
    private Boolean flgImpresionMasiva;
    private Boolean flgCajExt = Boolean.FALSE;

    private Boolean flgBtnImprimir;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private List<CajaGenBean> listaCajaGenBean;
    private List<CodigoBean> listaTipoStatusDocIng;
    private List<CodigoBean> listaTipoDscto;

//    private ProgramacionDsctoBean programacionDsctoBean;
    private Double montoDscPro;
    private String cajaGen = null;
    private Integer idCajaGenVista;

    public void generarCodigoPersona() {
        try {
            System.out.println("flg ini " + this.flgGenCod);
            if (this.flgGenCod.equals(Boolean.TRUE)) {
                this.flgGenCod = Boolean.TRUE;
//                System.out.println("no tiene dni");
                getPersonaBean().setIdPersona(MaristaConstantes.SIN_NRODOC_DOCINGRESO);
                getPersonaBean().setNroDoc(MaristaConstantes.SIN_NRODOC_DOCINGRESO);
                System.out.println("id...1 " + getPersonaBean().getIdPersona());
            } else {
                this.flgGenCod = Boolean.FALSE;
                if (getPersonaBean().getIdPersona().equals(null) || getPersonaBean().getIdPersona().equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
//                    System.out.println("noooooo tiene dni" + getPersonaBean().getIdPersona());
                    getPersonaBean().setIdPersona("");
                    getPersonaBean().setNroDoc("");
                } else {
                    getPersonaBean().setIdPersona(getPersonaBean().getIdPersona().replace(" ", ""));
//                    System.out.println("siii tiene dni" + getPersonaBean().getIdPersona());
                }
            }
            System.out.println("flg fin " + this.flgGenCod);
            System.out.println("id... " + getPersonaBean().getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verSexo() {
        try {
            System.out.println("sex " + getPersonaBean().getSexo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void aaaaa() {
        try {
            System.out.println("aaaaaaaa -->" + this.flgGenCod);
            System.out.println("name -->" + getPersonaBean().getIdPersona() + ", " + getPersonaBean().getApepat() + " " + getPersonaBean().getApemat() + " " + getPersonaBean().getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerCajaGenEnDoc(CajaGenBean cajaGenBean) {
        try {
            System.out.println("numero_de_caja1: " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
            System.out.println("Antiguo_numero1: " + idCajaGenVista);
            if (this.cajaGen != null) {
                System.out.println("caja ->" + this.cajaGen);
                if (this.cajaGen.equals("cajaGen")) {
                    detDocIngresoBean.getDocIngresoBean().setCajaGenBean(cajaGenBean);
                    detDocIngresoBean.getDocIngresoBean().setFlgCajaGenNull(null);
                    System.out.println("idcajagen2 " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
                    System.out.println("idcajagen2 " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().getIdCaja());
                }
                if (this.cajaGen.equals("cajaGenAnulado")) {
                    detDocIngresoBean.getDocIngresoBean().setIdCajaGenAnulado(cajaGenBean.getIdCajaGen());
                    detDocIngresoBean.getDocIngresoBean().setNombreCajaAnulado(cajaGenBean.getCajaBean().getNombre());
                    detDocIngresoBean.getDocIngresoBean().setFlgCajaGenAnuladoNull(null);
                    System.out.println("idcajagen3 " + detDocIngresoBean.getDocIngresoBean().getIdCajaGenAnulado());
                    System.out.println("idcajagen3 " + detDocIngresoBean.getDocIngresoBean().getNombreCajaAnulado());
                }
            } else {
                System.out.println("null this.cajaGen");
            }

            System.out.println("numero_de_caja2: " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
            System.out.println("Antiguo_numero2: " + idCajaGenVista);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void nullearCajaDocIngreso(String tipo) {
        try {
            if (tipo.equals("cajaGen")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setIdCajaGen(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().setIdCaja(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getCajaBean().setNombre(null);
                    detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaAperturaView("  /  /  ");
                }
            }
            if (tipo.equals("cajaGenAnulado")) {
                if (detDocIngresoBean != null) {
                    detDocIngresoBean.getDocIngresoBean().setIdCajaGenAnulado(null);
                    detDocIngresoBean.getDocIngresoBean().setNombreCajaAnulado(null);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void verHistoriaArqueosGeneral2(String tipo) {
        try {
            if (tipo == null) {
                System.out.println("caja null->" + this.cajaGen);
            } else {
                this.cajaGen = tipo;
                System.out.println("caja ->" + this.cajaGen);
            }
            limpiarCajaGen();
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio() != null) {
                Timestamp t = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaInicio(t);
            }
            if (detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin() != null) {
                Timestamp u = new Timestamp(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detDocIngresoBean.getDocIngresoBean().getCajaGenBean().setFechaFin(u);
            }
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            listaCajaGenBean = cajaGenService.obtenerCierresCajaPorCajero(null, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaInicio(), detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getFechaFin());
            if (listaCajaGenBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCajaGen() {
        try {
            listaCajaGenBean = new ArrayList<>();
            Calendar fechaActual = new GregorianCalendar();
            getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoBean().getDocIngresoBean().getCajaGenBean().setFechaFin(fechaActual.getTime());

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerDocIngresoPorId(Object object, String tipo) {
        try {
            docIngresoBean = (DocIngresoBean) object;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            docIngresoBean = docIngresoService.obtenerDocIngresoPorId(docIngresoBean.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (tipo.equals("estado")) {
                if (docIngresoBean.getTipoStatusDocIng().getCodigo().equals(MaristaConstantes.COD_STA_DOC_PAGADO)) {
                    flgAnulado = false;
                    CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                    CodigoService codigoService = BeanFactory.getCodigoService();
                    CajaGenBean cajaGen1 = new CajaGenBean();
                    cajaGen1 = cajaGenService.verificarCierreCajaGen(docIngresoBean.getCajaGenBean().getIdCajaGen(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    //si cajaGen1==null-> caja abierta, se puede anular o hacer devolucion, default solo anular,sí afecta al cuadre
                    //si cajaGen1 != null->  solo puedo hacer devolucion 
                    listaTipoEstadpDocIng = new ArrayList<>();
                    listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
                    if (cajaGen1 != null) {
                        for (CodigoBean lista : listaTipoEstadpDocIng) {
                            if (lista.getCodigo().equals(MaristaConstantes.COD_STA_DOC_ANULADO) || lista.getCodigo().equals(MaristaConstantes.COD_STA_DOC_PAGADO)) {
                                listaTipoEstadpDocIng.remove(lista);
                                break;
                            }
                        }
                    } else {
                        for (CodigoBean lista : listaTipoEstadpDocIng) {
                            if (lista.getCodigo().equals(MaristaConstantes.COD_STA_DOC_DEVUELTO) || lista.getCodigo().equals(MaristaConstantes.COD_STA_DOC_PAGADO)) {
                                listaTipoEstadpDocIng.remove(lista);
                                break;
                            }
                        }
                    }
                } else {
                    listaTipoEstadpDocIng = new ArrayList<>();
                    flgAnulado = true;
//                new MensajePrime().addInformativeMessagePer("msjCambioNoProcede");
                }
            } else {
                PersonaService personaService = BeanFactory.getPersonaService();
                PersonaBean persona = new PersonaBean();
                persona.setIdPersona(docIngresoBean.getCodDiscente());
                persona.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                persona = personaService.obtenerPersPorId(persona);
                if (persona != null) {
                    if (persona.getIdPersona() != null) {
                        docIngresoBean.setIdDiscente(persona);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDocIngresoPorId2(Object object) {
        try {
            System.out.println("obtenerDocIngresoPorId2");
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerTodosMatri();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaModoPago = codigoService.obtenerCodigoDocIngreso();
            listaTipoDscto = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DSCTO));
            listaTipoStatusDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorIng(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            docIngresoBean = (DocIngresoBean) object;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();

            docIngresoBean = docIngresoService.obtenerDocIngresoPorIdFull(docIngresoBean.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (docIngresoBean != null) {
                Integer count = 0;
                count = docIngresoService.cantDetPorIdDocIngreso(docIngresoBean.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                System.out.println("id doc ing..." + docIngresoBean.getIdDocIngreso() + "----" + count);
                if (count >= 1) {
                    DetDocIngresoBean det = new DetDocIngresoBean();
                    det = docIngresoService.obtenerDetDocIngPorDocIngreso(docIngresoBean.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    if (det != null) {
                        detDocIngresoBean = det;
                        detDocIngresoBean.setDocIngresoBean(docIngresoBean);
                    }
                }
            }
            System.out.println("numero_de_caja: " + detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
            idCajaGenVista = detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen();
            System.out.println("Antiguo_numero: " + idCajaGenVista);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String modificarDocIngFull() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();

            if (pagina == null) {
//                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                detDocIngresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                detDocIngresoBean.getDocIngresoBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                detDocIngresoBean.setIdDocIngreso(detDocIngresoBean.getDocIngresoBean().getIdDocIngreso());
                if (detDocIngresoBean.getDocIngresoBean().getCodDiscente().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setCodDiscente(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getNombreDiscente().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setNombreDiscente(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getSeccion().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setSeccion(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getDireccion().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setDireccion(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getTelefono().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setTelefono(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getFlgCajaGenNull().equals(Boolean.TRUE)) {
                    detDocIngresoBean.getDocIngresoBean().setCajaGenBean(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getImpresoraCajaBean().getImpresora().getImpresora().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getIdRespPago().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setIdRespPago(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getNomResPago().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setNomResPago(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getCreaStatus().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setCreaStatus(null);
                }
                if (detDocIngresoBean.getDocIngresoBean().getObs().equals("")) {
                    detDocIngresoBean.getDocIngresoBean().setObs(null);
                }
                if (detDocIngresoBean.getCuentaD().getCuenta().equals("")) {
                    detDocIngresoBean.getCuentaD().setCuenta(null);
                }
                if (detDocIngresoBean.getCuentaH().getCuenta().equals("")) {
                    detDocIngresoBean.getCuentaH().setCuenta(null);
                }
                if (detDocIngresoBean.getReferencia().equals("")) {
                    detDocIngresoBean.setReferencia(null);
                }
                docIngresoService.modificarDocIngyDetalleFullCajGen(detDocIngresoBean);
                
                CajaGenService cajaGenService = BeanFactory.getCajaGenService(); 
                if (!detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen().equals(idCajaGenVista)) {
                    DocIngresoBean docCajaNueva = new DocIngresoBean();
                    docCajaNueva = docIngresoService.obtenerMontosAntiguaCajaGen(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
                    
                    CajaGenBean cajaNueva = new CajaGenBean();
                    cajaNueva.setIngresoSol(docCajaNueva.getMontoEfectivoSol());
                    cajaNueva.setIngresoDol(docCajaNueva.getMontoEfectivoDol());
                    cajaNueva.setIngresoPos1(docCajaNueva.getMontoPos1());
                    cajaNueva.setIngresoPos2(docCajaNueva.getMontoPos2());
                    cajaNueva.setIdCajaGen(detDocIngresoBean.getDocIngresoBean().getCajaGenBean().getIdCajaGen());
                    cajaNueva.getUniNeg().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    cajaGenService.modificarIngresoSolYDol(cajaNueva);
                    
                    DocIngresoBean docCajaAntiguo = new DocIngresoBean();
                    docCajaAntiguo = docIngresoService.obtenerMontosAntiguaCajaGen(idCajaGenVista); 
                    CajaGenBean cajaAntigua = new CajaGenBean();
                    cajaAntigua.setIngresoSol(docCajaAntiguo.getMontoEfectivoSol());
                    cajaAntigua.setIngresoDol(docCajaAntiguo.getMontoEfectivoDol());
                    cajaAntigua.setIngresoPos1(docCajaAntiguo.getMontoPos1());
                    cajaAntigua.setIngresoPos2(docCajaAntiguo.getMontoPos2());
                    cajaAntigua.setIdCajaGen(idCajaGenVista);
                    cajaAntigua.getUniNeg().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    cajaGenService.modificarIngresoSolYDol(cajaAntigua);
                } 
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void cambiarEstadoDocIngreso() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            String estado = null;
            if (docIngresoBean.getTipoStatusDocIng().getIdCodigo().equals(MaristaConstantes.COD_ANULADO)) {
                estado = MaristaConstantes.COD_STA_DOC_ANULADO;
            } else if (docIngresoBean.getTipoStatusDocIng().getIdCodigo().equals(MaristaConstantes.COD_DEVUELTO)) {
                estado = MaristaConstantes.COD_STA_DOC_DEVUELTO;
                new MensajePrime().addInformativeMessagePer("msjCajaOffNoAfecta");

            } else if (docIngresoBean.getTipoStatusDocIng().getIdCodigo().equals(MaristaConstantes.COD_PAGADO)) {
                estado = MaristaConstantes.COD_STA_DOC_PAGADO;
                new MensajePrime().addInformativeMessagePer("msjCajaOffNoAfecta");
            }
            if (estado != null) {
                docIngresoService.cambiarEstadoDocIngreso(docIngresoBean, estado, usuarioLoginBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                obtenerFiltroDocIngreso();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarDatDocIngreso() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            docIngresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            docIngresoService.cambiarDatBasicosDocIngreso(docIngresoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            obtenerFiltroDocIngreso();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMontosRep(String tipo) {
        try {
            resetMontosRep();
            if (tipo.equals("docIng")) {
                for (DocIngresoBean doc : listDocIngresoBean) {
                    totSoles = totSoles + (doc.getMontoEfectivoSol());
                    tolDolares = tolDolares + (doc.getMontoEfectivoDol());
//                    tolDolaresConvSoles = (tolDolares.doubleValue() * (doc.getTc()));
                    totPOS1 = totPOS1 + (doc.getMontoPos1());
                    totPOS2 = totPOS2 + (doc.getMontoPos2());
                }
            } else {
                for (DetDocIngresoRepBean doc : listaDetDocIngresoRepBean) {
                    totSoles = totSoles + (doc.getMontoSoles());
                    tolDolares = tolDolares + (doc.getMontoDolares());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validaCampos() {
        try {
            if (docIngresoBean.getObs() != null && !docIngresoBean.getObs().equals("")) {
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                if (this.flgAnulado.equals(false)) {
                    if (docIngresoBean.getTipoStatusDocIng().getIdCodigo().equals(MaristaConstantes.COD_ANULADO)) {
                        docIngresoBean.getTipoStatusDocIng().setCodigo(MaristaConstantes.COD_STA_DOC_ANULADO);
                        new MensajePrime().addInformativeMessagePer("msjCajaOffSiAfecta");
                    } else if (docIngresoBean.getTipoStatusDocIng().getIdCodigo().equals(MaristaConstantes.COD_DEVUELTO)) {
                        docIngresoBean.getTipoStatusDocIng().setCodigo(MaristaConstantes.COD_STA_DOC_DEVUELTO);
                        new MensajePrime().addInformativeMessagePer("msjCajaOffNoAfecta");
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjCajaOffNoAfecta");
                }
            } else {
                new MensajePrime().addInformativeMessagePer("etiquetaDatosRequeridos");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void aaa() throws FileNotFoundException {
        try {
            System.out.println(personaFiltroBean.getNombreFiltro());
            System.out.println(personaFiltroBean.getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void impri() throws FileNotFoundException {
        try {
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob printerJob = defaultPrintService.createPrintJob();
            File pdfFile = new File("myPdfFile.pdf");
            SimpleDoc simpleDoc = new SimpleDoc(pdfFile.toURL(), DocFlavor.URL.AUTOSENSE, null);
            printerJob.print(simpleDoc, null);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void resetMontosRep() {
        try {
            totPOS1 = (0.0);
            totPOS2 = (0.0);
            totSoles = (0.0);
            tolDolares = (0.0);
            tolDolaresConvSoles = (0.0);
            totalSol = (0.0);
            totalDol = (0.0);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosRep() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            fechaActual = new GregorianCalendar();
            getDocIngresoFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocIngresoFiltroBean().setFechaFin(fechaActual.getTime());
            getDocIngresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            //validar perfil
            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLoginBean);
//            docIngresoFiltroBean.getIdTipoLugarPago().setIdCodigo(MaristaConstantes.COD_LUGAR_BANCO);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.ANULAR_SOLI) || usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("BARINA")) {
                    this.flgSistemas = true;
                    break;
                } else {
                    this.flgSistemas = false;
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDetDatosRep() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            fechaActual = new GregorianCalendar();
            getDetDocIngresoRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setFechaFin(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = new ArrayList<>();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivos();
            listaPlanContable = new ArrayList<>();
            listaPlanContable = planContableService.obtenerPlanContableNombre();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDetDatosRepMoras() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            fechaActual = new GregorianCalendar();
            getDetDocIngresoRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setFechaFin(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = new ArrayList<>();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoIngreso();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivosIngresos();
            listaPlanContable = new ArrayList<>();
            listaPlanContable = planContableService.obtenerPlanContableNombre();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDetDatosMatricula() {
        try {

            getDetDocIngresoRepFiltroBean().setFechaInicio(null);
            getDetDocIngresoRepFiltroBean().setFechaFin(null);
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoConceptoBean = new ArrayList<>();
            listaPlanContable = new ArrayList<>();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(getDetDocIngresoRepFiltroBean().getIdTipoConcepto(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoPorConcepto(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            Integer id = null;
            id = conceptoUniNegService.obtenerTipoPorIdConcepto(idConcepto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!id.equals(null)) {
                TipoConceptoBean tipo = new TipoConceptoBean();
                tipo = tipoConceptoService.obtenerTipoConceptoPorId(id);
                if (!tipo.equals(null)) {
                    getDetDocIngresoRepFiltroBean().setIdTipoConcepto(tipo.getIdTipoConcepto());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void displaySelectedMultiple(TreeNode[] nodes) {
        if (nodes != null && nodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : nodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void limpiarDocFiltroDocIng() {
        try {
            listDocIngresoBean = new ArrayList<>();
            docIngresoFiltroBean = new DocIngresoBean();
            fechaActual = new GregorianCalendar();
            getDocIngresoFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocIngresoFiltroBean().setFechaFin(fechaActual.getTime());
            getDocIngresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            resetMontosRep();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDetDocFiltroDocIng() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            PlanContableService planContableService = BeanFactory.getPlanContableService();

            listaDetDocIngresoRepBean = new ArrayList<>();
            listaConceptoUniNegBean = new ArrayList<>();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivos();

            detDocIngresoRepFiltroBean = new DetDocIngresoRepBean();

            listaPlanContable = new ArrayList<>();
            listaPlanContable = planContableService.obtenerPlanContableNombre();

            fechaActual = new GregorianCalendar();
            getDetDocIngresoRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setFechaFin(fechaActual.getTime());
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            resetMontosRep();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDetDocFiltroPagoMat() {
        try {
            listaDetDocIngresoRepBean = new ArrayList<>();
            listaConceptoUniNegBean = new ArrayList<>();
            detDocIngresoRepFiltroBean = new DetDocIngresoRepBean();
            listaPlanContable = new ArrayList<>();
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            resetMontosRep();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //programacion
    public void obtenerProgramacionActivos(Integer idConcepto) {
        try {
            if (idConcepto != null) {
//                for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
//                    System.out.println("lista prog  v2: " + listaProgramacion.getFlgInscrito());
//                }
                List<Integer> lista = new ArrayList<>();
                if (listDetDocIngreso.isEmpty()) {
                    lista.add(0);
                } else {
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        lista.add(det.getProgramacionBean().getIdProgramacion());
                    }
                }
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
//                if (personaBean.getIdPersona() != null) {
//                    List<Integer> lista2 = new ArrayList<>();
//                    lista2 = programacionService.obtenerProgRegPorDiscente(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), personaBean.getIdPersona());
//                    if (!lista2.isEmpty()) {
//                        for (Integer p : lista2) {
////                            System.out.println("listyy" + p);
//                            lista.add(p);
//                        }
//                    }
//                }
                listaProgramacionBean = new ArrayList<>();
                if (!idConcepto.equals(null)) {
                    listaProgramacionBean = programacionService.obtenerProgPorTipoActivosDocIngFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, idConcepto);
                    for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
                        if (listaProgramacion.getCupos().equals(0)) {
//                        if (listaProgramacion.getDisponibles().equals(0)) {
//                    listaProgramacionBean.remove(listaProgramacion);
                            listaProgramacion.setFlgBloqueado(Boolean.TRUE);
//                            break;
                        } else {
                            listaProgramacion.setFlgBloqueado(Boolean.FALSE);
                        }

                    }
                } else {
                    listaProgramacionBean = programacionService.obtenerProgPorTipoActivos(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    for (ProgramacionBean listaProgramacion : listaProgramacionBean) {
                        if (listaProgramacion.getDisponibles().equals(0)) {
//                    listaProgramacionBean.remove(listaProgramacion);
                            listaProgramacion.setFlgBloqueado(Boolean.TRUE);
//                            break;
                        } else {
                            listaProgramacion.setFlgBloqueado(Boolean.FALSE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionRegistroVer2() {
        try {
//            System.out.println("obtenerProgramacionRegistroVer2");
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() != null) {
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {

                    ProgramacionService programacionService = BeanFactory.getProgramacionService();
                    if (listaProgramacionSessionBean == null) {
                        listaProgramacionSessionBean = new ArrayList<>();
                    }
                    int est = 0;
                    for (ProgramacionBean lista : listaProgramacionBean) {
//                        System.out.println("est " + lista.getFlgInscrito());
                        if (lista.getFlgInscrito() == (null)) {
                        } else {
                            if (lista.getFlgInscrito().equals(true)) {
                                est = 1;
                                break;
                            }
                        }
                    }

                    /////////////// //ingresa
                    if (est == 1) {
                        ProgramacionBean progra = new ProgramacionBean();
                        for (ProgramacionBean listaProgramacionBean1 : listaProgramacionBean) {
                            if (listaProgramacionBean1.getFlgInscrito() != null) {
                                if (!listaProgramacionBean1.getFlgInscrito().equals(null)) {
                                    if (listaProgramacionBean1.getFlgInscrito().equals(true)) {
                                        progra.setIdProgramacion(listaProgramacionBean1.getIdProgramacion());
                                        progra.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                                        progra = programacionService.obtenerPrograPorId(progra);
//                                        System.out.println("add prog:" + progra.getDescripProgramacion());
                                        listaProgramacionSessionBean.add(progra);
                                        for (DetDocIngresoBean listDetDocIngreso2 : listDetDocIngreso) {
                                            if (progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
                                                    && listDetDocIngreso2.getConceptoBean().getIdConcepto().equals(progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto())) {
                                                if (listDetDocIngreso2.getProgramacionBean().getIdProgramacion() == null) {
                                                    listDetDocIngreso2.setReferencia(progra.getGlosa());
                                                    listDetDocIngreso2.setProgramacionBean(progra);
                                                } else {
                                                    if (progra.getIdProgramacion().equals(listDetDocIngreso2.getProgramacionBean().getIdProgramacion())) {
                                                        listDetDocIngreso2.setReferencia(progra.getGlosa());
                                                        listDetDocIngreso2.setProgramacionBean(progra);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //fase 2
                        Double monto = 0.0;
                        for (DetDocIngresoBean det : listDetDocIngreso) {
                            det.setMontoPagado(det.getMontoConDscto());
                            monto = monto + det.getMontoConDscto().doubleValue();
                            if (det.getDscto().equals(null)) {
                                det.setDscto(new BigDecimal(0));
                            } else {
                                det.setDscto(det.getDsctoTipoDicente());
                            }
                        }

                        //fase 3
//                        System.out.println("validarDescuentosTalleresFase1");
                        ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
                        ProgramacionDsctoBean programacionDsctoBean = new ProgramacionDsctoBean();
                        List<Integer> lista = new ArrayList<>();//ids de las programaciones del detalle  

                        for (DetDocIngresoBean det : listDetDocIngreso) {
//                            System.out.println("id det :" + det.getProgramacionBean().getIdProgramacion());
                            if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                lista.add(det.getProgramacionBean().getIdProgramacion());
                            }
                        }

                        //fase 4
                        System.out.println("alumno:" + tipoDiscenteBean.getFlgAlu());
                        //si es alumno            
                        Double montoDscto;
                        int cantProg = lista.size();
                        for (int i = cantProg; 1 < cantProg; i--) {
                            cantProg = i;
                            programacionDsctoBean = programacionDsctoService.obtenerProgDsctoPorProgramacionesForVer2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, cantProg, monto, tipoDiscenteBean.getFlgAlu());
                            if (programacionDsctoBean != null) {
                                for (DetDocIngresoBean det : listDetDocIngreso) {
                                    if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                        Integer estadoDscto = 0;
                                        estadoDscto = programacionDsctoService.validarProgEnDetDscto(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), det.getProgramacionBean().getIdProgramacion(), programacionDsctoBean.getIdProgramacionDscto());
//                                        System.out.println("estado:"+estado);
                                        if (estadoDscto.equals(1)) {
                                            Double montoConDscto = det.getMontoConDscto().doubleValue();
//                                            System.out.println("montoConDscto:" + montoConDscto);
                                            Double porcenDscto = (double) Math.round((programacionDsctoBean.getValor().doubleValue()) * 100) / 100;
//                                            System.out.println("porcenDscto:" + porcenDscto);
                                            if (programacionDsctoBean.getTipoValorBean().getCodigo().equals(MaristaConstantes.Porcentual)) {
                                                montoDscto = (double) Math.round((montoConDscto * porcenDscto) * 100) / 100;
                                            } else {
                                                System.out.println("ordinal");
                                                montoDscto = (double) Math.round((porcenDscto) * 100) / 100;
                                            }
//                                            System.out.println("montoDscto:" + montoDscto);
                                            //double rounded = (double) Math.round(monDolares * 100) / 100;
                                            BigDecimal montoDsctoBD = new BigDecimal(montoDscto);
                                            montoDsctoBD = montoDsctoBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                            BigDecimal dsctoBD = new BigDecimal(montoDscto);
                                            dsctoBD = dsctoBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                            det.setMontoPagado(det.getMontoConDscto().subtract(montoDsctoBD));
                                            det.setDscto(dsctoBD.add(det.getDsctoTipoDicente()));
//                                            System.out.println("id>>>> " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado()); 
                                        } else {
//                                            System.out.println("no tiene dad");
                                        }
                                    }
                                }
//                                System.out.println("fiin");
                                break;
                            }
                        }

                        obtenerDscto();
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        RequestContext.getCurrentInstance().addCallbackParam("operacionError", true);
                    }
                } else if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                    obtenerProgramacionRegistroSanlui();
                } else {
                    obtenerProgramacionRegistro();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void afterRemove() {
        try {

//            System.out.println("obtenerProgramacionRegistroVer2");
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() != null) {
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {

//                    ProgramacionService programacionService = BeanFactory.getProgramacionService();
                    if (listaProgramacionSessionBean == null) {
                        listaProgramacionSessionBean = new ArrayList<>();
                    }
                    //fase 2
                    Double monto = 0.0;
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        det.setMontoPagado(det.getMontoConDscto());
                        monto = monto + det.getMontoConDscto().doubleValue();
                        if (det.getDscto().equals(null)) {
                            det.setDscto(new BigDecimal(0));
                        } else {
                            det.setDscto(det.getDsctoTipoDicente());
                        }
                    }

                    //fase 3
//                    System.out.println("validarDescuentosTalleresFase1");
                    ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
                    ProgramacionDsctoBean programacionDsctoBean = new ProgramacionDsctoBean();
                    List<Integer> lista = new ArrayList<>();//ids de las programaciones del detalle  

                    for (DetDocIngresoBean det : listDetDocIngreso) {
//                        System.out.println("id det :" + det.getProgramacionBean().getIdProgramacion());
                        if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                            lista.add(det.getProgramacionBean().getIdProgramacion());
                        }
                    }

                    //fase 4
                    int cantProg = lista.size();
                    for (int i = cantProg; 1 < cantProg; i--) {
                        cantProg = i;
                        programacionDsctoBean = programacionDsctoService.obtenerProgDsctoPorProgramacionesForVer2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, cantProg, monto, tipoDiscenteBean.getFlgAlu());
                        if (programacionDsctoBean != null) {
                            for (DetDocIngresoBean det : listDetDocIngreso) {
                                if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                    Integer estado = 0;
                                    estado = programacionDsctoService.validarProgEnDetDscto(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), det.getProgramacionBean().getIdProgramacion(), programacionDsctoBean.getIdProgramacionDscto());
                                    if (estado.equals(1)) {
                                        Double montoConDscto = det.getMontoConDscto().doubleValue();
//                                            System.out.println("montoConDscto:" + montoConDscto);
                                        Double porcenDscto = (double) Math.round((programacionDsctoBean.getValorUnitario().doubleValue()) * 100) / 100;
//                                            System.out.println("porcenDscto:" + porcenDscto);
                                        Double montoDscto = (double) Math.round(porcenDscto * montoConDscto * 100) / 100;
//                                            System.out.println("montoDscto:" + montoDscto);
                                        //double rounded = (double) Math.round(monDolares * 100) / 100;
                                        BigDecimal montoDsctoBD = new BigDecimal(montoDscto);
                                        montoDsctoBD = montoDsctoBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                        BigDecimal dsctoBD = new BigDecimal(montoDscto);
                                        dsctoBD = dsctoBD.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                                        det.setMontoPagado(det.getMontoConDscto().subtract(montoDsctoBD));
                                        det.setDscto(dsctoBD.add(det.getDsctoTipoDicente()));
//                                            System.out.println("id>>>> " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
                                    } else {
//                                            System.out.println("no tiene dad");
                                    }
                                }
                            }
//                            System.out.println("fiin");
                            break;
                        }
                    }

//                    }
                    obtenerDscto();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    obtenerProgramacionRegistro();
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
            if (listaProgramacionSessionBean == null) {
                listaProgramacionSessionBean = new ArrayList<>();
            }
            int est = 0;
            for (ProgramacionBean lista : listaProgramacionBean) {
//                System.out.println("est " + lista.getFlgInscrito());
                if (lista.getFlgInscrito() == (null)) {
//                    System.out.println("null lista.getFlgInscrito()");
                } else {
                    if (lista.getFlgInscrito().equals(true)) {
                        est = 1;
                        break;
                    }
                }
            }
//            System.out.println("-------------------estado prog break------------" + est);
            //ingresa
            if (est == 1) {
                ProgramacionBean progra = new ProgramacionBean();
                for (ProgramacionBean listaProgramacionBean1 : listaProgramacionBean) {
                    if (listaProgramacionBean1.getFlgInscrito() != null) {
                        if (!listaProgramacionBean1.getFlgInscrito().equals(null)) {
                            if (listaProgramacionBean1.getFlgInscrito().equals(true)) {
                                progra.setIdProgramacion(listaProgramacionBean1.getIdProgramacion());
                                progra.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                                progra = programacionService.obtenerPrograPorId(progra);
                                listaProgramacionSessionBean.add(progra);
                                System.out.println("descrip:" + progra.getDescripProgramacion());
                                for (DetDocIngresoBean listDetDocIngreso2 : listDetDocIngreso) {
                                    if (progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
                                            && listDetDocIngreso2.getConceptoBean().getIdConcepto().equals(progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto())) {
                                        if (listDetDocIngreso2.getProgramacionBean().getIdProgramacion() == null) {
                                            listDetDocIngreso2.setReferencia(progra.getGlosa());
                                            listDetDocIngreso2.setProgramacionBean(progra);
                                        } else {
                                            if (progra.getIdProgramacion().equals(listDetDocIngreso2.getProgramacionBean().getIdProgramacion())) {
                                                listDetDocIngreso2.setReferencia(progra.getGlosa());
                                                listDetDocIngreso2.setProgramacionBean(progra);
                                            }
                                        }
                                    }
                                    System.out.println("list prog->" + listDetDocIngreso2.getProgramacionBean().getIdProgramacion());
                                }
                                System.out.println("list->" + listDetDocIngreso.size());
                            }
                        }
                    }
                }
                validarDescuentosTalleresFase1(null);
                for (DetDocIngresoBean det : listDetDocIngreso) {
                    if (det.getDscto().equals(null)) {
                        det.setDscto(new BigDecimal(0));
                    } else {
                        det.setDscto(det.getDsctoTipoDicente());
                    }
                }
                if (listaProgramacionDsctoBean != null) {
                    if (listaProgramacionDsctoBean.size() > 0) {
                        for (ProgramacionDsctoBean li : listaProgramacionDsctoBean) {
                            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
                            System.out.println("id progra despues validarDescuentosTalleres " + li.getIdProgramacionDscto());

                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(li.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            for (DetProgramacionDsctoBean lis : listaDetProgDscto) {
                                System.out.println("-----------inicio:" + lis.getProgramacionBean().getIdProgramacion());
                                for (DetDocIngresoBean det : listDetDocIngreso) {
                                    System.out.println("id 1:" + lis.getProgramacionBean().getIdProgramacion() + "--" + det.getProgramacionBean().getIdProgramacion());
                                    if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                        if (lis.getProgramacionBean().getIdProgramacion().equals(det.getProgramacionBean().getIdProgramacion())) {
                                            System.out.println("id det->>>>>>>>>>>>>> :" + det.getProgramacionBean().getIdProgramacion() + "-" + lis.getValor().doubleValue());
                                            BigDecimal dscto = new BigDecimal(lis.getValor().doubleValue());
                                            det.setMontoPagado(det.getMontoConDscto().subtract(dscto));
                                            det.setDscto(dscto.add(det.getDsctoTipoDicente()));
                                            System.out.println("id>>>> " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
                                            break;
                                        }
                                    }
                                }
                                System.out.println("-----------fiin:" + lis.getProgramacionBean().getIdProgramacion());
                            }
                        }
                    }
                }
                obtenerDscto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionError", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerProgramacionRegistroSanlui() {
        try {
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            if (listaProgramacionSessionBean == null) {
                listaProgramacionSessionBean = new ArrayList<>();
            }
            int est = 0;
            for (ProgramacionBean lista : listaProgramacionBean) {
//                System.out.println("est " + lista.getFlgInscrito());
                if (lista.getFlgInscrito() == (null)) {
//                    System.out.println("null lista.getFlgInscrito()");
                } else {
                    if (lista.getFlgInscrito().equals(true)) {
                        est = 1;
                        break;
                    }
                }
            }
//            System.out.println("-------------------estado prog break------------" + est);
            //ingresa
            if (est == 1) {
                ProgramacionBean progra = new ProgramacionBean();
                for (ProgramacionBean listaProgramacionBean1 : listaProgramacionBean) {
                    if (listaProgramacionBean1.getFlgInscrito() != null) {
                        if (!listaProgramacionBean1.getFlgInscrito().equals(null)) {
                            if (listaProgramacionBean1.getFlgInscrito().equals(true)) {
                                progra.setIdProgramacion(listaProgramacionBean1.getIdProgramacion());
                                progra.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                                progra = programacionService.obtenerPrograPorId(progra);
                                listaProgramacionSessionBean.add(progra);
                                System.out.println("descrip:" + progra.getDescripProgramacion());
                                for (DetDocIngresoBean listDetDocIngreso2 : listDetDocIngreso) {
                                    if (progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto() != null
                                            && listDetDocIngreso2.getConceptoBean().getIdConcepto().equals(progra.getConceptoUniNegBean().getConceptoBean().getIdConcepto())) {
                                        if (listDetDocIngreso2.getProgramacionBean().getIdProgramacion() == null) {
                                            listDetDocIngreso2.setReferencia(progra.getGlosa());
                                            listDetDocIngreso2.setProgramacionBean(progra);
                                        } else {
                                            if (progra.getIdProgramacion().equals(listDetDocIngreso2.getProgramacionBean().getIdProgramacion())) {
                                                listDetDocIngreso2.setReferencia(progra.getGlosa());
                                                listDetDocIngreso2.setProgramacionBean(progra);
                                            }
                                        }
                                    }
                                    System.out.println("list prog->" + listDetDocIngreso2.getProgramacionBean().getIdProgramacion());
                                }
                                System.out.println("list->" + listDetDocIngreso.size());

                            }
                        }
                    }
                }
                validarDescuentosTalleresFase1San(null);
                for (DetDocIngresoBean det : listDetDocIngreso) {
                    if (det.getDscto().equals(null)) {
                        det.setDscto(new BigDecimal(0));
                    } else {
                        det.setDscto(det.getDsctoTipoDicente());
                    }
                }
                if (listaProgramacionDsctoBean != null) {
                    if (listaProgramacionDsctoBean.size() > 0) {
                        for (ProgramacionDsctoBean li : listaProgramacionDsctoBean) {
                            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
                            System.out.println("id progra despues validarDescuentosTalleres " + li.getIdProgramacionDscto());

                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(li.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            for (DetProgramacionDsctoBean lis : listaDetProgDscto) {
                                System.out.println("-----------inicio:" + lis.getProgramacionBean().getIdProgramacion());
                                for (DetDocIngresoBean det : listDetDocIngreso) {
                                    System.out.println("id 1:" + lis.getProgramacionBean().getIdProgramacion() + "--" + det.getProgramacionBean().getIdProgramacion());
                                    if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                                        if (lis.getProgramacionBean().getIdProgramacion().equals(det.getProgramacionBean().getIdProgramacion())) {
                                            System.out.println("id det->>>>>>>>>>>>>> :" + det.getProgramacionBean().getIdProgramacion() + "-" + lis.getValor().doubleValue());
                                            BigDecimal dscto = new BigDecimal(lis.getValor().doubleValue());
                                            det.setMontoPagado(det.getMontoConDscto().subtract(dscto));
                                            det.setDscto(dscto.add(det.getDsctoTipoDicente()));
                                            System.out.println("id>>>> " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
                                            break;
                                        }
                                    }
                                }
                                montoDscPro = lis.getValor();
                                System.out.println("-----------fiin:" + lis.getProgramacionBean().getIdProgramacion());
                            }
                        }
                    }
                }
                obtenerDscto();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                RequestContext.getCurrentInstance().addCallbackParam("operacionError", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDscto() {
        try {
            this.dscto = new BigDecimal(0);
            for (DetDocIngresoBean detDocIngresoBean : getListDetDocIngreso()) {
                System.out.println("dscto " + detDocIngresoBean.getDscto());
                if (detDocIngresoBean.getDscto() == null) {
                    detDocIngresoBean.setDscto(new BigDecimal(0));
                }
                this.dscto = this.dscto.add(detDocIngresoBean.getDscto());
                Double monto = (double) Math.round((new Double(dscto.doubleValue())) * 100) / 100;

                BigDecimal des = new BigDecimal(monto);
                des = des.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                this.dscto = des;

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public List<ProgramacionDsctoBean> validarDescuentosTalleresFase1(List<Integer> listaNewIds) {
        try {

            System.out.println("validarDescuentosTalleresFase1");
            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
            ProgramacionDsctoBean programacionDsctoBean = new ProgramacionDsctoBean();
            List<Integer> lista = new ArrayList<>();//ids de las programaciones del detalle 
            List<ProgramacionDsctoBean> listaProg = new ArrayList<>();//ids de las programaciones del detalle
            if (listaNewIds != null) {
                for (Integer l : lista) {
                    System.out.println("  lista..." + l);
                }
            } else {
                System.out.println("  lista null...");

            }
            if (listaNewIds == null) {
                System.out.println("llenando lista...");
                //OBTENGO LOS IDS DE LAS PROGRAMACIONES DEL DETALLE DEL DOC ING
                if (listDetDocIngreso.isEmpty()) {
                    lista.add(0);
                } else {
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                            lista.add(det.getProgramacionBean().getIdProgramacion());
                            System.out.println("id det :" + det.getProgramacionBean().getIdProgramacion());
                        }
                    }
                }
            } else {
                System.out.println("llenando listaNewIds ...");
                lista = listaNewIds;
            }

            Integer size = lista.size();
            Integer sizeDetLista = listDetDocIngreso.size();
            System.out.println("//OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO");
            //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO
            listaProg = programacionDsctoService.obtenerProgDsctoPorProgramacionesCantidadFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, size);
            if (listaProg != null) {
                if (!listaProg.isEmpty()) {
                    ProgramacionDsctoBean progDsctoAux = new ProgramacionDsctoBean();
                    Double aux = 0.0;
                    for (ProgramacionDsctoBean siz : listaProg) {
                        programacionDsctoBean = programacionDsctoService.obtenerProgDsctoPorProgramacionesFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, siz.getCantProgramaciones());
//                    System.out.println("id========>" + programacionDsctoBean.getIdProgramacionDscto());
                        if (programacionDsctoBean != null) {
                            if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                                System.out.println("id--------------------------------->" + programacionDsctoBean.getIdProgramacionDscto());
                                if (progDsctoAux.getValor() == null) {
                                    progDsctoAux.setValor(aux);
                                }
                                System.out.println("aux->" + aux);
                                System.out.println("bean->" + programacionDsctoBean.getValor());
                                if (programacionDsctoBean.getValor() > progDsctoAux.getValor()) {
                                    progDsctoAux = programacionDsctoBean;
                                }
                            } else {
                                System.out.println("nada");
                            }
                        }
                    }
                    if (progDsctoAux.getIdProgramacionDscto() != null) {
//                        System.out.println("win:" + programacion,,,,..DsctoBean.getIdProgramacionDscto());
                        programacionDsctoBean = progDsctoAux;
                    } else {
                        programacionDsctoBean = null;

                    }
//                    System.out.println("winnnn----" + programacionDsctoBean.getIdProgramacionDscto());
                    if (programacionDsctoBean != null) {
                        if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                            System.out.println("prog dscto" + programacionDsctoBean.getIdProgramacionDscto() + " - " + programacionDsctoBean.getDescripcion());
                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(programacionDsctoBean.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            //SI EL TAMAÑO DE PROGRAMACIONES DEL DETALLE ES IGUAL AL DEL DETALLE DE PROGRAMCIONES DEL DSCTO 
                            if (sizeDetLista.equals(listaDetProgDscto.size())) {
                                listaProgramacionDsctoBean = new ArrayList<>();//= SOLO HAY UN DSCTO -->NEW ARRAY LIST  
                                listaProgramacionDsctoBean.add(programacionDsctoBean);
                                System.out.println("fin!!!!!!!!!!!");
                            } else {
                                List<Integer> listaNew = new ArrayList<>();
                                for (DetProgramacionDsctoBean A : listaDetProgDscto) {
                                    listaNew.add(A.getProgramacionBean().getIdProgramacion());
                                }

                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    System.out.println("id1:" + programacionDsctoBean.getIdProgramacionDscto() + "id2:" + list.getIdProgramacionDscto());
                                    Integer estado = 0;
                                    if (!programacionDsctoBean.getIdProgramacionDscto().equals(list.getIdProgramacionDscto())) {
                                        System.out.println("! equal id");
                                        estado = programacionDsctoService.validarProg1En2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaNew, list.getIdProgramacionDscto(), listaNew.size());
                                        if (estado == null) {
                                            estado = 0;
                                        }
                                        list.setFlgDelete(estado);
                                    } else {
                                        System.out.println("equal id");
                                        list.setFlgDelete(estado);
                                    }
                                }
                                removeProgramacionDscto();
                                List<Integer> listaIds = new ArrayList<>();//ids de las programaciones  
                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    List<Integer> listaIdProgDscto = new ArrayList<>();
                                    listaIdProgDscto.add(list.getIdProgramacionDscto());
                                    List<DetProgramacionDsctoBean> listaDetProgDscto2 = new ArrayList<>();
                                    listaDetProgDscto2 = programacionDsctoService.obtenerDetallePorProgramacionDscto(listaIdProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    for (DetProgramacionDsctoBean l : listaDetProgDscto2) {
                                        listaIds.add(l.getProgramacionBean().getIdProgramacion());
                                    }
                                }
//                                for (Integer l : listaIds) {
//                                    System.out.println("listaIds :" + l);
//                                }
//                                for (Integer l : lista) {
//                                    System.out.println("lista  :" + l);
//                                }
                                Integer sumSize = 0;
                                if (listaIds != null && lista != null) {
                                    sumSize = lista.size() + listaIds.size();
                                }
                                if (!sumSize.equals(sizeDetLista)) {
                                    System.out.println("sigue-----------------!!");
                                    validarIdProgramacion(listaIds, lista);
                                } else {
                                    listaProgramacionDsctoBean.add(programacionDsctoBean);
                                    System.out.println("fin");
                                }
                            }
                        }
                    } else {
                        System.out.println("prog dscto null");
                    }
                } else {
                    System.out.println(" if (!listaProg.isEmpty())  null");
                    //añadir doc ing
                    listaProgramacionDsctoBean = new ArrayList<>();
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        System.out.println("xxxx");
//                        det.setProgramacionBean(null);
                        if (det.getDscto().equals(null)) {
                            det.setDscto(new BigDecimal(0));
                        } else {
                            det.setDscto(det.getDsctoTipoDicente());
                        }
                    }
                    obtenerDscto();
                }
            } else {
                System.out.println("prog dscto null<<<<<<<");
            }
            System.out.println("-----------fin //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO-------");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaProgramacionDsctoBean;
    }

    public List<ProgramacionDsctoBean> validarDescuentosTalleresFase1San(List<Integer> listaNewIds) {
        try {

            System.out.println("validarDescuentosTalleresFase1");
            ProgramacionDsctoService programacionDsctoService = BeanFactory.getProgramacionDsctoService();
            ProgramacionDsctoBean programacionDsctoBean = new ProgramacionDsctoBean();
            List<Integer> lista = new ArrayList<>();//ids de las programaciones del detalle 
            List<ProgramacionDsctoBean> listaProg = new ArrayList<>();//ids de las programaciones del detalle
            if (listaNewIds != null) {
                for (Integer l : lista) {
                    System.out.println("  lista..." + l);
                }
            } else {
                System.out.println("  lista null...");

            }
            if (listaNewIds == null) {
                System.out.println("llenando lista...");
                //OBTENGO LOS IDS DE LAS PROGRAMACIONES DEL DETALLE DEL DOC ING
                if (listDetDocIngreso.isEmpty()) {
                    lista.add(0);
                } else {
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        if (!det.getProgramacionBean().getIdProgramacion().equals(null)) {
                            lista.add(det.getProgramacionBean().getIdProgramacion());
                            System.out.println("id det :" + det.getProgramacionBean().getIdProgramacion());
                        }
                    }
                }
            } else {
                System.out.println("llenando listaNewIds ...");
                lista = listaNewIds;
            }

            Integer size = lista.size();
            Integer sizeDetLista = listDetDocIngreso.size();
            System.out.println("//OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO");
            //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO
            listaProg = programacionDsctoService.obtenerProgDsctoPorProgramacionesCantidadFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, size);
            if (listaProg != null) {
                if (!listaProg.isEmpty()) {
                    ProgramacionDsctoBean progDsctoAux = new ProgramacionDsctoBean();
                    Double aux = 0.0;
                    for (ProgramacionDsctoBean siz : listaProg) {
                        programacionDsctoBean = programacionDsctoService.obtenerProgDsctoPorProgramacionesForSan(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, siz.getCantProgramaciones(), tipoDiscenteBean.getFlgAlu(), listDetDocIngreso.get(0).getMes());
//                    System.out.println("id========>" + programacionDsctoBean.getIdProgramacionDscto());
                        if (programacionDsctoBean != null) {
                            if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                                System.out.println("id--------------------------------->" + programacionDsctoBean.getIdProgramacionDscto());
                                if (progDsctoAux.getValor() == null) {
                                    progDsctoAux.setValor(aux);
                                }
                                System.out.println("aux->" + aux);
                                System.out.println("bean->" + programacionDsctoBean.getValor());
                                if (programacionDsctoBean.getValor() > progDsctoAux.getValor()) {
                                    progDsctoAux = programacionDsctoBean;
                                }
                            } else {
                                System.out.println("nada");
                            }
                        }
                    }
                    if (progDsctoAux.getIdProgramacionDscto() != null) {
//                        System.out.println("win:" + programacion,,,,..DsctoBean.getIdProgramacionDscto());
                        programacionDsctoBean = progDsctoAux;
                    } else {
                        programacionDsctoBean = null;

                    }
//                    System.out.println("winnnn----" + programacionDsctoBean.getIdProgramacionDscto());
                    if (programacionDsctoBean != null) {
                        if (programacionDsctoBean.getIdProgramacionDscto() != null) {
                            System.out.println("prog dscto" + programacionDsctoBean.getIdProgramacionDscto() + " - " + programacionDsctoBean.getDescripcion());
                            List<DetProgramacionDsctoBean> listaDetProgDscto = new ArrayList<>();
                            List<Integer> idProgDscto = new ArrayList<>();//ids de las programaciones del detalle
                            idProgDscto.add(programacionDsctoBean.getIdProgramacionDscto());

                            listaDetProgDscto = programacionDsctoService.obtenerDetallePorProgramacionDscto(idProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                            //SI EL TAMAÑO DE PROGRAMACIONES DEL DETALLE ES IGUAL AL DEL DETALLE DE PROGRAMCIONES DEL DSCTO 
                            if (sizeDetLista.equals(listaDetProgDscto.size())) {
                                listaProgramacionDsctoBean = new ArrayList<>();//= SOLO HAY UN DSCTO -->NEW ARRAY LIST  
                                listaProgramacionDsctoBean.add(programacionDsctoBean);
                                System.out.println("fin!!!!!!!!!!!");
                            } else {
                                List<Integer> listaNew = new ArrayList<>();
                                for (DetProgramacionDsctoBean A : listaDetProgDscto) {
                                    listaNew.add(A.getProgramacionBean().getIdProgramacion());
                                }

                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    System.out.println("id1:" + programacionDsctoBean.getIdProgramacionDscto() + "id2:" + list.getIdProgramacionDscto());
                                    Integer estado = 0;
                                    if (!programacionDsctoBean.getIdProgramacionDscto().equals(list.getIdProgramacionDscto())) {
                                        System.out.println("! equal id");
                                        estado = programacionDsctoService.validarProg1En2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaNew, list.getIdProgramacionDscto(), listaNew.size());
                                        if (estado == null) {
                                            estado = 0;
                                        }
                                        list.setFlgDelete(estado);
                                    } else {
                                        System.out.println("equal id");
                                        list.setFlgDelete(estado);
                                    }
                                }
                                removeProgramacionDscto();
                                List<Integer> listaIds = new ArrayList<>();//ids de las programaciones  
                                for (ProgramacionDsctoBean list : listaProgramacionDsctoBean) {
                                    List<Integer> listaIdProgDscto = new ArrayList<>();
                                    listaIdProgDscto.add(list.getIdProgramacionDscto());
                                    List<DetProgramacionDsctoBean> listaDetProgDscto2 = new ArrayList<>();
                                    listaDetProgDscto2 = programacionDsctoService.obtenerDetallePorProgramacionDscto(listaIdProgDscto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                    for (DetProgramacionDsctoBean l : listaDetProgDscto2) {
                                        listaIds.add(l.getProgramacionBean().getIdProgramacion());
                                    }
                                }
//                                for (Integer l : listaIds) {
//                                    System.out.println("listaIds :" + l);
//                                }
//                                for (Integer l : lista) {
//                                    System.out.println("lista  :" + l);
//                                }
                                Integer sumSize = 0;
                                if (listaIds != null && lista != null) {
                                    sumSize = lista.size() + listaIds.size();
                                }
                                if (!sumSize.equals(sizeDetLista)) {
                                    System.out.println("sigue-----------------!!");
                                    validarIdProgramacion(listaIds, lista);
                                } else {
                                    listaProgramacionDsctoBean.add(programacionDsctoBean);
                                    System.out.println("fin");
                                }
                            }
                        }
                    } else {
                        System.out.println("prog dscto null");
                    }
                } else {
                    System.out.println(" if (!listaProg.isEmpty())  null");
                    //añadir doc ing
                    listaProgramacionDsctoBean = new ArrayList<>();
                    for (DetDocIngresoBean det : listDetDocIngreso) {
                        System.out.println("xxxx");
//                        det.setProgramacionBean(null);
                        if (det.getDscto().equals(null)) {
                            det.setDscto(new BigDecimal(0));
                        } else {
                            det.setDscto(det.getDsctoTipoDicente());
                        }
                    }
                    obtenerDscto();
                }
            } else {
                System.out.println("prog dscto null<<<<<<<");
            }
            System.out.println("-----------fin //OBTENGO LA MEJOR OPCION DE LA PROGRAMACIONDSCTO-------");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaProgramacionDsctoBean;
    }

    public void validarIdProgramacion(List<Integer> listIds, List<Integer> listDetalleDoc) {

        try {
            System.out.println("validarIdProgramacion");
            List<Integer> newList = new ArrayList<>();
//            newList = listDetalleDoc;
            for (Integer X : listDetalleDoc) {
                Integer estad = 1;
                System.out.println("X:" + X);
                for (Integer Y : listIds) {
                    System.out.println("X:" + X + " = " + "Y:" + Y);
                    if (X.equals(Y)) {
                        estad = 0;
                        break;
                    }
                }
                if (estad.equals(1)) {
                    System.out.println("add:" + X);
                    newList.add(X);
                }
            }

            if (newList.size() > 0) {
                System.out.println("newList.size()>0");
                validarDescuentosTalleresFase1(newList);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public ProgramacionDsctoBean validarIdProgramacionDscto() {
        ProgramacionDsctoBean programacionDscto = new ProgramacionDsctoBean();
        try {

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return programacionDscto;
    }

    public void removeProgramacionDscto() {
        try {
            System.out.println("removeProgramacionDscto");
            if (listaProgramacionDsctoBean != null) {
                List<ProgramacionDsctoBean> lista = new ArrayList<>();
                lista = listaProgramacionDsctoBean;
                for (ProgramacionDsctoBean list : lista) {
                    if (list.getFlgDelete() != null) {
                        if (list.getFlgDelete().equals(1)) {
                            System.out.println("eliminando ... " + list.getIdProgramacionDscto());
                            listaProgramacionDsctoBean.remove(list);
                        } else {
                            System.out.println("se queda ... " + list.getIdProgramacionDscto());
                        }
                    }
                }
//                for (ProgramacionDsctoBean o : listaProgramacionDsctoBean) {
//                    System.out.println("lista final " + o.getIdProgramacionDscto());
//                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarProgramacion(Integer idProgramacion) {
        try {
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getStatus() != null) {
                    if (lista.getStatus().equals(Boolean.TRUE) && lista.getIdProgramacion().equals(idProgramacion)) {
                        lista.setFlgInscrito(Boolean.TRUE);
                    } else {
                        lista.setFlgInscrito(Boolean.FALSE);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void noSeleccionarProgra() {
        try {
            for (ProgramacionBean lista : listaProgramacionBean) {
                lista.setFlgInscrito(Boolean.FALSE);
            }
            this.setValAdmTodos(Boolean.FALSE);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ejecutar() {
        System.out.println("yuyuyu");
    }

    public void cambiarMonto(RowEditEvent event) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean conceptoUNBean = new ConceptoUniNegBean();

            conceptoUNBean.setImporte(((ConceptoUniNegBean) event.getObject()).getImporte());
            conceptoUNBean.setModiPor(usuarioLoginBean.getUsuario());
            conceptoUNBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            conceptoUNBean.getConceptoBean().setIdConcepto(((ConceptoUniNegBean) event.getObject()).getConceptoBean().getIdConcepto());
            conceptoUniNegService.modificarMontoConceptoUniNeg(conceptoUNBean);
//            obtenerDsctoTipoDiscente(personaBean.getIdPersona());
            obtenerDsctoTipoDiscenteVer2(personaBean.getIdPersona());

            conceptoUNBean = new ConceptoUniNegBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarMontoAcero(Integer idConcepto) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean conceptoUNBean = new ConceptoUniNegBean();
            conceptoUNBean.setImporte(new BigDecimal(0.0));
            conceptoUNBean.setModiPor(usuarioLoginBean.getUsuario());
            conceptoUNBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            conceptoUNBean.getConceptoBean().setIdConcepto(idConcepto);
            conceptoUniNegService.modificarMontoConceptoUniNeg(conceptoUNBean);
            conceptoUNBean = new ConceptoUniNegBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public CajaGenBean getCajaGenBean() {
        if (cajaGenBean == null) {
            cajaGenBean = new CajaGenBean();
        }
        return cajaGenBean;
    }

    public void setCajaGenBean(CajaGenBean cajaGenBean) {
        this.cajaGenBean = cajaGenBean;
    }

    public TipoCambioBean getTipoCambio() {
        if (tipoCambio == null) {
            tipoCambio = new TipoCambioBean();
        }
        return tipoCambio;
    }

    public void setTipoCambio(TipoCambioBean tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public List<CodigoBean> getListaModoPago() {
        if (listaModoPago == null) {
            listaModoPago = new ArrayList<>();
        }
        return listaModoPago;
    }

    public void setListaModoPago(List<CodigoBean> listaModoPago) {
        this.listaModoPago = listaModoPago;
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

    public Map<String, String> getMapFamiliar() {
        return mapFamiliar;
    }

    public void setMapFamiliar(Map<String, String> mapFamiliar) {
        this.mapFamiliar = mapFamiliar;
    }

    public List<FamiliarEstudianteBean> getListaFamiliar() {
        return listaFamiliar;
    }

    public void setListaFamiliar(List<FamiliarEstudianteBean> listaFamiliar) {
        this.listaFamiliar = listaFamiliar;
    }

    public List<CodigoBean> getListaTipoDocumento() {
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<CodigoBean> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
    }

    public Map<String, Integer> getMapTipoDocumento() {
        return mapTipoDocumento;
    }

    public void setMapTipoDocumento(Map<String, Integer> mapTipoDocumento) {
        this.mapTipoDocumento = mapTipoDocumento;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
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

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
    }

    public List<PersonaBean> getListaPersonalBean() {
        if (listaPersonaBean == null) {
            listaPersonaBean = new ArrayList<>();
        }
        return listaPersonaBean;
    }

    public void setListaPersonalBean(List<PersonaBean> listaPersonalBean) {
        this.listaPersonaBean = listaPersonalBean;
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

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
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

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public TipoDiscenteBean getTipoDiscenteBean() {
        if (tipoDiscenteBean == null) {
            tipoDiscenteBean = new TipoDiscenteBean();
        }
        return tipoDiscenteBean;
    }

    public void setTipoDiscenteBean(TipoDiscenteBean tipoDiscenteBean) {
        this.tipoDiscenteBean = tipoDiscenteBean;
    }

    public List<DetDocIngresoBean> getListDetDocIngreso() {
        if (listDetDocIngreso == null) {
            listDetDocIngreso = new ArrayList<>();
        }
        return listDetDocIngreso;
    }

    public void setListDetDocIngreso(List<DetDocIngresoBean> listDetDocIngreso) {
        this.listDetDocIngreso = listDetDocIngreso;
    }

    public List<ConceptoUniNegBean> getListaConceptos() {
        if (listaConceptos == null) {
            listaConceptos = new ArrayList<>();
        }
        return listaConceptos;
    }

    public void setListaConceptos(List<ConceptoUniNegBean> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public DocIngresoSerieBean getDocIngresoSerieBean() {
        if (docIngresoSerieBean == null) {
            docIngresoSerieBean = new DocIngresoSerieBean();
        }
        return docIngresoSerieBean;
    }

    public void setDocIngresoSerieBean(DocIngresoSerieBean docIngresoSerieBean) {
        this.docIngresoSerieBean = docIngresoSerieBean;
    }

    public String getNumActual() {
        return numActual;
    }

    public void setNumActual(String numActual) {
        this.numActual = numActual;
    }

    public List<ImpresoraBean> getListaImpresora() {
        return listaImpresora;
    }

    public void setListaImpresora(List<ImpresoraBean> listaImpresora) {
        this.listaImpresora = listaImpresora;
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

    public ImpresoraCajaBean getImpresoraCajaBean() {
        if (impresoraCajaBean == null) {
            impresoraCajaBean = new ImpresoraCajaBean();
        }
        return impresoraCajaBean;
    }

    public void setImpresoraCajaBean(ImpresoraCajaBean impresoraCajaBean) {
        this.impresoraCajaBean = impresoraCajaBean;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public List<CodigoBean> getListaLugarPago() {
        return listaLugarPago;
    }

    public void setListaLugarPago(List<CodigoBean> listaLugarPago) {
        this.listaLugarPago = listaLugarPago;
    }

    public Map<String, Integer> getMapLugarPago() {
        return mapLugarPago;
    }

    public void setMapLugarPago(Map<String, Integer> mapLugarPago) {
        this.mapLugarPago = mapLugarPago;
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

    public Double getMontoTotalDouble() {
        return montoTotalDouble;
    }

    public void setMontoTotalDouble(Double montoTotalDouble) {
        this.montoTotalDouble = montoTotalDouble;
    }

    public Boolean validarConceptoPorAgregar(Integer mes, Integer anio, String idEstudiante) {
        Boolean valor = false;
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            System.out.println("mes" + mes);
            //add mes  
            if (this.addMes == null) {
                this.addMes = mes;
            }
            System.out.println("mes add" + addMes);

            Integer SgtePago = 0;
            if (listDetDocIngreso != null) {
                if (listDetDocIngreso.size() > 0) {
                    SgtePago = cuentasPorCobrarService.obtenerSgteMesPagoAft(idEstudiante, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, addMes);
                } else {
                    SgtePago = cuentasPorCobrarService.obtenerSgteMesPagoAft(idEstudiante, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, null);
                }
            } else {
                SgtePago = cuentasPorCobrarService.obtenerSgteMesPagoAft(idEstudiante, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, null);
            }
            System.out.println("sgte mes " + SgtePago);
            Integer dif = mes - SgtePago;
            System.out.println("dif " + dif);
            if (dif.equals(0)) {
                //pasa 
                if (mes <= 12) {
                    this.addMes = cuentasPorCobrarService.obtenerSgteMesPagoBef(idEstudiante, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, SgtePago);
                    System.out.println("sgte mes " + addMes);
                }
                valor = true;
                System.out.println("pasó");
            } else {
                //mes incorrecto
                valor = false;
                this.addMes = null;
                System.out.println("incorrecto");
            }
            System.out.println(valor);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public Boolean validarConceptoPorAgregarPrimerPago(Integer mes, Integer anio, String idEstudiante) {
        Boolean valor = false;

        try {
            for (DetDocIngresoBean detalle : listDetDocIngreso) {
                if (Objects.equals(detalle.getCuentasPorCobrarBean().getMes(), mes) && Objects.equals(detalle.getCuentasPorCobrarBean().getAnio(), anio)) {
                    valor = true;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return valor;
    }

    public void obtenerCrPorGrado(Integer idTipo) {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            getListaCentroResponsabilidadBean();
            String nivel = null;
            Integer cr2 = 311;
            if (idTipo.equals(103)) {
                nivel = MaristaConstantes.COD_CR_TALLER;
                listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(nivel);
//511	TALLER-Dir.Académico-Academico
//512	TALLER-Dir.Académico-Cultural
//513	TALLER-Dir.Académico-Deporte
                this.cr = 513;
            } else {

                EstudianteBean estudiante = new EstudianteBean();
                estudiante.getPersonaBean().setIdPersona(personaBean.getIdPersona());
                estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                estudiante = estudianteService.obtenerEstudianteGradoAca(estudiante);
                if (estudiante != null) {
                    if (estudiante.getNivelAcademico() != null) {
                        switch (estudiante.getNivelAcademico()) {
                            case MaristaConstantes.NIV_ACA_INI:
                                nivel = MaristaConstantes.COD_CR_INI;
                                cr2 = 111;
                                break;
                            case MaristaConstantes.NIV_ACA_PRI:
                                nivel = MaristaConstantes.COD_CR_PRI;
                                cr2 = 211;
                                break;
                            case MaristaConstantes.NIV_ACA_SEC:
                                if (estudiante.getGradoHabilitado().getNombre().equals(MaristaConstantes.Cuarto_Bach_Secundaria)
                                        || estudiante.getGradoHabilitado().getNombre().equals(MaristaConstantes.Quinto_Bach_Secundaria)) {
                                    nivel = MaristaConstantes.COD_CR_BACH;
                                    cr2 = 400;
                                } else {
                                    nivel = MaristaConstantes.COD_CR_SEC;
                                    cr2 = 311;
                                }
                                break;
                        }
                    }
                    listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCrPorNivelAcademico(nivel);
                    this.cr = cr2;
                } else {
                    listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();
                    this.cr = 311;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCrPorGradoBach() {
        try {
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResponsabilidadBean = new ArrayList<>();
            listaCentroResponsabilidadBean = centroResponsabilidadService.obtenerCentroResponsabilidad();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCr() {
        try {
            System.out.println(this.getCr());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String getMontoTotal() {
        System.out.println("getMontoTotal()");
        Double total = 0.0;
        String tot = "";
        String sol = "S/. ";
        try {
            for (DetDocIngresoBean det : getListDetDocIngreso()) {
//                System.out.println(">>id " + det.getProgramacionBean().getIdProgramacion() + " dscto:" + det.getDscto() + " monto:" + det.getMontoPagado());
            }

            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            docIngresoBean.setTipoCambioBean(tipoCambio);
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigo.obtenerPorId(docIngresoBean.getIdTipoMoneda());
            for (DetDocIngresoBean detDocIngresoBean : getListDetDocIngreso()) {
//                obtenerDscto();
                total += detDocIngresoBean.getMontoPagado().doubleValue();
            }
            if (cod != null) {
                switch (cod.getCodigo()) {
                    case "Soles":
                        if (getTipoMoneda() != null) {
                            switch (getTipoMoneda().getCodigo()) {
                                case "Soles":
                                    montoTotalDouble = total;
                                    tot = new DecimalFormat("#,##0.00").format(total);
                                    this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                    break;
                                case "Dolares":
                                    montoTotalDouble = (double) Math.round((total / tipoCambio.getTcVenta().doubleValue()) * 100) / 100;
                                    total = montoTotalDouble;
                                    tot = new DecimalFormat("#,##0.00").format(total);
                                    this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                    break;
                            }
                            break;
                        }
                    case "Dolares":
                        if (getTipoMoneda() != null) {
                            switch (getTipoMoneda().getCodigo()) {
                                case "Soles":
                                    montoTotalDouble = (double) Math.round((total * tipoCambio.getTcVenta().doubleValue()) * 100) / 100;
                                    total = montoTotalDouble;
                                    tot = new DecimalFormat("#,##0.00").format(total);
                                    this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                    break;
                                case "Dolares":
                                    montoTotalDouble = total;
                                    tot = new DecimalFormat("#,##0.00").format(total);
                                    this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                    break;
                            }
                        }
                        break;
                }
            } else {
                montoTotalDouble = total;
                tot = new DecimalFormat("#,##0.00").format(total);
                montoTotal = sol.concat(tot);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return montoTotal;
    }

    public void limpiarDocIngreso() {
        try {
            personaFiltroBean = new PersonaBean();
            listaCuentasPorCobrarBean = new ArrayList<>();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            personaBean = new PersonaBean();
            listaConceptoUniNegBean = new ArrayList<>();
            tipoDiscenteBean = new TipoDiscenteBean();
            listaTipoConceptoBean = new ArrayList<>();

            //settear monto a cero de los conceptos
            //10708	DEVOLUCION ADELANTO A RENDIR
            //10707	OTRS - BANCO DE LIBROS
            //10709	OTRS - VENTAS VARIAS
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            if (!listDetDocIngreso.isEmpty()) {
                if (flgSerGen.equals(Boolean.TRUE)) {
                    for (DetDocIngresoBean lista : listDetDocIngreso) {
                        System.out.println("id:" + lista.getConceptoBean().getIdConcepto());
                        Integer estado = 0;
                        estado = conceptoUniNegService.obtenerPorIdConceptoMontoCero(lista.getConceptoBean().getIdConcepto(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        if (estado.equals(1)) {
                            cambiarMontoAcero(lista.getConceptoBean().getIdConcepto());
                        }
                    }
                }
            }

            listDetDocIngreso = new ArrayList<>();

            listaConceptos = new ArrayList<>();
            docIngresoSerieBean = new DocIngresoSerieBean();
            numActual = new String();
            listaTipoDocumento = new ArrayList<>();
            mapTipoDocumento = new HashMap<>();
            docIngresoBean = new DocIngresoBean();
            impresoraCajaBean = new ImpresoraCajaBean();
            listaFamiliar = new ArrayList<>();
            mapFamiliar = new HashMap<>();
            montoTotalDouble = new Double("0.00");
            montoTotalDolDouble = new Double("0.00");
            montoTotalSolDouble = new Double("0.00");
            montoTotalPos1Double = new Double("0.00");
            montoTotalPos2Double = new Double("0.00");
            montoParcialDol = "";
            setCr(null);
            listaCentroResponsabilidadBean = new ArrayList<>();
            if (listaImpresora.size() >= 1) {
                getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
                obtenerTipDoc();
            }
            listaProgramacionSessionBean = new ArrayList<>();
//        obtenerProgramacionActivos();
            this.setFlgModPOS(false);
            this.setFlgModPagoAmbos(false);
            this.setFlgPagoParDol(false);
            this.setFlgPension(false);
            this.setFlgSerGen(false);
            this.setPosVisa(false);
            this.setPosMC(false);
            DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmSerGen:tblSerGen");
            if (!dataTable.getFilters().isEmpty()) {
                dataTable.reset();
            }
            cargarConceptosDefaultPersonaExterna();
//            setFlgGenCod(Boolean.TRUE);
            generarCodigoPersona();
            setFlgDisableGenCod(Boolean.FALSE);
            this.flgBtnImprimir = Boolean.TRUE;

            if (this.flgCajExt.equals(Boolean.TRUE)) {
                this.flgSoloEst = Boolean.FALSE;
            } else {
                this.flgSoloEst = Boolean.TRUE;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public void buscarPersonal() {
        try {
            listaPersonaBean = new ArrayList<>();
            PersonaService personaService = BeanFactory.getPersonaService();
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            personaFiltroBean = new PersonaBean();
//            personaFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            listaPersonaBean = docIngresoService.buscarPersona(personaFiltroBean);
            listaPersonaBean = personaService.obtenerTop10Persona(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentasPorCobrarBean = new ArrayList<>();
            listaConceptoUniNegBean = new ArrayList<>();
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

    public void buscarPersona() {
        try {
            listaPersonaBean = new ArrayList<>();
            PersonaService personaService = BeanFactory.getPersonaService();
            personaFiltroBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            System.out.println("0.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("0.-" + personaFiltroBean.getIdPersona());
            if (personaFiltroBean.getIdPersona() != null && !personaFiltroBean.getIdPersona().trim().equals("")) {
                personaFiltroBean.setIdPersona(personaFiltroBean.getIdPersona());
            } else {
                personaFiltroBean.setIdPersona(null);
            }
            if (personaFiltroBean.getNombreFiltro() != null && !personaFiltroBean.getNombreFiltro().trim().equals("")) {
                personaFiltroBean.setNombreFiltro(personaFiltroBean.getNombreFiltro());
            } else {
                personaFiltroBean.setNombreFiltro(null);
            }
//            System.out.println("1.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("1.-" + personaFuiltroBean.getIdPersona());
            Integer flg = 1;
            System.out.println(flgSoloEst);
            if (flgSoloEst.equals(Boolean.TRUE)) {
                flg = 1;
            } else {
                flg = 0;
            }
            getPersonaFiltroBean().setFiltro(flg);
            listaPersonaBean = personaService.SP_obtenerPersonaPorFiltro(personaFiltroBean);
//            System.out.println("2.-" + personaFiltroBean.getNombreFiltro());
//            System.out.println("2.-" + personaFiltroBean.getIdPersona());
            if (personaFiltroBean.getIdPersona() == null && personaFiltroBean.getNombreFiltro() == null) {
                listaPersonaBean = personaService.obtenerTop10Persona(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            listaCuentasPorCobrarBean = new ArrayList<>();
//            listaConceptoUniNegBean = new ArrayList<>();
            personaBean = new PersonaBean();
            personaBean.setColl(false);
            if (listaPersonaBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                personaBean.setColl(true);
                listaPersonaBean = new ArrayList<>();
                getPersonaBean().setColl(Boolean.TRUE);
                getPersonaBean();
                setFlgGenCod(Boolean.TRUE);
                generarCodigoPersona();

            }
            if (listaConceptoUniNegBean.isEmpty()) {
                obtenerDsctoTipoDiscenteVer2(null);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            anio = Integer.parseInt(date);

            String rutaFoto = "";
//            FamiliarService familiarService = BeanFactory.getFamiliarService(); 
            PersonaService personaService = BeanFactory.getPersonaService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            personaBean = (PersonaBean) event.getObject();
//            System.out.println(">>>>" + personaBean.getEstadoPersona());
//            if (personaBean.getEstado().equals("Bloqueado")) {
//                new MensajePrime().addInformativeMessagePer("msjAlumnoBloq");
//            }
            anio = cuentasPorCobrarService.obtenerAnioDeuda(getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null);
            rutaFoto = personaService.obtenerFoto(getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("f/" + rutaFoto);
            personaBean.setFoto(rutaFoto);
//            personaBean = personaService.obtenerPersPorId(personaBean);
            personaBean.setColl(true);
            this.flgDisableGenCod = Boolean.TRUE;
            this.addMes = null;
            tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(getPersonaBean().getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            obtenerDsctoTipoDiscenteVer2(personaBean.getIdPersona());
//            obtenerDsctoTipoDiscente(personaBean.getIdPersona());
            listDetDocIngreso = new ArrayList<>();
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();

            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Colegio", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "POS", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            } else {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Efectivo", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            }
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));

            docIngresoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            docIngresoBean.setIdTipoModoPago(codigoTipoModoPago);
            docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);

            this.setFlgPagoParDol(false);
            resetearMontoDol();
            listaCentroResponsabilidadBean = new ArrayList<>();
            personalBean = new PersonalBean();
            bloquearDolares();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarConceptosDefaultPersonaExterna() {
        try {
            System.out.println("cargarConceptosDefaultPersonaExterna");
            String rutaFoto = "";
//            PersonaService personaService = BeanFactory.getPersonaService();
            CodigoService codigoService = BeanFactory.getCodigoService();
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            getPersonaBean().setIdPersona(" ");
            getPersonaBean();
            setFlgGenCod(Boolean.TRUE);
            generarCodigoPersona();
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                getPersonaBean().setFoto(MaristaConstantes.NO_FOTO_CHAMPS);
            } else if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                getPersonaBean().setFoto(MaristaConstantes.NO_FOTO_BARINA);
            } else {
                getPersonaBean().setFoto(MaristaConstantes.NO_FOTO_BARINA);
            }

            getPersonaBean().setColl(true);
            tipoDiscenteBean = new TipoDiscenteBean();
            this.addMes = null;
            getTipoDiscenteBean().setFlgExt(Boolean.TRUE);
            getTipoDiscenteBean().setFlgAlu(Boolean.FALSE);
            getTipoDiscenteBean().setFlgExa(Boolean.FALSE);
            getTipoDiscenteBean().setFlgHem(Boolean.FALSE);
            getTipoDiscenteBean().setFlgHex(Boolean.FALSE);
            obtenerDsctoTipoDiscenteVer2(null);
//            obtenerDsctoTipoDiscente(null);

            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();
            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Colegio", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "POS", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            } else {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Efectivo", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            }
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));

            docIngresoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            docIngresoBean.setIdTipoModoPago(codigoTipoModoPago);
            docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);

            this.setFlgPagoParDol(false);
            resetearMontoDol();
            listDetDocIngreso = new ArrayList<>();
            listaCentroResponsabilidadBean = new ArrayList<>();
            personalBean = new PersonalBean();
            bloquearDolares();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void settearMoneda(String mon) {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoMoneda = new CodigoBean();
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, mon, new TipoCodigoBean(MaristaConstantes.TIP_MON)));
            docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);
            if (getDocIngresoBean().getIdTipoMoneda().getCodigo().equals("Dolares")) {
                CodigoBean codigoModoPago = new CodigoBean();
                codigoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Efectivo", new TipoCodigoBean(MaristaConstantes.TIP_MODOPAGO)));
                docIngresoBean.setIdTipoModoPago(codigoModoPago);
            }
            mostrarModAmbos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void rowSelectObject(Object persona) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            anio = Integer.parseInt(date);
            CodigoService codigoService = BeanFactory.getCodigoService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            personaBean = (PersonaBean) persona;
            anio = cuentasPorCobrarService.obtenerAnioDeuda(getPersonaBean().getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null);

            personaBean.setColl(true);
            tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(personaBean.getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            obtenerDsctoTipoDiscenteVer2(personaBean.getIdPersona());
//            obtenerDsctoTipoDiscente(personaBean.getIdPersona());
            listDetDocIngreso = new ArrayList<>();
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();

            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Colegio", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "POS", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            } else {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Efectivo", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            }
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));
            this.addMes = null;
            docIngresoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            docIngresoBean.setIdTipoModoPago(codigoTipoModoPago);
            docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);

            this.setFlgPagoParDol(false);
            resetearMontoDol();
            listaCentroResponsabilidadBean = new ArrayList<>();
            personalBean = new PersonalBean();
            bloquearDolares();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void agregarConcepto(Integer idConcepto) {
        try {
            if (listaImpresora.size() >= 1) {
                getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
                obtenerTipDoc();
            }
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            ConceptoUniNegBean con = new ConceptoUniNegBean();
            con.getConceptoBean().setIdConcepto(idConcepto);
            con.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            con = conceptoUniNegService.obtenerConceptoPorId(con);
            if (con.getConceptoBean().getNombre().toUpperCase().equals("DEVOLUCION ADELANTO A RENDIR M.N.")
                    || con.getConceptoBean().getNombre().toUpperCase().equals("DEVOLUCION ADELANTO A RENDIR M.E.")) {
                con.getConceptoBean().setNombre(MaristaConstantes.CON_DEV_ARENDIR_MAYUS);
            }
            if (con.getImporte().doubleValue() == 0 && !con.getConceptoBean().getNombre().toUpperCase().equals(MaristaConstantes.CON_DEV_ARENDIR_MAYUS)) {
                new MensajePrime().addInformativeMessagePer("msjSerGenImpCero");
                System.out.println("cero -" + con.getConceptoBean().getNombre());
            } else {
                System.out.println("!cero");
                Integer id = 0;
                id = conceptoUniNegService.obtenerTipoPorProgramacion(idConcepto, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (id.equals(1)) {
                    RequestContext.getCurrentInstance().addCallbackParam("abrirProgramcion", true);
                    obtenerProgramacionActivos(con.getConceptoBean().getIdConcepto());
                    if (listaProgramacionBean.size() == 1 && listaProgramacionBean.get(0).getCupos() > 0) {
                        listaProgramacionBean.get(0).setFlgInscrito(Boolean.TRUE);
                        obtenerProgramacionRegistro();
                    } else {
                        System.out.println("listaProgramacionBean.size()>1");
                    }
                }
                if (con.getConceptoBean().getIdConcepto().equals(10708) || con.getConceptoBean().getIdConcepto().equals(10722)) {
                    obtenerTipDocComprobante();
                }
                this.flgSerGen = true;
                SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
                String dia = fecha.format(new Date());
                ConceptoUniNegBean concepto = new ConceptoUniNegBean();
                DetDocIngresoBean detalle = new DetDocIngresoBean();
                concepto.getConceptoBean().setIdConcepto(idConcepto);
                concepto.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                tipoMoneda = new CodigoBean();
                if (getTipoDiscenteBean().getFlgAlu()) {
                    Object dscto = MaristaConstantes.DSCTO_ALU;
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoAlumno().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO  
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                } else if (getTipoDiscenteBean().getFlgExa()) {
                    Object dscto = MaristaConstantes.DSCTO_EX_ALU;
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoExAlumno().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                } else if (getTipoDiscenteBean().getFlgExt()) {
                    Object dscto = MaristaConstantes.DSCTO_EXT;
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoExterno().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO 
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                } else if (getTipoDiscenteBean().getFlgHem()) {
                    Object dscto = MaristaConstantes.DSCTO_EMP;
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoEmpleado().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO 
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                } else if (getTipoDiscenteBean().getFlgHex()) {
                    Object dscto = MaristaConstantes.DSCTO_EX_ALU;
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoExAlumno().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO 
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                } else if (tipIndividuo == 2) {
                    Object dscto = MaristaConstantes.DSCTO_EMP;
                    concepto = conceptoUniNegService.obtenerConceptoPorId(concepto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    docIngresoBean.setIdTipoMoneda(tipoMoneda);
                    settearMoneda(tipoMoneda.getCodigo());
                    ConceptoService service = BeanFactory.getConceptoService();
                    ConceptoBean conceptoB = new ConceptoBean();
                    conceptoB.setIdConcepto(MaristaConstantes.COD_CONCEPTO_DEV_AREN);
                    conceptoB = service.obtenerConceptoPorId(conceptoB);
                    concepto = conceptoUniNegService.obtenerConceptoPorIdConDscto(concepto, dscto);
                    tipoMoneda = concepto.getIdTipoMoneda();
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                        docIngresoBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
                    } else {
                        docIngresoBean.setIdTipoMoneda(tipoMoneda);
                        settearMoneda(tipoMoneda.getCodigo());
                    }
                    detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    detalle.setDocIngresoBean(docIngresoBean);
                    detalle.setConceptoBean(concepto.getConceptoBean());
                    detalle.setMonto(concepto.getImporte());
                    Double dsctoIm = concepto.getImporte().doubleValue() - concepto.getImporteConDscto().doubleValue();
//                Double dsctoIm = concepto.getImporte().doubleValue() * (concepto.getDsctoExterno().doubleValue()) / 100;
                    detalle.setDsctoTipoDicente(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDscto(BigDecimal.valueOf(dsctoIm));//concepto.getDsctoTipoPersona
                    detalle.setDsctoBeca(new BigDecimal("0.00"));// 
                    detalle.setMontoConDscto(concepto.getImporteConDscto());// 
                    detalle.setMontoPagado(concepto.getImporteConDscto());//concepto.getDsctoTipoPersona   RESTA DE MONTO CON DSCTO 
                    detalle.setIdTipoDscto(null);
                    detalle.setIdTipoMotivoDscto(null);
                    detalle.setCuentaD(concepto.getConceptoBean().getPlanContableCuentaDBean());
                    detalle.setCuentaH(concepto.getConceptoBean().getPlanContableCuentaHBean());
                    detalle.setCentroResponsabilidadBean(concepto.getConceptoBean().getCr());
                    detalle.setCreaPor(usuarioLoginBean.getUsuario());
                    detalle.setCreaFecha(fecha.parse(dia));
                    detalle.setReferencia(concepto.getConceptoBean().getNombre());
                }
                if (id.equals(0)) {
                    listDetDocIngreso.add(detalle);
                    obtenerCrPorGrado(con.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                } else {
                    listDetDocIngreso.add(detalle);
                    obtenerCrPorGrado(con.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostrarDetaConcepto(Integer idConcepto) {
        try {
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(idConcepto);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void settearMontoDevolucion(Integer id) {
        try {
            System.out.println("id: " + id + " mo " + tipoMoneda.getCodigo());
            if (id != null) {
                if (!listDetDocIngreso.isEmpty()) {
                    Integer size = listDetDocIngreso.size();
                    if (size.equals(1)) {
                        SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                        SolicitudCajaCHBean soli = new SolicitudCajaCHBean();
                        soli = solicitudCajaCHService.obtenerPorTipoEstRendPorId(id, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        System.out.println("entró id");
                        Double montoDev = soli.getDiferenciaAdevolver();
                        double rounded = (double) Math.round(montoDev * 100) / 100;
                        System.out.println("montooo " + rounded);
                        if (tipoMoneda.getCodigo().equals("Soles")) {
                            listDetDocIngreso.get(0).setMonto(BigDecimal.valueOf((rounded)));
                            listDetDocIngreso.get(0).setMontoPagado(BigDecimal.valueOf((rounded)));
                        }
                        if (tipoMoneda.getCodigo().equals("Dolares")) {
                            listDetDocIngreso.get(0).setMonto(BigDecimal.valueOf((rounded)));
                            listDetDocIngreso.get(0).setMontoPagado(BigDecimal.valueOf((rounded)));
                        }
//                        System.out.println("getMonto<<<<<<"+listDetDocIngreso.get(0).getMonto());
//                        System.out.println("getMontoPagado<<<<<"+listDetDocIngreso.get(0).getMontoPagado());
                        listDetDocIngreso.get(0).setReferencia(soli.getReferencia());
                    }
                    obtenerCambio();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void agregarCtaXCobrar(Integer idCtasXCobrar) {
        try {
//            if(listDetDocIngreso!=null){
//            }
            this.flgPension = true;
            SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
            String dia = fecha.format(new Date());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean ctas = new CuentasPorCobrarBean();
            ctas = cuentasPorCobrarService.obtenerCuentaPorId(idCtasXCobrar, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            DetDocIngresoBean detalle = new DetDocIngresoBean();
            detalle.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            detalle.setDocIngresoBean(docIngresoBean);
            detalle.setDocIngresoBean(ctas.getDocIngresoBean());
            detalle.setConceptoBean(ctas.getConceptoBean());
            if (ctas.getMes() != null) {
                if (ctas.getMes() != 2) {
                    if (detalle.getReferencia() == null) {
                        detalle.setReferencia(ctas.getConceptoBean().getNombre().concat(" ").concat(ctas.getNomMes()).concat(" ").concat(ctas.getAnio().toString()));
                    }
                } else {
                    if (detalle.getReferencia() == null) {
                        detalle.setReferencia("PAGO POR MATRÍCULA".concat(" ").concat(ctas.getAnio().toString()));
                    }
                }
            } else {
                detalle.setReferencia(ctas.getConceptoBean().getNombre());
            }
            detalle.getConceptoBean().setNombre(ctas.getConceptoBean().getNombre().concat(" ").concat(ctas.getNomMes()));
            detalle.setMonto(ctas.getMonto());
            detalle.setDscto(ctas.getDscto());
            detalle.setMora(ctas.getMora());
            detalle.getCuentasPorCobrarBean().setIdCtasXCobrar(ctas.getIdCtasXCobrar());
            detalle.setIdCtasXCobrar(ctas.getIdCtasXCobrar());
            detalle.setMes(ctas.getMes());
            detalle.setDsctoBeca(ctas.getDsctoBeca());
//            detalle.setMontoPagado(ctas.getMonto().add(ctas.getMora()).subtract(ctas.getDsctoBeca()));//monto + mora - dsctobeca
            detalle.setMontoConDscto(ctas.getMonto().add(ctas.getMora()).subtract(ctas.getDsctoBeca()).subtract(ctas.getDscto()));
            detalle.setMontoPagado(ctas.getMonto().add(ctas.getMora()).subtract(ctas.getDsctoBeca()).subtract(ctas.getDscto()));//monto + mora - dsctobeca - scto (mora o penssion)
            detalle.setIdTipoDscto(ctas.getIdTipoDscto());
            detalle.setIdTipoMotivoDscto(ctas.getIdTipoMotivoDscto());
            detalle.setCuentaD(ctas.getCuentaD());
            detalle.setCuentaH(ctas.getCuentaH());
            detalle.setCentroResponsabilidadBean(ctas.getCentroResponsabilidadBean());
            detalle.setCuentasPorCobrarBean(ctas);
            detalle.setCreaPor(usuarioLoginBean.getUsuario());
            detalle.setCreaFecha(fecha.parse(dia));
            docIngresoBean.setCuentasPorCobrarBean(ctas);
            System.out.println("validarConceptoPorAgregar 2" + ctas.getDocIngresoBean().getIdDocIngreso());
            if (!usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("BARINA")) {
                if (validarConceptoPorAgregar(ctas.getMes(), ctas.getAnio(), ctas.getEstudianteBean().getIdEstudiante())) {
                    listDetDocIngreso.add(detalle);
                    if (ctas.getDocIngresoBean().getIdDocIngreso() == null) {
                        System.out.println("mes 2    obtenerTipImpresoraParaPensiones();");
                        obtenerTipImpresoraParaPensiones();
                    }
                    System.out.println("ingresó");
                }
            } else {
                listDetDocIngreso.add(detalle);
            }

            tipoMoneda = new CodigoBean();
            getTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
            getTipoMoneda().setCodigo(MaristaConstantes.PAGO_MON_SOL);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostrarModAmbos() {
        try {
            resetearMontoDol();
            if (getDocIngresoBean().getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS) || docIngresoBean.getIdTipoMoneda().getCodigo().equals(MaristaConstantes.COD_SOL_DOL)) {
                this.flgModPagoAmbos = true;
            } else {
                this.flgModPagoAmbos = false;
            }
            if (getDocIngresoBean().getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                this.flgModPOS = true;
                this.flgPagoParDol = false;
            } else {
                this.flgModPOS = false;
            }
            bloquearDolares();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void bloquearDolares() {
        try {
//            if (docIngresoBean.getIdTipoModoPago().getCodigo().equals(MaristaConstantes.COD_POS)) {
//                CodigoService codigoService = BeanFactory.getCodigoService();
//                CodigoBean codigoTipoMoneda = new CodigoBean();
//                codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));
//                docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);
//                this.flgPagoParDol = false;
//            }
//            else{
//             
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void quitarConcepto(DetDocIngresoBean detalle) {
        try {
            if (detalle.getMes() != null) {
                Integer mesDel = 0;
                Integer fila = 0;
                fila = listDetDocIngreso.size() - 1;
                for (int i = 0; i < 1; i++) {
                    mesDel = listDetDocIngreso.get(fila).getMes();
//                    listaDocIngresoRep.get(i)
                }
//                System.out.println(mesDel);
                Integer dif = mesDel - detalle.getMes();
                if (dif.equals(0)) {
                    CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                    System.out.println("mes -1");
                    Integer SgtePago = 2;
                    SgtePago = cuentasPorCobrarService.obtenerSgteMesPagoAnterior(personaBean.getIdPersona(),
                            usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, detalle.getMes());
                    System.out.println(SgtePago);
                    this.addMes = SgtePago;
                    listDetDocIngreso.remove(detalle);
                } else {
                    System.out.println("error");

                }

            } else {
                listDetDocIngreso.remove(detalle);
            }

            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                System.out.println("ssssssssssssssssssss");
                if (listaProgramacionSessionBean != null) {
                    if (!listaProgramacionSessionBean.isEmpty()) {
                        for (ProgramacionBean lista : listaProgramacionSessionBean) {
                            System.out.println("1:" + lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto() + "-" + "2:" + detalle.getConceptoBean().getIdConcepto());
                            System.out.println("3:" + lista.getIdProgramacion() + "-" + "4:" + detalle.getProgramacionBean().getIdProgramacion());
                            if (detalle.getConceptoBean().getIdConcepto() != null
                                    && lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto().equals(detalle.getConceptoBean().getIdConcepto())
                                    && lista.getIdProgramacion().equals(detalle.getProgramacionBean().getIdProgramacion())) {
                                System.out.println("remove..." + lista.getDescripProgramacion());
                                listaProgramacionSessionBean.remove(lista);
                                break;
                            }
                        }
                        afterRemove();
                    }
                }
                if (listDetDocIngreso != null) {
                    if (listDetDocIngreso.isEmpty()) {
                        listaCentroResponsabilidadBean = new ArrayList<>();
                    }
                }
            } else {
                System.out.println("no champs");
                if (listaProgramacionSessionBean != null) {
                    if (!listaProgramacionSessionBean.isEmpty()) {
                        for (ProgramacionBean lista : listaProgramacionSessionBean) {
                            if (detalle.getConceptoBean().getIdConcepto() != null
                                    && lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto().equals(detalle.getConceptoBean().getIdConcepto())
                                    && lista.getIdProgramacion().equals(detalle.getProgramacionBean().getIdProgramacion())) {
                                listaProgramacionSessionBean.remove(lista);
                                break;
                            }
                        }
                        validarDescuentosTalleresFase1(null);
                    }
                }
                if (listDetDocIngreso != null) {
                    if (listDetDocIngreso.isEmpty()) {
                        listaCentroResponsabilidadBean = new ArrayList<>();
                    }
                }
            }
            obtenerProgramacionRegistroVer2();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void quitarUltimoConceptoProgramacion() {
        try {
            Integer size = listDetDocIngreso.size() - 1;
            System.out.println("size " + size);
            DetDocIngresoBean detalle = new DetDocIngresoBean();
            detalle = listDetDocIngreso.get(size);
            listDetDocIngreso.remove(detalle);

            if (listaProgramacionSessionBean != null) {
                if (!listaProgramacionSessionBean.isEmpty()) {
                    for (ProgramacionBean lista : listaProgramacionSessionBean) {
                        if (detalle.getConceptoBean().getIdConcepto() != null
                                && lista.getConceptoUniNegBean().getConceptoBean().getIdConcepto().equals(detalle.getConceptoBean().getIdConcepto())
                                && lista.getIdProgramacion().equals(detalle.getProgramacionBean().getIdProgramacion())) {
                            listaProgramacionSessionBean.remove(lista);
                            break;
                        }
                    }
                }
            }
            if (listDetDocIngreso != null) {
                if (listDetDocIngreso.isEmpty()) {
                    listaCentroResponsabilidadBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAnio() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(personaBean.getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            obtenerDsctoTipoDiscenteVer2(personaBean.getIdPersona());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            List<CuentasPorCobrarBean> list = new ArrayList<>();
            list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(personaBean.getIdPersona(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            listaCuentasPorCobrarBean = new ArrayList<>();
            for (CuentasPorCobrarBean ctas : list) {
                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                    ctas.setDscto(ctas.getDscto());
                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                    listaCuentasPorCobrarBean.add(ctas);
                } else {
                    listaCuentasPorCobrarBean.add(ctas);
                }
            }
//            obtenerDsctoTipoDiscente(personaBean.getIdPersona());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void update(ValueChangeEvent event) {
        anio = (Integer) event.getNewValue();
    }

    public void obtenerDetDocIngPorId(Object detalle) {
        try {
            DetDocIngresoBean detDocIngreso = new DetDocIngresoBean();
            detDocIngreso = (DetDocIngresoBean) detalle;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            detDocIngreso = docIngresoService.obtenerDetDocIngresoBeanPorId(detDocIngreso);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoTipoDiscente(String idPersona) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = anio.toString();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            UsuarioBean usuario = usuarioLoginBean;
//            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonaBean persona = new PersonaBean();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            listaCuentasPorCobrarBean = new ArrayList<>();
            this.serGenCollapsed = false;
            if (getTipoDiscenteBean().getFlgAlu()) {
                Object dscto = MaristaConstantes.DSCTO_ALU;
                persona = docIngresoService.buscarPersonaPostulante(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                if (persona != null) {
                    listaConceptoUniNegBean = new ArrayList<>();
                    listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoPost(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                } else {
                    persona = docIngresoService.buscarPersonaInscPostulante(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                    if (persona != null) {
                        listaConceptoUniNegBean = new ArrayList<>();
                        listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoInscr(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        //
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                list.get(0).setDisabled("true");
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
//                                    ctas.getIdTipoDscto().setCodigo("Beca ".concat(ctas.getEstudianteBecaBean().getBecaBean().getNombre()));//?
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                        //
                    } else {
                        persona = docIngresoService.buscarPersonaMatriculado(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                                    ctas.getIdTipoDscto().setCodigo("Beca ".concat(ctas.getEstudianteBecaBean().getBecaBean().getNombre()));
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                    }
                }
            } else if (getTipoDiscenteBean().getFlgExa()) {
                Object dscto = MaristaConstantes.DSCTO_EX_ALU;
                persona = docIngresoService.buscarPersonaPostulante(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                if (persona != null) {
                    listaConceptoUniNegBean = new ArrayList<>();
                    listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoPost(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                } else {
                    persona = docIngresoService.buscarPersonaInscPostulante(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                    if (persona != null) {
                        listaConceptoUniNegBean = new ArrayList<>();
                        listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoInscr(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                    } else {
                        persona = docIngresoService.buscarPersonaMatriculado(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                                    ctas.setIdTipoDscto(ctas.getEstudianteBecaBean().getBecaBean().getCodigoBean());
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                    }
                }
            } else if (getTipoDiscenteBean().getFlgExt()) {
                Object dscto = MaristaConstantes.DSCTO_EXT;
                persona = docIngresoService.buscarPersonaPostulante(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                if (persona != null) {
                    listaConceptoUniNegBean = new ArrayList<>();
                    listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoPost(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                } else {
                    persona = docIngresoService.buscarPersonaInscPostulante(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                    if (persona != null) {
                        listaConceptoUniNegBean = new ArrayList<>();
                        listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoInscr(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                    } else {
                        persona = docIngresoService.buscarPersonaMatriculado(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                                    ctas.setIdTipoDscto(ctas.getEstudianteBecaBean().getBecaBean().getCodigoBean());
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                    }
                }
            } else if (getTipoDiscenteBean().getFlgHem()) {
                Object dscto = MaristaConstantes.DSCTO_EMP;
                persona = docIngresoService.buscarPersonaPostulante(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                if (persona != null) {
                    listaConceptoUniNegBean = new ArrayList<>();
                    listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoPost(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                } else {
                    persona = docIngresoService.buscarPersonaInscPostulante(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                    if (persona != null) {
                        listaConceptoUniNegBean = new ArrayList<>();
                        listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoInscr(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                    } else {
                        persona = docIngresoService.buscarPersonaMatriculado(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                                    ctas.setIdTipoDscto(ctas.getEstudianteBecaBean().getBecaBean().getCodigoBean());
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                    }
                }
            } else if (getTipoDiscenteBean().getFlgHex()) {
                Object dscto = MaristaConstantes.DSCTO_EX_ALU;
                persona = docIngresoService.buscarPersonaPostulante(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                if (persona != null) {
                    listaConceptoUniNegBean = new ArrayList<>();
                    listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoPost(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                } else {
                    persona = docIngresoService.buscarPersonaInscPostulante(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                    if (persona != null) {
                        listaConceptoUniNegBean = new ArrayList<>();
                        listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoInscr(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                    } else {
                        persona = docIngresoService.buscarPersonaMatriculado(Integer.parseInt(date), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idPersona);
                        if (persona != null) {
                            List<CuentasPorCobrarBean> list = new ArrayList<>();
                            list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            if (list != null) {
                                if (list.isEmpty()) {
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                                } else {
//                                    new MensajePrime().addInformativeMessagePer("msjEstudianteDeudor");
                                    RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
                                }
                            } else {
                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                            }
                            for (CuentasPorCobrarBean ctas : list) {
                                if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                                    ctas.setIdTipoDscto(ctas.getEstudianteBecaBean().getBecaBean().getCodigoBean());
                                    ctas.setDscto(ctas.getDscto());
                                    ctas.setDsctoBeca(ctas.getDsctoBeca());
                                    listaCuentasPorCobrarBean.add(ctas);
                                } else {
                                    listaCuentasPorCobrarBean.add(ctas);
                                }
                            }
                            if (listaCuentasPorCobrarBean.isEmpty()) {
                                this.serGenCollapsed = false;
                            } else {
                                this.serGenCollapsed = true;
                            }
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoEstMatri(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        } else {
                            listaConceptoUniNegBean = new ArrayList<>();
                            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
                        }
                    }
                }
            }
            System.out.println(serGenCollapsed);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoTipoDiscenteVer2(String idPersona) {
        try {
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("obtenerDsctoTipoDiscenteVer2");
//            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
//            String date = anio.toString();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            listaCuentasPorCobrarBean = new ArrayList<>();
            this.serGenCollapsed = false;
            //new
            Object dscto = null;
            if (idPersona == null || idPersona.equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
                getTipoDiscenteBean().setFlgAlu(Boolean.FALSE);
                getTipoDiscenteBean().setFlgExa(Boolean.FALSE);
                getTipoDiscenteBean().setFlgExt(Boolean.TRUE);
                getTipoDiscenteBean().setFlgHem(Boolean.FALSE);
                getTipoDiscenteBean().setFlgHex(Boolean.FALSE);
////                    tipoDiscenteBean = docIngresoService.obtenerTipoDiscente(personaBean.getIdPersona(), anio, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
            if (getTipoDiscenteBean() != null) {
                if (getTipoDiscenteBean().getFlgAlu()) {
                    dscto = MaristaConstantes.DSCTO_ALU;
                } else if (getTipoDiscenteBean().getFlgExa()) {
                    dscto = MaristaConstantes.DSCTO_EX_ALU;
                } else if (getTipoDiscenteBean().getFlgExt()) {
                    dscto = MaristaConstantes.DSCTO_EXT;
                } else if (getTipoDiscenteBean().getFlgHem()) {
                    dscto = MaristaConstantes.DSCTO_EMP;
                } else if (getTipoDiscenteBean().getFlgHex()) {
                    dscto = MaristaConstantes.DSCTO_EX_ALU;
                } else {
                    dscto = MaristaConstantes.DSCTO_EXT;
                }
            }

            if (personaBean.getEstadoPersona() != null) {
                System.out.println(">>> entro bloqueado");
                if (personaBean.getEstadoPersona().equals(MaristaConstantes.EST_BLOQUEO)) {
                    personaBean.setColl(false);
                    setDisableCuenta(true);
                    new MensajePrime().errorEstudianteBloqueo();
                }
            }
            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String date = formato.format(new Date());
            anio = Integer.parseInt(date);

            if (idPersona != null && !getTipoDiscenteBean().getFlgExt().equals(Boolean.TRUE)) {
                System.out.println("no es externo");
//                Integer mes = 0;
//                Date fecha = new Date();
//                mes = fecha.getMonth() + 1;
//                System.out.println("mes " + mes);
                anio = cuentasPorCobrarService.obtenerAnioDeuda(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null);
                System.out.println("anio..." + date);
                List<CuentasPorCobrarBean> list = new ArrayList<>();
                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//                list = cuentasPorCobrarService.obtenerDeudaEstudiantePorAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);

                if (list != null) {
                    if (list.size() > 0) {
                        System.out.println("siz " + list.size());
                        if (list.isEmpty()) {
                            System.out.println("if (list.isEmpty()) ");
//                            list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
                        } else {
                            RequestContext.getCurrentInstance().addCallbackParam("operacionDeuda", true);
//                            if (mes.equals(12)) {
//                                list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//                            } else {
//                                if (list2.size() > 0) {
//                                    System.out.println("if (list2.size() > 0)");
//                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio - 1);
//                                    anio = anio - 1;
//                                } else {
//                                    System.out.println("else...");
//                                    list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//                                }
//                            }
                        }
                    } else {
//                        if (mes.equals(12)) {
//                            list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio + 1);
//                            anio = anio + 1;
//                        } else {
//                            list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//                        }
                    }
                    for (CuentasPorCobrarBean ctas : list) {
                        if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                            ctas.setDscto(ctas.getDscto());
                            ctas.setDsctoBeca(ctas.getDsctoBeca());
                            listaCuentasPorCobrarBean.add(ctas);
                        } else {
                            listaCuentasPorCobrarBean.add(ctas);
                        }
                    }
                } else {
//                    if (mes.equals(12)) {
//                        list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio + 1);
//                        anio = anio + 1;
//                    } else {
//                        list = cuentasPorCobrarService.obtenerCuentaPorMatAnio(idPersona, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
//                    }
                    for (CuentasPorCobrarBean ctas : list) {
                        if (personaBean.getEstadoPersona() != null) {
                            System.out.println(">>> entro bloqueado");
                            if (personaBean.getEstadoPersona().equals(MaristaConstantes.EST_BLOQUEO)) {
                                ctas.setDisabled("true");
                            }
                        }
                        if (ctas.getEstudianteBecaBean().getIdEstudianteBeca() != null) {
                            ctas.setDscto(ctas.getDscto());
                            ctas.setDsctoBeca(ctas.getDsctoBeca());
                            listaCuentasPorCobrarBean.add(ctas);
                        } else {
                            listaCuentasPorCobrarBean.add(ctas);
                        }
                    }
                    personaBean.setColl(false);
                }
                if (listaCuentasPorCobrarBean.isEmpty()) {
                    this.serGenCollapsed = Boolean.FALSE;
                } else {
                    this.serGenCollapsed = Boolean.TRUE;
                }
            } else {
                listaCuentasPorCobrarBean = new ArrayList<>();
                this.serGenCollapsed = Boolean.TRUE;
            }
            listaConceptoUniNegBean = new ArrayList<>();
            System.out.println("flgCajExt" + flgCajExt);
            if (this.flgCajExt.equals(Boolean.FALSE)) {
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
            } else if (this.flgCajExt.equals(Boolean.TRUE)) {
                System.out.println("ext...");
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoConProgramacion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
            } else {
                listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoExterno(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
            }
            System.out.println("size lista..." + listaConceptoUniNegBean.size());
//fin new 

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String autenticarCajero() {
        String pagina = null;
        try {
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            cajeroCajaBean.setUnidadNegocioBean(usuario.getPersonalBean().getUnidadNegocioBean());
            cajeroCajaBean.setUsuarioBean(usuario);
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
            listaImpresora = new ArrayList<>();
            if (cajeroCajaBean != null) {
                SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
                String dateCompleto = formatoDiaCompleto.format(new Date());
                getCajaGenBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean());
                getCajaGenBean().setCajaBean(cajeroCajaBean.getCajaBean());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                getCajaGenBean().setAnio(Integer.parseInt(date));
                getCajaGenBean().setUsuarioBean(usuario);
                getCajaGenBean().setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGenBean.getTipoCajaGen().getTipoCodigoBean().setDescripcion(MaristaConstantes.COD_CAJA_GEN_COL);
                cajaGenBean.getTipoCajaGen().setCodigo(MaristaConstantes.TIP_STATUS_CAJA_GEN);
                cajaGeneral = cajaGenService.verificarApertura(cajaGenBean);
                if (cajaGeneral != null) {
//                    System.out.println("idCajaGen: " + cajaGeneral.getIdCajaGen());
                    cajaGenBean = cajaGeneral;
                    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");
                    String dia = fecha.format(new Date());
                    String diaTC = fecha.format(cajaGeneral.getFecApertura());
                    if (!dia.equals(diaTC)) {
                        System.out.println(diaTC);
                        System.out.println(dia);
                        new MensajePrime().addInformativeMessagePer("msjCajaGenDiaAnterior");
                    }
                }
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
                    listaImpresora = docIngresoService.obtenerImpresoraCajero(cajeroCajaBean);
                    System.out.println("size" + listaImpresora.size());
                    if (listaImpresora.size() >= 1) {
                        getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
                        obtenerTipDoc();
                    }
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

    public String cargarDatosCobranza(Integer tipo) {
        String pagina = null;
        try {
            if (tipo.equals(0)) {
                this.flgCajExt = Boolean.FALSE;
            } else {
                this.flgCajExt = Boolean.TRUE;
            }
            if (this.flgCajExt.equals(Boolean.TRUE)) {
                this.flgSoloEst = Boolean.FALSE;
            } else {
                this.flgSoloEst = Boolean.TRUE;
            }
            System.out.println("flg->" + flgCajExt);
            autenticarCajero();
            buscarPersonal();
            obtenerTipoCambio();
            changeFlgEst();
            cargarConceptosDefaultPersonaExterna();
            System.out.println("tipo..." + tipo);
            docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
            pagina = null;
        }
        return pagina;
    }

    public void obtenerTipDoc() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = docIngresoService.obtenerTipDocumentoPorImpresora(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBean.getCajaBean().getIdCaja());
            CodigoBean codigoTipoDoc = new CodigoBean();
            for (CodigoBean listaTipo : listaTipoDocumento) {
                if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_RECIBO)) {
                    codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_RECIBO, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                    break;
                } else {
                    if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_BOLETA)) {
                        codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_BOLETA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                        break;
                    } else {
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_FACTURA)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_FACTURA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        }
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_COMPROBANTE)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_COMPROBANTE, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        }
                    }
                }
            }

            if (codigoTipoDoc.getIdCodigo() != null) {
                docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((codigoTipoDoc));
            } else {
                if (listaImpresora.size() >= 1) {
                    if (!listaTipoDocumento.isEmpty()) {
                        getDocIngresoBean().getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((listaTipoDocumento.get(0)));
                    }
                }
            }
            obtenerDetalleDoc();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipDocComprobante() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = docIngresoService.obtenerTipDocumentoPorImpresoraComprobante(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBean.getCajaBean().getIdCaja(), MaristaConstantes.COD_DOC_COMPROBANTE);
            CodigoBean codigoTipoDoc = new CodigoBean();
            for (CodigoBean listaTipo : listaTipoDocumento) {
                if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_COMPROBANTE)) {
                    codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_COMPROBANTE, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                    break;
                } else {
                    if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_RECIBO)) {
                        codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_RECIBO, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                        break;
                    } else {
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_BOLETA)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_BOLETA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        } else {
                            if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_FACTURA)) {
                                codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_FACTURA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                                break;
                            }
                        }
                    }
                }
            }

            if (codigoTipoDoc.getIdCodigo() != null) {
                docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((codigoTipoDoc));
//                obtenerDetalleDoc();
            } else {
                if (listaImpresora.size() >= 1) {
                    if (!listaTipoDocumento.isEmpty()) {
                        getDocIngresoBean().getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((listaTipoDocumento.get(0)));
                    }
                }
            }
            obtenerDetalleDoc();

            //actualizar Meylin
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipImpresoraParaPensiones() {
        try {
            System.out.println("obtenerTipImpresoraParaPensiones");
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = docIngresoService.obtenerImpresoraParaRecibos(MaristaConstantes.IMPRESORA_PARA_PENS, usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBean.getCajaBean().getIdCaja(), MaristaConstantes.COD_DOC_RECIBO);

            CodigoBean codigoTipoDoc = new CodigoBean();
            for (CodigoBean listaTipo : listaTipoDocumento) {
                if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_RECIBO)) {
                    codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_RECIBO, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                    break;
                } else {
                    if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_COMPROBANTE)) {
                        codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_COMPROBANTE, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                        break;
                    } else {
                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_BOLETA)) {
                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_BOLETA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                            break;
                        } else {
                            if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_FACTURA)) {
                                codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_FACTURA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
                                break;
                            }
                        }
                    }
                }
            }

            if (codigoTipoDoc.getIdCodigo() != null) {
                docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((codigoTipoDoc));
//                obtenerDetalleDoc();
            } else {
                if (listaImpresora.size() >= 1) {
                    if (!listaTipoDocumento.isEmpty()) {
                        getDocIngresoBean().getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((listaTipoDocumento.get(0)));
                    }
                }
            }
            obtenerDetalleDocPension();

            //actualizar Meylin
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipDocReciboPensiones() {
        try {
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            CodigoService codigoService = BeanFactory.getCodigoService();
//            listaTipoDocumento = new ArrayList<>();
//            listaTipoDocumento = docIngresoService.obtenerTipDocumentoPorImpresoraPensiones("Recibos", usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBean.getCajaBean().getIdCaja(), MaristaConstantes.COD_DOC_RECIBO);
//            CodigoBean codigoTipoDoc = new CodigoBean();
//            for (CodigoBean listaTipo : listaTipoDocumento) {
//                if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_RECIBO)) {
//                    codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_RECIBO, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
//                    break;
//                } else {
//                    if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_COMPROBANTE)) {
//                        codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_COMPROBANTE, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
//                        break;
//                    } else {
//                        if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_BOLETA)) {
//                            codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_BOLETA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
//                            break;
//                        } else {
//                            if (listaTipo.getCodigo().equals(MaristaConstantes.COD_DOC_FACTURA)) {
//                                codigoTipoDoc = codigoService.obtenerPorCodigo(new CodigoBean(0, MaristaConstantes.COD_DOC_FACTURA, new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO)));
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (codigoTipoDoc.getIdCodigo() != null) {
//                docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((codigoTipoDoc));
////                obtenerDetalleDoc();
//            } else {
//                if (listaImpresora.size() == 1) {
//                    if (!listaTipoDocumento.isEmpty()) {
//                        getDocIngresoBean().getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((listaTipoDocumento.get(0)));
//                    }
//                }
////                listaImpresora = docIngresoService.obtenerImpresoraCajero(CajeroCajaBean);
//                    System.out.println("size" + listaImpresora.size());
//                    if (listaImpresora.size() == 1) {
//                        getDocIngresoBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
//                     }
//            }
//            obtenerDetalleDoc();

            //actualizar Meylin
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetalleDoc() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            impresoraCajaBean = new ImpresoraCajaBean();
            impresoraCajaBean = docIngresoService.obtenerDetalleTipoDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().getIdCodigo(), cajaGenBean.getCajaBean().getIdCaja());
            if (impresoraCajaBean != null) {
                numActual = String.format("%07d", impresoraCajaBean.getImpresora().getActual());
                System.out.println("numActual " + numActual);
//            impresoraCajaBean.getImpresora().setActual(new Integer(numActual));
                docIngresoBean.setImpresoraCajaBean(impresoraCajaBean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetalleDocPension() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            impresoraCajaBean = new ImpresoraCajaBean();
            impresoraCajaBean = docIngresoService.obtenerDetalleTipoDoc(MaristaConstantes.IMPRESORA_PARA_PENS, usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().getIdCodigo(), cajaGenBean.getCajaBean().getIdCaja());
            if (impresoraCajaBean != null) {
                numActual = String.format("%07d", impresoraCajaBean.getImpresora().getActual());
                System.out.println("numActual " + numActual);
//            impresoraCajaBean.getImpresora().setActual(new Integer(numActual));
                docIngresoBean.setImpresoraCajaBean(impresoraCajaBean);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoDet(Integer n) {
        try {
            if(listDetDocIngreso.get(n).getDscto().doubleValue()<=listDetDocIngreso.get(n).getMontoPagado().doubleValue()){
            System.out.println("n..." + n);
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANLUI)) {
                obtenerProgramacionRegistroSanlui();
            }
            if (listDetDocIngreso.get(n).getMes() == null) {
                System.out.println("entro1");
                listDetDocIngreso.get(n).setMontoPagado(listDetDocIngreso.get(n).getMonto().subtract(listDetDocIngreso.get(n).getDscto()));
                listDetDocIngreso.get(n).setDsctoTipoDicente((listDetDocIngreso.get(n).getDscto()));
            } else {
//                obtenerProgramacionRegistroSanlui();
//                System.out.println("entro2");
//                System.out.println("progracionDs: "+montoDscPro);
//                BigDecimal programacion=new BigDecimal(montoDscPro);
//                System.out.println("montoCONdSC: "+listDetDocIngreso.get(n).getMontoConDscto());
//                BigDecimal montoDsctoBD = listDetDocIngreso.get(n).getMontoConDscto().subtract(programacion);
//                Double total = montoDsctoBD.doubleValue() * listDetDocIngreso.get(n).getMes();
//                BigDecimal montoDsctoBDMes = new BigDecimal(total);
//                listDetDocIngreso.get(n).setMontoPagado(montoDsctoBDMes);
//                listDetDocIngreso.get(n).setDsctoTipoDicente((listDetDocIngreso.get(n).getDscto()));
//                System.out.println("total: "+total);
//                System.out.println("total: "+listDetDocIngreso.get(n).getDscto());
                if (montoDscPro != null) {
                    System.out.println("entro2");
                    System.out.println("progracionDs: " + montoDscPro);
                    System.out.println("montoCONdSC: " + listDetDocIngreso.get(n).getMontoConDscto());
                    Double montoDsctoBD = listDetDocIngreso.get(n).getMontoConDscto().doubleValue() * listDetDocIngreso.get(n).getMes();
                    Double total = montoDsctoBD - montoDscPro;
                    BigDecimal montoDsctoBDMes = new BigDecimal(total);
                    listDetDocIngreso.get(n).setMontoPagado(montoDsctoBDMes);
                    listDetDocIngreso.get(n).setDsctoTipoDicente((listDetDocIngreso.get(n).getDscto()));
                    System.out.println("total: " + total);
                    System.out.println("total: " + listDetDocIngreso.get(n).getDscto());
                } else {

                    System.out.println("entro3");
                    System.out.println("progracionDs: " + montoDscPro);
                    BigDecimal montoDs = listDetDocIngreso.get(n).getMonto().subtract(listDetDocIngreso.get(n).getDscto());
                    Double MesMult = montoDs.doubleValue() * listDetDocIngreso.get(n).getMes();
                    BigDecimal total = new BigDecimal(MesMult);
                    listDetDocIngreso.get(n).setMontoPagado(total);
                    listDetDocIngreso.get(n).setDsctoTipoDicente((listDetDocIngreso.get(n).getDscto()));
                }
            }

//            for (DetDocIngresoBean detDocIngresoBean : getListDetDocIngreso()) {
//                detDocIngresoBean.setMontoPagado(detDocIngresoBean.getMontoPagado().subtract(detDocIngresoBean.getDscto()));
//            }
            obtenerDscto();
            obtenerCambio();
            }else{
                listDetDocIngreso.get(n).setDscto(new BigDecimal("0.00"));
                this.dscto=new BigDecimal("0.00"); 
                 new MensajePrime().addInformativeMessagePer("msjEtiquetaMontoDsctoSuper");
                  listDetDocIngreso.get(n).setMontoPagado(listDetDocIngreso.get(n).getMonto().subtract(listDetDocIngreso.get(n).getDscto()));
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCambio() {
        mostrarModAmbos();
        resetearMontoDol();
        Double total = 0.0;
        String tot = "";
        this.montoTotal = "";
        String sol = "S/. ";
        try {
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            docIngresoBean.setTipoCambioBean(tipoCambio);
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigo.obtenerPorId(docIngresoBean.getIdTipoMoneda());
            for (DetDocIngresoBean detDocIngresoBean : getListDetDocIngreso()) {
//                obtenerDscto();
                total += detDocIngresoBean.getMontoPagado().doubleValue();
            }
            if (cod != null) {
                switch (cod.getCodigo()) {
                    case "Soles":
                        switch (tipoMoneda.getCodigo()) {
                            case "Soles":
                                montoTotalDouble = total;
                                tot = new DecimalFormat("#,##0.00").format(total);
                                this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                break;
                            case "Dolares":
                                montoTotalDouble = (double) Math.round((total / tipoCambio.getTcVenta().doubleValue()) * 100) / 100;
                                total = montoTotalDouble;
                                tot = new DecimalFormat("#,##0.00").format(total);
                                this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                break;
                        }
                        break;
                    case "Dolares":
                        switch (tipoMoneda.getCodigo()) {
                            case "Soles":
                                montoTotalDouble = (double) Math.round((total * tipoCambio.getTcVenta().doubleValue()) * 100) / 100;
                                total = montoTotalDouble;
                                tot = new DecimalFormat("#,##0.00").format(total);
                                this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                break;
                            case "Dolares":
                                montoTotalDouble = total;
                                tot = new DecimalFormat("#,##0.00").format(total);
                                this.montoTotal = cod.getValor().concat(" ").concat(tot);
                                break;
                        }
                        break;
                }
            } else {
                montoTotalDouble = total;
                tot = new DecimalFormat("#,##0.00").format(total);
                this.montoTotal = cod.getValor().concat(" ").concat(tot);
            }
            getMontoTotal();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCambioParcial(String origen) {
        Double total = 0.0d;
        Double parcialSol = 0.0d;
        Double parcialDol = 0.0d;
        String tot = "";

        this.montoParcialDol = "";
        try {
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            docIngresoBean.setTipoCambioBean(tipoCambio);
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigo.obtenerPorId(docIngresoBean.getIdTipoMoneda());
            if (this.flgPagoParDol == true && !docIngresoBean.getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                if (!getDocIngresoBean().getIdTipoMoneda().getCodigo().equals("Dolares")) {
                    if (origen.equals("soles")) {
                        if (docIngresoBean.getMontoEfectivoSol().doubleValue() > 0d && docIngresoBean.getMontoEfectivoSol().doubleValue() >= montoTotalDouble) {
                            RequestContext.getCurrentInstance().addCallbackParam("errorMontos", true);
                        } else {
                            total = montoTotalDouble - docIngresoBean.getMontoEfectivoSol().doubleValue();
                            parcialDol = total / tipoCambio.getTcVenta().doubleValue();
                            docIngresoBean.setMontoEfectivoDol((parcialDol));
                            montoParcialDolDouble = parcialDol * tipoCambio.getTcVenta().doubleValue();
                            tot = new DecimalFormat("#,##0.00").format(montoParcialDolDouble);
                            this.montoParcialDol = cod.getValor().concat(" ").concat(tot);
                        }
                    } else {
                        total = montoTotalDouble - docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                        if (!docIngresoBean.getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                            docIngresoBean.setMontoEfectivoSol((total));
                        }
                        parcialSol = total;
                        montoParcialDolDouble = montoTotalDouble - total;
                        tot = new DecimalFormat("#,##0.00").format(montoParcialDolDouble);
                        this.montoParcialDol = cod.getValor().concat(" ").concat(tot);
                    }
                } else {
                    Double total1 = 0.0d;
                    total = montoTotalDouble - docIngresoBean.getMontoEfectivoDol().doubleValue();
                    total1 = total * tipoCambio.getTcVenta().doubleValue();
                    double rounded = (double) Math.round(total1 * 100) / 100;
                    if (!docIngresoBean.getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                        docIngresoBean.setMontoEfectivoSol((rounded));
                    }
                }

            } else if (!docIngresoBean.getMontoEfectivoDol().equals(0.0) && this.flgPagoParDol == true && docIngresoBean.getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                total = docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                parcialSol = total;
                montoParcialDolDouble = parcialSol;
                tot = new DecimalFormat("#,##0.00").format(montoParcialDolDouble);
                this.montoParcialDol = cod.getValor().concat(" ").concat(tot);
                String a = "d?";
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void resetearMontoDol() {
        try {
            docIngresoBean.setMontoEfectivoSol((0.0));
            docIngresoBean.setMontoEfectivoDol((0.0));
            docIngresoBean.setMontoPos1((0.0));
            docIngresoBean.setMontoPos2((0.0));
            montoParcialDolDouble = null;
            montoParcialDol = "0,00";
            if (docIngresoBean.getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                setFlgModPOS(true);
            } else {
                setFlgModPOS(false);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void montosPOS() {
        try {
            if (!getDocIngresoBean().getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                Double montoEfe = 0.0;
                if (this.posVisa.equals(true) && this.posMC.equals(false)) {
                    if (getDocIngresoBean().getMontoEfectivoSol() != null) {
                        if (getDocIngresoBean().getMontoEfectivoSol() > 0) {
                            montoEfe = getDocIngresoBean().getMontoEfectivoSol();
                        }
                    }
                    docIngresoBean.setMontoPos1(montoTotalDouble - montoEfe);
                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    if (getDocIngresoBean().getMontoEfectivoSol() != null) {
                        if (getDocIngresoBean().getMontoEfectivoSol() > 0) {
                            montoEfe = getDocIngresoBean().getMontoEfectivoSol();
                        }
                    }
                    docIngresoBean.setMontoPos2(montoTotalDouble - montoEfe);
                } else {
                    docIngresoBean.setMontoPos1(0.0);
                    docIngresoBean.setMontoPos2(0.0);
                }
            } else {
                if (this.posVisa.equals(true) && this.posMC.equals(false)) {
                    docIngresoBean.setMontoPos1(montoTotalDouble - getDocIngresoBean().getMontoEfectivoSol());
                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    docIngresoBean.setMontoPos2(montoTotalDouble - getDocIngresoBean().getMontoEfectivoSol());
                } else {
                    docIngresoBean.setMontoPos1(0.0);
                    docIngresoBean.setMontoPos2(0.0);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void diferenciaPOS() {
        try {
            if (this.posVisa.equals(true) && this.posMC.equals(true)) {
                Double montoEfe = 0.0;
                if (getDocIngresoBean().getIdTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                    montoEfe = getDocIngresoBean().getMontoEfectivoSol();
                }
                if (docIngresoBean.getMontoPos1() > 0 && docIngresoBean.getMontoPos1() <= montoTotalDouble - montoEfe) {
                    Double total1 = 0.0d;
                    total1 = (montoTotalDouble - montoEfe) - docIngresoBean.getMontoPos1();
                    double rounded = (double) Math.round(total1 * 100) / 100;
                    docIngresoBean.setMontoPos2(rounded);
                } else {
//                    new MensajePrime().addInformativeMessageSearch("msgsMontoSuperaAcaja");
                    docIngresoBean.setMontoPos2(0.0);
                }
                if (docIngresoBean.getMontoPos2() > 0 && docIngresoBean.getMontoPos1() <= montoTotalDouble - montoEfe) {
                    Double total2 = 0.0d;
                    total2 = (montoTotalDouble - montoEfe) - docIngresoBean.getMontoPos2();
                    double rounded = (double) Math.round(total2 * 100) / 100;
                    docIngresoBean.setMontoPos1(rounded);
                } else {
//                    new MensajePrime().addInformativeMessageSearch("msgsMontoSuperaAcaja");
                    docIngresoBean.setMontoPos1(0.0);
                }
            } else {
                if (this.posVisa.equals(true) && this.posMC.equals(false) && docIngresoBean.getIdTipoModoPago().getCodigo().equals(MaristaConstantes.COD_POS)) {
                    docIngresoBean.setMontoPos2(0.0);

                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    docIngresoBean.setMontoPos1(0.0);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                tipoCambio.setCreaPor(usuarioLoginBean.getUsuario());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                tipoCambio.setCreaFecha(formato.parse(date));
                tipoCambio.setFechaTc(formato.parse(date));
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                tipoCambioService.insertar(tipoCambio);

                obtenerTipoCambio();
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String obtenerTipoCambio() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yy");
                String dia = fecha.format(new Date());
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
                tipoCambio = new TipoCambioBean();
                tipoCambio.setIdTipoMoneda(IdTipoMoneda);
                tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
                if (!dia.equals(tipoCambio.getFecha())) {
                    System.out.println(tipoCambio.getFecha());
                    System.out.println(dia);
                    new MensajePrime().addInformativeMessagePer("msjRealizarTipoCambio");
                }
                getDocIngresoBean().setTipoCambioBean(tipoCambio);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String calcularDescuento() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                Integer cantidad = listDetDocIngreso.size();
                for (int i = 0; i < listDetDocIngreso.size(); i++) {
//                for (DetDocIngresoBean det : listDetDocIngreso) {
                    System.out.println("concepto: " + listDetDocIngreso.get(i).getConceptoBean().getIdConcepto());
                    Integer idTipoConcepto = conceptoService.obtenerConceptoDesc(listDetDocIngreso.get(i).getConceptoBean().getIdConcepto());
                    DescuentoTallerBean descuentoTallerBean = new DescuentoTallerBean();
                    descuentoTallerBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    descuentoTallerBean.getTipoConceptoBean().setIdTipoConcepto(idTipoConcepto);
                    List<DescuentoTallerBean> listaDescuento = new ArrayList<>();
                    listaDescuento = docIngresoService.obtenerDescuentosHabilitados(descuentoTallerBean);
                    for (DescuentoTallerBean desc : listaDescuento) {
                        if (cantidad.equals(desc.getCantidad())) {
                            Double monto = listDetDocIngreso.get(i).getMontoPagado().doubleValue();
                            monto = ((monto * desc.getPorcentaje()) / 100);
                            System.out.println("monto: " + monto);
                            listDetDocIngreso.get(i).setDscto(BigDecimal.valueOf(monto));
                            obtenerDsctoDet(i);
                            System.out.println("posicion: " + cantidad);
                        }
                        if (!cantidad.equals(desc.getCantidad())) {
                            listDetDocIngreso.get(i).setDscto(BigDecimal.valueOf(0.00));
                            obtenerDsctoDet(i);
                        }
                    }
//                   
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String guardarDocIngreso() {
        String pagina = null;
        try {
            Integer estadoFlgP = 0;
            Integer estadoFlgSG = 0;
            for (DetDocIngresoBean lista : listDetDocIngreso) {
                if (lista.getMes() != null) {
                    estadoFlgP = 1;
                } else {
                    estadoFlgSG = 1;
                }
            }
            if (estadoFlgP.equals(0)) {
                this.flgPension = Boolean.FALSE;
            }
            if (estadoFlgSG.equals(0)) {
                this.flgSerGen = Boolean.FALSE;
            }
            System.out.println("estado pen..." + this.flgPension);
            if (this.flgPension.equals(true) && this.flgSerGen.equals(true)) {
                new MensajePrime().addInformativeMessagePer("msjNoPuedePagarPenSer");
            } else {
                if (getPersonaBean().getApepat() != null || getPersonalBean().getApepat() != null) {
                    DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
                    CodigoService codigoService = BeanFactory.getCodigoService();
                    CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                    EstudianteService estudianteService = BeanFactory.getEstudianteService();
//                PersonaService personaService = BeanFactory.getPersonaService();
                    pagina = new MaristaUtils().validaUsuarioSesion();
                    if (pagina == null) {
                        Double totalParcialDol = new Double("0.00");
                        Double totalParcialSol = new Double("0.00");
                        Double totalParcialPos1 = new Double("0.00");
                        Double totalParcialPos2 = new Double("0.00");

                        Double montoCajaPos1 = new Double("0.00");
                        Double montoCajaPos2 = new Double("0.00");
                        Double montoCajaDol = new Double("0.00");
                        Double montoCajaSol = new Double("0.00");

                        CodigoBean codigo = new CodigoBean();
                        CodigoBean tipoPago = new CodigoBean();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                        String date = formato.format(new Date());
                        SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
                        String dia = fecha.format(new Date());
//                    UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                        CajaGenBean cajaGeBean = new CajaGenBean();
                        if (cajaGenBean != null) {
                            cajaGeBean.setIdCajaGen(cajaGenBean.getIdCajaGen());
                        }
                        EstudianteBean estudiante = new EstudianteBean();

                        docIngresoBean.getImpresoraCajaBean().getCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                        docIngresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//                    if (personaBean.getIdPersona() != null) {
                        if (getPersonaBean().getIdPersona() != null) {
                            if (!getPersonaBean().getIdPersona().equals(MaristaConstantes.SIN_NRODOC_DOCINGRESO)) {
                                docIngresoBean.setIdDiscente(personaBean);
                                estudiante.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                                estudiante.setPersonaBean(personaBean);
                                estudiante.setAnio(anio);
                                estudiante = estudianteService.obtenerEstPorId(estudiante);
                                if (estudiante != null) {
                                    docIngresoBean.setDireccion(estudiante.getViaDomi());
                                    docIngresoBean.setTelefono(estudiante.getTelefono1Domi());
                                    docIngresoBean.getCuentasPorCobrarBean().getEstudianteBean().setGradoHabilitado(estudiante.getGradoHabilitado());
                                    docIngresoBean.getCuentasPorCobrarBean().getEstudianteBean().setSeccion(estudiante.getSeccion());
                                }
                            } else if (personalBean.getIdPersonal() != null) {
                                docIngresoBean.setPersonalBean(personalBean);
                                PersonalService personalService = BeanFactory.getPersonalService();
                                personalBean = personalService.buscarPorId(personalBean.getIdPersonal());
                                docIngresoBean.setDireccion(personalBean.getDomicilio());
                                docIngresoBean.setTelefono(personalBean.getCelular1());
                            }
                        } else if (personalBean.getIdPersonal() != null) {
                            docIngresoBean.setPersonalBean(personalBean);
                            PersonalService personalService = BeanFactory.getPersonalService();
                            personalBean = personalService.buscarPorId(personalBean.getIdPersonal());
                            docIngresoBean.setDireccion(personalBean.getDomicilio());
                            docIngresoBean.setTelefono(personalBean.getCelular1());
                        }

                        docIngresoBean.setAnio(Integer.parseInt(date));
                        docIngresoBean.setFechaPago(fecha.parse(dia));
                        docIngresoBean.setCreaPor(usuarioLoginBean.getUsuario());
                        docIngresoBean.setCreaFecha(fecha.parse(dia));
                        cajaGeBean.setUniNeg(docIngresoBean.getUnidadNegocioBean());
                        cajaGeBean.setCajaBean(docIngresoBean.getImpresoraCajaBean().getCajaBean());
                        cajaGeBean.setUsuarioBean(usuarioLoginBean);
                        cajaGeBean.setAnio(docIngresoBean.getAnio());
                        cajaGeBean.setFecApertura(new Date());
                        cajaGeBean = cajaGenService.verificarApertura(cajaGeBean);
                        docIngresoBean.setCajaGenBean(cajaGeBean);
                        montoCajaSol = cajaGeBean.getIngresoSol();
                        montoCajaDol = cajaGeBean.getIngresoDol();
                        //pos 
                        montoCajaPos1 = cajaGeBean.getIngresoPos1();
                        montoCajaPos2 = cajaGeBean.getIngresoPos2();

                        //CONGREGACION
//                montoCajaCongreSol = cajaGeBean.getIngresoCongreEfectivoSol();
//                montoCajaCongreDol = cajaGeBean.getIngresoCongreEfectivoDol();
                        tipoPago = codigoService.obtenerPorId(docIngresoBean.getIdTipoModoPago());

                        switch (tipoPago.getCodigo()) {
                            case "Efectivo":
                                codigo = codigoService.obtenerPorId(docIngresoBean.getIdTipoMoneda());
                                switch (codigo.getCodigo()) {
                                    case "Soles":
                                        if (montoCajaSol == null) {
                                            montoCajaSol = new Double("0.00");
                                            if (flgPagoParDol == false) {
                                                montoCajaSol = montoCajaSol + montoTotalDouble;
                                                docIngresoBean.setMontoEfectivoSol((montoTotalDouble));
//                                        docIngresoBean.setMontoEfectivoSol(BigDecimal.valueOf(montoTotalDouble));
                                            } else {
                                                montoCajaSol = montoCajaSol + docIngresoBean.getMontoEfectivoSol().doubleValue();
                                                montoCajaDol = montoCajaDol + docIngresoBean.getMontoEfectivoDol().doubleValue();
                                            }
                                            break;
                                        } else {
                                            if (flgPagoParDol == false) {
                                                montoCajaSol = montoCajaSol + montoTotalDouble;
                                                docIngresoBean.setMontoEfectivoSol((montoTotalDouble));
//                                        docIngresoBean.setMontoEfectivoSol(BigDecimal.valueOf(montoTotalDouble));
                                            } else {
                                                montoCajaSol = montoCajaSol + docIngresoBean.getMontoEfectivoSol().doubleValue();
                                                montoCajaDol = montoCajaDol + docIngresoBean.getMontoEfectivoDol().doubleValue();
                                            }
                                            break;
                                        }
                                    case "Dolares":
                                        if (montoCajaDol == null) {
                                            montoCajaDol = new Double("0.00");

                                            if (flgPagoParDol == false) {
                                                montoCajaDol = montoCajaDol + montoTotalDouble;
                                                docIngresoBean.setMontoEfectivoDol((montoTotalDouble));
                                            } else {
                                                montoCajaSol = montoCajaSol + docIngresoBean.getMontoEfectivoSol().doubleValue();
                                                montoCajaDol = montoCajaDol + docIngresoBean.getMontoEfectivoDol().doubleValue();
                                            }
                                            break;
                                        } else {
                                            if (flgPagoParDol == false) {
                                                montoCajaDol = montoCajaDol + montoTotalDouble;
                                                docIngresoBean.setMontoEfectivoDol((montoTotalDouble));
                                            } else {
                                                montoCajaSol = montoCajaSol + docIngresoBean.getMontoEfectivoSol().doubleValue();
                                                montoCajaDol = montoCajaDol + docIngresoBean.getMontoEfectivoDol().doubleValue();
                                            }
                                            break;
                                        }
                                }
                                break;
                            case "POS":
                                if (montoCajaPos1 == null) {
                                    montoCajaPos1 = new Double("0.00");
                                    montoTotalPos1Double = docIngresoBean.getMontoPos1().doubleValue();
                                    montoCajaPos1 = montoCajaPos1 + montoTotalPos1Double;
                                } else {
                                    montoTotalPos1Double = docIngresoBean.getMontoPos1().doubleValue();
                                    montoCajaPos1 = montoCajaPos1 + montoTotalPos1Double;
                                }

                                if (montoCajaPos2 == null) {
                                    montoCajaPos2 = new Double("0.00");
                                    montoTotalPos2Double = docIngresoBean.getMontoPos2().doubleValue();
                                    montoCajaPos2 = montoCajaPos2 + montoTotalPos2Double;
                                } else {
                                    montoTotalPos2Double = docIngresoBean.getMontoPos2().doubleValue();
                                    montoCajaPos2 = montoCajaPos2 + montoTotalPos2Double;
                                }
                                break;
                            case "Ambos":
                                if (montoCajaDol == null && montoCajaSol == null) {
                                    montoCajaDol = new Double("0.00");
                                    montoCajaSol = new Double("0.00");
                                    break;
                                } else {
                                    if (docIngresoBean.getMontoEfectivoSol() != null && !docIngresoBean.getMontoEfectivoSol().equals(0)) {
                                        totalParcialSol = docIngresoBean.getMontoEfectivoSol().doubleValue();
                                        montoTotalSolDouble = totalParcialSol;
                                    }
                                    if (docIngresoBean.getMontoEfectivoDol() != null && !docIngresoBean.getMontoEfectivoDol().equals(0)) {
                                        totalParcialDol = docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                                        montoTotalDolDouble = totalParcialDol;
                                    }
                                    if (docIngresoBean.getMontoPos1() != null && !docIngresoBean.getMontoPos1().equals(0)) {
                                        totalParcialPos1 = docIngresoBean.getMontoPos1().doubleValue();
                                        montoTotalPos1Double = totalParcialPos1;
                                    }
                                    if (docIngresoBean.getMontoPos2() != null && !docIngresoBean.getMontoPos2().equals(0)) {
                                        totalParcialPos2 = docIngresoBean.getMontoPos2().doubleValue();
                                        montoTotalPos2Double = totalParcialPos2;
                                    }
                                    montoCajaDol = montoCajaDol + montoTotalDolDouble;
                                    montoCajaSol = montoCajaSol + montoTotalSolDouble;
                                    montoCajaPos1 = montoCajaPos1 + montoTotalPos1Double;
                                    montoCajaPos2 = montoCajaPos2 + montoTotalPos2Double;
                                    break;
                                }
                            case "Banco":
                                docIngresoBean.setMontoEfectivoSol((montoTotalDouble));
                                CodigoBean codigoTipoLugarPago = new CodigoBean();
                                codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
                                docIngresoBean.setIdTipoLugarPago(codigoTipoLugarPago);
                                break;
                        }
                        cajaGeBean.setIngresoSol(montoCajaSol);
                        cajaGeBean.setIngresoDol(montoCajaDol);
                        cajaGeBean.setIngresoPos1(montoCajaPos1);
                        cajaGeBean.setIngresoPos2(montoCajaPos2);
                        System.out.println("id caja gen: " + cajaGenBean.getIdCajaGen());

                        //validando el monto que sea igual a lo que se ingresa de manera manual
                        Double total = 0.0d;
                        Double sumaTotal = 0.0d;
                        Integer estado = 0;

                        String totSistema = "";
                        String totInput = "";
                        Double monDolares = 0.0d;

                        ///////////////////////////////////////////////
                        for (DetDocIngresoBean detDocIngresoBean : getListDetDocIngreso()) {
                            total += detDocIngresoBean.getMontoPagado().doubleValue();
                        }
                        totSistema = new DecimalFormat("#,##0.00").format(total);
                        switch (tipoPago.getCodigo()) {
                            case "Efectivo":
                                if (this.flgPagoParDol == true) {
                                    if (!getDocIngresoBean().getIdTipoMoneda().getCodigo().equals("Dolares")) {
                                        monDolares = (docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue());
                                        sumaTotal = docIngresoBean.getMontoEfectivoSol().doubleValue() + monDolares;
                                    } else {
//                                    Double total1 = 0.0d;
                                        monDolares = (docIngresoBean.getMontoEfectivoSol().doubleValue() / tipoCambio.getTcVenta().doubleValue());
                                        double rounded = (double) Math.round(monDolares * 100) / 100;
                                        sumaTotal = docIngresoBean.getMontoEfectivoDol().doubleValue() + rounded;
                                    }
                                    //agregado
                                    totInput = new DecimalFormat("#,##0.00").format(sumaTotal);
                                    if (totInput.equals(totSistema)) {
                                        estado = 1;
                                    } else {
                                        estado = 0;
                                    }
                                } else {
                                    estado = 1;
                                }
                                break;
                            case "POS":
                                if (this.flgPagoParDol == true) {
                                    monDolares = docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                                    sumaTotal = docIngresoBean.getMontoPos1().doubleValue() + docIngresoBean.getMontoPos2().doubleValue() + monDolares;
                                    totInput = new DecimalFormat("#,##0.00").format(sumaTotal);
                                    if (totInput.equals(totSistema)) {
                                        estado = 1;
                                    } else {
                                        estado = 0;
                                    }
                                } else {
                                    sumaTotal = docIngresoBean.getMontoPos1() + (docIngresoBean.getMontoPos2()).doubleValue();
                                    totInput = new DecimalFormat("#,##0.00").format(sumaTotal);
                                    if (totInput.equals(totSistema)) {
                                        estado = 1;
                                    } else {
                                        estado = 0;
                                    }
                                }
                                break;
                            case "Ambos":
                                if (this.flgPagoParDol == true) {
                                    if (!getDocIngresoBean().getIdTipoMoneda().getCodigo().equals("Dolares")) {
                                        monDolares = docIngresoBean.getMontoEfectivoDol().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                                        sumaTotal = docIngresoBean.getMontoEfectivoSol().doubleValue()
                                                + docIngresoBean.getMontoPos1().doubleValue() + docIngresoBean.getMontoPos2().doubleValue() + monDolares;
                                    } else {
                                        new MensajePrime().addInformativeMessagePer("mensajeChequeError");
                                        sumaTotal = 0.0;
                                    }

                                    totInput = new DecimalFormat("#,##0.00").format(sumaTotal);
                                    if (totInput.equals(totSistema)) {
                                        estado = 1;
                                    } else {
                                        estado = 0;
                                    }
                                } else {
                                    sumaTotal = docIngresoBean.getMontoEfectivoSol().doubleValue() + docIngresoBean.getMontoPos1().doubleValue() + docIngresoBean.getMontoPos2().doubleValue();
                                    totInput = new DecimalFormat("#,##0.00").format(sumaTotal);
                                    if (totInput.equals(totSistema)) {
                                        estado = 1;
                                    } else {
                                        estado = 0;
                                    }
                                }
                                break;
                            case "Banco":
                                estado = 1;
                                break;
                        }
                        if (estado == 1) {
                            docIngresoService.insertarDocIngreso(docIngresoBean, listDetDocIngreso, cajaGeBean, cr, listaProgramacionSessionBean, getDocEgresoBean().getSolicitudCajaCHBean(), tipoCambio.getTcVenta(), this.flgPension, this.flgDisableGenCod, getPersonaBean());
                            if (docIngresoBean.getEstadoRegIng().equals(1)) {
//                            obtenerDsctoTipoDiscente(docIngimsoBean.getIdDiscente().getIdPersona());
                                obtenerDsctoTipoDiscenteVer2(docIngresoBean.getIdDiscente().getIdPersona());
                                this.setFlgPagoParDol(false);
//                            changeFlgEst();
                                if (this.flgCajExt.equals(Boolean.TRUE)) {
                                    this.flgSoloEst = Boolean.FALSE;
                                } else {
                                    this.flgSoloEst = Boolean.TRUE;
                                }
                                this.flgBtnImprimir = Boolean.FALSE;
                                System.out.println("flgBtnImprimir.." + flgBtnImprimir);
                                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                            } else if (docIngresoBean.getEstadoRegIng().equals(0)) {
                                RequestContext.getCurrentInstance().addCallbackParam("operacionErronea", true);
                                impresoraCajaBean = new ImpresoraCajaBean();
                                impresoraCajaBean = docIngresoService.obtenerDetalleTipoDoc(docIngresoBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), docIngresoBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().getIdCodigo(), cajaGenBean.getCajaBean().getIdCaja());
                                numActual = String.format("%07d", impresoraCajaBean.getImpresora().getActual() + 1);
                                impresoraCajaBean.getImpresora().setActual(new Integer(numActual));
                                docIngresoBean.setImpresoraCajaBean(impresoraCajaBean);
                            } else if (docIngresoBean.getEstadoRegIng().equals(3)) {
                                RequestContext.getCurrentInstance().addCallbackParam("operacionSinCupos", true);
                            }
                        } else {
                            RequestContext.getCurrentInstance().addCallbackParam("errorMontos", true);
                        }
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjEtiquetaPersReq");
                }
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //    obtenerFiltroDocIngreso
    public void obtenerFiltroDocIngreso() {
        try {
            int estado = 0;
            System.out.println("1:" + docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            if (docIngresoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(docIngresoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                docIngresoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (docIngresoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(docIngresoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                docIngresoFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo() != null && !docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getTipoStatusDocIng().setIdCodigo(docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoDoc().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoDoc().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoDoc().setIdCodigo(docIngresoFiltroBean.getIdTipoDoc().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoModoPago().setIdCodigo(docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoLugarPago().setIdCodigo(docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
                System.out.println("entró lugar");
                estado = 1;
            }

            if (docIngresoFiltroBean.getSerie() != null && !docIngresoFiltroBean.getSerie().equals("")) {
                docIngresoFiltroBean.setSerie(docIngresoFiltroBean.getSerie());
                estado = 1;
            }
            if (docIngresoFiltroBean.getNroDoc() != null && !docIngresoFiltroBean.getNroDoc().equals("")) {
                docIngresoFiltroBean.setNroDoc(docIngresoFiltroBean.getNroDoc());
                estado = 1;
            }
            if (docIngresoFiltroBean.getCodDiscente() != null && !docIngresoFiltroBean.getCodDiscente().equals("")) {
                docIngresoFiltroBean.setCodDiscente(docIngresoFiltroBean.getCodDiscente());
                estado = 1;
            }

            if (docIngresoFiltroBean.getNombreDiscente() != null && !docIngresoFiltroBean.getNombreDiscente().equals("")) {
                docIngresoFiltroBean.setNombreDiscente(docIngresoFiltroBean.getNombreDiscente());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listDocIngresoBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                System.out.println("2:" + docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
                listDocIngresoBean = new ArrayList<>();
                listDocIngresoBean = docIngresoService.obtenerFiltroDocIngreso(docIngresoFiltroBean);
                System.out.println("size" + ":" + listDocIngresoBean.size());
                if (listDocIngresoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    resetMontosRep();
                } else {
                    obtenerMontosRep("docIng");
                }
//                for (int i = 0; i < listDocIngresoBean.size(); i++) {
////                    System.out.println("lista" + i + "-" + listDocIngresoBean.get(i).getTipoStatusDocIng().getCodigo());
//                }

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

// Obtener Filtro DocIngreso Banco
    public void obtenerFiltroDocIngresoBanco() {
        try {
            int estado = 0;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            if (docIngresoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(docIngresoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                docIngresoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (docIngresoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(docIngresoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                docIngresoFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo() != null && !docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getTipoStatusDocIng().setIdCodigo(docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoDoc().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoDoc().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoDoc().setIdCodigo(docIngresoFiltroBean.getIdTipoDoc().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoModoPago().setIdCodigo(docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoLugarPago().setIdCodigo(docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
                estado = 1;
            }

            if (docIngresoFiltroBean.getSerie() != null && !docIngresoFiltroBean.getSerie().equals("")) {
                docIngresoFiltroBean.setSerie(docIngresoFiltroBean.getSerie());
                estado = 1;
            }
            if (docIngresoFiltroBean.getNroDoc() != null && !docIngresoFiltroBean.getNroDoc().equals("")) {
                docIngresoFiltroBean.setNroDoc(docIngresoFiltroBean.getNroDoc());
                estado = 1;
            }
            if (docIngresoFiltroBean.getCodDiscente() != null && !docIngresoFiltroBean.getCodDiscente().equals("")) {
                docIngresoFiltroBean.setCodDiscente(docIngresoFiltroBean.getCodDiscente());
                estado = 1;
            }

            if (docIngresoFiltroBean.getNombreDiscente() != null && !docIngresoFiltroBean.getNombreDiscente().equals("")) {
                docIngresoFiltroBean.setNombreDiscente(docIngresoFiltroBean.getNombreDiscente());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listDocIngresoBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                listDocIngresoBean = new ArrayList<>();
                listDocIngresoBean = docIngresoService.obtenerFiltroDocIngresoBanco(docIngresoFiltroBean);
                if (listDocIngresoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    resetMontosRep();
                } else {
                    obtenerMontosRep("docIng");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirRepBanco() {
        ServletOutputStream out = null;
        try {
            if (!listDocIngresoBean.isEmpty()) {
                List<ProcesoBancoRepBean> listaProcesoBancoRepBean = new ArrayList<>();
                for (DocIngresoBean doc : listDocIngresoBean) {
                    ProcesoBancoRepBean procesoBancoRepBean = new ProcesoBancoRepBean();
                    BigDecimal b = new BigDecimal(doc.getMontoPagadoSol(), MathContext.DECIMAL64);
                    BigDecimal c = new BigDecimal(getTotSoles(), MathContext.DECIMAL64);
                    procesoBancoRepBean.setUnineg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getNombreUniNeg());
                    procesoBancoRepBean.setCodDiscente(doc.getCodigo());
                    procesoBancoRepBean.setNombreDiscente(doc.getNombreDiscente());
                    procesoBancoRepBean.setFechaPago(doc.getFechaPagoVista());
                    procesoBancoRepBean.setLugarPago(doc.getIdTipoLugarPago().getCodigo());
                    procesoBancoRepBean.setMontoEfectivoSol(b);
                    procesoBancoRepBean.setTotSoles(c);
                    procesoBancoRepBean.setGrado(doc.getGrado());
                    procesoBancoRepBean.setConcepto(doc.getConcepto());
                    listaProcesoBancoRepBean.add(procesoBancoRepBean);
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePagosBanco.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaProcesoBancoRepBean);
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

// -------------------------
//    obtenerFiltrodetDocIngreso
    public void obtenerFiltroDetDocIngreso() {
        try {
            int estado = 0;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            if (detDocIngresoRepFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(detDocIngresoRepFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                detDocIngresoRepFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (detDocIngresoRepFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(detDocIngresoRepFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                detDocIngresoRepFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (detDocIngresoRepFiltroBean.getIdConcepto() != null && !detDocIngresoRepFiltroBean.getIdConcepto().equals(0)) {
                detDocIngresoRepFiltroBean.setIdConcepto(detDocIngresoRepFiltroBean.getIdConcepto());
                estado = 1;
            }
            if (detDocIngresoRepFiltroBean.getIdTipoConcepto() != null && !detDocIngresoRepFiltroBean.getIdTipoConcepto().equals(0)) {
                detDocIngresoRepFiltroBean.setIdTipoConcepto(detDocIngresoRepFiltroBean.getIdTipoConcepto());
                estado = 1;
            }
            if (detDocIngresoRepFiltroBean.getEstadoDocIng() != null && !detDocIngresoRepFiltroBean.getEstadoDocIng().equals("")) {
                detDocIngresoRepFiltroBean.setEstadoDocIng(detDocIngresoRepFiltroBean.getEstadoDocIng());
                estado = 1;
            }

            if (detDocIngresoRepFiltroBean.getReferencia() != null && !detDocIngresoRepFiltroBean.getReferencia().equals("")) {
                detDocIngresoRepFiltroBean.setReferencia(detDocIngresoRepFiltroBean.getReferencia());
                estado = 1;
            }
            if (detDocIngresoRepFiltroBean.getDiscente() != null && !detDocIngresoRepFiltroBean.getDiscente().equals("")) {
                detDocIngresoRepFiltroBean.setDiscente(detDocIngresoRepFiltroBean.getDiscente());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaDetDocIngresoRepBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                listaDetDocIngresoRepBean = new ArrayList<>();
                listaDetDocIngresoRepBean = docIngresoService.obtenerFiltroDetDocIngreso(detDocIngresoRepFiltroBean);
                if (listaDetDocIngresoRepBean.isEmpty()) {
                    resetMontosRep();
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    obtenerMontosRep("detDocIng");
                    for (DetDocIngresoRepBean det : listaDetDocIngresoRepBean) {
                        if (detDocIngresoRepFiltroBean.getIdConcepto() == null) {
                            det.setIdConcepto(null);
                            det.setNomConcepto(null);
                        }
                        if (detDocIngresoRepFiltroBean.getIdTipoConcepto() == null) {
                            det.setIdTipoConcepto(null);
                            det.setNomTipoConcepto(null);
                        }
//                        if (detDocIngresoRepFiltroBean.getReferencia() == null || detDocIngresoRepFiltroBean.getReferencia().equals("")) {
//                            det.setReferencia(null);
//                        }
//                        if (detDocIngresoRepFiltroBean.getDiscente() == null || detDocIngresoRepFiltroBean.getReferencia().equals("")) {
//                            det.setDiscente(null);
//                        }
//                        if (detDocIngresoRepFiltroBean.getEstadoDocIng() == null || detDocIngresoRepFiltroBean.getReferencia().equals("")) {
//                            det.setEstadoDocIng(null);
//                        }
                        det.setFechaInicio(detDocIngresoRepFiltroBean.getFechaInicio());
                        det.setFechaFin(detDocIngresoRepFiltroBean.getFechaFin());
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroDetPagoMatricula() {
        try {
            int estado = 1;
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            getDetDocIngresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detDocIngresoRepFiltroBean.setFechaInicio(null);
            detDocIngresoRepFiltroBean.setFechaFin(null);
            detDocIngresoRepFiltroBean.setEstadoDocIng("Pagado");
            System.out.println("dis---" + detDocIngresoRepFiltroBean.getDiscente());
            if (detDocIngresoRepFiltroBean.getDiscente() != null && !detDocIngresoRepFiltroBean.getDiscente().equals("")) {
                detDocIngresoRepFiltroBean.setDiscente(detDocIngresoRepFiltroBean.getDiscente());
            }
            if (estado == 1) {
                listaDetDocIngresoRepBean = new ArrayList<>();
                listaDetDocIngresoRepBean = docIngresoService.obtenerFiltroDetPagoMatricula(detDocIngresoRepFiltroBean);
                if (listaDetDocIngresoRepBean.isEmpty()) {
                    resetMontosRep();
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
//                    obtenerMontosRep("detDocIng");
//                    for (DetDocIngresoRepBean det : listaDetDocIngresoRepBean) {
//                        if (detDocIngresoRepFiltroBean.getIdConcepto() == null) {
//                            det.setIdConcepto(null);
//                            det.setNomConcepto(null);
//                        }
//                        if (detDocIngresoRepFiltroBean.getIdTipoConcepto() == null) {
//                            det.setIdTipoConcepto(null);
//                            det.setNomTipoConcepto(null);
//                        } 
//                        det.setFechaInicio(null);
//                        det.setFechaFin(null);
//                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    // reportes//////////////////////////////////////////
    public void imprimirPdf() {
        ServletOutputStream out = null;
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngreso.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
            DocIngresoBean docIngreso = new DocIngresoBean();
            docIngreso.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
            docIngreso.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
            listaDocIngresoRep = docIngresoService.obtenerDocIngreso(docIngreso);
            if (!listaDocIngresoRep.isEmpty()) {
                List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                listaRepDetDocIngreso = docIngresoService.obtenerDetalleDocIngreso(listaDocIngresoRep.get(0).getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaDocIngresoRep.get(0).setListaDetalle(listaRepDetDocIngreso);
            }
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

    // reportes//////////////////////////////////////////
    public void imprimirDetDocIngresoPdf() {
        ServletOutputStream out = null;
        try {
            if (listaDetDocIngresoRepBean != null) {
                if (!listaDetDocIngresoRepBean.isEmpty()) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                            getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/repPlanillaCobranza.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    List<DetDocIngresoRepBean> lista = new ArrayList<>();
                    lista = listaDetDocIngresoRepBean;
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

    public void reimpresionRecibo(DocIngresoBean doc) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
            cta = cuentasPorCobrarService.validarDocIngresoEnCtaCte(doc.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cta != null) {
                if (cta.getIdCtasXCobrar() != null) {
                    this.flgPension = true;
                    this.flgSerGen = false;
                }
            } else {
                this.flgPension = false;
                this.flgSerGen = true;
            }
            docIngresoBean = doc;
            imprimirFormatoPdf();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void reimpresionRecibo2(DocIngresoBean doc) {
//        try {
//            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
//            CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
//
//            cta = cuentasPorCobrarService.validarDocIngresoEnCtaCte(doc.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            if (cta != null) {
//                if (cta.getIdCtasXCobrar() != null) {
//                    this.flgPension = true;
//                    this.flgSerGen = false;
//                }
//            } else {
//                this.flgPension = false;
//                this.flgSerGen = true;
//            }
//            docIngresoBean = doc;
//            imprimirFormatoPdfBorrar();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
//    public void imp() {
//        try{
//// Create a PDFFile from a File reference
//        File f = new File("c:\\juixe\\techknow.pdf");
//        FileInputStream fis = new FileInputStream(f);
//        FileChannel fc = fis.getChannel();
//        ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
//        PDFFile pdfFile = new PDFFile(bb); // Create PDF Print Page
//        PDFPrintPage pages = new PDFPrintPage(pdfFile);
//
//// Create Print Job
//        PrinterJob pjob = PrinterJob.getPrinterJob();
//        PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
//        pjob.setJobName(f.getName());
//        Book book = new Book();
//        book.append(pages, pf, pdfFile.getNumPages());
//        pjob.setPageable(book);
//
//// Send print job to default printer
//        pjob.print();
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void imprimirRecBanco(DocIngresoBean doc, Integer dato) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
            if (doc != null) {
                cta = cuentasPorCobrarService.validarDocIngresoEnCtaCte(doc.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            } else if (doc == null) {
                this.flgPension = true;
                this.flgSerGen = false;
            }
            if (cta != null) {
                if (cta.getIdCtasXCobrar() != null) {
                    this.flgPension = true;
                    this.flgSerGen = false;
                }
            } else {
                this.flgPension = false;
                this.flgSerGen = true;
            }
            if (dato.equals(1)) {
                docIngresoBean = doc;
                docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                docIngresoService.modificarEstadoImpreso(docIngresoBean);
                imprimirFormatoPdf();
            } else if (dato.equals(2)) {
                if (!listDocIngresoBean.isEmpty()) {
                    for (DocIngresoBean docin : listDocIngresoBean) {
                        docin.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                        docIngresoService.modificarEstadoImpreso(docin);
                    }
                    imprimirFormatoPdfBancoMasivo();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirDocumento() {
        try {
            this.flgBtnImprimir = Boolean.TRUE;
            System.out.println("flgBtnImprimir.." + flgBtnImprimir);
            imprimirFormatoPdf();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirDocumento2() {
        try {
            System.out.println("xxxxxxxxxxxxxxxxxxx");
            this.flgBtnImprimir = Boolean.TRUE;
            RequestContext.getCurrentInstance().update("form:btnImprimir");
            RequestContext.getCurrentInstance().update("form:btnCerrar");
            imprimirFormatoPdf();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirFormatoPdf() {
        ServletOutputStream out = null;
        try {
//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            if (flgPension.equals(true)) {
                listaIdsDocIngreso = new ArrayList<>();
                if (listDetDocIngreso != null) {
                    if (!listDetDocIngreso.isEmpty()) {
                        for (DetDocIngresoBean listDetDocIngreso1 : listDetDocIngreso) {
                            listaIdsDocIngreso.add(listDetDocIngreso1.getIdDocIngreso());
                        }
                    } else {
                        listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                    }
                } else {
                    listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                }

                Integer formato = 0;
                formato = docIngresoService.obtenerFormatoDocIngresoPension(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                //foramto=1 -> formato de caja
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                //modelo simple con cabecera
                System.out.println("formato" + formato);
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoPension.jasper");
//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5.jasper");//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5_DobleCuerpo.jasper");//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5_DobleCuerpo.jasper");

                //modelo simple sin cabecera
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            //                          getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS.jasper");
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoPensionCHAMPS_v2.jasper");
                }
                //modelo dos cuerpos con cabecera
                if (formato.equals(1)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5.jasper");
//                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5_DobleCuerpo.jasper");
                    //modelo dos cuerpos sin cabecera
                    if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS_v2.jasper");
//                                getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS.jasper"); 
                    }
                }
                System.out.println(archivoJasper);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                if (formato.equals(0)) {
                    listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForForSimple(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                } else {
                    listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                    docIngresoService.cambiarFechaImpresion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                    if (!listaDocIngresoRep.isEmpty()) {
                        System.out.println("qr" + listaDocIngresoRep.get(0).getQr());
                        for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                            List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                            listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca());
                            listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                        }
                    }
                }
                //se acaba de sacar fecha de impreion en formato simple y paso mas arriba
//                docIngresoService.cambiarFechaImpresion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
//                System.out.println(archivoJasper); 
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            } else {
                listaIdsDocIngreso = new ArrayList<>();
                if (listDetDocIngreso != null) {
                    if (!listDetDocIngreso.isEmpty()) {
                        for (DetDocIngresoBean listDetDocIngreso1 : listDetDocIngreso) {
                            listaIdsDocIngreso.add(listDetDocIngreso1.getIdDocIngreso());
                        }
                    } else {
                        listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                    }
                } else {
                    listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoSANJOC.jasper");
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoLaser2017.jasper");
//                            getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoCHAMPS7.jasper");
                }
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                DocIngresoBean docIngreso = new DocIngresoBean();
                docIngreso.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
                System.out.println(">>> : " + docIngreso.getNombreDiscente());
                docIngreso.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                listaDocIngresoRep = docIngresoService.obtenerDocIngreso(docIngreso);
                if (!listaDocIngresoRep.isEmpty()) {
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    listaRepDetDocIngreso = docIngresoService.obtenerFormatoDetalleDocIngresoConDscto(listaDocIngresoRep.get(0).getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(0).getFlgDscto());
                    listaDocIngresoRep.get(0).setListaDetalle(listaRepDetDocIngreso);
                }
                docIngresoService.cambiarFechaImpresion(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);
//                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);
//                JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "inline; filename=Recibo.pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
                //nofunca
//                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);
//                JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
//                aaaa("D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
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

    public void imprimirFormatoPdfBancoMasivo() {
        ServletOutputStream out = null;
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println(">>>>" + listDocIngresoBean.size());
            if (flgPension.equals(true)) {
                listaIdsDocIngreso = new ArrayList<>();
                if (listDetDocIngreso != null) {
                    if (!listDetDocIngreso.isEmpty()) {
                        System.out.println(">>>" + 1);
                        for (DetDocIngresoBean listDetDocIngreso1 : listDetDocIngreso) {
                            listaIdsDocIngreso.add(listDetDocIngreso1.getIdDocIngreso());
                        }
                    } else {
                        if (!listDocIngresoBean.isEmpty()) {
                            System.out.println(">>>" + 2);
                            for (DocIngresoBean doc : listDocIngresoBean) {
                                listaIdsDocIngreso.add(doc.getIdDocIngreso());
                            }
                        }
                    }
                } else {
                    if (!listDocIngresoBean.isEmpty()) {
                        System.out.println(">>>" + 3);
                        for (DocIngresoBean doc : listDocIngresoBean) {
                            listaIdsDocIngreso.add(doc.getIdDocIngreso());
                        }
                    }
                }
                System.out.println("lista de detalle >>>> " + listaIdsDocIngreso.size());
                Integer formato = 0;
                formato = docIngresoService.obtenerFormatoDocIngresoPension(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                //foramto=1 -> formato de caja
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoPension.jasper");
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoPensionCHAMPS.jasper");

                }
                if (formato.equals(1)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5.jasper");
                }
                System.out.println(archivoJasper);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoFor(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);

                if (!listaDocIngresoRep.isEmpty()) {
                    for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca());
                        listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                    }
                }

                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            } else {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoSANJOC.jasper");
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoCHAMPS33.jasper");
                }
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                DocIngresoBean docIngreso = new DocIngresoBean();
                docIngreso.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
                System.out.println(">>> : " + docIngreso.getNombreDiscente());
                docIngreso.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                listaDocIngresoRep = docIngresoService.obtenerDocIngreso(docIngreso);
                if (!listaDocIngresoRep.isEmpty()) {
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    listaRepDetDocIngreso = docIngresoService.obtenerFormatoDetalleDocIngresoConDscto(listaDocIngresoRep.get(0).getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(0).getFlgDscto());
                    listaDocIngresoRep.get(0).setListaDetalle(listaRepDetDocIngreso);
                }

                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);
//                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);
//                JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "inline; filename=Recibo.pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
                //nofunca
//                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);
//                JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
//                aaaa("D:/GilmarLam/pruebas/" + docIngresoBean.getSerie() + "-" + docIngresoBean.getNroDoc() + ".pdf");
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

    public void changeIndividuo() {
        try {
            PersonalService personalService = BeanFactory.getPersonalService();
            listaPersonalFiltroBean = personalService.obtenerPorUnidadNegocio(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void changeFlgEst() {
        try {
            Integer flg = 1;
            System.out.println(flgSoloEst);
            if (flgSoloEst.equals(Boolean.TRUE)) {
                flg = 1;
                getPersonaFiltroBean().setFiltro(flg);
                buscarPersona();
            } else {
                getPersonaFiltroBean().setFiltro(flg);
                buscarPersona();
                flg = 0;
                cargarConceptosDefaultPersonaExterna();
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void rowSelectPersonal(SelectEvent event) {
        try {
            personalBean = getPersonalFiltroBean();
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();

            Object dscto = MaristaConstantes.DSCTO_EXT;
            listaConceptoUniNegBean = conceptoUniNegService.
                    obtenerConceptoExterno(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), dscto);
            this.addMes = null;
            this.flgDisableGenCod = Boolean.TRUE;
            CodigoService codigoService = BeanFactory.getCodigoService();
            listDetDocIngreso = new ArrayList<>();
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            CodigoBean codigoTipoModoPago = new CodigoBean();
            CodigoBean codigoTipoMoneda = new CodigoBean();

            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Colegio", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "POS", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            } else {
                codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Efectivo", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
            }
            codigoTipoMoneda = codigoService.obtenerPorCodigo(new CodigoBean(0, "Soles", new TipoCodigoBean(MaristaConstantes.TIP_MON)));
            docIngresoBean.setIdTipoLugarPago(codigoTipoLugarPago);
            docIngresoBean.setIdTipoModoPago(codigoTipoModoPago);
            docIngresoBean.setIdTipoMoneda(codigoTipoMoneda);
            tipoMoneda = codigoTipoMoneda;
            this.setFlgPagoParDol(false);
            resetearMontoDol();
            listaCentroResponsabilidadBean = new ArrayList<>();
            personaBean = new PersonaBean();
            bloquearDolares();
            if (personalBean.getIdPersonal() != null) {
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                getDocEgresoBean().getSolicitudCajaCHBean().setPersonalBean(personalBean);
                getDocEgresoBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                getDocEgresoBean().getSolicitudCajaCHBean().setTipoEstRend(new CodigoBean(MaristaConstantes.COD_REND_INICIADO));
                listaSolicitudCajaCHBean = solicitudCajaCHService.obtenerPorTipoEstRend(getDocEgresoBean().getSolicitudCajaCHBean());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //======================================= REIMPRESION MASIVO =================================================//
    public void cargarDatosReimpresionMasivo() {
        try {
            fechaActual = new GregorianCalendar();
            getDocIngresoFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocIngresoFiltroBean().setFechaFin(fechaActual.getTime());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoEstadpDocIng = new ArrayList<>();
            listaTipoEstadpDocIng = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_DOCING));
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            getDocIngresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            //validar perfil
            PerfilService perfilService = BeanFactory.getPerfilService();
            getListaPerfilBean();
            listaPerfilBean = perfilService.obtenerUsarioPerfil(usuarioLoginBean);
//            docIngresoFiltroBean.getIdTipoLugarPago().setIdCodigo(MaristaConstantes.COD_LUGAR_BANCO);
            for (int i = 0; i < listaPerfilBean.size(); i++) {
                if (listaPerfilBean.get(i).getNombre().equals(MaristaConstantes.ANULAR_SOLI) || usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals("BARINA")) {
                    this.flgSistemas = true;
                    break;
                } else {
                    this.flgSistemas = false;
                }
            }
            obtenerListaMeses();
            setSelFil(0);
            selFiltro();
            getFormatoRecibo();
            setFormatoRecibo(1);
            getEstadoImpresion();
            setEstadoImpresion(1);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroDocIngresoMasivo() {
        try {
            int estado = 0;
            System.out.println("1:" + docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            if (docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo() != null && !docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getTipoStatusDocIng().setIdCodigo(docIngresoFiltroBean.getTipoStatusDocIng().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoDoc().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoDoc().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoDoc().setIdCodigo(docIngresoFiltroBean.getIdTipoDoc().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoModoPago().setIdCodigo(docIngresoFiltroBean.getIdTipoModoPago().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo() != null && !docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo().equals(0)) {
                docIngresoFiltroBean.getIdTipoLugarPago().setIdCodigo(docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getSerie() != null && !docIngresoFiltroBean.getSerie().equals("")) {
                docIngresoFiltroBean.setSerie(docIngresoFiltroBean.getSerie());
                estado = 1;
            }
            if (docIngresoFiltroBean.getNroDocIni() != null && !docIngresoFiltroBean.getNroDocIni().equals("")) {
                docIngresoFiltroBean.setNroDocIni(docIngresoFiltroBean.getNroDocIni());
                estado = 1;
            }
            if (docIngresoFiltroBean.getNroDocFin() != null && !docIngresoFiltroBean.getNroDocFin().equals("")) {
                docIngresoFiltroBean.setNroDocFin(docIngresoFiltroBean.getNroDocFin());
                estado = 1;
            }
            if (docIngresoFiltroBean.getCodDiscente() != null && !docIngresoFiltroBean.getCodDiscente().equals("")) {
                docIngresoFiltroBean.setCodDiscente(docIngresoFiltroBean.getCodDiscente());
                estado = 1;
            }
            if (docIngresoFiltroBean.getCodigo() != null && !docIngresoFiltroBean.getCodigo().equals("")) {
                docIngresoFiltroBean.setCodigo(docIngresoFiltroBean.getCodigo());
                estado = 1;
            }
            if (docIngresoFiltroBean.getFechaInicio() != null) {
                docIngresoFiltroBean.setFechaInicio(docIngresoFiltroBean.getFechaInicio());
                estado = 1;
            }
            if (docIngresoFiltroBean.getFechaFin() != null) {
                docIngresoFiltroBean.setFechaFin(docIngresoFiltroBean.getFechaFin());
                estado = 1;
            }
            if (docIngresoFiltroBean.getEstadoImpresion() != null) {
                docIngresoFiltroBean.setEstadoImpresion(docIngresoFiltroBean.getEstadoImpresion());
                estado = 1;
            }
            if (docIngresoFiltroBean.getNombreDiscente() != null && !docIngresoFiltroBean.getNombreDiscente().equals("")) {
                docIngresoFiltroBean.setNombreDiscente(docIngresoFiltroBean.getNombreDiscente());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listDocIngresoBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                System.out.println("2:" + docIngresoFiltroBean.getIdTipoLugarPago().getIdCodigo());
                listDocIngresoBean = new ArrayList<>();
                listDocIngresoBean = docIngresoService.obtenerFiltroDocIngresoMasivo(docIngresoFiltroBean);
                if (listDocIngresoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    resetMontosRep();
                } else {
                    obtenerMontosRep("docIng");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerformatoRecibo() {
        try {
            System.out.println(">>>" + getFormatoRecibo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void reimpresionReciboMasivo(DocIngresoBean doc) {
        try {
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            CuentasPorCobrarBean cta = new CuentasPorCobrarBean();
            cta = cuentasPorCobrarService.validarDocIngresoEnCtaCte(doc.getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cta != null) {
                if (cta.getIdCtasXCobrar() != null) {
                    this.flgPension = true;
                    this.flgSerGen = false;
                }
            } else {
                this.flgPension = false;
                this.flgSerGen = true;
            }
            docIngresoBean = doc;
            System.out.println(">>>>>" + getFormatoRecibo());
            imprimirFormatoPdfMasivo(getFormatoRecibo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarDatDocIngresoMasivo() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            docIngresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            docIngresoService.cambiarDatBasicosDocIngreso(docIngresoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            obtenerFiltroDocIngreso();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarValAdmTodos() {
        try {
            if (flgImpresionMasiva) {
                for (DocIngresoBean docIng : listDocIngresoBean) {
                    docIng.setFlgImpresionMasiva(true);
                }
            } else if (!flgImpresionMasiva) {
                for (DocIngresoBean docIng : listDocIngresoBean) {
                    docIng.setFlgImpresionMasiva(false);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirFormatoMasivo() {
        ServletOutputStream out = null;
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            listaIdsDocIngreso = new ArrayList<>();
            if (!listDocIngresoBean.isEmpty()) {
                for (DocIngresoBean docIng : listDocIngresoBean) {
                    if (docIng.getFlgImpresionMasiva()) {
                        if (docIng.getFlgImpresion() == null) {
                            docIng.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            docIng.setSerie(MaristaConstantes.serie_numdoc);
                            Integer numDoc = docIngresoService.obtenerMaxNroDoc(docIng), var = 0;
                            System.out.println(">>>>>>" + numDoc);
                            docIng.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            docIng.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
                            docIng.setSerie(MaristaConstantes.serie_numdoc);
                            docIng.setFlgImpresion(2);
                            Integer numeroSerie = numDoc + 1;
                            docIng.setNroDoc(numeroSerie.toString());
                            System.out.println(numeroSerie + ">>>>>" + numDoc);
                            docIngresoService.modificarNroDocSerie(docIng);
                            listaIdsDocIngreso.add(docIng.getIdDocIngreso());
                        }
                    }
                }
                Integer formato = 0;
                formato = docIngresoService.obtenerFormatoDocIngresoPension(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = "";
                if (this.formatoRecibo.equals(1)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecSANJOCA5.jasper");
                } else if (this.formatoRecibo.equals(2)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteSANJOCA5.jasper");
                } else if (this.formatoRecibo.equals(3)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteUsuarioSANJOCA5.jasper");
                }
                System.out.println(archivoJasper);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForMasivo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                if (!listaDocIngresoRep.isEmpty()) {
                    for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngresoMasivo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca(), listaDocIngresoRep.get(i).getInfoMonto());
                        listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                    }
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
                parametros.put("rutaImagen", absoluteWebPath);
                parametros.put("SUBREPORT_DIR", ruta);

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            }
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

    public void imprimirFormatoPdfMasivo(Integer dato) {
        ServletOutputStream out = null;
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            if (docIngresoBean.getFlgImpresion() == null) {
                System.out.println(">>>>  nulo");
                docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                docIngresoBean.setSerie(MaristaConstantes.serie_numdoc);
                Integer numDoc = docIngresoService.obtenerMaxNroDoc(docIngresoBean), var = 0;
                System.out.println(">>>>>>" + numDoc);
                docIngresoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                docIngresoBean.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
                docIngresoBean.setSerie(MaristaConstantes.serie_numdoc);
                docIngresoBean.setFlgImpresion(2);
                Integer numeroSerie = numDoc + 1;
                docIngresoBean.setNroDoc(numeroSerie.toString());
                System.out.println(numeroSerie + ">>>>>" + numDoc);
                docIngresoService.modificarNroDocSerie(docIngresoBean);
            } else {
                System.out.println(">>>> no nulo");
            }
            if (flgPension.equals(true)) {
                listaIdsDocIngreso = new ArrayList<>();
                if (listDetDocIngreso != null) {
                    if (!listDetDocIngreso.isEmpty()) {
                        for (DetDocIngresoBean listDetDocIngreso1 : listDetDocIngreso) {
                            listaIdsDocIngreso.add(listDetDocIngreso1.getIdDocIngreso());
                            System.out.println(">>>>1");
                        }
                    } else {
                        listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                        System.out.println(">>>>2");
                    }
                } else {
                    listaIdsDocIngreso.add(docIngresoBean.getIdDocIngreso());
                    System.out.println(">>>>3");
                }

                Integer formato = 0;
                formato = docIngresoService.obtenerFormatoDocIngresoPension(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = "";
                if (this.formatoRecibo.equals(1)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecSANJOCA5.jasper");
                } else if (this.formatoRecibo.equals(2)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteSANJOCA5.jasper");
                } else if (this.formatoRecibo.equals(3)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecPendienteUsuarioSANJOCA5.jasper");
                }
                if (formato.equals(1)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5.jasper");
                }
                System.out.println(archivoJasper);
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForMasivo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);

                if (!listaDocIngresoRep.isEmpty()) {
                    for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngresoMasivo(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca(), listaDocIngresoRep.get(i).getInfoMonto());
                        listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                    }
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));
                parametros.put("rutaImagen", absoluteWebPath);
                parametros.put("SUBREPORT_DIR", ruta);

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            } else {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoSANJOC.jasper");
                if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoCHAMPS33.jasper");
                }
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                List<DocIngresoRepBean> listaDocIngresoRep = new ArrayList<>();
                DocIngresoBean docIngreso = new DocIngresoBean();
                docIngreso.setIdDocIngreso(docIngresoBean.getIdDocIngreso());
                System.out.println(">>> : " + docIngreso.getNombreDiscente());
                docIngreso.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                listaDocIngresoRep = docIngresoService.obtenerDocIngreso(docIngreso);
                if (!listaDocIngresoRep.isEmpty()) {
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    listaRepDetDocIngreso = docIngresoService.obtenerFormatoDetalleDocIngresoConDscto(listaDocIngresoRep.get(0).getIdDocIngreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(0).getFlgDscto());
                    listaDocIngresoRep.get(0).setListaDetalle(listaRepDetDocIngreso);
                }
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
                response.setHeader("Content-Disposition", "inline; filename=Recibo.pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    // ========================================================================================================== //
    public void selFiltro() {
        try {
            if (selFil.equals(0)) {
                Calendar fechaActual = new GregorianCalendar();
                flgConFec = true;
                flgConAnM = false;
                docIngresoFiltroBean.setMes(0);
                docIngresoFiltroBean.setAnio(0);
                docIngresoFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                docIngresoFiltroBean.setFechaInicio(fechaActual.getTime());
                docIngresoFiltroBean.setFechaFin(fechaActual.getTime());
            } else if (selFil.equals(1)) {
                flgConAnM = true;
                flgConFec = false;
                docIngresoFiltroBean.setFechaInicio(null);
                docIngresoFiltroBean.setFechaFin(null);
                docIngresoFiltroBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
                docIngresoFiltroBean.setMes(null);
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
    // ========================================================================================================== //

    public Boolean getFlgModPagoAmbos() {
        return flgModPagoAmbos;
    }

    public void setFlgModPagoAmbos(Boolean flgModPagoAmbos) {
        this.flgModPagoAmbos = flgModPagoAmbos;
    }

    public Double getMontoTotalDolDouble() {
        return montoTotalDolDouble;
    }

    public void setMontoTotalDolDouble(Double montoTotalDolDouble) {
        this.montoTotalDolDouble = montoTotalDolDouble;
    }

    public Double getMontoTotalSolDouble() {
        return montoTotalSolDouble;
    }

    public void setMontoTotalSolDouble(Double montoTotalSolDouble) {
        this.montoTotalSolDouble = montoTotalSolDouble;
    }

    public Double getMontoTotalPos1Double() {
        return montoTotalPos1Double;
    }

    public void setMontoTotalPos1Double(Double montoTotalPos1Double) {
        this.montoTotalPos1Double = montoTotalPos1Double;
    }

    public Double getMontoTotalPos2Double() {
        return montoTotalPos2Double;
    }

    public void setMontoTotalPos2Double(Double montoTotalPos2Double) {
        this.montoTotalPos2Double = montoTotalPos2Double;
    }

    public String getMontoParcialDol() {
        return montoParcialDol;
    }

    public void setMontoParcialDol(String montoParcialDol) {
        this.montoParcialDol = montoParcialDol;
    }

    public Double getMontoParcialDolDouble() {
        return montoParcialDolDouble;
    }

    public void setMontoParcialDolDouble(Double montoParcialDolDouble) {
        this.montoParcialDolDouble = montoParcialDolDouble;
    }

    public Boolean getFlgPagoParDol() {
        return flgPagoParDol;
    }

    public void setFlgPagoParDol(Boolean flgPagoParDol) {
        this.flgPagoParDol = flgPagoParDol;
    }

    public Boolean getFlgModPOS() {
        return flgModPOS;
    }

    public void setFlgModPOS(Boolean flgModPOS) {
        this.flgModPOS = flgModPOS;
    }

    public Double getMontoCongregacionSolDouble() {
        return montoCongregacionSolDouble;
    }

    public void setMontoCongregacionSolDouble(Double montoCongregacionSolDouble) {
        this.montoCongregacionSolDouble = montoCongregacionSolDouble;
    }

    public Double getMontoCongregacionDolDouble() {
        return montoCongregacionDolDouble;
    }

    public void setMontoCongregacionDolDouble(Double montoCongregacionDolDouble) {
        this.montoCongregacionDolDouble = montoCongregacionDolDouble;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBean() {
        if (listaCentroResponsabilidadBean == null) {
            listaCentroResponsabilidadBean = new ArrayList<>();
        }
        return listaCentroResponsabilidadBean;
    }

    public void setListaCentroResponsabilidadBean(List<CentroResponsabilidadBean> listaCentroResponsabilidadBean) {
        this.listaCentroResponsabilidadBean = listaCentroResponsabilidadBean;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
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

    public void llamarCalculadora() {
        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec("calc");
            p.waitFor();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Boolean getFlgFactura() {
        return flgFactura;
    }

    public void setFlgFactura(Boolean flgFactura) {
        this.flgFactura = flgFactura;
    }

    public Boolean getFlgPension() {
        return flgPension;
    }

    public void setFlgPension(Boolean flgPension) {
        this.flgPension = flgPension;
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

    public List<ProgramacionBean> getListaProgramacionSessionBean() {
        if (listaProgramacionSessionBean == null) {
            listaProgramacionSessionBean = new ArrayList<>();
        }
        return listaProgramacionSessionBean;
    }

    public void setListaProgramacionSessionBean(List<ProgramacionBean> listaProgramacionSessionBean) {
        this.listaProgramacionSessionBean = listaProgramacionSessionBean;
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

    public DocIngresoBean getDocIngresoFiltroBean() {
        if (docIngresoFiltroBean == null) {
            docIngresoFiltroBean = new DocIngresoBean();
        }
        return docIngresoFiltroBean;
    }

    public void setDocIngresoFiltroBean(DocIngresoBean docIngresoFiltroBean) {
        this.docIngresoFiltroBean = docIngresoFiltroBean;
    }

    public List<DocIngresoBean> getListDocIngresoBean() {
        if (listDocIngresoBean == null) {
            listDocIngresoBean = new ArrayList<>();
        }
        return listDocIngresoBean;
    }

    public void setListDocIngresoBean(List<DocIngresoBean> listDocIngresoBean) {
        this.listDocIngresoBean = listDocIngresoBean;
    }

    public List<CodigoBean> getListaTipoEstadpDocIng() {
        if (listaTipoEstadpDocIng == null) {
            listaTipoEstadpDocIng = new ArrayList<>();
        }
        return listaTipoEstadpDocIng;
    }

    public void setListaTipoEstadpDocIng(List<CodigoBean> listaTipoEstadpDocIng) {
        this.listaTipoEstadpDocIng = listaTipoEstadpDocIng;
    }

    public Double getTotPOS1() {
        return totPOS1;
    }

    public void setTotPOS1(Double totPOS1) {
        this.totPOS1 = totPOS1;
    }

    public Double getTotPOS2() {
        return totPOS2;
    }

    public void setTotPOS2(Double totPOS2) {
        this.totPOS2 = totPOS2;
    }

    public Double getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(Double totSoles) {
        this.totSoles = totSoles;
    }

    public Double getTolDolares() {
        return tolDolares;
    }

    public void setTolDolares(Double tolDolares) {
        this.tolDolares = tolDolares;
    }

    public Double getTolDolaresConvSoles() {
        return tolDolaresConvSoles;
    }

    public void setTolDolaresConvSoles(Double tolDolaresConvSoles) {
        this.tolDolaresConvSoles = tolDolaresConvSoles;
    }

    public Double llSol() {
        return totalSol;
    }

    public void setTotalSol(Double totalSol) {
        this.totalSol = totalSol;
    }

    public Double getTotalDol() {
        return totalDol;
    }

    public void setTotalDol(Double totalDol) {
        this.totalDol = totalDol;
    }

    public ConceptoUniNegBean getConceptoUniNegBean() {
        if (conceptoUniNegBean == null) {
            conceptoUniNegBean = new ConceptoUniNegBean();
        }
        return conceptoUniNegBean;
    }

    public void setConceptoUniNegBean(ConceptoUniNegBean conceptoUniNegBean) {
        this.conceptoUniNegBean = conceptoUniNegBean;
    }

    public Boolean getFlgAnulado() {
        return flgAnulado;
    }

    public void setFlgAnulado(Boolean flgAnulado) {
        this.flgAnulado = flgAnulado;
    }

    public Integer getTipIndividuo() {
        return tipIndividuo;
    }

    public void setTipIndividuo(Integer tipIndividuo) {
        this.tipIndividuo = tipIndividuo;
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

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
    }

    public DocEgresoBean getDocEgresoBean() {
        if (docEgresoBean == null) {
            docEgresoBean = new DocEgresoBean();
        }
        return docEgresoBean;
    }

    public void setDocEgresoBean(DocEgresoBean docEgresoBean) {
        this.docEgresoBean = docEgresoBean;
    }

    public Boolean getFlgSerGen() {
        return flgSerGen;
    }

    public void setFlgSerGen(Boolean flgSerGen) {
        this.flgSerGen = flgSerGen;
    }

    public Boolean getPosVisa() {
        return posVisa;
    }

    public void setPosVisa(Boolean posVisa) {
        this.posVisa = posVisa;
    }

    public Boolean getPosMC() {
        return posMC;
    }

    public void setPosMC(Boolean posMC) {
        this.posMC = posMC;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudCajaCHBean() {
        if (listaSolicitudCajaCHBean == null) {
            listaSolicitudCajaCHBean = new ArrayList<>();
        }
        return listaSolicitudCajaCHBean;
    }

    public void setListaSolicitudCajaCHBean(List<SolicitudCajaCHBean> listaSolicitudCajaCHBean) {
        this.listaSolicitudCajaCHBean = listaSolicitudCajaCHBean;
    }

    public CodigoBean getTipoMoneda() {
        if (tipoMoneda == null) {
            tipoMoneda = new CodigoBean();
        }
        return tipoMoneda;
    }

    public void setTipoMoneda(CodigoBean tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
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

    public Boolean getFlgSistemas() {
        return flgSistemas;
    }

    public void setFlgSistemas(Boolean flgSistemas) {
        this.flgSistemas = flgSistemas;
    }

    public List<Integer> getListaIdsDocIngreso() {
        if (listaIdsDocIngreso == null) {
            listaIdsDocIngreso = new ArrayList<>();
        }
        return listaIdsDocIngreso;
    }

    public void setListaIdsDocIngreso(List<Integer> listaIdsDocIngreso) {
        this.listaIdsDocIngreso = listaIdsDocIngreso;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public DetDocIngresoRepBean getDetDocIngresoRepFiltroBean() {
        if (detDocIngresoRepFiltroBean == null) {
            detDocIngresoRepFiltroBean = new DetDocIngresoRepBean();
        }
        return detDocIngresoRepFiltroBean;
    }

    public void setDetDocIngresoRepFiltroBean(DetDocIngresoRepBean detDocIngresoRepFiltroBean) {
        this.detDocIngresoRepFiltroBean = detDocIngresoRepFiltroBean;
    }

    public List<DetDocIngresoRepBean> getListaDetDocIngresoRepBean() {
        if (listaDetDocIngresoRepBean == null) {
            listaDetDocIngresoRepBean = new ArrayList<>();
        }
        return listaDetDocIngresoRepBean;
    }

    public void setListaDetDocIngresoRepBean(List<DetDocIngresoRepBean> listaDetDocIngresoRepBean) {
        this.listaDetDocIngresoRepBean = listaDetDocIngresoRepBean;
    }

    public DetDocIngresoRepBean getDetDocIngresoRepBean() {
        if (detDocIngresoRepBean == null) {
            detDocIngresoRepBean = new DetDocIngresoRepBean();
        }
        return detDocIngresoRepBean;
    }

    public void setDetDocIngresoRepBean(DetDocIngresoRepBean detDocIngresoRepBean) {
        this.detDocIngresoRepBean = detDocIngresoRepBean;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Boolean getSerGenCollapsed() {
        return serGenCollapsed;
    }

    public void setSerGenCollapsed(Boolean serGenCollapsed) {
        this.serGenCollapsed = serGenCollapsed;
    }

    public List<CodigoBean> getListaTipoLugarPago() {
        return listaTipoLugarPago;
    }

    public void setListaTipoLugarPago(List<CodigoBean> listaTipoLugarPago) {
        this.listaTipoLugarPago = listaTipoLugarPago;
    }

    public Boolean getFlgSoloEst() {
        return flgSoloEst;
    }

    public void setFlgSoloEst(Boolean flgSoloEst) {
        this.flgSoloEst = flgSoloEst;
    }

    public List<PlanContableBean> getListaPlanContable() {
        if (listaPlanContable == null) {
            listaPlanContable = new ArrayList<>();
        }
        return listaPlanContable;
    }

    public void setListaPlanContable(List<PlanContableBean> listaPlanContable) {
        this.listaPlanContable = listaPlanContable;
    }

    public Integer getAddMes() {
        return addMes;
    }

    public void setAddMes(Integer addMes) {
        this.addMes = addMes;
    }

    public List<ProgramacionDsctoBean> getListaProgramacionDsctoBean() {
        if (listaProgramacionDsctoBean == null) {
            listaProgramacionDsctoBean = new ArrayList<>();
        }
        return listaProgramacionDsctoBean;
    }

    public void setListaProgramacionDsctoBean(List<ProgramacionDsctoBean> listaProgramacionDsctoBean) {
        this.listaProgramacionDsctoBean = listaProgramacionDsctoBean;
    }

    public BigDecimal getDscto() {
        return dscto;
    }

    public void setDscto(BigDecimal dscto) {
        this.dscto = dscto;
    }

    public Boolean getFlgDisableGenCod() {
        return flgDisableGenCod;
    }

    public void setFlgDisableGenCod(Boolean flgDisableGenCod) {
        this.flgDisableGenCod = flgDisableGenCod;
    }

    public Boolean getFlgGenCod() {
        return flgGenCod;
    }

    public void setFlgGenCod(Boolean flgGenCod) {
        this.flgGenCod = flgGenCod;
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

    public Boolean getDisableCuenta() {
        return disableCuenta;
    }

    public void setDisableCuenta(Boolean disableCuenta) {
        this.disableCuenta = disableCuenta;
    }

    public Integer getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(Integer estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }

    public Integer getFormatoRecibo() {
        return formatoRecibo;
    }

    public void setFormatoRecibo(Integer formatoRecibo) {
        this.formatoRecibo = formatoRecibo;
    }

    public Boolean getFlgImpresionMasiva() {
        return flgImpresionMasiva;
    }

    public void setFlgImpresionMasiva(Boolean flgImpresionMasiva) {
        this.flgImpresionMasiva = flgImpresionMasiva;
    }

    public Boolean getFlgCajExt() {
        return flgCajExt;
    }

    public void setFlgCajExt(Boolean flgCajExt) {
        this.flgCajExt = flgCajExt;
    }

    public Boolean getFlgBtnImprimir() {
        return flgBtnImprimir;
    }

    public void setFlgBtnImprimir(Boolean flgBtnImprimir) {
        this.flgBtnImprimir = flgBtnImprimir;
    }

    public DetDocIngresoBean getDetDocIngresoBean() {
        if (detDocIngresoBean == null) {
            detDocIngresoBean = new DetDocIngresoBean();
        }
        return detDocIngresoBean;
    }

    public void setDetDocIngresoBean(DetDocIngresoBean detDocIngresoBean) {
        this.detDocIngresoBean = detDocIngresoBean;
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

    public List<CajaGenBean> getListaCajaGenBean() {
        if (listaCajaGenBean == null) {
            listaCajaGenBean = new ArrayList<>();
        }
        return listaCajaGenBean;
    }

    public void setListaCajaGenBean(List<CajaGenBean> listaCajaGenBean) {
        this.listaCajaGenBean = listaCajaGenBean;
    }

    public List<CodigoBean> getListaTipoStatusDocIng() {
        if (listaTipoStatusDocIng == null) {
            listaTipoStatusDocIng = new ArrayList<>();
        }
        return listaTipoStatusDocIng;
    }

    public void setListaTipoStatusDocIng(List<CodigoBean> listaTipoStatusDocIng) {
        this.listaTipoStatusDocIng = listaTipoStatusDocIng;
    }

    public List<CodigoBean> getListaTipoDscto() {
        if (listaTipoDscto == null) {
            listaTipoDscto = new ArrayList<>();
        }
        return listaTipoDscto;
    }

    public void setListaTipoDscto(List<CodigoBean> listaTipoDscto) {
        this.listaTipoDscto = listaTipoDscto;
    }

    public Double getMontoDscPro() {
        return montoDscPro;
    }

    public void setMontoDscPro(Double montoDscPro) {
        this.montoDscPro = montoDscPro;
    }

    public String getCajaGen() {
        return cajaGen;
    }

    public void setCajaGen(String cajaGen) {
        this.cajaGen = cajaGen;
    }

    public Integer getIdCajaGenVista() {
        return idCajaGenVista;
    }

    public void setIdCajaGenVista(Integer idCajaGenVista) {
        this.idCajaGenVista = idCajaGenVista;
    }

}
