/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.DetraccionBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ChequesEmitidosLPMRepBean;
import pe.marista.sigma.bean.reporte.ConceptoPlanillaRepBean;
import pe.marista.sigma.bean.reporte.DetDetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DocEgresoRepBean;
import pe.marista.sigma.bean.reporte.PagosEmitidosRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AsientoService;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.ChequeService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.DetraccionService;
import pe.marista.sigma.service.DocEgresoService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.ImpresoraService;
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
public class DocEgresoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of DocEgresoMB
     */
    @PostConstruct
    public void init() {
        try {
            DocEgresoMB docEgresoMB = (DocEgresoMB) new MaristaUtils().sesionObtenerObjeto("solicitudDoc");
            if (docEgresoMB != null) {
                setDocEgresoBean(docEgresoMB.getDocEgresoBean());
                collapse = true;
                setFlgIdTipoDoc(Boolean.TRUE);
                new MaristaUtils().sesionColocarObjeto("solicitudDoc", null);
            }
            setFlgIdTipoDoc(Boolean.TRUE);
            //sesiÃ³n del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            autenticarCajero();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoPagoBean();
            listaTipoPagoBean = codigoService.obtenerCodigoDocEgreso();
            for (CodigoBean lista : listaTipoPagoBean) {
                if (lista.getCodigo().equals(MaristaConstantes.COD_EFECTIVO)) {
                    listaTipoPagoBean.remove(lista);
                }
            }
            getListaTipoPagoBeanLpm();
            listaTipoPagoBeanLpm = codigoService.obtenerCodigoDocEgreso();
            for (CodigoBean lista2 : listaTipoPagoBeanLpm) {
                if (lista2.getCodigo().equals(MaristaConstantes.COD_EFECTIVO)) {
                    listaTipoPagoBeanLpm.remove(lista2);
                }
                if (lista2.getCodigo().equals(MaristaConstantes.COD_Carta_Orden)) {
                    listaTipoPagoBeanLpm.remove(lista2);
                }
            }
            getListaTipoMonedaBean();
            listaTipoMonedaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            getListaDetraccionFiltroBean();
            listaDetraccionFiltroBean = detraccionService.obtenerTodosActivos();
            //filtros
            fechaActual = new GregorianCalendar();
            getDocEgresoFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocEgresoFiltroBean().setFechaFin(fechaActual.getTime());
            getDocEgresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());

            getPagosEmitidosRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setFechaFin(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            getListaCuentaBancoFiltroBean();
            listaCuentaBancoFiltroBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDocEgresoBean();
            obtenerCuentaBancoPorTipMoneda();

            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            getDocEgresoBean().setTipoCambioBean(tipoCambio);
            getChequeBean();
            getListaDetraccionBean();

            getListaTipoDocBean();
            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));

//            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
//            List<SolicitudCajaCHBean> solicitudCajaCH = new ArrayList<>();
//            solicitudCajaCHMB.setListaSolicitudesEnSesio(solicitudCajaCH);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private DocEgresoBean docEgresoBean;
    private DocEgresoBean docEgresoReporteBean;
    private DocEgresoRepBean docEgresoRepFiltroBean;
    private PagosEmitidosRepBean pagosEmitidosRepFiltroBean;
    private DocEgresoBean docEgresoFiltroBean;
    private List<DocEgresoBean> listaDocEgresoTop1Bean;
    private List<DocEgresoBean> listaDocEgresoIdsRegistroCompraBean;
    private List<DocEgresoBean> listaDocEgresoBean;
    private List<DocEgresoBean> listaDetDocEgresoBean;
    private List<FacturaCompraBean> listaDocEgresoFacturaBean;
    private List<CuentaBancoBean> listaCuentaBancoBean;
    private List<CuentaBancoBean> listaCuentaBancoFiltroBean;
