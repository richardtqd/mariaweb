package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.bean.ActividadCrBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.IndicadorBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.PresupuestoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadCrService;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.IndicadorService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTCalculadoraCR;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class ActividadMB extends BaseMB implements Serializable {

    @PostConstruct
    public void ActividadMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            //CARGANDO UNIDAD ORGANICA
            UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            listaUnidadOrganica = unidadOrganicaService.obtenerTodos();
            //CARGANDO INDICADORES
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            listaIndicadorBean = indicadorService.obtenerPorTipoUso(MaristaConstantes.COD_TIP_USO_IND_PO);
            //LISTA DE TIPOS DE CODIGOS
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoUsoIndicador();
            listaTipoUsoIndicador = codigoService.obtenerPorTipoOpe(new TipoCodigoBean(MaristaConstantes.TIP_UsoIndicador));
            getListaTipoValor();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));
            getListaTipoValor();
            listaTipoValor = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_VALOR));
            //LISTA DE CUENTAS CONTABLES
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            getListaPlanContableBean();
            listaPlanContableBean = planContableService.obtenerPlanContable();
            //OBTENIENDO TIPO DE SOLICITUD
            getListaTipoDistribucionCRBean();
            listaTipoDistribucionCRBean = codigoService.obtenerPorTipo(new TipoCodigoBean("TipoDistribucionCR"));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //DATOS DE SESION
    private UsuarioBean usuarioBean;

    //CLASES
    private IndicadorBean indicadorBean;
    private ActividadBean actividadBean;
    private DetActividadBean detActividadBean;
    private ActividadCrBean actividadCrBean;
    private IndicadorBean indicadorFiltroBean;
    private PresupuestoBean presupuestoBean;
    private PresupuestoBean presupuestoFiltroBean;
    private PresupuestoBean presupuesto; //BEAN DE INSERCION

    //LISTAS
    private List<ActividadBean> listaActividadBean;
    private List<DetActividadBean> listaDetActividadBean;
    private List<ActividadCrBean> listaActividadCrBean;
    private List<UnidadOrganicaBean> listaUnidadOrganica;
    private List<IndicadorBean> listaIndicadorBean;
    private List<CodigoBean> listaTipoUsoIndicador;
    private List<CodigoBean> listaTipoValor;
    private List<PlanContableBean> listaPlanContableBean;
    private Map<String, Integer> listaMesesExpMap;
    private List<CentroResponsabilidadBean> listCR;
    private List<CentroResponsabilidadBean> listCRB;
    private DualListModel<CentroResponsabilidadBean> dualDetActCr;
    private List<CodigoBean> listaTipoDistribucionCRBean;
    private List<PresupuestoBean> listaPresupuestoBean;
    private List<CodigoBean> listaTipoPeriodo;

    //VARIABLES DE AYUDA
    private BigDecimal montoActividad;
    private BigDecimal montoDetActividad;
    private Boolean flgPeriodo;

    //METODOS
    public void cargarDatos() {
        try {
            //SETEANDO VALORES ACTIVIDAD
            getActividadBean();
            getActividadBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getActividadBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            //SETEANDO VALORES DET-ACTIVIDAD
            getDetActividadBean();
            getDetActividadBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //SETEANDO VALORES DE ACTIVIDAD-CR
            getActividadCrBean();
            getActividadCrBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //OBTENER ACTIVIDAD
//            obtenerActividad();
            //LISTA DE MESES
            listaMesesForSup();
            //OBTENIENDO PRESUPUESTO
            getPresupuestoFiltroBean();
            getPresupuestoFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPresupuestoFiltroBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            //PRESUPUESTO INSERT
            getPresupuesto();
            getPresupuesto().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
            //OBTENIENDO DATOS DE PERIODO
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoPeriodo();
            listaTipoPeriodo = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_PERIODO));
            //OBTENIENDO FILTRO DE PERIODO
            setFlgPeriodo(false);
            obtenerFiltroPeriodo();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //METODOS BÁSICOS
    public void obtenerFiltroPeriodo() {
        try {
            if (detActividadBean.getTipoPeriodo().getIdCodigo() != null) {
                if (detActividadBean.getTipoPeriodo().getCodigo().equals(MaristaConstantes.COD_LAB_PERIODO_MENSUAL)) {
                    setFlgPeriodo(true);
                    detActividadBean.setMes(null);
                    detActividadBean.getTipoPeriodo().setCodigo(detActividadBean.getTipoPeriodo().getCodigo());
                } else if (detActividadBean.getTipoPeriodo().getCodigo().equals(MaristaConstantes.COD_LAB_PERIODO_ANUAL)) {
                    setFlgPeriodo(false);
                    detActividadBean.setMes(1);
                    detActividadBean.getTipoPeriodo().setCodigo(detActividadBean.getTipoPeriodo().getCodigo());
                } else if (detActividadBean.getTipoPeriodo().getCodigo().equals(MaristaConstantes.COD_LAB_PERIODO_BIMESTRAL)) {
                    setFlgPeriodo(false);
                    detActividadBean.setMes(2);
                    detActividadBean.getTipoPeriodo().setCodigo(detActividadBean.getTipoPeriodo().getCodigo());
                } else if (detActividadBean.getTipoPeriodo().getCodigo().equals(MaristaConstantes.COD_LAB_PERIODO_SEMESTRAL)) {
                    setFlgPeriodo(false);
                    detActividadBean.setMes(6);
                    detActividadBean.getTipoPeriodo().setCodigo(detActividadBean.getTipoPeriodo().getCodigo());
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
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
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerDistribucion() {
        try {
            actividadCrBean.setCodDistribucion(actividadCrBean.getCodDistribucion());
            System.out.println(">>>" + actividadCrBean.getCodDistribucion());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void distribuir() {
        try {
            List<CentroResponsabilidadBean> listaCentroResponsabilidad = new ArrayList<>();
            listaCentroResponsabilidad = dualDetActCr.getTarget();
            if (listaCentroResponsabilidad != null) {
                if (!listaCentroResponsabilidad.isEmpty()) {
                    listaActividadCrBean = new ArrayList<>();
                    //2.-Invocar calculadora
                    for (CodigoBean tipoDistribucionCRBean : listaTipoDistribucionCRBean) {
                        if (actividadCrBean.getCodDistribucion().toString().equals(tipoDistribucionCRBean.getIdCodigo().toString())) {
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PON)) {
                                new GLTCalculadoraCR().calcularPorPonderacion(listaCentroResponsabilidad, detActividadBean.getImporte().doubleValue());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_DIV)) {
                                new GLTCalculadoraCR().calcularPorDivision(listaCentroResponsabilidad, detActividadBean.getImporte().doubleValue());
                                break;
                            }
                            if (tipoDistribucionCRBean.getCodigo().equals(MaristaConstantes.TIP_DIST_PER)) {
                                new GLTCalculadoraCR().calcularPorPersonalizacion(listaCentroResponsabilidad);
                                break;
                            }
                        }
                    }
                    //3.-Crear Lista Respuesta
                    List<ActividadCrBean> listaDetSolicitudCajaChCRBean = new ArrayList<>();
                    for (CentroResponsabilidadBean centroResponsabilidadBean : listaCentroResponsabilidad) {
                        //SETENADO DET EN ACTIVIDAD CR
                        ActividadCrBean actividadCrBean = new ActividadCrBean();
                        actividadCrBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        actividadCrBean.setCreaPor(usuarioBean.getUsuario());
                        actividadCrBean.setIdActividad(detActividadBean.getActividadBean().getIdActividad());
                        actividadCrBean.setAnio(detActividadBean.getAnio());
                        actividadCrBean.getDetActividadBean().setIdDetActividad(detActividadBean.getIdDetActividad());
                        actividadCrBean.getPlanContableBean().setCuenta(detActividadBean.getPlanContableBean().getCuenta());
                        actividadCrBean.getUnidadOrganicaBean().setIdUniOrg(detActividadBean.getIdUniOrg());
                        actividadCrBean.setCentroResponsabilidadBean(centroResponsabilidadBean);
                        actividadCrBean.setValorD(centroResponsabilidadBean.getMontoDistribucion());
                        listaActividadCrBean.add(actividadCrBean);
                        System.out.println(">>> lista llena" + listaActividadCrBean.size());
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                    listaActividadCrBean = new ArrayList<>();
                }
            } else {
                new MensajePrime().addInformativeMessagePer("msjListaCRNull");
                listaActividadCrBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //METODOS DE INDICADOR
    public void onRowEditIndicador(RowEditEvent event) {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            indicadorFiltroBean = new IndicadorBean();
            indicadorFiltroBean.setIdIndicador(((IndicadorBean) event.getObject()).getIdIndicador());
            indicadorFiltroBean.setNombre(((IndicadorBean) event.getObject()).getNombre());
            indicadorFiltroBean.getCodigoTiPoIndicador().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTiPoIndicador().getIdCodigo());
            indicadorFiltroBean.setFormula(((IndicadorBean) event.getObject()).getFormula());
            indicadorFiltroBean.setModiPor(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            indicadorFiltroBean.getCodigoTiPoUso().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTiPoUso().getIdCodigo());
            indicadorFiltroBean.getCodigoTipoValor().setIdCodigo(((IndicadorBean) event.getObject()).getCodigoTipoValor().getIdCodigo());
            indicadorFiltroBean.setMeta(((IndicadorBean) event.getObject()).getMeta());
            indicadorService.actualizar(indicadorFiltroBean);
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
            }
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
            }
            if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
            }
            indicadorFiltroBean = new IndicadorBean();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarIndicador() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            listaIndicadorBean = indicadorService.obtenerPorTipoUso(indicadorBean.getCodigoTiPoUso().getIdCodigo());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerIndicador(Object object) {
        try {
            indicadorFiltroBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorFiltroBean = indicadorService.buscarPorId(indicadorFiltroBean.getIdIndicador());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerIndicador(Object object) {
        try {
            indicadorFiltroBean = (IndicadorBean) object;
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            indicadorFiltroBean = indicadorService.buscarPorId(indicadorFiltroBean.getIdIndicador());
            actividadBean.setIndicadorBean(indicadorFiltroBean);
            actividadBean.getIndicadorBean().setIdIndicador(indicadorFiltroBean.getIdIndicador());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCodigoIndicador() {
        try {
            IndicadorService indicadorService = BeanFactory.getIndicadorService();
            String codigo = "IND-";
            if (indicadorBean == null) {
                indicadorBean = new IndicadorBean();
            }
            String cod = indicadorService.obtenerCodigo(indicadorBean.getCodigo());
            System.out.println("ind. cod" + cod);
            Integer var = Integer.parseInt(cod) + 1;
            String base = String.format("%03d", var);
            indicadorBean.setCodigo(codigo.concat(base));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarIndicador() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorService.eliminar(indicadorFiltroBean.getIdIndicador());
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(MaristaConstantes.COD_TIP_USO_IND_PE)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(MaristaConstantes.COD_TIP_USO_IND_PE);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(MaristaConstantes.COD_TIP_USO_IND_PO)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(MaristaConstantes.COD_TIP_USO_IND_PO);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(MaristaConstantes.COD_TIP_USO_IND_AM)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(MaristaConstantes.COD_TIP_USO_IND_AM);
                }
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void guardarIndicador() {
        try {
            if (indicadorBean.getIdIndicador() == null) {
                insertarIndicador();
            } else {
                modificarIndicador();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarIndicadorBean() {
        try {
            indicadorBean = new IndicadorBean();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
                String date = formato.format(new Date());
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setCreaPor(user.getUsuario());
                indicadorBean.setFechaCrea(formato.parse(date));
                indicadorService.insertar(indicadorBean);
                CodigoBean codigoUso = new CodigoBean();
                CodigoService codigoService = BeanFactory.getCodigoService();
                System.out.println(indicadorBean.getCodigoTiPoUso().getIdCodigo());
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                    System.out.println(14401);
                }
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14402);
                }
                if (indicadorBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14403);
                }
                limpiarIndicadorBean();
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarIndicador() {
        String pagina = null;
        try {
            UsuarioBean user = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                IndicadorService indicadorService = BeanFactory.getIndicadorService();
                indicadorBean.setModiPor(user.getUsuario());
                indicadorService.actualizar(indicadorBean);
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14401)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14401);
                    System.out.println(14401);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14402)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14402);
                }
                if (indicadorFiltroBean.getCodigoTiPoUso().getIdCodigo().equals(14403)) {
                    listaIndicadorBean = indicadorService.obtenerPorTipoUso(14402);
                    System.out.println(14403);
                }
                limpiarIndicadorBean();
                obtenerCodigoIndicador();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            pagina = null;
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //METODOS DE ACTIVIDAD
    public void obtenerActividad() {
        try {
            ActividadService actividadService = BeanFactory.getActividadService();
            listaActividadBean = actividadService.obtenerPorUnidadNegocio(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void nuevaActividad() {
        try {
            actividadBean = new ActividadBean();
            getActividadBean();
            getActividadBean().getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getActividadBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarActividad() {
        try {
            if (actividadBean.getIdActividad() != null) {
                modificarActividad();
            } else {
                insertarActividad();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarActividad() {
        String pagina = null;
        Integer id = 0;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
                actividadBean.setCreaPor(beanUsuarioSesion.getUsuario());
                actividadService.insertarActividad(actividadBean);
                nuevaActividad();
                obtenerPresupuestoId(presupuestoBean, 1);
//                obtenerActividad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public String modificarActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ActividadService actividadService = BeanFactory.getActividadService();
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                actividadBean.setModiPor(beanUsuarioSesion.getUsuario());
                actividadService.actualizarActividad(actividadBean);
                nuevaActividad();
                obtenerPresupuestoId(presupuestoBean, 1);
//                obtenerActividad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void obtenerActividadId(Object object, Integer valor) {
        try {
            ActividadBean act = (ActividadBean) object;
            act.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ActividadService actividadService = BeanFactory.getActividadService();
            actividadBean = actividadService.obtenerObjPorId(act);
            actividadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            montoActividad = actividadService.obtenerImporteActividad(actividadBean);
            if (valor.equals(1)) {
                detActividadBean = new DetActividadBean();
                detActividadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                detActividadBean.getUnidadOrganicaBean().setIdUniOrg(actividadBean.getUnidadOrganicaBean().getIdUniOrg());
                detActividadBean.setIdUniOrg(actividadBean.getUnidadOrganicaBean().getIdUniOrg());
                detActividadBean.getActividadBean().setIdActividad(actividadBean.getIdActividad());
                detActividadBean.setAnio(actividadBean.getAnio());
                detActividadBean.getPlanContableBean().setCuenta(presupuestoBean.getPlanContableBean().getCuenta());
                obtenerDetPoActividad();
                DetActividadService detActividadService = BeanFactory.getDetActividadService();
                montoDetActividad = detActividadService.obtenerImporteDetActividad(detActividadBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String eliminarActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                ActividadService actividadService = BeanFactory.getActividadService();
                actividadService.eliminarActividad(actividadBean.getIdActividad());
                obtenerPresupuestoId(presupuestoBean, 1);
//                obtenerActividad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    //METODOS DE DETACTIVIDAD
    public void obtenerDetPoActividad() {
        try {
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            listaDetActividadBean = detActividadService.obtenerDetaPorActividad(detActividadBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void guardarDetActividad() {
        try {
            getDetActividadBean();
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            montoDetActividad = detActividadService.obtenerImporteDetActividad(detActividadBean);
            montoDetActividad = montoDetActividad.add(getDetActividadBean().getImporte());
            Integer res = montoDetActividad.compareTo(montoActividad);
            if (detActividadBean.getIdDetActividad() != null) {
                if (res == 1) {
                    new MensajePrime().errorMontoDetActividad();
                } else if (res == -1) {
                    modificarDetActividad();
                } else if (res == 0) {
                    new MensajePrime().errorMontoDetActividad();
                }
            } else {
                if (res == 1) {
                    new MensajePrime().errorMontoDetActividad();
                } else if (res == -1) {
                    insertarDetaActividad();
                } else if (res == 0) {
                    new MensajePrime().errorMontoDetActividad();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarDetaActividad() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                if (detActividadBean.getImporte().intValue() != 0) {
                    DetActividadService detActividadService = BeanFactory.getDetActividadService();
                    detActividadBean.setCreaPor(usuarioBean.getUsuario());
                    detActividadService.insertarDetActividad(detActividadBean);
                    //EJECUTANDO PRESUPUESTO
                    PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                    presupuestoService.execPresupuesto(getPresupuestoBean());
                    //=====================
                    obtenerDetPoActividad();
                    limpiarDetActividad();
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else {
                    limpiarDetActividad();
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void modificarDetActividad() {
        try {
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            if (detActividadBean.getImporte().intValue() != 0) {
                detActividadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                detActividadBean.setModiPor(usuarioBean.getUsuario());
                detActividadService.modificarDetActividad(detActividadBean);
                //EJECUTANDO PRESUPUESTO
                PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                presupuestoService.execPresupuesto(getPresupuestoBean());
                //=====================
                obtenerDetPoActividad();
                limpiarDetActividad();
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                limpiarDetActividad();
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerDetActividadId(Object obj, Integer dato) {
        try {
            detActividadBean = (DetActividadBean) obj;
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            detActividadBean = detActividadService.obtenerPorId(detActividadBean.getIdDetActividad());
            if (dato.equals(1)) {
                detActividadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                //SETENADO DET EN ACTIVIDAD CR
                actividadCrBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                actividadCrBean.setIdActividad(detActividadBean.getActividadBean().getIdActividad());
                actividadCrBean.setAnio(detActividadBean.getAnio());
                actividadCrBean.getDetActividadBean().setIdDetActividad(detActividadBean.getIdDetActividad());
                actividadCrBean.getPlanContableBean().setCuenta(detActividadBean.getPlanContableBean().getCuenta());
                actividadCrBean.getUnidadOrganicaBean().setIdUniOrg(detActividadBean.getIdUniOrg());
                CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                listCR = centroResponsabilidadService.obtenerCrOutDetAct(detActividadBean);
                listCRB = centroResponsabilidadService.obtenerCrInDetAct(detActividadBean);
                dualDetActCr = new DualListModel<>(listCR, listCRB);
                obtenerActividadCr(actividadCrBean);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarDetActividad() {
        try {
            detActividadBean.setIdDetActividad(null);
            detActividadBean.setImporte(null);
            detActividadBean.setDescripcion(null);
            detActividadBean.setMes(null);
            detActividadBean.getPlanContableBean().setCuenta(presupuestoBean.getPlanContableBean().getCuenta());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void eliminarDetActividad() {
        try {
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            detActividadService.eliminarDetActividad(detActividadBean.getIdDetActividad());
            //EJECUTANDO PRESUPUESTO
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            getPresupuestoBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuestoService.execPresupuesto(getPresupuestoBean());
            //=====================
            obtenerDetPoActividad();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //METODOS DE ACTIVIDAD CR
    public void obtenerActividadCr(ActividadCrBean actividadCrBean) {
        try {
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            listaActividadCrBean = actividadCrService.obtenerDetActividadCrId(actividadCrBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String insertarActCr() {
        String pagina = null;
        try {
            if (pagina == null) {
                if (!listaActividadCrBean.isEmpty()) {
                    CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
                    ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                    actividadCrBean.setCreaPor(usuarioBean.getUsuario());
                    //actividadCrService.insertarDetaActividadCr(actividadCrBean, dualDetActCr.getTarget()); //dualDetActCr.getTarget()
                    actividadCrService.insertarDetaActividadCr2(actividadCrBean, listaActividadCrBean); //dualDetActCr.getTarget()
                    listCR = centroResponsabilidadService.obtenerCrOutDetAct(detActividadBean);
                    listCRB = centroResponsabilidadService.obtenerCrInDetAct(detActividadBean);
                    dualDetActCr = new DualListModel<>(listCR, listCRB);
                    obtenerActividadCr(actividadCrBean);
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pagina;
    }

    public void asignarActCr() {
        try {
            //Obtener Lista_Dual_CR
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            for (int i = 0; i < dualDetActCr.getTarget().size(); i++) {
                CentroResponsabilidadBean cr = new CentroResponsabilidadBean();
                Object objeto = dualDetActCr.getTarget().get(i);
                cr = centroResponsabilidadService.obtenerCRPorId(new Integer(objeto.toString()));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //METODOS DE PRESUPUESTO
    public void buscarPresupuesto() {
        try {
            Integer res = 0;
            if (getPresupuestoFiltroBean().getAnio() != null) {
                getPresupuestoFiltroBean().setAnio(getPresupuestoFiltroBean().getAnio());
                res = 1;
            }
            if (getPresupuestoBean().getPlanContableBean().getCuenta() != null) {
                getPresupuestoFiltroBean().getPlanContableBean().setCuenta(getPresupuestoFiltroBean().getPlanContableBean().getCuenta());
                res = 1;
            }
            if (res == 1) {
                PresupuestoService presupuestoSeviService = BeanFactory.getPresupuestoService();
                listaPresupuestoBean = presupuestoSeviService.filtrarPresupuesto(getPresupuestoFiltroBean());
                if (listaPresupuestoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPresupuestoBean = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPresupuestoBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarPresupuesto() {
        try {
            presupuestoBean = new PresupuestoBean();
            listaPresupuestoBean = new ArrayList<>();
            presupuestoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuestoBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerPresupuestoId(Object obj, Integer dato) {
        try {
            presupuestoBean = (PresupuestoBean) obj;
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoBean = presupuestoService.obtenerPorId(presupuestoBean.getIdPresupuesto());
            if (dato.equals(1)) {
                actividadBean = new ActividadBean();
                actividadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                actividadBean.setAnio(presupuestoBean.getAnio());
                detActividadBean.getPlanContableBean().setCuenta(presupuestoBean.getPlanContableBean().getCuenta());
                ActividadService actividadService = BeanFactory.getActividadService();
                listaActividadBean = actividadService.obtenerObjPorAnio(actividadBean);
            } else if (dato.equals(2)) {
                presupuesto = presupuestoService.obtenerPorId(presupuestoBean.getIdPresupuesto());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void grabarPresupuesto() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            PresupuestoBean pres = new PresupuestoBean();
            pres.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            pres.getPlanContableBean().setCuenta(presupuesto.getPlanContableBean().getCuenta());
            pres = presupuestoService.obtenerPorCuenta(presupuesto);
            if (pres == null) {
                if (presupuesto.getIdPresupuesto() != null) {
                    modificarPresupuesto();
                } else {
                    insertarPresupuesto();
                }
            } else {
                new MensajePrime().errorPresupuestoCuenta();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void insertarPresupuesto() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuesto.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setCreaPor(usuarioBean.getUsuario());
            presupuestoService.insertarPresupesto(presupuesto);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarPresupuesto() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuesto.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            presupuesto.setModiPor(usuarioBean.getUsuario());
            presupuestoService.modificarPresupesto(presupuesto);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void eliminarPresupuesto() {
        try {
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoService.eliminarPresupestoNuevo(presupuestoBean);
            buscarPresupuesto();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarPresupuestoInsert() {
        try {
            presupuesto = new PresupuestoBean();
            presupuesto.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco) + 1);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public ActividadBean getActividadBean() {
        if (actividadBean == null) {
            actividadBean = new ActividadBean();
        }
        return actividadBean;
    }

    public void setActividadBean(ActividadBean actividadBean) {
        this.actividadBean = actividadBean;
    }

    public DetActividadBean getDetActividadBean() {
        if (detActividadBean == null) {
            detActividadBean = new DetActividadBean();
        }
        return detActividadBean;
    }

    public void setDetActividadBean(DetActividadBean detActividadBean) {
        this.detActividadBean = detActividadBean;
    }

    public List<ActividadBean> getListaActividadBean() {
        if (listaActividadBean == null) {
            listaActividadBean = new ArrayList<>();
        }
        return listaActividadBean;
    }

    public void setListaActividadBean(List<ActividadBean> listaActividadBean) {
        this.listaActividadBean = listaActividadBean;
    }

    public List<DetActividadBean> getListaDetActividadBean() {
        if (listaDetActividadBean == null) {
            listaDetActividadBean = new ArrayList<>();
        }
        return listaDetActividadBean;
    }

    public void setListaDetActividadBean(List<DetActividadBean> listaDetActividadBean) {
        this.listaDetActividadBean = listaDetActividadBean;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganica() {
        if (listaUnidadOrganica == null) {
            listaUnidadOrganica = new ArrayList<>();
        }
        return listaUnidadOrganica;
    }

    public void setListaUnidadOrganica(List<UnidadOrganicaBean> listaUnidadOrganica) {
        this.listaUnidadOrganica = listaUnidadOrganica;
    }

    public List<IndicadorBean> getListaIndicadorBean() {
        if (listaIndicadorBean == null) {
            listaIndicadorBean = new ArrayList<>();
        }
        return listaIndicadorBean;
    }

    public void setListaIndicadorBean(List<IndicadorBean> listaIndicadorBean) {
        this.listaIndicadorBean = listaIndicadorBean;
    }

    public IndicadorBean getIndicadorFiltroBean() {
        if (indicadorFiltroBean == null) {
            indicadorFiltroBean = new IndicadorBean();
        }
        return indicadorFiltroBean;
    }

    public void setIndicadorFiltroBean(IndicadorBean indicadorFiltroBean) {
        this.indicadorFiltroBean = indicadorFiltroBean;
    }

    public List<CodigoBean> getListaTipoUsoIndicador() {
        if (listaTipoUsoIndicador == null) {
            listaTipoUsoIndicador = new ArrayList<>();
        }
        return listaTipoUsoIndicador;
    }

    public void setListaTipoUsoIndicador(List<CodigoBean> listaTipoUsoIndicador) {
        this.listaTipoUsoIndicador = listaTipoUsoIndicador;
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

    public IndicadorBean getIndicadorBean() {
        if (indicadorBean == null) {
            indicadorBean = new IndicadorBean();
        }
        return indicadorBean;
    }

    public void setIndicadorBean(IndicadorBean indicadorBean) {
        this.indicadorBean = indicadorBean;
    }

    public List<PlanContableBean> getListaPlanContableBean() {
        if (listaPlanContableBean == null) {
            listaPlanContableBean = new ArrayList<>();
        }
        return listaPlanContableBean;
    }

    public void setListaPlanContableBean(List<PlanContableBean> listaPlanContableBean) {
        this.listaPlanContableBean = listaPlanContableBean;
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

    public ActividadCrBean getActividadCrBean() {
        if (actividadCrBean == null) {
            actividadCrBean = new ActividadCrBean();
        }
        return actividadCrBean;
    }

    public void setActividadCrBean(ActividadCrBean actividadCrBean) {
        this.actividadCrBean = actividadCrBean;
    }

    public List<ActividadCrBean> getListaActividadCrBean() {
        if (listaActividadCrBean == null) {
            listaActividadCrBean = new ArrayList<>();
        }
        return listaActividadCrBean;
    }

    public void setListaActividadCrBean(List<ActividadCrBean> listaActividadCrBean) {
        this.listaActividadCrBean = listaActividadCrBean;
    }

    public List<CentroResponsabilidadBean> getListCR() {
        if (listCR == null) {
            listCR = new ArrayList<>();
        }
        return listCR;
    }

    public void setListCR(List<CentroResponsabilidadBean> listCR) {
        this.listCR = listCR;
    }

    public List<CentroResponsabilidadBean> getListCRB() {
        if (listCRB == null) {
            listCRB = new ArrayList<>();
        }
        return listCRB;
    }

    public void setListCRB(List<CentroResponsabilidadBean> listCRB) {
        this.listCRB = listCRB;
    }

    public DualListModel<CentroResponsabilidadBean> getDualDetActCr() {
        if (dualDetActCr == null) {
            dualDetActCr = new DualListModel<>();
        }
        return dualDetActCr;
    }

    public void setDualDetActCr(DualListModel<CentroResponsabilidadBean> dualDetActCr) {
        this.dualDetActCr = dualDetActCr;
    }

    public List<CodigoBean> getListaTipoDistribucionCRBean() {
        if (listaTipoDistribucionCRBean == null) {
            listaTipoDistribucionCRBean = new ArrayList<>();
        }
        return listaTipoDistribucionCRBean;
    }

    public void setListaTipoDistribucionCRBean(List<CodigoBean> listaTipoDistribucionCRBean) {
        this.listaTipoDistribucionCRBean = listaTipoDistribucionCRBean;
    }

    public BigDecimal getMontoActividad() {
        return montoActividad;
    }

    public void setMontoActividad(BigDecimal montoActividad) {
        this.montoActividad = montoActividad;
    }

    public BigDecimal getMontoDetActividad() {
        return montoDetActividad;
    }

    public void setMontoDetActividad(BigDecimal montoDetActividad) {
        this.montoDetActividad = montoDetActividad;
    }

    public PresupuestoBean getPresupuestoBean() {
        if (presupuestoBean == null) {
            presupuestoBean = new PresupuestoBean();
        }
        return presupuestoBean;
    }

    public void setPresupuestoBean(PresupuestoBean presupuestoBean) {
        this.presupuestoBean = presupuestoBean;
    }

    public List<PresupuestoBean> getListaPresupuestoBean() {
        if (listaPresupuestoBean == null) {
            listaPresupuestoBean = new ArrayList<>();
        }
        return listaPresupuestoBean;
    }

    public void setListaPresupuestoBean(List<PresupuestoBean> listaPresupuestoBean) {
        this.listaPresupuestoBean = listaPresupuestoBean;
    }

    public PresupuestoBean getPresupuestoFiltroBean() {
        if (presupuestoFiltroBean == null) {
            presupuestoFiltroBean = new PresupuestoBean();
        }
        return presupuestoFiltroBean;
    }

    public void setPresupuestoFiltroBean(PresupuestoBean presupuestoFiltroBean) {
        this.presupuestoFiltroBean = presupuestoFiltroBean;
    }

    public PresupuestoBean getPresupuesto() {
        if (presupuesto == null) {
            presupuesto = new PresupuestoBean();
        }
        return presupuesto;
    }

    public void setPresupuesto(PresupuestoBean presupuesto) {
        this.presupuesto = presupuesto;
    }

    public List<CodigoBean> getListaTipoPeriodo() {
        if (listaTipoPeriodo == null) {
            listaTipoPeriodo = new ArrayList<>();
        }
        return listaTipoPeriodo;
    }

    public void setListaTipoPeriodo(List<CodigoBean> listaTipoPeriodo) {
        this.listaTipoPeriodo = listaTipoPeriodo;
    }

    public Boolean getFlgPeriodo() {
        return flgPeriodo;
    }

    public void setFlgPeriodo(Boolean flgPeriodo) {
        this.flgPeriodo = flgPeriodo;
    }

}
