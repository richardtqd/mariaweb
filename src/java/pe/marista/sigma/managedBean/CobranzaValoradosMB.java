package pe.marista.sigma.managedBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.bean.EventoTipoPaganteBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.service.CajeroService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.EventoTipoPaganteService;
import pe.marista.sigma.service.FichaService;
import pe.marista.sigma.service.PaganteService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class CobranzaValoradosMB extends BaseMB implements Serializable {

    @PostConstruct
    public void CobranzaValoradosMB() {
        try {
            //USUARIO
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            EventoService eventoService = BeanFactory.getEventoService();
            getListaEventoBean();
            getEventoBean();
            getEventoTipoPaganteBean();
            getPaganteBean();
            getFichaBean();

            getPaganteBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getEventoBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getEventoTipoPaganteBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            listaEventoBean = eventoService.obtener(getEventoBean());
            autenticarCajero();
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaMoneda();
            listaMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));

            getListaModoPago();
            listaModoPago = codigoService.obtenerCodigoDocIngreso();

            obtenerTipoCambio();
            getFichaBean().getTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
            getFichaBean().getTipoModoPago().setIdCodigo(MaristaConstantes.COD_EFEC);
            getFichaEspBean();
            fichaEspBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            //OBTENIENDO FILTRO ESPECIAL
            setTipoFiltro(1);
            obtenerBusquedaEspecial();
            getFichaEspecialBean();
            getFichaEspecialBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            /* TIPO DE ASIGNACION */
            setTipAsignacion(0);

            /* INGRESO DE PERSONA NUEVA */
            getPersonaBean();
            getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

//            /* OBTENIENDO EVENTO POR DEFECTO */
//            List<EventoBean> listaEventoDefecto = new ArrayList<>();
//            EventoBean eventoBean = new EventoBean();
//            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            listaEventoDefecto = eventoService.obtenerEventoDefecto(eventoBean);
//            if (listaEventoDefecto.size() == 1) {
//                paganteBean.getEventoBean().setIdEvento(listaEventoDefecto.get(0).getIdEvento());
//            } else if (listaEventoDefecto.size() > 1) {
//                paganteBean.getEventoBean().setIdEvento(null);
//            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CLASES
    private UsuarioBean usuarioLoginBean;
    private PaganteBean paganteBean;
    private PaganteBean paganteView;
    private EventoBean eventoBean;
    private EventoTipoPaganteBean eventoTipoPaganteBean;
    private FichaBean fichaBean;
    private CajaGenBean cajaGenBean;
    private ImpresoraCajaBean impresoraCajaBean;
    private TipoCambioBean tipoCambio;
    private PersonaBean personaVista;
    private PersonaBean personaBean;
    private PaganteBean paganteNewBean;
    private FichaBean fichaEspecialBean;

    //LISTAS DE CLASES
    private List<PaganteBean> listaPaganteBean;
    private List<EventoBean> listaEventoBean;
    private List<EventoTipoPaganteBean> listaEventoTipoPaganteBean;
    private List<ImpresoraBean> listaImpresora;
    private List<CodigoBean> listaTipoDocumento;
    private List<FichaBean> listaFichaAfterBean;
    private List<FichaBean> listaFichaPagoBean;
    private List<CodigoBean> listaMoneda;
    private List<CodigoBean> listaModoPago;

    //METODOS DE AYUDA
    private Integer tipPagante;
    private String numActual;
    private Boolean flgPagoParDol;
    private Double montoTotalDouble;
    private String montoTotal;
    private Boolean renderBotonSave;
    private Boolean disabledGuardar;
    private Boolean renderImprimir = true;
    private Boolean flgModPOS;
    private Boolean posVisa;
    private Boolean posMC;
    private Boolean flgModPagoAmbos;
    private Boolean flgGenCod;
    private Integer tipAsignacion;
    private Boolean flgRenderEsp;
    private Integer nroAsig;
    private Integer nroIni;
    private Integer nroFin;
    private Integer tipoFiltro;
    private Boolean disFil;
    private String estadoFicha;

    //CONSULTA ESPECIAL SANJOC
    private FichaBean fichaEspBean;
    private List<FichaBean> listaFichaEspBean;

    private FichaBean fichaInsert;

    //METODOS DE CLASES
    public void buscarPagante() {
        try {

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTipoPagante() {
        try {
            EventoTipoPaganteService eventoTipoPaganteService = BeanFactory.getEventoTipoPaganteService();
            eventoTipoPaganteBean.getEventoBean().setIdEvento(paganteBean.getEventoBean().getIdEvento());
            listaEventoTipoPaganteBean = eventoTipoPaganteService.obtenerPorEvento(eventoTipoPaganteBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //FILTRO DE PAGANTES
    public void filtrarPaganteMasivo() {
        try {
            PaganteService paganteService = BeanFactory.getPaganteService();
            Integer res = 0;
            if (getPaganteBean().getNroDoc() != null && !getPaganteBean().getNroDoc().equals("")) {
                getPaganteBean().setNroDoc(getPaganteBean().getNroDoc());
                res = 1;
            }
            if (getPaganteBean().getNomPagante() != null && !getPaganteBean().getNomPagante().equals("")) {
                getPaganteBean().setNomPagante(getPaganteBean().getNomPagante());
                res = 1;
            }
            if (getPaganteBean().getEventoBean().getIdEvento() != null) {
                getPaganteBean().getEventoBean().setIdEvento(getPaganteBean().getEventoBean().getIdEvento());
                res = 1;
            }
            if (getPaganteBean().getNroFicha() != null) {
                getPaganteBean().setNroFicha(getPaganteBean().getNroFicha());
                getFichaEspecialBean().setNroficha(getPaganteBean().getNroFicha());

                /* OBTENIENDO ESTADO DE FICHA */
                FichaBean ficha = new FichaBean();
                FichaService fichaService = BeanFactory.getFichaService();
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                ficha.setNroficha(fichaEspecialBean.getNroficha());
                ficha = fichaService.obtenerPorNroFichaEspecial(ficha);
                if (ficha != null) {
                    estadoFicha = ficha.getTipoStatusFicha().getCodigo();
                }
                res = 1;
            }
            if (res == 1) {
                if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante() != null) {
                    if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                        listaPaganteBean = paganteService.filtrarPaganteEst(getPaganteBean());
                    } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(2)) {
                        listaPaganteBean = paganteService.filtrarPagantePer(getPaganteBean());
                    } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(3)) {
                        listaPaganteBean = paganteService.filtrarPaganteExt(getPaganteBean());
                    } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(4)) {
                        listaPaganteBean = paganteService.filtrarPaganteEnt(getPaganteBean());
                    }
                } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante() == null) {
                    listaPaganteBean = paganteService.filtrarPaganteObj(getPaganteBean());
                }
                if (listaPaganteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPaganteBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            }
            if (getTipoFiltro().equals(0)) {
                /* OBTENIENDO ESTADO DE FICHA */
                if (!listaPaganteBean.isEmpty()) {
                    FichaService fichaService = BeanFactory.getFichaService();
                    FichaBean ficha = new FichaBean();
                    paganteView = listaPaganteBean.get(0);
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                    ficha.setNroficha(fichaEspecialBean.getNroficha());
                    fichaEspecialBean = fichaService.obtenerPorNroFichaEspecial(ficha);
                    if (fichaEspecialBean != null) {
                        estadoFicha = fichaEspecialBean.getTipoStatusFicha().getCodigo();
                        if (fichaEspecialBean.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_PAG)) {
//                    RequestContext.getCurrentInstance().addCallbackParam("pagada", true);
                        } else if (fichaEspecialBean.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_PEN)) {
                            RequestContext.getCurrentInstance().addCallbackParam("especial", true);
                        }
                    } else if (fichaEspecialBean == null) {
                        new MensajePrime().addMessageNumberNotExist();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarFiltroPagante() {
        try {
            paganteBean = new PaganteBean();
            listaPaganteBean = new ArrayList<>();
            paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerPorIdPagante(Object object) {
        try {
            PaganteBean pagante = (PaganteBean) object;
            FichaBean ficha = new FichaBean();
            PaganteService paganteService = BeanFactory.getPaganteService();
            FichaService fichaService = BeanFactory.getFichaService();
            if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante() != null) {
                if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                    pagante = paganteService.obtenerPorIdPagEst(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getMatriculaBean().setIdMatricula(pagante.getMatriculaBean().getIdMatricula());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEst(ficha);
                } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(2)) {
                    pagante = paganteService.obtenerPorIdPagPer(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getPersonalBean().setIdPersonal(pagante.getPersonalBean().getIdPersonal());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPagantePer(ficha);
                } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(3)) {
                    pagante = paganteService.obtenerPorIdPagExt(pagante);
                    paganteView = pagante;
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    ficha.getPaganteBean().getPersonaBean().setIdPersona(pagante.getIdPagante());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteExt(ficha);
                } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(4)) {
                    pagante = paganteService.obtenerPorIdPagEnt(pagante);
                    paganteView = pagante;
                    System.out.println(">>>>" + pagante.getIdPagante());
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//                    ficha.getPaganteBean().getEntidadBean().setRuc(pagante.getIdPagante());
                    ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                    ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                    listaFichaAfterBean = fichaService.obtenerFichaPorPaganteEnt(ficha);
                }
            } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante() == null) {
                pagante = paganteService.obtenerPorId(pagante);
                System.out.println(">>>>" + pagante.getIdPagante());
                System.out.println(">>>>" + pagante.getTipoPaganteBean().getIdtipoPagante());
                paganteView = pagante;
                ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                ficha.getPaganteBean().setIdPagante(pagante.getIdPagante());
                ficha.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(pagante.getTipoPaganteBean().getIdtipoPagante());
                listaFichaAfterBean = fichaService.obtenerFichaPorPaganteObj(ficha);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarFicha(Object object, Integer valor) {
        try {
            //1 AGREGAR POR UNIDAD
            //2 AGREGAR TODAS LAS ASIGNACIONES POR PAGANTE
            Integer por = 0;
            Double monto = Double.parseDouble(por.toString());
            if (valor.equals(1)) {
                FichaBean ficha = (FichaBean) object;
                System.out.println(">>>>>" + listaFichaPagoBean.contains(ficha));
                if (!listaFichaPagoBean.contains(ficha)) {
                    if (!ficha.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_DON)) {

                    }
                    ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaFichaPagoBean.add(ficha);
                    listaFichaPagoBean.contains(ficha);
                    if (!listaFichaPagoBean.isEmpty()) {
                        for (FichaBean fichaPago : listaFichaPagoBean) {
                            monto = fichaPago.getMonto().doubleValue() + monto;
                        }
                    }
                    getFichaBean().setMontoEfectivoSol(monto);
                    getRenderBotonSave();
                    setRenderBotonSave(true);
                    setRenderImprimir(true);
                    setPaganteView(paganteView);
                    setMontoTotal(monto.toString());
                    setFlgPagoParDol(false);
                }

            } else if (valor.equals(2)) {
                if (!listaFichaAfterBean.isEmpty()) {
                    if (listaFichaPagoBean.isEmpty()) {
                        for (FichaBean ficha : listaFichaAfterBean) {
                            if (ficha.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_PEN)) {
                                listaFichaPagoBean.add(ficha);
                            }
                        }
                        if (!listaFichaPagoBean.isEmpty()) {
                            for (FichaBean fichaPago : listaFichaPagoBean) {
                                monto = fichaPago.getMonto().doubleValue() + monto;
                            }
                        }
                    } else if (!listaFichaPagoBean.isEmpty()) {
                        listaFichaPagoBean.clear();
                        for (FichaBean ficha : listaFichaAfterBean) {
                            if (ficha.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_PEN)) {
                                listaFichaPagoBean.add(ficha);
                            }
                        }
                        if (!listaFichaPagoBean.isEmpty()) {
                            for (FichaBean fichaPago : listaFichaPagoBean) {
                                monto = fichaPago.getMonto().doubleValue() + monto;
                            }
                        }
                    }
                    getFichaBean().setMontoEfectivoSol(monto);
                    setPaganteView(paganteView);
                    setMontoTotal(monto.toString());
                    getRenderBotonSave();
                    setRenderBotonSave(true);
                    setRenderImprimir(true);
                    setFlgPagoParDol(false);
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void quitarAsignacion(Object object) {
        try {
            Integer por = 0;
            Double monto = Double.parseDouble(por.toString());
            FichaBean ficha = (FichaBean) object;
            listaFichaPagoBean.remove(ficha);
            if (!listaFichaPagoBean.isEmpty()) {
                for (FichaBean fichaPago : listaFichaPagoBean) {
                    monto = fichaPago.getMonto().doubleValue() + monto;
                }
            } else if (listaFichaPagoBean.isEmpty()) {
                monto = 0.0;
            }
            setMontoTotal(monto.toString());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void grabarAsignacion() {
        try {
            Calendar fechaActual = new GregorianCalendar();
            FichaService fichaService = BeanFactory.getFichaService();
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroDoc(Integer.parseInt(numActual));
//            Integer nro = fichaService.obtenerNroDocFicha(ficha);
//            if (nro == null || nro == 0) {
//            } else if (nro != null || nro > 0) {
//                RequestContext.getCurrentInstance().addCallbackParam("numeroIgual", true);
//                obtenerSiguienteNumero();
//            }

//            if (nro == null || nro == 0) {
                if (!listaFichaPagoBean.isEmpty()) {
                    for (FichaBean fichaPago : listaFichaPagoBean) {
                        /* MODIFICANDO FICHAS */
                        fichaPago.getTipoModoPago().setIdCodigo(fichaBean.getTipoModoPago().getIdCodigo());
                        fichaPago.getTipoMoneda().setIdCodigo(fichaBean.getTipoMoneda().getIdCodigo());
                        fichaPago.getCajaBean().setIdCaja(cajaGenBean.getCajaBean().getIdCaja());
                        fichaPago.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        fichaPago.setModiPor(usuarioLoginBean.getUsuario());
                        if (!fichaPago.getTipoStatusFicha().getIdCodigo().equals(MaristaConstantes.STATUS_FICHA_DON)) {
                            fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PAG);
                        }
                        fichaPago.getCajaGenBean().setIdCajaGen(cajaGenBean.getIdCajaGen());
                        fichaPago.setMontoPagado(fichaPago.getMonto());
                        fichaPago.setFechaPago(fechaActual.getTime());
                        fichaPago.setSerie(impresoraCajaBean.getImpresora().getSerie());
                        fichaPago.setNroDoc(Integer.parseInt(numActual));
                        fichaService.modificarFichaPagada(fichaPago);
                        /* EXEC ASIENTO */
                        procesoFinalService.execProAsiento(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), "MT_Ficha", fichaPago.getIdFicha(), usuarioLoginBean.getUsuario(), null);
                    }
                    //MODIFICAR CAJA GEN
                    modificarCajaGenApafa();
                    modificarNroImpresora();
                    autenticarCajero();
                    disabledGuardar = true;
                    renderImprimir = false;
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
//            } else if (nro != null || nro > 0) {
//                RequestContext.getCurrentInstance().addCallbackParam("numeroIgual", true);
//                obtenerSiguienteNumero();
//            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerSiguienteNumero() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean fichaBean = new FichaBean();
            fichaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            Integer numero = 0;
            numero = fichaService.obtenerMaxNroDocFicha(fichaBean) + 1;
//            numero = Integer.parseInt(numActual) + 1;
            System.out.println(">>>" + numero);
            numActual = String.format("%07d", numero);
//            numActual = numero.toString();
            impresoraCajaBean.getImpresora().setActual(numero);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void grabarAsiento() {
        try {

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void modificarCajaGenApafa() {
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            BigDecimal tc = tipoCambio.getTcVenta();
            BigDecimal monto = null;
            if (fichaBean.getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                if (fichaBean.getTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                    BigDecimal ingreso = new BigDecimal(Double.parseDouble(montoTotal));
                    monto = ingreso.divide(tc, 2, RoundingMode.HALF_UP);
                    if (posVisa) {
                        cajaGenBean.setIngresoPos1(monto.doubleValue());
                    } else if (posMC) {
                        cajaGenBean.setIngresoPos2(monto.doubleValue());
                    }
                } else if (fichaBean.getTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    if (posVisa) {
                        cajaGenBean.setIngresoPos1(Double.parseDouble(montoTotal));
                    } else if (posMC) {
                        cajaGenBean.setIngresoPos2(Double.parseDouble(montoTotal));
                    }
                }
            } else if (fichaBean.getTipoModoPago().getIdCodigo().equals(MaristaConstantes.COD_EFEC)) {
                if (fichaBean.getTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_DOLARES)) {
                    BigDecimal ingreso = new BigDecimal(Double.parseDouble(montoTotal));
                    monto = ingreso.divide(tc, 2, RoundingMode.HALF_UP);
                    cajaGenBean.setIngresoDol(monto.doubleValue());
                } else if (fichaBean.getTipoMoneda().getIdCodigo().equals(MaristaConstantes.COD_SOLES)) {
                    cajaGenBean.setIngresoSol(Double.parseDouble(montoTotal));
                }
            }
            cajaGenBean.setFlgTipoCajaGen(Boolean.FALSE);
            cajaGenService.modificarIngresoSolYDolEvento(cajaGenBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void modificarNroImpresora() {
        try {
            ImpresoraBean impresoraBean = new ImpresoraBean();
            impresoraBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            impresoraBean.setImpresora(fichaBean.getImpresoraCajaBean().getImpresora().getImpresora());
            impresoraBean.getIdTipoDoc().setIdCodigo(fichaBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().getIdCodigo());
            impresoraBean.setActual(Integer.parseInt(numActual) + 1);
            FichaService fichaService = BeanFactory.getFichaService();
            fichaService.modificarImpresoraActual(impresoraBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarAsignacion() {
        try {
            paganteView = new PaganteBean();
            listaFichaPagoBean = new ArrayList<>();
            montoTotal = "0";
            disabledGuardar = false;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void resetearMontoDol() {
        try {
            fichaBean.setMontoEfectivoSol((0.0));
            fichaBean.setMontoEfectivoDol((0.0));
            fichaBean.setMontoPos1((0.0));
            fichaBean.setMontoPos2((0.0));
            if (fichaBean.getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                setFlgModPOS(true);
            } else {
                setFlgModPOS(false);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirFormatoPdf() {
        try {

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void montosPOS() {
        try {
            if (!getFichaBean().getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                Double montoEfe = 0.0;
                if (this.posVisa.equals(true) && this.posMC.equals(false)) {
                    if (getFichaBean().getMontoEfectivoSol() != null) {
                        if (getFichaBean().getMontoEfectivoSol() > 0) {
                            montoEfe = getFichaBean().getMontoEfectivoSol();
                        }
                    }
                    getFichaBean().setMontoPos1(montoTotalDouble - montoEfe);
                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    if (getFichaBean().getMontoEfectivoSol() != null) {
                        if (getFichaBean().getMontoEfectivoSol() > 0) {
                            montoEfe = getFichaBean().getMontoEfectivoSol();
                        }
                    }
                    getFichaBean().setMontoPos2(montoTotalDouble - montoEfe);
                } else {
                    getFichaBean().setMontoPos1(0.0);
                    getFichaBean().setMontoPos2(0.0);
                }
            } else {
                if (this.posVisa.equals(true) && this.posMC.equals(false)) {
                    getFichaBean().setMontoPos1(montoTotalDouble - getFichaBean().getMontoEfectivoSol());
                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    getFichaBean().setMontoPos2(montoTotalDouble - getFichaBean().getMontoEfectivoSol());
                } else {
                    getFichaBean().setMontoPos1(0.0);
                    getFichaBean().setMontoPos2(0.0);
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
                if (getFichaBean().getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS)) {
                    montoEfe = getFichaBean().getMontoEfectivoSol();
                }
                if (getFichaBean().getMontoPos1() > 0 && getFichaBean().getMontoPos1() <= montoTotalDouble - montoEfe) {
                    Double total1 = 0.0d;
                    total1 = (montoTotalDouble - montoEfe) - getFichaBean().getMontoPos1();
                    double rounded = (double) Math.round(total1 * 100) / 100;
                    getFichaBean().setMontoPos2(rounded);
                } else {
                    getFichaBean().setMontoPos2(0.0);
                }
                if (getFichaBean().getMontoPos2() > 0 && getFichaBean().getMontoPos1() <= montoTotalDouble - montoEfe) {
                    Double total2 = 0.0d;
                    total2 = (montoTotalDouble - montoEfe) - getFichaBean().getMontoPos2();
                    double rounded = (double) Math.round(total2 * 100) / 100;
                    getFichaBean().setMontoPos1(rounded);
                } else {
                    getFichaBean().setMontoPos1(0.0);
                }
            } else {
                if (this.posVisa.equals(true) && this.posMC.equals(false) && getFichaBean().getTipoModoPago().getCodigo().equals(MaristaConstantes.COD_POS)) {
                    getFichaBean().setMontoPos2(0.0);

                } else if (this.posVisa.equals(false) && this.posMC.equals(true)) {
                    getFichaBean().setMontoPos1(0.0);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void mostrarModAmbos() {
        try {
            resetearMontoDol();
            if (fichaBean.getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_EFE_POS) || fichaBean.getTipoMoneda().getCodigo().equals(MaristaConstantes.COD_SOL_DOL)) {
                this.flgModPagoAmbos = true;
            } else {
                this.flgModPagoAmbos = false;
            }
            if (fichaBean.getTipoModoPago().getIdCodigo().equals(MaristaConstantes.CODIGO_POS)) {
                this.flgModPOS = true;
                this.flgPagoParDol = false;
            } else {
                this.flgModPOS = false;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    /* METODOS DE FICHAS */
    /* PERSONA PAGANTE */
    public void prueba() {
        System.out.println("Esto es una prueba");
    }

    public void comprobarPersonaFicha(String tipo) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            personaVista = personaService.obtenerPersPorId(personaBean);
            if (personaVista != null) {
                RequestContext.getCurrentInstance().addCallbackParam("error", true);
            } else if (personaVista == null) {
                if (personaBean.getIdPersonaOld() == null) {
                    insertarPersonaFicha(tipo);
                } else {
                    modificarPersonaFicha(tipo);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarPersonaFicha(String tipo) {
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
//                    if (tipo != null) {
//                        DocIngresoMB docIngresoMB = (DocIngresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("docIngresoMB");
//                        docIngresoMB.setPersonaBean(personaBean);
//                        docIngresoMB.rowSelectObject(personaBean);
//                    }
                    PersonaBean persona = new PersonaBean();
                    persona = personaService.obtenerPersPorId(personaBean);
                    PaganteService paganteService = BeanFactory.getPaganteService();
                    PaganteBean pagante = new PaganteBean();
                    pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    pagante.setIdPagante(persona.getIdPersona());
                    pagante.setNomPagante(persona.getNombreCompleto());
                    pagante.getEventoBean().setIdEvento(paganteNewBean.getEventoBean().getIdEvento());
                    pagante.getTipoPaganteBean().setIdtipoPagante(3);
                    pagante.setCreaPor(usuarioLoginBean.getUsuario());
                    paganteService.insertar(pagante);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    limpiarPersonaFicha();
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

    public String modificarPersonaFicha(String tipo) {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                personaBean.setModiPor(usuarioBean.getUsuario());
                PersonaService personaService = BeanFactory.getPersonaService();
                personaService.modificarPersona(personaBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
//                if (tipo != null && !tipo.equals("")) {
//                    DocIngresoMB docIngresoMB = (DocIngresoMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("DocIngresoMB");
//                    docIngresoMB.setPersonaBean(personaBean);
//                    docIngresoMB.rowSelectObject(personaBean);
//                }
                PersonaBean persona = new PersonaBean();
                persona = personaService.obtenerPersPorId(personaBean);
                PaganteService paganteService = BeanFactory.getPaganteService();
                PaganteBean pagante = new PaganteBean();
                pagante.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                pagante.setIdPagante(persona.getIdPersona());
                pagante.setNomPagante(persona.getNombreCompleto());
                pagante.getEventoBean().setIdEvento(paganteNewBean.getEventoBean().getIdEvento());
                pagante.setModiPor(usuarioLoginBean.getUsuario());
                paganteService.actualizar(pagante);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarPersonaFicha();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void limpiarPersonaFicha() {
        try {
            personaBean = new PersonaBean();
            this.flgGenCod = false;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerNroAsigna() {
        try {
            /* OBTENIENDO CONCEPTO */
            ConceptoUniNegBean conceptoUniNegBean = new ConceptoUniNegBean();
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            conceptoUniNegBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            conceptoUniNegBean.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
            conceptoUniNegBean = conceptoUniNegService.obtenerConceptoPorId(conceptoUniNegBean);

            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroficha(nroIni);
            Integer numero = fichaService.obtenerNroFicha(ficha);
            if (paganteView.getTipoPaganteBean().getIdtipoPagante() != null) {
                if (tipAsignacion != null) {
                    if (numero == null) {
                        FichaBean fichaPago = new FichaBean();
                        //INSERTANDO FICHA
                        fichaPago.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        fichaPago.getTipoDoc().setIdCodigo(MaristaConstantes.COD_DOC_REC);
                        fichaPago.setSerie(MaristaConstantes.serie_numdoc);
                        fichaPago.setNroficha(nroIni);
                        if (tipAsignacion.equals(1)) {
                            fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_DON);
                        } else if (tipAsignacion.equals(0)) {
                            fichaPago.getTipoStatusFicha().setIdCodigo(MaristaConstantes.STATUS_FICHA_PEN);
                        }
                        fichaPago.setMonto(conceptoUniNegBean.getImporte());
                        fichaPago.getConceptoBean().setIdConcepto(MaristaConstantes.CON_VALORADOS);
                        fichaPago.setIdTipoPagante(paganteView.getTipoPaganteBean().getIdtipoPagante());
                        fichaPago.getPaganteBean().setIdPagante(paganteView.getIdPagante());
                        fichaPago.setReferencia("COLABORACIÓN DEL NÚMERO " + nroIni.toString());
                        fichaPago.setCreaPor(usuarioLoginBean.getUsuario());
                        fichaPago.setFlgAdicional(1);
                        fichaService.insertar(fichaPago);
                        obtenerMaxIdFicha();
                        setPaganteView(paganteView);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                        nroIni = 0;
                        nroFin = 0;
                        tipAsignacion = null;
                        setFlgPagoParDol(false);
                    } else if (numero != null) {
                        new MensajePrime().addMessageNumber();
                    }
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void asignarFichasEsp(Object object) {
        try {
            PaganteBean pagante = (PaganteBean) object;
            paganteView = pagante;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerMaxIdFicha() {
        try {
            Integer por = 0;
            Double monto = Double.parseDouble(por.toString());
            FichaService fichaService = BeanFactory.getFichaService();
            FichaBean ficha = new FichaBean();
            ficha.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ficha.setNroficha(nroIni);
            Integer idFicha = fichaService.obtenerIdNroFicha(ficha);
            FichaBean fichaBean = new FichaBean();
            fichaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fichaBean.setIdFicha(idFicha);
            List<FichaBean> listaFichaBean = fichaService.obtenerPorId(fichaBean);
            listaFichaPagoBean.add(listaFichaBean.get(0));
            if (!listaFichaPagoBean.isEmpty()) {
                for (FichaBean fichaPago : listaFichaPagoBean) {
                    monto = fichaPago.getMonto().doubleValue() + monto;
                }
            }
            setMontoTotal(monto.toString());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void generarCodigoPersona() {
        try {
            if (flgGenCod.equals(true)) {
                PersonaService personaService = BeanFactory.getPersonaService();
                String cod = null;
                cod = personaService.generarCodigoPersona(2016, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
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

    //VALIDAR CAJERO
    public String autenticarCajero() {
        String pagina = null;
        try {
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            CajeroCajaBean cajeroCajaBean = new CajeroCajaBean();
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            CajeroService cajeroService = BeanFactory.getCajeroService();
            FichaService fichaService = BeanFactory.getFichaService();
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
//                getCajaGenBean().getTipoCajaGen().setIdCodigo(MaristaConstantes.COD_CAJA_APAFA);
                getCajaGenBean().setFlgTipoCajaGen(Boolean.FALSE);
                CajaGenBean cajaGeneral = new CajaGenBean();
                cajaGeneral = cajaGenService.verificarAperturaCajaEvento(cajaGenBean);
                if (cajaGeneral != null) {
//                    System.out.println("idCajaGen: " + cajaGeneral.getIdCajaGen());
                    cajaGenBean = cajaGeneral;
                    System.out.println(">>>>" + cajaGenBean.getIdCajaGen());
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
                    listaImpresora = fichaService.obtenerImpresoraCajero(cajeroCajaBean);
                    System.out.println("size" + listaImpresora.size());
                    if (listaImpresora.size() == 1) {
                        getFichaBean().getImpresoraCajaBean().getImpresora().setImpresora(listaImpresora.get(0).getImpresora());
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

    public void obtenerTipDoc() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoDocumento = new ArrayList<>();
            listaTipoDocumento = fichaService.obtenerTipDocumentoPorImpresora(fichaBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuarioLoginBean.getUsuario(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cajaGenBean.getCajaBean().getIdCaja());
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
                fichaBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((codigoTipoDoc));
//                obtenerDetalleDoc();
            } else {
                if (listaImpresora.size() == 1) {
                    if (!listaTipoDocumento.isEmpty()) {
                        fichaBean.getImpresoraCajaBean().getIdTipoDoc().setIdTipoDoc((listaTipoDocumento.get(0)));
                    }
                }
            }
            obtenerDetalleDoc();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerDetalleDoc() {
        try {
            FichaService fichaService = BeanFactory.getFichaService();
            UsuarioBean usuario = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            impresoraCajaBean = new ImpresoraCajaBean();
            impresoraCajaBean = fichaService.obtenerDetalleTipoDoc(fichaBean.getImpresoraCajaBean().getImpresora().getImpresora(), usuario.getUsuario(), usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fichaBean.getImpresoraCajaBean().getIdTipoDoc().getIdTipoDoc().getIdCodigo(), cajaGenBean.getCajaBean().getIdCaja());
            if (impresoraCajaBean != null) {
                numActual = String.format("%07d", impresoraCajaBean.getImpresora().getActual());
                fichaBean.setImpresoraCajaBean(impresoraCajaBean);
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
                getFichaBean().setTipoCambioBean(tipoCambio);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    /* COMPROBANTE DE VALORADOS */
    public void generarComprobanteValorado() {
        ServletOutputStream out = null;
        try {
            if (!listaFichaPagoBean.isEmpty()) {
                List<Integer> listaIds = new ArrayList<>();
                for (FichaBean ficha : listaFichaPagoBean) {
                    listaIds.add(ficha.getIdFicha());
                }
                List<CobranzaValoradoRepBean> listaCobranzaValoradoRepBean = new ArrayList<>();
                FichaService fichaService = BeanFactory.getFichaService();
                listaCobranzaValoradoRepBean = fichaService.generarReciboValorado(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
                for (CobranzaValoradoRepBean cob : listaCobranzaValoradoRepBean) {
                    cob.setMontoTotal(montoTotal);
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepCobranzaValorado.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCobranzaValoradoRepBean);
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

    //PDF AUTOMATICO
    public void generaComprobanteAutomatico() {
        ServletOutputStream out = null;
        try {
            if (!listaFichaPagoBean.isEmpty()) {
                List<Integer> listaIds = new ArrayList<>();
                for (FichaBean ficha : listaFichaPagoBean) {
                    listaIds.add(ficha.getIdFicha());
                }
                List<CobranzaValoradoRepBean> listaCobranzaValoradoRepBean = new ArrayList<>();
                FichaService fichaService = BeanFactory.getFichaService();
                listaCobranzaValoradoRepBean = fichaService.generarReciboValorado(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
                for (CobranzaValoradoRepBean cob : listaCobranzaValoradoRepBean) {
                    cob.setMontoTotal(montoTotal);
                }
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepCobranzaValorado.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCobranzaValoradoRepBean);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);

                ByteArrayOutputStream salida = new ByteArrayOutputStream();
                JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);

                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, salida);
                exporter.exportReport();

//                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                ByteArrayInputStream input = new ByteArrayInputStream(salida.toByteArray());

//                response.reset();
//                response.setContentType("application/pdf");
//                response.setContentLength(bytes.length);
//                out = response.getOutputStream();
//                out.write(bytes);
//                out.flush();
            }
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
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

    //CONSULTA ESPECIAL SANJOC
    public void obtenerFichaEspecial() {
        try {
            Integer res = 0;
            FichaService fichaService = BeanFactory.getFichaService();
            if (getFichaEspBean().getNroficha() != null) {
                getFichaEspBean().setNroficha(getFichaEspBean().getNroficha());
                res = 1;
            }
            listaFichaEspBean = fichaService.obtenerPorNroFicha(getFichaEspBean());
            if (listaFichaEspBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaFichaEspBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerBusquedaEspecial() {
        try {
            if (getTipoFiltro() != null) {
                if (getTipoFiltro().equals(1)) {
                    setDisFil(false);
                } else if (getTipoFiltro().equals(0)) {
                    setDisFil(true);
                    paganteBean = new PaganteBean();
                    paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void agregarFichaCompra() {
        try {
            Integer por = 0;
            Double monto = Double.parseDouble(por.toString());
            System.out.println(">>>>>" + listaFichaPagoBean.contains(fichaEspecialBean));
            if (!listaFichaPagoBean.contains(fichaEspecialBean)) {
                fichaEspecialBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                listaFichaPagoBean.add(fichaEspecialBean);
                listaFichaPagoBean.contains(fichaEspecialBean);
                if (!listaFichaPagoBean.isEmpty()) {
                    for (FichaBean fichaPago : listaFichaPagoBean) {
                        monto = fichaPago.getMonto().doubleValue() + monto;
                    }
                }
                getFichaBean().setMontoEfectivoSol(monto);
                getRenderBotonSave();
                setRenderBotonSave(true);
                setRenderImprimir(true);
                setPaganteView(paganteView);
                setMontoTotal(monto.toString());
                setFlgPagoParDol(false);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public Integer getTipPagante() {
        return tipPagante;
    }

    public void setTipPagante(Integer tipPagante) {
        this.tipPagante = tipPagante;
    }

    public PaganteBean getPaganteBean() {
        if (paganteBean == null) {
            paganteBean = new PaganteBean();
        }
        return paganteBean;
    }

    public void setPaganteBean(PaganteBean paganteBean) {
        this.paganteBean = paganteBean;
    }

    public List<PaganteBean> getListaPaganteBean() {
        if (listaPaganteBean == null) {
            listaPaganteBean = new ArrayList<>();
        }
        return listaPaganteBean;
    }

    public void setListaPaganteBean(List<PaganteBean> listaPaganteBean) {
        this.listaPaganteBean = listaPaganteBean;
    }

    public List<EventoBean> getListaEventoBean() {
        if (listaEventoBean == null) {
            listaEventoBean = new ArrayList<>();
        }
        return listaEventoBean;
    }

    public void setListaEventoBean(List<EventoBean> listaEventoBean) {
        this.listaEventoBean = listaEventoBean;
    }

    public List<EventoTipoPaganteBean> getListaEventoTipoPaganteBean() {
        if (listaEventoTipoPaganteBean == null) {
            listaEventoTipoPaganteBean = new ArrayList<>();
        }
        return listaEventoTipoPaganteBean;
    }

    public void setListaEventoTipoPaganteBean(List<EventoTipoPaganteBean> listaEventoTipoPaganteBean) {
        this.listaEventoTipoPaganteBean = listaEventoTipoPaganteBean;
    }

    public EventoBean getEventoBean() {
        if (eventoBean == null) {
            eventoBean = new EventoBean();
        }
        return eventoBean;
    }

    public void setEventoBean(EventoBean eventoBean) {
        this.eventoBean = eventoBean;
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

    public EventoTipoPaganteBean getEventoTipoPaganteBean() {
        if (eventoTipoPaganteBean == null) {
            eventoTipoPaganteBean = new EventoTipoPaganteBean();
        }
        return eventoTipoPaganteBean;
    }

    public void setEventoTipoPaganteBean(EventoTipoPaganteBean eventoTipoPaganteBean) {
        this.eventoTipoPaganteBean = eventoTipoPaganteBean;
    }

    public FichaBean getFichaBean() {
        if (fichaBean == null) {
            fichaBean = new FichaBean();
        }
        return fichaBean;
    }

    public void setFichaBean(FichaBean fichaBean) {
        this.fichaBean = fichaBean;
    }

    public List<ImpresoraBean> getListaImpresora() {
        if (listaImpresora == null) {
            listaImpresora = new ArrayList<>();
        }
        return listaImpresora;
    }

    public void setListaImpresora(List<ImpresoraBean> listaImpresora) {
        this.listaImpresora = listaImpresora;
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

    public List<CodigoBean> getListaTipoDocumento() {
        if (listaTipoDocumento == null) {
            listaTipoDocumento = new ArrayList<>();
        }
        return listaTipoDocumento;
    }

    public void setListaTipoDocumento(List<CodigoBean> listaTipoDocumento) {
        this.listaTipoDocumento = listaTipoDocumento;
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

    public String getNumActual() {
        return numActual;
    }

    public void setNumActual(String numActual) {
        this.numActual = numActual;
    }

    public PaganteBean getPaganteView() {
        if (paganteView == null) {
            paganteView = new PaganteBean();
        }
        return paganteView;
    }

    public void setPaganteView(PaganteBean paganteView) {
        this.paganteView = paganteView;
    }

    public List<FichaBean> getListaFichaAfterBean() {
        if (listaFichaAfterBean == null) {
            listaFichaAfterBean = new ArrayList<>();
        }
        return listaFichaAfterBean;
    }

    public void setListaFichaAfterBean(List<FichaBean> listaFichaAfterBean) {
        this.listaFichaAfterBean = listaFichaAfterBean;
    }

    public List<FichaBean> getListaFichaPagoBean() {
        if (listaFichaPagoBean == null) {
            listaFichaPagoBean = new ArrayList<>();
        }
        return listaFichaPagoBean;
    }

    public void setListaFichaPagoBean(List<FichaBean> listaFichaPagoBean) {
        this.listaFichaPagoBean = listaFichaPagoBean;
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

    public Boolean getFlgPagoParDol() {
        return flgPagoParDol;
    }

    public void setFlgPagoParDol(Boolean flgPagoParDol) {
        this.flgPagoParDol = flgPagoParDol;
    }

    public TipoCambioBean getTipoCambio() {
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

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
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

    public Boolean getRenderBotonSave() {
        return renderBotonSave;
    }

    public void setRenderBotonSave(Boolean renderBotonSave) {
        this.renderBotonSave = renderBotonSave;
    }

    public Boolean getDisabledGuardar() {
        return disabledGuardar;
    }

    public void setDisabledGuardar(Boolean disabledGuardar) {
        this.disabledGuardar = disabledGuardar;
    }

    public Boolean getRenderImprimir() {
        return renderImprimir;
    }

    public void setRenderImprimir(Boolean renderImprimir) {
        this.renderImprimir = renderImprimir;
    }

    public Boolean getFlgModPOS() {
        return flgModPOS;
    }

    public void setFlgModPOS(Boolean flgModPOS) {
        this.flgModPOS = flgModPOS;
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

    public Boolean getFlgModPagoAmbos() {
        return flgModPagoAmbos;
    }

    public void setFlgModPagoAmbos(Boolean flgModPagoAmbos) {
        this.flgModPagoAmbos = flgModPagoAmbos;
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

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public Boolean getFlgGenCod() {
        return flgGenCod;
    }

    public void setFlgGenCod(Boolean flgGenCod) {
        this.flgGenCod = flgGenCod;
    }

    public PaganteBean getPaganteNewBean() {
        if (paganteNewBean == null) {
            paganteNewBean = new PaganteBean();
        }
        return paganteNewBean;
    }

    public void setPaganteNewBean(PaganteBean paganteNewBean) {
        this.paganteNewBean = paganteNewBean;
    }

    public Integer getTipAsignacion() {
        return tipAsignacion;
    }

    public void setTipAsignacion(Integer tipAsignacion) {
        this.tipAsignacion = tipAsignacion;
    }

    public Boolean getFlgRenderEsp() {
        return flgRenderEsp;
    }

    public void setFlgRenderEsp(Boolean flgRenderEsp) {
        this.flgRenderEsp = flgRenderEsp;
    }

    public Integer getNroAsig() {
        return nroAsig;
    }

    public void setNroAsig(Integer nroAsig) {
        this.nroAsig = nroAsig;
    }

    public Integer getNroIni() {
        return nroIni;
    }

    public void setNroIni(Integer nroIni) {
        this.nroIni = nroIni;
    }

    public Integer getNroFin() {
        return nroFin;
    }

    public void setNroFin(Integer nroFin) {
        this.nroFin = nroFin;
    }

    public FichaBean getFichaEspBean() {
        if (fichaEspBean == null) {
            fichaEspBean = new FichaBean();
        }
        return fichaEspBean;
    }

    public void setFichaEspBean(FichaBean fichaEspBean) {
        this.fichaEspBean = fichaEspBean;
    }

    public List<FichaBean> getListaFichaEspBean() {
        if (listaFichaEspBean == null) {
            listaFichaEspBean = new ArrayList<>();
        }
        return listaFichaEspBean;
    }

    public void setListaFichaEspBean(List<FichaBean> listaFichaEspBean) {
        this.listaFichaEspBean = listaFichaEspBean;
    }

    public Integer getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(Integer tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }

    public Boolean getDisFil() {
        return disFil;
    }

    public void setDisFil(Boolean disFil) {
        this.disFil = disFil;
    }

    public FichaBean getFichaEspecialBean() {
        if (fichaEspecialBean == null) {
            fichaEspecialBean = new FichaBean();
        }
        return fichaEspecialBean;
    }

    public void setFichaEspecialBean(FichaBean fichaEspecialBean) {
        this.fichaEspecialBean = fichaEspecialBean;
    }

    public String getEstadoFicha() {
        return estadoFicha;
    }

    public void setEstadoFicha(String estadoFicha) {
        this.estadoFicha = estadoFicha;
    }

    public FichaBean getFichaInsert() {
        return fichaInsert;
    }

    public void setFichaInsert(FichaBean fichaInsert) {
        this.fichaInsert = fichaInsert;
    }

}