//    private List<CodigoBean> listaTipoMonedaBean;
    private List<CodigoBean> listaTipoPagoBean;
    private List<CodigoBean> listaTipoPagoBeanLpm;
    private List<CodigoBean> listaTipoMonedaBean;

    private UsuarioBean usuarioLoginBean;

    private List<ImpresoraBean> listaImpresora;
    private List<DetraccionBean> listaDetraccionBean;
    private List<DetraccionBean> listaDetraccionFiltroBean;
    private List<SolicitudCajaCHBean> listaSolicitudCajaChBean;
    private Calendar fechaActual;
    private Boolean flgCheque;
    private Boolean mostrarPanel = false;
    private Boolean flgSolGeneral = false;
    private Boolean mostrarCheque = false;
    private Boolean flgIdTipoDoc = false; //0=RegistroCompra 1=A rendir
    private Boolean mostrarDetraccion = false;
    private Boolean mostrarImpresora = false;
    private String montoTotal;
    private Double montoDetraccion = 0.0;
    private Boolean flgCajaAperturada = false;
    private TipoCambioBean tipoCambio;
    private Double montoTotalDouble;
    private Integer origen;

    private CajeroCajaBean cajeroCajaBean;
    private Boolean collapse;

    private ImpresoraBean impresoraBean;
    private ChequeBean chequeBean;

    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<DocEgresoRepBean> listaDocEgresoRepBean;
    private List<PagosEmitidosRepBean> listaPagosEmitidosRepBean;

    private Double totSoles = (0.0);
    private Double tolDolares = (0.0);
    private Double totalSol = (0.0);
    private Double totalDol = (0.0);

    private Boolean flgImpresionFecha;

    private List<CodigoBean> listaTipoDocBean;

    //Ayuda
    private List<DocEgresoBean> listaDocEgresoDetalle;
    private Integer mes;
    private List<MesBean> listaMesAll;
    private Integer anio;
    private List<ChequesEmitidosLPMRepBean> listaChequeEmitidosLPM;

    public void obtenerListaMeses() {
        try {
            listaMesAll.add(new MesBean(1, MaristaConstantes.ENERO));
            listaMesAll.add(new MesBean(2, MaristaConstantes.MES_FEBRERO));
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

    public void obtenerChequeLPM() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            setAnio(miCalendario.get(Calendar.YEAR));
            getListaMesAll();
            obtenerListaMeses();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void resetMontosRep() {
        try {
            totSoles = (0.0);
            tolDolares = (0.0);
            totalSol = (0.0);
            totalDol = (0.0);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMontosRep(String tipo) {
        try {
            resetMontosRep();
            if (tipo.equals("docEgreso")) {
//                for (DocIngresoBean doc : listDocIngresoBean) {
//                    totSoles = totSoles + (doc.getMontoEfectivoSol());
//                    tolDolares = tolDolares + (doc.getMontoEfectivoDol());  
//                }
            } else if (tipo.equals("detDocIng")) {
                for (DocEgresoRepBean doc : listaDocEgresoRepBean) {
                    if (doc.getMontoPagadoSol() == null) {
                        doc.setMontoPagadoSol(0.0);
                    }
                    if (doc.getMontoPagadoDol() == null) {
                        doc.setMontoPagadoDol(0.0);
                    }
                    totSoles = totSoles + (doc.getMontoPagadoSol());
                    tolDolares = tolDolares + (doc.getMontoPagadoDol());
                }
            } else {
//                for (PagosEmitidosRepBean pag : listaPagosEmitidosRepBean) {
//                    if (pag.getMontoPagadoSol() == null) {
//                        pag.setMontoPagadoSol("0.0");
//                    }
//                    if (pag.getMontoPagadoDol() == null) {
//                        pag.setMontoPagadoDol("0.0");
//                    }
//                    totSoles = totSoles + (pag.getMontoPagadoSol().);
//                    tolDolares = tolDolares + (pag.getMontoPagadoDol());
//                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMontosRepLPM(String tipo) {
        try {
            resetMontosRep();
            if (tipo.equals("docEgreso")) {
            } else if (tipo.equals("detDocIng")) {
                for (DocEgresoRepBean doc : listaDocEgresoRepBean) {
                    if (doc.getMontoPagadoSol() == null) {
                        doc.setMontoPagadoSol(0.0);
                    }
                    if (doc.getMontoPagadoDol() == null) {
                        doc.setMontoPagadoDol(0.0);
                    }
                    if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                        totSoles = totSoles + (doc.getMontoPagado());
                        tolDolares = 0.0;
                    } else if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                        tolDolares = tolDolares + (doc.getMontoPagado());
                        totalDol = 0.0;
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroDocEgresoCheque() {
        try {
            int estado = 0;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            if (docEgresoRepFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(docEgresoRepFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                docEgresoRepFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (docEgresoRepFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(docEgresoRepFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                docEgresoRepFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (docEgresoRepFiltroBean.getIdConcepto() != null && !docEgresoRepFiltroBean.getIdConcepto().equals(0)) {
                docEgresoRepFiltroBean.setIdConcepto(docEgresoRepFiltroBean.getIdConcepto());
                estado = 1;
            }
            if (docEgresoRepFiltroBean.getIdTipoConcepto() != null && !docEgresoRepFiltroBean.getIdTipoConcepto().equals(0)) {
                docEgresoRepFiltroBean.setIdTipoConcepto(docEgresoRepFiltroBean.getIdTipoConcepto());
                estado = 1;
            }

            if (docEgresoRepFiltroBean.getGlosa() != null && !docEgresoRepFiltroBean.getGlosa().equals("")) {
                docEgresoRepFiltroBean.setGlosa(docEgresoRepFiltroBean.getGlosa());
                estado = 1;
            }
            if (docEgresoRepFiltroBean.getNomRespCheque() != null && !docEgresoRepFiltroBean.getNomRespCheque().equals("")) {
                docEgresoRepFiltroBean.setNomRespCheque(docEgresoRepFiltroBean.getNomRespCheque());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaDocEgresoRepBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                listaDocEgresoRepBean = new ArrayList<>();
                listaDocEgresoRepBean = docEgresoService.obtenerFiltroDocEgresoCheque(docEgresoRepFiltroBean);
                if (listaDocEgresoRepBean.isEmpty()) {
                    resetMontosRep();
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    obtenerMontosRep("detDocIng");
                    for (DocEgresoRepBean det : listaDocEgresoRepBean) {
                        if (docEgresoRepFiltroBean.getIdConcepto() == null) {
                            det.setIdConcepto(null);
                            det.setNomConcepto(null);
                        }
                        if (docEgresoRepFiltroBean.getIdTipoConcepto() == null) {
                            det.setIdTipoConcepto(null);
                            det.setNomTipoConcepto(null);
                        }

                        det.setFechaInicio(docEgresoRepFiltroBean.getFechaInicio());
                        det.setFechaFin(docEgresoRepFiltroBean.getFechaFin());
                        if (totSoles == null) {
                            totSoles = 0.0;
                        }
                        if (tolDolares == null) {
                            tolDolares = 0.0;
                        }
                        det.setMontoTotSol(totSoles);
                        det.setMontoTotDol(tolDolares);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroDocEgresoChequeLPM() {
        try {
            int estado = 0;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            if (docEgresoRepFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(docEgresoRepFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                docEgresoRepFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (docEgresoRepFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(docEgresoRepFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                docEgresoRepFiltroBean.setFechaFin(u);
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaDocEgresoRepBean = new ArrayList<>();
                resetMontosRep();
            }
            if (estado == 1) {
                listaDocEgresoRepBean = new ArrayList<>();
                docEgresoRepFiltroBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    listaDocEgresoRepBean = docEgresoService.obtenerFiltroDocEgresoChequeSolesLPM(docEgresoRepFiltroBean);
                } else if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                    listaDocEgresoRepBean = docEgresoService.obtenerFiltroDocEgresoChequeDolesLPM(docEgresoRepFiltroBean);
                }
                if (listaDocEgresoRepBean.isEmpty()) {
                    resetMontosRep();
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    obtenerMontosRepLPM("detDocIng");
                    for (DocEgresoRepBean det : listaDocEgresoRepBean) {
                        det.setFechaInicio(docEgresoRepFiltroBean.getFechaInicio());
                        det.setFechaFin(docEgresoRepFiltroBean.getFechaFin());
                        if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                            if (totSoles == null) {
                                totSoles = 0.0;
                                det.setMontoTotal(totSoles);
                            } else if (docEgresoBean.getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                                if (tolDolares == null) {
                                    tolDolares = 0.0;
                                }
                                det.setMontoTotal(tolDolares);
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

    public void obtenerFiltroDocEgresoChequeLPMEXCEL() {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection conexion = DriverManager
                    .getConnection("jdbc:odbc:un_excel");

            Statement st = conexion.createStatement();

            st.execute("create table kk2 (id NUMBER, nombre TEXT, precio NUMBER)");

            st.execute("INSERT INTO kk2 (id,nombre,precio) "
                    + "VALUES (1,'hola',12.52)");

            conexion.close();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroPagosEmitidos() {
        try {
            String textoFiltro = "";
            StringBuilder sb = new StringBuilder();
            int estado = 0;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            if (pagosEmitidosRepFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(pagosEmitidosRepFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                pagosEmitidosRepFiltroBean.setFechaInicio(t);
                estado = 1;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(pagosEmitidosRepFiltroBean.getFechaInicio());
                sb.append("Fecha de Inicio: ").append(date).append(" al ");
            }
            if (pagosEmitidosRepFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(pagosEmitidosRepFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                pagosEmitidosRepFiltroBean.setFechaFin(u);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String date = sdf.format(pagosEmitidosRepFiltroBean.getFechaFin());
                sb.append(date).append(", ");
                estado = 1;
            }
            if (pagosEmitidosRepFiltroBean.getFlgAnulados() != null) {
                pagosEmitidosRepFiltroBean.setFlgAnulados(pagosEmitidosRepFiltroBean.getFlgAnulados());

                sb.append("Anulados,").append(", ");
                estado = 1;
            }
            if (pagosEmitidosRepFiltroBean.getIdTipoConcepto() != null && !pagosEmitidosRepFiltroBean.getIdTipoConcepto().equals(0)) {
                pagosEmitidosRepFiltroBean.setIdTipoConcepto(pagosEmitidosRepFiltroBean.getIdTipoConcepto());
                TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
                TipoConceptoBean tipoConceptoBean = new TipoConceptoBean();
                tipoConceptoBean = tipoConceptoService.obtenerTipoConceptoPorId(pagosEmitidosRepFiltroBean.getIdTipoConcepto());
                sb.append("Tipo Concepto: ").append(tipoConceptoBean.getNombre()).append(", ");
                estado = 1;
            }
            if (pagosEmitidosRepFiltroBean.getIdConcepto() != null && !pagosEmitidosRepFiltroBean.getIdConcepto().equals(0)) {
                pagosEmitidosRepFiltroBean.setIdConcepto(pagosEmitidosRepFiltroBean.getIdConcepto());
                ConceptoService conceptoService = BeanFactory.getConceptoService();
                ConceptoBean conceptoBean = new ConceptoBean();
                conceptoBean.setIdConcepto(pagosEmitidosRepFiltroBean.getIdConcepto());
                conceptoBean.getTipoConceptoBean().setIdTipoConcepto(pagosEmitidosRepFiltroBean.getIdTipoConcepto());
                conceptoBean = conceptoService.obtenerConceptoPorId(conceptoBean);
                sb.append("Concepto: ").append(conceptoBean.getNombre()).append(", ");
                estado = 1;
            }

            if (pagosEmitidosRepFiltroBean.getGlosa() != null && !pagosEmitidosRepFiltroBean.getGlosa().equals("")) {
                pagosEmitidosRepFiltroBean.setGlosa(pagosEmitidosRepFiltroBean.getGlosa());
                sb.append("Referencia: ").append(pagosEmitidosRepFiltroBean.getGlosa()).append(", ");
                estado = 1;
            }
            if (pagosEmitidosRepFiltroBean.getModoPago() != null && !pagosEmitidosRepFiltroBean.getModoPago().equals("")) {
                pagosEmitidosRepFiltroBean.setModoPago(pagosEmitidosRepFiltroBean.getModoPago());
                sb.append("Modo Pago: ").append(pagosEmitidosRepFiltroBean.getModoPago()).append(", ");
                estado = 1;
            }
            if (pagosEmitidosRepFiltroBean.getNomRespCheque() != null && !pagosEmitidosRepFiltroBean.getNomRespCheque().equals("")) {
                pagosEmitidosRepFiltroBean.setNomRespCheque(pagosEmitidosRepFiltroBean.getNomRespCheque());
                sb.append("Responsable: ").append(pagosEmitidosRepFiltroBean.getNomRespCheque()).append(", ");
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPagosEmitidosRepBean = new ArrayList<>();
                resetMontosRep();
            }
            textoFiltro = sb.toString();
            if (estado == 1) {

                listaPagosEmitidosRepBean = new ArrayList<>();
                listaPagosEmitidosRepBean = docEgresoService.obtenerFiltrosPagosEmitidosRep(pagosEmitidosRepFiltroBean);
                if (listaPagosEmitidosRepBean.isEmpty()) {
                    resetMontosRep();
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
//                    obtenerMontosRep("pagos");
                    for (PagosEmitidosRepBean det : listaPagosEmitidosRepBean) {
                        det.setFechaInicio(pagosEmitidosRepFiltroBean.getFechaInicio());
                        det.setFechaFin(pagosEmitidosRepFiltroBean.getFechaFin());
                        det.setTextoFiltro("Filtros: " + textoFiltro);
//                        if (totSoles == null) {
//                            totSoles = 0.0;
//                        }
//                        if (totalDol == null) {
//                            totalDol = 0.0;
//                        }
                        pagosEmitidosRepFiltroBean.setMontoTotSol(det.getMontoTotSol());
                        pagosEmitidosRepFiltroBean.setMontoTotDol(det.getMontoTotDol());
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarListaDetraccion() {
        try {
            if (listaDetraccionBean != null) {
                if (listaDetraccionBean.isEmpty()) {
                    DetraccionService detraccionService = BeanFactory.getDetraccionService();
                    getListaDetraccionBean();
                    listaDetraccionBean = detraccionService.obtenerTodosActivos();
                }
            } else {
                getListaDetraccionBean();
                listaDetraccionBean = new ArrayList();
                cargarListaDetraccion();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void cambiarTipoDoc() {
//        try {
//            if (flgIdTipoDoc == false) {
//                this.flgIdTipoDoc = false;
//            } else {
//                this.flgIdTipoDoc = true;
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void obtenerDetracion() {
        try {
            Double total = 0.0;
            String tot = "";
            this.montoTotal = "";
            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            DetraccionBean detraccion = new DetraccionBean();
            detraccion = detraccionService.obtenerPorId(docEgresoBean.getDetraccionBean());

//            if (flgIdTipoDoc == false) {
//                montoDetraccion = docEgresoBean.getRegistroCompraBean().getTotal() * (detraccion.getValor() / 100);
//                montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
//                docEgresoBean.setMontoPagado(BigDecimal.valueOf(docEgresoBean.getRegistroCompraBean().getTotal() - montoDetraccion));
//                docEgresoBean.getDetraccionBean().setValor(detraccion.getValor());
//            }
            if (flgIdTipoDoc == true) {
                double monDouble = 0;
                double mon = 0;
                monDouble = montoTotalDouble;
//                montoDetraccion = docEgresoBean.getSolicitudCajaCHBean().getMontoAprobado() * (detraccion.getValor() / 100);
                docEgresoBean.setDetraccionBean(detraccion);
                if (docEgresoBean.getDetraccionBean().getDescripcion().equals("Retención RH")) {
                    montoDetraccion = montoTotalDouble * (detraccion.getValor() / 100);
                    montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                } else {
                    montoDetraccion = montoTotalDouble * (detraccion.getValor() / 100);
                    montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                    montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                }

                mon = (double) Math.round((montoTotalDouble - montoDetraccion) * 100) / 100;
                docEgresoBean.setMontoPagado(BigDecimal.valueOf(mon));
//                docEgresoBean.setMontoPagado(BigDecimal.valueOf(docEgresoBean.getSolicitudCajaCHBean().getMontoAprobado() - montoDetraccion));
                docEgresoBean.getDetraccionBean().setValor(detraccion.getValor());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetracionDet(SolicitudCajaCHBean soli) {
        try {
            Double total = 0.0;
            String tot = "";
            this.montoTotal = "";
            DetraccionService detraccionService = BeanFactory.getDetraccionService();
            DetraccionBean detraccion = new DetraccionBean();
            detraccion = detraccionService.obtenerPorId(soli.getDetraccionBean());
            montoTotalDouble = soli.getMontoAprobado();
            System.out.println("moneda..." + getDocEgresoBean().getTipoMonedaBean().getCodigo());
            if (flgIdTipoDoc == true) {
                double monDouble = 0;
                double mon = 0;
                monDouble = montoTotalDouble;
                if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo() == null) {
                    if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                        getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().setCodigo(MaristaConstantes.COD_Soles_Cod);
                    } else {
                        getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().setCodigo(MaristaConstantes.COD_Dolares);
                    }
                }
                if (getDocEgresoBean().getTipoMonedaBean().getCodigo().equals("Soles")) {
                    soli.setDetraccionBean(detraccion);
                    if (soli.getDetraccionBean().getDescripcion().equals("Retención RH")) {
                        montoDetraccion = montoTotalDouble * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                    } else {
                        montoDetraccion = montoTotalDouble * (detraccion.getValor() / 100);
                        montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;
                        montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                    }

                    mon = (double) Math.round((montoTotalDouble - montoDetraccion) * 100) / 100;
                    soli.setMontoPagado((mon));
                    soli.setMontoDetraccion((montoDetraccion));
                    soli.getDetraccionBean().setValor(montoDetraccion);

                    Double montoPag = (soli.getMontoPagado());
                    Double garantia = (soli.getGarantia());
                    Double dsctoNota = (soli.getDsctoNotCred());

                    System.out.println("montoPagado: " + soli.getMontoPagado());

                    if (soli.getGarantia() == 0.0 || soli.getGarantia() == null) {
                        soli.setMontoPagado(montoPag);
                        if (soli.getDsctoNotCred() == null || soli.getDsctoNotCred() == 0.0) {
                            soli.setMontoPagado(montoPag);
                        } else {
                            soli.setMontoPagado(montoPag - dsctoNota);
                        }
                    } else {
                        soli.setMontoPagado(montoPag - garantia);
                        System.out.println("aaa");
                        if (soli.getDsctoNotCred() == null || soli.getDsctoNotCred() == 0.0) {
                            soli.setMontoPagado(montoPag - garantia);
                            System.out.println("aaab");
                        } else {
                            soli.setMontoPagado((montoPag - garantia) - dsctoNota);
                            System.out.println("aaac");
                        }
                    }

                    System.out.println("montoPagado: " + soli.getMontoPagado());
                    System.out.println("garantia: " + garantia);
                    System.out.println("DsctoNota: " + dsctoNota);

                } else if (getDocEgresoBean().getTipoMonedaBean().getCodigo().equals("Dolares")) {
                    soli.setDetraccionBean(detraccion);

                    Double montoASoles = soli.getMontoAprobado().doubleValue() * tipoCambio.getTcVenta().doubleValue();
                    montoASoles = (double) Math.round(montoASoles * 100) / 100;
                    System.out.println("montoASoles..." + montoASoles);
                    Double m = detraccion.getValor() / 100;
                    System.out.println("monto..." + m + "   valor->" + detraccion.getValor());
                    montoDetraccion = montoASoles * (detraccion.getValor() / 100);
                    System.out.println("montoDetraccion111..." + montoDetraccion);
                    montoDetraccion = (double) Math.round(montoDetraccion * 100) / 100;

                    montoDetraccion = detraccionService.redondearDetraccionAfavor(montoDetraccion);
                    System.out.println("montoDetraccion..." + montoDetraccion);
                    mon = (double) Math.round((montoASoles - montoDetraccion) * 100) / 100;
                    mon = (double) Math.round((mon / tipoCambio.getTcVenta().doubleValue()) * 100) / 100;
                    System.out.println("mon..." + mon);

                    soli.setMontoPagado((mon));
                    soli.setMontoDetraccion((montoDetraccion));
                    soli.getDetraccionBean().setValor(montoDetraccion);

                    Double montoPag = (soli.getMontoPagado());
                    Double garantia = (soli.getGarantia());
                    Double dsctoNota = (soli.getDsctoNotCred());

                    System.out.println("montoPagado: " + soli.getMontoPagado());

                    if (soli.getGarantia() == 0.0 || soli.getGarantia() == null) {
                        soli.setMontoPagado(montoPag);
                        if (soli.getDsctoNotCred() == null || soli.getDsctoNotCred() == 0.0) {
                            soli.setMontoPagado(montoPag);
                        } else {
                            soli.setMontoPagado(montoPag - dsctoNota);
                        }
                    } else {
                        soli.setMontoPagado(montoPag - garantia);
                        System.out.println("aaa");
                        if (soli.getDsctoNotCred() == null || soli.getDsctoNotCred() == 0.0) {
                            soli.setMontoPagado(montoPag - garantia);
                            System.out.println("aaab");
                        } else {
                            soli.setMontoPagado((montoPag - garantia) - dsctoNota);
                            System.out.println("aaac");
                        }
                    }

                    System.out.println("montoPagado: " + soli.getMontoPagado());
                    System.out.println("garantia: " + garantia);
                    System.out.println("DsctoNota: " + dsctoNota);

                }
                obtenerCambio();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerGarantia(SolicitudCajaCHBean soli) {
        try {
            Double monDouble = 0.0;
            Double mon = 0.0;
            System.out.println("obtenerGarantia");
            if (soli.getDsctoNotCred() == null) {
                soli.setMontoPagado(soli.getMontoAprobado() - soli.getDetraccionBean().getValor());
            } else {
                soli.setMontoPagado((soli.getMontoAprobado() - soli.getDetraccionBean().getValor()) - soli.getDsctoNotCred());
            }
            if (flgIdTipoDoc == true) {
                if (soli.getGarantia() == null) {
                    soli.setGarantia(0.0);
                }
                if (soli.getMontoPagado() == null) {
                    soli.setMontoPagado(0.0);
                }
                monDouble = soli.getGarantia();
                mon = (double) Math.round((soli.getMontoPagado() - monDouble) * 100) / 100;
                System.out.println("monto..." + mon);
                soli.setMontoPagado((mon));
                obtenerCambio();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoNotaCred(FacturaCompraBean fact) {
        try {
            Double monDouble = 0.0;
            Double mon = 0.0;
            System.out.println("obtenerGarantia");

            if (fact.getDsctoNotCred() == null) {
                fact.setDsctoNotCred(0.0);
                mon = fact.getMontoPago();
            } else {
                monDouble = fact.getDsctoNotCred();
                mon = fact.getMontoPago() - monDouble;
            }
            setMontoTotal(String.valueOf(mon));
            System.out.println("monto..." + mon);
            System.out.println("monto2..." + montoTotal);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDsctoNotaCredSolicitu(SolicitudCajaCHBean bean) {
        try {
            Double monDouble = 0.0;
            Double mon = 0.0;
            System.out.println("obtenerGarantia");

            if (bean.getDsctoNotCred() == null) {
                bean.setDsctoNotCred(0.0);
                if (bean.getGarantia() == null || bean.getGarantia() == 0.0) {
                    mon = (bean.getMontoPagado() - bean.getDetraccionBean().getValor());
                } else {
                    mon = ((bean.getMontoPagado() - bean.getDetraccionBean().getValor()) - bean.getGarantia());
                }
            } else {
                monDouble = bean.getDsctoNotCred();
                if (bean.getGarantia() == 0.0 || bean.getGarantia() == null) {
                    mon = ((bean.getMontoAprobado() - bean.getDetraccionBean().getValor()) - bean.getDsctoNotCred());
                } else {
                    mon = (((bean.getMontoAprobado() - bean.getDetraccionBean().getValor()) - bean.getGarantia()) - bean.getDsctoNotCred());
                }

            }
            setMontoTotal(String.valueOf(mon));
            bean.setMontoPagado(mon);
            System.out.println("montoBean..." + bean.getMontoPagado());
            System.out.println("monto..." + mon);
            System.out.println("monto2..." + montoTotal);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerTipoDoc(SolicitudCajaCHBean soli) {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorId(soli.getTipoDocBean());
            soli.setTipoDocBean(cod);

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDocEgresoFiltro() {
        docEgresoFiltroBean = new DocEgresoBean();
        listaDocEgresoBean = new ArrayList<>();
        //filtros
        fechaActual = new GregorianCalendar();
        getDocEgresoFiltroBean().setFechaInicio(fechaActual.getTime());
        getDocEgresoFiltroBean().setFechaFin(fechaActual.getTime());
        getDocEgresoFiltroBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        montoDetraccion = new Double("0.00");
        listaDetDocEgresoBean = new ArrayList<>();

    }

    public void limpiarDocEgreso() {
        ImpresoraService impresoraService = BeanFactory.getImpresoraService();
        docEgresoBean = new DocEgresoBean();
        flgCheque = false;
        montoDetraccion = new Double("0.00");
        montoTotalDouble = new Double("0.00");
        autenticarCajero();
        mostrarImpresora = false;
        listaDocEgresoFacturaBean = new ArrayList<>();
        listaDetDocEgresoBean = new ArrayList<>();
        listaSolicitudCajaChBean = new ArrayList<>();
        SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
        List<SolicitudCajaCHBean> solicitudCajaCH = new ArrayList<>();
        solicitudCajaCHMB.setListaSolicitudCajaDocEgresoBean(solicitudCajaCH);
        obtenerCuentaBancoPorTipMoneda();
    }

    public void limpiarDocEgresoGrabar() {
        ImpresoraService impresoraService = BeanFactory.getImpresoraService();
        docEgresoBean = new DocEgresoBean();
        flgCheque = false;
        montoDetraccion = new Double("0.00");
        montoTotalDouble = new Double("0.00");
        autenticarCajero();
        mostrarImpresora = false;
        listaDocEgresoFacturaBean = new ArrayList<>();
        listaDetDocEgresoBean = new ArrayList<>();
        listaSolicitudCajaChBean = new ArrayList<>();
        SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
        List<SolicitudCajaCHBean> solicitudCajaCH = new ArrayList<>();
        solicitudCajaCHMB.setListaSolicitudCajaDocEgresoBean(solicitudCajaCH);
        RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
        List<FacturaCompraBean> fact = new ArrayList<>();
        registroCompraMB.setListaFacturaCompraDocEgresoBean(fact);
//        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
//            System.out.println("karla :D");
//        } else {
//            chequeBean.setImpresora(null);
//        }
        if (listaImpresora.size() == 1) {
            getChequeBean().setImpresora(listaImpresora.get(0).getImpresora());

        } else {
            chequeBean.setImpresora(null);
        }
        obtenerCuentaBancoPorTipMoneda();
    }

    public void limpiarDocEgresoBarina() {
        docEgresoBean = new DocEgresoBean();
        flgCheque = false;
        montoDetraccion = new Double("0.00");
        montoTotalDouble = new Double("0.00");
        autenticarCajero();
        mostrarImpresora = false;
        listaDocEgresoFacturaBean = new ArrayList<>();
        listaDetDocEgresoBean = new ArrayList<>();
        listaSolicitudCajaChBean = new ArrayList<>();
        SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
        List<SolicitudCajaCHBean> solicitudCajaCH = new ArrayList<>();
        solicitudCajaCHMB.setListaSolicitudCajaDocEgresoBean(solicitudCajaCH);
        obtenerCuentaBancoPorTipMoneda();
        solicitudCajaCHMB.limpiarSolicitudCajaCH();
    }

    public void limpiarDocEgresoCaja() {
        if (docEgresoBean == null) {
            docEgresoBean = new DocEgresoBean();
        }
        flgCheque = false;
        montoDetraccion = new Double("0.00");
        montoTotalDouble = new Double("0.00");
//        autenticarCajero();
//        mostrarImpresora = false;
        listaDocEgresoFacturaBean = new ArrayList<>();
        listaDetDocEgresoBean = new ArrayList<>();
//        listaSolicitudCajaChBean = new ArrayList<>();
    }

    public void ponerDocEgresoEnChequeAnulado(Object docEgre) {
        try {
            DocEgresoService docEgr = BeanFactory.getDocEgresoService();
            docEgresoBean = (DocEgresoBean) docEgre;
            ChequeAnuladoMB chequeAnuladoMB = (ChequeAnuladoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("chequeAnuladoMB");
            System.out.println("");
            chequeAnuladoMB.getChequeAnuladoBean().setDocEgresoBean(docEgresoBean);
            chequeAnuladoMB.getChequeAnuladoBean().setUnidadNegocioBean(docEgresoBean.getUnidadNegocioBean());
            chequeAnuladoMB.getChequeAnuladoBean().setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("chequeAnuladoMB", chequeAnuladoMB);
            System.out.println("ok :D");
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosDocEgreso() {
        try {
            fechaActual = new GregorianCalendar();
            getDocEgresoRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocEgresoRepFiltroBean().setFechaFin(fechaActual.getTime());
            getDocEgresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = new ArrayList<>();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDatosPagosRep() {
        try {
            fechaActual = new GregorianCalendar();
            getPagosEmitidosRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setFechaFin(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = new ArrayList<>();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConcepto();

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imrprimirChequesLPM() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
//            Date inicio = docEgresoRepFiltroBean.getFechaInicio();
//            Date fin = docEgresoRepFiltroBean.getFechaFin();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteChequesEmitidosLPM.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            Integer idMoneda = docEgresoBean.getTipoMonedaBean().getIdCodigo();
            Integer idTipoModoPago = docEgresoBean.getTipoPagoBean().getIdCodigo();

            List<ChequesEmitidosLPMRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = docEgresoService.obtenerChequesLPMCabecera(uniNeg, anio, mes, idMoneda, idTipoModoPago);
            if (!listaCabecera.isEmpty()) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    List<ChequesEmitidosLPMRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = docEgresoService.obtenerChequesLPMDetalle(uniNeg, anio, mes, listaCabecera.get(j).getNumCheque());
                    listaCabecera.get(j).setListaDetalle(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ChequesEmitidosLPMRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = docEgresoService.obtenerChequesLPMSubDetalle(uniNeg, anio, mes, listaCabecera.get(j).getNumCheque());
                            listaDetalle.get(s).setListaSubDetalle(listaSubDetalle);
                            listaCabecera.get(j).setListaDetalle(listaDetalle);
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void obtenerExcelFormatoLpmCheque() {
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();

            List<ChequesEmitidosLPMRepBean> listaCabecera = new ArrayList<>();
            listaChequeEmitidosLPM = new ArrayList<>();
            Integer idMoneda = docEgresoBean.getTipoMonedaBean().getIdCodigo();
            Integer idTipoModoPago = docEgresoBean.getTipoPagoBean().getIdCodigo();
            listaCabecera = docEgresoService.obtenerChequesLPMCabecera(uniNeg, anio, mes, idMoneda, idTipoModoPago);
            if (!listaCabecera.isEmpty()) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    List<ChequesEmitidosLPMRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = docEgresoService.obtenerChequesLPMDetalle(uniNeg, anio, mes, listaCabecera.get(j).getNumCheque());
                    listaCabecera.get(j).setListaDetalle(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ChequesEmitidosLPMRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = docEgresoService.obtenerChequesLPMSubDetalle(uniNeg, anio, mes, listaCabecera.get(j).getNumCheque());
                            listaDetalle.get(s).setListaSubDetalle(listaSubDetalle);
                            listaCabecera.get(j).setListaDetalle(listaDetalle);
                            listaChequeEmitidosLPM.add(listaCabecera.get(0));
                        }
                    }
                }
            }

            for (ChequesEmitidosLPMRepBean l : listaChequeEmitidosLPM) {
                System.out.println("listaaaa " + l.getNumCheque());
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarDetDocFiltroDocIng() {
        try {
            listaDocEgresoRepBean = new ArrayList<>();
            docEgresoRepFiltroBean = new DocEgresoRepBean();
            fechaActual = new GregorianCalendar();
            getDocEgresoRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getDocEgresoRepFiltroBean().setFechaFin(fechaActual.getTime());
            getDocEgresoRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            resetMontosRep();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPagosEmitidos() {
        try {
            listaPagosEmitidosRepBean = new ArrayList<>();
            docEgresoRepFiltroBean = new DocEgresoRepBean();
            fechaActual = new GregorianCalendar();
            getPagosEmitidosRepFiltroBean().setFechaInicio(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setFechaFin(fechaActual.getTime());
            getPagosEmitidosRepFiltroBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            resetMontosRep();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostarTipoPago() {
        try {
            if (docEgresoBean.getTipoPagoBean().getIdCodigo() != null) {
                CodigoService codigoService = BeanFactory.getCodigoService();
                CodigoBean cod = new CodigoBean();
                cod.setIdCodigo(docEgresoBean.getTipoPagoBean().getIdCodigo());
                cod = codigoService.obtenerPorId(cod);
                if (cod.getIdCodigo() != null) {
                    docEgresoBean.setTipoPagoBean(cod);
                }
                System.out.println("tipo " + docEgresoBean.getTipoPagoBean().getCodigo());
                if (docEgresoBean.getTipoPagoBean().getIdCodigo().equals(MaristaConstantes.CODIGO_CHEQUE)) {
                    if (chequeBean.getImpresora() != null) {
                        getDocEgresoBean().setDescripTransfCta(null);
                        flgCheque = true;
                        ChequeService chequeService = BeanFactory.getChequeService();
                        ChequeBean cheque = new ChequeBean();
//                        cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//                        cheque.setImpresora(chequeBean.getImpresora()); 
                        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                            cheque = chequeService.obtenerUltimoChequeMasUno(docEgresoBean.getTipoMonedaBean().getIdCodigo(), chequeBean.getImpresora(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        } else {
                            cheque = chequeService.obtenerUltimoChequeMasUno(docEgresoBean.getTipoMonedaBean().getIdCodigo(), chequeBean.getImpresora(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        }
                        if (cheque != null) {
                            docEgresoBean.setNumCheque(cheque.getActual());
                        } else {
                            new MensajePrime().addInformativeMessagePer("msjImpNoCheque");
                        }
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                        flgCheque = false;
                        docEgresoBean.setNumCheque(null);
                    }
                } else if (docEgresoBean.getTipoPagoBean().getIdCodigo().equals(MaristaConstantes.CODIGO_TRANSFERENCIA)) {
                    if (chequeBean.getImpresora() != null) {
                        flgCheque = false;
                        DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                        String actual = null;
                        actual = docEgresoService.obtenerActualMas1PorTipoPago(docEgresoBean.getTipoPagoBean().getCodigo(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        if (actual != null) {
                            docEgresoBean.setNumCheque(actual.toString());
                        }
                        EntidadService entidadService = BeanFactory.getEntidadService();
                        String ruc = "";
                        int estado = 0;
                        if (getDocEgresoBean().getSolicitudCajaCHBean().getIdRespCheque() != null) {
                            ruc = getDocEgresoBean().getSolicitudCajaCHBean().getIdRespCheque();
                            estado = 1;
                        }
                        if (getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().getRuc() != null) {
                            ruc = getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().getRuc();
                            estado = 1;
                        }
                        if (estado == 1) {
                            String txt = entidadService.obtenerInfoCta(ruc, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), docEgresoBean.getTipoMonedaBean().getCodigo());
                            System.out.println("txt " + txt);
                            if (txt != null && !txt.equals(null)) {
                                getDocEgresoBean().setDescripTransfCta(txt);
                            }
                            System.out.println("txt " + getDocEgresoBean().getDescripTransfCta());
                        } else {
                            System.out.println("txt 0");
                        }
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                    }
                } else {
                    flgCheque = false;
                    if (chequeBean.getImpresora() != null) {
                        flgCheque = false;
                        DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                        String actual = null;
                        actual = docEgresoService.obtenerActualMas1PorTipoPago(docEgresoBean.getTipoPagoBean().getCodigo(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        if (actual != null) {
                            docEgresoBean.setNumCheque(actual.toString());
                        }
                        EntidadService entidadService = BeanFactory.getEntidadService();
                        String ruc = "";
                        int estado = 0;
                        if (getDocEgresoBean().getSolicitudCajaCHBean().getIdRespCheque() != null) {
                            ruc = getDocEgresoBean().getSolicitudCajaCHBean().getIdRespCheque();
                            estado = 1;
                        }
                        if (getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().getRuc() != null) {
                            ruc = getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().getRuc();
                            estado = 1;
                        }
                        if (estado == 1) {
                            String txt = entidadService.obtenerInfoCta(ruc, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), docEgresoBean.getTipoMonedaBean().getCodigo());
                            System.out.println("txt " + txt);
                            if (txt != null && !txt.equals(null)) {
                                getDocEgresoBean().setDescripTransfCta(txt);
                            }
                            System.out.println("txt " + getDocEgresoBean().getDescripTransfCta());
                        } else {
                            System.out.println("txt 0");
                        }
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verImpresora() {
        try {
//            System.out.println(chequeBean.getImpresora());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cheque() {
        try {
            if (chequeBean.getImpresora() != null) {

            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroDocEgreso(String tipo) {
        try {
            int estado = 0;
            this.mostrarPanel = true;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            if (docEgresoFiltroBean.getFechaInicio() != null) {
                Timestamp t = new Timestamp(docEgresoFiltroBean.getFechaInicio().getTime());
                t.setHours(0);
                t.setMinutes(0);
                t.setSeconds(0);
                docEgresoFiltroBean.setFechaInicio(t);
                estado = 1;
            }
            if (docEgresoFiltroBean.getFechaFin() != null) {
                Timestamp u = new Timestamp(docEgresoFiltroBean.getFechaFin().getTime());
                u.setHours(23);
                u.setMinutes(59);
                u.setSeconds(59);
                docEgresoFiltroBean.setFechaFin(u);
                estado = 1;
            }
            if (docEgresoFiltroBean.getIdDocEgreso() != null && !docEgresoFiltroBean.getIdDocEgreso().equals(0)) {
                docEgresoFiltroBean.setIdDocEgreso(docEgresoFiltroBean.getIdDocEgreso());
                estado = 1;
            }
            if (docEgresoFiltroBean.getDetraccionBean().getIdDetraccion() != null && !docEgresoFiltroBean.getDetraccionBean().getIdDetraccion().equals(0)) {
                docEgresoFiltroBean.getDetraccionBean().setIdDetraccion(docEgresoFiltroBean.getDetraccionBean().getIdDetraccion());
                estado = 1;
            }
            if (docEgresoFiltroBean.getFacturaCompraBean().getRegistroCompraBean().getIdRegistroCompra() != null && !docEgresoFiltroBean.getFacturaCompraBean().getRegistroCompraBean().getIdRegistroCompra().equals(0)) {
                docEgresoFiltroBean.getFacturaCompraBean().getRegistroCompraBean().setIdRegistroCompra(docEgresoFiltroBean.getFacturaCompraBean().getRegistroCompraBean().getIdRegistroCompra());
                estado = 1;
            }
            if (docEgresoFiltroBean.getEntidadBean().getRuc() != null && !docEgresoFiltroBean.getEntidadBean().getRuc().equals("")) {
                docEgresoFiltroBean.getEntidadBean().setRuc(docEgresoFiltroBean.getEntidadBean().getRuc());
                estado = 1;
            }
            if (docEgresoFiltroBean.getGlosa() != null && !docEgresoFiltroBean.getGlosa().equals("")) {
                docEgresoFiltroBean.setGlosa(docEgresoFiltroBean.getGlosa().toUpperCase().trim());
                estado = 1;
            }
            if (docEgresoFiltroBean.getTipoPagoBean().getIdCodigo() != null && !docEgresoFiltroBean.getTipoPagoBean().getIdCodigo().equals(0)) {
                docEgresoFiltroBean.getTipoPagoBean().setIdCodigo(docEgresoFiltroBean.getTipoPagoBean().getIdCodigo());
                estado = 1;
            }
            if (docEgresoFiltroBean.getCuentaBancoBean().getNumCuenta() != null && !docEgresoFiltroBean.getCuentaBancoBean().getNumCuenta().equals(0)) {
                docEgresoFiltroBean.getCuentaBancoBean().setNumCuenta(docEgresoFiltroBean.getCuentaBancoBean().getNumCuenta());
                estado = 1;
            }
            if (docEgresoFiltroBean.getNumCheque() != null && !docEgresoFiltroBean.getNumCheque().equals(0)) {
                docEgresoFiltroBean.setNumCheque(docEgresoFiltroBean.getNumCheque());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaDocEgresoBean = new ArrayList<>();
            }
            if (estado == 1) {
                if (tipo.equals(MaristaConstantes.COD_CHEQUE)) {
                    listaDocEgresoBean = docEgresoService.obtenerDocEgresoChequePorFiltro(docEgresoFiltroBean);
                } else if (tipo.equals(MaristaConstantes.COD_CHEQUE_No_Anulado)) {
                    listaDocEgresoBean = docEgresoService.obtenerDocEgresoPorFiltroDistinctNoAnulados(docEgresoFiltroBean);
                } else {
                    listaDocEgresoBean = docEgresoService.obtenerDocEgresoPorFiltroDistinct(docEgresoFiltroBean);
                }
                if (listaDocEgresoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarDocEgreso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (chequeBean.getImpresora() != null) {
                    String opc = "false";
                    if (listaDocEgresoFacturaBean.size() > 0) {
                        opc = "true";
                    } else {
                        for (SolicitudCajaCHBean soli : getListaSolicitudCajaChBean()) {
                            if (soli.getDocRefAyuda() == null || soli.equals("-")) {
                                opc = "false";
                            } else {
                                if (soli.getMontoPagado() == null) {
                                    opc = "false";
                                } else {
                                    opc = "true";
                                }
                            }
                            break;
                        }
                    }
                    if (opc.equals("true")) {
                        //                    if (docEgresoBean.getRegistroCompraBean().getIdRegistroCompra() != null || docEgresoBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
                        DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                        getDocEgresoBean().setTipoCambioBean(tipoCambio);
                        docEgresoService.insertarDocEgreso(docEgresoBean, listaDocEgresoFacturaBean, usuarioLoginBean, cajeroCajaBean, origen, chequeBean.getImpresora(), listaSolicitudCajaChBean);
                        listaDocEgresoTop1Bean = docEgresoService.obtenerDocEgresoPorIdDistinct(docEgresoBean);
                        if (origen == 0) {
                            if (!listaDocEgresoTop1Bean.isEmpty()) {
                                for (DocEgresoBean detDoc : getListaDocEgresoTop1Bean()) {
                                    docEgresoBean = detDoc;
                                }
                            }
                            listaDetDocEgresoBean = docEgresoService.obtenerPorNroDocEgreso(docEgresoBean);
                            if (docEgresoBean.getTipoPagoBean().getCodigo().equals(MaristaConstantes.COD_CHEQUE)) {
                                mostrarCheque = true;
                            } else {
                                mostrarCheque = false;
                            }
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        mostrarImpresora = true;
                        autenticarCajero();
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                    }
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String insertarDocEgresoBarina() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (chequeBean.getImpresora() != null) {
                    String opc = "false";
                    if (listaDocEgresoFacturaBean.size() > 0) {
                        opc = "true";
                    } else {
                        for (SolicitudCajaCHBean soli : getListaSolicitudCajaChBean()) {
                            if (soli.getDocRefAyuda() == null || soli.equals("-")) {
                                opc = "false";
                            } else {
                                if (soli.getMontoPagado() == null) {
                                    opc = "false";
                                } else {
                                    opc = "true";
                                }
                            }
                            break;
                        }
                    }
                    if (opc.equals("true")) {
                        //                    if (docEgresoBean.getRegistroCompraBean().getIdRegistroCompra() != null || docEgresoBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
                        DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                        getDocEgresoBean().setTipoCambioBean(tipoCambio);
                        docEgresoService.insertarDocEgresoBarina(docEgresoBean, listaDocEgresoFacturaBean, usuarioLoginBean, cajeroCajaBean, origen, chequeBean.getImpresora(), listaSolicitudCajaChBean);
                        listaDocEgresoTop1Bean = docEgresoService.obtenerDocEgresoPorIdDistinct(docEgresoBean);
                        if (origen == 0) {
                            if (!listaDocEgresoTop1Bean.isEmpty()) {
                                for (DocEgresoBean detDoc : getListaDocEgresoTop1Bean()) {
                                    docEgresoBean = detDoc;
                                }
                            }
                            listaDetDocEgresoBean = docEgresoService.obtenerPorNroDocEgreso(docEgresoBean);
                            if (docEgresoBean.getTipoPagoBean().getCodigo().equals(MaristaConstantes.COD_CHEQUE)) {
                                mostrarCheque = true;
                            } else {
                                mostrarCheque = false;
                            }
                        }
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        mostrarImpresora = true;
                        autenticarCajero();
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
                    }
                } else {
                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerListaBarina(SolicitudCajaCHBean soli) {

        try {
            SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
            List<SolicitudCajaCHBean> solicitudCajaCH = new ArrayList<>();
            solicitudCajaCH.add(soli);
            docEgresoBean.setSolicitudCajaCHBean(soli);
            solicitudCajaCHMB.setListaSolicitudesEnSesio(solicitudCajaCH);
//            listaSolicitudCajaChBean = solicitudCajaCHMB.getListaSolicitudesEnSesio();
            getListaSolicitudCajaChBean();
            for (SolicitudCajaCHBean so : listaSolicitudCajaChBean) {
                docEgresoBean.getSolicitudCajaCHBean().setNombreSolicitante(so.getNombreSolicitante());
                System.out.println("nombre solicitante: " + so.getNombreSolicitante());
                solicitudCajaCHMB.ponerSoliCajaEnDocEgreso(so);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    public String quitaEspacios(String numCheque) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(numCheque);
        numCheque = "";
        while (tokens.hasMoreTokens()) {
            numCheque += "" + tokens.nextToken();
        }
        numCheque = numCheque.toString();
        numCheque = numCheque.trim();
        docEgresoBean.setNumCheque(numCheque);
        System.out.println("num: " + numCheque);
        return numCheque;
    }

    public String modificarFechaDocEgreso() {
        String pagina = null;
        try {
            if (pagina == null) {
                DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                DocEgresoBean doc = new DocEgresoBean();
                quitaEspacios(docEgresoBean.getNumCheque());
                doc.setNumCheque(docEgresoBean.getNumCheque());
                doc.setNroDocEgreso(docEgresoBean.getNroDocEgreso());
                System.out.println("DocEgreso: " + docEgresoBean.getNroDocEgreso());
                doc.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                DocEgresoBean doc2 = new DocEgresoBean();
//                doc2 = docEgresoService.obtenerDocEgresoCheque(doc);
//                docEgresoBean.setIdDocEgreso(doc2.getIdDocEgreso());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(docEgresoBean.getCreaFecha());
                doc.setCreaFecha(formato.parse(date));
                docEgresoService.modificarFechaDocEgreso(doc);
                System.out.println("fecha: " + docEgresoBean.getCreaFecha());
                System.out.println("unidad: " + docEgresoBean.getUnidadNegocioBean().getUniNeg());
                System.out.println("numChe: " + docEgresoBean.getNumCheque());
                System.out.println("DocEgreso: " + docEgresoBean.getIdDocEgreso());
                System.out.println("DocEgreso: " + docEgresoBean.getNroDocEgreso());
                this.flgImpresionFecha = true;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void actualizaNroCheque() {
        try {

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void quitarFactura(FacturaCompraBean factura) {
        try {
            listaDocEgresoFacturaBean.remove(factura);
//            RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
//            registroCompraMB.getListaFacturaCompraDocEgresoBean().add(factura); 
//            FacesContext.getCurrentInstance().getViewRoot().getViewMap().put("registroCompraMB", registroCompraMB);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCuentaBancoPorTipMoneda() {
        try {
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            CodigoBean cod = new CodigoBean();
            CodigoService codigo = BeanFactory.getCodigoService();
            cod = codigo.obtenerPorId(docEgresoBean.getTipoMonedaBean());
            listaCuentaBancoBean = cuentaBancoService.obtenerBancoPorTipMonedaBcoColegio(MaristaConstantes.TIP_MON, cod.getCodigo(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (listaCuentaBancoBean.size() == 1) {
                getDocEgresoBean().getCuentaBancoBean().setNumCuenta(listaCuentaBancoBean.get(0).getNumCuenta());
                obtenerRuc();
            } else {
                if (!listaCuentaBancoBean.isEmpty()) {
                    if (getDocEgresoBean().getTipoPagoBean().getIdCodigo() == null) {
                        getDocEgresoBean().getTipoPagoBean().setIdCodigo(MaristaConstantes.CODIGO_CHEQUE);
                    }
                    System.out.println("entró..." + getDocEgresoBean().getTipoPagoBean().getIdCodigo() + "-" + getDocEgresoBean().getTipoPagoBean().getCodigo());
                    CodigoBean tipoCta = new CodigoBean();

                    if (getDocEgresoBean().getTipoPagoBean().getIdCodigo().equals(MaristaConstantes.CODIGO_CHEQUE)) {
                        tipoCta.setCodigo(MaristaConstantes.COD_TIPO_CTA_CTE);
                    }
                    if (getDocEgresoBean().getTipoPagoBean().getIdCodigo().equals(MaristaConstantes.CODIGO_TRANSFERENCIA)) {
                        tipoCta.setCodigo(MaristaConstantes.COD_TIPO_AHORROS);
                    }
                    System.out.println("tipoCta setCodigo " + tipoCta.getCodigo());
                    tipoCta.getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_CUENTA_BCO.toString());
                    tipoCta = codigo.obtenerPorCodigo(tipoCta);
                    CuentaBancoBean cta = new CuentaBancoBean();
                    cta.getEntidadBancoBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    cta.setTipoMonedaBean(docEgresoBean.getTipoMonedaBean());
                    cta.setFlgCobranza(Boolean.FALSE);
                    cta.setFlgCtaCongre(Boolean.FALSE);
                    if (tipoCta != null) {
                        cta.setTipoCuentaBancoBean(tipoCta);
                    }
                    List<CuentaBancoBean> lista = new ArrayList<>();
                    lista = cuentaBancoService.obtenerCuentaPorTipo(cta);
                    if (lista != null) {
                        if (!lista.isEmpty()) {
                            if (lista.size() == 1) {
                                cta = lista.get(0);
                            }
                        }
                    }

//                    cta = cuentaBancoService.obtenerCuentaPorTipo(cta);
//                System.out.println("cta " + cta.getNumCuenta());
                    if (cta != null) {
                        if (listaSolicitudCajaChBean == null) {
                            cta.setTipoCuentaBancoBean(tipoCta);
                            getDocEgresoBean().setCuentaBancoBean(cta);
                        } else {
                            if (listaSolicitudCajaChBean.isEmpty()) {
                                cta.setTipoCuentaBancoBean(tipoCta);
                                getDocEgresoBean().setCuentaBancoBean(cta);
                            } else {
                                for (SolicitudCajaCHBean listaSol : listaSolicitudCajaChBean) {
                                    if (listaSol.getNumCuenta() == null) {
                                        cta.setTipoCuentaBancoBean(tipoCta);
                                        getDocEgresoBean().setCuentaBancoBean(cta);
                                    } else {
                                        getDocEgresoBean().getCuentaBancoBean().setNumCuenta(listaSol.getNumCuenta());
                                        getDocEgresoBean().getCuentaBancoBean().getEntidadBancoBean().setRuc(listaSol.getRucBanco());
                                    }
                                }
                            }
                        }

                        System.out.println("cta por defect " + cta.getNumCuenta());
                        System.out.println("ctaaaaa final" + getDocEgresoBean().getCuentaBancoBean().getNumCuenta());

                        obtenerRuc();
                    }
                } else {
                    System.out.println("x");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerRuc() {
        try {
            for (int i = 0; i < listaCuentaBancoBean.size(); i++) {
                if (docEgresoBean.getCuentaBancoBean().getNumCuenta() != null
                        && listaCuentaBancoBean.get(i).getNumCuenta().equals(docEgresoBean.getCuentaBancoBean().getNumCuenta())) {
                    docEgresoBean.getCuentaBancoBean().getEntidadBancoBean().setRuc(listaCuentaBancoBean.get(i).getEntidadBancoBean().getRuc());
                    break;
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDocEgresoPorId(Object objeto) {
        try {
            docEgresoBean = (DocEgresoBean) objeto;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();

            listaDocEgresoTop1Bean = docEgresoService.obtenerDocEgresoPorIdDistinct(docEgresoBean);
            if (!listaDocEgresoTop1Bean.isEmpty()) {
                for (DocEgresoBean detDoc : getListaDocEgresoTop1Bean()) {
                    docEgresoBean = detDoc;
                }
            }
//            docEgresoBean = docEgresoService.obtenerDocEgresoPorId(docEgresoBean);
            listaDetDocEgresoBean = docEgresoService.obtenerPorNroDocEgreso(docEgresoBean);
            if (docEgresoBean.getTipoPagoBean().getCodigo().equals(MaristaConstantes.COD_CHEQUE)) {
                mostrarCheque = true;
            } else {
                mostrarCheque = false;
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDocEgresoPorId222(Object objeto) {
        try {
            docEgresoBean = (DocEgresoBean) objeto;
            this.flgImpresionFecha = false;
            imprimirPdf();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDocEgresoPorIdFecha(String numCheque) {
        try {
//            docEgresoBean = (DocEgresoBean) objeto;
            this.flgImpresionFecha = true;
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            DocEgresoBean doc = new DocEgresoBean();
            quitaEspacios(numCheque);
            doc.setNumCheque(docEgresoBean.getNumCheque());
            doc.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            doc.setNroDocEgreso(docEgresoBean.getNroDocEgreso());
            docEgresoBean = docEgresoService.obtenerDocEgresoPorCheque(doc);
            imprimirPdf();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarDocEgreso(Object objeto) {
        try {
            docEgresoBean = (DocEgresoBean) objeto;
//            DocEgresoService docEgresoService=BeanFactory.getDocEgresoService();
//            docEgresoBean=docEgresoService.obtenerDocEgresoCheque(docEgresoBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(getDocEgresoRepFiltroBean().getIdTipoConcepto(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo2(Integer idTipo) {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(idTipo, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetalleDocEgreso(DocEgresoRepBean bean) {
        try {
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            docEgresoBean.setNumCheque(bean.getNumCheque());
            docEgresoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaDocEgresoDetalle = docEgresoService.obtenerListaDocEgresoDetalle(docEgresoBean);
            System.out.println("cantidad: " + listaDocEgresoDetalle.size());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void validarTipoPago() {
        try {
            if (getDocEgresoBean().getTipoPagoBean().getCodigo() != null) {
                if (getDocEgresoBean().getTipoPagoBean().getCodigo().equals("Cheque")) {
                    this.flgCheque = true;
                } else {
                    this.flgCheque = false;
                }
            } else {
                this.flgCheque = false;
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void obtenerDocEgresoPorId2() {
//        try {
//
//            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
//            docEgresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
//            docEgresoBean = docEgresoService.obtenerDocEgresoPorId(docEgresoBean);
//            if (docEgresoBean.getTipoPagoBean().getCodigo().equals(MaristaConstantes.COD_CHEQUE)) {
//                mostrarCheque = true;
//            } else {
//                mostrarCheque = false;
//            }
//            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    public void imprimirPdf() {
        ServletOutputStream out = null;
        try {
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            List<DocEgresoRepBean> listaDocEgresoRep = new ArrayList<>();
            DocEgresoBean docEgreso = new DocEgresoBean();
            docEgreso.setNroDocEgreso(docEgresoBean.getNroDocEgreso());
            docEgreso.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            if (getFlgImpresionFecha() != null) {
                if (flgImpresionFecha == false) {
                    listaDocEgresoRep = docEgresoService.obtenerDocEgreso(docEgreso);
                } else {
                    listaDocEgresoRep = docEgresoService.obtenerDocEgreso2(docEgreso);
                }
            } else {
                listaDocEgresoRep = docEgresoService.obtenerDocEgreso(docEgreso);
            }
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteDocEgreso.jasper");
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS) && !listaDocEgresoRep.get(0).getModoPago().equals("Modo Pago: Transferencia") && !listaDocEgresoRep.get(0).getModoPago().equals("Modo Pago: Carta orden")) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocEgresoFormatoVoucherMatriz.jasper");
//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocEgresoFormatoVoucher.jasper");
            }
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR) && !listaDocEgresoRep.get(0).getModoPago().equals("Modo Pago: Transferencia") && !listaDocEgresoRep.get(0).getModoPago().equals("Modo Pago: Carta orden")) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocEgresoFormatoVoucherMatrizSector.jasper");
//                        getRequest()).getServletContext().getRealPath("/reportes/reporteDocEgresoFormatoVoucher.jasper");
            }
            System.out.println("ruta " + archivoJasper);
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
//            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            if (!listaDocEgresoRep.isEmpty()) {
                if (listaDocEgresoRep.get(0).getTipoDocEgreso().equals("F")) {
                    for (int i = 0; i < listaDocEgresoRep.size(); i++) {
                        //obteniendo cuentas 
                        List<DetDocEgresoRepBean> listaRepDetDocEgreso = new ArrayList<>();
                        listaRepDetDocEgreso = docEgresoService.obtenerDetalleFacturaDocEgreso(listaDocEgresoRep.get(0).getNroDocEgreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        //obteniendo detracc
                        for (int l = 0; l < listaRepDetDocEgreso.size(); l++) {
                            if (listaRepDetDocEgreso.get(l).getDetra().equals(1)) {
                                Integer count = 0;
                                if (listaRepDetDocEgreso.get(l).getCountDetra() == null) {
                                    listaRepDetDocEgreso.get(l).setCountDetra(0);
                                }
                                Integer idFact = listaRepDetDocEgreso.get(l).getIdSolRC();
                                for (DetDocEgresoRepBean lista : listaRepDetDocEgreso) {
//                                    System.out.println("id L: " + idFact + " id lista " + lista.getIdSolRC() + " count " + listaRepDetDocEgreso.get(l).getCountDetra());
                                    if (lista.getIdSolRC().equals(idFact)) {
                                        count = listaRepDetDocEgreso.get(l).getCountDetra() + 1;
                                        listaRepDetDocEgreso.get(l).setCountDetra(count);
                                        for (DetDocEgresoRepBean listaRepDetDocEgreso1 : listaRepDetDocEgreso) {
                                            if (listaRepDetDocEgreso1.getIdSolRC().equals(idFact)) {
                                                listaRepDetDocEgreso1.setNroLista(l);
                                            }
                                        }
                                    } else {
                                        if (listaRepDetDocEgreso.get(l).getCountDetra() < 1) {
                                            System.out.println("equal cero");
                                            count = 0;
                                        } else {
                                            count = 1;
                                            listaRepDetDocEgreso.get(l).setNroLista(l);
                                        }
                                        listaRepDetDocEgreso.get(l).setCountDetra(count);
                                    }
                                }
                            } else {
                                listaRepDetDocEgreso.get(l).setCountDetra(0);
                            }
                        }
                        listaDocEgresoRep.get(0).setListaDetDocEgresoRepBean(listaRepDetDocEgreso);

                        if (!listaRepDetDocEgreso.isEmpty()) {
                            for (int j = 0; j < listaDocEgresoRep.get(0).getListaDetDocEgresoRepBean().getData().size(); j++) {
                                //obteniendo CRs 
                                Integer detra = 0;
                                if (listaRepDetDocEgreso.get(j).getDetra().equals(1)) {
                                    if (listaRepDetDocEgreso.get(j).getCountDetra() > 1) {
                                        Integer c = j;
                                        Integer x = listaRepDetDocEgreso.get(j).getNroLista();
                                        System.out.println("c " + c + " / x " + x);
                                        if (c.equals(x)) {
                                            detra = 1;
                                        } else {
                                            detra = 0;
                                        }
                                    } else {
                                        detra = listaRepDetDocEgreso.get(j).getDetra();
                                    }
                                }
                                System.out.println("detra " + detra);
                                List<DetDetDocEgresoRepBean> listaRepDetDetDocEgreso = new ArrayList<>();
                                listaRepDetDetDocEgreso = docEgresoService.obtenerDetalleDetalleFacturaDocEgreso(listaDocEgresoRep.get(0).getNroDocEgreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), new Integer(listaRepDetDocEgreso.get(j).getIdSolRC()), detra, listaRepDetDocEgreso.get(j).getCuentaD());
                                listaRepDetDocEgreso.get(j).setListaDetDetDocEgresoRepBean(listaRepDetDetDocEgreso);
                                listaDocEgresoRep.get(0).setListaDetDocEgresoRepBean(listaRepDetDocEgreso);
                            }
                        } else {
                            System.out.println("lista null listaRepDetDocEgreso");
                        }
                    }
                } else {
                    for (int i = 0; i < listaDocEgresoRep.size(); i++) {
                        List<DetDocEgresoRepBean> listaRepDetDocEgreso = new ArrayList<>();
                        listaRepDetDocEgreso = docEgresoService.obtenerDetalleDocEgreso(listaDocEgresoRep.get(0).getNroDocEgreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listaDocEgresoRep.get(0).setListaDetDocEgresoRepBean(listaRepDetDocEgreso);
                        if (!listaRepDetDocEgreso.isEmpty()) {
                            for (int j = 0; j < listaDocEgresoRep.get(0).getListaDetDocEgresoRepBean().getData().size(); j++) {
                                System.out.println("(listaRepDetDocEgreso.get(j).getGarantia())->" + (listaRepDetDocEgreso.get(j).getGara()));
                                List<DetDetDocEgresoRepBean> listaRepDetDetDocEgreso = new ArrayList<>();
                                listaRepDetDetDocEgreso = docEgresoService.obtenerDetalleDetalleDocEgreso(listaDocEgresoRep.get(0).getNroDocEgreso(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), new Integer(listaRepDetDocEgreso.get(j).getIdSolRC()), (listaRepDetDocEgreso.get(j).getDetra()), (listaRepDetDocEgreso.get(j).getGara()));
                                System.out.println("size " + listaRepDetDetDocEgreso.size());
                                listaRepDetDocEgreso.get(j).setListaDetDetDocEgresoRepBean(listaRepDetDetDocEgreso);
                                listaDocEgresoRep.get(0).setListaDetDocEgresoRepBean(listaRepDetDocEgreso);
                            }
                        } else {
                            System.out.println("lista null listaRepDetDocEgreso");
                        }
                    }
                }

            } else {
                System.out.println("lista null listaDocEgresoRep");
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaDocEgresoRep);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            System.out.println("archivo: " + archivoJasper);
            System.out.println("ruta: " + ruta);
            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=ArqGen_Det_" + listaDocEgresoRep.get(0).getNumCheque() + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
//            }
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

    public void imprimirRepChequesPdf() {
        // reportes////////////////////////////////////////// 
        ServletOutputStream out = null;
        try {
            if (listaDocEgresoRepBean != null) {
                if (!listaDocEgresoRepBean.isEmpty()) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                            getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reporteChequesEmitidos.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    List<DocEgresoRepBean> lista = new ArrayList<>();
                    lista = listaDocEgresoRepBean;
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

    public void imprimirRepPagosPdf() {
        // reportes////////////////////////////////////////// 
        ServletOutputStream out = null;
        try {
            if (listaPagosEmitidosRepBean != null) {
                if (!listaPagosEmitidosRepBean.isEmpty()) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                            getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/reportes/reportePagosEmitidos.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    List<PagosEmitidosRepBean> lista = new ArrayList<>();
                    lista = listaPagosEmitidosRepBean;
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

    public String autenticarCajero() {
        String pagina = null;
        try {

            CajeroCajaBean cajeroCaja = new CajeroCajaBean();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            ImpresoraService impresoraService = BeanFactory.getImpresoraService();
            cajeroCaja.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
            cajeroCaja.setUsuarioBean(usuarioLoginBean);
//-----------IP CLIENTE
//            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            String remoteAddress = request.getRemoteAddr();
//            cajeroCajaBean.getCajaBean().setHostIp(remoteAddress);
//----------IP SERVIDOR
            InetAddress localHost = InetAddress.getLocalHost();
            cajeroCaja.getCajaBean().setHostIp(localHost.getHostAddress());
            System.out.println(">>>" + localHost.getHostAddress());
            cajeroCaja = cajeroService.autenticarUsuarioConCaja(cajeroCaja);
            cajeroCajaBean = cajeroCaja;
            listaImpresora = new ArrayList<>();
            if (cajeroCajaBean != null) {
                SimpleDateFormat formatoDiaCompleto = new SimpleDateFormat("dd-MM-yy");
                String dateCompleto = formatoDiaCompleto.format(new Date());
                CajaGenBean cajaGen = new CajaGenBean();
                cajaGen.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cajaGen.setCajaBean(cajeroCajaBean.getCajaBean());
                Calendar.getInstance().get((Calendar.YEAR));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy");
                String date = formato.format(new Date());
                cajaGen.setAnio(Integer.parseInt(date));
                cajaGen.setUsuarioBean(usuarioLoginBean);
                cajaGen.setFecApertura(formatoDiaCompleto.parse(dateCompleto));
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarApertura(cajaGen);
                if (cajaGeneral != null) {
                    System.out.println("idCajaGen: " + cajaGeneral.getIdCajaGen());
                }
                if (cajaGeneral == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeNoApertura", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                    this.flgCajaAperturada = false;
                } else if (cajaGeneral.getFecCierre() != null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeYaCerrada", null), MensajesBackEnd.getValueOfKey("msgsNoCaj", null)));
                    this.flgCajaAperturada = false;
                } else {
                    String rutaCajero = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                            getRequest()).getServletContext().getRealPath("/mantenimientos/mantDocEgreso.xhtml");
                    new MaristaUtils().sesionColocarObjeto("ruta_cajero", rutaCajero);
                    pagina = "toRoot";
                    System.out.println("ruta" + rutaCajero);
                    listaImpresora = docIngresoService.obtenerImpresoraCajeroDocEgreso(cajeroCajaBean);
                    if (!listaImpresora.isEmpty()) {
                        listaImpresora = impresoraService.obtenerImpresoraPrincipal(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        if (listaImpresora.size() == 1) {
                            getChequeBean().setImpresora(listaImpresora.get(0).getImpresora());
                            CodigoService codigoService = BeanFactory.getCodigoService();
                            CodigoBean codigoTipoModoPago = new CodigoBean();
                            codigoTipoModoPago = codigoService.obtenerPorCodigo(new CodigoBean(0, "Cheque", new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO)));
                            docEgresoBean.setTipoPagoBean(codigoTipoModoPago);
                            this.flgCheque = true;
                            mostarTipoPago();
                        }
                    }
                    this.flgCajaAperturada = true;
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

    public String eliminarDocEgreso() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                Calendar miCalendario = Calendar.getInstance();
                Double montoCajaDol = new Double("0.00");
                Double montoCajaSol = new Double("0.00");

                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                CajaGenBean cajaGenBean = new CajaGenBean();
                cajaGenBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCajaBean(cajeroCajaBean.getCajaBean());
                cajaGenBean.setUsuarioBean(usuarioLoginBean);
                cajaGenBean.setAnio(miCalendario.get(Calendar.YEAR));
                cajaGenBean.setFecApertura(new Date());
                cajaGenBean = cajaGenService.verificarApertura(cajaGenBean);

                montoCajaSol = cajaGenBean.getEgresoSol();
                montoCajaDol = cajaGenBean.getEgresoDol();

                switch (docEgresoBean.getTipoPagoBean().getCodigo()) {
                    case "Efectivo":
                        switch (docEgresoBean.getTipoMonedaBean().getCodigo()) {
                            case "Soles":
                                if (montoCajaSol == null) {
                                    montoCajaSol = new Double("0.00");
                                    montoCajaSol = montoCajaSol - docEgresoBean.getMontoPagado().doubleValue();

                                    break;
                                } else {
                                    montoCajaSol = montoCajaSol - docEgresoBean.getMontoPagado().doubleValue();
                                }
                                break;

                            case "Dolares":
                                if (montoCajaDol == null) {
                                    montoCajaDol = new Double("0.00");
                                    montoCajaDol = montoCajaDol - docEgresoBean.getMontoPagado().doubleValue();
                                } else {
                                    montoCajaDol = montoCajaDol - docEgresoBean.getMontoPagado().doubleValue();
                                }
                                break;
                        }
                }
                cajaGenBean.setEgresoSol(montoCajaSol);
                cajaGenBean.setEgresoDol(montoCajaDol);
                docEgresoService.eliminarDocEgreso(docEgresoBean, cajaGenBean);
                listaDocEgresoBean = docEgresoService.obtenerDocEgresoPorFiltro(docEgresoFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerCambio() {
        Double total = 0.0;
        String tot = "";
        this.montoTotal = "";
        try {
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            docEgresoBean.setTipoCambioBean(tipoCambio);
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
//            System.out.println("id moneda " + docEgresoBean.getTipoMonedaBean().getIdCodigo());
//            System.out.println("moneda " + docEgresoBean.getTipoMonedaBean().getCodigo());
            if (docEgresoBean.getTipoMonedaBean().getIdCodigo() == null) {
                if (docEgresoBean.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.COD_Soles_Cod)) {
                    docEgresoBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
                } else {
                    docEgresoBean.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_DOLARES);
                }
            }
            cod = codigo.obtenerPorId(docEgresoBean.getTipoMonedaBean());
            docEgresoBean.setTipoMonedaBean(cod);
            getDocEgresoBean().getSolicitudCajaCHBean().setTipoMonedaBean(cod);
            System.out.println("id moneda " + docEgresoBean.getTipoMonedaBean().getIdCodigo());
            System.out.println("moneda " + docEgresoBean.getTipoMonedaBean().getCodigo());
            if (listaDocEgresoFacturaBean.size() > 0) {
                for (FacturaCompraBean detDocEggreso : getListaDocEgresoFacturaBean()) {
                    total += detDocEggreso.getMontoPago();
                }
            } else {
//                total = getDocEgresoBean().getSolicitudCajaCHBean().getMontoAprobado();
                for (SolicitudCajaCHBean soli : getListaSolicitudCajaChBean()) {
                    if (soli.getMontoPagado() == null) {
                        total += soli.getMontoAprobado();
                    } else {
                        total += soli.getMontoPagado();
                    }
                }
            }
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            DecimalFormat df = (DecimalFormat) nf;
            if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.COD_Dolares)) {
                switch (cod.getCodigo()) {
                    case "Soles":
                        montoTotalDouble = total * tipoCambio.getTcVenta().doubleValue();
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                    case "Dolares":
//                        total = total;
                        montoTotalDouble = total;
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                }

            } else {
                switch (cod.getCodigo()) {
                    case "Soles":
                        montoTotalDouble = total;
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                    case "Dolares":
                        total = total / tipoCambio.getTcVenta().doubleValue();
                        montoTotalDouble = total;
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                }
            }
            getMontoTotal();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    // métodos getter and setter
    public DocEgresoBean getDocEgresoBean() {
        if (docEgresoBean == null) {
            docEgresoBean = new DocEgresoBean();
        }
        return docEgresoBean;
    }

    public void setDocEgresoBean(DocEgresoBean docEgresoBean) {
        this.docEgresoBean = docEgresoBean;
    }

    public DocEgresoBean getDocEgresoFiltroBean() {
        if (docEgresoFiltroBean == null) {
            docEgresoFiltroBean = new DocEgresoBean();
        }
        return docEgresoFiltroBean;
    }

    public void setDocEgresoFiltroBean(DocEgresoBean docEgresoFiltroBean) {
        this.docEgresoFiltroBean = docEgresoFiltroBean;
    }

    public List<DocEgresoBean> getListaDocEgresoBean() {
        if (listaDocEgresoBean == null) {
            listaDocEgresoBean = new ArrayList<>();
        }
        return listaDocEgresoBean;
    }

    public void setListaDocEgresoBean(List<DocEgresoBean> listaDocEgresoBean) {
        this.listaDocEgresoBean = listaDocEgresoBean;
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

//    public List<CodigoBean> getListaTipoMonedaBean() {
//        if (listaTipoMonedaBean==null) {
//            listaTipoMonedaBean= new ArrayList<>();
//        }
//        return listaTipoMonedaBean;
//    }
//
//    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
//        this.listaTipoMonedaBean = listaTipoMonedaBean;
//    }
    public List<CodigoBean> getListaTipoPagoBean() {
        if (listaTipoPagoBean == null) {
            listaTipoPagoBean = new ArrayList();
        }
        return listaTipoPagoBean;
    }

    public void setListaTipoPagoBean(List<CodigoBean> listaTipoPagoBean) {
        this.listaTipoPagoBean = listaTipoPagoBean;
    }

    public List<ImpresoraBean> getListaImpresora() {
        if (listaImpresora == null) {
            listaImpresora = new ArrayList();
        }
        return listaImpresora;
    }

    public void setListaImpresora(List<ImpresoraBean> listaImpresora) {
        this.listaImpresora = listaImpresora;
    }

    public Boolean getFlgCheque() {
        return flgCheque;
    }

    public void setFlgCheque(Boolean flgCheque) {
        this.flgCheque = flgCheque;
    }

    public List<CuentaBancoBean> getListaCuentaBancoFiltroBean() {
        if (listaCuentaBancoFiltroBean == null) {
            listaCuentaBancoFiltroBean = new ArrayList();
        }
        return listaCuentaBancoFiltroBean;
    }

    public void setListaCuentaBancoFiltroBean(List<CuentaBancoBean> listaCuentaBancoFiltroBean) {
        this.listaCuentaBancoFiltroBean = listaCuentaBancoFiltroBean;
    }

    public Boolean getMostrarPanel() {
        return mostrarPanel;
    }

    public void setMostrarPanel(Boolean mostrarPanel) {
        this.mostrarPanel = mostrarPanel;
    }

    public Boolean getMostrarCheque() {
        return mostrarCheque;
    }

    public void setMostrarCheque(Boolean mostrarCheque) {
        this.mostrarCheque = mostrarCheque;
    }

    public Boolean getMostrarDetraccion() {
        return mostrarDetraccion;
    }

    public void setMostrarDetraccion(Boolean mostrarDetraccion) {
        this.mostrarDetraccion = mostrarDetraccion;
    }

    public List<DetraccionBean> getListaDetraccionBean() {
        if (listaDetraccionBean == null) {
            listaDetraccionBean = new ArrayList<>();
        }
        return listaDetraccionBean;
    }

    public void setListaDetraccionBean(List<DetraccionBean> listaDetraccionBean) {
        this.listaDetraccionBean = listaDetraccionBean;
    }

    public Double getMontoDetraccion() {
        return montoDetraccion;
    }

    public void setMontoDetraccion(Double montoDetraccion) {
        this.montoDetraccion = montoDetraccion;
    }

    public String getMontoTotal() {
        Double total = 0.0;
//        Double totalParSol = 0.0;
//        Double totalParDol = 0.0;
        String tot = "";
        String sol = "S/. ";
        try {
            TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
            Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
            tipoCambio = new TipoCambioBean();
            tipoCambio.setIdTipoMoneda(IdTipoMoneda);
            tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
            docEgresoBean.setTipoCambioBean(tipoCambio);
            CodigoService codigo = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigo.obtenerPorId(docEgresoBean.getTipoMonedaBean());
            if (listaDocEgresoFacturaBean != null) {
                if (listaDocEgresoFacturaBean.size() > 0) {
                    for (FacturaCompraBean detDocEggreso : getListaDocEgresoFacturaBean()) {
                        if (detDocEggreso.getDsctoNotCred() == null) {
                            total += detDocEggreso.getMontoPago();
                        } else {
                            total += detDocEggreso.getMontoPago() - detDocEggreso.getDsctoNotCred();
                        }
                    }// 
                } else {
//                total = getDocEgresoBean().getSolicitudCajaCHBean().getMontoAprobado();
                    for (SolicitudCajaCHBean soli : getListaSolicitudCajaChBean()) {
                        if (soli.getMontoPagado() == null) {
                            total += soli.getMontoAprobado();
                        } else {
                            total += soli.getMontoPagado();
                        }
                    }
                }
            } else {
//                total = getDocEgresoBean().getSolicitudCajaCHBean().getMontoAprobado();
                for (SolicitudCajaCHBean soli : getListaSolicitudCajaChBean()) {
                    if (soli.getMontoPagado() == null) {
                        if (soli.getMontoAprobado() == null) {
                            soli.setMontoAprobado(soli.getMonto());
                        }
                        total += soli.getMontoAprobado();
                    } else {
                        total += soli.getMontoPagado();
                    }
                }
            }
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            DecimalFormat df = (DecimalFormat) nf;
            if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo() == null) {
                if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().setCodigo(MaristaConstantes.COD_Soles_Cod);
                } else {
                    getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().setCodigo(MaristaConstantes.COD_Dolares);
                }
            }
            if (getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.COD_Dolares)) {
                switch (cod.getCodigo()) {
                    case "Soles":
                        montoTotalDouble = total * tipoCambio.getTcVenta().doubleValue();
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                    case "Dolares":
                        montoTotalDouble = total;
                        tot = df.format(total);
                        this.montoTotal = cod.getValor().concat(" ").concat(tot);
                        break;
                }
            } else {
                if (cod != null) {
                    switch (cod.getCodigo()) {
                        case "Soles":
                            montoTotalDouble = total;
                            tot = df.format(total);
                            montoTotal = cod.getValor().concat(" ").concat(tot);
                            break;
                        case "Dolares":
                            total = total / tipoCambio.getTcVenta().doubleValue();
                            montoTotalDouble = total;
                            tot = df.format(total);
                            montoTotal = cod.getValor().concat(" ").concat(tot);
                            break;
                    }
                } else {
                    montoTotalDouble = total;
                    tot = df.format(total);
                    montoTotal = sol.concat(tot);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Boolean getFlgIdTipoDoc() {
        return flgIdTipoDoc;
    }

    public void setFlgIdTipoDoc(Boolean flgIdTipoDoc) {
        this.flgIdTipoDoc = flgIdTipoDoc;
    }

    public CajeroCajaBean getCajeroCajaBean() {
        if (cajeroCajaBean == null) {
            cajeroCajaBean = new CajeroCajaBean();
        }
        return cajeroCajaBean;
    }

    public void setCajeroCajaBean(CajeroCajaBean cajeroCajaBean) {
        this.cajeroCajaBean = cajeroCajaBean;
    }

    public Boolean getFlgCajaAperturada() {
        return flgCajaAperturada;
    }

    public void setFlgCajaAperturada(Boolean flgCajaAperturada) {
        this.flgCajaAperturada = flgCajaAperturada;
    }

    public List<FacturaCompraBean> getListaDocEgresoFacturaBean() {
        if (listaDocEgresoFacturaBean == null) {
            listaDocEgresoFacturaBean = new ArrayList<>();
        }
        return listaDocEgresoFacturaBean;
    }

    public void setListaDocEgresoFacturaBean(List<FacturaCompraBean> listaDocEgresoFacturaBean) {
        this.listaDocEgresoFacturaBean = listaDocEgresoFacturaBean;
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

    public Double getMontoTotalDouble() {
        return montoTotalDouble;
    }

    public void setMontoTotalDouble(Double montoTotalDouble) {
        this.montoTotalDouble = montoTotalDouble;
    }

    public List<CodigoBean> getListaTipoMonedaBean() {
        if (listaTipoMonedaBean == null) {
            listaTipoMonedaBean = new ArrayList<>();
        }
        return listaTipoMonedaBean;
    }

    public void setListaTipoMonedaBean(List<CodigoBean> listaTipoMonedaBean) {
        this.listaTipoMonedaBean = listaTipoMonedaBean;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public Boolean getCollapse() {
        return collapse;
    }

    public void setCollapse(Boolean collapse) {
        this.collapse = collapse;
    }

    public List<DocEgresoBean> getListaDetDocEgresoBean() {
        if (listaDetDocEgresoBean == null) {
            listaDetDocEgresoBean = new ArrayList<>();
        }
        return listaDetDocEgresoBean;
    }

    public void setListaDetDocEgresoBean(List<DocEgresoBean> listaDetDocEgresoBean) {
        this.listaDetDocEgresoBean = listaDetDocEgresoBean;
    }

    public List<DocEgresoBean> getListaDocEgresoTop1Bean() {
        if (listaDocEgresoTop1Bean == null) {
            listaDocEgresoTop1Bean = new ArrayList<>();
        }
        return listaDocEgresoTop1Bean;
    }

    public void setListaDocEgresoTop1Bean(List<DocEgresoBean> listaDocEgresoTop1Bean) {
        this.listaDocEgresoTop1Bean = listaDocEgresoTop1Bean;
    }

    public Boolean getMostrarImpresora() {
        return mostrarImpresora;
    }

    public void setMostrarImpresora(Boolean mostrarImpresora) {
        this.mostrarImpresora = mostrarImpresora;
    }

    public DocEgresoBean getDocEgresoReporteBean() {
        if (docEgresoReporteBean == null) {
            docEgresoReporteBean = new DocEgresoBean();
        }
        return docEgresoReporteBean;
    }

    public void setDocEgresoReporteBean(DocEgresoBean docEgresoReporteBean) {
        this.docEgresoReporteBean = docEgresoReporteBean;
    }

    public List<DocEgresoBean> getListaDocEgresoIdsRegistroCompraBean() {
        if (listaDocEgresoIdsRegistroCompraBean == null) {
            listaDocEgresoIdsRegistroCompraBean = new ArrayList<>();
        }
        return listaDocEgresoIdsRegistroCompraBean;
    }

    public void setListaDocEgresoIdsRegistroCompraBean(List<DocEgresoBean> listaDocEgresoIdsRegistroCompraBean) {
        this.listaDocEgresoIdsRegistroCompraBean = listaDocEgresoIdsRegistroCompraBean;
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

    public ChequeBean getChequeBean() {
        if (chequeBean == null) {
            chequeBean = new ChequeBean();
        }
        return chequeBean;
    }

    public void setChequeBean(ChequeBean chequeBean) {
        this.chequeBean = chequeBean;
    }

    public List<SolicitudCajaCHBean> getListaSolicitudCajaChBean() {
        if (listaSolicitudCajaChBean == null) {
            listaSolicitudCajaChBean = new ArrayList<>();
        }
        return listaSolicitudCajaChBean;
    }

    public void setListaSolicitudCajaChBean(List<SolicitudCajaCHBean> listaSolicitudCajaChBean) {
        this.listaSolicitudCajaChBean = listaSolicitudCajaChBean;
    }

    public DocEgresoRepBean getDocEgresoRepFiltroBean() {
        if (docEgresoRepFiltroBean == null) {
            docEgresoRepFiltroBean = new DocEgresoRepBean();
        }
        return docEgresoRepFiltroBean;
    }

    public void setDocEgresoRepFiltroBean(DocEgresoRepBean docEgresoRepFiltroBean) {
        this.docEgresoRepFiltroBean = docEgresoRepFiltroBean;
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

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public Double getTotalDol() {
        return totalDol;
    }

    public void setTotalDol(Double totalDol) {
        this.totalDol = totalDol;
    }

    public Double getTotalSol() {
        return totalSol;
    }

    public void setTotalSol(Double totalSol) {
        this.totalSol = totalSol;
    }

    public Double getTolDolares() {
        return tolDolares;
    }

    public void setTolDolares(Double tolDolares) {
        this.tolDolares = tolDolares;
    }

    public Double getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(Double totSoles) {
        this.totSoles = totSoles;
    }

    public List<DocEgresoRepBean> getListaDocEgresoRepBean() {
        if (listaDocEgresoRepBean == null) {
            listaDocEgresoRepBean = new ArrayList<>();
        }
        return listaDocEgresoRepBean;
    }

    public void setListaDocEgresoRepBean(List<DocEgresoRepBean> listaDocEgresoRepBean) {
        this.listaDocEgresoRepBean = listaDocEgresoRepBean;
    }

    public PagosEmitidosRepBean getPagosEmitidosRepFiltroBean() {
        if (pagosEmitidosRepFiltroBean == null) {
            pagosEmitidosRepFiltroBean = new PagosEmitidosRepBean();
        }
        return pagosEmitidosRepFiltroBean;
    }

    public void setPagosEmitidosRepFiltroBean(PagosEmitidosRepBean pagosEmitidosRepFiltroBean) {
        this.pagosEmitidosRepFiltroBean = pagosEmitidosRepFiltroBean;
    }

    public List<PagosEmitidosRepBean> getListaPagosEmitidosRepBean() {
        if (listaPagosEmitidosRepBean == null) {
            listaPagosEmitidosRepBean = new ArrayList<>();
        }
        return listaPagosEmitidosRepBean;
    }

    public void setListaPagosEmitidosRepBean(List<PagosEmitidosRepBean> listaPagosEmitidosRepBean) {
        this.listaPagosEmitidosRepBean = listaPagosEmitidosRepBean;
    }

    public List<DetraccionBean> getListaDetraccionFiltroBean() {
        if (listaDetraccionFiltroBean == null) {
            listaDetraccionFiltroBean = new ArrayList<>();
        }
        return listaDetraccionFiltroBean;
    }

    public void setListaDetraccionFiltroBean(List<DetraccionBean> listaDetraccionFiltroBean) {
        this.listaDetraccionFiltroBean = listaDetraccionFiltroBean;
    }

    public List<CodigoBean> getListaTipoDocBean() {
        if (listaTipoDocBean == null) {
            listaTipoDocBean = new ArrayList<>();
        }
        return listaTipoDocBean;
    }

    public void setListaTipoDocBean(List<CodigoBean> listaTipoDocBean) {
        this.listaTipoDocBean = listaTipoDocBean;
    }

    public Boolean getFlgSolGeneral() {
        return flgSolGeneral;
    }

    public void setFlgSolGeneral(Boolean flgSolGeneral) {
        this.flgSolGeneral = flgSolGeneral;
    }

    public Boolean getFlgImpresionFecha() {
        return flgImpresionFecha;
    }

    public void setFlgImpresionFecha(Boolean flgImpresionFecha) {
        this.flgImpresionFecha = flgImpresionFecha;
    }

    public List<DocEgresoBean> getListaDocEgresoDetalle() {
        if (listaDocEgresoDetalle == null) {
            listaDocEgresoDetalle = new ArrayList<>();
        }
        return listaDocEgresoDetalle;
    }

    public void setListaDocEgresoDetalle(List<DocEgresoBean> listaDocEgresoDetalle) {
        this.listaDocEgresoDetalle = listaDocEgresoDetalle;
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

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public List<ChequesEmitidosLPMRepBean> getListaChequeEmitidosLPM() {
        if (listaChequeEmitidosLPM == null) {
            listaChequeEmitidosLPM = new ArrayList<>();
        }
        return listaChequeEmitidosLPM;
    }

    public void setListaChequeEmitidosLPM(List<ChequesEmitidosLPMRepBean> listaChequeEmitidosLPM) {
        this.listaChequeEmitidosLPM = listaChequeEmitidosLPM;
    }

    public List<CodigoBean> getListaTipoPagoBeanLpm() {
        if (listaTipoPagoBeanLpm==null) {
            listaTipoPagoBeanLpm= new ArrayList<>();
        }
        return listaTipoPagoBeanLpm;
    }

    public void setListaTipoPagoBeanLpm(List<CodigoBean> listaTipoPagoBeanLpm) {
        this.listaTipoPagoBeanLpm = listaTipoPagoBeanLpm;
    }

}
