package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.ConceptoUniNegBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ConciliaGradoRepBean;
import pe.marista.sigma.bean.reporte.ConciliaNivelRepBean;
import pe.marista.sigma.bean.reporte.ConciliaRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ConceptoUniNegService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajesBackEnd;

@ManagedBean
@ViewScoped
public class CtaCteMB2 extends BaseMB implements Serializable {

    @PostConstruct
    public void CtaCteMB2() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerListaMeses();
            getCuentasPorCobrarBean();
            selFil = 0;
            selFiltro();
            getCuentasPorCobrarBean();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getValConTodos();
            valConTodos = false;
            listaOrden();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //LISTAS DE CLASES
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<ConceptoBean> listaConceptoBean;
    private List<ConceptoUniNegBean> listaConceptoUniNegBean;
    private List<MesBean> listaMesAll;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;

    //CLASES
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private UsuarioBean usuarioLoginBean;
    private TipoConceptoBean tipoConceptoBean;
    private ConceptoBean conceptoBean;

    /* VARIABLES DE AYUDA */
    private Calendar fechaActual;
    private Double totSoles = (0.0);
    private Integer selFil;
    private Boolean flgConFec;
    private Boolean flgConAnM;
    private Boolean valConTodos;
    private Integer pagoBanco = MaristaConstantes.COD_LUGAR_BANCO;
    private Integer pagoAmbos = MaristaConstantes.COD_LUGAR_AMBOS;
    private Integer pagoCaja = MaristaConstantes.COD_COLEGIO;

    //PARA ORDER AL MODO QUE REQUIERAN 
    private Map<String, Integer> listaOrden;

