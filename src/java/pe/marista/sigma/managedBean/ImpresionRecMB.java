/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ExporterFilter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.RecibosMoraBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.DetDocIngresoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoABRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoRepBean;
import pe.marista.sigma.bean.reporte.ImpresionReciboRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.ImpresoraService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.RecibosMoraService;
import pe.marista.sigma.service.UnidadNegocioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS001
 */
public class ImpresionRecMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of ImpresionRecMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaRecibosBean();
            getCuentasPorCobrarBean();
            Calendar miCalendario = Calendar.getInstance();
            periodo = miCalendario.get(Calendar.YEAR);
            periodoInicio = 2017;//desde ese año
            periodoFin = periodo;

            CodigoService codigoService = BeanFactory.getCodigoService();
            listaLugarPagoBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            setIdTipoLugarPago(codigoTipoLugarPago.getIdCodigo());

            listaMeses();
            listaOrden();

            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademico();
            listaNivelAcademico = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));
            listaTiempos();
            listaMontos();

            listaTipoStatusCtaCte = codigoService.obtenerPorTipoStatusCtaCte(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE));
            obtenerImpresora();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    private UsuarioBean usuarioLogin;
    private List<CuentasPorCobrarBean> listaRecibosBean;// lista de recibos
    private CuentasPorCobrarBean cuentasPorCobrarBean;// 

    //Filtros
    private List<CodigoBean> listaLugarPagoBean;
    private Integer idTipoLugarPago;

    //rangos de fecha
    private Date fechaInicio;
    private Date fechaFin;
    private Map<String, Integer> listaMeses;
    private Integer orden;
    private Map<String, Integer> listaOrden;

    //Filtros pagos a tiempo
    private Integer periodo; //periodo de la pension
    private Integer mesPension;

    //Filtros pagos a destiempo
    private List<MesBean> listaMesNoConsiderar;
    private Integer mesPago;

    private Integer periodoInicio;
    private Integer periodoFin;

    private List<NivelAcademicoBean> listaNivelAcademico;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<MatriculaBean> listaSeccionBean;
    private Integer idNivelAcademico;
    private Integer idGradoAcademico;
    private String seccion;
    private String nombre;
    private String nroDoc;
    private String serie;

    private Integer tiempoPago;
    private Integer recibosMora;

    private Map<String, Integer> listaTiemposPago;
    private Map<String, Integer> listaMontosMoras;
    private List<MesBean> listaMesAll;
    private Integer[] selectedMesesNoConsiderar = new Integer[12];
    private List<DocIngresoRepBean> listaDocIngresoRep;
    private Boolean valAdmTodos;
    private Integer cantValAdmTodos;
    private Boolean flgBtnImp;
    private Integer cantidadSinFlg;
    private Integer cantidadImpresos;
    private Integer cantidadNoImpresos;

    private Integer flgRecImpr = 1;
    private Integer tipoFormato = 1;

    private ImpresoraBean impresoraBean;
    private List<ImpresoraBean> listaImpresoraBean;

    private Boolean flgPdfPorAlumno = false;
    private List<CodigoBean> listaTipoStatusCtaCte;
    private List<DocIngresoBean> listaTalleresWebBean;
    private DocIngresoBean docIngresoBean;

    public void obtenerRecibosATiempo(Integer tiempo) {
        try {
            Integer estado = 0;
            valAdmTodos = Boolean.FALSE;
//            if (idTipoLugarPago != null && !idTipoLugarPago.equals(0)) {
//                idTipoLugarPago = getIdTipoLugarPago();
//                estado = 1;
//            }
            if (periodo != null) {
                periodo = getPeriodo();
                estado = 1;
            }
            if (mesPension != null) {
                mesPension = getMesPension();
                estado = 1;
            }
            if (fechaInicio != null) {
                fechaInicio = getFechaInicio();
                estado = 1;
            } else {
                setFechaInicio(null);
            }
            if (fechaFin != null) {
                fechaFin = getFechaFin();
                estado = 1;
            } else {
                setFechaFin(null);
            }
            if (orden != null) {
                orden = getOrden();
                estado = 1;
            }

            if (idNivelAcademico != null) {
                idNivelAcademico = getIdNivelAcademico();
                estado = 1;
            }

            if (idGradoAcademico != null) {
                idGradoAcademico = getIdGradoAcademico();
                estado = 1;
            }

            if (seccion != null && !seccion.trim().equals("")) {
                seccion = getSeccion();
                estado = 1;
            }

            if (nroDoc != null && !nroDoc.trim().equals("")) {
                nroDoc = getNroDoc();
                estado = 1;
            }
            if (serie != null && !serie.trim().equals("")) {
                serie = getSerie();
                estado = 1;
            }
            if (nombre != null && !nombre.trim().equals("")) {
                nombre = getNombre();
                estado = 1;
            }
            if (flgRecImpr != null) {
                flgRecImpr = getFlgRecImpr();
                estado = 1;
            } else {
                flgRecImpr = null;
            }
            obtenerFlg(flgRecImpr);

            if (estado == 1) {
                cantValAdmTodos = 0;
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                listaRecibosBean = new ArrayList<>();
                List<Integer> listaMesesNoConsiderar = new ArrayList<>();
                for (int i = 0; i < selectedMesesNoConsiderar.length; i++) {
                    listaMesesNoConsiderar.add(selectedMesesNoConsiderar[i]);
                    System.out.print(selectedMesesNoConsiderar[i] + "\t");
                }
                for (Integer list : listaMesesNoConsiderar) {
                    System.out.println("for i:" + list);
                }
                if (tiempo.equals(1)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosParaImprimirATiempo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, periodo, listaMesesNoConsiderar, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosParaImprimirATiempo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, periodo, null, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, flgRecImpr);
                        System.out.println("listaMesesNoConsiderar.isEmpty()");
                    }
                } else if (tiempo.equals(2)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosParaImprimirDespuesFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, periodo, mesPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, listaMesesNoConsiderar, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosParaImprimirDespuesFecha(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, periodo, mesPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, null, flgRecImpr);
                        System.out.println("listaMesesNoConsiderar.isEmpty()");
                    }
                } else if (tiempo.equals(3)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosAll(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, listaMesesNoConsiderar, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosAll(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, null, flgRecImpr);
                        System.out.println("listaMesesNoConsiderar.isEmpty()");
                    }
                } else if (tiempo.equals(4)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosDeudores(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, listaMesesNoConsiderar, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosDeudores(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, null, flgRecImpr);
                    }
                } else if (tiempo.equals(5)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosSinServicio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, listaMesesNoConsiderar, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosSinServicio(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, null, flgRecImpr);
                    }
                } else {
                    System.out.println("x");
                }

                System.out.println("size--->" + listaRecibosBean.size());
                if (listaRecibosBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaRecibosBean = new ArrayList<>();
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerRecibosTalleres() {
        try {
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            listaTalleresWebBean = docIngresoService.obtenerTalleresWeb(uniNeg, periodo, orden, serie, nroDoc, nombre, flgRecImpr);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerRecibosxMoras(Integer tiempo, Integer moras) {
        try {
            Integer estado = 0;
            valAdmTodos = Boolean.FALSE;
//            if (idTipoLugarPago != null && !idTipoLugarPago.equals(0)) {
//                idTipoLugarPago = getIdTipoLugarPago();
//                estado = 1;
//            }
            if (periodo != null) {
                periodo = getPeriodo();
                estado = 1;
            }
            if (mesPension != null) {
                mesPension = getMesPension();
                estado = 1;
            }
            if (fechaInicio != null) {
                fechaInicio = getFechaInicio();
                estado = 1;
            } else {
                setFechaInicio(null);
            }
            if (fechaFin != null) {
                fechaFin = getFechaFin();
                estado = 1;
            } else {
                setFechaFin(null);
            }
            if (orden != null) {
                orden = getOrden();
                estado = 1;
            }

            if (idNivelAcademico != null) {
                idNivelAcademico = getIdNivelAcademico();
                estado = 1;
            }

            if (idGradoAcademico != null) {
                idGradoAcademico = getIdGradoAcademico();
                estado = 1;
            }

            if (seccion != null && !seccion.trim().equals("")) {
                seccion = getSeccion();
                estado = 1;
            }

            if (nroDoc != null && !nroDoc.trim().equals("")) {
                nroDoc = getNroDoc();
                estado = 1;
            }
            if (serie != null && !serie.trim().equals("")) {
                serie = getSerie();
                estado = 1;
            }
            if (nombre != null && !nombre.trim().equals("")) {
                nombre = getNombre();
                estado = 1;
            }
            if (flgRecImpr != null) {
                flgRecImpr = getFlgRecImpr();
                estado = 1;
            } else {
                flgRecImpr = null;
            }
            obtenerFlg(flgRecImpr);

            if (estado == 1) {
                cantValAdmTodos = 0;
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                listaRecibosBean = new ArrayList<>();
                List<Integer> listaMesesNoConsiderar = new ArrayList<>();
                for (int i = 0; i < selectedMesesNoConsiderar.length; i++) {
                    listaMesesNoConsiderar.add(selectedMesesNoConsiderar[i]);
                    System.out.print(selectedMesesNoConsiderar[i] + "\t");
                }
                for (Integer list : listaMesesNoConsiderar) {
                    System.out.println("for i:" + list);
                }
                if (tiempo.equals(3)) {
                    if (!listaMesesNoConsiderar.isEmpty()) {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosAllMoras(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, listaMesesNoConsiderar, recibosMora, flgRecImpr);
                    } else {
                        listaRecibosBean = cuentasPorCobrarService.obtenerRecibosAllMoras(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                                idTipoLugarPago, fechaInicio, fechaFin, orden, idNivelAcademico, idGradoAcademico, seccion, serie, nroDoc, nombre, periodoInicio, periodoFin, null, recibosMora, flgRecImpr);
                        System.out.println("listaMesesNoConsiderar.isEmpty()");
                    }
                } else {
                    System.out.println("x");
                }

                System.out.println("size--->" + listaRecibosBean.size());
                if (listaRecibosBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaRecibosBean = new ArrayList<>();
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarFiltros() {
        try {
            System.out.println("limpiarFiltros--->");
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean codigoTipoLugarPago = new CodigoBean();
            codigoTipoLugarPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Banco", new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO)));
            setIdTipoLugarPago(codigoTipoLugarPago.getIdCodigo());
            listaGradoAcademicoFiltroBean = new ArrayList<>();
            listaSeccionBean = new ArrayList<>();
            idNivelAcademico = null;
            idGradoAcademico = null;
            seccion = null;
            nroDoc = null;
            serie = null;
            nombre = null;
            fechaInicio = null;
            fechaFin = null;
            tiempoPago = 1;
            recibosMora = 1;
            Calendar miCalendario = Calendar.getInstance();
            periodo = miCalendario.get(Calendar.YEAR);
            periodoInicio = 2017;//desde ese año
            periodoFin = periodo;
            mesPago = null;
            mesPension = null;
            listaMesesNoConsiderar();
            selectedMesesNoConsiderar = new Integer[12];
            listaRecibosBean = new ArrayList<>();
            listaDocIngresoRep = new ArrayList<>();
            flgRecImpr = 1;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void listaMeses() {
        listaMeses = new LinkedHashMap<>();
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
        listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
        listaMeses = Collections.unmodifiableMap(listaMeses);
    }

    public void listaOrden() {
        listaOrden = new LinkedHashMap<>();
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorNivelGradoSec", null), 1);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorFechaPagoAsc", null), 2);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorFechaPagoDesc", null), 3);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorNumeroRec", null), 4);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorApellidos", null), 5);
        listaOrden = Collections.unmodifiableMap(listaOrden);
    }

    public void listaTiempos() {
        if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo() != null) {
            listaTiemposPago = new LinkedHashMap<>();
            listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosATiempo", null), 1);
            listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosADespFecha", null), 2);
            listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosTodos", null), 3);
            listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaDeudores", null), 4);
            listaTiemposPago = Collections.unmodifiableMap(listaTiemposPago);
        } else {
            tiempoPago = 1;
            obtenerTiempo(tiempoPago);
        }
    }

    public void listaMontos() {
        listaMontosMoras = new LinkedHashMap<>();
        listaMontosMoras.put(MensajesBackEnd.getValueOfKey("etiquetaMayoraCinco", null), 1);
        listaMontosMoras.put(MensajesBackEnd.getValueOfKey("etiquetaMenoraCinco", null), 2);
        listaMontosMoras.put(MensajesBackEnd.getValueOfKey("etiquetaTodosMora", null), 3);
        listaMontosMoras = Collections.unmodifiableMap(listaMontosMoras);
        recibosMora = 1;
    }

    public void listaMesesNoConsiderar() {
        getListaMesAll();
        listaMesAll = new ArrayList<>();
//        listaMesAll.add(new MesBean(1, MaristaConstantes.ENERO));
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
        obtenerMeses();
    }

    public void obtenerMeses() {
        if (mesPago != null) {
            selectedMesesNoConsiderar = new Integer[12];
            for (int i = 3; i < mesPago; i++) {
                System.out.print("xxx " + i);
                selectedMesesNoConsiderar[i] = i;
            }
        } else {
            selectedMesesNoConsiderar = new Integer[12];
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(idNivelAcademico);
            if (listaGradoAcademicoFiltroBean.isEmpty()) {
                listaGradoAcademicoFiltroBean = new ArrayList<>();
                listaSeccionBean = new ArrayList<>();
                idGradoAcademico = null;
                seccion = null;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTiempo(Integer tiempo) {
        try {
            if (tiempo != null) {
                System.out.println("tiempo " + tiempo);
                tiempoPago = tiempo;
//                if (tiempoPago.equals(2)) {
                listaMesesNoConsiderar();
//                } else {
//                    listaMesAll = new ArrayList<>();
//                }
            } else {
                System.out.println("obtenerTiempo null");
            }
            listaRecibosBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorEstadoCtaCte() {
        try {
            listaTiemposPago = new LinkedHashMap<>();
            if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo() == 19401) {
                listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaDeudores", null), 4);
            } else if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo() == 19404) {
                listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosATiempo", null), 1);
                listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosADespFecha", null), 2);
                listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaPagosTodos", null), 3);
            } else if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo() == 19406) {
                listaTiemposPago.put(MensajesBackEnd.getValueOfKey("etiquetaSinServicio", null), 5);
            } else {
                System.out.println("obtenerTiempo null");
            }
            listaTiemposPago = Collections.unmodifiableMap(listaTiemposPago);
            tiempoPago = 1;
            obtenerTiempo(tiempoPago);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMesesAConsiderar() {
        try {
//            for (int i = 0; i < selectedMesesNoConsiderar.length; i++) {
//                System.out.print(selectedMesesNoConsiderar[i] + "\t");
//            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarValAdmTodos(Boolean flg) {
        try {
            if (cantValAdmTodos > 0) {
                if (flg.equals(Boolean.TRUE)) {
                    if (cantValAdmTodos % 2 == 0) {
                        valAdmTodos = Boolean.TRUE;
                        this.flgPdfPorAlumno = true;
                    } else {
                        valAdmTodos = Boolean.FALSE;
                        this.flgPdfPorAlumno = false;
                    }
                }
                cantValAdmTodos = cantValAdmTodos + 1;
            } else {
                valAdmTodos = Boolean.TRUE;
                this.flgPdfPorAlumno = true;
                cantValAdmTodos = cantValAdmTodos + 1;
            }

            System.out.println("después valAdmTodos " + valAdmTodos);
            if (valAdmTodos) {
                for (CuentasPorCobrarBean list : listaRecibosBean) {
                    list.getDocIngresoBean().setFlgImpresionMasiva(Boolean.TRUE);

                }
            } else {
                for (CuentasPorCobrarBean list : listaRecibosBean) {
                    list.getDocIngresoBean().setFlgImpresionMasiva(Boolean.FALSE);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarPdfUnico() {
        try {
            Integer cantSinFlg = 0;
            Integer cantImpresos = 0;
            Integer cantNoImpresos = 0;
            for (CuentasPorCobrarBean ctas : listaRecibosBean) {
                if (ctas.getDocIngresoBean().getFlgImpresionMasiva() == null) {
                    ctas.getDocIngresoBean().setFlgImpresionMasiva(Boolean.FALSE);
                }
                if (ctas.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                    if (ctas.getDocIngresoBean().getFechaImpresion() != null) {
                        cantImpresos = cantImpresos + 1;
                    } else {
                        cantNoImpresos = cantNoImpresos + 1;
                    }
                } else {
                    cantSinFlg = cantSinFlg + 1;
                }
            }
            if ((cantImpresos + cantNoImpresos) > 0) {
                flgPdfPorAlumno = Boolean.TRUE;
            } else {
                flgPdfPorAlumno = Boolean.FALSE;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCantidadRecibos() {
        try {
            Integer cantSinFlg = 0;
            Integer cantImpresos = 0;
            Integer cantNoImpresos = 0;
//            cantSinFlg = listaRecibosBean.size();

            for (CuentasPorCobrarBean ctas : listaRecibosBean) {
                System.out.println("aaa: " + ctas.getDocIngresoBean().getFlgImpresionMasiva());
                if (ctas.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                    if (ctas.getDocIngresoBean().getFechaImpresion() != null) {
                        cantImpresos = cantImpresos + 1;
                    } else {
                        cantNoImpresos = cantNoImpresos + 1;
                    }
                } else {
                    cantSinFlg = cantSinFlg + 1;
                }
            }
            if ((cantImpresos + cantNoImpresos) > 0) {
                flgBtnImp = Boolean.FALSE;
            } else {
                flgBtnImp = Boolean.TRUE;
            }
            System.out.println("flgBtnImp " + flgBtnImp);
            cantidadSinFlg = cantSinFlg;
            cantidadNoImpresos = cantNoImpresos;
            cantidadImpresos = cantImpresos;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerIdGradoAca(Integer id) {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            System.out.println("idgrado " + id);
            listaSeccionBean = matriculaService.obtenerSeccionPorGrado(id, periodo, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFlg(Integer flg) {
        try {
            System.out.println("flg " + flg);
            flgRecImpr = flg;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String obtenerImpresora() {
        String pagina = null;
        try {

            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            listaImpresoraBean = new ArrayList<>();
            System.out.println("unineg " + usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaImpresoraBean = impresoraService.obtenerTodosActivos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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

    public void imprimirRecibosPdfEstudianteUnico() {
        try {
            File carpeta1 = new File("c://SigmaDocumentos//Recibos_PorAlumno//");
            if (carpeta1.exists()) {
                carpeta1 = new File(carpeta1 + File.separator + Calendar.getInstance().get(Calendar.YEAR));
                carpeta1 = new File(carpeta1 + File.separator + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                carpeta1.mkdirs();
            }
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            if (listaRecibosBean != null) {
                if (!listaRecibosBean.isEmpty()) {
                    for (CuentasPorCobrarBean lista : listaRecibosBean) {
                        if (lista.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getDocIngresoBean().getIdDocIngreso());
                        } else {
                        }
                    }
                }
            }
            for (Integer l : listaIdsDocIngreso) {

                //foramto=1 -> formato de caja
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                //modelo dos cuerpos con cabecera
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteCtasPorAlumno.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                listaDocIngresoRep = new ArrayList<>();
                listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForSinOrderByUnico(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                        l, this.orden);
                if (!listaDocIngresoRep.isEmpty()) {
                    for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        //SIN MMORA 
                        listaDocIngresoRep.get(i).setMora(0);
                        listaDocIngresoRep.get(i).setDscto(0);
                        listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca());
                        listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);

                        //crear el pdf
                        JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                        JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoRep);
                        Map<String, Object> parametros = new HashMap<>();
                        String ruta = absoluteWebPath + "reportes\\";
                        parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                        parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                        parametros.put("SUBREPORT_DIR", ruta);

                        byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                        String mes = "";
                        if (listaDocIngresoRep.get(0).getMes() <= 9) {
                            mes = "0" + listaDocIngresoRep.get(0).getMes().toString();
                        } else {
                            mes = listaDocIngresoRep.get(0).getMes().toString();
                        }

                        String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ'´?¡¿<>";
                        // Cadena de caracteres ASCII que reemplazarán los originales.
                        String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy'´?¡¿<>";
                        String output = listaDocIngresoRep.get(0).getDiscente();
                        for (int m = 0; m < original.length(); m++) {
                            // Reemplazamos los caracteres especiales. 
                            output = output.replace(original.charAt(m), ascii.charAt(m));

                        }

                        Files.write(new File(carpeta1 + File.separator + "Recibo_" + listaDocIngresoRep.get(0).getCodEstudiante() + "_" + mes + listaDocIngresoRep.get(0).getAnio() + "_" + output + ".pdf").toPath(), bytes);

                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerSeccion(String sec) {
        try {
            seccion = sec;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerAprobacion() {
        try {
            imprimirRecibosPdfEstudianteUnico();
            limpiarFiltros();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFormato(Integer tipo) {
        try {
            if (tipo.equals(1)) {
                imprimirRecibosPdf();
            } else if (tipo.equals(2)) {
                imprimirRecibosABPdf("EMISOR");
            } else if (tipo.equals(3)) {
                imprimirRecibosABPdf("USUARIO");
            } else {
                new MensajePrime().addInformativeMessagePer("etiquetaSelecFormato");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFormatoMoras(Integer tipo) {
        try {
            if (tipo.equals(1)) {
                imprimirRecibosPdfMora();
            } else if (tipo.equals(2)) {
                imprimirRecibosABPdfMora("EMISOR");
            } else if (tipo.equals(3)) {
                imprimirRecibosABPdfMora("USUARIO");
            } else {
                new MensajePrime().addInformativeMessagePer("etiquetaSelecFormato");
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFor(Integer tipo) {
        try {
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirRecibosPdf() {
        ServletOutputStream out = null;
        try {//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            if (listaRecibosBean != null) {
                if (!listaRecibosBean.isEmpty()) {
                    for (CuentasPorCobrarBean lista : listaRecibosBean) {
                        System.out.println("id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        System.out.println("nro:" + lista.getDocIngresoBean().getSerieNroDoc());
                        if (lista.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getDocIngresoBean().getIdDocIngreso());
                        } else {
//                            System.out.println("no se genera id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                } else {
                    //texto
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                //texto
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //modelo dos cuerpos con cabecera
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5.jasper");
            //modelo dos cuerpos sin cabecera
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS_v2.jasper");
            }

            for (Integer l : listaIdsDocIngreso) {
                System.out.println("listaIdsDocIngreso id:" + l);
            }
            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDocIngresoRep = new ArrayList<>();
            listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForSinOrderBy(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    listaIdsDocIngreso, this.orden);
            if (!listaDocIngresoRep.isEmpty()) {
//                System.out.println("qr" + listaDocIngresoRep.get(0).getQr());
                for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                    System.out.println("id" + listaDocIngresoRep.get(i).getIdDocIngreso());
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    //SIN MMORA
                    System.out.println("mora: " + listaDocIngresoRep.get(i).getMora());
                    listaDocIngresoRep.get(i).setMora(0);
                    listaDocIngresoRep.get(i).setDscto(0);
                    listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), listaDocIngresoRep.get(i).getDscto(), listaDocIngresoRep.get(i).getBeca());
                    listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                    System.out.println("1: " + listaDocIngresoRep.get(i).getFechaPago());
                }
            }

            docIngresoService.cambiarFechaImpresion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
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
            response.setHeader("Content-Disposition", "inline; filename=RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirRecibosPdfTalleres() {
        ServletOutputStream out = null;
        try {//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            DocIngresoBean doc = new DocIngresoBean();
            if (listaTalleresWebBean != null) {
                if (!listaTalleresWebBean.isEmpty()) {
                    for (DocIngresoBean lista : listaTalleresWebBean) {
                        System.out.println("id:" + lista.getIdDocIngreso());
                        System.out.println("nro:" + lista.getSerieNroDoc());
                        if (lista.getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getIdDocIngreso());
                            doc.setIdDocIngreso(lista.getIdDocIngreso());
                        } else {
//                            System.out.println("no se genera id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                } else {
                    //texto
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                //texto
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //modelo dos cuerpos con cabecera
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoTalleres.jasper");
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteFormatoDocIngresoLaser2017.jasper");
            }

            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            DocIngresoBean docIngreso = new DocIngresoBean();
            List<DocIngresoRepBean> listDocAyuda = new ArrayList<>();
            for (Integer l : listaIdsDocIngreso) {
                docIngreso.setIdDocIngreso(l);
                System.out.println(">>> : " + docIngreso.getNombreDiscente());
                docIngreso.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

                listaDocIngresoRep = docIngresoService.obtenerDocIngreso(docIngreso); 
                for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                    listDocAyuda.add(listaDocIngresoRep.get(i));
                } 
            }
            if (!listDocAyuda.isEmpty()) {
                for (int j = 0; j < listDocAyuda.size(); j++) {
                List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                listaRepDetDocIngreso = docIngresoService.obtenerFormatoDetalleDocIngresoConDscto(listDocAyuda.get(j).getIdDocIngreso(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listDocAyuda.get(j).getFlgDscto());
                listDocAyuda.get(j).setListaDetalle(listaRepDetDocIngreso);
            }
            }
            docIngresoService.cambiarFechaImpresion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);

            ///
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listDocAyuda);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirRecibosABPdf(String tipo) {
        ServletOutputStream out = null;
        try {//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            System.out.println("llegué");
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            if (listaRecibosBean != null) {
                if (!listaRecibosBean.isEmpty()) {
                    for (CuentasPorCobrarBean lista : listaRecibosBean) {
                        if (lista.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getDocIngresoBean().getIdDocIngreso());
                        } else {
//                            System.out.println("no se genera id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                } else {
                    //texto
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                //texto
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //modelo dos cuerpos con cabecera
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5_verAB.jasper");
            //modelo dos cuerpos sin cabecera
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5_verAB_sinCabecera.jasper");
//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS_v2.jasper");
            }

            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            listaDocIngresoRep = new ArrayList<>();
            listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForSinOrderBy(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso, this.orden);
            if (!listaDocIngresoRep.isEmpty()) {
//                System.out.println("listaDocIngresoRep");
                for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngreso(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                            listaDocIngresoRep.get(i).getIdDocIngreso(), 0, 0, listaDocIngresoRep.get(i).getBeca());
                    listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                }
            }

            List<DocIngresoABRepBean> listaDocIngresoABRep = new ArrayList<>();
            Integer size = 0;
            if (listaDocIngresoRep.size() % 2 == 0) {
                size = listaDocIngresoRep.size() / 2;
            } else {
                size = (listaDocIngresoRep.size() + 1) / 2;
            }
            Integer sizeX = 0;
            String format = "";
            if (!tipo.equals("")) {
                format = tipo;
            }
            System.out.println("size->" + size);
            for (int x = 0; x < size; x++) {
                if (size - x == 1) {
                    if (size == 1) {
                        if (listaDocIngresoRep.size() == 1) {
                            sizeX = (x * 2);
                        } else {
                            sizeX = (x * 2) + 1;
                        }
                    } else {
                        if (listaDocIngresoRep.size() % 2 == 0) {
                            sizeX = (x * 2) + 1;
                        } else {
                            sizeX = (x * 2);
                        }
                    }
                } else {
                    sizeX = (x * 2) + 1;
                }

//                System.out.println("inicio de x..." + x + "----------------" + sizeX);
                DocIngresoABRepBean lista = new DocIngresoABRepBean();
                for (int i = (x * 2); i <= sizeX; i++) {
//                    System.out.println("i..." + i);
//                    System.out.println("getQr " + listaDocIngresoRep.get(i).getQr());
//                    System.out.println("getDiscente " + listaDocIngresoRep.get(i).getDiscente());
                    lista.setNombre(listaDocIngresoRep.get(i).getNombre());
                    lista.setRuc(listaDocIngresoRep.get(i).getRuc());
                    lista.setDireccion(listaDocIngresoRep.get(i).getDireccion());
                    lista.setTelefono(listaDocIngresoRep.get(i).getTelefono());
                    lista.setCorreo(listaDocIngresoRep.get(i).getCorreo());
                    lista.setNomDistrito(listaDocIngresoRep.get(i).getNomDistrito());
                    System.out.println("fecha xxx" + i + ":" + listaDocIngresoRep.get(i).getFechaPago());
                    lista.setFormato(format);
                    if (i % 2 == 0) {
//                        System.out.println(i + " es par");
                        lista.setBancoA(listaDocIngresoRep.get(i).getBanco());
                        lista.setBecadoA(listaDocIngresoRep.get(i).getBecado());
                        lista.setCodEstudianteA(listaDocIngresoRep.get(i).getCodEstudiante());
                        lista.setColegioA(listaDocIngresoRep.get(i).getColegio());
                        lista.setCorrespondienteaA(listaDocIngresoRep.get(i).getCorrespondientea());
                        lista.setDiscenteA(listaDocIngresoRep.get(i).getDiscente());
                        lista.setFechaA(listaDocIngresoRep.get(i).getFecha());
                        lista.setFechaVencA(listaDocIngresoRep.get(i).getFechaVenc());
                        lista.setInfoReciboA(listaDocIngresoRep.get(i).getInfoRecibo());
                        lista.setNombreGradoA(listaDocIngresoRep.get(i).getNombreGrado());
                        lista.setNombreNivelA(listaDocIngresoRep.get(i).getNombreNivel());
                        lista.setPaganteA(listaDocIngresoRep.get(i).getPagante());
                        lista.setResPagoA(listaDocIngresoRep.get(i).getResPago());
                        lista.setSerieNroDocA(listaDocIngresoRep.get(i).getSerieNroDoc());
                        lista.setTipoBecaA(listaDocIngresoRep.get(i).getTipoBeca());
                        lista.setTipoDocA(listaDocIngresoRep.get(i).getTipoDoc());
                        lista.setQrA(listaDocIngresoRep.get(i).getQr());
                        lista.setFechaPagoA(listaDocIngresoRep.get(i).getFechaPago());
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        listaRepDetDocIngreso = (List<DetDocIngresoRepBean>) listaDocIngresoRep.get(i).getListaDetalle().getData();
                        lista.setReferencialA(listaDocIngresoRep.get(i).getReferencial());
//                        System.out.println("size detalle..." + listaRepDetDocIngreso.size());

                        if (listaRepDetDocIngreso.size() == 1) {
                            lista.setCuentaDA(listaRepDetDocIngreso.get(0).getCuentaD());
                            lista.setNomConceptoA(listaRepDetDocIngreso.get(0).getNomConcepto());
                            lista.setMontoVistaA(listaRepDetDocIngreso.get(0).getMontoVista());
                            lista.setMontoPagadoVistaA(listaRepDetDocIngreso.get(0).getMontoPagadoVista());
                            lista.setTextoMontoA(listaRepDetDocIngreso.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaA("");
                            lista.setLabelDsctoBecaA("");
                            lista.setDsctobecaA("");
                        } else {
//                            System.out.println("size detalle 2..." + listaRepDetDocIngreso.get(1).getLabelDsctoBeca());
                            lista.setCuentaDA(listaRepDetDocIngreso.get(0).getCuentaD());
                            lista.setNomConceptoA(listaRepDetDocIngreso.get(0).getNomConcepto());
                            lista.setMontoVistaA(listaRepDetDocIngreso.get(0).getMontoVista());
                            lista.setMontoPagadoVistaA(listaRepDetDocIngreso.get(0).getMontoPagadoVista());
                            lista.setTextoMontoA(listaRepDetDocIngreso.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaA(listaRepDetDocIngreso.get(1).getCuentaD().toString());
                            lista.setLabelDsctoBecaA(listaRepDetDocIngreso.get(1).getNomConcepto());
                            lista.setDsctobecaA(listaRepDetDocIngreso.get(1).getMontoVista());
                        }
                    } else {
//                        System.out.println(i + " es impar");
                        lista.setBancoB(listaDocIngresoRep.get(i).getBanco());
                        lista.setBecadoB(listaDocIngresoRep.get(i).getBecado());
                        lista.setCodEstudianteB(listaDocIngresoRep.get(i).getCodEstudiante());
                        lista.setColegioB(listaDocIngresoRep.get(i).getColegio());
                        lista.setCorrespondienteaB(listaDocIngresoRep.get(i).getCorrespondientea());
                        lista.setDiscenteB(listaDocIngresoRep.get(i).getDiscente());
                        lista.setFechaB(listaDocIngresoRep.get(i).getFecha());
                        lista.setFechaVencB(listaDocIngresoRep.get(i).getFechaVenc());
                        lista.setInfoReciboB(listaDocIngresoRep.get(i).getInfoRecibo());
                        lista.setNombreGradoB(listaDocIngresoRep.get(i).getNombreGrado());
                        lista.setNombreNivelB(listaDocIngresoRep.get(i).getNombreNivel());
                        lista.setPaganteB(listaDocIngresoRep.get(i).getPagante());
                        lista.setResPagoB(listaDocIngresoRep.get(i).getResPago());
                        lista.setSerieNroDocB(listaDocIngresoRep.get(i).getSerieNroDoc());
                        lista.setTipoBecaB(listaDocIngresoRep.get(i).getTipoBeca());
                        lista.setTipoDocB(listaDocIngresoRep.get(i).getTipoDoc());
                        lista.setFechaPagoB(listaDocIngresoRep.get(i).getFechaPago());
                        lista.setQrB(listaDocIngresoRep.get(i).getQr());
                        List<DetDocIngresoRepBean> listaRepDetDocIngresoB = new ArrayList<>();
                        listaRepDetDocIngresoB = (List<DetDocIngresoRepBean>) listaDocIngresoRep.get(i).getListaDetalle().getData();
                        lista.setReferencialB(listaDocIngresoRep.get(i).getReferencial());
//                        System.out.println("size detalle..." + listaRepDetDocIngresoB.size());
                        if (listaRepDetDocIngresoB.size() == 1) {
                            lista.setCuentaDB(listaRepDetDocIngresoB.get(0).getCuentaD());
                            lista.setNomConceptoB(listaRepDetDocIngresoB.get(0).getNomConcepto());
                            lista.setMontoVistaB(listaRepDetDocIngresoB.get(0).getMontoVista());
                            lista.setMontoPagadoVistaB(listaRepDetDocIngresoB.get(0).getMontoPagadoVista());
                            lista.setTextoMontoB(listaRepDetDocIngresoB.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaB("");
                            lista.setLabelDsctoBecaB("");
                            lista.setDsctobecaB("");
                        } else {
//                            System.out.println("size detalle 2..." + listaRepDetDocIngresoB.get(1).getLabelDsctoBeca());
                            lista.setCuentaDB(listaRepDetDocIngresoB.get(0).getCuentaD());
                            lista.setNomConceptoB(listaRepDetDocIngresoB.get(0).getNomConcepto());
                            lista.setMontoVistaB(listaRepDetDocIngresoB.get(0).getMontoVista());
                            lista.setMontoPagadoVistaB(listaRepDetDocIngresoB.get(0).getMontoPagadoVista());
                            lista.setTextoMontoB(listaRepDetDocIngresoB.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaB(listaRepDetDocIngresoB.get(1).getCuentaD().toString());
                            lista.setLabelDsctoBecaB(listaRepDetDocIngresoB.get(1).getNomConcepto());
                            lista.setDsctobecaB(listaRepDetDocIngresoB.get(1).getMontoVista());
                        }
                    }
                }
                listaDocIngresoABRep.add(lista);
//                System.out.println("fin de x..." + x);
            }
            if (!tipo.equals("")) {
                if (tipo.equals("USUARIO")) {
                    docIngresoService.cambiarFechaImpresion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso);
                } else {
                    System.out.println("NO se coloca fecha de impr.");
                }
            }
            System.out.println("2: " + listaDocIngresoABRep.get(0).getFechaPagoA());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoABRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirRecibosPdfMora() {
        ServletOutputStream out = null;
        try {//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            RecibosMoraService recibosMoraService = BeanFactory.getRecibosMoraService();
            System.out.println("llegué");
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            if (listaRecibosBean != null) {
                if (!listaRecibosBean.isEmpty()) {
                    for (CuentasPorCobrarBean lista : listaRecibosBean) {
                        System.out.println("id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        System.out.println("nro:" + lista.getDocIngresoBean().getSerieNroDoc());
                        if (lista.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getDocIngresoBean().getIdDocIngreso());
                        } else {
//                            System.out.println("no se genera id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                } else {
                    //texto
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                //texto
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //modelo dos cuerpos con cabecera
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecMora.jasper");
            //modelo dos cuerpos sin cabecera
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecMoraCHAMPS_v2.jasper");
            }

            for (Integer l : listaIdsDocIngreso) {
                System.out.println("listaIdsDocIngreso id:" + l);
            }
            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
//                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaDocIngresoRep = new ArrayList<>();
            listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForSinOrderBy(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    listaIdsDocIngreso, this.orden);
            if (!listaDocIngresoRep.isEmpty()) {
//                System.out.println("qr" + listaDocIngresoRep.get(0).getQr());
                for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                    if (listaDocIngresoRep.get(i).getIdRecibosMora() == null) {
//                        for (DocIngresoRepBean Doc : listaDocIngresoRep) {
                        RecibosMoraBean recibos = new RecibosMoraBean();
                        //1. er paso: obtener el ultimo recibo para la secuencia y generacion del numero
                        recibos.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        String uniNeg = recibos.getUnidadNegocioBean().getUniNeg();
                        Integer nroDo = recibosMoraService.obtenerUltimo(uniNeg);
                        //2. do paso: darle numero secuencial
                        recibos.getDocIngresoBean().setIdDocIngreso(listaDocIngresoRep.get(i).getIdDocIngreso());
                        recibos.getEstudianteBean().setIdEstudiante(listaDocIngresoRep.get(i).getIdEstudiante());
//                        recibos.setSerieMora("008"); Pasandole opcion de escoger serie
                        recibos.setSerieMora(impresoraBean.getSerie());

                        if (nroDo.equals(0)) {
                            recibos.setNroDocMora(1);
                        } else {
                            recibos.setNroDocMora(nroDo + 1);
                        }
                        recibos.setAnio(listaDocIngresoRep.get(i).getAnio());
                        recibos.setMes(listaDocIngresoRep.get(i).getMes());
                        recibos.setCreaPor(usuarioLogin.getUsuario());
                        recibos.setFlgImpresionMora(true);
                        recibosMoraService.insertarRecibosMora(recibos);
                        RecibosMoraBean recibosMoraBean = recibosMoraService.obtenerId(recibos.getNroDocMora(), uniNeg, recibos.getSerieMora(), listaDocIngresoRep.get(i).getIdDocIngreso());
                        System.out.println("mora: " + recibosMoraBean);
                        docIngresoService.ponerNroReciboMora(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), recibosMoraBean.getIdRecibosMora(), recibos.getDocIngresoBean().getIdDocIngreso());
//                        }
                    }
                    String nroDocMora = recibosMoraService.obtenerIdDocIngreso(listaDocIngresoRep.get(i).getIdDocIngreso());

                    System.out.println("id" + listaDocIngresoRep.get(i).getIdDocIngreso());
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    //SIN MMORA
                    System.out.println("mora: " + listaDocIngresoRep.get(i).getMora());
//                    listaDocIngresoRep.get(i).setMora(0);
                    listaDocIngresoRep.get(i).setSerieNroDocMora(nroDocMora);
                    listaDocIngresoRep.get(i).setDscto(0);
                    listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngresoMora(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), 0, 0);
                    listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                    System.out.println("1: " + listaDocIngresoRep.get(i).getFechaPago());
                }

                Integer nroDo = recibosMoraService.obtenerUltimo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
                    ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                    impresoraBean.setActual(nroDo);
                    impresoraService.cambiarNro(impresoraBean);
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
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirRecibosABPdfMora(String tipo) {
        ServletOutputStream out = null;
        try {//            
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            RecibosMoraService recibosMoraService = BeanFactory.getRecibosMoraService();
            System.out.println("llegué");
            List<Integer> listaIdsDocIngreso = new ArrayList<>();
            if (listaRecibosBean != null) {
                if (!listaRecibosBean.isEmpty()) {
                    for (CuentasPorCobrarBean lista : listaRecibosBean) {
                        if (lista.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                            listaIdsDocIngreso.add(lista.getDocIngresoBean().getIdDocIngreso());
                        } else {
//                            System.out.println("no se genera id:" + lista.getDocIngresoBean().getIdDocIngreso());
                        }
                    }
                } else {
                    //texto
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                //texto
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

            //foramto=1 -> formato de caja
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            //modelo dos cuerpos con cabecera
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecMora_verAB.jasper");
            //modelo dos cuerpos sin cabecera
            if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecMora_verAB_sinCabecera.jasper");
//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocIngresoRecA5CHAMPS_v2.jasper");
            }

            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            listaDocIngresoRep = new ArrayList<>();
            listaDocIngresoRep = docIngresoService.obtenerRecDocIngresoForSinOrderBy(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIdsDocIngreso, this.orden);
            //Agregando numero de recibo mora al que no tiene

            if (!listaDocIngresoRep.isEmpty()) {
//                System.out.println("listaDocIngresoRep");
                for (int i = 0; i < listaDocIngresoRep.size(); i++) {
                    List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                    listaRepDetDocIngreso = docIngresoService.obtenerRecDetDocIngresoMora(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                            listaDocIngresoRep.get(i).getIdDocIngreso(), listaDocIngresoRep.get(i).getMora(), 0, 0);
                    listaDocIngresoRep.get(i).setListaDetalle(listaRepDetDocIngreso);
                }
            }

            List<DocIngresoABRepBean> listaDocIngresoABRep = new ArrayList<>();
            Integer size = 0;
            if (listaDocIngresoRep.size() % 2 == 0) {
                size = listaDocIngresoRep.size() / 2;
            } else {
                size = (listaDocIngresoRep.size() + 1) / 2;
            }
            Integer sizeX = 0;
            String format = "";
            if (!tipo.equals("")) {
                format = tipo;
            }
            System.out.println("size->" + size);
            for (int x = 0; x < size; x++) {
                if (size - x == 1) {
                    if (size == 1) {
                        if (listaDocIngresoRep.size() == 1) {
                            sizeX = (x * 2);
                        } else {
                            sizeX = (x * 2) + 1;
                        }
                    } else {
                        if (listaDocIngresoRep.size() % 2 == 0) {
                            sizeX = (x * 2) + 1;
                        } else {
                            sizeX = (x * 2);
                        }
                    }
                } else {
                    sizeX = (x * 2) + 1;
                }

//                System.out.println("inicio de x..." + x + "----------------" + sizeX);
                DocIngresoABRepBean lista = new DocIngresoABRepBean();
                for (int i = (x * 2); i <= sizeX; i++) {
                    System.out.println("numero de recibo id: " + listaDocIngresoRep.get(i).getIdRecibosMora());

                    if (listaDocIngresoRep.get(i).getIdRecibosMora() == null) {
//                        for (DocIngresoRepBean Doc : listaDocIngresoRep) {
                        RecibosMoraBean recibos = new RecibosMoraBean();
                        //1. er paso: obtener el ultimo recibo para la secuencia y generacion del numero
                        recibos.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        String uniNeg = recibos.getUnidadNegocioBean().getUniNeg();
                        Integer nroDo = recibosMoraService.obtenerUltimo(uniNeg);
                        //2. do paso: darle numero secuencial
                        recibos.getDocIngresoBean().setIdDocIngreso(listaDocIngresoRep.get(i).getIdDocIngreso());
                        recibos.getEstudianteBean().setIdEstudiante(listaDocIngresoRep.get(i).getIdEstudiante());
//                        recibos.setSerieMora("008"); Pasandole opcion de escoger serie
                        recibos.setSerieMora(impresoraBean.getSerie());
                        if (nroDo.equals(0)) {
                            recibos.setNroDocMora(1);
                        } else {
                            recibos.setNroDocMora(nroDo + 1);
                        }
                        recibos.setAnio(listaDocIngresoRep.get(i).getAnio());
                        recibos.setMes(listaDocIngresoRep.get(i).getMes());
                        recibos.setCreaPor(usuarioLogin.getUsuario());
                        recibos.setFlgImpresionMora(true);
                        recibosMoraService.insertarRecibosMora(recibos);
                        RecibosMoraBean recibosMoraBean = recibosMoraService.obtenerId(recibos.getNroDocMora(), uniNeg, recibos.getSerieMora(), listaDocIngresoRep.get(i).getIdDocIngreso());
                        System.out.println("mora: " + recibosMoraBean);
                        docIngresoService.ponerNroReciboMora(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), recibosMoraBean.getIdRecibosMora(), recibos.getDocIngresoBean().getIdDocIngreso());
//                        }
                    }
//                    System.out.println("i..." + i);
//                    System.out.println("getQr " + listaDocIngresoRep.get(i).getQr());
//                    System.out.println("getDiscente " + listaDocIngresoRep.get(i).getDiscente()); 
                    lista.setNombre(listaDocIngresoRep.get(i).getNombre());
                    lista.setRuc(listaDocIngresoRep.get(i).getRuc());
                    lista.setDireccion(listaDocIngresoRep.get(i).getDireccion());
                    lista.setTelefono(listaDocIngresoRep.get(i).getTelefono());
                    lista.setCorreo(listaDocIngresoRep.get(i).getCorreo());
                    lista.setNomDistrito(listaDocIngresoRep.get(i).getNomDistrito());
                    System.out.println("fecha xxx" + i + ":" + listaDocIngresoRep.get(i).getFechaPago());
                    lista.setFormato(format);
                    String nroDocMora = recibosMoraService.obtenerIdDocIngreso(listaDocIngresoRep.get(i).getIdDocIngreso());
                    if (i % 2 == 0) {
                        //                        System.out.println(i + " es par");
                        lista.setBancoA(listaDocIngresoRep.get(i).getBanco());
                        lista.setBecadoA(listaDocIngresoRep.get(i).getBecado());
                        lista.setCodEstudianteA(listaDocIngresoRep.get(i).getCodEstudiante());
                        lista.setColegioA(listaDocIngresoRep.get(i).getColegio());
                        lista.setCorrespondienteaA(listaDocIngresoRep.get(i).getCorrespondientea());
                        lista.setDiscenteA(listaDocIngresoRep.get(i).getDiscente());
                        lista.setFechaA(listaDocIngresoRep.get(i).getFecha());
                        lista.setFechaVencA(listaDocIngresoRep.get(i).getFechaVenc());
                        lista.setInfoReciboA(listaDocIngresoRep.get(i).getInfoRecibo());
                        lista.setNombreGradoA(listaDocIngresoRep.get(i).getNombreGrado());
                        lista.setNombreNivelA(listaDocIngresoRep.get(i).getNombreNivel());
                        lista.setPaganteA(listaDocIngresoRep.get(i).getPagante());
                        lista.setResPagoA(listaDocIngresoRep.get(i).getResPago());
                        lista.setSerieNroDocA(listaDocIngresoRep.get(i).getSerieNroDoc());
                        lista.setTipoBecaA(listaDocIngresoRep.get(i).getTipoBeca());
                        lista.setTipoDocA(listaDocIngresoRep.get(i).getTipoDoc());
                        lista.setQrA(listaDocIngresoRep.get(i).getQr());
                        lista.setFechaPagoA(listaDocIngresoRep.get(i).getFechaPago());
                        System.out.println("nroDocMoraA: " + nroDocMora);
                        lista.setSerieNroDocMoraA(nroDocMora);
                        System.out.println("nroDocMoraA2: " + lista.getSerieNroDocMoraA());
                        lista.setIdRecibosMoraA(listaDocIngresoRep.get(i).getIdRecibosMora());
                        List<DetDocIngresoRepBean> listaRepDetDocIngreso = new ArrayList<>();
                        listaRepDetDocIngreso = (List<DetDocIngresoRepBean>) listaDocIngresoRep.get(i).getListaDetalle().getData();
//                        System.out.println("size detalle..." + listaRepDetDocIngreso.size());

                        if (listaRepDetDocIngreso.size() == 1) {
                            lista.setCuentaDA(listaRepDetDocIngreso.get(0).getCuentaD());
                            lista.setNomConceptoA(listaRepDetDocIngreso.get(0).getNomConcepto());
                            lista.setMontoVistaA(listaRepDetDocIngreso.get(0).getMontoVista());
                            lista.setMontoPagadoVistaA(listaRepDetDocIngreso.get(0).getMontoPagadoVista());
                            lista.setTextoMontoA(listaRepDetDocIngreso.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaA("");
                            lista.setLabelDsctoBecaA("");
                            lista.setDsctobecaA("");
                        } else {
//                            System.out.println("size detalle 2..." + listaRepDetDocIngreso.get(1).getLabelDsctoBeca());
                            lista.setCuentaDA(listaRepDetDocIngreso.get(0).getCuentaD());
                            lista.setNomConceptoA(listaRepDetDocIngreso.get(0).getNomConcepto());
                            lista.setMontoVistaA(listaRepDetDocIngreso.get(0).getMontoVista());
                            lista.setMontoPagadoVistaA(listaRepDetDocIngreso.get(0).getMontoPagadoVista());
                            lista.setTextoMontoA(listaRepDetDocIngreso.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaA(listaRepDetDocIngreso.get(1).getCuentaD().toString());
                            lista.setLabelDsctoBecaA(listaRepDetDocIngreso.get(1).getNomConcepto());
                            lista.setDsctobecaA(listaRepDetDocIngreso.get(1).getMontoVista());
                        }
//                        }
                    } else {
//                        System.out.println(i + " es impar");
                        lista.setBancoB(listaDocIngresoRep.get(i).getBanco());
                        lista.setBecadoB(listaDocIngresoRep.get(i).getBecado());
                        lista.setCodEstudianteB(listaDocIngresoRep.get(i).getCodEstudiante());
                        lista.setColegioB(listaDocIngresoRep.get(i).getColegio());
                        lista.setCorrespondienteaB(listaDocIngresoRep.get(i).getCorrespondientea());
                        lista.setDiscenteB(listaDocIngresoRep.get(i).getDiscente());
                        lista.setFechaB(listaDocIngresoRep.get(i).getFecha());
                        lista.setFechaVencB(listaDocIngresoRep.get(i).getFechaVenc());
                        lista.setInfoReciboB(listaDocIngresoRep.get(i).getInfoRecibo());
                        lista.setNombreGradoB(listaDocIngresoRep.get(i).getNombreGrado());
                        lista.setNombreNivelB(listaDocIngresoRep.get(i).getNombreNivel());
                        lista.setPaganteB(listaDocIngresoRep.get(i).getPagante());
                        lista.setResPagoB(listaDocIngresoRep.get(i).getResPago());
                        lista.setSerieNroDocB(listaDocIngresoRep.get(i).getSerieNroDoc());
                        lista.setTipoBecaB(listaDocIngresoRep.get(i).getTipoBeca());
                        lista.setTipoDocB(listaDocIngresoRep.get(i).getTipoDoc());
                        System.out.println("nroDocMoraB: " + nroDocMora);
                        lista.setSerieNroDocMoraB(nroDocMora);
                        System.out.println("nroDocMoraB2: " + lista.getSerieNroDocMoraB());
                        lista.setFechaPagoB(listaDocIngresoRep.get(i).getFechaPago());
                        lista.setIdRecibosMoraB(listaDocIngresoRep.get(i).getIdRecibosMora());
                        lista.setQrB(listaDocIngresoRep.get(i).getQr());
                        List<DetDocIngresoRepBean> listaRepDetDocIngresoB = new ArrayList<>();
                        listaRepDetDocIngresoB = (List<DetDocIngresoRepBean>) listaDocIngresoRep.get(i).getListaDetalle().getData();
//                        System.out.println("size detalle..." + listaRepDetDocIngresoB.size());
                        if (listaRepDetDocIngresoB.size() == 1) {
                            lista.setCuentaDB(listaRepDetDocIngresoB.get(0).getCuentaD());
                            lista.setNomConceptoB(listaRepDetDocIngresoB.get(0).getNomConcepto());
                            lista.setMontoVistaB(listaRepDetDocIngresoB.get(0).getMontoVista());
                            lista.setMontoPagadoVistaB(listaRepDetDocIngresoB.get(0).getMontoPagadoVista());
                            lista.setTextoMontoB(listaRepDetDocIngresoB.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaB("");
                            lista.setLabelDsctoBecaB("");
                            lista.setDsctobecaB("");
                        } else {
//                            System.out.println("size detalle 2..." + listaRepDetDocIngresoB.get(1).getLabelDsctoBeca());
                            lista.setCuentaDB(listaRepDetDocIngresoB.get(0).getCuentaD());
                            lista.setNomConceptoB(listaRepDetDocIngresoB.get(0).getNomConcepto());
                            lista.setMontoVistaB(listaRepDetDocIngresoB.get(0).getMontoVista());
                            lista.setMontoPagadoVistaB(listaRepDetDocIngresoB.get(0).getMontoPagadoVista());
                            lista.setTextoMontoB(listaRepDetDocIngresoB.get(0).getTextoMonto());
                            lista.setCuentaDsctoBecaB(listaRepDetDocIngresoB.get(1).getCuentaD().toString());
                            lista.setLabelDsctoBecaB(listaRepDetDocIngresoB.get(1).getNomConcepto());
                            lista.setDsctobecaB(listaRepDetDocIngresoB.get(1).getMontoVista());
                        }
                    }
                }
                listaDocIngresoABRep.add(lista);

            }
            Integer nroDo = recibosMoraService.obtenerUltimo(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (impresoraBean.getActual() != null && impresoraBean.getSerie() != null) {
                ImpresoraService impresoraService = BeanFactory.getImpresoraService();
                impresoraBean.setActual(nroDo);
                impresoraService.cambiarNro(impresoraBean);
            }
            System.out.println("2: " + listaDocIngresoABRep.get(0).getFechaPagoA());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocIngresoABRep);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    public void imprimirListadoRecibosPdf() {
        ServletOutputStream out = null;
        try {//             
            System.out.println("llegué");
            List<ImpresionReciboRepBean> listaRecibos = new ArrayList<>();
            Integer c = 0;
            String titulo = "RELACIÓN DE ALUMNOS CON PAGOS";
            String subTitulo = "";
            String parte1 = "";
            String parte2 = null;
            String anio = null;
            Iterator it = listaTiemposPago.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if (listaTiemposPago.get(key).equals(tiempoPago)) {
                    parte1 = key.toString();
//                    System.out.println("Clave: " + key + " -> Valor: " + listaTiemposPago.get(key));
                    break;
                }
            }

//            RELACIÓN DE ALUMNOS CON PAGOS --TXT
//                     (A MAYO 2017)
            if (tiempoPago.equals(1)) {
                anio = periodo.toString();
            }
            if (tiempoPago.equals(2)) {
                if (periodoInicio == periodoFin) {
                    anio = periodoInicio.toString();
                } else {
                    anio = periodoInicio.toString() + "-" + periodoFin.toString();
                }
            }
            if (tiempoPago.equals(3)) {
                if (periodoInicio == periodoFin) {
                    anio = periodoInicio.toString();
                } else {
                    anio = periodoInicio.toString() + "-" + periodoFin.toString();
                }
            }
            Iterator it2 = listaMeses.keySet().iterator();
//            Integer mes = 0;
            for (int i = 0; i < selectedMesesNoConsiderar.length; i++) {
                System.out.print("mes..." + selectedMesesNoConsiderar[i] + "\t");
                while (it2.hasNext()) {
                    Object key = it2.next();
                    System.out.println("Mes: " + key + " -> Valor: " + listaMeses.get(key));
                    if (listaMeses.get(key).equals(selectedMesesNoConsiderar[i])) {
                        if (parte2 != null) {
                            System.out.println("xxx" + selectedMesesNoConsiderar[i]);
                            parte2 = parte2 + "-" + key.toString();
                            break;
                        } else {
                            parte2 = key.toString();
                        }
                    }
                }
            }
            parte2 = parte2 + " " + anio;

            //ARMANDO TITULO 
            if (tiempoPago.equals(3)) {
//                titulo = titulo;
            } else {
                titulo = titulo + " " + parte1.toUpperCase();
            }
            if (parte2 != null) {
                subTitulo = "(A " + parte2.toUpperCase() + ")";
            }

            UnidadNegocioService unidadNegocioService = BeanFactory.getUnidadNegocioService();
            UnidadNegocioBean unidad = new UnidadNegocioBean();
            unidad.setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            unidad = unidadNegocioService.obtenerPorFiltro(unidad);
            if (listaRecibosBean != null) {
                for (CuentasPorCobrarBean cuenta : listaRecibosBean) {
                    if (cuenta.getDocIngresoBean().getFlgImpresionMasiva().equals(Boolean.TRUE)) {
                        c = c + 1;
                        ImpresionReciboRepBean impresionReciboRep = new ImpresionReciboRepBean();
                        impresionReciboRep.setNro(c.toString());
                        impresionReciboRep.setNroRecibo(cuenta.getDocIngresoBean().getSerieNroDoc());
                        impresionReciboRep.setAlumno(cuenta.getDocIngresoBean().getNombreDiscente());
                        impresionReciboRep.setGrado(cuenta.getMatriculaBean().getGradoAcademicoBean().getNombre());
                        impresionReciboRep.setReferencia(cuenta.getReferenciaCuenta());
                        impresionReciboRep.setLugar(cuenta.getDocIngresoBean().getIdTipoLugarPago().getCodigo());
                        impresionReciboRep.setFechaPago(cuenta.getFechaPagoVista());
                        impresionReciboRep.setLugar(cuenta.getDocIngresoBean().getIdTipoLugarPago().getCodigo().substring(0, 1));
                        if (titulo != null) {
                            impresionReciboRep.setTitulo(titulo);
                        } else {
                        }
                        if (subTitulo != null) {
                            impresionReciboRep.setSubTitulo(subTitulo);
                        } else {
                            subTitulo = "";
                        }

                        impresionReciboRep.setNombreUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getNombreUniNeg());
                        impresionReciboRep.setRucUniNeg("R.U.C. " + unidad.getRuc());
                        listaRecibos.add(impresionReciboRep);
                    } else {
                        System.out.println("no se genera id:" + cuenta.getDocIngresoBean().getIdDocIngreso());
                    }
                }
            } else {
                //texto                
            }

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteRecibosImpresos.jasper");

            System.out.println(archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            System.out.println("3: " + listaRecibos.get(0).getFechaPago());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaRecibos);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=LISTADO_RECIBOS_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
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

    //metodos getters and setter
    public List<CuentasPorCobrarBean> getListaRecibosBean() {
        if (listaRecibosBean == null) {
            listaRecibosBean = new ArrayList<>();
        }
        return listaRecibosBean;
    }

    public void setListaRecibosBean(List<CuentasPorCobrarBean> listaRecibosBean) {
        this.listaRecibosBean = listaRecibosBean;
    }

    public List<CodigoBean> getListaLugarPagoBean() {
        return listaLugarPagoBean;
    }

    public void setListaLugarPagoBean(List<CodigoBean> listaLugarPagoBean) {
        this.listaLugarPagoBean = listaLugarPagoBean;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getMesPension() {
        return mesPension;
    }

    public void setMesPension(Integer mesPension) {
        this.mesPension = mesPension;
    }

    public List<MesBean> getListaMesNoConsiderar() {
        return listaMesNoConsiderar;
    }

    public void setListaMesNoConsiderar(List<MesBean> listaMesNoConsiderar) {
        this.listaMesNoConsiderar = listaMesNoConsiderar;
    }

    public Integer getMesPago() {
        return mesPago;
    }

    public void setMesPago(Integer mesPago) {
        this.mesPago = mesPago;
    }

    public Integer getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Integer periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Integer getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Integer periodoFin) {
        this.periodoFin = periodoFin;
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

    public Map<String, Integer> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, Integer> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Map<String, Integer> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(Map<String, Integer> listaOrden) {
        this.listaOrden = listaOrden;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdTipoLugarPago() {
        return idTipoLugarPago;
    }

    public void setIdTipoLugarPago(Integer idTipoLugarPago) {
        this.idTipoLugarPago = idTipoLugarPago;
    }

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
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

    public List<MatriculaBean> getListaSeccionBean() {
        if (listaSeccionBean == null) {
            listaSeccionBean = new ArrayList<>();
        }
        return listaSeccionBean;
    }

    public void setListaSeccionBean(List<MatriculaBean> listaSeccionBean) {
        this.listaSeccionBean = listaSeccionBean;
    }

    public Integer getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(Integer idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Map<String, Integer> getListaTiemposPago() {
        return listaTiemposPago;
    }

    public void setListaTiemposPago(Map<String, Integer> listaTiemposPago) {
        this.listaTiemposPago = listaTiemposPago;
    }

    public Integer getTiempoPago() {
        return tiempoPago;
    }

    public void setTiempoPago(Integer tiempoPago) {
        this.tiempoPago = tiempoPago;
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

    public Integer[] getSelectedMesesNoConsiderar() {
        return selectedMesesNoConsiderar;
    }

    public void setSelectedMesesNoConsiderar(Integer[] selectedMesesNoConsiderar) {
        this.selectedMesesNoConsiderar = selectedMesesNoConsiderar;
    }

    public List<DocIngresoRepBean> getListaDocIngresoRep() {
        if (listaDocIngresoRep == null) {
            listaDocIngresoRep = new ArrayList<>();
        }
        return listaDocIngresoRep;
    }

    public void setListaDocIngresoRep(List<DocIngresoRepBean> listaDocIngresoRep) {
        this.listaDocIngresoRep = listaDocIngresoRep;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public Integer getCantidadImpresos() {
        return cantidadImpresos;
    }

    public void setCantidadImpresos(Integer cantidadImpresos) {
        this.cantidadImpresos = cantidadImpresos;
    }

    public Integer getCantidadNoImpresos() {
        return cantidadNoImpresos;
    }

    public void setCantidadNoImpresos(Integer cantidadNoImpresos) {
        this.cantidadNoImpresos = cantidadNoImpresos;
    }

    public Integer getCantidadSinFlg() {
        return cantidadSinFlg;
    }

    public void setCantidadSinFlg(Integer cantidadSinFlg) {
        this.cantidadSinFlg = cantidadSinFlg;
    }

    public Boolean getFlgBtnImp() {
        return flgBtnImp;
    }

    public void setFlgBtnImp(Boolean flgBtnImp) {
        this.flgBtnImp = flgBtnImp;
    }

    public Integer getFlgRecImpr() {
        return flgRecImpr;
    }

    public void setFlgRecImpr(Integer flgRecImpr) {
        this.flgRecImpr = flgRecImpr;
    }

    public Integer getCantValAdmTodos() {
        return cantValAdmTodos;
    }

    public void setCantValAdmTodos(Integer cantValAdmTodos) {
        this.cantValAdmTodos = cantValAdmTodos;
    }

    public Integer getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(Integer tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public Map<String, Integer> getListaMontosMoras() {
        return listaMontosMoras;
    }

    public void setListaMontosMoras(Map<String, Integer> listaMontosMoras) {
        this.listaMontosMoras = listaMontosMoras;
    }

    public Integer getRecibosMora() {
        return recibosMora;
    }

    public void setRecibosMora(Integer recibosMora) {
        this.recibosMora = recibosMora;
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

    public Boolean getFlgPdfPorAlumno() {
        return flgPdfPorAlumno;
    }

    public void setFlgPdfPorAlumno(Boolean flgPdfPorAlumno) {
        this.flgPdfPorAlumno = flgPdfPorAlumno;
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

    public List<DocIngresoBean> getListaTalleresWebBean() {
        if (listaTalleresWebBean == null) {
            listaTalleresWebBean = new ArrayList<>();
        }
        return listaTalleresWebBean;
    }

    public void setListaTalleresWebBean(List<DocIngresoBean> listaTalleresWebBean) {
        this.listaTalleresWebBean = listaTalleresWebBean;
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
}
