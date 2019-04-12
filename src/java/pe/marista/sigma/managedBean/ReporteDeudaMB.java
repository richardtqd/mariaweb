package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.util.JRLoader;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ReciboPensionRepBean;
import pe.marista.sigma.bean.reporte.ReporteDeudaGeneralRepBean;
import pe.marista.sigma.bean.reporte.ReporteDeudasRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class ReporteDeudaMB implements Serializable {

    @PostConstruct
    public void reporteDeudaMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            cargarMeses();
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentasPorCobrarBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            Integer dato = 0;
            Float m = null;
            m = dato.floatValue();
            totalMonto = new BigDecimal(m);
            totalSubMonto = new BigDecimal(m);
            totalMora = new BigDecimal(m);

            getListaMesAll();
            obtenerListaMeses();
            setSelFil(1);
            selFiltro();
            getIdEstadoPagado();
            getIdEstadoPendiente();
            setIdEstadoPagado(MaristaConstantes.COD_STA_CTA_EST_PAG);
            setIdEstadoPendiente(MaristaConstantes.COD_STA_CTA_EST_PEN);
            this.flgVista = false;
            this.flgVistaGen = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private UsuarioBean beanUsuarioSesion;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private Map<String, Integer> listaMesesExpMap;
    private List<Integer> listaMeses;
    private List<ReporteDeudasRepBean> listaReporteDeudasRepBean;
    private List<ReporteDeudasRepBean> listaReporteDeudasRepGeneralBean;
    private BigDecimal totalSubMonto;
    private BigDecimal totalMora;
    private BigDecimal totalMonto;
    private List<MesBean> listaMesAll;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;

    //Variables de Ayuda
    private Integer selFil;
    private Boolean flgConFec;
    private Boolean flgConAnM;
    private Boolean flgHoraCorte;
    private Boolean flgVisionGen;

    private Integer idEstadoPagado;
    private Integer idEstadoPendiente;

    private Boolean flgVista;
    private Boolean flgVistaGen;

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
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

    public Map<String, Integer> getListaMesesExpMap() {
        return listaMesesExpMap;
    }

    public void setListaMesesExpMap(Map<String, Integer> listaMesesExpMap) {
        this.listaMesesExpMap = listaMesesExpMap;
    }

    public void cargarMeses() {
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

    public void filtrarDeudas() {
        try {
            listaReporteDeudasRepGeneralBean = new ArrayList<>();
            listaReporteDeudasRepBean = new ArrayList<>();
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (cuentasPorCobrarBean.getEstudianteBean().getCodigo() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getCodigo().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setCodigo(cuentasPorCobrarBean.getEstudianteBean().getCodigo());
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().setIdEstudiante(cuentasPorCobrarBean.getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto() != null
                    && !cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")) {
                cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().setNombreCompleto(cuentasPorCobrarBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
                res = 1;
            }
            if (cuentasPorCobrarBean.getFechaInicio() != null) {
                cuentasPorCobrarBean.setFechaInicio(cuentasPorCobrarBean.getFechaInicio());
                res = 1;
            }
            if (cuentasPorCobrarBean.getFechaFin() != null) {
                cuentasPorCobrarBean.setFechaFin(cuentasPorCobrarBean.getFechaFin());
                res = 1;
            }
            if (cuentasPorCobrarBean.getMes() != null) {
                cuentasPorCobrarBean.setMes(cuentasPorCobrarBean.getMes());
                res = 1;
            }
            if (cuentasPorCobrarBean.getAnio() != null) {
                cuentasPorCobrarBean.setAnio(cuentasPorCobrarBean.getAnio());
                res = 1;
            }
            if (cuentasPorCobrarBean.getHoraCorte() != null) {
                cuentasPorCobrarBean.setHoraCorte(cuentasPorCobrarBean.getHoraCorte());
                res = 1;
            }
            if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo() != null) {
                cuentasPorCobrarBean.getIdTipoStatusCtaCte().setIdCodigo(cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo());
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
            if (res == 1) {
                if (selFil.equals(2)) {
//                    listaReporteDeudasRepGeneralBean = new ArrayList<>();
                    flgVista = true;
                    flgVistaGen = false;
                    listaReporteDeudasRepBean = procesoBancoService.obtenerHoraCorte(cuentasPorCobrarBean);
                } else if (selFil.equals(1)) {
//                    listaReporteDeudasRepGeneralBean = new ArrayList<>();
                    flgVista = true;
                    flgVistaGen = false;
                    listaReporteDeudasRepBean = procesoBancoService.obtenerReporteDeuda(cuentasPorCobrarBean);
                } else if (selFil.equals(3)) {
//                    listaReporteDeudasRepBean = new ArrayList<>();
                    flgVista = false;
                    flgVistaGen = true;
                    listaReporteDeudasRepBean = procesoBancoService.obtenerReporteDeudaHor(cuentasPorCobrarBean);
                    Integer dato = 0;
                    Float m = null;
                    m = dato.floatValue();
                    totalMonto = new BigDecimal(m);
                    totalSubMonto = new BigDecimal(m);
                    totalMora = new BigDecimal(m);
                }
                if (listaReporteDeudasRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaReporteDeudasRepBean = new ArrayList<>();
                    Integer dato = 0;
                    Float m = null;
                    m = dato.floatValue();
                    totalMonto = new BigDecimal(m);
                    totalSubMonto = new BigDecimal(m);
                    totalMora = new BigDecimal(m);
                } else {
                    totalSubMonto = listaReporteDeudasRepBean.get(0).getSubMonto();
                    totalMora = listaReporteDeudasRepBean.get(0).getMontoMora();
                    totalMonto = listaReporteDeudasRepBean.get(0).getMontoTotal();
                }
//                if (listaReporteDeudasRepGeneralBean.isEmpty()) {
//                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
//                    listaReporteDeudasRepGeneralBean = new ArrayList<>();
//                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void agregarMeses() {
        try {
            listaMeses.add(cuentasPorCobrarBean.getMes());
            System.out.println(">>>>" + listaMeses.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltro() {
        try {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void generarImpresion() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                        getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                        getRequest()).getServletContext().getRealPath("/reportes/RepDeuda.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);

                List<ReporteDeudasRepBean> listaReporteDeudas = new ArrayList<>();
                for (ReporteDeudasRepBean deuda : listaReporteDeudasRepBean) {
                    ReporteDeudasRepBean reporteDeudasRepBean = new ReporteDeudasRepBean();
                    reporteDeudasRepBean.setAnio(deuda.getAnio());
                    reporteDeudasRepBean.setCodEstudiante(deuda.getCodEstudiante());
                    reporteDeudasRepBean.setConcepto(deuda.getConcepto());
                    reporteDeudasRepBean.setDeuda(deuda.getDeuda());
                    reporteDeudasRepBean.setGrado(deuda.getGrado());
                    reporteDeudasRepBean.setIdestudiante(deuda.getIdestudiante());
                    reporteDeudasRepBean.setMes(deuda.getMes());
                    reporteDeudasRepBean.setMonto(deuda.getMonto());
                    reporteDeudasRepBean.setMontoMora(deuda.getMontoMora());
                    reporteDeudasRepBean.setMontoTotal(deuda.getMontoTotal());
                    reporteDeudasRepBean.setMora(deuda.getMora());
                    reporteDeudasRepBean.setNombreFull(deuda.getNombreFull());
                    reporteDeudasRepBean.setSubMonto(deuda.getSubMonto());
                    listaReporteDeudas.add(reporteDeudasRepBean);
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaReporteDeudas);
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
                response.setHeader("Content-Disposition", "inline; filename=ReporteDeudas" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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

    public void obtenerListaMeses() {
        try {
            listaMesAll = new ArrayList<>();
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
            GregorianCalendar fechaActual = new GregorianCalendar();
            flgVista = false;
            flgVistaGen = false;
            if (selFil.equals(0)) {
                setSelFil(getSelFil());
                flgConFec = true;
                flgConAnM = false;
                flgHoraCorte = false;
                flgVisionGen = false;
                getCuentasPorCobrarBean().setMes(0);
                getCuentasPorCobrarBean().setAnio(0);
                getCuentasPorCobrarBean().setHoraCorte(null);
                getCuentasPorCobrarBean().setIdTipoStatusCtaCte(null);
                cargarDatos();
                listaReporteDeudasRepBean = new ArrayList<>();
            } else if (selFil.equals(1)) {
                setSelFil(getSelFil());
                flgConAnM = true;
                flgConFec = false;
                flgHoraCorte = false;
                flgVisionGen = false;
                getCuentasPorCobrarBean().setFechaInicio(null);
                getCuentasPorCobrarBean().setFechaFin(null);
                getCuentasPorCobrarBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
                getCuentasPorCobrarBean().setHoraCorte(null);
                getCuentasPorCobrarBean().setIdTipoStatusCtaCte(null);
                listaReporteDeudasRepBean = new ArrayList<>();
            } else if (selFil.equals(2)) {
                setSelFil(getSelFil());
                flgHoraCorte = true;
                flgConAnM = false;
                flgConFec = false;
                flgVisionGen = false;
                getCuentasPorCobrarBean().setFechaInicio(null);
                getCuentasPorCobrarBean().setFechaFin(null);
                getCuentasPorCobrarBean().setAnio(null);
                getCuentasPorCobrarBean().setHoraCorte(fechaActual.getTime());
                listaReporteDeudasRepBean = new ArrayList<>();
            } else if (selFil.equals(3)) {
                setSelFil(getSelFil());
                flgVisionGen = true;
                flgHoraCorte = false;
                flgConAnM = false;
                flgConFec = false;
                getCuentasPorCobrarBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
                getCuentasPorCobrarBean().setFechaInicio(null);
                getCuentasPorCobrarBean().setFechaFin(null);
                getCuentasPorCobrarBean().setHoraCorte(null);
                getCuentasPorCobrarBean().getIdTipoStatusCtaCte().setIdCodigo(null);
                listaReporteDeudasRepBean = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarDatos() {
        try {
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            cargarMeses();
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            Integer dato = 0;
            Float m = null;
            m = dato.floatValue();
            totalMonto = new BigDecimal(m);
            totalSubMonto = new BigDecimal(m);
            totalMora = new BigDecimal(m);
            getListaMesAll();
            obtenerListaMeses();
            getListaNivelAcademicoBean();
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerPorTipoFormacion(3);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

    public void imprimir() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                String titulo = "";
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = "";
                if (getSelFil().equals(1)) { //TITULO POR ANO Y MES
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDeudores.jasper");
                    titulo = "REPORTE DE DEUDORES POR AÑO Y MES";
                } else if (getSelFil().equals(2)) { //TITULO POR HORA DE CORTE
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDeudores.jasper");
                    DateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
                    if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo().equals(MaristaConstantes.COD_STA_CTA_EST_PAG)) {
                        titulo = "REPORTE DE PAGANTES POR HORA DE CORTE " + fecha.format(cuentasPorCobrarBean.getHoraCorte());
                    } else if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo().equals(MaristaConstantes.COD_STA_CTA_EST_PEN)) {
                        titulo = "REPORTE DE DEUDORES POR HORA DE CORTE " + fecha.format(cuentasPorCobrarBean.getHoraCorte());
                    }
                } else if (getSelFil().equals(3)) { //TITULO POR VISION GENERAL
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteDeudoresGeneral.jasper");
                    if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo().equals(MaristaConstantes.COD_STA_CTA_EST_PAG)) {
                        titulo = "REPORTE GENERAL DE PAGANTES";
                    } else if (cuentasPorCobrarBean.getIdTipoStatusCtaCte().getIdCodigo().equals(MaristaConstantes.COD_STA_CTA_EST_PEN)) {
                        titulo = "REPORTE GENERAL DE DEUDORES";
                    }
                }
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<ReporteDeudaGeneralRepBean> listaReporteDeudaGeneralRepBean = new ArrayList<>();
                if (getSelFil().equals(1) || getSelFil().equals(2)) { //REPORTE POR DEUDORES Y HORA DE CORTE
                    for (ReporteDeudasRepBean deuda : listaReporteDeudasRepBean) {
                        ReporteDeudaGeneralRepBean general = new ReporteDeudaGeneralRepBean();
                        general.setNombreUniNeg(deuda.getNombreUniNeg());
                        general.setRuc(deuda.getRuc());
                        general.setTitulo(titulo);
                        general.setAnio(deuda.getAnio().toString());
                        general.setCodEstudiante(deuda.getCodEstudiante());
                        general.setConcepto(deuda.getConcepto());
                        general.setDeuda(deuda.getDeuda().toString());
                        general.setGrado(deuda.getGrado());
                        general.setIdestudiante(deuda.getIdestudiante());
                        general.setMes(deuda.getMes());
                        general.setMonto(deuda.getMonto().toString());
                        general.setMontoMora(deuda.getMontoMora().toString());
                        general.setMontoTotal(deuda.getMontoTotal().toString());
                        general.setMora(deuda.getMora().toString());
                        general.setNombreFull(deuda.getNombreFull());
                        general.setSubMonto(deuda.getSubMonto().toString());
                        listaReporteDeudaGeneralRepBean.add(general);
                    }
                } else if (getSelFil().equals(3)) {
                    for (ReporteDeudasRepBean deudaGen : listaReporteDeudasRepBean) {
                        ReporteDeudaGeneralRepBean general = new ReporteDeudaGeneralRepBean();
                        general.setNombreUniNeg(deudaGen.getNombreUniNeg());
                        general.setRuc(deudaGen.getRuc());
                        general.setTitulo(titulo);
                        general.setAnio(deudaGen.getAnio().toString());
                        general.setCodEstudiante(deudaGen.getCodEstudiante());
                        general.setGrado(deudaGen.getGrado());
                        general.setIdestudiante(deudaGen.getIdestudiante());
                        general.setNombreFull(deudaGen.getNombreFull());
                        general.setMatricula(deudaGen.getMatricula().toString());
                        general.setMarzo(deudaGen.getMarzo().toString());
                        general.setAbril(deudaGen.getAbril().toString());
                        general.setMayo(deudaGen.getMayo().toString());
                        general.setJunio(deudaGen.getJunio().toString());
                        general.setJulio(deudaGen.getJulio().toString());
                        general.setAgosto(deudaGen.getAgosto().toString());
                        general.setSetiembre(deudaGen.getSetiembre().toString());
                        general.setOctubre(deudaGen.getOctubre().toString());
                        general.setNoviembre(deudaGen.getNoviembre().toString());
                        general.setDiciembre(deudaGen.getDiciembre().toString());
                        listaReporteDeudaGeneralRepBean.add(general);
                    }
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaReporteDeudaGeneralRepBean);
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
                response.setHeader("Content-Disposition", "inline; filename=ReporteDeudas" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                response.setHeader("Cache-Control", "cache, must-revalidate");
                response.setHeader("Pragma", "public");
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<Integer> getListaMeses() {
        if (listaMeses == null) {
            listaMeses = new ArrayList<>();
        }
        return listaMeses;
    }

    public void setListaMeses(List<Integer> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<ReporteDeudasRepBean> getListaReporteDeudasRepBean() {
        if (listaReporteDeudasRepBean == null) {
            listaReporteDeudasRepBean = new ArrayList<>();
        }
        return listaReporteDeudasRepBean;
    }

    public void setListaReporteDeudasRepBean(List<ReporteDeudasRepBean> listaReporteDeudasRepBean) {
        this.listaReporteDeudasRepBean = listaReporteDeudasRepBean;
    }

    public BigDecimal getTotalSubMonto() {
        return totalSubMonto;
    }

    public void setTotalSubMonto(BigDecimal totalSubMonto) {
        this.totalSubMonto = totalSubMonto;
    }

    public BigDecimal getTotalMora() {
        return totalMora;
    }

    public void setTotalMora(BigDecimal totalMora) {
        this.totalMora = totalMora;
    }

    public BigDecimal getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(BigDecimal totalMonto) {
        this.totalMonto = totalMonto;
    }

    public UsuarioBean getBeanUsuarioSesion() {
        if (beanUsuarioSesion == null) {
            beanUsuarioSesion = new UsuarioBean();
        }
        return beanUsuarioSesion;
    }

    public void setBeanUsuarioSesion(UsuarioBean beanUsuarioSesion) {
        this.beanUsuarioSesion = beanUsuarioSesion;
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

    public Boolean getFlgHoraCorte() {
        return flgHoraCorte;
    }

    public void setFlgHoraCorte(Boolean flgHoraCorte) {
        this.flgHoraCorte = flgHoraCorte;
    }

    public Integer getIdEstadoPagado() {
        return idEstadoPagado;
    }

    public void setIdEstadoPagado(Integer idEstadoPagado) {
        this.idEstadoPagado = idEstadoPagado;
    }

    public Integer getIdEstadoPendiente() {
        return idEstadoPendiente;
    }

    public void setIdEstadoPendiente(Integer idEstadoPendiente) {
        this.idEstadoPendiente = idEstadoPendiente;
    }

    public Boolean getFlgVisionGen() {
        return flgVisionGen;
    }

    public void setFlgVisionGen(Boolean flgVisionGen) {
        this.flgVisionGen = flgVisionGen;
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

    public List<ReporteDeudasRepBean> getListaReporteDeudasRepGeneralBean() {
        if (listaReporteDeudasRepGeneralBean == null) {
            listaReporteDeudasRepGeneralBean = new ArrayList<>();
        }
        return listaReporteDeudasRepGeneralBean;
    }

    public void setListaReporteDeudasRepGeneralBean(List<ReporteDeudasRepBean> listaReporteDeudasRepGeneralBean) {
        this.listaReporteDeudasRepGeneralBean = listaReporteDeudasRepGeneralBean;
    }

    public Boolean getFlgVista() {
        return flgVista;
    }

    public void setFlgVista(Boolean flgVista) {
        this.flgVista = flgVista;
    }

    public Boolean getFlgVistaGen() {
        return flgVistaGen;
    }

    public void setFlgVistaGen(Boolean flgVistaGen) {
        this.flgVistaGen = flgVistaGen;
    }

}
