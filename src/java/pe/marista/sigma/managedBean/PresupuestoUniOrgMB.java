/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ActividadBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.PresupuestoUniOrgBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ActividadUniOrgRepBean;
import pe.marista.sigma.bean.reporte.DetPresUniOrgRepBean;
import pe.marista.sigma.bean.reporte.PresUniOrgRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoUniOrgRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoUniOrgService;
import pe.marista.sigma.service.UniNegUniOrgService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class PresupuestoUniOrgMB implements Serializable {

    @PostConstruct
    public void PresupuestoUniOrgMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerPresupuestoOrgGrafo();
            cargarAnio();
//            execProUpdate();
            donutModel = obtenerDona();
            barModel = obtenerLineaPresUniOrg();

            UniNegUniOrgService uniNegUniOrgService = BeanFactory.getUniNegUniOrgService();
            getListaUnidadOrganicaPorUniNeg();
            listaUnidadOrganicaPorUniNeg = uniNegUniOrgService.obtenerUniOrgPorUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaUnidadOrganicaPorUniNeg.isEmpty()) {
                for (UniNegUniOrgBean uo : listaUnidadOrganicaPorUniNeg) {
                    uo.setFlgSeleccionar(Boolean.TRUE);
                }
            }

            SimpleDateFormat formato = new SimpleDateFormat("yyyy");
            String dia = formato.format(new Date());
            anio = new Integer(dia);
