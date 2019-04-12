package pe.marista.sigma.managedBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.EventoGrafoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.PaganteService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import java.util.Map;

public class EventoGrafoMB {

    @PostConstruct
    public void EventoGrafoMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            //OBTENIENDO LISTA DE EVENTOS
            EventoBean eventoBean = new EventoBean();
            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EventoService eventoService = BeanFactory.getEventoService();
            getListaEventoBean();
            listaEventoBean = eventoService.obtener(eventoBean);

            //OBTENIENDO LISTA DE TIPOS
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEstado();
            listaTipoEstado = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_FICHA));
            getListaTipoModoPago();
            listaTipoModoPago = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO));

            //OBTENIENDO TIPO DE PAGANTE
            getPaganteBean();
            setTipPagante(1);
            obtenerTipoGrafo();
            obtenerGrafoPagante();

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CHART
    private PieChartModel pieEventoModel;

    //CLASES
    private UsuarioBean usuarioLoginBean;
    private PaganteBean paganteBean;

    //LISTAS
    private List<PaganteBean> listaPaganteBean;
    private List<EventoBean> listaEventoBean;
    private List<CodigoBean> listaTipoEstado;
    private List<CodigoBean> listaTipoModoPago;

    //VARIABLES DE CLASE
    private Integer tipPagante;

    //FLG PAGANTE
    private Boolean flgPaganteEsp;

    //FLG DE ESTUDIANTE
    private Boolean flgEstudiante;
    private Boolean flgTodosEstudiante;
    private Boolean flgNivelEst;
    private Boolean flgNivelGradoEst;
    private Boolean flgNivelGradoSecEst;

    //FLG DE PERSONAL
    private Boolean flgPersonal;
    private Boolean flgTodosPersonal;
    private Boolean flgUniOrgPer;

    //FLG DE EXTERNO
    private Boolean flgExterno;
    private Boolean flgTodosExterno;

    //FLG DE ENTIDAD
    private Boolean flgEntidad;
    private Boolean flgTodosEntidad;

    //METODOS DE CLASE
    public void obtenerTipoGrafo() {
        try {
            if (tipPagante != null) {
                paganteBean = new PaganteBean();
                if (tipPagante.equals(1)) {
                    this.flgEstudiante = true;
                    this.flgPersonal = false;
                    this.flgExterno = false;
                    this.flgEntidad = false;

                    this.flgTodosEstudiante = true;
                    this.flgNivelEst = false;
                    this.flgNivelGradoEst = false;
                    this.flgNivelGradoSecEst = false;
                    this.flgPaganteEsp = false;
                } else if (tipPagante.equals(2)) {
                    this.flgPersonal = true;
                    this.flgEstudiante = false;
                    this.flgExterno = false;
                    this.flgEntidad = false;

                    this.flgTodosPersonal = true;
                    this.flgUniOrgPer = false;
                    this.flgPaganteEsp = false;
                } else if (tipPagante.equals(3)) {
                    this.flgExterno = true;
                    this.flgPersonal = false;
                    this.flgEstudiante = false;
                    this.flgEntidad = false;

                    this.flgTodosExterno = true;
                    this.flgPaganteEsp = false;
                } else if (tipPagante.equals(4)) {
                    this.flgEntidad = true;
                    this.flgExterno = false;
                    this.flgPersonal = false;
                    this.flgEstudiante = false;

                    this.flgTodosEntidad = true;
                    this.flgPaganteEsp = false;
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //METODOS FLG ESTUDIANTE
    public void obtenerFlgEstudiante(Integer dato) {
        try {
            if (this.flgEstudiante == true) {
                if (dato.equals(1)) {
                    this.flgTodosEstudiante = true;
                    this.flgNivelEst = false;
                    this.flgNivelGradoEst = false;
                    this.flgNivelGradoSecEst = false;
                    this.flgPaganteEsp = false;
                } else if (dato.equals(2)) {
                    this.flgNivelEst = true;
                    this.flgTodosEstudiante = false;
                    this.flgNivelGradoEst = false;
                    this.flgNivelGradoSecEst = false;
                    this.flgPaganteEsp = false;
                    this.flgPaganteEsp = false;
                } else if (dato.equals(3)) {
                    this.flgNivelGradoEst = true;
                    this.flgNivelEst = false;
                    this.flgTodosEstudiante = false;
                    this.flgNivelGradoSecEst = false;
                    this.flgPaganteEsp = false;
                } else if (dato.equals(4)) {
                    this.flgNivelGradoSecEst = true;
                    this.flgNivelGradoEst = false;
                    this.flgNivelEst = false;
                    this.flgTodosEstudiante = false;
                    this.flgPaganteEsp = false;
                } else if (dato.equals(5)) {
                    this.flgPaganteEsp = true;
                    this.flgNivelGradoSecEst = false;
                    this.flgNivelGradoEst = false;
                    this.flgNivelEst = false;
                    this.flgTodosEstudiante = false;
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFlgPersonal(Integer dato) {
        try {
            if (this.flgPersonal == true) {
                if (dato.equals(1)) {
                    flgTodosPersonal = true;
                    flgUniOrgPer = false;
                    flgPaganteEsp = false;
                } else if (dato.equals(2)) {
                    flgUniOrgPer = true;
                    flgTodosPersonal = false;
                    flgPaganteEsp = false;
                } else if (dato.equals(3)) {
                    flgPaganteEsp = true;
                    flgUniOrgPer = false;
                    flgTodosPersonal = false;
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFlgExterno(Integer dato) {
        try {
            if (dato.equals(1)) {
                flgTodosExterno = true;
                flgPaganteEsp = false;
            } else if (dato.equals(2)) {
                flgPaganteEsp = true;
                flgTodosExterno = false;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFlgEntidad(Integer dato) {
        try {
            if (dato.equals(1)) {
                flgTodosEntidad = true;
                flgPaganteEsp = false;
            } else if (dato.equals(2)) {
                flgPaganteEsp = true;
                flgTodosEntidad = false;
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerGrafoPagante() {
        try {
            if (flgEstudiante == true) {
                obtenerEstudianteGrafo();
            } else if (flgPersonal == true) {
                obtenerPersonalGrafo();
            } else if (flgExterno == true) {
                obtenerExternoGrafo();
            } else if (flgEntidad == true) {
                obtenerEntidadGrafo();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEstudianteGrafo() {
        pieEventoModel = new PieChartModel();
        try {
            Integer res = 0;
            PaganteService paganteService = BeanFactory.getPaganteService();
            listaPaganteBean = new ArrayList<>();
            paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (paganteBean.getIdTipoEstado() != null) {
                paganteBean.setIdTipoEstado(paganteBean.getIdTipoEstado());
                res = 1;
            }
            if (paganteBean.getIdTipoModoPago() != null) {
                paganteBean.setIdTipoModoPago(paganteBean.getIdTipoModoPago());
                res = 1;
            }
            if (paganteBean.getNroDoc() != null && !paganteBean.getNroDoc().equals("")) {
                paganteBean.setNroDoc(paganteBean.getNroDoc());
                res = 1;
            }
            if (paganteBean.getNomPagante() != null && !paganteBean.getNomPagante().equals("")) {
                paganteBean.setNomPagante(paganteBean.getNomPagante());
                res = 1;
            }
            if (flgTodosEstudiante) {
                paganteBean.setDato(1);
            } else if (flgNivelEst) {
                paganteBean.setDato(2);
            } else if (flgNivelGradoEst) {
                paganteBean.setDato(3);
            } else if (flgNivelGradoSecEst) {
                paganteBean.setDato(4);
            }
            listaPaganteBean = paganteService.obtenerGrafoEstudiante(paganteBean);
            if (listaPaganteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            } else {
                for (PaganteBean pagante : listaPaganteBean) {
                    pieEventoModel.set(pagante.getGrafo(), pagante.getMonto().intValue());
                }
                pieEventoModel.setTitle("PAGOS DE ESTUDIANTE");
                pieEventoModel.setLegendPosition("w");
                pieEventoModel.setFill(true);
                pieEventoModel.setShowDataLabels(true);
                pieEventoModel.setDiameter(350);
                pieEventoModel.setExtender("pieExtender");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerPersonalGrafo() {
        pieEventoModel = new PieChartModel();
        try {
            Integer res = 0;
            PaganteService paganteService = BeanFactory.getPaganteService();
            listaPaganteBean = new ArrayList<>();
            paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (paganteBean.getIdTipoEstado() != null) {
                paganteBean.setIdTipoEstado(paganteBean.getIdTipoEstado());
                res = 1;
            }
            if (paganteBean.getIdTipoModoPago() != null) {
                paganteBean.setIdTipoModoPago(paganteBean.getIdTipoModoPago());
                res = 1;
            }
            if (paganteBean.getNroDoc() != null && !paganteBean.getNroDoc().equals("")) {
                paganteBean.setNroDoc(paganteBean.getNroDoc());
                res = 1;
            }
            if (paganteBean.getNomPagante() != null && !paganteBean.getNomPagante().equals("")) {
                paganteBean.setNomPagante(paganteBean.getNomPagante());
                res = 1;
            }
            if (flgTodosPersonal) {
                paganteBean.setDato(1);
            } else if (flgUniOrgPer) {
                paganteBean.setDato(2);
            }
            listaPaganteBean = paganteService.obtenerGrafoPersonal(paganteBean);
            if (listaPaganteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            } else {
                for (PaganteBean pagante : listaPaganteBean) {
                    pieEventoModel.set(pagante.getGrafo(), pagante.getMonto().intValue());
                }
                pieEventoModel.setTitle("PAGOS DE PERSONAL");
                pieEventoModel.setLegendPosition("w");
                pieEventoModel.setFill(true);
                pieEventoModel.setShowDataLabels(true);
                pieEventoModel.setDiameter(350);
                pieEventoModel.setExtender("pieExtender");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerExternoGrafo() {
        pieEventoModel = new PieChartModel();
        try {
            Integer res = 0;
            PaganteService paganteService = BeanFactory.getPaganteService();
            listaPaganteBean = new ArrayList<>();
            paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (paganteBean.getIdTipoEstado() != null) {
                paganteBean.setIdTipoEstado(paganteBean.getIdTipoEstado());
                res = 1;
            }
            if (paganteBean.getIdTipoModoPago() != null) {
                paganteBean.setIdTipoModoPago(paganteBean.getIdTipoModoPago());
                res = 1;
            }
            if (paganteBean.getNroDoc() != null && !paganteBean.getNroDoc().equals("")) {
                paganteBean.setNroDoc(paganteBean.getNroDoc());
                res = 1;
            }
            if (paganteBean.getNomPagante() != null && !paganteBean.getNomPagante().equals("")) {
                paganteBean.setNomPagante(paganteBean.getNomPagante());
                res = 1;
            }
            if (flgTodosExterno) {
                paganteBean.setDato(1);
            }
            listaPaganteBean = paganteService.obtenerGrafoExterno(paganteBean);
            if (listaPaganteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            } else {
                for (PaganteBean pagante : listaPaganteBean) {
                    pieEventoModel.set(pagante.getGrafo(), pagante.getMonto().intValue());
                }
                pieEventoModel.setTitle("PAGOS DE EXTERNO");
                pieEventoModel.setLegendPosition("w");
                pieEventoModel.setFill(true);
                pieEventoModel.setShowDataLabels(true);
                pieEventoModel.setDiameter(350);
                pieEventoModel.setExtender("pieExtender");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerEntidadGrafo() {
        pieEventoModel = new PieChartModel();
        try {
            Integer res = 0;
            PaganteService paganteService = BeanFactory.getPaganteService();
            listaPaganteBean = new ArrayList<>();
            paganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (paganteBean.getIdTipoEstado() != null) {
                paganteBean.setIdTipoEstado(paganteBean.getIdTipoEstado());
                res = 1;
            }
            if (paganteBean.getIdTipoModoPago() != null) {
                paganteBean.setIdTipoModoPago(paganteBean.getIdTipoModoPago());
                res = 1;
            }
            if (paganteBean.getNroDoc() != null && !paganteBean.getNroDoc().equals("")) {
                paganteBean.setNroDoc(paganteBean.getNroDoc());
                res = 1;
            }
            if (paganteBean.getNomPagante() != null && !paganteBean.getNomPagante().equals("")) {
                paganteBean.setNomPagante(paganteBean.getNomPagante());
                res = 1;
            }
            if (flgTodosEntidad) {
                paganteBean.setDato(1);
            }
            listaPaganteBean = paganteService.obtenerGrafoEntidad(paganteBean);
            if (listaPaganteBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            } else {
                for (PaganteBean pagante : listaPaganteBean) {
                    pieEventoModel.set(pagante.getGrafo(), pagante.getMonto().intValue());
                }
                pieEventoModel.setTitle("PAGOS DE ENTIDAD");
                pieEventoModel.setLegendPosition("w");
                pieEventoModel.setFill(true);
                pieEventoModel.setShowDataLabels(true);
                pieEventoModel.setDiameter(350);
                pieEventoModel.setExtender("pieExtender");
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirGrafo() {
        ServletOutputStream out = null;
        try {
            if (!listaPaganteBean.isEmpty()) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteEventoGrafo.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                List<EventoGrafoRepBean> listaEventoGrafoRepBean = new ArrayList<>();
                for (PaganteBean pagante : listaPaganteBean) {
                    EventoGrafoRepBean eventoGrafoRepBean = new EventoGrafoRepBean();
                    eventoGrafoRepBean.setMonto(pagante.getMonto());
                    eventoGrafoRepBean.setGrafo(pagante.getGrafo());
                    listaEventoGrafoRepBean.add(eventoGrafoRepBean);
                }
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaEventoGrafoRepBean);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                System.out.println("path: " + absoluteWebPath);
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                parametros.put("SUBREPORT_DIR", ruta);
                UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                response.reset();
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                out = response.getOutputStream();
                out.write(bytes);
                out.flush();
                return;
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
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

    public Integer getTipPagante() {
        return tipPagante;
    }

    public void setTipPagante(Integer tipPagante) {
        this.tipPagante = tipPagante;
    }

    public Boolean getFlgNivelEst() {
        return flgNivelEst;
    }

    public void setFlgNivelEst(Boolean flgNivelEst) {
        this.flgNivelEst = flgNivelEst;
    }

    public Boolean getFlgNivelGradoEst() {
        return flgNivelGradoEst;
    }

    public void setFlgNivelGradoEst(Boolean flgNivelGradoEst) {
        this.flgNivelGradoEst = flgNivelGradoEst;
    }

    public Boolean getFlgNivelGradoSecEst() {
        return flgNivelGradoSecEst;
    }

    public void setFlgNivelGradoSecEst(Boolean flgNivelGradoSecEst) {
        this.flgNivelGradoSecEst = flgNivelGradoSecEst;
    }

    public Boolean getFlgTodosEstudiante() {
        return flgTodosEstudiante;
    }

    public void setFlgTodosEstudiante(Boolean flgTodosEstudiante) {
        this.flgTodosEstudiante = flgTodosEstudiante;
    }

    public Boolean getFlgTodosPersonal() {
        return flgTodosPersonal;
    }

    public void setFlgTodosPersonal(Boolean flgTodosPersonal) {
        this.flgTodosPersonal = flgTodosPersonal;
    }

    public Boolean getFlgUniOrgPer() {
        return flgUniOrgPer;
    }

    public void setFlgUniOrgPer(Boolean flgUniOrgPer) {
        this.flgUniOrgPer = flgUniOrgPer;
    }

    public Boolean getFlgTodosExterno() {
        return flgTodosExterno;
    }

    public void setFlgTodosExterno(Boolean flgTodosExterno) {
        this.flgTodosExterno = flgTodosExterno;
    }

    public Boolean getFlgTodosEntidad() {
        return flgTodosEntidad;
    }

    public void setFlgTodosEntidad(Boolean flgTodosEntidad) {
        this.flgTodosEntidad = flgTodosEntidad;
    }

    public Boolean getFlgEstudiante() {
        return flgEstudiante;
    }

    public void setFlgEstudiante(Boolean flgEstudiante) {
        this.flgEstudiante = flgEstudiante;
    }

    public Boolean getFlgPersonal() {
        return flgPersonal;
    }

    public void setFlgPersonal(Boolean flgPersonal) {
        this.flgPersonal = flgPersonal;
    }

    public Boolean getFlgExterno() {
        return flgExterno;
    }

    public void setFlgExterno(Boolean flgExterno) {
        this.flgExterno = flgExterno;
    }

    public Boolean getFlgEntidad() {
        return flgEntidad;
    }

    public void setFlgEntidad(Boolean flgEntidad) {
        this.flgEntidad = flgEntidad;
    }

    public PieChartModel getPieEventoModel() {
        return pieEventoModel;
    }

    public void setPieEventoModel(PieChartModel pieEventoModel) {
        this.pieEventoModel = pieEventoModel;
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

    public List<CodigoBean> getListaTipoEstado() {
        if (listaTipoEstado == null) {
            listaTipoEstado = new ArrayList<>();
        }
        return listaTipoEstado;
    }

    public void setListaTipoEstado(List<CodigoBean> listaTipoEstado) {
        this.listaTipoEstado = listaTipoEstado;
    }

    public List<CodigoBean> getListaTipoModoPago() {
        if (listaTipoModoPago == null) {
            listaTipoModoPago = new ArrayList<>();
        }
        return listaTipoModoPago;
    }

    public void setListaTipoModoPago(List<CodigoBean> listaTipoModoPago) {
        this.listaTipoModoPago = listaTipoModoPago;
    }

    public Boolean getFlgPaganteEsp() {
        return flgPaganteEsp;
    }

    public void setFlgPaganteEsp(Boolean flgPaganteEsp) {
        this.flgPaganteEsp = flgPaganteEsp;
    }

}
