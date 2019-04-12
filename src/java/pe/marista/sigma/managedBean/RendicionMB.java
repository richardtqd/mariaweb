package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaLiquidacionBean;
import pe.marista.sigma.bean.CajaChicaMovBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.TipoCambioBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.TipoSolicitudBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovRepBean;
import pe.marista.sigma.bean.reporte.LiquidacionRepBean;
import pe.marista.sigma.bean.reporte.ReciboDevolucionRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AsientoService;
import pe.marista.sigma.service.CajaChicaLiquidacionService;
import pe.marista.sigma.service.CajaChicaMovService;
import pe.marista.sigma.service.CajaChicaService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentaBancoService;
import pe.marista.sigma.service.DocEgresoService;
import pe.marista.sigma.service.EntidadService;
import pe.marista.sigma.service.SolicitudCajaCHService;
import pe.marista.sigma.service.TipoCambioService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTCalculadoraCR;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class RendicionMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of RendicionMB
     */
    @PostConstruct
    public void init() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getCajaChicaMovFiltroBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
//            listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorFiltro(cajaChicaMovFiltroBean);
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoDocBean();
            listaTipoDocBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            TipoConceptoService conceptoCategoriaService = BeanFactory.getTipoConceptoService();
            getTipoConceptoBean();
            listaTipoConceptoBean = conceptoCategoriaService.obtenerTipoConcepto();

            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            getListaConceptoUniNegBean();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegActivos();

            getListaTipoPagoBean();
            listaTipoPagoBean = codigoService.obtenerCodigoDocEgreso();
            CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
            getListaCuentaBancoFiltroBean();
            listaCuentaBancoFiltroBean = cuentaBancoService.obtenerCuentaPorUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getDocEgresoFiltroBean().setFechaInicio(new Date());
            getDocEgresoFiltroBean().setFechaFin(new Date());
            getDocEgresoFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

            getCajaChicaMovFiltroBean().getCajaChicaBean().setInicioFecApertura(new Date());
            getCajaChicaMovFiltroBean().getCajaChicaBean().setFinFecApertura(new Date());
            getCajaChicaMovFiltroBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());

//            EntidadService entidadService = BeanFactory.getEntidadService();
//            listaEntidadBean = entidadService.obtenerEntidadPorFiltroProveedor(new EntidadBean());
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
            fechaActual = new GregorianCalendar();
            getCajaChicaLiquidacionBean().setFechaDoc(fechaActual.getTime());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }
    private CajaChicaLiquidacionBean cajaChicaLiquidacionBean;
    private List<CajaChicaMovBean> listaCajaChicaMovBean;
    private CajaChicaMovBean cajaChicaMovFiltroBean;
    private UsuarioBean usuarioLogin;
    private Boolean flgTipoRendicion = Boolean.FALSE;
//    private Boolean flgTipoRendicion1;
    private CajaChicaLiquidacionBean chicaLiquidacionBean;
    private List<CodigoBean> listaTipoDocBean;
    private TipoConceptoBean tipoConceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<CajaChicaLiquidacionBean> listaCajaChicaLiquidacionBean;
    private Double montoTotalRend = 0d;
    private Double montoDiferenciaRend = 0d;
    private Boolean operacion1;
    private Boolean operacion2;
    private Boolean operacion3;
    private Boolean operacion4;
    private ReciboDevolucionRepBean reciboDevolucionRepBean;
//Parte 2
    private DocEgresoBean docEgresoFiltroBean;
    private List<CodigoBean> listaTipoPagoBean;
    private List<CuentaBancoBean> listaCuentaBancoFiltroBean;
    private List<DocEgresoBean> listaDocEgresoBean;
    private CajaChicaMovBean cajaChicaMov;
    private List<EntidadBean> listaEntidadBean;
    private Double porcentaje = 0D;
    //CR
    private DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB;
    private List<CentroResponsabilidadBean> listaCentroResponsabilidadBean;
