/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;
import pe.marista.sigma.bean.CantidadBean;
import pe.marista.sigma.bean.GrPieChartBean;
import pe.marista.sigma.bean.HistoricoBean;
import pe.marista.sigma.bean.PoblacionBean;
import pe.marista.sigma.bean.PoblacionGrOcuBean;
import pe.marista.sigma.bean.ResDimensionBean;
import pe.marista.sigma.bean.ResSatGeneralBean;
import pe.marista.sigma.bean.UniNegBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ResDimensionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public class ResDimensionMB implements Serializable {

    private HorizontalBarChartModel HoriResDimension;
    private List<ResDimensionBean> listaResDimension;
    private List<ResDimensionBean> listaResDimensionbar;
    private List<ResSatGeneralBean> listaSatGeneral;
    private List<ResDimensionBean> listaGrpOcuMantenimiento;
    private List<HistoricoBean> listaHistorico;
    private List<UniNegBean> listaUniNeg;

    private BarChartModel barResDimension;
    private BarChartModel barGrpDirectiva;
    private BarChartModel barGrpADministrativa;
    private BarChartModel barGrpSecretariado;
    private BarChartModel barGrpSistemas;
    private BarChartModel barGrpTesoreria;
    private BarChartModel barGrpFormativa;
    private BarChartModel barSatGeneral;
    private BarChartModel barGrpOcuMantenimiento;
    private BarChartModel barHistorico;
    private BarChartModel barGrpInicial;
    private BarChartModel barGrpPrimaria;
    private BarChartModel barGrpSecundaria;

    private PieChartModel pieSatisfaccionGeneral;
    private PieChartModel pieOcuDirectiva;
    private PieChartModel pieOcuAdministrativa;
    private PieChartModel pieOcuSecretariado;
    private PieChartModel pieOcuSistemas;
    private PieChartModel pieOcuFormativa;
    private PieChartModel pieOcuTesoreria;
    private PieChartModel pieOcuInicial;
    private PieChartModel pieOcuPrimaria;
    private PieChartModel pieOcuSecundaria;
    private PieChartModel pieTipoMasculino;
    private PieChartModel pieTipoFemenino;
    private PieChartModel pieEdad_30801;
    private PieChartModel pieEdad_30802;
    private PieChartModel pieEdad_30803;
    private PieChartModel pieTiempoServicio_30601;
    private PieChartModel pieTiempoServicio_30602;
    private PieChartModel pieTiempoServicio_30603;

    private List<GrPieChartBean> listaTiempoServicio_30601;
    private List<GrPieChartBean> listaTiempoServicio_30602;
    private List<GrPieChartBean> listaTiempoServicio_30603;
    private List<GrPieChartBean> listaEdad_30801;
    private List<GrPieChartBean> listaEdad_30802;
    private List<GrPieChartBean> listaEdad_30803;
    private List<GrPieChartBean> listaTipo_Femenino;
    private List<GrPieChartBean> listaTipo_Masculino;
    private List<GrPieChartBean> listaSatisfaccionGeneral;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Directiva;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Administrativa;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Formativa;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Secretariado;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Sistemas;
    private List<GrPieChartBean> listaGrpOcuNoDocente_Tesoreria;
    private List<ResDimensionBean> listaGrpDirectivabar;
    private List<ResDimensionBean> listaGrpAdministrativabar;
    private List<ResDimensionBean> listaGrpSecretariadobar;
    private List<ResDimensionBean> listaGrpSistemasbar;
    private List<ResDimensionBean> listaGrpTesoreriabar;
    private List<ResDimensionBean> listaGrpFormativabar;
    private List<GrPieChartBean> listaGrpOcuDocente_Inicial;
    private List<GrPieChartBean> listaGrpOcuDocente_Primaria;
    private List<GrPieChartBean> listaGrpOcuDocente_Secundaria;

    private List<ResDimensionBean> listaGrpInicialbar;
    private List<ResDimensionBean> listaGrpPrimariabar;
    private List<ResDimensionBean> listaGrpSecundariabar;
    
    private List<PoblacionBean> listaCantPoblacion;
    private List<PoblacionGrOcuBean> listaCantPoblacionGrpOcu;
    
    private List<PoblacionGrOcuBean> listaCantPoblacionSexo;
    private List<PoblacionGrOcuBean> listaCantPoblacionRangoEdad;
    private List<PoblacionGrOcuBean> listaCantPoblacionTiempoSer;
    private List<PoblacionGrOcuBean> listaCantPoblacionGrpAdmDirFor;
    private List<PoblacionGrOcuBean> listaCantPoblacionInicial;
    private List<PoblacionGrOcuBean> listaCantPoblacionPrimaria;
    private List<PoblacionGrOcuBean> listaCantPoblacionSecundaria;
    
    private List<PoblacionGrOcuBean> listaCantPoblacionDirectiva;
    private List<PoblacionGrOcuBean> listaCantPoblacionAdministrativa;
    private List<PoblacionGrOcuBean> listaCantPoblacionformativa;
    private List<PoblacionGrOcuBean> listaCantPoblacionMantenimiento;
    private List<UniNegBean> listaUnidadNegocio;
    private List<PoblacionGrOcuBean> listaCantPoblacionNivelAcademico;
    private List<CantidadBean> cargartablas;
    
    private String nombre_unidad;
    private int idTipoArea;
    private int idTipoPersonal;
    private int idNivelAcademico;
    private int sexo; //1 - Masculino , 2 - Femenino
    private int idEdad;
    private int idTiempoServicio;
    public  static String uniNeg = "";
    private int flag_Print = 0;
    private int cantPoblacionUnineg=0;
    Calendar fecha = new GregorianCalendar();
    private int  anio = fecha.get(Calendar.YEAR);
    public  static String rec_titulo_grp_ocupa = "";
    private String titulo_grp_ocupa_2 = "";
    private String titulo_grp_ocupa= "";
    
    @PostConstruct
    public void ResDimensionMB() {
        try {
            
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            
            cargartablas= resDimensionService.sp_mc_cargar_tbl_poblaciones();
            listaUniNeg = resDimensionService.sp_mc_unidadnegocio();
//          execProUpdate();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    public void cargarGraficos(String uniNeg1) throws Exception{
        /*uniNeg1="UMCH";  || !uniNeg1.equalsIgnoreCase("UMCH")*/
        if(uniNeg1.equalsIgnoreCase("SECTOR") || uniNeg1.equalsIgnoreCase("UMCH")){
            titulo_grp_ocupa="POR GRUPOS OCUPACIONALES";
        }else{
            titulo_grp_ocupa="POR GRUPOS OCUPACIONALES: NO DOCENTES";
        }
        rec_titulo_grp_ocupa = titulo_grp_ocupa;
        setUniNeg(uniNeg1);
        System.out.println("unineg: " + uniNeg);
        obtenerHistoricoBar(uniNeg1);
        obtenersatGeneralBar(uniNeg1);
        obtenerResDimensionBar(uniNeg1);
        obtenerResDimensionBarHor(uniNeg1);
        obtenerGrpDirectivaBar(uniNeg1);
        obtenerGrpAdministrativoBar(uniNeg1);
        obtenerGrpFormativaBar(uniNeg1);
        obtenerGrpOcuDirectivaPie(uniNeg1);
        obtenerGrpOcuAdministrativaPie(uniNeg1);

        obtenerGrpOcuFormativaPie(uniNeg1);

        obtenerGrpOcuSecretariadoPie(uniNeg1);
        obtenerGrpOcuSistemasPie(uniNeg1);
        obtenerGrpOcuTesoreriaPie(uniNeg1);
        obtenerSatisfaccionGeneralPie(uniNeg1);
        obtenerGrpOcuMantenimiento(uniNeg1);
        obtenerGrpOcuSecundariaPie(uniNeg1);
        obtenerGrpOcuPrimarialPie(uniNeg1);
        obtenerGrpOcuInicialPie(uniNeg1);
        obtenerGrpInicialBar(uniNeg1);
        obtenerGrpSecundariaBar(uniNeg1);
        obtenerGrpPrimariaBar(uniNeg1);
        obtenerTipoMasculinoPie(uniNeg1);
        obtenerTipoFemeninoPie(uniNeg1);
        obtenerEdad_30801Pie(uniNeg1);
        obtenerEdad_30802Pie(uniNeg1);
        obtenerEdad_30803Pie(uniNeg1);
        obtenerEdad_30601Pie(uniNeg1);
        obtenerEdad_30602Pie(uniNeg1);
        obtenerEdad_30603Pie(uniNeg1);
        obtenerGrpSistemasBar(uniNeg1);
        obtenerGrpTesoreriaBar(uniNeg1);
        obtenerGrpSecretariadoBar(uniNeg1);
        System.out.println("unineg: " + uniNeg1);
        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();  
        listaCantPoblacion  = resDimensionService.sp_mc_cant_poblacion(uniNeg1,anio);
        cantPoblacionUnineg =  listaCantPoblacion.size();        
        listaCantPoblacionSexo = resDimensionService.sp_mc_cant_poblacion_sexo(uniNeg1,anio);
        listaCantPoblacionRangoEdad = resDimensionService.sp_mc_cant_poblacion_rang_edad(uniNeg1,anio);
        listaCantPoblacionTiempoSer = resDimensionService.sp_mc_cant_poblacion_time_ser(uniNeg1,anio);
        listaCantPoblacionGrpAdmDirFor = resDimensionService.sp_mc_cant_poblacion_grp_ocupacional(uniNeg1,anio);        
        listaCantPoblacionInicial = resDimensionService.sp_mc_cant_poblacion_nivel_academico(uniNeg1,6,anio);
        listaCantPoblacionPrimaria = resDimensionService.sp_mc_cant_poblacion_nivel_academico(uniNeg1,5,anio);
        listaCantPoblacionSecundaria = resDimensionService.sp_mc_cant_poblacion_nivel_academico(uniNeg1,4,anio);
        
        listaCantPoblacionDirectiva = resDimensionService.sp_mc_cant_poblacion_area(uniNeg1,30701,anio);
        listaCantPoblacionAdministrativa = resDimensionService.sp_mc_cant_poblacion_area(uniNeg1,30705,anio);               
        listaCantPoblacionformativa = resDimensionService.sp_mc_cant_poblacion_area(uniNeg1,30703,anio);               
        listaCantPoblacionMantenimiento = resDimensionService.sp_mc_cant_poblacion_per_mant(uniNeg1,anio);
        listaCantPoblacionNivelAcademico = resDimensionService.sp_mc_cant_poblacion_grp_nivel(anio,uniNeg1);
        System.out.println(" NIVEL ACADEMICO ===============>"+ listaCantPoblacionNivelAcademico.size());
    }
    public void ReccargarGraficos(){
    String parametro = (String) new MaristaUtils().requestObtenerObjeto("unid");
    parametro = uniNeg;
    titulo_grp_ocupa_2 = rec_titulo_grp_ocupa;
    System.out.println("unidad de negocio recuperada : " + uniNeg);  
    try{
        
        obtenerHistoricoBar(parametro);
        obtenersatGeneralBar(parametro);
        obtenerResDimensionBar(parametro);
        obtenerResDimensionBarHor(parametro);
        obtenerGrpDirectivaBar(parametro);
        obtenerGrpAdministrativoBar(parametro);
        obtenerGrpFormativaBar(parametro);
        obtenerGrpOcuDirectivaPie(parametro);
        obtenerGrpOcuAdministrativaPie(parametro);

        obtenerGrpOcuFormativaPie(parametro);

        obtenerGrpOcuSecretariadoPie(parametro);
        obtenerGrpOcuSistemasPie(parametro);
        obtenerGrpOcuTesoreriaPie(parametro);
        obtenerSatisfaccionGeneralPie(parametro);
        obtenerGrpOcuMantenimiento(parametro);
        obtenerGrpOcuSecundariaPie(parametro);
        obtenerGrpOcuPrimarialPie(parametro);
        obtenerGrpOcuInicialPie(parametro);
        obtenerGrpInicialBar(parametro);
        obtenerGrpSecundariaBar(parametro);
        obtenerGrpPrimariaBar(parametro);
        obtenerTipoMasculinoPie(parametro);
        obtenerTipoFemeninoPie(parametro);
        obtenerEdad_30801Pie(parametro);
        obtenerEdad_30802Pie(parametro);
        obtenerEdad_30803Pie(parametro);
        obtenerEdad_30601Pie(parametro);
        obtenerEdad_30602Pie(parametro);
        obtenerEdad_30603Pie(parametro);
        obtenerGrpSistemasBar(parametro);
        obtenerGrpTesoreriaBar(parametro);
        obtenerGrpSecretariadoBar(parametro);
        System.out.println("unineg: " + parametro);
        ResDimensionService resDimensionService = BeanFactory.getResDimensionService();  
        listaCantPoblacion  = resDimensionService.sp_mc_cant_poblacion(parametro,anio);
        cantPoblacionUnineg =  listaCantPoblacion.size();        
        listaCantPoblacionSexo = resDimensionService.sp_mc_cant_poblacion_sexo(parametro,anio);
        listaCantPoblacionRangoEdad = resDimensionService.sp_mc_cant_poblacion_rang_edad(parametro,anio);
        listaCantPoblacionTiempoSer = resDimensionService.sp_mc_cant_poblacion_time_ser(parametro,anio);
        listaCantPoblacionGrpAdmDirFor = resDimensionService.sp_mc_cant_poblacion_grp_ocupacional(parametro,anio);        
        listaCantPoblacionInicial = resDimensionService.sp_mc_cant_poblacion_nivel_academico(parametro,6,anio);
        listaCantPoblacionPrimaria = resDimensionService.sp_mc_cant_poblacion_nivel_academico(parametro,5,anio);
        listaCantPoblacionSecundaria = resDimensionService.sp_mc_cant_poblacion_nivel_academico(parametro,4,anio);
        
        listaCantPoblacionDirectiva = resDimensionService.sp_mc_cant_poblacion_area(parametro,30701,anio);
        listaCantPoblacionAdministrativa = resDimensionService.sp_mc_cant_poblacion_area(parametro,30705,anio);               
        listaCantPoblacionformativa = resDimensionService.sp_mc_cant_poblacion_area(parametro,30703,anio);               
        listaCantPoblacionMantenimiento = resDimensionService.sp_mc_cant_poblacion_per_mant(parametro,anio);
        listaUnidadNegocio =  resDimensionService.sp_mc_desc_unidadnegocio(parametro);
        nombre_unidad= listaUnidadNegocio.get(0).getNombreUniNeg();
        listaCantPoblacionNivelAcademico = resDimensionService.sp_mc_cant_poblacion_grp_nivel(anio,parametro);
    } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    /* RESULTADOS POR DIMENSION */

    public HorizontalBarChartModel obtenerResDimensionBarHor(String uniNegocio) {
        HoriResDimension = new HorizontalBarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0; 
		
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();  
            listaResDimension = resDimensionService.sp_mc_resultadoxdimensiones(uniNeg,anio);
            System.out.println("Lista  de Dimension =====>" + listaResDimension.size());
            if (!listaResDimension.isEmpty()) {
                for (ResDimensionBean resDimension : listaResDimension) {
                    res = Math.round(resDimension.getNoSatisfecho().floatValue())  + 
						  Math.round(resDimension.getMedSatisfecho().floatValue()) +  
						  Math.round(resDimension.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resDimension.getNoSatisfecho().floatValue()) + 
						Math.round(resDimension.getMedSatisfecho().floatValue()))  ; 
                        resDimension.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resDimension.getNoSatisfecho().floatValue()) +
						Math.round(resDimension.getSatisfecho().floatValue())));    
                        resDimension.setMedSatisfecho(newval);
                    }                    
                    satisfecho.set(resDimension.getIdTipoDimension(), resDimension.getSatisfecho().floatValue());
                    med_satisfecho.set(resDimension.getIdTipoDimension(), resDimension.getMedSatisfecho().floatValue());
                    no_satisfecho.set(resDimension.getIdTipoDimension(), resDimension.getNoSatisfecho().floatValue());
                }
                HoriResDimension.setTitle("Resultados por Dimensiones");
                HoriResDimension.setLegendPosition("ne");
                HoriResDimension.setStacked(true);
                HoriResDimension.setAnimate(true);
                HoriResDimension.setShowPointLabels(true);
                HoriResDimension.setExtender("chartHorizontalExtender");
                HoriResDimension.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                //horizontalBaHistorico.setBarMargin(100);
                //horizontalBaHistorico.setBarPadding(250);FFD700
                HoriResDimension.setSeriesColors("82E0AA,FFFF00,ff7f7f");
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

    /**
     * ************************************************************************************************
     */

    public BarChartModel obtenerResDimensionBar(String uniNegocio) {
        barResDimension = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0; 
        
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaResDimensionbar = resDimensionService.sp_mc_resultadoxdimensiones(uniNeg,anio);
            /*String  val_no_satisfecho;*/
            if (!listaResDimensionbar.isEmpty()) {
                for (ResDimensionBean resDimensionbar : listaResDimensionbar) {
                    res = Math.round(resDimensionbar.getNoSatisfecho().floatValue()) +  Math.round(resDimensionbar.getMedSatisfecho().floatValue()) +  Math.round(resDimensionbar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resDimensionbar.getNoSatisfecho().floatValue()) + Math.round(resDimensionbar.getMedSatisfecho().floatValue()))  ; 
                        resDimensionbar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resDimensionbar.getNoSatisfecho().floatValue()) + Math.round(resDimensionbar.getSatisfecho().floatValue())));    
                        resDimensionbar.setMedSatisfecho(newval);
                    }                    
                    /*val_no_satisfecho = String.valueOf(historico.getPorNoSatisfecho().intValue())+ "%";*/
                    no_satisfecho.set(resDimensionbar.getIdTipoDimension(), resDimensionbar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(resDimensionbar.getIdTipoDimension(), resDimensionbar.getMedSatisfecho().floatValue());
                    satisfecho.set(resDimensionbar.getIdTipoDimension(), resDimensionbar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barResDimension.setTitle("Resultado por Dimensiones");
                barResDimension.setLegendPosition("ne");
                /*barResDimension.setShowDatatip(true);   */
                barResDimension.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                barResDimension.setShowPointLabels(true);
                barResDimension.setAnimate(true);
                barResDimension.setExtender("chartBarExtender"); //
                barResDimension.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barResDimension.addSeries(satisfecho);
        barResDimension.addSeries(med_satisfecho);
        barResDimension.addSeries(no_satisfecho);
        return barResDimension;
    }

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - NO DOCENTES (DIRECTIVA) */
    public PieChartModel obtenerGrpOcuDirectivaPie(String uniNegocio) {
        pieOcuDirectiva = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        idTipoArea = 30701;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* DIRECTIVA */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuNoDocente_Directiva = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista pie directiva  ==> " + listaGrpOcuNoDocente_Directiva.size());
            if (!listaGrpOcuNoDocente_Directiva.isEmpty()) {
                
                for (GrPieChartBean pieGrpDirectivares : listaGrpOcuNoDocente_Directiva) {
                   res += Math.round(pieGrpDirectivares.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpDirectiva : listaGrpOcuNoDocente_Directiva) {
                    val = Math.round(pieGrpDirectiva.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpDirectiva.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpDirectiva.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                pieOcuDirectiva.set(pieGrpDirectiva.getDescripcion(), newval);  
                }
                pieOcuDirectiva.setTitle("DIRECTIVA");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuDirectiva.setFill(true);
                pieOcuDirectiva.setDiameter(200);
                pieOcuDirectiva.setShowDataLabels(true);
                pieOcuDirectiva.setExtender("extLegend");
                pieOcuDirectiva.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuDirectiva;
    }

    /**
     * @return ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - NO DOCENTES (ADMINISTRATIVA) */

    public PieChartModel obtenerGrpOcuAdministrativaPie(String uniNegocio) {
        pieOcuAdministrativa = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        idTipoArea = 30705;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* ADMINISTRATIVA */
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            if (uniNegocio.equalsIgnoreCase("SECTOR")) {
                listaGrpOcuNoDocente_Administrativa = resDimensionService.sp_mc_grp_ocupacionales_sector_adm(anio);
            } else {
                listaGrpOcuNoDocente_Administrativa = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            }
            System.out.println("lista pie administrativa  ==> " + listaGrpOcuNoDocente_Administrativa.size());
            if (!listaGrpOcuNoDocente_Administrativa.isEmpty()) {
                
                for (GrPieChartBean pieGrpAdministrativares : listaGrpOcuNoDocente_Administrativa) {
                   res += Math.round(pieGrpAdministrativares.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpAdministrativa : listaGrpOcuNoDocente_Administrativa) {
                    val = Math.round(pieGrpAdministrativa.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpAdministrativa.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpAdministrativa.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                pieOcuAdministrativa.set(pieGrpAdministrativa.getDescripcion(), newval);
                }
                pieOcuAdministrativa.setTitle("ADMINISTRATIVA");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuAdministrativa.setFill(true);
                pieOcuAdministrativa.setDiameter(200);
                pieOcuAdministrativa.setShowDataLabels(true);
                pieOcuAdministrativa.setExtender("extLegend");
                pieOcuAdministrativa.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuAdministrativa;
    }

    /**
     * ************************************************************************************************
     */

    public PieChartModel obtenerGrpOcuFormativaPie(String uniNegocio) {
        pieOcuFormativa = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        String titulo = "";
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        idTipoArea = 30703;
        /* FORMATIVA */
        titulo = "FORMATIVA";  
        if (uniNegocio.equalsIgnoreCase("SECTOR")) {
            idTipoArea = 30710;
            /* SECRETARIADO */
            titulo = "SECRETARIADO";
        }
        if (uniNegocio.equalsIgnoreCase("UMCH")) {
            titulo = "MANTENIMIENTO";
        }
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            if (uniNegocio.equalsIgnoreCase("UMCH")) {
                listaGrpOcuNoDocente_Formativa = resDimensionService.sp_mc_grp_ocupacionales_no_docentes_mant(uniNeg,30503,anio);
            } else {
                listaGrpOcuNoDocente_Formativa = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            }            
            //listaGrpOcuNoDocente_Formativa = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg);
            System.out.println("lista pie formativa  ==> " + listaGrpOcuNoDocente_Formativa.size());
            if (!listaGrpOcuNoDocente_Formativa.isEmpty()) {
                for (GrPieChartBean pieGrpformativares : listaGrpOcuNoDocente_Formativa) {
                   res += Math.round(pieGrpformativares.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpformativa : listaGrpOcuNoDocente_Formativa) {
                    val = Math.round(pieGrpformativa.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpformativa.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpformativa.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                 pieOcuFormativa.set(pieGrpformativa.getDescripcion(), newval);
                }
                pieOcuFormativa.setTitle(titulo);
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuFormativa.setFill(true);
                pieOcuFormativa.setDiameter(200);
                pieOcuFormativa.setShowDataLabels(true);
                pieOcuFormativa.setExtender("extLegend");
                pieOcuFormativa.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuFormativa;
    }

    /**
     * ************************************************************************************************
     * /* GRUPOS OCUPACIONALES - NO DOCENTES (SECRETARIADO)
     */

    public PieChartModel obtenerGrpOcuSecretariadoPie(String uniNegocio) {
        pieOcuSecretariado = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        idTipoArea = 30710;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* SECRETARIADO */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuNoDocente_Secretariado = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista pie secretariado  ==> " + listaGrpOcuNoDocente_Secretariado.size());
            if (!listaGrpOcuNoDocente_Secretariado.isEmpty()) {
                for (GrPieChartBean pieGrpSecretariadores : listaGrpOcuNoDocente_Secretariado) {
                   res += Math.round(pieGrpSecretariadores.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpSecretariado : listaGrpOcuNoDocente_Secretariado) {
                    val = Math.round(pieGrpSecretariado.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpSecretariado.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpSecretariado.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                    pieOcuSecretariado.set(pieGrpSecretariado.getDescripcion(), newval);
                }
                pieOcuSecretariado.setTitle("SECRETARIADO");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuSecretariado.setFill(true);
                pieOcuSecretariado.setDiameter(200);
                pieOcuSecretariado.setShowDataLabels(true);
                pieOcuSecretariado.setExtender("extLegend");
                pieOcuSecretariado.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuSecretariado;
    }

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - NO DOCENTES (SISTEMAS) */

    public PieChartModel obtenerGrpOcuSistemasPie(String uniNegocio) {
        pieOcuSistemas = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        idTipoArea = 30712;
        /* SISTEMAS */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuNoDocente_Sistemas = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista pie sistemas  ==> " + listaGrpOcuNoDocente_Sistemas.size());
            if (!listaGrpOcuNoDocente_Sistemas.isEmpty()) {
                for (GrPieChartBean pieGrpSistemas : listaGrpOcuNoDocente_Sistemas) {

                    pieOcuSistemas.set(pieGrpSistemas.getDescripcion(), pieGrpSistemas.getResultado().floatValue());
                }
                pieOcuSistemas.setTitle("SISTEMAS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuSistemas.setFill(true);
                pieOcuSistemas.setDiameter(200);
                pieOcuSistemas.setShowDataLabels(true);
                pieOcuSistemas.setExtender("extLegend");
                pieOcuSistemas.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuSecretariado;
    }

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - NO DOCENTES (TESORERIA) */

    public PieChartModel obtenerGrpOcuTesoreriaPie(String uniNegocio) {
        pieOcuTesoreria = new PieChartModel();
        idTipoPersonal = 30502;
        /* NO DOCENTE */
        idTipoArea = 30711;
        /* TESORERIA */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuNoDocente_Tesoreria = resDimensionService.sp_mc_grp_ocupacionales_no_docentes(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista pie tesoreria  ==> " + listaGrpOcuNoDocente_Tesoreria.size());
            if (!listaGrpOcuNoDocente_Tesoreria.isEmpty()) {
                for (GrPieChartBean pieGrpTesoreria : listaGrpOcuNoDocente_Tesoreria) {

                    pieOcuTesoreria.set(pieGrpTesoreria.getDescripcion(), pieGrpTesoreria.getResultado().floatValue());
                }
                pieOcuTesoreria.setTitle("TESORERIA");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuTesoreria.setFill(true);
                pieOcuTesoreria.setDiameter(200);
                pieOcuTesoreria.setShowDataLabels(true);
                pieOcuTesoreria.setExtender("extLegend");
                pieOcuTesoreria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuTesoreria;
    }

    /**
     * ************************************************************************************************
     */

    public BarChartModel obtenerGrpDirectivaBar(String uniNegocio) {
        setBarGrpDirectiva(new BarChartModel());
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30701;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;         
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpDirectivabar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos barras ==> " + listaGrpDirectivabar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpDirectivabar.isEmpty()) {
                for (ResDimensionBean grpDirectivabar : listaGrpDirectivabar) {
                    
                    res = Math.round(grpDirectivabar.getNoSatisfecho().floatValue()) + 
                            Math.round(grpDirectivabar.getMedSatisfecho().floatValue()) +  
                            Math.round(grpDirectivabar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpDirectivabar.getNoSatisfecho().floatValue()) +
                                Math.round(grpDirectivabar.getMedSatisfecho().floatValue()))  ; 
                        grpDirectivabar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpDirectivabar.getNoSatisfecho().floatValue()) + 
                                Math.round(grpDirectivabar.getSatisfecho().floatValue())));    
                        grpDirectivabar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(grpDirectivabar.getIdTipoDimension(), grpDirectivabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpDirectivabar.getIdTipoDimension(), grpDirectivabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpDirectivabar.getIdTipoDimension(), grpDirectivabar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                getBarGrpDirectiva().setTitle("Directivos");
                getBarGrpDirectiva().setLegendPosition("ne");
                getBarGrpDirectiva().setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                /*barHistorico.setShowDatatip(true);    */
                getBarGrpDirectiva().setShowPointLabels(true);
                getBarGrpDirectiva().setAnimate(true);
                getBarGrpDirectiva().setExtender("chartBarExtender");
                getBarGrpDirectiva().setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        getBarGrpDirectiva().addSeries(satisfecho);
        getBarGrpDirectiva().addSeries(med_satisfecho);
        getBarGrpDirectiva().addSeries(no_satisfecho);
        return getBarGrpDirectiva();
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenerGrpAdministrativoBar(String uniNegocio) {
        setBarGrpADministrativa(new BarChartModel());
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30705;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;         
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpAdministrativabar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos administrativa ==> " + listaGrpAdministrativabar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpAdministrativabar.isEmpty()) {
                for (ResDimensionBean grpAdministrativabar : listaGrpAdministrativabar) {
                    res = Math.round(grpAdministrativabar.getNoSatisfecho().floatValue())  + 
						  Math.round(grpAdministrativabar.getMedSatisfecho().floatValue()) +  
						  Math.round(grpAdministrativabar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpAdministrativabar.getNoSatisfecho().floatValue()) +
                                Math.round(grpAdministrativabar.getMedSatisfecho().floatValue()))  ; 
                        grpAdministrativabar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpAdministrativabar.getNoSatisfecho().floatValue()) +
                                Math.round(grpAdministrativabar.getSatisfecho().floatValue())));    
                        grpAdministrativabar.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(grpAdministrativabar.getIdTipoDimension(), grpAdministrativabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpAdministrativabar.getIdTipoDimension(), grpAdministrativabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpAdministrativabar.getIdTipoDimension(), grpAdministrativabar.getSatisfecho().floatValue());
                }
                getBarGrpADministrativa().setTitle("Administrativos");
                getBarGrpADministrativa().setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                getBarGrpADministrativa().setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                getBarGrpADministrativa().setShowPointLabels(true);
                getBarGrpADministrativa().setAnimate(true);
                getBarGrpADministrativa().setExtender("chartBarExtender");
                getBarGrpADministrativa().setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        getBarGrpADministrativa().addSeries(satisfecho);
        getBarGrpADministrativa().addSeries(med_satisfecho);
        getBarGrpADministrativa().addSeries(no_satisfecho);
        return getBarGrpADministrativa();
    }

    /**
     * /**************************************************************************************************
     */
    public BarChartModel obtenerGrpSecretariadoBar(String uniNegocio) {
        barGrpSecretariado = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30710;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpSecretariadobar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos secretariado ==> " + listaGrpSecretariadobar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpSecretariadobar.isEmpty()) {
                for (ResDimensionBean grpSecretariadobar : listaGrpSecretariadobar) {
                    /*val_no_satisfecho = String.valueOf(historico.getPorNoSatisfecho().intValue())+ "%";*/
                    no_satisfecho.set(grpSecretariadobar.getIdTipoDimension(), grpSecretariadobar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpSecretariadobar.getIdTipoDimension(), grpSecretariadobar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpSecretariadobar.getIdTipoDimension(), grpSecretariadobar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barGrpSecretariado.setTitle("Secretaria");
                barGrpSecretariado.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpSecretariado.setShowPointLabels(true);
                barGrpSecretariado.setAnimate(true);
                barGrpSecretariado.setExtender("chartBarExtender");
                barGrpSecretariado.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpSecretariado.addSeries(satisfecho);
        barGrpSecretariado.addSeries(med_satisfecho);
        barGrpSecretariado.addSeries(no_satisfecho);
        return barGrpSecretariado;
    }
    //********************************************************************************************************

    public BarChartModel obtenerGrpSistemasBar(String uniNegocio) {
        barGrpSistemas = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30712;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpSistemasbar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos sistemas ==> " + listaGrpSistemasbar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpSistemasbar.isEmpty()) {
                for (ResDimensionBean grpSistemasbar : listaGrpSistemasbar) {
                    no_satisfecho.set(grpSistemasbar.getIdTipoDimension(), grpSistemasbar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpSistemasbar.getIdTipoDimension(), grpSistemasbar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpSistemasbar.getIdTipoDimension(), grpSistemasbar.getSatisfecho().floatValue());
                }
                barGrpSistemas.setTitle("Sistemas");
                barGrpSistemas.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpSistemas.setShowPointLabels(true);
                barGrpSistemas.setAnimate(true);
                barGrpSistemas.setExtender("chartBarExtender");
                barGrpSistemas.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpSistemas.addSeries(satisfecho);
        barGrpSistemas.addSeries(med_satisfecho);
        barGrpSistemas.addSeries(no_satisfecho);
        return barGrpSistemas;
    }
    //******************************************************************************************************** 

    public BarChartModel obtenerGrpTesoreriaBar(String uniNegocio) {
        barGrpTesoreria = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30711;
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpTesoreriabar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos tesoreria ==> " + listaGrpTesoreriabar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpTesoreriabar.isEmpty()) {
                for (ResDimensionBean grpTesoreriabar : listaGrpTesoreriabar) {
                    no_satisfecho.set(grpTesoreriabar.getIdTipoDimension(), grpTesoreriabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpTesoreriabar.getIdTipoDimension(), grpTesoreriabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpTesoreriabar.getIdTipoDimension(), grpTesoreriabar.getSatisfecho().floatValue());
                }
                barGrpTesoreria.setTitle("Tesoreria");
                barGrpTesoreria.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpTesoreria.setShowPointLabels(true);
                barGrpTesoreria.setAnimate(true);
                barGrpTesoreria.setExtender("chartBarExtender");
                barGrpTesoreria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpTesoreria.addSeries(satisfecho);
        barGrpTesoreria.addSeries(med_satisfecho);
        barGrpTesoreria.addSeries(no_satisfecho);
        return barGrpTesoreria;
    }
    //********************************************************************************************************    
/* 
     *        
     * @return  *************************************************************************************************/

    public BarChartModel obtenerGrpFormativaBar(String uniNegocio) {
        setBarGrpFormativa(new BarChartModel());
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30502;
        idTipoArea = 30703;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;         
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpFormativabar = resDimensionService.sp_mc_grp_ocupacionales_dir_adm_for(idTipoPersonal, idTipoArea, uniNeg,anio);
            System.out.println("lista graficos formativa ==> " + listaGrpFormativabar.size());
            /*String  val_no_satisfecho;*/
            if (!listaGrpFormativabar.isEmpty()) {
                for (ResDimensionBean grpFormativabar : listaGrpFormativabar) {
                res = Math.round(grpFormativabar.getNoSatisfecho().floatValue())  + 
						  Math.round(grpFormativabar.getMedSatisfecho().floatValue()) +  
						  Math.round(grpFormativabar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpFormativabar.getNoSatisfecho().floatValue()) + 
						Math.round(grpFormativabar.getMedSatisfecho().floatValue()))  ; 
                        grpFormativabar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpFormativabar.getNoSatisfecho().floatValue()) +
						Math.round(grpFormativabar.getSatisfecho().floatValue())));    
                        grpFormativabar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(grpFormativabar.getIdTipoDimension(), grpFormativabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpFormativabar.getIdTipoDimension(), grpFormativabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpFormativabar.getIdTipoDimension(), grpFormativabar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                getBarGrpFormativa().setTitle("Formativos");
                getBarGrpFormativa().setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                getBarGrpFormativa().setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                getBarGrpFormativa().setShowPointLabels(true);
                getBarGrpFormativa().setAnimate(true);
                getBarGrpFormativa().setExtender("chartBarExtender");
                getBarGrpFormativa().setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        getBarGrpFormativa().addSeries(satisfecho);
        getBarGrpFormativa().addSeries(med_satisfecho);
        getBarGrpFormativa().addSeries(no_satisfecho);
        return getBarGrpFormativa();
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenersatGeneralBar(String uniNegocio) {
        barSatGeneral = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;

        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaCantPoblacionGrpOcu = resDimensionService.sp_mc_grp_ocupacionales_encuestados(uniNeg,anio);
            listaSatGeneral = resDimensionService.sp_mc_grp_ocupacionales_general(uniNeg,anio);
            System.out.println("lista sat general poblacion ==> " + listaCantPoblacionGrpOcu.size());
            /*String  val_no_satisfecho;*/
            if (!listaSatGeneral.isEmpty()) {
               
                for (ResSatGeneralBean resSatGeneralbar : listaSatGeneral) {           
                    res = Math.round(resSatGeneralbar.getNoSatisfecho().floatValue()) +  Math.round(resSatGeneralbar.getMedSatisfecho().floatValue()) +  Math.round(resSatGeneralbar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(resSatGeneralbar.getNoSatisfecho().floatValue()) + Math.round(resSatGeneralbar.getMedSatisfecho().floatValue()))  ; 
                        resSatGeneralbar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(resSatGeneralbar.getNoSatisfecho().floatValue()) + Math.round(resSatGeneralbar.getSatisfecho().floatValue())));    
                        resSatGeneralbar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(resSatGeneralbar.getDesPersonal(), resSatGeneralbar.getNoSatisfecho());
                    med_satisfecho.set(resSatGeneralbar.getDesPersonal(), resSatGeneralbar.getMedSatisfecho());
                    satisfecho.set(resSatGeneralbar.getDesPersonal(),  resSatGeneralbar.getSatisfecho());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barSatGeneral.setTitle("Satisfaccion General");
                barSatGeneral.setLegendPosition("ne");
                barSatGeneral.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                /*barHistorico.setShowDatatip(true);    */
                barSatGeneral.setShowPointLabels(true);
                barSatGeneral.setAnimate(true);
                barSatGeneral.setExtender("chartBarSatisfaccionExtender");
                barSatGeneral.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barSatGeneral.addSeries(satisfecho);
        barSatGeneral.addSeries(med_satisfecho);
        barSatGeneral.addSeries(no_satisfecho);
        return barSatGeneral;
    }

    /**
     * ************************************************************************************************
     */
    public PieChartModel obtenerSatisfaccionGeneralPie(String uniNegocio) {
        pieSatisfaccionGeneral = new PieChartModel();
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;

        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            System.out.println(cantPoblacionUnineg);
            listaSatisfaccionGeneral = resDimensionService.sp_mc_pie_satisfaccion_general(uniNeg,anio);
            System.out.println("lista pie directiva  ==> " + listaSatisfaccionGeneral.size());
            if (!listaSatisfaccionGeneral.isEmpty()) {
                for (GrPieChartBean pieSatisfaccionGralPieres : listaSatisfaccionGeneral) {
                   res += Math.round(pieSatisfaccionGralPieres.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieSatisfaccionGralPie : listaSatisfaccionGeneral) {
                    val = Math.round(pieSatisfaccionGralPie.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieSatisfaccionGralPie.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieSatisfaccionGralPie.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                       newval = val;
                    }
                    pieSatisfaccionGeneral.set(pieSatisfaccionGralPie.getDescripcion(), newval);
                }
                pieSatisfaccionGeneral.setTitle("Satisfaccion General");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieSatisfaccionGeneral.setFill(true);
                // pieSatisfaccionGeneral.setDiameter(200);
                pieSatisfaccionGeneral.setShowDataLabels(true);
                pieSatisfaccionGeneral.setExtender("extLegend");
                pieSatisfaccionGeneral.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieSatisfaccionGeneral;
    }

    /**
     * ************************************************************************************************
     */
    /* RESULTADOS POR DIMENSION */
    public BarChartModel obtenerGrpOcuMantenimiento(String uniNegocio) {
        barGrpOcuMantenimiento = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuMantenimiento = resDimensionService.sp_mc_grp_ocupacionales_mantenimiento(uniNeg,anio);
            System.out.println("Lista  de Mantenimiento =====>" + listaResDimension.size());
            if (!listaGrpOcuMantenimiento.isEmpty()) {
                for (ResDimensionBean grpOcuMantenimiento : listaGrpOcuMantenimiento) {
                    res = Math.round(grpOcuMantenimiento.getNoSatisfecho().floatValue())  + 
						  Math.round(grpOcuMantenimiento.getMedSatisfecho().floatValue()) +  
						  Math.round(grpOcuMantenimiento.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpOcuMantenimiento.getNoSatisfecho().floatValue()) + 
                                Math.round(grpOcuMantenimiento.getMedSatisfecho().floatValue()))  ; 
                        grpOcuMantenimiento.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpOcuMantenimiento.getNoSatisfecho().floatValue()) +
                                Math.round(grpOcuMantenimiento.getSatisfecho().floatValue())));    
                        grpOcuMantenimiento.setMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(grpOcuMantenimiento.getIdTipoDimension(), grpOcuMantenimiento.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpOcuMantenimiento.getIdTipoDimension(), grpOcuMantenimiento.getMedSatisfecho().floatValue());
                    satisfecho.set(grpOcuMantenimiento.getIdTipoDimension(), grpOcuMantenimiento.getSatisfecho().floatValue());
                }

                barGrpOcuMantenimiento.setTitle("Mantenimiento");
                barGrpOcuMantenimiento.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpOcuMantenimiento.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                barGrpOcuMantenimiento.setShowPointLabels(true);
                barGrpOcuMantenimiento.setAnimate(true);
                barGrpOcuMantenimiento.setExtender("chartBarExtender");
                barGrpOcuMantenimiento.setSeriesColors("82E0AA,FFFF00,ff7f7f");

            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpOcuMantenimiento.addSeries(satisfecho);
        barGrpOcuMantenimiento.addSeries(med_satisfecho);
        barGrpOcuMantenimiento.addSeries(no_satisfecho);
        return barGrpOcuMantenimiento;
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenerHistoricoBar(String uniNegocio) {
        barHistorico = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0; 
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaHistorico = resDimensionService.sp_mc_general_historico(uniNeg);
            System.out.println("Lista  de historico =====>" + listaHistorico.size());
            /*String  val_no_satisfecho;*/
            if (!listaHistorico.isEmpty()) {
                for (HistoricoBean historico : listaHistorico) {
                    res = Math.round(historico.getPorNoSatisfecho().floatValue())  + 
						  Math.round(historico.getPorMedSatisfecho().floatValue()) +  
						  Math.round(historico.getPorSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(historico.getPorNoSatisfecho().floatValue()) + 
						Math.round(historico.getPorMedSatisfecho().floatValue()))  ; 
                        historico.setPorSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(historico.getPorNoSatisfecho().floatValue()) +
						Math.round(historico.getPorSatisfecho().floatValue())));    
                        historico.setPorMedSatisfecho(newval);
                    }                    
                    no_satisfecho.set(historico.getAnio(), historico.getPorNoSatisfecho().floatValue());
                    med_satisfecho.set(historico.getAnio(), historico.getPorMedSatisfecho().floatValue());
                    satisfecho.set(historico.getAnio(), historico.getPorSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barHistorico.setTitle("Historico");
                barHistorico.setLegendPosition("ne");
                barHistorico.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                /*barHistorico.setShowDatatip(true);    */
                barHistorico.setStacked(true);
                barHistorico.setShowPointLabels(true);
                barHistorico.setAnimate(true);
                barHistorico.setExtender("chartBarHistoricoExtender");
                barHistorico.setSeriesColors("82E0AA,FFFF00,ff7f7f");
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

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - DOCENTES (INICIAL) */

    public PieChartModel obtenerGrpOcuInicialPie(String uniNegocio) {
        pieOcuInicial = new PieChartModel();
        idTipoPersonal = 30501;
        /* DOCENTE */
        idNivelAcademico = 6;
        /* INICIAL */
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuDocente_Inicial = resDimensionService.sp_mc_grp_ocupacionales_docentes(idTipoPersonal, idNivelAcademico, uniNeg,anio);

            if (!listaGrpOcuDocente_Inicial.isEmpty()) {
                for (GrPieChartBean pieGrpInicialres : listaGrpOcuDocente_Inicial) {
                   res += Math.round(pieGrpInicialres.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpInicial : listaGrpOcuDocente_Inicial) {
                    val = Math.round(pieGrpInicial.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpInicial.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpInicial.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                       newval = val;
                    }
                    pieOcuInicial.set(pieGrpInicial.getDescripcion(), newval);
                }

                pieOcuInicial.setTitle("INICIAL");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuInicial.setFill(true);
                pieOcuInicial.setDiameter(200);
                pieOcuInicial.setShowDataLabels(true);
                pieOcuInicial.setExtender("extLegend");
                pieOcuInicial.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuInicial;
    }

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - DOCENTES (PRIMARIA) */

    public PieChartModel obtenerGrpOcuPrimarialPie(String uniNegocio) {
        pieOcuPrimaria = new PieChartModel();
        idTipoPersonal = 30501;
        /* DOCENTE */
        idNivelAcademico = 5;
        /* PRIMARIA */
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuDocente_Primaria = resDimensionService.sp_mc_grp_ocupacionales_docentes(idTipoPersonal, idNivelAcademico, uniNeg,anio);

            if (!listaGrpOcuDocente_Primaria.isEmpty()) {
                for (GrPieChartBean pieGrpPrimariares : listaGrpOcuDocente_Primaria) {
                   res += Math.round(pieGrpPrimariares.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpPrimaria : listaGrpOcuDocente_Primaria) {
                    val = Math.round(pieGrpPrimaria.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpPrimaria.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpPrimaria.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                       newval = val;
                    }
                    pieOcuPrimaria.set(pieGrpPrimaria.getDescripcion(), newval);
                }                

                pieOcuPrimaria.setTitle("PRIMARIA");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuPrimaria.setFill(true);
                pieOcuPrimaria.setDiameter(200);
                pieOcuPrimaria.setShowDataLabels(true);
                pieOcuPrimaria.setExtender("extLegend");
                pieOcuPrimaria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuPrimaria;
    }

    /**
     * ************************************************************************************************
     */
    /* GRUPOS OCUPACIONALES - DOCENTES (SECUNDARIA) */

    public PieChartModel obtenerGrpOcuSecundariaPie(String uniNegocio) {
        pieOcuSecundaria = new PieChartModel();
        idTipoPersonal = 30501;
        /* DOCENTE */
        idNivelAcademico = 4;
        /* SECUNDARIA */
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpOcuDocente_Secundaria = resDimensionService.sp_mc_grp_ocupacionales_docentes(idTipoPersonal, idNivelAcademico, uniNeg,anio);

            if (!listaGrpOcuDocente_Secundaria.isEmpty()) {
                for (GrPieChartBean pieGrpSecundariares : listaGrpOcuDocente_Secundaria) {
                   res += Math.round(pieGrpSecundariares.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieGrpSecundaria : listaGrpOcuDocente_Secundaria) {
                    val = Math.round(pieGrpSecundaria.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieGrpSecundaria.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieGrpSecundaria.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                       newval = val;
                    }
                    pieOcuSecundaria.set(pieGrpSecundaria.getDescripcion(), newval);
                }                 

                pieOcuSecundaria.setTitle("SECUNDARIA");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieOcuSecundaria.setFill(true);
                pieOcuSecundaria.setDiameter(200);
                pieOcuSecundaria.setShowDataLabels(true);
                pieOcuSecundaria.setExtender("extLegend");
                pieOcuSecundaria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieOcuSecundaria;
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenerGrpInicialBar(String uniNegocio) {
        barGrpInicial = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30501;
        idNivelAcademico = 6;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;         
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpInicialbar = resDimensionService.sp_mc_grp_ocupacionales_ini_pri_sec(idTipoPersonal, idNivelAcademico, uniNeg, anio);
            /*String  val_no_satisfecho;*/
            if (!listaGrpInicialbar.isEmpty()) {
                for (ResDimensionBean grpInicialbar : listaGrpInicialbar) {
                res = Math.round(grpInicialbar.getNoSatisfecho().floatValue())  + 
						  Math.round(grpInicialbar.getMedSatisfecho().floatValue()) +  
						  Math.round(grpInicialbar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpInicialbar.getNoSatisfecho().floatValue()) + 
						Math.round(grpInicialbar.getMedSatisfecho().floatValue()))  ; 
                        grpInicialbar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpInicialbar.getNoSatisfecho().floatValue()) +
						Math.round(grpInicialbar.getSatisfecho().floatValue())));    
                        grpInicialbar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(grpInicialbar.getIdTipoDimension(), grpInicialbar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpInicialbar.getIdTipoDimension(), grpInicialbar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpInicialbar.getIdTipoDimension(), grpInicialbar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barGrpInicial.setTitle("INICIAL");
                barGrpInicial.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpInicial.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                barGrpInicial.setShowPointLabels(true);
                barGrpInicial.setAnimate(true);
                barGrpInicial.setExtender("chartBarExtender");
                barGrpInicial.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpInicial.addSeries(satisfecho);
        barGrpInicial.addSeries(med_satisfecho);
        barGrpInicial.addSeries(no_satisfecho);
        return barGrpInicial;
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenerGrpPrimariaBar(String uniNegocio) {
        barGrpPrimaria = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30501;
        idNivelAcademico = 5;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0;         
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpPrimariabar = resDimensionService.sp_mc_grp_ocupacionales_ini_pri_sec(idTipoPersonal, idNivelAcademico, uniNeg, anio);

            /*String  val_no_satisfecho;*/
            if (!listaGrpPrimariabar.isEmpty()) {
                for (ResDimensionBean grpPrimariabar : listaGrpPrimariabar) {
                    res = Math.round(grpPrimariabar.getNoSatisfecho().floatValue())  + 
						  Math.round(grpPrimariabar.getMedSatisfecho().floatValue()) +  
						  Math.round(grpPrimariabar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpPrimariabar.getNoSatisfecho().floatValue()) + 
						Math.round(grpPrimariabar.getMedSatisfecho().floatValue()))  ; 
                        grpPrimariabar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpPrimariabar.getNoSatisfecho().floatValue()) +
						Math.round(grpPrimariabar.getSatisfecho().floatValue())));    
                        grpPrimariabar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(grpPrimariabar.getIdTipoDimension(), grpPrimariabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpPrimariabar.getIdTipoDimension(), grpPrimariabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpPrimariabar.getIdTipoDimension(), grpPrimariabar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barGrpPrimaria.setTitle("PRIMARIA");
                barGrpPrimaria.setLegendPosition("ne");
                barGrpPrimaria.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                /*barHistorico.setShowDatatip(true);    */
                barGrpPrimaria.setShowPointLabels(true);
                barGrpPrimaria.setAnimate(true);
                barGrpPrimaria.setExtender("chartBarExtender");
                barGrpPrimaria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpPrimaria.addSeries(satisfecho);
        barGrpPrimaria.addSeries(med_satisfecho);
        barGrpPrimaria.addSeries(no_satisfecho);
        return barGrpPrimaria;
    }

    /**
     * ************************************************************************************************
     */
    public BarChartModel obtenerGrpSecundariaBar(String uniNegocio) {
        barGrpSecundaria = new BarChartModel();
        ChartSeries satisfecho = new ChartSeries();
        satisfecho.setLabel("Satisfecho");
        ChartSeries med_satisfecho = new ChartSeries();
        med_satisfecho.setLabel("Medio Satisfecho");
        ChartSeries no_satisfecho = new ChartSeries();
        no_satisfecho.setLabel("No Satisfecho");
        idTipoPersonal = 30501;
        idNivelAcademico = 4;
        Integer res=0;
        Integer des=0;
        float nosat=0;
        float newval=0; 
		        
        try {
            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaGrpSecundariabar = resDimensionService.sp_mc_grp_ocupacionales_ini_pri_sec(idTipoPersonal, idNivelAcademico, uniNeg, anio);

            /*String  val_no_satisfecho;*/
            if (!listaGrpSecundariabar.isEmpty()) {
                for (ResDimensionBean grpSecundariabar : listaGrpSecundariabar) {
                    res = Math.round(grpSecundariabar.getNoSatisfecho().floatValue())  + 
						  Math.round(grpSecundariabar.getMedSatisfecho().floatValue()) +  
						  Math.round(grpSecundariabar.getSatisfecho().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        newval = res - (des + Math.round(grpSecundariabar.getNoSatisfecho().floatValue()) + 
						Math.round(grpSecundariabar.getMedSatisfecho().floatValue()))  ; 
                        grpSecundariabar.setSatisfecho(newval);
                    }
                    if(res < 100){
                        des = 100 - res; 
                        newval = des + (res - (Math.round(grpSecundariabar.getNoSatisfecho().floatValue()) +
						Math.round(grpSecundariabar.getSatisfecho().floatValue())));    
                        grpSecundariabar.setMedSatisfecho(newval);
                    }
                    no_satisfecho.set(grpSecundariabar.getIdTipoDimension(), grpSecundariabar.getNoSatisfecho().floatValue());
                    med_satisfecho.set(grpSecundariabar.getIdTipoDimension(), grpSecundariabar.getMedSatisfecho().floatValue());
                    satisfecho.set(grpSecundariabar.getIdTipoDimension(), grpSecundariabar.getSatisfecho().floatValue());
                    /* System.out.println(" No satisfecho ====>" + historico.getPorNoSatisfecho().floatValue());
                    System.out.println(" Med satisfecho ===>" + historico.getPorMedSatisfecho().floatValue());
                    System.out.println(" Satisfecho =======>" + historico.getPorSatisfecho().floatValue());*/
                }
                barGrpSecundaria.setTitle("SECUNDARIA");
                barGrpSecundaria.setLegendPosition("ne");
                /*barHistorico.setShowDatatip(true);    */
                barGrpSecundaria.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
                barGrpSecundaria.setShowPointLabels(true);
                barGrpSecundaria.setAnimate(true);
                barGrpSecundaria.setExtender("chartBarExtender");
                barGrpSecundaria.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        barGrpSecundaria.addSeries(satisfecho);
        barGrpSecundaria.addSeries(med_satisfecho);
        barGrpSecundaria.addSeries(no_satisfecho);
        return barGrpSecundaria;
    }

    /**
     * ************************************************************************************************
     */
    /* TIPOS DE  SEXO - MASCULINO */
    public PieChartModel obtenerTipoMasculinoPie(String uniNegocio) {
        pieTipoMasculino = new PieChartModel();
        sexo = 1;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* MASCULINO */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaTipo_Masculino = resDimensionService.sp_mc_tipo_sexo(sexo, uniNeg, anio);
            if (!listaTipo_Masculino.isEmpty()) {
                for (GrPieChartBean pieTipMasculinores : listaTipo_Masculino) {
                   res += Math.round(pieTipMasculinores.getResultado().floatValue());                    
                }
                System.out.println("res Masculino : " +res);
                for (GrPieChartBean pieTipMasculino : listaTipo_Masculino) {
                    val = Math.round(pieTipMasculino.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieTipMasculino.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieTipMasculino.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }
                    System.out.println("Masculino : " +newval);
                    pieTipoMasculino.set(pieTipMasculino.getDescripcion(), newval);
                }
                pieTipoMasculino.setTitle("HOMBRES");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieTipoMasculino.setFill(true);
                pieTipoMasculino.setDiameter(150);
                pieTipoMasculino.setShowDataLabels(true);
                pieTipoMasculino.setExtender("extLegend");
                pieTipoMasculino.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieTipoMasculino;
    }

    /**
     * ************************************************************************************************
     */
    /* TIPOS DE  SEXO - FEMENICO */

    public PieChartModel obtenerTipoFemeninoPie(String uniNegocio) {
        pieTipoFemenino = new PieChartModel();
        sexo = 2;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* FEMENINO */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaTipo_Femenino = resDimensionService.sp_mc_tipo_sexo(sexo, uniNeg, anio);

            if (!listaTipo_Femenino.isEmpty()) {
                for (GrPieChartBean pieTipFemeninores : listaTipo_Femenino) {
                   res += Math.round(pieTipFemeninores.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieTipFemenino : listaTipo_Femenino) {
                    val = Math.round(pieTipFemenino.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieTipFemenino.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieTipFemenino.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                    pieTipoFemenino.set(pieTipFemenino.getDescripcion(),newval) ;
                }                
                pieTipoFemenino.setTitle("MUJERES");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieTipoFemenino.setFill(true);
                pieTipoFemenino.setDiameter(150);
                pieTipoFemenino.setShowDataLabels(true);
                pieTipoFemenino.setExtender("extLegend");
                pieTipoFemenino.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieTipoFemenino;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30801 */
    public PieChartModel obtenerEdad_30801Pie(String uniNegocio) {
        pieEdad_30801 = new PieChartModel();
        idEdad = 30801;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30801 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaEdad_30801 = resDimensionService.sp_mc_rango_de_edades(idEdad, uniNeg, anio);
            if (!listaEdad_30801.isEmpty()) {
                
                for (GrPieChartBean pieEdad30801res : listaEdad_30801) {
                   res += Math.round(pieEdad30801res.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieEdad30801 : listaEdad_30801) {
                    val = Math.round(pieEdad30801.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieEdad30801.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieEdad30801.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                    pieEdad_30801.set(pieEdad30801.getDescripcion(), newval);
                }                 

                pieEdad_30801.setTitle("25 - 35 AÑOS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieEdad_30801.setFill(true);
                pieEdad_30801.setDiameter(150);
                pieEdad_30801.setShowDataLabels(true);
                pieEdad_30801.setExtender("extLegend");
                pieEdad_30801.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieEdad_30801;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30802 */
    public PieChartModel obtenerEdad_30802Pie(String uniNegocio) {
        pieEdad_30802 = new PieChartModel();
        idEdad = 30802;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30802 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaEdad_30802 = resDimensionService.sp_mc_rango_de_edades(idEdad, uniNeg, anio);
            if (!listaEdad_30802.isEmpty()) {
                for (GrPieChartBean pieEdad30802res : listaEdad_30802) {
                   res += Math.round(pieEdad30802res.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieEdad30802 : listaEdad_30802) {
                    val = Math.round(pieEdad30802.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieEdad30802.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }
                    if(res < 100){
                        des = 100 - res;                        
                        if(pieEdad30802.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                    pieEdad_30802.set(pieEdad30802.getDescripcion(), newval);
                }
                pieEdad_30802.setTitle("36 - 45 AÑOS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieEdad_30802.setFill(true);
                pieEdad_30802.setDiameter(150);
                pieEdad_30802.setShowDataLabels(true);
                pieEdad_30802.setExtender("extLegend");
                pieEdad_30802.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieEdad_30802;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30803 */
    public PieChartModel obtenerEdad_30803Pie(String uniNegocio) {
        pieEdad_30803 = new PieChartModel();
        idEdad = 30803;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30803 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaEdad_30803 = resDimensionService.sp_mc_rango_de_edades(idEdad, uniNeg, anio);
            if (!listaEdad_30803.isEmpty()) {
                for (GrPieChartBean pieEdad30803re : listaEdad_30803) {
                   res += Math.round(pieEdad30803re.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieEdad30803 : listaEdad_30803) {
                    val = Math.round(pieEdad30803.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieEdad30803.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieEdad30803.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    } 
                    if(res == 100){
                        newval = val;
                    }                    
                    pieEdad_30803.set(pieEdad30803.getDescripcion(), newval);
                }
                pieEdad_30803.setTitle("46 AÑOS A MÁS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieEdad_30803.setFill(true);
                pieEdad_30803.setDiameter(150);
                pieEdad_30803.setShowDataLabels(true);
                pieEdad_30803.setExtender("extLegend");
                pieEdad_30803.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieEdad_30803;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30601 */
    public PieChartModel obtenerEdad_30601Pie(String uniNegocio) {
        pieTiempoServicio_30601 = new PieChartModel();
        idTiempoServicio = 30601;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30803 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaTiempoServicio_30601 = resDimensionService.sp_mc_tiempo_de_servicio(idTiempoServicio, uniNeg,anio);
            if (!listaTiempoServicio_30601.isEmpty()) {
                for (GrPieChartBean pieTiempoServicio30601res : listaTiempoServicio_30601) {
                   res += Math.round(pieTiempoServicio30601res.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieTiempoServicio30601 : listaTiempoServicio_30601) {
                    val = Math.round(pieTiempoServicio30601.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieTiempoServicio30601.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieTiempoServicio30601.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }  
                    if(res == 100){
                        newval = val;
                    }                  
                    pieTiempoServicio_30601.set(pieTiempoServicio30601.getDescripcion(), newval);
                }
                pieTiempoServicio_30601.setTitle("0 - 3 AÑOS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieTiempoServicio_30601.setFill(true);
                pieTiempoServicio_30601.setDiameter(150);
                pieTiempoServicio_30601.setShowDataLabels(true);
                pieTiempoServicio_30601.setExtender("extLegend");
                pieTiempoServicio_30601.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieTiempoServicio_30601;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30602 */
    public PieChartModel obtenerEdad_30602Pie(String uniNegocio) {
        pieTiempoServicio_30602 = new PieChartModel();
        idTiempoServicio = 30602;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30602 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaTiempoServicio_30602 = resDimensionService.sp_mc_tiempo_de_servicio(idTiempoServicio, uniNeg,anio);
            if (!listaTiempoServicio_30602.isEmpty()) {
                
                for (GrPieChartBean pieTiempoServicio30602res : listaTiempoServicio_30602) {
                   res += Math.round(pieTiempoServicio30602res.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieTiempoServicio30602 : listaTiempoServicio_30602) {
                    val = Math.round(pieTiempoServicio30602.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieTiempoServicio30602.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieTiempoServicio30602.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }  
                    if(res == 100){
                        newval = val;
                    }                    
                    pieTiempoServicio_30602.set(pieTiempoServicio30602.getDescripcion(), newval);
                }
                pieTiempoServicio_30602.setTitle("4 - 10 AÑOS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieTiempoServicio_30602.setFill(true);
                pieTiempoServicio_30602.setDiameter(150);
                pieTiempoServicio_30602.setShowDataLabels(true);
                pieTiempoServicio_30602.setExtender("extLegend");
                pieTiempoServicio_30602.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieTiempoServicio_30602;
    }

    /**
     * ************************************************************************************************
     */
    /* EDAD - 30603 */
    public PieChartModel obtenerEdad_30603Pie(String uniNegocio) {
        pieTiempoServicio_30603 = new PieChartModel();
        idTiempoServicio = 30603;
        Integer res=0;
        Integer des=0;
        Integer val=0;
        Integer newval=0;        
        /* 30603 */
        try {

            ResDimensionService resDimensionService = BeanFactory.getResDimensionService();
            listaTiempoServicio_30603 = resDimensionService.sp_mc_tiempo_de_servicio(idTiempoServicio, uniNeg,anio);
            if (!listaTiempoServicio_30603.isEmpty()) {
                
                for (GrPieChartBean pieTiempoServicio30603res : listaTiempoServicio_30603) {
                   res += Math.round(pieTiempoServicio30603res.getResultado().floatValue());                    
                }
                for (GrPieChartBean pieTiempoServicio30603 : listaTiempoServicio_30603) {
                    val = Math.round(pieTiempoServicio30603.getResultado().floatValue());                    
                    if(res > 100){
                        des = res -100; 
                        if(pieTiempoServicio30603.getDescripcion().equalsIgnoreCase("SATISFECHO")){
                           newval = val-des;
                        }else{
                           newval= val;
                        }                       
                    }if(res < 100){
                        des = 100 - res;                        
                        if(pieTiempoServicio30603.getDescripcion().equalsIgnoreCase("MED. SATISFECHO")){
                           newval = val + des;
                        }else{
                           newval= val;
                        }                         
                    }
                    if(res == 100){
                        newval = val;
                    }                    
                    pieTiempoServicio_30603.set(pieTiempoServicio30603.getDescripcion(), newval);
                }
                pieTiempoServicio_30603.setTitle("11 A MÁS");
                //pieHistoricoSat.setLegendPosition("s");
                //pieHistoricoSat.setLegendRows(1);
                pieTiempoServicio_30603.setFill(true);
                pieTiempoServicio_30603.setDiameter(150);
                pieTiempoServicio_30603.setShowDataLabels(true);
                pieTiempoServicio_30603.setExtender("extLegend");
                pieTiempoServicio_30603.setSeriesColors("82E0AA,FFFF00,ff7f7f");
            } else {
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return pieTiempoServicio_30603;
    }

    /**
     * @return the HoriResDimension
     */
    public HorizontalBarChartModel getHoriResDimension() {
        return HoriResDimension;
    }

    /**
     * @param HoriResDimension the HoriResDimension to set
     */
    public void setHoriResDimension(HorizontalBarChartModel HoriResDimension) {
        this.HoriResDimension = HoriResDimension;
    }

    /**
     * @return the listaResDimension
     */
    public List<ResDimensionBean> getListaResDimension() {
        if (listaResDimension == null) {
            listaResDimension = new ArrayList<>();
        }
        return listaResDimension;
    }

    /**
     * @param listaResDimension the listaResDimension to set
     */
    public void setListaResDimension(List<ResDimensionBean> listaResDimension) {
        this.listaResDimension = listaResDimension;
    }

    /**
     * @return the listaResDimensionbar
     */
    public List<ResDimensionBean> getListaResDimensionbar() {
        if (listaResDimensionbar == null) {
            listaResDimensionbar = new ArrayList<>();
        }
        return listaResDimensionbar;
    }

    /**
     * @param listaResDimensionbar the listaResDimensionbar to set
     */
    public void setListaResDimensionbar(List<ResDimensionBean> listaResDimensionbar) {
        this.listaResDimensionbar = listaResDimensionbar;
    }

    /**
     * @return the barResDimension
     */
    public BarChartModel getBarResDimension() {
        return barResDimension;
    }

    /**
     * @param barResDimension the barResDimension to set
     */
    public void setBarResDimension(BarChartModel barResDimension) {
        this.barResDimension = barResDimension;
    }

    /**
     * @return the pieOcuDirectiva
     */
    public PieChartModel getPieOcuDirectiva() {
        return pieOcuDirectiva;
    }

    /**
     * @param pieOcuDirectiva the pieOcuDirectiva to set
     */
    public void setPieOcuDirectiva(PieChartModel pieOcuDirectiva) {
        this.pieOcuDirectiva = pieOcuDirectiva;
    }

    /**
     * @return the idTipoArea
     */
    public int getIdTipoArea() {
        return idTipoArea;
    }

    /**
     * @param idTipoArea the idTipoArea to set
     */
    public void setIdTipoArea(int idTipoArea) {
        this.idTipoArea = idTipoArea;
    }

    /**
     * @return the listaGrpOcuNoDocente_Directiva
     */
    public List<GrPieChartBean> getListaGrpOcuNoDocente_Directiva() {
        if (listaGrpOcuNoDocente_Directiva == null) {
            listaGrpOcuNoDocente_Directiva = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Directiva;
    }

    /**
     * @param listaGrpOcuNoDocente_Directiva the listaGrpOcuNoDocente_Directiva
     * to set
     */
    public void setListaGrpOcuNoDocente_Directiva(List<GrPieChartBean> listaGrpOcuNoDocente_Directiva) {
        this.listaGrpOcuNoDocente_Directiva = listaGrpOcuNoDocente_Directiva;
    }

    /**
     * @return the listaGrpOcuNoDocente_Administrativa
     */
    public List<GrPieChartBean> getListaGrpOcuNoDocente_Administrativa() {
        if (listaGrpOcuNoDocente_Administrativa == null) {
            listaGrpOcuNoDocente_Administrativa = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Administrativa;
    }

    /**
     * @param listaGrpOcuNoDocente_Administrativa the
     * listaGrpOcuNoDocente_Administrativa to set
     */
    public void setListaGrpOcuNoDocente_Administrativa(List<GrPieChartBean> listaGrpOcuNoDocente_Administrativa) {
        this.listaGrpOcuNoDocente_Administrativa = listaGrpOcuNoDocente_Administrativa;
    }

    /**
     * @return the pieOcuAdministrativa
     */
    public PieChartModel getPieOcuAdministrativa() {
        return pieOcuAdministrativa;
    }

    /**
     * @param pieOcuAdministrativa the pieOcuAdministrativa to set
     */
    public void setPieOcuAdministrativa(PieChartModel pieOcuAdministrativa) {
        this.pieOcuAdministrativa = pieOcuAdministrativa;
    }

    /**
     * @return the idTipoPersonal
     */
    public int getIdTipoPersonal() {
        return idTipoPersonal;
    }

    /**
     * @param idTipoPersonal the idTipoPersonal to set
     */
    public void setIdTipoPersonal(int idTipoPersonal) {
        this.idTipoPersonal = idTipoPersonal;
    }

    /**
     * @return the listaGrpDirectivabar
     */
    public List<ResDimensionBean> getListaGrpDirectivabar() {
        if (listaGrpDirectivabar == null) {
            listaGrpDirectivabar = new ArrayList<>();
        }
        return listaGrpDirectivabar;
    }

    /**
     * @param listaGrpDirectivabar the listaGrpDirectivabar to set
     */
    public void setListaGrpDirectivabar(List<ResDimensionBean> listaGrpDirectivabar) {
        this.listaGrpDirectivabar = listaGrpDirectivabar;
    }

    /**
     * @return the listaGrpAdministrativabar
     */
    public List<ResDimensionBean> getListaGrpAdministrativabar() {
        if (listaGrpAdministrativabar == null) {
            listaGrpAdministrativabar = new ArrayList<>();
        }
        return listaGrpAdministrativabar;
    }

    /**
     * @param listaGrpAdministrativabar the listaGrpAdministrativabar to set
     */
    public void setListaGrpAdministrativabar(List<ResDimensionBean> listaGrpAdministrativabar) {
        this.listaGrpAdministrativabar = listaGrpAdministrativabar;
    }

    /**
     * @return the listaGrpFormativabar
     */
    public List<ResDimensionBean> getListaGrpFormativabar() {
        if (listaGrpFormativabar == null) {
            listaGrpFormativabar = new ArrayList<>();
        }
        return listaGrpFormativabar;
    }

    /**
     * @param listaGrpFormativabar the listaGrpFormativabar to set
     */
    public void setListaGrpFormativabar(List<ResDimensionBean> listaGrpFormativabar) {
        this.listaGrpFormativabar = listaGrpFormativabar;
    }

    /**
     * @return the barGrpDirectiva
     */
    public BarChartModel getBarGrpDirectiva() {
        return barGrpDirectiva;
    }

    /**
     * @param barGrpDirectiva the barGrpDirectiva to set
     */
    public void setBarGrpDirectiva(BarChartModel barGrpDirectiva) {
        this.barGrpDirectiva = barGrpDirectiva;
    }

    /**
     * @return the barGrpADministrativa
     */
    public BarChartModel getBarGrpADministrativa() {
        return barGrpADministrativa;
    }

    /**
     * @param barGrpADministrativa the barGrpADministrativa to set
     */
    public void setBarGrpADministrativa(BarChartModel barGrpADministrativa) {
        this.barGrpADministrativa = barGrpADministrativa;
    }

    /**
     * @return the barGrpFormativa
     */
    public BarChartModel getBarGrpFormativa() {
        return barGrpFormativa;
    }

    /**
     * @param barGrpFormativa the barGrpFormativa to set
     */
    public void setBarGrpFormativa(BarChartModel barGrpFormativa) {
        this.barGrpFormativa = barGrpFormativa;
    }

    /**
     * @return the barSatGeneral
     */
    public BarChartModel getBarSatGeneral() {
        return barSatGeneral;
    }

    /**
     * @param barSatGeneral the barSatGeneral to set
     */
    public void setBarSatGeneral(BarChartModel barSatGeneral) {
        this.barSatGeneral = barSatGeneral;
    }

    /**
     * @return the listaSatGeneral
     */
    public List<ResSatGeneralBean> getListaSatGeneral() {
        return listaSatGeneral;
    }

    /**
     * @param listaSatGeneral the listaSatGeneral to set
     */
    public void setListaSatGeneral(List<ResSatGeneralBean> listaSatGeneral) {
        this.listaSatGeneral = listaSatGeneral;
    }

    /**
     * @return the pieSatisfaccionGeneral
     */
    public PieChartModel getPieSatisfaccionGeneral() {
        return pieSatisfaccionGeneral;
    }

    /**
     * @param pieSatisfaccionGeneral the pieSatisfaccionGeneral to set
     */
    public void setPieSatisfaccionGeneral(PieChartModel pieSatisfaccionGeneral) {
        this.pieSatisfaccionGeneral = pieSatisfaccionGeneral;
    }

    /**
     * @return the listaSatisfaccionGeneral
     */
    public List<GrPieChartBean> getListaSatisfaccionGeneral() {
        if (listaSatisfaccionGeneral == null) {
            listaSatisfaccionGeneral = new ArrayList<>();
        }
        return listaSatisfaccionGeneral;
    }

    /**
     * @param listaSatisfaccionGeneral the listaSatisfaccionGeneral to set
     */
    public void setListaSatisfaccionGeneral(List<GrPieChartBean> listaSatisfaccionGeneral) {
        this.listaSatisfaccionGeneral = listaSatisfaccionGeneral;
    }

    /**
     * @return the listaGrpOcuMantenimiento
     */
    public List<ResDimensionBean> getListaGrpOcuMantenimiento() {
        if (listaGrpOcuMantenimiento == null) {
            listaGrpOcuMantenimiento = new ArrayList<>();
        }
        return listaGrpOcuMantenimiento;
    }

    /**
     * @param listaGrpOcuMantenimiento the listaGrpOcuMantenimiento to set
     */
    public void setListaGrpOcuMantenimiento(List<ResDimensionBean> listaGrpOcuMantenimiento) {
        this.listaGrpOcuMantenimiento = listaGrpOcuMantenimiento;
    }

    /**
     * @return the barGrpOcuMantenimiento
     */
    public BarChartModel getBarGrpOcuMantenimiento() {
        return barGrpOcuMantenimiento;
    }

    /**
     * @param barGrpOcuMantenimiento the barGrpOcuMantenimiento to set
     */
    public void setBarGrpOcuMantenimiento(BarChartModel barGrpOcuMantenimiento) {
        this.barGrpOcuMantenimiento = barGrpOcuMantenimiento;
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

    /**
     * @param listaHistorico the listaHistorico to set
     */
    public void setListaHistorico(List<HistoricoBean> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

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
     * @return the pieOcuInicial
     */
    public PieChartModel getPieOcuInicial() {
        return pieOcuInicial;
    }

    /**
     * @param pieOcuInicial the pieOcuInicial to set
     */
    public void setPieOcuInicial(PieChartModel pieOcuInicial) {
        this.pieOcuInicial = pieOcuInicial;
    }

    /**
     * @return the pieOcuPrimaria
     */
    public PieChartModel getPieOcuPrimaria() {
        return pieOcuPrimaria;
    }

    /**
     * @param pieOcuPrimaria the pieOcuPrimaria to set
     */
    public void setPieOcuPrimaria(PieChartModel pieOcuPrimaria) {
        this.pieOcuPrimaria = pieOcuPrimaria;
    }

    /**
     * @return the pieOcuSecundaria
     */
    public PieChartModel getPieOcuSecundaria() {
        return pieOcuSecundaria;
    }

    /**
     * @param pieOcuSecundaria the pieOcuSecundaria to set
     */
    public void setPieOcuSecundaria(PieChartModel pieOcuSecundaria) {
        this.pieOcuSecundaria = pieOcuSecundaria;
    }

    /**
     * @return the listaGrpOcuDocente_Inicial
     */
    public List<GrPieChartBean> getListaGrpOcuDocente_Inicial() {
        if (listaGrpOcuDocente_Inicial == null) {
            listaGrpOcuDocente_Inicial = new ArrayList<>();
        }
        return listaGrpOcuDocente_Inicial;
    }

    /**
     * @param listaGrpOcuDocente_Inicial the listaGrpOcuDocente_Inicial to set
     */
    public void setListaGrpOcuDocente_Inicial(List<GrPieChartBean> listaGrpOcuDocente_Inicial) {
        this.listaGrpOcuDocente_Inicial = listaGrpOcuDocente_Inicial;
    }

    /**
     * @return the listaGrpOcuDocente_Primaria
     */
    public List<GrPieChartBean> getListaGrpOcuDocente_Primaria() {
        if (listaGrpOcuDocente_Primaria == null) {
            listaGrpOcuDocente_Primaria = new ArrayList<>();
        }
        return listaGrpOcuDocente_Primaria;
    }

    /**
     * @param listaGrpOcuDocente_Primaria the listaGrpOcuDocente_Primaria to set
     */
    public void setListaGrpOcuDocente_Primaria(List<GrPieChartBean> listaGrpOcuDocente_Primaria) {
        this.listaGrpOcuDocente_Primaria = listaGrpOcuDocente_Primaria;
    }

    /**
     * @return the listaGrpOcuDocente_Secundaria
     */
    public List<GrPieChartBean> getListaGrpOcuDocente_Secundaria() {
        if (listaGrpOcuDocente_Secundaria == null) {
            listaGrpOcuDocente_Secundaria = new ArrayList<>();
        }
        return listaGrpOcuDocente_Secundaria;
    }

    /**
     * @param listaGrpOcuDocente_Secundaria the listaGrpOcuDocente_Secundaria to
     * set
     */
    public void setListaGrpOcuDocente_Secundaria(List<GrPieChartBean> listaGrpOcuDocente_Secundaria) {
        this.listaGrpOcuDocente_Secundaria = listaGrpOcuDocente_Secundaria;
    }

    /**
     * @return the idNivelAcademico
     */
    public int getIdNivelAcademico() {
        return idNivelAcademico;
    }

    /**
     * @param idNivelAcademico the idNivelAcademico to set
     */
    public void setIdNivelAcademico(int idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    /**
     * @return the barGrpInicial
     */
    public BarChartModel getBarGrpInicial() {
        return barGrpInicial;
    }

    /**
     * @param barGrpInicial the barGrpInicial to set
     */
    public void setBarGrpInicial(BarChartModel barGrpInicial) {
        this.barGrpInicial = barGrpInicial;
    }

    /**
     * @return the barGrpPrimaria
     */
    public BarChartModel getBarGrpPrimaria() {
        return barGrpPrimaria;
    }

    /**
     * @param barGrpPrimaria the barGrpPrimaria to set
     */
    public void setBarGrpPrimaria(BarChartModel barGrpPrimaria) {
        this.barGrpPrimaria = barGrpPrimaria;
    }

    /**
     * @return the barGrpSecundaria
     */
    public BarChartModel getBarGrpSecundaria() {
        return barGrpSecundaria;
    }

    /**
     * @param barGrpSecundaria the barGrpSecundaria to set
     */
    public void setBarGrpSecundaria(BarChartModel barGrpSecundaria) {
        this.barGrpSecundaria = barGrpSecundaria;
    }

    /**
     * @return the listaGrpInicialbar
     */
    public List<ResDimensionBean> getListaGrpInicialbar() {
        if (listaGrpInicialbar == null) {
            listaGrpInicialbar = new ArrayList<>();
        }
        return listaGrpInicialbar;
    }

    /**
     * @param listaGrpInicialbar the listaGrpInicialbar to set
     */
    public void setListaGrpInicialbar(List<ResDimensionBean> listaGrpInicialbar) {
        this.listaGrpInicialbar = listaGrpInicialbar;
    }

    /**
     * @return the listaGrpPrimariabar
     */
    public List<ResDimensionBean> getListaGrpPrimariabar() {
        if (listaGrpPrimariabar == null) {
            listaGrpPrimariabar = new ArrayList<>();
        }
        return listaGrpPrimariabar;
    }

    /**
     * @param listaGrpPrimariabar the listaGrpPrimariabar to set
     */
    public void setListaGrpPrimariabar(List<ResDimensionBean> listaGrpPrimariabar) {
        this.listaGrpPrimariabar = listaGrpPrimariabar;
    }

    /**
     * @return the listaGrpSecundariabar
     */
    public List<ResDimensionBean> getListaGrpSecundariabar() {
        if (listaGrpSecundariabar == null) {
            listaGrpSecundariabar = new ArrayList<>();
        }
        return listaGrpSecundariabar;
    }

    /**
     * @param listaGrpSecundariabar the listaGrpSecundariabar to set
     */
    public void setListaGrpSecundariabar(List<ResDimensionBean> listaGrpSecundariabar) {
        this.listaGrpSecundariabar = listaGrpSecundariabar;
    }

    public PieChartModel getPieTipoMasculino() {
        return pieTipoMasculino;
    }

    public void setPieTipoMasculino(PieChartModel pieTipoMasculino) {
        this.pieTipoMasculino = pieTipoMasculino;
    }

    public PieChartModel getPieTipoFemenino() {
        return pieTipoFemenino;
    }

    public void setPieTipoFemenino(PieChartModel pieTipoFemenino) {
        this.pieTipoFemenino = pieTipoFemenino;
    }

    public List<GrPieChartBean> getListaTipo_Femenino() {
        if (listaTipo_Femenino == null) {
            listaTipo_Femenino = new ArrayList<>();
        }
        return listaTipo_Femenino;
    }

    public void setListaTipo_Femenino(List<GrPieChartBean> listaTipo_Femenino) {
        this.listaTipo_Femenino = listaTipo_Femenino;
    }

    public List<GrPieChartBean> getListaTipo_Masculino() {
        if (listaTipo_Masculino == null) {
            listaTipo_Masculino = new ArrayList<>();
        }
        return listaTipo_Masculino;
    }

    public void setListaTipo_Masculino(List<GrPieChartBean> listaTipo_Masculino) {
        this.listaTipo_Masculino = listaTipo_Masculino;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public PieChartModel getPieEdad_30801() {
        return pieEdad_30801;
    }

    public void setPieEdad_30801(PieChartModel pieEdad_30801) {
        this.pieEdad_30801 = pieEdad_30801;
    }

    public PieChartModel getPieEdad_30802() {
        return pieEdad_30802;
    }

    public void setPieEdad_30802(PieChartModel pieEdad_30802) {
        this.pieEdad_30802 = pieEdad_30802;
    }

    public PieChartModel getPieEdad_30803() {
        return pieEdad_30803;
    }

    public void setPieEdad_30803(PieChartModel pieEdad_30803) {
        this.pieEdad_30803 = pieEdad_30803;
    }

    public List<GrPieChartBean> getListaEdad_30801() {
        if (listaEdad_30801 == null) {
            listaEdad_30801 = new ArrayList<>();
        }
        return listaEdad_30801;
    }

    public void setListaEdad_30801(List<GrPieChartBean> listaEdad_30801) {
        this.listaEdad_30801 = listaEdad_30801;
    }

    public List<GrPieChartBean> getListaEdad_30802() {
        if (listaEdad_30802 == null) {
            listaEdad_30802 = new ArrayList<>();
        }
        return listaEdad_30802;
    }

    public void setListaEdad_30802(List<GrPieChartBean> listaEdad_30802) {
        this.listaEdad_30802 = listaEdad_30802;
    }

    public List<GrPieChartBean> getListaEdad_30803() {
        if (listaEdad_30803 == null) {
            listaEdad_30803 = new ArrayList<>();
        }
        return listaEdad_30803;
    }

    public void setListaEdad_30803(List<GrPieChartBean> listaEdad_30803) {
        this.listaEdad_30803 = listaEdad_30803;
    }

    public int getIdEdad() {
        return idEdad;
    }

    public void setIdEdad(int idEdad) {
        this.idEdad = idEdad;
    }

    public PieChartModel getPieTiempoServicio_30601() {
        return pieTiempoServicio_30601;
    }

    public void setPieTiempoServicio_30601(PieChartModel pieTiempoServicio_30601) {
        this.pieTiempoServicio_30601 = pieTiempoServicio_30601;
    }

    public PieChartModel getPieTiempoServicio_30602() {
        return pieTiempoServicio_30602;
    }

    public void setPieTiempoServicio_30602(PieChartModel pieTiempoServicio_30602) {
        this.pieTiempoServicio_30602 = pieTiempoServicio_30602;
    }

    public PieChartModel getPieTiempoServicio_30603() {
        return pieTiempoServicio_30603;
    }

    public void setPieTiempoServicio_30603(PieChartModel pieTiempoServicio_30603) {
        this.pieTiempoServicio_30603 = pieTiempoServicio_30603;
    }

    public List<GrPieChartBean> getListaTiempoServicio_30601() {
        if (listaTiempoServicio_30601 == null) {
            listaTiempoServicio_30601 = new ArrayList<>();
        }
        return listaTiempoServicio_30601;
    }

    public void setListaTiempoServicio_30601(List<GrPieChartBean> listaTiempoServicio_30601) {
        this.listaTiempoServicio_30601 = listaTiempoServicio_30601;
    }

    public List<GrPieChartBean> getListaTiempoServicio_30602() {
        if (listaTiempoServicio_30602 == null) {
            listaTiempoServicio_30602 = new ArrayList<>();
        }
        return listaTiempoServicio_30602;
    }

    public void setListaTiempoServicio_30602(List<GrPieChartBean> listaTiempoServicio_30602) {
        this.listaTiempoServicio_30602 = listaTiempoServicio_30602;
    }

    public List<GrPieChartBean> getListaTiempoServicio_30603() {
        if (listaTiempoServicio_30603 == null) {
            listaTiempoServicio_30603 = new ArrayList<>();
        }
        return listaTiempoServicio_30603;
    }

    public void setListaTiempoServicio_30603(List<GrPieChartBean> listaTiempoServicio_30603) {
        this.listaTiempoServicio_30603 = listaTiempoServicio_30603;
    }

    public int getIdTiempoServicio() {
        return idTiempoServicio;
    }

    public void setIdTiempoServicio(int idTiempoServicio) {
        this.idTiempoServicio = idTiempoServicio;
    }

    public List<UniNegBean> getListaUniNeg() {
        return listaUniNeg;
    }

    public void setListaUniNeg(List<UniNegBean> listaUniNeg) {
        this.listaUniNeg = listaUniNeg;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public PieChartModel getPieOcuSecretariado() {
        return pieOcuSecretariado;
    }

    public void setPieOcuSecretariado(PieChartModel pieOcuSecretariado) {
        this.pieOcuSecretariado = pieOcuSecretariado;
    }

    public PieChartModel getPieOcuSistemas() {
        return pieOcuSistemas;
    }

    public void setPieOcuSistemas(PieChartModel pieOcuSistemas) {
        this.pieOcuSistemas = pieOcuSistemas;
    }

    public PieChartModel getPieOcuTesoreria() {
        return pieOcuTesoreria;
    }

    public void setPieOcuTesoreria(PieChartModel pieOcuTesoreria) {
        this.pieOcuTesoreria = pieOcuTesoreria;
    }

    public List<GrPieChartBean> getListaGrpOcuNoDocente_Secretariado() {
        if (listaGrpOcuNoDocente_Secretariado == null) {
            listaGrpOcuNoDocente_Secretariado = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Secretariado;
    }

    public void setListaGrpOcuNoDocente_Secretariado(List<GrPieChartBean> listaGrpOcuNoDocente_Secretariado) {
        this.listaGrpOcuNoDocente_Secretariado = listaGrpOcuNoDocente_Secretariado;
    }

    public List<GrPieChartBean> getListaGrpOcuNoDocente_Sistemas() {
        if (listaGrpOcuNoDocente_Sistemas == null) {
            listaGrpOcuNoDocente_Sistemas = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Sistemas;
    }

    public void setListaGrpOcuNoDocente_Sistemas(List<GrPieChartBean> listaGrpOcuNoDocente_Sistemas) {
        this.listaGrpOcuNoDocente_Sistemas = listaGrpOcuNoDocente_Sistemas;
    }

    public List<GrPieChartBean> getListaGrpOcuNoDocente_Tesoreria() {
        if (listaGrpOcuNoDocente_Tesoreria == null) {
            listaGrpOcuNoDocente_Tesoreria = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Tesoreria;
    }

    public void setListaGrpOcuNoDocente_Tesoreria(List<GrPieChartBean> listaGrpOcuNoDocente_Tesoreria) {
        this.listaGrpOcuNoDocente_Tesoreria = listaGrpOcuNoDocente_Tesoreria;
    }

    public BarChartModel getBarGrpSecretariado() {
        return barGrpSecretariado;
    }

    public void setBarGrpSecretariado(BarChartModel barGrpSecretariado) {
        this.barGrpSecretariado = barGrpSecretariado;
    }

    public BarChartModel getBarGrpSistemas() {
        return barGrpSistemas;
    }

    public void setBarGrpSistemas(BarChartModel barGrpSistemas) {
        this.barGrpSistemas = barGrpSistemas;
    }

    public BarChartModel getBarGrpTesoreria() {
        return barGrpTesoreria;
    }

    public void setBarGrpTesoreria(BarChartModel barGrpTesoreria) {
        this.barGrpTesoreria = barGrpTesoreria;
    }

    public List<ResDimensionBean> getListaGrpSecretariadobar() {
        if (listaGrpSecretariadobar == null) {
            listaGrpSecretariadobar = new ArrayList<>();
        }
        return listaGrpSecretariadobar;
    }

    public void setListaGrpSecretariadobar(List<ResDimensionBean> listaGrpSecretariadobar) {
        this.listaGrpSecretariadobar = listaGrpSecretariadobar;
    }

    public List<ResDimensionBean> getListaGrpSistemasbar() {
        if (listaGrpSistemasbar == null) {
            listaGrpSistemasbar = new ArrayList<>();
        }
        return listaGrpSistemasbar;
    }

    public void setListaGrpSistemasbar(List<ResDimensionBean> listaGrpSistemasbar) {
        this.listaGrpSistemasbar = listaGrpSistemasbar;
    }

    public List<ResDimensionBean> getListaGrpTesoreriabar() {
        if (listaGrpTesoreriabar == null) {
            listaGrpTesoreriabar = new ArrayList<>();
        }
        return listaGrpTesoreriabar;
    }

    public void setListaGrpTesoreriabar(List<ResDimensionBean> listaGrpTesoreriabar) {
        this.listaGrpTesoreriabar = listaGrpTesoreriabar;
    }

    public PieChartModel getPieOcuFormativa() {
        return pieOcuFormativa;
    }

    public void setPieOcuFormativa(PieChartModel pieOcuFormativa) {
        this.pieOcuFormativa = pieOcuFormativa;
    }

    public List<GrPieChartBean> getListaGrpOcuNoDocente_Formativa() {
        if (listaGrpOcuNoDocente_Formativa == null) {
            listaGrpOcuNoDocente_Formativa = new ArrayList<>();
        }
        return listaGrpOcuNoDocente_Formativa;
    }

    public void setListaGrpOcuNoDocente_Formativa(List<GrPieChartBean> listaGrpOcuNoDocente_Formativa) {
        this.listaGrpOcuNoDocente_Formativa = listaGrpOcuNoDocente_Formativa;
    }

    public int getFlag_Print() {
        return flag_Print;
    }

    public void setFlag_Print(int flag_Print) {
        this.flag_Print = flag_Print;
    }

    public List<PoblacionBean> getListaCantPoblacion() {
        if (listaCantPoblacion == null) {
            listaCantPoblacion = new ArrayList<>();
        }        
        return listaCantPoblacion;
    }

    public void setListaCantPoblacion(List<PoblacionBean> listaCantPoblacion) {
        this.listaCantPoblacion = listaCantPoblacion;
    }

    public int getCantPoblacionUnineg() {
        return cantPoblacionUnineg;
    }

    public void setCantPoblacionUnineg(int cantPoblacionUnineg) {
        this.cantPoblacionUnineg = cantPoblacionUnineg;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionGrpOcu() {
        if (listaCantPoblacionGrpOcu == null) {
            listaCantPoblacionGrpOcu = new ArrayList<>();
        }                
        return listaCantPoblacionGrpOcu;
    }

    public void setListaCantPoblacionGrpOcu(List<PoblacionGrOcuBean> listaCantPoblacionGrpOcu) {
        this.listaCantPoblacionGrpOcu = listaCantPoblacionGrpOcu;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionSexo() {
        if (listaCantPoblacionSexo == null) {
            listaCantPoblacionSexo = new ArrayList<>();
        }              
        return listaCantPoblacionSexo;
    }

    public void setListaCantPoblacionSexo(List<PoblacionGrOcuBean> listaCantPoblacionSexo) {
        this.listaCantPoblacionSexo = listaCantPoblacionSexo;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionRangoEdad() {
        if (listaCantPoblacionRangoEdad == null) {
            listaCantPoblacionRangoEdad = new ArrayList<>();
        }              
        return listaCantPoblacionRangoEdad;
    }

    public void setListaCantPoblacionRangoEdad(List<PoblacionGrOcuBean> listaCantPoblacionRangoEdad) {
        this.listaCantPoblacionRangoEdad = listaCantPoblacionRangoEdad;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionTiempoSer() {
        if (listaCantPoblacionTiempoSer == null) {
            listaCantPoblacionTiempoSer = new ArrayList<>();
        }              
        return listaCantPoblacionTiempoSer;
    }

    public void setListaCantPoblacionTiempoSer(List<PoblacionGrOcuBean> listaCantPoblacionTiempoSer) {
        this.listaCantPoblacionTiempoSer = listaCantPoblacionTiempoSer;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionGrpAdmDirFor() {
        if (listaCantPoblacionGrpAdmDirFor == null) {
            listaCantPoblacionGrpAdmDirFor = new ArrayList<>();
        }              
        return listaCantPoblacionGrpAdmDirFor;
    }

    public void setListaCantPoblacionGrpAdmDirFor(List<PoblacionGrOcuBean> listaCantPoblacionGrpAdmDirFor) {
        this.listaCantPoblacionGrpAdmDirFor = listaCantPoblacionGrpAdmDirFor;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionInicial() {
        if (listaCantPoblacionInicial == null) {
            listaCantPoblacionInicial = new ArrayList<>();
        }          
        return listaCantPoblacionInicial;
    }

    public void setListaCantPoblacionInicial(List<PoblacionGrOcuBean> listaCantPoblacionInicial) {
        this.listaCantPoblacionInicial = listaCantPoblacionInicial;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionPrimaria() {
        if (listaCantPoblacionPrimaria == null) {
            listaCantPoblacionPrimaria = new ArrayList<>();
        }          
        return listaCantPoblacionPrimaria;
    }

    public void setListaCantPoblacionPrimaria(List<PoblacionGrOcuBean> listaCantPoblacionPrimaria) {
        this.listaCantPoblacionPrimaria = listaCantPoblacionPrimaria;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionSecundaria() {
        if (listaCantPoblacionSecundaria == null) {
            listaCantPoblacionSecundaria = new ArrayList<>();
        }          
        return listaCantPoblacionSecundaria;
    }

    public void setListaCantPoblacionSecundaria(List<PoblacionGrOcuBean> listaCantPoblacionSecundaria) {
        this.listaCantPoblacionSecundaria = listaCantPoblacionSecundaria;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionDirectiva() {
        if (listaCantPoblacionDirectiva == null) {
            listaCantPoblacionDirectiva = new ArrayList<>();
        }            
        return listaCantPoblacionDirectiva;
    }

    public void setListaCantPoblacionDirectiva(List<PoblacionGrOcuBean> listaCantPoblacionDirectiva) {
        this.listaCantPoblacionDirectiva = listaCantPoblacionDirectiva;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionAdministrativa() {
        if (listaCantPoblacionAdministrativa == null) {
            listaCantPoblacionAdministrativa = new ArrayList<>();
        }            
        return listaCantPoblacionAdministrativa;
    }

    public void setListaCantPoblacionAdministrativa(List<PoblacionGrOcuBean> listaCantPoblacionAdministrativa) {
        this.listaCantPoblacionAdministrativa = listaCantPoblacionAdministrativa;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionformativa() {
        if (listaCantPoblacionformativa == null) {
            listaCantPoblacionformativa = new ArrayList<>();
        }            
        return listaCantPoblacionformativa;
    }

    public void setListaCantPoblacionformativa(List<PoblacionGrOcuBean> listaCantPoblacionformativa) {
        this.listaCantPoblacionformativa = listaCantPoblacionformativa;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionMantenimiento() {
        if (listaCantPoblacionMantenimiento == null) {
            listaCantPoblacionMantenimiento = new ArrayList<>();
        }            
        return listaCantPoblacionMantenimiento;
    }

    public void setListaCantPoblacionMantenimiento(List<PoblacionGrOcuBean> listaCantPoblacionMantenimiento) {
        this.listaCantPoblacionMantenimiento = listaCantPoblacionMantenimiento;
    }

    public List<UniNegBean> getListaUnidadNegocio() {
        if (listaUnidadNegocio == null) {
            listaUnidadNegocio = new ArrayList<>();
        }            
        return listaUnidadNegocio;
    }

    public void setListaUnidadNegocio(List<UniNegBean> listaUnidadNegocio) {
        this.listaUnidadNegocio = listaUnidadNegocio;
    }

    public String getNombre_unidad() {
        return nombre_unidad;
    }

    public void setNombre_unidad(String nombre_unidad) {
        this.nombre_unidad = nombre_unidad;
    }

    public List<PoblacionGrOcuBean> getListaCantPoblacionNivelAcademico() {
        if (listaCantPoblacionNivelAcademico == null) {
            listaCantPoblacionNivelAcademico = new ArrayList<>();
        }              
        return listaCantPoblacionNivelAcademico;
    }

    public void setListaCantPoblacionNivelAcademico(List<PoblacionGrOcuBean> listaCantPoblacionNivelAcademico) {
        this.listaCantPoblacionNivelAcademico = listaCantPoblacionNivelAcademico;
    }

    public List<CantidadBean> getCargartablas() {
        if (cargartablas == null) {
            cargartablas = new ArrayList<>();
        }            
        return cargartablas;
    }

    public void setCargartablas(List<CantidadBean> cargartablas) {
        this.cargartablas = cargartablas;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public static String getRec_titulo_grp_ocupa() {
        return rec_titulo_grp_ocupa;
    }

    public static void setRec_titulo_grp_ocupa(String rec_titulo_grp_ocupa) {
        ResDimensionMB.rec_titulo_grp_ocupa = rec_titulo_grp_ocupa;
    }

    public String getTitulo_grp_ocupa() {
        return titulo_grp_ocupa;
    }

    public void setTitulo_grp_ocupa(String titulo_grp_ocupa) {
        this.titulo_grp_ocupa = titulo_grp_ocupa;
    }

    public String getTitulo_grp_ocupa_2() {
        return titulo_grp_ocupa_2;
    }

    public void setTitulo_grp_ocupa_2(String titulo_grp_ocupa_2) {
        this.titulo_grp_ocupa_2 = titulo_grp_ocupa_2;
    }



    
    
}