    public void cargarDatos() {
        try {
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            totSoles = (0.0);
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            getListaTipoConceptoBean();
            listaTipoConceptoBean = tipoConceptoService.obtenerPorTipoProcesoBanco();
            getListaNivelAcademicoBean();
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerPorTipoFormacion(3);
            //SETEANDO PAGO BANCO
            getCuentasPorCobrarBean().getDocIngresoBean().getIdTipoLugarPago().setIdCodigo(getPagoBanco());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorGradoAcademico() {
        try {
            getListaGradoAcademicoBean();
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarConcilia() {
        try {
            Integer res = 0;
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (cuentasPorCobrarBean.getFechaInicio() != null) {
                cuentasPorCobrarBean.setFechaInicio(cuentasPorCobrarBean.getFechaInicio());
                res = 1;
            }
            if (cuentasPorCobrarBean.getFechaFin() != null) {
                cuentasPorCobrarBean.setFechaFin(cuentasPorCobrarBean.getFechaFin());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setIdEstudiante(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getCodigo() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getCodigo().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setCodigo(cuentasPorCobrarBean.getEstudianteBean().getCodigo());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().setNombreCompleto(cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
                res = 1;
            }
            if (cuentasPorCobrarBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto() != null) {
                cuentasPorCobrarBean.getConceptoBean().getTipoConceptoBean().setIdTipoConcepto(cuentasPorCobrarBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto());
                res = 1;
            }
            if (cuentasPorCobrarBean.getConceptoBean().getIdConcepto() != null) {
                cuentasPorCobrarBean.getConceptoBean().setIdConcepto(cuentasPorCobrarBean.getConceptoBean().getIdConcepto());
                res = 1;
            }
            if (cuentasPorCobrarBean.getMes() != null
                    && !cuentasPorCobrarBean.getMes().equals(0)) {
                cuentasPorCobrarBean.setMes(cuentasPorCobrarBean.getMes());
                res = 1;
            }
            if (cuentasPorCobrarBean.getAnio() != null
                    && !cuentasPorCobrarBean.getAnio().equals(0)) {
                cuentasPorCobrarBean.setAnio(cuentasPorCobrarBean.getAnio());
                res = 1;
            }
            if (cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null) {
                cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                res = 1;
            }
            if (cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico() != null) {
                cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().setIdGradoAcademico(cuentasPorCobrarBean.getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico());
                res = 1;
            }
            if (cuentasPorCobrarBean.getDocIngresoBean().getIdTipoLugarPago().getIdCodigo() != null) {
                cuentasPorCobrarBean.getDocIngresoBean().getIdTipoLugarPago().setIdCodigo(cuentasPorCobrarBean.getDocIngresoBean().getIdTipoLugarPago().getIdCodigo());
                res = 1;
            }
            if (cuentasPorCobrarBean.getOrden() != null) {
                cuentasPorCobrarBean.setOrden(cuentasPorCobrarBean.getOrden());
                res = 1;
            }
            if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            } else if (res == 1) {
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                if (cuentasPorCobrarBean.getDocIngresoBean().getIdTipoLugarPago().getIdCodigo().equals(MaristaConstantes.COD_LUGAR_AMBOS)) {
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.filtrarConciliaAmbos(cuentasPorCobrarBean);
                } else {
                    listaCuentasPorCobrarBean = cuentasPorCobrarService.filtrarConcilia(cuentasPorCobrarBean);
                }
                if (listaCuentasPorCobrarBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    listaCuentasPorCobrarBean.get(0).setFechaInicio(cuentasPorCobrarBean.getFechaInicio());
                    listaCuentasPorCobrarBean.get(0).setFechaFin(cuentasPorCobrarBean.getFechaFin());
                    listaCuentasPorCobrarBean.get(0).setOrden(cuentasPorCobrarBean.getOrden());
                    if (listaCuentasPorCobrarBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    } else {
                        totSoles = Double.parseDouble("0");
                        for (CuentasPorCobrarBean cta : listaCuentasPorCobrarBean) {
                            totSoles = totSoles + (Double.parseDouble(cta.getMontoPagado().toString()));
                        }
                        System.out.println(">>>" + listaCuentasPorCobrarBean.size() + " // " + totSoles);
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void listaOrden() {
        listaOrden = new LinkedHashMap<>();
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorNivelGradoSec", null), 1);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorFechaPagoAsc", null), 2);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorFechaPagoDesc", null), 3);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorNombreDesc", null), 4);
        listaOrden.put(MensajesBackEnd.getValueOfKey("etiquetaOrdenPorNroDoc", null), 5);
        listaOrden = Collections.unmodifiableMap(listaOrden);
    }

    public void limpiarConcilia() {
        try {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            cargarDatos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerConceptoPorTipo() {
        try {
            ConceptoUniNegService conceptoUniNegService = BeanFactory.getConceptoUniNegService();
            listaConceptoUniNegBean = conceptoUniNegService.obtenerConceptoUniNegPorTip(cuentasPorCobrarBean.getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
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

    public void selFiltro() {
        try {
            if (selFil.equals(0)) {
                flgConFec = true;
                flgConAnM = false;
                cuentasPorCobrarBean.setMes(0);
                cuentasPorCobrarBean.setAnio(0);
                cargarDatos();
            } else if (selFil.equals(1)) {
                flgConAnM = true;
                flgConFec = false;
                cuentasPorCobrarBean.setFechaInicio(null);
                cuentasPorCobrarBean.setFechaFin(null);
                cuentasPorCobrarBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarValAdmTodos() {
        try {
//            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            if (listaCuentasPorCobrarBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            } else if (!listaCuentasPorCobrarBean.isEmpty()) {
                if (valConTodos) {
                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                        cuenta.setEstadoConcilia(true);
                        cuenta.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        cuenta.setModiPor(usuarioLoginBean.getUsuario());
                        cuenta.setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                        cuentasPorCobrarService.modificarConcilia(cuenta);
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                } else if (!valConTodos) {
                    for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                        cuenta.setEstadoConcilia(false);
                        cuenta.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        cuenta.setModiPor(usuarioLoginBean.getUsuario());
                        cuenta.setIdCtasXCobrar(cuenta.getIdCtasXCobrar());
                        cuentasPorCobrarService.modificarConcilia(cuenta);
                    }
                    RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                }
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificarConcilia(Object object) {
        try {
//            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            CuentasPorCobrarBean cuentasPorCobrarBean = (CuentasPorCobrarBean) object;
            CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
            cuentasPorCobrarBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentasPorCobrarBean.setModiPor(usuarioLoginBean.getUsuario());
            cuentasPorCobrarBean.setEstadoConcilia(true);
            cuentasPorCobrarBean.setIdCtasXCobrar(cuentasPorCobrarBean.getIdCtasXCobrar());
            cuentasPorCobrarService.modificarConcilia(cuentasPorCobrarBean);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirReporte() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/reporteConciliados.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<Integer> lista = new ArrayList<Integer>();
                List<ConciliaRepBean> listaConciliaRepBean = new ArrayList<>();
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                for (CuentasPorCobrarBean cuenta : listaCuentasPorCobrarBean) {
                    lista.add(cuenta.getIdCtasXCobrar());
                }
                for (int j = 0; j < listaCuentasPorCobrarBean.size(); j++) {
                    if (listaCuentasPorCobrarBean.get(0).getOrden() == 1) {
                        listaConciliaRepBean = cuentasPorCobrarService.obtenerReporteConcilia(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, listaCuentasPorCobrarBean.get(0).getOrden(), listaCuentasPorCobrarBean.get(0).getFechaInicio(), listaCuentasPorCobrarBean.get(0).getFechaFin(), listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar());
                    } else if (listaCuentasPorCobrarBean.get(0).getOrden() == 2) {
                        listaConciliaRepBean = cuentasPorCobrarService.obtenerReporteConcilia2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, listaCuentasPorCobrarBean.get(0).getOrden(), listaCuentasPorCobrarBean.get(0).getFechaInicio(), listaCuentasPorCobrarBean.get(0).getFechaFin(), listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar());
                    } else if (listaCuentasPorCobrarBean.get(0).getOrden() == 3) {
                        listaConciliaRepBean = cuentasPorCobrarService.obtenerReporteConcilia3(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, listaCuentasPorCobrarBean.get(0).getOrden(), listaCuentasPorCobrarBean.get(0).getFechaInicio(), listaCuentasPorCobrarBean.get(0).getFechaFin(), listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar());
                    } else if (listaCuentasPorCobrarBean.get(0).getOrden() == 4) {
                        listaConciliaRepBean = cuentasPorCobrarService.obtenerReporteConcilia4(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, listaCuentasPorCobrarBean.get(0).getOrden(), listaCuentasPorCobrarBean.get(0).getFechaInicio(), listaCuentasPorCobrarBean.get(0).getFechaFin(), listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar());
                    } else if (listaCuentasPorCobrarBean.get(0).getOrden() == 5) {
                        listaConciliaRepBean = cuentasPorCobrarService.obtenerReporteConcilia5(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), lista, listaCuentasPorCobrarBean.get(0).getOrden(), listaCuentasPorCobrarBean.get(0).getFechaInicio(), listaCuentasPorCobrarBean.get(0).getFechaFin(), listaCuentasPorCobrarBean.get(j).getIdCtasXCobrar());
                    } else {
                        new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                        System.out.println("no hay resultados");
                    }
                }
//                listaConciliaRepBean = cuentasPorCobrarService.obtenerReportePagados(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),listaCuentasPorCobrarBean.get(0).getDocIngresoBean().getIdTipoLugarPago().getIdCodigo(),
//                   listaCuentasPorCobrarBean.get(0).getFechaInicio(),listaCuentasPorCobrarBean.get(0).getFechaFin(),listaCuentasPorCobrarBean.get(0).getConceptoBean().getIdConcepto(),listaCuentasPorCobrarBean.get(0).getConceptoBean().getTipoConceptoBean().getIdTipoConcepto(),
//                   listaCuentasPorCobrarBean.get(0).getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico(),listaCuentasPorCobrarBean.get(0).getDocIngresoBean().getNombreDiscente(),
//                   listaCuentasPorCobrarBean.get(0).getEstudianteBean().getCodigo(),listaCuentasPorCobrarBean.get(0).getEstudianteBean().getIdEstudiante(),listaCuentasPorCobrarBean.get(0).getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico(),listaCuentasPorCobrarBean.get(0).getOrden()); 
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConciliaRepBean);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                System.out.println("archivo: " + archivoJasper);
                System.out.println("ruta: " + ruta);
                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "inline; filename=ReportePagos" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
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

    public void imprimirReportePorNivelGrado(Integer dato) {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                CuentasPorCobrarService cuentasPorCobrarService = BeanFactory.getCuentasPorCobrarService();
                List<ConciliaNivelRepBean> listaConciliaNivelRepBean = new ArrayList<>();
                List<ConciliaGradoRepBean> listaConciliaGradoRepBean = new ArrayList<>();
                if (dato.equals(1)) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteConciliaNivel.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    listaConciliaNivelRepBean = cuentasPorCobrarService.obtenerReporteConciliaNivel(cuentasPorCobrarBean);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConciliaNivelRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    System.out.println("archivo: " + archivoJasper);
                    System.out.println("ruta: " + ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "inline; filename=EstadísticaPagos" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                    response.setHeader("Cache-Control", "cache, must-revalidate");
                    response.setHeader("Pragma", "public");
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                } else if (dato.equals(2)) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteConciliaGrado.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    listaConciliaGradoRepBean = cuentasPorCobrarService.obtenerReporteConciliaGrado(cuentasPorCobrarBean);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConciliaGradoRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    System.out.println("archivo: " + archivoJasper);
                    System.out.println("ruta: " + ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "inline; filename=EstadísticaPagos" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                    response.setHeader("Cache-Control", "cache, must-revalidate");
                    response.setHeader("Pragma", "public");
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                } else if (dato.equals(3)) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteConciliaNivel.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    listaConciliaNivelRepBean = cuentasPorCobrarService.obtenerReporteConciliaNivelTotal(cuentasPorCobrarBean);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConciliaNivelRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    System.out.println("archivo: " + archivoJasper);
                    System.out.println("ruta: " + ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "inline; filename=EstadísticaPagos" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                    response.setHeader("Cache-Control", "cache, must-revalidate");
                    response.setHeader("Pragma", "public");
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                } else if (dato.equals(4)) {
                    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                    String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteConciliaGrado.jasper");
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    listaConciliaGradoRepBean = cuentasPorCobrarService.obtenerReporteConciliaGradoTotal(cuentasPorCobrarBean);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaConciliaGradoRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    System.out.println("archivo: " + archivoJasper);
                    System.out.println("ruta: " + ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    response.setContentType("application/pdf");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "inline; filename=EstadísticaPagos" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                    response.setHeader("Cache-Control", "cache, must-revalidate");
                    response.setHeader("Pragma", "public");
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                }
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

    public void imprimirReportePorGrado() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {

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

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public Calendar getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Calendar fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Double getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(Double totSoles) {
        this.totSoles = totSoles;
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

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
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

    public List<ConceptoUniNegBean> getListaConceptoUniNegBean() {
        if (listaConceptoUniNegBean == null) {
            listaConceptoUniNegBean = new ArrayList<>();
        }
        return listaConceptoUniNegBean;
    }

    public void setListaConceptoUniNegBean(List<ConceptoUniNegBean> listaConceptoUniNegBean) {
        this.listaConceptoUniNegBean = listaConceptoUniNegBean;
    }

    public Integer getSelFil() {
        return selFil;
    }

    public void setSelFil(Integer selFil) {
        this.selFil = selFil;
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

    public Boolean getValConTodos() {
        return valConTodos;
    }

    public void setValConTodos(Boolean valConTodos) {
        this.valConTodos = valConTodos;
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

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public Integer getPagoBanco() {
        return pagoBanco;
    }

    public void setPagoBanco(Integer pagoBanco) {
        this.pagoBanco = pagoBanco;
    }

    public Integer getPagoCaja() {
        return pagoCaja;
    }

    public void setPagoCaja(Integer pagoCaja) {
        this.pagoCaja = pagoCaja;
    }

    public Map<String, Integer> getListaOrden() {
        return listaOrden;
    }

    public void setListaOrden(Map<String, Integer> listaOrden) {
        this.listaOrden = listaOrden;
    }

    public Integer getPagoAmbos() {
        return pagoAmbos;
    }

    public void setPagoAmbos(Integer pagoAmbos) {
        this.pagoAmbos = pagoAmbos;
    }
}