//    private Integer tipoEstRend=0;

    private List<CodigoBean> listaTipoDistribucionCRBean;
    private Calendar fechaActual;
    private Boolean flgDscto = Boolean.TRUE;
    private Boolean flgCr = false;
    //Logica de Negocio y Aplicacion
    
    private String flgRenderCr = "true";

    public void obtenerCajaMovimiento() {
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            listaCajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorFiltro(cajaChicaMovFiltroBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    //añadido
    public void obtenerDocEgresoFiltro() {
        try {
            DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
            TipoSolicitudBean tsb = new TipoSolicitudBean();
            tsb.setNombre(MaristaConstantes.A_RENDIR);
            getDocEgresoFiltroBean().getSolicitudCajaCHBean().setTipoSolicitudBean(tsb);
            listaDocEgresoBean = docEgresoService.obtenerDocEgresoPorFiltroARend(docEgresoFiltroBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }

    }

    public void obtenerTipoPorConcepto(Integer idConcepto) {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            this.flgRenderCr = conceptoService.validarSiTieneCr(idConcepto);
            System.out.println("flgRenderCr--->" + flgRenderCr);
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            Integer id = null;
            id = conceptoUniNegService.obtenerTipoPorIdConcepto(idConcepto, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!id.equals(null)) {
                TipoConceptoBean tipo = new TipoConceptoBean();
                tipo = tipoConceptoService.obtenerTipoConceptoPorId(id);
                if (!tipo.equals(null)) {
//                    tipoConceptoBean=tipo; 
                    getCajaChicaLiquidacionBean().getConceptoBean().setTipoConceptoBean(tipo);

                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
    public void crBarina() {
        setFlgCr(flgCr);
        System.out.println("si chau xD: " + flgCr);
    }

    public void limpiarFiltroCajaMov() {
        try {
            cajaChicaMovFiltroBean = new CajaChicaMovBean();
            //añadido
            listaCajaChicaMovBean = new ArrayList<>();
            getCajaChicaMovFiltroBean().getCajaChicaBean().setInicioFecApertura(new Date());
            getCajaChicaMovFiltroBean().getCajaChicaBean().setFinFecApertura(new Date());
            getCajaChicaMovFiltroBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarMovimiento(Object object) {
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
            getCajaChicaLiquidacionBean().setFechaDoc(fechaActual.getTime());
            tipoConceptoBean = new TipoConceptoBean();
            cajaChicaMov = (CajaChicaMovBean) object;
            setCajaChicaMov(cajaChicaMovService.obtenerCajaChicaMovPorId(cajaChicaMov));
            getCajaChicaLiquidacionBean().setCajaChicaMovBean(cajaChicaMov);
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaLiquidacionBean = cajaChicaLiquidacionService.obtenerCajaChicaLiquidacionPorMov(cajaChicaLiquidacionBean);
            montoTotalRend = 0d;
            for (CajaChicaLiquidacionBean rendicion : listaCajaChicaLiquidacionBean) {
                montoTotalRend = montoTotalRend + rendicion.getMontoTotal();
            }
            //CR
            Integer idSol = 0;
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
//            System.out.println("idSolicitud: " + cajaChicaMov.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
            if (getCajaChicaMov().getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
                idSol = getCajaChicaMov().getSolicitudCajaCHBean().getIdSolicitudCajaCh();
                System.out.println(idSol);
            }
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorCodigoDisCR(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cod != null) {
                cajaChicaLiquidacionBean.setCodDistribucion(cod.getIdCodigo());
                cajaChicaLiquidacionBean.getTipoDistribucion().setIdCodigo(cod.getIdCodigo());
            }

            listaCentroResponsabilidadBean = centroResponsabilidadService.
                    obtenerInCrSolCaj(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.
                    obtenerOutCrSolCaj(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarMovimiento2(Integer id) {
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
            getCajaChicaLiquidacionBean().setFechaDoc(fechaActual.getTime());
            tipoConceptoBean = new TipoConceptoBean();
            CajaChicaMovBean chica = new CajaChicaMovBean();
            chica.setIdCajaChicaMov(id);
            chica.getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            cajaChicaMov = (CajaChicaMovBean) chica;
            setCajaChicaMov(cajaChicaMovService.obtenerCajaChicaMovPorId(cajaChicaMov));
            getCajaChicaLiquidacionBean().setCajaChicaMovBean(cajaChicaMov);
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaLiquidacionBean = cajaChicaLiquidacionService.obtenerCajaChicaLiquidacionPorMov(cajaChicaLiquidacionBean);
            montoTotalRend = 0d;
            for (CajaChicaLiquidacionBean rendicion : listaCajaChicaLiquidacionBean) {
                montoTotalRend = montoTotalRend + rendicion.getMontoTotal();
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cajaChicaLiquidacionBean = (CajaChicaLiquidacionBean) event.getObject();
//            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
//            
            setTipoConceptoBean(cajaChicaLiquidacionBean.getConceptoBean().getTipoConceptoBean());
            obtenerConceptoPorTipo();
            //CR
            AsientoService asientoService = BeanFactory.getAsientoService();
            AsientoBean asientoBean = new AsientoBean();
            asientoBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            asientoBean.setObjeto(MaristaConstantes.OBJ_LIQUIDACION);
            asientoBean.setIdObjeto(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion());

            //CR 
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentroResponsabilidadBean = new ArrayList<>();
            listaCentroResponsabilidadBeanB = new ArrayList<>();

            listaCentroResponsabilidadBean = asientoService.obtenerCRLiquidacion(asientoBean);
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.
                    obtenerOutCrLiq(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

            List<AsientoBean> lista = new ArrayList<>();
            lista = asientoService.obtenerCRLiq(cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (lista != null) {
                cajaChicaLiquidacionBean.setListaCajaChicaLiquidacionCRBean(null);
                cajaChicaLiquidacionBean.setListaCajaChicaLiquidacionCRBean(lista);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(cajaChicaLiquidacionBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarCajaChicaLiquidacionBean() {
        try {
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarCajaChicaLiquidacionBean(Integer idDoc, Integer idCaj) {
        try {
            System.out.println(idDoc + "-" + idCaj);
            if (idCaj == null) {
                cargarDocEgreso2(idDoc);
            }
            if (idDoc == null) {
                cargarMovimiento2(idCaj);
            }
//            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerRendicionPorId(Object objeto) {
        try {
            cajaChicaLiquidacionBean = (CajaChicaLiquidacionBean) objeto;
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void insertarRendicion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                TipoCambioService tipoCambioService = BeanFactory.getTipoCambioService();
                TipoCambioBean tipoCambio = new TipoCambioBean();
                Integer IdTipoMoneda = tipoCambioService.obtenerUltimoTipCambio();
                tipoCambio.setIdTipoMoneda(IdTipoMoneda);
                tipoCambio = tipoCambioService.buscarPorId(tipoCambio);
                CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
                cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                cajaChicaLiquidacionBean.setCreaPor(usuarioLogin.getUsuario());
                if (dualCentroResponsabilidadBean != null) {
                    cajaChicaLiquidacionService.insertarCajaChicaLiquidacion(cajaChicaLiquidacionBean, dualCentroResponsabilidadBean.getSource(), tipoCambio.getTcVenta().doubleValue());
                } else {
                    cajaChicaLiquidacionService.insertarCajaChicaLiquidacion(cajaChicaLiquidacionBean, dualCentroResponsabilidadBean.getSource(), tipoCambio.getTcVenta().doubleValue());
                }
//                cargarMovimiento(cajaChicaLiquidacionBean.getCajaChicaMovBean());
//                limpiarCajaChicaLiquidacionBean();
//                listaCajaChicaLiquidacionBean = cajaChicaLiquidacionService.obtenerCajaChicaLiquidacion(cajaChicaLiquidacionBean);
                this.flgDscto = Boolean.FALSE;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void modificarRendicion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
                cajaChicaLiquidacionBean.setModiPor(usuarioLogin.getUsuario());
                cajaChicaLiquidacionService.modificarCajaChicaLiquidacion(cajaChicaLiquidacionBean);
//                cargarMovimiento(cajaChicaLiquidacionBean.getCajaChicaMovBean());
                this.flgDscto = Boolean.FALSE;
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void eliminarRendicion() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
                cajaChicaLiquidacionBean.setModiPor(usuarioLogin.getUsuario());
                cajaChicaLiquidacionService.eliminarCajaChicaLiquidacion(cajaChicaLiquidacionBean);
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null
                        && cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() == null) {
                    if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() == null) {
                        cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getIdSolCajaCh());
                    }
                    cargarMovimiento(cajaChicaLiquidacionBean.getCajaChicaMovBean());
                } else if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null
                        && cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() == null) {
                        cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getIdSolCajaCh());
                    }
                    cargarDocEgreso(cajaChicaLiquidacionBean.getDocEgresoBean());
                }

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void guardarRendicion() {
        try {
            if (cajaChicaLiquidacionBean.getRuc() != null && !cajaChicaLiquidacionBean.getRuc().equals("")) {
                if (cajaChicaLiquidacionBean.getIdCajaChicaLiquidacion() == null) {
                    insertarRendicion();
                } else {
                    modificarRendicion();
                }
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null
                        && cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() == null) {
                    if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() == null) {
                        cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getIdSolCajaCh());
                    }
                    cargarMovimiento(cajaChicaLiquidacionBean.getCajaChicaMovBean());
                } else if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null
                        && cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() == null) {
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh() == null) {
                        cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setIdSolicitudCajaCh(cajaChicaLiquidacionBean.getIdSolCajaCh());
                    }
                    cargarDocEgreso(cajaChicaLiquidacionBean.getDocEgresoBean());
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjDatosRequeridos");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Centro de Responsabilidad
    public void distribuir() {
        System.out.println("entró distribuir()");
        try {
            //1.-Mappear Dual a Lista
//            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
//            DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidad = new DualListModel<>();
//            dualCentroResponsabilidad=dualCentroResponsabilidadBean;
            listaCentroResponsabilidad = dualCentroResponsabilidadBean.getTarget();
//            for (int i = 0; i < dualCentroResponsabilidadBean.getTarget().size(); i++) {
//                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
//                Object objeto = dualCentroResponsabilidadBean.getTarget().get(i);
//                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
//                listaCentroResponsabilidad.add(cr);
//            }
            if (listaCentroResponsabilidad != null) {
                if (!listaCentroResponsabilidad.isEmpty()) {
                    //2.-Invocar calculadora
                    for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                        if (cajaChicaLiquidacionBean.getTipoDistribucion().getIdCodigo().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, cajaChicaLiquidacionBean.getMontoTotal());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, cajaChicaLiquidacionBean.getMontoTotal());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                                new GLTCalculadoraCR().calcularPorPersonalizacion(listaCentroResponsabilidad);
                                break;
                            }
                        }
                    }
                    //3.-Crear Lista Respuesta
                    List<AsientoBean> listaCajaChicaLiquidacion = new ArrayList<>();
                    for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                        AsientoBean detCajaChicaLiquidacionCRBean = new AsientoBean();
                        detCajaChicaLiquidacionCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
//                        detCajaChicaLiquidacionCRBean.setTipoDistribucion(new CodigoBean(cajaChicaLiquidacionBean.getCodDistribucion()));
//                        detCajaChicaLiquidacionCRBean.setCodDistribucion((cajaChicaLiquidacionBean.getCodDistribucion()));
                        detCajaChicaLiquidacionCRBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                        detCajaChicaLiquidacionCRBean.setMonto(centroResponsabilidadBean.getMontoDistribucion());
                        listaCajaChicaLiquidacion.add(detCajaChicaLiquidacionCRBean);
                    }
                    cajaChicaLiquidacionBean.setListaCajaChicaLiquidacionCRBean(listaCajaChicaLiquidacion);
                } else {
                    new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                    cajaChicaLiquidacionBean.setListaCajaChicaLiquidacionCRBean(null);
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                cajaChicaLiquidacionBean.setListaCajaChicaLiquidacionCRBean(null);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void rendirCuentas() {
        try {
            CajaChicaService cajaChicaService = BeanFactory.getCajaChicaService();
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            CajaChicaBean cajaChica = new CajaChicaBean();
            cajaChica.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//            System.out.println("cajachica: "+cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getIdCajaChica());
            cajaChica.setIdCajaChica(cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().getIdCajaChica());
            cajaChica = cajaChicaService.obtenerCajaChicaPorId(cajaChica);
            List<CajaChicaBean> listaAbierto = cajaChicaService.obtenerCajaChicaAbierto(cajaChica);
            //Operacion 1: Genera solicitud.
            if (operacion1) {
                CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
                CajaChicaBean cajaChicaBean = new CajaChicaBean();
                if (listaAbierto.size() == 1) {
                    cajaChicaBean = listaAbierto.get(0);
                }
                montoDiferenciaRend = montoDiferenciaRend * (-1);
                cajaChicaLiquidacionService.rendirCuentas(operacion1, usuarioLogin, cajaChicaLiquidacionBean, montoDiferenciaRend, cajaChicaBean.getMontoMaxMovSol(), cajaChicaBean.getMontoMaxMovDol());

//                if (listaAbierto.size() == 1) {
//                    cajaChicaBean = listaAbierto.get(0);
//                }
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                    if (montoDiferenciaRend <= cajaChicaBean.getMontoMaxMovSol()) {
                        prepararFormCajaChicaMov(cajaChicaService, cajaChicaMovService, cajaChicaBean);
                    } else if (montoDiferenciaRend > cajaChicaBean.getMontoMaxMovDol()) {
//                        DocEgresoBean docEgresoBean = new DocEgresoBean();
                        DocEgresoMB docEgresoMB = new DocEgresoMB();
                        docEgresoMB.getDocEgresoBean().setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());
//                        docEgresoBean.setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
//                        docEgresoBean = cajaChicaLiquidacionBean.getDocEgresoBean();
//                        docEgresoBean.setIdDocEgreso(null);
//                        docEgresoMB.setDocEgresoBean(docEgresoBean);
                        new MaristaUtils().sesionColocarObjeto("solicitudDoc", docEgresoMB);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta11", true);
                    }
                }
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)
                            && montoDiferenciaRend <= cajaChicaBean.getMontoMaxMovSol()) {
                        cajaChicaLiquidacionBean.getCajaChicaMovBean().setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                        prepararFormCajaChicaMov(cajaChicaService, cajaChicaMovService, cajaChicaBean);
                    } else if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)
                            && montoDiferenciaRend <= cajaChicaBean.getMontoMaxMovDol()) {
                        prepararFormCajaChicaMov(cajaChicaService, cajaChicaMovService, cajaChicaBean);
                    }
                    if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)
                            && montoDiferenciaRend > cajaChicaBean.getMontoMaxMovSol()) {
                        DocEgresoBean docEgresoBean = new DocEgresoBean();
                        DocEgresoMB docEgresoMB = new DocEgresoMB();
//                        docEgresoBean.setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                        docEgresoBean = cajaChicaLiquidacionBean.getDocEgresoBean();
                        docEgresoBean.setIdDocEgreso(null);
                        docEgresoMB.setDocEgresoBean(docEgresoBean);
                        new MaristaUtils().sesionColocarObjeto("solicitudDoc", docEgresoMB);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta11", true);
                    } else if (cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)
                            && montoDiferenciaRend > cajaChicaBean.getMontoMaxMovDol()) {
                        DocEgresoBean docEgresoBean = new DocEgresoBean();
                        DocEgresoMB docEgresoMB = new DocEgresoMB();
//                        docEgresoBean.setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                        docEgresoBean = cajaChicaLiquidacionBean.getDocEgresoBean();
                        docEgresoBean.setIdDocEgreso(null);
                        docEgresoMB.setDocEgresoBean(docEgresoBean);
                        new MaristaUtils().sesionColocarObjeto("solicitudDoc", docEgresoMB);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta11", true);
                    }
                }
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
            //Operacion 2: GEnera ingreso Cajachica Abierta.
            if (operacion2) {
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                    //1.- Inserta Devolucion 
                    CajaChicaMovBean cajaChicaMovBean = cajaChicaLiquidacionBean.getCajaChicaMovBean();
                    cajaChicaMovBean = cajaChicaMovService.obtenerCajaChicaMovPorId(cajaChicaMovBean);
//                cajaChicaMovBean.getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    if (listaAbierto.size() == 1) {
                        cajaChicaMovBean.setCajaChicaBean(listaAbierto.get(0));
                        cajaChicaMovBean.setMotivo(MensajesBackEnd.getValueOfKey("msjDevRend", null));
                        cajaChicaMovBean.setAnio(listaAbierto.get(0).getAnio());
                        cajaChicaMovBean.setFecOrden(new Date());
                        cajaChicaMovBean.setSolicitudCajaCHBean(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());
                        cajaChicaMovBean.getSolicitudCajaCHBean().setTipoMonedaBean(cajaChicaMovBean.getTipoMonedaBean());
                        cajaChicaMovBean.setMonto(montoDiferenciaRend);
                        cajaChicaMovBean.setCreaPor(usuarioLogin.getUsuario());
                        cajaChicaMovBean.setFlgRendicion(Boolean.FALSE);
                        cajaChicaMovBean.setFlgMov(1);
                        cajaChicaMovBean.setIdDevolucion(cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov());
                        cajaChicaMovService.insertarCajaChicaMov(cajaChicaMovBean);
                        //2.- Genera recibo
                        cargarRecibo();
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta2", true);
                        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                    } else {
                        new MensajePrime().addInformativeMessagePer("msjNoHayCajaAbi");
                    }
                }
            }
            if (operacion4) {
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
                    CodigoBean cb = new CodigoBean(MaristaConstantes.COD_REND_INICIADO);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setTipoEstRend(cb);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHService.modificarTipoEstRend(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta4", true);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
            //añadido GL19
            if (operacion3) {
                if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
                    CodigoBean cb = new CodigoBean(MaristaConstantes.COD_REND_FINALIZADO);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setTipoEstRend(cb);
                    cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    cajaChicaLiquidacionBean.getDocEgresoBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHService.modificarTipoEstRend(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean());

                    cajaChicaLiquidacionBean.getDocEgresoBean().setFlgRendicion(Boolean.TRUE);
                    DocEgresoService docEgresoService = BeanFactory.getDocEgresoService();
                    docEgresoService.cambiarEstadoRendicionDoc(cajaChicaLiquidacionBean.getDocEgresoBean());

                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta3", true);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
                if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                    CodigoBean cb = new CodigoBean(MaristaConstantes.COD_REND_FINALIZADO);
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setTipoEstRend(cb);
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                    solicitudCajaCHService.modificarTipoEstRend(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());

                    cajaChicaLiquidacionBean.getCajaChicaMovBean().setFlgRendicion(Boolean.TRUE);
                    cajaChicaMovService.cambiarEstadoRendicionMov(cajaChicaLiquidacionBean.getCajaChicaMovBean());

                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta3", true);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
            obtenerDocEgresoFiltro();
            //fin
            obtenerCajaMovimiento();
            cargarMovimiento(cajaChicaLiquidacionBean.getCajaChicaMovBean());
//            imprimirLiquidacion(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getIdSolicitudCajaCh());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void prepararFormCajaChicaMov(CajaChicaService cajaChicaService, CajaChicaMovService cajaChicaMovService, CajaChicaBean cajaChicaBean) throws Exception {
        cajaChicaBean = cajaChicaService.obtenerCajaChicaPorId(cajaChicaBean);
        List<CajaChicaMovBean> listaCajaChicaMov = new ArrayList<>();
        cajaChicaMov = new CajaChicaMovBean();
        cajaChicaMov.setCajaChicaBean(cajaChicaBean);
        listaCajaChicaMov = cajaChicaMovService.obtenerCajaChicaMovPorCCH(cajaChicaMov);
        CajaChicaMovMB cajaChicaMovMB = new CajaChicaMovMB();
        cajaChicaMovMB.setCajaChicaBean(cajaChicaBean);
        cajaChicaMovMB.ponerSolicitud(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean());
        cajaChicaMovMB.setListaCajaChicaMovBean(listaCajaChicaMov);
        new MaristaUtils().sesionColocarObjeto("solicitud", cajaChicaMovMB);
        RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta1", true);
    }

    public void cargarRecibo() {
        try {
            reciboDevolucionRepBean = new ReciboDevolucionRepBean();
            reciboDevolucionRepBean.setNombre(cajaChicaLiquidacionBean.getCajaChicaMovBean().getSolicitudCajaCHBean().getPersonalBean().getNombreCompleto());
            reciboDevolucionRepBean.setImporte(montoDiferenciaRend.toString());
            reciboDevolucionRepBean.setTipoMoneda(cajaChicaLiquidacionBean.getCajaChicaMovBean().getTipoMonedaBean().getCodigo());
//            reciboDevolucionRepBean.setFecha(cajaChicaLiquidacionBean.getCreaFecha().toString());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void confirmaRendirCuentas() {
        try {
            operacion1 = false;
            operacion2 = false;
            operacion3 = false;
            operacion4 = false;
            Double montoTR=montoTotalRend;
            double rounded = (double) Math.round(montoTR * 100D) / 100D;
            montoTotalRend=rounded;
            if (cajaChicaLiquidacionBean.getCajaChicaMovBean().getIdCajaChicaMov() != null) {
                montoDiferenciaRend = cajaChicaLiquidacionBean.getCajaChicaMovBean().getMonto() - montoTotalRend;
                if (montoTotalRend > cajaChicaLiquidacionBean.getCajaChicaMovBean().getMonto()) {
                    operacion1 = true;
                }
                if (montoTotalRend < cajaChicaLiquidacionBean.getCajaChicaMovBean().getMonto()) {
                    operacion2 = true;
                }
                if (Objects.equals(montoTotalRend, cajaChicaLiquidacionBean.getCajaChicaMovBean().getMonto())) {
                    operacion3 = true;
                }
            }
            if (cajaChicaLiquidacionBean.getDocEgresoBean().getIdDocEgreso() != null) {
                montoDiferenciaRend = cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue() - montoTotalRend;
                System.out.println("montoDiferenciaRend "+montoDiferenciaRend);
                System.out.println("montoTotalRend "+montoTotalRend);
                System.out.println("cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue() "+cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue());
                if (montoTotalRend > cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue()) {
                    operacion1 = true;
                }
                if (montoTotalRend < cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue()) {
                    operacion4 = true;
                }
                if (Objects.equals(montoTotalRend, cajaChicaLiquidacionBean.getDocEgresoBean().getMontoPagado().doubleValue())) {
                    operacion3 = true;
                }
                if (Objects.equals(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoEstRend().getIdCodigo(), MaristaConstantes.COD_REND_INICIADO)) {
//                    tipoEstRend=1;
                }
                if (Objects.equals(cajaChicaLiquidacionBean.getDocEgresoBean().getSolicitudCajaCHBean().getTipoEstRend().getIdCodigo(), MaristaConstantes.COD_REND_FINALIZADO)) {
//                    tipoEstRend=2;
                }
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //Rendicion 2
    public void obtenerFiltroDocEgreso() {
        try {
            int estado = 0;
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
                TipoSolicitudBean tsb = new TipoSolicitudBean();
                tsb.setNombre(MaristaConstantes.A_RENDIR);
                getDocEgresoFiltroBean().getSolicitudCajaCHBean().setTipoSolicitudBean(tsb);
                docEgresoFiltroBean.setFlgAnulado(Boolean.FALSE);
                listaDocEgresoBean = docEgresoService.obtenerDocEgresoPorFiltroARend(docEgresoFiltroBean);
                if (listaDocEgresoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarDocEgresoFiltro() {
        docEgresoFiltroBean = new DocEgresoBean();
        listaDocEgresoBean = new ArrayList<>();
        //filtros
        getDocEgresoFiltroBean().setFechaInicio(new Date());
        getDocEgresoFiltroBean().setFechaFin(new Date());
        getDocEgresoFiltroBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
//        montoDetraccion = new Double("0.00");
    }

    public void cargarDocEgreso(Object object) {
        try {
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
            getCajaChicaLiquidacionBean().setFechaDoc(fechaActual.getTime());
            tipoConceptoBean = new TipoConceptoBean();
            DocEgresoBean bean = (DocEgresoBean) object;
            getCajaChicaLiquidacionBean().setDocEgresoBean(bean);
            getCajaChicaLiquidacionBean().getDocEgresoBean().setMontoPagado(bean.getMontoPagado());
            System.out.println(">>>>>" + getCajaChicaLiquidacionBean().getDocEgresoBean().getMontoPagado());
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            cajaChicaLiquidacionBean.setIdSolCajaCh(bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
            System.out.println("total" + bean.getMontoTotal());
            listaCajaChicaLiquidacionBean = cajaChicaLiquidacionService.obtenerCajaChicaLiquidacionPorDoc(cajaChicaLiquidacionBean);
            montoTotalRend = 0d;
            for (CajaChicaLiquidacionBean rendicion : listaCajaChicaLiquidacionBean) {
                montoTotalRend = montoTotalRend + rendicion.getMontoTotal();
            }
            //CR
            Integer idSol = 0;
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            if (bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh() != null) {
                idSol = bean.getSolicitudCajaCHBean().getIdSolicitudCajaCh();
                System.out.println("id:" + idSol);
            }
            CodigoService codigoService = BeanFactory.getCodigoService();
            CodigoBean cod = new CodigoBean();
            cod = codigoService.obtenerPorCodigoDisCR(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cod != null) {
                cajaChicaLiquidacionBean.setCodDistribucion(cod.getIdCodigo());
                cajaChicaLiquidacionBean.getTipoDistribucion().setIdCodigo(cod.getIdCodigo());
            }
            listaCentroResponsabilidadBean = centroResponsabilidadService.
                    obtenerInCrSolCaj(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCentroResponsabilidadBeanB = centroResponsabilidadService.
                    obtenerOutCrSolCaj(idSol, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            dualCentroResponsabilidadBean = new DualListModel<>(listaCentroResponsabilidadBeanB, listaCentroResponsabilidadBean);

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarDocEgreso2(Integer id) {
        try {
//            DocEgresoService docEgresoService=BeanFactory.getDocEgresoService();
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
            getCajaChicaLiquidacionBean().setFechaDoc(fechaActual.getTime());
            tipoConceptoBean = new TipoConceptoBean();
            DocEgresoBean doc = new DocEgresoBean();
            doc.setIdDocEgreso(id);
            doc.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            DocEgresoBean bean = (DocEgresoBean) doc;

            getCajaChicaLiquidacionBean().setDocEgresoBean(bean);
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            cajaChicaLiquidacionBean.getCajaChicaMovBean().getCajaChicaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaLiquidacionBean = cajaChicaLiquidacionService.obtenerCajaChicaLiquidacionPorDoc(cajaChicaLiquidacionBean);
            montoTotalRend = 0d;
            for (CajaChicaLiquidacionBean rendicion : listaCajaChicaLiquidacionBean) {
                montoTotalRend = montoTotalRend + rendicion.getMontoTotal();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //REportes
    public void imprimirReciboDevPdf() {
        ServletOutputStream out = null;
        try {
            CajaChicaMovService cajaChicaMovService = BeanFactory.getCajaChicaMovService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reciboDevCajaChica.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaChicaMovRepBean> listaCajaChicaMovRepBean = new ArrayList<>();
//            cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaCajaChicaMovRepBean = cajaChicaMovService.obtenerCajaChicaMovRepPorIdDev(cajaChicaMov.getIdDevolucion());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaChicaMovRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
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

    //REportes
    public void imprimirLiquidacion(Integer id) {
        ServletOutputStream out = null;
        try {
            CajaChicaLiquidacionService cajaChicaLiquidacionService = BeanFactory.getCajaChicaLiquidacionService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteLiquidacion.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<LiquidacionRepBean> listaLiquidacionRepBean = new ArrayList<>();
//            cajaChicaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            listaLiquidacionRepBean = cajaChicaLiquidacionService.obtenerDetLiquidacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), id, null);
//            listaLiquidacionRepBean = cajaChicaLiquidacionService.obtenerLiquidacion(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), id);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaLiquidacionRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
//            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
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

    //Entidad
    public String actualizarEntidad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                EntidadService entidadService = BeanFactory.getEntidadService();
                listaEntidadBean = entidadService.obtenerEntidadPorUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void obtenerEntidadPorId(EntidadBean bean) {
        try {
            EntidadService entidadService = BeanFactory.getEntidadService();
            EntidadBean entidadBean = entidadService.obtenerEntidadPorId(bean);
            cajaChicaLiquidacionBean.setProveedor(entidadBean.getNombre());
            cajaChicaLiquidacionBean.setRuc(entidadBean.getRuc());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void change() {
        System.out.println(">>>: " + cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo());

        if (cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo() != null) {
//            if (cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo() == 15201) {
//                porcentaje = 18d;
//                this.flgDscto = Boolean.TRUE;
//            } 
            if (this.flgDscto.equals(Boolean.TRUE)) {
                if (cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo() == 15201) {
                    porcentaje = 18d; 
                } else if (cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo() == 15202) {
                    porcentaje = 0d;
                } else if (cajaChicaLiquidacionBean.getTipoDoc().getIdCodigo() == 15203) {
                    porcentaje = 8d;
                } else {
                    porcentaje = 0d;
                }
                calcular();
            } else {
                porcentaje = 0d;
                calcular();
            }
        } else {
            porcentaje = 0d;
            calcular();
        }
    }

    public void calcular() {
        if (cajaChicaLiquidacionBean.getMonto() > 0) {
            Float total1 = 0f;
            Float total2 = 0f;
            System.out.println("porcentaje: " + porcentaje);
            System.out.println("monto: " + cajaChicaLiquidacionBean.getMonto());
            System.out.println("descuento: " + cajaChicaLiquidacionBean.getDescuento());
            if (cajaChicaLiquidacionBean.getMonto() != null) {
                total1 = total1 + cajaChicaLiquidacionBean.getMonto().floatValue();
                double rounded1 = (double) Math.round(total1 * 100) / 100;
//            System.out.println("total11: " + total1);
            }
            if (cajaChicaLiquidacionBean.getDescuento() != null) {
                total1 = total1 - cajaChicaLiquidacionBean.getDescuento().floatValue();
                double rounded1 = (double) Math.round(total1 * 100) / 100;
//            System.out.println("total2: " + total1);
            }
            if (total1 != null && total1 != 0f) {
//            double igv = porcentaje; 
                cajaChicaLiquidacionBean.setImpuesto((porcentaje * total1) / 100);
//            System.out.println("total13: " + total1);
            }
            total2 = total1 + cajaChicaLiquidacionBean.getImpuesto().floatValue();
            double rounded3 = (double) Math.round(total2 * 100) / 100;
            cajaChicaLiquidacionBean.setMontoTotal(rounded3);
//        System.out.println("total1: " + total1);
//        System.out.println("total2: " + total2);
            calcularTotal();
        }

    }

    public void calcularTotal() {
        Float monto = 0f;
        Float imp = 0f;
        Float montot = cajaChicaLiquidacionBean.getMontoTotal().floatValue();
        if (cajaChicaLiquidacionBean.getDescuento() != null) {
            montot = montot - cajaChicaLiquidacionBean.getDescuento().floatValue();
            System.out.println("total2: " + montot);
        }
        monto = montot / (((porcentaje.floatValue()) / 100) + 1);
        double montoRounded = (double) Math.round(monto * 100) / 100;
        imp = montot - (montot / (((porcentaje.floatValue()) / 100) + 1));
        double impRounded = (double) Math.round(imp * 100) / 100;
        System.out.println(">>>>" + cajaChicaLiquidacionBean.getMontoTotal());
        cajaChicaLiquidacionBean.setMontoTotal(cajaChicaLiquidacionBean.getMontoTotal());
        cajaChicaLiquidacionBean.setImpuesto(impRounded);
        cajaChicaLiquidacionBean.setMonto(montoRounded);
        distribuir();
    }

    //Getter y Setter
    public CajaChicaLiquidacionBean getCajaChicaLiquidacionBean() {
        if (cajaChicaLiquidacionBean == null) {
            cajaChicaLiquidacionBean = new CajaChicaLiquidacionBean();
        }
        return cajaChicaLiquidacionBean;
    }

    public void setCajaChicaLiquidacionBean(CajaChicaLiquidacionBean cajaChicaLiquidacionBean) {
        this.cajaChicaLiquidacionBean = cajaChicaLiquidacionBean;
    }

    public List<CajaChicaMovBean> getListaCajaChicaMovBean() {
        if (listaCajaChicaMovBean == null) {
            listaCajaChicaMovBean = new ArrayList<>();
        }
        return listaCajaChicaMovBean;
    }

    public void setListaCajaChicaMovBean(List<CajaChicaMovBean> listaCajaChicaMovBean) {
        this.listaCajaChicaMovBean = listaCajaChicaMovBean;
    }

    public CajaChicaMovBean getCajaChicaMovFiltroBean() {
        if (cajaChicaMovFiltroBean == null) {
            cajaChicaMovFiltroBean = new CajaChicaMovBean();
        }
        return cajaChicaMovFiltroBean;
    }

    public void setCajaChicaMovFiltroBean(CajaChicaMovBean cajaChicaMovFiltroBean) {
        this.cajaChicaMovFiltroBean = cajaChicaMovFiltroBean;
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

    public Boolean getFlgTipoRendicion() {
        return flgTipoRendicion;
    }

    public void setFlgTipoRendicion(Boolean flgTipoRendicion) {
        this.flgTipoRendicion = flgTipoRendicion;
    }

    public CajaChicaLiquidacionBean getChicaLiquidacionBean() {
        if (chicaLiquidacionBean == null) {
            chicaLiquidacionBean = new CajaChicaLiquidacionBean();
        }
        return chicaLiquidacionBean;
    }

    public void setChicaLiquidacionBean(CajaChicaLiquidacionBean chicaLiquidacionBean) {
        this.chicaLiquidacionBean = chicaLiquidacionBean;
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

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public List<CajaChicaLiquidacionBean> getListaCajaChicaLiquidacionBean() {
        if (listaCajaChicaLiquidacionBean == null) {
            listaCajaChicaLiquidacionBean = new ArrayList<>();
        }
        return listaCajaChicaLiquidacionBean;
    }

    public void setListaCajaChicaLiquidacionBean(List<CajaChicaLiquidacionBean> listaCajaChicaLiquidacionBean) {
        this.listaCajaChicaLiquidacionBean = listaCajaChicaLiquidacionBean;
    }

    public Double getMontoTotalRend() {
        return montoTotalRend;
    }

    public void setMontoTotalRend(Double montoTotalRend) {
        this.montoTotalRend = montoTotalRend;
    }

    public Double getMontoDiferenciaRend() {
        return montoDiferenciaRend;
    }

    public void setMontoDiferenciaRend(Double montoDiferenciaRend) {
        this.montoDiferenciaRend = montoDiferenciaRend;
    }

    public Boolean getOperacion1() {
        return operacion1;
    }

    public void setOperacion1(Boolean operacion1) {
        this.operacion1 = operacion1;
    }

    public Boolean getOperacion2() {
        return operacion2;
    }

    public void setOperacion2(Boolean operacion2) {
        this.operacion2 = operacion2;
    }

    public Boolean getOperacion3() {
        return operacion3;
    }

    public void setOperacion3(Boolean operacion3) {
        this.operacion3 = operacion3;
    }

    public ReciboDevolucionRepBean getReciboDevolucionRepBean() {
        if (reciboDevolucionRepBean == null) {
            reciboDevolucionRepBean = new ReciboDevolucionRepBean();
        }
        return reciboDevolucionRepBean;
    }

    public void setReciboDevolucionRepBean(ReciboDevolucionRepBean reciboDevolucionRepBean) {
        this.reciboDevolucionRepBean = reciboDevolucionRepBean;
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

    public List<CodigoBean> getListaTipoPagoBean() {
        if (listaTipoPagoBean == null) {
            listaTipoPagoBean = new ArrayList<>();
        }
        return listaTipoPagoBean;
    }

    public void setListaTipoPagoBean(List<CodigoBean> listaTipoPagoBean) {
        this.listaTipoPagoBean = listaTipoPagoBean;
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

    public List<DocEgresoBean> getListaDocEgresoBean() {
        if (listaDocEgresoBean == null) {
            listaDocEgresoBean = new ArrayList<>();
        }
        return listaDocEgresoBean;
    }

    public void setListaDocEgresoBean(List<DocEgresoBean> listaDocEgresoBean) {
        this.listaDocEgresoBean = listaDocEgresoBean;
    }

    public CajaChicaMovBean getCajaChicaMov() {
        if (cajaChicaMov == null) {
            cajaChicaMov = new CajaChicaMovBean();
        }
        return cajaChicaMov;
    }

    public void setCajaChicaMov(CajaChicaMovBean cajaChicaMov) {
        this.cajaChicaMov = cajaChicaMov;
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

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public DualListModel<CentroResponsabilidadBean> getDualCentroResponsabilidadBean() {
        if (dualCentroResponsabilidadBean == null) {
            dualCentroResponsabilidadBean = new DualListModel<>();
        }
        return dualCentroResponsabilidadBean;
    }

    public void setDualCentroResponsabilidadBean(DualListModel<CentroResponsabilidadBean> dualCentroResponsabilidadBean) {
        this.dualCentroResponsabilidadBean = dualCentroResponsabilidadBean;
    }

    public List<CentroResponsabilidadBean> getListaCentroResponsabilidadBeanB() {
        if (listaCentroResponsabilidadBeanB == null) {
            listaCentroResponsabilidadBeanB = new ArrayList<>();
        }
        return listaCentroResponsabilidadBeanB;
    }

    public void setListaCentroResponsabilidadBeanB(List<CentroResponsabilidadBean> listaCentroResponsabilidadBeanB) {
        this.listaCentroResponsabilidadBeanB = listaCentroResponsabilidadBeanB;
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

    public Boolean getOperacion4() {
        return operacion4;
    }

    public void setOperacion4(Boolean operacion4) {
        this.operacion4 = operacion4;
    }

//    public Integer getTipoEstRend() {
//        return tipoEstRend;
//    }
//
//    public void setTipoEstRend(Integer tipoEstRend) {
//        this.tipoEstRend = tipoEstRend;
//    }
    public List<CodigoBean> getListaTipoDistribucionCRBean() {
        if (listaTipoDistribucionCRBean == null) {
            listaTipoDistribucionCRBean = new ArrayList();
        }
        return listaTipoDistribucionCRBean;
    }

    public void setListaTipoDistribucionCRBean(List<CodigoBean> listaTipoDistribucionCRBean) {
        this.listaTipoDistribucionCRBean = listaTipoDistribucionCRBean;
    }

    public Boolean getFlgDscto() {
        return flgDscto;
    }

    public void setFlgDscto(Boolean flgDscto) {
        this.flgDscto = flgDscto;
    }

    public String getFlgRenderCr() {
        return flgRenderCr;
    }

    public void setFlgRenderCr(String flgRenderCr) {
        this.flgRenderCr = flgRenderCr;
    }

    public Boolean getFlgCr() {
        return flgCr;
    }

    public void setFlgCr(Boolean flgCr) {
        this.flgCr = flgCr;
    }

}