//            setValSelTodos(Boolean.TRUE);

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private PieChartModel pieModel;
    private BubbleChartModel bubbleModel;
    private DonutChartModel donutModel;
    private HorizontalBarChartModel barModel;
    private List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean;
    private List<PresupuestoUniOrgBean> listaPresupuestoUo;
    private PresupuestoUniOrgBean presupuestoUniOrgBean;
    private List<DetActividadBean> listaDetActividadBean;
    private List<Integer> listaAnios;
    private Integer anio;
    private String nombre;
    private Integer importe;
    private Integer idUniOrg;
    private List<UniNegUniOrgBean> listaUnidadOrganicaPorUniNeg;
    private UsuarioBean beanUsuarioSesion;
    private List<Integer> listaIdUniOrg;
    private Boolean valSelTodos = true;

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public List<PresupuestoUniOrgBean> getListaPresupuestoUniOrgBean() {
        if (listaPresupuestoUniOrgBean == null) {
            listaPresupuestoUniOrgBean = new ArrayList<>();
        }
        return listaPresupuestoUniOrgBean;
    }

    public void setListaPresupuestoUniOrgBean(List<PresupuestoUniOrgBean> listaPresupuestoUniOrgBean) {
        this.listaPresupuestoUniOrgBean = listaPresupuestoUniOrgBean;
    }

    public PresupuestoUniOrgBean getPresupuestoUniOrgBean() {
        if (presupuestoUniOrgBean == null) {
            presupuestoUniOrgBean = new PresupuestoUniOrgBean();
        }
        return presupuestoUniOrgBean;
    }

    public void setPresupuestoUniOrgBean(PresupuestoUniOrgBean presupuestoUniOrgBean) {
        this.presupuestoUniOrgBean = presupuestoUniOrgBean;
    }

    public List<PresupuestoUniOrgBean> getListaPresupuestoUo() {
        if (listaPresupuestoUo == null) {
            listaPresupuestoUo = new ArrayList<>();
        }
        return listaPresupuestoUo;
    }

    public void setListaPresupuestoUo(List<PresupuestoUniOrgBean> listaPresupuestoUo) {
        this.listaPresupuestoUo = listaPresupuestoUo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getImporte() {
        return importe;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
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

    public List<Integer> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public BubbleChartModel getBubbleModel() {
        return bubbleModel;
    }

    public void setBubbleModel(BubbleChartModel bubbleModel) {
        this.bubbleModel = bubbleModel;
    }

    public DonutChartModel getDonutModel() {
        return donutModel;
    }

    public void setDonutModel(DonutChartModel donutModel) {
        this.donutModel = donutModel;
    }

    public HorizontalBarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(HorizontalBarChartModel barModel) {
        this.barModel = barModel;
    }

    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            listaAnios = new ArrayList<>();
            Integer inicio = MaristaConstantes.INICIO;
            Integer fin = MaristaConstantes.FIN;
            for (int i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public PieChartModel obtenerPresupuestoOrgGrafo() {
        pieModel = new PieChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaPresupuestoUniOrgBean.isEmpty()) {
                for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                    pieModel.set(presupuesto.getUnidadOrganica(), presupuesto.getImporte().intValue());
                }
                pieModel.setTitle("Presupuesto Unidades Organicas");
                pieModel.setLegendPosition("w");
                pieModel.setFill(true);
                pieModel.setShowDataLabels(true);
                pieModel.setDiameter(450);
                pieModel.setExtender("pieExtender");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieModel;
    }

    public void obtenerGrafo(ItemSelectEvent e) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            Integer dato = -1;
            if (!listaPresupuestoUniOrgBean.isEmpty()) {
                for (int j = 0; j < listaPresupuestoUniOrgBean.size(); j++) {
                    dato = dato + 1;
                    if (dato.equals(e.getItemIndex())) {
                        setImporte(0);
                        setNombre(listaPresupuestoUniOrgBean.get(dato).getUnidadOrganica());
                        setIdUniOrg(listaPresupuestoUniOrgBean.get(dato).getIduniorg());
                        listaDetActividadBean = detActividadService.obtenerPorUniOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaPresupuestoUniOrgBean.get(dato).getIduniorg());
//                        listaPresupuestoUo = presupuestoUniOrgService.obtenerPresupuestoOrgId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2015, listaPresupuestoUniOrgBean.get(dato).getIduniorg());
                        for (DetActividadBean pres : listaDetActividadBean) {
                            importe = importe + pres.getImporte().intValue();
                        }
                    }
                }
            }
            if (!listaDetActividadBean.isEmpty()) {
                RequestContext.getCurrentInstance().addCallbackParam("openModal", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public PieChartModel obtenerPorAnio() {
        pieModel = new PieChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoOrgId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (!anio.equals(0) || anio != null) {
                if (!listaPresupuestoUniOrgBean.isEmpty()) {
                    for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                        pieModel.set(presupuesto.getUnidadOrganica(), presupuesto.getImporte().intValue());
                    }
                    pieModel.setTitle("Presupuesto Cuenta Contable");
                    pieModel.setLegendPosition("w");
                    pieModel.setFill(true);
                    pieModel.setShowDataLabels(true);
                    pieModel.setDiameter(350);
                } else {
                    if (listaPresupuestoUniOrgBean.isEmpty()) {
                        obtenerPresupuestoOrgGrafo();
                        limpiar();
                        RequestContext.getCurrentInstance().addCallbackParam("openDialog", true);
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieModel;
    }

    public void limpiar() {
        try {
            anio = null;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarSelecTodos() {
        try {
            if (valSelTodos.equals(Boolean.TRUE)) {
                for (UniNegUniOrgBean uo : listaUnidadOrganicaPorUniNeg) {
                    uo.setFlgSeleccionar(Boolean.TRUE);
                }
            } else {
                for (UniNegUniOrgBean uo : listaUnidadOrganicaPorUniNeg) {
                    uo.setFlgSeleccionar(Boolean.FALSE);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void execProUpdate() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            presupuestoUniOrgService.execProPresUniOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public BubbleChartModel obtenerBubleUniOrg() {
        bubbleModel = new BubbleChartModel();
        try {
            Integer var = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaPresupuestoUniOrgBean.isEmpty()) {
                for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                    var++;
                    bubbleModel.add(new BubbleChartSeries(presupuesto.getUnidadOrganica(), presupuesto.getImporte().intValue(), var, 100));
                }
                bubbleModel.setTitle("Presupuesto Unidades Organicas");
                bubbleModel.getAxis(AxisType.X).setLabel("Total Presupuestado");
                Axis yAxis = bubbleModel.getAxis(AxisType.Y);
                yAxis.setMin(0);
                yAxis.setMax(1000000);
                yAxis.setLabel("Unidades Organicas");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return bubbleModel;
    }

    public void validar() {
        try {
            listaIdUniOrg = new ArrayList<>();
            for (UniNegUniOrgBean lista : listaUnidadOrganicaPorUniNeg) {
                if (lista.getFlgSeleccionar() != null) {
                    if (lista.getFlgSeleccionar().equals(Boolean.TRUE)) {
                        listaIdUniOrg.add(lista.getUnidadOrganicaBean().getIdUniOrg());
                    }
                }
            }

            imprimirPresupuestoUniOrgFor();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public void validar2() {
        try {
            listaIdUniOrg = new ArrayList<>();
            for (UniNegUniOrgBean lista : listaUnidadOrganicaPorUniNeg) {
                if (lista.getFlgSeleccionar() != null) {
                    if (lista.getFlgSeleccionar().equals(Boolean.TRUE)) {
                        listaIdUniOrg.add(lista.getUnidadOrganicaBean().getIdUniOrg());
                    }
                }
            }

            imprimirPresupuestoUniOrgFor2();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPresupuestoUniOrgFor() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoUniOrg.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            List<PresUniOrgRepBean> listaPresUniOrgRep = new ArrayList<>();
            listaPresUniOrgRep = presupuestoUniOrgService.obtenerPresupuestoPorUniOrgForTop1(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, listaIdUniOrg);
            if (!listaPresUniOrgRep.isEmpty()) {
                for (int i = 0; i < listaPresUniOrgRep.size(); i++) {
                    List<PresUniOrgRepBean> listaRepDetPresUniOrg = new ArrayList<>();
                    listaRepDetPresUniOrg = presupuestoUniOrgService.obtenerPresupuestoPorUniOrgFor(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, listaIdUniOrg);
                    listaPresUniOrgRep.get(0).setListaDetalle(listaRepDetPresUniOrg);
                    if (!listaRepDetPresUniOrg.isEmpty()) {
                        for (int j = 0; j < listaPresUniOrgRep.get(0).getListaDetalle().getData().size(); j++) {
                            List<DetPresUniOrgRepBean> listaRepDetDetPresUniOrg = new ArrayList<>();
                            listaRepDetDetPresUniOrg = presupuestoUniOrgService.obtenerDetPresupuestoPorUniOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, (new Integer(listaRepDetPresUniOrg.get(j).getIdUniOrg())));
                            listaRepDetPresUniOrg.get(j).setListaDetallePresUniOrg(listaRepDetDetPresUniOrg);
                            listaPresUniOrgRep.get(0).setListaDetalle(listaRepDetPresUniOrg);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresUniOrgRep);

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

    public void imprimirPresupuestoUniOrgFor2() {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoUniOrg.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            List<PresUniOrgRepBean> listaPresUniOrgRep = new ArrayList<>();
            listaPresUniOrgRep = presupuestoUniOrgService.obtenerPresupuestoPorUniOrgForTop1(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, listaIdUniOrg);
            if (!listaPresUniOrgRep.isEmpty()) {
                for (int i = 0; i < listaPresUniOrgRep.size(); i++) {
                    List<PresUniOrgRepBean> listaRepDetPresUniOrg = new ArrayList<>();
                    listaRepDetPresUniOrg = presupuestoUniOrgService.obtenerPresupuestoPorUniOrgFor(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, listaIdUniOrg);
                    listaPresUniOrgRep.get(0).setListaDetalle(listaRepDetPresUniOrg);
                    if (!listaRepDetPresUniOrg.isEmpty()) {
                        for (int j = 0; j < listaPresUniOrgRep.get(0).getListaDetalle().getData().size(); j++) {
                            List<DetPresUniOrgRepBean> listaRepDetDetPresUniOrg = new ArrayList<>();
                            listaRepDetDetPresUniOrg = presupuestoUniOrgService.obtenerDetPresupuestoPorUniOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, (new Integer(listaRepDetPresUniOrg.get(j).getIdUniOrg())));
                            listaRepDetPresUniOrg.get(j).setListaDetallePresUniOrg(listaRepDetDetPresUniOrg);
                            listaPresUniOrgRep.get(0).setListaDetalle(listaRepDetPresUniOrg);
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresUniOrgRep);

            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, jrbc);

            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + jasperPrint.getName() + ".docx");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

            JRDocxExporter docxExporter = new JRDocxExporter();
            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
            docxExporter.exportReport();
            FacesContext.getCurrentInstance().responseComplete();

//            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            out = response.getOutputStream();
//            out.write(bytes);
//            out.flush();
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

    public void imprimirPresupuestoUniOrg() {
        ServletOutputStream out = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoUniOrg.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<PresupuestoUniOrgRepBean> listaPresupuestoUniOrgRepBean = new ArrayList<>();
            List<ActividadBean> listaActividadBean = new ArrayList<>();
            for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                PresupuestoUniOrgRepBean presupuestoUniOrgRepBean = new PresupuestoUniOrgRepBean();
                presupuestoUniOrgRepBean.setIdUniOrg(presupuesto.getIduniorg());
                presupuestoUniOrgRepBean.setImporte(presupuesto.getImporte());
                presupuestoUniOrgRepBean.setNombreUniOrg(presupuesto.getUnidadOrganica());
                presupuestoUniOrgRepBean.setNumActividad(presupuesto.getNumActividad());
                presupuestoUniOrgRepBean.setNumSubActividad(presupuesto.getNumSubActividad());
                presupuestoUniOrgRepBean.setFecha(presupuesto.getCreaFechaAc());
                presupuestoUniOrgRepBean.setHora(presupuesto.getCreaHoraAc());
                ActividadService actividadService = BeanFactory.getActividadService();
                listaActividadBean = actividadService.obtenerPorUnidadOrganica(presupuesto.getIduniorg(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 2015);
                System.out.println(">>>" + listaActividadBean.size());
                List<ActividadUniOrgRepBean> listaActividadUniOrgRepBean = new ArrayList<>();
                for (ActividadBean actividad : listaActividadBean) {
                    ActividadUniOrgRepBean actividadUniOrgRepBean = new ActividadUniOrgRepBean();
                    actividadUniOrgRepBean.setIdActividad(actividad.getIdActividad());
                    actividadUniOrgRepBean.setNombre(actividad.getNombre());
                    actividadUniOrgRepBean.setIngreso(actividad.getIngreso());
                    actividadUniOrgRepBean.setEgreso(actividad.getEgreso());
                    actividadUniOrgRepBean.setResponsable(actividad.getResponsable());
                    listaActividadUniOrgRepBean.add(actividadUniOrgRepBean);
                    System.out.println(">>>" + listaActividadUniOrgRepBean.size());
                }
                presupuestoUniOrgRepBean.setListaDetalle(listaActividadUniOrgRepBean);
                listaPresupuestoUniOrgRepBean.add(presupuestoUniOrgRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresupuestoUniOrgRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("uniNeg", ub.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            parametros.put("anio", 2015);
            parametros.put("idUniOrg", idUniOrg);
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
        }
    }

    public DonutChartModel obtenerDona() {
        DonutChartModel model = new DonutChartModel();
        try {
            Map<String, Number> donaPresUniOrg = new LinkedHashMap<String, Number>();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaPresupuestoUniOrgBean.isEmpty()) {
                for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                    donaPresUniOrg.put(presupuesto.getUnidadOrganica(), presupuesto.getImporte().intValue());
                }
                model.addCircle(donaPresUniOrg);
                model.setTitle("Presupuesto Unidades Organicas");
                model.setLegendPosition("w");
                model.setSliceMargin(5);
                model.setShowDataLabels(true);
                model.setDataFormat("value");
                model.setShadow(false);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return model;
    }

    public HorizontalBarChartModel obtenerLineaPresUniOrg() {
        HorizontalBarChartModel barChartModel = new HorizontalBarChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoUniOrgService presupuestoUniOrgService = BeanFactory.getPresupuestoUniOrgService();
            listaPresupuestoUniOrgBean = presupuestoUniOrgService.obtenerPresupuestoOrg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ChartSeries presUniOrg = new ChartSeries();
            ChartSeries presUniOrg1 = new ChartSeries();
            if (!listaPresupuestoUniOrgBean.isEmpty()) {
                for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                    presUniOrg.setLabel("Presupuesto Programado");
                    presUniOrg.set(presupuesto.getUnidadOrganica().replaceAll(" ", "\n"), presupuesto.getImporte().intValue());
                }
                barChartModel.addSeries(presUniOrg);

                for (PresupuestoUniOrgBean presupuesto : listaPresupuestoUniOrgBean) {
                    presUniOrg1.setLabel("Presupuesto Ejecutado");
                    presUniOrg1.set(presupuesto.getUnidadOrganica().replaceAll(" ", "\n"), presupuesto.getPresupuestoejec().intValue() + 10);
                }
                barChartModel.addSeries(presUniOrg1);

                barChartModel.setTitle("Presupuesto Unidades Orgánicas");
                barChartModel.setLegendPosition("e");
                barChartModel.setStacked(true);

                Axis xAxis = barChartModel.getAxis(AxisType.X);
                xAxis.setLabel("Unidades Orgánicas");

                Axis yAxis = barChartModel.getAxis(AxisType.Y);
                yAxis.setLabel("Presupuesto");
                yAxis.setMin(0);
                yAxis.setMax(100000);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return barChartModel;
    }

    public List<UniNegUniOrgBean> getListaUnidadOrganicaPorUniNeg() {
        if (listaUnidadOrganicaPorUniNeg == null) {
            listaUnidadOrganicaPorUniNeg = new ArrayList<>();
        }
        return listaUnidadOrganicaPorUniNeg;
    }

    public void setListaUnidadOrganicaPorUniNeg(List<UniNegUniOrgBean> listaUnidadOrganicaPorUniNeg) {
        this.listaUnidadOrganicaPorUniNeg = listaUnidadOrganicaPorUniNeg;
    }

    public List<Integer> getListaIdUniOrg() {
        return listaIdUniOrg;
    }

    public void setListaIdUniOrg(List<Integer> listaIdUniOrg) {
        this.listaIdUniOrg = listaIdUniOrg;
    }

    public Boolean getValSelTodos() {
        return valSelTodos;
    }

    public void setValSelTodos(Boolean valSelTodos) {
        this.valSelTodos = valSelTodos;
    }

}
