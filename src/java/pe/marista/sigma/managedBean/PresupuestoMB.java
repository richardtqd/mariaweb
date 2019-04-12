/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import java.util.GregorianCalendar;
import java.util.Calendar;
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ActividadCrBean;
import pe.marista.sigma.bean.AnioHistBean;
import pe.marista.sigma.bean.CentroResponsabilidadBean;
import pe.marista.sigma.bean.DetActividadBean;
import pe.marista.sigma.bean.HistoricoBean;
import pe.marista.sigma.bean.HistoricoBeanFiltro;
import pe.marista.sigma.bean.HistoricoGraficoBean;
import pe.marista.sigma.bean.HistoricoSatBean;
import pe.marista.sigma.bean.PlanContableBean;
import pe.marista.sigma.bean.ResDimensionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.PresupuestoCrRepBean;
import pe.marista.sigma.bean.reporte.PresupuestoCrRepSubBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ActividadCrService;
import pe.marista.sigma.service.CentroResponsabilidadService;
import pe.marista.sigma.service.DetActividadService;
import pe.marista.sigma.service.PlanContableService;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.service.ResDimensionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class PresupuestoMB implements Serializable {

    private List<HistoricoBean> filteredHistorico;
    private BarChartModel barHistorico3;
    private BarChartModel VerticalBaHistorico;
    private HorizontalBarChartModel horizontalBaHistorico;
    private BarChartModel barHistorico;
    private List<HistoricoBean> listaHistorico;
    private HorizontalBarChartModel HoriResDimension;
    private List<ResDimensionBean> listaResDimension;
    //private List<HistoricoGraficoBean> listaHistoricoGrafico;
    private List<AnioHistBean> listaAnioHist;
    private List<Integer> listaAnioHistorico;
    private List<HistoricoSatBean> listaHistoricoSat;
    private List<ActividadCrBean> listaPresupuesto;
    private List<ActividadCrBean> listaPresupuestoCr;
    private PieChartModel pieModel;
    private PieChartModel pieHistorico;
    private PieChartModel pieHistoricoSat;
    private PieChartModel piPresModel;
    private Integer exec;
    private ActividadCrBean crA;
    private Integer cr;
    private String crNombre;
    private String bartipFormat = "%s - %d";

    //Plan Contable
    private List<PlanContableBean> listaPlanContableBean;
    private List<DetActividadBean> listaDetACtividad;
    private List<Integer> listaAnios;
    private Integer importeSubActividad = 0;
    private Integer anio = 0;
    private Map<String,String> countries;
    private String nombre;

    //VARIABLES DE TOTALES
    private BigDecimal totalPres;
    private BigDecimal totalExec;
    private BigDecimal totalSald;
    private List<HistoricoBeanFiltro> listaHistorico3;
    Calendar fecha = new GregorianCalendar();
    private int  anio_actual = fecha.get(Calendar.YEAR);

    
    @PostConstruct
    public void PresupuestoMB() {
        try {        
        
            obtenerPresupuesto();
            obtenerPresupuestoUniNeg();
            obtenerHistorico();
            obtenerHistoricoBar();
            obtenerHistoricoBarHor();
            obtenerHistoricoBarVer();
            obtenerHistoricoSat();
            obtenerHistoricoBar3();
            cargarAnio();
            cargaAnioHist();
//          execProUpdate();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public List<ActividadCrBean> getListaPresupuesto() {
        if (listaPresupuesto == null) {
            listaPresupuesto = new ArrayList<>();
        }
        return listaPresupuesto;
    }

    public void setListaPresupuesto(List<ActividadCrBean> listaPresupuesto) {
        this.listaPresupuesto = listaPresupuesto;
    }

        /**
     * @return the listaHistorico
     */
    public List<HistoricoBean> getListaHistorico() {
        if (listaHistorico == null) {
            listaHistorico = new ArrayList<>();
        }
        return listaHistorico; 
    }

    public void setListaHistorico(List<HistoricoBean> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }
    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    
    public Integer getExec() {
        return exec;
    }

    public void setExec(Integer exec) {
        this.exec = exec;
    }

    public ActividadCrBean getCrA() {
        if (crA == null) {
            crA = new ActividadCrBean();
        }
        return crA;
    }

    public void setCrA(ActividadCrBean crA) {
        this.crA = crA;
    }

    public Integer getCr() {
        return cr;
    }

    public void setCr(Integer cr) {
        this.cr = cr;
    }

    public String getCrNombre() {
        return crNombre;
    }

    public void setCrNombre(String crNombre) {
        this.crNombre = crNombre;
    }

    public List<ActividadCrBean> getListaPresupuestoCr() {
        if (listaPresupuestoCr == null) {
            listaPresupuestoCr = new ArrayList<>();
        }
        return listaPresupuestoCr;
    }

    public void setListaPresupuestoCr(List<ActividadCrBean> listaPresupuestoCr) {
        this.listaPresupuestoCr = listaPresupuestoCr;
    }

    public PieChartModel getPiPresModel() {
        return piPresModel;
    }

    public void setPiPresModel(PieChartModel piPresModel) {
        this.piPresModel = piPresModel;
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

    public List<DetActividadBean> getListaDetACtividad() {
        return listaDetACtividad;
    }

    public void setListaDetACtividad(List<DetActividadBean> listaDetACtividad) {
        this.listaDetACtividad = listaDetACtividad;
    }

    public Integer getImporteSubActividad() {
        return importeSubActividad;
    }

    public void setImporteSubActividad(Integer importeSubActividad) {
        this.importeSubActividad = importeSubActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getListaAnios() {
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getTotalPres() {
        return totalPres;
    }

    public void setTotalPres(BigDecimal totalPres) {
        this.totalPres = totalPres;
    }

    public BigDecimal getTotalExec() {
        return totalExec;
    }

    public void setTotalExec(BigDecimal totalExec) {
        this.totalExec = totalExec;
    }

    public BigDecimal getTotalSald() {
        return totalSald;
    }

    public void setTotalSald(BigDecimal totalSald) {
        this.totalSald = totalSald;
    }
    
        /**
     * @return the pieHistorico
     */
    public PieChartModel getPieHistorico() {
        return pieHistorico;
    }

    public void setPieHistorico(PieChartModel pieHistorico) {
        this.pieHistorico = pieHistorico;
    }
    
        public HorizontalBarChartModel obtenerResDimensionBarHor() {
        HoriResDimension = new HorizontalBarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
           listaResDimension = resDimensionService.sp_mc_resultadoxdimensiones("SECTOR",anio_actual);
            System.out.println("Lista  de Dimension =====>"+ listaResDimension.size());
            if (!listaResDimension.isEmpty()) {
                for (ResDimensionBean resDimension : listaResDimension) { 
                    no_satisfecho.set(resDimension.getDesDimension(), resDimension.getNoSatisfecho());
                    med_satisfecho.set(resDimension.getDesDimension(), resDimension.getMedSatisfecho());
                    satisfecho.set(resDimension.getDesDimension(), resDimension.getSatisfecho());
                }
                HoriResDimension.setTitle("Historico Horizontal/C.R.");
                HoriResDimension.setLegendPosition("ne");
                HoriResDimension.setStacked(true);
                HoriResDimension.setAnimate(true);
                HoriResDimension.setShowPointLabels(true);
                HoriResDimension.setExtender("chartHorizontalExtender");
                //horizontalBaHistorico.setBarMargin(100);
                //horizontalBaHistorico.setBarPadding(250);
                HoriResDimension.setSeriesColors("90EE90,34495E,00BFFF");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        HoriResDimension.addSeries(satisfecho);
        HoriResDimension.addSeries(med_satisfecho);
        HoriResDimension.addSeries(no_satisfecho);
        return HoriResDimension;        
    }  
    
    public void cargaAnioHist(){
        try {
                listaAnioHist = new ArrayList<AnioHistBean>();
                listaAnioHistorico = new ArrayList<>(); 
                UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//                listaAnioHist = actividadCrService.obtenerAnio();
                for (int i = 1; i < listaAnioHist.size(); i++) {
                    listaAnioHistorico.add(listaAnioHist.get(i).getAnio());
                    System.out.println(" Anioooooooooo  Satisfecho =======>" + i);
                }
            } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    public PieChartModel obtenerPresupuesto() {
        pieModel = new PieChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            listaPresupuesto = actividadCrService.obtenerPresupuestoCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (listaPresupuesto != null) {
                for (ActividadCrBean presupuesto : listaPresupuesto) {
                    pieModel.set(presupuesto.getPlanContableBean().getNombre(), presupuesto.getImporte().intValue());
                }
                pieModel.setTitle("Presupuesto/C.R.");
                pieModel.setLegendPosition("w");
                pieModel.setFill(true);
                pieModel.setDiameter(350);
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieModel;
    }

    /** filtros*/
        public BarChartModel obtenerHistoricoBar() {
        barHistorico = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            listaHistorico=actividadCrService.obtenerHistorico();
            /*String  val_no_satisfecho;*/
            if (!listaHistorico.isEmpty()) {
                for (HistoricoBean historico : listaHistorico) { 
                    /*val_no_satisfecho = String.valueOf(historico.getPorNoSatisfecho().intValue())+ "%";*/
                    no_satisfecho.set(historico.getAnio(),historico.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(historico.getAnio(), historico.getPorMedSatisfecho().floatValue());
                    satisfecho.set(historico.getAnio(), historico.getPorSatisfecho().floatValue());
                   /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barHistorico.setTitle("Historico/C.R.");
                barHistorico.setLegendPosition("ne");   
                /*barHistorico.setShowDatatip(true);    */
                barHistorico.setShowPointLabels(true);
                barHistorico.setAnimate(true);
                barHistorico.setExtender("chartBarExtender");
                barHistorico.setSeriesColors("B0E0E6,2471A3,FFA500");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barHistorico.addSeries(satisfecho);
        barHistorico.addSeries(med_satisfecho);
        barHistorico.addSeries(no_satisfecho);
        return barHistorico;        
    }
        /*********************************/
    public BarChartModel obtenerHistoricoBar3() {
        //String cad ="todos";
        barHistorico3 = new BarChartModel();
        ChartSeries satisfecho3 = new ChartSeries();
        satisfecho3.setLabel("Satisfecho");
        ChartSeries med_satisfecho3 = new ChartSeries();
        med_satisfecho3.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho3 = new ChartSeries();
        no_satisfecho3.setLabel("No Satisfecho");
        
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            listaHistorico3 = actividadCrService.sp_prueba(anio);
            
            /*String  val_no_satisfecho;*/
            if (!anio.equals(0) || anio != null) {
                if (!listaHistorico3.isEmpty()) {
                    for (HistoricoBeanFiltro historico3 : listaHistorico3) { 
                        /*val_no_satisfecho = String.valueOf(historico.getPorNoSatisfecho().intValue())+ "%";*/
                        no_satisfecho3.set(anio,historico3.getPorNoSatisfecho().floatValue());
                        med_satisfecho3.set(anio, historico3.getPorMedSatisfecho().floatValue());
                        satisfecho3.set(anio, historico3.getPorSatisfecho().floatValue());
                        System.out.println(" No satisfecho 3 ====>" + historico3.getPorNoSatisfecho().floatValue());
                        System.out.println(" Med satisfecho 3 ===>" + historico3.getPorMedSatisfecho().floatValue());
                        System.out.println(" Satisfecho 3 =======>" + historico3.getPorSatisfecho().floatValue());
                    }
                    barHistorico3.setTitle("Historico/C.R.");
                    barHistorico3.setLegendPosition("ne");   
                    /*barHistorico.setShowDatatip(true);    */
                    barHistorico3.setShowPointLabels(true);
                    barHistorico3.setAnimate(true);
                    barHistorico3.setExtender("chartBarExtender");
                    barHistorico3.setSeriesColors("B0E0E6,2471A3,FFA500");
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barHistorico3.addSeries(satisfecho3);
        barHistorico3.addSeries(med_satisfecho3);
        barHistorico3.addSeries(no_satisfecho3);
        return barHistorico3;       
    }

   /* public List<AnioHistBean> obtenerAnioHist() {
        listaAnioHist = new ArrayList<AnioHistBean>();
        try {
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            listaAnioHist=actividadCrService.obtenerAnio();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaAnioHist; 
    }*/
    
    public HorizontalBarChartModel obtenerHistoricoBarHor() {
        horizontalBaHistorico = new HorizontalBarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            listaHistorico = actividadCrService.obtenerHistorico();
            if (!listaHistorico.isEmpty()) {
                for (HistoricoBean historico : listaHistorico) { 
                    no_satisfecho.set(historico.getAnio(), historico.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(historico.getAnio(), historico.getPorMedSatisfecho().floatValue());
                    satisfecho.set(historico.getAnio(), historico.getPorSatisfecho().floatValue());
                }
                horizontalBaHistorico.setTitle("Historico Horizontal/C.R.");
                horizontalBaHistorico.setLegendPosition("ne");
                horizontalBaHistorico.setStacked(true);
                horizontalBaHistorico.setAnimate(true);
                horizontalBaHistorico.setShowPointLabels(true);
                horizontalBaHistorico.setExtender("chartHorizontalExtender");
                //horizontalBaHistorico.setBarMargin(100);
                //horizontalBaHistorico.setBarPadding(250);
                horizontalBaHistorico.setSeriesColors("90EE90,34495E,00BFFF");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        horizontalBaHistorico.addSeries(satisfecho);
        horizontalBaHistorico.addSeries(med_satisfecho);
        horizontalBaHistorico.addSeries(no_satisfecho);
        return horizontalBaHistorico;        
    }

     public BarChartModel obtenerHistoricoBarVer() {
        VerticalBaHistorico = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            listaHistorico = actividadCrService.obtenerHistorico();
            if (!listaHistorico.isEmpty()) {
                for (HistoricoBean historico : listaHistorico) { 
                    no_satisfecho.set(historico.getAnio(), historico.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(historico.getAnio(), historico.getPorMedSatisfecho().floatValue());
                    satisfecho.set(historico.getAnio(), historico.getPorSatisfecho().floatValue());
                }
                VerticalBaHistorico.setTitle("Historico Vertical/C.R.");
                VerticalBaHistorico.setLegendPosition("ne");
                VerticalBaHistorico.setStacked(true);
                VerticalBaHistorico.setAnimate(true);
                VerticalBaHistorico.setShowPointLabels(true);
                VerticalBaHistorico.setBarMargin(100);
                VerticalBaHistorico.setBarPadding(250);
                VerticalBaHistorico.setExtender("chartVerticalExtender");

                getVerticalBaHistorico().setSeriesColors("7FFF00,00FFFF,FFD700");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        
        VerticalBaHistorico.addSeries(satisfecho);
        VerticalBaHistorico.addSeries(med_satisfecho);
        VerticalBaHistorico.addSeries(no_satisfecho);
        return VerticalBaHistorico;        
    }
     
    public void printPDF() throws Exception{
       
        List<HistoricoGraficoBean> listaHistoricoGrafico = new ArrayList<>();
        ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//        listaHistoricoGrafico = actividadCrService.sp_prueba_grafico(anio);
        String filename = "name.pdf";
        String jasperPath="/reportes/report5.jasper";
        Map<String, Object> params = new HashMap<>();
        params.put("ano1", anio);
        System.out.println("lista  grafico ====> " + listaHistoricoGrafico.size());
        this.PDF(params, jasperPath, listaHistoricoGrafico, filename);
    } 
     

    public void PDF(Map<String, Object> params,String jasperPath, List<?> dataSource, String filename) throws JRException, IOException{
        
        String RelativeWebPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
        File file = new File(RelativeWebPath);
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource, false);
        JasperPrint print= JasperFillManager.fillReport(file.getPath(), params, source);
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment;file="+ filename);
        ServletOutputStream stream  = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(print, stream);
        FacesContext.getCurrentInstance().responseComplete();
        /*ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/report5.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            listaHistoricoGrafico = new ArrayList<>();
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            listaHistoricoGrafico = actividadCrService.sp_prueba_grafico(anio);
            System.out.println("lista  grafico ====> " + listaHistoricoGrafico.size());
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaHistoricoGrafico);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "\reportes\\";
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
        FacesContext.getCurrentInstance().responseComplete();*/
    }
     
    
    public PieChartModel obtenerHistorico() {
        pieHistorico = new PieChartModel();
        try {
 
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            listaHistorico = actividadCrService.obtenerHistorico();
            if (!listaHistorico.isEmpty()) {
                for (HistoricoBean historico : listaHistorico) {
                    
                    pieHistorico.set(historico.getAnio(), historico.getPorNoSatisfecho().floatValue());
                }
                pieHistorico.setTitle("Historico/C.R.");
                pieHistorico.setLegendPosition("w");
                pieHistorico.setFill(true);
                pieHistorico.setDiameter(350);
                pieHistorico.setShowDataLabels(true);
                pieHistorico.setSeriesColors("ADFF2F,FA8072,E6E6FA,1E90FF,C71585,FFD700");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieHistorico;
    }

    public PieChartModel obtenerHistoricoSat() {
        pieHistoricoSat = new PieChartModel();
        try {
 
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
//            setListaHistoricoSat(actividadCrService.obtenerHistoricoSat());
            if (!listaHistoricoSat.isEmpty()) {
                for (HistoricoSatBean historicoSat : listaHistoricoSat){
                    
                    pieHistoricoSat.set(historicoSat.getSatisfaccion(), historicoSat.getValor());
                }
                pieHistoricoSat.setTitle("Historico/C.R.");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieHistoricoSat.setFill(true);
                pieHistoricoSat.setDiameter(350);
                pieHistoricoSat.setShowDataLabels(true);
                pieHistoricoSat.setExtender("extLegend");
                pieHistoricoSat.setSeriesColors("FFD700,C71585,E6E6FA,1E90FF,C71585,FFD700");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieHistorico;
    }
    
    public PieChartModel obtenerPresupuestoUniNeg() {
        piPresModel = new PieChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaPlanContableBean = planContableService.obtenerPresupuestoUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaPlanContableBean.isEmpty()) {
                for (PlanContableBean plan : listaPlanContableBean) {
                    piPresModel.set(plan.getNombrePlan(), plan.getImporte().intValue());
                }
                piPresModel.setTitle("Presupuesto Cuenta Contable");
                piPresModel.setLegendPosition("w");
                piPresModel.setFill(true);
                piPresModel.setShowDataLabels(true);
                piPresModel.setDiameter(350);
                piPresModel.setExtender("pieExtender");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return piPresModel;
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


    public PieChartModel obtenerPorAnio() {
        piPresModel = new PieChartModel();
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PlanContableService planContableService = BeanFactory.getPlanContableService();
            listaPlanContableBean = planContableService.obtenerPresupuestoUniNegAnio(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio);
            if (!anio.equals(0) || anio != null) {
                if (!listaPlanContableBean.isEmpty()) {
                    for (PlanContableBean plan : listaPlanContableBean) {
                        piPresModel.set(plan.getNombrePlan(), plan.getImporte().intValue());
                    }
                    piPresModel.setTitle("Presupuesto Cuenta Contable");
                    piPresModel.setLegendPosition("w");
                    piPresModel.setFill(true);
                    piPresModel.setShowDataLabels(true);
                    piPresModel.setDiameter(350);
                } else {
                    if (listaPlanContableBean.isEmpty()) {
                        obtenerPresupuestoUniNeg();
                        limpiar();
                        RequestContext.getCurrentInstance().addCallbackParam("openDialog", true);
                    }
                }
            }
            System.out.println(">>>" + listaPlanContableBean.size());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return piPresModel;
    }

    public void obtenerGrafo(ItemSelectEvent e) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ActividadCrBean gilmarr = (ActividadCrBean) new MaristaUtils().sesionObtenerObjeto("gilmarr");
            ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
            Integer dato = -1;
            for (int j = 0; j < listaPresupuesto.size(); j++) {
                dato = dato + 1;
                if (dato.equals(e.getItemIndex())) {
                    exec = actividadCrService.obetenerPresupuestoGeneralExec(listaPresupuesto.get(dato).getCentroResponsabilidadBean().getCr(), beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaPresupuesto.get(dato).getPlanContableBean().getCuenta());
                    listaPresupuesto.get(dato).getPlanContableBean().getCuenta();
                    crA.getPlanContableBean().setCuenta(listaPresupuesto.get(dato).getPlanContableBean().getCuenta());
                    crA.getCentroResponsabilidadBean().setCr(listaPresupuesto.get(dato).getCentroResponsabilidadBean().getCr());
                    setCr(listaPresupuesto.get(dato).getCentroResponsabilidadBean().getCr());
                    setCrNombre(listaPresupuesto.get(dato).getCentroResponsabilidadBean().getNombre());
                    crA.setImporte(listaPresupuesto.get(dato).getImporte());
                    crA.setExec(exec);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerCuentasPres(ItemSelectEvent e) {
        try {

            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            DetActividadService detActividadService = BeanFactory.getDetActividadService();
            Integer dato = -1;
            String cuenta = "";
            Double n = 0.0;
            totalPres = new BigDecimal(n);
            totalExec = new BigDecimal(n);
            totalSald = new BigDecimal(n);
            for (int i = 0; i < listaPlanContableBean.size(); i++) {
                dato = dato + 1;
                if (dato.equals(e.getItemIndex())) {
                    setImporteSubActividad(0);
                    setNombre(listaPlanContableBean.get(dato).getNombrePlan());
                    cuenta = listaPlanContableBean.get(dato).getCuenta().toString();
                    listaDetACtividad = detActividadService.obtenerCuentas(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuenta);
                    for (DetActividadBean deta : listaDetACtividad) {
                        importeSubActividad = importeSubActividad + deta.getImporte().intValue();
                        totalPres = totalPres.add(deta.getImporte());
                        totalExec = totalExec.add(deta.getEjecutado());
                        totalSald = totalSald.add((deta.getImporte().subtract(deta.getEjecutado())));
                    }
                }
            }
            if (!listaDetACtividad.isEmpty()) {
                RequestContext.getCurrentInstance().addCallbackParam("openModal", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiar() {
        try {
            anio = null;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void limpiarGeneral() {
        try {
            anio = 0;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void execProUpdate() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            presupuestoService.execProPres(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirPresupuestoCr() {
        ServletOutputStream out = null;
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoCr.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            //Inicializando Lista de Rep CrActividad
            List<PresupuestoCrRepBean> listaPresupuestoCrRepBean = new ArrayList<>();
            List<CentroResponsabilidadBean> listaCentro = new ArrayList<>();

            //Poblando Lista Cr en ActividadCr
            CentroResponsabilidadService centroResponsabilidadService = BeanFactory.getCentroResponsabilidadService();
            listaCentro = centroResponsabilidadService.obtenerPresCr(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            for (CentroResponsabilidadBean centro : listaCentro) {
                PresupuestoCrRepBean presupuestoCrRepBean = new PresupuestoCrRepBean();
                presupuestoCrRepBean.setCr(centro.getCr());
                presupuestoCrRepBean.setNivel(centro.getNivel());
                presupuestoCrRepBean.setNombre(centro.getNombre());
                presupuestoCrRepBean.setCreaFechaAc(centro.getCreaFechaAc());
                presupuestoCrRepBean.setCreaHoraAc(centro.getCreaHoraAc());
                ActividadCrService actividadCrService = BeanFactory.getActividadCrService();
                listaPresupuestoCr = actividadCrService.obtenerPresupuestoCrId(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), centro.getCr());
                List<PresupuestoCrRepSubBean> listaPresupuestoCrRepSubBean = new ArrayList<>();
                for (ActividadCrBean acr : listaPresupuestoCr) {
                    PresupuestoCrRepSubBean presSub = new PresupuestoCrRepSubBean();
                    presSub.setCr(acr.getCentroResponsabilidadBean().getCr());
                    presSub.setImporte(acr.getImporte());
                    presSub.setNomPlanCu(acr.getPlanContableBean().getNombre());
                    presSub.setNombreCR(acr.getCentroResponsabilidadBean().getNombre());
                    presSub.setNumCuenta(acr.getPlanContableBean().getCuenta());
                    presSub.setEjecutado(acr.getEjecutado().toString());
                    listaPresupuestoCrRepSubBean.add(presSub);
                }
                presupuestoCrRepBean.setListaDetalle(listaPresupuestoCrRepSubBean);
                listaPresupuestoCrRepBean.add(presupuestoCrRepBean);
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPresupuestoCrRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
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
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

//    public void crearExcel() {
//        String filename = "test.xls";
//        List sheetData = new ArrayList();
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(filename);
//            HSSFWorkbook workbook = new HSSFWorkbook(fis);
//            HSSFSheet sheet = workbook.getSheetAt(0);
//            Iterator rows = sheet.rowIterator();
//            while (rows.hasNext()) {
//                HSSFRow row = (HSSFRow) rows.next();
//                Iterator cells = row.cellIterator();
//                List data = new ArrayList();
//                while (cells.hasNext()) {
//                    HSSFCell cell = (HSSFCell) cells.next();
//                    data.add(cell);
//                }
//                sheetData.add(data);
//            }
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        }
//    }

    /**
     * @return the barHistorico
     */
    public BarChartModel getBarHistorico() {
        return barHistorico;
    }

    /**
     * @param barHistorico the barHistorico to set
     */
    public void setBarHistorico(BarChartModel barHistorico) {
        this.barHistorico = barHistorico;
    }

    /**
     * @return the horizontalBaHistorico
     */
    public HorizontalBarChartModel getHorizontalBaHistorico() {
        return horizontalBaHistorico;
    }

    /**
     * @param horizontalBaHistorico the horizontalBaHistorico to set
     */
    public void setHorizontalBaHistorico(HorizontalBarChartModel horizontalBaHistorico) {
        this.horizontalBaHistorico = horizontalBaHistorico;
    }

    /**
     * @return the bartipFormat
     */
    public String getBartipFormat() {
        return bartipFormat;
    }

    /**
     * @param bartipFormat the bartipFormat to set
     */
    public void setBartipFormat(String bartipFormat) {
        this.bartipFormat = bartipFormat;
    }

    /**
     * @return the VerticalBaHistorico
     */
    public BarChartModel getVerticalBaHistorico() {
        return VerticalBaHistorico;
    }

    /**
     * @param VerticalBaHistorico the VerticalBaHistorico to set
     */
    public void setVerticalBaHistorico(BarChartModel VerticalBaHistorico) {
        this.VerticalBaHistorico = VerticalBaHistorico;
    }

    public PieChartModel getPieHistoricoSat() {
        return pieHistoricoSat;
    }

    /**
     * @param pieHistoricoSat the pieHistoricoSat to set
     */
    public void setPieHistoricoSat(PieChartModel pieHistoricoSat) {
        this.pieHistoricoSat = pieHistoricoSat;
    }

    /**
     * @return the listaHistoricoSat
     */
    public List<HistoricoSatBean> getListaHistoricoSat() {
        return listaHistoricoSat;
    }

    /**
     * @param listaHistoricoSat the listaHistoricoSat to set
     */
    public void setListaHistoricoSat(List<HistoricoSatBean> listaHistoricoSat) {
        this.listaHistoricoSat = listaHistoricoSat;
    }    


    /**
     * @return the barHistorico3
     */
    public BarChartModel getBarHistorico3() {
        return barHistorico3;
    }

    /**
     * @param barHistorico3 the barHistorico3 to set
     */
    public void setBarHistorico3(BarChartModel barHistorico3) {
        this.barHistorico3 = barHistorico3;
    }

    /**
     * @return the listaHistorico3
     */
    public List<HistoricoBeanFiltro> getListaHistorico3() {
        return listaHistorico3;
    }

    /**
     * @param listaHistorico3 the listaHistorico3 to set
     */
    public void setListaHistorico3(List<HistoricoBeanFiltro> listaHistorico3) {
        this.listaHistorico3 = listaHistorico3;
    }

    /**
     * @return the countries
     */
    public Map<String,String> getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(Map<String,String> countries) {
        this.countries = countries;
    }

    /**
     * @return the listaAnioHist
     */
    public List<AnioHistBean> getListaAnioHist() {
        return listaAnioHist;
    }

    /**
     * @param listaAnioHist the listaAnioHist to set
     */
    public void setListaAnioHist(List<AnioHistBean> listaAnioHist) {
        this.listaAnioHist = listaAnioHist;
    }

    /**
     * @return the listaAnioHistorico
     */
    public List<Integer> getListaAnioHistorico() {
        return listaAnioHistorico;
    }

    /**
     * @param listaAnioHistorico the listaAnioHistorico to set
     */
    public void setListaAnioHistorico(List<Integer> listaAnioHistorico) {
        this.listaAnioHistorico = listaAnioHistorico;
    }

    /**
     * @return the filteredHistorico
     */
    public List<HistoricoBean> getFilteredHistorico() {
        return filteredHistorico;
    }

    /**
     * @param filteredHistorico the filteredHistorico to set
     */
    public void setFilteredHistorico(List<HistoricoBean> filteredHistorico) {
        this.filteredHistorico = filteredHistorico;
    }

    public HorizontalBarChartModel getHoriResDimension() {
        return HoriResDimension;
    }

    public void setHoriResDimension(HorizontalBarChartModel HoriResDimension) {
        this.HoriResDimension = HoriResDimension;
    }

    public List<ResDimensionBean> getListaResDimension() {
        return listaResDimension;
    }

    public void setListaResDimension(List<ResDimensionBean> listaResDimension) {
        this.listaResDimension = listaResDimension;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }



}  

